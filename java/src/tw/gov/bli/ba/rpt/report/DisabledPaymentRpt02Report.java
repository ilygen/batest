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
import tw.gov.bli.ba.query.cases.PaymentQueryArclistDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryArclistDetailDataCase;
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
import tw.gov.bli.ba.util.DateUtility;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import tw.gov.bli.ba.util.ValidateUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.DateUtil;

/**
 * 勞保老年年金給付查詢 歸檔記錄
 * 
 * @author KIYOMI
 */
public class DisabledPaymentRpt02Report extends ReportBase {

    private int nPage; // 每筆資料的頁數

    private String printDate = ""; // 印表日期

    private String line = "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

    public DisabledPaymentRpt02Report() throws Exception {
        super();
        nPage = 0;
        printDate = DateUtility.formatNowChineseDateString(false);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4.rotate(), 10, 4, 20, 20);
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
    public Table addHeader(UserBean userData, PaymentQueryDetailDataCase caseData, boolean attached) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        createPageText(735, 540, writer.getCurrentPageNumber(), "頁次 ：", " (共  ", 8, 150, 50);
        // 460, 795, writer.getCurrentPageNumber(), "第 ", " 頁，共 ", 11, 150, 50,bfKChinese
        addColumn(table, 60, 1, "給付查詢 - 歸檔記錄", fontCh18, 0, CENTER);

        addColumn(table, 10, 1, "受理編號：" + caseData.getApNoStrDisplay(), fontCh8, 0, LEFT);
        addColumn(table, 20, 1, "事故者姓名：" + caseData.getEvtName(), fontCh8, 0, LEFT);
        addColumn(table, 15, 1, "印表人員：" + userData.getEmpNo(), fontCh8, 0, RIGHT);
        addColumn(table, 15, 1, "   印表日期：" + printDate, fontCh8, 0, LEFT);
        // addColumn(table, 6, 1, "頁次：" + StringUtils.leftPad(String.valueOf(writer.getCurrentPageNumber() - nPage), 3, "0"), fontCh8, 0, RIGHT);
        // addColumn(table, 6, 1, "( 共 " + StringUtils.leftPad(String.valueOf(writer.getCurrentPageNumber() - nPage)+" 頁 )", 3, "0"), fontCh8, 0, RIGHT);
        addLine(table);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, PaymentQueryDetailDataCase caseData, PaymentQueryForm qryForm, PaymentQueryArclistDataCase arclistDetailData) throws Exception {
        try {
            document.open();

            // for (int index = 0; index < 5; index++) {
            // // 頁次處理
            // if (index > 0) {
            // nPage = writer.getPageNumber();
            // }

            // 表頭
            Table table = addHeader(userData, caseData, false);

            addColumn(table, 20, 1, "受理編號：" + caseData.getApNoStrDisplay(), fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "事故日期/申請日期：" + DateUtil.changeDateType(caseData.getEvtJobDate()) + "/" + DateUtil.changeDateType(caseData.getAppDate()), fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "給付別：" + caseData.getPagePayKindStr(), fontCh12, 0, LEFT);

            addColumn(table, 60, 1, "事故者姓名：" + caseData.getEvtName(), fontCh12, 0, LEFT);

            addColumn(table, 20, 1, "事故者身分證號：" + caseData.getEvtIdnNo(), fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "事故者出生日期：" + caseData.getEvtBrDateStr(), fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "事故者年齡/性別：" + caseData.getEvtAge() + "歲/" + caseData.getEvtSexStr(), fontCh12, 0, LEFT);

            addColumn(table, 20, 1, "電腦/人工審核結果：" + caseData.getAcceptMkStr() + "/" + caseData.getManchkMkStr(), fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "處理狀態：" + caseData.getProcStatStr(), fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "給付種類：" + caseData.getPayKindStr(), fontCh12, 0, LEFT);

            addLine(table); // 【歸檔記錄資料】

            addColumn(table, 30, 1, "【歸檔記錄資料】", fontCh12, 0, LEFT);
            if (StringUtils.isBlank(caseData.getEmpExt())) {
                caseData.setEmpExt("");
            }
            addColumn(table, 22, 1, " ", fontCh9, 0, RIGHT);
            addColumn(table, 8, 1, "承辦人分機號碼：" + caseData.getEmpExt(), fontCh9, 0, LEFT);

            addColumn(table, 60, 1, "核定年月起迄：" + arclistDetailData.getMinIssuYmChinese() + "~" + arclistDetailData.getMaxIssuYmChinese(), fontCh12, 0, LEFT);

            addColumn(table, 4, 1, "序號", fontCh12, 0, CENTER);
            addColumn(table, 6, 1, "核定年月", fontCh12, 0, CENTER);
            addColumn(table, 10, 1, "案件類別", fontCh12, 0, CENTER);
            addColumn(table, 6, 1, "審核人員", fontCh12, 0, CENTER);
            addColumn(table, 6, 1, "決行人員", fontCh12, 0, CENTER);
            addColumn(table, 6, 1, "決行日期", fontCh12, 0, CENTER);
            addColumn(table, 6, 1, "歸檔日期", fontCh12, 0, CENTER);
            addColumn(table, 6, 1, "歸檔頁次", fontCh12, 0, CENTER);
            addColumn(table, 10, 1, "異動註記", fontCh12, 0, CENTER);

            if (arclistDetailData.getDetailList().size() > 0) {

                    // 受款人資料
                    for (int x = 0; x < arclistDetailData.getDetailList().size(); x++) {

                        // ---在塞分隔線前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 3);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
                            deleteRow(table, 3);
                            document.add(table);
                            table = addHeader(userData, caseData, false);
                        }
                        else {
                            deleteRow(table, 3);
                        }
                        // ---

                        PaymentQueryArclistDetailDataCase arclistData = arclistDetailData.getDetailList().get(x);
                        String intValue = Integer.toString(x+1);
                        addColumn(table, 4, 1, intValue, fontCh12, 0, CENTER); // 序號
                        addColumn(table, 6, 1, arclistData.getIssuYmChinese(), fontCh12, 0, CENTER); // 核定年月
                        addColumn(table, 10, 1, arclistData.getCaseTypStr(), fontCh12, 0, CENTER); // 案件類別
                        addColumn(table, 6, 1, arclistData.getChkMan(), fontCh12, 0, CENTER); // 審核人員
                        addColumn(table, 6, 1, arclistData.getExeMan(), fontCh12, 0, CENTER); // 決行人員
                        addColumn(table, 6, 1, arclistData.getExeDateChinese(), fontCh12, 0, CENTER); // 決行日期
                        addColumn(table, 6, 1, arclistData.getArcDateChinese(), fontCh12, 0, CENTER); // 歸檔日期
                        addColumn(table, 6, 1, arclistData.getArcPg().toString(), fontCh12, 0, RIGHT); // 歸檔頁次
                        addColumn(table, 10, 1, arclistData.getDelMl(), fontCh12, 0, CENTER); // 異動註記
                    }

            }
            else {
                addColumn(table, 60, 1, "無資料", fontCh12, 0, CENTER);
            }

            // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
            addEmptyRow(table, 5);

            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // 換了頁就不印分隔線了
                deleteRow(table, 5);
                document.add(table);
                table = addHeader(userData, caseData, false);
            }
            else {
                deleteRow(table, 5);
            }

            // ]

            document.add(table);

        }
        // document.close();
        // }

        finally {
            closePageText(String.valueOf(writer.getPageNumber()) + " 頁)", 8);
            document.close();
        }

        return bao;
    }
}
