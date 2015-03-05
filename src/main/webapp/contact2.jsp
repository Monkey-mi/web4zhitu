<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>加入我们</title>
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
	       	<li><a href="zhitu_about" title="关于织图">关于织图</a></li>
	       	<li><a href="zhitu_joinus" title="加入我们">加入我们</a></li>
	      	</ul>
	   	</div>
   	</div>
   	<div id="main">
   		<div id="main-left">
   			<ul>
				<li><a href="zhitu_about">关于织图</a></li>
				<li><a href="zhitu_joinus">加入我们</a></li>
				<li><a href="zhitu_contact"  class="highlight">联系我们</a></li>
				<li><a href="zhitu_agreement">用户协议</a></li>
			</ul>
   		</div>
   		<div id="main-right">
   			<div class="p2 highlight">
	   			织图的成长需要你我共同来“编织”，我们会不断改进。你的意见和想法都会是我们成长的原动力，你的每一字、每一句，我们都将虚心接受！可以通过以下途径联系我们：<br />
			</div>
			<div class="p2">
			<span class="list-style"></span>加入QQ群，互动交流<br />
			群1：243724531
			</div>
			<div class="p2">
			<span class="list-style"></span>加入微信，与我们交流<br />
			微信公众号：织图
			</div>
			<div class="p2">
			<span class="list-style"></span>加入微博，与我们交流<br />
			新浪微博：@织图
			</div>
			<div class="p2">
			<span class="list-style"></span>发邮件，留下你的意见和想法<br />
			邮箱：hi@imzhitu.com
			</div>
			<div class="p2">
			<span class="list-style"></span>如有合作意向请联系<br />
			QQ：2435072761<br />
			</div>
			<div class="p2">
			如果涉及到版权相关合作及问题，请用上方的电话或者QQ联系我们。<br />
			为了能更及时的回复大家的问题，避免QQ因好友过多
			而无法正常运行，我们不接受加QQ好友的请求，请大家谅解。
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
       <span class="copyright">|&nbsp;粤ICP备12087844号-1</span> <span class="copyright">©2013-2014 imzhitu.com.All Rights Reserved</span>
   </div>	
</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>