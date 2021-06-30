package tw.gov.bli.ba.update.actions;

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
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baregivedtl;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.forms.PaymentDataUpdateForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 給付資料更正 (BAMO0D020C)
 * 
 * @author Rickychi
 */
public class PaymentDataUpdateAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(PaymentDataUpdateAction.class);

    private UpdateService updateService;
    private SelectOptionService selectOptionService;
    // 更正作業 - 給付資料更正 - 給付資料詳細頁面
    private static final String FORWARD_PAYMENT_DETAIL = "paymentDetail";
    // 更正作業 - 給付資料更正 - 給付資料清單頁面
    private static final String FORWARD_PAYMENT_LIST = "paymentList";

    /**
     * 更正作業 - 給付資料更正 - 查詢頁面 (bamo0d020c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 給付資料更正 - 查詢頁面 PaymentDataUpdateAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentDataUpdateForm iform = (PaymentDataUpdateForm) form;
        String forward = ConstantKey.FORWARD_QUERY_FAIL;
        try {
            List<BaappbaseDataUpdateCase> dataList = updateService.selectBaappbaseDataList(null, iform.getApNoStr(), null, new String[] { "90", "99" }, "not in");

            // 資料筆數<=0時，於下方訊息區顯示訊息(MSG：W0040無查詢資料)。
            if (dataList.size() <= 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                forward = ConstantKey.FORWARD_QUERY_FAIL;
            }
            // 資料筆數=1時，查詢該筆明細資料
            // else if (dataList.size() == 1) {
            // BaappbaseDataUpdateCase obj = (BaappbaseDataUpdateCase) dataList.get(0);
            // BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(obj.getBaappbaseId(), "('90', '99')", null, "not in");
            //
            // if (detailData != null) {
            // // 取得給付方式 下拉選單
            // request.getSession().setAttribute(ConstantKey.PAYTYP_OPTION_LIST, selectOptionService.getPayTypOptionList());
            // // 取得具名領取 下拉選單
            // request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionList(detailData.getBaappbaseId(), detailData.getApNo()));
            //
            // // 取得具名領取筆數
            // List<Baappbase> benList = updateService.getBenList(detailData.getBaappbaseId(), detailData.getApNo(), "3", null, detailData.getSeqNo());
            // if (benList.size() >= 1) {
            // detailData.setBenSelectResult("1");
            // }
            // else {
            // detailData.setBenSelectResult("0");
            // }
            //
            // // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
            // PaymentDataUpdateForm modifyForm = new PaymentDataUpdateForm();
            // BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
            // FormSessionHelper.setPaymentDataUpdateForm(modifyForm, request);
            // CaseSessionHelper.setPaymentDataUpdateCase(detailData, request);
            //
            // forward = FORWARD_PAYMENT_DETAIL;
            // }
            // else {
            // // 設定查無資料訊息
            // saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
            // forward = ConstantKey.FORWARD_QUERY_FAIL;
            // }
            // }
            // 資料筆數>1時，將系統畫面導至『給付資料更正查詢列表作業(BAMO0D023C)』
            else {
                // 排除遺屬年金事故者資料
                dataList = updateService.excludeEvtDataForSurvivor(dataList);

                BaappbaseDataUpdateCase obj = (BaappbaseDataUpdateCase) dataList.get(0);

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentDataUpdateList(dataList, request);
                CaseSessionHelper.setPaymentDataUpdateCase(obj, request);

                forward = ConstantKey.FORWARD_QUERY_SUCCESS;
            }

            log.debug("執行 更正作業 - 給付資料更正 - 查詢頁面 PaymentDataUpdateAction.doQuery() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentDataUpdateAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 給付資料更正 - 詳細資料頁面 (bamo0d022c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doModifyDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 給付資料更正 - 詳細資料頁面 PaymentDataUpdateAction.doModifyDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentDataUpdateForm iform = (PaymentDataUpdateForm) form;
        String forward = FORWARD_PAYMENT_LIST;
        try {
            // 取得給付主檔詳細資料
            BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(iform.getBaappbaseId(), new String[] { "90", "99" }, null, "not in");

            if (detailData != null) {
                // 取得給付方式 下拉選單
                request.getSession().setAttribute(ConstantKey.PAYTYP_OPTION_LIST, selectOptionService.getPayTypOptionList(detailData.getBenEvtRel()));
                // 取得具名領取 下拉選單
                List<Baappbase> benList = selectOptionService.getBenOptionList(detailData.getBaappbaseId(), detailData.getApNo());
                request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, benList);
                if (benList.size() >= 1) {
                    detailData.setBenSelectResult("1");
                }
                else {
                    detailData.setBenSelectResult("0");
                }
                // 取得被具名領取筆數
                detailData.setAccSeqNoAmt(updateService.getAccSeqNoAmt(detailData.getApNo(), detailData.getSeqNo()));

                if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(detailData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(detailData.getPayTyp())) {
                    detailData.setPayEeacc("");
                }

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                PaymentDataUpdateForm modifyForm = new PaymentDataUpdateForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
                FormSessionHelper.setPaymentDataUpdateForm(modifyForm, request);
                CaseSessionHelper.setPaymentDataUpdateCase(detailData, request);

                forward = FORWARD_PAYMENT_DETAIL;
            }
            else {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());
                forward = FORWARD_PAYMENT_LIST;
            }
            log.debug("執行 更正作業 - 給付資料更正 - 詳細資料頁面 PaymentDataUpdateAction.doModifyDetail() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentDataUpdateAction.doModifyDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_PAYMENT_LIST);
        }
    }

    /**
     * 更正作業 - 給付資料更正 - 修改作業存檔 (bamo0d021c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 給付資料更正 - 修改作業存檔 PaymentDataUpdateAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PaymentDataUpdateForm iform = (PaymentDataUpdateForm) form;
        // set專戶選擇
        if (StringUtils.isBlank(iform.getSpecialAccAfter())) {
            iform.setSpecialAcc("");
        }
        else {
            iform.setSpecialAcc("Y");
        }
        try {
            BaappbaseDataUpdateCase caseObj = new BaappbaseDataUpdateCase();
            BeanUtility.copyProperties(caseObj, iform);

            // 檢查銀行帳號
            if (("1").equals(caseObj.getPayCategory())) {
                if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(caseObj.getPayTyp())) {
                    if (!updateService.checkBankAccount(caseObj.getPayBankIdBranchId())) {
                        // 設定訊息: 銀行帳號前7碼錯誤
                        saveMessages(session, CustomMessageHelper.getBankIdErrorMessage());
                        return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
                    }
                }
                else if (((ConstantKey.BAAPPBASE_PAYTYP_7).equals(caseObj.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_8).equals(caseObj.getPayTyp())) && !("7000010").equals(caseObj.getPayBankIdBranchId())
                                && !("7000021").equals(caseObj.getPayBankIdBranchId())) {
                    if (!updateService.checkBankAccount(caseObj.getPayBankIdBranchId())) {
                        // 設定訊息: 銀行帳號前7碼錯誤
                        saveMessages(session, CustomMessageHelper.getBankIdErrorMessage());
                        return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
                    }
                }
            }

            // 將被共同具領之受款人員資料存進caseObj
            if (("1").equals(caseObj.getPayCategory())) {
                caseObj.setAccSeqNo(caseObj.getSeqNo());
            }
            else if (("2").equals(caseObj.getPayCategory())) {
                String[] accData = iform.getAccData().split(";");
                caseObj.setAccBaappbaseId(new BigDecimal(accData[0]));
                caseObj.setAccSeqNo(accData[1]);
            }

            // 依畫面輸入欄位條件轉換 給付主檔部分欄位
            caseObj = updateService.transUpdateData(caseObj, null, "payment");

            // 取得需記錄異動LOG的欄位資料
            // 為不影響前端頁面顯示, 故須複製一份 Form
            PaymentDataUpdateForm tempForm = new PaymentDataUpdateForm();
            BeanUtility.copyProperties(tempForm, form);

            // 當異動資料的受款人序尾2碼為"00"，且作業畫面中的「給付方式」由原本的5、6異動成<>5、6時，
            // 存檔時需將「BAAPPBASE.INTERVALMONTH」的值一併修改為0，
            // 此欄位的異動需以整個案件(APNO)為條件，即將整個案件(APNO)下全部受款人(含事故者)的「BAAPPBASE.INTERVALMONTH」修改為0，
            boolean isUpdIntValMon = false;
            BaappbaseDataUpdateCase oldCaseObj = CaseSessionHelper.getPaymentDataUpdateCase(request);
            if (StringUtils.equals(caseObj.getSeqNo().substring(2, 4), "00") && (StringUtils.equals(oldCaseObj.getPayTyp(), ConstantKey.BAAPPBASE_PAYTYP_5) || StringUtils.equals(oldCaseObj.getPayTyp(), ConstantKey.BAAPPBASE_PAYTYP_6))
                            && (!StringUtils.equals(caseObj.getPayTyp(), ConstantKey.BAAPPBASE_PAYTYP_5) && !StringUtils.equals(caseObj.getPayTyp(), ConstantKey.BAAPPBASE_PAYTYP_6))) {
                isUpdIntValMon = true;
                caseObj.setInterValMonth(ConstantKey.BAAPPBASE_INTERVALMONTH_BY_MONTH);
                tempForm.setOldInterValMonth(oldCaseObj.getInterValMonth());
                tempForm.setInterValMonth(ConstantKey.BAAPPBASE_INTERVALMONTH_BY_MONTH);
            }

            caseObj.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYMENT_FIELD_RESOURCE_PREFIX));
            // 取得更新前資料
            BaappbaseDataUpdateCase detailData = CaseSessionHelper.getPaymentDataUpdateCase(request);

            // 存檔時要判斷，此筆資料在畫面上設定為專戶，如果本來此筆資料在資料庫中專戶的設定就為Y，專戶日期也有值，則存檔時就維持原本資料庫中的值，不要用新的日期蓋掉資料庫中的值。
            // set頁面專戶勾選狀態
            if (StringUtils.isBlank(iform.getSpecialAccAfter())) {
                iform.setSpecialAccAfter("");
            }
            if (detailData.getSpecialAcc().equals("Y") && StringUtils.isNotBlank(detailData.getSpeAccDate())) {
                if (iform.getSpecialAccAfter().equals("Y")) {
                    caseObj.setSpecialAcc("Y");
                    caseObj.setSpeAccDate(detailData.getSpeAccDate());
                }
                else {
                    caseObj.setSpecialAcc("");
                    caseObj.setSpeAccDate("");
                }
            }
            else if (StringUtils.isBlank(detailData.getSpecialAcc())) {
                if (iform.getSpecialAccAfter().equals("Y")) {
                    caseObj.setSpecialAcc("Y");
                    caseObj.setSpeAccDate(DateUtility.getNowWestDate());
                }
                else {
                    caseObj.setSpecialAcc("");
                    caseObj.setSpeAccDate("");
                }
            }

            // 根據 baappbaseId 更新主檔
            updateService.updateDataForPayment(userData, caseObj, isUpdIntValMon);

            // 2009.09.15新增, 比對資料決定顯示存擋成功之訊息
            Baregivedtl baregivedtl = updateService.getBaregivedtlPayDataForPaymentDataUpdate(detailData.getApNo(), detailData.getAccSeqNo());

            boolean isDataMatch = true;
            if (baregivedtl != null) {
                // 檢查 PAYBANKID
                if (!(baregivedtl.getPayBankId()).equals(caseObj.getPayKind())) {
                    isDataMatch = false;
                }
                // 檢查 BranchId
                else if (!(baregivedtl.getBranchId()).equals(caseObj.getBranchId())) {
                    isDataMatch = false;
                }
                // 檢查 PayEeacc
                else if (!(baregivedtl.getPayEeacc()).equals(caseObj.getPayEeacc())) {
                    isDataMatch = false;
                }
                // 檢查 AccName
                else if (!(baregivedtl.getAccName()).equals(caseObj.getAccName())) {
                    isDataMatch = false;
                }
            }

            // 設定更新成功訊息
            if (isDataMatch) {
                // 與改匯資料吻合
                saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(iform.getApNoStrDisplay()));
            }
            else {
                // 與改匯資料不合
                saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessButDataNotMatchMessage(iform.getApNoStrDisplay()));
            }

            List<BaappbaseDataUpdateCase> dataList = updateService.selectBaappbaseDataList(null, iform.getApNo(), null, new String[] { "90", "99" }, "not in");

            // 資料筆數<=0時，將系統畫面導至『給付資料更正查詢作業(BAMO0D010C)』
            if (dataList.size() <= 0) {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removePaymentDataUpdateQueryForm(request);
                FormSessionHelper.removePaymentDataUpdateForm(request);
                CaseSessionHelper.removePaymentDataUpdateList(request);

                log.debug("執行 更正作業 - 給付資料更正 - 修改作業存檔 PaymentDataUpdateAction.doUpdate() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
            }
            // 資料筆數>=1時，將系統畫面導至『給付資料更正查詢列表作業(BAMO0D012C)』
            else {
                // 排除遺屬年金事故者資料
                dataList = updateService.excludeEvtDataForSurvivor(dataList);
                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentDataUpdateList(dataList, request);

                log.debug("執行 更正作業 - 給付資料更正 - 修改作業存檔 PaymentDataUpdateAction.doUpdate() 完成 ... ");
                return mapping.findForward(FORWARD_PAYMENT_LIST);
            }
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("PaymentDataUpdateAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 給付資料更正 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 給付資料更正 - 返回 PaymentDataUpdateAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removePaymentDataUpdateQueryForm(request);
        FormSessionHelper.removePaymentDataUpdateForm(request);
        CaseSessionHelper.removePaymentDataUpdateList(request);

        log.debug("執行 更正作業 - 給付資料更正 - 返回 PaymentDataUpdateAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 更正作業 - 給付資料更正 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doModifyBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 給付資料更正 - 返回 PaymentDataUpdateAction.doModifyBack() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentDataUpdateForm iform = (PaymentDataUpdateForm) form;

        try {
            String apNo = iform.getApNo();// 受理編號
            List<BaappbaseDataUpdateCase> dataList = updateService.selectBaappbaseDataList(null, apNo, null, new String[] { "90", "99" }, "not in");

            // 資料筆數<=0時，將系統畫面導至『給付資料更正查詢作業(BAMO0D010C)』
            if (dataList.size() <= 0) {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removePaymentDataUpdateQueryForm(request);
                FormSessionHelper.removePaymentDataUpdateForm(request);
                CaseSessionHelper.removePaymentDataUpdateList(request);

                log.debug("執行 更正作業 - 給付資料更正 - 返回 PaymentDataUpdateAction.doModifyBack() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }
            // 資料筆數>=1時，將系統畫面導至『給付資料更正查詢列表作業(BAMO0D012C)』
            else {
                // 排除遺屬年金事故者資料
                dataList = updateService.excludeEvtDataForSurvivor(dataList);

                BaappbaseDataUpdateCase obj = (BaappbaseDataUpdateCase) dataList.get(0);
                // 將 查詢結果清單 及相關 List Case / Detail Case 存到 Request Scope
                CaseSessionHelper.setPaymentDataUpdateList(dataList, request);
                CaseSessionHelper.setPaymentDataUpdateCase(obj, request);

                log.debug("執行 更正作業 - 給付資料更正 - 返回 PaymentDataUpdateAction.doModifyBack() 完成 ... ");
                return mapping.findForward(FORWARD_PAYMENT_LIST);
            }
        }
        catch (Exception e) {
            log.error("PaymentDataUpdateAction.doModifyBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        }
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
}
