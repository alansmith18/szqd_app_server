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

        var paramMap = {};
        paramMap["appName"] = "${param.appname}";

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
                        window.location.href = "../feedback_download.jsp";

                    }
                });
            }

        }

        function close_window(url){
            var newWindow = window.open('', '_self', ''); //open the current window
            window.close(url);
        }

        function reInstall()
        {
            window.location.href = "${pageContext.request.contextPath}/tracking-statistics/tracking-data.do?id=4";
        }

    </script>
</head>


<body>	
	<div class="container-fluid">

		<%--<div class="feedback-top">--%>
			<%--<img src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/project/flashlight/feedback/images/feedback_top_pic.jpg"/>--%>
            <%--<a href="http://openbox.mobilem.360.cn/index/d/sid/246564">误删重装</a>--%>
		<%--</div>--%>

        <div class="feedback-top">
            <div class="feedback-top-img">
                <img src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/project/flashlight/feedback/images/feedback_top_pic_01.jpg"/>
                <a id="shou-hua-wu-shan-id" href="${pageContext.request.contextPath}/tracking-statistics/tracking-data.do?id=1">
                    <span>手滑误删</span>
                    <span>重装安装</span>
                </a>
            </div>
            <div class="feedback-top-btn">
                <a id="wu-suo-ping-ban-id" class="feedback-top-btn-simplify" href="${pageContext.request.contextPath}/tracking-statistics/tracking-data.do?id=2">精简-无锁屏版</a>
                <a id="wan-neng-gong-ju-ban-id" class="feedback-top-btn-almighty" href="${pageContext.request.contextPath}/tracking-statistics/tracking-data.do?id=3">万能-工具版</a>
            </div>
        </div>

		<div class="form-horizontal">
			<div class="form-control">
                <form id="feedbackFormID">
                    <div class="form-group clearfix" id="div1ID">
                        <label class="form-label">后置闪光灯无法照明</label>
                        <input class="form-input" type="radio" id="checkbox-1" name="message" value="1.后置闪光灯无法照明" />
                    </div>
                    <div class="form-group clearfix">
                        <label class="form-label">不喜欢锁屏电筒</label>
                        <input class="form-input" type="radio" id="checkbox-2" name="message" value="2.不喜欢锁屏电筒"/>
                    </div>
                    <div class="form-group clearfix">
                        <label class="form-label">希望锁屏电筒改进</label>
                        <input class="form-input" type="radio" id="checkbox-6" name="message" value="3.希望锁屏电筒改进" />
                    </div>

                    <div class="form-group clearfix">
                        <label class="form-label">闪光灯亮了但是无法关闭</label>
                        <input class="form-input" type="radio" id="checkbox-4" name="message" value="4.闪光灯亮了但是无法关闭" />
                    </div>
                    <div class="form-group clearfix">
                        <label class="form-label">屏幕一黑，闪光灯也熄灭了</label>
                        <input class="form-input" type="radio" id="checkbox-5" name="message" value="5.屏幕一黑，闪光灯也熄灭了" />
                    </div>

                    <div class="form-group clearfix">
                        <label class="form-label">开灯有点慢</label>
                        <input class="form-input" type="radio" id="checkbox-7" name="message" value="6.开灯有点慢" />
                    </div>
                    <div class="form-group clearfix">
                        <label class="form-label">爱上别人了</label>
                        <input class="form-input" type="radio" id="checkbox-8" name="message" value="7.爱上别人了" />
                    </div>
                    <%--<div class="form-group clearfix">--%>
                        <%--<label class="form-label">其他</label>--%>
                        <%--<input class="form-input" type="checkbox" id="checkbox-9" name="message"  />--%>
                    <%--</div>--%>
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

                    <input type="hidden" name="appName" value="${param.appname}" />
                    <input type="hidden" name="platform" value="${param.platform}" />
                    <input type="hidden" name="appVersion" value="${param.appversion}" />
                    <input type="hidden" name="osVerion" value="${param.osversion}" />
                    <input type="hidden" name="phoneModel" value="${param.model}" />

                    <div class="form-btn">
                        <button class="fl" type="button" onclick="submitFeedback()" >提交</button>
                        <button class="fr" type="button" onclick="reInstall()" >再给一次机会</button>
                    </div>
                </form>
            </div>
		</div>

        <%--<div class="feedback-bottom"  >--%>
            <%--<a href="http://apk.hiapk.com/appdown/com.szqd.mini/6">--%>
                <%--<img src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/project/flashlight/feedback/images/feedback_bottom_pic_01.jpg"/>--%>
            <%--</a>--%>
            <%--<a href="http://apk.hiapk.com/appdown/com.tqkj.shenzhi/24">--%>
                <%--<img src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/project/flashlight/feedback/images/feedback_bottom_pic_02.jpg"/>--%>
            <%--</a>--%>
        <%--</div>--%>

        <%--<div class="fankuiimg" style="float: left;">--%>
            <%--<img style="cursor: hand" src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}/project/flashlight/feedback/images/feedback_bottom_pic.jpg" onclick="window.open('http://openbox.mobilem.360.cn/index/d/sid/2952254')">--%>
        <%--</div>--%>
	</div>

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

























