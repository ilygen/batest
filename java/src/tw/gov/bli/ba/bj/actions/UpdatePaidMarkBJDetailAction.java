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
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.bj.cases.UpdatePaidMarkBJCase;
import tw.gov.bli.ba.bj.forms.UpdatePaidMarkBJDetailForm;
import tw.gov.bli.ba.bj.forms.UpdatePaidMarkBJForm;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class UpdatePaidMarkBJDetailAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(UpdatePaidMarkBJDetailAction.class);

    private BjService bjService;
    
    /**
     * 批次處理 - 給付媒體回押註記作業 - 詳細資料頁面 (baba0m012x.jsp)
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 給付媒體回押註記作業 - 詳細資料頁面 UpdatePaidMarkBJDetailAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        UpdatePaidMarkBJDetailForm detailForm = (UpdatePaidMarkBJDetailForm) form;
        String updatePaidMarkMessage="";
        String procMsg = "";
        
        try {

            if (detailForm != null) {
                UpdatePaidMarkBJCase detailData = new UpdatePaidMarkBJCase();
                BeanUtility.copyProperties(detailData, detailForm);

                // 存檔
                if(detailData.getProcFlag().equals("1")){
                     bjService.doUpdatePaidMarkBJDetailProFlag1(userData, detailData);
                }else if(detailData.getProcFlag().equals("2")){
                	 bjService.doUpdatePaidMarkBJDetailProFlag2(userData, detailData);
                }
                // [回押註記]radio=1(是)
                if(detailData.getProcFlag().equals("1")){
                    // 呼叫StoreProcedure Name:Ba_ProcGiveDtl('2',qry_BABATCHREC.BABATCHRECID)
                    procMsg = bjService.doUpdatePaidMarkStatus("2",detailForm.getPayCode(), detailData.getBaBatchRecId(), userData);
                }

                // 更新清單資料
                UpdatePaidMarkBJForm queryForm = (UpdatePaidMarkBJForm) FormSessionHelper.getUpdatePaidMarkBJForm(request);
                List<UpdatePaidMarkBJCase> dataList = bjService.getUpdatePaidMarkBJListData(DateUtility.changeDateType(queryForm.getUpTimeBeg()), DateUtility.changeDateType(queryForm.getUpTimeEnd()));
                CaseSessionHelper.setUpdatePaidMarkBJCase(dataList, request);

                // 設定回押作業完成成功訊息
                if(detailData.getProcFlag().equals("1")){
                     updatePaidMarkMessage = procMsg;
                }else if(detailData.getProcFlag().equals("2")){
                	 updatePaidMarkMessage = "G1007 回押作業完成";
                }
                saveMessages(session, CustomMessageHelper.getUpdatePaidMarkMessage(updatePaidMarkMessage));
            }

            log.debug("執行 更正作業 - 受款人資料更正作業 - 新增頁面  UpdatePaidMarkBJDetailAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            // 設定存檔失敗訊息
            log.error("UpdatePaidMarkBJDetailAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
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
        log.debug("執行 批次處理 - 給付媒體回押註記作業 - 返回 UpdatePaidMarkBJDetailAction.doBack() 開始 ... ");

        try {
            // 更新清單資料
            UpdatePaidMarkBJForm queryForm = (UpdatePaidMarkBJForm) FormSessionHelper.getUpdatePaidMarkBJForm(request);
            List<UpdatePaidMarkBJCase> dataList = bjService.getUpdatePaidMarkBJListData(DateUtility.changeDateType(queryForm.getUpTimeBeg()), DateUtility.changeDateType(queryForm.getUpTimeEnd()));
            CaseSessionHelper.setUpdatePaidMarkBJCase(dataList, request);

            log.debug("執行 批次處理 - 給付媒體回押註記作業 - 返回 UpdatePaidMarkBJDetailAction.doBack() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_BACK);

        }
        catch (Exception e) {
            log.error("UpdatePaidMarkBJDetailAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }
}
