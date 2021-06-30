package tw.gov.bli.ba.reviewfee.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeDecisionCase;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeDecisionDetailCase;
import tw.gov.bli.ba.reviewfee.forms.ReviewFeeDecisionForm;
import tw.gov.bli.ba.reviewfee.helper.CaseSessionHelper;
import tw.gov.bli.ba.services.ReviewFeeService;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp bare0d031a.jsp bare0d032a.jsp)
 * 
 * @author Goston
 */
public class ReviewFeeDecisionAction extends DispatchAction {
    private static Log log = LogFactory.getLog(ReviewFeeDecisionAction.class);

    private ReviewFeeService reviewFeeService;

    private static final String FORWARD_QUERY_PAGE = "queryPage";
    private static final String FORWARD_LIST_PAGE = "listPage";
    private static final String FORWARD_DETAIL_PAGE = "detailPage";

    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用決行作業 - 查詢頁面 ReviewFeeDecisionAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeDecisionForm queryForm = (ReviewFeeDecisionForm) form;

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeReviewFeeDecisionCase(request);

            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(queryForm.getApNo1())) + StringUtils.trimToEmpty(queryForm.getApNo2()) + StringUtils.trimToEmpty(queryForm.getApNo3()) + StringUtils.trimToEmpty(queryForm.getApNo4());

            if (StringUtils.length(apNo) != ConstantKey.APNO_LENGTH) { // 受理編號長度不滿 12 碼則不做查詢
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 取得案件資料
            ReviewFeeDecisionCase caseData = reviewFeeService.getReviewFeeDecisionData(apNo, userData);

            // 無查詢資料
            if (caseData == null) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 將 CaseData 塞到 Session 中
            CaseSessionHelper.setReviewFeeDecisionCase(caseData, request);

            log.debug("執行 複檢費用 - 複檢費用決行作業 - 查詢頁面 ReviewFeeDecisionAction.doQuery() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReviewFeeDecisionAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d031a.jsp) <br>
     * 按下「明細資料」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryDetailData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用決行作業 - List 頁面 ReviewFeeDecisionAction.doQueryDetailData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeDecisionForm listForm = (ReviewFeeDecisionForm) form;

        try {
            // 自 Session 中取出 Case 資料
            ReviewFeeDecisionCase caseData = CaseSessionHelper.getReviewFeeDecisionCase(request);

            if (caseData == null) {
                // 設定查詢失敗訊息
                saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
                return mapping.findForward(FORWARD_QUERY_PAGE);
            }

            // 欲查詢之明細資料重置
            caseData.setDetailData(null);

            // 自 Case 中取得欲審核的該筆資料
            ReviewFeeDecisionDetailCase detailCaseData = caseData.getDetailDataByApSeq(listForm.getListApSeq());

            if (detailCaseData == null) {
                // 設定查詢失敗訊息
                saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
                return mapping.findForward(FORWARD_LIST_PAGE);
            }

            // 設定欲查詢之明細資料
            caseData.setDetailData(detailCaseData);

            log.debug("執行 複檢費用 - 複檢費用決行作業 - List 頁面 ReviewFeeDecisionAction.doQueryDetailData() 完成 ... ");

            return mapping.findForward(FORWARD_DETAIL_PAGE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReviewFeeDecisionAction.doQueryDetailData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_LIST_PAGE);
        }
    }

    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d031a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 複檢費用 - 複檢費用決行作業 - List 頁面 ReviewFeeDecisionAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ReviewFeeDecisionForm listForm = (ReviewFeeDecisionForm) form;

        try {
            // 自 Session 中取出 Case 資料
            ReviewFeeDecisionCase caseData = CaseSessionHelper.getReviewFeeDecisionCase(request);

            if (caseData == null) {
                // 設定存檔失敗訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
                return mapping.findForward(FORWARD_QUERY_PAGE);
            }

            List<ReviewFeeDecisionDetailCase> detailCaseList = new ArrayList<ReviewFeeDecisionDetailCase>();

            // 可以執行 複核
            if (caseData.isShowReCheck()) {
                String[] reCheckList = listForm.getReCheckList();

                if (reCheckList != null) {
                    for (int i = 0; i < reCheckList.length; i++) {
                        ReviewFeeDecisionDetailCase detailCaseData = caseData.getDetailDataByApSeq(new BigDecimal(reCheckList[i]));
                        detailCaseData.setProcStat("30"); // 處理狀態 - 複核 = '30' 決行 = '40' 退件 = '11'

                        detailCaseList.add(detailCaseData);
                    }
                }
            }

            // 可以執行 決行
            if (caseData.isShowDecision()) {
                String[] decisionList = listForm.getDecisionList();

                if (decisionList != null) {
                    for (int i = 0; i < decisionList.length; i++) {
                        ReviewFeeDecisionDetailCase detailCaseData = caseData.getDetailDataByApSeq(new BigDecimal(decisionList[i]));
                        detailCaseData.setProcStat("40"); // 處理狀態 - 複核 = '30' 決行 = '40' 退件 = '11'

                        detailCaseList.add(detailCaseData);
                    }
                }
            }

            // 可以執行 退件
            if (caseData.isShowReturn()) {
                String[] returnList = listForm.getReturnList();

                if (returnList != null) {
                    for (int i = 0; i < returnList.length; i++) {
                        ReviewFeeDecisionDetailCase detailCaseData = caseData.getDetailDataByApSeq(new BigDecimal(returnList[i]));
                        detailCaseData.setProcStat("11"); // 處理狀態 - 複核 = '30' 決行 = '40' 退件 = '11'

                        detailCaseList.add(detailCaseData);
                    }
                }
            }

            // 存檔
            reviewFeeService.updateReviewFeeDecisionData(userData, detailCaseList);

            // 設定存檔成功訊息
            saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());

            // 取得存檔後的案件資料
            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(listForm.getApNo1())) + StringUtils.trimToEmpty(listForm.getApNo2()) + StringUtils.trimToEmpty(listForm.getApNo3()) + StringUtils.trimToEmpty(listForm.getApNo4());
            caseData = reviewFeeService.getReviewFeeDecisionData(apNo, userData);

            // 更新 Session 中的 CaseData 資料
            CaseSessionHelper.setReviewFeeDecisionCase(caseData, request);
            
            // Form 值重置
            listForm.clearFields();

            log.debug("執行 複檢費用 - 複檢費用決行作業 - List 頁面 ReviewFeeDecisionAction.doSave() 完成 ... ");

            if (caseData != null && caseData.getDetailList() != null && caseData.getDetailList().size() > 0)
                return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
            else
                return mapping.findForward(FORWARD_QUERY_PAGE);
        }
        catch (Exception e) {
            // 設定存檔失敗訊息
            log.error("ReviewFeeDecisionAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d031a.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToQueryPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ReviewFeeDecisionForm listForm = (ReviewFeeDecisionForm) form;

        // 將 Case 清除
        CaseSessionHelper.removeReviewFeeDecisionCase(request);

        // Form 值重置
        listForm.clearFields();
        
        return mapping.findForward(FORWARD_QUERY_PAGE);
    }

    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d032a.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToListPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ReviewFeeDecisionForm detailForm = (ReviewFeeDecisionForm) form;

        // 自 Session 中取出 Case 資料
        ReviewFeeDecisionCase caseData = CaseSessionHelper.getReviewFeeDecisionCase(request);

        // 欲查詢之明細資料重置
        caseData.setDetailData(null);

        return mapping.findForward(FORWARD_LIST_PAGE);
    }

    public void setReviewFeeService(ReviewFeeService reviewFeeService) {
        this.reviewFeeService = reviewFeeService;
    }

}
