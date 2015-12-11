var winWidth, processMarginTop, config, inapp, uid, sid;

var data = 
[
{
    "id": "1",
    "name": "测试",
    "summary": '葫芦屯草莓一号之"我是番茄你想怎样".',
    "description": "纯棉面料和细纱布表面很有光泽度，舒适透气，质地和做工都很好，细节做的很用心，看似简单的裙子却是花了很大的心思，裙摆形成褶皱非常好看，很秀气，松紧腰，适合更多人群裙摆圆滑的弧度，优雅大方，款式更加出众上身乍一眼并不惊艳，可是很耐看，收到的时候你一定会惊喜。北京设计师原创设计，活在当下做自己！嘿尔闹出品",
    "worldId": "0",
    "imgPath": "http://static.imzhitu.com/android/image/2015/12/09/16/fyUzkcDiaOCUwUaTlfZhUClHugGvbHGt0cSSbHw.jpg",
    "imgThumb": "http://static.imzhitu.com/android/image/2015/12/09/16/fyUzkcDiaOCUwUaTlfZhUClHugGvbHGt0cSSbHw.jpg",
    "price": "12.0",
    "sale": "100",
    "sales": "100",
    "stock": "100",
    "itemId": "17349903224",
    "itemType": "2",
    "link": "http://s.click.taobao.com/OpqjBfx",
    "like": "1000000",
    "isLiked": "true"
},
{
    "id": "1",
    "name": "测试",
    "summary": '葫芦屯草莓一号之"我是番茄你想怎样".',
    "description": "纯棉面料和细纱布表面很有光泽度，舒适透气，质地和做工都很好，细节做的很用心，看似简单的裙子却是花了很大的心思，裙摆形成褶皱非常好看，很秀气，松紧腰，适合更多人群裙摆圆滑的弧度，优雅大方，款式更加出众上身乍一眼并不惊艳，可是很耐看，收到的时候你一定会惊喜。北京设计师原创设计，活在当下做自己！嘿尔闹出品",
    "worldId": "28654",
    "imgPath": "http://static.imzhitu.com/android/image/2015/12/09/16/fyUzkcDiaOCUwUaTlfZhUClHugGvbHGt0cSSbHw.jpg",
    "imgThumb": "http://static.imzhitu.com/android/image/2015/12/09/16/fyUzkcDiaOCUwUaTlfZhUClHugGvbHGt0cSSbHw.jpg",
    "price": "12.0",
    "sale": "100",
    "sales": "100",
    "stock": "100",
    "itemId": "17349903224",
    "itemType": "2",
    "link": "http://s.click.taobao.com/OpqjBfx",
    "like": "100",
    "isLiked": "false"
},
{
    "id": "1",
    "name": "测试",
    "summary": '葫芦屯草莓一号之"我是番茄你想怎样".',
    "description": "纯棉面料和细纱布表面很有光泽度，舒适透气，质地和做工都很好，细节做的很用心，看似简单的裙子却是花了很大的心思，裙摆形成褶皱非常好看，很秀气，松紧腰，适合更多人群裙摆圆滑的弧度，优雅大方，款式更加出众上身乍一眼并不惊艳，可是很耐看，收到的时候你一定会惊喜。北京设计师原创设计，活在当下做自己！嘿尔闹出品",
    "worldId": "28654",
    "imgPath": "http://static.imzhitu.com/android/image/2015/12/09/16/fyUzkcDiaOCUwUaTlfZhUClHugGvbHGt0cSSbHw.jpg",
    "imgThumb": "http://static.imzhitu.com/android/image/2015/12/09/16/fyUzkcDiaOCUwUaTlfZhUClHugGvbHGt0cSSbHw.jpg",
    "price": "12.0",
    "sale": "100",
    "sales": "100",
    "stock": "100",
    "itemId": "17349903224",
    "itemType": "2",
    "link": "http://s.click.taobao.com/OpqjBfx",
    "like": "100"
},
];

$(document).ready(function() {
	winWidth = ui.getWinWidth();
	inapp = ui.getInapp();
	uid = ui.getUID();
	sid = ui.getSetId();
	ui.initLayout();
	ui.appendItem(data);
	//ajax.fetchSet(ui.getSetId(), ui.getUID());
	
	$(".like-btn").live("click", function() {
		var $this = $(this);
		ajax.like(uid, $this.data("item-id"), $this);
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
		var $content, $dividingLine, $likeIcon;
		
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
			+ '		<a class="buy-btn" href="'+ui.getClickLink(item["itemId"], item["itemType"], item["link"])+'">购买</a>'
			+ '		<a class="like-btn" data-item-id="'+item["id"]+'" data-islike="'+item["isLiked"]+'" href="javascript:void(0);"><img class="like-icon" src="'+$likeIcon+'" /><span>'+ui.countformat(item["like"])+'</span></a>'
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
	
};

var ajax = {
	fetchSet : function(setId, uid) {
		$.post("./trade/item_queryItemInfo",{
			'itemSetId':setId,
			"uid":uid
		},function(result){
			if(result['result'] == 0){
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
				$like.children(".likc-icon:eq(0)").attr("src", 
						"/staticres/item/images/liked.png");
			}
		},"json");
		
	}
};
