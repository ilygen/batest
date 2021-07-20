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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt20Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.BaBusinessUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.common.helper.SpringHelper;

/**
 * 應收款立帳核定清單
 * 
 * @author Rickychi
 */
public class MonthlyRpt20Report extends ReportBase {

    private RptService rptService;

    public MonthlyRpt20Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 30, 30, 60, 60);
        return document;
    }

    public Table getHeader(boolean bPrintMainHeader, String payCode, String chkDate, String pageNum, String naChgMk, String nlWkMk, String adWkMk, String isNaChgMk, String cPrnDate) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(35);
        table.setAutoFillEmptyCells(true);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.setBorder(0);

        addColumn(table, 35, 1, " ", fontCh10, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 35, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()), fontCh14b, 0, CENTER);
            addColumn(table, 35, 1, RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh12, 0, CENTER);
            // addColumn(table, 35, 1, "勞工保險局", fontCh14b, 0, CENTER);
            // addColumn(table, 35, 1, "給付處", fontCh12, 0, CENTER);
            addColumn(table, 35, 1, "應收款立帳核定清單", fontCh12, 0, CENTER);

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

            addColumn(table, 35, 1, "給付別：" + payCodeStr, fontCh12, 0, LEFT);
            addColumn(table, 13, 1, "核定日期：" + DateUtility.formatChineseDateTimeString(chkDate, true), fontCh12, 0, LEFT);

            // addColumn(table, 5, 1, "第" + StringUtility.chtLeftPad(String.valueOf(pageNum + 570000), 6, "0") + "頁", fontCh12, 0, LEFT);
            addColumn(table, 13, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(cPrnDate, true), fontCh12, 0, LEFT);
            // addColumn(table, 7, 1, "第" + StringUtility.chtLeftPad(String.valueOf(pageNum + 500000), 6, "0") + "頁", fontCh10, 0, LEFT);
            if (StringUtils.isBlank(pageNum)) {
                addColumn(table, 9, 1, "", fontCh12, 0, LEFT);// 20130731
            }
            else {
                addColumn(table, 9, 1, "第" + pageNum + "頁", fontCh12, 0, LEFT);// 20130731
            }
        }

        return table;
    }

    public Table addFooter(Table table, String payCode, BigDecimal tunAcpAmt) throws Exception {
        table.setBorderWidth(1);

        int rowCount = 0;

        // 會計科目 - 借
        // 核定金額
        addColumn(table, 27, 1, "借：1178121 11111 8其他應收款－普通－老年給付(年金)", fontCh12, 0, LEFT);
        addColumn(table, 8, 1, formatBigDecimalToInteger(tunAcpAmt), fontCh12, 0, RIGHT);
        rowCount += 1;

        // 會計科目 - 貸(貸字前空六個空格)
        // 應付金額
        addColumn(table, 27, 1, "      貸：5509121 11111 2保險給付－普通－老年給付(年金)", fontCh12, 0, LEFT);
        addColumn(table, 8, 1, formatBigDecimalToInteger(tunAcpAmt), fontCh12, 0, RIGHT);
        rowCount += 1;

        // 補空白行
        for (int i = 0; i < 9 - rowCount; i++) {
            addColumn(table, 35, 1, "　", fontCh12, 0, CENTER);
        }
        if (("L").equals(payCode)) {
            addColumn(table, 12, 1, RptTitleUtility.getOldAgePaymentTitle(DateUtility.getNowWestDate()), fontCh12, 0, LEFT);
        }
        else if (("K").equals(payCode)) {
            addColumn(table, 12, 1, RptTitleUtility.getDisabledPaymentTitle(DateUtility.getNowWestDate()), fontCh12, 0, LEFT);
        }
        else if (("S").equals(payCode)) {
            addColumn(table, 12, 1, RptTitleUtility.getSurvivorPaymentTitle(DateUtility.getNowWestDate()), fontCh12, 0, LEFT);
        }
        else {
            addColumn(table, 12, 1, " ", fontCh12, 0, CENTER);
        }
        addColumn(table, 12, 1, "科長：", fontCh12, 0, LEFT);
        addColumn(table, 11, 1, "給付帳務科：", fontCh12, 0, LEFT);

        addColumn(table, 35, 1, "　", fontCh12, 0, CENTER);

        return table;
    }

    @SuppressWarnings("unchecked")
    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt20Case> caseData, List<MonthlyRpt20Case> caseData1, List<MonthlyRpt20Case> caseDataFooter, List<MonthlyRpt20Case> caseDataFooter1, HashMap<String, Object> map)
                    throws Exception {
        try {
            // Integer pageNum = 1;// 頁數

            RptService rptService = (RptService) SpringHelper.getBeanById("rptService");

            String payCode = (String) map.get("payCode");
            String issuYm = (String) map.get("issuYm");

            // 2009.6.8 EvelynHsu add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt20_" + Encode.forJava(printDate) + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt20_" + printDate + ".pdf");
            // writer = PdfWriter.getInstance(document, bao);

            document.open();

            // 【NACHGMK is null】
            // 201205 start

            // 建立資料Map
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 payTyp + nlWkMk + adWkMk 下的所有資料
            Map<String, List<MonthlyRpt20Case>> dataMap = new TreeMap<String, List<MonthlyRpt20Case>>();
            Map<String, List<MonthlyRpt20Case>> dataMapFooter = new TreeMap<String, List<MonthlyRpt20Case>>(); // 20120406
            // Map<String, List<MonthlyRpt04Case>> dataMapFooter = new TreeMap<String, List<MonthlyRpt04Case>>(); //20120406
            for (int i = 0; i < caseData.size(); i++) {
                MonthlyRpt20Case obj = caseData.get(i);
                // dataMap.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt20Case>());
                dataMap.put(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt20Case>());
            }

            for (int i = 0; i < caseData.size(); i++) {
                MonthlyRpt20Case obj = caseData.get(i);
                // (dataMap.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
                (dataMap.get(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
            }

            for (int i = 0; i < caseData.size(); i++) { // 20120416
                MonthlyRpt20Case obj = caseData.get(i);
                dataMapFooter.put(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt20Case>());
            }

            for (int i = 0; i < caseDataFooter.size(); i++) { // 20120416
                MonthlyRpt20Case obj = caseDataFooter.get(i);
                (dataMapFooter.get(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
            }

            /**
             * for (int i = 0; i < caseData.size(); i++) { //20120416 MonthlyRpt04Case obj = caseData.get(i); dataMapFooter.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt04Case>()); } for (int i = 0; i <
             * caseDataFooter.size(); i++) { //20120416 MonthlyRpt04Case obj = caseDataFooter.get(i); (dataMapFooter.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj); }
             **/
            for (Iterator it = dataMap.keySet().iterator(); it.hasNext();) {

                // 201205 end

                Table table = null;
                Table headtable = null;
                Integer seqNo = 1;// 序號
                BigDecimal tapNoAmt = new BigDecimal(0);// 件數
                BigDecimal tdataAmt = new BigDecimal(0);// 筆數
                BigDecimal tissueAmt = new BigDecimal(0);// 核定金額
                BigDecimal tunAcpAmt = new BigDecimal(0);// 應收金額

                String key = (String) it.next();
                List<MonthlyRpt20Case> caseList = dataMap.get(key);

                // MonthlyRpt20Case tmpCaseObj = caseData.get(0);
                MonthlyRpt20Case tmpCaseObj = caseList.get(0);

                // 拿取頁次 20130731
                List<MonthlyRpt20Case> caseListPageNum = dataMapFooter.get(key);
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

                headtable = getHeader(true, payCode, tmpCaseObj.getChkDateStr(), pageNum, "", tmpCaseObj.getNlWkMk(), tmpCaseObj.getAdWkMk(), "N", tmpCaseObj.getcPrnDateStr());
                document.add(headtable);

                table = createTable(35);
                table.setAutoFillEmptyCells(true);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.setPadding(5);

                addColumn(table, 4, 1, "序號", fontCh12, 1, CENTER);
                addColumn(table, 5, 1, "件數", fontCh12, 1, CENTER);
                addColumn(table, 5, 1, "筆數", fontCh12, 1, CENTER);
                addColumn(table, 8, 1, "核定金額", fontCh12, 1, CENTER);
                addColumn(table, 8, 1, "應收金額", fontCh12, 1, CENTER);
                addColumn(table, 5, 1, "備註", fontCh12, 1, CENTER);

                // for (int i = 0; i < caseData.size(); i++) {
                // MonthlyRpt20Case caseObj = caseData.get(i);
                for (MonthlyRpt20Case caseObj : caseList) {

                    // 計算合計資料
                    tapNoAmt = tapNoAmt.add(caseObj.getApNoAmt());// 件數
                    tdataAmt = tdataAmt.add(caseObj.getDataAmt());// 筆數
                    tissueAmt = tissueAmt.add(caseObj.getIssueAmt());// 核定金額
                    tunAcpAmt = tunAcpAmt.add(caseObj.getUnacpAmt());// 應收金額

                    addColumn(table, 4, 1, Integer.toString(seqNo), fontCh12, 1, CENTER);
                    addColumn(table, 5, 1, formatBigDecimalToInteger(caseObj.getApNoAmt()), fontCh12, 1, RIGHT);
                    addColumn(table, 5, 1, formatBigDecimalToInteger(caseObj.getDataAmt()), fontCh12, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getIssueAmt()), fontCh12, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getUnacpAmt()), fontCh12, 1, RIGHT);
                    addColumn(table, 5, 1, "　", fontCh10, 1, CENTER);

                    seqNo++;
                }
                // int dataSize = caseData.size();
                int dataSize = caseList.size();
                if (dataSize < 9) {
                    for (int i = 0; i < (9 - dataSize); i++) {
                        addColumn(table, 4, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 5, 1, "", fontCh12, 1, RIGHT);
                        addColumn(table, 5, 1, "", fontCh12, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh12, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh12, 1, RIGHT);
                        addColumn(table, 5, 1, "", fontCh12, 1, CENTER);
                    }
                }

                addColumn(table, 4, 1, "合計", fontCh12, 1, CENTER);
                addColumn(table, 5, 1, formatBigDecimalToInteger(tapNoAmt), fontCh12, 1, RIGHT);
                addColumn(table, 5, 1, formatBigDecimalToInteger(tdataAmt), fontCh12, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(tissueAmt), fontCh12, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(tunAcpAmt), fontCh12, 1, RIGHT);
                addColumn(table, 5, 1, "", fontCh12, 1, CENTER);

                /**
                 * 20120416 table = addFooter(table, payCode, tunAcpAmt);
                 **/

                // 20120406 begin
                table.setBorderWidth(1);
                int rowCount = 0;
                List<MonthlyRpt20Case> caseList1 = dataMapFooter.get(key);
                for (MonthlyRpt20Case caseObj1 : caseList1) {

                    if ((caseObj1.getAccountSeq().toString()).equals("0")) {
                        addColumn(table, 3, 1, "借：", fontCh10, 0, LEFT);
                        addColumn(table, 6, 1, caseObj1.getAccountNo(), fontCh10, 0, LEFT);
                        addColumn(table, 18, 1, caseObj1.getAccountName(), fontCh10, 0, LEFT);
                        addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj1.getPayAmt()), fontCh10, 0, RIGHT);
                        rowCount += 1;
                    }
                    else if ((caseObj1.getAccountSeq().toString()).equals("1")) {

                        String chkMk = "";
                        if (caseObj1.getAccountNo().substring(0, 10).equals("212312  12")) {
                            chkMk = BaBusinessUtility.getAccountChkMk(caseObj1.getAccountNo() + ".");
                        }

                        addColumn(table, 2, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 3, 1, "貸：", fontCh10, 0, LEFT);
                        addColumn(table, 6, 1, caseObj1.getAccountNo() + " " + chkMk, fontCh10, 0, LEFT);
                        addColumn(table, 16, 1, caseObj1.getAccountName(), fontCh10, 0, LEFT);
                        addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj1.getPayAmt()), fontCh10, 0, RIGHT);
                        rowCount += 1;

                    }

                    seqNo++;
                }

                // 補空白行
                for (int i = 0; i < 9 - rowCount; i++) {
                    addColumn(table, 35, 1, "　", fontCh12, 0, CENTER);
                }
                if (("L").equals(payCode)) {
                    addColumn(table, 12, 1, RptTitleUtility.getOldAgePaymentTitle(DateUtility.getNowWestDate()), fontCh12, 0, LEFT);
                }
                else if (("K").equals(payCode)) {
                    addColumn(table, 12, 1, RptTitleUtility.getDisabledPaymentTitle(DateUtility.getNowWestDate()), fontCh12, 0, LEFT);
                }
                else if (("S").equals(payCode)) {
                    addColumn(table, 12, 1, RptTitleUtility.getSurvivorPaymentTitle(DateUtility.getNowWestDate()), fontCh12, 0, LEFT);
                }
                else {
                    addColumn(table, 12, 1, " ", fontCh12, 0, CENTER);
                }
                addColumn(table, 12, 1, "科長：", fontCh12, 0, LEFT);
                addColumn(table, 11, 1, "給付帳務科：", fontCh12, 0, LEFT);

                addColumn(table, 35, 1, "　", fontCh12, 0, CENTER);
                // 20120406 end

                document.add(table);
                // pageNum++;

            } // 201205 分頁

            // 【NACHGMK is not null】
            // 建立資料Map
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 payTyp + nlWkMk + adWkMk 下的所有資料
            Map<String, List<MonthlyRpt20Case>> dataMap1 = new TreeMap<String, List<MonthlyRpt20Case>>();
            Map<String, List<MonthlyRpt20Case>> dataMapFooter1 = new TreeMap<String, List<MonthlyRpt20Case>>(); // 20120406
            // Map<String, List<MonthlyRpt04Case>> dataMapFooter1 = new TreeMap<String, List<MonthlyRpt04Case>>(); //20120406
            for (int i = 0; i < caseData1.size(); i++) {
                MonthlyRpt20Case obj = caseData1.get(i);
                // dataMap1.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt20Case>());
                dataMap1.put(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt20Case>());
            }

            for (int i = 0; i < caseData1.size(); i++) {
                MonthlyRpt20Case obj = caseData1.get(i);
                // (dataMap1.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
                (dataMap1.get(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
            }

            for (int i = 0; i < caseData1.size(); i++) { // 20120416
                MonthlyRpt20Case obj = caseData1.get(i);
                dataMapFooter1.put(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt20Case>());
            }

            for (int i = 0; i < caseDataFooter1.size(); i++) { // 20120416
                MonthlyRpt20Case obj = caseDataFooter1.get(i);
                (dataMapFooter1.get(obj.getNaChgMk() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj);
            }

            /**
             * for (int i = 0; i < caseData1.size(); i++) { //20120416 MonthlyRpt04Case obj = caseData1.get(i); dataMapFooter1.put(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk(), new ArrayList<MonthlyRpt04Case>()); } for (int i = 0; i <
             * caseDataFooter1.size(); i++) { //20120416 MonthlyRpt04Case obj = caseDataFooter1.get(i); (dataMapFooter1.get(obj.getPayTyp() + obj.getNlWkMk() + obj.getAdWkMk())).add(obj); }
             **/
            for (Iterator it = dataMap1.keySet().iterator(); it.hasNext();) {

                // 201205 end

                Table table = null;
                Table headtable = null;
                Integer seqNo = 1;// 序號
                BigDecimal tapNoAmt = new BigDecimal(0);// 件數
                BigDecimal tdataAmt = new BigDecimal(0);// 筆數
                BigDecimal tissueAmt = new BigDecimal(0);// 核定金額
                BigDecimal tunAcpAmt = new BigDecimal(0);// 應收金額

                String key = (String) it.next();
                List<MonthlyRpt20Case> caseList = dataMap1.get(key);

                // MonthlyRpt20Case tmpCaseObj = caseData1.get(0);
                MonthlyRpt20Case tmpCaseObj = caseList.get(0);

                // 拿取頁次 20130731
                List<MonthlyRpt20Case> caseListPageNumSub = dataMapFooter1.get(key);
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

                headtable = getHeader(true, payCode, tmpCaseObj.getChkDateStr(), pageNumSub, tmpCaseObj.getNaChgMk(), tmpCaseObj.getNlWkMk(), tmpCaseObj.getAdWkMk(), "Y", tmpCaseObj.getcPrnDateStr());
                document.add(headtable);

                table = createTable(35);
                table.setAutoFillEmptyCells(true);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.setPadding(5);

                addColumn(table, 4, 1, "序號", fontCh12, 1, CENTER);
                addColumn(table, 5, 1, "件數", fontCh12, 1, CENTER);
                addColumn(table, 5, 1, "筆數", fontCh12, 1, CENTER);
                addColumn(table, 8, 1, "核定金額", fontCh12, 1, CENTER);
                addColumn(table, 8, 1, "應收金額", fontCh12, 1, CENTER);
                addColumn(table, 5, 1, "備註", fontCh12, 1, CENTER);

                // for (int i = 0; i < caseData1.size(); i++) {
                // MonthlyRpt20Case caseObj = caseData1.get(i);
                for (MonthlyRpt20Case caseObj : caseList) {

                    // 計算合計資料
                    tapNoAmt = tapNoAmt.add(caseObj.getApNoAmt());// 件數
                    tdataAmt = tdataAmt.add(caseObj.getDataAmt());// 筆數
                    tissueAmt = tissueAmt.add(caseObj.getIssueAmt());// 核定金額
                    tunAcpAmt = tunAcpAmt.add(caseObj.getUnacpAmt());// 應收金額

                    addColumn(table, 4, 1, Integer.toString(seqNo), fontCh12, 1, CENTER);
                    addColumn(table, 5, 1, formatBigDecimalToInteger(caseObj.getApNoAmt()), fontCh12, 1, RIGHT);
                    addColumn(table, 5, 1, formatBigDecimalToInteger(caseObj.getDataAmt()), fontCh12, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getIssueAmt()), fontCh12, 1, RIGHT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj.getUnacpAmt()), fontCh12, 1, RIGHT);
                    addColumn(table, 5, 1, "　", fontCh10, 1, CENTER);

                    seqNo++;
                }
                // int dataSize = caseData1.size();
                int dataSize = caseList.size();
                if (dataSize < 9) {
                    for (int i = 0; i < (9 - dataSize); i++) {
                        addColumn(table, 4, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 5, 1, "", fontCh12, 1, RIGHT);
                        addColumn(table, 5, 1, "", fontCh12, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh12, 1, RIGHT);
                        addColumn(table, 8, 1, "", fontCh12, 1, RIGHT);
                        addColumn(table, 5, 1, "", fontCh12, 1, CENTER);
                    }
                }

                addColumn(table, 4, 1, "合計", fontCh12, 1, CENTER);
                addColumn(table, 5, 1, formatBigDecimalToInteger(tapNoAmt), fontCh12, 1, RIGHT);
                addColumn(table, 5, 1, formatBigDecimalToInteger(tdataAmt), fontCh12, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(tissueAmt), fontCh12, 1, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(tunAcpAmt), fontCh12, 1, RIGHT);
                addColumn(table, 5, 1, "", fontCh12, 1, CENTER);

                /**
                 * 20120416 table = addFooter(table, payCode, tunAcpAmt);
                 **/

                // 20120406 begin
                table.setBorderWidth(1);
                int rowCount = 0;
                List<MonthlyRpt20Case> caseList1 = dataMapFooter1.get(key);
                for (MonthlyRpt20Case caseObj1 : caseList1) {

                    if ((caseObj1.getAccountSeq().toString()).equals("0")) {
                        addColumn(table, 3, 1, "借：", fontCh10, 0, LEFT);
                        addColumn(table, 6, 1, caseObj1.getAccountNo(), fontCh10, 0, LEFT);
                        addColumn(table, 18, 1, caseObj1.getAccountName(), fontCh10, 0, LEFT);
                        addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj1.getPayAmt()), fontCh10, 0, RIGHT);
                        rowCount += 1;
                    }
                    else if ((caseObj1.getAccountSeq().toString()).equals("1")) {

                        String chkMk = "";
                        if (caseObj1.getAccountNo().substring(0, 10).equals("212312  12")) {
                            chkMk = BaBusinessUtility.getAccountChkMk(caseObj1.getAccountNo() + ".");
                        }

                        addColumn(table, 2, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 3, 1, "貸：", fontCh10, 0, LEFT);
                        addColumn(table, 6, 1, caseObj1.getAccountNo() + " " + chkMk, fontCh10, 0, LEFT);
                        addColumn(table, 16, 1, caseObj1.getAccountName(), fontCh10, 0, LEFT);
                        addColumn(table, 8, 1, formatBigDecimalToInteger(caseObj1.getPayAmt()), fontCh10, 0, RIGHT);
                        rowCount += 1;

                    }

                    seqNo++;
                }

                // 補空白行
                for (int i = 0; i < 9 - rowCount; i++) {
                    addColumn(table, 35, 1, "　", fontCh12, 0, CENTER);
                }
                if (("L").equals(payCode)) {
                    addColumn(table, 12, 1, RptTitleUtility.getOldAgePaymentTitle(DateUtility.getNowWestDate()), fontCh12, 0, LEFT);
                }
                else if (("K").equals(payCode)) {
                    addColumn(table, 12, 1, RptTitleUtility.getDisabledPaymentTitle(DateUtility.getNowWestDate()), fontCh12, 0, LEFT);
                }
                else if (("S").equals(payCode)) {
                    addColumn(table, 12, 1, RptTitleUtility.getSurvivorPaymentTitle(DateUtility.getNowWestDate()), fontCh12, 0, LEFT);
                }
                else {
                    addColumn(table, 12, 1, " ", fontCh12, 0, CENTER);
                }
                addColumn(table, 12, 1, "科長：", fontCh12, 0, LEFT);
                addColumn(table, 11, 1, "給付帳務科：", fontCh12, 0, LEFT);

                addColumn(table, 35, 1, "　", fontCh12, 0, CENTER);
                // 20120406 end

                document.add(table);
                // pageNum++;

            } // 201205 分頁

            document.close();
            // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
            printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt20_" + Encode.forJava(printDate) + ".pdf"), bao);
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
