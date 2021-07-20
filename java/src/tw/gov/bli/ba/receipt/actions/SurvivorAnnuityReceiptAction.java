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
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bafamilytemp;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptBenCase;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptCase;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptEvtCase;
import tw.gov.bli.ba.receipt.forms.SurvivorAnnuityReceiptForm;
import tw.gov.bli.ba.receipt.helper.CaseSessionHelper;
import tw.gov.bli.ba.receipt.helper.FormSessionHelper;
import tw.gov.bli.ba.services.ReceiptService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 受理作業 - 遺屬年金給付受理作業 (BAAP0D030A)
 * 
 * @author Rickychi
 */
public class SurvivorAnnuityReceiptAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(SurvivorAnnuityReceiptAction.class);

    // 受理作業 - 遺屬年金給付受理作業 - 整案新增作業確認成功
    private static final String FORWARD_SAVE_ALL_SUCCESS = "saveAllSuccess";
    // 受理作業 - 遺屬年金給付受理作業 - 整案新增作業確認失敗
    private static final String FORWARD_SAVE_ALL_FAIL = "saveAllFail";
    // 受理作業 - 遺屬年金給付受理作業 - 整案修改作業確認成功
    private static final String FORWARD_UPDATE_ALL_SUCCESS = "updateAllSuccess";
    // 受理作業 - 遺屬年金給付受理作業 - 整案修改作業確認失敗
    private static final String FORWARD_UPDATE_ALL_FAIL = "updateAllFail";
    // 受理作業 - 遺屬年金給付受理作業 - 整案刪除作業確認成功
    private static final String FORWARD_DELETE_ALL_SUCCESS = "deleteAllSuccess";
    // 受理作業 - 遺屬年金給付受理作業 - 整案刪除作業確認失敗
    private static final String FORWARD_DELETE_ALL_FAIL = "deleteAllFail";
    // 受理作業 - 遺屬年金給付受理作業 - 可修改之受理作業清單頁面
    private static final String FORWARD_RECEIPT_MODIFY_LIST = "receiptModifyList";
    // 受理作業 - 遺屬年金給付受理作業 - 可修改之受理作業詳細資料頁面
    private static final String FORWARD_RECEIPT_MODIFY_DETAIL = "receiptModifyDetail";

    // 案件操作模式 - 新增
    private static final String APP_ACTION_TYP_INSERT = "insertMode";
    // 案件操作模式 - 修改
    private static final String APP_ACTION_TYP_UPDATE = "updateMode";
    // 遺屬資料部分操作模式 - 新增遺屬
    private static final String BEN_DATA_OPTION_INSERT_MODE = "insertMode";
    // 遺屬資料部分操作模式 - 修改遺屬
    private static final String BEN_DATA_OPTION_UPDATE_MODE = "updateMode";

    private ReceiptService receiptService;
    private SelectOptionService selectOptionService;

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 登錄新增作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 登錄新增作業 SurvivorAnnuityReceiptAction.prepareInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        SurvivorAnnuityReceiptForm iform = (SurvivorAnnuityReceiptForm) form;
        try {
            // 取得 遺屬眷屬暫存檔(BAFAMILYTEMP) 暫存檔資料列編號(Sequence.BAFAMILYTEMPID)
            BigDecimal bafamilytempId = receiptService.getNewbafamilytempId();
            // 取得國籍清單
            request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
            // 取得具名領取清單
            request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, new ArrayList<Baappbase>());
            // 眷屬資料清單
            List<SurvivorAnnuityReceiptBenCase> famDataList = new ArrayList<SurvivorAnnuityReceiptBenCase>();

            CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(new ArrayList<SurvivorAnnuityReceiptBenCase>(), request);
            CaseSessionHelper.setSurvivorAnnuityReceiptBafamilytempId(bafamilytempId, request);

            log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 登錄新增作業 SurvivorAnnuityReceiptAction.prepareInsert() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorAnnuityReceiptAction.prepareInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 登錄修改查詢作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareModify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 登錄修改查詢作業 SurvivorAnnuityReceiptAction.prepareModify() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorAnnuityReceiptForm iform = (SurvivorAnnuityReceiptForm) form;

        try {
            String apNo = "";
            if (!("").equals(iform.getApNo1().trim()) && !("").equals(iform.getApNo2().trim()) && !("").equals(iform.getApNo3().trim()) && !("").equals(iform.getApNo4().trim())) {
                apNo = iform.getApNoStr();
            }
            List<SurvivorAnnuityReceiptCase> dataList = receiptService.getBaappbaseDataListForSurvivor(iform.getEvtIdnNo(), apNo);

            // 受理案件資料筆數<=0時，於下方訊息區顯示訊息(MSG：W0040無查詢資料)。
            if (dataList.size() <= 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 登錄修改查詢作業 SurvivorAnnuityReceiptAction.prepareModify() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_FAIL);
            }
            // 受理案件資料筆數=1時，查詢該筆受理案件明細資料，將系統畫面導至 登錄修改作業(BAAP0D013C)頁面
            else if (dataList.size() == 1) {
                SurvivorAnnuityReceiptCase obj = (SurvivorAnnuityReceiptCase) dataList.get(0);
                // 取得事故者資料
                SurvivorAnnuityReceiptEvtCase detailData = receiptService.getSurvivorEvtDetailData(obj.getBaappbaseId());
                // 轉換日期格式
                detailData = receiptService.transDateForSurvivorEvt(detailData);
                // 取得案件遺屬資料清單
                List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService.getSurvivorBenDataList(detailData.getApNo(), new String[] { "00" }, "in");
                // 已存在遺屬資料之身分證號+出生日期
                // String benIdnBrDate = receiptService.transExistBenIdnBrDate(benDataList);
                // 取得國籍清單
                request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                // 取得具名領取清單
                request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionListForSurvivor(null, detailData.getApNo()));

                // 將欲修改的資料帶到 Form 中, 並將 Form 存到 Request Scope
                SurvivorAnnuityReceiptForm modifyForm = new SurvivorAnnuityReceiptForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
                modifyForm.setEvtBaappbaseId(detailData.getBaappbaseId());
                FormSessionHelper.setSurvivorAnnuityReceiptForm(modifyForm, request);

                // 將查詢條件/查詢結果 存入 session
                SurvivorAnnuityReceiptForm qryCondForm = new SurvivorAnnuityReceiptForm();
                BeanUtility.copyProperties(qryCondForm, iform);
                qryCondForm.setApNo(apNo);
                FormSessionHelper.setSurvivorAnnuityReceiptQryCondForm(qryCondForm, request);
                CaseSessionHelper.setSurvivorAnnuityReceiptEvtData(detailData, request);
                CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);
                // CaseSessionHelper.setSurvivorAnnuityReceiptBenIdnBrDateStr(benIdnBrDate, request);

                log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 登錄修改查詢作業 SurvivorAnnuityReceiptAction.prepareModify() 完成 ... ");
                return mapping.findForward(FORWARD_RECEIPT_MODIFY_DETAIL);
            }
            // 受理案件資料筆數>1時，將系統畫面導至 登錄修改查詢作業(BAAP0D012C)頁面
            else {
                // 將 查詢條件/查詢結果 存到 Request Scope
                SurvivorAnnuityReceiptForm qryCondForm = new SurvivorAnnuityReceiptForm();
                BeanUtility.copyProperties(qryCondForm, iform);
                qryCondForm.setApNo(apNo);
                FormSessionHelper.setSurvivorAnnuityReceiptQryCondForm(qryCondForm, request);
                CaseSessionHelper.setSurvivorAnnuityReceiptList(dataList, request);

                log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 登錄修改查詢作業 SurvivorAnnuityReceiptAction.prepareModify() 完成 ... ");
                return mapping.findForward(FORWARD_RECEIPT_MODIFY_LIST);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorAnnuityReceiptAction.prepareModify() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 受理作業 - 遺屬年金受理作業 - 登錄修改作業 (BAAP0D032C)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareModifyDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 遺屬年金受理作業 - 登錄修改作業 SurvivorAnnuityReceiptAction.prepareModifyDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = ConstantKey.FORWARD_QUERY_FAIL;
        try {
            SurvivorAnnuityReceiptForm iform = (SurvivorAnnuityReceiptForm) form;
            // 取得案件事故者詳細資料
            SurvivorAnnuityReceiptEvtCase evtCase = receiptService.getSurvivorEvtDetailData(iform.getBaappbaseId());

            if (evtCase == null) {
                // 設定查詢失敗訊息
                saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            }
            else {
                // 轉換日期格式
                evtCase = receiptService.transDateForSurvivorEvt(evtCase);

                // 取得遺屬資料清單
                List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService.getSurvivorBenDataList(evtCase.getApNo(), new String[] { "00" }, "in");
                // 已存在遺屬資料之身分證號+出生日期
                // String benIdnBrDate = receiptService.transExistBenIdnBrDate(benDataList);
                // 取得國籍清單
                request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                // 取得具名領取清單
                request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionListForSurvivor(null, evtCase.getApNo()));

                // 將欲修改的資料帶到 Form 中, 並將 Form 存到 Request Scope
                SurvivorAnnuityReceiptForm modifyForm = new SurvivorAnnuityReceiptForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, evtCase, ConstantKey.OLD_FIELD_PREFIX);
                modifyForm.setEvtBaappbaseId(evtCase.getBaappbaseId());
                FormSessionHelper.setSurvivorAnnuityReceiptForm(modifyForm, request);

                // 將查詢條件/查詢結果 存入 session
                CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);
                // CaseSessionHelper.setSurvivorAnnuityReceiptBenIdnBrDateStr(benIdnBrDate, request);

                forward = ConstantKey.FORWARD_QUERY_SUCCESS;
            }
            log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 登錄修改作業 SurvivorAnnuityReceiptAction.prepareModifyDetail() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorAnnuityReceiptAction.prepareModifyDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 新增作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 新增作業 SurvivorAnnuityReceiptAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = FORWARD_SAVE_ALL_FAIL;
        try {
            SurvivorAnnuityReceiptForm iform = (SurvivorAnnuityReceiptForm) form;
            String apNo = iform.getApNoStr();

            // 檢查輸入之事故者資料是否與眷屬資料重複
            if (receiptService.checkExistFamTempData(iform.getBafamilytempId(), iform.getEvtIdnNo(), DateUtility.changeDateType(iform.getEvtBrDate()), null, null)) {
                // 設定 事故者資料與眷屬資料重複訊息
                saveMessages(session, DatabaseMessageHelper.getSaveFailCauseDupEvtFamDataMessage());
            }
            else {
                // 手動輸入之受理編號不可為 K00000000000
                if (StringUtils.equalsIgnoreCase(apNo, "S00000000000")) {
                    // 設定訊息: 受理編號編碼規則錯誤
                    saveMessages(session, DatabaseMessageHelper.getApNoErrorMessage());
                }
                else {
                    // 檢查受理編號是否存在
                    if (StringUtils.isNotBlank(apNo) && receiptService.checkApNoExist(apNo)) {
                        // 設定訊息:新增失敗，此受理號碼已存在！
                        saveMessages(session, DatabaseMessageHelper.getApNoExistMessage());
                    }
                    else {
                        if (StringUtils.isBlank(apNo)) {
                            // 自動取得受理編號
                            apNo = "S2" + StringUtility.chtLeftPad(receiptService.getSequenceSApNo(), 10, "0");
                        }

                        SurvivorAnnuityReceiptEvtCase evtCase = new SurvivorAnnuityReceiptEvtCase();
                        BeanUtility.copyProperties(evtCase, iform);
                        evtCase.setApNo(apNo);

                        // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                        evtCase = receiptService.transSurvivorEvtInputData(evtCase);

                        // 新增遺屬年金受理資料
                        receiptService.insertDataForSurvivor(userData, evtCase, iform.getBafamilytempId());

                        // 設定新增成功訊息
                        saveMessages(session, DatabaseMessageHelper.getReceiptSaveSuccessMessage(evtCase.getApNoStrDisplay()));

                        // 取得 遺屬眷屬暫存檔(BAFAMILYTEMP) 暫存檔資料列編號(Sequence.BAFAMILYTEMPID)
                        BigDecimal bafamilytempId = receiptService.getNewbafamilytempId();
                        // 取得國籍清單
                        request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                        // 取得具名領取清單
                        request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, new ArrayList<Baappbase>());
                        // 眷屬資料清單
                        List<SurvivorAnnuityReceiptBenCase> famDataList = new ArrayList<SurvivorAnnuityReceiptBenCase>();

                        // 移除所有Session中的資料
                        FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
                        FormSessionHelper.removeSurvivorAnnuityReceiptQryCondForm(request);
                        FormSessionHelper.removeSurvivorAnnuityReceiptQryForm(request);
                        CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);
                        // 將資料存入Session
                        CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(new ArrayList<SurvivorAnnuityReceiptBenCase>(), request);
                        CaseSessionHelper.setSurvivorAnnuityReceiptBafamilytempId(bafamilytempId, request);

                        forward = FORWARD_SAVE_ALL_SUCCESS;
                    }
                }
            }
            log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 新增作業 SurvivorAnnuityReceiptAction.doInsert() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定新增失敗訊息
            log.error("SurvivorAnnuityReceiptAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(FORWARD_SAVE_ALL_FAIL);
        }
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 修改作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 修改作業 SurvivorAnnuityReceiptAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = FORWARD_UPDATE_ALL_FAIL;
        try {
            SurvivorAnnuityReceiptForm iform = (SurvivorAnnuityReceiptForm) form;
            SurvivorAnnuityReceiptEvtCase evtCase = new SurvivorAnnuityReceiptEvtCase();
            BeanUtility.copyProperties(evtCase, iform);
            // 檢查輸入之事故者資料是否與眷屬資料重複
            if (receiptService.checkExistBenData(iform.getApNo(), iform.getEvtIdnNo(), DateUtility.changeDateType(iform.getEvtBrDate()), null, null)) {
                // 設定 事故者資料與眷屬資料重複訊息
                saveMessages(session, DatabaseMessageHelper.getSaveFailCauseDupEvtFamDataMessage());
            }
            else {
                // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                evtCase = receiptService.transSurvivorEvtInputData(evtCase);

                // 修改遺屬年金受理資料
                receiptService.updateDataForSurvivor(userData, evtCase);

                // 設定修改成功訊息
                saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(evtCase.getApNoStrDisplay()));

                // 移除所有Session中的資料
                FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
                FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);

                // 取得初始查詢條件
                SurvivorAnnuityReceiptForm qryCondForm = FormSessionHelper.getSurvivorAnnuityReceiptQryCondForm(request);

                // 重新查詢資料, 判斷該導回哪一頁
                List<SurvivorAnnuityReceiptCase> dataList = receiptService.getBaappbaseDataListForSurvivor(qryCondForm.getEvtIdnNo(), qryCondForm.getApNo());

                // 受理案件資料筆數<=1時，如果只有一筆資料,正常情況下該筆資料應為使用者原本欲修改的資料
                // 故將系統畫面導至『老年年金給付受理作業(BAAP0D030A)』
                if (dataList.size() <= 1) {
                    // 移除所有Session中的資料
                    FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
                    FormSessionHelper.removeSurvivorAnnuityReceiptQryCondForm(request);
                    FormSessionHelper.removeSurvivorAnnuityReceiptQryForm(request);
                    FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                    CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);

                    forward = FORWARD_UPDATE_ALL_SUCCESS;
                }
                // 受理案件資料筆數>1時，將系統畫面導至 登錄修改查詢作業(BAAP0D032C)頁面
                else {
                    // 將 查詢結果 存到 Request Scope
                    FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
                    FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                    CaseSessionHelper.setSurvivorAnnuityReceiptList(dataList, request);

                    forward = FORWARD_RECEIPT_MODIFY_LIST;
                }
            }
            log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 修改作業 SurvivorAnnuityReceiptAction.doUpdate() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定修改失敗訊息
            log.error("SurvivorAnnuityReceiptAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(FORWARD_UPDATE_ALL_FAIL);
        }
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 刪除作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 刪除作業 SurvivorAnnuityReceiptAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = FORWARD_DELETE_ALL_SUCCESS;
        try {
            SurvivorAnnuityReceiptForm iform = (SurvivorAnnuityReceiptForm) form;

            // 刪除所選擇資料
            receiptService.deleteAllDataForSurvivor(userData, iform.getApNo());

            // 重新查詢資料, 判斷該導回哪一頁
            SurvivorAnnuityReceiptForm qryCondForm = FormSessionHelper.getSurvivorAnnuityReceiptQryCondForm(request);
            List<SurvivorAnnuityReceiptCase> dataList = receiptService.getBaappbaseDataListForSurvivor(qryCondForm.getEvtIdnNo(), qryCondForm.getApNo());

            // 受理案件資料筆數<=0時,將系統畫面導至『老年年金給付受理作業(BAAP0D030A)』
            if (dataList.size() <= 0) {
                // 移除所有Session中的資料
                FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
                FormSessionHelper.removeSurvivorAnnuityReceiptQryCondForm(request);
                FormSessionHelper.removeSurvivorAnnuityReceiptQryForm(request);
                FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);

                forward = ConstantKey.FORWARD_BACK;
            }
            // 受理案件資料筆數>=1時，將系統畫面導至 登錄修改查詢作業(BAAP0D032C)頁面
            else {
                // 移除所有Session中的資料
                FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
                FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);

                // 將 查詢結果 存到 Request Scope
                CaseSessionHelper.setSurvivorAnnuityReceiptList(dataList, request);
            }

            // 設定刪除成功訊息
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());

            log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 刪除作業 SurvivorAnnuityReceiptAction.doDelete() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("SurvivorAnnuityReceiptAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(FORWARD_DELETE_ALL_FAIL);
        }
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 遺屬新增作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsertBenData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 遺屬新增作業 SurvivorAnnuityReceiptAction.doInsertBenData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = ConstantKey.FORWARD_SAVE_FAIL;
        try {
            SurvivorAnnuityReceiptForm iform = (SurvivorAnnuityReceiptForm) form;
            SurvivorAnnuityReceiptBenCase caseObj = new SurvivorAnnuityReceiptBenCase();
            BeanUtility.copyProperties(caseObj, iform);

            // 保存畫面上已輸入之事故者資料
            SurvivorAnnuityReceiptForm evtForm = FormSessionHelper.getSurvivorAnnuityReceiptForm(request);
            evtForm = receiptService.keepInputEvtFormDataForSurvivor(evtForm, iform);
            FormSessionHelper.setSurvivorAnnuityReceiptForm(evtForm, request);

            // 先判斷該案是新增或修改
            if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
                // 檢查輸入之眷屬資料是否已存在(眷屬暫存檔)
                if (receiptService.checkExistFamTempData(caseObj.getBafamilytempId(), caseObj.getBenIdnNo(), DateUtility.changeDateType(caseObj.getBenBrDate()), null, null)) {
                    // 設定 眷屬資料重複訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
                }
                else {
                    // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                    caseObj = receiptService.transSurvivorBenInputData(caseObj, APP_ACTION_TYP_INSERT);
                    // 新增遺屬暫存檔資料
                    receiptService.insertSurvivorBafamilytempData(caseObj, userData);
                    // 設定新增成功訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
                    forward = ConstantKey.FORWARD_SAVE_SUCCESS;

                    // 重新查詢資料
                    // [
                    // 取得國籍清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 取得具名領取清單
                    request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionListForSurvivorTemp(caseObj.getBafamilytempId(), null));
                    // 眷屬資料清單
                    List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService.getSurvivorBafamilytempDataList(caseObj.getBafamilytempId());

                    // 將資料存入Session
                    FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                    CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

                    SurvivorAnnuityReceiptForm benForm = new SurvivorAnnuityReceiptForm();
                    benForm.setFocusLocation("benNationTyp");
                    FormSessionHelper.setSurvivorAnnuityReceiptBenForm(benForm, request);
                    // ]
                }
            }
            else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
                // 檢查輸入之眷屬資料是否已存在(眷屬暫存檔)
                if (receiptService.checkExistBenData(caseObj.getApNo(), caseObj.getBenIdnNo(), DateUtility.changeDateType(caseObj.getBenBrDate()), null, null)) {
                    // 設定 眷屬資料重複訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
                }
                else {
                    // 取得事故者資料
                    SurvivorAnnuityReceiptEvtCase evtCase = CaseSessionHelper.getSurvivorAnnuityReceiptEvtData(request);

                    // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                    caseObj = receiptService.transSurvivorBenInputData(caseObj, APP_ACTION_TYP_UPDATE);
                    // 新增遺屬暫存檔資料
                    receiptService.insertSurvivorBaappbaseBenData(evtCase, caseObj, userData);
                    // 設定新增成功訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
                    forward = ConstantKey.FORWARD_SAVE_SUCCESS;

                    // 重新查詢資料
                    // [
                    // 取得國籍清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 取得具名領取清單
                    request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionListForSurvivor(null, caseObj.getApNo()));
                    // 眷屬資料清單
                    List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService.getSurvivorBenDataList(caseObj.getApNo(), new String[] { "00" }, "in");

                    // 將資料存入Session
                    FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                    CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

                    SurvivorAnnuityReceiptForm benForm = new SurvivorAnnuityReceiptForm();
                    benForm.setFocusLocation("benNationTyp");
                    FormSessionHelper.setSurvivorAnnuityReceiptBenForm(benForm, request);
                    // ]
                }
            }

            log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 遺屬新增作業 SurvivorAnnuityReceiptAction.doInsertBenData() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定新增失敗訊息
            log.error("SurvivorAnnuityReceiptAction.doInsertBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 遺屬修改作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdateBenData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 遺屬修改作業 SurvivorAnnuityReceiptAction.doUpdateBenData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = ConstantKey.FORWARD_UPDATE_SUCCESS;
        try {
            SurvivorAnnuityReceiptForm iform = (SurvivorAnnuityReceiptForm) form;
            SurvivorAnnuityReceiptBenCase benCase = new SurvivorAnnuityReceiptBenCase();
            BeanUtility.copyProperties(benCase, iform);

            // 保存畫面上已輸入之事故者資料
            SurvivorAnnuityReceiptForm evtForm = FormSessionHelper.getSurvivorAnnuityReceiptForm(request);
            evtForm = receiptService.keepInputEvtFormDataForSurvivor(evtForm, iform);
            FormSessionHelper.setSurvivorAnnuityReceiptForm(evtForm, request);

            // 先判斷該案是新增或修改
            if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
                // 取得具名領取清單
                request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionListForSurvivorTemp(benCase.getBafamilytempId(), benCase.getSeqNo()));

                // 檢查輸入之眷屬資料是否已存在(眷屬暫存檔)
                if (receiptService.checkExistFamTempData(benCase.getBafamilytempId(), benCase.getBenIdnNo(), DateUtility.changeDateType(benCase.getBenBrDate()), benCase.getSeqNo(), "<>")) {
                    // 設定 眷屬資料重複訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
                }
                else {
                    // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                    benCase = receiptService.transSurvivorBenInputData(benCase, APP_ACTION_TYP_INSERT);
                    // 修改遺屬暫存檔資料
                    receiptService.updateSurvivorBafamilytempData(benCase);
                    // 設定修改成功訊息
                    saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
                    forward = ConstantKey.FORWARD_UPDATE_SUCCESS;

                    // 重新查詢資料
                    // [
                    // 取得國籍清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 取得具名領取清單
                    request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionListForSurvivorTemp(benCase.getBafamilytempId(), null));
                    // 眷屬資料清單
                    List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService.getSurvivorBafamilytempDataList(benCase.getBafamilytempId());

                    // 將資料存入Session
                    FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                    CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

                    SurvivorAnnuityReceiptForm benForm = new SurvivorAnnuityReceiptForm();
                    benForm.setFocusLocation("benNationTyp");
                    FormSessionHelper.setSurvivorAnnuityReceiptBenForm(benForm, request);
                    // ]
                }
            }
            else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
                // 取得具名領取清單
                request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionListForSurvivor(benCase.getBaappbaseId(), benCase.getApNo()));

                // 檢查輸入之眷屬資料是否已存在(眷屬暫存檔)
                if (receiptService.checkExistBenData(benCase.getApNo(), benCase.getBenIdnNo(), DateUtility.changeDateType(benCase.getBenBrDate()), benCase.getBaappbaseId(), "<>")) {
                    // 設定 眷屬資料重複訊息
                    saveMessages(session, DatabaseMessageHelper.getSaveFailCauseFamDataExistMessage());
                }
                else {
                    if (StringUtils.isNotBlank(benCase.getBenAppDate())) {
                        benCase.setBenAppDate(DateUtility.changeDateType(benCase.getBenAppDate()));
                    }
                    // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                    benCase = receiptService.transSurvivorBenInputData(benCase, APP_ACTION_TYP_UPDATE);

                    // 取得需記錄異動LOG的欄位資料
                    // 為不影響前端頁面顯示, 故須複製一份 Form
                    // SurvivorAnnuityReceiptForm tempForm = new SurvivorAnnuityReceiptForm();
                    // BeanUtility.copyProperties(tempForm, form);
                    // benCase.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_RECEIPT_SURVIVOR_FIELD_RESOURCE_PREFIX));

                    // 修改遺屬暫存檔資料
                    receiptService.updateSurvivorBaappbaseBenData(benCase, userData);
                    // 設定修改成功訊息
                    saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
                    forward = ConstantKey.FORWARD_UPDATE_SUCCESS;

                    // 重新查詢資料
                    // [
                    // 取得國籍清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 取得具名領取清單
                    request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionListForSurvivor(null, benCase.getApNo()));
                    // 眷屬資料清單
                    List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService.getSurvivorBenDataList(benCase.getApNo(), new String[] { "00" }, "in");

                    // 將資料存入Session
                    FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                    CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

                    SurvivorAnnuityReceiptForm benForm = new SurvivorAnnuityReceiptForm();
                    benForm.setFocusLocation("benNationTyp");
                    FormSessionHelper.setSurvivorAnnuityReceiptBenForm(benForm, request);
                    // ]
                }
            }
            log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 遺屬修改作業 SurvivorAnnuityReceiptAction.doUpdateBenData() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定修改失敗訊息
            log.error("SurvivorAnnuityReceiptAction.doUpdateBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 遺屬刪除作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDeleteBenData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 遺屬刪除作業 SurvivorAnnuityReceiptAction.doDeleteBenData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = ConstantKey.FORWARD_DELETE_SUCCESS;
        try {
            SurvivorAnnuityReceiptForm iform = (SurvivorAnnuityReceiptForm) form;

            // 先判斷該案是新增或修改
            if ((APP_ACTION_TYP_INSERT).equals(iform.getActionTyp())) {
                // 檢查是否為 被具名領取對象
                if (receiptService.getAccSeqNoAmtFromBafamilytemp(iform.getBafamilytempId(), iform.getSeqNo()) > 0) {
                    // 該筆資料為其他資料之具名領取者，不可刪除。

                    saveMessages(session, CustomMessageHelper.getAccDataCantDeleteMessage());
                }
                else {
                    // 刪除該筆眷屬資料
                    String seqNo = iform.getSeqNo();
                    BigDecimal bafamilytempId = iform.getBafamilytempId();
                    List<Bafamilytemp> bafamilyTempList = receiptService.selectDeleteAfterDataBy(iform.getBafamilytempId(), iform.getSeqNo());
                    receiptService.deleteBafamilytempData(iform.getBafamilytempId(), iform.getSeqNo());

                    Bafamilytemp bafamilyTemp = new Bafamilytemp();
                    Integer beforeSeqNo = new Integer(seqNo);
                    if (bafamilyTemp != null && bafamilyTempList.size() > 0) {
                        for (int i = 0; i < bafamilyTempList.size(); i++) {
                            String afterSeqNo = beforeSeqNo + i < 10 ? "0" + String.valueOf(beforeSeqNo + i) : String.valueOf(beforeSeqNo + i);
                            bafamilyTemp = bafamilyTempList.get(i);
                            receiptService.updateBafamilytempSeqNoData(bafamilytempId, bafamilyTemp.getSeqNo(), afterSeqNo);

                        }
                    }

                    // 設定刪除成功訊息
                    saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());

                    // 設定 forward
                    forward = ConstantKey.FORWARD_DELETE_SUCCESS;

                    // 重新查詢資料
                    // [
                    // 取得國籍清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 取得具名領取清單
                    request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionListForSurvivorTemp(iform.getBafamilytempId(), null));
                    // 眷屬資料清單
                    List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService.getSurvivorBafamilytempDataList(iform.getBafamilytempId());

                    // 將資料存入Session
                    FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                    CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

                    SurvivorAnnuityReceiptForm benForm = new SurvivorAnnuityReceiptForm();
                    benForm.setFocusLocation("benNationTyp");
                    FormSessionHelper.setSurvivorAnnuityReceiptBenForm(benForm, request);
                    // ]
                }
            }
            else if ((APP_ACTION_TYP_UPDATE).equals(iform.getActionTyp())) {
                // 檢查是否為 被具名領取對象
                if (receiptService.getAccSeqNoAmtFromBaappbase(iform.getApNo(), iform.getSeqNo()) > 0) {
                    // 該筆資料為其他資料之具名領取者，不可刪除。
                    saveMessages(session, CustomMessageHelper.getAccDataCantDeleteMessage());
                }
                else {
                    // 刪除該筆眷屬資料
                    boolean isDeleteEvt = receiptService.deleteSurvivorBaappbaseBenData(iform.getBaappbaseId(), iform.getApNo(), userData);

                    // 設定刪除成功訊息
                    saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());

                    if (isDeleteEvt) {// 事故者資料已刪除
                        FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
                        FormSessionHelper.removeSurvivorAnnuityReceiptQryForm(request);
                        FormSessionHelper.removeSurvivorAnnuityReceiptQryCondForm(request);
                        FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                        CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);

                        // 設定 forward
                        forward = FORWARD_DELETE_ALL_SUCCESS;
                    }
                    else {
                        // 設定 forward
                        forward = ConstantKey.FORWARD_DELETE_SUCCESS;
                        // 重新查詢資料
                        // [
                        // 取得國籍清單
                        request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                        // 取得具名領取清單
                        request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionListForSurvivor(null, iform.getApNo()));
                        // 眷屬資料清單
                        List<SurvivorAnnuityReceiptBenCase> benDataList = receiptService.getSurvivorBenDataList(iform.getApNo(), new String[] { "00" }, "in");
                        // 將資料存入Session
                        FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                        CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList(benDataList, request);

                        SurvivorAnnuityReceiptForm benForm = new SurvivorAnnuityReceiptForm();
                        benForm.setFocusLocation("benNationTyp");
                        FormSessionHelper.setSurvivorAnnuityReceiptBenForm(benForm, request);
                        // ]
                    }
                }
            }
            log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 遺屬刪除作業 SurvivorAnnuityReceiptAction.doDeleteBenData() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("SurvivorAnnuityReceiptAction.doDeleteBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 返回修改清單
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackModifyList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 返回修改清單 SurvivorAnnuityReceiptAction.doBackModifyList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = FORWARD_RECEIPT_MODIFY_LIST;
        try {
            // 取得初始查詢條件
            SurvivorAnnuityReceiptForm qryCondForm = FormSessionHelper.getSurvivorAnnuityReceiptQryCondForm(request);

            // 重新查詢資料, 判斷該導回哪一頁
            List<SurvivorAnnuityReceiptCase> dataList = receiptService.getBaappbaseDataListForSurvivor(qryCondForm.getEvtIdnNo(), qryCondForm.getApNo());

            // 受理案件資料筆數<=1時，如果只有一筆資料,正常情況下該筆資料應為使用者原本欲修改的資料
            // 故將系統畫面導至『老年年金給付受理作業(BAAP0D030A)』
            if (dataList.size() <= 1) {
                // 移除所有Session中的資料
                FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
                FormSessionHelper.removeSurvivorAnnuityReceiptQryCondForm(request);
                FormSessionHelper.removeSurvivorAnnuityReceiptQryForm(request);
                FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);

                forward = ConstantKey.FORWARD_BACK;
            }
            // 受理案件資料筆數>1時，將系統畫面導至 登錄修改查詢作業(BAAP0D032C)頁面
            else {
                // 將 查詢結果 存到 Request Scope
                FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
                FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
                CaseSessionHelper.setSurvivorAnnuityReceiptList(dataList, request);
            }

            log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 返回修改清單 SurvivorAnnuityReceiptAction.doBackModifyList() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            log.error("SurvivorAnnuityReceiptAction.doBackModifyList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(forward);
        }
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 返回 SurvivorAnnuityReceiptAction.doBack() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = ConstantKey.FORWARD_BACK;
        try {
            // 移除所有Session中的資料
            FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
            FormSessionHelper.removeSurvivorAnnuityReceiptQryCondForm(request);
            FormSessionHelper.removeSurvivorAnnuityReceiptQryForm(request);
            FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
            CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);

            log.debug("執行 受理作業 - 遺屬年金給付受理作業 - 返回 SurvivorAnnuityReceiptAction.doBack() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            log.error("SurvivorAnnuityReceiptAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(forward);
        }
    }

    public void setReceiptService(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }

}
