package com.szqd.project.common.controller;

import com.szqd.framework.controller.SpringMVCController;
import com.szqd.framework.model.Pager;
import com.szqd.framework.model.SelectEntity;
import com.szqd.project.common.model.FeedbackEntity;
import com.szqd.project.common.model.FeedbackPojo;
import com.szqd.project.common.service.FeedbackService;
import com.szqd.project.popularize.analysis.service.PopularizeAnalysisService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by like on 4/20/15.
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController extends SpringMVCController {
    private static final Logger LOGGER = Logger.getLogger(FeedbackController.class);

    private FeedbackService feedbackService = null;

    private PopularizeAnalysisService popularizeAnalysisService = null;


    @RequestMapping(value = "/add-feedback.do", method = RequestMethod.POST)
    public void saveFeedback(FeedbackEntity feedback) {
        this.feedbackService.saveFeedback(feedback);
    }

    private static final String FEED_BACK_PAGE = "/module/feedback/feedback-list.jsp";

    @RequestMapping(value = "/find-feedback-pager.do", method = RequestMethod.GET)
    public ModelAndView findFeedbackListWithPager(Pager pager, FeedbackEntity queryCondition, String beginDate, String endDate) {
        ModelAndView modelAndView = new ModelAndView();
        Map param = new HashMap();
        List<FeedbackPojo> list = this.feedbackService.findFeedbackListByPage(pager, queryCondition, beginDate, endDate);
        int querySize = list.size();
        int pageSize = pager.getCapacity();
        boolean hasNextPage = querySize == pageSize;


        List platformList = new ArrayList();
        SelectEntity selectIOS = new SelectEntity();
        selectIOS.setText("IOS");
        selectIOS.setValue("1");
        platformList.add(selectIOS);

        SelectEntity selectAndroid = new SelectEntity();
        selectAndroid.setText("ANDROID");
        selectAndroid.setValue("0");
        platformList.add(selectAndroid);


        param.put("platformList", platformList);
        param.put("feedbackList", list);
        param.put("hasNextPage", hasNextPage);
        param.put("pager", pager);
        param.put("queryCondition", queryCondition);
        param.put("beginDate", beginDate);
        param.put("endDate", endDate);


        modelAndView.addAllObjects(param);
        modelAndView.setViewName(FEED_BACK_PAGE);
        return modelAndView;
    }

    @RequestMapping(value = "/generate-feedback-excel.do")
    public ModelAndView generateExcel(FeedbackEntity queryCondition,String beginDate,String endDate)
    {


        String appName = null;
        if (queryCondition != null && queryCondition.getAppName()!= null) {
            appName = queryCondition.getAppName().trim();
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date begin = sf.parse(beginDate);
            Date end = sf.parse(endDate);
            long time = end.getTime() - begin.getTime();
            long day = time/1000/60/60/24;
            if (day > 3l || appName == null || "".equals(appName))
            {
                return new ModelAndView(new AbstractExcelView() {
                    @Override
                    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

                    }
                }, "list", new ArrayList<>());
            }

        } catch (ParseException e) {
            LOGGER.error("com.szqd.project.common.controller.FeedbackController.generateExcel(FeedbackEntity queryCondition,String beginDate,String endDate)",e);
        }

        List<FeedbackPojo> list = this.feedbackService.findFeedbackListByPage(null, queryCondition, beginDate, endDate);


        AbstractExcelView excelView = new AbstractExcelView() {
            @Override
            protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

                String beginDate = (String)model.get("beginDate");
                String endDate = (String)model.get("endDate");


                response.setHeader("Content-Disposition", "attachment; filename=\""+beginDate+"-"+endDate+".xls\"");

                HSSFSheet sheet = workbook.createSheet("sheet");
                sheet.setDefaultColumnWidth(20);
//                sheet.setDefaultRowHeight((short)15);

                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell title0 = titleRow.createCell(0);
                title0.setCellValue("反馈日期");
                HSSFCell title1 = titleRow.createCell(1);
                title1.setCellValue("app版本");
                HSSFCell title2 = titleRow.createCell(2);
                title2.setCellValue("系统版本");
                HSSFCell title3 = titleRow.createCell(3);
                title3.setCellValue("机器型号");
                HSSFCell title4 = titleRow.createCell(4);
                title4.setCellValue("联系方式");
                HSSFCell title5 = titleRow.createCell(5);
                title5.setCellValue("反馈意见");
                HSSFCell title6 = titleRow.createCell(6);
                title6.setCellValue("性别");
                HSSFCell title7 = titleRow.createCell(7);
                title7.setCellValue("年龄");

                List<FeedbackPojo> list = (List<FeedbackPojo>)model.get("list");
                for (int i = 0; i < list.size(); i++)
                {
                    FeedbackPojo pojo = list.get(i);
                    int rowIndex = i+1;
                    HSSFRow dataRow = sheet.createRow(rowIndex);
                    HSSFCell cell0 = dataRow.createCell(0);
                    cell0.setCellValue(pojo.getFeedbackTimeText());
                    HSSFCell cell1 = dataRow.createCell(1);
                    cell1.setCellValue(pojo.getAppVersion());
                    HSSFCell cell2 = dataRow.createCell(2);
                    cell2.setCellValue(pojo.getOsVerion());
                    HSSFCell cell3 = dataRow.createCell(3);
                    cell3.setCellValue(pojo.getPhoneModel());
                    HSSFCell cell4 = dataRow.createCell(4);
                    cell4.setCellValue(pojo.getContactInformation());
                    HSSFCell cell5 = dataRow.createCell(5);
                    cell5.setCellValue(pojo.getMessage());

                    HSSFCell cell6 = dataRow.createCell(6);
                    cell6.setCellValue(pojo.getUserSexText());
                    HSSFCell cell7 = dataRow.createCell(7);
                    cell7.setCellValue(pojo.getUserAgeText());





                }
            }
        };

        HashMap map = new HashMap();
        map.put("list",list);
        map.put("beginDate",beginDate);
        map.put("endDate",endDate);

        return new ModelAndView(excelView,map);
    }

    @RequestMapping(value = "/add-uninstall-count.do")
    public void addUninstallCount(String appName)
    {
        this.feedbackService.addUninstallTodayCount(appName);
    }

    private static final String UNINSTALL_LIST_PAGE = "/module/feedback/uninstall-list.jsp";
    @RequestMapping(value = "/uninstall-list.do")
    public ModelAndView fetchUninstallCount(String appName,String beginDate,String endDate)
    {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Long beginDateLong = null,endDateLong = null;
        try
        {
            if (beginDate != null)
            {
                beginDateLong = sf.parse(beginDate).getTime();
            }

            if (endDate != null)
            {
                endDateLong = sf.parse(endDate).getTime();
            }
        } catch (ParseException e) {
            LOGGER.error("com.szqd.project.common.controller.FeedbackController.fetchUninstallCount(String appName,String beginDate,String endDate)",e);
            throw new RuntimeException("查询时间条件出错");
        }
        List<Map> resultList = this.feedbackService.fetchUninstall(appName,beginDateLong,endDateLong);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("resultList",resultList);
        modelAndView.addObject("appName",appName);
        modelAndView.addObject("beginDate",beginDate);
        modelAndView.addObject("endDate",endDate);
        modelAndView.setViewName(UNINSTALL_LIST_PAGE);

        return modelAndView;
    }

    public FeedbackService getFeedbackService() {
        return feedbackService;
    }

    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    public PopularizeAnalysisService getPopularizeAnalysisService() {
        return popularizeAnalysisService;
    }

    public void setPopularizeAnalysisService(PopularizeAnalysisService popularizeAnalysisService) {
        this.popularizeAnalysisService = popularizeAnalysisService;
    }
}
