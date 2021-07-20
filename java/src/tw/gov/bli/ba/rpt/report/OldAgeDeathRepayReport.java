package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.query.cases.PaymentQueryBenDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryChkFileDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryIssuPayDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryIssuPayExtDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryLetterTypeMkCase;
import tw.gov.bli.ba.query.forms.PaymentQueryForm;
import tw.gov.bli.ba.rpt.cases.CiptUtilityCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01AnnuityPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01BenDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01BenPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01Case;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01ChangeDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01ChkfileDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01ChkfileDescCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DecideDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DeductAnnuityDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DeductOnceDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DieOncePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DiePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DisablePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01InjuryPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01IssueAmtDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01JoblessPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01LoanDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01MonthAvgAmtDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01NotifyDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01NpPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01OncePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01PayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01UnitCase;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.forms.OldAgeDeathRepayForm;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import tw.gov.bli.ba.util.ValidateUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.DateUtil;

/**
 * 勞保老年年金給付查詢 案件資料
 * 
 * @author frank
 */
public class OldAgeDeathRepayReport extends ReportBase {

    private int nPage; // 每筆資料的頁數

    private String printDate = ""; // 印表日期

    private String line = "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

    public OldAgeDeathRepayReport() throws Exception {
        super();
        nPage = 0;
        printDate = DateUtility.formatNowChineseDateString(false);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 20, 4, 20, 20);
        return document;
    }

    /**
     * 加入分隔線
     * 
     * @param table
     * @param icolspan Column Span
     * @throws Exception
     */
    public void addLine(Table table) throws Exception {
        addColumn(table, 60, 1, line, fontCh8, 0, LEFT);
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
            addColumn(table, 60, 1, "　", fontCh12, 0, LEFT); // 注意: 內容是全形空白
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
     * @param payListSize 給付查詢 案件資料
     * @param attached 是否為附表
     * @return
     * @throws Exception
     */
    public Table addHeader(UserBean userData, OldAgeDeathRepayForm queryForm, BaappbaseDataUpdateCase checkOldAgeDeathRepayDataCase, BaappbaseDataUpdateCase oldAgeDeathRepayData, boolean attached) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        createPageStringText(65, 90, "此 致", 14, 150, 50);
        createPageStringText(30, 70, RptTitleUtility.getSafetyPaymentTitle1(DateUtility.getNowWestDate()), 14, 150, 50);
        if (queryForm.getApNo1().equals("L")) {
            createPageStringText(180, 35, RptTitleUtility.getOldAgePaymentTitle(DateUtility.getNowWestDate()) + "    承辦人：            複核：", 14, 150, 50);
        }
        if (queryForm.getApNo1().equals("K")) {
            createPageStringText(180, 35, RptTitleUtility.getDisabledPaymentTitle(DateUtility.getNowWestDate()) + "    承辦人：            複核：", 14, 150, 50);
        }
        if (queryForm.getApNo1().equals("S")) {
            createPageStringText(180, 35, RptTitleUtility.getSurvivorPaymentTitle(DateUtility.getNowWestDate()) + "    承辦人：            複核：", 14, 150, 50);
        }
        // createPageStringText(30, 70,"給付出納科",14, 150, 50);
        // if(queryForm.getApNo1().equals("L")){
        // createPageStringText(180, 35,"生老給付科 承辦人： 複核：",14, 150, 50);
        // }
        // if(queryForm.getApNo1().equals("K")){
        // createPageStringText(180, 35,"傷殘給付科 承辦人： 複核：",14, 150, 50);
        // }
        // if(queryForm.getApNo1().equals("S")){
        // createPageStringText(180, 35,"死亡給付科 承辦人： 複核：",14, 150, 50);
        // }

        addColumn(table, 60, 1, "受款人死亡改匯通知單", fontCh16b, 0, CENTER);

        addColumn(table, 40, 1, " ", fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "印表日期：" + printDate, fontCh12, 0, LEFT);

        addColumn(table, 40, 1, "退匯日期：" + DateUtility.formatChineseDateTimeString(oldAgeDeathRepayData.getBrChkDate(), true), fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "退匯原因：" + oldAgeDeathRepayData.getParamName(), fontCh12, 0, LEFT);

        addColumn(table, 60, 1, "給付種類：" + oldAgeDeathRepayData.getIssuKind() + "-老年年金給付", fontCh12, 0, LEFT);

        addColumn(table, 60, 1, "\n", fontCh12, 0, LEFT);

        addColumn(table, 20, 1, "受理編號：" + queryForm.getApNo1() + "-" + queryForm.getApNo2() + "-" + queryForm.getApNo3() + "-" + queryForm.getApNo4(), fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "核定年月：" + DateUtility.formatChineseYearMonthString(oldAgeDeathRepayData.getOriIssuYm(), true), fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "給付年月：" + DateUtility.formatChineseYearMonthString(oldAgeDeathRepayData.getPayYm(), true), fontCh12, 0, LEFT);

        addColumn(table, 20, 1, "原受款人姓名：" + checkOldAgeDeathRepayDataCase.getBenName(), fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "原受款序：" + checkOldAgeDeathRepayDataCase.getSeqNo(), fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "退匯金額：" + formatBigDecimalToInteger(oldAgeDeathRepayData.getRemitAmt()), fontCh12, 0, LEFT);

        addColumn(table, 60, 1, "\n", fontCh12, 0, LEFT);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, OldAgeDeathRepayForm queryForm, BaappbaseDataUpdateCase checkOldAgeDeathRepayDataCase, List<BaappbaseDataUpdateCase> oldAgeDeathRepayDataCaseList, List<BaappbaseDataUpdateCase> heirSeqNoExistDataCaseList, List<BaappbaseDataUpdateCase> repayIssueAmtDataCaseList) throws Exception {
        try {
            document.open();

            // for(BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList){
            // 紀錄list index 跑每月分配人數使用
            int RepayIssueAmtDataCount = 0;

            for (int i = 0; i < oldAgeDeathRepayDataCaseList.size(); i++) {
                // 表頭
                Table table = addHeader(userData, queryForm, checkOldAgeDeathRepayDataCase, oldAgeDeathRepayDataCaseList.get(i), false);

                if (repayIssueAmtDataCaseList.size() > 0) {
                    // 取得繼承人第一順位
                    String heirBenName = repayIssueAmtDataCaseList.get(0).getBenName();
                    
                    // 取得分配人數
                    // int heirSize = repayIssueAmtDataCaseList.size();
                    
                    // 每月分配金額人數(產生報表時需使用)
                    int indexCount = 0;
                    if (repayIssueAmtDataCaseList.get(0).getIndexCount() == null) {
                        indexCount = 0;
                    }
                    else {
                        indexCount = Integer.parseInt(repayIssueAmtDataCaseList.get(0).getIndexCount());
                    }

                    addColumn(table, 60, 1, "原受款人已死亡，請改匯" + heirBenName + "等" + indexCount + "人，分配狀況如下，敬請辦理改匯作業：", fontCh12, 0, LEFT);

                    if (indexCount > 0) {
                        for (int n = 0; n < indexCount; n++) {
                            addColumn(table, 20, 1, (n + 1) + ". " + "受款人序：" + repayIssueAmtDataCaseList.get(RepayIssueAmtDataCount).getSeqNo(), fontCh12, 0, LEFT);
                            addColumn(table, 20, 1, "姓名：" + repayIssueAmtDataCaseList.get(RepayIssueAmtDataCount).getBenName(), fontCh12, 0, LEFT);
                            addColumn(table, 20, 1, "改匯金額：" + formatBigDecimalToInteger(repayIssueAmtDataCaseList.get(RepayIssueAmtDataCount).getIssueAmt()), fontCh12, 0, LEFT);

                            addColumn(table, 20, 1, "   給付方式：" + repayIssueAmtDataCaseList.get(RepayIssueAmtDataCount).getPayTypStr(), fontCh12, 0, LEFT);
                            // addColumn(table, 20, 1, "帳號："+repayIssueAmtDataCaseList.get(RepayIssueAmtDataCount).getPayBankId()+"-"+repayIssueAmtDataCaseList.get(RepayIssueAmtDataCount).getBranchId()+"-"+repayIssueAmtDataCaseList.get(RepayIssueAmtDataCount).getPayEeacc() , fontCh12, 0, LEFT);
                            addColumn(table, 20, 1, "帳號：" + repayIssueAmtDataCaseList.get(RepayIssueAmtDataCount).getPayBankIdStr(), fontCh12, 0, LEFT);
                            addColumn(table, 20, 1, "", fontCh12, 0, LEFT);

                            RepayIssueAmtDataCount++;

                            addEmptyRow(table, 9);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                // 換了頁就不印分隔線了
                                deleteRow(table, 9);
                                document.add(table);
                                table = addHeader(userData, queryForm, checkOldAgeDeathRepayDataCase, oldAgeDeathRepayDataCaseList.get(i), false);
                            }
                            else {
                                deleteRow(table, 9);
                            }
                        }
                    }
                }

                // 與最後一筆受款人的改匯資料需間隔3行空白。
                addColumn(table, 60, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 60, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 60, 1, " ", fontCh12, 0, LEFT);

                document.add(table);
            }
            // 計數器規零
            RepayIssueAmtDataCount = 0;
        }

        finally {
            document.close();
        }

        return bao;
    }
}
