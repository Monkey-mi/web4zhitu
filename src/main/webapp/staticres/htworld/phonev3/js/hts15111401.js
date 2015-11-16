var worldId,userId, shortLink, winWidth;
var maxCommentId = 0;
var maxSuperbId = 0;
var isSuperbloading = false;
$(document).ready(function() {
	winWidth = ui.getWinWidth();
	shortLink = ui.getShortLink();
	var likeLimit = ui.getLikeLimit(winWidth);
	ajax.fetchWorld(shortLink, likeLimit);
	ajax.fetchSuperb();
	
	$(window).scroll(function () {
	    if ($(document).scrollTop() + $(window).height() >= $(document).height()) {
	    	ajax.fetchSuperb();
	    }
	});
});

var ui = {
	setShareInfo : function(title) {
		
	},
	getShortLink : function() {
		var str = window.location.pathname;
		return str.substr(3);
	},
	getWinWidth : function() {
		return $(window).outerWidth();
	},
	getLikeLimit : function(winWidth) {
		return parseInt((winWidth-10*2-60)/30);
	},
	initWorld : function(result) {
		var winHeight, worldWidth, worldHeight, world, user, channel;
		winHeight = $(window).height();
		worldWidth = winWidth;
		worldHeight = worldWidth;
		world = result['htworld'];
		user = result['htworld']['userInfo'];
		worldId = world['id'];
		userId = world['authorId'];
		
		$('.zt-container:eq(0)').css({width:worldWidth + 'px', height:worldHeight + 'px'});
		$('#world').css({height:worldHeight + 'px', width:worldWidth + 'px'});
		$('.zt-title').children('img:eq(0)').attr('src', world['titlePath']);
		$(".zt-desc, .zt-desc-show, .zt-desc-hide, .zt-desc-show, .zt-desc-hide")
			.css({'margin-bottom':winHeight-54-worldHeight + 'px'});
		
		$('#main-box').show();
		
		$(".avatar-wrap").live('click', function() {
			ui.gohome($(this).data('checksum'));
		});
		
		$("#latest-more").click(function() {
			ui.gohome(user['checksum']);
		});
		
		$("#comment-fetch-btn").bind('click', function() {
			ajax.fetchComment(worldId);
		});
		
		$("#user-avatar-wrap").data('checksum', user['checksum']);
		
		$('#user-avatar').attr('src', user['userAvatar']);
		if(user['verifyIcon'] != '') {
			$('#user-verify').attr('src', user['verifyIcon']);
			$('#user-verify').show();
		}
		$('#user-name').text(user['userName']);
		if(world['locationAddr'] != '') {
			$('#location').text(world['locationAddr']);
			$('#location-wrap').show();
		}
		$('#child-count').text(world['childCount']);
		$('#date-modified').text(ui.dateformat(world['dateModified'], new Date()));
		$('#click-count').text(ui.countformat(world['clickCount']));
		if(world['worldDesc'] != '') {
			$('#world-desc').text(world['worldDesc']).show();
			document.title = user['userName'] + ' | ' + world['worldDesc'];
		} else {
			if(world['likeCount'] > 0)
				document.title = "被赞" + world['likeCount'] + "次 | 分享来自" + user['userName'] + "的织图";
			else 
				document.title = "分享来自" + user['userName'] + "的织图";
			
		}
		$("#share-img").attr('src', world['titlePath']);
		$('.like-count').text(world['likeCount']);
		$('#comment-count').text(world['commentCount']);
		if(ui.appendLikes(world['likes']) == 0)
			$("#like-users-wrap").show();
		
		if(ui.appendLabels(world['worldLabel'], world['channelNames']) == 0)
			$("#label-wrap").show();
		
		if(ui.appendChannel(result['channels']) == 0) {
			$("#channel-wrap").show();
		}
		
		$('#loading').hide();
		var scale = 1,
			width = worldWidth,
			height = worldHeight,
			radiu = parseInt(25 * scale),
			config = {w:width,h:height,r:radiu,s:scale};
		ui.initChildWorld(config, world['worldDesc']);
		
		return worldId;
	},
	appendLikes : function(likes) {
		var $like, $likesWrap, $verifyIcon;
		
		if(likes == '' || likes == 0) {
			return -1;
		}
		
		$likesWrap = $('#liked-users');
		for(var i in likes) {
			if(likes[i]["verifyIcon"] != '')
				$verifyIcon = '<img class="verify" src="'+likes[i]["verifyIcon"]+'"/>';
			else
				$verifyIcon = '';
			var $like = 
					$('<div class="avatar-wrap" data-checksum="'+likes[i]["checksum"]+'">'
					+ '<img class="avatar" alt="" src="'+likes[i]["userAvatar"]+'" />'
					+ '<div class="border"></div>'
					+ $verifyIcon
					+ '</div>');
			$likesWrap.append($like);
		}
		return 0;
	},
	appendLabels : function(labelsStr, channels) {
		var $label, $labelWrap, labelArray;
		
		if(labelsStr == '' && channels == '')
			return -1;
		
		//设置页面关键字
		var pageKeywords = $("meta[name=keywords]").attr('content');
		pageKeywords = labelsStr + pageKeywords;
		$("meta[name=keywords]").attr('content', pageKeywords);
		
		$labelWrap = $("#labels");
		labelArray = labelsStr.split(',');
		
		for(var i in channels) {
			$label = $('<div class="label channel">'+channels[i]['name']+'</div>');
			$labelWrap.append($label);
		}
		
		for(var i in labelArray) {
			if(labelArray[i] == '')
				break;
			$label = $('<div class="label">'+labelArray[i]+'</div>');
			$labelWrap.append($label);
		}
		return 0;
	},
	appendChannel : function(channels) {
		if(channels == '' || channels == undefined)
			return -1;
		
		var channel = channels[0];
		$("#channel-banner").attr('src', channel['subIcon']);
		$("#channel-name").text(channel['channelName']);
		$("#channel-desc").text(channel['channelDesc']);
		$("#channel-info").click(function() {
			window.location.href = "/operations/channelPage.html?cid=" + channel['id'];
		});
		return 0;
	},
	appendSuperb : function(superbs) {
		
		if(superbs == ''|| superbs.length == 0) {
			return -1;
		}
		
		var superbSize = parseInt((winWidth - 2*4 - 2*5 - 2*3) / 3);
		var $superbWrap = $("#superbs");
		var $superb;
		for(var i in superbs) {
			$superb = $('<a class="superb" href="/DT'+superbs[i]['shortLink']+'">'
					  + '<img src="'+superbs[i]['titleThumbPath']+'" width="'+superbSize+'" height="'+superbSize+'" /></a>');
			$superb.css({"width":superbSize + "px", "height":superbSize+"px"});
			$superbWrap.append($superb);
		}
		
		if(maxSuperbId == 0) {
			$("#superb-wrap").show();
		}
		
		return 0;
	},
	appendLatest : function(latests) {
		if(latests == ''|| latests.length == 0) {
			return -1;
		}
		
		if(latests.length = 4) {
			$("#latest-more").show();
		}
		
		var $latest;
		var count = 0;
		var latestsSize = parseInt((winWidth - 2*4 - 2*5 - 2*3) / 3);
		var $latestsWrap = $("#latests");
		for(var i in latests) {
			if(latests[i]['shortLink'] == shortLink)
				continue;
			
			if(count == 3) 
				break;
			
			$latest = $('<a class="latest" href="/DT'+latests[i]['shortLink']+'">'
					  + '<img src="'+latests[i]['titleThumbPath']+'" width="'+latestsSize+'" height="'+latestsSize+'" /></a>');
			$latest.css({"width":latestsSize + "px", "height":latestsSize+"px"});
			$latestsWrap.append($latest);
			++count;
		}
		if(count > 0) {
			$("#latest-wrap").show();
		}
		return 0;
	},
	initChildWorld : function(config, desc) {
		var worldSettings = {
			adminKey			: config.key,
			worldId 			: worldId, // 浏览的世界ID
			ver					: 1,
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
			url 				: '/ztworld/ztworld_queryTitleChildWorldPage', // 数据获取地址
			loadMoreURL			: '/ztworld/ztworld_queryChildWorldPage', // 加载更多子世界地址
			inZoomfactor 		: 5, // 背景图渐变过程中放大的倍数
			inSpeedfactor 		: 650, // 渐变速度
			inImgdelayfactor 	: 3, // 渐变延迟时间
			outZoomfactor 		: 3, // 背景图渐变过程中放大的倍数
			outSpeedfactor 		: 650, // 渐变速度
			outImgdelayfactor 	: 0
		};
		$('.zt-container:eq(0)').htszoomtour(worldSettings);
	},
	appendComment : function(comments) {
		var comment, author, $comment, $verifyIcon, shareComment, shareCommentCount;
		shareCommentCount = 0;
		var $commentWrap = $("#comment-wrap");
		var now = new Date();
		
		if(comments == ''|| comments.length == 0) {
			return -1;
		}
		
		for(var i in comments) {
			comment = comments[i];
			author = comment['userInfo'];
			
			if(author['verifyIcon'] != '')
				$verifyIcon = '<img class="verify" src="'+author['verifyIcon']+'"/>';
			else
				$verifyIcon = '';
			
			$comment = 
				$('<div class="comment">'
				+ '<div class="avatar-wrap" data-checksum="'+author["checksum"]+'">'
				+ '<img class="avatar" alt="" src="'+author['userAvatar']+'" />'
				+ '<div class="border"></div>'
				+ $verifyIcon
				+ '</div>'
				+ '<div class="comment-content-wrap">'
				+ '<div class="comment-user-name-wrap">'
				+ '<span　class="comment-user-name">'+author['userName']+'</span>'
				+ '<span class="comment-date">'+ui.dateformat(comment['commentDate'], now)+'</span>'
				+ '</div>'
				+ '<div class="comment-content">'+comment['content']+'</div>'
				+ '<hr class="comment-dividing"/>'
				+ '</div>'
				+ '</div>');
			
			$commentWrap.append($comment);
			
			if(maxCommentId == 0 && comment['reAuthorId'] == 0) {
				if(shareCommentCount == 0) {
					shareComment = "[评论]:" + comment['content'];
				} else if(shareCommentCount <= 3) {
					shareComment = shareComment + " || " + comment['content'];
				}
				++shareCommentCount;
			}
		}
		
		if(maxCommentId == 0) {
			$commentWrap.show();
			$("meta[name=description]").attr('content', shareComment);
		}
		
		return 0;
	},
	showCommentLoading : function() {
		$("#comment-fetch-loading").show();
		$("#comment-fetch-btn").hide();
	},
	hideCommentLoading : function(hasMore) {
		if(hasMore) {
			$("#comment-fetch-btn").show();
		} else {
			$("#comment-fetch-btn-wrap").hide();
		}
		$("#comment-fetch-loading").hide();
	},
	showSuperbLoading : function() {
		$("#superb-fetch-loading-wrap").show();
	},
	hideSuperbLoading : function() {
		$("#superb-fetch-loading-wrap").hide();
	},
	gohome : function(checksum) {
		window.location.href = "/home?s=" + checksum;
	},
	dateformat : function(dateStr, now) {
		var res, date;
		date = new Date((dateStr).replace(new RegExp("-","gm"),"/"))
		var secondPassed = (now.getTime() - date.getTime()) / 1000;
		if(secondPassed < 60) {
			res = "刚刚";
		} else if(secondPassed < 3600) {
			res = parseInt(secondPassed/60) + "分钟前";
		} else if(secondPassed < 60*60*24) {
			res = parseInt(secondPassed/(60*60)) + "小时前";
		} else if(secondPassed < 60*60*24*30) {
			res = parseInt(secondPassed/(60*60*24)) + "天前";
		} else {
			res = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate();
		}
		return res;
	},
	countformat : function(count) {
		var res;
		if(count < 1000) {
			res = count;
		} else if(count < 100000) {
			res = (parseFloat(count) / 1000).toFixed(1) + "k";
		} else {
			res = (parseFloat(count) / 10000).toFixed(1) + "w";
		}
		return res;
	}
};
var ajax = {
	fetchWorld : function(shortLink, likeLimit) {
		$.post('/ztworld/interact_queryInteractByLink', {
			'shortLink':shortLink,
			'likedLimit':likeLimit
		}, function(data) {
			if(data['result'] == 0) {
				ui.initWorld(data);
				ajax.fetchComment(worldId, likeLimit);
				ajax.fetchLatest(userId);
			} else {
			}
		}, 'json');
	},
	fetchComment : function(worldId) {
		ui.showCommentLoading();
		$.post('/ztworld/interact_queryOpenComment', {
			'worldId':worldId,
			'maxId':maxCommentId,
			'start':1,
			'limit':20
		 },function(result) {
			 if(result['result'] == 0) {
				 var len = result['comments'].length;
				 if(len > 0) {
					 ui.appendComment(result['comments']);
					 maxCommentId = result['comments'][len-1]['id']-1;
					 if(len < 20 || maxCommentId <= 0)
						 ui.hideCommentLoading(false);
					 else
						 ui.hideCommentLoading(true);
				 } else {
					 ui.hideCommentLoading(false);
				 }
			 } else {
				 ui.hideCommentLoading(true);
			 }
		 },'json');
	},
	fetchSuperb : function() {
		if(isSuperbloading)
			return;
		
		isSuperbloading = true;
		ui.showSuperbLoading();
		$.post('/operations/ztworld_querySuperbTypeSquareListV2', {
			'maxId':maxSuperbId,
			'typeId':0,
			'trimConcernId':'true',
			'commentLimit':0,
			'likedLimit':0,
			'completeLimit':24,
			'limit':24
		 },function(result) {
			 if(result['result'] == 0) {
				 var len = result['htworld'].length;
				 if(len > 0) {
					 ui.appendSuperb(result['htworld']);
					 maxSuperbId = result['htworld'][len-1]['recommendId']-1;
					 if(maxSuperbId <= 0) { // 已经没有数据了，不允许再次加载
						 ui.hideSuperbLoading();
						 isSuperbloading = true;
						 return;
					 }
				 }
			 }
			 ui.hideSuperbLoading();
			 isSuperbloading = false;
		 },'json');
	},
	fetchLatest : function(userId) {
		$.post('/ztworld/ztworld_queryUserLastNThumb', {
			'userId':userId,
			'limit':4
		},function(result) {
			 if(result['result'] == 0) {
				 ui.appendLatest(result['htworld']);
			 }
		},'json');
	},
};