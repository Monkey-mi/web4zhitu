<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<meta name="description" content="相册,美图,社交,旅行,故事,时尚,织图,下载,织图下载">
<meta name="keywords" content="相册,美图,社交,旅行,故事,时尚,织图,下载,织图下载">
<meta name="viewport" />
<jsp:include page="../base/downhead.jsp?var=${webVar}"></jsp:include>
<title>织图下载</title>
<script type="text/javascript">
var _hmt = _hmt || [];

function download(){
		window.location.href=downloadUrlAndriod;
}
</script>
</head>
<body style="margin:0;padding:0;">
	<div id="main_box" style="width:100%;">
		<div id="head_banner" style="width:100%;height:20px;background-color:#373c3e;font-size:14px;text-align:center;color:#ffffff;">如果不能下载，请点击右上角"在浏览器打开"下载</div>
		<div id="head_content" style="width:100%;height:384px;overflow: hidden;">
				<img src="http://imzhitu.qiniudn.com/images/d4a_head_bg.jpg" style="width:100%;z-index:-9999;position:absolute;margin:auto;"/>
			<div id="head_logo" style="width:183px;height:88px;margin:0 auto;padding-top:42px;padding-bottom:132px;">
				<img alt="" src="${webRootPath }/index/images/d4a_logo.png" style="width:183px;height:88px;"/>
			</div>
			<div style="margin: 0 auto;width:190px;height:44px;">
				<a href="https://itunes.apple.com/cn/app/zhi-tu/id576156900?mt=8" target="_blank" title="点击下载织图IPhone版">
					<img src="${webRootPath }/index/images/d4a_btn_ios_up.png" style="width:190px;height:44px;"/>
				</a>
			</div>
			<div style="margin:0 auto;width:190px;height:44px;padding-top:10px;">
				<a href="javascript:void(0);" >
					<img src="${webRootPath }/index/images/d4a_btn_andrio_up.png" style="width:190px;height:44px;" onclick="download()"/>
				</a>
			</div>
		</div>
		<div id="content" style="width:100%;height:795px;background-color:#f6f5ea;">
			<div style="margin:0 auto;padding-top:30px;width:284px;height:15px;"><img src="${webRootPath }/index/images/d4a_desc_1.png" style="width:284px;height:15px;"/></div>
			<div style="margin:0 auto;padding-top:20px;width:162px;height:284px;"><img src="http://imzhitu.qiniudn.com/images/d4a_desc_bg_1.jpg?ver=${webVer}" style="width:162px;height:284px;"/></div>
			<div style="margin:0 auto;padding-top:30px;width:228px;height:37px;"><img src="${webRootPath }/index/images/d4a_desc_3.png" style="width:228px;height:37px;"/></div>
			<div style="margin:0 auto;padding-top:20px;width:160px;height:284px;"><img src="http://imzhitu.qiniudn.com/images/d4a_desc_bg_2.jpg?ver=${webVer}" style="width:160px;height:284px;"/></div>
			
			<div id="btn_download" style="width:300px;height:64px;margin:0 auto; padding-top:30px;">
				<a href="http://www.imzhitu.com/APPtfG9pSw" target="_blank" title="点击下载织图IPhone版">
					<img src="${webRootPath }/index/images/d4a_btn_ios_down.png" style="float:left;width:145px;height:44px;"/>
				</a>
				<a href="javascript:void(0);" >
					<img src="${webRootPath }/index/images/d4a_btn_andrio_down.png" style="margin-left:10px;width:145px;height:44px;" onclick="download()"/>	
				</a>			
			</div>			
		</div>
	
	</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>