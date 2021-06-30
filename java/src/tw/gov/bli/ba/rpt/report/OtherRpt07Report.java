package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.FileOutputStream;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.OtherRpt07Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 轉催收核定清冊
 * 
 * @author zehua
 */
public class OtherRpt07Report extends ReportBase {

    public Integer totalIssueAmt = 0;
    public Integer totalUnacpAmt = 0;

    public OtherRpt07Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        return document;
    }

    /**
     * 加入空白行
     * 
     * @param table
     * @param rows 行數
     * @throws Exception
     */
    public void addEmptyRow(Table table, int rows) throws Exception {
        for (int i = 0; i < rows; i++) {
            addColumnAssignVAlignmentAndLineSpace(table, 2, 1, "　", fontCh10, 8, 1, CENTER, TOP);
            addColumnAssignVAlignmentAndLineSpace(table, 7, 1, "　", fontCh10, 8, 1, CENTER, TOP);
            addColumnAssignVAlignmentAndLineSpace(table, 4, 1, "　", fontCh10, 8, 1, CENTER, TOP);
            addColumnAssignVAlignmentAndLineSpace(table, 11, 1, "　", fontCh10, 8, 1, LEFT, TOP);
            addColumnAssignVAlignmentAndLineSpace(table, 4, 1, "　", fontCh10, 8, 1, CENTER, TOP);
            addColumnAssignVAlignmentAndLineSpace(table, 4, 1, "　", fontCh10, 8, 1, CENTER, TOP);
            addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "　", fontCh10, 8, 1, CENTER, TOP);
            addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "　", fontCh10, 8, 1, CENTER, TOP);
            
        }
    }

    public Table getHeader(boolean bPrintMainHeader, String payCodeStr, String payYm, String chkDate, HashMap<String, Object> map, String payCode, String cPrnDate, String rptPage) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(42);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 34, 1, "報表編號：BALP0D670L", fontCh10, 0, LEFT);
            addColumn(table, 8, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);

            addColumn(table, 8, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 26, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate())+"　"+RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
            addColumn(table, 8, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 14, 1, "轉催收清冊", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);

            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);

            addColumn(table, 42, 1, "給 付 別：" + payCodeStr, fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "處理日期：" + DateUtility.formatChineseDateTimeString(DateUtility.changeDateType(chkDate), true), fontCh10, 0, LEFT);
            addColumn(table, 14, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(cPrnDate, true), fontCh10, 0, LEFT);
            addColumn(table, 14, 1, "頁　　次：" + rptPage , fontCh10, 0, LEFT);
            //addColumn(table, 14, 1, "" + rptPage , fontCh10, 0, LEFT);

        }

        addColumnAssignVAlignmentAndLineSpace(table, 2, 1, "序號", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 7, 1, "受理編號", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 4, 1, "受款人序", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 11, 1, "受款人姓名", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 4, 1, "核定年月", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 4, 1, "給付年月", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "核定金額", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "應收金額", fontCh10, 8, 1, CENTER, TOP);
        
        return table;
    }

    public void addFooter(Table table, String DeptName, List<OtherRpt07Case> amtList, Integer rowCount, List<OtherRpt07Case> caseList, Integer rowBeg, Integer rowEnd, String payCode) throws Exception {
        Integer sumIssueAmt = 0;
        Integer sumUnacpAmt = 0;

        Integer allIssueAmt = 0;
        Integer allUnacpAmt = 0;

        // 本頁小計
        for (int i = 0; i < rowCount; i++) {
        	OtherRpt07Case amtData = amtList.get(i);
            sumIssueAmt += amtData.getIssueAmt().intValue();
            sumUnacpAmt += amtData.getUnacpAmt().intValue();
        }
        // 總累計
        for (int j = rowBeg; j < rowEnd; j++) {
        	OtherRpt07Case caseData = caseList.get(j);
            allIssueAmt += caseData.getIssueAmt().intValue();
            allUnacpAmt += caseData.getUnacpAmt().intValue();
        }

        totalIssueAmt = totalIssueAmt + sumIssueAmt;
        totalUnacpAmt = totalUnacpAmt + sumUnacpAmt;

        // 清空暫存
        amtList.clear();
        rowCount = 0;

        addColumnAssignVAlignmentAndLineSpace(table, 9, 0, "", fontCh10, 8, 0, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 15, 1, "本頁小計", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 8, 1, ReportBase.formatNumber(sumIssueAmt), fontCh10, 8, 1, RIGHT, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 10, 1, ReportBase.formatNumber(sumUnacpAmt), fontCh10, 8, 1, RIGHT, TOP);
        
        addColumnAssignVAlignmentAndLineSpace(table, 9, 0, RptTitleUtility.getDivisionTitle(payCode, DateUtility.getNowWestDate()), fontCh10, 8, 0, LEFT, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 15, 1, "總(累)計", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 8, 1, ReportBase.formatNumber(totalIssueAmt), fontCh10, 8, 1, RIGHT, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 10, 1, ReportBase.formatNumber(totalUnacpAmt), fontCh10, 8, 1, RIGHT, TOP);
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<OtherRpt07Case> caseData, List<OtherRpt07Case> caseData1, HashMap<String, Object> map) throws Exception {
        try {
        	int rptPage = 0; 
            String payCode = (String) map.get("payCode");
            String printDate = DateUtility.getNowChineseDate();
            writer = PdfWriter.getInstance(document, bao);

            document.open();

            Map<String, List<OtherRpt07Case>> dataMap = new TreeMap<String, List<OtherRpt07Case>>();

            for (int i = 0; i < caseData.size(); i++) {
            	OtherRpt07Case obj = caseData.get(i);
                dataMap.put(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<OtherRpt07Case>());
            }

            for (int i = 0; i < caseData.size(); i++) {
            	OtherRpt07Case obj = caseData.get(i);
                (dataMap.get(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
            }

            for (Iterator it = dataMap.keySet().iterator(); it.hasNext();) {

                // 201205 end

                Table table = null;

                String deptName = null; // 部門名稱

                List<OtherRpt07Case> amtList = new ArrayList<OtherRpt07Case>();

                // 序號
                Integer seqNo = 1;
                // 計算每頁筆數
                Integer rowCount = 0;
                // 累計金額起始
                Integer rowBeg = 0;

                String key = (String) it.next();
                List<OtherRpt07Case> caseList = dataMap.get(key);
               
               for (int i = 0; i < caseList.size(); i++) { // 受理案件資料
                	OtherRpt07Case otherRpt07Case = caseList.get(i);
                    //BigDecimal rptPage = otherRpt07Case.getRptPage();
                    
                	if (i == 0) {
                		rptPage = rptPage + 1;
                        // 取得表頭
                        table = getHeader(true, otherRpt07Case.getPayCodeStr(otherRpt07Case.getAdWkMk(), otherRpt07Case.getNlWkMk(), "N"), otherRpt07Case.getPayYm(), otherRpt07Case.getChkDate(), map,payCode,otherRpt07Case.getcPrnDateStr(),String.valueOf(rptPage));
                    }
                    else {
                        if (((rowCount) % 20 == 0)) {
                        	
                            // 不滿20筆補空白行
                            if (rowCount % 20 > 0)
                                addEmptyRow(table, 20 - (rowCount % 20));

                            // 加入表尾
                            addFooter(table, otherRpt07Case.getDeptNameString(), amtList, rowCount, caseList, rowBeg, i,payCode);
                            document.add(table);
                            rptPage = rptPage + 1;
                            // 取得表頭
                            table = getHeader(true, otherRpt07Case.getPayCodeStr(otherRpt07Case.getAdWkMk(), otherRpt07Case.getNlWkMk(), "N"), otherRpt07Case.getPayYm(), otherRpt07Case.getChkDate(), map,payCode,otherRpt07Case.getcPrnDateStr(), String.valueOf(rptPage));

                            rowCount = 0;
                        }
                    }

                    // 部門名稱
                    deptName = otherRpt07Case.getDeptNameString();
                    // 序號
                    addColumnAssignVAlignmentAndLineSpace(table, 2, 1, Integer.toString(seqNo), fontCh10, 8, 1, CENTER, TOP);
                    // 受理編號
                    addColumnAssignVAlignmentAndLineSpace(table, 7, 1, otherRpt07Case.getApNoStr(), fontCh10, 8, 1, CENTER, TOP);
                    // 受款人序
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, otherRpt07Case.getSeqNo(), fontCh10, 8, 1, CENTER, TOP);
                    // 受款人姓名
                    addColumnAssignVAlignmentAndLineSpace(table, 11, 1, otherRpt07Case.getBenName(), fontCh10, 8, 1, LEFT, TOP);
                    // 核定年月
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, DateUtility.changeWestYearMonthType(otherRpt07Case.getIssuYm()), fontCh10, 8, 1, CENTER, TOP);
                    // 給付年月
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, DateUtility.changeWestYearMonthType(otherRpt07Case.getPayYm()), fontCh10, 8, 1, CENTER, TOP);
                    // 核定金額
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, ReportBase.formatNumber(otherRpt07Case.getIssueAmt().longValue()), fontCh10, 8, 1, RIGHT, TOP);
                    // 應收金額
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, ReportBase.formatNumber(otherRpt07Case.getUnacpAmt().longValue()), fontCh10, 8, 1, RIGHT, TOP);
                    
                    // 計算本頁小計用
                    amtList.add(otherRpt07Case);

                    seqNo++;
                    rowCount++;

                }
                // 不滿20筆補空白行
                if (rowCount % 20 > 0)
                    addEmptyRow(table, 20 - (rowCount % 20));

                // 加入表尾
                addFooter(table, deptName, amtList, rowCount, caseList, rowBeg, caseList.size(),payCode);
                document.add(table);

            } 
            Map<String, List<OtherRpt07Case>> dataMap1 = new TreeMap<String, List<OtherRpt07Case>>();
            
            for (int i = 0; i < caseData1.size(); i++) {
            	OtherRpt07Case obj = caseData1.get(i);
                dataMap1.put(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<OtherRpt07Case>());
            }

            for (int i = 0; i < caseData1.size(); i++) {
            	OtherRpt07Case obj = caseData1.get(i);
                (dataMap1.get(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
            }

            for (Iterator it = dataMap1.keySet().iterator(); it.hasNext();) {

                Table table = null;

                String deptName = null; // 部門名稱

                List<OtherRpt07Case> amtList = new ArrayList<OtherRpt07Case>();

                // 序號
                Integer seqNo = 1;
                // 計算每頁筆數
                Integer rowCount = 0;
                // 累計金額起始
                Integer rowBeg = 0;

                String key = (String) it.next();
                List<OtherRpt07Case> caseList = dataMap1.get(key);
                //int rptPage = 0;
                for (int i = 0; i < caseList.size(); i++) { // 受理案件資料
                	OtherRpt07Case otherRpt07Case = caseList.get(i);
                    //BigDecimal rptPage = otherRpt07Case.getRptPage();
                    
                    if (i == 0) {
                    	rptPage = rptPage + 1;
                        // 取得表頭
                        table = getHeader(true, otherRpt07Case.getPayCodeStr(otherRpt07Case.getAdWkMk(), otherRpt07Case.getNlWkMk(), "Y"), otherRpt07Case.getPayYm(), otherRpt07Case.getChkDate(), map,payCode,otherRpt07Case.getcPrnDateStr(),String.valueOf(rptPage));
                    }
                    else {
                        if (((rowCount) % 20 == 0)) {
                            // 不滿20筆補空白行
                            if (rowCount % 20 > 0)
                                addEmptyRow(table, 20 - (rowCount % 20));

                            // 加入表尾
                            addFooter(table, otherRpt07Case.getDeptNameString(), amtList, rowCount, caseList, rowBeg, i,payCode);
                            document.add(table);
                            rptPage = rptPage + 1;
                            // 取得表頭
                            table = getHeader(true, otherRpt07Case.getPayCodeStr(otherRpt07Case.getAdWkMk(), otherRpt07Case.getNlWkMk(), "Y"), otherRpt07Case.getPayYm(), otherRpt07Case.getChkDate(), map,payCode,otherRpt07Case.getcPrnDateStr(),String.valueOf(rptPage));

                            rowCount = 0;
                        }
                    }

                    // 部門名稱
                    deptName = otherRpt07Case.getDeptNameString();

                    // 序號
                    addColumnAssignVAlignmentAndLineSpace(table, 2, 1, Integer.toString(seqNo), fontCh10, 8, 1, CENTER, TOP);
                    // 受理編號
                    addColumnAssignVAlignmentAndLineSpace(table, 7, 1, otherRpt07Case.getApNoStr(), fontCh10, 8, 1, CENTER, TOP);
                    // 受款人序
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, otherRpt07Case.getSeqNo(), fontCh10, 8, 1, CENTER, TOP);
                    // 受款人姓名
                    addColumnAssignVAlignmentAndLineSpace(table, 11, 1, otherRpt07Case.getBenName(), fontCh10, 8, 1, LEFT, TOP);
                    // 核定年月
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, DateUtility.changeWestYearMonthType(otherRpt07Case.getIssuYm()), fontCh10, 8, 1, CENTER, TOP);
                    // 給付年月
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, DateUtility.changeWestYearMonthType(otherRpt07Case.getPayYm()), fontCh10, 8, 1, CENTER, TOP);
                    // 核定金額
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, ReportBase.formatNumber(otherRpt07Case.getIssueAmt().longValue()), fontCh10, 8, 1, RIGHT, TOP);
                    // 應收金額
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, ReportBase.formatNumber(otherRpt07Case.getUnacpAmt().longValue()), fontCh10, 8, 1, RIGHT, TOP);
                   
                    // 計算本頁小計用
                    amtList.add(otherRpt07Case);

                    seqNo++;
                    rowCount++;

                }

                // 不滿20筆補空白行
                if (rowCount % 20 > 0)
                    addEmptyRow(table, 20 - (rowCount % 20));

                // 加入表尾
                addFooter(table, deptName, amtList, rowCount, caseList, rowBeg, caseList.size(),payCode);
                document.add(table);

            } 

            document.close();
        }
        finally {
            document.close();
        }
        return bao;
    }
}

