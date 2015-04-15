//download
function download(){
	if(navigator.userAgent.indexOf("iPhone")==-1){
		window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.htshuo.htsg&g_f=991653#opened";
	}else{
		window.location.href="http://um0.cn/5HgX8j/";
	}
};

function setCookie(name,value){
	var exp  = new Date();
	exp.setTime(exp.getTime()+24*60*60*1000);
	document.cookie = name + "=" + escape(value) + ";expires="+exp.toUTCString();
}

function getCookie(name){
	var arr = document.cookie.match(new RegExp("(^|)"+name+"=([^;]*)(;|$)"));
	if(arr != null)
		return (arr[2]);
	return null;
}

//baidu caculate
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fd52a9c49fb7a48b836a1a23f21b49f1d' type='text/javascript'%3E%3C/script%3E"));

//
var canvasTopVar = 160;
var newTipHeight = 20;
var downTopVar = 39;
var deep = 8;
var clickAreaTopVar = 140;
var opacity = 0;
var opacityDeep = 5;
var showTipDeep = 12;
var tipInterval;
var clickAreaChangeVar = 2;

/**
 * 显示运势
 */
function showFortune(){
	if(tipInterval != null){
		clearInterval(tipInterval);
		tipInterval = null;
		document.getElementById("click-area").style.display="none";
	}
	document.getElementById("canvas-area").className="";
	document.getElementById("canvasHiden").className="none";
	showCanvas();
}

function showTip(){
	var clickArea = document.getElementById("click-area");
	
	if(showTipDeep > 0){
		showTipDeep--;
		setTimeout(function(){
			clickAreaTopVar += clickAreaChangeVar;
			clickArea.style.top = clickAreaTopVar + "px";
			showTip();
		}, 80);
	} else {
		showTipDeep = 12;
		opacityAnimation();
	}
	if(showTipDeep % 3 == 0){
		clickAreaChangeVar = -1 * clickAreaChangeVar;
	}
}

function opacityAnimation(){
	if(opacity < 1){
		document.getElementById("click-area").style.opacity = opacity;
		opacity += 0.2;
		setTimeout(opacityAnimation, 200);
	} else {
		opacity = 0;
	}
}
/**
 * 显示运势动画
 */
function showCanvas(){
	
	setTimeout(function(){
		
		if(deep > 0){
			deep--;
			document.getElementById("canvas-area").style.top=canvasTopVar+"px";
			canvasTopVar -= 10;
			
			document.getElementById("newTip").style.height=newTipHeight+"px";
			newTipHeight += 20;
			
			document.getElementById("down").style.top=downTopVar+"px";
			downTopVar +=20;
			
			showCanvas();
		} else {
			canvasTopVar = 160;
			newTipHeight = 20;
			downTopVar = 39;
			deep = 8;
			document.getElementById("canvasHiden").className="";
		}
	}, 100)
	
}

/**
 * 隐藏运势
 */
function hideCanvas(){
	document.getElementById("canvas-area").className="none";
	document.getElementById("canvas-area").style.top="160px";
	document.getElementById("newTip").style.height="20px";
	document.getElementById("down").style.top="39px";
	document.getElementById("click-area").style.display="";
	tipInterval = setInterval(showTip,2000);
	//document.getElementById("canvasHiden").className="none";
}
function isCookieExist(){
	var constellation = getCookie("_CONSTELLATION");
	if(constellation == null){
		window.location.href="http://www.imzhitu.com/act/constellation.html";
	}
}

//
function parseConstellation(){
		var birthdayStr = document.getElementById("birthday_input").value;
		if(birthdayStr.length>0 && birthdayStr.length<6 ){
			var md = birthdayStr.split(".");
			if(md.length == 2){
				var month = parseInt(md[0], 10);
				var day = parseInt(md[1], 10);
				switch(month){
				case 1:
					if(day>0 && day<=18){
						window.location.href="http://www.imzhitu.com/act/capricorn2sagittarius.html";
					}else if(day == 19){
						window.location.href="http://www.imzhitu.com/act/capricorn.html";
					}else if(day >=20 && day <=31){
						window.location.href="http://www.imzhitu.com/act/aquarius2capricorn.html";
					}
					break;
				case 2:
					if(day>0 && day<=15){
						window.location.href="http://www.imzhitu.com/act/aquarius2capricorn.html";
					}else if(day >= 16 && day <=18){
						window.location.href="http://www.imzhitu.com/act/aquarius.html";
					}else if(day >=19 && day <=29){
						window.location.href="http://www.imzhitu.com/act/fish2aquarius.html";
					}
					break;
				case 3:
					if(day >0 && day <= 10){
						window.location.href="http://www.imzhitu.com/act/fish2aquarius.html";
					}else if(day >= 11 && day <=20){
						window.location.href="http://www.imzhitu.com/act/fish.html";
					}else if(day >= 21 && day <=31){
						window.location.href="http://www.imzhitu.com/act/aries2fish.html";
					}
					break;
				case 4:
					if(day > 0 && day <= 18){
						window.location.href="http://www.imzhitu.com/act/aries2fish.html";
					}else if(day == 19){
						window.location.href="http://www.imzhitu.com/act/aries.html";
					}else if(day >= 20 && day <= 30){
						window.location.href="http://www.imzhitu.com/act/taurus2aries.html";
					}
					break;
				case 5:
					if(day > 0 && day <= 13){
						window.location.href="http://www.imzhitu.com/act/taurus2aries.html";
					}else if(day >= 14 && day <= 20){
						window.location.href="http://www.imzhitu.com/act/taurus.html";
					}else if(day >= 21 && day <= 31){
						window.location.href="http://www.imzhitu.com/act/gemini2taurus.html";
					}
					break;
				case 6:
					if(day > 0 && day <= 20){
						window.location.href="http://www.imzhitu.com/act/gemini2taurus.html";
					}else if(day == 21){
						window.location.href="http://www.imzhitu.com/act/gemini.html";
					}else if(day >= 22 && day <= 30){
						window.location.href="http://www.imzhitu.com/act/cancer2gemini.html";
					}
					break;
				case 7:
					if(day > 0 && day <= 19){
						window.location.href="http://www.imzhitu.com/act/cancer2gemini.html";
					}else if(day >= 20 && day <= 22){
						window.location.href="http://www.imzhitu.com/act/cancer.html";
					}else if(day >= 23 && day <= 31){
						window.location.href="http://www.imzhitu.com/act/lion2cancer.html";
					}
					break;
				case 8:
					if(day > 0 && day <= 10){
						window.location.href="http://www.imzhitu.com/act/lion2cancer.html";
					}else if(day >= 11 && day <= 22){
						window.location.href="http://www.imzhitu.com/act/lion.html";
					}else if(day >= 23 && day <= 31){
						window.location.href="http://www.imzhitu.com/act/virgo2lion.html";
					}
					break;
				case 9:
					if(day > 0 && day <= 15){
						window.location.href="http://www.imzhitu.com/act/virgo2lion.html";
					}else if(day >= 16 && day <= 22){
						window.location.href="http://www.imzhitu.com/act/virgo.html";
					}else if(day >= 23 && day <= 30){
						window.location.href="http://www.imzhitu.com/act/libra2virgo.html";
					} 
					break;
				case 10:
					if(day > 0 && day <= 23){
						window.location.href="http://www.imzhitu.com/act/libra2virgo.html";
					}else if(day >= 24 && day <= 29){
						window.location.href="http://www.imzhitu.com/act/scorpio2virgo.html";
					}else if(day >= 30 && day <= 31){
						window.location.href="http://www.imzhitu.com/act/scorpio2libra.html";
					}
					break;
				case 11:
					if(day > 0 && day <= 22){
						window.location.href="http://www.imzhitu.com/act/scorpio2libra.html";
					}else if(day >= 23 && day <= 29){
						window.location.href="http://www.imzhitu.com/act/sagittarius2scorpio.html";
					}else if(day == 30){
						window.location.href="http://www.imzhitu.com/act/sagittarius2ophiuchus.html";
					}
					break;
				case 12:
					if(day > 0 && day <= 17){
						window.location.href="http://www.imzhitu.com/act/sagittarius2ophiuchus.html";
					}else if(day >= 18 && day <= 22){
						window.location.href="http://www.imzhitu.com/act/sagittarius.html";
					}else if(day >= 23 && day <= 31){
						window.location.href="http://www.imzhitu.com/act/capricorn2sagittarius.html";
					}
					break;
					
				}
				
			}
		}
		document.getElementById("birthday_input").value="";
	}