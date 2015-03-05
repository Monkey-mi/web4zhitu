<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${authorName }</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport">
<link rel="stylesheet" type="text/css" href="${webRootPath}/htworld/phone/css/style.css" />
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.6.2.min.js"></script>
<script src=" http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=3145866427" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="${webRootPath}/htworld/common/js/hts.js"></script>
<script type="text/javascript">
var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-33767721-1']);
_gaq.push(['_trackPageview']);

(function() {
  var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
  ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
  var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
})();
</script>
<script>
$(document).ready(function() {htworldUI.initPhoneLayoutConfig(htworldTools.getConfigByWidth());});
WB2.anyWhere(function(W){W.widget.connectButton({id: "sina_login",type:'3,5',callback : {login:htworldUI.wbLoginCallback,logout:htworldUI.wbLogoutCallback}});});
</script>
</head>
<body>
	<div id="h_banner" class="h_width">
		<span id="h_zhitu_logo"></span>
		<a href="https://itunes.apple.com/cn/app/zhi-tu/id576156900?mt=8" title="下载iphone客户端"><span id="h_ios_down_btn"></span></a>
		<a href="http://oss.aliyuncs.com/imzhitu/update_pack/android/zhitu_v2.5.1.apk" title="下载android客户端"><span id="h_android_down_btn"></span></a>
	</div>
	<div id="h_wrapper">
		<div id="h_world_main" class="h_width">
			<div id="h_world" class="h_width h_height" wid="${worldId }" cover="${coverPath }" author="${authorName }" stag="${shareTag }" pcode="${platformCode }">
				<iframe link="${webRootPath}/ztworld/ztworld_phonezoomtour?wid=${worldId }&adminKey=${adminKey}" width="398px" height="593px" frameborder="0" scrolling="no"></iframe>
			</div>
			<div id="h_comment_shadow"></div>
			<div id="h_world_comment">
				<div id="h_comment_header">
					<table>
						<tbody>
							<tr>
								<td id="comment_count_icon_td"><div id="h_icon_comment_count"></div></td>
								<td id="comment_count_label_td">评论：</td>
								<td id="zt-comment-count">${commentCount }</td>
								<td id="like_count_icon_td"><div id="h_icon_like_count" class="like_count_enable" title="喜欢这个织图"></div></td>
								<td id="comment_count_label_td">喜欢：</td>
								<td id="zt-like-count" class="zt-like-count">${likeCount}</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="h_comment_main">
					<div id="h_comment_form_div">
						<input id="comment_text" disabled="disabled" autofocus="autofocus" value="评论请先登录新浪微博"/>
						<span id="h_icon_submit"></span> 
					</div>
					<table id="h_comment_table">
						<thead>
							<tr>
								<td colspan="5" id="sina_login_td">
								<span id="sina_login"></span>
								<span id="sina_sync_check_div"><input id="sina_sync" type="checkbox" checked="checked" />评论同步到新浪微博</span>
								</td>
							</tr>
							<tr><td colspan="5" id="h_comment_loading">评论提交中...</td></tr>
						</thead>
						
						<tbody></tbody>
						</table>
					<div id="h_no_comment_tip">暂时还没有评论噢!</div>
					<div id="h_fetch_loading_div">获取评论中...</div>
					<div id="h_fetch_more_div">点击获取更多评论</div>
				</div>
			</div>
		</div>
		<div id="h_footer" class="h_width">
			<span  class="copyright">织图工作室 版权所有 粤ICP备 1208784</span><br />
			<span class="copyright">Copyright &copy;2012 imzhitu.com All Rights Reserved.</span>
		</div>
	</div>
</body>
</html>