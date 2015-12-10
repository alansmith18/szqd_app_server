
 domain = "http://121.42.47.71:8888";

function uploadWallpaperWithCustomParam(uploadButtonID,resultID,contextPath,paramMap,callbackFunction)
{

    $(uploadButtonID).uploadify({
        'buttonText' : '上传',
        'formData'      : paramMap,
        'fileObjName' : 'uploadFile',
        'auto'     : true,
        'multi'    : false,
        'fileTypeExts' : '*.jpeg; *.jpg; *.png',
        'swf'      : contextPath+'/resource/js/uploadify/uploadify.swf',
        'uploader' : domain+contextPath+'/wallpaper/upload-wallpaper.do',
        'onUploadSuccess' : function(file, data, response)
        {
            //var jsonData = jQuery.parseJSON(data);
            callbackFunction(resultID,data);
        },
        'onUploadError' : function(file, errorCode, errorMsg, errorString) {
            alert(file.name+"上传失败, errorCode:"+errorCode+" errorMsg:"+errorMsg);

        }
    });

}

function uploadThemeWithCustomParam(uploadButtonID,resultID,contextPath,paramMap,callbackFunction)
{
    $(uploadButtonID).uploadify({
        'buttonText' : '上传',
        'formData'      : paramMap,
        'fileObjName' : 'uploadFile',
        'auto'     : true,
        'multi'    : false,
        'fileTypeExts' : '*.zip; *.jpeg; *.jpg; *.png',
        'swf'      : contextPath+'/resource/js/uploadify/uploadify.swf',
        'uploader' : domain+contextPath+'/theme/upload-theme.do',
        'onUploadSuccess' : function(file, data, response)
        {
            var jsonData = jQuery.parseJSON(data);
            callbackFunction(resultID,jsonData);

        },
        'onUploadError' : function(file, errorCode, errorMsg, errorString) {
            alert(file.name+"上传失败, errorCode:"+errorCode+" errorMsg:"+errorMsg);

        }
    });

}

function uploadFileWithCustomParam(url,uploadButtonID,contextPath,paramMap,callbackFunction,callbackParam)
{


    $(uploadButtonID).uploadify({
        'buttonText' : '上传',
        'formData'      : paramMap,
        'fileObjName' : 'uploadFile',
        'auto'     : true,
        'multi'    : false,
        'fileTypeExts' : '*.zip; *.jpeg; *.jpg; *.png; *.MP3; ',
        'swf'      : contextPath+'/resource/js/uploadify/uploadify.swf',
        'uploader' : domain+contextPath+url,
        //'uploader' : contextPath+url,
        'onUploadSuccess' : function(file, data, response)
        {

            var jsonData = jQuery.parseJSON(data);
            callbackParam["jsonData"] = jsonData;
            callbackFunction(callbackParam);

        },
        'onUploadError' : function(file, errorCode, errorMsg, errorString) {
            alert(file.name+"上传失败, errorCode:"+errorCode+" errorMsg:"+errorMsg);

        }
    });


}

function getFileByURL(url)
{
    window.open(domain+url);
}

function delFile(referencePK,contextPath)
{
    var delURL = domain+contextPath+"/process/del-upload-file.do";
    $.ajax({
        type: "GET",
        url: delURL,
        data: { referencePK: referencePK},
        success: function( data, textStatus, jqXHR )
        {
            $("#"+referencePK).remove();
        },
        error: function( jqXHR, textStatus, errorThrown )
        {
            alert('删除文件失败:'+errorThrown);
        }
    });
}
