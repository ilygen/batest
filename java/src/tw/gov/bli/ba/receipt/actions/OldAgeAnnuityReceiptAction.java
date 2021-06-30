package tw.gov.bli.ba.receipt.actions;

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
import tw.gov.bli.ba.receipt.cases.OldAgeAnnuityReceiptCase;
import tw.gov.bli.ba.receipt.forms.OldAgeAnnuityReceiptForm;
import tw.gov.bli.ba.receipt.helper.CaseSessionHelper;
import tw.gov.bli.ba.receipt.helper.FormSessionHelper;
import tw.gov.bli.ba.services.ReceiptService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 受理作業 - 老年年金給付受理作業 (BAAP0D010A)
 * 
 * @author Rickychi
 */
public class OldAgeAnnuityReceiptAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OldAgeAnnuityReceiptAction.class);

    // 受理作業 - 老年年金給付受理作業 - 可修改之受理作業清單頁面
    private static final String FORWARD_RECEIPT_MODIFY_LIST = "receiptModifyList";
    // 受理作業 - 老年年金給付受理作業 - 可修改之受理作業詳細資料頁面
    private static final String FORWARD_RECEIPT_MODIFY_DETAIL = "receiptModifyDetail";

    private ReceiptService receiptService;
    private SelectOptionService selectOptionService;

    /**
     * 受理作業 - 老年年金給付受理作業 - 登錄新增作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 老年年金給付受理作業 - 登錄新增作業 OldAgeAnnuityReceiptAction.prepareInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgeAnnuityReceiptForm iform = (OldAgeAnnuityReceiptForm) form;
        try {
            // 取得國籍清單
            request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());

            // 移除頁面輸入資料
            FormSessionHelper.removeOldAgeAnnuityReceiptForm(request);

            log.debug("執行 受理作業 - 老年年金給付受理作業 - 登錄新增作業 OldAgeAnnuityReceiptAction.prepareInsert() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldAgeAnnuityReceiptAction.prepareInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 受理作業 - 老年年金給付受理作業 - 登錄修改查詢作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareModify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 老年年金給付受理作業 - 登錄修改查詢作業 OldAgeAnnuityReceiptAction.prepareModify() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgeAnnuityReceiptForm iform = (OldAgeAnnuityReceiptForm) form;
        try {
            String apNo = "";
            if (!("").equals(iform.getApNo1().trim()) && !("").equals(iform.getApNo2().trim()) && !("").equals(iform.getApNo3().trim()) && !("").equals(iform.getApNo4().trim())) {
                apNo = iform.getApNoStr();
            }
            List<OldAgeAnnuityReceiptCase> dataList = receiptService.selectBaappbaseDataList(iform.getEvtIdnNo(), apNo, null, new String[] { "00" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);

            // 受理案件資料筆數<=0時，於下方訊息區顯示訊息(MSG：W0040無查詢資料)。
            if (dataList.size() <= 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 受理作業 - 老年年金給付受理作業 - 登錄修改查詢作業 OldAgeAnnuityReceiptAction.prepareModify() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_FAIL);
            }
            // 受理案件資料筆數=1時，查詢該筆受理案件明細資料，將系統畫面導至 登錄修改作業(BAAP0D013C)頁面
            else if (dataList.size() == 1) {
                OldAgeAnnuityReceiptCase obj = (OldAgeAnnuityReceiptCase) dataList.get(0);
                OldAgeAnnuityReceiptCase detailData = receiptService.getBaappbaseDetail(obj.getBaappbaseId(), new String[] { "00" }, "in");
                // 取得國籍清單
                request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                // 將欲修改的資料帶到 Form 中, 並將 Form 存到 Request Scope
                OldAgeAnnuityReceiptForm modifyForm = new OldAgeAnnuityReceiptForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
                FormSessionHelper.setOldAgeAnnuityReceiptForm(modifyForm, request);

                // 將查詢條件存入form
                OldAgeAnnuityReceiptForm qryForm = new OldAgeAnnuityReceiptForm();
                BeanUtility.copyProperties(qryForm, iform);
                qryForm.setApNo(apNo);
                FormSessionHelper.setOldAgeAnnuityReceiptQryCondForm(qryForm, request);

                log.debug("執行 受理作業 - 老年年金給付受理作業 - 登錄修改查詢作業 OldAgeAnnuityReceiptAction.prepareModify() 完成 ... ");
                return mapping.findForward(FORWARD_RECEIPT_MODIFY_DETAIL);
            }
            // 受理案件資料筆數>1時，將系統畫面導至 登錄修改查詢作業(BAAP0D012C)頁面
            else {

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setOldAgeAnnuityReceiptList(dataList, request);

                // 將查詢條件存入form
                OldAgeAnnuityReceiptForm qryForm = new OldAgeAnnuityReceiptForm();
                BeanUtility.copyProperties(qryForm, iform);
                qryForm.setApNo(apNo);
                FormSessionHelper.setOldAgeAnnuityReceiptQryCondForm(qryForm, request);

                log.debug("執行 受理作業 - 老年年金給付受理作業 - 登錄修改查詢作業 OldAgeAnnuityReceiptAction.prepareModify() 完成 ... ");
                return mapping.findForward(FORWARD_RECEIPT_MODIFY_LIST);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldAgeAnnuityReceiptAction.prepareModify() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 受理作業 - 老年年金給付受理作業 - 登錄修改作業 (BAAP0D012C)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareModifyDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 老年年金給付受理作業 - 登錄修改作業 OldAgeAnnuityReceiptAction.prepareModifyDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        try {
            OldAgeAnnuityReceiptForm iform = (OldAgeAnnuityReceiptForm) form;
            // 取得受理案件明細資料
            OldAgeAnnuityReceiptCase detailData = receiptService.getBaappbaseDetail(iform.getBaappbaseId(), new String[] { "00" }, "in");

            // 取得國籍清單
            request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());

            // 將欲修改的資料帶到 Form 中, 並將 Form 存到 Request Scope
            OldAgeAnnuityReceiptForm modifyForm = new OldAgeAnnuityReceiptForm();
            BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
            FormSessionHelper.setOldAgeAnnuityReceiptForm(modifyForm, request);
            log.debug("執行 受理作業 - 老年年金給付受理作業 - 登錄修改作業 OldAgeAnnuityReceiptAction.prepareModifyDetail() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldAgeAnnuityReceiptAction.prepareModifyDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 受理作業 - 老年年金給付受理作業 - 新增作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 老年年金給付受理作業 - 新增作業 OldAgeAnnuityReceiptAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String forward = ConstantKey.FORWARD_SAVE_SUCCESS;
        try {
            OldAgeAnnuityReceiptForm iform = (OldAgeAnnuityReceiptForm) form;
            OldAgeAnnuityReceiptCase caseObj = new OldAgeAnnuityReceiptCase();
            BeanUtility.copyProperties(caseObj, iform);

            // 依畫面輸入欄位條件轉換 給付主檔部分欄位
            caseObj = receiptService.transReceiptInputData(caseObj);

            // 受理編號
            // 20090123 新增開放可以手動輸入受理編號
            // 如果畫面上有輸入受理編號則使用輸入之編號, 並判斷受理編號是否存在
            // 否則自動取號
            String apNo = "";
            if (StringUtils.isNotBlank(iform.getApNoStr())) {
                apNo = iform.getApNoStr();

                // 手動輸入之受理編號不可為 L00000000000
                if (StringUtils.equalsIgnoreCase(apNo, "L00000000000")) {
                    // 設定訊息: 受理編號編碼規則錯誤
                    saveMessages(session, DatabaseMessageHelper.getApNoErrorMessage());
                    forward = ConstantKey.FORWARD_SAVE_FAIL;
                    return mapping.findForward(forward);
                }

                // 判斷該受理編號是否已存在
                List<OldAgeAnnuityReceiptCase> list = receiptService.selectBaappbaseDataList(null, apNo, "0000", null, null, null);
                if (list.size() == 0) {
                    caseObj.setApNo(apNo);
                    // 受理編號(頁面顯示)
                    String apNoStr = apNo.substring(0, 1) + apNo.substring(1, 2) + apNo.substring(2, 7) + apNo.substring(7, 12);
                    // 新增資料至給付主檔
                    receiptService.insertBaappbaseData(userData, caseObj);

                    // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                    FormSessionHelper.removeOldAgeAnnuityReceiptForm(request);
                    FormSessionHelper.removeOldAgeAnnuityReceiptQryCondForm(request);
                    FormSessionHelper.removeOldAgeAnnuityReceiptQryForm(request);
                    CaseSessionHelper.removeOldAgeAnnuityReceiptList(request);

                    // 設定新增成功訊息
                    saveMessages(session, DatabaseMessageHelper.getReceiptSaveSuccessMessage(caseObj.getApNoStrDisplay()));
                }
                else {
                    // 設定訊息:新增失敗，此受理號碼已存在！
                    saveMessages(session, DatabaseMessageHelper.getApNoExistMessage());
                    forward = ConstantKey.FORWARD_SAVE_FAIL;
                }
            }
            else {
                apNo = "L2" + StringUtility.chtLeftPad(receiptService.getSequenceApNo(), 10, "0");
                caseObj.setApNo(apNo);
                // 受理編號(頁面顯示)
                String apNoStr = apNo.substring(0, 1) + apNo.substring(1, 2) + apNo.substring(2, 7) + apNo.substring(7, 12);
                // 新增資料至給付主檔
                receiptService.insertBaappbaseData(userData, caseObj);

                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeOldAgeAnnuityReceiptForm(request);
                FormSessionHelper.removeOldAgeAnnuityReceiptQryCondForm(request);
                FormSessionHelper.removeOldAgeAnnuityReceiptQryForm(request);
                CaseSessionHelper.removeOldAgeAnnuityReceiptList(request);

                // 設定新增成功訊息
                saveMessages(session, DatabaseMessageHelper.getReceiptSaveSuccessMessage(caseObj.getApNoStrDisplay()));
            }
            log.debug("執行 受理作業 - 老年年金給付受理作業 - 新增作業 OldAgeAnnuityReceiptAction.doInsert() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定新增失敗訊息
            log.error("OldAgeAnnuityReceiptAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 受理作業 - 老年年金給付受理作業 - 修改作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 老年年金給付受理作業 - 修改作業 OldAgeAnnuityReceiptAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgeAnnuityReceiptForm iform = (OldAgeAnnuityReceiptForm) form;
        try {
            OldAgeAnnuityReceiptCase caseObj = new OldAgeAnnuityReceiptCase();
            BeanUtility.copyProperties(caseObj, iform);

            // 依畫面輸入欄位條件轉換 給付主檔部分欄位
            caseObj = receiptService.transReceiptInputData(caseObj);
            // 取得需記錄異動LOG的欄位資料
            // 為不影響前端頁面顯示, 故須複製一份 Form
            OldAgeAnnuityReceiptForm tempForm = new OldAgeAnnuityReceiptForm();
            BeanUtility.copyProperties(tempForm, form);
            tempForm.setCommZip(caseObj.getCommZip());
            tempForm.setCommAddr(caseObj.getCommAddr());
            caseObj.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_RECEIPT_OLDAGE_FIELD_RESOURCE_PREFIX));

            // 根據 baappbaseId 更新主檔
            receiptService.updateBaappbaseData(userData, caseObj);

            // 取得 查詢條件
            OldAgeAnnuityReceiptForm qryForm = FormSessionHelper.getOldAgeAnnuityReceiptQryCondForm(request);
            List<OldAgeAnnuityReceiptCase> dataList = receiptService.selectBaappbaseDataList(qryForm.getEvtIdnNo(), qryForm.getApNo(), null, new String[] { "00" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);

            // 受理案件資料筆數<=1時，將系統畫面導至『老年年金給付受理作業(BAAP0D010A)』
            if (dataList.size() <= 1) {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeOldAgeAnnuityReceiptForm(request);
                FormSessionHelper.removeOldAgeAnnuityReceiptQryCondForm(request);
                FormSessionHelper.removeOldAgeAnnuityReceiptQryForm(request);
                CaseSessionHelper.removeOldAgeAnnuityReceiptList(request);

                // 設定更新成功訊息
                saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(iform.getApNoStrDisplay()));

                log.debug("執行 受理作業 - 老年年金給付受理作業 - 修改作業 OldAgeAnnuityReceiptAction.doUpdate() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
            }
            // 受理案件資料筆數>1時，將系統畫面導至『登錄修改查詢作業(BAAP0D012C)頁面』
            else {
                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setOldAgeAnnuityReceiptList(dataList, request);
                // 設定更新成功訊息
                saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(iform.getApNoStrDisplay()));

                log.debug("執行 受理作業 - 老年年金給付受理作業 - 修改作業 OldAgeAnnuityReceiptAction.doUpdate() 完成 ... ");
                return mapping.findForward(FORWARD_RECEIPT_MODIFY_LIST);
            }
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("OldAgeAnnuityReceiptAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 受理作業 - 老年年金給付受理作業 - 刪除作業
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 老年年金給付受理作業 - 刪除作業 OldAgeAnnuityReceiptAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgeAnnuityReceiptForm iform = (OldAgeAnnuityReceiptForm) form;
        try {
            OldAgeAnnuityReceiptCase caseObj = new OldAgeAnnuityReceiptCase();
            BeanUtility.copyProperties(caseObj, iform);

            // 根據 baappbaseId 刪除主檔
            receiptService.deleteBaappbaseData(userData, caseObj);

            // 取得 查詢條件
            OldAgeAnnuityReceiptForm qryForm = FormSessionHelper.getOldAgeAnnuityReceiptQryCondForm(request);
            List<OldAgeAnnuityReceiptCase> dataList = receiptService.selectBaappbaseDataList(qryForm.getEvtIdnNo(), qryForm.getApNo(), null, new String[] { "00" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);

            // 受理案件資料筆數<=0時，將系統畫面導至『老年年金給付受理作業(BAAP0D010A)』
            if (dataList.size() <= 0) {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeOldAgeAnnuityReceiptForm(request);
                FormSessionHelper.removeOldAgeAnnuityReceiptQryCondForm(request);
                FormSessionHelper.removeOldAgeAnnuityReceiptQryForm(request);
                CaseSessionHelper.removeOldAgeAnnuityReceiptList(request);

                // 設定刪除成功訊息
                saveMessages(session, DatabaseMessageHelper.getReceiptDeleteSuccessMessage(iform.getApNoStrDisplay()));

                log.debug("執行 受理作業 - 老年年金給付受理作業 - 刪除作業 OldAgeAnnuityReceiptAction.doDelete() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);
            }
            // 受理案件資料筆數>1時，將系統畫面導至『登錄修改查詢作業(BAAP0D012C)頁面』
            else {
                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setOldAgeAnnuityReceiptList(dataList, request);

                // 設定刪除成功訊息
                saveMessages(session, DatabaseMessageHelper.getReceiptDeleteSuccessMessage(iform.getApNoStrDisplay()));

                log.debug("執行 受理作業 - 老年年金給付受理作業 - 刪除作業 OldAgeAnnuityReceiptAction.doDelete() 完成 ... ");
                return mapping.findForward(FORWARD_RECEIPT_MODIFY_LIST);
            }
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("OldAgeAnnuityReceiptAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 受理作業 - 老年年金給付受理作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 老年年金給付受理作業 - 返回 OldAgeAnnuityReceiptAction.doBack() 開始 ... ");
        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeOldAgeAnnuityReceiptForm(request);
            FormSessionHelper.removeOldAgeAnnuityReceiptQryCondForm(request);
            FormSessionHelper.removeOldAgeAnnuityReceiptQryForm(request);
            CaseSessionHelper.removeOldAgeAnnuityReceiptList(request);

            log.debug("執行 受理作業 - 老年年金給付受理作業 - 返回 OldAgeAnnuityReceiptAction.doBack() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        }
        catch (RuntimeException e) {
            log.error("OldAgeAnnuityReceiptAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        }
    }

    /**
     * 受理作業 - 老年年金給付受理作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doModifyBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 受理作業 - 老年年金給付受理作業 - 返回 OldAgeAnnuityReceiptAction.doModifyBack() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgeAnnuityReceiptForm iform = (OldAgeAnnuityReceiptForm) form;

        try {
            // 取得 查詢條件
            OldAgeAnnuityReceiptForm qryForm = FormSessionHelper.getOldAgeAnnuityReceiptQryCondForm(request);
            List<OldAgeAnnuityReceiptCase> dataList = receiptService.selectBaappbaseDataList(qryForm.getEvtIdnNo(), qryForm.getApNo(), null, new String[] { "00" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);

            // 受理案件資料筆數<=1時，將系統畫面導至『老年年金給付受理作業(BAAP0D010A)』
            if (dataList.size() <= 1) {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeOldAgeAnnuityReceiptForm(request);
                FormSessionHelper.removeOldAgeAnnuityReceiptQryCondForm(request);
                FormSessionHelper.removeOldAgeAnnuityReceiptQryForm(request);
                CaseSessionHelper.removeOldAgeAnnuityReceiptList(request);

                log.debug("執行 受理作業 - 老年年金給付受理作業 - 返回 OldAgeAnnuityReceiptAction.doModifyBack() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }
            // 受理案件資料筆數>1時，將系統畫面導至『登錄修改查詢作業(BAAP0D012C)頁面』
            else {
                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setOldAgeAnnuityReceiptList(dataList, request);
                log.debug("執行 受理作業 - 老年年金給付受理作業 - 返回 OldAgeAnnuityReceiptAction.doModifyBack() 完成 ... ");
                return mapping.findForward(FORWARD_RECEIPT_MODIFY_LIST);
            }
        }
        catch (Exception e) {
            log.error("OldAgeAnnuityReceiptAction.doModifyBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
