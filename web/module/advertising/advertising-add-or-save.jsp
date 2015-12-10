<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>添加广告</title>


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

    <link href="${pageContext.request.contextPath}/resource/js/uploadify/uploadify.css" rel="stylesheet" type="text/css" charset="utf-8">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/My97DatePicker/WdatePicker.js"></script>



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
            paramMap["subpath"] = "/advertising";

            var callbackParam = {};
            callbackParam["resultID"] = "#picPathID";
            callbackParam["divID"] = "#picPathDIVID";
            uploadFileWithCustomParam("/common/uploadFile.do","#picPathFileID","${pageContext.request.contextPath}",paramMap,callback,callbackParam);


        });


        function callback(callbackParam)
        {
            var jsonData = callbackParam["jsonData"];
            var resultID = callbackParam["resultID"];
            var divID = callbackParam["divID"];

            $(resultID).val(jsonData.resultData.relativePath);

            $(divID).html("<img src='${pageContext.request.contextPath}"+jsonData.resultData.relativePath+"' width='100px' height='100px' >");
        }


    </script>



    </head>
    <body>


<!--面包屑-->

<div class="right_content_pagePath">您现在所在的位置：添加广告</div>
<!--面包屑  end-->

<table width="100%"  bgcolor="#2b7bc7" height="35px" align="center"  >
   <tr>
     <td  align="center"><span style=" font-size:18px; font-family:微软雅黑; color:#FFF"></span></td>
   </tr>
</table>

<div class="form">
  <div class="table" style="display:">
    <h3 class="title"  id="001" > <b >填写广告信息</b>
   
    
    <img class="more_up form_action" src="${pageContext.request.contextPath}/resource/images/transparent.gif" alt="收起/展开" title="收起/展开" onClick="formAction(event)"> </h3>

      <form id="formSaveUserID" action="${pageContext.request.contextPath}/advertising/add-advertising.do" method="post" >
        <table class="list_table" border="0" cellspacing="0" cellpadding="0" width="100%" >

            <input type="hidden" name="id" value="${edit.id}" />

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>名称</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="nameID" title="2到10个字符" class="input w200"  name="name" value="${edit.name}" required minlength="2" maxlength="10"   />
                </td>
            </tr>


            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>类型</td>
                <td class=" w245"  colspan="3">

                    <s:select path="edit.type">
                        <s:options items="${advertisingTypeList}" itemLabel="name" itemValue="id" />
                    </s:select>

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>平台</td>
                <td class=" w245"  colspan="3">

                    <s:checkboxes path="edit.platform" items="${platformEnumList}" itemValue="id" itemLabel="name" />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>网络</td>
                <td class=" w245"  colspan="3">

                    <s:checkboxes path="edit.networkType" items="${netOperatorsEnumList}" itemValue="id" itemLabel="name" />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>项目</td>
                <td class=" w245"  colspan="3">

                    <s:checkboxes path="edit.projects" items="${projectList}" itemValue="id" itemLabel="name" />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>广告点击地址</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="targetURLID" title="5到100个字符" class="input w200"  name="targetURL" value="${edit.targetURL}"   maxlength="100"   />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>包名</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="packageNameID" title="最大100个字符" class="input w200"  name="packageName" value="${edit.packageName}"   maxlength="100"   />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>描述</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="descID" title="最大100个字符" class="input w200"  name="desc" value="${edit.desc}"   maxlength="100"   />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>广告图片:</td>
                <td class=" w245"  colspan="3">

                    <input type="file" id="picPathFileID"  />
                    <input type="hidden" id="picPathID" name="picPath" value="${edit.picPath}" />

                    <br/>
                    <div id="picPathDIVID">
                        <c:if test="${!empty edit.picPath}">
                            <img src="${pageContext.request.contextPath}${edit.picPath}" width="100px" height="100px"/>
                        </c:if>
                    </div>

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>播放次数</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="playTimeID" title="填写数字" class="input w200"  name="playTime" value="${edit.playTime}" required digits="true"  maxlength="5"   />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>时长</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="durationID" title="填写数字" class="input w200"  name="duration" value="${edit.duration}" required digits="true"  maxlength="5"   />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>占用屏幕类型</td>
                <td class=" w245"  colspan="3">

                    <s:select path="edit.frameType">
                        <s:options items="${frameTypeEnumList}" itemLabel="name" itemValue="id" />
                    </s:select>
                </td>
            </tr>


            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>点击类型</td>
                <td class=" w245"  colspan="3">

                    <s:select path="edit.clickType">
                        <s:options items="${clickTypeEnumList}" itemLabel="name" itemValue="id" />
                    </s:select>
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>生效开始时间</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="effectiveStartTimeTextID" title="选择生效时间" class="input w200"  name="effectiveStartTimeText" value="${effectiveStartTimeText}" required readonly="readonly" onclick="WdatePicker()"     />
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>生效结束时间</td>
                <td class=" w245"  colspan="3">
                    <input type="text" id="effectiveEndTimeTextID" title="选择生效时间" class="input w200"  name="effectiveEndTimeText" value="${effectiveEndTimeText}" required readonly="readonly" onclick="WdatePicker()"     />
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
