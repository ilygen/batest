package tw.gov.bli.ba.maint.actions;

import java.math.BigDecimal;
import java.util.Iterator;
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
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Babasicamt;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.domain.Bacpirec;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import tw.gov.bli.ba.maint.forms.BasicAmtMaintDetailForm;
import tw.gov.bli.ba.maint.forms.BasicAmtMaintListForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintListForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintQueryForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintDetailForm;
import tw.gov.bli.ba.maint.cases.BasicAmtMaintCase;
import tw.gov.bli.ba.maint.cases.CpiDtlMaintCase;
import tw.gov.bli.ba.maint.forms.CpiRecMaintListForm;
import tw.gov.bli.ba.maint.forms.CpiRecMaintQueryForm;
import tw.gov.bli.ba.maint.forms.CpiRecMaintDetailForm;
import tw.gov.bli.ba.maint.cases.CpiRecMaintCase;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class SurvivorBasicAmtMaintListAction extends DispatchAction {
    private static Log log = LogFactory.getLog(SurvivorBasicAmtMaintListAction.class);

    private MaintService maintService;

    private static final String FORWARD_INSERT_BASIC_AMT = "insertBasicAmt"; // 遺屬年金基本金額調整作業新增作業頁面
    private static final String FORWARD_UPDATE_BASIC_AMT = "updateBasicAmt"; // 遺屬年金基本金額調整作業修改作業頁面
    private static final String FORWARD_BASIC_AMT = "basicAmt"; 
    private static final String FORWARD_CHECK_PAYYME_FAIL = "checkpayymeFail"; 

    /**
     * 維護作業 - 遺屬年金基本金額調整作業 - 查詢頁面 (bapa0x101f.jsp) <br>
     * 按下「新增」的動作
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */

    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 遺屬年金基本金額調整作業 - 查詢頁面 SurvivorBasicAmtMaintListAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        
        try{
        
        FormSessionHelper.removeBasicAmtMaintQueryForm(request);
        FormSessionHelper.removeBasicAmtMaintDetailForm(request);

      //取得最後一筆資料迄月
        List<BasicAmtMaintCase> applyList = CaseSessionHelper.getBasicAmtMaintQueryCase(request);
          
        BasicAmtMaintDetailForm insertForm = new BasicAmtMaintDetailForm();
        
        //選取修改資料的前一筆資料  取得前一筆加計金額使用
        //帶入最後一筆資料之加計金額隱藏欄位
        if(applyList.size() == 0){
        	//沒資料金額帶入0
          	BigDecimal lastBasicAmt = new BigDecimal("0");
          	insertForm.setLastBasicAmt(lastBasicAmt);
          	//帶入前一筆日期+1個月，沒資料起日帶入08901，判斷會+1所以帶入09800
          	insertForm.setPayYmB("09801");
          	insertForm.setPayYmE("09801");
        }else{
        	//取得form前一筆資料
          	BasicAmtMaintCase beforecaseData = applyList.get(0);
          	insertForm.setLastBasicAmt(beforecaseData.getBasicAmt());
            //自動帶入前一筆日期+1個月
            String BeforePayYmB = beforecaseData.getPayYmE();
            
            //前一筆資料的給付年月迄月為空，不得執行新增作業
            if(StringUtils.isBlank(BeforePayYmB)){
            	saveMessages(session, CustomMessageHelper.getCheckPayYmEFailMessage());
            	log.debug("執行 維護作業 - 老人年金加計金額調整作業 - 新增頁面BasicAmtMaintListAction.doInsert() 完成 ... ");
                return mapping.findForward(FORWARD_CHECK_PAYYME_FAIL);
            }
          //自動帶入前一筆日期+1個月
            if(BeforePayYmB.substring(3, 5).equals("12")){
                String lastPayYmB = Integer.toString((Integer.parseInt(BeforePayYmB) + 89));
                if(lastPayYmB.length()==4){
                lastPayYmB = 0+lastPayYmB;
                }
            	insertForm.setPayYmB(lastPayYmB);
            }else{
            	String lastPayYmB = Integer.toString((Integer.parseInt(BeforePayYmB) + 1));
            	if(lastPayYmB.length()==4){
            	lastPayYmB = 0+lastPayYmB;
            	}
            	insertForm.setPayYmB(lastPayYmB);
            }
            }

        FormSessionHelper.setBasicAmtMaintDetailForm(insertForm, request);
        
        return mapping.findForward(FORWARD_INSERT_BASIC_AMT);
        
        }catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorBasicAmtMaintListAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
        }

    /**
     * 維護作業 - 遺屬年金基本金額調整作業 - 查詢頁面 (bapa0x101f.jsp) <br>
     * 按下「修改」的動作
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 遺屬年金基本金額調整作業  - 查詢頁面 SurvivorBasicAmtMaintListAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        BasicAmtMaintListForm queryForm = (BasicAmtMaintListForm) form;

        try {
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<BasicAmtMaintCase> applyList = CaseSessionHelper.getBasicAmtMaintQueryCase(request);
            BasicAmtMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);
            
            //設定給付類別 及 日期民國轉西元
            caseData.setPayCode("S");
            String payYmB = DateUtility.changeChineseYearMonthType(caseData.getPayYmB());
            // 取得老人年金調整資料
            Babasicamt detailData = maintService.getBasicAmtMaintForUpdateCaseBy(payYmB,caseData.getPayCode());
            //資料轉換為民國
            detailData.setCpiYear1(DateUtility.changeWestYearType(detailData.getCpiYear1()));
            detailData.setCpiYear2(DateUtility.changeWestYearType(detailData.getCpiYear2()));
            detailData.setPayYmB(DateUtility.changeWestYearMonthType(detailData.getPayYmB()));
            detailData.setPayYmE(DateUtility.changeWestYearMonthType(detailData.getPayYmE()));
            detailData.setSysDate(DateUtility.changeWestYearMonthType(detailData.getSysDate()));
            detailData.setDate(DateUtility.changeDateType(detailData.getDate()));
            
            BasicAmtMaintDetailForm updateForm = new BasicAmtMaintDetailForm();
            BeanUtility.copyProperties(updateForm, detailData);
            
            //選取修改資料的前一筆資料  取得前一筆加計金額使用
            //帶入最後一筆資料之加計金額隱藏欄位
              if(applyList.size() == Integer.parseInt(rowNum)){
              	BigDecimal lastBasicAmt = new BigDecimal("0");
              	updateForm.setLastBasicAmt(lastBasicAmt);
              }else{
              	BasicAmtMaintCase beforecaseData = applyList.get(Integer.parseInt(rowNum));
              	updateForm.setLastBasicAmt(beforecaseData.getBasicAmt());
              }
            
            //放入BasicAmtMaintDetailForm
            FormSessionHelper.setBasicAmtMaintDetailForm(updateForm, request);

            return mapping.findForward(FORWARD_UPDATE_BASIC_AMT);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息 
            log.error("SurvivorBasicAmtMaintListAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }



    /**
     * 維護作業 - 遺屬年金基本金額調整作業  - 刪除作業
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 老人年金加計金額紀錄查詢作業 - 刪除作業 SurvivirBasicAmtMaintListAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        BasicAmtMaintListForm queryForm = (BasicAmtMaintListForm) form;
        
        try {
        	String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<BasicAmtMaintCase> applyList = CaseSessionHelper.getBasicAmtMaintQueryCase(request);
            BasicAmtMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);
            //設定給付類別 及 日期民國轉西元
            caseData.setPayCode("S");
            String payYmB = DateUtility.changeChineseYearMonthType(caseData.getPayYmB());
            // 取得老年加計金額調整資料
            Babasicamt detailData = maintService.getBasicAmtMaintForUpdateCaseBy(payYmB,caseData.getPayCode());

            BasicAmtMaintDetailForm updateForm = new BasicAmtMaintDetailForm();
            BeanUtility.copyProperties(updateForm, detailData);
            
                        
            BasicAmtMaintCase caseObj = new BasicAmtMaintCase();
            BeanUtility.copyProperties(caseObj, updateForm);
            
   
            // 變更民國年為西元年            
            if (StringUtils.defaultString(caseObj.getPayYmB()).trim().length() == 5) {
                caseObj.setPayYmB(Integer.toString((Integer.parseInt(caseObj.getPayYmB().substring(0, 3)) + 1911))+ caseObj.getPayYmB().substring(3, 5));
            }            
            //設定給付類別
            caseObj.setPayCode("S");
            
            //刪除選取資料
            maintService.deleteBasicAmtData(userData, caseObj);
            
            //更新頁面資料
            List<BasicAmtMaintCase> applyList1 = maintService.getBasicAmtMaintQueryCaseBy(null,"S");
            CaseSessionHelper.setBasicAmtMaintQueryCase(applyList1, request);            
            //設定刪除成功訊息                        
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());
            
            log.debug("執行 維護作業 - 老人年金加計金額紀錄查詢作業 - 刪除作業 SurvivorBasicAmtMaintListAction.doDelete() 完成... ");
            
            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);    

        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("SurvivorBasicAmtMaintListAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }



    /**
     * 維護作業 - 遺屬年金基本金額調整作業 (bapa0x101f.jsp) <br>
     * 按下「返回」的動作
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        //移除查詢條件
    	FormSessionHelper.removeBasicAmtMaintQueryForm(request);
    	return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }

}
