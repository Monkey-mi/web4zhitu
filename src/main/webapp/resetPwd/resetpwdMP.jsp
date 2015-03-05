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
<title>修改密码</title>
<style type="text/css">
	body{margin:0;display:block;background-color:#eaedec;}
	#head{ width:100%;height:88px;background-color:#69c0de;}
	#head-main{width:100%;height:54px;padding:0px 0px 0px 30px;}
	#logo-pic{width:25%;float:left;vertical-align:middle;}
	#resetpwdTable{width:100%;}
	#reset-content{width:94%;padding:0px 0px 0px 20px;margin:auto;background-color:#f3f3f3;}
	#reset-content table{width:100%;}
	#content-title{width:100%;height:74px;font-size:26pt;color:#757574;line-height:74px;vertical-align:middle;font-weight:bold;border-bottom:solid 2px #e6e6e6;}
	.none{display:none;}
	.no-none{display:true;}
	.red{color:red;}
	#reset-content tr{width:98%;height:64px;}
	#reset-content td{width:50%;padding:0;}
	.resetpwd-td-title{font-size:26pt;color:#757574;vertical-align:bottom;}
	.resetpwd-td-input{height:50px;width:92%;font-size:22pt;padding:0;}
	.td-right {padding-left:5%;font-size:22pt;}
	#pwdTip{color:#acb0b2;}
	#submitBtnDiv{width:40%;margin:auto;padding:20px 0px 20px 0px;}
	#resetpwdSubmitBtn{width:100%;height:58px;color:#ffffff;font-size:24pt;background-color:#69c0de;margin:20px 0 20px 0;border:0;}
	
</style>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.6.2.min.js"></script>
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
							<tr><td><input name="pwd" id="pwd" type="password" maxlength="11" class="resetpwd-td-input"/></td><td id="pwdTip" class="td-right"><div>密码长度至少6位数</div></td></tr>
							<tr><td class="resetpwd-td-title">再输一遍</td></tr>
							<tr><td><input  id="pwdAgain" type="password" maxlength="11" class="resetpwd-td-input"/></td><td class="td-right"><div class="red" style="display: none" id="pwdAgainTip">您输入的密码与确认密码不一致</div></td></tr>
							<tr style="display:none">
								<td><input name="sid" value="${sid}" style="display: none"/></td>
							</tr>
						</tbody>
					</table>
				</form>
				<div id="submitBtnDiv">
					<input type="button" id="resetpwdSubmitBtn" onclick="resetPwdSubmit();" value="重置密码" />
				</div>
			</div>
		</div>	
	</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>