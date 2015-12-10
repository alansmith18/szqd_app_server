<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>壁纸类别</title>
<link rel="stylesheet" type="text/css" charset="utf-8" href="${pageContext.request.contextPath}/resource/css/styles.css">
<link href="${pageContext.request.contextPath}/resource/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/layout.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/common.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resource/css/calendar.css" rel="stylesheet" type="text/css">

<script src="${pageContext.request.contextPath}/resource/js/jquery-1.7.2.min.js" type="text/javascript"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.ztree.all-3.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/back_to_top.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery.validate.js"></script>

<link href="${pageContext.request.contextPath}/resource/js/jquery-ui-1.11.4/jquery-ui.min.css" rel="stylesheet" type="text/css" charset="utf-8">
    <link href="${pageContext.request.contextPath}/resource/js/jquery-ui-1.11.4/jquery-ui.theme.min.css" rel="stylesheet" type="text/css" charset="utf-8">

    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui-1.11.4/jquery-ui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui-contextmenu-master/jquery.ui-contextmenu.min.js"></script>

    <script>

        $(document).ready(
                function ()
                {
                    var validator = $("#addCategoryFormID").validate
                    (
                            {
                                submitHandler: function(form)
                                {
                                    form.submit();
                                }

                            }

                    );

                    var validator2 = $("#editCategoryFormID").validate
                    (
                            {
                                submitHandler: function(form)
                                {
                                    form.submit();
                                }

                            }

                    );


                    $("#wallpaperListID").contextmenu({
                        delegate: ".hasmenu",
                        menu: [
                            {
                                title: "编辑",
                                uiIcon: "ui-icon-closethick",
                                action: function(event, ui)
                                {

                                    var wallpaperID = ui.target.attr("typeID");
                                    var wallpaperName = ui.target.attr("typeName");

                                    editCategory(wallpaperID,wallpaperName)
                                }
                            }

                        ]
//                        ,
//                        select: function(event, ui) {
//                            alert("select " + ui.target.text() + " on " );
//                        }
                    });

                }
        );

        function editCategory(wallpaperID,wallpaperName)
        {
            $("#categoryIDEditID").val(wallpaperID);
            $("#categoryNameEditID").val(wallpaperName);

            dialog = $("#edit-form").dialog({
//                height: 300,
//                width: 350,
                modal: true
                ,
                buttons:
                {

                    "保存": submitEditCategory,
                    "取消": function() {

                        dialog.dialog( "close" );

                    }
                }
                ,
                close: function() {

                }
            });
        }

        function clearForm()
        {
            $("#categoryNameID").attr("value","");
        }

        function addCategory()
        {
            $("#addCategoryFormID").submit();
        }

        function submitEditCategory()
        {
            $("#editCategoryFormID").submit();
        }

        function showAddCategory()
        {
            dialog = $("#dialog-form").dialog({
//                height: 300,
//                width: 350,
                modal: true
                ,
                buttons:
                {

                    "保存": addCategory,
                    "取消": function() {

                        dialog.dialog( "close" );
                        clearForm();
                    }
                }
                ,
                close: function() {

                }
            });
        }


    </script>
</head>
<body>

    <!--面包屑-->

    <div class="right_content_pagePath">您现在所在的位置：壁纸类别</div>
    <!--面包屑  end-->

    <table class="groupPanelTable" width="100%" cellspacing="0">
        <tr>
            <td  colspan="4" class="tab2s">
                <div class="btns" >
                    <button  class="btn_save t-center" onclick="showAddCategory();">添加类别</button>
                </div>
            </td>
        </tr>
    </table>


    <div id="dialog-form" title="创建分类" style="display: none">
        <form id="addCategoryFormID" method="GET" action="${pageContext.request.contextPath}/wallpaper/add-wallpaper-category.do">
            <fieldset>
                <label for="categoryNameID">分类名称:</label>
                <input type="text" class="input w200" title="2到6个字符,且不能重复" name="typeName" id="categoryNameID"
                       remote="${pageContext.request.contextPath}/wallpaper/check-duplicate-wallpapercategory.do"
                       required minlength="2" maxlength="6" >

            </fieldset>
        </form>
    </div>


    <div id="edit-form" title="编辑分类" style="display: none">
        <form id="editCategoryFormID" method="GET" action="${pageContext.request.contextPath}/wallpaper/edit-wallpaper-category.do">
            <fieldset>
                <label for="categoryNameEditID">分类名称:</label>
                <input id="categoryIDEditID" type="hidden" name="id" />
                <input type="text" class="input w200" title="2到6个字符,且不能重复" name="typeName" id="categoryNameEditID"
                       remote="${pageContext.request.contextPath}/wallpaper/check-duplicate-wallpapercategory.do"
                       required minlength="2" maxlength="6" />

            </fieldset>
        </form>
    </div>

    <div id="wallpaperListID" style="float: left">
        <c:forEach items="${allWallpaperType}" var="pt">
            <a  class="hasmenu"  href="${pageContext.request.contextPath}/wallpaper/list-wallpaper-bytype.do?type=${pt.id}"><img typeID="${pt.id}" typeName="${pt.typeName}" src="${pageContext.request.contextPath}/resource/images/szqd/folder.ico" />${pt.typeName}</a>
        </c:forEach>
    </div>



</body>
</html>
