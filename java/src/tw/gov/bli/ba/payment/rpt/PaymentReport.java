package tw.gov.bli.ba.payment.rpt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfSmartCopy;

import tw.gov.bli.ba.payment.cases.PaymentProcessCase;
import tw.gov.bli.ba.payment.cases.PaymentStageDtlCase;
import tw.gov.bli.ba.payment.forms.PaymentProcessQueryForm;
import tw.gov.bli.ba.rpt.report.ReportBase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ReportUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 保險溢領給付收回繳款單
 *
 * @author Zehua Chen
 */

public class PaymentReport extends ReportBase {

    private int nPage; // 每筆資料的頁數
    private static final String TEMPLATE_FILE = "/pdf/PaymentRpt.pdf";
    private static final String TEMPLATE_FILE1 = "/pdf/paymentReportDtl.pdf";

    public PaymentReport() throws Exception {
        super();
        nPage = 0;
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 90, 90, 10, 10);
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
            addColumn(table, 17, 1, "　", fontCh10, 0, LEFT); // 注意: 內容是全形空白
        }
    }

    /**
     * 刪除行
     *
     * @param table
     * @param rows 行數
     * @throws Exception
     */
    public void deleteRow(Table table, int rows) throws Exception {
        for (int i = 0; i < rows; i++) {
            table.deleteLastRow();
        }
    }

    /**
     * 建立表頭
     *
     * @param payListSize 事故者 給付資料 筆數
     * @param attached 是否為附表
     * @return
     * @throws Exception
     */

    public Table addHeader() throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(17);
        table.setAutoFillEmptyCells(true);

        addEmptyRow(table, 3);

        return table;
    }

    public ByteArrayOutputStream doReportComp(String paymentDate, List<String> paymentNoDtlList, InputStream inFile, String evtName, String mApno, String appName, String mPayKind, String empExt, List<PaymentStageDtlCase> caseList,
                    List<PaymentProcessCase> apnoList, PaymentProcessQueryForm queryForm, String paymentSex) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        try {
            // 2009.2.20 EvelynHsu add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + "_PaymentReport_" + printDate + ".pdf");
            // 讀入 Template 檔

            byte[] templateFile;
            byte[] templateFileDtl;
            if (inFile == null) {
                InputStream inDtl = null, in = null;
                try {
                    inDtl = HttpHelper.getHttpRequest().getSession(true).getServletContext().getResourceAsStream(TEMPLATE_FILE1);
                    in = HttpHelper.getHttpRequest().getSession(true).getServletContext().getResourceAsStream(TEMPLATE_FILE);
                    templateFile = IOUtils.toByteArray(in);
                    templateFileDtl = IOUtils.toByteArray(inDtl);
                }
                finally {
                    ExceptionUtil.safeColse(inDtl);
                    ExceptionUtil.safeColse(in);
                }
            }
            else {
                templateFile = IOUtils.toByteArray(inFile);
                templateFileDtl = IOUtils.toByteArray(inFile);
            }
            String payCodeType = RptTitleUtility.getPaymentTileForPayment(mApno.substring(6, 8));
            String department = RptTitleUtility.getGroupsTitle(mApno.substring(0, 1), "");
            String divisionTitle = RptTitleUtility.getDivisionTitle(mApno.substring(0, 1), "");
            HashMap<String, String> mapSend = new HashMap<String, String>();

            byte[] tempResultFile = null;
            byte[] resultFile = null;

            PdfSmartCopy copy;
            if (inFile == null) {
                copy = new PdfSmartCopy(document, out);
            }
            else {
                copy = new PdfSmartCopy(document, bao);
            }
            boolean flag = false;
            document.open();

            for (int i = 0; i < caseList.size(); i = i + 1) {
                List<PaymentProcessCase> apnoListDtl = new ArrayList<PaymentProcessCase>();
                for (int j = 0; j < paymentNoDtlList.size(); j++) {
                    if (StringUtils.equals(caseList.get(i).getPaymentNoDetail(), paymentNoDtlList.get(j))) {
                        flag = true;
                    }
                }
                if (flag == true) {
                    document.newPage();
                    mapSend.clear();
                    PaymentStageDtlCase caseData = caseList.get(i);
                    // 50093111
                    mapSend.put("memo1", " ※本繳款單請至各地郵局繳納");
                    mapSend.put("memo2", " 提醒您，勿至銀行或超商繳納");
                    mapSend.put("payCodeType1", payCodeType);
                    mapSend.put("payCodeType2", payCodeType);
                    mapSend.put("zipCode", queryForm.getZipCode());
                    if (queryForm.getAddr().length() > 19) {
                        mapSend.put("address", queryForm.getAddr().substring(0, 19));
                        mapSend.put("address2", queryForm.getAddr().substring(19, queryForm.getAddr().length()));
                    }
                    else {
                        mapSend.put("address", queryForm.getAddr());
                    }

                    if (StringUtils.equals("1", paymentSex)) {
                        mapSend.put("name", appName + " 先生");
                    }
                    else if (StringUtils.equals("2", paymentSex)) {
                        mapSend.put("name", appName + " 女士");
                    }
                    else {
                        mapSend.put("name", appName + " 君");
                    }
                    mapSend.put("department1", "洽詢電話：本局" + divisionTitle + "（02）23961266 轉分機 " + empExt);
                    // mapSend.put("department2", "");

                    if (caseList.size() > 1) {
                        mapSend.put("explain1", "台端溢領給付經本局同意按分期繳還，請依限繳納，");
                        mapSend.put("explain2", "如未按期繳納即視同全部到期，須一次繳清，否則將");
                        mapSend.put("explain3", "依法訴追。");
                        mapSend.put("thisStage1", "本期");
                        mapSend.put("thisStage2", "本期");
                    }
                    mapSend.put("crtDate", DateUtility.formatChineseDateString(paymentDate, false));
                    mapSend.put("nowStage1", (i + 1) + "/" + (caseList.size()));
                    mapSend.put("nowStage2", (i + 1) + "/" + (caseList.size()));
                    mapSend.put("pageNum", (i + 1) + "/" + (caseList.size()));
                    if (StringUtils.equals(queryForm.getChkObj(), "1") && StringUtils.isBlank(caseData.getPaymentDateLine())) {
                        mapSend.put("paymentDateLine1", "文到之翌日起30日內繳納");
                    }else if(StringUtils.equals(queryForm.getChkObj(), "3") && StringUtils.isBlank(caseData.getPaymentDateLine())) {
                        mapSend.put("paymentDateLine1", "文到之翌日起15日內繳納");
                    }
                    else {
                        mapSend.put("paymentDateLine1", DateUtility.formatChineseDateString(caseData.getPaymentDateLine(), true));
                    }
                    mapSend.put("stagePayAmt1", formatBigDecimalToInteger(caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()))) + "元	");
                    mapSend.put("appName1", appName);
                    mapSend.put("totAmt", formatBigDecimalToInteger(queryForm.getTotAmt()) + " 元");
                    BigDecimal payAmt = queryForm.getPayTotAmt().subtract(queryForm.getTotExec()).subtract(queryForm.getTotInterst());
                    // if(queryForm.getTotInterst().compareTo(BigDecimal.ZERO)<=0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO)<=0 ){
                    // mapSend.put("explain4", "，本金"+formatBigDecimalToInteger(payAmt)+"元");
                    // }else
                    if (caseList.size() == 1) {
                        if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) > 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) <= 0) {
                            mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(payAmt) + "元、利息" + formatBigDecimalToInteger(queryForm.getTotInterst()) + "元）");
                        }
                        else if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) <= 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) > 0) {
                            mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(payAmt) + "元、執行費用" + formatBigDecimalToInteger(queryForm.getTotExec()) + "元）");
                        }
                        else if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) > 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) > 0) {
                            mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(payAmt) + "元、利息" + formatBigDecimalToInteger(queryForm.getTotInterst()) + "元、執行費用" + formatBigDecimalToInteger(queryForm.getTotExec()) + "元）");
                        }
                    }
                    else {
                        if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) > 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) <= 0) {
                            mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(payAmt) + "元、利息" + formatBigDecimalToInteger(queryForm.getTotInterst()) + "元），本次攤還"
                                            + formatBigDecimalToInteger(caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()))) + "元");
                        }
                        else if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) <= 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) > 0) {
                            mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(payAmt) + "元、執行費用" + formatBigDecimalToInteger(queryForm.getTotExec()) + "元），本次攤還"
                                            + formatBigDecimalToInteger(caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()))) + "元");
                        }
                        else if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) > 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) > 0) {
                            mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(payAmt) + "元、利息" + formatBigDecimalToInteger(queryForm.getTotInterst()) + "元、執行費用" + formatBigDecimalToInteger(queryForm.getTotExec()) + "元），本次攤還"
                                            + formatBigDecimalToInteger(caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()))) + "元");
                        }
                        else if (caseList.size() > 1) {
                            mapSend.put("explain4", "，本次攤還" + formatBigDecimalToInteger(payAmt) + "元");
                        }
                    }

                    StringBuilder sb1 = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    StringBuilder sb3 = new StringBuilder();
                    // for(int j=0;j<apnoList.size();j++){
                    // PaymentProcessCase apnoCase = apnoList.get(j);
                    // String payCodeString ="";
                    // if(StringUtils.equals(apnoCase.getApno().substring(0,1), "L")){
                    // payCodeString = "勞保老年年金給付";
                    // }else if(StringUtils.equals(apnoCase.getApno().substring(0,1), "K")){
                    // payCodeString = "勞保失能年金給付";
                    // }else{
                    // payCodeString = "勞保遺屬年金給付";
                    // }
                    // sb1.append(payCodeString+"\n");
                    // sb2.append(apnoCase.getBenName()+"\n");
                    // sb3.append(apnoCase.getApno()+"\n");
                    // sb4.append(formatBigDecimalToInteger(apnoCase.getPayAmt())+"\n");
                    //
                    // }
                    for (PaymentProcessCase cs : apnoList) {
                        if (!StringUtils.isBlank(cs.getWriteoffSeqNo()) && !StringUtils.equals(cs.getWriteoffSeqNo(), "0")) {
                            apnoListDtl.add(cs);
                        }
                    }
                    String payCodeStringL = "";
                    BigDecimal totPayAmtL = BigDecimal.ZERO;
                    String payCodeStringS = "";
                    BigDecimal totPayAmtS = BigDecimal.ZERO;
                    String payCodeStringK = "";
                    BigDecimal totPayAmtK = BigDecimal.ZERO;
                    for (int j = 0; j < apnoListDtl.size(); j++) {
                        PaymentProcessCase apnoCase = apnoListDtl.get(j);
                        if (StringUtils.equals(apnoCase.getApno().substring(0, 1), "L")) {
                            payCodeStringL = "勞保老年年金給付";
                            totPayAmtL = totPayAmtL.add(apnoCase.getPayAmt());

                        }
                        else if (StringUtils.equals(apnoCase.getApno().substring(0, 1), "K")) {
                            payCodeStringK = "勞保失能年金給付";
                            totPayAmtK = totPayAmtK.add(apnoCase.getPayAmt());
                        }
                        else {
                            payCodeStringS = "勞保遺屬年金給付";
                            totPayAmtS = totPayAmtS.add(apnoCase.getPayAmt());
                        }

                        /*
                         * if(j==0){ sb1.append(payCodeString+"\n"); sb2.append(evtName+"\n"); sb3.append(apnoCase.getApno()+"\n"); sb5.append(apnoCase.getPayYm()+"\n"); totPayAmt = totPayAmt.add(apnoCase.getPayAmt()); if((j+1)==apnoListDtl.size()){
                         * sb4.append(formatBigDecimalToInteger(totPayAmt)+"\n"); } }else if(j<apnoListDtl.size()&& (j+1)!=apnoListDtl.size()){ if(StringUtils.equals(apnoListDtl.get(j).getPayYm(), apnoListDtl.get(j-1).getPayYm()) &&
                         * StringUtils.equals(apnoListDtl.get(j).getApno(), apnoListDtl.get(j-1).getApno())){ totPayAmt = totPayAmt.add(apnoCase.getPayAmt()); }else{ sb4.append(formatBigDecimalToInteger(totPayAmt)+"\n"); totPayAmt = BigDecimal.ZERO;
                         * sb1.append(payCodeString+"\n"); sb2.append(evtName+"\n"); sb3.append(apnoCase.getApno()+"\n"); sb5.append(apnoCase.getPayYm()+"\n"); totPayAmt = totPayAmt.add(apnoCase.getPayAmt()); } }else{
                         * if(StringUtils.equals(apnoListDtl.get(j).getPayYm(), apnoListDtl.get(j-1).getPayYm()) && StringUtils.equals(apnoListDtl.get(j).getApno(), apnoListDtl.get(j-1).getApno())){ totPayAmt = totPayAmt.add(apnoCase.getPayAmt());
                         * sb4.append(formatBigDecimalToInteger(totPayAmt)+"\n"); }else{ sb4.append(formatBigDecimalToInteger(totPayAmt)+"\n"); totPayAmt = BigDecimal.ZERO; sb1.append(payCodeString+"\n"); sb2.append(evtName+"\n");
                         * sb3.append(apnoCase.getApno()+"\n"); totPayAmt = totPayAmt.add(apnoCase.getPayAmt()); sb4.append(formatBigDecimalToInteger(totPayAmt)+"\n"); sb5.append(apnoCase.getPayYm()+"\n"); } }
                         */
                    }
                    if (StringUtils.isNotBlank(payCodeStringL)) {
                        sb1.append(payCodeStringL + "\n");
                        sb2.append(evtName + "\n");
                        sb3.append(formatBigDecimalToInteger(totPayAmtL) + "\n");
                    }
                    if (StringUtils.isNotBlank(payCodeStringK)) {
                        sb1.append(payCodeStringK + "\n");
                        sb2.append(evtName + "\n");
                        sb3.append(formatBigDecimalToInteger(totPayAmtK) + "\n");
                    }
                    if (StringUtils.isNotBlank(payCodeStringS)) {
                        sb1.append(payCodeStringS + "\n");
                        sb2.append(evtName + "\n");
                        sb3.append(formatBigDecimalToInteger(totPayAmtS) + "\n");
                    }
                    mapSend.put("detailData1", sb1.toString());
                    mapSend.put("detailData2", sb2.toString());
                    mapSend.put("detailData3", sb3.toString());

                    BigDecimal thisPayAmt = caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()));
                    // 條碼產生
                    String firstTwCode = getTwCodeFirst(paymentDate);
                    String secondTwCode = getTwCodeSecond(payCodeType, caseData.getPaymentNoDetail());
                    String thirdTwCode = getTwCodeThird(firstTwCode, secondTwCode, paymentDate, thisPayAmt);
                    mapSend.put("twCode1", firstTwCode + "   " + secondTwCode + "   " + thirdTwCode);
                    // 表尾
                    mapSend.put("appName", evtName);
                    mapSend.put("applyName1", appName);
                    mapSend.put("applyName2", appName);
                    mapSend.put("nowStage1", (i + 1) + "/" + (caseList.size()));
                    if (StringUtils.equals(queryForm.getChkObj(), "1") && StringUtils.isBlank(caseData.getPaymentDateLine())) {
                        mapSend.put("paymentDateLine2", "文到之翌日起30日內繳納");
                    }else if(StringUtils.equals(queryForm.getChkObj(), "3") && StringUtils.isBlank(caseData.getPaymentDateLine())) {
                        mapSend.put("paymentDateLine2", "文到之翌日起15日內繳納");
                    }
                    else {
                        mapSend.put("paymentDateLine2", DateUtility.formatChineseDateString(caseData.getPaymentDateLine(), true));
                    }
                    mapSend.put("stagePayAmt2", formatBigDecimalToInteger(caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()))));

                    mapSend.put("barCode1", firstTwCode);
                    mapSend.put("barCode2", secondTwCode);
                    mapSend.put("barCode3", thirdTwCode);

                    if (StringUtils.isNotBlank(firstTwCode) && StringUtils.isNotBlank(firstTwCode) && StringUtils.isNotBlank(firstTwCode)) {
                        caseList.get(i).setBarCode1(firstTwCode);
                        caseList.get(i).setBarCode2(secondTwCode);
                        caseList.get(i).setBarCode3(thirdTwCode);
                    }
                    mapSend.put("barCodeText1", StringUtils.defaultString(firstTwCode + "(條碼一)"));
                    mapSend.put("barCodeText2", StringUtils.defaultString(secondTwCode + "(條碼二)"));
                    mapSend.put("barCodeText3", StringUtils.defaultString(thirdTwCode + "(條碼三 )"));

                    mapSend.put("compDate", "補印日期:" + DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(), true));
                    // Image a = getBarCodeImage(firstTwCode, 70, 120, 480, 775);
                    ReportUtility.templateReportForPayment(firstTwCode, secondTwCode, thirdTwCode, templateFile, mapSend, copy);

                    flag = false;
                }
            }
            List<PaymentProcessCase> apnoListDtl = new ArrayList<PaymentProcessCase>();

            for (int x = 0; x < apnoList.size(); x++) {
                PaymentProcessCase cs = apnoList.get(x);
                if (x > 0) {
                    if (apnoListDtl.size() > 0) {
                        if (!StringUtils.isBlank(cs.getWriteoffSeqNo()) && !StringUtils.equals(cs.getWriteoffSeqNo(), "0")) {
                            if (StringUtils.equals(cs.getPayYm(), apnoListDtl.get(apnoListDtl.size() - 1).getPayYm())) {
                                apnoListDtl.get(apnoListDtl.size() - 1).setPayAmt(cs.getPayAmt().add(apnoListDtl.get(apnoListDtl.size() - 1).getPayAmt()));
                            }
                            else {
                                apnoListDtl.add(cs);
                            }
                        }
                    }
                    else if (!StringUtils.isBlank(cs.getWriteoffSeqNo()) && !StringUtils.equals(cs.getWriteoffSeqNo(), "0")) {
                        apnoListDtl.add(cs);
                    }
                }
                else if (!StringUtils.isBlank(cs.getWriteoffSeqNo()) && !StringUtils.equals(cs.getWriteoffSeqNo(), "0")) {
                    apnoListDtl.add(cs);
                }
            }

            String pageControl = String.valueOf((apnoListDtl.size() / 25) + 1);

            // dtl報表
            document.newPage();
            mapSend.clear();
            mapSend.put("paymentDate", DateUtility.formatChineseDateString(paymentDate, false));
            mapSend.put("paymentPage", "1" + "/" + pageControl);
            StringBuilder sbDtl1 = new StringBuilder();
            StringBuilder sbDtl2 = new StringBuilder();
            StringBuilder sbDtl3 = new StringBuilder();
            StringBuilder sbDtl4 = new StringBuilder();
            StringBuilder sbDtl5 = new StringBuilder();
            StringBuilder sbDtl6 = new StringBuilder();
            String startYm = "";
            String endYm = "";
            int tmpCount = 1;
            int months = 1;
            BigDecimal totPayAmt = BigDecimal.ZERO;
            BigDecimal payAmt = BigDecimal.ZERO;
            for (int j = 0; j < apnoListDtl.size(); j++) {
                if ((tmpCount) % 25 == 0) {
                    mapSend.put("paymentDetailData1", sbDtl1.toString());
                    mapSend.put("paymentDetailData2", sbDtl2.toString());
                    mapSend.put("paymentDetailData3", sbDtl3.toString());
                    mapSend.put("paymentDetailData4", sbDtl4.toString());
                    mapSend.put("paymentDetailData5", sbDtl5.toString());
                    mapSend.put("paymentDetailData6", sbDtl6.toString());
                    ReportUtility.templateReportForPaymentDtl(templateFileDtl, mapSend, copy);
                    sbDtl1 = new StringBuilder();
                    sbDtl2 = new StringBuilder();
                    sbDtl3 = new StringBuilder();
                    sbDtl4 = new StringBuilder();
                    sbDtl5 = new StringBuilder();
                    sbDtl6 = new StringBuilder();
                    document.newPage();
                    mapSend.clear();
                    mapSend.put("paymentName", appName);
                    mapSend.put("paymentPage", String.valueOf((tmpCount) / 25 + 1) + "／" + pageControl);
                }
                PaymentProcessCase apnoCase = apnoListDtl.get(j);
                String payCodeString = "";
                if (StringUtils.equals(apnoCase.getApno().substring(0, 1), "L")) {
                    payCodeString = "勞保老年年金給付";
                }
                else if (StringUtils.equals(apnoCase.getApno().substring(0, 1), "K")) {
                    payCodeString = "勞保失能年金給付";
                }
                else {
                    payCodeString = "勞保遺屬年金給付";
                }

                if (j == 0) {
                    sbDtl1.append(payCodeString + "\n");
                    sbDtl2.append(evtName + "\n");
                    sbDtl3.append(apnoCase.getApno() + "\n");
                    startYm = apnoCase.getPayYm();
                    if ((j + 1) == apnoListDtl.size()) {
                        endYm = apnoCase.getPayYm();
                        if (StringUtils.isBlank(endYm)) {
                            endYm = startYm;
                        }
                        sbDtl4.append(startYm + "-" + endYm + "\n");
                        sbDtl5.append(formatBigDecimalToInteger(apnoCase.getPayAmt()) + "\n");
                        sbDtl6.append(String.valueOf(months) + "\n");
                        tmpCount = tmpCount + 1;
                    }
                }
                else if (j < apnoListDtl.size() && (j + 1) != apnoListDtl.size()) {
                    if (StringUtils.equals(apnoListDtl.get(j).getApno(), apnoListDtl.get(j - 1).getApno())
                                    && (StringUtils.equals(apnoCase.getPayYm(), apnoListDtl.get(j - 1).getPayYm()) || StringUtils.equals(apnoListDtl.get(j - 1).getPayYm() + "01", DateUtility.calMonth(apnoCase.getPayYm() + "01", -1)))
                                    && apnoListDtl.get(j - 1).getPayAmt().compareTo(apnoCase.getPayAmt()) == 0) {
                        endYm = apnoCase.getPayYm();
                        if (!StringUtils.equals(apnoCase.getPayYm(), apnoListDtl.get(j - 1).getPayYm())) {
                            months = months + 1;
                        }
                    }
                    else {
                        if (StringUtils.isBlank(endYm)) {
                            endYm = startYm;
                        }
                        sbDtl4.append(startYm + "-" + endYm + "\n");
                        sbDtl5.append(formatBigDecimalToInteger(apnoListDtl.get(j - 1).getPayAmt()) + "\n");
                        sbDtl6.append(String.valueOf(months) + "\n");
                        months = 1;
                        tmpCount = tmpCount + 1;
                        startYm = "";
                        endYm = "";
                        sbDtl1.append(payCodeString + "\n");
                        sbDtl2.append(evtName + "\n");
                        sbDtl3.append(apnoCase.getApno() + "\n");
                        startYm = apnoCase.getPayYm();
                    }
                }
                else {
                    if (StringUtils.equals(apnoListDtl.get(j).getApno(), apnoListDtl.get(j - 1).getApno())
                                    && (StringUtils.equals(apnoCase.getPayYm(), apnoListDtl.get(j - 1).getPayYm()) || StringUtils.equals(apnoListDtl.get(j - 1).getPayYm() + "01", DateUtility.calMonth(apnoCase.getPayYm() + "01", -1)))
                                    && apnoListDtl.get(j - 1).getPayAmt().compareTo(apnoCase.getPayAmt()) == 0) {
                        if (!StringUtils.equals(apnoCase.getPayYm(), apnoListDtl.get(j - 1).getPayYm())) {
                            months = months + 1;
                        }
                        if (StringUtils.isBlank(endYm)) {
                            endYm = startYm;
                        }
                        endYm = apnoCase.getPayYm();
                        sbDtl4.append(startYm + "-" + endYm + "\n");
                        sbDtl5.append(formatBigDecimalToInteger(apnoListDtl.get(j - 1).getPayAmt()) + "\n");
                        sbDtl6.append(String.valueOf(months) + "\n");
                        months = 1;
                        tmpCount = tmpCount + 1;
                    }
                    else {
                        if (StringUtils.isBlank(endYm)) {
                            endYm = startYm;
                        }
                        sbDtl4.append(startYm + "-" + endYm + "\n");
                        sbDtl5.append(formatBigDecimalToInteger(apnoListDtl.get(j - 1).getPayAmt()) + "\n");
                        sbDtl6.append(String.valueOf(months) + "\n");
                        months = 1;
                        tmpCount = tmpCount + 1;
                        sbDtl1.append(payCodeString + "\n");
                        sbDtl2.append(evtName + "\n");
                        sbDtl3.append(apnoCase.getApno() + "\n");
                        startYm = apnoCase.getPayYm();
                        endYm = apnoCase.getPayYm();
                        sbDtl4.append(startYm + "-" + endYm + "\n");
                        sbDtl5.append(formatBigDecimalToInteger(apnoCase.getPayAmt()) + "\n");
                        sbDtl6.append(String.valueOf(months) + "\n");
                    }
                }
            }
            mapSend.put("paymentName", appName);
            mapSend.put("paymentDetailData1", sbDtl1.toString());
            mapSend.put("paymentDetailData2", sbDtl2.toString());
            mapSend.put("paymentDetailData3", sbDtl3.toString());
            mapSend.put("paymentDetailData4", sbDtl4.toString());
            mapSend.put("paymentDetailData5", sbDtl5.toString());
            mapSend.put("paymentDetailData6", sbDtl6.toString());
            mapSend.put("compDate", "補印日期:" + DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(), true));

            ReportUtility.templateReportForPaymentDtl(templateFileDtl, mapSend, copy);
            document.close();
        }
        finally {
            document.close();
        }

        if (inFile == null) {
            return out;
        }
        else {
            return bao;
        }

    }

    public ByteArrayOutputStream doReport(InputStream inFile, String mApno, String evtName, String appName, String mPayKind, String empExt, List<PaymentStageDtlCase> caseList, List<PaymentProcessCase> apnoList, PaymentProcessQueryForm queryForm,
                    String paymentSex) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        try {
            // 2009.2.20 EvelynHsu add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + "_PaymentReport_" + printDate + ".pdf");
            // 讀入 Template 檔
            byte[] templateFile;
            byte[] templateFileDtl;
            if (inFile == null) {
                InputStream inDtl = null, in = null;
                try {
                    inDtl = HttpHelper.getHttpRequest().getSession(true).getServletContext().getResourceAsStream(TEMPLATE_FILE1);
                    in = HttpHelper.getHttpRequest().getSession(true).getServletContext().getResourceAsStream(TEMPLATE_FILE);
                    templateFile = IOUtils.toByteArray(in);
                    templateFileDtl = IOUtils.toByteArray(inDtl);
                }
                finally {
                    ExceptionUtil.safeColse(inDtl);
                    ExceptionUtil.safeColse(in);
                }
            }
            else {
                templateFile = IOUtils.toByteArray(inFile);
                templateFileDtl = IOUtils.toByteArray(inFile);
            }
            String payCodeType = RptTitleUtility.getPaymentTileForPayment(mApno.substring(6, 8));
            String department = RptTitleUtility.getGroupsTitle(mApno.substring(0, 1), "");
            String divisionTitle = RptTitleUtility.getDivisionTitle(mApno.substring(0, 1), "");
            HashMap<String, String> mapSend = new HashMap<String, String>();

            byte[] tempResultFile = null;
            byte[] resultFile = null;

            PdfSmartCopy copy;
            if (inFile == null) {
                copy = new PdfSmartCopy(document, out);
            }
            else {
                copy = new PdfSmartCopy(document, bao);
            }

            document.open();

            for (int i = 0; i < caseList.size(); i = i + 1) {
                List<PaymentProcessCase> apnoListDtl = new ArrayList<PaymentProcessCase>();
                document.newPage();
                mapSend.clear();
                PaymentStageDtlCase caseData = caseList.get(i);
                // 50093111
                mapSend.put("payCodeType1", payCodeType);
                mapSend.put("payCodeType2", payCodeType);
                mapSend.put("zipCode", queryForm.getZipCode());
                if (queryForm.getAddr().length() > 19) {
                    mapSend.put("address", queryForm.getAddr().substring(0, 19));
                    mapSend.put("address2", queryForm.getAddr().substring(19, queryForm.getAddr().length()));
                }
                else {
                    mapSend.put("address", queryForm.getAddr());
                }
                if (StringUtils.equals("1", paymentSex)) {
                    mapSend.put("name", appName + " 先生");
                    ;
                }
                else if (StringUtils.equals("2", paymentSex)) {
                    mapSend.put("name", appName + " 女士");
                }
                else {
                    mapSend.put("name", appName + " 君");
                }

                mapSend.put("memo1", " ※本繳款單請至各地郵局繳納");
                mapSend.put("memo2", " 提醒您，勿至銀行或超商繳納");
                mapSend.put("department1", "洽詢電話：本局" + divisionTitle + "（02）23961266 轉分機 " + empExt);
                // mapSend.put("department2", );

                if (caseList.size() > 1) {
                    mapSend.put("explain1", "台端溢領給付經本局同意按分期繳還，請依限繳納，");
                    mapSend.put("explain2", "如未按期繳納即視同全部到期，須一次繳清，否則將");
                    mapSend.put("explain3", "依法訴追。");
                    mapSend.put("thisStage1", "本期");
                    mapSend.put("thisStage2", "本期");
                }
                mapSend.put("crtDate", DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(), false));
                mapSend.put("nowStage1", (i + 1) + "/" + (caseList.size()));
                mapSend.put("nowStage2", (i + 1) + "/" + (caseList.size()));
                mapSend.put("pageNum", (i + 1) + "/" + (caseList.size()));
                if (StringUtils.equals(queryForm.getChkObj(), "1") && StringUtils.isBlank(caseData.getPaymentDateLine())) {
                    mapSend.put("paymentDateLine1", "文到之翌日起30日內繳納");
                }else if(StringUtils.equals(queryForm.getChkObj(), "3") && StringUtils.isBlank(caseData.getPaymentDateLine())) {
                    mapSend.put("paymentDateLine1", "文到之翌日起15日內繳納");
                }
                else {
                    mapSend.put("paymentDateLine1", DateUtility.formatChineseDateString(caseData.getPaymentDateLine(), true));
                }
                mapSend.put("stagePayAmt1", formatBigDecimalToInteger(caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()))) + "元	");
                mapSend.put("applyName1", appName);
                mapSend.put("appName1", appName);
                mapSend.put("totAmt", formatBigDecimalToInteger(queryForm.getTotAmt()) + " 元");

                // if(queryForm.getTotInterst().compareTo(BigDecimal.ZERO)<=0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO)<=0 ){
                // mapSend.put("explain4", "，本金"+formatBigDecimalToInteger(queryForm.getPayTotAmt())+"元");
                // }else
                if (caseList.size() == 1) {
                    if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) > 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) <= 0) {
                        mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(queryForm.getPayTotAmt()) + "元、利息" + formatBigDecimalToInteger(queryForm.getTotInterst()) + "元）");
                    }
                    else if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) <= 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) > 0) {
                        mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(queryForm.getPayTotAmt()) + "元、執行費用" + formatBigDecimalToInteger(queryForm.getTotExec()) + "元）");
                    }
                    else if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) > 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) > 0) {
                        mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(queryForm.getPayTotAmt()) + "元、利息" + formatBigDecimalToInteger(queryForm.getTotInterst()) + "元、執行費用" + formatBigDecimalToInteger(queryForm.getTotExec()) + "元）");
                    }
                }
                else {
                    if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) > 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) <= 0) {
                        mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(queryForm.getPayTotAmt()) + "元、利息" + formatBigDecimalToInteger(queryForm.getTotInterst()) + "元），本次攤還"
                                        + formatBigDecimalToInteger(caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()))) + "元");
                    }
                    else if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) <= 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) > 0) {
                        mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(queryForm.getPayTotAmt()) + "元、執行費用" + formatBigDecimalToInteger(queryForm.getTotExec()) + "元），本次攤還"
                                        + formatBigDecimalToInteger(caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()))) + "元");
                    }
                    else if (queryForm.getTotInterst().compareTo(BigDecimal.ZERO) > 0 && queryForm.getTotExec().compareTo(BigDecimal.ZERO) > 0) {
                        mapSend.put("explain4", "（含本金" + formatBigDecimalToInteger(queryForm.getPayTotAmt()) + "元、利息" + formatBigDecimalToInteger(queryForm.getTotInterst()) + "元、執行費用" + formatBigDecimalToInteger(queryForm.getTotExec()) + "元），本次攤還"
                                        + formatBigDecimalToInteger(caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()))) + "元");
                    }
                    else if (caseList.size() > 1) {
                        mapSend.put("explain4", "，本次攤還" + formatBigDecimalToInteger(queryForm.getPayTotAmt()) + "元");
                    }
                }

                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                StringBuilder sb3 = new StringBuilder();
                // for(int j=0;j<apnoList.size();j++){
                // PaymentProcessCase apnoCase = apnoList.get(j);
                // String payCodeString ="";
                // if(StringUtils.equals(apnoCase.getApno().substring(0,1), "L")){
                // payCodeString = "勞保老年年金給付";
                // }else if(StringUtils.equals(apnoCase.getApno().substring(0,1), "K")){
                // payCodeString = "勞保失能年金給付";
                // }else{
                // payCodeString = "勞保遺屬年金給付";
                // }
                // sb1.append(payCodeString+"\n");
                // sb2.append(apnoCase.getBenName()+"\n");
                // sb3.append(apnoCase.getApno()+"\n");
                // sb4.append(formatBigDecimalToInteger(apnoCase.getPayAmt())+"\n");
                //
                // }

                for (PaymentProcessCase cs : apnoList) {
                    if (!StringUtils.isBlank(cs.getWriteoffSeqNo()) && !StringUtils.equals(cs.getWriteoffSeqNo(), "0")) {
                        apnoListDtl.add(cs);
                    }
                }
                String payCodeStringL = "";
                BigDecimal totPayAmtL = BigDecimal.ZERO;
                String payCodeStringS = "";
                BigDecimal totPayAmtS = BigDecimal.ZERO;
                String payCodeStringK = "";
                BigDecimal totPayAmtK = BigDecimal.ZERO;
                for (int j = 0; j < apnoListDtl.size(); j++) {
                    PaymentProcessCase apnoCase = apnoListDtl.get(j);
                    if (StringUtils.equals(apnoCase.getApno().substring(0, 1), "L")) {
                        payCodeStringL = "勞保老年年金給付";
                        totPayAmtL = totPayAmtL.add(apnoCase.getPayAmt());

                    }
                    else if (StringUtils.equals(apnoCase.getApno().substring(0, 1), "K")) {
                        payCodeStringK = "勞保失能年金給付";
                        totPayAmtK = totPayAmtK.add(apnoCase.getPayAmt());
                    }
                    else {
                        payCodeStringS = "勞保遺屬年金給付";
                        totPayAmtS = totPayAmtS.add(apnoCase.getPayAmt());
                    }

                    /*
                     * if(j==0){ sb1.append(payCodeString+"\n"); sb2.append(evtName+"\n"); sb3.append(apnoCase.getApno()+"\n"); sb5.append(apnoCase.getPayYm()+"\n"); totPayAmt = totPayAmt.add(apnoCase.getPayAmt()); if((j+1)==apnoListDtl.size()){
                     * sb4.append(formatBigDecimalToInteger(totPayAmt)+"\n"); } }else if(j<apnoListDtl.size()&& (j+1)!=apnoListDtl.size()){ if(StringUtils.equals(apnoListDtl.get(j).getPayYm(), apnoListDtl.get(j-1).getPayYm()) &&
                     * StringUtils.equals(apnoListDtl.get(j).getApno(), apnoListDtl.get(j-1).getApno())){ totPayAmt = totPayAmt.add(apnoCase.getPayAmt()); }else{ sb4.append(formatBigDecimalToInteger(totPayAmt)+"\n"); totPayAmt = BigDecimal.ZERO;
                     * sb1.append(payCodeString+"\n"); sb2.append(evtName+"\n"); sb3.append(apnoCase.getApno()+"\n"); sb5.append(apnoCase.getPayYm()+"\n"); totPayAmt = totPayAmt.add(apnoCase.getPayAmt()); } }else{
                     * if(StringUtils.equals(apnoListDtl.get(j).getPayYm(), apnoListDtl.get(j-1).getPayYm()) && StringUtils.equals(apnoListDtl.get(j).getApno(), apnoListDtl.get(j-1).getApno())){ totPayAmt = totPayAmt.add(apnoCase.getPayAmt());
                     * sb4.append(formatBigDecimalToInteger(totPayAmt)+"\n"); }else{ sb4.append(formatBigDecimalToInteger(totPayAmt)+"\n"); totPayAmt = BigDecimal.ZERO; sb1.append(payCodeString+"\n"); sb2.append(evtName+"\n");
                     * sb3.append(apnoCase.getApno()+"\n"); totPayAmt = totPayAmt.add(apnoCase.getPayAmt()); sb4.append(formatBigDecimalToInteger(totPayAmt)+"\n"); sb5.append(apnoCase.getPayYm()+"\n"); } }
                     */
                }
                if (StringUtils.isNotBlank(payCodeStringL)) {
                    sb1.append(payCodeStringL + "\n");
                    sb2.append(evtName + "\n");
                    sb3.append(formatBigDecimalToInteger(totPayAmtL) + "\n");
                }
                if (StringUtils.isNotBlank(payCodeStringK)) {
                    sb1.append(payCodeStringK + "\n");
                    sb2.append(evtName + "\n");
                    sb3.append(formatBigDecimalToInteger(totPayAmtK) + "\n");
                }
                if (StringUtils.isNotBlank(payCodeStringS)) {
                    sb1.append(payCodeStringS + "\n");
                    sb2.append(evtName + "\n");
                    sb3.append(formatBigDecimalToInteger(totPayAmtS) + "\n");
                }
                mapSend.put("detailData1", sb1.toString());
                mapSend.put("detailData2", sb2.toString());
                mapSend.put("detailData3", sb3.toString());
                BigDecimal thisPayAmt = caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()));
                // 條碼產生
                String firstTwCode = getTwCodeFirst(caseData.getPaymentDateLine());
                String secondTwCode = getTwCodeSecond(payCodeType, caseData.getPaymentNoDetail());
                String thirdTwCode = getTwCodeThird(firstTwCode, secondTwCode, caseData.getPaymentDateLine(), thisPayAmt);
                mapSend.put("twCode1", firstTwCode + "   " + secondTwCode + "   " + thirdTwCode);
                // 表尾
                mapSend.put("appName", evtName);
                mapSend.put("applyName2", appName);
                mapSend.put("nowStage1", (i + 1) + "/" + (caseList.size()));
                if (StringUtils.equals(queryForm.getChkObj(), "1") && StringUtils.isBlank(caseData.getPaymentDateLine())) {
                    mapSend.put("paymentDateLine2", "文到之翌日起30日內繳納");
                }else if(StringUtils.equals(queryForm.getChkObj(), "3") && StringUtils.isBlank(caseData.getPaymentDateLine())) {
                    mapSend.put("paymentDateLine2", "文到之翌日起15日內繳納");
                }
                else {
                    mapSend.put("paymentDateLine2", DateUtility.formatChineseDateString(caseData.getPaymentDateLine(), true));
                }
                mapSend.put("stagePayAmt2", formatBigDecimalToInteger(caseData.getRePayAmt().add(caseData.getExecAmt().add(caseData.getPayInterest()))));

                mapSend.put("barCode1", firstTwCode);
                mapSend.put("barCode2", secondTwCode);
                mapSend.put("barCode3", thirdTwCode);

                mapSend.put("barCodeText1", StringUtils.defaultString(firstTwCode + "(條碼一)"));
                mapSend.put("barCodeText2", StringUtils.defaultString(secondTwCode + "(條碼二)"));
                mapSend.put("barCodeText3", StringUtils.defaultString(thirdTwCode + "(條碼三 )"));
                if (StringUtils.isNotBlank(firstTwCode) && StringUtils.isNotBlank(firstTwCode) && StringUtils.isNotBlank(firstTwCode)) {
                    caseList.get(i).setBarCode1(firstTwCode);
                    caseList.get(i).setBarCode2(secondTwCode);
                    caseList.get(i).setBarCode3(thirdTwCode);
                }

                if (StringUtils.isNotBlank(caseData.getCompDate())) {
                    mapSend.put("compDate", "補印日期:" + caseData.getCompDate());
                }
                // Image a = getBarCodeImage(firstTwCode, 70, 120, 480, 775);
                ReportUtility.templateReportForPayment(firstTwCode, secondTwCode, thirdTwCode, templateFile, mapSend, copy);

            }
            List<PaymentProcessCase> apnoListDtl = new ArrayList<PaymentProcessCase>();

            for (int x = 0; x < apnoList.size(); x++) {
                PaymentProcessCase cs = apnoList.get(x);
                if (x > 0) {
                    if (apnoListDtl.size() > 0) {
                        if (!StringUtils.isBlank(cs.getWriteoffSeqNo()) && !StringUtils.equals(cs.getWriteoffSeqNo(), "0")) {
                            if (StringUtils.equals(cs.getPayYm(), apnoListDtl.get(apnoListDtl.size() - 1).getPayYm())) {
                                apnoListDtl.get(apnoListDtl.size() - 1).setPayAmt(cs.getPayAmt().add(apnoListDtl.get(apnoListDtl.size() - 1).getPayAmt()));
                            }
                            else {
                                apnoListDtl.add(cs);
                            }
                        }
                    }
                    else if (!StringUtils.isBlank(cs.getWriteoffSeqNo()) && !StringUtils.equals(cs.getWriteoffSeqNo(), "0")) {
                        apnoListDtl.add(cs);
                    }
                }
                else if (!StringUtils.isBlank(cs.getWriteoffSeqNo()) && !StringUtils.equals(cs.getWriteoffSeqNo(), "0")) {
                    apnoListDtl.add(cs);
                }
            }
            String pageControl = String.valueOf((apnoListDtl.size() / 25) + 1);

            // dtl報表
            document.newPage();
            mapSend.clear();
            mapSend.put("paymentDate", DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(), false));
            mapSend.put("paymentPage", "1" + "/" + pageControl);
            StringBuilder sbDtl1 = new StringBuilder();
            StringBuilder sbDtl2 = new StringBuilder();
            StringBuilder sbDtl3 = new StringBuilder();
            StringBuilder sbDtl4 = new StringBuilder();
            StringBuilder sbDtl5 = new StringBuilder();
            StringBuilder sbDtl6 = new StringBuilder();
            String startYm = "";
            String endYm = "";
            int tmpCount = 1;
            int months = 1;
            for (int j = 0; j < apnoListDtl.size(); j++) {
                if ((tmpCount) % 25 == 0) {
                    mapSend.put("paymentDetailData1", sbDtl1.toString());
                    mapSend.put("paymentDetailData2", sbDtl2.toString());
                    mapSend.put("paymentDetailData3", sbDtl3.toString());
                    mapSend.put("paymentDetailData4", sbDtl4.toString());
                    mapSend.put("paymentDetailData5", sbDtl5.toString());
                    mapSend.put("paymentDetailData6", sbDtl6.toString());
                    ReportUtility.templateReportForPaymentDtl(templateFileDtl, mapSend, copy);
                    sbDtl1 = new StringBuilder();
                    sbDtl2 = new StringBuilder();
                    sbDtl3 = new StringBuilder();
                    sbDtl4 = new StringBuilder();
                    sbDtl5 = new StringBuilder();
                    sbDtl6 = new StringBuilder();
                    document.newPage();
                    mapSend.clear();
                    mapSend.put("paymentName", appName);
                    mapSend.put("paymentPage", String.valueOf((tmpCount) / 25 + 1) + "／" + pageControl);
                }
                PaymentProcessCase apnoCase = apnoListDtl.get(j);
                String payCodeString = "";
                if (StringUtils.equals(apnoCase.getApno().substring(0, 1), "L")) {
                    payCodeString = "勞保老年年金給付";
                }
                else if (StringUtils.equals(apnoCase.getApno().substring(0, 1), "K")) {
                    payCodeString = "勞保失能年金給付";
                }
                else {
                    payCodeString = "勞保遺屬年金給付";
                }

                if (j == 0) {
                    sbDtl1.append(payCodeString + "\n");
                    sbDtl2.append(evtName + "\n");
                    sbDtl3.append(apnoCase.getApno() + "\n");
                    startYm = apnoCase.getPayYm();
                    if ((j + 1) == apnoListDtl.size()) {
                        endYm = apnoCase.getPayYm();
                        if (StringUtils.isBlank(endYm)) {
                            endYm = startYm;
                        }
                        sbDtl4.append(startYm + "-" + endYm + "\n");
                        sbDtl5.append(formatBigDecimalToInteger(apnoCase.getPayAmt()) + "\n");

                        tmpCount = tmpCount + 1;
                        sbDtl6.append(String.valueOf(months));

                    }
                }
                else if (j < apnoListDtl.size() && (j + 1) != apnoListDtl.size()) {
                    if (StringUtils.equals(apnoListDtl.get(j).getApno(), apnoListDtl.get(j - 1).getApno())
                                    && (StringUtils.equals(apnoCase.getPayYm(), apnoListDtl.get(j - 1).getPayYm()) || StringUtils.equals(apnoListDtl.get(j - 1).getPayYm() + "01", DateUtility.calMonth(apnoCase.getPayYm() + "01", -1)))
                                    && apnoListDtl.get(j - 1).getPayAmt().compareTo(apnoCase.getPayAmt()) == 0) {
                        endYm = apnoCase.getPayYm();
                        if (!StringUtils.equals(apnoCase.getPayYm(), apnoListDtl.get(j - 1).getPayYm())) {
                            months = months + 1;
                        }
                    }
                    else {
                        if (StringUtils.isBlank(endYm)) {
                            endYm = startYm;
                        }
                        sbDtl4.append(startYm + "-" + endYm + "\n");
                        sbDtl5.append(formatBigDecimalToInteger(apnoListDtl.get(j - 1).getPayAmt()) + "\n");
                        sbDtl6.append(String.valueOf(months) + "\n");
                        months = 1;
                        tmpCount = tmpCount + 1;
                        startYm = "";
                        endYm = "";
                        sbDtl1.append(payCodeString + "\n");
                        sbDtl2.append(evtName + "\n");
                        sbDtl3.append(apnoCase.getApno() + "\n");
                        startYm = apnoCase.getPayYm();
                    }
                }
                else {
                    if (StringUtils.equals(apnoListDtl.get(j).getApno(), apnoListDtl.get(j - 1).getApno())
                                    && (StringUtils.equals(apnoCase.getPayYm(), apnoListDtl.get(j - 1).getPayYm()) || StringUtils.equals(apnoListDtl.get(j - 1).getPayYm() + "01", DateUtility.calMonth(apnoCase.getPayYm() + "01", -1)))
                                    && apnoListDtl.get(j - 1).getPayAmt().compareTo(apnoCase.getPayAmt()) == 0) {
                        if (!StringUtils.equals(apnoCase.getPayYm(), apnoListDtl.get(j - 1).getPayYm())) {
                            months = months + 1;
                        }
                        endYm = apnoCase.getPayYm();
                        if (StringUtils.isBlank(endYm)) {
                            endYm = startYm;
                        }
                        sbDtl4.append(startYm + "-" + endYm + "\n");
                        sbDtl5.append(formatBigDecimalToInteger(apnoListDtl.get(j - 1).getPayAmt()) + "\n");
                        sbDtl6.append(String.valueOf(months) + "\n");
                        months = 1;
                        tmpCount = tmpCount + 1;

                    }
                    else {
                        if (StringUtils.isBlank(endYm)) {
                            endYm = startYm;
                        }
                        sbDtl4.append(startYm + "-" + endYm + "\n");
                        sbDtl5.append(formatBigDecimalToInteger(apnoListDtl.get(j - 1).getPayAmt()) + "\n");
                        sbDtl6.append(String.valueOf(months) + "\n");
                        months = 1;
                        tmpCount = tmpCount + 1;
                        sbDtl1.append(payCodeString + "\n");
                        sbDtl2.append(evtName + "\n");
                        sbDtl3.append(apnoCase.getApno() + "\n");
                        startYm = apnoCase.getPayYm();
                        endYm = apnoCase.getPayYm();
                        if (StringUtils.isBlank(endYm)) {
                            endYm = startYm;
                        }
                        sbDtl4.append(startYm + "-" + endYm + "\n");
                        sbDtl5.append(formatBigDecimalToInteger(apnoCase.getPayAmt()) + "\n");
                        sbDtl6.append(String.valueOf(months) + "\n");
                        months = 1;
                    }
                }
            }
            mapSend.put("paymentName", appName);
            mapSend.put("paymentDetailData1", sbDtl1.toString());
            mapSend.put("paymentDetailData2", sbDtl2.toString());
            mapSend.put("paymentDetailData3", sbDtl3.toString());
            mapSend.put("paymentDetailData4", sbDtl4.toString());
            mapSend.put("paymentDetailData5", sbDtl5.toString());
            mapSend.put("paymentDetailData6", sbDtl6.toString());
            ReportUtility.templateReportForPaymentDtl(templateFileDtl, mapSend, copy);
            document.close();
        }
        finally {
            document.close();
        }

        if (inFile == null) {
            return out;
        }
        else {
            return bao;
        }

    }

    /*
     * 條碼第一段
     */
    public String getTwCodeFirst(String paymentDateLine) {
        if (StringUtils.isNotBlank(paymentDateLine)) {
            return StringUtils.substring(DateUtility.calYear(paymentDateLine, 1), 1, 7) + "6ZC";
        }
        else {
            return StringUtils.substring(DateUtility.calYear(DateUtility.getNowChineseDate(), 1), 1, 7) + "6ZC";
        }
    }

    /*
     * 條碼第二段
     */
    public String getTwCodeSecond(String payCodeType, String paymentNoDetail) {
        String twCodeSecond = "";
        if (StringUtils.equals(payCodeType, "勞工")) {
            twCodeSecond = "1";
        }
        else {
            twCodeSecond = "2";
        }
        return twCodeSecond + paymentNoDetail;
    }

    /*
     * 條碼第三段
     */
    public String getTwCodeThird(String firstTwCode, String secondTwCode, String paymentDateLine, BigDecimal thisPayAmt) {
        int[] aryFirst = new int[firstTwCode.length()]; // 宣告一個整數陣列，長度為字串 s 的長度
        int oddNum = 0;
        int evenNum = 0;
        for (int i = 0; i < firstTwCode.length(); i++) {
            // 利用迴圈，依序讀取字串 s 中的每一個字元
            String transFirst = firstTwCode.substring(i, i + 1); // 利用 substring() 來捉每一個字元
            if (StringUtils.equals(transFirst, "Z")) {
                transFirst = "9";
            }
            else if (StringUtils.equals(transFirst, "C")) {
                transFirst = "3";
            }
            aryFirst[i] = Integer.parseInt(transFirst); // 將捉到的字元轉為整數，存入陣列
            if (i % 2 == 0) {
                evenNum = evenNum + aryFirst[i];

            }
            else {
                oddNum = oddNum + aryFirst[i];

            }

        }
        int[] arySecond = new int[secondTwCode.length()]; // 宣告一個整數陣列，長度為字串 s 的長度
        for (int i = 0; i < secondTwCode.length(); i++) {
            // 利用迴圈，依序讀取字串 s 中的每一個字元
            String transSecond = secondTwCode.substring(i, i + 1); // 利用 substring() 來捉每一個字元
            if (StringUtils.equals(transSecond, "Z")) {
                transSecond = "9";
            }
            else if (StringUtils.equals(transSecond, "C")) {
                transSecond = "3";
            }
            arySecond[i] = Integer.parseInt(transSecond); // 將捉到的字元轉為整數，存入陣列
            if (i % 2 == 0) {
                evenNum = evenNum + arySecond[i];
            }
            else {
                oddNum = oddNum + arySecond[i];
            }
        }
        String thirdTwCode = "";
        if (StringUtils.isNotBlank(paymentDateLine)) {
            thirdTwCode = StringUtils.substring(paymentDateLine, 1, 5);
        }
        else {
            thirdTwCode = StringUtils.substring(DateUtility.getNowChineseDate(), 1, 5);
        }

        String payAmt = StringUtility.chtLeftPad(thisPayAmt.toString(), 9, "0");
        thirdTwCode = thirdTwCode + payAmt;
        int[] aryThird = new int[thirdTwCode.length()]; // 宣告一個整數陣列，長度為字串 s 的長度
        for (int i = 0; i < thirdTwCode.length(); i++) {
            // 利用迴圈，依序讀取字串 s 中的每一個字元
            String transthird = thirdTwCode.substring(i, i + 1); // 利用 substring() 來捉每一個字元
            if (StringUtils.equals(transthird, "Z")) {
                transthird = "9";
            }
            else if (StringUtils.equals(transthird, "C")) {
                transthird = "3";
            }
            aryThird[i] = Integer.parseInt(transthird); // 將捉到的字元轉為整數，存入陣列
            if (i % 2 == 0) {
                evenNum = evenNum + aryThird[i];
            }
            else {
                oddNum = oddNum + aryThird[i];
            }
        }

        if (StringUtils.isNotBlank(paymentDateLine)) {
            paymentDateLine = StringUtils.substring(paymentDateLine, 1, 5);
        }
        else {
            paymentDateLine = StringUtils.substring(DateUtility.getNowChineseDate(), 1, 5);
        }
        String secondCheck = String.valueOf(oddNum % 11);
        String firstCheck = String.valueOf(evenNum % 11);
        if (StringUtils.equals(firstCheck, "0")) {
            firstCheck = "A";
        }
        if (StringUtils.equals(secondCheck, "0")) {
            secondCheck = "X";
        }
        if (StringUtils.equals(firstCheck, "10")) {
            firstCheck = "B";
        }
        if (StringUtils.equals(secondCheck, "10")) {
            secondCheck = "Y";
        }
        String thirdCode = paymentDateLine + firstCheck + secondCheck + payAmt;
        return thirdCode;
    }

}
