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
<link rel="stylesheet" type="text/css" href="./index/css/index2.css?ver=${webVer}" />
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
 	<img id="bg" src="./index/images/bg.jpg">
  	<div id="title">
	  	<div id="title-bg"></div>
	  	<div id="title-content">
	  		<a href="${webRootPath }/" title="织图首页" id="icon-logo" class="icon"></a>
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
	    	<div id="text-slogan">连接图片里的一切</div>
	       	<div id="qr-download">
	       		<img title="扫一扫，下载织图，记录你的生活！" src="http://qr.liantu.com/api.php?el=l&w=200&m=10&text=http://a.app.qq.com/o/simple.jsp?pkgname=com.htshuo.htsg&g_f=991653">
	       	</div>
	       	<div id="qr-tip">扫一扫，马上下载</div>
	       	<a class="btn-download" title="点击下载织图ios版" 
	       		onclick="_hmt.push(['_trackEvent', 'app', 'download', 'ios'])"
	       		href="https://itunes.apple.com/cn/app/zhi-tu/id576156900?mt=8">ios版下载</a>
	       	<a class="btn-download" title="点击下载织图android版" 
	       		href="javascript:download()">android版下载</a>
	       	
	       	<div id="concern-box">
	       		<span id="text-concern">关注织图：</span>
	       		<a class="icon-concern icon" target="_blank" href="http://weibo.com/htshuo?topnav=1&wvr=5&topsug=1" title="关注织图新浪官方微博" id="icon-sina"></a>
	       		<span class="divider">|</span>
	       		<a class="icon-concern icon" href="${webRootPath }/index/concern_wechat.html" title="关注织图微信公众账号" id="icon-wechat"></a>
	       	</div>
	    </div>
	    <div id="main-right">
	       	<div class="zt-container">
				<div class="zt-title">
					<img alt="">
				</div>
				<div class="zt-desc-hide" title="隐藏描述"></div>
				<div class="zt-desc-show" title="显示描述"></div>
				<div class="zt-desc">
					<div class="zt-desc-text"></div>
					<div class="zt-desc-bg"></div>
				</div>
			<div class="zt-loading">
				<div class="zt-loading-bg"></div>
				<div class="zt-loading-icon"></div>
			<div><span>加载中...</span><span id="zt-progress">0</span><span>%</span></div>
			</div>
			</div>
			
			<div id="carousel-box">
				<div class="buttons prev">
					<div class="icon-buttons icon-prev"></div>
				</div>
				<div class="viewport">
					<ul class="overview">
					</ul>
				</div>
				<div class="buttons next">
					<div class="icon-buttons icon-next"></div>
				</div>
			</div>
			
		</div>
	</div>
</div>
<div id="func-box">
   	<div class="func-odd">
   		<img alt="" src="./index/images/pic-func1.png">
   		<img alt="" src="./index/images/pic-func1-text.png">
   	</div>
   	<div>
   		<img alt="" src="./index/images/pic-func2-text.png">
   		<img alt="" src="./index/images/pic-func2.png">
   	</div>
   	<div class="func-odd">
   		<img alt="" src="./index/images/pic-func3.png">
   		<img alt="" src="./index/images/pic-func3-text.png">
   	</div>
   	<div>
   		<img alt="" src="./index/images/pic-func4-text.png">
   		<img alt="" src="./index/images/pic-func4.png">
   	</div>
   	<div class="func-odd">
   		<img alt="" src="./index/images/pic-func5.png">
   		<img alt="" src="./index/images/pic-func5-text.png">
   	</div>
</div>
<div id="copyright-box">
	<div class="copyright-info">
		<a href="zhitu_about">关于织图</a>&nbsp;|&nbsp;<a href="zhitu_agreement">用户协议</a>&nbsp;|&nbsp;<a href="zhitu_joinus">招聘信息</a>&nbsp;|&nbsp;<a href="zhitu_contact">联系我们</a>
	</div>
   	<div class="copyright-div">
       <span class="copyright">服务条款</span>
       <span class="copyright">|&nbsp;粤ICP备12087844号-1</span> <span class="copyright">©2013-2014 imzhitu.com.All Rights Reserved</span>
   </div>	
</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.8.2.min.js?ver=${webVer}"></script>
<link rel="stylesheet" type="text/css" href="${webRootPath }/htworld/webv2/css/htszoomtourV2.css?ver=${webVer}" />

<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js?ver=${webVer}"></script>
<script type="text/javascript" src="${webRootPath }/htworld/common/js/jquery.htszoomtourV2.js?ver=${webVer}"></script>
<link rel="stylesheet" type="text/css" href="./base/js/jquery/fancybox/jquery.fancybox-1.3.4.css?ver=${webVer}" />
<script type="text/javascript" src="./base/js/jquery/fancybox/jquery.fancybox-1.3.4.js?ver=${webVer}"></script>
<link rel="stylesheet" type="text/css" href="${webRootPath }/base/js/jquery/tinycarousel/tinycarousel.css?ver=${webVer}" />
<script type="text/javascript" src="${webRootPath }/base/js/jquery/tinycarousel/jquery.tinycarousel.min.js?ver=${webVer}"></script>
<script type="text/javascript" src="${webRootPath }/index/js/index2.js?ver=${webVer}"></script>
</html>