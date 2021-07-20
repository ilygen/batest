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
import org.apache.struts.actions.DispatchAction;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.maint.cases.CpiRecMaintCase;
import tw.gov.bli.ba.maint.forms.CpiRecMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CpiRecMaintListForm;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class CpiRecMaintListAction extends DispatchAction {
    private static Log log = LogFactory.getLog(CpiDtlMaintListAction.class);

    private MaintService maintService;

    private static final String FORWARD_INSERT_CPI_REC = "insertCpiRec"; // 物價指數調整紀錄新增作業頁面
    private static final String FORWARD_UPDATE_CPI_REC = "updateCpiRec"; // 物價指數調整紀錄修改作業頁面
    private static final String FORWARD_CPI_REC = "cpiRec";

    /**
     * 維護作業 - 物價指數調整紀錄維護作業 - 查詢頁面 (bapa0x070f.jsp) <br>
     * 按下「新增」的動作
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */

    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 物價指數調整紀錄維護作業 - 查詢頁面 CpiRecMaintListAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CpiRecMaintListForm queryForm = (CpiRecMaintListForm) form;

        try {

            // 取得調整年度明細，判斷 adjmk狀態
            List<CpiRecMaintCase> caseList = maintService.getCpiDtlMaintForCpiRecCaseBy();
            CpiRecMaintCase caseData = (caseList.size() > 0) ? caseList.get(0) : null;
            if (caseData != null) {
                if (caseData.getAdjMk().equals("Y")) {
                    // Bacpidtl的adjmk為Y回傳 此年度已做過調整，不可再進行調整作業。
                    saveMessages(session, CustomMessageHelper.getCheckAdjMkFailMessage());
                    log.debug("執行 維護作業 - 物價指數調整紀錄維護作業 - 查詢頁面 CpiRecMaintListAction.doInsert() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
            }
            else {
                // 無資料回傳 無當年度物價指數調整明細資料，不可做調整。
                saveMessages(session, CustomMessageHelper.getCheckCpiDltDataFailMessage());
                log.debug("執行 維護作業 - 物價指數調整紀錄維護作業 - 查詢頁面 CpiRecMaintListAction.doInsert() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            CpiRecMaintDetailForm detailForm = new CpiRecMaintDetailForm();

            // 取得前一筆資料，放入前一筆資料之調整年月至修改頁面隱藏欄位lastAdjYmB
            List<CpiRecMaintCase> applyList = CaseSessionHelper.getCpiRecMaintQueryCase(request);

            if (applyList.size() == 0) {
                detailForm.setLastAdjYmB("09801");
            }
            else {
                CpiRecMaintCase caseData1 = applyList.get(applyList.size() - 1);
                detailForm.setLastAdjYmB(caseData1.getAdjYmB());
            }

            // 將cpidlt當年度資料放入CpiRecMaintDetailForm
            detailForm.setIssuYear(caseData.getIssuYear());
            detailForm.setIssuRpno(caseData.getIssuRpno());
            detailForm.setIssuDesc(caseData.getIssuDesc());
            detailForm.setReqRpno(caseData.getReqRpno());

            // 將 detailForm 加入 Session 中
            FormSessionHelper.setCpiRecMaintDetailForm(detailForm, request);
            CaseSessionHelper.setCpiRecMaintDetailCaseList(caseList, request);

            return mapping.findForward(FORWARD_INSERT_CPI_REC);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CpiRecMaintListAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 物價指數調整紀錄查詢作業 - 查詢頁面 (bapa0x071c.jsp) <br>
     * 按下「修改」的動作
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 物價指數調整紀錄查詢作業 - 查詢頁面 CpiRecMaintListAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CpiRecMaintListForm queryForm = (CpiRecMaintListForm) form;

        try {
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<CpiRecMaintCase> applyList = CaseSessionHelper.getCpiRecMaintQueryCase(request);
            CpiRecMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);

            // 取得物價指數調整資料
            List<CpiRecMaintCase> caseList = maintService.getCpiRecMaintForUpdateCaseBy(DateUtility.changeChineseYearType(caseData.getIssuYear()));
            CpiRecMaintCase detailData = (caseList.size() > 0) ? caseList.get(0) : null;
            if (detailData != null) {
                // 將撈出資料轉為民國放入FORM
                detailData.setIssuYear(DateUtility.changeWestYearType(detailData.getIssuYear()));
                detailData.setAdjYmB(detailData.getAdjYmB());
                detailData.setDate(DateUtility.changeDateType(detailData.getDate()));
            }

            CpiRecMaintDetailForm updateForm = new CpiRecMaintDetailForm();
            // 取得前一筆資料，放入前一筆資料之調整年月至修改頁面隱藏欄位lastAdjYmB
            int beforeRowNum = Integer.parseInt(rowNum) - 2;
            if (beforeRowNum < 0) {
                updateForm.setLastAdjYmB("09801");
            }
            else {
                CpiRecMaintCase caseData1 = applyList.get(Integer.parseInt(rowNum) - 2);
                updateForm.setLastAdjYmB(caseData1.getAdjYmB());
            }

            BeanUtility.copyProperties(updateForm, detailData);
            FormSessionHelper.setCpiRecMaintDetailForm(updateForm, request);
            CaseSessionHelper.setCpiRecMaintDetailCaseList(caseList, request);

            return mapping.findForward(FORWARD_UPDATE_CPI_REC);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CpiRecMaintListAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 物價指數調整明細查詢作業 - 刪除作業
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 物價指數調整紀錄查詢作業 - 刪除作業 CpiRecMaintListAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CpiRecMaintListForm listForm = (CpiRecMaintListForm) form;

        try {

            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<CpiRecMaintCase> applyList = CaseSessionHelper.getCpiRecMaintQueryCase(request);
            CpiRecMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);

            // 取得物價指數調整資料
            List<CpiRecMaintCase> caseList = maintService.getCpiRecMaintForUpdateCaseBy(caseData.getIssuYear());
            CpiRecMaintCase detailData = (caseList.size() > 0) ? caseList.get(0) : null;

            Bacpidtl updateIssuYearData = new Bacpidtl();

            updateIssuYearData.setIssuYear(DateUtility.changeChineseYearType(caseData.getIssuYear()));
            updateIssuYearData.setAdjMk("");

            maintService.deleteBacpirecData(userData, caseData, updateIssuYearData);
            // maintService.updateAdjYear(updateIssuYearData, userData);

            // 更新資料清單
            List<CpiRecMaintCase> applyList1 = maintService.getCpiRecMaintQueryCaseBy(null);
            CaseSessionHelper.setCpiRecMaintQueryCase(applyList1, request);
            // 設定刪除成功訊息
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());
            log.debug("執行 維護作業 - 物價指數調整紀錄查詢作業  - 刪除作業 CpiRecMaintListAction.doDelete() 完成... ");
            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);

        }
        catch (Exception e) {
            // 設定刪除失敗訊息
            log.error("CpiRecMaintListAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 維護作業 - 物價指數調整紀錄查詢作業 (bapa0x071c.jsp) <br>
     * 按下「返回」的動作
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }

}
