var admin=false,
	leftPanelW = 240, // 左面板宽度
	rightPanelW = 348, // 右面板宽度
	mainSlideDist = (rightPanelW - leftPanelW) / 2, // 主面板滑动距离
	mainOptSpace = 4, // 主面板操作边距
	mainOptW = 100,	//织图操作布局宽度
	mainSlideSpeed = 500, // 主面板滑动速度
	mainTopMargin = 20, // 主面板上边距
	isShowDesc = true,
	
	worldId = 0,
	authorId = 0,
	ver = 0,
	desc = '',
	maxContainHeight = 640,
	idLoadingComment = false,
	commentMaxId = 0,
	commentTotal = 0,
	commentPage = 0,
	commentPageStart = 1,
	commentPageLimit = 20,
	$commentScrollPanel,
	
	shareHead = 'http://s.share.baidu.com/?uid=6844316&searchPic=0&sign=off&type=text&title=织图-旅行故事&desc=', //分享头部
	shareURL, // 分享URL
	
	worldSettings;
$(document).ready(function() {
	var adminKey = baseTools.getQueryString("adminKey");
	if(adminKey != null && adminKey != 'undefined' && adminKey == 'zhangjiaxin') {
		admin = true;
		ui.initLayout(adminKey);
		$("#main").show();
	} else {
		$("#btn-open-right").pageslide({
			direction : "left",
			modal : false,
			speed: mainSlideSpeed,
			expose: 21,
			href :	"#right-panel",
			onInit: function(){
				$('#right-panel:eq(0)').remove();
				$commentScrollPanel = $('#comment-body');
				$("#left-panel").css({left:'0px'}).show();
				$('body').css({'margin-left': -mainSlideDist + 'px'});
				ui.initLayout(adminKey);
				ajax.fetchComment(worldId);
				ajax.fetchMoreWorld(authorId);
				$("#main").show();
			},
			onShow:	function(){
				$('body').animate({'margin-left': -mainSlideDist + 'px',queue:false}, mainSlideSpeed);
				$("#left-panel").animate({left:'0px',queue:false}, mainSlideSpeed);
				$("#btn-open-right").hide();
			},
			onClose: function(){
				$("#left-panel").animate({left: -leftPanelW + 'px',queue:false}, mainSlideSpeed);
				$('body').animate({'margin-left':'0px',queue:false}, mainSlideSpeed);
				$("#btn-open-right").show();
			}
		});
	}
	
});

var ui = {
	initLayout : function(adminKey) {
		var winHeight = $(window).height(),
			containerHeight = winHeight - mainTopMargin*2,
			containerWidth = parseInt(containerHeight / 1.5),
			mainWidth = containerWidth + (mainOptW + mainOptSpace) * 2,
			commentH = winHeight - 250;
			$ztcontainer = $(".zt-container:eq(0)");
			
		worldId = $ztcontainer.data("id");
		authorId = $ztcontainer.data('aid');
		ver = $ztcontainer.data('ver');
		desc = $ztcontainer.data('desc');
		
		var htworldWidth = containerWidth,
			htworldHeight = 0,
			margin = 0,
			title = $ztcontainer.data('title');
		
		if(ver == 1) {
			$(".zt-title").children('img:eq(0)').attr('src', title);
			htworldHeight = htworldWidth;
			margin = (containerHeight-containerWidth) / 2;
			if(margin < 0) {
				margin = 0;
			}
		} else {
			htworldHeight = containerHeight;
			maxContainHeight = 960;
		}
			
		$("#main").css({height:winHeight, width:mainWidth});
		$("#zt-world").css({height:containerHeight + 'px', width:containerWidth + 'px'});
		$ztcontainer.css({width:htworldWidth + 'px'})
			.css({height:htworldHeight + 'px', 'margin-top': margin + 'px', 'margin-bottom': margin+'px'});
		$(".zt-desc").css({'margin-top':(containerHeight - 100) + 'px', width:containerWidth + 'px'});
		
		$("#comment-body").css({'height':commentH + 'px', 'overflow':'auto'});
		$("#comment-body").niceScroll({cursorborder:"",cursorcolor:"#15191A",boxzoom:false});
		
		var scale = htworldWidth / 640,
			width = htworldWidth,
			height = htworldHeight,
			radiu = parseInt(40 * scale),
			config = {w:width,h:height,r:radiu,s:scale,key:adminKey};
		ui.loadWorld(config);
		
		var coverPath = $ztcontainer.data('cover'),
			worldURL = $ztcontainer.data('url');
		ui.initShareInfo(coverPath, worldURL);
		ui.initLike(worldId);
	},
	
	shareToPlatform : function(i) {
		var platform,
			key = null;
		switch(i) {
		case 1:
			key = '100507011';
			platform = 'qzone';
			break;
		case 2:
			platform = 'renren';
			break;
		default:
			key = '3145866427';
			platform = 'tsina';
			break;
		}
		window.open(encodeURI(shareURL + '&to=' + platform + '&key=' + key));
	},
	
	/**
	 * 初始化分享信息
	 */
	initShareInfo : function(coverPath, worldURL) {
		shareURL = shareHead + '&pic=' + coverPath + '&url=' + worldURL;
		$(".platform").each(function(i) {
			var $this = $(this);
			$this.click(function(){
				ui.shareToPlatform(i);
			});
		});
	},
	
	loadWorld : function(config) {
		worldSettings = {
			adminKey			: config.key,
			worldId 			: worldId, // 浏览的世界ID
			ver					: ver,
			desc				: desc,
			scale 				: config.s, // 缩放比例
			width 				: config.w, // 显示框宽度
			height 				: config.h, // 显示宽长度
			radius				: config.r,	//圆圈半径
			maxContainHeight	: maxContainHeight, // 播放容器最大高度
			limit 				: 5, // 每页查询条数
			url 				: './ztworld/ztworld_queryTitleChildWorldPage', // 数据获取地址
			loadMoreURL			: './ztworld/ztworld_queryChildWorldPage', // 加载更多子世界地址
			inZoomfactor 		: 5, // 背景图渐变过程中放大的倍数
			inSpeedfactor 		: 650, // 渐变速度
			inImgdelayfactor 	: 3, // 渐变延迟时间
			outZoomfactor 		: 3, // 背景图渐变过程中放大的倍数
			outSpeedfactor 		: 650, // 渐变速度
			outImgdelayfactor 	: 0,
		};
		$('.zt-container:eq(0)').htszoomtour(worldSettings);
	},
	
	/**
	 * 初始化评论
	 */
	initComment : function(commentObj, total) {
		++commentPageStart;
		var $commentLoading = $("#loadin-comment-tip"),
			$commentCount = $("#comment-count-num"),
			now = new Date();
		
		$commentCount.text(total);
		ui.initCommentPage(total);
		$commentScrollPanel.show();
		if(commentObj.length > 0) {
			for(i in commentObj) {
				var c = commentObj[i],
					u = c['userInfo'],
					date = baseTools.getShortDate(now, baseTools.parseDate(c['commentDate']));
				if(i == 0) {
					commentMaxId = c['id'];
				}
				ui.appendComment(u['userAvatar'], u['userName'], c['content'], c['reAuthorId'], date);
			}
			if(commentPageStart <= commentPage) {
				ui.appendMoreCommentTip();
			}
			$commentLoading.hide();
			$("#comment-body").getNiceScroll().resize();
		} else {
			$commentLoading.hide();
			$("#no-comment-tip").show();
		}
	},
	
	addMoreComment : function(commentObj) {
		++commentPageStart;
		var now = new Date();
		if(commentObj.length > 0) {
			for(i in commentObj) {
				var c = commentObj[i],
					u = c['userInfo'],
					date = baseTools.getShortDate(now, baseTools.parseDate(c['commentDate']));
				ui.appendComment(u['userAvatar'], u['userName'], c['content'], c['reAuthorId'], date);
			}
			if(commentPageStart <= commentPage) {
				ui.appendMoreCommentTip();
			}
			$("#comment-body").getNiceScroll().resize();
			$(".load-more-comment:eq(0)").remove();
		} 
	},
	
	appendComment : function(userAvatar, userName, content, reAuthorId, date) {
		if(reAuthorId != 0)
			content = ":" + content;
		
		var $comment = 	$('<div class="comment">'
				+	'<img class="comment-avatar" src="'+ userAvatar + '" />'
				+	'<div class="comment-text-wrap">'
				+	'<span class="comment-name">'+ userName + '</span>'
				+	'<span class="comment-text">'+ content +'</span>'
				+	'<div class="comment-date">'+ date +'</div>'
				+	'<div class="dash-line"></div></div></div>');
		$commentScrollPanel.append($comment);
	},
	initCommentPage : function(total) {
		var c = total % commentPageLimit,
			p = parseInt(total / commentPageLimit);
		if(c > 0) {
			commentPage = ++p;
		} else {
			commentPage = p;
		}
	},
	initCommentFailed : function() {
		$("#loadin-comment-tip").hide();
		$("#failed-comment-tip").show();
	},
	
	appendMoreCommentTip : function() {
		var $comment = 	$(
				'<div class="comment load-more-comment">'
				+	'<div class="comment-text-wrap">'
				+	'<a href="#">加载更多</a>'
				+	'</div></div>');
		$comment.click(function(e){
			if(!idLoadingComment) {
				idLoadingComment = true;
				var $this = $(this);
				$this.find('a').text('加载中...');
				ajax.fetchMoreComment(worldId, 2);
			}
		});
		$commentScrollPanel.append($comment);
	},
	/**
	 * 初始化更多织图
	 */
	initMoreWorld : function(worldObj) {
		var $thumbs = $("#more .thumbs:eq(0)");
		if(worldObj.length > 0) {
			for(i in worldObj) {
				var w = worldObj[i],
					id = w['id'];
				if(id != worldId) {
					var $img = $('<img src="' + w['titleThumbPath']  + '" />'),
						$a = $('<a class="thumb" href="'+ w['worldURL'] 
							+ '" title="' + ui.getClickCountTitle(w['clickCount']) + '"></a>'),
						$more= $('<li></li>');
					$more.append($a);
					$a.append($img);
					$thumbs.append($more);
					ui.fixMoreImgPos($img);
				}
			}
			$("#more").show();
		} else {
		}
	},
	
	getClickCountTitle : function(count) {
		return "播放" + count + "次";
	},
	
	/**
	 * 修正更多织图图片的位置
	 */
	fixMoreImgPos : function(img) {
		img.load(function() {
			var w = 0,
				h = 0,
				s = 1,
				l = 0,
				t = 0,
				width = img.width(),
				height = img.height();
			if(width >= height) {	
				s = 70 / height;
			} else {
				s = 70/ width;
			}
			w = width * s;
			h = height * s;
			l = -(w-70)/2;
			t = -(h-70)/2;
			img.css({width:w+'px', height:h+'px', left:l+'px', top:t+'px'});
			img.fadeIn();
		});
	},
	
	initLike : function(worldId) {
		var likeKey = "ht_like_" + worldId;
		if(baseTools.checkCookieEnable) {
			var htLike = baseTools.getCookie(likeKey);
			if(htLike == null || htLike == 'null') {
				ui.enableLike(worldId);
				return;
			}
		}
		ui.disableLike(worldId);
		return;
	},
	
	enableLike : function(worldId) {
		var likeKey = "ht_like_" + worldId;
		var $like = $("#like-opt");
			$text = $("#like-count"),
			count = $text.text();
		$like.unbind('click');
		$like.children('.icon:eq(0)').addClass('unlike').removeClass('like');
		$like.attr("title", "赞一个");
		$like.bind('click', function() {
			ui.disableLike();
			$.post("./ztworld/interact_likeDirect", {
				'worldId' : worldId
			}, function(result) {
				if (result['result'] == 0) {
					var likeDate = new Date().getTime().toString();
					baseTools.setCookie(likeKey, likeDate);
					$text.text(++count);
				} else {
					$text.text(--count);
					ui.enableLike(worldId);
				}
			});
		});
	},
	disableLike : function(worldId) {
		var $like = $("#like-opt");
		$like.attr("title", "你已经赞过");
		$like.unbind('click');
		$like.children('.icon:eq(0)').removeClass('unlike').addClass('like');
	}
	
};
var ajax = {
	fetchComment : function(worldId) {
		idLoadingComment = false,
		commentMaxId = 0,
		commentTotal = 0,
		commentPage = 0,
		commentPageStart = 1;
		$("#comment-count-num").text('0');
		$("#no-comment-tip").hide();
		$commentScrollPanel.hide();
		$commentScrollPanel.children(".comment").remove(); // 移除所有评论
		$("#loadin-comment-tip").show();
		$.post('./ztworld/interact_queryOpenComment', {
					'worldId':worldId,
					'maxId':commentMaxId,
					'start':commentPageStart,
					'limit':commentPageLimit
				 },function(result) {
					 idLoadingComment = false;
					 if(result['result'] == 0) {
						 ui.initComment(result['comments'], result['totalCount']);
					 } else {
						 ui.initCommentFailed();
					 }
				 },'json');
	},
	fetchMoreComment : function(worldId, page) {
		$.post('./ztworld/interact_queryOpenComment', {
			'worldId':worldId,
			'maxId':commentMaxId,
			'start':commentPageStart,
			'limit':commentPageLimit
		 },function(result) {
			 idLoadingComment = false;
			 if(result['result'] == 0) {
				 ui.addMoreComment(result['comments'], result['totalCount']);
			 } else {
				 $(".load-more-comment:eq(0)").text('加载失败！点击刷新');
			 }
		 },'json');
	},
	
	/**
	 * 获取更多织图
	 */
	fetchMoreWorld : function(userId) {
		if(userId == 0) return;
		$.post('./ztworld/ztworld_queryUserRandomWorld', {
			'userId':userId,
			'limit':4
		 },function(result) {
			 if(result['result'] == 0) {
				 ui.initMoreWorld(result['htworld']);
			 }
		 },'json');
		
	}
};