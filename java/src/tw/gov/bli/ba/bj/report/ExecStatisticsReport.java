package tw.gov.bli.ba.bj.report;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import tw.gov.bli.ba.bj.cases.ExecStatisticReportCase;
import tw.gov.bli.ba.rpt.report.ExcelReportBase;
import tw.gov.bli.ba.rpt.report.ReportBase;

public class ExecStatisticsReport  extends ExcelReportBase {
	private HSSFSheet tempSheet;
    private HSSFCell tempCell;

    public ExecStatisticsReport() throws Exception {
        super("ExecStatisticsReport.xls");
        sheetName = "檢核報表";
    }
    
    public ByteArrayOutputStream doReport(List<ExecStatisticReportCase> dataList) throws Exception {
    	tempSheet = workbook.getSheetAt(0);
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);
        HSSFCell cell;
        
        for(int i=0; i<dataList.size(); i++) {
        	ExecStatisticReportCase caseData = dataList.get(i);
        	row = incrementRow(sheet, row);
        	
        	// 給付種類
            tempCell = tempSheet.getRow(1).getCell((short) 2);
            cell = row.createCell((short) 0);
            setCell(cell, tempCell.getCellStyle(), caseData.getPayNo());
            
            // 核定年月
            tempCell = tempSheet.getRow(1).getCell((short) 2);
            cell = row.createCell((short) 1);
            setCell(cell, tempCell.getCellStyle(), caseData.getIssuYm());
            
            // 首發註記
            tempCell = tempSheet.getRow(1).getCell((short) 2);
            cell = row.createCell((short) 2);
            setCell(cell, tempCell.getCellStyle(), caseData.getFirstPay());
            
            // 傷病分類
            tempCell = tempSheet.getRow(1).getCell((short) 2);
            cell = row.createCell((short) 3);
            setCell(cell, tempCell.getCellStyle(), caseData.getEvType());
            
            // 已領老年註記
            tempCell = tempSheet.getRow(1).getCell((short) 2);
            cell = row.createCell((short) 4);
            setCell(cell, tempCell.getCellStyle(), caseData.getAdWkMk());

            // 核付總件數
            tempCell = tempSheet.getRow(1).getCell((short) 5);
            cell = row.createCell((short) 5);
            setCell(cell, tempCell.getCellStyle(), ReportBase.formatBigDecimalToInteger(caseData.getPayCnt()));
            
            // 核付總金額
            tempCell = tempSheet.getRow(1).getCell((short) 6);
            cell = row.createCell((short) 6);
            setCell(cell,  tempCell.getCellStyle(), ReportBase.formatBigDecimalToInteger(caseData.getPamts()));
        
        }        
        workbook.getSheetAt(0).setSelected(true);
        workbook.write(bao);
        return bao;
    }
   
}
