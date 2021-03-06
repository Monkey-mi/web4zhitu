<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	String hideSquareRule = request.getParameter("hideSquareRule");
	String showApply = request.getParameter("showApply");
	Integer userId = (Integer)request.getAttribute("userId"); 
	Integer state = (Integer)request.getAttribute("state");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"/>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.8.2.min.js"></script>
<title>织图</title>
<style type="text/css">
	body {
		margin: 0 auto;
		color: #757574;
		font-size: 15px;
	}
	#main_box {
		position:absolute;
		margin: 0 auto;
		height: 100%;
		text-align: left;
	}
	
	#start_rule_wrap, #square_rule_wrap {
		display: none;
	}
	#square_rule_wrap .title {
		padding-left: 5px;
	}
	.title {
		color: #fc6565;
		margin: 10px 0 10px 0px;
		font-weight: bold;
	}
	.title span{
		vertical-align: middle;
	}
	.title img {
		width: 20px;
		height: 20px;
		vertical-align: middle;
	}
	ul {
		display:block;
		list-style-type:decimal;
		line-height:20px;
		padding-left: 25px;
	}

	#start_rule_wrap ul {
		line-height:20px;
		font-size: 13px;
	}
	.condition {
		margin: 10px 0 10px 5px;
		color: #414241;
	}
	
	#star_condition_2 {
		margin-left: 70px;
	}
		
	ul li {
		margin-bottom: 5px;
	}
	
	.state_wrap {
		width: 100%;
		text-align: center;
		display: none;
	}
	#state_btn {
		background: #6abfde;
		color: #ffffff;
		display:inline-block;
		float:left;
		width: 125px;
		height: 36px;
		line-height:36px;
		left: 50%;
		font-size:14px;
		
		text-decoration:none;
		border-radius:4px;
		-webkit-border-radius: 4px;
		-moz-border-radius: 4px;
	}
	
	#invite_btn {
		background: #f0f0f0;
		color: #757574;
		margin-left: 10px;
		display:inline-block;
		width: 125px;
		height: 36px;
		line-height:36px;
		left: 50%;
		font-size:14px;
		
		text-decoration:none;
		border-radius:4px;
		-webkit-border-radius: 4px;
		-moz-border-radius: 4px;
	}
	
	#star_rule_title {
		font-size: 16px;
		margin-top: 10px;
		display: inline-block;
		
	}
	
	.apply {
		background: #fc6565;
	}
	
	.pending {
		background: #fc6565;
	}
	
	.pass {
		background: #3f9f00;
	}
</style>
<script type="text/javascript">
	var userAgent = navigator.userAgent.toLowerCase();
	if(userAgent .match(/uc/i) != "uc") {
		var dpr = window.devicePixelRatio,
		dpi = dpr * 320;
		$("meta[name='viewport']").attr('content', 'width=device-width,target-densitydpi='+dpi+',initial-scale=1.0, user-scalable=true');
	} else {
		$("meta[name='viewport']").attr('content', 'width=target-densitydpi=device-dpi,initial-scale=1.0, user-scalable=true');
	}
	var sending = false,
		hideSquareRule = <%=hideSquareRule%>,
		showApply = <%=showApply%>,
		userId = <%=userId%>,
		state = <%=state%>;
	$(document).ready(function(){
		if(hideSquareRule) {
			$("#start_rule_wrap").show();
		} else {
			$("#square_rule_wrap").show();
		}
		if(showApply) {
			$(".state_wrap").show();
			var $stateBtn = $("#state_btn");
			switch(state) {
			case 2:
				$stateBtn.removeClass('apply')
					.addClass('pending')
					.text('审核中');
				break;
			case 3:
				$stateBtn.removeClass('apply')
					.addClass('pass')
					.text('已经是明星');
				break;
			case -1:
				$stateBtn.text('登录成为明星');
				break;
			default:
				$stateBtn.click(function() {
					if(sending){
						return;
					}
					sending = true;
					
					$.post("./operations/user_applyRecommend", {
						'userId' : userId
					}, function(result) {
						sending = false;
						if (result['result'] == 0) {
							$stateBtn.unbind();
							$stateBtn.removeClass('apply')
								.addClass('pending')
								.text('审核中');
						}
					});
				});
				break;
			}
		}
	});
</script>
</head>
<body>
	<div id="main_box">
		<div id="start_rule_wrap">
		<p class="title"><span>申请成为明星</span><img alt="申请成为明星" src="${webRootPath}/operations/phone/images/vip.png"></p>
		<p class="condition">
			<span>织图&gt;10个</span>
			<span id="star_condition_2">邀请好友&gt;3人</span>
		</p>	
		<p class="state_wrap">
			<a id="state_btn" class="apply" href="javascript:void(0);">我要做明星</a>
			<a id="invite_btn" class="apply" href="hts://invite">邀请好友</a>
		</p>
		<div id="star_rule_title">
			详细规则：
		</div>
		<ul>
			<li>尽量不要发广告；</li>
			<li>Instagram等美图软件重度爱好者优先；</li>
			<li>30天之内没有发织图将会被取消明星资格；</li>
			<li>明星有义务引领织图社区氛围；</li>
			<li>发图越多，排名越靠前，越容易获得粉丝；</li>
			<li>申请后，若一周之内通过则申请成功。</li>
		</ul>
			
		</div>
		<div id="square_rule_wrap">
			<p id="world_title" class="title">广场规则</p>
			<ul>
				<li>精选织图由小编挑选，轻松活动更多粉丝与互动；</li>
				<li>原创织图入选几率更大；</li>
				<li>每个用户均有机会，多次入选成为明星机会更大。</li>
			</ul>
		</div>
	</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>