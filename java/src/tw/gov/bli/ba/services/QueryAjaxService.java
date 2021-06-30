package tw.gov.bli.ba.services;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.Bccmf01Dao;
import tw.gov.bli.ba.dao.Bccmf42Dao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.domain.Bccmf42;
import tw.gov.bli.ba.domain.Nbdapr;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.query.cases.PaymentQueryIssuPayDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryIssuPayExtDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryMasterCase;
import tw.gov.bli.ba.query.cases.PaymentQueryNpIssuDataCase;
import tw.gov.bli.ba.query.helper.CaseSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;

/**
 * Ajax Service for 查詢作業
 * 
 * @author Rickychi
 */
public class QueryAjaxService {
    private static Log log = LogFactory.getLog(QueryAjaxService.class);

    private static final String FLASH_ISSU_DATA_LIST_COND_ISSUYM = "_flashIssuDataCondIssuYm";
    private static final String FLASH_ISSU_DATA_LIST_COND_PAYYM = "_flashIssuDataCondPayYm";
    private static final String FLASH_ISSU_DATA_LIST_COND_SEQNO = "_flashIssuDataCondSeqNo";
    private BaappbaseDao baappbaseDao;
    private Bccmf01Dao bccmf01Dao;
    private Bccmf42Dao bccmf42Dao;

    /**
     * 依傳入條件取得 篩選核定資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @param optionYm 篩選下拉選單年月
     * @param seqNo 受款人序號
     * @param qryCond 查詢條件
     * @return 內含<code>PaymentQueryIssuPayDataCase</code>物件的List
     */
    public PaymentQueryIssuPayDataCase[] flashIssuDataList(String apNo, String optionYm, String seqNo, String qryCond) {
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest request = ctx.getHttpServletRequest();
        List<Badapr> origIssuPayDataList = CaseSessionHelper.getPaymentQueryOrigIssuPayDataCaseList(request);
        List<PaymentQueryIssuPayDataCase> groupList = new ArrayList<PaymentQueryIssuPayDataCase>();

        String groupCond = "";
        String mapKey = "";

        // 先根據頁面下拉選單選取之條件篩選資料
        // [
        // 建立資料Map
        // Key為issuYm or payYm or seqNo, Value則是同為該key下的所有核定資料
        if (!StringUtils.equals("ALL", optionYm)) {
            mapKey = optionYm;
            if (StringUtils.equals("ISSUYM", qryCond)) {
                groupCond = FLASH_ISSU_DATA_LIST_COND_ISSUYM;
            }
            else if (StringUtils.equals("PAYYM", qryCond)) {
                groupCond = FLASH_ISSU_DATA_LIST_COND_PAYYM;
            }
        }
        else if (!StringUtils.equals("ALL", seqNo)) {
            groupCond = FLASH_ISSU_DATA_LIST_COND_SEQNO;
            mapKey += seqNo;
        }

        Map<String, List<PaymentQueryIssuPayExtDataCase>> selectedMap = new TreeMap<String, List<PaymentQueryIssuPayExtDataCase>>();
        selectedMap.put(mapKey, new ArrayList<PaymentQueryIssuPayExtDataCase>());

        for (int i = 0; i < origIssuPayDataList.size(); i++) {
            Badapr obj = origIssuPayDataList.get(i);

            String dataKey = "";
            if (StringUtils.equals(FLASH_ISSU_DATA_LIST_COND_ISSUYM, groupCond)) {
                dataKey = obj.getIssuYm();
            }
            else if (StringUtils.equals(FLASH_ISSU_DATA_LIST_COND_PAYYM, groupCond)) {
                dataKey = obj.getPayYm();
            }
            else if (StringUtils.equals(FLASH_ISSU_DATA_LIST_COND_SEQNO, groupCond)) {
                dataKey = obj.getSeqNo();
            }

            // 篩選資料
            if (mapKey.equals(dataKey)) {
                PaymentQueryIssuPayExtDataCase caseObj = new PaymentQueryIssuPayExtDataCase();
                BeanUtility.copyProperties(caseObj, obj);
                (selectedMap.get(dataKey)).add(caseObj);
            }
        }
        // ]

        // 將篩選好的資料根據payYm分類
        // [
        // 建立資料Map
        // qryCond = ISSUYM, Map Key = issuYm
        // qryCond = PAYYM, Map Key = payYm
        // Value則是同為該 issuYm／payYm 下的所有給付核定資料
        List<PaymentQueryIssuPayExtDataCase> selectedIsuDataList = selectedMap.get(mapKey);
        Map<String, List<PaymentQueryIssuPayExtDataCase>> map = new LinkedHashMap<String, List<PaymentQueryIssuPayExtDataCase>>();
        for (int i = 0; i < selectedIsuDataList.size(); i++) {
            PaymentQueryIssuPayExtDataCase caseObj = selectedIsuDataList.get(i);
            if (StringUtils.equals(qryCond, "ISSUYM")) {
                map.put(caseObj.getIssuYm(), new ArrayList<PaymentQueryIssuPayExtDataCase>());
            }
            else if (StringUtils.equals(qryCond, "PAYYM")) {
                map.put(caseObj.getPayYm(), new ArrayList<PaymentQueryIssuPayExtDataCase>());
            }
        }

        for (int i = 0; i < selectedIsuDataList.size(); i++) {
            PaymentQueryIssuPayExtDataCase caseObj = selectedIsuDataList.get(i);
            if (StringUtils.equals(qryCond, "ISSUYM")) {
                (map.get(caseObj.getIssuYm())).add(caseObj);
            }
            else if (StringUtils.equals(qryCond, "PAYYM")) {
                (map.get(caseObj.getPayYm())).add(caseObj);
            }
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            List<PaymentQueryIssuPayExtDataCase> subDataList = map.get(key);
            PaymentQueryIssuPayDataCase caseObj = new PaymentQueryIssuPayDataCase();
            List<PaymentQueryIssuPayExtDataCase> issuExtData = map.get(key);

            if (StringUtils.equals(qryCond, "ISSUYM")) {
                caseObj.setIssuYm(key);
            }
            else if (StringUtils.equals(qryCond, "PAYYM")) {
                caseObj.setPayYm(key);
            }

            // 建立子資料 SubMap
            // qryCond = ISSUYM, SubMap Key = payYm
            // qryCond = PAYYM, SubMap Key = issuYm
            // Value則是同為該 issuYm／payYm 下的所有給付核定資料
            // ------------------------------------------------------------------------
            Map<String, List<PaymentQueryIssuPayExtDataCase>> subMap = new LinkedHashMap<String, List<PaymentQueryIssuPayExtDataCase>>();
            for (int i = 0; i < subDataList.size(); i++) {
                PaymentQueryIssuPayExtDataCase obj = (PaymentQueryIssuPayExtDataCase) subDataList.get(i);
                if (StringUtils.equals(qryCond, "ISSUYM")) {
                    subMap.put(obj.getPayYm(), new ArrayList<PaymentQueryIssuPayExtDataCase>());
                }
                else if (StringUtils.equals(qryCond, "PAYYM")) {
                    subMap.put(obj.getIssuYm(), new ArrayList<PaymentQueryIssuPayExtDataCase>());
                }
            }

            for (int i = 0; i < subDataList.size(); i++) {
                PaymentQueryIssuPayExtDataCase subCaseObj = (PaymentQueryIssuPayExtDataCase) subDataList.get(i);
                if (StringUtils.equals(qryCond, "ISSUYM")) {
                    (subMap.get(subCaseObj.getPayYm())).add(subCaseObj);
                }
                else if (StringUtils.equals(qryCond, "PAYYM")) {
                    (subMap.get(subCaseObj.getIssuYm())).add(subCaseObj);
                }
            }

            // 將 分類好的 SubMap 轉為 list
            List<PaymentQueryIssuPayDataCase> subReturnList = new ArrayList<PaymentQueryIssuPayDataCase>();
            for (Iterator subIt = subMap.keySet().iterator(); subIt.hasNext();) {
                String subKey = (String) subIt.next();
                List<PaymentQueryIssuPayExtDataCase> tempList = subMap.get(subKey);
                PaymentQueryIssuPayDataCase subCaseObj = new PaymentQueryIssuPayDataCase();
                subCaseObj.setApNo(apNo);
                subCaseObj.setIssuYm(tempList.get(0).getIssuYm());
                subCaseObj.setPayYm(tempList.get(0).getPayYm());
                subCaseObj.setAplpayDate(tempList.get(0).getAplpayDate());
                subCaseObj.setStexpndDate(tempList.get(0).getStexpndDate());
                subCaseObj.setIssuPayExtDataArray(tempList.toArray(new PaymentQueryIssuPayExtDataCase[tempList.size()]));

                // 檢查受理清單PDF是否存在
                // 2009.10.05 修改路徑/nps/ap/ba_rpt/final_rpt/→/nps/ba_rpt/final_rpt/
                // String fileUrl = "/nps/ap/ba_rpt/final_rpt/" + caseObj.getApNo() + "_" + caseObj.getIssuYm() + "_F.pdf";
                String fileUrl = PropertyHelper.getProperty("rpt.path.final.rpt") + subCaseObj.getApNo() + "_" + subCaseObj.getIssuYm() + "_F.pdf";
                File file = new File(Encode.forJava(fileUrl));
                if (file.exists()) {
                    subCaseObj.setViewFlag("Y");
                }
                else {
                    subCaseObj.setViewFlag("N");
                }

                subReturnList.add(subCaseObj);
            }
            // ------------------------------------------------------------------------

            caseObj.setIssuPayDataArray(subReturnList.toArray(new PaymentQueryIssuPayDataCase[subReturnList.size()]));
            groupList.add(caseObj);
        }
        return (PaymentQueryIssuPayDataCase[]) groupList.toArray(new PaymentQueryIssuPayDataCase[groupList.size()]);
    }

    /**
     * 依傳入條件取得 篩選核定資料 for 失能年金給付查詢 - 國保核定資料
     * 
     * @param optionYm 篩選下拉選單年月
     * @param qryCond 查詢條件
     * @return 內含<code>PaymentQueryNpIssuDataCase</code>物件的List
     */
    public PaymentQueryNpIssuDataCase[] flashNpIssuDataList(String optionYm, String qryCond) {
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest request = ctx.getHttpServletRequest();
        List<Nbdapr> origNpIssuDataList = CaseSessionHelper.getPaymentQueryOrigNpIssuDataList(request);
        List<PaymentQueryNpIssuDataCase> issuDataList = new ArrayList<PaymentQueryNpIssuDataCase>();

        String groupCond = "";
        String mapKey = "";

        // 建立資料Map
        // qryCond = ISSUYM, Map Key = issuYm
        // qryCond = PAYYM, Map Key = payYm
        // Value則是同為該 issuYm／payYm 下的所有給付核定資料
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, List<PaymentQueryNpIssuDataCase>> map = new TreeMap<String, List<PaymentQueryNpIssuDataCase>>();
        for (int i = 0; i < origNpIssuDataList.size(); i++) {
            Nbdapr obj = (Nbdapr) origNpIssuDataList.get(i);
            if (StringUtils.equals(optionYm, "ALL")) {
                if (StringUtils.equals(qryCond, "ISSUYM")) {
                    map.put(obj.getIssuYm(), new ArrayList<PaymentQueryNpIssuDataCase>());
                }
                else if (StringUtils.equals(qryCond, "PAYYM")) {
                    map.put(obj.getPayYm(), new ArrayList<PaymentQueryNpIssuDataCase>());
                }
            }
            else {
                map.put(optionYm, new ArrayList<PaymentQueryNpIssuDataCase>());
            }
        }

        for (int i = 0; i < origNpIssuDataList.size(); i++) {
            Nbdapr obj = (Nbdapr) origNpIssuDataList.get(i);
            PaymentQueryNpIssuDataCase caseObj = new PaymentQueryNpIssuDataCase();
            BeanUtility.copyProperties(caseObj, obj);

            if (StringUtils.equals(optionYm, "ALL")) {
                if (StringUtils.equals(qryCond, "ISSUYM")) {
                    (map.get(obj.getIssuYm())).add(caseObj);
                }
                else if (StringUtils.equals(qryCond, "PAYYM")) {
                    (map.get(obj.getPayYm())).add(caseObj);
                }
            }
            else {
                if (StringUtils.equals(qryCond, "ISSUYM") && StringUtils.equals(optionYm, obj.getIssuYm())) {
                    (map.get(obj.getIssuYm())).add(caseObj);
                }
                else if (StringUtils.equals(qryCond, "PAYYM") && StringUtils.equals(optionYm, obj.getPayYm())) {
                    (map.get(obj.getPayYm())).add(caseObj);
                }
            }
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            List<PaymentQueryNpIssuDataCase> tempList = map.get(key);
            PaymentQueryNpIssuDataCase caseObj = new PaymentQueryNpIssuDataCase();

            if (StringUtils.equals(qryCond, "ISSUYM")) {
                caseObj.setIssuYm(key);
            }
            else if (StringUtils.equals(qryCond, "PAYYM")) {
                caseObj.setPayYm(key);
            }
            caseObj.setIssuDataArray(tempList.toArray(new PaymentQueryNpIssuDataCase[tempList.size()]));
            issuDataList.add(caseObj);
        }
        return (PaymentQueryNpIssuDataCase[]) issuDataList.toArray(new PaymentQueryNpIssuDataCase[issuDataList.size()]);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 給付查詢MasterData
     * 
     * @param apNo 受理編號
     * @param benIdnNo 受款人身分證號
     * @param benName 受款人姓名
     * @param benBrDate 受款人出生日期
     * @param qryCond 查詢條件
     * @param startYm 查詢年月(起)
     * @param endYm 查詢年月(迄)
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<PaymentQueryMasterCase> selectPaymentQueryMasterData(String apNo, String benIdnNo, String benName, String benBrDate, String qryCond, String startYm, String endYm) {
        List<Baappbase> dataList = baappbaseDao.selectPaymentQueryMasterDataBy(apNo, benIdnNo, benName, benBrDate, qryCond, startYm, endYm);
        List<PaymentQueryMasterCase> caseList = new ArrayList<PaymentQueryMasterCase>();
        for (int i = 0; i < dataList.size(); i++) {
            Baappbase obj = dataList.get(i);
            PaymentQueryMasterCase caseObj = new PaymentQueryMasterCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseList.add(caseObj);
        }
        return caseList;
    }

    /**
     * 取得 保密資料檔(<code>BCCMF42</code>) 是否有保密資料
     * 
     * @param idnNo 身分證號
     * @return <code>data</code> String物件
     */
    public BigDecimal checkIdnNoExist(String idnNo) {

        return bccmf42Dao.checkIdnNoExist(idnNo);
    }

    /**
     * 取得 保密資料檔(<code>BCCMF42</code>) payTyp
     * 
     * @param idnNo 身分證號
     * @return <code>data</code> String物件
     */
    public Bccmf42 selectPayTypBy(String idnNo) {

        return bccmf42Dao.selectPayTypBy(idnNo);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 給付查詢保密資料
     * 
     * @param IdnNo 受款人身分證號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectIdnNoDataBy(String apNo) {

        List<Baappbase> dataList = baappbaseDao.selectIdnNoDataBy(apNo);

        return dataList;
    }

    /**
     * 取得 保密資料檔(<code>BCCMF01</code>) 是否可查看保密資料
     * 
     * @param idnNo 身分證號
     * @return <code>data</code> String物件
     */
    public BigDecimal selectBccmf01CheckCount(String prpNo) {

        return bccmf01Dao.selectBccmf01CheckCount(prpNo);
    }

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setBccmf42Dao(Bccmf42Dao bccmf42Dao) {
        this.bccmf42Dao = bccmf42Dao;
    }

    public void setBccmf01Dao(Bccmf01Dao bccmf01Dao) {
        this.bccmf01Dao = bccmf01Dao;
    }
}
