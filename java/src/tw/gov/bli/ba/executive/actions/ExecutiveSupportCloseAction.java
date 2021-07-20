package tw.gov.bli.ba.executive.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportCloseCase;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportDataCase;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportCloseForm;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportCloseListForm;
import tw.gov.bli.ba.executive.helper.CaseSessionHelper;
import tw.gov.bli.ba.executive.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.ExecutiveService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 行政支援作業 - 行政支援記錄銷案 - 查詢頁面 (basu0d020d.jsp)
 * 
 * @author jerry
 */
public class ExecutiveSupportCloseAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ExecutiveSupportCloseAction.class);

    private ExecutiveService executiveService;

    /**
     * 行政支援作業 - 行政支援記錄銷案 - 查詢頁面 (basu0d0120d.jsp) <br>
     * 按下「確定」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 查詢頁面 ExecutiveSupportCloseAction.doModify() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ExecutiveSupportCloseForm queryForm = (ExecutiveSupportCloseForm) form;
        ExecutiveSupportCloseListForm queryListForm = new ExecutiveSupportCloseListForm();

        try {
            // 根據進入 行政支援記錄銷案頁面 設定受理編號 以及 查詢失敗頁面
            String qryFromWhere = queryForm.getQryFromWhere();
            String apNo = "";
            if (("BASU0D020D").equals(qryFromWhere)) {
                apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            }
            else if (("BACO0D013Q").equals(qryFromWhere) || ("BACO0D113Q").equals(qryFromWhere) || ("BACO0D213Q").equals(qryFromWhere)) {
                apNo = queryForm.getApNo();
            }
            String issuYm;
            if (queryForm.getIssuYm() != null){
                issuYm = DateUtility.changeChineseYearMonthType(queryForm.getIssuYm());
            }else{
                issuYm = "";
            }

            // 查詢主檔是否有符合資料
            ExecutiveSupportCloseCase caseData = executiveService.getExecutiveSupportCloseBy(apNo, issuYm);

            if (caseData == null) {
                CaseSessionHelper.removeExecutiveSupportCloseListCase(request);
                CaseSessionHelper.removeExecutiveSupportCloseCase(request);
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL); 
            }

            // 查詢Maadmrec資料
            List<ExecutiveSupportDataCase> caseList = executiveService.getExecutiveSupportCloseListBy(apNo, issuYm);

            // 行政支援記錄銷案查詢結果列表中的 CheckBox 及【確認】鍵狀態

            Maadmrec maadmrec = null;
            
            Integer q1 = executiveService.getUpdateLetterTypeOptionList(apNo, issuYm);
            Integer q2 = executiveService.getButtonStatus(apNo);
            Integer q3 = 0;
            for(ExecutiveSupportDataCase caseListData:caseList){
                if((!"21".equals(caseListData.getLetterType())&&!"22".equals(caseListData.getLetterType())&&!"23".equals(caseListData.getLetterType())&&
                                !"24".equals(caseListData.getLetterType())&&!"30".equals(caseListData.getLetterType())&&!"31".equals(caseListData.getLetterType())&&
                                !"40".equals(caseListData.getLetterType())&&!"Y".equals(caseListData.getPayMk()))||caseListData.getClosDate()==null){
                    q3+=1;
                    
                }
            }
            
            if (caseList.size() != 0) {

                if (q2 > 0) {
                    session.setAttribute("buttonStatus", "true");
                }
                else if (q2 <= 0) {
                    session.setAttribute("buttonStatus", "false");
                  if(q3>0){
                      session.setAttribute("buttonStatusTemp", "false");
                  }else{
                      session.setAttribute("buttonStatusTemp", "true");
                  }
                }
                else {
                    session.setAttribute("buttonStatus", "false");
                    
                }
            }

            CaseSessionHelper.setExecutiveSupportCloseCase(caseData, request);
            CaseSessionHelper.setExecutiveSupportCloseListCase(caseList, request);
            FormSessionHelper.setExecutiveSupportCloseQueryForm(queryForm, request);

            log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 查詢頁面 ExecutiveSupportCloseAction.doModify() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportCloseAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setExecutiveService(ExecutiveService executiveService) {
        this.executiveService = executiveService;
    }

}
