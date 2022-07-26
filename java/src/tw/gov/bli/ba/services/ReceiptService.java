package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.Baap0d040Dao;
import tw.gov.bli.ba.dao.Baap0d060Dao;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BaappexpandDao;
import tw.gov.bli.ba.dao.BaapplogDao;
import tw.gov.bli.ba.dao.BacountryDao;
import tw.gov.bli.ba.dao.BafamilyDao;
import tw.gov.bli.ba.dao.BafamilytempDao;
import tw.gov.bli.ba.dao.BaparamDao;
import tw.gov.bli.ba.dao.Bbcmf09Dao;
import tw.gov.bli.ba.dao.BcbpfDao;
import tw.gov.bli.ba.dao.CvldtlDao;
import tw.gov.bli.ba.dao.NbappbaseDao;
import tw.gov.bli.ba.dao.NbexcepDao;
import tw.gov.bli.ba.dao.NpbanklistDao;
import tw.gov.bli.ba.dao.NppostlistDao;
import tw.gov.bli.ba.domain.Baap0d060;
import tw.gov.bli.ba.domain.Baap0d040;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.domain.Bafamily;
import tw.gov.bli.ba.domain.Bafamilytemp;
import tw.gov.bli.ba.domain.Cvldtl;
import tw.gov.bli.ba.domain.Nbappbase;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptCase;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptEvtCase;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptFamCase;
import tw.gov.bli.ba.receipt.cases.OldAgeAnnuityReceiptCase;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptBenCase;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptCase;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptEvtCase;
import tw.gov.bli.ba.receipt.forms.DisabledAnnuityReceiptForm;
import tw.gov.bli.ba.receipt.forms.SurvivorAnnuityReceiptForm;
import tw.gov.bli.ba.receipt.forms.SurvivorAnnuityWalkInReceiptForm;
import tw.gov.bli.ba.util.BaBusinessUtility;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.util.FrameworkLogUtil;

/**
 * Service for 受理作業
 * 
 * @author Rickychi
 */
public class ReceiptService {
    private static Log log = LogFactory.getLog(ReceiptService.class);

    private BaappbaseDao baappbaseDao;
    private BacountryDao bacountryDao;
    private CvldtlDao cvldtlDao;
    private NpbanklistDao npbanklistDao;
    private BcbpfDao bcbpfDao;
    private NppostlistDao nppostlistDao;
    private BaapplogDao baapplogDao;
    // private BapaallocateDao bapaallocateDao;
    private Bbcmf09Dao bbcmf09Dao;
    private BaparamDao baparamDao;
    private BaappexpandDao baappexpandDao;
    private BafamilytempDao bafamilytempDao;
    private BafamilyDao bafamilyDao;
    private NbappbaseDao nbappbaseDao;
    private Baap0d060Dao baap0d060Dao;
    private NbexcepDao nbexcepDao;

    // ------------------------------ 老年年金受理作業 ------------------------------
    /**
     * 依畫面輸入欄位條件轉換 新增/修改 給付主檔 部分欄位<br>
     * 
     * @param payBankIdBranchId 帳號(前)
     * @return
     */
    public OldAgeAnnuityReceiptCase transReceiptInputData(OldAgeAnnuityReceiptCase caseObj) {
        // Baappbase obj = new Baappbase();
        // BeanUtility.copyProperties(obj, caseObj);
        // 日期轉換：「申請日期」、「離職日期」、「事故者出生日期」、「法定代理人出生日期」
        // 轉換成西元年日期再回存，其轉換後的日期格式為YYYYMMDD。
        if (StringUtils.isNotBlank(caseObj.getAppDate())) {
            caseObj.setAppDate(DateUtility.changeDateType(caseObj.getAppDate()));
        }
        if (StringUtils.isNotBlank(caseObj.getEvtJobDate())) {
            caseObj.setEvtJobDate(DateUtility.changeDateType(caseObj.getEvtJobDate()));
        }
        if (StringUtils.isNotBlank(caseObj.getEvtBrDate())) {
            caseObj.setEvtBrDate(DateUtility.changeDateType(caseObj.getEvtBrDate()));
        }
        if (StringUtils.isNotBlank(caseObj.getGrdBrDate())) {
            caseObj.setGrdBrDate(DateUtility.changeDateType(caseObj.getGrdBrDate()));
        }

        // 根據 事故者身分證號, 出生日期 取得戶政資料
        Cvldtl cvldtlData = selectCvldtlNameBy(caseObj.getEvtIdnNo(), caseObj.getEvtBrDate());

        // 將 事故者姓名 填入 事故者申請時姓名
        caseObj.setEvtAppName(caseObj.getEvtName());

        // 「國籍別」=1時
        // 「性別」=「事故者身分證號」的第2碼
        // 「國籍」= '000'
        // 「國籍別」=2時
        // 「性別」=【BAAP0D011A】畫面中的「性別」欄位
        // 「國籍」=【BAAP0D011A】畫面中的「國籍」欄位
        if (("1").equals(caseObj.getEvtNationTpe())) {
            if (StringUtils.isNotBlank(caseObj.getEvtIdnNo()) && caseObj.getEvtIdnNo().length() >= 2) {
                caseObj.setEvtSex(caseObj.getEvtIdnNo().substring(1, 2));
            }
            caseObj.setEvtNationCode("000");
        }

        // 「社福識別碼」若查無資料時，則「社福識別碼」= ' '。
        if (("").equals(cvldtlData.getNpIds()) || cvldtlData.getNpIds() == null) {
            caseObj.setEvtIds("");
            caseObj.setBenIds("");
        }
        else {
            caseObj.setEvtIds(cvldtlData.getNpIds());
            caseObj.setBenIds(cvldtlData.getNpIds());
        }

        // 「通訊郵遞區號」、「通訊地址」：
        // 當「通訊地址別」=1時，「通訊郵遞區號」、「通訊地址」=戶籍檔的「通訊郵遞區號」、「通訊地址」；
        // 當「通訊地址別」=2時，「通訊郵遞區號」、「通訊地址」=【BAAP0D011A】畫面中的「通訊郵遞區號」、「通訊地址」欄位。
        if ((ConstantKey.BAAPPBASE_COMMTYP_1).equals(caseObj.getCommTyp())) {
            caseObj.setCommZip(cvldtlData.getHzip());
            caseObj.setCommAddr(cvldtlData.getHaddr());
        }
        else if ((ConstantKey.BAAPPBASE_COMMTYP_2).equals(caseObj.getCommTyp())) {
            caseObj.setCommZip(caseObj.getCommZip());
            caseObj.setCommAddr(caseObj.getCommAddr());
        }
        else {
            caseObj.setCommZip("");
            caseObj.setCommAddr("");
        }
        // 「事故者年齡」：「事故者出生日期」－「申請日期」
        caseObj.setEvtAge(BaBusinessUtility.calAge(caseObj.getAppDate(), caseObj.getEvtBrDate()));

        // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」
        // 當「給付方式」=1時，「金融機構名稱」= 銀行名稱(「金融機構名稱一」+「金融機構名稱二」)
        // 「金融機構總代號」=「帳號」(前：1~3碼)。
        // 「分支代號」=「帳號」(前：4~7碼)。
        // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
        String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
        String accountB = StringUtils.leftPad(caseObj.getPayEeacc(), 14, "0");// 帳號(後)

        if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(caseObj.getPayTyp())) {
            caseObj.setBankName(getBankName(accountF));
            if (accountF.length() >= 3) {
                caseObj.setPayBankId(accountF.substring(0, 3));
            }
            else {
                caseObj.setPayBankId(accountF.substring(0, accountF.length()));
            }
            if (accountF.length() >= 4) {
                caseObj.setBranchId(accountF.substring(3, accountF.length()));
            }
            else {
                caseObj.setBranchId("");
            }
            caseObj.setPayEeacc(accountB);
        }
        // 當「給付方式」=2時，「金融機構名稱」= 郵局名稱
        // 「金融機構總代號」=「帳號」(前：1~3碼)。
        // 「分支代號」=「帳號」(前：4~7碼)。
        // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
        else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(caseObj.getPayTyp())) {
            caseObj.setBankName(getPostName(accountF));
            // caseObj.setPayBankId(ConstantKey.BAAPPBASE_PAYTYP_2_PAYBANKID);
            // caseObj.setBranchId(ConstantKey.BAAPPBASE_PAYTYP_2_BRANCHID);
            if (accountF.length() >= 3) {
                caseObj.setPayBankId(accountF.substring(0, 3));
            }
            else {
                caseObj.setPayBankId(accountF.substring(0, accountF.length()));
            }
            if (accountF.length() >= 4) {
                caseObj.setBranchId(accountF.substring(3, accountF.length()));
            }
            else {
                caseObj.setBranchId("");
            }
            caseObj.setPayEeacc(accountB);
        }
        // 「給付方式」=3、4時，「金融機構名稱」= ' '
        // 「金融機構總代號」= ' '
        // 「分支代號」= ' '
        // 「銀行帳號」= ' '
        else {
            caseObj.setBankName("");
            caseObj.setPayBankId("");
            caseObj.setBranchId("");
            caseObj.setPayEeacc("");
        }

        // 帳號複驗 轉換 20121203更新
        // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」
        // 當「給付方式」=1時，「金融機構名稱」= 銀行名稱(「金融機構名稱一」+「金融機構名稱二」)
        // 「金融機構總代號」=「帳號」(前：1~3碼)。
        // 「分支代號」=「帳號」(前：4~7碼)。
        // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
        String accountChkF = caseObj.getChkPayBankIdChkBranchId();// 帳號(前)
        String accountChkB = StringUtils.leftPad(caseObj.getChkPayEeacc(), 14, "0");// 帳號(後)

        if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(caseObj.getPayTyp())) {

            if (accountChkF.length() >= 3) {
                caseObj.setChkPayBankId(accountChkF.substring(0, 3));
            }
            else {
                caseObj.setChkPayBankId(accountChkF.substring(0, accountChkF.length()));
            }
            if (accountChkF.length() >= 4) {
                caseObj.setChkBranchId(accountChkF.substring(3, accountChkF.length()));
            }
            else {
                caseObj.setChkBranchId("");
            }
            caseObj.setChkPayEeacc(accountChkB);
        }
        // 當「給付方式」=2時，「金融機構名稱」= 郵局名稱
        // 「金融機構總代號」=「帳號」(前：1~3碼)。
        // 「分支代號」=「帳號」(前：4~7碼)。
        // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
        else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(caseObj.getPayTyp())) {

            if (accountChkF.length() >= 3) {
                caseObj.setChkPayBankId(accountChkF.substring(0, 3));
            }
            else {
                caseObj.setChkPayBankId(accountChkF.substring(0, accountChkF.length()));
            }
            if (accountChkF.length() >= 4) {
                caseObj.setChkBranchId(accountChkF.substring(3, accountChkF.length()));
            }
            else {
                caseObj.setChkBranchId("");
            }
            caseObj.setChkPayEeacc(accountChkB);
        }
        // 「給付方式」=3、4時，「金融機構名稱」= ' '
        // 「金融機構總代號」= ' '
        // 「分支代號」= ' '
        // 「銀行帳號」= ' '
        else {
            caseObj.setChkPayBankId("");
            caseObj.setChkBranchId("");
            caseObj.setChkPayEeacc("");
        }

        // 「國、勞合併申請」= Y 時, 則「給付種類」= 48
        // 「國、勞合併申請」= "" 時, 則「給付種類」= 45
        if (("Y").equals(caseObj.getCombapMark())) {
            caseObj.setPayKind("48");
        }
        else {
            caseObj.setPayKind("45");
        }

        caseObj.setAccRel("1");

        // 手機複驗
        String tel1 = caseObj.getTel1();
        String tel2 = caseObj.getTel2();
        String shortTel1 = "";
        String shortTel2 = "";
        if (tel1.length() >= 2) {
            shortTel1 = tel1.substring(0, 2);
        }
        if (tel2.length() >= 2) {
            shortTel2 = tel2.substring(0, 2);
        }

        if (!((StringUtils.equals(shortTel1, "09") && tel1.length() == 10) || (StringUtils.equals(shortTel2, "09") && tel2.length() == 10))) {
            caseObj.setMobilePhone("");
        }

        // 2009.11.20修改
        // 當「給付方式」=1或2,「金融機構總代號(BAAPPBASE.PAYBANKID)」、「分支代號(BAAPPBASE.BRANCHID)」、「銀行帳號(BAAPPBASE.PAYEEACC)」欄位的資料長度不足時,
        // 需靠右左以"0"補齊資料長度回存。
        // 金融機構總代號(BAAPPBASE.PAYBANKID) = 3碼
        // 分支代號(BAAPPBASE.BRANCHID) = 4碼
        // 銀行帳號(BAAPPBASE.PAYEEACC) = 14碼
        if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(caseObj.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(caseObj.getPayTyp())) {
            caseObj.setPayBankId(StringUtils.leftPad(caseObj.getPayBankId(), 3, "0"));
            caseObj.setBranchId(StringUtils.leftPad(caseObj.getBranchId(), 4, "0"));
            caseObj.setPayEeacc(StringUtils.leftPad(caseObj.getPayEeacc(), 14, "0"));
        }

        return caseObj;
    }

    /**
     * 依傳入條件取得 給付主檔 資料清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return
     */
    public List<OldAgeAnnuityReceiptCase> selectBaappbaseDataList(String evtIdnNo, String apNo, String seqNo, String[] procStat, String eqType, String payKind) {
        List<Baappbase> list = baappbaseDao.selectDataBy(evtIdnNo, apNo, seqNo, procStat, eqType, payKind);
        List<OldAgeAnnuityReceiptCase> returnList = new ArrayList<OldAgeAnnuityReceiptCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            OldAgeAnnuityReceiptCase caseObj = new OldAgeAnnuityReceiptCase();
            BeanUtility.copyProperties(caseObj, baappbase);

            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔 詳細資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public OldAgeAnnuityReceiptCase getBaappbaseDetail(BigDecimal baappbaseId, String[] procStat, String eqType) {
        Baappbase baappbase = baappbaseDao.selectDetailDataBy(baappbaseId, procStat, null, eqType);
        OldAgeAnnuityReceiptCase caseObj = null;
        if (baappbase != null) {
            caseObj = new OldAgeAnnuityReceiptCase();
            BeanUtility.copyProperties(caseObj, baappbase);

            if (StringUtils.isNotBlank(caseObj.getAppDateStr())) {
                caseObj.setAppDate(caseObj.getAppDateStr());
            }
            if (StringUtils.isNotBlank(caseObj.getEvtBrDateStr())) {
                caseObj.setEvtBrDate(caseObj.getEvtBrDateStr());
            }
            if (StringUtils.isNotBlank(caseObj.getEvtJobDateStr())) {
                caseObj.setEvtJobDate(caseObj.getEvtJobDateStr());
            }
            if (StringUtils.isNotBlank(caseObj.getGrdBrDateStr())) {
                caseObj.setGrdBrDate(caseObj.getGrdBrDateStr());
            }
            if (StringUtils.isNotBlank(caseObj.getPayBankId()) || StringUtils.isNotBlank(caseObj.getBranchId())) {
                caseObj.setPayBankIdBranchId(caseObj.getPayBankId() + caseObj.getBranchId());
            }
            if (StringUtils.isNotBlank(caseObj.getChkPayBankId()) || StringUtils.isNotBlank(caseObj.getChkBranchId())) {
                caseObj.setChkPayBankIdChkBranchId(caseObj.getChkPayBankId() + caseObj.getChkBranchId());
            }
        }
        return caseObj;
    }

    /**
     * 新增 給付主檔 資料
     * 
     * @param baappbase 給付主檔
     */
    public void insertBaappbaseData(UserBean userData, OldAgeAnnuityReceiptCase baappbaseData) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAAPPBASE ...");
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, baappbaseData);

        baappbase.setSeqNo("0000");// 序號
        baappbase.setImk("1");// 保險別

        // if (("Y").equals(baappbase.getCombapMark())) {// 給付種類
        // baappbase.setPayKind("48");
        // }
        // else {
        // baappbase.setPayKind("45");
        // }
        baappbase.setCaseTyp("1");// 案件類別
        baappbase.setProcStat("00");// 處理狀態
        baappbase.setEvtIdent("1");// 事故者身分別
        baappbase.setBenIdnNo(baappbase.getEvtIdnNo());// 受益人身分證號
        baappbase.setBenName(baappbase.getEvtName());// 受益人姓名
        baappbase.setBenBrDate(baappbase.getEvtBrDate());// 受益人出生日期
        baappbase.setBenSex(baappbase.getEvtSex());// 受益人性別
        baappbase.setBenNationTyp(baappbase.getEvtNationTpe());// 受益人國籍別
        baappbase.setBenNationCode(baappbase.getEvtNationCode());// 受益人國籍
        baappbase.setBenEvtRel("1");// 受益人與事故者關係
        baappbase.setBenPayMk("1");// 受益人領取狀態註記
        baappbase.setAccSeqNo("0000");
        baappbase.setMitRate(new BigDecimal(0));

        if ((ConstantKey.BAAPPBASE_PAYTYP_3).equals(baappbase.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_4).equals(baappbase.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_9).equals(baappbase.getPayTyp())
                        || (ConstantKey.BAAPPBASE_PAYTYP_A).equals(baappbase.getPayTyp())) {
            baappbase.setBankName("");// 金融機構名稱
            baappbase.setPayBankId("");// 金融機構總代號
            baappbase.setBranchId("");// 分支代號
            baappbase.setPayEeacc("");// 銀行帳號
            // 20121203新增複驗帳號
            baappbase.setChkPayBankId("");// 金融機構總代號 複驗
            baappbase.setChkBranchId("");// 分支代號 複驗
            baappbase.setChkPayEeacc("");// 銀行帳號 複驗
            // ----------------
            baappbase.setAccIdn("");// 戶名IDN
            baappbase.setAccName("");// 戶名
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(baappbase.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(baappbase.getPayTyp())) {
            // 20130426 modified by Kiyomi 取消老年及失能年金受理作業帶入戶名之條件
            baappbase.setAccIdn(baappbase.getEvtIdnNo());// 戶名IDN
            // baappbase.setAccName(baappbase.getEvtName());// 戶名
            baappbase.setAccName("");// 戶名
        }
        baappbase.setCrtUser(userData.getEmpNo());// 新增者代號
        baappbase.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
        baappbase.setPromoteUser(bbcmf09Dao.selectPrpNoBy(baappbase.getApNo()));// 承辦者代號
        
        // Added by EthanChen 20210511 [Begin]
        // for ba_1100427619
        // 當專戶註記由空到有值時，要上專戶日期為系統日
        if("Y".equals(baappbase.getSpecialAcc())) {
        	baappbase.setSpeAccDate(DateUtility.getNowWestDate());
        }
        // Added by EthanChen 20210511 [End]

        // 新增給付主檔資料
        BigDecimal baappbaseId = baappbaseDao.insertData(baappbase);

        // Insert MMAPLOG
        baappbase.setBaappbaseId(baappbaseId);

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baappbase);
        }
        log.debug("Insert BAAPPBASE Finished ...");
        // ]

        // 新增資料到 更正記錄檔
        // [
        log.debug("Start Insert BAAPPLOG ...");
        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(baappbaseId);// 資料列編號
        baapplog.setStatus("A");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplog.setUpCol(); // 異動項目
        // baapplog.setBvalue("");// 改前內容
        // baapplog.setAvalue(); // 改後內容
        baapplogDao.insertData(baapplog);
        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 更新 給付主檔 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateBaappbaseData(UserBean userData, OldAgeAnnuityReceiptCase baappbaseData) {
        Baappbase beforBaappbase = new Baappbase();// 改前值物件
        Baappbase afterBaappbase = new Baappbase();// 改後值物件

        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, baappbaseData);
        baappbase.setProcStat("00");// 處理狀態
        baappbase.setUpdUser(userData.getEmpNo());// 異動者代號
        baappbase.setUpdTime(DateUtility.getNowWestDateTime(true));// 異動日期時間

        if ((ConstantKey.BAAPPBASE_PAYTYP_A).equals(baappbase.getPayTyp())) {
            baappbase.setBankName("");// 金融機構名稱
            baappbase.setPayBankId("");// 金融機構總代號
            baappbase.setBranchId("");// 分支代號
            baappbase.setPayEeacc("");// 銀行帳號
            // 帳號複驗
            baappbase.setChkPayBankId("");// 金融機構總代號
            baappbase.setChkBranchId("");// 分支代號
            baappbase.setChkPayEeacc("");// 銀行帳號
            baappbase.setAccIdn("");// 戶名IDN
            baappbase.setAccName("");// 戶名
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(baappbase.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(baappbase.getPayTyp())) {
            // 20130426 modified by Kiyomi 取消老年及失能年金受理作業帶入戶名之條件
            baappbase.setAccIdn(baappbase.getEvtIdnNo());// 戶名IDN
            // baappbase.setAccName(baappbase.getEvtName());// 戶名
            baappbase.setAccName("");// 戶名
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 取得改前值物件
            beforBaappbase = baappbaseDao.selectDetailDataBy(baappbase.getBaappbaseId(), null, null, null);

            // 改後值物件
            afterBaappbase = (Baappbase) BeanUtility.cloneBean(beforBaappbase);
            afterBaappbase.setApItem(baappbase.getApItem());
            afterBaappbase.setAppDate(baappbase.getAppDate());
            afterBaappbase.setCombapMark(baappbase.getCombapMark());
            afterBaappbase.setApUbno(baappbase.getApUbno());
            afterBaappbase.setEvtIds(baappbase.getEvtIds());
            afterBaappbase.setEvtIdnNo(baappbase.getEvtIdnNo());
            afterBaappbase.setEvtName(baappbase.getEvtName());
            afterBaappbase.setEvtBrDate(baappbase.getEvtBrDate());
            afterBaappbase.setEvtSex(baappbase.getEvtSex());
            afterBaappbase.setEvtNationTpe(baappbase.getEvtNationTpe());
            afterBaappbase.setEvtNationCode(baappbase.getEvtNationCode());
            afterBaappbase.setEvtJobDate(baappbase.getEvtJobDate());
            afterBaappbase.setEvtAge(baappbase.getEvtAge());
            afterBaappbase.setBenIds(baappbase.getBenIds());
            afterBaappbase.setBenIdnNo(baappbase.getEvtIdnNo());// 受益人身分證號
            afterBaappbase.setBenName(baappbase.getEvtName());// 受益人姓名
            afterBaappbase.setBenBrDate(baappbase.getEvtBrDate());// 受益人出生日期
            afterBaappbase.setBenSex(baappbase.getEvtSex());// 受益人性別
            afterBaappbase.setBenNationTyp(baappbase.getEvtNationTpe());// 受益人國籍別
            afterBaappbase.setBenNationCode(baappbase.getEvtNationCode());// 受益人國籍
            afterBaappbase.setProcStat(baappbase.getProcStat());
            afterBaappbase.setTel1(baappbase.getTel1());
            afterBaappbase.setTel2(baappbase.getTel2());
            afterBaappbase.setCommTyp(baappbase.getCommTyp());
            afterBaappbase.setCommZip(baappbase.getCommZip());
            afterBaappbase.setCommAddr(baappbase.getCommAddr());
            afterBaappbase.setPayTyp(baappbase.getPayTyp());
            afterBaappbase.setGrdIdnNo(baappbase.getGrdIdnNo());
            afterBaappbase.setGrdName(baappbase.getGrdName());
            afterBaappbase.setGrdBrDate(baappbase.getGrdBrDate());
            afterBaappbase.setAccIdn(baappbase.getAccIdn());// 戶名IDN
            afterBaappbase.setAccName(baappbase.getAccName());// 戶名
            afterBaappbase.setBankName(baappbase.getBankName());
            afterBaappbase.setPayBankId(baappbase.getPayBankId());
            afterBaappbase.setBranchId(baappbase.getBranchId());
            afterBaappbase.setPayEeacc(baappbase.getPayEeacc());
            afterBaappbase.setChkPayBankId(baappbase.getChkPayBankId());
            afterBaappbase.setChkBranchId(baappbase.getChkBranchId());
            afterBaappbase.setChkPayEeacc(baappbase.getChkPayEeacc());
            afterBaappbase.setUpdUser(baappbase.getUpdUser());// 異動者代號
            afterBaappbase.setUpdTime(baappbase.getUpdTime());// 異動日期時間
            afterBaappbase.setEvtAppName(baappbase.getEvtAppName());
            afterBaappbase.setMobilePhone(baappbase.getMobilePhone());// 手機複驗
            // Added by EthanChen 20210512 [Begin]
            // for ba_1100427619
            // 1.當專戶註記由空變Y時，專戶日期要上系統日
            // 2.當專戶註記由Y變空時，要清空專戶日期
            if(StringUtils.isBlank(beforBaappbase.getSpecialAcc()) && "Y".equals(baappbase.getSpecialAcc())) {
            	afterBaappbase.setSpeAccDate(DateUtility.getNowWestDate());
            }else if("Y".equals(beforBaappbase.getSpecialAcc()) && StringUtils.isBlank(baappbase.getSpecialAcc())) {
            	afterBaappbase.setSpeAccDate("");
            }
            afterBaappbase.setSpecialAcc(baappbase.getSpecialAcc());
            // Added by EthanChen 20210512 [End]
        }

        // 更新到 給付主檔
        baappbaseDao.updateData(afterBaappbase);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforBaappbase, afterBaappbase);
        }
        log.debug("Update BAAPPBASE Finished ...");
        // ]

        // // 新增資料到 更正記錄檔
        // // [
        log.debug("Start Insert BAAPPLOG ...");
        BigDecimal baappbaseId = baappbaseData.getBaappbaseId();
        List<Baapplog> logList = baappbaseData.getBaapplogList();
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
    }

    /**
     * 刪除 給付主檔 資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     */
    public void deleteBaappbaseData(UserBean userData, OldAgeAnnuityReceiptCase baappbaseData) {
        // 刪除 給付主檔
        // [
        log.debug("Start Delete BAAPPBASE ...");
        BigDecimal baappbaseId = baappbaseData.getBaappbaseId();

        // 取得改前值物件
        Baappbase beforBaappbase = baappbaseDao.selectDetailDataBy(baappbaseData.getBaappbaseId(), null, null, null);

        // 刪除 給付主檔
        baappbaseDao.deleteData(baappbaseId, "00");

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteLog(beforBaappbase);
        }
        log.debug("Delete BAAPPBASE Finished ...");
        // ]

        // // 新增資料到 更正記錄檔
        // // [
        log.debug("Start Insert BAAPPLOG ...");
        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(baappbaseId);// 資料列編號

        baapplog.setStatus("D");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplog.setUpCol(); // 異動項目 - Log 已設
        // // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
        // // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
        baapplogDao.insertData(baapplog);
        log.debug("Insert BAAPPLOG Finished ...");
        // // ]
    }

    // ------------------------------ 老年年金受理作業 ------------------------------

    // ------------------------------ 失能年金受理作業 ------------------------------
    /**
     * 依畫面輸入欄位條件轉換 新增/修改 給付主檔 部分欄位<br>
     * 
     * @param caseObj 失能年金受理作業 事故者資料
     * @return
     */
    /**
     * 依畫面輸入欄位條件轉換 新增/修改 給付主檔 部分欄位<br>
     * 
     * @param caseObj 失能年金受理作業 事故者資料
     * @return
     */
    public DisabledAnnuityReceiptEvtCase transDisabledEvtInputData(DisabledAnnuityReceiptEvtCase caseObj) {
        // 日期轉換：「申請日期」、「事故者出生日期」、「法定代理人出生日期」、「診斷失能日期」
        // 轉換成西元年日期再回存，其轉換後的日期格式為YYYYMMDD。
        if (StringUtils.isNotBlank(caseObj.getAppDate())) {
            caseObj.setAppDate(DateUtility.changeDateType(caseObj.getAppDate()));
        }
        if (StringUtils.isNotBlank(caseObj.getEvtBrDate())) {
            caseObj.setEvtBrDate(DateUtility.changeDateType(caseObj.getEvtBrDate()));
        }
        if (StringUtils.isNotBlank(caseObj.getGrdBrDate())) {
            caseObj.setGrdBrDate(DateUtility.changeDateType(caseObj.getGrdBrDate()));
        }
        if (StringUtils.isNotBlank(caseObj.getEvtJobDate())) {
            caseObj.setEvtJobDate(DateUtility.changeDateType(caseObj.getEvtJobDate()));
        }

        // 根據 事故者身分證號, 出生日期 取得戶政資料
        Cvldtl cvldtlData = selectCvldtlNameBy(caseObj.getEvtIdnNo(), caseObj.getEvtBrDate());

        // 「國籍別」=1時
        // 「性別」=「事故者身分證號」的第2碼
        // 「國籍」= '000'
        // 「國籍別」=2時
        // 「性別」= 畫面中的「性別」欄位
        // 「國籍」= 畫面中的「國籍」欄位
        if (("1").equals(caseObj.getEvtNationTpe())) {
            if (StringUtils.isNotBlank(caseObj.getEvtIdnNo()) && caseObj.getEvtIdnNo().length() >= 2) {
                caseObj.setEvtSex(caseObj.getEvtIdnNo().substring(1, 2));
            }
            caseObj.setEvtNationCode("000");
        }

        // 「社福識別碼」若查無資料時，則「社福識別碼」= ' '。
        if (("").equals(cvldtlData.getNpIds()) || cvldtlData.getNpIds() == null) {
            caseObj.setEvtIds("");
            caseObj.setBenIds("");
        }
        else {
            caseObj.setEvtIds(cvldtlData.getNpIds());
            caseObj.setBenIds(cvldtlData.getNpIds());
        }

        // 「通訊郵遞區號」、「通訊地址」：
        // 當「通訊地址別」=1時，「通訊郵遞區號」、「通訊地址」=戶籍檔的「通訊郵遞區號」、「通訊地址」；
        // 當「通訊地址別」=2時，「通訊郵遞區號」、「通訊地址」=【BAAP0D011A】畫面中的「通訊郵遞區號」、「通訊地址」欄位。
        if ((ConstantKey.BAAPPBASE_COMMTYP_1).equals(caseObj.getCommTyp())) {
            caseObj.setCommZip(cvldtlData.getHzip());
            caseObj.setCommAddr(cvldtlData.getHaddr());
        }
        else if ((ConstantKey.BAAPPBASE_COMMTYP_2).equals(caseObj.getCommTyp())) {
            caseObj.setCommZip(caseObj.getCommZip());
            caseObj.setCommAddr(caseObj.getCommAddr());
        }
        else {
            caseObj.setCommZip("");
            caseObj.setCommAddr("");
        }
        // 「事故者年齡」：「事故者出生日期」－「申請日期」
        caseObj.setEvtAge(BaBusinessUtility.calAge(caseObj.getAppDate(), caseObj.getEvtBrDate()));

        // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」
        // 當「給付方式」=1時，「金融機構名稱」= 銀行名稱(「金融機構名稱一」+「金融機構名稱二」)
        // 「金融機構總代號」=「帳號」(前：1~3碼)。
        // 「分支代號」=「帳號」(前：4~7碼)。
        // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
        String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
        String accountB = StringUtils.leftPad(caseObj.getPayEeacc(), 14, "0");// 帳號(後)

        if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(caseObj.getPayTyp())) {
            caseObj.setBankName(getBankName(accountF));
            if (accountF.length() >= 3) {
                caseObj.setPayBankId(accountF.substring(0, 3));
            }
            else {
                caseObj.setPayBankId(accountF.substring(0, accountF.length()));
            }
            if (accountF.length() >= 4) {
                caseObj.setBranchId(accountF.substring(3, accountF.length()));
            }
            else {
                caseObj.setBranchId("");
            }
            caseObj.setPayEeacc(accountB);
        }
        // 當「給付方式」=2時，「金融機構名稱」= 郵局名稱
        // 「金融機構總代號」=「帳號」(前：1~3碼)。
        // 「分支代號」=「帳號」(前：4~7碼)。
        // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
        else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(caseObj.getPayTyp())) {
            caseObj.setBankName(getPostName(caseObj.getPayBankIdBranchId()));
            // caseObj.setPayBankId(ConstantKey.BAAPPBASE_PAYTYP_2_PAYBANKID);
            // caseObj.setBranchId(ConstantKey.BAAPPBASE_PAYTYP_2_BRANCHID);
            if (accountF.length() >= 3) {
                caseObj.setPayBankId(accountF.substring(0, 3));
            }
            else {
                caseObj.setPayBankId(accountF.substring(0, accountF.length()));
            }
            if (accountF.length() >= 4) {
                caseObj.setBranchId(accountF.substring(3, accountF.length()));
            }
            else {
                caseObj.setBranchId("");
            }
            caseObj.setPayEeacc(accountB);
        }
        // 「給付方式」=3、4、A時，「金融機構名稱」= ' '
        // 「金融機構總代號」= ' '
        // 「分支代號」= ' '
        // 「銀行帳號」= ' '
        else {
            caseObj.setBankName("");
            caseObj.setPayBankId("");
            caseObj.setBranchId("");
            caseObj.setPayEeacc("");

        }

        // 帳號複驗 轉換 20121203更新
        // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」
        // 當「給付方式」=1時，「金融機構名稱」= 銀行名稱(「金融機構名稱一」+「金融機構名稱二」)
        // 「金融機構總代號」=「帳號」(前：1~3碼)。
        // 「分支代號」=「帳號」(前：4~7碼)。
        // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
        String accountChkF = caseObj.getChkPayBankIdChkBranchId();// 帳號(前)
        String accountChkB = StringUtils.leftPad(caseObj.getChkPayEeacc(), 14, "0");// 帳號(後)

        if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(caseObj.getPayTyp())) {

            if (accountChkF.length() >= 3) {
                caseObj.setChkPayBankId(accountChkF.substring(0, 3));
            }
            else {
                caseObj.setChkPayBankId(accountChkF.substring(0, accountChkF.length()));
            }
            if (accountChkF.length() >= 4) {
                caseObj.setChkBranchId(accountChkF.substring(3, accountChkF.length()));
            }
            else {
                caseObj.setChkBranchId("");
            }
            caseObj.setChkPayEeacc(accountChkB);
        }
        // 當「給付方式」=2時，「金融機構名稱」= 郵局名稱
        // 「金融機構總代號」=「帳號」(前：1~3碼)。
        // 「分支代號」=「帳號」(前：4~7碼)。
        // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
        else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(caseObj.getPayTyp())) {

            if (accountChkF.length() >= 3) {
                caseObj.setChkPayBankId(accountChkF.substring(0, 3));
            }
            else {
                caseObj.setChkPayBankId(accountChkF.substring(0, accountChkF.length()));
            }
            if (accountChkF.length() >= 4) {
                caseObj.setChkBranchId(accountChkF.substring(3, accountChkF.length()));
            }
            else {
                caseObj.setChkBranchId("");
            }
            caseObj.setChkPayEeacc(accountChkB);
        }
        // 「給付方式」=3、4時，「金融機構名稱」= ' '
        // 「金融機構總代號」= ' '
        // 「分支代號」= ' '
        // 「銀行帳號」= ' '
        else {
            caseObj.setChkPayBankId("");
            caseObj.setChkBranchId("");
            caseObj.setChkPayEeacc("");
        }

        // 「醫療院所代碼」：長度不足10碼時，資料左補0補足10碼長度。
        String hosId = caseObj.getHosId();
        if (StringUtils.isNotBlank(hosId)) {
            hosId = StringUtils.leftPad(caseObj.getHosId(), 10, "0");
        }
        caseObj.setHosId(hosId);

        // 手機複驗 & 有無診斷書
        String tel1 = caseObj.getTel1();
        String tel2 = caseObj.getTel2();
        String shortTel1 = "";
        String shortTel2 = "";
        if (tel1.length() >= 2) {
            shortTel1 = tel1.substring(0, 2);
        }
        if (tel2.length() >= 2) {
            shortTel2 = tel2.substring(0, 2);
        }

        if (!((StringUtils.equals(shortTel1, "09") && tel1.length() == 10) || (StringUtils.equals(shortTel2, "09") && tel2.length() == 10))) {
            caseObj.setMobilePhone("");
        }

        if (StringUtils.isBlank(caseObj.getMobilePhone())) {
            caseObj.setEvtHandIcapMk("");
        }

        // 受傷部位、失能項目、醫師姓名、國際疾病代碼 欄位順序調整
        try {
            for (int x = 1; x <= 4; x++) {
                int itemAmt = 0;
                String itemName = "";
                // 受傷部位
                if (x == 1) {
                    itemAmt = 3;
                    itemName = "criInPart";
                }
                // 失能項目
                else if (x == 2) {
                    itemAmt = 10;
                    itemName = "criInJdp";
                }
                // 醫師姓名
                else if (x == 3) {
                    itemAmt = 2;
                    itemName = "doctorName";
                }
                // 國際疾病代碼
                else if (x == 4) {
                    itemAmt = 4;
                    itemName = "criInJnme";
                }

                List<String> valueList = new ArrayList();
                // 判斷有值的欄位
                for (int i = 1; i <= itemAmt; i++) {
                    String tmpValue = StringUtils.defaultString(ConvertUtils.convert(PropertyUtils.getProperty(caseObj, itemName + i)));
                    PropertyUtils.setProperty(caseObj, itemName + i, null);
                    if (StringUtils.isNotBlank(tmpValue)) {
                        valueList.add(tmpValue);
                    }
                }
                // 將有值的欄位依序填入
                for (int i = 1; i <= valueList.size(); i++) {
                    String value = valueList.get(i - 1);
                    PropertyUtils.setProperty(caseObj, itemName + i, value);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return caseObj;
    }

    /**
     * 依畫面輸入欄位條件轉換 新增/修改 給付主檔 部分欄位<br>
     * 
     * @param caseObj 失能年金受理作業 事故者資料 For 國保資料
     * @return
     */
    public DisabledAnnuityReceiptEvtCase transDisabledEvtInputDataFor36(DisabledAnnuityReceiptEvtCase caseObj) {
        // 日期轉換：「申請日期」、「事故者出生日期」、「法定代理人出生日期」、「診斷失能日期」
        // 轉換成西元年日期再回存，其轉換後的日期格式為YYYYMMDD。
        if (StringUtils.isNotBlank(caseObj.getAppDate())) {
            caseObj.setAppDate(DateUtility.changeDateType(caseObj.getAppDate()));
        }
        if (StringUtils.isNotBlank(caseObj.getEvtBrDate())) {
            caseObj.setEvtBrDate(DateUtility.changeDateType(caseObj.getEvtBrDate()));
        }
        if (StringUtils.isNotBlank(caseObj.getGrdBrDate())) {
            caseObj.setGrdBrDate(DateUtility.changeDateType(caseObj.getGrdBrDate()));
        }
        if (StringUtils.isNotBlank(caseObj.getEvtJobDate())) {
            caseObj.setEvtJobDate(DateUtility.changeDateType(caseObj.getEvtJobDate()));
        }

        // 「國籍別」=1時
        // 「性別」=「事故者身分證號」的第2碼
        // 「國籍」= '000'
        // 「國籍別」=2時
        // 「性別」= 畫面中的「性別」欄位
        // 「國籍」= 畫面中的「國籍」欄位
        if (("1").equals(caseObj.getEvtNationTpe())) {
            if (StringUtils.isNotBlank(caseObj.getEvtIdnNo()) && caseObj.getEvtIdnNo().length() >= 2) {
                caseObj.setEvtSex(caseObj.getEvtIdnNo().substring(1, 2));
            }
            caseObj.setEvtNationCode("000");
        }

        // 「通訊郵遞區號」、「通訊地址」：
        if ((ConstantKey.BAAPPBASE_COMMTYP_1).equals(caseObj.getCommTyp())) {
            Cvldtl cvldtlData = cvldtlDao.selectHaddrBy(caseObj.getEvtIds());
            if (cvldtlData != null) {
                caseObj.setCommZip(cvldtlData.getHzip());
                caseObj.setCommAddr(cvldtlData.getHaddr());
            }
        }
        else if ((ConstantKey.BAAPPBASE_COMMTYP_2).equals(caseObj.getCommTyp())) {
            // Cvldtl cvldtlData = cvldtlDao.selectInfoBy(caseObj.getEvtIds(),"");
            // caseObj.setCommZip(cvldtlData.getV_commzip());
            // caseObj.setCommAddr(cvldtlData.getV_commaddr());
        }

        // 「事故者年齡」：「事故者出生日期」－「申請日期」
        caseObj.setEvtAge(BaBusinessUtility.calAge(caseObj.getAppDate(), caseObj.getEvtBrDate()));

        // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」
        // 當「給付方式」=1時，「金融機構名稱」= 銀行名稱(「金融機構名稱一」+「金融機構名稱二」)
        // 「金融機構總代號」=「帳號」(前：1~3碼)。
        // 「分支代號」=「帳號」(前：4~7碼)。
        // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
        String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
        String accountB = StringUtils.leftPad(caseObj.getPayEeacc(), 14, "0");// 帳號(後)

        if (StringUtils.isNotBlank(accountF)) {

            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(caseObj.getPayTyp())) {
                caseObj.setBankName(getBankName(accountF));
                if (accountF.length() >= 3) {
                    caseObj.setPayBankId(accountF.substring(0, 3));
                }
                else {
                    caseObj.setPayBankId(accountF.substring(0, accountF.length()));
                }
                if (accountF.length() >= 4) {
                    caseObj.setBranchId(accountF.substring(3, accountF.length()));
                }
                else {
                    caseObj.setBranchId("");
                }
                caseObj.setPayEeacc(accountB);
            }
            // 當「給付方式」=2時，「金融機構名稱」= 郵局名稱
            // 「金融機構總代號」=「帳號」(前：1~3碼)。
            // 「分支代號」=「帳號」(前：4~7碼)。
            // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
            else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(caseObj.getPayTyp())) {
                caseObj.setBankName(getPostName(caseObj.getPayBankIdBranchId()));
                // caseObj.setPayBankId(ConstantKey.BAAPPBASE_PAYTYP_2_PAYBANKID);
                // caseObj.setBranchId(ConstantKey.BAAPPBASE_PAYTYP_2_BRANCHID);
                if (accountF.length() >= 3) {
                    caseObj.setPayBankId(accountF.substring(0, 3));
                }
                else {
                    caseObj.setPayBankId(accountF.substring(0, accountF.length()));
                }
                if (accountF.length() >= 4) {
                    caseObj.setBranchId(accountF.substring(3, accountF.length()));
                }
                else {
                    caseObj.setBranchId("");
                }
                caseObj.setPayEeacc(accountB);
            }
            // 「給付方式」=3、4、A時，「金融機構名稱」= ' '
            // 「金融機構總代號」= ' '
            // 「分支代號」= ' '
            // 「銀行帳號」= ' '
            else {
                caseObj.setBankName("");
                caseObj.setPayBankId("");
                caseObj.setBranchId("");
                caseObj.setPayEeacc("");

            }

        }

        // 帳號複驗 轉換 20121203更新
        // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」
        // 當「給付方式」=1時，「金融機構名稱」= 銀行名稱(「金融機構名稱一」+「金融機構名稱二」)
        // 「金融機構總代號」=「帳號」(前：1~3碼)。
        // 「分支代號」=「帳號」(前：4~7碼)。
        // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
        String accountChkF = caseObj.getChkPayBankIdChkBranchId();// 帳號(前)
        String accountChkB = StringUtils.leftPad(caseObj.getChkPayEeacc(), 14, "0");// 帳號(後)

        if (StringUtils.isNotBlank(accountChkF)) {

            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(caseObj.getPayTyp())) {

                if (accountChkF.length() >= 3) {
                    caseObj.setChkPayBankId(accountChkF.substring(0, 3));
                }
                else {
                    caseObj.setChkPayBankId(accountChkF.substring(0, accountChkF.length()));
                }
                if (accountChkF.length() >= 4) {
                    caseObj.setChkBranchId(accountChkF.substring(3, accountChkF.length()));
                }
                else {
                    caseObj.setChkBranchId("");
                }
                caseObj.setChkPayEeacc(accountChkB);
            }
            // 當「給付方式」=2時，「金融機構名稱」= 郵局名稱
            // 「金融機構總代號」=「帳號」(前：1~3碼)。
            // 「分支代號」=「帳號」(前：4~7碼)。
            // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
            else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(caseObj.getPayTyp())) {

                if (accountChkF.length() >= 3) {
                    caseObj.setChkPayBankId(accountChkF.substring(0, 3));
                }
                else {
                    caseObj.setChkPayBankId(accountChkF.substring(0, accountChkF.length()));
                }
                if (accountChkF.length() >= 4) {
                    caseObj.setChkBranchId(accountChkF.substring(3, accountChkF.length()));
                }
                else {
                    caseObj.setChkBranchId("");
                }
                caseObj.setChkPayEeacc(accountChkB);
            }
            // 「給付方式」=3、4時，「金融機構名稱」= ' '
            // 「金融機構總代號」= ' '
            // 「分支代號」= ' '
            // 「銀行帳號」= ' '
            else {
                caseObj.setChkPayBankId("");
                caseObj.setChkBranchId("");
                caseObj.setChkPayEeacc("");
            }

        }
        // 「醫療院所代碼」：長度不足10碼時，資料左補0補足10碼長度。
        String hosId = caseObj.getHosId();
        if (StringUtils.isNotBlank(hosId)) {
            hosId = StringUtils.leftPad(caseObj.getHosId(), 10, "0");
        }
        caseObj.setHosId(hosId);

        // 手機複驗 & 有無診斷書
        String tel1 = caseObj.getTel1();
        String tel2 = caseObj.getTel2();
        String shortTel1 = "";
        String shortTel2 = "";
        if (tel1.length() >= 2) {
            shortTel1 = tel1.substring(0, 2);
        }
        if (tel2.length() >= 2) {
            shortTel2 = tel2.substring(0, 2);
        }

        if (!((StringUtils.equals(shortTel1, "09") && tel1.length() == 10) || (StringUtils.equals(shortTel2, "09") && tel2.length() == 10))) {
            caseObj.setMobilePhone("");
        }

        if (StringUtils.isBlank(caseObj.getMobilePhone())) {
            caseObj.setEvtHandIcapMk("");
        }

        // 受傷部位、失能項目、醫師姓名、國際疾病代碼 欄位順序調整
        try {
            for (int x = 1; x <= 4; x++) {
                int itemAmt = 0;
                String itemName = "";
                // 受傷部位
                if (x == 1) {
                    itemAmt = 3;
                    itemName = "criInPart";
                }
                // 失能項目
                else if (x == 2) {
                    itemAmt = 10;
                    itemName = "criInJdp";
                }
                // 醫師姓名
                else if (x == 3) {
                    itemAmt = 2;
                    itemName = "doctorName";
                }
                // 國際疾病代碼
                else if (x == 4) {
                    itemAmt = 4;
                    itemName = "criInJnme";
                }

                List<String> valueList = new ArrayList();
                // 判斷有值的欄位
                for (int i = 1; i <= itemAmt; i++) {
                    String tmpValue = StringUtils.defaultString(ConvertUtils.convert(PropertyUtils.getProperty(caseObj, itemName + i)));
                    PropertyUtils.setProperty(caseObj, itemName + i, null);
                    if (StringUtils.isNotBlank(tmpValue)) {
                        valueList.add(tmpValue);
                    }
                }
                // 將有值的欄位依序填入
                for (int i = 1; i <= valueList.size(); i++) {
                    String value = valueList.get(i - 1);
                    PropertyUtils.setProperty(caseObj, itemName + i, value);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return caseObj;
    }

    /**
     * 依傳入條件取得 給付主檔 資料清單 for 失能年金受理作業
     * 
     * @param evtIdnNo 事故者身分證號
     * @param apNo 受理編號
     * @return
     */
    public List<DisabledAnnuityReceiptCase> getBaappbaseDataListForDisabled(String evtIdnNo, String apNo) {
        List<Baappbase> list = baappbaseDao.selectDataForAnnuityReceiptBy(evtIdnNo, apNo, "0000", new String[] { "00" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
        List<DisabledAnnuityReceiptCase> returnList = new ArrayList<DisabledAnnuityReceiptCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            DisabledAnnuityReceiptCase caseObj = new DisabledAnnuityReceiptCase();
            BeanUtility.copyProperties(caseObj, baappbase);

            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付紀錄資料 for 失能年金給付受理
     * 
     * @param apNo 國寶受理編號
     * @return 內含 <code>Nbappbase</code> 物件的 List
     */
    public List<DisabledAnnuityReceiptEvtCase> selectNbappbaseDataBy(String apNo) {
        List<Nbappbase> list = nbappbaseDao.selectDataBy(apNo, "0000");
        String maxApnoStr = baappbaseDao.getMaxApno().substring(1, 12);
        Long maxApno = Long.parseLong(maxApnoStr);
        List<DisabledAnnuityReceiptEvtCase> returnList = new ArrayList<DisabledAnnuityReceiptEvtCase>();
        for (int i = 0; i < list.size(); i++) {
            Nbappbase nbappbase = (Nbappbase) list.get(i);
            DisabledAnnuityReceiptEvtCase caseObj = new DisabledAnnuityReceiptEvtCase();
            BeanUtility.copyProperties(caseObj, nbappbase);
            // 國保相關受理編號
            caseObj.setMapNo(nbappbase.getApNo());
            // 給予最大受理編號
            String nextApno = String.valueOf(maxApno + 1);
            caseObj.setApNo("K" + nextApno);
            caseObj.setApNo1(caseObj.getApNo().substring(0, 1));
            caseObj.setApNo2(caseObj.getApNo().substring(1, 2));
            caseObj.setApNo3(caseObj.getApNo().substring(2, 7));
            caseObj.setApNo4(caseObj.getApNo().substring(7, 12));
            // 事故者出生日期
            caseObj.setEvtBrDate(nbappbase.getEvteeBirt());
            // 事故者姓名
            caseObj.setEvtName(nbappbase.getEvteeName());
            // 事故者死亡日期
            caseObj.setEvtDieDate(nbappbase.getDieDate());
            // 受益人出生日期
            caseObj.setBenBrDate(nbappbase.getBeneeBirt());
            // 受益人姓名
            caseObj.setBenName(nbappbase.getBeneeName());
            // 電話1 電話2
            caseObj.setTel1(nbappbase.getCommTel());
            caseObj.setTel2(nbappbase.getMobile());
            // 通訊地址別
            caseObj.setCommTyp(nbappbase.getCommType());
            // 給付方式 國勞不一致(待釐清)
            caseObj.setPayTyp(nbappbase.getPayType());
            // 法定代理人出生日期
            caseObj.setGrdBrDate(nbappbase.getGrdBirth());
            // 傷病分類
            caseObj.setEvTyp("4");
            // 事故者國籍別
            caseObj.setEvtNationTpe("1");
            // 事故者國籍
            caseObj.setEvtNationCode("000");
            // 銀行帳號
            caseObj.setPayBankIdBranchId(nbappbase.getPayBankId() + nbappbase.getBranchId());
            // 銀行帳號
            caseObj.setChkPayBankIdChkBranchId(nbappbase.getPayBankId() + nbappbase.getBranchId());
            // 銀行帳號
            caseObj.setChkPayEeacc(caseObj.getPayEeacc());

            // 取得診斷失能日期 evtJobDate = ctrlBdt
            int nbexcepCount = nbexcepDao.selectCountForNbexcep(nbappbase.getEvtIds());
            String ctrlBdt = "";
            if (nbexcepCount > 0) {
                ctrlBdt = nbexcepDao.selectCtrlBdtBy(nbappbase.getEvtIds());
            }
            if (StringUtils.isBlank(ctrlBdt)) {
                String handIcApDt = (StringUtils.isNotBlank(nbappbase.getHandIcApDt()) ? nbappbase.getHandIcApDt() : "0");
                String diaDabDt = (StringUtils.isNotBlank(nbappbase.getDiaDabDt()) ? nbappbase.getDiaDabDt() : "0");

                if (Integer.parseInt(handIcApDt) > Integer.parseInt(diaDabDt)) {
                    ctrlBdt = handIcApDt.equals("0") ? "" : handIcApDt;
                }
                else {
                    ctrlBdt = diaDabDt.equals("0") ? "" : handIcApDt;
                }
            }
            caseObj.setEvtJobDate(ctrlBdt);

            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔 詳細資料 for 失能年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public DisabledAnnuityReceiptEvtCase getBaappbaseDetailForDisabled(BigDecimal baappbaseId) {
        Baappbase baappbase = baappbaseDao.selectDetailDataForDisabledAnnuityReceiptBy(baappbaseId, "0000", new String[] { "00" }, "in");
        DisabledAnnuityReceiptEvtCase caseObj = null;
        if (baappbase != null) {
            caseObj = new DisabledAnnuityReceiptEvtCase();
            BeanUtility.copyProperties(caseObj, baappbase);
            caseObj.setEvtHandIcapMk(baappbase.getHandIcapMk());
        }
        return caseObj;
    }

    /**
     * 新增 失能年金資料
     * 
     * @param userData user資料
     * @param evtCase 事故者資料
     * @param famCaseList 眷屬資料
     */
    public void insertDataForDisabled(UserBean userData, DisabledAnnuityReceiptEvtCase evtCase, BigDecimal bafamilytempId) {
        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAAPPBASE ...");
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, evtCase);
        baappbase.setSeqNo("0000");// 序號 本人
        baappbase.setImk("1");// 保險別
        baappbase.setApItem("0");// 申請項目
        baappbase.setPayKind("35");// 給付種類
        baappbase.setCaseTyp("1");// 案件類別
        baappbase.setProcStat("00");// 處理狀態
        baappbase.setEvtAppName(baappbase.getEvtName());// 事故者申請時姓名
        baappbase.setEvtIdent("1");// 事故者身分別
        baappbase.setBenIdnNo(baappbase.getEvtIdnNo());// 受益人身分證號
        baappbase.setBenName(baappbase.getEvtName());// 受益人姓名
        baappbase.setBenBrDate(baappbase.getEvtBrDate());// 受益人出生日期
        baappbase.setBenSex(baappbase.getEvtSex());// 受益人性別
        baappbase.setBenNationTyp(baappbase.getEvtNationTpe());// 受益人國籍別
        baappbase.setBenNationCode(baappbase.getEvtNationCode());// 受益人國籍
        baappbase.setBenEvtRel("1");// 受益人與事故者關係
        baappbase.setBenPayMk("1");// 受益人領取狀態註記
        baappbase.setAccRel("1");// 戶名與受益人關係
        baappbase.setAccSeqNo("0000");// 被共同具領之受款人員序號
        if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(baappbase.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(baappbase.getPayTyp())) {
            // 20130426 modified by Kiyomi 取消老年及失能年金受理作業帶入戶名之條件
            baappbase.setAccIdn(baappbase.getEvtIdnNo());// 戶名IDN
            // baappbase.setAccName(baappbase.getEvtName());// 戶名
            baappbase.setAccName("");// 戶名
        }
        else {
            baappbase.setAccIdn("");// 戶名IDN
            baappbase.setAccName("");// 戶名
        }
        baappbase.setMitRate(new BigDecimal(0));
        baappbase.setCrtUser(userData.getEmpNo());// 新增者代號
        baappbase.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
        baappbase.setPromoteUser(bbcmf09Dao.selectPrpNoBy(baappbase.getApNo()));// 承辦者代號

        BigDecimal baappbaseId = baappbaseDao.insertDataForDisabledAnnuityReceipt(baappbase);
        log.debug("Insert BAAPPBASE Finished ...");
        // ]

        // 新增資料到 給付延伸主檔
        // [
        log.debug("Start Insert BAAPPEXPAND ...");
        Baappexpand baappexpand = new Baappexpand();
        BeanUtility.copyProperties(baappexpand, evtCase);
        baappexpand.setBaappbaseId(baappbaseId);// 給付主檔序號
        baappexpand.setSeqNo("0000");// 序號 本人
        baappexpand.setCrtUser(userData.getEmpNo());// 新增者代號
        baappexpand.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
        baappexpand.setHandIcapMk(evtCase.getEvtHandIcapMk());// 有無診斷書
        baappexpand.setEvAppTyp(evtCase.getEvTyp());// 申請傷病分類
        BigDecimal baappexpandId = baappexpandDao.insertDataForDisabledAnnuityReceipt(baappexpand);
        log.debug("Insert BAAPPEXPAND Finished ...");
        // ]

        // 將眷屬暫存檔資料搬至眷屬檔
        // [
        // 取得眷屬暫存檔資料
        List<Bafamilytemp> famTmpDataList = bafamilytempDao.selectDataBy(bafamilytempId, null);
        List<Bafamily> famDataList = new ArrayList<Bafamily>();
        for (int i = 0; i < famTmpDataList.size(); i++) {
            Bafamilytemp famData = famTmpDataList.get(i);
            Bafamily bafamily = new Bafamily();
            BeanUtility.copyProperties(bafamily, famData);
            bafamily.setBaappbaseId(baappbaseId);// 給付主檔資料列編號
            bafamily.setApNo(evtCase.getApNo());
            bafamily.setSeqNo(StringUtils.leftPad(String.valueOf(i + 1), 2, "0"));// 序號 (照順序重新編排, 不照暫存檔內的序號儲存)
            bafamily.setCrtUser(userData.getEmpNo());// 新增者代號
            bafamily.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
            famDataList.add(bafamily);
        }

        // 儲存眷屬檔資料
        bafamilyDao.insertDataForDisabledAnnuityReceipt(famDataList);

        // 搬檔完成後刪除眷屬暫存檔資料
        bafamilytempDao.deleteDataBy(bafamilytempId, null);
        // ]

        // Insert MMAPLOG
        // [
        baappbase.setBaappbaseId(baappbaseId);
        baappexpand.setBaappexpandId(baappexpandId);
        log.debug("Start Insert MMAPLOG ...");
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baappbase);
            FrameworkLogUtil.doInsertLog(baappexpand);
            FrameworkLogUtil.doInsertListLog(famDataList);
        }

        log.debug("Insert MMAPLOG Finished ...");
        // ]

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");
        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(baappbaseId);// 資料列編號
        baapplog.setStatus("A");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplog.setUpCol(); // 異動項目
        // baapplog.setBvalue("");// 改前內容
        // baapplog.setAvalue(); // 改後內容
        baapplogDao.insertData(baapplog);
        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 新增 失能年金資料 For 國保
     * 
     * @param userData user資料
     * @param evtCase 事故者資料
     * @param famCaseList 眷屬資料
     */
    public void insertDataForDisabled36Data(UserBean userData, DisabledAnnuityReceiptEvtCase evtCase, BigDecimal bafamilytempId) {
        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAAPPBASE ...");
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, evtCase);
        baappbase.setSeqNo("0000");// 序號 本人
        baappbase.setImk("1");// 保險別
        baappbase.setApItem("0");// 申請項目
        baappbase.setPayKind("36");// 給付種類
        baappbase.setCaseTyp("1");// 案件類別
        baappbase.setProcStat("00");// 處理狀態
        baappbase.setCombapMark("Y");
        baappbase.setEvtAppName(baappbase.getEvtName());// 事故者申請時姓名
        baappbase.setBenNationTyp(baappbase.getEvtNationTpe());// 受益人國籍別
        baappbase.setBenNationCode(baappbase.getEvtNationCode());// 受益人國籍
        baappbase.setAccSeqNo("0000");// 被共同具領之受款人員序號
        baappbase.setMitRate(new BigDecimal(0));
        baappbase.setNotifyForm("999");
        baappbase.setCrtUser(userData.getEmpNo());// 新增者代號
        baappbase.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
        baappbase.setPromoteUser(bbcmf09Dao.selectPrpNoBy(baappbase.getApNo()));// 承辦者代號

        BigDecimal baappbaseId = baappbaseDao.insertDataDisabledAnnuityReceiptFor36(baappbase);
        log.debug("Insert BAAPPBASE Finished ...");
        // ]

        // 新增資料到 給付延伸主檔
        // [
        log.debug("Start Insert BAAPPEXPAND ...");
        Baappexpand baappexpand = new Baappexpand();
        BeanUtility.copyProperties(baappexpand, evtCase);
        baappexpand.setBaappbaseId(baappbaseId);// 給付主檔序號
        baappexpand.setSeqNo("0000");// 序號 本人
        baappexpand.setCrtUser(userData.getEmpNo());// 新增者代號
        baappexpand.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
        baappexpand.setHandIcapMk(evtCase.getEvtHandIcapMk());// 有無診斷書
        baappexpand.setEvAppTyp(evtCase.getEvTyp());// 申請傷病分類
        BigDecimal baappexpandId = baappexpandDao.insertDataForDisabledAnnuityReceipt(baappexpand);
        log.debug("Insert BAAPPEXPAND Finished ...");
        // ]

        // 將眷屬暫存檔資料搬至眷屬檔
        // [
        // 取得眷屬暫存檔資料
        List<Bafamilytemp> famTmpDataList = bafamilytempDao.selectDataBy(bafamilytempId, null);
        List<Bafamily> famDataList = new ArrayList<Bafamily>();
        for (int i = 0; i < famTmpDataList.size(); i++) {
            Bafamilytemp famData = famTmpDataList.get(i);
            Bafamily bafamily = new Bafamily();
            BeanUtility.copyProperties(bafamily, famData);
            bafamily.setBaappbaseId(baappbaseId);// 給付主檔資料列編號
            bafamily.setApNo(evtCase.getApNo());
            bafamily.setSeqNo(StringUtils.leftPad(String.valueOf(i + 1), 2, "0"));// 序號 (照順序重新編排, 不照暫存檔內的序號儲存)
            // bafamily.setCrtUser(userData.getEmpNo());// 新增者代號
            bafamily.setCrtUser("BaCm");// 新增者代號
            bafamily.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
            famDataList.add(bafamily);
        }

        // 儲存眷屬檔資料
        bafamilyDao.insertDataForDisabledAnnuityReceipt(famDataList);

        // 搬檔完成後刪除眷屬暫存檔資料
        bafamilytempDao.deleteDataBy(bafamilytempId, null);
        // ]

        // Insert MMAPLOG
        // [
        baappbase.setBaappbaseId(baappbaseId);
        baappexpand.setBaappexpandId(baappexpandId);
        log.debug("Start Insert MMAPLOG ...");
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baappbase);
            FrameworkLogUtil.doInsertLog(baappexpand);
            FrameworkLogUtil.doInsertListLog(famDataList);
        }

        log.debug("Insert MMAPLOG Finished ...");
        // ]

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");
        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(baappbaseId);// 資料列編號
        baapplog.setStatus("A");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplog.setUpCol(); // 異動項目
        // baapplog.setBvalue("");// 改前內容
        // baapplog.setAvalue(); // 改後內容
        baapplogDao.insertData(baapplog);
        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 更新 給付主檔 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDisabled(UserBean userData, DisabledAnnuityReceiptEvtCase evtCase) {
        Baappbase beforBaappbase = new Baappbase();// 改前值物件
        Baappbase afterBaappbase = new Baappbase();// 改後值物件

        Baappexpand beforBaappexpand = new Baappexpand();// 延伸主檔改前值物件
        Baappexpand afterBaappexpand = new Baappexpand();// 延伸主檔改後直物件

        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");
        BigDecimal baappbaseId = evtCase.getBaappbaseId();
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, evtCase);
        baappbase.setProcStat("00");
        baappbase.setEvtAppName(baappbase.getEvtName());// 事故者申請時姓名
        baappbase.setBenIdnNo(baappbase.getEvtIdnNo());// 受益人身分證號
        baappbase.setBenName(baappbase.getEvtName());// 受益人姓名
        baappbase.setBenBrDate(baappbase.getEvtBrDate());// 受益人出生日期
        baappbase.setBenSex(baappbase.getEvtSex());// 受益人性別
        baappbase.setBenNationTyp(baappbase.getEvtNationTpe());// 受益人國籍別
        baappbase.setBenNationCode(baappbase.getEvtNationCode());// 受益人國籍
        if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(baappbase.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(baappbase.getPayTyp())) {
            // 20130426 modified by Kiyomi 取消老年及失能年金受理作業帶入戶名之條件
            baappbase.setAccIdn(baappbase.getEvtIdnNo());// 戶名IDN
            // baappbase.setAccName(baappbase.getEvtName());// 戶名
            baappbase.setAccName("");// 戶名
        }
        else {
            baappbase.setAccIdn("");// 戶名IDN
            baappbase.setAccName("");// 戶名
        }

        baappbase.setUpdUser(userData.getEmpNo());// 異動者代號
        baappbase.setUpdTime(DateUtility.getNowWestDateTime(true));// 異動日期時間

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 取得改前值物件
            beforBaappbase = baappbaseDao.selectDetailDataForDisabledAnnuityReceiptBy(baappbaseId, null, null, null);
            BeanUtility.copyProperties(afterBaappbase, beforBaappbase);

            // 改後值物件
            afterBaappbase.setAppDate(baappbase.getAppDate());// 申請日期
            afterBaappbase.setApUbno(baappbase.getApUbno());// 申請單位保險證號
            afterBaappbase.setEvtIds(baappbase.getEvtIds());// 社福識別碼
            afterBaappbase.setEvtIdnNo(baappbase.getEvtIdnNo());// 事故者身分證號
            afterBaappbase.setEvtName(baappbase.getEvtName());// 事故者姓名
            afterBaappbase.setEvtAppName(baappbase.getEvtAppName());// 事故者姓名
            afterBaappbase.setEvtBrDate(baappbase.getEvtBrDate());// 事故者出生日期
            afterBaappbase.setEvtSex(baappbase.getEvtSex());// (事故者)性別
            afterBaappbase.setEvtNationTpe(baappbase.getEvtNationTpe());// (事故者)國籍別
            afterBaappbase.setEvtNationCode(baappbase.getEvtNationCode());// (事故者)國籍
            afterBaappbase.setEvtJobDate(baappbase.getEvtJobDate());// 診斷失能日期
            afterBaappbase.setEvtAge(baappbase.getEvtAge());// 事故者年齡
            afterBaappbase.setBenIds(baappbase.getBenIds());// 社福識別碼
            afterBaappbase.setBenIdnNo(baappbase.getBenIdnNo());// 事故者身分證號
            afterBaappbase.setBenName(baappbase.getBenName());// 事故者姓名
            afterBaappbase.setBenBrDate(baappbase.getBenBrDate());// 事故者出生日期
            afterBaappbase.setBenSex(baappbase.getBenSex());// (事故者)性別
            afterBaappbase.setBenNationTyp(baappbase.getBenNationTyp());// (事故者)國籍別
            afterBaappbase.setBenNationCode(baappbase.getBenNationCode());// (事故者)國籍
            afterBaappbase.setTel1(baappbase.getTel1());// 電話1
            afterBaappbase.setTel2(baappbase.getTel2());// 電話2
            afterBaappbase.setCommTyp(baappbase.getCommTyp());// 通訊地址別
            afterBaappbase.setCommZip(baappbase.getCommZip());// 郵遞區號
            afterBaappbase.setCommAddr(baappbase.getCommAddr());// 地址
            afterBaappbase.setPayTyp(baappbase.getPayTyp());// 給付方式
            afterBaappbase.setGrdIdnNo(baappbase.getGrdIdnNo());// 法定代理人身分證號
            afterBaappbase.setGrdName(baappbase.getGrdName());// 法定代理人姓名
            afterBaappbase.setGrdBrDate(baappbase.getGrdBrDate());// 法定代理人出生日期
            afterBaappbase.setAccIdn(baappbase.getAccIdn());// 事故者身分證號
            afterBaappbase.setAccName(baappbase.getAccName());// 事故者姓名
            afterBaappbase.setBankName(baappbase.getBankName());// 金融機構名稱
            afterBaappbase.setPayBankId(baappbase.getPayBankId());// 金融機構總代號
            afterBaappbase.setBranchId(baappbase.getBranchId());// 分支代號
            afterBaappbase.setPayEeacc(baappbase.getPayEeacc());// 銀行帳號
            afterBaappbase.setChkPayBankId(baappbase.getChkPayBankId());// 金融機構總代號 複驗
            afterBaappbase.setChkBranchId(baappbase.getChkBranchId());// 分支代號 複驗
            afterBaappbase.setChkPayEeacc(baappbase.getChkPayEeacc());// 銀行帳號 複驗
            afterBaappbase.setUpdUser(baappbase.getUpdUser());// Session.員工編號
            afterBaappbase.setUpdTime(baappbase.getUpdTime());// 修改時間
            afterBaappbase.setMobilePhone(baappbase.getMobilePhone());// 手機複驗

            // 延伸主檔改前值
            beforBaappexpand.setBaappbaseId(beforBaappbase.getBaappbaseId());
            beforBaappexpand.setEvTyp(beforBaappbase.getEvTyp());
            beforBaappexpand.setEvCode(beforBaappbase.getEvCode());
            beforBaappexpand.setCriInPart1(beforBaappbase.getCriInPart1());
            beforBaappexpand.setCriInPart2(beforBaappbase.getCriInPart2());
            beforBaappexpand.setCriInPart3(beforBaappbase.getCriInPart3());
            beforBaappexpand.setCriMedium(beforBaappbase.getCriMedium());
            beforBaappexpand.setCriInJdp1(beforBaappbase.getCriInJdp1());
            beforBaappexpand.setCriInJdp2(beforBaappbase.getCriInJdp2());
            beforBaappexpand.setCriInJdp3(beforBaappbase.getCriInJdp3());
            beforBaappexpand.setCriInJdp4(beforBaappbase.getCriInJdp4());
            beforBaappexpand.setCriInJdp5(beforBaappbase.getCriInJdp5());
            beforBaappexpand.setCriInJdp6(beforBaappbase.getCriInJdp6());
            beforBaappexpand.setCriInJdp7(beforBaappbase.getCriInJdp7());
            beforBaappexpand.setCriInJdp8(beforBaappbase.getCriInJdp8());
            beforBaappexpand.setCriInJdp9(beforBaappbase.getCriInJdp9());
            beforBaappexpand.setCriInJdp10(beforBaappbase.getCriInJdp10());
            beforBaappexpand.setHosId(beforBaappbase.getHosId());
            beforBaappexpand.setCriInJnme1(beforBaappbase.getCriInJnme1());
            beforBaappexpand.setCriInJnme2(beforBaappbase.getCriInJnme2());
            beforBaappexpand.setCriInJnme3(beforBaappbase.getCriInJnme3());
            beforBaappexpand.setCriInJnme4(beforBaappbase.getCriInJnme4());
            beforBaappexpand.setDoctorName1(beforBaappbase.getDoctorName1());
            beforBaappexpand.setDoctorName2(beforBaappbase.getDoctorName2());
            beforBaappexpand.setHandIcapMk(beforBaappbase.getHandIcapMk());
            beforBaappexpand.setEvAppTyp(beforBaappbase.getEvTyp());
        }
        // 更新給付主檔
        baappbaseDao.updateDataForDisabledAnnuityReceipt(baappbase);
        log.debug("Update BAAPPBASE Finished ...");

        // 更新資料到 給付延伸主檔
        // [
        log.debug("Start Insert BAAPPEXPAND ...");
        Baappexpand baappexpand = new Baappexpand();
        BeanUtility.copyProperties(baappexpand, evtCase);
        baappexpand.setHandIcapMk(evtCase.getEvtHandIcapMk());// 有無診斷書
        baappexpand.setUpdUser(userData.getEmpNo());// 修改者代號
        baappexpand.setUpdTime(DateUtility.getNowWestDateTime(true));// 修改時間
        baappexpand.setEvAppTyp(evtCase.getEvTyp());// 申請傷病分類

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {

            BeanUtility.copyProperties(afterBaappexpand, beforBaappexpand);

            // 延伸主檔 改後值物件
            afterBaappexpand.setEvTyp(baappexpand.getEvTyp());
            afterBaappexpand.setEvCode(baappexpand.getEvCode());
            afterBaappexpand.setCriInPart1(baappexpand.getCriInPart1());
            afterBaappexpand.setCriInPart2(baappexpand.getCriInPart2());
            afterBaappexpand.setCriInPart3(baappexpand.getCriInPart3());
            afterBaappexpand.setCriMedium(baappexpand.getCriMedium());
            afterBaappexpand.setCriInJdp1(baappexpand.getCriInJdp1());
            afterBaappexpand.setCriInJdp2(baappexpand.getCriInJdp2());
            afterBaappexpand.setCriInJdp3(baappexpand.getCriInJdp3());
            afterBaappexpand.setCriInJdp4(baappexpand.getCriInJdp4());
            afterBaappexpand.setCriInJdp5(baappexpand.getCriInJdp5());
            afterBaappexpand.setCriInJdp6(baappexpand.getCriInJdp6());
            afterBaappexpand.setCriInJdp7(baappexpand.getCriInJdp7());
            afterBaappexpand.setCriInJdp8(baappexpand.getCriInJdp8());
            afterBaappexpand.setCriInJdp9(baappexpand.getCriInJdp9());
            afterBaappexpand.setCriInJdp10(baappexpand.getCriInJdp10());
            afterBaappexpand.setHosId(baappexpand.getHosId());
            afterBaappexpand.setCriInJnme1(baappexpand.getCriInJnme1());
            afterBaappexpand.setCriInJnme2(baappexpand.getCriInJnme2());
            afterBaappexpand.setCriInJnme3(baappexpand.getCriInJnme3());
            afterBaappexpand.setCriInJnme4(baappexpand.getCriInJnme4());
            afterBaappexpand.setDoctorName1(baappexpand.getDoctorName1());
            afterBaappexpand.setDoctorName2(baappexpand.getDoctorName2());
            afterBaappexpand.setHandIcapMk(baappexpand.getHandIcapMk());
            afterBaappexpand.setEvAppTyp(baappexpand.getEvAppTyp());
        }

        baappexpandDao.updateDataForDisabledAnnuityReceipt(baappexpand);

        log.debug("Insert BAAPPEXPAND Finished ...");
        // ]

        // Insert MMAPLOG
        // [
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforBaappbase, afterBaappbase);
            FrameworkLogUtil.doUpdateLog(beforBaappexpand, afterBaappexpand);
        }
        // ]

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");
        List<Baapplog> logList = evtCase.getBaapplogList();
        for (int i = 0; i < logList.size(); i++) {
            Baapplog baapplog = logList.get(i);
            baapplog.setBaappbaseId(baappbaseId);// 資料列編號
            baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            // baapplog.setUpCol(); // 異動項目 - Log 已設
            // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
            // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
            baapplogDao.insertData(baapplog);
        }
        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 刪除 給付主檔、給付延伸主檔、眷屬檔資料
     * 
     * @param baappbaseId 給付主檔資料列編號
     */
    public void deleteDataForDisabled(UserBean userData, BigDecimal baappbaseId) {
        baappbaseDao.deleteDataForDisabledAnnuityReceipt(baappbaseId, "00");

        log.debug("Start Delete BAAPPBASE ...");
        // 取得改前值物件
        Baappbase beforBaappbase = baappbaseDao.selectDetailDataBy(baappbaseId, null, null, null);

        // 刪除 給付主檔
        baappbaseDao.deleteDataForDisabledAnnuityReceipt(baappbaseId, "00");

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteLog(beforBaappbase);
        }

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");
        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(baappbaseId);// 資料列編號
        baapplog.setStatus("D");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplog.setUpCol(); // 異動項目
        // baapplog.setBvalue("");// 改前內容
        // baapplog.setAvalue(); // 改後內容
        baapplogDao.insertData(baapplog);
        log.debug("Insert BAAPPLOG Finished ...");
        // ]
        log.debug("Delete BAAPPBASE Finished ...");
    }

    /**
     * 依畫面輸入欄位條件轉換 眷屬檔 部分欄位 For 失能年金受理作業<br>
     * 
     * @param payBankIdBranchId 帳號(前)
     * @return
     */
    public DisabledAnnuityReceiptFamCase transDisabledFamInputData(DisabledAnnuityReceiptFamCase caseObj) {
        // 日期轉換：「眷屬申請日期」、「眷屬出生日期」、「結婚日期」
        // 轉換成西元年日期再回存，其轉換後的日期格式為YYYYMMDD。
        if (StringUtils.isNotBlank(caseObj.getFamAppDate())) {
            caseObj.setFamAppDate(DateUtility.changeDateType(caseObj.getFamAppDate()));
        }
        if (StringUtils.isNotBlank(caseObj.getFamBrDate())) {
            caseObj.setFamBrDate(DateUtility.changeDateType(caseObj.getFamBrDate()));
        }
        if (StringUtils.isNotBlank(caseObj.getMarryDate())) {
            caseObj.setMarryDate(DateUtility.changeDateType(caseObj.getMarryDate()));
        }

        // 「國籍別」=1時
        // 「性別」=「眷屬身分證號」的第2碼
        // 「國籍」= '000'
        // 「國籍別」=2時
        // 「性別」=【BAAP0D021A】畫面中的「性別」欄位
        // 「國籍」=【BAAP0D021A】畫面中的「國籍」欄位
        if (("1").equals(caseObj.getFamNationTyp())) {
            if (StringUtils.isNotBlank(caseObj.getFamIdnNo()) && caseObj.getFamIdnNo().length() >= 2) {
                caseObj.setFamSex(caseObj.getFamIdnNo().substring(1, 2));
            }
            caseObj.setFamNationCode("000");
        }

        return caseObj;
    }

    /**
     * 新增 眷屬暫存檔資料 For 失能年金受理作業<br>
     * 
     * @param caseObj 眷屬資料
     * @param userData user資料
     */
    public void insertDisabledBafamilytempData(DisabledAnnuityReceiptFamCase caseObj, UserBean userData) {
        log.debug("Start Insert BAFAMILYTEMP ...");
        Bafamilytemp bafamilytemp = new Bafamilytemp();
        BeanUtility.copyProperties(bafamilytemp, caseObj);

        bafamilytemp.setSeqNo(bafamilytempDao.selectNewSeqNo(caseObj.getBafamilytempId()));
        bafamilytemp.setCrtUser(userData.getEmpNo());
        bafamilytemp.setCrtTime(DateUtility.getNowWestDateTime(true));

        bafamilytempDao.insertDataForDisabledAnnuityReceipt(bafamilytemp);
        log.debug("Insert BAFAMILYTEMP Finished...");
    }

    /**
     * 新增 眷屬資料 For 失能年金受理作業<br>
     * 
     * @param caseObj 眷屬資料
     * @param userData user資料
     */
    public void insertDisabledBafamilyData(DisabledAnnuityReceiptFamCase caseObj, UserBean userData) {
        log.debug("Start Insert BAFAMILY ...");
        Bafamily bafamily = new Bafamily();
        BeanUtility.copyProperties(bafamily, caseObj);

        bafamily.setSeqNo(bafamilyDao.selectNewSeqNo(caseObj.getBaappbaseId(), caseObj.getApNo()));
        bafamily.setCrtUser(userData.getEmpNo());
        bafamily.setCrtTime(DateUtility.getNowWestDateTime(true));

        BigDecimal bafamilyId = bafamilyDao.insertDataForDisabledAnnuityReceipt(bafamily);

        // Insert MMAPLOG
        bafamily.setBafamilyId(bafamilyId);
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bafamily);
        }

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");

        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(caseObj.getBaappbaseId());// 資料列編號
        baapplog.setStatus("A");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplog.setUpCol(); // 異動項目 - Log 已設
        // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
        // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
        baapplogDao.insertData(baapplog);

        log.debug("Insert BAAPPLOG Finished ...");
        // ]

        log.debug("Insert BAFAMILY Finished...");
    }

    /**
     * 修改 眷屬暫存檔資料 For 失能年金受理作業<br>
     * 
     * @param caseObj 眷屬資料
     * @param userData user資料
     */
    public void updateDisabledBafamilytempData(DisabledAnnuityReceiptFamCase caseObj, UserBean userData) {
        log.debug("Start Update BAFAMILYTEMP ...");
        Bafamilytemp bafamilytemp = new Bafamilytemp();
        BeanUtility.copyProperties(bafamilytemp, caseObj);

        bafamilytempDao.updateDataForDisabledAnnuityReceipt(bafamilytemp);

        log.debug("Update BAFAMILYTEMP Finished...");
    }

    /**
     * 修改 眷屬暫存檔資料 For 失能年金受理作業<br>
     * 
     * @param caseObj 眷屬資料
     * @param userData user資料
     */
    public void updateDisabledBafamilyData(DisabledAnnuityReceiptFamCase caseObj, UserBean userData) {
        log.debug("Start Update BAFAMILY ...");
        Bafamily bafamily = new Bafamily();
        BeanUtility.copyProperties(bafamily, caseObj);
        Bafamily beforBafamily = new Bafamily();// 眷屬主檔改前值物件
        Bafamily afterBafamily = new Bafamily();// 眷屬主檔改後直物件

        String bafamilyId = caseObj.getBafamilyId().toString();
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            beforBafamily = bafamilyDao.getDependantDataUpdateForUpdateCaseBy(bafamilyId);
            BeanUtility.copyProperties(afterBafamily, beforBafamily);

            // 改後值物件
            afterBafamily.setApNo(caseObj.getApNo());
            afterBafamily.setBaappbaseId(caseObj.getBaappbaseId());
            afterBafamily.setBafamilyId(caseObj.getBafamilyId());
            afterBafamily.setSeqNo(caseObj.getSeqNo());
            afterBafamily.setFamAppDate(caseObj.getFamAppDate());
            afterBafamily.setFamBrDate(caseObj.getFamBrDate());
            afterBafamily.setFamEvtRel(caseObj.getFamEvtRel());
            afterBafamily.setFamName(caseObj.getFamName());
            afterBafamily.setFamNationCode(caseObj.getFamNationCode());
            afterBafamily.setFamNationTyp(caseObj.getFamNationTyp());
            afterBafamily.setFamSex(caseObj.getFamSex());
            afterBafamily.setFamIdnNo(caseObj.getFamIdnNo());
            afterBafamily.setFamBrDate(caseObj.getFamBrDate());
            afterBafamily.setFamEvtRel(caseObj.getFamEvtRel());
            afterBafamily.setMarryDate(caseObj.getMarryDate());
            afterBafamily.setRaiseChildMk(caseObj.getRaiseChildMk());
            afterBafamily.setStudMk(caseObj.getStudMk());
            afterBafamily.setMonIncomeMk(caseObj.getMonIncomeMk());
            afterBafamily.setMonIncome(caseObj.getMonIncome());
            afterBafamily.setHandIcapMk(caseObj.getHandIcapMk());
            afterBafamily.setInterDictMk(caseObj.getInterDictMk());
        }

        bafamilyDao.updateDataForDisabledAnnuityReceipt(bafamily);

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforBafamily, afterBafamily);

        }

        // 取得需記錄異動LOG的欄位資料
        // 為不影響前端頁面顯示, 故須複製一份 Form
        DisabledAnnuityReceiptForm tempForm = new DisabledAnnuityReceiptForm();
        BeanUtility.copyPropertiesForUpdate(tempForm, beforBafamily, ConstantKey.OLD_FIELD_PREFIX);
        BeanUtility.copyProperties(tempForm, afterBafamily);
        caseObj.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_RECEIPT_DISABLED_FIELD_RESOURCE_PREFIX));
        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");
        List<Baapplog> logList = caseObj.getBaapplogList();
        for (int i = 0; i < logList.size(); i++) {
            Baapplog baapplog = logList.get(i);
            baapplog.setBaappbaseId(caseObj.getBaappbaseId());// 資料列編號
            baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            // baapplog.setUpCol(); // 異動項目 - Log 已設
            // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
            // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
            baapplogDao.insertData(baapplog);
        }
        log.debug("Insert BAAPPLOG Finished ...");
        // ]
        log.debug("Update BAFAMILY Finished...");
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 遺屬資料 For 失能年金受理作業
     * 
     * @param apNo 受理編號
     * @param baappbaseId 給付主檔資料列編號
     * @return 內含 <code>DisabledAnnuityReceiptFamCase</code> 物件的 List
     */
    public List<DisabledAnnuityReceiptFamCase> getDisabledBafamilytempData(BigDecimal bafamilytempId) {
        List<Bafamilytemp> list = bafamilytempDao.selectFamDataListForAnnuityReceiptBy(bafamilytempId);
        List<DisabledAnnuityReceiptFamCase> returnList = new ArrayList<DisabledAnnuityReceiptFamCase>();
        for (Bafamilytemp bafamilytemp : list) {
            DisabledAnnuityReceiptFamCase famCase = new DisabledAnnuityReceiptFamCase();
            BeanUtility.copyProperties(famCase, bafamilytemp);
            returnList.add(famCase);
        }
        return returnList;
    }

    /**
     * 依傳入資料(<code>SurvivorAnnuityReceiptEvtCase</code>) 將西元年月日轉換成民國年月日 for 遺屬年金受理作業 事故者資料
     * 
     * @param caseobj 轉換資料之物件
     * @return <code>DisabledAnnuityReceiptEvtCase</code> 物件
     */
    public DisabledAnnuityReceiptEvtCase transDateForDisabledEvt(DisabledAnnuityReceiptEvtCase caseObj) {
        if (StringUtils.isNotBlank(caseObj.getAppDateStr())) {
            caseObj.setAppDate(caseObj.getAppDateStr());
        }
        if (StringUtils.isNotBlank(caseObj.getEvtBrDateStr())) {
            caseObj.setEvtBrDate(caseObj.getEvtBrDateStr());
        }
        if (StringUtils.isNotBlank(caseObj.getGrdBrDateStr())) {
            caseObj.setGrdBrDate(caseObj.getGrdBrDateStr());
        }
        if (StringUtils.isNotBlank(caseObj.getEvtJobDateStr())) {
            caseObj.setEvtJobDate(caseObj.getEvtJobDateStr());
        }
        if (StringUtils.isNotBlank(caseObj.getPayBankId()) || StringUtils.isNotBlank(caseObj.getBranchId())) {
            caseObj.setPayBankIdBranchId(caseObj.getPayBankId() + caseObj.getBranchId());
        }
        if (StringUtils.isNotBlank(caseObj.getChkPayBankId()) || StringUtils.isNotBlank(caseObj.getChkBranchId())) {
            caseObj.setChkPayBankIdChkBranchId(caseObj.getChkPayBankId() + caseObj.getChkBranchId());
        }
        return caseObj;
    }

    /**
     * 依傳入資料(<code>SurvivorAnnuityReceiptEvtCase</code>) 將西元年月日轉換成民國年月日 for 失能年金受理作業 事故者資料 國併勞
     * 
     * @param caseobj 轉換資料之物件
     * @return <code>DisabledAnnuityReceiptEvtCase</code> 物件
     */
    public DisabledAnnuityReceiptEvtCase transDateForDisabledEvtFor36(DisabledAnnuityReceiptEvtCase caseObj) {
        if (StringUtils.isNotBlank(caseObj.getAppDateStr())) {
            caseObj.setAppDate(caseObj.getAppDateStr());
        }
        if (StringUtils.isNotBlank(caseObj.getEvtBrDateStr())) {
            caseObj.setEvtBrDate(caseObj.getEvtBrDateStr());
        }
        if (StringUtils.isNotBlank(caseObj.getGrdBrDateStr())) {
            caseObj.setGrdBrDate(caseObj.getGrdBrDateStr());
        }
        if (StringUtils.isNotBlank(caseObj.getEvtJobDateStr())) {
            caseObj.setEvtJobDate(caseObj.getEvtJobDateStr());
        }

        return caseObj;
    }

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamilyId 資料列編號
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    public List<DisabledAnnuityReceiptFamCase> getBafamilyDataForDiasbled(BigDecimal baappbaseId, String apNo) {
        List<Bafamily> list = bafamilyDao.selectDisabledFamDataListBy(baappbaseId, apNo);
        List<DisabledAnnuityReceiptFamCase> returnList = new ArrayList<DisabledAnnuityReceiptFamCase>();
        for (Bafamily bafamily : list) {
            DisabledAnnuityReceiptFamCase famCase = new DisabledAnnuityReceiptFamCase();
            BeanUtility.copyProperties(famCase, bafamily);
            returnList.add(famCase);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 已存在之眷屬資料 for 失能年金受理作業
     * 
     * @param bafamilytempId 資料列編號
     * @param famIdnNo 遺屬/眷屬身分證號
     * @param famBrDate 遺屬/眷屬出生日期
     * @param seqNo 序號
     * @param eqType SQL EqualType
     * @return <code>boolean</code>
     */
    public boolean checkExistFamData(BigDecimal bafamilyId, String famIdnNo, String famBrDate, String seqNo, String eqType) {
        List<BigDecimal> list = bafamilyDao.selectExistDataForAnnuityReceiptBy(bafamilyId, famIdnNo, famBrDate, seqNo, eqType);
        boolean chkResult = false;
        if (list.size() != 0) {
            chkResult = true;
        }
        return chkResult;
    }

    /**
     * 保存畫面上已輸入之事故者資料 for 失能受理
     * 
     * @param evtForm
     * @param famForm
     * @return
     */
    public DisabledAnnuityReceiptForm keepInputEvtFormDataForDisabled(DisabledAnnuityReceiptForm evtForm, DisabledAnnuityReceiptForm famForm) {
        if (evtForm != null && famForm != null) {
            evtForm.setApNo1(famForm.getTempApNo1());// 受理編號-1
            evtForm.setApNo2(famForm.getTempApNo2());// 受理編號-2
            evtForm.setApNo3(famForm.getTempApNo3());// 受理編號-3
            evtForm.setApNo4(famForm.getTempApNo4());// 受理編號-4
            evtForm.setAppDate(famForm.getTempAppDate());// 申請日期
            evtForm.setApUbno(famForm.getTempApUbno());// 申請單位保險證號
            evtForm.setEvtNationTpe(famForm.getTempEvtNationTpe()); // 事故者國籍別
            evtForm.setEvtSex(famForm.getTempEvtSex()); // 性別
            evtForm.setEvtNationCode(famForm.getTempEvtNationCode()); // 事故者國籍
            evtForm.setEvtNationCodeOption(famForm.getTempEvtNationCodeOption()); // 事故者國籍下拉選單
            evtForm.setEvtName(famForm.getTempEvtName()); // 事故者姓名
            evtForm.setCvldtlName(famForm.getTempCvldtlName()); // 戶籍姓名
            evtForm.setEvtIdnNo(famForm.getTempEvtIdnNo()); // 事故者身分證號
            evtForm.setEvtBrDate(famForm.getTempEvtBrDate()); // 事故者出生日期
            evtForm.setTel1(famForm.getTempTel1()); // 電話1
            evtForm.setTel2(famForm.getTempTel2()); // 電話2
            evtForm.setCommTyp(famForm.getTempCommTyp()); // 通訊地址別
            evtForm.setCommZip(famForm.getTempCommZip()); // 通訊郵遞區號
            evtForm.setCommAddr(famForm.getTempCommAddr()); // 通訊地址
            evtForm.setGrdIdnNo(famForm.getTempGrdIdnNo()); // 法定代理人身分證號
            evtForm.setGrdName(famForm.getTempGrdName()); // 法定代理人姓名
            evtForm.setGrdBrDate(famForm.getTempGrdBrDate()); // 法定代理人出生日期
            evtForm.setEvTyp(famForm.getTempEvTyp()); // 傷病分類
            evtForm.setEvtJobDate(famForm.getTempEvtJobDate()); // 診斷失能日期
            evtForm.setEvCode(famForm.getTempEvCode()); // 傷病原因
            evtForm.setCriInPart1(famForm.getTempCriInPart1()); // 受傷部位
            evtForm.setCriInPart2(famForm.getTempCriInPart2()); // 受傷部位
            evtForm.setCriInPart3(famForm.getTempCriInPart3()); // 受傷部位
            evtForm.setCriMedium(famForm.getTempCriMedium()); // 媒 介 物
            evtForm.setCriInJdp1(famForm.getTempCriInJdp1()); // 失能項目
            evtForm.setCriInJdp2(famForm.getTempCriInJdp2()); // 失能項目
            evtForm.setCriInJdp3(famForm.getTempCriInJdp3()); // 失能項目
            evtForm.setCriInJdp4(famForm.getTempCriInJdp4()); // 失能項目
            evtForm.setCriInJdp5(famForm.getTempCriInJdp5()); // 失能項目
            evtForm.setCriInJdp6(famForm.getTempCriInJdp6()); // 失能項目
            evtForm.setCriInJdp7(famForm.getTempCriInJdp7()); // 失能項目
            evtForm.setCriInJdp8(famForm.getTempCriInJdp8()); // 失能項目
            evtForm.setCriInJdp9(famForm.getTempCriInJdp9()); // 失能項目
            evtForm.setCriInJdp10(famForm.getTempCriInJdp10()); // 失能項目
            evtForm.setHosId(famForm.getTempHosId()); // 醫療院所代碼
            evtForm.setDoctorName1(famForm.getTempDoctorName1()); // 醫師姓名
            evtForm.setDoctorName2(famForm.getTempDoctorName2()); // 醫師姓名
            evtForm.setCriInJnme1(famForm.getTempCriInJnme1()); // 國際疾病代碼
            evtForm.setCriInJnme2(famForm.getTempCriInJnme2()); // 國際疾病代碼
            evtForm.setCriInJnme3(famForm.getTempCriInJnme3()); // 國際疾病代碼
            evtForm.setCriInJnme4(famForm.getTempCriInJnme4()); // 國際疾病代碼
            evtForm.setPayTyp(famForm.getTempPayTyp()); // 給付方式
            evtForm.setPayBankIdBranchId(famForm.getTempPayBankIdBranchId()); // 帳號(前)
            evtForm.setPayEeacc(famForm.getTempPayEeacc()); // 帳號(後)
            evtForm.setChkPayBankIdChkBranchId(famForm.getTempChkPayBankIdChkBranchId()); // 帳號(前) 複驗
            evtForm.setChkPayEeacc(famForm.getTempChkPayEeacc()); // 帳號(後) 複驗
            evtForm.setMobilePhone(famForm.getTempMobilePhone());// 手機複驗
            evtForm.setEvtHandIcapMk(famForm.getTempEvtHandIcapMk());// 有無診斷書
            evtForm.setDefaultGrdData(famForm.getTempDefaultGrdData());// 法定代理人預設眷屬1
            evtForm.setPayBankId(famForm.getTempPayBankId()); // 帳號(前)
            evtForm.setBranchId(famForm.getTempBranchId()); // 帳號(前)
            evtForm.setChkPayBankId(famForm.getTempChkPayBankId()); // 帳號(前) 複驗
            evtForm.setChkBranchId(famForm.getTempChkBranchId()); // 帳號(前) 複驗
        }

        return evtForm;
    }

    // ------------------------------ 失能年金受理作業 ------------------------------

    // ------------------------------ 遺屬年金受理作業 ------------------------------
    /**
     * 依傳入條件取得 給付主檔 資料清單 for 失能年金受理作業
     * 
     * @param evtIdnNo 事故者身分證號
     * @param apNo 受理編號
     * @return
     */
    public List<SurvivorAnnuityReceiptCase> getBaappbaseDataListForSurvivor(String evtIdnNo, String apNo) {
        List<Baappbase> list = baappbaseDao.selectDataForAnnuityReceiptBy(evtIdnNo, apNo, "0000", new String[] { "00" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);
        List<SurvivorAnnuityReceiptCase> returnList = new ArrayList<SurvivorAnnuityReceiptCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            SurvivorAnnuityReceiptCase caseObj = new SurvivorAnnuityReceiptCase();
            BeanUtility.copyProperties(caseObj, baappbase);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 遺屬資料 For 遺屬年金受理作業
     * 
     * @param apNo 受理編號
     * @param baappbaseId 給付主檔資料列編號
     * @return 內含 <code>DisabledAnnuityReceiptFamCase</code> 物件的 List
     */
    public List<SurvivorAnnuityReceiptBenCase> getSurvivorBafamilytempDataList(BigDecimal bafamilytempId) {
        List<Bafamilytemp> list = bafamilytempDao.selectFamDataListForAnnuityReceiptBy(bafamilytempId);
        List<SurvivorAnnuityReceiptBenCase> returnList = new ArrayList<SurvivorAnnuityReceiptBenCase>();
        for (Bafamilytemp bafamilytemp : list) {
            SurvivorAnnuityReceiptBenCase benCase = new SurvivorAnnuityReceiptBenCase();
            BeanUtility.copyProperties(benCase, bafamilytemp);
            benCase.setBenEvtRel(benCase.getFamEvtRel());
            benCase.setBenName(benCase.getFamName());
            benCase.setBenIdnNo(benCase.getFamIdnNo());
            benCase.setBenBrDate(benCase.getFamBrDate());
            returnList.add(benCase);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬資料清單 for 遺屬年金受理作業
     * 
     * @param apNo 受理編號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return 內含 <code>SurvivorAnnuityReceiptBenCase</code> 物件的 List
     */
    public List<SurvivorAnnuityReceiptBenCase> getSurvivorBenDataList(String apNo, String[] procStat, String eqType) {
        List<Baappbase> list = baappbaseDao.selectSurvivorBenDataListBy(apNo, procStat, eqType);
        List<SurvivorAnnuityReceiptBenCase> returnList = new ArrayList<SurvivorAnnuityReceiptBenCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            SurvivorAnnuityReceiptBenCase caseObj = new SurvivorAnnuityReceiptBenCase();
            BeanUtility.copyProperties(caseObj, baappbase);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者詳細資料 for 遺屬年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public SurvivorAnnuityReceiptEvtCase getSurvivorEvtDetailData(BigDecimal baappbaseId) {
        Baappbase baappbase = baappbaseDao.selectDetailDataForSurvivorAnnuityReceiptBy(baappbaseId, "0000", new String[] { "00" }, "in");
        SurvivorAnnuityReceiptEvtCase caseObj = null;
        if (baappbase != null) {
            caseObj = new SurvivorAnnuityReceiptEvtCase();
            BeanUtility.copyProperties(caseObj, baappbase);
            if (caseObj.getApNo().length() == ConstantKey.APNO_LENGTH) {
                caseObj.setApNo1(caseObj.getApNo().substring(0, 1));
                caseObj.setApNo2(caseObj.getApNo().substring(1, 2));
                caseObj.setApNo3(caseObj.getApNo().substring(2, 7));
                caseObj.setApNo4(caseObj.getApNo().substring(7, 12));
            }
        }
        return caseObj;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬詳細資料 for 遺屬年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return 內含 <code>SurvivorAnnuityReceiptBenCase</code> 物件的 List
     */
    public SurvivorAnnuityReceiptBenCase getSurvivorBenDetailData(BigDecimal baappbaseId, String apNo, String[] procStat, String eqType) {
        Baappbase baappbase = baappbaseDao.selectDetailDataForSurvivorAnnuityReceiptBy(baappbaseId, apNo, procStat, eqType);
        SurvivorAnnuityReceiptBenCase caseObj = new SurvivorAnnuityReceiptBenCase();
        if (baappbase != null) {
            BeanUtility.copyProperties(caseObj, baappbase);
            caseObj.setBenAppDate(baappbase.getAppDate());
            caseObj.setPayBankIdBranchId(baappbase.getPayBankId() + baappbase.getBranchId());
        }
        return caseObj;
    }

    /**
     * 依傳入資料(<code>SurvivorAnnuityReceiptEvtCase</code>) 將西元年月日轉換成民國年月日 for 遺屬年金受理作業 事故者資料
     * 
     * @param caseobj 轉換資料之物件
     * @return <code>SurvivorAnnuityReceiptEvtCase</code> 物件
     */
    public SurvivorAnnuityReceiptEvtCase transDateForSurvivorEvt(SurvivorAnnuityReceiptEvtCase caseObj) {
        if (StringUtils.isNotBlank(caseObj.getAppDate()) && caseObj.getAppDate().length() == 8)
            caseObj.setAppDate(DateUtility.changeDateType(caseObj.getAppDate()));
        if (StringUtils.isNotBlank(caseObj.getEvtDieDate()) && caseObj.getEvtDieDate().length() == 8)
            caseObj.setEvtDieDate(DateUtility.changeDateType(caseObj.getEvtDieDate()));
        if (StringUtils.isNotBlank(caseObj.getEvtJobDate()) && caseObj.getEvtJobDate().length() == 8)
            caseObj.setEvtJobDate(DateUtility.changeDateType(caseObj.getEvtJobDate()));
        if (StringUtils.isNotBlank(caseObj.getEvtBrDate()) && caseObj.getEvtBrDate().length() == 8)
            caseObj.setEvtBrDate(DateUtility.changeDateType(caseObj.getEvtBrDate()));
        return caseObj;
    }

    /**
     * 依傳入資料(<code>SurvivorAnnuityReceiptBenCase</code>) 將西元年月日轉換成民國年月日 for 遺屬年金受理作業 遺屬資料
     * 
     * @param caseobj 轉換資料之物件
     * @return <code>SurvivorAnnuityReceiptBenCase</code> 物件
     */
    public SurvivorAnnuityReceiptBenCase transDateForSurvivorBen(SurvivorAnnuityReceiptBenCase caseObj) {
        if (StringUtils.isNotBlank(caseObj.getBenAppDate()) && caseObj.getBenAppDate().length() == 8)
            caseObj.setBenAppDate(DateUtility.changeDateType(caseObj.getBenAppDate()));
        if (StringUtils.isNotBlank(caseObj.getBenBrDate()) && caseObj.getBenBrDate().length() == 8)
            caseObj.setBenBrDate(DateUtility.changeDateType(caseObj.getBenBrDate()));
        if (StringUtils.isNotBlank(caseObj.getGrdBrDate()) && caseObj.getGrdBrDate().length() == 8)
            caseObj.setGrdBrDate(DateUtility.changeDateType(caseObj.getGrdBrDate()));
        if (StringUtils.isNotBlank(caseObj.getMarryDate()) && caseObj.getMarryDate().length() == 8)
            caseObj.setMarryDate(DateUtility.changeDateType(caseObj.getMarryDate()));
        return caseObj;
    }

    /**
     * 依畫面輸入欄位條件轉換 新增/修改 給付主檔 部分欄位 For 遺屬年金受理作業
     * 
     * @param caseObj 欲轉換之物件
     * @return
     */
    public SurvivorAnnuityReceiptEvtCase transSurvivorEvtInputData(SurvivorAnnuityReceiptEvtCase caseObj) {
        // 日期轉換：轉換成西元年日期再回存，其轉換後的日期格式為YYYYMMDD。
        // [
        // 「申請日期」
        if (StringUtils.isNotBlank(caseObj.getAppDate()) && caseObj.getAppDate().length() == 7) {
            caseObj.setAppDate(DateUtility.changeDateType(caseObj.getAppDate()));
        }
        // 「死亡日期」/「事故日期」
        if (StringUtils.isNotBlank(caseObj.getEvtDieDate()) && caseObj.getEvtDieDate().length() == 7) {
            caseObj.setEvtDieDate(DateUtility.changeDateType(caseObj.getEvtDieDate()));
            caseObj.setEvtJobDate(caseObj.getEvtDieDate());
        }
        // 「事故者出生日期」
        if (StringUtils.isNotBlank(caseObj.getEvtBrDate()) && caseObj.getEvtBrDate().length() == 7) {
            caseObj.setEvtBrDate(DateUtility.changeDateType(caseObj.getEvtBrDate()));
        }
        // 「判決日期」
        if (StringUtils.isNotBlank(caseObj.getJudgeDate()) && caseObj.getJudgeDate().length() == 7) {
        	caseObj.setJudgeDate(DateUtility.changeDateType(caseObj.getJudgeDate()));
        }
        // ]

        // 根據 事故者身分證號, 出生日期 取得戶政資料
        Cvldtl cvldtlEvtData = selectCvldtlNameBy(caseObj.getEvtIdnNo(), caseObj.getEvtBrDate());
        // 「社福識別碼」若查無資料時，則「社福識別碼」= ' '。
        if (StringUtils.isNotBlank(cvldtlEvtData.getNpIds())) {
            caseObj.setEvtIds(cvldtlEvtData.getNpIds());
        }
        else {
            caseObj.setEvtIds("");
        }
        // 將 事故者姓名 填入 事故者申請時姓名
        caseObj.setEvtAppName(caseObj.getEvtName());

        // 「國籍別」=1時
        // 「性別」=「身分證號」的第2碼
        // 「國籍」= '000'
        // 「國籍別」=2時
        // 「性別」=【BAAP0D031A】畫面中的「性別」欄位
        // 「國籍」=【BAAP0D031A】畫面中的「國籍」欄位
        if (("1").equals(caseObj.getEvtNationTpe())) {
            if (StringUtils.isNotBlank(caseObj.getEvtIdnNo()) && caseObj.getEvtIdnNo().length() >= 2) {
                caseObj.setEvtSex(caseObj.getEvtIdnNo().substring(1, 2));
            }
            caseObj.setEvtNationCode("000");
        }
        // 「事故者年齡」：「死亡日期」 - 「事故者出生日期」
        caseObj.setEvtAge(BaBusinessUtility.calAge(caseObj.getEvtDieDate(), caseObj.getEvtBrDate()));

        return caseObj;
    }

    /**
     * 依畫面輸入欄位條件轉換 遺屬資料 部分欄位 For 遺屬年金受理作業
     * 
     * @param caseObj 欲轉換之物件
     * @return
     */
    public SurvivorAnnuityReceiptBenCase transSurvivorBenInputData(SurvivorAnnuityReceiptBenCase caseObj, String actionType) {
        // 日期轉換：轉換成西元年日期再回存，其轉換後的日期格式為YYYYMMDD。
        // [
        // 「遺屬申請日期」
        if (StringUtils.isNotBlank(caseObj.getBenAppDate()) && caseObj.getBenAppDate().length() == 7) {
            caseObj.setBenAppDate(DateUtility.changeDateType(caseObj.getBenAppDate()));
        }
        // 「遺屬出生日期」
        if (StringUtils.isNotBlank(caseObj.getBenBrDate()) && caseObj.getBenBrDate().length() == 7) {
            caseObj.setBenBrDate(DateUtility.changeDateType(caseObj.getBenBrDate()));
        }
        // 「法定代理人出生日期」
        if (StringUtils.isNotBlank(caseObj.getGrdBrDate()) && caseObj.getGrdBrDate().length() == 7) {
            caseObj.setGrdBrDate(DateUtility.changeDateType(caseObj.getGrdBrDate()));
        }
        // 「結婚日期」
        if (StringUtils.isNotBlank(caseObj.getMarryDate()) && caseObj.getMarryDate().length() == 7) {
            caseObj.setMarryDate(DateUtility.changeDateType(caseObj.getMarryDate()));
        }
        // 「收養日期」
        if (StringUtils.isNotBlank(caseObj.getAdoPtDate()) && caseObj.getAdoPtDate().length() == 7) {
        	caseObj.setAdoPtDate(DateUtility.changeDateType(caseObj.getAdoPtDate()));
        }
        // 「代辦人出生日期」
        if (StringUtils.isNotBlank(caseObj.getAssignBrDate()) && caseObj.getAssignBrDate().length() == 7) {
        	caseObj.setAssignBrDate(DateUtility.changeDateType(caseObj.getAssignBrDate()));
        }
        // ]
        // 根據 遺屬身分證號, 出生日期 取得戶政資料
        Cvldtl cvldtlBenData = selectCvldtlNameBy(caseObj.getBenIdnNo(), caseObj.getBenBrDate());
        // 遺屬社福識別碼
        if (StringUtils.isNotBlank(cvldtlBenData.getNpIds())) {
            caseObj.setBenIds(cvldtlBenData.getNpIds());
        }
        else {
            caseObj.setBenIds("");
        }

        // 「國籍別」=1時
        // 「性別」=「身分證號」的第2碼
        // 「國籍」= '000'
        // 「國籍別」=2時
        // 「性別」=【BAAP0D031A】畫面中的「性別」欄位
        // 「國籍」=【BAAP0D031A】畫面中的「國籍」欄位
        if (("1").equals(caseObj.getBenNationTyp())) {
            if (StringUtils.isNotBlank(caseObj.getBenIdnNo()) && caseObj.getBenIdnNo().length() >= 2) {
                caseObj.setBenSex(caseObj.getBenIdnNo().substring(1, 2));
            }
            caseObj.setBenNationCode("000");
        }
        // 「通訊郵遞區號」、「通訊地址」：
        // 當「通訊地址別」=1時，「通訊郵遞區號」、「通訊地址」=戶籍檔的「通訊郵遞區號」、「通訊地址」；
        // 當「通訊地址別」=2時，「通訊郵遞區號」、「通訊地址」=【BAAP0D011A】畫面中的「通訊郵遞區號」、「通訊地址」欄位。
        if ((ConstantKey.BAAPPBASE_COMMTYP_1).equals(caseObj.getCommTyp())) {
            caseObj.setCommZip(cvldtlBenData.getHzip());
            caseObj.setCommAddr(cvldtlBenData.getHaddr());
        }
        else if ((ConstantKey.BAAPPBASE_COMMTYP_2).equals(caseObj.getCommTyp())) {
            caseObj.setCommZip(caseObj.getCommZip());
            caseObj.setCommAddr(caseObj.getCommAddr());
        }
        else {
            caseObj.setCommZip("");
            caseObj.setCommAddr("");
        }

        // 本人領取
        if (("1").equals(caseObj.getPayCategory())) {
            caseObj.setAccRel("1");// 戶名與受益人關係
            caseObj.setAccSeqNo(caseObj.getSeqNo());// 具名領取人
            caseObj.setAccIdn(caseObj.getBenIdnNo());
            caseObj.setAccName(caseObj.getBenName());
            caseObj.setMitRate(new BigDecimal(0));
            String payTyp = caseObj.getPayTyp();

            // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」
            // 「給付方式」=1時
            // 「金融機構名稱」=「金融機構名稱一」+「金融機構名稱二」
            // 「金融機構總代號」=「帳號」(前：1~3碼)
            // 「戶名」=【BAMO0D021C】畫面中的「戶名」(已存在case中)
            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(payTyp)) {
                String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
                String accountB = caseObj.getPayEeacc();// 帳號(後)
                caseObj.setBankName(getBankName(accountF));// 金融機構名稱
                // 為防止輸入長度不足七碼
                if (accountF.length() > 3) {
                    caseObj.setPayBankId(accountF.substring(0, 3));
                    caseObj.setBranchId(accountF.substring(3, accountF.length()));
                }
                else {
                    caseObj.setPayBankId(accountF.substring(0, accountF.length()));
                    caseObj.setBranchId("");
                }
                caseObj.setPayEeacc(accountB);
            }
            // 「給付方式」=2時，「金融機構名稱」= 郵局名稱
            // 「金融機構總代號」=「帳號」(前：1~3碼)
            // 「分支代號」=「帳號」(前：4~7碼)
            // 「銀行帳號」=「帳號」(後)
            // 「戶名」=【BAMO0D021C】畫面中的「戶名」
            else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(payTyp)) {
                String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
                String accountB = caseObj.getPayEeacc();// 帳號(後)
                caseObj.setBankName(getPostName(caseObj.getPayBankIdBranchId()));
                // 為防止輸入長度不足七碼
                if (accountF.length() > 3) {
                    caseObj.setPayBankId(accountF.substring(0, 3));
                    caseObj.setBranchId(accountF.substring(3, accountF.length()));
                }
                else {
                    caseObj.setPayBankId(accountF.substring(0, accountF.length()));
                    caseObj.setBranchId("");
                }
                caseObj.setPayEeacc(accountB);
            }
            // 當「給付方式」(本人領取)=A時，「金融機構名稱」= ' '
            // 「金融機構總代號」= ' '
            // 「分支代號」= ' '
            // 「銀行帳號」= ' '
            // 「戶名」=「受款人姓名」
            else if ((ConstantKey.BAAPPBASE_PAYTYP_A).equals(payTyp)) {
                caseObj.setBankName("");
                caseObj.setPayBankId("");
                caseObj.setBranchId("");
                caseObj.setPayEeacc("");
                caseObj.setAccIdn("");
                caseObj.setAccName("");
            }

            // 帳號複驗 轉換 20121203更新
            // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」
            // 當「給付方式」=1時，「金融機構名稱」= 銀行名稱(「金融機構名稱一」+「金融機構名稱二」)
            // 「金融機構總代號」=「帳號」(前：1~3碼)。
            // 「分支代號」=「帳號」(前：4~7碼)。
            // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
            String accountChkF = caseObj.getChkPayBankIdChkBranchId();// 帳號(前)
            String accountChkB = StringUtils.leftPad(caseObj.getChkPayEeacc(), 14, "0");// 帳號(後)

            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(caseObj.getPayTyp())) {

                if (accountChkF.length() >= 3) {
                    caseObj.setChkPayBankId(accountChkF.substring(0, 3));
                }
                else {
                    caseObj.setChkPayBankId(accountChkF.substring(0, accountChkF.length()));
                }
                if (accountChkF.length() >= 4) {
                    caseObj.setChkBranchId(accountChkF.substring(3, accountChkF.length()));
                }
                else {
                    caseObj.setChkBranchId("");
                }
                caseObj.setChkPayEeacc(accountChkB);
            }
            // 當「給付方式」=2時，「金融機構名稱」= 郵局名稱
            // 「金融機構總代號」=「帳號」(前：1~3碼)。
            // 「分支代號」=「帳號」(前：4~7碼)。
            // 「銀行帳號」=「帳號」(後)，長度不足14碼時，資料左補0補足14碼長度。
            else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(caseObj.getPayTyp())) {

                if (accountChkF.length() >= 3) {
                    caseObj.setChkPayBankId(accountChkF.substring(0, 3));
                }
                else {
                    caseObj.setChkPayBankId(accountChkF.substring(0, accountChkF.length()));
                }
                if (accountChkF.length() >= 4) {
                    caseObj.setChkBranchId(accountChkF.substring(3, accountChkF.length()));
                }
                else {
                    caseObj.setChkBranchId("");
                }
                caseObj.setChkPayEeacc(accountChkB);
            }
            // 「給付方式」=3、4時，「金融機構名稱」= ' '
            // 「金融機構總代號」= ' '
            // 「分支代號」= ' '
            // 「銀行帳號」= ' '
            else {
                caseObj.setChkPayBankId("");
                caseObj.setChkBranchId("");
                caseObj.setChkPayEeacc("");
            }
        }
        // 具名領取
        else if (("2").equals(caseObj.getPayCategory())) {
            caseObj.setAccRel("3");
            caseObj.setMitRate(new BigDecimal(0));
            String[] accData = caseObj.getAccData().split(";");
            // 取得具名領取相關資料
            if (("insertMode").equals(actionType)) {
                if (accData.length == 2) {
                    caseObj.setAccSeqNo(accData[1]);
                }
                Bafamilytemp bafamilytemp = bafamilytempDao.selectBankDataBy(caseObj.getBafamilytempId(), caseObj.getAccSeqNo());
                if (bafamilytemp != null) {
                    caseObj.setPayTyp(bafamilytemp.getPayTyp());
                    caseObj.setBankName(bafamilytemp.getBankName());
                    caseObj.setPayBankId(bafamilytemp.getPayBankId());
                    caseObj.setBranchId(bafamilytemp.getBranchId());
                    caseObj.setPayEeacc(bafamilytemp.getPayEeacc());
                    // 帳號複驗
                    caseObj.setChkPayBankId(bafamilytemp.getChkPayBankId());
                    caseObj.setChkBranchId(bafamilytemp.getChkBranchId());
                    caseObj.setChkPayEeacc(bafamilytemp.getChkPayEeacc());
                    caseObj.setAccIdn(bafamilytemp.getAccIdn());
                    caseObj.setAccName(bafamilytemp.getAccName());
                }
                else {
                    caseObj.setPayTyp("");
                    caseObj.setBankName("");
                    caseObj.setPayBankId("");
                    caseObj.setBranchId("");
                    caseObj.setPayEeacc("");
                    caseObj.setChkPayBankId("");
                    caseObj.setChkBranchId("");
                    caseObj.setChkPayEeacc("");
                    caseObj.setAccIdn("");
                    caseObj.setAccName("");
                }
            }
            else if (("updateMode").equals(actionType)) {
                BigDecimal tmpBaappbasId = null;
                if (accData.length == 2) {
                    tmpBaappbasId = new BigDecimal(accData[0]);
                    caseObj.setAccSeqNo(accData[1]);
                }
                Baappbase baappbase = baappbaseDao.selectBankDataBy(tmpBaappbasId, caseObj.getAccSeqNo());
                if (baappbase != null) {
                    caseObj.setPayTyp(baappbase.getPayTyp());
                    caseObj.setBankName(baappbase.getBankName());
                    caseObj.setPayBankId(baappbase.getPayBankId());
                    caseObj.setBranchId(baappbase.getBranchId());
                    caseObj.setPayEeacc(baappbase.getPayEeacc());
                    // 帳號複驗
                    caseObj.setChkPayBankId(baappbase.getChkPayBankId());
                    caseObj.setChkBranchId(baappbase.getChkBranchId());
                    caseObj.setChkPayEeacc(baappbase.getChkPayEeacc());
                    caseObj.setAccIdn(baappbase.getAccIdn());
                    caseObj.setAccName(baappbase.getAccName());
                }
                else {
                    caseObj.setPayTyp("");
                    caseObj.setBankName("");
                    caseObj.setPayBankId("");
                    caseObj.setBranchId("");
                    caseObj.setPayEeacc("");
                    caseObj.setChkPayBankId("");
                    caseObj.setChkBranchId("");
                    caseObj.setChkPayEeacc("");
                    caseObj.setAccIdn("");
                    caseObj.setAccName("");
                }
            }
        }

        if (("insertMode").equals(actionType)) {
            caseObj.setFamAppDate(caseObj.getBenAppDate());// 遺屬/眷屬申請日期
            caseObj.setFamIdnNo(caseObj.getBenIdnNo());// 遺屬/眷屬身分證號
            caseObj.setFamName(caseObj.getBenName());// 遺屬/眷屬姓名
            caseObj.setFamBrDate(caseObj.getBenBrDate());// 遺屬/眷屬出生日期
            caseObj.setFamSex(caseObj.getBenSex());// 遺屬/眷屬性別
            caseObj.setFamNationTyp(caseObj.getBenNationTyp());// 遺屬/眷屬國籍別
            caseObj.setFamNationCode(caseObj.getBenNationCode());// 遺屬/眷屬國籍
            caseObj.setFamEvtRel(caseObj.getBenEvtRel());// 遺屬/眷屬與事故者關係
        }

        // 手機複驗
        String tel1 = caseObj.getTel1();
        String tel2 = caseObj.getTel2();
        String shortTel1 = "";
        String shortTel2 = "";
        if (tel1.length() >= 2) {
            shortTel1 = tel1.substring(0, 2);
        }
        if (tel2.length() >= 2) {
            shortTel2 = tel2.substring(0, 2);
        }

        if (!((StringUtils.equals(shortTel1, "09") && tel1.length() == 10) || (StringUtils.equals(shortTel2, "09") && tel2.length() == 10))) {
            caseObj.setMobilePhone("");
        }
        return caseObj;
    }

    /**
     * 新增 遺屬暫存檔資料 For 遺屬年金受理作業<br>
     * 
     * @param caseObj 眷屬資料
     * @param userData user資料
     */
    public String insertSurvivorBafamilytempData(SurvivorAnnuityReceiptBenCase caseObj, UserBean userData) {
        log.debug("Start Insert BAFAMILYTEMP ...");
        Bafamilytemp bafamilytemp = new Bafamilytemp();
        BeanUtility.copyProperties(bafamilytemp, caseObj);
        // 取得資料序號
        String seqNo = bafamilytempDao.selectNewSeqNo(caseObj.getBafamilytempId());
        bafamilytemp.setSeqNo(seqNo);
        if (("1").equals(caseObj.getPayCategory())) {
            bafamilytemp.setAccSeqNo(seqNo);
        }
        bafamilytemp.setCrtUser(userData.getEmpNo());
        bafamilytemp.setCrtTime(DateUtility.getNowWestDateTime(true));

        bafamilytempDao.insertDataForSurvivorAnnuityReceipt(bafamilytemp);
        log.debug("Insert BAFAMILYTEMP Finished...");
        
        return seqNo;
    }

    /**
     * 修改 遺屬暫存檔資料 For 遺屬年金受理作業<br>
     * 
     * @param caseObj 眷屬資料
     * @param userData user資料
     */
    public void updateSurvivorBafamilytempData(SurvivorAnnuityReceiptBenCase caseObj) {
        log.debug("Start Update BAFAMILYTEMP ...");
        Bafamilytemp bafamilytemp = new Bafamilytemp();
        BeanUtility.copyProperties(bafamilytemp, caseObj);
        // if (("1").equals(caseObj.getPayCategory())) {
        // bafamilytemp.setAccSeqNo(caseObj.getSeqNo());
        // }
        bafamilytempDao.updateDataForSurvivorAnnuityReceipt(bafamilytemp);

        // 更新相關資料之具名領取資料
        if (("1").equals(caseObj.getAccRel())) {
            bafamilytempDao.updateAccDataForSurvivorAnnuityReceipt(bafamilytemp);
        }
        log.debug("Update BAFAMILYTEMP Finished...");
    }

    /**
     * 新增 給付主檔 資料 for 遺屬年金受理作業
     * 
     * @param userData 使用者資料
     * @param baappbase 給付主檔
     */
    public void insertSurvivorBaappbaseBenData(SurvivorAnnuityReceiptEvtCase evtCase, SurvivorAnnuityReceiptBenCase caseObj, UserBean userData) {
        // 新增給付主檔資料
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, caseObj);
        String seqNo = baappbaseDao.getNewSeqNoForSurvivorAnnuity(baappbase.getApNo());
        baappbase.setSeqNo(seqNo);
        if (("1").equals(baappbase.getAccRel())) {
            baappbase.setAccSeqNo(seqNo);
        }

        baappbase.setApUbno(evtCase.getApUbno());
        baappbase.setAppDate(caseObj.getBenAppDate());
        baappbase.setApItem(evtCase.getApItem());
        baappbase.setEvtIds(evtCase.getEvtIds());
        baappbase.setEvtIdnNo(evtCase.getEvtIdnNo());
        baappbase.setEvtName(evtCase.getEvtName());
        baappbase.setEvtAppName(evtCase.getEvtAppName());
        baappbase.setEvtBrDate(DateUtility.changeDateType(evtCase.getEvtBrDate()));
        baappbase.setEvtSex(evtCase.getEvtSex());
        baappbase.setEvtNationTpe(evtCase.getEvtNationTpe());
        baappbase.setEvtNationCode(evtCase.getEvtNationCode());
        baappbase.setEvtJobDate(DateUtility.changeDateType(evtCase.getEvtJobDate()));
        baappbase.setEvtDieDate(DateUtility.changeDateType(evtCase.getEvtDieDate()));
        baappbase.setEvtAge(evtCase.getEvtAge());

        baappbase.setImk("1");
        baappbase.setPayKind("55");
        baappbase.setCaseTyp("1");
        baappbase.setProcStat("00");
        baappbase.setEvtIdent("1");
        baappbase.setBenPayMk("1");
        baappbase.setCrtUser(userData.getEmpNo());// 新增者代號
        baappbase.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間

        BigDecimal baappbaseId = baappbaseDao.insertBenDataForSurvivorAnnuityReceipt(baappbase);

        // 新增給付延伸檔資料
        Baappexpand baappexpand = new Baappexpand();
        BeanUtility.copyProperties(baappexpand, caseObj);
        baappexpand.setBaappbaseId(baappbaseId);
        baappexpand.setSeqNo(seqNo);
        baappexpand.setCrtUser(userData.getEmpNo());// 新增者代號
        baappexpand.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
        BigDecimal baappexpandId = baappexpandDao.insertDataForSurvivorAnnuityReceipt(baappexpand);

        // Insert MMAPLOG
        // [
        baappbase.setBaappbaseId(baappbaseId);
        baappexpand.setBaappexpandId(baappexpandId);
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baappbase);
            FrameworkLogUtil.doInsertLog(baappexpand);
        }
        log.debug("Insert BAAPPBASE Finished ...");
        // ]

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");
        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(baappbaseId);// 資料列編號
        baapplog.setStatus("A");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        baapplogDao.insertData(baapplog);
        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 修改 給付主檔 資料 for 遺屬年金受理作業 遺屬資料
     * 
     * @param userData 使用者資料
     * @param baappbase 給付主檔
     */
    public void updateSurvivorBaappbaseBenData(SurvivorAnnuityReceiptBenCase benCase, UserBean userData) {
        Baappbase beforeBaappbase = new Baappbase();// 改前值物件
        Baappbase afterBaappbase = new Baappbase();// 改後值物件
        List<Baappbase> beforeBaappbaseList = new ArrayList<Baappbase>();// 改前值物件List
        List<Baappbase> afterBaappbaseList = new ArrayList<Baappbase>();// 改後值物件List

        Baappexpand beforeBaappexpand = new Baappexpand();// 延伸主檔改前值物件

        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, benCase);
        baappbase.setAppDate(benCase.getBenAppDate());// 申請日期
        baappbase.setUpdUser(userData.getEmpNo());// 修改者代號
        baappbase.setUpdTime(DateUtility.getNowWestDateTime(true));// 修改日期時間

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 取得改前值物件
            beforeBaappbase = baappbaseDao.selectDetailDataForSurvivorAnnuityReceiptBy(baappbase.getBaappbaseId(), null, null, null);
            BeanUtility.copyProperties(afterBaappbase, beforeBaappbase);
            beforeBaappbaseList.add(beforeBaappbase);

            // 改後值物件
            afterBaappbase.setAppDate(baappbase.getAppDate());// 申請日期
            afterBaappbase.setBenIds(baappbase.getBenIds());// 社福識別碼
            afterBaappbase.setBenIdnNo(baappbase.getBenIdnNo());// 事故者身分證號
            afterBaappbase.setBenName(baappbase.getBenName());// 事故者姓名
            afterBaappbase.setBenBrDate(baappbase.getBenBrDate());// 事故者出生日期
            afterBaappbase.setBenEvtRel(baappbase.getBenEvtRel());// // 關係
            afterBaappbase.setBenSex(baappbase.getBenSex());// (事故者)性別
            afterBaappbase.setBenNationTyp(baappbase.getBenNationTyp());// (事故者)國籍別
            afterBaappbase.setBenNationCode(baappbase.getBenNationCode());// (事故者)國籍
            afterBaappbase.setTel1(baappbase.getTel1());// 電話1
            afterBaappbase.setTel2(baappbase.getTel2());// 電話2
            afterBaappbase.setCommTyp(baappbase.getCommTyp());// 通訊地址別
            afterBaappbase.setCommZip(baappbase.getCommZip());// 郵遞區號
            afterBaappbase.setCommAddr(baappbase.getCommAddr());// 地址
            afterBaappbase.setPayTyp(baappbase.getPayTyp());// 給付方式
            afterBaappbase.setGrdIdnNo(baappbase.getGrdIdnNo());// 法定代理人身分證號
            afterBaappbase.setGrdName(baappbase.getGrdName());// 法定代理人姓名
            afterBaappbase.setGrdBrDate(baappbase.getGrdBrDate());// 法定代理人出生日期
            afterBaappbase.setMarryDate(baappbase.getMarryDate());// 結婚日期
            afterBaappbase.setAccRel(baappbase.getAccRel());// 戶名與受益人關係
            afterBaappbase.setAccSeqNo(baappbase.getAccSeqNo());// 被共同具領之受款人員序號
            afterBaappbase.setStudMk(baappbase.getStudMk());// 在學
            afterBaappbase.setMonIncomeMk(baappbase.getMonIncomeMk());// 每月工作收入註記
            afterBaappbase.setMonIncome(baappbase.getMonIncome());// 每月工作收入
            afterBaappbase.setHandIcapMk(baappbase.getHandIcapMk());// 領有重度以上身心障礙手冊或證明
            afterBaappbase.setInterDictMk(baappbase.getInterDictMk());// 受禁治產(監護)宣告
            afterBaappbase.setAccIdn(baappbase.getAccIdn());// 事故者身分證號
            afterBaappbase.setAccName(baappbase.getAccName());// 事故者姓名
            afterBaappbase.setAccRel(baappbase.getAccRel());// 戶名與受益人關係
            afterBaappbase.setBankName(baappbase.getBankName());// 金融機構名稱
            afterBaappbase.setPayBankId(baappbase.getPayBankId());// 金融機構總代號
            afterBaappbase.setBranchId(baappbase.getBranchId());// 分支代號
            afterBaappbase.setPayEeacc(baappbase.getPayEeacc());// 銀行帳號
            afterBaappbase.setChkPayBankId(baappbase.getChkPayBankId());// 金融機構總代號 複驗
            afterBaappbase.setChkBranchId(baappbase.getChkBranchId());// 分支代號 複驗
            afterBaappbase.setChkPayEeacc(baappbase.getChkPayEeacc());// 銀行帳號 複驗
            afterBaappbase.setUpdUser(baappbase.getUpdUser());// Session.員工編號
            afterBaappbase.setUpdTime(baappbase.getUpdTime());// 修改時間
            afterBaappbase.setMobilePhone(baappbase.getMobilePhone());// 手機複驗
            // 20141003新增判斷 申請項目 給予 NOTIFYFORM
            if (afterBaappbase.getApItem().equals("4") || afterBaappbase.getApItem().equals("5")) {
                afterBaappbase.setNotifyForm("001");
            }
            else if (afterBaappbase.getApItem().equals("7")) {
                afterBaappbase.setNotifyForm("031");
            }
            else if (afterBaappbase.getApItem().equals("8")) {
                afterBaappbase.setNotifyForm("023");
            }
            afterBaappbaseList.add(afterBaappbase);

            // 取得需記錄異動LOG的欄位資料
            // 為不影響前端頁面顯示, 故須複製一份 Form
            SurvivorAnnuityReceiptForm tempForm = new SurvivorAnnuityReceiptForm();
            BeanUtility.copyPropertiesForUpdate(tempForm, beforeBaappbase, ConstantKey.OLD_FIELD_PREFIX);
            BeanUtility.copyProperties(tempForm, afterBaappbase);
            benCase.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_RECEIPT_SURVIVOR_FIELD_RESOURCE_PREFIX));
        }
        baappbaseDao.updateBenDataForSurvivorAnnuityReceipt(baappbase);

        // 修改給付延伸檔資料
        Baappexpand baappexpand = new Baappexpand();
        BeanUtility.copyProperties(baappexpand, benCase);
        BeanUtility.copyProperties(beforeBaappexpand, benCase);
        baappexpand.setUpdUser(userData.getEmpNo());// 新增者代號
        baappexpand.setUpdTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
        baappexpandDao.updateBenDataForSurvivorAnnuityReceipt(baappexpand);

        // 更新 相關具名領取資料
        if (("1").equals(baappbase.getAccRel())) {
            List<Baappbase> accDataList = baappbaseDao.selectAccDataForSurvivorAnnuityReceipt(baappbase.getApNo(), baappbase.getSeqNo());
            List<Baappbase> updateList = new ArrayList<Baappbase>();
            for (int i = 0; i < accDataList.size(); i++) {
                beforeBaappbase = new Baappbase();// 改前值物件
                afterBaappbase = new Baappbase();// 改後值物件

                beforeBaappbase = accDataList.get(i);
                beforeBaappbaseList.add(beforeBaappbase);
                BeanUtility.copyProperties(afterBaappbase, beforeBaappbase);

                afterBaappbase.setAccIdn(baappbase.getAccIdn());
                afterBaappbase.setAccName(baappbase.getAccName());
                afterBaappbase.setBankName(baappbase.getBankName());
                afterBaappbase.setPayBankId(baappbase.getPayBankId());
                afterBaappbase.setBranchId(baappbase.getBranchId());
                afterBaappbase.setPayEeacc(baappbase.getPayEeacc());
                afterBaappbase.setChkPayBankId(baappbase.getChkPayBankId());
                afterBaappbase.setChkBranchId(baappbase.getChkBranchId());
                afterBaappbase.setChkPayEeacc(baappbase.getChkPayEeacc());
                afterBaappbase.setPayTyp(baappbase.getPayTyp());

                afterBaappbaseList.add(afterBaappbase);
                updateList.add(afterBaappbase);
            }

            if (updateList.size() != 0) {
                baappbaseDao.updateAccDataForSurvivorAnnuityReceipt(updateList);
            }
        }

        // Insert MMAPLOG
        // [
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeBaappbaseList, afterBaappbaseList);
            FrameworkLogUtil.doUpdateLog(beforeBaappexpand, baappexpand);
        }
        // ]

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");
        List<Baapplog> logList = benCase.getBaapplogList();
        for (int i = 0; i < logList.size(); i++) {
            Baapplog baapplog = logList.get(i);
            baapplog.setBaappbaseId(benCase.getBaappbaseId());// 資料列編號
            baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            // baapplog.setUpCol(); // 異動項目 - Log 已設
            // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
            // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
            baapplogDao.insertData(baapplog);
        }
        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 新增 遺屬年金資料
     * 
     * @param userData 使用者資料
     * @param evtCase 事故者資料
     * @param bafamilytempId 遺屬暫存檔資料列編號
     */
    public void insertDataForSurvivor(UserBean userData, SurvivorAnnuityReceiptEvtCase evtCase, BigDecimal bafamilytempId) {
        // 主檔物件List
        List<Object> baappbaseList = new ArrayList<Object>();
        // 延伸檔物件List
        List<Baappexpand> baappexpandList = new ArrayList<Baappexpand>();
        // 給付主檔資料列編號
        BigDecimal baappbaseId = null;

        // 新增給付主檔資料 for 事故者
        Baappbase evtObj = new Baappbase();
        BeanUtility.copyProperties(evtObj, evtCase);
        evtObj.setSeqNo("0000");
        evtObj.setImk("1");
        evtObj.setPayKind("55");
        evtObj.setCaseTyp("1");
        evtObj.setProcStat("00");
        evtObj.setEvtIdent("1");
        evtObj.setBenEvtRel("1");
        evtObj.setBenPayMk("0");
        evtObj.setMitRate(new BigDecimal(0));
        evtObj.setAccSeqNo("0000");
        evtObj.setAccRel("1");
        evtObj.setCrtUser(userData.getEmpNo());// 新增者代號
        evtObj.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
        // 20141003新增判斷 申請項目 給予 NOTIFYFORM
        if (evtObj.getApItem().equals("4") || evtObj.getApItem().equals("5")) {
            evtObj.setNotifyForm("001");
        }
        else if (evtObj.getApItem().equals("7")) {
            evtObj.setNotifyForm("031");
        }
        else if (evtObj.getApItem().equals("8")) {
            evtObj.setNotifyForm("023");
        }
        // baappbaseList.add(evtObj);
        baappbaseId = baappbaseDao.insertEvtDataForSurvivorAnnuityReceipt(evtObj);

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");
        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(baappbaseId);// 資料列編號
        baapplog.setStatus("A");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplog.setUpCol(); // 異動項目
        // baapplog.setBvalue("");// 改前內容
        // baapplog.setAvalue(); // 改後內容
        baapplogDao.insertData(baapplog);
        log.debug("Insert BAAPPLOG Finished ...");
        // ]

        // 新增延伸檔資料 for 事故者
        Baappexpand baappexpand = new Baappexpand();
        BeanUtility.copyProperties(baappexpand, evtCase);
        baappexpand.setBaappbaseId(baappbaseId);
        baappexpand.setSeqNo("0000");
        baappexpand.setCrtUser(userData.getEmpNo());// 新增者代號
        baappexpand.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
//        baappexpand.setEvAppTyp(evtCase.getEvTyp());// 申請傷病分類
        BigDecimal baappexpandId = baappexpandDao.insertDataForSurvivorAnnuityReceipt(baappexpand);

        baappexpand.setBaappexpandId(baappexpandId);
        log.debug("Start Insert MMAPLOG ...");
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            FrameworkLogUtil.doInsertLog(evtObj);
            FrameworkLogUtil.doInsertLog(baappexpand);
        }

        // 取得遺屬資料
        List<Bafamilytemp> famTmpDataList = bafamilytempDao.selectDataBy(bafamilytempId, null);
        for (int i = 0; i < famTmpDataList.size(); i++) {
            // 新增給付主檔資料 for 遺屬
            Baappbase benObj = new Baappbase();
            Bafamilytemp famData = famTmpDataList.get(i);
            BeanUtility.copyProperties(benObj, famData);
            // benObj.setBaappbaseId(baappbaseId);// 給付主檔資料列編號
            benObj.setApNo(evtCase.getApNo());
            String seqNo = i + 1 < 10 ? StringUtils.rightPad("0" + String.valueOf(i + 1), 4, "0") : StringUtils.rightPad(String.valueOf(i + 1), 4, "0");
            String accSeqNo = StringUtils.rightPad(benObj.getAccSeqNo(), 4, "0");
            benObj.setSeqNo(seqNo);// 序號 (照順序重新編排, 不照暫存檔內的序號儲存)
            benObj.setImk("1");
            benObj.setPayKind("55");
            benObj.setCaseTyp("1");
            benObj.setProcStat("00");
            benObj.setEvtIdent("1");
            benObj.setBenPayMk("1");
            benObj.setMitRate(new BigDecimal(0));
            if (!("3").equals(benObj.getAccRel())) {
                benObj.setAccSeqNo(seqNo);
            }
            benObj.setAppDate(famData.getFamAppDate());
            benObj.setApItem(evtObj.getApItem());
            benObj.setApUbno(evtObj.getApUbno());
            benObj.setEvtIds(evtObj.getEvtIds());// 「社福識別碼」
            benObj.setEvtIdnNo(evtObj.getEvtIdnNo());// 「事故者身分證號」
            benObj.setEvtName(evtObj.getEvtName());// 「事故者姓名」
            benObj.setEvtAppName(evtObj.getEvtAppName());// 「事故者姓名」
            benObj.setEvtBrDate(evtObj.getEvtBrDate());// 「事故者出生日期」
            benObj.setEvtSex(evtObj.getEvtSex());// 「(事故者)性別」
            benObj.setEvtNationTpe(evtObj.getEvtNationTpe());// 「(事故者)國籍別」
            benObj.setEvtNationCode(evtObj.getEvtNationCode());// 「(事故者)國籍」
            benObj.setEvtJobDate(evtObj.getEvtJobDate());// 「死亡日期」
            benObj.setEvtDieDate(evtObj.getEvtDieDate());// 「死亡日期」
            benObj.setEvtAge(evtObj.getEvtAge());// 「事故者年齡」
            benObj.setAccSeqNo(accSeqNo);

            benObj.setBenIdnNo(famData.getFamIdnNo());// 「(對應)遺屬身分證號」
            benObj.setBenName(famData.getFamName());// 「(對應)遺屬姓名」
            benObj.setBenBrDate(famData.getFamBrDate());// 「(對應)遺屬出生日期」
            benObj.setBenSex(famData.getFamSex());// 「(對應)遺屬性別」
            benObj.setBenNationTyp(famData.getFamNationTyp());// 「(對應)遺屬國籍別」
            benObj.setBenNationCode(famData.getFamNationCode());// 「(對應)遺屬國籍」
            benObj.setBenEvtRel(famData.getFamEvtRel());// 「(對應)遺屬與事故者關係」
            // 根據 遺屬身分證號, 出生日期 取得戶政資料
            Cvldtl cvldtlBenData = selectCvldtlNameBy(benObj.getBenIdnNo(), benObj.getBenBrDate());
            // 遺屬社福識別碼
            if (StringUtils.isNotBlank(cvldtlBenData.getNpIds())) {
                benObj.setBenIds(cvldtlBenData.getNpIds());
            }
            else {
                benObj.setBenIds("");
            }

            benObj.setCrtUser(userData.getEmpNo());// 新增者代號
            benObj.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間

            // 20141003新增判斷 申請項目 給予 NOTIFYFORM
            if (benObj.getApItem().equals("4") || benObj.getApItem().equals("5")) {
                benObj.setNotifyForm("001");
            }
            else if (benObj.getApItem().equals("7")) {
                benObj.setNotifyForm("031");
            }
            else if (benObj.getApItem().equals("8")) {
                benObj.setNotifyForm("023");
            }
            benObj.setApnoFm(evtObj.getApnoFm());
            benObj.setSysCode(evtObj.getSysCode());

            baappbaseId = baappbaseDao.insertBenDataForSurvivorAnnuityReceipt(benObj);

            // Insert BAAPPLOG
            // [
            log.debug("Start Insert BAAPPLOG ...");
            Baapplog baapplogBen = new Baapplog();
            baapplogBen.setBaappbaseId(baappbaseId);// 資料列編號
            baapplogBen.setStatus("A");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplogBen.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplogBen.setUpdUser(userData.getEmpNo());// 異動人員
            // baapplog.setUpCol(); // 異動項目
            // baapplog.setBvalue("");// 改前內容
            // baapplog.setAvalue(); // 改後內容
            baapplogDao.insertData(baapplogBen);
            log.debug("Insert BAAPPLOG Finished ...");
            // ]

            benObj.setBaappbaseId(baappbaseId);
            // baappbaseList.add(benObj);

            // 新增延伸檔資料 for 遺屬
            baappexpand = new Baappexpand();
            BeanUtility.copyProperties(baappexpand, benObj);
            baappexpand.setBaappbaseId(baappbaseId);
            baappexpand.setSeqNo(seqNo);
            baappexpand.setEvTyp(evtCase.getEvTyp());// 傷病分類
            baappexpand.setCrtUser(userData.getEmpNo());// 新增者代號
            baappexpand.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
            baappexpand.setEvAppTyp(evtCase.getEvAppTyp());// 申請傷病分類
            baappexpandId = baappexpandDao.insertDataForSurvivorAnnuityReceipt(baappexpand);

            // Insert MMAPLOG
            // [
            baappexpand.setBaappexpandId(baappexpandId);
            log.debug("Start Insert MMAPLOG ...");
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                FrameworkLogUtil.doInsertLog(benObj);
                FrameworkLogUtil.doInsertLog(baappexpand);
            }
            // ]
        }

        // 搬檔完成後刪除眷屬暫存檔資料
        bafamilytempDao.deleteDataBy(bafamilytempId, null);

        // 更新ACCSEQNO
        for (int i = 0; i < baappbaseList.size(); i++) {
            Baappbase baappbase = (Baappbase) baappbaseList.get(i);
            Baappbase afterBaappbasee = new Baappbase();
            BeanUtility.copyProperties(baappbase, afterBaappbasee);
            if (("3").equals(baappbase.getAccRel())) {
                String accSeqNo = baappbaseDao.selectAccSeqNoForSurvivorAnnuityReceipt(baappbase.getApNo(), baappbase.getAccIdn());
                baappbaseDao.updateAccSeqNoForSurvivorAnnuityReceipt(accSeqNo, baappbase.getBaappbaseId());
                afterBaappbasee.setAccSeqNo(accSeqNo);
            }

        }

    }

    /**
     * 更新 給付主檔 資料 for 遺屬年金受理作業 事故者資料
     * 
     * @param userData 使用者資料
     * @param evtCase 事故者資料
     */
    public void updateDataForSurvivor(UserBean userData, SurvivorAnnuityReceiptEvtCase evtCase) {
        // 改前值物件List
        List<Object> beforeList = new ArrayList<Object>();
        // 改後值物件List
        List<Object> afterList = new ArrayList<Object>();
        // 延伸檔物件List
        List<Baappexpand> baappexpandList = new ArrayList<Baappexpand>();
        // 改前改後值List for BAAPPLOG
        List<SurvivorAnnuityReceiptEvtCase> evtBaapplogList = new ArrayList<SurvivorAnnuityReceiptEvtCase>();

        // 延伸主檔改前值
        List<Object> beforBaappexpandList = new ArrayList<Object>();
        List<Object> afterBaappexpandList = new ArrayList<Object>();

        // 取得資料清單
        List<Baappbase> dataList = baappbaseDao.selectDetailDataListForSurvivorAnnuityReceiptBy(evtCase.getApNo(), null, new String[] { "00" }, "in");
        for (int i = 0; i < dataList.size(); i++) {
            // 給付主檔改前值物件
            Baappbase beforeObj = dataList.get(i);
            beforeList.add(beforeObj);

            // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
            SurvivorAnnuityReceiptEvtCase caseObj = new SurvivorAnnuityReceiptEvtCase();
            BeanUtility.copyProperties(caseObj, beforeObj);
            SurvivorAnnuityReceiptForm objForm = new SurvivorAnnuityReceiptForm();
            BeanUtility.copyPropertiesForUpdate(objForm, caseObj, ConstantKey.OLD_FIELD_PREFIX);

            // 給付主檔改後值物件
            Baappbase afterObj = new Baappbase();
            BeanUtility.copyProperties(afterObj, beforeObj);
            afterObj.setAppDate(evtCase.getAppDate());// 申請日期
            afterObj.setApUbno(evtCase.getApUbno());// 申請單位保險證號
            afterObj.setApItem(evtCase.getApItem());// 申請項目
            afterObj.setEvtIds(evtCase.getEvtIds());// 社福識別碼
            afterObj.setEvtIdnNo(evtCase.getEvtIdnNo());// 事故者身分證號
            afterObj.setEvtName(evtCase.getEvtName());// 事故者姓名
            afterObj.setEvtAppName(evtCase.getEvtAppName());// 事故者姓名
            afterObj.setEvtBrDate(evtCase.getEvtBrDate());// 事故者出生日期
            afterObj.setEvtSex(evtCase.getEvtSex());// (事故者)性別
            afterObj.setEvTyp(evtCase.getEvTyp());// 傷病分類
            afterObj.setEvtNationTpe(evtCase.getEvtNationTpe());// (事故者)國籍別
            afterObj.setEvtNationCode(evtCase.getEvtNationCode());// (事故者)國籍
            afterObj.setEvtJobDate(evtCase.getEvtJobDate());// 死亡日期
            afterObj.setEvtDieDate(evtCase.getEvtDieDate());// 死亡日期
            afterObj.setEvtAge(evtCase.getEvtAge());// 事故者年齡
            afterObj.setUpdUser(userData.getEmpNo());// Session.員工編號
            afterObj.setUpdTime(DateUtility.getNowWestDateTime(true));// 系統日期時間
            // 20141003新增判斷 申請項目 給予 NOTIFYFORM
            if (afterObj.getApItem().equals("4") || afterObj.getApItem().equals("5")) {
                afterObj.setNotifyForm("001");
            }
            else if (afterObj.getApItem().equals("7")) {
                afterObj.setNotifyForm("031");
            }
            else if (afterObj.getApItem().equals("8")) {
                afterObj.setNotifyForm("023");
            }
            afterList.add(afterObj);

            // 給付延伸檔
            Baappexpand baappexpand = new Baappexpand();
            Baappexpand beforBaappexpand = new Baappexpand();
            BeanUtility.copyProperties(beforBaappexpand, beforeObj);
            BeanUtility.copyProperties(baappexpand, beforeObj);
            baappexpand.setEvTyp(evtCase.getEvTyp());// 傷病分類
            baappexpand.setUpdUser(userData.getEmpNo());// Session.員工編號
            baappexpand.setUpdTime(DateUtility.getNowWestDateTime(true));// 系統日期時間
            baappexpand.setEvAppTyp(evtCase.getEvTyp());// 申請傷病分類
            baappexpandList.add(baappexpand);
            beforBaappexpandList.add(beforBaappexpand);
            afterBaappexpandList.add(baappexpand);

            if (beforeObj.getSeqNo().equals("0000")) {
                // 改前改後值 for BAAPPLOG 只修改0000
                // [
                BeanUtility.copyProperties(objForm, afterObj);
                objForm.setAppDate(evtCase.getAppDate());// 申請日期
                objForm.setApUbno(evtCase.getApUbno());// 申請單位保險證號
                objForm.setEvtIdnNo(evtCase.getEvtIdnNo());// 事故者身分證號
                objForm.setEvtName(evtCase.getEvtName());// 事故者姓名
                objForm.setEvtBrDate(evtCase.getEvtBrDate());// 事故者出生日期
                objForm.setEvtSex(evtCase.getEvtSex());// (事故者)性別
                objForm.setEvtNationTpe(evtCase.getEvtNationTpe());// (事故者)國籍別
                objForm.setEvtNationCode(evtCase.getEvtNationCode());// (事故者)國籍
                objForm.setEvtJobDate(evtCase.getEvtJobDate());// 死亡日期
                objForm.setEvtDieDate(evtCase.getEvtDieDate());// 死亡日期
                objForm.setBenEvtRel("");
                objForm.setBenNationTyp("");
                // ]
                caseObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_RECEIPT_SURVIVOR_FIELD_RESOURCE_PREFIX));
                evtBaapplogList.add(caseObj);
            }
        }

        // 更新給付主檔
        baappbaseDao.updateEvtDataForSurvivorAnnuityReceipt((Baappbase) afterList.get(0));

        // 更新給付延伸檔
        baappexpandDao.updateEvtDataForSurvivorAnnuityReceipt(baappexpandList);

        // Insert MMAPLOG
        // [
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
            FrameworkLogUtil.doUpdateListLog(beforBaappexpandList, afterBaappexpandList);
        }
        // ]

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int i = 0; i < evtBaapplogList.size(); i++) {
            SurvivorAnnuityReceiptEvtCase logObj = evtBaapplogList.get(i);
            BigDecimal baappbaseId = logObj.getBaappbaseId();
            List<Baapplog> logList = logObj.getBaapplogList();
            for (int j = 0; j < logList.size(); j++) {
                Baapplog baapplog = logList.get(j);
                baapplog.setBaappbaseId(baappbaseId);// 資料列編號
                baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
                baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
                baapplog.setUpdUser(userData.getEmpNo());// 異動人員
                // baapplog.setUpCol(); // 異動項目 - Log 已設
                // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
                // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
                baapplogDao.insertData(baapplog);
            }
        }
        log.debug("Insert BAAPPLOG Finished ...");
        // ]

    }

    /**
     * 刪除 給付主檔 遺屬資料 for 遺屬年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     */
    public boolean deleteSurvivorBaappbaseBenData(BigDecimal baappbaseId, String apNo, UserBean userData) {
        // 事故者資料是否被刪除
        boolean isDeleteEvt = false;
        // 改前值物件List
        List<Object> beforeList = new ArrayList<Object>();

        // 刪除 選取的 給付主檔
        // [
        log.debug("Start Delete BAAPPBASE ...");

        // 取得改前值物件
        Baappbase beforBaappbase = baappbaseDao.selectDetailDataForSurvivorAnnuityReceiptBy(baappbaseId, null, null, null);
        beforeList.add(beforBaappbase);

        // 刪除 給付主檔
        baappbaseDao.deleteDataForSurvivorAnnuityReceiptById(baappbaseId, "00");

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");
        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(baappbaseId);// 資料列編號
        baapplog.setStatus("D");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        baapplogDao.insertData(baapplog);
        log.debug("Insert BAAPPLOG Finished ...");
        // ]

        // 檢查如果只剩事故者(seqNo='0000')的資料
        // 則一起刪除掉
        List<Baappbase> dataList = baappbaseDao.selectDataForAnnuityReceiptBy(null, apNo, null, new String[] { "00" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);
        if (dataList.size() == 1) {
            Baappbase tempObj = dataList.get(0);
            // 取得改前值物件
            Baappbase tempBaappbase = baappbaseDao.selectDetailDataForSurvivorAnnuityReceiptBy(tempObj.getBaappbaseId(), null, null, null);
            beforeList.add(tempBaappbase);
            // 刪除主檔資料
            baappbaseDao.deleteDataForSurvivorAnnuityReceiptById(tempObj.getBaappbaseId(), "00");

            // Insert BAAPPLOG
            // [
            log.debug("Start Insert BAAPPLOG ...");
            Baapplog baapplog2 = new Baapplog();
            baapplog2.setBaappbaseId(baappbaseId);// 資料列編號
            baapplog2.setStatus("D");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog2.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog2.setUpdUser(userData.getEmpNo());// 異動人員
            baapplogDao.insertData(baapplog2);
            log.debug("Insert BAAPPLOG Finished ...");
            // ]

            isDeleteEvt = true;
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteListLog(beforeList);
        }

        log.debug("Delete BAAPPBASE Finished ...");
        // ]
        return isDeleteEvt;
    }

    /**
     * 刪除 給付主檔 資料 for 遺屬年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     */
    public void deleteAllDataForSurvivor(UserBean userData, String apNo) {
        // 改前值物件List
        List<Object> beforeList = new ArrayList<Object>();

        // 刪除 選取的 給付主檔
        // [
        log.debug("Start Delete BAAPPBASE ...");

        // 取得改前值物件

        List<Baappbase> dataList = baappbaseDao.selectDataForAnnuityReceiptBy(null, apNo, null, new String[] { "00" }, "in", ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);
        for (int i = 0; i < dataList.size(); i++) {
            Baappbase tmpObj = dataList.get(i);
            Baappbase beforeObj = baappbaseDao.selectDetailDataForSurvivorAnnuityReceiptBy(tmpObj.getBaappbaseId(), null, null, null);
            beforeList.add(beforeObj);
        }
        // 刪除 該APNO所有 給付主檔資料
        baappbaseDao.deleteAllDataForSurvivorAnnuityReceiptByApNo(apNo);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteListLog(beforeList);
        }

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int i = 0; i < dataList.size(); i++) {
            Baappbase obj = (Baappbase) dataList.get(i);
            Baapplog baapplog = new Baapplog();
            baapplog.setBaappbaseId(obj.getBaappbaseId());// 資料列編號
            baapplog.setStatus("D");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            baapplogDao.insertData(baapplog);
        }
        log.debug("Insert BAAPPLOG Finished ...");
        // ]

        log.debug("Delete BAAPPBASE Finished ...");
        // ]
    }

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 已存在之遺屬資料 for 遺屬年金受理作業
     * 
     * @param apNo 受理編號
     * @param benIdnNo 遺屬身分證號
     * @param benBrDate 遺屬出生日期
     * @param baappbaseId 資料列編號
     * @param eqType SQL EqualType
     * @return <code>boolean</code>
     */
    public boolean checkExistBenData(String apNo, String benIdnNo, String benBrDate, BigDecimal baappbaseId, String eqType) {
        List<BigDecimal> list = baappbaseDao.selectExistDataIdForSurvivorAnnuityReceipt(apNo, benIdnNo, benBrDate, baappbaseId, eqType);
        boolean chkResult = false;
        if (list.size() != 0) {
            chkResult = true;
        }
        return chkResult;
    }

    // /**
    // * 依傳入資料(<code>SurvivorAnnuityReceiptEvtCase</code>) 將西元年月日轉換成民國年月日 for 遺屬年金受理作業 事故者資料
    // *
    // * @param caseobj 轉換資料之物件
    // * @return <code>SurvivorAnnuityReceiptEvtCase</code> 物件
    // */
    // public String transExistBenIdnBrDate(List<SurvivorAnnuityReceiptBenCase> caseList) {
    // String benIdnBrDate = "";
    // for (int i = 0; i < caseList.size(); i++) {
    // SurvivorAnnuityReceiptBenCase caseObj = caseList.get(i);
    // if (!("").equals(benIdnBrDate)) {
    // benIdnBrDate += ";";
    // }
    // benIdnBrDate += caseObj.getBenIdnNo() + caseObj.getBenBrDateStr();
    // }
    //
    // return benIdnBrDate;
    // }

    // ------------------------------ 遺屬年金受理作業 ------------------------------

    // ------------------------------ 共用function ------------------------------
    /**
     * 依傳入條件取得 戶政全戶檔 資料<br>
     * 為避免回傳資料超過一筆以上, 故查詢結果不等於一筆皆回傳空字串<br>
     * 
     * @param idn 事故者身分證號
     * @param ebDate 出生日期
     * @return
     */
    public Cvldtl selectCvldtlNameBy(String idn, String ebDate) {
        List<Cvldtl> list = cvldtlDao.selectDataBy(idn, ebDate);
        Cvldtl obj = new Cvldtl();
        // 為避免回傳資料超過一筆以上, 故查詢結果不等於一筆皆回傳空字串

        if (list.size() == 1) {
            obj = (Cvldtl) list.get(0);
            return obj;
        }
        else {
            return obj;
        }
    }

    /**
     * 依傳入條件取得 銀行資料檔 金融機構名稱<br>
     * 
     * @param payBankIdBranchId 帳號(前)
     * @return
     */
    public String getBankName(String payBankIdBranchId) {
        // 取得金融機構名稱一
        String bankName1 = "";
        if (payBankIdBranchId.length() >= 3) {
            bankName1 = bcbpfDao.selectBankNameBy(payBankIdBranchId.substring(0, 3) + "0000");
        }
        else {
            bankName1 = bcbpfDao.selectBankNameBy(payBankIdBranchId.substring(0, payBankIdBranchId.length()) + "0000");
        }
        // 取得金融機構名稱二

        String bankName2 = bcbpfDao.selectBankNameBy(payBankIdBranchId);

        // 分行名稱為空值回傳總行名稱, 否則只回傳分行名稱 (2008.01.31 modify by Goston)
        return (StringUtils.isBlank(bankName2)) ? StringUtils.defaultString(bankName1) : StringUtils.defaultString(bankName2);
    }

    /**
     * 依傳入條件取得 郵局代碼檔 郵局名稱<br>
     * 
     * @param postId 郵局電腦代號 (「帳號」(前))
     * @return
     */
    public String getPostName(String postId) {
        return baparamDao.selectParamNameBy(null, "PAYPOSTNAME", postId);
    }

    /**
     * 取得 SEQUENCE BAAPNO
     */
    public String getSequenceApNo() {
        return baappbaseDao.getSequenceApNo();
    }

    /**
     * 取得 SEQUENCE BAKAPNO
     */
    public String getSequenceKApNo() {
        return baappbaseDao.getSequenceKApNo();
    }

    /**
     * 取得 SEQUENCE BASAPNO
     */
    public String getSequenceSApNo() {
        return baappbaseDao.getSequenceSApNo();
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) new 資料列編號
     * 
     * @return <code>BigDecimal</code> 物件
     */
    public BigDecimal getNewbafamilytempId() {
        return bafamilytempDao.getSequenceBafamilytempId();
    }

    /**
     * 依傳入條件取得 眷屬暫存檔檔 (<code>BAFAMILYTEMP</code>) 已存在之眷屬資料
     * 
     * @param bafamilytempId 資料列編號
     * @param famIdnNo 遺屬/眷屬身分證號
     * @param famBrDate 遺屬/眷屬出生日期
     * @param seqNo 序號
     * @param eqType SQL EqualType
     * @return <code>boolean</code>
     */
    public boolean checkExistFamTempData(BigDecimal bafamilytempId, String famIdnNo, String famBrDate, String seqNo, String eqType) {
        List<BigDecimal> list = bafamilytempDao.selectExistDataForAnnuityReceiptBy(bafamilytempId, famIdnNo, famBrDate, seqNo, eqType);
        boolean chkResult = false;
        if (list.size() != 0) {
            chkResult = true;
        }
        return chkResult;
    }

    /**
     * 檢查APNO是否已經存在
     * 
     * @param apNo 受理編號
     * @return
     */
    public boolean checkApNoExist(String apNo) {
        List<Baappbase> list = baappbaseDao.selectDataBy(null, apNo, null, null, null, null);
        boolean result = false;
        if (list.size() != 0) {
            result = true;
        }
        return result;
    }

    /**
     * 刪除 眷屬暫存檔資料
     * 
     * @param caseObj 眷屬資料
     * @param userData user資料
     */
    public void deleteBafamilytempData(BigDecimal bafamilytempId, String seqNo) {
        log.debug("Start Delete BAFAMILYTEMP ...");

        bafamilytempDao.deleteDataBy(bafamilytempId, seqNo);

        log.debug("Delete BAFAMILYTEMP Finished...");
    }

    /**
     * 更新 眷屬暫存檔資料
     * 
     * @param caseObj 眷屬資料
     * @param userData user資料
     */
    public void updateBafamilytempSeqNoData(BigDecimal bafamilytempId, String beforeSeqNo, String afterSeqNo) {
        log.debug("Start Delete BAFAMILYTEMP ...");

        bafamilytempDao.updateSeqNoForSurvivorAnnuityReceipt(bafamilytempId, beforeSeqNo, afterSeqNo);
        bafamilytempDao.updateAcccSeqNoForSurvivorAnnuityReceipt(bafamilytempId, beforeSeqNo, afterSeqNo);

        log.debug("Delete BAFAMILYTEMP Finished...");
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 序號
     */
    public List<Bafamilytemp> selectDeleteAfterDataBy(BigDecimal bafamilytempId, String seqNo) {

        List<Bafamilytemp> bafamilyTemp = bafamilytempDao.selectDeleteAfterDataBy(bafamilytempId, seqNo);
        return bafamilyTemp;
    }

    /**
     * 刪除 眷屬暫存檔資料 By bafamilyId
     * 
     * @param bafamilyId 眷屬檔資料列編號
     */
    public void deleteBafamilyDataByBafamilyId(BigDecimal bafamilyId, UserBean userData) {
        log.debug("Start Delete BAFAMILY ...");

        Bafamily beforBafamily = bafamilyDao.getDependantDataUpdateForUpdateCaseBy(bafamilyId.toString());

        bafamilyDao.deleteDataByBafamilyId(bafamilyId);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteLog(beforBafamily);
        }

        // Insert BAAPPLOG
        // [
        log.debug("Start Insert BAAPPLOG ...");

        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(beforBafamily.getBaappbaseId());// 資料列編號
        baapplog.setStatus("D");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplog.setUpCol(); // 異動項目 - Log 已設
        // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
        // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
        baapplogDao.insertData(baapplog);

        log.debug("Insert BAAPPLOG Finished ...");
        // ]

        log.debug("Insert BAFAMILY Finished...");

        log.debug("Delete BAFAMILY Finished...");
    }

    /**
     * 刪除 眷屬暫存檔資料 By baappbaseId
     * 
     * @param baappbaseId 給付主檔資料列編號
     */
    public void deleteDisabledBafamilyDataByBaappbaseId(BigDecimal baappbaseId) {
        log.debug("Start Delete BAFAMILY ...");

        List<Bafamily> beforBafamily = bafamilyDao.selectDataByBaappbaseIdByLog(baappbaseId);

        bafamilyDao.deleteDataByBaappbaseId(baappbaseId);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteListLog(beforBafamily);
        }

        log.debug("Delete BAFAMILY Finished...");
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 被具名領取筆數
     * 
     * @param apNo 受理編號
     * @param seqNo 資料列序號
     * @return <code>String</code> 物件
     */
    public Integer getAccSeqNoAmtFromBaappbase(String apNo, String seqNo) {
        // Integer status = baappbaseDao.selectAccSeqNoAmt(apNo, seqNo);
        // String accSeqNoAmt = "0";
        // if (status > 0) {
        // accSeqNoAmt = "1";
        // }
        // return accSeqNoAmt;
        return baappbaseDao.selectAccSeqNoAmt(apNo, seqNo);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 被具名領取筆數
     * 
     * @param bafamilytempId 受理編號
     * @param seqNo 資料列序號
     * @return <code>String</code> 物件
     */
    public Integer getAccSeqNoAmtFromBafamilytemp(BigDecimal bafamilytempId, String seqNo) {
        // Integer status = bafamilytempDao.selectAccSeqNoAmt(bafamilytempId, seqNo);
        // String accSeqNoAmt = "0";
        // if (status > 0) {
        // accSeqNoAmt = "1";
        // }
        // return accSeqNoAmt;
        return bafamilytempDao.selectAccSeqNoAmt(bafamilytempId, seqNo);
    }
    
    /**
     * 保存畫面上已輸入之事故者資料 for 遺屬臨櫃受理
     * 
     * @param evtForm
     * @param famForm
     * @return
     */
    public SurvivorAnnuityWalkInReceiptForm keepInputEvtFormDataForWalkInSurvivor(
    		SurvivorAnnuityWalkInReceiptForm evtForm, SurvivorAnnuityWalkInReceiptForm benForm) {
    	if (evtForm != null && benForm != null) {
    		evtForm.setApNo1(benForm.getTempApNo1());// 受理編號-1
    		evtForm.setApNo2(benForm.getTempApNo2());// 受理編號-2
    		evtForm.setApNo3(benForm.getTempApNo3());// 受理編號-3
    		evtForm.setApNo4(benForm.getTempApNo4());// 受理編號-4
    		evtForm.setApUbno(benForm.getTempApUbno());// 申請單位保險證號
    		evtForm.setAppDate(benForm.getTempAppDate());// 申請日期
    		evtForm.setEvtNationTpe(benForm.getTempEvtNationTpe());// 國籍別
    		evtForm.setEvtDieDate(benForm.getTempEvtDieDate());// 死亡日期
    		evtForm.setEvtSex(benForm.getTempEvtSex());// 性別
    		evtForm.setEvtNationCode(benForm.getTempEvtNationCode());// 國籍
    		evtForm.setEvtNationCodeOption(benForm.getTempEvtNationCodeOption());// 事故者國籍下拉選單
    		evtForm.setEvtName(benForm.getTempEvtName());// 事故者姓名
    		evtForm.setEvtIdnNo(benForm.getTempEvtIdnNo());// 事故者身分證號
    		evtForm.setEvtBrDate(benForm.getTempEvtBrDate());// 事故者出生日期
    		evtForm.setEvAppTyp(benForm.getTempEvAppTyp());// 申請傷病分類
    		evtForm.setEvTyp(benForm.getTempEvTyp());// 核定傷病分類
    		evtForm.setApItem(benForm.getTempApItem());// 申請項目
    	}
    	
    	return evtForm;
    }
    
    public SurvivorAnnuityWalkInReceiptForm convertSurvivorTurnInData(SurvivorAnnuityWalkInReceiptForm form,
			Baap0d060 baap0d060) {
    	BeanUtility.copyProperties(form, baap0d060);
    	
    	String procType = form.getProcType();
    	// 日期轉換
		// 申請日期
		if (StringUtils.isNotBlank(baap0d060.getAppDate())) {
			form.setAppDate(DateUtility.changeDateType(baap0d060.getAppDate()));
		}
		// 死亡日期
		if (StringUtils.isNotBlank(baap0d060.getEvtDieDate())) {
			form.setEvtDieDate(DateUtility.changeDateType(baap0d060.getEvtDieDate()));
		}
		// 事故者出生日期
		if (StringUtils.isNotBlank(baap0d060.getEvtBrDate())) {
			form.setEvtBrDate(DateUtility.changeDateType(baap0d060.getEvtBrDate()));
		}
		// 判決日期
		if (StringUtils.isNotBlank(baap0d060.getJudgeDate())) {
			form.setJudgeDate(DateUtility.changeDateType(baap0d060.getJudgeDate()));
		}
		
		// BC、BE 依身分證號判斷本國籍或外籍
		if (StringUtils.contains("2,3", procType) && baap0d060.getEvtIdnNo().matches("[a-zA-Z][1-2]\\d{8}")) {
			form.setEvtNationTpe("1");
		} else {
			form.setEvtNationTpe("2");
		}
		

		// 當申請傷病分類為 1、3 時，核定傷病分類為 3
		// 當申請傷病分類為 2、4 時，核定傷病分類為 4
		if (StringUtils.isNotBlank(baap0d060.getEvAppTyp())) {
			String evAppTyp = baap0d060.getEvAppTyp();
			if (StringUtils.contains("1,3", evAppTyp)) {
				form.setEvTyp("3");
			} else if (StringUtils.contains("2,4", evAppTyp)) {
				form.setEvTyp("4");
			}
		}
		
		// 申請項目
		// BC、BE 申請項目為 2 時寫入 5，其餘寫入 4
		if (StringUtils.contains("2,3", procType)) {
			String apItem = baap0d060.getApItem();
			if (StringUtils.equals(apItem, "2")) {
				form.setApItem("5");
			} else {
				form.setApItem("4");
			}
		}

		return form;
    }
    
    /**
	 * 取得遺屬年金轉入受理作業所需要的個人資料
	 * 
	 * @param apno
	 * @param procType
	 * @return
	 */
	public List<Baap0d060> getSurvivorTurnInData(SurvivorAnnuityWalkInReceiptForm form) {
		String procType = form.getProcType();

		List<Baap0d060> list = null;
		if (StringUtils.equals(procType, "2")) {
			list = baap0d060Dao.selectSurvivorTurnInDataFromBe(form.getApNoStr());
		} else if (StringUtils.equals(procType, "3")) {
			list = baap0d060Dao.selectSurvivorTurnInDataFromBc(form.getApNoStr());
		} else if (StringUtils.equals(procType, "4")) {
			list = baap0d060Dao.selectSurvivorTurnInDataFromBb(form.getApNoStrForBb());
		}
		return list;
	}
	
	/**
	 * 取得遺屬年金轉入受理作業所需要的遺屬資料
	 * 
	 * @param form
	 * @return
	 */
	public List<SurvivorAnnuityReceiptBenCase> getSurvivorTurnInBen(SurvivorAnnuityWalkInReceiptForm form,
			UserBean userData, BigDecimal bafamilytempId) {
		String procType = form.getProcType();
		
		List<SurvivorAnnuityReceiptBenCase> benList = null;
		if (StringUtils.equals(procType, "2")) {
			benList = baap0d060Dao.selectSurvivorTurnInBenFromBe(form.getApNoStr());
		} else if (StringUtils.equals(procType, "3")) {
			benList = baap0d060Dao.selectSurvivorTurnInBenFromBc(form.getApNoStr());
		} else if (StringUtils.equals(procType, "4")) {
			benList = baap0d060Dao.selectSurvivorTurnInBenFromBb(form.getApNoStrForBb());
		}
		
		List<SurvivorAnnuityReceiptBenCase> benDataList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(benList)) {
			for (SurvivorAnnuityReceiptBenCase caseObj : benList) {
				caseObj.setBafamilytempId(bafamilytempId);
				// BC、BE 遺屬申請日期取自個人資料的申請日期，國籍別、國籍同事故者
				if (StringUtils.contains("2,3", procType)) {
					caseObj.setBenAppDate(form.getAppDate());
					caseObj.setBenNationTyp(form.getEvtNationTpe());
					caseObj.setBenNationCode(form.getEvtNationCode());
				}
				caseObj.setChkBranchId(caseObj.getBranchId());
				caseObj.setChkPayBankId(caseObj.getPayBankId());
				caseObj.setChkPayEeacc(caseObj.getPayEeacc());
				caseObj = transSurvivorBenInputData(caseObj, "insertMode");
				String seqNo = insertSurvivorBafamilytempData(caseObj, userData);
				caseObj.setSeqNo(seqNo);
				benDataList.add(caseObj);
			}
		}
		return benDataList;
	}

    /**
     * 保存畫面上已輸入之事故者資料 for 失能受理
     * 
     * @param evtForm
     * @param famForm
     * @return
     */
    public SurvivorAnnuityReceiptForm keepInputEvtFormDataForSurvivor(SurvivorAnnuityReceiptForm evtForm, SurvivorAnnuityReceiptForm benForm) {
        if (evtForm != null && benForm != null) {
            evtForm.setApNo1(benForm.getTempApNo1());// 受理編號-1
            evtForm.setApNo2(benForm.getTempApNo2());// 受理編號-2
            evtForm.setApNo3(benForm.getTempApNo3());// 受理編號-3
            evtForm.setApNo4(benForm.getTempApNo4());// 受理編號-4
            evtForm.setApUbno(benForm.getTempApUbno());// 申請單位保險證號
            evtForm.setAppDate(benForm.getTempAppDate());// 申請日期
            evtForm.setEvtNationTpe(benForm.getTempEvtNationTpe());// 國籍別
            evtForm.setEvtDieDate(benForm.getTempEvtDieDate());// 死亡日期
            evtForm.setEvtSex(benForm.getTempEvtSex());// 性別
            evtForm.setEvtNationCode(benForm.getTempEvtNationCode());// 國籍
            evtForm.setEvtNationCodeOption(benForm.getTempEvtNationCodeOption());// 事故者國籍下拉選單
            evtForm.setEvtName(benForm.getTempEvtName());// 事故者姓名
            evtForm.setEvtIdnNo(benForm.getTempEvtIdnNo());// 事故者身分證號
            evtForm.setEvtBrDate(benForm.getTempEvtBrDate());// 事故者出生日期
            evtForm.setEvTyp(benForm.getTempEvTyp());// 傷病分類
            evtForm.setApItem(benForm.getTempApItem());// 申請項目
        }

        return evtForm;
    }

    // ------------------------------ 共用function ------------------------------

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setBacountryDao(BacountryDao bacountryDao) {
        this.bacountryDao = bacountryDao;
    }

    public void setCvldtlDao(CvldtlDao cvldtlDao) {
        this.cvldtlDao = cvldtlDao;
    }

    public void setNpbanklistDao(NpbanklistDao npbanklistDao) {
        this.npbanklistDao = npbanklistDao;
    }

    public void setBcbpfDao(BcbpfDao bcbpfDao) {
        this.bcbpfDao = bcbpfDao;
    }

    public void setNppostlistDao(NppostlistDao nppostlistDao) {
        this.nppostlistDao = nppostlistDao;
    }

    public void setBaapplogDao(BaapplogDao baapplogDao) {
        this.baapplogDao = baapplogDao;
    }

    // public void setBapaallocateDao(BapaallocateDao bapaallocateDao) {
    // this.bapaallocateDao = bapaallocateDao;
    // }

    public void setBbcmf09Dao(Bbcmf09Dao bbcmf09Dao) {
        this.bbcmf09Dao = bbcmf09Dao;
    }

    public void setBaparamDao(BaparamDao baparamDao) {
        this.baparamDao = baparamDao;
    }

    public void setBafamilytempDao(BafamilytempDao bafamilytempDao) {
        this.bafamilytempDao = bafamilytempDao;
    }

    public void setBaappexpandDao(BaappexpandDao baappexpandDao) {
        this.baappexpandDao = baappexpandDao;
    }

    public void setBafamilyDao(BafamilyDao bafamilyDao) {
        this.bafamilyDao = bafamilyDao;
    }

    public void setBaap0d060Dao(Baap0d060Dao baap0d060Dao) {
		this.baap0d060Dao = baap0d060Dao;
	}

	public void setNbappbaseDao(NbappbaseDao nbappbaseDao) {
        this.nbappbaseDao = nbappbaseDao;
    }

    public void setNbexcepDao(NbexcepDao nbexcepDao) {
        this.nbexcepDao = nbexcepDao;
    }

}
