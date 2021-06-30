package tw.gov.bli.ba.update.actions;

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
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.BadaprDataCase;
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

/**
 * 更正作業 - 受款人資料更正 (BAMO0D082C)
 * 
 * @author swim
 */
public class PayeeDataUpdateListAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(PayeeDataUpdateListAction.class);

    private UpdateService updateService;
    private SelectOptionService selectOptionService;

    // 更正作業 - 受款人資料更正 - 受款人資料新增作業頁面
    private static final String FORWARD_INSERT_PAYEE_DETAIL = "insertPayeeData";
    // 更正作業 - 受款人資料更正 - 受款人資料修改作業頁面
    private static final String FORWARD_UPDATE_PAYEE_DATA = "updatePayeeData";
    // 更正作業 - 受款人資料更正 - 受款人資料可領金額作業頁面
    private static final String FORWARD_QUERY_PAYEE_DATA = "queryPayeeData";
    // 更正作業 - 受款人資料更正 - 受款人資料清單頁面
    private static final String FORWARD_MODIFY_PAYEE_DATA_LIST = "modifyPayeeDataList";

    /**
     * 更正作業 - 受款人資料更正作業 - 清單頁面 (bamo0d081a.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 受款人資料更正 - 清單頁面 PayeeDataUpdateQueryAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PayeeDataUpdateQueryForm queryForm = FormSessionHelper.getPayeeDataUpdateQueryForm(request);

        // 清除明細資料畫面
        FormSessionHelper.removePayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removePayeeDataUpdateDetailCase(request);

        try {
            List<BaappbaseDataUpdateCase> applyList = updateService.selectPayeeDataForInsert(queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4());

            if (applyList.size() <= 0) {
                // MSG：W1003-無此受理號碼或尚未產生核定資料！
                saveMessages(session, DatabaseMessageHelper.getNoResultForApNoMessage());
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }

            BaappbaseDataUpdateCase baappbaseDataUpdateCase = null;
            String tempString = null;

            // 控制關係下拉欄位
            Integer relationQ1 = updateService.selectForPayeeDataUpdateRelationQ1(applyList.get(0).getApNo());
            Integer relationQ2 = updateService.selectForPayeeDataUpdateRelationQ2(applyList.get(0).getApNo());
            Integer relationQ3 = updateService.selectForPayeeDataUpdateRelationQ3(applyList.get(0).getApNo());
            Integer relationQ4 = updateService.selectForPayeeDataUpdateRelationQ4(applyList.get(0).getEvtBrDate(), applyList.get(0).getEvtIdnNo());

            // 取得給付方式 下拉選單
            request.getSession().setAttribute(ConstantKey.PAYTYP_OPTION_LIST, selectOptionService.getPayTypOptionList(applyList.get(0).getBenEvtRel()));
            // 取得具名領取 下拉選單
            request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionList((applyList.get(0)).getBaappbaseId(), (applyList.get(0)).getApNo()));
            // 取得國籍清單
            request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
            // 取得關係 下拉選單
            Boolean relationStatus = false;
            if (relationQ3 >= relationQ4) {
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
            request.getSession().setAttribute(ConstantKey.BENNAME_OPTION_LIST, selectOptionService.selectBenNameOptionList(queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4(), null));
            // 控制畫面身份查核年月與具名領取是否顯示
            Integer Q1 = updateService.selectForPayeeDataUpdateQ1(applyList.get(0).getApNo(), applyList.get(0).getIssuYm());
            Integer Q2 = updateService.selectForPayeeDataUpdateQ2(applyList.get(0).getBaappbaseId(), applyList.get(0).getApNo());
            // 控制畫面具名領取enable;disabled
            String accSeqNoControl = "0";// updateService.getAccSeqNoAmt(detailData.getApNo(), detailData.getSeqNo());

            // 取得申請代算單位 下拉選單
            for (int i = 0; i < applyList.size(); i++) {
                baappbaseDataUpdateCase = applyList.get(i);
                if (i == 0 && !(baappbaseDataUpdateCase.getOldAplDpt()).equals(applyList.get(i).getOldAplDpt())) {
                    tempString += "'" + baappbaseDataUpdateCase.getOldAplDpt() + "'";
                }
                else if (!(baappbaseDataUpdateCase.getOldAplDpt()).equals(applyList.get(i).getOldAplDpt())) {
                    tempString += ",'" + baappbaseDataUpdateCase.getOldAplDpt() + "'";
                }

            }

            request.getSession().setAttribute(ConstantKey.OLDAPLDPT_OPTION_LIST, selectOptionService.getOldAplDptOptionList(applyList.get(0).getEvtBrDate(), applyList.get(0).getEvtIdnNo(), tempString));

            CaseSessionHelper.setPayeeDataUpdateDetailCase(null, Q1, Q2, accSeqNoControl, relationQ2, request);

            return mapping.findForward(FORWARD_INSERT_PAYEE_DETAIL);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PayeeDataUpdateListAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 受款人資料更正作業 - 清單頁面 (bamo0d082c.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 受款人資料更正作業 - 清單頁面 PayeeDataUpdateListAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PayeeDataUpdateListForm queryForm = (PayeeDataUpdateListForm) form;
        // 清除明細資料畫面
        FormSessionHelper.removePayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removePayeeDataUpdateDetailCase(request);

        try {
            String baappbaseId = StringUtils.defaultString(request.getParameter("baappbaseId"));

            // 重新抓取資料
            Baappbase detailData = updateService.getPayeeDataUpdateForUpdateCaseBy(baappbaseId);

            // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
            PayeeDataUpdateDetailForm updateForm = new PayeeDataUpdateDetailForm();
            BeanUtility.copyPropertiesForUpdate(updateForm, detailData, ConstantKey.OLD_FIELD_PREFIX);

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
            if (StringUtils.isNotBlank(updateForm.getIdnChkYm())) {
                updateForm.setIdnChkYm(DateUtility.changeWestYearMonthType(updateForm.getIdnChkYm().trim()));
            }

            // if (StringUtils.isNotBlank(detailData.getOldAplDpt())){
            // updateForm.setUname(updateService.getCaubUname(detailData.getOldAplDpt()));
            // }

            // 關係
            if (("A").equals(detailData.getBenEvtRel())) {
                // 關係 - A - 投保單位
                updateForm.setMustIssueAmt1(detailData.getCutAmt().toString());
            }
            else if (("Z").equals(detailData.getBenEvtRel())) {
                // 關係 - Z - 補償繳還單位
                updateForm.setMustIssueAmt2(detailData.getCutAmt().toString());

            }

            // 查核年月 分成兩個欄位
            if (StringUtils.defaultString(detailData.getIdnChkYm()).trim().length() != 0) {
                updateForm.setIdnChkYear(DateUtility.changeWestYearMonthType(detailData.getIdnChkYm()).substring(0, 3));
                updateForm.setIdnChkMonth(DateUtility.changeWestYearMonthType(detailData.getIdnChkYm()).substring(3, 5));
            }

            // 帳號 分成兩個欄位或一個欄位
            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(detailData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(detailData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_8).equals(detailData.getPayTyp())) {
                updateForm.setPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId());
                detailData.setPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId());
            }
            else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(detailData.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(detailData.getPayTyp())) {
                updateForm.setPayAccount(detailData.getPayBankId() + detailData.getBranchId() + detailData.getPayEeacc());
            }

            // 控制關係下拉欄位
            Integer relationQ1 = updateService.selectForPayeeDataUpdateRelationQ1(detailData.getApNo());
            Integer relationQ2 = updateService.selectForPayeeDataUpdateRelationQ2(detailData.getApNo());
            Integer relationQ3 = updateService.selectForPayeeDataUpdateRelationQ3(detailData.getApNo());
            Integer relationQ4 = updateService.selectForPayeeDataUpdateRelationQ4(detailData.getEvtBrDate(), detailData.getEvtIdnNo());

            // 取得申請代算單位 下拉式選單

            List<BaappbaseDataUpdateCase> applyList = updateService.selectPayeeDataForList(detailData.getApNo());
            BaappbaseDataUpdateCase baappbaseDataUpdateCase = null;
            String tempString = null;

            for (int i = 0; i < applyList.size(); i++) {
                baappbaseDataUpdateCase = applyList.get(i);
                if (i == 0 && !("").equals(applyList.get(i).getOldAplDpt()) && !(baappbaseDataUpdateCase.getOldAplDpt()).equals(detailData.getOldAplDpt())) {
                    tempString = baappbaseDataUpdateCase.getOldAplDpt();
                }
                else if (!("").equals(applyList.get(i).getOldAplDpt()) && tempString == null && !(baappbaseDataUpdateCase.getOldAplDpt()).equals(detailData.getOldAplDpt())) {
                    tempString = baappbaseDataUpdateCase.getOldAplDpt();
                }
                else if (!("").equals(applyList.get(i).getOldAplDpt()) && tempString != null && i + 1 != applyList.size() && !(baappbaseDataUpdateCase.getOldAplDpt()).equals(detailData.getOldAplDpt())) {
                    tempString += "','" + baappbaseDataUpdateCase.getOldAplDpt() + "'";
                }
                else if (!("").equals(applyList.get(i).getOldAplDpt()) && tempString != null && i + 1 == applyList.size() && !(baappbaseDataUpdateCase.getOldAplDpt()).equals(detailData.getOldAplDpt())) {
                    tempString += "','" + baappbaseDataUpdateCase.getOldAplDpt();
                }
            }
            request.getSession().setAttribute(ConstantKey.OLDAPLDPT_OPTION_LIST, selectOptionService.getOldAplDptOptionList(detailData.getEvtBrDate(), detailData.getEvtIdnNo(), tempString));

            // 取得給付方式 下拉選單
            request.getSession().setAttribute(ConstantKey.PAYTYP_OPTION_LIST, selectOptionService.getPayTypOptionList(detailData.getBenEvtRel()));
            // 取得具名領取 下拉選單
            request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, selectOptionService.getBenOptionList(detailData.getBaappbaseId(), detailData.getApNo()));
            // 取得國籍清單
            request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
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
            request.getSession().setAttribute(ConstantKey.BENNAME_OPTION_LIST, selectOptionService.selectBenNameOptionList(detailData.getApNo(), detailData.getBaappbaseId().toString()));

            // 控制畫面身份查核年月與具名領取是否顯示
            Integer Q1 = updateService.selectForPayeeDataUpdateQ1(detailData.getApNo(), detailData.getIssuYm());
            Integer Q2 = updateService.selectForPayeeDataUpdateQ2(detailData.getBaappbaseId(), detailData.getApNo());
            // 控制畫面具名領取enable;disabled
            String accSeqNoControl = updateService.getAccSeqNoAmt(detailData.getApNo(), detailData.getSeqNo());

            FormSessionHelper.setPayeeDataUpdateDetailForm(updateForm, request);
            CaseSessionHelper.setPayeeDataUpdateDetailCase(detailData, Q1, Q2, accSeqNoControl, relationQ2, request);

            return mapping.findForward(FORWARD_UPDATE_PAYEE_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PayeeDataUpdateListAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 受款人資料更正作業 - 清單頁面 (bamo0d082c.jsp) <br>
     * 按下「刪除」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 受款人資料更正作業 - 清單頁面 PayeeDataUpdateListAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PayeeDataUpdateListForm queryForm = (PayeeDataUpdateListForm) form;
        // 清除明細資料畫面
        FormSessionHelper.removePayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removePayeeDataUpdateDetailCase(request);

        try {
            String baappbaseId = StringUtils.defaultString(request.getParameter("baappbaseId"));

            // 重新抓取資料
            Baappbase detailData = updateService.getPayeeDataUpdateForUpdateCaseBy(baappbaseId);

            // 刪除資料
            updateService.deletepPayeeDataUpdate(userData, detailData);

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

            log.debug("執行 更正作業 - 受款人資料更正作業 - 清單頁面刪除 - 呼叫即時編審結果... " + returnCode);
            if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
                return mapping.findForward(FORWARD_MODIFY_PAYEE_DATA_LIST);
            }

            // 設定刪除成功訊息
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());
            return mapping.findForward(FORWARD_MODIFY_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("PayeeDataUpdateListAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }
    
    /**
     * 更正作業 - 受款人資料更正作業 - 清單頁面 (bamo0d082c.jsp) <br>
     * 按下「改匯註銷」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 受款人資料更正作業 - 清單頁面 PayeeDataUpdateListAction.doCancel() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PayeeDataUpdateListForm queryForm = (PayeeDataUpdateListForm) form;
        // 清除明細資料畫面
        FormSessionHelper.removePayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removePayeeDataUpdateDetailCase(request);

        try {
        	String baappbaseId = StringUtils.defaultString(request.getParameter("baappbaseId"));

            // 重新抓取資料
            Baappbase detailData = updateService.getPayeeDataUpdateForUpdateCaseBy(baappbaseId);
            Baappbase baappbase = new Baappbase();
            //放入update資料
            baappbase.setApNo(detailData.getApNo());
            baappbase.setSeqNo(detailData.getSeqNo());
            baappbase.setUpdTime(DateUtility.getNowWestDateTime(true)); // 系統日期時間
            baappbase.setEmpId(userData.getEmpNo());
            
            updateService.updateForOldAgeDeathRepayDoCancel(baappbase);
            
            // 呼叫即時編審WebService 因UPDATE經編審後 PROCSTAT會被改回10 因此不做編審 此為特例
            //SingleCheckMarkServiceHttpBindingStub binding;
            //SingleCheckMarkServiceLocator singleCheckMarkServiceLocator = new SingleCheckMarkServiceLocator();
            //singleCheckMarkServiceLocator.setSingleCheckMarkServiceHttpPortEndpointAddress((ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage"));
            //binding = (SingleCheckMarkServiceHttpBindingStub) singleCheckMarkServiceLocator.getSingleCheckMarkServiceHttpPort();
            //String returnCode = binding.doCheckMark(detailData.getApNo());

            // 更新清單資料 (須在呼叫編審 WebService 後執行)
            PayeeDataUpdateQueryForm listForm = (PayeeDataUpdateQueryForm) FormSessionHelper.getPayeeDataUpdateQueryForm(request);
            List<BaappbaseDataUpdateCase> applyList = updateService.selectPayeeDataForList(listForm.getApNo1() + listForm.getApNo2() + listForm.getApNo3() + listForm.getApNo4());
            CaseSessionHelper.setPayeeDataUpdateQueryCase(applyList, request);

            // 呼叫即時編審WebService 因UPDATE經編審後 PROCSTAT會被改回10 因此不做編審 此為特例
            //log.debug("執行 更正作業 - 受款人資料更正作業 - 清單頁面刪除 - 呼叫即時編審結果... " + returnCode);
            //if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
            //    saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
            //    return mapping.findForward(FORWARD_MODIFY_PAYEE_DATA_LIST);
            //}
            
            // 設定改匯註銷成功訊息
            saveMessages(session, DatabaseMessageHelper.getCancelSuccessMessage());
            return mapping.findForward(FORWARD_MODIFY_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("PayeeDataUpdateListAction.doCancel() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getCancelFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 更正作業 - 受款人資料更正作業 - 可領金額頁面 (bamo0d084c.jsp) <br>
     * 按下「確定」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 受款人資料更正作業 - 可領金額頁面 PayeeDataUpdateListAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PayeeDataUpdateListForm queryForm = (PayeeDataUpdateListForm) form;
        String mustIssueAmt[] = queryForm.getMustIssueAmt();

        try {
            BadaprDataCase applyData = CaseSessionHelper.getPayeeDataForBadaprCase(request);
            List<BaappbaseDataUpdateCase> baappbaseData = CaseSessionHelper.getPayeeDataUpdateQueryCase(request);

            // 存檔
            updateService.updateBaappbaseDataForMustIssueAmt(userData, baappbaseData, mustIssueAmt);

            // 呼叫即時編審WebService
            // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
            String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
            log.info("webServiceUrl: "+ webServiceUrl);
            SingleCheckMarkServiceHttpBindingStub binding;
            SingleCheckMarkServiceLocator singleCheckMarkServiceLocator = new SingleCheckMarkServiceLocator();
            singleCheckMarkServiceLocator.setSingleCheckMarkServiceHttpPortEndpointAddress(webServiceUrl);
            binding = (SingleCheckMarkServiceHttpBindingStub) singleCheckMarkServiceLocator.getSingleCheckMarkServiceHttpPort();
            String returnCode = binding.doCheckMark(applyData.getApNo(),SecureToken.getInstance().getToken());

            // 更新清單資料 (須在呼叫編審 WebService 後執行 - by Goston)
            PayeeDataUpdateQueryForm listForm = (PayeeDataUpdateQueryForm) FormSessionHelper.getPayeeDataUpdateQueryForm(request);
            List<BaappbaseDataUpdateCase> applyList = updateService.selectPayeeDataForList(listForm.getApNo1() + listForm.getApNo2() + listForm.getApNo3() + listForm.getApNo4());
            CaseSessionHelper.setPayeeDataUpdateQueryCase(applyList, request);

            log.debug("執行 更正作業 - 受款人資料更正作業 - 可領金額作業存檔 - 呼叫即時編審結果... " + returnCode);
            if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
                return mapping.findForward(FORWARD_MODIFY_PAYEE_DATA_LIST);
            }

            // 設定更新成功訊息
            saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            return mapping.findForward(FORWARD_MODIFY_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("PayeeDataUpdateListAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 受款人資料更正作業 - 可領金額頁面 (bamo0d084c.jsp) <br>
     * 按下「可領金額」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 受款人資料更正作業 - 可領金額頁面 PayeeDataUpdateListAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PayeeDataUpdateListForm queryForm = (PayeeDataUpdateListForm) form;

        // 清除 Session
        CaseSessionHelper.removePayeeDataForBadaprCase(request);
        // 取得查詢List
        List<BaappbaseDataUpdateCase> queryList = CaseSessionHelper.getPayeeDataUpdateQueryCase(request);
        BaappbaseDataUpdateCase queryData = (BaappbaseDataUpdateCase) queryList.get(0);

        try {
            // 取得受款人可領金額資料
            BadaprDataCase caseData = updateService.selectPayeeDataForBadapr(queryData.getApNo());

            CaseSessionHelper.setPayeeDataForBadaprCase(caseData, request);

            return mapping.findForward(FORWARD_QUERY_PAYEE_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PayeeDataUpdateListAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 受款人資料更正作業 (bamo0d082c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        // 清除 Session
        CaseSessionHelper.removePayeeDataUpdateQueryCase(request);
        CaseSessionHelper.removePayeeDataUpdateDetailCase(request);
        CaseSessionHelper.removePayeeDataUpdateList(request);
        CaseSessionHelper.removePayeeDataForBadaprCase(request);
        // 清除明細資料畫面
        FormSessionHelper.removePayeeDataUpdateQueryForm(request);
        FormSessionHelper.removePayeeDataUpdateDetailForm(request);
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 更正作業 - 受款人資料更正作業 - 可領金額 (bamo0d084c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 受款人資料更正作業 - 可領金額頁面 PayeeDataUpdateListAction.doBackList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PayeeDataUpdateListForm queryForm = (PayeeDataUpdateListForm) form;

        try {
            // 清除 Session
            CaseSessionHelper.removePayeeDataForBadaprCase(request);

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
            log.error("PayeeDataUpdateListAction.doBackList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
