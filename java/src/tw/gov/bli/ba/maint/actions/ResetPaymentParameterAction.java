package tw.gov.bli.ba.maint.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.maint.cases.ResetPaymentParameterCase;
import tw.gov.bli.ba.maint.forms.ResetPaymentParameterForm;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.DateUtil;

/**
 * 更正作業 - 重設案件給付參數資料 - 查詢頁面 (bapa0x150q.jsp)
 * 
 * @author KIYOMI
 */
public class ResetPaymentParameterAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ResetPaymentParameterAction.class);
    private static final String FORWARD_QUERY_SUCCESS = "querySuccess";
    private static final String FORWARD_QUERY_FAIL = "queryFail";
    private static final String FORWARD_UPDATE_SUCCESS = "updateSuccess";
    private static final String FORWARD_UPDATE_FAIL = "updateFail";
    private static final String FORWARD_BACK = "back";
    private static final String FORWARD_FAIL = "fail";
    private MaintService maintService;
    private UpdateService updateService;

    /**
     * 更正作業 - 重設案件給付參數資料 - 查詢頁面 (bapa0x150q.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(" 更正作業 - 重設案件給付參數資料 - 查詢頁面 ResetPaymentParameterAction.doQuery() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ResetPaymentParameterForm queryForm = (ResetPaymentParameterForm) form;
        try {
            String sApNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            ResetPaymentParameterCase querycase = new ResetPaymentParameterCase();

            // 查詢資料
            ResetPaymentParameterCase dataList = new ResetPaymentParameterCase();

            dataList = maintService.selectDataForResetPaymentParameter(sApNo);

            if (dataList == null) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            CaseSessionHelper.setResetPaymentParameterCase(dataList, request);

            log.debug("執行 更正作業 - 重設案件給付參數資料 - 查詢頁面 ResetPaymentParameterAction.doQuery() 完成 ... ");
            return mapping.findForward(FORWARD_QUERY_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ResetPaymentParameterAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維謢作業 - 重設案件給付參數資料 - 修改頁面 (bapa0x151c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(" 維謢作業 - 重設案件給付參數資料 - 修改頁面 ResetPaymentParameterAction.doUpdate() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ResetPaymentParameterForm queryForm = (ResetPaymentParameterForm) form;
        try {
            String sApNo = queryForm.getApNo();

            if (StringUtils.equals(queryForm.getFuncType(), "1")) { // 重讀 CIPB
                maintService.regetCipbForResetPaymentParameter(sApNo, userData);

            }
            else if (StringUtils.equals(queryForm.getFuncType(), "2")) { // 清除 CPI 基準年月
                maintService.updateCpiBaseYMForResetPaymentParameter(sApNo, userData);

            }
            else if (StringUtils.equals(queryForm.getFuncType(), "3")) { // 清除計算平均薪資申請年度
                maintService.updateInsAvgAmtAppYearForResetPaymentParameter(sApNo, userData);

            }

            // 重新查詢更新後的資料
            ResetPaymentParameterCase dataList = new ResetPaymentParameterCase();

            dataList = maintService.selectDataForResetPaymentParameter(sApNo);

            if (dataList == null) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            CaseSessionHelper.setResetPaymentParameterCase(dataList, request);

            saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            log.debug("執行 維謢作業 - 重設案件給付參數資料 - 修改頁面 ResetPaymentParameterAction.doUpdate() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ResetPaymentParameterAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 維謢作業 - 重設案件給付參數資料 - 修改頁面 (bapa0x151c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("維謢作業 - 重設案件給付參數資料 - 修改頁面 ResetPaymentParameterAction.doBack() 開始 ... ");
        HttpSession session = request.getSession();

        try {
            CaseSessionHelper.removeResetPaymentParameterCase(request);
            FormSessionHelper.removeResetPaymentParameterForm(request);
            log.debug("執行 維謢作業 - 重設案件給付參數資料 - 修改頁面 ResetPaymentParameterAction.doBack() 完成 ... ");
            return mapping.findForward(FORWARD_BACK);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ResetPaymentParameterAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

}
