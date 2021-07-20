package tw.gov.bli.ba.update.actions;

import java.io.ByteArrayOutputStream;
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
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.cases.DataUpdateRpt05Case;
import tw.gov.bli.ba.rpt.report.DataUpdateRpt05Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.StopPaymentProcessCase;
import tw.gov.bli.ba.update.forms.StopPaymentProcessDetailForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 止付處理作業 - 查詢頁面 (bamo0d041n.jsp)
 * 
 * @author Swim
 */
public class StopPaymentProcessDetailAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(StopPaymentProcessDetailAction.class);

    private UpdateService updateService;
    private RptService rptService;

    /**
     * 更正作業 - 止付處理作業 - 查詢頁面 (bamo0d041n.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 止付處理作業 - 修改頁面 StopPaymentProcessDetailAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        StopPaymentProcessDetailForm detailForm = (StopPaymentProcessDetailForm) form;

        try {
            if (detailForm != null) {
                StopPaymentProcessCase beforeCaseData = CaseSessionHelper.getStopPaymentProcessQueryCase(request);
                StopPaymentProcessCase caseData = CaseSessionHelper.getStopPaymentProcessQueryCase(request);

                // 存檔
                updateService.updateStopPaymentProcessData(caseData.getApplyList(), detailForm.getStexpndReason(), userData, beforeCaseData.getApplyList());

                // 設定更新成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 更正作業 - 止付處理作業 - 修改頁面  StopPaymentProcessDetailAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("StopPaymentProcessDetailAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 止付處理作業 - 修改頁面 (bamo0d041n.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 止付處理作業 - 修改頁面 StopPaymentProcessDetailAction.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        StopPaymentProcessDetailForm detailForm = (StopPaymentProcessDetailForm) form;

        try {
            StopPaymentProcessCase caseData = CaseSessionHelper.getStopPaymentProcessQueryCase(request);
            // 抓報表資料
            List<DataUpdateRpt05Case> reportData = rptService.getDataUpdateRpt05DataBy(caseData.getApNo());

            if (reportData.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                DataUpdateRpt05Report report = new DataUpdateRpt05Report();
                baoOutput = report.doReport(userData, reportData);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"DataUpdateRpt05_" + printDate + ".pdf" + "\"");
                response.setHeader("Content-Encoding", "pdf");
                response.setContentType("application/pdf"); // 設定MIME
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setContentLength(baoOutput.size());

                // 傳回 Client 端
                ServletOutputStream sout = response.getOutputStream();
                baoOutput.writeTo(sout);
                sout.flush();
                baoOutput.close();
                sout.close();

                return null;
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("StopPaymentProcessDetailAction.doReport() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 止付處理作業 (bamo0d041n.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        // 清除Session資料
        CaseSessionHelper.removeStopPaymentProcessQueryCase(request);
        FormSessionHelper.removeStopPaymentProcessDetailForm(request);
        FormSessionHelper.removeStopPaymentProcessQueryForm(request);
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
