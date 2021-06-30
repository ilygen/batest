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
import tw.gov.bli.ba.domain.Baban;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportDataCase;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportMaintCase;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportMaintModifyForm;
import tw.gov.bli.ba.executive.helper.CaseSessionHelper;
import tw.gov.bli.ba.executive.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.ExecutiveService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class ExecutiveSupportMaintModifyAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ExecutiveSupportMaintModifyAction.class);

    private ExecutiveService executiveService;

    /**
     * 行政支援作業 - 行政支援記錄修改 - 修改頁面 (basu0d013c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援記錄修改 - 修改頁面 ExecutiveSupportMaintModifyAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ExecutiveSupportMaintModifyForm detailForm = (ExecutiveSupportMaintModifyForm) form;

        try {
            ExecutiveSupportMaintCase caseData = CaseSessionHelper.getExecutiveSupportMaintCase(request);
            // 取得修改前的資料
            ExecutiveSupportDataCase detailData = CaseSessionHelper.getExecutiveSupportMaintDetailCase(request);
            
            // 複製修改後的資料
            Maadmrec maadmrec = new Maadmrec();
            BeanUtility.copyProperties(maadmrec, detailForm);
            maadmrec.setSlrpNo(detailForm.getSlrpNo1()+detailForm.getSlrpNo2()+detailForm.getSlrpNo3()+detailForm.getSlrpNo4()+detailForm.getSlrpNo5());
            maadmrec.setSlrelate(detailForm.getSlrelate1()+detailForm.getSlrelate2()+detailForm.getSlrelate3()+detailForm.getSlrelate4()+detailForm.getSlrelate5());
            maadmrec.setCloseNo(detailForm.getCloseNo1()+detailForm.getCloseNo2()+detailForm.getCloseNo3()+detailForm.getCloseNo4()+detailForm.getCloseNo5());
            Baban baban = new Baban();

            if(detailData.getPayMk() == null || !detailForm.getPayMk().equals(detailData.getPayMk())){
                baban.setPayMk(detailForm.getPayMk());
                if("A".equals(detailForm.getPayMk())){
                    baban.setAdvDte(DateUtility.getNowWestDate());
                }else{
                    baban.setAdvDte("");
                }
            }
            String payMk = detailForm.getPayMk();
            
            // 刪除舊資料
            //executiveService.deleteMaadmrec(detailData);
            // 新增資料
            //executiveService.insertMaadmrec(maadmrec, caseData, userData);
            // 更新資料
            executiveService.updateMaadmrec(maadmrec, detailData, userData, caseData, baban);
            
            // 查詢明細資料
            List<ExecutiveSupportDataCase> detailList = executiveService.getExecutiveSupportMaintListBy(caseData.getApNo(), DateUtility.changeChineseYearMonthType(caseData.getIssuYm()));
            CaseSessionHelper.setExecutiveSupportMaintListCase(detailList, request);
            
            // 清除Session資料
            FormSessionHelper.removeExecutiveSupportMaintModifyForm(request);
            CaseSessionHelper.removeExecutiveSupportMaintDetailCase(request);
            
            // 設定存檔成功訊息
            saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            log.debug("執行 行政支援作業 - 行政支援記錄修改 - 修改頁面 ExecutiveSupportMaintModifyAction.doSave() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportMaintModifyAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 行政支援作業 - 行政支援記錄修改 - 修改頁面 (basu0d013c.jsp) <br>
     * 按下「刪除」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援記錄修改 - 修改頁面 ExecutiveSupportMaintModifyAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            ExecutiveSupportMaintCase caseData = CaseSessionHelper.getExecutiveSupportMaintCase(request);
            
            // 取得修改前的資料
            ExecutiveSupportDataCase detailData = CaseSessionHelper.getExecutiveSupportMaintDetailCase(request);
            // 刪除資料
            if("17".equals(detailData.getLetterType())){
                // 複製修改後的資料
                   Maadmrec maadmrec = new Maadmrec();
                   BeanUtility.copyProperties(maadmrec, detailData);
                   maadmrec.setDelMk("D");
                   
                   executiveService.updateMaadmrec(maadmrec, detailData, userData, caseData, null);
                          
                   
               }else{
                   executiveService.deleteMaadmrec(detailData);
               }

            // 查詢明細資料
            List<ExecutiveSupportDataCase> detailList = executiveService.getExecutiveSupportMaintListBy(detailData.getApNo(), DateUtility.changeChineseYearMonthType(detailData.getIssuYm()));
            CaseSessionHelper.setExecutiveSupportMaintListCase(detailList, request);
            
            // 清除Session資料
            FormSessionHelper.removeExecutiveSupportMaintModifyForm(request);
            CaseSessionHelper.removeExecutiveSupportMaintDetailCase(request);
            
            // 設定刪除成功訊息
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());
            log.debug("執行 行政支援作業 - 行政支援記錄修改 - 修改頁面 ExecutiveSupportMaintModifyAction.doDelete() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);
        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("ExecutiveSupportMaintModifyAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 行政支援作業 - 行政支援記錄修改 - 修改頁面 (basu0d013c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援記錄修改 - 修改頁面 ExecutiveSupportMaintModifyAction.doBack() 開始 ... ");

        HttpSession session = request.getSession();

        try {
            ExecutiveSupportMaintCase caseData = CaseSessionHelper.getExecutiveSupportMaintCase(request);
            
            // 查詢明細資料
            List<ExecutiveSupportDataCase> detailList = executiveService.getExecutiveSupportMaintListBy(caseData.getApNo(), DateUtility.changeChineseYearMonthType(caseData.getIssuYm()));
            CaseSessionHelper.setExecutiveSupportMaintListCase(detailList, request);
            
            // 清除Session資料
            FormSessionHelper.removeExecutiveSupportMaintModifyForm(request);
            CaseSessionHelper.removeExecutiveSupportMaintDetailCase(request);
            log.debug("執行 行政支援作業 - 行政支援記錄修改 - 修改頁面 ExecutiveSupportMaintModifyAction.doBack() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportMaintModifyAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setExecutiveService(ExecutiveService executiveService) {
        this.executiveService = executiveService;
    }
}
