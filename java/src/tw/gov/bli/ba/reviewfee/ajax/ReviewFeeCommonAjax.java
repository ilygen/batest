package tw.gov.bli.ba.reviewfee.ajax;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.domain.Bbcmf07;
import tw.gov.bli.ba.services.ReviewFeeAjaxService;

/**
 * AJAX for 複檢費用
 * 
 * @author Goston
 */
public class ReviewFeeCommonAjax {
    private static Log log = LogFactory.getLog(ReviewFeeCommonAjax.class);

    private ReviewFeeAjaxService reviewFeeAjaxService;

    /**
     * 依傳入的 醫療院所代碼 自 醫療院所參數檔 (<code>BBCMF07<code>) 取得 醫療院所名稱
     * 
     * @param hosId 醫院代碼
     * @return
     */
    public String getHosName(String hosId) {
        Bbcmf07 hosData = reviewFeeAjaxService.getHosDataBy(hosId);
        if (hosData != null)
            return hosData.getHpSnam();
        else
            return "";
    }

    public void setReviewFeeAjaxService(ReviewFeeAjaxService reviewFeeAjaxService) {
        this.reviewFeeAjaxService = reviewFeeAjaxService;
    }

}
