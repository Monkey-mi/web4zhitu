<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
         <%String sid = request.getParameter("sid"); %>
        <%String loginCode = request.getParameter("loginCode"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="height = device-height,
							   width = device-width,
							   initial-scale = 1.0,
							   minimum-scale = 1.0,
							   maximum-scale = 1.0,
							   user-scalable = yes,
							   target-densitydpi = device-dpi"/>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.6.2.min.js"></script>
<title>修改密码失败</title>
<style type="text/css">
	body{margin:0;display:block;background-color:#eaedec;}
	#head{ width:100%;height:100px;background-color:#69c0de;margin:0 0 20px 0;}
	#head-main{width:96%;height:100%;margin:auto;}
	#logo-pic{float:left;vertical-align:middle;}
	
	#resetpwdFailedContent{width:96%;height:484px;margin:auto;background-color:#f3f3f3;}
	#contentMain{width:92%;margin:auto;}
	#contentTitle{font-size:26pt;color:#757574;font-weight:bold; border-bottom:solid 2px #e6e6e6;width:100%;height: 80px;line-height:80px;vertical-align:middle;}
	#content{font-size:22pt;color:#ee9194;font-weight:500;width:100%;}
	#content a{color:#a0a0a0;}
	
</style>
</head>
<body>
	<div id="main">
		<div id="head">
			<div id="head-main">
				<div id="logo-pic"><p><img alt="" src="${webRootPath }/resetPwd/images/logo_s.png"/></p></div>
			</div>
		</div>
		<div id="resetpwdFailedContent">
			<div id="contentMain">
				<div id="contentTitle"><p>重置密码</p></div>
				<div id="content"><p>Oop ! 重置密码失败，请 <a href="${webRootPath }/user/rpwd_resetPWD?loginCode=${loginCode}&sid=${sid}" ><span>返回</span></a> 上一层重新设置您的密码。</p></div>
				<div id="Illustration"><p><img src="${webRootPath }/resetPwd/images/resetpwdfail.png"/></p></div>
			</div>
		</div>
	</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>