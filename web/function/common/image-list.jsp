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


<script type="text/javascript">


    function add()
    {
        window.location.href = "${pageContext.request.contextPath}/image-manager/upsert-image-page.do";
    }

    function edit(id)
    {
        window.location.href = "${pageContext.request.contextPath}/image-manager/upsert-image-page.do?id="+id;
    }

    function queryWithCondition()
    {
        $("#queryForm").submit();
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

    $(document).ready(function()
    {
        $(document).bind("contextmenu",function(e){
            return true;
        });

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
            <div class="right_content_pagePath">您现在所在的位置：图片列表</div>
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

                        <form id="queryForm" action="${pageContext.request.contextPath}/image-manager/image-list.do" method="get">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" >

                                <input type="hidden" name="pageNo" value="${pager.pageNo}" id="pageNoID"/>

                                <tr class="">
                                    <td class="td_border_l td_title2 border_t">标题：</td>
                                    <td class="td_border_l border_t ">
                                        <input type="text" name="titleRegex" value="${queryCondition.titleRegex}" />
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
                <button  class="btn_save t-center" onclick="add();">添加</button>

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
                                <th class=" bold" >图片id</th>
                                <th class=" bold" >图片名称</th>
                                <th class=" bold" style="width: 140px">图片URL</th>
                                <th class=" bold" >操作</th>
                            </tr>

                          <c:forEach items="${list}" var="listVar"  >
                          <tr  class="row" >
                              <td>
                                  ${listVar.id}
                              </td>
                              <td >
                                  ${listVar.title}
                              </td>
                              <td>
                                  ${listVar.url}
                              </td>
                              <td>
                                  <a href="#" onclick="edit('${listVar.id}')">操作</a>
                              </td>
                          </tr>
                          </c:forEach>

                              <tr >
                                  <td colspan="2"></td>
                                  <td colspan="3">
                                      <c:if test="${pager.pageNo > 1 }">
                                          <a class="blue" onclick="previousPage()">上一页</a>
                                      </c:if>
                                      <c:if test="${hasNextPage}">
                                          <a class="blue" onclick="nextPage()">下一页</a>
                                      </c:if>
                                  </td>
                              </tr>
                          
                        </table>
                             </div>
                          </div>
                       </div>
                          <!--1-->
	</div>	
    
</body>
</html>
