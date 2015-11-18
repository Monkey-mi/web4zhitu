$(document).ready(function() {
	 //获取channelId
	 	var url = window.location.href;
	 	var channelId1 = url.substring(url.lastIndexOf("?")+1,url.length);
	 	var end = channelId1.length;
	 	if(channelId1.indexOf("&") != -1){
	 		end = channelId1.indexOf("&");
	 	}
	 	var channelId = channelId1.substring(channelId1.indexOf("=")+1,end);
	 	
	 	$.post("/operations/channel_getChannelSharePageInfo",{
	 		channelId:channelId
	 	},function(data){
	 		if(data.result == 0){
	 		var result = data['obj'];
	 		var channelName = result.opChannelDetail.channelName; 
	 		var channelDesc = result.opChannelDetail.channelDesc;
	 		var worldCount = result.opChannelDetail.worldCount;
	 		var superbCount = result.opChannelDetail.superbCount;
	 		var memberCount = result.opChannelDetail.memberCount;
	 		var banner = result.opChannelDetail.banner;
	 		var imgs;
	 		var starListInfo;
	 		
	 		//循环加入图片
	 		if(result.opChannelWorldDto.length > 0){
	 		for(var i = 0; i < result.opChannelWorldDto.length; i++ ){
	 			imgs  += '<a id="img'+ result.opChannelWorldDto[i].id +' " href="http://imzhitu.com/DT' +result.opChannelWorldDto[i].shortLink + '"><img  alt="" src=" ' + (result.opChannelWorldDto[i].titleThumbPath).replace(".middleImage","") + ' "></a>' 
	 		}
	 		}else{
	 			alert('还没有产生精选奥。');
	 		}
	 		
	 		//循环加入红人头像
	 		if(result.starList.length > 0){
	 		for(var i = 0; i < result.starList.length; i++ ){
	 			starListInfo  += '<img class="userAvatar"  alt="" src=" ' + result.starList[i].userAvatar + ' ">'
	 		}
	 		}else{
	 			starListInfo= '';
	 		}
	 		
			//修改文件描述
			 var oMeta = document.getElementsByTagName('meta')[3];
			 oMeta.content = channelDesc;
			 
				 //设置标题
	 			$("title").text(channelName);
			 	$("#wx_pic").append("<img src='" + banner + "' />");
				$("#activityLoading").append('<div id="concernDataShow"  class="concernDataShow" ><span style="color:#ffab08">' + changeNumber(memberCount) + '</span>人关注</div>');
				$("#activityLoading").append('<div id="header"><img alt="" src=" ' + banner + ' " /> </div>');
				$("#activityLoading").append('<div id="channelIntro"  class="channelIntro" >' + channelDesc + '</div>');
				if(result.starList.length > 0){//如果有红人推荐，则显示出来
					$("#activityLoading").append('<div class="starList"><div class="starListFont">达人推荐</div>'
						+'<div class="starListUserAvatorOut"> <div class="starListUserAvatorIn">'+starListInfo+'</div> </div></div>');
				}
				$("#activityLoading").append('<div id="dataShow"  class="dataShow"><div id="superbDataShow" class="superbDataShow" ><span>' + changeNumber(superbCount) + '<br>精选</span></div>' + 
														'<div id="stickerDataShow"  class="stickerDataShow" ><span>' + changeNumber(worldCount) + '<br>参与</span></div></div>');
				$("#activityLoading").append('<div id="preShowPicForWtworld"  class="preShowPicForWtworld" >' + imgs+ '</div>');
				initLayout(); 
	 		}else{
	 			alert("数据返回错误");
	 		}
	 	},"json");
	 

	$("#download-wrap").show();
 	
});
 
 function changeNumber(number){
	 if (number > 10000){
		 number = (number / 1000).toFixed(1) ;
		 number = number + 'k';
	 }
	 return number;
 }

function initLayout() {
	
	var winWidth = $(window).width();
	var scale = winWidth / 320;
	var picWidth = initPic(winWidth,100,3,4,7);
	var slidWidth = 306 *  scale;
	var slidHeight = 142 *  scale;
	var mainWidth = winWidth - 14;
	
	zBase.init(mainWidth,1000,3000,'div-slide','div-slide-img','div-slide-btn');
	
	$(".preShowPicForWtworld img").css({
		"width":picWidth + "px",
		"height":picWidth + "px",
	});
	
	$("#div-propaganda").css({
		"padding-top":3 * scale + "px",
		"padding-bottom":4 * scale + "px"
	}).show();
	
	$(".slide").css({
		"width":mainWidth + "px",
		"height":142 * (mainWidth/306) + "px",
		"border"	: "1px solid #ffffff",
		"-moz-border-radius"	: 8 * scale + "px",
		"-webkit-border-radius"	: 8 * scale + "px",
		"border-radius"	: 8 * scale + "px"
	});
	
	$(".slide .div-slide-img").css({
		"width":mainWidth * 5 + "px"
	});
	
	$(".slide img").css({
		"width":mainWidth + "px"
	});
	
	$(".div-slide-btn").css({
		"width":mainWidth + "px",
		"height":10 * scale + "px",
		"line-height":10 * scale + "px",
		"bottom":10 * scale + "px",
	});
	
	 $(".concernDataShow").css({
		"margin-top":75 * (winWidth/320) + "px",
	}); 
	
/* 	 $(".officeButton").show(); */
	 $("#div-propaganda").show();
	
	$("#download-wrap").show();
	
	$("#download-logo").show();
	
	$("#download-close").show();
	
	$("#download-tip").show();
	
/*	$("#slogan").css({
		"display"		: "inline-block",
		"vertical-align": "top",
		"position"		: "absolute",
		"left"			: 60 +"px"
	});
	
	$("#zhitu-name").css({
		"display"	: "block",
		"font-size"	: 15 + "px",
		"color"		: "#373e46",
		"margin-top": 7 + "px",
		"text-align": "left"
	});
	
	$("#zhitu-slogan").css({
		"display"	: "block",
		"font-size"	: 12 + "px",
		"color"		: "#959da5",
		"margin-top": 3 + "px"
	});*/
}

//根据参数计算出展示图片的尺寸
/*  
 * @winWidth 屏幕宽度
 * @picWidth 图片宽（附加margin大小）
 * @picMarginLeft 图片左边距
 * @mainLeft&mainRight 左右侧距离
 */
function initPic(winWidth,picWidth,picMarginLeft,mainLeft,mainRight){
	var pic = picWidth;
	if(winWidth != 320){
		pic = (winWidth - 3*picMarginLeft - mainLeft - mainRight) / 3;
		pic = parseInt(pic);
	}
	return pic;
}

function hideDownloadBtn() {
	$("#download-wrap").hide();
	$("#main").css({"margin-bottom":"0px"});
}