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
import tw.gov.bli.ba.maint.forms.SurvivorReplacementRatioMaintDetailForm;
import tw.gov.bli.ba.maint.forms.SurvivorReplacementRatioMaintListForm;
import tw.gov.bli.ba.maint.forms.SurvivorReplacementRatioMaintQueryForm;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.cases.AvgAmtMonMaintCase;
import tw.gov.bli.ba.maint.cases.CpiDtlMaintCase;
import tw.gov.bli.ba.maint.cases.SurvivorReplacementRatioMaintCase;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 遺屬年金所得替代率參數維護作業 - 查詢頁面 (bapa0x140q.jsp)
 * 
 * @author Noctis
 */
public class SurvivorReplacementRatioMaintAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(SurvivorReplacementRatioMaintAction.class);

    private static final String FORWARD_QUERY_REPLACEMENT_RATIO_LIST = "querySuccess"; // 遺屬年金所得替代率參數維護作業
    private static final String FORWARD_INSERT_REPLACEMENT_RATIO = "insertAvgAmtMon"; // 遺屬年金所得替代率參數維護明細新增作業頁面
    private static final String FORWARD_UPDATE_REPLACEMENT_RATIO = "updateAvgAmtMon"; // 遺屬年金所得替代率參數維護明細修改作業頁面
    private static final String payCode = "S"; //給付別
    
    private MaintService maintService;


    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業 - 查詢頁面 (bapa0x140q.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 遺屬年金所得替代率參數維護作業 - 查詢頁面SurvivorReplacementRatioMaintAction.doQuery() 開始 ... ");

        String effYear = null;

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        SurvivorReplacementRatioMaintQueryForm queryForm = (SurvivorReplacementRatioMaintQueryForm) form;
        SurvivorReplacementRatioMaintListForm listForm = new SurvivorReplacementRatioMaintListForm();
        
        //民國年月轉西元傳入查詢
        String effYmSelect = DateUtility.changeChineseYearMonthType(queryForm.getEffYm());
        try {
            List<SurvivorReplacementRatioMaintCase> applyList = maintService.selectBapairrDataForSurvivorReplacementRatioMaintBy(effYmSelect,payCode);

            // 施行年度
            if (StringUtils.isNotBlank(queryForm.getEffYm())){
            	effYear = queryForm.getEffYm();
            }
            
            //放入session 更新頁面時使用
            listForm.setEffYmOrg(effYmSelect);

            CaseSessionHelper.setSurvivorReplacementRatioMaintQueryCaseList(applyList, request);
            FormSessionHelper.setSurvivorReplacementRatioMaintQueryForm(queryForm, effYear, request);
            FormSessionHelper.setSurvivorReplacementRatioMaintListForm(listForm, request);

            return mapping.findForward(FORWARD_QUERY_REPLACEMENT_RATIO_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorReplacementRatioMaintAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業 - 明細頁面 (bapa0x141f.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
        
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 遺屬年金所得替代率參數維護作業 - 查詢頁面 SurvivorReplacementRatioMaintAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        SurvivorReplacementRatioMaintListForm listForm = (SurvivorReplacementRatioMaintListForm) form;
        SurvivorReplacementRatioMaintDetailForm detailForm = new SurvivorReplacementRatioMaintDetailForm();
        detailForm.setEffYmOrg(listForm.getEffYmOrg());

        try {
            FormSessionHelper.setSurvivorReplacementRatioMaintDetailForm(detailForm,request);
            return mapping.findForward(FORWARD_INSERT_REPLACEMENT_RATIO);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorReplacementRatioMaintAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業 - 查詢頁面 (bapa0x141f.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 遺屬年金所得替代率參數維護作業 - 查詢頁面 AvgAmtMonMaintAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        
        SurvivorReplacementRatioMaintListForm listForm = (SurvivorReplacementRatioMaintListForm) form;
        SurvivorReplacementRatioMaintDetailForm detailForm = new SurvivorReplacementRatioMaintDetailForm();

        try {
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<SurvivorReplacementRatioMaintCase> applyList = CaseSessionHelper.getSurvivorReplacementRatioMaintQueryCaseList(request);
            SurvivorReplacementRatioMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);
            
            BeanUtility.copyProperties(detailForm, caseData);
            detailForm.setEffYm(DateUtility.changeWestYearMonthType(caseData.getEffYm()));
            detailForm.setEffYmOrg(listForm.getEffYmOrg());
            detailForm.setDate(DateUtility.changeDateType(detailForm.getDate()));

            CaseSessionHelper.setSurvivorReplacementRatioMaintQueryCase(caseData, request);
            FormSessionHelper.setSurvivorReplacementRatioMaintDetailForm(detailForm, request);

            return mapping.findForward(FORWARD_UPDATE_REPLACEMENT_RATIO);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorReplacementRatioMaintAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業 - 刪除作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 遺屬年金所得替代率參數維護作業  - 刪除作業 SurvivorReplacementRatioMaintAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        SurvivorReplacementRatioMaintListForm listForm = (SurvivorReplacementRatioMaintListForm) form;

        try {
            
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<SurvivorReplacementRatioMaintCase> applyList = CaseSessionHelper.getSurvivorReplacementRatioMaintQueryCaseList(request);
            SurvivorReplacementRatioMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);
            caseData.setPayCode(payCode);
      
            maintService.deleteSurvivorBapairrData(userData, caseData);
      
            // 更新清單資料
            List<SurvivorReplacementRatioMaintCase> applyListNew = maintService.selectBapairrDataForSurvivorReplacementRatioMaintBy(listForm.getEffYmOrg(),payCode);
            CaseSessionHelper.setSurvivorReplacementRatioMaintQueryCaseList(applyListNew, request);            
            //設定刪除成功訊息                        
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());                         
 
            log.debug("執行 維護作業 - 遺屬年金所得替代率參數維護作業  - 刪除作業 SurvivorReplacementRatioMaintAction.doDelete() 完成... ");
            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);            
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("SurvivorReplacementRatioMaintAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
     * 維護作業 - 遺屬年金所得替代率參數維護作業 - 新增頁面 (bapa0x142a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 遺屬年金所得替代率參數維護作業   - 新增頁面 AvgAmtMonMaintAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        SurvivorReplacementRatioMaintDetailForm detailForm = (SurvivorReplacementRatioMaintDetailForm) form;

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
            List<SurvivorReplacementRatioMaintCase> checkList = maintService.selectBapairrDataForSurvivorReplacementRatioMaintBy(null,payCode);
            
            for(SurvivorReplacementRatioMaintCase checkData : checkList){
            	if(checkData.getEffYm().equals(checkEffYm)){
            		// 設定該筆資料已存在訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultToExistMessage());
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            	}
            }

            if (detailForm != null) {
            	SurvivorReplacementRatioMaintCase detailData = new SurvivorReplacementRatioMaintCase();
                BeanUtility.copyProperties(detailData, detailForm);
                detailData.setPayCode(payCode);
                // 存檔
                maintService.insertSurvivorBapairrData(detailData, userData);
                                                
                // 更新清單資料
                List<SurvivorReplacementRatioMaintCase> applyListNew = maintService.selectBapairrDataForSurvivorReplacementRatioMaintBy(detailForm.getEffYmOrg(),payCode);
                CaseSessionHelper.setSurvivorReplacementRatioMaintQueryCaseList(applyListNew, request);    
                                    
                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 維護作業 - 遺屬年金所得替代率參數維護作業  - 新增頁面  SurvivorReplacementRatioMaintAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);            
                                                           
            
        }
        catch (Exception e) {
            // 設定新增失敗訊息
            log.error("SurvivorReplacementRatioMaintAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業  - 修改頁面 (bapa0x143c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行維護作業 - 遺屬年金所得替代率參數維護作業 - 修改頁面 SurvivorReplacementRatioMaintAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        SurvivorReplacementRatioMaintDetailForm detailForm = (SurvivorReplacementRatioMaintDetailForm) form;
        SurvivorReplacementRatioMaintCase beforeData = new SurvivorReplacementRatioMaintCase();
        
        // 取得未更新資料
        String checkEffYm  = DateUtility.changeChineseYearMonthType(detailForm.getEffYm());
        List<SurvivorReplacementRatioMaintCase> checkList = maintService.selectBapairrDataForSurvivorReplacementRatioMaintBy(null,payCode);
        
        for(SurvivorReplacementRatioMaintCase checkData : checkList){
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
                
            	SurvivorReplacementRatioMaintCase detailData = new SurvivorReplacementRatioMaintCase();
                BeanUtility.copyProperties(detailData, detailForm);
                
                // 存檔
                detailData.setPayCode(payCode);
                beforeData.setPayCode(payCode);
                maintService.updateSurvivorBapairrData(detailData,beforeData, userData);

                // 更新清單資料
                List<SurvivorReplacementRatioMaintCase> applyListNew = maintService.selectBapairrDataForSurvivorReplacementRatioMaintBy(detailForm.getEffYmOrg(),payCode);
                CaseSessionHelper.setSurvivorReplacementRatioMaintQueryCaseList(applyListNew, request); 

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 維護作業 - 遺屬年金所得替代率參數維護作業  - 修改頁面  SurvivorReplacementRatioMaintAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorReplacementRatioMaintAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
