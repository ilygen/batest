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
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.maint.cases.AdviceMaintCase;
import tw.gov.bli.ba.maint.cases.BaletterCodeCase;
import tw.gov.bli.ba.maint.forms.AdviceMaintDetailForm;
import tw.gov.bli.ba.maint.forms.AdviceMaintQueryForm;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 核定通知書維護作業 - 查詢頁面 (bapa0x021a.jsp)
 * 
 * @author Swim
 */
public class AdviceMaintDetailAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(AdviceMaintDetailAction.class);

    private static final String FORWARD_ADD_DATA_EXPLAIN = "addDataExplain";
    private static final String FORWARD_MODIFY_DATA_EXPLAIN = "modifyDataExplain";
    private static final String ADD_CODE_PAGE = "addCodePage";
    
    private MaintService maintService;

    /**
     * 維護作業 - 核定通知書維護作業 - 新增頁面 (bapa0x021a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面 AdviceMaintDetailAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintDetailForm detailForm = (AdviceMaintDetailForm) form;

        try {
            // 檢查是否有重複資料
            List<AdviceMaintCase> checkList = maintService.getAdviceMaintQueryCaseBy(detailForm.getPayCode(), detailForm.getAuthTyp());

            if (checkList.size() > 0) {
                // 設定該筆資料已存在訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultToExistMessage());

                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            if (detailForm != null) {
                // 取得存放說明的List
                List<AdviceMaintCase> banotifyList = (List<AdviceMaintCase>) CaseSessionHelper.getAdviceMaintDataContCase(request);

                AdviceMaintCase detailData = new AdviceMaintCase();
                BeanUtility.copyProperties(detailData, detailForm);

                // 存檔
                maintService.saveAdviceMaintData(detailData, banotifyList, userData);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面  AdviceMaintDetailAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintDetailAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 核定通知書維護作業 - 修改頁面 (bapa0x023c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 修改頁面 AdviceMaintDetailAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintDetailForm detailForm = (AdviceMaintDetailForm) form;

        try {
            if (detailForm != null) {
                // 取得存放說明的List
                List<AdviceMaintCase> banotifyList = (List<AdviceMaintCase>) CaseSessionHelper.getAdviceMaintDataContCase(request);

                AdviceMaintCase detailData = new AdviceMaintCase();
                BeanUtility.copyProperties(detailData, detailForm);

                // 存檔
                maintService.updateAdviceMaintData(detailData, banotifyList, userData);

                // 更新清單資料
                AdviceMaintQueryForm queryForm = (AdviceMaintQueryForm) FormSessionHelper.getAdviceMaintQueryForm(request);
                List<AdviceMaintCase> applyList = maintService.getAdviceMaintQueryCaseBy(queryForm.getPayCode(), queryForm.getAuthTyp());
                CaseSessionHelper.setAdviceMaintQueryCase(applyList, request);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 維護作業 - 編審註記維護作業 - 修改頁面  AdviceMaintDetailAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintDetailAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 維護作業 - 核定通知書維護作業 - 新增頁面 (bapa0x021a.jsp) <br>
     * 按下「新增說明」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doAddDataCount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面 AdviceMaintDetailAction.doAddDataCount() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintDetailForm detailForm = (AdviceMaintDetailForm) form;

        try {
            // 取得存放說明的List
            List<AdviceMaintCase> banotifyList = (List<AdviceMaintCase>) CaseSessionHelper.getAdviceMaintDataContCase(request);

            AdviceMaintCase banotify = new AdviceMaintCase();

            banotify.setDataCont(maintService.removeTag(detailForm.getDataContExplain()));
            banotify.setCrtTime(detailForm.getCrtTime());
            banotify.setCrtUser(detailForm.getCrtUser());
            
            // 畫面說明欄位清空
            detailForm.setDataContExplain("");

            banotifyList.add(banotify);

            CaseSessionHelper.setAdviceMaintDataContCase(banotifyList, request);

            log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面  AdviceMaintDetailAction.doAddDataCount() 完成 ... ");

            if (detailForm.getType().equals("add"))
                return mapping.findForward(FORWARD_ADD_DATA_EXPLAIN);
            else
                return mapping.findForward(FORWARD_MODIFY_DATA_EXPLAIN);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintDetailAction.doAddDataCount() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 核定通知書維護作業 - 新增頁面 (bapa0x021a.jsp) <br>
     * 按下「修改說明」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doModifyDataCount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 ApplyDataSinglePayModifyAction.doModifyDataCount() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        String rowId = StringUtils.defaultString(request.getParameter("rowId"));
        String type = StringUtils.defaultString(request.getParameter("type"));

        AdviceMaintDetailForm detailForm = (AdviceMaintDetailForm) form;

        try {
            // 取得存放說明的List
            List<AdviceMaintCase> banotifyList = (List<AdviceMaintCase>) CaseSessionHelper.getAdviceMaintDataContCase(request);

            // 複製該筆資料顯示在畫面上
            detailForm.setDataContExplain(((AdviceMaintCase) banotifyList.get(Integer.parseInt(rowId) - 1)).getDataCont());
            detailForm.setCrtTime(((AdviceMaintCase) banotifyList.get(Integer.parseInt(rowId) - 1)).getCrtTime());
            detailForm.setCrtUser(((AdviceMaintCase) banotifyList.get(Integer.parseInt(rowId) - 1)).getCrtUser());

            // 自List移除該筆資料
            banotifyList.remove(Integer.parseInt(rowId) - 1);

            CaseSessionHelper.setAdviceMaintDataContCase(banotifyList, request);

            log.debug("執行 ApplyDataSinglePayModifyAction.doModifyDataCount() 成功 ... ");

            if (type.equals("add"))
                return mapping.findForward(FORWARD_ADD_DATA_EXPLAIN);
            else
                return mapping.findForward(FORWARD_MODIFY_DATA_EXPLAIN);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintDetailAction.doModifyDataCount() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 核定通知書維護作業 - 新增頁面 (bapa0x021a.jsp) <br>
     * 按下「刪除說明」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDeleteDataCount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 ApplyDataSinglePayModifyAction.doDeleteDataCount() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        String rowId = StringUtils.defaultString(request.getParameter("rowId"));
        String type = StringUtils.defaultString(request.getParameter("type"));

        AdviceMaintDetailForm detailForm = (AdviceMaintDetailForm) form;

        try {
            // 取得存放說明的List
            List<AdviceMaintCase> banotifyList = (List<AdviceMaintCase>) CaseSessionHelper.getAdviceMaintDataContCase(request);

            // 移除該筆資料
            banotifyList.remove(Integer.parseInt(rowId) - 1);

            CaseSessionHelper.setAdviceMaintDataContCase(banotifyList, request);

            log.debug("執行 ApplyDataSinglePayModifyAction.doDeleteDataCount() 成功 ... ");

            if (type.equals("add"))
                return mapping.findForward(FORWARD_ADD_DATA_EXPLAIN);
            else
                return mapping.findForward(FORWARD_MODIFY_DATA_EXPLAIN);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintDetailAction.doDeleteDataCount() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 維護作業 - 核定通知書維護作業 - 新增頁面 (bapa0x021a.jsp) <br>
     * 按下「插入代碼」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doAddA(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面  AdviceMaintDetailAction.doAddA() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintDetailForm detailForm = (AdviceMaintDetailForm) form;

        try {
        	
        	//取得 核定函代碼參數檔 資料
        	List<BaletterCodeCase> baletterCodeCaseList = maintService.selectBalettercodeDataBy();
        	
        	detailForm.setAddMode("A");

        	FormSessionHelper.setAdviceMaintDetailForm(detailForm, request);
        	CaseSessionHelper.setBaletterCodeCaseList(baletterCodeCaseList, request);
        	
            log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面  AdviceMaintDetailAction.doAddA() 完成 ... ");

            return mapping.findForward(ADD_CODE_PAGE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintDetailAction.doAdd() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 維護作業 - 核定通知書維護作業 - 新增頁面 (bapa0x021a.jsp) <br>
     * 按下「插入代碼」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doAddU(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面  AdviceMaintDetailAction.doAddU() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintDetailForm detailForm = (AdviceMaintDetailForm) form;

        try {
        	
        	//取得 核定函代碼參數檔 資料
        	List<BaletterCodeCase> baletterCodeCaseList = maintService.selectBalettercodeDataBy();
        	
        	detailForm.setAddMode("U");

        	FormSessionHelper.setAdviceMaintDetailForm(detailForm, request);
        	CaseSessionHelper.setBaletterCodeCaseList(baletterCodeCaseList, request);
        	
            log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面  AdviceMaintDetailAction.doAddU() 完成 ... ");

            return mapping.findForward(ADD_CODE_PAGE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintDetailAction.doAdd() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 維護作業 - 核定通知書維護作業 - 插入代碼頁面 (bapa0x024c.jsp) <br>
     * 按下「確認」的動作 從新增進入
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doAddCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面 AdviceMaintDetailAction.doAddCode() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintDetailForm detailForm = (AdviceMaintDetailForm) form;

        try {

        	if(detailForm.getChooseData().equals("1")){
        		//滑鼠位置
            	int cursorPosition = Integer.parseInt(detailForm.getCursorPurposePosition());
        	    detailForm.setDataContPurpose(detailForm.getDataContPurpose().substring(0, cursorPosition)+"＜"+detailForm.getAddCode()+"＞"+detailForm.getDataContPurpose().substring(cursorPosition, detailForm.getDataContPurpose().length()));
        	}else if(detailForm.getChooseData().equals("2")){
        		//滑鼠位置
            	int cursorPosition = Integer.parseInt(detailForm.getCursorExplainPosition());
        		detailForm.setDataContExplain(detailForm.getDataContExplain().substring(0, cursorPosition)+"＜"+detailForm.getAddCode()+"＞"+detailForm.getDataContExplain().substring(cursorPosition, detailForm.getDataContExplain().length()));
        	}
           
            //清除代碼
        	detailForm.setAddCode("");
        	
            log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面  AdviceMaintDetailAction.doAddCode() 完成 ... ");
            
            if(detailForm.getAddMode().equals("A")){
                return mapping.findForward(FORWARD_ADD_DATA_EXPLAIN);
            }else{
            	return mapping.findForward(FORWARD_MODIFY_DATA_EXPLAIN);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintDetailAction.doAddCode() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 核定通知書維護作業 - 新增頁面 (bapa0x021a.jsp) 修改頁面 (bapa0x023c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面 AdviceMaintDetailAction.doBack() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintDetailForm detailForm = (AdviceMaintDetailForm) form;

        try {

            log.debug("執行 維護作業 - 核定通知書維護作業 - 新增頁面  AdviceMaintDetailAction.doBack() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintDetailAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }
}
