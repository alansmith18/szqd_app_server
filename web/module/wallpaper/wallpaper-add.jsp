<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>添加壁纸</title>
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

    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/My97DatePicker/WdatePicker.js"></script>

    <script>

        $(document).ready(
                function ()
                {


                    var validator = $("#addWallpaperFormID").validate
                    (
                            {
                                submitHandler: function(form)
                                {
                                    form.submit();
                                },
                                ignore: []
                            }


                    );


                    var paramMap = {};

                    paramMap['scale']=4.3;
                    uploadWallpaperWithCustomParam("#wallpaperUpload4_3ID","#wallpaperUpload4_3ResultID","${pageContext.request.contextPath}",paramMap,callbackFunctionForUploadWallpaper);
                    paramMap['scale']=5.3;
                    uploadWallpaperWithCustomParam("#wallpaperUpload5_3ID","#wallpaperUpload5_3ResultID","${pageContext.request.contextPath}",paramMap,callbackFunctionForUploadWallpaper);
                    paramMap['scale']=16.9;
                    uploadWallpaperWithCustomParam("#wallpaperUpload16_9ID","#wallpaperUpload16_9ResultID","${pageContext.request.contextPath}",paramMap,callbackFunctionForUploadWallpaper);


                    $("#wallpaperListID").contextmenu({
                        delegate: ".hasmenu",
                        menu: [
                            {
                                title: "删除",
                                uiIcon: "ui-icon-closethick",
                                action: function(event, ui)
                                {
                                    var wallpaperID = ui.target.attr("wallpaperID");
                                    deleteWallpaperByID(wallpaperID,ui.target);
                                }
                            }
                            ,
                            {title: "----"}
                            ,
                            {
                                title: "编辑",
                                uiIcon: "ui-icon-closethick",
                                action: function(event, ui)
                                {

                                    var wallpaperID = ui.target.attr("wallpaperID");
                                    var downloadCount = ui.target.attr("downloadCount");
                                    var paperType = ui.target.attr("paperType");

                                    editWallPaperCount(wallpaperID,downloadCount,paperType)
                                }
                            }
                            ,
                            {title: "----"}
                            ,
                            {
                                title: "下载", children:
                                    [
                                        {
                                            title: "800x600(4:3)",
                                            uiIcon: "ui-icon-circle-arrow-s",
                                            action: function(event, ui)
                                            {

                                                var wallpaperID = ui.target.attr("wallpaperID");
                                                window.open("${pageContext.request.contextPath}/wallpaper/download-sourcepaper-pic.do?scale=8.6&wallpaperID="+wallpaperID);
                                            }
                                        },
                                        {
                                            title: "800x480(5:3)",
                                            uiIcon: "ui-icon-circle-arrow-s",
                                            action: function(event, ui)
                                            {
                                                var wallpaperID = ui.target.attr("wallpaperID");
                                                window.open("${pageContext.request.contextPath}/wallpaper/download-sourcepaper-pic.do?scale=8.48&wallpaperID="+wallpaperID);
                                            }
                                        },
                                        {
                                            title: "1920x1080(16:9)",
                                            uiIcon: "ui-icon-circle-arrow-s",
                                            action: function(event, ui)
                                            {
                                                var wallpaperID = ui.target.attr("wallpaperID");
                                                window.open("${pageContext.request.contextPath}/wallpaper/download-sourcepaper-pic.do?scale=192.108&wallpaperID="+wallpaperID);
                                            }
                                        },
                                    ]
                            }
                        ]
//                        ,
//                        select: function(event, ui) {
//                            alert("select " + ui.target.text() + " on " );
//                        }
                    });
                }
        );


        function editWallPaperCount(wallpaperID,downloadCount,paperType)
        {
            $("#categoryIDEditID").val(wallpaperID);
            $("#categoryNameEditID").val(downloadCount);
            $("#paperTypeID").val(paperType);


            dialog = $("#editCategoryFormID").dialog({
//                height: 300,
//                width: 350,
                modal: true
                ,
                buttons:
                {

                    "保存": submitEditWallpaper,
                    "取消": function() {

                        dialog.dialog( "close" );

                    }
                }
                ,
                close: function() {

                }
            });
        }

        function submitEditWallpaper()
        {
            $("#editCategoryFormID").submit();
        }

        function deleteWallpaperByID(id,ui)
        {
            var paramMap = {};
            paramMap["wallpaperID"]= id;
            $.ajax
            ({
                url: "${pageContext.request.contextPath}/wallpaper/del-wallpaper.do",
                data: paramMap,
//                async: false,
                success: function()
                {
                    ui.remove();
                }
            });
        }

        function callbackFunctionForUploadWallpaper(resultID,callbackData)
        {
            $(resultID).val(callbackData);
        }

        function clearForm()
        {
            $("#addWallpaperFormID")[0].reset();
        }

        function addCategory()
        {
            $("#addWallpaperFormID").submit();
        }

        function showAddWallpaper()
        {
            dialog = $("#dialog-form").dialog({
//                height: 500,
                width: 650,
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

        function showMenu(wid)
        {

        }

    </script>
</head>
<body>

    <!--面包屑-->

    <div class="right_content_pagePath">您现在所在的位置：壁纸列表</div>
    <!--面包屑  end-->

    <table class="groupPanelTable" width="100%" cellspacing="0">
        <tr>
            <td  colspan="4" class="tab2s">
                <div class="btns" >
                    <button  class="btn_save t-center" onclick="showAddWallpaper();">添加壁纸</button>
                </div>
            </td>
        </tr>
    </table>



    <div id="dialog-form" title="创建壁纸" style="display: none">

            <fieldset>
                <form id="addWallpaperFormID" method="POST" action="${pageContext.request.contextPath}/wallpaper/save-wallpaper.do">
                    <label for="wallpaperUpload4_3ID">4:3(800*600)</label>
                    <input type="file"   id="wallpaperUpload4_3ID"  />
                    <input type="hidden"  title="请上传4:3的源图" id="wallpaperUpload4_3ResultID" name="wallpaperUpload4$3" required />

                    <label for="wallpaperUpload5_3ID">5:3(800*480)</label>
                    <input type="file"   id="wallpaperUpload5_3ID" />
                    <input type="hidden" title="请上传5:3的源图" id="wallpaperUpload5_3ResultID" name="wallpaperUpload5$3" required />

                    <label for="wallpaperUpload16_9ID">16:9(1920*1080)</label>
                    <input type="file"   id="wallpaperUpload16_9ID" />
                    <input type="hidden" title="请上传16:9的源图" id="wallpaperUpload16_9ResultID" name="wallpaperUpload16$9" required />

                    <label for="effectiveTimeLabel">选择生效时间:</label>
                    <input id="effectiveTimeLabel" type="text" name="effectiveTime" readonly="readonly" onclick="WdatePicker()" required>

                    <input type="hidden" name="paperType" value="${paperType}" />
                </form>
            </fieldset>

    </div>

    <div id="edit-form" title="编辑壁纸" style="display: none">
        <form id="editCategoryFormID" method="GET" action="${pageContext.request.contextPath}/wallpaper/edit-wallpaper-downloadcount.do">
            <fieldset>
                <label for="categoryNameEditID">下载数量:</label>
                <input id="categoryIDEditID" type="hidden" name="id" />

                <input id="paperTypeID" type="hidden" name="pagerType" />

                <input type="text" class="input w200" title="2到6个字符,且不能重复" name="downloadCount" id="categoryNameEditID" digits="true"
                       required minlength="1" maxlength="6" />


            </fieldset>
        </form>
    </div>

    <div style="float: left" id="wallpaperListID">
        <c:forEach items="${wallpaperList}" var="wp">
            <a href="#"  class="hasmenu"  >
                <img width="90" wallpaperID="${wp.id}" paperType="${paperType}" downloadCount="${wp.downloadCount}" height="150"  src="${pageContext.request.contextPath}${wp.thumbnails}" />
            </a>
        </c:forEach>
        <%--onclick="showMenu('${wp.id}')"--%>
    </div>




</body>
</html>
