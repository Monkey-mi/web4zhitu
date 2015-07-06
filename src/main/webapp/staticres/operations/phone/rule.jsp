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
<meta name="viewport" content="target-densitydpi=device-dpi, user-scalable=yes"/>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.8.2.min.js"></script>
<title>织图</title>
<style type="text/css">
	body {
		margin: 0 auto;
		color: #757574;
		font-size: 30px;
	}
	#bg {
		width: 100%;
		height: 100%;
		position: absolute;
		z-index: -1;
	}
	#main_box {
		position:absolute;
		margin: 0 auto;
		height: 100%;
		overflow:scroll;
		text-align: left;
		padding:0 30px 0 30px;
	}
	.title {
		color: #fc6565;
		font-size: 38px;
		margin: 30px 0 30px 0;
		font-weight: bold;
	}
	ul {
		display:block;
		list-style-type:decimal;
		line-height:40px;
		/*margin: 0 20px 0 30px;*/
	}	
	.condition {
		line-height:40px;
		margin: 0 0 20px 0;
	}
	ul li {
		margin-bottom: 20px;
	}
	#world_title {
		margin-top: 50px;
	}
	
	.state_wrap {
		width: 100%;
		text-align: center;
		display: none;
	}
	#state_btn {
		display:inline-block;
		width: 300px;
		height: 50px;
		line-height:50px;
		left: 50%;
		font-size:30px;
		color: #ffffff;
		text-decoration:none;
		-moz-box-shadow:1px 1px 2px rgba(0,0,0,0.7);
		-webkit-box-shadow:1px 1px 2px rgba(0,0,0,0.7);
		box-shadow:1px 1px 2px rgba(0,0,0,0.7);
		border-radius:4px;
		-webkit-border-radius: 4px;
		-moz-border-radius: 4px;
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
	var sending = false,
		hideSquareRule = <%=hideSquareRule%>,
		showApply = <%=showApply%>,
		userId = <%=userId%>,
		state = <%=state%>;
	$(document).ready(function(){
		if(hideSquareRule) {
			$("#square_rule_wrap").hide();
		} else {
			$("#start_rule_wrap").hide();
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
					.text('您已经是织图明星');
				break;
			case -1:
				$stateBtn.text('登录后可以成为明星');
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
	<img id="bg" alt="" src="${webRootPath }/operations/phone/images/bg.jpg">
	<div id="main_box">
		<div id="start_rule_wrap">
		<p class="title">#明星用户#规则</p>
		<ul>
			<li>织图明星会享受上万粉丝的拥戴，引领和带动气氛；</li>
			<li>有10个以上织图相册（不要卖广告哦），并且可以持续分享高质量的相册；</li>
			<li>织图相册图片精致格调高。逻辑清楚，能表达出生活故事，一段旅行，一次美食体验或自己的心情；</li>
			<li>至少邀请5个好友加入织图；</li>
			<li>旅行达人/美食达人/搭配达人优先；</li>
			<li>Instagram重度爱好者或者豆瓣资深用户优先，新浪微博认证用户或者摄影爱好者优先；</li>
			<li>20天之内没有发织图将会被取消明星资格。</li>
		</ul>
		<p class="condition" style="font-size: 26px; margin-top: 30px;">
			（小窍门，可以在个人描述里面添加自己的社交账号，让小编更快了解你）
		</p>	
		<p class="condition">
			小编审核后，即可成为织图明星用户获得认证标签，马上享受上万粉丝的拥戴。
			也可直接跟小编自我推荐：微信号imzhitu或邮箱hello@imzhitu.com。
		</p>		
		<p class="state_wrap">
			<a id="state_btn" class="apply" href="javascript:void(0);">我要做明星</a>
		</p>
		</div>
		<div id="square_rule_wrap">
			<p id="world_title" class="title">#织图广场#规则</p>
			<ul>
				<li>广场的织图是由小编精选而来，代表着织图的气质，也代表发图者的气质；</li>
				<li>每个用户都有均等机会上广场，多次上广场，可以被推举为织图明星；</li>
				<li>广场的织图相册需由多张精美图片组成，可以体现织图的特色和格调；</li>
				<li>旅行类作品，光影层次丰富、色调优美、构图新颖，将被优先推荐；</li>
				<li>美食类作品，画面清晰、内容故事突出、场景丰富，将被优先推荐；</li>
				<li>穿搭类作品，主题明确、画面精美、搭配实用有参考学习价值，将被优先推荐；</li>
				<li>故事类作品，逻辑清晰、情感丰富、有描述辅助故事画面，将被优先推荐；</li>
				<li>心情类作品，情感真挚、画面清晰，有良好情绪带入感，将被优先推荐；</li>
				<li>原创作品优先。</li>
			</ul>
			<p class="condition">
				有如下情况的作品，将不会被推荐：
			</p>
			<ul>
				<li>作品中有过多的商业广告；</li>
				<li>含有影响作品美观的网站LOGO水印等信息。</li>
			</ul>
			<p class="condition">
				我们并不能保证所有的优秀作品都会被推荐，没被推荐的作品也不代表不被认可。
				为此，社区的热门织图是基于用户的喜好偏爱，另外，小编会全力帮助各作者的
				优秀织图脱颖而出，被更多人浏览喜欢。
			</p>
		</div>
	</div>
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>