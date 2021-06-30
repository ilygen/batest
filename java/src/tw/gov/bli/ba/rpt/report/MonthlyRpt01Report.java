package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt01Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.helper.SpringHelper;

public class MonthlyRpt01Report extends ReportBase {

    private RptService rptService;

    public MonthlyRpt01Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        return document;
    }

    public Table getHeader(boolean bPrintMainHeader, String issuYm, String payCode, String rptPayCode) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(42);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 34, 1, "報表編號：BALP0D310", fontCh10, 0, LEFT);
            addColumn(table, 8, 1, "頁次：" + StringUtility.chtLeftPad(String.valueOf(writer.getPageNumber()), 3, "0"), fontCh10, 0, LEFT);

            addColumn(table, 8, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 26, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(rptPayCode, DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
            // addColumn(table, 26, 1, "勞工保險局 給付處", fontCh18, 0, CENTER);
            addColumn(table, 8, 1, "日期：" + DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), false), fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 14, 1, "月處理核定合格清冊", fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);

            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);

            addColumn(table, 42, 1, "核定年月：" + issuYm, fontCh12, 0, LEFT);

            addColumn(table, 10, 1, "給付別：" + payCode, fontCh12, 0, LEFT);
            addColumn(table, 10, 1, "", fontCh10, 0, LEFT);
            addColumn(table, 10, 1, "", fontCh10, 0, LEFT);
            addColumn(table, 12, 1, "", fontCh10, 0, LEFT);

        }

        addColumn(table, 2, 1, "序號", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, "申請日期", fontCh10, 1, CENTER);
        addColumn(table, 7, 1, "受理編號", fontCh10, 1, CENTER);
        addColumn(table, 10, 1, "給付年月起迄", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, "姓名", fontCh10, 1, CENTER);
        addColumn(table, 10, 1, "轉帳帳號", fontCh10, 1, CENTER);
        addColumn(table, 5, 1, "核定金額\n實付金額", fontCh10, 1, CENTER);

        return table;
    }

    public void addFooter(Table table, Integer pageRowCount, Integer listCount) throws Exception {
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);

        addColumn(table, 4, 1, " ", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, "件數", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 4, 1, "決行", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, "審核", fontCh10, 1, CENTER);
        addColumn(table, 22, 1, " ", fontCh10, 0, CENTER);

        addColumn(table, 4, 1, "本頁小計", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, pageRowCount.toString(), fontCh10, 1, CENTER);
        addColumn(table, 4, 0, " ", fontCh10, 0, CENTER);
        addColumn(table, 4, 2, " ", fontCh10, 1, CENTER);
        addColumn(table, 4, 2, " ", fontCh10, 1, CENTER);
        addColumn(table, 22, 0, " ", fontCh10, 0, CENTER);

        addColumn(table, 4, 1, "總計", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, listCount.toString(), fontCh10, 1, CENTER);
        addColumn(table, 4, 0, " ", fontCh10, 0, CENTER);
        // addColumn(table, 4, 2, " ", fontCh10, 1, CENTER);
        // addColumn(table, 4, 2, " ", fontCh10, 1, CENTER);
        addColumn(table, 22, 0, " ", fontCh10, 0, CENTER);

        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "註：1.比對參數維護之分案原則，按分案原則之分案號碼歸類，分送承辦人以利抽檢核對。", fontCh8, 0, LEFT);
        addColumn(table, 42, 1, "　　2.本欄位依清單種類分類，計有：新案、續發案、停發後續發案、補發案、停發後補發案。", fontCh8, 0, LEFT);
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt01Case> caseData, HashMap<String, Object> map) throws Exception {
        try {
            RptService rptService = (RptService) SpringHelper.getBeanById("rptService");

            document.open();

            Table table = null;

            // 序號
            Integer seqNo = 1;
            // 一頁筆數
            Integer pageRowCount = 0;
            // 總數
            Integer listCount = caseData.size();
            // 表頭資料
            String issuYm = caseData.get(0).getIssuYmStr();
            String payCode = caseData.get(0).getPayCodeStr();
            String rptPayCode = caseData.get(0).getApNo().substring(0, 1);
            // 取得表頭
            table = getHeader(true, issuYm, payCode, rptPayCode);
            for (int i = 0; i < caseData.size(); i++) { // 受理案件資料
                MonthlyRpt01Case monthlyRpt01Case = caseData.get(i);

                // if(i==0){
                // // 取得表頭
                // table = getHeader(true, issuYm,payCode,rptPayCode);
                // }

                // 序號
                addColumn(table, 2, 1, Integer.toString(seqNo), fontCh10, 1, CENTER);
                // 申請日期
                addColumn(table, 4, 1, monthlyRpt01Case.getAppDateStr(), fontCh10, 1, CENTER);
                // 受理編號
                addColumn(table, 7, 1, monthlyRpt01Case.getApNoStr(), fontCh10, 1, CENTER);
                // 給付年月起迄
                addColumn(table, 10, 1, monthlyRpt01Case.getPayYm(), fontCh10, 1, CENTER);
                // 姓名
                addColumn(table, 4, 1, monthlyRpt01Case.getEvtName(), fontCh10, 1, CENTER);
                // 轉帳帳號
                addColumn(table, 10, 1, monthlyRpt01Case.getPayBankIdStr(), fontCh10, 1, CENTER);
                // 核定金額實付金額
                addColumn(table, 5, 1, monthlyRpt01Case.getAplpayAmt().toString(), fontCh10, 1, RIGHT);

                seqNo++;
                pageRowCount++;

                addColumn(table, 42, 1, "　", fontCh10, 1, CENTER);
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
                    table.deleteLastRow();

                    // 加入表尾
                    addFooter(table, pageRowCount, listCount);
                    document.add(table);

                    pageRowCount = 0;

                    // 取得表頭
                    table = getHeader(true, issuYm, payCode, rptPayCode);
                }
                else {
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                }
            } // for(int i=0;i<caseData.size();i++) end

            // 加入表尾
            addFooter(table, pageRowCount, listCount);
            if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數
            {
                // 刪除造成換頁的該筆資料 (一筆有一行)
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();

                // 加入表尾
                addFooter(table, pageRowCount, listCount);
                document.add(table);

                pageRowCount = 0;
                // 取得表頭
                table = getHeader(true, issuYm, payCode, rptPayCode);
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
