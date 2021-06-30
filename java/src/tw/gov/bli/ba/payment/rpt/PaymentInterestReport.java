package tw.gov.bli.ba.payment.rpt;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

import tw.gov.bli.ba.payment.cases.PaymentStageDtlCase;
import tw.gov.bli.ba.payment.forms.PaymentProcessQueryForm;
import tw.gov.bli.ba.rpt.report.ReportBase;

public class PaymentInterestReport extends ReportBase {

    public PaymentInterestReport() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        return document;
    }

    public Table getHeader(PaymentProcessQueryForm queryForm) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(42);

        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);
        addColumn(table, 42, 1, "利息試算結果 ", fontCh14, 0, LEFT);

        addColumn(table, 12, 1, "身分證號：" + queryForm.getIdn(), fontCh10, 0, LEFT);
        addColumn(table, 12, 1, "利息試算期數：" + String.valueOf(queryForm.getInterestTryStage()), fontCh10, 0, LEFT);
        addColumn(table, 18, 1, "", fontCh10, 0, LEFT);

        addColumn(table, 12, 1, "繳款單號：" + queryForm.getPaymentNo(), fontCh10, 0, LEFT);
        if (StringUtils.isNotBlank(queryForm.getClassNo())) {
            addColumn(table, 12, 1, "移送案號：" + queryForm.getClassNo(), fontCh10, 0, LEFT);
        }
        else {
            addColumn(table, 12, 1, "", fontCh10, 0, LEFT);
        }
        addColumn(table, 18, 1, "", fontCh10, 0, LEFT);

        if (StringUtils.equals(queryForm.getChkObj(), "2")) {
            addColumn(table, 12, 1, "繳款期限：" + queryForm.getPaymentDateLine(), fontCh10, 0, LEFT);
        }
        else {
            addColumn(table, 12, 1, "繳款期限：文到30日內", fontCh10, 0, LEFT);
        }
        addColumn(table, 12, 1, "試算本利和：" + formatBigDecimalToInteger(queryForm.getTryInterestAmt()), fontCh10, 0, LEFT);
        addColumn(table, 12, 1, "利息標的金額：" + formatBigDecimalToInteger(queryForm.getTotTrgAmt()), fontCh10, 0, LEFT);
        addColumn(table, 6, 1, "總利息：" + formatBigDecimalToInteger(queryForm.getTotInterst()), fontCh10, 0, LEFT);

        // =========================================================================================
        addColumn(table, 2, 1, "期別", fontCh10, 1, CENTER);
        addColumn(table, 6, 1, "期初本金餘額", fontCh10, 1, CENTER);
        addColumn(table, 6, 1, "期末還款金額", fontCh10, 1, CENTER);
        addColumn(table, 6, 1, "期末本金餘額", fontCh10, 1, CENTER);
        addColumn(table, 3, 1, "利率", fontCh10, 1, CENTER);
        addColumn(table, 5, 1, "利息起算日", fontCh10, 1, CENTER);
        addColumn(table, 5, 1, "利息結算日", fontCh10, 1, CENTER);
        addColumn(table, 3, 1, "天數", fontCh10, 1, CENTER);
        addColumn(table, 3, 1, "試算\n利息", fontCh10, 1, CENTER);
        addColumn(table, 3, 1, "調整\n利息", fontCh10, 1, CENTER);

        return table;
    }

    public ByteArrayOutputStream doReport(PaymentProcessQueryForm queryForm, List<PaymentStageDtlCase> caseListDb) throws Exception {
        try {
            document.open();

            Table table = getHeader(queryForm);

            for (int i = 0; i < caseListDb.size(); i++) {
                PaymentStageDtlCase stageData = caseListDb.get(i);
                // if (i == 0) {
                // // 取得表頭
                // table = getHeader(queryForm);
                // }

                if (stageData.getRePayAmt().intValue() == 0 && stageData.getAccAmt().intValue() == 0 && stageData.getiRate().compareTo(BigDecimal.ZERO) > 0) {
                    // 期別
                    addColumn(table, 2, 1, "", fontCh10, 1, CENTER);
                    // 期初本金餘額
                    addColumn(table, 6, 1, "", fontCh10, 1, CENTER);
                    // 期末還款金額
                    addColumn(table, 6, 1, "", fontCh10, 1, CENTER);
                    // 期末本金餘額
                    addColumn(table, 6, 1, "", fontCh10, 1, CENTER);

                }
                else {
                    // 期別
                    addColumn(table, 2, 1, String.valueOf(stageData.getNowStage()), fontCh10, 1, CENTER);
                    // 期初本金餘額
                    addColumn(table, 6, 1, formatBigDecimalToInteger(stageData.getAccAmt()), fontCh10, 1, CENTER);
                    // 期末還款金額
                    addColumn(table, 6, 1, formatBigDecimalToInteger(stageData.getRePayAmt()), fontCh10, 1, CENTER);
                    // 期末本金餘額
                    addColumn(table, 6, 1, formatBigDecimalToInteger(stageData.getAccAmtEnd()), fontCh10, 1, CENTER);

                }

                // 利率
                addColumn(table, 3, 1, String.valueOf(stageData.getiRate()), fontCh10, 1, CENTER);
                // 利息起算日
                addColumn(table, 5, 1, stageData.getInterestBegDate(), fontCh10, 1, CENTER);
                // 利息結算日
                addColumn(table, 5, 1, stageData.getInterestEndDate(), fontCh10, 1, CENTER);
                // 天數
                addColumn(table, 3, 1, String.valueOf(stageData.getDateBetween()), fontCh10, 1, CENTER);
                // 試算利息
                addColumn(table, 3, 1, formatBigDecimalToInteger(stageData.getTryInterest()), fontCh10, 1, CENTER);
                // 調整利息
                addColumn(table, 3, 1, formatBigDecimalToInteger(stageData.getAdjInterest()), fontCh10, 1, CENTER);

            }
            if (!writer.fitsPage(table)) // 超過一頁所能顯示的行數
            {
                // 刪除造成換頁的該筆資料 (一筆有一行)
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();
                table.deleteLastRow();

                document.add(table);
                // 取得表頭
                table = getHeader(queryForm);
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
}
