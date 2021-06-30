package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
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

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt17Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt17Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt17Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK17Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS17Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 月核定處理相關報表 - 退回現金清冊 - 查詢頁面 (balp0d3h0l.jsp)
 * 
 * @author Goston
 */
public class MonthlyRpt17Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt17Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 月核定處理相關報表 - 退回現金清冊 - 查詢頁面 (balp0d3h0l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 月核定處理相關報表 - 退回現金清冊 - 查詢頁面 MonthlyRpt17Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt17Form queryForm = (MonthlyRpt17Form) form;

        String payCode = queryForm.getPayCode();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);

        try {

            String paySeqNo = null;
            if (queryForm.getNpWithLip().equals("Y")) {
                paySeqNo = "2";
            }
            else {
                paySeqNo = "1";
            }

            List<MonthlyRpt17Case> caseList = new ArrayList<MonthlyRpt17Case>();
            // 報表列印資料
            List<MonthlyRpt17Case> reportData = new ArrayList<MonthlyRpt17Case>();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(queryForm.getPayCode())) {// 老年
                // 抓報表資料
                caseList = rptService.getMonthlyRpt17DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());

                if (caseList.size() > 0) {

                    // 【NACHGMK is null】
                    for (MonthlyRpt17Case caseData : caseList) {
                        if (StringUtils.isBlank(caseData.getNaChgMk())) {
                            caseData.setIsNaChgMk("N");
                            reportData.add(caseData);
                        }
                    }
                    // 【NACHGMK is not null】
                    for (MonthlyRpt17Case caseData : caseList) {
                        if (StringUtils.isNotBlank(caseData.getNaChgMk())) {
                            caseData.setIsNaChgMk("Y");
                            reportData.add(caseData);
                        }
                    }

                }
            }
            else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(queryForm.getPayCode())) {// 失能
                caseList = rptService.getMonthlyRptK17DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate(), paySeqNo);

                if (caseList.size() > 0) {

                    // 【NACHGMK is null】
                    for (MonthlyRpt17Case caseData : caseList) {
                        if (StringUtils.isBlank(caseData.getNaChgMk())) {
                            caseData.setIsNaChgMk("N");
                            reportData.add(caseData);
                        }
                    }
                    // 【NACHGMK is not null】
                    for (MonthlyRpt17Case caseData : caseList) {
                        if (StringUtils.isNotBlank(caseData.getNaChgMk())) {
                            caseData.setIsNaChgMk("Y");
                            reportData.add(caseData);
                        }
                    }

                }
            }
            else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(queryForm.getPayCode())) {// 遺屬
                caseList = rptService.getMonthlyRptS17DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());

                if (caseList.size() > 0) {

                    // 【NACHGMK is null】
                    for (MonthlyRpt17Case caseData : caseList) {
                        if (StringUtils.isBlank(caseData.getNaChgMk())) {
                            caseData.setIsNaChgMk("N");
                            reportData.add(caseData);
                        }
                    }
                    // 【NACHGMK is not null】
                    for (MonthlyRpt17Case caseData : caseList) {
                        if (StringUtils.isNotBlank(caseData.getNaChgMk())) {
                            caseData.setIsNaChgMk("Y");
                            reportData.add(caseData);
                        }
                    }

                }
            }

            if (caseList.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(queryForm.getPayCode())) {// 老年
                    MonthlyRpt17Report report = new MonthlyRpt17Report();
                    baoOutput = report.doReport(userData, reportData, map);
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(queryForm.getPayCode())) {// 失能
                    MonthlyRptK17Report report = new MonthlyRptK17Report();
                    baoOutput = report.doReport(userData, reportData, map);
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(queryForm.getPayCode())) {// 遺屬
                    MonthlyRptS17Report report = new MonthlyRptS17Report();
                    baoOutput = report.doReport(userData, reportData, map);
                }

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                // response.setHeader("Content-disposition", "attachment; filename=\"MonthlyRpt17_" + printDate + ".pdf" + "\"");
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(queryForm.getPayCode()) + "_MonthlyRpt17_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
            log.error("產製 退匯通知書 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
