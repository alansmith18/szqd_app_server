<%@ page import="com.szqd.framework.config.SystemConfigParameter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sn" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>图片列表</title>
<link href="${pageContext.request.contextPath}/resource/css/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css" charset="utf-8" href="${pageContext.request.contextPath}/resource/css/styles.css">
<link href="${pageContext.request.contextPath}/resource/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/layout.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/calendar.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"  href="${pageContext.request.contextPath}/resource/css/ui_style.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/custom-report/report.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">


    $(document).ready(function()
    {
        $(document).bind("contextmenu",function(e){
            return true;
        });

        var reportData = ${list};
//        alert(reportData[0].createTimeText+" : "+reportData[0].numberOfClick);
//        var reportData = [200,1000,4231,2341,50,0,1000,50,0,1000,
//            200,1000,4231,2341,50,0,1000,50,0,1000,
//            200,1000,4231,2341,50,0,1000,50,0,1000];
        var lineReport = new LineReport("reportAreaID",reportData);



    });


    function queryWithCondition()
    {
        $("#queryForm").submit();
    }



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

</head>
<body>
            <!--面包屑-->
            <div class="right_content_pagePath">您现在所在的位置：事件列表</div>
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

            <div class="btns" >
                <div id="search_form" class="form_content" >
                    <div class="table" style="">

                        <form id="queryForm" action="${pageContext.request.contextPath}/tracking-statistics/report-number-of-trigger-event.do" method="get">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" >

                                <tr class="">

                                    <td class="td_border_l td_title2 border_t">选择事件：</td>
                                    <td class="td_border_l border_t ">
                                        <s:select path="queryCondition.eventID">
                                            <s:option value="" label="请选择"/>
                                            <s:options items="${eventList}" itemLabel="name" itemValue="id"/>
                                        </s:select>
                                    </td>

                                    <td class="td_border_l td_title2 border_t">开始时间：</td>
                                    <td class="td_border_l border_t ">
                                        <input type="text" id="beginDayID"  title="选择开始时间" class="input w200"  name="beginDay" value="${queryCondition.beginDayText}" readonly  required onfocus="WdatePicker()" >
                                    </td>

                                    <td class="td_border_l td_title2 border_t">结束时间：</td>
                                    <td class="td_border_l border_t ">
                                        <input type="text" id="endDayID"  title="选择结束时间" class="input w200"  name="endDay" value="${queryCondition.endDayText}" readonly  required onfocus="WdatePicker()" >
                                    </td>

                                </tr>

                            </table>
                        </form>
                        <%--<!--显示查询-->--%>


                        <ul class="query_btn float_r" style=" margin-top:12px">
                            <li class="li_bt">
                                <a id="query_btn" class="handin_bt" href="#" onclick="queryWithCondition()">
                                    <span><b>查询</b></span>
                                </a>

                                <%--<a id="query_btn" class="btn_save" href="#"><span>重置</span></a>--%>
                            </li>
                        </ul>
                    <%--</div>--%>
                <%--</div>--%>


            </div>
          </td>

         </tr>
	</table>
    
 
    
    
    <!--显示查询-->
	<div class="groupPanels">
    <!--1-->
     <div id="query_result" class="form_content"   >
               
                <div class="grid_list">
                    <div class="grid_table_col margin-top_none" style="height: 500px;"  >

                        <canvas id="reportAreaID" width="1200" height="500" >
                        </canvas>

                    </div>
                </div>
     </div>
                          <!--1-->
	</div>	
    
</body>
</html>
