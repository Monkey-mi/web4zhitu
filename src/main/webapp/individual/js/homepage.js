function initLayout(s,scale){	
		queryUserInfo(s,scale);
		getMyWorldList(s,scale);
		
		$("#main").css({
			"width"		: winWidth + "px",
			"margin"	: "0 auto"
		});

		
		$(".avatar-radius").css({
			"-moz-border-radius"	:34 +"px",
			"-webkit-border-radius"	:34 +"px"	
		});
		
		$("#head").css({
			"width"		: "100%",
			"height"	: 83 + "px",
			"font-family"		: "微软雅黑"
		});
		$("#user-info-avatar").css({
			"width"		: 83 + "px",
			"height"	: 83 + "px",
			"position"	: "relative",
			"display"	: "inline-block",
			"margin"	: "0 auto"
		});
		$("#head-user-info").css({
			"width"		: (winWidth - 83) + "px",
			"height"	: 73 + "px",
			"overflow"	: "hidden"
		});
		$("#user-avatar").css({
			"width"		: 67 + "px",
			"height"	: 67 + "px",
			"left"		: 10 + "px",
			"top"		: 10 + "px",
			"z-index"	: "1",
			"position"	: "absolute"
		});
		
		$("#user-avatar-mask").css({
			"width"		: 68 + "px",
			"height"	: 68 + "px",
			"left"		: 10 + "px",
			"top"		: 10 + "px",
			"z-index"	: "999",
			"position"	: "absolute"
		});
		
		$(".avatar-radius").css({
			"-moz-border-radius"	: 34 + "px",
			"-webkit-border-radius"	: 34 + "px"	
		});
		
		
		
		
		
		$("#user-info-concern-btn").css({
			"width" 			: (winWidth - 93) + "px",
			"height"			: 21 + "px",
			"line-height"		: 22 + "px",
			"display"			: "block",
			"background-color"	: "#6abfde", 
			"text-align"		: "center",
			"border"			: "1px #5caac7 solid",
			"color"				: "#ffffff",
			"font-size"			: 13 + "px",
			"font-family"		: "微软雅黑",
			"-moz-border-radius"	: 10 + "px",
			"-webkit-border-radius"	: 10 + "px",
			"cursor"			: "pointer"	
		});
		
		$("#user-info-signature").css({
			"padding-left" 		: 12 + "px",
			"text-align"  		: "left",
			"display"			: "block",
			"font-size"			: 12 + "px",
			"color"				: "#62707d",
			"margin-top"		: 10 + "px",
			"line-height"		: 18 + "px"
		});
		
		$("#user-data").css({
			"height"		: "36px",
			"margin"		: "auto",
			"margin-top"	: 8 + "px",
			"border-bottom"	: "1px #d7dce0 solid",
			"border-top"	: "1px solid #d7dce0",
			"font-size"		: "0"
		});
		
		
		
		$(".span-user-data-desc").css({
			"font-size"		: 11 + "px",
			"color"			: "#b6bec6",
			"font-family"	: "微软雅黑",
			"margin-bottom"	: "1px",
			"line-height"	: "17px"
		});
		
		$(".span-user-data").css({
			"font-size"		: 15 + "px",
			"color"			: "#62707d",
			"font-family"	: "微软雅黑",
			"margin-top"	: "1px",
			"line-height"	: "20px"
		});
		
		$("#user-data div").css({
			"display"		: "inline-block",
			"vertical-align": "top",
			"width"			: "32%"
		});
		
		$("#user-data span").css({
			"width"			: "100%",
			"height"		: "17px",
			"text-align"	: "center",
			"display"		: "block"
		});
		
		
		
		$("#user-concernCount span").css({
			"border-left"	: "1px #d7dce0 solid",
			"border-right"	: "1px #d7dce0 solid",
			"height"		: "14px"
		});
		
		$("#concernCount").css({
			"margin-top"	: "4px",
			"line-height"   : "14px"
		});
		$("#span-user-data-desc").css({
			"line-height"   : "14px"
		});
		
		
		
		$("#div-propaganda").css({
			"margin"			: "auto",
			"padding-top"		: 3 + "px",
			"background-color"	: "#f0f4f7"												
		});
		
		$("#download-wrap").css({
			"width"				: 640 * scale + "px",
			"height"			: 52 + "px",
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
			"height"		: 40 + "px",
			"margin-top"	: 5 + "px",
			"margin-left"	: 7 + "px",
			"display"		: "inline",
			"float"			: "left"
		});
		
		$("#download-close").css({
			"display"		: "inline-block",
			"cursor"		: "pointer",
			"vertical-align": "center",
			"margin"		: 20 + "px " + 14 + "px 0 0",
			"float"			: "right"
		});
		$("#download-close img").css({
			"width"			: 10 + "px",
			"height"		: 10 + "px",
		});
		
		$("#download-tip").css({
			"background"			: "#3a97d0",
			"width"					: 100 + "px",
			"height"				: 30 + "px",
			"color"					: "#ffffff",
			"-moz-border-radius"	: 2 + "px",
			"-webkit-border-radius"	: 2 + "px",
			"border-radius"			: 2 + "px",
			"text-align"			: "center",
			"font-size"				: 13 + "px",
			"display"				: "inline-block",
			"vertical-align"		: "top",
			"cursor"				: "pointer",
			"line-height"			: 30 + "px",
			"float"					: "right",
			"margin"				: 11 + "px " + (11) + "px 0 0",
			"position"				: "absolute",
			"right"					: 26 + "px"
		});
		
		$("#slogan").css({
			"display"		: "inline-block",
			"vertical-align": "top",
			"float"			: "left",
			"font-size"		: "0",
			"padding-left"	: 8 + "px",
			"padding-top"	: 7 + "px"
		});
		
		$("#zhitu-name").css({
			"display"	: "block",
			"valign"	: "top",
			"height"	: 15 + "px",
			"font-size"	: 14 + "px",
			"color"		: "#373e46",
			"text-align": "left"
		});
		
		$("#zhitu-slogan").css({
			"display"	: "block",
			"valign"	: "top",
			"height"	: 12 + "px",
			"font-size"	: 12 + "px",
			"margin-top": 4 + "px",
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
		
		
	}

function getMyWorldList(s,scale){
	$.post("./ztworld/ztworld_getLastNWorldByUserId",{
		's':s,
		'limit':24
	},function(result){
		if(result['result'] == 0){
			var worldList = result['htworld'];
			for ( var i = 0 ; i < worldList.length; i++){
				var worldItem = worldList[i];
				var htm = "<img class='img-grid-style' src='"+worldItem['titleThumbPath']+"' onclick='window.location.href=\"" +prefix+ worldItem['shortLink'] + "\"'/>";
				if(i % 3 == 1){
					htm = "<img class='img-grid-style img-grid-mid' src='"+worldItem['titleThumbPath']+"' onclick='window.location.href=\"" +prefix+ worldItem['shortLink'] + "\"'/>";
				}
				$("#user-pic").append(htm);
			}
			
			if(worldList.length > 0){
					var imgW = 200;
					var mgrT = 6;
					var mgrR = 6 ;
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
						"cursor"				: "pointer"
					});
				$(".img-grid-mid").css({
					"margin-left"			: mgrL * scale + "px",
					"margin-right"			: mgrR * scale + "px"
				});
				
				$("#user-pic-container").css({
					"text-align"	  : "center",
					"background-color": "#f0f4f7",
					"padding"		  : 10 * scale + "px " + "0px " + 3 + "px 0px"
				});
				
				$("#user-pic").css({
					"text-align"	  : "left"
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
				"margin-right"	: 5 + "px",
				"width"			: 24 + "px",
				"overflow"		: "hidden"
			});
			$("#user-info-sex").text(result['sex']);
			
			var address = result['address'];
			if(address.length < 5){
				address = "地点" + address;
			}
			$("#user-info-address").css({
				"margin-right"	: 5 + "px",
				"width"			: (winWidth -127)/2 + "px",
				"overflow"		: "hidden"
			});
			$("#user-info-address").text(address);
			
			var job = result['job'];
			if(job.length < 3){
				job = "职业:" + job;
			}
			$("#user-info-job").css({
				"width"			: (winWidth -127)/2 + "px",
				"overflow"		: "hidden"
			});
			$("#user-info-job").text(job);
			
			if( result['verifyName'].length > 0 ){
				$("#user-info-verify span").text(result['verifyName']);
				
				$("#verifyIcon").attr('src',result['verifyIcon']);
				$("#verifyIcon").css({
					"width"			: 15 + "px",
					"margin-bottom"	: 3  + "px",
					"vertical-align": "middle",
					"margin-right"	: "2px"
				});
				
				$("#user-info-verify").css({
					"width"		: 154 * scale + "px",
					"overflow"	: "hidden",
					"margin-right"	: 5 + "px"
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
				"padding-top"	: 10 + "px",
				"display"		: "inline-block",
				"vertical-align": "top",
				"text-align"	: "left",
				"font-size"		: "0"
				
			});
			
			$("#head-user-info span").css({
				"-moz-border-radius"	: 10 + "px",
				"-webkit-border-radius"	: 10 + "px"
			});
			
			$(".user-info").css({
				"margin-bottom" 	: 4 + "px",
				"background-color"	: "#ecf0f3",
				"color"				: "#62707d",
				"text-align"		: "center",
				"display"			: "inline-block",
				"font-size"			: 12 + "px",
				"font-family"		: "微软雅黑",
				"height"			: 21 + "px",
				"line-height"		: 22 + "px",
				
			});
			$("#main").css({"display":""});
		}else{
	
		}
	},"json");
}