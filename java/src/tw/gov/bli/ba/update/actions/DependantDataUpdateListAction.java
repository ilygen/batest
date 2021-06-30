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
import tw.gov.bli.ba.domain.Bafamily;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewCase;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.DependantDataUpdateCase;
import tw.gov.bli.ba.update.cases.DependantDataUpdateCompelDataCase;
import tw.gov.bli.ba.update.cases.DependantEvtDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCompelDataCase;
import tw.gov.bli.ba.update.forms.DependantDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.DependantDataUpdateQueryForm;
import tw.gov.bli.ba.update.forms.PayeeDataUpdateListForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 眷屬資料更正 (BAMO0D071A)
 * 
 * @author Evelyn Hsu
 */

public class DependantDataUpdateListAction  extends BaseDispatchAction {

    private static Log log = LogFactory.getLog(PayeeDataUpdateListAction.class);

    private UpdateService updateService;
    private SelectOptionService selectOptionService;
    
    // 更正作業 - 眷屬資料更正 - 眷屬資料新增作業頁面
    private static final String FORWARD_INSERT_DEPENDANT_DETAIL = "insertDependantData";
    // 更正作業 - 眷屬資料更正 - 眷屬資料修改作業頁面
    private static final String FORWARD_UPDATE_DEPENDANT_DATA = "updateDependantData";
    // 更正作業 - 眷屬資料更正 - 眷屬資料清單頁面
    private static final String FORWARD_MODIFY_DEPENDANT_DATA_LIST = "modifyDependantDataList";
    
    
    /**
     * 更正作業 - 眷屬資料更正作業 - 清單頁面 (bamo0d071a.jsp)
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正 - 清單頁面 DependantDataUpdateQueryAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        
        DependantDataUpdateQueryForm queryForm = FormSessionHelper.getDependantDataUpdateQueryForm(request);

        // 清除明細資料畫面
        FormSessionHelper.removeDependantDataUpdateDetailForm(request);
        CaseSessionHelper.removeDependantDataUpdateDetailCase(request);
        
        try {
            
            String apNo = queryForm.getApNo1()+queryForm.getApNo2()+queryForm.getApNo3()+queryForm.getApNo4();
            List<DependantEvtDataUpdateCase> dependentEvtList = updateService.selectDependantEvtDataForList(apNo);
            List<DependantDataUpdateCase> dependentList = updateService.selectDependantDataForList(apNo);
            
//            if (dependentList.size() <= 0) {
//                // MSG：W1003-無此受理號碼或尚未產生核定資料！
//                saveMessages(session, DatabaseMessageHelper.getNoResultForApNoMessage());
//                return mapping.findForward(ConstantKey.FORWARD_BACK);
//            }
            
           
            // 取得關係下拉選單
            request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getDependantRelationOptionList());
            // 取得國籍清單
            request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
            // 取得結案原因
            request.getSession().setAttribute(ConstantKey.CLOSE_OPTION_LIST, selectOptionService.getDependantCloseOptionList());
            // 取得學校代碼
            request.getSession().setAttribute(ConstantKey.SCHOOLCODE_OPTION_LIST, selectOptionService.selectNpCodeOptionBy());
            
            session.setAttribute("studStatus", 0);
            session.setAttribute("studResult", "");
            
            return mapping.findForward(FORWARD_INSERT_DEPENDANT_DETAIL);
            
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DependantDataUpdateQueryAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }
    
    /**
     * 更正作業 - 眷屬資料更正作業 - 清單頁面 (bamo0d073c.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 清單頁面 DependantDataUpdateListAction.doUpdate() 開始 ... ");
        
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        
        DependantDataUpdateQueryForm queryForm = FormSessionHelper.getDependantDataUpdateQueryForm(request);

        // 清除明細資料畫面
        FormSessionHelper.removeDependantDataUpdateDetailForm(request);
        CaseSessionHelper.removeDependantDataUpdateDetailCase(request);
        try{
            
            String apNo = queryForm.getApNo1()+queryForm.getApNo2()+queryForm.getApNo3()+queryForm.getApNo4();
            List<DependantEvtDataUpdateCase> dependentEvtList = updateService.selectDependantEvtDataForList(apNo);
            
            String bafamilyId = StringUtils.defaultString(request.getParameter("bafamilyId"));
            // 取得關係下拉選單
            request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getDependantRelationOptionList());
            // 取得國籍清單
            request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
            // 取得結案原因
            request.getSession().setAttribute(ConstantKey.CLOSE_OPTION_LIST, selectOptionService.getDependantCloseOptionList());
            // 取得學校代碼
            request.getSession().setAttribute(ConstantKey.SCHOOLCODE_OPTION_LIST, selectOptionService.selectNpCodeOptionBy());
            
            // 重新抓取資料
            DependantDataUpdateCase detailData = updateService.getDependantDataUpdateForUpdateCaseBy(bafamilyId);
            
            // 取得 眷屬編審註記資料
            List<PaymentQueryDetailDataCase> benChkList = updateService.getPaymentQueryOtherChkList(detailData.getApNo(), "0000", "B", detailData.getSeqNo());
            // 取得 符合註記資料
            List<PaymentQueryDetailDataCase> matchChkList = updateService.getPaymentQueryOtherChkList(detailData.getApNo(), "0000", "C", detailData.getSeqNo());
            
            //取得是否顯示身份查核欄位狀態
            Integer checkStatus = updateService.getDataCount2ForDependant(detailData.getApNo(), detailData.getSeqNo(), dependentEvtList.get(0).getIssuYm());
            
            
            //取得在學清單資料
            //需判斷Session是否有資料,若有資料則直接用Session資料
            //若無資料則fetch from db
            List<Bastudterm> termList = CaseSessionHelper.getDependantDataUpdateStudtermList(request);
            if(termList == null){
                termList = updateService.selectStudtermListForDependantDataUpdate(detailData.getApNo(), detailData.getSeqNo());
            }
            
            String maxPayYm = updateService.selectMaxPayYmBy(apNo, "0000");
            String results = "";
            
            for (int i = 0; i < termList.size(); i++) {
                String studEDate1 = termList.get(i).getStudEdate();
                
                if (studEDate1.length() == 5) {
                    studEDate1 = DateUtility.changeChineseYearMonthType(studEDate1);
                }
                
                if (Integer.parseInt(studEDate1) > Integer.parseInt(maxPayYm)){
                    results = "Y";
                }
            }            
            
            // 取得不合格年月清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<DependantDataUpdateCompelDataCase> compelDataList = CaseSessionHelper.getDependantDataUpdateCompelDataList(request);
            if (compelDataList == null) {
                compelDataList = updateService.getCompelDataListForDependant(detailData.getApNo(), detailData.getSeqNo());
            }            
            
            CaseSessionHelper.setDependantDataUpdateStudtermList(termList, request);
            session.setAttribute("studStatus", termList.size()>0?termList.size():0);
            session.setAttribute("studResult", results);
            session.setAttribute("checkStatus", checkStatus);
            
            CaseSessionHelper.setDependantDataUpdateCompelDataList(compelDataList, request);
            if (compelDataList != null && !compelDataList.isEmpty()) {
                request.getSession().setAttribute("DEPENDANT_COMPELDATA_SIZE", compelDataList.size());
            }
            else {
                request.getSession().setAttribute("DEPENDANT_COMPELDATA_SIZE", 0);
            }            
            
            // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
            DependantDataUpdateDetailForm updateForm = new DependantDataUpdateDetailForm();
            BeanUtility.copyPropertiesForUpdate(updateForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
            
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
                updateForm.setRelatChgDate(DateUtility.changeDateType(updateForm.getRelatChgDate().trim()));
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
            if(StringUtils.isNotBlank(detailData.getIdnChkYm().trim())){
                updateForm.setIdnChkYm(DateUtility.changeWestYearMonthType(detailData.getIdnChkYm().trim()));
                updateForm.setIdnChkY(DateUtility.changeWestYearMonthType(detailData.getIdnChkYm().trim()).substring(0, 3));
                session.setAttribute("idnChkY", DateUtility.changeWestYearMonthType(detailData.getIdnChkYm().trim()).substring(0, 3));
                updateForm.setIdnChkM(DateUtility.changeWestYearMonthType(detailData.getIdnChkYm().trim()).substring(3, 5));
                session.setAttribute("idnChkM", DateUtility.changeWestYearMonthType(detailData.getIdnChkYm().trim()).substring(3, 5));
            }else{
                session.setAttribute("idnChkY", "   ");
                session.setAttribute("idnChkM", "  ");
            }
            
            FormSessionHelper.setDependantDataUpdateDetailForm(updateForm, request);
            CaseSessionHelper.setDependantQueryBenChkFileDataCase(benChkList, request);
            CaseSessionHelper.setDependantQueryMatchChkFileDataCase(matchChkList, request);
            CaseSessionHelper.setDependantDataUpdateDetailCase(detailData, request);
            
            return mapping.findForward(FORWARD_UPDATE_DEPENDANT_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PayeeDataUpdateListAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }

        
    }
    
    /**
     * 更正作業 - 眷屬資料更正作業 - 清單頁面 (bamo0d072c.jsp) <br>
     * 按下「刪除」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 清單頁面 DependantDataUpdateListAction.doUpdate() 開始 ... ");
        
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        
        DependantDataUpdateQueryForm queryForm = FormSessionHelper.getDependantDataUpdateQueryForm(request);
        // 清除明細資料畫面
        FormSessionHelper.removeDependantDataUpdateDetailForm(request);
        CaseSessionHelper.removeDependantDataUpdateDetailCase(request);
        
        
        
        String bafamilyId = StringUtils.defaultString(request.getParameter("bafamilyId"));
        // 重新抓取資料
        DependantDataUpdateCase detailData = updateService.getDependantDataUpdateForUpdateCaseBy(bafamilyId);
        
        
        try{
          
            if (detailData != null) {
                Bafamily detailDataCase = new Bafamily();
                BeanUtility.copyProperties(detailDataCase, detailData);
                updateService.deleteDependantData(userData, detailData, detailDataCase);
                
                // 呼叫即時編審WebService
                // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
                String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
                log.info("webServiceUrl: "+ webServiceUrl);
                SingleCheckMarkServiceHttpBindingStub binding;
                binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator().getSingleCheckMarkServiceHttpPort();
                // 呼叫即時編審
                String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
                try {
                    returnCode = binding.doCheckMark(detailData.getApNo(),SecureToken.getInstance().getToken());
                }
                catch (Exception e) {
                    log.error( "執行  更正作業 - 眷屬資料更正作業 - 修改頁面 DependantDataUpdateDetailAction.doSave() 即時編審發生錯誤:" + ExceptionUtility.getStackTrace(e));
                }
                                
                List<DependantDataUpdateCase> dependentList = updateService.selectDependantDataForList(detailData.getApNo());
                CaseSessionHelper.setDependantDataUpdateQueryCase(dependentList, request);
            }
            
            
            
            // 設定刪除成功訊息
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());
            return mapping.findForward(FORWARD_MODIFY_DEPENDANT_DATA_LIST);
            
        }catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("PayeeDataUpdateListAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }
    
    /**
     * 更正作業 - 眷屬資料更正作業 (bamo0d072c.jsp) <br>
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
        CaseSessionHelper.removeDependantDataUpdateQueryCase(request);
        CaseSessionHelper.removeDependantDataUpdateDetailCase(request);
        CaseSessionHelper.removeDependantDataUpdateList(request);
       
        // 清除明細資料畫面
        FormSessionHelper.removeDependantDataUpdateQueryForm(request);
        FormSessionHelper.removeDependantDataUpdateDetailForm(request);
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }
    
    
    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
    
}
