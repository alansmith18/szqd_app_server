<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>添加或编辑推送信息</title>


    <link rel="stylesheet" type="text/css" charset="utf-8" href="${pageContext.request.contextPath}/resource/css/styles.css">
    <link href="${pageContext.request.contextPath}/resource/css/reset.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resource/css/layout.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resource/css/main.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resource/css/calendar.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resource/css/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" charset="utf-8">
    <script src="${pageContext.request.contextPath}/resource/js/jquery-1.8.2.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui-1.8.18.custom.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.ztree.all-3.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/back_to_top.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.validate.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/uploadify/jquery.uploadify.js" ></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/uploadifyUtil/uploadifyUtil.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/My97DatePicker/WdatePicker.js"></script>

    <link href="${pageContext.request.contextPath}/resource/js/uploadify/uploadify.css" rel="stylesheet" type="text/css" charset="utf-8" />




    <!--操作提示 JS-->
    <script>
        $(document).ready(function() {

            var validator = $("#formSaveUserID").validate
            (
                {

                    submitHandler: function(form)
                    {

                        // do other things for a valid form
                        $(".operation_alert_box").animate({
                            height: 'show',
                            opacity: 'show'
                        }, 'slow');
                        $(".operation_alert_box").delay(1500).animate({
                            height: 'hide',
                            opacity: 'hide'
                        }, 'slow');

                        form.submit();
                    }
//                    ,
//                    ignore: []
                }

            );


            $(".handin_bt").click(function() {

                $("#formSaveUserID").submit();
            });


            var paramMap = {};
            paramMap["subpath"] = "/push-message";

            var callbackParam = {};
            callbackParam["resultID"] = "#iconID";
            callbackParam["divID"] = "#iconDIVID";
            callbackParam["width"] = 72;
            callbackParam["height"] = 72;
            uploadFileWithCustomParam("/common/uploadFile.do","#iconFileID","${pageContext.request.contextPath}",paramMap,callback,callbackParam);

            callbackParam = {};
            callbackParam["resultID"] = "#picID";
            callbackParam["divID"] = "#picDIVID";
            callbackParam["width"] = 480;
            callbackParam["height"] = 250;
            uploadFileWithCustomParam("/common/uploadFile.do","#picFileID","${pageContext.request.contextPath}",paramMap,callback,callbackParam);
        });


        function callback(callbackParam)
        {
            var jsonData = callbackParam["jsonData"];
            var resultID = callbackParam["resultID"];
            var divID = callbackParam["divID"];
            var width = callbackParam["width"];
            var height = callbackParam["height"];


            $(resultID).val(jsonData.resultData.relativePath);
            $(divID).html("<img src='${pageContext.request.contextPath}"+jsonData.resultData.relativePath+"' width='"+width+"' height='"+height+"' >");
        }


    </script>



    </head>
    <body>


<!--面包屑-->

<div class="right_content_pagePath">您现在所在的位置：添加或编辑推送信息</div>
<!--面包屑  end-->

<table width="100%"  bgcolor="#2b7bc7" height="35px" align="center"  >
   <tr>
     <td  align="center"><span style=" font-size:18px; font-family:微软雅黑; color:#FFF"></span></td>
   </tr>
</table>

<div class="form">
  <div class="table" style="display:">
    <h3 class="title"  id="001" > <b >填写推送信息</b>
   
    
    <img class="more_up form_action" src="${pageContext.request.contextPath}/resource/images/transparent.gif" alt="收起/展开" title="收起/展开" onClick="formAction(event)"> </h3>

      <form id="formSaveUserID" action="${pageContext.request.contextPath}/push-message/add-push-message.do" method="post" >
        <table class="list_table" border="0" cellspacing="0" cellpadding="0" width="100%" >

            <input type="hidden" name="id" value="${edit.id}" />

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>icon:</td>
                <td class=" w245"  colspan="3">

                    <input type="file" id="iconFileID" />
                    <input type="hidden" id="iconID" name="icon" value="${edit.icon}"/>

                    <br/>
                    <div id="iconDIVID" >
                        <c:if test="${!empty edit.icon}">
                            <img src="${pageContext.request.contextPath}${edit.icon}" width="72px" height="72px" />
                        </c:if>
                    </div>

                </td>
            </tr>


            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>图片:</td>
                <td class=" w245"  colspan="3">

                    <input type="file" id="picFileID" />
                    <input type="hidden" id="picID" name="contentPic" value="${edit.contentPic}" />

                    <br/>
                    <div id="picDIVID">
                        <c:if test="${!empty edit.contentPic}">
                            <img src="${pageContext.request.contextPath}${edit.contentPic}" width="480px" height="250px" />
                        </c:if>
                    </div>

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>推送形式</td>
                <td class=" w245"  colspan="3">

                    <s:select path="edit.pushType">
                        <s:options items="${pushTypeEnumList}" itemLabel="name" itemValue="id" ></s:options>
                    </s:select>

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>所属项目</td>
                <td class=" w245"  colspan="3">
                    <s:select path="edit.project">
                        <s:options items="${projectList}" itemLabel="name" itemValue="id" ></s:options>
                    </s:select>
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>app名称</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="appNameID" title="2到20个字符" class="input w200"  name="appName" value="${edit.appName}" required minlength="1" maxlength="8"   />
                </td>
            </tr>


            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>标题</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="titleID" title="2到20个字符" class="input w200"  name="title" value="${edit.title}" required minlength="2" maxlength="20"   />
                </td>
            </tr>


            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>子标题</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="subTitleID"  title="2到20个字符" class="input w200"  name="subTitle" value="${edit.subTitle}" minlength="2" maxlength="20"     >
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>目标地址</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="targetURLID"  title="5到100个字符" class="input w200"  name="targetURL" value="${edit.targetURL}" minlength="5" maxlength="100"  required   >
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>URL类型</td>
                <td class=" w245"  colspan="3">

                    <s:select path="edit.urlType">
                        <s:options items="${targetURLTypeEnumList}" itemLabel="name" itemValue="id" ></s:options>
                    </s:select>

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>程序包名</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="packageNameID"  title="1到100个字符" class="input w200"  name="packageName" value="${edit.packageName}" minlength="1" maxlength="100"     >
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>消息类型</td>
                <td class=" w245"  colspan="3">

                    <s:checkboxes path="edit.messageTypes" items="${messageTypeEnumList}" itemValue="id" itemLabel="name" title="请选择" required="true" />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>开始时间</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="beginTimeID"  title="选择开始时间" class="input w200"  name="beginTimeText" value="${edit.beginTimeText}" readonly  required onfocus="WdatePicker()" >
                </td>
            </tr>


            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>结束时间</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="endTimeID"  title="选择结束时间" class="input w200"  name="endTimeText" value="${edit.endTimeText}" readonly  required onfocus="WdatePicker()" >
                </td>
            </tr>

        </table>

      <table class="list_table" border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>

          <td style="width: 340px; border-left:none; padding-left:25px">
              <a id="submit" class="handin_bt t-center" ><span>确认</span></a>

              <a id="cancel"  class="btn_save t-center " onclick="window.history.back();" ><span>返回</span> </a>
          </td>
        </tr>
      </table>
    </form>

    </div>
  </div>


<!--Content end-->


<!--操作提示-->
<div class="operation_alert_box" style="display:none;">
  <p class="content"> <span class="ok_icon"></span> <span class="text">正在提交,请稍后
    <!--<a class="cancel" href="####">撤销</a>--> 
    </span> </p>
</div>
<!--操作提示 End--> 
<!--对话框 JS--> 
<!--End 对话框 JS--> 

<!--操作提示 JS End-->



</body>
</html>
