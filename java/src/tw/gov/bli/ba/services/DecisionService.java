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
import tw.gov.bli.ba.dao.BaarclistDao;
import tw.gov.bli.ba.dao.BachkfileDao;
import tw.gov.bli.ba.dao.BadaprDao;
import tw.gov.bli.ba.dao.BaexalistDao;
import tw.gov.bli.ba.dao.Balp0d020Dao;
import tw.gov.bli.ba.dao.BanotifyDao;
import tw.gov.bli.ba.dao.BaoncepayDao;
import tw.gov.bli.ba.dao.BaparamDao;
import tw.gov.bli.ba.dao.BasenimaintDao;
import tw.gov.bli.ba.dao.Bbcmf07Dao;
import tw.gov.bli.ba.dao.BirefDao;
import tw.gov.bli.ba.dao.CiptDao;
import tw.gov.bli.ba.dao.MaadmrecDao;
import tw.gov.bli.ba.dao.NbappbaseDao;
import tw.gov.bli.ba.dao.PbbmsaDao;
import tw.gov.bli.ba.decision.cases.ChkFileCase;
import tw.gov.bli.ba.decision.cases.DisabledPaymentDecisionCase;
import tw.gov.bli.ba.decision.cases.DisabledPaymentDecisionExtCase;
import tw.gov.bli.ba.decision.cases.PaymentDecisionCase;
import tw.gov.bli.ba.decision.cases.PaymentDecisionExtCase;
import tw.gov.bli.ba.decision.cases.SurvivorPaymentDecisionCase;
import tw.gov.bli.ba.decision.cases.SurvivorPaymentDecisionExtCase;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.domain.Bachkfile;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.domain.Balp0d020;
import tw.gov.bli.ba.domain.Banotify;
import tw.gov.bli.ba.domain.Baoncepay;
import tw.gov.bli.ba.domain.Baparam;
import tw.gov.bli.ba.domain.Basenimaint;
import tw.gov.bli.ba.domain.Biref;
import tw.gov.bli.ba.domain.Cipt;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.domain.Nbappbase;
import tw.gov.bli.ba.domain.Pbbmsa;
import tw.gov.bli.ba.framework.domain.UserBean;
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
public class DecisionService {
    private static Log log = LogFactory.getLog(DecisionService.class);

    private BaappbaseDao baappbaseDao;
    private BaexalistDao baexalistDao;
    private MaadmrecDao maadmrecDao;
    private BachkfileDao bachkfileDao;
    private BasenimaintDao basenimaintDao;
    private CiptDao ciptDao;
    private BadaprDao badaprDao;
    private BaparamDao baparamDao;
    private BaoncepayDao baoncepayDao;
    private PbbmsaDao pbbmsaDao;
    private BirefDao birefDao;
    private NbappbaseDao nbappbaseDao;
    private BanotifyDao banotifyDao;
    private Balp0d020Dao balp0d020Dao;
    private BaappexpandDao baappexpandDao;
    private Bbcmf07Dao bbcmf07Dao;
    private BaarclistDao baarclistDao;
    private BaapplogDao baapplogDao;

    // ------------------------------ 老年年金給付決行 ------------------------------
    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 給付決行資料
     * 
     * @param apNo 受理編號
     * @param empId 員工編號
     * @return 內含 <code>PaymentDecisionCase</code> 物件的 List
     */
    public List<PaymentDecisionCase> getDecisionDataListByApNo(String apNo, String empId) {
        List<Baappbase> list = baappbaseDao.selectDecisionDataByApNo(apNo, empId, ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            PaymentDecisionCase caseObj = new PaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);

            // [複核]及[決行]的radio 預設隱藏
            caseObj.setIsShowReChk("N");
            caseObj.setIsShowExeMk("N");

            // 當EXEMK>=MEXCLVL,[複核]及[決行]的radio都要顯示
            if (Integer.parseInt(caseObj.getExeMk()) >= Integer.parseInt(caseObj.getMexcLvl())) {
                caseObj.setIsShowReChk("Y");
                caseObj.setIsShowExeMk("Y");
            }
            // 當EXEMK<MEXCLVL,且RECHK=Y,[複核]的radio要顯示,[決行]的radio隱藏
            else {
                if (("Y").equals(StringUtils.upperCase(caseObj.getReChk()))) {
                    caseObj.setIsShowReChk("Y");
                }
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依入條件取得 審核決行清單紀錄檔(<code>BAAPPBASE</code>) 給付決行資料
     * 
     * @param rptDate 列印日期
     * @param pageNo 頁次
     * @param chkMan 審核人員
     * @param empId 員工編號
     * @return 內含 <code>PaymentDecisionCase</code> 物件的 List
     */
    public List<PaymentDecisionCase> selectDecisionDataByRptDate(String rptDate, BigDecimal pageNo, String chkMan, String empId) {
        List<Baappbase> list = baappbaseDao.selectDecisionDataByRptDate(rptDate, pageNo, chkMan, empId, ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            PaymentDecisionCase caseObj = new PaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);

            // [複核]及[決行]的radio 預設隱藏
            caseObj.setIsShowReChk("N");
            caseObj.setIsShowExeMk("N");

            // 當EXEMK>=MEXCLVL,[複核]及[決行]的radio都要顯示
            if (Integer.parseInt(caseObj.getExeMk()) >= Integer.parseInt(caseObj.getMexcLvl())) {
                caseObj.setIsShowReChk("Y");
                caseObj.setIsShowExeMk("Y");
            }
            // 當EXEMK<MEXCLVL,且RECHK=Y,[複核]的radio要顯示,[決行]的radio隱藏
            else {
                if (("Y").equals(StringUtils.upperCase(caseObj.getReChk()))) {
                    caseObj.setIsShowReChk("Y");
                }
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 給付決行
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>PaymentDecisionCase</code> 物件的 List
     */
    public List<PaymentDecisionCase> selectDetailDecisionData(String apNo) {
        List<Baappbase> list = baappbaseDao.selectDataForReviewBy(apNo, "0000", new String[] { "20", "30", "40" }, "in", null, null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            PaymentDecisionCase caseObj = new PaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);

            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            // 更正原因
            List<Baparam> baparamList = baparamDao.selectListBy(null, "CHGNOTE", caseObj.getChgNote());
            if (baparamList.size() == 1) {
                caseObj.setChgNote(baparamList.get(0).getParamName());
            }
            else {
                caseObj.setChgNote("");
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    // /**
    // * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 註記資料
    // *
    // * @param apNo 受理編號
    // * @param selectType 查詢函類別 1=交叉函; 2=不給付函; 3=照會函; 4=補件通知函; 5=其他簽函
    // * @return
    // */
    // public String getLetterTypeMk(String apNo, String selectType) {
    // String closeDate = "LPAD(TO_NUMBER(SUBSTR(Y.CLOSDATE,0,4)-1911),3,'0')||SUBSTR(Y.CLOSDATE,5,8)";
    // String proDate = "LPAD(TO_NUMBER(SUBSTR(Y.PRODATE,0,4)-1911),3,'0')||SUBSTR(Y.PRODATE,5,8)";
    // String seqNo = "0000";
    // String procStat = "('10','11','12','13')";
    // String proDateDecode = "";
    // String letterType = "";
    // String proDateCond = "";
    // String eqType1 = "in";
    // String eqType2 = "";
    // String eqType3 = "";
    // if (("1").equals(selectType)) {
    // proDateDecode = "DECODE(NVL(TRIM(" + closeDate + "),'P'),'P',('發函'||" + proDate + "),('回覆'||" + closeDate + "))";
    // letterType = "16";
    // proDateCond = "1";
    // eqType2 = null;
    // eqType3 = null;
    // }
    // else if (("2").equals(selectType)) {
    // proDateDecode = proDate;
    // letterType = "21";
    // proDateCond = "1";
    // eqType2 = null;
    // eqType3 = null;
    // }
    // else if (("3").equals(selectType)) {
    // proDateDecode = "DECODE(NVL(TRIM(" + closeDate + "),'P'),'P',('發函'||" + proDate + "),('回覆'||" + closeDate + "))";
    // letterType = "('14','15','19')";
    // proDateCond = "1";
    // eqType2 = "in";
    // eqType3 = "in";
    // }
    // else if (("4").equals(selectType)) {
    // proDateDecode = "DECODE(Y.LETTERTYPE,'11',(DECODE(NVL(TRIM(" + closeDate + "),'P'),'P',('通知'||" + proDate + "),('回覆'||" + closeDate + "))),(DECODE(NVL(TRIM(" + closeDate + "),'P'),'P',('催辦'||" + proDate + "),('回覆'||" + closeDate + "))))";
    // letterType = "('11','12')";
    // proDateCond = "2";
    // eqType2 = "in";
    // eqType3 = "in";
    // }
    // else if (("5").equals(selectType)) {
    // proDateDecode = "DECODE(NVL(TRIM(" + closeDate + "),'P'),'P',('發函'||" + proDate + "),('回覆'||" + closeDate + "))";
    // letterType = "('17','22','23','31','40')";
    // proDateCond = "1";
    // eqType2 = "in";
    // eqType3 = "in";
    // }
    //
    // return maadmrecDao.selectProDateForPaymentBy(apNo, seqNo, procStat, proDateDecode, letterType, proDateCond, eqType1, eqType2, eqType3);
    // }

    // /**
    // * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料
    // *
    // * @param apNo 受理編號
    // * @return
    // */
    // public PaymentDecisionCase getLetterTypeMk(PaymentDecisionCase caseObj) {
    // String apNo = caseObj.getApNo();
    //
    // // 交查函註記
    // Maadmrec mk1 = getLetterTypeMk1(apNo);
    // caseObj.setLetterTypeMk1(mk1.getProDate());
    // caseObj.setLetterTypeMk1Title(mk1.getNdomk1());
    //
    // // 不給付函註記
    // Maadmrec mk2 = getLetterTypeMk2(apNo);
    // caseObj.setLetterTypeMk2(mk2.getProDate());
    // caseObj.setLetterTypeMk2Title(mk2.getNdomk1());
    //
    // // 補件通知函註記
    // Maadmrec mk3 = getLetterTypeMk3(apNo);
    // caseObj.setLetterTypeMk3(mk3.getProDate());
    // caseObj.setLetterTypeMk3Title(mk3.getNdomk1());
    //
    // // 照會函註記
    // Maadmrec mk4 = getLetterTypeMk4(apNo);
    // caseObj.setLetterTypeMk4(mk4.getProDate());
    // caseObj.setLetterTypeMk4Title(mk4.getNdomk1());
    //
    // // 其他簽函註記
    // Maadmrec mk5 = getLetterTypeMk5(apNo);
    // caseObj.setLetterTypeMk5(mk5.getProDate());
    // caseObj.setLetterTypeMk5Title(mk5.getNdomk1());
    //
    // return caseObj;
    // }
    //
    // /**
    // * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料
    // *
    // * @param apNo 受理編號
    // * @return
    // */
    // public Maadmrec getLetterTypeMk1(String apNo) {
    // List<Maadmrec> list = maadmrecDao.selectLetterTypeMk1(apNo);
    // Maadmrec obj = new Maadmrec();
    // if (list.size() == 1) {
    // obj = list.get(0);
    // }
    // return obj;
    // }
    //
    // /**
    // * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料
    // *
    // * @param apNo 受理編號
    // * @return
    // */
    // public Maadmrec getLetterTypeMk2(String apNo) {
    // List<Maadmrec> list = maadmrecDao.selectLetterTypeMk2(apNo);
    // Maadmrec obj = new Maadmrec();
    // if (list.size() == 1) {
    // obj = list.get(0);
    // }
    // return obj;
    // }
    //
    // /**
    // * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料
    // *
    // * @param apNo 受理編號
    // * @return
    // */
    // public Maadmrec getLetterTypeMk3(String apNo) {
    // List<Maadmrec> list = maadmrecDao.selectLetterTypeMk3(apNo);
    // Maadmrec obj = new Maadmrec();
    // if (list.size() == 1) {
    // obj = list.get(0);
    // }
    // return obj;
    // }
    //
    // /**
    // * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料
    // *
    // * @param apNo 受理編號
    // * @return
    // */
    // public Maadmrec getLetterTypeMk4(String apNo) {
    // List<Maadmrec> list = maadmrecDao.selectLetterTypeMk4(apNo);
    // Maadmrec obj = new Maadmrec();
    // if (list.size() == 1) {
    // obj = list.get(0);
    // }
    // return obj;
    // }
    //
    // /**
    // * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料
    // *
    // * @param apNo 受理編號
    // * @return
    // */
    // public Maadmrec getLetterTypeMk5(String apNo) {
    // List<Maadmrec> list = maadmrecDao.selectLetterTypeMk5(apNo);
    // Maadmrec obj = new Maadmrec();
    // if (list.size() == 1) {
    // obj = list.get(0);
    // }
    // return obj;
    // }
    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<PaymentDecisionCase> getLetterTypeMk1List(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk1(apNo);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            PaymentDecisionCase caseObj = new PaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<PaymentDecisionCase> getLetterTypeMk2List(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk2(apNo);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            PaymentDecisionCase caseObj = new PaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<PaymentDecisionCase> getLetterTypeMk3List(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk3(apNo);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            PaymentDecisionCase caseObj = new PaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<PaymentDecisionCase> getLetterTypeMk4List(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk4(apNo);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            PaymentDecisionCase caseObj = new PaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<PaymentDecisionCase> getLetterTypeMk5List(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk5(apNo);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            PaymentDecisionCase caseObj = new PaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    public PaymentDecisionCase getLetterTypeMk6List(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk6(apNo);
        PaymentDecisionCase caseObj = new PaymentDecisionCase();
        if (list.size() >= 1) {
            Maadmrec obj = list.get(0);
            BeanUtility.copyProperties(caseObj, obj);

            String kind = getLetterTypeMk6Kind(obj.getReliefKind());
            String stat = getLetterTypeMk6Stat(obj.getReliefKind(), obj.getReliefStat());
            caseObj.setReliefKind(kind);
            caseObj.setReliefStat(stat);
        }

        return caseObj;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 救濟種類資料
     * 
     * @param reliefKind 救濟種類
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6Kind(String reliefKind) {

        return maadmrecDao.selectLetterTypeMk6Kind(reliefKind);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟辦理情形資料
     * 
     * @param reliefKind 救濟種類
     * @param reliefStat 行政救濟辦理情形
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6Stat(String reliefKind, String reliefStat) {

        return maadmrecDao.selectLetterTypeMk6Stat(reliefKind, reliefStat);
    }

    // /**
    // * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料
    // *
    // * @param apNo 受理編號
    // * @param seqNo 序號
    // * @param chkTyp 編審註記種類 (A:事故者編審註記, B:眷屬編審註記, C:符合註記)
    // * @return <code>PaymentDecisionExtCase</code> 物件 List
    // */
    // @SuppressWarnings("unchecked")
    // public List<PaymentDecisionExtCase> getPaymentReviewChkList(String apNo, String seqNo) {
    // List<Bachkfile> list = bachkfileDao.selectReviewChkListBy(apNo, seqNo, "A", null);
    // List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
    // Map<String, Object> returnMap = new HashMap<String, Object>();
    //
    // // 建立資料Map
    // // Map的Key 為 issuYm+payYm
    // // Value則是同為該 issuYm+payYm 下的所有編審註記資料
    // Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
    // for (int i = 0; i < list.size(); i++) {
    // Bachkfile obj = (Bachkfile) list.get(i);
    // map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
    // }
    //
    // // 當編審註記有出現BD時,需顯示'戶籍姓名'欄位(Disabled)
    // for (int i = 0; i < list.size(); i++) {
    // Bachkfile obj = (Bachkfile) list.get(i);
    // ChkFileCase caseObj = new ChkFileCase();
    // BeanUtility.copyProperties(caseObj, obj);
    // (map.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(caseObj);
    // }
    //
    // // 將 分類好的 map 轉為 list
    // for (Iterator it = map.keySet().iterator(); it.hasNext();) {
    // String key = (String) it.next();
    // List<ChkFileCase> tempList = map.get(key);
    // PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
    // caseObj.setIssuPayYm(key);
    // caseObj.setChkFileList(tempList);
    // caseObj.setChkFileDataSize(tempList.size());
    // returnList.add(caseObj);
    // }
    // return returnList;
    // }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審註記資料 for 老年年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>PaymentReviewExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<PaymentDecisionExtCase> getOldAgePaymentDecisionEvtChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewEvtChkListForOldAge(apNo);
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
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
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
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
     * @return <code>PaymentDecisionCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<PaymentDecisionCase> getOldAgePaymentDecisionBenChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewBenChkListForOldAge(apNo);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();
        List<PaymentDecisionExtCase> subList = new ArrayList<PaymentDecisionExtCase>();
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
            subList = new ArrayList<PaymentDecisionExtCase>();
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
                PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
                caseObj.setIssuPayYm(subKey);
                caseObj.setChkFileList(subTempList);
                caseObj.setChkFileDataSize(subTempList.size());
                subList.add(caseObj);
            }
            if (subTempList.size() != 0) {
                PaymentDecisionCase masterCase = new PaymentDecisionCase();
                masterCase.setSeqNo(key);
                masterCase.setOtherChkDataList(subList);
                returnList.add(masterCase);
            }
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人清單
     * 
     * @param apNo 受理編號
     * @param benPayMk 受益人領取狀態註記
     * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
     */
    public List<PaymentDecisionExtCase> getBenNameList(String apNo, String benPayMk) {
        List<String> list = baappbaseDao.selectBenNameBy(apNo, benPayMk);
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
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
     * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
     */
    public List<PaymentDecisionExtCase> getSenimaintData(String apNo, String seniTyp) {
        List<Basenimaint> list = basenimaintDao.selectDataBy(apNo, null, seniTyp);
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Basenimaint obj = list.get(i);
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
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
     * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
     */
    public List<PaymentDecisionExtCase> getCiptData(String apNo, String idn, String inTyp) {
        List<Cipt> list = ciptDao.selectTxcdDataBy(apNo, "0000", idn, inTyp);
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Cipt obj = list.get(i);
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @param issuym 核定年月
     * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
     */
    public List<PaymentDecisionExtCase> getPayData(String apNo, String issuYm) {
        List<Badapr> list = badaprDao.selectPayDataBy(apNo, issuYm);
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Badapr obj = list.get(i);
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
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
     * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
     */
    public PaymentDecisionExtCase getOncePayData(String apNo, String issuYm) {
        List<Baappbase> baappbaseList = baappbaseDao.selectOncePayDataBy(apNo);
        List<Badapr> badaprList = badaprDao.selectOncePayDataBy(apNo, issuYm);
        List<Baoncepay> baoncepayList = baoncepayDao.selectOncePayDataBy(apNo);

        PaymentDecisionExtCase returnCase = new PaymentDecisionExtCase();

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
     * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
     */
    public List<PaymentDecisionExtCase> getOncePayModifyData(String apNo) {
        List<Basenimaint> list = basenimaintDao.selectDataBy(apNo, null, "1");
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Basenimaint obj = list.get(i);
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
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
     * @return <code>PaymentDecisionExtCase</code> 物件
     */
    public PaymentDecisionExtCase getNotifyData(PaymentDecisionCase caseObj) {
        String apNo = caseObj.getApNo();
        String notifyForm = caseObj.getNotifyForm();
        Baappbase evtData = new Baappbase();
        BeanUtility.copyProperties(evtData, caseObj);

        PaymentDecisionExtCase returnCase = null;

        // 取得 核定通知書格式檔 (BANOTIFY 核定通知書資料)
        // [
        // a. 若核定通知書格式為 999 或為 空白 及 NULL, 則整段核定通知書不顯示
        // b. 若查無受理案件核定通知書格式, 則整段核定通知書不顯示
        if (StringUtils.isNotBlank(notifyForm) && !StringUtils.equals(notifyForm, "999")) {
            returnCase = new PaymentDecisionExtCase();
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
     * @return <code>PaymentDecisionExtCase</code> 物件
     */
    public PaymentDecisionExtCase getOncePayDetailData(String evtIdnNo, String evtBrDate) {
        List<Pbbmsa> list = pbbmsaDao.selectOncePayDataForPaymentBy(evtIdnNo, evtBrDate);
        PaymentDecisionExtCase caseObj = null;
        if (list.size() == 1) {
            Pbbmsa obj = list.get(0);
            caseObj = new PaymentDecisionExtCase();
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
     * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
     */
    public List<PaymentDecisionExtCase> getAnnuityPayData(String apNo, String evtIdnNo, String evtBrDate) {
        List<Baappbase> list = baappbaseDao.selectAnnuityPayDataBy(apNo, evtIdnNo, evtBrDate);
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
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
     * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
     */
    public List<PaymentDecisionExtCase> getCriPayApplyData(String evtIdnNo, String evtBrDate) {
        List<Pbbmsa> list = pbbmsaDao.selectCriPayApplyDataForPaymentBy(evtIdnNo, evtBrDate);
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Pbbmsa obj = list.get(i);
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
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
     * @return <code>PaymentDecisionExtCase</code> 物件
     */
    public PaymentDecisionExtCase getDiePayApplyData(String evtIdnNo, String evtBrDate) {
        List<Pbbmsa> list = pbbmsaDao.selectDiePayApplyDataForPaymentBy(evtIdnNo, evtBrDate);
        PaymentDecisionExtCase caseObj = null;
        if (list.size() == 1) {
            Pbbmsa obj = list.get(0);
            caseObj = new PaymentDecisionExtCase();
            BeanUtility.copyProperties(caseObj, obj);
        }
        return caseObj;
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 請領同類／他類／另案扣減 - 申請傷病給付紀錄資料
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
     */
    public List<PaymentDecisionExtCase> getInjPayApplyData(String evtIdnNo, String evtBrDate) {
        List<Pbbmsa> list = pbbmsaDao.selectInjPayApplyDataForPaymentBy(evtIdnNo, evtBrDate);
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Pbbmsa obj = list.get(i);
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
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
     * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
     */
    public List<PaymentDecisionExtCase> getUnEmpPayApplyData(String evtIdnNo, String evtBrDate) {
        List<Biref> list = birefDao.selectUnEmpDataBy(evtIdnNo, evtBrDate);
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Biref obj = list.get(i);
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 申請失業給付紀錄資料
     * 
     * @param idNo 身分證號
     * @param brth 出生日期
     * @return 內含 <code>PaymentDecisionExtCase</code> 物件的 List
     */
    public List<PaymentDecisionExtCase> getUnEmpData(String idNo, String brth) {
        List<Biref> list = birefDao.selectUnEmpDataBy(idNo, brth);
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Biref obj = list.get(i);
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付紀錄資料
     * 
     * @param evtIds 事故者社福識別碼
     * @return <code>PaymentDecisionExtCase</code> 物件
     */
    public PaymentDecisionExtCase getNpPayApplyData(String evtIds) {
        List<Nbappbase> list = nbappbaseDao.selectNpPayDataBy(evtIds);
        PaymentDecisionExtCase caseObj = null;
        if (list.size() == 1) {
            Nbappbase obj = list.get(0);
            caseObj = new PaymentDecisionExtCase();
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
    public Baappbase gettReviewCond(String apNo) {
        return baappbaseDao.selectReviewCond(apNo, "0000");
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料、受款人編審註記資料 FOR 給付審核
     * 
     * @param apNo 受理編號
     * @return 內含 <code>PaymentDecisionCase</code> 物件的 List
     */
    public List<PaymentDecisionCase> getAllBenData(String apNo) {
        List<PaymentDecisionCase> beneficiaryDataList = getBeneficiaryDataList(apNo);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();

        for (int i = 0; i < beneficiaryDataList.size(); i++) {
            PaymentDecisionCase obj = beneficiaryDataList.get(i);
            apNo = obj.getApNo();
            String seqNo = obj.getSeqNo();

            PaymentDecisionCase caseObj = new PaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            // caseObj.setBenIssueDataList(getBenIssueDataList(apNo, seqNo));
            caseObj.setBenChkDataList(getBenChkDataList(apNo, seqNo));
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料、受款人核定資料 FOR 給付審核
     * 
     * @param apNo 受理編號
     * @return 內含 <code>PaymentDecisionCase</code> 物件的 List
     */
    public List<PaymentDecisionCase> getAllIssuData(String apNo) {
        List<PaymentDecisionCase> beneficiaryDataList = getBeneficiaryDataList(apNo);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();

        for (int i = 0; i < beneficiaryDataList.size(); i++) {
            PaymentDecisionCase obj = beneficiaryDataList.get(i);
            apNo = obj.getApNo();
            String seqNo = obj.getSeqNo();

            PaymentDecisionCase caseObj = new PaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseObj.setBenIssueDataList(getBenIssueDataList(apNo, seqNo));
            // caseObj.setBenChkDataList(getBenChkDataList(apNo, seqNo));
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料 for getAllBenData()/getAllIssuData()
     * 
     * @param apNo 受理編號
     * @return 內含 <code>PaymentDecisionCase</code> 物件的 List
     */
    public List<PaymentDecisionCase> getBeneficiaryDataList(String apNo) {
        List<Baappbase> list = baappbaseDao.selectBeneficiaryDataBy(apNo);
        List<PaymentDecisionCase> returnList = new ArrayList<PaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            PaymentDecisionCase caseObj = new PaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<PaymentDecisionExtCase> getBenIssueDataList(String apNo, String seqNo) {
        List<Baappbase> list = baappbaseDao.selectBenIssueDataBy(apNo, seqNo, new String[] { "20", "30", "40" }, "in");
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 受款人編審註記資料 for getAllBenData()
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>PaymentDecisionExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<PaymentDecisionExtCase> getBenChkDataList(String apNo, String seqNo) {
        List<Bachkfile> list = bachkfileDao.selectBenChkDataBy(apNo, seqNo);
        List<PaymentDecisionExtCase> returnList = new ArrayList<PaymentDecisionExtCase>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 issuYm+payYm
        // Value則是同為該 issuYm+payYm 下的所有編審註記資料
        Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
        }

        // 當編審註記有出現BD時,需顯示'戶籍姓名'欄位(Disabled)
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
            PaymentDecisionExtCase caseObj = new PaymentDecisionExtCase();
            caseObj.setIssuPayYm(key);
            caseObj.setChkFileList(tempList);
            caseObj.setChkFileDataSize(tempList.size());
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 批次決行確認儲存 - 修改 給付主檔
     * 
     * @param updData 受理編號+序號+處理狀態
     * @param userData 使用者資料
     */
    public void updateData(String updData, UserBean userData) {
        // 更新資料到 給付主檔
        // [
        String[] dataList = updData.split(",");
        String empNo = userData.getEmpNo();

        for (int i = 0; i < dataList.length; i++) {
            String[] tempData = dataList[i].split(";");
            String apNo = tempData[0];
            BigDecimal balp0d020Id = new BigDecimal(tempData[1]);
            String procStat = tempData[2];

            Baappbase beforeBaappbase = new Baappbase();
            beforeBaappbase = baappbaseDao.selectForCheckMarkLevelAdjustByLog(apNo);

            // 更新 給付主檔
            Baappbase baappbase = new Baappbase();
            baappbase.setApNo(apNo);
            baappbase.setProcStat(procStat);
            baappbase.setEmpId(empNo);
            if ((ConstantKey.BAAPPBASE_PROCSTAT_11).equals(procStat)) {
                baappbase.setRechkMan("");
                baappbase.setRechkDate("");
                baappbase.setExeMan("");
                baappbase.setExeDate("");
                baappbase.setArcDate("");
                baappbase.setArcPg("");
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_30).equals(procStat)) {
                baappbase.setRechkMan(empNo);
                baappbase.setRechkDate(DateUtility.getNowWestDate());
                baappbase.setExeMan("");
                baappbase.setExeDate("");
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_40).equals(procStat)) {
                baappbase.setRechkMan(empNo);
                baappbase.setRechkDate(DateUtility.getNowWestDate());
                baappbase.setExeMan(empNo);
                baappbase.setExeDate(DateUtility.getNowWestDate());
            }
            baappbaseDao.updateDataForDecision(baappbase);

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBaappbase, baappbase);
            }

            // 新增資料到 更正記錄檔
            // [
            log.debug("Start Insert BAAPPLOG ...");
            Baapplog baapplog = new Baapplog();
            baapplog.setBaappbaseId(beforeBaappbase.getBaappbaseId());// 資料列編號
            baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            baapplog.setUpCol("處理狀態"); // 異動項目
            baapplog.setBvalue(beforeBaappbase.getProcStat());// 改前內容
            baapplog.setAvalue(procStat); // 改後內容
            baapplogDao.insertData(baapplog);
            log.debug("Insert BAAPPLOG Finished ...");
            // ]

            // 更新 審核給付清單紀錄檔
            Balp0d020 balp0d020 = new Balp0d020();
            balp0d020.setApNo(apNo);
            balp0d020.setBalp0d020Id(balp0d020Id);
            balp0d020.setProcStat(procStat);
            balp0d020.setEmpNo(empNo);
            balp0d020.setExeDate(DateUtility.getNowWestDate());
            balp0d020.setExeMan(empNo);
            if ((ConstantKey.BAAPPBASE_PROCSTAT_11).equals(procStat)) {
                balp0d020.setExeMk("3");
                balp0d020.setReChkMan("");
                balp0d020.setReChkDate("");
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_30).equals(procStat)) {
                balp0d020.setExeMk("1");
                balp0d020.setReChkMan(empNo);
                balp0d020.setReChkDate(DateUtility.getNowWestDate());
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_40).equals(procStat)) {
                balp0d020.setExeMk("2");
                balp0d020.setReChkMan(empNo);
                balp0d020.setReChkDate(DateUtility.getNowWestDate());
            }
            balp0d020Dao.updateDataForDecision(balp0d020);

            // 20111028 新增決行作業退件存檔時,歸檔清單檔回壓歸檔D註記
            if ((ConstantKey.BAAPPBASE_PROCSTAT_11).equals(procStat))
                baarclistDao.updateDataForDecision(baappbase);
        }
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

    // ------------------------------ 老年年金給付決行 ------------------------------

    // ------------------------------ 失能年金給付決行 ------------------------------
    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 給付決行資料 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @param empId 員工編號
     * @return 內含 <code>DisabledPaymentDecisionCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionCase> getDecisionDataListByApNoForDisabled(String apNo, String empId) {
        List<Baappbase> list = baappbaseDao.selectDecisionDataByApNo(apNo, empId, ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            DisabledPaymentDecisionCase caseObj = new DisabledPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);

            // [複核]及[決行]的radio 預設隱藏
            caseObj.setIsShowReChk("N");
            caseObj.setIsShowExeMk("N");

            // 當EXEMK>=MEXCLVL,[複核]及[決行]的radio都要顯示
            if (Integer.parseInt(caseObj.getExeMk()) >= Integer.parseInt(caseObj.getMexcLvl())) {
                caseObj.setIsShowReChk("Y");
                caseObj.setIsShowExeMk("Y");
            }
            // 當EXEMK<MEXCLVL,且RECHK=Y,[複核]的radio要顯示,[決行]的radio隱藏
            else {
                if (("Y").equals(StringUtils.upperCase(caseObj.getReChk()))) {
                    caseObj.setIsShowReChk("Y");
                }
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依入條件取得 審核決行清單紀錄檔(<code>BAAPPBASE</code>) 給付決行資料 for 失能年金給付決行作業
     * 
     * @param rptDate 列印日期
     * @param pageNo 頁次
     * @param chkMan 審核人員
     * @param empId 員工編號
     * @return 內含 <code>DisabledPaymentDecisionCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionCase> selectDecisionDataByRptDateForDisabled(String rptDate, BigDecimal pageNo, String chkMan, String empId) {
        List<Baappbase> list = baappbaseDao.selectDecisionDataByRptDate(rptDate, pageNo, chkMan, empId, ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            DisabledPaymentDecisionCase caseObj = new DisabledPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);

            // [複核]及[決行]的radio 預設隱藏
            caseObj.setIsShowReChk("N");
            caseObj.setIsShowExeMk("N");

            // 當EXEMK>=MEXCLVL,[複核]及[決行]的radio都要顯示
            if (Integer.parseInt(caseObj.getExeMk()) >= Integer.parseInt(caseObj.getMexcLvl())) {
                caseObj.setIsShowReChk("Y");
                caseObj.setIsShowExeMk("Y");
            }
            // 當EXEMK<MEXCLVL,且RECHK=Y,[複核]的radio要顯示,[決行]的radio隱藏
            else {
                if (("Y").equals(StringUtils.upperCase(caseObj.getReChk()))) {
                    caseObj.setIsShowReChk("Y");
                }
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>DisabledPaymentDecisionCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionCase> selectDetailDecisionDataForDisabled(String apNo) {
        List<Baappbase> list = baappbaseDao.selectDataForReviewBy(apNo, "0000", new String[] { "20", "30", "40" }, "in", new String[] { "35", "36", "38" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            DisabledPaymentDecisionCase caseObj = new DisabledPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);

            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            // 更正原因
            List<Baparam> baparamList = baparamDao.selectListBy(null, "CHGNOTE", caseObj.getChgNote());
            if (baparamList.size() == 1) {
                caseObj.setChgNote(baparamList.get(0).getParamName());
            }
            else {
                caseObj.setChgNote("");
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>DisabledPaymentDecisionCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionCase> getLetterTypeMk1ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk1(apNo);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            DisabledPaymentDecisionCase caseObj = new DisabledPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>DisabledPaymentDecisionCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionCase> getLetterTypeMk2ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk2(apNo);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            DisabledPaymentDecisionCase caseObj = new DisabledPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>DisabledPaymentDecisionCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionCase> getLetterTypeMk3ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk3(apNo);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            DisabledPaymentDecisionCase caseObj = new DisabledPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>DisabledPaymentDecisionCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionCase> getLetterTypeMk4ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk4(apNo);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            DisabledPaymentDecisionCase caseObj = new DisabledPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>DisabledPaymentDecisionCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionCase> getLetterTypeMk5ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk5(apNo);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            DisabledPaymentDecisionCase caseObj = new DisabledPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return <code>DisabledPaymentDecisionCase</code> 物件
     */
    public DisabledPaymentDecisionCase getLetterTypeMk6ListForDisabled(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk6(apNo);
        DisabledPaymentDecisionCase caseObj = new DisabledPaymentDecisionCase();
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
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 救濟種類資料 for 失能年金給付決行作業
     * 
     * @param reliefKind 救濟種類
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6KindForDisabled(String reliefKind) {

        return maadmrecDao.selectLetterTypeMk6Kind(reliefKind);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟辦理情形資料 for 失能年金給付決行作業
     * 
     * @param reliefKind 救濟種類
     * @param reliefStat 行政救濟辦理情形
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6StatForDisabled(String reliefKind, String reliefStat) {

        return maadmrecDao.selectLetterTypeMk6Stat(reliefKind, reliefStat);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 職災相關資料 for 失能年金給付審核作業
     * 
     * @param apNo 受理編號
     * @return <code>DisabledPaymentReviewCase</code>
     */
    public DisabledPaymentDecisionCase getOccAccDataForDisabled(String apNo) {
        Badapr badapr = badaprDao.selectOccAccDataForDisabledPaymentReview(apNo);
        DisabledPaymentDecisionCase returnCase = new DisabledPaymentDecisionCase();
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
    public DisabledPaymentDecisionCase selectDisabledDataForDisabled(String apNo) {
        Baappexpand baappexpand = baappexpandDao.selectDisabledDataForPaymentReview(apNo);
        DisabledPaymentDecisionCase returnCase = new DisabledPaymentDecisionCase();
        if (baappexpand != null) {
            BeanUtility.copyProperties(returnCase, baappexpand);
            // 取得 醫院簡稱
            returnCase.setHpSnam(bbcmf07Dao.selectHpSnamBy(returnCase.getHosId()));
        }
        return returnCase;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @param issuym 核定年月
     * @return 內含 <code>DisabledPaymentDecisionExtCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionExtCase> getPayDataForDisabled(String apNo, String issuYm) {
        List<Badapr> list = badaprDao.selectPayDataBy(apNo, issuYm);
        List<DisabledPaymentDecisionExtCase> returnList = new ArrayList<DisabledPaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Badapr obj = list.get(i);
            DisabledPaymentDecisionExtCase caseObj = new DisabledPaymentDecisionExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseObj.setAplPayAmt(obj.getAplpayAmt());
            returnList.add(caseObj);
        }
        return returnList;
    }

    // /**
    // * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 for 失能年金給付決行作業
    // *
    // * @param apNo 受理編號
    // * @param seqNo 序號
    // * @param chkTyp 編審註記種類 (A:事故者編審註記, B:眷屬編審註記, C:符合註記)
    // * @return <code>PaymentDecisionExtCase</code> 物件 List
    // */
    // @SuppressWarnings("unchecked")
    // public List<DisabledPaymentDecisionExtCase> getPaymentDecisionEvtChkListForDisabled(String apNo, String seqNo) {
    // List<Bachkfile> list = bachkfileDao.selectReviewChkListBy(apNo, seqNo, "A", null);
    // List<DisabledPaymentDecisionExtCase> returnList = new ArrayList<DisabledPaymentDecisionExtCase>();
    // Map<String, Object> returnMap = new HashMap<String, Object>();
    //
    // // 建立資料Map
    // // Map的Key 為 issuYm+payYm
    // // Value則是同為該 issuYm+payYm 下的所有編審註記資料
    // Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
    // for (int i = 0; i < list.size(); i++) {
    // Bachkfile obj = (Bachkfile) list.get(i);
    // map.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
    // }
    //
    // for (int i = 0; i < list.size(); i++) {
    // Bachkfile obj = (Bachkfile) list.get(i);
    // ChkFileCase caseObj = new ChkFileCase();
    // BeanUtility.copyProperties(caseObj, obj);
    // (map.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(caseObj);
    // }
    //
    // // 將 分類好的 map 轉為 list
    // for (Iterator it = map.keySet().iterator(); it.hasNext();) {
    // String key = (String) it.next();
    // List<ChkFileCase> tempList = map.get(key);
    // DisabledPaymentDecisionExtCase caseObj = new DisabledPaymentDecisionExtCase();
    // caseObj.setIssuPayYm(key);
    // caseObj.setChkFileList(tempList);
    // caseObj.setChkFileDataSize(tempList.size());
    // returnList.add(caseObj);
    // }
    // return returnList;
    // }
    //
    // /**
    // * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 for 失能年金給付審核
    // *
    // * @param apNo 受理編號
    // * @param seqNo 序號
    // * @param chkTyp 編審註記種類 (B:眷屬編審註記, C:符合註記)
    // * @return <code>DisabledPaymentDecisionExtCase</code> 物件 List
    // */
    // @SuppressWarnings("unchecked")
    // public List<DisabledPaymentDecisionCase> getPaymentDecisionOtherChkListForDisabled(String apNo, String seqNo, String chkTyp) {
    // List<Bachkfile> list = bachkfileDao.selectReviewChkListBy(apNo, seqNo, chkTyp, null);
    // List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
    // List<DisabledPaymentDecisionExtCase> subList = new ArrayList<DisabledPaymentDecisionExtCase>();
    // Map<String, Object> returnMap = new HashMap<String, Object>();
    //
    // // 建立資料Map
    // // Map的Key 為 seqNo
    // // Value則是同為該 seqNo 下的所有編審註記資料
    // Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
    // for (int i = 0; i < list.size(); i++) {
    // Bachkfile obj = (Bachkfile) list.get(i);
    // map.put(obj.getSeqNo(), new ArrayList<ChkFileCase>());
    // }
    // for (int i = 0; i < list.size(); i++) {
    // Bachkfile obj = (Bachkfile) list.get(i);
    // ChkFileCase caseObj = new ChkFileCase();
    // BeanUtility.copyProperties(caseObj, obj);
    // (map.get(obj.getSeqNo())).add(caseObj);
    // }
    //
    // // 將 分類好的 map 轉為 list
    // for (Iterator it = map.keySet().iterator(); it.hasNext();) {
    // subList = new ArrayList<DisabledPaymentDecisionExtCase>();
    // String key = (String) it.next();
    // List<ChkFileCase> tempList = map.get(key);
    //
    // // 建立資料SubMap
    // // Map的Key 為 issuYm+payYm
    // // Value則是同為該 issuYm+payYm 下的所有編審註記資料
    // Map<String, List<ChkFileCase>> subMap = new TreeMap<String, List<ChkFileCase>>();
    // for (int i = 0; i < tempList.size(); i++) {
    // ChkFileCase obj = (ChkFileCase) tempList.get(i);
    // subMap.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<ChkFileCase>());
    // }
    //
    // for (int i = 0; i < tempList.size(); i++) {
    // ChkFileCase obj = (ChkFileCase) tempList.get(i);
    // (subMap.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(obj);
    // }
    //
    // // 將 分類好的 SubMap 轉為 list
    // for (Iterator subIt = subMap.keySet().iterator(); subIt.hasNext();) {
    // String subKey = (String) subIt.next();
    // List<ChkFileCase> subTempList = subMap.get(subKey);
    // DisabledPaymentDecisionExtCase caseObj = new DisabledPaymentDecisionExtCase();
    // caseObj.setIssuPayYm(subKey);
    // caseObj.setChkFileList(tempList);
    // caseObj.setChkFileDataSize(tempList.size());
    // subList.add(caseObj);
    // }
    // if (subList.size() != 0) {
    // DisabledPaymentDecisionCase masterCase = new DisabledPaymentDecisionCase();
    // masterCase.setSeqNo(key);
    // masterCase.setOtherChkDataList(subList);
    // returnList.add(masterCase);
    // }
    // }
    // return returnList;
    // }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>DisabledPaymentDecisionExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<DisabledPaymentDecisionExtCase> getDisabledPaymentDecisionEvtChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewEvtChkListForDisabled(apNo);
        List<DisabledPaymentDecisionExtCase> returnList = new ArrayList<DisabledPaymentDecisionExtCase>();
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
            DisabledPaymentDecisionExtCase caseObj = new DisabledPaymentDecisionExtCase();
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
     * @return <code>DisabledPaymentDecisionExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<DisabledPaymentDecisionCase> getDisabledPaymentDecisionBenChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewBenChkListForDisabled(apNo);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
        List<DisabledPaymentDecisionExtCase> subList = new ArrayList<DisabledPaymentDecisionExtCase>();
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
            subList = new ArrayList<DisabledPaymentDecisionExtCase>();
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
                DisabledPaymentDecisionExtCase caseObj = new DisabledPaymentDecisionExtCase();
                caseObj.setIssuPayYm(subKey);
                caseObj.setChkFileList(subTempList);
                caseObj.setChkFileDataSize(subTempList.size());
                subList.add(caseObj);
            }
            if (subTempList.size() != 0) {
                DisabledPaymentDecisionCase masterCase = new DisabledPaymentDecisionCase();
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
     * @return <code>DisabledPaymentDecisionExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<DisabledPaymentDecisionCase> getDisabledPaymentDecisionOtherChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewOtherChkListForDisabled(apNo);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
        List<DisabledPaymentDecisionExtCase> subList = new ArrayList<DisabledPaymentDecisionExtCase>();
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
            subList = new ArrayList<DisabledPaymentDecisionExtCase>();
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
                DisabledPaymentDecisionExtCase caseObj = new DisabledPaymentDecisionExtCase();
                caseObj.setIssuPayYm(subKey);
                caseObj.setChkFileList(subTempList);
                caseObj.setChkFileDataSize(subTempList.size());
                subList.add(caseObj);
            }
            if (subTempList.size() != 0) {
                DisabledPaymentDecisionCase masterCase = new DisabledPaymentDecisionCase();
                masterCase.setSeqNo(key);
                masterCase.setOtherChkDataList(subList);
                returnList.add(masterCase);
            }
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料、受款人核定資料 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>DisabledPaymentDecisionCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionCase> getAllIssuDataForDisabled(String apNo) {
        List<DisabledPaymentDecisionCase> beneficiaryDataList = getBeneficiaryDataListForDisabled(apNo);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();

        for (int i = 0; i < beneficiaryDataList.size(); i++) {
            DisabledPaymentDecisionCase obj = beneficiaryDataList.get(i);
            apNo = obj.getApNo();
            String seqNo = obj.getSeqNo();

            DisabledPaymentDecisionCase caseObj = new DisabledPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseObj.setBenIssueDataList(getBenIssueDataListForDisabled(apNo, seqNo));
            // caseObj.setBenChkDataList(getBenChkDataList(apNo, seqNo));
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料 for 失能年金給付決行作業 (for getAllBenData()/getAllIssuData())
     * 
     * @param apNo 受理編號
     * @return 內含 <code>DisabledPaymentDecisionCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionCase> getBeneficiaryDataListForDisabled(String apNo) {
        List<Baappbase> list = baappbaseDao.selectBeneficiaryDataBy(apNo);
        List<DisabledPaymentDecisionCase> returnList = new ArrayList<DisabledPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            DisabledPaymentDecisionCase caseObj = new DisabledPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>DisabledPaymentDecisionExtCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionExtCase> getBenIssueDataListForDisabled(String apNo, String seqNo) {
        List<Baappbase> list = baappbaseDao.selectBenIssueDataBy(apNo, seqNo, new String[] { "20", "30", "40" }, "in");
        List<DisabledPaymentDecisionExtCase> returnList = new ArrayList<DisabledPaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            DisabledPaymentDecisionExtCase caseObj = new DisabledPaymentDecisionExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人清單 for 失能年金給付決行作業
     * 
     * @param apNo 受理編號
     * @param benPayMk 受益人領取狀態註記
     * @return 內含 <code>DisabledPaymentDecisionExtCase</code> 物件的 List
     */
    public List<DisabledPaymentDecisionExtCase> getBenNameListForDisabled(String apNo, String benPayMk) {
        List<String> list = baappbaseDao.selectBenNameBy(apNo, benPayMk);
        List<DisabledPaymentDecisionExtCase> returnList = new ArrayList<DisabledPaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            DisabledPaymentDecisionExtCase caseObj = new DisabledPaymentDecisionExtCase();
            caseObj.setBenName((String) list.get(i));
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 批次決行確認儲存 - 修改 給付主檔 for 失能年金給付決行作業
     * 
     * @param updData 受理編號+序號+處理狀態
     * @param userData 使用者資料
     */
    public void updateDataForDisabled(String updData, UserBean userData) {
        // 更新資料到 給付主檔
        // [
        String[] dataList = updData.split(",");
        String empNo = userData.getEmpNo();

        for (int i = 0; i < dataList.length; i++) {
            String[] tempData = dataList[i].split(";");
            String apNo = tempData[0];
            BigDecimal balp0d020Id = new BigDecimal(tempData[1]);
            String procStat = tempData[2];

            Baappbase beforeBaappbase = new Baappbase();
            beforeBaappbase = baappbaseDao.selectForCheckMarkLevelAdjustByLog(apNo);

            // 更新 給付主檔
            Baappbase baappbase = new Baappbase();
            baappbase.setApNo(apNo);
            baappbase.setProcStat(procStat);
            baappbase.setEmpId(empNo);
            if ((ConstantKey.BAAPPBASE_PROCSTAT_11).equals(procStat)) {
                baappbase.setRechkMan("");
                baappbase.setRechkDate("");
                baappbase.setExeMan("");
                baappbase.setExeDate("");
                baappbase.setArcDate("");
                baappbase.setArcPg("");
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_30).equals(procStat)) {
                baappbase.setRechkMan(empNo);
                baappbase.setRechkDate(DateUtility.getNowWestDate());
                baappbase.setExeMan("");
                baappbase.setExeDate("");
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_40).equals(procStat)) {
                baappbase.setRechkMan(empNo);
                baappbase.setRechkDate(DateUtility.getNowWestDate());
                baappbase.setExeMan(empNo);
                baappbase.setExeDate(DateUtility.getNowWestDate());
            }
            baappbaseDao.updateDataForDecision(baappbase);

            // 新增資料到 更正記錄檔
            // [
            log.debug("Start Insert BAAPPLOG ...");
            Baapplog baapplog = new Baapplog();
            baapplog.setBaappbaseId(beforeBaappbase.getBaappbaseId());// 資料列編號
            baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            baapplog.setUpCol("處理狀態"); // 異動項目
            baapplog.setBvalue(beforeBaappbase.getProcStat());// 改前內容
            baapplog.setAvalue(procStat); // 改後內容
            baapplogDao.insertData(baapplog);
            log.debug("Insert BAAPPLOG Finished ...");
            // ]

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBaappbase, baappbase);
            }

            // 更新 審核給付清單紀錄檔
            Balp0d020 balp0d020 = new Balp0d020();
            balp0d020.setApNo(apNo);
            balp0d020.setBalp0d020Id(balp0d020Id);
            balp0d020.setProcStat(procStat);
            balp0d020.setEmpNo(empNo);
            balp0d020.setExeDate(DateUtility.getNowWestDate());
            balp0d020.setExeMan(empNo);
            if ((ConstantKey.BAAPPBASE_PROCSTAT_11).equals(procStat)) {
                balp0d020.setExeMk("3");
                balp0d020.setReChkMan("");
                balp0d020.setReChkDate("");
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_30).equals(procStat)) {
                balp0d020.setExeMk("1");
                balp0d020.setReChkMan(empNo);
                balp0d020.setReChkDate(DateUtility.getNowWestDate());
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_40).equals(procStat)) {
                balp0d020.setExeMk("2");
                balp0d020.setReChkMan(empNo);
                balp0d020.setReChkDate(DateUtility.getNowWestDate());
            }
            balp0d020Dao.updateDataForDecision(balp0d020);

            // 20111028 新增決行作業退件存檔時,歸檔清單檔回壓歸檔D註記
            if ((ConstantKey.BAAPPBASE_PROCSTAT_11).equals(procStat))
                baarclistDao.updateDataForDecision(baappbase);
        }
    }

    // ------------------------------ 失能年金給付決行 ------------------------------

    // ------------------------------ 遺屬年金給付決行 ------------------------------
    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 給付決行資料 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @param empId 員工編號
     * @return 內含 <code>SurvivorPaymentDecisionCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionCase> getDecisionDataListByApNoForSurvivor(String apNo, String empId) {
        List<Baappbase> list = baappbaseDao.selectDecisionDataByApNo(apNo, empId, ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);

            // [複核]及[決行]的radio 預設隱藏
            caseObj.setIsShowReChk("N");
            caseObj.setIsShowExeMk("N");

            // 當EXEMK>=MEXCLVL,[複核]及[決行]的radio都要顯示
            if (Integer.parseInt(caseObj.getExeMk()) >= Integer.parseInt(caseObj.getMexcLvl())) {
                caseObj.setIsShowReChk("Y");
                caseObj.setIsShowExeMk("Y");
            }
            // 當EXEMK<MEXCLVL,且RECHK=Y,[複核]的radio要顯示,[決行]的radio隱藏
            else {
                if (("Y").equals(StringUtils.upperCase(caseObj.getReChk()))) {
                    caseObj.setIsShowReChk("Y");
                }
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依入條件取得 審核決行清單紀錄檔(<code>BAAPPBASE</code>) 給付決行資料 for 遺屬年金給付決行作業
     * 
     * @param rptDate 列印日期
     * @param pageNo 頁次
     * @param chkMan 審核人員
     * @param empId 員工編號
     * @return 內含 <code>SurvivorPaymentDecisionCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionCase> selectDecisionDataByRptDateForSurvivor(String rptDate, BigDecimal pageNo, String chkMan, String empId) {
        List<Baappbase> list = baappbaseDao.selectDecisionDataByRptDate(rptDate, pageNo, chkMan, empId, ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);

            // [複核]及[決行]的radio 預設隱藏
            caseObj.setIsShowReChk("N");
            caseObj.setIsShowExeMk("N");

            // 當EXEMK>=MEXCLVL,[複核]及[決行]的radio都要顯示
            if (Integer.parseInt(caseObj.getExeMk()) >= Integer.parseInt(caseObj.getMexcLvl())) {
                caseObj.setIsShowReChk("Y");
                caseObj.setIsShowExeMk("Y");
            }
            // 當EXEMK<MEXCLVL,且RECHK=Y,[複核]的radio要顯示,[決行]的radio隱藏
            else {
                if (("Y").equals(StringUtils.upperCase(caseObj.getReChk()))) {
                    caseObj.setIsShowReChk("Y");
                }
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>SurvivorPaymentDecisionCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionCase> selectDetailDecisionDataForSurvivor(String apNo) {
        List<Baappbase> list = baappbaseDao.selectDataForReviewBy(apNo, "0000", new String[] { "20", "30", "40" }, "in", null, null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);

            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            // 更正原因
            List<Baparam> baparamList = baparamDao.selectListBy(null, "CHGNOTE", caseObj.getChgNote());
            if (baparamList.size() == 1) {
                caseObj.setChgNote(baparamList.get(0).getParamName());
            }
            else {
                caseObj.setChgNote("");
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>SurvivorPaymentDecisionCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionCase> getLetterTypeMk1ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk1(apNo);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>SurvivorPaymentDecisionCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionCase> getLetterTypeMk2ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk2(apNo);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>SurvivorPaymentDecisionCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionCase> getLetterTypeMk3ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk3(apNo);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>SurvivorPaymentDecisionCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionCase> getLetterTypeMk4ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk4(apNo);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>SurvivorPaymentDecisionCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionCase> getLetterTypeMk5ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk5(apNo);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Maadmrec obj = list.get(i);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return <code>SurvivorPaymentDecisionCase</code> 物件
     */
    public SurvivorPaymentDecisionCase getLetterTypeMk6ListForSurvivor(String apNo) {
        List<Maadmrec> list = maadmrecDao.selectLetterTypeMk6(apNo);
        SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
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
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 救濟種類資料 for 遺屬年金給付決行作業
     * 
     * @param reliefKind 救濟種類
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6KindForSurvivor(String reliefKind) {

        return maadmrecDao.selectLetterTypeMk6Kind(reliefKind);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟辦理情形資料 for 遺屬年金給付決行作業
     * 
     * @param reliefKind 救濟種類
     * @param reliefStat 行政救濟辦理情形
     * @return <code>Maadmrec</code> 物件
     */
    public String getLetterTypeMk6StatForSurvivor(String reliefKind, String reliefStat) {

        return maadmrecDao.selectLetterTypeMk6Stat(reliefKind, reliefStat);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 失能相關資料 for 失能年金給付審核作業
     * 
     * @param apNo 受理編號
     * @return <code>SurvivorPaymentDecisionCase</code> 物件
     */
    public SurvivorPaymentDecisionCase selectDisabledDataForSurvivor(String apNo) {
        Baappexpand baappexpand = baappexpandDao.selectDisabledDataForPaymentReview(apNo);
        SurvivorPaymentDecisionCase returnCase = new SurvivorPaymentDecisionCase();
        if (baappexpand != null) {
            BeanUtility.copyProperties(returnCase, baappexpand);
            // 取得 醫院簡稱
            returnCase.setHpSnam(bbcmf07Dao.selectHpSnamBy(returnCase.getHosId()));
        }
        return returnCase;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @param issuym 核定年月
     * @return 內含 <code>SurvivorPaymentDecisionExtCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionExtCase> getPayDataForSurvivor(String apNo, String issuYm) {
        List<Badapr> list = badaprDao.selectPayDataBy(apNo, issuYm);
        List<SurvivorPaymentDecisionExtCase> returnList = new ArrayList<SurvivorPaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Badapr obj = list.get(i);
            SurvivorPaymentDecisionExtCase caseObj = new SurvivorPaymentDecisionExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseObj.setAplPayAmt(obj.getAplpayAmt());
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審註記資料 for 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>SurvivorPaymentDecisionExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<SurvivorPaymentDecisionExtCase> getSurvivorPaymentDecisionEvtChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewEvtChkListForSurvivor(apNo);
        List<SurvivorPaymentDecisionExtCase> returnList = new ArrayList<SurvivorPaymentDecisionExtCase>();
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
            SurvivorPaymentDecisionExtCase caseObj = new SurvivorPaymentDecisionExtCase();
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
     * @return <code>SurvivorPaymentDecisionExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<SurvivorPaymentDecisionCase> getSurvivorPaymentDecisionBenChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewBenChkListForSurvivor(apNo);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        List<SurvivorPaymentDecisionExtCase> subList = new ArrayList<SurvivorPaymentDecisionExtCase>();
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
            subList = new ArrayList<SurvivorPaymentDecisionExtCase>();
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
                SurvivorPaymentDecisionExtCase caseObj = new SurvivorPaymentDecisionExtCase();
                caseObj.setIssuPayYm(subKey);
                caseObj.setChkFileList(subTempList);
                caseObj.setChkFileDataSize(subTempList.size());
                subList.add(caseObj);
            }
            if (subTempList.size() != 0) {
                SurvivorPaymentDecisionCase masterCase = new SurvivorPaymentDecisionCase();
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
     * @return <code>SurvivorPaymentDecisionExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<SurvivorPaymentDecisionCase> getSurvivorPaymentDecisionOtherChkList(String apNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewOtherChkListForSurvivor(apNo);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        List<SurvivorPaymentDecisionExtCase> subList = new ArrayList<SurvivorPaymentDecisionExtCase>();
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
            subList = new ArrayList<SurvivorPaymentDecisionExtCase>();
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
                SurvivorPaymentDecisionExtCase caseObj = new SurvivorPaymentDecisionExtCase();
                caseObj.setIssuPayYm(subKey);
                caseObj.setChkFileList(subTempList);
                caseObj.setChkFileDataSize(subTempList.size());
                subList.add(caseObj);
            }
            if (subTempList.size() != 0) {
                SurvivorPaymentDecisionCase masterCase = new SurvivorPaymentDecisionCase();
                masterCase.setSeqNo(key);
                masterCase.setOtherChkDataList(subList);
                returnList.add(masterCase);
            }
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料 for 遺屬年金給付審核 (for getAllBenData()/getAllIssuData())
     * 
     * @param apNo 受理編號
     * @return 內含 <code>SurvivorPaymentDecisionCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionCase> getbeneficiaryDataListForSurvivor(String apNo) {
        List<Baappbase> list = baappbaseDao.selectEvtBenDataForSurvivorPaymentReview(apNo);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            if (("0000").equals(caseObj.getSeqNo())) {
                caseObj.setBenIssueDataList(getIssuDataListForSurvivor(null, caseObj.getApNo(), caseObj.getSeqNo(), "="));
            }
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
    public List<Object> transInitBenIssuDataForSurvivor(List<SurvivorPaymentDecisionExtCase> benIssuDataList) {
        List<SurvivorPaymentDecisionCase> payYmList = new ArrayList<SurvivorPaymentDecisionCase>();
        List<Object> returnList = new ArrayList<Object>();

        // 建立資料Map
        // payYmMap的Key為payYm, Value則是同為該payYm下的所有核定資料
        // idMap的Key為badaprId, Value則是同為該badaprId之核定資料
        Map<String, List<SurvivorPaymentDecisionExtCase>> payYmMap = new TreeMap<String, List<SurvivorPaymentDecisionExtCase>>();
        Map<BigDecimal, SurvivorPaymentDecisionExtCase> idMap = new TreeMap<BigDecimal, SurvivorPaymentDecisionExtCase>();
        for (int i = 0; i < benIssuDataList.size(); i++) {
            SurvivorPaymentDecisionExtCase obj = benIssuDataList.get(i);
            payYmMap.put(obj.getPayYm(), new ArrayList<SurvivorPaymentDecisionExtCase>());
            idMap.put(obj.getBadaprId(), obj);
        }

        for (int i = 0; i < benIssuDataList.size(); i++) {
            SurvivorPaymentDecisionExtCase obj = benIssuDataList.get(i);
            (payYmMap.get(obj.getPayYm())).add(obj);
        }

        // 將分類好的payYmMap轉為list
        for (Iterator it = payYmMap.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            List<SurvivorPaymentDecisionExtCase> tempList = payYmMap.get(key);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            caseObj.setPayYm(key);
            caseObj.setBenIssueDataList(tempList);
            payYmList.add(caseObj);
        }
        returnList.add(0, payYmList);
        returnList.add(1, idMap);
        return returnList;
    }

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料 for 遺屬年金給付審核 (for getAllIssuData())
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param eqType SQL EqualType
     * @return 內含 <code>SurvivorPaymentDecisionExtCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionExtCase> getIssuDataListForSurvivor(List<SurvivorPaymentDecisionCase> beneficiaryDataList, String apNo, String seqNo, String eqType) {
        List<Baappbase> list = baappbaseDao.selectEvtBenIssuDataForSurvivorPaymentReview(apNo, seqNo, eqType);
        List<SurvivorPaymentDecisionExtCase> returnList = new ArrayList<SurvivorPaymentDecisionExtCase>();

        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            SurvivorPaymentDecisionExtCase caseObj = new SurvivorPaymentDecisionExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料、受款人核定資料 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>SurvivorPaymentDecisionCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionCase> getAllIssuDataForSurvivor(String apNo) {
        List<SurvivorPaymentDecisionCase> beneficiaryDataList = getBeneficiaryDataListForSurvivor(apNo);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();

        for (int i = 0; i < beneficiaryDataList.size(); i++) {
            SurvivorPaymentDecisionCase obj = beneficiaryDataList.get(i);
            apNo = obj.getApNo();
            String seqNo = obj.getSeqNo();

            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseObj.setBenIssueDataList(getBenIssueDataListForSurvivor(apNo, seqNo));
            // caseObj.setBenChkDataList(getBenChkDataList(apNo, seqNo));
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料 for 遺屬年金給付決行作業 (for getAllBenData()/getAllIssuData())
     * 
     * @param apNo 受理編號
     * @return 內含 <code>SurvivorPaymentDecisionCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionCase> getBeneficiaryDataListForSurvivor(String apNo) {
        List<Baappbase> list = baappbaseDao.selectBeneficiaryDataBy(apNo);
        List<SurvivorPaymentDecisionCase> returnList = new ArrayList<SurvivorPaymentDecisionCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            SurvivorPaymentDecisionCase caseObj = new SurvivorPaymentDecisionCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>SurvivorPaymentDecisionExtCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionExtCase> getBenIssueDataListForSurvivor(String apNo, String seqNo) {
        List<Baappbase> list = baappbaseDao.selectBenIssueDataBy(apNo, seqNo, new String[] { "20", "30", "40" }, "in");
        List<SurvivorPaymentDecisionExtCase> returnList = new ArrayList<SurvivorPaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase obj = list.get(i);
            SurvivorPaymentDecisionExtCase caseObj = new SurvivorPaymentDecisionExtCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人清單 for 遺屬年金給付決行作業
     * 
     * @param apNo 受理編號
     * @param benPayMk 受益人領取狀態註記
     * @return 內含 <code>SurvivorPaymentDecisionExtCase</code> 物件的 List
     */
    public List<SurvivorPaymentDecisionExtCase> getBenNameListForSurvivor(String apNo, String benPayMk) {
        List<String> list = baappbaseDao.selectBenNameBy(apNo, benPayMk);
        List<SurvivorPaymentDecisionExtCase> returnList = new ArrayList<SurvivorPaymentDecisionExtCase>();
        for (int i = 0; i < list.size(); i++) {
            SurvivorPaymentDecisionExtCase caseObj = new SurvivorPaymentDecisionExtCase();
            caseObj.setBenName((String) list.get(i));
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 批次決行確認儲存 - 修改 給付主檔 for 遺屬年金給付決行作業
     * 
     * @param updData 受理編號+序號+處理狀態
     * @param userData 使用者資料
     */
    public void updateDataForSurvivor(String updData, UserBean userData) {
        // 更新資料到 給付主檔
        // [
        String[] dataList = updData.split(",");
        String empNo = userData.getEmpNo();

        for (int i = 0; i < dataList.length; i++) {
            String[] tempData = dataList[i].split(";");
            String apNo = tempData[0];
            BigDecimal balp0d020Id = new BigDecimal(tempData[1]);
            String procStat = tempData[2];

            Baappbase beforeBaappbase = new Baappbase();
            beforeBaappbase = baappbaseDao.selectForCheckMarkLevelAdjustByLog(apNo);

            // 更新 給付主檔
            Baappbase baappbase = new Baappbase();

            baappbase.setApNo(apNo);
            baappbase.setProcStat(procStat);
            baappbase.setEmpId(empNo);
            if ((ConstantKey.BAAPPBASE_PROCSTAT_11).equals(procStat)) {
                baappbase.setRechkMan("");
                baappbase.setRechkDate("");
                baappbase.setExeMan("");
                baappbase.setExeDate("");
                baappbase.setArcDate("");
                baappbase.setArcPg("");
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_30).equals(procStat)) {
                baappbase.setRechkMan(empNo);
                baappbase.setRechkDate(DateUtility.getNowWestDate());
                baappbase.setExeMan("");
                baappbase.setExeDate("");
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_40).equals(procStat)) {
                baappbase.setRechkMan(empNo);
                baappbase.setRechkDate(DateUtility.getNowWestDate());
                baappbase.setExeMan(empNo);
                baappbase.setExeDate(DateUtility.getNowWestDate());
            }
            baappbaseDao.updateDataForDecision(baappbase);

            // 新增資料到 更正記錄檔
            // [
            log.debug("Start Insert BAAPPLOG ...");
            Baapplog baapplog = new Baapplog();
            baapplog.setBaappbaseId(beforeBaappbase.getBaappbaseId());// 資料列編號
            baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            baapplog.setUpCol("處理狀態"); // 異動項目
            baapplog.setBvalue(beforeBaappbase.getProcStat());// 改前內容
            baapplog.setAvalue(procStat); // 改後內容
            baapplogDao.insertData(baapplog);
            log.debug("Insert BAAPPLOG Finished ...");
            // ]

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBaappbase, baappbase);
            }

            // 更新 審核給付清單紀錄檔
            Balp0d020 balp0d020 = new Balp0d020();
            balp0d020.setApNo(apNo);
            balp0d020.setBalp0d020Id(balp0d020Id);
            balp0d020.setProcStat(procStat);
            balp0d020.setEmpNo(empNo);
            balp0d020.setExeDate(DateUtility.getNowWestDate());
            balp0d020.setExeMan(empNo);
            if ((ConstantKey.BAAPPBASE_PROCSTAT_11).equals(procStat)) {
                balp0d020.setExeMk("3");
                balp0d020.setReChkMan("");
                balp0d020.setReChkDate("");
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_30).equals(procStat)) {
                balp0d020.setExeMk("1");
                balp0d020.setReChkMan(empNo);
                balp0d020.setReChkDate(DateUtility.getNowWestDate());
            }
            else if ((ConstantKey.BAAPPBASE_PROCSTAT_40).equals(procStat)) {
                balp0d020.setExeMk("2");
                balp0d020.setReChkMan(empNo);
                balp0d020.setReChkDate(DateUtility.getNowWestDate());
            }
            balp0d020Dao.updateDataForDecision(balp0d020);

            // 20111028 新增決行作業退件存檔時,歸檔清單檔回壓歸檔D註記
            if ((ConstantKey.BAAPPBASE_PROCSTAT_11).equals(procStat))
                baarclistDao.updateDataForDecision(baappbase);
        }
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

    // ------------------------------ 遺屬年金給付決行 ------------------------------
    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setBaexalistDao(BaexalistDao baexalistDao) {
        this.baexalistDao = baexalistDao;
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

    public void setBaparamDao(BaparamDao baparamDao) {
        this.baparamDao = baparamDao;
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

    public void setMaadmrecDao(MaadmrecDao maadmrecDao) {
        this.maadmrecDao = maadmrecDao;
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

    public void setBaarclistDao(BaarclistDao baarclistDao) {
        this.baarclistDao = baarclistDao;
    }

    public void setBaapplogDao(BaapplogDao baapplogDao) {
        this.baapplogDao = baapplogDao;
    }
}
