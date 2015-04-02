var worldSettings;
var anim = false;
var ltie9 = false;
$(document).ready(function() {
	ltie9 = $.browser.msie && parseInt($.browser.version) <= 8 ? true : false;
	var $posters = $("img.anim-img");
	var loaded		= 0,
		totalPosters	= $posters.length;
	$posters.one("load", function() {
		++loaded;
		if( loaded === totalPosters ) { //加载完最后一张图片以后
			posterAnim.startAnim();
			$(window).scroll(function() {
				if($(document).scrollTop() < 20) {
					posterAnim.startAnim();
				}
			});
		}
	}).each(function() {
	  if(this.complete) $(this).load();
	});
	
	var scale = 450 / 640,
	width = 450,
	height = 450,
	radiu = parseInt(36 * scale),
	config = {w:width,h:height,r:radiu,s:scale,key:"zhangjiaxin"};
	
	worldSettings = {
		adminKey			: config.key,
		worldId 			: 11704, // 浏览的世界ID
		scale 				: config.s, // 缩放比例
		width 				: config.w, // 显示框宽度
		height 				: config.h, // 显示宽长度
		radius				: config.r,	//圆圈半径
		limit 				: 5, // 每页查询条数
		url 				: '/hts/ztworld/ztworld_queryTitleChildWorldPage', // 数据获取地址
		loadMoreURL			: '/hts/ztworld/ztworld_queryChildWorldPage', // 加载更多子世界地址
		inZoomfactor 		: 5, // 背景图渐变过程中放大的倍数
		inSpeedfactor 		: 650, // 渐变速度
		inImgdelayfactor 	: 3, // 渐变延迟时间
		outZoomfactor 		: 3, // 背景图渐变过程中放大的倍数
		outSpeedfactor 		: 650, // 渐变速度
		tagTopPadding		: 1.5*scale,
		outImgdelayfactor 	: 0,
		ver					: 1,
	};
	loadworld(0);
});

var posterAnim = {
	startAnim : function() {
		if(!anim) {
			anim = true;
			posterAnim.hideAllAnimImg();
			posterAnim.showBlink1();
		}
	},
	showBlink1 : function() {
		if(!ltie9) {
			$("#blink-fore1").addClass('tag-blink-fore');
			$("#blink-back1").addClass('tag-blink-back');
			setTimeout(function() {
				posterAnim.showClickUp1();
			}, 1500);
		} else {
			posterAnim.showClickUp1();
		}
	},
	showClickUp1 : function() {
		if(!ltie9) {
			$("#click-up-1").fadeIn();
		} else {
			$("#click-up-1").show();
		}
		setTimeout(function() {
			$("#click-up-1").hide();
			posterAnim.showClickDown1();
		}, 500);
	},
	
	showClickDown1 : function() {
		$("#click-down-1").show();
		setTimeout(function() {
			if(!ltie9) {
				$("#click-down-1").fadeOut();
			} else {
				$("#click-down-1").hide();
			}
		}, 500);
		posterAnim.showLight1();
	},
	showLight1 : function() {
		if(!ltie9) {
			$("#light1").fadeIn('600');
		} else {
			$("#light1").css({'width':'0px', 'height':'0px', 'display':'inline-block','margin-left':'253px', 'margin-top':'372px'});
			$("#light1").animate({
				'width': '260px',
				'height' : '282px',
				'margin-top':'110px',
				'margin-left':'13px'}, 300);
		}
		setTimeout(function() {
			posterAnim.showAixin();
		}, 500);
	},
	showAixin : function() {
		$("#aixin-l").css({'width':'0px', 'height':'0px', 'display':'inline-block', 'margin-left':'147px', 'margin-top':'236px'});
		$("#aixin-l").animate({
			'width': '294px',
			'height' : '272px',
			'margin-left':'0px',
			'margin-top':'100px'}, 500, function() {
				posterAnim.showStarS();
			});
	},
	showStarS : function() {
		if(!ltie9) {
			$("#star-s-wrap").show();
			$("#blink-fore2").addClass('tag-blink-fore');
			$("#blink-back2").addClass('tag-blink-back');
			setTimeout(function() {
				posterAnim.showClickUp2();
			},1500);
		} else {
			$("#star-s-wrap").show();
			posterAnim.showClickUp2();
		}
		
		
	},
	showClickUp2 : function() {
		if(!ltie9) {
			$("#click-up-2").fadeIn();
		} else {
			$("#click-up-2").show();
		}
		setTimeout(function() {
			$("#click-up-2").hide();
			posterAnim.showClickDown2();
		}, 500);
	},
	
	showClickDown2 : function() {
		$("#click-down-2").show();
		setTimeout(function() {
			if(!ltie9) {
				$("#click-down-2").fadeOut();
			} else {
				$("#click-down-2").hide();
			}
		}, 500);
		posterAnim.showLight2();
		
	},
	showLight2 : function() {
		if(!ltie9) {
			$("#light2").fadeIn('500',function() {
				posterAnim.showStarL();
			});
		} else {
			$("#light2").css({'width':'0px', 'height':'0px', 'display':'inline-block','margin-left':'179px', 'margin-top':'140px'});
			$("#light2").animate({
				'width': '236px',
				'height' : '140px',
				'margin-top':'10px',
				'margin-left':'170px'}, 300, function() {
					posterAnim.showStarL();
				});
			
		}
	},
	showStarL : function() {
		$("#star-l").css({'width':'0px', 'height':'0px', 'display':'inline-block','margin-left':'345px', 'margin-top':'85px'});
		$("#star-l").animate({
			'width': '176px',
			'height' : '170px',
			'margin-left':'257px',
			'margin-top':'0px'}, 500, function() {
				if(!ltie9) {
					$("#blink-fore1, #blink-fore2").removeClass('tag-blink-fore');
					$("#blink-back1, #blink-back2").removeClass('tag-blink-back');
				}
				anim = false;
			});
	},
	hideAllAnimImg : function() {
		$(".poster-anim").hide();
	},
};

function loadworld(index) {
	$(".load-highlight:eq(0)").removeClass("load-highlight");
	var $a = $(".load-btn").eq(index);
	$a.addClass("load-highlight");
	var title = $a.attr("title");
	$("#demo-desc").text(title);
	$(".zt-item").remove();
	worldSettings.worldId = $a.data('id');
	$("#zt-progress").text(0);
	$(".zt-container:eq(0)").data('init', false).htszoomtour(worldSettings);
}