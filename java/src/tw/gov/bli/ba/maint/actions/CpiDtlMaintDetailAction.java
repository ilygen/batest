package tw.gov.bli.ba.maint.actions;

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
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.SystemMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.maint.cases.CpiDtlMaintCase;
import tw.gov.bli.ba.maint.cases.CpiRecMaintCase;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintQueryForm;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 物價指數調整明細新增作業 - 查詢頁面 (bapa0x062a.jsp)
 * 
 * @author Kiyomi
 */
public class CpiDtlMaintDetailAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(CpiDtlMaintDetailAction.class);

    private MaintService maintService;

    /**
     * 維護作業 - 物價指數調整明細新增作業 - 新增頁面 (bapa0x062a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 物價指數調整明細新增作業 - 新增頁面 CpiDtlMaintDetailAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CpiDtlMaintDetailForm detailForm = (CpiDtlMaintDetailForm) form;

        try {
            // 檢查輸入的年度必需小於等於系統年
        	String nowYear = userData.getLoginDate().substring(0,3);
        	
            if (Integer.parseInt(detailForm.getIssuYear()) > Integer.parseInt(nowYear)) {
                saveMessages(session, CustomMessageHelper.getOverYearFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);                
            }
            
            //指數年度必需小於核定年度
            if (Integer.parseInt(detailForm.getCpiYear()) >= Integer.parseInt(detailForm.getIssuYear()) || Integer.parseInt(detailForm.getCpiYearE()) >= Integer.parseInt(detailForm.getIssuYear())) {
                saveMessages(session, CustomMessageHelper.getOverAdjYearFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);                
            }
            
            //民國轉西元帶出重複資料
            String checkIssuYear  = DateUtility.changeChineseYearType(detailForm.getIssuYear());
            int checkAdjYearS  = Integer.parseInt(DateUtility.changeChineseYearType(detailForm.getAdjYearS()));
            int checkAdjYearE  = Integer.parseInt(DateUtility.changeChineseYearType(detailForm.getAdjYearE()));
            
            // 檢查是否有重複資料
            for(int i = checkAdjYearS ; i < checkAdjYearE+1 ; i++){

               Bacpidtl checkData = maintService.getCpiDtlMaintForCheckSaveCaseBy(checkIssuYear, String.valueOf(i));

               if (checkData != null) {
                  // 設定該筆資料已存在訊息
                  saveMessages(session, CustomMessageHelper.getCpiDtlSaveErrorMessage(detailForm.getIssuYear(), DateUtility.changeWestYearType(String.valueOf(i))));
                  return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
               }
            
            }
            
            List<CpiRecMaintCase> checkAdjMkList = maintService.selectInsertDataForCpiRec(checkIssuYear);
            
            if(checkAdjMkList.size() > 0){
               for(CpiRecMaintCase adjMkCase : checkAdjMkList){
            	  if(StringUtils.equals(adjMkCase.getAdjMk(), "Y")){
            		  // 設定該筆資料已存在訊息
            		  saveMessages(session, CustomMessageHelper.getCheckAdjMkFailMessage());
                      return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            	  }
               }
            }
        
            if (detailForm != null) {
                Bacpidtl detailData = new Bacpidtl();
                BeanUtility.copyProperties(detailData, detailForm);

                // 變更民國年為西元年
                detailData.setIssuYear(DateUtility.changeChineseYearType(detailForm.getIssuYear()));
                detailData.setAdjYearS(DateUtility.changeChineseYearType(detailForm.getAdjYearS()));
                detailData.setAdjYearE(DateUtility.changeChineseYearType(detailForm.getAdjYearE()));
                detailData.setCpiYear(DateUtility.changeChineseYearType(detailForm.getCpiYear()));
                detailData.setCpiYearE(DateUtility.changeChineseYearType(detailForm.getCpiYearE()));
                
                BigDecimal newSeqNo = maintService.selectSeqNoForCpiDtl(detailData);
                
                if(newSeqNo == null){
                	newSeqNo = BigDecimal.ONE;
                }

                // 存檔
                maintService.saveCpiDtlMaintData(detailData, userData, newSeqNo); 
                
                List<CpiDtlMaintCase> adjYearDataList = maintService.selectAdjYearDataBy(detailData);
                
                CaseSessionHelper.setAdjYearAmountCaseList(adjYearDataList, request);                    
                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 維護作業 - 物價指數調整明細新增作業 - 新增頁面  CpiDtlMaintDetailAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SUCCESS);            
                                                          
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CpiDtlMaintDetailAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 維護作業 - 物價指數調整明細新增作業 - 修改頁面 (bapa0x063c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行維護作業 - 物價指數調整明細新增作業 - 修改頁面 CpiDtlMaintDetailAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CpiDtlMaintDetailForm detailForm = (CpiDtlMaintDetailForm) form;
        
        //指數年度必需小於核定年度
        if (Integer.parseInt(detailForm.getCpiYear()) >= Integer.parseInt(detailForm.getIssuYear()) || Integer.parseInt(detailForm.getCpiYearE()) >= Integer.parseInt(detailForm.getIssuYear())) {
            saveMessages(session, CustomMessageHelper.getOverAdjYearFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);                
        }

        try {
            if (detailForm != null) {

            	// 取得欲刪除之申請年度資料 old
            	CpiDtlMaintCase oldData = CaseSessionHelper.getOldAdjYearCase(request);
                // 取得欲刪除之申請年度資料 old

                // 取得欲刪除之申請年度資料 new
                CpiDtlMaintCase newData = new CpiDtlMaintCase();
                BeanUtility.copyProperties(newData, detailForm);
                // 取得欲刪除之申請年度資料 new
                
                // 存檔
                maintService.updateCpiDtlMaintData(newData, oldData, userData);

                // 更新清單資料
                List<CpiDtlMaintCase> applyListRe = maintService.getCpiDtlMaintQueryCaseBy(null);
                CaseSessionHelper.setCpiDtlMaintQueryCase(applyListRe, request);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 維護作業 - 物價指數調整明細新增作業 - 修改頁面  CpiDtlMaintDetailAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CpiDtlMaintDetailAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }
    
    /**
     * 維護作業 - 物價指數調整明細新增作業 - 新增頁面 (bapa0x062a.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 物價指數調整明細新增作業 - 新增頁面 CpiDtlMaintDetailAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();

        CpiDtlMaintDetailForm detailForm = (CpiDtlMaintDetailForm) form;

        try {
                                   
            // 更新清單資料
            List<CpiDtlMaintCase> applyList = maintService.getCpiDtlMaintQueryCaseBy(null);
            CaseSessionHelper.setCpiDtlMaintQueryCase(applyList, request);
            // 清除明細資料
            FormSessionHelper.removeCpiDtlMaintDetailForm(request);

            log.debug("執行 維護作業 - 物價指數調整明細新增作業 - 新增頁面  CpiDtlMaintDetailAction.doBack() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);                                                       
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CpiDtlMaintDetailAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }
}
