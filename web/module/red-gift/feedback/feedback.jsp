<%@ page import="com.szqd.framework.config.SystemConfigParameter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
	<title>反馈</title>
    <link type="text/css" rel="stylesheet" href="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/module/red-gift/feedback/feedback/feedback.css"/>
    <script src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/resource/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/module/red-gift/feedback/feedback/feedback.js"></script>

    <script>

        var paramMap = {};
        paramMap["appName"] = "${feedback.appName}";

        $.ajax
        ({
            type: "POST",
            url: "${pageContext.request.contextPath}/feedback/add-uninstall-count.do",
            data: paramMap,
            dataType: "json",
            success: function(data){
            }
        });

        var isSubmit = false;
        function submitFeedback()
        {
            if(isSubmit)
            {
                alert("请勿重复提交");
                return;
            }
            else
            {

                isSubmit = true;
                var paramsString = $("#feedbackFormID").serialize();
                $.ajax
                ({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/feedback/add-feedback.do",
                    data: paramsString,
                    dataType: "json",
                    success: function(data)
                    {
                        window.location.href = "${pageContext.request.contextPath}/project/flashlight/feedback_download.jsp";
                    }
                });
            }

        }

        function reInstall()
        {
            window.location.href = "http://openbox.mobilem.360.cn/index/d/sid/3077312";
        }



    </script>

</head>
<body>
	<div class="container-fluid">

        <div class="feedback-top">
            <div class="feedback-top-img">
                <img src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/module/red-gift/feedback/feedback/images/feedback_top_pic.png"/>
                <a href="http://openbox.mobilem.360.cn/index/d/sid/3077312">
                    <span>手滑误删</span>
                </a>
            </div>
        </div>

		<div class="form-horizontal">
			<div class="form-control">
                <form id="feedbackFormID">
                    <div class="form-group clearfix">
                        <label class="form-label">抢不到红包</label>
                        <input class="form-input" type="checkbox" id="checkbox-1" name="message" value="抢不到红包"/>
                    </div>
                    <div class="form-group clearfix">
                        <label class="form-label">抢红包速度太慢了</label>
                        <input class="form-input" type="checkbox" id="checkbox-2" name="message" value="抢红包速度太慢了"/>
                    </div>
                    <div class="form-group clearfix">
                        <label class="form-label">不会用</label>
                        <input class="form-input" type="checkbox" id="checkbox-3" name="message" value="不会用"/>
                    </div>
                    <div class="form-group clearfix">
                        <label class="form-label">会自动停止运行</label>
                        <input class="form-input" type="checkbox" id="checkbox-4" name="message" value="会自动停止运行"/>
                    </div>
                    <div class="form-group clearfix">
                        <label class="form-label">微信/QQ帐号被查</label>
                        <input class="form-input" type="checkbox" id="checkbox-5" name="message" value="微信/QQ帐号被查" />
                    </div>
                    <div class="form-group clearfix">
                        <label class="form-label">爱上别人了</label>
                        <input class="form-input" type="checkbox" id="checkbox-6" name="message" value="爱上别人了"/>
                    </div>
            
                    <div class="form-group-tt">
                        <span>其他问题:</span>
                        <textarea id="fg-tt" name="message" rows="5"></textarea>
                    </div>
                    <div class="form-group-gender clearfix">
                        <span>性别：</span>
                        <div id="radio-gender-2" class="form-radio form-radio-gender">
                            <input class="form-input" id="gender-radio-1" type="radio" name="userSex" value="0"/>
                            <i></i>
                            <span>美女</span>
                        </div>
                        <div id="radio-gender-1" class="form-radio form-radio-gender">
                            <input class="form-input" id="gender-radio-2" type="radio" name="userSex" value="1"/>
                            <i></i>
                            <span>帅哥</span>
                        </div>
                    </div>
                    <div class="form-group-age clearfix">
                        <span>年龄：</span>
                        <div id="radio-age-2" class="form-radio form-radio-age">
                            <input class="form-input" id="age-radio-1" type="radio" name="userAge" value="0"/>
                            <i></i>
                            <span>18-25</span>
                        </div>
                        <div id="radio-age-1" class="form-radio form-radio-age">
                            <input class="form-input" id="age-radio-2" type="radio" name="userAge" value="1"/>
                            <i></i>
                            <span>0-17</span>
                        </div>
                        <div id="radio-age-4" class="form-radio form-radio-age">
                            <input class="form-input" id="age-radio-3" type="radio" name="userAge" value="2"/>
                            <i></i>
                            <span>36-45</span>
                        </div>
                        <div id="radio-age-3" class="form-radio form-radio-age">
                            <input class="form-input" id="age-radio-4" type="radio" name="userAge" value="3"/>
                            <i></i>
                            <span>26-35</span>
                        </div>
                        <div id="radio-age-6" class="form-radio form-radio-age age-radio-bottom">
                            <input class="form-input" id="age-radio-5" type="radio" name="userAge" value="4"/>
                            <i></i>
                            <span>56以上</span>
                        </div>
                        <div id="radio-age-5" class="form-radio form-radio-age age-radio-bottom">
                            <input class="form-input" id="age-radio-6" type="radio" name="userAge" value="5"/>
                            <i></i>
                            <span>46-55</span>
                        </div>
                    </div>
                    <div class="form-group-qq">
                        <span>您的邮箱或QQ:</span>
                        <input type="text" name="contactInformation"/>
                    </div>

                    <input type="hidden" name="appName" value="${feedback.appName}" />
                    <input type="hidden" name="platform" value="${feedback.platform}" />
                    <input type="hidden" name="appVersion" value="${feedback.appVersion}" />
                    <input type="hidden" name="osVerion" value="${feedback.osVerion}" />
                    <input type="hidden" name="phoneModel" value="${feedback.phoneModel}" />

                    <div class="form-btn clearfix">
                        <button class="fl" type="button" name="submit" onclick="submitFeedback()">提交</button>
                        <button class="fr" type="button" name="submit" onclick="reInstall()">再给一次机会</button>
                    </div>
                </form>
            </div>
		</div>
	</div>
</body>
</html>

























