package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.encoder.Encode;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bapayrptrecord;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt04Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt07Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt08Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt09Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type1PayAmtCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type1RptCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type2PayAmtCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type2RptCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type3RptCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt11Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt12Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt13Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt14Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt15Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt16Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt17Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt18Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt19Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt20Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt21Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt23Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt24Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt24PayAmtCase;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt22Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt04Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt07Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt08Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt09Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt10Type1Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt10Type2Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt10Type3Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt11Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt12Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt13Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt14Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt15Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt16Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt17Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt18Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt19Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt20Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt21Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt23Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt24Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK04Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK07Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK08Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK10Type1Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK10Type2Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK10Type3Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK17Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK18Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK23Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS04Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS07Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS08Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS10Type1Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS10Type2Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS10Type3Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS17Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS18Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS23Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.ReportUtility;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 列印作業 - 月核定處理相關報表 - 月處理核定報表彙整 - 查詢頁面 (balp0d3m0l.jsp)
 * 
 * @author Evelyn Hsu
 */

public class MonthlyRpt22Action extends BaseDispatchAction {

    private static Log log = LogFactory.getLog(MonthlyRpt22Action.class);

    private RptService rptService;
    private SelectOptionService selectOptionService;
    private static final String TEMPLATE_FILE = "/pdf/MonthlyRpt23.pdf";

    private ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 月核定處理相關報表 - 月處理核定報表彙整 - 查詢頁面 MonthlyRpt22Action.doReport() 開始 ... ");

        final HttpSession session = request.getSession();
        final UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt22Form queryForm = (MonthlyRpt22Form) form;
        try {
            final String payCode = queryForm.getPayCode();
            final String issuYm = queryForm.getIssuYm();
            final String chkDate = queryForm.getChkDate();
            final String npWithLip = queryForm.getNpWithLip(); // 國併勞

            final HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("issuYm", issuYm);
            map.put("chkDate", chkDate);
            map.put("npWithLip", npWithLip);

            // 20101206 Kiyomi 月核定撥付總表改用map1
            final HashMap<String, Object> map1 = new HashMap<String, Object>();
            map1.put("payCode", payCode);
            map1.put("issuYm", DateUtility.changeChineseYearMonthType(issuYm));
            map1.put("chkDate", DateUtility.changeChineseYearMonthType(chkDate));
            map1.put("npWithLip", npWithLip);

            List<Bapayrptrecord> caseData22 = rptService.getMonthlyRpt22DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));

            String tempString = "";
            String[] splitStr;
            final Integer q1 = rptService.getMonthlyRpt22DataByPaytyp(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), "<>");
            final Integer q2 = rptService.getMonthlyRpt22DataByPaytyp(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), null);

            if (caseData22.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                queryForm.reset(mapping, request);
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }
            else {

                // 判斷是否產生報表
                for (Bapayrptrecord rptData : caseData22) {

                    if ((rptData.getRptTyp()).equals("1")) {
                        tempString += "  月核定撥付總表,  月核定合格清冊,";
                    }

                    if ((rptData.getRptTyp()).equals("2")) {
                        tempString += "  給付抵銷紓困貸款明細,";
                    }

                    if ((rptData.getRptTyp()).equals("3")) {

                        if (q1 > 0)
                            tempString += "  核付清單,";

                        if (q2 > 0)
                            tempString += "  核付清單(郵政匯票), 郵政匯票通知／入戶匯款證明,";
                    }

                    if ((rptData.getRptTyp()).equals("4")) {
                        tempString += "  核付明細表,  月核定給付撥款總額表,";
                    }

                    if ((rptData.getRptTyp()).equals("5")) {
                        tempString += "  退匯核定清單,  退匯清冊,  退匯通知書,";
                    }

                    if ((rptData.getRptTyp()).equals("6")) {
                        tempString += "  改匯核定清單,  改匯清冊,";
                    }

                    if ((rptData.getRptTyp()).equals("7")) {
                        tempString += "  應收款立帳清冊,  應收款立帳核定清單,";
                    }

                    if ((rptData.getRptTyp()).equals("8")) {
                        tempString += "  應收已收清冊,  應收已收核定清單,";
                    }

                    if ((rptData.getRptTyp()).equals("9")) {
                        tempString += " 退回現金轉暫收及待結轉清單,";
                    }

                    if ((rptData.getRptTyp()).equals("10")) {
                        tempString += " 勞保年金退回現金清冊,";
                    }

                    if ((rptData.getRptTyp()).equals("11")) {
                        tempString += " 代扣補償金清冊,";
                    }
                }

                String printDate = DateUtility.getNowChineseDate();

                splitStr = tempString.split(",");
                int row = splitStr.length + 1;

                String[][] rtnVals = new String[row][3];
                for (int i = 0; i < splitStr.length; i++) {
                    int d1 = i / 3;
                    int d2 = i % 3;
                    rtnVals[d1][d2] = splitStr[i];

                }

                queryForm.reset(mapping, request);

                // 傳回已成功產製報表清單
                session.setAttribute("rtnValsRow", row % 3 != 0 ? row / 3 + 1 : row / 3);
                session.setAttribute("rtnValsCol", 3);
                session.setAttribute("rtnVals", rtnVals);
                session.setAttribute("issu_Ym", issuYm);
                session.setAttribute("pay_Code", payCode);
                session.setAttribute("chkDate", chkDate);
                session.setAttribute("reports", caseData22.size() == 0 ? 0 : splitStr.length);

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_MonthlyRpt22_" + Encode.forJava(printDate) + ".pdf" + "\"");
                response.setHeader("Content-Encoding", "pdf");
                response.setContentType("application/pdf"); // 設定MIME
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setContentLength(baoOutput.size());
                final InputStream in = HttpHelper.getHttpRequest().getSession(true).getServletContext().getResourceAsStream(TEMPLATE_FILE);

                // 產製報表
                Thread reportThread = new Thread() {
                    public void run() {
                        try {

                            String paySeqNo = null;
                            String isNull = null;
                            String isEqual = null;
                            String notNull = null;
                            String notEqual = null;

                            if (payCode.equals("K")) {
                                if (npWithLip.equals("Y")) {
                                    paySeqNo = "2";
                                }
                                else {
                                    paySeqNo = "1";
                                }
                            }
                            else {
                                paySeqNo = "";
                            }

                            if (payCode.equals("L")) {
                                isNull = "";
                                isEqual = "";
                                notNull = "";
                                notEqual = "";

                            }
                            else {
                                isNull = "IS NULL";
                                isEqual = "=";
                                notNull = "IS NOT NULL";
                                notEqual = "<>";
                            }

                            // 抓 代扣補償金清冊 報表資料
                            {
                                List<MonthlyRpt24Case> caseData24 = rptService.getMonthlyRpt24DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), chkDate);
                                HashMap<String, MonthlyRpt24PayAmtCase> payAmtMap24 = rptService.getMonthlyRpt24PayAmtDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), chkDate);
                                if (caseData24.size() > 0) {
                                    MonthlyRpt24Report report = new MonthlyRpt24Report();
                                    baoOutput = report.doReport(userData, caseData24, payAmtMap24, map);
                                }
                            }

                            // 抓 應收款立帳清冊 報表資料
                            // 【NACHGMK is null】
                            {
                                List<MonthlyRpt21Case> caseData21 = rptService.getMonthlyRpt21DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, isNull, isEqual);
                                // 【NACHGMK is not null】
                                List caseData2101 = null;
                                caseData2101 = new ArrayList<MonthlyRpt21Case>();
                                if (!(payCode.equals("L"))) {
                                    caseData2101 = rptService.getMonthlyRpt21DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, notNull, notEqual);
                                }
                                if (caseData21.size() > 0 || caseData2101.size() > 0) {
                                    MonthlyRpt21Report report = new MonthlyRpt21Report();
                                    baoOutput = report.doReport(userData, caseData21, caseData2101, map);
                                }
                            }

                            // 抓 應收款立帳核定清單 報表資料
                            {
                                List<MonthlyRpt20Case> caseData20 = rptService.getMonthlyRpt20DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, isNull, isEqual);
                                List<MonthlyRpt20Case> caseDataFooter20 = rptService.getMonthlyRpt20DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, isNull, isEqual);
                                List caseData2001 = null;
                                List caseDataFooter2001 = null; // 20120406
                                caseData2001 = new ArrayList<MonthlyRpt20Case>();
                                caseDataFooter2001 = new ArrayList<MonthlyRpt20Case>(); // 20120406
                                if (!(payCode.equals("L"))) {
                                    caseData2001 = rptService.getMonthlyRpt20DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, notNull, notEqual);
                                    caseDataFooter2001 = rptService.getMonthlyRpt20DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, notNull, notEqual);
                                }
                                if (caseData20.size() > 0 || caseData2001.size() > 0) {
                                    MonthlyRpt20Report report = new MonthlyRpt20Report();
                                    baoOutput = report.doReport(userData, caseData20, caseData2001, caseDataFooter20, caseDataFooter2001, map);
                                }
                            }

                            // 抓 退匯應收已收核定清單 報表資料
                            {
                                List caseData19 = null;
                                List caseData1901 = null; // 20120406
                                List caseDataFooter19 = null; // 20120406
                                List caseDataFooter1901 = null; // 20120406
                                caseData19 = new ArrayList<MonthlyRpt19Case>();
                                caseData1901 = new ArrayList<MonthlyRpt19Case>(); // 20120406
                                caseDataFooter19 = new ArrayList<MonthlyRpt19Case>(); // 20120406
                                caseDataFooter1901 = new ArrayList<MonthlyRpt19Case>(); // 20120406

                                if (payCode.equals("L")) {
                                    caseData19 = rptService.getMonthlyRpt19DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "", "");
                                    caseDataFooter19 = rptService.getMonthlyRpt19DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "", "");
                                    caseData1901 = null;
                                    caseDataFooter1901 = null;
                                }
                                else {
                                    // 【NACHGMK is null】
                                    caseData19 = rptService.getMonthlyRpt19DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "=");
                                    caseDataFooter19 = rptService.getMonthlyRpt19DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "=");

                                    // 【NACHGMK is not null】
                                    caseData1901 = rptService.getMonthlyRpt19DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL", "<>");
                                    caseDataFooter1901 = rptService.getMonthlyRpt19DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL", "<>");
                                }

                                // if (caseData19.size() > 0) {
                                if ((payCode.equals("L") && (caseData19 != null && caseData19.size() > 0))
                                                || ((payCode.equals("K") || payCode.equals("S")) && ((caseData19 != null && caseData19.size() > 0) || (caseData1901 != null && caseData1901.size() > 0)))) {
                                    MonthlyRpt19Report report = new MonthlyRpt19Report();
                                    baoOutput = report.doReport(userData, caseData19, caseDataFooter19, caseData1901, caseDataFooter1901, map);
                                }
                            }

                            // 抓 退回現金轉暫收及待結轉清單 報表資料
                            {
                                List caseList18 = null;
                                caseList18 = new ArrayList<MonthlyRpt18Case>();
                                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                    caseList18 = rptService.getMonthlyRpt18DataBy(payCode, issuYm, chkDate);
                                }
                                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                    caseList18 = rptService.getMonthlyRptK18DataBy(payCode, issuYm, chkDate, paySeqNo);
                                }
                                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                    caseList18 = rptService.getMonthlyRptS18DataBy(payCode, issuYm, chkDate);
                                }

                                if (caseList18.size() > 0) {
                                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                        MonthlyRpt18Report report = new MonthlyRpt18Report();
                                        baoOutput = report.doReport(userData, caseList18, map);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                        MonthlyRptK18Report report = new MonthlyRptK18Report();
                                        baoOutput = report.doReport(userData, caseList18, map);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                        MonthlyRptS18Report report = new MonthlyRptS18Report();
                                        baoOutput = report.doReport(userData, caseList18, map);
                                    }
                                }
                            }

                            // 抓 退回現金清冊 報表資料
                            {
                                List caseList17 = null;
                                caseList17 = new ArrayList<MonthlyRpt17Case>();
                                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                    caseList17 = rptService.getMonthlyRpt17DataBy(payCode, issuYm, chkDate);
                                }
                                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                    caseList17 = rptService.getMonthlyRptK17DataBy(payCode, issuYm, chkDate, paySeqNo);
                                }
                                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                    caseList17 = rptService.getMonthlyRptS17DataBy(payCode, issuYm, chkDate);
                                }
                                if (caseList17.size() > 0) {
                                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                        MonthlyRpt17Report report = new MonthlyRpt17Report();
                                        baoOutput = report.doReport(userData, caseList17, map);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                        MonthlyRptK17Report report = new MonthlyRptK17Report();
                                        baoOutput = report.doReport(userData, caseList17, map);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                        MonthlyRptS17Report report = new MonthlyRptS17Report();
                                        baoOutput = report.doReport(userData, caseList17, map);
                                    }
                                }
                            }

                            // 抓 退匯通知書 報表資料
                            {
                                List<MonthlyRpt16Case> caseList16 = rptService.getMonthlyRpt16DataBy(payCode, issuYm, null, null, chkDate);
                                if (caseList16.size() > 0) {
                                    MonthlyRpt16Report report = new MonthlyRpt16Report();
                                    baoOutput = report.doReport(userData, caseList16, true, map);
                                }
                            }

                            // 抓 退匯應收已收清冊 報表資料
                            {
                                List caseData15 = null;
                                List caseData1501 = null;
                                caseData15 = new ArrayList<MonthlyRpt15Case>();
                                caseData1501 = new ArrayList<MonthlyRpt15Case>();
                                if (payCode.equals("L")) {
                                    caseData15 = rptService.getMonthlyRpt15DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "", "");
                                    caseData1501 = null;
                                }
                                else {
                                    // 抓報表資料
                                    // 【NACHGMK is null】
                                    caseData15 = rptService.getMonthlyRpt15DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "=");

                                    // 【NACHGMK is not null】
                                    caseData1501 = rptService.getMonthlyRpt15DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL", "<>");
                                }
                                if ((payCode.equals("L") && caseData15.size() > 0)) {
                                    MonthlyRpt15Report report = new MonthlyRpt15Report();
                                    baoOutput = report.doReport(userData, caseData15, caseData1501, map);
                                }
                                else if (((payCode.equals("K") || payCode.equals("S")) && (caseData15 != null && caseData15.size() > 0) || (caseData1501 != null && caseData1501.size() > 0))) {
                                    MonthlyRpt15Report report = new MonthlyRpt15Report();
                                    baoOutput = report.doReport(userData, caseData15, caseData1501, map);
                                }
                            }

                            // 抓 改匯清冊 報表資料
                            {
                                List<MonthlyRpt14Case> caseData14 = rptService.getMonthlyRpt14DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                                if (caseData14.size() > 0) {
                                    MonthlyRpt14Report report = new MonthlyRpt14Report();
                                    baoOutput = report.doReport(userData, caseData14, map);
                                }
                            }

                            // 抓 改匯核定清單 報表資料
                            {
                                List<MonthlyRpt13Case> caseData13 = rptService.getMonthlyRpt13DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                                List<MonthlyRpt13Case> caseDataFooter13 = rptService.getMonthlyRpt13DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                                if (caseData13.size() > 0) {
                                    MonthlyRpt13Report report = new MonthlyRpt13Report();
                                    baoOutput = report.doReport(userData, caseData13, caseDataFooter13, map);
                                }
                            }

                            // 抓 退匯清冊 報表資料
                            {
                                List<MonthlyRpt12Case> caseData12 = rptService.getMonthlyRpt12DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                                if (caseData12.size() > 0) {
                                    MonthlyRpt12Report report = new MonthlyRpt12Report();
                                    baoOutput = report.doReport(userData, caseData12, map);
                                }
                            }

                            // 抓 退匯核定清單 報表資料
                            {
                                List<MonthlyRpt11Case> caseData11 = rptService.getMonthlyRpt11DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                                List<MonthlyRpt11Case> caseDataFooter11 = rptService.getMonthlyRpt11DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                                if (caseData11.size() > 0) {
                                    MonthlyRpt11Report report = new MonthlyRpt11Report();
                                    baoOutput = report.doReport(userData, caseData11, caseDataFooter11, map);
                                }
                            }

                            // 抓 核付清單 報表資料
                            {
                                if (q1 > 0) {
                                    List caseList10 = null;
                                    List subCaseList10 = null;
                                    HashMap payAmtMap = null;
                                    caseList10 = new ArrayList<MonthlyRpt10Type1RptCase>();
                                    subCaseList10 = new ArrayList<MonthlyRpt10Type1RptCase>();
                                    payAmtMap = new HashMap<String, MonthlyRpt10Type1PayAmtCase>();

                                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                        caseList10 = rptService.getMonthlyRpt10RptType1DataBy(payCode, issuYm, chkDate, new String[] { "F", "N", "Z" }, "NOT IN", new String[] { "A.PAYTYP", "A.APNO", "A.SEQNO", "A.ISSUYM", "A.PAYYM" });
                                        // 代扣補償金
                                        subCaseList10 = rptService.getMonthlyRpt10RptType1DataBy(payCode, issuYm, chkDate, new String[] { "Z" }, "IN", new String[] { "A.BENEVTREL", "A.PAYTYP", "A.COMPNAME1", "A.APNO" });
                                        payAmtMap = rptService.getMonthlyRpt10RptType1PayAmtDataBy(payCode, issuYm, chkDate);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                        caseList10 = rptService.getMonthlyRptK10RptType1DataBy(payCode, issuYm, chkDate, paySeqNo);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                        caseList10 = rptService.getMonthlyRptS10RptType1DataBy(payCode, issuYm, chkDate);
                                    }

                                    if (caseList10.size() > 0 || (payCode.equals("L") && (subCaseList10.size() > 0))) {
                                        if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                            MonthlyRpt10Type1Report report = new MonthlyRpt10Type1Report();
                                            baoOutput = report.doReport(userData, caseList10, payAmtMap, subCaseList10, map);
                                        }
                                        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                            MonthlyRptK10Type1Report report = new MonthlyRptK10Type1Report();
                                            baoOutput = report.doReport(userData, caseList10, map);
                                        }
                                        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                            MonthlyRptS10Type1Report report = new MonthlyRptS10Type1Report();
                                            baoOutput = report.doReport(userData, caseList10, map);
                                        }
                                    }
                                }
                            }

                            // 抓 核付明細表 報表資料
                            {
                                List caseList10Type2 = null;
                                List subCaseList2 = null;
                                HashMap payAmtMap2 = null;
                                caseList10Type2 = new ArrayList<MonthlyRpt10Type2RptCase>();
                                subCaseList2 = new ArrayList<MonthlyRpt10Type2RptCase>();
                                payAmtMap2 = new HashMap<String, MonthlyRpt10Type2PayAmtCase>();

                                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                    caseList10Type2 = rptService.getMonthlyRpt10RptType2DataBy(payCode, issuYm, chkDate, new String[] { "F", "N", "Z" }, "NOT IN", new String[] { "A.APNO", "A.SEQNO", "A.ISSUYM", "A.PAYYM" });
                                    // 代扣補償金
                                    subCaseList2 = rptService.getMonthlyRpt10RptType2DataBy(payCode, issuYm, chkDate, new String[] { "Z" }, "IN", new String[] { "A.BENEVTREL", "A.COMPNAME1", "A.PAYBANKID", "A.BRANCHID", "A.PAYEEACC" });
                                    payAmtMap2 = rptService.getMonthlyRpt10RptType2PayAmtDataBy(payCode, issuYm, chkDate);
                                }
                                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                    caseList10Type2 = rptService.getMonthlyRptK10RptType2DataBy(payCode, issuYm, chkDate, paySeqNo);
                                }
                                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                    caseList10Type2 = rptService.getMonthlyRptS10RptType2DataBy(payCode, issuYm, chkDate);
                                }

                                if (caseList10Type2.size() > 0 || (payCode.equals("L") && (subCaseList2.size() > 0))) {
                                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                        MonthlyRpt10Type2Report report2 = new MonthlyRpt10Type2Report();
                                        baoOutput = report2.doReport(userData, caseList10Type2, payAmtMap2, subCaseList2, map);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                        MonthlyRptK10Type2Report report2 = new MonthlyRptK10Type2Report();
                                        if (StringUtils.equals(paySeqNo, "2")) {
                                            map.put("paySeqNo", "2");
                                        }
                                        else {
                                            map.put("paySeqNo", "1");
                                        }
                                        baoOutput = report2.doReport(userData, caseList10Type2, map);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                        MonthlyRptS10Type2Report report2 = new MonthlyRptS10Type2Report();
                                        baoOutput = report2.doReport(userData, caseList10Type2, map);
                                    }

                                }
                            }

                            {
                                if (q2 > 0) {
                                    List caseList10Type3 = null;
                                    List subCaseList3 = null;
                                    HashMap payAmtMap3 = null;
                                    caseList10Type3 = new ArrayList<MonthlyRpt10Type3RptCase>();
                                    subCaseList3 = new ArrayList<MonthlyRpt10Type3RptCase>();
                                    payAmtMap3 = new HashMap<String, MonthlyRpt10Type1PayAmtCase>();

                                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                        // 抓 核付清單(郵政匯票) 報表資料
                                        caseList10Type3 = rptService.getMonthlyRpt10RptType3DataBy(payCode, issuYm, chkDate, new String[] { "F", "N", "Z" }, "NOT IN", new String[] { "A.APNO", "A.SEQNO", "A.ISSUYM", "A.PAYYM" });
                                        // 代扣補償金
                                        subCaseList3 = rptService.getMonthlyRpt10RptType3DataBy(payCode, issuYm, chkDate, new String[] { "Z" }, "IN", new String[] { "A.BENEVTREL", "A.COMPNAME1", "A.PAYBANKID", "A.BRANCHID", "A.PAYEEACC" });
                                        payAmtMap3 = rptService.getMonthlyRpt10RptType1PayAmtDataBy(payCode, issuYm, chkDate);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                        caseList10Type3 = rptService.getMonthlyRptK10RptType3DataBy(payCode, issuYm, chkDate, paySeqNo);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                        caseList10Type3 = rptService.getMonthlyRptS10RptType3DataBy(payCode, issuYm, chkDate);
                                    }

                                    if (caseList10Type3.size() > 0 || (payCode.equals("L") && (subCaseList3.size() > 0))) {

                                        if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                            MonthlyRpt10Type3Report report3 = new MonthlyRpt10Type3Report();
                                            baoOutput = report3.doReport(userData, caseList10Type3, payAmtMap3, subCaseList3, map);
                                        }
                                        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                            MonthlyRptK10Type3Report report3 = new MonthlyRptK10Type3Report();
                                            baoOutput = report3.doReport(userData, caseList10Type3, map);
                                        }
                                        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                            MonthlyRptS10Type3Report report3 = new MonthlyRptS10Type3Report();
                                            baoOutput = report3.doReport(userData, caseList10Type3, map);
                                        }
                                    }
                                }

                                // 抓 郵政匯票通知／入戶匯款證明報表資料
                                {
                                    InputStream inFile = in;
                                    List caseList23 = null;
                                    caseList23 = new ArrayList<MonthlyRpt23Case>();
                                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                        caseList23 = rptService.getMonthlyRpt23DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                        caseList23 = rptService.getMonthlyRptK23DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                        caseList23 = rptService.getMonthlyRptS23DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                                    }
                                    if (caseList23.size() > 0) {
                                        if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                            MonthlyRpt23Report report = new MonthlyRpt23Report();
                                            baoOutput = report.doReport(inFile, userData, caseList23, map);
                                        }
                                        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                            MonthlyRptK23Report report = new MonthlyRptK23Report();
                                            baoOutput = report.doReport(inFile, userData, caseList23, map);
                                        }
                                        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                            MonthlyRptS23Report report = new MonthlyRptS23Report();
                                            baoOutput = report.doReport(inFile, userData, caseList23, map);
                                        }
                                    }
                                }
                            }

                            // 抓 給付抵銷紓困貸款明細 報表資料
                            {
                                List<MonthlyRpt09Case> caseList09 = rptService.getMonthlyRpt09DataBy(payCode, issuYm, chkDate);
                                if (caseList09.size() > 0) {
                                    MonthlyRpt09Report report = new MonthlyRpt09Report();
                                    baoOutput = report.doReport(userData, caseList09, map);
                                }
                            }

                            // 抓 月核定合格清冊 報表資料
                            {
                                // for Log
                                // [
                                /*
                                 * String procuser = userData.getUserId(); String proctime = DateUtility.getNowWestDateTime(true);
                                 */
                                int pagePerFile = rptService.getMonthlyRpt08Pages(); // 每個檔案的頁數
                                String fileNpWithLip = "N";
                                if (StringUtils.isNotBlank(npWithLip))
                                    fileNpWithLip = npWithLip;
                                String fileName = StringUtils.trimToEmpty(payCode) + "_" + StringUtils.trimToEmpty(DateUtility.changeChineseYearMonthType(issuYm)) + "_" + StringUtils.trimToEmpty(DateUtility.changeDateType(chkDate)) + "_"
                                                + fileNpWithLip + "_MonthlyRpt08_";
                                map.put("baseFileName", fileName);
                                map.put("pagePerFile", String.valueOf(pagePerFile));
                                /*
                                 * map.put("logRptName", "BALP0D3M0L"); map.put("logQryCondition", "PAYCODE=" + StringUtils.trimToEmpty(payCode) + ",ISSUYM=" + StringUtils.trimToEmpty(DateUtility.changeChineseYearMonthType(issuYm)) + ",CHKDATE=" +
                                 * StringUtils.trimToEmpty(DateUtility.changeDateType(chkDate)) + ",NPWITHLIP=" + npWithLip); // private String apNo; // 報表中塞 map.put("logIssuYm",
                                 * StringUtils.trimToEmpty(DateUtility.changeChineseYearMonthType(issuYm))); // private String payYm; // 報表中塞 map.put("logChkdate", StringUtils.trimToEmpty(DateUtility.changeDateType(chkDate))); // private BigDecimal
                                 * page; // 報表中塞 // private String filename; // 報表中塞 map.put("logProcuser", procuser); map.put("logProctime", proctime);
                                 */
                                // ]

                                // 沒產製過再做
                                File[] fileList = ReportUtility.listPDFFile(fileName);
                                if (fileList == null || fileList.length == 0) { // ... [
                                    List caseData08 = null;
                                    List caseData0801 = null; // 20120406
                                    caseData08 = new ArrayList<MonthlyRpt08Case>();
                                    caseData0801 = new ArrayList<MonthlyRpt08Case>(); // 20120406
                                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                        caseData08 = rptService.getMonthlyRpt08DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                        caseData08 = rptService.getMonthlyRptK08DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "=");
                                        caseData0801 = rptService.getMonthlyRptK08DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL", "<>");
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                        caseData08 = rptService.getMonthlyRptS08DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), "IS NULL", "=");
                                        caseData0801 = rptService.getMonthlyRptS08DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), "IS NOT NULL", "<>");
                                    }

                                    if (caseData08.size() > 0 || (!payCode.equals("L") && (caseData0801.size() > 0))) {
                                        if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                            MonthlyRpt08Report report = new MonthlyRpt08Report();
                                            baoOutput = report.doReport(userData, caseData08, map);
                                        }
                                        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                            MonthlyRptK08Report report = new MonthlyRptK08Report();
                                            baoOutput = report.doReport(userData, caseData08, caseData0801, map);
                                        }
                                        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                            MonthlyRptS08Report report = new MonthlyRptS08Report();
                                            baoOutput = report.doReport(userData, caseData08, caseData0801, map);
                                        }
                                    }

                                    // PDF 拆檔
                                    // [
                                    PdfReader reader = new PdfReader(baoOutput.toByteArray());
                                    int n = reader.getNumberOfPages();
                                    log.info("Number of pages : " + n);

                                    int fileSeq = 1;
                                    String outFile = null;
                                    Document document = null;
                                    PdfCopy writer = null;
                                    FileOutputStream fos = null;
                                    try {
                                        for (int i = 1; i <= n; i++) {
                                            outFile = PropertyHelper.getProperty("rpt.path") + fileName + String.format("%03d", fileSeq) + ".pdf";

                                            if (i == 1) {
                                                document = new Document(reader.getPageSizeWithRotation(1));
                                                fos = new FileOutputStream(Encode.forJava(outFile));
                                                writer = new PdfCopy(document, fos);

                                            }

                                            if (document != null && writer != null) {
                                                document.open();

                                                PdfImportedPage page = writer.getImportedPage(reader, i);
                                                writer.addPage(page);

                                                if (i == n || (i % pagePerFile == 0)) {
                                                    fileSeq++;
                                                    document.close();
                                                    writer.close();
                                                    if (i != n) {
                                                        ExceptionUtil.safeColse(fos);
                                                        outFile = PropertyHelper.getProperty("rpt.path") + fileName + String.format("%03d", fileSeq) + ".pdf";
                                                        document = new Document(reader.getPageSizeWithRotation(1));
                                                        fos = new FileOutputStream(Encode.forJava(outFile));
                                                        writer = new PdfCopy(document, fos);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    finally {
                                        ExceptionUtil.safeColse(fos);
                                        ExceptionUtil.safeColse(baoOutput);
                                    }

                                    // 塞 Log
                                    /*
                                     * if (caseData08 != null && caseData08.size() > 0) rptService.insertBarptlogData(caseData08); if (caseData0801 != null && caseData0801.size() > 0) rptService.insertBarptlogData(caseData0801);
                                     */
                                } // ] ... end if (fileList == null || fileList.length == 0)
                            }

                            // 抓 月核定給付撥款總額表 報表資料
                            {
                                List caseList07 = null;
                                caseList07 = new ArrayList<MonthlyRpt07Case>();
                                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                    caseList07 = rptService.getMonthlyRpt07DataBy(payCode, issuYm, chkDate);
                                }
                                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                    caseList07 = rptService.getMonthlyRptK07DataBy(payCode, issuYm, chkDate, paySeqNo);
                                }
                                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                    caseList07 = rptService.getMonthlyRptS07DataBy(payCode, issuYm, chkDate);
                                }

                                if (caseList07.size() > 0) {
                                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                        MonthlyRpt07Report report = new MonthlyRpt07Report();
                                        baoOutput = report.doReport(userData, caseList07, map);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                        MonthlyRptK07Report report = new MonthlyRptK07Report();
                                        baoOutput = report.doReport(userData, caseList07, map);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                        MonthlyRptS07Report report = new MonthlyRptS07Report();
                                        baoOutput = report.doReport(userData, caseList07, map);
                                    }
                                }
                            }

                            // 抓 月核定撥付總表 報表資料
                            {
                                List caseDataFooter04 = null; // 20120406
                                List subCaseDataFooter04 = null; // 20120406
                                List caseDataFooter0401 = null; // 20120406
                                List subCaseDataFooter0401 = null; // 20120406
                                caseDataFooter04 = new ArrayList<MonthlyRpt04Case>(); // 20120406
                                subCaseDataFooter04 = new ArrayList<MonthlyRpt04Case>(); // 20120406
                                caseDataFooter0401 = new ArrayList<MonthlyRpt04Case>(); // 20120406
                                subCaseDataFooter0401 = new ArrayList<MonthlyRpt04Case>(); // 20120406

                                List caseData04 = null;
                                List subCaseData04 = null;
                                caseData04 = new ArrayList<MonthlyRpt04Case>();
                                subCaseData04 = new ArrayList<MonthlyRpt04Case>();

                                List caseData0401 = null; // 20120406
                                List subCaseData0401 = null; // 20120406
                                caseData0401 = new ArrayList<MonthlyRpt04Case>(); // 20120406
                                subCaseData0401 = new ArrayList<MonthlyRpt04Case>(); // 20120406

                                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                    caseData04 = rptService.getMonthlyRpt04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate));
                                    caseDataFooter04 = rptService.getMonthlyRpt04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate));
                                    // 抓 月核定撥付總表 子報表資料
                                    subCaseData04 = rptService.getMonthlyRpt04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "Z" }, "in", DateUtility.changeDateType(chkDate));
                                    subCaseDataFooter04 = rptService.getMonthlyRpt04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "Z" }, "in", DateUtility.changeDateType(chkDate));

                                }
                                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                    // 【NACHGMK is null】
                                    caseData04 = rptService.getMonthlyRptK04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "=");
                                    caseDataFooter04 = rptService.getMonthlyRptK04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), paySeqNo,
                                                    "IS NULL", "=");
                                    // 【NACHGMK is not null】
                                    caseData0401 = rptService.getMonthlyRptK04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL",
                                                    "<>");
                                    caseDataFooter0401 = rptService.getMonthlyRptK04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), paySeqNo,
                                                    "IS NOT NULL", "<>");

                                }
                                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                    // 【NACHGMK is null】
                                    caseData04 = rptService.getMonthlyRptS04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), "IS NULL", "=");
                                    caseDataFooter04 = rptService.getMonthlyRptS04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), "IS NULL", "=");
                                    // 【NACHGMK is not null】
                                    caseData0401 = rptService.getMonthlyRptS04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), "IS NOT NULL", "<>");
                                    caseDataFooter0401 = rptService.getMonthlyRptS04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), "IS NOT NULL",
                                                    "<>");

                                }

                                if (caseData04.size() > 0) {
                                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                                        MonthlyRpt04Report report = new MonthlyRpt04Report();
                                        baoOutput = report.doReport(userData, caseData04, subCaseData04, caseDataFooter04, subCaseDataFooter04, map1);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                                        MonthlyRptK04Report report = new MonthlyRptK04Report();
                                        baoOutput = report.doReport(userData, caseData04, caseData0401, caseDataFooter04, caseDataFooter0401, paySeqNo, map);
                                    }
                                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                                        MonthlyRptS04Report report = new MonthlyRptS04Report();
                                        baoOutput = report.doReport(userData, caseData04, caseData0401, caseDataFooter04, caseDataFooter0401, paySeqNo, map);
                                    }
                                }
                            }
                        }
                        catch (Exception e) {
                            // 設定查詢失敗訊息
                            log.error("產製 月處理核定報表彙整  發生錯誤:" + ExceptionUtility.getStackTrace(e));
                            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
                        }

                    }
                };

                reportThread.start();

                return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 月處理核定報表彙整  發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);

        }

    }

    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MonthlyRpt22Action.doBack() : 列印作業 - 月核定處理相關報表 - 月處理核定報表彙整  - 查詢頁面 (balp0d3m0l.jsp) ...");

        HttpSession session = request.getSession();

        // 將成功產製報表清單 自 Session 中移除
        session.removeAttribute(ConstantKey.PAYKIND_OPTION_LIST);
        session.removeAttribute("rtnValsRow");
        session.removeAttribute("rtnValsCol");
        session.removeAttribute("rtnVals");
        session.removeAttribute("issu_Ym");
        session.removeAttribute("issuYm");
        session.removeAttribute("pay_Code");
        session.removeAttribute("payCode");
        session.removeAttribute("chkDate");
        session.removeAttribute("reports");
        session.removeAttribute("MonthlyRpt22Form");

        // 取得給付種類下拉選單的內容

        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
}
