package tw.gov.bli.ba.update.actions;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bachkfile;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01Case;
import tw.gov.bli.ba.rpt.report.DisableReviewRpt01Kind36Report;
import tw.gov.bli.ba.rpt.report.DisableReviewRpt01Report;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.BadaprDataCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCase;
import tw.gov.bli.ba.update.forms.DisabledPayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.DisabledPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 更正作業 - 受款人更正 - 失能年金受款人資料更正 (BAMO0D182C)
 * 
 * @author Azuritul
 */
public class DisabledPayeeDataUpdateListAction extends DisabledPayeeDataUpdateAction {

    private static Log log = LogFactory.getLog(DisabledPayeeDataUpdateListAction.class);
    // 更正作業 - 失能受款人資料更正 - 失能受款人資料新增作業頁面
    private static final String FORWARD_INSERT_DISABLED_PAYEE_DETAIL = "insertPayeeData";
    // 更正作業 - 失能受款人資料更正 - 失能受款人資料修改作業頁面
    private static final String FORWARD_UPDATE_DISABLED_PAYEE_DATA = "updatePayeeData";
    // 更正作業 - 失能受款人資料更正 - 失能受款人資料可領金額作業頁面
    private static final String FORWARD_QUERY_DISABLED_PAYEE_DATA = "queryPayeeData";
    // 更正作業 - 失能受款人資料更正 - 失能受款人資料清單頁面
    private static final String FORWARD_MODIFY_DISABLED_PAYEE_DATA_LIST = "modifyPayeeDataList";

    private static final String LOG_INFO_DOPREPAREINSERT_START = "執行 失能年金受款人資料更正作業 - 清單頁面 DisabledPayeeDataUpdateListAction.doPrepareInsert() 開始 ... ";
    private static final String LOG_INFO_DOPREPAREINSERT_ERROR = "執行 失能年金受款人資料更正作業 - 清單頁面 DisabledPayeeDataUpdateListAction.doPrepareInsert() 發生錯誤:";

    private static final String LOG_INFO_DOPREPAREUPDATE_START = "執行 失能年金受款人資料更正作業 - 清單頁面 DisabledPayeeDataUpdateListAction.doPrepareUpdate() 開始 ... ";
    private static final String LOG_INFO_DOPREPAREUPDATE_ERROR = "執行 失能年金受款人資料更正作業 - 清單頁面 DisabledPayeeDataUpdateListAction.doPrepareUpdate() 發生錯誤:";

    private static final String LOG_INFO_DODELETE_START = "執行 失能年金受款人資料更正作業 - 刪除 DisabledPayeeDataUpdateListAction.doDelete() 開始 ... ";
    private static final String LOG_INFO_DODELETE_CHECKMARK_FAIL = "執行 失能年金受款人資料更正作業 - 刪除 DisabledApplicationDataUpdateAction.doDelete() 即時編審發生錯誤:";
    private static final String LOG_INFO_DODELETE_CHECKMARK_RESULT = "執行 失能年金受款人資料更正作業 - 刪除 - 呼叫即時編審結果... ";
    private static final String LOG_INFO_DODELETE_ERROR = "執行 失能年金受款人資料更正作業 - 刪除 DisabledPayeeDataUpdateListAction.doDelete() 發生錯誤:";

    private static final String LOG_INFO_DOQUERY_START = "執行 失能年金受款人資料更正作業 - 可領金額頁面 DisabledPayeeDataUpdateListAction.doQuery() 開始 ... ";
    private static final String LOG_INFO_DOQUERY_ERROR = "執行 失能年金受款人資料更正作業 - 可領金額頁面 DisabledPayeeDataUpdateListAction.doQuery() 發生錯誤:";

    private static final String LOG_INFO_DOPRINT_START = "執行 失能年金受款人資料更正作業 - 檢視受理/審核清單 DisabledPayeeDataUpdateListAction.doPrint() 開始 ... ";
    private static final String LOG_INFO_DOPRINT_COMPLETE = "執行 失能年金受款人資料更正作業 - 檢視受理/審核清單 DisabledPayeeDataUpdateListAction.doPrint() 完成 ... ";
    private static final String LOG_INFO_DOPRINT_ERROR = "執行 失能年金受款人資料更正作業 - 檢視受理/審核清單 DisabledPayeeDataUpdateListAction.doPrint() 發生錯誤:";

    private static final String LOG_INFO_DOBACKLIST_START = "執行 失能年金受款人資料更正作業 - 返回 DisabledPayeeDataUpdateListAction.doBackList() 開始 ... ";
    private static final String LOG_INFO_DOBACKLIST_ERROR = "執行 失能年金受款人資料更正作業 - 返回 DisabledPayeeDataUpdateListAction.doBackList() 發生錯誤:";

    /**
     * 更正作業 - 失能年金受款人資料更正作業 - 清單頁面 (bamo0d182c.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPREPAREINSERT_START);
        DisabledPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getDisabledPayeeDataUpdateQueryForm(request);

        // 清除明細資料畫面
        FormSessionHelper.removeDisabledPayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removeDisabledPayeeDataUpdateDetailCase(request);

        try {
            List<BaappbaseDataUpdateCase> applyList = fetchDisabledPayeeDataUpdateCaseList(queryForm.getApNo());
            if (applyList.isEmpty()) {
                saveMessages(request.getSession(), DatabaseMessageHelper.getNoResultForApNoMessage());
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }

            setNecessaryDropDownMenu(request, applyList.get(0), null, null);

            // 檢查具名領取下拉選單的size,size <= 0則畫面將具名領取選單隱藏起來
            boolean toHideCoreceiveNameListDropDownMenu = (getCoreceiveNameListDropDownMenu(request)).isEmpty();
            request.getSession().setAttribute("toHideCoreceiveNameListDropDownMenu", toHideCoreceiveNameListDropDownMenu);

            CaseSessionHelper.setDisabledPayeeDataUpdateDetailCase(null, false, false, "", request);
            return mapping.findForward(FORWARD_INSERT_DISABLED_PAYEE_DETAIL);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOPREPAREINSERT_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * [目前未使用] 更正作業 - 失能年金受款人資料更正作業 - 清單頁面 (bamo0d182c.jsp) <br>
     * 按下「新增繼承人」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    /*
     * public ActionForward doPrepareInsertInherit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) { log.debug("執行 更正作業 - 失能年金受款人資料更正 - 清單頁面 DisabledPayeeDataUpdateListAction.doPrepareInsertInherit()
     * 開始 ... "); DisabledPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getDisabledPayeeDataUpdateQueryForm(request); // 清除明細資料畫面 FormSessionHelper.removeDisabledPayeeDataUpdateDetailForm(request);
     * CaseSessionHelper.removeDisabledPayeeDataUpdateDetailCase(request); //取得上層的baappbaseId //取得該主檔資料，儲存於Session以供繼承人使用 String baappbaseId = StringUtils.defaultString(request.getParameter("baappbaseId")); BaappbaseDataUpdateCase inheritFrom =
     * updateService.getBaappbaseDetail(new BigDecimal(baappbaseId), null, null, null); request.getSession().setAttribute(ConstantKey.DISABLED_PAYEE_DATA_INHERIT_FROM, inheritFrom); try { List<BaappbaseDataUpdateCase> applyList =
     * updateService.selectPayeeDataForInsert(queryForm.getApNo()); if (applyList.size() <= 0) { // MSG：W1003-無此受理號碼或尚未產生核定資料！ saveMessages(request.getSession(), DatabaseMessageHelper.getNoResultForApNoMessage()); return
     * mapping.findForward(ConstantKey.FORWARD_BACK); } setNecessaryDropDownMenu(request, applyList.get(0), null, null); // 控制畫面身份查核年月與具名領取是否顯示 // displayIdnChkYearMonth > 0 則顯示 // Integer displayIdnChkYearMonth =
     * updateService.selectForPayeeDataUpdateQ1(applyList.get(0).getApNo(), applyList.get(0).getIssuYm()); // Integer displayReceiveTogether = updateService.selectForPayeeDataUpdateQ2(applyList.get(0).getBaappbaseId(), applyList.get(0).getApNo()); //
     * 控制畫面具名領取enable;disabled String accSeqNoControl = "0";//updateService.getAccSeqNoAmt(detailData.getApNo(), detailData.getSeqNo()); CaseSessionHelper.setDisabledPayeeDataUpdateDetailCase(null, null, null, accSeqNoControl, request); return
     * mapping.findForward(FORWARD_INSERT_DISABLED_INHERIT_PAYEE_DETAIL); } catch (Exception e) { // 設定查詢失敗訊息 log.error("DisabledPayeeDataUpdateListAction.doPrepareInsertInherit() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
     * saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage()); return mapping.findForward(ConstantKey.FORWARD_FAIL); } }
     */

    /**
     * 在 更正作業 - 失能年金受款人資料更正作業 - 清單頁面 (bamo0d182c.jsp) <br>
     * 按下「修改」的動作 (修改受款人)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPREPAREUPDATE_START);

        // 清除明細資料畫面
        FormSessionHelper.removeDisabledPayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removeDisabledPayeeDataUpdateDetailCase(request);

        try {
            String baappbaseId = StringUtils.defaultString(request.getParameter("baappbaseId"));

            // 重新抓取資料
            Baappbase detailData = updateService.getDisabledPayeeDataUpdateForUpdateCaseBy(baappbaseId);
            // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
            DisabledPayeeDataUpdateDetailForm updateForm = new DisabledPayeeDataUpdateDetailForm();
            BeanUtility.copyPropertiesForUpdate(updateForm, detailData, ConstantKey.OLD_FIELD_PREFIX);

            // 變更日期為民國年
            updateForm.setAppDate(convertToChineseDate(updateForm.getAppDate()));
            updateForm.setBenBrDate(convertToChineseDate(updateForm.getBenBrDate()));
            updateForm.setBenDieDate(convertToChineseDate(updateForm.getBenDieDate()));
            updateForm.setEvtDieDate(convertToChineseDate(updateForm.getEvtDieDate()));
            updateForm.setGrdBrDate(convertToChineseDate(updateForm.getGrdBrDate()));
            updateForm.setIdnChkYm(convertToChineseDate(updateForm.getIdnChkYm()));

            // 查核年月 分成兩個欄位
            if (StringUtils.defaultString(detailData.getIdnChkYm()).trim().length() != 0) {
                updateForm.setIdnChkYear(DateUtility.changeWestYearMonthType(detailData.getIdnChkYm()).substring(0, 3));
                updateForm.setIdnChkMonth(DateUtility.changeWestYearMonthType(detailData.getIdnChkYm()).substring(3, 5));
            }

            // 帳號 分成兩個欄位或一個欄位
            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(detailData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(detailData.getPayTyp())) {
                updateForm.setPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId());
                updateForm.setOldPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId());
                detailData.setPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId());
            }
            else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(detailData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(detailData.getPayTyp())) {
                updateForm.setPayAccount(detailData.getPayBankId() + detailData.getBranchId() + detailData.getPayEeacc());
                updateForm.setOldPayAccount(detailData.getPayBankId() + detailData.getBranchId() + detailData.getPayEeacc());
            }

            List<BaappbaseDataUpdateCase> applyList = fetchDisabledPayeeDataUpdateCaseList(detailData.getApNo());
            setNecessaryDropDownMenu(request, applyList.get(0), baappbaseId, updateForm.getBenEvtRel());

            // 傳入受款人的APNO,SEQNO,ISSUYM以確定是否要顯示身份查核年月
            boolean toDisplayIdnCheck = updateService.displayIdnChkYearMonth(detailData.getApNo(), detailData.getSeqNo(), detailData.getIssuYm());
            // 檢查是否有共同具名領取的受款人, 若為true 則具名領取的選項及下拉選單要設為disable
            boolean toDisableCoreceiver = updateService.hasCoreceiver(detailData.getApNo(), detailData.getSeqNo());
            // 檢查具名領取下拉選單的size,size <= 0則畫面將具名領取選單隱藏起來
            boolean toHideCoreceiveNameListDropDownMenu = (getCoreceiveNameListDropDownMenu(request)).isEmpty();
            // 設定事故者的死亡日期是否可修改
            boolean isEvtDieDateUpdatable = isEvtDieDateUpdatable(detailData.getApNo());

            // 取得受款人編審註記
            Map<String, ArrayList<Bachkfile>> mapChkMk = fetchChkmkList(detailData.getApNo(), detailData.getSeqNo());

            FormSessionHelper.setDisabledPayeeDataUpdateDetailForm(updateForm, request);
            request.getSession().setAttribute("checkMarkMap", mapChkMk);
            request.getSession().setAttribute("toHideCoreceiveNameListDropDownMenu", toHideCoreceiveNameListDropDownMenu);
            request.getSession().setAttribute("isEvtDieDateUpdatable", isEvtDieDateUpdatable);
            CaseSessionHelper.setDisabledPayeeDataUpdateDetailCase(detailData, toDisplayIdnCheck, toDisableCoreceiver, "", request);

            return mapping.findForward(FORWARD_UPDATE_DISABLED_PAYEE_DATA);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOPREPAREUPDATE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * [目前未使用] 在 更正作業 - 失能年金受款人資料更正作業 - 清單頁面 (bamo0d182c.jsp) <br>
     * 按下「修改」的動作 (修改繼承人)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    /*
     * public ActionForward doPrepareUpdateHeir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) { log.debug("執行 更正作業 - 失能年金受款人資料更正作業 - 修改繼承人頁面 DisabledPayeeDataUpdateListAction.doPrepareUpdateHeir()
     * 開始 ... "); // 清除明細資料畫面 FormSessionHelper.removeDisabledPayeeDataUpdateDetailForm(request); CaseSessionHelper.removeDisabledPayeeDataUpdateDetailCase(request); try { String baappbaseId =
     * StringUtils.defaultString(request.getParameter("baappbaseId")); // 重新抓取資料 - 直接用舊有的SQL即可 Baappbase detailData = updateService.getPayeeDataUpdateForUpdateCaseBy(baappbaseId); // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request
     * Scope DisabledPayeeDataUpdateDetailForm updateForm = new DisabledPayeeDataUpdateDetailForm(); BeanUtility.copyPropertiesForUpdate(updateForm, detailData, ConstantKey.OLD_FIELD_PREFIX); // 變更日期為民國年 if
     * (StringUtils.isNotBlank(updateForm.getAppDate())) { updateForm.setAppDate(DateUtility.changeDateType(updateForm.getAppDate().trim())); } if (StringUtils.isNotBlank(updateForm.getBenBrDate())) {
     * updateForm.setBenBrDate(DateUtility.changeDateType(updateForm.getBenBrDate().trim())); } if (StringUtils.isNotBlank(updateForm.getGrdBrDate())) { updateForm.setGrdBrDate(DateUtility.changeDateType(updateForm.getGrdBrDate().trim())); } // 帳號
     * 分成兩個欄位或一個欄位 if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(detailData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(detailData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_8).equals(detailData.getPayTyp())) {
     * updateForm.setPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId()); } else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(detailData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(detailData.getPayTyp())) {
     * updateForm.setPayAccount(detailData.getPayBankId() + detailData.getBranchId() + detailData.getPayEeacc()); } List<BaappbaseDataUpdateCase> applyList = fetchDisabledPayeeDataUpdateCaseList(detailData.getApNo());
     * setNecessaryDropDownMenu(request, applyList.get(0), baappbaseId, updateForm.getBenEvtRel()); // 控制畫面身份查核年月與具名領取是否顯示 // displayIdnChkYearMonth > 0 則顯示, 新增時不顯示身分查核年月 // diplayReceiveTogether > 0 //Integer displayIdnChkYearMonth =
     * updateService.selectForPayeeDataUpdateQ1(applyList.get(0).getApNo(), applyList.get(0).getIssuYm()); //Integer displayReceiveTogether = updateService.selectForPayeeDataUpdateQ2(applyList.get(0).getBaappbaseId(), applyList.get(0).getApNo()); //
     * 控制畫面具名領取enable;disabled //String accSeqNoControl = updateService.getAccSeqNoAmt(detailData.getApNo(), detailData.getSeqNo()); FormSessionHelper.setDisabledPayeeDataUpdateDetailForm(updateForm, request);
     * CaseSessionHelper.setDisabledPayeeDataUpdateDetailCase(detailData, null, null, "", request); return mapping.findForward(FORWARD_UPDATE_DISABLED_INHERIT_PAYEE_DATA); } catch (Exception e) { // 設定查詢失敗訊息
     * log.error("DisabledPayeeDataUpdateListAction.doPrepareUpdateHeir() 發生錯誤:" + ExceptionUtility.getStackTrace(e)); saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage()); return
     * mapping.findForward(ConstantKey.FORWARD_FAIL); } }
     */

    /**
     * 更正作業 - 失能年金受款人資料更正作業 - 清單頁面 (bamo0d182c.jsp) <br>
     * 按下「刪除」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DODELETE_START);
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        // 清除明細資料畫面
        FormSessionHelper.removeDisabledPayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removeDisabledPayeeDataUpdateDetailCase(request);

        try {
            String baappbaseId = StringUtils.defaultString(request.getParameter("baappbaseId"));
            Baappbase detailData = updateService.getPayeeDataUpdateForUpdateCaseBy(baappbaseId);
            updateService.deleteDisabledPayeeDataUpdate(userData, detailData);

            // 呼叫即時編審
            String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
            try {
                returnCode = callCheckMarkWebService(detailData.getApNo());
            }
            catch (Exception e) {
                log.error(LOG_INFO_DODELETE_CHECKMARK_FAIL + ExceptionUtility.getStackTrace(e));
            }

            DisabledPayeeDataUpdateQueryForm listForm = FormSessionHelper.getDisabledPayeeDataUpdateQueryForm(request);
            CaseSessionHelper.setDisabledPayeeDataUpdateQueryCase(fetchDisabledPayeeDataUpdateCaseList(listForm.getApNo()), request);

            log.debug(LOG_INFO_DODELETE_CHECKMARK_RESULT + returnCode);
            if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                saveMessages(request.getSession(), CustomMessageHelper.getCheckMarkFailMessage());
                return mapping.findForward(FORWARD_MODIFY_DISABLED_PAYEE_DATA_LIST);
            }

            saveMessages(request.getSession(), DatabaseMessageHelper.getDeleteSuccessMessage());
            return mapping.findForward(FORWARD_MODIFY_DISABLED_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DODELETE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 更正作業 - 失能年金受款人資料更正作業 - 可領金額頁面 (bamo0d182c.jsp) <br>
     * 按下「可領金額」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOQUERY_START);

        // 取得查詢List
        List<BaappbaseDataUpdateCase> queryList = CaseSessionHelper.getDisabledPayeeDataUpdateQueryCase(request);
        BaappbaseDataUpdateCase queryData = queryList.get(0);
        try {
            // 取得受款人可領金額資料
            BadaprDataCase caseData = updateService.selectDisabledPayeeDataForBadapr(queryData.getApNo());
            CaseSessionHelper.setDisabledPayeeDataForBadaprCase(caseData, request);
            return mapping.findForward(FORWARD_QUERY_DISABLED_PAYEE_DATA);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOQUERY_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 失能年金受款人資料更正作業 - 檢視受理/審核清單 列印報表 (bamo0d182c.jsp) <br>
     * 按下「檢視受理/審核清單」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPRINT_START);
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 取得查詢List
            List<BaappbaseDataUpdateCase> queryList = CaseSessionHelper.getDisabledPayeeDataUpdateQueryCase(request);
            BaappbaseDataUpdateCase queryData = queryList.get(0);
            List<DisableReviewRpt01Case> caseList = rptService.getDisableReviewRpt01DataBy(queryData.getApNo(), queryData.getApNo());

            if (queryList == null || queryList.size() == 0) {
                saveMessages(request.getSession(), CustomMessageHelper.getReportGenerateFailMessage());
                return mapping.findForward(FORWARD_MODIFY_DISABLED_PAYEE_DATA_LIST);
            }

            if (caseList == null || caseList.size() == 0) {
                saveMessages(request.getSession(), CustomMessageHelper.getReportGenerateFailMessage());
                return mapping.findForward(FORWARD_MODIFY_DISABLED_PAYEE_DATA_LIST);
            }
            
            if(caseList != null){
                for(int i = 0 ; i<caseList.size(); i++){
                	//放入得請領起始年月formatChineseYearMonthString
                	DisableReviewRpt01Case caseDataCase = (DisableReviewRpt01Case) caseList.get(i);
                	// 處理日期前
                    String ableapsYmBefor = rptService.getAbleapsYmForDisabledCheckMarkLevelAdjust(caseDataCase.getApNo(),caseDataCase.getBaappbaseId());
                    
                    if(StringUtils.isNotBlank(ableapsYmBefor)){
                    // 處理日期後 放入list內各筆資料之AbleapsYm 得請領起始年月
                    String ableapsYmAfter = DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(ableapsYmBefor),false);
                    caseDataCase.setAbleapsYm(ableapsYmAfter);
                    }
                }
                }

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
            
            DisableReviewRpt01Case caseObj = new DisableReviewRpt01Case();
            
            caseObj = (DisableReviewRpt01Case)caseList.get(0);   
            
            if("36".equals(caseObj.getPayKind())){
                DisableReviewRpt01Kind36Report report = new DisableReviewRpt01Kind36Report();
                baoOutput = report.doReport(userData, caseList); 
            } else {
                DisableReviewRpt01Report report = new DisableReviewRpt01Report();
                baoOutput = report.doReport(userData, caseList);                        
            }

            String printDate = DateUtility.getNowChineseDate();
            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            response.setHeader("Content-disposition", "attachment; filename=\"DisableReviewRpt01_" + printDate + ".pdf" + "\"");
            response.setHeader("Content-Encoding", "pdf");
            response.setContentType("application/pdf"); // 設定MIME
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentLength(baoOutput.size());

            // 傳回 Client 端
            ServletOutputStream sout = null;
            try {
                sout = response.getOutputStream();
                baoOutput.writeTo(sout);
                sout.flush();
            }
            catch (Exception e) {
            }
            finally {
                if (baoOutput != null)
                    baoOutput.close();
                if (sout != null)
                    sout.close();
            }
            log.debug(LOG_INFO_DOPRINT_COMPLETE);

            return null;
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOPRINT_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MODIFY_DISABLED_PAYEE_DATA_LIST);
        }
    }

    /**
     * 更正作業 - 失能年金受款人資料更正作業 (bamo0d182c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        cleanDataInSession(request);
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 更正作業 - 失能年金受款人資料更正作業 - 可領金額 (bamo0d184c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOBACKLIST_START);
        try {
            // 更新清單資料
            DisabledPayeeDataUpdateQueryForm listForm = FormSessionHelper.getDisabledPayeeDataUpdateQueryForm(request);
            CaseSessionHelper.setDisabledPayeeDataUpdateQueryCase(fetchDisabledPayeeDataUpdateCaseList(listForm.getApNo()), request);

            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_MODIFY_DISABLED_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOBACKLIST_ERROR + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 抓取畫面下拉選單之資料
     * 
     * @param request
     * @param dataUpdateCase
     * @param selfBaappbaseId
     */
    private void setNecessaryDropDownMenu(HttpServletRequest request, BaappbaseDataUpdateCase dataUpdateCase, String selfBaappbaseId, String benEvtRel) {
        // 取得給付方式 下拉選單
        setPayTypeDropDownMenu(request, dataUpdateCase.getBenEvtRel());
        // 取得具名領取 下拉選單
        setCoreceiveNameListDropDownMenu(request, dataUpdateCase.getBaappbaseId(), dataUpdateCase.getApNo(), selfBaappbaseId);
        // 取得國籍清單
        setCountryDropDownMenu(request);
        // 視與事故著關係為何，取得關係 下拉選單(只要自然人部份)
        setRelationDropDownMenu(request, benEvtRel);
        // 取得結案原因 下拉選單
        setCloseCauseDropDownMenu(request, dataUpdateCase.getApNo().substring(0, 1));
    }

}
