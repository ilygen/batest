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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt07Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt07Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt07Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK07Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS07Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 月核定處理相關報表 - 月核定給付撥款總額表 - 查詢頁面 (balp0d370l.jsp)
 * 
 * @author Goston
 */
public class MonthlyRpt07Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt07Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 月核定處理相關報表 - 月核定給付撥款總額表 - 查詢頁面 (balp0d370l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 月核定處理相關報表 - 月核定給付撥款總額表 - 查詢頁面 MonthlyRpt07Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt07Form queryForm = (MonthlyRpt07Form) form;

        try {
            String payCode = queryForm.getPayCode();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);

            String paySeqNo = null;
            if (queryForm.getNpWithLip().equals("Y")) {
                paySeqNo = "2";
            }
            else {
                paySeqNo = "1";
            }

            List caseList = null;
            caseList = new ArrayList<MonthlyRpt07Case>();
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(queryForm.getPayCode())) {// 老年
                // 抓報表資料
                // List<MonthlyRpt07Case> caseList = rptService.getMonthlyRpt07DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());
                caseList = rptService.getMonthlyRpt07DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());
            }
            else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(queryForm.getPayCode())) {// 失能
                caseList = rptService.getMonthlyRptK07DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate(), paySeqNo);
            }
            else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(queryForm.getPayCode())) {// 遺屬
                caseList = rptService.getMonthlyRptS07DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());
            }

            if (caseList.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(queryForm.getPayCode())) {// 老年
                    MonthlyRpt07Report report = new MonthlyRpt07Report();
                    baoOutput = report.doReport(userData, caseList, map);
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(queryForm.getPayCode())) {// 失能
                    MonthlyRptK07Report report = new MonthlyRptK07Report();
                    baoOutput = report.doReport(userData, caseList, map);
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(queryForm.getPayCode())) {// 遺屬
                    MonthlyRptS07Report report = new MonthlyRptS07Report();
                    baoOutput = report.doReport(userData, caseList, map);
                }

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                // response.setHeader("Content-disposition", "attachment; filename=\"MonthlyRpt07_" + printDate + ".pdf" + "\"");
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_MonthlyRpt07_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
            log.error("產製 月核定給付撥款總額表 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
