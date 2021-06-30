package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
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
import tw.gov.bli.ba.rpt.cases.MonthlyBatchRptCase;
import tw.gov.bli.ba.rpt.forms.MonthlyBatchRptForm;
import tw.gov.bli.ba.rpt.report.MonthlyBatchRptReport;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 月核定處理相關報表 - 月處理異動清單 - 查詢頁面 (balp0d3r0l.jsp)
 * 
 * @author Noctis
 */
public class MonthlyBatchRptAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyBatchRptAction.class);

    private RptService rptService;

    /**
     * 列印作業 - 月核定處理相關報表 - 月處理異動清單 - 查詢頁面 (balp0d3r0l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 月核定處理相關報表 - 月處理異動清單 - 查詢頁面 MonthlyBatchRptAction.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyBatchRptForm queryForm = (MonthlyBatchRptForm) form;

        try {
            String payCode = queryForm.getPayCode();
            String issuYm = queryForm.getIssuYm();
            String mtestMk = queryForm.getMtestMk();
            String chkDate = queryForm.getChkDate();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("issuYm", DateUtility.changeChineseYearMonthType(issuYm));
            map.put("mtestMk", mtestMk);
            map.put("chkDate", DateUtility.changeDateType(chkDate));

            // 抓報表資料
            List<MonthlyBatchRptCase> caseData = rptService.getMonthlyBatchRptDataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), mtestMk, DateUtility.changeDateType(chkDate));

            if (caseData.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                MonthlyBatchRptReport report = new MonthlyBatchRptReport();
                baoOutput = report.doReport(userData, caseData, map);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_MonthlyBatchRpt_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
            log.error("產製 月處理異動清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
