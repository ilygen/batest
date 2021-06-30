package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt02Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;

public class MonthlyRpt02Report extends ReportBase {

    private RptService rptService;

    public MonthlyRpt02Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        return document;
    }

    public Table getHeader(boolean bPrintMainHeader, String payCode, String issuYm, String chkMan, String reportTitle, String reportNo, String rptPayCode) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(42);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);

        // 列印大標題
        if (bPrintMainHeader) {
            addColumn(table, 34, 1, "報表編號：" + reportNo, fontCh10, 0, LEFT);
            addColumn(table, 8, 1, "頁次：" + StringUtility.chtLeftPad(String.valueOf(writer.getPageNumber()), 3, "0"), fontCh10, 0, LEFT);

            addColumn(table, 8, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 26, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(rptPayCode, DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
            // addColumn(table, 26, 1, "勞工保險局 給付處", fontCh18, 0, CENTER);
            addColumn(table, 8, 1, "日期：" + DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), false), fontCh10, 0, LEFT);

            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
            addColumn(table, 14, 1, reportTitle, fontCh18, 0, CENTER);
            addColumn(table, 14, 1, "", fontCh10, 0, CENTER);

            addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);

            addColumn(table, 42, 1, "給付別：" + payCode, fontCh12, 0, LEFT);

            addColumn(table, 10, 1, "核定年月：" + issuYm, fontCh12, 0, LEFT);
            addColumn(table, 10, 1, "初核人員：" + chkMan, fontCh12, 0, LEFT);
            addColumn(table, 10, 1, "", fontCh10, 0, LEFT);
            addColumn(table, 12, 1, "", fontCh10, 0, LEFT);

        }

        addColumn(table, 2, 1, "序號", fontCh10, 1, CENTER);
        // addColumn(table, 4, 1, "申請日期", fontCh10, 1, CENTER);
        addColumn(table, 7, 1, "受理編號", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, "給付年月\n起迄", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, "姓名", fontCh10, 1, CENTER);
        addColumn(table, 13, 1, "異動原因", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, "歸檔\n印表日期", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, "歸檔頁次", fontCh10, 1, CENTER);
        addColumn(table, 4, 1, "種類", fontCh10, 1, CENTER);

        return table;
    }

    public void addFooter(Table table) throws Exception {
        addColumn(table, 42, 1, "註：", fontCh8, 0, LEFT);
        addColumn(table, 42, 1, "　1.本清單於月編審處理後，由系統依編審後之狀況比對有影響之給付年月資料予以列印。", fontCh8, 0, LEFT);
        addColumn(table, 42, 1, "　2.本欄位依資料種類顯示：新案、續發案。", fontCh8, 0, LEFT);
        addColumn(table, 42, 1, "　3.異動狀況：含核定金額變動、實付金額變動、事故者資料變動或遺屬資料變動等。", fontCh8, 0, LEFT);
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt02Case> caseData, HashMap<String, Object> map) throws Exception {
        try {

            document.open();

            Table table = null;

            // 序號
            Integer seqNo = 1;
            String payCode = caseData.get(0).getPayCodeStr();
            String rptPayCode = caseData.get(0).getApNo().substring(0, 1);
            String issuYm = caseData.get(0).getIssuYmStr();
            String chkMan = caseData.get(0).getChkMan();
            String mtestMk = (String) map.get("mtestMk");
            String reportTitle = null;
            String reportNo = null;

            if (mtestMk.equals("M")) {
                reportTitle = "月試核異動清單";
                reportNo = "BALP0D320";
            }
            else {
                reportTitle = "月核定異動清單";
                reportNo = "BALP0D321";
            }

            // 取得表頭
            table = getHeader(true, payCode, issuYm, chkMan, reportTitle, reportNo, rptPayCode);
            for (int i = 0; i < caseData.size(); i++) { // 受理案件資料
                MonthlyRpt02Case monthlyRpt02Case = caseData.get(i);

                // if (i == 0) {
                // // 取得表頭
                // table = getHeader(true, payCode, issuYm, chkMan, reportTitle, reportNo, rptPayCode);
                // }

                // 序號
                addColumn(table, 2, 1, Integer.toString(seqNo), fontCh10, 1, CENTER);
                // 受理編號
                addColumn(table, 7, 1, monthlyRpt02Case.getApNoStr(), fontCh10, 1, CENTER);
                // 給付年月
                addColumn(table, 4, 1, monthlyRpt02Case.getPaysYmStr() + "\n" + monthlyRpt02Case.getPayeYmStr(), fontCh10, 1, CENTER);
                // 姓名
                addColumn(table, 4, 1, monthlyRpt02Case.getEvtName(), fontCh10, 1, CENTER);
                // 異動狀況
                addColumn(table, 13, 1, monthlyRpt02Case.getUpCauseCodeStr(), fontCh10, 1, CENTER);
                // 印表日期
                addColumn(table, 4, 1, monthlyRpt02Case.getCprnDateStr(), fontCh10, 1, RIGHT);
                // 清單頁次
                addColumn(table, 4, 1, monthlyRpt02Case.getCprnPage(), fontCh10, 1, RIGHT);
                // 種類
                addColumn(table, 4, 1, monthlyRpt02Case.getCaseTypStr(), fontCh10, 1, CENTER);

                seqNo++;

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

                    // 加入表尾
                    addFooter(table);
                    document.add(table);
                    // 取得表頭
                    table = getHeader(true, payCode, issuYm, chkMan, reportTitle, reportNo, rptPayCode);
                }
                else {
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                    table.deleteLastRow();
                }

            } // for(int i=0;i<caseData.size();i++) end

            // 加入表尾
            addFooter(table);
            if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數
            {
                // 刪除造成換頁的該筆資料 (一筆有一行)
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();

                // 加入表尾
                addFooter(table);
                document.add(table);
                // 取得表頭
                table = getHeader(true, payCode, issuYm, chkMan, reportTitle, reportNo, rptPayCode);
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
