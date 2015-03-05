<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html>
<head>
<title>HTSZoomTour Page</title>
<jsp:include page="../common/htszoomtourHeader.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${webRootPath}/htworld/web/css/htszoomtour2013100501.css" />
<script type="text/javascript" src="${webRootPath}/htworld/common/js/htszoomtour2013100501.js"></script>
</head>
<body>
	<div id="container">
		<div id="zt-desc">
			<div id="zt-desc-header">
				<div id="zt-header-arrow" title="展开"></div>
			</div>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td rowspan="2" id="zt-author-img-td"><img src="../base/images/no_avatar_ssmall.jpg" alt="" title="" onerror="baseTools.imgErrorCallback(this,'../base/images/no_avatar_ssmall.jpg')"/></td>
					<td id="zt-author-td"><p><span id="zt-author-name">织图用户</span><span id="zt-click-time-label">播放次数 :</span></p></td>
					<td id="zt-click-time-td"><span id="zt-click-time"></span></td>
				</tr>
				<tr><td colspan="3" id="zt-desc-text-td"><span id="zt-desc-text">暂无描述...</span></td></tr>
				<tr><td colspan="3"  id="zt-desc-footer-td"><span id="zt-share-time-wrapper">图片织于：<span id="zt-share-time-text"></span></span><span id="zt-footer-arrow" title="收起"></span></td></tr>
			</table>
		</div>
		<div id="zt-container" class="zt-container">
			<div class="zt-loading"></div>
		</div>
		<div id="zt-opt-layout">
			<span id="zt-tip">已经在第一层</span>
			<div id="zt-opt-btns-layout">
				<div id="zt-first-btn" class="zt-opt-btn" title="返回第一层"></div>
				<div id="zt-hide-tag-btn" class="zt-opt-btn" title="隐藏小圈圈"></div>
				<div id="zt-show-tag-btn" class="zt-opt-btn" title="显示小圈圈"></div>
				<div id="zt-tag-back" class="zt-opt-btn" title="返回上一层"></div>
			</div>
		</div>
	</div>
</body>
</html>