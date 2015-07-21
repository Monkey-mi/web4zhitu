function play68_init() {
	updateShare(0);
}


function play68_submitScore(score) {
	updateShareScore(score);
	Play68.shareFriend();
	 // setTimeout( function() { Play68.shareFriend(); }, 1000 )
}

function updateShare(perfection) {
	var descContent = "画个圆";
    var score = (perfection * 100).toFixed(2);
	if (perfection == 0) {
		shareTitle = '来68微游戏画个圆，画个圆圆满满送给好友吧！';
	}else if (perfection < 0.1) {
        shareTitle = '我画了个'+score+'分的圆，你看得见吗？';
    } else if (perfection < 0.5) {
        shareTitle = '我画了个'+score+'分的圆，不...不.我这次画的不是圆。。';
    } else if (perfection < 0.7) {
        shareTitle = '我画了个'+score+'分的圆，看这个圆有种怪怪的感觉。';
    } else if (perfection < 0.8) {
        shareTitle = '我画了个'+score+'分的圆，我这是苗条圆！';
    } else if (perfection < 0.9) {
        shareTitle = '我画了个'+score+'分的圆，我的圆有种自然美。';
    } else if (perfection < 0.93) {
        shareTitle = '我画了个'+score+'分的圆，比达芬奇的鸡蛋要圆了！';
    } else if (perfection < 0.94) {
        shareTitle = '我画了个'+score+'分的圆，就差一点点了！';
    } else if (perfection < 0.95) {
        shareTitle = '我画了个'+score +'分的圆，以后可以不用圆规了！';
    } else if (perfection < 0.96) {
        shareTitle = '我画了个'+score+'分的圆，美术老师我给您磕个头。';
    } else if (perfection < 0.97) {
        shareTitle = '我画了个'+score+'分的圆，我是圆规投胎来的！';
    } else if (perfection < 0.98) {
        shareTitle = '我画了个'+score+'分的圆，手上无圆规，心中有圆规！';
    } else if (perfection < 0.99) {
        shareTitle = '我画了个'+score+'分的圆，我是神手马良！';
    } else {
        shareTitle = '我画了个'+score+'分的圆，我就是完美,完美就是我！';
    }
	appid = '';
	//Play68.setShareInfo(shareTitle,descContent);
}

function updateShareScore(bestScore) {
  updateShare(bestScore); 
}
