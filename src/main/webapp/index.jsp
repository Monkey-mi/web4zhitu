<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content="织图，一个有趣的秀美图神器。还原美食图片、浏览美丽时尚的明星搭配、秘密约会交陌友、分享海量随手自拍至微信新浪，是达人在路上、去哪儿都玩的创意相机。">
<meta name="keywords" content="相册,美图,社交,旅行,故事,时尚">
<jsp:include page="./base/downhead.jsp?var=${webVar}"></jsp:include>
<title>织图，全球首个场景式图片社交应用</title>
<link rel="stylesheet" type="text/css" href="./index/css/index4.css?ver=${webVer}" />
<script>
var _hmt = _hmt || [];
function download(){
	_hmt.push(['_trackEvent', 'app', 'download', 'android']);
	window.location.href=downloadUrlAndriod;
}
</script>
</head>
<body>
<div id="main-box">
  	<div id="title">
	  	<div id="title-bg"></div>
	  	<div id="title-content">
	  		<a href="${webRootPath }/" title="织图首页" id="icon-logo">
	  			<img src="./index/images/icon-index.png" height=41 />
	  		</a>
	     	<ul class="title-text">
	      	<li><a href="${webRootPath }/" title="织图首页" class="highlight">首页</a></li>
	       	<li><span class="divider">|</span></li>
	       	<li><a href="zhitu_about" title="关于织图">关于织图</a></li>
	       	<li><span class="divider">|</span></li>
	       	<li><a href="zhitu_joinus" title="加入我们">加入我们</a></li>
	      	</ul>
	   	</div>
   	</div>
	<div id="main">
		<div id="main-left">
	       	<img src="./index/images/intro2015021201.png" />
	   	</div>
	   	 <div id="main-right">
	   	 	<div id="slogan">
				<img alt="" src="./index/images/slogan2015021201.png">			
			</div>
			<img id="qrdownload" alt="" src="./index/images/qrdownload2015021201.png">
		</div>
	</div>
</div>

<div id="superb-box">
<p id="superb-title">今日精选</p>
</div>
<div id="copyright-box">
	<div class="copyright-info">
		<a href="zhitu_about">关于织图</a>&nbsp;|&nbsp;<a href="zhitu_agreement">用户协议</a>&nbsp;|&nbsp;<a href="zhitu_joinus">招聘信息</a>&nbsp;|&nbsp;<a href="zhitu_contact">联系我们</a>
	</div>
   	<div class="copyright-div">
       <span class="copyright">服务条款</span>
       <span class="copyright">|&nbsp;粤ICP备12087844号-1</span> <span class="copyright">©2013-2015 imzhitu.com.All Rights Reserved</span>
   </div>	
</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.8.2.min.js?ver=${webVer}"></script>
<link rel="stylesheet" type="text/css" href="${webRootPath }/htworld/webv2/css/htszoomtourV2.css?ver=${webVer}" />
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js?ver=${webVer}"></script>
<script type="text/javascript" src="${webRootPath }/htworld/common/js/jquery.htszoomtourV3.js?ver=${webVer}"></script>
<script type="text/javascript" src="${webRootPath }/index/js/index4.js?ver=${webVer}"></script>
</html>
