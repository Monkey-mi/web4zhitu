var worldSettings;

$(document).ready(function() {

var scale = 320 / 640,
width = 320,
height = 320,
radiu = parseInt(40 * scale),
config = {w:width,h:height,r:radiu,s:scale,key:"zhangjiaxin"};

worldSettings = {
	adminKey			: config.key,
	worldId 			: 11704, // 浏览的世界ID
	scale 				: config.s, // 缩放比例
	width 				: config.w, // 显示框宽度
	height 				: config.h, // 显示宽长度
	radius				: config.r,	//圆圈半径
	limit 				: 5, // 每页查询条数
	url 				: './ztworld/ztworld_queryTitleChildWorldPage', // 数据获取地址
	loadMoreURL			: './ztworld/ztworld_queryChildWorldPage', // 加载更多子世界地址
	inZoomfactor 		: 5, // 背景图渐变过程中放大的倍数
	inSpeedfactor 		: 650, // 渐变速度
	inImgdelayfactor 	: 3, // 渐变延迟时间
	outZoomfactor 		: 3, // 背景图渐变过程中放大的倍数
	outSpeedfactor 		: 650, // 渐变速度
	tagTopPadding		: 1.5*scale,
	outImgdelayfactor 	: 0,
	multiple			: true,
};

$.post("operations/ztworld_querySquarePushIndex",{
	"trimLabel":true,
	"trimNormal":true,
	"trimActivity":true,
	"superbLimit":9
}, function(result){
	if(result['result'] == 0) {
		$("#superb-title").show();
		var worlds = result['htworld'];
		var $superbBox = $('#superb-box');
		for(var i = 0; i < worlds.length; i++) {

			var setting = worldSettings;
			setting.worldId = worlds[i]['id'];
			setting.desc = worlds[i]['worldDesc'];
			setting.ver = worlds[i]['ver'];

			var $superb = $(
					'<div class="superb"><div class="zt-container">'
					+'<div class="zt-title">'
					+'	<img alt="">'
					+'</div>'
					+'<div class="zt-desc-hide" title="隐藏描述"></div>'
					+'<div class="zt-desc-show" title="显示描述"></div>'
					+'<div class="zt-desc">'
					+'	<div class="zt-desc-text"></div>'
					+'	<div class="zt-desc-bg"></div>'
					+'</div>'
					+'<div class="zt-loading">'
					+'<div class="zt-loading-bg"></div>'
					+'<div class="zt-loading-icon"></div>'
					+'<div><span>加载中...</span><span id="zt-progress">0</span><span>%</span></div>'
					+'</div>'
					+'</div></div>');
			if(((i+1)%3) != 0)
				$superb.addClass('superb-margin');
			$superbBox.append($superb);
			if(worldSettings.ver == 1) {
				$superb.find(".zt-title:eq(0)").show().children('img').attr('src', $img.data('title'));
			}
			$superb.find(".zt-container:eq(0)").htszoomtour(setting);


		}
	}

}, "json");
});

function loadWorld(index) {
	var $a = $(".btn-loadworld").eq(index),
		$img = $a.children('img:eq(0)'),
		$bg = $a.children('.carousel-bg:eq(0)');
	$(".zt-item").remove();
	worldSettings.worldId = $img.data('id');
	worldSettings.desc = $img.data('desc');
	worldSettings.ver = $img.data('ver');
	$('.carousel-highlight').removeClass('carousel-highlight');
	$bg.addClass('carousel-highlight');
	if(worldSettings.ver == 1) {
		$(".zt-title:eq(0)").show().children('img').attr('src', $img.data('title'));
	}
	$(".zt-container:eq(0)").data('init', false).htszoomtour(worldSettings);
}

