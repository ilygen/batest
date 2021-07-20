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
import tw.gov.bli.ba.maint.forms.ReplacementRatioMaintDetailForm;
import tw.gov.bli.ba.maint.forms.ReplacementRatioMaintListForm;
import tw.gov.bli.ba.maint.forms.ReplacementRatioMaintQueryForm;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.cases.AvgAmtMonMaintCase;
import tw.gov.bli.ba.maint.cases.CpiDtlMaintCase;
import tw.gov.bli.ba.maint.cases.ReplacementRatioMaintCase;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 老年年金所得替代率參數維護作業 - 查詢頁面 (bapa0x120q.jsp)
 * 
 * @author Noctis
 */
public class ReplacementRatioMaintAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ReplacementRatioMaintAction.class);

    private static final String FORWARD_QUERY_REPLACEMENT_RATIO_LIST = "querySuccess"; // 老年年金所得替代率參數維護作業
    private static final String FORWARD_INSERT_REPLACEMENT_RATIO = "insertAvgAmtMon"; // 老年年金所得替代率參數維護明細新增作業頁面
    private static final String FORWARD_UPDATE_REPLACEMENT_RATIO = "updateAvgAmtMon"; // 老年年金所得替代率參數維護明細修改作業頁面
    private static final String payCode = "L"; //給付別
    
    private MaintService maintService;


    /**
     * 維護作業 - 老年年金所得替代率參數維護作業 - 查詢頁面 (bapa0x120q.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 老年年金所得替代率參數維護作業 - 查詢頁面ReplacementRatioMaintAction.doQuery() 開始 ... ");

        String effYear = null;

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReplacementRatioMaintQueryForm queryForm = (ReplacementRatioMaintQueryForm) form;
        ReplacementRatioMaintListForm listForm = new ReplacementRatioMaintListForm();
        
        //民國年月轉西元傳入查詢
        String effYmSelect = DateUtility.changeChineseYearMonthType(queryForm.getEffYm());
        try {
            List<ReplacementRatioMaintCase> applyList = maintService.selectBapairrDataForReplacementRatioMaintBy(effYmSelect,payCode);

            // 施行年度
            if (StringUtils.isNotBlank(queryForm.getEffYm())){
            	effYear = queryForm.getEffYm();
            }
            
            //放入session 更新頁面時使用
            listForm.setEffYmOrg(effYmSelect);

            CaseSessionHelper.setReplacementRatioMaintQueryCaseList(applyList, request);
            FormSessionHelper.setReplacementRatioMaintQueryForm(queryForm, effYear, request);
            FormSessionHelper.setReplacementRatioMaintListForm(listForm, request);

            return mapping.findForward(FORWARD_QUERY_REPLACEMENT_RATIO_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReplacementRatioMaintAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業 - 明細頁面 (bapa0x121f.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
        
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 老年年金所得替代率參數維護作業 - 查詢頁面 ReplacementRatioMaintAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReplacementRatioMaintListForm listForm = (ReplacementRatioMaintListForm) form;
        ReplacementRatioMaintDetailForm detailForm = new ReplacementRatioMaintDetailForm();
        detailForm.setEffYmOrg(listForm.getEffYmOrg());

        try {
            FormSessionHelper.setReplacementRatioMaintDetailForm(detailForm,request);
            return mapping.findForward(FORWARD_INSERT_REPLACEMENT_RATIO);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReplacementRatioMaintAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業 - 查詢頁面 (bapa0x121f.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 老年年金所得替代率參數維護作業 - 查詢頁面 AvgAmtMonMaintAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        
        ReplacementRatioMaintListForm listForm = (ReplacementRatioMaintListForm) form;
        ReplacementRatioMaintDetailForm detailForm = new ReplacementRatioMaintDetailForm();

        try {
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<ReplacementRatioMaintCase> applyList = CaseSessionHelper.getReplacementRatioMaintQueryCaseList(request);
            ReplacementRatioMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);
            
            BeanUtility.copyProperties(detailForm, caseData);
            detailForm.setEffYm(DateUtility.changeWestYearMonthType(caseData.getEffYm()));
            detailForm.setEffYmOrg(listForm.getEffYmOrg());
            detailForm.setDate(DateUtility.changeDateType(detailForm.getDate()));

            CaseSessionHelper.setReplacementRatioMaintQueryCase(caseData, request);
            FormSessionHelper.setReplacementRatioMaintDetailForm(detailForm, request);

            return mapping.findForward(FORWARD_UPDATE_REPLACEMENT_RATIO);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReplacementRatioMaintAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業 - 刪除作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 老年年金所得替代率參數維護作業  - 刪除作業 ReplacementRatioMaintAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReplacementRatioMaintListForm listForm = (ReplacementRatioMaintListForm) form;

        try {
            
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<ReplacementRatioMaintCase> applyList = CaseSessionHelper.getReplacementRatioMaintQueryCaseList(request);
            ReplacementRatioMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);
            caseData.setPayCode(payCode);
      
            maintService.deleteBapairrData(userData, caseData);
      
            // 更新清單資料
            List<ReplacementRatioMaintCase> applyListNew = maintService.selectBapairrDataForReplacementRatioMaintBy(listForm.getEffYmOrg(),payCode);
            CaseSessionHelper.setReplacementRatioMaintQueryCaseList(applyListNew, request);            
            //設定刪除成功訊息                        
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());                         
 
            log.debug("執行 維護作業 - 老年年金所得替代率參數維護作業  - 刪除作業 ReplacementRatioMaintAction.doDelete() 完成... ");
            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);            
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("ReplacementRatioMaintAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
     * 維護作業 - 老年年金所得替代率參數維護作業 - 新增頁面 (bapa0x122a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 老年年金所得替代率參數維護作業   - 新增頁面 AvgAmtMonMaintAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReplacementRatioMaintDetailForm detailForm = (ReplacementRatioMaintDetailForm) form;

        try {
        	//轉換BigDecimal
            detailForm.setInsAvg1(getBigDecimalValue(detailForm.getInsAvg1()));
            detailForm.setInsAvg2(getBigDecimalValue(detailForm.getInsAvg2()));
            detailForm.setInsAvg3(getBigDecimalValue(detailForm.getInsAvg3()));
            detailForm.setInsAvg4(getBigDecimalValue(detailForm.getInsAvg4()));
            detailForm.setIrrTypeA1(getBigDecimalValue(detailForm.getIrrTypeA1()));
            detailForm.setIrrTypeB1(getBigDecimalValue(detailForm.getIrrTypeB1()));
            detailForm.setIrrTypeC1(getBigDecimalValue(detailForm.getIrrTypeC1()));
            detailForm.setIrrTypeD1(getBigDecimalValue(detailForm.getIrrTypeD1()));
            detailForm.setIrrTypeA2(getBigDecimalValue(detailForm.getIrrTypeA2()));
            detailForm.setIrrTypeB2(getBigDecimalValue(detailForm.getIrrTypeB2()));
            detailForm.setIrrTypeC2(getBigDecimalValue(detailForm.getIrrTypeC2()));
            detailForm.setIrrTypeD2(getBigDecimalValue(detailForm.getIrrTypeD2()));
            
        	// 檢查是否有重複資料
            String checkEffYm  = DateUtility.changeChineseYearMonthType(detailForm.getEffYm());
            List<ReplacementRatioMaintCase> checkList = maintService.selectBapairrDataForReplacementRatioMaintBy(null,payCode);
            
            for(ReplacementRatioMaintCase checkData : checkList){
            	if(checkData.getEffYm().equals(checkEffYm)){
            		// 設定該筆資料已存在訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultToExistMessage());
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            	}
            }

            if (detailForm != null) {
            	ReplacementRatioMaintCase detailData = new ReplacementRatioMaintCase();
                BeanUtility.copyProperties(detailData, detailForm);
                detailData.setPayCode(payCode);
                // 存檔
                maintService.insertBapairrData(detailData, userData);
                                                
                // 更新清單資料
                List<ReplacementRatioMaintCase> applyListNew = maintService.selectBapairrDataForReplacementRatioMaintBy(detailForm.getEffYmOrg(),payCode);
                CaseSessionHelper.setReplacementRatioMaintQueryCaseList(applyListNew, request);    
                                    
                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 維護作業 - 老年年金所得替代率參數維護作業  - 新增頁面  ReplacementRatioMaintAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);            
                                                           
            
        }
        catch (Exception e) {
            // 設定新增失敗訊息
            log.error("ReplacementRatioMaintAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 維護作業 - 老年年金所得替代率參數維護作業  - 修改頁面 (bapa0x123c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行維護作業 - 老年年金所得替代率參數維護作業 - 修改頁面 ReplacementRatioMaintAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReplacementRatioMaintDetailForm detailForm = (ReplacementRatioMaintDetailForm) form;
        ReplacementRatioMaintCase beforeData = new ReplacementRatioMaintCase();
        
        // 取得未更新資料
        String checkEffYm  = DateUtility.changeChineseYearMonthType(detailForm.getEffYm());
        List<ReplacementRatioMaintCase> checkList = maintService.selectBapairrDataForReplacementRatioMaintBy(null,payCode);
        
        for(ReplacementRatioMaintCase checkData : checkList){
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
                detailForm.setIrrTypeA1(getBigDecimalValue(detailForm.getIrrTypeA1()));
                detailForm.setIrrTypeB1(getBigDecimalValue(detailForm.getIrrTypeB1()));
                detailForm.setIrrTypeC1(getBigDecimalValue(detailForm.getIrrTypeC1()));
                detailForm.setIrrTypeD1(getBigDecimalValue(detailForm.getIrrTypeD1()));
                detailForm.setIrrTypeA2(getBigDecimalValue(detailForm.getIrrTypeA2()));
                detailForm.setIrrTypeB2(getBigDecimalValue(detailForm.getIrrTypeB2()));
                detailForm.setIrrTypeC2(getBigDecimalValue(detailForm.getIrrTypeC2()));
                detailForm.setIrrTypeD2(getBigDecimalValue(detailForm.getIrrTypeD2()));
                
            	ReplacementRatioMaintCase detailData = new ReplacementRatioMaintCase();
                BeanUtility.copyProperties(detailData, detailForm);
                
                // 存檔
                detailData.setPayCode(payCode);
                beforeData.setPayCode(payCode);
                maintService.updateBapairrData(detailData,beforeData, userData);

                // 更新清單資料
                List<ReplacementRatioMaintCase> applyListNew = maintService.selectBapairrDataForReplacementRatioMaintBy(detailForm.getEffYmOrg(),payCode);
                CaseSessionHelper.setReplacementRatioMaintQueryCaseList(applyListNew, request); 

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 維護作業 - 老年年金所得替代率參數維護作業  - 修改頁面  ReplacementRatioMaintAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReplacementRatioMaintAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
