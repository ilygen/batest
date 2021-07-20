package tw.gov.bli.ba.maint.actions;

import java.util.ArrayList;
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
import org.apache.struts.actions.DispatchAction;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintListForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintDetailForm;
import tw.gov.bli.ba.maint.cases.CpiDtlMaintCase;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class CpiDtlMaintListAction extends DispatchAction {
    private static Log log = LogFactory.getLog(CpiDtlMaintListAction.class);

    private MaintService maintService;

    private static final String FORWARD_INSERT_CPI_DTL = "insertCpiDtl"; // 物價指數調整明細新增作業頁面
    private static final String FORWARD_UPDATE_CPI_DTL = "updateCpiDtl"; // 物價指數調整明細修改作業頁面

    /**
     * 維護作業 - 物價指數調整明細維護作業 - 查詢頁面 (bapa0x060f.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
        
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 物價指數調整明細維護作業 - 查詢頁面 CpiDtlMaintListAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();

        CpiDtlMaintListForm listForm = (CpiDtlMaintListForm) form;

        try {
        	
        	//頁面顯示新增申請年度使用 需先放入空List
        	List<CpiDtlMaintCase> adjYearDisList = new ArrayList<CpiDtlMaintCase>();
            CaseSessionHelper.setAdjYearAmountCaseList(adjYearDisList, request);
            
            CpiDtlMaintDetailForm detailForm = new CpiDtlMaintDetailForm();
            detailForm.setIssuYear(DateUtility.getNowChineseDate().substring(0, 3));
            
            //新增預設帶入當年核定年度
            FormSessionHelper.setCpiDtlMaintDetailForm(detailForm, request);
            FormSessionHelper.removeCpiDtlMaintQueryForm(request);
            
            return mapping.findForward(FORWARD_INSERT_CPI_DTL);
            
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CpiDtlMaintListAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    
    /**
     * 維護作業 - 物價指數調整明細查詢作業 - 查詢頁面 (bapa0x061c.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 物價指數調整明細查詢作業 - 查詢頁面 CpiDtlMaintListAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CpiDtlMaintListForm queryForm = (CpiDtlMaintListForm) form;

        try {
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<CpiDtlMaintCase> applyList = CaseSessionHelper.getCpiDtlMaintQueryCase(request);
            CpiDtlMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);
            
            //取得欲更新之申請年度資料
            Bacpidtl bacpidtl = new Bacpidtl();
            bacpidtl.setIssuYear(DateUtility.changeChineseYearType(caseData.getIssuYear()));
            bacpidtl.setCpiYear(DateUtility.changeChineseYearType(caseData.getCpiYear()));
            bacpidtl.setCpiYearE(DateUtility.changeChineseYearType(caseData.getCpiYearE()));
            bacpidtl.setCpiB(caseData.getCpiB());
            bacpidtl.setCpiE(caseData.getCpiE());
            bacpidtl.setCpIndex(caseData.getCpIndex());
            List<CpiDtlMaintCase> updList = maintService.selectUpdDelDataBy(bacpidtl);
            
            //申請年度需x筆斷行 因此做以下處理
            List<CpiDtlMaintCase> adjYearList = new ArrayList<CpiDtlMaintCase>();
            
            StringBuffer adjYearStr = new StringBuffer();

            for(int i = 0 ; i < updList.size(); i++ ){
            	if(i == updList.size()-1 || (i+1)%10 == 0){
            		adjYearStr.append(DateUtility.changeWestYearType(updList.get(i).getAdjYear()));
            		CpiDtlMaintCase adjYearCase = new CpiDtlMaintCase();
            		adjYearCase.setAdjYear(adjYearStr.toString());
            		adjYearList.add(adjYearCase);
            		adjYearStr.setLength(0);
            	}else{
            		adjYearStr.append(DateUtility.changeWestYearType(updList.get(i).getAdjYear())+", ");
            	}
            }
            
            //取得異動人員清單
            List<CpiDtlMaintCase> userDetailList = maintService.selectDetailUserDataBy(bacpidtl);

            CpiDtlMaintDetailForm updateForm = new CpiDtlMaintDetailForm();
            BeanUtility.copyProperties(updateForm, caseData);

            CaseSessionHelper.setAdjYearCaseList(adjYearList, request);
            CaseSessionHelper.setOldAdjYearCase(caseData, request);
            CaseSessionHelper.setUserDetailCaseList(userDetailList, request);
            FormSessionHelper.setCpiDtlMaintDetailForm(updateForm, request);

            return mapping.findForward(FORWARD_UPDATE_CPI_DTL);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CpiDtlMaintListAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    
    
    /**
     * 維護作業 - 物價指數調整明細查詢作業 - 刪除作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 物價指數調整明細查詢作業 - 刪除作業 CpiDtlMaintListAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();

        CpiDtlMaintListForm queryForm = (CpiDtlMaintListForm) form;

        try {
            
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<CpiDtlMaintCase> applyList = CaseSessionHelper.getCpiDtlMaintQueryCase(request);
            CpiDtlMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);

            maintService.deleteBacpidtlData(caseData);      
            
            // 更新清單資料
            List<CpiDtlMaintCase> applyListRe = maintService.getCpiDtlMaintQueryCaseBy(null);
            CaseSessionHelper.setCpiDtlMaintQueryCase(applyListRe, request);            
            //設定刪除成功訊息                        
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());                         

            log.debug("執行 維護作業 - 物價指數調整明細查詢作業 - 刪除作業 CpiDtlMaintListAction.doDelete() 完成... ");
            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);            
            
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("CpiDtlMaintListAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }

}
