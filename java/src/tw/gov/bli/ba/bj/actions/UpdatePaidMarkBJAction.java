package tw.gov.bli.ba.bj.actions;

import java.math.BigDecimal;
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
import tw.gov.bli.ba.bj.cases.UpdatePaidMarkBJCase;
import tw.gov.bli.ba.bj.cases.BagivedtlCase;
import tw.gov.bli.ba.bj.forms.UpdatePaidMarkBJForm;
import tw.gov.bli.ba.bj.forms.UpdatePaidMarkBJDetailForm;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 批次處理 - 給付媒體回押註記作業 (BABA0M010X)
 * 
 * @author swim
 */
public class UpdatePaidMarkBJAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(UpdatePaidMarkBJAction.class);

    private BjService bjService;
    
    private static final String FORWARD_UPDATE_PAID_MARK_DETAIL = "updatePaidMarkDetail";

    /**
     * 批次處理 - 給付媒體回押註記作業 - 查詢頁面 (baba0m010x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 給付媒體回押註記作業 - 查詢頁面 UpdatePaidMarkBJAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        UpdatePaidMarkBJForm queryForm = (UpdatePaidMarkBJForm) form;

        try {
            String upTimeBeg = DateUtility.changeDateType(queryForm.getUpTimeBeg());
            String upTimeEnd = DateUtility.changeDateType(queryForm.getUpTimeEnd());

            List<UpdatePaidMarkBJCase> dataList = bjService.getUpdatePaidMarkBJListData(upTimeBeg, upTimeEnd);

            if (dataList.size() > 0) {

                // 將 查詢條件/查詢結果清單 存到 Session Scope
                CaseSessionHelper.setUpdatePaidMarkBJCase(dataList, request);
                FormSessionHelper.setUpdatePaidMarkBJForm(queryForm, request);

                log.debug("執行 批次處理 - 給付媒體回押註記作業 - 查詢頁面 UpdatePaidMarkBJAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
            else {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("執行 批次處理 - 給付媒體回押註記作業 - 查詢頁面 UpdatePaidMarkBJAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("UpdatePaidMarkBJAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 給付媒體回押註記作業 - 批次處理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 給付媒體回押註記作業 - 批次處理 UpdatePaidMarkBJAction.doBatch() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        UpdatePaidMarkBJForm iform = (UpdatePaidMarkBJForm) form;

        try {
            String[] baBatchRecId = iform.getIdForConfirm().split(",");
            String procMsg = "";
            
            // 執行 回壓處理狀態
            for (String recId : baBatchRecId) {
                String[] recIds = {recId};
                BigDecimal batchRecId = new BigDecimal(recId.split(";")[0]);
                
                String payCode = bjService.doUpdatePaidMarkBJ(recIds, userData.getEmpNo(), userData);
                // 呼叫StoreProcedure Name:Ba_ProcGiveDtl('1',qry_BABATCHREC.BABATCHRECID)
                if (StringUtils.isNotBlank(payCode)) {
                    procMsg = bjService.doUpdatePaidMarkProcedure(payCode, batchRecId, userData);
                    log.info("給付媒體回押註記 - 呼叫 Stored Procedure 完成, procMsg = " + StringUtils.trimToEmpty(procMsg));
                }
            }

            // 重新查詢資料
            UpdatePaidMarkBJForm queryForm = FormSessionHelper.getUpdatePaidMarkBJForm(request);
            List<UpdatePaidMarkBJCase> dataList = bjService.getUpdatePaidMarkBJListData(DateUtility.changeDateType(queryForm.getUpTimeBeg()), DateUtility.changeDateType(queryForm.getUpTimeEnd()));
            CaseSessionHelper.setUpdatePaidMarkBJCase(dataList, request);

            // 設定批次處理成功訊息
            //String batchJobMessage = "批次處理成功   "+procMsg;
            String batchJobMessage = procMsg;
            saveMessages(session, CustomMessageHelper.getBatchJobMessage(batchJobMessage));
            log.debug("執行 批次處理 - 給付媒體回押註記作業 - 批次處理 UpdatePaidMarkBJAction.doBatch() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);

        }
        catch (Exception e) {
            // 設定批次處理失敗訊息
            log.error("UpdatePaidMarkBJAction.doBatch() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getBatchJobFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }
    
    /**
     * 批次處理 - 給付媒體回押註記作業 - 批次處理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 給付媒體回押註記作業 - 批次處理 UpdatePaidMarkBJAction.doUpdate() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        UpdatePaidMarkBJForm iform = (UpdatePaidMarkBJForm) form;

        try {
            String baBatchRecId = StringUtils.defaultString(request.getParameter("baBatchRecId"));
            String payDate = StringUtils.defaultString(request.getParameter("payDate"));
            String taTyp = StringUtils.defaultString(request.getParameter("taTyp"));
            BigDecimal baBatchRecIdBig = new BigDecimal(baBatchRecId);
            //抓取給付別
            String payCode = bjService.selectUpdatePaidMarkBJPayCodeBy(payDate,baBatchRecIdBig,taTyp);
            String payCodeStr = "";
            if(StringUtils.isNotBlank(payCode)){
            	if(payCode.equals("L")){
            		payCodeStr = "老年年金";
            	}else if (payCode.equals("K")){
            		payCodeStr = "失能年金";
            	}else if (payCode.equals("S")){
            		payCodeStr = "遺屬年金";
            	}
            }
            // 重新抓取資料
            UpdatePaidMarkBJCase detailData = bjService.getUpdatePaidMarkBJData(baBatchRecId);
            
            // 給付媒體檔案資料比對結果
            BagivedtlCase caseData = bjService.getUpdatePaidMarkBJData2(baBatchRecId,payCode,payDate,taTyp);

            // 給付媒體檔案轉入資訊
            List<BagivedtlCase> caseList = bjService.getUpdatePaidMarkBJData3(baBatchRecId);
            
            // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
            UpdatePaidMarkBJDetailForm updateForm = new UpdatePaidMarkBJDetailForm();
            BeanUtility.copyPropertiesForUpdate(updateForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
            updateForm.setPayCode(payCode);
            updateForm.setPayCodeStr(payCodeStr);
            CaseSessionHelper.setUpdatePaidMarkBJDetailCase(detailData, request);
            CaseSessionHelper.setUpdatePaidMarkBJDetailCase2(caseData, request);
            CaseSessionHelper.setUpdatePaidMarkBJDetailCase3(caseList, request);
            FormSessionHelper.setUpdatePaidMarkBJDetailForm(updateForm, request);
            
            log.debug("執行 批次處理 - 給付媒體回押註記作業 - 批次處理 UpdatePaidMarkBJAction.doUpdate() 完成 ... ");
            return mapping.findForward(FORWARD_UPDATE_PAID_MARK_DETAIL);

        }
        catch (Exception e) {
            // 設定批次處理失敗訊息
            log.error("UpdatePaidMarkBJAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 給付媒體回押註記作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 給付媒體回押註記作業 - 返回 UpdatePaidMarkBJAction.doBack() 開始 ... ");

        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeUpdatePaidMarkBJForm(request);
            CaseSessionHelper.removeUpdatePaidMarkBJCase(request);
            CaseSessionHelper.removeUpdatePaidMarkBJDetailCase(request);
            CaseSessionHelper.removeUpdatePaidMarkBJDetailCase2(request);
            CaseSessionHelper.removeUpdatePaidMarkBJDetailCase3(request);

            log.debug("執行 批次處理 - 給付媒體回押註記作業 - 返回 UpdatePaidMarkBJAction.doBack() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_BACK);

        }
        catch (Exception e) {
            log.error("UpdatePaidMarkBJAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }
}
