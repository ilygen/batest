package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.owasp.encoder.Encode;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt16Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.StringUtility;

public class MonthlyRpt16Report extends ReportBase {

    private RptService rptService;
    private final String bliAddress = "10013\r\n" + "台北市中正區羅斯福路一段四號\r\n" + "電話：(02)2396-1266 轉";
    private String printDate = ""; // 印表日期
    private int seqNo = 1;

    public MonthlyRpt16Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(true);
    }

    public Document createDocument() {
        Rectangle rectangle = new Rectangle(576, 862);
        Document document = new Document(rectangle, 20, 20, 0, 0);
        return document;
    }

    public Table printPage(MonthlyRpt16Case caseData, String description1) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(60);

        addColumn(table, 60, 1, "　", fontKCh20, 0, RIGHT);

        addColumn(table, 10, 1, " ", fontKCh20, 0, RIGHT);
        addColumnAssignLineSpace(table, 40, 1, "勞動部勞工保險局" + "      函", fontKCh22b, 0, 0, CENTER);
        addColumn(table, 10, 1, " ", fontKCh20, 0, RIGHT);

        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);

        addColumnAssignLineSpace(table, 6, 1, "地  址：", fontKCh12, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 29, 1, StringUtility.replaceSpaceString(caseData.getCommZip()), fontKCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 25, 1, "機關地址：10013台北市羅斯福路1段4號", fontKCh11, 0, 0, LEFT);

        addColumnAssignLineSpace(table, 6, 1, "    ", fontKCh12, 0, 0, RIGHT);
        addColumnAssignLineSpace(table, 29, 1, StringUtility.replaceSpaceString(caseData.getCommAddr()), fontKCh12, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 25, 1, "承辦單位：普通事故給付組保險收支科", fontKCh11, 0, 0, LEFT);

        addColumn(table, 35, 1, " ", fontKCh20, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "聯絡電話：(02)2396-1266 分機：2212　", fontKCh11, 0, 0, LEFT);
        addColumn(table, 35, 1, " ", fontKCh20, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, "傳真電話：02-23223747", fontKCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 35, 1, "受文者：" + StringUtility.replaceSpaceString(caseData.getBenName()) + "　君", fontKCh16, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 25, 1, "受理編號：" + caseData.getApNo(), fontKCh11, 0, 0, LEFT);

        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);

        addColumnAssignLineSpace(table, 60, 1, "發文日期：中華民國 " + DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(), true), fontKCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "發文字號：勞保退匯字第" + caseData.getApNo() + "號", fontKCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "速別：普通件", fontKCh11, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 60, 1, "密等及解密條件或保密期限：普通", fontKCh11, 0, 0, LEFT);

        addColumnAssignLineSpace(table, 60, 1, "附件：", fontKCh11, 0, 0, LEFT);
        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);

        addColumnAssignLineSpace(table, 5, 1, "主旨：", fontKCh11, 5, 0, LEFT);
        addColumnAssignLineSpace(table, 55, 1, description1, fontKCh12, 5, 0, JUSTIFIED);

        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);

        addColumnAssignLineSpace(table, 5, 1, "正本：", fontKCh12, 5, 0, LEFT);
        addColumnAssignLineSpace(table, 55, 1, StringUtility.replaceSpaceString(caseData.getBenName()) + "　君", fontKCh12, 5, 0, LEFT);
        addColumnAssignLineSpace(table, 5, 1, "副本：", fontKCh12, 5, 0, LEFT);
        addColumnAssignLineSpace(table, 55, 1, "", fontKCh12, 5, 0, LEFT);

        while (writer.fitsPage(table)) {
            addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT);
        }

        for (int i = 0; i < 19; i++)
            table.deleteLastRow();
        // 列印總經理
        if (StringUtils.isNotBlank(caseData.getManager())) {
            addColumn(table, 60, 1, "     " + caseData.getManager(), fontKCh20, 0, LEFT);
        }
        else {
            addColumn(table, 60, 1, "     ", fontKCh20, 0, LEFT);
        }
        for (int i = 0; i < 2; i++) {
            addColumnAssignLineSpace(table, 60, 1, "　", fontKCh20, 1, 0, RIGHT);
        }

        return table;
    }

    public Table printPage2(MonthlyRpt16Case caseData, String description1) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(60);

        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);

        addColumn(table, 10, 1, " ", fontKCh20, 0, RIGHT);
        addColumnAssignLineSpace(table, 40, 1, "勞保年金給付改匯回條", fontKCh22b, 0, 0, CENTER);
        addColumn(table, 10, 1, " ", fontKCh20, 0, RIGHT);

        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);

        addColumnAssignLineSpace(table, 15, 1, "姓名：" + StringUtility.replaceSpaceString(caseData.getBenName()), fontKCh16, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 25, 1, "身分證號：" + StringUtils.trimToEmpty(caseData.getBenIdnString()), fontKCh16, 0, 0, LEFT);
        addColumnAssignLineSpace(table, 20, 1, "電話：" + StringUtils.trimToEmpty(caseData.getCommTelString()), fontKCh16, 0, 0, LEFT);

        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);

        addColumnAssignLineSpace(table, 2, 1, "1.", fontKCh12, 1, 0, LEFT);
        addColumnAssignVAlignment(table, 2, 1, " ", fontCh1, 1, 1, LEFT);
        addColumnAssignLineSpace(table, 26, 1, "匯入本人金融機構帳戶：金融機構名稱：", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 30, 1, "＿＿＿＿＿＿＿ 銀行＿＿＿＿＿＿＿＿分行", fontKCh12, 1, 0, LEFT);

        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);

        addColumnAssignLineSpace(table, 5, 1, "　", fontKCh12, 1, 0, LEFT);
        addColumnAssignVAlignment(table, 6, 1, "總代號", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignLineSpace(table, 2, 1, "　", fontKCh12, 1, 0, LEFT);
        addColumnAssignVAlignment(table, 8, 1, "分支代號", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignLineSpace(table, 2, 1, "　", fontKCh12, 1, 0, LEFT);
        addColumnAssignVAlignment(table, 2, 1, "帳", fontKCh11, 1, 13, CENTER, CENTER);
        addColumnAssignVAlignment(table, 28, 1, "金融機構存款帳號(分行別、科目、編號、檢查號碼)", fontKCh10, 1, 15, CENTER, CENTER);
        addColumnAssignLineSpace(table, 7, 1, "　", fontKCh11, 1, 0, LEFT);

        addColumnAssignLineSpace(table, 5, 1, "　", fontKCh12, 1, 0, LEFT);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignLineSpace(table, 2, 1, "　", fontKCh12, 1, 0, LEFT);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignLineSpace(table, 2, 1, "　", fontKCh12, 1, 0, LEFT);
        addColumnAssignVAlignment(table, 2, 1, "號", fontKCh11, 1, 14, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignVAlignment(table, 2, 1, "　", fontKCh11, 1, 15, CENTER, CENTER);
        addColumnAssignLineSpace(table, 7, 1, "　", fontKCh11, 1, 0, LEFT);

        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);

        addColumnAssignLineSpace(table, 2, 1, "2.", fontKCh12, 1, 0, LEFT);
        addColumnAssignVAlignment(table, 2, 1, " ", fontCh1, 1, 1, LEFT);
        addColumnAssignLineSpace(table, 46, 1, "匯入本人郵局帳戶：(郵政存簿儲金局號或帳號不足七位者，請在左邊補零)", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 10, 1, " ", fontKCh12, 1, 0, LEFT);

        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);

        addColumnAssignLineSpace(table, 5, 1, " ", fontKCh12, 1, 0, MIDDLE);
        addColumnAssignLineSpace(table, 7, 1, "局號：", fontKCh12, 0, 0, JUSTIFIED);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, MIDDLE);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, MIDDLE);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, MIDDLE);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, MIDDLE);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, MIDDLE);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, MIDDLE);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, MIDDLE);
        addColumnAssignLineSpace(table, 4, 1, " ", fontKCh12, 1, 0, MIDDLE);
        addColumnAssignLineSpace(table, 7, 1, "帳號：", fontKCh12, 0, 0, JUSTIFIED);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, CENTER);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, CENTER);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, CENTER);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, CENTER);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, CENTER);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, CENTER);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 1, CENTER);
        addColumnAssignLineSpace(table, 9, 1, " ", fontKCh12, 1, 0, CENTER);

        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);

        addColumnAssignVAlignment(table, 35, 1, "　", fontKCh11, 1, 0, CENTER, CENTER);
        addColumnAssignVAlignment(table, 6, 1, "　", fontKCh11, 1, 13, CENTER, CENTER);
        addColumnAssignVAlignment(table, 19, 1, "　", fontKCh11, 1, 0, CENTER, CENTER);

        addColumnAssignLineSpace(table, 5, 1, " ", fontKCh12, 1, 0, MIDDLE);
        addColumnAssignVAlignment(table, 30, 1, "申請人簽章：", fontKCh18, 1, 0, LEFT, LEFT);
        addColumnAssignVAlignment(table, 6, 1, "　", fontKCh11, 1, 12, CENTER, CENTER);
        addColumnAssignVAlignment(table, 19, 1, "　", fontKCh11, 1, 0, CENTER, CENTER);

        addColumnAssignVAlignment(table, 35, 1, "　", fontKCh11, 1, 0, CENTER, CENTER);
        addColumnAssignVAlignment(table, 6, 1, "　", fontKCh11, 1, 14, CENTER, CENTER);
        addColumnAssignVAlignment(table, 19, 1, "　", fontKCh11, 1, 0, CENTER, CENTER);

        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);
        addColumn(table, 60, 1, " ", fontKCh20, 0, RIGHT);

        addColumnAssignLineSpace(table, 2, 1, "註", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 58, 1, "1.請黏貼存摺封面影本後傳真或寄回本局", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 58, 1, "2.寄回地址：10013-臺北市中正區羅斯福路1段4號", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 58, 1, "3.傳真號碼：02-23223747", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 2, 1, " ", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 58, 1, "4.退匯原因如為「收受者統編錯誤」，發生原因及處理方法：", fontKCh12, 1, 0, LEFT);

        addColumnAssignLineSpace(table, 4, 1, " ", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 56, 1, "(1)開戶時所使用身分證件(如居留證)與現在不同--請洽金融機構或郵局更正一致後", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 4, 1, " ", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 56, 1, "　再來電(02-23961266 轉 2212) 通知本局改匯。", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 4, 1, " ", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 56, 1, "(2)非本人帳戶或帳號錯誤--請重新提供本人目前正常使用中帳戶。", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 7, 1, " ", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 53, 1, "核付金額：" + formatBigDecimalToInteger(caseData.getRemitAmtNonNull()), fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 7, 1, " ", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 53, 1, "受理編號：" + StringUtils.trimToEmpty(caseData.getApNoString()), fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 7, 1, " ", fontKCh12, 1, 0, LEFT);
        addColumnAssignLineSpace(table, 53, 1, "退回日期：" + caseData.getBrChkDateChtString(), fontKCh12, 1, 0, LEFT);

        addColumnAssignLineSpace(table, 60, 1, "‧‧‧‧‧請將存摺封面影本黏貼於此處‧‧‧‧‧", fontKCh12, 1, 0, CENTER);

        for (int i = 0; i < 9; i++) {
            addColumnAssignLineSpace(table, 60, 1, "　", fontKCh20, 1, 0, RIGHT);
        }

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt16Case> caseList, boolean isBach, HashMap<String, Object> map) throws Exception {

        try {
            String payCode = (String) map.get("payCode");

            document.open();

            Table table = null;
            Table table2 = null;

            for (MonthlyRpt16Case caseData : caseList) {

                // 勞工保險局給付處通知單 - 通知事項 第一點
                String description1 = "台端" + StringUtility.replaceSpaceString(caseData.getBenName()) + "君，請領" + caseData.getPayCodeString() + "給付" + "（受理編號為 " + StringUtils.trimToEmpty(caseData.getApNoString()) + "）計 "
                                + formatBigDecimalToInteger(caseData.getRemitAmtNonNull()) + " 元，" + "前經本局於 " + caseData.getPayDateString() + "核付，因" + StringUtility.replaceSpaceString(caseData.getBrNote()) + "，於 " + caseData.getBrChkDateChtString()
                                + "退回本局。為重新匯款，請填寫帳戶資料於改匯回條內（如次頁），連同存摺封面影本寄回或傳真本局，以利改匯作業。";
                // String description1 = "台端" + StringUtility.replaceSpaceString(caseData.getBenName()) + "君，請領" + caseData.getPayCodeString() + "給付" + "（受理編號為 " + StringUtils.trimToEmpty(caseData.getApNoString()) + "）計 " +
                // formatBigDecimalToInteger(caseData.getRemitAmtNonNull()) + " 元，" + "前經本局於 " + caseData.getPayDateString() + "核付，因" + StringUtility.replaceSpaceString(caseData.getBrNote()) + "，於 " + caseData.getBrChkDateChtString() +
                // "退回本局。為重新再匯款，請填寫正確資料於下聯改匯回條內，速予回覆（寄回或傳真)，以免影響本局續發作業。";
                caseData.setPayCode(payCode);
                table = printPage(caseData, description1);
                table2 = printPage2(caseData, description1);
                document.add(table);
                document.add(table2);
            }
            document.close();

            // 2009.2.20 EvelynHsu add 輸出報表檔至主機上
            if (isBach) {
                String printDate = DateUtility.getNowChineseDate();
                // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt16_" + Encode.forJava(printDate) + ".pdf");
                // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt16_" + printDate + ".pdf");
                // writer = PdfWriter.getInstance(document, bao);
                // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
                printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt16_" + Encode.forJava(printDate) + ".pdf"), bao);
            }
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
