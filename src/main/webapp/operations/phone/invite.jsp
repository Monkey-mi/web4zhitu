<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,target-densitydpi=device-dpi, initial-scale=1.0, user-scalable=no"/>
<title>织图</title>
<style type="text/css">
	body {
		margin: 0 auto;
		background: #59bad2;
		text-align: center;
	}
	#main_box {
		margin: 0 auto;
		width: 100%;
		height: auto;
		text-align: center;
	}
	#content {
		display: inline-block;
		width: 80%;
		height:auto;
	}
	#banner {
		display: inline-block;
		margin-top:10%;
		width: 100%;
		height:auto;
	}
	#btn_download {
		display: inline-block;
		margin-top: 7%;
		width: 100%;
		height: auto;
	}
	#pic_intro {
		display: inline-block;
		width:80%;
		height: auto;
		position: absolute;
		left:10%;
		bottom: 0px;
		
	}
</style>
</head>
<body>
<div id="main_box">
	<div id="content">
		<img id="banner" alt="" src="${webRootPath}/operations/phone/images/banner3.png" />
		<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.htshuo.htsg&g_f=991653"><img id="btn_download" src="${webRootPath}/operations/phone/images/download_normal2.png"/></a>
		<img id="pic_intro" alt="" src="${webRootPath}/operations/phone/images/iphone_android2.png" />
	</div>
</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>