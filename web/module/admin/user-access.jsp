<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>用户权限管理</title>
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

    function addUser()
    {
        window.location.href = "${pageContext.request.contextPath}/popularize/show-adduser-page.do";
    }

    function editUser(url)
    {
        window.location.href = url;
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
            <div class="right_content_pagePath">您现在所在的位置：用户权限管理</div>
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
           <%--<div id="search_form" class="form_content" >--%>
                <%--<div class="table" style="">--%>
                    <%--<table border="0" cellspacing="0" cellpadding="0" width="100%" >--%>
                        <%--<tr class="">--%>
                         <%--<td class="td_title2 border_t td_border_l">发起来源：</td>--%>
                            <%--<td  class="border_t td_border_l">--%>
                      <%--<select name="select" class="input w130 ">--%>
                         <%--<option value="0">重点工作督办</option>--%>
                         <%--<option value="0">议定事项落实</option>--%>
                         <%--<option value="0">专项任务</option>--%>
                     <%--</select>--%>
                            <%--</td>--%>
                            <%--<td class=" td_title2  td_left_top_n">责任部门：</td>--%>
                            <%--<td class="border_t td_border_l ">--%>
                     <%--<select name="select" class="input w130 ">--%>
                         <%--<option value="0">战法部</option>--%>
                         <%--<option value="0">综合部</option>--%>
                     <%--</select>--%>
                            <%--</td>--%>
                           <%----%>
                            <%--<td class="td_border_l td_title2 border_t">工作编号：</td>--%>
                            <%--<td class="td_border_l border_t "><input type="text" class="input name  w130 "></td>--%>
                        <%--</tr>--%>
                        <%----%>
                        <%----%>
                      <%--</table> --%>
                        <%----%>
                     <%--<!--显示查询-->   --%>
                        <%----%>
                   <%----%>
                    <%--<ul class="query_btn float_r" style=" margin-top:12px">--%>
                                <%--<li class="li_bt"> <a id="query_btn" class="handin_bt" href="#"><span><b>查询</b></span></a>--%>
                                 <%--<a id="query_btn" class="btn_save" href="#"><span>重置</span></a></li>--%>
                            <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
            <div class="btns" >
            	<%--<button id="search_switch" class="btn_save t-center">更多筛选条件</button>--%>
                <button  class="btn_save t-center" onclick="addUser();">添加用户</button>
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
                            <tr class="tr_bg02" style=" border-top:none" >

                                <th class=" bold">供应商名称</th>
                                <th class=" bold">登陆名</th>
                                <th class=" userName bold ">登陆密码</th>
                                <th class=" bold">增量</th>
                                <th class=" bold">比例</th>
                                <th class=" bold td_border_r">操作</th>

                            </tr>
                            
                          <c:forEach items="${platformUsers}" var="user" >
                          <tr  class="row">
                            <td>${user.platformName}</td>
                            <td>${user.loginName}</td>
                            <td>${user.loginPwd}</td>
                            <td>${user.incremental}</td>
                            <td>${user.scale}</td>
                            <td><a class="blue" onclick="editUser('${pageContext.request.contextPath}/popularize/edit-user.do?id=${user.id}')">编辑</a></td>

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
