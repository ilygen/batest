package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt06Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.helper.SpringHelper;

public class MonthlyRpt06Report extends ReportBase {

    private RptService rptService;

    public MonthlyRpt06Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        return document;
    }

    public Table getHeader(boolean bPrintMainHeader, String payTyp, String caseTypStr, String issuYm, String payCode) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(42);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 34, 1, "報表編號：BALP0D360", fontCh10, 0, LEFT);
            addColumn(table, 8, 1, "頁次：" + StringUtility.chtLeftPad(String.valueOf(writer.getPageNumber()), 3, "0"), fontCh10, 0, LEFT);

            addColumn(table, 8, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 26, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
            // addColumn(table, 26, 1, "勞工保險局 給付處", fontCh18, 0, CENTER);
            addColumn(table, 8, 1, "日期：" + DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), false), fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 14, 1, "核定清單", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);

            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);

            addColumn(table, 26, 1, " ", fontCh12, 0, CENTER);
            addBarcode39(table, DateUtility.getNowChineseDate(), 50, 50, 8, 0, 0, CENTER, MIDDLE);
            addColumn(table, 3, 1, " ", fontCh12, 0, CENTER);
            addBarcode39(table, StringUtility.chtLeftPad(String.valueOf(writer.getPageNumber()), 3, "0"), 50, 50, 5, 0, 0, CENTER, MIDDLE);

            addColumn(table, 42, 1, "給付別：" + payTyp + "(" + caseTypStr + ")", fontCh12, 0, LEFT);

            addColumn(table, 10, 1, "核定年月：" + DateUtility.changeWestYearMonthType(issuYm), fontCh12, 0, LEFT);
            addColumn(table, 10, 1, "", fontCh12, 0, LEFT);
            addColumn(table, 10, 1, "", fontCh10, 0, LEFT);
            addColumn(table, 12, 1, "(印表日期：" + DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), false) + "第" + StringUtility.chtLeftPad(String.valueOf(writer.getPageNumber()), 3, "0") + "頁)", fontCh10, 0, LEFT);

        }

        addColumn(table, 3, 1, "序號", fontCh10, 1, CENTER);
        addColumn(table, 9, 1, "受理編號\n給付年月起迄", fontCh10, 1, CENTER);
        addColumn(table, 10, 1, "金融機構轉帳帳號", fontCh10, 1, CENTER);
        addColumn(table, 5, 1, "受款人姓名", fontCh10, 1, CENTER);
        addColumn(table, 5, 1, "核定金額\n實付金額", fontCh10, 1, CENTER);
        addColumn(table, 3, 1, "版次", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, "初核人員", fontCh10, 1, CENTER);
        addColumn(table, 3, 1, "備註", fontCh10, 1, CENTER);

        return table;
    }

    public void addFooter(Table table, String payCode) throws Exception {

        addColumn(table, 42, 1, "借：", fontCh10, 0, LEFT);
        addColumn(table, 42, 1, "　　貸：", fontCh10, 0, LEFT);
        addColumn(table, 42, 1, "", fontCh8, 0, LEFT);
        addColumn(table, 42, 1, "", fontCh8, 0, LEFT);
        addColumn(table, 42, 1, "", fontCh8, 0, LEFT);

        addColumn(table, 15, 1, RptTitleUtility.getDivisionTitle(payCode, DateUtility.getNowWestDate()) + "：", fontCh10, 0, LEFT);
        // addColumn(table, 15, 1, "OO給付科", fontCh10, 0, LEFT);
        addColumn(table, 27, 1, RptTitleUtility.getSafetyPaymentTitle3(DateUtility.getNowWestDate()) + "：", fontCh10, 0, LEFT);
        // addColumn(table, 27, 1, "出納：", fontCh10, 0, LEFT);
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt06Case> caseData, HashMap<String, Object> map) throws Exception {
        try {
            RptService rptService = (RptService) SpringHelper.getBeanById("rptService");

            document.open();

            Table table = createTable(42);

            // 序號
            Integer seqNo = 1;

            String caseTyp = caseData.get(0).getCaseTyp();
            String issuYm = caseData.get(0).getIssuYm();
            String payTyp = caseData.get(0).getPayCodeStr();
            String payCode = caseData.get(0).getApNo().substring(0, 1);

            for (int i = 0; i < caseData.size(); i++) { // 受理案件資料
                MonthlyRpt06Case monthlyRpt06Case = caseData.get(i);

                if (i == 0) {
                    // 取得表頭
                    table = getHeader(true, payTyp, monthlyRpt06Case.getCaseTypStr(), issuYm, payCode);
                }
                else {
                    if (!(monthlyRpt06Case.getCaseTyp()).equals(caseTyp)) {
                        // 加入表尾
                        addFooter(table, payCode);

                        document.add(table);
                        // 取得表頭
                        table = getHeader(true, payTyp, monthlyRpt06Case.getCaseTypStr(), issuYm, payCode);

                        // 序號歸零
                        seqNo = 1;

                    }
                }
                // 案件類別
                caseTyp = monthlyRpt06Case.getCaseTyp();

                // 序號
                addColumn(table, 3, 1, Integer.toString(seqNo), fontCh10, 1, CENTER);
                // 受理編號 給付年月
                addColumn(table, 9, 1, monthlyRpt06Case.getApNoStr() + "\n" + monthlyRpt06Case.getPayYmStr(), fontCh10, 1, CENTER);
                // 金融機構
                addColumn(table, 10, 1, monthlyRpt06Case.getPayBankIdStr(), fontCh10, 1, CENTER);
                // 受款人姓名
                addColumn(table, 5, 1, monthlyRpt06Case.getBenName(), fontCh10, 1, CENTER);
                // 核定金額 實付金額
                addColumn(table, 5, 1, formatBigDecimalToInteger(monthlyRpt06Case.getIssueAmt()) + "\n" + formatBigDecimalToInteger(monthlyRpt06Case.getAplpayAmt()), fontCh10, 1, RIGHT);
                // 版次
                addColumn(table, 3, 1, monthlyRpt06Case.getVeriSeq(), fontCh10, 1, CENTER);
                // 初核人員
                addColumn(table, 4, 1, monthlyRpt06Case.getChkMan(), fontCh10, 1, CENTER);
                // 備註
                addColumn(table, 3, 1, "", fontCh10, 1, CENTER);

                seqNo++;

                addColumn(table, 42, 1, "　", fontCh10, 1, CENTER);
                addColumn(table, 42, 1, "　", fontCh10, 1, CENTER);
                addColumn(table, 42, 1, "　", fontCh10, 1, CENTER);
                addColumn(table, 42, 1, "　", fontCh10, 1, CENTER);
                addColumn(table, 42, 1, "　", fontCh10, 1, CENTER);
                addColumn(table, 42, 1, "　", fontCh10, 1, CENTER);

                if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數
                {
                    // 刪除造成換頁的該筆資料 (一筆有一行)
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();

                    // 加入表尾
                    addFooter(table, payCode);
                    document.add(table);
                    // 取得表頭
                    table = getHeader(true, payTyp, monthlyRpt06Case.getCaseTypStr(), issuYm, payCode);
                }
                else {
                    // 刪除造成換頁的該筆資料 (一筆有一行)
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                }
            } // for(int i=0;i<caseData.size();i++) end

            // 加入表尾
            addFooter(table, payCode);
            if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數
            {
                // 刪除造成換頁的該筆資料 (一筆有一行)
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();

                // 加入表尾
                addFooter(table, payCode);
                document.add(table);
            }
            else {
                document.add(table);
            }

            document.close();
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
