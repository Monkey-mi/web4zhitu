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
<link rel="stylesheet" type="text/css" href="/staticres/htworld/phonev3/css/htszoomtour2013122801.css" />
<link rel="stylesheet" type="text/css" href="/staticres/htworld/phonev3/css/style2013122801.css" />
<style type="text/css">

.avatar-wrap {
	width: 34px;
	height: 34px;
	display: inline-block;
}

.avatar-wrap .avatar {
	width:32px;
	height:32px;
	position: absolute;
	border-radius: 100px;
	-moz-border-radius: 100px;
	-webkit-border-radius: 100px;
}

.avatar-wrap .verify {
	width:17px;
	height:17px;
	margin:18px 0 0 20px;
	position: absolute;
	border-radius: 100px;
	-moz-border-radius: 100px;
	-webkit-border-radius: 100px;
}

#user {
	height:20px;
	width:100%;
}

#world-desc {
	margin-top:17px;
	padding:0 10px 0 10px;
}

#user .avatar-wrap {
	margin-left:10px;
	margin-top:11px;
}

#user {
	height: 54px;
}

#user #user-name {
	color: #3a98d0;
	font-size:14px;
	display: inline-block;
	position: absolute;
	left:52px;
	top:11px;
}

#user #count-wrap {
	float:right;
	margin-top:11px;
	margin-right:10px;
	font-size:12px;
	color: #b6bec6;
	line-height:18px;
	text-align: right;
	vertical-align: middle;
}

#btn-wrap {
	padding:0 10px 0 10px;
	margin-top:15px;
}

#btn-wrap .opt-btn {
	width:54px;
	height:27px;
	border:1px solid #e8ecf0;
	display: inline-block;
	border-radius: 2x;
	-moz-border-radius: 2px;
	-webkit-border-radius: 2px;
	margin-right:6px;
	background: #ffffff;
}

#share-btn {
	float:right;
	margin-right:0px !important;
}

#liked-user {
	margin-top:15px;
	padding:0 10px 0 10px;
	line-height: 34px;
}

#liked-user .verify {
	width:12px;
	height:12px;
	margin:22px 0 0 20px;
}

#liked-user #liked-count {
	background:#ffffff;
	border:1px solid #d7dce0;
	display:inline-block;
	border-radius: 100px;
	-moz-border-radius: 100px;
	-webkit-border-radius: 100px;
	float:right;
	padding:0 10px 0 10px;
	font-size:12px;
	color: #62707d;
	vertical-align: middle;
	line-height: 28px;
	min-width: 8px;
	text-align: center;
	margin-top:3px;
}


</style>
</head>
<body>
<span id="loading">载入中...</span>
<div id="main-box">
<div id="user">
	<div class="avatar-wrap">
		<img class="avatar" alt="" 
		src="http://wx.qlogo.cn/mmopen/Ib5852jAyb9UlibqaPeKmgL5jEjTIJIiaA4elsXtK29R0jhglIhMZVEd6ViaZI4VMUOlVKtpdP3IUgUiaLHf6iaPK6RwGnnQrH6Kb/0" />
		<img class="verify" src="http://imzhitu.qiniudn.com/user/verify/1438243301000.png"/>
	</div>
	<div id="user-name">
		朱天杰
	</div>
	<div id="count-wrap">
		<div id="child-count-wrap">
			<span id="child-count">4</span>张<span> | </span><span>1天前</span>
		</div>
		<div id="click-count-wrap">
			<span>12.7k</span><span>浏览</span>
		</div>
	</div>
</div>
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
<div id="world-desc">
	The old time.The old time.sdfsdf分水电费水电费谁的发生的发达是阿斯顿发生地方
</div>
<div id="btn-wrap">
	<div class="opt-btn"></div>
	<div class="opt-btn"></div>
	<div id="share-btn" class="opt-btn"></div>
</div>

<div id="liked-user">
	<div class="avatar-wrap">
		<img class="avatar" alt="" 
		src="http://wx.qlogo.cn/mmopen/Ib5852jAyb9UlibqaPeKmgL5jEjTIJIiaA4elsXtK29R0jhglIhMZVEd6ViaZI4VMUOlVKtpdP3IUgUiaLHf6iaPK6RwGnnQrH6Kb/0" />
		<img class="verify" src="http://imzhitu.qiniudn.com/user/verify/1438243301000.png"/>
	</div>
	<div class="avatar-wrap">
		<img class="avatar" alt="" 
		src="http://wx.qlogo.cn/mmopen/Ib5852jAyb9UlibqaPeKmgL5jEjTIJIiaA4elsXtK29R0jhglIhMZVEd6ViaZI4VMUOlVKtpdP3IUgUiaLHf6iaPK6RwGnnQrH6Kb/0" />
		<img class="verify" src="http://imzhitu.qiniudn.com/user/verify/1438243301000.png"/>
	</div>
	<div class="avatar-wrap">
		<img class="avatar" alt="" 
		src="http://wx.qlogo.cn/mmopen/Ib5852jAyb9UlibqaPeKmgL5jEjTIJIiaA4elsXtK29R0jhglIhMZVEd6ViaZI4VMUOlVKtpdP3IUgUiaLHf6iaPK6RwGnnQrH6Kb/0" />
		<img class="verify" src="http://imzhitu.qiniudn.com/user/verify/1438243301000.png"/>
	</div>
	<div class="avatar-wrap">
		<img class="avatar" alt="" 
		src="http://wx.qlogo.cn/mmopen/Ib5852jAyb9UlibqaPeKmgL5jEjTIJIiaA4elsXtK29R0jhglIhMZVEd6ViaZI4VMUOlVKtpdP3IUgUiaLHf6iaPK6RwGnnQrH6Kb/0" />
		<img class="verify" src="http://imzhitu.qiniudn.com/user/verify/1438243301000.png"/>
	</div>
	<div class="avatar-wrap">
		<img class="avatar" alt="" 
		src="http://wx.qlogo.cn/mmopen/Ib5852jAyb9UlibqaPeKmgL5jEjTIJIiaA4elsXtK29R0jhglIhMZVEd6ViaZI4VMUOlVKtpdP3IUgUiaLHf6iaPK6RwGnnQrH6Kb/0" />
		<img class="verify" src="http://imzhitu.qiniudn.com/user/verify/1438243301000.png"/>
	</div>
	<div id="liked-count">
		1000
	</div>
</div>

<div></div>

</div>
<script type="text/javascript" src="/staticres/base/js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/staticres/base/js/baseTools.js"></script>
<script type="text/javascript" src="/staticres/htworld/common/js/jquery.htszoomtourV3.js"></script>
<script type="text/javascript" src="/staticres/htworld/phonev3/js/hts2014022401.js"></script>
<div style="display:none;">
<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1255868073'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1255868073' type='text/javascript'%3E%3C/script%3E"));</script>
</div>
</body>
</html>