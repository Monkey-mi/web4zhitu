/*
 * jQuery htszoomtour v1.0
 *
 * Author: TinKit Chu(zhutianjie89@gmail.com)
 * 
 */
(function($) {
	var settings = {
		adminKey		: '', // 管理key
		worldId			: 1, //查询的世界ID
		ver				: 1, // 版本号，新版本为1，旧版本为0
		desc			: '', // 织图描述
		limit			: 10, //每页查询的最大条数
		maxContainWidth : 640, // 播放容器最大宽度
		maxContainHeight: 640, // 播放容器最大高度
		maxTagSize		: 54,
		scale 			: 1, //页面缩放比例
		url				: '', //数据访问的URL
		init			: function(ds){}, //初始化过程同时进行的函数
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
	
		radius			: 40,	//圆圈半径
		border			: 2,  // 边框
		arrowPathPrefix : 'http://imzhitu.qiniudn.com/world/arrowv2/arrow-',
		arrowPathSuffix : '.png',
		multiple		: false,
		thumbBorderIndex: 7,
		thumbMaskIndex  : 8,
		
		baseTypePath2	: 'http://imzhitu.qiniudn.com/world/thumbs/1403056393000.png',
		baseTypePath3   : 'http://imzhitu.qiniudn.com/world/thumbs/1403057093000.png',
		baseTypePath4	: 'http://imzhitu.qiniudn.com/world/thumbs/1403056953000.png',
		baseTypePath5	: 'http://imzhitu.qiniudn.com/world/thumbs/1403057046000.png'
		
		
	},
	cache = null,
	//viewedList = [], // 保存查看过的ItemID队列
	tag	= {
		/**
		 * 初始化缩略图属性
		 */
		saveInitialData	: function($tag) {
			$tag.data({
				width	: $tag.width(),
				height	: $tag.height(),
				left	: $tag.position().left,
				top		: $tag.position().top
			}).addClass($tag.data('link'));
		},
	},
	
	image = {
		/**
		 * 初始化图片数据，包括，宽、高等
		 */
		saveInitialData : function($image) {
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
		enter : function($ztcontainer, $tag) {
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
			
			//对应的大图还没有加载
			if($new_ztitemimage.size() == 0) { 
				//获取数据并更新页面元素
				hts.refetch($ztcontainer, $tag, $tag.data('toid'));
			} else {
				$ztcontainer.data('viewedList').push(link);
				this.zoomin($ztcontainer, $tag, $ztitem, ztitemid, $ztimage, zoomfactor, speedfactor, 
						imgdelayfactor, zoomAnimation, $new_ztitem, $new_ztitemimage);
			}
				
		},
		
		back : function($ztcontainer) {
			var viewLen 		= $ztcontainer.data('viewedList').length;
			if( viewLen < 2 ) {
				viewport.showTip($ztcontainer, '已经在第一层');
				return false;
			}
			var ztitemid		= $ztcontainer.data('viewedList').pop(),
				$ztitem			= $ztcontainer.find('div.' + ztitemid),
				$ztimage		= $ztitem.children('img.zt-current'),
				
				zoomfactor		= settings.outZoomfactor, // 背景图渐变过程中放大的倍数
				speedfactor		= settings.outSpeedfactor, // 渐变速度
				imgdelayfactor 	= settings.outImgdelayfactor, // 渐变延迟时间
				
				$new_ztitem		= $ztcontainer.find('div.' + $ztcontainer.data('viewedList')[viewLen - 2]),
				$new_ztitemimage= $new_ztitem.find('img.zt-current'),
				zoomAnimation	= true;
				
			this.zoomout($ztcontainer, $ztitem, ztitemid, $ztimage, zoomfactor, speedfactor, imgdelayfactor, 
					zoomAnimation, $new_ztitem, $new_ztitemimage);
			settings.onBack();
		},
		
		/**
		 * 缩放出现特效
		 */
		zoomin : function($ztcontainer, $tag, $ztitem, ztitemid, $ztimage, zoomfactor, speedfactor, imgdelayfactor, 
				zoomAnimation, $new_ztitem, $new_ztitemimage) {
			
			var imgWidth = $new_ztitemimage.data('width'),
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
				left	: - ( ( zoomfactor * ( 2 * el_l ) ) - ( 2 * el_l ) ) / 2 + 'px',
				top		: - ( ( zoomfactor * ( 2 * el_t ) ) - ( 2 * el_t ) ) / 2 + 'px'
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
			
			var newztimg = $new_ztitemimage.attr('src');
			
			var tagCss	= {
				position	: 'absolute',
				width		: $tag.data('width'),
				height		: $tag.data('height'),
				left		: $tag.data('left'),
				top			: $tag.data('top')
			};
			
			$ztitem.append(
				$('<img src="' + newztimg + '" class="zt-temp"></img>')
				.css( tagCss )
				.delay(imgdelayfactor)
				.applyStyle( imgAnimationCss, $.extend( true, [], { duration : speedfactor, easing : settings.zoominEasing, complete : function() {
					viewport.zoominCallback($ztcontainer, $(this), $new_ztitem, $ztitem, $ztimage,newDesc);
					$('img.zt-temp').remove();
				}}))
			);
			
			if( !zoomAnimation )
				this.zoominCallback($ztcontainer, $ztitem.find('img.zt-temp'), $new_ztitem, $ztitem, $ztimage, newDesc);
		},
		
		/**
		 * 缩放出现特效回调
		 */
		zoominCallback	: function($ztcontainer, $zttemp, $new_ztitem, $ztitem, $ztimage, desc) {
			
			var imgWidth = $ztimage.data('width'),
				imgHeight = $ztimage.data('height'),
				centerX = $ztimage.data('centerX'),
				centerY = $ztimage.data('centerY');
		
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
			this.refreshDesc($ztcontainer, desc);
			
			cache.animTour	= false;
		},
		
		/**
		 * 缩放消失特效
		 */
		zoomout	: function($ztcontainer, $ztitem, ztitemid, $ztimage, zoomfactor, speedfactor, imgdelayfactor,
				zoomAnimation, $new_ztitem, $new_ztitemimage) {
			if(cache.animTour) {
				return;
			}
			
			var $originalTag	= $new_ztitem.children( 'div.' + ztitemid ),
				o_tag_w			= $originalTag.data('width'),
				o_tag_h			= $originalTag.data('height'),
				o_tag_l			= $originalTag.data('left'),
				o_tag_t			= $originalTag.data('top'),
			
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
			
			var imgAnimationCss	= {
				width	: o_tag_w + 'px',
				height	: o_tag_h + 'px',
				left	: o_tag_l + 'px',
				top		: o_tag_t + 'px',
				opacity	: 0
			};
			
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
				viewport.zoomoutCallback($ztcontainer, $new_ztitem_tags, newDesc);
			} } ) );
			
			if( !zoomAnimation )
				this.zoomoutCallback($ztcontainer, $new_ztitem_tags, newDesc);
			
		},
		
		zoomoutCallback	: function($ztcontainer, $new_ztitem_tags, desc) {
			$new_ztitem_tags.show();
			this.refreshDesc($ztcontainer, desc);
			cache.animTour	= false;
		},
		
		showTip : function($ztcontainer, tip) {
			$ztcontainer.find('.zt-tag-blink, .zt-tag-blink2').hide(function() {
				$(this).fadeIn();
			});
		},
		
		/**
		 * 刷新描述
		 */
		refreshDesc : function($ztcontainer, desc) {
			var $desc,
				$showDesc,
				$hideDesc;
			if(!settings.multiple) {
				$desc = $(".zt-desc:eq(0)");
				$showDesc = $('.zt-desc-show:eq(0)'),
				$hideDesc = $('.zt-desc-hide:eq(0)');
			} else {
				$desc = $ztcontainer.children(".zt-desc:eq(0)");
				$showDesc = $ztcontainer.find('.zt-desc-show:eq(0)'),
				$hideDesc = $ztcontainer.find('.zt-desc-hide:eq(0)');
			}
			var $descText = $desc.children(".zt-desc-text");
				
			if(desc == '' || desc == 'undefined') {
				$desc.hide();
				$showDesc.hide();
				$hideDesc.hide();
			} else {
				$desc.show();
				$hideDesc.show();
				$showDesc.hide();
				$descText.text(desc);
				var height = $descText.innerHeight();
				$desc.css({height:height+'px'});
				$hideDesc.css({'bottom':(height+5) + 'px'});
			}
		}
	},
	
	zoomtour = {
		refresh : function($ztcontainer,$loading) {
			var $ztitems			= $ztcontainer.children('div.zt-new'),
				$ztimages			= $ztitems.children('img.zt-current'),
				$zttags				= $ztitems.children('div.zt-tag'),
				$zttitle			= $ztcontainer.children("div.zt-title");
			// 为每个item添加值为"id"的class。稍后将会使用这些items.
			$ztitems.each( function() {
				var $ztitem	= $(this);
				$ztitem.addClass( $ztitem.data('id'))
				.removeClass('zt-new');
			});
			
			$zttags.each(function() {
				var $tag	= $(this);
				
				// 保存每个tag的width、height、left、top
				tag.saveInitialData($tag);
				
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
				image.saveInitialData($image);
				$image.load(function() {
					++loaded;
					//如果图片加载失败，重新保存其参数
					if($image.attr('iserror') == 1) {
						image.saveInitialData($image);
					}
					
					if(loaded === 1) {
						$zttitle.hide();
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
							viewport.enter( $ztcontainer, $tag);
							return false;
						});
						$zttags.bind('mouseenter.zoomtour', function(e) {
							var $tag = $(this);
							$tag.children('.zt-tag-img:eq(0)').addClass('zt-tag-img-highlight');
						});
						$zttags.bind('mouseleave.zoomtour', function(e) {
							var $tag = $(this);
							$tag.children('.zt-tag-img:eq(0)').removeClass('zt-tag-img-highlight');
						});
					}
					
				}).attr( 'src', $(this).attr('src') );
			});
		}
	},
	hts = {
		init : function($ztcontainer) {
			$ztcontainer.unbind('click.zoomtour');
			var $loading = $ztcontainer.children('div.zt-loading');
			if(!$ztcontainer.data('init')) {
				$loading.show();
				$.post(settings.url, 
					{'start':1,
					 'limit':settings.limit,
					 'adminKey':settings.adminKey,
					 'worldId':$ztcontainer.data('id')
					 },function(ds){
						 if(ds['result'] == 0) {
								hts.parse($ztcontainer,ds['htworld'], ds['htworld']['childs'],function (ds) {
									settings.init(ds);
									hts.showTitle($ztcontainer, $loading);
									$ztcontainer.data('init', true);  // 更新初始化标记
									
									//返回上一页
									$ztcontainer.bind('click.zoomtour', function(e) {
										if( !cache.animTour ) {
											viewport.back($ztcontainer);
										}
									});
								});
						 } else {
							 //TODO 网络连接错误处理
						 }
					 },'json' );
			} else {
				this.showTitle($ztcontainer, $loading),
				$ztcontainer.children('div.zt-item:eq(0)').children('div.zt-tag').show();
			}
		},
		
		/**
		 * 显示封面
		 */
		showTitle: function($ztcontainer,$loading) {
			$ztcontainer.data('viewedList', []);
			// 更新页面
			zoomtour.refresh($ztcontainer,$loading);
			var $ztitems = $ztcontainer.children('div.zt-item:eq(0)');
			viewport.refreshDesc($ztcontainer, $ztitems.find('img:eq(0)').data('desc'));
			// 显示第一个
			$ztitems.first().show();
			$ztcontainer.data('viewedList').push($ztitems.first().data('id'));
		},
		
		/**
		 * 重新加载
		 */
		refetch : function($ztcontainer,$tag, childId) {
			var $loading = $ztcontainer.children('div.zt-loading');
			$loading.show();
			$.post(settings.loadMoreURL, 
				{'childId':childId,
				 'limit':settings.limit,
				 'worldId':$ztcontainer.data('id')
				 },function(ds) {
					 if(ds['result'] == 0) {
						 hts.parse($ztcontainer,ds['htworld'],ds['htworld']['childs'], function(ds) {
							// 更新页面
							zoomtour.refresh($ztcontainer,$loading);
							viewport.enter($ztcontainer, $tag);
						 },'json' );
					 } else {
						 // TODO 网络连接错误处理
					 }
				 });
		},
		
		/**
		 * 解析数据
		 */
		parse : function($ztcontainer,worldInfo, childWorlds,callback) {
			for(var index in childWorlds) {
				this.appendChild($ztcontainer,childWorlds[index], index);
			}
			callback(worldInfo);
		},
		/**
		 * 添加子世界
		 */
		appendChild : function($ztcontainer, child, childIndex) {
			var childInfo = child['child'],
				childW = childInfo['width'],
				childH = childInfo['height'],
				containerW = settings.maxContainWidth * settings.scale,
				containerH = settings.maxContainHeight * settings.scale,
				desc = childInfo['childWorldDesc'];
			
			// 如果第一页没有描述，就赋值织图描述
			if(childIndex == 0 && (desc == '' || desc == undefined || desc == 'undefined')) {
				desc = $ztcontainer.data('desc');
			}
			
			if(childW >= childH) {
				childH = childH * (containerW / childW);
				childW = containerW;
			} else {
				childW = childW * (containerH / childH);
				childH = containerH;
			}
			
			var thumbs = child['thumbs'];
			var $zitem = $('<div class="zt-item zt-new"  data-id="zt-item-' + childInfo['id'] + '"></div>');
			var $childImg = $('<img onerror=baseTools.imgErrorCallbackWithWH(this,"http://oss.aliyuncs.com/imzhitu/tmp/notfound.png",' 
					+ settings.width + ',' + settings.height + ') class="zt-current" src="' + childInfo['path'] + '" width="' 
					+ childW + '" height="' + childH + '" data-desc="' + desc + '" />');
			$zitem.append($childImg);
			for(var index in thumbs) {
				var borderW = 0,
					tw = (2*settings.radius),
					th = (2*settings.radius);
				
				if(tw > settings.maxTagSize) {
					tw = settings.maxTagSize;
				}
				
				if(th > settings.maxTagSize) {
					th = settings.maxTagSize;
				}
				
				var thumb = thumbs[index],
					tagPos = this.getPosistion(thumb['coordinatex'],thumb['coordinatey'], settings.radius),
					coorx = tagPos.x + '%',
					coory = tagPos.y + '%';
				var $thumb = $('<div class="zt-tag" data-toid=' + thumb['toId'] + '  data-link="zt-item-' + thumb['toId'] +'"></div>'),
					$thumbImg = $('<img class="zt-tag-img" src="' + thumb['thumbPath'] + '"/>'),
					$border;
				
				if(cache.webkit && (thumb['typePath'] != '' || thumb['type'] != 1)) {
					var typePath = this.getTypePath(thumb['typePath'], thumb['type']),
						end = typePath.lastIndexOf('.'),
						prefix = typePath.substring(0, end),
						suffix = typePath.substring(end),
						border = prefix + settings.thumbBorderIndex + suffix,
						mask = prefix + settings.thumbMaskIndex + suffix;
					$thumbImg.css({'-webkit-mask-image':'url('+ mask +')'});
					$border = $('<img class="zt-tag-border" />');
					$border.attr('src', border);
				} else {
					borderW = 2*settings.border;
					$border = $('<div class="zt-tag-border"></div>');
					$border.addClass('zt-tag-border-nowebkit');
				}
				
				if(childIndex == 0) {
					var $blink = $('<div class="zt-tag-blink"></div>');
					var $blink2 = $('<div class="zt-tag-blink2"></div>');
					$thumb.append($blink).append($blink2);
				}
				
				tw = tw-borderW;
				th = th-borderW;
				$thumbImg.css({width:tw + 'px',height:th + 'px'});
				$thumb.css({top:coory, left:coorx,width:tw + 'px',height:th + 'px'});
				$border.css({width:tw + 'px',height:th + 'px'});
				$thumb.append($thumbImg);
				this.appendArrow($thumb, thumb['angle'], tw, th);
				$thumb.append($border);
				$zitem.append($thumb);
			}
			$ztcontainer.append($zitem);
		},
		
		/**
		 * 获取类型路径
		 */
		getTypePath : function(typePath, type) {
			if(typePath == "" || typePath == undefined) {
				switch (type) {
				case 2:
					typePath = settings.baseTypePath2;
					break;
				case 3:
					typePath = settings.baseTypePath3;
					break;
				case 4:
					typePath = settings.baseTypePath4;
					break;
				case 5:
					typePath = settings.baseTypePath5;
					break;
				default:
					break;
				}
			}
			return typePath;
			
		},
		
		/**
		 * 获取圆圈位置
		 */
		getPosistion : function(coorx, coory, radius) {
			var posistion = {x : coorx, y : coory};
			posistion.x = coorx - cache.tagdim.x;
			if(settings.ver == 0) {
				posistion.y = coory - cache.tagdim.y;
			} else {
				posistion.y = coory * 1.5 - 25 - cache.tagdim.y;
			}
			return posistion;
		},
		
		/**
		 * 添加箭头
		 */
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
		/**
		 * 初始化所有参数
		 */
		init : function( options ) {
			if( this.length ) {
				return this.each(function() {
					// 判断是否重置了动画参数配置
					if (options) {
						$.extend(settings, options);
					}
					var $ztcontainer = $(this);
					$ztcontainer.data('id', settings.worldId);
					$ztcontainer.data('desc', settings.desc);
					
					var $ztdesc = descShow = descHide = 'undefined';
					if(!settings.multiple) {
						$ztdesc = $(".zt-desc:eq(0)");
						$descHide = $(".zt-desc-hide:eq(0)");
						$descShow = $(".zt-desc-show:eq(0)");
					} else {
						$ztdesc = $ztcontainer.find(".zt-desc:eq(0)");
						$descHide = $ztcontainer.find(".zt-desc-hide:eq(0)");
						$descShow = $ztcontainer.find(".zt-desc-show:eq(0)");
					}
					$descShow.click(function() {
						$ztdesc.fadeIn();
						$descHide.show();
						$(this).hide();
						return false;
					});
						
					$descHide.click(function() {
						var $this = $(this);
						$ztdesc.fadeOut(function() {
							$this.hide();
							$descShow.show();
						});
						return false;
					});
					$ztcontainer.find('.zt-desc-show:eq(0)').click(function() {
						var $this = $(this),
							$descHide,
							$ztdesc;
						if(!settings.multiple) {
							$descHide = $(".zt-desc-hide:eq(0)");
							$ztdesc = $(".zt-desc:eq(0)");
						} else {
							$descHide = $ztcontainer.find('.zt-desc-hide:eq(0)');
							$ztdesc = $ztcontainer.find('.zt-desc:eq(0)');
						}
						$descHide.show();
						$this.hide();
						$ztdesc.fadeIn();
						return false;
					});
					
					$ztcontainer.find('.zt-desc-hide:eq(0)').click(function() {
						var $this = $(this),
							$descShow,
							$ztdesc;
						if(!settings.multiple) {
							$descShow = $('.zt-desc-show:eq(0)');
							$ztdesc = $('.zt-desc:eq(0)');
						} else {
							$descShow = $ztcontainer.find('.zt-desc-show:eq(0)');
							$ztdesc = $ztcontainer.find('.zt-desc:eq(0)');
						}
						$ztdesc.fadeOut(function() {
							$this.hide();
							$descShow.show();
						});
						return false;
					});
					
					// 更新浏览器缓存
					if(cache == null) {
						cache = {
								ztdim    : {x : settings.width,y : settings.height},
								// 判断浏览器 <= IE8
								ieLte8   : ($.browser.msie && parseInt($.browser.version) <= 8),
								ie9		 : ($.browser.msie && parseInt($.browser.version) == 9),
								webkit	 : $.browser.webkit,
								tagdim	 : {x : (settings.radius * 100) / settings.width, y : (settings.radius * 100) / settings.height},
								// 如果正在动画则设置为true
								animTour : false
							};
					}
					//初始化页面数据
					hts.init($ztcontainer);
				});
			}
		},
		
		reload : function(options) {
			var $ztcontainer = $(this);
			$ztcontainer.data('init', false);
			this.init(options);
		},
		
		/**
		 * 恢复浏览
		 */
		resume : function() {
			var $ztcontainer = $(this);
			var $loading = $ztcontainer.children('div.zt-loading');
			hts.showTitle($ztcontainer, $loading);
			$ztcontainer.children('div.zt-item:eq(0)').children('div.zt-tag').show();
		},
		
		/**
		 * 暂停浏览
		 */
		pause : function() {
			var ztitemid		= viewedList.pop(),
				$ztitem			= $(this).find('div.' + ztitemid);
			$ztitem.hide();
			// 隐藏所有tags
			$ztitem.children('div.zt-tag').hide();
			$(this).children('.zt-title').show();
		}, 
		
		/**
		 * 获取初始化状态
		 */
		getInitState : function() {
			var state = $(this).data('init');
			if(state == undefined) {
				return false;
			}
			return state;
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