package com.tricycle.up.framework;

import java.io.Serializable;

/**
 * @author pzf
 * @version 1.0
 * @date 2023/2/13 1:22
 * @description
 */
public class Result implements Serializable {

    public static final int SUCCESS_CODE=1;
    public static final String SUCCESS_MSG="操作成功！";

    public static final int FAIL_CODE=-1;
    public static final String FAIL_MSG="操作失败！";
    
    private int code;
    private String msg;
    private Object data;

    public static Result getSucc(Object data){
        return new Result(Result.SUCCESS_CODE, Result.SUCCESS_MSG,data);
    }

    public static Result getSucc(){
        return getSucc(null);
    }

    public static Result getFail(){
        return new Result();
    }
    public static Result getFail(Integer code,String msg){
        return new Result(code,msg,null);
    }

    public static Result getFail(String msg){
        return new Result(Result.FAIL_CODE, msg,null);
    }


    public Result(){
        this.code= Result.FAIL_CODE;
        this.msg= Result.FAIL_MSG;
        this.data="";
    }
    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public Result(int success){
        this.code= Result.SUCCESS_CODE;
        this.msg= Result.SUCCESS_MSG;
        this.data="";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

