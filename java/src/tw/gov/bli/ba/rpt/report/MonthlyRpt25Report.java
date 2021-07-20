package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.owasp.encoder.Encode;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt25Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.BaBusinessUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.common.helper.SpringHelper;

/**
 * 退匯/現應收已收核定清單
 * 
 * @author Noctis
 */
public class MonthlyRpt25Report extends ReportBase {

    private RptService rptService;

    public MonthlyRpt25Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);
        return document;
    }

    public Table getHeader(boolean bPrintMainHeader, String payCode, String payTypStr, String chkDate, String pageNum, String cPrnDate, String naChgMk, String nlWkMk, String adWkMk, String isNaChgMk, String receiveType) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(99);
        table.setAutoFillEmptyCells(true);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        addColumn(table, 99, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 99, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()), fontCh12b, 0, CENTER);
            addColumn(table, 99, 1, RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh10, 0, CENTER);
            // addColumn(table, 99, 1, "勞工保險局", fontCh12b, 0, CENTER);
            // addColumn(table, 99, 1, "給付處", fontCh10, 0, CENTER);
            if (receiveType.equals("1")) {
                addColumn(table, 99, 1, "退匯應收已收核定清單", fontCh10, 0, CENTER);
            }
            else {
                addColumn(table, 99, 1, "退現應收已收核定清單", fontCh10, 0, CENTER);
            }
            if (!("").equals(payTypStr)) {
                addColumn(table, 99, 1, "[" + payTypStr + "]", fontCh10, 0, CENTER);
            }

            // String payCodeStr = "";
            // if (("L").equals(payCode)) {
            // payCodeStr = "普通-老年給付(年金)";
            // }
            // else if (("K").equals(payCode)) {
            // payCodeStr = "○○-失能給付(年金)";
            // }
            // else if (("S").equals(payCode)) {
            // payCodeStr = "○○-遺屬給付(年金)";
            // }
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

            addColumn(table, 99, 1, "給付別：" + payCodeStr, fontCh10, 0, LEFT);

            addColumn(table, 63, 1, "核定日期：" + DateUtility.formatChineseDateTimeString(chkDate, true), fontCh10, 0, LEFT);
            addColumn(table, 24, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(cPrnDate, true), fontCh10, 0, LEFT);
            // addColumn(table, 7, 1, "第" + StringUtility.chtLeftPad(String.valueOf(pageNum + 500000), 6, "0") + "頁", fontCh10, 0, LEFT);
            if (StringUtils.isBlank(pageNum)) {
                addColumn(table, 12, 1, "", fontCh10, 0, LEFT);// 20130731
            }
            else {
                addColumn(table, 12, 1, "第" + pageNum + "頁", fontCh10, 0, LEFT);// 20130731
            }

        }

        addColumn(table, 4, 1, "序號", fontCh10, 1, CENTER);
        addColumn(table, 5, 1, "件數", fontCh10, 1, CENTER);
        addColumn(table, 5, 1, "筆數", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "核定金額(A)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "另案扣減同保險(B)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "另案扣減他保險(C)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "匯費(D)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "抵銷紓困(E)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "代扣補償金(F)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "保留遺屬津貼(G)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "代扣利息所得稅(H)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "其他(I)", fontCh10, 1, CENTER);
        addColumn(table, 8, 1, "應付金額=(A)-(B)-(C)-(D)-(E)-(F)-(G)-(H)-(I)", fontCh10, 1, CENTER);
        addColumn(table, 5, 1, "備註", fontCh10, 1, CENTER);

        return table;
    }

    public Table addFooter(Table table, String payCode, String payTyp, BigDecimal tissueAmt) throws Exception {
        table.setBorderWidth(1);

        int rowCount = 0;

        // 會計科目 - 借
        // 核定金額
        addColumn(table, 86, 1, "借：110212  0053  2銀行存款-土地銀行台北分行13986-2帳戶", fontCh10, 0, LEFT);
        addColumn(table, 8, 1, formatBigDecimalToInteger(tissueAmt), fontCh10, 0, RIGHT);
        addColumn(table, 5, 1, "", fontCh10, 0, CENTER);
        rowCount += 1;

        // 會計科目 - 貸(貸字前空六個空格)
        // 應付金額
        addColumn(table, 86, 1, "      貸：2822121 11111 0應付保管款-普通-老年給付(年金)", fontCh10, 0, LEFT);
        addColumn(table, 8, 1, formatBigDecimalToInteger(tissueAmt), fontCh10, 0, RIGHT);
        addColumn(table, 5, 1, "", fontCh10, 0, CENTER);
        rowCount += 1;

        // 補空白行
        for (int i = 0; i < 13 - rowCount; i++) {
            addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
        }

        addColumn(table, 30, 1, "經辦人員", fontCh10, 0, LEFT);
        addColumn(table, 69, 1, RptTitleUtility.getSafetyPaymentTitle3(DateUtility.getNowWestDate()) + "科長：", fontCh10, 0, LEFT);
        // addColumn(table, 69, 1, "出納科長：", fontCh10, 0, LEFT);
        addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);

        return table;
    }

    @SuppressWarnings("unchecked")
    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt25Case> caseData, List<MonthlyRpt25Case> caseDataFooter, HashMap<String, Object> map) throws Exception {
        try {
            // Integer pageNum = 1;// 頁數

            Table table = null;
            String payCode = (String) map.get("payCode");
            String receiveType = (String) map.get("receiveType");
            String issuYm = (String) map.get("issuYm");

            RptService rptService = (RptService) SpringHelper.getBeanById("rptService");

            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt11_" + Encode.forJava(printDate) + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt11_" + printDate + ".pdf");
            // writer = PdfWriter.getInstance(document, bao);

            document.open();

            // 建立資料Map
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 payTyp 下的所有資料
            Map<String, List<MonthlyRpt25Case>> dataMap = new TreeMap<String, List<MonthlyRpt25Case>>();
            Map<String, List<MonthlyRpt25Case>> dataMapFooter = new TreeMap<String, List<MonthlyRpt25Case>>(); // 20120406
            for (int i = 0; i < caseData.size(); i++) {
                MonthlyRpt25Case obj = caseData.get(i);
                dataMap.put(obj.getPayTyp(), new ArrayList<MonthlyRpt25Case>());
            }

            for (int i = 0; i < caseData.size(); i++) {
                MonthlyRpt25Case obj = caseData.get(i);
                (dataMap.get(obj.getPayTyp())).add(obj);
            }

            for (int i = 0; i < caseData.size(); i++) { // 20120416
                MonthlyRpt25Case obj = caseData.get(i);
                dataMapFooter.put(obj.getPayTyp(), new ArrayList<MonthlyRpt25Case>());
            }

            for (int i = 0; i < caseDataFooter.size(); i++) { // 20120416
                MonthlyRpt25Case obj = caseDataFooter.get(i);
                (dataMapFooter.get(obj.getPayTyp())).add(obj);
            }

            for (Iterator it = dataMap.keySet().iterator(); it.hasNext();) {
                Integer seqNo = 1;// 序號
                BigDecimal tapNoAmt = new BigDecimal(0);// 件數
                BigDecimal tdataAmt = new BigDecimal(0);// 筆數
                BigDecimal tissueAmt = new BigDecimal(0);// 核定金額
                BigDecimal totheraAmt = new BigDecimal(0);// 另案扣減(同類保險)金額
                BigDecimal totherbAmt = new BigDecimal(0);// 另案扣減(他類保險)金額
                BigDecimal tmitRate = new BigDecimal(0);// 匯款匯費
                BigDecimal toffsetAmt = new BigDecimal(0);// 抵銷紓困
                BigDecimal tcompenAmt = new BigDecimal(0);// 代扣補償金
                BigDecimal tinherItorAmt = new BigDecimal(0);// 保留遺屬津貼
                BigDecimal titrtTax = new BigDecimal(0);// 代扣利息所得稅
                BigDecimal totherAmt = new BigDecimal(0);// 其他
                BigDecimal tpayAmt = new BigDecimal(0);// 應付金額

                String key = (String) it.next();
                List<MonthlyRpt25Case> caseList = dataMap.get(key);
                MonthlyRpt25Case tmpCaseObj = caseList.get(0);

                // 拿取頁次 20130731
                List<MonthlyRpt25Case> caseListPageNum = dataMapFooter.get(key);
                String rptPage = caseListPageNum.get(0).getRptPage().toString();
                String eRptPage = caseListPageNum.get(0).geteRptPage().toString();
                String pageNum = "";
                // 判斷頁次
                if (rptPage.equals("0") && eRptPage.equals("0")) {
                    pageNum = "";
                }
                else {
                    if (eRptPage.equals(rptPage)) {
                        pageNum = rptPage;
                    }
                    else {
                        pageNum = rptPage + "~" + eRptPage;
                    }
                }

                table = getHeader(true, payCode, tmpCaseObj.getPayTypStr(), tmpCaseObj.getChkDateStr(), pageNum, tmpCaseObj.getcPrnDateStr(), tmpCaseObj.getNaChgMk(), tmpCaseObj.getNlWkMk(), tmpCaseObj.getAdWkMk(), tmpCaseObj.getIsNaChgMk(),
                                receiveType);
                for (MonthlyRpt25Case caseObj : caseList) {
                    BigDecimal payAmt = new BigDecimal(0);// 應付金額歸0
                    // 計算應付金額
                    payAmt = caseObj.getIssueAmt();// 應付金額 = 核定金額
                    payAmt = payAmt.subtract(caseObj.getOtheraAmt());
                    payAmt = payAmt.subtract(caseObj.getOtherbAmt());
                    payAmt = payAmt.subtract(caseObj.getMitRate());
                    payAmt = payAmt.subtract(caseObj.getOffsetAmt());
                    payAmt = payAmt.subtract(caseObj.getCompenAmt());
                    payAmt = payAmt.subtract(caseObj.getInherItorAmt());
                    payAmt = payAmt.subtract(caseObj.getItrtTax());
                    payAmt = payAmt.subtract(caseObj.getOtherAmt());

                    // 計算合計資料
                    tapNoAmt = tapNoAmt.add(caseObj.getApNoAmt());// 件數
                    tdataAmt = tdataAmt.add(caseObj.getDataAmt());// 筆數
                    tissueAmt = tissueAmt.add(caseObj.getIssueAmt());// 核定金額
                    totheraAmt = totheraAmt.add(caseObj.getOtheraAmt());// 另案扣減(同類保險)金額
                    totherbAmt = totherbAmt.add(caseObj.getOtherbAmt());// 另案扣減(他類保險)金額
                    tmitRate = tmitRate.add(caseObj.getMitRate());// 匯款匯費
                    toffsetAmt = toffsetAmt.add(caseObj.getOffsetAmt());// 抵銷紓困
                    tcompenAmt = tcompenAmt.add(caseObj.getCompenAmt());// 代扣補償金
                    tinherItorAmt = tinherItorAmt.add(caseObj.getInherItorAmt());// 保留遺屬津貼
                    titrtTax = titrtTax.add(caseObj.getItrtTax());// 代扣利息所得稅
                    totherAmt = totherAmt.add(caseObj.getOtherAmt());// 其他
                    tpayAmt = tpayAmt.add(payAmt);// 應付金額

                    addColumn(table, 4, 1, Integer.toString(seqNo), fontCh10, 1, CENTER);
                    addColumn(table, 5, 1, formatBigDecimalToInteger(caseObj.getApNoAmt()), fontCh10, 1, RIGHT);
                    addColumn(table, 5, 1, formatBigDecimalToInteger(caseObj.getDataAmt()), fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getIssueAmt()), fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getOtheraAmt()), fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getOtherbAmt()), fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getMitRate()), fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getOffsetAmt()), fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getCompenAmt()), fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getInherItorAmt()), fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getItrtTax()), fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getOtherAmt()), fontCh10, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(payAmt), fontCh10, 1, RIGHT);
                    addColumn(table, 5, 1, caseObj.getMchkTypStr(), fontCh8, 1, CENTER);

                    seqNo++;
                }
                int dataSize = caseList.size();
                if (dataSize < 7) {
                    for (int i = 0; i < (7 - dataSize); i++) {
                        addColumn(table, 4, 1, "　", fontCh10, 1, CENTER);
                        addColumn(table, 5, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 5, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh10, 1, RIGHT);
                        addColumn(table, 5, 1, "", fontCh10, 1, CENTER);
                    }
                }

                addColumn(table, 4, 1, "合計", fontCh10, 1, CENTER);
                addColumn(table, 5, 1, formatBigDecimalToInteger(tapNoAmt), fontCh10, 1, RIGHT);
                addColumn(table, 5, 1, formatBigDecimalToInteger(tdataAmt), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(tissueAmt), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(totheraAmt), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(totherbAmt), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(tmitRate), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(toffsetAmt), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(tcompenAmt), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(tinherItorAmt), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(titrtTax), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(totherAmt), fontCh10, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(tpayAmt), fontCh10, 1, RIGHT);
                addColumn(table, 5, 1, "", fontCh10, 1, CENTER);

                // table = addFooter(table, payCode, key, tissueAmt);
                // 20120406 begin
                table.setBorderWidth(1);
                int rowCount = 0;
                List<MonthlyRpt25Case> caseList1 = dataMapFooter.get(key);
                for (MonthlyRpt25Case caseObj1 : caseList1) {

                    if ((caseObj1.getAccountSeq().toString()).equals("0")) {
                        addColumn(table, 4, 1, "借：", fontCh10, 0, LEFT);
                        addColumn(table, 12, 1, caseObj1.getAccountNo(), fontCh10, 0, LEFT);
                        addColumn(table, 70, 1, caseObj1.getAccountName(), fontCh10, 0, LEFT);
                        addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj1.getPayAmt()), fontCh10, 0, RIGHT);
                        addColumn(table, 5, 1, "", fontCh10, 0, CENTER);
                        rowCount += 1;
                    }
                    else if ((caseObj1.getAccountSeq().toString()).equals("1")) {

                        String chkMk = "";
                        if (caseObj1.getAccountNo().substring(0, 10).equals("212312  12")) {
                            chkMk = BaBusinessUtility.getAccountChkMk(caseObj1.getAccountNo() + ".");
                        }

                        addColumn(table, 4, 1, "　　", fontCh10, 0, LEFT);
                        addColumn(table, 4, 1, "貸：", fontCh10, 0, LEFT);
                        addColumn(table, 12, 1, caseObj1.getAccountNo() + " " + chkMk, fontCh10, 0, LEFT);
                        addColumn(table, 66, 1, caseObj1.getAccountName(), fontCh10, 0, LEFT);
                        addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj1.getPayAmt()), fontCh10, 0, RIGHT);
                        addColumn(table, 5, 1, "", fontCh10, 0, CENTER);
                        rowCount += 1;

                    }

                    seqNo++;
                }

                // 補空白行
                for (int i = 0; i < 13 - rowCount; i++) {
                    addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
                }

                addColumn(table, 30, 1, "經辦人員", fontCh10, 0, LEFT);
                addColumn(table, 69, 1, RptTitleUtility.getSafetyPaymentTitle3(DateUtility.getNowWestDate()) + "科長：", fontCh10, 0, LEFT);
                // addColumn(table, 69, 1, "出納科長：", fontCh10, 0, LEFT);
                addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
                // 20120406 end

                document.add(table);
                // pageNum++;
            }
            document.close();
            // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
            printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt25_" + Encode.forJava(printDate) + ".pdf"), bao);
        }
        finally {
            document.close();
        }
        return bao;
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }

}
