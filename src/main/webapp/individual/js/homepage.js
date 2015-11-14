function initLayout(scale){	
		
		
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
			"height"	: 180 * scale + "px",
			"font-family"		: "微软雅黑"
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
		if(address.length > 7){
			var p = address.indexOf("：");
			address = address.substr(p+1,address.length - p > 7 ? 7 : address.length - p);
			$("#user-info-address").text(address);
		}
		
		var job = $("#user-info-job").text();
		if(job.length > 4){
			var p = job.indexOf("：");
			job = job.substr(p+1,job.length - p > 5 ? 5 : job.length - p);
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
			"font-family"	: "微软雅黑"			
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
			"margin"		: 36 * scale + "px 0 0 0",
			"position"		: "absolute",
			"right"			: ( 40 * scale ) + "px"
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
			"text-align": "left",
			"padding"	: "0"
		});
		
		$("#zhitu-slogan").css({
			"display"	: "block",
			"font-size"	: 24 * scale + "px",
			"color"		: "#959da5",
			"margin-top": 6 * scale + "px",
			"padding"	: "0"
		});
		
		$(".slide").css({
			"width"		: 612 * scale + "px",
			"height"	: 284 * scale + "px",
			"border"	: "1px solid #ffffff",
			"-moz-border-radius"	: 8 * scale + "px",
			"-webkit-border-radius"	: 8 * scale + "px"
		});
		$(".slide .div-slide-img").width(3060*scale);
		$(".slide .div-slide-img").click(function(){
			window.location.href="http://www.imzhitu.com";
		});
		
		$(".slide img").css({
			"width"					: 612 * scale + "px"	
		});
		
		$(".div-slide-btn").css({
			"width"		: 612 * scale + "px",
			"height"	: 20 * scale + "px"
		});
		
		$("#main").css({"display":""});
	}