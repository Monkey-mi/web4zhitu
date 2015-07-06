var admin=false,
	leftPanelW = 240, // 左面板宽度
	rightPanelW = 348, // 右面板宽度
	mainSlideDist = (rightPanelW - leftPanelW) / 2, // 主面板滑动距离
	mainOptSpace = 4, // 主面板操作边距
	mainOptW = 100,	//织图操作布局宽度
	mainSlideSpeed = 500, // 主面板滑动速度
	mainTopMargin = 20, // 主面板上边距
	htworldPadding = 7, // 织图边距
	containerWidth = 0,
	containerHeight = 0,
	isShowDesc = true,
	
	worldId = 0,
	authorId = 0,
	idLoadingComment = false,
	commentMaxId = 0,
	commentTotal = 0,
	commentPage = 0,
	commentPageStart = 1,
	commentPageLimit = 10,
	$commentScrollPanel,
	
	shareHead = 'http://s.share.baidu.com/?uid=6844316&searchPic=0&sign=off&type=text&title=不一样的多图分享，点击体验&desc=', //分享头部
	shareURL, // 分享URL
	
	worldSettings;

$(document).ready(function() {
	var winHeight = $(window).height(),
	htworldHeight = winHeight - mainTopMargin*2 - htworldPadding*2,
	htworldWidth = htworldHeight;

	containerWidth = htworldWidth + htworldPadding*2,
	containerHeight = htworldHeight + htworldPadding*2;
		
	var scale = htworldWidth / 640,
		width = htworldWidth,
		height = htworldHeight,
		radiu = parseInt(40 * scale),
		config = {w:width,h:height,r:radiu,s:scale,key:"zhangjiaxin"};
	
	$(".zt-desc").css({'top':(containerHeight - 100) + 'px'});
	
	$(".zt-wraper")
		.css({width:config.w + 'px'})
		.css({height:config.h + 'px'});
	$(".zt-container")
		.css({width:config.w + 'px'})
		.css({height:config.h + 'px'});
	worldSettings = {
		adminKey			: config.key,
		worldId 			: 11715, // 浏览的世界ID
		scale 				: config.s, // 缩放比例
		width 				: config.w, // 显示框宽度
		height 				: config.h, // 显示宽长度
		radius				: config.r,	//圆圈半径
		limit 				: 10, // 每页查询条数
		url 				: '/hts/ztworld/ztworld_queryTitleChildWorldPage', // 数据获取地址
		loadMoreURL			: '/hts/ztworld/ztworld_queryChildWorldPage', // 加载更多子世界地址
		inZoomfactor 		: 5, // 背景图渐变过程中放大的倍数
		inSpeedfactor 		: 650, // 渐变速度
		inImgdelayfactor 	: 3, // 渐变延迟时间
		outZoomfactor 		: 3, // 背景图渐变过程中放大的倍数
		outSpeedfactor 		: 650, // 渐变速度
		tagTopPadding		: 1.5*scale,
		outImgdelayfactor 	: 0,
	};
	
	var $window = $(window),
		scrollTop = $window.scrollTop(),
		currentTop = scrollTop,
		index = Math.round(scrollTop/containerHeight),
	$currentWorld = $('.zt-container').eq(index);
	
	worldSettings.worldId = $currentWorld.data("id");
	$currentWorld.htszoomtour(worldSettings);
	
	$(window).scroll(function(data) {
		var scrollTop = $window.scrollTop(),
			differ = scrollTop - currentTop;
		currentTop = scrollTop;
		var i = Math.round(scrollTop/containerHeight);
		if((differ > 0 && i > index) || (differ < 0 && i < index)) {
			$currentWorld.htszoomtour('pause'); // 停止当前织图
			index = i;
			$currentWorld = $('.zt-container').eq(index);
			if(!$currentWorld.htszoomtour('getInitState')) {
				worldSettings.worldId = $currentWorld.data("id");
				$currentWorld.htszoomtour(worldSettings);
			} else {
				$currentWorld.htszoomtour('resume');
			}
			
		}
	} );
});