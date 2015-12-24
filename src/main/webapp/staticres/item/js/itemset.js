var winWidth, processMarginTop, config, inapp, uid, sid;
var isFinished = false;
var leftTimeInterval;
var now;
now = new Date();

$(document).ready(function() {
	winWidth = ui.getWinWidth();
	inapp = ui.getInapp();
	uid = ui.getUID();
	sid = ui.getSetId();
	ui.initLayout();
	
	ajax.fetchSet(ui.getSetId(), ui.getUID());
	ajax.fetchItemShow(ui.getSetId());
	
	$(".like-btn").live("click", function() {
		var $this = $(this);
		if(!$this.data("liked")) {
			ajax.like(uid, $this.data("item-id"), $this);
		}
	});
	
	$(".buy-btn").live("click", function() {
		if(isFinished)
			return false;
		
	});
	
});

var ui = {
	getWinWidth : function() {
		return $(window).width();
	},
	getInapp : function() {
		var reg = new RegExp("(^|&)inapp=([^&]*)(&|$)","i");
		var r   = window.location.search.substr(1).match(reg);
		if(r != null && r[2] == 1)
			return true;
		return false;
	},
	getUID : function() {
		var reg = new RegExp("(^|&)uid=([^&]*)(&|$)","i");
		var r   = window.location.search.substr(1).match(reg);
		if(r != null)
			return r[2];
		return -1;
	},
	getSetId : function() {
		var reg = new RegExp("(^|&)itemSetId=([^&]*)(&|$)","i");
		var r   = window.location.search.substr(1).match(reg);
		if(r != null)
			return r[2];
		return -1;
	},
	initLayout : function() {
		var scale = 1,
			width = winWidth,
			height = winWidth,
			radiu = 25;
		config = {w:width,h:height,r:radiu,s:scale};
		
		processMarginTop = winWidth - 4;
		
	},
	
	initSetInfo : function(setInfo) {
		$("#set-banner").attr("src", setInfo["bulletinPath"]);
		$("#set-desc").text(setInfo["bulletinDesc"]);
		ui.initLeftTime(setInfo["deadline"]);
	},
	
	hidePageLoading : function() {
		$("#loading").hide();
		$("#main").show();
	},
	
	initShareInfo : function(setInfo) {
		var thumb = setInfo['bulletinThumb'] == "" ? setInfo['bulletinPath'] : setInfo['bulletinThumb'];
		$("#share-img").attr("src", thumb);
		if(inapp == 1)
			$("meta[name=description]").attr('content', setInfo['bulletinName']);
		else
			$("meta[name=description]").attr('content', setInfo['bulletinDesc']);
        document.title = setInfo["bulletinName"];
	},
	
	appendItem : function(items) {
		if(items == ''|| items.length == 0) {
			return -1;
		}
		
		var $itemWrap, $item, item;
		$itemWrap = $("#item-wrap");
		
		for(var i in items) {
			item = items[i];
			$item = ui.getItemUI(items, i);
			if(item['worldId'] != 0)
				ui.initChildWorld(item['worldId'], $item, "");
			
			$itemWrap.append($item);
		}
	},
	
	/**
	 * 渲染买家秀模块
	 * mishengliang
	 */
	appendItemShow : function(itemShows){
		if(itemShows == '' || itemShows.length == 0){
			return -1;
		}

		var $itemShowWrap,$itemShow,itemShow;
		$itemShowWrap = $("#item-show-wrap");

		for(var i in itemShows){
			itemShow = itemShows[i];
			$itemShow = ui.getItemShowUI(itemShows,i);
			if(itemShow.worldId != 0){
				ui.initChildWorld(itemShow.worldId,$itemShow,"");
				$itemShowWrap.append($itemShow);
			}
		}
		
	},
	
	getItemUI :function(items, index) {
		var item = items[index];
		var $content,
			$dividingLine, 
			$likeIcon, 
			$buyText;  // 购买按钮文本
		
		if(item['worldId'] != 0)
			$content = ui.getWorldUI(item['imgPath']);
		else
			$content = '<img class="full-screen" src="'+item["imgPath"]+'" />';
		
		
		if(index != items.length - 1)
			$dividingLine = '<div class="dividing-line"></div>';
		else 
			$dividingLine = '';
		
		if(item['isLiked']) {
			$likeIcon = '/staticres/item/images/liked.png';
		} else {
			$likeIcon = '/staticres/item/images/unlike.png';
		}
		
		if(isFinished) {
			$buyText = "已结束";
		} else {
			$buyText = "了解详情"
		}
		
		var $item = 
			$('<div class="item">'
			+ '	<div class="item-title"><span class="line-num">'+(parseInt(index)+1)+'</span>'+item["summary"]+'</div>'
			+ '	<div class="item-content">'
			+ $content
			+ '	</div>'
			+ '	<div class="item-desc">'
			+ item['description']
			+ '	</div>'
			+ '	<div class="item-price-wrap">'
			+ '		<span class="price-label">价格:</span><span class="price">'+item['price']+'</span><span class="price-unit">元</span>'
			+ '		<a class="buy-btn" href="'+ui.getClickLink(item["itemId"], item["itemType"], item["link"])+'">'+$buyText+'</a>'
			+ '		<a class="like-btn" data-item-id="'+item["id"]+'" data-liked="'+item["isLiked"]+'" href="javascript:void(0);"><img class="like-icon" src="'+$likeIcon+'" /><span class="like-count">'+ui.countformat(item["like"])+'</span></a>'
			+ '	</div>'
			+ $dividingLine
			+ '</div>'
			);
		return $item;
	},
	
	/**
	 * 展示买家秀每个模块
	 * mishengliang
	 */
	getItemShowUI :function(itemShows, index) {
		var itemShow = itemShows[index];
		var $content,
			$itemShowAdd,
			$dividingLine, 
			$userInfo,
			$verify,
			
		
		//织图初始化
		$content = ui.getWorldUI(itemShow['title_thumb_path']);
		
		//用户信息展示
		$userInfo = 
				'<div class="userInfo">'
			+ '	<span>'
			+'			<img class="userAvatar" alt="" src="' + itemShow['userAvatar'] + '">'
			+'		</span>'
			+'		<div class="userName" >'
			+ 			itemShow['userName'] 
			+'		</div>'
			+'</div>';
		
/*		//地址
		if(itemShow['addr'] != '' && itemShow['addr'] != null)
			$itemShowAdd = 
					'<div class="world-addr-out">' 
				+ '	<div class="world-addr" >'
				+ '		<div class="addrIcon">'
				+'				<img alt="" src="/staticres/item/images/localaddr.png">'
				+'			</div>'
				+'			<div class="addrText">'+ itemShow['addr'] +'</div>'
				+'		</div>'
				+'</div>';
		else
			$itemShowAdd = '';*/
		
		//地址
		$location = itemShow['addr'] == "" ? "" : 
			'    <div class="location-wrap">'
			+ '	     <img alt="" src="/staticres/htworld/phonev3/images/icon-loc.png">'
			+ '      <span class="location">'+itemShow['addr']+'</span>'
			+ '    </div>'

		//分割线
		if(index != itemShows.length - 1)
			$dividingLine = '<div class="dividing-line"></div>';
		else 
			$dividingLine = '';
		
		//用户认证标示
		$verify = itemShow['verifyIcon'] == "" ? "" : 
			'<img class="user-verify verify" src="'+itemShow['verifyIcon']+'" />'
		
/*		//买家秀单元模块
		var $itemShow = 
			$('<div class="itemShow">'
			+$userInfo
			+ '	<div class="item-show-content">'
			+ $content
			+ '	</div>'
			+$itemShowAdd
			+ ' 	<div class="item-desc">' + itemShow['worldDes'] + '</div>'
			+ $dividingLine
			+ '</div>'
			);*/
			
			//描述
			$desc = itemShow['worldDes'] == "" ? "" :
				'<div class="world-desc">'
				+ itemShow["worldDes"]
				+ '</div>'
		
		var $itemShow = 
			$('<div class="world">'
			+ '  <div class="user">'
			+ '    <div class="user-avatar-wrap avatar-wrap">'
			+ '      <img class="user-avatar avatar" alt="" src="'+itemShow['userAvatar']+'" />'
			+ '      <div class="border"></div>'
			+        $verify
			+ '  </div>'
			+ '  <div class="user-name-wrap">'
			+ '    <div class="user-name">'+itemShow['userName'] +'</div>'
			+      $location
			+ '  </div>'
			+ '  <div class="count-wrap">'
			+ '    <div class="child-count-wrap">'
			+ '	     <span class="child-count">'+itemShow["childCount"]+'</span>张<span> | </span><span id="date-modified">'+ui.dateformat(itemShow['dateModified'], now)+'</span>'
			+ '    </div>'
			+ '    <div class="click-count-wrap">'
			+ '      <span class="click-count">'+ui.countformat(itemShow['clickCount'])+'</span><span>浏览</span>'
			+ '    </div>'
			+ '</div>'
		    + '</div>'
		    + '<div class="world-info" style="width:'+winWidth+';height:'+winWidth+'px">'
			+ '<div class="zt-container" style="width:'+winWidth+';height:'+winWidth+'px">'
			+ '  <div class="zt-title">'
			+ '    <img alt="" src="'+itemShow['title_thumb_path']+'">'
			+ '  </div>'
			+ '  <div class="zt-loading" style="margin-top:'+processMarginTop+'px">'
			+ '    <div class="zt-progress"></div>'
			+ '  </div>'
			+ '  <img class="zt-desc-hide" title="隐藏描述" src="/staticres/htworld/phonev3/images/zt-desc-icon-up.png"/>'
			+ '  <img class="zt-desc-show" title="显示描述" src="/staticres/htworld/phonev3/images/zt-desc-icon-down.png"/>'
			+ '  <div class="zt-desc">'
			+ '  <div class="zt-desc-text"></div>'
			+ '  <div class="zt-desc-bg"></div>'
			+ '</div>'
			+ '</div>'
			+ '</div>'
			+ $desc
			+ '<div class="btn-wrap">'
			+ '<a class="opt-btn" href="/index4ph.html"><img src="/staticres/htworld/phonev3/images/icon-like.png"/><span class="like-count">'+ui.countformat(itemShow["likeCount"])+'</span></a>'
			+ '<a class="opt-btn" href="/index4ph.html"><img src="/staticres/htworld/phonev3/images/icon-comment.png"/>评论</a>'
			+ '<a class="opt-btn share-btn" href="/DT'+itemShow["shortLink"]+'"><img src="/staticres/htworld/phonev3/images/icon-share.png"/>分享</a>'
			+ '</div>'
/*			+ ui.getWorldComments(itemShow['comments'], itemShow['commentCount'])
			+ ui.getWorldLabels(itemShow['worldLabel'], itemShow['channelNames'])*/
			+ '<div class="dividing-line"></div>'
			+ '</div>'
			);
		
		return $itemShow;
	},
	
	getWorldUI : function(path) {
		var $world = 
			'<div class="world-info" style="width:'+winWidth+';height:'+winWidth+'px">'
			+ '<div class="zt-container" style="width:'+winWidth+';height:'+winWidth+'px">'
			+ '  <div class="zt-title">'
			+ '    <img alt="" src="'+path+'">'
			+ '  </div>'
			+ '  <div class="zt-loading" style="margin-top:'+processMarginTop+'px">'
			+ '    <div class="zt-progress"></div>'
			+ '  </div>'
			+ '  <img class="zt-desc-hide" title="隐藏描述" src="/staticres/htworld/phonev3/images/zt-desc-icon-up.png"/>'
			+ '  <img class="zt-desc-show" title="显示描述" src="/staticres/htworld/phonev3/images/zt-desc-icon-down.png"/>'
			+ '  <div class="zt-desc">'
			+ '  <div class="zt-desc-text"></div>'
			+ '  <div class="zt-desc-bg"></div>'
			+ '</div>'
			+ '</div>'
			+ '</div>';
		return $world;
		
	},
	
	getClickLink : function(itemId, itemType, link) {
		if(!inapp)
			return link;
		else 
			return 'imzhitu://buyItem?itemId='+itemId+'&itemType=' + itemType + '&link=' + encodeURIComponent(link);
	},
	
	initChildWorld : function(worldId, $world, desc) {
		var worldSettings = {
			worldId 			: worldId, // 浏览的世界ID
			ver					: 1,
			desc 				: desc,
			scale 				: config.s, // 缩放比例
			width 				: config.w, // 显示框宽度
			height 				: config.h, // 显示宽长度
			radius				: config.r,	//圆圈半径
			maxContainWidth 	: config.w, // 播放容器最大宽度
			maxContainHeight	: config.h, // 播放容器最大高度
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
	
	countformat : function(count) {
		if(count == "") {
			return 0;
		}
		
		var res;
		if(count < 10000) {
			res = count;
		} else if(count < 100000) {
			res = (parseFloat(count) / 1000).toFixed(1) + "k";
		} else {
			res = (parseFloat(count) / 10000).toFixed(1) + "w";
		}
		return res;
	},
	
	initLeftTime : function(endTime) {
		if(endTime == undefined || endTime == "")
			return;
		
		
		if(parseInt((endTime-new Date().getTime())/1000) <= 0) {
			$("#left-time").text("已结束");
			isFinished = true;
		}
		
		$("#set-banner").one("load", function() {
			var bannerHeight = $("#set-banner-wrap").height();
			$("#left-time").css({"top": bannerHeight - 35}).show();
			
			if(!isFinished) {
				ui.refreshLeftTime(endTime);
				
				leftTimeInterval = setInterval(function(){
					ui.refreshLeftTime(endTime);
				}, 1000);
			}
			
		}).each(function() {
			  if(this.complete) $(this).load();
		});;
	},
	
	refreshLeftTime : function(endtime){
        var nowtime = new Date();
        var leftsecond=parseInt((endtime-nowtime.getTime())/1000);
        
        if(leftsecond > 0) {
        	
        	var text = "";
            d=parseInt(leftsecond/3600/24);
            h=parseInt((leftsecond/3600)%24);
            m=parseInt((leftsecond/60)%60);
            s=parseInt(leftsecond%60);

            if(d > 0) {
            	text = text + d + "天";
            }
            
            if(h > 0) {
            	text = text + h + "小时";
            }
            
            text = text + m + "分钟" + s + "秒";
            
            $("#left-time").text(text);
            
        } else {
        	$("#left-time").text("已结束");
        	clearInterval(leftTimeInterval);
        	isFinished = true;
        }
	},
	dateformat : function(dateStr, now) {
		alert(dateStr);
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
};

var ajax = {
	fetchSet : function(setId, uid) {
		$.post("/trade/item_queryItemInfo",{
			'itemSetId':setId,
			"uid":uid
		},function(result){
			if(result['result'] == 0){
				ui.hidePageLoading();
				ui.initSetInfo(result["itemSet"]);
				ui.initShareInfo(result["itemSet"]);
				ui.appendItem(result["items"]);
			}
		},"json");
	},
	
	like : function(uid, itemId, $like) {
		if(uid < 0)
			return;
		
		$.post("/trade/item_likeItem",{
			'itemId':itemId,
			"uid":uid
		},function(result){
			var $likeCount = $like.children(".like-count:eq(0)");
			var count = $likeCount.text();
			$likeCount.text(parseInt(count) + 1);
			$like.children(".like-icon:eq(0)").attr("src", 
					"/staticres/item/images/liked.png");
			$like.data('liked', true);
		},"json");
		
	},
	
	/**
	 * mishengliang
	 * 获取集合下的商品秀信息
	 */
	fetchItemShow : function(setId){
		$.post("/trade/itemShow_queryItemShow",{
			'itemSetId':setId
				},function(data){
					if(data.result == 0){
						ui.appendItemShow(data["rows"]);
					}else{
						alert(data.result);
					}
				},"json");
	}
};

	/**
	 * 控制展示内容
	 */
	var apper = {
			itemApper : function(){
				$('.item-select').css('color','#000000');
				$('.show-select').css('color','#999EA2');
				$('#item-show-wrap').hide();
				$('#item-wrap').show();
			},
	
			showApper : function(){
				$('.show-select').css('color','#000000');
				$('.item-select').css('color','#999EA2');
				$('#item-wrap').hide();
				$('#item-show-wrap').show();
			}
	};