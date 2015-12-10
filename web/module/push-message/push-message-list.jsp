<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>推送消息列表</title>
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

    function forecastPushMessageCount()
    {
        var paramMap = {};


        $.ajax
        ({
            type: "POST",
            url: "${pageContext.request.contextPath}/push-message/forecast-push-message-count-for-tomorrow.do",
            data: paramMap,
            dataType: "json",
            success: function(data)
            {
                alert("预计明天推送消息数量为:"+data.resultData);

            }
        });
    }

    function topPush(id)
    {
        window.location.href = "${pageContext.request.contextPath}/push-message/top-push-message.do?id="+id;
    }

    function addPush()
    {
        window.location.href = "${pageContext.request.contextPath}/push-message/add-push-message-page.do";
    }

    function editPush(id)
    {
        window.location.href = "${pageContext.request.contextPath}/push-message/add-push-message-page.do?id="+id;
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
            <div class="right_content_pagePath">您现在所在的位置：推送消息列表</div>
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

                        <form id="queryForm" action="${pageContext.request.contextPath}/push-message/list-push-message.do" method="get">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%"  >

                                <input type="hidden" name="pageNo" value="1" id="pageNoID"/>

                                <tr class="">
                                    <td class="td_border_l td_title2 border_t" style="width: 60px" >标题：</td>
                                    <td class="td_border_l border_t " style="width: 100px">
                                        <input type="text" name="title" value="${queryCondition.title}" />
                                    </td>

                                    <td class="td_border_l td_title2 border_t">子标题：</td>
                                    <td class="td_border_l border_t " >
                                        <input type="text" name="subTitle" value="${queryCondition.subTitle}" />
                                    </td>
                                </tr>

                                <tr class="">
                                    <td class="td_border_l td_title2 border_t" >推送形式：</td>
                                    <td class="td_border_l border_t ">
                                        <s:select path="queryCondition.pushType">
                                            <s:option value="" label="请选择"/>
                                            <s:options items="${pushTypeEnumList}" itemLabel="name" itemValue="id" />
                                        </s:select>
                                    </td>

                                    <td class="td_border_l td_title2 border_t">目标地址类型：</td>
                                    <td class="td_border_l border_t ">
                                        <s:select path="queryCondition.urlType">
                                            <s:option value="" label="请选择"/>
                                            <s:options items="${targetURLTypeEnumList}" itemLabel="name" itemValue="id" />
                                        </s:select>
                                    </td>

                                    <td class="td_border_l td_title2 border_t" >所属项目：</td>
                                    <td class="td_border_l border_t " >
                                        <s:select path="queryCondition.project">
                                            <s:option value="" label="请选择"/>
                                            <s:options items="${projectList}" itemLabel="name" itemValue="id" />
                                        </s:select>
                                    </td>
                                </tr>

                                <tr class="" >
                                    <td class="td_border_l td_title2 border_t" >消息类型：</td>
                                    <td class="td_border_l border_t " colspan="4" >
                                        <s:checkboxes path="queryCondition.messageTypes" items="${messageTypeEnumList}" itemValue="id" itemLabel="name" />
                                    </td>
                                </tr>


                                <tr>
                                    <td class="td_border_l td_title2 border_t">开始时间：</td>
                                    <td class="td_border_l border_t ">
                                        <input type="text" readonly="readonly" class="input name  w130 " name="beginTimeText" value="${queryCondition.beginTimeText}" onclick="WdatePicker()">
                                    </td>

                                    <td class="td_title2 border_t td_border_l">结束时间:</td>
                                    <td  class="border_t td_border_l">
                                        <input type="text" readonly="readonly" class="input name  w130 " name="endTimeText" value="${queryCondition.endTimeText}" onclick="WdatePicker()">
                                    </td>


                                    <td class="td_title2 border_t td_border_l">未过期:</td>
                                    <td  class="border_t td_border_l">
                                        <input type="checkbox" readonly="readonly" class="input name  w130 " name="unexpired" value="true"
                                                <c:if test="${queryCondition.unexpired}">
                                                    checked
                                                </c:if>
                                                >
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
                                <a id="query_btn" class="btn_save" href="#" onclick="forecastPushMessageCount()" ><span>预计推送</span></a>
                                <%--<a id="query_btn" class="btn_save" href="#"><span>重置</span></a>--%>
                            </li>
                        </ul>
                    <%--</div>--%>
                <%--</div>--%>
                <button  class="btn_save t-center" onclick="addPush();">添加推送消息</button>

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

                                <th class=" bold" style="width: 60px">icon</th>
                                <th class=" bold" style="width: 140px">标题</th>
                                <th class=" bold" style="width: 140px">子标题</th>
                                <th class=" bold" style="width: 60px">消息类型</th>
                                <th class=" bold" style="width: 140px">推送形式</th>
                                <th class=" bold" style="width: 140px">有效时间</th>
                                <th class=" bold" style="width: 140px">操作</th>

                            </tr>

                          <c:forEach items="${pushMessageList}" var="pushVar" >
                          <tr  class="row">
                              <td >
                                  <img width="72px" height="72px" src="${pageContext.request.contextPath}${pushVar.icon}">
                              </td>
                              <td >
                                  ${pushVar.title}
                              </td>
                              <td>
                                  ${pushVar.subTitle}
                              </td>
                              <td>
                                  ${pushVar.messageTypesText}
                              </td>
                              <td>
                                  ${pushVar.pushTypeText}
                              </td>
                              <td>${pushVar.beginTimeText} -- ${pushVar.endTimeText}</td>
                              <td>
                                  <a onclick="editPush('${pushVar.id}')">修改</a>
                                  <a onclick="topPush('${pushVar.id}')">置顶</a>
                                  <%--<a>删除</a>--%>
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
