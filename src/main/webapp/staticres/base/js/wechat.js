/*
 * 微信分享脚本
 * 
 * 详情见:http://mp.weixin.qq.com/wiki/7/1c97470084b73f8e224fe6d9bab1625b.html
 */
var wxtool = {
	post : function(url, callback) {
		var xmlhttp=null;
		if (window.XMLHttpRequest) {
			xmlhttp=new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		if (xmlhttp != null) {
			
			xmlhttp.onreadystatechange=function() {
				if (xmlhttp.readyState==4) {
			  		if (xmlhttp.status==200) {
			    		callback(xmlhttp.responseText);
				  	} else {
				 	}
			  	}
			};
		  	xmlhttp.open("POST",url,true);
		  	xmlhttp.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded");
		  	xmlhttp.send();
		} else {
			// do with error
		}
	},
	/*　获取分享标题　*/
	getTitle : function() {
		return document.title;
	},
	/* 获取分享描述　*/
	getDescription : function() {
		var descriptions = document.getElementsByName('description');
		if(descriptions != undefined && descriptions.length > 0) 
			return descriptions[0].getAttribute('content');
		else 
			return null;
	},
	/* 获取分享图片 */
	getFirstImgUrl : function() {
		var imgs = document.getElementsByTagName('img');
		if(imgs != undefined && imgs.length > 0) 
			return imgs[0].getAttribute('src');
		else 
			return null;
	},
	/* 获取分享链接 */
	getLink : function() {
		return window.location.href;
	},
	/* 获取分享类型 */
	getType : function() {
		return 'link';
	}
};

/*　
 * 获取签名　
 */
wxtool.post("/wechat/signature", function(data) {
	var res = JSON.parse(data);
	if(res['result'] == 0) {
		var o = res['msg'];
		wx.config({
		    debug: false,
		    appId: o.appid,
		    timestamp: o.timestamp,
		    nonceStr: o.noncestr,
		    signature: o.signature,
		    jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage', 
		                'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone']
		});
		
		wx.error(function(res){
			console.log(res);
		});
		
		wx.ready(function(){
		    wx.onMenuShareTimeline({
		        title: wxtool.getTitle(),
		        link: wxtool.getLink(),
		        imgUrl: wxtool.getFirstImgUrl()
		    });
		    
		    wx.onMenuShareAppMessage({
		        title: wxtool.getTitle(),
		        desc: wxtool.getDescription(),
		        link: wxtool.getLink(),
		        imgUrl: wxtool.getFirstImgUrl(),
		        type: wxtool.getType(),
		    });
		    
		    wx.onMenuShareQQ({
		        title: wxtool.getTitle(),
		        desc: wxtool.getDescription(),
		        link: wxtool.getLink(),
		        imgUrl: wxtool.getFirstImgUrl(),
		        success: function () { 
		        },
		        cancel: function () { 
		        }
		    });
		    
		    wx.onMenuShareWeibo({
		        title: wxtool.getTitle(),
		        desc: wxtool.getDescription(),
		        link: wxtool.getLink(),
		        imgUrl: wxtool.getFirstImgUrl(),
		        success: function () { 
		        },
		        cancel: function () { 
		        }
		    });
		    
		    wx.onMenuShareQZone({
		        title: wxtool.getTitle(),
		        desc: wxtool.getDescription(),
		        link: wxtool.getLink(),
		        imgUrl: wxtool.getFirstImgUrl(),
		        success: function () { 
		        },
		        cancel: function () { 
		        }
		    });
		    
		});
		
	}
});


