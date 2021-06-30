package tw.gov.bli.ba.bj.actions;

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
import tw.gov.bli.ba.bj.cases.CheckJobCase;
import tw.gov.bli.ba.bj.forms.CheckJobForm;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FormSessionHelper;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 批次處理 - 檢核作業 - 檢核確認作業 - 查詢頁面  (baba0m210x.jsp)
 * 
 * @author Noctis
 */
public class CheckJobAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(CheckJobAction.class);

    private BjService bjService;
    
    // session key
 	public static final String CHECKBOX_INDEX = "checkBoxIndex";
    
    /**
     * 批次處理 - 檢核作業 - 檢核確認作業 - 查詢頁面  (baba0m210x.jsp) 按下 確認
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 檢核作業 - 檢核確認作業 - 查詢頁面  CheckJobAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        CheckJobForm queryForm = (CheckJobForm) form;

        try {
        	
            // 取得資料
        	List<CheckJobCase> dataList = bjService.selectBarvconfirmDataBy(queryForm.getPayCode(), DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()));
        	
        	// 紀錄checkBox狀態 (目前只會有單筆，多筆時會用到)
        	StringBuffer checkConfirmMk = new StringBuffer();
        	
        	for(int i = 0 ; i < dataList.size() ; i++){
        	   String confirmMk = dataList.get(i).getConfirmMk();
        	   
        	   if(StringUtils.equals(confirmMk, "Y")){
        		   if( i == dataList.size() - 1){
        			   checkConfirmMk.append(i);
        		   }else{
        			   checkConfirmMk.append(i + ",");
        		   }
        	   }
        	}
        	
        	if(dataList.size() == 0){
        			
        		saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("批次處理 - 檢核作業 - 檢核確認作業 - 查詢頁面  CheckJobAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                    
        	}else{

        		queryForm.setConfirmMkOfConfirm(checkConfirmMk.toString());
            		
            	// 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setCheckJobCaseList(dataList, request);
                FormSessionHelper.setCheckJobForm(queryForm, request);
                    
            	log.debug("批次處理 - 檢核作業 - 檢核確認作業 - 查詢頁面  CheckJobAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_LIST);
        	}
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CheckJobAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 批次處理 - 檢核作業 - 檢核確認作業 - 清單頁面  (baba0m211c.jsp) 按下 確認
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 檢核作業 - 檢核確認作業 - 清單頁面  CheckJobAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        CheckJobForm detailForm = (CheckJobForm) form;

        try {
        	
        	// 取得頁面資料
        	List<CheckJobCase> caseList = CaseSessionHelper.getCheckJobCaseList(request);
        	
        	// 頁面checkBox選取的資料位置
        	String[] checkBoxIndex = detailForm.getConfirmMkOfConfirm().split(",");
        	
        	// 目前需求為單筆 如為多筆勾選 需更改
        	// 不是必填 如無勾選 ConfirmMk為空值
        	if(checkBoxIndex[0].isEmpty()){
        		for(CheckJobCase caseData : caseList){
        			caseData.setConfirmMk("");
        		}
        	}else{
        	   for(int i = 0; i < checkBoxIndex.length; i++){
         	    	caseList.get(Integer.parseInt(checkBoxIndex[i])).setConfirmMk("Y");
         	   }
        	}

        	bjService.updateBarvconfirmData(caseList);
        	
        	log.debug("批次處理 - 檢核作業 - 檢核確認作業 - 清單頁面  CheckJobAction.doConfirm() 完成 ... ");
        	saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
        	return mapping.findForward(ConstantKey.FORWARD_QUERY_LIST);
    		
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CheckJobAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }
    
    
    /**
     * 批次處理 - 檢核作業 - 檢核確認作業 - 清單頁面  (baba0m211c.jsp) 按下 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 檢核作業 - 檢核確認作業 - 返回 CheckJobAction.doBack() 開始 ... ");
        
        CheckJobForm detailForm = (CheckJobForm) form;
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        detailForm.setPayCode("");
        detailForm.setIssuYm("");
        detailForm.setConfirmMkOfConfirm("");

        CaseSessionHelper.removeCheckJobCaseList(request);

        log.debug("執行批次處理 - 檢核作業 - 檢核確認作業 - 返回 CheckJobAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }
   
   
}
