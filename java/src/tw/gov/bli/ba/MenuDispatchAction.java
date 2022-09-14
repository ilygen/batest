package tw.gov.bli.ba;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.bj.forms.CheckJobForm;
import tw.gov.bli.ba.bj.forms.CheckJobRptForm;
import tw.gov.bli.ba.bj.forms.MonthCheckLForm;
import tw.gov.bli.ba.bj.forms.MonthLForm;
import tw.gov.bli.ba.bj.forms.RunProcedureForm;
import tw.gov.bli.ba.bj.forms.QryProcedureForm;
import tw.gov.bli.ba.domain.Baparam;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseMappingDispatchAction;
import tw.gov.bli.ba.maint.cases.BasicAmtMaintCase;
import tw.gov.bli.ba.maint.forms.BasicAmtMaintQueryForm;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.receipt.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;

/**
 * Mapping Dispatch Action for Menu
 * 
 * @author Goston
 */
public class MenuDispatchAction extends BaseMappingDispatchAction {
    private static Log log = LogFactory.getLog(MenuDispatchAction.class);
    public static final String CSRF_TOKEN = "csrfToken";
    private SelectOptionService selectOptionService;
    private MaintService maintService;
    private BjService bjService;
    /**
     * 查詢作業 - 更正日誌查詢 - 查詢頁面 (baiq0d030q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterUpdateLogQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterUpdateLogQuery() : 查詢作業 - 更正日誌查詢 - 查詢頁面 (baiq0d030q.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.query.helper.FormSessionHelper.removeUpdateLogQueryForm(request);
        tw.gov.bli.ba.query.helper.CaseSessionHelper.removeUpdateLogQueryCase(request);

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 查詢作業 - 編審註記查詢作業 - 查詢頁面 (baiq0d040q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterCheckMarkLevelQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterCheckMarkLevelQuery() : 查詢作業 - 編審註記查詢作業 (baiq0d040q.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.query.helper.FormSessionHelper.removeCheckMarkLevelQueryForm(request);
        tw.gov.bli.ba.query.helper.CaseSessionHelper.removeCheckMarkLevelQueryCase(request);

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());
        session.setAttribute(ConstantKey.PAYCATEGORY_OPTION_LIST, selectOptionService.getPayCategoryOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 維護作業 - 扣減參數維護作業 - 查詢頁面 (bapa0x050f.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDeductItemMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDeductItemMaint() : 維護作業 - 扣減參數維護作業 (bapa0x050f.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeDeductItemMaintDetailForm(request);

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 維護作業 - 編審註記維護作業 - 查詢頁面 (bapa0x010f.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterCheckMarkMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterCheckMarkMaint() : 維護作業 - 編審註記維護作業 (bapa0x010f.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeCheckMarkMaintQueryForm(request);
        tw.gov.bli.ba.maint.helper.CaseSessionHelper.removeCheckMarkMaintQueryCase(request);

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());
        session.setAttribute(ConstantKey.PAYCATEGORY_OPTION_LIST, selectOptionService.getPayCategoryOptionList());
        session.setAttribute(ConstantKey.CHKLEVEL_OPTION_LIST, selectOptionService.getChkLevelOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 維護作業 - 核定通知書維護作業 - 查詢頁面 (bapa0x020f.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterAdviceMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterCheckMarkMaint() : 維護作業 - 核定通知書維護作業 (bapa0x020f.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeAdviceMaintQueryForm(request);
        tw.gov.bli.ba.maint.helper.CaseSessionHelper.removeAdviceMaintQueryCase(request);

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 維護作業 - 決行層級維護作業 - 查詢頁面 (bapa0x030f.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDecisionLevelMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDecisionLevelMaint() : 維護作業 - 決行層級維護作業 (bapa0x030f.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeDecisionLevelMaintQueryForm(request);
        tw.gov.bli.ba.maint.helper.CaseSessionHelper.removeDecisionLevelMaintQueryCase(request);

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());
        session.setAttribute(ConstantKey.RECHKLVL_OPTION_LIST, selectOptionService.getRechkLvlOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 維護作業 - 先抽對象維護作業 - 查詢頁面 (bapa0x040f.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterPreviewConditionMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterPreviewConditionMaint() : 維護作業 - 先抽對象維護作業 (bapa0x040f.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removePreviewConditionMaintForm(request);
        tw.gov.bli.ba.maint.helper.CaseSessionHelper.removePreviewConditionMaintCase(request);

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    
    /**
     * 維護作業 - 物價指數調整明細維護作業 - 查詢頁面 (bapa0x060f.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterCpiDtlMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterCpiDtlMaint() : 維護作業 - 物價指數調整明細 (bapa0x060f.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeCpiDtlMaintDetailForm(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);                                                               
    }
    
    /**
     * 維護作業 - 物價指數調整紀錄維護作業 - 查詢頁面 (bapa0x070f.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterCpiRecMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterCpiRecMaint() : 維護作業 - 物價指數調整紀錄 (bapa0x070f.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeCpiRecMaintDetailForm(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);                                                               
    }       
    
    /**
     * 維護作業 - 老年年金加計金額調整作業 - 查詢頁面 (bapa0x080q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    
    public ActionForward enterBasicAmtMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterBasicAmtMaint() : 維護作業 - 老年年金加計金額調整紀錄 (bapa0x080c.jsp) ...");
        HttpSession session = request.getSession();
        
    
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);  

    }    
    
    /**
     * 維護作業 - 失能年金基本金額調整作業 - 查詢頁面 (bapa0x090q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    
    public ActionForward enterDisabledBasicAmtMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterBasicAmtMaint() : 維護作業 - 失能年金基本金額調整紀錄 (bapa0x090q.jsp) ...");
        HttpSession session = request.getSession();
        
    
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);  

    }    
    
    /**
     * 維護作業 - 遺屬年金基本金額調整作業 - 查詢頁面 (bapa0x100q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    
    public ActionForward enterSurvivorBasicAmtMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterBasicAmtMaint() : 維護作業 - 遺屬年金基本金額調整紀錄 (bapa0x100q.jsp) ...");
        HttpSession session = request.getSession();
        
    
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);  

    }    
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護作業 - 查詢頁面 (bapa0x110q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    
    public ActionForward enterAvgAmtMonMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterAvgAmtMonMaint() : 維護作業 - 勞保年金平均薪資月數參數維護作業 (bapa0x110q.jsp) ...");
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeAvgAmtMonMaintQueryForm(request);
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeAvgAmtMonMaintDetailForm(request);
        tw.gov.bli.ba.maint.helper.CaseSessionHelper.removeAvgAmtMonMaintQueryCaseList(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);  

    }    
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業 - 查詢頁面 (bapa0x120q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    
    public ActionForward enterReplacementRatioMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterAvgAmtMonMaint() : 維護作業 - 老年年金所得替代率參數維護作業 (bapa0x120q.jsp) ...");
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeReplacementRatioMaintQueryForm(request);
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeReplacementRatioMaintDetailForm(request);
        tw.gov.bli.ba.maint.helper.CaseSessionHelper.removeReplacementRatioMaintQueryCaseList(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);  

    }    
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 - 查詢頁面 (bapa0x130q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    
    public ActionForward enterDisabledReplacementRatioMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledReplacementRatioMaint() : 維護作業 - 失能年金所得替代率參數維護作業 (bapa0x130q.jsp) ...");
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeDisabledReplacementRatioMaintQueryForm(request);
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeDisabledReplacementRatioMaintDetailForm(request);
        tw.gov.bli.ba.maint.helper.CaseSessionHelper.removeDisabledReplacementRatioMaintQueryCaseList(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);  

    }    
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業 - 查詢頁面 (bapa0x140q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    
    public ActionForward enterSurvivorReplacementRatioMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterSurvivorReplacementRatioMaint() : 維護作業 - 遺屬年金所得替代率參數維護作業 (bapa0x140q.jsp) ...");
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeSurvivorReplacementRatioMaintQueryForm(request);
        tw.gov.bli.ba.maint.helper.FormSessionHelper.removeSurvivorReplacementRatioMaintDetailForm(request);
        tw.gov.bli.ba.maint.helper.CaseSessionHelper.removeSurvivorReplacementRatioMaintQueryCaseList(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);  

    }
    
    /**
     * 維護作業 - 重設案件給付參數資料 - 查詢頁面 (bapa0x150q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterResetPaymentParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterResetPaymentParameter() : 維護作業 - 重設案件給付參數資料 - 查詢頁面(bapa0x150q.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
//        tw.gov.bli.ba.update.helper.FormSessionHelper.removeInheritorRegisterForm(request);
//        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeInheritorRegisterCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 維護作業 - 年金受理資料轉出 - 查詢頁面 (bapa0x160q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterAnnuityAcceptDataTransfer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterResetPaymentParameter() : 維護作業 - 年金受理資料轉出 - 查詢頁面(bapa0x160q.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
//        tw.gov.bli.ba.update.helper.FormSessionHelper.removeInheritorRegisterForm(request);
//        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeInheritorRegisterCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 查詢作業 - 行政支援查詢 - 查詢頁面 (biq0d050q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterExecutiveSupportQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterExecutiveSupportQuery() : 查詢作業 - 行政支援查詢 (biq0d050q.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.query.helper.FormSessionHelper.removeExecutiveSupportQueryForm(request);
        tw.gov.bli.ba.query.helper.FormSessionHelper.removeExecutiveSupportQueryCondForm(request);
        tw.gov.bli.ba.query.helper.CaseSessionHelper.removeExecutiveSupportQueryCase(request);
        tw.gov.bli.ba.query.helper.CaseSessionHelper.removeExecutiveSupportQueryCaseList(request);
        tw.gov.bli.ba.query.helper.CaseSessionHelper.removeExecutiveSupportQueryDataCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 受理作業 - 老年年金給付受理作業 - 受理頁面 (baap0d010a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOldAgeAnnuityReceipt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldAgeAnnuityReceipt() : 受理作業 - 老年年金給付受理作業 (baap0d010a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeOldAgeAnnuityReceiptForm(request);
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeOldAgeAnnuityReceiptQryCondForm(request);
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeOldAgeAnnuityReceiptQryForm(request);
        tw.gov.bli.ba.receipt.helper.CaseSessionHelper.removeOldAgeAnnuityReceiptList(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 受理作業 - 失能年金臨櫃受理作業 - 受理頁面 (baap0d040a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledAnnuityWalkInReceipt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	log.debug("執行 MenuDispatchAction.enterDisabledAnnuityWalkInReceipt() : 受理作業 - 失能年金臨櫃受理作業 (baap0d040a.jsp) ...");
    	
    	// 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
    	tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeDisabledAnnuityWalkInReceiptQueryForm(request);
    	
    	return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 行政支援作業 - 行政支援記錄維護 - 查詢頁面 (basu0d010a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterExecutiveSupportMaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterExecutiveSupportMaint() : 行政支援作業 - 行政支援記錄維護 (basu0d010a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.executive.helper.CaseSessionHelper.removeExecutiveSupportMaintCase(request);
        tw.gov.bli.ba.executive.helper.FormSessionHelper.removeExecutiveSupportMaintAddForm(request);
        tw.gov.bli.ba.executive.helper.FormSessionHelper.removeExecutiveSupportMaintForm(request);
        tw.gov.bli.ba.executive.helper.FormSessionHelper.removeExecutiveSupportMaintListForm(request);
        tw.gov.bli.ba.executive.helper.FormSessionHelper.removeExecutiveSupportMaintModifyForm(request);
        // 取得給函別類下拉選單的內容
        session.setAttribute(ConstantKey.LETTERTYPE_OPTION_LIST, selectOptionService.getLetterTypeOptionList());
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 受理作業 - 國併勞年金臨櫃受理作業 - 受理頁面 (baap0d050a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterNpDisabledAnnuityReceipt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	log.debug("執行 MenuDispatchAction.enterNpDisabledAnnuityReceipt() : 受理作業 - 國併勞年金臨櫃受理作業 (baap0d050a.jsp) ...");
    	
    	// 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeDisabledAnnuityReceiptForm(request);
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeDisabledAnnuityReceiptQryCondForm(request);
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeDisabledAnnuityReceiptQueryForm(request);
        tw.gov.bli.ba.receipt.helper.CaseSessionHelper.removeAllDisabledAnnuityReceiptCase(request);
    	
    	return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 通訊資料更正 - 查詢頁面 (bamo0d010c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterCommunicationDataUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterCommunicationDataUpdate() : 更正作業 - 通訊資料更正 (bamo0d010c.jsp) ...");

        HttpSession session = request.getSession();
        String tokenId = RandomStringUtils.randomAlphanumeric(40);
        session.setAttribute(CSRF_TOKEN, tokenId);
		// 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeCommunicationDataUpdateQueryForm(request);
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeCommunicationDataUpdateForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeCommunicationDataUpdateList(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 行政支援作業 - 行政支援記錄銷案 - 查詢頁面 (basu0d010a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterExecutiveSupportClose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterExecutiveSupportMaint() : 行政支援作業 - 行政支援記錄銷案 (basu0d020d.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.executive.helper.CaseSessionHelper.removeExecutiveSupportCloseListCase(request);
        tw.gov.bli.ba.executive.helper.FormSessionHelper.removeExecutiveSupportCloseQueryForm(request);
        tw.gov.bli.ba.executive.helper.FormSessionHelper.removeExecutiveSupportCloseForm(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 給付資料更正 - 查詢頁面 (bamo0d020c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterPaymentDataUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterPaymentDataUpdate() : 更正作業 - 給付資料更正 (bamo0d020c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removePaymentDataUpdateQueryForm(request);
        tw.gov.bli.ba.update.helper.FormSessionHelper.removePaymentDataUpdateForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removePaymentDataUpdateList(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 債務繼承人資料登錄作業 - 查詢頁面 (bamo0d050a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterInheritorRegister(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterInheritorRegister() : 更正作業 - 債務繼承人資料登錄作業 - 查詢頁面(bamo0d050a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeInheritorRegisterForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeInheritorRegisterCase(request);
        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.RELATIION_OPTION_LIST, selectOptionService.getRelationOptionList());
        session.setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 結案狀態變更作業 - 老年年金結案狀態變更作業 -查詢頁面 (bamo0d110c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOldAgeCloseStatusAlteration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldAgeCloseStatusAlteration() : 更正作業 - 結案狀態變更作業 - 老年年金結案狀態變更作業 -查詢頁面 (bamo0d110c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeInheritorRegisterForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeInheritorRegisterCase(request);
        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.RELATIION_OPTION_LIST, selectOptionService.getRelationOptionList());
        session.setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 -查詢頁面 (bamo0d210c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledCloseStatusAlteration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledCloseStatusAlteration() : 更正作業 - 結案狀態變更作業 - 失能年金結案狀態變更作業 -查詢頁面 (bamo0d210c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeInheritorRegisterForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeInheritorRegisterCase(request);
        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.RELATIION_OPTION_LIST, selectOptionService.getRelationOptionList());
        session.setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 更正作業 - 結案狀態變更作業 - 遺屬年金結案狀態變更作業 -查詢頁面 (bamo0d310c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSurvivorCloseStatusAlteration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterSurvivorCloseStatusAlteration() : 更正作業 - 結案狀態變更作業 - 遺屬年金結案狀態變更作業 -查詢頁面 (bamo0d310c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeInheritorRegisterForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeInheritorRegisterCase(request);
        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.RELATIION_OPTION_LIST, selectOptionService.getRelationOptionList());
        session.setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
 
    /**
     * 更正作業 - 受款人資料更正 - 查詢頁面 (bamo0d080c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterPayeeDataUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterPayeeDataUpdate() : 更正作業 - 受款人資料更正 (bamo0d080c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removePayeeDataUpdateQueryForm(request);
        tw.gov.bli.ba.update.helper.FormSessionHelper.removePayeeDataUpdateDetailForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removePayeeDataUpdateQueryCase(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removePayeeDataUpdateDetailCase(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removePayeeDataUpdateList(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removePayeeDataForBadaprCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 止付處理 - 查詢頁面 (bamo0d040n.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterStopPaymentProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterPayeeDataUpdate() : 更正作業 - 止付處理 (bamo0d040n.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeStopPaymentProcessQueryForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeStopPaymentProcessQueryCase(request);

        // 取得止付原因下拉選單的內容
        session.setAttribute(ConstantKey.STEXPNDREASON_OPTION_LIST, selectOptionService.getStexpndReasonOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 案件資料更正 - 查詢頁面 (bamo0d030c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterApplicationDataUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterApplicationDataUpdate() : 更正作業 - 案件資料更正 (bamo0d030c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeApplicationDataUpdateForm(request);
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeApplicationDataUpdateQueryForm(request);
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeOnceAmtDataUpdateForm(request);
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeOwesDataUpdateForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeApplicationDataUpdateCase(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeBasenimaintCaseList(request);
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 編審註記程度調整 - 查詢頁面 (bamo0d060c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterCheckMarkLevelAdjust(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterCheckMarkLevelAdjust() : 更正作業 - 編審註記程度調整 (bamo0d060c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeCheckMarkLevelAdjustCase(request);
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeCheckMarkLevelAdjustFormm(request);
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 審核作業相關報表 - 勞保老年年金給付受理編審清單 - 查詢頁面 (balp0d010l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOldAgeReviewRpt01(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldAgeReviewRpt01() : 列印作業 - 審核作業相關報表 - 勞保老年年金給付受理編審清單 - 查詢頁面 (balp0d010l.jsp) ...");
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 審核作業相關報表 - 勞保老年年金給付審核給付清單 - 查詢頁面 (balp0d020l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOldAgeReviewRpt02(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldAgeReviewRpt02() : 列印作業 - 審核作業相關報表 - 審核給付清單 - 查詢頁面 (balp0d020l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 審核作業相關報表 - 另案扣減照會單 - 查詢頁面 (balp0d040l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOldAgeReviewRpt04(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldAgeReviewRpt04() : 列印作業 - 審核作業相關報表 - 另案扣減照會單 - 查詢頁面 (balp0d040l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 更正作業相關報表 - 紓困繳納查詢單 - 查詢頁面 (balp0d110l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDataUpdateRpt01(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDataUpdateRpt01() : 列印作業 - 更正作業相關報表 - 紓困繳納查詢單 - 查詢頁面 (balp0d110l.jsp) ...");
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 更正作業相關報表 - 紓困貸款撥款情形查詢清單 - 查詢頁面 (balp0d120l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDataUpdateRpt02(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDataUpdateRpt02() : 列印作業 - 更正作業相關報表 - 紓困貸款撥款情形查詢清單 - 查詢頁面 (balp0d120l.jsp) ...");
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 更正作業相關報表 - 紓困貸款抵銷情形照會單 - 查詢頁面 (balp0d130l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDataUpdateRpt03(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDataUpdateRpt03() : 列印作業 - 更正作業相關報表 - 紓困貸款抵銷情形照會單 - 查詢頁面 (balp0d130l.jsp) ...");
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 更正作業相關報表 -紓困貸款呆帳債務人照會單 - 查詢頁面 (balp0d140l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDataUpdateRpt04(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDataUpdateRpt04() : 列印作業 - 更正作業相關報表 - 紓困貸款呆帳債務人照會單 - 查詢頁面 (balp0d140l.jsp) ...");
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 更正作業相關報表 - 止付單 - 查詢頁面 (balp0d150l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDataUpdateRpt05(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDataUpdateRpt05() : 列印作業 - 更正作業相關報表 - 止付單 - 查詢頁面 (balp0d150l.jsp) ...");
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 決行作業相關報表 - 歸檔清單 - 查詢頁面 (balp0d210l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDecisionRpt01(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDecisionRpt01() : 列印作業 - 決行作業相關報表 - 歸檔清單 - 查詢頁面 (balp0d210l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 決行作業相關報表 - 歸檔清單點交清冊 - 查詢頁面 (balp0d220l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDecisionRpt02(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDecisionRpt02() : 列印作業 - 決行作業相關報表 - 歸檔清單點交清冊 - 查詢頁面 (balp0d220l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 決行作業相關報表 - 歸檔清單點交清冊 - 查詢頁面 (balp0d230l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDecisionRpt03(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDecisionRpt03() : 列印作業 - 決行作業相關報表 - 歸檔清單點交清冊 - 查詢頁面 (balp0d230l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 稽催管制相關報表 - 逾期未決行案件管制表 - 查詢頁面 (balp0d420l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterAuditRpt01(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterAuditRpt01() : 列印作業 - 稽催管制相關報表 - 逾期未決行案件管制表 - 查詢頁面 (balp0d420l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 月處理核定合格清冊 - 查詢頁面 (balp0d310l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt01(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt01() : 列印作業 - 月核定處理相關報表 - 月處理核定合格清冊  - 查詢頁面 (balp0d310l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 月編審異動清單 - 查詢頁面 (balp0d320l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt02(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt02() : 列印作業 - 月核定處理相關報表 - 月編審異動清單  - 查詢頁面 (balp0d320l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 月核定差異統計表 - 查詢頁面 (balp0d330l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt03(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt03() : 列印作業 - 月核定處理相關報表 - 月核定差異統計表 - 查詢頁面 (balp0d330l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 月核定撥付總表 - 查詢頁面 (balp0d340l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt04(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt04() : 列印作業 - 月核定處理相關報表 - 月核定撥付總表  - 查詢頁面 (balp0d340l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 給付函核定通知書 - 查詢頁面 (balp0d350l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt05(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt05() : 列印作業 - 月核定處理相關報表 - 給付函核定通知書  - 查詢頁面 (balp0d350l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 核定清單 - 查詢頁面 (balp0d360l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt06(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt06() : 列印作業 - 月核定處理相關報表 - 核定清單  - 查詢頁面 (balp0d360l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 月核定給付撥款總額表 - 查詢頁面 (balp0d370l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt07(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt07() : 列印作業 - 月核定處理相關報表 - 月核定給付撥款總額表 - 查詢頁面 (balp0d370l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 月核定合格清冊 - 查詢頁面 (balp0d380l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt08(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt08() : 列印作業 - 月核定處理相關報表 - 月核定合格清冊  - 查詢頁面 (balp0d380l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 給付抵銷紓困貸款明細 - 查詢頁面 (balp0d390l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt09(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt09() : 列印作業 - 月核定處理相關報表 - 給付抵銷紓困貸款明細 - 查詢頁面 (balp0d390l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 核付清單及核付明細表 - 查詢頁面 (balp0d3a0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt10(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt10() : 列印作業 - 月核定處理相關報表 - 核付清單及核付明細表 - 查詢頁面 (balp0d3a0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 退匯核定清單 - 查詢頁面 (balp0d3b0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt11(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt11() : 列印作業 - 月核定處理相關報表 - 退匯核定清單  - 查詢頁面 (balp0d3b0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 退匯清冊 - 查詢頁面 (balp0d3c0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt12(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt12() : 列印作業 - 月核定處理相關報表 - 退匯清冊  - 查詢頁面 (balp0d3c0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 改匯核定清單 - 查詢頁面 (balp0d3d0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt13(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt13() : 列印作業 - 月核定處理相關報表 - 改匯核定清單  - 查詢頁面 (balp0d3d0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 改匯清冊 - 查詢頁面 (balp0d3e0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt14(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt14() : 列印作業 - 月核定處理相關報表 - 改匯清冊  - 查詢頁面 (balp0d3e0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 退匯應收已收清冊 - 查詢頁面 (balp0d3f0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt15(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt15() : 列印作業 - 月核定處理相關報表 - 退匯應收已收清冊  - 查詢頁面 (balp0d3f0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 退匯通知書 - 查詢頁面 (balp0d3g0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt16(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt16() : 列印作業 - 月核定處理相關報表 - 退匯通知書 - 查詢頁面 (balp0d3g0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 退回現金清冊 - 查詢頁面 (balp0d3h0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt17(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt17() : 列印作業 - 月核定處理相關報表 - 退回現金清冊 - 查詢頁面 (balp0d3h0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 退回現金轉暫收及待結轉清單 - 查詢頁面 (balp0d3i0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt18(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt18() : 列印作業 - 月核定處理相關報表 - 退回現金轉暫收及待結轉清單 - 查詢頁面 (balp0d3i0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 退匯應收已收核定清單 - 查詢頁面 (balp0d3h0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt19(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt19() : 列印作業 - 月核定處理相關報表 - 退匯應收已收核定清單 - 查詢頁面 (balp0d3hj0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 應收款立帳核定清單 - 查詢頁面 (balp0d3h0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt20(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt20() : 列印作業 - 月核定處理相關報表 - 應收款立帳核定清單 - 查詢頁面 (balp0d3k0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 應收款立帳清冊 - 查詢頁面 (balp0d3l0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt21(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt21() : 列印作業 - 月核定處理相關報表 - 應收款立帳清冊  - 查詢頁面 (balp0d3l0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 月處理核定報表彙整 - 查詢頁面 (balp0d3m0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt22(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt22() : 列印作業 - 月核定處理相關報表 - 月處理核定報表彙整  - 查詢頁面 (balp0d3m0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容

        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 月核定處理相關報表 - 郵政匯票通知／入戶匯款證明 - 查詢頁面 (balp0d3n0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt23(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt23() : 列印作業 - 月核定處理相關報表 - 郵政匯票通知／入戶匯款證明  - 查詢頁面 (balp0d3n0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 月核定處理相關報表 - 代扣補償金清冊 - 查詢頁面 (balp0d3o0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt24(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt24() : 列印作業 - 月核定處理相關報表 - 代扣補償金清冊 - 查詢頁面 (balp0d3o0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 應收收回相關報表 - 退匯/現應收已收核定清單 (BALP0D610L)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt25(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt25() : 列印作業 - 應收收回相關報表 - 退匯/現應收已收核定清單 (BALP0D610L) ...");

        HttpSession session = request.getSession();

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 應收收回相關報表 - 退匯/現應收已收清冊 (BALP0D620L)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt26(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt26() : 列印作業 - 應收收回相關報表 - 退匯/現應收已收清冊 (BALP0D620L) ...");

        HttpSession session = request.getSession();

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 應收收回相關報表 - 退匯/現應收已收註銷核定清單 (BALP0D630L)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt27(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt27() : 列印作業 - 應收收回相關報表 - 退匯/現應收已收註銷核定清單 (BALP0D630L) ...");

        HttpSession session = request.getSession();

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 應收收回相關報表 - 退匯/現應收已收註銷清冊 (BALP0D640L)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt28(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt28() : 列印作業 - 應收收回相關報表 - 退匯/現應收已收註銷清冊 (BALP0D640L) ...");

        HttpSession session = request.getSession();

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 月核定處理相關報表 - 補送在學證明通知函 - 查詢頁面 (balp0d3p0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt29(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt29() : 列印作業 - 月核定處理相關報表 - 補送在學證明通知函  - 查詢頁面 (balp0d3p0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        // 移除L
        List<Baparam> dataList = selectOptionService.getPayKindOptionList();
        
        for(int i = 0 ; i < dataList.size() ; i++){
        	if(StringUtils.equalsIgnoreCase(dataList.get(i).getParamCode(), "L")){
        		dataList.remove(i);
        	}
        }
        
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, dataList);
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.review.helper.FormSessionHelper.removeMonthlyRpt29Form(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 查核失能程度通知函 - 查詢頁面 (balp0d3s0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt30(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt30() : 列印作業 - 其他類報表 - 查核失能程度通知函  - 查詢頁面 (balp0d3s0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        // 移除L/S
        List<Baparam> dataList = selectOptionService.getPayKindOptionList();
        
        for(int i = 0 ; i < dataList.size() ; i++){
            if(StringUtils.equalsIgnoreCase(dataList.get(i).getParamCode(), "L") || StringUtils.equalsIgnoreCase(dataList.get(i).getParamCode(), "S")){
                dataList.remove(i);
            }
        }
        
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, dataList);
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.review.helper.FormSessionHelper.removeMonthlyRpt30Form(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 老年差額金通知 - 查詢頁面 (balp0d3t0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt31(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt31() : 列印作業 - 其他類報表 - 老年差額金通知  - 查詢頁面 (balp0d3t0l.jsp) ...");

        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.review.helper.FormSessionHelper.removeMonthlyRpt31Form(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 月核定處理相關報表 - 保留遺屬年金計息存儲核定清單 - 查詢頁面 (balp0d3u0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt32(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt32() : 列印作業 - 月核定處理相關報表 - 保留遺屬年金計息存儲核定清單 - 查詢頁面 (balp0d3u0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        // 移除L/K
        List<Baparam> dataList = selectOptionService.getPayKindOptionList();

        for (int i = 0; i < dataList.size(); i++) {
            if (StringUtils.equalsIgnoreCase(dataList.get(i).getParamCode(), "L")) {
                dataList.remove(i);
            }
        }
        
        for (int i = 0; i < dataList.size(); i++) {
            if (StringUtils.equalsIgnoreCase(dataList.get(i).getParamCode(), "K")) {
                dataList.remove(i);
            }
        }

        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, dataList);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 保留遺屬年金計息存儲清冊 - 查詢頁面 (balp0d3v0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyRpt33(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyRpt33() : 列印作業 - 月核定處理相關報表 - 保留遺屬年金計息存儲清冊  - 查詢頁面 (balp0d3v0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        // 移除L/K
        List<Baparam> dataList = selectOptionService.getPayKindOptionList();

        for (int i = 0; i < dataList.size(); i++) {
            if (StringUtils.equalsIgnoreCase(dataList.get(i).getParamCode(), "L")) {
                dataList.remove(i);
            }
        }
        
        for (int i = 0; i < dataList.size(); i++) {
            if (StringUtils.equalsIgnoreCase(dataList.get(i).getParamCode(), "K")) {
                dataList.remove(i);
            }
        }

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, dataList);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 月核定處理相關報表 - 月處理異動清單 - 查詢頁面 (balp0d3r0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthlyBatchRpt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthlyBatchRpt() : 列印作業 - 月核定處理相關報表 - 月處理異動清單  - 查詢頁面 (balp0d3r0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 調卷/還卷單 - 查詢頁面 (balp0d510l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt01(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOtherRpt01() : 列印作業 - 其他類報表 - 調卷/還卷單 - 查詢頁面 (balp0d510l.jsp) ...");
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 已退匯止付或退回現金尚未沖轉收回案件清單 - 查詢頁面 (balp0d520l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt02(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	log.debug("執行 MenuDispatchAction.enterOtherRpt02() : 列印作業 - 其他類報表 - 已退匯止付或退回現金尚未沖轉收回案件清單 - 查詢頁面 (balp0d520l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 已退匯止付尚未應收款立帳案件清單 - 查詢頁面 (balp0d530l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt03(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	log.debug("執行 MenuDispatchAction.enterOtherRpt03() : 列印作業 - 其他類報表 - 已退匯止付尚未應收款立帳案件清單 - 查詢頁面 (balp0d530l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 受理案件統計表 - 查詢頁面 (balp0d540l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt04(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	log.debug("執行 MenuDispatchAction.enterOtherRpt04() : 列印作業 - 其他類報表 - 受理案件統計表 - 查詢頁面 (balp0d540l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 批次核定函 - 查詢頁面 (balp0d3q0l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt05(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	log.debug("執行 MenuDispatchAction.enterOtherRpt05() : 列印作業 - 其他類報表 - 批次核定函 - 查詢頁面 (balp0d3q0l.jsp) ...");

        HttpSession session = request.getSession();

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 審核作業 - 給付審核作業 - 查詢頁面 (baco0d010a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterPaymentReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterPaymentReview() : 審核作業 - 給付審核作業 (baco0d010a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.review.helper.FormSessionHelper.removePaymentReviewForm(request);

        tw.gov.bli.ba.review.helper.FormSessionHelper.removeReviewQueryForm(request);
        tw.gov.bli.ba.review.helper.CaseSessionHelper.removeAllPaymentReviewCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 查詢頁面 (baco0d110a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledPaymentReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledPaymentReview() : 審核作業 - 失能年金給付審核作業 (baco0d110a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.review.helper.FormSessionHelper.removeDisabledPaymentReviewForm(request);
        tw.gov.bli.ba.review.helper.FormSessionHelper.removeDisabledPaymentReviewQueryForm(request);
        tw.gov.bli.ba.review.helper.CaseSessionHelper.removeAllDisabledPaymentReviewCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 決行作業 - 老年年金給付決行作業 - 查詢頁面 (barc0d010a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterPaymentDecision(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterPaymentDecision() : 決行作業 - 老年年金給付決行作業 (barc0d010a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.decision.helper.FormSessionHelper.removePaymentDecisionForm(request);
        tw.gov.bli.ba.decision.helper.FormSessionHelper.removeDecisionQueryForm(request);
        tw.gov.bli.ba.decision.helper.CaseSessionHelper.removeAllPaymentDecisionCase(request);
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 查詢頁面 (barc0d110a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledPaymentDecision(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledPaymentDecision() : 決行作業 - 失能年金給付決行作業 (barc0d110a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.decision.helper.FormSessionHelper.removeDisabledPaymentDecisionForm(request);
        tw.gov.bli.ba.decision.helper.FormSessionHelper.removeDisabledPaymentDecisionQueryForm(request);
        tw.gov.bli.ba.decision.helper.CaseSessionHelper.removeAllDisabledPaymentDecisionCase(request);
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 查詢頁面 (barc0d210a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSurvivorPaymentDecision(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterSurvivorPaymentDecision() : 決行作業 - 遺屬年金給付決行作業 (barc0d210a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.decision.helper.FormSessionHelper.removeSurvivorPaymentDecisionForm(request);
        tw.gov.bli.ba.decision.helper.FormSessionHelper.removeSurvivorPaymentDecisionQueryForm(request);
        tw.gov.bli.ba.decision.helper.CaseSessionHelper.removeAllSurvivorPaymentDecisionCase(request);
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 查詢頁面 (baiq0d010q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterPaymentQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterPaymentQuery() : 查詢作業 - 給付查詢作業 (baiq0d010q.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.query.helper.FormSessionHelper.removePaymentQueryForm(request);
        tw.gov.bli.ba.query.helper.FormSessionHelper.removePaymentQueryCondForm(request);
        tw.gov.bli.ba.query.helper.CaseSessionHelper.removeAllPaymentQueryCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 查詢作業 - 應收查詢作業 - 查詢頁面 (baiq0d020q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterReceivableQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterReceivableQuery() : 查詢作業 - 應收查詢作業 (baiq0d010q.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.query.helper.FormSessionHelper.removeReceivableQueryForm(request);
        tw.gov.bli.ba.query.helper.FormSessionHelper.removeReceivableQueryCondForm(request);
        tw.gov.bli.ba.query.helper.CaseSessionHelper.removeAllReceivableQueryCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 批次處理 - 給付媒體回押註記作業 - 查詢頁面 (baba0m010x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterUpdatePaidMarkBJ(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterUpdatePaidMarkBJ() : 批次處理 - 給付媒體回押註記作業 (baba0m010x.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeUpdatePaidMarkBJForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeUpdatePaidMarkBJCase(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeUpdatePaidMarkBJDetailCase(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeUpdatePaidMarkBJDetailCase2(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeUpdatePaidMarkBJDetailCase3(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 批次處理 - 收回作業 - 查詢頁面 (baba0m020x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterReturnWriteOffBJ(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterReturnWriteOffBJ() : 批次處理 - 收回作業 (baba0m020x.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeReturnWriteOffBJForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeReturnWriteOffBJCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 批次處理 - 轉催收作業 - 查詢頁面 (baba0m030x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterTrans2OverdueReceivableBJ(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterTrans2OverdueReceivableBJ() : 批次處理 - 轉催收作業 (baba0m030x.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeTrans2OverdueReceivableBJForm(request);
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeTrans2OverdueReceivableBJCondForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllTrans2OverdueReceivableBJCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 批次處理 - 已收調整作業 - 查詢頁面 (baba0m040x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterReceivableAdjustBJ(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterReceivableAdjustBJ() : 批次處理 - 已收調整作業 (baba0m040x.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeReceivableAdjustBJForm(request);
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeReceivableAdjustBJCondForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllReceivableAdjustBJCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 線上產製媒體檔作業- 老年年金線上批次產製媒體檔作業- 執行頁面 (BABA0M170X.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOldMediaOnline(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldMediaOnline() : 批次處理 - 線上產製媒體檔作業- 老年年金線上產製媒體檔作業  (BABA0M170X.jsp)...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除 
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeOldMediaOnlineForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllOldMediaOnlineCaseList(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    /**
     * 批次處理 - 線上產製媒體檔作業- 失能年金線上批次產製媒體檔作業 (BABA0M180X.jsp)- 執行頁面 (BABA0M180X.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledMediaOnline(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledMediaOnline() : 批次處理 - 線上產製媒體檔作業- 失能年金線上批次產製媒體檔作業 (BABA0M180X.jsp)...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除 
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeDisabledMediaOnlineForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllDisabledMediaOnlineCaseList(request);
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    /**
     * 批次處理 - 線上產製媒體檔作業- 遺屬年金線上批次產製媒體檔作業 (BABA0M190X.jsp)- 執行頁面 (BABA0M190X.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSurvivorMediaOnline(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterSurvivorMediaOnline() : 批次處理 - 線上產製媒體檔作業- 遺屬年金線上批次產製媒體檔作業  (BABA0M190X.jsp)...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除 
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeSurvivorMediaOnlineForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllSurvivorMediaOnlineCaseList(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    /**
     * 受理作業 - 失能年金批次受理作業 - 受理頁面 (baap0d020a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledAnnuityReceipt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledAnnuityReceipt() : 受理作業 - 失能年金給付受理作業 (baap0d020a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeDisabledAnnuityReceiptForm(request);
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeDisabledAnnuityReceiptFamForm(request);
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeDisabledAnnuityReceiptQryCondForm(request);
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeDisabledAnnuityReceiptQueryForm(request);
        tw.gov.bli.ba.receipt.helper.CaseSessionHelper.removeAllDisabledAnnuityReceiptCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 受理頁面 (baap0d030a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSurvivorAnnuityReceipt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldAgeAnnuityReceipt() : 受理作業 - 遺屬年金給付受理作業 (baap0d030a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeSurvivorAnnuityReceiptForm(request);
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeSurvivorAnnuityReceiptBenForm(request);
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeSurvivorAnnuityReceiptQryCondForm(request);
        tw.gov.bli.ba.receipt.helper.FormSessionHelper.removeSurvivorAnnuityReceiptQryForm(request);
        tw.gov.bli.ba.receipt.helper.CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 (bamo0d160c.jsp bamo0d161c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledCheckMarkLevelAdjust(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledCheckMarkLevelAdjust() : 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 (bamo0d160c.jsp bamo0d161c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeDisabledCheckMarkLevelAdjustForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeDisabledCheckMarkLevelAdjustCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整 (bamo0d260c.jsp bamo0d261c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSurvivorCheckMarkLevelAdjust(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterSurvivorCheckMarkLevelAdjust() : 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整 (bamo0d260c.jsp bamo0d261c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeSurvivorCheckMarkLevelAdjustForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeSurvivorCheckMarkLevelAdjustCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledApplicationDataUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledApplicationDataUpdate() : 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeDisabledApplicationDataUpdateForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeDisabledApplicationDataUpdateCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSurvivorApplicationDataUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterSurvivorApplicationDataUpdate() : 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeSurvivorApplicationDataUpdateForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeSurvivorApplicationDataUpdateCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterReviewFeeReceipt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterReviewFeeReceipt() : 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.reviewfee.helper.FormSessionHelper.removeReviewFeeReceiptForm(request);
        tw.gov.bli.ba.reviewfee.helper.CaseSessionHelper.removeReviewFeeReceiptCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterReviewFeeReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterReviewFeeReview() : 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.reviewfee.helper.FormSessionHelper.removeReviewFeeReviewForm(request);
        tw.gov.bli.ba.reviewfee.helper.CaseSessionHelper.removeReviewFeeReviewCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp bare0d031a.jsp bare0d032a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterReviewFeeDecision(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterReviewFeeDecision() : 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp bare0d031a.jsp bare0d032a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.reviewfee.helper.FormSessionHelper.removeReviewFeeDecisionForm(request);
        tw.gov.bli.ba.reviewfee.helper.CaseSessionHelper.removeReviewFeeDecisionCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 複檢費用 - 複檢費用列印作業 - 複檢費用受理編審清單 - 查詢頁面 (bare0d040l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterReviewFeeReceiptRpt01(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterReviewFeeReceiptRpt01() : 複檢費用 - 複檢費用列印作業 - 複檢費用受理編審清單 - 查詢頁面 (bare0d040l.jsp) ...");
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 複檢費用 - 複檢費用列印作業 - 複檢費用核定通知書 - 查詢頁面 (bare0d041l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterReviewFeeReceiptRpt02(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterReviewFeeReceiptRpt01() : 複檢費用 - 複檢費用列印作業 - 複檢費用核定通知單 - 查詢頁面 (bare0d041l.jsp) ...");
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 複檢費用 - 複檢費用列印作業 - 複檢費用核定清單 - 查詢頁面 (bare0d042l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterReviewFeeReceiptRpt03(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterReviewFeeReceiptRpt01() : 複檢費用 - 複檢費用列印作業 - 複檢費用核定清單 - 查詢頁面 (bare0d042l.jsp) ...");
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 眷屬資料更正 - 查詢頁面 (bamo0d080c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDependantDataUpdateQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDependantDataUpdateQuery() : 更正作業 - 眷屬資料更正 (bamo0d070c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeDependantDataUpdateQueryFrom(request);
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeDependantDataUpdateDetailForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removePayeeDataUpdateQueryCase(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removePayeeDataUpdateList(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理 - 查詢頁面  (bamo0d090c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOldAgeDeathRepay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldAgeDeathRepay() : 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理 - 查詢頁面 (bamo0d090c.jsp) ...");
        
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeOldAgeDeathRepayForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeCheckOldAgeDeathRepayDataCase(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeOldAgeDeathRepayDataCaseList(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeHeirSeqNoExistDataCaseList(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeRepayIssueAmtDataCaseList(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面  (bamo0d100c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOldAgePaymentReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldAgePaymentReceive() : 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 (bamo0d100c.jsp) ...");
        
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 查詢頁面  (bamo0d200c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledPaymentReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledPaymentReceive() : 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 查詢頁面 (bamo0d200c.jsp) ...");
        
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理 - 查詢頁面  (bamo0d300c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSurvivorPaymentReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterSurvivorPaymentReceive() : 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理 - 查詢頁面 (bamo0d300c.jsp) ...");
        
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 更正作業 - 失能年金受款人資料更正 - 查詢頁面 (bamo0d180c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledPayeeDataUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledPayeeDataUpdate() : 更正作業 - 受款人資料更正 - 失能年金受款人資料更正 (bamo0d180c.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeDisabledPayeeDataUpdateQueryForm(request);
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeDisabledPayeeDataUpdateDetailForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeDisabledPayeeDataUpdateQueryCase(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeDisabledPayeeDataUpdateDetailCase(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeDisabledPayeeDataUpdateList(request);
        // tw.gov.bli.ba.update.helper.CaseSessionHelper.removeDisabledPayeeDataForBadaprCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 (baco0d210a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSurvivorPaymentReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterSurvivorPaymentReview() : 審核作業 - 遺屬年金給付審核作業 (baco0d210a.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.review.helper.FormSessionHelper.removeSurvivorPaymentReviewForm(request);
        tw.gov.bli.ba.review.helper.FormSessionHelper.removeSurvivorPaymentReviewQueryForm(request);
        tw.gov.bli.ba.review.helper.CaseSessionHelper.removeAllSurvivorPaymentReviewCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正 - 查詢頁面 (bamo0d280c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSurvivorPayeeDataUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterSurvivorPayeeDataUpdate() : 更正作業 - 受款人資料更正 - 遺屬年金受款人資料更正 (bamo0d280c.jsp) ...");

        HttpSession session = request.getSession();
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeSurvivorPayeeDataUpdateQueryForm(request);
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeSurvivorPayeeDataUpdateDetailForm(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeSurvivorPayeeDataUpdateQueryCase(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeSurvivorPayeeDataUpdateDetailCase(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeSurvivorPayeeDataUpdateList(request);
        tw.gov.bli.ba.update.helper.CaseSessionHelper.removeSurvivorPayeeDataForBadaprCase(request);
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 線上月核定作業 - 老年年金線上月核定作業 - 查詢頁面  (baba0m110x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthL() : 批次處理 - 線上月核定作業 - 老年年金線上月核定作業 - 查詢頁面  (baba0m110x.jsp) ...");
        
        HttpSession session = request.getSession();
        
    	// 核定年月條件於確認時，僅能輸入目前qryISSUYM所查出的核定年月減一個月。
    	// 讀取目前系統最大核定年月
        String maxIssuYm = bjService.selectMaxIssuYmBy("L");
        String checkIssuYm = DateUtility.calMonth(maxIssuYm+"01", -1).substring(0, 6);
        // 查詢系統日期是否介於批次月試核及月核定期間，若是則[確認]鍵disable。
        BigDecimal checkCount = bjService.selectCheckCountForBjMonthBy("L");
        //BigDecimal checkCount = new BigDecimal("0");
        String checkCountStr = "";
        
        if(checkCount.intValue() > 0 ){
        	checkCountStr = "Y";
        }
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeMonthLForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeMonthLCheckCountStr(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.setMonthLCheckCountStr(checkCountStr, request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.setMonthQueryIssuYmStr(DateUtility.changeWestYearMonthType(checkIssuYm), request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 線上月核定作業 - 失能年金線上月核定作業 - 查詢頁面  (baba0m120x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthK(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthK() : 批次處理 - 線上月核定作業 - 失能年金線上月核定作業 - 查詢頁面  (baba0m120x.jsp) ...");
        
        HttpSession session = request.getSession();
        
    	// 核定年月條件於確認時，僅能輸入目前qryISSUYM所查出的核定年月減一個月。
    	// 讀取目前系統最大核定年月
        String maxIssuYm = bjService.selectMaxIssuYmBy("K");
        String checkIssuYm = DateUtility.calMonth(maxIssuYm+"01", -1).substring(0, 6);
        // 查詢系統日期是否介於批次月試核及月核定期間，若是則[確認]鍵disable。
        BigDecimal checkCount = bjService.selectCheckCountForBjMonthBy("K");
        String checkCountStr = "";
        
        if(checkCount.intValue() > 0 ){
        	checkCountStr = "Y";
        }
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeMonthKForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeMonthLCheckCountStr(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.setMonthLCheckCountStr(checkCountStr, request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.setMonthQueryIssuYmStr(DateUtility.changeWestYearMonthType(checkIssuYm), request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面  (baba0m130x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthS() : 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面  (baba0m130x.jsp) ...");
        
        HttpSession session = request.getSession();
        
    	// 核定年月條件於確認時，僅能輸入目前qryISSUYM所查出的核定年月減一個月。
    	// 讀取目前系統最大核定年月
        String maxIssuYm = bjService.selectMaxIssuYmBy("S");
        String checkIssuYm = DateUtility.calMonth(maxIssuYm+"01", -1).substring(0, 6);
        // 查詢系統日期是否介於批次月試核及月核定期間，若是則[確認]鍵disable。
        BigDecimal checkCount = bjService.selectCheckCountForBjMonthBy("S");
        String checkCountStr = "";
        
        if(checkCount.intValue() > 0 ){
        	checkCountStr = "Y";
        }
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeMonthSForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeMonthLCheckCountStr(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.setMonthLCheckCountStr(checkCountStr, request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.setMonthQueryIssuYmStr(DateUtility.changeWestYearMonthType(checkIssuYm), request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 線上月試核作業 - 老年年金線上月試核作業 - 查詢頁面  (baba0m080x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthCheckL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthCheckL() : 批次處理 - 線上月試核作業 - 老年年金線上月試核作業 - 查詢頁面  (baba0m080x.jsp) ...");
        
        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeMonthCheckLForm(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 查詢頁面  (baba0m090x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthCheckK(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthCheckK() : 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 查詢頁面  (baba0m090x.jsp) ...");
        
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeMonthCheckKForm(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 線上月試核作業 - 遺屬年金線上月試核作業 - 查詢頁面  (baba0m100x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthCheckS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthCheckS() : 批次處理 - 線上月試核作業 - 遺屬年金線上月試核作業 - 查詢頁面  (baba0m100x.jsp) ...");
        
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeMonthCheckSForm(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 批次月處理作業 - 老年年金批次月處理作業 - 查詢頁面  (baba0m050x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthBatchL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthBatchL() : 批次處理 - 批次月處理作業 - 老年年金批次月處理作業 - 查詢頁面  (baba0m050x.jsp) ...");
        
        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeMonthBatchLForm(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 批次月處理作業 - 失能年金批次月處理作業 - 查詢頁面  (baba0m060x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthBatchK(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthBatchK() : 批次處理 - 批次月處理作業 - 失能年金批次月處理作業 - 查詢頁面  (baba0m060x.jsp) ...");
        
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeMonthBatchKForm(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面  (baba0m070x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterMonthBatchS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterMonthBatchS() : 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面  (baba0m070x.jsp) ...");
        
        HttpSession session = request.getSession();
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeMonthBatchSForm(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 線上產製媒體檔作業- 老年年金線上批次產製媒體檔作業- 執行頁面 (BABA0M170X.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOldMediaBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldMediaBatch() : 批次處理 - 線上產製媒體檔作業- 老年年金批次產製媒體檔作業  (BABA0M170X.jsp)...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除 
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeOldMediaBatchForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllOldMediaOnlineCaseList(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    /**
     * 批次處理 - 線上產製媒體檔作業- 失能年金線上批次產製媒體檔作業 (BABA0M180X.jsp)- 執行頁面 (BABA0M180X.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledMediaBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledMediaOnline() : 批次處理 - 線上產製媒體檔作業- 失能年金線上批次產製媒體檔作業 (BABA0M180X.jsp)...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除 
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeDisabledMediaBatchForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllDisabledMediaOnlineCaseList(request);
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    /**
     * 列印作業 - 其他類報表 - 轉催核定清單 - 查詢頁面 (balp0d650l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt06(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOtherRpt06() : 其他類報表 - 轉催核定清單 - 查詢頁面  (balp0d650l.jsp)...");

        HttpSession session = request.getSession();
        
        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 轉催核定清冊- 查詢頁面 (balp0d660l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt07(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOtherRpt07() : 其他類報表 - 轉催核定清單 - 查詢頁面  (balp0d660l.jsp)...");

        HttpSession session = request.getSession();
        
        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 轉催核定清單 - 查詢頁面 (balp0d650l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt08(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOtherRpt08() : 其他類報表 - 轉呆核定清單 - 查詢頁面  (balp0d670l.jsp)...");

        HttpSession session = request.getSession();
        
        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 轉催核定清冊- 查詢頁面 (balp0d660l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt09(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOtherRpt09() : 其他類報表 - 轉呆核定清冊 - 查詢頁面  (balp0d680l.jsp)...");

        HttpSession session = request.getSession();
        
        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    /**
     * 批次處理 - 線上產製媒體檔作業- 遺屬年金線上批次產製媒體檔作業 (BABA0M190X.jsp)- 執行頁面 (BABA0M190X.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSurvivorMediaBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterSurvivorMediaOnline() : 批次處理 - 線上產製媒體檔作業- 遺屬年金線上批次產製媒體檔作業  (BABA0M190X.jsp)...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除 
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeSurvivorMediaBatchForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllSurvivorMediaOnlineCaseList(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 檢核作業 - 產生檢核案件作業 - 查詢頁面 (baba0m200x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterCheckJobRpt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	log.debug("執行 MenuDispatchAction.enterCheckJobRpt() : 批次處理 - 檢核作業 - 產生檢核案件作業 - 查詢頁面 (baba0m200x.jsp) ...");

        HttpSession session = request.getSession();
        
        CheckJobRptForm queryForm = new CheckJobRptForm();
        
        queryForm.setIssuYm(DateUtility.getNowChineseDate().substring(0, 5));

        // 帶入預設核定年月(系統年月)
        tw.gov.bli.ba.bj.helper.FormSessionHelper.setCheckJobRptForm(queryForm, request);
        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 檢核作業 - 檢核確認作業 - 查詢頁面 (baba0m210x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterCheckJob(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	log.debug("執行 MenuDispatchAction.enterCheckJob() : 批次處理 - 檢核作業 - 檢核確認作業 - 查詢頁面 (baba0m210x.jsp) ...");

        HttpSession session = request.getSession();
        
        CheckJobForm queryForm = new CheckJobForm();
        
        queryForm.setIssuYm(DateUtility.getNowChineseDate().substring(0, 5));
        
        // 帶入預設核定年月(系統年月)
        tw.gov.bli.ba.bj.helper.FormSessionHelper.setCheckJobForm(queryForm, request);

        // 取得給付種類下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());
        
        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除 
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeCheckJobCaseList(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 批次排程作業 - 批次程式設定作業 - 查詢頁面 (baba0m220x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSetProcedure(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterSetProcedure() : 批次處理 - 批次排程作業 - 批次程式設定作業 - 查詢頁面 (baba0m220x.jsp) ...");

        HttpSession session = request.getSession();

        // 取得下拉選單的內容
        session.setAttribute(ConstantKey.PROCEDURE_NAME_LIST, selectOptionService.getProcedureList());   

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 批次排程作業 - 批次程式執行作業 - 查詢頁面 (baba0m230x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterRunProcedure(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterRunProcedure() : 批次處理 - 批次排程作業 - 批次程式執行作業 - 查詢頁面 (baba0m230x.jsp) ...");

        HttpSession session = request.getSession();

        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeRunProcedureForm(request);
        
        // 取得下拉選單的內容
        session.setAttribute(ConstantKey.PROCEDURE_NAME_LIST, selectOptionService.getProcedureList());        
        
        //新增預設帶入啟動時間
        RunProcedureForm detailForm = new RunProcedureForm();
        detailForm.setRunDateBegin(DateUtility.getNowWestDateTime(true));
        
        tw.gov.bli.ba.bj.helper.FormSessionHelper.setRunProcedureForm(detailForm, request);
        
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 批次排程作業 - 批次程式查詢作業 - 查詢頁面 (baba0m240x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterQryProcedure(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterQryProcedure() : 批次處理 - 批次排程作業 - 批次程式查詢作業 - 查詢頁面 (baba0m240x.jsp) ...");

        HttpSession session = request.getSession();
        
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeQryProcedureForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllPaymentCaseList(request);
        // 取得給付別下拉選單的內容
        //session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }  
    
    
    /**
     * 繳款單作業 - 開單維護作業 - 查詢頁面 (BAPM0D010L.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterPaymentProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterPaymentProcess() : 繳款單作業 - 開單維護作業 - 查詢頁面(BAPM0D010L.jsp) ...");

        HttpSession session = request.getSession();
        
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removePaymentProcessQueryForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllPaymentCaseList(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }   
    
    /**
     * 繳款單作業 - 繳款單列印作業 - 頁面 (BAPM0D020L.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterPaymentReprint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterPaymentProcess() : 繳款單作業 - 開單維護作業 - 查詢頁面(BAPM0D010L.jsp) ...");

        HttpSession session = request.getSession();
        
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removePaymentReprintForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllPaymentCaseList(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }   
    /**
     *  繳款單作業 - 查詢作業 - 查詢頁面 (BAPM0D030L.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterPaymentProgressQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterPaymentProcess() : 繳款單作業 - 開單維護作業 - 查詢頁面(BAPM0D010L.jsp) ...");

        HttpSession session = request.getSession();
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removePaymentQueryForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeAllPaymentCaseList(request);
        // 取得給付別下拉選單的內容
        //session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 穿透案件統計表 - 查詢頁面 (balp0d550l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt10(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOtherRpt10() : 列印作業 - 其他類報表 - 穿透案件統計表 - 查詢頁面 (balp0d550l.jsp) ...");

        HttpSession session = request.getSession();

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 列印作業 - 其他類報表 - 老年差額金通知補印作業 - 查詢頁面 (balp0d560l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt11(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOtherRpt11() : 列印作業 - 其他類報表 - 老年差額金通知補印作業 - 查詢頁面 (balp0d560l.jsp) ...");

        HttpSession session = request.getSession();

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 其他類報表 - 批次老年差額金通知列印作業 - 查詢頁面 (balp0d570l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOtherRpt12(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOtherRpt11() : 列印作業 - 其他類報表 - 批次老年差額金通知列印作業 - 查詢頁面 (balp0d570l.jsp) ...");

        HttpSession session = request.getSession();

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 - 查詢頁面 (balp0d690l.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterBatchPaymentReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterBatchPaymentReceive() : 應收收回相關報表 - 整批收回核定清單 - 查詢頁面  (balp0d690l.jsp)...");

        HttpSession session = request.getSession();
        
        tw.gov.bli.ba.update.helper.FormSessionHelper.removeBatchPaymentReceiveForm(request);
        
        // 取得給付別下拉選單的內容
        session.setAttribute(ConstantKey.PAYKIND_OPTION_LIST, selectOptionService.getPayKindOptionList());

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次作業 - 年金統計執行作業 - 查詢頁面 (bast0m270s.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterExecStatistics(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterExecStatistics() : 批次處裡 - 年金統計執行作業 - 查詢頁面  (bast0m270s.jsp)...");

        HttpSession session = request.getSession();
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeExecStatisticsForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeExecStatisticsDataDtlList(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeExecStatisticsDataList(request);
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 補正核付資料作業 - 老年年金補正核付作業 (baba0m240f.jsp)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterOldAgeAnnuityPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldAgeAnnuityPayment() : 批次處理 - 補正核付資料作業 - 老年年金補正核付作業 (baba0m240f.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeCompPaymentForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeCompPaymentCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }

    /**
     * 批次處理 - 補正核付資料作業 - 失能年金補正核付作業 (baba0m250f.jsp)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterDisabledAnnuityPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterDisabledAnnuityPayment() : 批次處理 - 補正核付資料作業 - 失能年金補正核付作業 (baba0m250f.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeCompPaymentForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeCompPaymentCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 批次處理 - 補正核付資料作業 - 遺屬年金補正核付作業 (baba0m260f.jsp)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterSurvivorAnnuityPayment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterOldAgeAnnuityPayment() : 批次處理 - 補正核付資料作業 - 老年年金補正核付作業 (baba0m240f.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeCompPaymentForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeCompPaymentCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    
    /**
     * 查詢作業 - 年金統計查詢 (baiq0d070q.jsp)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterAnnuityStatistics(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterAnnuityStatistics() : 查詢作業 - 年金統計查詢 (baiq0d070q.jsp) ...");

        HttpSession session = request.getSession();

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        tw.gov.bli.ba.bj.helper.FormSessionHelper.removeCompPaymentForm(request);
        tw.gov.bli.ba.bj.helper.CaseSessionHelper.removeCompPaymentCase(request);

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    /**
     * 檢核作業 - 執行檢核作業 (baba0x220x.jsp)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward enterCheckExec(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 MenuDispatchAction.enterCheckExec() : 檢核作業 - 執行檢核作業 ...");

        HttpSession session = request.getSession();

        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;	
    }
    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }
}
