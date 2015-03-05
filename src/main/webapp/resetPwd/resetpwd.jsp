<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
        <%String sid = request.getParameter("sid"); %>
        <%String loginCode = request.getParameter("loginCode"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.6.2.min.js"></script>
<title>修改密码</title>
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
	#resetpwdTable{width:980px;margin:auto;background-color:#f3f3f3;}
	#reset-content{width:940px;margin:auto;}
	#content-title{width:100%;height:60px;font-size:18pt;color:#757574;line-height:60px;vertical-align:middle;font-weight:bold;border-bottom:solid 2px #e6e6e6;}
	.none{display:none;}
	.no-none{display:true;}
	.red{color:red;}
	#reset-content td{height:46px;}
	.resetpwd-td-title{font-size:16pt;color:#757574;}
	.resetpwd-td-input{height:50px;width:270px;font-size:20pt;padding-left:15px;padding-right:15px;}
	#pwdTip{color:#acb0b2;}
	#resetpwdSubmitBtn{width:160px;height:50px;color:#ffffff;font-size:14pt;background-color:#69c0de;margin:20px 0 20px 0;border:0;}
	#copyright-box{width:980px;margin:auto;}
	.copyright-info{width:100%;margin-top:50px;text-align:center;color:#acb0b2;}
	.copyright-info a{text-decoration: none;color:#acb0b2;}
	.copyright-info a:hover{color:#69c0de;}
	.copyright-div{width:100%;text-align:center;color:#acb0b2;}
	#pwdTip{width:158px;}
</style>
<script type="text/javascript">	
	function resetPwdSubmit(){
		$("#pwdAgainTip").css("display","none");
		$("#pwdTip").css("color","#acb0b2");
		var pwd = $("#pwd").val();
		var pwdAgain = $("#pwdAgain").val();
		if(pwd.trim()==""){
			$("#pwdAgainTip").css("display","");
			return;
		}
		if(pwd.length<6){
			$("#pwdTip").css("color","red");
		}
		if(pwd==pwdAgain){
			$("#resetpwdForm").submit();
		}else{
			$("#pwdAgainTip").css("display","");
			$("#pwd").val("");
			$("#pwdAgain").val("");
		}
	}
</script>
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
		<div id="resetpwdTable">
			<div id="reset-content">
				<div id="content-title"><p>重置密码</p></div>
				<form id="resetpwdForm" action="./rpwd_resetPWD" method="post">
					<table>
						<tbody>
							<tr><td class="resetpwd-td-title">您的账号</td></tr>
							<tr><td><input name="loginCode" value="${loginCode}" readonly="readonly" class="resetpwd-td-input"/></td></tr>
							<tr><td class="resetpwd-td-title">新密码</td></tr>
							<tr><td><input name="pwd" id="pwd" type="password" maxlength="11" class="resetpwd-td-input"/></td><td id="pwdTip">密码长度至少6位数，字母区分大小写</td></tr>
							<tr><td class="resetpwd-td-title">再输一遍</td></tr>
							<tr><td><input  id="pwdAgain" type="password" maxlength="11" class="resetpwd-td-input"/></td><td><div class="red" style="display: none" id="pwdAgainTip">您输入的密码与确认密码不一致</div></td></tr>
							<tr style="display:none">
								<td><input name="sid" value="${sid}" style="display: none"/></td>
							</tr>
						</tbody>
					</table>
				</form>
				<div>
					<input type="button" id="resetpwdSubmitBtn" onclick="resetPwdSubmit();" value="重置密码"/>
				</div>
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