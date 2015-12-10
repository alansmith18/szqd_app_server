<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>app激活信息列表</title>
<link href="${pageContext.request.contextPath}/resource/css/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css" charset="utf-8" href="${pageContext.request.contextPath}/resource/css/styles.css">
<link href="${pageContext.request.contextPath}/resource/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/layout.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/calendar.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"  href="${pageContext.request.contextPath}/resource/css/ui_style.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/My97DatePicker/WdatePicker.js"></script>


<script type="text/javascript">

    function detailPage(orderID)
    {
        window.location.href = "${pageContext.request.contextPath}/process/process-detail-page.do?pk="+orderID;
    }

    function dealOrder(orderID)
    {

        window.location.href = "${pageContext.request.contextPath}/process/deal-process-page.do?pk="+orderID;
    }

    function queryWithCondition()
    {

        $("#queryForm").submit();
    }

    function addOrder()
    {
        window.location.href = "${pageContext.request.contextPath}/process/make-process-page.do";
    }

    function nextPage()
    {
        var value = parseInt($("#pageNoID").attr("value"));
        $("#pageNoID").attr("value",(value+1));
        $("#queryForm").submit();
    }

    function previousPage()
    {
        var value = parseInt($("#pageNoID").attr("value"));
        $("#pageNoID").attr("value",(value-1));
        $("#queryForm").submit();
    }

    $(document).ready(function(){
        $(document).bind("contextmenu",function(e){
            return false;
        });


//        $('dl').css('cursor', 'pointer')

    });

//
$(function() {
	//
	$('.tabs').click(function(){
		$(this).addClass('active').siblings().removeClass('active').andSelf().removeClass('shadow');
		$(this).next().addClass('shadow');
		$('.groupPanels').hide().eq($(this).index()).show();
		parent.fixIframeSize(true);
	}).first().click();
});
</script>
<%--<script src="${pageContext.request.contextPath}/resource/js/jquery.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript">

//$(function(){
//	$('.calendar').datepicker();
//	$('#search_switch').click(function(){
//		$('#search_form').toggle();
//		$(this).text($('#search_form').is(':visible')?'收起筛选条件':'更多筛选条件');
//		parent.fixMainFrame();
//	}).click();
//});


</script>
</head>
<body>
            <!--面包屑-->
            <div class="right_content_pagePath">您现在所在的位置：app激活信息列表</div>
            <!--面包屑  end-->


           <!--查询-->
         <table class="groupPanelTable" width="100%" cellspacing="0">
		  <%--<tr>--%>
            <%--<td class="tabs">--%>
			<%--<div class="panelCell">--%>
				<%--<div class="icon">--%>
                    <%--<img src="${pageContext.request.contextPath}/resource/images/btn/fp.png" />--%>
                <%--</div>--%>
				<%--<div class="title">全部</div>--%>
				<%--<label>16</label>--%>
		  	<%--</div>--%>
		  <%--</td>--%>
		<%--</tr>--%>
        <tr>
          <td  colspan="4" class="tab2s">
           <div id="search_form" class="form_content" >
                <div class="table" style="">

                    <form id="queryForm" action="${pageContext.request.contextPath}/popularize/query-activation.do" method="get">
                    <%--<s:form action="${pageContext.request.contextPath}/popularize/query-activation.do" method="get" id="queryForm">--%>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%" >

                        <tr class="">

                            <td class="td_border_l td_title2 border_t">产品：</td>
                            <td class="td_border_l border_t ">

                                <s:select path="appCondition.appID"   >
                                    <s:option value="" label="所有" />
                                    <s:options items="${allAppProductList}" itemLabel="appName" itemValue="appID" />
                                </s:select>

                            </td>
                            <security:authentication var="principal" property="principal" />
                            <c:if test="${principal.username == 'admin'}">
                                <td class="td_border_l td_title2 border_t">合作方：</td>
                                <td class="td_border_l border_t ">
                                    <s:select path="platformCondition.platformID"  >
                                        <s:option value="" label="所有" />
                                        <s:options items="${allPlatformUsersExceptAdmin}"  itemLabel="platformName" itemValue="platformID" />
                                    </s:select>

                                </td>
                            </c:if>

                            <td class="td_border_l td_title2 border_t">开始时间：</td>
                            <td class="td_border_l border_t ">
                                <input type="text" readonly="readonly" class="input name  w130 " name="beginDate" value="${appCondition.beginDate}" onclick="WdatePicker()">
                            </td>

                            <td class="td_title2 border_t td_border_l">结束时间:</td>
                            <td  class="border_t td_border_l">
                                <input type="text" readonly="readonly" class="input name  w130 " name="endDate" value="${appCondition.endDate}" onclick="WdatePicker()">
                            </td>

                        </tr>

                      </table>
                    </form>
                     <!--显示查询-->


                    <ul class="query_btn float_r" style=" margin-top:12px">
                        <li class="li_bt">
                            <a id="query_btn" class="handin_bt" href="#" onclick="queryWithCondition()">
                                <span><b>查询</b></span>
                            </a>
                            <%--<a id="query_btn" class="btn_save" href="#"><span>重置</span></a>--%>
                        </li>
                    </ul>
                </div>
            </div>

          </td>

         </tr>
	</table>




    <!--显示查询-->
	<div class="groupPanels">
    <!--1-->
     <div id="query_result" class="form_content"   >

                <div class="grid_list">
                    <div class="grid_table_col margin-top_none">
	                        <table id="content_t" border="0" cellspacing="0" cellpadding="0" class="grid_table">

                                    <tr class="tr_bg02" style=" border-top:none;"  >

                                        <th class=" bold">合作方</th>
                                        <th class=" bold">产品名称</th>
                                        <th class=" bold">有效用户数</th>

                                    </tr>

                                  <c:forEach items="${analysisInfoForPlatform}" var="appAnalysisInfoVar" >
                                  <tr  class="row">
                                    <td>${appAnalysisInfoVar.plateformName}</td>
                                    <td>${appAnalysisInfoVar.appName}</td>
                                    <td>${appAnalysisInfoVar.calculateUserCount}</td>

                                  </tr>
                                  </c:forEach>
                                  <%--<tr >--%>
                                      <%--<td colspan="2"></td>--%>
                                      <%--<td colspan="3">--%>
                                          <%--<c:if test="${salesOrderCondition.pageNo != 1}">--%>
                                            <%--<a class="blue" onclick="previousPage()">上一页</a>--%>
                                          <%--</c:if>--%>
                                          <%--<c:if test="${hasNextPage}">--%>
                                            <%--<a class="blue" onclick="nextPage()">下一页</a>--%>
                                          <%--</c:if>--%>
                                      <%--</td>--%>
                                  <%--</tr>--%>

                           </table>
                             </div>
                          </div>
                       </div>
                          <!--1-->
	</div>

</body>
</html>
