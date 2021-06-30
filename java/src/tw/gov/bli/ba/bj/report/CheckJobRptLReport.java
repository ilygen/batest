package tw.gov.bli.ba.bj.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import tw.gov.bli.ba.bj.cases.CheckJobRptCase;
import tw.gov.bli.ba.rpt.report.ExcelReportBase;
import tw.gov.bli.ba.rpt.report.ReportBase;
import tw.gov.bli.ba.util.DateUtility;

public class CheckJobRptLReport extends ExcelReportBase {

    private HSSFSheet tempSheet;
    private HSSFCell tempCell;

    public CheckJobRptLReport() throws Exception {
        super("CheckJobRptL.xls");
        sheetName = "sheet2";
    }
    
    public ByteArrayOutputStream doReport(List<CheckJobRptCase> dataListForRv1, List<CheckJobRptCase> dataListForRv2, HashMap<String, String> map) throws Exception {
        if(dataListForRv1.size() > 0){
        	printRv1(dataListForRv1, map);
        }
        if(dataListForRv2.size() > 0){
        	printRv2(dataListForRv2, map);
        }

        workbook.getSheetAt(0).setSelected(true);
        workbook.write(bao);
        return bao;
    }

    public void printRv1Head(CheckJobRptCase caseData, HashMap<String, String> map) {
        tempSheet = workbook.getSheetAt(0);
        HSSFSheet sheet = workbook.getSheetAt(0);

        String issuYm = map.get("issuYm");

        HSSFRow row = sheet.getRow(1);

        // 給付別
        tempCell = tempSheet.getRow(1).getCell((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        setCell(cell, tempCell.getCellStyle(), "給付別：老年年金");
        
        // 核定年月
        tempCell = tempSheet.getRow(1).getCell((short) 1);
        cell = row.createCell((short) 1);
        setCell(cell, tempCell.getCellStyle(), "核定年月：" + issuYm);

        // 印表日期
        tempCell = tempSheet.getRow(1).getCell((short) 3);
        cell = row.createCell((short) 3);
        setCell(cell, tempCell.getCellStyle(), "印表日期：" + StringUtils.trimToEmpty(DateUtility.getNowChineseDate()));
    }
    
    public void printRv2Head(CheckJobRptCase caseData, HashMap<String, String> map) {
        tempSheet = workbook.getSheetAt(1);
        HSSFSheet sheet = workbook.getSheetAt(1);

        String issuYm = map.get("issuYm");
        
        HSSFRow row = sheet.getRow(1);

        // 保險證號
        tempCell = tempSheet.getRow(1).getCell((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        setCell(cell, tempCell.getCellStyle(), "給付別：老年年金");
        
        // 核定年月
        tempCell = tempSheet.getRow(1).getCell((short) 1);
        cell = row.createCell((short) 1);
        setCell(cell, tempCell.getCellStyle(), "核定年月：" + issuYm);

        // 申請補助年度
        tempCell = tempSheet.getRow(1).getCell((short) 3);
        cell = row.createCell((short) 3);
        setCell(cell, tempCell.getCellStyle(), "印表日期：" + StringUtils.trimToEmpty(DateUtility.getNowChineseDate()));
    }

    public void printRv1(List<CheckJobRptCase> detailList, HashMap<String, String> map) throws Exception {
    	
    	tempSheet = workbook.getSheetAt(0);

        HSSFSheet sheet = workbook.getSheetAt(0);

        // head
        printRv1Head(detailList.get(0), map);

        HSSFRow row = sheet.getRow(2);
        row = incrementRow(sheet, row);
        HSSFCell cell;

        // 列印資料內容
        for (int i = 0; i < detailList.size(); i++) {
        	CheckJobRptCase detailCase = (CheckJobRptCase) detailList.get(i);

            // 受理編號
            tempCell = tempSheet.getRow(3).getCell((short) 0);
            cell = row.createCell((short) 0);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(detailCase.getApNo()));
            
            // 月核案件類別
            tempCell = tempSheet.getRow(3).getCell((short) 1);
            cell = row.createCell((short) 1);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(detailCase.getMchkTypStr()));
            
            // 給付年月
            tempCell = tempSheet.getRow(3).getCell((short) 2);
            cell = row.createCell((short) 2);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(DateUtility.changeWestYearMonthType(detailCase.getPayYm())));
            
            // 展延/減額比率
            tempCell = tempSheet.getRow(3).getCell((short) 3);
            cell = row.createCell((short) 3);
            setCell(cell, tempCell.getCellStyle(), detailCase.getOldRate());
            
            // 總核定金額
            tempCell = tempSheet.getRow(3).getCell((short) 4);
            cell = row.createCell((short) 4);
            setCell(cell, tempCell.getCellStyle(), ReportBase.formatBigDecimalToInteger(detailCase.getBefIssueAmt()));

            row = incrementRow(sheet, row);
        }

    }
    
 public void printRv2(List<CheckJobRptCase> detailList, HashMap<String, String> map) throws Exception {
    	
    	tempSheet = workbook.getSheetAt(1);

    	HSSFSheet sheet = workbook.getSheetAt(1);
    	
        // head
    	printRv2Head(detailList.get(0), map);

        HSSFRow row = sheet.getRow(2);
        row = incrementRow(sheet, row);
        HSSFCell cell;

        // 列印資料內容
        for (int i = 0; i < detailList.size(); i++) {
        	
        	CheckJobRptCase detailCase = (CheckJobRptCase) detailList.get(i);
            
        	 // 受理編號
            tempCell = tempSheet.getRow(3).getCell((short) 0);
            cell = row.createCell((short) 0);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(detailCase.getApNo()));
            
            // 月核案件類別
            tempCell = tempSheet.getRow(3).getCell((short) 1);
            cell = row.createCell((short) 1);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(detailCase.getMchkTypStr()));
            
            // 給付年月
            tempCell = tempSheet.getRow(3).getCell((short) 2);
            cell = row.createCell((short) 2);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(DateUtility.changeWestYearMonthType(detailCase.getPayYm())));
            
            // 展延/減額比率
            tempCell = tempSheet.getRow(3).getCell((short) 3);
            cell = row.createCell((short) 3);
            setCell(cell, tempCell.getCellStyle(), detailCase.getOldRate());
            
            // 總核定金額
            tempCell = tempSheet.getRow(3).getCell((short) 4);
            cell = row.createCell((short) 4);
            setCell(cell, tempCell.getCellStyle(), ReportBase.formatBigDecimalToInteger(detailCase.getBefIssueAmt()));

            row = incrementRow(sheet, row);
        }

    }
    
}
