package com.szqd.framework.controller;

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
 * Created by like on 6/29/15.
 */
public class BasicExcelView extends AbstractExcelView
{
    public static final String SHEET_KEY = "SHEET_NAME_KEY";
    public static final String ROW_DATA = "ROW_DATA_KEY";

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sheetKey = (String)model.get(SHEET_KEY);
        List<Map<String,Object>> rowsData = (List<Map<String,Object>>)model.get(ROW_DATA);
        HSSFSheet sheet = workbook.createSheet(sheetKey);
        for (int i = 0; i < rowsData.size(); i++) {
            Map<String, Object> rowData =  rowsData.get(i);
            HSSFRow row = sheet.createRow(i);
            int cellIndex = 1;
            for (Map.Entry<String, Object> rowDataVar : rowData.entrySet())
            {

                String key = rowDataVar.getKey();
                String value = (String)rowDataVar.getValue();
                HSSFCell cell = row.createCell(cellIndex);
                cell.setCellValue(value);
                cellIndex++;
            }
        }
    }
}
