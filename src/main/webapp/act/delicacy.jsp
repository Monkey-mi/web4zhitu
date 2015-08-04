<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="相册,美图,社交,旅行,故事,时尚,织图,下载,织图下载">
<meta name="keywords" content="相册,美图,社交,旅行,故事,时尚,织图,下载,织图下载">
<meta name="viewport" id="viewport"/>
<jsp:include page="../base/downhead.jsp?var=${webVar}"></jsp:include>
<title>织图下载</title>
<style type="text/css">

</style>
<script type="text/javascript">
var userAgent = navigator.userAgent.toLowerCase();
if(userAgent.match(/uc/i) != "uc") {
	var dpr = window.devicePixelRatio,
		dpi = dpr * 320;
	document.getElementById('viewport').setAttribute('content', 'width=device-width,target-densitydpi='+dpi+',initial-scale=1.0, user-scalable=true');
} else {
	document.getElementById('viewport').setAttribute('content', 'width=target-densitydpi=device-dpi,initial-scale=1.0, user-scalable=true');
}
var _hmt = _hmt || [];

function download(){
	if(navigator.userAgent.indexOf("iPhone")==-1){
		window.location.href=downloadUrlAndriod;
	}else{
		window.location.href="http://um0.cn/5HgX8j/";
	}
}
</script>
</head>
<body style="margin:0;padding:0;background-color:RGB(253,103,149);">
	<div id="main" style="width:100%;height:100%;">
		<div id="head-banner" style="padding-top:26px;margin: 0px auto;height:58px;width:274px;">
			<img src="http://static.imzhitu.com/op%2Factivity%2Fweb%2Fweibo_post_title.png?${webVer}" style="height:58px;width:274px;"/>
		</div>
		<div id="download-btn" style="margin:0 auto;width:170px;height:40px;margin-top:22px;z-index:30;">
			<a href="javascript:void(0);" >
				<img alt="" src="http://static.imzhitu.com/op%2Factivity%2Fweb%2Fweibo_free_download_btn.png?${webVer} " style="width:170px;height:40px;" onclick="download()">
			</a>
		</div>
		<div  style="position:absolute;z-index:-1;top:121px;width:100%;height:456px;">
			<div id="poster-pic" style="position:relative;margin:0 auto;width:320px;height:456px;">
				<img alt="" src="http://static.imzhitu.com/op%2Factivity%2Fweb%2Fweibo_post_img.png?${webVer}" style="width:320px;height:456px;">
			</div>
		</div>
		<div id="post-words" style="margin:0 auto;height:20px;width:150px;margin-top:360px;">
			<img src="http://static.imzhitu.com/op%2Factivity%2Fweb%2Fweibo_post_words.png?${webVer}" style="height:20px;width:150px;"/>
		</div>
		<div id="click_btn" style="margin: 22px auto 0 auto;height:64px;width:170px;">
			<a href="javascript:void(0);" >
				<img src="http://static.imzhitu.com/op%2Factivity%2Fweb%2Fkick_dowloan_btn.png?${webVer}" style="height:40px;width:170px;" onclick="download()"/>
			</a>
		</div>
	</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>