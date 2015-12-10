<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>精品排序</title>
<link href="${pageContext.request.contextPath}/resource/css/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" charset="utf-8">
<link rel="stylesheet" type="text/css" charset="utf-8" href="${pageContext.request.contextPath}/resource/css/styles.css">
<link href="${pageContext.request.contextPath}/resource/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/layout.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/calendar.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"  href="${pageContext.request.contextPath}/resource/css/ui_style.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.validate.js"></script>


<script type="text/javascript">


    $(document).ready(function()
    {
        $(document).bind("contextmenu",function(e){
            return true;
        });


        var validator = $("#formID").validate
        (
                {
                    submitHandler: function(form)
                    {

                        form.submit();
                    }
//                    ,
//                    ignore: []
                }

        );

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
            <div class="right_content_pagePath">您现在所在的位置：精品排序</div>
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
                <%--<div id="search_form" class="form_content" >--%>
                    <%--<div class="table" style="">--%>

                        <%--<form id="queryForm" action="${pageContext.request.contextPath}/theme/theme-list.do" method="get">--%>
                            <%--<table border="0" cellspacing="0" cellpadding="0" width="100%" >--%>


                                <%--<input type="hidden" name="pageNo" value="1" id="pageNoID"/>--%>

                                <%--<tr class="">--%>

                                    <%--<td class="td_border_l td_title2 border_t">主题名称：</td>--%>
                                    <%--<td class="td_border_l border_t ">--%>
                                        <%--<input type="text" name="name" value="${queryCondition.name}" />--%>
                                    <%--</td>--%>

                                <%--</tr>--%>

                            <%--</table>--%>
                        <%--</form>--%>
                        <%--<!--显示查询-->--%>


                        <%--<ul class="query_btn float_r" style=" margin-top:12px">--%>
                            <%--<li class="li_bt">--%>
                                <%--<a id="query_btn" class="handin_bt" href="#" onclick="queryWithCondition()">--%>
                                    <%--<span><b>查询</b></span>--%>
                                <%--</a>--%>
                                <%--&lt;%&ndash;<a id="query_btn" class="btn_save" href="#"><span>重置</span></a>&ndash;%&gt;--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<button  class="btn_save t-center" onclick="addRecommendCategory();">添加精品分类</button>--%>

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
                        <form id="formID" action="${pageContext.request.contextPath}/recommend/save-orderby-category.do" method="post">
	                    <table id="content_t" border="0" cellspacing="0" cellpadding="0" class="grid_table">

                            <c:set value="order${category.project}" var="orderKey" />
                            <input type="hidden" name="orderFieldName" value="${orderKey}"/>


                          <c:forEach items="${recommendList}" var="recommendVar" varStatus="varStatus">
                          <tr  class="row">
                              <input type="hidden" name="recommendID" value="${recommendVar['_id']}"/>
                              <td >
                                <img src="${pageContext.request.contextPath}${recommendVar['icon']}" width="100px" height="100px">
                              </td>
                              <td>
                                  ${recommendVar['appName']}
                              </td>
                              <td>
                                  <input type="text" id="orderKey${varStatus.index}" name="${orderKey}" value="${recommendVar[orderKey]}" title="填写数字" required digits="true" />
                              </td>
                          </tr>

                          </c:forEach>
                            <tr>
                                <td>
                                    <input type="submit" value="保存"/>
                                </td>
                            </tr>
                        </table>
                        </form>
                             </div>
                          </div>
                       </div>
                          <!--1-->
	</div>	
    
</body>
</html>
