package tw.gov.bli.ba.update.actions;

import java.util.List;
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
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.forms.PayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 受款人資料更正 (BAMO0D080C)
 * 
 * @author swim
 */
public class PayeeDataUpdateQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(PayeeDataUpdateQueryAction.class);

    private UpdateService updateService;
    private SelectOptionService selectOptionService;

    // 更正作業 - 受款人資料更正 - 受款人資料清單頁面
    private static final String FORWARD_MODIFY_PAYEE_DATA_LIST = "modifyPayeeDataList";

    /**
     * 更正作業 - 受款人資料更正 - 查詢頁面 (bamo0d080c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 受款人資料更正 - 查詢頁面 PayeeDataUpdateQueryAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PayeeDataUpdateQueryForm queryForm = (PayeeDataUpdateQueryForm) form;

        // 清除 Session
        CaseSessionHelper.removePayeeDataUpdateQueryCase(request);
        CaseSessionHelper.removePayeeDataUpdateDetailCase(request);
        CaseSessionHelper.removePayeeDataUpdateList(request);
        CaseSessionHelper.removePayeeDataForBadaprCase(request);
        // 清除明細資料畫面
        FormSessionHelper.removePayeeDataUpdateQueryForm(request);
        FormSessionHelper.removePayeeDataUpdateDetailForm(request);

        try {
            List<BaappbaseDataUpdateCase> applyList = updateService.selectPayeeDataForList(queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4());
            
            if (applyList.size() <= 0) {
                // MSG：W1003-無此受理號碼或尚未產生核定資料！
                saveMessages(session, DatabaseMessageHelper.getNoResultForApNoMessage());
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }
            
            for(BaappbaseDataUpdateCase caseData : applyList){
            	//判斷是否開啟 改匯註銷按鈕使用
            	String dateCount1 = updateService.getDataCount1ForQuery(caseData.getApNo(),caseData.getSeqNo());
            	String dateCount2 = updateService.getDataCount2ForQuery(caseData.getApNo(),caseData.getSeqNo());
            	String dateCount3 = updateService.getDataCount3ForQuery(caseData.getApNo(),caseData.getSeqNo());
            	caseData.setDateCount1(dateCount1);
            	caseData.setDateCount2(dateCount2);
            	caseData.setDateCount3(dateCount3);
            }

            FormSessionHelper.setPayeeDataUpdateQueryForm(queryForm, request);
            CaseSessionHelper.setPayeeDataUpdateQueryCase(applyList, request);

            return mapping.findForward(FORWARD_MODIFY_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PayeeDataUpdateQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
}
