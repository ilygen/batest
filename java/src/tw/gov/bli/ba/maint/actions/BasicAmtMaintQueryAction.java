package tw.gov.bli.ba.maint.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.jaxen.function.SubstringFunction;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import tw.gov.bli.ba.maint.forms.BasicAmtMaintQueryForm;
import tw.gov.bli.ba.maint.cases.BasicAmtMaintCase;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class BasicAmtMaintQueryAction extends DispatchAction {
    private static Log log = LogFactory.getLog(BasicAmtMaintListAction.class);

    private MaintService maintService;

    private static final String FORWARD_QUERY_BASIC_AMT_LIST = "querySuccess";
    
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 老年年金調整明細維護作業 - 查詢頁面BasicAmtMaintQueryAction.doQuery() 開始 ... ");
        
        String payYmB = null;

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        
        BasicAmtMaintQueryForm queryForm = (BasicAmtMaintQueryForm) form;
        queryForm.setPayCode("L");
        
       //將民國轉為西元
        queryForm.setPayYmB(DateUtility.changeChineseYearMonthType(queryForm.getPayYmB()));
      
        
        try {
            List<BasicAmtMaintCase> applyList = maintService.getBasicAmtMaintQueryCaseBy(queryForm.getPayYmB(),queryForm.getPayCode());
           
            if (!queryForm.getPayYmB().equals(""))
            	payYmB = queryForm.getPayYmB();
            
            CaseSessionHelper.setBasicAmtMaintQueryCase(applyList, request);
            FormSessionHelper.setBasicAmtMaintQueryForm(queryForm, payYmB, request);

            return mapping.findForward(FORWARD_QUERY_BASIC_AMT_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("BasicAmtMaintQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    


    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }

}
