package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt05Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt05Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt05Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class MonthlyRpt05Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt05Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 月核定處理相關報表 - 給付函核定通知書 - 查詢頁面 (balp0d350l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 月核定處理相關報表 - 給付函核定通知書 - 查詢頁面 MonthlyRpt05Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt05Form queryForm = (MonthlyRpt05Form) form;

        try {
            // String payCode = queryForm.getPayCode();
            String issuYm = queryForm.getIssuYm();
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            String payCode = queryForm.getPayCode();
            // HashMap<String, Object> map = new HashMap<String, Object>();
            // map.put("payCode", payCode);
            // map.put("issuYm", DateUtility.changeChineseYearMonthType(issuYm));

            List<MonthlyRpt05Case> caseData = new ArrayList<MonthlyRpt05Case>();
            if (queryForm.getApNo1().equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S)) {
                caseData = rptService.getMonthlyRpt05SurvivorDataBy(userData, apNo, DateUtility.changeChineseYearMonthType(issuYm));
            }
            else if (queryForm.getApNo1().equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_K)) {
                caseData = rptService.getMonthlyRpt05DisabledDataBy(apNo, DateUtility.changeChineseYearMonthType(issuYm));
            }
            else {
                caseData = rptService.getMonthlyRpt05DataBy(apNo, DateUtility.changeChineseYearMonthType(issuYm));
            }

            if (caseData == null || caseData.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                MonthlyRpt05Report report = new MonthlyRpt05Report();
                baoOutput = report.doReport(userData, caseData, payCode, null, null, DateUtility.changeChineseYearMonthType(issuYm), 1);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(queryForm.getPayCode()) + "_MonthlyRpt05_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
            log.error("產製 給付函核定通知書 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
