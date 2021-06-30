package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BaappexpandDao;
import tw.gov.bli.ba.dao.BaapplogDao;
import tw.gov.bli.ba.dao.BachkfileDao;
import tw.gov.bli.ba.dao.BadaprDao;
import tw.gov.bli.ba.dao.BaexalistDao;
import tw.gov.bli.ba.dao.Balp0d020Dao;
import tw.gov.bli.ba.dao.BanotifyDao;
import tw.gov.bli.ba.dao.BaoncepayDao;
import tw.gov.bli.ba.dao.BasenimaintDao;
import tw.gov.bli.ba.dao.Bbcmf07Dao;
import tw.gov.bli.ba.dao.BirefDao;
import tw.gov.bli.ba.dao.CiptDao;
import tw.gov.bli.ba.dao.MaadmrecDao;
import tw.gov.bli.ba.dao.NbappbaseDao;
import tw.gov.bli.ba.dao.PbbmsaDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.domain.Bachkfile;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.domain.Baexalist;
import tw.gov.bli.ba.domain.Balp0d020;
import tw.gov.bli.ba.domain.Banotify;
import tw.gov.bli.ba.domain.Baoncepay;
import tw.gov.bli.ba.domain.Basenimaint;
import tw.gov.bli.ba.domain.Biref;
import tw.gov.bli.ba.domain.Cipt;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.domain.Nbappbase;
import tw.gov.bli.ba.domain.Pbbmsa;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.review.cases.ChkFileCase;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewCase;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewExtCase;
import tw.gov.bli.ba.review.cases.PaymentReviewCase;
import tw.gov.bli.ba.review.cases.PaymentReviewExtCase;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewCase;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewExtCase;
import tw.gov.bli.ba.util.BaReportReplaceUtility;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.util.FrameworkLogUtil;

/**
 * Service for 審核作業
 * 
 * @author Rickychi
 */
public class ReviewService {
    private static Log log = LogFactory.getLog(ReviewService.class);

    private BaappbaseDao baappbaseDao;
    private BaexalistDao baexalistDao;
    private MaadmrecDao maadmrecDao;
    private BachkfileDao bachkfileDao;
    private BasenimaintDao basenimaintDao;
    private CiptDao ciptDao;
    private BadaprDao badaprDao;
    private BaoncepayDao baoncepayDao;
    private PbbmsaDao pbbmsaDao;
    private BirefDao birefDao;
    private NbappbaseDao nbappbaseDao;
    private BanotifyDao banotifyDao;
    private Balp0d020Dao balp0d020Dao;
    private BaappexpandDao baappexpandDao;
    private Bbcmf07Dao bbcmf07Dao;
    private BaapplogDao baapplogDao;

    // ------------------------------ 老年年金給付審核 ------------------------------
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 給付審核
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>PaymentReviewCase</code> 物件的 List
     */
    public List<PaymentReviewCase> selectReviewDataFromBaappbase(String apNo) {
        List<Baappbase> list = baappbaseDao.selectDataForReviewBy(apNo, "0000", new String[] { "10", "11", "12", "20" }, "in", new String[] { "45", "48" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);
        List<PaymentReviewCase> returnList = new ArrayList<PaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            PaymentReviewCase caseObj = new PaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);

            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 審核決行清單紀錄檔(<code>BAEXALIST</code>) 資料清單
     * 
     * @param rptDate 列印日期
     * @param pageNo 頁次
     * @return 內含 <code>PaymentReviewCase</code> 物件的 List
     */
    public List<PaymentReviewCase> selectReviewDataFromExalist(String rptDate, BigDecimal pageNo) {
        List<Baexalist> list = baexalistDao.selectReviewDataBy(rptDate, pageNo, ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);
        List<PaymentReviewCase> returnList = new ArrayList<PaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Baexalist obj = list.get(i);
            PaymentReviewCase caseObj = new PaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料 for 老年年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<PaymentReviewCase> getLetterTypeMk1ListForOldAge(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk1(apNo);
        List<PaymentReviewCase> returnList = new ArrayList<PaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            PaymentReviewCase caseObj = new PaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料 for 老年年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<PaymentReviewCase> getLetterTypeMk2ListForOldAge(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk2(apNo);
        List<PaymentReviewCase> returnList = new ArrayList<PaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            PaymentReviewCase caseObj = new PaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料 for 老年年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<PaymentReviewCase> getLetterTypeMk3ListForOldAge(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk3(apNo);
        List<PaymentReviewCase> returnList = new ArrayList<PaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            PaymentReviewCase caseObj = new PaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料 for 老年年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<PaymentReviewCase> getLetterTypeMk4ListForOldAge(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk4(apNo);
        List<PaymentReviewCase> returnList = new ArrayList<PaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            PaymentReviewCase caseObj = new PaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料 for 老年年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<PaymentReviewCase> getLetterTypeMk5ListForOldAge(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk5(apNo);
        List<PaymentReviewCase> returnList = new ArrayList<PaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            PaymentReviewCase caseObj = new PaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料 for 老年年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    public PaymentReviewCase getLetterTypeMk6ListForOldAge(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk6(apNo);
        PaymentReviewCase caseObj = new PaymentReviewCase();
        if (list.size() >= 1) {
            Maadmrec obj = list.get(0);
            BeanUtility.copyProperties(caseObj, obj);

            String kind = getLetterTypeMk6KindForOldAge(obj.getReliefKind());
            String stat = getLetterTypeMk6StatForOldAge(obj.getReliefKind(), obj.getReliefStat());
            caseObj.setReliefKind(kind);
            caseObj.setReliefStat(stat);
        }

        return caseObj;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 救濟種類資料 for 老年年金給付審核
     * 
     * @param reliefKind 救濟種類
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6KindForOldAge(String reliefKind) {

        return maadmrecDao.selectLetterTypeMk6Kind(reliefKind);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟辦理情形資料 for 老年年金給付審核
     * 
     * @param reliefKind 救濟種類
     * @param reliefStat 行政救濟辦理情形
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6StatForOldAge(String reliefKind, String reliefStat) {

        return maadmrecDao.selectLetterTypeMk6Stat(reliefKind, reliefStat);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審註記資料 for 老年年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>PaymentReviewExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<PaymentReviewExtCase> getOldAgePaymentReviewEvtChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewEvtChkListForOldAge(apNo);
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 issuYm+payYm
        // Value則是同為該 issuYm+payYm 下的所有編審註記資料
        Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
        }

        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            ChkFileCase caseObj = new ChkFileCase();
            BeanUtility.copyProperties(caseObj, obj);
            (map.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(caseObj);
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            List<ChkFileCase> tempList = map.get(key);
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            caseObj.setIssuPayYm(key);
            caseObj.setChkFileList(tempList);
            caseObj.setChkFileDataSize(tempList.size());
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 受款人編審註記資料 for 老年年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>PaymentReviewExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<PaymentReviewCase> getOldAgePaymentReviewBenChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewBenChkListForOldAge(apNo);
        List<PaymentReviewCase> returnList = new ArrayList<PaymentReviewCase>();
        List<PaymentReviewExtCase> subList = new ArrayList<PaymentReviewExtCase>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 seqNo
        // Value則是同為該 seqNo 下的所有編審註記資料
        Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            map.put(obj.getSeqNo(), new ArrayList<ChkFileCase>());
        }
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            ChkFileCase caseObj = new ChkFileCase();
            BeanUtility.copyProperties(caseObj, obj);
            (map.get(obj.getSeqNo())).add(caseObj);
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            subList = new ArrayList<PaymentReviewExtCase>();
            String key = (String) it.next();
            List<ChkFileCase> tempList = map.get(key);

            // 建立資料SubMap
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 issuYm+payYm 下的所有編審註記資料
            Map<String, List<ChkFileCase>> subMap = new TreeMap<String, List<ChkFileCase>>();
            for (int i = 0; i < tempList.size(); i++) {
                ChkFileCase obj = (ChkFileCase) tempList.get(i);
                subMap.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
            }

            for (int i = 0; i < tempList.size(); i++) {
                ChkFileCase obj = (ChkFileCase) tempList.get(i);
                (subMap.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(obj);
            }

            // 將 分類好的 SubMap 轉為 list
            List<ChkFileCase> subTempList = new ArrayList<ChkFileCase>();
            for (Iterator subIt = subMap.keySet().iterator(); subIt.hasNext();) {
                String subKey = (String) subIt.next();
                subTempList = subMap.get(subKey);
                PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
                caseObj.setIssuPayYm(subKey);
                caseObj.setChkFileList(subTempList);
                caseObj.setChkFileDataSize(subTempList.size());
                subList.add(caseObj);
            }
            if (subTempList.size() != 0) {
                PaymentReviewCase masterCase = new PaymentReviewCase();
                masterCase.setSeqNo(key);
                masterCase.setOtherChkDataList(subList);
                returnList.add(masterCase);
            }
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param chkTyp 編審註記種類 (A:事故者編審註記, B:眷屬編審註記, C:符合註記)
     * @return <code>PaymentReviewExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<PaymentReviewExtCase> getPaymentReviewChkList(String apNo, String seqNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewChkListBy(apNo, seqNo, "A", null);
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 issuYm+payYm
        // Value則是同為該 issuYm+payYm 下的所有編審註記資料
        Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
        }

        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            ChkFileCase caseObj = new ChkFileCase();
            BeanUtility.copyProperties(caseObj, obj);
            (map.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(caseObj);
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            List<ChkFileCase> tempList = map.get(key);
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            caseObj.setIssuPayYm(key);
            caseObj.setChkFileList(tempList);
            caseObj.setChkFileDataSize(tempList.size());
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人清單
     * 
     * @param apNo 受理編號
     * @param benPayMk 受益人領取狀態註記
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<PaymentReviewExtCase> getBenNameList(String apNo, String benPayMk) {
        List<String> list = baappbaseDao.selectBenNameBy(apNo, benPayMk);
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            caseObj.setBenName((String) list.get(i));
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 年資維護介接檔(<code>BASENIMAINT</code>) 欠費期間資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param seniTyp 處理狀態
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<PaymentReviewExtCase> getSenimaintData(String apNo, String seniTyp) {
        List<Basenimaint> list = basenimaintDao.selectDataBy(apNo, null, seniTyp);
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Basenimaint obj = list.get(i);
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 被保險人異動資料檔(<code>CIPT</code>) 承保異動資料
     * 
     * @param idn 身分證號
     * @param inTyp 保險別
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<PaymentReviewExtCase> getCiptData(String apNo, String idn, String inTyp) {
        List<Cipt> list = ciptDao.selectTxcdDataBy(apNo, "0000", idn, inTyp);
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Cipt obj = list.get(i);
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 核定資料
     * 
     * @param apNo 受理編號
     * @param issuym 核定年月
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<PaymentReviewExtCase> getPayData(String apNo, String issuYm) {
        List<Badapr> list = badaprDao.selectPayDataBy(apNo, issuYm);
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Badapr obj = list.get(i);
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseObj.setAplPayAmt(obj.getAplpayAmt());
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 一次給付資料 for 給付審核
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public PaymentReviewExtCase getOncePayData(String apNo, String issuYm) {
        List<Baappbase> baappbaseList = baappbaseDao.selectOncePayDataBy(apNo);
        List<Badapr> badaprList = badaprDao.selectOncePayDataBy(apNo, issuYm);
        List<Baoncepay> baoncepayList = baoncepayDao.selectOncePayDataBy(apNo);

        PaymentReviewExtCase returnCase = new PaymentReviewExtCase();

        if (baappbaseList.size() == 1) {
            Baappbase obj = baappbaseList.get(0);
            BeanUtility.copyProperties(returnCase, obj);
        }
        if (badaprList.size() == 1) {
            Badapr obj = badaprList.get(0);
            BeanUtility.copyProperties(returnCase, obj);
        }
        if (baoncepayList.size() == 1) {
            Baoncepay obj = baoncepayList.get(0);
            BeanUtility.copyProperties(returnCase, obj);
        }
        BigDecimal dabAnnuAmt = new BigDecimal(0);
        BigDecimal annuAmt = new BigDecimal(0);
        BigDecimal oncePayAmt = new BigDecimal(0);
        BigDecimal sumPayAmt = new BigDecimal(0);
        if (returnCase.getDabAnnuAmt() != null) {
            dabAnnuAmt = returnCase.getDabAnnuAmt();
        }
        if (returnCase.getAnnuAmt() != null) {
            annuAmt = returnCase.getAnnuAmt();
        }
        if (returnCase.getOncePayAmt() != null) {
            oncePayAmt = returnCase.getOncePayAmt();
        }
        if (returnCase.getSumPayAmt() != null) {
            sumPayAmt = returnCase.getSumPayAmt();
        }

        returnCase.setSumPayAmt(dabAnnuAmt.add(annuAmt));
        returnCase.setDiffAmt(oncePayAmt.subtract(sumPayAmt));

        return returnCase;
    }

    /**
     * 依傳入條件取得 年資維護介接檔(<code>BASENIMAINT</code>) 一次給付更正 for 給付審核
     * 
     * @param apNo 受理編號
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<PaymentReviewExtCase> getOncePayModifyData(String apNo) {
        List<Basenimaint> list = basenimaintDao.selectDataBy(apNo, null, "1");
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Basenimaint obj = list.get(i);
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            caseObj.setOwesBdate(obj.getOwesBdate());
            caseObj.setOwesEdate(obj.getOwesEdate());
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 核定通知書格式檔(<code>NBAPPBASE</code>) 核定通知書(稿)資料
     * 
     * @param apNo 受理編號
     * @param notifyForm 核定格式
     * @return <code>PaymentReviewExtCase</code> 物件
     */
    public PaymentReviewExtCase getNotifyData(PaymentReviewCase caseObj) {
        String apNo = caseObj.getApNo();
        String notifyForm = caseObj.getNotifyForm();
        Baappbase evtData = new Baappbase();
        BeanUtility.copyProperties(evtData, caseObj);

        PaymentReviewExtCase returnCase = null;

        // 取得 核定通知書格式檔 (BANOTIFY 核定通知書資料)
        // [
        // a. 若核定通知書格式為 999 或為 空白 及 NULL, 則整段核定通知書不顯示
        // b. 若查無受理案件核定通知書格式, 則整段核定通知書不顯示
        if (StringUtils.isNotBlank(notifyForm) && !StringUtils.equals(notifyForm, "999")) {
            returnCase = new PaymentReviewExtCase();
            returnCase.setNotifyForm(notifyForm);

            // 取得受文者(受益人)姓名清單
            List<String> benNameList = baappbaseDao.selectBenNameBy(apNo, "1");
            returnCase.setNotifyBenName(benNameList);

            BaReportReplaceUtility strReplace = new BaReportReplaceUtility(evtData, false, null, false, null);

            // 取得 核定通知書 - 主旨
            List<Banotify> subjectList = banotifyDao.getNotifyListForReviewBy(StringUtils.substring(apNo, 0, 1), evtData.getNotifyForm(), "0");
            if (subjectList.size() > 0) {
                returnCase.setSubject(strReplace.replace(StringUtils.defaultString(subjectList.get(0).getDataCont())));

                // 取得 核定通知書 - 說明
                List<Banotify> contentList = banotifyDao.getNotifyListForReviewBy(StringUtils.substring(apNo, 0, 1), evtData.getNotifyForm(), "1");
                List<String> contents = new ArrayList<String>();
                for (Banotify contentData : contentList) {
                    if (StringUtils.isNotBlank(contentData.getDataCont()))
                        contents.add(strReplace.replace(contentData.getDataCont()));
                }
                returnCase.setContent(contents);
            }
        }
        return returnCase;
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 請領同類／他類／另案扣減 - 一次給付資料
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return <code>PaymentReviewExtCase</code> 物件
     */
    public PaymentReviewExtCase getOncePayDetailData(String evtIdnNo, String evtBrDate) {
        List<Pbbmsa> list = pbbmsaDao.selectOncePayDataForPaymentBy(evtIdnNo, evtBrDate);
        PaymentReviewExtCase caseObj = null;
        if (list.size() == 1) {
            Pbbmsa obj = list.get(0);
            caseObj = new PaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
        }
        return caseObj;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 請領同類／他類／另案扣減 - 年金給付資料
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<PaymentReviewExtCase> getAnnuityPayData(String apNo, String evtIdnNo, String evtBrDate) {
        List<Baappbase> list = baappbaseDao.selectAnnuityPayDataBy(apNo, evtIdnNo, evtBrDate);
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 請領同類／他類／另案扣減 - 申請失能給付紀錄資料
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<PaymentReviewExtCase> getCriPayApplyData(String evtIdnNo, String evtBrDate) {
        List<Pbbmsa> list = pbbmsaDao.selectCriPayApplyDataForPaymentBy(evtIdnNo, evtBrDate);
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Pbbmsa obj = list.get(i);
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 請領同類／他類／另案扣減 - 申請死亡給付紀錄資料
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return <code>PaymentReviewExtCase</code> 物件
     */
    public PaymentReviewExtCase getDiePayApplyData(String evtIdnNo, String evtBrDate) {
        List<Pbbmsa> list = pbbmsaDao.selectDiePayApplyDataForPaymentBy(evtIdnNo, evtBrDate);
        PaymentReviewExtCase caseObj = null;
        if (list.size() == 1) {
            Pbbmsa obj = list.get(0);
            caseObj = new PaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
        }
        return caseObj;
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 請領同類／他類／另案扣減 - 申請傷病給付紀錄資料
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<PaymentReviewExtCase> getInjPayApplyData(String evtIdnNo, String evtBrDate) {
        List<Pbbmsa> list = pbbmsaDao.selectInjPayApplyDataForPaymentBy(evtIdnNo, evtBrDate);
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Pbbmsa obj = list.get(i);
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 請領同類／他類／另案扣減 - 申請失業給付紀錄資料
     * 
     * @param evtIdnNo 身分證號
     * @param evtBrDate 出生日期
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<PaymentReviewExtCase> getUnEmpPayApplyData(String evtIdnNo, String evtBrDate) {
        List<Biref> list = birefDao.selectUnEmpDataBy(evtIdnNo, evtBrDate);
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Biref obj = list.get(i);
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付紀錄資料
     * 
     * @param evtIds 事故者社福識別碼
     * @return <code>PaymentReviewExtCase</code> 物件
     */
    public PaymentReviewExtCase getNpPayApplyData(String evtIds) {
        List<Nbappbase> list = nbappbaseDao.selectNpPayDataBy(evtIds);
        PaymentReviewExtCase caseObj = null;
        if (list.size() == 1) {
            Nbappbase obj = list.get(0);
            caseObj = new PaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
        }
        return caseObj;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 審核管制條件
     * 
     * @param apNo 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase getReviewCond(String apNo) {
        return baappbaseDao.selectReviewCond(apNo, "0000");
    }

    // /**
    // * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者/受款人資料、受款人編審註記資料 FOR 給付審核
    // *
    // * @param apNo 受理編號
    // * @return 內含 <code>PaymentReviewCase</code> 物件的 List
    // */
    // public List<PaymentReviewCase> getAllBenData(String apNo) {
    // List<PaymentReviewCase> beneficiaryDataList = getBeneficiaryDataList(apNo);
    // List<PaymentReviewCase> returnList = new ArrayList<PaymentReviewCase>();
    //
    // for (int i = 0; i < beneficiaryDataList.size(); i++) {
    // PaymentReviewCase obj = beneficiaryDataList.get(i);
    // apNo = obj.getApNo();
    // String seqNo = obj.getSeqNo();
    //
    // PaymentReviewCase caseObj = new PaymentReviewCase();
    // BeanUtility.copyProperties(caseObj, obj);
    // // caseObj.setBenIssueDataList(getBenIssueDataList(apNo, seqNo));
    // caseObj.setBenChkDataList(getBenChkDataList(apNo, seqNo));
    // returnList.add(caseObj);
    // }
    // return returnList;
    // }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料 FOR 給付審核
     * 
     * @param apNo 受理編號
     * @return 內含 <code>PaymentReviewCase</code> 物件的 List
     */
    public List<PaymentReviewCase> getAllIssuData(String apNo) {
        List<PaymentReviewCase> beneficiaryDataList = getBeneficiaryDataList(apNo);
        List<PaymentReviewCase> returnList = new ArrayList<PaymentReviewCase>();

        for (int i = 0; i < beneficiaryDataList.size(); i++) {
            PaymentReviewCase obj = beneficiaryDataList.get(i);
            apNo = obj.getApNo();
            String seqNo = obj.getSeqNo();

            PaymentReviewCase caseObj = new PaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            List<PaymentReviewExtCase> benIssueDataList = getBenIssueDataList(apNo, seqNo);
            caseObj.setBenIssueDataList(benIssueDataList);
            // String showManchkMk = "Y";
            // String manchkMkValue = "";
            // // 以電腦編審結果來判斷是否顯示Radio
            // // 以人工編審結果來判斷 人工審核結果Radio值
            // // Y-合格
            // for (int j = 0; j < benIssueDataList.size(); j++) {
            // PaymentReviewExtCase benIssueData = benIssueDataList.get(j);
            // if (("Y").equals(benIssueData.getAcceptMk())) {
            // showManchkMk = "Y";
            // }
            // if (("Y").equals(benIssueData.getManchkMk())) {
            // manchkMkValue = "Y";
            // }
            // }
            // // S-待處理
            // for (int j = 0; j < benIssueDataList.size(); j++) {
            // PaymentReviewExtCase benIssueData = benIssueDataList.get(j);
            // if (!("N").equals(benIssueData.getAcceptMk()) && !("Y").equals(benIssueData.getAcceptMk()) && StringUtils.isBlank(StringUtils.trimToEmpty(benIssueData.getAcceptMk()))) {
            // showManchkMk = "S";
            // }
            // if (!("N").equals(benIssueData.getManchkMk()) && !("Y").equals(benIssueData.getManchkMk()) && StringUtils.isBlank(StringUtils.trimToEmpty(benIssueData.getManchkMk()))) {
            // manchkMkValue = " ";
            // }
            // }
            // // N-不合格
            // for (int j = 0; j < benIssueDataList.size(); j++) {
            // PaymentReviewExtCase benIssueData = benIssueDataList.get(j);
            // if (("N").equals(benIssueData.getAcceptMk())) {
            // showManchkMk = "N";
            // }
            // if (("N").equals(benIssueData.getManchkMk())) {
            // manchkMkValue = "N";
            // }
            // }
            //
            // caseObj.setShowManchkMk(showManchkMk);
            // caseObj.setManchkMkValue(manchkMkValue);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料 for getAllBenData()/getAllIssuData()
     * 
     * @param apNo 受理編號
     * @return 內含 <code>PaymentReviewCase</code> 物件的 List
     */
    public List<PaymentReviewCase> getBeneficiaryDataList(String apNo) {
        List<Baappbase> list = baappbaseDao.selectBeneficiaryDataBy(apNo);
        List<PaymentReviewCase> returnList = new ArrayList<PaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            PaymentReviewCase caseObj = new PaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);

            // // 取得受款人資料顯示判斷條件
            // caseObj.setBenDataQ1(getBenDataQ1(caseObj.getApNo(), caseObj.getIssuYm()));
            // caseObj.setBenDataQ2(getBenDataQ2(caseObj.getApNo()));
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料 for getAllIssuData()
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<PaymentReviewExtCase> getBenIssueDataList(String apNo, String seqNo) {
        List<Baappbase> list = baappbaseDao.selectBenIssueDataBy(apNo, seqNo, new String[] { "10", "11", "12", "20" }, "in");
        List<PaymentReviewExtCase> returnList = new ArrayList<PaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            PaymentReviewExtCase caseObj = new PaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 單筆資料審核確認儲存 - 修改 給付主檔/給付核定檔
     * 
     * @param apNo 受理編號
     * @param notifyForm 核定格式
     * @param chgNote 更正原因
     * @param baappbaseIdList 要修改的給付主檔ID清單
     * @param manchkMk 人工審核結果
     */
    public void updateSingleBeneficiaryData(PaymentReviewCase caseObj, String notifyForm, String chgNote, String manchkMk, UserBean userData) {
        String apNo = caseObj.getApNo();
        String issuYm = caseObj.getIssuYm();
        String empNo = userData.getEmpNo();

        // 更新給付主檔相關欄位
        // [
        log.debug("Start Update BAAPPBASE ...");

        Baappbase beforeBaappbase = new Baappbase(); // 給付主檔改前值

        BeanUtility.copyProperties(beforeBaappbase, caseObj);
        Baappbase obj = new Baappbase();
        obj.setApNo(apNo);
        obj.setNotifyForm(notifyForm);
        obj.setChgNote(chgNote);
        obj.setManchkMk(manchkMk);
        obj.setProcStat("20");
        obj.setChkDate(DateUtility.getNowWestDate());
        obj.setChkMan(empNo);
        baappbaseDao.updateDataForReview(obj);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
        }

        // // 新增資料到 更正記錄檔
        // // [
        log.debug("Start Insert BAAPPLOG ...");
        BigDecimal baappbaseId = caseObj.getBaappbaseId();
        List<Baapplog> logList = caseObj.getBaapplogList();
        for (int i = 0; i < logList.size(); i++) {
            Baapplog baapplog = logList.get(i);
            baapplog.setBaappbaseId(baappbaseId);// 資料列編號

            baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            // baapplog.setUpCol(); // 異動項目 - Log 已設
            // // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
            // // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
            baapplogDao.insertData(baapplog);
        }
        log.debug("Insert BAAPPLOG Finished ...");
        // // ]

        log.debug("Update BAAPPBASE Finished...");
        // ]

        // 更新給付核定檔相關欄位
        Integer q3 = baappbaseDao.selectDataCountForReviewUpdate(apNo);

        if (q3 > 0) {
            Badapr badapr = new Badapr();
            badapr.setManchkMk(manchkMk);
            badapr.setApNo(apNo);
            badapr.setIssuYm(issuYm);
            badapr.setMtestMk("D");
            badaprDao.updateDataForReview(badapr);
        }

        // 更新審核給付清單紀錄檔
        Balp0d020 balp0d020 = new Balp0d020();
        balp0d020.setProcMk("2");
        balp0d020.setApNo(apNo);
        balp0d020.setIssuYm(issuYm);
        balp0d020Dao.updateDataForReview(balp0d020);

        // 新增審核給付清單紀錄檔
        if (("Y").equals(manchkMk) || ("N").equals(manchkMk)) {
            balp0d020 = new Balp0d020();
            balp0d020.setPayCode("L");
            balp0d020.setApNo(caseObj.getApNo());
            balp0d020.setSeqNo("0000");
            balp0d020.setIssuYm(caseObj.getIssuYm());
            balp0d020.setVeriSeq(caseObj.getVeriSeq());
            balp0d020.setCaseTyp(caseObj.getCaseTyp());
            balp0d020.setMchkTyp(caseObj.getMchkTyp());
            balp0d020.setPaysYm(caseObj.getMinPayYm());
            balp0d020.setPayeYm(caseObj.getMaxPayYm());
            balp0d020.setTissueAmt(caseObj.getBefIssueAmt());
            balp0d020.setTaplPayAmt(caseObj.getAplPayAmt());
            balp0d020.setMexcLvl(caseObj.getMexcLvl());
            balp0d020.setChkDate(DateUtility.getNowWestDate());
            balp0d020.setChkMan(empNo);
            balp0d020.setExeMk("0");
            balp0d020.setEvtIds(caseObj.getEvtIds());
            balp0d020.setEvtIdnNo(caseObj.getEvtIdnNo());
            balp0d020.setEvtBrDate(caseObj.getEvtBrDate());
            balp0d020.setEvtName(caseObj.getEvtName());
            balp0d020.setBenIds(caseObj.getBenIds());
            balp0d020.setBenIdnNo(caseObj.getBenIdnNo());
            balp0d020.setBenBrDate(caseObj.getBenBrDate());
            balp0d020.setBenName(caseObj.getBenName());
            balp0d020.setProcMk("0");
            balp0d020.setCrtUser(empNo);
            balp0d020.setCrtTime(DateUtility.getNowWestDateTime(true));
            balp0d020.setAcceptMk(caseObj.getAcceptMk());
            balp0d020.setManchkMk(manchkMk);
            balp0d020Dao.insertDataForReview(balp0d020);
        }
        log.debug("Update BAAPPBASE MANCHKMK Finished...");
    }

    /**
     * 批次審核確認儲存 - 修改 給付主檔
     * 
     * @param apNo 受理編號
     * @param userData 使用者資料
     */
    public void updateAllBeneficiaryData(String indexList, UserBean userData, List dataList) {
        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");
        String[] tmpIndexList = indexList.split(",");
        String empNo = userData.getEmpNo();

        for (int i = 0; i < tmpIndexList.length; i++) {
            String dataIndex = tmpIndexList[i];
            if (StringUtils.isNotBlank(dataIndex)) {

                PaymentReviewCase caseObj = (PaymentReviewCase) dataList.get(Integer.parseInt(dataIndex));
                Baappbase beforeBaappbase = new Baappbase(); // 給付主檔改前值

                BeanUtility.copyProperties(beforeBaappbase, caseObj);
                // 更新給付主檔相關欄位(以案來看,不以人來看)
                Baappbase obj = new Baappbase();
                obj.setManchkMk(caseObj.getManchkMk());
                obj.setProcStat("20");
                obj.setChkMan(empNo);
                obj.setChkDate(DateUtility.getNowWestDate());
                obj.setApNo(caseObj.getApNo());
                baappbaseDao.updateDataForReview(obj);

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                }

                // 新增資料到 更正記錄檔
                // [
                log.debug("Start Insert BAAPPLOG ...");
                Baapplog baapplog = new Baapplog();
                baapplog.setBaappbaseId(caseObj.getBaappbaseId());// 資料列編號
                baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
                baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
                baapplog.setUpdUser(userData.getEmpNo());// 異動人員
                baapplog.setUpCol("處理狀態"); // 異動項目
                baapplog.setBvalue("10");// 改前內容
                baapplog.setAvalue("20"); // 改後內容
                baapplogDao.insertData(baapplog);
                log.debug("Insert BAAPPLOG Finished ...");
                // ]

                // 更新給付核定檔相關欄位
                Integer q3 = baappbaseDao.selectDataCountForReviewUpdate(caseObj.getApNo());
                if (q3 > 0) {// q3<=0時不更新Badapr
                    Badapr badapr = new Badapr();
                    badapr.setManchkMk(caseObj.getManchkMk());
                    badapr.setApNo(caseObj.getApNo());
                    badapr.setIssuYm(caseObj.getIssuYm());
                    badapr.setMtestMk(caseObj.getMtestMk());
                    badaprDao.updateDataForReview(badapr);
                }

                // 更新審核給付清單紀錄檔
                Balp0d020 balp0d020 = new Balp0d020();
                balp0d020.setProcMk("2");
                balp0d020.setApNo(caseObj.getApNo());
                balp0d020.setIssuYm(caseObj.getIssuYm());
                balp0d020Dao.updateDataForReview(balp0d020);

                // 新增審核給付清單紀錄檔
                if (("Y").equals(caseObj.getManchkMk()) || ("N").equals(caseObj.getManchkMk())) {
                    balp0d020 = new Balp0d020();
                    balp0d020.setPayCode("L");
                    balp0d020.setApNo(caseObj.getApNo());
                    balp0d020.setSeqNo("0000");
                    balp0d020.setIssuYm(caseObj.getIssuYm());
                    balp0d020.setVeriSeq(caseObj.getVeriSeq());
                    balp0d020.setCaseTyp(caseObj.getCaseTyp());
                    balp0d020.setMchkTyp(caseObj.getMchkTyp());
                    balp0d020.setPaysYm(caseObj.getPaysYm());
                    balp0d020.setPayeYm(caseObj.getPayeYm());
                    balp0d020.setTissueAmt(caseObj.getTissueAmt());
                    balp0d020.setTaplPayAmt(caseObj.getTaplPayAmt());
                    balp0d020.setMexcLvl(caseObj.getMexcLvl());
                    balp0d020.setChkDate(DateUtility.getNowWestDate());
                    balp0d020.setChkMan(empNo);
                    balp0d020.setExeMk("0");
                    balp0d020.setEvtIds(caseObj.getEvtIds());
                    balp0d020.setEvtIdnNo(caseObj.getEvtIdnNo());
                    balp0d020.setEvtBrDate(caseObj.getEvtBrDate());
                    balp0d020.setEvtName(caseObj.getEvtName());
                    balp0d020.setBenIds(caseObj.getBenIds());
                    balp0d020.setBenIdnNo(caseObj.getBenIdnNo());
                    balp0d020.setBenBrDate(caseObj.getBenBrDate());
                    balp0d020.setBenName(caseObj.getBenName());
                    balp0d020.setProcMk("0");
                    balp0d020.setCrtUser(empNo);
                    balp0d020.setCrtTime(DateUtility.getNowWestDateTime(true));
                    balp0d020.setAcceptMk(caseObj.getAcceptMk());
                    balp0d020.setManchkMk(caseObj.getManchkMk());
                    balp0d020Dao.insertDataForReview(balp0d020);
                }
            }
        }
        log.debug("Update BAAPPBASE Finished...");
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 人工編審註記筆數
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param qryTyp 查詢方式 1:(MANCHKMK IS NULL OR MANCHKMK=' ') ; 2:MANCHKMK = 'N'
     * @return 內含 <code>BigDecimal</code> 物件的 List
     */
    public BigDecimal selectManChkMkBy(String apNo, String issuYm, String qryTyp) {
        return badaprDao.selectManChkMkBy(apNo, issuYm, qryTyp);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 受款人資料顯示判斷條件1
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public Integer getBenDataQ1(String apNo, String issuYm) {
        return bachkfileDao.selectForPayeeDataUpdateQ1(apNo, issuYm);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料顯示判斷條件2
     * 
     * @param apNo 受理編號
     */
    public Integer getBenDataQ2(String apNo) {
        return baappbaseDao.selectForPayeeDataUpdateQ2(apNo);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 確認按鈕狀態
     * 
     * @param apNo 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public String checkBtnStatusForPaymentReview(String apNo) {
        BigDecimal dataCount = baappbaseDao.checkBtnStatusForPaymentReview(apNo);
        String btnStatus = "N";
        if (dataCount.compareTo(new BigDecimal(0)) > 0) {
            btnStatus = "Y";
        }
        return btnStatus;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) Q1 for 給付審核(判斷「人工審核結果」radio button用)
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return <code>Integer</code> 物件
     */
    public Integer getQ1ForManchkMkRadio(String apNo, String issuYm) {
        return maadmrecDao.selectQ1ForReview(apNo, issuYm);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) Q2 for 給付審核(判斷「人工審核結果」radio button用)
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return <code>Integer</code> 物件
     */
    public Integer getQ2ForManchkMkRadio(String apNo, String issuYm) {
        return maadmrecDao.selectQ2ForReview(apNo, issuYm);
    }

    // ------------------------------ 老年年金給付審核 ------------------------------

    // ------------------------------ 失能年金給付審核 ------------------------------
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>DisabledPaymentReviewCase</code> 物件的 List
     */
    public List<DisabledPaymentReviewCase> selectReviewDataFromBaappbaseForDisabled(String apNo) {
        List<Baappbase> list = baappbaseDao.selectDataForReviewBy(apNo, "0000", new String[] { "10", "11", "12", "20" }, "in", new String[] { "35", "36", "38" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
        List<DisabledPaymentReviewCase> returnList = new ArrayList<DisabledPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 審核決行清單紀錄檔(<code>BAEXALIST</code>) 資料清單 for 失能年金給付審核
     * 
     * @param rptDate 列印日期
     * @param pageNo 頁次
     * @return 內含 <code>DisabledPaymentReviewCase</code> 物件的 List
     */
    public List<DisabledPaymentReviewCase> selectReviewDataFromExalistForDisabled(String rptDate, BigDecimal pageNo) {
        List<Baexalist> list = baexalistDao.selectReviewDataBy(rptDate, pageNo, ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
        List<DisabledPaymentReviewCase> returnList = new ArrayList<DisabledPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Baexalist obj = list.get(i);
            DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<DisabledPaymentReviewCase> getLetterTypeMk1ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk1(apNo);
        List<DisabledPaymentReviewCase> returnList = new ArrayList<DisabledPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<DisabledPaymentReviewCase> getLetterTypeMk2ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk2(apNo);
        List<DisabledPaymentReviewCase> returnList = new ArrayList<DisabledPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<DisabledPaymentReviewCase> getLetterTypeMk3ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk3(apNo);
        List<DisabledPaymentReviewCase> returnList = new ArrayList<DisabledPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<DisabledPaymentReviewCase> getLetterTypeMk4ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk4(apNo);
        List<DisabledPaymentReviewCase> returnList = new ArrayList<DisabledPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<DisabledPaymentReviewCase> getLetterTypeMk5ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk5(apNo);
        List<DisabledPaymentReviewCase> returnList = new ArrayList<DisabledPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    public DisabledPaymentReviewCase getLetterTypeMk6ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk6(apNo);
        DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
        if (list.size() >= 1) {
            Maadmrec obj = list.get(0);
            BeanUtility.copyProperties(caseObj, obj);
            String kind = getLetterTypeMk6KindForDisabled(obj.getReliefKind());
            String stat = getLetterTypeMk6StatForDisabled(obj.getReliefKind(), obj.getReliefStat());
            caseObj.setReliefKind(kind);
            caseObj.setReliefStat(stat);
        }

        return caseObj;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 救濟種類資料 for 失能年金給付審核
     * 
     * @param reliefKind 救濟種類
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6KindForDisabled(String reliefKind) {
        return maadmrecDao.selectLetterTypeMk6Kind(reliefKind);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟辦理情形資料 for 失能年金給付審核
     * 
     * @param reliefKind 救濟種類
     * @param reliefStat 行政救濟辦理情形
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6StatForDisabled(String reliefKind, String reliefStat) {
        return maadmrecDao.selectLetterTypeMk6Stat(reliefKind, reliefStat);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>DisabledPaymentReviewExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<DisabledPaymentReviewExtCase> getDisabledPaymentReviewEvtChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewEvtChkListForDisabled(apNo);
        List<DisabledPaymentReviewExtCase> returnList = new ArrayList<DisabledPaymentReviewExtCase>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 issuYm+payYm
        // Value則是同為該 issuYm+payYm 下的所有編審註記資料
        Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
        }

        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            ChkFileCase caseObj = new ChkFileCase();
            BeanUtility.copyProperties(caseObj, obj);
            (map.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(caseObj);
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            List<ChkFileCase> tempList = map.get(key);
            DisabledPaymentReviewExtCase caseObj = new DisabledPaymentReviewExtCase();
            caseObj.setIssuPayYm(key);
            caseObj.setChkFileList(tempList);
            caseObj.setChkFileDataSize(tempList.size());
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 眷屬編審註記 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>DisabledPaymentReviewExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<DisabledPaymentReviewCase> getDisabledPaymentReviewBenChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewBenChkListForDisabled(apNo);
        List<DisabledPaymentReviewCase> returnList = new ArrayList<DisabledPaymentReviewCase>();
        List<DisabledPaymentReviewExtCase> subList = new ArrayList<DisabledPaymentReviewExtCase>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 seqNo
        // Value則是同為該 seqNo 下的所有編審註記資料
        Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            map.put(obj.getSeqNo(), new ArrayList<ChkFileCase>());
        }
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            ChkFileCase caseObj = new ChkFileCase();
            BeanUtility.copyProperties(caseObj, obj);
            (map.get(obj.getSeqNo())).add(caseObj);
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            subList = new ArrayList<DisabledPaymentReviewExtCase>();
            String key = (String) it.next();
            List<ChkFileCase> tempList = map.get(key);

            // 建立資料SubMap
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 issuYm+payYm 下的所有編審註記資料
            Map<String, List<ChkFileCase>> subMap = new TreeMap<String, List<ChkFileCase>>();
            for (int i = 0; i < tempList.size(); i++) {
                ChkFileCase obj = (ChkFileCase) tempList.get(i);
                subMap.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
            }

            for (int i = 0; i < tempList.size(); i++) {
                ChkFileCase obj = (ChkFileCase) tempList.get(i);
                (subMap.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(obj);
            }

            // 將 分類好的 SubMap 轉為 list
            List<ChkFileCase> subTempList = new ArrayList<ChkFileCase>();
            for (Iterator subIt = subMap.keySet().iterator(); subIt.hasNext();) {
                String subKey = (String) subIt.next();
                subTempList = subMap.get(subKey);
                DisabledPaymentReviewExtCase caseObj = new DisabledPaymentReviewExtCase();
                caseObj.setIssuPayYm(subKey);
                caseObj.setChkFileList(subTempList);
                caseObj.setChkFileDataSize(subTempList.size());
                subList.add(caseObj);
            }
            if (subTempList.size() != 0) {
                DisabledPaymentReviewCase masterCase = new DisabledPaymentReviewCase();
                masterCase.setSeqNo(key);
                masterCase.setOtherChkDataList(subList);
                returnList.add(masterCase);
            }
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 眷屬符合註記編審資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>DisabledPaymentReviewExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<DisabledPaymentReviewCase> getDisabledPaymentReviewOtherChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewOtherChkListForDisabled(apNo);
        List<DisabledPaymentReviewCase> returnList = new ArrayList<DisabledPaymentReviewCase>();
        List<DisabledPaymentReviewExtCase> subList = new ArrayList<DisabledPaymentReviewExtCase>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 seqNo
        // Value則是同為該 seqNo 下的所有編審註記資料
        Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            map.put(obj.getSeqNo(), new ArrayList<ChkFileCase>());
        }
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            ChkFileCase caseObj = new ChkFileCase();
            BeanUtility.copyProperties(caseObj, obj);
            (map.get(obj.getSeqNo())).add(caseObj);
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            subList = new ArrayList<DisabledPaymentReviewExtCase>();
            String key = (String) it.next();
            List<ChkFileCase> tempList = map.get(key);

            // 建立資料SubMap
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 issuYm+payYm 下的所有編審註記資料
            Map<String, List<ChkFileCase>> subMap = new TreeMap<String, List<ChkFileCase>>();
            for (int i = 0; i < tempList.size(); i++) {
                ChkFileCase obj = (ChkFileCase) tempList.get(i);
                subMap.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
            }

            for (int i = 0; i < tempList.size(); i++) {
                ChkFileCase obj = (ChkFileCase) tempList.get(i);
                (subMap.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(obj);
            }

            // 將 分類好的 SubMap 轉為 list
            List<ChkFileCase> subTempList = new ArrayList<ChkFileCase>();
            for (Iterator subIt = subMap.keySet().iterator(); subIt.hasNext();) {
                String subKey = (String) subIt.next();
                subTempList = subMap.get(subKey);
                DisabledPaymentReviewExtCase caseObj = new DisabledPaymentReviewExtCase();
                caseObj.setIssuPayYm(subKey);
                caseObj.setChkFileList(subTempList);
                caseObj.setChkFileDataSize(subTempList.size());
                subList.add(caseObj);
            }
            if (subTempList.size() != 0) {
                DisabledPaymentReviewCase masterCase = new DisabledPaymentReviewCase();
                masterCase.setSeqNo(key);
                masterCase.setOtherChkDataList(subList);
                returnList.add(masterCase);
            }
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人清單 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @param benPayMk 受益人領取狀態註記
     * @return 內含 <code>DisabledPaymentReviewExtCase</code> 物件的 List
     */
    public List<DisabledPaymentReviewExtCase> getBenNameListForDisabled(String apNo, String benPayMk) {
        List<String> list = baappbaseDao.selectBenNameBy(apNo, benPayMk);
        List<DisabledPaymentReviewExtCase> returnList = new ArrayList<DisabledPaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            DisabledPaymentReviewExtCase caseObj = new DisabledPaymentReviewExtCase();
            caseObj.setBenName((String) list.get(i));
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @param issuym 核定年月
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<DisabledPaymentReviewExtCase> getPayDataForDisabled(String apNo, String issuYm) {
        List<Badapr> list = badaprDao.selectPayDataBy(apNo, issuYm);
        List<DisabledPaymentReviewExtCase> returnList = new ArrayList<DisabledPaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Badapr obj = list.get(i);
            DisabledPaymentReviewExtCase caseObj = new DisabledPaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseObj.setAplPayAmt(obj.getAplpayAmt());
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return 內含 <code>DisabledPaymentReviewCase</code> 物件的 List
     */
    public List<DisabledPaymentReviewCase> getAllIssuDataForDisabled(String apNo) {
        List<DisabledPaymentReviewCase> beneficiaryDataList = getBeneficiaryDataListForDisabled(apNo);
        List<DisabledPaymentReviewCase> returnList = new ArrayList<DisabledPaymentReviewCase>();

        for (int i = 0; i < beneficiaryDataList.size(); i++) {
            DisabledPaymentReviewCase obj = beneficiaryDataList.get(i);
            apNo = obj.getApNo();
            String seqNo = obj.getSeqNo();

            DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            List<DisabledPaymentReviewExtCase> benIssueDataList = getBenIssueDataListForDisabled(apNo, seqNo);
            caseObj.setBenIssueDataList(benIssueDataList);
            String showManchkMk = "Y";
            String manchkMkValue = "";
            // 以電腦編審結果來判斷是否顯示Radio
            // 以人工編審結果來判斷 人工審核結果Radio值
            // Y-合格
            for (int j = 0; j < benIssueDataList.size(); j++) {
                DisabledPaymentReviewExtCase benIssueData = benIssueDataList.get(j);
                if (("Y").equals(benIssueData.getAcceptMk())) {
                    showManchkMk = "Y";
                }
                if (("Y").equals(benIssueData.getManchkMk())) {
                    manchkMkValue = "Y";
                }
            }
            // S-待處理
            for (int j = 0; j < benIssueDataList.size(); j++) {
                DisabledPaymentReviewExtCase benIssueData = benIssueDataList.get(j);
                if (!("N").equals(benIssueData.getAcceptMk()) && !("Y").equals(benIssueData.getAcceptMk()) && StringUtils.isBlank(StringUtils.trimToEmpty(benIssueData.getAcceptMk()))) {
                    showManchkMk = "S";
                }
                if (!("N").equals(benIssueData.getManchkMk()) && !("Y").equals(benIssueData.getManchkMk()) && StringUtils.isBlank(StringUtils.trimToEmpty(benIssueData.getManchkMk()))) {
                    manchkMkValue = " ";
                }
            }
            // N-不合格
            for (int j = 0; j < benIssueDataList.size(); j++) {
                DisabledPaymentReviewExtCase benIssueData = benIssueDataList.get(j);
                if (("N").equals(benIssueData.getAcceptMk())) {
                    showManchkMk = "N";
                }
                if (("N").equals(benIssueData.getManchkMk())) {
                    manchkMkValue = "N";
                }
            }

            caseObj.setShowManchkMk(showManchkMk);
            caseObj.setManchkMkValue(manchkMkValue);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料 for 失能年金給付審核 (for getAllBenData()/getAllIssuData())
     * 
     * @param apNo 受理編號
     * @return 內含 <code>DisabledPaymentReviewCase</code> 物件的 List
     */
    public List<DisabledPaymentReviewCase> getBeneficiaryDataListForDisabled(String apNo) {
        List<Baappbase> list = baappbaseDao.selectBeneficiaryDataBy(apNo);
        List<DisabledPaymentReviewCase> returnList = new ArrayList<DisabledPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            // 取得受款人資料顯示判斷條件
            caseObj.setBenDataQ1(getBenDataQ1(caseObj.getApNo(), caseObj.getIssuYm()));
            caseObj.setBenDataQ2(getBenDataQ2(caseObj.getApNo()));
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料 for 失能年金給付審核 (for getAllIssuData())
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>DisabledPaymentReviewExtCase</code> 物件的 List
     */
    public List<DisabledPaymentReviewExtCase> getBenIssueDataListForDisabled(String apNo, String seqNo) {
        List<Baappbase> list = baappbaseDao.selectBenIssueDataBy(apNo, seqNo, new String[] { "10", "11", "12", "20" }, "in");
        List<DisabledPaymentReviewExtCase> returnList = new ArrayList<DisabledPaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            DisabledPaymentReviewExtCase caseObj = new DisabledPaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 職災相關資料 for 失能年金給付審核作業
     * 
     * @param apNo 受理編號
     * @return <code>DisabledPaymentReviewCase</code>
     */
    public DisabledPaymentReviewCase getOccAccDataForDisabled(String apNo) {
        Badapr badapr = badaprDao.selectOccAccDataForDisabledPaymentReview(apNo);
        DisabledPaymentReviewCase returnCase = new DisabledPaymentReviewCase();
        if (badapr != null) {
            BeanUtility.copyProperties(returnCase, badapr);
        }
        return returnCase;
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 失能相關資料 for 失能年金給付審核作業
     * 
     * @param apNo 受理編號
     * @return <code>DisabledPaymentReviewCase</code> 物件
     */
    public DisabledPaymentReviewCase selectDisabledDataForDisabled(String apNo) {
        Baappexpand baappexpand = baappexpandDao.selectDisabledDataForPaymentReview(apNo);
        DisabledPaymentReviewCase returnCase = new DisabledPaymentReviewCase();
        if (baappexpand != null) {
            BeanUtility.copyProperties(returnCase, baappexpand);
            // 取得 醫院簡稱
            returnCase.setHpSnam(bbcmf07Dao.selectHpSnamBy(returnCase.getHosId()));
        }
        return returnCase;
    }

    /**
     * 單筆資料審核確認儲存 - 修改 給付主檔/給付核定檔
     * 
     * @param apNo 受理編號
     * @param notifyForm 核定格式
     * @param chgNote 更正原因
     * @param baappbaseIdList 要修改的給付主檔ID清單
     * @param manchkMk 人工審核結果
     */
    public void updateSingleBeneficiaryDataForDisabled(DisabledPaymentReviewCase caseObj, String notifyForm, String chgNote, String manchkMk, UserBean userData) {
        String apNo = caseObj.getApNo();
        String issuYm = caseObj.getIssuYm();
        String empNo = userData.getEmpNo();

        // 更新給付主檔相關欄位
        // [
        log.debug("Start Update BAAPPBASE ...");

        Baappbase beforeBaappbase = new Baappbase(); // 給付主檔改前值

        BeanUtility.copyProperties(beforeBaappbase, caseObj);

        Baappbase obj = new Baappbase();
        obj.setApNo(apNo);
        obj.setNotifyForm(notifyForm);
        obj.setChgNote(chgNote);
        obj.setManchkMk(manchkMk);
        obj.setProcStat("20");
        obj.setChkDate(DateUtility.getNowWestDate());
        obj.setChkMan(empNo);
        baappbaseDao.updateDataForReview(obj);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
        }

        // // 新增資料到 更正記錄檔
        // // [
        log.debug("Start Insert BAAPPLOG ...");
        BigDecimal baappbaseId = caseObj.getBaappbaseId();
        List<Baapplog> logList = caseObj.getBaapplogList();
        for (int i = 0; i < logList.size(); i++) {
            Baapplog baapplog = logList.get(i);
            baapplog.setBaappbaseId(baappbaseId);// 資料列編號

            baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            // baapplog.setUpCol(); // 異動項目 - Log 已設
            // // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
            // // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
            baapplogDao.insertData(baapplog);
        }
        log.debug("Insert BAAPPLOG Finished ...");
        // // ]

        log.debug("Update BAAPPBASE Finished...");
        // ]

        // 更新給付核定檔相關欄位
        Integer q3 = baappbaseDao.selectDataCountForReviewUpdate(apNo);

        if (q3 > 0) {
            Badapr badapr = new Badapr();
            badapr.setManchkMk(manchkMk);
            badapr.setApNo(apNo);
            badapr.setIssuYm(issuYm);
            badapr.setMtestMk("D");
            badaprDao.updateDataForReview(badapr);
        }

        // 更新審核給付清單紀錄檔
        Balp0d020 balp0d020 = new Balp0d020();
        balp0d020.setProcMk("2");
        balp0d020.setApNo(apNo);
        balp0d020.setIssuYm(issuYm);
        balp0d020Dao.updateDataForReview(balp0d020);

        // 新增審核給付清單紀錄檔
        if (("Y").equals(manchkMk) || ("N").equals(manchkMk)) {
            balp0d020 = new Balp0d020();
            balp0d020.setPayCode("K");
            balp0d020.setApNo(caseObj.getApNo());
            balp0d020.setSeqNo("0000");
            balp0d020.setIssuYm(caseObj.getIssuYm());
            balp0d020.setVeriSeq(caseObj.getVeriSeq());
            balp0d020.setCaseTyp(caseObj.getCaseTyp());
            balp0d020.setMchkTyp(caseObj.getMchkTyp());
            balp0d020.setPaysYm(caseObj.getMinPayYm());
            balp0d020.setPayeYm(caseObj.getMaxPayYm());
            balp0d020.setTissueAmt(caseObj.getBefIssueAmt());
            balp0d020.setTaplPayAmt(caseObj.getAplPayAmt());
            balp0d020.setMexcLvl(caseObj.getMexcLvl());
            balp0d020.setChkDate(DateUtility.getNowWestDate());
            balp0d020.setChkMan(empNo);
            balp0d020.setExeMk("0");
            balp0d020.setEvtIds(caseObj.getEvtIds());
            balp0d020.setEvtIdnNo(caseObj.getEvtIdnNo());
            balp0d020.setEvtBrDate(caseObj.getEvtBrDate());
            balp0d020.setEvtName(caseObj.getEvtName());
            balp0d020.setBenIds(caseObj.getBenIds());
            balp0d020.setBenIdnNo(caseObj.getBenIdnNo());
            balp0d020.setBenBrDate(caseObj.getBenBrDate());
            balp0d020.setBenName(caseObj.getBenName());
            balp0d020.setProcMk("0");
            balp0d020.setCrtUser(empNo);
            balp0d020.setCrtTime(DateUtility.getNowWestDateTime(true));
            balp0d020.setAcceptMk(caseObj.getAcceptMk());
            balp0d020.setManchkMk(manchkMk);
            balp0d020Dao.insertDataForReview(balp0d020);
        }
        log.debug("Update BAAPPBASE MANCHKMK Finished...");
    }

    /**
     * 批次審核確認儲存 - 修改 給付主檔
     * 
     * @param apNo 受理編號
     * @param userData 使用者資料
     */
    public void updateAllBeneficiaryDataForDisabled(String indexList, UserBean userData, List dataList) {
        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");
        String[] tmpIndexList = indexList.split(",");
        String empNo = userData.getEmpNo();

        for (int i = 0; i < tmpIndexList.length; i++) {
            String dataIndex = tmpIndexList[i];
            if (StringUtils.isNotBlank(dataIndex)) {

                DisabledPaymentReviewCase caseObj = (DisabledPaymentReviewCase) dataList.get(Integer.parseInt(dataIndex));
                Baappbase beforeBaappbase = new Baappbase(); // 給付主檔改前值

                BeanUtility.copyProperties(beforeBaappbase, caseObj);

                // 更新給付主檔相關欄位(以案來看,不以人來看)
                Baappbase obj = new Baappbase();
                obj.setManchkMk(caseObj.getManchkMk());
                obj.setProcStat("20");
                obj.setChkMan(empNo);
                obj.setChkDate(DateUtility.getNowWestDate());
                obj.setApNo(caseObj.getApNo());
                baappbaseDao.updateDataForReview(obj);

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                }

                // 新增資料到 更正記錄檔
                // [
                log.debug("Start Insert BAAPPLOG ...");
                Baapplog baapplog = new Baapplog();
                baapplog.setBaappbaseId(caseObj.getBaappbaseId());// 資料列編號
                baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
                baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
                baapplog.setUpdUser(userData.getEmpNo());// 異動人員
                baapplog.setUpCol("處理狀態"); // 異動項目
                baapplog.setBvalue("10");// 改前內容
                baapplog.setAvalue("20"); // 改後內容
                baapplogDao.insertData(baapplog);
                log.debug("Insert BAAPPLOG Finished ...");
                // ]

                // 更新給付核定檔相關欄位
                Integer q3 = baappbaseDao.selectDataCountForReviewUpdate(caseObj.getApNo());
                if (q3 > 0) {// q3<=0時不更新Badapr
                    Badapr badapr = new Badapr();
                    badapr.setManchkMk(caseObj.getManchkMk());
                    badapr.setApNo(caseObj.getApNo());
                    badapr.setIssuYm(caseObj.getIssuYm());
                    badapr.setMtestMk(caseObj.getMtestMk());
                    badaprDao.updateDataForReview(badapr);
                }

                // 更新審核給付清單紀錄檔
                Balp0d020 balp0d020 = new Balp0d020();
                balp0d020.setProcMk("2");
                balp0d020.setApNo(caseObj.getApNo());
                balp0d020.setIssuYm(caseObj.getIssuYm());
                balp0d020Dao.updateDataForReview(balp0d020);

                // 新增審核給付清單紀錄檔
                if (("Y").equals(caseObj.getManchkMk()) || ("N").equals(caseObj.getManchkMk())) {
                    balp0d020 = new Balp0d020();
                    balp0d020.setPayCode("K");
                    balp0d020.setApNo(caseObj.getApNo());
                    balp0d020.setSeqNo("0000");
                    balp0d020.setIssuYm(caseObj.getIssuYm());
                    balp0d020.setVeriSeq(caseObj.getVeriSeq());
                    balp0d020.setCaseTyp(caseObj.getCaseTyp());
                    balp0d020.setMchkTyp(caseObj.getMchkTyp());
                    balp0d020.setPaysYm(caseObj.getPaysYm());
                    balp0d020.setPayeYm(caseObj.getPayeYm());
                    balp0d020.setTissueAmt(caseObj.getTissueAmt());
                    balp0d020.setTaplPayAmt(caseObj.getTaplPayAmt());
                    balp0d020.setMexcLvl(caseObj.getMexcLvl());
                    balp0d020.setChkDate(DateUtility.getNowWestDate());
                    balp0d020.setChkMan(empNo);
                    balp0d020.setExeMk("0");
                    balp0d020.setEvtIds(caseObj.getEvtIds());
                    balp0d020.setEvtIdnNo(caseObj.getEvtIdnNo());
                    balp0d020.setEvtBrDate(caseObj.getEvtBrDate());
                    balp0d020.setEvtName(caseObj.getEvtName());
                    balp0d020.setBenIds(caseObj.getBenIds());
                    balp0d020.setBenIdnNo(caseObj.getBenIdnNo());
                    balp0d020.setBenBrDate(caseObj.getBenBrDate());
                    balp0d020.setBenName(caseObj.getBenName());
                    balp0d020.setProcMk("0");
                    balp0d020.setCrtUser(empNo);
                    balp0d020.setCrtTime(DateUtility.getNowWestDateTime(true));
                    balp0d020.setAcceptMk(caseObj.getAcceptMk());
                    balp0d020.setManchkMk(caseObj.getManchkMk());
                    balp0d020Dao.insertDataForReview(balp0d020);
                }
            }
        }
        log.debug("Update BAAPPBASE Finished...");
    }

    // ------------------------------ 失能年金給付審核 ------------------------------

    // ------------------------------ 遺屬年金給付審核 ------------------------------
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>SurvivorPaymentReviewCase</code> 物件的 List
     */
    public List<SurvivorPaymentReviewCase> selectReviewDataFromBaappbaseForSurvivor(String apNo) {
        List<Baappbase> list = baappbaseDao.selectDataForReviewBy(apNo, "0000", new String[] { "10", "11", "12", "20" }, "in", new String[] { "55", "56", "59" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);
        List<SurvivorPaymentReviewCase> returnList = new ArrayList<SurvivorPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 審核決行清單紀錄檔(<code>BAEXALIST</code>) 資料清單 for 遺屬年金給付審核
     * 
     * @param rptDate 列印日期
     * @param pageNo 頁次
     * @return 內含 <code>SurvivorPaymentReviewCase</code> 物件的 List
     */
    public List<SurvivorPaymentReviewCase> selectReviewDataFromExalistForSurvivor(String rptDate, BigDecimal pageNo) {
        List<Baexalist> list = baexalistDao.selectReviewDataBy(rptDate, pageNo, ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);
        List<SurvivorPaymentReviewCase> returnList = new ArrayList<SurvivorPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Baexalist obj = list.get(i);
            SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<SurvivorPaymentReviewCase> getLetterTypeMk1ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk1(apNo);
        List<SurvivorPaymentReviewCase> returnList = new ArrayList<SurvivorPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<SurvivorPaymentReviewCase> getLetterTypeMk2ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk2(apNo);
        List<SurvivorPaymentReviewCase> returnList = new ArrayList<SurvivorPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<SurvivorPaymentReviewCase> getLetterTypeMk3ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk3(apNo);
        List<SurvivorPaymentReviewCase> returnList = new ArrayList<SurvivorPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<SurvivorPaymentReviewCase> getLetterTypeMk4ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk4(apNo);
        List<SurvivorPaymentReviewCase> returnList = new ArrayList<SurvivorPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<SurvivorPaymentReviewCase> getLetterTypeMk5ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk5(apNo);
        List<SurvivorPaymentReviewCase> returnList = new ArrayList<SurvivorPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    public SurvivorPaymentReviewCase getLetterTypeMk6ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk6(apNo);
        SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
        if (list.size() >= 1) {
            Maadmrec obj = list.get(0);
            BeanUtility.copyProperties(caseObj, obj);
            String kind = getLetterTypeMk6KindForSurvivor(obj.getReliefKind());
            String stat = getLetterTypeMk6StatForSurvivor(obj.getReliefKind(), obj.getReliefStat());
            caseObj.setReliefKind(kind);
            caseObj.setReliefStat(stat);
        }

        return caseObj;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 救濟種類資料 for 遺屬年金給付審核
     * 
     * @param reliefKind 救濟種類
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6KindForSurvivor(String reliefKind) {
        return maadmrecDao.selectLetterTypeMk6Kind(reliefKind);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟辦理情形資料 for 遺屬年金給付審核
     * 
     * @param reliefKind 救濟種類
     * @param reliefStat 行政救濟辦理情形
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6StatForSurvivor(String reliefKind, String reliefStat) {
        return maadmrecDao.selectLetterTypeMk6Stat(reliefKind, reliefStat);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審註記資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>SurvivorPaymentReviewExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<SurvivorPaymentReviewExtCase> getSurvivorPaymentReviewEvtChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewEvtChkListForSurvivor(apNo);
        List<SurvivorPaymentReviewExtCase> returnList = new ArrayList<SurvivorPaymentReviewExtCase>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 issuYm+payYm
        // Value則是同為該 issuYm+payYm 下的所有編審註記資料
        Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
        }

        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            ChkFileCase caseObj = new ChkFileCase();
            BeanUtility.copyProperties(caseObj, obj);
            (map.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(caseObj);
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            List<ChkFileCase> tempList = map.get(key);
            SurvivorPaymentReviewExtCase caseObj = new SurvivorPaymentReviewExtCase();
            caseObj.setIssuPayYm(key);
            caseObj.setChkFileList(tempList);
            caseObj.setChkFileDataSize(tempList.size());
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 遺屬編審註記資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>SurvivorPaymentReviewExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<SurvivorPaymentReviewCase> getSurvivorPaymentReviewBenChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewBenChkListForSurvivor(apNo);
        List<SurvivorPaymentReviewCase> returnList = new ArrayList<SurvivorPaymentReviewCase>();
        List<SurvivorPaymentReviewExtCase> subList = new ArrayList<SurvivorPaymentReviewExtCase>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 seqNo
        // Value則是同為該 seqNo 下的所有編審註記資料
        Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            map.put(obj.getSeqNo(), new ArrayList<ChkFileCase>());
        }
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            ChkFileCase caseObj = new ChkFileCase();
            BeanUtility.copyProperties(caseObj, obj);
            (map.get(obj.getSeqNo())).add(caseObj);
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            subList = new ArrayList<SurvivorPaymentReviewExtCase>();
            String key = (String) it.next();
            List<ChkFileCase> tempList = map.get(key);

            // 建立資料SubMap
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 issuYm+payYm 下的所有編審註記資料
            Map<String, List<ChkFileCase>> subMap = new TreeMap<String, List<ChkFileCase>>();
            for (int i = 0; i < tempList.size(); i++) {
                ChkFileCase obj = (ChkFileCase) tempList.get(i);
                subMap.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
            }

            for (int i = 0; i < tempList.size(); i++) {
                ChkFileCase obj = (ChkFileCase) tempList.get(i);
                (subMap.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(obj);
            }

            // 將 分類好的 SubMap 轉為 list
            List<ChkFileCase> subTempList = new ArrayList<ChkFileCase>();
            for (Iterator subIt = subMap.keySet().iterator(); subIt.hasNext();) {
                String subKey = (String) subIt.next();
                subTempList = subMap.get(subKey);
                SurvivorPaymentReviewExtCase caseObj = new SurvivorPaymentReviewExtCase();
                caseObj.setIssuPayYm(subKey);
                caseObj.setChkFileList(subTempList);
                caseObj.setChkFileDataSize(subTempList.size());
                subList.add(caseObj);
            }
            if (subTempList.size() != 0) {
                SurvivorPaymentReviewCase masterCase = new SurvivorPaymentReviewCase();
                masterCase.setSeqNo(key);
                masterCase.setOtherChkDataList(subList);
                returnList.add(masterCase);
            }
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 遺屬符合註記資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>SurvivorPaymentReviewExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<SurvivorPaymentReviewCase> getSurvivorPaymentReviewOtherChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewOtherChkListForSurvivor(apNo);
        List<SurvivorPaymentReviewCase> returnList = new ArrayList<SurvivorPaymentReviewCase>();
        List<SurvivorPaymentReviewExtCase> subList = new ArrayList<SurvivorPaymentReviewExtCase>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 seqNo
        // Value則是同為該 seqNo 下的所有編審註記資料
        Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            map.put(obj.getSeqNo(), new ArrayList<ChkFileCase>());
        }
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            ChkFileCase caseObj = new ChkFileCase();
            BeanUtility.copyProperties(caseObj, obj);
            (map.get(obj.getSeqNo())).add(caseObj);
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            subList = new ArrayList<SurvivorPaymentReviewExtCase>();
            String key = (String) it.next();
            List<ChkFileCase> tempList = map.get(key);

            // 建立資料SubMap
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 issuYm+payYm 下的所有編審註記資料
            Map<String, List<ChkFileCase>> subMap = new TreeMap<String, List<ChkFileCase>>();
            for (int i = 0; i < tempList.size(); i++) {
                ChkFileCase obj = (ChkFileCase) tempList.get(i);
                subMap.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
            }

            for (int i = 0; i < tempList.size(); i++) {
                ChkFileCase obj = (ChkFileCase) tempList.get(i);
                (subMap.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(obj);
            }

            // 將 分類好的 SubMap 轉為 list
            List<ChkFileCase> subTempList = new ArrayList<ChkFileCase>();
            for (Iterator subIt = subMap.keySet().iterator(); subIt.hasNext();) {
                String subKey = (String) subIt.next();
                subTempList = subMap.get(subKey);
                SurvivorPaymentReviewExtCase caseObj = new SurvivorPaymentReviewExtCase();
                caseObj.setIssuPayYm(subKey);
                caseObj.setChkFileList(subTempList);
                caseObj.setChkFileDataSize(subTempList.size());
                subList.add(caseObj);
            }
            if (subTempList.size() != 0) {
                SurvivorPaymentReviewCase masterCase = new SurvivorPaymentReviewCase();
                masterCase.setSeqNo(key);
                masterCase.setOtherChkDataList(subList);
                returnList.add(masterCase);
            }
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人清單 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @param benPayMk 受益人領取狀態註記
     * @return 內含 <code>SurvivorPaymentReviewExtCase</code> 物件的 List
     */
    public List<SurvivorPaymentReviewExtCase> getBenNameListForSurvivor(String apNo, String benPayMk) {
        List<String> list = baappbaseDao.selectBenNameBy(apNo, benPayMk);
        List<SurvivorPaymentReviewExtCase> returnList = new ArrayList<SurvivorPaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            SurvivorPaymentReviewExtCase caseObj = new SurvivorPaymentReviewExtCase();
            caseObj.setBenName((String) list.get(i));
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @param issuym 核定年月
     * @return 內含 <code>PaymentReviewExtCase</code> 物件的 List
     */
    public List<SurvivorPaymentReviewExtCase> getPayDataForSurvivor(String apNo, String issuYm) {
        List<Badapr> list = badaprDao.selectPayDataBy(apNo, issuYm);
        List<SurvivorPaymentReviewExtCase> returnList = new ArrayList<SurvivorPaymentReviewExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Badapr obj = list.get(i);
            SurvivorPaymentReviewExtCase caseObj = new SurvivorPaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseObj.setAplPayAmt(obj.getAplpayAmt());
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料 for 遺屬年金給付審核 (for getAllBenData()/getAllIssuData())
     * 
     * @param apNo 受理編號
     * @return 內含 <code>SurvivorPaymentReviewCase</code> 物件的 List
     */
    public List<SurvivorPaymentReviewCase> getbeneficiaryDataListForSurvivor(String apNo, Integer q1, Integer q2) {
        List<Baappbase> list = baappbaseDao.selectEvtBenDataForSurvivorPaymentReview(apNo);
        List<SurvivorPaymentReviewCase> returnList = new ArrayList<SurvivorPaymentReviewCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
            BeanUtility.copyProperties(caseObj, obj);
            if (("0000").equals(caseObj.getSeqNo())) {
                caseObj.setBenIssueDataList(getIssuDataListForSurvivor(null, caseObj.getApNo(), caseObj.getSeqNo(), "="));
                // 設定人工審核radio button狀態
                caseObj = setEvtDataManchkMkCtl(caseObj, q1, q2);
            }

            if (!("0000").equals(caseObj.getSeqNo())) {
                // 事故者資料
                SurvivorPaymentReviewCase evtCaseObj = returnList.get(0);
                // 設定人工審核radio button狀態
                caseObj = setBenDataManchkMkCtl(evtCaseObj, caseObj, null, null);
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料 for 遺屬年金給付審核 (for getAllIssuData())
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param eqType SQL EqualType
     * @return 內含 <code>SurvivorPaymentReviewExtCase</code> 物件的 List
     */
    public List<SurvivorPaymentReviewExtCase> getIssuDataListForSurvivor(List<SurvivorPaymentReviewCase> beneficiaryDataList, String apNo, String seqNo, String eqType) {
        List<Baappbase> list = baappbaseDao.selectEvtBenIssuDataForSurvivorPaymentReview(apNo, seqNo, eqType);
        List<SurvivorPaymentReviewExtCase> returnList = new ArrayList<SurvivorPaymentReviewExtCase>();

        // 將受款人表頭資料轉Map
        Map<String, SurvivorPaymentReviewCase> map = new TreeMap<String, SurvivorPaymentReviewCase>();
        if (beneficiaryDataList != null) {
            for (int i = 0; i < beneficiaryDataList.size(); i++) {
                SurvivorPaymentReviewCase caseObj = beneficiaryDataList.get(i);
                map.put(caseObj.getSeqNo(), caseObj);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            SurvivorPaymentReviewExtCase caseObj = new SurvivorPaymentReviewExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            // 設定人工審核radio button狀態
            caseObj = setBenIssuDataManchkMkCtl(map, caseObj);

            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料 for 遺屬年金給付審核
     * 
     * @param benIssuDataList 受款人核定資料
     * @return List (index=0 頁面顯示用之List; index=1 session暫存用之map)
     */
    public List<Object> transInitBenIssuDataForSurvivor(List<SurvivorPaymentReviewExtCase> benIssuDataList) {
        List<SurvivorPaymentReviewCase> payYmList = new ArrayList<SurvivorPaymentReviewCase>();
        List<Object> returnList = new ArrayList<Object>();

        // 建立資料Map
        // payYmMap的Key為payYm, Value則是同為該payYm下的所有核定資料
        // idMap的Key為badaprId, Value則是同為該badaprId之核定資料
        Map<String, List<SurvivorPaymentReviewExtCase>> payYmMap = new TreeMap<String, List<SurvivorPaymentReviewExtCase>>();
        Map<BigDecimal, SurvivorPaymentReviewExtCase> idMap = new TreeMap<BigDecimal, SurvivorPaymentReviewExtCase>();
        for (int i = 0; i < benIssuDataList.size(); i++) {
            SurvivorPaymentReviewExtCase obj = benIssuDataList.get(i);
            payYmMap.put(obj.getPayYm(), new ArrayList<SurvivorPaymentReviewExtCase>());
            idMap.put(obj.getBadaprId(), obj);
        }

        for (int i = 0; i < benIssuDataList.size(); i++) {
            SurvivorPaymentReviewExtCase obj = benIssuDataList.get(i);
            (payYmMap.get(obj.getPayYm())).add(obj);
        }

        // 將分類好的payYmMap轉為list
        for (Iterator it = payYmMap.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            List<SurvivorPaymentReviewExtCase> tempList = payYmMap.get(key);
            SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
            caseObj.setPayYm(key);
            caseObj.setBenIssueDataList(tempList);
            payYmList.add(caseObj);
        }
        returnList.add(0, payYmList);
        returnList.add(1, idMap);
        return returnList;
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 失能相關資料 for 遺屬年金給付審核作業
     * 
     * @param apNo 受理編號
     * @return <code>SurvivorPaymentReviewCase</code> 物件
     */
    public SurvivorPaymentReviewCase selectDisabledDataForSurvivor(String apNo) {
        Baappexpand baappexpand = baappexpandDao.selectDisabledDataForPaymentReview(apNo);
        SurvivorPaymentReviewCase returnCase = new SurvivorPaymentReviewCase();
        if (baappexpand != null) {
            BeanUtility.copyProperties(returnCase, baappexpand);
            // 取得 醫院簡稱
            returnCase.setHpSnam(bbcmf07Dao.selectHpSnamBy(returnCase.getHosId()));
        }
        return returnCase;
    }

    // /**
    // * 單筆資料審核確認儲存 - 修改 給付主檔/給付核定檔 var.1.00
    // *
    // * @param apNo 受理編號
    // * @param notifyForm 核定格式
    // * @param chgNote 更正原因
    // * @param baappbaseIdList 要修改的給付主檔ID清單
    // * @param manchkMk 人工審核結果
    // */
    // public void updateSingleBeneficiaryDataForSurvivor(SurvivorPaymentReviewCase caseObj, String notifyForm, String chgNote, String manchkMk, Map<BigDecimal, SurvivorPaymentReviewExtCase> benIssuDataMap, UserBean userData) {
    // String apNo = caseObj.getApNo(); // 受理編號
    // String issuYm = caseObj.getIssuYm();// 核定年月
    // String empNo = userData.getEmpNo();// 員工編號
    // String evtManchkMk = "";// 事故者人審核結果
    // String[] dataArray = manchkMk.split(",");
    //
    // Baappbase beforeBaappbase = new Baappbase();
    // beforeBaappbase.setApNo(apNo);
    // beforeBaappbase.setSeqNo(caseObj.getSeqNo());
    // beforeBaappbase.setNotifyForm(caseObj.getNotifyForm());
    // beforeBaappbase.setManchkMk(caseObj.getManchkMk());
    // beforeBaappbase.setChgNote(caseObj.getChgNote());
    // beforeBaappbase.setProcStat(caseObj.getProcStat());
    // beforeBaappbase.setChkDate(caseObj.getChkDate());
    // beforeBaappbase.setChkMan(caseObj.getChkMan());
    //
    // // 更新給付主檔相關欄位
    // // [
    // if (!(dataArray.length == 1 && ("").equals(dataArray[0])))
    // for (int i = 0; i < dataArray.length; i++) {
    // String[] data = dataArray[i].split(";");
    // String dataManchkMk = data[0];
    // String dataSeqNo = data[1];
    //
    // if (("0000").equals(dataSeqNo)) {
    // evtManchkMk = dataManchkMk;
    // }
    //
    // Baappbase baappbase = new Baappbase();
    // baappbase.setApNo(apNo);
    // baappbase.setSeqNo(dataSeqNo);
    // baappbase.setNotifyForm(notifyForm);
    // baappbase.setManchkMk(dataManchkMk);
    // baappbase.setChgNote(chgNote);
    // baappbase.setProcStat("20");
    // baappbase.setChkDate(DateUtility.getNowWestDate());
    // baappbase.setChkMan(empNo);
    // baappbaseDao.updateDataForSurvivorPaymentReview(baappbase);
    //
    // // Insert MMAPLOG
    // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
    // // 紀錄 Log
    // FrameworkLogUtil.doUpdateLog(beforeBaappbase, baappbase);
    // }
    // }
    // // ]
    //
    // // 判斷『給付主檔』目前的處理狀態是否可以更新
    // // 「資料筆數」>0時，更新『給付核定檔』, 「資料筆數」<=0時，不更新『給付核定檔』
    // Integer countValue1 = baappbaseDao.selectDataCount1ForSurvivorPaymentReview(apNo);
    // if (countValue1 > 0) {
    // // 更新事故者核定資料
    // Badapr badapr = new Badapr();
    // badapr.setApNo(apNo);
    // badapr.setManchkMk(evtManchkMk);
    // badaprDao.updateEvtDataForSurvivorPaymentReview(badapr);
    //
    // // 更新受款人核定資料
    // // (受款人核定資料之人工審核結果 在頁面選擇時已經存入session
    // // 所以不需要另外處理, 直接存入資料庫即可)
    // for (Iterator it = benIssuDataMap.keySet().iterator(); it.hasNext();) {
    // BigDecimal key = (BigDecimal) it.next();
    // SurvivorPaymentReviewExtCase benIssuCase = benIssuDataMap.get(key);
    //
    // badapr = new Badapr();
    // badapr.setApNo(apNo);
    // badapr.setSeqNo(benIssuCase.getSeqNo());
    // badapr.setIssuYm(benIssuCase.getIssuYm());
    // badapr.setPayYm(benIssuCase.getPayYm());
    // badapr.setManchkMk(benIssuCase.getManchkMk());
    // badaprDao.updateBenDataForSurvivorPaymentReview(badapr);
    // }
    // }
    //
    // // 更新『審核給付清單紀錄檔』相關欄位
    // Balp0d020 balp0d020 = new Balp0d020();
    // balp0d020.setProcMk("2");
    // balp0d020.setApNo(apNo);
    // balp0d020.setIssuYm(issuYm);
    // balp0d020Dao.updateDataForReview(balp0d020);
    //
    // // 新增『審核給付清單紀錄檔』的記錄資料
    // // 取得 判斷目前各給付年月是否已審核完成 之資料筆數條件
    // Integer countValue2 = baappbaseDao.selectDataCount2ForSurvivorPaymentReview(apNo);
    //
    // // 「資料筆數」>0，不需新增記錄
    // // 「資料筆數」=0時，且【事故者－「人工審核結果」】=Y或N時，才需新增記錄
    // if (countValue2 == 0) {
    // if (("Y").equals(evtManchkMk) || ("N").equals(evtManchkMk)) {
    // balp0d020 = new Balp0d020();
    // balp0d020.setPayCode("S");
    // balp0d020.setApNo(caseObj.getApNo());
    // balp0d020.setSeqNo("0000");
    // balp0d020.setIssuYm(caseObj.getIssuYm());
    // balp0d020.setVeriSeq(caseObj.getVeriSeq());
    // balp0d020.setCaseTyp(caseObj.getCaseTyp());
    // balp0d020.setMchkTyp(caseObj.getMchkTyp());
    // balp0d020.setPaysYm(caseObj.getMinPayYm());
    // balp0d020.setPayeYm(caseObj.getMaxPayYm());
    // balp0d020.setTissueAmt(caseObj.getBefIssueAmt());
    // balp0d020.setTaplPayAmt(caseObj.getAplPayAmt());
    // balp0d020.setAcceptMk(caseObj.getAcceptMk());
    // balp0d020.setManchkMk(evtManchkMk);
    // balp0d020.setMexcLvl(caseObj.getMexcLvl());
    // balp0d020.setChkDate(DateUtility.getNowWestDate());
    // balp0d020.setChkMan(empNo);
    // balp0d020.setExeMk("0");
    // balp0d020.setEvtIds(caseObj.getEvtIds());
    // balp0d020.setEvtIdnNo(caseObj.getEvtIdnNo());
    // balp0d020.setEvtBrDate(caseObj.getEvtBrDate());
    // balp0d020.setEvtName(caseObj.getEvtName());
    // balp0d020.setProcMk("0");
    // balp0d020.setCrtUser(empNo);
    // balp0d020.setCrtTime(DateUtility.getNowWestDateTime(true));
    // balp0d020Dao.insertDataForSurvivorPaymentReview(balp0d020);
    // }
    // }
    // }

    /**
     * 單筆資料審核確認儲存 - 修改 給付主檔/給付核定檔 var.2.00
     * 
     * @param apNo 受理編號
     * @param notifyForm 核定格式
     * @param chgNote 更正原因
     * @param baappbaseIdList 要修改的給付主檔ID清單
     * @param manchkMk 人工審核結果
     */
    public void updateSingleBeneficiaryDataForSurvivor(SurvivorPaymentReviewCase caseObj, String notifyForm, String chgNote, String manchkMk, List<SurvivorPaymentReviewCase> beneficiaryDataList, List<SurvivorPaymentReviewExtCase> benIssuDataList,
                    UserBean userData) {
        String apNo = caseObj.getApNo(); // 受理編號
        String issuYm = caseObj.getIssuYm();// 核定年月
        String empNo = userData.getEmpNo();// 員工編號
        String evtManchkMk = manchkMk;// 事故者人審核結果
        Map dataMap = new HashMap();

        Baappbase beforeBaappbase = new Baappbase();
        beforeBaappbase.setApNo(apNo);
        beforeBaappbase.setSeqNo(caseObj.getSeqNo());
        beforeBaappbase.setNotifyForm(caseObj.getNotifyForm());
        beforeBaappbase.setManchkMk(caseObj.getManchkMk());
        beforeBaappbase.setChgNote(caseObj.getChgNote());
        beforeBaappbase.setProcStat(caseObj.getProcStat());
        beforeBaappbase.setChkDate(caseObj.getChkDate());
        beforeBaappbase.setChkMan(caseObj.getChkMan());

        // 更新給付主檔相關欄位
        // [
        for (int i = 0; i < beneficiaryDataList.size(); i++) {
            SurvivorPaymentReviewCase dataCase = beneficiaryDataList.get(i);
            Baappbase baappbase = new Baappbase();
            baappbase.setApNo(apNo);
            baappbase.setSeqNo(dataCase.getSeqNo());
            baappbase.setNotifyForm(notifyForm);

            if (!("0000").equals(dataCase.getSeqNo())) {
                if (StringUtils.equals("N", evtManchkMk)) {
                    baappbase.setManchkMk("N");
                }
                else {
                    if (StringUtils.equals("N", dataCase.getAcceptMk())) {
                        baappbase.setManchkMk("N");
                    }
                    else {
                        baappbase.setManchkMk(manchkMk);
                    }
                }
            }
            else {
                baappbase.setManchkMk(evtManchkMk);
            }
            baappbase.setChgNote(chgNote);
            baappbase.setProcStat("20");
            baappbase.setChkDate(DateUtility.getNowWestDate());
            baappbase.setChkMan(empNo);
            baappbaseDao.updateDataForSurvivorPaymentReview(baappbase);

            dataMap.put(baappbase.getSeqNo(), baappbase.getManchkMk());

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBaappbase, baappbase);
            }
        }
        // ]

        // // 新增資料到 更正記錄檔
        // // [
        log.debug("Start Insert BAAPPLOG ...");
        BigDecimal baappbaseId = caseObj.getBaappbaseId();
        List<Baapplog> logList = caseObj.getBaapplogList();
        for (int i = 0; i < logList.size(); i++) {
            Baapplog baapplog = logList.get(i);
            baapplog.setBaappbaseId(baappbaseId);// 資料列編號

            baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            // baapplog.setUpCol(); // 異動項目 - Log 已設
            // // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
            // // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
            baapplogDao.insertData(baapplog);
        }
        log.debug("Insert BAAPPLOG Finished ...");
        // // ]

        // 判斷『給付主檔』目前的處理狀態是否可以更新
        // 「資料筆數」>0時，更新『給付核定檔』, 「資料筆數」<=0時，不更新『給付核定檔』
        Integer countValue1 = baappbaseDao.selectDataCount1ForSurvivorPaymentReview(apNo);
        if (countValue1 > 0) {
            // 更新事故者核定資料
            Badapr badapr = new Badapr();
            badapr.setApNo(apNo);
            badapr.setManchkMk(evtManchkMk);
            badaprDao.updateEvtDataForSurvivorPaymentReview(badapr);

            // 更新受款人核定資料
            for (int i = 0; i < benIssuDataList.size(); i++) {
                SurvivorPaymentReviewExtCase benIssuCase = benIssuDataList.get(i);
                if (StringUtils.isBlank(benIssuCase.getAcceptMk())) {
                    benIssuCase.setAcceptMk(" ");
                }
                String benManChkMk = (String) dataMap.get(benIssuCase.getSeqNo());

                badapr = new Badapr();
                badapr.setApNo(apNo);
                badapr.setSeqNo(benIssuCase.getSeqNo());
                badapr.setIssuYm(benIssuCase.getIssuYm());
                badapr.setPayYm(benIssuCase.getPayYm());
                if (StringUtils.equals("N", benManChkMk)) {
                    badapr.setManchkMk("N");
                }
                else {
                    if (StringUtils.equals("N", benIssuCase.getAcceptMk())) {
                        badapr.setManchkMk("N");
                    }
                    else {
                        badapr.setManchkMk(benManChkMk);
                    }
                }

                badaprDao.updateBenDataForSurvivorPaymentReview(badapr);
            }
        }

        // 更新『審核給付清單紀錄檔』相關欄位
        Balp0d020 balp0d020 = new Balp0d020();
        balp0d020.setProcMk("2");
        balp0d020.setApNo(apNo);
        balp0d020.setIssuYm(issuYm);
        balp0d020Dao.updateDataForReview(balp0d020);

        // 新增『審核給付清單紀錄檔』的記錄資料
        // 取得 判斷目前各給付年月是否已審核完成 之資料筆數條件
        Integer countValue2 = baappbaseDao.selectDataCount2ForSurvivorPaymentReview(apNo);

        // 「資料筆數」>0，不需新增記錄
        // 「資料筆數」=0時，且【事故者－「人工審核結果」】=Y或N時，才需新增記錄
        if (countValue2 == 0) {
            if (("Y").equals(evtManchkMk) || ("N").equals(evtManchkMk)) {
                balp0d020 = new Balp0d020();
                balp0d020.setPayCode("S");
                balp0d020.setApNo(caseObj.getApNo());
                balp0d020.setSeqNo("0000");
                balp0d020.setIssuYm(caseObj.getIssuYm());
                balp0d020.setVeriSeq(caseObj.getVeriSeq());
                balp0d020.setCaseTyp(caseObj.getCaseTyp());
                balp0d020.setMchkTyp(caseObj.getMchkTyp());
                balp0d020.setPaysYm(caseObj.getMinPayYm());
                balp0d020.setPayeYm(caseObj.getMaxPayYm());
                balp0d020.setTissueAmt(caseObj.getBefIssueAmt());
                balp0d020.setTaplPayAmt(caseObj.getAplPayAmt());
                balp0d020.setAcceptMk(caseObj.getAcceptMk());
                balp0d020.setManchkMk(evtManchkMk);
                balp0d020.setMexcLvl(caseObj.getMexcLvl());
                balp0d020.setChkDate(DateUtility.getNowWestDate());
                balp0d020.setChkMan(empNo);
                balp0d020.setExeMk("0");
                balp0d020.setEvtIds(caseObj.getEvtIds());
                balp0d020.setEvtIdnNo(caseObj.getEvtIdnNo());
                balp0d020.setEvtBrDate(caseObj.getEvtBrDate());
                balp0d020.setEvtName(caseObj.getEvtName());
                balp0d020.setProcMk("0");
                balp0d020.setCrtUser(empNo);
                balp0d020.setCrtTime(DateUtility.getNowWestDateTime(true));
                balp0d020Dao.insertDataForSurvivorPaymentReview(balp0d020);
            }
        }
    }

    /**
     * 批次審核確認儲存 - 修改 給付主檔
     * 
     * @param apNo 受理編號
     * @param userData 使用者資料
     */
    public void updateAllBeneficiaryDataForSurvivor(String indexList, UserBean userData, List dataList) {
        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");
        String[] tmpIndexList = indexList.split(",");
        String empNo = userData.getEmpNo();

        for (int i = 0; i < tmpIndexList.length; i++) {
            String dataIndex = tmpIndexList[i];
            if (StringUtils.isNotBlank(dataIndex)) {

                SurvivorPaymentReviewCase caseObj = (SurvivorPaymentReviewCase) dataList.get(Integer.parseInt(dataIndex));
                Baappbase beforeBaappbase = new Baappbase(); // 給付主檔改前值

                BeanUtility.copyProperties(beforeBaappbase, caseObj);

                // 更新給付主檔相關欄位(以案來看,不以人來看)
                Baappbase obj = new Baappbase();
                obj.setManchkMk(caseObj.getManchkMk());
                obj.setProcStat("20");
                obj.setChkMan(empNo);
                obj.setChkDate(DateUtility.getNowWestDate());
                obj.setApNo(caseObj.getApNo());
                baappbaseDao.updateDataForReview(obj);

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                }

                // 更新給付核定檔相關欄位
                Integer q3 = baappbaseDao.selectDataCountForReviewUpdate(caseObj.getApNo());
                if (q3 > 0) {// q3<=0時不更新Badapr
                    Badapr badapr = new Badapr();
                    badapr.setManchkMk(caseObj.getManchkMk());
                    badapr.setApNo(caseObj.getApNo());
                    badapr.setIssuYm(caseObj.getIssuYm());
                    badapr.setMtestMk(caseObj.getMtestMk());
                    badaprDao.updateDataForReview(badapr);
                }

                // 更新審核給付清單紀錄檔
                Balp0d020 balp0d020 = new Balp0d020();
                balp0d020.setProcMk("2");
                balp0d020.setApNo(caseObj.getApNo());
                balp0d020.setIssuYm(caseObj.getIssuYm());
                balp0d020Dao.updateDataForReview(balp0d020);

                // 新增審核給付清單紀錄檔
                if (("Y").equals(caseObj.getManchkMk()) || ("N").equals(caseObj.getManchkMk())) {
                    balp0d020 = new Balp0d020();
                    balp0d020.setPayCode("K");
                    balp0d020.setApNo(caseObj.getApNo());
                    balp0d020.setSeqNo("0000");
                    balp0d020.setIssuYm(caseObj.getIssuYm());
                    balp0d020.setVeriSeq(caseObj.getVeriSeq());
                    balp0d020.setCaseTyp(caseObj.getCaseTyp());
                    balp0d020.setMchkTyp(caseObj.getMchkTyp());
                    balp0d020.setPaysYm(caseObj.getPaysYm());
                    balp0d020.setPayeYm(caseObj.getPayeYm());
                    balp0d020.setTissueAmt(caseObj.getTissueAmt());
                    balp0d020.setTaplPayAmt(caseObj.getTaplPayAmt());
                    balp0d020.setMexcLvl(caseObj.getMexcLvl());
                    balp0d020.setChkDate(DateUtility.getNowWestDate());
                    balp0d020.setChkMan(empNo);
                    balp0d020.setExeMk("0");
                    balp0d020.setEvtIds(caseObj.getEvtIds());
                    balp0d020.setEvtIdnNo(caseObj.getEvtIdnNo());
                    balp0d020.setEvtBrDate(caseObj.getEvtBrDate());
                    balp0d020.setEvtName(caseObj.getEvtName());
                    balp0d020.setBenIds(caseObj.getBenIds());
                    balp0d020.setBenIdnNo(caseObj.getBenIdnNo());
                    balp0d020.setBenBrDate(caseObj.getBenBrDate());
                    balp0d020.setBenName(caseObj.getBenName());
                    balp0d020.setProcMk("0");
                    balp0d020.setCrtUser(empNo);
                    balp0d020.setCrtTime(DateUtility.getNowWestDateTime(true));
                    balp0d020.setAcceptMk(caseObj.getAcceptMk());
                    balp0d020.setManchkMk(caseObj.getManchkMk());
                    balp0d020Dao.insertDataForReview(balp0d020);
                }
            }
        }
        log.debug("Update BAAPPBASE Finished...");
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付年月下拉選單 FOR 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含<code>Badapr</code> 物件的List
     */
    public List<Badapr> getPayYmOptionListForSurvivor(String apNo, String issuYm) {
        return badaprDao.selectPayYmListForSurvivorPaymentReview(apNo, issuYm);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人下拉選單 FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public List<Baappbase> getSeqNoOptionListForSurvivor(String apNo) {
        return baappbaseDao.selectSeqNoListForSurvivorPaymentReview(apNo);
    }

    /**
     * 設定 事故者 人工審核radio button
     * 
     * @param caseObj 事故者資料
     * @return <code>SurvivorPaymentReviewCase</code>
     */
    public SurvivorPaymentReviewCase setEvtDataManchkMkCtl(SurvivorPaymentReviewCase caseObj, Integer q1, Integer q2) {
        // 人工審核radio button預設值
        caseObj.setManchkMkCtlY("Y");
        caseObj.setManchkMkCtlN("Y");
        caseObj.setManchkMkCtlSpace("Y");
        if ((q2 > 0) && (!caseObj.getCaseTyp().equals("3") && !caseObj.getCaseTyp().equals("6"))) {
            caseObj.setManchkMkCtlY("N");
            caseObj.setManchkMkCtlN("N");
            caseObj.setManchkMkCtlSpace("N");
        }
        else {
            if (q1 > 0) {
                caseObj.setManchkMk("N");
                caseObj.setManchkMkCtlY("N");
                caseObj.setManchkMkCtlSpace("N");
            }
            else {
                if (("N").equals(caseObj.getAcceptMk())) {
                    caseObj.setManchkMk("N");
                    caseObj.setManchkMkCtlY("N");
                    caseObj.setManchkMkCtlSpace("N");
                }
                else {
                    if (("Y").equals(caseObj.getManchkMk())) {
                        caseObj.setManchkMk("Y");
                    }
                    else if (("N").equals(caseObj.getManchkMk())) {
                        caseObj.setManchkMk("N");
                    }
                    else if (StringUtils.isBlank(caseObj.getManchkMk()) && ("Y").equals(caseObj.getAcceptMk())) {
                        caseObj.setManchkMk("Y");
                    }
                    else if (StringUtils.isBlank(caseObj.getManchkMk()) && StringUtils.isBlank(caseObj.getAcceptMk())) {
                        caseObj.setManchkMk(" ");
                    }
                }
            }
        }
        return caseObj;
    }

    /**
     * 設定 受款人 人工審核radio button
     * 
     * @param evtCaseObj 事故者資料
     * @param benCaseObj 受款人資料
     * @return <code>SurvivorPaymentReviewCase</code>
     */
    public SurvivorPaymentReviewCase setBenDataManchkMkCtl(SurvivorPaymentReviewCase evtCaseObj, SurvivorPaymentReviewCase benCaseObj, String benManchkMk, Map<String, String> benChgManchkMkMap) {
        // 事故者資料
        String manchkMkDefValue = evtCaseObj.getManchkMk();
        // if(StringUtils.isNotEmpty(benManchkMk)){
        // manchkMkDefValue = benManchkMk;
        // }
        String manchkMkCtlY = evtCaseObj.getManchkMkCtlY();
        String ManchkMkCtlN = evtCaseObj.getManchkMkCtlN();
        String ManchkMkCtlSpace = evtCaseObj.getManchkMkCtlSpace();

        // 人工審核radio button預設值
        benCaseObj.setManchkMkCtlY("Y");
        benCaseObj.setManchkMkCtlN("Y");
        benCaseObj.setManchkMkCtlSpace("Y");

        if (benChgManchkMkMap != null) {
            benCaseObj.setManchkMk(benChgManchkMkMap.get(benCaseObj.getSeqNo()));
        }

        if (("Y").equals(manchkMkDefValue)) {
            if (("N").equals(benCaseObj.getAcceptMk())) {
                benCaseObj.setManchkMk("N");
                benCaseObj.setManchkMkCtlY("N");
                benCaseObj.setManchkMkCtlSpace("N");
            }
            else {
                if (("Y").equals(benCaseObj.getManchkMk())) {
                    benCaseObj.setManchkMk("Y");
                }
                else if (("N").equals(benCaseObj.getManchkMk())) {
                    benCaseObj.setManchkMk("N");
                }
                else if (StringUtils.isBlank(benCaseObj.getManchkMk()) && ("Y").equals(benCaseObj.getAcceptMk())) {
                    benCaseObj.setManchkMk("Y");
                }
                else if (StringUtils.isBlank(benCaseObj.getManchkMk()) && StringUtils.isBlank(benCaseObj.getAcceptMk())) {
                    benCaseObj.setManchkMk(" ");
                }
            }
        }
        else if (("N").equals(manchkMkDefValue)) {
            benCaseObj.setManchkMk("N");
            benCaseObj.setManchkMkCtlY("N");
            benCaseObj.setManchkMkCtlSpace("N");
        }
        else {
            benCaseObj.setManchkMk(" ");
            benCaseObj.setManchkMkCtlY("N");
            benCaseObj.setManchkMkCtlN("N");
        }
        return benCaseObj;
    }

    /**
     * 設定 受款人核定資料 人工審核radio button
     * 
     * @param beneficiaryDataMap 事故者/受款人資料Map
     * @param benIssuDataCase 受款人核定資料
     * @return <code>SurvivorPaymentReviewExtCase</code>
     */
    public SurvivorPaymentReviewExtCase setBenIssuDataManchkMkCtl(Map<String, SurvivorPaymentReviewCase> beneficiaryDataMap, SurvivorPaymentReviewExtCase benIssuDataCase) {
        if (beneficiaryDataMap.size() != 0) {
            // 受款人資料
            SurvivorPaymentReviewCase benCase = beneficiaryDataMap.get(benIssuDataCase.getSeqNo());
            String manchkMkDefValue = benCase.getManchkMk();
            String manchkMkCtlY = benCase.getManchkMkCtlY();
            String ManchkMkCtlN = benCase.getManchkMkCtlN();
            String ManchkMkCtlSpace = benCase.getManchkMkCtlSpace();

            // 人工審核radio button預設值
            benIssuDataCase.setManchkMkCtlY("Y");
            benIssuDataCase.setManchkMkCtlN("Y");
            benIssuDataCase.setManchkMkCtlSpace("Y");

            if (("Y").equals(manchkMkDefValue)) {
                if (("N").equals(benIssuDataCase.getAcceptMk())) {
                    benIssuDataCase.setManchkMk("N");
                    benIssuDataCase.setManchkMkCtlY("N");
                    benIssuDataCase.setManchkMkCtlSpace("N");
                }
                else {
                    // if (("Y").equals(benIssuDataCase.getManchkMk())) {
                    // benIssuDataCase.setManchkMk("Y");
                    // }
                    // else if (("N").equals(benIssuDataCase.getManchkMk())) {
                    // benIssuDataCase.setManchkMk("N");
                    // }
                    // else if (StringUtils.isBlank(benIssuDataCase.getManchkMk()) && ("Y").equals(benIssuDataCase.getAcceptMk())) {
                    // benIssuDataCase.setManchkMk("Y");
                    // }
                    // else if (StringUtils.isBlank(benIssuDataCase.getManchkMk()) && StringUtils.isBlank(benIssuDataCase.getAcceptMk())) {
                    // benIssuDataCase.setManchkMk(" ");
                    // }
                    benIssuDataCase.setManchkMk("Y");
                }
            }
            else if (("N").equals(manchkMkDefValue)) {
                benIssuDataCase.setManchkMk("N");
                benIssuDataCase.setManchkMkCtlY("N");
                benIssuDataCase.setManchkMkCtlSpace("N");
            }
            else {
                if (("N").equals(benIssuDataCase.getAcceptMk())) {
                    benIssuDataCase.setManchkMk("N");
                    benIssuDataCase.setManchkMkCtlY("N");
                    benIssuDataCase.setManchkMkCtlSpace("N");
                }
                else {
                    benIssuDataCase.setManchkMk(" ");
                    benIssuDataCase.setManchkMkCtlY("N");
                    benIssuDataCase.setManchkMkCtlN("N");
                }
            }
        }

        return benIssuDataCase;
    }

    // ------------------------------ 遺屬年金給付審核 ------------------------------

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setBaexalistDao(BaexalistDao baexalistDao) {
        this.baexalistDao = baexalistDao;
    }

    public void setMaadmrecDao(MaadmrecDao maadmrecDao) {
        this.maadmrecDao = maadmrecDao;
    }

    public void setBachkfileDao(BachkfileDao bachkfileDao) {
        this.bachkfileDao = bachkfileDao;
    }

    public void setBasenimaintDao(BasenimaintDao basenimaintDao) {
        this.basenimaintDao = basenimaintDao;
    }

    public void setCiptDao(CiptDao ciptDao) {
        this.ciptDao = ciptDao;
    }

    public void setBadaprDao(BadaprDao badaprDao) {
        this.badaprDao = badaprDao;
    }

    public void setBaoncepayDao(BaoncepayDao baoncepayDao) {
        this.baoncepayDao = baoncepayDao;
    }

    public void setPbbmsaDao(PbbmsaDao pbbmsaDao) {
        this.pbbmsaDao = pbbmsaDao;
    }

    public void setBirefDao(BirefDao birefDao) {
        this.birefDao = birefDao;
    }

    public void setNbappbaseDao(NbappbaseDao nbappbaseDao) {
        this.nbappbaseDao = nbappbaseDao;
    }

    public void setBanotifyDao(BanotifyDao banotifyDao) {
        this.banotifyDao = banotifyDao;
    }

    public void setBalp0d020Dao(Balp0d020Dao balp0d020Dao) {
        this.balp0d020Dao = balp0d020Dao;
    }

    public void setBaappexpandDao(BaappexpandDao baappexpandDao) {
        this.baappexpandDao = baappexpandDao;
    }

    public void setBbcmf07Dao(Bbcmf07Dao bbcmf07Dao) {
        this.bbcmf07Dao = bbcmf07Dao;
    }

    public void setBaapplogDao(BaapplogDao baapplogDao) {
        this.baapplogDao = baapplogDao;
    }
}
