<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>项目管理</title>
    <link rel="stylesheet" type="text/css" charset="utf-8" href="${pageContext.request.contextPath}/resource/css/styles.css">
    <link href="${pageContext.request.contextPath}/resource/css/reset.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resource/css/layout.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resource/css/main.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resource/css/calendar.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/js/uploadify/uploadify.css" >

    <script src="${pageContext.request.contextPath}/resource/js/jquery-1.8.2.min.js" type="text/javascript"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.ztree.all-3.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/back_to_top.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.validate.js"></script>

    <link href="${pageContext.request.contextPath}/resource/js/jquery-ui-1.11.4/jquery-ui.min.css" rel="stylesheet" type="text/css" charset="utf-8">
    <link href="${pageContext.request.contextPath}/resource/js/jquery-ui-1.11.4/jquery-ui.theme.min.css" rel="stylesheet" type="text/css" charset="utf-8">

    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui-1.11.4/jquery-ui.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/uploadify/jquery.uploadify.js" ></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/uploadifyUtil/uploadifyUtil.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui-contextmenu-master/jquery.ui-contextmenu.min.js"></script>



<script type="text/javascript">


    function submitForm()
    {
        $("#addProjectFormID").submit();
    }

    function showDialog()
    {

         dialog = $("#dialogForm").dialog({
            width: 650,
            modal: true
            ,
            buttons:
            {
                "保存": submitForm
                ,
                "取消": function()
                {
                    dialog.dialog( "close" );
                }
            }
            ,
            close: function() {

            }
        });

    }




    function addProject()
    {

        $("#idID").val("");

        showDialog();
    }

    function editProject(id,name,desc)
    {
        $("#idID").val(id);
        $("#nameID").val(name);
        $("#descriptionID").val(desc);
        showDialog();

    }

    $(document).ready(function(){
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

$(function(){
	$('.calendar').datepicker();
	$('#search_switch').click(function(){
		$('#search_form').toggle();
		$(this).text($('#search_form').is(':visible')?'收起筛选条件':'更多筛选条件');
		parent.fixMainFrame();
	}).click();
});


</script>
</head>
<body>
            <!--面包屑-->
            <div class="right_content_pagePath">您现在所在的位置：项目管理</div>
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

                                    <%--<td class="td_border_l td_title2 border_t">项目名称：</td>--%>
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
                                <%----%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <button  class="btn_save t-center" onclick="addProject()">添加项目</button>

            </div>
          </td>      

         <%--</tr>--%>
	</table>
    
 
    
    
    <!--显示查询-->
	<div class="groupPanels">
    <!--1-->
     <div id="query_result" class="form_content"   >
               
                <div class="grid_list">
                    <div class="grid_table_col margin-top_none">
	  <table id="content_t" border="0" cellspacing="0" cellpadding="0" class="grid_table">
                            <tr class="tr_bg02" style=" border-top:none" >

                                <th class=" bold" style="width: 100px" >id</th>
                                <th class=" bold" style="width: 50px">名称</th>
                                <th class=" bold">描述</th>
                                <th class=" bold td_border_r">操作</th>
                            </tr>

                          <c:forEach items="${projectList}" var="projectVar" >
                          <tr  class="row">
                              <td style="width: 100px">
                                  ${projectVar.id}
                              </td>
                              <td style="width: 50px">${projectVar.name}</td>
                              <td>${projectVar.description}</td>
                              <td>
                                  <a class="blue" onclick="editProject('${projectVar.id}','${projectVar.name}','${projectVar.description}')">编辑</a>
                              </td>

                          </tr>
                          </c:forEach>

                              <%--<tr >--%>
                                  <%--<td colspan="2"></td>--%>
                                  <%--<td colspan="3">--%>

                                      <%--<c:if test="${pager.pageNo > 1 }">--%>
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

        <div id="dialogForm" title="添加项目" style="display: none">

                <form id="addProjectFormID" method="POST" action="${pageContext.request.contextPath}/project/upsert-project.do">
                    <table border="0" cellspacing="0" cellpadding="0" class="grid_table">
                        <input type="hidden" name="id" id="idID" />
                        <tr>
                            <td>名称</td>
                            <td>
                                <input type="text" name="name"  id="nameID" required minlength="4" maxlength="8"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                描述
                            </td>
                            <td>
                                <textarea id="descriptionID" name="description" minlength="1" maxlength="50" rows="10" cols="60" ></textarea>
                            </td>
                        </tr>
                    </table>
                </form>

        </div>
    
</body>
</html>
