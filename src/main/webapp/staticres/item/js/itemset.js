var winWidth, processMarginTop, config, inapp, uid, sid;
var isFinished = false;
var leftTimeInterval;

var data = 
[
{
    "id": "1",
    "name": "无人机",
    "summary": '无人机一号之"我是无人机你想怎样".',
    "description": "纯棉面料和细纱布表面很有光泽度，舒适透气，质地和做工都很好，细节做的很用心，看似简单的裙子却是花了很大的心思，裙摆形成褶皱非常好看，很秀气，松紧腰，适合更多人群裙摆圆滑的弧度，优雅大方，款式更加出众上身乍一眼并不惊艳，可是很耐看，收到的时候你一定会惊喜。北京设计师原创设计，活在当下做自己！嘿尔闹出品",
    "worldId": "0",
    "imgPath": "http://imzhitu.qiniudn.com/trade/item/medium_1.jpg",
    "imgThumb": "http://imzhitu.qiniudn.com/trade/item/medium_1.jpg",
    "price": "1012.0",
    "sale": "100",
    "sales": "100",
    "stock": "100",
    "itemId": "42358748787",
    "itemType": "2",
    "link": "http://s.click.taobao.com/0JXOxex",
    "like": "1000000",
    "isLiked": "true"
},
{
    "id": "1",
    "name": "无人机1",
    "summary": '无人机一号之"我也是无人机你想怎样".',
    "description": "纯棉面料和细纱布表面很有光泽度，舒适透气，质地和做工都很好，细节做的很用心，看似简单的裙子却是花了很大的心思，裙摆形成褶皱非常好看，很秀气，松紧腰，适合更多人群裙摆圆滑的弧度，优雅大方，款式更加出众上身乍一眼并不惊艳，可是很耐看，收到的时候你一定会惊喜。北京设计师原创设计，活在当下做自己！嘿尔闹出品",
    "worldId": "1774418",
    "imgPath": "http://imzhitu.qiniudn.com/ios/image/2015/05/02/16/216f24cadbee2570f4d6236737caae60_20989@2x.jpg",
    "imgThumb": "http://imzhitu.qiniudn.com/ios/image/2015/05/02/16/216f24cadbee2570f4d6236737caae60_20989@2x.jpg",
    "price": "3412.0",
    "sale": "100",
    "sales": "100",
    "stock": "100",
    "itemId": "42358748787",
    "itemType": "2",
    "link": "http://s.click.taobao.com/0JXOxex",
    "like": "100",
    "isLiked": "false"
},
{
    "id": "1",
    "name": "无人机2",
    "summary": '无人机三号之"我还是无人机你想怎样".',
    "description": "纯棉面料和细纱布表面很有光泽度，舒适透气，质地和做工都很好，细节做的很用心，看似简单的裙子却是花了很大的心思，裙摆形成褶皱非常好看，很秀气，松紧腰，适合更多人群裙摆圆滑的弧度，优雅大方，款式更加出众上身乍一眼并不惊艳，可是很耐看，收到的时候你一定会惊喜。北京设计师原创设计，活在当下做自己！嘿尔闹出品",
    "worldId": "3570272",
    "imgPath": "http://static.imzhitu.com/ios/image/2015/11/06/12/95e17bc38a758336176f96eb9851c599_30002@2x.jpg",
    "imgThumb": "http://static.imzhitu.com/ios/image/2015/11/06/12/95e17bc38a758336176f96eb9851c599_30002@2x.jpg",
    "price": "2012.0",
    "sale": "100",
    "sales": "100",
    "stock": "100",
    "itemId": "42358748787",
    "itemType": "2",
    "link": "http://s.click.taobao.com/0JXOxex",
    "like": "100"
},
];

$(document).ready(function() {
	winWidth = ui.getWinWidth();
	inapp = ui.getInapp();
	uid = ui.getUID();
	sid = ui.getSetId();
	ui.initLayout();
	ui.hidePageLoading();
	ui.initLeftTime("2015-12-11 21:00:00");
	ui.appendItem(data);
	
	ajax.fetchSet(ui.getSetId(), ui.getUID());
	
	$(".like-btn").live("click", function() {
		var $this = $(this);
		ajax.like(uid, $this.data("item-id"), $this);
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
		var reg = new RegExp("(^|&)inapp=([^&]*)(&|$)","i");
		var r   = window.location.search.substr(1).match(reg);
		if(r != null && r[2] == 1)
			return r[2];
		return -1;
	},
	getSetId : function() {
		var reg = new RegExp("(^|&)itemSetId=([^&]*)(&|$)","i");
		var r   = window.location.search.substr(1).match(reg);
		if(r != null && r[2] == 1)
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
		$("#set-desc").text(setInfo["bulletinName"]);
		ui.initLeftTime(setInfo["deadline"]);
	},
	
	hidePageLoading : function() {
		$("#loading").hide();
		$("#main").show();
	},
	
	initShareInfo : function(setInfo) {
		$("#share-img").attr("src", setInfo['bulletinPath']);
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
			$buyText = "购买"
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
			+ '		<a class="like-btn" data-item-id="'+item["id"]+'" data-islike="'+item["isLiked"]+'" href="javascript:void(0);"><img class="like-icon" src="'+$likeIcon+'" /><span class="like_count">'+ui.countformat(item["like"])+'</span></a>'
			+ '	</div>'
			+ $dividingLine
			+ '</div>'
			);
		return $item;
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
			+ '  <div class="zt-desc-hide" title="隐藏描述" ></div>'
			+ '  <div class="zt-desc-show" title="显示描述"></div>'
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
			return 'imzhitu://buyItem?itemId='+itemId+'&itemType=' + itemType + '&link=' + encodeURI(link);
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
	
	initLeftTime : function(endTimeStr) {
		if(endTimeStr == undefined && endTimeStr == "")
			return;
		
		endTime = new Date((endTimeStr).replace(new RegExp("-","gm"),"/"))
		
		if(parseInt((endTime.getTime()-new Date().getTime())/1000) <= 0) {
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
        var leftsecond=parseInt((endtime.getTime()-nowtime.getTime())/1000);
        
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
	
};

var ajax = {
	fetchSet : function(setId, uid) {
		$.post("./trade/item_queryItemInfo",{
			'itemSetId':setId,
			"uid":uid
		},function(result){
			if(result['result'] == 0){
				ui.hidePageLoading();
				ui.initSetInfo(result["itemSet"]);
				ui.appendItem(result["items"]);
			}
		},"json");
	},
	
	like : function(uid, itemId, $like) {
		if(uid < 0)
			return;
		
		$.post("./trade/item_likeItem",{
			'itemId':itemId,
			"uid":uid
		},function(result){
			if(result['result'] == 0){
				var $likeCount = $like.children(".like-count:eq(0)");
				var count = $likeCount.text();
				likeCount.text(parseInt(count) + 1);
				$like.children(".like-icon:eq(0)").attr("src", 
						"/staticres/item/images/liked.png");
			}
		},"json");
		
	}
};
