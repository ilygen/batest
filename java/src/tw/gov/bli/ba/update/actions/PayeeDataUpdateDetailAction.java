package tw.gov.bli.ba.update.actions;

import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.iisi.SecureToken;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.forms.PayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.PayeeDataUpdateListForm;
import tw.gov.bli.ba.update.forms.PayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;

public class PayeeDataUpdateDetailAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(PayeeDataUpdateDetailAction.class);

    private UpdateService updateService;
    private SelectOptionService selectOptionService;

    // 更正作業 - 受款人資料更正 - 受款人資料清單頁面
    private static final String FORWARD_MODIFY_PAYEE_DATA_LIST = "modifyPayeeDataList";

    /**
     * 更正作業 - 受款人資料更正作業 - 新增頁面 (bamo0d081a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 受款人資料更正作業 - 新增頁面 PayeeDataUpdateDetailAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PayeeDataUpdateDetailForm detailForm = (PayeeDataUpdateDetailForm) form;

        // 取得查詢List
        List<BaappbaseDataUpdateCase> queryList = CaseSessionHelper.getPayeeDataUpdateQueryCase(request);
        BaappbaseDataUpdateCase queryData = (BaappbaseDataUpdateCase) queryList.get(0);

        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, queryData);

        try {
            String payeeDataCount = updateService.getPayeeDataCount(queryData.getApNo(), detailForm.getBenIdnNo(), DateUtility.changeDateType(detailForm.getBenBrDate()));

            // 檢核新增的受款人資料是否已存在
            if (Integer.parseInt(payeeDataCount) > 0) {
                saveMessages(session, DatabaseMessageHelper.getBenNameExistMessage());
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            // 檢核新增的法定代理人是否為事故者或受款人
            if ((detailForm.getGrdIdnNo() + detailForm.getGrdBrDate()).equals(detailForm.getBenIdnNo() + detailForm.getBenBrDate())
                            || (detailForm.getGrdIdnNo() + detailForm.getGrdBrDate()).equals(queryData.getEvtIdnNo() + DateUtility.changeDateType(queryData.getEvtBrDate()))) {
                saveMessages(session, DatabaseMessageHelper.getGrdNameExistMessage());
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            if (detailForm != null) {
                BaappbaseDataUpdateCase detailData = new BaappbaseDataUpdateCase();
                BeanUtility.copyProperties(detailData, detailForm);

                // 變更日期為西元年若為空值預設為系統日期
                if (StringUtils.isNotBlank(detailForm.getAppDate())) {
                    detailData.setAppDate(DateUtility.changeDateType(detailForm.getAppDate()));
                }
                else if (("Z").equals(detailForm.getBenEvtRel())) {
                    detailData.setAppDate(DateUtility.getNowWestDate());
                }
                if (StringUtils.isNotBlank(detailForm.getBenBrDate())) {
                    detailData.setBenBrDate(DateUtility.changeDateType(detailForm.getBenBrDate()));
                }
                if (StringUtils.isNotBlank(detailForm.getGrdBrDate())) {
                    detailData.setGrdBrDate(DateUtility.changeDateType(detailForm.getGrdBrDate()));
                }

                // 扣減/補償總金額
                if (("A").equals(detailForm.getBenEvtRel())) {
                    detailData.setCutAmt(new BigDecimal(detailForm.getMustIssueAmt1()));
                }
                else if (("Z").equals(detailForm.getBenEvtRel())) {
                    detailData.setCutAmt(new BigDecimal(detailForm.getMustIssueAmt2()));
                }
                else {
                    detailData.setCutAmt(new BigDecimal(0));
                }

                // 查核年月 兩個欄位合一
                detailData.setIdnChkYm(DateUtility.changeChineseYearMonthType(detailForm.getIdnChkYear() + detailForm.getIdnChkMonth()));

                // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                detailData = updateService.transPayeeUpdateData(detailData, baappbase, "I");

                // 申請代算單位
                detailData.setOldAplDpt(detailForm.getOldAplDpt());

                // 2009.12.03 新增 檢核銀行帳號
                // 檢查銀行帳號
                // if (("1").equals(detailForm.getPayCategory()) && (ConstantKey.BAAPPBASE_PAYTYP_1).equals(detailForm.getPayTyp())) {
                // if (!updateService.checkBankAccount(detailForm.getPayBankIdBranchId())) {
                // // 設定訊息: 銀行帳號前7碼錯誤
                //                        
                // saveMessages(session, CustomMessageHelper.getBankIdErrorMessage());
                // return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
                // }
                // }

                // 儲存INTERVALMONTH的值
                String sInterValMonth = null;                
                if (!detailData.getAccRel().equals("3")){ // 20111107 非共同具領時才做判斷                    
                    if (!detailForm.getPayTyp().equals("5") && !detailForm.getPayTyp().equals("6")) {
                        sInterValMonth = "0";
                        // updateService.updateInterValMonth(queryData.getApNo(), "0");
                    }
                    else {
                        sInterValMonth = updateService.getInterValMonth(queryData.getApNo(), "0000");
                        if (sInterValMonth == null){
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
                updateService.insertBaappbaseDataForPayeeData(userData, queryData, detailData, sInterValMonth);
                
                // 案件類別為 2 或 4 時，呼叫 sp_BA_Get_CIPB                
                if (detailData.getBenEvtRel().equals("2") || detailData.getBenEvtRel().equals("3")
                                || detailData.getBenEvtRel().equals("4") || detailData.getBenEvtRel().equals("5")
                                || detailData.getBenEvtRel().equals("6") || detailData.getBenEvtRel().equals("7")){                
                        if(queryData.getCaseTyp().equals("2") || queryData.getCaseTyp().equals("4")){
                            // 呼叫StoreProcedure sp_BA_Get_CIPB(眷屬身份證號,事故日期【NULL】)
                            updateService.doGetCipb(detailData.getApNo(), detailData.getSeqNo(),detailData.getAppDate(), detailData.getBenIdnNo(), null);
                        }     
                }

                // 呼叫即時編審WebService
                // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
                String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
                log.info("webServiceUrl: "+ webServiceUrl);
                SingleCheckMarkServiceHttpBindingStub binding;
                SingleCheckMarkServiceLocator singleCheckMarkServiceLocator = new SingleCheckMarkServiceLocator();
                singleCheckMarkServiceLocator.setSingleCheckMarkServiceHttpPortEndpointAddress(webServiceUrl);
                binding = (SingleCheckMarkServiceHttpBindingStub) singleCheckMarkServiceLocator.getSingleCheckMarkServiceHttpPort();
                String returnCode = binding.doCheckMark(detailData.getApNo(),SecureToken.getInstance().getToken());

                // 更新清單資料 (須在呼叫編審 WebService 後執行 - by Goston)
                PayeeDataUpdateQueryForm queryForm = (PayeeDataUpdateQueryForm) FormSessionHelper.getPayeeDataUpdateQueryForm(request);
                List<BaappbaseDataUpdateCase> applyList = updateService.selectPayeeDataForList(queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4());
                CaseSessionHelper.setPayeeDataUpdateQueryCase(applyList, request);

                log.debug("執行 更正作業 - 受款人資料更正作業 - 新增作業存檔 - 呼叫即時編審結果... " + returnCode);

                if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                    saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
                }

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 更正作業 - 受款人資料更正作業 - 新增頁面  PayeeDataUpdateDetailAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PayeeDataUpdateDetailAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 更正作業 - 受款人資料更正作業 - 修改頁面 (bamo0d083c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 受款人資料更正作業 - 修改頁面 PayeeDataUpdateDetailAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PayeeDataUpdateDetailForm detailForm = (PayeeDataUpdateDetailForm) form;

        // 取得查詢List
        List<BaappbaseDataUpdateCase> queryList = CaseSessionHelper.getPayeeDataUpdateQueryCase(request);
        BaappbaseDataUpdateCase queryData = (BaappbaseDataUpdateCase) queryList.get(0);
        // 取得改前的更正資料
        Baappbase caseData = CaseSessionHelper.getPayeeDataUpdateDetailCase(request);

        try {
            String payeeDataCount = updateService.getPayeeDataCountForUpdate(caseData.getApNo(), detailForm.getBenIdnNo(), detailForm.getBenBrDate(), caseData.getBaappbaseId().toString());

            // 檢核修改的受款人資料是否已存在
            if (Integer.parseInt(payeeDataCount) > 0) {
                saveMessages(session, DatabaseMessageHelper.getBenNameExistMessage());
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            // 檢核新增的法定代理人是否為事故者或受款人
            if ((detailForm.getGrdIdnNo() + detailForm.getGrdBrDate()).equals(detailForm.getBenIdnNo() + detailForm.getBenBrDate())
                            || (detailForm.getGrdIdnNo() + detailForm.getGrdBrDate()).equals(queryData.getEvtIdnNo() + DateUtility.changeDateType(queryData.getEvtBrDate()))) {
                saveMessages(session, DatabaseMessageHelper.getGrdNameExistMessage());
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            // 2009.12.03 新增 檢核銀行帳號
            // 檢查銀行帳號
            if (("1").equals(detailForm.getPayCategory()) && (ConstantKey.BAAPPBASE_PAYTYP_1).equals(detailForm.getPayTyp())) {
                if (!updateService.checkBankAccount(detailForm.getPayBankIdBranchId())) {
                    // 設定訊息: 銀行帳號前7碼錯誤
                    saveMessages(session, CustomMessageHelper.getBankIdErrorMessage());
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
                }
            }

            if (detailForm != null) {
                BaappbaseDataUpdateCase detailData = new BaappbaseDataUpdateCase();
                BeanUtility.copyProperties(detailData, detailForm);

                // 依畫面輸入欄位條件轉換 給付主檔部分欄位
                detailData = updateService.transPayeeUpdateData(detailData, caseData, "U");

                // 判斷給付方式二選一
                // if (("1").equals(detailForm.getPayCategory())
                // detailData.setAccSeqNo("");
                // if (detailForm.getPayCategory().equals("2"))
                // detailData.setPayTyp("");

                // 關係
                if (("A").equals(detailData.getBenEvtRel())) {
                    detailData.setCutAmt(new BigDecimal(detailForm.getMustIssueAmt1()));
                }
                else if (("Z").equals(detailData.getBenEvtRel())) {
                    detailData.setCutAmt(new BigDecimal(detailForm.getMustIssueAmt2()));
                }
                else {
                    detailData.setCutAmt(new BigDecimal(0));
                }

                // 查核年月
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
                
                // 取得需記錄異動LOG的欄位資料
                // 為不影響前端頁面顯示, 故須複製一份 Form
                PayeeDataUpdateDetailForm tempForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(tempForm, detailForm);
                BeanUtility.copyProperties(tempForm, detailData);

                // 變更日期為西元年
                if (StringUtils.defaultString(tempForm.getAppDate()).trim().length() == 7) {
                    tempForm.setAppDate(DateUtility.changeDateType(tempForm.getAppDate()));
                }
                if (StringUtils.defaultString(tempForm.getBenBrDate()).trim().length() == 7) {
                    tempForm.setBenBrDate(DateUtility.changeDateType(tempForm.getBenBrDate()));
                }
                if (StringUtils.defaultString(tempForm.getGrdBrDate()).trim().length() == 7) {
                    tempForm.setGrdBrDate(DateUtility.changeDateType(tempForm.getGrdBrDate()));
                }

                // 扣減/補償總金額
                if (("A").equals(tempForm.getBenEvtRel())) {
                    tempForm.setCutAmt(tempForm.getMustIssueAmt1());
                }
                else if (("Z").equals(tempForm.getBenEvtRel())) {
                    tempForm.setCutAmt(tempForm.getMustIssueAmt2());
                }
                else {
                    tempForm.setCutAmt("0");
                }

                // 查核年月 兩個欄位合一
                if (StringUtils.equals(detailForm.getIdnChkNote(), "1")) {
                    tempForm.setIdnChkYm(DateUtility.calMonth(DateUtility.getNowWestDate(), 13).substring(0, 6));
                }
                else if (StringUtils.equals(detailForm.getIdnChkNote(), "2")) {
                    tempForm.setIdnChkYm(DateUtility.changeChineseYearMonthType(detailForm.getIdnChkYear() + detailForm.getIdnChkMonth()));
                }
                else {
                    tempForm.setIdnChkNote(detailForm.getOldIdnChkNote());
                    tempForm.setIdnChkYm(detailForm.getOldIdnChkYm());
                }

                // 給付資料更正主檔 改前改後值 for BAAPPLOG
                detailData.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 申請代算單位
                detailData.setOldAplDpt(detailForm.getOldAplDpt());

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
                
                // 存檔
                //updateService.updateBaappbaseDataForPayeeData(userData, detailData, caseData, interValMonth);
                updateService.updateBaappbaseDataForOldAgeDeathRepayPayeeData(userData, detailData, caseData, sInterValMonth);

                // 案件類別為 2 或 4 時，呼叫 sp_BA_Get_CIPB
                if (detailData.getBenEvtRel().equals("2") || detailData.getBenEvtRel().equals("3")
                                || detailData.getBenEvtRel().equals("4") || detailData.getBenEvtRel().equals("5")
                                || detailData.getBenEvtRel().equals("6") || detailData.getBenEvtRel().equals("7")){
                    if(queryData.getCaseTyp().equals("2") || queryData.getCaseTyp().equals("4")){
                        // 呼叫StoreProcedure sp_BA_Get_CIPB(眷屬身份證號,事故日期【NULL】)
                        updateService.doGetCipb(detailData.getApNo(), detailData.getSeqNo(),detailData.getAppDate(), detailData.getBenIdnNo(), null);
                    }     
                }

                // 呼叫即時編審WebService
                // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
                String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
                log.info("webServiceUrl: "+ webServiceUrl);
                SingleCheckMarkServiceHttpBindingStub binding;
                SingleCheckMarkServiceLocator singleCheckMarkServiceLocator = new SingleCheckMarkServiceLocator();
                singleCheckMarkServiceLocator.setSingleCheckMarkServiceHttpPortEndpointAddress(webServiceUrl);
                binding = (SingleCheckMarkServiceHttpBindingStub) singleCheckMarkServiceLocator.getSingleCheckMarkServiceHttpPort();
                String returnCode = binding.doCheckMark(detailData.getApNo(),SecureToken.getInstance().getToken());

                // 更新清單資料 (須在呼叫編審 WebService 後執行 - by Goston)
                PayeeDataUpdateQueryForm queryForm = (PayeeDataUpdateQueryForm) FormSessionHelper.getPayeeDataUpdateQueryForm(request);
                List<BaappbaseDataUpdateCase> applyList = updateService.selectPayeeDataForList(queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4());
                CaseSessionHelper.setPayeeDataUpdateQueryCase(applyList, request);

                // 重新抓取資料
                Baappbase baappbaseData = updateService.getPayeeDataUpdateForUpdateCaseBy(detailData.getBaappbaseId().toString());

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                PayeeDataUpdateDetailForm updateForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyPropertiesForUpdate(updateForm, baappbaseData, ConstantKey.OLD_FIELD_PREFIX);

                // 變更日期為民國年
                if (StringUtils.isNotBlank(updateForm.getAppDate())) {
                    updateForm.setAppDate(DateUtility.changeDateType(updateForm.getAppDate().trim()));
                }
                if (StringUtils.isNotBlank(updateForm.getBenBrDate())) {
                    updateForm.setBenBrDate(DateUtility.changeDateType(updateForm.getBenBrDate().trim()));
                }
                if (StringUtils.isNotBlank(updateForm.getGrdBrDate())) {
                    updateForm.setGrdBrDate(DateUtility.changeDateType(updateForm.getGrdBrDate().trim()));
                }

                // 關係
                if (("A").equals(baappbaseData.getBenEvtRel())) {
                    // 關係 - A - 投保單位
                    updateForm.setMustIssueAmt1(baappbaseData.getCutAmt().toString());
                }
                else if (("Z").equals(baappbaseData.getBenEvtRel())) {
                    // 關係 - Z - 補償繳還單位
                    updateForm.setMustIssueAmt2(baappbaseData.getCutAmt().toString());
                }

                // 查核年月 分成兩個欄位
                if (StringUtils.defaultString(baappbaseData.getIdnChkYm()).trim().length() != 0) {
                    updateForm.setIdnChkYear(DateUtility.changeWestYearMonthType(baappbaseData.getIdnChkYm()).substring(0, 3));
                    updateForm.setIdnChkMonth(DateUtility.changeWestYearMonthType(baappbaseData.getIdnChkYm()).substring(3, 5));
                }

                // 帳號 分成兩個欄位或一個欄位
                if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(baappbaseData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(baappbaseData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_8).equals(baappbaseData.getPayTyp())) {
                    updateForm.setPayBankIdBranchId(baappbaseData.getPayBankId() + baappbaseData.getBranchId());
                    baappbaseData.setPayBankIdBranchId(baappbaseData.getPayBankId() + baappbaseData.getBranchId());
                }
                else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(baappbaseData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(baappbaseData.getPayTyp())) {
                    updateForm.setPayAccount(baappbaseData.getPayBankId() + baappbaseData.getBranchId() + baappbaseData.getPayEeacc());
                }

                // 控制關係下拉欄位
                Integer relationQ1 = updateService.selectForPayeeDataUpdateRelationQ1(baappbaseData.getApNo());
                Integer relationQ2 = updateService.selectForPayeeDataUpdateRelationQ2(baappbaseData.getApNo());
                Integer relationQ3 = updateService.selectForPayeeDataUpdateRelationQ3(detailData.getApNo());
                Integer relationQ4 = updateService.selectForPayeeDataUpdateRelationQ4(detailData.getEvtBrDate(), detailData.getEvtIdnNo());

                // 取得給付方式 下拉選單

                request.getSession().setAttribute(ConstantKey.PAYTYP_OPTION_LIST, selectOptionService.getPayTypOptionList(baappbaseData.getBenEvtRel()));

                // 取得具名領取 下拉選單
                request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionList(baappbaseData.getBaappbaseId(), baappbaseData.getApNo()));
                // 取得國籍清單
                request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                // 取得關係 下拉選單
                // 取得關係 下拉選單
                Boolean relationStatus = false;
                if (relationQ3 >= relationQ4 && !"Z".equals(detailData.getBenEvtRel())) {
                    relationStatus = true;
                }
                if (relationQ1 > 0 && relationQ2 > 0) {
                    request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getPayeeRelationOptionList2("typeOne", null, null, null, null, relationStatus));
                }
                else if (relationQ1 > 0 && relationQ2 <= 0) {
                    request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getPayeeRelationOptionList2(null, "typeTwo", null, null, null, relationStatus));
                }
                else if (relationQ1 <= 0 && relationQ2 <= 0) {
                    request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getPayeeRelationOptionList2(null, null, "typeThree", null, null, relationStatus));
                }
                else if (relationQ1 <= 0 && relationQ2 > 0) {
                    request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getPayeeRelationOptionList2(null, null, null, "typefour", null, relationStatus));
                }
                else if (relationStatus) {
                    request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getPayeeRelationOptionList2(null, "typeTwo", null, null, null, relationStatus));
                }
                else {
                    request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getPayeeRelationOptionList());
                }
                // 取得繼承自受款人 下拉選單
                request.getSession().setAttribute(ConstantKey.BENNAME_OPTION_LIST, selectOptionService.selectBenNameOptionList(baappbaseData.getApNo(), baappbaseData.getBaappbaseId().toString()));

                // 控制畫面身份查核年月與具名領取是否顯示
                Integer Q1 = updateService.selectForPayeeDataUpdateQ1(baappbaseData.getApNo(), baappbaseData.getIssuYm());
                Integer Q2 = updateService.selectForPayeeDataUpdateQ2(baappbaseData.getBaappbaseId(), baappbaseData.getApNo());
                // 控制畫面具名領取enable;disabled
                String accSeqNoControl = updateService.getAccSeqNoAmt(detailData.getApNo(), detailData.getSeqNo());

                FormSessionHelper.setPayeeDataUpdateDetailForm(updateForm, request);
                CaseSessionHelper.setPayeeDataUpdateDetailCase(baappbaseData, Q1, Q2, accSeqNoControl, relationQ2, request);

                log.debug("執行 更正作業 - 受款人資料更正作業 - 修改作業存檔 - 呼叫即時編審結果... " + returnCode);

                if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                    saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
                }

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 更正作業 - 受款人資料更正作業 - 修改頁面  PayeeDataUpdateDetailAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PayeeDataUpdateDetailAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 受款人資料更正作業 - 修改頁面 (bamo0d083c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 受款人資料更正作業 - 修改頁面 PayeeDataUpdateDetailAction.doBackList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PayeeDataUpdateDetailForm queryForm = (PayeeDataUpdateDetailForm) form;

        try {
            // 清除 Session
            CaseSessionHelper.removePayeeDataUpdateDetailCase(request);
            FormSessionHelper.removePayeeDataUpdateDetailForm(request);

            // 更新清單資料
            PayeeDataUpdateQueryForm listForm = (PayeeDataUpdateQueryForm) FormSessionHelper.getPayeeDataUpdateQueryForm(request);
            List<BaappbaseDataUpdateCase> applyList = updateService.selectPayeeDataForList(listForm.getApNo1() + listForm.getApNo2() + listForm.getApNo3() + listForm.getApNo4());
            //放入判斷註銷按鈕是否開啟
            for(BaappbaseDataUpdateCase caseData : applyList){
            	//判斷是否開啟 改匯註銷按鈕使用
            	String dateCount1 = updateService.getDataCount1ForQuery(caseData.getApNo(),caseData.getSeqNo());
            	String dateCount2 = updateService.getDataCount2ForQuery(caseData.getApNo(),caseData.getSeqNo());
            	String dateCount3 = updateService.getDataCount3ForQuery(caseData.getApNo(),caseData.getSeqNo());
            	caseData.setDateCount1(dateCount1);
            	caseData.setDateCount2(dateCount2);
            	caseData.setDateCount3(dateCount3);
            }
            CaseSessionHelper.setPayeeDataUpdateQueryCase(applyList, request);

            // 設定更新成功訊息
            saveMessages(session, null);
            return mapping.findForward(FORWARD_MODIFY_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("PayeeDataUpdateDetailAction.doBackList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
