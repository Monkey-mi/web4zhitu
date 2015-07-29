<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="${worldDesc }">
<meta name="keywords" content="相册,美图,社交,旅行,故事,时尚,${worldLabel}">
<title>${title}</title>
<link rel="stylesheet" type="text/css" href="/staticres/htworld/webv2/css/style.css" />
</head>
<body>
	<div id="main" >
		<div id="left-opt" class="opt"></div>
		<div id="zt-world">
		<div class="zt-container"  data-id="${worldId }"  data-cover="${coverPath }" data-aid="${authorId }"
			 data-url="${worldURL }" data-ver="${ver }" data-desc="${worldDesc }" data-title="${titlePath }">
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
		<div id="right-opt" class="opt">
			<div id="like-opt" class="opt-btn btn-round">
				<span class="unlike icon"></span>
				<span id="like-count">${likeCount}</span>
			</div>
		</div>
	</div>
	<div id="left-panel">
		<a class="logo" href="/" title="织图首页"><img src="/staticres/htworld/webv2/images/logo.png" /></a>
		<a id="download-iphone" class="btn-download btn-round" title="织图iPhone版本下载" target="_blank" 
			onclick="_hmt.push(['_trackEvent', 'app', 'download', 'ios'])"
			href="https://itunes.apple.com/cn/app/zhi-tu/id576156900?mt=8">iPhone版本下载</a>
		<a id="download-android" class="btn-download btn-round" title="织图Android版本下载" target="_blank" 
			onclick="_hmt.push(['_trackEvent', 'app', 'download', 'android'])"
			href="http://dd.myapp.com/16891/1923471F4A3601B5AB88B5FB0EDB8965.apk?fsname=com%2Ehtshuo%2Ehtsg%5F2%2E9%2E5%5F28.apk">Android版本下载</a>
		<div id="more-wrap">
			<div id="more" class="none">
				<div class="title">更多Ta的织图</div>
				<div class="dash-line"></div>
				<div id="thumbs" class="navigation">
					<ul class="thumbs">
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div id="right-panel">
		<a id="btn-colse-right" class="btn-right-panel" href="javascript:$.pageslide.close();" title="收起" ></a>
		<div class="wrap">
			<div id="user-info" class="btn-round">
				<div>
					<img id="user-avatar" alt="" src="${authorAvatar}" onerror="baseTools.imgErrorCallbackWithWH(this,'http://oss.aliyuncs.com/imzhitu/tmp/0.jpg')" />
					<span id="user-name">${authorName}</span>
				</div>
				<div>
					织图时间：<span id="create-time">${dateAdded}</span>
				</div>
				<div>
					织图张数：<span id="child-count">${childCount }</span>
				</div>
			</div>
			<div id="share-platform">
				<span>分享到：</span>
				<span id="sina" class="platform icon" title="分享到新浪微博"></span>
				<span id="qzone" class="platform icon" title="分享到QQ空间"></span>
				<span id="renren" class="platform icon" title="分享到人人网"></span>
			</div>
				
			<div id="share-platform-dashline" class="dash-line"></div>
			<div id="comment-wrap">
				<div class="comment-head">
					<span id="icon-click-count" class="icon"></span><span id="click-count-num">${clickCount}</span>
					<span id="icon-comment-count" class="icon"></span><span id="comment-count-num">0</span>
				</div>
				<div class="dash-line"></div>
				<div id="loadin-comment-tip" class="loading-tip none">正在加载评论...</div>
				<div id="no-comment-tip" class="loading-tip none">暂无评论...</div>
				<div id="failed-comment-tip" class="loading-tip none">评论加载失败...</div>
				<div id="comment-body">
				</div>
			</div>
		</div>
	</div>
	<a id="btn-open-right" class="btn-right-panel none" href="#right-panel" title="展开" ></a>
	<link rel="stylesheet" type="text/css" href="/staticres/base/js/jquery/pageslide-2.0.0/jquery.pageslide.css" />
	<link rel="stylesheet" type="text/css" href="/staticres/htworld/webv2/css/htszoomtourV2.css" />
	<script type="text/javascript" src="/staticres/base/js/jquery/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="/staticres/base/js/baseTools.js"></script>
	<script type="text/javascript" src="/staticres/base/js/jquery/jquery.nicescroll.min.js"></script>
	<script type="text/javascript" src="/staticres/htworld/common/js/jquery.htszoomtourV3.js"></script>
	<script type="text/javascript" src="/staticres/htworld/webv2/js/hts2014022401.js"></script>
	<script type="text/javascript" src="/staticres/base/js/jquery/pageslide-2.0.0/jquery.pageslide.js"></script>
	<div style="display:none;">
	<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1255868073'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1255868073' type='text/javascript'%3E%3C/script%3E"));</script>
	</div>
</body>
</html>