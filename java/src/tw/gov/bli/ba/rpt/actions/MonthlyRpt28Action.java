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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt28Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt28Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt28Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 列印作業 - 應收收回相關報表 - 退匯現應收已收註銷清冊 - 查詢頁面 (balp0d640l.jsp)
 * 
 * @author Noctis
 */
public class MonthlyRpt28Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt28Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 應收收回相關報表 - 退匯現應收已收註銷清冊 - 查詢頁面 (balp0d640l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 應收收回相關報表 - 退匯現應收已收註銷清冊 - 查詢頁面 MonthlyRpt28Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt28Form queryForm = (MonthlyRpt28Form) form;

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

            List<MonthlyRpt28Case> caseDataList = new ArrayList<MonthlyRpt28Case>();

            // 抓報表資料
            if (receiveType.equals("1")) {
                caseDataList = rptService.getMonthlyRpt28DataBy("15", apNo, DateUtility.changeDateType(chkDate), seqNo, null);
            }
            else {
                caseDataList = rptService.getMonthlyRpt28DataBy("14", apNo, DateUtility.changeDateType(chkDate), seqNo, null);
            }
            // 報表列印資料
            List<MonthlyRpt28Case> reportData = new ArrayList<MonthlyRpt28Case>();

            if (caseDataList.size() > 0) {

                // 【NACHGMK is null】
                for (MonthlyRpt28Case caseData : caseDataList) {
                    if (StringUtils.isBlank(caseData.getNaChgMk())) {
                        caseData.setIsNaChgMk("N");
                        reportData.add(caseData);
                    }
                }
                // 【NACHGMK is not null】
                for (MonthlyRpt28Case caseData : caseDataList) {
                    if (StringUtils.isNotBlank(caseData.getNaChgMk())) {
                        caseData.setIsNaChgMk("Y");
                        reportData.add(caseData);
                    }
                }

            }

            if (caseDataList.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                queryForm.reset(mapping, request);
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                MonthlyRpt28Report report = new MonthlyRpt28Report();
                baoOutput = report.doReport(userData, reportData, map);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_MonthlyRpt28_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
                queryForm.reset(mapping, request);
                return null;
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 退匯現應收已收註銷清冊 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
