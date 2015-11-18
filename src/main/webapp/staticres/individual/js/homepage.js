function initLayout(s,scale){	
		$("#main").width(winWidth);
		$("#download-wrap").width(winWidth);
		$(".slide").css({
			"width"		: (winWidth-14) + "px"
		});
		$(".slide .div-slide-img").width(5*(winWidth-14));
		$(".slide .div-slide-img").click(function(){
			window.location.href="http://www.imzhitu.com";
		});
		
		$(".slide img").css({
			"width"					: (winWidth-14) + "px"	
		});
		
		$(".div-slide-btn").css({
			"width"		: (winWidth-14) + "px",
			"height"	: 10 + "px"
		});
		$("#head-user-info").css({
			"width"		: (winWidth - 83) + "px",
		});
		
		$("#user-info-concern-btn").css({
			"width" 			: (winWidth - 93) + "px"
		});
	
		queryUserInfo(s,scale);
		getMyWorldList(s,scale);
		
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
				$("#user-pic").append(htm);
			}
			
			if(worldList.length > 0){
					var imgW = parseInt((winWidth - 2*2 - 2*4 - 2*5)/3); 
					var mgrT = 2;
					var mgrR = 2;
					var mgrL = 2;
					var w    = parseInt(imgW * 3 + 2*2 + 2*4);
				if(worldList.length < 3){
					imgW = w;
					imgH = w;
					mgrR = 0;
					mgrL = 0;
					mgrT = 6;
				}
				
				$(".img-grid-style").css({
						"width"					: imgW  + "px",
						"margin-top"			: mgrT  + "px",
						"margin-bottom"			: mgrT  + "px", 
						"cursor"				: "pointer",
						"margin-left"			: mgrL  + "px",
						"margin-right"			: mgrR  + "px"
					});
				
				$("#user-pic").css({
					"width"			  : w + "px"
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
			$("#user-info-sex").text(result['sex']);
			
			var address = result['address'];
			if(address.length < 5){
				address = "地点" + address;
			}
			$("#user-info-address").css({
				"width"			: (winWidth -127)/2 + "px"
			});
			$("#user-info-address").text(address);
			
			var job = result['job'];
			if(job.length < 3){
				job = "职业:" + job;
			}
			$("#user-info-job").css({
				"width"			: (winWidth -127)/2 + "px"
			});
			$("#user-info-job").text(job);
			
			if( result['verifyName'].length > 0 ){
				$("#user-info-verify span").text(result['verifyName']);
				$("#verifyIcon").attr('src',result['verifyIcon']);
				
			} else{
				$("#user-info-verify").css({
					"display"	: "none"
				});
			}
			
			$("#user-info-picCount span").text(result['picCount']);
			$("#user-info-signature").text(result['signature']);
			$("#worldCount").text(result['worldCount']);
			$("#concernCount").text(result['concernCount']);
			$("#followCount").text(result['followCount']);
			$("#user-info-concern-btn").attr('href',"imzhitu://user?uid=" + result['userId']);
			
			$("#main").css({"display":""});
		}else{
	
		}
	},"json");
}