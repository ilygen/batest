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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt30Case;
import tw.gov.bli.ba.rpt.report.ExcelReportBase;
import tw.gov.bli.ba.util.DateUtility;

public class MonthlyRpt30ExcelReport extends ExcelReportBase {

    private HSSFSheet tempSheet;
    private HSSFCell tempCell;

    public MonthlyRpt30ExcelReport(String excelTyp) throws Exception {
        super(excelTyp);
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

    public void printHead(MonthlyRpt30Case caseData, String rptKind, String payCode, String issuYm) {
        tempSheet = workbook.getSheetAt(0);
        HSSFSheet sheet = workbook.getSheetAt(0);
        // HSSFSheet sheet = workbook.cloneSheet(0);
        // copyRows(workbook.getSheetName(1), workbook.getSheetName(2), 0, 2, 0, 10);
        HSSFCell cell;
        HSSFRow row0 = sheet.getRow(0);
        HSSFRow row1 = sheet.getRow(1);
        HSSFRow row2 = sheet.getRow(2);
        //
        tempCell = tempSheet.getRow(0).getCell((short) 0);
        cell = row0.createCell((short) 0);
        if (rptKind.equals("1")) {
            setCell(cell, tempCell.getCellStyle(), "重新查核失能程度案件明細資料");
        }
        else {
            setCell(cell, tempCell.getCellStyle(), "通過查核續發失能年金案件明細資料");
        }

        //
        /**
         * tempCell = tempSheet.getRow(1).getCell((short) 0); 
         * cell = row1.createCell((short) 0);
         */

        // 印表日期
        tempCell = tempSheet.getRow(1).getCell((short) 0);
        cell = row1.createCell((short) 0);
        setCell(cell, tempCell.getCellStyle(), "印表日期：" + DateUtility.formatChineseDateTimeString(StringUtils.trimToEmpty(DateUtility.getNowChineseDate()), false));

        // 給付別 + 核定年月
        tempCell = tempSheet.getRow(2).getCell((short) 0);
        cell = row2.createCell((short) 0);
        setCell(cell, tempCell.getCellStyle(), "給付別：" + getPayCodeStr(payCode) + "　　　　　核定年月：" + DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(issuYm), false));

        // 核定年月
        /**
         * tempCell = tempSheet.getRow(2).getCell((short) 1); 
         * cell = row2.createCell((short) 1); 
         * setCell(cell, tempCell.getCellStyle(), "核定年月：" + issuYm);
         */

    }

    public ByteArrayOutputStream doReport(List<MonthlyRpt30Case> dataList, HashMap<String, Object> map) throws Exception {

        tempSheet = workbook.getSheetAt(0);
        // HSSFSheet sheet = workbook.createSheet(sheetName);
        HSSFSheet sheet = workbook.getSheetAt(0);
        // HSSFSheet sheet = workbook.cloneSheet(0);
        // workbook.setSheetName(0, "老年年金");

        String rptKind = (String) map.get("rptKind");
        String payCode = (String) map.get("payCode");
        String issuYm = (String) map.get("issuYm");

        // head
        printHead(dataList.get(0), rptKind, payCode, issuYm);

        HSSFRow row = sheet.getRow(3);
        row = incrementRow(sheet, row);
        HSSFCell cell;

        int rowCount = 1;// 本頁列印筆數
        // 列印資料內容
        for (int i = 0; i < dataList.size(); i++) {
            MonthlyRpt30Case caseData = (MonthlyRpt30Case) dataList.get(i);

            // 從templet複製row
            // copyRows(workbook.getSheetName(1), workbook.getSheetName(2), 3, 3, row.getRowNum(), 10);

            if (StringUtils.equals("1", rptKind)) {
                
                row.setHeightInPoints(25);

                // 序號
                tempCell = tempSheet.getRow(4).getCell((short) 0);
                cell = row.createCell((short) 0);
                setCell(cell, tempCell.getCellStyle(), rowCount++);

                // 受理編號
                tempCell = tempSheet.getRow(4).getCell((short) 1);
                cell = row.createCell((short) 1);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getApNo()));

                // 事故者姓名
                tempCell = tempSheet.getRow(4).getCell((short) 2);
                cell = row.createCell((short) 2);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getEvtName()));

                // 法定代理人姓名
                tempCell = tempSheet.getRow(4).getCell((short) 3);
                cell = row.createCell((short) 3);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getGrdName()));

                // 查核年月
                tempCell = tempSheet.getRow(4).getCell((short) 4);
                cell = row.createCell((short) 4);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(DateUtility.changeWestYearMonthType(caseData.getRehcYm())));

                // 郵遞區號
                tempCell = tempSheet.getRow(4).getCell((short) 5);
                cell = row.createCell((short) 5);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getCommZip()));

                // 地址
                tempCell = tempSheet.getRow(4).getCell((short) 6);
                cell = row.createCell((short) 6);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getCommAddr()));

                // 失能項目
                tempCell = tempSheet.getRow(4).getCell((short) 7);
                cell = row.createCell((short) 7);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getCriInJdp()));
                
                // 承辦人員蓋章
                tempCell = tempSheet.getRow(4).getCell((short) 8);
                cell = row.createCell((short) 8);
                setCell(cell, tempCell.getCellStyle(), "　");

            }
            else {

                // 序號
                tempCell = tempSheet.getRow(4).getCell((short) 0);
                cell = row.createCell((short) 0);
                setCell(cell, tempCell.getCellStyle(), rowCount++);

                // 受理編號
                tempCell = tempSheet.getRow(4).getCell((short) 1);
                cell = row.createCell((short) 1);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getApNo()));

                // 事故者姓名
                tempCell = tempSheet.getRow(4).getCell((short) 2);
                cell = row.createCell((short) 2);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getEvtName()));

                // 完成查核年月
                tempCell = tempSheet.getRow(4).getCell((short) 3);
                cell = row.createCell((short) 3);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(DateUtility.changeWestYearMonthType(caseData.getComRechkYm())));

                // 郵遞區號
                tempCell = tempSheet.getRow(4).getCell((short) 4);
                cell = row.createCell((short) 4);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getCommZip()));

                // 地址
                tempCell = tempSheet.getRow(4).getCell((short) 5);
                cell = row.createCell((short) 5);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getCommAddr()));

                // 初核人員
                tempCell = tempSheet.getRow(4).getCell((short) 6);
                cell = row.createCell((short) 6);
                setCell(cell, tempCell.getCellStyle(), StringUtils.trimToEmpty(caseData.getPrpNo()));

            }

            row = incrementRow(sheet, row);
        }

        workbook.getSheetAt(0).setSelected(true);
        workbook.write(bao);
        return bao;

        // workbook.removeSheetAt(0);
        // workbook.setSheetName(0, "勞保年金專戶資料回寫清冊");
        // workbook.getSheetAt(0).setSelected(true);
        // workbook.write(bao);
        // return bao;
    }

}
