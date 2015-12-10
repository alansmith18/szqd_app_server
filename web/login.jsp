<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 14-7-15
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <%--<script>--%>
        <%--var _hmt = _hmt || [];--%>
        <%--(function() {--%>
            <%--var hm = document.createElement("script");--%>
            <%--hm.src = "//hm.baidu.com/hm.js?c65e0a14cb62670ff78c7725495cabf1";--%>
            <%--var s = document.getElementsByTagName("script")[0];--%>
            <%--s.parentNode.insertBefore(hm, s);--%>
        <%--})();--%>
    <%--</script>--%>

        <script>
            function registerAd()
            {

                window.location.href = "${pageContext.request.contextPath}/ad-alliance/add-user-page.do";

            }
        </script>


    <title></title>

    <style type="text/css">
        .download{margin:20px 33px 10px;*margin-bottom:30px;padding:5px;border-radius:3px;-webkit-border-radius:3px;-moz-border-radius:3px;background:#e6e6e6;border:1px dashed #df0031;font-size:14px;font-family:Comic Sans MS;font-weight:bolder;color:#555}
        .download a{padding-left:5px;font-size:14px;font-weight:normal;color:#555;text-decoration:none;letter-spacing:1px}
        .download a:hover{text-decoration:underline;color:#36F}
        .download span{float:right}
    </style>


    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/login.css" />

</head>
<body>
<div class="main">
    <div class="header hide">平台管理</div>
    <div class="content">
        <div class="title hide">登录</div>

        <form action="${pageContext.request.contextPath}/login.do" method="post">
            <fieldset>
                <div class="input">

                    <c:if test="${param.loginError == '1'}">
                        <span style="color: red">用户名或者密码出错</span>
                    </c:if>
                </div>
                <div class="input">
                    <input class="input_all name" name="user.loginName" id="name" placeholder="用户名" type="text" onFocus="this.className='input_all name_now';" onBlur="this.className='input_all name'" maxLength="24" />
                </div>
                <div class="input">
                    <input class="input_all password" name="user.loginPwd" id="password" type="password" placeholder="密码" onFocus="this.className='input_all password_now';" onBlur="this.className='input_all password'" maxLength="24" />
                </div>
                <%--<div class="checkbox">--%>
                    <%--<input type="checkbox" name="remember" id="remember" /><label for="remember"><span>记住密码</span></label>--%>
                <%--</div>--%>
                <div class="enter">
                    <input class="button hide" name="submit" type="submit" value="登录" />
                    <%--<a href="${pageContext.request.contextPath}/tracking-statistics/tracking-data.do?id=1">测试</a>--%>
                </div>

            </fieldset>
        </form>
    </div>
</div>

</body>
</html>
