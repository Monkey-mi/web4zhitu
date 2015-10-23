var admin=false,
	mainTopMargin = 20, // 主面板上边距
	htworldPadding = 7, // 织图边距
	htworldHeight = 0,
	htworldHeight = 0,
	isShowDesc = true,
	
	worldId = 0,
	authorId = 0,
	ver = 1,
	desc = '',
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
	$commentScrollPanel = $('#comment-body');
	ui.initLayout();
	ajax.fetchComment(worldId);
	ajax.fetchMoreWorld(authorId);
});

var ui = {
	initLayout : function() {
		var $ztcontainer = $(".zt-container:eq(0)");
		worldId = $ztcontainer.data("id");
		authorId = $ztcontainer.data('aid');
		desc = $ztcontainer.data('desc');
		ver = $ztcontainer.data('ver');
		var title = $ztcontainer.data('title');
		
		var winHeight = $(window).height(),
			winWidth = $(window).width(),
			margin = 0;
		
		htworldWidth = winWidth;
		if(ver == 1) {
			$(".zt-title").children('img:eq(0)').attr('src', title);
			htworldHeight = winWidth;
			margin = (winHeight-winWidth) / 2;
			if(margin < 0) {
				margin = 0;
			}
		} else {
			htworldHeight = winHeight;
		}
		
		$("#main-box").show();
		$("#world").css({height:winHeight + 'px', width:winWidth 
			+ 'px'});
		
		$ztcontainer.css({width:htworldWidth + 'px'})
			.css({height:htworldHeight + 'px', 'margin-top': margin + 'px', 'margin-bottom': margin+'px'});
		$("#loading").hide();
		var scale = 1,
			width = htworldWidth,
			height = htworldHeight,
			radiu = parseInt(25 * scale),
			config = {w:width,h:height,r:radiu,s:scale};
		ui.loadWorld(config);
		
	},
	
	loadWorld : function(config) {
		worldSettings = {
			adminKey			: config.key,
			worldId 			: worldId, // 浏览的世界ID
			ver					: ver,
			desc 				: desc,
			scale 				: config.s, // 缩放比例
			width 				: config.w, // 显示框宽度
			height 				: config.h, // 显示宽长度
			radius				: config.r,	//圆圈半径
			maxContainWidth 	: config.w, // 播放容器最大宽度
			maxContainHeight	: config.h, // 播放容器最大高度
			maxTagSize 			: 120, //圆圈最大尺寸
			thumbBorderIndex	: 9,
			thumbMaskIndex		: 10,
			limit 				: 5, // 每页查询条数
			url 				: './ztworld/ztworld_queryTitleChildWorldPage', // 数据获取地址
			loadMoreURL			: './ztworld/ztworld_queryChildWorldPage', // 加载更多子世界地址
			inZoomfactor 		: 5, // 背景图渐变过程中放大的倍数
			inSpeedfactor 		: 650, // 渐变速度
			inImgdelayfactor 	: 3, // 渐变延迟时间
			outZoomfactor 		: 3, // 背景图渐变过程中放大的倍数
			outSpeedfactor 		: 650, // 渐变速度
			outImgdelayfactor 	: 0
		};
		$('.zt-container:eq(0)').htszoomtour(worldSettings);
	},
	
	reloadWorld : function(worldId, authorName, authorAvatar, dateAdded, likeCount, clickCount, childCount, worldDesc) {
		if(!isShowDesc) {
			$(".zt-desc").hide();
			ui.showDesc();
			$("#show-thumb").hide();
			$("#hide-thumb").show();
		}
		$("#zt-progress").text('0');
		ui.initLike(worldId);
		$("#user-name").text(authorName);
		$("#user-avatar").attr('src', authorAvatar);
		$("#create-time").text(baseTools.parseDate(dateAdded).format('yyyy-MM-dd hh:mm'));
		$("#like-count").text(likeCount);
		$("#click-count").text(clickCount);
		$("#child-count").text(childCount);
		var title = worldDesc != "" && worldDesc != undefined ? worldDesc : (authorName + "的织图");
		$("title").text(title);
		var $ztcontainer = $('#zt-container');
		$ztcontainer.children('.zt-item').remove();
		$ztcontainer.htszoomtour(worldSettings);
	},
	
	/**
	 * 初始化评论
	 */
	initComment : function(commentObj, total) {
		++commentPageStart;
		var $commentLoading = $("#loadin-comment-tip"),
			$commentCount = $("#comment-count"),
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
			} else {
				$commentScrollPanel.find('.dash-line:last').remove();
			}
			$commentLoading.hide();
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
			} else {
				$commentScrollPanel.find('.dash-line:last').remove();
			}
			$(".load-more-comment:eq(0)").remove();
		} 
	},
	
	appendComment : function(userAvatar, userName, content, date) {
		if(reAuthorId != 0)
			content = ":" + content;
		
		var $comment = 	$('<div class="comment">'
				+	'<img class="comment-avatar" src="'+ userAvatar + '" />'
				+	'<div class="comment-text-wrap">'
				+	'<span class="comment-name">'+ userName + '</span>'
				+	'<span class="comment-text">'+ content +'</span>'
				+	'<div class="comment-date">'+ date +'</div>'
				+	'</div></div><div class="dash-line"></div>');
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
				+	'<a href="javascript:void(0);">加载更多</a>'
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
		var $thumbs = $("#thumbs");
		if(worldObj.length > 0) {
			for(i in worldObj) {
				var w = worldObj[i],
					id = w['id'];
				if(id != worldId) {
					var $img = $('<img src="' + w['titleThumbPath']  + '" />'),
						$a = $('<a class="thumb" href="'+ w['worldURL'] +'" title="' + ui.getClickCountTitle(w['clickCount']) + '"></a>');
					$a.append($img);
					$thumbs.append($a);
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
				s = 50 / height;
			} else {
				s = 50/ width;
			}
			w = width * s;
			h = height * s;
			l = -(w-50)/2;
			t = -(h-50)/2;
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
		var $like = $("#like-opt"),
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
		$("#comment-count").text('0');
		$("#no-comment-tip").hide();
		$commentScrollPanel.hide();
		$commentScrollPanel.children(".comment, .dash-line").remove(); // 移除所有评论
		$("#loadin-comment-tip").show();
		$.post('./ztworld/interact_queryComments', {
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
		$.post('./ztworld/interact_queryComments', {
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