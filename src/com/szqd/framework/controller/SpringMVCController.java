package com.szqd.framework.controller;


import com.szqd.framework.model.FileEntity;
import com.szqd.framework.model.Resolution;
import com.szqd.framework.security.UserDetailsEntity;
import com.szqd.framework.util.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/common")
public abstract class SpringMVCController extends ApplicationObjectSupport
{

    public SpringMVCController()
    {

    }



    @InitBinder     /* Converts empty strings into null when a form is submitted */
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));



    }

	private static Logger logger = Logger.getLogger(SpringMVCController.class);

    private Boolean actionOccurError = null;

    private String errorInfo = null;

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    private Object dataObject = null;

    public void setDataObject(Object dataObject) {
        this.dataObject = dataObject;
    }

    public void setActionOccurError(Boolean actionOccurError) {
        this.actionOccurError = actionOccurError;
    }

    public Boolean getActionOccurError() {
        if (this.actionOccurError == null)
        {
            this.actionOccurError = new Boolean(false);
        }
        return this.actionOccurError;
    }

    private Map<String,Object> resultData = new HashMap<String, Object>();

    public Map<String, Object> getResultData()
    {
        if (this.getActionOccurError())
        {
            this.resultData.clear();
            this.resultData.put(PageGlobalKeyEnum.STATUS.toString(),PageGlobalKeyEnum.STATUS_ERROR_CODE.toString());
            this.resultData.put(PageGlobalKeyEnum.ERROR_INFO.toString(),this.getErrorInfo());
        }
        else
        {
            this.resultData.put(PageGlobalKeyEnum.STATUS.toString(),PageGlobalKeyEnum.STATUS_SUCCESS_CODE.toString());
            if (this.dataObject != null)
            {
                this.resultData.put(PageGlobalKeyEnum.RESULT_DATA.toString(),this.dataObject);
            }

        }

        return resultData;
    }

    private static final long serialVersionUID = 1L;

    public static final String GLOBAL_ERROR_PAGE = "/error.jsp";


    public UserDetailsEntity getLoginUser()
    {
//        UserDetailsEntity loginUser = (UserDetailsEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return loginUser;
		return null;
    }

    public String getUploadFileURL(HttpServletRequest request,String fileName)
    {
        StringBuilder uploadFileURL = new StringBuilder();
//        uploadFileURL.append(request.getContextPath());
        uploadFileURL.append("/upload/");
        uploadFileURL.append(fileName);
        return uploadFileURL.toString();
    }


	public String getContextRealPath(HttpServletRequest request)
	{
		String contextRealPath = request.getServletContext().getRealPath("/");
		contextRealPath = contextRealPath.replaceAll("\\\\", "/");
		return contextRealPath;
	}

	public String getContextPath(HttpServletRequest request)
	{
		String contextPath = request.getContextPath();
		contextPath = contextPath.replaceAll("\\\\", "/");
		return contextPath;
	}

	public String getBasePath(HttpServletRequest request)
	{

		String contextPath = getContextPath(request);
		String basePath = request.getScheme() + "://"
		+ request.getLocalAddr() + ":" + request.getServerPort()
		+ contextPath + "/";
		return basePath;
	}

    public static final String UPLOAD_DIR = "/file";
    /**
     * 上传图片并转移文件到指定目录
     * @param uploadFile
     * @param request
     * @param subpath
     * @return 返回转移后的相对路径和绝对路径
     */
    public FileEntity saveUploadFile(MultipartFile uploadFile,HttpServletRequest request,String subpath,Boolean needForDimension,Boolean checkForDimension,Float checkWidth,Float checkHeight)
    {

        try
        {
            if ((uploadFile==null))
            {
                logger.debug("上传文件为null");
            }
            String fileID = UUID.randomUUID().toString();
            String originalFilename = uploadFile.getOriginalFilename();
            String fileTypeName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uploadFileName = fileID+fileTypeName;

            String relativeFolderPath = null;

            StringBuilder relativeUploadFilePath = new StringBuilder();
            relativeUploadFilePath.append("/");
            relativeUploadFilePath.append(subpath);
            relativeUploadFilePath.append("/");
            relativeFolderPath = relativeUploadFilePath.toString();
            relativeUploadFilePath.append(uploadFileName);


            String contextRealPath = this.getContextRealPath(request);

            StringBuilder destFileRealPath = new StringBuilder();
            destFileRealPath.append(contextRealPath);
            destFileRealPath.append(relativeUploadFilePath);

            StringBuilder realFolderPath = new StringBuilder();
            realFolderPath.append(contextRealPath);
            realFolderPath.append(relativeFolderPath);

            Resolution resolution = ImageUtil.getImageDimension(uploadFile.getInputStream());
            if (checkForDimension != null && checkForDimension)
            {
                float width = resolution.getWidth();
                float height = resolution.getHeight();
                if (checkWidth != width || checkHeight != height)
                    throw new RuntimeException("上传文件和指定分辨率不匹配");
            }

            File destFile = new File(destFileRealPath.toString());
            FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), destFile);

            FileEntity fileEntity = new FileEntity();

            if (needForDimension != null && needForDimension)
            {
                fileEntity.setResolution(resolution);
            }

            fileEntity.setRealPath(destFileRealPath.toString());
            fileEntity.setRelativePath(relativeUploadFilePath.toString());
            fileEntity.setRelativeFolderPath(relativeFolderPath);
            fileEntity.setRealFolderPath(realFolderPath.toString());
            fileEntity.setFileType(fileTypeName);
            return fileEntity;
        } catch (Exception e)
        {
            logger.debug("com.szqd.framework.controller.SpringMVCController.saveUploadFile(MultipartFile uploadFile,HttpServletRequest request)",e);
            throw new RuntimeException("上传文件失败");
        }
    }

    @RequestMapping(value = "/uploadFile.do",method = RequestMethod.POST)
    public FileEntity uploadFile(MultipartFile uploadFile,HttpServletRequest request,String subpath,Boolean needForDimension,Boolean checkForDimension,Float checkWidth,Float checkHeight)
    {
        FileEntity fileEntity = this.saveUploadFile(uploadFile,request,subpath,needForDimension,checkForDimension,checkWidth,checkHeight);
        return fileEntity;
    }

    /**
     * 获取地址
     * @param request
     * @return
     */
    @RequestMapping(value = "/get-hosts.do")
    public Map<String,String> getHosts(HttpServletRequest request)
    {
        String contextPath = this.getContextPath(request);

        Map<String,String> hostsMap = new HashMap<>();
        hostsMap.put("pushMessage",new StringBuilder("http://app.szqd.com").append(contextPath).toString());
        hostsMap.put("normal",new StringBuilder("http://app.szqd.com").append(contextPath).toString());

        return hostsMap;
    }


    @RequestMapping(value = "/forward-url/{action}/{beanID}/{key}.do")
    public ModelAndView forwardURL(@PathVariable("action") String action,@PathVariable("beanID") String beanID ,@PathVariable("key") String key)
    {
        /**
         * 等待修改
         */

//        ApplicationContext context = this.getApplicationContext();
//        ForwardURLService forwardURLService = (ForwardURLService)context.getBean(beanID);
//        Map<String,Object> param = new HashMap<>();
//        param.put("key",key);
//        forwardURLService.executeService(param);
        ModelAndView modelAndView = new ModelAndView();
        action = action.replaceAll("\\*","/"); //StringUtil.simpleDecodeForString(action);
        StringBuilder forwardURLBuilder = new StringBuilder();
        forwardURLBuilder.append("redirect:");
        forwardURLBuilder.append(action);
        modelAndView.setViewName(forwardURLBuilder.toString());
        return modelAndView;
    }



}