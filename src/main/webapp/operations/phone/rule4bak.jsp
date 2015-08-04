<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	Integer userId = (Integer)request.getAttribute("userId"); 
	Integer state = (Integer)request.getAttribute("state");
	String verifyName = (String)request.getAttribute("verifyName");
	String verifyIcon = (String)request.getAttribute("verifyIcon");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"/>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.8.2.min.js"></script>
<title>申请明星</title>
<style type="text/css">
	body {
		margin: 0 auto;
		color: #757574;
		font-size: 16px;
		padding-left: 3%;
		padding-right: 3%;
	}
	#main-box {
		margin: 0 auto;
		height: 100%;
		text-align: left;
	}
	
	ul {
		margin: 0;
		list-style: decimal;
		padding-left: 20px;
		font-size: 14px;
	}
	
	li {
		margin-bottom: 8px;
	}
	
	.title {
		color: #69b1db;
		margin: 15px 0 10px 0;
	}
	
	#apply-btns-wrap-title {
	}
	
	#rule-wrap-title {
		margin-top: 30px;
	}
	
	#apply-require-wrap {
		height: 50px;
	}
	
	#apply-require-wrap .left{
		display: inline-block;
	}
	
	
	#apply-require-wrap .right {
		display: inline-block;
		margin-right: 7%;
		width: 45%;
		float: right;
		margin-top: 20px;
	}
	
	.color-btn {
		display:inline-block;
		height: 30px;
		line-height:30px;
		font-size:14px;
		text-align:center;
		text-decoration:none;
		border-radius:4px;
		-webkit-border-radius: 4px;
		-moz-border-radius: 4px;
	}
	
	.apply-btn {
		background: #6abfde;
		color: #ffffff;
		margin-bottom:5px;
		width: 45%;
	}
	
	.apply-btn-odd {
		margin-right: 5px;
	}
	
	#state-btn {
		color: #ffffff;
		margin-bottom:5px;
		width: 90%;
	}
	
	.gray-btn {
		background: #f0f0f0;
		color: #757574;
		margin-left: 10px;
		width: 100%;
	}
	
	#invite-icon {
		height: 10px;
		margin-right: 5px;
	}
	
	.btn-icon {
		display:inline-block;
		width:15px;
		margin-right:5px;
		vertical-align: text-top;
	}
	
	#state-icon {
		display: none;
	}
	
	#apply-btns-wrap {
		text-align:center;
		height: auto;
		display: none;
	}
	
	#state-btns-wrap {
		text-align: center;
		height: auto;
	}
	
	.apply {
		background: #6abfde;
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
		userId = <%=userId%>,
		state = <%=state%>,
		verifyName = "<%=verifyName%>",
		verifyIcon = "<%=verifyIcon%>";
	$(document).ready(function(){
		var $stateBtn = $("#state-btn");
		switch(state) {
		case -2: // 审核中
			$stateBtn.removeClass('apply')
				.addClass('pending')
				.text('审核中...');
			break;
		case -1: // 未登录
			$stateBtn.text('请登录后申请达人');
			break;
		case 0: // 未申请
			$.post("./operations/user_queryVerify", {
			}, function(result) {
				if (result['result'] == 0) {
					var $applyBtnsWrap = $("#apply-btns-wrap"),
						verifys = result['verify'];
					for(var i = 0; i < verifys.length; i++) {
						var verify = verifys[i];
						var $btn = 
							$('<a verify-id="'+verify['id']+'" class="color-btn apply-btn" href="javascript:void(0);">'
							+  '<img class="btn-icon" alt="" src="'+verify["verifyIcon"]+'" />'
							+  '<span>申请'+verify["verifyName"]+'</span>'
							+'</a>');
						if(i%2 == 0) {
							$btn.addClass("apply-btn-odd");
						}
						$btn.bind('click', [{'userId':userId,'verifyId':verify['id']}], function(e) {
							var d = e.data[0];
							applyFunc(d.userId, d.verifyId);
						});
						$applyBtnsWrap.append($btn);
					}
					$stateBtn.hide();
					$("#apply-btns-wrap").show();
				}
			});
			break;
		default:
			var text = "您已经是"+verifyName;
			$stateBtn.removeClass('apply')
				.addClass('pass')
				.text(text);
			$("#state-icon").attr('src', verifyIcon).show();
			break;
		}
	});
	
	function applyFunc(userId, verifyId) {
		if(sending){
			return;
		}
		sending = true;
		
		$("#apply-btns-wrap").hide();
		$("#state-btn").show();
		$.post("./operations/user_applyRecommend", {
			'userId' : userId,
			'verifyId' : verifyId
		}, function(result) {
			sending = false;
			if (result['result'] == 0) {
				$("#state-btn").removeClass('apply')
					.addClass('pending')
					.text('审核中...');
			} else {
				$("#apply-btns-wrap").show();
				$("#state-btn").hide();
			}
		});
	};
</script>
</head>
<body>
	<div id="main-box">
		<p id="apply-require-title" class="title">申请要求：</p>
		<div id="apply-require-wrap">
			<div class="left">
				<ul>
					<li>织图至少10个</li>
					<li>邀请好友多于3人</li>
				</ul>
			</div>
			<div class="right">
				<a class="color-btn gray-btn" href="hts://invite"><img id="invite-icon" alt="" 
					src="${webRootPath }/operations/phone/images/invite-icon.png" />去邀请好友</a>
			</div>
		</div>
		<p id="apply-btns-wrap-title" class="title">达人类型：</p>
		<div id="apply-btns-wrap">
		</div>
		<div id="state-btns-wrap">
			<a id="state-btn" class="color-btn apply" href="javascript:void(0);">
				<img id="state-icon" class="btn-icon" alt="" src="http://static.imzhitu.com/user/verify/2014072201.png" />
				<span>加载中...</span>
			</a>
		</div>
		<p id="rule-wrap-title" class="title">详细规则：</p>
		<ul>
			<li>尽量不要发广告；</li>
			<li>Instagram等美图软件重度爱好者优先；</li>
			<li>30天之内没有发织图将会被取消明星资格；</li>
			<li>明星有义务引领织图社区氛围；</li>
			<li>发图越多，排名越靠前，越容易获得粉丝；</li>
			<li>申请后，若一周之内通过则申请成功。</li>
		</ul>
	</div>
	<!-- 
	<jsp:include page="/base/baidustats.jsp"></jsp:include>
 	-->
</body>
</html>