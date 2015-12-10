<%@ page import="com.szqd.framework.config.SystemConfigParameter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="a" uri="http://www.springframework.org/tags" %>



<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>广告列表</title>
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

    function switchExpired()
    {

    }
<security:authorize ifAllGranted="ROLE_AD_PLATFORM_ADVERTISER">
    function add()
    {
        window.location.href = "${pageContext.request.contextPath}/ad-alliance/submit-advertising-page.do";
    }
</security:authorize>

<security:authorize ifAnyGranted="ROLE_AD_PLATFORM_ADVERTISER,ROLE_OPERATIONS">
    function edit(id)
    {
        window.location.href = "${pageContext.request.contextPath}/ad-alliance/submit-advertising-page.do?id="+id;
    }
</security:authorize>

    <security:authorize ifAnyGranted="ROLE_OPERATIONS">
    function editActivation(id)
    {
        window.parent.open("${pageContext.request.contextPath}/ad-alliance/activation-page.do?id="+id);
    }
    </security:authorize>

<security:authorize ifAllGranted="ROLE_AD_PLATFORM_CHANNEL">
    function selectAd(id)
    {
        $.ajax
        ({
            type: "GET",
            url: "${pageContext.request.contextPath}/ad-alliance/select-ad.do?id="+id,
//            data: paramMap,
            dataType: "json",
            success: function(data)
            {
                queryWithCondition();
            },
            error: function()
            {
                alert("请刷新后重新登录");
            }
        });

    }
</security:authorize>


    function expired(id)
    {
        <%--window.location.href = "${pageContext.request.contextPath}/red-gift/save-gift.do?id="+id+"&isValidateExpire=true";--%>
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
            <div class="right_content_pagePath">您现在所在的位置：广告列表</div>
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

                        <form id="queryForm" action="${pageContext.request.contextPath}/ad-alliance/list-advertising.do" method="get">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%" >

                                <input type="hidden" name="pageNo" value="${pager.pageNo}" id="pageNoID"/>

                                <tr class="">
                                    <td class="td_border_l td_title2 border_t">名称：</td>
                                    <td class="td_border_l border_t ">
                                        <input type="text" name="regexName" value="${queryCondition.regexName}" />
                                    </td>

                                    <security:authorize ifAllGranted="ROLE_AD_PLATFORM_CHANNEL" >
                                        <input type="hidden" name="status" value="1" />
                                    </security:authorize>

                                    <security:authorize ifAnyGranted="ROLE_AD_PLATFORM_ADVERTISER,ROLE_OPERATIONS">
                                    <td class="td_border_l td_title2 border_t">状态：</td>
                                    <td class="td_border_l border_t ">
                                        <s:radiobuttons path="queryCondition.status" items="${statusList}" itemLabel="text" itemValue="value" />
                                    </td>
                                    </security:authorize>
                                    <%--<td class="td_border_l td_title2 border_t">是否过期：</td>--%>
                                    <%--<td class="td_border_l border_t ">--%>
                                        <%--<input type="checkbox" name="isValidateExpire" value="false"--%>
                                                <%--<c:if test="${queryCondition.isValidateExpire != null}">checked</c:if>--%>
                                                <%--/>过期--%>
                                    <%--</td>--%>
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
                    </div>
                </div>
                <security:authorize ifAllGranted="ROLE_AD_PLATFORM_ADVERTISER">
                    <button  class="btn_save t-center" onclick="add();">添加</button>
                </security:authorize>

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
                                <th class=" bold" style="width: 60px">图片</th>

                                <security:authorize ifAnyGranted="ROLE_AD_PLATFORM_CHANNEL,ROLE_OPERATIONS">
                                <th class=" bold" style="width: 60px">激活数</th>
                                </security:authorize>

                                <th class=" bold" style="width: 60px">名称</th>
                                <th class=" bold" style="width: 140px">地址</th>
                                <th class=" bold" style="width: 140px">描述</th>
                                <th class=" bold" style="width: 140px">分辨率</th>
                                <th class=" bold" style="width: 140px">状态</th>
                                <th class=" bold" style="width: 140px">操作</th>
                            </tr>

                            <security:authentication property="principal.userEntity.id" var="loginUserID"/>

                          <c:forEach items="${list}" var="listVar" >
                          <tr  class="row"
                                  <security:authorize ifAnyGranted="ROLE_AD_PLATFORM_CHANNEL">
                                    <c:forEach items="${listVar.channelIDList}" var="channelIDVar">
                                        <c:if test="${channelIDVar == loginUserID}">
                                            style="background-color: limegreen"
                                        </c:if>
                                    </c:forEach>
                                  </security:authorize>

                                  <security:authorize ifAnyGranted="ROLE_OPERATIONS">

                                      <c:if test="${listVar.pendingSize > 0}">
                                      style="background-color: limegreen"
                                      </c:if>

                                  </security:authorize>

                                  >
                              <td >

                                  <img width="72px" height="72px" src="<%=SystemConfigParameter.HTTP_IMAGE_DOMAIN()%>${pageContext.request.contextPath}${listVar.pic}">
                              </td>

                              <security:authorize ifAnyGranted="ROLE_AD_PLATFORM_CHANNEL">
                              <td>
                                  <c:forEach items="${listVar.activations}" var="actVar">
                                    <c:if test="${actVar.channelID == loginUserID}">
                                        ${actVar.numberOfActivation}
                                    </c:if>
                                  </c:forEach>
                              </td>
                              </security:authorize>

                              <td >
                                  ${listVar.name}
                              </td>
                              <td>
                                  <a href="${listVar.url}">${listVar.url}</a>
                              </td>
                              <td>
                                  ${listVar.desc}
                              </td>
                              <td >
                                  ${listVar.width} X ${listVar.height}
                              </td>
                              <td>
                                  ${listVar.statusText}
                              </td>
                              <td>
                                  <security:authorize ifAllGranted="ROLE_AD_PLATFORM_ADVERTISER">
                                      <c:if test="${listVar.status == 2}">
                                          <a onclick="edit('${listVar.id}')">编辑</a>
                                      </c:if>
                                  </security:authorize>

                                  <security:authorize ifAllGranted="ROLE_OPERATIONS">
                                      <a onclick="edit('${listVar.id}')">编辑</a>
                                      <a onclick="editActivation('${listVar.id}')">激活数</a>
                                  </security:authorize>

                                  <security:authorize ifAllGranted="ROLE_AD_PLATFORM_CHANNEL">
                                      <a onclick="selectAd('${listVar.id}')">选择</a>
                                  </security:authorize>

                                  <%--<a onclick="expired('${listVar.id}')">过期</a>--%>
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
                          
                        </div>
                             </div>
                          </div>
                       </div>
                          <!--1-->
	</div>	
    
</body>
</html>
