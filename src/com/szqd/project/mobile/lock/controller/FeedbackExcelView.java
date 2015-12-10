package com.szqd.project.mobile.lock.controller;

import com.szqd.project.common.model.FeedbackPojo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by like on 6/30/15.
 */
public class FeedbackExcelView extends AbstractExcelView
{
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HSSFSheet sheet = workbook.createSheet("sheet");
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
        }
    }
}
