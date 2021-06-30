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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt20Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt20Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt20Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 月核定處理相關報表 - 應收款立帳核定清單 - 查詢頁面 (balp0d3d0l.jsp)
 * 
 * @author swim
 */
public class MonthlyRpt20Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt02Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 月核定處理相關報表 - 應收款立帳核定清單 - 查詢頁面 (balp0d340l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 月核定處理相關報表 - 應收款立帳核定清單 - 查詢頁面 MonthlyRpt13Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt20Form queryForm = (MonthlyRpt20Form) form;

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

            List caseData = null;
            List caseDataFooter = null; // 20120406
            caseData = new ArrayList<MonthlyRpt20Case>();
            caseDataFooter = new ArrayList<MonthlyRpt20Case>(); // 20120406
            List caseData1 = null;
            List caseDataFooter1 = null; // 20120406
            caseData1 = new ArrayList<MonthlyRpt20Case>();
            caseDataFooter1 = new ArrayList<MonthlyRpt20Case>(); // 20120406

            // 抓報表資料
            // 【NACHGMK is null】
            // List<MonthlyRpt20Case> caseData = rptService.getMonthlyRpt20DataBy(payCode,DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
            caseData = rptService.getMonthlyRpt20DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, isNull, isEqual);
            caseDataFooter = rptService.getMonthlyRpt20DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, isNull, isEqual);
            // 【NACHGMK is not null】

            if (!(payCode.equals("L"))) {
                caseData1 = rptService.getMonthlyRpt20DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, notNull, notEqual);
                caseDataFooter1 = rptService.getMonthlyRpt20DataFooterBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, notNull, notEqual);
            }

            if (caseData.size() == 0 && caseData1.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                MonthlyRpt20Report report = new MonthlyRpt20Report();
                baoOutput = report.doReport(userData, caseData, caseData1, caseDataFooter, caseDataFooter1, map);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_MonthlyRpt20_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
            log.error("產製 應收款立帳核定清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
