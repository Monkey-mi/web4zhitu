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
<title>织图，年轻人的生活方式</title>
<script>
(function(){
    var sUserAgent = navigator.userAgent.toLowerCase();
    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
    var bIsMidp = sUserAgent.match(/midp/i) == "midp";
    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
    var bIsAndroid = sUserAgent.match(/android/i) == "android";
    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
    if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM){
        window.location.href='./index4ph.html';
    }
})();
var _hmt = _hmt || [];
function download(){
	_hmt.push(['_trackEvent', 'app', 'download', 'android']);
	window.location.href=downloadUrlAndriod;
}
</script>
<link rel="stylesheet" type="text/css" href="./index/v5/css/style.css?ver=${webVer}" />
</head>
<body>
<div id="main-box-wrap">
	<div id="main-box">
		<div id="main-left">
			<div id="title">
			  	<ul class="title-text">
			      	<li><a href="${webRootPath }/" title="织图首页" class="highlight">首页</a></li>
			       	<li><span class="divider">|</span></li>
			       	<li><a href="zhitu_about" title="关于织图">关于织图</a></li>
			       	<li><span class="divider">|</span></li>
			       	<li><a href="zhitu_joinus" title="加入我们">加入我们</a></li>
		      	</ul>
		   	</div>
			<div id="slogan">
				<img alt="织图，年轻人的生活方式" src="./index/v5/images/slogan.png">			
			</div>
			<div id="download-btn-box">
				<a title="点击下载织图iPhone客户端" class="download-btn"
					href="https://itunes.apple.com/cn/app/zhi-tu/id576156900?mt=8">iPhone下载</a>
				<a title="点击下载织图Android客户端" class="download-btn" href="javascript:download();">Android下载</a>
			</div>
			<img id="download-code" src="./index/v5/images/download-qrcode.png"/>
	   	</div>
	   	 <div id="main-right">
	   	 	<img class="poster" id="poster-main" src="./index/v5/images/poster.png" />
	   	 	<img class="poster-anim anim-img" id="light1" alt="" src="./index/v5/images/light1.png" />
	   	 	<div id="aixin-s-wrap">
	   	 		<div id="blink-fore1"></div>
	   	 		<div id="blink-back1"></div>
	   	 		<img id="aixin-s" class="anim-img" width="41" src="./index/v5/images/aixin.png" />
	   	 	</div>
	   	 	<img class="poster-anim anim-img" id="aixin-l" alt="" src="./index/v5/images/aixin.png" />
	   	 	<div id="star-s-wrap" class="poster-anim">
	   	 		<div id="blink-fore2"></div>
	   	 		<div id="blink-back2"></div>
	   	 		<img id="star-s" class="anim-img" width="28" src="./index/v5/images/star.png" />
	   	 	</div>
	   	 	<img class="poster-anim anim-img" id="light2" src="./index/v5/images/light2.png" />
	   	 	<img class="poster-anim anim-img" id="star-l" alt="" src="./index/v5/images/star.png" />
	   	 	<img class="poster-anim anim-img" id="click-down-1" src="./index/v5/images/click-down.png" />
	   	 	<img class="poster-anim anim-img" id="click-up-1" src="./index/v5/images/click-up.png" />
	   	 	<img class="poster-anim anim-img" id="click-up-2" src="./index/v5/images/click-up.png" />
	   	 	<img class="poster-anim anim-img" id="click-down-2" src="./index/v5/images/click-down.png" />
		</div>
	</div>
</div>
<div id="intro-box">
	<div class="intro intro-odd" id="intro1">
		<div class="intro-wrap">
			<div id="world-demo-bg"></div>
			<div id="demo-desc">文艺青年，记录生活点滴。</div>
			<div id="load-btn-box">
				<a class="load-btn load-btn-normal load-highlight" title="文艺青年，记录生活点滴。" data-id="213930" href="javascript:void(0);" onclick="javascript:loadworld(0)">趣味生活</a>
				<a class="load-btn load-btn-normal" title="时尚达人，彰显个性品味。" data-id="214224" href="javascript:void(0);" onclick="javascript:loadworld(1)">时尚穿搭</a>
				<a class="load-btn load-btn-normal" title="用图片记录旅途。" data-id="213933" href="javascript:void(0);" onclick="javascript:loadworld(2)">旅行风景</a>
			</div>
			<div class="intro-text">
				<div class="intro-title">创意图中图</div>
				<div class="intro-sub-title">多图嵌套，动态浏览</div>
				<div class="intro-text-content">
					全球首创多图式场景，<br />
					更多视角，极致展示。<br/>
					轻松制作故事相册，<br />
					更好的解决图片碎片化。
				</div>
			</div>
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
		</div>
	</div>
	
	<div class="intro intro-even" id="intro2">
		<div class="intro-wrap">
			<div id="filter-demo"></div>
			<div class="intro-text">
				<div class="intro-title">一秒高大上</div>
				<div class="intro-sub-title">滤镜贴纸，酷炫美化</div>
				<div class="intro-text-content">
					18款顶级滤镜，<br />
					普通照片也能瞬间高逼格。<br />
					海量贴纸库，独家原创， <br />
					新鲜有趣玩不停。
				</div>
			</div>
		</div>
	</div>
	
	<div class="intro intro-odd" id="intro3">
		<div class="intro-wrap">
			<div id="film-demo"></div>
			<div class="intro-text">
				<div class="intro-title">轻松变大片</div>
				<div class="intro-sub-title">序幕台词，一键添加</div>
				<div class="intro-text-content">
					首创电影序幕功能，<br />
					做自己生活的编剧和导演。<br />
					用字幕表达你的每刻心情， <br />
					快速分享朋友圈。
				</div>
			</div>
		</div>
	</div>
	<div class="intro intro-even" id="intro4">
		<div class="intro-wrap">
			<div id="interact-demo"></div>
			<div class="intro-text">
				<div class="intro-title">认识新朋友</div>
				<div class="intro-sub-title">独特私聊，兴趣交友</div>
				<div class="intro-text-content">
					汇聚达人的社区，<br />
					随时跟感兴趣的人做朋友。<br />
					最有话题的聊天方式，<br />
					评论或点赞即可互动。
				</div>
			</div>
		</div>
	</div>
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
<link rel="stylesheet" type="text/css" href="${webRootPath }/htworld/webv2/css/htszoomtourV2.css?ver=${webVer}" />
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.8.2.min.js?ver=${webVer}"></script>
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js?ver=${webVer}"></script>
<script type="text/javascript" src="${webRootPath }/htworld/common/js/jquery.htszoomtourV3.js?ver=${webVer}"></script>
<script type="text/javascript" src="${webRootPath }/index/v5/js/index.js?ver=${webVer}"></script>
</html>
