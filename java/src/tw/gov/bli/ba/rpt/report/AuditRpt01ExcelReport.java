package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.rpt.cases.AuditRpt01Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt29Case;
import tw.gov.bli.ba.rpt.report.ExcelReportBase;
import tw.gov.bli.ba.util.DateUtility;

public class AuditRpt01ExcelReport extends ExcelReportBase {

    private HSSFSheet tempSheet;
    private HSSFCell tempCell;

    public AuditRpt01ExcelReport() throws Exception {
        super("AuditRpt01Excel.xls");
    }
    
    // 取得給付別中文說明
    private String getPayCodeStr(String payCode) {
        if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_L, payCode)) {
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
        }
        else if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_K, payCode)) {
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
        }
        else if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S, payCode)) {
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
        }
        else {
            return "";
        }
    }
    
    public void printHead(String endYm, String payCode) {
        tempSheet = workbook.getSheetAt(0);
        HSSFSheet sheet = workbook.getSheetAt(0);
        //HSSFSheet sheet = workbook.cloneSheet(0);
        //copyRows(workbook.getSheetName(1), workbook.getSheetName(2), 0, 2, 0, 10);
        HSSFCell cell;
        HSSFRow row1 = sheet.getRow(1);
        HSSFRow row2 = sheet.getRow(2);

        //
        tempCell = tempSheet.getRow(1).getCell((short) 0);
        cell = row1.createCell((short) 0);
        setCell(cell, tempCell.getCellStyle(), "");
        
        // 印表日期
        tempCell = tempSheet.getRow(1).getCell((short) 15);
        cell = row1.createCell((short) 15);
        setCell(cell, tempCell.getCellStyle(), "印表日期：" + StringUtils.trimToEmpty(DateUtility.getNowChineseDate()));

        // 給付別
        tempCell = tempSheet.getRow(2).getCell((short) 0);
        cell = row2.createCell((short) 0);
        setCell(cell, tempCell.getCellStyle(), "給付別："+getPayCodeStr(payCode));

        // 截止年月
        tempCell = tempSheet.getRow(2).getCell((short) 15);
        cell = row2.createCell((short) 15);
        setCell(cell, tempCell.getCellStyle(), "截止年月："+DateUtility.formatChineseYearMonthString(endYm, false) );
    }

    public ByteArrayOutputStream doReport(List<AuditRpt01Case> dataList ,String endYm, String payCode) throws Exception {
    	
    	tempSheet = workbook.getSheetAt(0);
        //HSSFSheet sheet = workbook.createSheet(sheetName);
        HSSFSheet sheet = workbook.getSheetAt(0);
    	//HSSFSheet sheet = workbook.cloneSheet(0);
        //workbook.setSheetName(0, "老年年金");
        
        // head
        printHead(endYm, payCode);

        HSSFRow row = sheet.getRow(3);
        row = incrementRow(sheet, row);
        HSSFCell cell;

        int rowCount = 1;// 本頁列印筆數
        // 列印資料內容
        for (int i = 0; i < dataList.size(); i++) {
        	AuditRpt01Case caseData = (AuditRpt01Case) dataList.get(i);

            // 從templet複製row
            //copyRows(workbook.getSheetName(1), workbook.getSheetName(2), 3, 3, row.getRowNum(), 10);
            
            // 序號
            tempCell = tempSheet.getRow(4).getCell((short) 0);
            cell = row.createCell((short) 0);
            setCell(cell, tempCell.getCellStyle(), rowCount++);
            
            // 申請日期
            tempCell = tempSheet.getRow(4).getCell((short) 1);
            cell = row.createCell((short) 1);
            setCell(cell, tempCell.getCellStyle(), DateUtility.changeDateType(StringUtils.trimToEmpty(caseData.getAppDate())));
            
            // 受理日期
            tempCell = tempSheet.getRow(4).getCell((short) 2);
            cell = row.createCell((short) 2);
            setCell(cell, tempCell.getCellStyle(), DateUtility.changeDateType(StringUtils.trimToEmpty(caseData.getCrtTime())));
            
            // 受理編號
            tempCell = tempSheet.getRow(4).getCell((short) 3);
            cell = row.createCell((short) 3);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getApNo()));
            
            // 姓名
            tempCell = tempSheet.getRow(4).getCell((short) 4);
            cell = row.createCell((short) 4);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getEvtName()));
            
            // 申請項目
            tempCell = tempSheet.getRow(4).getCell((short) 5);
            cell = row.createCell((short) 5);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getApItemStr()));
            
            // 處理狀態
            tempCell = tempSheet.getRow(4).getCell((short) 6);
            cell = row.createCell((short) 6);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getProcStatString()));
            
            // 處理日期
            tempCell = tempSheet.getRow(4).getCell((short) 7);
            cell = row.createCell((short) 7);
            setCell(cell, tempCell.getCellStyle(), DateUtility.changeDateType(StringUtils.trimToEmpty(caseData.getLastDate())));
            
            // 處理人員
            tempCell = tempSheet.getRow(4).getCell((short) 8);
            cell = row.createCell((short) 8);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getLastUser()));
            
            // 函別
            tempCell = tempSheet.getRow(4).getCell((short) 9);
            cell = row.createCell((short) 9);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getLeterTypeStr()));
            
            // 註記
            tempCell = tempSheet.getRow(4).getCell((short) 10);
            cell = row.createCell((short) 10);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getNdomk1Str()));

            // 案件類別
            tempCell = tempSheet.getRow(4).getCell((short) 11);
            cell = row.createCell((short) 11);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getCaseTypString()));
            
            // 初核人員
            tempCell = tempSheet.getRow(4).getCell((short) 12);
            cell = row.createCell((short) 12);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getChkMan()));
            
            // 本次編審註記列表
            tempCell = tempSheet.getRow(4).getCell((short) 13);
            cell = row.createCell((short) 13);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getChkList()));
            
            // 前次編審註記列表
            tempCell = tempSheet.getRow(4).getCell((short) 14);
            cell = row.createCell((short) 14);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getPreChkList()));
            
            // 被保險人ID
            tempCell = tempSheet.getRow(4).getCell((short) 15);
            cell = row.createCell((short) 15);
            setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getEvtIdnNo()));

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
