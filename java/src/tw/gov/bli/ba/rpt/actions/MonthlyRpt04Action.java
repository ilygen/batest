package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt04Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt04Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt04Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK04Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS04Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 月核定處理相關報表 - 月核定撥付總表 - 查詢頁面 (balp0d340l.jsp)
 * 
 * @author swim
 */
public class MonthlyRpt04Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt04Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 月核定處理相關報表 - 月核定撥付總表 - 查詢頁面 (balp0d340l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 月核定處理相關報表 - 月核定撥付總表 - 查詢頁面 MonthlyRpt04Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt04Form queryForm = (MonthlyRpt04Form) form;

        try {
            String payCode = queryForm.getPayCode();
            String issuYm = queryForm.getIssuYm();
            String chkDate = queryForm.getChkDate();
            String npWithLip = queryForm.getNpWithLip(); // 國併勞

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("issuYm", DateUtility.changeChineseYearMonthType(issuYm));
            map.put("chkDate", DateUtility.changeDateType(chkDate));
            map.put("npWithLip", npWithLip);

            String paySeqNo = null;
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

            List caseData = null;
            List subCaseData = null;
            caseData = new ArrayList<MonthlyRpt04Case>();
            subCaseData = new ArrayList<MonthlyRpt04Case>();

            List caseDataFooter = null; // 20120406
            List subCaseDataFooter = null; // 20120406
            List caseDataFooter1 = null; // 20120406
            List subCaseDataFooter1 = null; // 20120406
            caseDataFooter = new ArrayList<MonthlyRpt04Case>(); // 20120406
            subCaseDataFooter = new ArrayList<MonthlyRpt04Case>(); // 20120406
            caseDataFooter1 = new ArrayList<MonthlyRpt04Case>(); // 20120406
            subCaseDataFooter1 = new ArrayList<MonthlyRpt04Case>(); // 20120406

            List caseData1 = null; // 20120406
            List subCaseData1 = null; // 20120406
            caseData1 = new ArrayList<MonthlyRpt04Case>(); // 20120406
            subCaseData1 = new ArrayList<MonthlyRpt04Case>(); // 20120406

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                // 抓報表資料
                // List<MonthlyRpt04Case> caseData = rptService.getMonthlyRpt04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "('F','N','Z')", "NOT IN", DateUtility.changeDateType(chkDate));
                caseData = rptService.getMonthlyRpt04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate));
                caseDataFooter = rptService.getMonthlyRpt04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate));

                // 抓子報表資料（代扣補償金）
                // List<MonthlyRpt04Case> subCaseData = rptService.getMonthlyRpt04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "Z", null, DateUtility.changeDateType(chkDate));
                subCaseData = rptService.getMonthlyRpt04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "Z" }, "in", DateUtility.changeDateType(chkDate));
                subCaseDataFooter = rptService.getMonthlyRpt04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "Z" }, "in", DateUtility.changeDateType(chkDate));
            }
            else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                // 抓報表資料
                // 【NACHGMK is null】
                caseData = rptService.getMonthlyRptK04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "=");
                caseDataFooter = rptService.getMonthlyRptK04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "=");
                // 【NACHGMK is not null】
                caseData1 = rptService.getMonthlyRptK04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL", "<>");
                caseDataFooter1 = rptService.getMonthlyRptK04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL", "<>");

                /**
                 * // 抓子報表資料（代扣補償金） // 【NACHGMK is null】 subCaseData = rptService.getMonthlyRptK04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "Z", null, DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "=");
                 * subCaseDataFooter = rptService.getMonthlyRptK04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "Z", null, DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "="); // 【NACHGMK is not null】 subCaseData1 =
                 * rptService.getMonthlyRptK04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "Z", null, DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL", "<>"); subCaseDataFooter1 =
                 * rptService.getMonthlyRptK04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "Z", null, DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL", "<>");
                 **/
            }
            else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                // 抓報表資料
                // 【NACHGMK is null】
                caseData = rptService.getMonthlyRptS04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), "IS NULL", "=");
                caseDataFooter = rptService.getMonthlyRptS04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), "IS NULL", "=");
                // 【NACHGMK is not null】
                caseData1 = rptService.getMonthlyRptS04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), "IS NOT NULL", "<>");
                caseDataFooter1 = rptService.getMonthlyRptS04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), new String[] { "F", "N", "Z" }, "NOT IN", DateUtility.changeDateType(chkDate), "IS NOT NULL", "<>");

                /**
                 * // 抓子報表資料（代扣補償金） // 【NACHGMK is null】 subCaseData = rptService.getMonthlyRptS04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "Z", null, DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "=");
                 * subCaseDataFooter = rptService.getMonthlyRptS04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "Z", null, DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "="); // 【NACHGMK is not null】 subCaseData1 =
                 * rptService.getMonthlyRptS04DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "Z", null, DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL", "<>"); subCaseDataFooter1 =
                 * rptService.getMonthlyRptS04DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), "Z", null, DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL", "<>");
                 **/
            }

            if (caseData.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                    MonthlyRpt04Report report = new MonthlyRpt04Report();
                    baoOutput = report.doReport(userData, caseData, subCaseData, caseDataFooter, subCaseDataFooter, map);
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                    MonthlyRptK04Report report = new MonthlyRptK04Report();
                    baoOutput = report.doReport(userData, caseData, caseData1, caseDataFooter, caseDataFooter1, paySeqNo, map);
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                    MonthlyRptS04Report report = new MonthlyRptS04Report();
                    baoOutput = report.doReport(userData, caseData, caseData1, caseDataFooter, caseDataFooter1, paySeqNo, map);
                }

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                // response.setHeader("Content-disposition", "attachment; filename=\"MonthlyRpt04_" + printDate + ".pdf" + "\"");
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_MonthlyRpt04_" + Encode.forJava(printDate) + ".pdf" + "\"");
                response.setHeader("Content-Encoding", "pdf");
                response.setContentType("application/pdf"); // 設定MIME
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setContentLength(baoOutput.size());

                // 傳回 Client 端

                ServletOutputStream sout = null;
                try {
                    sout = response.getOutputStream();
                    baoOutput.writeTo(sout);
                    sout.flush();
                }
                catch (Exception e) {
                }
                finally {
                    if (baoOutput != null)
                        baoOutput.close();
                    if (sout != null)
                        sout.close();
                }

                return null;
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 月核定撥付總表 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
