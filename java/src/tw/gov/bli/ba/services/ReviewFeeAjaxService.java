package tw.gov.bli.ba.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.dao.Bbcmf07Dao;
import tw.gov.bli.ba.domain.Bbcmf07;

/**
 * Service for 複檢費用 AJAX
 * 
 * @author Goston
 */
public class ReviewFeeAjaxService {
    private static Log log = LogFactory.getLog(ReviewFeeAjaxService.class);

    private Bbcmf07Dao bbcmf07Dao;

    /**
     * 依傳入的 醫療院所代碼 自 醫療院所參數檔 (<code>BBCMF07<code>) 取得 醫療院所名稱
     * 
     * @param hosId 醫院代碼
     * @return
     */
    public Bbcmf07 getHosDataBy(String hosId) {
        return bbcmf07Dao.getReviewFeeReceiptHosDataBy(hosId);
    }

    public void setBbcmf07Dao(Bbcmf07Dao bbcmf07Dao) {
        this.bbcmf07Dao = bbcmf07Dao;
    }

}
