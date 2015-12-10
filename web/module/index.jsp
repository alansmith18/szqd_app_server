<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>平台管理</title>
<link rel="stylesheet" type="text/css" charset="utf-8" href="${pageContext.request.contextPath}/resource/css/styles.css">
<link href="${pageContext.request.contextPath}/resource/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/layout.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/calendar.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/jquery-ui-1.8.18.custom.css" rel="stylesheet" type="text/css" charset="utf-8">
<script src="${pageContext.request.contextPath}/resource/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/back_to_top.js"></script>
<script type="text/javascript">

    function clickMenuWithUrl(url)
    {
        $("#mainFrame").attr("src",url);

    }

    $(document).ready(function(){
        $(document).bind("contextmenu",function(e){
            return false;
        });


        $('dl').css('cursor', 'pointer')


    });


    $(function(){
	var nav_offset = $('#sub_nav').offset().top;
	$(window).scroll(function(){
		$('#sub_nav').toggleClass('float', $(this).scrollTop()>nav_offset);
	}).resize(function(){$(this).scroll()}).scroll();
	$('#mainFrame').height($('#sub_nav').height());
	//
	$(".close_dialog").live('click', function(){
		closeDialog();
	});
	$('.dialog_box img.form_action').live('click', function(){
		$(this).toggleClass('more_down').parent().next().toggle();
	});
});

function fixMainFrame(){
	$('.dialog_box').dialog('destroy').remove();
	$('#tags_container').html($('#mainFrame').contents().find('#tags_list').clone().show());
	$('#mainFrame').contents().find('#dialogs').children().clone().addClass('dialog_box').appendTo('body').dialog({autoOpen:false, modal:true, resizable:false, width:700});
//    var height = $('#mainFrame').contents().find('body').height();
//    alert(height)
	$('#mainFrame').height($(window).height());
	$(window).resize();
//    document.getElementById("mainFrame").style.height =
//    document.getElementById("mainFrame").contentWindow.document.body.offsetHeight + 'px';
}

function openDialog(id){
	$(id).dialog("open");
}
function closeDialog(){
	$('.dialog_box').dialog("close");
}

</script>
</head>
<body style="background-color:#f8fafd;">
<div id="wrap" style="position:relative"> 
  <!--Header-->
  <div id="header"> 
    <!--LOGO-->
    <ul>
      <%--<li id="logo"><a href="#"><img src="${pageContext.request.contextPath}/resource/images/header/logo.png" alt="chinamobilelogo"></a></li>--%>
      <li id="logo_header"><img src="${pageContext.request.contextPath}/resource/images/header/logo_header.png" alt="ISDlogo"></li>
      <li id="company_title">
        <p> <span class="company_title1">平台管理</span></p>
      </li>
    </ul>
      <!--友好导航-->
      <div class="header_menu">
          <ul>
              <li>您好，<security:authentication property="name" /></li>
              <li><a href="#">帮助</a>|<a href="${pageContext.request.contextPath}/logout.do">退出</a></li>
          </ul>
          <span class="support_tel">支持电话：110&nbsp;&nbsp;支持邮箱：like@szqd.com</span> </div>
      <!--友好导航 end-->
  </div>

    <!--Navigation-->
      <div id="main_nav">
          <div class="mian_nav_col">
              <ul>
                  <li>
                      <%--<a href="index.html"  class="current">首页</a>--%>
                  </li>
                  <li>
                      <%--<a href="查询与统计02.html" target="_parent" >查询与统计</a>--%>
                  </li>
                  <li>
                      <%--<a href="系统管理.html" target="_parent" >系统管理</a>--%>
                  </li>
              </ul>
              <div class="clear"></div>
          </div>
          <div class="search"> </div>
      </div>
      <!--Navigation end-->

      <!--Content-->
  <div id="content"> 
    
    <!--left_content-->
      <div id="sub_nav">
        <div class="left_content nav_color" >
            <div id="box_left">
                <div class="left_nav ">

                    <div class="border">
                    
                        <div   style="margin-top:10px" class="nav_title">功能列表</div>

                            <c:forEach items="${menuList}" var="menu">
                            <dl>
                                <dt class=" ">
                                    <a class="title"  onclick="clickMenuWithUrl('${pageContext.request.contextPath}${menu.menuURL}')" >
                                        <img width="24" height="24"  src="${pageContext.request.contextPath}${menu.menuImage}"/>${menu.menuName}
                                    </a>
                                </dt>
                            </dl>
                            </c:forEach>
                    </div>

                </div>
            </div>
        </div>
      </div>
      <!--left_content  end-->
      <%--class="right_content"--%>
    <!--right_content-->
    <div >
      <iframe id="mainFrame" class="right_content" name="mainFrame" width="100%"  height="100%" frameborder="0" scrolling="yes"  onload="fixMainFrame();"></iframe>
    </div>
    <!--right_content end-->
    <div class="clear"></div>
    
    <!--footer-->
    <div id="footer_v1">
      <div class="footer_logo_v1"></div>
      <div class="footer_info_v1">
        <div class="footer_link_v1">重庆神指奇动有限公司</div>
        <div class="footer_link_v2">|</div>
        <div class="footer_link_v3">平台管理</div>
      </div>
    </div>
    <!--footer  end--> 
  </div>
  <!--Content end--> 
</div>
<div id="tags_container"> </div>
</body>
</html>
