var callSetting = {
	schemeIOS: 'imzhitu://',
	schemeADR: 'imzhitu://open',
	url: 'http://imzhitu.com/index4ph.html',
	
};
var inapp = 0,
	reg = new RegExp("(^|&)inapp=([^&]*)(&|$)", "i"),
	r = window.location.search.substr(1).match(reg),
	ua = navigator.userAgent.toLowerCase(),
	t,
	config = {timeout: 600};

function callAppInit() {
	if(r == null || r == undefined) inapp = 0;
	else inapp=unescape(r[2]);
	
	
	if(!callSetting) {
	   	config.schemeIOS = 'imzhitu://';
	   	config.schemeADR = 'imzhitu://open';
	   	config.url = 'http://imzhitu.com/index4ph.html';
	} else {
		if(callSetting.sechemeIOS) config.schemeIOS = callSetting.sechemeIOS;
		if(callSetting.schemeADR) config.schemeADR = callSetting.schemeADR;
		if(callSetting.url) config.url = callSetting.url;
	}
	
	return inapp;
}

function openclient(checkConf) {
	if(inapp == 1)
		return;

	this.removeAttribute('href');
	var ifr = document.createElement('iframe');
    ifr.style.display = 'none';
	var uatype = getUAType(ua);
	switch(uatype) {
	case 1:
		ifr.src = config.schemeIOS;
		break;
	case 2:
		ifr.src = config.schemeADR;
		break;
	default:
	}
	if(uatype > 0) {
		var startTime = Date.now();
        document.body.appendChild(ifr);
        var t = setTimeout(function() {
            var endTime = Date.now();
            if (!startTime || endTime - startTime < config.timeout + 200) { 
                window.location = config.url;
            } else {
            }
        }, config.timeout);
        window.onblur = function() {
            clearTimeout(t);
        }
    } else {
    	window.location = config.url;
    }
}

function getUAType(ua) {
	if(ua.indexOf('iphone') > 0 || ua.indexOf('ipad') > 0) return 1;
	else if(ua.indexOf('android') > 0) return 2;
	else return 0;
}
