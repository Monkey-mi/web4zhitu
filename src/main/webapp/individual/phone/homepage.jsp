<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<%String s = request.getParameter("s"); %>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>${userName}</title>
<link rel="stylesheet" href="/individual/css/rorate.css" type="text/css" />
 <style type="text/css">
 *{
	padding:0;
	margin:0;
}
 
 </style>

</head>
<body>
  	<div id="main" style="display:none">
		<div id="head">
			<div id="user-info-avatar">
				<img id="user-avatar" class="avatar-radius" src="${avatarImgPath }"/>
				<img id="user-avatar-mask" src="/individual/images/individual_avatar_@2x.png"/>				
			</div><!-- 
			 --><div id="head-user-info">
				<span id="user-info-sex" class="user-info">${sex}</span>
				<span id="user-info-address" class="user-info">地点：${address}</span>
				<span id="user-info-job" class="user-info">职业：${job}</span>
				<br>
				
				<span id="user-info-verify" class="user-info"><img id="verifyIcon" src="${verifyIcon}">${verifyName}</span>					
				<span id="user-info-picCount" class="user-info">发布照片：${picCount}张</span>
				<br>
				
				<a href="imzhitu://user?uid=${userId}" id="user-info-concern-btn" target="_blank">加关注</a>
			</div>
				
		</div>
		<div id="user-info-signature">${signature}</div>	
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
			
			<span id="download-tip" onclick="download()">点击下载</span>
			<span id="download-close" onclick="javascript:hideDownloadBtn();"><img title="隐藏"  alt="" src="/individual/images/x@2x.png"></span>
			
			
		</div>
	</div>
	
</body>
<script type="text/javascript" src="/staticres/base/js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/individual/js/rorate.js"></script>
<script type="text/javascript" src="/staticres/base/js/callapp2.js"></script>
<script type="text/javascript" src="/individual/js/homepage.js"></script>
<script type="text/javascript">
	var maxWidth = 640;
	var winWidth = $(window).width();
		winWidth  = winWidth > maxWidth ? maxWidth : winWidth;
	var scale = winWidth / maxWidth;
	var inapp = 0;	
	var prefix = "www.imzhitu.com/DT";
	$(function(){
		
		inapp = callAppInit();
	
		$.post("./ztworld/ztworld_getLastNWorldByUserId",{
			's':<%=s%>
		},function(result){
			if(result['result'] == 0){
				var worldList = result['myWorldList'];
				var shortLinkList = result['shortLinkList'];
				for(var i=0; i<worldList.length && shortLinkList.length; i++){
					var worldTitleImg = worldList[i];
					var htm = "<img class='img-grid-style' src='"+worldTitleImg+"' onclick='window.location.href=\"" +prefix+ shortLinkList[i] + "\"'/>";
					if(i % 3 == 1){
						htm = "<img class='img-grid-style img-grid-mid' src='"+worldTitleImg+"' onclick='window.location.href=\"" +prefix+ shortLinkList[i] + "\"'/>";
					}
					$("#user-pic").append(htm);
				}
				if(worldList.length > 0){
						var imgW = 198;
						var imgH = 198;
						var mgrT = 6;
						var mgrR = 9;
						var mgrL = 9;
					if(worldList.length > 2){						
						if(worldList.length == 4){
							imgW = 296;
							imgH = 296;
							mgrR = 12;
							mgrL = 12;
							mgrT = 7;
						}
					}else{
						imgW = 612;
						imgH = 612;
						mgrR = 0;
						mgrL = 0;
						mgrT = 8;
					}
					$(".img-grid-style").css({
							"width"					: imgW * scale + "px",
							"height"				: imgH * scale + "px",
							"margin-top"			: mgrT * scale + "px", 
							"-moz-border-radius"	: 4 * scale + "px",
							"-webkit-border-radius"	: 4 * scale + "px",
							"cursor"				: "pointer"			
						});
					$(".img-grid-mid").css({
							"margin-left"			: ( mgrL * scale ) + "px",
							"margin-right"			: ( mgrR * scale ) + "px" 
					});
				}
			}
		},"json");
		
		initLayout(scale);
		
		zBase.init(612 * scale ,2000,2000,'div-slide','div-slide-img','div-slide-btn');
		
		if(inapp == 1){
			$("#download-wrap").hiden();
		}else{
			$("#download-wrap").show();
			$("#main").css({"margin-bottom": 124 * scale + "px"});
		}
		document.getElementById("user-info-concern-btn").addEventListener('click',openclientV2,false);
	});
	
	function hideDownloadBtn() {
		$("#download-wrap").hide();
		$("#main").css({"margin-bottom":20 * scale  + "px"});
	}
	
	function download(){
		window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.htshuo.htsg";
	}	
</script>
</html>