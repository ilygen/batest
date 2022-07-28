package tw.gov.bli.ba.receipt.actions;

import java.math.BigDecimal;
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

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptCase;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptEvtCase;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptFamCase;
import tw.gov.bli.ba.receipt.forms.DisabledAnnuityReceiptForm;
import tw.gov.bli.ba.receipt.helper.CaseSessionHelper;
import tw.gov.bli.ba.receipt.helper.FormSessionHelper;
import tw.gov.bli.ba.services.ReceiptService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.ba.util.StringUtility;

/**
 * 受理作業 - 失能年金給付受理作業 (BAAP0D020A)
 * 
 * @author Rickychi
 */
public class DisabledAnnuityReceiptAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DisabledAnnuityReceiptAction.class);

    // 受理作業 - 失能年金給付受理作業 - 整案新增作業確認成功
    private static final String FORWARD_SAVE_ALL_SUCCESS = "saveAllSuccess";
    // 受理作業 - 失能年金給付受理作業 - 整案新增作業確認成功 國併勞
    private static final String FORWARD_SAVE_ALL_SUCCESS_FOR_36 = "saveAllSuccessFor36";
    // 受理作業 - 失能年金給付受理作業 - 整案新增作業確認失敗
    private static final String FORWARD_SAVE_ALL_FAIL = "saveAllFail";
    // 受理作業 - 失能年金給付受理作業 - 整案新增作業確認失敗 國併勞
    private static final String FORWARD_SAVE_ALL_FAIL_FOR_36 = "saveAllFailFor36";
    // 受理作業 - 失能年金給付受理作業 - 整案修改作業確認成功
    private static final String FORWARD_UPDATE_ALL_SUCCESS = "updateAllSuccess";
    // 受理作業 - 失能年金給付受理作業 - 整案修改作業確認失敗
    private static final String FORWARD_UPDATE_ALL_FAIL = "updateAllFail";
    // 受理作業 - 失能年金給付受理作業 - 整案刪除作業確認成功
    private static final String FORWARD_DELETE_ALL_SUCCESS = "deleteAllSuccess";
    // 受理作業 - 失能年金給付受理作業 - 整案刪除作業確認失敗
    private static final String FORWARD_DELETE_ALL_FAIL = "deleteAllFail";
    // 受理作業 - 失能年金給付受理作業 - 可修改之受理作業清單頁面
    private static final String FORWARD_RECEIPT_MODIFY_LIST = "receiptModifyList";
    // 受理作業 - 失能年金給付受理作業 - 可修改之受理作業詳細資料頁面
    private static final String FORWARD_RECEIPT_MODIFY_DETAIL = "receiptModifyDetail";

    // 案件操作模式 - 新增
    private static final String APP_ACTION_TYP_INSERT = "insertMode";
    // 案件操作模式 - 修改
    private static final String APP_ACTION_TYP_UPDATE = "updateMode";
    // 眷屬資料部分操作模式 - 新增遺屬
    private static final String FAM_DATA_OPTION_INSERT_MODE = "insertMode";
    // 眷屬資料部分操作模式 - 修改遺屬
    private static final String FAM_DATA_OPTION_UPDATE_MODE = "updateMode";
    
    private static final String SUCCESSFOR36 = "successFor36";

    private ReceiptService receiptService;
    private SelectOptionService selectOptionService;

    /**
     * 受理作業 - 失能年金給付受理作業 - 登錄新增作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄新增作業 DisabledAnnuityReceiptAction.prepareInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;
        try {
            // 取得 遺屬眷屬暫存檔(BAFAMILYTEMP) 暫存檔資料列編號(Sequence.BAFAMILYTEMPID)
            BigDecimal bafamilytempId = receiptService.getNewbafamilytempId();

            // 取得國籍清單
            request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());

            // 眷屬資料清單
            List<DisabledAnnuityReceiptFamCase> famDataList = new ArrayList<DisabledAnnuityReceiptFamCase>();

            // 移除頁面輸入資料
            FormSessionHelper.removeDisabledAnnuityReceiptForm(request);

            // 將資料存入Session
            CaseSessionHelper.setDisabledAnnuityReceiptBafamilytempId(bafamilytempId, request);
            CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);

            log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄新增作業 DisabledAnnuityReceiptAction.prepareInsert() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledAnnuityReceiptAction.prepareInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }
    
    /**
     * 受理作業 - 失能年金給付受理作業 - 國保受理編號 登錄新增作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareInsert36Data(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄國保受理新增作業 DisabledAnnuityReceiptAction.prepareModify36Data() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;
        //紀錄頁面checkbox是否勾選
        iform.setOrigSFlag36(iform.getsFlag36());
        
        try {
        	 String apNo = "";
             if (!("").equals(iform.getApNo1For36().trim()) && !("").equals(iform.getApNo2For36().trim()) && !("").equals(iform.getApNo3For36().trim()) && !("").equals(iform.getApNo4For36().trim())) {
                 apNo = iform.getApNoStrFor36();
             }
             
             // 查詢資料
             List<DisabledAnnuityReceiptEvtCase> dataList = receiptService.selectNbappbaseDataBy(apNo);

             // 受理案件資料筆數<=0時，於下方訊息區顯示訊息(MSG：W0040無查詢資料)。
             if (dataList.size() <= 0) {
                 // 設定查無資料訊息
             	 // 如查無資料 checkbox值清除
             	 iform.setsFlag36("");
                 saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                 log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄修改查詢作業 DisabledAnnuityReceiptAction.prepareModify() 完成 ... ");
                 return mapping.findForward(ConstantKey.FORWARD_FAIL);
                 
             }else{
            	 
            	 DisabledAnnuityReceiptEvtCase evtCase = dataList.get(0);
                 // 依畫面輸入欄位條件轉換 事故者資料欄位
                 evtCase = receiptService.transDateForDisabledEvtFor36(evtCase);
        	
                 // 取得 遺屬眷屬暫存檔(BAFAMILYTEMP) 暫存檔資料列編號(Sequence.BAFAMILYTEMPID)
                 BigDecimal bafamilytempId = receiptService.getNewbafamilytempId();

                 // 取得國籍清單
                 request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());

                 // 眷屬資料清單
                 List<DisabledAnnuityReceiptFamCase> famDataList = new ArrayList<DisabledAnnuityReceiptFamCase>();

                 // 將事故者資料帶入畫面
                 DisabledAnnuityReceiptForm modifyForm = new DisabledAnnuityReceiptForm();
                 modifyForm.setsFlag36(iform.getsFlag36());
                 BeanUtility.copyProperties(modifyForm, evtCase);

                 // 將資料存入Session
                 FormSessionHelper.setDisabledAnnuityReceiptForm(modifyForm, request);
                 CaseSessionHelper.setDisabledAnnuityReceiptBafamilytempId(bafamilytempId, request);
                 CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
                 CaseSessionHelper.setDisabledAnnuityReceiptEvtCase(evtCase, request);

                 log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄國保受理新增作業 DisabledAnnuityReceiptAction.prepareModify36Data() 完成 ... ");

                 return mapping.findForward(SUCCESSFOR36);
             }
        }
        catch (Exception e) {
        	iform.setsFlag36("");
            // 設定查詢失敗訊息
            log.error("DisabledAnnuityReceiptAction.prepareModify36Data() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 登錄修改查詢作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareModify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄修改查詢作業 DisabledAnnuityReceiptAction.prepareModify() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;
        //紀錄頁面checkbox是否勾選
        iform.setOrigSFlag36(iform.getsFlag36());

        try {
            String apNo = "";
            if (!("").equals(iform.getApNo1().trim()) && !("").equals(iform.getApNo2().trim()) && !("").equals(iform.getApNo3().trim()) && !("").equals(iform.getApNo4().trim())) {
                apNo = iform.getApNoStr();
            }

            // 查詢資料
            List<DisabledAnnuityReceiptCase> dataList = receiptService.getBaappbaseDataListForDisabled(iform.getEvtIdnNo(), apNo);

            // 受理案件資料筆數<=0時，於下方訊息區顯示訊息(MSG：W0040無查詢資料)。
            if (dataList.size() <= 0) {
                // 設定查無資料訊息
            	//如查無資料 checkbox值清除
            	iform.setsFlag36("");
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄修改查詢作業 DisabledAnnuityReceiptAction.prepareModify() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_FAIL);
            }
            // 受理案件資料筆數=1時，查詢該筆受理案件明細資料，將系統畫面導至 登錄修改作業(BAAP0D023C)頁面
            else if (dataList.size() == 1) {
                DisabledAnnuityReceiptCase obj = (DisabledAnnuityReceiptCase) dataList.get(0);
                // 取得事故者資料
                DisabledAnnuityReceiptEvtCase evtCase = receiptService.getBaappbaseDetailForDisabled(obj.getBaappbaseId());
                // 轉換日期格式
                evtCase = receiptService.transDateForDisabledEvt(evtCase);
                // 將事故者資料帶入畫面
                DisabledAnnuityReceiptForm modifyForm = new DisabledAnnuityReceiptForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, evtCase, ConstantKey.OLD_FIELD_PREFIX);

                // 取得國籍清單
                request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                // 取得眷屬資料
                List<DisabledAnnuityReceiptFamCase> famDataList = receiptService.getBafamilyDataForDiasbled(evtCase.getBaappbaseId(), evtCase.getApNo());

                // 將查詢條件/查詢結果存入session
                DisabledAnnuityReceiptForm condForm = new DisabledAnnuityReceiptForm();
                BeanUtility.copyProperties(condForm, iform);
                condForm.setApNo(apNo);
                FormSessionHelper.setDisabledAnnuityReceiptQryCondForm(condForm, request);
                FormSessionHelper.setDisabledAnnuityReceiptForm(modifyForm, request);
                CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);

                log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄修改查詢作業 DisabledAnnuityReceiptAction.prepareModify() 完成 ... ");
                return mapping.findForward(FORWARD_RECEIPT_MODIFY_DETAIL);
            }
            // 受理案件資料筆數>1時，將系統畫面導至 登錄修改查詢作業(BAAP0D022C)頁面
            else {
                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setDisabledAnnuityReceiptList(dataList, request);

                // 將查詢條件存入form
                DisabledAnnuityReceiptForm condForm = new DisabledAnnuityReceiptForm();
                BeanUtility.copyProperties(condForm, iform);
                condForm.setApNo(iform.getApNoStr());
                FormSessionHelper.setDisabledAnnuityReceiptQryCondForm(condForm, request);

                log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄修改查詢作業 DisabledAnnuityReceiptAction.prepareModify() 完成 ... ");
                return mapping.findForward(FORWARD_RECEIPT_MODIFY_LIST);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledAnnuityReceiptAction.prepareModify() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 登錄修改作業 (BAAP0D022C)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareModifyDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄修改作業 DisabledAnnuityReceiptAction.prepareModifyDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = ConstantKey.FORWARD_QUERY_FAIL;
        try {
            DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;
            // 取得事故者資料
            DisabledAnnuityReceiptEvtCase evtCase = receiptService.getBaappbaseDetailForDisabled(iform.getBaappbaseId());

            if (evtCase == null) {
                // 設定查詢失敗訊息
                saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            }
            else {
                // 轉換日期格式
                evtCase = receiptService.transDateForDisabledEvt(evtCase);
                // 取得國籍清單
                request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                // 取得眷屬資料
                List<DisabledAnnuityReceiptFamCase> famDataList = receiptService.getBafamilyDataForDiasbled(evtCase.getBaappbaseId(), evtCase.getApNo());

                // 將欲修改的資料帶到 Form 中, 並將 Form 存到 Request Scope
                DisabledAnnuityReceiptForm modifyForm = new DisabledAnnuityReceiptForm();
                BeanUtility.copyProperties(modifyForm, evtCase);
                FormSessionHelper.setDisabledAnnuityReceiptForm(modifyForm, request);
                CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);

                forward = ConstantKey.FORWARD_QUERY_SUCCESS;
            }
            log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄修改作業 DisabledAnnuityReceiptAction.prepareModifyDetail() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledAnnuityReceiptAction.prepareModifyDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 新增作業確認存檔
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 新增作業確認存檔 DisabledAnnuityReceiptAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = FORWARD_SAVE_ALL_FAIL;
        try {
            DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;
            String apNo = iform.getApNoStr();
            // 當受理編號未鍵入時，從 BAS.BAAPNOK3(SEQUENCE) 取得受理編號
 			if (StringUtils.equals(apNo, "K")) {
 				apNo = "K3" + StringUtility.chtLeftPad(receiptService.getSequenceApNoK3(), 10, "0");
 			}


            // 手動輸入之受理編號不可為 K00000000000
            if (StringUtils.equalsIgnoreCase(apNo, "K00000000000")) {
                // 設定訊息: 受理編號編碼規則錯誤
                saveMessages(session, DatabaseMessageHelper.getApNoErrorMessage());
            }
            else {
                // 檢查受理編號是否存在
                if (receiptService.checkApNoExist(apNo)) {
                    // 設定訊息:新增失敗，此受理號碼已存在！
                    saveMessages(session, DatabaseMessageHelper.getApNoExistMessage());
                }
                else {
                    DisabledAnnuityReceiptEvtCase evtCase = new DisabledAnnuityReceiptEvtCase();
                    BeanUtility.copyProperties(evtCase, iform);
                    // 依畫面輸入欄位條件轉換 事故者資料欄位
                    evtCase.setApNo(apNo);
                    evtCase = receiptService.transDisabledEvtInputData(evtCase);
                    // 取得眷屬暫存檔資料序號
                    BigDecimal bafamilytempId = CaseSessionHelper.getDisabledAnnuityReceiptBafamilytempId(request);

                    // 新增給付主檔、給付延伸主檔、眷屬檔資料
                    receiptService.insertDataForDisabled(userData, evtCase, bafamilytempId);

                    // 設定新增成功訊息
                    saveMessages(session, DatabaseMessageHelper.getReceiptSaveSuccessMessage(evtCase.getApNoStrDisplay()));
                    forward = FORWARD_SAVE_ALL_SUCCESS;

                    // 重新取得頁面資料
                    // [
                    // 清除所有session資料
                    FormSessionHelper.removeDisabledAnnuityReceiptForm(request);
                    FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
                    FormSessionHelper.removeDisabledAnnuityReceiptQryCondForm(request);
                    FormSessionHelper.removeDisabledAnnuityReceiptQueryForm(request);
                    CaseSessionHelper.removeAllDisabledAnnuityReceiptCase(request);
                    // 取得 遺屬眷屬暫存檔(BAFAMILYTEMP) 暫存檔資料列編號(Sequence.BAFAMILYTEMPID)
                    bafamilytempId = receiptService.getNewbafamilytempId();
                    // 取得國籍清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 眷屬資料清單
                    List<DisabledAnnuityReceiptFamCase> famDataList = new ArrayList<DisabledAnnuityReceiptFamCase>();
                    // 將資料存入Session
                    CaseSessionHelper.setDisabledAnnuityReceiptBafamilytempId(bafamilytempId, request);
                    CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
                    // ]
                }
            }

            log.debug("執行 受理作業 - 失能年金給付受理作業 - 新增作業確認存檔 DisabledAnnuityReceiptAction.doInsert() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定新增失敗訊息
            log.error("DisabledAnnuityReceiptAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(FORWARD_SAVE_ALL_FAIL);
        }
    }
    
    /**
     * 受理作業 - 失能年金給付受理作業 - 新增作業確認存檔
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert36Data(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 新增作業確認存檔  國併勞 DisabledAnnuityReceiptAction.doInsert36Data() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = FORWARD_SAVE_ALL_FAIL_FOR_36;
        try {
            DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;
            String apNo = iform.getApNoStr();

            // 手動輸入之受理編號不可為 K00000000000
            if (StringUtils.equalsIgnoreCase(apNo, "K00000000000")) {
                // 設定訊息: 受理編號編碼規則錯誤
                saveMessages(session, DatabaseMessageHelper.getApNoErrorMessage());
            }
            else {
                // 檢查受理編號是否存在
                if (receiptService.checkApNoExist(apNo)) {
                    // 設定訊息:新增失敗，此受理號碼已存在！
                    saveMessages(session, DatabaseMessageHelper.getApNoExistMessage());
                }
                else {
                	//國保案件資料使用
                	DisabledAnnuityReceiptEvtCase evtCase = CaseSessionHelper.getDisabledAnnuityReceiptEvtCase(request);
                	
                    // 依畫面輸入欄位條件轉換 事故者資料欄位
                    evtCase = receiptService.transDisabledEvtInputDataFor36(evtCase);
                    // 取得眷屬暫存檔資料序號
                    BigDecimal bafamilytempId = CaseSessionHelper.getDisabledAnnuityReceiptBafamilytempId(request);

                    // 新增給付主檔、給付延伸主檔、眷屬檔資料
                    receiptService.insertDataForDisabled36Data(userData, evtCase , bafamilytempId);

                    // 設定新增成功訊息
                    saveMessages(session, DatabaseMessageHelper.getReceiptSaveSuccessMessage(evtCase.getApNoStrDisplay()));
                    forward = FORWARD_SAVE_ALL_SUCCESS_FOR_36;

                    // 重新取得頁面資料
                    // [
                    // 清除所有session資料
                    FormSessionHelper.removeDisabledAnnuityReceiptForm(request);
                    FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
                    FormSessionHelper.removeDisabledAnnuityReceiptQryCondForm(request);
                    FormSessionHelper.removeDisabledAnnuityReceiptQueryForm(request);
                    CaseSessionHelper.removeAllDisabledAnnuityReceiptCase(request);
                    // 取得 遺屬眷屬暫存檔(BAFAMILYTEMP) 暫存檔資料列編號(Sequence.BAFAMILYTEMPID)
                    bafamilytempId = receiptService.getNewbafamilytempId();
                    // 取得國籍清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 眷屬資料清單
                    List<DisabledAnnuityReceiptFamCase> famDataList = new ArrayList<DisabledAnnuityReceiptFamCase>();
                    // 將資料存入Session
                    CaseSessionHelper.setDisabledAnnuityReceiptBafamilytempId(bafamilytempId, request);
                    CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
                    // ]
                }
            }

            log.debug("執行 受理作業 - 失能年金給付受理作業 - 新增作業確認存檔 國併勞 DisabledAnnuityReceiptAction.doInsert36Data() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定新增失敗訊息
            log.error("DisabledAnnuityReceiptAction.doInsert36Data() 國併勞 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(FORWARD_SAVE_ALL_FAIL_FOR_36);
        }
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 登錄修改作業存檔
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 登錄修改作業存檔 DisabledAnnuityReceiptAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;
        String forward = FORWARD_UPDATE_ALL_FAIL;
        try {
            DisabledAnnuityReceiptEvtCase evtCase = new DisabledAnnuityReceiptEvtCase();
            BeanUtility.copyProperties(evtCase, iform);
            // 依畫面輸入欄位條件轉換 事故者資料欄位
            evtCase = receiptService.transDisabledEvtInputData(evtCase);

            // 取得需記錄異動LOG的欄位資料
            // 為不影響前端頁面顯示, 故須複製一份 Form
            DisabledAnnuityReceiptForm tempForm = new DisabledAnnuityReceiptForm();
            BeanUtility.copyProperties(tempForm, form);
            evtCase.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_RECEIPT_DISABLED_FIELD_RESOURCE_PREFIX));

            // 修改給付主檔、給付延伸主檔
            receiptService.updateDataForDisabled(userData, evtCase);

            // 設定修改成功訊息
            saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(iform.getApNoStrDisplay()));

            // 取得 查詢條件
            DisabledAnnuityReceiptForm condForm = FormSessionHelper.getDisabledAnnuityReceiptQryCondForm(request);
            List<DisabledAnnuityReceiptCase> dataList = receiptService.getBaappbaseDataListForDisabled(condForm.getEvtIdnNo(), condForm.getApNoStr());

            // 受理案件資料筆數<=1時，將系統畫面導至『失能年金給付受理作業(BAAP0D020A)』
            if (dataList.size() <= 1) {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeDisabledAnnuityReceiptForm(request);
                FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
                FormSessionHelper.removeDisabledAnnuityReceiptQryCondForm(request);
                FormSessionHelper.removeDisabledAnnuityReceiptQueryForm(request);
                CaseSessionHelper.removeAllDisabledAnnuityReceiptCase(request);
                forward = FORWARD_UPDATE_ALL_SUCCESS;
            }
            // 受理案件資料筆數>1時，將系統畫面導至『登錄修改查詢作業(BAAP0D022C)頁面』
            else {
                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setDisabledAnnuityReceiptList(dataList, request);
                forward = FORWARD_RECEIPT_MODIFY_LIST;
            }
            log.debug("執行 受理作業 - 失能年金給付受理作業 - 刪除作業 DisabledAnnuityReceiptAction.doDelete() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("DisabledAnnuityReceiptAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 刪除作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 刪除作業 DisabledAnnuityReceiptAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;
        String forward = FORWARD_DELETE_ALL_FAIL;
        try {
            // 根據 baappbaseId 刪除給付主檔、給付延伸主檔、眷屬檔資料
            receiptService.deleteDataForDisabled(userData, iform.getBaappbaseId());

            // 設定刪除成功訊息
            saveMessages(session, DatabaseMessageHelper.getReceiptDeleteSuccessMessage(iform.getApNoStrDisplay()));

            // 取得 查詢條件
            DisabledAnnuityReceiptForm condForm = FormSessionHelper.getDisabledAnnuityReceiptQryCondForm(request);
            List<DisabledAnnuityReceiptCase> dataList = receiptService.getBaappbaseDataListForDisabled(condForm.getEvtIdnNo(), condForm.getApNoStr());

            // 受理案件資料筆數<=1時，將系統畫面導至『失能年金給付受理作業(BAAP0D020A)』
            if (dataList.size() <= 1) {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeDisabledAnnuityReceiptForm(request);
                FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
                FormSessionHelper.removeDisabledAnnuityReceiptQryCondForm(request);
                FormSessionHelper.removeDisabledAnnuityReceiptQueryForm(request);
                CaseSessionHelper.removeAllDisabledAnnuityReceiptCase(request);
                forward = FORWARD_DELETE_ALL_SUCCESS;
            }
            // 受理案件資料筆數>1時，將系統畫面導至『登錄修改查詢作業(BAAP0D022C)頁面』
            else {
                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setDisabledAnnuityReceiptList(dataList, request);
                forward = FORWARD_RECEIPT_MODIFY_LIST;
            }
            log.debug("執行 受理作業 - 失能年金給付受理作業 - 刪除作業 DisabledAnnuityReceiptAction.doDelete() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("DisabledAnnuityReceiptAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(FORWARD_DELETE_ALL_FAIL);
        }
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 新增眷屬作業存檔
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsertFamData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 新增眷屬作業存檔 DisabledAnnuityReceiptAction.doInsertFamData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = ConstantKey.FORWARD_SAVE_FAIL;
        try {
            DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;
            DisabledAnnuityReceiptFamCase caseObj = new DisabledAnnuityReceiptFamCase();
            BeanUtility.copyProperties(caseObj, iform);

            // 保存畫面上已輸入之事故者資料
            DisabledAnnuityReceiptForm evtForm = FormSessionHelper.getDisabledAnnuityReceiptForm(request);
            evtForm = receiptService.keepInputEvtFormDataForDisabled(evtForm, iform);
            FormSessionHelper.setDisabledAnnuityReceiptForm(evtForm, request);

            // 先判斷該案是新增或修改
            if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
                // 檢查輸入之眷屬資料是否已存在(眷屬暫存檔)
                if (receiptService.checkExistFamTempData(caseObj.getBafamilytempId(), caseObj.getFamIdnNo(), DateUtility.changeDateType(caseObj.getFamBrDate()), null, null)) {
                    // 設定 眷屬資料重複訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
                }
                else {
                    // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                    caseObj = receiptService.transDisabledFamInputData(caseObj);
                    receiptService.insertDisabledBafamilytempData(caseObj, userData);
                    // 設定新增成功訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());

                    forward = ConstantKey.FORWARD_SAVE_SUCCESS;

                    // 重新查詢資料
                    // [
                    // 取得國籍清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 眷屬資料清單
                    List<DisabledAnnuityReceiptFamCase> famDataList = receiptService.getDisabledBafamilytempData(caseObj.getBafamilytempId());

                    // 重置頁面輸入資料
                    DisabledAnnuityReceiptForm insertForm = new DisabledAnnuityReceiptForm();
                    BeanUtility.copyProperties(insertForm, iform);
                    insertForm.cleanFamData();
                    insertForm.setFocusLocation("famNationTyp");

                    // 將資料存入Session
                    FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
                    FormSessionHelper.setDisabledAnnuityReceiptFamForm(insertForm, request);
                    CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
                    // ]
                }
            }
            else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
                // 檢查輸入之眷屬資料是否已存在(眷屬檔)
                if (receiptService.checkExistFamData(caseObj.getBaappbaseId(), caseObj.getFamIdnNo(), DateUtility.changeDateType(caseObj.getFamBrDate()), null, null)) {
                    // 設定 眷屬資料重複訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
                }
                else {
                    // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                    caseObj = receiptService.transDisabledFamInputData(caseObj);
                    receiptService.insertDisabledBafamilyData(caseObj, userData);
                    // 設定新增成功訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());

                    forward = ConstantKey.FORWARD_SAVE_SUCCESS;

                    // 重新查詢資料
                    // [
                    // 取得國籍清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 取得眷屬資料
                    List<DisabledAnnuityReceiptFamCase> famDataList = receiptService.getBafamilyDataForDiasbled(caseObj.getBaappbaseId(), caseObj.getApNo());

                    // 重置頁面輸入資料
                    DisabledAnnuityReceiptForm insertForm = new DisabledAnnuityReceiptForm();
                    BeanUtility.copyProperties(insertForm, iform);
                    insertForm.cleanFamData();
                    insertForm.setFocusLocation("famNationTyp");

                    // 將資料存入Session
                    FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
                    FormSessionHelper.setDisabledAnnuityReceiptFamForm(insertForm, request);
                    CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
                    // ]
                }
            }
            log.debug("執行 受理作業 - 失能年金給付受理作業 - 新增眷屬作業存檔 DisabledAnnuityReceiptAction.doInsertFamData() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定新增失敗訊息
            log.error("DisabledAnnuityReceiptAction.doInsertFamData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 修改眷屬作業存檔
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdateFamData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 修改眷屬作業存檔 DisabledAnnuityReceiptAction.doUpdateFamData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = ConstantKey.FORWARD_UPDATE_FAIL;

        try {
            DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;
            DisabledAnnuityReceiptFamCase caseObj = new DisabledAnnuityReceiptFamCase();
            BeanUtility.copyProperties(caseObj, iform);

            // 保存畫面上已輸入之事故者資料
            DisabledAnnuityReceiptForm evtForm = FormSessionHelper.getDisabledAnnuityReceiptForm(request);
            evtForm = receiptService.keepInputEvtFormDataForDisabled(evtForm, iform);
            FormSessionHelper.setDisabledAnnuityReceiptForm(evtForm, request);

            // 先判斷該案是新增或修改
            if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
                // 檢查輸入之眷屬資料是否已存在
                if (receiptService.checkExistFamTempData(caseObj.getBafamilytempId(), caseObj.getFamIdnNo(), DateUtility.changeDateType(caseObj.getFamBrDate()), caseObj.getSeqNo(), "<>")) {
                    // 設定 眷屬資料重複訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
                }
                else {
                    // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                    caseObj = receiptService.transDisabledFamInputData(caseObj);
                    receiptService.updateDisabledBafamilytempData(caseObj,userData);
                    // 設定修改成功訊息
                    saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());

                    forward = ConstantKey.FORWARD_UPDATE_SUCCESS;

                    // 重新查詢資料
                    // [
                    // 取得國籍清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 眷屬資料清單
                    List<DisabledAnnuityReceiptFamCase> famDataList = receiptService.getDisabledBafamilytempData(caseObj.getBafamilytempId());

                    // 重置頁面輸入資料
                    DisabledAnnuityReceiptForm insertForm = new DisabledAnnuityReceiptForm();
                    BeanUtility.copyProperties(insertForm, iform);
                    insertForm.cleanFamData();
                    insertForm.setFocusLocation("famNationTyp");

                    // 將資料存入Session
                    FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
                    FormSessionHelper.setDisabledAnnuityReceiptFamForm(insertForm, request);
                    CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
                    // ]
                }
            }
            else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
                // 檢查輸入之眷屬資料是否已存在(眷屬檔)
                if (receiptService.checkExistFamData(caseObj.getBaappbaseId(), caseObj.getFamIdnNo(), DateUtility.changeDateType(caseObj.getFamBrDate()), caseObj.getSeqNo(), "<>")) {
                    // 設定 眷屬資料重複訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
                }
                else {
                    // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                    caseObj = receiptService.transDisabledFamInputData(caseObj);
                    receiptService.updateDisabledBafamilyData(caseObj, userData);
                    // 設定修改成功訊息
                    saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());

                    forward = ConstantKey.FORWARD_UPDATE_SUCCESS;

                    // 重新查詢資料
                    // [
                    // 取得國籍清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 取得眷屬資料
                    List<DisabledAnnuityReceiptFamCase> famDataList = receiptService.getBafamilyDataForDiasbled(caseObj.getBaappbaseId(), caseObj.getApNo());

                    // 重置頁面輸入資料
                    DisabledAnnuityReceiptForm insertForm = new DisabledAnnuityReceiptForm();
                    BeanUtility.copyProperties(insertForm, iform);
                    insertForm.cleanFamData();
                    insertForm.setFocusLocation("famNationTyp");

                    // 將資料存入Session
                    FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
                    FormSessionHelper.setDisabledAnnuityReceiptFamForm(insertForm, request);
                    CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
                    // ]
                }

            }
            log.debug("執行 受理作業 - 失能年金給付受理作業 - 修改眷屬作業存檔 DisabledAnnuityReceiptAction.doUpdateFamData() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定修改失敗訊息
            log.error("DisabledAnnuityReceiptAction.doUpdateFamData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 刪除眷屬作業存檔
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDeleteFamData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 刪除眷屬作業存檔 DisabledAnnuityReceiptAction.doDeleteFamData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = ConstantKey.FORWARD_DELETE_FAIL;

        try {
            DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;

            // 先判斷該案是新增或修改
            if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
                // 刪除該筆眷屬資料
                receiptService.deleteBafamilytempData(iform.getBafamilytempId(), iform.getSeqNo());

                // 設定刪除成功訊息
                saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());

                // 設定 forward
                forward = ConstantKey.FORWARD_DELETE_SUCCESS;

                // 重新查詢資料
                // [
                // 取得國籍清單
                request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                // 取得眷屬資料
                List<DisabledAnnuityReceiptFamCase> famDataList = receiptService.getDisabledBafamilytempData(iform.getBafamilytempId());

                // 重置頁面輸入資料
                DisabledAnnuityReceiptForm insertForm = new DisabledAnnuityReceiptForm();
                BeanUtility.copyProperties(insertForm, iform);
                insertForm.cleanFamData();
                insertForm.setFocusLocation("famNationTyp");

                // 將資料存入Session
                FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
                FormSessionHelper.setDisabledAnnuityReceiptFamForm(insertForm, request);
                CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
                // ]
            }
            else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
                // 刪除該筆眷屬資料
                receiptService.deleteBafamilyDataByBafamilyId(iform.getBafamilyId(), userData);

                // 設定刪除成功訊息
                saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());
                // 設定 forward
                forward = ConstantKey.FORWARD_DELETE_SUCCESS;

                // 重新查詢資料
                // [
                // 取得國籍清單
                request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                // 眷屬資料清單
                List<DisabledAnnuityReceiptFamCase> famDataList = receiptService.getBafamilyDataForDiasbled(iform.getBaappbaseId(), iform.getApNo());

                // 重置頁面輸入資料
                DisabledAnnuityReceiptForm insertForm = new DisabledAnnuityReceiptForm();
                BeanUtility.copyProperties(insertForm, iform);
                insertForm.cleanFamData();
                insertForm.setFocusLocation("famNationTyp");

                // 將資料存入Session
                FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
                FormSessionHelper.setDisabledAnnuityReceiptFamForm(insertForm, request);
                CaseSessionHelper.setDisabledAnnuityReceiptFamDataList(famDataList, request);
                // ]
            }

            log.debug("執行 受理作業 - 失能年金給付受理作業 - 刪除眷屬作業存檔 DisabledAnnuityReceiptAction.doDeleteBenData() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("DisabledAnnuityReceiptAction.doDeleteBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 返回 DisabledAnnuityReceiptAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removeDisabledAnnuityReceiptForm(request);
        FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
        FormSessionHelper.removeDisabledAnnuityReceiptQryCondForm(request);
        FormSessionHelper.removeDisabledAnnuityReceiptQueryForm(request);
        CaseSessionHelper.removeAllDisabledAnnuityReceiptCase(request);

        log.debug("執行 受理作業 - 失能年金給付受理作業 - 返回 DisabledAnnuityReceiptAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doModifyBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 失能年金給付受理作業 - 返回 DisabledAnnuityReceiptAction.doModifyBack() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledAnnuityReceiptForm iform = (DisabledAnnuityReceiptForm) form;

        try {
            // 取得 查詢條件
            DisabledAnnuityReceiptForm condForm = FormSessionHelper.getDisabledAnnuityReceiptQryCondForm(request);
            List<DisabledAnnuityReceiptCase> dataList = receiptService.getBaappbaseDataListForDisabled(condForm.getEvtIdnNo(), condForm.getApNoStr());

            // 受理案件資料筆數<=1時，將系統畫面導至『失能年金給付受理作業(BAAP0D020A)』
            if (dataList.size() <= 1) {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeDisabledAnnuityReceiptForm(request);
                FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
                FormSessionHelper.removeDisabledAnnuityReceiptQryCondForm(request);
                FormSessionHelper.removeDisabledAnnuityReceiptQueryForm(request);
                CaseSessionHelper.removeAllDisabledAnnuityReceiptCase(request);

                log.debug("執行 受理作業 - 失能年金給付受理作業 - 返回 DisabledAnnuityReceiptAction.doModifyBack() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }
            // 受理案件資料筆數>1時，將系統畫面導至『登錄修改查詢作業(BAAP0D022C)頁面』
            else {
                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setDisabledAnnuityReceiptList(dataList, request);
                log.debug("執行 受理作業 - 失能年金給付受理作業 - 返回 DisabledAnnuityReceiptAction.doModifyBack() 完成 ... ");
                return mapping.findForward(FORWARD_RECEIPT_MODIFY_LIST);
            }
        }
        catch (Exception e) {
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        }
    }

    public void setReceiptService(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
}
