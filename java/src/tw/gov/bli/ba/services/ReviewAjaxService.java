package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewCase;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewExtCase;
import tw.gov.bli.ba.review.helper.CaseSessionHelper;

/**
 * Ajax Service for 審核作業
 * 
 * @author Rickychi
 */
public class ReviewAjaxService {
    private static Log log = LogFactory.getLog(ReviewAjaxService.class);
    private ReviewService reviewService;

    /**
     * 依傳入條件取得 篩選核定資料 for 遺屬年金給付審核
     * 
     * @param payYm 給付年月
     * @param seqNo 受款人序號
     * @param acceptMk 電腦審核結果
     * @return 內含<code>SurvivorPaymentReviewCase</code>物件的List
     */
    public SurvivorPaymentReviewCase[] flashIssuDataListForSurvivor(String payYm, String seqNo, String acceptMk) {
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest request = ctx.getHttpServletRequest();
        Map<BigDecimal, SurvivorPaymentReviewExtCase> issuDataMap = CaseSessionHelper.getSurvivorBenIssuDataMap(request);

        // 先根據頁面下拉選單選取之條件篩選資料
        // [
        // 建立資料Map
        // Key為payYm+seqNo+acceptMk, Value則是同為該key下的所有核定資料
        String mapKey = "";
        if (!("ALL").equals(payYm)) {
            mapKey += payYm + "-";
        }
        if (!("ALL").equals(seqNo)) {
            mapKey += seqNo + "-";
        }
        if (!("ALL").equals(acceptMk)) {
            mapKey += acceptMk + "-";
        }

        Map<String, List<SurvivorPaymentReviewExtCase>> map = new TreeMap<String, List<SurvivorPaymentReviewExtCase>>();
        map.put(mapKey, new ArrayList<SurvivorPaymentReviewExtCase>());

        for (Iterator it = issuDataMap.keySet().iterator(); it.hasNext();) {
            BigDecimal key = (BigDecimal) it.next();
            SurvivorPaymentReviewExtCase caseObj = issuDataMap.get(key);

            String dataKey = "";
            if (!("ALL").equals(payYm)) {
                dataKey += caseObj.getPayYm() + "-";
            }
            if (!("ALL").equals(seqNo)) {
                dataKey += caseObj.getSeqNo() + "-";
            }
            if (!("ALL").equals(acceptMk)) {
                if (StringUtils.isNotBlank(caseObj.getAcceptMk())) {
                    dataKey += caseObj.getAcceptMk() + "-";
                }
                else {
                    dataKey += " " + "-";
                }
            }
            // 篩選資料
            if (mapKey.equals(dataKey)) {
                (map.get(dataKey)).add(caseObj);
            }
        }
        // ]

        // 將篩選好的資料根據payYm分類
        // [
        List<SurvivorPaymentReviewExtCase> selectedIsuDataList = map.get(mapKey);
        List<SurvivorPaymentReviewCase> returnList = new ArrayList<SurvivorPaymentReviewCase>();

        // 建立資料Map
        // payYmMap的Key為payYm, Value則是同為該payYm下的所有核定資料
        Map<String, List<SurvivorPaymentReviewExtCase>> payYmMap = new TreeMap<String, List<SurvivorPaymentReviewExtCase>>();
        Map<BigDecimal, SurvivorPaymentReviewExtCase> idMap = new TreeMap<BigDecimal, SurvivorPaymentReviewExtCase>();
        for (int i = 0; i < selectedIsuDataList.size(); i++) {
            SurvivorPaymentReviewExtCase obj = selectedIsuDataList.get(i);
            payYmMap.put(obj.getPayYm(), new ArrayList<SurvivorPaymentReviewExtCase>());
        }

        for (int i = 0; i < selectedIsuDataList.size(); i++) {
            SurvivorPaymentReviewExtCase obj = selectedIsuDataList.get(i);
            (payYmMap.get(obj.getPayYm())).add(obj);
        }

        // 將分類好的payYmMap轉為list
        for (Iterator it = payYmMap.keySet().iterator(); it.hasNext();) {
            String payYmMapKey = (String) it.next();
            List<SurvivorPaymentReviewExtCase> tempList = payYmMap.get(payYmMapKey);
            SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
            caseObj.setPayYm(payYmMapKey);
            caseObj.setBenIssueDataArray(tempList.toArray(new SurvivorPaymentReviewExtCase[tempList.size()]));
            returnList.add(caseObj);
        }
        return (SurvivorPaymentReviewCase[]) returnList.toArray(new SurvivorPaymentReviewCase[returnList.size()]);
        // ]
    }

    /**
     * 將核定資料存入session for 遺屬年金給付審核 (單筆)
     * 
     * @param badaprId 核定檔資料列編號
     * @param manchkMk 人工審核結果
     */
    public void saveSingleIssuDataForSurvivor(BigDecimal badaprId, String manchkMk) {
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest request = ctx.getHttpServletRequest();
        Map<BigDecimal, SurvivorPaymentReviewExtCase> issuDataMap = CaseSessionHelper.getSurvivorBenIssuDataMap(request);

        SurvivorPaymentReviewExtCase caseObj = issuDataMap.get(badaprId);
        caseObj.setManchkMk(manchkMk);
        issuDataMap.put(badaprId, caseObj);

        CaseSessionHelper.setSurvivorBenIssuDataMap(issuDataMap, request);
    }

    /**
     * 將核定資料存入session for 遺屬年金給付審核 (多筆)
     * 
     * @param badaprId 核定檔資料列編號
     * @param manchkMk 人工審核結果
     */
    public SurvivorPaymentReviewCase[] saveIssuDataByUpdKindForSurvivor(String updKind, String manchkMk, String benChgManchkMk, String payYmOption, String seqNoOption, String manchkMkOption) {
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest request = ctx.getHttpServletRequest();
        // 明細資料
        SurvivorPaymentReviewCase detailData = CaseSessionHelper.getSurvivorPaymentReviewCase(request);
        // 事故者/受款人資料
        List<SurvivorPaymentReviewCase> beneficiaryDataList = CaseSessionHelper.getSurvivorBeneficiaryDataList(request);
        // 核定資料Map
        Map<BigDecimal, SurvivorPaymentReviewExtCase> issuDataMap = CaseSessionHelper.getSurvivorBenIssuDataMap(request);

        // 全部更新
        if (("ALL").equals(updKind)) {
            String[] tmpBenManchkMk = benChgManchkMk.split(",");
            Map<String, String> benChgManchkMkMap = new TreeMap<String, String>();
            for (int i = 0; i < tmpBenManchkMk.length; i++) {
                String[] chgManchkMk = tmpBenManchkMk[i].split(";");
                if (chgManchkMk.length == 2) {
                    benChgManchkMkMap.put(chgManchkMk[0], chgManchkMk[1]);
                }
            }

            // 更新事故者/受款人資料
            for (int i = 0; i < beneficiaryDataList.size(); i++) {
                SurvivorPaymentReviewCase caseObj = beneficiaryDataList.get(i);
                if (("0000").equals(caseObj.getSeqNo())) {
                    // 設定人工審核radio button狀態
                    caseObj.setManchkMk(manchkMk);
                    caseObj = reviewService.setEvtDataManchkMkCtl(caseObj, detailData.getQ1(), detailData.getQ2());
                    beneficiaryDataList.set(i, caseObj);
                }

                if (!("0000").equals(caseObj.getSeqNo())) {
                    // 事故者資料
                    SurvivorPaymentReviewCase evtCaseObj = beneficiaryDataList.get(0);
                    // 設定人工審核radio button狀態
                    caseObj = reviewService.setBenDataManchkMkCtl(evtCaseObj, caseObj, caseObj.getManchkMk(), benChgManchkMkMap);

                    beneficiaryDataList.set(i, caseObj);
                }
            }

            // 將受款人表頭資料轉Map
            Map<String, SurvivorPaymentReviewCase> beneficiaryDataMap = new TreeMap<String, SurvivorPaymentReviewCase>();
            if (beneficiaryDataList != null) {
                for (int i = 0; i < beneficiaryDataList.size(); i++) {
                    SurvivorPaymentReviewCase caseObj = beneficiaryDataList.get(i);
                    beneficiaryDataMap.put(caseObj.getSeqNo(), caseObj);
                }
            }

            // 更新 受款人核定資料
            for (Iterator it = issuDataMap.keySet().iterator(); it.hasNext();) {
                BigDecimal key = (BigDecimal) it.next();
                SurvivorPaymentReviewExtCase caseObj = issuDataMap.get(key);
                caseObj.setManchkMk(manchkMk);
                // 設定人工審核radio button狀態
                caseObj = reviewService.setBenIssuDataManchkMkCtl(beneficiaryDataMap, caseObj);

                issuDataMap.put(key, caseObj);
            }

            for (Iterator it = issuDataMap.keySet().iterator(); it.hasNext();) {
                BigDecimal key = (BigDecimal) it.next();
                SurvivorPaymentReviewExtCase caseObj = issuDataMap.get(key);

                System.out.println(caseObj.getPayYmStr() + "," + caseObj.getSeqNo() + "," + caseObj.getManchkMk() + "," + caseObj.getManchkMkCtlY() + "," + caseObj.getManchkMkCtlN() + "," + caseObj.getManchkMkCtlSpace());
            }
        }
        // 更新特定受款人之核定資料
        else {
            // 更新特定受款人資料
            for (int i = 0; i < beneficiaryDataList.size(); i++) {
                SurvivorPaymentReviewCase caseObj = beneficiaryDataList.get(i);
                if ((updKind).equals(caseObj.getSeqNo())) {
                    // // 事故者資料
                    // SurvivorPaymentReviewCase evtCaseObj = beneficiaryDataList.get(0);
                    // // 設定人工審核radio button狀態
                    // caseObj = reviewService.setBenDataManchkMkCtl(evtCaseObj, caseObj);

                    caseObj.setManchkMk(manchkMk);
                    beneficiaryDataList.set(i, caseObj);
                }
            }

            // 將受款人表頭資料轉Map
            Map<String, SurvivorPaymentReviewCase> beneficiaryDataMap = new TreeMap<String, SurvivorPaymentReviewCase>();
            if (beneficiaryDataList != null) {
                for (int i = 0; i < beneficiaryDataList.size(); i++) {
                    SurvivorPaymentReviewCase caseObj = beneficiaryDataList.get(i);
                    beneficiaryDataMap.put(caseObj.getSeqNo(), caseObj);
                }
            }

            for (Iterator it = issuDataMap.keySet().iterator(); it.hasNext();) {
                BigDecimal key = (BigDecimal) it.next();
                SurvivorPaymentReviewExtCase caseObj = issuDataMap.get(key);
                if ((updKind).equals(caseObj.getSeqNo())) {
                    // 更新選單狀態
                    // [
                    // 受款人資料
                    SurvivorPaymentReviewCase benCase = beneficiaryDataMap.get(caseObj.getSeqNo());

                    // 設定 受款人核定資料 人工審核radio button狀態
                    caseObj = reviewService.setBenIssuDataManchkMkCtl(beneficiaryDataMap, caseObj);
                    // ]

                    issuDataMap.put(key, caseObj);
                }
            }
        }

        // 存入sesion中
        CaseSessionHelper.setSurvivorBeneficiaryDataList(beneficiaryDataList, request);
        CaseSessionHelper.setSurvivorBenIssuDataMap(issuDataMap, request);

        // 更新資料
        return flashIssuDataListForSurvivor(payYmOption, seqNoOption, manchkMkOption);
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
}
