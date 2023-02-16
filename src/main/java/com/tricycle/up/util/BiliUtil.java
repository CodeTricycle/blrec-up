package com.tricycle.up.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tricycle.up.config.Config;
import com.tricycle.up.entity.Live;
import com.tricycle.up.entity.QRCode;
import com.tricycle.up.entity.User;
import com.tricycle.up.entity.Video;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author tricycle
 * @version 1.0
 * @date 2023/2/7 9:01
 * @description
 */
@Slf4j
public class BiliUtil {

    public static ExecutorService uploadService;
    public static BlockingQueue<Runnable> uploadQueue;

    static {
        uploadQueue = new LinkedBlockingQueue<>();
        uploadService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                uploadQueue);
    }

    public static Map<String, Object> sign(Map<String, Object> params) {
        params.put("appkey", Config.APP_KEY);
        params = BiliUtil.sorted(params);
        String query = URLUtil.buildQuery(params, CharsetUtil.parse("UTF-8"));
        String sign = DigestUtil.md5Hex(query + Config.APP_SEC);
        params.put("sign", sign);
        params = BiliUtil.sorted(params);
        return params;
    }

    private static Map<String, Object> sorted(Map<String, Object> params) {
        return MapUtil.sort(params);
    }

    public static QRCode getQRCode() {
        Map<String, Object> params = new HashMap<>();
        params.put("local_id", 0);
        params.put("ts", System.currentTimeMillis());
        params = BiliUtil.sign(params);

        HttpResponse response = HttpRequest.post("https://passport.bilibili.com/x/passport-tv-login/qrcode/auth_code")
                .form(params)
                .execute();

        JSONObject object = JSONUtil.parseObj(response.body());
        if (object.getInt("code") == 0) {
            JSONObject data = object.getJSONObject("data");
            QRCode qrCode = new QRCode();
            qrCode.setUrl(data.getStr("url"));
            qrCode.setAuthCode(data.getStr("auth_code"));
            return qrCode;
        }
        return null;
    }

    public static User login(String authCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("auth_code", authCode);
        params.put("local_id", 0);
        params.put("ts", System.currentTimeMillis());
        params = BiliUtil.sign(params);

        HttpResponse response = HttpRequest.post("https://passport.bilibili.com/x/passport-tv-login/qrcode/poll")
                .form(params)
                .execute();

        JSONObject object = JSONUtil.parseObj(response.body());
        if (object.getInt("code") != 0) {
            return null;
        }
        User user = new User();
        JSONObject data = object.getJSONObject("data");
        user.setMid(data.getStr("mid"));
        user.setAccessToken(data.getStr("access_token"));
        user.setRefreshToken(data.getStr("refresh_token"));

        List<JSONObject> cookies = data.getJSONObject("cookie_info").getJSONArray("cookies").toList(JSONObject.class);
        Map<String, String> cookieMap = cookies.stream().collect(Collectors.toMap(item -> item.getStr("name"), item -> item.getStr("value")));
        user.setSessData(cookieMap.get("SESSDATA"));
        user.setBiliJct(cookieMap.get("bili_jct"));
        user.setDedeUserId(cookieMap.get("DedeUserID"));
        user.setCkMd5(cookieMap.get("DedeUserID__ckMd5"));
        user.setSid(cookieMap.get("sid"));

        return user;
    }

    /**
     * 文件预处理
     *
     * @param video
     * @param user
     * @return
     * @throws Exception
     */
    public static void preUpload(Video video, User user) throws Exception {
        Map<String, String> params =
                MapUtil.builder("access_key", user.getAccessToken())
                        .put("mid", user.getMid())
                        .put("profile", "ugcfr/pc3")
                        .build();

        String url, filename, complete;
        HttpRequest preUploadRequest = HttpRequest.get("https://member.bilibili.com/preupload?" +
                        URLUtil.buildQuery(params, CharsetUtil.parse(CharsetUtil.UTF_8)))
                .cookie("sid=" + user.getSid());

        JSONObject preUpload = HttpUtil.execute(preUploadRequest, 1);
        if (preUpload.getInt("OK") == 1) {
            url = preUpload.getStr("url");
            filename = preUpload.getStr("filename");
            complete = preUpload.getStr("complete");
            video.setUrl(url);
            video.setFilename(filename);
            video.setComplete(complete);
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @param video
     * @throws IOException
     */
    public static void uploadFile(File file, Video video) throws IOException {
        RandomAccessFile randomAccessFile = null;
        try {
            String filename = file.getName();
            String serverFilename = video.getFilename();
            String url = video.getUrl();
            int chunks = new BigDecimal(file.length()).divide(BigDecimal.valueOf(Config.CHUNK_SIZE)).setScale(0, BigDecimal.ROUND_UP).intValue();//切片
            long total = file.length();

            randomAccessFile = new RandomAccessFile(file, "r");
            String md5Hex = "";
            int successChunk = 0;
            byte[] buffer = new byte[Config.CHUNK_SIZE];
            int end = 0;
            for (int chunk = 0; chunk < chunks; chunk++) {
                try {
                    if (end + buffer.length > file.length()) {
                        buffer = new byte[(int) (file.length() - end)];
                    }
                    randomAccessFile.read(buffer);
                    log.info("{}上传中，进度：{}/{}", filename, (chunk + 1), chunks);
                    HttpRequest uploadRequest = HttpRequest.post(url)
                            .cookie("PHPSESSID=" + serverFilename)
                            .contentType("multipart/form-data")
                            .form("version", "2.3.0.1088")
                            .form("filesize", buffer.length)
                            .form("chunk", chunk)
                            .form("chunks", chunks)
                            .form("md5", DigestUtil.md5Hex(buffer))
                            .form("file", buffer, "file");

                    JSONObject uploadObject = HttpUtil.execute(uploadRequest, 10);
                    end += Config.CHUNK_SIZE;
                    if (uploadObject.getInt("OK") == 1) {
                        md5Hex = DigestUtil.md5Hex(md5Hex + buffer);
                        successChunk++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            randomAccessFile.close();

            if (successChunk == chunks) {
                //上传完成
                HttpRequest complete = HttpRequest.post(video.getComplete())
                        .form("chunks", chunks)
                        .form("filesize", total)
                        .form("md5", md5Hex)
                        .form("name", filename)
                        .form("version", "2.3.0.1088");
                String completeBody = complete.execute().body();
                JSONObject completeObj = JSONUtil.parseObj(completeBody);
                if (completeObj.getInt("OK") == 1) {
                    video.setSuccess(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (Objects.nonNull(randomAccessFile)) {
                randomAccessFile.close();
            }
        }
    }

    public static String uploadImg(String imgPath, User user) {
        Map<String, Object> addParams = new HashMap<>();
        addParams.put("access_key", user.getAccessToken());
        addParams = sign(addParams);

        HttpRequest coverRequest = HttpRequest.post("https://member.bilibili.com/x/vu/client/cover/up?" +
                        URLUtil.buildQuery(addParams, CharsetUtil.parse("UTF-8")))
                .form("file", new File(imgPath));

        JSONObject coverObject = HttpUtil.execute(coverRequest, 5);
        String url = coverObject.getJSONObject("data").getStr("url");
        return url;
    }

    public static boolean release(String imgUrl, Live live, List<Video> videoList, User user) {
        String desc = BiliUtil.parseTemplate(live.getDesc(), live, videoList);
        String title = BiliUtil.parseTemplate(live.getTitleTemplate(), live, videoList);
        JSONArray videos = BiliUtil.getVideos(desc, videoList, live);

        Map<String, Object> params = new HashMap<>();
        params.put("access_key", user.getAccessToken());
        params = BiliUtil.sign(params);

        JSONObject body = new JSONObject();

        if (live.getCopyright()) {
            //自制
            body.set("desc", desc);
        } else {
            body.set("source", desc);
        }

        body.set("cover", imgUrl);
        body.set("title", title);
        body.set("copyright", live.getCopyright() ? 1 : 2);
        body.set("tag", live.getTags());
        body.set("recreate", -1);
        body.set("tid", live.getTid());
        body.set("dynamic", desc);
        body.set("interactive", 0);
        body.set("new_web_edit", 1);
        body.set("act_reserve_create", 0);
        body.set("handle_staff", false);
        body.set("topic_grey", 1);
        body.set("mission_id", 0);
        body.set("no_reprint", 0);
        body.set("is_360", -1);
        body.set("web_os", 1);
        body.set("csrf", user.getBiliJct());
        body.set("videos", videos);
        HttpRequest request = HttpRequest.post("https://member.bilibili.com/x/vu/client/add?" +
                        URLUtil.buildQuery(params, CharsetUtil.parse("UTF-8")))
                .body(JSONUtil.toJsonStr(body));

        String releaseResponse = request.execute().body();
        JSONObject releaseObj = JSONUtil.parseObj(releaseResponse);
        Integer code = releaseObj.getInt("code");
        if (code == 0 || code == 21503) {
            log.info("发布成功");
            return true;
        }

        return false;
    }


    private static JSONArray getVideos(String desc, List<Video> videoList, Live live) {
        JSONArray videos = new JSONArray();
        for (Video video : videoList) {
            videos.add(new JSONObject()
                    .set("desc", desc)
                    .set("filename", video.getFilename())
                    .set("title", parsePart(video, live)));
        }
        return videos;
    }

    private static String parsePart(Video video, Live live) {
        return live.getPartTitleTemplate().replace("${index}", String.valueOf(video.getPIndex()));
    }

    private static String parseTemplate(String content, Live live, List<Video> videoList) {
        Video video = videoList.get(0);

        DateTime dateTime = DateTime.of(video.getFileOpenTime());

        if (Objects.nonNull(live.getName())) {
            content = content.replace("${uname}", live.getName());
        } else {
            content = content.replace("${uname}", "直播回放");
        }

        return content
                .replace("${roomId}", String.valueOf(live.getRoomId()))
                .replace("${title}", live.getTitle())
                .replace("yyyy", String.valueOf(dateTime.getField(DateField.YEAR)))
                .replace("MM", String.valueOf(dateTime.getField(DateField.MONTH)))
                .replace("dd", String.valueOf(dateTime.getField(DateField.DAY_OF_MONTH)))
                .replace("HH", String.valueOf(dateTime.getField(DateField.HOUR_OF_DAY)))
                .replace("mm", String.valueOf(dateTime.getField(DateField.MINUTE)))
                .replace("ss", String.valueOf(dateTime.getField(DateField.SECOND)))
                .replace("${", "")
                .replace("}", "");
    }

}
