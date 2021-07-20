package tw.gov.bli.ba.reviewfee.actions;

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
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeReviewCase;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeReviewDetailCase;
import tw.gov.bli.ba.reviewfee.forms.ReviewFeeReviewForm;
import tw.gov.bli.ba.reviewfee.helper.CaseSessionHelper;
import tw.gov.bli.ba.services.ReviewFeeService;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp)
 * 
 * @author Goston
 */
public class ReviewFeeReviewAction extends DispatchAction {
    private static Log log = LogFactory.getLog(ReviewFeeReviewAction.class);

    private ReviewFeeService reviewFeeService;

    private static final String FORWARD_QUERY_PAGE = "queryPage";
    private static final String FORWARD_LIST_PAGE = "listPage";
    private static final String FORWARD_DETAIL_PAGE = "detailPage";

    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用審核作業 - 查詢頁面 ReviewFeeReviewAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeReviewForm queryForm = (ReviewFeeReviewForm) form;

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeReviewFeeReviewCase(request);

            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(queryForm.getApNo1())) + StringUtils.trimToEmpty(queryForm.getApNo2()) + StringUtils.trimToEmpty(queryForm.getApNo3()) + StringUtils.trimToEmpty(queryForm.getApNo4());

            if (StringUtils.length(apNo) != ConstantKey.APNO_LENGTH) { // 受理編號長度不滿 12 碼則不做查詢
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 取得案件資料
            ReviewFeeReviewCase caseData = reviewFeeService.getReviewFeeReviewData(apNo);

            // 無查詢資料
            if (caseData == null) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 將 CaseData 塞到 Session 中
            CaseSessionHelper.setReviewFeeReviewCase(caseData, request);

            log.debug("執行 複檢費用 - 複檢費用審核作業 - 查詢頁面 ReviewFeeReviewAction.doQuery() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReviewFeeReviewAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d021a.jsp) <br>
     * 按下「個案審核」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryDetailData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用審核作業 - List 頁面 ReviewFeeReviewAction.doQueryDetailData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeReviewForm listForm = (ReviewFeeReviewForm) form;

        try {
            // 自 Session 中取出 Case 資料
            ReviewFeeReviewCase caseData = CaseSessionHelper.getReviewFeeReviewCase(request);

            if (caseData == null) {
                // 設定查詢失敗訊息
                saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
                return mapping.findForward(FORWARD_QUERY_PAGE);
            }

            // 欲審核之明細資料重置
            caseData.setDetailData(null);

            // 自 Case 中取得欲審核的該筆資料
            ReviewFeeReviewDetailCase detailCaseData = caseData.getDetailDataByApSeq(listForm.getListApSeq());

            if (detailCaseData == null) {
                // 設定查詢失敗訊息
                saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
                return mapping.findForward(FORWARD_LIST_PAGE);
            }

            // 設定欲審核之明細資料
            caseData.setDetailData(detailCaseData);

            // Form 值重置
            listForm.clearFields();

            log.debug("執行 複檢費用 - 複檢費用審核作業 - List 頁面 ReviewFeeReviewAction.doQueryDetailData() 完成 ... ");

            return mapping.findForward(FORWARD_DETAIL_PAGE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReviewFeeReviewAction.doQueryDetailData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_LIST_PAGE);
        }
    }

    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d021a.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToQueryPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ReviewFeeReviewForm listForm = (ReviewFeeReviewForm) form;

        // 將 Case 清除
        CaseSessionHelper.removeReviewFeeReviewCase(request);

        // Form 值重置
        listForm.clearFields();

        return mapping.findForward(FORWARD_QUERY_PAGE);
    }

    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d022a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用審核作業 - Detail 頁面 ReviewFeeReviewAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeReviewForm detailForm = (ReviewFeeReviewForm) form;

        try {
            // 自 Session 中取出 Case 資料
            ReviewFeeReviewCase caseData = CaseSessionHelper.getReviewFeeReviewCase(request);

            if (caseData == null || caseData.getDetailData() == null || !StringUtils.equals(caseData.getDetailData().getReApNo(), detailForm.getReApNo()) || caseData.getDetailData().getApSeq().compareTo(detailForm.getApSeq()) != 0) {
                // 設定存檔失敗訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
                return mapping.findForward(FORWARD_QUERY_PAGE);
            }

            // 存檔
            reviewFeeService.updateReviewFeeReviewData(userData, caseData);

            // 設定存檔成功訊息
            saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());

            // 取得存檔後的案件資料
            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(detailForm.getApNo1())) + StringUtils.trimToEmpty(detailForm.getApNo2()) + StringUtils.trimToEmpty(detailForm.getApNo3()) + StringUtils.trimToEmpty(detailForm.getApNo4());
            caseData = reviewFeeService.getReviewFeeReviewData(apNo);
            
            // 更新 Session 中的 CaseData 資料
            CaseSessionHelper.setReviewFeeReviewCase(caseData, request);

            // Form 值重置
            detailForm.clearFields();

            log.debug("執行 複檢費用 - 複檢費用審核作業 - Detail 頁面 ReviewFeeReviewAction.doSave() 完成 ... ");

            if (caseData !=null && caseData.getDetailList() != null && caseData.getDetailList().size() > 0)
                return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
            else
                return mapping.findForward(FORWARD_QUERY_PAGE);
        }
        catch (Exception e) {
            // 設定存檔失敗訊息
            log.error("ReviewFeeReviewAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d022a.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToListPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ReviewFeeReviewForm detailForm = (ReviewFeeReviewForm) form;

        // 自 Session 中取出 Case 資料
        ReviewFeeReviewCase caseData = CaseSessionHelper.getReviewFeeReviewCase(request);

        // 欲審核之明細資料重置
        caseData.setDetailData(null);

        // Form 值重置
        detailForm.clearFields();

        return mapping.findForward(FORWARD_LIST_PAGE);
    }

    public void setReviewFeeService(ReviewFeeService reviewFeeService) {
        this.reviewFeeService = reviewFeeService;
    }

}
