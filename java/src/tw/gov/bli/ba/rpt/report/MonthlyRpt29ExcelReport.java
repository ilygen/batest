package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import tw.gov.bli.ba.rpt.cases.MonthlyRpt29Case;
import tw.gov.bli.ba.rpt.report.ExcelReportBase;
import tw.gov.bli.ba.util.DateUtility;

public class MonthlyRpt29ExcelReport extends ExcelReportBase {

    private HSSFSheet tempSheet;
    private HSSFCell tempCell;

    public MonthlyRpt29ExcelReport(String excelTyp) throws Exception {
    	super(excelTyp);
    }
    
    public void printHead(MonthlyRpt29Case caseData, String rptKind, String payCode) {
        tempSheet = workbook.getSheetAt(0);
        HSSFSheet sheet = workbook.getSheetAt(0);
        //HSSFSheet sheet = workbook.cloneSheet(0);
        //copyRows(workbook.getSheetName(1), workbook.getSheetName(2), 0, 2, 0, 10);
        HSSFCell cell;
        HSSFRow row0 = sheet.getRow(0);
        HSSFRow row = sheet.getRow(1);
        
        //
        tempCell = tempSheet.getRow(0).getCell((short) 0);
        cell = row0.createCell((short) 0);
        if(rptKind.equals("1")){
        	if(payCode.equals("K")){
        		setCell(cell, tempCell.getCellStyle(), "勞保失能年金每月補送在學證明案件明細資料");
        	}else{
        		setCell(cell, tempCell.getCellStyle(), "勞保遺屬年金每月補送在學證明案件明細資料");
        	}
        }else{
            if(payCode.equals("K")){
            	setCell(cell, tempCell.getCellStyle(), "勞保失能年金年度補送在學證明案件明細資料");
        	}else{
        		setCell(cell, tempCell.getCellStyle(), "勞保遺屬年金年度補送在學證明案件明細資料");
        	}
        }

        //
        tempCell = tempSheet.getRow(1).getCell((short) 0);
        cell = row.createCell((short) 0);

        // 印表日期
        tempCell = tempSheet.getRow(1).getCell((short) 2);
        cell = row.createCell((short) 2);
        setCell(cell, tempCell.getCellStyle(), "印表日期：" + StringUtils.trimToEmpty(DateUtility.getNowChineseDate()));
    }

    public ByteArrayOutputStream doReport(List<MonthlyRpt29Case> dataList, HashMap<String, Object> map) throws Exception {
    	
    	tempSheet = workbook.getSheetAt(0);
        //HSSFSheet sheet = workbook.createSheet(sheetName);
        HSSFSheet sheet = workbook.getSheetAt(0);
    	//HSSFSheet sheet = workbook.cloneSheet(0);
        //workbook.setSheetName(0, "老年年金");
        
        String rptKind = (String) map.get("rptKind");
        String payCode = (String) map.get("payCode");
        
        // head
        printHead(dataList.get(0), rptKind, payCode);

        HSSFRow row = sheet.getRow(2);
        row = incrementRow(sheet, row);
        HSSFCell cell;

        // 列印資料內容
        for (int i = 0; i < dataList.size(); i++) {
        	MonthlyRpt29Case caseData = (MonthlyRpt29Case) dataList.get(i);

            // 從templet複製row
            //copyRows(workbook.getSheetName(1), workbook.getSheetName(2), 3, 3, row.getRowNum(), 10);
            
        	if(StringUtils.equals("1", rptKind)){
        	
                // 受理編號
                tempCell = tempSheet.getRow(3).getCell((short) 0);
                cell = row.createCell((short) 0);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getApNo()));
            
                // 發文字號
                tempCell = tempSheet.getRow(3).getCell((short) 1);
                cell = row.createCell((short) 1);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty("保職簡字第"+caseData.getApNo())+"號");
            
                // 事故者姓名
                tempCell = tempSheet.getRow(3).getCell((short) 2);
                cell = row.createCell((short) 2);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getEvtName()));
            
                // 眷(遺)屬姓名
                tempCell = tempSheet.getRow(3).getCell((short) 3);
                cell = row.createCell((short) 3);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getBenName()));
            
                // 郵遞區號
                tempCell = tempSheet.getRow(3).getCell((short) 4);
                cell = row.createCell((short) 4);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getCommZip()));
            
                // 地址
                tempCell = tempSheet.getRow(3).getCell((short) 5);
                cell = row.createCell((short) 5);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getCommAddr()));
            
                // 符合註記
                tempCell = tempSheet.getRow(3).getCell((short) 6);
                cell = row.createCell((short) 6);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getChkCode()));
            
                // 歸檔日期
                tempCell = tempSheet.getRow(3).getCell((short) 7);
                cell = row.createCell((short) 7);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(DateUtility.changeDateType(caseData.getArcDate())));
            
                // 歸檔頁次
                tempCell = tempSheet.getRow(3).getCell((short) 8);
                cell = row.createCell((short) 8);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getArcPg()));
            
                // 處理狀態
                tempCell = tempSheet.getRow(3).getCell((short) 9);
                cell = row.createCell((short) 9);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getProcStat()));
            
                // 初核人員
                tempCell = tempSheet.getRow(3).getCell((short) 10);
                cell = row.createCell((short) 10);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getPrpNo()));
            
        	}else{
        		
        		 // 受理編號
                tempCell = tempSheet.getRow(3).getCell((short) 0);
                cell = row.createCell((short) 0);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getApNo()));
                
                // 在學起迄年月的最大值
                tempCell = tempSheet.getRow(3).getCell((short) 1);
                cell = row.createCell((short) 1);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(DateUtility.changeWestYearMonthType(caseData.getStudEdate())));
                
                // 發文字號
                tempCell = tempSheet.getRow(3).getCell((short) 2);
                cell = row.createCell((short) 2);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty("保職簡字第"+caseData.getApNo())+"號");
                
                // 事故者姓名
                tempCell = tempSheet.getRow(3).getCell((short) 3);
                cell = row.createCell((short) 3);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getEvtName()));
                
                // 眷(遺)屬姓名
                tempCell = tempSheet.getRow(3).getCell((short) 4);
                cell = row.createCell((short) 4);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getBenName()));
                
                // 郵遞區號
                tempCell = tempSheet.getRow(3).getCell((short) 5);
                cell = row.createCell((short) 5);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getCommZip()));
                
                // 地址
                tempCell = tempSheet.getRow(3).getCell((short) 6);
                cell = row.createCell((short) 6);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getCommAddr()));
                
                // 符合註記
                tempCell = tempSheet.getRow(3).getCell((short) 7);
                cell = row.createCell((short) 7);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getChkCode()));
                
                // 歸檔日期
                tempCell = tempSheet.getRow(3).getCell((short) 8);
                cell = row.createCell((short) 8);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(DateUtility.changeDateType(caseData.getArcDate())));
                
                // 歸檔頁次
                tempCell = tempSheet.getRow(3).getCell((short) 9);
                cell = row.createCell((short) 9);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getArcPg()));
                
                // 處理狀態
                tempCell = tempSheet.getRow(3).getCell((short) 10);
                cell = row.createCell((short) 10);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getProcStat()));
                
                // 初核人員
                tempCell = tempSheet.getRow(3).getCell((short) 11);
                cell = row.createCell((short) 11);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getPrpNo()));
        		
        	}

            row = incrementRow(sheet, row);
        }
        
        workbook.getSheetAt(0).setSelected(true);
        workbook.write(bao);
        return bao;

//        workbook.removeSheetAt(0);
//        workbook.setSheetName(0, "勞保年金專戶資料回寫清冊");
//        workbook.getSheetAt(0).setSelected(true);
//        workbook.write(bao);
//        return bao;
    }

}
