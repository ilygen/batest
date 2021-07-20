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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt33Case;
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
 * 保留遺屬年金計息存儲清冊
 * 
 * @author KIYOMI
 */
public class MonthlyRpt33Report extends ReportBase {

    public Integer totalIssueAmt = 0;
    public Integer totalOtheraAmt = 0;
    public Integer totalOtherbAmt = 0;
    public Integer totalMitRate = 0;
    public Integer totalOffsetAmt = 0;
    public Integer totalCompenAmt = 0;
    public Integer totalInherItorAmt = 0;
    public Integer totalItrtTax = 0;
    public Integer totalOtherAmt = 0;

    public MonthlyRpt33Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);
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
            addColumn(table, 2, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 7, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 3, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 9, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 5, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
        }
    }

    public Table getHeader(boolean bPrintMainHeader, String payCodeStr, String mchkTypStr, String payTypStr, String payYm, String payTyp, String isNaChgMk, HashMap<String, Object> map, String payCode, String cPrnDate, BigDecimal rptPage) throws Exception {

        document.newPage();
        // 建立表格
        Table table = createTable(66);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 66, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 52, 1, "報表編號：BALP0D3V0", fontCh10, 0, LEFT);
            addColumn(table, 14, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 38, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
            // addColumn(table, 38, 1, "勞工保險局 給付處", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 38, 1, "保留遺屬年金計息存儲清冊", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);

            addColumn(table, 30, 1, "給 付 別：" + payCodeStr, fontCh10, 0, LEFT);
            addColumn(table, 28, 1, "類別：" + mchkTypStr, fontCh10, 0, LEFT);
            // addColumn(table, 8, 1, "給付方式：" + payTypStr, fontCh10, 0, LEFT);
            addColumn(table, 8, 1, "　", fontCh10, 0, LEFT);

            addColumn(table, 30, 1, "核定年月：" + map.get("issuYm"), fontCh10, 0, LEFT);
            addColumn(table, 28, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(cPrnDate, false), fontCh10, 0, LEFT);
            addColumn(table, 8, 1, "頁　　次：" + rptPage, fontCh10, 0, LEFT);

        }

        addColumn(table, 2, 1, "序號", fontCh9, 1, CENTER);
        addColumn(table, 7, 1, "受理編號", fontCh9, 1, CENTER);
        addColumn(table, 3, 1, "給付\n年月", fontCh9, 1, CENTER);
        if (payTyp.equals(ConstantKey.BAAPPBASE_PAYTYP_A))
            addColumn(table, 9, 1, "受款人身份證字號", fontCh9, 1, CENTER);
        else
            addColumn(table, 9, 1, "金融機構轉帳帳號", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "被保險人\n姓　名", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "受款人\n姓　名", fontCh9, 1, CENTER);
        addColumn(table, 5, 1, "核定金額(A)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "另案扣減同保險(B)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "另案扣減他保險(C)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "匯費(D)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "抵銷紓困(E)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "代扣補償金(F)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "保留遺屬津貼(G)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "代扣利息所得稅(H)", fontCh9, 1, CENTER);
        addColumn(table, 4, 1, "其他(I)", fontCh9, 1, CENTER);

        return table;
    }

    public void addFooter(Table table, String DeptName, List<MonthlyRpt33Case> amtList, Integer rowCount, List<MonthlyRpt33Case> caseList, String issuTyp, String payTyp, Integer rowBeg, Integer rowEnd, String payCode) throws Exception {
        Integer sumIssueAmt = 0;
        Integer sumOtheraAmt = 0;
        Integer sumOtherbAmt = 0;
        Integer sumMitRate = 0;
        Integer sumOffsetAmt = 0;
        Integer sumCompenAmt = 0;
        Integer sumInherItorAmt = 0;
        Integer sumItrtTax = 0;
        Integer sumOtherAmt = 0;

        Integer allIssueAmt = 0;
        Integer allOtheraAmt = 0;
        Integer allOtherbAmt = 0;
        Integer allMitRate = 0;
        Integer allOffsetAmt = 0;
        Integer allCompenAmt = 0;
        Integer allInherItorAmt = 0;
        Integer allItrtTax = 0;
        Integer allOtherAmt = 0;

        // 本頁小計
        for (int i = 0; i < rowCount; i++) {
            MonthlyRpt33Case amtData = amtList.get(i);
            sumIssueAmt += amtData.getIssueAmt().intValue();
            sumOtheraAmt += amtData.getOtheraAmt().intValue();
            sumOtherbAmt += amtData.getOtherbAmt().intValue();
            sumMitRate += amtData.getMitRate().intValue();
            sumOffsetAmt += amtData.getOffsetAmt().intValue();
            sumCompenAmt += amtData.getCompenAmt().intValue();
            sumInherItorAmt += amtData.getInherItorAmt().intValue();
            sumItrtTax += amtData.getItrtTax().intValue();
            sumOtherAmt += amtData.getOtherAmt().intValue();
        }
        // 總累計
        for (int j = rowBeg; j < rowEnd; j++) {
            MonthlyRpt33Case caseData = caseList.get(j);
            if (caseData.getIssuTyp().equals(issuTyp) && caseData.getPayTyp().equals(payTyp)) {
                allIssueAmt += caseData.getIssueAmt().intValue();
                allOtheraAmt += caseData.getOtheraAmt().intValue();
                allOtherbAmt += caseData.getOtherbAmt().intValue();
                allMitRate += caseData.getMitRate().intValue();
                allOffsetAmt += caseData.getOffsetAmt().intValue();
                allCompenAmt += caseData.getCompenAmt().intValue();
                allInherItorAmt += caseData.getInherItorAmt().intValue();
                allItrtTax += caseData.getItrtTax().intValue();
                allOtherAmt += caseData.getOtherAmt().intValue();
            }
        }

        totalIssueAmt = totalIssueAmt + sumIssueAmt;
        totalOtheraAmt = totalOtheraAmt + sumOtheraAmt;
        totalOtherbAmt = totalOtherbAmt + sumOtherbAmt;
        totalMitRate = totalMitRate + sumMitRate;
        totalOffsetAmt = totalOffsetAmt + sumOffsetAmt;
        totalCompenAmt = totalCompenAmt + sumCompenAmt;
        totalInherItorAmt = totalInherItorAmt + sumInherItorAmt;
        totalItrtTax = totalItrtTax + sumItrtTax;
        totalOtherAmt = totalOtherAmt + sumOtherAmt;

        // 清空暫存
        amtList.clear();
        rowCount = 0;

        addColumn(table, 25, 0, "", fontCh9, 0, CENTER);
        addColumn(table, 4, 1, "本頁小計", fontCh9, 1, CENTER);
        addColumn(table, 5, 1, ReportBase.formatNumber(sumIssueAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOtheraAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOtherbAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumMitRate), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOffsetAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumCompenAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumInherItorAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumItrtTax), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOtherAmt), fontCh8, 1, RIGHT);

        addColumn(table, 25, 0, RptTitleUtility.getDivisionTitle(payCode, DateUtility.getNowWestDate()), fontCh9, 0, LEFT);
        // addColumn(table, 24, 0, DeptName + "給付科", fontCh9, 0, LEFT);
        addColumn(table, 4, 1, "總(累)計", fontCh9, 1, CENTER);
        /**
         * addColumn(table, 5, 1, ReportBase.formatNumber(allIssueAmt), fontCh8, 1, RIGHT); addColumn(table, 4, 1, ReportBase.formatNumber(allOtheraAmt), fontCh8, 1, RIGHT); addColumn(table, 4, 1, ReportBase.formatNumber(allOtherbAmt), fontCh8, 1, RIGHT); addColumn(table, 4, 1, ReportBase.formatNumber(allMitRate), fontCh8, 1, RIGHT); addColumn(table, 4, 1, ReportBase.formatNumber(allOffsetAmt), fontCh8, 1, RIGHT); addColumn(table, 4, 1, ReportBase.formatNumber(allCompenAmt), fontCh8, 1, RIGHT);
         * addColumn(table, 4, 1, ReportBase.formatNumber(allInherItorAmt), fontCh8, 1, RIGHT); addColumn(table, 4, 1, ReportBase.formatNumber(allItrtTax), fontCh8, 1, RIGHT); addColumn(table, 4, 1, ReportBase.formatNumber(allOtherAmt), fontCh8, 1, RIGHT);
         **/
        addColumn(table, 5, 1, ReportBase.formatNumber(totalIssueAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(totalOtheraAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(totalOtherbAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(totalMitRate), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(totalOffsetAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(totalCompenAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(totalInherItorAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(totalItrtTax), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(totalOtherAmt), fontCh8, 1, RIGHT);
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt33Case> caseData, List<MonthlyRpt33Case> caseData1, HashMap<String, Object> map) throws Exception {
        try {
            // 2009.2.20 swim add 輸出報表檔至主機上
            String payCode = (String) map.get("payCode");
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(ConstantKey.RPT_PATH + (String) map.get("payCode") + "_MonthlyRpt33_" + printDate + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt33_" + printDate + ".pdf");

            writer = PdfWriter.getInstance(document, bao);

            document.open();
            if (!(caseData == null) && caseData.size() > 0) {
                // 印主要報表 START - 【NACHGMK is null】
                // ================================================================================================================
                // 201205 start

                // 建立資料Map
                // Map的Key 為 issuYm+payYm
                // Value則是同為該 payTyp + nlWkMk + adWkMk 下的所有資料
                Map<String, List<MonthlyRpt33Case>> dataMap = new TreeMap<String, List<MonthlyRpt33Case>>();

                // Map<String, List<MonthlyRpt04Case>> dataMapFooter = new TreeMap<String, List<MonthlyRpt04Case>>(); //20120406
                for (int i = 0; i < caseData.size(); i++) {
                    MonthlyRpt33Case obj = caseData.get(i);
                    dataMap.put(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getPayTyp(), new ArrayList<MonthlyRpt33Case>());
                }

                for (int i = 0; i < caseData.size(); i++) {
                    MonthlyRpt33Case obj = caseData.get(i);
                    (dataMap.get(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getPayTyp())).add(obj);
                }

                for (Iterator it = dataMap.keySet().iterator(); it.hasNext();) {

                    // 201205 end

                    Table table = null;

                    String issuTyp = null; // 案件類別
                    String payTyp = null; // 給付方式
                    String deptName = null; // 部門名稱

                    List<MonthlyRpt33Case> amtList = new ArrayList<MonthlyRpt33Case>();

                    // 序號
                    Integer seqNo = 1;
                    // 計算每頁筆數
                    Integer rowCount = 0;
                    // 累計金額起始
                    Integer rowBeg = 0;

                    String key = (String) it.next();
                    List<MonthlyRpt33Case> caseList = dataMap.get(key);

                    // for (int i = 0; i < caseData.size(); i++) { // 受理案件資料
                    // MonthlyRpt33Case monthlyRpt33Case = caseData.get(i);
                    for (int i = 0; i < caseList.size(); i++) { // 受理案件資料
                        MonthlyRpt33Case monthlyRpt33Case = caseList.get(i);
                        BigDecimal rptPage = monthlyRpt33Case.getRptPage();
                        if (i == 0) {
                            // 取得表頭

                            table = getHeader(true, monthlyRpt33Case.getPayCodeStr(monthlyRpt33Case.getAdWkMk(), monthlyRpt33Case.getNlWkMk(), "N"), monthlyRpt33Case.getPayTypStr(), monthlyRpt33Case.getPayYm(), monthlyRpt33Case.getPayTyp(), monthlyRpt33Case.getMchkTypStr(), "N", map, payCode, monthlyRpt33Case.getcPrnDateStr(), rptPage);

                        }
                        else {

                            if (((rowCount) % 20 == 0) || !(monthlyRpt33Case.getIssuTyp()).equals(issuTyp) || !(monthlyRpt33Case.getPayTyp()).equals(payTyp)) {
                                // 不滿20筆補空白行

                                if (rowCount % 20 > 0)
                                    addEmptyRow(table, 20 - (rowCount % 20));

                                // 加入表尾
                                addFooter(table, monthlyRpt33Case.getDeptNameString(), amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, i, payCode);
                                document.add(table);

                                // 取得表頭
                                table = getHeader(true, monthlyRpt33Case.getPayCodeStr(monthlyRpt33Case.getAdWkMk(), monthlyRpt33Case.getNlWkMk(), "N"), monthlyRpt33Case.getPayTypStr(), monthlyRpt33Case.getPayYm(), monthlyRpt33Case.getPayTyp(), monthlyRpt33Case.getMchkTypStr(), "N", map, payCode, monthlyRpt33Case.getcPrnDateStr(), rptPage);
                                if (!(monthlyRpt33Case.getIssuTyp()).equals(issuTyp) || !(monthlyRpt33Case.getPayTyp()).equals(payTyp)) {
                                    // 序號歸零
                                    seqNo = 1;
                                    rowBeg = i;
                                }
                                rowCount = 0;
                            }
                        }

                        // 案件類別
                        issuTyp = monthlyRpt33Case.getIssuTyp();
                        // 給付方式
                        payTyp = monthlyRpt33Case.getPayTyp();
                        // 部門名稱
                        deptName = monthlyRpt33Case.getDeptNameString();

                        // 序號
                        addColumn(table, 2, 1, Integer.toString(seqNo), fontCh9, 1, CENTER);
                        // 受理編號
                        addColumn(table, 7, 1, monthlyRpt33Case.getApNoStr(), fontCh9, 1, CENTER);
                        // 給付年月
                        addColumn(table, 3, 1, DateUtility.changeWestYearMonthType(monthlyRpt33Case.getPayYm()), fontCh9, 1, CENTER);
                        // 金融機構轉帳帳號
                        if (payTyp.equals(ConstantKey.BAAPPBASE_PAYTYP_STR_A))
                            addColumn(table, 9, 1, monthlyRpt33Case.getBenIdn(), fontCh9, 1, CENTER);
                        else
                            addColumn(table, 9, 1, monthlyRpt33Case.getPayBankIdStr(), fontCh9, 1, CENTER);
                        // 被保險人姓名
                        addColumn(table, 4, 1, monthlyRpt33Case.getEvtName(), fontCh9, 1, CENTER);
                        // 受款人姓名
                        addColumn(table, 4, 1, monthlyRpt33Case.getBenName(), fontCh9, 1, CENTER);
                        // 核定金額(A)
                        addColumn(table, 5, 1, ReportBase.formatNumber(monthlyRpt33Case.getIssueAmt().longValue()), fontCh9, 1, RIGHT);
                        // 另案扣減同保險(B)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getOtheraAmt().longValue()), fontCh9, 1, RIGHT);
                        // 另案扣減他保險(C)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getOtherbAmt().longValue()), fontCh9, 1, RIGHT);
                        // 匯費(D)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getMitRate().longValue()), fontCh9, 1, RIGHT);
                        // 抵銷紓困(E)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getOffsetAmt().longValue()), fontCh9, 1, RIGHT);
                        // 代扣補償金(F)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getCompenAmt().longValue()), fontCh9, 1, RIGHT);
                        // 保留遺屬津貼(G)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getInherItorAmt().longValue()), fontCh9, 1, RIGHT);
                        // 代扣利息所得稅(H)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getItrtTax().longValue()), fontCh9, 1, RIGHT);
                        // 其他(I)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getOtherAmt().longValue()), fontCh9, 1, RIGHT);

                        // 計算本頁小計用
                        amtList.add(monthlyRpt33Case);

                        seqNo++;
                        rowCount++;

                        /*
                         * addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); //addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數 { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); //table.deleteLastRow(); // 不滿20筆補空白行 if(rowCount
                         * % 20 > 0) addEmptyRow(table, 20 - (rowCount % 20)); // 加入表尾 addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, i); document.add(table); // 加入表頭 table = getHeader(true, monthlyRpt33Case.getPayCodeStr(), monthlyRpt33Case.getIssuTypStr(), monthlyRpt33Case.getPayTypStr(), monthlyRpt33Case.getPayYm(), monthlyRpt33Case.getPayTyp(), map); rowCount = 0; } else { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow();
                         * table.deleteLastRow(); table.deleteLastRow(); //table.deleteLastRow(); }
                         */
                    } // for(int i=0;i<caseData.size();i++) end

                    // 不滿20筆補空白行
                    if (rowCount % 20 > 0) {
                        addEmptyRow(table, 20 - (rowCount % 20));

                        // 加入表尾
                        addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, caseData.size(), payCode);
                        document.add(table);
                    }
                    else {
                        // 加入表尾
                        addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, caseData.size(), payCode);
                        document.add(table);
                    }
                    /*
                     * if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數 { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); //table.deleteLastRow(); // 不滿20筆補空白行 if(rowCount % 20 > 0) addEmptyRow(table, 20 - (rowCount % 20)); // 加入表尾 addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, caseData.size()); document.add(table); } else { document.add(table); }
                     */

                } // 201205 分頁
                  // ================================================================================================================
                  // 印主要報表 END - 【NACHGMK is null】
            }
            if (!(caseData1 == null) && caseData1.size() > 0) {
                // 印主要報表 START - 【NACHGMK is not null】
                // ================================================================================================================
                // 201205 start
                // 建立資料Map
                // Map的Key 為 issuYm+payYm
                // Value則是同為該 payTyp + nlWkMk + adWkMk 下的所有資料
                Map<String, List<MonthlyRpt33Case>> dataMap1 = new TreeMap<String, List<MonthlyRpt33Case>>();

                // Map<String, List<MonthlyRpt04Case>> dataMapFooter = new TreeMap<String, List<MonthlyRpt04Case>>(); //20120406
                for (int i = 0; i < caseData1.size(); i++) {
                    MonthlyRpt33Case obj = caseData1.get(i);
                    dataMap1.put(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getPayTyp(), new ArrayList<MonthlyRpt33Case>());
                }

                for (int i = 0; i < caseData1.size(); i++) {
                    MonthlyRpt33Case obj = caseData1.get(i);
                    (dataMap1.get(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getPayTyp())).add(obj);
                }

                for (Iterator it = dataMap1.keySet().iterator(); it.hasNext();) {

                    // 201205 end

                    Table table = null;

                    String issuTyp = null; // 案件類別
                    String payTyp = null; // 給付方式
                    String deptName = null; // 部門名稱

                    List<MonthlyRpt33Case> amtList = new ArrayList<MonthlyRpt33Case>();

                    // 序號
                    Integer seqNo = 1;
                    // 計算每頁筆數
                    Integer rowCount = 0;
                    // 累計金額起始
                    Integer rowBeg = 0;

                    String key = (String) it.next();
                    List<MonthlyRpt33Case> caseList = dataMap1.get(key);

                    // for (int i = 0; i < caseData.size(); i++) { // 受理案件資料
                    // MonthlyRpt33Case monthlyRpt33Case = caseData.get(i);
                    for (int i = 0; i < caseList.size(); i++) { // 受理案件資料
                        MonthlyRpt33Case monthlyRpt33Case = caseList.get(i);
                        BigDecimal rptPage = monthlyRpt33Case.getRptPage();
                        if (i == 0) {
                            // 取得表頭

                            table = getHeader(true, monthlyRpt33Case.getPayCodeStr(monthlyRpt33Case.getAdWkMk(), monthlyRpt33Case.getNlWkMk(), "Y"), monthlyRpt33Case.getPayTypStr(), monthlyRpt33Case.getPayYm(), monthlyRpt33Case.getPayTyp(), monthlyRpt33Case.getMchkTypStr(), "Y", map, payCode, monthlyRpt33Case.getcPrnDateStr(), rptPage);

                        }
                        else {

                            if (((rowCount) % 20 == 0) || !(monthlyRpt33Case.getIssuTyp()).equals(issuTyp) || !(monthlyRpt33Case.getPayTyp()).equals(payTyp)) {
                                // 不滿20筆補空白行

                                if (rowCount % 20 > 0)
                                    addEmptyRow(table, 20 - (rowCount % 20));

                                // 加入表尾
                                addFooter(table, monthlyRpt33Case.getDeptNameString(), amtList, rowCount, caseData1, issuTyp, payTyp, rowBeg, i, payCode);
                                document.add(table);

                                // 取得表頭
                                table = getHeader(true, monthlyRpt33Case.getPayCodeStr(monthlyRpt33Case.getAdWkMk(), monthlyRpt33Case.getNlWkMk(), "Y"), monthlyRpt33Case.getPayTypStr(), monthlyRpt33Case.getPayYm(), monthlyRpt33Case.getPayTyp(), monthlyRpt33Case.getMchkTypStr(), "Y", map, payCode, monthlyRpt33Case.getcPrnDateStr(), rptPage);
                                if (!(monthlyRpt33Case.getIssuTyp()).equals(issuTyp) || !(monthlyRpt33Case.getPayTyp()).equals(payTyp)) {
                                    // 序號歸零
                                    seqNo = 1;
                                    rowBeg = i;
                                }
                                rowCount = 0;
                            }
                        }

                        // 案件類別
                        issuTyp = monthlyRpt33Case.getIssuTyp();
                        // 給付方式
                        payTyp = monthlyRpt33Case.getPayTyp();
                        // 部門名稱
                        deptName = monthlyRpt33Case.getDeptNameString();

                        // 序號
                        addColumn(table, 2, 1, Integer.toString(seqNo), fontCh9, 1, CENTER);
                        // 受理編號
                        addColumn(table, 7, 1, monthlyRpt33Case.getApNoStr(), fontCh9, 1, CENTER);
                        // 給付年月
                        addColumn(table, 3, 1, DateUtility.changeWestYearMonthType(monthlyRpt33Case.getPayYm()), fontCh9, 1, CENTER);
                        // 金融機構轉帳帳號
                        if (payTyp.equals(ConstantKey.BAAPPBASE_PAYTYP_STR_A))
                            addColumn(table, 9, 1, monthlyRpt33Case.getBenIdn(), fontCh9, 1, CENTER);
                        else
                            addColumn(table, 9, 1, monthlyRpt33Case.getPayAccount(), fontCh9, 1, CENTER);
                        // 被保險人姓名
                        addColumn(table, 4, 1, monthlyRpt33Case.getEvtName(), fontCh9, 1, CENTER);
                        // 受款人姓名
                        addColumn(table, 4, 1, monthlyRpt33Case.getBenName(), fontCh9, 1, CENTER);
                        // 核定金額(A)
                        addColumn(table, 5, 1, ReportBase.formatNumber(monthlyRpt33Case.getIssueAmt().longValue()), fontCh9, 1, RIGHT);
                        // 另案扣減同保險(B)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getOtheraAmt().longValue()), fontCh9, 1, RIGHT);
                        // 另案扣減他保險(C)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getOtherbAmt().longValue()), fontCh9, 1, RIGHT);
                        // 匯費(D)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getMitRate().longValue()), fontCh9, 1, RIGHT);
                        // 抵銷紓困(E)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getOffsetAmt().longValue()), fontCh9, 1, RIGHT);
                        // 代扣補償金(F)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getCompenAmt().longValue()), fontCh9, 1, RIGHT);
                        // 保留遺屬津貼(G)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getInherItorAmt().longValue()), fontCh9, 1, RIGHT);
                        // 代扣利息所得稅(H)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getItrtTax().longValue()), fontCh9, 1, RIGHT);
                        // 其他(I)
                        addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt33Case.getOtherAmt().longValue()), fontCh9, 1, RIGHT);

                        // 計算本頁小計用
                        amtList.add(monthlyRpt33Case);

                        seqNo++;
                        rowCount++;

                        /*
                         * addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); //addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數 { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); //table.deleteLastRow(); // 不滿20筆補空白行 if(rowCount
                         * % 20 > 0) addEmptyRow(table, 20 - (rowCount % 20)); // 加入表尾 addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, i); document.add(table); // 加入表頭 table = getHeader(true, monthlyRpt33Case.getPayCodeStr(), monthlyRpt33Case.getIssuTypStr(), monthlyRpt33Case.getPayTypStr(), monthlyRpt33Case.getPayYm(), monthlyRpt33Case.getPayTyp(), map); rowCount = 0; } else { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow();
                         * table.deleteLastRow(); table.deleteLastRow(); //table.deleteLastRow(); }
                         */
                    } // for(int i=0;i<caseData.size();i++) end

                    // 不滿20筆補空白行
                    if (rowCount % 20 > 0) {
                        addEmptyRow(table, 20 - (rowCount % 20));

                        // 加入表尾
                        addFooter(table, deptName, amtList, rowCount, caseData1, issuTyp, payTyp, rowBeg, caseData1.size(), payCode);
                        document.add(table);
                    }
                    else {
                        // 加入表尾
                        addFooter(table, deptName, amtList, rowCount, caseData1, issuTyp, payTyp, rowBeg, caseData1.size(), payCode);
                        document.add(table);
                    }
                    /*
                     * if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數 { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); //table.deleteLastRow(); // 不滿20筆補空白行 if(rowCount % 20 > 0) addEmptyRow(table, 20 - (rowCount % 20)); // 加入表尾 addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, caseData.size()); document.add(table); } else { document.add(table); }
                     */

                } // 201205 分頁
                  // ================================================================================================================
                  // 印主要報表 END - 【NACHGMK is not null】
            }
            document.close();
        }
        finally {
            document.close();
        }
        return bao;
    }
}
