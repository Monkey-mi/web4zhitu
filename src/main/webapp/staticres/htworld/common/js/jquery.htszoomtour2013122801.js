/*
 * jQuery htszoomtour v1.0
 *
 * Copyright © 2012 www.imzhitu.com All rights reserved.
 * 
 * Email:imzhitu@163.com 
 * 
 * Author: zhutianjie(zhutianjie89@gmail.com)
 * 
 */
(function($) {
	var settings = {
		adminKey		: '', // 管理key
		worldId			: 1, //查询的世界ID
		limit			: 10, //每页查询的最大条数
		maxContainWidth : 640, // 播放容器最大宽度
		maxContainHeight: 960, // 播放容器最大高度
		maxTagSize		: 50,
		scale 			: 1, //页面缩放比例
		url				: '', //数据访问的URL
		init			: function(ds){}, //初始化过程同时进行的函数
		rotation		: false,	// 设置tag是否会被旋转
		zoominEasing	: '',  	// 放大进入动画. ex: easeOutBounce
		zoomoutEasing	: '',	// 放大消失动画
		width			: 0, //展示宽宽度
		height			: 0, //展示宽高度
		inZoomfactor		: 3, // 背景图渐变过程中放大的倍数
		inSpeedfactor		: 250, // 渐变速度
		inImgdelayfactor  : 0, // 渐变延迟时间
		
		
		outZoomfactor		: 3, // 背景图渐变过程中放大的倍数
		outSpeedfactor		: 250, // 渐变速度
		outImgdelayfactor  : 0, // 渐变延迟时间
		onBack			: function(){},
	
		phoneType		: 'ios', //手机类型
		radius			: 40,	//圆圈半径
		arrowPathPrefix : 'http://oss.aliyuncs.com/imzhitu/images/arrow-v2/arrow-',
		arrowPathSuffix : '.png'
	},
	cache = null,
	viewedList = [], // 保存查看过的ItemID队列
	tag	= {
		/**
		 * 初始化缩略图属性
		 */
		saveInitialData	: function( $tag ) {
			$tag.data({
				width	: $tag.width(),
				height	: $tag.height(),
				left	: $tag.position().left,
				top		: $tag.position().top
			}).addClass( $tag.data('link') );
		},
		/**
		 * 旋转属性
		 */
		rotate	: function( $tag, cache ) {	
			// 获取元素的中点
			var center	= {
				x	: $tag.position().left + $tag.width() / 2,
				y	: $tag.position().top + $tag.height() / 2
			};
			
			var quadrant	= tag.getElementQuadrant( center, cache );
			// distance from element's center to the quadrants origin
			var dist_element = null;
			switch( quadrant ) {
				case 1 :
					dist_element = Math.sqrt( Math.pow( ( center.x - 0 ), 2 ) + Math.pow( ( center.y - 0 ), 2 ) );
					break;
				case 2 :
					dist_element = Math.sqrt( Math.pow( ( center.x - cache.ztdim.x ), 2 ) + Math.pow( ( center.y - 0 ), 2 ) );
					break;
				case 3 :
					dist_element = Math.sqrt( Math.pow( ( center.x - 0 ), 2 ) + Math.pow( ( center.y - cache.ztdim.y ), 2 ) );
					break;
				case 4 :
					dist_element = Math.sqrt( Math.pow( ( center.x - cache.ztdim.x ), 2 ) + Math.pow( ( center.y - cache.ztdim.y ), 2 ) );
					break;
			}
			var anglefactor = 25;
			var angle	= ( ( cache.dist_center - dist_element ) / cache.dist_center ) * anglefactor;
			
			switch( quadrant ) {
				case 1 :
					$tag.data( 'rotate', angle ).transform({'rotate'	: angle + 'deg'});
					break;
				case 2 :
					$tag.data( 'rotate', -angle ).transform({'rotate'	: -angle + 'deg'});
					break;
				case 3 :
					$tag.data( 'rotate', -angle ).transform({'rotate'	: -angle + 'deg'});
					break;
				case 4 :
					$tag.data( 'rotate', angle ).transform({'rotate'	: angle + 'deg'});
					break;
			}
		},
		getElementQuadrant	: function( c, cache ) {
			if( c.x <= cache.ztdim.x / 2 && c.y <= cache.ztdim.y / 2 ) 
				return 1;
			else if( c.x > cache.ztdim.x / 2 && c.y <= cache.ztdim.y / 2 ) 
				return 2;	
			else if( c.x <= cache.ztdim.x / 2 && c.y >= cache.ztdim.y / 2 ) 
				return 3;
			else if( c.x > cache.ztdim.x / 2 && c.y > cache.ztdim.y / 2 ) 
				return 4;
		}
	},
	
	image = {
		/**
		 * 初始化图片数据，包括，宽、高等
		 */
		saveInitialData : function( $image, cache) {
			var imgWidth = $image.attr('width'),
				imgHeight = $image.attr('height');
			
			var imgCenterX = cache.ztdim.x / 2 - imgWidth / 2,
				imgCenterY = cache.ztdim.y / 2 - imgHeight / 2;
				
			$image.css({
				width	: imgWidth,
				height	: imgHeight,
				left 	: imgCenterX,
				top 	: imgCenterY
			});
			$image.data({
				width	: imgWidth,
				height	: imgHeight,
				centerX : imgCenterX,
				centerY : imgCenterY
			});
		}
	},
	
	/**
	 * 特效接口
	 */
	viewport = {
		enter : function( $ztcontainer, $tag, cache, settings ) {
			var $ztitem			= $tag.closest('div.zt-item'),
				ztitemid		= $ztitem.data('id'),
				$ztimage		= $ztitem.children('img.zt-current'),
				
				zoomfactor		= settings.inZoomfactor, // 背景图渐变过程中放大的倍数
				speedfactor		= settings.inSpeedfactor, // 渐变速度
				imgdelayfactor 	= settings.inImgdelayfactor, // 渐变延迟时间
				link			= $tag.data('link'),
				
				$new_ztitem		= $ztcontainer.find('div.' + link),
				$new_ztitemimage= $new_ztitem.find('img.zt-current'),
				zoomAnimation	= true;
			if($new_ztitemimage.size() == 0) { // 对应的large image还没有加载
				//获取数据并更新页面元素
				hts.refetch($ztcontainer, $tag, $tag.data('toid'));
			} else {
				viewedList.push(link);
				viewport.zoomin( $tag, $ztitem, ztitemid, $ztimage, zoomfactor, speedfactor, imgdelayfactor, zoomAnimation, $new_ztitem, $new_ztitemimage, cache, settings );
			}
				
		},
		
		back : function( $ztcontainer, cache, settings ) {
			var viewLen 		= viewedList.length;
			if( viewLen < 2 ) {
				viewport.showTip();
				return false;
			}
			var ztitemid		= viewedList.pop(),
				$ztitem			= $ztcontainer.find('div.' + ztitemid),
				$ztimage		= $ztitem.children('img.zt-current'),
				
				zoomfactor		= settings.outZoomfactor, // 背景图渐变过程中放大的倍数
				speedfactor		= settings.outSpeedfactor, // 渐变速度
				imgdelayfactor 	= settings.outImgdelayfactor, // 渐变延迟时间
				
				$new_ztitem		= $ztcontainer.find('div.' + viewedList[viewLen - 2]),
				$new_ztitemimage= $new_ztitem.find('img.zt-current'),
				zoomAnimation	= true;
				
			viewport.zoomout( $ztitem, ztitemid, $ztimage, zoomfactor, speedfactor, imgdelayfactor, zoomAnimation, $new_ztitem, $new_ztitemimage, cache, settings );
			settings.onBack();
		},
		
		/**
		 * 返回第一层
		 * @param $ztcontainer
		 * @param cache
		 * @param settings
		 * @returns {Boolean}
		 */
		back2first : function( $ztcontainer, cache, settings ) {
			if(cache.animTour) {
				return;
			}
			
			var viewLen 		= viewedList.length;
			if( viewLen < 2 ) {
				viewport.showTip();
				return false;
			}
			var ztitemid		= viewedList.pop(),
				$ztitem			= $ztcontainer.find('div.' + ztitemid),
				$ztimage		= $ztitem.children('img.zt-current'),
				
				zoomfactor		= settings.outZoomfactor, // 背景图渐变过程中放大的倍数
				speedfactor		= settings.outSpeedfactor, // 渐变速度
				imgdelayfactor 	= settings.outImgdelayfactor, // 渐变延迟时间
				
				$new_ztitem		= $ztcontainer.find('div.' + viewedList[0]),
				$new_ztitemimage= $new_ztitem.find('img.zt-current');
			

			cache.animTour	= true;
			
			var newList = [];
				newList[0] = viewedList[0],
				viewedList = [],
				viewedList = newList;

				$ztitem.hide();
				$new_ztitem.show(function() {
					$new_ztitem.children('div.zt-tag').show();
					cache.animTour	= false;
				});
				
		},
		/**
		 * 缩放出现特效
		 */
		zoomin : function( $tag, $ztitem, ztitemid, $ztimage, zoomfactor, speedfactor, imgdelayfactor, zoomAnimation, $new_ztitem, $new_ztitemimage, cache, settings ) {
			var 
				imgWidth = $new_ztitemimage.data('width'),
				imgHeight = $new_ztitemimage.data('height'),
				centerX = $new_ztitemimage.data('centerX'),
				centerY = $new_ztitemimage.data('centerY'),
				el_l = cache.ztdim.x / 2,
				el_t = cache.ztdim.y / 2,
				newDesc = $new_ztitemimage.data('desc');
			
			
			$.fn.applyStyle = ( zoomAnimation ) ? $.fn.animate : $.fn.css;
			
			$ztimage.applyStyle( {
				width	: imgWidth * zoomfactor + 'px',
				height	: imgHeight * zoomfactor + 'px',
				left	: - el_l + 'px',
				top		: - el_t + 'px'
			}, $.extend( true, [], { duration : speedfactor } ) );
			
			// 隐藏所有tags
			$ztitem.children('div.zt-tag').hide();
			
			var imgAnimationCss	= {
				width	: imgWidth  + 'px',
				height	: imgHeight + 'px',
				left	: centerX   + 'px',
				top		: centerY   + 'px',
				opacity	: 1
			};
			if( settings.rotation && !cache.ieLte8 )
				imgAnimationCss.rotate	= '0deg';
			
			var newztimg = $new_ztitemimage.attr('src');
			
			var tagCss	= {
				position	: 'absolute',
				width		: $tag.data('width'),
				height		: $tag.data('height'),
				left		: $tag.data('left'),
				top			: $tag.data('top')
			};
			if( settings.rotation && !cache.ieLte8 )
				tagCss.rotate = $tag.data('rotate') + 'deg';
			
			$ztitem.append(
				$('<img src="' + newztimg + '" class="zt-temp"></img>')
				.css( tagCss )
				.delay(imgdelayfactor)
				.applyStyle( imgAnimationCss, $.extend( true, [], { duration : speedfactor, easing : settings.zoominEasing, complete : function() {
					viewport.zoominCallback( $(this), $new_ztitem, $ztitem, $ztimage,newDesc, cache );
					$('img.zt-temp').remove();
				}}))
			);
			
			if( !zoomAnimation )
				viewport.zoominCallback( $ztitem.find('img.zt-temp'), $new_ztitem, $ztitem, $ztimage, newDesc, cache );
		},
		
		/**
		 * 缩放出现特效回调
		 */
		zoominCallback	: function( $zttemp, $new_ztitem, $ztitem, $ztimage, desc, cache) {
			
			var imgWidth = $ztimage.data('width'),
				imgHeight = $ztimage.data('height'),
				centerX = $ztimage.data('centerX'),
				centerY = $ztimage.data('centerY'),
				newDesc = $new_ztitem.data('desc');
		
			$(this).remove();

			$new_ztitem.show();
			$new_ztitem.children('div.zt-tag').show();
			
			$ztitem.hide();
			
			$ztimage.css({
				width	: imgWidth + 'px',
				height	: imgHeight + 'px',
				left	: centerX + 'px',
				top		: centerY + 'px'
			});
			viewport.refreshDesc(desc);
			
			cache.animTour	= false;
		},
		
		/**
		 * 缩放消失特效
		 */
		zoomout	: function($ztitem, ztitemid, $ztimage, zoomfactor, speedfactor, imgdelayfactor, zoomAnimation, $new_ztitem, $new_ztitemimage, cache, settings ) {
			
			if(cache.animTour) {
				return;
			}
			
			var $originalTag	= $new_ztitem.children( 'div.' + ztitemid ),
				o_tag_w			= $originalTag.data('width'),
				o_tag_h			= $originalTag.data('height'),
				o_tag_l			= $originalTag.data('left'),
				o_tag_t			= $originalTag.data('top'),
				o_tag_r			= $originalTag.data('rotate'),
			
				el_l 			= o_tag_l + o_tag_w / 2,
				el_t 			= o_tag_t + o_tag_h / 2,
				
				imgWidth 		= $ztimage.data('width'),
				imgHeight		= $ztimage.data('height'),
				imgCenterX 		= $ztimage.data('centerX'),
				imgCenterY		= $ztimage.data('centerY'),
				
				newImgWidth 	= $new_ztitemimage.data('width'),
				newImgHeight 	= $new_ztitemimage.data('height'),
				newImgCenterX	= $new_ztitemimage.data('centerX'),
				newImgCenterY	= $new_ztitemimage.data('centerY'),
				newDesc			= $new_ztitemimage.data('desc');
			
			$.fn.applyStyle 	= ( zoomAnimation ) ? $.fn.animate : $.fn.css;
			$new_ztitemimage.css({
				width	: cache.ztdim.x * zoomfactor + 'px',
				height	: cache.ztdim.y * zoomfactor + 'px',
				left	: - ( ( zoomfactor * ( 2 * el_l ) ) - ( 2 * el_l ) ) / 2 + 'px',
				top		: - ( ( zoomfactor * ( 2 * el_t ) ) - ( 2 * el_t ) ) / 2 + 'px'
			});
			
			$ztitem.hide();
			
			var $new_ztitem_tags = $new_ztitem.children('div.zt-tag');
			$new_ztitem_tags.hide();
			
			$new_ztitem.show();
			
			var expandCss	= {
				width	: imgWidth + 'px',
				height	: imgHeight + 'px',
				left	: imgCenterX + 'px',
				top		: imgCenterY + 'px',
				opacity	: 1
			};
			if( settings.rotation && !cache.ieLte8 )
				expandCss.rotate	= '0deg';
			
			var imgAnimationCss	= {
				width	: o_tag_w + 'px',
				height	: o_tag_h + 'px',
				left	: o_tag_l + 'px',
				top		: o_tag_t + 'px',
				opacity	: 0
			};
			if( settings.rotation && !cache.ieLte8 )
				imgAnimationCss.rotate	= o_tag_r + 'deg';
			
			$new_ztitem.append( 
				$('<img src="' + $ztimage.attr('src') + '" class="zt-temp"></img>').css(expandCss)
			);
			
			var $zttemp = $new_ztitem.find('img.zt-temp');
			
			$zttemp.applyStyle( imgAnimationCss, $.extend( true, [], { duration : speedfactor, complete : function() {
				$(this).remove();
			} } ) );
			
			if( !zoomAnimation ) 
				$zttemp.remove();
			
			$new_ztitemimage
			.delay( imgdelayfactor )
			.applyStyle( {
				width	: newImgWidth + 'px',
				height	: newImgHeight + 'px',
				left	: newImgCenterX + 'px',
				top		: newImgCenterY + 'px'
			}, $.extend( true, [], { duration : speedfactor, easing : settings.zoomoutEasing, complete : function() {
				viewport.zoomoutCallback( $new_ztitem_tags, cache, newDesc );
			} } ) );
			
			if( !zoomAnimation )
				viewport.zoomoutCallback( $new_ztitem_tags, cache, newDesc );
			
		},
		
		zoomoutCallback	: function( $new_ztitem_tags, cache, desc ) {
			$new_ztitem_tags.show();
			viewport.refreshDesc(desc);
			cache.animTour	= false;
		},
		
		showTip : function() {
			var $zttip = $("#zt-tip");
			if($zttip.size() == 0) {
				var $ztcontainer = $("#zt-container");
				$zttip = $("<div />").attr('id','zt-tip').text('已经在第一层');
				$ztcontainer.append($zttip);
			}
			$zttip.fadeIn();
			setTimeout(function() {
				$zttip.fadeOut(function() {
				});
			}, 1000);
		},
		
		refreshDesc : function(desc) {
			var $descTest = $(".zt-desc-text:eq(0)"),
				$desc = $(".zt-desc:eq(0)")
			if(desc == '' || desc == 'undefined') {
				$desc.hide();
			} else {
				$desc.show();
				$descTest.text(desc);
			}
		}
	},
	
	zoomtour = {
		refresh : function($ztcontainer,$loading,cache) {
			var $ztitems			= $ztcontainer.children('div.zt-new'),
				$ztimages			= $ztitems.children('img.zt-current'),
				$zttags				= $ztitems.children('div.zt-tag'),
				$showTagBtn			= $("#zt-show-tag-btn"),
				$hideTagBtn			= $("#zt-hide-tag-btn");
			// 为每个item添加值为"id"的class。稍后将会使用这些items.
			$ztitems.each( function() {
				var $ztitem	= $(this);
				$ztitem.addClass( $ztitem.data('id'))
				.removeClass('zt-new');
			});
			
			if( settings.rotation && !cache.ieLte8 )
				cache.dist_center		= Math.sqrt( Math.pow( ( cache.ztdim.x / 2 ), 2 ) + Math.pow( ( cache.ztdim.y / 2 ), 2 ) );
			
			$zttags.each(function() {
				var $tag	= $(this);
				// 保存每个tag的width、height、left、top
				tag.saveInitialData( $tag );
				// 旋转tags
				if( settings.rotation && !cache.ieLte8 && !$tag.hasClass('zt-tag-back') )
					tag.rotate( $tag, cache );
			}).hide(); // 隐藏所有tags
			
			$ztitems.hide();
			// 预先下载所有大图
			var $ztProgress = $("#zt-progress"),
				loaded		= 0,
				totalImages	= $ztimages.length;

			if($ztProgress != null && $ztProgress != 'undefined') {
				$ztProgress.text('0');
			}
			
			$ztimages.each( function() {
				var $image = $(this);
				image.saveInitialData($image, cache);
				$image.load(function() {
					++loaded;
					//如果图片加载失败，重新保存其参数
					if($image.attr('iserror') == 1) {
						image.saveInitialData($image, cache);
					}
					if($ztProgress != null && $ztProgress != 'undefined') {
						$ztProgress.text(parseInt(loaded * 100 / totalImages));
					}
					if( loaded === totalImages ) { //加载完最后一张图片以后
						
						// 显示所有缩略图（仅第一张可见）
						$ztimages.show();
						// 隐藏缓冲提示
						$loading.hide();
						// 显示缩略图
						$zttags.show();
						// 绑定缩略图点击事件
						$zttags.bind('click.zoomtour', function( e ) {
							if( cache.animTour ) return false;
							cache.animTour	= true;
							var $tag = $(this);
							viewport.enter( $ztcontainer, $tag, cache, settings );
						});
						$zttags.bind('mouseenter.zoomtour', function(e) {
							var $tag = $(this);
							$tag.children('.zt-tag-img:eq(0)').addClass('zt-tag-img-highlight');
						});
						$zttags.bind('mouseleave.zoomtour', function(e) {
							var $tag = $(this);
							$tag.children('.zt-tag-img:eq(0)').removeClass('zt-tag-img-highlight');
						});
						$showTagBtn.bind('click.zoomtour',function(e) {
							if( !cache.animTour ) {
								$zttags.show();
								$(this).hide();
								$hideTagBtn.show();
							}
						});
						
						$hideTagBtn.bind('click.zoomtour', function(e) {
							if( !cache.animTour ) {
								$zttags.hide();
								$(this).hide();
								$showTagBtn.show();
							}
						});
					}
					
				}).attr( 'src', $(this).attr('src') );
			});
		}
	},
	hts = {
		init : function($ztcontainer) {
			viewedList = [];
			$("#zt-tag-back,#zt-container").unbind('click.zoomtour');
			var $loading = $ztcontainer.children('div.zt-loading');
			$loading.show();
			$.post(settings.url, 
				{'start':1,
				 'limit':settings.limit,
				 'adminKey':settings.adminKey,
				 'worldId':settings.worldId
				 },function(ds){
					 if(ds['result'] == 0) {
							hts.parse($ztcontainer,ds['htworld'], ds['htworld']['childs'],function (ds) {
								
								settings.init(ds);
								
								// 更新页面
								zoomtour.refresh($ztcontainer,$loading,cache);
								var $ztitems = $ztcontainer.children('div.zt-item:eq(0)');
								viewport.refreshDesc($ztitems.find('img:eq(0)').data('desc'));
								// 显示第一个
								$ztitems.first().show();
								viewedList.push($ztitems.first().data('id'));
								
								
								//返回上一页
								$("#zt-tag-back,#zt-container").bind('click.zoomtour', function(e) {
									if( !cache.animTour ) {
										viewport.back( $ztcontainer, cache, settings );
									}
								});
								
							});
					 } else {
					 }
					
					
				 },'json' );
		},
		refetch : function($ztcontainer,$tag, childId) {
			var $loading = $ztcontainer.children('div.zt-loading');
			$loading.show();
			$.post(settings.loadMoreURL, 
				{'childId':childId,
				 'limit':settings.limit,
				 'worldId':settings.worldId
				 },function(ds) {
					 if(ds['result'] == 0) {
						 hts.parse($ztcontainer,ds['htworld'],ds['htworld']['childs'], function(ds) {
							// 更新页面
							zoomtour.refresh($ztcontainer,$loading,cache);
							viewport.enter($ztcontainer, $tag, cache, settings);
						 },'json' );
					 } else {
					 }
					
				 });
		},
		parse : function($ztcontainer,worldInfo, childWorlds,callback) {
			for(var index in childWorlds) {
				hts.appendChild($ztcontainer,childWorlds[index]);
			}
			callback(worldInfo);
		},
		/**
		 * 添加子世界
		 */
		appendChild : function($ztcontainer, child) {
			var childInfo = child['child'],
				childW = childInfo['width'],
				childH = childInfo['height'],
				desc = childInfo['childWorldDesc'];
			if(childW > settings.maxContainWidth && childW >= childH) {
				childH = childH * (settings.maxContainWidth / childW) * settings.scale;
				childW = settings.maxContainWidth * settings.scale;
			} else if(childH > settings.maxContainHeight && childH > childW) {
				childW = childW * (settings.maxContainHeight / childH) * settings.scale;
				childH = settings.maxContainHeight * settings.scale;
			} else {
				childW = childW * settings.scale;
				childH = childH * settings.scale;
			}
			
			var thumbs = child['thumbs'];
			var $zitem = $('<div class="zt-item zt-new"  data-id="zt-item-' + childInfo['id'] + '"></div>');
			var $childImg = $('<img onerror=baseTools.imgErrorCallbackWithWH(this,"http://oss.aliyuncs.com/imzhitu/tmp/notfound.png",' + settings.width + ',' + settings.height + ') class="zt-current" src="' + childInfo['path'] + '" width="' + childW + '" height="' + childH + '" data-desc="' + desc + '" />');
			$zitem.append($childImg);
			for(var index in thumbs) {
				var tw = (2*settings.radius),
					th = (2*settings.radius);
				
				if(tw > settings.maxTagSize) {
					tw = settings.maxTagSize;
				}
				
				if(th > settings.maxTagSize) {
					th = settings.maxTagSize;
				}
				
				var thumb = thumbs[index],
					tagPos = hts.getPosistion(thumb['coordinatex'],thumb['coordinatey'], settings.radius),
					coorx = tagPos.x + '%',
					coory = tagPos.y + '%';
				var $thumb = $('<div class="zt-tag" data-toid=' + thumb['toId'] + '  data-link="zt-item-' + thumb['toId'] +'"></div>'),
					$thumbImg = $('<img class="zt-tag-img" width="' + tw + '" height="'+ th + '" src="' + thumb['thumbPath'] + '"/>'),
					$border = $('<div class="zt-tag-border"></div>');
					
				$thumb.css({top:coory, left:coorx,width:tw + 'px',height:th + 'px'});
				$border.css({width:tw + 'px',height:th + 'px'});
				$thumb.append($thumbImg);
				hts.appendArrow($thumb, thumb['angle'], tw, th);
				$thumb.append($border);
				$zitem.append($thumb);
				
			}
			$ztcontainer.append($zitem);
		},
		initWorldInfo : function(ds) {
		},
		
		/**
		 * 获取圆圈位置
		 * @param coorx 原始x坐标
		 * @param coory 原始y坐标
		 * @param radius
		 */
		getPosistion : function(coorx, coory, radius) {
			var posistion = {x : coorx, y : coory};
			if(settings.phoneType == 'ios') {
				posistion.x = coorx - cache.tagdim.x;
				posistion.y = coory - cache.tagdim.y;
			}
			return posistion;
		},
		appendArrow : function(thumb, angle, tw, th) {
			var arrowCode = 0,
				arrow;
			if(angle != null && angle != -1) {
				if(cache.ieLte8 || cache.ie9) {
					var i = parseInt(angle / 45);
					switch(i) {
					case 1:
						arrowCode = 60;
						break;
					case 2:
						if(angle == 90) {
							arrowCode = 90;
						} else {
							arrowCode = 120;
						}
						break;
					case 3:
						arrowCode = 150;
						break;
					case 4:
						if(angle == 180) {
							arrowCode = 180;
						} else {
							arrowCode = 210;
						}
						break;
					case 5:
						arrowCode = 240;
						break;
					case 6:
						if(angle == 270) {
							arrowCode = 270;
						} else {
							arrowCode = 300;
						}
						break;
					case 7:
						arrowCode = 330;
						break;
					default:
						if(angle == 0 || angle == 360)  {
							arrowCode = 0;
						} else {
							arrowCode = 30;
						} 
						break;
					}
				}
				var path = settings.arrowPathPrefix + arrowCode + settings.arrowPathSuffix;
	        	arrow = $('<img class="zt-arrow" width="' + tw + '" height="'+ th + '" src="' + path + '"/>');
	        	arrow.css({"-webkit-transform":"rotate(" + angle+ "deg)", "-moz-transform":"rotate(" + angle + "deg)", "transform":"rotate(" + angle + "deg)"});
	    		thumb.append(arrow);
			}
		}
		
	},
	/**
	 * 插件开放接口
	 */
	methods = {
		// 初始化所有参数
		init : function( options ) {
			if( this.length ) {
				return this.each(function() {
					// 判断是否重置了动画参数配置
					if ( options ) {
						$.extend( settings, options );
					}
					var $ztcontainer = $(this);
					// 更新浏览器缓存
					cache = {
						ztdim    : {x : settings.width,y : settings.height},
						// 判断浏览器 <= IE8
						ieLte8   : ($.browser.msie && parseInt($.browser.version) <= 8),
						ie9		 : ($.browser.msie && parseInt($.browser.version) == 9),
						tagdim	 : {x : (settings.radius * 100) / settings.width, y : (settings.radius * 100) / settings.height},
						// 如果正在动画则设置为true
						animTour : false};
					//初始化页面数据
					hts.init($ztcontainer);
				});
			}
		}
	};
	
	$.fn.htszoomtour = function(method) {
		if ( methods[method] ) {
			return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
		} else if ( typeof method === 'object' || ! method ) {
			return methods.init.apply( this, arguments );
		} else {
			$.error( '方法： ' +  method + ' 不存在' );
		}
	};
	
})(jQuery);