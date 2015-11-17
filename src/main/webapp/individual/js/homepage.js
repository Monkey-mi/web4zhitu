function initLayout(s,scale){	
		queryUserInfo(s,scale);
		getMyWorldList(s,scale);
		
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
		$("#head-user-info").css({
			"width"		: 460 * scale + "px",
			"height"	: 160 * scale + "px",
			"overflow"	: "hidden"
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
		
		
		
		
		
		$("#user-info-concern-btn").css({
			"width" 			: 430 * scale + "px",
			"height"			: 48 * scale + "px",
			"line-height"		: 48 * scale + "px",
			"display"			: "block",
			"background-color"	: "#6abfde", 
			"text-align"		: "center",
			"border"			: "1px #5caac7 solid",
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
			"position"			: "fixed",
			"z-index"			: "99999" 
		});
		
		$("#download-logo").css({
			"width"			: "auto",
			"height"		: 80  * scale + "px",
			"margin-top"	: 10 * scale + "px",
			"margin-left"	: 14 * scale + "px",
			"display"		: "inline",
			"float"			: "left"
		});
		
		$("#download-close").css({
			"width"			: 16 * scale + "px",
			"height"		: 16 * scale + "px",
			"display"		: "inline-block",
			"cursor"		: "pointer",
			"vertical-align": "center",
			"margin"		: 36 * scale + "px " + ( 36 * scale ) + "px 0 0",
			"float"			: "right"
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
			"right"					: 52 * scale + "px"
		});
		
		$("#slogan").css({
			"display"		: "inline-block",
			"vertical-align": "top",
			"float"			: "left",
			"font-size"		: "0",
			"padding-left"	: 16 * scale + "px",
			"padding-top"	: 14 * scale + "px"
		});
		
		$("#zhitu-name").css({
			"display"	: "block",
			"valign"	: "top",
			"height"	: 30 * scale + "px",
			"font-size"	: 30 * scale + "px",
			"color"		: "#373e46",
			"text-align": "left"
		});
		
		$("#zhitu-slogan").css({
			"display"	: "block",
			"valign"	: "top",
			"height"	: 24 * scale + "px",
			"font-size"	: 24 * scale + "px",
			"margin-top": 8 * scale + "px",
			"color"		: "#959da5",
		});
		
		$(".slide").css({
			"width"		: 612 * scale + "px",
			"height"	: 284 * scale + "px",
			"border"	:"1px solid #ffffff",
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

function getMyWorldList(s,scale){
	$.post("./ztworld/ztworld_getLastNWorldByUserId",{
		's':s
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
					var imgW = 200;
					var mgrT = 6;
					var mgrR = 6;
					var mgrL = 6;
				if(worldList.length > 2){
					/*
					if(worldList.length == 4){
						imgW = 300;
						imgH = 300;
						mgrR = 12;
						mgrL = 0;
						mgrT = 6;
					}*/
				}else{
					imgW = 612;
					imgH = 612;
					mgrR = 0;
					mgrL = 0;
					mgrT = 6;
				}
				
				$(".img-grid-style").css({
						"width"					: imgW * scale + "px",
						"margin-top"			: mgrT * scale + "px", 
						"-moz-border-radius"	: 4 * scale + "px",
						"-webkit-border-radius"	: 4 * scale + "px",
						"cursor"				: "pointer"
					});
				$(".img-grid-mid").css({
					"margin-left"			: mgrL * scale + "px",
					"margin-right"			: mgrR * scale + "px"
				});
				
				var maxWidth = 640;
				var winWidth = $(window).width();
					winWidth  = winWidth > maxWidth ? maxWidth : winWidth;
				$("#user-pic").css({
					"padding"		: 8 * scale + "px " + "0 " + 8 * scale + "px " + 0 + "px",
					"background-color": "#f0f4f7"
				});
				$("#user-pic-container").css({
					"text-align"	: "center"
				});
			}
		}
	},"json");
}

function queryUserInfo(s,scale){
	$.post("./user/user_getIndividualHomePage",{'s':s},function(result){
		if(result['result'] == 0){
			document.title = result['userName'];
			if(result['signature'].length > 0){
				var descriptions = document.getElementsByName('description');
				descriptions[0].setAttribute("content",result['signature']);
			}
			$("#user-avatar").attr('src',result['avatarImgPath']);
			$("#user-info-sex").css({
				"margin-right"	: 10 * scale + "px",
				"width"			: 58 * scale + "px",
				"overflow"		: "hidden"
			});
			$("#user-info-sex").text(result['sex']);
			
			var address = result['address'];
			if(address.length < 5){
				address = "地点" + address;
			}
			$("#user-info-address").css({
				"margin-right"	: 10 * scale + "px",
				"width"			: 186 * scale + "px",
				"overflow"		: "hidden"
			});
			$("#user-info-address").text(address);
			
			var job = result['job'];
			if(job.length < 3){
				job = "职业:" + job;
			}
			$("#user-info-job").css({
				"width"			: 176 * scale + "px",
				"overflow"		: "hidden"
			});
			$("#user-info-job").text(job);
			
			if( result['verifyName'].length > 0 ){
				$("#user-info-verify span").text(result['verifyName']);
				
				$("#verifyIcon").attr('src',result['verifyIcon']);
				$("#verifyIcon").css({
					"width"			: 30 * scale + "px",
					"margin-bottom"	: 7 * scale  + "px",
					"vertical-align": "middle"
				});
				
				$("#user-info-verify").css({
					"width"		: 154 * scale + "px",
					"overflow"	: "hidden",
					"margin-right"	: 10 * scale + "px"
				});
			} else{
				$("#user-info-verify").css({
					"display"	: "none"
				});
			}
			
			$("#user-info-picCount span").text(result['picCount']);
			$("#user-info-picCount").css({
				"width"		: 230 * scale + "px",
				"overflow"	: "hidden"
			});
			
			
			$("#user-info-signature").text(result['signature']);
			$("#worldCount").text(result['worldCount']);
			$("#concernCount").text(result['concernCount']);
			$("#followCount").text(result['followCount']);
			$("#user-info-concern-btn").attr('href',"imzhitu://user?uid=" + result['userId']);
			
			$("#head-user-info").css({
				"padding-top"	: 20 * scale + "px",
				"display"		: "inline-block",
				"vertical-align": "top",
				"text-align"	: "left",
				"font-size"		: "0"
				
			});
			
			$("#head-user-info span").css({
				"-moz-border-radius"	: 20 * scale + "px",
				"-webkit-border-radius"	: 20 * scale + "px"
			});
			
			$(".user-info").css({
				"margin-bottom" 	: 8 * scale + "px",
				"background-color"	: "#ecf0f3",
				"color"				: "#62707d",
				"text-align"		: "center",
				"display"			: "inline-block",
				"font-size"			: 24 * scale + "px",
				"font-family"		: "微软雅黑",
				"height"			: 44 * scale + "px",
				"line-height"		: 44 * scale + "px",
				
			});
			
		}else{
	
		}
	},"json");
}