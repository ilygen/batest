package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.decision.cases.PaymentDecisionCase;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baexalist;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt04Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

public class OldAgeReviewRpt04Report extends ReportBase {

    private RptService rptService;

    public OldAgeReviewRpt04Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        return document;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<OldAgeReviewRpt04Case> dataList) throws Exception {
        try {
            document.open();
            if (dataList != null) {
                for (OldAgeReviewRpt04Case caseData : dataList) {
                    if (caseData != null) {
                        doReport(caseData);
                    }
                }
            }
            document.close();
        }
        finally {
            document.close();
        }

        return bao;
    }

    public void doReport(OldAgeReviewRpt04Case caseData) throws Exception {

        document.newPage();
        // 建立表格
        Table table = createTable(42);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);

        addColumn(table, 34, 1, "報表編號：BALP0D040", fontCh12, 0, LEFT);
        addColumn(table, 8, 1, "", fontCh12, 0, LEFT);

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 42, 1, RptTitleUtility.getGroupsTitle(caseData.getGiveApNo().substring(0, 1), DateUtility.getNowWestDate()) + RptTitleUtility.getDivisionTitle(caseData.getGiveApNo().substring(0, 1), DateUtility.getNowWestDate()) + "　另案扣減照會單", fontCh18, 0, CENTER);
        //addColumn(table, 42, 1, "給付處" + caseData.getGiveDeptName() + "　另案扣減照會單", fontCh18, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 14, 1, "", fontCh12, 0, CENTER);
        addColumn(table, 18, 1, "", fontCh18, 0, CENTER);
        addColumn(table, 10, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), false), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 16, 1, "待收回給付種類：" + caseData.getPayItem(), fontCh12, 0, LEFT);
        addColumn(table, 16, 1, "受理編號：" + caseData.getApNoStr(), fontCh12, 0, LEFT);
        if (StringUtils.isBlank(caseData.getIssuYm())) {
            addColumn(table, 10, 1, "", fontCh12, 0, LEFT);
        }
        else {
            addColumn(table, 10, 1, "核定年月：" + caseData.getIssuYm(), fontCh12, 0, LEFT);
        }

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 16, 1, "核付金額：" + caseData.getIssueAmt(), fontCh12, 0, LEFT);
        addColumn(table, 16, 1, "應收餘額：" + caseData.getRecAmt(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "受理日期：" + caseData.getAppDate(), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 10, 1, "立帳序：" + caseData.getSeqNo(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "立帳對象：" + caseData.getEvtName(), fontCh12, 0, LEFT);
        addColumn(table, 12, 1, "身分證號：" + caseData.getEvtIdnNo(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "出生日期：" + caseData.getEvtBrDate(), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 16, 1, "待撥付給付種類：" + caseData.getGivePayItem(), fontCh12, 0, LEFT);
        addColumn(table, 16, 1, "受理編號：" + caseData.getGiveApNoStr(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "核定年月：" + caseData.getGiveIssuYm(), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 16, 1, "核付金額：" + caseData.getGiveIssueAmt(), fontCh12, 0, LEFT);
        addColumn(table, 16, 1, "應付餘額：" + caseData.getGiveRecAmt(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "受理日期：" + caseData.getGiveAppDate(), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 10, 1, "受款人序：" + caseData.getGiveSeqNo(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "受款人名：" + caseData.getGiveEvtName(), fontCh12, 0, LEFT);
        addColumn(table, 12, 1, "身分證號：" + caseData.getGiveEvtIdnNo(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "出生日期：" + caseData.getGiveEvtBrDate(), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);

        addColumn(table, 42, 1, "上列應扣減案件，敬請儘速回覆是否逕予扣減並請於系統中回應扣減狀況。", fontCh12, 0, LEFT);

        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);

        addColumn(table, 42, 1, "　　此　致", fontCh12, 0, LEFT);

        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);

        addColumn(table, 42, 1, RptTitleUtility.getDivisionTitleForPayCode(caseData.getGiveApNo().substring(0, 1),caseData.getApNoStr().substring(6, 8), DateUtility.getNowWestDate()), fontCh12, 0, LEFT);
        //addColumn(table, 42, 1, caseData.getDeptName() + "給付科", fontCh12, 0, LEFT);
        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);

        addColumn(table, 42, 1, "　　　　" + RptTitleUtility.getDivisionTitle(caseData.getGiveApNo().substring(0, 1), DateUtility.getNowWestDate()) + "　承辦人：＿＿＿＿＿＿＿＿＿　複核：＿＿＿＿＿＿＿＿＿", fontCh12, 0, LEFT);
        //addColumn(table, 42, 1, "　　　　" + caseData.getGiveDeptName() + "　承辦人：＿＿＿＿＿＿＿＿＿　複核：＿＿＿＿＿＿＿＿＿", fontCh12, 0, LEFT);
        addColumn(table, 42, 1, "日期：" + DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(), true), fontCh12, 0, RIGHT);

        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, "-------------------------------------------------------------------------------------", fontCh12, 0, CENTER);

        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);

        addColumn(table, 42, 1, RptTitleUtility.getGroupsTitle(caseData.getGiveApNo().substring(0, 1), DateUtility.getNowWestDate()) + RptTitleUtility.getDivisionTitle(caseData.getGiveApNo().substring(0, 1), DateUtility.getNowWestDate()) + "　另案扣減照會單(回執聯)", fontCh18, 0, CENTER);
        //addColumn(table, 42, 1, "給付處" + caseData.getGiveDeptName() + "　另案扣減照會單(回執聯)", fontCh18, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 14, 1, "", fontCh12, 0, CENTER);
        addColumn(table, 18, 1, "", fontCh18, 0, CENTER);
        addColumn(table, 10, 1, "印表日期：" + DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), false), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 16, 1, "待收回給付種類：" + caseData.getPayItem(), fontCh12, 0, LEFT);
        addColumn(table, 16, 1, "受理編號：" + caseData.getApNoStr(), fontCh12, 0, LEFT);
        if (StringUtils.isBlank(caseData.getIssuYm())) {
            addColumn(table, 10, 1, "", fontCh12, 0, LEFT);
        }
        else {
            addColumn(table, 10, 1, "核定年月：" + caseData.getIssuYm(), fontCh12, 0, LEFT);
        }

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 16, 1, "核付金額：" + caseData.getIssueAmt(), fontCh12, 0, LEFT);
        addColumn(table, 16, 1, "應收餘額：" + caseData.getRecAmt(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "受理日期：" + caseData.getAppDate(), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 10, 1, "立帳序：" + caseData.getSeqNo(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "立帳對象：" + caseData.getEvtName(), fontCh12, 0, LEFT);
        addColumn(table, 12, 1, "身分證號：" + caseData.getEvtIdnNo(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "出生日期：" + caseData.getEvtBrDate(), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 16, 1, "待撥付給付種類：" + caseData.getGivePayItem(), fontCh12, 0, LEFT);
        addColumn(table, 16, 1, "受理編號：" + caseData.getGiveApNoStr(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "核定年月：" + caseData.getGiveIssuYm(), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 16, 1, "核付金額：" + caseData.getGiveIssueAmt(), fontCh12, 0, LEFT);
        addColumn(table, 16, 1, "應付餘額：" + caseData.getGiveRecAmt(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "受理日期：" + caseData.getGiveAppDate(), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, " ", fontCh12, 0, CENTER);

        addColumn(table, 10, 1, "受款人序：" + caseData.getGiveSeqNo(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "受款人名：" + caseData.getGiveEvtName(), fontCh12, 0, LEFT);
        addColumn(table, 12, 1, "身分證號：" + caseData.getGiveEvtIdnNo(), fontCh12, 0, LEFT);
        addColumn(table, 10, 1, "出生日期：" + caseData.getGiveEvtBrDate(), fontCh12, 0, LEFT);

        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);

        addColumn(table, 42, 1, "□逕予扣減　　□不予扣減", fontCh12, 0, LEFT);

        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);

        addColumn(table, 42, 1, "　　此　致", fontCh12, 0, LEFT);

        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);

        addColumn(table, 42, 1, RptTitleUtility.getDivisionTitle(caseData.getGiveApNo().substring(0, 1), DateUtility.getNowWestDate()), fontCh12, 0, LEFT);
        //addColumn(table, 42, 1, caseData.getGiveDeptName() + "給付科", fontCh12, 0, LEFT);
        addColumn(table, 42, 1, "", fontCh12, 0, CENTER);

        addColumn(table, 42, 1, "　　　　" + RptTitleUtility.getDivisionTitleForPayCode(caseData.getGiveApNo().substring(0, 1),caseData.getApNoStr().substring(6, 8), DateUtility.getNowWestDate()) + "　承辦人：＿＿＿＿＿＿＿＿＿　複核：＿＿＿＿＿＿＿＿＿", fontCh12, 0, LEFT);

        addColumn(table, 42, 1, "日期：" + DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(), true), fontCh12, 0, RIGHT);

        document.add(table);
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }

}
