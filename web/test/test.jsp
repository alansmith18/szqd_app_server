<%@ page import="com.szqd.framework.config.SystemConfigParameter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
	<title>反馈</title>
    <link type="text/css" rel="stylesheet" href="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/project/flashlight/feedback/feedback.css"/>
    <script src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/project/flashlight/feedback/feedback.js"></script>
    <script src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/resource/js/jquery-1.7.2.min.js" type="text/javascript"></script>

    <script>


//window.location.href="/data/alarminfo/0102.html";
        $.ajax
        ({
            type: "GET",
            url: "http://www.weather.com.cn/data/alarminfo/0102.html",
            dataType: "script",
//            crossDomain: true,
            cache:false,
            async:false,
            success: function(data){
                alert(data);

            },
            error: function(jqXHR,textStatus,errorThrown)
            {
                alert(textStatus+errorThrown);
            }
        });


    </script>
</head>
<body>

</body>
</html>

























