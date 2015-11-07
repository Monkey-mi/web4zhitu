var zBase = {
	config:{
		index:0,
		auto:true,
		direct:'left',
		imgWidth:500,
		FPSms:1000,
		cycleTimems:3000,
		slideDiv:'slide',
		imgDiv:'slide-img-div',
		slideBtn:'slide-btn',
		vDirect: 1
	},
	init:function(imgWidth,FPSms,cycleTimems,slideDiv,imgDiv,slideBtn){
		this.config.imgWidth = imgWidth;
		this.config.FPSms= FPSms;
		this.config.cycleTimems = cycleTimems;
		this.config.slideDiv = slideDiv;
		this.config.imgDiv = imgDiv;
		this.config.slideBtn = slideBtn;
		this.slide = this.$id(this.config.slideDiv);
		this.img_div = this.$c(this.config.imgDiv)[0],
		this.slide_btn = this.$tag('a',this.$c(this.config.slideBtn)[0]);
		this.img_arr = this.$tag('img',this.img_div);
		if(this.config.auto) this.play();
		this.hover();
	},
	$id:function(id){return document.getElementById(id);},
	$tag:function(tagName,obj){return (obj ?obj : document).getElementsByTagName(tagName);	},
	$c: function (claN,obj){
		var tag = this.$tag('*'),reg = new RegExp('(^|\\s)'+claN+'(\\s|$)'),arr=[];
		for(var i=0;i<tag.length;i++){
			if (reg.test(tag[i].className)){
				arr.push(tag[i]);
			}
		}
		return arr;
	},
	$add:function(obj,claN){
		reg = new RegExp('(^|\\s)'+claN+'(\\s|$)');
		if (!reg.test(obj.className)){
			
			obj.className += ' '+claN;
		}
	},
	$remve:function(obj,claN){var cla=obj.className,reg="/\\s*"+claN+"\\b/g";obj.className=cla?cla.replace(eval(reg),''):''},
	css:function(obj,attr,value){
		if(value){
		  obj.style[attr] = value;
		}else{
	   return  typeof window.getComputedStyle != 'undefined' ? window.getComputedStyle(obj,null)[attr] : obj.currentStyle[attr];
	   }
	},
	animate:function(obj,attr,val){
		var d = this.config.FPSms;
		if(obj[attr+'timer']) clearInterval(obj[attr+'timer']);
		var start = parseInt(zBase.css(obj,attr));
		
		var space =  val- start,st=(new Date).getTime();
//		space = val==0 ? zBase.config.imgWidth : space;
		obj[attr+'timer'] = setInterval(function(){
			var t=(new Date).getTime()-st;
			if (t < d){
//				zBase.css(obj,attr,Math[m](zBase.easing['easeOut'](t,start,space,d)) +'px');
				zBase.css(obj,attr,zBase.easing['easeOut'](t,start,space,d) +'px');
			}else{
				clearInterval(obj[attr+'timer']);
				zBase.css(obj,attr,top+space+'px');
			}
		},20);
	},
	play:function(){
		this.slide.timer = setInterval(function(){
			zBase.config.index += zBase.config.vDirect;
			
			if(zBase.config.index>=zBase.img_arr.length) {
				zBase.config.vDirect *= -1; 
				zBase.config.index -=2;
			}
			if(zBase.config.index < 0) {
				zBase.config.vDirect *= -1; 
				zBase.config.index = 1;
			}
			
			
			zBase.animate(zBase.img_div,zBase.config.direct,-zBase.config.index*zBase.config.imgWidth);
			for(var j=0;j<zBase.slide_btn.length;j++){
				zBase.$remve(zBase.slide_btn[j],'hover');
			}
			zBase.$add(zBase.slide_btn[zBase.config.index],'hover');
			
		},this.config.cycleTimems)
			
			
	},
	hover:function(){
		for(var i=0;i<this.slide_btn.length;i++){
			this.slide_btn[i].index = i;
			this.slide_btn[i].onmouseover = function(){
				if(zBase.slide.timer) clearInterval(zBase.slide.timer);
				zBase.config.index =this.index; 
				
				for(var j=0;j<zBase.slide_btn.length;j++){
					zBase.$remve(zBase.slide_btn[j],'hover') ;
				}
				zBase.$add(zBase.slide_btn[zBase.config.index],'hover');
				zBase.animate(zBase.img_div,zBase.config.direct,-zBase.config.index*zBase.config.imgWidth);
			}
			this.slide_btn[i].onmouseout = function(){
				zBase.play();
			}
		}
	},
	 easing :{
		linear:function(t,b,c,d){return c*t/d + b;},
		swing:function(t,b,c,d) {return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b;},
		easeIn:function(t,b,c,d){return c*(t/=d)*t*t*t + b;},
		easeOut:function(t,b,c,d){return -c*((t=t/d-1)*t*t*t - 1) + b;},
		easeInOut:function(t,b,c,d){return ((t/=d/2) < 1)?(c/2*t*t*t*t + b):(-c/2*((t-=2)*t*t*t - 2) + b);}
	}
}