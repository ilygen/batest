package tw.gov.bli.ba.reviewfee.actions;

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
import org.apache.struts.actions.DispatchAction;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bcbpf;
import tw.gov.bli.ba.domain.Cvldtl;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeReceiptCase;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeReceiptDetailCase;
import tw.gov.bli.ba.reviewfee.forms.ReviewFeeReceiptForm;
import tw.gov.bli.ba.reviewfee.helper.CaseSessionHelper;
import tw.gov.bli.ba.services.ReviewFeeService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp)
 * 
 * @author Goston
 */
public class ReviewFeeReceiptAction extends DispatchAction {
    private static Log log = LogFactory.getLog(ReviewFeeReceiptAction.class);

    private ReviewFeeService reviewFeeService;
    private SelectOptionService selectOptionService;

    private static final String FORWARD_QUERY_PAGE = "queryPage";
    private static final String FORWARD_ADD_PAGE = "addPage";
    private static final String FORWARD_LIST_PAGE = "listPage";
    private static final String FORWARD_MODIFY_PAGE = "modifyPage";

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsertQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用受理作業 - 查詢頁面 ReviewFeeReceiptActions.doInsertQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeReceiptForm insertQueryForm = (ReviewFeeReceiptForm) form;

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeReviewFeeReceiptCase(request);

            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(insertQueryForm.getApNo1())) + StringUtils.trimToEmpty(insertQueryForm.getApNo2()) + StringUtils.trimToEmpty(insertQueryForm.getApNo3()) + StringUtils.trimToEmpty(insertQueryForm.getApNo4());

            if (StringUtils.length(apNo) != ConstantKey.APNO_LENGTH) { // 受理編號長度不滿 12 碼則不做查詢
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getApNoNonExistErrorMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 取得 新增案件 Header 資料
            ReviewFeeReceiptCase caseData = reviewFeeService.getReviewFeeReceiptDataForInsert(apNo);

            // 無查詢資料
            if (caseData == null) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getApNoNonExistErrorMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 將 CaseData 塞到 Session 中
            CaseSessionHelper.setReviewFeeReceiptCase(caseData, request);

            // Form 值重置
            insertQueryForm.clearFields();

            // Form 下拉式選單初始化
            // [
            insertQueryForm.setReasCodeOptionList(selectOptionService.getReasCodeOptionList()); // 複檢原因下拉選單
            insertQueryForm.setBenEvtRelOptionList(selectOptionService.getReviewFeeReceiptRelationOptionList()); // 受益人與事故者關係下拉選單
            insertQueryForm.setCountryOptionList(selectOptionService.getCountry());// 國籍下拉選單
            insertQueryForm.setPayTypOptionList(selectOptionService.getReviewFeeReceiptPayTypeOptionList()); // 給付方式下拉選單
            // ]

            log.debug("執行 複檢費用 - 複檢費用受理作業 - 查詢頁面 ReviewFeeReceiptActions.doInsertQuery() 完成 ... ");

            return mapping.findForward(FORWARD_ADD_PAGE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReviewFeeReceiptActions.doInsertQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d011a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用受理作業 - 新增頁面 ReviewFeeReceiptActions.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeReceiptForm insertForm = (ReviewFeeReceiptForm) form;

        try {
            // 自 Session 中取出 Case 資料
            ReviewFeeReceiptCase caseData = CaseSessionHelper.getReviewFeeReceiptCase(request);

            if (caseData == null) {
                // 設定存檔失敗訊息
                saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            // 先檢核帳號是否合法
            if (!isValidAccountNo(insertForm.getPayTyp(), insertForm.getAccountNo1(), insertForm.getAccountNo2())) {
                // 訊息: 帳號錯誤, 請重新輸入
                saveErrors(session, CustomMessageHelper.getAccountNoErrorMessage());
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            // 將 Form 的值設定到 Case
            // [
            ReviewFeeReceiptDetailCase addCaseData = new ReviewFeeReceiptDetailCase();

            addCaseData.setReApNo(caseData.getReApNo()); // 複檢費用受理編號
            addCaseData.setApSeq(caseData.getApSeq()); // 複檢費用申請序
            addCaseData.setApNo(caseData.getApNo()); // 受理編號
            addCaseData.setProcStat("00"); // 處理狀態
            addCaseData.setInreDate((StringUtils.length(insertForm.getInreDate()) == 7) ? DateUtility.changeDateType(insertForm.getInreDate()) : insertForm.getInreDate()); // 通知複檢日期
            addCaseData.setReasCode(insertForm.getReasCode()); // 複檢原因
            addCaseData.setHosId(insertForm.getHosId()); // 醫療院所代碼
            addCaseData.setRecosDate((StringUtils.length(insertForm.getRecosDate()) == 7) ? DateUtility.changeDateType(insertForm.getRecosDate()) : insertForm.getRecosDate()); // 複檢費用申請日期
            addCaseData.setReNum(insertForm.getReNum()); // 複檢門診次數
            addCaseData.setRehpStay(insertForm.getRehpStay()); // 複檢住院天數
            addCaseData.setReFees(insertForm.getReFees()); // 複檢費用
            addCaseData.setNonreFees(insertForm.getNonreFees()); // 非複檢必須費用

            // 複檢實付金額
            BigDecimal reAmtPay = ((insertForm.getReFees() == null) ? new BigDecimal(0) : insertForm.getReFees());
            if (insertForm.getNonreFees() != null)
                reAmtPay = reAmtPay.subtract(insertForm.getNonreFees());
            addCaseData.setReAmtPay(reAmtPay); // 複檢實付金額

            addCaseData.setNotifyForm(insertForm.getNotifyForm()); // 核定通知書格式
            addCaseData.setBenIdnNo(insertForm.getBenIdnNo()); // 受益人身分證號
            addCaseData.setBenName(insertForm.getBenName()); // 受益人姓名
            addCaseData.setBenBrDate((StringUtils.length(insertForm.getBenBrDate()) == 7 || StringUtils.equals(StringUtils.substring(insertForm.getBenBrDate(), 0, 1), "-")) ? DateUtility.changeDateType(insertForm.getBenBrDate()) : insertForm.getBenBrDate()); // 受益人出生日期

            // 受益人性別
            if (StringUtils.equals(insertForm.getBenNationTyp(), "1"))
                addCaseData.setBenSex(StringUtils.substring(insertForm.getBenIdnNo(), 1, 2)); // 受益人性別
            else
                addCaseData.setBenSex(insertForm.getBenSex()); // 受益人性別

            addCaseData.setBenNationTyp(insertForm.getBenNationTyp()); // 受益人國籍別

            // 受益人國籍
            if (StringUtils.equals(insertForm.getBenNationTyp(), "1"))
                addCaseData.setBenNationCode("000"); // 受益人國籍
            else
                addCaseData.setBenNationCode(insertForm.getBenNationCode()); // 受益人國籍

            addCaseData.setBenEvtRel(insertForm.getBenEvtRel()); // 受益人與事故者關係
            addCaseData.setTel1(insertForm.getTel1()); // 電話1
            addCaseData.setTel2(insertForm.getTel2()); // 電話2
            addCaseData.setCommTyp(insertForm.getCommTyp()); // 通訊地址別

            // 通訊地址
            if (StringUtils.equals(insertForm.getCommTyp(), "1")) {
                // 同戶籍地
                List<Cvldtl> cvldtlList = reviewFeeService.getReviewFeeReceiptCommAddrData(insertForm.getBenIdnNo(), insertForm.getBenBrDate());
                if (cvldtlList != null) {
                    for (Cvldtl cvldtlData : cvldtlList) {
                        addCaseData.setCommZip(cvldtlData.getHzip()); // 通訊郵遞區號
                        addCaseData.setCommAddr(cvldtlData.getHaddr()); // 通訊地址
                    }
                }
            }
            else {
                // 現住址
                addCaseData.setCommZip(insertForm.getCommZip()); // 通訊郵遞區號
                addCaseData.setCommAddr(insertForm.getCommAddr()); // 通訊地址
            }

            addCaseData.setPayTyp(insertForm.getPayTyp()); // 給付方式

            // 金融機構名稱 金融機構總代號 分支代號 銀行帳號 戶名 處理
            if (StringUtils.equals(insertForm.getPayTyp(), "1")) { // 匯入銀行帳戶
            	Bcbpf bankData = reviewFeeService.getBankData(insertForm.getAccountNo1());
                StringBuffer bankName = new StringBuffer("");

                if (bankData != null)
                    bankName.append(StringUtils.defaultString(bankData.getBnkNme()));

                addCaseData.setBankName(bankName.toString()); // 金融機構名稱
                addCaseData.setPayBankId(StringUtils.substring(insertForm.getAccountNo1(), 0, 3)); // 金融機構總代號
                addCaseData.setBranchId(StringUtils.substring(insertForm.getAccountNo1(), 3, 7)); // 分支代號
                addCaseData.setPayeeAcc(StringUtils.leftPad(insertForm.getAccountNo2(), 14, "0")); // 銀行帳號
                addCaseData.setAccName(insertForm.getAccName()); // 戶名
            }
            else if (StringUtils.equals(insertForm.getPayTyp(), "2")) { // 匯入郵局帳號
                addCaseData.setBankName(StringUtils.defaultString(reviewFeeService.getPostNameBy(insertForm.getAccountNo1()))); // 金融機構名稱
                addCaseData.setPayBankId(StringUtils.substring(insertForm.getAccountNo1(), 0, 3)); // 金融機構總代號
                addCaseData.setBranchId(StringUtils.substring(insertForm.getAccountNo1(), 3, 7)); // 分支代號

                String accountNo1 = StringUtils.defaultString(insertForm.getAccountNo1());
                String accountNo2 = StringUtils.defaultString(insertForm.getAccountNo2());
                if (StringUtils.equals(accountNo1, "7000010"))
                    accountNo2 = StringUtils.leftPad(accountNo2, 8, "0");
                else if (StringUtils.equals(accountNo1, "7000021"))
                    accountNo2 = StringUtils.leftPad(accountNo2, 14, "0");

                addCaseData.setPayeeAcc(accountNo2); // 銀行帳號
                addCaseData.setAccName(insertForm.getAccName()); // 戶名
            }
            else if (StringUtils.equals(insertForm.getPayTyp(), "5") || StringUtils.equals(insertForm.getPayTyp(), "6")) { // 國外匯款 大陸地區匯款
                addCaseData.setBankName(insertForm.getBankName()); // 金融機構名稱
                addCaseData.setPayBankId(StringUtils.substring(insertForm.getAccountNo1(), 0, 3)); // 金融機構總代號
                addCaseData.setBranchId(StringUtils.substring(insertForm.getAccountNo1(), 3, 7)); // 分支代號
                addCaseData.setPayeeAcc(StringUtils.substring(insertForm.getAccountNo1(), 7, 21)); // 銀行帳號
                addCaseData.setAccName(insertForm.getAccName()); // 戶名
            }

            caseData.setAddCaseData(addCaseData);
            // ]

            // 新增存檔
            reviewFeeService.saveReviewFeeReceiptData(userData, caseData);

            // 設定更新成功訊息
            saveMessages(session, DatabaseMessageHelper.getReceiptSaveSuccessMessage(caseData.getApNo()));

            // 將 CaseData 自 Session 移除
            CaseSessionHelper.removeReviewFeeReceiptCase(request);

            // Form 值重置
            insertForm.clearFields();

            log.debug("執行 複檢費用 - 複檢費用受理作業 - 新增頁面 ReviewFeeReceiptActions.doInsert() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            // 設定存檔失敗訊息
            log.error("ReviewFeeReceiptActions.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doModifyQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用受理作業 - 查詢頁面 ReviewFeeReceiptActions.doModifyQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeReceiptForm modifyQueryForm = (ReviewFeeReceiptForm) form;

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeReviewFeeReceiptCase(request);

            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(modifyQueryForm.getApNo1())) + StringUtils.trimToEmpty(modifyQueryForm.getApNo2()) + StringUtils.trimToEmpty(modifyQueryForm.getApNo3()) + StringUtils.trimToEmpty(modifyQueryForm.getApNo4());

            if (StringUtils.length(apNo) != ConstantKey.APNO_LENGTH) { // 受理編號長度不滿 12 碼則不做查詢
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getApNoNonExistErrorMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 取得修改案件資料
            ReviewFeeReceiptCase caseData = reviewFeeService.getReviewFeeReceiptDataForUpdate(apNo, modifyQueryForm.getQryIdnNo());

            // 無查詢資料
            if (caseData == null) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getApNoNonExistErrorMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 將 CaseData 塞到 Session 中
            CaseSessionHelper.setReviewFeeReceiptCase(caseData, request);

            log.debug("執行 複檢費用 - 複檢費用受理作業 - 查詢頁面 ReviewFeeReceiptActions.doModifyQuery() 完成 ... ");

            return mapping.findForward(FORWARD_LIST_PAGE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReviewFeeReceiptActions.doModifyQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d012c.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryModifyData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用受理作業 - 修改 List 頁面 ReviewFeeReceiptActions.doQueryModifyData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeReceiptForm listForm = (ReviewFeeReceiptForm) form;

        try {
            // 自 Session 中取出 Case 資料
            ReviewFeeReceiptCase caseData = CaseSessionHelper.getReviewFeeReceiptCase(request);

            if (caseData == null) {
                // 設定更新失敗訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
                return mapping.findForward(FORWARD_QUERY_PAGE);
            }

            // 自 Case 中取得欲修改的該筆資料
            ReviewFeeReceiptDetailCase detailCaseData = caseData.getDetailDataByApSeq(listForm.getListApSeq());

            if (detailCaseData == null) {
                // 設定更新失敗訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
                return mapping.findForward(FORWARD_LIST_PAGE);
            }

            // 將 Detail Case 的資料設定到 Form 中
            // [
            // Form 值重置
            listForm.clearFields();

            BeanUtility.copyPropertiesForUpdate(listForm, detailCaseData, ConstantKey.OLD_FIELD_PREFIX);

            listForm.setTmpBenNationCode(detailCaseData.getBenNationCode()); // 受益人國籍代碼
            listForm.setOldTmpBenNationCode(detailCaseData.getBenNationCode()); // 受益人國籍代碼

            // 西元日期轉民國日期
            listForm.setInreDate(DateUtility.changeDateType(detailCaseData.getInreDate())); // 通知複檢日期
            listForm.setRecosDate(DateUtility.changeDateType(detailCaseData.getRecosDate())); // 複檢費用申請日期
            listForm.setBenBrDate(DateUtility.changeDateType(detailCaseData.getBenBrDate())); // 受益人出生日期

            listForm.setOldInreDate(DateUtility.changeDateType(detailCaseData.getInreDate())); // 通知複檢日期
            listForm.setOldRecosDate(DateUtility.changeDateType(detailCaseData.getRecosDate())); // 複檢費用申請日期
            listForm.setOldBenBrDate(DateUtility.changeDateType(detailCaseData.getBenBrDate())); // 受益人出生日期

            // 帳號 (前)、帳號 (後)、金融機構名稱、戶名 處理
            if (StringUtils.equals(detailCaseData.getPayTyp(), "1") || StringUtils.equals(detailCaseData.getPayTyp(), "2")) {
                listForm.setAccountNo1(StringUtils.defaultString(detailCaseData.getPayBankId()) + StringUtils.defaultString(detailCaseData.getBranchId()));
                listForm.setAccountNo2(detailCaseData.getPayeeAcc());
                listForm.setBankName(""); // 注意: 此欄位的改前值不用設為空值
                listForm.setAccName(""); // 注意: 此欄位的改前值不用設為空值

                listForm.setOldAccountNo1(StringUtils.defaultString(detailCaseData.getPayBankId()) + StringUtils.defaultString(detailCaseData.getBranchId()));
                listForm.setOldAccountNo2(detailCaseData.getPayeeAcc());
                listForm.setOldBankName(detailCaseData.getBankName());
                listForm.setOldAccName(detailCaseData.getAccName());
            }
            else if (StringUtils.equals(detailCaseData.getPayTyp(), "5") || StringUtils.equals(detailCaseData.getPayTyp(), "6")) {
                listForm.setAccountNo1(StringUtils.defaultString(detailCaseData.getPayBankId()) + StringUtils.defaultString(detailCaseData.getBranchId()) + StringUtils.defaultString(detailCaseData.getPayeeAcc()));
                listForm.setAccountNo2("");
                listForm.setBankName(detailCaseData.getBankName());
                listForm.setAccName(detailCaseData.getAccName());

                listForm.setOldAccountNo1(StringUtils.defaultString(detailCaseData.getPayBankId()) + StringUtils.defaultString(detailCaseData.getBranchId()) + StringUtils.defaultString(detailCaseData.getPayeeAcc()));
                listForm.setOldAccountNo2("");
                listForm.setOldBankName(detailCaseData.getBankName());
                listForm.setOldAccName(detailCaseData.getAccName());
            }
            else {
                listForm.setAccountNo1("");
                listForm.setAccountNo2("");
                listForm.setBankName("");
                listForm.setAccName("");

                listForm.setOldAccountNo1("");
                listForm.setOldAccountNo2("");
                listForm.setOldBankName("");
                listForm.setOldAccName("");
            }
            // ]

            // Form 下拉式選單初始化
            // [
            listForm.setReasCodeOptionList(selectOptionService.getReasCodeOptionList()); // 複檢原因下拉選單
            listForm.setBenEvtRelOptionList(selectOptionService.getReviewFeeReceiptRelationOptionList()); // 受益人與事故者關係下拉選單
            listForm.setCountryOptionList(selectOptionService.getCountry());// 國籍下拉選單
            listForm.setPayTypOptionList(selectOptionService.getReviewFeeReceiptPayTypeOptionList()); // 給付方式下拉選單
            // ]

            log.debug("執行 複檢費用 - 複檢費用受理作業 - 修改 List 頁面 ReviewFeeReceiptActions.doQueryModifyData() 完成 ... ");

            return mapping.findForward(FORWARD_MODIFY_PAGE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReviewFeeReceiptActions.doQueryModifyData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_LIST_PAGE);
        }
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d012c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToQueryPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ReviewFeeReceiptForm listForm = (ReviewFeeReceiptForm) form;

        // 將 Case 清除
        CaseSessionHelper.removeReviewFeeReceiptCase(request);

        // Form 值重置
        listForm.clearFields();

        return mapping.findForward(FORWARD_QUERY_PAGE);
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d013c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用受理作業 - 修改 Detail 頁面 ReviewFeeReceiptActions.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeReceiptForm updateForm = (ReviewFeeReceiptForm) form;

        try {
            // 自 Session 中取出 Case 資料
            ReviewFeeReceiptCase caseData = CaseSessionHelper.getReviewFeeReceiptCase(request);

            if (caseData == null) {
                // 設定更新失敗訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
                return mapping.findForward(FORWARD_QUERY_PAGE);
            }

            // 先檢核帳號是否合法
            if (!isValidAccountNo(updateForm.getPayTyp(), updateForm.getAccountNo1(), updateForm.getAccountNo2())) {
                // 訊息: 帳號錯誤, 請重新輸入
                saveErrors(session, CustomMessageHelper.getAccountNoErrorMessage());
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            // 將 Form 的值設定到 Case
            // [
            ReviewFeeReceiptDetailCase updateCaseData = new ReviewFeeReceiptDetailCase();

            updateCaseData.setReApNo(updateForm.getReApNo()); // 複檢費用受理編號
            updateCaseData.setApSeq(updateForm.getApSeq()); // 複檢費用申請序
            updateCaseData.setApNo(updateForm.getApNo()); // 受理編號
            updateCaseData.setInreDate((StringUtils.length(updateForm.getInreDate()) == 7) ? DateUtility.changeDateType(updateForm.getInreDate()) : updateForm.getInreDate()); // 通知複檢日期
            updateCaseData.setReasCode(updateForm.getReasCode()); // 複檢原因
            updateCaseData.setHosId(updateForm.getHosId()); // 醫療院所代碼
            updateCaseData.setRecosDate((StringUtils.length(updateForm.getRecosDate()) == 7) ? DateUtility.changeDateType(updateForm.getRecosDate()) : updateForm.getRecosDate()); // 複檢費用申請日期
            updateCaseData.setReNum(updateForm.getReNum()); // 複檢門診次數
            updateCaseData.setRehpStay(updateForm.getRehpStay()); // 複檢住院天數
            updateCaseData.setReFees(updateForm.getReFees()); // 複檢費用
            updateCaseData.setNonreFees(updateForm.getNonreFees()); // 非複檢必須費用

            // 複檢實付金額
            BigDecimal reAmtPay = ((updateForm.getReFees() == null) ? new BigDecimal(0) : updateForm.getReFees());
            if (updateForm.getNonreFees() != null)
                reAmtPay = reAmtPay.subtract(updateForm.getNonreFees());
            updateCaseData.setReAmtPay(reAmtPay); // 複檢實付金額

            updateCaseData.setNotifyForm(updateForm.getNotifyForm()); // 核定通知書格式
            updateCaseData.setBenIdnNo(updateForm.getBenIdnNo()); // 受益人身分證號
            updateCaseData.setBenName(updateForm.getBenName()); // 受益人姓名
            updateCaseData.setBenBrDate((StringUtils.length(updateForm.getBenBrDate()) == 7 || StringUtils.equals(StringUtils.substring(updateForm.getBenBrDate(), 0, 1), "-")) ? DateUtility.changeDateType(updateForm.getBenBrDate()) : updateForm.getBenBrDate()); // 受益人出生日期

            // 受益人性別
            if (StringUtils.equals(updateForm.getBenNationTyp(), "1"))
                updateCaseData.setBenSex(StringUtils.substring(updateForm.getBenIdnNo(), 1, 2)); // 受益人性別
            else
                updateCaseData.setBenSex(updateForm.getBenSex()); // 受益人性別

            updateCaseData.setBenNationTyp(updateForm.getBenNationTyp()); // 受益人國籍別

            // 受益人國籍
            if (StringUtils.equals(updateForm.getBenNationTyp(), "1"))
                updateCaseData.setBenNationCode("000"); // 受益人國籍
            else
                updateCaseData.setBenNationCode(updateForm.getBenNationCode()); // 受益人國籍

            updateCaseData.setBenEvtRel(updateForm.getBenEvtRel()); // 受益人與事故者關係
            updateCaseData.setTel1(updateForm.getTel1()); // 電話1
            updateCaseData.setTel2(updateForm.getTel2()); // 電話2
            updateCaseData.setCommTyp(updateForm.getCommTyp()); // 通訊地址別

            // 通訊地址
            if (StringUtils.equals(updateForm.getCommTyp(), "1")) {
                // 同戶籍地
                List<Cvldtl> cvldtlList = reviewFeeService.getReviewFeeReceiptCommAddrData(updateForm.getBenIdnNo(), updateForm.getBenBrDate());
                if (cvldtlList != null) {
                    for (Cvldtl cvldtlData : cvldtlList) {
                        updateCaseData.setCommZip(cvldtlData.getHzip()); // 通訊郵遞區號
                        updateCaseData.setCommAddr(cvldtlData.getHaddr()); // 通訊地址
                    }
                }
            }
            else {
                // 現住址
                updateCaseData.setCommZip(updateForm.getCommZip()); // 通訊郵遞區號
                updateCaseData.setCommAddr(updateForm.getCommAddr()); // 通訊地址
            }

            updateCaseData.setPayTyp(updateForm.getPayTyp()); // 給付方式

            // 金融機構名稱 金融機構總代號 分支代號 銀行帳號 戶名 處理
            if (StringUtils.equals(updateForm.getPayTyp(), "1")) { // 匯入銀行帳戶
            	Bcbpf bankData = reviewFeeService.getBankData(updateForm.getAccountNo1());
                StringBuffer bankName = new StringBuffer("");

                if (bankData != null)
                    bankName.append(StringUtils.defaultString(bankData.getBnkNme()));

                updateCaseData.setBankName(bankName.toString()); // 金融機構名稱
                updateCaseData.setPayBankId(StringUtils.substring(updateForm.getAccountNo1(), 0, 3)); // 金融機構總代號
                updateCaseData.setBranchId(StringUtils.substring(updateForm.getAccountNo1(), 3, 7)); // 分支代號
                updateCaseData.setPayeeAcc(StringUtils.leftPad(updateForm.getAccountNo2(), 14, "0")); // 銀行帳號
                updateCaseData.setAccName(updateForm.getAccName()); // 戶名
            }
            else if (StringUtils.equals(updateForm.getPayTyp(), "2")) { // 匯入郵局帳號
                updateCaseData.setBankName(StringUtils.defaultString(reviewFeeService.getPostNameBy(updateForm.getAccountNo1()))); // 金融機構名稱
                updateCaseData.setPayBankId(StringUtils.substring(updateForm.getAccountNo1(), 0, 3)); // 金融機構總代號
                updateCaseData.setBranchId(StringUtils.substring(updateForm.getAccountNo1(), 3, 7)); // 分支代號

                String accountNo1 = StringUtils.defaultString(updateForm.getAccountNo1());
                String accountNo2 = StringUtils.defaultString(updateForm.getAccountNo2());
                if (StringUtils.equals(accountNo1, "7000010"))
                    accountNo2 = StringUtils.leftPad(accountNo2, 8, "0");
                else if (StringUtils.equals(accountNo1, "7000021"))
                    accountNo2 = StringUtils.leftPad(accountNo2, 14, "0");

                updateCaseData.setPayeeAcc(accountNo2); // 銀行帳號
                updateCaseData.setAccName(updateForm.getAccName()); // 戶名
            }
            else if (StringUtils.equals(updateForm.getPayTyp(), "5") || StringUtils.equals(updateForm.getPayTyp(), "6")) { // 國外匯款 大陸地區匯款
                updateCaseData.setBankName(updateForm.getBankName()); // 金融機構名稱
                updateCaseData.setPayBankId(StringUtils.substring(updateForm.getAccountNo1(), 0, 3)); // 金融機構總代號
                updateCaseData.setBranchId(StringUtils.substring(updateForm.getAccountNo1(), 3, 7)); // 分支代號
                updateCaseData.setPayeeAcc(StringUtils.substring(updateForm.getAccountNo1(), 7, 21)); // 銀行帳號
                updateCaseData.setAccName(updateForm.getAccName()); // 戶名
            }

            caseData.setModifyCaseData(updateCaseData);
            // ]

            // 更新存檔
            reviewFeeService.updateReviewFeeReceiptData(userData, caseData, updateForm);

            // 設定更新成功訊息
            saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(caseData.getApNo()));

            // 取得修改後的案件資料
            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(updateForm.getApNo1())) + StringUtils.trimToEmpty(updateForm.getApNo2()) + StringUtils.trimToEmpty(updateForm.getApNo3()) + StringUtils.trimToEmpty(updateForm.getApNo4());
            caseData = reviewFeeService.getReviewFeeReceiptDataForUpdate(apNo, updateForm.getQryIdnNo());

            // 更新 Session 中的 CaseData 資料
            CaseSessionHelper.setReviewFeeReceiptCase(caseData, request);

            // Form 值重置
            updateForm.clearFields();

            log.debug("執行 複檢費用 - 複檢費用受理作業 - 修改 Detail 頁面 ReviewFeeReceiptActions.doUpdate() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定存檔失敗訊息
            log.error("ReviewFeeReceiptActions.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d013c.jsp) <br>
     * 按下「刪除」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用受理作業 - 修改 Detail 頁面 ReviewFeeReceiptActions.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeReceiptForm deleteForm = (ReviewFeeReceiptForm) form;

        try {
            // 自 Session 中取出 Case 資料
            ReviewFeeReceiptCase caseData = CaseSessionHelper.getReviewFeeReceiptCase(request);

            if (caseData == null) {
                // 設定刪除失敗訊息
                saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
                return mapping.findForward(FORWARD_QUERY_PAGE);
            }

            // 刪除存檔
            reviewFeeService.deleteReviewFeeReceiptData(userData, deleteForm.getReApNo(), deleteForm.getApSeq());

            // 設定刪除成功訊息
            saveMessages(session, DatabaseMessageHelper.getReceiptDeleteSuccessMessage(caseData.getApNo()));

            // 取得刪除後的案件資料
            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(deleteForm.getApNo1())) + StringUtils.trimToEmpty(deleteForm.getApNo2()) + StringUtils.trimToEmpty(deleteForm.getApNo3()) + StringUtils.trimToEmpty(deleteForm.getApNo4());
            caseData = reviewFeeService.getReviewFeeReceiptDataForUpdate(apNo, deleteForm.getQryIdnNo());

            // 更新 Session 中的 CaseData 資料
            CaseSessionHelper.setReviewFeeReceiptCase(caseData, request);

            // Form 值重置
            deleteForm.clearFields();

            log.debug("執行 複檢費用 - 複檢費用受理作業 - 修改 Detail 頁面 ReviewFeeReceiptActions.doDelete() 完成 ... ");

            if (caseData !=null && caseData.getDetailList() != null && caseData.getDetailList().size() > 0)
                return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);
            else
                return mapping.findForward(FORWARD_QUERY_PAGE);
        }
        catch (Exception e) {
            // 設定存檔失敗訊息
            log.error("ReviewFeeReceiptActions.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d013c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToListPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ReviewFeeReceiptForm updateForm = (ReviewFeeReceiptForm) form;

        // 自 Session 中取出 Case 資料
        ReviewFeeReceiptCase caseData = CaseSessionHelper.getReviewFeeReceiptCase(request);

        caseData.setAddCaseData(null);
        caseData.setModifyCaseData(null);

        // Form 值重置
        updateForm.clearFields();

        return mapping.findForward(FORWARD_LIST_PAGE);
    }

    /**
     * 檢核帳號是否合法
     * 
     * @param payTyp 給付方式
     * @param accountNo1 帳號 (前)
     * @param accountNo2 帳號 (後)
     * @return
     */
    private boolean isValidAccountNo(String payTyp, String accountNo1, String accountNo2) {
        if (StringUtils.equals(payTyp, "1")) { // 匯入銀行帳戶
            if (StringUtils.isBlank(accountNo1) || StringUtils.isBlank(accountNo2))
                return false;

            // 帳號 (前) 為 7 碼 (後) 為 14 碼
            if (StringUtils.length(accountNo1) != 7)
                return false;

            // 帳號 必須為數字
            if (!StringUtils.isNumeric(accountNo1) || !StringUtils.isNumeric(accountNo2))
                return false;

            // 檢查 帳號 (前: 1 ~ 3 碼) 的輸入的資料值不可為 '700'
            if (StringUtils.equals(StringUtils.substring(accountNo1, 0, 3), "700"))
                return false;

            // 檢查 帳號 是否有在銀行檔裡
            if (reviewFeeService.getBankData(accountNo1) == null)
                return false;
        }
        else if (StringUtils.equals(payTyp, "2")) { // 匯入郵局帳號
            if (StringUtils.isBlank(accountNo1) || StringUtils.isBlank(accountNo2))
                return false;

            // 帳號 必須為數字
            if (!StringUtils.isNumeric(accountNo1) || !StringUtils.isNumeric(accountNo2))
                return false;

            // 帳號 (前) 限定只能輸入 '7000010' 或 '7000021'
            if (!StringUtils.equals(accountNo1, "7000010") && !StringUtils.equals(accountNo1, "7000021"))
                return false;

            // 當 帳號 (前) 輸入 '7000010' 時, 帳號(後) 需限制輸入的長度為 8 碼;
            // 帳號 (前) 輸入 '7000021' 時, 帳號 (後) 需限制輸入的長度為 14 碼
            if (StringUtils.equals(accountNo1, "7000010")) {
                if (StringUtils.length(accountNo2) > 8) // 不足 8 碼於存檔時會補 0 故只需判斷大於 8 碼的情況
                    return false;

                // 檢查 帳號 (後) 的值是否正確
                // 公式:
                // a[7] = ((11-((a[0]*2+a[1]*3+a[2]*4+a[3]*5+a[4]*6+a[5]*7+a[6]*8)%11))%10)
                // [
                char[] c = StringUtils.leftPad(accountNo2, 8, "0").toCharArray();
                int[] d = new int[8];

                for (int i = 0; i < c.length; i++)
                    d[i] = Integer.parseInt(String.valueOf(c[i]));

                if (d[7] != ((11 - ((d[0] * 2 + d[1] * 3 + d[2] * 4 + d[3] * 5 + d[4] * 6 + d[5] * 7 + d[6] * 8) % 11)) % 10))
                    return false;
                // ]
            }
            else {
                if (StringUtils.length(accountNo2) > 14) // 不足 14 碼於存檔時會補 0 故只需判斷大於 14 碼的情況
                    return false;

                // 檢查 帳號 (後) 的值是否正確
                // 公式:
                // 先檢查 a[6] = ((11-((a[0]*2+a[1]*3+a[2]*4+a[3]*5+a[4]*6+a[5]*7)%11))%10)
                // 通過後
                // 再檢查 a[13] = ((11-((a[7]*2+a[8]*3+a[9]*4+a[10]*5+a[11]*6+a[12]*7)%11))%10)
                // [
                char[] c = StringUtils.leftPad(accountNo2, 14, "0").toCharArray();
                int[] d = new int[14];

                for (int i = 0; i < c.length; i++)
                    d[i] = Integer.parseInt(String.valueOf(c[i]));

                if (d[6] != ((11 - ((d[0] * 2 + d[1] * 3 + d[2] * 4 + d[3] * 5 + d[4] * 6 + d[5] * 7) % 11)) % 10))
                    return false;

                if (d[13] != ((11 - ((d[7] * 2 + d[8] * 3 + d[9] * 4 + d[10] * 5 + d[11] * 6 + d[12] * 7) % 11)) % 10))
                    return false;
                // ]
            }
        }
        else if (StringUtils.equals(payTyp, "5") || StringUtils.equals(payTyp, "6")) { // 國外匯款 大陸地區匯款
            if (StringUtils.isBlank(accountNo1) || StringUtils.length(accountNo1) > 21)
                return false;

            // 須為英數字
            if (!StringUtils.isAlphanumeric(accountNo1))
                return false;
        }

        return true;
    }

    public void setReviewFeeService(ReviewFeeService reviewFeeService) {
        this.reviewFeeService = reviewFeeService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }

}
