<%--
  Created by IntelliJ IDEA.
  User: like
  Date: 12/8/15
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <script src="${pageContext.request.contextPath}/resource/js/jquery-1.8.2.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resource/js/jquery.validate.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/My97DatePicker/WdatePicker.js"></script>
    <title>激活</title>
    <script>

        window.onload = function switchChannel()
        {
//            $("#formID").validate();
            var select = document.getElementById("channelSelectID");
            select.onchange = function(e)
            {
                var channelID = e.target.value;
//                alert(channelID);
                var dateText = document.getElementById("dateTextID").value;
                var param = "channelID="+channelID+"&adID=${condition.adID}&dateText="+dateText;
                window.location.href = "${pageContext.request.contextPath}/ad-alliance/activation-page.do?"+param;
            };

        };

        function queryForm()
        {
            var channelID = document.getElementById("channelSelectID").value;
            var dateText = document.getElementById("dateTextID").value;
            var param = "channelID="+channelID+"&adID=${condition.adID}&dateText="+dateText;
            window.location.href = "${pageContext.request.contextPath}/ad-alliance/activation-page.do?"+param;
        }

        function updateActivation()
        {
            if($("#formID").valid())
            {
                var data = $("#formID").serialize();
                $.ajax({
                    url: "${pageContext.request.contextPath}/ad-alliance/update-activation.do",
                    data: data,
                    dataType: "json",
                    success: function(data,textStatus,jqXHR)
                    {
                        alert("提交成功");
                        window.location.reload();
                    }
                });
            }

        }



    </script>
</head>
<body>
    <form id="formID" action="${pageContext.request.contextPath}/ad-alliance/activation-page.do" >
    <table>

        <tr>
            <td>
                <s:select id="channelSelectID" path="condition.channelID" required="true" title="没有渠道选择该广告" >
                    <s:options items="${channelList}" itemLabel="platformName" itemValue="id"/>
                </s:select>
            </td>

            <td>
                <input id="dateTextID" type="text" readonly="readonly" class="input name  w130 " title="请选择时间" required name="dateText" value="${condition.dateText}" onclick="WdatePicker({maxDate:'%y-%M-%d',onpicked:queryForm})">
            </td>

            <td>
                <input id="activationID" type="text" title="必须数字" name="numberOfActivation" digits="true" required value="${condition.numberOfActivation}" />
                <input type="hidden" name="adID" value="${condition.adID}" readonly />
            </td>
        </tr>
        <tr>
            <td>
                <input type="button"  value="提交" onclick="updateActivation()"/>
            </td>
        </tr>
    </table>
    </form>
</body>
</html>
