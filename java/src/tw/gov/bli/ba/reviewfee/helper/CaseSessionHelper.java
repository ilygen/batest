package tw.gov.bli.ba.reviewfee.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeDecisionCase;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeReceiptCase;
import tw.gov.bli.ba.reviewfee.cases.ReviewFeeReviewCase;

/**
 * Case Helper for 複檢費用
 * 
 * @author Goston
 */
public class CaseSessionHelper {
    private static Log log = LogFactory.getLog(CaseSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 複檢費用 - 複檢費用受理作業
    // ---------------------------------------------------------------------------------------
    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp) <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReviewFeeReceiptCase(ReviewFeeReceiptCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setReviewFeeReceiptCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REVIEW_FEE_RECEIPT_CASE, caseData);
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp) <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static ReviewFeeReceiptCase getReviewFeeReceiptCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReviewFeeReceiptCase() ...");

        HttpSession session = request.getSession();
        return (ReviewFeeReceiptCase) session.getAttribute(ConstantKey.REVIEW_FEE_RECEIPT_CASE);
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp) <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReviewFeeReceiptCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReviewFeeReceiptCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.REVIEW_FEE_RECEIPT_CASE);
    }

    // ---------------------------------------------------------------------------------------
    // 複檢費用 - 複檢費用審核作業
    // ---------------------------------------------------------------------------------------
    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp) <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReviewFeeReviewCase(ReviewFeeReviewCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setReviewFeeReviewCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REVIEW_FEE_REVIEW_CASE, caseData);
    }

    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp) <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static ReviewFeeReviewCase getReviewFeeReviewCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReviewFeeReviewCase() ...");

        HttpSession session = request.getSession();
        return (ReviewFeeReviewCase) session.getAttribute(ConstantKey.REVIEW_FEE_REVIEW_CASE);
    }

    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp) <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReviewFeeReviewCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReviewFeeReviewCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.REVIEW_FEE_REVIEW_CASE);
    }

    // ---------------------------------------------------------------------------------------
    // 複檢費用 - 複檢費用決行作業
    // ---------------------------------------------------------------------------------------
    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp bare0d031a.jsp bare0d032a.jsp) <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReviewFeeDecisionCase(ReviewFeeDecisionCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setReviewFeeDecisionCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REVIEW_FEE_DECISION_CASE, caseData);
    }

    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp bare0d031a.jsp bare0d032a.jsp) <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static ReviewFeeDecisionCase getReviewFeeDecisionCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReviewFeeDecisionCase() ...");

        HttpSession session = request.getSession();
        return (ReviewFeeDecisionCase) session.getAttribute(ConstantKey.REVIEW_FEE_DECISION_CASE);
    }

    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp bare0d031a.jsp bare0d032a.jsp) <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReviewFeeDecisionCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReviewFeeDecisionCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.REVIEW_FEE_DECISION_CASE);
    }
}
