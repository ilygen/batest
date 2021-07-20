package tw.gov.bli.ba.maint.actions;

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
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.cases.AvgAmtMonMaintCase;
import tw.gov.bli.ba.maint.cases.CpiDtlMaintCase;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 - 查詢頁面 (bapa0x110q.jsp)
 * 
 * @author Noctis
 */
public class AvgAmtMonMaintAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(AvgAmtMonMaintAction.class);

    private static final String FORWARD_QUERY_AVG_AMT_MON_LIST = "querySuccess"; // 勞保年金平均薪資月數參數維護修改作業
    private static final String FORWARD_INSERT_AVG_AMT_MON = "insertAvgAmtMon"; // 勞保年金平均薪資月數參數維護明細新增作業頁面
    private static final String FORWARD_UPDATE_AVG_AMT_MON = "updateAvgAmtMon"; // 物價指數調整明細修改作業頁面
    
    private MaintService maintService;


    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 - 查詢頁面 (bapa0x110q.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 勞保年金平均薪資月數參數維護修改作業 - 查詢頁面AvgAmtMonMaintAction.doQuery() 開始 ... ");

        String effYear = null;

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AvgAmtMonMaintQueryForm queryForm = (AvgAmtMonMaintQueryForm) form;
        AvgAmtMonMaintListForm listForm = new AvgAmtMonMaintListForm();
        
        //民國年轉西元傳入查詢
        String effYearSelect = DateUtility.changeChineseYearType(queryForm.getEffYear());
        try {
            List<AvgAmtMonMaintCase> applyList = maintService.selectBapaavgmonDataListForAvgAmtMonMaintBy(effYearSelect);

            // 施行年度
            if (StringUtils.isNotBlank(queryForm.getEffYear())){
            	effYear = queryForm.getEffYear();
            }
            
            //放入session 更新頁面時使用
            listForm.setEffYearOrg(effYearSelect);

            CaseSessionHelper.setAvgAmtMonMaintQueryCaseList(applyList, request);
            FormSessionHelper.setAvgAmtMonMaintQueryForm(queryForm, effYear, request);
            FormSessionHelper.setAvgAmtMonMaintListForm(listForm, request);

            return mapping.findForward(FORWARD_QUERY_AVG_AMT_MON_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AvgAmtMonMaintAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 - 明細頁面 (bapa0x111f.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
        
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 勞保年金平均薪資月數參數維護修改作業 - 查詢頁面 AvgAmtMonMaintAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AvgAmtMonMaintListForm listForm = (AvgAmtMonMaintListForm) form;
        AvgAmtMonMaintDetailForm detailForm = new AvgAmtMonMaintDetailForm();
        detailForm.setEffYearOrg(listForm.getEffYearOrg());

        try {
            //FormSessionHelper.removeAvgAmtMonMaintQueryForm(request);
            FormSessionHelper.setAvgAmtMonMaintDetailForm(detailForm,request);
            return mapping.findForward(FORWARD_INSERT_AVG_AMT_MON);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AvgAmtMonMaintAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 - 查詢頁面 (bapa0x111f.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 勞保年金平均薪資月數參數維護修改作業 - 查詢頁面 AvgAmtMonMaintAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        
        AvgAmtMonMaintListForm listForm = (AvgAmtMonMaintListForm) form;
        AvgAmtMonMaintDetailForm detailForm = new AvgAmtMonMaintDetailForm();

        try {
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<AvgAmtMonMaintCase> applyList = CaseSessionHelper.getAvgAmtMonMaintQueryCaseList(request);
            AvgAmtMonMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);
            caseData.setDate(DateUtility.changeDateType(caseData.getDate()));
            
            BeanUtility.copyProperties(detailForm, caseData);
            detailForm.setEffYear(DateUtility.changeWestYearType(caseData.getEffYear()));
            detailForm.setEffYearOrg(listForm.getEffYearOrg());

            CaseSessionHelper.setAvgAmtMonMaintQueryCase(caseData, request);
            FormSessionHelper.setAvgAmtMonMaintDetailForm(detailForm, request);

            return mapping.findForward(FORWARD_UPDATE_AVG_AMT_MON);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AvgAmtMonMaintAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 - 刪除作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 勞保年金平均薪資月數參數維護修改作業  - 刪除作業 AvgAmtMonMaintAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AvgAmtMonMaintListForm listForm = (AvgAmtMonMaintListForm) form;

        try {
            
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<AvgAmtMonMaintCase> applyList = CaseSessionHelper.getAvgAmtMonMaintQueryCaseList(request);
            AvgAmtMonMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);
      
            maintService.deleteBapaavgmonData(userData, caseData);
      
            // 更新清單資料
            List<AvgAmtMonMaintCase> applyListNew = maintService.selectBapaavgmonDataListForAvgAmtMonMaintBy(listForm.getEffYearOrg());
            CaseSessionHelper.setAvgAmtMonMaintQueryCaseList(applyListNew, request);            
            //設定刪除成功訊息                        
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());                         
 
            log.debug("執行 維護作業 - 勞保年金平均薪資月數參數維護修改作業  - 刪除作業 AvgAmtMonMaintAction.doDelete() 完成... ");
            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);            
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("AvgAmtMonMaintAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 - 新增頁面 (bapa0x112a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 勞保年金平均薪資月數參數維護修改作業   - 新增頁面 AvgAmtMonMaintAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AvgAmtMonMaintDetailForm detailForm = (AvgAmtMonMaintDetailForm) form;

        try {

        	// 檢查是否有重複資料
            String checkEffYear  = DateUtility.changeChineseYearType(detailForm.getEffYear());
            List<AvgAmtMonMaintCase> checkList = maintService.selectBapaavgmonDataListForAvgAmtMonMaintBy(null);
            
            for(AvgAmtMonMaintCase checkData : checkList){
            	if(checkData.getEffYear().equals(checkEffYear)){
            		// 設定該筆資料已存在訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultToExistMessage());
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            	}
            }

            if (detailForm != null) {
            	AvgAmtMonMaintCase detailData = new AvgAmtMonMaintCase();
                BeanUtility.copyProperties(detailData, detailForm);

                // 存檔
                maintService.insertBapaavgmonData(detailData, userData);
                                                
                // 更新清單資料
                List<AvgAmtMonMaintCase> applyListNew = maintService.selectBapaavgmonDataListForAvgAmtMonMaintBy(detailForm.getEffYearOrg());
                CaseSessionHelper.setAvgAmtMonMaintQueryCaseList(applyListNew, request);    
                                    
                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 維護作業 - 勞保年金平均薪資月數參數維護修改作業  - 新增頁面  AvgAmtMonMaintAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);            
                                                           
            
        }
        catch (Exception e) {
            // 設定新增失敗訊息
            log.error("AvgAmtMonMaintAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業  - 修改頁面 (bapa0x113c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行維護作業 - 物價指數調整明細新增作業 - 修改頁面 AvgAmtMonMaintAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AvgAmtMonMaintDetailForm detailForm = (AvgAmtMonMaintDetailForm) form;
        AvgAmtMonMaintCase beforeData = new AvgAmtMonMaintCase();
        
        // 取得未更新資料
        String checkEffYear  = DateUtility.changeChineseYearType(detailForm.getEffYear());
        List<AvgAmtMonMaintCase> checkList = maintService.selectBapaavgmonDataListForAvgAmtMonMaintBy(null);
        
        for(AvgAmtMonMaintCase checkData : checkList){
        	if(checkData.getEffYear().equals(checkEffYear)){
        		beforeData = checkData;
        	}
        }
        
        try {
            if (detailForm != null) {
            	AvgAmtMonMaintCase detailData = new AvgAmtMonMaintCase();
                BeanUtility.copyProperties(detailData, detailForm);
                
                // 存檔
                maintService.updateBapaavgmonData(detailData,beforeData, userData);

                // 更新清單資料
                List<AvgAmtMonMaintCase> applyListNew = maintService.selectBapaavgmonDataListForAvgAmtMonMaintBy(detailForm.getEffYearOrg());
                CaseSessionHelper.setAvgAmtMonMaintQueryCaseList(applyListNew, request); 

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 維護作業 - 勞保年金平均薪資月數參數維護修改作業  - 修改頁面  AvgAmtMonMaintAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AvgAmtMonMaintAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }

}
