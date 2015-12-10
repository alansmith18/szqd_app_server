package com.szqd.project.mobile.lock.controller;

import com.szqd.framework.controller.JQueryValidateRemoteValue;
import com.szqd.framework.controller.SpringMVCController;
import com.szqd.framework.model.FileEntity;
import com.szqd.framework.model.Pager;
import com.szqd.framework.model.Resolution;
import com.szqd.framework.model.Scale;
import com.szqd.project.mobile.lock.model.WallpaperEntity;
import com.szqd.project.mobile.lock.model.WallpaperTypeEntity;
import com.szqd.project.mobile.lock.service.WallpaperService;
import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by like on 4/15/15.
 */
@RestController
@RequestMapping("/wallpaper")
public class WallpaperController extends SpringMVCController
{
    private static final Logger logger = Logger.getLogger(WallpaperController.class);

    private WallpaperService wallpaperService = null;

    public WallpaperService getWallpaperService() {
        return wallpaperService;
    }

    public void setWallpaperService(WallpaperService wallpaperService) {
        this.wallpaperService = wallpaperService;
    }

    private static final String LIST_WALL_PAPER_TYPE_DO = "/wallpaper/list-wallpapertype.do";
    @RequestMapping(value = "/add-wallpaper-category.do",method = RequestMethod.GET)
    public ModelAndView saveWallpaperCategory(WallpaperTypeEntity wallpaperTypeEntity)
    {
        ModelAndView modelAndView = new ModelAndView(LIST_WALL_PAPER_TYPE_DO);
        this.wallpaperService.addWallpaperType(wallpaperTypeEntity);
        return modelAndView;
    }

    @RequestMapping(value = "/check-duplicate-wallpapercategory.do",method = RequestMethod.GET)
    public JQueryValidateRemoteValue isDuplicateWallpaperCategoryName(WallpaperTypeEntity wallpaperType)
    {
        boolean result = this.wallpaperService.checkDuplicatePaperType(wallpaperType);
        JQueryValidateRemoteValue remote = new JQueryValidateRemoteValue();
        remote.setValue(result);
        return remote;

    }

    private static final String WALLPAPER_TYPE_ADD_PAGE = "/module/wallpaper/wallpapertype-add.jsp";
    @RequestMapping(value = "/list-wallpapertype.do", method = RequestMethod.GET)
    public ModelAndView listWallpaperType()
    {
        ModelAndView modelAndView = new ModelAndView(WALLPAPER_TYPE_ADD_PAGE);
        List<WallpaperTypeEntity> allWallpaperType = this.wallpaperService.listAllWallpaperType();
        Map param = new HashMap();
        param.put("allWallpaperType", allWallpaperType);
        modelAndView.addAllObjects(param);

        return modelAndView;
    }

    @RequestMapping(value = "/list-wallpapertype-json.do",method = RequestMethod.GET)
    public List<WallpaperTypeEntity> listWallpaperTypeForJsonFormat()
    {
        List<WallpaperTypeEntity> allWallpaperType = this.wallpaperService.listAllWallpaperType();
        return allWallpaperType;
    }

    @RequestMapping(value = "/list-wallpaper-bytype-json.do",method = RequestMethod.GET)
    public List<WallpaperEntity> listWallpaperByTypeForJsonFormat(Integer type,Pager pager)
    {
        List<WallpaperEntity> wallpaperEntityList = this.wallpaperService.listWallpaperWithPageByType(type, pager);
        return wallpaperEntityList;
    }

    private static final String LIST_WALLPAPER_BYTYPE_PAGE = "/module/wallpaper/wallpaper-add.jsp";
    @RequestMapping(value = "/list-wallpaper-bytype.do",method = RequestMethod.GET)
    public ModelAndView listWallpaperByType(Integer type)
    {
        List<WallpaperEntity> wallpaperEntityList = this.wallpaperService.listWallpaperByType(type);
        ModelAndView modelAndView = new ModelAndView();
        Map param = new HashMap();
        param.put("wallpaperList",wallpaperEntityList);
        param.put("paperType", type);
        modelAndView.addAllObjects(param);
        modelAndView.setViewName(LIST_WALLPAPER_BYTYPE_PAGE);
        return modelAndView;
    }


    private static final String SUBPATH_FOR_WALLPAPER = "wallpaper";
    @RequestMapping(value = "/upload-wallpaper.do",method = RequestMethod.POST)
    public Map uploadFileForWallPaper(MultipartFile uploadFile,float scale,HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView();
        FileEntity sourceFileEntity = this.saveUploadFile(uploadFile,request,SUBPATH_FOR_WALLPAPER,null,null,null,null);
        List<Resolution> resolutionList = this.wallpaperService.generateResoulutionList(scale);
        List<FileEntity> fileEntityList = this.wallpaperService.scaleImage(sourceFileEntity, resolutionList);

        Resolution sourceResolution = new Resolution();
        if (scale == Scale.SCALE16$9.getValue())
        {
            sourceResolution.setKey(1920.108f);
            sourceResolution.setWidth(1920);
            sourceResolution.setHeight(1080);
        }
        else if (scale == Scale.SCALE4$3.getValue())
        {
            sourceResolution.setKey(800.6f);
            sourceResolution.setWidth(800);
            sourceResolution.setHeight(600);
        }
        else if (scale == Scale.SCALE5$3.getValue())
        {
            sourceResolution.setKey(800.48f);
            sourceResolution.setWidth(800);
            sourceResolution.setHeight(480);
        }
        sourceFileEntity.setResolution(sourceResolution);
        fileEntityList.add(sourceFileEntity);
        Map param = new HashMap();
        param.put("scalePhotos", fileEntityList);
        return param;
    }

    private static final String LIST_WALLPAPER_BY_TYPE_DO = "redirect:/wallpaper/list-wallpaper-bytype.do?type=%d";
    @RequestMapping(value = "/save-wallpaper.do",method = RequestMethod.POST)
    public ModelAndView saveWallpaper(@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") Date effectiveTime,String wallpaperUpload4$3, String wallpaperUpload5$3, String wallpaperUpload16$9,Long paperType)
    {
        ModelAndView modelAndView = new ModelAndView();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(sf.format(effectiveTime));

        this.wallpaperService.addWallpaper(effectiveTime.getTime(),wallpaperUpload4$3, wallpaperUpload5$3, wallpaperUpload16$9, paperType);
        String forwardPage = String.format(LIST_WALLPAPER_BY_TYPE_DO,paperType);
        modelAndView.setViewName(forwardPage);

        return modelAndView;
    }

    @RequestMapping(value = "/del-wallpaper.do" , method = RequestMethod.GET)
    public void deleteWallpaper(Integer wallpaperID,HttpServletRequest request)
    {
        this.wallpaperService.deleteWallpaperByID(wallpaperID, this.getContextRealPath(request));
    }

    @RequestMapping(value = "/download-sourcepaper-pic.do", method = RequestMethod.GET,produces = "application/octet-stream")
    public ModelAndView downloadPic(Integer wallpaperID, float scale)
    {
        ModelAndView modelAndView = new ModelAndView();
        WallpaperEntity wallpaper = this.wallpaperService.findWallpaperByID(wallpaperID);
        String picAddr = null;
        if (8.6f == scale)//800x600
        {
            picAddr = wallpaper.getUrlSvga();
        }
        else if (8.48f == scale) //800x480
        {
            picAddr = wallpaper.getUrlWvga();
        }
        else if (192.108f == scale)//1920x1080
        {
            picAddr = wallpaper.getUrl1080p();
        }
        logger.debug("picAddr:"+picAddr);
        modelAndView.setViewName(picAddr);
        return modelAndView;
    }


    @RequestMapping(value = "/send-wallpaper-toclient.do",method = RequestMethod.GET)
    public List<WallpaperEntity> findWallpaperByPage(Pager pager,int orderType,WallpaperEntity queryCondition)
    {
        List<WallpaperEntity> wallpaperList = this.wallpaperService.findWallpaperByPager(pager,orderType,queryCondition);
        return wallpaperList;
    }


    @RequestMapping(value = "/update-downloadcount.do",method = RequestMethod.GET)
    public void updateDownloadCount(Integer wallPaperID)
    {
        this.wallpaperService.updateWallpaperDownloadCount(wallPaperID);
    }


    @RequestMapping(value = "/kind-of-papercount.do",method = RequestMethod.GET)
    public Map findKindOfPaperCount()
    {
        int totalCount = 0;
        List<HashMap> resultList = this.wallpaperService.queryKindOfWallpaperCount();
        for (int i = 0; i < resultList.size() ; i++)
        {
            Map map = resultList.get(i);
            Integer paperCount = (Integer)map.get("paperCount");
            totalCount += paperCount;
        }
        HashMap totalMap = new HashMap();
        totalMap.put("totalCount", totalCount);
        totalMap.put("kindOfCount", resultList);

        return totalMap;
    }



    private static final String LIST_ALL_WALLPAPERTYPE_DO = "/wallpaper/list-wallpapertype.do";
    @RequestMapping(value = "/edit-wallpaper-category.do",method = RequestMethod.GET)
    public ModelAndView updateCategoryName(WallpaperTypeEntity wallpaperType)
    {

        this.wallpaperService.updateWallPaperCategoryName(wallpaperType);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(LIST_ALL_WALLPAPERTYPE_DO);
        return modelAndView;
    }


    @RequestMapping(value = "/edit-wallpaper-downloadcount.do",method = RequestMethod.GET)
    public ModelAndView updateWallpaperDownloadCount(WallpaperEntity wallpaper)
    {
        this.wallpaperService.updateWallpaperDownloadCount(wallpaper);
        ModelAndView modelAndView = new ModelAndView();
        String forwardPage = String.format(LIST_WALLPAPER_BY_TYPE_DO,wallpaper.getPagerType());
        modelAndView.setViewName(forwardPage);
        return modelAndView;
    }
}

