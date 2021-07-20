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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt20Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt21Case;
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
 * 應收款立帳清冊
 * 
 * @author swim
 */
public class MonthlyRpt21Report extends ReportBase {

    public Integer totalIssueAmt = 0;
    public Integer totalUnacpAmt = 0;

    public MonthlyRpt21Report() throws Exception {
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
            // addColumnAssignVAlignmentAndLineSpace(table, 7, 1, "　", fontCh10, 8, 1, CENTER, TOP);
        }
    }

    public Table getHeader(boolean bPrintMainHeader, String payCodeStr, String payYm, String chkDate, HashMap<String, Object> map, String payCode, String cPrnDate, BigDecimal rptPage) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(42);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 34, 1, "報表編號：BALP0D3L0", fontCh10, 0, LEFT);
            addColumn(table, 8, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);

            addColumn(table, 8, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 26, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate())+"　"+RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
            //addColumn(table, 26, 1, "勞工保險局　給付處", fontCh18, 0, CENTER);
            addColumn(table, 8, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 14, 1, "應收款立帳清冊", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);

            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);

            addColumn(table, 42, 1, "給 付 別：" + payCodeStr, fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "核定日期：" + DateUtility.formatChineseDateString(DateUtility.changeDateType(chkDate), true), fontCh10, 0, LEFT);
            addColumn(table, 14, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(cPrnDate, false), fontCh10, 0, LEFT);
            addColumn(table, 14, 1, "頁　　次：" + rptPage , fontCh10, 0, LEFT);

        }

        addColumnAssignVAlignmentAndLineSpace(table, 2, 1, "序號", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 7, 1, "受理編號", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 4, 1, "受款人序", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 11, 1, "受款人姓名", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 4, 1, "核定年月", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 4, 1, "給付年月", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "核定金額", fontCh10, 8, 1, CENTER, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "應收金額", fontCh10, 8, 1, CENTER, TOP);
        // addColumnAssignVAlignmentAndLineSpace(table, 7, 1, "備註", fontCh10, 8, 1, CENTER, TOP);

        return table;
    }

    public void addFooter(Table table, String DeptName, List<MonthlyRpt21Case> amtList, Integer rowCount, List<MonthlyRpt21Case> caseList, Integer rowBeg, Integer rowEnd, String payCode) throws Exception {
        Integer sumIssueAmt = 0;
        Integer sumUnacpAmt = 0;

        Integer allIssueAmt = 0;
        Integer allUnacpAmt = 0;

        // 本頁小計
        for (int i = 0; i < rowCount; i++) {
            MonthlyRpt21Case amtData = amtList.get(i);
            sumIssueAmt += amtData.getIssueAmt().intValue();
            sumUnacpAmt += amtData.getUnacpAmt().intValue();
        }
        // 總累計
        for (int j = rowBeg; j < rowEnd; j++) {
            MonthlyRpt21Case caseData = caseList.get(j);
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
        // addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "", fontCh10, 8, 1, CENTER, TOP);

        addColumnAssignVAlignmentAndLineSpace(table, 9, 0, RptTitleUtility.getDivisionTitle(payCode, DateUtility.getNowWestDate()), fontCh10, 8, 0, LEFT, TOP);
        //addColumnAssignVAlignmentAndLineSpace(table, 9, 0, DeptName + "給付科", fontCh10, 8, 0, LEFT, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 15, 1, "總(累)計", fontCh10, 8, 1, CENTER, TOP);
        // addColumnAssignVAlignmentAndLineSpace(table, 8, 1, ReportBase.formatNumber(allIssueAmt), fontCh10, 8, 1, RIGHT, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 8, 1, ReportBase.formatNumber(totalIssueAmt), fontCh10, 8, 1, RIGHT, TOP);
        // addColumnAssignVAlignmentAndLineSpace(table, 10, 1, ReportBase.formatNumber(allUnacpAmt), fontCh10, 8, 1, RIGHT, TOP);
        addColumnAssignVAlignmentAndLineSpace(table, 10, 1, ReportBase.formatNumber(totalUnacpAmt), fontCh10, 8, 1, RIGHT, TOP);
        // addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "", fontCh10, 8, 1, CENTER, TOP);
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt21Case> caseData, List<MonthlyRpt21Case> caseData1, HashMap<String, Object> map) throws Exception {
        try {
            // 2009.2.20 swim add 輸出報表檔至主機上
        	String payCode = (String) map.get("payCode");
            String printDate = DateUtility.getNowChineseDate();
            //FileOutputStream bao = new FileOutputStream(ConstantKey.RPT_PATH + (String) map.get("payCode") + "_MonthlyRpt21_" + printDate + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt21_" + printDate + ".pdf");
            writer = PdfWriter.getInstance(document, bao);

            document.open();

            // 201205 start
            // 【NACHGMK is null】
            // 建立資料Map
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 nlWkMk + adWkMk 下的所有資料
            Map<String, List<MonthlyRpt21Case>> dataMap = new TreeMap<String, List<MonthlyRpt21Case>>();

            // Map<String, List<MonthlyRpt04Case>> dataMapFooter = new TreeMap<String, List<MonthlyRpt04Case>>(); //20120406
            for (int i = 0; i < caseData.size(); i++) {
                MonthlyRpt21Case obj = caseData.get(i);
                // dataMap.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt20Case>());
                dataMap.put(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt21Case>());
            }

            for (int i = 0; i < caseData.size(); i++) {
                MonthlyRpt21Case obj = caseData.get(i);
                // (dataMap.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
                (dataMap.get(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
            }

            for (Iterator it = dataMap.keySet().iterator(); it.hasNext();) {
            	totalIssueAmt = 0;
                totalUnacpAmt = 0;
                // 201205 end

                Table table = null;

                String deptName = null; // 部門名稱

                List<MonthlyRpt21Case> amtList = new ArrayList<MonthlyRpt21Case>();

                // 序號
                Integer seqNo = 1;
                // 計算每頁筆數
                Integer rowCount = 0;
                // 累計金額起始
                Integer rowBeg = 0;

                String key = (String) it.next();
                List<MonthlyRpt21Case> caseList = dataMap.get(key);

                // for(int i=0;i<caseData.size();i++){ // 受理案件資料
                // MonthlyRpt21Case monthlyRpt21Case = caseData.get(i);
                for (int i = 0; i < caseList.size(); i++) { // 受理案件資料
                    MonthlyRpt21Case monthlyRpt21Case = caseList.get(i);
                    BigDecimal rptPage = monthlyRpt21Case.getRptPage();
                    if (i == 0) {
                        // 取得表頭
                        table = getHeader(true, monthlyRpt21Case.getPayCodeStr(monthlyRpt21Case.getAdWkMk(), monthlyRpt21Case.getNlWkMk(), "N"), monthlyRpt21Case.getPayYm(), monthlyRpt21Case.getChkDate(), map,payCode,monthlyRpt21Case.getcPrnDateStr(),rptPage);
                    }
                    else {
                        if (((rowCount) % 20 == 0)) {
                            // 不滿20筆補空白行
                            if (rowCount % 20 > 0)
                                addEmptyRow(table, 20 - (rowCount % 20));

                            // 加入表尾
                            // addFooter(table, monthlyRpt21Case.getDeptNameString(), amtList, rowCount, caseData, rowBeg, i);
                            addFooter(table, monthlyRpt21Case.getDeptNameString(), amtList, rowCount, caseList, rowBeg, i,payCode);
                            document.add(table);

                            // 取得表頭
                            table = getHeader(true, monthlyRpt21Case.getPayCodeStr(monthlyRpt21Case.getAdWkMk(), monthlyRpt21Case.getNlWkMk(), "N"), monthlyRpt21Case.getPayYm(), monthlyRpt21Case.getChkDate(), map,payCode,monthlyRpt21Case.getcPrnDateStr(), rptPage);

                            rowCount = 0;
                        }
                    }

                    // 部門名稱
                    deptName = monthlyRpt21Case.getDeptNameString();

                    // 序號
                    addColumnAssignVAlignmentAndLineSpace(table, 2, 1, Integer.toString(seqNo), fontCh10, 8, 1, CENTER, TOP);
                    // 受理編號
                    addColumnAssignVAlignmentAndLineSpace(table, 7, 1, monthlyRpt21Case.getApNoStr(), fontCh10, 8, 1, CENTER, TOP);
                    // 受款人序
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, monthlyRpt21Case.getSeqNo(), fontCh10, 8, 1, CENTER, TOP);
                    // 受款人姓名
                    addColumnAssignVAlignmentAndLineSpace(table, 11, 1, monthlyRpt21Case.getBenName(), fontCh10, 8, 1, LEFT, TOP);
                    // 核定年月
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, DateUtility.changeWestYearMonthType(monthlyRpt21Case.getIssuYm()), fontCh10, 8, 1, CENTER, TOP);
                    // 給付年月
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, DateUtility.changeWestYearMonthType(monthlyRpt21Case.getPayYm()), fontCh10, 8, 1, CENTER, TOP);
                    // 核定金額
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, ReportBase.formatNumber(monthlyRpt21Case.getIssueAmt().longValue()), fontCh10, 8, 1, RIGHT, TOP);
                    // 應收金額
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, ReportBase.formatNumber(monthlyRpt21Case.getUnacpAmt().longValue()), fontCh10, 8, 1, RIGHT, TOP);
                    // 備註
                    // addColumnAssignVAlignmentAndLineSpace(table, 7, 1, "", fontCh10, 8, 1, CENTER, TOP);

                    // 計算本頁小計用
                    amtList.add(monthlyRpt21Case);

                    seqNo++;
                    rowCount++;

                }// for(int i=0;i<caseData.size();i++) end

                // 不滿20筆補空白行
                if (rowCount % 20 > 0)
                    addEmptyRow(table, 20 - (rowCount % 20));

                // 加入表尾
                // addFooter(table, deptName, amtList, rowCount, caseData, rowBeg, caseData.size());
                addFooter(table, deptName, amtList, rowCount, caseList, rowBeg, caseList.size(),payCode);
                document.add(table);

            } // 201205 分頁
            
            // 201205 start
            // 【NACHGMK is not null】
            // 建立資料Map
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 nlWkMk + adWkMk 下的所有資料
            Map<String, List<MonthlyRpt21Case>> dataMap1 = new TreeMap<String, List<MonthlyRpt21Case>>();
            
            // Map<String, List<MonthlyRpt04Case>> dataMapFooter1 = new TreeMap<String, List<MonthlyRpt04Case>>(); //20120406
            for (int i = 0; i < caseData1.size(); i++) {
                MonthlyRpt21Case obj = caseData1.get(i);
                // dataMap1.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt20Case>());
                dataMap1.put(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt21Case>());
            }

            for (int i = 0; i < caseData1.size(); i++) {
                MonthlyRpt21Case obj = caseData1.get(i);
                // (dataMap1.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
                (dataMap1.get(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
            }

            for (Iterator it = dataMap1.keySet().iterator(); it.hasNext();) {
            	totalIssueAmt = 0;
                totalUnacpAmt = 0;
                // 201205 end

                Table table = null;

                String deptName = null; // 部門名稱

                List<MonthlyRpt21Case> amtList = new ArrayList<MonthlyRpt21Case>();

                // 序號
                Integer seqNo = 1;
                // 計算每頁筆數
                Integer rowCount = 0;
                // 累計金額起始
                Integer rowBeg = 0;

                String key = (String) it.next();
                List<MonthlyRpt21Case> caseList = dataMap1.get(key);

                // for(int i=0;i<caseData1.size();i++){ // 受理案件資料
                // MonthlyRpt21Case monthlyRpt21Case = caseData1.get(i);
                for (int i = 0; i < caseList.size(); i++) { // 受理案件資料
                    MonthlyRpt21Case monthlyRpt21Case = caseList.get(i);
                    BigDecimal rptPage = monthlyRpt21Case.getRptPage();
                    if (i == 0) {
                        // 取得表頭
                        table = getHeader(true, monthlyRpt21Case.getPayCodeStr(monthlyRpt21Case.getAdWkMk(), monthlyRpt21Case.getNlWkMk(), "Y"), monthlyRpt21Case.getPayYm(), monthlyRpt21Case.getChkDate(), map,payCode,monthlyRpt21Case.getcPrnDateStr(),rptPage);
                    }
                    else {
                        if (((rowCount) % 20 == 0)) {
                            // 不滿20筆補空白行
                            if (rowCount % 20 > 0)
                                addEmptyRow(table, 20 - (rowCount % 20));

                            // 加入表尾
                            // addFooter(table, monthlyRpt21Case.getDeptNameString(), amtList, rowCount, caseData1, rowBeg, i);
                            addFooter(table, monthlyRpt21Case.getDeptNameString(), amtList, rowCount, caseList, rowBeg, i,payCode);
                            document.add(table);

                            // 取得表頭
                            table = getHeader(true, monthlyRpt21Case.getPayCodeStr(monthlyRpt21Case.getAdWkMk(), monthlyRpt21Case.getNlWkMk(), "Y"), monthlyRpt21Case.getPayYm(), monthlyRpt21Case.getChkDate(), map,payCode,monthlyRpt21Case.getcPrnDateStr(),rptPage);

                            rowCount = 0;
                        }
                    }

                    // 部門名稱
                    deptName = monthlyRpt21Case.getDeptNameString();

                    // 序號
                    addColumnAssignVAlignmentAndLineSpace(table, 2, 1, Integer.toString(seqNo), fontCh10, 8, 1, CENTER, TOP);
                    // 受理編號
                    addColumnAssignVAlignmentAndLineSpace(table, 7, 1, monthlyRpt21Case.getApNoStr(), fontCh10, 8, 1, CENTER, TOP);
                    // 受款人序
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, monthlyRpt21Case.getSeqNo(), fontCh10, 8, 1, CENTER, TOP);
                    // 受款人姓名
                    addColumnAssignVAlignmentAndLineSpace(table, 11, 1, monthlyRpt21Case.getBenName(), fontCh10, 8, 1, LEFT, TOP);
                    // 核定年月
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, DateUtility.changeWestYearMonthType(monthlyRpt21Case.getIssuYm()), fontCh10, 8, 1, CENTER, TOP);
                    // 給付年月
                    addColumnAssignVAlignmentAndLineSpace(table, 4, 1, DateUtility.changeWestYearMonthType(monthlyRpt21Case.getPayYm()), fontCh10, 8, 1, CENTER, TOP);
                    // 核定金額
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, ReportBase.formatNumber(monthlyRpt21Case.getIssueAmt().longValue()), fontCh10, 8, 1, RIGHT, TOP);
                    // 應收金額
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, ReportBase.formatNumber(monthlyRpt21Case.getUnacpAmt().longValue()), fontCh10, 8, 1, RIGHT, TOP);
                    // 備註
                    // addColumnAssignVAlignmentAndLineSpace(table, 7, 1, "", fontCh10, 8, 1, CENTER, TOP);

                    // 計算本頁小計用
                    amtList.add(monthlyRpt21Case);

                    seqNo++;
                    rowCount++;

                }// for(int i=0;i<caseData1.size();i++) end

                // 不滿20筆補空白行
                if (rowCount % 20 > 0)
                    addEmptyRow(table, 20 - (rowCount % 20));

                // 加入表尾
                // addFooter(table, deptName, amtList, rowCount, caseData1, rowBeg, caseData1.size());
                addFooter(table, deptName, amtList, rowCount, caseList, rowBeg, caseList.size(),payCode);
                document.add(table);

            } // 201205 分頁            

            document.close();
        }
        finally {
            document.close();
        }
        return bao;
    }
}
