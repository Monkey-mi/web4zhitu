/*
 * 使用方法示例：
 * 
 * var $container = $('<div />');
 * ...
 * TODO many operations for $container
 * 
 * $container.appendtour({
 * 		'width':'宽度',
 *		'worldId':'织图id',
 *		'ver':'织图版本',
 *		'worldDesc':'织图描述',
 *		'titlePath':'封面路径',
 *		'url':'./admin_ztworld/ztworld_queryTitleChildWorldPage(首页子世界请求路径)',
 *		'loadMoreURL':'./admin_ztworld/ztworld_queryChildWorldPage(下一页子世界请求路径)'
 * });
 */
(function($) {
	var settings = {
		width : 320,
		worldId: 0,
		worldDesc: '',
		ver:1,
		titlePath:'',
		url:'',
		loadMoreURL:''
	},
	worldSettings = {
		adminKey			: 'zhangjiaxin',
		limit 				: 5, // 每页查询条数
		inZoomfactor 		: 5, // 背景图渐变过程中放大的倍数
		inSpeedfactor 		: 650, // 渐变速度
		inImgdelayfactor 	: 3, // 渐变延迟时间
		outZoomfactor 		: 3, // 背景图渐变过程中放大的倍数
		outSpeedfactor 		: 650, // 渐变速度
		outImgdelayfactor 	: 0,
		multiple			: true
	},
	/**
	 * 插件开放接口
	 */
	methods = {
		init : function( options ) {
			if( this.length ) {
				return this.each(function() {
					if (options) {
						$.extend(settings, options);
					}
					worldSettings.worldId = settings.worldId;
					worldSettings.width = settings.width;
					worldSettings.height = settings.width;
					worldSettings.scale = settings.width / 640;
					worldSettings.tagTopPadding = 1.5*worldSettings.scale;
					worldSettings.radius = parseInt(40 * worldSettings.scale);
					worldSettings.url = settings.url;
					worldSettings.loadMoreURL = settings.loadMoreURL;
					var $parent = $(this);
					var $zoomtour = 
						$('<div class="zt-container">'
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
						+'</div>');
					$zoomtour.css({'width':worldSettings.width, 'height':worldSettings.height});
					$parent.append($zoomtour);
					if(settings.ver == 1) {
						$zoomtour.find(".zt-title:eq(0)").show().children('img').attr('src', 
								settings.titlePath);
					}
					$zoomtour.htszoomtour(worldSettings);
				});
			}
		}
	};
	
	$.fn.appendtour = function(method) {
		if ( methods[method] ) {
			return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
		} else if ( typeof method === 'object' || ! method ) {
			return methods.init.apply( this, arguments );
		} else {
			$.error( '方法： ' +  method + ' 不存在' );
		}
	};
	
})(jQuery);