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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt19Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.BaBusinessUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.common.helper.SpringHelper;

/**
 * 應收已收核定清單
 * 
 * @author Rickychi
 */
public class MonthlyRpt19Report extends ReportBase {

    private RptService rptService;

    public MonthlyRpt19Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);
        return document;
    }

    public Table getHeader(boolean bPrintMainHeader, String payCode, String issuTypStr, String chkDate, String pageNum, String naChgMk, String nlWkMk, String adWkMk, String isNaChgMk, String cPrnDate) throws Exception {
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
            addColumn(table, 99, 1, "應收已收核定清單(" + issuTypStr + ")", fontCh10, 0, CENTER);
            if (!("").equals(issuTypStr)) {
                // addColumn(table, 99, 1, "[" + payTypStr + "]", fontCh10, 0, CENTER);
                addColumn(table, 99, 1, " ", fontCh10, 0, CENTER);
            }

            String payCodeStr = "";
            String isNaChgMkStr = "";
            String paySeqStr = "";

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
                    payCodeStr = "普通-遺屬給付(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("1").equals(adWkMk)) {
                    payCodeStr = "職災-遺屬給付(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("2").equals(adWkMk)) {
                    payCodeStr = "加職-遺屬給付(年金)" + isNaChgMkStr;
                }
                else {
                    payCodeStr = "普通-遺屬給付(年金)";
                }
            }

            addColumn(table, 99, 1, "給付別：" + payCodeStr, fontCh10, 0, LEFT);

            addColumn(table, 63, 1, "核定日期：" + DateUtility.formatChineseDateTimeString(chkDate, true), fontCh10, 0, LEFT);
            addColumn(table, 24, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(cPrnDate, true), fontCh10, 0, LEFT);
            // addColumn(table, 7, 1, "第" + StringUtility.chtLeftPad(String.valueOf(pageNum + 580000), 6, "0") + "頁", fontCh10, 0, LEFT);
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
        addColumn(table, 86, 1, "借：2822121 11111 0應付保管款－普通－老年給付(年金)", fontCh10, 0, LEFT);
        addColumn(table, 8, 1, formatBigDecimalToInteger(tissueAmt), fontCh10, 0, RIGHT);
        addColumn(table, 5, 1, "", fontCh10, 0, CENTER);
        rowCount += 1;

        // 會計科目 - 貸(貸字前空六個空格)
        // 應付金額
        addColumn(table, 86, 1, "      貸：1178121 11111 8其他應收款－普通－老年給付(年金)", fontCh10, 0, LEFT);
        addColumn(table, 8, 1, formatBigDecimalToInteger(tissueAmt), fontCh10, 0, RIGHT);
        addColumn(table, 5, 1, "", fontCh10, 0, CENTER);
        rowCount += 1;

        // 補空白行
        for (int i = 0; i < 11 - rowCount; i++) {
            addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
        }
        if (("L").equals(payCode)) {
            addColumn(table, 30, 1, RptTitleUtility.getOldAgePaymentTitle(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
        }
        else if (("K").equals(payCode)) {
            addColumn(table, 30, 1, RptTitleUtility.getDisabledPaymentTitle(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
        }
        else if (("S").equals(payCode)) {
            addColumn(table, 30, 1, RptTitleUtility.getSurvivorPaymentTitle(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
        }
        else {
            addColumn(table, 30, 1, "　", fontCh10, 0, CENTER);
        }
        addColumn(table, 30, 1, "科長：", fontCh10, 0, LEFT);
        addColumn(table, 39, 1, "給付帳務科：", fontCh10, 0, LEFT);

        addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);

        return table;
    }

    @SuppressWarnings("unchecked")
    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt19Case> caseData, List<MonthlyRpt19Case> caseDataFooter, List<MonthlyRpt19Case> caseData1, List<MonthlyRpt19Case> caseDataFooter1, HashMap<String, Object> map)
                    throws Exception {
        try {
            // Integer pageNum = 1;// 頁數

            RptService rptService = (RptService) SpringHelper.getBeanById("rptService");

            Table table = null;
            String payCode = (String) map.get("payCode");
            String issuYm = (String) map.get("issuYm");

            // 2009.6.8 EvelynHsu add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt19_" + Encode.forJava(printDate) + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt19_" + printDate + ".pdf");
            // writer = PdfWriter.getInstance(document, bao);

            document.open();

            if (!(caseData == null)) {
                // 印主要報表 START - 【NACHGMK is null】
                // ================================================================================================================
                // 建立資料Map
                // Map的Key 為 issuYm+payYm
                // Value則是同為該 payTyp 下的所有資料
                Map<String, List<MonthlyRpt19Case>> dataMap = new TreeMap<String, List<MonthlyRpt19Case>>();
                Map<String, List<MonthlyRpt19Case>> dataMapFooter = new TreeMap<String, List<MonthlyRpt19Case>>(); // 20120406
                for (int i = 0; i < caseData.size(); i++) {
                    MonthlyRpt19Case obj = caseData.get(i);
                    dataMap.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getIssuTyp(), new ArrayList<MonthlyRpt19Case>());
                }

                for (int i = 0; i < caseData.size(); i++) {
                    MonthlyRpt19Case obj = caseData.get(i);
                    (dataMap.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getIssuTyp())).add(obj);
                }
                // caseData & caseDataFooter 的 IssuTyp 必須一致 不然報表會產失敗 key會錯誤
                for (int i = 0; i < caseData.size(); i++) { // 20120416
                    MonthlyRpt19Case obj = caseData.get(i);
                    dataMapFooter.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getIssuTyp(), new ArrayList<MonthlyRpt19Case>());
                }

                for (int i = 0; i < caseDataFooter.size(); i++) { // 20120416
                    MonthlyRpt19Case obj = caseDataFooter.get(i);
                    (dataMapFooter.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getIssuTyp())).add(obj);
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
                    List<MonthlyRpt19Case> caseList = dataMap.get(key);
                    MonthlyRpt19Case tmpCaseObj = caseList.get(0);

                    // 拿取頁次 20130731
                    List<MonthlyRpt19Case> caseListPageNum = dataMapFooter.get(key);
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

                    table = getHeader(true, payCode, tmpCaseObj.getIssuTypStr(), tmpCaseObj.getChkDateStr(), pageNum, "", tmpCaseObj.getNlWkMk(), tmpCaseObj.getAdWkMk(), "N", tmpCaseObj.getcPrnDateStr());
                    for (MonthlyRpt19Case caseObj : caseList) {
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
                    if (dataSize < 9) {
                        for (int i = 0; i < (9 - dataSize); i++) {
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

                    /**
                     * 20120416 table = addFooter(table, payCode, key, tissueAmt);
                     **/
                    // 20120406 begin
                    table.setBorderWidth(1);
                    int rowCount = 0;
                    List<MonthlyRpt19Case> caseList1 = dataMapFooter.get(key);
                    for (MonthlyRpt19Case caseObj1 : caseList1) {

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
                    if (rowCount <= 11) {
                        for (int i = 0; i < 11 - rowCount; i++) {
                            addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
                        }
                    }
                    else {
                        addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
                    }

                    if (("L").equals(payCode)) {
                        addColumn(table, 30, 1, RptTitleUtility.getOldAgePaymentTitle(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                    }
                    else if (("K").equals(payCode)) {
                        addColumn(table, 30, 1, RptTitleUtility.getDisabledPaymentTitle(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                    }
                    else if (("S").equals(payCode)) {
                        addColumn(table, 30, 1, RptTitleUtility.getSurvivorPaymentTitle(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                    }
                    else {
                        addColumn(table, 30, 1, "　", fontCh10, 0, CENTER);
                    }
                    addColumn(table, 32, 1, "科長：", fontCh10, 0, LEFT);
                    addColumn(table, 37, 1, "給付帳務科：", fontCh10, 0, LEFT);

                    addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
                    // 20120406 end

                    document.add(table);
                    // pageNum++;
                }
                // ================================================================================================================
                // 印主要報表 END - 【NACHGMK is null】
            }
            if (!(caseData1 == null)) {
                // 印主要報表 START - 【NACHGMK is not null】
                // ================================================================================================================
                // 建立資料Map
                // Map的Key 為 issuYm+payYm
                // Value則是同為該 payTyp 下的所有資料
                Map<String, List<MonthlyRpt19Case>> dataMap1 = new TreeMap<String, List<MonthlyRpt19Case>>();
                Map<String, List<MonthlyRpt19Case>> dataMapFooter1 = new TreeMap<String, List<MonthlyRpt19Case>>(); // 20120406
                for (int i = 0; i < caseData1.size(); i++) {
                    MonthlyRpt19Case obj = caseData1.get(i);
                    dataMap1.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getIssuTyp(), new ArrayList<MonthlyRpt19Case>());
                }

                for (int i = 0; i < caseData1.size(); i++) {
                    MonthlyRpt19Case obj = caseData1.get(i);
                    (dataMap1.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getIssuTyp())).add(obj);
                }

                for (int i = 0; i < caseData1.size(); i++) { // 20120416
                    MonthlyRpt19Case obj = caseData1.get(i);
                    dataMapFooter1.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getIssuTyp(), new ArrayList<MonthlyRpt19Case>());
                }

                for (int i = 0; i < caseDataFooter1.size(); i++) { // 20120416
                    MonthlyRpt19Case obj = caseDataFooter1.get(i);
                    (dataMapFooter1.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk() + obj.getIssuTyp())).add(obj);
                }

                for (Iterator it = dataMap1.keySet().iterator(); it.hasNext();) {
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
                    List<MonthlyRpt19Case> caseList = dataMap1.get(key);
                    MonthlyRpt19Case tmpCaseObj = caseList.get(0);

                    // 拿取頁次 20130731
                    List<MonthlyRpt19Case> caseListPageNumSub = dataMapFooter1.get(key);
                    String rptPage = caseListPageNumSub.get(0).getRptPage().toString();
                    String eRptPage = caseListPageNumSub.get(0).geteRptPage().toString();
                    String pageNumSub = "";
                    // 判斷頁次
                    if (rptPage.equals("0") && eRptPage.equals("0")) {
                        pageNumSub = "";
                    }
                    else {
                        if (eRptPage.equals(rptPage)) {
                            pageNumSub = rptPage;
                        }
                        else {
                            pageNumSub = rptPage + "~" + eRptPage;
                        }
                    }

                    table = getHeader(true, payCode, tmpCaseObj.getIssuTypStr(), tmpCaseObj.getChkDateStr(), pageNumSub, "1", tmpCaseObj.getNlWkMk(), tmpCaseObj.getAdWkMk(), "Y", tmpCaseObj.getcPrnDateStr());
                    for (MonthlyRpt19Case caseObj : caseList) {
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

                    /**
                     * 20120406 table = addFooter(table, payCode, issuYm, key, tissueAmt, tpayAmt, toffsetAmt, tmitRate, tcompenAmt, totheraAmt, totherbAmt);
                     **/

                    // 20120406 begin
                    table.setBorderWidth(1);
                    int rowCount = 0;
                    List<MonthlyRpt19Case> caseList1 = dataMapFooter1.get(key);
                    for (MonthlyRpt19Case caseObj1 : caseList1) {

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
                    if (rowCount <= 11) {
                        for (int i = 0; i < 11 - rowCount; i++) {
                            addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
                        }
                    }
                    else {
                        addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
                    }

                    if (("L").equals(payCode)) {
                        addColumn(table, 30, 1, RptTitleUtility.getOldAgePaymentTitle(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                    }
                    else if (("K").equals(payCode)) {
                        addColumn(table, 30, 1, RptTitleUtility.getDisabledPaymentTitle(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                    }
                    else if (("S").equals(payCode)) {
                        addColumn(table, 30, 1, RptTitleUtility.getSurvivorPaymentTitle(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
                    }
                    else {
                        addColumn(table, 30, 1, "　", fontCh10, 0, CENTER);
                    }
                    addColumn(table, 32, 1, "科長：", fontCh10, 0, LEFT);
                    addColumn(table, 37, 1, "給付帳務科：", fontCh10, 0, LEFT);

                    addColumn(table, 99, 1, "　", fontCh10, 0, CENTER);
                    // 20120406 end

                    document.add(table);
                    // pageNum++;
                }
                // ================================================================================================================
                // 印主要報表 END - 【NACHGMK is not null】
            }

            document.close();
            // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
            printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt19_" + Encode.forJava(printDate) + ".pdf"), bao);
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
