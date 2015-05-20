<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关于织图</title>
<link rel="stylesheet" type="text/css" href="./index/css/index2.css?ver=${webVer}"/>
<link rel="stylesheet" type="text/css" href="./index/css/intro2.css?ver=${webVer}"/>
</head>
<body>
<div id="main-box">
  	<div id="title">
	  	<div id="title-content">
	  		<a href="${webRootPath }/" title="织图首页" id="icon-logo">
	  			<img src="./index/images/icon-index.png" height=41 />
	  		</a>
	     	<ul class="title-text">
	      	<li><a href="${webRootPath }/" title="织图首页">首页</a></li>
	       	<li><a href="zhitu_about" title="关于织图" class="highlight">关于织图</a></li>
	       	<li><a href="zhitu_joinus" title="加入我们">加入我们</a></li>
	      	</ul>
	   	</div>
   	</div>
   	<div id="main">
   		<div id="main-left">
   			<ul>
				<li><a href="zhitu_about" class="highlight">关于织图</a></li>
				<li><a href="zhitu_joinus">加入我们</a></li>
				<li><a href="zhitu_contact">联系我们</a></li>
				<li><a href="zhitu_agreement">用户协议</a></li>
			</ul>
   		</div>
   		<div id="main-right">
   			<div class="p">
	   			<span class="h3 highlight">为什么会有织图</span>
	   			我们曾经藏在城市的角落， 隐秘，徘徊，是许多人眼中的异类。但现在，我们正在尝试改变一种趋势。
	   			 这个时代最宝贵的东西，不是财富，而是人们在观念上的不断革新。从这个意义上来说， 织图，希望影响人们看待世界的方式。 
	   			<br />
				织图是由一个年轻团队开发的产品，织图团队专注于移动互联网图片市场，让用户最好的浏览图片和处理图片是织图未来渴望实现的愿景。
				最后，织图想做的不仅是一款图片软件，而是做一款通过图片，让人与人之间的距离更近一些，更乐于分享，发掘更多美丽的事物。
			</div>
			<hr class="divide-line" />
			<div class="p">
				<span class="h3 highlight">什么是织图</span>
				你是否烦恼过要选哪几张假期旅游时的图片分享到社交平台？
				你是否无奈每次聚会拍了很多照片最后只上传了一张大合照？
				你是否感到过无助当你发现用一张图片或语言无法描述一家别致的餐厅？
				<br />
				织图是全球首个场景式图片社交应用。织图面向的不再是单张图片， 
				而是通过用户轻轻松松的拍摄和编辑多张图片，实现全新的图片浏览方式。
				 使用织图，用全新的方式分享你的心情，分享美的全景，分享你轨迹中的点点滴滴。
			</div>
			<hr class="divide-line" />
			<div class="p">
				<span class="h3 highlight">主要功能</span>
				<ul>
					<li><span class="list-style"></span>多图连接，将照片做成有故事的动态场景相册</li>
					<li><span class="list-style"></span>一键织图，懒人必备，帮你轻松管理杂乱照片</li>
					<li><span class="list-style"></span>图文编辑，可添加描述和表情，个性表达情感</li>
					<li><span class="list-style"></span>顶级滤镜，让照片变美丽，一次批量处理多图</li>
					<li><span class="list-style"></span>设置标签，根据分类，认识兴趣相投的新朋友</li>
					<li><span class="list-style"></span>搜索用户，关注、点赞、评论互动，分享转发</li>
					<li><span class="list-style"></span>精品推荐，随时成为明星达人，参与丰富活动</li>
					<li><span class="list-style"></span>绑定第三方应用如：微博，微信，QQ</li>
				</ul>
			</div>
   		</div>
   	</div>
</div>
<div id="copyright-box">
	<div class="copyright-info">
		<a href="zhitu_about">关于织图</a>&nbsp;|&nbsp;<a href="zhitu_agreement">用户协议</a>&nbsp;|&nbsp;<a href="zhitu_joinus">招聘信息</a>&nbsp;|&nbsp;<a href="zhitu_contact">联系我们</a>
	</div>
   	<div class="copyright-div">
       <span class="copyright">服务条款</span>
       <span class="copyright">|&nbsp;粤ICP备12087844号-1</span> <span class="copyright">©2013-2015 imzhitu.com.All Rights Reserved</span>
   </div>	
</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>