package tw.gov.bli.ba.executive.actions;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportMaintCase;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportMaintForm;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportDataCase;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.ExecutiveService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.ba.executive.helper.*;

/**
 * 行政支援作業 - 行政支援註記維護 - 查詢頁面 (basu0d010a.jsp)
 * 
 * @author jerry
 */
public class ExecutiveSupportMaintAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ExecutiveSupportMaintAction.class);

    private static final String FORWARD_ADD = "queryAdd"; // 行政支援註記維護新增
    private static final String FORWARD_MODIFY = "queryModify"; // 行政支援註記維護修改
    private ExecutiveService executiveService;
    private SelectOptionService selectOptionService;

    /**
     * 行政支援作業 - 行政支援記錄查詢 - 查詢頁面 (basu0d010a.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援註記維護 - 查詢頁面 ExecutiveSupportMaintAction.doAdd() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ExecutiveSupportMaintForm queryForm = (ExecutiveSupportMaintForm) form;

        try {
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            String issuYm = queryForm.getIssuYm();
            // 查詢資料
            ExecutiveSupportMaintCase caseData = executiveService.getExecutiveSupportMaintBy(apNo, DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()));
            
            if (caseData == null) {
                CaseSessionHelper.removeExecutiveSupportMaintCase(request);
                FormSessionHelper.removeExecutiveSupportMaintForm(request);
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            // 依照編號的開頭判別是哪種年金
            String lpaymk = null, kpaymk = null, spaymk = null;
            if ("L".equals(queryForm.getApNo1())) {
                lpaymk = "1";
                caseData.setKind("L");
            }
            else if ("K".equals(queryForm.getApNo1())) {
                kpaymk = "1";
                caseData.setKind("K");
            }
            else if ("S".equals(queryForm.getApNo1())) {
                spaymk = "1";
                caseData.setKind("S");
            }

            CaseSessionHelper.setExecutiveSupportMaintCase(caseData, request);
            FormSessionHelper.setExecutiveSupportMaintForm(queryForm, request);
            
            //取得處理函別下拉選單條件
            Integer q1 = executiveService.getUpdateLetterTypeOptionList(apNo, DateUtility.changeChineseYearMonthType(issuYm));

            // 取得下拉式選單資料
            session.setAttribute(ConstantKey.NDOMK_OPTION_LIST, selectOptionService.getNdomkOptionList("11", lpaymk, kpaymk, spaymk));
            
            // 取得給付性質下拉式選單資料
            session.setAttribute(ConstantKey.RELIEFTYP_OPTION_LIST, selectOptionService.getReliefTypOptionList());
            
            // 取得救濟種類下拉式選單資料
            session.setAttribute(ConstantKey.RELIEFKIND_OPTION_LIST, selectOptionService.getReliefKindOptionList());
            
            // 取得處理函別下拉式選單資料
            if(q1<=0){
                session.setAttribute(ConstantKey.LETTERTYPE_OPTION_LIST,  selectOptionService.getUpdateLetterTypeOptionList());
            }else{
                session.setAttribute(ConstantKey.LETTERTYPE_OPTION_LIST,  selectOptionService.getLetterTypeOptionList());
            }
            
            log.debug("執行 行政支援作業 - 行政支援註記維護 - 查詢頁面 ExecutiveSupportMaintAction.doAdd() 完成 ... ");
            return mapping.findForward(FORWARD_ADD);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportMaintAction.doAdd() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 行政支援作業 - 行政支援記錄查詢 - 查詢頁面 (basu0d010a.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doModify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援註記維護 - 查詢頁面 ExecutiveSupportMaintAction.doModify() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ExecutiveSupportMaintForm queryForm = (ExecutiveSupportMaintForm) form;

        try {
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            String issuYm = queryForm.getIssuYm();
            
            // 查詢資料
            ExecutiveSupportMaintCase caseData = executiveService.getExecutiveSupportMaintBy(apNo, DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()));
            
            if (caseData == null) {
                CaseSessionHelper.removeExecutiveSupportMaintCase(request);
                FormSessionHelper.removeExecutiveSupportMaintForm(request);
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            // 依照編號的開頭判別是哪種年金
            String lpaymk = null, kpaymk = null, spaymk = null;
            if ("L".equals(queryForm.getApNo1())) {
                lpaymk = "1";
                caseData.setKind("L");
            }
            else if ("K".equals(queryForm.getApNo1())) {
                kpaymk = "1";
                caseData.setKind("K");
            }
            else if ("S".equals(queryForm.getApNo1())) {
                spaymk = "1";
                caseData.setKind("S");
            }
                 
            // 查詢明細資料
            List<ExecutiveSupportDataCase> detailList = executiveService.getExecutiveSupportMaintListBy(apNo, DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()));
            
            
            if (detailList == null || detailList.size() == 0) {
                CaseSessionHelper.removeExecutiveSupportMaintCase(request); 
                FormSessionHelper.removeExecutiveSupportMaintForm(request);
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            
                
            
            CaseSessionHelper.setExecutiveSupportMaintCase(caseData, request);
            FormSessionHelper.setExecutiveSupportMaintForm(queryForm, request);
            CaseSessionHelper.setExecutiveSupportMaintListCase(detailList, request);
            
            log.debug("執行 行政支援作業 - 行政支援註記維護 - 查詢頁面 ExecutiveSupportMaintAction.doModify() 完成 ... ");

            return mapping.findForward(FORWARD_MODIFY);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportMaintAction.doModify() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setExecutiveService(ExecutiveService executiveService) {
        this.executiveService = executiveService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }

}
