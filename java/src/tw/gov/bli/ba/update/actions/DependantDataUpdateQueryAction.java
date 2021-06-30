package tw.gov.bli.ba.update.actions;

import java.math.BigDecimal;
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
import tw.gov.bli.ba.update.cases.DependantDataUpdateCase;
import tw.gov.bli.ba.update.cases.DependantEvtDataUpdateCase;
import tw.gov.bli.ba.update.forms.DependantDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 眷屬資料更正 (BAMO0D070C)
 * 
 * @author Evelyn Hsu
 */

public class DependantDataUpdateQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DependantDataUpdateQueryAction.class);

    private UpdateService updateService;
    private SelectOptionService selectOptionService;
    
    // 更正作業 - 眷屬資料更正 - 受款人資料清單頁面
    private static final String FORWARD_MODIFY_DEPENDANT_DATA_LIST = "modifyDependantDataList";
    
    /**
     * 更正作業 - 眷屬資料更正 - 查詢頁面 (bamo0d070c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("更正作業 - 眷屬資料更正 - 查詢頁面 DependantDataUpdateQueryAction.doQuery() 開始 ... ");
        
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DependantDataUpdateQueryForm iform = (DependantDataUpdateQueryForm) form;
                        
        // 清除 Session
        CaseSessionHelper.removeDependantDataUpdateQueryCase(request);
        CaseSessionHelper.removeDependantDataUpdateList(request);

        // 清除明細資料畫面
        FormSessionHelper.removeDependantDataUpdateQueryForm(request);
        FormSessionHelper.removePayeeDataUpdateDetailForm(request);

        try {
            String apNo = iform.getApNo1()+iform.getApNo2()+iform.getApNo3()+iform.getApNo4();
            
            List<DependantEvtDataUpdateCase> dependentEvtList = updateService.selectDependantEvtDataForList(apNo);
            List<DependantDataUpdateCase> dependentList = updateService.selectDependantDataForList(apNo);
            
            //取得是否顯示刪除鍵狀態
            BigDecimal deleteStatus = updateService.getDependantDataCount(apNo);
            
            
            if (dependentEvtList.size() <= 0) {
                // MSG：W1003-無此受理號碼或尚未產生核定資料！
                saveMessages(session, DatabaseMessageHelper.getNoResultForApNoMessage());
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }
           
            FormSessionHelper.setDependantDataUpdateQueryForm(iform, request);
            CaseSessionHelper.setDependantDataUpdateQueryCase(dependentList, request);
            CaseSessionHelper.setDependantEvtDataUpdateQueryCase(dependentEvtList, request);
            session.setAttribute("deleteStatus", deleteStatus);
            return mapping.findForward(FORWARD_MODIFY_DEPENDANT_DATA_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DependantDataUpdateQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
