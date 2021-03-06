<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>客户信息</title>
<link rel="stylesheet" type="text/css" charset="utf-8" href="${pageContext.request.contextPath}/resource/css/styles.css">
<link href="${pageContext.request.contextPath}/resource/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/layout.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/calendar.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" charset="utf-8">
<script src="${pageContext.request.contextPath}/resource/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.ztree.all-3.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/back_to_top.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/My97DatePicker/WdatePicker.js"></script>
    <!--操作提示 JS-->
    <script>
        $(document).ready(function() {




        });
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

<div class="right_content_pagePath">您现在所在的位置：客户信息</div>
<!--面包屑  end-->

<table width="100%"  bgcolor="#2b7bc7" height="35px" align="center"  >
   <tr>
     <td  align="center"><span style=" font-size:18px; font-family:微软雅黑; color:#FFF"></span></td>
   </tr>
</table>

<div class="form">
  <div class="table" style="display:">
    <h3 class="title"  id="001" > <b >客户信息</b>
   
    
    <img class="more_up form_action" src="${pageContext.request.contextPath}/resource/images/transparent.gif" alt="收起/展开" title="收起/展开" onClick="formAction(event)"> </h3>

      <form id="formSaveProcessID" action="${pageContext.request.contextPath}/process/update-process.do" method="post">
        <table class="list_table" border="0" cellspacing="0" cellpadding="0" width="100%" >
            <input type="hidden" name="pk" value="${sale.pk}" />
            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>客户姓名</td>
                <td class=" w245"  colspan="3">

                    ${sale.customerName}
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>客户手机</td>
                <td class=" w245"  colspan="3">
                    ${sale.customerPhone}

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>客户生日</td>
                <td class=" w245"  colspan="3">
                    ${sale.customerBirth}

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>客户email</td>
                <td class=" w245"  colspan="3">
                    ${sale.customerEmail}

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>客户性别</td>
                <td class=" w245"  colspan="3">
                   <c:if test="${sale.customerSex == 0}">
                        女
                   </c:if>


                    <c:if test="${sale.customerSex == 1}">
                        男
                    </c:if>
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>客户学校</td>
                <td class=" w245"  colspan="3">
                    ${sale.customerSchool}

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>入学年季</td>
                <td class=" w245"  colspan="3">
                    ${sale.customerGrade}

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>客户类型</td>
                <td class=" w245"  colspan="3">
                    ${sale.customerAchievement}

                </td>
            </tr>


            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>家长姓名</td>
                <td class=" w245"  colspan="3">
                    ${sale.customerParent}

                </td>
            </tr>


            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>家长手机</td>
                <td class=" w245"  colspan="3">
                    ${sale.customerParentPhone}

                </td>
            </tr>


            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>客户来源</td>
                <td class=" w245"  colspan="3">
                    ${sale.customerSource}

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>是否签约</td>
                <td class=" w245"  colspan="3">

                   <c:if test="${!sale.contract}">
                        未签约
                   </c:if>


                    <c:if test="${sale.contract}">
                        已签约
                    </c:if>

                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>签约日期</td>
                <td class=" w245"  colspan="3">
                    ${sale.customerContractDate}
                </td>
            </tr>

            <tr>
                <td class="td_title_left w130"><span class="red">&nbsp;</span>备注</td>
                <td class=" w245"  colspan="3">

                    <textarea rows="10" title="必填,长度为1到500" cols="55" name="customerRemark" rangelength="1,500" readonly maxlength="500" >${sale.customerRemark}</textarea>
                </td>
            </tr>

            <%--<tr>--%>
                <%--<td class="td_title_left w130">流程：</td>--%>
                <%--<td  class=" w245">--%>
                    <%--<s:select path="sale.processStep" disabled="true" >--%>
                        <%--<s:options items="${salesStatusList}"  itemLabel="statusText" itemValue="statusCode" />--%>
                    <%--</s:select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td class="td_title_left w130"><span class="red">&nbsp;</span>人员分配</td>--%>
                <%--<td class=" w245"  colspan="3">--%>
                    <%--<s:select path="salesProcess.salesUserId" cssClass=" input" title="请选择销售人员" required="true" >--%>
                        <%--<s:options items="${salesUserList}"  itemLabel="userName" itemValue="pk"  />--%>
                    <%--</s:select>--%>
                <%--</td>--%>
            <%--</tr>--%>

        </table>

      <table class="list_table" border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>

          <td style="width: 340px; border-left:none; padding-left:25px">
              <%--<a id="submit" style="cursor:pointer;" class="handin_bt t-center" ><span>确认</span></a>--%>

              <%--<a id="cancel" style="cursor:pointer;" class="btn_save t-center " onclick="window.history.back();" ><span>返回</span> </a></td>--%>
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
