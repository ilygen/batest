package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.owasp.encoder.Encode;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt14Case;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;

/**
 * 改匯清冊
 * 
 * @author swim
 */
public class MonthlyRpt14Report extends ReportBase {

    public MonthlyRpt14Report() throws Exception {
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
            addColumn(table, 3, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 7, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 3, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 11, 1, "　", fontCh10, 1, CENTER);
            addColumn(table, 5, 1, "　", fontCh10, 1, CENTER);
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

    public Table getHeader(boolean bPrintMainHeader, String mchkTypStr, String payTypStr, String payYm, String payTyp, HashMap<String, Object> map, String payCode, String cPrnDate, String naChgMk, String nlWkMk, String adWkMk, String isNaChgMk,
                    BigDecimal rptPage) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(66);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 66, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 52, 1, "報表編號：BALP0D3E0", fontCh10, 0, LEFT);
            addColumn(table, 14, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 38, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
            // addColumn(table, 38, 1, "勞工保險局 給付處", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 38, 1, "改匯清冊", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);

            String payCodeStr = "";
            String isNaChgMkStr = "";

            if ("Y".equals(isNaChgMk)) {
                if ("12".equals(naChgMk)) {
                    isNaChgMkStr = " - 普改職";
                }
                else if ("13".equals(naChgMk)) {
                    isNaChgMkStr = " - 普改加職";
                }
                else if ("21".equals(naChgMk)) {
                    isNaChgMkStr = " - 職改普";
                }
                else if ("23".equals(naChgMk)) {
                    isNaChgMkStr = " - 職改加職";
                }
                else if ("31".equals(naChgMk)) {
                    isNaChgMkStr = " - 加職改普";
                }
                else if ("32".equals(naChgMk)) {
                    isNaChgMkStr = " - 加職改職";
                }
            }
            else if ("N".equals(isNaChgMk)) {
                isNaChgMkStr = "";
            }

            if (("L").equals(payCode)) {
                payCodeStr = "普通-老年給付(年金)";
            }
            else if (("K").equals(payCode)) {
                // payCodeStr = "○○-失能給付(年金)";
                if (("1").equals(nlWkMk)) {
                    payCodeStr = "普通-失能給付(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("1").equals(adWkMk)) {
                    payCodeStr = "職災-失能給付(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("2").equals(adWkMk)) {
                    payCodeStr = "加職-失能給付(年金)" + isNaChgMkStr;
                }
                else {
                    payCodeStr = "普通-失能給付(年金)";
                }

            }
            else if (("S").equals(payCode)) {
                // payCodeStr = "○○-遺屬給付(年金)";
                if (("1").equals(nlWkMk)) {
                    payCodeStr = "普通-遺屬給付(年金)";
                }
                else if (("2").equals(nlWkMk) && ("1").equals(adWkMk)) {
                    payCodeStr = "職災-遺屬給付(年金)";
                }
                else if (("2").equals(nlWkMk) && ("2").equals(adWkMk)) {
                    payCodeStr = "加職-遺屬給付(年金)";
                }
                else {
                    payCodeStr = "普通-遺屬給付(年金)";
                }
            }

            addColumn(table, 30, 1, "給 付 別：" + payCodeStr, fontCh10, 0, LEFT);
            addColumn(table, 28, 1, "類別：" + mchkTypStr, fontCh10, 0, LEFT);
            addColumn(table, 8, 1, "給付方式：" + payTypStr, fontCh10, 0, LEFT);

            addColumn(table, 30, 1, "核定年月：" + map.get("issuYm"), fontCh10, 0, LEFT);
            addColumn(table, 28, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(cPrnDate, false), fontCh10, 0, LEFT);
            addColumn(table, 8, 1, "頁　　次：" + rptPage, fontCh10, 0, LEFT);

        }

        addColumn(table, 3, 1, "序號", fontCh9, 1, CENTER);
        addColumn(table, 7, 1, "受理編號", fontCh9, 1, CENTER);
        addColumn(table, 3, 1, "給付\n年月", fontCh9, 1, CENTER);
        if (payTyp.equals(ConstantKey.BAAPPBASE_PAYTYP_A))
            addColumn(table, 11, 1, "受款人身份證字號", fontCh9, 1, CENTER);
        else
            addColumn(table, 11, 1, "金融機構轉帳帳號", fontCh9, 1, CENTER);
        addColumn(table, 5, 1, "受款人\n姓　名", fontCh9, 1, CENTER);
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

    public void addFooter(Table table, String DeptName, List<MonthlyRpt14Case> amtList, Integer rowCount, List<MonthlyRpt14Case> caseList, String issuTyp, String payTyp, Integer rowBeg, Integer rowEnd) throws Exception {
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
            MonthlyRpt14Case amtData = amtList.get(i);
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
            MonthlyRpt14Case caseData = caseList.get(j);
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

        // 清空暫存
        amtList.clear();
        rowCount = 0;

        addColumn(table, 24, 0, "", fontCh9, 0, CENTER);
        addColumn(table, 5, 1, "本頁小計", fontCh9, 1, CENTER);
        addColumn(table, 5, 1, ReportBase.formatNumber(sumIssueAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOtheraAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOtherbAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumMitRate), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOffsetAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumCompenAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumInherItorAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumItrtTax), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(sumOtherAmt), fontCh8, 1, RIGHT);

        addColumn(table, 24, 0, RptTitleUtility.getSafetyPaymentTitle1(DateUtility.getNowWestDate()), fontCh9, 0, LEFT);
        // addColumn(table, 24, 0, "給付出納科", fontCh9, 0, LEFT);
        addColumn(table, 5, 1, "總(累)計", fontCh9, 1, CENTER);
        addColumn(table, 5, 1, ReportBase.formatNumber(allIssueAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allOtheraAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allOtherbAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allMitRate), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allOffsetAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allCompenAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allInherItorAmt), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allItrtTax), fontCh8, 1, RIGHT);
        addColumn(table, 4, 1, ReportBase.formatNumber(allOtherAmt), fontCh8, 1, RIGHT);
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt14Case> caseData, HashMap<String, Object> map) throws Exception {
        try {
            String payCode = (String) map.get("payCode");

            // 2009.2.20 swim add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt14_" + Encode.forJava(printDate) + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt14_" + printDate + ".pdf");
            // writer = PdfWriter.getInstance(document, bao);

            document.open();

            Table table = null;

            String issuTyp = null; // 案件類別
            String payTyp = null; // 給付方式
            String deptName = null; // 部門名稱

            List<MonthlyRpt14Case> amtList = new ArrayList<MonthlyRpt14Case>();

            // 序號
            Integer seqNo = 1;
            // 計算每頁筆數
            Integer rowCount = 0;
            // 累計金額起始
            Integer rowBeg = 0;

            for (int i = 0; i < caseData.size(); i++) { // 受理案件資料
                MonthlyRpt14Case monthlyRpt14Case = caseData.get(i);
                BigDecimal pageNum = monthlyRpt14Case.getRptPage();
                if (i == 0) {
                    // 取得表頭
                    table = getHeader(true, monthlyRpt14Case.getIssuTypStr(), monthlyRpt14Case.getPayTypStr(), monthlyRpt14Case.getPayYm(), monthlyRpt14Case.getPayTyp(), map, payCode, monthlyRpt14Case.getcPrnDateStr(), monthlyRpt14Case.getNaChgMk(),
                                    monthlyRpt14Case.getNlWkMk(), monthlyRpt14Case.getAdWkMk(), monthlyRpt14Case.getIsNaChgMk(), pageNum);
                }
                else {
                    if (((rowCount) % 20 == 0) || !(monthlyRpt14Case.getIssuTyp()).equals(issuTyp) || !(monthlyRpt14Case.getPayTyp()).equals(payTyp)) {
                        // 不滿20筆補空白行
                        if (rowCount % 20 > 0)
                            addEmptyRow(table, 20 - (rowCount % 20));

                        // 加入表尾
                        addFooter(table, monthlyRpt14Case.getDeptNameString(), amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, i);
                        document.add(table);

                        // 取得表頭
                        table = getHeader(true, monthlyRpt14Case.getIssuTypStr(), monthlyRpt14Case.getPayTypStr(), monthlyRpt14Case.getPayYm(), monthlyRpt14Case.getPayTyp(), map, payCode, monthlyRpt14Case.getcPrnDateStr(),
                                        monthlyRpt14Case.getNaChgMk(), monthlyRpt14Case.getNlWkMk(), monthlyRpt14Case.getAdWkMk(), monthlyRpt14Case.getIsNaChgMk(), pageNum);

                        if (!(monthlyRpt14Case.getIssuTyp()).equals(issuTyp) || !(monthlyRpt14Case.getPayTyp()).equals(payTyp)) {
                            // 序號歸零
                            seqNo = 1;
                            rowBeg = i;
                        }

                        rowCount = 0;
                    }
                }
                // 案件類別
                issuTyp = monthlyRpt14Case.getIssuTyp();
                // 給付方式
                payTyp = monthlyRpt14Case.getPayTyp();
                // 部門名稱
                deptName = monthlyRpt14Case.getDeptNameString();

                // 序號
                addColumn(table, 3, 1, Integer.toString(seqNo), fontCh9, 1, CENTER);
                // 受理編號
                addColumn(table, 7, 1, monthlyRpt14Case.getApNoStr(), fontCh9, 1, CENTER);
                // 給付年月
                addColumn(table, 3, 1, DateUtility.changeWestYearMonthType(monthlyRpt14Case.getPayYm()), fontCh9, 1, CENTER);
                // 金融機構轉帳帳號
                if (payTyp.equals(ConstantKey.BAAPPBASE_PAYTYP_STR_A))
                    addColumn(table, 11, 1, monthlyRpt14Case.getBenIdn(), fontCh9, 1, CENTER);
                else
                    addColumn(table, 11, 1, monthlyRpt14Case.getPayBankIdStr(), fontCh9, 1, CENTER);
                // 受款人姓名
                addColumn(table, 5, 1, monthlyRpt14Case.getBenName(), fontCh9, 1, CENTER);
                // 核定金額(A)
                addColumn(table, 5, 1, ReportBase.formatNumber(monthlyRpt14Case.getIssueAmt().longValue()), fontCh9, 1, RIGHT);
                // 另案扣減同保險(B)
                addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt14Case.getOtheraAmt().longValue()), fontCh9, 1, RIGHT);
                // 另案扣減他保險(C)
                addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt14Case.getOtherbAmt().longValue()), fontCh9, 1, RIGHT);
                // 匯費(D)
                addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt14Case.getMitRate().longValue()), fontCh9, 1, RIGHT);
                // 抵銷紓困(E)
                addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt14Case.getOffsetAmt().longValue()), fontCh9, 1, RIGHT);
                // 代扣補償金(F)
                addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt14Case.getCompenAmt().longValue()), fontCh9, 1, RIGHT);
                // 保留遺屬津貼(G)
                addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt14Case.getInherItorAmt().longValue()), fontCh9, 1, RIGHT);
                // 代扣利息所得稅(H)
                addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt14Case.getItrtTax().longValue()), fontCh9, 1, RIGHT);
                // 其他(I)
                addColumn(table, 4, 1, ReportBase.formatNumber(monthlyRpt14Case.getOtherAmt().longValue()), fontCh9, 1, RIGHT);

                // 計算本頁小計用
                amtList.add(monthlyRpt14Case);

                seqNo++;
                rowCount++;

                /*
                 * addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); addColumn(table, 66, 1, "　" , fontCh10, 1, CENTER); //addColumn(table, 66,
                 * 1, "　" , fontCh10, 1, CENTER); if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數 { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); //table.deleteLastRow(); // 不滿20筆補空白行
                 * if(rowCount % 20 > 0) addEmptyRow(table, 20 - (rowCount % 20)); // 加入表尾 addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, i); document.add(table); // 加入表頭 table = getHeader(true,
                 * monthlyRpt14Case.getPayCodeStr(), monthlyRpt14Case.getIssuTypStr(), monthlyRpt14Case.getPayTypStr(), monthlyRpt14Case.getPayYm(), monthlyRpt14Case.getPayTyp(), map); rowCount = 0; } else { // 刪除造成換頁的該筆資料 (一筆有一行)
                 * table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); //table.deleteLastRow(); }
                 */

            } // for(int i=0;i<caseData.size();i++) end

            // 不滿20筆補空白行
            if (rowCount % 20 > 0)
                addEmptyRow(table, 20 - (rowCount % 20));

            // 加入表尾
            addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, caseData.size());
            /*
             * if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數 { // 刪除造成換頁的該筆資料 (一筆有一行) table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); table.deleteLastRow(); //table.deleteLastRow(); // 不滿20筆補空白行 if(rowCount % 20 > 0)
             * addEmptyRow(table, 20 - (rowCount % 20)); // 加入表尾 addFooter(table, deptName, amtList, rowCount, caseData, issuTyp, payTyp, rowBeg, caseData.size()); document.add(table); } else { document.add(table); }
             */
            document.add(table);
            document.close();
            // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
            printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt13_" + Encode.forJava(printDate) + ".pdf"), bao);
        }
        finally {
            document.close();
        }
        return bao;
    }
}
