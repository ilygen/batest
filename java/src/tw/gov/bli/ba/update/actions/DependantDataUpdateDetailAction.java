package tw.gov.bli.ba.update.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.iisi.SecureToken;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Bafamily;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.DependantDataUpdateCase;
import tw.gov.bli.ba.update.cases.DependantEvtDataUpdateCase;
import tw.gov.bli.ba.update.cases.DependantDataUpdateCompelDataCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCompelDataCase;
import tw.gov.bli.ba.update.forms.DependantDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.DependantDataUpdateQueryForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;

public class DependantDataUpdateDetailAction extends BaseDispatchAction {

    private static Log log = LogFactory.getLog(PayeeDataUpdateDetailAction.class);

    private UpdateService updateService;
    private SelectOptionService selectOptionService;

    // 更正作業 - 眷屬資料更正 - 眷屬資料清單頁面
    private static final String FORWARD_MODIFY_DEPENDANT_DATA_LIST = "modifyDependantDataList";
    // 更正作業 - 眷屬資料更正 - 眷屬新增在學期間頁面
    private static final String FORWARD_INSERT_STUDTERM = "insertStudterm";
    // 更正作業 - 眷屬資料更正 - 眷屬修改在學期間頁面
    private static final String FORWARD_MODIFY_STUDTERM = "modifyStudterm";    
    // 更正作業 - 眷屬資料更正 - 眷屬新增不合格年月頁面
    private static final String FORWARD_INSERT_COMPELDATA = "insertCompelData";
    // 更正作業 - 眷屬資料更正 - 眷屬修改不合格年月頁面
    private static final String FORWARD_MODIFY_COMPELDATA = "modifyCompelData";

    /**
     * 更正作業 - 眷屬資料更正作業 - 新增頁面 (bamo0d071a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */

    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 新增頁面 DependantDataUpdateDetailAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DependantDataUpdateDetailForm dependantDetailForm = (DependantDataUpdateDetailForm) form;

        List<DependantDataUpdateCase> queryList = CaseSessionHelper.getDependantDataUpdateQueryCase(request);
        List<DependantEvtDataUpdateCase> evtQueryList = CaseSessionHelper.getDependantEvtDataUpdateQueryCase(request);
        // DependantDataUpdateCase queryData = (DependantDataUpdateCase) queryList.get(0);
        DependantEvtDataUpdateCase evtQueryData = (DependantEvtDataUpdateCase) evtQueryList.get(0);

        Bafamily bafamily = new Bafamily();
        // BeanUtility.copyProperties(bafamily, queryData);
        bafamily.setApNo(evtQueryData.getApNo());
        bafamily.setEvtIdnNo(evtQueryData.getEvtIdnNo());
        bafamily.setEvtBrDate(evtQueryData.getEvtBrDate());
        bafamily.setEvtName(evtQueryData.getEvtName());
        bafamily.setBaappbaseId(evtQueryData.getBaappbaseId());
        bafamily.setAppDate(evtQueryData.getAppDate());

        try {

            String apNo = bafamily.getApNo();
            String famIdnNo = dependantDetailForm.getFamIdnNo();
            String famBrDate = DateUtility.changeDateType(dependantDetailForm.getFamBrDate());

            Integer dependantDataCount = updateService.getDependantDataCount(apNo, famIdnNo, null);

            // 檢核新增的眷屬資料是否已存在
            if (dependantDataCount > 0) {
                saveMessages(session, DatabaseMessageHelper.getBenNameExistMessage());
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            if (dependantDetailForm != null) {
                DependantDataUpdateCase dependateData = new DependantDataUpdateCase();
                BeanUtility.copyProperties(dependateData, dependantDetailForm);
                Integer dependantSeqNoDataCount = 0;

                // 眷屬序號

                if (queryList.size() > 0 || updateService.getDependantSeqNoDataCount(apNo) != null)
                    dependantSeqNoDataCount = updateService.getDependantSeqNoDataCount(apNo) + 1;
                else
                    dependantSeqNoDataCount = dependantSeqNoDataCount + 1;

                dependateData.setSeqNo(dependantSeqNoDataCount < 10 ? "0" + dependantSeqNoDataCount.toString() : dependantSeqNoDataCount.toString());

                // 每月工作收入
                if (("Y").equals(dependantDetailForm.getMonIncome())) {
                    dependateData.setMonIncome(new BigDecimal(dependantDetailForm.getMonIncome()));
                }

                // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                dependateData = updateService.transDependantUpdateData(dependateData, bafamily, "I");

                // 取得在學期間資料List
                List<Bastudterm> termList = CaseSessionHelper.getDependantDataUpdateStudtermList(request);
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
                
                // 取得不合格年月資料List
                List<DependantDataUpdateCompelDataCase> compelDataList = CaseSessionHelper.getDependantDataUpdateCompelDataList(request);
                // 將在學起迄轉為西元年
                if (compelDataList != null) {
                    for (DependantDataUpdateCompelDataCase o : compelDataList) {
                        // 若是民國年(5碼)才轉為西元年月
                        if (StringUtils.isNotBlank(o.getCompelSdate()) && o.getCompelSdate().length() == 5) {
                            o.setCompelSdate(DateUtility.changeChineseYearMonthType(o.getCompelSdate()));
                        }
                        if (StringUtils.isNotBlank(o.getCompelEdate()) && o.getCompelEdate().length() == 5) {
                            o.setCompelEdate(DateUtility.changeChineseYearMonthType(o.getCompelEdate()));
                        }

                    }
                }                

                // 存檔
                updateService.insertDependantData(userData, evtQueryData, dependateData, termList, compelDataList);
                
                // 案件類別為 2 或 4 時，呼叫 sp_BA_Get_CIPB
                if(evtQueryData.getCaseTyp().equals("2") || evtQueryData.getCaseTyp().equals("4")){
                    // 呼叫StoreProcedure sp_BA_Get_CIPB(眷屬身份證號,事故日期【NULL】)
                    updateService.doGetCipb(apNo, dependateData.getSeqNo(), dependateData.getFamAppDate(), dependateData.getFamIdnNo(), null);
                }

                // 呼叫即時編審WebService
                // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
                String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
                log.info("webServiceUrl: "+ webServiceUrl);
                SingleCheckMarkServiceHttpBindingStub binding;
                binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator().getSingleCheckMarkServiceHttpPort();

                // 呼叫即時編審
                String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
                try {
                    returnCode = binding.doCheckMark(evtQueryData.getApNo(),SecureToken.getInstance().getToken());
                }
                catch (Exception e) {
                    log.error("執行  更正作業 - 眷屬資料更正作業 - 新增頁面 DependantDataUpdateDetailAction.doSave() 即時編審發生錯誤:" + ExceptionUtility.getStackTrace(e));
                }

                log.debug("執行 更正作業 - 眷屬資料更正作業 - 新增頁面 - 呼叫即時編審結果... " + returnCode);

                List<DependantDataUpdateCase> dependentList = updateService.selectDependantDataForList(apNo);
                CaseSessionHelper.setDependantDataUpdateQueryCase(dependentList, request);

                // 設定更新訊息
                if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                    // 設定即時編審失敗訊息
                    saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
                }

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 更正作業 - 眷屬資料更正作業 - 新增頁面  DependantDataUpdateDetailAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DependantDataUpdateDetailAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }

    }

    /**
     * 更正作業 - 眷屬資料更正作業 - 修改頁面 (bamo0d073c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 修改頁面 DependantDataUpdateDetailAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        try {
            DependantDataUpdateDetailForm detailForm = (DependantDataUpdateDetailForm) form;
            // 取得查詢List
            List<DependantDataUpdateCase> queryList = CaseSessionHelper.getDependantDataUpdateQueryCase(request);
            DependantDataUpdateCase queryData = (DependantDataUpdateCase) queryList.get(0);
            // 取得改前的更正資料
            DependantDataUpdateCase caseData = CaseSessionHelper.getDependantDataUpdateDetailCase(request);
            Bafamily bafamilyList = new Bafamily();
            BeanUtility.copyProperties(bafamilyList, caseData);
            String apNo = queryData.getApNo();
            String idnChkYm = detailForm.getIdnChkY() + detailForm.getIdnChkM();

            List<DependantEvtDataUpdateCase> dependentEvtList = updateService.selectDependantEvtDataForList(apNo);

            if (detailForm != null) {
                DependantDataUpdateCase detailData = new DependantDataUpdateCase();
                BeanUtility.copyProperties(detailData, detailForm);
                detailData.setIdnChkYm(idnChkYm);

                // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                detailData = updateService.transDependantUpdateData(detailData, bafamilyList, "U");

                // 查核年月 兩個欄位合一
                if (StringUtils.equals(detailForm.getIdnChkNote(), "1")) {
                    detailData.setIdnChkYm(DateUtility.calMonth(DateUtility.getNowWestDate(), 13).substring(0, 6));
                    detailForm.setIdnChkYm(DateUtility.calMonth(DateUtility.getNowWestDate(), 13).substring(0, 6));
                }
                else if (StringUtils.equals(detailForm.getIdnChkNote(), "2")) {
                    detailData.setIdnChkYm(DateUtility.changeChineseYearMonthType(detailForm.getIdnChkY() + detailForm.getIdnChkM()));
                    detailForm.setIdnChkYm(DateUtility.changeChineseYearMonthType(detailForm.getIdnChkY() + detailForm.getIdnChkM()));
                }
                else {
                    detailData.setIdnChkNote(detailForm.getOldIdnChkNote());
                    detailData.setIdnChkYm(detailForm.getOldIdnChkYm());
                    detailForm.setIdnChkNote(detailForm.getOldIdnChkNote());
                    detailForm.setIdnChkYm(detailForm.getOldIdnChkYm());
                }
                
                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                DependantDataUpdateDetailForm updateForm = new DependantDataUpdateDetailForm();
                BeanUtility.copyPropertiesForUpdate(updateForm, detailData, ConstantKey.OLD_FIELD_PREFIX);

                // 取得需記錄異動LOG的欄位資料
                // 為不影響前端頁面顯示, 故須複製一份 Form
                DependantDataUpdateDetailForm tempForm = new DependantDataUpdateDetailForm();
                BeanUtility.copyProperties(tempForm, detailForm);
                tempForm.setFamAppDate(DateUtility.changeDateType(tempForm.getFamAppDate()));
                tempForm.setMarryDate(DateUtility.changeDateType(tempForm.getMarryDate()));
                tempForm.setAbanApsYm(DateUtility.changeChineseYearMonthType(tempForm.getAbanApsYm()));
                detailData.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_DEPENDANT_FIELD_RESOURCE_PREFIX));

                // 取得在學期間資料List
                List<Bastudterm> termList = CaseSessionHelper.getDependantDataUpdateStudtermList(request);
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
                
                // 取得不合格年月資料List
                List<DependantDataUpdateCompelDataCase> compelDataList = CaseSessionHelper.getDependantDataUpdateCompelDataList(request);
                // 將在學起迄轉為西元年
                if (compelDataList != null) {
                    for (DependantDataUpdateCompelDataCase o : compelDataList) {
                        // 若是民國年(5碼)才轉為西元年月
                        if (StringUtils.isNotBlank(o.getCompelSdate()) && o.getCompelSdate().length() == 5) {
                            o.setCompelSdate(DateUtility.changeChineseYearMonthType(o.getCompelSdate()));
                        }
                        if (StringUtils.isNotBlank(o.getCompelEdate()) && o.getCompelEdate().length() == 5) {
                            o.setCompelEdate(DateUtility.changeChineseYearMonthType(o.getCompelEdate()));
                        }

                    }
                }                

                // 存檔
                updateService.updateDependantData(userData, detailData, bafamilyList, termList, dependentEvtList.get(0).getCaseTyp(), compelDataList);

                // 案件類別為 2 或 4 時，呼叫 sp_BA_Get_CIPB
                if(dependentEvtList.get(0).getCaseTyp().equals("2") || dependentEvtList.get(0).getCaseTyp().equals("4")){
                    // 呼叫StoreProcedure sp_BA_Get_CIPB(眷屬身份證號,事故日期【NULL】)
                    updateService.doGetCipb(apNo, detailData.getSeqNo(), detailData.getFamAppDate(), detailData.getFamIdnNo(), null);
                }                

                // 呼叫即時編審WebService
                // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
                String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
                log.info("webServiceUrl: "+ webServiceUrl);
                SingleCheckMarkServiceHttpBindingStub binding;
                binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator().getSingleCheckMarkServiceHttpPort();
                // 呼叫即時編審
                String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
                try {
                    returnCode = binding.doCheckMark(queryData.getApNo(),SecureToken.getInstance().getToken());
                }
                catch (Exception e) {
                    log.error("執行  更正作業 - 眷屬資料更正作業 - 修改頁面 DependantDataUpdateDetailAction.doSave() 即時編審發生錯誤:" + ExceptionUtility.getStackTrace(e));
                }

                log.debug("執行 更正作業 - 眷屬資料更正作業 - 修改頁面 - 呼叫即時編審結果... " + returnCode);
                // 設定更新訊息
                if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                    // 設定即時編審失敗訊息
                    saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
                }

                String bafamilyId = detailData.getBafamilyId().toString();

                // 取得關係下拉選單
                request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getDependantRelationOptionList());
                // 取得國籍清單
                request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                // 取得結案原因
                request.getSession().setAttribute(ConstantKey.CLOSE_OPTION_LIST, selectOptionService.getDependantCloseOptionList());

                // 重新抓取資料
                DependantDataUpdateCase detailCaseData = updateService.getDependantDataUpdateForUpdateCaseBy(bafamilyId);
                termList = updateService.selectStudtermListForSurvivorPayeeDataUpdate(detailData.getApNo(), detailData.getSeqNo());
                // 取得是否顯示身份查核欄位狀態
                Integer checkStatus = updateService.getDataCount2ForDependant(detailData.getApNo(), detailData.getSeqNo(), dependentEvtList.get(0).getIssuYm());

                // 取得 眷屬編審註記資料
                List<PaymentQueryDetailDataCase> benChkList = updateService.getPaymentQueryOtherChkList(detailCaseData.getApNo(), "0000", "B", detailCaseData.getSeqNo());
                // 取得 符合註記資料
                List<PaymentQueryDetailDataCase> matchChkList = updateService.getPaymentQueryOtherChkList(detailCaseData.getApNo(), "0000", "C", detailCaseData.getSeqNo());
                // 重新取得不合格年月清單資料
                List<DependantDataUpdateCompelDataCase> refetchedCompelDataList = updateService.getCompelDataListForDependant(detailCaseData.getApNo(), detailCaseData.getSeqNo());                

                List<DependantDataUpdateCase> dependentList = updateService.selectDependantDataForList(apNo);
                CaseSessionHelper.setDependantDataUpdateQueryCase(dependentList, request);

                BeanUtility.copyPropertiesForUpdate(updateForm, detailCaseData, ConstantKey.OLD_FIELD_PREFIX);

                // 取得是否顯示刪除鍵狀態
                BigDecimal deleteStatus = updateService.getDependantDataCount(apNo);

                // 變更日期為民國年

                // 眷屬申請日期
                if (StringUtils.isNotBlank(updateForm.getFamAppDate())) {
                    updateForm.setFamAppDate(DateUtility.changeDateType(updateForm.getFamAppDate().trim()));
                }
                // 眷屬出生日期
                if (StringUtils.isNotBlank(updateForm.getFamBrDate())) {
                    updateForm.setFamBrDate(detailData.getFamBrDateStr());
                }

                // 眷屬死亡日期
                if (StringUtils.isNotBlank(updateForm.getFamDieDate())) {
                    updateForm.setFamDieDate(DateUtility.changeDateType(updateForm.getFamDieDate().trim()));
                }

                // 放棄請領起始年月
                if (StringUtils.isNotBlank(updateForm.getAbanApsYm())) {
                    updateForm.setAbanApsYm(DateUtility.changeWestYearMonthType(updateForm.getAbanApsYm().trim()));
                }

                // 受禁治產宣告日期
                if (StringUtils.isNotBlank(updateForm.getInterDictDate())) {
                    updateForm.setInterDictDate(DateUtility.changeDateType(updateForm.getInterDictDate().trim()));
                }

                // 禁治產撤消日期
                if (StringUtils.isNotBlank(updateForm.getRepealInterDictDate())) {
                    updateForm.setRepealInterDictDate(DateUtility.changeDateType(updateForm.getRepealInterDictDate().trim()));
                }

                // 親屬關係變動日期
                if (StringUtils.isNotBlank(updateForm.getRelatChgDate())) {
                    updateForm.setRelatChgDate(DateUtility.changeDateType(detailCaseData.getRelatChgDate().trim()));
                }

                // 收養日期
                if (StringUtils.isNotBlank(updateForm.getAdoPtDate())) {
                    updateForm.setAdoPtDate(DateUtility.changeDateType(updateForm.getAdoPtDate().trim()));
                }

                // 受益人失蹤期間(起)
                if (StringUtils.isNotBlank(updateForm.getBenMissingSdate())) {
                    updateForm.setBenMissingSdate(DateUtility.changeDateType(updateForm.getBenMissingSdate().trim()));
                }

                // 受益人失蹤期間(迄)
                if (StringUtils.isNotBlank(updateForm.getBenMissingEdate())) {
                    updateForm.setBenMissingEdate(DateUtility.changeDateType(updateForm.getBenMissingEdate().trim()));
                }

                // 監管期間(起)
                if (StringUtils.isNotBlank(updateForm.getPrisonSdate())) {
                    updateForm.setPrisonSdate(DateUtility.changeDateType(updateForm.getPrisonSdate().trim()));
                }

                // 監管期間(迄)
                if (StringUtils.isNotBlank(updateForm.getPrisonEdate())) {
                    updateForm.setPrisonEdate(DateUtility.changeDateType(updateForm.getPrisonEdate().trim()));
                }

                // 結婚日期
                if (StringUtils.isNotBlank(updateForm.getMarryDate())) {
                    updateForm.setMarryDate(DateUtility.changeDateType(updateForm.getMarryDate().trim()));
                }

                // 離婚日期
                if (StringUtils.isNotBlank(updateForm.getDivorceDate())) {
                    updateForm.setDivorceDate(DateUtility.changeDateType(updateForm.getDivorceDate().trim()));
                }

                // 在學起始年月
                if (StringUtils.isNotBlank(updateForm.getStudSdate())) {
                    updateForm.setStudSdate(DateUtility.changeWestYearMonthType(updateForm.getStudSdate().trim()));
                }

                // 在學結束年月
                if (StringUtils.isNotBlank(updateForm.getStudEdate())) {
                    updateForm.setStudEdate(DateUtility.changeWestYearMonthType(updateForm.getStudEdate().trim()));
                }
                
                // 得請領起月
                if (StringUtils.isNotBlank(updateForm.getAbleApsYm())) {
                    updateForm.setAbleApsYm(DateUtility.changeWestYearMonthType(updateForm.getAbleApsYm().trim()));
                }

                // 身分查核年月
                if (!"0".equals(detailData.getIdnChkNote()) && !"".equals(detailData.getIdnChkYm().trim())) {
                    updateForm.setIdnChkY(DateUtility.changeWestYearMonthType(detailData.getIdnChkYm().trim()).substring(0, 3));
                    session.setAttribute("idnChkY", DateUtility.changeWestYearMonthType(detailData.getIdnChkYm().trim()).substring(0, 3));
                }
                else {
                    session.setAttribute("idnChkY", "");
                }
                if (!"0".equals(detailData.getIdnChkNote()) && !"".equals(detailData.getIdnChkYm().trim())) {

                    updateForm.setIdnChkM(DateUtility.changeWestYearMonthType(detailData.getIdnChkYm().trim()).substring(3, 5));
                    session.setAttribute("idnChkM", DateUtility.changeWestYearMonthType(detailData.getIdnChkYm().trim()).substring(3, 5));
                }
                else {
                    session.setAttribute("idnChkM", "");
                }

                FormSessionHelper.setDependantDataUpdateDetailForm(updateForm, request);
                CaseSessionHelper.setDependantQueryBenChkFileDataCase(benChkList, request);
                CaseSessionHelper.setDependantQueryMatchChkFileDataCase(matchChkList, request);
                CaseSessionHelper.setDependantDataUpdateDetailCase(detailCaseData, request);
                CaseSessionHelper.setDependantDataUpdateStudtermList(termList, request);
                session.setAttribute("studStatus", termList.size() > 0 ? termList.size() : 0);
                session.setAttribute("checkStatus", checkStatus);
                session.setAttribute("deleteStatus", deleteStatus);
                
                CaseSessionHelper.setDependantDataUpdateCompelDataList(refetchedCompelDataList, request);
                if (refetchedCompelDataList != null && !refetchedCompelDataList.isEmpty()) {
                    request.getSession().setAttribute("DEPENDANT_COMPELDATA_SIZE", refetchedCompelDataList.size());
                }
                else {
                    request.getSession().setAttribute("DEPENDANT_COMPELDATA_SIZE", 0);
                }                

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 更正作業 - 眷屬資料更正作業 - 修改頁面  DependantDataUpdateDetailAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DependantDataUpdateDetailAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 眷屬資料更正作業 - 修改頁面 (bamo0d073c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 修改頁面 DependantDataUpdateDetailAction.doBackList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DependantDataUpdateDetailForm queryForm = (DependantDataUpdateDetailForm) form;

        try {
            // 清除 Session
            CaseSessionHelper.removePayeeDataUpdateDetailCase(request);
            FormSessionHelper.removePayeeDataUpdateDetailForm(request);
            request.getSession().removeAttribute("DEPENDANT_COMPELDATA_SIZE");

            // 更新清單資料
            DependantDataUpdateQueryForm listForm = (DependantDataUpdateQueryForm) FormSessionHelper.getDependantDataUpdateQueryForm(request);
            List<DependantDataUpdateCase> dependentList = updateService.selectDependantDataForList(listForm.getApNo1() + listForm.getApNo2() + listForm.getApNo3() + listForm.getApNo4());
            CaseSessionHelper.setDependantDataUpdateQueryCase(dependentList, request);

            // 設定更新成功訊息
            saveMessages(session, null);
            return mapping.findForward(FORWARD_MODIFY_DEPENDANT_DATA_LIST);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("PayeeDataUpdateDetailAction.doBackList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 眷屬資料更正作業 - 修改眷屬資料頁面 <br/> (bamo0d073c.jsp) <br/> 按下在學期間維護按鈕
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareMaintainStudterm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 在學期間維護 DependantDataUpdateDetailAction.doPrepareMaintainStudterm() 開始 ... ");
        HttpSession session = request.getSession();
        try {
            DependantDataUpdateDetailForm detailForm = (DependantDataUpdateDetailForm) form;
            DependantDataUpdateQueryForm queryForm = FormSessionHelper.getDependantDataUpdateQueryForm(request);

            // 取得改前的更正資料
            DependantDataUpdateCase caseData = CaseSessionHelper.getDependantDataUpdateDetailCase(request);
            caseData.setStudMk("Y");

            // 取得在學清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<Bastudterm> listDependant = CaseSessionHelper.getDependantDataUpdateStudtermList(request);

            if (listDependant == null) {

                listDependant = updateService.selectStudtermListForDependantDataUpdate(detailForm.getApNo(), detailForm.getSeqNo());
            }

            session.setAttribute("famName", detailForm.getFamName());
            session.setAttribute("famBrDate", detailForm.getFamBrDate());
            session.setAttribute("famIdnNo", detailForm.getFamIdnNo());
            FormSessionHelper.setDependantDataUpdateDetailForm(detailForm, request);
            CaseSessionHelper.setDependantDataUpdateDetailCase(caseData, request);
            CaseSessionHelper.setDependantDataUpdateStudtermList(listDependant, request);

            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_MODIFY_STUDTERM);

        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("DependantDataUpdateDetailAction.doPrepareMaintainStudterm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 眷屬資料更正作業 - 新增眷屬資料頁面 <br/> (bamo0d071a.jsp) <br/> 按下在學期間維護按鈕
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareInsertStudterm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 在學期間維護 DependantDataUpdateDetailAction.doPrepareInsertStudterm() 開始 ... ");

        HttpSession session = request.getSession();
        try {
            DependantDataUpdateDetailForm detailForm = (DependantDataUpdateDetailForm) form;
            DependantDataUpdateQueryForm queryForm = FormSessionHelper.getDependantDataUpdateQueryForm(request);

            // 取得在學清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<Bastudterm> listDependant = CaseSessionHelper.getDependantDataUpdateStudtermList(request);
            if (listDependant == null) {
                listDependant = new ArrayList<Bastudterm>(0);
            }

            session.setAttribute("famName", detailForm.getFamName());
            session.setAttribute("famBrDate", detailForm.getFamBrDate());
            session.setAttribute("famIdnNo", detailForm.getFamIdnNo());
            FormSessionHelper.setDependantDataUpdateDetailForm(detailForm, request);
            CaseSessionHelper.setDependantDataUpdateStudtermList(listDependant, request);
            request.getSession().setAttribute("StudTermMode", "I");

            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_INSERT_STUDTERM);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("DependantDataUpdateDetailAction.doPrepareInsertStudterm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }
    
    /**
     * 更正作業 - 眷屬資料更正作業 - 新增眷屬資料頁面 <br/> (bamo0d281a.jsp) <br/> 按下強迫不合格註記維護按鈕
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareInsertCompelData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 新增不合格年月 DependantDataUpdateDetailAction.doPrepareInsertCompelData() 開始 ... ");
        try {
            DependantDataUpdateDetailForm detailForm = (DependantDataUpdateDetailForm) form;

            // 取得不合格年月資料清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<DependantDataUpdateCompelDataCase> list = CaseSessionHelper.getDependantDataUpdateCompelDataList(request);
            if (list == null) {
                list = new ArrayList<DependantDataUpdateCompelDataCase>(0);
            }

            FormSessionHelper.setDependantDataUpdateDetailForm(detailForm, request);
            CaseSessionHelper.setDependantDataUpdateCompelDataList(list, request);
            request.getSession().setAttribute("CompelDataMode", "I");

            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_INSERT_COMPELDATA);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("DependantDataUpdateDetailAction.doPrepareInsertCompelData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 不合格年月維護 DependantDataUpdateDetailAction.doPrepareMaintainCompelData() 開始 ...");
        try {
            DependantDataUpdateDetailForm detailForm = (DependantDataUpdateDetailForm) form;
            DependantDataUpdateQueryForm queryForm = FormSessionHelper.getDependantDataUpdateQueryForm(request);
            ////Baappexpand expand = CaseSessionHelper.getSurvivorPayeeDataUpdateDetailBaappexpandCase(request);
            ////expand.setCompelMk("Y");

            // 取得不合格年月資料清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<DependantDataUpdateCompelDataCase> list = CaseSessionHelper.getDependantDataUpdateCompelDataList(request);
            if (list == null) {
                list = updateService.getCompelDataListForDependant(queryForm.getApNo(), detailForm.getSeqNo());
            }

            FormSessionHelper.setDependantDataUpdateDetailForm(detailForm, request);
            ////CaseSessionHelper.setDependantDataUpdateDetailBaappexpandCase(request, expand);
            CaseSessionHelper.setDependantDataUpdateCompelDataList(list, request);

            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_MODIFY_COMPELDATA);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("執行 更正作業 - 眷屬資料更正作業 - 不合格年月維護 DependantDataUpdateDetailAction.doPrepareMaintainCompelData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }    

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }

}
