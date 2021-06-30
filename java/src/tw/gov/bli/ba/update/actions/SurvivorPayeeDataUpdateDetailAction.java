package tw.gov.bli.ba.update.actions;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Bachkfile;
import tw.gov.bli.ba.domain.Bahandicapterm;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCompelDataCase;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

/**
 * 更正作業 - 遺屬年金受款人資料更正 - Detail
 * 
 * @author Azuritul
 */
public class SurvivorPayeeDataUpdateDetailAction extends SurvivorPayeeDataUpdateAction {

    private static Log log = LogFactory.getLog(SurvivorPayeeDataUpdateDetailAction.class);

    // 更正作業 - 遺屬年金受款人資料更正 - 遺屬年金受款人資料清單頁面
    private static final String FORWARD_MODIFY_SURVIVOR_PAYEE_DATA_LIST = "modifyDisabledPayeeDataList";
    // 更正作業 - 遺屬年金受款人資料更正 - 遺屬年金受款人新增繼承人失敗後頁面
    private static final String FORWARD_SAVE_INHERIT_FAIL = "saveInheritFail";
    // 更正作業 - 遺屬年金受款人資料更正 - 遺屬年金受款人修改繼承人失敗後頁面
    private static final String FORWARD_UPDATE_INHERIT_FAIL = "updateInheritFail";
    // 更正作業 - 遺屬年金受款人資料更正 - 遺屬年金受款人修改繼承人成功後頁面
    private static final String FORWARD_UPDATE_INHERIT_SUCCESS = "updateInheritSuccess";
    // 更正作業 - 遺屬年金受款人資料更正 - 遺屬年金受款人修改受款人新增在學期間頁面
    private static final String FORWARD_INSERT_STUDTERM = "insertStudterm";
    // 更正作業 - 遺屬年金受款人資料更正 - 遺屬年金受款人修改受款人修改在學期間頁面
    private static final String FORWARD_MODIFY_STUDTERM = "modifyStudterm";
    // 更正作業 - 遺屬年金受款人資料更正 - 遺屬年金受款人修改受款人修改重殘起訖年度維護頁面
    private static final String FORWARD_MODIFY_HANDICAPTERM = "modifyHandicapterm";
    // 更正作業 - 遺屬年金受款人資料更正 - 遺屬年金受款人修改受款人新增不合格年月頁面
    private static final String FORWARD_INSERT_COMPELDATA = "insertCompelData";
    // 更正作業 - 遺屬年金受款人資料更正 - 遺屬年金受款人修改受款人修改不合格年月頁面
    private static final String FORWARD_MODIFY_COMPELDATA = "modifyCompelData";

    // 受款人新增作業Log訊息
    private static final String LOG_INFO_DOSAVE_START = "執行 遺屬年金受款人資料更正作業 - 新增頁面 SurvivorPayeeDataUpdateDetailAction.doSave() 開始 ... ";
    private static final String LOG_INFO_DOSAVE_COMPLETE = "執行 遺屬年金受款人資料更正作業 - 新增頁面 SurvivorPayeeDataUpdateDetailAction.doSave() 完成 ... ";
    private static final String LOG_INFO_DOSAVE_ERROR = "執行 遺屬年金受款人資料更正作業 - 新增頁面 SurvivorPayeeDataUpdateDetailAction.doSave() 發生錯誤:";
    private static final String LOG_INFO_DOSAVE_CHECKMARK_FAIL = "執行 遺屬年金受款人資料更正作業 - 新增頁面 SurvivorPayeeDataUpdateDetailAction.doSave() 即時編審發生錯誤:";
    private static final String LOG_INFO_DOSAVE_CHECKMARK_RESULT = "執行 遺屬年金受款人資料更正作業 - 新增作業存檔 - 呼叫即時編審結果... ";

    // 繼承人新增作業Log訊息
    private static final String LOG_INFO_DOSAVEINHERIT_START = "執行 遺屬年金受款人資料更正作業 - 新增繼承人頁面 SurvivorPayeeDataUpdateDetailAction.doSaveInherit() 開始 ... ";
    private static final String LOG_INFO_DOSAVEINHERIT_COMPLETE = "執行 遺屬年金受款人資料更正作業 - 新增繼承人頁面  SurvivorPayeeDataUpdateDetailAction.doSaveInherit() 完成 ... ";
    private static final String LOG_INFO_DOSAVEINHERIT_ERROR = "執行 遺屬年金受款人資料更正作業 - 新增繼承人頁面 SurvivorPayeeDataUpdateDetailAction.doSaveInherit() 發生錯誤:";
    private static final String LOG_INFO_DOSAVEINHERIT_CHECKMARK_FAIL = "執行 遺屬年金受款人資料更正作業 - 新增繼承人頁面 SurvivorPayeeDataUpdateDetailAction.doSaveInherit() 即時編審發生錯誤:";
    private static final String LOG_INFO_DOSAVEINHERIT_CHECKMARK_RESULT = "執行 遺屬年金受款人資料更正作業 - 新增繼承人作業存檔 - 呼叫即時編審結果... ";

    // 受款人修改作業Log訊息
    private static final String LOG_INFO_DOCONFIRM_START = "執行 遺屬年金受款人資料更正作業 - 修改頁面 SurvivorPayeeDataUpdateDetailAction.doConfirm() 開始 ... ";
    private static final String LOG_INFO_DOCONFIRM_REFETCH_ERROR = "執行 遺屬年金受款人資料更正作業 - 修改頁面 SurvivorPayeeDataUpdateDetailAction.doConfirm() 重新查詢資料時發生錯誤:";
    private static final String LOG_INFO_DOCONFIRM_COMPLETE = "執行 遺屬年金受款人資料更正作業 - 修改頁面 SurvivorPayeeDataUpdateDetailAction.doConfirm() 完成 ... ";
    private static final String LOG_INFO_DOCONFIRM_ERROR = "執行 遺屬年金受款人資料更正作業 - 修改頁面 SurvivorPayeeDataUpdateDetailAction.doConfirm() 發生錯誤:";
    private static final String LOG_INFO_DOCONFIRM_CHECKMARK_FAIL = "執行 遺屬年金受款人資料更正作業 - 修改頁面 SurvivorPayeeDataUpdateDetailAction.doConfirm() 即時編審發生錯誤:";
    private static final String LOG_INFO_DOCONFIRM_CHECKMARK_RESULT = "執行 遺屬年金受款人資料更正作業 - 修改作業存檔 SurvivorPayeeDataUpdateDetailAction.doConfirm() 呼叫即時編審結果... ";

    // 繼承人修改作業Log訊息
    private static final String LOG_INFO_DOCONFIRMINHERIT_START = "執行 遺屬年金受款人資料更正作業 - 修改繼承人頁面 SurvivorPayeeDataUpdateDetailAction.doConfirmInherit() 開始 ... ";
    private static final String LOG_INFO_DOCONFIRMINHERIT_COMPLETE = "執行 遺屬年金受款人資料更正作業 - 修改繼承人頁面  SurvivorPayeeDataUpdateDetailAction.doConfirmInherit() 完成 ... ";
    private static final String LOG_INFO_DOCONFIRMINHERIT_REFETCH_START = "執行 遺屬年金受款人資料更正作業 - 修改繼承人頁面 SurvivorPayeeDataUpdateListAction.doConfirmInherit() 重新查詢資料開始 ... ";
    private static final String LOG_INFO_DOCONFIRMINHERIT_REFETCH_ERROR = "執行 遺屬年金受款人資料更正作業 - 修改繼承人頁面 SurvivorPayeeDataUpdateDetailAction.doConfirmInherit() 重新查詢資料時發生錯誤:";
    private static final String LOG_INFO_DOCONFIRMINHERIT_ERROR = "執行 遺屬年金受款人資料更正作業 - 修改繼承人頁面 SurvivorPayeeDataUpdateDetailAction.doConfirmInherit() 發生錯誤:";
    private static final String LOG_INFO_DOCONFIRMINHERIT_CHECKMARK_FAIL = "執行 遺屬年金受款人資料更正作業 - 修改繼承人頁面 SurvivorPayeeDataUpdateDetailAction.doConfirmInherit() 即時編審發生錯誤:";
    private static final String LOG_INFO_DOCONFIRMINHERIT_CHECKMARK_RESULT = "執行 遺屬年金受款人資料更正作業 - 修改繼承人作業存檔 SurvivorPayeeDataUpdateDetailAction.doConfirmInherit() 呼叫即時編審結果... ";

    // 受款人在學資料維護作業Log訊息
    private static final String LOG_INFO_DOPREPAREMAINTAINSTUDTERM_START = "執行 遺屬年金受款人資料更正作業 - 在學期間維護 SurvivorPayeeDataUpdateDetailAction.doPrepareMaintainStudterm() 開始 ... ";
    private static final String LOG_INFO_DOPREPAREMAINTAINSTUDTERM_ERROR = "執行 遺屬年金受款人資料更正作業 - 在學期間維護 SurvivorPayeeDataUpdateDetailAction.doPrepareMaintainStudterm() 發生錯誤:";
    private static final String LOG_INFO_DOPREPAREINSERTSTUDTERM_START = "執行 遺屬年金受款人資料更正作業 - 新增在學期間 SurvivorPayeeDataUpdateDetailAction.doPrepareInsertStudterm() 開始 ... ";
    private static final String LOG_INFO_DOPREPAREINSERTSTUDTERM_ERROR = "執行 遺屬年金受款人資料更正作業 - 新增在學期間 SurvivorPayeeDataUpdateDetailAction.doPrepareInsertStudterm() 發生錯誤:";

    // 受款人重殘起訖年月維護Log訊息
    private static final String LOG_INFO_DOPREPAREMAINTAINHANDICAPTERM_START = "執行 遺屬年金受款人資料更正作業 -  重殘起訖年月維護 SurvivorPayeeDataUpdateDetailAction.doPrepareMaintainHandicapterm() 開始 ... ";
    private static final String LOG_INFO_DOPREPAREMAINTAINHANDICAPTERM_ERROR = "執行 重殘年金受款人資料更正作業 - 重殘起訖年月維護 SurvivorPayeeDataUpdateDetailAction.doPrepareMaintainHandicapterm() 發生錯誤:";
    
    // 受款人強末不合格資料維護作業Log訊息
    private static final String LOG_INFO_DOPREPAREMAINTAIN_COMPELDATA_START = "執行 遺屬年金受款人資料更正作業 - 不合格年月維護 SurvivorPayeeDataUpdateDetailAction.doPrepareMaintainCompelData() 開始 ... ";
    private static final String LOG_INFO_DOPREPAREMAINTAIN_COMPELDATA_ERROR = "執行 遺屬年金受款人資料更正作業 - 不合格年月維護 SurvivorPayeeDataUpdateDetailAction.doPrepareMaintainCompelData() 發生錯誤:";
    private static final String LOG_INFO_DOPREPAREINSERT_COMPELDATA_START = "執行 遺屬年金受款人資料更正作業 - 新增不合格年月 SurvivorPayeeDataUpdateDetailAction.doPrepareInsertCompelData() 開始 ... ";
    private static final String LOG_INFO_DOPREPAREINSERT_COMPELDATA_ERROR = "執行 遺屬年金受款人資料更正作業 - 新增不合格年月 SurvivorPayeeDataUpdateDetailAction.doPrepareInsertCompelData() 發生錯誤:";

    private static final String LOG_INFO_DOBACKLIST_START = "執行 遺屬年金受款人資料更正作業 - 各頁面(返回) SurvivorPayeeDataUpdateDetailAction.doBackList() 開始 ... ";

    /**
     * 更正作業 - 遺屬受款人資料更正作業 - 新增頁面 (bamo0d281a.jsp) <br>
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

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorPayeeDataUpdateDetailForm detailForm = (SurvivorPayeeDataUpdateDetailForm) form;

        // 取得查詢List
        List<SurvivorPayeeDataUpdateCase> queryList = CaseSessionHelper.getSurvivorPayeeEvtDataUpdateQueryCase(request);
        // 取得主檔(SEQNO = 0000的那筆)
        SurvivorPayeeDataUpdateCase queryData = queryList.get(0);

        // 將主檔資料存入BAAPPBASE，供後續使用
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, queryData);

        try {

            if (isNotValidBankAccount(request, detailForm))
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);

            if (isGrdAlreadyDead(request, detailForm.getGrdIdnNo(), queryData.getApNo()))
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);

            if (!detailForm.getBenEvtRel().equals("O")){
                if (isGrdAlsoEvtOrPayee(session, detailForm.getGrdIdnNo(), detailForm.getBenIdnNo(), queryData.getEvtIdnNo()))
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);

                if (isAssignAlsoEvtOrPayee(session, detailForm.getAssignIdnNo(), detailForm.getBenIdnNo(), queryData.getEvtIdnNo()))
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            // String payeeDataCount = updateService.getPayeeDataCount(queryData.getApNo(), detailForm.getBenIdnNo(), "");
            // if (Integer.parseInt(payeeDataCount) > 0) {
            // saveMessages(session, DatabaseMessageHelper.getBenNameExistMessage());
            // return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            // }

            if (detailForm != null) {
                SurvivorPayeeDataUpdateCase detailData = new SurvivorPayeeDataUpdateCase();
                BeanUtility.copyProperties(detailData, detailForm);

                // 將畫面上欄位轉換為西元日期
                detailData = convertCaseDateToWesternStyle(detailData, detailForm);

                // 查核年月 兩個欄位合一
                if (StringUtils.isNotBlank(detailForm.getIdnChkYear()) && StringUtils.isNotBlank(detailForm.getIdnChkMonth())) {
                    detailData.setIdnChkYm(DateUtility.changeChineseYearMonthType(detailForm.getIdnChkYear() + detailForm.getIdnChkMonth()));
                }

                // 判斷給付方式二選一
                if (detailForm.getPayCategory().equals("1")) {
                    detailData.setAccSeqNo(detailData.getSeqNo());
                }
                else if (detailForm.getPayCategory().equals("2")) {
                    detailData.setPayTyp("");
                }

                // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                detailData = updateService.transSurvivorPayeeUpdateData(detailData, baappbase, "I");

                // 取得不合格年月資料List
                List<SurvivorPayeeDataUpdateCompelDataCase> compelDataList = CaseSessionHelper.getSurvivorPayeeDataUpdateCompelDataList(request);
                // 將在學起迄轉為西元年
                if (compelDataList != null) {
                    for (SurvivorPayeeDataUpdateCompelDataCase o : compelDataList) {
                        // 若是民國年(5碼)才轉為西元年月
                        if (StringUtils.isNotBlank(o.getCompelSdate()) && o.getCompelSdate().length() == 5) {
                            o.setCompelSdate(DateUtility.changeChineseYearMonthType(o.getCompelSdate()));
                        }
                        if (StringUtils.isNotBlank(o.getCompelEdate()) && o.getCompelEdate().length() == 5) {
                            o.setCompelEdate(DateUtility.changeChineseYearMonthType(o.getCompelEdate()));
                        }

                    }
                }

                // 取得在學期間資料List
                List<Bastudterm> termList = CaseSessionHelper.getSurvivorPayeeDataUpdateStudtermList(request);
                // 將在學起迄轉為西元年
                if (termList != null) {
                    for (Bastudterm o : termList) {
                        // 若是民國年(5碼)才轉為西元年月
                        if (StringUtils.isNotBlank(o.getStudSdate()) && o.getStudSdate().length() == 5) {
                            o.setStudSdate(DateUtility.changeDateType(o.getStudSdate() + "01"));
                            o.setStudSdate(o.getStudSdate().substring(0, 6));
                        }
                        if (StringUtils.isNotBlank(o.getStudEdate()) && o.getStudEdate().length() == 5) {
                            o.setStudEdate(DateUtility.changeDateType(o.getStudEdate() + "01"));
                            o.setStudEdate(o.getStudEdate().substring(0, 6));
                        }
                    }
                }

                // 儲存INTERVALMONTH的值
                String sInterValMonth = null;
                if (!detailData.getAccRel().equals("3")){ // 20111107 非共同具領時才做判斷                  
                    if (!detailForm.getPayTyp().equals("5") && !detailForm.getPayTyp().equals("6")) {
                        sInterValMonth = "0";
                        // updateService.updateInterValMonth(queryData.getApNo(), "0");
                    }
                    else {
                        sInterValMonth = updateService.getInterValMonth(queryData.getApNo(), "0000");
                        if (sInterValMonth == null) {
                            sInterValMonth = "";
                        }
                        // updateService.updateInterValMonth(queryData.getApNo(), sInterValMonth);
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
                updateService.insertDataForSurvivorPayeeData(userData, queryData, detailData, termList, sInterValMonth, compelDataList);
                
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
                SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);
                CaseSessionHelper.setSurvivorPayeeDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(queryForm.getApNo(), "0000"), request);
                CaseSessionHelper.setSurvivorPayeeEvtDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(queryForm.getApNo(), null), request);

                if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                    saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
                }

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug(LOG_INFO_DOSAVE_COMPLETE);
            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOSAVE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬受款人資料更正作業 - 新增繼承人頁面 (bamo0d287a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSaveInherit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOSAVEINHERIT_START);

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        // 取得傳出的Form
        SurvivorPayeeDataUpdateDetailForm detailForm = (SurvivorPayeeDataUpdateDetailForm) form;
        // 取得本繼承人是繼承自誰
        BaappbaseDataUpdateCase inheritFrom = (BaappbaseDataUpdateCase) request.getSession().getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_INHERIT_FROM);

        // 取得查詢List
        List<SurvivorPayeeDataUpdateCase> queryList = CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase(request);
        SurvivorPayeeDataUpdateCase queryData = queryList.get(0);

        // 將上一層的主檔資料存入BAAPPBASE，供後續使用
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyPropertiesNonNull(baappbase, inheritFrom);

        try {

            if (isNotValidBankAccount(request, detailForm))
                return mapping.findForward(FORWARD_SAVE_INHERIT_FAIL);

            if (isGrdAlreadyDead(request, detailForm.getGrdIdnNo(), queryData.getApNo()))
                return mapping.findForward(FORWARD_SAVE_INHERIT_FAIL);

            if (!detailForm.getBenEvtRel().equals("O")){
                if (isGrdAlsoEvtOrPayee(session, detailForm.getGrdIdnNo(), detailForm.getBenIdnNo(), queryData.getEvtIdnNo()))
                    return mapping.findForward(FORWARD_SAVE_INHERIT_FAIL);

                if (isAssignAlsoEvtOrPayee(session, detailForm.getAssignIdnNo(), detailForm.getBenIdnNo(), queryData.getEvtIdnNo()))
                    return mapping.findForward(FORWARD_SAVE_INHERIT_FAIL);
            }

            // String payeeDataCount = updateService.getPayeeDataCount(queryData.getApNo(), detailForm.getBenIdnNo(), "");
            // if (Integer.parseInt(payeeDataCount) > 0) {
            // saveMessages(session, DatabaseMessageHelper.getBenNameExistMessage());
            // return mapping.findForward(FORWARD_SAVE_INHERIT_FAIL);
            // }

            if (detailForm != null) {
                SurvivorPayeeDataUpdateCase detailData = new SurvivorPayeeDataUpdateCase();
                BeanUtility.copyProperties(detailData, detailForm);
                detailData = convertCaseDateToWesternStyle(detailData, detailForm);

                // 判斷給付方式二選一
                if (detailForm.getPayCategory().equals("1")) {
                    detailData.setAccSeqNo(detailData.getSeqNo());
                }
                else if (detailForm.getPayCategory().equals("2")) {
                    detailData.setPayTyp("");
                }

                // 依畫面輸入欄位條件轉換給付主檔部分欄位
                detailData = updateService.transSurvivorPayeeUpdateData(detailData, baappbase, "II");

                // 存檔
                updateService.insertDataForSurvivorPayeeData(userData, queryData, detailData, null, "N", null);
                
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
                    log.error(LOG_INFO_DOSAVEINHERIT_CHECKMARK_FAIL + ExceptionUtility.getStackTrace(e));
                }
                log.debug(LOG_INFO_DOSAVEINHERIT_CHECKMARK_RESULT + returnCode);

                // 更新清單資料
                SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);
                CaseSessionHelper.setSurvivorPayeeDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(queryForm.getApNo(), "0000"), request);
                CaseSessionHelper.setSurvivorPayeeEvtDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(queryForm.getApNo(), null), request);

                if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                    saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
                }
                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }
            log.debug(LOG_INFO_DOSAVEINHERIT_COMPLETE);
            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOSAVEINHERIT_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(FORWARD_SAVE_INHERIT_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 修改頁面 (bamo0d283c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOCONFIRM_START);

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorPayeeDataUpdateDetailForm detailForm = (SurvivorPayeeDataUpdateDetailForm) form;
        // 取得查詢List
        List<SurvivorPayeeDataUpdateCase> queryList = CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase(request);

        // 取得主檔(SEQNO = 0000的那筆)
        SurvivorPayeeDataUpdateCase queryData = queryList.get(0);
        // 取得改前的更正資料
        Baappbase caseData = CaseSessionHelper.getSurvivorPayeeDataUpdateDetailCase(request);

        // 將主檔資料存入BAAPPBASE，供後續使用
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, queryData);

        try {
            if (isNotValidBankAccount(request, detailForm))
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);

            if (isGrdAlreadyDead(request, detailForm.getGrdIdnNo(), queryData.getApNo()))
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);

            if (!detailForm.getBenEvtRel().equals("O")) {
                if (isGrdAlsoEvtOrPayee(session, detailForm.getGrdIdnNo(), detailForm.getBenIdnNo(), queryData.getEvtIdnNo()))
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);

                if (isAssignAlsoEvtOrPayee(session, detailForm.getAssignIdnNo(), detailForm.getBenIdnNo(), queryData.getEvtIdnNo()))
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            // 檢核修改的受款人資料是否已存在
            // String payeeDataCount = updateService.getPayeeDataCountForDisabledUpdate(caseData.getApNo(),detailForm.getBenIdnNo(), "",caseData.getBaappbaseId().toString(), caseData.getSeqNo());
            // if (Integer.parseInt(payeeDataCount) > 0) {
            // saveMessages(request.getSession(), DatabaseMessageHelper.getBenNameExistMessage());
            // return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            // }

            if (detailForm != null) {
                SurvivorPayeeDataUpdateCase detailData = new SurvivorPayeeDataUpdateCase();
                BeanUtility.copyProperties(detailData, detailForm);

                // 將畫面上欄位轉換為西元日期
                detailData = convertCaseDateToWesternStyle(detailData, detailForm);

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
                }
                else if (detailForm.getPayCategory().equals("2")) {
                    detailData.setPayTyp("");
                }

                // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                detailData = updateService.transSurvivorPayeeUpdateData(detailData, baappbase, "U");

                // 取得需記錄異動LOG的欄位資料
                // 為不影響前端頁面顯示, 故須複製一份 Form
                SurvivorPayeeDataUpdateDetailForm tempForm = new SurvivorPayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(tempForm, detailForm);
                BeanUtility.copyProperties(tempForm, detailData);
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

                // 取得不合格年月資料List
                List<SurvivorPayeeDataUpdateCompelDataCase> compelDataList = CaseSessionHelper.getSurvivorPayeeDataUpdateCompelDataList(request);
                // 將在學起迄轉為西元年
                if (compelDataList != null) {
                    for (SurvivorPayeeDataUpdateCompelDataCase o : compelDataList) {
                        // 若是民國年(5碼)才轉為西元年月
                        if (StringUtils.isNotBlank(o.getCompelSdate()) && o.getCompelSdate().length() == 5) {
                            o.setCompelSdate(DateUtility.changeChineseYearMonthType(o.getCompelSdate()));
                        }
                        if (StringUtils.isNotBlank(o.getCompelEdate()) && o.getCompelEdate().length() == 5) {
                            o.setCompelEdate(DateUtility.changeChineseYearMonthType(o.getCompelEdate()));
                        }

                    }
                }

                // 取得在學期間資料List
                List<Bastudterm> termList = CaseSessionHelper.getSurvivorPayeeDataUpdateStudtermList(request);
                // 將在學起迄轉為西元年
                if (termList != null) {
                    for (Bastudterm o : termList) {
                        // 若是民國年(5碼)才轉為西元年月
                        if (StringUtils.isNotBlank(o.getStudSdate()) && o.getStudSdate().length() == 5) {
                            o.setStudSdate(DateUtility.changeDateType(o.getStudSdate() + "01"));
                            o.setStudSdate(o.getStudSdate().substring(0, 6));
                        }
                        if (StringUtils.isNotBlank(o.getStudEdate()) && o.getStudEdate().length() == 5) {
                            o.setStudEdate(DateUtility.changeDateType(o.getStudEdate() + "01"));
                            o.setStudEdate(o.getStudEdate().substring(0, 6));
                        }
                    }
                }

                // 取得重殘期間資料List
                List<Bahandicapterm> bahandicapTermList = CaseSessionHelper.getSurvivorPayeeDataUpdateHandicaptermList(request);
                // 將重殘起迄轉為西元年
                if (bahandicapTermList != null) {
                    for (Bahandicapterm o : bahandicapTermList) {
                        // 若是民國年(5碼)才轉為西元年月
                        if (StringUtils.isNotBlank(o.getHandicapSdate()) && o.getHandicapSdate().length() == 5) {
                            o.setHandicapSdate(DateUtility.changeDateType(o.getHandicapSdate() + "01"));
                            o.setHandicapSdate(o.getHandicapSdate().substring(0, 6));
                        }
                        if (StringUtils.isNotBlank(o.getHandicapEdate()) && o.getHandicapEdate().length() == 5) {
                            o.setHandicapEdate(DateUtility.changeDateType(o.getHandicapEdate() + "01"));
                            o.setHandicapEdate(o.getHandicapEdate().substring(0, 6));
                        }
                    }
                }
                
                // 儲存INTERVALMONTH的值
                String sInterValMonth = null;
                if (!detailData.getAccRel().equals("3")) { // 20111107 非共同具領時才做判斷
                    if (!detailForm.getPayTyp().equals("5") && !detailForm.getPayTyp().equals("6")) {
                        sInterValMonth = "0";
                        // updateService.updateInterValMonth(queryData.getApNo(), "0");
                    }
                    else {
                        sInterValMonth = updateService.getInterValMonth(queryData.getApNo(), "0000");
                        if (sInterValMonth == null) {
                            sInterValMonth = "";
                        }
                        // updateService.updateInterValMonth(queryData.getApNo(), sInterValMonth);
                    }
                }
                else {
                    sInterValMonth = detailData.getInterValMonth();
                }

                // 存檔時要判斷，此筆資料在畫面上設定為專戶，如果本來此筆資料在資料庫中專戶的設定就為Y，專戶日期也有值，則存檔時就維持原本資料庫中的值，不要用新的日期蓋掉資料庫中的值。
                // set頁面專戶勾選狀態
                if (StringUtils.isBlank(detailForm.getSpecialAccAfter())) {
                    detailForm.setSpecialAccAfter("");
                }
                if (caseData.getSpecialAcc().equals("Y") && StringUtils.isNotBlank(caseData.getSpeAccDate())) {
                    if (detailForm.getSpecialAccAfter().equals("Y")) {
                        detailData.setSpecialAcc("Y");
                        detailData.setSpeAccDate(caseData.getSpeAccDate());
                    }
                    else {
                        detailData.setSpecialAcc("");
                        detailData.setSpeAccDate("");
                    }
                }
                else if (StringUtils.isBlank(caseData.getSpecialAcc())) {
                    if (detailForm.getSpecialAccAfter().equals("Y")) {
                        detailData.setSpecialAcc("Y");
                        detailData.setSpeAccDate(DateUtility.getNowWestDate());
                    }
                    else {
                        detailData.setSpecialAcc("");
                        detailData.setSpeAccDate("");
                    }
                }

                // 存檔
                updateService.updateDataForSurvivorPayeeData(userData, queryData, detailData, termList, sInterValMonth, compelDataList , bahandicapTermList);

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
                    log.error(LOG_INFO_DOCONFIRM_CHECKMARK_FAIL + ExceptionUtility.getStackTrace(e));
                }
                log.debug(LOG_INFO_DOCONFIRM_CHECKMARK_RESULT + returnCode);

                // 更新清單資料
                SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);
                CaseSessionHelper.setSurvivorPayeeDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(queryForm.getApNo(), "0000"), request);
                CaseSessionHelper.setSurvivorPayeeEvtDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(queryForm.getApNo(), null), request);
                if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                    saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
                }

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());

                // 存檔完後不導回清單頁,停於本頁,因此需重抓資料
                // 清除明細資料畫面
                FormSessionHelper.removeSurvivorPayeeDataUpdateDetailForm(request);
                CaseSessionHelper.removeSurvivorPayeeDataUpdateDetailCase(request);

                try {
                    String baappbaseId = StringUtils.defaultString(request.getParameter("baappbaseId"));
                    // 重新抓取資料 - 直接用舊有的SQL即可(PS只有抓主檔)
                    Baappbase requeryDetail = updateService.getPayeeDataUpdateForUpdateCaseBy(baappbaseId);
                    // 重新抓取資料 - 還要再抓延伸主檔 並設至Form及Case中
                    Baappexpand detailBaappexpandData = updateService.getSurvivorPayeeDataUpdateForUpdateCaseBy(new BigDecimal(baappbaseId));

                    // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                    SurvivorPayeeDataUpdateDetailForm updateForm = new SurvivorPayeeDataUpdateDetailForm();
                    // 帶到Baappbase
                    BeanUtility.copyPropertiesForUpdate(updateForm, requeryDetail, ConstantKey.OLD_FIELD_PREFIX);
                    // 將Baappexpand資料設至Form
                    BigDecimal monIncomeBigDecimal = detailBaappexpandData.getMonIncome();
                    if (monIncomeBigDecimal != null) {
                        if (StringUtils.isNotBlank(monIncomeBigDecimal.toString())) {
                            updateForm.setMonIncome(monIncomeBigDecimal.toString());
                        }
                    }
                    if (detailBaappexpandData.getBaappexpandId() != null) {
                        updateForm.setBaappexpandId(StringUtils.defaultString(detailBaappexpandData.getBaappexpandId().toString()));
                    }
                    if (detailBaappexpandData.getBaappbaseId() != null) {
                        updateForm.setBaappbaseId(new BigDecimal(StringUtils.defaultString(detailBaappexpandData.getBaappbaseId().toString())));
                    }
                    updateForm.setMarryDate(StringUtils.defaultString(detailBaappexpandData.getMarryDate()));
                    updateForm.setDigaMyDate(StringUtils.defaultString(detailBaappexpandData.getDigamyDate()));
                    updateForm.setMonIncomeMk(detailBaappexpandData.getMonIncomeMk());
                    updateForm.setHandicapMk(StringUtils.defaultString(detailBaappexpandData.getHandIcapMk()));
                    updateForm.setCompelMk(StringUtils.defaultString(detailBaappexpandData.getCompelMk()));
                    updateForm.setStudMk(StringUtils.defaultString(detailBaappexpandData.getStudMk()));
                    updateForm.setAbanApplyMk(StringUtils.defaultString(detailBaappexpandData.getAbanApplyMk()));
                    updateForm.setAbanApSym(StringUtils.defaultString(detailBaappexpandData.getAbanApsYm()));
                    updateForm.setAbleApsYm(StringUtils.defaultString(detailBaappexpandData.getAbleApsYm()));
                    updateForm.setRaiseChildMk(StringUtils.defaultString(detailBaappexpandData.getRaiseChildMk()));
                    updateForm.setInterdictMk(StringUtils.defaultString(detailBaappexpandData.getInterDictMk()));
                    updateForm.setInterdictDate(StringUtils.defaultString(detailBaappexpandData.getInterDictDate()));
                    updateForm.setRepealInterdictDate(StringUtils.defaultString(detailBaappexpandData.getRepealInterdictDate()));
                    updateForm.setBenMissingSdate(StringUtils.defaultString(detailBaappexpandData.getBenMissingSdate()));
                    updateForm.setBenMissingEdate(StringUtils.defaultString(detailBaappexpandData.getBenMissingEdate()));
                    updateForm.setPrisonSdate(StringUtils.defaultString(detailBaappexpandData.getPrisonSdate()));
                    updateForm.setPrisonEdate(StringUtils.defaultString(detailBaappexpandData.getPrisonEdate()));
                    updateForm.setRelatChgDate(StringUtils.defaultString(detailBaappexpandData.getRelatChgDate()));
                    updateForm.setAdoptDate(StringUtils.defaultString(detailBaappexpandData.getAdoPtDate()));
                    updateForm.setAssignBrDate(StringUtils.defaultString(detailBaappexpandData.getAssignBrDate()));
                    updateForm.setAssignName(StringUtils.defaultString(detailBaappexpandData.getAssignName()));
                    updateForm.setAssignIdnNo(StringUtils.defaultString(detailBaappexpandData.getAssignIdnNo()));
                    updateForm.setRaiseEvtMk(StringUtils.defaultString(detailBaappexpandData.getRaiseEvtMk()));
                    updateForm.setSavingMk(StringUtils.defaultString(detailBaappexpandData.getSavingMk()));
                    updateForm.setSchoolCode(StringUtils.defaultString(detailBaappexpandData.getSchoolCode()));
                    // 變更日期為民國年
                    updateForm = convertDateToChineseYear(updateForm);
                    detailBaappexpandData = convertDateToChineseYear(detailBaappexpandData);

                    // 查核年月 分成兩個欄位
                    if (StringUtils.isNotBlank(requeryDetail.getIdnChkYm())) {
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

                    // 使用一開始的QueryAction就抓的QueryCase的List
                    List<SurvivorPayeeDataUpdateCase> list = CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase(request);
                    List<SurvivorPayeeDataUpdateCase> evtlist = CaseSessionHelper.getSurvivorPayeeEvtDataUpdateQueryCase(request);
                    setNecessaryDropDownMenu(request, evtlist.get(0), baappbaseId, updateForm.getBenEvtRel());

                    // 傳入受款人的APNO,SEQNO,ISSUYM以確定是否要顯示身份查核年月
                    boolean toDisplayIdnCheck = updateService.displayIdnChkYearMonth(requeryDetail.getApNo(), requeryDetail.getSeqNo(), requeryDetail.getIssuYm());
                    // 檢查是否有共同具名領取的受款人, 若為true 則具名領取的選項及下拉選單要設為disable
                    boolean toDisableCoreceiver = updateService.hasCoreceiver(requeryDetail.getApNo(), requeryDetail.getSeqNo());
                    // 檢查具名領取下拉選單的size,size <= 0則畫面將具名領取選單隱藏起來
                    boolean toHideCoreceiveNameListDropDownMenu = (getCoreceiveNameListDropDownMenu(request)).isEmpty();
                    // 檢查是否要顯示再婚日期
                    boolean displayDigamyDate = updateService.displayDigamyDate(requeryDetail.getApNo(), requeryDetail.getSeqNo(), requeryDetail.getIssuYm());

                    // 重新取得不合格年月清單資料
                    List<SurvivorPayeeDataUpdateCompelDataCase> refetchedCompelDataList = updateService.getCompelDataList(requeryDetail.getApNo(), updateForm.getSeqNo());
                    // 重新取得在學清單資料
                    List<Bastudterm> refetchedTermList = updateService.selectStudtermListForSurvivorPayeeDataUpdate(requeryDetail.getApNo(), updateForm.getSeqNo());
                    // 重新取得重殘起訖資料
                    List<Bahandicapterm> refetchedHandicapTermList = updateService.selectHandicaptermListForSurvivorPayeeDataUpdate(requeryDetail.getApNo(), requeryDetail.getSeqNo());
                    // 取得遺屬註記及符合註記
                    Map<String, ArrayList<Bachkfile>> mapChkMk = fetchChkmkList(requeryDetail.getApNo(), requeryDetail.getSeqNo());
                    Map<String, ArrayList<Bachkfile>> map = fetchQualifyChkmkList(requeryDetail.getApNo(), requeryDetail.getSeqNo());

                    request.getSession().setAttribute("qualifyMarkMap", map);
                    request.getSession().setAttribute("checkMarkMap", mapChkMk);
                    request.getSession().setAttribute("toHideCoreceiveNameListDropDownMenu", toHideCoreceiveNameListDropDownMenu);
                    request.getSession().setAttribute("toDisplayDigaMyDate", displayDigamyDate);
                    FormSessionHelper.setSurvivorPayeeDataUpdateDetailForm(updateForm, request);
                    CaseSessionHelper.setSurvivorPayeeDataUpdateCompelDataList(refetchedCompelDataList, request);
                    if (refetchedCompelDataList != null && !refetchedCompelDataList.isEmpty()) {
                        request.getSession().setAttribute("SURVIVOR_PAYEE_COMPELDATA_SIZE", refetchedCompelDataList.size());
                    }
                    else {
                        request.getSession().setAttribute("SURVIVOR_PAYEE_COMPELDATA_SIZE", 0);
                    }
                    CaseSessionHelper.setSurvivorPayeeDataUpdateStudtermList(refetchedTermList, request);
                    if (refetchedTermList != null && !refetchedTermList.isEmpty()) {
                        request.getSession().setAttribute("SURVIVOR_PAYEE_STUDTERM_SIZE", refetchedTermList.size());
                    }
                    else {
                        request.getSession().setAttribute("SURVIVOR_PAYEE_STUDTERM_SIZE", 0);
                    }
                    CaseSessionHelper.setSurvivorPayeeDataUpdateHandicaptermList(refetchedHandicapTermList, request);
                    if (refetchedHandicapTermList != null && refetchedHandicapTermList.size() > 0) {
                        request.getSession().setAttribute("SURVIVOR_PAYEE_HANDICAPTERM_SIZE", refetchedHandicapTermList.size());
                    }else {
                        request.getSession().setAttribute("SURVIVOR_PAYEE_HANDICAPTERM_SIZE", 0);
                    }
                    CaseSessionHelper.setSurvivorPayeeDataUpdateDetailCase(requeryDetail, detailBaappexpandData, toDisplayIdnCheck, toDisableCoreceiver, "", request);
                }
                catch (Exception e) {
                    log.error(LOG_INFO_DOCONFIRM_REFETCH_ERROR + ExceptionUtility.getStackTrace(e));
                    saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
                }
            }

            log.debug(LOG_INFO_DOCONFIRM_COMPLETE);
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOCONFIRM_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 修改繼承人頁面 (bamo0d288c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirmInherit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOCONFIRMINHERIT_START);

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        SurvivorPayeeDataUpdateDetailForm detailForm = (SurvivorPayeeDataUpdateDetailForm) form;

        // 取得查詢List
        List<SurvivorPayeeDataUpdateCase> queryList = CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase(request);
        SurvivorPayeeDataUpdateCase queryData = queryList.get(0);
        // 取得改前的更正資料
        Baappbase caseData = CaseSessionHelper.getSurvivorPayeeDataUpdateDetailCase(request);

        try {

            if (isNotValidBankAccount(request, detailForm))
                return mapping.findForward(FORWARD_UPDATE_INHERIT_FAIL);

            if (isGrdAlreadyDead(request, detailForm.getGrdIdnNo(), queryData.getApNo()))
                return mapping.findForward(FORWARD_UPDATE_INHERIT_FAIL);

            if (!detailForm.getBenEvtRel().equals("O")){
                if (isGrdAlsoEvtOrPayee(session, detailForm.getGrdIdnNo(), detailForm.getBenIdnNo(), queryData.getEvtIdnNo()))
                    return mapping.findForward(FORWARD_UPDATE_INHERIT_FAIL);

                if (isAssignAlsoEvtOrPayee(session, detailForm.getAssignIdnNo(), detailForm.getBenIdnNo(), queryData.getEvtIdnNo()))
                    return mapping.findForward(FORWARD_UPDATE_INHERIT_FAIL);
            }

            // String payeeDataCount = updateService.getPayeeDataCountForDisabledUpdate(caseData.getApNo(),detailForm.getBenIdnNo(), "",caseData.getBaappbaseId().toString(), caseData.getSeqNo());
            // if (Integer.parseInt(payeeDataCount) > 0) {
            // saveMessages(request.getSession(), DatabaseMessageHelper.getBenNameExistMessage());
            // return mapping.findForward(FORWARD_UPDATE_INHERIT_FAIL);
            // }

            if (detailForm != null) {
                SurvivorPayeeDataUpdateCase detailData = new SurvivorPayeeDataUpdateCase();
                BeanUtility.copyProperties(detailData, detailForm);

                // 將畫面上欄位轉換為西元日期
                detailData = convertCaseDateToWesternStyle(detailData, detailForm);

                // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                detailData = updateService.transSurvivorPayeeUpdateData(detailData, caseData, "U");

                // 判斷給付方式二選一
                if (detailForm.getPayCategory().equals("1")) {
                    detailData.setAccSeqNo(detailData.getSeqNo());
                }
                // else if (detailForm.getPayCategory().equals("2")){ detailData.setPayTyp(""); }

                // 取得需記錄異動LOG的欄位資料
                // 為不影響前端頁面顯示, 故須複製一份 Form
                SurvivorPayeeDataUpdateDetailForm tempForm = new SurvivorPayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(tempForm, detailForm);
                BeanUtility.copyProperties(tempForm, detailData);

                // 給付資料更正主檔 改前改後值 for BAAPPLOG
                detailData.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 存檔
                if (StringUtils.isBlank(detailData.getAbleApsYm())) {
                    detailData.setAbleApsYm("");// 繼承人不會有得請領起月欄位
                }
                updateService.updateDataForSurvivorPayeeData(userData, queryData, detailData, null, "N", null, null);
                
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
                    log.error(LOG_INFO_DOCONFIRMINHERIT_CHECKMARK_FAIL + ExceptionUtility.getStackTrace(e));
                }
                log.debug(LOG_INFO_DOCONFIRMINHERIT_CHECKMARK_RESULT + returnCode);
                // 更新清單資料
                SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);
                CaseSessionHelper.setSurvivorPayeeDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(queryForm.getApNo(), "0000"), request);
                CaseSessionHelper.setSurvivorPayeeEvtDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(queryForm.getApNo(), null), request);

                if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                    saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
                    return mapping.findForward(FORWARD_UPDATE_INHERIT_SUCCESS);
                }

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());

                log.debug(LOG_INFO_DOCONFIRMINHERIT_COMPLETE);

                // 修改完成後,不回到列表頁面,而是停留在原頁
                log.debug(LOG_INFO_DOCONFIRMINHERIT_REFETCH_START);
                // 清除明細資料畫面
                FormSessionHelper.removeSurvivorPayeeDataUpdateDetailForm(request);
                CaseSessionHelper.removeSurvivorPayeeDataUpdateDetailCase(request);
                try {
                    // 透過baappbaseId取得該受款人資料
                    String baappbaseId = caseData.getBaappbaseId().toString();
                    BaappbaseDataUpdateCase benDetail = updateService.getBaappbaseDetail(new BigDecimal(baappbaseId), null, null, null);
                    Baappbase upperBen = updateService.getDisabledAncestorMainData(benDetail.getApNo(), benDetail.getSeqNo());
                    request.getSession().setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_INHERIT_FROM, upperBen);

                    // 重新抓取資料 - 直接用舊有的SQL即可
                    Baappbase requeryDetail = updateService.getPayeeDataUpdateForUpdateCaseBy(baappbaseId);
                    // 重新抓取資料 - 還要再抓延伸主檔 並設至Form及Case中
                    Baappexpand detailBaappexpandData = updateService.getSurvivorPayeeDataUpdateForUpdateCaseBy(requeryDetail.getBaappbaseId());
                    // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                    SurvivorPayeeDataUpdateDetailForm updateForm = new SurvivorPayeeDataUpdateDetailForm();
                    BeanUtility.copyPropertiesForUpdate(updateForm, requeryDetail, ConstantKey.OLD_FIELD_PREFIX);

                    // 帳號 分成兩個欄位或一個欄位
                    if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(requeryDetail.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(requeryDetail.getPayTyp())) {
                        updateForm.setPayBankIdBranchId(requeryDetail.getPayBankId() + requeryDetail.getBranchId());
                    }
                    else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(requeryDetail.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(requeryDetail.getPayTyp())) {
                        updateForm.setPayAccount(requeryDetail.getPayBankId() + requeryDetail.getBranchId() + requeryDetail.getPayEeacc());
                    }

                    // 將Baappexpand資料設至Form
                    BigDecimal monIncomeBigDecimal = detailBaappexpandData.getMonIncome();
                    if (monIncomeBigDecimal != null) {
                        if (StringUtils.isNotBlank(monIncomeBigDecimal.toString())) {
                            updateForm.setMonIncome(monIncomeBigDecimal.toString());
                        }
                    }
                    updateForm.setBaappexpandId(StringUtils.defaultString(detailBaappexpandData.getBaappexpandId().toString()));
                    updateForm.setBaappbaseId(new BigDecimal(StringUtils.defaultString(detailBaappexpandData.getBaappbaseId().toString())));
                    updateForm.setMarryDate(StringUtils.defaultString(detailBaappexpandData.getMarryDate()));
                    updateForm.setMonIncomeMk(detailBaappexpandData.getMonIncomeMk());
                    updateForm.setHandicapMk(StringUtils.defaultString(detailBaappexpandData.getHandIcapMk()));
                    updateForm.setStudMk(StringUtils.defaultString(detailBaappexpandData.getStudMk()));
                    updateForm.setAbanApplyMk(StringUtils.defaultString(detailBaappexpandData.getAbanApplyMk()));
                    updateForm.setAbanApSym(StringUtils.defaultString(detailBaappexpandData.getAbanApsYm()));
                    updateForm.setRaiseChildMk(StringUtils.defaultString(detailBaappexpandData.getRaiseChildMk()));
                    updateForm.setInterdictMk(StringUtils.defaultString(detailBaappexpandData.getInterDictMk()));
                    updateForm.setInterdictDate(StringUtils.defaultString(detailBaappexpandData.getInterDictDate()));
                    updateForm.setBenMissingSdate(StringUtils.defaultString(detailBaappexpandData.getBenMissingSdate()));
                    updateForm.setBenMissingEdate(StringUtils.defaultString(detailBaappexpandData.getBenMissingEdate()));
                    updateForm.setPrisonSdate(StringUtils.defaultString(detailBaappexpandData.getPrisonSdate()));
                    updateForm.setPrisonEdate(StringUtils.defaultString(detailBaappexpandData.getPrisonEdate()));
                    updateForm.setRelatChgDate(StringUtils.defaultString(detailBaappexpandData.getRelatChgDate()));
                    updateForm.setAdoptDate(StringUtils.defaultString(detailBaappexpandData.getAdoPtDate()));
                    updateForm.setAssignBrDate(StringUtils.defaultString(detailBaappexpandData.getAssignBrDate()));
                    updateForm.setAssignName(StringUtils.defaultString(detailBaappexpandData.getAssignName()));
                    updateForm.setAssignIdnNo(StringUtils.defaultString(detailBaappexpandData.getAssignIdnNo()));
                    updateForm.setRaiseEvtMk(StringUtils.defaultString(detailBaappexpandData.getRaiseEvtMk()));
                    updateForm.setSavingMk(StringUtils.defaultString(detailBaappexpandData.getSavingMk()));
                    // 變更日期為民國年
                    updateForm = convertDateToChineseYear(updateForm);
                    detailBaappexpandData = convertDateToChineseYear(detailBaappexpandData);

                    List<SurvivorPayeeDataUpdateCase> list = CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase(request);
                    List<SurvivorPayeeDataUpdateCase> evtlist = CaseSessionHelper.getSurvivorPayeeEvtDataUpdateQueryCase(request);
                    setNecessaryDropDownMenu(request, evtlist.get(0), baappbaseId, updateForm.getBenEvtRel());

                    // 檢查是否有共同具名領取的受款人, 若為true 則具名領取的選項及下拉選單要設為disable
                    // boolean toDisableCoreceiver = updateService.hasCoreceiver(list.get(0).getApNo(), list.get(0).getSeqNo());

                    // 檢查是否有共同具名領取的受款人, 若為true 則具名領取的選項及下拉選單要設為disable
                    boolean toDisableCoreceiver = updateService.hasCoreceiver(detailData.getApNo(), detailData.getSeqNo());
                    // 檢查具名領取下拉選單的size,size <= 0則畫面將具名領取選單隱藏起來
                    boolean toHideCoreceiveNameListDropDownMenu = (getCoreceiveNameListDropDownMenu(request)).isEmpty();

                    request.getSession().setAttribute("toHideCoreceiveNameListDropDownMenu", toHideCoreceiveNameListDropDownMenu);
                    CaseSessionHelper.setSurvivorPayeeDataUpdateDetailCase(requeryDetail, detailBaappexpandData, false, toDisableCoreceiver, "", request);
                    FormSessionHelper.setSurvivorPayeeDataUpdateDetailForm(updateForm, request);
                }
                catch (Exception e) {
                    log.error(LOG_INFO_DOCONFIRMINHERIT_REFETCH_ERROR + ExceptionUtility.getStackTrace(e));
                    saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
                    return mapping.findForward(FORWARD_UPDATE_INHERIT_FAIL);
                }
            }
            return mapping.findForward(FORWARD_UPDATE_INHERIT_SUCCESS);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOCONFIRMINHERIT_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(FORWARD_UPDATE_INHERIT_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 修改受款人頁面 <br/> (bamo0d283c.jsp) <br/> 按下在學期間維護按鈕
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareMaintainStudterm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPREPAREMAINTAINSTUDTERM_START);
        try {
            SurvivorPayeeDataUpdateDetailForm detailForm = (SurvivorPayeeDataUpdateDetailForm) form;
            SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);
            Baappexpand expand = CaseSessionHelper.getSurvivorPayeeDataUpdateDetailBaappexpandCase(request);
            expand.setStudMk("Y");

            // 取得在學清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<Bastudterm> list = CaseSessionHelper.getSurvivorPayeeDataUpdateStudtermList(request);
            if (list == null) {
                list = updateService.selectStudtermListForSurvivorPayeeDataUpdate(queryForm.getApNo(), detailForm.getSeqNo());
            }

            FormSessionHelper.setSurvivorPayeeDataUpdateDetailForm(detailForm, request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateDetailBaappexpandCase(request, expand);
            CaseSessionHelper.setSurvivorPayeeDataUpdateStudtermList(list, request);

            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_MODIFY_STUDTERM);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error(LOG_INFO_DOPREPAREMAINTAIN_COMPELDATA_ERROR + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 新增受款人頁面 <br/> (bamo0d281a.jsp) <br/> 按下在學期間維護按鈕
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareInsertStudterm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPREPAREINSERTSTUDTERM_START);
        try {
            SurvivorPayeeDataUpdateDetailForm detailForm = (SurvivorPayeeDataUpdateDetailForm) form;

            // 取得在學清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<Bastudterm> list = CaseSessionHelper.getSurvivorPayeeDataUpdateStudtermList(request);
            if (list == null) {
                list = new ArrayList<Bastudterm>(0);
            }

            FormSessionHelper.setSurvivorPayeeDataUpdateDetailForm(detailForm, request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateStudtermList(list, request);
            request.getSession().setAttribute("StudTermMode", "I");

            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_INSERT_STUDTERM);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error(LOG_INFO_DOPREPAREINSERT_COMPELDATA_ERROR + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 修改受款人頁面 <br/> (bamo0d283c.jsp) <br/> 按下重殘起訖年月維護按鈕
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareMaintainHandicapterm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPREPAREMAINTAINHANDICAPTERM_START);
        try {
            SurvivorPayeeDataUpdateDetailForm detailForm = (SurvivorPayeeDataUpdateDetailForm) form;
            SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);
            Baappexpand expand = CaseSessionHelper.getSurvivorPayeeDataUpdateDetailBaappexpandCase(request);
            expand.setHandIcapMk("Y");

            // 取得重殘起訖年度清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<Bahandicapterm> list = CaseSessionHelper.getSurvivorPayeeDataUpdateHandicaptermList(request);
            if (list == null) {
                list = updateService.selectHandicaptermListForSurvivorPayeeDataUpdate(queryForm.getApNo(), detailForm.getSeqNo());
            }

            FormSessionHelper.setSurvivorPayeeDataUpdateDetailForm(detailForm, request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateDetailBaappexpandCase(request, expand);
            CaseSessionHelper.setSurvivorPayeeDataUpdateHandicaptermList(list, request);

            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_MODIFY_HANDICAPTERM);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error(LOG_INFO_DOPREPAREMAINTAINHANDICAPTERM_ERROR + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }
    
    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 修改受款人頁面 <br/> (bamo0d283c.jsp) <br/> 按下強迫不合格註記維護按鈕
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareMaintainCompelData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPREPAREMAINTAIN_COMPELDATA_START);
        try {
            SurvivorPayeeDataUpdateDetailForm detailForm = (SurvivorPayeeDataUpdateDetailForm) form;
            SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);
            Baappexpand expand = CaseSessionHelper.getSurvivorPayeeDataUpdateDetailBaappexpandCase(request);
            expand.setCompelMk("Y");

            // 取得不合格年月資料清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<SurvivorPayeeDataUpdateCompelDataCase> list = CaseSessionHelper.getSurvivorPayeeDataUpdateCompelDataList(request);
            if (list == null) {
                list = updateService.getCompelDataList(queryForm.getApNo(), detailForm.getSeqNo());
            }

            FormSessionHelper.setSurvivorPayeeDataUpdateDetailForm(detailForm, request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateDetailBaappexpandCase(request, expand);
            CaseSessionHelper.setSurvivorPayeeDataUpdateCompelDataList(list, request);

            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_MODIFY_COMPELDATA);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error(LOG_INFO_DOPREPAREMAINTAIN_COMPELDATA_ERROR + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 新增受款人頁面 <br/> (bamo0d281a.jsp) <br/> 按下強迫不合格註記維護按鈕
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareInsertCompelData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPREPAREINSERT_COMPELDATA_START);
        try {
            SurvivorPayeeDataUpdateDetailForm detailForm = (SurvivorPayeeDataUpdateDetailForm) form;

            // 取得不合格年月資料清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<SurvivorPayeeDataUpdateCompelDataCase> list = CaseSessionHelper.getSurvivorPayeeDataUpdateCompelDataList(request);
            if (list == null) {
                list = new ArrayList<SurvivorPayeeDataUpdateCompelDataCase>(0);
            }

            FormSessionHelper.setSurvivorPayeeDataUpdateDetailForm(detailForm, request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateCompelDataList(list, request);
            request.getSession().setAttribute("CompelDataMode", "I");

            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_INSERT_COMPELDATA);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error(LOG_INFO_DOPREPAREINSERT_COMPELDATA_ERROR + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 各新增及修改頁面 <br/> (bamo0d281a.jsp, bamo0d283c.jsp, bamo0d287a.jsp, bamo0d288c.jsp) <br/> 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOBACKLIST_START);
        CaseSessionHelper.removeSurvivorPayeeDataUpdateDetailCase(request);
        FormSessionHelper.removeSurvivorPayeeDataUpdateDetailForm(request);
        request.getSession().removeAttribute("SURVIVOR_PAYEE_STUDTERM_SIZE");
        request.getSession().removeAttribute("SURVIVOR_PAYEE_COMPELDATA_SIZE");
        return mapping.findForward(FORWARD_MODIFY_SURVIVOR_PAYEE_DATA_LIST);
    }

    /**
     * 檢核新增的法定代理人身分證號不可為其他已死的受款人
     * 
     * @param request
     * @param grdIdnNo
     * @param apNo
     * @return
     */
    private boolean isGrdAlreadyDead(HttpServletRequest request, String grdIdnNo, String apNo) {
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
     * 檢核新增的代辦人是否為事故者或受款人
     * 
     * @param session
     * @param assignIdentity
     * @param benIdentity
     * @param evtIdentity
     * @return
     */
    private boolean isAssignAlsoEvtOrPayee(HttpSession session, String assignIdentity, String benIdentity, String evtIdentity) {
        if (assignIdentity.equals(benIdentity) || assignIdentity.equals(evtIdentity)) {
            saveMessages(session, DatabaseMessageHelper.getGrdNameExistMessage());
            return true;
        }
        return false;
    }

    /**
     * 檢核新增的法定代理人是否為事故者或受款人
     * 
     * @param session
     * @param grdIdentity
     * @param benIdentity
     * @param evtIdentity
     * @return
     */
    private boolean isGrdAlsoEvtOrPayee(HttpSession session, String grdIdentity, String benIdentity, String evtIdentity) {
        if (grdIdentity.equals(benIdentity) || grdIdentity.equals(evtIdentity)) {
            saveMessages(session, DatabaseMessageHelper.getGrdNameExistMessage());
            return true;
        }
        return false;
    }

    /**
     * 後端檢查銀行帳號是否輸入正確
     * 
     * @param request
     * @param detailForm
     * @return
     */
    private boolean isNotValidBankAccount(HttpServletRequest request, SurvivorPayeeDataUpdateDetailForm detailForm) {
        if ("1".equals(detailForm.getPayCategory())) {
            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(detailForm.getPayTyp())) {
                if (StringUtils.isNotBlank(detailForm.getPayBankIdBranchId())) {
                    if (!StringUtils.isAlphanumeric(detailForm.getPayBankIdBranchId())) {
                        saveMessages(request.getSession(), CustomMessageHelper.getBankIdErrorMessage());
                        return true;
                    }
                }
                if (!updateService.checkBankAccount(detailForm.getPayBankIdBranchId())) {
                    saveMessages(request.getSession(), CustomMessageHelper.getBankIdErrorMessage());
                    return true;
                }
            }
            else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(detailForm.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(detailForm.getPayTyp())) {
                if (StringUtils.isNotBlank(detailForm.getPayAccount())) {
                    if (!StringUtils.isAlphanumeric(detailForm.getPayAccount())) {
                        saveMessages(request.getSession(), CustomMessageHelper.getBankIdErrorMessage());
                        return true;
                    }
                }
            }
            else if (((ConstantKey.BAAPPBASE_PAYTYP_7).equals(detailForm.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_8).equals(detailForm.getPayTyp())) && !("7000010").equals(detailForm.getPayBankIdBranchId())
                            && !("7000021").equals(detailForm.getPayBankIdBranchId())) {
                if (!updateService.checkBankAccount(detailForm.getPayBankIdBranchId())) {
                    saveMessages(request.getSession(), CustomMessageHelper.getBankIdErrorMessage());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 抓取畫面下拉選單之資料
     * 
     * @param request
     * @param evtCase
     * @param selfBaappbaseId
     */
    private void setNecessaryDropDownMenu(HttpServletRequest request, SurvivorPayeeDataUpdateCase evtCase, String selfBaappbaseId, String benEvtRel) {
        setCountryDropDownMenu(request);
        setCloseCauseDropDownMenu(request);
        setRelationDropDownMenu(request, benEvtRel);
        setPayTypeDropDownMenu(request, benEvtRel);
        setCoreceiveNameListDropDownMenu(request, evtCase.getBaappbaseId(), evtCase.getApNo(), selfBaappbaseId);
        setAbleApsymMenu(request, evtCase.getEvtDieDateStr());
    }
}
