package tw.gov.bli.ba.maint.actions;

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
import tw.gov.bli.ba.domain.Bacpirec;
import tw.gov.bli.ba.maint.cases.CpiDtlMaintCase;
import tw.gov.bli.ba.maint.cases.CpiRecMaintCase;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintQueryForm;
import tw.gov.bli.ba.maint.forms.CpiRecMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CpiRecMaintQueryForm;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 物價指數調整明細新增作業 - 查詢頁面 (bapa0x072a.jsp)
 *
 * @author Kiyomi
 */
public class CpiRecMaintDetailAction extends BaseDispatchAction {
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
        log.debug("執行 維護作業 - 物價指數調整紀錄新增作業 - 新增頁面 CpiRecMaintDetailAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CpiRecMaintDetailForm detailForm = (CpiRecMaintDetailForm) form;

        try {

            // 檢查是否有重複資料
            List<CpiRecMaintCase> checkList = maintService.getCpiRecMaintDetailCaseBy(detailForm.getIssuYear());

            if (checkList.size() > 0) {
                // 設定該筆資料已存在訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultToExistMessage());
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            if (detailForm != null) {
            	
            	// 取得新增資料
                List<CpiRecMaintCase> insertList = maintService.selectInsertDataForCpiRec(DateUtility.changeChineseYearType(detailForm.getIssuYear()));
                // 調整起月
                String adjYmB = DateUtility.changeChineseYearMonthType(detailForm.getAdjYmB());
              
                Bacpidtl updateIssuYearData = new Bacpidtl();                                
                                             
                updateIssuYearData.setIssuYear(DateUtility.changeChineseYearType(detailForm.getIssuYear()));
                updateIssuYearData.setAdjMk("Y");

                // 存檔
                maintService.saveCpiRecMaintData(insertList, updateIssuYearData, adjYmB, userData);
                //maintService.updateAdjYear(updateIssuYearData, userData);

                // 更新清單資料
                List<CpiRecMaintCase> applyList = maintService.getCpiRecMaintQueryCaseBy(null);
                CaseSessionHelper.setCpiRecMaintQueryCase(applyList, request);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 維護作業 - 物價指數調整紀錄新增作業 - 新增頁面  CpiRecMaintDetailAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);


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
        log.debug("執行維護作業 - 物價指數調整紀錄新增作業 - 修改頁面 CpiRecMaintDetailAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CpiRecMaintDetailForm detailForm = (CpiRecMaintDetailForm) form;
 
        try {
            if (detailForm != null) {
                Bacpirec updData = new Bacpirec();
                updData.setIssuYear(DateUtility.changeChineseYearType(detailForm.getIssuYear()));
                updData.setAdjYmB(DateUtility.changeChineseYearMonthType(detailForm.getAdjYmB()));
                
                // 存檔
                maintService.updateCpiRecMaintData(updData, userData);

                // 更新清單資料
                CpiRecMaintQueryForm queryForm = (CpiRecMaintQueryForm) FormSessionHelper.getCpiRecMaintQueryForm(request);
                List<CpiRecMaintCase> applyList = maintService.getCpiRecMaintQueryCaseBy(queryForm.getIssuYear());
                CaseSessionHelper.setCpiRecMaintQueryCase(applyList, request);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 維護作業 - 物價指數調整明細新增作業 - 修改頁面  CpiRecMaintDetailAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CpiRecMaintDetailAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }
}
