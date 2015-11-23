/*
 * 底部下载条工具方法,页面加载的同时在body插入下载条
 * 
 * @author lynch 2015-11-23
 */

var downbanner = {
	
	/* 隐藏cookie key */
	hideCookie : 'hidedownbanner',

	/* 添加下载条 */
	appendBanner : function() {
		var downURL = "http://a.app.qq.com/o/simple.jsp?pkgname=com.htshuo.htsg";
		var indexURL = "http://imzhitu.com/index4ph.html";
		var body = document.body;
		var downWrap = document.createElement('div');
		
		downWrap.setAttribute("style", "width:100%;height:52px;margin:0 auto;bottom: 0;text-align:center;overflow:hidden;font-family: Microsoft YaHei;padding-bottom:2px;position:fixed; z-index: 9999;background: rgba(255,255,255,0.9);");
		downWrap.innerHTML =
			 '<a href="'+indexURL+'">'
			+'<img style="width: auto;height: 45px;margin-top: 5px;margin-left: 7px;display: inline;float: left;" alt="" src="/staticres/home/images/logo.png">'
			+'</a>'
			+'<div style="display:inline-block;vertical-align: top;padding-left: 8px;padding-top: 11px;float: left;font-size: 0;line-height: 11px;">'
			+'	<div style="display: block;font-size: 14px;color: #373e46;text-align: left;height: 15px;line-height: 15px;">织图</div>'
			+'	<div style="display: block;font-size: 12px;color: #959da5;vertical-align:top;height: 12px;margin-top: 4px;">年轻人的生活方式</div>'
			+'</div>'
			+'<a href="'+downURL+'" style="background: #3a98d0;width: 96px;height: 26px;color: #ffffff;-moz-border-radius: 4px;-webkit-border-radius: 4px;border: 2px solid #3083b5;text-align: center;font-size: 13px;display: inline-block;vertical-align: top;cursor: pointer;line-height: 30px;float: right;margin: 12px 0 0 0;position:absolute;right:30px;">点击下载</a>'
			+'<span onclick="javascript:downbanner.hideBanner(this);" style="display: inline-block;cursor: pointer;vertical-align: center;margin:22px 10px 0 0;float: right;">'
			+'<img title="隐藏"  alt="" src="/staticres/home/images/x@2x.png" style="width: 10px;height: 10px;">'
			+'</span>';
		
		body.appendChild(downWrap);
		body.style.marginBottom = downWrap.clientHeight;
	},
	
	/* 隐藏banner */
	hideBanner : function(btn) {
		var downWrap = btn.parentNode;
		downWrap.style.display = "none";
		downWrap.parentNode.style.marginBottom = 0;
		downbanner.setCookie(downbanner.hideCookie);
	},

	/* 检测banner是否设置为隐藏 */
	isHideBanner : function() {
		return downbanner.getCookie(downbanner.hideCookie) != null ? true : false;
	},
	
	/* 设置cookie,默认过期时间为1天 */
	setCookie : function(name,value) {
	    var exp = new Date(); 
	    exp.setTime(exp.getTime() + 1*24*60*60*1000); 
	    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
	},
	
	/* 获取cookie */
	getCookie : function(name) {
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	    if(arr=document.cookie.match(reg))
	        return unescape(arr[2]); 
	    else 
	        return null; 
	},
	
	/* 检测是否可用cookie */
	checkCookieEnable : function() {
		return navigator.cookieEnabled;
	},
	
};

(function(){
	downbanner.appendBanner();
//	if(downbanner.checkCookieEnable && !downbanner.isHideBanner()) {
//		downbanner.appendBanner();
//	}
})();