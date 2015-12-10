<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>添加或编辑版本信息</title>


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

            var validator = $("#formID").validate
            (
                {

                    submitHandler: function(form)
                    {

                        // do other things for a valid form
//                        $(".operation_alert_box").animate({
//                            height: 'show',
//                            opacity: 'show'
//                        }, 'slow');
//                        $(".operation_alert_box").delay(1500).animate({
//                            height: 'hide',
//                            opacity: 'hide'
//                        }, 'slow');

                        form.submit();
                    }
//                    ,
//                    ignore: []
                }

            );


            $(".handin_bt").click(function() {

                $("#formID").submit();
            });


        });









    </script>



    </head>
    <body>


<!--面包屑-->

<div class="right_content_pagePath">您现在所在的位置：添加或编辑版本信息</div>
<!--面包屑  end-->

<table width="100%"  bgcolor="#2b7bc7" height="35px" align="center"  >
   <tr>
     <td  align="center"><span style=" font-size:18px; font-family:微软雅黑; color:#FFF"></span></td>
   </tr>
</table>

<div class="form">
  <div class="table" style="display:">
    <h3 class="title"  id="001" > <b >填写版本信息</b>
   
    
    <img class="more_up form_action" src="${pageContext.request.contextPath}/resource/images/transparent.gif" alt="收起/展开" title="收起/展开" onClick="formAction(event)"> </h3>

      <form id="formID" action="${pageContext.request.contextPath}/version/upsert-version.do" method="post" >
        <table class="list_table" border="0" cellspacing="0" cellpadding="0" width="100%" >

            <input type="hidden" name="id" value="${edit.id}" />


            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>app名称</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="appNameID" title="1到20个字符" class="input w200"  name="appName" value="${edit.appName}" required minlength="1" maxlength="20"   />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>版本</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="versionNoID" title="1到20个字符" class="input w200"  name="versionNo" value="${edit.versionNo}" required minlength="1" maxlength="20"   />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>渠道</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="channelNoID" title="1到20个字符" class="input w200"  name="channelNo" value="${edit.channelNo}" required minlength="1" maxlength="20"   />
                </td>
            </tr>


            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>URL</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="urlID" title="1到20个字符" class="input w200"  name="url" value="${edit.url}" required minlength="1" maxlength="100"   />
                </td>
            </tr>

            <tr>
                <td class=" w245"  colspan="3">
                    <s:select path="edit.status">
                        <s:options items="${statusOptions}" itemLabel="text" itemValue="value"/>
                    </s:select>
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
