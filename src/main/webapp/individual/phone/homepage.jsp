<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<%String s = request.getParameter("s"); %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="相册,美图,社交,旅行,故事,时尚,织图,下载,织图下载">
<meta name="keywords" content="相册,美图,社交,旅行,故事,时尚,织图,下载,织图下载">
<meta name="viewport" id="viewport"/>
<title>${userName}</title>
<link rel="stylesheet" href="/individual/css/rorate.css" type="text/css" />
<link rel="stylesheet" href="/individual/css/individual.css" type="text/css" />

<script type="text/javascript" src="/staticres/base/js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/individual/js/rorate.js"></script>
<script type="text/javascript">

	var userAgent = navigator.userAgent.toLowerCase();
	if(userAgent.match(/uc/i) != "uc") {
		var dpr = window.devicePixelRatio,
			dpi = dpr * 320;
		document.getElementById('viewport').setAttribute('content', 'width=device-width,target-densitydpi='+dpi+',initial-scale=1.0, user-scalable=true');
	} else {
		document.getElementById('viewport').setAttribute('content', 'width=target-densitydpi=device-dpi,initial-scale=1.0, user-scalable=true');
	}

	$(function(){
		$.post("./ztworld/ztworld_getLastNWorldByUserId",{
			's':<%=s%>
		},function(result){
			if(result['result'] == 0){
				var worldList = result['myWorldList'];
				for(var i=0; i<worldList.length; i++){
					var worldTitleImg = worldList[i];
					var htm = "<img class='world-title-list' src='"+worldTitleImg+"'/>";
					$("#user-pic").append(htm);
				}
				
				if(worldList.length > 4){
				 	$(".world-title-list").addClass("img-grid-style");
				}
			}
		},"json");
		
		zBase.init(612,1000,2000,'div-slide','div-slide-img','div-slide-btn');
	});
	
	function hideDownloadBtn() {
		$("#download-wrap").hide();
		$("#main").css({"margin-bottom":"0px"});
	}
	
	function download(){
		window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.htshuo.htsg";
	}
</script>
</head>
<body>
  	<div id="main">
		<div id="head">
			<div id="user-info-avatar">
				<img id="user-avatar" class="avatar-radius" src="${avatarImgPath }"/>
				<img id="user-avatar-mask" src="/individual/images/individual_avatar_@2x.png"/>				
			</div>
			<div id="head-user-info">
				<span id="user-info-sex" class="user-info">${sex}</span>
				<span id="user-info-address" class="user-info">地点：${address}</span>
				<span id="user-info-job" class="user-info">职业：${job}</span>
				<br>
				
				<span id="user-info-verify" class="user-info">${verifyName}</span>					
				<span id="user-info-picCount" class="user-info">发布照片：${picCount}张</span>
				<br>
				<span id="user-info-concern-btn">加关注</span>
			</div>
			<span id="user-info-signature">${signature}</span>		
		</div>
		<div id="user-data">
			<div id="user-worldCount" >
				<span id="worldCount"  class="span-user-data" >${worldCount }</span>
				<span class="span-user-data-desc">相册</span>
			</div>
			<div id="user-concernCount" >
				<span id="concernCount" class="span-user-data" >${concernCount }</span>
				<span class="span-user-data-desc">关注</span>
			</div>
			<div id="user-followCount" >
				<span id="followCount" class="span-user-data">${followCount }</span>
				<span class="span-user-data-desc">粉丝</span>
			</div>
		</div>
		
		<!-- 用户图片 -->
		<div id="user-pic"></div>
		
		<!-- 轮播图片 -->
		<div id="div-propaganda">
			<div class="slide" id="div-slide">
				<div class="div-slide-img">
					<img src="http://ww4.sinaimg.cn/large/a0a4bc09gw1er8n9yh6poj20fk078ab8.jpg" title="图片1"/>
					<img src="http://ww1.sinaimg.cn/large/a0a4bc09gw1er8rwlndlkj20fk078aaz.jpg" title="图片2"/>
					<img src="http://ww4.sinaimg.cn/large/a0a4bc09gw1er8rwo4pn3j20fk078757.jpg" title="图片3"/>
					<img src="http://ww1.sinaimg.cn/large/a0a4bc09gw1er8rwxb6fyj20fk078gmi.jpg" title="图片4">
					<img src="http://ww2.sinaimg.cn/large/a0a4bc09gw1er8rwzl6tej20fk078q3w.jpg" title="图片5">
				</div>
				<div class="div-slide-btn">
					<a href="#" class="hover">●</a>
					<a href="#">●</a>
					<a href="#">●</a>
					<a href="#">●</a>
					<a href="#">●</a>
				</div>
			</div> 
		</div>
		
		<div id="download-wrap">
					
			<img id="download-logo" alt="" src="/individual/images/logo.png">
			<div id="slogan">
				<span id="zhitu-name">织图</span>
				<span id="zhitu-slogan">年轻人的生活方式</span>
			</div>				
			<span id="download-close" onclick="javascript:hideDownloadBtn();"><img title="隐藏"  alt="" src="/staticres/base/images/down-close.jpg"></span>
			<span id="download-tip" onclick="download()">点击下载</span>
			
			
		</div>
	</div>
	
</body>
</html>