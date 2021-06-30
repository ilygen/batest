package tw.gov.bli.ba.decision.ajax;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.decision.cases.SurvivorPaymentDecisionCase;
import tw.gov.bli.ba.services.DecisionAjaxService;

/**
 * Ajax Service for 給付決行
 * 
 * @author Rickychi
 */
public class DecisionCommonAjax {
    private static Log log = LogFactory.getLog(DecisionCommonAjax.class);

    private DecisionAjaxService decisionAjaxService;

    /**
     * 依傳入條件取得 篩選核定資料 for 遺屬年金給付決行
     * 
     * @param payYm 給付年月
     * @param seqNo 受款人序號
     * @param acceptMk 電腦審核結果
     * @return 內含<code>SurvivorPaymentReviewCase</code>物件的List
     */
    public SurvivorPaymentDecisionCase[] flashIssuDataList(String payYm, String seqNo, String acceptMk) {
        log.debug("執行 DecisionCommonAjax.flashIssuDataList 開始 ...");

        return decisionAjaxService.flashIssuDataListForSurvivor(payYm, seqNo, acceptMk);
    }

    public void setDecisionAjaxService(DecisionAjaxService decisionAjaxService) {
        this.decisionAjaxService = decisionAjaxService;
    }
}
