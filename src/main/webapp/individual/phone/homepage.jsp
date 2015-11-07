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
				
				<span href="imzhitu://user?uid=${userId}" id="user-info-concern-btn">加关注</span>
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
			<span id="download-close" onclick="javascript:hideDownloadBtn();"><img title="隐藏"  alt="" src="/individual/images/x@2x.png"></span>
			<span id="download-tip" onclick="download()">点击下载</span>
			
			
		</div>
	</div>
	
</body>
<script type="text/javascript" src="/staticres/base/js/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/individual/js/rorate.js"></script>
<script type="text/javascript" src="/staticres/base/js/callapp2.js"></script> 
<script type="text/javascript">
	var maxWidth = 640;
	var winWidth = $(window).width();
		winWidth  = winWidth > maxWidth ? maxWidth : winWidth;
	var scale = winWidth / maxWidth;
	var inapp = 0;	

	$(function(){
		
		inapp = callAppInit();
	
		$.post("./ztworld/ztworld_getLastNWorldByUserId",{
			's':<%=s%>
		},function(result){
			if(result['result'] == 0){
				var worldList = result['myWorldList'];
				for(var i=0; i<worldList.length; i++){
					var worldTitleImg = worldList[i];
					var htm = "<img class='img-grid-style' src='"+worldTitleImg+"'/>";
					$("#user-pic").append(htm);
				}
				if(worldList.length > 0){
						var imgW = 198;
						var imgH = 198;
						var mgrT = 6;
						var mgrR = 3;
						var mgrL = 3;
					if(worldList.length > 2){						
						if(worldList.length == 4){
							imgW = 296;
							imgH = 296;
							mgrR = 4;
							mgrL = 4;
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
							"margin"				: mgrT * scale + "px " + ( mgrR * scale )+ "px 0 " + ( mgrL * scale ) + "px", 
							"-moz-border-radius"	: 4 * scale + "px",
							"-webkit-border-radius"	: 4 * scale + "px"
						});
				}
			}
		},"json");
		
		initLayout();
		
		zBase.init(612 * scale ,2000,2000,'div-slide','div-slide-img','div-slide-btn');
		
		if(inapp == 1){
			$("#download-wrap").hiden();
		}else{
			$("#download-wrap").show();
			$("#main").css({"margin-bottom": 124 * scale + "px"});
		}
		document.getElementById("user-info-concern-btn").addEventListener('click',openclient,false);
	});
	
	function hideDownloadBtn() {
		$("#download-wrap").hide();
		$("#main").css({"margin-bottom":20 * scale  + "px"});
	}
	
	function download(){
		window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.htshuo.htsg";
	}
	
	function initLayout(){	
		
		
		$("#main").css({
			"width"		: winWidth + "px",
			"margin"	: "0 auto"
		});

		
		$(".avatar-radius").css({
			"-moz-border-radius"	:75 * scale +"px",
			"-webkit-border-radius"	:75 * scale +"px"	
		});
		
		$("#head").css({
			"width"		: "100%",
			"height"	: 180 * scale + "px"
		});
		$("#user-info-avatar").css({
			"width"		: 180 * scale + "px",
			"height"	: 180 * scale + "px",
			"position"	: "relative",
			"display"	: "inline-block",
			"margin"	: "0 auto"
		});
		$("#user-avatar").css({
			"width"		: 150 * scale + "px",
			"height"	: 150 * scale + "px",
			"left"		: 20 * scale + "px",
			"top"		: 20 * scale + "px",
			"z-index"	: "1",
			"position"	: "absolute"
		});
		
		$("#user-avatar-mask").css({
			"width"		: 152 * scale + "px",
			"height"	: 152 * scale + "px",
			"left"		: 20 * scale + "px",
			"top"		: 20 * scale + "px",
			"z-index"	: "999",
			"position"	: "absolute"
		});
		
		$(".avatar-radius").css({
			"-moz-border-radius"	: 75 * scale + "px",
			"-webkit-border-radius"	: 75 * scale + "px"	
		});
		
		$("#head-user-info").css({
			"padding-top"	: 20 * scale + "px",
			"display"		: "inline-block",
			"vertical-align": "top",
			"text-align"	: "left"
		});
		
		$("#head-user-info span").css({
			"-moz-border-radius"	: 20 * scale + "px",
			"-webkit-border-radius"	: 20 * scale + "px"	
		});
		
		$(".user-info").css({
			"margin-bottom" 	: 8 * scale + "px",
			"padding" 			: "0 " + ( 16 * scale ) + "px",
			"background-color"	: "#ecf0f3",
			"color"				: "#62707d",
			"text-align"		: "center",
			"display"			: "inline-block",
			"font-size"			: 24 * scale + "px",
			"font-family"		: "微软雅黑",
			"height"			: 44 * scale + "px",
			"line-height"		: 44 * scale + "px"
		});
		
		if($("#verifyIcon").attr("src").length > 0){
			$("#verifyIcon").css({
				"width"		: 30 * scale + "px",
				"margin-bottom": 7 * scale + "px",
				"vertical-align"	: "middle"
			});
		}else{
			$("#user-info-verify").css({
				"display"	: "none"
			});
		}
		
		var address = $("#user-info-address").text();
		if(address.length > 8){
			var p = address.indexOf("：");
			address = address.substr(p+1,address.length - p > 8 ? 8 : address.length - p);
			$("#user-info-address").text(address);
		}
		
		var job = $("#user-info-job").text();
		if(job.length > 5){
			var p = job.indexOf("：");
			job = job.substr(p+1,job.length - p > 8 ? 8 : job.length - p);
			$("#user-info-job").text(job);
		}
		
		$("#user-info-concern-btn").css({
			"width" 			: 430 * scale + "px",
			"height"			: 48 * scale + "px",
			"line-height"		: 48 * scale + "px",
			"display"			: "block",
			"background-color"	: "#6abfde", 
			"text-align"		: "center",
			"border"			: "2px #5caac7 solid",
			"color"				: "#ffffff",
			"font-size"			: 26 * scale + "px",
			"font-family"		: "微软雅黑",
			"-moz-border-radius"	: 24 * scale + "px",
			"-webkit-border-radius"	: 24 * scale + "px",
			"cursor"			: "pointer"	
		});
		
		$("#user-info-signature").css({
			"padding-left" 		: 24 * scale + "px",
			"text-align"  		: "left",
			"display"			: "block",
			"font-size"			: 24 * scale + "px",
			"color"				: "#62707d",
			"margin-top"		: 10 * scale + "px"
		});
		
		$("#user-data").css({
			"margin"		: "auto",
			"margin-top"	: 20 * scale + "px",
			"border-bottom"	: "1px #d7dce0 solid",
			"border-top"	: "1px solid #d7dce0"
		});
		
		$("#user-pic").css({
			"margin"		: "auto",
			"text-align"	: "center",
			"padding-top"	: 8 * scale + "px",
			"background-color": "#f0f4f7"
		});
		
		$(".span-user-data-desc").css({
			"font-size"		: 22 * scale + "px",
			"color"			: "#b6bec6",
			"font-family"	: "微软雅黑"
		});
		
		$(".span-user-data").css({
			"font-size"		: 30 * scale + "px",
			"color"			: "#62707d",
			"font-family"	: "ArialMT Regular"			
		});
		
		$("#user-data div").css({
			"display"		: "inline-block",
			"vertical-align": "top",
			"width"			: "32%",
			"padding"		: 4 * scale + "px 0 " + (10*scale) + "px 0"
		});
		
		$("#user-data span").css({
			"width"			: "100%",
			"height"		: "50%",
			"text-align"	: "center",
			"display"		: "block",
			"margin-top"	: 6 * scale + "px"
		});
		
		
		
		$("#user-concernCount").css({
			"border-left"	: "1px #d7dce0 solid",
			"border-right"	: "1px #d7dce0 solid"
		});
		
		
		
		$("#div-propaganda").css({
			"margin"			: "auto",
			"padding-top"		: 6 * scale + "px",
			"background-color"	: "#f0f4f7"												
		});
		
		$("#download-wrap").css({
			"width"				: 640 * scale + "px",
			"height"			: 104 * scale + "px",
			"margin"			: "0 auto",
			"bottom"			: "0",
			"text-align"		: "center",
			"overflow"			: "hidden",	
			"font-family"		: "微软雅黑",
			"background-color"  : "rgba(255,255,255,0.9)",
			"padding-bottom"	: 10 * scale + "px",
			"position"			: "fixed",
			"z-index"			: "99999" 
		});
		
		$("#download-logo").css({
			"width"			: "auto",
			"height"		: 80  * scale + "px",
			"margin-top"	: 10 * scale + "px",
			"margin-left"	: 14 * scale + "px",
			"display"		: "inline",
			"position"		: "absolute",
			"left"			: "0"
		});
		
		$("#download-close").css({
			"width"			: 16 * scale + "px",
			"height"		: 16 * scale + "px",
			"display"		: "inline-block",
			"cursor"		: "pointer",
			"vertical-align": "center",
			"margin"		: 36 * scale + "px " + ( 30 * scale ) + "px 0 0",
			"position"		: "absolute",
			"right"			: "0"
		});
		
		$("#download-tip").css({
			"background"			: "#3a97d0",
			"width"					: 200 * scale + "px",
			"height"				: 60 * scale + "px",
			"color"					: "#ffffff",
			"-moz-border-radius"	: 4 * scale + "px",
			"-webkit-border-radius"	: 4 * scale + "px",
			"border-radius"			: 4 * scale + "px",
			"text-align"			: "center",
			"font-size"				: 24 * scale + "px",
			"display"				: "inline-block",
			"vertical-align"		: "top",
			"cursor"				: "pointer",
			"line-height"			: 60 * scale + "px",
			"float"					: "right",
			"margin"				: 22 * scale + "px " + (22 * scale) + "px 0 0",
			"position"				: "absolute",
			"right"					: 58 * scale + "px"
		});
		
		$("#slogan").css({
			"display"		: "inline-block",
			"vertical-align": "top",
			"position"		: "absolute",
			"left"			: 120 * scale +"px"
		});
		
		$("#zhitu-name").css({
			"display"	: "block",
			"font-size"	: 30 * scale + "px",
			"color"		: "#373e46",
			"margin-top": 14 * scale + "px",
			"text-align": "left"
		});
		
		$("#zhitu-slogan").css({
			"display"	: "block",
			"font-size"	: 24 * scale + "px",
			"color"		: "#959da5",
			"margin-top": 6 * scale + "px"
		});
		
		$(".slide").css({
			"width"		: 612 * scale + "px",
			"height"	: 284 * scale + "px",
			"-moz-border-radius"	: 8 * scale + "px",
			"-webkit-border-radius"	: 8 * scale + "px"
		});
		$(".slide .div-slide-img").width(3060*scale);
		$(".slide img").css({
			"width"					: 612 * scale + "px"	
		});
		
		$(".div-slide-btn").css({
			"width"		: 612 * scale + "px",
			"height"	: 20 * scale + "px"
		});
		
		$("#main").css({"display":""});
	}
</script>
</html>