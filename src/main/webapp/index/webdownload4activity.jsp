<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="相册,美图,社交,旅行,故事,时尚,织图,下载,织图下载">
<meta name="keywords" content="相册,美图,社交,旅行,故事,时尚,织图,下载,织图下载,多图">
<meta name="viewport" />
<jsp:include page="../base/downhead.jsp?var=${webVar}"></jsp:include>
<title>织图下载</title>
<style type="text/css">
	#main_box{width:100%;}
	#head_box{
		width:100%;
		height:792px;
	/*	background-image: url(${webRootPath }/index/images/web_dl_bg.png);*/
	/*	background-color: RGB(64,32,29);*/
	}
	#head{
		width:100%;
		height:100%;
		margin:auto;
		
	}
	#img_bg {
		width:100%;
		height:auto;
		position:absolute;
		z-index:-99;
		margin:auto;
		overflow:hidden;
	}
	#head_body{
		width:380px;
		height:100%;
		padding-top:109px;
		margin:auto;
	}
	#img_logo{
		padding-left:8px;
	}
	#download_erweima{
		width:180px;
		height:220px;
		margin: 258px 0px 0 90px;
	}
	#content{
		width:100%;
		height:963px;
		background-color:#f6f5ea;
	}
	#content_body{
		width:980px;
		height:100%;
		margin:0 auto;
	}
	#desc_1{
		width:868px;
		height:23px;
		padding-top:60px;
		margin:0 auto;
	}
	#img_desc_1{
		float:left;
	}
	#img_desc_2{
		float:right;
	}
	#img_dp{
		height:568px;
		width:100%;
		margin:50px 0px 60px 0px;
	}
	#img_dp_1{
		float:left;
		margin-left:90px;
	}
	#img_dp_2{
		float:right;
		margin-right:90px;
	}
	#desc_3{
		height:36px;
		width:854px;
		margin:0 auto;
	}
	#dl_btn{
		margin:60px auto;
		width:600px;
	}
	#img_ios_down_dl{
		float:left;
	}
	#img_andrio_down_dl{
		float:right;
	}
</style>
<script type="text/javascript" src="${webRootPath }/base/js/jquery/jquery-1.8.2.min.js?ver=${webVer}"></script>
<script type="text/javascript">
function download(){
	window.location.href=downloadUrlAndriod;
}
</script>
</head>
<body style="margin:0;padding:0;">
	<div id="main_box">
		<div id="head_box">
			<div id="head">
			 	<img id="img_bg" src="http://static.imzhitu.com/images/web_dl_bg.jpg"/>
				<div id="head_body">
					<img id="img_logo" src="${webRootPath }/index/images/d4a_logo.png"/>
					<div id="download_erweima">
						<img id="img_erweima" src="${webRootPath }/index/images/erweima.png"/>
					</div>
				</div>
			</div>
		</div>
		<div id="content">
			<div id="content_body">
				<div id="desc_1">
					<img id="img_desc_1" src="${webRootPath }/index/images/web_dl_desc_1.png"/>
					<img id="img_desc_2" alt="" src="${webRootPath }/index/images/web_dl_desc_2.png">
				</div>
				<div id="img_dp">
					<img id="img_dp_1" src="http://static.imzhitu.com/images/d4a_desc_bg_1.jpg?ver=${webVer}"/>
					<img id="img_dp_2" src="http://static.imzhitu.com/images/d4a_desc_bg_2.jpg?ver=${webVer}"/>
				</div>
				<div id="desc_3">
					<img id="img_desc_3" alt="" src="${webRootPath }/index/images/web_dl_desc_3.png"/>
				</div>
				<div id="dl_btn">
					<a href="https://itunes.apple.com/cn/app/zhi-tu/id576156900?mt=8" target="_blank" title="点击下载织图IPhone版">
						<img id="img_ios_down_dl" src="${webRootPath }/index/images/d4a_btn_ios_down.png"/>
					</a>
					<a href="javascript:void(0);">
						<img id="img_andrio_down_dl" src="${webRootPath }/index/images/d4a_btn_andrio_down.png" onclick="download()"/>
					</a>
				</div>
			</div>
		</div>
	</div>
	
<jsp:include page="/base/baidustats.jsp"></jsp:include>
</body>
</html>