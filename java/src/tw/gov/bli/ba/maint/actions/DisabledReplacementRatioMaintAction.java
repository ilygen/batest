package tw.gov.bli.ba.maint.actions;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.SystemMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.maint.forms.AvgAmtMonMaintDetailForm;
import tw.gov.bli.ba.maint.forms.AvgAmtMonMaintListForm;
import tw.gov.bli.ba.maint.forms.AvgAmtMonMaintQueryForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintListForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintQueryForm;
import tw.gov.bli.ba.maint.forms.DisabledReplacementRatioMaintDetailForm;
import tw.gov.bli.ba.maint.forms.DisabledReplacementRatioMaintListForm;
import tw.gov.bli.ba.maint.forms.DisabledReplacementRatioMaintQueryForm;
import tw.gov.bli.ba.maint.forms.ReplacementRatioMaintDetailForm;
import tw.gov.bli.ba.maint.forms.ReplacementRatioMaintListForm;
import tw.gov.bli.ba.maint.forms.ReplacementRatioMaintQueryForm;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.cases.AvgAmtMonMaintCase;
import tw.gov.bli.ba.maint.cases.CpiDtlMaintCase;
import tw.gov.bli.ba.maint.cases.DisabledReplacementRatioMaintCase;
import tw.gov.bli.ba.maint.cases.ReplacementRatioMaintCase;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 失能年金所得替代率參數維護作業 - 查詢頁面 (bapa0x130q.jsp)
 * 
 * @author Noctis
 */
public class DisabledReplacementRatioMaintAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DisabledReplacementRatioMaintAction.class);

    private static final String FORWARD_QUERY_REPLACEMENT_RATIO_LIST = "querySuccess"; // 失能年金所得替代率參數維護作業
    private static final String FORWARD_INSERT_REPLACEMENT_RATIO = "insertAvgAmtMon"; // 失能年金所得替代率參數維護明細新增作業頁面
    private static final String FORWARD_UPDATE_REPLACEMENT_RATIO = "updateAvgAmtMon"; // 失能年金所得替代率參數維護明細修改作業頁面
    private static final String payCode = "K"; //給付別
    
    private MaintService maintService;


    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 - 查詢頁面 (bapa0x130q.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 失能年金所得替代率參數維護作業 - 查詢頁面DisabledReplacementRatioMaintAction.doQuery() 開始 ... ");

        String effYear = null;

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledReplacementRatioMaintQueryForm queryForm = (DisabledReplacementRatioMaintQueryForm) form;
        DisabledReplacementRatioMaintListForm listForm = new DisabledReplacementRatioMaintListForm();
        
        //民國年月轉西元傳入查詢
        String effYmSelect = DateUtility.changeChineseYearMonthType(queryForm.getEffYm());
        try {
            List<DisabledReplacementRatioMaintCase> applyList = maintService.selectBapairrDataForDisabledReplacementRatioMaintBy(effYmSelect,payCode);

            // 施行年度
            if (StringUtils.isNotBlank(queryForm.getEffYm())){
            	effYear = queryForm.getEffYm();
            }
            
            //放入session 更新頁面時使用
            listForm.setEffYmOrg(effYmSelect);

            CaseSessionHelper.setDisabledReplacementRatioMaintQueryCaseList(applyList, request);
            FormSessionHelper.setDisabledReplacementRatioMaintQueryForm(queryForm, effYear, request);
            FormSessionHelper.setDisabledReplacementRatioMaintListForm(listForm, request);

            return mapping.findForward(FORWARD_QUERY_REPLACEMENT_RATIO_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledReplacementRatioMaintAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 - 明細頁面 (bapa0x131f.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
        
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 失能年金所得替代率參數維護作業 - 查詢頁面 DisabledReplacementRatioMaintAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledReplacementRatioMaintListForm listForm = (DisabledReplacementRatioMaintListForm) form;
        DisabledReplacementRatioMaintDetailForm detailForm = new DisabledReplacementRatioMaintDetailForm();
        detailForm.setEffYmOrg(listForm.getEffYmOrg());

        try {
            FormSessionHelper.setDisabledReplacementRatioMaintDetailForm(detailForm,request);
            return mapping.findForward(FORWARD_INSERT_REPLACEMENT_RATIO);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledReplacementRatioMaintAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 - 查詢頁面 (bapa0x131f.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 失能年金所得替代率參數維護作業 - 查詢頁面 AvgAmtMonMaintAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        
        DisabledReplacementRatioMaintListForm listForm = (DisabledReplacementRatioMaintListForm) form;
        DisabledReplacementRatioMaintDetailForm detailForm = new DisabledReplacementRatioMaintDetailForm();

        try {
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<DisabledReplacementRatioMaintCase> applyList = CaseSessionHelper.getDisabledReplacementRatioMaintQueryCaseList(request);
            DisabledReplacementRatioMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);
            
            BeanUtility.copyProperties(detailForm, caseData);
            detailForm.setEffYm(DateUtility.changeWestYearMonthType(caseData.getEffYm()));
            detailForm.setEffYmOrg(listForm.getEffYmOrg());
            detailForm.setDate(DateUtility.changeDateType(detailForm.getDate()));

            CaseSessionHelper.setDisabledReplacementRatioMaintQueryCase(caseData, request);
            FormSessionHelper.setDisabledReplacementRatioMaintDetailForm(detailForm, request);

            return mapping.findForward(FORWARD_UPDATE_REPLACEMENT_RATIO);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledReplacementRatioMaintAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 - 刪除作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 失能年金所得替代率參數維護作業  - 刪除作業 DisabledReplacementRatioMaintAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledReplacementRatioMaintListForm listForm = (DisabledReplacementRatioMaintListForm) form;

        try {
            
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<DisabledReplacementRatioMaintCase> applyList = CaseSessionHelper.getDisabledReplacementRatioMaintQueryCaseList(request);
            DisabledReplacementRatioMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);
            caseData.setPayCode(payCode);
      
            maintService.deleteDisabledBapairrData(userData, caseData);
      
            // 更新清單資料
            List<DisabledReplacementRatioMaintCase> applyListNew = maintService.selectBapairrDataForDisabledReplacementRatioMaintBy(listForm.getEffYmOrg(),payCode);
            CaseSessionHelper.setDisabledReplacementRatioMaintQueryCaseList(applyListNew, request);            
            //設定刪除成功訊息                        
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());                         
 
            log.debug("執行 維護作業 - 失能年金所得替代率參數維護作業  - 刪除作業 DisabledReplacementRatioMaintAction.doDelete() 完成... ");
            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);            
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("DisabledReplacementRatioMaintAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }
    
    
    /**
     * 維護作業 - 物價指數調整明細查詢作業 (bapa0x061c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	//移除查詢條件
    	FormSessionHelper.removeCpiDtlMaintQueryForm(request);
    	return mapping.findForward(ConstantKey.FORWARD_BACK);
    }
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 - 新增頁面 (bapa0x132a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 失能年金所得替代率參數維護作業   - 新增頁面 AvgAmtMonMaintAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledReplacementRatioMaintDetailForm detailForm = (DisabledReplacementRatioMaintDetailForm) form;

        try {
        	//轉換BigDecimal
            detailForm.setInsAvg1(getBigDecimalValue(detailForm.getInsAvg1()));
            detailForm.setInsAvg2(getBigDecimalValue(detailForm.getInsAvg2()));
            detailForm.setInsAvg3(getBigDecimalValue(detailForm.getInsAvg3()));
            detailForm.setInsAvg4(getBigDecimalValue(detailForm.getInsAvg4()));
            detailForm.setIrrTypeA2(getBigDecimalValue(detailForm.getIrrTypeA2()));
            detailForm.setIrrTypeB2(getBigDecimalValue(detailForm.getIrrTypeB2()));
            detailForm.setIrrTypeC2(getBigDecimalValue(detailForm.getIrrTypeC2()));
            detailForm.setIrrTypeD2(getBigDecimalValue(detailForm.getIrrTypeD2()));

        	// 檢查是否有重複資料
            String checkEffYm  = DateUtility.changeChineseYearMonthType(detailForm.getEffYm());
            List<DisabledReplacementRatioMaintCase> checkList = maintService.selectBapairrDataForDisabledReplacementRatioMaintBy(null,payCode);
            
            for(DisabledReplacementRatioMaintCase checkData : checkList){
            	if(checkData.getEffYm().equals(checkEffYm)){
            		// 設定該筆資料已存在訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultToExistMessage());
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            	}
            }

            if (detailForm != null) {
            	DisabledReplacementRatioMaintCase detailData = new DisabledReplacementRatioMaintCase();
                BeanUtility.copyProperties(detailData, detailForm);
                detailData.setPayCode(payCode);
                // 存檔
                maintService.insertDisabledBapairrData(detailData, userData);
                                                
                // 更新清單資料
                List<DisabledReplacementRatioMaintCase> applyListNew = maintService.selectBapairrDataForDisabledReplacementRatioMaintBy(detailForm.getEffYmOrg(),payCode);
                CaseSessionHelper.setDisabledReplacementRatioMaintQueryCaseList(applyListNew, request);    
                                    
                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 維護作業 - 失能年金所得替代率參數維護作業  - 新增頁面  DisabledReplacementRatioMaintAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);            
                                                           
            
        }
        catch (Exception e) {
            // 設定新增失敗訊息
            log.error("DisabledReplacementRatioMaintAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 維護作業 - 失能年金所得替代率參數維護作業  - 修改頁面 (bapa0x133c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行維護作業 - 失能年金所得替代率參數維護作業 - 修改頁面 DisabledReplacementRatioMaintAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledReplacementRatioMaintDetailForm detailForm = (DisabledReplacementRatioMaintDetailForm) form;
        DisabledReplacementRatioMaintCase beforeData = new DisabledReplacementRatioMaintCase();
        
        // 取得未更新資料
        String checkEffYm  = DateUtility.changeChineseYearMonthType(detailForm.getEffYm());
        List<DisabledReplacementRatioMaintCase> checkList = maintService.selectBapairrDataForDisabledReplacementRatioMaintBy(null,payCode);
        
        for(DisabledReplacementRatioMaintCase checkData : checkList){
        	if(checkData.getEffYm().equals(checkEffYm)){
        		beforeData = checkData;
        	}
        }
        
        try {
            if (detailForm != null) {
            	
            	//轉換BigDecimal
                detailForm.setInsAvg1(getBigDecimalValue(detailForm.getInsAvg1()));
                detailForm.setInsAvg2(getBigDecimalValue(detailForm.getInsAvg2()));
                detailForm.setInsAvg3(getBigDecimalValue(detailForm.getInsAvg3()));
                detailForm.setInsAvg4(getBigDecimalValue(detailForm.getInsAvg4()));
                detailForm.setIrrTypeA2(getBigDecimalValue(detailForm.getIrrTypeA2()));
                detailForm.setIrrTypeB2(getBigDecimalValue(detailForm.getIrrTypeB2()));
                detailForm.setIrrTypeC2(getBigDecimalValue(detailForm.getIrrTypeC2()));
                detailForm.setIrrTypeD2(getBigDecimalValue(detailForm.getIrrTypeD2()));
            	
            	DisabledReplacementRatioMaintCase detailData = new DisabledReplacementRatioMaintCase();
                BeanUtility.copyProperties(detailData, detailForm);
                
                // 存檔
                detailData.setPayCode(payCode);
                beforeData.setPayCode(payCode);
                maintService.updateDisabledBapairrData(detailData,beforeData, userData);

                // 更新清單資料
                List<DisabledReplacementRatioMaintCase> applyListNew = maintService.selectBapairrDataForDisabledReplacementRatioMaintBy(detailForm.getEffYmOrg(),payCode);
                CaseSessionHelper.setDisabledReplacementRatioMaintQueryCaseList(applyListNew, request); 

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 維護作業 - 失能年金所得替代率參數維護作業  - 修改頁面  DisabledReplacementRatioMaintAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledReplacementRatioMaintAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }
    
    /**
     * BigDecimal 為 null 時轉換為 0
     * 
     * @param mapping
     * @return BigDecimal
     */
    public BigDecimal getBigDecimalValue(BigDecimal formValue){
    	if(formValue == null){
    		return new BigDecimal(0);
    	}else{
    		return formValue;
    	}
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }

}
