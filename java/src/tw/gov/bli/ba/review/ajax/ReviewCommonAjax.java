package tw.gov.bli.ba.review.ajax;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewCase;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewExtCase;
import tw.gov.bli.ba.review.helper.CaseSessionHelper;
import tw.gov.bli.ba.services.ReviewAjaxService;
import tw.gov.bli.ba.services.SelectOptionService;

import tw.gov.bli.ba.domain.Maadmrec;

/**
 * Ajax Service for 給付審核
 * 
 * @author Rickychi
 */
public class ReviewCommonAjax {
    private static Log log = LogFactory.getLog(ReviewCommonAjax.class);

    private SelectOptionService selectOptionService;
    private ReviewAjaxService reviewAjaxService;

    /**
     * 依傳入條件 檢查核定格式<br>
     * 
     * @param apNo 受理編號
     * @param notifyForm 核定格式
     * @return
     */
    public String[] checkNotifyForm(String apNo) {
        log.debug("執行 ReviewCommonAjax.checkNotifyForm 開始 ...");
        List<String> resultList = selectOptionService.selectNotifyFormBy(apNo);
        String[] notifyForms = new String[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            notifyForms[i] = resultList.get(i);
        }
        log.debug("執行 ReviewCommonAjax.checkNotifyForm 結束 ...");
        return notifyForms;
    }

    /**
     * 依傳入條件 檢查核定格式<br>
     * 
     * @param payYm 給付年月
     * @param seqNo 受款人序號
     * @param acceptMk 電腦審核結果
     * @return 內含<code>SurvivorPaymentReviewCase</code>物件的List
     */
    public SurvivorPaymentReviewCase[] flashIssuDataList(String payYm, String seqNo, String acceptMk) {
        log.debug("執行 ReviewCommonAjax.flashIssuDataList 開始 ...");

        return reviewAjaxService.flashIssuDataListForSurvivor(payYm, seqNo, acceptMk);
    }

    /**
     * 將核定資料存入session for 遺屬年金給付審核 (單筆)
     * 
     * @param badaprId 核定檔資料列編號
     * @param manchkMk 人工審核結果
     */
    public void saveSingleIssuDataForSurvivor(BigDecimal badaprId, String manchkMk) {
        log.debug("執行 ReviewCommonAjax.saveSingleIssuDataForSurvivor 開始 ...");
        reviewAjaxService.saveSingleIssuDataForSurvivor(badaprId, manchkMk);
    }

    /**
     * 將核定資料存入session for 遺屬年金給付審核 (多筆)
     * 
     * @param updKind 更新種類 (傳入ALL全部更新, 傳入特定受款人seqNo則只更新該受款人核定資料)
     * @param manchkMk 人工審核結果
     * @param benChgManchkMk 受款人人工審核結果
     * @param payYmOption 給付年月下拉選單
     * @param seqNoOption 受款人下拉選單
     * @param manchkMkOption 人工審核結果下拉選單
     */
    public SurvivorPaymentReviewCase[] saveIssuDataByUpdKindForSurvivor(String updKind, String manchkMk, String benChgManchkMk, String payYmOption, String seqNoOption, String manchkMkOption) {
        log.debug("執行 ReviewCommonAjax.saveIssuDataByUpdKindForSurvivor 開始 ...");
        return reviewAjaxService.saveIssuDataByUpdKindForSurvivor(updKind, manchkMk, benChgManchkMk, payYmOption, seqNoOption, manchkMkOption);
    }
    
    /**
     * 依傳入條件 檢查行政支援記錄檔<br>
     * 
     * @param apNo 受理編號
     * @param 
     * @return 
     */
    public String checkMaadmrec(String apNo, String caseTyp) {
        log.debug("執行 ReviewCommonAjax.checkMaadmrec 開始 ...");
        List<Maadmrec> resultList = selectOptionService.selectMaadmrecBy(apNo);
        String message = "";                     
        
        if (resultList != null) {            
            for (int i = 0; i < resultList.size(); i++) {
                Maadmrec obj = resultList.get(i);
                
                if (obj.getSeqNo().equals("1")){
                    if ( (Integer.valueOf(obj.getNdomk1())>0 || Integer.valueOf(obj.getNdomk2())>0) && (caseTyp.equals("1") || caseTyp.equals("2") || caseTyp.equals("4"))){
                        message = "show";
                    }                                                            
                } else if (obj.getSeqNo().equals("2")){
                    if ( (Integer.valueOf(obj.getNdomk1()) == 0 && Integer.valueOf(obj.getNdomk2()) == 0) && caseTyp.equals("3") ){
                        message = "show";
                    }
                } else if (obj.getSeqNo().equals("3")){
                    if ( (Integer.valueOf(obj.getNdomk1()) == 0 && Integer.valueOf(obj.getNdomk2()) == 0) && caseTyp.equals("6") ){
                        message = "show";
                    }
                }                                                                                                         
            }             
        }

        log.debug("執行 ReviewCommonAjax.checkMaadmrec 結束 ...");
        return message;
    }    

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }

    public void setReviewAjaxService(ReviewAjaxService reviewAjaxService) {
        this.reviewAjaxService = reviewAjaxService;
    }
}
