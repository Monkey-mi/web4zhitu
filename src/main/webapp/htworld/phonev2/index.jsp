<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<meta name="description" content="${worldDesc }">
<meta name="keywords" content="相册,美图,社交,旅行,故事,时尚,${worldLabel}">
<title>${title }</title>
<link rel="stylesheet" type="text/css" href="/staticres/htworld/phonev2/css/htszoomtour2013122801.css" />
<link rel="stylesheet" type="text/css" href="/staticres/htworld/phonev2/css/style2013122801.css" />
<script>
var _hmt = _hmt || [];
</script>
</head>
<body>
<span id="loading">载入中...</span>
<div id="main-box">
	<div id="world">
		<div class="zt-container" data-id="${worldId }" data-aid="${authorId }" data-ver="${ver }" data-desc="${worldDesc }" data-title="${titlePath }">
			<div class="zt-title">
				<img alt="">
			</div>
			<div class="zt-loading">
				<div class="zt-loading-bg"></div>
				<div class="zt-loading-icon"></div>
			<div><span>加载中...</span><span id="zt-progress">0</span><span>%</span></div>
			</div>
		</div>
		<div class="zt-desc-hide" title="隐藏描述"></div>
			<div class="zt-desc-show" title="显示描述"></div>
		<div class="zt-desc">
			<div class="zt-desc-text"></div>
		<div class="zt-desc-bg"></div>
		</div>
	</div>
<div id="world-info-wrap">
	<div class="left-wrap">
		<img id="user-avatar" alt="" src="${authorAvatar}" />
	</div>
	<div class="right-wrap">
		<div id="user-name">${authorName }</div>
		<div>
			织图时间：<span id="create-time">${dateAdded }</span>
		</div>
		<div>
			织图张数：<span id="child-count">${childCount }</span>
		</div>
	</div>
</div>
<div id="comment-head-wrap">
	<div class="dash-line"></div>
	<span class="icon" id="icon-click-count"></span>
	<span id="click-count">${clickCount }</span>
	<span class="icon" id="icon-comment-count"></span>
	<span id="comment-count">${commentCount }</span>
</div>
<div id="comment-wrap">
	<div class="dash-line"></div>
	<div id="loadin-comment-tip" class="loading-tip none">正在加载评论...</div>
	<div id="no-comment-tip" class="loading-tip none">暂无评论...</div>
	<div id="failed-comment-tip" class="loading-tip none">评论加载失败...</div>
	<div id="comment-body"></div>
</div>
<div id="extra-wrap">
<div id="more-wrap">
	<div id="more" class="none">
		<div class="title">更多Ta的织图</div>
		<div id="thumbs" class="navigation">
		</div>
		<div class="dash-line"></div>
	</div>
</div>
<div id="foot-wrap">
	<img class="logo" height="45" src="/staticres/htworld/phonev2/images/logo.png"/>
	<div id="download-btn-wrap">
		<a class="download-btn" target="_blank" id="ios-download" 
			onclick="_hmt.push(['_trackEvent', 'app', 'download', 'zhitu'])"
			href="http://a.app.qq.com/o/simple.jsp?pkgname=com.htshuo.htsg&g_f=991653">点击下载最新版</a>
	</div>
</div>
</div>
</div>
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fd52a9c49fb7a48b836a1a23f21b49f1d' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript" src="/staticres/base/js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/staticres/base/js/baseTools.js"></script>
<script type="text/javascript" src="/staticres/htworld/common/js/jquery.htszoomtourV3.js"></script>
<script type="text/javascript" src="/staticres/htworld/phonev2/js/hts2014022401.js"></script>

</body>
</html>