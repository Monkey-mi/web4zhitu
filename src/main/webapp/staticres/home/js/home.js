var winWidth,s;

$(document).ready(function() {
	winWidth = ui.getWinWidth();
	s = ui.getS();
	ui.initLayout();
	ajax.fetchUserInfo(s);
	ajax.fetchWorldInfo(s);
});

var ui = {
	getWinWidth : function() {
		return $(window).width();
	},
	getS : function() {
		var reg = new RegExp("(^|&)s=([^&]*)(&|$)","i");
		var r   = window.location.search.substr(1).match(reg);
		var s   = r[2];
		if(s != undefined) {
			return s;
		}
		return -1;
	},
	initLayout : function() {
		var avatarWidth = 100;
		$("#concern-btn").width(winWidth - avatarWidth + 'px');
		$("#address, #job").width((winWidth - 170) / 2);
		$(".personal-info-wrap").css({"display":"inline-block"}).show();
		
	},
	initUserInfo : function(user) {
		var job, addr, sex;
		
		job = $.trim(user['job']) == "" ? "职业:暂无" : $.trim(user['job']);
		addr = $.trim(user['address']) == "" ? "地点:暂无" : $.trim(user['address']);
		sex = user['sex'] == 2 ? "女" : "男";

		$("#job").text(job);
		$("#address").text(addr);
		$("#sex").text(sex);
		
		$("#world-count").text(user["worldCount"]);
		$("#child-count").text(user["childCount"]);
		$("#concern-count").text(user["concernCount"]);
		$("#follow-count").text(user["followCount"]);
		
		if(user['signature'] != "") {
			$("#signature").text(user['signature']).show();
		}
		
		$("#user-avatar, #share-img").attr("src", user["userAvatarL"]);
		$("#user-avatar-wrap").css({"visibility": "visible"});
		
		if(user['verifyName'] != "") {
			$("#verify-icon").attr("src", user["verifyIcon"]);
			$("#verify-name").text(user['verifyName']);
			$("#verify-name-wrap").css({"display":"inline-block"}).show();
		}
		ui.initShare(user);
	},
	initShare : function(user) {
		var title = user['worldCount'] > 0 ? user['userName'] + " | 相册×" + user['worldCount'] 
			: user['userName'];
		
		var desc = "";
		var sex = user['sex'] == 2 ? "女" : "男";
		var addr = $.trim(user['address']) == "" ? "来自" + addr : "";
		
		desc = desc + sex + "生";
		
		if(user['verifyName'] != "") {
			desc = desc + "(" + user['verifyName'] + ")";
		}
		
		if(user['followCount'] > 0) {
			desc = desc + "，粉丝×" + user['followCount'];
		}
		
		if($.trim(user['address']) != "") {
			desc = desc + "，来自: " + $.trim(user['address']);
		}
		
		if($.trim(user['job']) != "") {
			desc = desc + "，职业: " + $.trim(user['job']);
		}
		
		if($.trim(user['signature']) != "") {
			desc = desc + "，个人介绍: " + $.trim(user['signature']);
		}
		
		$("meta[name=description]").attr('content', desc);
		
        var $body = $('body');
        document.title = title;
        var $iframe = $('<iframe src="/favicon.ico"></iframe>');
        $iframe.hide();
        $iframe.on('load',function() {
            setTimeout(function() {
                $iframe.off('load').remove();
            }, 0);
        }).appendTo($body);
		
	},
	appendThumb : function(thumbs) {
		if(thumbs == ''|| thumbs.length == 0) {
			return -1;
		}
		 
		var thumbSize = parseInt((winWidth - 2*4 - 2*5 - 2*2) / 3);
		var $thumbWrap = $("#worlds");
		var $thumb;
		for(var i in thumbs) {
			$thumb = $('<a class="world-thumb" href="/DT'+thumbs[i]['shortLink']+'">'
					  + '<img src="'+thumbs[i]["titleThumbPath"]+'" width="'+thumbSize+'" height="'+thumbSize+'" /></a>');
			$thumb.css({"width":thumbSize + "px", "height":thumbSize+"px"});
			$thumbWrap.append($thumb);
		}
		
		$thumbWrap.css({"width":thumbSize * 3 + 2*4 + 2*2});
		$("#ad-dividing-top").show();
		$("#world-wrap").show();
		
		return 0;
	},
	
	appendWorld : function(worlds) {
		if(worlds == ''|| worlds.length == 0) {
			return -1;
		}
		
		var processMarginTop = winWidth - 4;
		
		var scale = 1,
		width = winWidth,
		height = winWidth,
		radiu = 25,
		config = {w:width,h:height,r:radiu,s:scale};
		
		var $wrap = $("#worlds");
		var $world, $verify, $location, $desc;
		var user, world, worldId;
		var now;
		now = new Date();
		
		for(var i in worlds) {

			world = worlds[i];
			user = worlds[i]['userInfo'];
			
			$verify = user['verifyIcon'] == "" ? "" : 
				'<img class="user-verify verify" src="'+user['verifyIcon']+'" />'
				
			$location = world['locationAddr'] == "" ? "" : 
				'    <div class="location-wrap">'
				+ '	     <img alt="" src="/staticres/htworld/phonev3/images/icon-loc.png">'
				+ '      <span class="location">'+world['locationAddr']+'</span>'
				+ '    </div>'
				
			$desc = world["worldDesc"] == "" ? "" :
				'<div class="world-desc">'
				+ world["worldDesc"]
				+ '</div>'
			
			$world = 
				$('<div class="world">'
				+ '  <div class="user">'
				+ '    <div class="user-avatar-wrap avatar-wrap">'
				+ '      <img class="user-avatar avatar" alt="" src="'+user["userAvatar"]+'" />'
				+ '      <div class="border"></div>'
				+        $verify
				+ '  </div>'
				+ '  <div class="user-name-wrap">'
				+ '    <div class="user-name">'+user["userName"]+'</div>'
				+      $location
				+ '  </div>'
				
				+ '  <div class="count-wrap">'
				+ '    <div class="child-count-wrap">'
				+ '	     <span class="child-count">'+world["childCount"]+'</span>张<span> | </span><span id="date-modified">'+ui.dateformat(world['dateModified'], now)+'</span>'
				+ '    </div>'
				+ '    <div class="click-count-wrap">'
				+ '      <span class="click-count">'+ui.countformat(world['clickCount'])+'</span><span>浏览</span>'
				+ '    </div>'
				+ '</div>'
			    + '</div>'
			    + '<div class="world-info" style="width:'+winWidth+';height:'+winWidth+'px">'
				+ '<div class="zt-container" style="width:'+winWidth+';height:'+winWidth+'px">'
				+ '  <div class="zt-title">'
				+ '    <img alt="" src="'+world['titlePath']+'">'
				+ '  </div>'
				+ '  <div class="zt-loading" style="margin-top:'+processMarginTop+'px">'
				+ '    <div class="zt-progress"></div>'
				+ '  </div>'
				+ '  <div class="zt-desc-hide" title="隐藏描述" ></div>'
				+ '  <div class="zt-desc-show" title="显示描述"></div>'
				+ '  <div class="zt-desc">'
				+ '  <div class="zt-desc-text"></div>'
				+ '  <div class="zt-desc-bg"></div>'
				+ '</div>'
				+ '</div>'
				+ '</div>'
				+ $desc
				+ '<div class="btn-wrap">'
				+ '<a class="opt-btn" href="/index4ph.html"><img src="/staticres/htworld/phonev3/images/icon-like.png"/><span class="like-count">'+ui.countformat(world["likeCount"])+'</span></a>'
				+ '<a class="opt-btn" href="/index4ph.html"><img src="/staticres/htworld/phonev3/images/icon-comment.png"/>评论</a>'
				+ '<a class="opt-btn share-btn" href="/DT'+world["shortLink"]+'"><img src="/staticres/htworld/phonev3/images/icon-share.png"/>分享</a>'
				+ '</div>'
				+ ui.getWorldComments(world['comments'], world['commentCount'])
				+ ui.getWorldLabels(world['worldLabel'], world['channelNames'])
				+ '<div class="dividing-line"></div>'
				+ '</div>'
				);
				$wrap.append($world);
				ui.initChildWorld(world['id'], $world, config, world['worldDesc']);
		}
		$("#world-wrap").css({"margin-top":0}).show();
		
		return 0;
	},
	
	initChildWorld : function(worldId, $world, config, desc) {
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
			outImgdelayfactor 	: 0,
			multiple			: true
		};
		$world.find('.zt-container:eq(0)').htszoomtour(worldSettings);
	},
	
	initAd : function() {
		$(".slide").css({
			"width":winWidth -10 - 4 + "px",
			"height":142 * (winWidth/306) + "px",
		});
		
		$(".slide .div-slide-img").css({
			"width":winWidth * 5 + "px"
		});
		
		$(".slide img").css({
			"width":winWidth + "px"
		});
		
		$(".div-slide-btn").css({
			"width":winWidth + "px",
		});
		
		$("#ad-wrap").show();
		zBase.init(winWidth,1000,3000,'div-slide','div-slide-img','div-slide-btn');
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
	},
	getWorldComments : function(comments, total) {
		if(comments == "" || comments.length == 0) {
			return "";
		}
		
		var wrap = "",
			$comment = "";
	
		for(var i in comments) {
			$comment += '<div class="world-comment"><span class="comment-name">'+comments[i]['userInfo']['userName']
				+': </span>'+comments[i]["content"]+'</div>';
		}
		
		if($comment != "")  {
			wrap =
				 '<div class="comment-wrap">'
				+ $comment
				+ '<div class="comment-total-tip">所有评论('+total+'条)</div>'
				+ '</div>';
		}
		return wrap;
		
	},
	getWorldLabels : function(labelsStr, channels) {
		
		if(labelsStr == '' && channels == '')
			return "";
		
		var wrap = "",
			labels = "",
			labelArray = labelsStr.split(',');
		
		for(var i in channels) {
			labels += '<div class="label channel">'+channels[i]['name']+'</div>';
		}
		
		for(var i in labelArray) {
			if(labelArray[i] == '')
				break;
			labels += '<div class="label">'+labelArray[i]+'</div>';
		}
		
		if(labels != "")  {
			wrap =
				 '<div class="label-wrap">'
				+ '<div class="label-name">标签:</div>'
				+ '<div class="labels">'
				+ labels
				+ '</div>'
				+ '</div>';
		}
		return wrap;
	},
	showFetchWorldLoading : function() {
		$("#fetch-world-loading-wrap").show();
	},
	hideFetchWorldLoading : function() {
		$("#fetch-world-loading-wrap").hide();
	},
	showWorldEmpty : function() {
		
		$("#world-empty-wrap").show();
	}
};

var ajax = {
	fetchUserInfo : function(s) {
		$.post("./user/user_getIndividualHomePage",{
			's':s
		},function(result){
			if(result['result'] == 0){
				ui.initUserInfo(result['userInfo']);
			}
		},"json");
	},
	fetchWorldInfo : function(s) {
		var limit = 9;
		$.post("./ztworld/ztworld_getLastNWorldByUserId",{
			's':s,
			'limit':limit
		},function(result){
			if(result['result'] == 0){
				var worldList = result['htworld'];
				var len = worldList.length;
				
				if(len == 9) {
					ui.appendThumb(worldList);
					ui.initAd();
				} else if(len > 0) {
					ui.appendWorld(worldList);
					ui.initAd();
				} else {
					ui.showWorldEmpty();
				}
				
			} else {
				ui.showWorldEmpty();
			}
			ui.hideFetchWorldLoading();
		},"json");
	}
};