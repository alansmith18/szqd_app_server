<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.szqd.framework.config.SystemConfigParameter" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
	<title>反馈下载</title>
    <link href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/resource/js/jquery-1.7.2.min.js" type="text/javascript"></script>
	<style type="text/css">
        html{
            min-width: 320px;
        }
        a:hover,
        a:focus {
            color: transparent;
            text-decoration: none;
        }
        a:focus {
            outline: none;
        }
        #download-pic-01{
            width: 100%;
        }
        .container-fluid{
            position: relative;
            padding: 0;
        }
        .download-pic{
            background-color: #333743;
            width: 100%;
        }
        #download-pic-title{
            position: relative;
            text-align: center;
            padding-top: 3%;
        }
        @media (min-width: 375px) {
            #download-pic-title{
                padding-top: 15%;
            }
        }
        #download-pic-title > .title{
            font-size: 20px;
            color: #FFD452;
        }
        #download-pic-line{
            margin-top: 5px;
        }
        #download-pic-line > .download-pic-line-top{
            position: relative;
            width: 50%;
            height: 15px;
            border-right: 1px solid #FFD452;
        }
        #download-pic-line > .download-pic-line-inner{
            min-height: 15px;
        }
        #download-pic-line > .download-pic-line-inner > .download-pic-line-left,
        #download-pic-line > .download-pic-line-inner > .download-pic-line-right{
            position: relative;
            width: 35%;
            height: 15px;
            border-top: 1px solid #FFD452;
            border-right: 1px solid #FFD452;
            float: left;
        }
        #download-pic-line > .download-pic-line-inner > .download-pic-line-left{
            margin-left: 15%;
            border-left: 1px solid #FFD452;
        }

        #download-pic-line > .download-pic-line-top > i,
        #download-pic-line > .download-pic-line-inner > .download-pic-line-left > i,
        #download-pic-line > .download-pic-line-inner > .download-pic-line-right > i{
            position: absolute;
            display: block;
            width: 5px;
            height: 5px;
            background-color: #FFD452;
            border-radius: 100%;
        }
        #download-pic-line > .download-pic-line-top > i{
            right: -2.5px;
            bottom: -2.5px;
        }
        #download-pic-line > .download-pic-line-inner > .download-pic-line-left > i{
            top: -2.5px;
            left: -2.5px;
        }
        #download-pic-line > .download-pic-line-inner > .download-pic-line-right > i{
            top: -2.5px;
            right: -2.5px;
        }
        .download-btn-wrap{
            position: absolute;
            width: 100%;
            padding-left: 2%;
        }
        .download-btn-wrap > a{
            position: relative;
            display: block;
            margin-top: 10px;
            text-align: center  ;
            float: left;
            width: 30%;
        }
        .download-btn-wrap > #download-btn-02{
            margin-left: 4%;
        }
        .download-btn-wrap > #download-btn-03{
            margin-left: 5%;
        }
        .download-btn-wrap > a > span{
            display: block;
            margin: 0 auto;
            color: #FFD452;
        }
        .download-btn-wrap > a > img{
            display: block;
            width: 50%;
            margin: 5px auto 0;
        }
        .download-btn-wrap > a > img:hover{
            border: 1px solid transparent;
            border-radius: 8px;
        }
	</style>
</head>
<body>
    <div id="download-pic-wrap" class="container-fluid">
        <img id="download-pic-01" src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/project/flashlight/feedback/images/feedback_download_pic_01.jpg"/>
        <div class="download-pic">
            <div id="download-pic-title">
                <b class="title">看看其他产品</b>
            </div>
            <div id="download-pic-line">
                <div class="download-pic-line-top"></div>
                <div class="download-pic-line-inner">
                    <div class="download-pic-line-left"></div>
                    <div class="download-pic-line-right"></div>
                </div>
            </div>
            <div class="download-btn-wrap">
                <a id="download-btn-01" href="${pageContext.request.contextPath}/tracking-statistics/tracking-data.do?id=5">
                    <span>闪记</span>
                    <img src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/project/flashlight/feedback/images/feedback_download_btn_01.png"/>
                </a>
                <a id="download-btn-02" href="${pageContext.request.contextPath}/tracking-statistics/tracking-data.do?id=6">
                    <span>神指记账</span>
                    <img src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/project/flashlight/feedback/images/feedback_download_btn_02.png"/>
                </a>
                <a id="download-btn-03" href="${pageContext.request.contextPath}/tracking-statistics/tracking-data.do?id=7">
                    <span>应用锁</span>
                    <img src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/project/flashlight/feedback/images/feedback_download_btn_03.png"/>
                </a>
            </div>
        </div>

    </div>

    <script>
        $(function(){
            var screenHeight = $(window).outerHeight();
            var screenWidth = $(window).outerWidth();
            var currentHeight = screenHeight - $("#download-pic-01").outerHeight();
            if(screenHeight > screenWidth){
               $(".download-pic").height(currentHeight);
            } else {
                $(".download-pic").height("300px");
            }
        })
    </script>


    <script>
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "//hm.baidu.com/hm.js?c65e0a14cb62670ff78c7725495cabf1";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
</body>
</html>