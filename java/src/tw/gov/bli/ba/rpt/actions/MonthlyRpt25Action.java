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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt25Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt25Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt25Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 應收收回相關報表 - 退匯/現應收已收核定清單 - 查詢頁面 (balp0d610l.jsp)
 * 
 * @author swim
 */
public class MonthlyRpt25Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt02Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 月核定處理相關報表 - 退匯核定清單 - 查詢頁面 (balp0d3b0l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 應收收回相關報表 - 退匯/現應收已收註銷核定清單 - 查詢頁面  MonthlyRpt25Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt25Form queryForm = (MonthlyRpt25Form) form;

        try {
            String payCode = queryForm.getApNo1();
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            String seqNo = queryForm.getSeqNo();
            String chkDate = queryForm.getChkDate();
            String receiveType = queryForm.getReceiveType();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("chkDAte", DateUtility.changeDateType(chkDate));
            map.put("receiveType", receiveType);

            List<MonthlyRpt25Case> caseDataList = new ArrayList<MonthlyRpt25Case>();
            List<MonthlyRpt25Case> caseDataFooter = new ArrayList<MonthlyRpt25Case>();

            // 抓報表資料
            if (receiveType.equals("1")) {
                // 退匯
                caseDataList = rptService.selectMonthlyRpt25DataBy("13", apNo, DateUtility.changeDateType(chkDate), seqNo, null);
                caseDataFooter = rptService.selectMonthlyRpt25Report("13", apNo, DateUtility.changeDateType(chkDate), seqNo, null);
            }
            else {
                // 退現
                caseDataList = rptService.selectMonthlyRpt25DataBy("12", apNo, DateUtility.changeDateType(chkDate), seqNo, null);
                caseDataFooter = rptService.selectMonthlyRpt25Report("12", apNo, DateUtility.changeDateType(chkDate), seqNo, null);
            }
            // 報表列印資料
            List<MonthlyRpt25Case> reportDataList = new ArrayList<MonthlyRpt25Case>();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode) || (ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 失能

                if (caseDataList.size() > 0) {

                    // 【NACHGMK is null】
                    for (MonthlyRpt25Case caseData : caseDataList) {
                        if (StringUtils.isBlank(caseData.getNaChgMk())) {
                            caseData.setIsNaChgMk("N");
                            reportDataList.add(caseData);
                        }
                    }
                    // 【NACHGMK is not null】
                    for (MonthlyRpt25Case caseData : caseDataList) {
                        if (StringUtils.isNotBlank(caseData.getNaChgMk())) {
                            caseData.setIsNaChgMk("Y");
                            reportDataList.add(caseData);
                        }
                    }

                }
            }

            if (caseDataList.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 失能
                    MonthlyRpt25Report report = new MonthlyRpt25Report();
                    baoOutput = report.doReport(userData, caseDataList, caseDataFooter, map);
                }
                else {
                    MonthlyRpt25Report report = new MonthlyRpt25Report();
                    baoOutput = report.doReport(userData, reportDataList, caseDataFooter, map);
                }

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_MonthlyRpt25_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
            log.error("產製 退匯/現應收已收核定清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
