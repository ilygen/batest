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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt23Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt23Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt23Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK23Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS23Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 月核定處理相關報表 - 郵政匯票通知／入戶匯款證明整 - 查詢頁面 (balp0d3n0l.jsp)
 * 
 * @author Evelyn Hsu
 */

public class MonthlyRpt23Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt23Action.class);
    private RptService rptService;

    /**
     * 列印作業 - 月核定處理相關報表 - 應收款立帳清冊 - 查詢頁面 (balp0d3l0l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 月核定處理相關報表 -郵政匯票通知／入戶匯款證明整 - 查詢頁面 MonthlyRpt23Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt23Form queryForm = (MonthlyRpt23Form) form;

        try {
            String payCode = queryForm.getPayCode();
            String issuYm = queryForm.getIssuYm();
            String chkDate = queryForm.getChkDate();
            String npWithLip = queryForm.getNpWithLip(); // 國併勞

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("issuYm", issuYm);
            map.put("chkDate", chkDate);
            map.put("npWithLip", npWithLip);

            String paySeqNo = null;
            if (npWithLip.equals("Y")) {
                paySeqNo = "2";
            }
            else {
                paySeqNo = "1";
            }

            List caseData = null;
            caseData = new ArrayList<MonthlyRpt23Case>();
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                // 抓報表資料
                // List<MonthlyRpt23Case> caseData = rptService.getMonthlyRpt23DataBy(payCode,DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                caseData = rptService.getMonthlyRpt23DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
            }
            else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                caseData = rptService.getMonthlyRptK23DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo);
            }
            else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                caseData = rptService.getMonthlyRptS23DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
            }

            if (caseData.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                queryForm.reset(mapping, request);
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                    MonthlyRpt23Report report = new MonthlyRpt23Report();
                    baoOutput = report.doReport(null, userData, caseData, map);
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                    MonthlyRptK23Report report = new MonthlyRptK23Report();
                    baoOutput = report.doReport(null, userData, caseData, map);
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                    MonthlyRptS23Report report = new MonthlyRptS23Report();
                    baoOutput = report.doReport(null, userData, caseData, map);
                }

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                // response.setHeader("Content-disposition", "attachment; filename=\"MonthlyRpt23_" + printDate + ".pdf" + "\"");
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_MonthlyRpt23_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
            log.error("產製 應郵政匯票通知／入戶匯款證明  發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
