<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>添加主题</title>
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
                    ,
                    ignore: []
                }

            );


            $(".handin_bt").click(function() {

                $("#formSaveUserID").submit();
            });


            uploadThemeWithCustomParam("#thumbnailsFileID","#thumbnailsID","${pageContext.request.contextPath}","{}",callbackFunctionForUploadTheme);

            uploadThemeWithCustomParam("#themeFileID","#themeID","${pageContext.request.contextPath}","{}",callbackFunctionForUploadTheme);
        });


        function callbackFunctionForUploadTheme(resultID,callbackData)
        {

            $(resultID).val(callbackData.resultData.relativePath);
        }
    </script>

<%--<script type="text/javascript">--%>

<%--$(function(){--%>
	<%--//--%>
	<%--// approveBar...--%>
	<%--$('#approvePlace').height($('#approveBar').outerHeight());--%>
	<%--$('#approveBar .btns a').click(function(){--%>
		<%--$('#approveBar').toggleClass('open', $(this).is('.showlink'));--%>
		<%--$('#approvePlace').height($('#approveBar').outerHeight());--%>
		<%--parent.fixMainFrame();--%>
		<%--$(parent).scroll();--%>
	<%--});--%>
	<%--$(parent).scroll(function(){--%>
		<%--var _v = $(this.document).height()-$(this.window).height()-$(this).scrollTop()-70;--%>
		<%--$('#approveBar').toggleClass('float', _v>0).css('bottom',_v);--%>
	<%--}).resize(function(){$(this).scroll();}).scroll();--%>

	<%----%>
<%--});--%>

<%--function formAction(event) {--%>
	<%--var target = (event.currentTarget) ? event.currentTarget : event.srcElement;--%>
	<%--if ($(target).parent().next().is(':visible') ) {--%>
		<%--$(target).parent().next().hide();--%>
		<%--$(target).addClass('more_down');--%>
	<%--}else {--%>
		<%--$(target).parent().next().show();--%>
		<%--$(target).removeClass('more_down');--%>
	<%--}--%>
	<%--parent.fixMainFrame();--%>
	<%--$(parent).scroll();--%>
<%--}--%>

<%--$(function(){--%>
	<%--$('#tabs').tabs().bind('tabsshow',function(){--%>
		<%--parent.fixMainFrame();--%>
	<%--});--%>
	<%--//--%>
	<%--$(".show_btn7").click(function(){--%>
		<%--alert('是否确定要删除所选会签部门？');--%>
	<%--});--%>
	<%--$(".show_btn2").click(function(){--%>
		<%--parent.openDialog("#dialog2");--%>
	<%--});--%>
	<%--$(".show_btn8").click(function(){--%>
		<%--parent.openDialog("#dialog8");--%>
	<%--});--%>
	<%--$(".show_btn1").click(function(){--%>
		<%--parent.openDialog("#dialog1");--%>
	<%--});--%>
<%--});--%>
    <%--</script>--%>

    </head>
    <body>
    <%--<ul id="tags_list">--%>
  <%--<li><a target="mainFrame" href="发起部门主要工作负责人审批.html#001">工作信息</a></li>--%>
  <%--<li><a target="mainFrame" href="发起部门主要工作负责人审批.html#002">月分解值</a></li>--%>
  <%--<li><a target="mainFrame" href="发起部门主要工作负责人审批.html#003">其他信息</a></li>--%>
  <%--<li><a target="mainFrame" href="发起部门主要工作负责人审批.html#004">查看备注</a></li>--%>
  <%--<li><a target="mainFrame" href="发起部门主要工作负责人审批.html#005">审批历史</a></li>--%>
<%--</ul>--%>

<!--面包屑-->

<div class="right_content_pagePath">您现在所在的位置：添加主题</div>
<!--面包屑  end-->

<table width="100%"  bgcolor="#2b7bc7" height="35px" align="center"  >
   <tr>
     <td  align="center"><span style=" font-size:18px; font-family:微软雅黑; color:#FFF">新增的主题将立即生效</span></td>
   </tr>
</table>

<div class="form">
  <div class="table" style="display:">
    <h3 class="title"  id="001" > <b >主题信息</b>
   
    
    <img class="more_up form_action" src="${pageContext.request.contextPath}/resource/images/transparent.gif" alt="收起/展开" title="收起/展开" onClick="formAction(event)"> </h3>

      <form id="formSaveUserID" action="${pageContext.request.contextPath}/theme/add-theme.do" method="post">
        <table class="list_table" border="0" cellspacing="0" cellpadding="0" width="100%" >

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>名称</td>
                <td class=" w245"  colspan="3">

                    <input type="text" title="只能为2到10个字符,并且不能重复" class="input w200" id="userIdID" name="name"  required minlength="2" maxlength="10"  />

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>预览图</td>
                <td class=" w245"  colspan="3">
                    <input type="file" id="thumbnailsFileID"   >
                    <input type="hidden" id="thumbnailsID" name="thumbnails"  required />
                </td>
            </tr>


            <tr >
                <td class="td_title_left w130"><span class="red">&nbsp;</span>主题包</td>
                <td class=" w245"  colspan="3">
                    <input type="file" id="themeFileID"   >
                    <input type="hidden" id="themeID" name="url" required />

                </td>
            </tr>


        </table>

      <table class="list_table" border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>

          <td style="width: 340px; border-left:none; padding-left:25px">
              <a id="submit" class="handin_bt t-center" ><span>确认</span></a>

              <a id="cancel"  class="btn_save t-center " onclick="window.history.back();" ><span>返回</span> </a></td>
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
