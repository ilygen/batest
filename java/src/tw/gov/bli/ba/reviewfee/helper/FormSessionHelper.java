package tw.gov.bli.ba.reviewfee.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.reviewfee.forms.ReviewFeeDecisionForm;
import tw.gov.bli.ba.reviewfee.forms.ReviewFeeReceiptForm;
import tw.gov.bli.ba.reviewfee.forms.ReviewFeeReviewForm;

/**
 * Form Helper for 複檢費用
 * 
 * @author Goston
 */
public class FormSessionHelper {
    private static Log log = LogFactory.getLog(FormSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 複檢費用 - 複檢費用受理作業
    // ---------------------------------------------------------------------------------------
    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */
    public static ReviewFeeReceiptForm getReviewFeeReceiptForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getReviewFeeReceiptForm() ...");

        HttpSession session = request.getSession();
        return (ReviewFeeReceiptForm) session.getAttribute(ConstantKey.REVIEW_FEE_RECEIPT_FORM);
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param form
     * @param request
     */
    public static void setReviewFeeReceiptForm(ReviewFeeReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReviewFeeReceiptForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REVIEW_FEE_RECEIPT_FORM, form);
    }

    /**
     * 複檢費用 - 複檢費用受理作業 (bare0d010a.jsp bare0d011a.jsp bare0d012c.jsp bare0d013c.jsp) <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReviewFeeReceiptForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReviewFeeReceiptForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.REVIEW_FEE_RECEIPT_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 複檢費用 - 複檢費用審核作業
    // ---------------------------------------------------------------------------------------
    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */
    public static ReviewFeeReviewForm getReviewFeeReviewForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getReviewFeeReviewForm() ...");

        HttpSession session = request.getSession();
        return (ReviewFeeReviewForm) session.getAttribute(ConstantKey.REVIEW_FEE_REVIEW_FORM);
    }

    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param form
     * @param request
     */
    public static void setReviewFeeReviewForm(ReviewFeeReviewForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReviewFeeReviewForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REVIEW_FEE_REVIEW_FORM, form);
    }

    /**
     * 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp) <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReviewFeeReviewForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReviewFeeReviewForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.REVIEW_FEE_REVIEW_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 複檢費用 - 複檢費用決行作業
    // ---------------------------------------------------------------------------------------
    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp bare0d031a.jsp bare0d032a.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */
    public static ReviewFeeDecisionForm getReviewFeeDecisionForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getReviewFeeDecisionForm() ...");

        HttpSession session = request.getSession();
        return (ReviewFeeDecisionForm) session.getAttribute(ConstantKey.REVIEW_FEE_DECISION_FORM);
    }

    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp bare0d031a.jsp bare0d032a.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param form
     * @param request
     */
    public static void setReviewFeeDecisionForm(ReviewFeeDecisionForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReviewFeeDecisionForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REVIEW_FEE_DECISION_FORM, form);
    }

    /**
     * 複檢費用 - 複檢費用決行作業 (bare0d030a.jsp bare0d031a.jsp bare0d032a.jsp) <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReviewFeeDecisionForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReviewFeeDecisionForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.REVIEW_FEE_DECISION_FORM);
    }
}
