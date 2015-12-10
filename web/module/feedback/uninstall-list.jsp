<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>卸载信息列表</title>
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



    function queryWithCondition()
    {
        $("#queryForm").submit();
    }




    $(document).ready(function(){
        $(document).bind("contextmenu",function(e){
            return true;
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
            <div class="right_content_pagePath">您现在所在的位置：卸载信息列表</div>
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

                    <form id="queryForm" action="${pageContext.request.contextPath}/feedback/uninstall-list.do" method="get">
                    <table border="0" cellspacing="0" cellpadding="0" width="100%" >


                        <input type="hidden" name="pageNo" value="1" id="pageNoID"/>

                        <tr class="">

                            <td class="td_border_l td_title2 border_t">产品名：</td>
                            <td class="td_border_l border_t ">
                                <input type="text" name="appName" value="${appName}" />
                            </td>


                        </tr>
                        <tr class="">
                            <td class="td_border_l td_title2 border_t">开始时间：</td>
                            <td class="td_border_l border_t ">
                                <input type="text" readonly="readonly" class="input name  w130 " name="beginDate" value="${beginDate}" onclick="WdatePicker()">
                            </td>

                            <td class="td_title2 border_t td_border_l">结束时间:</td>
                            <td  class="border_t td_border_l">
                                <input type="text" readonly="readonly" class="input name  w130 " name="endDate" value="${endDate}" onclick="WdatePicker()">
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

                                        <th class=" bold" >名称</th>
                                        <th class=" bold" >数量</th>

                                    </tr>

                                  <c:forEach items="${resultList}" var="uninstall" >
                                  <tr  class="row">
                                    <td style="width: 60px">
                                        ${uninstall["_id"]}
                                    </td>
                                    <td style="width: 140px">
                                        ${uninstall["uninstallCount"]}
                                    </td>
                                  </tr>
                                  </c:forEach>


                           </table>
                             </div>
                          </div>
                       </div>
                          <!--1-->
	</div>

</body>
</html>
