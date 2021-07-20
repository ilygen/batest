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
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.forms.DisabledPayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.DisabledPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 更正作業 - 失能年金受款人資料更正 - Detail
 * @author Azuritul
 *
 */
public class DisabledPayeeDataUpdateDetailAction extends DisabledPayeeDataUpdateAction {

    private static Log log = LogFactory.getLog(DisabledPayeeDataUpdateDetailAction.class);

    // 更正作業 - 失能年金受款人資料更正 - 失能年金受款人資料清單頁面
    private static final String FORWARD_MODIFY_DISABLED_PAYEE_DATA_LIST = "modifyDisabledPayeeDataList";

    private static final String LOG_INFO_DOSAVE_START = "執行 失能年金受款人資料更正作業 - 新增頁面 DisabledPayeeDataUpdateDetailAction.doSave() 開始 ... ";
    private static final String LOG_INFO_DOSAVE_COMPLETE = "執行 失能年金受款人資料更正作業 - 新增頁面  DisabledPayeeDataUpdateDetailAction.doSave() 完成 ... ";
    private static final String LOG_INFO_DOSAVE_ERROR = "執行 失能年金受款人資料更正作業 - 新增頁面 DisabledPayeeDataUpdateDetailAction.doSave() 發生錯誤:";
    private static final String LOG_INFO_DOSAVE_CHECKMARK_FAIL = "執行 失能年金受款人資料更正作業 - 新增頁面 DisabledPayeeDataUpdateDetailAction.doSave() 即時編審發生錯誤:";
    private static final String LOG_INFO_DOSAVE_CHECKMARK_RESULT = "執行 失能年金受款人資料更正作業 - 新增頁面 DisabledPayeeDataUpdateDetailAction.doSave() 呼叫即時編審結果 ... ";
    private static final String LOG_INFO_DOCONFIRM_START = "執行 失能年金受款人資料更正作業 - 修改頁面 DisabledPayeeDataUpdateDetailAction.doConfirm() 開始 ... ";
    private static final String LOG_INFO_DOCONFIRM_COMPLETE = "執行 失能年金受款人資料更正作業 - 修改頁面  DisabledPayeeDataUpdateDetailAction.doConfirm() 完成 ... ";
    private static final String LOG_INFO_DOCONFIRM_ERROR = "執行 失能年金受款人資料更正作業 - 修改頁面 DisabledPayeeDataUpdateDetailAction.doConfirm() 發生錯誤:";
    private static final String LOG_INFO_DOCONFIRM_REFETCH_ERROR = "執行 失能年金受款人資料更正作業 - 修改頁面 DisabledPayeeDataUpdateDetailAction.doConfirm() 重新取得資料時發生錯誤:";
    private static final String LOG_INFO_DOCONFIRM_REFETCH_START = "執行 失能年金受款人資料更正作業 - 修改頁面 DisabledPayeeDataUpdateDetailAction.doConfirm() 修改後重新取出資料開始 ... ";
    private static final String LOG_INFO_DOCONFIRM_CHECKMARK_FAIL = "執行 失能年金受款人資料更正作業 - 修改頁面 DisabledPayeeDataUpdateDetailAction.doConfirm() 即時編審發生錯誤:";
    private static final String LOG_INFO_DOCONFIRM_CHECKMARK_RESULT = "執行 失能年金受款人資料更正作業 - 修改頁面 DisabledPayeeDataUpdateDetailAction.doConfirm() 呼叫即時編審結果... ";
    private static final String LOG_INFO_DOBACKLIST_START = "執行 失能年金受款人資料更正作業 - 各頁面(返回) DisabledPayeeDataUpdateDetailAction.doBackList() 開始 ... ";
    private static final String LOG_INFO_DOBACKLIST_ERROR = "執行 失能年金受款人資料更正作業 - 各頁面(返回) DisabledPayeeDataUpdateDetailAction.doBackList() 發生錯誤:";

    /**
     * 更正作業 - 受款人資料更正作業 - 新增頁面 (bamo0d181a.jsp)
     * <br /> 
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOSAVE_START);
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPayeeDataUpdateDetailForm detailForm = (DisabledPayeeDataUpdateDetailForm) form;

        // 取得查詢List
        List<BaappbaseDataUpdateCase> queryList = CaseSessionHelper.getDisabledPayeeDataUpdateQueryCase(request);
        BaappbaseDataUpdateCase queryData = queryList.get(0);
        
        //將主檔資料存入BAAPPBASE，供後續使用
        Baappbase evtMainData = new Baappbase();
        BeanUtility.copyProperties(evtMainData, queryData);

        try {

            if (isNotValidBankAccount(request, detailForm.getPayCategory(), detailForm.getPayBankIdBranchId(), detailForm.getPayTyp(), detailForm.getPayAccount()))
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);

            if (isGrdAlreadyDead(request, queryData.getApNo(), detailForm.getGrdIdnNo()))
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);

            if (isGrdAlsoEvtOrPayee(request, queryData.getEvtIdnNo(), detailForm.getBenIdnNo(), detailForm.getGrdIdnNo()))
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);

            if (isPayeeExist(request, queryData.getApNo(), detailForm.getBenIdnNo()))
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);

            // 依畫面輸入欄位條件轉換 給付主檔部分欄位
            if (detailForm != null) {
                BaappbaseDataUpdateCase detailData = prepareData(detailForm);
                detailData = updateService.transDisabledPayeeUpdateData(detailData, evtMainData, "I");
                
                
                // 儲存INTERVALMONTH的值
                String sInterValMonth = null;
                if (!detailData.getAccRel().equals("3")){ // 20111107 非共同具領時才做判斷                  
                    if (!detailForm.getPayTyp().equals("5") && !detailForm.getPayTyp().equals("6")){
                        sInterValMonth = "0";
                        //updateService.updateInterValMonth(queryData.getApNo(), "0");                    
                    } else {
                        sInterValMonth = updateService.getInterValMonth(queryData.getApNo(), "0000");
                        if (sInterValMonth == null){
                            sInterValMonth = "";
                        }                    
                        //updateService.updateInterValMonth(queryData.getApNo(), sInterValMonth);                    
                    }                  
                } else {
                    sInterValMonth = detailData.getInterValMonth();
                }
                
                //專戶
                detailData.setSpecialAcc(detailForm.getSpecialAcc());
                if(StringUtils.isNotBlank(detailForm.getSpecialAcc())){
                	detailData.setSpeAccDate(DateUtility.getNowWestDate());
                }
                
                // 存檔
                updateService.insertBaappbaseDataForDisabledPayeeData(userData, queryData, detailData, sInterValMonth);                                          
                
                // 案件類別為 2 或 4 時，呼叫 sp_BA_Get_CIPB                
                if (detailData.getBenEvtRel().equals("2") || detailData.getBenEvtRel().equals("3")
                                || detailData.getBenEvtRel().equals("4") || detailData.getBenEvtRel().equals("5")
                                || detailData.getBenEvtRel().equals("6") || detailData.getBenEvtRel().equals("7")){                
                        if(queryData.getCaseTyp().equals("2") || queryData.getCaseTyp().equals("4")){
                            // 呼叫StoreProcedure sp_BA_Get_CIPB(眷屬身份證號,事故日期【NULL】)
                            updateService.doGetCipb(detailData.getApNo(), detailData.getSeqNo(),detailData.getAppDate(), detailData.getBenIdnNo(), null);
                        }
                }
                
                // 呼叫即時編審
                String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
                try {
                    returnCode = callCheckMarkWebService(detailData.getApNo());
                }
                catch (Exception e) {
                    log.error(LOG_INFO_DOSAVE_CHECKMARK_FAIL + ExceptionUtility.getStackTrace(e));
                }
                log.debug(LOG_INFO_DOSAVE_CHECKMARK_RESULT + returnCode);

                // 更新清單資料
                DisabledPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getDisabledPayeeDataUpdateQueryForm(request);
                CaseSessionHelper.setDisabledPayeeDataUpdateQueryCase(fetchDisabledPayeeDataUpdateCaseList(queryForm.getApNo()), request);

                if (StringUtils.equals(ConstantKey.DO_CHECK_MARK_FAIL, returnCode)) {
                    saveMessages(request.getSession(), CustomMessageHelper.getCheckMarkFailMessage());
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
                }
                saveMessages(request.getSession(), DatabaseMessageHelper.getSaveSuccessMessage());
            }
            
            log.debug(LOG_INFO_DOSAVE_COMPLETE);
            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOSAVE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 更正作業 - 失能年金受款人資料更正作業 - 修改頁面 (bamo0d183c.jsp) <br />
     * 按下「確認」的動作
     * @todo move the logic of query and setting base data to ancestor
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOCONFIRM_START);

        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPayeeDataUpdateDetailForm detailForm = (DisabledPayeeDataUpdateDetailForm) form;
        // 取得查詢List
        List<BaappbaseDataUpdateCase> queryList = CaseSessionHelper.getDisabledPayeeDataUpdateQueryCase(request);
        BaappbaseDataUpdateCase queryData = queryList.get(0);
        // 取得改前的更正資料
        Baappbase caseData = CaseSessionHelper.getDisabledPayeeDataUpdateDetailCase(request);
        try {

            if (isNotValidBankAccount(request, detailForm.getPayCategory(), detailForm.getPayBankIdBranchId(), detailForm.getPayTyp(), detailForm.getPayAccount()))
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);

            if (isGrdAlreadyDead(request, queryData.getApNo(), detailForm.getGrdIdnNo()))
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);

            if (isGrdAlsoEvtOrPayee(request, queryData.getEvtIdnNo(), detailForm.getBenIdnNo(), detailForm.getGrdIdnNo()))
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);

            // 檢核修改的受款人資料是否已存在
            String payeeDataCount = updateService.getPayeeDataCountForDisabledUpdate(caseData.getApNo(),detailForm.getBenIdnNo(), "",caseData.getBaappbaseId().toString(), caseData.getSeqNo());
            if (Integer.parseInt(payeeDataCount) > 0) {
                saveMessages(request.getSession(), DatabaseMessageHelper.getBenNameExistMessage());
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (detailForm != null) {
                BaappbaseDataUpdateCase detailData = prepareData(detailForm);

                // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                detailData = updateService.transDisabledPayeeUpdateData(detailData, caseData, "U");

                // 儲存INTERVALMONTH的值
                String sInterValMonth = null;
                if (!detailData.getAccRel().equals("3")){ // 20111107 非共同具領時才做判斷                  
                    if (!detailForm.getPayTyp().equals("5") && !detailForm.getPayTyp().equals("6")){
                        sInterValMonth = "0";
                        //updateService.updateInterValMonth(queryData.getApNo(), "0");                    
                    } else {
                        sInterValMonth = updateService.getInterValMonth(queryData.getApNo(), "0000");
                        if (sInterValMonth == null){
                            sInterValMonth = "";
                        }                    
                        //updateService.updateInterValMonth(queryData.getApNo(), sInterValMonth);                    
                    }                  
                } else {
                    sInterValMonth = detailData.getInterValMonth();
                }           
                
                //存檔時要判斷，此筆資料在畫面上設定為專戶，如果本來此筆資料在資料庫中專戶的設定就為Y，專戶日期也有值，則存檔時就維持原本資料庫中的值，不要用新的日期蓋掉資料庫中的值。
                //set頁面專戶勾選狀態
                if(StringUtils.isBlank(detailForm.getSpecialAccAfter())){
                	detailForm.setSpecialAccAfter("");
                }
                if(caseData.getSpecialAcc().equals("Y") && StringUtils.isNotBlank(caseData.getSpeAccDate())){
                	if(detailForm.getSpecialAccAfter().equals("Y")){
                		detailData.setSpecialAcc("Y");
                	    detailData.setSpeAccDate(caseData.getSpeAccDate());
                	}else{
                		detailData.setSpecialAcc("");
                		detailData.setSpeAccDate("");
                	}
                }else if(StringUtils.isBlank(caseData.getSpecialAcc())){
                	if(detailForm.getSpecialAccAfter().equals("Y")){
                		detailData.setSpecialAcc("Y");
                		detailData.setSpeAccDate(DateUtility.getNowWestDate());
                	}else{
                		detailData.setSpecialAcc("");
                		detailData.setSpeAccDate("");
                	}
                }
                
                // 取得需記錄異動LOG的欄位資料
                // 為不影響前端頁面顯示, 故須複製一份 Form
                DisabledPayeeDataUpdateDetailForm tempForm = new DisabledPayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(tempForm, detailForm);
                BeanUtility.copyProperties(tempForm, detailData);

                // 變更日期為西元年
                tempForm.setAppDate(convertToWesternDate(tempForm.getAppDate()));
                tempForm.setBenBrDate(convertToWesternDate(tempForm.getBenBrDate()));
                tempForm.setGrdBrDate(convertToWesternDate(tempForm.getGrdBrDate()));
                tempForm.setBenDieDate(convertToWesternDate(detailForm.getBenDieDate()));
                tempForm.setEvtDieDate(convertToWesternDate(detailForm.getEvtDieDate()));
                // 查核年月 兩個欄位合一
                if (StringUtils.equals(detailForm.getIdnChkNote(), "1")) {
                    tempForm.setIdnChkYm(DateUtility.calMonth(DateUtility.getNowWestDate(), 13).substring(0, 6));
                }
                else if (StringUtils.equals(detailForm.getIdnChkNote(), "2")) {
                    tempForm.setIdnChkYm(DateUtility.changeChineseYearMonthType(detailForm.getIdnChkYear() + detailForm.getIdnChkMonth()));
                }
                else {
                    tempForm.setIdnChkNote(tempForm.getOldIdnChkNote());
                    tempForm.setIdnChkYm(tempForm.getOldIdnChkYm());
                }
                // 給付資料更正主檔 改前改後值 for BAAPPLOG
                detailData.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));
                
                // 存檔
                updateService.updateBaappbaseDataForDisabledPayeeData(userData, detailData, caseData, sInterValMonth);
                
                // 案件類別為 2 或 4 時，呼叫 sp_BA_Get_CIPB
                if (detailData.getBenEvtRel().equals("2") || detailData.getBenEvtRel().equals("3")
                                || detailData.getBenEvtRel().equals("4") || detailData.getBenEvtRel().equals("5")
                                || detailData.getBenEvtRel().equals("6") || detailData.getBenEvtRel().equals("7")){                
                        if(queryData.getCaseTyp().equals("2") || queryData.getCaseTyp().equals("4")){
                            // 呼叫StoreProcedure sp_BA_Get_CIPB(眷屬身份證號,事故日期【NULL】)
                            updateService.doGetCipb(detailData.getApNo(), detailData.getSeqNo(), detailData.getAppDate() , detailData.getBenIdnNo(), null);
                        }
                }   

                // 呼叫即時編審
                String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
                try {
                    returnCode = callCheckMarkWebService(detailData.getApNo());
                }
                catch (Exception e) {
                    log.error(LOG_INFO_DOCONFIRM_CHECKMARK_FAIL + ExceptionUtility.getStackTrace(e));
                }
                log.debug(LOG_INFO_DOCONFIRM_CHECKMARK_RESULT + returnCode);
                
                // 更新清單資料
                DisabledPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getDisabledPayeeDataUpdateQueryForm(request);
                CaseSessionHelper.setDisabledPayeeDataUpdateQueryCase(fetchDisabledPayeeDataUpdateCaseList(queryForm.getApNo()), request);
                
                if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                    saveMessages(request.getSession(), CustomMessageHelper.getCheckMarkFailMessage());
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
                }
                
                // 設定存檔成功訊息
                saveMessages(request.getSession(), DatabaseMessageHelper.getUpdateSuccessMessage());
                
                /**
                 * 2009.12.14 Added
                 * 更新完後不帶回列表,而是停留在修改頁
                 */
                log.debug(LOG_INFO_DOCONFIRM_REFETCH_START);
                
                // 重新清除明細資料畫面
                FormSessionHelper.removeDisabledPayeeDataUpdateDetailForm(request);
                CaseSessionHelper.removeDisabledPayeeDataUpdateDetailCase(request);

                try {
                	String baappbaseId = caseData.getBaappbaseId().toString();
                    // 重新抓取資料
                    Baappbase requeryDetail = updateService.getDisabledPayeeDataUpdateForUpdateCaseBy(baappbaseId);
                    
                    // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                    DisabledPayeeDataUpdateDetailForm updateForm = new DisabledPayeeDataUpdateDetailForm();
                    BeanUtility.copyPropertiesForUpdate(updateForm, requeryDetail, ConstantKey.OLD_FIELD_PREFIX);
                    
                    // 變更日期為民國年
                    updateForm.setAppDate(convertToChineseDate(updateForm.getAppDate()));
                    updateForm.setBenBrDate(convertToChineseDate(updateForm.getBenBrDate()));
                    updateForm.setBenDieDate(convertToChineseDate(updateForm.getBenDieDate()));
                    updateForm.setEvtDieDate(convertToChineseDate(updateForm.getEvtDieDate()));
                    updateForm.setGrdBrDate(convertToChineseDate(updateForm.getGrdBrDate()));
                    
                    // 查核年月 分成兩個欄位
                    if (StringUtils.defaultString(requeryDetail.getIdnChkYm()).trim().length() != 0) {
                        updateForm.setIdnChkYear(DateUtility.changeWestYearMonthType(requeryDetail.getIdnChkYm()).substring(0, 3));
                        updateForm.setIdnChkMonth(DateUtility.changeWestYearMonthType(requeryDetail.getIdnChkYm()).substring(3, 5));
                    }
                    // 帳號 分成兩個欄位或一個欄位
                    if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(requeryDetail.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(requeryDetail.getPayTyp())) {
                        updateForm.setPayBankIdBranchId(requeryDetail.getPayBankId() + requeryDetail.getBranchId());
                        requeryDetail.setPayBankIdBranchId(requeryDetail.getPayBankId() + requeryDetail.getBranchId());
                    }
                    else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(requeryDetail.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(requeryDetail.getPayTyp())) {
                        updateForm.setPayAccount(requeryDetail.getPayBankId() + requeryDetail.getBranchId() + requeryDetail.getPayEeacc());
                    }

                    List<BaappbaseDataUpdateCase> applyList =  fetchDisabledPayeeDataUpdateCaseList(requeryDetail.getApNo());
                    //setNecessaryDropDownMenu(request, applyList.get(0), baappbaseId, updateForm.getBenEvtRel());
                    
                    // 傳入受款人的APNO,SEQNO,ISSUYM以確定是否要顯示身份查核年月
                    boolean toDisplayIdnCheck = updateService.displayIdnChkYearMonth(requeryDetail.getApNo(), requeryDetail.getSeqNo(), requeryDetail.getIssuYm());
                    // 檢查是否有共同具名領取的受款人, 若為true 則具名領取的選項及下拉選單要設為disable
                    boolean toDisableCoreceiver = updateService.hasCoreceiver(requeryDetail.getApNo(), requeryDetail.getSeqNo());
                    // 檢查具名領取下拉選單的size,size <= 0則畫面將具名領取選單隱藏起來
                    boolean toHideCoreceiveNameListDropDownMenu = (getCoreceiveNameListDropDownMenu(request)).isEmpty();
                    
                    // 設定事故者的死亡日期是否可修改
                    boolean isEvtDieDateUpdatable = isEvtDieDateUpdatable(requeryDetail.getApNo());
                    
                    // 取得受款人編審註記
                    Map<String, ArrayList<Bachkfile>> mapChkMk = fetchChkmkList(detailData.getApNo(), detailData.getSeqNo());
                    
                    FormSessionHelper.setDisabledPayeeDataUpdateDetailForm(updateForm, request);
                    request.getSession().setAttribute("checkMarkMap", mapChkMk);
                    request.getSession().setAttribute("toHideCoreceiveNameListDropDownMenu", toHideCoreceiveNameListDropDownMenu);
                    request.getSession().setAttribute("isEvtDieDateUpdatable", isEvtDieDateUpdatable);
                    CaseSessionHelper.setDisabledPayeeDataUpdateDetailCase(requeryDetail, toDisplayIdnCheck, toDisableCoreceiver, "", request);
                    
                }
                catch (Exception e) {
                    log.error(LOG_INFO_DOCONFIRM_REFETCH_ERROR + ExceptionUtility.getStackTrace(e));
                    saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
                    return mapping.findForward(ConstantKey.FORWARD_FAIL);
                }
            }

            log.debug(LOG_INFO_DOCONFIRM_COMPLETE);
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOCONFIRM_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }
    
    /**
     * 更正作業 - 失能年金受款人資料更正作業 - 各新增及修改頁面 <br/> 
     * (bamo0d181a.jsp, bamo0d183c.jsp, bamo0d187a.jsp, bamo0d188c.jsp) <br/>
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
            // 清除 Session
            CaseSessionHelper.removePayeeDataUpdateDetailCase(request);
            FormSessionHelper.removePayeeDataUpdateDetailForm(request);
            // 更新清單資料
            DisabledPayeeDataUpdateQueryForm listForm = FormSessionHelper.getDisabledPayeeDataUpdateQueryForm(request);
            CaseSessionHelper.setDisabledPayeeDataUpdateQueryCase(fetchDisabledPayeeDataUpdateCaseList(listForm.getApNo()), request);
            // 設定更新成功訊息
            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_MODIFY_DISABLED_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOBACKLIST_ERROR + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 在transData前的基本轉換
     * @param detailForm
     * @return
     */
    private BaappbaseDataUpdateCase prepareData(DisabledPayeeDataUpdateDetailForm detailForm) {
        BaappbaseDataUpdateCase detailData = new BaappbaseDataUpdateCase();
        BeanUtility.copyProperties(detailData, detailForm);

        // 變更日期為西元年
        detailData.setBenBrDate(convertToWesternDate(detailForm.getBenBrDate()));
        detailData.setEvtDieDate(convertToWesternDate(detailForm.getEvtDieDate()));
        detailData.setBenDieDate(convertToWesternDate(detailForm.getBenDieDate()));
        detailData.setGrdBrDate(convertToWesternDate(detailForm.getGrdBrDate()));
        detailData.setAppDate(convertToWesternDate(detailForm.getAppDate()));

        // 查核年月 兩個欄位合一
        if (StringUtils.equals(detailForm.getIdnChkNote(), "1")) {
            detailData.setIdnChkYm(DateUtility.calMonth(DateUtility.getNowWestDate(), 13).substring(0, 6));
        }
        else if (StringUtils.equals(detailForm.getIdnChkNote(), "2")) {
            detailData.setIdnChkYm(DateUtility.changeChineseYearMonthType(detailForm.getIdnChkYear() + detailForm.getIdnChkMonth()));
        }
        else {
            detailData.setIdnChkNote(detailForm.getOldIdnChkNote());
            detailData.setIdnChkYm(detailForm.getOldIdnChkYm());
        }

        // 判斷給付方式二選一
        if (detailForm.getPayCategory().equals("1")) {
            detailData.setAccSeqNo(detailData.getSeqNo());
        } else if (detailForm.getPayCategory().equals("2")) {
            detailData.setPayTyp("");
        }

        return detailData;
    }
    
    /**
     * 檢核新增的法定代理人是否為事故者或受款人
     * @param request
     * @param evtIdentity
     * @param benIdentity
     * @param grdIdentity
     * @return
     */
    private boolean isGrdAlsoEvtOrPayee(HttpServletRequest request, String evtIdentity, String benIdentity, String grdIdentity) {
        if ((grdIdentity).equals(benIdentity) || (grdIdentity).equals(evtIdentity)) {
            saveMessages(request.getSession(), DatabaseMessageHelper.getGrdNameExistMessage());
            return true;
        }
        return false;
    }

    /**
     * 檢核新增的法定代理人身分證號不可為其他已死的受款人
     * @param request
     * @param apNo
     * @param grdIdnNo
     * @return
     */
    private boolean isGrdAlreadyDead(HttpServletRequest request, String apNo, String grdIdnNo) {
        if (StringUtils.isNotBlank(grdIdnNo)) {
            String deadPayeeDataCount = updateService.getDeadPayeeDataCount(apNo, grdIdnNo);
            if (Integer.parseInt(deadPayeeDataCount) > 0) {
                saveMessages(request.getSession(), DatabaseMessageHelper.getGrdDeadMessage());
                return true;
            }
        }
        return false;
    }

    /**
     * 後端檢查銀行帳號是否輸入正確
     * payType == 1, 2 檢查payBankIdBranchId,payEeacc,只能英數
     * payType == 5, 6 檢查payAccount,只能英數
     * @param payBankIdBranchId
     * @param payType
     * @param payAccount
     * @return
     */
    private boolean isNotValidBankAccount(HttpServletRequest request, String payCategory, String payBankIdBranchId, String payType, String payAccount) {

        if ("1".equals(payCategory)) {
            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(payType)) {
                if (StringUtils.isNotBlank(payBankIdBranchId)) {
                    if (!StringUtils.isAlphanumeric(payBankIdBranchId)) {
                        saveMessages(request.getSession(), CustomMessageHelper.getBankIdErrorMessage());
                        return true;
                    }
                }
                if (!updateService.checkBankAccount(payBankIdBranchId)) {
                    saveMessages(request.getSession(), CustomMessageHelper.getBankIdErrorMessage());
                    return true;
                }
            } else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(payType) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(payType)) {
                if (StringUtils.isNotBlank(payAccount)) {
                    if (!StringUtils.isAlphanumeric(payAccount)) {
                        saveMessages(request.getSession(), CustomMessageHelper.getBankIdErrorMessage());
                        return true;
                    }
                }
            } else if (((ConstantKey.BAAPPBASE_PAYTYP_7).equals(payType) || (ConstantKey.BAAPPBASE_PAYTYP_8).equals(payType)) && !("7000010").equals(payBankIdBranchId)
                && !("7000021").equals(payBankIdBranchId)) {
                if (!updateService.checkBankAccount(payBankIdBranchId)) {
                    saveMessages(request.getSession(), CustomMessageHelper.getBankIdErrorMessage());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 檢核新增的受款人資料是否已存在
     * @param request
     * @param apNo
     * @param benIdnNo
     * @return
     */
    private boolean isPayeeExist(HttpServletRequest request, String apNo, String benIdnNo) {
        String payeeDataCount = updateService.getPayeeDataCount(apNo, benIdnNo, "");
        if (Integer.parseInt(payeeDataCount) > 0) {
            saveMessages(request.getSession(), DatabaseMessageHelper.getBenNameExistMessage());
            return true;
        }
        return false;
    }


    /**
     * 抓取畫面下拉選單之資料
     * @param request
     * @param dataUpdateCase
     * @param selfBaappbaseId
     */
    private void setNecessaryDropDownMenu(HttpServletRequest request, BaappbaseDataUpdateCase dataUpdateCase, String selfBaappbaseId, String benEvtRel){
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
