<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>织图提示</title>
<style type="text/css">
	
	body {
		font-family:"微软雅黑",Arial, Helvetica, sans-serif;
		margin:0;
		padding:0;
		background: url("./notice/images/common_bg.png");
	}
	#wrapper {
		margin:100px auto 0 auto;
		width: 611px;
		height:447px;
		background: url("./notice/images/bg_notice.png");
	}
	
	#tip {
		display:inline-block;
		margin: 190px 0 0 280px;
		letter-spacing: 2px;
		text-align: left;
	}
	#tip .tip_title {
		display:inline-block;
		color: #b09c83;
		font-size: 28px;
		margin-bottom: 16px;
	}
	
	.tip_link,.tip_text {
		color: #8b8378;
		font-size: 18px;
	}
	.tip_link a {
		color: #8b8378;
	}
	
</style>
</head>
<body>
	<div id="wrapper">
		<div id="tip">
			<span class="tip_title">织图搬家啦！</span><br />
			<span class="tip_text">点击链接，进入新织图</span><br />
			<span class="tip_link">
				<a href="http://www.imzhitu.com" title="点击链接，进入新织图">www.imzhitu.com</a>
			</span>
		</div>
	</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>