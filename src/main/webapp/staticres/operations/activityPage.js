
 $(document).ready(function() {
	 //获取channelId
	 	var url = window.location.href;
	 	var activityId1 = url.substring(url.lastIndexOf("?")+1,url.length);
	 	var end = activityId1.length;
	 	if(activityId1.indexOf("&") != -1){
	 		end = activityId1.indexOf("&");
	 	}
	 	var activityId = activityId1.substring(activityId1.indexOf("=")+1,end);
	 	
	 	$.post("/operations/activity_getActivityPageInfo",{
	 		aid:activityId
	 	},function(data){
	 		if(data.result == 0){
	 		var result = data['obj'];
	 		var activityName = result.labelName; 
	 		var activityDesc = result.opActivity.activityDesc;
	 		var activityRemainDay = result.remianDay > 0 ? result.remianDay : 0;
	 		 var activityCount = result.activityCount; 
	 		/* var worldCount = result.opActivity.worldCount; */
	 		var banner = result.opActivity.titlePath;
	 		var imgs;
	 		var starListInfo;
	 		
	 		//循环加入图片
	 		if(result.htWorlds.length > 0){
	 		for(var i = 0; i < result.htWorlds.length; i++ ){
	 			imgs  += '<a id="img'+ result.htWorlds[i].id +' " href="http://imzhitu.com/DT' +result.htWorlds[i].shortLink + '"><img  alt="" src=" ' + (result.htWorlds[i].titleThumbPath).replace(".middleImage","") + ' "></a>' 
	 		}
	 		}else{
	 			alert('还没有人参加活动奥，快来参加吧');
	 		}
	 		
	 		//循环加入红人头像
	 		if(result.opActivityStars.length > 0){
	 		for(var i = 0; i < result.opActivityStars.length; i++ ){
	 			starListInfo  += '<img class="userAvatar" alt="" src=" ' + result.opActivityStars[i].userAvatar + ' ">'
	 		}
	 		}else{
	 			starListInfo= '';
	 		}
	 		
			//修改文件描述
			 var oMeta = document.getElementsByTagName('meta')[3];
			 oMeta.content = activityDesc;
			 
				 //设置标题
	 			$("title").text(activityName);
	 		 	$("#wx_pic").append("<img src='" + banner + "' />");
	 		 	
	 			$("#activityLoading").append('<img style="display:none" alt="" src=" ' + banner + ' " />');
				$("#activityLoading").append('<div id="concernDataShow"  class="concernDataShow" >剩余<span style="color:#ffab08">' + activityRemainDay + '</span>天</div>');
				$("#activityLoading").append('<div id="header"><img alt="" src=" ' + banner + ' " /> </div>');
				$("#activityLoading").append('<div id="channelIntro"  class="channelIntro" >' + activityDesc + '</div>');
				if(result.opActivityStars.length > 0){//如果有红人推荐，则显示出来
					$("#activityLoading").append('<div class="starList"><div class="starListFont">活动红人</div>'
							+'<div class="starListUserAvatorOut"> <div class="starListUserAvatorIn">'+starListInfo+'</div> </div></div>');
				}
	 			$("#activityLoading").append('<div class="activityNumber" ><div style="position: absolute;">活动织图</div>'
	 				+'<div style="text-align:right">参与<span style="color:red;">' + changeNumber(activityCount) + '</span>人</div></div>');
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
		"margin-top":140 * (winWidth/320) + "px",
	}); 
	
/*	 $(".officeButton").show();*/
	$("#div-propaganda").show();
	
	$("#div-propaganda").show();
		
	$("#download-wrap").show();
		
	$("#download-logo").show();
		
	$("#download-close").show();
		
	$("#download-tip").show();
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