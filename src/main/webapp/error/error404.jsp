<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta id="viewport" name="viewport">
<title>提示:找不到指定路径</title>
<link rel="stylesheet" type="text/css" href="${webRootPath }/error/css/style.css" />
<script>
var userAgent = navigator.userAgent.toLowerCase(),
	viewport = document.getElementById("viewport");
if(userAgent .match(/uc/i) != "uc") {
	var dpr = window.devicePixelRatio,
	dpi = dpr * 320;
	viewport.setAttribute('content', 'width=device-width,target-densitydpi='+dpi+',initial-scale=1.0, user-scalable=true');
} else {
	viewport.setAttribute('content', 'width=target-densitydpi=device-dpi,initial-scale=1.0, user-scalable=true');
}
var defaultWinWidth = 1366,
	defaultFontSize = 16;
function setFontSize() {
	var scale = parseFloat(window.innerWidth) / defaultWinWidth,
		fontSize = scale * defaultFontSize;
	document.getElementById("text").style.fontSize = fontSize + 'px';
}
window.onload = setFontSize;
window.onresize = setFontSize;
</script>
</head>
<body>
	<div id="wrapper">
		<img id="icon" src="${webRootPath }/error/images/bg-error.png"/>
		<img id="tip" src="${webRootPath }/error/images/error-tip.png" />
	</div>
	<div id="text">找不到您指定的路径：<a href="http://www.imzhitu.com">www.imzhitu.com</a></div>
</body>
</html>