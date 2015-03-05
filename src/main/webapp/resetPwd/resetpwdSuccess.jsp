<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.6.2.min.js"></script>
<title>修改密码成功</title>
<style type="text/css">
	body{margin:0;display:block;background-color:#eaedec;}
	#head{ width:100%;height:90px;background-color:#69c0de;margin:0 0 20px 0;}
	#head-main{width:980px;height:100%;margin:auto;}
	#logo-pic{float:left;vertical-align:middle;}
	#head-menu{width:363px;height:100%;float:right;display:inline-block;}
	#head-menu ul{width:100%;height:100%;margin:0;padding:0;}
	#head-menu li{width:120px;height:100%;float:left;vertical-align:middle;text-align:center;
	    *display: inline;
	    display: inline-block;
	}
	#head-menu li p{line-height:58px;}
	#head-menu li:hover{background-color:#7dd0ed;}
	#head-menu a{height:100%;width:100%;font-size:20pt;color:#ffffff;font-weight:500;text-decoration:none}
	#head-menu a:hover{background-color:#7dd0ed;}
	
	#resetpwdSuccessContent{width:980px;height:484px;margin:auto;background-color:#f3f3f3;}
	#contentMain{width:940px;margin:auto;}
	#contentTitle{font-size:18pt;color:#757574;font-weight:bold; border-bottom:solid 2px #e6e6e6;width:100%;height: 60px;line-height:60px;vertical-align:middle;}
	#content{font-size:16pt;color:#757574;font-weight:500;width:100%;}
	
	#copyright-box{width:980px;margin:auto;}
	.copyright-info{width:100%;margin-top:50px;text-align:center;color:#acb0b2;}
	.copyright-info a{text-decoration: none;color:#acb0b2;}
	.copyright-info a:hover{color:#69c0de;}
	.copyright-div{width:100%;text-align:center;color:#acb0b2;}
</style>

</head>
<body>
	<div id="main">
		<div id="head">
			<div id="head-main">
				<div id="logo-pic"><p><img alt="" src="${webRootPath }/resetPwd/images/logo_s.png"/></p></div>
				<div id="head-menu">
					<ul class="head-menu-ul">
						<li><p><a href="${webRootPath }/" title="织图首页" class="highlight">首页</a></p></li>
						<li><p><a href="${webRootPath }/zhitu_about" title="关于织图">关于织图</a></p></li>
						<li><p><a href="${webRootPath }/zhitu_joinus" title="加入我们">加入织图</a></p></li>
					</ul>
				</div>
			</div>
		</div>
		<div id="resetpwdSuccessContent">
			<div id="contentMain">
				<div id="contentTitle"><p>重置密码</p></div>
				<div id="content"><p>新密码设置成功，快去登录您的app吧！</p></div>
				<div id="Illustration"><p><img src="${webRootPath }/resetPwd/images/resetpwdsuccess.png"/></p></div>
			</div>
		</div>
		<div id="copyright-box">
			<div class="copyright-info">
				<a href="${webRootPath }/zhitu_about">关于织图</a>&nbsp;|&nbsp;<a href="${webRootPath }/zhitu_agreement">用户协议</a>&nbsp;|&nbsp;<a href="${webRootPath }/zhitu_joinus">招聘信息</a>&nbsp;|&nbsp;<a href="${webRootPath }/zhitu_contact">联系我们</a>
			</div>
		   	<div class="copyright-div">
		       <span class="copyright">服务条款</span>
		       <span class="copyright">|&nbsp;粤ICP备 1208784</span> <span class="copyright">©2013-2014 imzhitu.com.All Rights Reserved</span>
		   </div>	
		</div>
	</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>