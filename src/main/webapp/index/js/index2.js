var worldSettings;
	
$(document).ready(function() {
	
	$('#icon-wechat').fancybox({
		width				: 376,
		height				: 231,
		'overlayColor' 		: "#000000", //要指定黑色背景，
		'overlayOpacity' 	: 0.8, 
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe',
		'autoScale'			: false,
		'padding'			: 0,
		'titleShow'			: false
	});
	var scale = 464 / 640,
		width = 464,
		height = 464,
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
	
	$.post("operations/ztworld_querySquarePushIndex",{
		"trimLabel":true,
		"trimNormal":true,
		"trimActivity":true,
		"superbLimit":30
	}, function(result){
		if(result['result'] == 0) {
			var $carousel = $("#carousel-box").find('.overview:eq(0)');
			var worlds = result['htworld'];
			for(var i = 0; i < worlds.length; i++) {
				var $img = $('<img class="img-loadworld" data-id="'+worlds[i]["id"]+'" data-ver="'+worlds[i]["ver"]
					+'" data-desc="'+worlds[i]["worldDesc"]+'" data-title="'+worlds[i]['titlePath']
					+'" src="' + worlds[i]["titleThumbPath"] + '" alt="" />');
				var $a = $("<a class='btn-loadworld' href='"+worlds[i]['worldURL']+"' onclick='loadWorld(" + i + ");return false;'></a>");
				var $bg = $("<div class='carousel-bg'></div>");
				$a.append($img);
				$a.append($bg);
				var $world = $("<li></li>");
					
				$world.append($a);
				$carousel.append($world);
				
				if(i == 0) {
					worldSettings.worldId = worlds[i]['id'];
					worldSettings.desc = worlds[i]['worldDesc'];
					$bg.addClass('carousel-highlight');
					if($img.data('ver') == 1) {
						$(".zt-title:eq(0)").children('img').attr('src', worlds[i]['titlePath']);
					}
					$(".zt-container:eq(0)").htszoomtour(worldSettings);
				}
			}
			$('#carousel-box').tinycarousel({
				animationTime: 200,
				infinite: false
			}).show();
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

