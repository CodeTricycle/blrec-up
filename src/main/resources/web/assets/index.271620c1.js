import{r as e,a as t,w as l,o as i,c as a,b as o,d as n,t as s,e as d,f as r,n as u,F as m,g as c,h as p,i as h,j as f,k as b,E as g,l as v,m as _,p as w,q as V,s as y,V as C,u as T}from"./vendor.63d8578b.js";!function(){const e=document.createElement("link").relList;if(!(e&&e.supports&&e.supports("modulepreload"))){for(const e of document.querySelectorAll('link[rel="modulepreload"]'))t(e);new MutationObserver((e=>{for(const l of e)if("childList"===l.type)for(const e of l.addedNodes)"LINK"===e.tagName&&"modulepreload"===e.rel&&t(e)})).observe(document,{childList:!0,subtree:!0})}function t(e){if(e.ep)return;e.ep=!0;const t=function(e){const t={};return e.integrity&&(t.integrity=e.integrity),e.referrerpolicy&&(t.referrerPolicy=e.referrerpolicy),"use-credentials"===e.crossorigin?t.credentials="include":"anonymous"===e.crossorigin?t.credentials="omit":t.credentials="same-origin",t}(e);fetch(e.href,t)}}();const L={};L.render=function(o,n){const s=e("router-view"),d=t("wechat-title");return l((i(),a(s,null,null,512)),[[d,o.$route.meta.title]])};var I="biliUp",k="biliUp",U=[{name:"直播间",routerName:"live",disabled:!1},{name:"用户",routerName:"user",disabled:!1},{name:"录制历史",routerName:"session",disabled:!1}];const x={components:{},data:function(){return{isCollapse:!1,mainTabs:[],mainTabsActiveName:"",menuActiveName:"",menus:[],longTitle:"",littleTitle:"",breadcrumbObj:{},breadcrumbList:[]}},created(){let e=this;e.routeHandle(e.$route),e.menus=U,e.longTitle=I,e.littleTitle=k,e.breadcrumbList=[e.$route.meta.title];var t=e.menus;for(let l of t)if(l.submenu)for(let t of l.submenu)if(t.submenu)for(let i of t.submenu)i.submenu||(e.breadcrumbObj[i.name]=[l.name,t.name,i.name]);else e.breadcrumbObj[t.name]=[l.name,t.name],console.log(t.name);else e.breadcrumbObj[l.name]=[l.name],console.log(l.name)},watch:{$route:{handler(e,t){e.path!=t.path&&(this.routeHandle(e),this.breadcrumbList=this.breadcrumbObj[e.meta.title])}}},methods:{selectedTabHandle:function(e,t){(e=this.mainTabs.filter((t=>t.name===e.paneName))).length>=1&&this.$router.push({name:e[0].name,query:e[0].query,params:e[0].params})},removeTabHandle:function(e){if(this.mainTabs=this.mainTabs.filter((t=>t.name!==e)),this.mainTabs.length>=1){if(e===this.mainTabsActiveName){var t=this.mainTabs[this.mainTabs.length-1];this.$router.push({name:t.name,query:t.query,params:t.params},(()=>{this.mainTabsActiveName=this.$route.name}))}}else this.menuActiveName="",this.$router.push({name:"Home"})},resetDocumentClientHeight:function(){this.documentClientHeight=document.documentElement.clientHeight,window.onresize=()=>{this.documentClientHeight=document.documentElement.clientHeight,this.loadSiteContentViewHeight()}},loadSiteContentViewHeight:function(){let e=this.documentClientHeight-52;console.log(this.$route.meta.isTab),this.$route.meta.isTab?(e-=70,this.siteContentViewHeight=e):(e-=30,this.siteContentViewHeight=e)},routeHandle:function(e){if(this.resetDocumentClientHeight(),this.loadSiteContentViewHeight(),e.meta.isTab){var t=this.mainTabs.filter((t=>t.name===e.name))[0];if(!t){if(e.meta.isDynamic&&!(e=this.dynamicMenuRoutes.filter((t=>t.name===e.name))[0]))return console.error("未能找到可用标签页!");t={menuId:e.meta.menuId||e.name,name:e.name,title:e.meta.title,iframeUrl:e.meta.iframeUrl||"",params:e.params,query:e.query},this.mainTabs=this.mainTabs.concat(t)}this.menuActiveName=t.menuId+"",this.mainTabsActiveName=t.name}},mounted:function(){this.resetDocumentClientHeight(),this.loadSiteContentViewHeight()}}},H={class:"menuLeft"},N={class:"menu-nav-header"},S={slot:""},D={slot:""},$={slot:""},O={slot:""},A={slot:""},E={class:"content-main"},z={class:"navTop horizontalView"},R={class:"nav_tools horizontalView"},q={key:1};x.render=function(t,l,b,g,v,_){const w=e("SvgIcon"),V=e("el-menu-item"),y=e("el-sub-menu"),C=e("el-menu"),T=e("el-breadcrumb-item"),L=e("el-breadcrumb"),I=e("router-view"),k=e("el-card"),U=e("el-tab-pane"),x=e("el-scrollbar"),j=e("el-tabs");return i(),o("div",{class:u(["content",t.isCollapse?"menu--fold":"menu--unfold"])},[n("div",H,[n("div",N,[n("span",null,s(t.isCollapse?t.littleTitle:t.longTitle),1)]),d(C,{"active-text-color":"#fff","background-color":"#263238",class:"el-menu-vertical-demo","collapse-transition":!1,"text-color":"#96a4ab ",onOpen:t.handleOpen,onClose:t.handleClose,collapse:t.isCollapse},{default:r((()=>[(i(!0),o(m,null,c(t.menus,((e,l)=>(i(),o(m,null,[e.submenu?(i(),a(y,{key:1,index:l},{title:r((()=>[d(w,{name:e.iconName,class:"icon-svg"},null,8,["name"]),n("span",D,"  "+s(e.name),1)])),default:r((()=>[(i(!0),o(m,null,c(e.submenu,((e,u)=>(i(),o(m,null,[e.submenu?(i(),a(y,{key:1,index:l+"-"+u},{title:r((()=>[d(w,{name:e.iconName,class:"icon-svg"},null,8,["name"]),n("span",O,"  "+s(e.name),1)])),default:r((()=>[(i(!0),o(m,null,c(e.submenu,((e,o)=>(i(),a(V,{index:l,disabled:e.disabled,onClick:l=>t.$router.push({name:e.routerName})},{default:r((()=>[d(w,{name:e.iconName,class:"icon-svg"},null,8,["name"]),n("span",A,"  "+s(e.name),1)])),_:2},1032,["index","disabled","onClick"])))),256))])),_:2},1032,["index"])):(i(),a(V,{key:0,index:l+"-"+u,disabled:e.disabled,onClick:l=>t.$router.push({name:e.routerName})},{default:r((()=>[d(w,{name:e.iconName,class:"icon-svg"},null,8,["name"]),n("span",$,"  "+s(e.name),1)])),_:2},1032,["index","disabled","onClick"]))],64)))),256))])),_:2},1032,["index"])):(i(),a(V,{key:0,index:l,onClick:l=>t.$router.push({name:e.routerName}),disabled:e.disabled},{default:r((()=>[d(w,{name:e.iconName,class:"icon-svg"},null,8,["name"]),n("span",S,"  "+s(e.name),1)])),_:2},1032,["index","onClick","disabled"]))],64)))),256))])),_:1},8,["onOpen","onClose","collapse"])]),n("div",E,[n("div",z,[n("div",R,[d(w,{name:t.isCollapse?"expand":"fold",class:"icon-svg",onClick:l[0]||(l[0]=e=>t.isCollapse=!t.isCollapse)},null,8,["name"])]),d(L,{separator:"/"},{default:r((()=>[(i(!0),o(m,null,c(t.breadcrumbList,(e=>(i(),a(T,null,{default:r((()=>[h(s(e),1)])),_:2},1024)))),256))])),_:1})]),t.$route.meta.isTab?(i(),a(j,{key:0,modelValue:t.mainTabsActiveName,"onUpdate:modelValue":l[1]||(l[1]=e=>t.mainTabsActiveName=e),closable:!0,onTabClick:_.selectedTabHandle,onTabRemove:_.removeTabHandle},{default:r((()=>[d(x,{ref:"scroll",height:t.siteContentViewHeight+32+"px",onScroll:t.scroll},{default:r((()=>[(i(!0),o(m,null,c(t.mainTabs,(e=>(i(),a(U,{label:e.title,name:e.name},{default:r((()=>[d(k,{style:p("min-height:"+t.siteContentViewHeight+"px")},{default:r((()=>[e.name===t.mainTabsActiveName?(i(),a(I,{key:0})):f("",!0)])),_:2},1032,["style"])])),_:2},1032,["label","name"])))),256))])),_:1},8,["height","onScroll"])])),_:1},8,["modelValue","onTabClick","onTabRemove"])):(i(),o("div",q,[d(x,{ref:"scroll",height:t.siteContentViewHeight+32+"px",onScroll:t.scroll},{default:r((()=>[d(k,{style:p("min-height:"+t.siteContentViewHeight+"px")},{default:r((()=>[d(I)])),_:1},8,["style"])])),_:1},8,["height","onScroll"])]))])],2)};const j="http://localhost:8888";function B(){return b({url:j+"/user/getUserList",method:"get"})}const P={data:function(){return{liveList:[],userList:[],dialogVisible:!1,liveInfo:{}}},methods:{getLiveList:function(){this.liveList=[],b({url:j+"/live/getLiveList",method:"get"}).then((e=>{let t=e.data;1==t.code&&(this.liveList=t.data)}))},getUserList:function(){B().then((e=>{let t=e.data;1==t.code&&(this.userList=t.data)}))},closeDialog:function(){this.dialogVisible=!1,this.getLiveList(),this.liveInfo={}},handleDelete:function(e){g.confirm("是否要进行删除？").then((()=>{var t;(t={id:e},b({url:j+"/live/deleteById",method:"get",params:t})).then((e=>{this.getLiveList()}))}))},handleEdit:function(e){e&&(this.liveInfo=e),this.dialogVisible=!0},submit(){var e;this.liveInfo.id?(e=this.liveInfo,b({url:j+"/live/updateById",method:"post",data:e})).then((()=>{this.closeDialog()})):function(e){return b({url:j+"/live/insert",method:"post",data:e})}(this.liveInfo).then((()=>{this.closeDialog()}))}},created(){this.getLiveList(),this.getUserList()}},M=h("创建/更新"),F=h("取消"),K=h("新增直播间"),Q=h("刷新"),G=h("编辑"),J=h("删除");P.render=function(t,l,n,u,p,f){const b=e("el-input"),g=e("el-form-item"),v=e("el-switch"),_=e("el-option"),w=e("el-select"),V=e("el-button"),y=e("el-form"),C=e("el-dialog"),T=e("el-table-column"),L=e("el-table");return i(),o(m,null,[d(C,{modelValue:t.dialogVisible,"onUpdate:modelValue":l[12]||(l[12]=e=>t.dialogVisible=e),title:t.liveInfo.roomId?t.liveInfo.roomId+"直播间":"新增直播间",width:"30%"},{default:r((()=>[d(y,{ref:"form",model:t.liveInfo,"label-width":"80px"},{default:r((()=>[d(g,{label:"直播间"},{default:r((()=>[d(b,{modelValue:t.liveInfo.roomId,"onUpdate:modelValue":l[0]||(l[0]=e=>t.liveInfo.roomId=e)},null,8,["modelValue"])])),_:1}),d(g,{label:"主播名"},{default:r((()=>[d(b,{modelValue:t.liveInfo.name,"onUpdate:modelValue":l[1]||(l[1]=e=>t.liveInfo.name=e)},null,8,["modelValue"])])),_:1}),d(g,{label:"标题"},{default:r((()=>[d(b,{modelValue:t.liveInfo.title,"onUpdate:modelValue":l[2]||(l[2]=e=>t.liveInfo.title=e)},null,8,["modelValue"])])),_:1}),d(g,{label:"封面"},{default:r((()=>[d(b,{modelValue:t.liveInfo.cover,"onUpdate:modelValue":l[3]||(l[3]=e=>t.liveInfo.cover=e)},null,8,["modelValue"])])),_:1}),d(g,{label:"标签"},{default:r((()=>[d(b,{modelValue:t.liveInfo.tags,"onUpdate:modelValue":l[4]||(l[4]=e=>t.liveInfo.tags=e)},null,8,["modelValue"])])),_:1}),d(g,{label:"分区"},{default:r((()=>[d(b,{modelValue:t.liveInfo.tid,"onUpdate:modelValue":l[5]||(l[5]=e=>t.liveInfo.tid=e)},null,8,["modelValue"])])),_:1}),d(g,{label:"标题模板"},{default:r((()=>[d(b,{modelValue:t.liveInfo.titleTemplate,"onUpdate:modelValue":l[6]||(l[6]=e=>t.liveInfo.titleTemplate=e)},null,8,["modelValue"])])),_:1}),d(g,{label:"简介"},{default:r((()=>[d(b,{type:"textarea",rows:4,placeholder:"请输入内容",modelValue:t.liveInfo.desc,"onUpdate:modelValue":l[7]||(l[7]=e=>t.liveInfo.desc=e)},null,8,["modelValue"])])),_:1}),d(g,{label:"分P模板"},{default:r((()=>[d(b,{modelValue:t.liveInfo.partTitleTemplate,"onUpdate:modelValue":l[8]||(l[8]=e=>t.liveInfo.partTitleTemplate=e)},null,8,["modelValue"])])),_:1}),d(g,{label:"是否自制",prop:"copyright"},{default:r((()=>[d(v,{modelValue:t.liveInfo.copyright,"onUpdate:modelValue":l[9]||(l[9]=e=>t.liveInfo.copyright=e)},null,8,["modelValue"])])),_:1}),d(g,{label:"是否上传",prop:"upload"},{default:r((()=>[d(v,{modelValue:t.liveInfo.upload,"onUpdate:modelValue":l[10]||(l[10]=e=>t.liveInfo.upload=e)},null,8,["modelValue"])])),_:1}),d(g,{label:"上传用户",prop:"userId"},{default:r((()=>[d(w,{modelValue:t.liveInfo.userId,"onUpdate:modelValue":l[11]||(l[11]=e=>t.liveInfo.userId=e),placeholder:"请选择活动区域"},{default:r((()=>[(i(!0),o(m,null,c(t.userList,((e,t)=>(i(),a(_,{key:t,value:e.dedeUserId},{default:r((()=>[h(s(e.dedeUserId),1)])),_:2},1032,["value"])))),128))])),_:1},8,["modelValue"])])),_:1}),d(g,null,{default:r((()=>[d(V,{type:"primary",onClick:f.submit},{default:r((()=>[M])),_:1},8,["onClick"]),d(V,{onClick:f.closeDialog},{default:r((()=>[F])),_:1},8,["onClick"])])),_:1})])),_:1},8,["model"])])),_:1},8,["modelValue","title"]),d(V,{type:"primary",onClick:l[13]||(l[13]=e=>f.handleEdit())},{default:r((()=>[K])),_:1}),d(V,{type:"primary",onClick:f.getLiveList},{default:r((()=>[Q])),_:1},8,["onClick"]),d(L,{data:t.liveList,style:{width:"100%"}},{default:r((()=>[d(T,{prop:"name",label:"主播",width:"100"}),d(T,{prop:"roomId",label:"直播间号",width:"100"}),d(T,{prop:"title",label:"直播标题",width:"200"}),d(T,{prop:"tags",label:"标签",width:"200"}),d(T,{prop:"titleTemplate",label:"直播标题模板",width:"200"}),d(T,{prop:"partTitleTemplate",label:"分P标题模板",width:"200"}),d(T,{prop:"desc",label:"简介",width:"200"}),d(T,{prop:"tid",label:"分区",width:"100"}),d(T,{label:"上传",width:"100"},{default:r((e=>[h(s(e.row.upload?"是":"否"),1)])),_:1}),d(T,{label:"自制",width:"100"},{default:r((e=>[h(s(e.row.copyright?"是":"否"),1)])),_:1}),d(T,{label:"操作",width:"200"},{default:r((e=>[d(V,{onClick:t=>f.handleEdit(e.row)},{default:r((()=>[G])),_:2},1032,["onClick"]),d(V,{type:"danger",onClick:t=>f.handleDelete(e.row.id)},{default:r((()=>[J])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"])],64)};const W={data:function(){return{recordeList:[],videoList:[],page:1,pageSize:10,total:0,dialogVisible:!1}},methods:{initRecordeList:function(){this.page=1,this.recordeList=[],this.getRecordeList()},getRecordeList:function(){(function(e){return b({url:j+"/recorde/getRecordeList",method:"get",params:e})})({page:this.page-1,pageSize:this.pageSize}).then((e=>{let t=e.data;1==t.code&&(t=t.data,this.page=t.currentPage+1,this.pageSize=t.pageSize,this.total=t.totalCount,t.data.forEach((e=>{e.live_start_time&&(e.live_start_time=new Date(e.live_start_time).toLocaleString()),e.live_stop_time?e.live_stop_time=new Date(e.live_stop_time).toLocaleString():e.live_stop_time="未结束",e.success?e.success="已发布":e.success="未发布",this.recordeList.push(e)})))}))},getVideoList:function(e){let t={id:e};this.videoList=[],function(e){return b({url:j+"/video/getVideoList",method:"get",params:e})}(t).then((e=>{let t=e.data;1==t.code&&(t.data.forEach((e=>{e.fileOpenTime&&(e.fileOpenTime=new Date(e.fileOpenTime).toLocaleString()),e.fileCloseTime&&(e.fileCloseTime=new Date(e.fileCloseTime).toLocaleString()),e.success&&0!=e.success?1==e.success?e.success="已上传":-1==e.success&&(e.success="出错"):e.success="未上传",this.videoList.push(e)})),this.dialogVisible=!0)}))}},created(){this.initRecordeList()}},X={slot:"footer",class:"dialog-footer"},Y={class:"dialog-footer"},Z=h("关 闭"),ee=h("刷新"),te=h("查看文件历史");W.render=function(t,l,a,s,u,c){const p=e("el-table-column"),h=e("el-table"),f=e("el-button"),b=e("el-dialog"),g=e("el-pagination");return i(),o(m,null,[d(b,{title:"提示",modelValue:t.dialogVisible,"onUpdate:modelValue":l[1]||(l[1]=e=>t.dialogVisible=e),width:"80%"},{footer:r((()=>[n("span",Y,[d(f,{onClick:l[0]||(l[0]=e=>t.dialogVisible=!1)},{default:r((()=>[Z])),_:1})])])),default:r((()=>[n("span",X,[d(h,{data:t.videoList},{default:r((()=>[d(p,{prop:"id",label:"id",width:"100"}),d(p,{prop:"roomId",label:"房间号",width:"100"}),d(p,{prop:"pIndex",label:"分P",width:"100"}),d(p,{prop:"path",label:"文件路径"}),d(p,{prop:"fileOpenTime",label:"文件创建时间",width:"200"}),d(p,{prop:"fileCloseTime",label:"文件关闭时间",width:"200"})])),_:1},8,["data"])])])),_:1},8,["modelValue"]),d(f,{type:"primary",onClick:c.initRecordeList},{default:r((()=>[ee])),_:1},8,["onClick"]),d(h,{data:t.recordeList},{default:r((()=>[d(p,{prop:"id",label:"id",width:"100"}),d(p,{prop:"room_id",label:"房间号",width:"150"}),d(p,{prop:"title",label:"标题",width:"300"}),d(p,{prop:"success",label:"是否发布",width:"100"}),d(p,{prop:"live_start_time",label:"录制开始时间",width:"200"}),d(p,{prop:"live_stop_time",label:"录制结束时间",width:"200"}),d(p,{label:"操作",width:"200"},{default:r((e=>[d(f,{type:"primary",onClick:t=>c.getVideoList(e.row.id)},{default:r((()=>[te])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"]),d(g,{layout:"prev, pager, next",total:t.total,"page-size":t.pageSize,"current-page":t.page},null,8,["total","page-size","current-page"])],64)};const le={data:function(){return{userList:[],dialogVisible:!1}},methods:{getImg:function(){return j+"/user/getQRCode?"+(new Date).getTime()},getUserList:function(){B().then((e=>{let t=e.data;1==t.code&&(this.userList=t.data)}))},handleDelete:function(e){g.confirm("是否要进行删除？").then((()=>{var t;(t={id:e},b({url:j+"/user/deleteById",method:"get",params:t})).then((e=>{this.getUserList()}))}))}},created(){this.getUserList()}},ie=["src"],ae={class:"dialog-footer"},oe=h("关 闭"),ne=h("新增用户"),se=h("刷新"),de=h("删除");le.render=function(t,l,a,s,u,c){const p=e("el-button"),h=e("el-dialog"),f=e("el-table-column"),b=e("el-table");return i(),o(m,null,[d(h,{modelValue:t.dialogVisible,"onUpdate:modelValue":l[1]||(l[1]=e=>t.dialogVisible=e),title:"请扫描二维码",width:"30%"},{footer:r((()=>[n("span",ae,[d(p,{onClick:l[0]||(l[0]=e=>t.dialogVisible=!1)},{default:r((()=>[oe])),_:1})])])),default:r((()=>[n("span",null,[n("img",{src:c.getImg()},null,8,ie)])])),_:1},8,["modelValue"]),d(p,{type:"primary",onClick:l[2]||(l[2]=e=>t.dialogVisible=!0)},{default:r((()=>[ne])),_:1}),d(p,{type:"primary",onClick:c.getUserList},{default:r((()=>[se])),_:1},8,["onClick"]),d(b,{data:t.userList,style:{width:"100%"}},{default:r((()=>[d(f,{prop:"mid",label:"mid",width:"200"}),d(f,{prop:"accessToken",label:"accessToken",width:"300"}),d(f,{prop:"refreshToken",label:"refreshToken",width:"300"}),d(f,{label:"操作",width:"200"},{default:r((e=>[d(p,{type:"danger",onClick:t=>c.handleDelete(e.row.id)},{default:r((()=>[de])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"])],64)};const re=[{path:"/home",name:"home",component:x,children:[{path:"/",redirect:"live"},{path:"",redirect:"live"},{path:"/live",name:"live",component:P,meta:{title:"直播",isTab:!0}},{path:"/user",name:"user",component:le,meta:{title:"用户",isTab:!0}},{path:"/session",name:"session",component:W,meta:{title:"录制历史",isTab:!0}}]}],ue=v({history:_(),routes:re});if("undefined"!=typeof window){let e=function(){var e=document.body,t=document.getElementById("__svg__icons__dom__");t||((t=document.createElementNS("http://www.w3.org/2000/svg","svg")).style.position="absolute",t.style.width="0",t.style.height="0",t.id="__svg__icons__dom__",t.setAttribute("xmlns","http://www.w3.org/2000/svg"),t.setAttribute("xmlns:link","http://www.w3.org/1999/xlink")),t.innerHTML="",e.insertBefore(t,e.lastChild)};"loading"===document.readyState?document.addEventListener("DOMContentLoaded",e):e()}const me=w({name:"SvgIcon",props:{prefix:{type:String,default:"icon"},name:{type:String,required:!0}},setup:e=>({symbolId:V((()=>`#${e.prefix}-${e.name}`))})}),ce={"aria-hidden":"true"},pe=["xlink:href"];me.render=function(e,t,l,a,s,d){return i(),o("svg",ce,[n("use",{"xlink:href":e.symbolId},null,8,pe)])};const he=y(L);he.use(ue),he.use(C),he.use(T),he.component("SvgIcon",me),he.mount("#app");