<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${authorName }</title>
<link rel="stylesheet" type="text/css" href="${webRootPath}/htworld/web/css/style.css" />
<script type="text/javascript" src="${webRootPath }/base/js/baseTools.js"></script>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.6.2.min.js"></script>
<script src=" http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=3145866427" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="${webRootPath}/htworld/common/js/hts.js"></script>
<script type="text/javascript" src="${webRootPath}/htworld/web/js/gmap3.min.js"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&amp;key=AIzaSyDQmItNYkBmfLyI-JRo-KcpQAtyxrgOI3I&amp;language=zh">

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
<script type="text/javascript">
$(document).ready(function() {
	htworldUI.initLayoutConfig(htworldTools.getConfig());
});
WB2.anyWhere(function(W){W.widget.connectButton({id: "sina_login",type:'5,5',callback : {login:htworldUI.wbLoginCallback,logout:htworldUI.wbLogoutCallback}});});
</script>
</head>
<body>
	<div id="h_wrapper">
		<div id="h_world_main" class="h_width">
			<div id="h_world" class="h_width h_height" wid="${worldId }" cover="${coverPath }" author="${authorName }" stag="${shareTag }" pcode="${platformCode }"
			latitude="${latitude }" longitude="${longitude }" ldesc="${locationDesc }">
				<iframe link="${webRootPath}/ztworld/ztworld_htszoomtour?wid=${worldId }&adminKey=${adminKey}" width="398px" height="593px" frameborder="0" scrolling="no"></iframe>
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
								<td id="map_btn_td" style="visibility: hidden;"><div id="h_map_btn" title="地理位置"></div></td>
								<td id="qr_link_btn_td" style="visibility: hidden;"><div id="h_qr_link_btn" title="扫描二维码"></div></td>
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
		<div id="h_banner">
			<a id="h_logo_big" href="${webRootPath }/" title="返回织图首页"></a>
			<!-- 
			<a href="${webRootPath }/"><div id="h_logo_normal" title="返回织图首页"></div></a>
			 -->
			<!-- 
			<p id="h_desc_p">
				全新的图片浏览方式<br />
				把图片之间的联系诠释得更紧密	
			</p>
			 -->
			 <a id="h_ios_down_btn" class="btn_download" href="https://itunes.apple.com/cn/app/zhi-tu/id576156900?mt=8" title="下载iphone客户端">
				<label class="icon_download" id="icon_iphone_download"></label>
				<span class="text_download">IPhone版下载</span>
			</a>
			<a id="h_android_down_btn" class="btn_download" href="http://apk.91.com/Soft/Android/com.htshuo.htsg-15.html" title="下载android客户端">
				<label class="icon_download" id="icon_android_download"></label>
				<span class="text_download">Android版下载</span>
			</a>
		</div>
		<div id="h_footer" class="h_width">
			<span  class="copyright">织图工作室 版权所有 粤ICP备 1208784</span><br />
			<span class="copyright">Copyright &copy;2013 imzhitu.com All Rights Reserved.</span>
		</div>
	</div>
	<div id="h_world_qrcode_div">
		<div class="h_qr_arrow_left"></div>
		<div class="h_qr_container">
			<div class="h_qr_close_btn_div"><div class="h_qr_close_btn" title="关闭二维码"></div></div>
			<div class="h_qr_main"><img src="" width="200px" height="200px"/></div>
			<div class="h_qr_desc">扫描二维码，用手机浏览织图</div>
		</div>
	</div>
	<div id="map"></div>
</body>
</html>