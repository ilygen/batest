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
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportMaintCase;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportDataCase;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportMaintForm;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportMaintListForm;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportMaintModifyForm;
import tw.gov.bli.ba.executive.helper.CaseSessionHelper;
import tw.gov.bli.ba.executive.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.ExecutiveService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class ExecutiveSupportMaintListAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ExecutiveSupportMaintListAction.class);

    private static String FORWARD_MODIFY = "modify";
    private SelectOptionService selectOptionService;
    private ExecutiveService executiveService;

    /**
     * 行政支援記錄維護作業 - 行政支援記錄維護 - 清單頁面 (baisu0d012c.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援記錄維護作業 - 行政支援記錄維護 - 清單頁面 ExecutiveSupportMaintListAction.doDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ExecutiveSupportMaintListForm listForm = (ExecutiveSupportMaintListForm) form;
        
        CaseSessionHelper.removeExecutiveSupportMaintDetailCase(request);

        try {
            String index = listForm.getIndex();
            ExecutiveSupportMaintForm queryForm = FormSessionHelper.getExecutiveSupportMaintForm(request);

            List<ExecutiveSupportDataCase> caseList = CaseSessionHelper.getExecutiveSupportMaintListCase(request);
            ExecutiveSupportDataCase caseData = caseList.get(Integer.parseInt(index) - 1);
            
            ExecutiveSupportMaintModifyForm detailForm = new ExecutiveSupportMaintModifyForm();
            BeanUtility.copyProperties(detailForm, caseData);
            if(StringUtils.isNotBlank(caseData.getSlrpNo())){
                detailForm.setSlrpNo1(caseData.getSlrpNo().substring(0, 3));
                detailForm.setSlrpNo2(caseData.getSlrpNo().substring(3, 5));
                detailForm.setSlrpNo3(caseData.getSlrpNo().substring(5, 6));
                detailForm.setSlrpNo4(caseData.getSlrpNo().substring(6, 12));
                detailForm.setSlrpNo5(caseData.getSlrpNo().substring(12, 13));
            }
            
            if(StringUtils.isNotBlank(caseData.getSlrelate())){
                detailForm.setSlrelate1(caseData.getSlrelate().substring(0, 3));
                detailForm.setSlrelate2(caseData.getSlrelate().substring(3, 5));
                detailForm.setSlrelate3(caseData.getSlrelate().substring(5, 6));
                detailForm.setSlrelate4(caseData.getSlrelate().substring(6, 12));
                detailForm.setSlrelate5(caseData.getSlrelate().substring(12, 13));
            }
            
            if(StringUtils.isNotBlank(caseData.getCloseNo())){
                detailForm.setCloseNo1(caseData.getCloseNo().substring(0, 3));
                detailForm.setCloseNo2(caseData.getCloseNo().substring(3, 5));
                detailForm.setCloseNo3(caseData.getCloseNo().substring(5, 6));
                detailForm.setCloseNo4(caseData.getCloseNo().substring(6, 12));
                detailForm.setCloseNo5(caseData.getCloseNo().substring(12, 13));
            }

            // 取得下拉式選單資料
            // 依照編號的開頭判別是哪種年金
            String lpaymk = null, kpaymk = null, spaymk = null;
            if ("L".equals(queryForm.getApNo1())) {
                lpaymk = "1";
            }
            else if ("K".equals(queryForm.getApNo1())) {
                kpaymk = "1";
            }
            else if ("S".equals(queryForm.getApNo1())) {
                spaymk = "1";
            }
            
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            String issuYm = queryForm.getIssuYm();
            
          //行政支援記錄維護修改中 【確認】及【刪除】鍵狀態
            ExecutiveSupportDataCase executiveSupportDataCase = caseList.get(0);
            Integer q1 = executiveService.getUpdateLetterTypeOptionList(apNo, DateUtility.changeChineseYearMonthType(issuYm));
            Integer q2 = executiveService.getButtonStatus(apNo);
            
            session.setAttribute(ConstantKey.LETTERTYPE_OPTION_LIST,  selectOptionService.getLetterTypeOptionList());
            
            if(q2>0 && !"17".equals(caseData.getLetterType())){
                session.setAttribute("buttonStatus",  "true");
            }else if(q2<=0 && !"17".equals(caseData.getLetterType())){
                if(q1>0){
                    session.setAttribute("buttonStatus",  "false");
                }else if(q1<=0 && "21".equals(caseData.getLetterType())){
                    session.setAttribute("buttonStatus",  "true");
                }else if(q1<=0 && !"21".equals(caseData.getLetterType())){
                    session.setAttribute("buttonStatus",  "false");
                    session.setAttribute(ConstantKey.LETTERTYPE_OPTION_LIST,  selectOptionService.getUpdateLetterTypeOptionList());
                }
            }else if("17".equals(caseData.getLetterType())){
                
                session.setAttribute(ConstantKey.LETTERTYPE_OPTION_LIST,  selectOptionService.getUpdateLetterByBaban());
                
            }else{
                session.setAttribute("buttonStatus",  "false");
            }
            
            // 取得下拉式選單資料
            session.setAttribute(ConstantKey.NDOMK_OPTION_LIST, selectOptionService.getNdomkOptionList(caseData.getLetterType(), lpaymk, kpaymk, spaymk));
            
            // 取得給付性質下拉式選單資料
            session.setAttribute(ConstantKey.RELIEFTYP_OPTION_LIST, selectOptionService.getReliefTypOptionList());
            
            // 取得救濟種類下拉式選單資料
            session.setAttribute(ConstantKey.RELIEFKIND_OPTION_LIST, selectOptionService.getReliefKindOptionList());
            
            // 取得行政救濟辦理情形下拉式選單資料
            session.setAttribute(ConstantKey.RELIEFSTAT_OPTION_LIST, selectOptionService.getReliefStatOptionList(caseData.getReliefKind()));
            
            CaseSessionHelper.setExecutiveSupportMaintDetailCase(caseData, request);
            FormSessionHelper.setExecutiveSupportMaintModifyForm(detailForm, request);
            
            log.debug("執行 行政支援記錄維護作業 - 行政支援記錄維護 - 清單頁面 ExecutiveSupportMaintListAction.doDetail() 完成 ... ");

            return mapping.findForward(FORWARD_MODIFY);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportMaintListAction.doDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 行政支援記錄維護作業 - 行政支援記錄維護 - 清單頁面 (basu0d011a.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援記錄維護作業 - 行政支援記錄維護 - 清單頁面 ExecutiveSupportMaintListAction.doBack() 開始 ... ");

        HttpSession session = request.getSession();

        try {
            // 清除Session資料
            CaseSessionHelper.removeExecutiveSupportMaintCase(request);
            CaseSessionHelper.removeExecutiveSupportMaintDetailCase(request);
            FormSessionHelper.removeExecutiveSupportMaintForm(request);
            FormSessionHelper.removeExecutiveSupportMaintListForm(request);
            log.debug("執行 行政支援記錄維護作業 - 行政支援記錄維護 - 清單頁面 ExecutiveSupportMaintListAction.doBack() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_BACK);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportMaintListAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
    
    public void setExecutiveService(ExecutiveService executiveService) {
        this.executiveService = executiveService;
    }

}
