package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import tw.gov.bli.ba.decision.cases.SurvivorPaymentDecisionCase;
import tw.gov.bli.ba.decision.cases.SurvivorPaymentDecisionExtCase;
import tw.gov.bli.ba.decision.helper.CaseSessionHelper;

/**
 * Ajax Service for 決行作業
 * 
 * @author Rickychi
 */
public class DecisionAjaxService {
    private static Log log = LogFactory.getLog(DecisionAjaxService.class);

    /**
     * 依傳入條件取得 篩選核定資料 for 遺屬年金給付決行
     * 
     * @param payYm 給付年月
     * @param seqNo 受款人序號
     * @param acceptMk 電腦審核結果
     * @return 內含<code>SurvivorPaymentDecisionCase</code>物件的List
     */
    public SurvivorPaymentDecisionCase[] flashIssuDataListForSurvivor(String payYm, String seqNo, String acceptMk) {
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest request = ctx.getHttpServletRequest();
        Map<BigDecimal, SurvivorPaymentDecisionExtCase> issuDataMap = CaseSessionHelper.getSurvivorBenIssuDataMap(request);

        // 先根據頁面下拉選單選取之條件篩選資料
        // [
        // 建立資料Map
        // Key為payYm+seqNo+manchkMk, Value則是同為該key下的所有核定資料
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

        Map<String, List<SurvivorPaymentDecisionExtCase>> map = new TreeMap<String, List<SurvivorPaymentDecisionExtCase>>();
        map.put(mapKey, new ArrayList<SurvivorPaymentDecisionExtCase>());

        for (Iterator it = issuDataMap.keySet().iterator(); it.hasNext();) {
            BigDecimal key = (BigDecimal) it.next();
            SurvivorPaymentDecisionExtCase caseObj = issuDataMap.get(key);

            String dataKey = "";
            if (!("ALL").equals(payYm)) {
                dataKey += caseObj.getPayYm() + "-";
            }
            if (!("ALL").equals(seqNo)) {
                dataKey += caseObj.getSeqNo() + "-";
            }
            if (!("ALL").equals(acceptMk)) {
                dataKey += caseObj.getAcceptMk() + "-";
            }
            // 篩選資料
            if (mapKey.equals(dataKey)) {
                (map.get(dataKey)).add(caseObj);
            }
        }
        // ]

        // 將篩選好的資料根據payYm分類
        // [
        List<SurvivorPaymentDecisionExtCase> selectedIsuDataList = map.get(mapKey);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();

        // 建立資料Map
        // payYmMap的Key為payYm, Value則是同為該payYm下的所有核定資料
        Map<String, List<SurvivorPaymentDecisionExtCase>> payYmMap = new TreeMap<String, List<SurvivorPaymentDecisionExtCase>>();
        Map<BigDecimal, SurvivorPaymentDecisionExtCase> idMap = new TreeMap<BigDecimal, SurvivorPaymentDecisionExtCase>();
        for (int i = 0; i < selectedIsuDataList.size(); i++) {
            SurvivorPaymentDecisionExtCase obj = selectedIsuDataList.get(i);
            payYmMap.put(obj.getPayYm(), new ArrayList<SurvivorPaymentDecisionExtCase>());
        }

        for (int i = 0; i < selectedIsuDataList.size(); i++) {
            SurvivorPaymentDecisionExtCase obj = selectedIsuDataList.get(i);
            (payYmMap.get(obj.getPayYm())).add(obj);
        }

        // 將分類好的payYmMap轉為list
        for (Iterator it = payYmMap.keySet().iterator(); it.hasNext();) {
            String payYmMapKey = (String) it.next();
            List<SurvivorPaymentDecisionExtCase> tempList = payYmMap.get(payYmMapKey);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            caseObj.setPayYm(payYmMapKey);
            caseObj.setBenIssueDataArray(tempList.toArray(new SurvivorPaymentDecisionExtCase[tempList.size()]));
            returnList.add(caseObj);
        }
        return (SurvivorPaymentDecisionCase[]) returnList.toArray(new SurvivorPaymentDecisionCase[returnList.size()]);
        // ]
    }

}
