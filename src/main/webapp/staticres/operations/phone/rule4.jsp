<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta id="viewport" name="viewport"/>
<title>达人规则</title>
<style type="text/css">
	body {
		margin: 0 auto;
		color: #757574;
		font-size: 16px;
		text-align: left;
	}
	#main-box {
		margin: 0 auto;
		text-align: left;
		width: 230px;
		display: inline-block;
	}
	
	ul {
		margin: 0;
		list-style: decimal;
		padding-left: 20px;
		font-size: 14px;
	}
	
	li {
		margin-bottom: 8px;
	}
	
	.title {
		color: #69b1db;
		margin: 10px 0 10px 0;
	}
	
	
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
</script>
</head>
<body>
	<div id="main-box">
		<p class="title">成为达人后：</p>
		<ul>
			<li>尽量不要发广告；</li>
			<li>30天之内没有发织图将会被取消达人资格；</li>
			<li>达人有义务引领织图社区氛围；</li>
			<li>发图越多，排名越靠前，越容易获得粉丝。</li>
		</ul>
	</div>
</body>
</html>