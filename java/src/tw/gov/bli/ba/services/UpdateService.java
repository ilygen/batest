package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.BaacpdtlDao;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BaappexpandDao;
import tw.gov.bli.ba.dao.BaapplogDao;
import tw.gov.bli.ba.dao.BachkcontrlDao;
import tw.gov.bli.ba.dao.BachkfileDao;
import tw.gov.bli.ba.dao.BacolumnrecDao;
import tw.gov.bli.ba.dao.BacompelnopayDao;
import tw.gov.bli.ba.dao.BacriinjDao;
import tw.gov.bli.ba.dao.BadaprDao;
import tw.gov.bli.ba.dao.BadupeidnDao;
import tw.gov.bli.ba.dao.BafailurelistDao;
import tw.gov.bli.ba.dao.BafamilyDao;
import tw.gov.bli.ba.dao.Balp0d020Dao;
import tw.gov.bli.ba.dao.BamarginamtnotifyDao;
import tw.gov.bli.ba.dao.BaoncepayDao;
import tw.gov.bli.ba.dao.BaparamDao;
import tw.gov.bli.ba.dao.BapayrptaccountDao;
import tw.gov.bli.ba.dao.BapayrptrecordDao;
import tw.gov.bli.ba.dao.BapayrptsumDao;
import tw.gov.bli.ba.dao.BapflbacDao;
import tw.gov.bli.ba.dao.BarecheckDao;
import tw.gov.bli.ba.dao.BaregivedtlDao;
import tw.gov.bli.ba.dao.BasenimaintDao;
import tw.gov.bli.ba.dao.BastudtermDao;
import tw.gov.bli.ba.dao.BahandicaptermDao;
import tw.gov.bli.ba.dao.BaunacpdtlDao;
import tw.gov.bli.ba.dao.BaunqualifiednoticeDao;
import tw.gov.bli.ba.dao.Bbcmf06Dao;
import tw.gov.bli.ba.dao.Bbcmf07Dao;
import tw.gov.bli.ba.dao.Bbcmf08Dao;
import tw.gov.bli.ba.dao.Bbcmf29Dao;
import tw.gov.bli.ba.dao.BcbpfDao;
import tw.gov.bli.ba.dao.CaubDao;
import tw.gov.bli.ba.dao.CipbDao;
import tw.gov.bli.ba.dao.CvldtlDao;
import tw.gov.bli.ba.dao.LocalPfpcckyDao;
import tw.gov.bli.ba.dao.LocalPfpcckyuserrecDao;
import tw.gov.bli.ba.dao.MaadmrecDao;
import tw.gov.bli.ba.dao.NbappbaseDao;
import tw.gov.bli.ba.dao.NpbanklistDao;
import tw.gov.bli.ba.dao.NppostlistDao;
import tw.gov.bli.ba.dao.PfpfdDao;
import tw.gov.bli.ba.dao.PfpfmDao;
import tw.gov.bli.ba.domain.Baacpdtl;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.domain.Bachkcontrl;
import tw.gov.bli.ba.domain.Bachkfile;
import tw.gov.bli.ba.domain.Bacolumnrec;
import tw.gov.bli.ba.domain.Bacompelnopay;
import tw.gov.bli.ba.domain.Bacriinj;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.domain.Badupeidn;
import tw.gov.bli.ba.domain.Bafailurelist;
import tw.gov.bli.ba.domain.Bafamily;
import tw.gov.bli.ba.domain.Bahandicapterm;
import tw.gov.bli.ba.domain.Bamarginamtnotify;
import tw.gov.bli.ba.domain.Bapayrptaccount;
import tw.gov.bli.ba.domain.Bapayrptrecord;
import tw.gov.bli.ba.domain.Bapayrptsum;
import tw.gov.bli.ba.domain.Bapflbac;
import tw.gov.bli.ba.domain.Barecheck;
import tw.gov.bli.ba.domain.Baregivedtl;
import tw.gov.bli.ba.domain.Basenimaint;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.ba.domain.Baunacpdtl;
import tw.gov.bli.ba.domain.Baunqualifiednotice;
import tw.gov.bli.ba.domain.Bbcmf06;
import tw.gov.bli.ba.domain.Bbcmf07;
import tw.gov.bli.ba.domain.Bcbpf;
import tw.gov.bli.ba.domain.Caub;
import tw.gov.bli.ba.domain.Cvldtl;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.domain.Pfpccky;
import tw.gov.bli.ba.domain.Pfpfd;
import tw.gov.bli.ba.domain.Pfpfm;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.payment.cases.PaymentProcessCase;
import tw.gov.bli.ba.query.cases.PaymentQueryChkFileDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.rpt.cases.BatchPaymentReceiveDataCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt31Case;
import tw.gov.bli.ba.rpt.forms.BatchPaymentReceiveForm;
import tw.gov.bli.ba.update.cases.AccountsReceivableDataCase;
import tw.gov.bli.ba.update.cases.ApplicationUpdateDupeIdnNoCase;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.BadaprDataCase;
import tw.gov.bli.ba.update.cases.BasenimaintCase;
import tw.gov.bli.ba.update.cases.CashReceiveDataCase;
import tw.gov.bli.ba.update.cases.CheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.cases.ChkFileCase;
import tw.gov.bli.ba.update.cases.ChkFileCollectionCase;
import tw.gov.bli.ba.update.cases.CloseStatusAlterationCase;
import tw.gov.bli.ba.update.cases.CloseStatusAlterationCasePayeeList;
import tw.gov.bli.ba.update.cases.DependantDataUpdateCase;
import tw.gov.bli.ba.update.cases.DependantDataUpdateCompelDataCase;
import tw.gov.bli.ba.update.cases.DependantEvtDataUpdateCase;
import tw.gov.bli.ba.update.cases.DisabledAccountsReceivableDataCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateBareCheckCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCheckFileCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateDupeIdnCase;
import tw.gov.bli.ba.update.cases.DisabledCashReceiveDataCase;
import tw.gov.bli.ba.update.cases.DisabledCheckMarkLevelAdjustBenDataCase;
import tw.gov.bli.ba.update.cases.DisabledCheckMarkLevelAdjustBenDetailDataCase;
import tw.gov.bli.ba.update.cases.DisabledCheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.cases.DisabledCheckMarkLevelAdjustFamDataCase;
import tw.gov.bli.ba.update.cases.DisabledCheckMarkLevelAdjustFamDetailDataCase;
import tw.gov.bli.ba.update.cases.DisabledPaymentReceiveDataCase;
import tw.gov.bli.ba.update.cases.DisabledRemittanceReceiveDataCase;
import tw.gov.bli.ba.update.cases.InheritorRegisterCase;
import tw.gov.bli.ba.update.cases.OldAgePaymentReceiveDataCase;
import tw.gov.bli.ba.update.cases.RemittanceReceiveDataCase;
import tw.gov.bli.ba.update.cases.StopPaymentProcessCase;
import tw.gov.bli.ba.update.cases.StopPaymentProcessDetailCase;
import tw.gov.bli.ba.update.cases.SurvivorAccountsReceivableDataCase;
import tw.gov.bli.ba.update.cases.SurvivorApplicationDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorApplicationDataUpdateCheckFileCase;
import tw.gov.bli.ba.update.cases.SurvivorApplicationDataUpdateDupeIdnCase;
import tw.gov.bli.ba.update.cases.SurvivorCashReceiveDataCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustBenDataCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustBenDetailDataCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCompelDataCase;
import tw.gov.bli.ba.update.cases.SurvivorPaymentReceiveDataCase;
import tw.gov.bli.ba.update.cases.SurvivorRemittanceReceiveDataCase;
import tw.gov.bli.ba.update.forms.ApplicationDataUpdateForm;
import tw.gov.bli.ba.update.forms.DisabledApplicationDataUpdateForm;
import tw.gov.bli.ba.update.forms.OldAgePaymentReceiveForm;
import tw.gov.bli.ba.update.forms.PayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.PayeeDataUpdateListForm;
import tw.gov.bli.ba.update.forms.PaymentDataUpdateForm;
import tw.gov.bli.ba.update.forms.SurvivorApplicationDataUpdateForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateDetailForm;
import tw.gov.bli.ba.util.BaBusinessUtility;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.util.DateUtil;
import tw.gov.bli.common.util.FrameworkLogUtil;
import tw.gov.bli.pf.model.service.Pfxx0w040Service;
import tw.gov.bli.pf.model.vo.PfpcckyVO;
import tw.gov.bli.pf.model.vo.PfpcckyuserrecVO;

/**
 * Service for 更正作業
 * 
 * @author Rickychi
 */
public class UpdateService {
    private static Log log = LogFactory.getLog(UpdateService.class);

    private BaappbaseDao baappbaseDao;
    private CvldtlDao cvldtlDao;
    private NpbanklistDao npbanklistDao;
    private BcbpfDao bcbpfDao;
    private NppostlistDao nppostlistDao;
    private BaapplogDao baapplogDao;
    private BaparamDao baparamDao;
    private BadaprDao badaprDao;
    private BasenimaintDao basenimaintDao;
    private BachkfileDao bachkfileDao;
    private BaoncepayDao baoncepayDao;
    private BadupeidnDao badupeidnDao;
    private CaubDao caubDao;
    private Bbcmf08Dao bbcmf08Dao;
    private BafamilyDao bafamilyDao;
    private BachkcontrlDao bachkcontrlDao;
    private BaappexpandDao baappexpandDao;
    private Bbcmf07Dao bbcmf07Dao;
    private BaregivedtlDao baregivedtlDao;
    private BastudtermDao bastudtermDao;
    private BahandicaptermDao bahandicaptermDao;
    private Bbcmf06Dao bbcmf06Dao;
    private BacriinjDao bacriinjDao;
    private BacolumnrecDao bacolumnrecDao;
    private BacompelnopayDao bacompelnopayDao;
    private Balp0d020Dao balp0d020Dao;
    private CipbDao cipbDao;
    private BapflbacDao bapflbacDao;
    private Bbcmf29Dao bbcmf29Dao;
    private BaunacpdtlDao baunacpdtlDao;
    private LocalPfpcckyDao localPfpcckyDao;
    private BaacpdtlDao baacpdtlDao;
    private NbappbaseDao nbappbaseDao;
    private BarecheckDao barecheckDao;
    private BapayrptrecordDao bapayrptrecordDao;
    private BapayrptsumDao bapayrptsumDao;
    private BapayrptaccountDao bapayrptaccountDao;
    private LocalPfpcckyuserrecDao localpfpcckyuserrecDao;
    private BafailurelistDao bafailurelistDao;
    private PfpfmDao pfpfmDao;
    private PfpfdDao pfpfdDao;
    private BaunqualifiednoticeDao baunqualifiednoticeDao;
    private BamarginamtnotifyDao bamarginamtnotifyDao;
    private MaadmrecDao maadmrecDao;

    private Pfxx0w040Service pfxx0w040Service;

    // 更正作業 - 通訊資料更正
    private static final String UPDATE_TYPE_COMMUNICATION = "communication";
    // 更正作業 - 給付資料更正
    private static final String UPDATE_TYPE_PAYMENT = "payment";
    // 更正作業 - 案件資料更正
    private static final String UPDATE_TYPE_APPLICATION = "application";
    // 更正作業 - 案件資料更正 - 欠費期間維護 for oldSeniab
    private static final String UPDATE_OLDSENIAB_OWES = "owes";
    // 更正作業 - 案件資料更正 - 一次給付資料更正 for oldSeniab
    private static final String UPDATE_OLDSENIAB_ONCEAMT = "onceAmt";

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
    public List<BaappbaseDataUpdateCase> selectBaappbaseDataList(String evtIdnNo, String apNo, String seqNo, String[] procStat, String eqTyp) {
        List<Baappbase> list = baappbaseDao.selectDataBy(evtIdnNo, apNo, seqNo, procStat, eqTyp, null);
        List<BaappbaseDataUpdateCase> returnList = new ArrayList<BaappbaseDataUpdateCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            BaappbaseDataUpdateCase caseObj = new BaappbaseDataUpdateCase();
            BeanUtility.copyProperties(caseObj, baappbase);

            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔 資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @param benIds 受益人社福識別碼
     * @param eqType SQL EqualType
     * @return
     */
    public BaappbaseDataUpdateCase getBaappbaseDetail(BigDecimal baappbaseId, String[] procStat, String benIds, String eqType) {
        Baappbase baappbase = baappbaseDao.selectDetailDataBy(baappbaseId, procStat, benIds, eqType);
        BaappbaseDataUpdateCase caseObj = null;
        if (baappbase != null) {
            caseObj = new BaappbaseDataUpdateCase();
            BeanUtility.copyProperties(caseObj, baappbase);

            // 取得 戶籍姓名資料
            List<Cvldtl> list = cvldtlDao.selectDataBy(baappbase.getBenIdnNo(), baappbase.getBenBrDate());
            String cvldtlName = "";
            // 為避免回傳資料超過一筆以上, 故查詢結果不等於一筆皆回傳空字串

            if (list.size() == 1) {
                Cvldtl obj = list.get(0);
                cvldtlName = obj.getName();
                caseObj.setCvlDtlCommAddr(obj.getHaddr());
                caseObj.setCvlDtlCommZip(obj.getHzip());
            }
            caseObj.setCvldtlName(cvldtlName);

            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            // 已領失能年金受理編號1~5
            if (StringUtils.isNotBlank(caseObj.getDabApNo()) && caseObj.getDabApNo().length() == ConstantKey.APNO_LENGTH) {
                String dabApNo = caseObj.getDabApNo();
                caseObj.setDabApNo1(dabApNo.substring(0, 1));
                caseObj.setDabApNo2(dabApNo.substring(1, 2));
                caseObj.setDabApNo3(dabApNo.substring(2, 7));
                caseObj.setDabApNo4(dabApNo.substring(7, 12));
            }
            // 根據 給付方式 填入帳號資料
            // 給付方式=1、2、7、8，帳號資料欄位的輸入方式分為兩格，第1格長度為7碼(payBankId + branchId)
            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(caseObj.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(caseObj.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_7).equals(caseObj.getPayTyp())
                            || (ConstantKey.BAAPPBASE_PAYTYP_8).equals(caseObj.getPayTyp())) {
                String payBankIdBranchId = "";
                if (StringUtils.isNotBlank(caseObj.getPayBankId()))
                    payBankIdBranchId += caseObj.getPayBankId();
                if (StringUtils.isNotBlank(caseObj.getBranchId()))
                    payBankIdBranchId += caseObj.getBranchId();
                caseObj.setPayBankIdBranchId(payBankIdBranchId);
            }
            // 給付方式=5、6，帳號資料欄位的輸入方式為一格, 帳號(前)與 帳號(後)合併在同一個輸入格中顯示(payBankId + branchId + payEeacc)
            if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(caseObj.getPayTyp()) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(caseObj.getPayTyp())) {
                String payAccount = "";
                if (StringUtils.isNotBlank(caseObj.getPayBankId()))
                    payAccount += caseObj.getPayBankId();
                if (StringUtils.isNotBlank(caseObj.getBranchId()))
                    payAccount += caseObj.getBranchId();
                if (StringUtils.isNotBlank(caseObj.getPayEeacc()))
                    payAccount += caseObj.getPayEeacc();
                caseObj.setPayAccount(payAccount);
            }
        }
        return caseObj;
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 死亡改匯繼承人維護作業
     * 
     * @param apNo 受理編號
     * @return caseObj
     */
    public BaappbaseDataUpdateCase selectDataForPayeeDataUpdateDetail(String apNo) {
        Baappbase baappbase = baappbaseDao.selectDataForPayeeDataUpdateDetail(apNo);
        BaappbaseDataUpdateCase caseObj = null;
        if (baappbase != null) {
            caseObj = new BaappbaseDataUpdateCase();
            BeanUtility.copyProperties(caseObj, baappbase);
        }
        return caseObj;
    }

    /**
     * 更新通訊資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForCommunication(UserBean userData, BaappbaseDataUpdateCase baappbaseData) {
        Baappbase beforeBaappbase = new Baappbase();// 改前值物件

        Baappbase afterBaappbase = new Baappbase();// 改後值物件

        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, baappbaseData);
        baappbase.setUpdUser(userData.getEmpNo());// 異動者代號

        baappbase.setUpdTime(DateUtility.getNowWestDateTime(true));// 異動日期時間
        baappbase.setChgMk("Y");

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 取得改前值物件

            beforeBaappbase = baappbaseDao.selectDetailDataBy(baappbase.getBaappbaseId(), null, null, null);
            // 改後值物件

            afterBaappbase = (Baappbase) BeanUtility.cloneBean(beforeBaappbase);
            afterBaappbase.setTel1(baappbase.getTel1());
            afterBaappbase.setTel2(baappbase.getTel2());
            afterBaappbase.setCommTyp(baappbase.getCommTyp());
            afterBaappbase.setCommZip(baappbase.getCommZip());
            afterBaappbase.setCommAddr(baappbase.getCommAddr());
            afterBaappbase.setChgMk(baappbase.getChgMk());
            afterBaappbase.setUpdUser(baappbase.getUpdUser());// 異動者代號

            afterBaappbase.setUpdTime(baappbase.getUpdTime());// 異動日期時間
        }

        // 更新通訊資料到 給付主檔
        baappbaseDao.updateDataForCommunication(baappbase);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforeBaappbase, afterBaappbase);
        }
        log.debug("Update BAAPPBASE Finished ...");
        // ]

        // 新增資料到 更正記錄檔

        // [
        log.debug("Start Insert BAAPPLOG ...");
        BigDecimal baappbaseId = afterBaappbase.getBaappbaseId();
        List<Baapplog> logList = baappbaseData.getBaapplogList();
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
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForPayment(UserBean userData, BaappbaseDataUpdateCase caseObj, boolean isUpdIntValMon) {
        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");

        // update BALP0D020 PROCMK
        updateProcMkByApNo(caseObj.getApNo());

        // 相關要修改的主檔清單
        List<Baappbase> extModifyList = new ArrayList<Baappbase>();
        // 給付資料更正改前物件List
        List<Baappbase> beforeList = new ArrayList<Baappbase>();
        // 給付資料更正改後物件List
        List<Baappbase> afterList = new ArrayList<Baappbase>();
        // 改前改後值List for BAAPPLOG
        List<BaappbaseDataUpdateCase> baapplogList = new ArrayList<BaappbaseDataUpdateCase>();

        caseObj.setUpdUser(userData.getEmpNo());// 異動者代號

        caseObj.setUpdTime(DateUtility.getNowWestDateTime(true));// 異動日期時間
        caseObj.setChgMk("Y");// 更正註記
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, caseObj);
        afterList.add(baappbase);

        // updateList for AccRel=1
        List<Baappbase> baappbaseList = baappbaseDao.selectDataBy(null, caseObj.getApNo(), null, null, null, null);
        List<BigDecimal> idList = baappbaseDao.selectBaappbaseIdByAccSeqNo(caseObj.getApNo(), caseObj.getAccSeqNo());

        HashMap idMap = new HashMap();
        for (int i = 0; i < idList.size(); i++) {
            BigDecimal baappbaseId = idList.get(i);
            idMap.put(baappbaseId, baappbaseId);
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 取得給付資料修改主檔改前值物件

            Baappbase beforeBaappbase = new Baappbase();
            // 給付資料修改主檔改後值物件

            BeanUtility.copyProperties(beforeBaappbase, getBaappbaseDetail(caseObj.getBaappbaseId(), null, null, null));
            beforeList.add(beforeBaappbase);

            // 給付資料修改 相關主檔改前值物件

            if (("1").equals(caseObj.getAccRel())) {
                for (int i = 0; i < idList.size(); i++) {
                    BigDecimal baappbaseId = idList.get(i);
                    BaappbaseDataUpdateCase beforeModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                    Baappbase before = new Baappbase();// 改後值物件

                    BeanUtility.copyProperties(before, beforeModifyObj);
                    beforeList.add(before);
                }

                if (isUpdIntValMon) {
                    for (int i = 0; i < baappbaseList.size(); i++) {
                        BigDecimal baappbaseId = baappbaseList.get(i).getBaappbaseId();
                        if (idMap.get(baappbaseId) == null && !StringUtils.equals(baappbaseId.toString(), caseObj.getBaappbaseId().toString())) {
                            BaappbaseDataUpdateCase beforeModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                            Baappbase before = new Baappbase();// 改後值物件

                            BeanUtility.copyProperties(before, beforeModifyObj);
                            beforeList.add(before);
                        }
                    }
                }
            }
            else if (isUpdIntValMon) {
                for (int i = 0; i < baappbaseList.size(); i++) {
                    BigDecimal baappbaseId = baappbaseList.get(i).getBaappbaseId();
                    if (!StringUtils.equals(baappbaseId.toString(), caseObj.getBaappbaseId().toString())) {
                        BaappbaseDataUpdateCase beforeModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                        Baappbase before = new Baappbase();// 改後值物件

                        BeanUtility.copyProperties(before, beforeModifyObj);
                        beforeList.add(before);
                    }
                }
            }
        }

        // 將 給付資料修改主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
        baapplogList.add(caseObj);

        // 判斷需要UPDATE的相關資料

        if (("1").equals(caseObj.getAccRel())) {
            for (int i = 0; i < idList.size(); i++) {
                BigDecimal baappbaseId = idList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PaymentDataUpdateForm objForm = new PaymentDataUpdateForm();
                BeanUtility.copyPropertiesForUpdate(objForm, extModifyObj, ConstantKey.OLD_FIELD_PREFIX);
                // 相關主檔需修改的欄位

                // [
                extModifyObj.setPayTyp(caseObj.getPayTyp());
                extModifyObj.setBankName(caseObj.getBankName());
                extModifyObj.setPayBankId(caseObj.getPayBankId());
                extModifyObj.setBranchId(caseObj.getBranchId());
                extModifyObj.setPayEeacc(caseObj.getPayEeacc());
                extModifyObj.setAccIdn(caseObj.getAccIdn());
                extModifyObj.setAccName(caseObj.getAccName());
                extModifyObj.setMitRate(caseObj.getMitRate());
                extModifyObj.setUpdUser(caseObj.getUpdUser());
                extModifyObj.setUpdTime(caseObj.getUpdTime());
                // ]

                // 相關主檔 改前改後值 for BAAPPLOG
                // [
                objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
                objForm.setPayEeacc(extModifyObj.getPayEeacc());
                objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
                // ]

                if (isUpdIntValMon) {
                    extModifyObj.setInterValMonth(ConstantKey.BAAPPBASE_INTERVALMONTH_BY_MONTH);
                    objForm.setInterValMonth(ConstantKey.BAAPPBASE_INTERVALMONTH_BY_MONTH);
                }

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYMENT_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                baapplogList.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);
                // }
            }

            // 這裡的資料只update interValMonth
            if (isUpdIntValMon) {
                for (int i = 0; i < baappbaseList.size(); i++) {
                    BigDecimal baappbaseId = baappbaseList.get(i).getBaappbaseId();
                    if (idMap.get(baappbaseId) == null && !StringUtils.equals(baappbaseId.toString(), caseObj.getBaappbaseId().toString())) {
                        BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                        Baappbase beforeObj = new Baappbase();// 改前值物件

                        Baappbase afterObj = new Baappbase();// 改後值物件

                        // Insert MMAPLOG
                        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                            BeanUtility.copyProperties(beforeObj, extModifyObj);
                            beforeList.add(beforeObj);
                        }

                        // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                        PaymentDataUpdateForm objForm = new PaymentDataUpdateForm();
                        BeanUtility.copyPropertiesForUpdate(objForm, extModifyObj, ConstantKey.OLD_FIELD_PREFIX);
                        // 相關主檔需修改的欄位

                        // [
                        extModifyObj.setInterValMonth(ConstantKey.BAAPPBASE_INTERVALMONTH_BY_MONTH);
                        // ]

                        // 相關主檔 改前改後值 for BAAPPLOG
                        // [
                        objForm.setInterValMonth(ConstantKey.BAAPPBASE_INTERVALMONTH_BY_MONTH);
                        // ]
                        extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYMENT_FIELD_RESOURCE_PREFIX));

                        // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                        baapplogList.add(extModifyObj);
                        BeanUtility.copyProperties(afterObj, extModifyObj);
                        afterList.add(afterObj);
                        // }
                    }
                }
            }
        }
        else if (isUpdIntValMon) {
            for (int i = 0; i < baappbaseList.size(); i++) {
                BigDecimal baappbaseId = baappbaseList.get(i).getBaappbaseId();
                if (!StringUtils.equals(baappbaseId.toString(), caseObj.getBaappbaseId().toString())) {
                    BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                    Baappbase beforeObj = new Baappbase();// 改前值物件

                    Baappbase afterObj = new Baappbase();// 改後值物件

                    // Insert MMAPLOG
                    if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                        BeanUtility.copyProperties(beforeObj, extModifyObj);
                        beforeList.add(beforeObj);
                    }

                    // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                    PaymentDataUpdateForm objForm = new PaymentDataUpdateForm();
                    BeanUtility.copyPropertiesForUpdate(objForm, extModifyObj, ConstantKey.OLD_FIELD_PREFIX);
                    // 相關主檔需修改的欄位

                    // [
                    extModifyObj.setInterValMonth(ConstantKey.BAAPPBASE_INTERVALMONTH_BY_MONTH);
                    // ]

                    // 相關主檔 改前改後值 for BAAPPLOG
                    // [
                    objForm.setInterValMonth(ConstantKey.BAAPPBASE_INTERVALMONTH_BY_MONTH);
                    // ]
                    extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYMENT_FIELD_RESOURCE_PREFIX));

                    // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                    baapplogList.add(extModifyObj);
                    BeanUtility.copyProperties(afterObj, extModifyObj);
                    afterList.add(afterObj);
                    // }
                }
            }
        }

        // 更新給付資料到 給付主檔
        for (int i = 0; i < afterList.size(); i++) {
            Baappbase obj = (Baappbase) afterList.get(i);
            baappbaseDao.updateDataForPayment(obj);
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            afterList = new ArrayList<Baappbase>();
            // 給付資料修改主檔改後值物件

            Baappbase afterBaappbase = new Baappbase();
            BeanUtility.copyProperties(afterBaappbase, getBaappbaseDetail(caseObj.getBaappbaseId(), null, null, null));
            afterList.add(afterBaappbase);

            // 給付資料修改 相關主檔改後值物件

            if (("1").equals(caseObj.getAccRel())) {
                for (int i = 0; i < idList.size(); i++) {
                    BigDecimal baappbaseId = idList.get(i);
                    BaappbaseDataUpdateCase modifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                    Baappbase afterObj = new Baappbase();// 改後值物件

                    BeanUtility.copyProperties(afterObj, modifyObj);
                    afterList.add(afterObj);
                }

                if (isUpdIntValMon) {
                    for (int i = 0; i < baappbaseList.size(); i++) {
                        BigDecimal baappbaseId = baappbaseList.get(i).getBaappbaseId();
                        if (idMap.get(baappbaseId) == null && !StringUtils.equals(baappbaseId.toString(), caseObj.getBaappbaseId().toString())) {
                            BaappbaseDataUpdateCase modifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                            Baappbase afterObj = new Baappbase();// 改後值物件

                            BeanUtility.copyProperties(afterObj, modifyObj);
                            afterList.add(afterObj);
                        }
                    }
                }
            }
            else if (isUpdIntValMon) {
                for (int i = 0; i < baappbaseList.size(); i++) {
                    BigDecimal baappbaseId = baappbaseList.get(i).getBaappbaseId();
                    if (!StringUtils.equals(baappbaseId.toString(), caseObj.getBaappbaseId().toString())) {
                        BaappbaseDataUpdateCase modifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                        Baappbase afterObj = new Baappbase();// 改後值物件

                        BeanUtility.copyProperties(afterObj, modifyObj);
                        afterList.add(afterObj);
                    }
                }
            }

            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        }
        log.debug("Update BAAPPBASE Finished ...");
        // ]

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int i = 0; i < baapplogList.size(); i++) {
            BaappbaseDataUpdateCase logObj = baapplogList.get(i);
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
     * 更新案件資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForApplication(UserBean userData, BaappbaseDataUpdateCase caseObj) {
        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");

        // update BALP0D020 PROCMK
        updateProcMkByApNo(caseObj.getApNo());

        // 相關要修改的主檔清單
        List<Baappbase> extModifyList = new ArrayList<Baappbase>();
        // 案件資料更正改前物件List
        List<Object> beforeList = new ArrayList<Object>();
        // 案件資料更正改後物件List
        List<Object> afterList = new ArrayList<Object>();
        // 改前改後值List for BAAPPLOG
        List<BaappbaseDataUpdateCase> baapplogList = new ArrayList<BaappbaseDataUpdateCase>();

        // 填入case相關內容
        caseObj.setEvtAppName(caseObj.getEvtName());
        caseObj.setEvtAppIdnNo(caseObj.getEvtIdnNo());
        caseObj.setEvtAppBrDate(caseObj.getEvtBrDate());
        if (("N").equals(caseObj.getEvtNameStatus())) {
            caseObj.setEvtName("");
            caseObj.setEvtAppName("");
        }
        caseObj.setUpdUser(userData.getEmpNo());// 異動者代號

        caseObj.setUpdTime(DateUtility.getNowWestDateTime(true));// 異動日期時間
        caseObj.setChgMk("Y");// 更正註記
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, caseObj);
        baappbase.setSeqNo("0000");
        afterList.add(baappbase);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 取得案件資料修改主檔改前值物件

            // Baappbase beforeBaappbase = new Baappbase();
            // beforeBaappbase = baappbaseDao.selectDetailDataBy(caseObj.getBaappbaseId(), null, null, null);
            // beforeList.add(beforeBaappbase);

            // 案件資料修改主檔改後值物件

            Baappbase beforeBaappbase = new Baappbase();
            BeanUtility.copyProperties(beforeBaappbase, getBaappbaseDetail(caseObj.getBaappbaseId(), null, null, null));
            beforeList.add(beforeBaappbase);

            // 案件資料修改 相關主檔改後值物件

            for (int i = 0; i < extModifyList.size(); i++) {
                BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();
                BaappbaseDataUpdateCase beforeModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改後值物件

                BeanUtility.copyProperties(beforeObj, beforeModifyObj);
                beforeList.add(beforeObj);
            }
        }

        // 將 案件資料修改主檔 改前改後值(forBAAPPLOG)放進 改前改後值List
        baapplogList.add(caseObj);

        // 判斷需要UPDATE的相關資料

        if (("1").equals(caseObj.getAccRel())) {
            extModifyList = baappbaseDao.selectDataBy(null, caseObj.getApNo(), null, null, null, null);

            for (int i = 0; i < extModifyList.size(); i++) {
                BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();
                // 根據baappbaseId取得詳細資料
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // 這裡只處理事故者以外的相關受款人資料,所以排除SEQNO=0000的

                if (!("0000").equals(extModifyObj.getSeqNo())) {
                    // Insert MMAPLOG
                    if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                        BeanUtility.copyProperties(beforeObj, extModifyObj);
                        beforeList.add(beforeObj);
                    }

                    // 處理修改主檔後相關主檔需修改的欄位 / 案件資料更正相關主檔 改前改後值 for BAAPPLOG
                    ApplicationDataUpdateForm objForm = new ApplicationDataUpdateForm();
                    BeanUtility.copyPropertiesForUpdate(objForm, extModifyObj, ConstantKey.OLD_FIELD_PREFIX);
                    // 相關主檔需修改的欄位

                    // [
                    extModifyObj.setEvtNationTpe(caseObj.getEvtNationTpe());// 事故者國籍別
                    extModifyObj.setEvtNationCode(caseObj.getEvtNationCode());// 事故者國籍

                    extModifyObj.setEvtSex(caseObj.getEvtSex());// 事故者性別
                    extModifyObj.setEvtName(caseObj.getEvtName());// 事故者姓名

                    extModifyObj.setEvtBrDate(caseObj.getEvtBrDate());// 事故者出生日期

                    extModifyObj.setEvtIdnNo(caseObj.getEvtIdnNo());// 事故者身分證號

                    extModifyObj.setEvtJobDate(caseObj.getEvtJobDate());// 事故者離職日期

                    extModifyObj.setEvtDieDate(caseObj.getBenDieDate());// 事故者死亡日期

                    // extModifyObj.setBenNationTyp(caseObj.getBenNationTyp());// 受益人國籍別
                    // extModifyObj.setBenNationCode(caseObj.getBenNationCode());// 受益人國籍

                    // extModifyObj.setBenSex(caseObj.getBenSex());// 受益人性別
                    // extModifyObj.setBenName(caseObj.getBenName());// 受益人姓名

                    // extModifyObj.setBenBrDate(caseObj.getBenBrDate());// 受益人出生日期

                    // extModifyObj.setBenIdnNo(caseObj.getBenIdnNo());// 受益人身分證號

                    // extModifyObj.setBenDieDate(caseObj.getBenDieDate());// 受益人死亡日期

                    extModifyObj.setCombapMark(caseObj.getCombapMark());// 國勞合併申請註記
                    // extModifyObj.setAppDate(caseObj.getAppDate());// 申請日期
                    extModifyObj.setApUbno(caseObj.getApUbno());// 申請單位保險證號
                    extModifyObj.setLsUbno(caseObj.getLsUbno());// 最後單位保險證號

                    extModifyObj.setApItem(caseObj.getApItem());// 申請項目
                    extModifyObj.setNotifyForm(caseObj.getNotifyForm());// 核定格式
                    extModifyObj.setLoanMk(caseObj.getLoanMk());// 結案日期
                    extModifyObj.setCloseDate(caseObj.getCloseDate());// 結案原因
                    extModifyObj.setCloseCause(caseObj.getCloseCause());// 不須抵銷紓困貸款註記
                    extModifyObj.setUpdUser(caseObj.getUpdUser());// 異動者代號

                    extModifyObj.setUpdTime(caseObj.getUpdTime());// 異動日期時間
                    extModifyObj.setDabApNo(caseObj.getDabApNo());// 已領失能年金受理編號
                    extModifyObj.setEvtAge(caseObj.getEvtAge());// 事故者申請時年齡
                    extModifyObj.setPayKind(caseObj.getPayKind());// 給付種類
                    extModifyObj.setEvtAppName(caseObj.getEvtAppName());// 事故者申請時姓名
                    extModifyObj.setEvtAppIdnNo(caseObj.getEvtAppIdnNo());// 事故者申請時身分證號
                    extModifyObj.setEvtAppBrDate(caseObj.getEvtAppBrDate());// 事故者申請時出生日期
                    extModifyObj.setInterValMonth(caseObj.getInterValMonth());// 發放方式
                    // ]

                    // 相關主檔 改前改後值 for BAAPPLOG
                    // [
                    objForm.setEvtNationCode(caseObj.getEvtNationCode());// 事故者國籍

                    // objForm.setEvtName(caseObj.getEvtName());// 事故者姓名

                    objForm.setEvtBrDate(caseObj.getEvtBrDate());// 事故者出生日期

                    objForm.setEvtIdnNo(caseObj.getEvtIdnNo());// 事故者身分證號

                    objForm.setEvtJobDate(caseObj.getEvtJobDate());// 事故者離職日期

                    objForm.setEvtDieDate(caseObj.getBenDieDate());// 事故者死亡日期

                    // objForm.setBenNationCode(caseObj.getBenNationCode());// 受益人國籍

                    // objForm.setBenName(caseObj.getBenName());// 受益人姓名

                    // objForm.setBenBrDate(caseObj.getBenBrDate());// 受益人出生日期

                    // objForm.setBenIdnNo(caseObj.getBenIdnNo());// 受益人身分證號

                    // objForm.setBenDieDate(caseObj.getBenDieDate());// 受益人死亡日期

                    objForm.setCombapMark(caseObj.getCombapMark());// 國勞合併申請註記
                    // objForm.setAppDate(caseObj.getAppDate());// 申請日期
                    objForm.setApUbno(caseObj.getApUbno());// 申請單位保險證號
                    objForm.setLsUbno(caseObj.getLsUbno());// 最後單位保險證號

                    objForm.setApItem(caseObj.getApItem());// 申請項目
                    objForm.setNotifyForm(caseObj.getNotifyForm());// 核定格式
                    objForm.setLoanMk(caseObj.getLoanMk());// 結案日期
                    objForm.setCloseDate(caseObj.getCloseDate());// 結案原因
                    objForm.setCloseCause(caseObj.getCloseCause());// 不須抵銷紓困貸款註記
                    objForm.setDabApNo(caseObj.getDabApNo());// 已領失能年金受理編號
                    objForm.setInterValMonth(caseObj.getInterValMonth());// 發放方式
                    // ]
                    extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_APPLICATION_FIELD_RESOURCE_PREFIX));
                    // 將 案件資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                    baapplogList.add(extModifyObj);
                    BeanUtility.copyProperties(afterObj, extModifyObj);
                    afterList.add(afterObj);
                }
            }
        }
        else {
            // 只更新INTERVALMONTH欄位
            extModifyList = baappbaseDao.selectDataBy(null, caseObj.getApNo(), null, null, null, null);

            for (int i = 0; i < extModifyList.size(); i++) {
                BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();
                // 根據baappbaseId取得詳細資料
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // 這裡只處理事故者以外的相關受款人資料,所以排除SEQNO=0000的

                if (!("0000").equals(extModifyObj.getSeqNo())) {
                    // Insert MMAPLOG
                    if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                        BeanUtility.copyProperties(beforeObj, extModifyObj);
                        beforeList.add(beforeObj);
                    }

                    // 處理修改主檔後相關主檔需修改的欄位 / 案件資料更正相關主檔 改前改後值 for BAAPPLOG
                    ApplicationDataUpdateForm objForm = new ApplicationDataUpdateForm();
                    BeanUtility.copyPropertiesForUpdate(objForm, extModifyObj, ConstantKey.OLD_FIELD_PREFIX);
                    // 相關主檔需修改的欄位

                    // [
                    extModifyObj.setInterValMonth(caseObj.getInterValMonth());// 發放方式
                    // ]

                    // 相關主檔 改前改後值 for BAAPPLOG
                    // [
                    objForm.setInterValMonth(caseObj.getInterValMonth());// 發放方式
                    // ]
                    extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_APPLICATION_FIELD_RESOURCE_PREFIX));
                    // 將 案件資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                    baapplogList.add(extModifyObj);
                    BeanUtility.copyProperties(afterObj, extModifyObj);
                    afterList.add(afterObj);
                }
            }
        }

        // 更新案件資料到 給付主檔
        for (int i = 0; i < afterList.size(); i++) {
            Baappbase obj = (Baappbase) afterList.get(i);
            baappbaseDao.updateDataForApplication(obj);
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            afterList = new ArrayList<Object>();

            // 案件資料修改主檔改後值物件

            Baappbase afterBaappbase = new Baappbase();
            BeanUtility.copyProperties(afterBaappbase, getBaappbaseDetail(caseObj.getBaappbaseId(), null, null, null));
            afterList.add(afterBaappbase);

            // 案件資料修改 相關主檔改後值物件

            for (int i = 0; i < extModifyList.size(); i++) {
                BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();
                BaappbaseDataUpdateCase modifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase afterObj = new Baappbase();// 改後值物件

                BeanUtility.copyProperties(afterObj, modifyObj);
                afterList.add(afterObj);
            }
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        }
        log.debug("Update BAAPPBASE Finished ...");
        // ]

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int i = 0; i < baapplogList.size(); i++) {
            BaappbaseDataUpdateCase obj = baapplogList.get(i);
            BigDecimal baappbaseId = obj.getBaappbaseId();
            List<Baapplog> logList = obj.getBaapplogList();
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
     * 註銷案件資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void cancelDataForApplication(UserBean userData, BaappbaseDataUpdateCase caseObj) {
        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");

        // update BALP0D020 PROCMK
        updateProcMkByApNo(caseObj.getApNo());

        // 相關要修改的主檔清單
        List<Baappbase> extModifyList = new ArrayList<Baappbase>();
        // 案件資料更正改前物件List
        List<Object> beforeList = new ArrayList<Object>();
        // 案件資料更正改後物件List
        List<Object> afterList = new ArrayList<Object>();
        // 改前改後值List for BAAPPLOG
        List<BaappbaseDataUpdateCase> baapplogList = new ArrayList<BaappbaseDataUpdateCase>();

        // 註銷案件資料 更動欄位值

        caseObj.setCaseMk("D");// 案件註記
        caseObj.setProcStat("99");// 處理狀態

        caseObj.setUpdUser(userData.getEmpNo());// 異動者代號

        caseObj.setUpdTime(DateUtility.getNowWestDateTime(true));// 異動日期時間
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, caseObj);
        afterList.add(baappbase);
        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 取得案件資料修改主檔改前值物件

            Baappbase beforeBaappbase = new Baappbase();
            beforeBaappbase = baappbaseDao.selectDetailDataBy(caseObj.getBaappbaseId(), null, null, null);
            beforeList.add(beforeBaappbase);
        }

        // 將 案件資料修改主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
        baapplogList.add(caseObj);

        // 判斷需要UPDATE的相關資料

        if (("1").equals(caseObj.getAccRel())) {
            extModifyList = baappbaseDao.selectDataBy(null, caseObj.getApNo(), null, null, null, null);

            for (int i = 0; i < extModifyList.size(); i++) {
                BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();
                // 根據baappbaseId取得詳細資料
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 案件資料更正相關主檔 改前改後值 for BAAPPLOG
                ApplicationDataUpdateForm objForm = new ApplicationDataUpdateForm();
                BeanUtility.copyPropertiesForUpdate(objForm, extModifyObj, ConstantKey.OLD_FIELD_PREFIX);

                // 相關主檔需修改的欄位

                // [
                extModifyObj.setCaseMk("D");// 案件註記
                extModifyObj.setProcStat("99");// 處理狀態

                extModifyObj.setUpdUser(caseObj.getUpdUser());// 異動者代號

                extModifyObj.setUpdTime(caseObj.getUpdTime());// 異動日期時間
                // ]

                // 相關主檔 改前改後值 for BAAPPLOG
                // [
                objForm.setCaseMk("D");// 案件註記
                objForm.setProcStat("99");// 處理狀態

                // ]

                // 將 案件資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                // extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_APPLICATION_FIELD_RESOURCE_PREFIX));
                baapplogList.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);
            }
        }

        // 更新案件資料到 給付主檔
        for (int i = 0; i < afterList.size(); i++) {
            Baappbase obj = (Baappbase) afterList.get(i);
            baappbaseDao.cancelForApplication(baappbase.getBaappbaseId(), baappbase.getCaseMk(), baappbase.getProcStat(), baappbase.getUpdUser(), baappbase.getUpdTime(), new String[] { "01", "10", "11", "12", "20", "30" }, "in");
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            afterList = new ArrayList<Object>();
            // 案件資料修改主檔改後值物件

            Baappbase afterBaappbase = new Baappbase();
            BeanUtility.copyProperties(afterBaappbase, getBaappbaseDetail(caseObj.getBaappbaseId(), null, null, null));
            afterList.add(afterBaappbase);

            // 案件資料修改 相關主檔改後值物件

            for (int i = 0; i < extModifyList.size(); i++) {
                BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();
                BaappbaseDataUpdateCase modifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase afterObj = new Baappbase();// 改後值物件

                BeanUtility.copyProperties(afterObj, modifyObj);
                afterList.add(afterObj);
            }
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        }
        log.debug("Update BAAPPBASE Finished ...");
        // ]

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int i = 0; i < baapplogList.size(); i++) {
            BaappbaseDataUpdateCase logObj = baapplogList.get(i);
            Baapplog baapplog = new Baapplog();
            baapplog.setBaappbaseId(logObj.getBaappbaseId());// 資料列編號

            baapplog.setStatus("D");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            baapplog.setUpCol(""); // 異動項目
            baapplog.setBvalue("");// 改前內容
            baapplog.setAvalue(""); // 改後內容
            baapplogDao.insertData(baapplog);
        }
        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 依畫面輸入欄位條件轉換 新增/修改 給付主檔 部分欄位<br>
     * 
     * @param payBankIdBranchId 帳號(前)
     * @return
     */
    public BaappbaseDataUpdateCase transUpdateData(BaappbaseDataUpdateCase caseObj, BaappbaseDataUpdateCase origCase, String updateType) {
        // 通訊資料更正
        if ((UPDATE_TYPE_COMMUNICATION).equals(updateType)) {
            // 根據 事故者身分證號, 出生日期 取得戶政資料
            Cvldtl cvldtlData = selectCvldtlNameBy(caseObj.getBenIdnNo(), caseObj.getBenBrDate());

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
        }
        // 給付資料更正
        if ((UPDATE_TYPE_PAYMENT).equals(updateType)) {
            // 匯款匯費預設為"0"
            caseObj.setMitRate(new BigDecimal(0));

            // 本人領取
            if (("1").equals(caseObj.getPayCategory())) {
                caseObj.setAccRel("1");
                caseObj.setAccIdn(caseObj.getBenIdnNo());
                caseObj.setAccName(caseObj.getAccName());
                caseObj.setAccSeqNo(caseObj.getSeqNo());
                String payTyp = caseObj.getPayTyp();
                // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」

                // 「給付方式」(本人領取)=1時

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
                    // caseObj.setPayBankId(ConstantKey.BAAPPBASE_PAYTYP_2_PAYBANKID);
                    // caseObj.setBranchId(ConstantKey.BAAPPBASE_PAYTYP_2_BRANCHID);
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
                // 當「給付方式」(本人領取)=5、6時，「金融機構名稱」=【BAMO0D021C】畫面中的「金融機構名稱」(已存在case中)
                // 「金融機構總代號」=「帳號」(1~3碼)
                // 「分支代號」=「帳號」(4~7碼)
                // 「銀行帳號」=「帳號」(8~21碼)
                // 「戶名」=【BAMO0D021C】畫面中的「戶名」(已存在case中)
                else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(payTyp)) {
                    String account = caseObj.getPayAccount();
                    if (account.length() > 7) {
                        caseObj.setPayBankId(account.substring(0, 3));
                        caseObj.setBranchId(account.substring(3, 7));
                        caseObj.setPayEeacc(account.substring(7, account.length()));
                    }
                    else if (account.length() > 3 && account.length() <= 7) {
                        caseObj.setPayBankId(account.substring(0, 3));
                        caseObj.setBranchId(account.substring(3, account.length()));
                        caseObj.setPayEeacc("");
                    }
                    else {
                        caseObj.setPayBankId(account.substring(0, account.length()));
                        caseObj.setBranchId("");
                        caseObj.setPayEeacc("");
                    }
                    // 匯款匯費
                    String mitRate = getMitRateData(caseObj.getPayTyp());
                    if (StringUtils.isNotBlank(mitRate)) {
                        caseObj.setMitRate(new BigDecimal(mitRate));
                    }
                }
                // 當「給付方式」(本人領取)=3、4、9、A時，「金融機構名稱」= ' '
                // 「金融機構總代號」= ' '
                // 「分支代號」= ' '
                // 「銀行帳號」= ' '
                // 「戶名」=「受款人姓名」

                else if ((ConstantKey.BAAPPBASE_PAYTYP_3).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_4).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_9).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_A).equals(payTyp)) {
                    caseObj.setBankName("");
                    caseObj.setPayBankId("");
                    caseObj.setBranchId("");
                    caseObj.setPayEeacc("");
                    caseObj.setAccIdn("");
                    caseObj.setAccName("");
                }
                // 當「給付方式」(本人領取)=7、8
                // 「金融機構總代號」=「帳號」(前：1~3碼)!=700, 轉換方式同「給付方式」(本人領取)=1
                // 「金融機構總代號」=「帳號」(前：1~3碼)=700, 轉換方式同「給付方式」(本人領取)=2
                else if ((ConstantKey.BAAPPBASE_PAYTYP_7).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_8).equals(payTyp)) {
                    String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
                    String accountB = caseObj.getPayEeacc();// 帳號(後)
                    if (!(ConstantKey.BAAPPBASE_PAYTYP_2_PAYBANKID).equals(accountF.substring(0, 3))) {
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
                    else {
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
                }
                // 2009.12.01修改 給付方式為1或2時,金融機構總代號.分支代號.帳號需補0
                if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_2).equals(payTyp)) {
                    caseObj.setPayBankId(StringUtils.leftPad(caseObj.getPayBankId(), 3, "0"));// 金融機構總代號

                    caseObj.setBranchId(StringUtils.leftPad(caseObj.getBranchId(), 4, "0"));// 分支代號
                    caseObj.setPayEeacc(StringUtils.leftPad(caseObj.getPayEeacc(), 14, "0"));// 帳號
                }
            }
            // 具名領取
            else if (("2").equals(caseObj.getPayCategory())) {
                caseObj.setAccRel("3");
                // 取得具名領取相關資料
                // Baappbase beforBaappbase = baappbaseDao.selectDetailDataBy(caseObj.getBaappbaseId(), null, caseObj.getBenIdnNo(), null);
                // Baappbase beforBaappbase = baappbaseDao.selectDetailDataBy(caseObj.getBaappbaseId(), null, caseObj.getAccSeqNo(), null);
                Baappbase baappbase = baappbaseDao.selectBankDataBy(caseObj.getAccBaappbaseId(), caseObj.getAccSeqNo());

                if (baappbase != null) {
                    caseObj.setPayTyp(baappbase.getPayTyp());
                    caseObj.setBankName(baappbase.getBankName());
                    caseObj.setPayBankId(baappbase.getPayBankId());
                    caseObj.setBranchId(baappbase.getBranchId());
                    caseObj.setPayEeacc(baappbase.getPayEeacc());
                    caseObj.setAccIdn(baappbase.getAccIdn());
                    caseObj.setAccName(baappbase.getAccName());
                    caseObj.setMitRate(baappbase.getMitRate());
                }
                else {
                    caseObj.setPayTyp("");
                    caseObj.setBankName("");
                    caseObj.setPayBankId("");
                    caseObj.setBranchId("");
                    caseObj.setPayEeacc("");
                    caseObj.setAccIdn("");
                    caseObj.setAccName("");
                    caseObj.setMitRate(new BigDecimal(0));
                }
            }
        }

        // 案件資料更正
        if ((UPDATE_TYPE_APPLICATION).equals(updateType)) {
            // 日期轉換：「申請日期」、「事故者出生日期」、「離職日期」、「死亡日期」、「結案日期」

            // 轉換成西元年日期再回存，其轉換後的日期格式為YYYYMMDD。

            if (StringUtils.isNotBlank(caseObj.getAppDate())) {
                caseObj.setAppDate(DateUtility.changeDateType(caseObj.getAppDate()));
            }
            if (StringUtils.isNotBlank(caseObj.getEvtJobDate())) {
                caseObj.setEvtJobDate(DateUtility.changeDateType(caseObj.getEvtJobDate()));
            }
            if (StringUtils.isNotBlank(caseObj.getEvtBrDate())) {
                caseObj.setEvtBrDate(DateUtility.changeDateType(caseObj.getEvtBrDate()));
                caseObj.setBenBrDate(caseObj.getEvtBrDate());
            }
            if (StringUtils.isNotBlank(caseObj.getEvtDieDate())) {
                caseObj.setEvtDieDate(DateUtility.changeDateType(caseObj.getEvtDieDate()));
                caseObj.setBenDieDate(caseObj.getEvtDieDate());
            }
            else {
                caseObj.setBenDieDate("");
            }
            if (StringUtils.isNotBlank(caseObj.getCloseDate())) {
                caseObj.setCloseDate(DateUtility.changeDateType(caseObj.getCloseDate()));
            }

            // 根據 事故者身分證號, 出生日期 取得戶政資料
            Cvldtl cvldtlData = selectCvldtlNameBy(caseObj.getEvtIdnNo(), caseObj.getEvtBrDate());
            caseObj.setBenIdnNo(caseObj.getEvtIdnNo());
            caseObj.setBenName(caseObj.getEvtName());

            // 「社福識別碼」若查無資料時，則「社福識別碼」= ' '。

            if (("").equals(cvldtlData.getNpIds()) || cvldtlData.getNpIds() == null) {
                caseObj.setEvtIds("");
                caseObj.setBenIds("");
            }
            else {
                caseObj.setEvtIds(cvldtlData.getNpIds());
                caseObj.setBenIds(cvldtlData.getNpIds());
            }

            // 「國籍別」=1時

            // 「性別」=「事故者身分證號」的第2碼

            // 「國籍」= '000'
            // 「國籍別」=2時

            // 「性別」=【BAAP0D011A】畫面中的「性別」欄位

            // 「國籍」=【BAAP0D011A】畫面中的「國籍」欄位

            caseObj.setBenNationTyp(caseObj.getEvtNationTpe());
            if (("1").equals(caseObj.getEvtNationTpe())) {
                if (StringUtils.isNotBlank(caseObj.getEvtIdnNo()) && caseObj.getEvtIdnNo().length() >= 2) {
                    caseObj.setEvtSex(caseObj.getEvtIdnNo().substring(1, 2));
                    caseObj.setBenSex(caseObj.getEvtIdnNo().substring(1, 2));
                }
                caseObj.setEvtNationCode("000");
                caseObj.setBenNationCode("000");
            }
            else {
                caseObj.setBenSex(caseObj.getEvtSex());
                caseObj.setBenNationCode(caseObj.getEvtNationCode());
            }

            // 「不須抵銷紓困貸款註記」的CheckBox有勾選時，則「不須抵銷紓困貸款註記」= '1'
            // 「不須抵銷紓困貸款註記」的CheckBox無勾選時，則「不須抵銷紓困貸款註記」= '0'
            if (!("1").equals(caseObj.getLoanMk())) {
                caseObj.setLoanMk("0");
            }

            // 「國、勞合併申請」= Y 時, 則「給付種類」= 48
            // 「國、勞合併申請」= "" 時, 則「給付種類」= 45
            if (("Y").equals(caseObj.getCombapMark())) {
                caseObj.setPayKind("48");
            }
            else {
                caseObj.setPayKind("45");
            }

            // 2011.12.20 「事故者年齡」改由編審帶入
            caseObj.setEvtAge(origCase.getEvtAge());

            // 2010.05.06 新增
            if (StringUtils.equals("1", origCase.getAccRel())) {
                if (StringUtils.equals(ConstantKey.BAAPPBASE_PAYTYP_3, origCase.getPayTyp()) || StringUtils.equals(ConstantKey.BAAPPBASE_PAYTYP_4, origCase.getPayTyp()) || StringUtils.equals(ConstantKey.BAAPPBASE_PAYTYP_A, origCase.getPayTyp())) {
                    caseObj.setAccIdn("");
                }
                else {
                    caseObj.setAccIdn(caseObj.getBenIdnNo());
                }
            }
        }

        return caseObj;
    }

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
        String bankName1 = bcbpfDao.selectBankNameBy(payBankIdBranchId.substring(0, 3) + "0000");
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
     * 依傳入條件取得 具名領取(<code>BAAPPBASE</code>) 清單
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @param accRel 戶名與受益人關係
     * @param benEvtRel 受益人與事故者關係
     * @param seqNo 序號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getBenList(BigDecimal baappbaseId, String apNo, String accRel, String[] benEvtRel, String seqNo) {
        return baappbaseDao.selectBenListBy(baappbaseId, apNo, accRel, benEvtRel, seqNo);
    }

    /**
     * 自 處理註記參數檔 (<code>BAPANDOMK</code>) 取得 匯款匯費 資料
     * 
     * @return
     */
    public String getMitRateData(String payTyp) {
        return baparamDao.selectParamNameBy(null, ConstantKey.BAPARAM_PARAMGROUP_MITRATE, payTyp);
    }

    /**
     * 依傳入的 受理編號 自 給付核定檔與主檔取得 <code>StopPaymentProcessCase</code><br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param apNo 受理編號
     * @return <code>List<StopPaymentProcessCase></code>; 若無資料回傳 <code>null</code>
     */
    public StopPaymentProcessCase getStopPaymentProcessCaseBy(String apNo, String issuYm) {
        log.debug("執行 UpdateService.getStopPaymentProcessCaseBy() 開始 ...");

        // 主檔
        StopPaymentProcessCase caseData = badaprDao.selectData(apNo, issuYm);
        // 核定檔資料

        List<StopPaymentProcessDetailCase> detailList = badaprDao.selectStopPaymentDetailListData(apNo, issuYm);
        List<StopPaymentProcessDetailCase> tempList = new ArrayList<StopPaymentProcessDetailCase>();
        // 給付年月表頭
        StopPaymentProcessDetailCase tempData1 = new StopPaymentProcessDetailCase();
        if (detailList.size() > 0) {
            tempData1.setFirstCol("給付年月：" + DateUtil.changeWestYearMonthType(detailList.get(0).getPayYm()));
        }
        if (caseData != null) {
            tempData1.setIssuYm(DateUtility.changeChineseYearMonthType(caseData.getIssuYm()));
        }
        tempList.add(tempData1);

        // 序號
        Integer rowNum = 1;
        String payYmtmp = "";
        if (detailList.size() > 0) {
            payYmtmp = detailList.get(0).getPayYm(); // 第一個
        }
        for (int i = 0; i < detailList.size(); i++) {
            if (i == 0) { // 第一筆

                StopPaymentProcessDetailCase tempData = detailList.get(i);
                tempData.setFirstCol(rowNum.toString());
                tempList.add(tempData);
            }
            else {// 第二筆

                if (payYmtmp.equals(detailList.get(i).getPayYm())) {// 相同時

                    StopPaymentProcessDetailCase tempData = detailList.get(i);
                    rowNum++;
                    tempData.setFirstCol(rowNum.toString());
                    tempList.add(tempData);
                }
                else {// 不同時

                    payYmtmp = detailList.get(i).getPayYm();
                    // 給付年月表頭
                    StopPaymentProcessDetailCase tempData2 = new StopPaymentProcessDetailCase();
                    tempData2.setFirstCol("給付年月：" + DateUtil.changeWestYearMonthType(detailList.get(i).getPayYm()));
                    tempList.add(tempData2);
                    rowNum = 1;
                    StopPaymentProcessDetailCase tempData = detailList.get(i);
                    tempData.setFirstCol(rowNum.toString());
                    tempList.add(tempData);
                }
            }

            if (detailList.get(i).getStexpnd().equals(" "))
                caseData.setBtnUpdate("enable");
        }

        caseData.setApplyList(tempList);
        log.debug("執行 UpdateService.getStopPaymentProcessCaseBy() 完成 ...");
        return caseData;
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BADAPR</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateStopPaymentProcessData(List<StopPaymentProcessDetailCase> detailData, String stexpndReason, UserBean userData, List<StopPaymentProcessDetailCase> beforeDetailData) {
        log.debug("執行 UpdateService.updateStopPaymentProcessData() 開始 ...");
        List<Badapr> beforeBadapr = new ArrayList<Badapr>(); // 給付核定檔改前值

        List<Badapr> afterBadapr = new ArrayList<Badapr>(); // 給付核定檔改後值

        if (beforeDetailData != null && userData != null) {
            for (int i = 0; i < beforeDetailData.size(); i++) {
                Badapr beforeBadaprData = new Badapr();
                BeanUtility.copyProperties(beforeBadaprData, beforeDetailData.get(i));

                beforeBadapr.add(beforeBadaprData);
            }
        }

        if (detailData != null && userData != null) {
            for (int i = 0; i < detailData.size(); i++) {
                Badapr badaprData = new Badapr();
                BeanUtility.copyProperties(badaprData, detailData.get(i));
                // 當止付條件為空值時，才可以更新
                if (StringUtils.isBlank(badaprData.getStexpnd())) {
                    badaprData.setStexpndReason(stexpndReason); // 止付原因
                    badaprData.setStexpndDate(DateUtility.getNowWestDate()); // 止付日期
                    badaprData.setProcTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間
                    badaprDao.updateData(badaprData);
                }
                afterBadapr.add(badaprData);
            }
        }

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeBadapr, afterBadapr);
        }

        log.debug("執行 UpdateService.updateStopPaymentProcessData() 完成 ...");
    }

    /**
     * 依傳入條件取得 給付主檔 債務繼承人資料清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> selectInheritorRsgisterBy(String apno) {
        return baappbaseDao.selectInheritorRsgisterBy(apno);
    }

    /**
     * 依傳入條件取得 給付主檔 債務繼承人資料清單
     * 
     * @param apno 受理編號
     * @param benName 繼承人姓名
     * @param benIdnNo 繼承人身分證號
     * @param benBrDate 繼承人出生日期
     * @return
     */
    public List<Baappbase> selectInheritorRsgisterForModify(String apno, String benName, String benIdnNo, String benBrDate) {
        return baappbaseDao.selectInheritorRsgisterForModify(apno, benName, benIdnNo, benBrDate);
    }

    /**
     * 依傳入條件取得 給付主檔 受款人資料清單 for 老年、失能結案狀態變更作業
     * 
     * @param apno 受理編號
     * @return
     */
    public List<CloseStatusAlterationCase> selectPayeeListForOldAgeAndDisabledCloseStatusAlteration(String apNo) {

        List<Baappbase> list = baappbaseDao.selectPayeeListForOldAgeAndDisabledCloseStatusAlteration(apNo);
        List<CloseStatusAlterationCase> returnList = new ArrayList<CloseStatusAlterationCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            CloseStatusAlterationCase caseObj = new CloseStatusAlterationCase();
            BeanUtility.copyProperties(caseObj, baappbase);

            returnList.add(caseObj);
        }
        return returnList;
    }
    
    /**
     * 依傳入條件取得 給付主檔 受款人資料清單 for 遺屬結案狀態變更作業
     * 
     * @param apno 受理編號
     * @return
     */
    public List<CloseStatusAlterationCase> selectPayeeListForCloseStatusAlteration(String apNo, String kind) {
        // return baappbaseDao.selectPayeeListForCloseStatusAlteration(apNo);

        List<Baappbase> list = baappbaseDao.selectPayeeListForCloseStatusAlteration(apNo, kind);
        List<CloseStatusAlterationCase> returnList = new ArrayList<CloseStatusAlterationCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            CloseStatusAlterationCase caseObj = new CloseStatusAlterationCase();
            BeanUtility.copyProperties(caseObj, baappbase);

            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔 受款人資料清單 for 結案狀態變更作業
     * 
     * @param apno 受理編號
     * @return
     */
    public List<CloseStatusAlterationCasePayeeList> selectPayeeListForCloseStatusAlteration1(String apNo, String kind) {
        // return baappbaseDao.selectPayeeListForCloseStatusAlteration(apNo);

        List<Baappbase> list = baappbaseDao.selectPayeeListForCloseStatusAlteration(apNo, kind);
        List<CloseStatusAlterationCasePayeeList> returnList = new ArrayList<CloseStatusAlterationCasePayeeList>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            CloseStatusAlterationCasePayeeList caseObj = new CloseStatusAlterationCasePayeeList();
            BeanUtility.copyProperties(caseObj, baappbase);

            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 給付主檔 受款人筆數 for 老年、失能結案狀態變更作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public String getPayeeDataCountForOldAgeAndDisabledCloseStatusAlteration(String apNo) {
        return baappbaseDao.getPayeeDataCountForOldAgeAndDisabledCloseStatusAlteration(apNo);
    }
    
    /**
     * 依傳入條件取得 給付主檔 受款人筆數 for 遺屬結案狀態變更作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public String getPayeeDataCountForCloseStatusAlteration(String apNo) {
        return baappbaseDao.getPayeeDataCountForCloseStatusAlteration(apNo);
    }

    /**
     * 依BAAPPBASEID 給付主檔 債務繼承人資料
     * 
     * @param BAAPPBASEID 受理編號
     * @return
     */
    public Baappbase selectInheritorRsgisterDetailBy(BigDecimal baappbaseId) {
        return baappbaseDao.selectDetailDataBy(baappbaseId, null, null, null);

    }

    /**
     * 新增繼承人資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void insertDataForInheritorregister(InheritorRegisterCase Case, UserBean userData) {
        Baappbase baappbase = new Baappbase();
        // 取得對應欄位資料
        Baappbase info = baappbaseDao.selectInheritorInfo(Case.getApNo(), Case.getBaappbaseId().toString());
        BeanUtility.copyProperties(baappbase, info);

        // 取得序號
        baappbase.setSeqNo(baappbaseDao.selectSeqNoBy(Case.getApNo()));
        baappbase.setApNo(Case.getApNo());

        // 債務繼承人 姓名 生日 身分證號 關係
        baappbase.setBenName(Case.getBenName());
        baappbase.setBenBrDate(Case.getBenBrDate());
        baappbase.setBenIdnNo(Case.getBenIdnNo());
        baappbase.setBenEvtRel(Case.getBenEvtRel());

        // 法定代理人 姓名 生日 身分證號
        baappbase.setGrdName(Case.getGrdName());
        baappbase.setGrdBrDate(Case.getGrdBrDate());
        baappbase.setGrdIdnNo(Case.getGrdIdnNo());

        // 取得債務繼承人社福識別碼、通訊郵遞區號、通訊地址
        List<Cvldtl> cvldtl = cvldtlDao.selectDataBy(Case.getBenIdnNo(), Case.getBenBrDate());
        // 債務繼承人社福識別碼
        if (cvldtl.size() > 0) {
            baappbase.setBenIds(cvldtl.get(0).getNpIds());
        }
        else {
            baappbase.setBenIds("");
        }
        // 當「國籍別」=1時，「性別」=「身分證號」的第2碼 國籍別」=2時，「性別」=「性別」欄位。

        baappbase.setBenNationTyp(Case.getBenNationTyp());
        if (baappbase.getBenNationTyp().equals("1")) {
            baappbase.setBenSex(Case.getBenIdnNo().substring(1, 2));
            baappbase.setBenNationCode("0");
        }
        else {
            baappbase.setBenSex(Case.getBenSex());
            baappbase.setBenNationCode(Case.getBenNationCode());
        }
        // 「通訊地址別」=1時，「通訊郵遞區號」、「通訊地址」=戶籍 , 「通訊地址別」=2時，「通訊郵遞區號」、「通訊地址」=畫面欄位
        baappbase.setCommTyp(Case.getCommTyp());
        if (baappbase.getCommTyp().equals("1")) {
            if (cvldtl.size() > 0) {
                baappbase.setCommZip(cvldtl.get(0).getCzip());
                baappbase.setCommAddr(cvldtl.get(0).getCaddr());
            }
        }
        else {
            baappbase.setCommZip(Case.getCommZip());
            baappbase.setCommAddr(Case.getCommAddr());
        }

        baappbase.setBenPayMk("0");
        baappbase.setCrtTime(DateUtility.getNowWestDateTime(true));
        // 新增給付主檔資料
        BigDecimal baappbaseId = baappbaseDao.insertDataForSurvivorPayeeInheritData(baappbase);

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
     * 更新繼承人資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForInheritorRegister(InheritorRegisterCase Case, UserBean userData) {
        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");
        // 取得改前值物件

        Baappbase beforBaappbase = baappbaseDao.selectDetailDataBy(Case.getBaappbaseId(), null, null, null);

        // 改後值物件

        Baappbase afterBaappbase = new Baappbase();
        BeanUtility.copyProperties(afterBaappbase, beforBaappbase);

        // 債務繼承人 姓名 生日 身分證號 關係
        afterBaappbase.setBenName(Case.getBenName());
        afterBaappbase.setBenBrDate(Case.getBenBrDate());
        afterBaappbase.setBenIdnNo(Case.getBenIdnNo());
        afterBaappbase.setBenEvtRel(Case.getBenEvtRel());

        // 法定代理人 姓名 生日 身分證號
        afterBaappbase.setGrdName(Case.getGrdName());
        afterBaappbase.setGrdBrDate(Case.getGrdBrDate());
        afterBaappbase.setGrdIdnNo(Case.getGrdIdnNo());

        // 取得債務繼承人社福識別碼、通訊郵遞區號、通訊地址
        List<Cvldtl> cvldtl = cvldtlDao.selectDataBy(Case.getBenIdnNo(), Case.getBenBrDate());
        if (cvldtl.size() > 0) {
            afterBaappbase.setBenIds(cvldtl.get(0).getNpIds());
        }
        else {
            afterBaappbase.setBenIds("");
        }

        // 當「國籍別」=1時，「性別」=「身分證號」的第2碼 國籍別」=2時，「性別」=「性別」欄位。

        afterBaappbase.setBenNationTyp(Case.getBenNationTyp());

        if (afterBaappbase.getBenNationTyp().equals("1")) {
            afterBaappbase.setBenSex(Case.getBenIdnNo().substring(1, 2));
            afterBaappbase.setBenNationCode("0");
        }
        else {
            afterBaappbase.setBenSex(Case.getBenSex());
            afterBaappbase.setBenNationCode(Case.getBenNationCode());
        }
        // 「通訊地址別」=1時，「通訊郵遞區號」、「通訊地址」=戶籍 , 「通訊地址別」=2時，「通訊郵遞區號」、「通訊地址」=畫面欄位
        afterBaappbase.setCommTyp(Case.getCommTyp());
        if (afterBaappbase.getCommTyp().equals("1")) {
            if (cvldtl.size() > 0) {
                afterBaappbase.setCommZip(cvldtl.get(0).getCzip());
                afterBaappbase.setCommAddr(cvldtl.get(0).getCaddr());
            }
        }
        else {
            afterBaappbase.setCommZip(Case.getCommZip());
            afterBaappbase.setCommAddr(Case.getCommAddr());
        }
        afterBaappbase.setTel1(Case.getTel1());
        afterBaappbase.setTel2(Case.getTel2());
        afterBaappbase.setUpdUser(userData.getEmpNo());// 異動者代號

        afterBaappbase.setUpdTime("");// 異動日期時間
        afterBaappbase.setChgMk("Y");// 更正註記

        // 更新通訊資料到 給付主檔
        baappbaseDao.updateDataForInheritorRegister(afterBaappbase);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforBaappbase, afterBaappbase);
        }
        log.debug("Update BAAPPBASE Finished ...");
        // ]

        // 新增資料到 更正記錄檔

        // [
        log.debug("Start Insert BAAPPLOG ...");
        BigDecimal baappbaseId = afterBaappbase.getBaappbaseId();
        List<Baapplog> logList = Case.getBaapplogList();
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
     * 依傳入條件取得 給付主檔 for 受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<BaappbaseDataUpdateCase> selectPayeeDataForInsert(String apno) {
        return baappbaseDao.selectPayeeDataForInsert(apno);
    }

    /**
     * 依傳入條件取得 給付主檔 for 受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<BaappbaseDataUpdateCase> selectPayeeDataForList(String apNo) {
        List<BaappbaseDataUpdateCase> caseList = baappbaseDao.selectPayeeDataForList(apNo);
        for (int i = 0; i < caseList.size(); i++) {
            BaappbaseDataUpdateCase caseData = caseList.get(i);
            caseData.setDelButtonQ4(baappbaseDao.selectForPayeeDataUpdateQueryQ4(caseData.getApNo(), caseData.getSeqNo()).toString());
        }
        return caseList;
    }

    /**
     * 依傳入條件取得 給付主檔 for 更正作業 - 死亡改匯 - 繼承人維護
     * 
     * @param apNo 受理編號
     * @param heirSeqNo 繼承人序
     * @return
     */
    public List<BaappbaseDataUpdateCase> selectPayeeDataListForOldAgeDeathRepayBy(String apNo, String heirSeqNo, String seqNo) {
        List<BaappbaseDataUpdateCase> caseList = baappbaseDao.selectPayeeDataListForOldAgeDeathRepayBy(apNo, heirSeqNo, seqNo);
        for (int i = 0; i < caseList.size(); i++) {
            BaappbaseDataUpdateCase caseData = caseList.get(i);
            caseData.setDelButtonQ4(baappbaseDao.selectForPayeeDataUpdateQueryQ4(caseData.getApNo(), caseData.getSeqNo()).toString());
        }
        return caseList;
    }

    /**
     * 依傳入條件取得 給付主檔 for 失能年金受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<BaappbaseDataUpdateCase> selectDisabledPayeeDataForList(String apNo) {
        List<BaappbaseDataUpdateCase> caseList = baappbaseDao.selectDisabledPayeeDataForList(apNo);
        for (int i = 0; i < caseList.size(); i++) {
            BaappbaseDataUpdateCase caseData = caseList.get(i);
            caseData.setDelButtonQ4(baappbaseDao.selectForPayeeDataUpdateQueryQ4(caseData.getApNo(), caseData.getSeqNo()).toString());
        }
        return caseList;
    }

    /**
     * 依傳入條件取得 失能年金受款人事故者死亡日期是否可以修改
     * 
     * @param apNo
     */
    public Boolean isEvtDieDateUpdatableForDisabledPayee(String apNo) {
        return baappbaseDao.isEvtDieDateUpdatableForDisabledPayee(apNo);
    }

    /**
     * 依傳入條件取得 在學期間檔 for 遺屬年金受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Bastudterm> selectStudtermListForSurvivorPayeeDataUpdate(String apNo, String seqNo) {
        List<Bastudterm> studtermList = bastudtermDao.selectStudtermListForSurvivorPayeeDataUpdate(apNo, seqNo);
        return studtermList;
    }

    /**
     * 依傳入條件取得 重殘期間檔 for 遺屬年金受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序號
     * @return 內含<code>Bahandicapterm</code> 物件的List
     */
    public List<Bahandicapterm> selectHandicaptermListForSurvivorPayeeDataUpdate(String apNo, String seqNo) {
        List<Bahandicapterm> handicaptermList = bahandicaptermDao.selectHandicaptermListForSurvivorPayeeDataUpdate(apNo, seqNo);
        return handicaptermList;
    }
    
    /**
     * 依傳入條件取得 給付主檔 for 遺屬年金受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<SurvivorPayeeDataUpdateCase> selectSurvivorPayeeDataForList(String apNo, String seqNo) {
        List<SurvivorPayeeDataUpdateCase> caseList = baappbaseDao.selectSurvivorPayeeDataForList(apNo, seqNo);
        for (int i = 0; i < caseList.size(); i++) {
            SurvivorPayeeDataUpdateCase caseData = caseList.get(i);
            caseData.setDelButtonQ4(baappbaseDao.selectForPayeeDataUpdateQueryQ4(caseData.getApNo(), caseData.getSeqNo()).toString());
        }
        return caseList;
    }

    /**
     * 依傳入條件取得 給付主檔 for 遺屬年金受款人資料更正作業(符合註記)
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Bachkfile> getSurvivorPayeeQualifyCheckMark(String apNo, String seqNo) {
        List<Bachkfile> list = bachkfileDao.getSurvivorPayeeQualifyCheckMark(apNo, seqNo);
        return list;
    }

    /**
     * 依傳入條件取得 給付主檔 for 遺屬年金受款人資料更正作業(遺屬註記)
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Bachkfile> getSurvivorPayeeCheckMark(String apNo, String seqNo) {
        List<Bachkfile> list = bachkfileDao.getSurvivorPayeeCheckMark(apNo, seqNo);
        return list;
    }

    /**
     * 依傳入條件取得 給付主檔 for 受款人資料更正作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public Baappbase getPayeeDataUpdateForUpdateCaseBy(String baappbaseId) {
        return baappbaseDao.getPayeeDataUpdateForUpdateCaseBy(baappbaseId);
    }

    /**
     * 依傳入條件取得 給付主檔 for 受款人資料更正作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public Baappbase getDisabledPayeeDataUpdateForUpdateCaseBy(String baappbaseId) {
        return baappbaseDao.getDisabledPayeeDataUpdateForUpdateCaseBy(baappbaseId);
    }

    /**
     * 依傳入條件取得 給付延伸主檔 for 遺屬年金受款人資料更正作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public Baappexpand getSurvivorPayeeDataUpdateForUpdateCaseBy(BigDecimal baappbaseId) {
        Baappexpand expand = baappexpandDao.selectDataForSurvivorPayeeDataUpdate(baappbaseId);
        return (expand == null) ? new Baappexpand() : expand;
    }

    /**
     * 依傳入條件取得 給付主檔 for 受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<BaappbaseDataUpdateCase> selectPayeeDataForMustIssueAmt(String apno) {
        return baappbaseDao.selectPayeeDataForMustIssueAmt(apno);
    }

    /**
     * 依傳入條件取得 給付主檔 for 受款人資料更正作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public String getPayeeDataCount(String apNo, String benIdnNo, String benBrDate) {
        return baappbaseDao.getPayeeDataCount(apNo, benIdnNo, benBrDate);
    }

    /**
     * 依傳入條件取得 給付主檔 for 失能受款人資料更正作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public String getDeadPayeeDataCount(String apNo, String benIdnNo) {
        return baappbaseDao.getDeadPayeeDataCount(apNo, benIdnNo);
    }

    /**
     * 依傳入條件取得 給付主檔 for 受款人資料更正作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public String getPayeeDataCountForUpdate(String apNo, String benIdnNo, String benBrDate, String baappbaseId) {
        return baappbaseDao.getPayeeDataCountForUpdate(apNo, benIdnNo, benBrDate, baappbaseId);
    }

    /**
     * 依傳入條件取得 給付主檔 for 受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public String getDataCount1ForQuery(String apNo, String seqNo) {
        return baappbaseDao.getDataCount1ForQuery(apNo, seqNo);
    }

    /**
     * 依傳入條件取得 BAREGIVEDTL for 受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public String getDataCount2ForQuery(String apNo, String seqNo) {
        return baregivedtlDao.getDataCount2ForQuery(apNo, seqNo);
    }

    /**
     * 依傳入條件取得 BAREGIVEDTL for 受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public String getDataCount3ForQuery(String apNo, String seqNo) {
        return baregivedtlDao.getDataCount3ForQuery(apNo, seqNo);
    }

    /**
     * 依傳入條件取得 BAREGIVEDTL for 死亡改匯處理作業
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public BigDecimal getRemitAmtDataForCheckAvgNum(String apNo, String seqNo, String oriIssuYm, String payYm) {
        return baregivedtlDao.getRemitAmtDataForCheckAvgNum(apNo, seqNo, oriIssuYm, payYm);
    }

    /**
     * 依傳入條件取得 給付主檔 for 失能受款人資料更正作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public String getPayeeDataCountForDisabledUpdate(String apNo, String benIdnNo, String benBrDate, String baappbaseId, String seqNo) {
        return baappbaseDao.getPayeeDataCountForDisabledUpdate(apNo, benIdnNo, benBrDate, baappbaseId, seqNo);
    }

    /**
     * 新增 給付主檔 for 受款人資料更正作業
     * 
     * @param baappbase 給付主檔
     */
    public void insertBaappbaseDataForPayeeData(UserBean userData, BaappbaseDataUpdateCase queryData, BaappbaseDataUpdateCase baappbaseData, String interValMonth) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(baappbaseData.getApNo());

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAAPPBASE ...");
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, baappbaseData);
        baappbase.setImk(queryData.getImk());// 保險別

        baappbase.setApItem(queryData.getApItem());
        baappbase.setPayKind(queryData.getPayKind());// 給付種類
        baappbase.setCaseTyp(queryData.getCaseTyp());// 案件類別
        baappbase.setMapNo(queryData.getMapNo());
        baappbase.setMapRootMk(queryData.getMapRootMk());
        baappbase.setCombapMark(queryData.getCombapMark());
        baappbase.setProcStat("01");// 處理狀態

        baappbase.setApUbno(queryData.getApUbno());
        baappbase.setEvtIds(queryData.getEvtIds());
        baappbase.setEvtIdnNo(queryData.getEvtIdnNo());
        baappbase.setEvtName(queryData.getEvtName());
        baappbase.setEvtBrDate(queryData.getEvtBrDate());
        baappbase.setEvtSex(queryData.getEvtSex());
        baappbase.setEvtNationTpe(queryData.getEvtNationTpe());
        baappbase.setEvtNationCode(queryData.getEvtNationCode());
        baappbase.setEvtIdent(queryData.getEvtIdent());// 事故者身分別
        baappbase.setEvtJobDate(queryData.getEvtJobDate());
        baappbase.setBenPayMk("1");
        baappbase.setChgMk("Y");// 更正註記
        baappbase.setCrtUser(userData.getEmpNo());// 新增者代號

        baappbase.setAppDate(baappbaseData.getAppDate());
        baappbase.setOldAplDpt(baappbaseData.getOldAplDpt()); // 申請代算單位
        baappbase.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間

        baappbase.setInterValMonth(interValMonth); // 間隔月份

        // 新增給付主檔資料
        BigDecimal baappbaseId = baappbaseDao.insertDataForSurvivorPayeeInheritData(baappbase);

        // Insert MMAPLOG
        baappbase.setBaappbaseId(baappbaseId);

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baappbase);
        }
        log.debug("Insert BAAPPBASE Finished ...");

        // 判斷需要UPDATE的相關資料

        // 相關要修改的主檔清單
        List<BigDecimal> payTypeList = new ArrayList<BigDecimal>();
        List<BigDecimal> benEvtRelList = new ArrayList<BigDecimal>();
        List<BigDecimal> procstatList = new ArrayList<BigDecimal>();
        // 給付資料更正改前物件List
        List<Baappbase> beforeList = new ArrayList<Baappbase>();
        // 給付資料更正改後物件List
        List<Baappbase> afterList = new ArrayList<Baappbase>();
        // 正在修改資料該筆改前改後值List for BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        // 其他需要同步資料的改前改後值List for BAAPPLOG
        List<BaappbaseDataUpdateCase> baapplogList2 = new ArrayList<BaappbaseDataUpdateCase>();
        Baappbase beforeBaappbase = new Baappbase();

        if (("1").equals(baappbaseData.getBenEvtRel())) { // 本人
            benEvtRelList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());
            for (int i = 0; i < benEvtRelList.size(); i++) {
                baappbaseId = (BigDecimal) benEvtRelList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);
                extModifyObj.setEvtIds(baappbaseData.getBenIds()); // 受款人社福識別碼
                extModifyObj.setEvtIdnNo(baappbaseData.getBenIdnNo()); // 受款人事故者身分證號

                extModifyObj.setEvtName(baappbaseData.getBenName()); // 受款人事故者姓名

                extModifyObj.setEvtBrDate(baappbaseData.getBenBrDate()); // 受款人事故者出生日期

                extModifyObj.setEvtSex(baappbaseData.getBenSex()); // 性別
                extModifyObj.setEvtNationTpe(baappbaseData.getBenNationTyp()); // 國籍別

                extModifyObj.setEvtNationCode(baappbaseData.getBenNationCode()); // 國籍
                extModifyObj.setUpdUser(userData.getEmpNo());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                extModifyObj.setProcStat("01");

                // 2010.05.05修改 新增欄位[
                extModifyObj.setChkDate("");
                extModifyObj.setChkMan("");
                extModifyObj.setRechkDate("");
                extModifyObj.setRechkMan("");
                extModifyObj.setExeDate("");
                extModifyObj.setExeMan("");
                // ]

                // 2011.07.29 修改
                // interValMonth
                extModifyObj.setInterValMonth(interValMonth); // 間隔年月

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForPayeeData(obj);
                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && (obj.getBaappbaseId()).equals(beforeBaappbase.getBaappbaseId())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                }
            }

            // Insert MMAPLOG
            // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // // 紀錄 Log
            // FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
            // }
        }
        else { // 其他親屬
            procstatList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());

            for (int i = 0; i < procstatList.size(); i++) {
                baappbaseId = (BigDecimal) procstatList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);
                extModifyObj.setProcStat("01");
                // 2010.05.05修改 新增欄位[
                extModifyObj.setChkDate("");
                extModifyObj.setChkMan("");
                extModifyObj.setRechkDate("");
                extModifyObj.setRechkMan("");
                extModifyObj.setExeDate("");
                extModifyObj.setExeMan("");
                // ]

                extModifyObj.setUpdUser(userData.getEmpNo());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForPayeeDataAll(obj);
                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && (obj.getBaappbaseId()).equals(beforeBaappbase.getBaappbaseId())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                }
            }

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
            }
        }
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
     * 新增 給付主檔 for 受款人資料更正作業
     * 
     * @param baappbase 給付主檔
     */
    public void insertBaappbaseDataForOldAgeDeathRepayPayeeData(UserBean userData, BaappbaseDataUpdateCase caseData, BaappbaseDataUpdateCase queryData, BaappbaseDataUpdateCase baappbaseData, String interValMonth) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(baappbaseData.getApNo());

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAAPPBASE ...");
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, baappbaseData);
        baappbase.setImk(queryData.getImk());// 保險別

        baappbase.setApItem(queryData.getApItem());
        baappbase.setPayKind(queryData.getPayKind());// 給付種類
        baappbase.setCaseTyp(queryData.getCaseTyp());// 案件類別
        baappbase.setMapNo(queryData.getMapNo());
        baappbase.setMapRootMk(queryData.getMapRootMk());
        baappbase.setCombapMark(queryData.getCombapMark());
        baappbase.setProcStat("01");// 處理狀態

        baappbase.setEvtIds(caseData.getEvtIds());
        baappbase.setEvtIdnNo(caseData.getEvtIdnNo());
        baappbase.setEvtBrDate(caseData.getEvtBrDate());
        baappbase.setEvtName(caseData.getEvtName());
        baappbase.setEvtAppIdnNo(caseData.getEvtAppIdnNo());
        baappbase.setEvtAppName(caseData.getEvtAppName());
        baappbase.setEvtAppBrDate(caseData.getEvtAppBrDate());
        baappbase.setEvtSex(caseData.getEvtSex());
        baappbase.setEvtNationTpe(caseData.getEvtNationTpe());
        baappbase.setEvtNationCode(caseData.getEvtNationCode());
        baappbase.setEvtIdent(caseData.getEvtIdent());// 事故者身分別
        baappbase.setEvtJobDate(caseData.getEvtJobDate());
        baappbase.setEvtDieDate(caseData.getEvtDieDate());
        baappbase.setEvtExpireDate(caseData.getEvtExpireDate());
        baappbase.setEvtEligibleDate(caseData.getEvtEligibleDate());
        baappbase.setEvtMissingDate(caseData.getEvtMissingDate());
        baappbase.setEvtAge(caseData.getEvtAge());

        baappbase.setApUbno(queryData.getApUbno());
        baappbase.setBenPayMk("1");
        baappbase.setChgMk("Y");// 更正註記
        baappbase.setCrtUser(userData.getEmpNo());// 新增者代號

        baappbase.setAppDate(baappbaseData.getAppDate());
        baappbase.setOldAplDpt(baappbaseData.getOldAplDpt()); // 申請代算單位
        baappbase.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間

        baappbase.setInterValMonth(interValMonth); // 間隔月份

        // 新增給付主檔資料
        BigDecimal baappbaseId = baappbaseDao.insertDataForPayeeDataUpdateDetail(baappbase);

        // Insert MMAPLOG
        baappbase.setBaappbaseId(baappbaseId);

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baappbase);
        }
        log.debug("Insert BAAPPBASE Finished ...");

        // 判斷需要UPDATE的相關資料

        // 相關要修改的主檔清單
        List<BigDecimal> payTypeList = new ArrayList<BigDecimal>();
        List<BigDecimal> benEvtRelList = new ArrayList<BigDecimal>();
        List<BigDecimal> procstatList = new ArrayList<BigDecimal>();
        // 給付資料更正改前物件List
        List<Baappbase> beforeList = new ArrayList<Baappbase>();
        // 給付資料更正改後物件List
        List<Baappbase> afterList = new ArrayList<Baappbase>();
        // 正在修改資料該筆改前改後值List for BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        // 其他需要同步資料的改前改後值List for BAAPPLOG
        List<BaappbaseDataUpdateCase> baapplogList2 = new ArrayList<BaappbaseDataUpdateCase>();
        Baappbase beforeBaappbase = new Baappbase();

        if (("1").equals(baappbaseData.getBenEvtRel())) { // 本人
            benEvtRelList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());
            for (int i = 0; i < benEvtRelList.size(); i++) {
                baappbaseId = (BigDecimal) benEvtRelList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);
                extModifyObj.setEvtIds(baappbaseData.getBenIds()); // 受款人社福識別碼
                extModifyObj.setEvtIdnNo(baappbaseData.getBenIdnNo()); // 受款人事故者身分證號

                extModifyObj.setEvtName(baappbaseData.getBenName()); // 受款人事故者姓名

                extModifyObj.setEvtBrDate(baappbaseData.getBenBrDate()); // 受款人事故者出生日期

                extModifyObj.setEvtSex(baappbaseData.getBenSex()); // 性別
                extModifyObj.setEvtNationTpe(baappbaseData.getBenNationTyp()); // 國籍別

                extModifyObj.setEvtNationCode(baappbaseData.getBenNationCode()); // 國籍
                extModifyObj.setUpdUser(userData.getEmpNo());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                extModifyObj.setProcStat("01");

                // 2010.05.05修改 新增欄位[
                extModifyObj.setChkDate("");
                extModifyObj.setChkMan("");
                extModifyObj.setRechkDate("");
                extModifyObj.setRechkMan("");
                extModifyObj.setExeDate("");
                extModifyObj.setExeMan("");
                // ]

                // 2011.07.29 修改
                // interValMonth
                extModifyObj.setInterValMonth(interValMonth); // 間隔年月

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForPayeeData(obj);
                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && (obj.getBaappbaseId()).equals(beforeBaappbase.getBaappbaseId())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                }
            }

            // Insert MMAPLOG
            // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // // 紀錄 Log
            // FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
            // }
        }
        else { // 其他親屬
            procstatList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());

            for (int i = 0; i < procstatList.size(); i++) {
                baappbaseId = (BigDecimal) procstatList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);
                extModifyObj.setProcStat("01");
                // 2010.05.05修改 新增欄位[
                extModifyObj.setChkDate("");
                extModifyObj.setChkMan("");
                extModifyObj.setRechkDate("");
                extModifyObj.setRechkMan("");
                extModifyObj.setExeDate("");
                extModifyObj.setExeMan("");
                // ]

                extModifyObj.setUpdUser(userData.getEmpNo());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForPayeeDataAll(obj);
                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && (obj.getBaappbaseId()).equals(beforeBaappbase.getBaappbaseId())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                }
            }

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
            }
        }
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
     * 修改 給付主檔 for 受款人資料更正作業
     * 
     * @param baappbase 給付主檔
     */
    public void updateBaappbaseDataForPayeeData(UserBean userData, BaappbaseDataUpdateCase baappbaseData, Baappbase caseData, String interValMonth) {
        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");

        // update BALP0D020 PROCMK
        updateProcMkByApNo(baappbaseData.getApNo());

        // 相關要修改的主檔清單
        List<BigDecimal> payTypeList = new ArrayList<BigDecimal>();
        Map<BigDecimal, BigDecimal> payTypeMap = new HashMap<BigDecimal, BigDecimal>();// 2010.05.05 新增
        List<Baappbase> payUpdateList = new ArrayList<Baappbase>();// 2010.05.05 新增
        List<BigDecimal> benEvtRelList = new ArrayList<BigDecimal>();
        List<BigDecimal> procstatList = new ArrayList<BigDecimal>();
        // 給付資料更正改前物件List
        List<Baappbase> beforeList = new ArrayList<Baappbase>();
        // 給付資料更正改後物件List
        List<Baappbase> afterList = new ArrayList<Baappbase>();
        // 正在修改資料該筆改前改後值List for BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        // 其他需要同步資料的改前改後值List for BAAPPLOG
        List<BaappbaseDataUpdateCase> baapplogList2 = new ArrayList<BaappbaseDataUpdateCase>();

        // 取得最新事故者資料
        Baappbase baappbase = baappbaseDao.selectDataForPayeeDataUpdateDetail(baappbaseData.getApNo());

        // 取得改前值

        Baappbase beforeBaappbase = new Baappbase();
        BaappbaseDataUpdateCase extModifyCase = getBaappbaseDetail(caseData.getBaappbaseId(), null, null, null);
        BeanUtility.copyProperties(beforeBaappbase, extModifyCase);
        // beforeList.add(beforeBaappbase); 2010.05.05 修改

        // 複製改後值

        Baappbase afterBaappbase = new Baappbase();
        BeanUtility.copyProperties(afterBaappbase, baappbaseData);
        afterBaappbase.setChgMk("Y");// 更正註記
        afterBaappbase.setUpdUser(userData.getEmpNo());// 異動者代號
        afterBaappbase.setUpdTime(DateUtility.getNowWestDateTime(true));// 異動日期時間

        afterBaappbase.setEvtIds(baappbase.getEvtIds());
        afterBaappbase.setEvtIdnNo(baappbase.getEvtIdnNo());
        afterBaappbase.setEvtBrDate(baappbase.getEvtBrDate());
        afterBaappbase.setEvtName(baappbase.getEvtName());
        afterBaappbase.setEvtAppIdnNo(baappbase.getEvtAppIdnNo());
        afterBaappbase.setEvtAppName(baappbase.getEvtAppName());
        afterBaappbase.setEvtAppBrDate(baappbase.getEvtAppBrDate());
        afterBaappbase.setEvtSex(baappbase.getEvtSex());
        afterBaappbase.setEvtNationTpe(baappbase.getEvtNationTpe());
        afterBaappbase.setEvtNationCode(baappbase.getEvtNationCode());
        afterBaappbase.setEvtIdent(baappbase.getEvtIdent());// 事故者身分別
        afterBaappbase.setEvtJobDate(baappbase.getEvtJobDate());
        afterBaappbase.setEvtDieDate(baappbase.getEvtDieDate());
        afterBaappbase.setEvtExpireDate(baappbase.getEvtExpireDate());
        afterBaappbase.setEvtEligibleDate(baappbase.getEvtEligibleDate());
        afterBaappbase.setEvtMissingDate(baappbase.getEvtMissingDate());
        afterBaappbase.setEvtAge(baappbase.getEvtAge());

        // 2011.07.29 修改
        // interValMonth
        if (!interValMonth.equals("N")) {
            afterBaappbase.setInterValMonth(interValMonth);
        }

        baappbaseDao.updateDataForPayeeUpdateData(afterBaappbase);

        // 將 受款人資料修改主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
        baapplogList = baappbaseData.getBaapplogList();

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int j = 0; j < baapplogList.size(); j++) {
            Baapplog baapplog = baapplogList.get(j);
            baapplog.setBaappbaseId(baappbaseData.getBaappbaseId());// 資料列編號

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

        // 判斷需要UPDATE的相關資料

        if (("1").equals(baappbaseData.getBenEvtRel())) { // 本人
            benEvtRelList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());

            // 需要一併修改相關具名領取資料

            if (("1").equals(baappbaseData.getAccRel())) { // 戶名與受益人關係
                // 具名領取清單
                payTypeList = baappbaseDao.selectBaappbaseIdByAccSeqNo(baappbaseData.getApNo(), baappbaseData.getSeqNo());
                payTypeMap = transListToMapForPayeeDataUpdate(payTypeList);
            }

            for (int i = 0; i < benEvtRelList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) benEvtRelList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // 有記MMAPLOG時才將改前值物件存入List
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 若目前資料為頁面所修改得該筆資料,則改前值物件須取 beforeBaappbase
                    if ((baappbaseId).equals(beforeBaappbase.getBaappbaseId())) {
                        beforeList.add(beforeBaappbase);
                    }
                    else {
                        BeanUtility.copyProperties(beforeObj, extModifyObj);
                        beforeList.add(beforeObj);
                    }
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);
                extModifyObj.setEvtIds(baappbaseData.getBenIds()); // 受款人社福識別碼
                extModifyObj.setEvtIdnNo(baappbaseData.getBenIdnNo()); // 受款人事故者身分證號

                extModifyObj.setEvtName(baappbaseData.getBenName()); // 受款人事故者姓名

                extModifyObj.setEvtBrDate(baappbaseData.getBenBrDate()); // 受款人事故者出生日期

                extModifyObj.setEvtSex(baappbaseData.getBenSex()); // 性別
                extModifyObj.setEvtNationTpe(baappbaseData.getBenNationTyp()); // 國籍別

                extModifyObj.setEvtNationCode(baappbaseData.getBenNationCode()); // 國籍
                extModifyObj.setUpdUser(userData.getEmpNo());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));

                // 2010.05.05修改 新增欄位[
                extModifyObj.setProcStat("01");
                extModifyObj.setChkDate("");
                extModifyObj.setChkMan("");
                extModifyObj.setRechkDate("");
                extModifyObj.setRechkMan("");
                extModifyObj.setExeDate("");
                extModifyObj.setExeMan("");
                // ]

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    extModifyObj.setPayTyp(baappbaseData.getPayTyp());
                    extModifyObj.setBankName(baappbaseData.getBankName()); // 金融機構名稱
                    extModifyObj.setPayBankId(baappbaseData.getPayBankId()); // 金融機構總代號

                    extModifyObj.setBranchId(baappbaseData.getBranchId()); // 分支代號
                    extModifyObj.setAccName(baappbaseData.getAccName()); // 戶名
                    extModifyObj.setPayEeacc(baappbaseData.getPayEeacc()); // 銀行帳號

                    extModifyObj.setAccIdn(baappbaseData.getAccIdn());
                    extModifyObj.setMitRate(baappbaseData.getMitRate()); // 匯款匯費
                    extModifyObj.setUpdUser(baappbaseData.getUpdUser());
                    extModifyObj.setUpdTime(baappbaseData.getUpdTime());
                    objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
                    objForm.setPayEeacc(extModifyObj.getPayEeacc());
                    objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
                }

                // 2011.07.29 修改
                // interValMonth
                if (!interValMonth.equals("N")) {
                    extModifyObj.setInterValMonth(interValMonth);
                }

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    payUpdateList.add(afterObj);
                }

            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForPayeeData(obj);
                // 2010.05.05 修改
                // // Insert MMAPLOG
                // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && (obj.getBaappbaseId()).equals(beforeBaappbase.getBaappbaseId())) {
                // // 紀錄 Log
                // FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                // }
            }
        }
        else { // 其他親屬
            procstatList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());

            if (("1").equals(baappbaseData.getAccRel())) { // 戶名與受益人關係
                // 具名領取清單
                payTypeList = baappbaseDao.selectBaappbaseIdByAccSeqNo(baappbaseData.getApNo(), baappbaseData.getSeqNo());
                payTypeMap = transListToMapForPayeeDataUpdate(payTypeList);
            }

            for (int i = 0; i < procstatList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) procstatList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // 有記MMAPLOG時才將改前值物件存入List
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 若目前資料為頁面所修改得該筆資料,則改前值物件須取 beforeBaappbase
                    if ((baappbaseId).equals(beforeBaappbase.getBaappbaseId())) {
                        beforeList.add(beforeBaappbase);
                    }
                    else {
                        BeanUtility.copyProperties(beforeObj, extModifyObj);
                        beforeList.add(beforeObj);
                    }
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);
                extModifyObj.setProcStat("01");
                extModifyObj.setUpdUser(userData.getEmpNo());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    extModifyObj.setPayTyp(baappbaseData.getPayTyp());
                    extModifyObj.setBankName(baappbaseData.getBankName()); // 金融機構名稱
                    extModifyObj.setPayBankId(baappbaseData.getPayBankId()); // 金融機構總代號

                    extModifyObj.setBranchId(baappbaseData.getBranchId()); // 分支代號
                    extModifyObj.setAccName(baappbaseData.getAccName()); // 戶名
                    extModifyObj.setPayEeacc(baappbaseData.getPayEeacc()); // 銀行帳號

                    extModifyObj.setAccIdn(baappbaseData.getAccIdn());
                    extModifyObj.setMitRate(baappbaseData.getMitRate()); // 匯款匯費
                    extModifyObj.setUpdUser(baappbaseData.getUpdUser());
                    extModifyObj.setUpdTime(baappbaseData.getUpdTime());
                    objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
                    objForm.setPayEeacc(extModifyObj.getPayEeacc());
                    objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
                }

                // 2011.07.29 修改
                // interValMonth
                if (!interValMonth.equals("N")) {
                    extModifyObj.setInterValMonth(interValMonth);
                }

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    payUpdateList.add(afterObj);
                }
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);

                baappbaseDao.updateDataForPayeeDataAll(obj);
                // 2010.05.05 修改
                // // Insert MMAPLOG
                // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && (obj.getBaappbaseId()).equals(beforeBaappbase.getBaappbaseId())) {
                // // 紀錄 Log
                // FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                // }
            }
        }
        // afterList.clear();
        //
        // if (("1").equals(baappbaseData.getAccRel())) { // 戶名與受益人關係
        // // payTypeList = baappbaseDao.selectBenListBy(baappbaseData.getBaappbaseId(), baappbaseData.getApNo(), "3", null, baappbaseData.getSeqNo());
        // payTypeList = baappbaseDao.selectBaappbaseIdByAccSeqNo(baappbaseData.getApNo(), baappbaseData.getSeqNo());
        //
        // for (int i = 0; i < payTypeList.size(); i++) {
        // BigDecimal baappbaseId = payTypeList.get(i);
        // BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
        // Baappbase beforeObj = new Baappbase();// 改前值物件

        // Baappbase afterObj = new Baappbase();// 改後值物件

        //
        // // Insert MMAPLOG
        // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
        // BeanUtility.copyProperties(beforeObj, extModifyObj);
        // beforeList.add(beforeObj);
        // }
        //
        // // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
        // PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
        // BeanUtility.copyProperties(objForm, extModifyObj);
        // extModifyObj.setPayTyp(baappbaseData.getPayTyp());
        // extModifyObj.setBankName(baappbaseData.getBankName()); // 金融機構名稱
        // extModifyObj.setPayBankId(baappbaseData.getPayBankId()); // 金融機構總代號

        // extModifyObj.setBranchId(baappbaseData.getBranchId()); // 分支代號
        // extModifyObj.setAccName(baappbaseData.getAccName()); // 戶名
        // extModifyObj.setPayEeacc(baappbaseData.getPayEeacc()); // 銀行帳號

        // extModifyObj.setAccIdn(baappbaseData.getAccIdn());
        // extModifyObj.setMitRate(baappbaseData.getMitRate()); // 匯款匯費
        // extModifyObj.setUpdUser(baappbaseData.getUpdUser());
        // extModifyObj.setUpdTime(baappbaseData.getUpdTime());
        // objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
        // objForm.setPayEeacc(extModifyObj.getPayEeacc());
        // objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
        // extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));
        //
        // // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
        // baapplogList2.add(extModifyObj);
        // BeanUtility.copyProperties(afterObj, extModifyObj);
        // afterList.add(afterObj);
        // }
        //
        // // 更新資料到 給付主檔
        // for (int i = 0; i < afterList.size(); i++) {
        // Baappbase obj = (Baappbase) afterList.get(i);
        // baappbaseDao.updateDataForPayment(obj);
        // }
        //
        // // Insert MMAPLOG
        // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && afterList.size() > 0) {
        // // 紀錄 Log
        // FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        // }
        // }
        // log.debug("Update BAAPPBASE Finished ...");
        // // ]

        // 2010.05.05 修改
        // 更新具名領取資料到 給付主檔
        if (("1").equals(baappbaseData.getAccRel())) {
            for (int i = 0; i < payUpdateList.size(); i++) {
                Baappbase obj = (Baappbase) payUpdateList.get(i);
                baappbaseDao.updateDataForPayment(obj);
            }
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        }

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int i = 0; i < baapplogList2.size(); i++) {
            BaappbaseDataUpdateCase logObj = baapplogList2.get(i);
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
     * 修改 給付主檔 for 死亡改匯繼承人資料維護
     * 
     * @param baappbase 給付主檔
     */
    public void updateBaappbaseDataForOldAgeDeathRepayPayeeData(UserBean userData, BaappbaseDataUpdateCase baappbaseData, Baappbase caseData, String interValMonth) {
        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");

        // update BALP0D020 PROCMK
        updateProcMkByApNo(baappbaseData.getApNo());

        // 相關要修改的主檔清單
        List<BigDecimal> payTypeList = new ArrayList<BigDecimal>();
        Map<BigDecimal, BigDecimal> payTypeMap = new HashMap<BigDecimal, BigDecimal>();// 2010.05.05 新增
        List<Baappbase> payUpdateList = new ArrayList<Baappbase>();// 2010.05.05 新增
        List<BigDecimal> benEvtRelList = new ArrayList<BigDecimal>();
        List<BigDecimal> procstatList = new ArrayList<BigDecimal>();
        // 給付資料更正改前物件List
        List<Baappbase> beforeList = new ArrayList<Baappbase>();
        // 給付資料更正改後物件List
        List<Baappbase> afterList = new ArrayList<Baappbase>();
        // 正在修改資料該筆改前改後值List for BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        // 其他需要同步資料的改前改後值List for BAAPPLOG
        // List<BaappbaseDataUpdateCase> baapplogList2 = new ArrayList<BaappbaseDataUpdateCase>();

        // 取得最新事故者資料
        Baappbase baappbase = baappbaseDao.selectDataForPayeeDataUpdateDetail(baappbaseData.getApNo());

        // 取得改前值

        Baappbase beforeBaappbase = new Baappbase();
        BaappbaseDataUpdateCase extModifyCase = getBaappbaseDetail(caseData.getBaappbaseId(), null, null, null);
        BeanUtility.copyProperties(beforeBaappbase, extModifyCase);
        // beforeList.add(beforeBaappbase); 2010.05.05 修改

        // 複製改後值

        Baappbase afterBaappbase = new Baappbase();
        BeanUtility.copyProperties(afterBaappbase, baappbaseData);
        afterBaappbase.setChgMk("Y");// 更正註記
        afterBaappbase.setUpdUser(userData.getEmpNo());// 異動者代號
        afterBaappbase.setUpdTime(DateUtility.getNowWestDateTime(true));// 異動日期時間

        afterBaappbase.setEvtIds(baappbase.getEvtIds());
        afterBaappbase.setEvtIdnNo(baappbase.getEvtIdnNo());
        afterBaappbase.setEvtBrDate(baappbase.getEvtBrDate());
        afterBaappbase.setEvtName(baappbase.getEvtName());
        afterBaappbase.setEvtAppIdnNo(baappbase.getEvtAppIdnNo());
        afterBaappbase.setEvtAppName(baappbase.getEvtAppName());
        afterBaappbase.setEvtAppBrDate(baappbase.getEvtAppBrDate());
        afterBaappbase.setEvtSex(baappbase.getEvtSex());
        afterBaappbase.setEvtNationTpe(baappbase.getEvtNationTpe());
        afterBaappbase.setEvtNationCode(baappbase.getEvtNationCode());
        afterBaappbase.setEvtIdent(baappbase.getEvtIdent());// 事故者身分別
        afterBaappbase.setEvtJobDate(baappbase.getEvtJobDate());
        afterBaappbase.setEvtDieDate(baappbase.getEvtDieDate());
        afterBaappbase.setEvtExpireDate(baappbase.getEvtExpireDate());
        afterBaappbase.setEvtEligibleDate(baappbase.getEvtEligibleDate());
        afterBaappbase.setEvtMissingDate(baappbase.getEvtMissingDate());
        afterBaappbase.setEvtAge(baappbase.getEvtAge());

        // 2011.07.29 修改
        // interValMonth
        if (!interValMonth.equals("N")) {
            afterBaappbase.setInterValMonth(interValMonth);
        }

        baappbaseDao.updateDataForOldAgeDeathRepayPayeeUpdateData(afterBaappbase);

        // 將 受款人資料修改主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
        baapplogList = baappbaseData.getBaapplogList();

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int j = 0; j < baapplogList.size(); j++) {
            Baapplog baapplog = baapplogList.get(j);
            baapplog.setBaappbaseId(baappbaseData.getBaappbaseId());// 資料列編號

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

        // 判斷需要UPDATE的相關資料

        if (("1").equals(baappbaseData.getBenEvtRel())) { // 本人
            benEvtRelList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());

            // 需要一併修改相關具名領取資料

            if (("1").equals(baappbaseData.getAccRel())) { // 戶名與受益人關係
                // 具名領取清單
                payTypeList = baappbaseDao.selectBaappbaseIdByAccSeqNo(baappbaseData.getApNo(), baappbaseData.getSeqNo());
                payTypeMap = transListToMapForPayeeDataUpdate(payTypeList);
            }

            for (int i = 0; i < benEvtRelList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) benEvtRelList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // 有記MMAPLOG時才將改前值物件存入List
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 若目前資料為頁面所修改得該筆資料,則改前值物件須取 beforeBaappbase
                    if ((baappbaseId).equals(beforeBaappbase.getBaappbaseId())) {
                        beforeList.add(beforeBaappbase);
                    }
                    else {
                        BeanUtility.copyProperties(beforeObj, extModifyObj);
                        beforeList.add(beforeObj);
                    }
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);
                extModifyObj.setEvtIds(baappbaseData.getBenIds()); // 受款人社福識別碼
                extModifyObj.setEvtIdnNo(baappbaseData.getBenIdnNo()); // 受款人事故者身分證號

                extModifyObj.setEvtName(baappbaseData.getBenName()); // 受款人事故者姓名

                extModifyObj.setEvtBrDate(baappbaseData.getBenBrDate()); // 受款人事故者出生日期

                extModifyObj.setEvtSex(baappbaseData.getBenSex()); // 性別
                extModifyObj.setEvtNationTpe(baappbaseData.getBenNationTyp()); // 國籍別

                extModifyObj.setEvtNationCode(baappbaseData.getBenNationCode()); // 國籍
                extModifyObj.setUpdUser(userData.getEmpNo());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));

                // 2010.05.05修改 新增欄位[
                extModifyObj.setProcStat("01");
                extModifyObj.setChkDate("");
                extModifyObj.setChkMan("");
                extModifyObj.setRechkDate("");
                extModifyObj.setRechkMan("");
                extModifyObj.setExeDate("");
                extModifyObj.setExeMan("");
                // ]

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    extModifyObj.setPayTyp(baappbaseData.getPayTyp());
                    extModifyObj.setBankName(baappbaseData.getBankName()); // 金融機構名稱
                    extModifyObj.setPayBankId(baappbaseData.getPayBankId()); // 金融機構總代號

                    extModifyObj.setBranchId(baappbaseData.getBranchId()); // 分支代號
                    extModifyObj.setAccName(baappbaseData.getAccName()); // 戶名
                    extModifyObj.setPayEeacc(baappbaseData.getPayEeacc()); // 銀行帳號

                    extModifyObj.setAccIdn(baappbaseData.getAccIdn());
                    extModifyObj.setMitRate(baappbaseData.getMitRate()); // 匯款匯費
                    extModifyObj.setUpdUser(baappbaseData.getUpdUser());
                    extModifyObj.setUpdTime(baappbaseData.getUpdTime());
                    objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
                    objForm.setPayEeacc(extModifyObj.getPayEeacc());
                    objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
                }

                // 2011.07.29 修改
                // interValMonth
                if (!interValMonth.equals("N")) {
                    extModifyObj.setInterValMonth(interValMonth);
                }

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                // baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    payUpdateList.add(afterObj);
                }

            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForPayeeData(obj);
                // 2010.05.05 修改
                // // Insert MMAPLOG
                // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && (obj.getBaappbaseId()).equals(beforeBaappbase.getBaappbaseId())) {
                // // 紀錄 Log
                // FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                // }
            }
        }
        else { // 其他親屬
            procstatList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());

            if (("1").equals(baappbaseData.getAccRel())) { // 戶名與受益人關係
                // 具名領取清單
                payTypeList = baappbaseDao.selectBaappbaseIdByAccSeqNo(baappbaseData.getApNo(), baappbaseData.getSeqNo());
                payTypeMap = transListToMapForPayeeDataUpdate(payTypeList);
            }

            for (int i = 0; i < procstatList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) procstatList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // 有記MMAPLOG時才將改前值物件存入List
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 若目前資料為頁面所修改得該筆資料,則改前值物件須取 beforeBaappbase
                    if ((baappbaseId).equals(beforeBaappbase.getBaappbaseId())) {
                        beforeList.add(beforeBaappbase);
                    }
                    else {
                        BeanUtility.copyProperties(beforeObj, extModifyObj);
                        beforeList.add(beforeObj);
                    }
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);
                extModifyObj.setProcStat("01");
                extModifyObj.setUpdUser(userData.getEmpNo());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    extModifyObj.setPayTyp(baappbaseData.getPayTyp());
                    extModifyObj.setBankName(baappbaseData.getBankName()); // 金融機構名稱
                    extModifyObj.setPayBankId(baappbaseData.getPayBankId()); // 金融機構總代號

                    extModifyObj.setBranchId(baappbaseData.getBranchId()); // 分支代號
                    extModifyObj.setAccName(baappbaseData.getAccName()); // 戶名
                    extModifyObj.setPayEeacc(baappbaseData.getPayEeacc()); // 銀行帳號

                    extModifyObj.setAccIdn(baappbaseData.getAccIdn());
                    extModifyObj.setMitRate(baappbaseData.getMitRate()); // 匯款匯費
                    extModifyObj.setUpdUser(baappbaseData.getUpdUser());
                    extModifyObj.setUpdTime(baappbaseData.getUpdTime());
                    objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
                    objForm.setPayEeacc(extModifyObj.getPayEeacc());
                    objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
                }

                // 2011.07.29 修改
                // interValMonth
                if (!interValMonth.equals("N")) {
                    extModifyObj.setInterValMonth(interValMonth);
                }

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                // baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    payUpdateList.add(afterObj);
                }
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);

                baappbaseDao.updateDataForPayeeDataAll(obj);
                // 2010.05.05 修改
                // // Insert MMAPLOG
                // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && (obj.getBaappbaseId()).equals(beforeBaappbase.getBaappbaseId())) {
                // // 紀錄 Log
                // FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                // }
            }
        }
        // afterList.clear();
        //
        // if (("1").equals(baappbaseData.getAccRel())) { // 戶名與受益人關係
        // // payTypeList = baappbaseDao.selectBenListBy(baappbaseData.getBaappbaseId(), baappbaseData.getApNo(), "3", null, baappbaseData.getSeqNo());
        // payTypeList = baappbaseDao.selectBaappbaseIdByAccSeqNo(baappbaseData.getApNo(), baappbaseData.getSeqNo());
        //
        // for (int i = 0; i < payTypeList.size(); i++) {
        // BigDecimal baappbaseId = payTypeList.get(i);
        // BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
        // Baappbase beforeObj = new Baappbase();// 改前值物件

        // Baappbase afterObj = new Baappbase();// 改後值物件

        //
        // // Insert MMAPLOG
        // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
        // BeanUtility.copyProperties(beforeObj, extModifyObj);
        // beforeList.add(beforeObj);
        // }
        //
        // // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
        // PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
        // BeanUtility.copyProperties(objForm, extModifyObj);
        // extModifyObj.setPayTyp(baappbaseData.getPayTyp());
        // extModifyObj.setBankName(baappbaseData.getBankName()); // 金融機構名稱
        // extModifyObj.setPayBankId(baappbaseData.getPayBankId()); // 金融機構總代號

        // extModifyObj.setBranchId(baappbaseData.getBranchId()); // 分支代號
        // extModifyObj.setAccName(baappbaseData.getAccName()); // 戶名
        // extModifyObj.setPayEeacc(baappbaseData.getPayEeacc()); // 銀行帳號

        // extModifyObj.setAccIdn(baappbaseData.getAccIdn());
        // extModifyObj.setMitRate(baappbaseData.getMitRate()); // 匯款匯費
        // extModifyObj.setUpdUser(baappbaseData.getUpdUser());
        // extModifyObj.setUpdTime(baappbaseData.getUpdTime());
        // objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
        // objForm.setPayEeacc(extModifyObj.getPayEeacc());
        // objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
        // extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));
        //
        // // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
        // baapplogList2.add(extModifyObj);
        // BeanUtility.copyProperties(afterObj, extModifyObj);
        // afterList.add(afterObj);
        // }
        //
        // // 更新資料到 給付主檔
        // for (int i = 0; i < afterList.size(); i++) {
        // Baappbase obj = (Baappbase) afterList.get(i);
        // baappbaseDao.updateDataForPayment(obj);
        // }
        //
        // // Insert MMAPLOG
        // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && afterList.size() > 0) {
        // // 紀錄 Log
        // FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        // }
        // }
        // log.debug("Update BAAPPBASE Finished ...");
        // // ]

        // 2010.05.05 修改
        // 更新具名領取資料到 給付主檔
        if (("1").equals(baappbaseData.getAccRel())) {
            for (int i = 0; i < payUpdateList.size(); i++) {
                Baappbase obj = (Baappbase) payUpdateList.get(i);
                baappbaseDao.updateDataForPayment(obj);
            }
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        }

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        // log.debug("Start Insert BAAPPLOG ...");
        // for (int i = 0; i < baapplogList2.size(); i++) {
        // BaappbaseDataUpdateCase logObj = baapplogList2.get(i);
        // BigDecimal baappbaseId = logObj.getBaappbaseId();
        // List<Baapplog> logList = logObj.getBaapplogList();
        // for (int j = 0; j < logList.size(); j++) {
        // Baapplog baapplog = logList.get(j);
        // baapplog.setBaappbaseId(baappbaseId);// 資料列編號
        //
        // baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        // baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        // baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // // baapplog.setUpCol(); // 異動項目 - Log 已設
        // // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
        // // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
        // baapplogDao.insertData(baapplog);
        // }
        // }
        // log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 修改 給付主檔 可領金額 for 受款人資料更正作業
     * 
     * @param baappbase 給付主檔
     */
    public void updateBaappbaseDataForMustIssueAmt(UserBean userData, List<BaappbaseDataUpdateCase> baappbaseData, String mustIssueAmt[]) {
        // 給付資料更正改前物件List
        List<Object> beforeList = new ArrayList<Object>();
        // 給付資料更正改後物件List
        List<Object> afterList = new ArrayList<Object>();
        // 改前改後值List for BAAPPLOG
        List<BaappbaseDataUpdateCase> baapplogList = new ArrayList<BaappbaseDataUpdateCase>();

        // 更新資料到 給付主檔
        // [
        log.debug("Start update BAAPPBASE ...");

        for (int i = 0; i < baappbaseData.size(); i++) {
            BaappbaseDataUpdateCase caseData = baappbaseData.get(i);
            // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
            PayeeDataUpdateListForm objForm = new PayeeDataUpdateListForm();
            BeanUtility.copyProperties(objForm, caseData);

            // 從資料庫抓改前值

            Baappbase beforeBaappbase = baappbaseDao.selectDetailDataBy(caseData.getBaappbaseId(), null, null, null);

            // 先存改前值

            beforeList.add(beforeBaappbase);
            Baappbase afterBaappbase = new Baappbase();
            BeanUtility.copyProperties(afterBaappbase, beforeBaappbase);
            afterBaappbase.setMustIssueAmt(new BigDecimal(mustIssueAmt[i])); // 可領金額
            afterBaappbase.setProcStat("01");
            afterBaappbase.setChgMk("Y"); // 更正註記
            afterBaappbase.setUpdUser(userData.getEmpNo()); // 異動者代號

            afterBaappbase.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間
            caseData.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

            // For BAAPPLOG
            baapplogList.add(caseData);

            // 再記改後值

            afterList.add(afterBaappbase);

            // 更新資料到 受款人主檔

            baappbaseDao.updateDataForPayeeDataMustIssueAmt(afterBaappbase);
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        }

        log.debug("Insert BAAPPBASE Finished ...");
        // ]

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int i = 0; i < baapplogList.size(); i++) {
            BaappbaseDataUpdateCase logObj = baapplogList.get(i);
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
     * 依畫面輸入欄位條件轉換 新增/修改 給付主檔 部分欄位<br>
     * 
     * @param payBankIdBranchId 帳號(前)
     * @return
     */
    public BaappbaseDataUpdateCase transPayeeUpdateData(BaappbaseDataUpdateCase caseObj, Baappbase caseData, String type) {
        // 以下是相同的欄位
        caseObj.setApNo(caseData.getApNo());

        // 關係是本人時,姓名出生日期身份證號Disabled,所以存檔時要補資料
        if (type.equals("U") && caseObj.getBenEvtRel().equals("1")) {
            caseObj.setBenName(caseData.getBenName());
            caseObj.setBenIdnNo(caseData.getBenIdnNo());
            caseObj.setBenBrDate(caseData.getBenBrDate());
        }

        if ("1".equals(caseObj.getBenNationTyp())) {
            if ("1".equals(caseObj.getBenIdnNo().substring(1, 2))) {
                caseObj.setBenSex("1");
            }
            else if ("2".equals(caseObj.getBenIdnNo().substring(1, 2))) {
                caseObj.setBenSex("2");
            }
        }

        // 變更日期為西元年
        if (caseObj.getBenEvtRel().equals("2") || caseObj.getBenEvtRel().equals("3") || caseObj.getBenEvtRel().equals("4") || caseObj.getBenEvtRel().equals("5") || caseObj.getBenEvtRel().equals("6") || caseObj.getBenEvtRel().equals("7")) {
            if (StringUtils.defaultString(caseObj.getAppDate()).trim().length() == 7)
                caseObj.setAppDate(DateUtility.changeDateType(caseObj.getAppDate()));
        }
        else {
            if (type.equals("I") && !caseObj.getBenEvtRel().equals("Z"))
                caseObj.setAppDate(""); // 新增時繼承人申請日期,關係為A~Z為空值

            else if (type.equals("I") && caseObj.getBenEvtRel().equals("Z"))
                caseObj.setAppDate(DateUtility.getNowWestDate());
            else
                caseObj.setAppDate(caseData.getAppDate()); // 修改時則不變更原值

        }
        if (StringUtils.defaultString(caseObj.getBenBrDate()).trim().length() == 7) {
            caseObj.setBenBrDate(DateUtility.changeDateType(caseObj.getBenBrDate()));
        }
        if (StringUtils.defaultString(caseObj.getGrdBrDate()).trim().length() == 7) {
            caseObj.setGrdBrDate(DateUtility.changeDateType(caseObj.getGrdBrDate()));
        }

        // 處理SEQNO
        if (type.equals("I")) {
            if (caseObj.getBenEvtRel().equals("1")) { // 本人
                // 本人無法從此功能新增
            }
            else if (caseObj.getBenEvtRel().equals("2") || caseObj.getBenEvtRel().equals("3") || caseObj.getBenEvtRel().equals("4") || caseObj.getBenEvtRel().equals("5") || caseObj.getBenEvtRel().equals("6") || caseObj.getBenEvtRel().equals("7")) {
                if (StringUtils.countMatches(caseObj.getAppUser(), "0") == 4) {
                    // 當「繼承自受款人」= '0000'
                    caseObj.setSeqNo(baappbaseDao.getPayeeDataForSeqNoOne(caseData.getApNo()));
                }
                else if (StringUtils.countMatches(caseObj.getAppUser(), "0") == 3) {
                    // 當「繼承自受款人」= '0x00'
                    caseObj.setSeqNo(baappbaseDao.getPayeeDataForSeqNoTwo(caseData.getApNo()));
                }
                else if (StringUtils.countMatches(caseObj.getAppUser(), "0") == 2) {
                    // 當「繼承自受款人」= '0xx0'
                    caseObj.setSeqNo(baappbaseDao.getPayeeDataForSeqNoThree(caseData.getApNo()));
                }
            }
            else {
                // 當「關係」=A、C、F、N、Z、O
                caseObj.setSeqNo(baappbaseDao.getPayeeDataForSeqNoFour(caseData.getApNo()));
            }
        }

        // 關係為本人，且死亡日期非空的話 因為給付方式不能修改(caseObj.getPayCategory()==null)，所以部分資料要手動塞入
        if (("1").equals(caseObj.getBenEvtRel()) && StringUtils.isNotBlank(caseData.getBenDieDate())) {
            caseObj.setAccIdn(caseData.getAccIdn());
            caseObj.setAccName(caseData.getAccName());
            caseObj.setAccSeqNo(caseData.getAccSeqNo());
        }

        // 本人領取
        if (("1").equals(caseObj.getPayCategory())) {
            caseObj.setAccRel("1"); // 戶名與受益人關係

            caseObj.setAccIdn(caseObj.getBenIdnNo());
            caseObj.setAccName(caseObj.getAccName());
            caseObj.setAccSeqNo(caseObj.getSeqNo());

            String payTyp = caseObj.getPayTyp();
            // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」

            // 「給付方式」(本人領取)=1、8時

            // 「金融機構名稱」=「金融機構名稱一」+「金融機構名稱二」

            // 「金融機構總代號」=「帳號」(前：1~3碼)
            // 「戶名」=【BAMO0D021C】畫面中的「戶名」(已存在case中)
            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_8).equals(payTyp)) {
                String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
                String accountB = caseObj.getPayEeacc();// 帳號(後)
                caseObj.setBankName(getBankName(accountF));// 金融機構名稱
                // 為防止輸入長度不足七碼

                if (accountF.length() >= 3)
                    caseObj.setPayBankId(accountF.substring(0, 3));
                else
                    caseObj.setPayBankId(accountF.substring(0, accountF.length()));
                if (accountF.length() >= 4)
                    caseObj.setBranchId(accountF.substring(3, accountF.length()));
                else
                    caseObj.setBranchId("");
                // caseObj.setPayBankId(accountF.substring(0, 3));// 金融機構總代號

                // caseObj.setBranchId(accountF.substring(3, 7));

                caseObj.setPayEeacc(accountB);
                if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(payTyp)) {
                    caseObj.setAccIdn(caseObj.getBenIdnNo());
                    caseObj.setAccName(caseObj.getAccName());
                    caseObj.setAccSeqNo(caseObj.getSeqNo());
                }
                // 「給付方式」<>5、6時,「匯款匯費(BAAPPBASE.MITRATE)」=0
                caseObj.setMitRate(new BigDecimal(0));
            }
            // 「給付方式」=2時，「金融機構名稱」= 郵局名稱
            // 「金融機構總代號」= '700'
            // 「分支代號」= '0021'
            // 「銀行帳號」=「帳號」(後)
            // 「戶名」=【BAMO0D021C】畫面中的「戶名」(已存在case中)
            else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(payTyp)) {
                String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
                String accountB = caseObj.getPayEeacc();// 帳號(後)
                caseObj.setBankName(getPostName(caseObj.getPayBankIdBranchId()));
                // caseObj.setPayBankId(ConstantKey.BAAPPBASE_PAYTYP_2_PAYBANKID);
                // caseObj.setBranchId(ConstantKey.BAAPPBASE_PAYTYP_2_BRANCHID);
                // 為防止輸入長度不足七碼

                if (accountF.length() >= 3)
                    caseObj.setPayBankId(accountF.substring(0, 3));
                else
                    caseObj.setPayBankId(accountF.substring(0, accountF.length()));

                if (accountF.length() >= 4)
                    caseObj.setBranchId(accountF.substring(3, accountF.length()));
                else
                    caseObj.setBranchId("");
                // caseObj.setPayBankId(accountF.substring(0, 3));
                // caseObj.setBranchId(accountF.substring(3, 7));
                caseObj.setPayEeacc(accountB);
                caseObj.setAccIdn(caseObj.getBenIdnNo());
                caseObj.setAccName(caseObj.getAccName());
                caseObj.setAccSeqNo(caseObj.getSeqNo());

                // 「給付方式」<>5、6時,「匯款匯費(BAAPPBASE.MITRATE)」=0
                caseObj.setMitRate(new BigDecimal(0));
            }
            // 當「給付方式」(本人領取)=5、6時，「金融機構名稱」=【BAMO0D021C】畫面中的「金融機構名稱」(已存在case中)
            // 「金融機構總代號」=「帳號」(1~3碼)
            // 「分支代號」=「帳號」(4~7碼)
            // 「銀行帳號」=「帳號」(8~21碼)
            // 「戶名」=【BAMO0D021C】畫面中的「戶名」(已存在case中)
            else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(payTyp)) {
                String account = caseObj.getPayAccount();
                caseObj.setAccIdn(caseObj.getBenIdnNo());

                if (account.length() > 7) {
                    caseObj.setPayBankId(account.substring(0, 3));
                    caseObj.setBranchId(account.substring(3, 7));
                    caseObj.setPayEeacc(account.substring(7, account.length()));
                }
                else if (account.length() > 3 && account.length() <= 7) {
                    caseObj.setPayBankId(account.substring(0, 3));
                    caseObj.setBranchId(account.substring(3, account.length()));
                    caseObj.setPayEeacc("");
                }
                else {
                    caseObj.setPayBankId(account.substring(0, account.length()));
                    caseObj.setBranchId("");
                    caseObj.setPayEeacc("");
                }
                // 匯款匯費
                String mitRate = getMitRateData(caseObj.getPayTyp());
                if (StringUtils.isNotBlank(mitRate)) {
                    caseObj.setMitRate(new BigDecimal(mitRate));
                }

                caseObj.setAccIdn(caseObj.getBenIdnNo());
                caseObj.setAccName(caseObj.getAccName());
                caseObj.setAccSeqNo(caseObj.getSeqNo());
            }
            // 當「給付方式」(本人領取)=3、4、9、A時，「金融機構名稱」= ' '
            // 「金融機構總代號」= ' '
            // 「分支代號」= ' '
            // 「銀行帳號」= ' '
            // 「戶名」=「受款人姓名」

            else if ((ConstantKey.BAAPPBASE_PAYTYP_3).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_4).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_9).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_A).equals(payTyp)) {
                caseObj.setBankName("");
                caseObj.setPayBankId("");
                caseObj.setBranchId("");
                caseObj.setPayEeacc("");
                caseObj.setAccIdn("");
                caseObj.setAccName("");
                caseObj.setAccSeqNo(caseObj.getSeqNo());

                // 「給付方式」<>5、6時,「匯款匯費(BAAPPBASE.MITRATE)」=0
                caseObj.setMitRate(new BigDecimal(0));
            }
        }
        // 具名領取
        else if (("2").equals(caseObj.getPayCategory())) {
            String payTyp = caseObj.getPayTyp();
            caseObj.setAccRel("3"); // 戶名與受益人關係
            // 取得具名領取相關資料
            // Baappbase beforBaappbase = baappbaseDao.selectDetailDataBy(caseObj.getBaappbaseId(), null, caseObj.getBenIdnNo(), null);
            Baappbase baappbase = baappbaseDao.selectBankDataByAccSeqNo(caseObj.getApNo(), caseObj.getAccSeqNo());
            if (baappbase != null) {
                caseObj.setPayTyp(baappbase.getPayTyp());
                caseObj.setBankName(baappbase.getBankName());
                caseObj.setPayBankId(baappbase.getPayBankId());
                caseObj.setBranchId(baappbase.getBranchId());
                caseObj.setPayEeacc(baappbase.getPayEeacc());
                caseObj.setAccIdn(baappbase.getAccIdn());
                caseObj.setAccName(baappbase.getAccName());
                caseObj.setMitRate(baappbase.getMitRate());
                caseObj.setInterValMonth(baappbase.getInterValMonth());
            }
            else {
                caseObj.setPayTyp("");
                caseObj.setBankName("");
                caseObj.setPayBankId("");
                caseObj.setBranchId("");
                caseObj.setPayEeacc("");
                caseObj.setAccIdn("");
                caseObj.setAccName("");
                caseObj.setMitRate(new BigDecimal(0));
                caseObj.setInterValMonth("");
            }
        }

        // 取得受款人社福識別碼
        List<Cvldtl> cvldtl = cvldtlDao.selectDataBy(caseObj.getBenIdnNo(), caseObj.getBenBrDate());
        if (cvldtl.size() > 0) {
            caseObj.setBenIds(cvldtl.get(0).getNpIds());
        }
        else {
            caseObj.setBenIds("");
        }

        // 「通訊地址別」=1時，「通訊郵遞區號」、「通訊地址」=戶籍 , 「通訊地址別」=2時，「通訊郵遞區號」、「通訊地址」=畫面欄位
        if (caseObj.getCommTyp().equals("1")) {
            if (cvldtl.size() > 0) {
                caseObj.setCommZip(cvldtl.get(0).getCzip());
                caseObj.setCommAddr(cvldtl.get(0).getCaddr());
            }
        }

        // 國籍
        if (caseObj.getBenNationTyp().equals("1")) {
            caseObj.setBenNationCode("000");
        }

        // 戶籍地址
        if (caseObj.getCommTyp().equals("1")) {
            // 郵遞區號

            caseObj.setCommZip(getCvldtlZip(caseObj.getBenIdnNo(), caseObj.getBenBrDate()));
            // 地址
            caseObj.setCommAddr(getCvldtlAddr(caseObj.getBenIdnNo(), caseObj.getBenBrDate()));
        }

        // 可領金額
        caseObj.setMustIssueAmt(new BigDecimal(0));

        return caseObj;
    }

    /**
     * 修改 給付主檔 for 失能年金受款人資料更正作業
     * 
     * @param baappbase 給付主檔
     */
    public void updateBaappbaseDataForDisabledPayeeData(UserBean userData, BaappbaseDataUpdateCase baappbaseData, Baappbase caseData, String interValMonth) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(baappbaseData.getApNo());

        // 更新資料到 給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");

        // 相關要修改的主檔清單
        List<BigDecimal> payTypeList = new ArrayList<BigDecimal>();
        List<BigDecimal> benEvtRelList = new ArrayList<BigDecimal>();
        List<BigDecimal> procstatList = new ArrayList<BigDecimal>();
        Map<BigDecimal, BigDecimal> payTypeMap = new HashMap<BigDecimal, BigDecimal>();// 2010.05.05 新增
        List<Baappbase> payUpdateList = new ArrayList<Baappbase>();// 2010.05.05 新增
        // 給付資料更正改前物件List
        List<Baappbase> beforeList = new ArrayList<Baappbase>();
        // 給付資料更正改後物件List
        List<Baappbase> afterList = new ArrayList<Baappbase>();
        // 正在修改資料該筆改前改後值List for BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        // 其他需要同步資料的改前改後值List for BAAPPLOG
        // List<BaappbaseDataUpdateCase> baapplogList2 = new ArrayList<BaappbaseDataUpdateCase>();

        // 取得改前值

        Baappbase beforeBaappbase = baappbaseDao.selectDetailDataBy(caseData.getBaappbaseId(), null, null, null);
        // beforeList.add(beforeBaappbase); 2010.05.05 修改

        // 複製改後值

        Baappbase afterBaappbase = new Baappbase();
        BeanUtility.copyProperties(afterBaappbase, baappbaseData);
        afterBaappbase.setChgMk("Y");// 更正註記
        afterBaappbase.setUpdUser(userData.getEmpNo());// 異動者代號

        // afterBaappbase.setCloseCause(baappbaseData.getCloseCause());//結案原因
        afterBaappbase.setUpdTime(DateUtility.getNowWestDateTime(true));// 異動日期時間

        // 2009.11.17 Added
        // 如果是事故者本人,且死亡日期為空,且其改之前的死亡日期為非空

        // 則要把其下所有的繼承人delete掉

        // 若改之前就是空的,則不可以delete
        if ("0000".equals(afterBaappbase.getSeqNo())) {
            if (StringUtils.isNotBlank(beforeBaappbase.getEvtDieDate()) && StringUtils.isBlank(afterBaappbase.getEvtDieDate())) {
                baappbaseDao.deleteDisabledPayeeForCleanEvtDieDate(afterBaappbase.getApNo());
            }
        }

        // 2011.07.29 修改
        // interValMonth
        if (!interValMonth.equals("N")) {
            afterBaappbase.setInterValMonth(interValMonth);
        }

        baappbaseDao.updateDataForDisabledPayeeDataUpdate(afterBaappbase);

        // // Insert MMAPLOG
        // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
        // // 紀錄 Log
        // FrameworkLogUtil.doUpdateLog(beforeBaappbase, afterBaappbase);
        // }

        // 將 受款人資料修改主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
        baapplogList = baappbaseData.getBaapplogList();

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int j = 0; j < baapplogList.size(); j++) {
            Baapplog baapplog = baapplogList.get(j);
            baapplog.setBaappbaseId(baappbaseData.getBaappbaseId());// 資料列編號

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

        // 判斷需要UPDATE的相關資料

        if (("1").equals(baappbaseData.getBenEvtRel())) { // 本人
            benEvtRelList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());

            if (("1").equals(baappbaseData.getAccRel())) { // 戶名與受益人關係
                // 具名領取清單
                payTypeList = baappbaseDao.selectBaappbaseIdByAccSeqNo(baappbaseData.getApNo(), baappbaseData.getSeqNo());
                payTypeMap = transListToMapForPayeeDataUpdate(payTypeList);
            }

            for (int i = 0; i < benEvtRelList.size(); i++) {
                BigDecimal baappbaseId = benEvtRelList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // 有記MMAPLOG時才將改前值物件存入List
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 若目前資料為頁面所修改得該筆資料,則改前值物件須取 beforeBaappbase
                    if ((baappbaseId).equals(beforeBaappbase.getBaappbaseId())) {
                        beforeList.add(beforeBaappbase);
                    }
                    else {
                        BeanUtility.copyProperties(beforeObj, extModifyObj);
                        beforeList.add(beforeObj);
                    }
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);

                // 判斷是否需清除具名領取資料
                if (isCleanCoreceivePaymentData(extModifyObj, baappbaseData)) {
                    // extModifyObj.setAccRel("");
                    // extModifyObj.setAccSeqNo("");
                    extModifyObj.setAccIdn("");
                    extModifyObj.setBankName("");
                    extModifyObj.setPayBankId("");
                    extModifyObj.setPayEeacc("");
                    extModifyObj.setPayTyp("");
                    extModifyObj.setBranchId("");
                }

                extModifyObj.setEvtIds(baappbaseData.getBenIds()); // 受款人社福識別碼
                extModifyObj.setEvtIdnNo(baappbaseData.getBenIdnNo()); // 受款人事故者身分證號
                extModifyObj.setEvtName(baappbaseData.getBenName()); // 受款人事故者姓名
                extModifyObj.setEvtBrDate(baappbaseData.getBenBrDate()); // 受款人事故者出生日期
                extModifyObj.setEvtSex(baappbaseData.getBenSex()); // 性別
                extModifyObj.setEvtNationTpe(baappbaseData.getBenNationTyp()); // 國籍別

                extModifyObj.setProcStat("01");
                // 2010.05.05修改 新增欄位[
                extModifyObj.setChkDate("");
                extModifyObj.setChkMan("");
                extModifyObj.setRechkDate("");
                extModifyObj.setRechkMan("");
                extModifyObj.setExeDate("");
                extModifyObj.setExeMan("");
                // ]
                extModifyObj.setEvtNationCode(baappbaseData.getBenNationCode()); // 國籍
                extModifyObj.setUpdUser(baappbaseData.getUpdUser());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    extModifyObj.setPayTyp(baappbaseData.getPayTyp());
                    extModifyObj.setBankName(baappbaseData.getBankName()); // 金融機構名稱
                    extModifyObj.setPayBankId(baappbaseData.getPayBankId()); // 金融機構總代號

                    extModifyObj.setBranchId(baappbaseData.getBranchId()); // 分支代號
                    extModifyObj.setAccName(baappbaseData.getAccName()); // 戶名
                    extModifyObj.setPayEeacc(baappbaseData.getPayEeacc()); // 銀行帳號

                    extModifyObj.setAccIdn(baappbaseData.getAccIdn());
                    extModifyObj.setMitRate(baappbaseData.getMitRate()); // 匯款匯費
                    extModifyObj.setUpdUser(baappbaseData.getUpdUser());
                    extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                    objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
                    objForm.setPayEeacc(extModifyObj.getPayEeacc());
                    objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
                }

                // 2011.07.29 修改
                // interValMonth
                if (!interValMonth.equals("N")) {
                    extModifyObj.setInterValMonth(interValMonth);
                }

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                // baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    payUpdateList.add(afterObj);
                }
            }

            // 判斷是否需清除相關資料之具名領取資料

            if (!"0000".equals(afterBaappbase.getSeqNo())) {
                if (StringUtils.isNotBlank(afterBaappbase.getBenDieDate())) {
                    baappbaseDao.cleanCoreceivePaymentData(afterBaappbase.getApNo(), afterBaappbase.getSeqNo());
                }
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForPayeeData(obj);
            }
        }
        else { // 其他親屬
            procstatList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());

            if (("1").equals(baappbaseData.getAccRel())) { // 戶名與受益人關係
                // 具名領取清單
                payTypeList = baappbaseDao.selectBaappbaseIdByAccSeqNo(baappbaseData.getApNo(), baappbaseData.getSeqNo());
                payTypeMap = transListToMapForPayeeDataUpdate(payTypeList);
            }

            for (int i = 0; i < procstatList.size(); i++) {
                BigDecimal baappbaseId = procstatList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // 有記MMAPLOG時才將改前值物件存入List
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 若目前資料為頁面所修改得該筆資料,則改前值物件須取 beforeBaappbase
                    if ((baappbaseId).equals(beforeBaappbase.getBaappbaseId())) {
                        beforeList.add(beforeBaappbase);
                    }
                    else {
                        BeanUtility.copyProperties(beforeObj, extModifyObj);
                        beforeList.add(beforeObj);
                    }
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);

                // 判斷是否需清除具名領取資料
                if (isCleanCoreceivePaymentData(extModifyObj, baappbaseData)) {
                    // extModifyObj.setAccRel("");
                    // extModifyObj.setAccSeqNo("");
                    extModifyObj.setAccIdn("");
                    extModifyObj.setBankName("");
                    extModifyObj.setPayBankId("");
                    extModifyObj.setPayEeacc("");
                    extModifyObj.setPayTyp("");
                    extModifyObj.setBranchId("");
                }

                extModifyObj.setProcStat("01");
                // 2010.05.05修改 新增欄位[
                extModifyObj.setChkDate("");
                extModifyObj.setChkMan("");
                extModifyObj.setRechkDate("");
                extModifyObj.setRechkMan("");
                extModifyObj.setExeDate("");
                extModifyObj.setExeMan("");
                // ]
                extModifyObj.setUpdUser(baappbaseData.getUpdUser());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    extModifyObj.setPayTyp(baappbaseData.getPayTyp());
                    extModifyObj.setBankName(baappbaseData.getBankName()); // 金融機構名稱
                    extModifyObj.setPayBankId(baappbaseData.getPayBankId()); // 金融機構總代號

                    extModifyObj.setBranchId(baappbaseData.getBranchId()); // 分支代號
                    extModifyObj.setAccName(baappbaseData.getAccName()); // 戶名
                    extModifyObj.setPayEeacc(baappbaseData.getPayEeacc()); // 銀行帳號

                    extModifyObj.setAccIdn(baappbaseData.getAccIdn());
                    extModifyObj.setMitRate(baappbaseData.getMitRate()); // 匯款匯費
                    extModifyObj.setUpdUser(baappbaseData.getUpdUser());
                    extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                    objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
                    objForm.setPayEeacc(extModifyObj.getPayEeacc());
                    objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
                }

                // 2011.07.29 修改
                // interValMonth
                if (!interValMonth.equals("N")) {
                    extModifyObj.setInterValMonth(interValMonth);
                }

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                // baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbaseData.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    payUpdateList.add(afterObj);
                }
            }

            // 判斷是否需清除相關資料之具名領取資料

            if (!"0000".equals(afterBaappbase.getSeqNo())) {
                if (StringUtils.isNotBlank(afterBaappbase.getBenDieDate())) {
                    baappbaseDao.cleanCoreceivePaymentData(afterBaappbase.getApNo(), afterBaappbase.getSeqNo());
                }
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForPayeeDataAll(obj);
            }
        }
        // afterList.clear();
        //
        // if (("1").equals(baappbaseData.getAccRel())) { // 戶名與受益人關係
        // // payTypeList = baappbaseDao.selectBenListBy(baappbaseData.getBaappbaseId(), baappbaseData.getApNo(), "3", null, baappbaseData.getSeqNo());
        // payTypeList = baappbaseDao.selectBaappbaseIdByAccSeqNo(baappbaseData.getApNo(), baappbaseData.getSeqNo());
        //
        // for (int i = 0; i < payTypeList.size(); i++) {
        // BigDecimal baappbaseId = payTypeList.get(i);
        // BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
        // Baappbase beforeObj = new Baappbase();// 改前值物件

        // Baappbase afterObj = new Baappbase();// 改後值物件

        //
        // // Insert MMAPLOG
        // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
        // BeanUtility.copyProperties(beforeObj, extModifyObj);
        // beforeList.add(beforeObj);
        // }
        //
        // // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
        // PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
        // BeanUtility.copyProperties(objForm, extModifyObj);
        // extModifyObj.setPayTyp(baappbaseData.getPayTyp());
        // extModifyObj.setBankName(baappbaseData.getBankName()); // 金融機構名稱
        // extModifyObj.setPayBankId(baappbaseData.getPayBankId()); // 金融機構總代號

        // extModifyObj.setBranchId(baappbaseData.getBranchId()); // 分支代號
        // extModifyObj.setAccName(baappbaseData.getAccName()); // 戶名
        // extModifyObj.setPayEeacc(baappbaseData.getPayEeacc()); // 銀行帳號

        // extModifyObj.setAccIdn(baappbaseData.getAccIdn());
        // extModifyObj.setMitRate(baappbaseData.getMitRate()); // 匯款匯費
        // extModifyObj.setUpdUser(baappbaseData.getUpdUser());
        // extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
        // objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
        // objForm.setPayEeacc(extModifyObj.getPayEeacc());
        // objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
        // extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));
        //
        // // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
        // baapplogList2.add(extModifyObj);
        // BeanUtility.copyProperties(afterObj, extModifyObj);
        // afterList.add(afterObj);
        // }
        //
        // // 更新資料到 給付主檔
        // for (int i = 0; i < afterList.size(); i++) {
        // Baappbase obj = (Baappbase) afterList.get(i);
        // baappbaseDao.updateDataForPayment(obj);
        // }
        //
        // // Insert MMAPLOG
        // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && afterList.size() > 0) {
        // // 紀錄 Log
        // FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        // }
        // }
        // log.debug("Update BAAPPBASE Finished ...");
        // // ]

        // 2010.05.05 修改
        // 更新具名領取資料到 給付主檔
        for (int i = 0; i < payUpdateList.size(); i++) {
            Baappbase obj = (Baappbase) payUpdateList.get(i);
            baappbaseDao.updateDataForPayment(obj);
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        }

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        // log.debug("Start Insert BAAPPLOG ...");
        // for (int i = 0; i < baapplogList2.size(); i++) {
        // BaappbaseDataUpdateCase logObj = baapplogList2.get(i);
        // BigDecimal baappbaseId = logObj.getBaappbaseId();
        // List<Baapplog> logList = logObj.getBaapplogList();
        // for (int j = 0; j < logList.size(); j++) {
        // Baapplog baapplog = logList.get(j);
        // baapplog.setBaappbaseId(baappbaseId);// 資料列編號
        //
        // baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        // baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        // baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // // baapplog.setUpCol(); // 異動項目 - Log 已設
        // // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
        // // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
        // baapplogDao.insertData(baapplog);
        // }
        // }
        // log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 依傳入條件取得 給付主檔繼承自誰 for 失能年金受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public Baappbase getDisabledAncestorMainData(String apNo, String seqNo) {
        Baappbase ancestor = baappbaseDao.getAncestorInfoBySeqNo(apNo, seqNo);
        return (ancestor == null) ? new Baappbase() : ancestor;
    }

    /**
     * 依傳入條件取得 給付主檔繼承自誰 for 失能年金受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public String getDisabledAncestorData(String apNo, String seqNo) {
        Baappbase ancestor = baappbaseDao.getAncestorInfoBySeqNo(apNo, seqNo);
        return (ancestor == null) ? "" : ancestor.getBenName();
    }

    /**
     * 依畫面輸入欄位條件轉換 新增/修改 給付主檔 部分欄位<br>
     * 
     * @param caseObj
     * @param caseData
     * @param type 是新增還是修改, I = 新增, U = 修改, II = 新增繼承人
     * @return BaappbaseDataUpdateCase
     */
    public BaappbaseDataUpdateCase transDisabledPayeeUpdateData(BaappbaseDataUpdateCase caseObj, Baappbase caseData, String type) {
        // 以下是相同的欄位
        caseObj.setApNo(caseData.getApNo());

        // 關係是本人時,姓名出生日期身份證號Disabled,所以存檔時要補資料
        if (type.equals("U") && caseObj.getBenEvtRel().equals("1")) {
            caseObj.setBenName(caseData.getBenName());
            caseObj.setBenIdnNo(caseData.getBenIdnNo());
            caseObj.setBenBrDate(caseData.getBenBrDate());
        }

        // 變更日期為西元年
        if (caseObj.getBenEvtRel().equals("2") || caseObj.getBenEvtRel().equals("3") || caseObj.getBenEvtRel().equals("4") || caseObj.getBenEvtRel().equals("5") || caseObj.getBenEvtRel().equals("6") || caseObj.getBenEvtRel().equals("7")) {
            if (StringUtils.defaultString(caseObj.getAppDate()).trim().length() == 7)
                caseObj.setAppDate(DateUtility.changeDateType(caseObj.getAppDate()));
        }
        else {
            if (type.equals("I") && !caseObj.getBenEvtRel().equals("Z"))
                caseObj.setAppDate(""); // 新增時繼承人申請日期,關係為A~Z為空值

            else if (type.equals("I") && caseObj.getBenEvtRel().equals("Z"))
                caseObj.setAppDate(DateUtility.getNowWestDate());
            else
                caseObj.setAppDate(caseData.getAppDate()); // 修改時則不變更原值

        }

        if (StringUtils.defaultString(caseObj.getBenBrDate()).trim().length() == 7) {
            caseObj.setBenBrDate(DateUtility.changeDateType(caseObj.getBenBrDate()));
        }
        if (StringUtils.defaultString(caseObj.getGrdBrDate()).trim().length() == 7) {
            caseObj.setGrdBrDate(DateUtility.changeDateType(caseObj.getGrdBrDate()));
        }

        // 新增受款人時，處理SEQNO
        if (type.equals("I")) {
            if (caseObj.getBenEvtRel().equals("2") || caseObj.getBenEvtRel().equals("3") || caseObj.getBenEvtRel().equals("4") || caseObj.getBenEvtRel().equals("5") || caseObj.getBenEvtRel().equals("6") || caseObj.getBenEvtRel().equals("7")) {
                if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 4) {
                    // 當「繼承自受款人」= '0000'
                    caseObj.setSeqNo(baappbaseDao.getPayeeDataForSeqNoOne(caseData.getApNo()));
                }
                else if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 3) {
                    // 當「繼承自受款人」= '0x00'
                    caseObj.setSeqNo(baappbaseDao.getPayeeDataForSeqNoTwo(caseData.getApNo()));
                }
                else if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 2) {
                    // 當「繼承自受款人」= '0xx0'
                    caseObj.setSeqNo(baappbaseDao.getPayeeDataForSeqNoThree(caseData.getApNo()));
                }
            }
        }
        else if (type.equals("II")) {
            if (caseObj.getBenEvtRel().equals("2") || caseObj.getBenEvtRel().equals("3") || caseObj.getBenEvtRel().equals("4") || caseObj.getBenEvtRel().equals("5") || caseObj.getBenEvtRel().equals("6") || caseObj.getBenEvtRel().equals("7")) {
                caseObj.setSeqNo(baappbaseDao.getDisabledPayeeHeirSeqNo(caseData.getApNo(), caseData.getSeqNo()));
            }
        }

        // 本人領取
        if (("1").equals(caseObj.getPayCategory())) {
            caseObj.setAccRel("1"); // 戶名與受益人關係
            caseObj.setAccSeqNo(caseObj.getSeqNo());// 2009.09.17AccSeqNo設為自己的SeqNo
            String payTyp = caseObj.getPayTyp();
            // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」

            // 「給付方式」(本人領取)=1、8時

            // 「金融機構名稱」=「金融機構名稱一」+「金融機構名稱二」

            // 「金融機構總代號」=「帳號」(前：1~3碼)
            // 「戶名」=【BAMO0D021C】畫面中的「戶名」(已存在case中)
            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_8).equals(payTyp)) {
                String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
                String accountB = caseObj.getPayEeacc();// 帳號(後)
                caseObj.setBankName(getBankName(accountF));// 金融機構名稱
                // 為防止輸入長度不足七碼

                caseObj.setAccIdn(caseObj.getBenIdnNo());
                if (accountF.length() >= 3)
                    caseObj.setPayBankId(accountF.substring(0, 3));
                else
                    caseObj.setPayBankId(accountF.substring(0, accountF.length()));
                if (accountF.length() >= 4)
                    caseObj.setBranchId(accountF.substring(3, accountF.length()));
                else
                    caseObj.setBranchId("");
                // caseObj.setPayBankId(accountF.substring(0, 3));// 金融機構總代號

                // caseObj.setBranchId(accountF.substring(3, 7));
                if (StringUtils.isNotBlank(accountB)) {
                    if (accountB.length() < 14) {
                        accountB = StringUtils.leftPad(accountB, 14, '0');
                    }
                }
                caseObj.setPayEeacc(accountB);
                // 「給付方式」<>5、6時,「匯款匯費(BAAPPBASE.MITRATE)」=0
                caseObj.setMitRate(new BigDecimal(0));
            }
            // 「給付方式」=2時，「金融機構名稱」= 郵局名稱
            // 「金融機構總代號」= '700'
            // 「分支代號」= '0021'
            // 「銀行帳號」=「帳號」(後)
            // 「戶名」=【BAMO0D021C】畫面中的「戶名」(已存在case中)
            else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(payTyp)) {
                String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
                String accountB = caseObj.getPayEeacc();// 帳號(後)
                caseObj.setBankName(getPostName(caseObj.getPayBankIdBranchId()));
                caseObj.setAccIdn(caseObj.getBenIdnNo());
                // caseObj.setPayBankId(ConstantKey.BAAPPBASE_PAYTYP_2_PAYBANKID);
                // caseObj.setBranchId(ConstantKey.BAAPPBASE_PAYTYP_2_BRANCHID);
                // 為防止輸入長度不足七碼

                if (accountF.length() >= 3)
                    caseObj.setPayBankId(accountF.substring(0, 3));
                else
                    caseObj.setPayBankId(accountF.substring(0, accountF.length()));

                if (accountF.length() >= 4)
                    caseObj.setBranchId(accountF.substring(3, accountF.length()));
                else
                    caseObj.setBranchId("");
                // caseObj.setPayBankId(accountF.substring(0, 3));
                // caseObj.setBranchId(accountF.substring(3, 7));
                caseObj.setPayEeacc(accountB);
                // 「給付方式」<>5、6時,「匯款匯費(BAAPPBASE.MITRATE)」=0
                caseObj.setMitRate(new BigDecimal(0));
            }
            // 當「給付方式」(本人領取)=5、6時，「金融機構名稱」=【BAMO0D021C】畫面中的「金融機構名稱」(已存在case中)
            // 「金融機構總代號」=「帳號」(1~3碼)
            // 「分支代號」=「帳號」(4~7碼)
            // 「銀行帳號」=「帳號」(8~21碼)
            // 「戶名」=【BAMO0D021C】畫面中的「戶名」(已存在case中)
            else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(payTyp)) {
                String account = caseObj.getPayAccount();
                caseObj.setAccIdn(caseObj.getBenIdnNo());
                // caseObj.setAccName(caseObj.getBenName());

                if (account.length() > 7) {
                    caseObj.setPayBankId(account.substring(0, 3));
                    caseObj.setBranchId(account.substring(3, 7));
                    caseObj.setPayEeacc(account.substring(7, account.length()));
                }
                else if (account.length() > 3 && account.length() <= 7) {
                    caseObj.setPayBankId(account.substring(0, 3));
                    caseObj.setBranchId(account.substring(3, account.length()));
                    caseObj.setPayEeacc("");
                }
                else {
                    caseObj.setPayBankId(account.substring(0, account.length()));
                    caseObj.setBranchId("");
                    caseObj.setPayEeacc("");
                }
                // 匯款匯費
                String mitRate = getMitRateData(caseObj.getPayTyp());
                if (StringUtils.isNotBlank(mitRate)) {
                    caseObj.setMitRate(new BigDecimal(mitRate));
                }
            }
            // 當「給付方式」(本人領取)=3、4、9、A時，「金融機構名稱」= ' '
            // 「金融機構總代號」= ' '
            // 「分支代號」= ' '
            // 「銀行帳號」= ' '
            // 「戶名」=「受款人姓名」

            else if ((ConstantKey.BAAPPBASE_PAYTYP_3).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_4).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_9).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_A).equals(payTyp)) {
                caseObj.setBankName("");
                caseObj.setPayBankId("");
                caseObj.setBranchId("");
                caseObj.setPayEeacc("");
                caseObj.setAccIdn("");
                caseObj.setAccName("");
                // 「給付方式」<>5、6時,「匯款匯費(BAAPPBASE.MITRATE)」=0
                caseObj.setMitRate(new BigDecimal(0));
            }
        }
        // 具名領取
        else if (("2").equals(caseObj.getPayCategory())) {
            caseObj.setAccRel("3"); // 戶名與受益人關係
            // 取得具名領取相關資料
            // Baappbase beforBaappbase = baappbaseDao.selectDetailDataBy(caseObj.getBaappbaseId(), null, caseObj.getBenIdnNo(), null);
            Baappbase baappbase = baappbaseDao.selectBankDataByAccSeqNo(caseObj.getApNo(), caseObj.getAccSeqNo());
            if (baappbase != null) {
                caseObj.setPayTyp(baappbase.getPayTyp());
                caseObj.setBankName(baappbase.getBankName());
                caseObj.setPayBankId(baappbase.getPayBankId());
                caseObj.setBranchId(baappbase.getBranchId());
                caseObj.setPayEeacc(baappbase.getPayEeacc());
                caseObj.setAccIdn(baappbase.getAccIdn());
                caseObj.setAccName(baappbase.getAccName());
                caseObj.setMitRate(baappbase.getMitRate());
                caseObj.setInterValMonth(baappbase.getInterValMonth());
            }
            else {
                caseObj.setPayTyp("");
                caseObj.setBankName("");
                caseObj.setPayBankId("");
                caseObj.setBranchId("");
                caseObj.setPayEeacc("");
                caseObj.setAccIdn("");
                caseObj.setAccName("");
                caseObj.setMitRate(new BigDecimal(0));
                caseObj.setInterValMonth("");
            }
        }

        // 取得受款人社福識別碼
        List<Cvldtl> cvldtl = cvldtlDao.selectDataBy(caseObj.getBenIdnNo(), caseObj.getBenBrDate());
        if (cvldtl.size() > 0) {
            caseObj.setBenIds(cvldtl.get(0).getNpIds());
        }
        else {
            caseObj.setBenIds("");
        }

        // 「通訊地址別」=1時，「通訊郵遞區號」、「通訊地址」=戶籍 , 「通訊地址別」=2時，「通訊郵遞區號」、「通訊地址」=畫面欄位
        if (caseObj.getCommTyp().equals("1")) {
            if (cvldtl.size() > 0) {
                caseObj.setCommZip(cvldtl.get(0).getCzip());
                caseObj.setCommAddr(cvldtl.get(0).getCaddr());
            }
        }

        // 國籍,性別
        if (caseObj.getBenNationTyp().equals("1")) {
            caseObj.setBenNationCode("000");
            if (caseObj.getSeqNo().equals("0000")) {
                if (StringUtils.isNotBlank(caseObj.getBenIdnNo())) {
                    caseObj.setEvtSex(caseObj.getBenIdnNo().substring(1, 2));
                    caseObj.setBenSex(caseObj.getBenIdnNo().substring(1, 2));
                }

            }
            else {
                if (StringUtils.isNotBlank(caseObj.getBenIdnNo())) {
                    caseObj.setBenSex(caseObj.getBenIdnNo().substring(1, 2));
                }

            }

        }

        // 戶籍地址
        if (caseObj.getCommTyp().equals("1")) {
            // 郵遞區號

            caseObj.setCommZip(getCvldtlZip(caseObj.getBenIdnNo(), caseObj.getBenBrDate()));
            // 地址
            caseObj.setCommAddr(getCvldtlAddr(caseObj.getBenIdnNo(), caseObj.getBenBrDate()));
        }
        // 可領金額
        caseObj.setMustIssueAmt(new BigDecimal(0));

        return caseObj;
    }

    /**
     * 依畫面輸入欄位條件轉換 新增/修改 給付主檔 部分欄位<br>
     * 遺屬年金受款人資料更正 使用
     * 
     * @param caseObj
     * @param caseData
     * @param type 是新增還是修改, I = 新增, U = 修改, II = 新增繼承人
     * @return SurvivorPayeeDataUpdateCase
     */
    public SurvivorPayeeDataUpdateCase transSurvivorPayeeUpdateData(SurvivorPayeeDataUpdateCase caseObj, Baappbase caseData, String type) {
        // 以下是相同的欄位
        caseObj.setApNo(caseData.getApNo());

        // 關係是本人時,姓名出生日期身份證號Disabled,所以存檔時要補資料
        if (type.equals("U") && caseObj.getBenEvtRel().equals("1")) {
            caseObj.setBenName(caseData.getBenName());
            caseObj.setBenIdnNo(caseData.getBenIdnNo());
            caseObj.setBenBrDate(caseData.getBenBrDate());
        }

        // 變更日期為西元年
        /*
         * if (caseObj.getBenEvtRel().equals("2") || caseObj.getBenEvtRel().equals("3") || caseObj.getBenEvtRel().equals("4") || caseObj.getBenEvtRel().equals("5") || caseObj.getBenEvtRel().equals("6") || caseObj.getBenEvtRel().equals("7")) { if
         * (StringUtils.defaultString(caseObj.getAppDate()).trim().length() == 7) caseObj.setAppDate(DateUtility.changeDateType(caseObj.getAppDate())); } else { if (type.equals("I") && !caseObj.getBenEvtRel().equals("Z")) caseObj.setAppDate(""); //
         * 新增時繼承人申請日期,關係為A~Z為空值 else if (type.equals("I") && caseObj.getBenEvtRel().equals("Z")) caseObj.setAppDate(DateUtility.getNowWestDate()); else caseObj.setAppDate(caseData.getAppDate()); // 修改時則不變更原值 }
         */

        // 新增受款人時，處理SEQNO
        if (type.equals("I")) {
            if (caseObj.getBenEvtRel().equals("2") || caseObj.getBenEvtRel().equals("3") || caseObj.getBenEvtRel().equals("4") || caseObj.getBenEvtRel().equals("5") || caseObj.getBenEvtRel().equals("6") || caseObj.getBenEvtRel().equals("7")
                            || caseObj.getBenEvtRel().equals("O")) {
                if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 4) {
                    // 當「繼承自受款人」= '0000'
                    caseObj.setSeqNo(baappbaseDao.getPayeeDataForSeqNoOne(caseData.getApNo()));
                }
                else if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 3) {
                    // 當「繼承自受款人」= '0x00'
                    caseObj.setSeqNo(baappbaseDao.getPayeeDataForSeqNoTwo(caseData.getApNo()));
                }
                else if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 2) {
                    // 當「繼承自受款人」= '0xx0'
                    caseObj.setSeqNo(baappbaseDao.getPayeeDataForSeqNoThree(caseData.getApNo()));
                }
            }
        }
        else if (type.equals("II")) {
            if (caseObj.getBenEvtRel().equals("2") || caseObj.getBenEvtRel().equals("3") || caseObj.getBenEvtRel().equals("4") || caseObj.getBenEvtRel().equals("5") || caseObj.getBenEvtRel().equals("6") || caseObj.getBenEvtRel().equals("7")
                            || caseObj.getBenEvtRel().equals("O")) {
                caseObj.setSeqNo(baappbaseDao.getDisabledPayeeHeirSeqNo(caseData.getApNo(), caseData.getSeqNo()));
            }
        }

        // 本人領取
        if (("1").equals(caseObj.getPayCategory())) {
            caseObj.setAccRel("1"); // 戶名與受益人關係
            caseObj.setAccSeqNo(caseObj.getSeqNo());// 2009.09.17AccSeqNo設為自己的SeqNo
            String payTyp = caseObj.getPayTyp();
            // 根據給付方式, 轉換「金融機構名稱」,「金融機構總代號」,「分支代號」,「銀行帳號」

            // 「給付方式」(本人領取)=1、8時

            // 「金融機構名稱」=「金融機構名稱一」+「金融機構名稱二」

            // 「金融機構總代號」=「帳號」(前：1~3碼)
            // 「戶名」=【BAMO0D021C】畫面中的「戶名」(已存在case中)
            if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_8).equals(payTyp)) {
                String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
                String accountB = caseObj.getPayEeacc();// 帳號(後)
                caseObj.setBankName(getBankName(accountF));// 金融機構名稱
                // 為防止輸入長度不足七碼

                caseObj.setAccIdn(caseObj.getBenIdnNo());

                if (accountF.length() >= 3)
                    caseObj.setPayBankId(accountF.substring(0, 3));
                else
                    caseObj.setPayBankId(accountF.substring(0, accountF.length()));
                if (accountF.length() >= 4)
                    caseObj.setBranchId(accountF.substring(3, accountF.length()));
                else
                    caseObj.setBranchId("");
                // caseObj.setPayBankId(accountF.substring(0, 3));// 金融機構總代號

                // caseObj.setBranchId(accountF.substring(3, 7));
                if (StringUtils.isNotBlank(accountB)) {
                    if (accountB.length() < 14) {
                        accountB = StringUtils.leftPad(accountB, 14, '0');
                    }
                }
                caseObj.setPayEeacc(accountB);
                // 「給付方式」<>5、6時,「匯款匯費(BAAPPBASE.MITRATE)」=0
                caseObj.setMitRate(new BigDecimal(0));
            }
            // 「給付方式」=2時，「金融機構名稱」= 郵局名稱
            // 「金融機構總代號」= '700'
            // 「分支代號」= '0021'
            // 「銀行帳號」=「帳號」(後)
            // 「戶名」=【BAMO0D021C】畫面中的「戶名」(已存在case中)
            else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(payTyp)) {
                String accountF = caseObj.getPayBankIdBranchId();// 帳號(前)
                String accountB = caseObj.getPayEeacc();// 帳號(後)
                caseObj.setBankName(getPostName(caseObj.getPayBankIdBranchId()));
                // caseObj.setPayBankId(ConstantKey.BAAPPBASE_PAYTYP_2_PAYBANKID);
                // caseObj.setBranchId(ConstantKey.BAAPPBASE_PAYTYP_2_BRANCHID);
                // 為防止輸入長度不足七碼

                caseObj.setAccIdn(caseObj.getBenIdnNo());
                if (accountF.length() >= 3)
                    caseObj.setPayBankId(accountF.substring(0, 3));
                else
                    caseObj.setPayBankId(accountF.substring(0, accountF.length()));

                if (accountF.length() >= 4)
                    caseObj.setBranchId(accountF.substring(3, accountF.length()));
                else
                    caseObj.setBranchId("");
                // caseObj.setPayBankId(accountF.substring(0, 3));
                // caseObj.setBranchId(accountF.substring(3, 7));
                caseObj.setPayEeacc(accountB);
                // 「給付方式」<>5、6時,「匯款匯費(BAAPPBASE.MITRATE)」=0
                caseObj.setMitRate(new BigDecimal(0));
            }
            // 當「給付方式」(本人領取)=5、6時，「金融機構名稱」=【BAMO0D021C】畫面中的「金融機構名稱」(已存在case中)
            // 「金融機構總代號」=「帳號」(1~3碼)
            // 「分支代號」=「帳號」(4~7碼)
            // 「銀行帳號」=「帳號」(8~21碼)
            // 「戶名」=【BAMO0D021C】畫面中的「戶名」(已存在case中)
            else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_6).equals(payTyp)) {
                String account = caseObj.getPayAccount();
                caseObj.setAccIdn(caseObj.getBenIdnNo());
                // caseObj.setAccName(caseObj.getBenName());
                if (account.length() > 7) {
                    caseObj.setPayBankId(account.substring(0, 3));
                    caseObj.setBranchId(account.substring(3, 7));
                    caseObj.setPayEeacc(account.substring(7, account.length()));
                }
                else if (account.length() > 3 && account.length() <= 7) {
                    caseObj.setPayBankId(account.substring(0, 3));
                    caseObj.setBranchId(account.substring(3, account.length()));
                    caseObj.setPayEeacc("");
                }
                else {
                    caseObj.setPayBankId(account.substring(0, account.length()));
                    caseObj.setBranchId("");
                    caseObj.setPayEeacc("");
                }
                // 匯款匯費
                String mitRate = getMitRateData(caseObj.getPayTyp());
                if (StringUtils.isNotBlank(mitRate)) {
                    caseObj.setMitRate(new BigDecimal(mitRate));
                }
            }
            // 當「給付方式」(本人領取)=3、4、9、A時，「金融機構名稱」= ' '
            // 「金融機構總代號」= ' '
            // 「分支代號」= ' '
            // 「銀行帳號」= ' '
            // 「戶名」=「受款人姓名」

            else if ((ConstantKey.BAAPPBASE_PAYTYP_3).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_4).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_9).equals(payTyp) || (ConstantKey.BAAPPBASE_PAYTYP_A).equals(payTyp)) {
                caseObj.setBankName("");
                caseObj.setPayBankId("");
                caseObj.setBranchId("");
                caseObj.setPayEeacc("");
                caseObj.setAccIdn("");
                caseObj.setAccName("");
                // 「給付方式」<>5、6時,「匯款匯費(BAAPPBASE.MITRATE)」=0
                caseObj.setMitRate(new BigDecimal(0));
            }
        }
        // 具名領取
        else if (("2").equals(caseObj.getPayCategory())) {
            caseObj.setAccRel("3"); // 戶名與受益人關係
            // 取得具名領取相關資料
            // Baappbase beforBaappbase = baappbaseDao.selectDetailDataBy(caseObj.getBaappbaseId(), null, caseObj.getBenIdnNo(), null);
            Baappbase baappbase = baappbaseDao.selectBankDataByAccSeqNo(caseObj.getApNo(), caseObj.getAccSeqNo());
            if (baappbase != null) {
                caseObj.setPayTyp(baappbase.getPayTyp());
                caseObj.setBankName(baappbase.getBankName());
                caseObj.setPayBankId(baappbase.getPayBankId());
                caseObj.setBranchId(baappbase.getBranchId());
                caseObj.setPayEeacc(baappbase.getPayEeacc());
                caseObj.setAccIdn(baappbase.getAccIdn());
                caseObj.setAccName(baappbase.getAccName());
                caseObj.setMitRate(baappbase.getMitRate());
                caseObj.setInterValMonth(baappbase.getInterValMonth());
            }
            else {
                caseObj.setPayTyp("");
                caseObj.setBankName("");
                caseObj.setPayBankId("");
                caseObj.setBranchId("");
                caseObj.setPayEeacc("");
                caseObj.setAccIdn("");
                caseObj.setAccName("");
                caseObj.setMitRate(new BigDecimal(0));
                caseObj.setInterValMonth("");
            }
        }

        // 取得受款人社福識別碼
        List<Cvldtl> cvldtl = cvldtlDao.selectDataBy(caseObj.getBenIdnNo(), caseObj.getBenBrDate());
        if (cvldtl.size() > 0) {
            caseObj.setBenIds(cvldtl.get(0).getNpIds());
        }
        else {
            caseObj.setBenIds("");
        }

        // 「通訊地址別」=1時，「通訊郵遞區號」、「通訊地址」=戶籍 , 「通訊地址別」=2時，「通訊郵遞區號」、「通訊地址」=畫面欄位
        if (caseObj.getCommTyp().equals("1")) {
            if (cvldtl.size() > 0) {
                caseObj.setCommZip(cvldtl.get(0).getCzip());
                caseObj.setCommAddr(cvldtl.get(0).getCaddr());
            }
        }

        // 國籍
        if (caseObj.getBenNationTyp().equals("1")) {
            caseObj.setBenNationCode("000");
            if (caseObj.getSeqNo().equals("0000")) { // 遺屬應該不可能跑到這段
                if (StringUtils.isNotBlank(caseObj.getBenIdnNo())) {
                    caseObj.setBenSex(caseObj.getBenIdnNo().substring(1, 2));
                }
                if (StringUtils.isNotBlank(caseObj.getEvtIdnNo())) {
                    caseObj.setEvtSex(caseObj.getEvtIdnNo().substring(1, 2));
                }
            }
            else {
                if (StringUtils.isNotBlank(caseObj.getBenIdnNo())) {
                    caseObj.setBenSex(caseObj.getBenIdnNo().substring(1, 2));
                }
            }
        }

        // 戶籍地址
        if (caseObj.getCommTyp().equals("1")) {
            // 郵遞區號

            caseObj.setCommZip(getCvldtlZip(caseObj.getBenIdnNo(), caseObj.getBenBrDate()));
            // 地址
            caseObj.setCommAddr(getCvldtlAddr(caseObj.getBenIdnNo(), caseObj.getBenBrDate()));
        }
        // 可領金額
        caseObj.setMustIssueAmt(new BigDecimal(0));

        return caseObj;
    }

    /**
     * 刪除 失能年金受款人資料
     * 
     * @param baappbase 給付主檔
     */
    public void deleteDisabledPayeeDataUpdate(UserBean userData, Baappbase caseData) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(caseData.getApNo());

        // 更新資料到 給付主檔
        // [
        log.debug("Start delete BAAPPBASE ...");
        List<Bachkfile> beforeBachkfileList = new ArrayList<Bachkfile>(); // 給付編審檔改前值

        List<Baappbase> beforeBaappbaseList = new ArrayList<Baappbase>(); // 給付主檔刪除前值

        Baappbase beforeBaappbase = new Baappbase(); // 給付主檔改前值

        BeanUtility.copyProperties(beforeBaappbase, caseData);

        // 刪除SEQNO該階層底下之相關資料
        if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 3) {

            // 「序號」= 0X00
            beforeBachkfileList = bachkfileDao.selectBachkfileDataByLog(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));
            beforeBaappbaseList = baappbaseDao.selectBaappbaseDataByLog(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));
            bachkfileDao.deleteBachkfileData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));
            badaprDao.deleteBadaprData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));
            baappbaseDao.deleteBaappbaseData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));

        }
        else if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 2) {
            // 「序號」= 0XX0
            beforeBachkfileList = bachkfileDao.selectBachkfileDataByLog(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 3));
            beforeBaappbaseList = baappbaseDao.selectBaappbaseDataByLog(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 3));
            bachkfileDao.deleteBachkfileData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 3));
            badaprDao.deleteBadaprData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));
            baappbaseDao.deleteBaappbaseData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 3));

        }
        else if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 1) {
            // 「序號」= 0XXX
            beforeBachkfileList = bachkfileDao.selectBachkfileDataByLog(caseData.getApNo(), caseData.getSeqNo());
            beforeBaappbaseList = baappbaseDao.selectBaappbaseDataByLog(caseData.getApNo(), caseData.getSeqNo());
            bachkfileDao.deleteBachkfileData(caseData.getApNo(), caseData.getSeqNo());
            badaprDao.deleteBadaprData(caseData.getApNo(), caseData.getSeqNo());
            baappbaseDao.deleteBaappbaseData(caseData.getApNo(), caseData.getSeqNo());

        }

        bachkcontrlDao.deleteDataBySeqNo(caseData.getApNo(), caseData.getSeqNo());
        // 相關要修改的主檔清單
        List<BigDecimal> payTypeList = new ArrayList<BigDecimal>();
        List<BigDecimal> benEvtRelList = new ArrayList<BigDecimal>();
        List<BigDecimal> procstatList = new ArrayList<BigDecimal>();
        // 給付資料更正改前物件List
        List<Baappbase> beforeList = new ArrayList<Baappbase>();
        // 給付資料更正改後物件List
        List<Baappbase> afterList = new ArrayList<Baappbase>();
        // 正在修改資料該筆改前改後值List for BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        // 其他需要同步資料的改前改後值List for BAAPPLOG
        List<BaappbaseDataUpdateCase> baapplogList2 = new ArrayList<BaappbaseDataUpdateCase>();

        if (("1").equals(caseData.getBenEvtRel())) { // 本人
            benEvtRelList = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());
            for (int i = 0; i < benEvtRelList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) benEvtRelList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

            }

        }
        else { // 其他親屬
            procstatList = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());

            for (int i = 0; i < procstatList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) procstatList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

            }
        }

        log.debug("delete BAAPPBASE Finished ...");
        // ]

        // [
        log.debug("Start update BAAPPBASE ...");
        // caseData.setUpdUser(userData.getEmpNo());// 異動人員
        // caseData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        // // 更新PROCSTAT='01'
        // baappbaseDao.updateProcstatForPayeeDataUpdate(caseData);
        log.debug("update BAAPPBASE Finished ...");
        // ]

        if (("1").equals(caseData.getBenEvtRel())) { // 本人
            benEvtRelList = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());
            for (int i = 0; i < benEvtRelList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) benEvtRelList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase afterObj = new Baappbase();// 改後值物件

                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(afterObj, extModifyObj);
                    afterList.add(afterObj);
                }

            }

        }
        else { // 其他親屬
            procstatList = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());

            for (int i = 0; i < procstatList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) procstatList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase afterObj = new Baappbase();// 改後值物件

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(afterObj, extModifyObj);
                    afterList.add(afterObj);
                }

            }
        }

        List<Baappbase> updateBaappbaseList = baappbaseDao.selectDependantDataList(caseData.getApNo());
        Baappbase afterBaappbase = new Baappbase();
        for (Baappbase updateBaappbase : updateBaappbaseList) {
            BeanUtility.copyProperties(afterBaappbase, updateBaappbase);
            afterBaappbase.setProcStat("01");
            afterBaappbase.setUpdTime(DateUtility.getNowWestDateTime(true));
            afterBaappbase.setUpdUser(userData.getEmpNo());
            afterBaappbase.setChkDate("");
            afterBaappbase.setChkMan("");
            afterBaappbase.setRechkDate("");
            afterBaappbase.setRechkMan("");
            afterBaappbase.setExeDate("");
            afterBaappbase.setExeMan("");
            baappbaseDao.updateDataForDependant(afterBaappbase);
            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(updateBaappbase, afterBaappbase);
            }
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteListLog(beforeBachkfileList);
            FrameworkLogUtil.doDeleteListLog(beforeBaappbaseList);
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        }

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");

        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(caseData.getBaappbaseId());// 資料列編號

        baapplog.setStatus("D");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplog.setUpCol(); // 異動項目 - Log 已設
        // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
        // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
        baapplogDao.insertData(baapplog);

        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 刪除 遺屬年金受款人資料
     * 
     * @param baappbase 給付主檔
     */
    public void deleteSurvivorPayeeDataUpdate(UserBean userData, Baappbase caseData) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(caseData.getApNo());

        // 據說DB有設Cascade,所以砍主檔就好
        // 刪除給付主檔資料
        // [
        log.debug("Start delete BAAPPBASE ...");

        List<Baappbase> beforeBaappbseList = baappbaseDao.selectBaappbaseDataByLog(caseData.getApNo(), caseData.getSeqNo());
        List<Bastudterm> beforStudtermList = bastudtermDao.selectStudtermListForSurvivorPayeeDataUpdate(caseData.getApNo(), caseData.getSeqNo());
        List<Bacompelnopay> beforCompelList = bacompelnopayDao.selectDataBy(caseData.getApNo(), caseData.getSeqNo());
        // 刪除SEQNO該階層底下之相關資料
        /*
         * if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 3) { // 「序號」= 0X00 baappbaseDao.deleteBaappbaseData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2)); } else if (StringUtils.countMatches(caseData.getSeqNo(),
         * "0") == 2) { // 「序號」= 0XX0 baappbaseDao.deleteBaappbaseData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 3)); } else if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 1) { // 「序號」= 0XXX
         * baappbaseDao.deleteBaappbaseData(caseData.getApNo(), caseData.getSeqNo()); }
         */
        baappbaseDao.deleteBaappbaseData(caseData.getApNo(), caseData.getSeqNo());
        bastudtermDao.deleteBastudterm(caseData.getApNo(), caseData.getSeqNo());
        bachkcontrlDao.deleteDataBySeqNo(caseData.getApNo(), caseData.getSeqNo());
        bacompelnopayDao.deleteData(caseData.getApNo(), caseData.getSeqNo());

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteListLog(beforeBaappbseList);
            FrameworkLogUtil.doDeleteListLog(beforStudtermList);
            FrameworkLogUtil.doDeleteListLog(beforStudtermList);
        }

        log.debug("Delete BAAPPBASE Finished ...");
        // ]

        // [
        log.debug("Start update BAAPPBASE ...");
        // Baappbase beforeBaappbase = new Baappbase();
        // BeanUtility.copyProperties(beforeBaappbase, caseData);
        // caseData.setUpdUser(userData.getEmpNo());// 異動人員
        // caseData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        // // 更新PROCSTAT='01'
        // baappbaseDao.updateProcstatForPayeeDataUpdate(caseData);
        //
        // // Insert MMAPLOG
        // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
        // // 紀錄 Log
        // FrameworkLogUtil.doUpdateLog(beforeBaappbase, caseData);
        // }

        List<Baappbase> updateBaappbaseList = baappbaseDao.selectDependantDataList(caseData.getApNo());
        Baappbase afterBaappbase = new Baappbase();
        for (Baappbase updateBaappbase : updateBaappbaseList) {
            BeanUtility.copyProperties(afterBaappbase, updateBaappbase);
            afterBaappbase.setProcStat("01");
            afterBaappbase.setUpdTime(DateUtility.getNowWestDateTime(true));
            afterBaappbase.setUpdUser(userData.getEmpNo());
            afterBaappbase.setChkDate("");
            afterBaappbase.setChkMan("");
            afterBaappbase.setRechkDate("");
            afterBaappbase.setRechkMan("");
            afterBaappbase.setExeDate("");
            afterBaappbase.setExeMan("");
            baappbaseDao.updateDataForDependant(afterBaappbase);
            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(updateBaappbase, afterBaappbase);
            }
        }
        log.debug("update BAAPPBASE Finished ...");
        // ]

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(caseData.getBaappbaseId());// 資料列編號

        baapplog.setStatus("D");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        baapplogDao.insertData(baapplog);

        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 新增 給付主檔 for 失能年金受款人資料更正作業
     * 
     * @param baappbase 給付主檔
     */
    public void insertBaappbaseDataForDisabledPayeeData(UserBean userData, BaappbaseDataUpdateCase queryData, BaappbaseDataUpdateCase baappbaseData, String interValMonth) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(baappbaseData.getApNo());

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAAPPBASE ...");
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, baappbaseData);
        baappbase.setImk(queryData.getImk());// 保險別

        baappbase.setApItem(queryData.getApItem());
        baappbase.setPayKind(queryData.getPayKind());// 給付種類
        baappbase.setCaseTyp(queryData.getCaseTyp());// 案件類別
        baappbase.setMapNo(queryData.getMapNo());
        baappbase.setMapRootMk(queryData.getMapRootMk());
        baappbase.setCombapMark(queryData.getCombapMark());
        baappbase.setProcStat(queryData.getProcStat());// 處理狀態

        baappbase.setApUbno(queryData.getApUbno());
        baappbase.setEvtIds(queryData.getEvtIds());
        baappbase.setEvtIdnNo(queryData.getEvtIdnNo());
        baappbase.setEvtName(queryData.getEvtName());
        baappbase.setEvtBrDate(queryData.getEvtBrDate());
        baappbase.setEvtSex(queryData.getEvtSex());
        baappbase.setEvtNationTpe(queryData.getEvtNationTpe());
        baappbase.setEvtNationCode(queryData.getEvtNationCode());
        baappbase.setEvtIdent(queryData.getEvtIdent());// 事故者身分別
        baappbase.setEvtJobDate(queryData.getEvtJobDate());
        baappbase.setBenPayMk("1");
        baappbase.setChgMk("Y");// 更正註記
        baappbase.setCrtUser(userData.getEmpNo());// 新增者代號

        baappbase.setAppDate(baappbaseData.getAppDate());
        baappbase.setOldAplDpt(baappbaseData.getOldAplDpt()); // 申請代算單位
        baappbase.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
        // baappbase.setCloseCause(baappbaseData.getCloseCause());//結案原因

        baappbase.setInterValMonth(interValMonth); // 間隔月份

        // 新增給付主檔資料
        BigDecimal baappbaseId = baappbaseDao.insertDataForDisabledPayeeDataUpdate(baappbase);
        // 新增給付延伸主檔資料
        Baappexpand expand = new Baappexpand();
        expand.setApNo(baappbase.getApNo());
        expand.setSeqNo(baappbase.getSeqNo());
        expand.setBaappbaseId(baappbaseId);
        expand.setCrtTime(DateUtility.getNowWestDateTime(true));
        expand.setCrtUser(userData.getEmpNo());
        baappexpandDao.insertDataForDisabledPayeeDataUpdate(expand);

        // Insert MMAPLOG
        baappbase.setBaappbaseId(baappbaseId);
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baappbase);
            FrameworkLogUtil.doInsertLog(expand);
        }
        log.debug("Insert BAAPPBASE Finished ...");
        // ]

        // 判斷需要UPDATE的相關資料

        // 相關要修改的主檔清單
        List<BigDecimal> payTypeList = new ArrayList<BigDecimal>();
        List<BigDecimal> benEvtRelList = new ArrayList<BigDecimal>();
        List<BigDecimal> procstatList = new ArrayList<BigDecimal>();
        // 給付資料更正改前物件List
        List<Baappbase> beforeList = new ArrayList<Baappbase>();
        // 給付資料更正改後物件List
        List<Baappbase> afterList = new ArrayList<Baappbase>();
        // 正在修改資料該筆改前改後值List for BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        // 其他需要同步資料的改前改後值List for BAAPPLOG
        List<BaappbaseDataUpdateCase> baapplogList2 = new ArrayList<BaappbaseDataUpdateCase>();
        Baappbase beforeBaappbase = new Baappbase();

        if (("1").equals(baappbaseData.getBenEvtRel())) { // 本人
            benEvtRelList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());
            for (int i = 0; i < benEvtRelList.size(); i++) {
                baappbaseId = (BigDecimal) benEvtRelList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);
                extModifyObj.setEvtIds(baappbaseData.getBenIds()); // 受款人社福識別碼
                extModifyObj.setEvtIdnNo(baappbaseData.getBenIdnNo()); // 受款人事故者身分證號

                extModifyObj.setEvtName(baappbaseData.getBenName()); // 受款人事故者姓名

                extModifyObj.setEvtBrDate(baappbaseData.getBenBrDate()); // 受款人事故者出生日期

                extModifyObj.setEvtSex(baappbaseData.getBenSex()); // 性別
                extModifyObj.setEvtNationTpe(baappbaseData.getBenNationTyp()); // 國籍別

                extModifyObj.setEvtNationCode(baappbaseData.getBenNationCode()); // 國籍
                extModifyObj.setUpdUser(userData.getEmpNo());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                extModifyObj.setProcStat("01");
                // 2011.07.29 修改
                // interValMonth
                extModifyObj.setInterValMonth(interValMonth); // 間隔年月
                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForPayeeData(obj);
                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && (obj.getBaappbaseId()).equals(beforeBaappbase.getBaappbaseId())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                }
            }

            // Insert MMAPLOG
            // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // // 紀錄 Log
            // FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
            // }
        }
        else { // 其他親屬
            procstatList = baappbaseDao.selectBaappbaseIdBy(baappbaseData.getApNo());

            for (int i = 0; i < procstatList.size(); i++) {
                baappbaseId = (BigDecimal) procstatList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);
                extModifyObj.setProcStat("01");
                extModifyObj.setUpdUser(userData.getEmpNo());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForPayeeDataAll(obj);
                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()) && (obj.getBaappbaseId()).equals(beforeBaappbase.getBaappbaseId())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateLog(beforeBaappbase, obj);
                }
            }

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
            }
        }

        // 新增資料到 更正記錄檔

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
     * 取得匯款匯費 for 遺屬年金受款人資料更正作業
     * 
     * @pramati paramCode
     */

    public BigDecimal getParamNameForSurviorPayeeData(String paramCode) {
        return baparamDao.getParaNameData(paramCode);
    }

    /**
     * 新增 給付主檔 for 遺屬年金受款人資料更正作業 queryData = 主檔 baappbaseData
     * 
     * @param baappbase 給付主檔
     */
    public void insertDataForSurvivorPayeeData(UserBean userData, SurvivorPayeeDataUpdateCase queryData, SurvivorPayeeDataUpdateCase caseData, List<Bastudterm> bastudtermList, String interValMonth,
                    List<SurvivorPayeeDataUpdateCompelDataCase> compelDataList) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(caseData.getApNo());

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAAPPBASE ...");
        Baappbase baappbase = new Baappbase();
        BeanUtility.copyProperties(baappbase, caseData);
        baappbase.setImk(queryData.getImk());// 保險別

        baappbase.setApItem(queryData.getApItem());
        baappbase.setPayKind(queryData.getPayKind());// 給付種類
        baappbase.setCaseTyp(queryData.getCaseTyp());// 案件類別
        baappbase.setMapNo(queryData.getMapNo());
        baappbase.setMapRootMk(queryData.getMapRootMk());
        baappbase.setCombapMark(queryData.getCombapMark());
        baappbase.setProcStat(queryData.getProcStat());// 處理狀態
        baappbase.setApUbno(queryData.getApUbno());
        baappbase.setEvtIds(queryData.getEvtIds());
        baappbase.setEvtIdnNo(queryData.getEvtIdnNo());
        baappbase.setEvtName(queryData.getEvtName());
        baappbase.setEvtBrDate(queryData.getEvtBrDate());
        baappbase.setEvtSex(queryData.getEvtSex());
        baappbase.setEvtNationTpe(queryData.getEvtNationTpe());
        baappbase.setEvtNationCode(queryData.getEvtNationCode());
        baappbase.setEvtIdent(queryData.getEvtIdent());// 事故者身分別
        baappbase.setEvtJobDate(queryData.getEvtJobDate());
        baappbase.setCloseCause(caseData.getCloseCause());
        baappbase.setBenPayMk("1");
        baappbase.setChgMk("Y");// 更正註記
        baappbase.setCrtUser(userData.getEmpNo());// 新增者代號
        baappbase.setAppDate(caseData.getAppDate());
        baappbase.setOldAplDpt(caseData.getOldAplDpt()); // 申請代算單位
        baappbase.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間
        baappbase.setUnqualifiedCause(caseData.getUnqualifiedCause());

        // 2011.07.29 儲存INTERVALMONTH的值
        if (!interValMonth.equals("N")) {
            baappbase.setInterValMonth(interValMonth); // 間隔月份
        }

        // 新增給付主檔資料
        BigDecimal baappbaseId = baappbaseDao.insertDataForSurvivorPayeeInheritData(baappbase);

        // 新增資料到 給付延伸主檔
        Baappexpand expand = new Baappexpand();
        expand.setBaappbaseId(baappbaseId);
        expand.setApNo(caseData.getApNo());
        expand.setSeqNo(caseData.getSeqNo());
        expand.setMonIncomeMk(caseData.getMonIncomeMk());
        /*
         * String monIncome = caseData.getMonIncome(); if (StringUtils.isBlank(caseData.getMonIncome()) || "0".equals(monIncome)) { monIncome = "0"; }
         */
        if (StringUtils.isNotBlank(caseData.getMonIncome())) {
            expand.setMonIncome(new BigDecimal(caseData.getMonIncome()));
        }
        expand.setMarryDate(caseData.getMarryDate());
        expand.setDigamyDate(caseData.getDigaMyDate());
        expand.setHandIcapMk(caseData.getHandicapMk());
        expand.setCompelMk(caseData.getCompelMk());
        expand.setStudMk(caseData.getStudMk());
        expand.setAbanApplyMk(caseData.getAbanApplyMk());
        expand.setAbanApsYm(caseData.getAbanApSym());
        expand.setAbleApsYm(caseData.getAbleApsYm());
        expand.setRaiseChildMk(caseData.getRaiseChildMk());
        expand.setInterDictMk(caseData.getInterdictMk());
        expand.setInterDictDate(caseData.getInterdictDate());
        expand.setRepealInterdictDate(caseData.getRepealInterdictDate());
        expand.setBenMissingSdate(caseData.getBenMissingSdate());
        expand.setBenMissingEdate(caseData.getBenMissingEdate());
        expand.setPrisonSdate(caseData.getPrisonSdate());
        expand.setPrisonEdate(caseData.getPrisonEdate());
        expand.setRelatChgDate(caseData.getRelatChgDate());
        expand.setAdoPtDate(caseData.getAdoptDate());
        expand.setAssignIdnNo(caseData.getAssignIdnNo());
        expand.setAssignName(caseData.getAssignName());
        expand.setAssignBrDate(caseData.getAssignBrDate());
        expand.setRaiseEvtMk(caseData.getRaiseEvtMk());
        expand.setSavingMk(caseData.getSavingMk());
        expand.setCrtUser(userData.getEmpNo());
        expand.setCrtTime(DateUtility.getNowWestDateTime(true));
        expand.setSchoolCode(caseData.getSchoolCode());
        BigDecimal baappexpandId = baappexpandDao.insertDataForSurvivorPayeeDataUpdate(expand);

        // 新增不合格年月資料,先把整個table的不合格年月資料delete掉,再insert上去
        // 直接砍掉所有與該受款人相關的不合格年月資料
        // 先檢查強迫不合格註記,若為Y才insert compelData list,若為N表示未強迫不合格
        // 則不新增
        bacompelnopayDao.deleteData(baappbase.getApNo(), baappbase.getSeqNo());
        if ("Y".equals(expand.getCompelMk())) {
            if (compelDataList != null && !compelDataList.isEmpty()) {
                int count = 1;
                for (SurvivorPayeeDataUpdateCompelDataCase o : compelDataList) {
                    Bacompelnopay bacompelnopay = new Bacompelnopay();
                    BeanUtility.copyProperties(bacompelnopay, o);
                    bacompelnopay.setSeqNo(caseData.getSeqNo());
                    bacompelnopay.setCompelNo(new BigDecimal(count));
                    bacompelnopayDao.insertData(bacompelnopay);
                    count++;
                }
            }
        }

        // 新增在學期間,先把整個table的在學期間delete掉,再insert上去
        // 直接砍掉所有與該受款人相關的在學期間資料
        // 先檢查在學註記,若為Y才insert studterm list,若為N表示未在學
        // 則不新增
        bastudtermDao.deleteBastudterm(baappbase.getApNo(), baappbase.getSeqNo());
        if ("Y".equals(expand.getStudMk())) {
            if (bastudtermList != null && !bastudtermList.isEmpty()) {
                int count = 1;
                for (Bastudterm o : bastudtermList) {
                    o.setSeqNo(caseData.getSeqNo());
                    o.setTermNo(new BigDecimal(count));
                    bastudtermDao.insertBastudterm(o);
                    count++;
                }
            }
        }

        // Insert MMAPLOG
        baappbase.setBaappbaseId(baappbaseId);
        expand.setBaappexpandId(baappexpandId);
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baappbase);
            FrameworkLogUtil.doInsertLog(expand);
            FrameworkLogUtil.doInsertListLog(bastudtermList);
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
        baapplogDao.insertData(baapplog);
        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 修改 給付主檔 for 遺屬年金受款人資料更正作業
     * 
     * @param userData 系統使用者
     * @param queryData 事故者資料
     * @param caseData 受款人資料
     * @param bastudtermList 在學期間資料
     * @param bahandicaptermList 重殘期間資料
     * @param bastudtermList
     */
    public void updateDataForSurvivorPayeeData(UserBean userData, SurvivorPayeeDataUpdateCase queryData, SurvivorPayeeDataUpdateCase caseData, List<Bastudterm> bastudtermList, String interValMonth,
                    List<SurvivorPayeeDataUpdateCompelDataCase> compelDataList,List<Bahandicapterm> bahandicaptermList ) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(caseData.getApNo());

        // 相關要修改的主檔清單
        List<BigDecimal> benEvtRelList = new ArrayList<BigDecimal>();
        // 給付資料更正改前物件List
        List<Baappbase> beforeList = new ArrayList<Baappbase>();
        // 給付資料更正改後物件List
        List<Baappbase> afterList = new ArrayList<Baappbase>();
        // 正在修改資料該筆改前改後值List for BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        // 其他需要同步資料的改前改後值List for BAAPPLOG
        // List<BaappbaseDataUpdateCase> baapplogList2 = new ArrayList<BaappbaseDataUpdateCase>();
        List<BigDecimal> payTypeList = new ArrayList<BigDecimal>();// 2010.06.01 新增
        Map<BigDecimal, BigDecimal> payTypeMap = new HashMap<BigDecimal, BigDecimal>();// 2010.06.01 新增
        List<Baappbase> payUpdateList = new ArrayList<Baappbase>();// 2010.06.01 新增
        List<BigDecimal> procstatList = new ArrayList<BigDecimal>();

        // 修改給付主檔
        log.debug("Start Update BAAPPBASE For SurvivorPayeeDataUpdate ...");
        Baappbase baappbase = new Baappbase();
        BigDecimal baappbaseId = caseData.getBaappbaseId();
        Baappbase beforeBaappbase = baappbaseDao.selectDetailDataBy(caseData.getBaappbaseId(), null, null, null);
        BeanUtility.copyProperties(baappbase, caseData);
        baappbase.setImk(queryData.getImk());// 保險別
        baappbase.setApItem(queryData.getApItem());
        baappbase.setPayKind(queryData.getPayKind());// 給付種類
        baappbase.setCaseTyp(queryData.getCaseTyp());// 案件類別
        baappbase.setMapNo(queryData.getMapNo());
        baappbase.setMapRootMk(queryData.getMapRootMk());
        baappbase.setCombapMark(queryData.getCombapMark());
        baappbase.setProcStat(queryData.getProcStat());// 處理狀態
        baappbase.setApUbno(queryData.getApUbno());
        baappbase.setEvtIds(queryData.getEvtIds());
        baappbase.setEvtIdnNo(queryData.getEvtIdnNo());
        baappbase.setEvtName(queryData.getEvtName());
        baappbase.setEvtBrDate(queryData.getEvtBrDate());
        baappbase.setEvtSex(queryData.getEvtSex());
        baappbase.setEvtNationTpe(queryData.getEvtNationTpe());
        baappbase.setEvtNationCode(queryData.getEvtNationCode());
        baappbase.setEvtIdent(queryData.getEvtIdent());// 事故者身分別
        baappbase.setEvtJobDate(queryData.getEvtJobDate());
        baappbase.setCloseCause(caseData.getCloseCause());
        baappbase.setIdnChkNote(caseData.getIdnChkNote());
        baappbase.setIdnChkYm(caseData.getIdnChkYm());
        baappbase.setBenPayMk("1");
        baappbase.setChgMk("Y");// 更正註記
        baappbase.setUpdUser(userData.getEmpNo());
        baappbase.setUpdTime(DateUtility.getNowWestDateTime(true));
        baappbase.setPayTyp(caseData.getPayTyp());
        baappbase.setUnqualifiedCause(caseData.getUnqualifiedCause());

        // 2011.07.29 修改
        // interValMonth
        if (!interValMonth.equals("N")) {
            baappbase.setInterValMonth(interValMonth);
        }

        baappbaseDao.updateDataForSurvivorPayeeDataUpdate(baappbase);

        // 將 受款人資料修改主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
        baapplogList = caseData.getBaapplogList();

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int j = 0; j < baapplogList.size(); j++) {
            Baapplog baapplog = baapplogList.get(j);
            baapplog.setBaappbaseId(caseData.getBaappbaseId());// 資料列編號

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

        // 判斷需要UPDATE的相關資料

        if (("1").equals(baappbase.getBenEvtRel())) { // 本人
            benEvtRelList = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());

            if (("1").equals(baappbase.getAccRel())) { // 戶名與受益人關係
                // 具名領取清單
                payTypeList = baappbaseDao.selectBaappbaseIdByAccSeqNo(baappbase.getApNo(), baappbase.getSeqNo());
                payTypeMap = transListToMapForPayeeDataUpdate(payTypeList);

            }

            for (int i = 0; i < benEvtRelList.size(); i++) {
                baappbaseId = benEvtRelList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件
                Baappbase afterObj = new Baappbase();// 改後值物件

                // 有記MMAPLOG時才將改前值物件存入List
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 若目前資料為頁面所修改得該筆資料,則改前值物件須取 beforeBaappbase
                    if ((baappbaseId).equals(beforeBaappbase.getBaappbaseId())) {
                        beforeList.add(beforeBaappbase);
                    }
                    else {
                        BeanUtility.copyProperties(beforeObj, extModifyObj);
                        beforeList.add(beforeObj);
                    }
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);

                // 判斷是否需清除具名領取資料
                if (isCleanCoreceivePaymentDataForSurvivor(extModifyObj, caseData)) {
                    // extModifyObj.setAccRel("");
                    // extModifyObj.setAccSeqNo("");
                    extModifyObj.setAccIdn("");
                    extModifyObj.setBankName("");
                    extModifyObj.setPayBankId("");
                    extModifyObj.setPayEeacc("");
                    // extModifyObj.setPayTyp("");
                    extModifyObj.setBranchId("");
                }
                extModifyObj.setEvtIds(baappbase.getBenIds()); // 受款人社福識別碼
                extModifyObj.setEvtIdnNo(baappbase.getBenIdnNo()); // 受款人事故者身分證號
                extModifyObj.setEvtName(baappbase.getBenName()); // 受款人事故者姓名
                extModifyObj.setEvtBrDate(baappbase.getBenBrDate()); // 受款人事故者出生日期
                extModifyObj.setEvtSex(baappbase.getBenSex()); // 性別
                extModifyObj.setEvtNationTpe(baappbase.getBenNationTyp()); // 國籍別
                extModifyObj.setProcStat("01");
                // 2010.05.05修改 新增欄位[
                extModifyObj.setChkDate("");
                extModifyObj.setChkMan("");
                extModifyObj.setRechkDate("");
                extModifyObj.setRechkMan("");
                extModifyObj.setExeDate("");
                extModifyObj.setExeMan("");
                // ]
                extModifyObj.setEvtNationCode(baappbase.getBenNationCode()); // 國籍
                extModifyObj.setUpdUser(baappbase.getUpdUser());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbase.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    extModifyObj.setPayTyp(baappbase.getPayTyp());
                    extModifyObj.setBankName(baappbase.getBankName()); // 金融機構名稱
                    extModifyObj.setPayBankId(baappbase.getPayBankId()); // 金融機構總代號
                    extModifyObj.setBranchId(baappbase.getBranchId()); // 分支代號
                    extModifyObj.setAccName(baappbase.getAccName()); // 戶名
                    extModifyObj.setPayEeacc(baappbase.getPayEeacc()); // 銀行帳號
                    extModifyObj.setAccIdn(baappbase.getAccIdn());
                    extModifyObj.setMitRate(baappbase.getMitRate()); // 匯款匯費
                    extModifyObj.setUpdUser(baappbase.getUpdUser());
                    extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                    objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
                    objForm.setPayEeacc(extModifyObj.getPayEeacc());
                    objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
                }

                // 2011.07.29 修改
                // interValMonth
                if (!interValMonth.equals("N")) {
                    extModifyObj.setInterValMonth(interValMonth);
                }

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                // baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbase.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    payUpdateList.add(afterObj);
                }
            }

            // 判斷是否需清除相關資料之具名領取資料
            if (!"0000".equals(baappbase.getSeqNo())) {
                if (StringUtils.isNotBlank(baappbase.getBenDieDate())) {
                    baappbaseDao.cleanCoreceivePaymentData(baappbase.getApNo(), baappbase.getSeqNo());
                }
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForSurvivorPayeeData(obj);
            }
        }
        else { // 其他親屬
            procstatList = baappbaseDao.selectBaappbaseIdBy(baappbase.getApNo());

            if (("1").equals(baappbase.getAccRel())) { // 戶名與受益人關係
                // 具名領取清單
                payTypeList = baappbaseDao.selectBaappbaseIdByAccSeqNo(baappbase.getApNo(), baappbase.getSeqNo());
                payTypeMap = transListToMapForPayeeDataUpdate(payTypeList);
            }

            for (int i = 0; i < procstatList.size(); i++) {
                baappbaseId = procstatList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                Baappbase afterObj = new Baappbase();// 改後值物件

                // 有記MMAPLOG時才將改前值物件存入List
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 若目前資料為頁面所修改得該筆資料,則改前值物件須取 beforeBaappbase
                    if ((baappbaseId).equals(beforeBaappbase.getBaappbaseId())) {
                        beforeList.add(beforeBaappbase);
                    }
                    else {
                        BeanUtility.copyProperties(beforeObj, extModifyObj);
                        beforeList.add(beforeObj);
                    }
                }

                // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
                PayeeDataUpdateDetailForm objForm = new PayeeDataUpdateDetailForm();
                BeanUtility.copyProperties(objForm, extModifyObj);

                // 判斷是否需清除具名領取資料
                // 判斷是否需清除具名領取資料
                if (isCleanCoreceivePaymentDataForSurvivor(extModifyObj, caseData)) {
                    // extModifyObj.setAccRel("");
                    // extModifyObj.setAccSeqNo("");
                    extModifyObj.setAccIdn("");
                    extModifyObj.setBankName("");
                    extModifyObj.setPayBankId("");
                    extModifyObj.setPayEeacc("");
                    // extModifyObj.setPayTyp("");
                    extModifyObj.setBranchId("");
                }

                extModifyObj.setProcStat("01");
                // 2010.05.05修改 新增欄位[
                extModifyObj.setChkDate("");
                extModifyObj.setChkMan("");
                extModifyObj.setRechkDate("");
                extModifyObj.setRechkMan("");
                extModifyObj.setExeDate("");
                extModifyObj.setExeMan("");
                // ]
                extModifyObj.setUpdUser(baappbase.getUpdUser());
                extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbase.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    extModifyObj.setPayTyp(baappbase.getPayTyp());
                    extModifyObj.setBankName(baappbase.getBankName()); // 金融機構名稱
                    extModifyObj.setPayBankId(baappbase.getPayBankId()); // 金融機構總代號
                    extModifyObj.setBranchId(baappbase.getBranchId()); // 分支代號
                    extModifyObj.setAccName(baappbase.getAccName()); // 戶名
                    extModifyObj.setPayEeacc(baappbase.getPayEeacc()); // 銀行帳號
                    extModifyObj.setAccIdn(baappbase.getAccIdn());
                    extModifyObj.setMitRate(baappbase.getMitRate()); // 匯款匯費
                    extModifyObj.setUpdUser(baappbase.getUpdUser());
                    extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                    objForm.setPayBankIdBranchId(extModifyObj.getPayBankId() + extModifyObj.getBranchId());
                    objForm.setPayEeacc(extModifyObj.getPayEeacc());
                    objForm.setPayAccount(extModifyObj.getPayBankId() + extModifyObj.getBranchId() + extModifyObj.getPayEeacc());
                }

                // 2011.07.29 修改
                // interValMonth
                if (!interValMonth.equals("N")) {
                    extModifyObj.setInterValMonth(interValMonth);
                }

                extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

                // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
                // baapplogList2.add(extModifyObj);
                BeanUtility.copyProperties(afterObj, extModifyObj);
                afterList.add(afterObj);

                // 2010.05.05 修改
                // 需要一併修改相關具名領取資料, 且目前資料在具名領取清單中

                if (("1").equals(baappbase.getAccRel()) && extModifyObj.getBaappbaseId().equals(payTypeMap.get(extModifyObj.getBaappbaseId()))) {
                    payUpdateList.add(afterObj);
                }
            }

            // benEvtRelList = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());

            // for (int i = 0; i < benEvtRelList.size(); i++) {
            // baappbaseId = benEvtRelList.get(i);
            // BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
            // Baappbase beforeObj = new Baappbase();// 改前值物件

            //
            // Baappbase afterObj = new Baappbase();// 改後值物件

            //
            // // 有記MMAPLOG時才將改前值物件存入List
            // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // // 若目前資料為頁面所修改得該筆資料,則改前值物件須取 beforeBaappbase
            // if ((baappbaseId).equals(beforeBaappbase.getBaappbaseId())) {
            // beforeList.add(beforeBaappbase);
            // }
            // else {
            // BeanUtility.copyProperties(beforeObj, extModifyObj);
            // beforeList.add(beforeObj);
            // }
            // }
            //
            // // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
            // SurvivorPayeeDataUpdateDetailForm objForm = new SurvivorPayeeDataUpdateDetailForm();
            // BeanUtility.copyProperties(objForm, extModifyObj);
            //
            // // 判斷是否需清除具名領取資料
            // if (isCleanCoreceivePaymentDataForSurvivor(extModifyObj, caseData)) {
            // extModifyObj.setAccRel("");
            // extModifyObj.setAccSeqNo("");
            // extModifyObj.setAccIdn("");
            // extModifyObj.setBankName("");
            // extModifyObj.setPayBankId("");
            // extModifyObj.setPayEeacc("");
            // extModifyObj.setPayTyp("");
            // extModifyObj.setBranchId("");
            // }
            //
            // /*
            // * 受款人資料修改存檔後,除了畫面欄位資料及SPEC內寫的欄位回存外, 下列欄位需一併處理:BAAPPBASE.CHGMK='Y', 而下列欄位的資料清空:BAAPPBASE.CHKDATE、BAAPPBASE.CHKMAN、 BAAPPBASE.RECHKDATE、BAAPPBASE.RECHKMAN、 BAAPPBASE.EXEDATE、BAAPPBASE.EXEMAN
            // */
            // extModifyObj.setProcStat("01");
            // extModifyObj.setChkDate("");
            // extModifyObj.setChkMan("");
            // extModifyObj.setRechkDate("");
            // extModifyObj.setRechkMan("");
            // extModifyObj.setExeDate("");
            // extModifyObj.setExeMan("");
            // extModifyObj.setUpdUser(baappbase.getUpdUser());
            // extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
            // extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));
            //
            // // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
            // baapplogList2.add(extModifyObj);
            // BeanUtility.copyProperties(afterObj, extModifyObj);
            // afterList.add(afterObj);
            // }

            // 判斷是否需清除相關資料之具名領取資料

            if (!"0000".equals(baappbase.getSeqNo())) {
                if (StringUtils.isNotBlank(baappbase.getBenDieDate())) {
                    baappbaseDao.cleanCoreceivePaymentData(baappbase.getApNo(), baappbase.getSeqNo());
                }
            }

            // 更新資料到 給付主檔
            for (int i = 0; i < afterList.size(); i++) {
                Baappbase obj = (Baappbase) afterList.get(i);
                baappbaseDao.updateDataForSurvivorPayeeData(obj);
            }
        }

        // 2010.05.05 修改
        // 更新具名領取資料到 給付主檔
        for (int i = 0; i < payUpdateList.size(); i++) {
            Baappbase obj = (Baappbase) payUpdateList.get(i);
            baappbaseDao.updateDataForPayment(obj);
        }

        // 修改給付延伸主檔
        Baappexpand beforBaappexpand = baappexpandDao.selectDataForSurvivorPayeeDataUpdate(caseData.getBaappbaseId());
        Baappexpand expand = new Baappexpand();
        BeanUtility.copyProperties(expand, beforBaappexpand);
        expand.setBaappbaseId(caseData.getBaappbaseId());
        expand.setBaappexpandId(new BigDecimal(caseData.getBaappexpandId()));
        expand.setApNo(caseData.getApNo());
        expand.setSeqNo(caseData.getSeqNo());
        expand.setMonIncomeMk(caseData.getMonIncomeMk());
        /*
         * String monIncome = caseData.getMonIncome(); if (StringUtils.isBlank(caseData.getMonIncome()) || "0".equals(monIncome)) { monIncome = "0"; }
         */
        if (StringUtils.isNotBlank(caseData.getMonIncome())) {
            expand.setMonIncome(new BigDecimal(caseData.getMonIncome()));
        }
        else {
            expand.setMonIncome(null);
        }
        expand.setMarryDate(caseData.getMarryDate());
        expand.setDigamyDate(caseData.getDigaMyDate());
        expand.setInterDictMk(caseData.getInterdictMk());
        expand.setHandIcapMk(caseData.getHandicapMk());
        expand.setCompelMk(caseData.getCompelMk());
        expand.setStudMk(caseData.getStudMk());
        expand.setAbanApplyMk(caseData.getAbanApplyMk());
        expand.setAbanApsYm(caseData.getAbanApSym());
        expand.setAbleApsYm(caseData.getAbleApsYm());
        expand.setRaiseChildMk(caseData.getRaiseChildMk());
        expand.setInterDictMk(caseData.getInterdictMk());
        expand.setInterDictDate(caseData.getInterdictDate());
        expand.setRepealInterdictDate(caseData.getRepealInterdictDate());
        expand.setBenMissingSdate(caseData.getBenMissingSdate());
        expand.setBenMissingEdate(caseData.getBenMissingEdate());
        expand.setPrisonSdate(caseData.getPrisonSdate());
        expand.setPrisonEdate(caseData.getPrisonEdate());
        expand.setRelatChgDate(caseData.getRelatChgDate());
        expand.setAdoPtDate(caseData.getAdoptDate());
        expand.setAssignIdnNo(caseData.getAssignIdnNo());
        expand.setAssignName(caseData.getAssignName());
        expand.setAssignBrDate(caseData.getAssignBrDate());
        expand.setRaiseEvtMk(caseData.getRaiseEvtMk());
        expand.setSavingMk(caseData.getSavingMk());
        expand.setUpdUser(userData.getEmpNo());
        expand.setUpdTime(DateUtility.getNowWestDateTime(true));
        expand.setSchoolCode(caseData.getSchoolCode());

        baappexpandDao.updateDataForSurvivorPayeeDataUpdate(expand);

        // 2011.05.26 新增 當案件主檔(seqNo=0000)的caseType!='1', 則判斷改前改後值，並存入BACOLUMNREC
        // [
        Bacolumnrec bacolumnrec = new Bacolumnrec();
        Bacolumnrec tmpBacolumnrec = new Bacolumnrec();
        tmpBacolumnrec.setApNo(caseData.getApNo());
        tmpBacolumnrec.setSeqNo(caseData.getSeqNo());
        tmpBacolumnrec.setBenEvtRel(caseData.getBenEvtRel());
        tmpBacolumnrec.setCrtUser(userData.getEmpNo());
        tmpBacolumnrec.setCrtTime(DateUtility.getNowWestDateTime(true));

        List<Bacolumnrec> bacolumnrecList = new ArrayList<Bacolumnrec>();
        String chgDate = null;

        if (!StringUtils.equals(ConstantKey.BAAPPBASE_CASETYP_1, queryData.getCaseTyp())) {
            // 畫面欄位「申請日期」->BAAPPBASE. APPDATE：

            chgDate = compareTwoDate(beforeBaappbase.getAppDate(), baappbase.getAppDate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);
                bacolumnrec.setColumnName("APPDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「死亡日期」->BAAPPBASE. BENDIEDATE：
            chgDate = compareTwoDate(beforeBaappbase.getBenDieDate(), baappbase.getBenDieDate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("BENDIEDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「結婚日期」-> BAAPPEXPAND. MARRYDATE：
            // 遺屬關係為2時，且該欄位發生異動才需記錄。
            chgDate = compareTwoDate(beforBaappexpand.getMarryDate(), expand.getMarryDate());
            if (StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_2, baappbase.getBenEvtRel()) && StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("MARRYDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「再婚日期」-> BAAPPEXPAND. DIGAMYDATE：
            // 遺屬關係為2時，且該欄位發生異動才需記錄。
            chgDate = compareTwoDate(beforBaappexpand.getDigamyDate(), expand.getDigamyDate());
            if (StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_2, baappbase.getBenEvtRel()) && StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("DIGAMYDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 寫入欄位名稱為畫面欄位+RowNo，例：第二筆的「在學起始年月」異動，則異動欄位名稱(BACOLUMNREC. COLUMNNAME) 欄位值寫入STUDEDATE2。
            // 畫面欄位「得請領起月」-> BAAPPEXPAND. ABLEAPSYM：
            // 遺屬關係為3時，且該欄位發生異動才需記錄。
            chgDate = compareTwoDate(beforBaappexpand.getAbanApsYm() + "01", expand.getAbanApsYm() + "01");
            if (StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_3, baappbase.getBenEvtRel()) && StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("ABLEAPSYM");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「被保險人扶養」-> BAAPPEXPAND. RAISEEVTMK：
            // 遺屬關係為6、7時，且該欄位發生異動才需記錄。
            if ((StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_6, baappbase.getBenEvtRel()) || StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_7, baappbase.getBenEvtRel()))
                            && !StringUtils.equals(beforBaappexpand.getRaiseEvtMk(), expand.getRaiseEvtMk())) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);
                bacolumnrec.setColumnName("RAISEEVTMK");
                bacolumnrec.setColumnValue("Y");
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「收養日期」-> BAAPPEXPAND. ADOPTDATE：
            // 遺屬關係為4時，且該欄位發生異動才需記錄。
            chgDate = compareTwoDate(beforBaappexpand.getAdoPtDate(), expand.getAdoPtDate());
            if (StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_4, baappbase.getBenEvtRel()) && StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);
                bacolumnrec.setColumnName("ADOPTDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「受禁治產(監護)宣告日期」-> BAAPPEXPAND. INTERDICTDATE：
            chgDate = compareTwoDate(beforBaappexpand.getInterDictDate(), expand.getInterDictDate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);
                bacolumnrec.setColumnName("INTERDICTDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「受禁治產(監護)撤銷日期」-> BAAPPEXPAND. REPEALINTERDICTDATE：
            chgDate = compareTwoDate(beforBaappexpand.getRepealInterdictDate(), expand.getRepealInterdictDate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("REPEALINTERDICTDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }
            // 畫面欄位「放棄請領起始年月」-> BAAPPEXPAND. ABANAPSYM：
            chgDate = compareTwoDate(beforBaappexpand.getAbanApsYm() + "01", expand.getAbanApsYm() + "01");
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("ABANAPSYM");
                bacolumnrec.setColumnValue(StringUtils.substring(chgDate, 0, 6));
                bacolumnrecList.add(bacolumnrec);
            }
            // 畫面欄位「受益人失蹤期間(起)」-> BAAPPEXPAND. BENMISSINGSDATE：
            chgDate = compareTwoDate(beforBaappexpand.getBenMissingSdate(), expand.getBenMissingSdate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("BENMISSINGSDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }
            // 畫面欄位「受益人失蹤期間(迄)」-> BAAPPEXPAND. BENMISSINGEDATE：
            chgDate = compareTwoDate(beforBaappexpand.getBenMissingEdate(), expand.getBenMissingEdate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("BENMISSINGEDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }
            // 畫面欄位「親屬關係變動日期」-> BAAPPEXPAND. RELATCHGDATE：
            chgDate = compareTwoDate(beforBaappexpand.getRelatChgDate(), expand.getRelatChgDate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("RELATCHGDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }
            // 畫面欄位「監管期間(起)」-> BAAPPEXPAND. PRISONSDATE：
            chgDate = compareTwoDate(beforBaappexpand.getPrisonSdate(), expand.getPrisonSdate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("PRISONSDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }
            // 畫面欄位「監管期間(迄)」-> BAAPPEXPAND. PRISONEDATE：
            chgDate = compareTwoDate(beforBaappexpand.getPrisonEdate(), expand.getPrisonEdate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("PRISONEDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }
        }
        // ]

        // 新增不合格年月資料,先把整個table的不合格年月資料delete掉,再insert上去
        // 直接砍掉所有與該受款人相關的不合格年月資料
        // 先檢查強迫不合格註記,若為Y才insert compelData list,若為N表示未強迫不合格
        // 則不新增
        List<Bacompelnopay> beforCompelDataList = bacompelnopayDao.selectDataBy(baappbase.getApNo(), baappbase.getSeqNo());
        bacompelnopayDao.deleteData(baappbase.getApNo(), baappbase.getSeqNo());
        if ("Y".equals(expand.getCompelMk())) {
            if (compelDataList != null && !compelDataList.isEmpty()) {
                int count = 1;
                for (SurvivorPayeeDataUpdateCompelDataCase o : compelDataList) {
                    Bacompelnopay bacompelnopay = new Bacompelnopay();
                    BeanUtility.copyProperties(bacompelnopay, o);
                    bacompelnopay.setSeqNo(caseData.getSeqNo());
                    bacompelnopay.setCompelNo(new BigDecimal(count));
                    bacompelnopayDao.insertData(bacompelnopay);
                    count++;
                }
            }

            // 當案件主檔(seqNo=0000)的caseType!='1', 則判斷改前改後值，並存入BACOLUMNREC
            // [
            // 先將改前改後值List複製一分至新List,並將兩邊筆數設為一致方便比對
            if (!StringUtils.equals(ConstantKey.BAAPPBASE_CASETYP_1, baappbase.getCaseTyp())) {
                List<Bacompelnopay> tmpBeforeList = new ArrayList<Bacompelnopay>();
                List<Bacompelnopay> tmpAfterList = new ArrayList<Bacompelnopay>();
                for (int i = 0; i < beforCompelDataList.size(); i++) {
                    Bacompelnopay origObj = beforCompelDataList.get(i);
                    Bacompelnopay modObj = new Bacompelnopay();
                    BeanUtility.copyProperties(modObj, origObj);
                    tmpBeforeList.add(modObj);
                }
                for (int i = 0; i < compelDataList.size(); i++) {
                    SurvivorPayeeDataUpdateCompelDataCase origObj = compelDataList.get(i);
                    Bacompelnopay modObj = new Bacompelnopay();
                    BeanUtility.copyProperties(modObj, origObj);
                    tmpAfterList.add(modObj);
                }

                if (tmpBeforeList.size() > tmpAfterList.size()) {
                    for (int i = tmpAfterList.size(); i < tmpBeforeList.size(); i++) {
                        Bacompelnopay modObj = new Bacompelnopay();
                        modObj.setCompelNo(new BigDecimal(i + 1));
                        tmpAfterList.add(modObj);
                    }
                }
                else if (tmpBeforeList.size() < tmpAfterList.size()) {
                    for (int i = tmpBeforeList.size(); i < tmpAfterList.size(); i++) {
                        Bacompelnopay modObj = new Bacompelnopay();
                        modObj.setCompelNo(new BigDecimal(i + 1));
                        tmpBeforeList.add(modObj);
                    }
                }

                // 開始比對
                for (int i = 0; i < tmpAfterList.size(); i++) {
                    Bacompelnopay origObj = tmpBeforeList.get(i);
                    Bacompelnopay modObj = tmpAfterList.get(i);

                    // 畫面欄位「不合格起始年月」->BACOMPELNOPAY. COMPELSDATE && 「不合格結束年月」->BACOMPELNOPAY. COMPELEDATE。
                    // 當該欄位發生異動才需記錄。
                    // 寫入欄位名稱為畫面欄位+RowNo，例：第二筆的「不合格起始年月」異動，則異動欄位名稱(BACOLUMNREC. COLUMNNAME) 欄位值寫入COMPELEDATE2。
                    chgDate = null;
                    chgDate = compareTwoDate(origObj.getCompelSdate(), modObj.getCompelSdate());
                    if (StringUtils.isNotBlank(chgDate)) {
                        bacolumnrec = new Bacolumnrec();
                        BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);
                        bacolumnrec.setColumnName("COMPELSDATE" + modObj.getCompelNo());
                        bacolumnrec.setColumnValue(chgDate);
                        bacolumnrecList.add(bacolumnrec);
                    }

                    chgDate = null;
                    chgDate = compareTwoDate(origObj.getCompelSdate(), modObj.getCompelSdate());
                    if (StringUtils.isNotBlank(chgDate)) {
                        bacolumnrec = new Bacolumnrec();
                        BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);
                        bacolumnrec.setColumnName("COMPELEDATE" + modObj.getCompelNo());
                        bacolumnrec.setColumnValue(chgDate);
                        bacolumnrecList.add(bacolumnrec);
                    }
                }
            }
            // ]
        }

        // 修改在學期間,先把整個table的在學期間delete掉,再insert上去
        // 直接砍掉所有與該受款人相關的在學期間資料
        // 先檢查在學註記,若為Y才insert studterm list,若為N表示未在學
        List<Bastudterm> beforStudtermList = bastudtermDao.selectStudtermListForSurvivorPayeeDataUpdate(baappbase.getApNo(), baappbase.getSeqNo());
        // 則不新增
        bastudtermDao.deleteBastudterm(baappbase.getApNo(), baappbase.getSeqNo());
        if ("Y".equals(expand.getStudMk())) {
            if (bastudtermList != null && !bastudtermList.isEmpty()) {
                int count = 1;
                for (Bastudterm o : bastudtermList) {
                    o.setTermNo(new BigDecimal(count));
                    bastudtermDao.insertBastudterm(o);
                    count++;
                }
            }

            // 2011.05.26 新增 當案件主檔(seqNo=0000)的caseType!='1', 則判斷改前改後值，並存入BACOLUMNREC
            // [
            // 先將改前改後值List複製一分至新List,並將兩邊筆數設為一致方便比對

            if (!StringUtils.equals(ConstantKey.BAAPPBASE_CASETYP_1, baappbase.getCaseTyp())) {
                List<Bastudterm> tmpBeforeList = new ArrayList<Bastudterm>();
                List<Bastudterm> tmpAfterList = new ArrayList<Bastudterm>();
                for (int i = 0; i < beforStudtermList.size(); i++) {
                    Bastudterm origObj = beforStudtermList.get(i);
                    Bastudterm modObj = new Bastudterm();
                    BeanUtility.copyProperties(modObj, origObj);
                    tmpBeforeList.add(modObj);
                }
                for (int i = 0; i < bastudtermList.size(); i++) {
                    Bastudterm origObj = bastudtermList.get(i);
                    Bastudterm modObj = new Bastudterm();
                    BeanUtility.copyProperties(modObj, origObj);
                    tmpAfterList.add(modObj);
                }

                if (tmpBeforeList.size() > tmpAfterList.size()) {
                    for (int i = tmpAfterList.size(); i < tmpBeforeList.size(); i++) {
                        Bastudterm modObj = new Bastudterm();
                        modObj.setTermNo(new BigDecimal(i + 1));
                        tmpAfterList.add(modObj);
                    }
                }
                else if (tmpBeforeList.size() < tmpAfterList.size()) {
                    for (int i = tmpBeforeList.size(); i < tmpAfterList.size(); i++) {
                        Bastudterm modObj = new Bastudterm();
                        modObj.setTermNo(new BigDecimal(i + 1));
                        tmpBeforeList.add(modObj);
                    }
                }

                // 開始比對
                for (int i = 0; i < tmpAfterList.size(); i++) {
                    Bastudterm origObj = tmpBeforeList.get(i);
                    Bastudterm modObj = tmpAfterList.get(i);

                    // 畫面欄位「在學起始年月」->BASTUDTERM. STUDSDATE && 「在學結束年月」->BASTUDTERM. STUDEDATE。

                    // 當眷屬關係為4、7時，且該欄位發生異動才需記錄。

                    // 寫入欄位名稱為畫面欄位+RowNo，例：第二筆的「在學起始年月」異動，則異動欄位名稱(BACOLUMNREC. COLUMNNAME) 欄位值寫入STUDEDATE2。

                    if (StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_4, baappbase.getBenEvtRel()) || StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_7, baappbase.getBenEvtRel())) {
                        chgDate = null;
                        chgDate = compareTwoDate(origObj.getStudSdate(), modObj.getStudSdate());
                        if (StringUtils.isNotBlank(chgDate)) {
                            bacolumnrec = new Bacolumnrec();
                            BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                            bacolumnrec.setColumnName("STUDSDATE" + modObj.getTermNo());
                            bacolumnrec.setColumnValue(chgDate);
                            bacolumnrecList.add(bacolumnrec);
                        }

                        chgDate = null;
                        chgDate = compareTwoDate(origObj.getStudEdate(), modObj.getStudEdate());
                        if (StringUtils.isNotBlank(chgDate)) {
                            bacolumnrec = new Bacolumnrec();
                            BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                            bacolumnrec.setColumnName("STUDEDATE" + modObj.getTermNo());
                            bacolumnrec.setColumnValue(chgDate);
                            bacolumnrecList.add(bacolumnrec);
                        }
                    }
                }
            }
            // ]
        }

        // 修改重殘期間,先把整個table的重殘期間delete掉,再insert上去
        // 直接砍掉所有與該受款人相關的重殘期間資料
        // 先檢查重殘註記,若為Y才insert handicapterm list,若為N表示未重殘
        List<Bahandicapterm> beforHandicaptermList = bahandicaptermDao.selectHandicaptermListForSurvivorPayeeDataUpdate(baappbase.getApNo(), baappbase.getSeqNo());
        // 則不新增
        bahandicaptermDao.deleteBahandicapterm(baappbase.getApNo(), baappbase.getSeqNo());
        if ("Y".equals(expand.getHandIcapMk())) {
            if (bahandicaptermList != null && !bahandicaptermList.isEmpty()) {
                int count = 1;
                for (Bahandicapterm o : bahandicaptermList) {
                    o.setTermNo(new BigDecimal(count));
                    bahandicaptermDao.insertBahandicapterm(o);
                    count++;
                }
            }

            // 2011.05.26 新增 當案件主檔(seqNo=0000)的caseType!='1', 則判斷改前改後值，並存入BACOLUMNREC
            // [
            // 先將改前改後值List複製一分至新List,並將兩邊筆數設為一致方便比對

            if (!StringUtils.equals(ConstantKey.BAAPPBASE_CASETYP_1, baappbase.getCaseTyp())) {
                List<Bahandicapterm> tmpBeforeList = new ArrayList<Bahandicapterm>();
                List<Bahandicapterm> tmpAfterList = new ArrayList<Bahandicapterm>();
                for (int i = 0; i < beforHandicaptermList.size(); i++) {
                    Bahandicapterm origObj = beforHandicaptermList.get(i);
                    Bahandicapterm modObj = new Bahandicapterm();
                    BeanUtility.copyProperties(modObj, origObj);
                    tmpBeforeList.add(modObj);
                }
                for (int i = 0; i < beforHandicaptermList.size(); i++) {
                	Bahandicapterm origObj = beforHandicaptermList.get(i);
                	Bahandicapterm modObj = new Bahandicapterm();
                    BeanUtility.copyProperties(modObj, origObj);
                    tmpAfterList.add(modObj);
                }

                if (tmpBeforeList.size() > tmpAfterList.size()) {
                    for (int i = tmpAfterList.size(); i < tmpBeforeList.size(); i++) {
                    	Bahandicapterm modObj = new Bahandicapterm();
                        modObj.setTermNo(new BigDecimal(i + 1));
                        tmpAfterList.add(modObj);
                    }
                }
                else if (tmpBeforeList.size() < tmpAfterList.size()) {
                    for (int i = tmpBeforeList.size(); i < tmpAfterList.size(); i++) {
                    	Bahandicapterm modObj = new Bahandicapterm();
                        modObj.setTermNo(new BigDecimal(i + 1));
                        tmpBeforeList.add(modObj);
                    }
                }

                // 開始比對
                for (int i = 0; i < tmpAfterList.size(); i++) {
                	Bahandicapterm origObj = tmpBeforeList.get(i);
                	Bahandicapterm modObj = tmpAfterList.get(i);

                    // 畫面欄位「重殘起始年月」->BAHANDICAPTERM. HANDICAPSDATE && 「重殘結束年月」->BAHANDICAPTERM. HANDICAPEDATE。

                    // 當眷屬關係為4、7時，且該欄位發生異動才需記錄。

                    // 寫入欄位名稱為畫面欄位+RowNo，例：第二筆的「重殘起始年月」異動，則異動欄位名稱(BACOLUMNREC. COLUMNNAME) 欄位值寫入HANDICAPEDATE2。

                    if (StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_4, baappbase.getBenEvtRel()) || StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_7, baappbase.getBenEvtRel())) {
                        chgDate = null;
                        chgDate = compareTwoDate(origObj.getHandicapSdate(), modObj.getHandicapSdate());
                        if (StringUtils.isNotBlank(chgDate)) {
                            bacolumnrec = new Bacolumnrec();
                            BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                            bacolumnrec.setColumnName("HANDICAPSDATE" + modObj.getTermNo());
                            bacolumnrec.setColumnValue(chgDate);
                            bacolumnrecList.add(bacolumnrec);
                        }

                        chgDate = null;
                        chgDate = compareTwoDate(origObj.getHandicapEdate(), modObj.getHandicapEdate());
                        if (StringUtils.isNotBlank(chgDate)) {
                            bacolumnrec = new Bacolumnrec();
                            BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                            bacolumnrec.setColumnName("HANDICAPSDATE" + modObj.getTermNo());
                            bacolumnrec.setColumnValue(chgDate);
                            bacolumnrecList.add(bacolumnrec);
                        }
                    }
                }
            }
            // ]
        }
        
        // 2011.05.26 新增 當案件主檔(seqNo=0000)的caseType!='1', 則判斷改前改後值，並存入BACOLUMNREC
        // 比對結果資料不為0筆，寫入資料庫

        if (bacolumnrecList.size() > 0) {
            bacolumnrecDao.insertData(bacolumnrecList);
        }

        // baappexpandDao.update
        // Insert MMAPLOG
        // baappbase.setBaappbaseId(baappbaseId);
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
            FrameworkLogUtil.doUpdateLog(beforBaappexpand, expand);
            FrameworkLogUtil.doUpdateListLog(beforStudtermList, bastudtermList);
            FrameworkLogUtil.doUpdateListLog(beforHandicaptermList, bahandicaptermList);
        }
        log.debug("Insert BAAPPBASE Finished ...");

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        // log.debug("Start Insert BAAPPLOG ...");
        // for (int i = 0; i < baapplogList2.size(); i++) {
        // BaappbaseDataUpdateCase logObj = baapplogList2.get(i);
        // baappbaseId = logObj.getBaappbaseId();
        // List<Baapplog> logList = logObj.getBaapplogList();
        // for (int j = 0; j < logList.size(); j++) {
        // Baapplog baapplog = logList.get(j);
        // baapplog.setBaappbaseId(baappbaseId);// 資料列編號
        //
        // baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        // baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        // baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // // baapplog.setUpCol(); // 異動項目 - Log 已設
        // // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
        // // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
        // baapplogDao.insertData(baapplog);
        // }
        // }
        // log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    /**
     * 修改 給付主檔 for 遺屬年金繼承人資料更正作業 queryData = 主檔 baappbaseData
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForSurvivorPayeeInheritData(UserBean userData, SurvivorPayeeDataUpdateCase queryData, SurvivorPayeeDataUpdateCase caseData) {
        // 相關要修改的主檔清單
        List<BigDecimal> benEvtRelList = new ArrayList<BigDecimal>();
        // 給付資料更正改前物件List
        List<Baappbase> beforeList = new ArrayList<Baappbase>();
        // 給付資料更正改後物件List
        List<Baappbase> afterList = new ArrayList<Baappbase>();
        // 正在修改資料該筆改前改後值List for BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        // 其他需要同步資料的改前改後值List for BAAPPLOG
        List<BaappbaseDataUpdateCase> baapplogList2 = new ArrayList<BaappbaseDataUpdateCase>();

        // 修改給付主檔
        // [
        log.debug("Start Update BAAPPBASE ...");
        Baappbase baappbase = new Baappbase();
        BigDecimal baappbaseId = caseData.getBaappbaseId();
        Baappbase beforeBaappbase = baappbaseDao.selectDetailDataBy(caseData.getBaappbaseId(), null, null, null);
        BeanUtility.copyProperties(baappbase, caseData);
        baappbase.setImk(queryData.getImk());// 保險別

        baappbase.setApItem(queryData.getApItem());
        baappbase.setPayKind(queryData.getPayKind());// 給付種類
        baappbase.setCaseTyp(queryData.getCaseTyp());// 案件類別
        baappbase.setMapNo(queryData.getMapNo());
        baappbase.setMapRootMk(queryData.getMapRootMk());
        baappbase.setCombapMark(queryData.getCombapMark());
        baappbase.setProcStat(queryData.getProcStat());// 處理狀態

        baappbase.setApUbno(queryData.getApUbno());
        baappbase.setEvtIds(queryData.getEvtIds());
        baappbase.setEvtIdnNo(queryData.getEvtIdnNo());
        baappbase.setEvtName(queryData.getEvtName());
        baappbase.setEvtBrDate(queryData.getEvtBrDate());
        baappbase.setEvtSex(queryData.getEvtSex());
        baappbase.setEvtNationTpe(queryData.getEvtNationTpe());
        baappbase.setEvtNationCode(queryData.getEvtNationCode());
        baappbase.setEvtIdent(queryData.getEvtIdent());// 事故者身分別
        baappbase.setEvtJobDate(queryData.getEvtJobDate());
        baappbase.setBenPayMk("1");
        baappbase.setChgMk("Y");// 更正註記
        baappbase.setUpdUser(userData.getEmpNo());
        baappbase.setUpdTime(DateUtility.getNowWestDateTime(true));
        baappbase.setAppDate(caseData.getAppDate());
        baappbaseDao.updateDataForSurvivorPayeeInheritData(baappbase);

        // 將 受款人資料修改主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
        baapplogList = caseData.getBaapplogList();

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int j = 0; j < baapplogList.size(); j++) {
            Baapplog baapplog = baapplogList.get(j);
            baapplog.setBaappbaseId(caseData.getBaappbaseId());// 資料列編號

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

        // 判斷需要UPDATE的相關資料

        benEvtRelList = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());

        for (int i = 0; i < benEvtRelList.size(); i++) {
            baappbaseId = benEvtRelList.get(i);
            BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
            Baappbase beforeObj = new Baappbase();// 改前值物件

            Baappbase afterObj = new Baappbase();// 改後值物件

            // 有記MMAPLOG時才將改前值物件存入List
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 若目前資料為頁面所修改得該筆資料,則改前值物件須取 beforeBaappbase
                if ((baappbaseId).equals(beforeBaappbase.getBaappbaseId())) {
                    beforeList.add(beforeBaappbase);
                }
                else {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }
            }

            // 處理修改主檔後相關主檔需修改的欄位 / 給付資料更正相關主檔 改前改後值 for BAAPPLOG
            SurvivorPayeeDataUpdateDetailForm objForm = new SurvivorPayeeDataUpdateDetailForm();
            BeanUtility.copyProperties(objForm, extModifyObj);

            /*
             * 除了畫面欄位資料及SPEC內寫的欄位回存外, 下列欄位需一併處理:BAAPPBASE.CHGMK='Y', 而下列欄位的資料清空:BAAPPBASE.CHKDATE、BAAPPBASE.CHKMAN、 BAAPPBASE.RECHKDATE、BAAPPBASE.RECHKMAN、 BAAPPBASE.EXEDATE、BAAPPBASE.EXEMAN
             */
            extModifyObj.setProcStat("01");
            extModifyObj.setChkDate("");
            extModifyObj.setChkMan("");
            extModifyObj.setRechkDate("");
            extModifyObj.setRechkMan("");
            extModifyObj.setExeDate("");
            extModifyObj.setExeMan("");
            extModifyObj.setUpdUser(baappbase.getUpdUser());
            extModifyObj.setUpdTime(DateUtility.getNowWestDateTime(true));
            extModifyObj.setBaapplogList(LoggingHelper.getBaapplogList(objForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_PAYEE_FIELD_RESOURCE_PREFIX));

            // 將 給付資料更正相關主檔 改前改後值 for BAAPPLOG 放進 改前改後值List
            baapplogList2.add(extModifyObj);
            BeanUtility.copyProperties(afterObj, extModifyObj);
            afterList.add(afterObj);
        }

        // 修改給付延伸主檔(只有代辦人相關欄位要改,其他沒有)
        Baappexpand beforeBaappexpand = baappexpandDao.getSurvivorApplicationDataUpdateExpandDataByBaappexpandId(new BigDecimal(caseData.getBaappexpandId()));
        Baappexpand afterBaappexpand = new Baappexpand();
        BeanUtility.copyProperties(afterBaappexpand, beforeBaappexpand);
        afterBaappexpand.setBaappbaseId(caseData.getBaappbaseId());
        afterBaappexpand.setBaappexpandId(new BigDecimal(caseData.getBaappexpandId()));
        afterBaappexpand.setApNo(caseData.getApNo());
        afterBaappexpand.setSeqNo(caseData.getSeqNo());
        afterBaappexpand.setAssignIdnNo(caseData.getAssignIdnNo());
        afterBaappexpand.setAssignName(caseData.getAssignName());
        afterBaappexpand.setAssignBrDate(caseData.getAssignBrDate());
        afterBaappexpand.setRaiseEvtMk(caseData.getRaiseEvtMk());
        afterBaappexpand.setUpdUser(userData.getEmpNo());
        afterBaappexpand.setUpdTime(DateUtility.getNowWestDateTime(true));
        baappexpandDao.updateDataForSurvivorPayeeDataUpdate(afterBaappexpand);
        // Baappexpand expand = new Baappexpand();
        // expand.setBaappbaseId(caseData.getBaappbaseId());
        // expand.setBaappexpandId(new BigDecimal(caseData.getBaappexpandId()));
        // expand.setApNo(caseData.getApNo());
        // expand.setSeqNo(caseData.getSeqNo());
        // expand.setAssignIdnNo(caseData.getAssignIdnNo());
        // expand.setAssignName(caseData.getAssignName());
        // expand.setAssignBrDate(caseData.getAssignBrDate());
        // expand.setRaiseEvtMk(caseData.getRaiseEvtMk());
        // expand.setUpdUser(userData.getEmpNo());
        // expand.setUpdTime(DateUtility.getNowWestDateTime(true));
        // baappexpandDao.updateDataForSurvivorPayeeDataUpdate(expand);

        // 更新資料到 給付主檔
        for (int i = 0; i < afterList.size(); i++) {
            Baappbase obj = (Baappbase) afterList.get(i);
            baappbaseDao.updateDataForSurvivorPayeeData(obj);
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
            FrameworkLogUtil.doUpdateLog(beforeBaappexpand, afterBaappexpand);
        }
        log.debug("Insert BAAPPBASE Finished ...");
        // ]

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");
        for (int i = 0; i < baapplogList2.size(); i++) {
            BaappbaseDataUpdateCase logObj = baapplogList2.get(i);
            baappbaseId = logObj.getBaappbaseId();
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

        // // 新增資料到 更正記錄檔

        //
        // // [
        // log.debug("Start Insert BAAPPLOG ...");
        // Baapplog baapplog = new Baapplog();
        // baapplog.setBaappbaseId(baappbase.getBaappbaseId());// 資料列編號

        //
        // baapplog.setStatus("U");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        // baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        // baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplogDao.insertData(baapplog);
        //
        // log.debug("Insert BAAPPLOG Finished ...");
        // // ]
    }

    /**
     * 依傳入條件取得 給付主檔 for 受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public BadaprDataCase selectPayeeDataForBadapr(String apNo) {
        Badapr detailData = badaprDao.selectPayeeDataForBadapr(apNo);

        BadaprDataCase caseData = new BadaprDataCase();
        if (detailData != null) {
            BeanUtility.copyProperties(caseData, detailData);
        }
        return caseData;
    }

    /**
     * 依傳入條件取得 給付主檔 for 失能受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public BadaprDataCase selectDisabledPayeeDataForBadapr(String apNo) {
        Badapr detailData = badaprDao.selectDisabledPayeeDataForBadapr(apNo);

        BadaprDataCase caseData = new BadaprDataCase();
        if (detailData != null) {
            BeanUtility.copyProperties(caseData, detailData);
        }
        return caseData;
    }

    /**
     * 依傳入條件取得 給付主檔 for 遺屬年金受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public BadaprDataCase selectSurvivorPayeeDataHeaderForBadapr(String apNo) {
        Badapr detailData = badaprDao.selectSurvivorPayeeDataHeaderForBadapr(apNo);

        BadaprDataCase caseData = new BadaprDataCase();
        if (detailData != null) {
            BeanUtility.copyProperties(caseData, detailData);
        }
        return caseData;
    }

    /**
     * 依傳入條件取得 給付主檔 for 遺屬年金受款人資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<SurvivorPayeeDataUpdateCase> selectSurvivorPayeeDataListForBadapr(String apNo) {
        List<SurvivorPayeeDataUpdateCase> detailDataList = badaprDao.selectSurvivorPayeeDataListForBadapr(apNo);
        return detailDataList;
    }

    /**
     * 依傳入條件取得 編審註記(<code>BAPADCHK</code>) 資料
     * 
     * @param baappbaseId 資料列編號
     * @return <code>BAPADCHK</code> 物件
     */
    public List<ChkFileCase> getChkListBy(BigDecimal baappbaseId) {
        return bachkfileDao.selectChkListBy(baappbaseId);
    }

    /**
     * 依傳入條件取得 編審註記(<code>BAPADCHK</code>) 資料 FOR 案件資料更正
     * 
     * @param baappbaseId 資料列編號
     * @return <code>Map<String, Object></code> List
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getChkListForAppUpdateBy(BigDecimal baappbaseId) {
        List<ChkFileCase> resultList = bachkfileDao.selectChkListBy(baappbaseId);
        String isShowCvldtlName = "N";// 是否顯示戶籍姓名
        String isShowLoanMk = "N";// 是否顯示不須抵銷紓困貸款
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 payYm
        // Value則是同為該 payYm 下的所有編審註記資料

        Map<String, List<ChkFileCase>> map = new TreeMap<String, List<ChkFileCase>>();
        for (int i = 0; i < resultList.size(); i++) {
            ChkFileCase obj = (ChkFileCase) resultList.get(i);
            map.put(obj.getPayYm(), new ArrayList<ChkFileCase>());
        }

        // 當編審註記有出現BD時,需顯示'戶籍姓名'欄位(Disabled)
        for (int i = 0; i < resultList.size(); i++) {
            ChkFileCase obj = (ChkFileCase) resultList.get(i);
            if (("BD").equals(obj.getChkFileCode())) {
                isShowCvldtlName = "Y";
            }
            if (("LS").equals(obj.getChkFileCode())) {
                isShowLoanMk = "Y";
            }
            (map.get(obj.getPayYm())).add(obj);
        }

        // 將 分類好的 map 轉為 list
        List<ChkFileCollectionCase> returnList = new ArrayList<ChkFileCollectionCase>();
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            String key = (String) it.next();
            List<ChkFileCase> list = map.get(key);
            ChkFileCollectionCase caseObj = new ChkFileCollectionCase();
            caseObj.setPayYm(key);
            caseObj.setChkFileCollection(list);
            returnList.add(caseObj);
        }

        returnMap.put("ChkFile", returnList);
        returnMap.put("isShowCvldtlName", isShowCvldtlName);
        returnMap.put("isShowLoanMk", isShowLoanMk);
        return returnMap;
    }

    /**
     * 依傳入條件取得 年資維護介接檔(<code>BASENIMAINT</code>) 資料List
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param seniTyp 處理狀態
     * @return 內含 <code>BasenimaintCase</code> 物件的 List
     */
    public List<BasenimaintCase> selectBasenimaintDataList(String apNo, String seqNo, String seniTyp) {
        List<Basenimaint> list = basenimaintDao.selectDataBy(apNo, seqNo, seniTyp);
        List<BasenimaintCase> returnList = new ArrayList<BasenimaintCase>();

        for (int i = 0; i < list.size(); i++) {
            Basenimaint obj = list.get(i);
            BasenimaintCase caseObj = new BasenimaintCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 年資維護介接檔(<code>BASENIMAINT</code>) 序號
     * 
     * @param apNo 受理編號
     * @param seniTyp 處理狀態
     * @return
     */
    public String getNewBasenimaintSeqNo(String apNo, String seniTyp) {
        return basenimaintDao.selectSeqNoBy(apNo, seniTyp);
    }

    /**
     * 新增 年資維護介接檔(<code>BASENIMAINT</code>)
     * 
     * @param userData 使用者資料
     * @param baappbase 給付主檔
     * @param basenimaint 年資維護介接檔
     * @param updateType 判斷是否更新 轉公保資料/相關案件oldSeniab欄位: owes(欠費期間維護)-不UPDAETE; onceAmt(一次給付資料更正)-UPDAETE
     */
    public void insertBasenimaintData(UserBean userData, Baappbase baappbase, Basenimaint basenimaint, String updateType) {
        // 更新 轉公保資料

        Baappbase beforeBaappbase = new Baappbase();

        beforeBaappbase = baappbaseDao.selectDataForOnceAmtDataUpdateByLog(baappbase.getApNo());

        baappbaseDao.updateDataForOnceAmtDataUpdate(baappbase);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforeBaappbase, baappbase);
        }

        // 新增資料到 年資維護介接檔

        // [
        log.debug("Start Insert BASENIMAINT ...");
        // 相關要修改的主檔清單
        List<Baappbase> extModifyList = new ArrayList<Baappbase>();
        // 案件資料更正改前物件List
        List<Object> beforeList = new ArrayList<Object>();
        // 案件資料更正改後物件List
        List<Object> afterList = new ArrayList<Object>();
        Basenimaint beforeBasenimaint = new Basenimaint();

        // 填入SEQNO, UPDUSER, UPDTIME
        basenimaint.setSeqNo(getNewBasenimaintSeqNo(basenimaint.getApNo(), basenimaint.getSeniTyp()));// 序號
        basenimaint.setCrtUser(userData.getEmpNo());// 新增者代號

        basenimaint.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間

        // 判斷需要UPDATE的相關資料

        extModifyList = baappbaseDao.selectDataBy(null, basenimaint.getApNo(), null, null, null, null);
        for (int i = 0; i < extModifyList.size(); i++) {
            BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();

            // 根據baappbaseId取得詳細資料
            BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
            Baappbase beforeObj = new Baappbase();// 改前值物件

            Baappbase afterObj = new Baappbase();// 改後值物件

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                BeanUtility.copyProperties(beforeObj, extModifyObj);
                beforeList.add(beforeObj);
            }

            // 處理修改主檔後相關主檔需修改的欄位 / 案件資料更正相關主檔 改前改後值 for BAAPPLOG
            ApplicationDataUpdateForm objForm = new ApplicationDataUpdateForm();
            BeanUtility.copyProperties(objForm, extModifyObj);

            // 相關主檔需修改的欄位

            // [
            extModifyObj.setProcStat("01");
            extModifyObj.setUpdUser(userData.getEmpNo());
            extModifyObj.setUpdTime(basenimaint.getCrtTime());
            // ]
            BeanUtility.copyProperties(afterObj, extModifyObj);
            afterList.add(afterObj);
        }

        // 新增資料到 年資維護介接檔

        basenimaintDao.insertData(basenimaint);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(basenimaint);
        }

        BeanUtility.copyProperties(beforeBasenimaint, basenimaint);

        // 更新資料到 年資維護介接檔

        basenimaintDao.updateData(basenimaint);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {

            FrameworkLogUtil.doUpdateLog(beforeBasenimaint, basenimaint);
        }

        // 判斷是否更新 轉公保資料

        if ((UPDATE_OLDSENIAB_ONCEAMT).equals(updateType)) {
            // 更新 轉公保資料

            beforeBaappbase = baappbaseDao.selectDataForOnceAmtDataUpdateByLog(baappbase.getApNo());

            baappbaseDao.updateDataForOnceAmtDataUpdate(baappbase);

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBaappbase, baappbase);
            }

        }

        // 更新處理狀態資料到 給付主檔
        for (int i = 0; i < afterList.size(); i++) {
            Baappbase obj = (Baappbase) afterList.get(i);
            baappbaseDao.updateProcStat(obj.getBaappbaseId(), obj.getProcStat(), obj.getUpdUser(), obj.getUpdTime(), new String[] { "01", "10", "11", "12", "20", "30" }, "in");
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            afterList = new ArrayList<Object>();

            // 案件資料修改 相關主檔改後值物件

            for (int i = 0; i < extModifyList.size(); i++) {
                BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();
                BaappbaseDataUpdateCase modifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase afterObj = new Baappbase();// 改後值物件

                BeanUtility.copyProperties(afterObj, modifyObj);
                afterList.add(afterObj);
            }
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);

        }
        log.debug("Insert BASENIMAINT Finished ...");
        // ]
    }

    /**
     * 修改 年資維護介接檔(<code>BASENIMAINT</code>)
     * 
     * @param userData 使用者資料
     * @param baappbase 給付主檔
     * @param basenimaint 年資維護介接檔
     * @param updateType 判斷是否更新 轉公保資料/相關案件oldSeniab欄位: owes(欠費期間維護)-不UPDAETE; onceAmt(一次給付資料更正)-UPDAETE
     */
    public void updateBasenimaintData(UserBean userData, Baappbase baappbase, Basenimaint basenimaint, String updateType, Basenimaint beforeBasenimaint) {
        // 修改資料到 年資維護介接檔

        // [
        log.debug("Start Update BASENIMAINT ...");
        // 相關要修改的主檔清單
        List<Baappbase> extModifyList = new ArrayList<Baappbase>();
        // 案件資料更正改前物件List
        List<Object> beforeList = new ArrayList<Object>();
        // 案件資料更正改後物件List
        List<Object> afterList = new ArrayList<Object>();
        // 填入UPDUSER, UPDTIME
        basenimaint.setUpdUser(userData.getEmpNo());// 異動者代號

        basenimaint.setUpdTime(DateUtility.getNowWestDateTime(true));// 異動日期時間

        // 判斷需要UPDATE的相關資料

        extModifyList = baappbaseDao.selectDataBy(null, basenimaint.getApNo(), null, null, null, null);

        for (int i = 0; i < extModifyList.size(); i++) {
            BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();

            // 根據baappbaseId取得詳細資料
            BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
            Baappbase beforeObj = new Baappbase();// 改前值物件

            Baappbase afterObj = new Baappbase();// 改後值物件

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                BeanUtility.copyProperties(beforeObj, extModifyObj);
                beforeList.add(beforeObj);
            }

            // 處理修改主檔後相關主檔需修改的欄位 / 案件資料更正相關主檔 改前改後值 for BAAPPLOG
            ApplicationDataUpdateForm objForm = new ApplicationDataUpdateForm();
            BeanUtility.copyProperties(objForm, extModifyObj);

            // 相關主檔需修改的欄位

            // [
            extModifyObj.setProcStat("01");
            extModifyObj.setUpdUser(userData.getEmpNo());
            extModifyObj.setUpdTime(basenimaint.getUpdTime());
            // ]
            BeanUtility.copyProperties(afterObj, extModifyObj);
            afterList.add(afterObj);
        }

        // 更新資料到 年資維護介接檔

        basenimaintDao.updateData(basenimaint);

        // 判斷是否更新 轉公保資料/相關案件oldSeniab欄位
        if ((UPDATE_OLDSENIAB_ONCEAMT).equals(updateType)) {
            // 更新 轉公保資料

            baappbaseDao.updateDataForOnceAmtDataUpdate(baappbase);
            // 修改相關案件oldSeniab欄位
            basenimaintDao.updateDataForOldSeniab(basenimaint);
        }

        // 更新處理狀態資料到 給付主檔
        for (int i = 0; i < afterList.size(); i++) {
            Baappbase obj = (Baappbase) afterList.get(i);
            baappbaseDao.updateProcStat(obj.getBaappbaseId(), obj.getProcStat(), obj.getUpdUser(), obj.getUpdTime(), new String[] { "01", "10", "11", "12", "20", "30" }, "in");
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            afterList = new ArrayList<Object>();

            // 案件資料修改 相關主檔改後值物件

            for (int i = 0; i < extModifyList.size(); i++) {
                BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();
                BaappbaseDataUpdateCase modifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase afterObj = new Baappbase();// 改後值物件

                BeanUtility.copyProperties(afterObj, modifyObj);
                afterList.add(afterObj);
            }
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
            FrameworkLogUtil.doUpdateLog(beforeBasenimaint, basenimaint);
        }

        log.debug("Update BASENIMAINT Finished ...");
        // ]
    }

    /**
     * 刪除 年資維護介接檔(<code>BASENIMAINT</code>)
     * 
     * @param userData 使用者資料
     * @param basenimaint 年資維護介接檔
     */
    public void daleteBasenimaintData(UserBean userData, Basenimaint basenimaint) {
        // 修改資料到 年資維護介接檔

        // [
        log.debug("Start Delete BASENIMAINT ...");
        // 相關要修改的主檔清單
        List<Baappbase> extModifyList = new ArrayList<Baappbase>();
        // 案件資料更正改前物件List
        List<Object> beforeList = new ArrayList<Object>();
        // 案件資料更正改後物件List
        List<Object> afterList = new ArrayList<Object>();
        List<Basenimaint> beforeBasenimaint = new ArrayList<Basenimaint>(); // 年資維護介接檔改前值

        // 判斷需要UPDATE的相關資料

        extModifyList = baappbaseDao.selectDataBy(null, basenimaint.getApNo(), null, null, null, null);

        for (int i = 0; i < extModifyList.size(); i++) {
            BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();
            // 根據baappbaseId取得詳細資料
            BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
            Baappbase beforeObj = new Baappbase();// 改前值物件

            Baappbase afterObj = new Baappbase();// 改後值物件

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                BeanUtility.copyProperties(beforeObj, extModifyObj);
                beforeList.add(beforeObj);
            }

            // 處理修改主檔後相關主檔需修改的欄位 / 案件資料更正相關主檔 改前改後值 for BAAPPLOG
            ApplicationDataUpdateForm objForm = new ApplicationDataUpdateForm();
            BeanUtility.copyProperties(objForm, extModifyObj);

            // 相關主檔需修改的欄位

            // [
            String updTime = DateUtility.getNowWestDateTime(true);// 異動日期時間
            extModifyObj.setProcStat("01");
            extModifyObj.setUpdUser(userData.getEmpNo());
            extModifyObj.setUpdTime(updTime);
            // ]
            BeanUtility.copyProperties(afterObj, extModifyObj);
            afterList.add(afterObj);
        }
        beforeBasenimaint = basenimaintDao.selectDataBy(basenimaint.getApNo(), basenimaint.getSeqNo(), basenimaint.getSeniTyp());
        // 刪除 年資維護介接檔 資料
        basenimaintDao.deleteData(basenimaint.getApNo(), basenimaint.getSeqNo(), basenimaint.getSeniTyp());

        // 更新處理狀態資料到 給付主檔
        for (int i = 0; i < afterList.size(); i++) {
            Baappbase obj = (Baappbase) afterList.get(i);
            baappbaseDao.updateProcStat(obj.getBaappbaseId(), obj.getProcStat(), obj.getUpdUser(), obj.getUpdTime(), new String[] { "01", "10", "11", "12", "20", "30" }, "in");
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            afterList = new ArrayList<Object>();

            // 案件資料修改 相關主檔改後值物件

            for (int i = 0; i < extModifyList.size(); i++) {
                BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();
                BaappbaseDataUpdateCase modifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase afterObj = new Baappbase();// 改後值物件

                BeanUtility.copyProperties(afterObj, modifyObj);
                afterList.add(afterObj);
            }
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
            FrameworkLogUtil.doDeleteListLog(beforeBasenimaint);
        }

        log.debug("Delete BASENIMAINT Finished ...");
        // ]
    }

    /**
     * 修改 給付主檔(<code>BAAPPBASE</code>) for 一次給付資料更正
     * 
     * @param userData 使用者資料
     * @param baappbase 給付主檔
     */
    public void updateBaappbaseDataForOnceAmtUpdate(UserBean userData, Baappbase baappbase) {
        // 修改資料到 年資維護介接檔

        // [
        log.debug("Start Update BASENIMAINT ...");
        // 相關要修改的主檔清單
        List<Baappbase> extModifyList = new ArrayList<Baappbase>();
        // 案件資料更正改前物件List
        List<Object> beforeList = new ArrayList<Object>();
        // 案件資料更正改後物件List
        List<Object> afterList = new ArrayList<Object>();
        // 填入UPDUSER, UPDTIME
        baappbase.setUpdUser(userData.getEmpNo());// 異動者代號

        baappbase.setUpdTime(DateUtility.getNowWestDateTime(true));// 異動日期時間

        // 判斷需要UPDATE的相關資料

        extModifyList = baappbaseDao.selectDataBy(null, baappbase.getApNo(), null, null, null, null);
        for (int i = 0; i < extModifyList.size(); i++) {
            BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();

            // 根據baappbaseId取得詳細資料
            BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
            Baappbase beforeObj = new Baappbase();// 改前值物件

            Baappbase afterObj = new Baappbase();// 改後值物件

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                BeanUtility.copyProperties(beforeObj, extModifyObj);
                beforeList.add(beforeObj);
            }

            // 處理修改主檔後相關主檔需修改的欄位 / 案件資料更正相關主檔 改前改後值 for BAAPPLOG
            ApplicationDataUpdateForm objForm = new ApplicationDataUpdateForm();
            BeanUtility.copyProperties(objForm, extModifyObj);

            // 相關主檔需修改的欄位

            // [
            extModifyObj.setProcStat("01");
            extModifyObj.setUpdUser(userData.getEmpNo());
            extModifyObj.setUpdTime(baappbase.getUpdTime());
            // ]
            BeanUtility.copyProperties(afterObj, extModifyObj);
            afterList.add(afterObj);
        }

        // 更新資料到 年資維護介接檔

        // basenimaintDao.updateData(basenimaint);
        // // 判斷是否更新 轉公保資料/相關案件oldSeniab欄位
        // if ((UPDATE_OLDSENIAB_ONCEAMT).equals(updateType)) {
        // 更新 轉公保資料

        baappbaseDao.updateDataForOnceAmtDataUpdate(baappbase);
        // // 修改相關案件oldSeniab欄位
        // basenimaintDao.updateDataForOldSeniab(basenimaint);
        // }

        // 更新處理狀態資料到 給付主檔
        for (int i = 0; i < afterList.size(); i++) {
            Baappbase obj = (Baappbase) afterList.get(i);
            baappbaseDao.updateProcStat(obj.getBaappbaseId(), obj.getProcStat(), obj.getUpdUser(), obj.getUpdTime(), new String[] { "01", "10", "11", "12", "20", "30" }, "in");
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            afterList = new ArrayList<Object>();

            // 案件資料修改 相關主檔改後值物件

            for (int i = 0; i < extModifyList.size(); i++) {
                BigDecimal baappbaseId = ((Baappbase) extModifyList.get(i)).getBaappbaseId();
                BaappbaseDataUpdateCase modifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase afterObj = new Baappbase();// 改後值物件

                BeanUtility.copyProperties(afterObj, modifyObj);
                afterList.add(afterObj);
            }
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        }

        log.debug("Update BASENIMAINT Finished ...");
        // ]
    }

    /**
     * 依傳入條件取得給付編審檔(<code>BACHKFILE</code>) 資料 FOR 編審註記程度調整
     * 
     * @param CheckMarkLevelAdjustCase
     * @param orderBy
     * @return <code>CheckMarkLevelAdjustCase</code> 物件
     */
    public CheckMarkLevelAdjustCase getChkListForCheckMarkLevelAdjust(CheckMarkLevelAdjustCase Case, String orderBy) {
        List<Bachkfile> dataList = bachkfileDao.selectChkListForCheckMarkLevelAdjust(Case.getApNo(), orderBy);

        if (dataList.size() == 0) {
            return Case;
        }
        else {
            // 先把日期換成民國年月
            for (Bachkfile bachkfile : dataList) {
                bachkfile.setIssuYm(DateUtility.changeWestYearMonthType(bachkfile.getIssuYm()));
                bachkfile.setPayYm(DateUtility.changeWestYearMonthType(bachkfile.getPayYm()));
            }
        }

        // 將核定年月 存入CASE
        Case.setIssuYm(dataList.get(0).getIssuYm());
        Case.setProcStat(dataList.get(0).getProcStat());
        Case.setCaseTyp(dataList.get(0).getCaseTyp());
        // 存放依照畫面點選的分類LIST資料
        ArrayList<ArrayList<Bachkfile>> sortList = new ArrayList<ArrayList<Bachkfile>>();

        // 編審註記：依給付年月排序 分出LIST
        if ("1".equals(orderBy)) {
            String payYm = dataList.get(0).getPayYm();
            ArrayList<Bachkfile> tempList = new ArrayList<Bachkfile>();
            // 編審註記：依給付年月排序 說明資料
            ArrayList<String> payYmList = new ArrayList<String>();
            payYmList.add(payYm);
            for (int i = 0; i < dataList.size(); i++) {
                if (payYm.equals(dataList.get(i).getPayYm())) {
                    tempList.add(dataList.get(i));
                }
                else {
                    sortList.add(tempList);
                    tempList = new ArrayList<Bachkfile>();
                    payYm = dataList.get(i).getPayYm();
                    payYmList.add(payYm);
                    tempList.add(dataList.get(i));
                }
            }
            sortList.add(tempList);
            Case.setDetailList(sortList);
            Case.setPayYmList(payYmList);
        }

        // 編審註記：依受款人序排序 分出LIST
        if ("2".equals(orderBy)) {
            String seqNo = dataList.get(0).getSeqNo();
            ArrayList<Bachkfile> tempList = new ArrayList<Bachkfile>();
            // 編審註記：依受款人排序 說明資料
            ArrayList<Bachkfile> seqnoList = new ArrayList<Bachkfile>();
            seqnoList.add(dataList.get(0));
            for (int i = 0; i < dataList.size(); i++) {
                if (seqNo.equals(dataList.get(i).getSeqNo())) {
                    tempList.add(dataList.get(i));
                }
                else {
                    sortList.add(tempList);
                    tempList = new ArrayList<Bachkfile>();
                    seqNo = dataList.get(i).getSeqNo();
                    seqnoList.add(dataList.get(i));
                    tempList.add(dataList.get(i));
                }
            }
            sortList.add(tempList);
            Case.setDetailList(sortList);
            Case.setSeqnoList(seqnoList);
        }

        // 編審註記：依編審註記排序 分出LIST
        if ("3".equals(orderBy)) {
            String mark = dataList.get(0).getChkCode() + "-" + dataList.get(0).getChkResult();
            ArrayList<Bachkfile> tempList = new ArrayList<Bachkfile>();
            // 編審註記：依編審註記排序 說明資料
            ArrayList<String> markList = new ArrayList<String>();
            markList.add(mark);
            for (int i = 0; i < dataList.size(); i++) {
                if (mark.equals(dataList.get(i).getChkCode() + "-" + dataList.get(i).getChkResult())) {
                    tempList.add(dataList.get(i));
                }
                else {
                    sortList.add(tempList);
                    tempList = new ArrayList<Bachkfile>();
                    mark = dataList.get(i).getChkCode() + "-" + dataList.get(i).getChkResult();
                    markList.add(mark);
                    tempList.add(dataList.get(i));
                }
            }
            sortList.add(tempList);
            Case.setDetailList(sortList);
            Case.setMarkList(markList);
        }

        return Case;
    }

    /**
     * 依傳入條件更新給付編審檔(<code>BACHKFILE</code>) 資料 FOR 編審註記程度調整
     * 
     * @param apNo
     * @param baChkFileIdW
     * @param baChkFileIdO
     * @param userDat
     */
    public void updateBachkfileForCheckMarkLevelAdjust(String apNo, String baChkFileIdW, String baChkFileIdO, UserBean userData) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(apNo);

        String[] idW = baChkFileIdW.split(",");// 存放要更新為W的baChkFileId
        String[] idO = baChkFileIdO.split(",");// 存放要更新為O的baChkFileId

        // 依照畫面點選的baChkFileId 更新
        for (int i = 0; i < idW.length; i++) {
            Bachkfile beforeBachkfile = bachkfileDao.selectChkCodePostByLog(idW[i]);
            bachkfileDao.updateChkCodePost(idW[i], "W");
            Bachkfile afterBachkfile = bachkfileDao.selectChkCodePostByLog(idW[i]);

            // Insert MMAPLOG
            if (StringUtils.isNotBlank(idW[i]) && beforeBachkfile != null) {
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateLog(beforeBachkfile, afterBachkfile);
                }
            }
        }
        // 依照畫面點選的baChkFileId 更新
        for (int i = 0; i < idO.length; i++) {
            Bachkfile beforeBachkfile = bachkfileDao.selectChkCodePostByLog(idO[i]);
            bachkfileDao.updateChkCodePost(idO[i], "O");
            Bachkfile afterBachkfile = bachkfileDao.selectChkCodePostByLog(idO[i]);

            // Insert MMAPLOG
            if (StringUtils.isNotBlank(idO[i]) && beforeBachkfile != null) {
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateLog(beforeBachkfile, afterBachkfile);
                }
            }
        }
        // // 合格記錄筆數
        // BigDecimal count = bachkfileDao.countForCheckMarkLevelAdjust(apNo);
        //
        // // 「合格記錄筆數」=0時，則需一併變更『給付主檔_BAAPPBASE』,『給付核定檔_BADAPR』的「合格註記」資料。

        //
        // if (count.intValue() == 0) {
        // baappbaseDao.updateManchkMk(apNo, userData.getUserId(), DateUtility.getNowWestDateTime(true));
        // badaprDao.updateManchkMk(apNo, DateUtility.getNowWestDateTime(true));
        // }
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 一次給付資料更正-轉公保資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateReSeniData(Baappbase baappbase) {
        baappbaseDao.updateDataForOnceAmtDataUpdate(baappbase);
    }

    /**
     * 轉換 案件更正資料畫面日期欄位<br>
     * 
     * @param modifyForm
     * @return
     */
    public ApplicationDataUpdateForm transApplicationDataUpdateFormData(ApplicationDataUpdateForm modifyForm) {
        // 將FORM中日期欄位西元轉民國
        if (StringUtils.isNotBlank(modifyForm.getAppDate())) {
            modifyForm.setAppDate(DateUtility.changeDateType(modifyForm.getAppDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getEvtBrDate())) {
            modifyForm.setEvtBrDate(DateUtility.changeDateType(modifyForm.getEvtBrDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getEvtDieDate())) {
            modifyForm.setEvtDieDate(DateUtility.changeDateType(modifyForm.getEvtDieDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getEvtJobDate())) {
            modifyForm.setEvtJobDate(DateUtility.changeDateType(modifyForm.getEvtJobDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getBenBrDate())) {
            modifyForm.setBenBrDate(DateUtility.changeDateType(modifyForm.getBenBrDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getBenDieDate())) {
            modifyForm.setBenDieDate(DateUtility.changeDateType(modifyForm.getBenDieDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getCloseDate())) {
            modifyForm.setCloseDate(DateUtility.changeDateType(modifyForm.getCloseDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getOldAppDate())) {
            modifyForm.setOldAppDate(DateUtility.changeDateType(modifyForm.getOldAppDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getOldEvtBrDate())) {
            modifyForm.setOldEvtBrDate(DateUtility.changeDateType(modifyForm.getOldEvtBrDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getOldEvtDieDate())) {
            modifyForm.setOldEvtDieDate(DateUtility.changeDateType(modifyForm.getOldEvtDieDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getOldEvtJobDate())) {
            modifyForm.setOldEvtJobDate(DateUtility.changeDateType(modifyForm.getOldEvtJobDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getOldBenBrDate())) {
            modifyForm.setOldBenBrDate(DateUtility.changeDateType(modifyForm.getOldBenBrDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getOldBenDieDate())) {
            modifyForm.setOldBenDieDate(DateUtility.changeDateType(modifyForm.getOldBenDieDate()));
        }
        if (StringUtils.isNotBlank(modifyForm.getOldCloseDate())) {
            modifyForm.setOldCloseDate(DateUtility.changeDateType(modifyForm.getOldCloseDate()));
        }
        return modifyForm;
    }

    /**
     * 依傳入條件取得 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<ApplicationUpdateDupeIdnNoCase> getDupeIdnNoData(String apNo) {
        List<Badupeidn> list = badupeidnDao.selectDataBy(apNo, "0000", null, null);
        List<ApplicationUpdateDupeIdnNoCase> returnList = new ArrayList<ApplicationUpdateDupeIdnNoCase>();
        for (int i = 0; i < list.size(); i++) {
            Badupeidn obj = list.get(i);
            ApplicationUpdateDupeIdnNoCase caseObj = new ApplicationUpdateDupeIdnNoCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 更新資料到 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>)
     * 
     * @param badupeidn <code>Badupeidn</code> 物件
     */
    public void updateBadupeidnData(Badupeidn badupeidn) {
        badupeidnDao.updateData(badupeidn);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 案件資料更正-事故者/受款人資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateEvtBenDataForApplicationUpdate(UserBean userData, String apNo, String evtName, String evtIdnNo, String evtBrDate) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(apNo);

        // 須修改的物件清單
        List<Baappbase> extModifyList = new ArrayList<Baappbase>();
        // 改前物件List
        List<Object> beforeList = new ArrayList<Object>();
        // 改後物件List
        List<Object> afterList = new ArrayList<Object>();

        // 取得受款人社福識別碼
        List<Cvldtl> cvldtl = cvldtlDao.selectDataBy(evtIdnNo, evtBrDate);
        String npIds = "";
        if (cvldtl.size() > 0) {
            npIds = cvldtl.get(0).getNpIds();
        }

        // 取得改前值物件

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            extModifyList = baappbaseDao.selectDataBy(null, apNo, null, new String[] { "01", "10", "11", "12", "20", "30" }, "in", null);

            for (int i = 0; i < extModifyList.size(); i++) {
                Baappbase obj = baappbaseDao.selectDetailDataBy((extModifyList.get(i)).getBaappbaseId(), null, null, null);
                Baappbase beforeObj = new Baappbase();
                BeanUtility.copyProperties(beforeObj, obj);
                Baappbase afterObj = new Baappbase();
                BeanUtility.copyProperties(afterObj, obj);

                // 改前值物件存入改前值物件List
                beforeList.add(obj);
                // 處理改後值物件

                afterObj.setApNo(apNo);
                afterObj.setEvtName(evtName);
                afterObj.setEvtIdnNo(evtIdnNo.substring(0, 10));
                afterObj.setEvtBrDate(evtBrDate);
                afterObj.setEvtIds(npIds);
                afterObj.setDupeIdnNoMk("2");
                afterObj.setProcStat("01");
                afterObj.setUpdUser(userData.getEmpNo());
                afterObj.setUpdTime(DateUtility.getNowWestDateTime(true));
                afterObj.setBenName(evtName);
                afterObj.setBenIdnNo(evtIdnNo.substring(0, 10));
                afterObj.setBenBrDate(evtBrDate);
                afterObj.setBenIds(npIds);
            }
        }

        // 主檔資料
        Baappbase baappbase = new Baappbase();
        baappbase.setApNo(apNo);
        baappbase.setEvtName(evtName);
        baappbase.setEvtIdnNo(evtIdnNo.substring(0, 10));
        baappbase.setEvtBrDate(evtBrDate);
        baappbase.setEvtIds(npIds);
        baappbase.setDupeIdnNoMk("2");
        baappbase.setProcStat("01");
        baappbase.setUpdUser(userData.getEmpNo());
        baappbase.setUpdTime(DateUtility.getNowWestDateTime(true));
        baappbase.setBenName(evtName);
        baappbase.setBenIdnNo(evtIdnNo.substring(0, 10));
        baappbase.setBenBrDate(evtBrDate);
        baappbase.setBenIds(npIds);

        // 被保險人承保身分證號重號檔資料

        Badupeidn badupeidn = new Badupeidn();
        badupeidn.setApNo(apNo);
        badupeidn.setSeqNo("0000");
        badupeidn.setUpdUser(userData.getEmpNo());
        badupeidn.setUpdTime(DateUtility.getNowWestDateTime(true));

        // 修改 事故者資料

        baappbaseDao.updateEvtDataForApplicationUpdate(baappbase);

        // 修改 受款人

        baappbaseDao.updateBenDataForApplicationUpdate(baappbase);

        // 修改 被保險人承保身分證號重號檔資料

        badupeidn.setSelMk("1");
        badupeidnDao.updateData(badupeidn);
        badupeidn.setSelMk("2");
        badupeidn.setName(evtName);
        badupeidn.setIdnNo(evtIdnNo);
        badupeidn.setBrDate(evtBrDate);
        badupeidnDao.updateData(badupeidn);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);
        }
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者存活狀態
     * 
     * @param apNo 受理編號
     * @return <code>Integer</code> 物件
     */
    public boolean checkEvtAlive(String apNo) {
        boolean alive = false;
        Integer status = baappbaseDao.selectEvtAliveStatusBy(apNo);
        if (status > 0) {
            alive = true;
        }
        return alive;
    }

    /**
     * 依傳入條件取得 給付編審檔 For 受款人資料更正作業 Q1
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Integer selectForPayeeDataUpdateQ1(String apNo, String issuYm) {
        return bachkfileDao.selectForPayeeDataUpdateQ1(apNo, issuYm);
    }

    /**
     * 依傳入條件取得 給付主檔 For 受款人資料更正作業 Q2
     * 
     * @param apNo 受理編號
     * @return
     */
    public Integer selectForPayeeDataUpdateQ2(BigDecimal baappbaseId, String apNo) {
        // 先檢查事故者是否活著

        Integer status = baappbaseDao.selectEvtAliveStatusBy(apNo);
        List<Baappbase> resultList = new ArrayList<Baappbase>();
        // 事故者活著, 則benEvtRel=('1', '2', '3', '4', '5', '6' ,'7', 'C', 'E')
        if (status > 0) {
            resultList = baappbaseDao.selectBenListBy(baappbaseId, apNo, "1", new String[] { "1", "2", "3", "4", "5", "6", "7", "C", "E" }, null);
        }
        // //事故者死亡, 則benEvtRel=('2', '3', '4', '5', '6' ,'7', 'C', 'E')
        else {
            resultList = baappbaseDao.selectBenListBy(baappbaseId, apNo, "1", new String[] { "2", "3", "4", "5", "6", "7", "C", "E" }, null);
        }
        return resultList.size();
    }

    /**
     * 依傳入條件取得 給付主檔 For 受款人資料更正作業 關係欄位控制 Q1
     * 
     * @param apNo 受理編號
     * @return
     */
    public Integer selectForPayeeDataUpdateRelationQ1(String apNo) {
        return baappbaseDao.selectForPayeeDataUpdateRelationQ1(apNo);
    }

    /**
     * 依傳入條件取得 給付主檔 For 受款人資料更正作業 關係欄位控制 Q2
     * 
     * @param apNo 受理編號
     * @return
     */
    public Integer selectForPayeeDataUpdateRelationQ2(String apNo) {
        return baappbaseDao.selectForPayeeDataUpdateRelationQ2(apNo);
    }

    /**
     * 依傳入條件取得 給付主檔 For 受款人資料更正作業 關係欄位控制 Q1
     * 
     * @param apNo 受理編號
     * @return
     */
    public Integer selectForPayeeDataUpdateRelationQ3(String apNo) {
        return baappbaseDao.selectForPayeeDataUpdateRelationQ3(apNo);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 關係欄位控制
     * 
     * @param brDate 事故者出生日期
     * @param idnNo 事故者身份字號
     */
    public Integer selectForPayeeDataUpdateRelationQ4(String brDate, String idnNo) {
        return baappbaseDao.selectForPayeeDataUpdateRelationQ4(brDate, idnNo);
    }

    /**
     * 依傳入條件取得 給付編審檔 For 失能年金受款人資料更正作業是否顯示身分查核年月 檢查傳入的受款人是否有W1,WH,WJ註記
     * 
     * @param apNo 受理編號
     * @param seqNo 受款序號
     * @param issuYm 核定年月
     * @return
     */
    public boolean displayIdnChkYearMonth(String apNo, String seqNo, String issuYm) {
        return bachkfileDao.displayIdnChkYearMonth(apNo, seqNo, issuYm);
    }

    /**
     * 依傳入條件取得 重新查核失能程度檔(<code>BARECHECK</code>) 資料 FOR 受理審核清單 重新查核失能程度 資料 資料庫是否已有同月份資料
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @param comReChkYm 完成重新查核年月
     * @return <code>Boolean</code>
     */
    public Boolean selectBareCheckDataCountBy(String apNo, String seqNo, String comReChkYm) {
        return barecheckDao.selectBareCheckDataCountBy(apNo, seqNo, comReChkYm);
    }

    /**
     * 依傳入條件取得 給付編審檔 For 遺屬年金受款人資料更正作業是否顯示再婚日期 檢查傳入的受款人是否有5G註記
     * 
     * @param apNo 受理編號
     * @param seqNo 受款序號
     * @param issuYm 核定年月
     * @return
     */
    public boolean displayDigamyDate(String apNo, String seqNo, String issuYm) {
        return bachkfileDao.displayDigamyDate(apNo, seqNo, issuYm);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 最新1筆的核付日期
     * 
     * @param apNo 受理編號
     * @return
     */
    public String getMaxAplPayDate(String apNo) {
        return badaprDao.selectMaxAplPayDate(apNo);
    }

    /**
     * 依傳入條件取得 出生日期錯誤參數檔(<code>BBCMF29</code>) 比對錯誤檔裡有無資料
     * 
     * @param IDNO 身分證號
     * @param BRDTE 出生日期
     * @return
     */
    public String checkIdnoExist(String idNo, String brDte) {
        String brDteChange = DateUtility.changeDateType(brDte);
        return bbcmf29Dao.checkIdnoExist(idNo, brDteChange);
    }

    /**
     * 依傳入條件取得 戶籍資料檔(<code>Cvldtl</code>) 郵遞區號
     * 
     * @param apNo 受理編號
     * @return
     */
    public String getCvldtlZip(String benIdnNo, String benBrDate) {
        String zip = null;
        // 取得戶籍資料檔

        List<Cvldtl> cvldtl = cvldtlDao.selectDataBy(benIdnNo, benBrDate);
        if (cvldtl.size() > 0)
            zip = cvldtl.get(0).getHzip();
        else
            zip = "";
        return zip;
    }

    /**
     * 依傳入條件取得 戶籍資料檔(<code>Cvldtl</code>) 地址
     * 
     * @param apNo 受理編號
     * @return
     */
    public String getCvldtlAddr(String benIdnNo, String benBrDate) {
        String addr = null;
        // 取得戶籍資料檔

        List<Cvldtl> cvldtl = cvldtlDao.selectDataBy(benIdnNo, benBrDate);
        if (cvldtl.size() > 0)
            addr = cvldtl.get(0).getHaddr();
        else
            addr = "";
        return addr;
    }

    /**
     * 依傳入條件取得 投保單位檔(<code>Caub</code>) 受款人姓名
     * 
     * @param ubNo 受款人身分證號(保險證號)
     * @return
     */
    public String getCaubUname(String ubNo) {
        String uname = null;
        // 取得投保單位檔資料

        List<Caub> caub = caubDao.selectPayeeDataUpdateCaubData(ubNo);
        if (caub.size() > 0)
            uname = caub.get(0).getUname();
        else
            uname = "";
        return uname;
    }

    /**
     * 依傳入條件取得 投保單位檔(<code>Caub</code>) 郵遞區號
     * 
     * @param ubNo 受款人身分證號(保險證號)
     * @return
     */
    public String getCaubCzpcd(String ubNo) {
        String czpcd = null;
        // 取得投保單位檔資料

        List<Caub> caub = caubDao.selectPayeeDataUpdateCaubData(ubNo);
        if (caub.size() > 0)
            czpcd = caub.get(0).getCzpcd();
        else
            czpcd = "";
        return czpcd;
    }

    /**
     * 依傳入條件取得 投保單位檔(<code>Caub</code>) 通訊地址
     * 
     * @param ubNo 受款人身分證號(保險證號)
     * @return
     */
    public String getCaubCaddr(String ubNo) {
        String caddr = null;
        // 取得投保單位檔資料

        List<Caub> caub = caubDao.selectPayeeDataUpdateCaubData(ubNo);
        if (caub.size() > 0)
            caddr = caub.get(0).getCaddr();
        else
            caddr = "";
        return caddr;
    }

    /**
     * 依傳入條件取得 投保單位檔(<code>Caub</code>) 電話
     * 
     * @param ubNo 受款人身分證號(保險證號)
     * @return
     */
    public String getCaubTel(String ubNo) {
        String tel = null;
        // 取得投保單位檔資料

        List<Caub> caub = caubDao.selectPayeeDataUpdateCaubData(ubNo);
        if (caub.size() > 0)
            tel = caub.get(0).getTel();
        else
            tel = "";
        return tel;
    }

    /**
     * 依傳入條件取得 投保單位檔(<code>Caub</code>) 電話
     * 
     * @param gvCd1 受款人身分證號(保險證號)
     * @return
     */
    public String getBbcmf08Data(String gvCd1) {
        return bbcmf08Dao.selectBbcmf08DataBy(gvCd1);
    }

    /**
     * 依傳入條件刪除 給付主檔(<code>BAAPPBASE</code>) 債務繼承人資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappbase</code> 物件
     */
    public void deleteForInheritorRegister(String apNo, String seqNo, BigDecimal baappbaseId, UserBean userData) {
        baappbaseDao.deleteForInheritorRegister(apNo, seqNo);

        // 新增資料到 更正記錄檔
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
    }

    /**
     * 依傳入條件刪除 重新查核失能程度檔(<code>BARECHECK</code>) 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param reChkYm 重新查核失能程度年月
     * @return <code>barecheck</code> 物件
     */
    public void deleteDataForBareCheck(String apNo, String seqNo, String reChkYm) {
        barecheckDao.deleteDataForBareCheck(apNo, seqNo, reChkYm);
    }

    /**
     * 刪除 受款人資料
     * 
     * @param baappbase 給付主檔
     */
    public void deletepPayeeDataUpdate(UserBean userData, Baappbase caseData, String progName) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(caseData.getApNo());

        // 更新資料到 給付主檔
        // [
        log.debug("Start delete BAAPPBASE ...");
        List<Bachkfile> beforeBachkfileList = new ArrayList<Bachkfile>(); // 給付編審檔改前值

        List<Baappbase> beforeBaappbaseList = new ArrayList<Baappbase>(); // 給付主檔刪除前值

        Baappbase beforeBaappbase = new Baappbase(); // 給付主檔改前值

        BeanUtility.copyProperties(beforeBaappbase, caseData);

        // 刪除SEQNO該階層底下之相關資料
        if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 3) {
            log.debug("SeqNo:" + caseData.getSeqNo());
            // 「序號」= 0X00
            beforeBachkfileList = bachkfileDao.selectBachkfileDataByLog(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));
            beforeBaappbaseList = baappbaseDao.selectBaappbaseDataByLog(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));
            bachkfileDao.deleteBachkfileData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));
            badaprDao.deleteBadaprData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));
            // 當 BAPFLBAC 存在該筆 APNO & SEQNO 時，不刪除 BAAPPBASE 的資料，只將 CASEMK 修改為 D
            if ("BAMO0D092C".equals(progName) && bapflbacDao.selectDataCountByApnoAndSeqno(caseData.getApNo(), caseData.getSeqNo()) > 0) {
            	// BAAPPBASE.CASEMK 改為 D
            	baappbaseDao.updateCasemkByApnoAndSeqno(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2), userData.getEmpNo());
            	// BAPFLBAC.BRMK, AFMK 改為 D
            	bapflbacDao.updateBrmkAndAfmkByApnoAndSeqno(caseData.getApNo(), caseData.getSeqNo(), userData);
            	// BAREGIVEDTL.MK, BRMK, AFMK 改為 D
            	baregivedtlDao.updateMkAndBrmkAndAfmkByApnoAndSeqno(caseData.getApNo(), caseData.getSeqNo(), userData);
            } else {
            	baappbaseDao.deleteBaappbaseData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));
            }
        }
        else if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 2) {
            // 「序號」= 0XX0
            beforeBachkfileList = bachkfileDao.selectBachkfileDataByLog(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 3));
            beforeBaappbaseList = baappbaseDao.selectBaappbaseDataByLog(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 3));
            bachkfileDao.deleteBachkfileData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 3));
            badaprDao.deleteBadaprData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 2));
            // 當 BAPFLBAC 存在該筆 APNO & SEQNO 時，不刪除 BAAPPBASE 的資料，只將 CASEMK 修改為 D
            if ("BAMO0D092C".equals(progName) && bapflbacDao.selectDataCountByApnoAndSeqno(caseData.getApNo(), caseData.getSeqNo()) > 0) {
            	// BAAPPBASE.CASEMK 改為 D
            	baappbaseDao.updateCasemkByApnoAndSeqno(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 3), userData.getEmpNo());
            	// BAPFLBAC.BRMK, AFMK 改為 D
            	bapflbacDao.updateBrmkAndAfmkByApnoAndSeqno(caseData.getApNo(), caseData.getSeqNo(), userData);
            	// BAREGIVEDTL.MK, BRMK, AFMK 改為 D
            	baregivedtlDao.updateMkAndBrmkAndAfmkByApnoAndSeqno(caseData.getApNo(), caseData.getSeqNo(), userData);
            } else {
            	baappbaseDao.deleteBaappbaseData(caseData.getApNo(), StringUtils.substring(caseData.getSeqNo(), 0, 3));
            }
        }
        else if (StringUtils.countMatches(caseData.getSeqNo(), "0") == 1) {
            // 「序號」= 0XXX
            beforeBachkfileList = bachkfileDao.selectBachkfileDataByLog(caseData.getApNo(), caseData.getSeqNo());
            beforeBaappbaseList = baappbaseDao.selectBaappbaseDataByLog(caseData.getApNo(), caseData.getSeqNo());
            bachkfileDao.deleteBachkfileData(caseData.getApNo(), caseData.getSeqNo());
            badaprDao.deleteBadaprData(caseData.getApNo(), caseData.getSeqNo());
            // 當 BAPFLBAC 存在該筆 APNO & SEQNO 時，不刪除 BAAPPBASE 的資料，只將 CASEMK 修改為 D
            if ("BAMO0D092C".equals(progName) && bapflbacDao.selectDataCountByApnoAndSeqno(caseData.getApNo(), caseData.getSeqNo()) > 0) {
            	// BAAPPBASE.CASEMK 改為 D
            	baappbaseDao.updateCasemkByApnoAndSeqno(caseData.getApNo(), caseData.getSeqNo(), userData.getEmpNo());
            	// BAPFLBAC.BRMK, AFMK 改為 D
            	bapflbacDao.updateBrmkAndAfmkByApnoAndSeqno(caseData.getApNo(), caseData.getSeqNo(), userData);
            	// BAREGIVEDTL.MK, BRMK, AFMK 改為 D
            	baregivedtlDao.updateMkAndBrmkAndAfmkByApnoAndSeqno(caseData.getApNo(), caseData.getSeqNo(), userData);
            } else {
            	baappbaseDao.deleteBaappbaseData(caseData.getApNo(), caseData.getSeqNo());
            }
        }

        // 相關要修改的主檔清單
        List<BigDecimal> payTypeList = new ArrayList<BigDecimal>();
        List<BigDecimal> benEvtRelList = new ArrayList<BigDecimal>();
        List<BigDecimal> procstatList = new ArrayList<BigDecimal>();
        // 給付資料更正改前物件List
        List<Baappbase> beforeList = new ArrayList<Baappbase>();
        // 給付資料更正改後物件List
        List<Baappbase> afterList = new ArrayList<Baappbase>();
        // 正在修改資料該筆改前改後值List for BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        // 其他需要同步資料的改前改後值List for BAAPPLOG
        List<BaappbaseDataUpdateCase> baapplogList2 = new ArrayList<BaappbaseDataUpdateCase>();

        if (("1").equals(caseData.getBenEvtRel())) { // 本人
            benEvtRelList = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());
            for (int i = 0; i < benEvtRelList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) benEvtRelList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

            }

        }
        else { // 其他親屬
            procstatList = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());

            for (int i = 0; i < procstatList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) procstatList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase beforeObj = new Baappbase();// 改前值物件

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(beforeObj, extModifyObj);
                    beforeList.add(beforeObj);
                }

            }
        }

        log.debug("Delete BAAPPBASE Finished ...");

        log.debug("delete BAAPPBASE Finished ...");
        // ]

        // [
        log.debug("Start update BAAPPBASE ...");
        caseData.setUpdUser(userData.getEmpNo());// 異動人員
        caseData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        // 更新PROCSTAT='01'
        baappbaseDao.updateProcstatForPayeeDataUpdate(caseData);
        log.debug("update BAAPPBASE Finished ...");
        // ]

        if (("1").equals(caseData.getBenEvtRel())) { // 本人
            benEvtRelList = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());
            for (int i = 0; i < benEvtRelList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) benEvtRelList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase afterObj = new Baappbase();// 改後值物件

                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(afterObj, extModifyObj);
                    afterList.add(afterObj);
                }

            }

        }
        else { // 其他親屬
            procstatList = baappbaseDao.selectBaappbaseIdBy(caseData.getApNo());

            for (int i = 0; i < procstatList.size(); i++) {
                BigDecimal baappbaseId = (BigDecimal) procstatList.get(i);
                BaappbaseDataUpdateCase extModifyObj = getBaappbaseDetail(baappbaseId, null, null, null);
                Baappbase afterObj = new Baappbase();// 改後值物件

                // Insert MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    BeanUtility.copyProperties(afterObj, extModifyObj);
                    afterList.add(afterObj);
                }

            }
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteListLog(beforeBachkfileList);
            FrameworkLogUtil.doDeleteListLog(beforeBaappbaseList);
            FrameworkLogUtil.doUpdateListLog(beforeList, afterList);

        }

        // 新增資料到 更正記錄檔(BAAPPLOG)
        // [
        log.debug("Start Insert BAAPPLOG ...");

        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(caseData.getBaappbaseId());// 資料列編號

        baapplog.setStatus("D");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplog.setUpCol(); // 異動項目 - Log 已設
        // baapplog.setBvalue("");// 改前內容 - Log 已設//改後內容
        // baapplog.setAvalue(); // 改後內容 - Log 已設//改後內容
        baapplogDao.insertData(baapplog);

        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    // 檢查銀行帳號前7碼

    public boolean checkBankAccount(String payBankIdBranchId) {
        boolean checkResult = false;
        List<Bcbpf> list = bcbpfDao.selectBankDataBy(payBankIdBranchId);
        if (list.size() == 1) {
            checkResult = true;
        }
        return checkResult;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 案件資料更正"註銷"按鈕狀態
     * 
     * @param apNo 受理編號
     * @return <code>String</code> 物件
     */
    public String getDelBtnForApplicationUpdate(String apNo) {
        BigDecimal result = baappbaseDao.checkDelBtnForPaymentUpdate(apNo);
        String delBtnStatus = "N";
        if (result.compareTo(new BigDecimal(0)) > 0) {
            delBtnStatus = "Y";
        }
        return delBtnStatus;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 案件資料更正"事故者姓名"可否修改狀態
     * 
     * @param apNo 受理編號
     * @return <code>String</code> 物件
     */
    public String getEvtNameModifyStatusForApplicationUpdate(String apNo) {
        BigDecimal result = baappbaseDao.selectEvtNameModifyStatusForApplicationUpdate(apNo);
        String delBtnStatus = "N";
        if (result.compareTo(new BigDecimal(0)) == 0) {
            delBtnStatus = "Y";
        }
        return delBtnStatus;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 被具名領取筆數
     * 
     * @param apNo 受理編號
     * @return <code>String</code> 物件
     */
    public String getAccSeqNoAmt(String apNo, String seqNo) {
        Integer status = baappbaseDao.selectAccSeqNoAmt(apNo, seqNo);
        String accSeqNoAmt = "0";
        if (status > 0) {
            accSeqNoAmt = "1";
        }
        return accSeqNoAmt;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 是否有被具名領取筆數 被具名領取筆數 > 0則回傳true
     * 
     * @param apNo 受理編號
     * @return <code>String</code> 物件
     */
    public boolean hasCoreceiver(String apNo, String seqNo) {
        Integer count = baappbaseDao.selectAccSeqNoAmt(apNo, seqNo);
        return count > 0;
    }

    /**
     * 更新給付主檔(<code>BAAPPBASE</code>) FOR 編審註記程度調整
     * 
     * @param apNo 受理編號
     */
    public void updateForCheckMarkLevelAdjust(String apNo, String regetCipbMk) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(apNo);

        // update BAAPPBASE REGETCIPBMK
        updateRegetCipbMkByApNo(apNo, regetCipbMk);

        Baappbase beforeBaappbase = baappbaseDao.selectForCheckMarkLevelAdjustByLog(apNo);
        baappbaseDao.updateForCheckMarkLevelAdjust(apNo);
        Baappbase afterBaappbase = baappbaseDao.selectForCheckMarkLevelAdjustByLog(apNo);
        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforeBaappbase, afterBaappbase);
        }

    }
    
    /**
     * 更新給付主檔(<code>BAAPPBASE</code>) FOR 編審註記程度調整
     * 
     * @param apNo 受理編號
     */
    public void updateCloseDateForCheckMarkLevelAdjust(String apNo) {

        Baappbase beforeBaappbase = baappbaseDao.selectForCheckMarkLevelAdjustByLog(apNo);
        baappbaseDao.updateCloseDateForCheckMarkLevelAdjust(apNo);
        Baappbase afterBaappbase = baappbaseDao.selectForCheckMarkLevelAdjustByLog(apNo);
        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforeBaappbase, afterBaappbase);
        }

    }

    /**
     * 依傳入之 受理編號 取得 失能年金編審註記程度調整 之資料 for 失能年金編審註記程度調整
     * 
     * @param apNo 受理編號
     * @return
     */
    public DisabledCheckMarkLevelAdjustCase getDisabledCheckMarkLevelAdjustData(String apNo) {
        DisabledCheckMarkLevelAdjustCase caseData = new DisabledCheckMarkLevelAdjustCase();

        // 取得 Header 資料 (BAAPPBASE)
        // [
        Baappbase headerData = baappbaseDao.getDisabledCheckMarkLevelAdjustHeaderDataBy(apNo);

        if (headerData == null)
            return null;

        caseData.setApNo(headerData.getApNo()); // 受理編號
        caseData.setEvtName(headerData.getEvtName()); // 事故者姓名

        caseData.setEvtIdnNo(headerData.getEvtIdnNo()); // 事故者身分證號

        caseData.setEvtBrDate(headerData.getEvtBrDate()); // 事故者出生日期

        caseData.setEvtJobDate(headerData.getEvtJobDate()); // 事故日期
        caseData.setIssuYm(headerData.getIssuYm()); // 核定年月
        caseData.setAppDate(headerData.getAppDate()); // 申請日期
        caseData.setProcStat(headerData.getProcStat()); // 處理狀態

        caseData.setCaseTyp(headerData.getCaseTyp()); // 案件類別
        caseData.setRegetCipbMk(headerData.getRegetCipbMk()); // 重讀CIPB
        caseData.setBaappbaseId(headerData.getBaappbaseId());
        caseData.setPayKind(headerData.getPayKind());// 給付種類
        // ]

        // 取得 受款人 及 受款人編審註記資料 (BAAPPBASE / BACHKFILE)
        // [
        List<Baappbase> benList = baappbaseDao.getDisabledCheckMarkLevelAdjustBenListBy(apNo);
        for (Baappbase benData : benList) {
            DisabledCheckMarkLevelAdjustBenDataCase benCase = new DisabledCheckMarkLevelAdjustBenDataCase();
            benCase.setApNo(benData.getApNo()); // 受理編號
            benCase.setSeqNo(benData.getSeqNo()); // 序號
            benCase.setBenIdnNo(benData.getBenIdnNo()); // 受款人身分證號

            benCase.setBenName(benData.getBenName()); // 受款人姓名

            benCase.setBenEvtRel(benData.getBenEvtRel()); // 受益人與事故者關係

            benCase.setBenDieDate(benData.getBenDieDate());// 受益人死亡日期

            // 受款人編審註記資料

            List<Bachkfile> benChkfileList = bachkfileDao.getDisabledCheckMarkLevelAdjustBenDetailDataBy(benData.getApNo(), benData.getSeqNo());
            for (Bachkfile benChkfileData : benChkfileList) {
                DisabledCheckMarkLevelAdjustBenDetailDataCase benDetailCase = new DisabledCheckMarkLevelAdjustBenDetailDataCase();
                benDetailCase.setBaChkFileId(benChkfileData.getBaChkFileId()); // 資料列編號

                benDetailCase.setApNo(benChkfileData.getApNo()); // 受理編號
                benDetailCase.setSeqNo(benChkfileData.getSeqNo()); // 序號
                benDetailCase.setIssuYm(benChkfileData.getIssuYm()); // 核定年月
                benDetailCase.setPayYm(benChkfileData.getPayYm()); // 給付年月
                benDetailCase.setChkCode(benChkfileData.getChkCode()); // 編審註記代號
                benDetailCase.setChkCodePre(benChkfileData.getChkCodePre()); // 編審註記代號類型
                benDetailCase.setChkCodePost(benChkfileData.getChkCodePost()); // 編審註記代號改後類型
                benDetailCase.setChkResult(benChkfileData.getChkResult()); // 編審結果說明
                benDetailCase.setKeyValue(benChkfileData.getKeyValue()); // 關鍵欄位值

                benDetailCase.setMaxMk(benChkfileData.getMaxMk()); // 最大給付年月註記

                // benDetailCase.setAdjLevel(); // 程度調整 - 畫面輸入欄位
                benDetailCase.setValisYm(benChkfileData.getValisYm()); // 有效年月起

                benDetailCase.setValieYm(benChkfileData.getValieYm()); // 有效年月迄

                // Add to Detail List
                benCase.addDetailData(benDetailCase);
            }
            // 新增 受款人 資料
            caseData.addBenData(benCase);
        }
        // ]

        // 取得 眷屬 及 眷屬編審註記資料 (BAFAMILY / BACHKFILE)
        // [
        List<Bafamily> famList = bafamilyDao.getDisabledCheckMarkLevelAdjustFamListBy(apNo);
        for (Bafamily famData : famList) {
            DisabledCheckMarkLevelAdjustFamDataCase famCase = new DisabledCheckMarkLevelAdjustFamDataCase();
            famCase.setApNo(famData.getApNo()); // 受理編號
            famCase.setSeqNo(famData.getSeqNo()); // 序號
            famCase.setFamIdnNo(famData.getFamIdnNo()); // 眷屬身分證號
            famCase.setFamName(famData.getFamName()); // 眷屬姓名
            famCase.setFamEvtRel(famData.getFamEvtRel()); // 眷屬與事故者關係

            famCase.setFamDieDate(famData.getFamDieDate());// 眷屬死亡日期
            // 眷屬編審註記資料
            List<Bachkfile> famChkfileList = bachkfileDao.getDisabledCheckMarkLevelAdjustFamDetailDataBy(famData.getApNo(), famData.getSeqNo());
            for (Bachkfile famChkfileData : famChkfileList) {
                DisabledCheckMarkLevelAdjustFamDetailDataCase famDetailCase = new DisabledCheckMarkLevelAdjustFamDetailDataCase();
                famDetailCase.setBaChkFileId(famChkfileData.getBaChkFileId()); // 資料列編號

                famDetailCase.setApNo(famChkfileData.getApNo()); // 受理編號
                famDetailCase.setSeqNo(famChkfileData.getSeqNo()); // 序號
                famDetailCase.setIssuYm(famChkfileData.getIssuYm()); // 核定年月
                famDetailCase.setPayYm(famChkfileData.getPayYm()); // 給付年月
                famDetailCase.setChkCode(famChkfileData.getChkCode()); // 編審註記代號
                famDetailCase.setChkCodePre(famChkfileData.getChkCodePre()); // 編審註記代號類型
                famDetailCase.setChkCodePost(famChkfileData.getChkCodePost()); // 編審註記代號改後類型
                famDetailCase.setChkResult(famChkfileData.getChkResult()); // 編審結果說明
                famDetailCase.setKeyValue(famChkfileData.getKeyValue()); // 關鍵欄位值

                famDetailCase.setMaxMk(famChkfileData.getMaxMk()); // 最大給付年月註記

                // famDetailCase.setAdjLevel(); // 程度調整 - 畫面輸入欄位
                famDetailCase.setValisYm(famChkfileData.getValisYm()); // 有效年月起

                famDetailCase.setValieYm(famChkfileData.getValieYm()); // 有效年月迄

                // Add to Detail List
                famCase.addDetailData(famDetailCase);
            }
            // 新增 眷屬 資料
            caseData.addFamData(famCase);
        }
        // ]
        return caseData;
    }

    /**
     * 失能年金編審註記程度調整 存檔處理 - for 失能年金編審註記程度調整 <br>
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param dataList
     */
    public void saveDisabledCheckMarkLevelAdjustData(String apNo, String issuYm, List<Bachkcontrl> dataList, String regetCipbMk) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(apNo);

        // update BAAPPBASE REGETCIPBMK
        updateRegetCipbMkByApNo(apNo, regetCipbMk);

        List<Bachkcontrl> beforeBachkcontrl = bachkcontrlDao.selectDataBy(apNo, issuYm);
        // 先刪除再新增
        bachkcontrlDao.deleteData(apNo);

        bachkcontrlDao.insertData(dataList);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteListLog(beforeBachkcontrl);
            FrameworkLogUtil.doInsertListLog(dataList);
        }
    }

    /**
     * 依傳入之 受理編號 取得 遺屬年金編審註記程度調整 之資料 for 遺屬年金編審註記程度調整
     * 
     * @param apNo 受理編號
     * @return
     */
    public SurvivorCheckMarkLevelAdjustCase getSurvivorCheckMarkLevelAdjustData(String apNo) {
        SurvivorCheckMarkLevelAdjustCase caseData = new SurvivorCheckMarkLevelAdjustCase();

        // 取得 Header 資料 (BAAPPBASE)
        // [
        Baappbase headerData = baappbaseDao.getSurvivorCheckMarkLevelAdjustHeaderDataBy(apNo);

        if (headerData == null)
            return null;

        caseData.setApNo(headerData.getApNo()); // 受理編號
        caseData.setEvtName(headerData.getEvtName()); // 事故者姓名

        caseData.setEvtIdnNo(headerData.getEvtIdnNo()); // 事故者身分證號

        caseData.setEvtBrDate(headerData.getEvtBrDate()); // 事故者出生日期

        caseData.setEvtJobDate(headerData.getEvtJobDate()); // 事故日期
        caseData.setIssuYm(headerData.getIssuYm()); // 核定年月
        caseData.setAppDate(headerData.getAppDate()); // 申請日期
        caseData.setProcStat(headerData.getProcStat()); // 處理狀態

        caseData.setCaseTyp(headerData.getCaseTyp()); // 案件類別
        caseData.setRegetCipbMk(headerData.getRegetCipbMk()); // 重讀CIPB
        // ]

        // 取得 受款人 及 受款人編審註記資料 (BAAPPBASE / BACHKFILE)
        // [
        List<Baappbase> benList = baappbaseDao.getSurvivorCheckMarkLevelAdjustBenListBy(apNo);
        for (Baappbase benData : benList) {
            SurvivorCheckMarkLevelAdjustBenDataCase benCase = new SurvivorCheckMarkLevelAdjustBenDataCase();
            benCase.setApNo(benData.getApNo()); // 受理編號
            benCase.setSeqNo(benData.getSeqNo()); // 序號
            benCase.setBenIdnNo(benData.getBenIdnNo()); // 受款人身分證號

            benCase.setBenName(benData.getBenName()); // 受款人姓名

            benCase.setBenEvtRel(benData.getBenEvtRel()); // 受益人與事故者關係

            benCase.setBenDieDate(benData.getBenDieDate());// 受益人死亡日期

            benCase.setAppDate(benData.getAppDate());// 申請日期
            // 受款人編審註記資料

            List<Bachkfile> benChkfileList = bachkfileDao.getSurvivorCheckMarkLevelAdjustBenDetailDataBy(benData.getApNo(), benData.getSeqNo());
            for (Bachkfile benChkfileData : benChkfileList) {
                SurvivorCheckMarkLevelAdjustBenDetailDataCase benDetailCase = new SurvivorCheckMarkLevelAdjustBenDetailDataCase();
                benDetailCase.setBaChkFileId(benChkfileData.getBaChkFileId()); // 資料列編號

                benDetailCase.setApNo(benChkfileData.getApNo()); // 受理編號
                benDetailCase.setSeqNo(benChkfileData.getSeqNo()); // 序號
                benDetailCase.setIssuYm(benChkfileData.getIssuYm()); // 核定年月
                benDetailCase.setPayYm(benChkfileData.getPayYm()); // 給付年月
                benDetailCase.setChkCode(benChkfileData.getChkCode()); // 編審註記代號
                benDetailCase.setChkCodePre(benChkfileData.getChkCodePre()); // 編審註記代號類型
                benDetailCase.setChkCodePost(benChkfileData.getChkCodePost()); // 編審註記代號改後類型
                benDetailCase.setChkResult(benChkfileData.getChkResult()); // 編審結果說明
                benDetailCase.setKeyValue(benChkfileData.getKeyValue()); // 關鍵欄位值

                benDetailCase.setMaxMk(benChkfileData.getMaxMk()); // 最大給付年月註記

                // benDetailCase.setAdjLevel(); // 程度調整 - 畫面輸入欄位
                benDetailCase.setValisYm(benChkfileData.getValisYm()); // 有效年月起

                benDetailCase.setValieYm(benChkfileData.getValieYm()); // 有效年月迄

                // Add to Detail List
                benCase.addDetailData(benDetailCase);
            }
            // 新增 受款人 資料
            caseData.addBenData(benCase);
        }
        // ]
        return caseData;
    }

    /**
     * 遺屬年金編審註記程度調整 存檔處理 - for 遺屬年金編審註記程度調整 <br>
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param dataList
     */
    public void saveSurvivorCheckMarkLevelAdjustData(String apNo, String issuYm, List<Bachkcontrl> dataList, String regetCipbMk) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(apNo);

        // update BAAPPBASE REGETCIPBMK
        updateRegetCipbMkByApNo(apNo, regetCipbMk);

        List<Bachkcontrl> beforeBachkcontrl = bachkcontrlDao.selectDataBy(apNo, issuYm);

        // 先刪除再新增
        bachkcontrlDao.deleteData(apNo);
        bachkcontrlDao.insertData(dataList);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteListLog(beforeBachkcontrl);
            FrameworkLogUtil.doInsertListLog(dataList);
        }
    }

    /**
     * 依傳入條件取得 眷屬檔 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<DependantDataUpdateCase> selectDependantDataForList(String apNo) {

        List<DependantDataUpdateCase> caseList = new ArrayList<DependantDataUpdateCase>();
        List<Bafamily> bafamilyList = bafamilyDao.selectDependantDataForList(apNo);

        for (Bafamily bafamily : bafamilyList) {
            DependantDataUpdateCase caseDateCase = new DependantDataUpdateCase();
            BeanUtility.copyProperties(caseDateCase, bafamily);
            caseList.add(caseDateCase);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 在學期間檔 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Bastudterm> selectStudtermListForDependantDataUpdate(String apNo, String seqNo) {
        List<Bastudterm> studtermList = bastudtermDao.selectStudtermListForSurvivorPayeeDataUpdate(apNo, seqNo);
        return studtermList;
    }

    /**
     * 依傳入條件取得 眷屬檔 for 眷屬資料更正作業 - 事故者資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<DependantEvtDataUpdateCase> selectDependantEvtDataForList(String apNo) {

        List<DependantEvtDataUpdateCase> caseList = new ArrayList<DependantEvtDataUpdateCase>();
        List<Baappbase> bafamilyEvtList = baappbaseDao.selectDependantEvtDataForList(apNo);

        for (Baappbase baappbase : bafamilyEvtList) {
            DependantEvtDataUpdateCase caseDateCase = new DependantEvtDataUpdateCase();
            BeanUtility.copyProperties(caseDateCase, baappbase);
            caseList.add(caseDateCase);
        }

        return caseList;
    }

    /**
     * 依傳入條件取得 眷屬檔 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @param famIdnNo 眷屬身份證字號
     * @param famBrDate 眷屬出生日期
     * @return
     */
    public Integer getDependantDataCount(String apNo, String famIdnNo, String bafamilyId) {
        return bafamilyDao.getDependantDataCount(apNo, famIdnNo, bafamilyId);

    }

    /**
     * 依傳入條件取得 眷屬檔 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @param famIdnNo 眷屬身份證字號
     * @param famBrDate 眷屬出生日期
     * @return
     */
    public Integer getDependantSeqNoDataCount(String apNo) {
        return bafamilyDao.getDependantSeqNoDataCount(apNo);
    }

    /**
     * 依傳入條件取得 眷屬檔 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @param famIdnNo 眷屬身份證字號
     * @param famBrDate 眷屬出生日期
     * @return
     */
    public Integer getDataCount2ForDependant(String apNo, String famIdnNo, String issuYm) {
        return bachkfileDao.selectDataCount2ForDependant(apNo, famIdnNo, issuYm);

    }

    /**
     * 依傳入條件取得 眷屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料 for 眷屬資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Bastudterm</code> 物件
     */
    public BigDecimal selectMaxTermNoBy(String apNo, String seqNo) {
        return bastudtermDao.selectMaxTermNoBy(apNo, seqNo);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 最大給付年月資料 for 眷屬資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappbase</code> 物件
     */
    public String selectMaxPayYmBy(String apNo, String seqNo) {
        return baappbaseDao.selectMaxPayYmBy(apNo, seqNo);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 最大給付年月資料 for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappbase</code> 物件
     */
    public String selectMaxPayYmForSBy(String apNo, String seqNo) {
        return baappbaseDao.selectMaxPayYmForSBy(apNo, seqNo);
    }

    /**
     * 依傳入條件取得 眷屬檔 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public BigDecimal getDependantDataCount(String apNo) {
        return badaprDao.getDependantDeleteStatus(apNo);

    }

    /**
     * 依畫面輸入欄位條件轉換 新增/修改 眷屬檔檔 部分欄位<br>
     * 
     * @param
     * @return
     */
    public DependantDataUpdateCase transDependantUpdateData(DependantDataUpdateCase caseObj, Bafamily caseData, String type) {
        // 以下是相同的欄位
        caseObj.setApNo(caseData.getApNo());

        // 眷屬申請日期
        // 變更日期為西元年
        if (caseObj.getFamEvtRel().equals("2") || caseObj.getFamEvtRel().equals("3") || caseObj.getFamEvtRel().equals("4") || caseObj.getFamEvtRel().equals("5") || caseObj.getFamEvtRel().equals("6") || caseObj.getFamEvtRel().equals("7")) {
            if (StringUtils.defaultString(caseObj.getFamAppDate()).trim().length() == 7)
                caseObj.setFamAppDate(DateUtility.changeDateType(caseObj.getFamAppDate()));
        }
        else {
            caseObj.setFamAppDate(caseData.getFamAppDate()); // 修改時則不變更原值

        }

        // 眷屬出生日期
        caseObj.setFamBrDate(DateUtility.changeDateType(caseObj.getFamBrDate()));

        // 眷屬死亡日期
        if (StringUtils.defaultString(caseObj.getFamDieDate()).trim().length() == 7) {
            caseObj.setFamDieDate(DateUtility.changeDateType(caseObj.getFamDieDate()));
        }

        // 放棄請領起始年月
        if ("Y".equals(caseObj.getAbanApplyMk()) && StringUtils.defaultString(caseObj.getAbanApsYm()).trim().length() == 5) {
            caseObj.setAbanApsYm(DateUtility.changeChineseYearMonthType(caseObj.getAbanApsYm()));
        }
        else {
            caseObj.setAbanApsYm("");
        }

        // 身分查核年月
        if ("2".equals(caseObj.getIdnChkNote()) && StringUtils.defaultString(caseObj.getIdnChkYm()).trim().length() == 5 && !"".equals(caseObj.getIdnChkYm())) {
            caseObj.setIdnChkYm(DateUtility.changeChineseYearMonthType(caseObj.getIdnChkYm()));
        }
        else {
            caseObj.setIdnChkYm("");
        }

        // 受禁治產(監護)宣告日期
        if (!"".equals(caseObj.getInterDictMk()) && caseObj.getInterDictMk() != null && StringUtils.defaultString(caseObj.getInterDictDate()).trim().length() == 7) {
            caseObj.setInterDictDate(DateUtility.changeDateType(caseObj.getInterDictDate()));
        }
        else {
            caseObj.setInterDictDate("");
        }

        // 禁治產撤消日期

        if (!"".equals(caseObj.getInterDictMk()) && caseObj.getInterDictMk() != null && StringUtils.defaultString(caseObj.getRepealInterDictDate()).trim().length() == 7) {
            caseObj.setRepealInterDictDate(DateUtility.changeDateType(caseObj.getRepealInterDictDate()));
        }
        else {
            caseObj.setRepealInterDictDate("");
        }

        // 親屬關係變動日期
        if (StringUtils.defaultString(caseObj.getRelatChgDate()).trim().length() == 7) {
            caseObj.setRelatChgDate(DateUtility.changeDateType(caseObj.getRelatChgDate()));
        }

        // 收養日期
        if (StringUtils.defaultString(caseObj.getAdoPtDate()).trim().length() == 7) {
            caseObj.setAdoPtDate(DateUtility.changeDateType(caseObj.getAdoPtDate()));
        }

        // 受益人失蹤期間(起)
        if (StringUtils.defaultString(caseObj.getBenMissingSdate()).trim().length() == 7) {
            caseObj.setBenMissingSdate(DateUtility.changeDateType(caseObj.getBenMissingSdate()));
        }

        // 受益人失蹤期間 (迄)
        if (StringUtils.defaultString(caseObj.getBenMissingEdate()).trim().length() == 7) {
            caseObj.setBenMissingEdate(DateUtility.changeDateType(caseObj.getBenMissingEdate()));
        }

        // 監管期間(起)
        if (StringUtils.defaultString(caseObj.getPrisonSdate()).trim().length() == 7) {
            caseObj.setPrisonSdate(DateUtility.changeDateType(caseObj.getPrisonSdate()));
        }

        // 監管期間(迄)
        if (StringUtils.defaultString(caseObj.getPrisonEdate()).trim().length() == 7) {
            caseObj.setPrisonEdate(DateUtility.changeDateType(caseObj.getPrisonEdate()));
        }

        // 結婚日期
        if (StringUtils.defaultString(caseObj.getMarryDate()).trim().length() == 7) {
            caseObj.setMarryDate(DateUtility.changeDateType(caseObj.getMarryDate()));
        }

        // 離婚日期
        if (StringUtils.defaultString(caseObj.getDivorceDate()).trim().length() == 7) {
            caseObj.setDivorceDate(DateUtility.changeDateType(caseObj.getDivorceDate()));
        }

        // 在學起始年月
        if ("Y".equals(caseObj.getStudMk()) && StringUtils.defaultString(caseObj.getStudSdate()).trim().length() == 5) {
            caseObj.setStudSdate(DateUtility.changeChineseYearMonthType(caseObj.getStudSdate()));
        }
        else {
            caseObj.setStudSdate("");
        }

        // 在學結束年月
        if ("Y".equals(caseObj.getStudMk()) && StringUtils.defaultString(caseObj.getStudEdate()).trim().length() == 5) {
            caseObj.setStudEdate(DateUtility.changeChineseYearMonthType(caseObj.getStudEdate()));
        }
        else {
            caseObj.setStudEdate("");
        }

        // 國籍
        if (caseObj.getFamNationTyp().equals("1")) {
            caseObj.setFamNationCode("000");
            if ("2".equals(caseObj.getFamIdnNo().substring(1, 2)))
                caseObj.setFamSex("2");
            else if ("1".equals(caseObj.getFamIdnNo().substring(1, 2)))
                caseObj.setFamSex("1");
        }

        // 得請領起月
        if (StringUtils.defaultString(caseObj.getAbleApsYm()).trim().length() == 5) {
            caseObj.setAbleApsYm(DateUtility.changeChineseYearMonthType(caseObj.getAbleApsYm()));
        }

        return caseObj;
    }

    /**
     * 新增資料 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public void insertDependantData(UserBean userData, DependantEvtDataUpdateCase queryData, DependantDataUpdateCase dependantData, List<Bastudterm> bastudtermList, List<DependantDataUpdateCompelDataCase> compelDataList) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(queryData.getApNo());

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAFAMILY ...");
        Bafamily bafamily = new Bafamily();
        BeanUtility.copyProperties(bafamily, dependantData);
        bafamily.setBaappbaseId(queryData.getBaappbaseId());
        bafamily.setCrtUser(userData.getEmpNo());// 新增者代號

        bafamily.setFamAppDate(dependantData.getFamAppDate());
        bafamily.setCrtTime(DateUtility.getNowWestDateTime(true));// 新增日期時間

        // 新增給付主檔資料
        BigDecimal bafamilyId = bafamilyDao.insertData(bafamily);
        // 則不新增
        bastudtermDao.deleteBastudterm(queryData.getApNo(), bafamily.getSeqNo());
        if ("Y".equals(bafamily.getStudMk())) {
            if (bastudtermList != null && !bastudtermList.isEmpty()) {
                for (Bastudterm o : bastudtermList) {
                    o.setSeqNo(bafamily.getSeqNo());
                    bastudtermDao.insertBastudterm(o);
                }
            }
        }

        // 新增不合格年月資料,先把整個table的不合格年月資料delete掉,再insert上去
        // 直接砍掉所有與該受款人相關的不合格年月資料
        // 先檢查強迫不合格註記,若為Y才insert compelData list,若為N表示未強迫不合格
        // 則不新增
        bacompelnopayDao.deleteData(bafamily.getApNo(), bafamily.getSeqNo());
        if ("Y".equals(bafamily.getCompelMk())) {
            if (compelDataList != null && !compelDataList.isEmpty()) {
                int count = 1;
                for (DependantDataUpdateCompelDataCase o : compelDataList) {
                    Bacompelnopay bacompelnopay = new Bacompelnopay();
                    BeanUtility.copyProperties(bacompelnopay, o);
                    bacompelnopay.setSeqNo(dependantData.getSeqNo());
                    bacompelnopay.setCompelNo(new BigDecimal(count));
                    bacompelnopayDao.insertData(bacompelnopay);
                    count++;
                }
            }
        }

        log.debug("Insert BAFAMILY Finished ...");
        // Update Baappbase
        List<Baappbase> afterBaappbaseList = new ArrayList<Baappbase>();
        List<Baappbase> beforeBaappbaseList = baappbaseDao.selectDependantDataList(bafamily.getApNo());

        for (Baappbase beforeBaappbase : beforeBaappbaseList) {
            Baappbase afterBaappbase = new Baappbase();
            BeanUtility.copyProperties(afterBaappbase, beforeBaappbase);
            afterBaappbase.setProcStat("01");
            afterBaappbase.setUpdTime(DateUtility.getNowWestDateTime(true));
            afterBaappbase.setUpdUser(userData.getEmpNo());
            afterBaappbase.setChkDate("");
            afterBaappbase.setChkMan("");
            afterBaappbase.setRechkDate("");
            afterBaappbase.setRechkMan("");
            afterBaappbase.setExeDate("");
            afterBaappbase.setExeMan("");
            baappbaseDao.updateDataForDependant(afterBaappbase);
            afterBaappbaseList.add(afterBaappbase);
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBaappbase, afterBaappbase);
            }
        }

        // Insert MMAPLOG
        bafamily.setBafamilyId(bafamilyId);
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bafamily);
            FrameworkLogUtil.doInsertListLog(bastudtermList);
        }
        log.debug("Insert  MMAPLOG Finished ...");
        // ]

        // 新增資料到 更正記錄檔

        // [
        log.debug("Start Insert BAAPPLOG ...");
        Baapplog baapplog = new Baapplog();
        for (Baappbase baappbase : afterBaappbaseList) {
            baapplog.setBaappbaseId(baappbase.getBaappbaseId());// 資料列編號

            baapplog.setStatus("A");// 狀態 'A'-新增，'U'-修改，'D'-刪除
            baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
            baapplog.setUpdUser(userData.getEmpNo());// 異動人員
            // baapplog.setUpCol(); // 異動項目
            // baapplog.setBvalue("");// 改前內容
            // baapplog.setAvalue(); // 改後內容
            baapplogDao.insertData(baapplog);
        }
        log.debug("Insert BAAPPLOG Finished ...");
        // ]
    }

    public void updateDependantData(UserBean userData, DependantDataUpdateCase detailData, Bafamily dependantData, List<Bastudterm> bastudtermList, String caseType, List<DependantDataUpdateCompelDataCase> compelDataList) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(detailData.getApNo());

        // 更新資料到 眷屬檔
        // [
        log.debug("Start Update BAFAMILY ...");
        Bafamily bafamily = new Bafamily();
        Bafamily beforBafamily = bafamilyDao.getDependantDataUpdateForUpdateCaseBy(detailData.getBafamilyId().toString());
        BeanUtility.copyProperties(bafamily, beforBafamily);
        BeanUtility.copyProperties(bafamily, detailData);
        bafamily.setUpdUser(userData.getEmpNo());// 更增者代號

        bafamily.setUpdTime(DateUtility.getNowWestDateTime(true));// 更增日期時間

        bafamilyDao.updateDataForDependant(bafamily);

        // 2011.05.26 新增 當案件主檔(seqNo=0000)的caseType!='1', 則判斷改前改後值，並存入BACOLUMNREC
        // [
        Bacolumnrec bacolumnrec = new Bacolumnrec();
        Bacolumnrec tmpBacolumnrec = new Bacolumnrec();
        tmpBacolumnrec.setApNo(bafamily.getApNo());
        tmpBacolumnrec.setSeqNo(bafamily.getSeqNo());
        tmpBacolumnrec.setBenEvtRel(bafamily.getFamEvtRel());
        tmpBacolumnrec.setCrtUser(userData.getEmpNo());
        tmpBacolumnrec.setCrtTime(DateUtility.getNowWestDateTime(true));

        List<Bacolumnrec> bacolumnrecList = new ArrayList<Bacolumnrec>();
        String chgDate = null;

        if (!StringUtils.equals(ConstantKey.BAAPPBASE_CASETYP_1, caseType)) {
            // 畫面欄位「得請領起月」->BAFAMILY. FAMAPPDATE：
            chgDate = compareTwoDate(beforBafamily.getAbleApsYm(), bafamily.getAbleApsYm());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("ABLEAPSYM");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「申請日期」->BAFAMILY. FAMAPPDATE：

            chgDate = compareTwoDate(beforBafamily.getFamAppDate(), bafamily.getFamAppDate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("FAMAPPDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「死亡日期」->BAFAMILY. FAMDIEDATE：

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getFamDieDate(), bafamily.getFamDieDate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("FAMDIEDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「結婚日期」->BAFAMILY. MARRYDATE：

            // 眷屬關係為2時，且該欄位發生異動才需記錄。

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getMarryDate(), bafamily.getMarryDate());
            if (StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_2, bafamily.getFamEvtRel()) && StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("MARRYDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「離婚日期」->BAFAMILY. DIVORCEDATE：

            // 眷屬關係為2時，且該欄位發生異動才需記錄。

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getDivorceDate(), bafamily.getDivorceDate());
            if (StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_2, bafamily.getFamEvtRel()) && StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("DIVORCEDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「收養日期」-> BAFAMILY. ADOPTDATE：

            // 眷屬關係為4時，且該欄位發生異動才需記錄。

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getAdoPtDate(), bafamily.getAdoPtDate());
            if (StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_4, bafamily.getFamEvtRel()) && StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("ADOPTDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「受禁治產(監護)宣告日期」-> BAFAMILY. INTERDICTDATE：

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getInterDictDate(), bafamily.getInterDictDate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("INTERDICTDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「受禁治產(監護)撤銷日期」-> BAFAMILY. REPEALINTERDICTDATE：

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getRepealInterDictDate(), bafamily.getRepealInterDictDate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("REPEALINTERDICTDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「放棄請領起始年月」-> BAFAMILY. ABANAPSYM：

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getAbanApsYm(), bafamily.getAbanApsYm());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("ABANAPSYM");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「受益人失蹤期間(起)」-> BAFAMILY. BENMISSINGSDATE：

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getBenMissingSdate(), bafamily.getBenMissingSdate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("BENMISSINGSDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「受益人失蹤期間(迄)」-> BAFAMILY. BENMISSINGEDATE：

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getBenMissingEdate(), bafamily.getBenMissingEdate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("BENMISSINGEDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「親屬關係變動日期」-> BAFAMILY. RELATCHGDATE：

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getRelatChgDate(), bafamily.getRelatChgDate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("RELATCHGDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「監管期間(起)」-> BAFAMILY. PRISONSDATE：

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getPrisonSdate(), bafamily.getPrisonSdate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("PRISONSDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }

            // 畫面欄位「監管期間(迄)」-> BAFAMILY. PRISONEDATE：

            chgDate = null;
            chgDate = compareTwoDate(beforBafamily.getPrisonEdate(), bafamily.getPrisonEdate());
            if (StringUtils.isNotBlank(chgDate)) {
                bacolumnrec = new Bacolumnrec();
                BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                bacolumnrec.setColumnName("PRISONEDATE");
                bacolumnrec.setColumnValue(chgDate);
                bacolumnrecList.add(bacolumnrec);
            }
        }
        // ]

        // 新增不合格年月資料,先把整個table的不合格年月資料delete掉,再insert上去
        // 直接砍掉所有與該受款人相關的不合格年月資料
        // 先檢查強迫不合格註記,若為Y才insert compelData list,若為N表示未強迫不合格
        // 則不新增
        List<Bacompelnopay> beforCompelDataList = bacompelnopayDao.selectDataBy(bafamily.getApNo(), bafamily.getSeqNo());
        bacompelnopayDao.deleteData(bafamily.getApNo(), bafamily.getSeqNo());
        if ("Y".equals(bafamily.getCompelMk())) {
            if (compelDataList != null && !compelDataList.isEmpty()) {
                int count = 1;
                for (DependantDataUpdateCompelDataCase o : compelDataList) {
                    Bacompelnopay bacompelnopay = new Bacompelnopay();
                    BeanUtility.copyProperties(bacompelnopay, o);
                    bacompelnopay.setSeqNo(bafamily.getSeqNo());
                    bacompelnopay.setCompelNo(new BigDecimal(count));
                    bacompelnopayDao.insertData(bacompelnopay);
                    count++;
                }
            }

            // 當案件主檔(seqNo=0000)的caseType!='1', 則判斷改前改後值，並存入BACOLUMNREC
            // [
            // 先將改前改後值List複製一分至新List,並將兩邊筆數設為一致方便比對
            if (!StringUtils.equals(ConstantKey.BAAPPBASE_CASETYP_1, caseType)) {
                List<Bacompelnopay> tmpBeforeList = new ArrayList<Bacompelnopay>();
                List<Bacompelnopay> tmpAfterList = new ArrayList<Bacompelnopay>();
                for (int i = 0; i < beforCompelDataList.size(); i++) {
                    Bacompelnopay origObj = beforCompelDataList.get(i);
                    Bacompelnopay modObj = new Bacompelnopay();
                    BeanUtility.copyProperties(modObj, origObj);
                    tmpBeforeList.add(modObj);
                }
                for (int i = 0; i < compelDataList.size(); i++) {
                    DependantDataUpdateCompelDataCase origObj = compelDataList.get(i);
                    Bacompelnopay modObj = new Bacompelnopay();
                    BeanUtility.copyProperties(modObj, origObj);
                    tmpAfterList.add(modObj);
                }

                if (tmpBeforeList.size() > tmpAfterList.size()) {
                    for (int i = tmpAfterList.size(); i < tmpBeforeList.size(); i++) {
                        Bacompelnopay modObj = new Bacompelnopay();
                        modObj.setCompelNo(new BigDecimal(i + 1));
                        tmpAfterList.add(modObj);
                    }
                }
                else if (tmpBeforeList.size() < tmpAfterList.size()) {
                    for (int i = tmpBeforeList.size(); i < tmpAfterList.size(); i++) {
                        Bacompelnopay modObj = new Bacompelnopay();
                        modObj.setCompelNo(new BigDecimal(i + 1));
                        tmpBeforeList.add(modObj);
                    }
                }

                // 開始比對
                for (int i = 0; i < tmpAfterList.size(); i++) {
                    Bacompelnopay origObj = tmpBeforeList.get(i);
                    Bacompelnopay modObj = tmpAfterList.get(i);

                    // 畫面欄位「不合格起始年月」->BACOMPELNOPAY. COMPELSDATE && 「不合格結束年月」->BACOMPELNOPAY. COMPELEDATE。
                    // 當該欄位發生異動才需記錄。
                    // 寫入欄位名稱為畫面欄位+RowNo，例：第二筆的「不合格起始年月」異動，則異動欄位名稱(BACOLUMNREC. COLUMNNAME) 欄位值寫入COMPELEDATE2。
                    chgDate = null;
                    chgDate = compareTwoDate(origObj.getCompelSdate(), modObj.getCompelSdate());
                    if (StringUtils.isNotBlank(chgDate)) {
                        bacolumnrec = new Bacolumnrec();
                        BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);
                        bacolumnrec.setColumnName("COMPELSDATE" + modObj.getCompelNo());
                        bacolumnrec.setColumnValue(chgDate);
                        bacolumnrecList.add(bacolumnrec);
                    }

                    chgDate = null;
                    chgDate = compareTwoDate(origObj.getCompelSdate(), modObj.getCompelSdate());
                    if (StringUtils.isNotBlank(chgDate)) {
                        bacolumnrec = new Bacolumnrec();
                        BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);
                        bacolumnrec.setColumnName("COMPELEDATE" + modObj.getCompelNo());
                        bacolumnrec.setColumnValue(chgDate);
                        bacolumnrecList.add(bacolumnrec);
                    }
                }
            }
            // ]
        }

        List<Bastudterm> beforStudtermList = bastudtermDao.selectStudtermListForSurvivorPayeeDataUpdate(bafamily.getApNo(), bafamily.getSeqNo());
        // 則不新增
        bastudtermDao.deleteBastudterm(bafamily.getApNo(), bafamily.getSeqNo());
        if ("Y".equals(bafamily.getStudMk())) {
            if (bastudtermList != null && !bastudtermList.isEmpty()) {
                for (Bastudterm o : bastudtermList) {
                    bastudtermDao.insertBastudterm(o);
                }
            }

            // 2011.05.26 新增 當案件主檔(seqNo=0000)的caseType!='1', 則判斷改前改後值，並存入BACOLUMNREC
            // [
            // 先將改前改後值List複製一分至新List,並將兩邊筆數設為一致方便比對

            if (!StringUtils.equals(ConstantKey.BAAPPBASE_CASETYP_1, caseType)) {
                List<Bastudterm> tmpBeforeList = new ArrayList<Bastudterm>();
                List<Bastudterm> tmpAfterList = new ArrayList<Bastudterm>();
                for (int i = 0; i < beforStudtermList.size(); i++) {
                    Bastudterm origObj = beforStudtermList.get(i);
                    Bastudterm modObj = new Bastudterm();
                    BeanUtility.copyProperties(modObj, origObj);
                    tmpBeforeList.add(modObj);
                }
                for (int i = 0; i < bastudtermList.size(); i++) {
                    Bastudterm origObj = bastudtermList.get(i);
                    Bastudterm modObj = new Bastudterm();
                    BeanUtility.copyProperties(modObj, origObj);
                    tmpAfterList.add(modObj);
                }

                if (tmpBeforeList.size() > tmpAfterList.size()) {
                    for (int i = tmpAfterList.size(); i < tmpBeforeList.size(); i++) {
                        Bastudterm modObj = new Bastudterm();
                        modObj.setTermNo(new BigDecimal(i + 1));
                        tmpAfterList.add(modObj);
                    }
                }
                else if (tmpBeforeList.size() < tmpAfterList.size()) {
                    for (int i = tmpBeforeList.size(); i < tmpAfterList.size(); i++) {
                        Bastudterm modObj = new Bastudterm();
                        modObj.setTermNo(new BigDecimal(i + 1));
                        tmpBeforeList.add(modObj);
                    }
                }

                // 開始比對
                for (int i = 0; i < tmpAfterList.size(); i++) {
                    Bastudterm origObj = tmpBeforeList.get(i);
                    Bastudterm modObj = tmpAfterList.get(i);

                    // 畫面欄位「在學起始年月」->BASTUDTERM. STUDSDATE && 「在學結束年月」->BASTUDTERM. STUDEDATE。

                    // 當眷屬關係為4、7時，且該欄位發生異動才需記錄。

                    // 寫入欄位名稱為畫面欄位+RowNo，例：第二筆的「在學起始年月」異動，則異動欄位名稱(BACOLUMNREC. COLUMNNAME) 欄位值寫入STUDEDATE2。

                    if (StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_4, bafamily.getFamEvtRel()) || StringUtils.equals(ConstantKey.BAAPPBASE_BENEVTREL_7, bafamily.getFamEvtRel())) {
                        chgDate = null;
                        chgDate = compareTwoDate(origObj.getStudSdate(), modObj.getStudSdate());
                        if (StringUtils.isNotBlank(chgDate)) {
                            bacolumnrec = new Bacolumnrec();
                            BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                            bacolumnrec.setColumnName("STUDSDATE" + modObj.getTermNo());
                            bacolumnrec.setColumnValue(chgDate);
                            bacolumnrecList.add(bacolumnrec);
                        }

                        chgDate = null;
                        chgDate = compareTwoDate(origObj.getStudEdate(), modObj.getStudEdate());
                        if (StringUtils.isNotBlank(chgDate)) {
                            bacolumnrec = new Bacolumnrec();
                            BeanUtility.copyProperties(bacolumnrec, tmpBacolumnrec);

                            bacolumnrec.setColumnName("STUDEDATE" + modObj.getTermNo());
                            bacolumnrec.setColumnValue(chgDate);
                            bacolumnrecList.add(bacolumnrec);
                        }
                    }
                }
            }
            // ]
        }

        // 2011.05.26 新增 當案件主檔(seqNo=0000)的caseType!='1', 則判斷改前改後值，並存入BACOLUMNREC
        // 比對結果資料不為0筆，寫入資料庫

        if (bacolumnrecList.size() > 0) {
            bacolumnrecDao.insertData(bacolumnrecList);
        }

        // Update Baappbase
        List<Baappbase> afterBaappbaseList = new ArrayList<Baappbase>();
        List<Baappbase> beforeBaappbaseList = baappbaseDao.selectDependantDataList(bafamily.getApNo());
        Baappbase afterBaappbase = new Baappbase();

        for (Baappbase beforeBaappbase : beforeBaappbaseList) {

            BeanUtility.copyProperties(afterBaappbase, beforeBaappbase);
            afterBaappbase.setProcStat("01");
            afterBaappbase.setUpdTime(DateUtility.getNowWestDateTime(true));
            afterBaappbase.setUpdUser(userData.getEmpNo());
            afterBaappbase.setChkDate("");
            afterBaappbase.setChkMan("");
            afterBaappbase.setRechkDate("");
            afterBaappbase.setRechkMan("");
            afterBaappbase.setExeDate("");
            afterBaappbase.setExeMan("");
            baappbaseDao.updateDataForDependant(afterBaappbase);

            afterBaappbaseList.add(afterBaappbase);
            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log

                FrameworkLogUtil.doUpdateLog(beforeBaappbase, afterBaappbase);
            }
        }

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doUpdateLog(beforBafamily, bafamily);
            FrameworkLogUtil.doUpdateListLog(beforStudtermList, bastudtermList);

        }
        log.debug("Insert  MMAPLOG Finished ...");

        // // 新增資料到 更正記錄檔
        // // [
        log.debug("Start Insert BAAPPLOG ...");
        BigDecimal baappbaseId = detailData.getBaappbaseId();
        List<Baapplog> logList = detailData.getBaapplogList();
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

    public void deleteDependantData(UserBean userData, DependantDataUpdateCase detailData, Bafamily dependantData) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(detailData.getApNo());

        // 刪除眷屬檔資料
        // [
        log.debug("Start Delete BAFAMILY ...");
        Bafamily bafamily = new Bafamily();
        BeanUtility.copyProperties(bafamily, dependantData);

        bafamilyDao.deleteDataForDependant(bafamily);
        bacompelnopayDao.deleteData(bafamily.getApNo(), bafamily.getSeqNo());

        List<Baappbase> beforeBaappbaseList = baappbaseDao.selectDependantDataList(bafamily.getApNo());
        Baappbase afterBaappbase = new Baappbase();
        for (Baappbase beforeBaappbase : beforeBaappbaseList) {
            BeanUtility.copyProperties(afterBaappbase, beforeBaappbase);
            afterBaappbase.setProcStat("01");
            afterBaappbase.setUpdTime(DateUtility.getNowWestDateTime(true));
            afterBaappbase.setUpdUser(userData.getEmpNo());
            afterBaappbase.setChkDate("");
            afterBaappbase.setChkMan("");
            afterBaappbase.setRechkDate("");
            afterBaappbase.setRechkMan("");
            afterBaappbase.setExeDate("");
            afterBaappbase.setExeMan("");
            baappbaseDao.updateDataForDependant(afterBaappbase);
            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBaappbase, afterBaappbase);
            }
        }

        bachkcontrlDao.deleteDataBySeqNo(bafamily.getApNo(), bafamily.getSeqNo());

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteLog(bafamily);
        }

        // 新增資料到 更正記錄檔
        // [
        log.debug("Start Insert BAAPPLOG ...");
        Baapplog baapplog = new Baapplog();
        baapplog.setBaappbaseId(detailData.getBaappbaseId());// 資料列編號
        baapplog.setStatus("D");// 狀態 'A'-新增，'U'-修改，'D'-刪除
        baapplog.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動時間
        baapplog.setUpdUser(userData.getEmpNo());// 異動人員
        // baapplog.setUpCol(); // 異動項目
        // baapplog.setBvalue("");// 改前內容
        // baapplog.setAvalue(); // 改後內容
        baapplogDao.insertData(baapplog);
        log.debug("Insert BAAPPLOG Finished ...");
        // ]

        log.debug("Delete BAAPPLOG Finished ...");
    }

    /**
     * 依傳入條件取得 眷屬檔 for 眷屬資料更正作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @return
     */
    public DependantDataUpdateCase getDependantDataUpdateForUpdateCaseBy(String bafamilyId) {
        Bafamily bafamily = bafamilyDao.getDependantDataUpdateForUpdateCaseBy(bafamilyId);
        DependantDataUpdateCase detailData = new DependantDataUpdateCase();
        BeanUtility.copyProperties(detailData, bafamily);
        detailData.setFamBrDate(DateUtility.changeDateType(detailData.getFamBrDate()));
        return detailData;
    }

    /**
     * 依傳入條件取得 眷屬強迫不合格記錄檔 for 眷屬資料更正作業 (<code>BACOMPELNOPAY</code>)
     */
    public List<DependantDataUpdateCompelDataCase> getCompelDataListForDependant(String apNo, String seqNo) {
        List<Bacompelnopay> list = bacompelnopayDao.selectDataBy(apNo, seqNo);
        List<DependantDataUpdateCompelDataCase> returnList = new ArrayList<DependantDataUpdateCompelDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Bacompelnopay obj = list.get(i);
            DependantDataUpdateCompelDataCase caseObj = new DependantDataUpdateCompelDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 for 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param chkTyp 編審註記種類 (B:眷屬編審註記, C:符合註記)
     * @return <code>DisabledPaymentReviewExtCase</code> 物件 List
     */
    @SuppressWarnings("unchecked")
    public List<PaymentQueryDetailDataCase> getPaymentQueryOtherChkList(String apNo, String seqNo, String chkTyp, String familySeqNo) {
        List<Bachkfile> list = bachkfileDao.selectReviewChkListBy(apNo, seqNo, chkTyp, familySeqNo);

        List<PaymentQueryDetailDataCase> returnList = new ArrayList<PaymentQueryDetailDataCase>();
        List<PaymentQueryDetailDataCase> subList = new ArrayList<PaymentQueryDetailDataCase>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 建立資料Map
        // Map的Key 為 seqNo
        // Value則是同為該 seqNo 下的所有編審註記資料

        Map<String, List<PaymentQueryChkFileDataCase>> map = new TreeMap<String, List<PaymentQueryChkFileDataCase>>();
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);

            map.put(obj.getSeqNo(), new ArrayList<PaymentQueryChkFileDataCase>());
        }
        for (int i = 0; i < list.size(); i++) {
            Bachkfile obj = (Bachkfile) list.get(i);
            PaymentQueryChkFileDataCase caseObj = new PaymentQueryChkFileDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            (map.get(caseObj.getSeqNo())).add(caseObj);
        }

        // 將 分類好的 map 轉為 list
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            subList = new ArrayList<PaymentQueryDetailDataCase>();
            String key = (String) it.next();
            List<PaymentQueryChkFileDataCase> tempList = map.get(key);

            // 建立資料SubMap
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 issuYm+payYm 下的所有編審註記資料

            Map<String, List<PaymentQueryChkFileDataCase>> subMap = new TreeMap<String, List<PaymentQueryChkFileDataCase>>();
            for (int i = 0; i < tempList.size(); i++) {
                PaymentQueryChkFileDataCase obj = (PaymentQueryChkFileDataCase) tempList.get(i);
                subMap.put(obj.getIssuYmStr() + "-" + obj.getPayYmStr(), new ArrayList<PaymentQueryChkFileDataCase>());
            }

            for (int i = 0; i < tempList.size(); i++) {
                PaymentQueryChkFileDataCase obj = (PaymentQueryChkFileDataCase) tempList.get(i);
                (subMap.get(obj.getIssuYmStr() + "-" + obj.getPayYmStr())).add(obj);
            }

            // 將 分類好的 SubMap 轉為 list
            List<PaymentQueryChkFileDataCase> subTempList = new ArrayList<PaymentQueryChkFileDataCase>();
            for (Iterator subIt = subMap.keySet().iterator(); subIt.hasNext();) {
                String subKey = (String) subIt.next();
                subTempList = subMap.get(subKey);
                PaymentQueryDetailDataCase caseObj = new PaymentQueryDetailDataCase();
                caseObj.setIssuPayYm(subKey);
                caseObj.setChkFileDataList(subTempList);
                caseObj.setChkFileDataSize(subTempList.size());
                subList.add(caseObj);
            }
            if (subTempList.size() != 0) {
                PaymentQueryDetailDataCase masterCase = new PaymentQueryDetailDataCase();
                masterCase.setSeqNo(key);
                masterCase.setOtherChkDataList(subList);
                returnList.add(masterCase);
            }
        }
        return returnList;
    }

    /**
     * 依傳入之 受理編號 取得 失能年金案件資料更正 之資料 for 失能年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    public DisabledApplicationDataUpdateCase getDisabledApplicationDataUpdateData(String apNo) {
        DisabledApplicationDataUpdateCase caseData = new DisabledApplicationDataUpdateCase();

        // 取得 BAAPPBASE 資料
        // [
        Baappbase masterData = baappbaseDao.getDisabledApplicationDataUpdateMasterDataBy(apNo);

        if (masterData == null)
            return null;

        caseData.setBaappbaseId(masterData.getBaappbaseId()); // 資料列編號
        caseData.setApNo(masterData.getApNo()); // 受理編號
        caseData.setSeqNo(masterData.getSeqNo()); // 序號
        caseData.setPayKind(masterData.getPayKind());// 給付種類
        caseData.setProcStat(masterData.getProcStat()); // 處理狀態
        caseData.setEvtNationTpe(masterData.getEvtNationTpe()); // 事故者國籍別
        caseData.setEvtNationCode(masterData.getEvtNationCode()); // 事故者國籍
        caseData.setEvtSex(masterData.getEvtSex()); // 事故者性別
        caseData.setEvtName(masterData.getEvtName()); // 事故者姓名
        caseData.setEvtIdnNo(masterData.getEvtIdnNo()); // 事故者身分證號
        caseData.setEvtBrDate(masterData.getEvtBrDate()); // 事故者出生日期
        caseData.setEvtJobDate(masterData.getEvtJobDate()); // 事故者離職日期 - 診斷失能日期
        caseData.setEvtDieDate(masterData.getEvtDieDate()); // 事故者死亡日期
        caseData.setAppDate(masterData.getAppDate()); // 申請日期
        caseData.setApUbno(masterData.getApUbno()); // 申請單位保險證號
        caseData.setApubnock(masterData.getApubnock()); // 申請單位保險證號檢查碼
        caseData.setLsUbno(masterData.getLsUbno()); // 最後單位保險證號 - 事故發生單位保險證號
        caseData.setLsUbnoCk(masterData.getLsUbnoCk()); // 最後單位保險證號檢查碼
        caseData.setNotifyForm(masterData.getNotifyForm()); // 核定通知書格式
        caseData.setCutAmt(masterData.getCutAmt()); // 扣減 / 補償總金額 - 年金應扣金額
        caseData.setLoanMk(masterData.getLoanMk()); // 不須抵銷紓困貸款註記
        caseData.setCloseCause(masterData.getCloseCause()); // 結案原因
        caseData.setChoiceYm(masterData.getChoiceYm()); // 擇領起月
        caseData.setDupeIdnNoMk(masterData.getDupeIdnNoMk()); // 身分證重號註記
        caseData.setInterValMonth(masterData.getInterValMonth());// 發放方式
        caseData.setMapNo(masterData.getMapNo());// 國保受理編號
        caseData.setSysCode(masterData.getSysCode());// 系統類別
        caseData.setApnoFm(masterData.getApnoFm());// 來源受理編號
        // ]

        // 取得 BAAPPEXPAND 資料
        // [
        Baappexpand expandData = baappexpandDao.getDisabledApplicationDataUpdateExpandDataBy(caseData.getBaappbaseId(), caseData.getApNo(), caseData.getSeqNo());

        if (expandData != null) {
            caseData.setBaappexpandId(expandData.getBaappexpandId()); // 資料編號
            caseData.setEvTyp(expandData.getEvTyp()); // 傷病分類
            caseData.setEvCode(expandData.getEvCode()); // 傷病原因
            caseData.setCriInPart1(expandData.getCriInPart1()); // 受傷部位1
            caseData.setCriInPart2(expandData.getCriInPart2()); // 受傷部位2
            caseData.setCriInPart3(expandData.getCriInPart3()); // 受傷部位3
            caseData.setCriMedium(expandData.getCriMedium()); // 媒介物
            caseData.setCriInIssul(expandData.getCriInIssul()); // 核定等級
            caseData.setCriInJcl1(expandData.getCriInJcl1()); // 失能等級1
            caseData.setCriInJcl2(expandData.getCriInJcl2()); // 失能等級2
            caseData.setCriInJcl3(expandData.getCriInJcl3()); // 失能等級3
            caseData.setCriInJdp1(expandData.getCriInJdp1()); // 失能項目1
            caseData.setCriInJdp2(expandData.getCriInJdp2()); // 失能項目2
            caseData.setCriInJdp3(expandData.getCriInJdp3()); // 失能項目3
            caseData.setCriInJdp4(expandData.getCriInJdp4()); // 失能項目4
            caseData.setCriInJdp5(expandData.getCriInJdp5()); // 失能項目5
            caseData.setCriInJdp6(expandData.getCriInJdp6()); // 失能項目6
            caseData.setCriInJdp7(expandData.getCriInJdp7()); // 失能項目7
            caseData.setCriInJdp8(expandData.getCriInJdp8()); // 失能項目8
            caseData.setCriInJdp9(expandData.getCriInJdp9()); // 失能項目9
            caseData.setCriInJdp10(expandData.getCriInJdp10()); // 失能項目10
            caseData.setHosId(expandData.getHosId()); // 醫療院所代碼
            caseData.setDoctorName1(expandData.getDoctorName1()); // 醫師姓名1
            caseData.setDoctorName2(expandData.getDoctorName2()); // 醫師姓名2
            caseData.setOcAccHosId(expandData.getOcAccHosId()); // 職病醫療院所代碼
            caseData.setOcAccDoctorName1(expandData.getOcAccDoctorName1()); // 職病醫師姓名1
            caseData.setOcAccDoctorName2(expandData.getOcAccDoctorName2()); // 職病醫師姓名2
            caseData.setCriInJnme1(expandData.getCriInJnme1()); // 國際疾病代碼1
            caseData.setCriInJnme2(expandData.getCriInJnme2()); // 國際疾病代碼2
            caseData.setCriInJnme3(expandData.getCriInJnme3()); // 國際疾病代碼3
            caseData.setCriInJnme4(expandData.getCriInJnme4()); // 國際疾病代碼4
            caseData.setRehcMk(expandData.getRehcMk()); // 重新查核失能程度註記
            caseData.setRehcYm(expandData.getRehcYm()); // 重新查核失能程度年月
            caseData.setOcaccIdentMk(expandData.getOcaccIdentMk()); // 符合第20條之1註記
            caseData.setPrType(expandData.getPrType()); // 先核普通
            caseData.setOcAccaddAmt(expandData.getOcAccaddAmt()); // 己領職災增給金額
            caseData.setDeductDay(expandData.getDeductDay()); // 扣除天數
            caseData.setEvAppTyp(expandData.getEvAppTyp());// 申請傷病分類
            caseData.setDisQualMk(expandData.getDisQualMk());// 年金請領資格
            caseData.setMonNotifyingMk(expandData.getMonNotifyingMk()); // 寄發月通知表
        }
        // ]

        // 取得 CVLDTL 資料 - 戶籍姓名
        // [
        List<Cvldtl> cvlList = cvldtlDao.selectDataBy(caseData.getEvtIdnNo(), caseData.getEvtBrDate());
        if (cvlList.size() > 0) {
            Cvldtl cvlData = cvlList.get(0);
            caseData.setName(cvlData.getName()); // 戶籍姓名
        }
        // ]

        // 取得 BBCMF07 資料 - 醫療院所名稱
        // [
        if (StringUtils.isNotBlank(caseData.getHosId())) {
            Bbcmf07 hosData = bbcmf07Dao.getDisabledApplicationDataUpdateHosDataBy(caseData.getHosId());
            if (hosData != null)
                caseData.setHosName(hosData.getHpSnam());
        }
        // ]

        // 是否顯示 註銷 按鍵
        // [
        Integer cancelCount = baappbaseDao.getDisabledApplicationDataUpdateIsShowCancelButtonBy(caseData.getApNo());
        if (cancelCount != null && cancelCount.intValue() > 0) {
            caseData.setShowCancelButton(true);
        }
        // ]

        // 事故者姓名 可否修改
        // [
        Integer modifyCount = baappbaseDao.getDisabledApplicationDataUpdateCanModifyEvtNameBy(caseData.getApNo());
        if (modifyCount != null & modifyCount.intValue() > 0) {
            caseData.setCanModifyEvtName(true);
        }
        // ]

        // 取得 BADUPEIDN 資料 - 身分證重號資料

        // [
        List<Badupeidn> dupeList = badupeidnDao.getDisabledApplicationDataUpdateDupeIdnListBy(caseData.getApNo(), caseData.getEvtIdnNo());
        for (Badupeidn dupeData : dupeList) {
            DisabledApplicationDataUpdateDupeIdnCase dupeCase = new DisabledApplicationDataUpdateDupeIdnCase();
            dupeCase.setApNo(dupeData.getApNo()); // 受理編號
            dupeCase.setSeqNo(dupeData.getSeqNo()); // 序號
            dupeCase.setIdnNo(dupeData.getIdnNo()); // 身分證號
            dupeCase.setName(dupeData.getName()); // 姓名
            dupeCase.setBrDate(dupeData.getBrDate()); // 出生日期
            dupeCase.setSelMk(dupeData.getSelMk()); // 選擇註記
            dupeCase.setUpdUser(dupeData.getUpdUser()); // 異動者代號

            dupeCase.setUpdTime(dupeData.getUpdTime()); // 異動日期時間
            caseData.addDupeIdnData(dupeCase);
        }
        // ]

        // 取得 BACHKFILE 資料 - 編審註記資料
        // [
        List<Bachkfile> checkFileList = bachkfileDao.getDisabledApplicationDataUpdateCheckFileListBy(caseData.getBaappbaseId());
        for (Bachkfile checkFileData : checkFileList) {
            DisabledApplicationDataUpdateCheckFileCase checkFileCase = new DisabledApplicationDataUpdateCheckFileCase();
            checkFileCase.setPayYm(checkFileData.getPayYm()); // 給付年月
            checkFileCase.setChkFileName(checkFileData.getChkFileName()); // 編審註記代號
            checkFileCase.setChkFileDesc(checkFileData.getChkFileDesc()); // 編審註記代號
            checkFileCase.setChkFileCode(checkFileData.getChkFileCode()); // 編審註記代號
            caseData.addCheckFileData(checkFileCase);
        }
        // ]

        // 取得 BADAPR 資料
        // [
        // 非本人的受款人筆數

        Integer benCount = badaprDao.getDisabledApplicationDataUpdateBenCountBy(caseData.getApNo());
        caseData.setBenCount(((benCount == null) ? new Integer(0) : benCount));

        // 月核後受款人筆數
        Integer benCountAfterCheck = badaprDao.getDisabledApplicationDataUpdateBenCountAfterCheckBy(caseData.getApNo());
        caseData.setBenCountAfterCheck(((benCountAfterCheck == null) ? new Integer(0) : benCountAfterCheck));
        // ]

        // 2013.07.23 新增
        if (StringUtils.equals(caseData.getPayKind(), "36")) {
            caseData.setComSeniority(nbappbaseDao.selectLabmergeByApNo(masterData.getMapNo()));
            // }else if (StringUtils.equals(caseData.getPayKind(), "38")){
        }
        else if (caseData.isContainCheckMark3M()) {
            caseData.setComSeniority(baappexpandDao.selectComnpmkBy(caseData.getBaappbaseId(), caseData.getApNo(), caseData.getSeqNo()));
        }
        else {
            caseData.setComSeniority("");
        }

        return caseData;
    }

    /**
     * 失能年金案件資料更正 - 存檔
     * 
     * @param caseData
     */
    public void updateDisabledApplicationDataUpdateData(UserBean userData, DisabledApplicationDataUpdateCase caseData, DisabledApplicationDataUpdateForm updateForm) {
        String dateTime = DateUtility.getNowWestDateTime(true);

        List<Baappbase> oldMasterList = new ArrayList<Baappbase>();
        List<Baappbase> masterList = new ArrayList<Baappbase>();
        List<Baappexpand> oldExpandList = new ArrayList<Baappexpand>();
        List<Baappexpand> expandList = new ArrayList<Baappexpand>();

        // update BALP0D020 PROCMK
        updateProcMkByApNo(caseData.getApNo());

        // 更新 BAAPPBASE 資料
        // [
        // 取得須更新的主檔資料列編號
        List<BigDecimal> baappbaseIdList = baappbaseDao.getDisabledApplicationDataUpdateListForUpdateBy(caseData.getApNo());

        for (BigDecimal baappbaseId : baappbaseIdList) {
            Baappbase masterData = baappbaseDao.getDisabledApplicationDataUpdateMasterDataByBaappbaseId(baappbaseId);

            // for MMAPLOG
            Baappbase oldMasterData = (Baappbase) BeanUtility.cloneBean(masterData);
            oldMasterList.add(oldMasterData);

            // 設定要更新的主檔資料
            masterData.setEvtNationTpe(caseData.getEvtNationTpe()); // 事故者國籍別
            masterData.setEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍
            masterData.setEvtSex(caseData.getEvtSex()); // 事故者性別
            masterData.setEvtName(caseData.getEvtName()); // 事故者姓名
            masterData.setEvtIdnNo(caseData.getEvtIdnNo()); // 事故者身分證號
            masterData.setEvtBrDate((StringUtils.length(caseData.getEvtBrDate()) == 7 || StringUtils.equals(StringUtils.substring(caseData.getEvtBrDate(), 0, 1), "-")) ? DateUtility.changeDateType(caseData.getEvtBrDate()) : caseData.getEvtBrDate()); // 事故者出生日期
            masterData.setEvtJobDate((StringUtils.length(caseData.getEvtJobDate()) == 7) ? DateUtility.changeDateType(caseData.getEvtJobDate()) : caseData.getEvtJobDate()); // 事故者離職日期 - 診斷失能日期
            masterData.setEvtDieDate((StringUtils.length(caseData.getEvtDieDate()) == 7) ? DateUtility.changeDateType(caseData.getEvtDieDate()) : caseData.getEvtDieDate()); // 事故者死亡日期
            masterData.setEvtAge(BaBusinessUtility.calAge((StringUtils.length(caseData.getAppDate()) == 7) ? DateUtility.changeDateType(caseData.getAppDate()) : caseData.getAppDate(),
                            (StringUtils.length(caseData.getEvtBrDate()) == 7 || StringUtils.equals(StringUtils.substring(caseData.getEvtBrDate(), 0, 1), "-")) ? DateUtility.changeDateType(caseData.getEvtBrDate()) : caseData.getEvtBrDate())); // 事故者申請時年齡
            // 事故者社福識別碼
            // [
            List<Cvldtl> cvlList = cvldtlDao.selectDataBy(caseData.getEvtIdnNo(), caseData.getEvtBrDate());
            if (cvlList.size() > 0) {
                Cvldtl cvlData = cvlList.get(0);
                masterData.setEvtIds(cvlData.getNpIds()); // 事故者社福識別碼
            }
            else {
                masterData.setEvtIds(""); // 事故者社福識別碼
            }
            // ]
            masterData.setAppDate((StringUtils.length(caseData.getAppDate()) == 7) ? DateUtility.changeDateType(caseData.getAppDate()) : caseData.getAppDate()); // 申請日期
            masterData.setApUbno(caseData.getApUbno()); // 申請單位保險證號
            masterData.setLsUbno(caseData.getLsUbno()); // 最後單位保險證號 - 事故發生單位保險證號
            masterData.setNotifyForm(caseData.getNotifyForm()); // 核定通知書格式
            masterData.setLoanMk(caseData.getLoanMk()); // 不須抵銷紓困貸款註記
            masterData.setCloseCause(caseData.getCloseCause()); // 結案原因
            masterData.setChoiceYm((StringUtils.length(caseData.getChoiceYm()) == 5) ? DateUtility.changeChineseYearMonthType(caseData.getChoiceYm()) : caseData.getChoiceYm()); // 擇領起月
            masterData.setCutAmt(caseData.getCutAmt()); // 扣減 / 補償總金額 - 年金應扣金額
            masterData.setUpdUser(userData.getEmpNo()); // 異動者代號
            masterData.setUpdTime(dateTime); // 異動日期時間
            masterData.setInterValMonth(caseData.getInterValMonth());// 發放方式
            // 20140325 有3M註記加入國保受理編號更新
            masterData.setMapNo(caseData.getMapNo());// 國保受理編號

            if (!StringUtils.equals(caseData.getEvtName(), updateForm.getOldEvtName()))
                masterData.setEvtAppName(caseData.getEvtName()); // 事故者申請時姓名
            if (!StringUtils.equals(caseData.getEvtIdnNo(), updateForm.getOldEvtIdnNo()))
                masterData.setEvtAppIdnNo(caseData.getEvtIdnNo()); // 事故者申請時身分證號
            if (!StringUtils.equals(updateForm.getEvtBrDate(), updateForm.getOldEvtBrDate()))
                masterData.setEvtAppBrDate(
                                (StringUtils.length(caseData.getEvtBrDate()) == 7 || StringUtils.equals(StringUtils.substring(caseData.getEvtBrDate(), 0, 1), "-")) ? DateUtility.changeDateType(caseData.getEvtBrDate()) : caseData.getEvtBrDate()); // 事故者申請時出生日期

            // BAAPPBASE.ACCIDN 處理
            if (StringUtils.equalsIgnoreCase(masterData.getSeqNo(), "0000")) {
                if (StringUtils.equalsIgnoreCase(masterData.getAccRel(), "1")) {
                    if (StringUtils.equalsIgnoreCase(masterData.getPayTyp(), "3") || StringUtils.equalsIgnoreCase(masterData.getPayTyp(), "4") || StringUtils.equalsIgnoreCase(masterData.getPayTyp(), "A"))
                        masterData.setAccIdn("");
                    else
                        masterData.setAccIdn(masterData.getEvtIdnNo());
                }
                else if (StringUtils.equalsIgnoreCase(masterData.getAccRel(), "3")) {
                    if (StringUtils.isNotBlank(StringUtils.trimToEmpty(masterData.getAccSeqNo()))) {
                        List<Baappbase> tempList = baappbaseDao.selectDataBy(null, masterData.getApNo(), masterData.getAccSeqNo(), null, null, null);
                        if (tempList != null && tempList.size() > 0) {
                            Baappbase tempData = tempList.get(0);
                            masterData.setAccIdn(tempData.getAccIdn());
                        }
                    }
                }
            }
            masterList.add(masterData);
        }

        baappbaseDao.updateDisabledApplicationDataUpdateData(masterList);
        // ]

        // 更新 BAAPPEXPAND 資料
        // [
        // 取得須更新的給付延伸主檔資料列編號

        List<BigDecimal> baappexpandIdList = baappexpandDao.getDisabledApplicationDataUpdateListForUpdateBy(caseData.getApNo(), caseData.getBaappbaseId());

        for (BigDecimal baappexpandId : baappexpandIdList) {
            Baappexpand expandData = baappexpandDao.getDisabledApplicationDataUpdateExpandDataByBaappexpandId(baappexpandId);

            // for MMAPLOG
            Baappexpand oldExpandData = (Baappexpand) BeanUtility.cloneBean(expandData);
            oldExpandList.add(oldExpandData);

            // 設定要更新的給付延伸主檔資料
            expandData.setEvAppTyp(caseData.getEvAppTyp()); // 申請傷病分類
            expandData.setEvTyp(caseData.getEvTyp()); // 傷病分類
            expandData.setDisQualMk(caseData.getDisQualMk()); // 年金請領資格
            expandData.setEvCode(caseData.getEvCode()); // 傷病原因
            expandData.setCriInPart1(caseData.getCriInPart1()); // 受傷部位1
            expandData.setCriInPart2(caseData.getCriInPart2()); // 受傷部位2
            expandData.setCriInPart3(caseData.getCriInPart3()); // 受傷部位3
            expandData.setCriMedium(caseData.getCriMedium()); // 媒介物
            expandData.setCriInIssul(caseData.getCriInIssul()); // 核定等級
            expandData.setCriInJdp1(caseData.getCriInJdp1()); // 失能項目1
            expandData.setCriInJdp2(caseData.getCriInJdp2()); // 失能項目2
            expandData.setCriInJdp3(caseData.getCriInJdp3()); // 失能項目3
            expandData.setCriInJdp4(caseData.getCriInJdp4()); // 失能項目4
            expandData.setCriInJdp5(caseData.getCriInJdp5()); // 失能項目5
            expandData.setCriInJdp6(caseData.getCriInJdp6()); // 失能項目6
            expandData.setCriInJdp7(caseData.getCriInJdp7()); // 失能項目7
            expandData.setCriInJdp8(caseData.getCriInJdp8()); // 失能項目8
            expandData.setCriInJdp9(caseData.getCriInJdp9()); // 失能項目9
            expandData.setCriInJdp10(caseData.getCriInJdp10()); // 失能項目10
            expandData.setHosId(caseData.getHosId()); // 醫療院所代碼
            expandData.setDoctorName1(caseData.getDoctorName1()); // 醫師姓名1
            expandData.setDoctorName2(caseData.getDoctorName2()); // 醫師姓名2
            expandData.setOcAccHosId(caseData.getOcAccHosId()); // 職病醫療院所代碼
            expandData.setOcAccDoctorName1(caseData.getOcAccDoctorName1()); // 職病醫師姓名1
            expandData.setOcAccDoctorName2(caseData.getOcAccDoctorName2()); // 職病醫師姓名2
            expandData.setCriInJnme1(caseData.getCriInJnme1()); // 國際疾病代碼1
            expandData.setCriInJnme2(caseData.getCriInJnme2()); // 國際疾病代碼2
            expandData.setCriInJnme3(caseData.getCriInJnme3()); // 國際疾病代碼3
            expandData.setCriInJnme4(caseData.getCriInJnme4()); // 國際疾病代碼4
            expandData.setRehcMk(caseData.getRehcMk()); // 重新查核失能程度註記
            // if (重新查核失能程度註記 = 1) 重新查核失能程度年月 = 系統年月＋61個月;
            // if(caseData.getRehcMk().equals("1")){
            // String nowTime = DateUtility.getNowWestDateTime(true);
            // String nowDate = StringUtils.substring(nowTime, 0, 8);
            // //系統年月＋61個月;
            // String newDate = DateUtility.calMonth(nowDate,61).substring(0, 6); // 重新查核失能程度年月
            // expandData.setRehcYm(newDate);
            // }else{
            expandData.setRehcYm((StringUtils.length(caseData.getRehcYm()) == 5) ? DateUtility.changeChineseYearMonthType(caseData.getRehcYm()) : caseData.getRehcYm()); // 重新查核失能程度年月
            // }
            expandData.setOcaccIdentMk(caseData.getOcaccIdentMk()); // 符合第20條之1註記
            expandData.setPrType(caseData.getPrType()); // 先核普通
            expandData.setOcAccaddAmt(caseData.getOcAccaddAmt()); // 己領職災增給金額
            expandData.setDeductDay(caseData.getDeductDay()); // 扣除天數
            expandData.setUpdUser(userData.getEmpNo()); // 異動者代號
            expandData.setUpdTime(dateTime); // 異動日期時間
            expandData.setMonNotifyingMk(caseData.getMonNotifyingMk()); // 寄發月通知表

            if (caseData.isContainCheckMark3M()) {
                expandData.setComnpMk((StringUtils.isNotBlank(updateForm.getComSeniority()) ? updateForm.getComSeniority() : ""));
            }

            // if(StringUtils.equals(caseData.getPayKind(), "38")){
            // expandData.setComnpMk((StringUtils.isNotBlank(updateForm.getComSeniority())?updateForm.getComSeniority():""));
            // }

            expandList.add(expandData);
        }

        baappexpandDao.updateDisabledApplicationDataUpdateData(expandList);
        // ]

        // 紀錄 BAAPPLOG
        updateForm.setRehcYm(DateUtility.changeChineseYearMonthType(updateForm.getRehcYm()));
        if (StringUtils.isBlank(updateForm.getLoanMk())) {
            updateForm.setLoanMk("0");
        }
        if (StringUtils.equals(updateForm.getMonNotifyingMk(), "Y"))
            updateForm.setMonNotifyingMk("Y"); // 寄發月通知表
        else
            updateForm.setMonNotifyingMk("N"); // 寄發月通知表

        List<Baapplog> baapplogList = LoggingHelper.getBaapplogList(updateForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_DISABLED_APPLICATION_DATA_UPDATE_FIELD_RESOURCE_PREFIX);
        for (Baapplog baapplog : baapplogList) {
            baapplog.setBaappbaseId(caseData.getBaappbaseId());
            baapplog.setStatus("U");
            baapplog.setUpdTime(dateTime);
            baapplog.setUpdUser(userData.getEmpNo());
        }
        baapplogDao.insertData(baapplogList);

        // 紀錄 MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            FrameworkLogUtil.doUpdateListLog(oldMasterList, masterList);
            FrameworkLogUtil.doUpdateListLog(oldExpandList, expandList);
        }
    }

    /**
     * 失能年金案件資料更正 - 身分證號重號存檔
     * 
     * @param caseData
     */
    public void updateDisabledApplicationDataUpdateDupeIdnData(UserBean userData, DisabledApplicationDataUpdateDupeIdnCase dupeIdnCase) {
        String dateTime = DateUtility.getNowWestDateTime(true);

        List<Baappbase> oldMasterList = new ArrayList<Baappbase>();
        List<Baappbase> masterList = new ArrayList<Baappbase>();

        // 更新 BAAPPBASE 資料
        // [
        // 取得須更新的主檔資料列編號

        List<BigDecimal> baappbaseIdList = baappbaseDao.getDisabledApplicationDataUpdateListForUpdateBy(dupeIdnCase.getApNo());

        for (BigDecimal baappbaseId : baappbaseIdList) {
            Baappbase masterData = baappbaseDao.getDisabledApplicationDataUpdateMasterDataByBaappbaseId(baappbaseId);

            // for MMAPLOG
            Baappbase oldMasterData = (Baappbase) BeanUtility.cloneBean(masterData);
            oldMasterList.add(oldMasterData);

            // 設定要更新的主檔資料
            masterData.setEvtName(dupeIdnCase.getName()); // 事故者姓名

            masterData.setEvtIdnNo(dupeIdnCase.getIdnNoTrim()); // 事故者身分證號

            masterData.setEvtBrDate(dupeIdnCase.getBrDate()); // 事故者出生日期

            // 事故者社福識別碼
            // [
            List<Cvldtl> cvlList = cvldtlDao.selectDataBy(dupeIdnCase.getIdnNo(), dupeIdnCase.getBrDate());
            if (cvlList.size() > 0) {
                Cvldtl cvlData = cvlList.get(0);
                masterData.setEvtIds(cvlData.getNpIds()); // 事故者社福識別碼
            }
            else {
                masterData.setEvtIds(""); // 事故者社福識別碼
            }
            // ]
            masterData.setUpdUser(userData.getEmpNo()); // 異動者代號

            masterData.setUpdTime(dateTime); // 異動日期時間
            masterList.add(masterData);
        }

        baappbaseDao.updateDisabledApplicationDataUpdateDupeIdnData(masterList);
        // ]

        // 更新 BADUPEIDN 資料
        // [
        Badupeidn dupeData = new Badupeidn();
        dupeData.setApNo(dupeIdnCase.getApNo()); // 受理編號
        dupeData.setSeqNo(dupeIdnCase.getSeqNo()); // 序號
        dupeData.setIdnNo(dupeIdnCase.getIdnNo()); // 身分證號
        dupeData.setName(dupeIdnCase.getName()); // 姓名
        dupeData.setBrDate(dupeIdnCase.getBrDate()); // 出生日期
        dupeData.setSelMk(dupeIdnCase.getSelMk()); // 選擇註記
        dupeData.setUpdUser(userData.getEmpNo()); // 異動者代號

        dupeData.setUpdTime(dateTime); // 異動日期時間

        // 重置
        badupeidnDao.updateDisabledApplicationDataUpdateResetDupeIdnData(dupeData);

        // 更新
        badupeidnDao.updateDisabledApplicationDataUpdateDupeIdnData(dupeData);
        // ]

        // 紀錄 MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            FrameworkLogUtil.doUpdateListLog(oldMasterList, masterList);
        }
    }

    /**
     * 失能年金案件資料更正 - 註銷
     * 
     * @param caseData
     */
    public void deleteDisabledApplicationDataUpdateData(UserBean userData, String apNo) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(apNo);

        List<Baappbase> masterList = new ArrayList<Baappbase>(); // for MMAPLOG

        // 此 List 要在註銷案件前先取 for BAAPPLOG
        List<BigDecimal> baappbaseIdList = baappbaseDao.getDisabledApplicationDataUpdateListForDeleteBy(apNo);

        // for MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            for (BigDecimal baappbaseId : baappbaseIdList) {
                Baappbase masterData = baappbaseDao.getDisabledApplicationDataUpdateMasterDataByBaappbaseId(baappbaseId);
                masterList.add(masterData);
            }
        }

        // 註銷案件
        baappbaseDao.deleteDisabledApplicationDataUpdateData(apNo, userData);
        String dateTime = DateUtility.getNowWestDateTime(true);
        // 紀錄 BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        for (BigDecimal baappbaseId : baappbaseIdList) {
            Baapplog baapplog = new Baapplog();
            baapplog.setBaappbaseId(baappbaseId);
            baapplog.setStatus("D");
            baapplog.setUpdTime(dateTime);
            baapplog.setUpdUser(userData.getEmpNo());

            baapplogList.add(baapplog);
        }
        baapplogDao.insertData(baapplogList);

        // 紀錄 MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            FrameworkLogUtil.doDeleteListLog(masterList);
        }
    }

    /**
     * 依傳入之 受理編號 取得 遺屬年金案件資料更正 之資料 for 遺屬年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    public SurvivorApplicationDataUpdateCase getSurvivorApplicationDataUpdateData(String apNo) {
        SurvivorApplicationDataUpdateCase caseData = new SurvivorApplicationDataUpdateCase();

        // 取得 BAAPPBASE 資料
        // [
        Baappbase masterData = baappbaseDao.getSurvivorApplicationDataUpdateMasterDataBy(apNo);

        if (masterData == null)
            return null;

        caseData.setBaappbaseId(masterData.getBaappbaseId()); // 資料列編號

        caseData.setApNo(masterData.getApNo()); // 受理編號
        caseData.setSeqNo(masterData.getSeqNo()); // 序號
        caseData.setProcStat(masterData.getProcStat()); // 處理狀態

        caseData.setEvtNationTpe(masterData.getEvtNationTpe()); // 事故者國籍別
        caseData.setEvtNationCode(masterData.getEvtNationCode()); // 事故者國籍

        caseData.setEvtSex(masterData.getEvtSex()); // 事故者性別
        caseData.setEvtName(masterData.getEvtName()); // 事故者姓名

        caseData.setEvtIdnNo(masterData.getEvtIdnNo()); // 事故者身分證號

        caseData.setEvtBrDate(masterData.getEvtBrDate()); // 事故者出生日期

        caseData.setEvtDieDate(masterData.getEvtDieDate()); // 事故者死亡日期

        caseData.setAppDate(masterData.getAppDate()); // 申請日期
        caseData.setApUbno(masterData.getApUbno()); // 申請單位保險證號
        caseData.setApubnock(masterData.getApubnock()); // 申請單位保險證號檢查碼

        caseData.setLsUbno(masterData.getLsUbno()); // 最後單位保險證號 - 事故發生單位保險證號
        caseData.setLsUbnoCk(masterData.getLsUbnoCk()); // 最後單位保險證號檢查碼
        caseData.setNotifyForm(masterData.getNotifyForm()); // 核定通知書格式

        caseData.setLoanMk(masterData.getLoanMk()); // 不須抵銷紓困貸款註記
        caseData.setCloseCause(masterData.getCloseCause()); // 結案原因
        caseData.setChoiceYm(masterData.getChoiceYm()); // 擇領起月
        caseData.setDupeIdnNoMk(masterData.getDupeIdnNoMk()); // 身分證重號註記

        caseData.setApItem(masterData.getApItem()); // 申請項目
        caseData.setCutAmt(masterData.getCutAmt());// 應扣失能金額
        caseData.setInterValMonth(masterData.getInterValMonth());// 發放方式
        caseData.setIssuNotifyingMk(masterData.getIssuNotifyingMk()); // 寄發核定通知書
        caseData.setCaseTyp(masterData.getCaseTyp()); // 案件類別
        caseData.setSysCode(masterData.getSysCode());// 系統類別
        caseData.setApnoFm(masterData.getApnoFm());// 來源受理編號
        // ]

        // 取得 BAAPPEXPAND 資料
        // [
        Baappexpand expandData = baappexpandDao.getSurvivorApplicationDataUpdateExpandDataBy(caseData.getBaappbaseId(), caseData.getApNo(), caseData.getSeqNo());

        if (expandData != null) {
            caseData.setBaappexpandId(expandData.getBaappexpandId()); // 資料編號
            caseData.setJudgeDate(expandData.getJudgeDate()); // 判決日期
            caseData.setEvTyp(expandData.getEvTyp()); // 傷病分類
            caseData.setEvCode(expandData.getEvCode()); // 傷病原因
            caseData.setCriInPart1(expandData.getCriInPart1()); // 受傷部位1
            caseData.setCriInPart2(expandData.getCriInPart2()); // 受傷部位2
            caseData.setCriInPart3(expandData.getCriInPart3()); // 受傷部位3
            caseData.setCriMedium(expandData.getCriMedium()); // 媒介物
            caseData.setCriInJnme1(expandData.getCriInJnme1()); // 國際疾病代碼1
            caseData.setCriInJnme2(expandData.getCriInJnme2()); // 國際疾病代碼2
            caseData.setCriInJnme3(expandData.getCriInJnme3()); // 國際疾病代碼3
            caseData.setCriInJnme4(expandData.getCriInJnme4()); // 國際疾病代碼4
            caseData.setPrType(expandData.getPrType()); // 先核普通
            caseData.setFamAppMk(expandData.getFamAppMk());// 符合第 63 條之 1 第 3 項註記
            caseData.setEvAppTyp(expandData.getEvAppTyp()); // 申請傷病分類
            caseData.setMonNotifyingMk(expandData.getMonNotifyingMk()); // 寄發月通知表
        }
        // ]

        // 取得 CVLDTL 資料 - 戶籍姓名
        // [
        List<Cvldtl> cvlList = cvldtlDao.selectDataBy(caseData.getEvtIdnNo(), caseData.getEvtBrDate());
        if (cvlList.size() > 0) {
            Cvldtl cvlData = cvlList.get(0);
            caseData.setName(cvlData.getName()); // 戶籍姓名
        }
        // ]

        // 是否顯示 註銷 按鍵
        // [
        Integer cancelCount = baappbaseDao.getSurvivorApplicationDataUpdateIsShowCancelButtonBy(caseData.getApNo());
        if (cancelCount != null && cancelCount.intValue() > 0) {
            caseData.setShowCancelButton(true);
        }
        // ]

        // 事故者姓名 可否修改
        // [
        Integer modifyCount = baappbaseDao.getSurvivorApplicationDataUpdateCanModifyEvtNameBy(caseData.getApNo());
        if (modifyCount != null & modifyCount.intValue() > 0) {
            caseData.setCanModifyEvtName(true);
        }
        // ]

        // 取得 BADUPEIDN 資料 - 身分證重號資料

        // [
        List<Badupeidn> dupeList = badupeidnDao.getSurvivorApplicationDataUpdateDupeIdnListBy(caseData.getApNo(), caseData.getEvtIdnNo());
        for (Badupeidn dupeData : dupeList) {
            SurvivorApplicationDataUpdateDupeIdnCase dupeCase = new SurvivorApplicationDataUpdateDupeIdnCase();
            dupeCase.setApNo(dupeData.getApNo()); // 受理編號
            dupeCase.setSeqNo(dupeData.getSeqNo()); // 序號
            dupeCase.setIdnNo(dupeData.getIdnNo()); // 身分證號
            dupeCase.setName(dupeData.getName()); // 姓名
            dupeCase.setBrDate(dupeData.getBrDate()); // 出生日期
            dupeCase.setSelMk(dupeData.getSelMk()); // 選擇註記
            dupeCase.setUpdUser(dupeData.getUpdUser()); // 異動者代號

            dupeCase.setUpdTime(dupeData.getUpdTime()); // 異動日期時間
            caseData.addDupeIdnData(dupeCase);
        }
        // ]

        // 取得 BACHKFILE 資料 - 編審註記資料
        // [
        List<Bachkfile> checkFileList = bachkfileDao.getSurvivorApplicationDataUpdateCheckFileListBy(caseData.getBaappbaseId());
        for (Bachkfile checkFileData : checkFileList) {
            SurvivorApplicationDataUpdateCheckFileCase checkFileCase = new SurvivorApplicationDataUpdateCheckFileCase();
            checkFileCase.setPayYm(checkFileData.getPayYm()); // 給付年月
            checkFileCase.setChkFileName(checkFileData.getChkFileName()); // 編審註記代號
            checkFileCase.setChkFileDesc(checkFileData.getChkFileDesc()); // 編審註記代號
            checkFileCase.setChkFileCode(checkFileData.getChkFileCode()); // 編審註記代號
            caseData.addCheckFileData(checkFileCase);
        }
        // ]

        return caseData;
    }

    /**
     * 遺屬年金案件資料更正 - 存檔
     * 
     * @param caseData
     */
    public void updateSurvivorApplicationDataUpdateData(UserBean userData, SurvivorApplicationDataUpdateCase caseData, SurvivorApplicationDataUpdateForm updateForm) {
        String dateTime = DateUtility.getNowWestDateTime(true);

        List<Baappbase> oldMasterList = new ArrayList<Baappbase>();
        List<Baappbase> masterList = new ArrayList<Baappbase>();

        List<Baappexpand> oldExpandList = new ArrayList<Baappexpand>();
        List<Baappexpand> expandList = new ArrayList<Baappexpand>();

        // update BALP0D020 PROCMK
        updateProcMkByApNo(caseData.getApNo());

        // 更新 BAAPPBASE 資料
        // [
        // 取得須更新的主檔資料列編號

        List<BigDecimal> baappbaseIdList = baappbaseDao.getSurvivorApplicationDataUpdateListForUpdateBy(caseData.getApNo());

        for (BigDecimal baappbaseId : baappbaseIdList) {
            Baappbase masterData = baappbaseDao.getSurvivorApplicationDataUpdateMasterDataByBaappbaseId(baappbaseId);

            // for MMAPLOG
            Baappbase oldMasterData = (Baappbase) BeanUtility.cloneBean(masterData);
            oldMasterList.add(oldMasterData);

            // 設定要更新的主檔資料
            masterData.setEvtNationTpe(caseData.getEvtNationTpe()); // 事故者國籍別
            masterData.setEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍
            masterData.setEvtSex(caseData.getEvtSex()); // 事故者性別
            masterData.setEvtName(caseData.getEvtName()); // 事故者姓名
            masterData.setEvtIdnNo(caseData.getEvtIdnNo()); // 事故者身分證號
            masterData.setEvtBrDate((StringUtils.length(caseData.getEvtBrDate()) == 7 || StringUtils.equals(StringUtils.substring(caseData.getEvtBrDate(), 0, 1), "-")) ? DateUtility.changeDateType(caseData.getEvtBrDate()) : caseData.getEvtBrDate()); // 事故者出生日期
            masterData.setEvtJobDate((StringUtils.length(caseData.getEvtDieDate()) == 7) ? DateUtility.changeDateType(caseData.getEvtDieDate()) : caseData.getEvtDieDate()); // 事故者離職日期 - 診斷失能日期 (遺屬年金塞死亡日期)
            masterData.setEvtDieDate((StringUtils.length(caseData.getEvtDieDate()) == 7) ? DateUtility.changeDateType(caseData.getEvtDieDate()) : caseData.getEvtDieDate()); // 事故者死亡日期
            masterData.setAppDate((StringUtils.length(caseData.getAppDate()) == 7) ? DateUtility.changeDateType(caseData.getAppDate()) : caseData.getAppDate()); // 申請日期
            masterData.setEvtAge(BaBusinessUtility.calAge((StringUtils.length(caseData.getAppDate()) == 7) ? DateUtility.changeDateType(caseData.getAppDate()) : caseData.getAppDate(),
                            (StringUtils.length(caseData.getEvtBrDate()) == 7 || StringUtils.equals(StringUtils.substring(caseData.getEvtBrDate(), 0, 1), "-")) ? DateUtility.changeDateType(caseData.getEvtBrDate()) : caseData.getEvtBrDate())); // 事故者申請時年齡
            // 事故者社福識別碼
            // [
            List<Cvldtl> cvlList = cvldtlDao.selectDataBy(caseData.getEvtIdnNo(), caseData.getEvtBrDate());
            if (cvlList.size() > 0) {
                Cvldtl cvlData = cvlList.get(0);
                masterData.setEvtIds(cvlData.getNpIds()); // 事故者社福識別碼
            }
            else {
                masterData.setEvtIds(""); // 事故者社福識別碼
            }
            // ]
            masterData.setApUbno(caseData.getApUbno()); // 申請單位保險證號
            masterData.setLsUbno(caseData.getLsUbno()); // 最後單位保險證號 - 事故發生單位保險證號
            masterData.setNotifyForm(caseData.getNotifyForm()); // 核定通知書格式
            masterData.setLoanMk(caseData.getLoanMk()); // 不須抵銷紓困貸款註記
            // 20111124 遺屬案件資料更正結案原因只更新事故者 不同步到其他遺屬
            if (StringUtils.equals("0000", masterData.getSeqNo()))
                masterData.setCloseCause(caseData.getCloseCause()); // 結案原因
            masterData.setChoiceYm((StringUtils.length(caseData.getChoiceYm()) == 5) ? DateUtility.changeChineseYearMonthType(caseData.getChoiceYm()) : caseData.getChoiceYm()); // 擇領起月
            masterData.setApItem(caseData.getApItem()); // 申請項目
            masterData.setCutAmt(caseData.getCutAmt());// 應扣失能金額
            masterData.setUpdUser(userData.getEmpNo()); // 異動者代號
            masterData.setUpdTime(dateTime); // 異動日期時間
            masterData.setInterValMonth(caseData.getInterValMonth()); // 發放方式
            masterData.setIssuNotifyingMk(caseData.getIssuNotifyingMk()); // 寄發核定通知書

            if (!StringUtils.equals(caseData.getEvtName(), updateForm.getOldEvtName()))
                masterData.setEvtAppName(caseData.getEvtName()); // 事故者申請時姓名
            if (!StringUtils.equals(caseData.getEvtIdnNo(), updateForm.getOldEvtIdnNo()))
                masterData.setEvtAppIdnNo(caseData.getEvtIdnNo()); // 事故者申請時身分證號
            if (!StringUtils.equals(updateForm.getEvtBrDate(), updateForm.getOldEvtBrDate()))
                masterData.setEvtAppBrDate(
                                (StringUtils.length(caseData.getEvtBrDate()) == 7 || StringUtils.equals(StringUtils.substring(caseData.getEvtBrDate(), 0, 1), "-")) ? DateUtility.changeDateType(caseData.getEvtBrDate()) : caseData.getEvtBrDate()); // 事故者申請時出生日期

            masterList.add(masterData);
        }

        baappbaseDao.updateSurvivorApplicationDataUpdateData(masterList);
        // ]

        // 更新 BAAPPEXPAND 資料
        // [
        // 取得須更新的給付延伸主檔資料列編號

        List<BigDecimal> baappexpandIdList = baappexpandDao.getSurvivorApplicationDataUpdateListForUpdateBy(caseData.getApNo(), caseData.getBaappbaseId());

        for (BigDecimal baappexpandId : baappexpandIdList) {
            Baappexpand expandData = baappexpandDao.getSurvivorApplicationDataUpdateExpandDataByBaappexpandId(baappexpandId);

            // for MMAPLOG
            Baappexpand oldExpandData = (Baappexpand) BeanUtility.cloneBean(expandData);
            oldExpandList.add(oldExpandData);

            // 設定要更新的給付延伸主檔資料
            expandData.setJudgeDate((StringUtils.length(caseData.getJudgeDate()) == 7) ? DateUtility.changeDateType(caseData.getJudgeDate()) : caseData.getJudgeDate()); // 判決日期
            expandData.setEvAppTyp(caseData.getEvAppTyp()); // 申請傷病分類
            expandData.setEvTyp(caseData.getEvTyp()); // 傷病分類
            expandData.setEvCode(caseData.getEvCode()); // 傷病原因
            expandData.setCriInPart1(caseData.getCriInPart1()); // 受傷部位1
            expandData.setCriInPart2(caseData.getCriInPart2()); // 受傷部位2
            expandData.setCriInPart3(caseData.getCriInPart3()); // 受傷部位3
            expandData.setCriMedium(caseData.getCriMedium()); // 媒介物
            expandData.setCriInJnme1(caseData.getCriInJnme1()); // 國際疾病代碼1
            expandData.setCriInJnme2(caseData.getCriInJnme2()); // 國際疾病代碼2
            expandData.setCriInJnme3(caseData.getCriInJnme3()); // 國際疾病代碼3
            expandData.setCriInJnme4(caseData.getCriInJnme4()); // 國際疾病代碼4
            expandData.setPrType(caseData.getPrType()); // 先核普通
            expandData.setFamAppMk(caseData.getFamAppMk());// 符合第 63 條之 1 第 3 項註記
            expandData.setMonNotifyingMk(caseData.getMonNotifyingMk()); // 寄發月通知表
            expandData.setUpdUser(userData.getEmpNo()); // 異動者代號
            expandData.setUpdTime(dateTime); // 異動日期時間
            expandList.add(expandData);
        }

        baappexpandDao.updateSurvivorApplicationDataUpdateData(expandList);
        // ]

        // 紀錄 BAAPPLOG
        if (StringUtils.equals(updateForm.getMonNotifyingMk(), "Y"))
            updateForm.setMonNotifyingMk("Y"); // 寄發月通知表
        else
            updateForm.setMonNotifyingMk("N"); // 寄發月通知表

        List<Baapplog> baapplogList = LoggingHelper.getBaapplogList(updateForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_SURVIVOR_APPLICATION_DATA_UPDATE_FIELD_RESOURCE_PREFIX);
        for (Baapplog baapplog : baapplogList) {
            baapplog.setBaappbaseId(caseData.getBaappbaseId());
            baapplog.setStatus("U");
            baapplog.setUpdTime(dateTime);
            baapplog.setUpdUser(userData.getEmpNo());
        }
        baapplogDao.insertData(baapplogList);

        // 紀錄 MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            FrameworkLogUtil.doUpdateListLog(oldMasterList, masterList);
            FrameworkLogUtil.doUpdateListLog(oldExpandList, expandList);
        }
    }

    /**
     * 遺屬年金案件資料更正 - 身分證號重號存檔
     * 
     * @param caseData
     */
    public void updateSurvivorApplicationDataUpdateDupeIdnData(UserBean userData, SurvivorApplicationDataUpdateDupeIdnCase dupeIdnCase) {
        String dateTime = DateUtility.getNowWestDateTime(true);

        List<Baappbase> oldMasterList = new ArrayList<Baappbase>();
        List<Baappbase> masterList = new ArrayList<Baappbase>();

        // 更新 BAAPPBASE 資料
        // [
        // 取得須更新的主檔資料列編號

        List<BigDecimal> baappbaseIdList = baappbaseDao.getSurvivorApplicationDataUpdateListForUpdateBy(dupeIdnCase.getApNo());

        for (BigDecimal baappbaseId : baappbaseIdList) {
            Baappbase masterData = baappbaseDao.getSurvivorApplicationDataUpdateMasterDataByBaappbaseId(baappbaseId);

            // for MMAPLOG
            Baappbase oldMasterData = (Baappbase) BeanUtility.cloneBean(masterData);
            oldMasterList.add(oldMasterData);

            // 設定要更新的主檔資料
            masterData.setEvtName(dupeIdnCase.getName()); // 事故者姓名

            masterData.setEvtIdnNo(dupeIdnCase.getIdnNoTrim()); // 事故者身分證號

            masterData.setEvtBrDate(dupeIdnCase.getBrDate()); // 事故者出生日期

            // 事故者社福識別碼
            // [
            List<Cvldtl> cvlList = cvldtlDao.selectDataBy(dupeIdnCase.getIdnNo(), dupeIdnCase.getBrDate());
            if (cvlList.size() > 0) {
                Cvldtl cvlData = cvlList.get(0);
                masterData.setEvtIds(cvlData.getNpIds()); // 事故者社福識別碼
            }
            else {
                masterData.setEvtIds(""); // 事故者社福識別碼
            }
            // ]
            masterData.setUpdUser(userData.getEmpNo()); // 異動者代號

            masterData.setUpdTime(dateTime); // 異動日期時間
            masterList.add(masterData);
        }

        baappbaseDao.updateSurvivorApplicationDataUpdateDupeIdnData(masterList);
        // ]

        // 更新 BADUPEIDN 資料
        // [
        Badupeidn dupeData = new Badupeidn();
        dupeData.setApNo(dupeIdnCase.getApNo()); // 受理編號
        dupeData.setSeqNo(dupeIdnCase.getSeqNo()); // 序號
        dupeData.setIdnNo(dupeIdnCase.getIdnNo()); // 身分證號
        dupeData.setName(dupeIdnCase.getName()); // 姓名
        dupeData.setBrDate(dupeIdnCase.getBrDate()); // 出生日期
        dupeData.setSelMk(dupeIdnCase.getSelMk()); // 選擇註記
        dupeData.setUpdUser(userData.getEmpNo()); // 異動者代號

        dupeData.setUpdTime(dateTime); // 異動日期時間

        // 重置
        badupeidnDao.updateSurvivorApplicationDataUpdateResetDupeIdnData(dupeData);

        // 更新
        badupeidnDao.updateSurvivorApplicationDataUpdateDupeIdnData(dupeData);
        // ]

        // 紀錄 MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            FrameworkLogUtil.doUpdateListLog(oldMasterList, masterList);
        }
    }

    /**
     * 遺屬年金案件資料更正 - 註銷
     * 
     * @param caseData
     */
    public void deleteSurvivorApplicationDataUpdateData(UserBean userData, String apNo) {
        // update BALP0D020 PROCMK
        updateProcMkByApNo(apNo);

        List<Baappbase> masterList = new ArrayList<Baappbase>(); // for MMAPLOG

        // 此 List 要在註銷案件前先取 for BAAPPLOG
        List<BigDecimal> baappbaseIdList = baappbaseDao.getSurvivorApplicationDataUpdateListForDeleteBy(apNo);

        // for MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            for (BigDecimal baappbaseId : baappbaseIdList) {
                Baappbase masterData = baappbaseDao.getSurvivorApplicationDataUpdateMasterDataByBaappbaseId(baappbaseId);
                masterList.add(masterData);
            }
        }

        // 註銷案件
        baappbaseDao.deleteSurvivorApplicationDataUpdateData(apNo, userData);

        String dateTime = DateUtility.getNowWestDateTime(true);

        // 紀錄 BAAPPLOG
        List<Baapplog> baapplogList = new ArrayList<Baapplog>();
        for (BigDecimal baappbaseId : baappbaseIdList) {
            Baapplog baapplog = new Baapplog();
            baapplog.setBaappbaseId(baappbaseId);
            baapplog.setStatus("D");
            baapplog.setUpdTime(dateTime);
            baapplog.setUpdUser(userData.getEmpNo());

            baapplogList.add(baapplog);
        }
        baapplogDao.insertData(baapplogList);

        // 紀錄 MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            FrameworkLogUtil.doDeleteListLog(masterList);
        }
    }

    /**
     * 檢核所輸入的 傷病原因 (<code>EVCODE</code>) 是否合法
     * 
     * @param evTyp 傷病分類
     * @param evCode 傷病原因
     * @return
     */
    public boolean isValidEvCode(String evTyp, String evCode) {
        if (StringUtils.isBlank(evTyp) || StringUtils.isBlank(evCode))
            return true;

        if (StringUtils.equals(evTyp, "1") || StringUtils.equals(evTyp, "3")) {
            List<Bbcmf06> list = bbcmf06Dao.selectListBy("1", "11", evCode);
            if (list != null && list.size() > 0)
                return true;
            else
                return false;
        }
        else if (StringUtils.equals(evTyp, "2") || StringUtils.equals(evTyp, "4")) {
            List<Bbcmf06> list = bbcmf06Dao.selectListBy("1", "12", evCode);
            if (list != null && list.size() > 0)
                return true;
            else
                return false;
        }

        return true;
    }

    /**
     * 檢核所輸入的 傷病原因 (<code>EVCODE</code>) 是否合法
     * 
     * @param evAppTyp 傷病分類
     * @param evCode 傷病原因
     * @return
     */
    public boolean isValidEvCode2(String evAppTyp, String evCode) {
        if (StringUtils.isBlank(evAppTyp) || StringUtils.isBlank(evCode))
            return true;

        if (StringUtils.equals(evAppTyp, "1") || StringUtils.equals(evAppTyp, "3")) {
            List<Bbcmf06> list = bbcmf06Dao.selectListBy("1", "11", evCode);
            if (list != null && list.size() > 0)
                return true;
            else
                return false;
        }
        else if (StringUtils.equals(evAppTyp, "2") || StringUtils.equals(evAppTyp, "4")) {
            List<Bbcmf06> list = bbcmf06Dao.selectListBy("1", "12", evCode);
            if (list != null && list.size() > 0)
                return true;
            else
                return false;
        }

        return true;
    }

    /**
     * 檢核所輸入的 受傷部位 (<code>CRIINPART</code>) 是否合法
     * 
     * @param criInPart 受傷部位
     * @return
     */
    public boolean isValidCriInPart(String criInPart) {
        if (StringUtils.isBlank(criInPart))
            return true;

        List<Bbcmf06> list = bbcmf06Dao.selectListBy("1", "13", criInPart);
        if (list != null && list.size() > 0)
            return true;
        else
            return false;
    }

    /**
     * 檢核所輸入的 媒介物 (<code>CRIMEDIUM</code>) 是否合法
     * 
     * @param criMedium 媒介物
     * @return
     */
    public boolean isValidCriMedium(String criMedium) {
        if (StringUtils.isBlank(criMedium))
            return true;

        List<Bbcmf06> list = bbcmf06Dao.selectListBy("1", "14", criMedium);
        if (list != null && list.size() > 0)
            return true;
        else
            return false;
    }

    /**
     * 檢核所輸入的 失能項目 (<code>CRIINJDP</code>) 是否合法
     * 
     * @param criInJdp 失能項目
     * @return
     */
    public boolean isValidCriInJdp(String criInJdp) {
        if (StringUtils.isBlank(criInJdp))
            return true;

        List<Bbcmf06> list = bbcmf06Dao.selectCriInJdpListBy(criInJdp);
        if (list != null && list.size() > 0)
            return true;
        else
            return false;
    }

    /**
     * 檢核所輸入的 國際疾病代碼 (<code>CRIINJNME</code>) 是否合法
     * 
     * @param criInJnme 國際疾病代碼
     * @return
     */
    public boolean isValidCriInJnme(String criInJnme) {
        if (StringUtils.isBlank(criInJnme))
            return true;

        Bacriinj data = bacriinjDao.selectDataBy(criInJnme);
        if (data != null)
            return true;
        else
            return false;
    }

    /**
     * 依傳入條件取得 改匯資料檔 (<code>BAREGIVEDTL</code>) 資料 for 給付資料更正
     * 
     * @param apNo 受理編號
     * @param accSeqNo 受款人序號
     * @return <code>Baregivedtl</code> 物件
     */
    public Baregivedtl getBaregivedtlPayDataForPaymentDataUpdate(String apNo, String accSeqNo) {
        List<Baregivedtl> list = baregivedtlDao.selectPayDataForPaymentDataUpdate(apNo, accSeqNo);
        Baregivedtl obj = null;
        if (list.size() != 0) {
            obj = list.get(0);
        }
        return obj;
    }

    /**
     * 排除遺屬年金事故者資料 for 通訊/給付資料更正
     * 
     * @param dataList
     * @return 內含<code>BaappbaseDataUpdateCase</code> 物件的List
     */
    public List<BaappbaseDataUpdateCase> excludeEvtDataForSurvivor(List<BaappbaseDataUpdateCase> dataList) {
        BaappbaseDataUpdateCase obj = (BaappbaseDataUpdateCase) dataList.get(0);

        if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S, obj.getPagePayKind())) {
            for (int i = 0; i < dataList.size(); i++) {
                BaappbaseDataUpdateCase caseObj = (BaappbaseDataUpdateCase) dataList.get(i);
                if (StringUtils.equals("0000", caseObj.getSeqNo())) {
                    dataList.remove(i);
                }
            }
        }
        return dataList;
    }

    /**
     * 將具名領取ID List轉為Map
     * 
     * @param payTypeList
     * @return
     */
    public Map<BigDecimal, BigDecimal> transListToMapForPayeeDataUpdate(List<BigDecimal> payTypeList) {
        Map<BigDecimal, BigDecimal> payTypeMap = new HashMap<BigDecimal, BigDecimal>();
        for (int i = 0; i < payTypeList.size(); i++) {
            payTypeMap.put(payTypeList.get(i), payTypeList.get(i));
        }
        return payTypeMap;
    }

    public boolean isCleanCoreceivePaymentData(BaappbaseDataUpdateCase caseObj, BaappbaseDataUpdateCase modifyData) {
        boolean isClean = false;
        if (!StringUtils.equals(caseObj.getSeqNo(), modifyData.getSeqNo()) && StringUtils.equals(caseObj.getAccSeqNo(), modifyData.getSeqNo()) && StringUtils.equals(caseObj.getAccRel(), "3")
                        && (StringUtils.equals(caseObj.getProcStat(), ConstantKey.BAAPPBASE_PROCSTAT_10) || StringUtils.equals(caseObj.getProcStat(), ConstantKey.BAAPPBASE_PROCSTAT_11)
                                        || StringUtils.equals(caseObj.getProcStat(), ConstantKey.BAAPPBASE_PROCSTAT_12) || StringUtils.equals(caseObj.getProcStat(), ConstantKey.BAAPPBASE_PROCSTAT_20))) {
            isClean = true;
        }

        return isClean;
    }

    public boolean isCleanCoreceivePaymentDataForSurvivor(BaappbaseDataUpdateCase caseObj, SurvivorPayeeDataUpdateCase modifyData) {
        boolean isClean = false;
        if (!StringUtils.equals(caseObj.getSeqNo(), modifyData.getSeqNo()) && StringUtils.equals(caseObj.getAccSeqNo(), modifyData.getSeqNo()) && StringUtils.equals(caseObj.getAccRel(), "3")
                        && (StringUtils.equals(caseObj.getProcStat(), ConstantKey.BAAPPBASE_PROCSTAT_10) || StringUtils.equals(caseObj.getProcStat(), ConstantKey.BAAPPBASE_PROCSTAT_11)
                                        || StringUtils.equals(caseObj.getProcStat(), ConstantKey.BAAPPBASE_PROCSTAT_12) || StringUtils.equals(caseObj.getProcStat(), ConstantKey.BAAPPBASE_PROCSTAT_20))) {
            isClean = true;
        }

        return isClean;
    }

    /**
     * 比較日期改前改後值，如果有變動，回傳非空值or較小的那個 如果日期都一樣，回傳null
     * 
     * @param dateA
     * @param dateB
     * @return
     */
    public String compareTwoDate(String dateA, String dateB) {
        if (StringUtils.isNotBlank(dateA) && StringUtils.length(dateA) == 7) {
            dateA = DateUtility.changeDateType(dateA);
        }
        if (StringUtils.isNotBlank(dateB) && StringUtils.length(dateB) == 7) {
            dateB = DateUtility.changeDateType(dateB);
        }

        // 開始比對
        if (StringUtils.equals(dateA, dateB)) {
            return null;
        }
        else if (StringUtils.isNotBlank(dateA) && StringUtils.isBlank(dateB)) {
            return dateA;
        }
        else if (StringUtils.isBlank(dateA) && StringUtils.isNotBlank(dateB)) {
            return dateB;
        }
        else {
            if (Integer.parseInt(dateA) <= Integer.parseInt(dateB)) {
                return dateA;
            }
            else {
                return dateB;
            }
        }
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 案件資料更正"發放方式"可否修改狀態
     * 
     * @param apNo 受理編號
     * @return <code>String</code> 物件
     */
    public String chkInterValMonthStatus(String apNo) {
        Integer result1 = baappbaseDao.chkInterValMonthStatus(apNo, "IN");
        // Integer result2 = baappbaseDao.chkInterValMonthStatus(apNo, "NOT_IN");
        String status = "N";
        if (result1 > 0) {
            status = "Y";
        }
        // if (result1 > 0 && result2 <= 0) {
        // status = "Y";
        // }
        return status;
    }

    /**
     * 依傳入條件取得 給付主檔 for 受款人資料更正作業
     * 
     * @param apNo
     * @param seqNo
     * @return
     */
    public String getInterValMonth(String apNo, String seqNo) {
        return baappbaseDao.getInterValMonth(apNo, seqNo);
    }

    /**
     * 依傳入條件取得 遺屬強迫不合格記錄檔 (<code>BACOMPELNOPAY</code>)
     */
    public List<SurvivorPayeeDataUpdateCompelDataCase> getCompelDataList(String apNo, String seqNo) {
        List<Bacompelnopay> list = bacompelnopayDao.selectDataBy(apNo, seqNo);
        List<SurvivorPayeeDataUpdateCompelDataCase> returnList = new ArrayList<SurvivorPayeeDataUpdateCompelDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Bacompelnopay obj = list.get(i);
            SurvivorPayeeDataUpdateCompelDataCase caseObj = new SurvivorPayeeDataUpdateCompelDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 案件類別為 2 或 4 時，呼叫 StoreProcedure Name:sp_BA_Get_CIPB(眷屬身份證號,事故日期【NULL】)
     * 
     * @param famIdnNo 眷屬身分證號
     * @param evtJobDate 事故日期
     */
    public void doGetCipb(String apNp, String seqNo, String appDate, String famIdnNo, String evtJobDate) {
        log.debug("UpdateService start doGetCipb... " + apNp + ";" + seqNo + ";" + appDate + ";" + famIdnNo + ";" + evtJobDate);
        // 呼叫StoreProcedure sp_BA_Get_CIPB(famIdnNo, evtJobDate)
        cipbDao.doGetCipb(apNp, seqNo, appDate, famIdnNo, evtJobDate);
        log.debug("UpdateService finish doGetCipb... " + apNp + ";" + seqNo + ";" + appDate + ";" + famIdnNo + ";" + evtJobDate);
    }

    // OldAge Death Repay being
    /**
     * 依傳入條件取得 給付主檔 資料清單 (檢核受款人資料是否符合可改匯處理的資格)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public List<BaappbaseDataUpdateCase> getCheckOldAgeDeathRepayDataBy(String apNo, String seqNo) {
        List<Baappbase> list = baappbaseDao.selectCheckOldAgeDeathRepayDataBy(apNo, seqNo);
        List<BaappbaseDataUpdateCase> returnList = new ArrayList<BaappbaseDataUpdateCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            BaappbaseDataUpdateCase caseObj = new BaappbaseDataUpdateCase();
            BeanUtility.copyProperties(caseObj, baappbase);

            // 給付別(受理編號第一碼)
            if (StringUtils.isNotBlank(caseObj.getApNo())) {
                caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
            }
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔 資料清單 (檢核有無符合資料，有無退匯資料)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public List<BaappbaseDataUpdateCase> getOldAgeDeathRepayDataBy(String apNo, String seqNo, String heirSeqNo) {
        List<Baappbase> list = baappbaseDao.selectOldAgeDeathRepayDataBy(apNo, seqNo, heirSeqNo);
        List<BaappbaseDataUpdateCase> returnList = new ArrayList<BaappbaseDataUpdateCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            // 預先專換西元日期=>民國日期 begin
            baappbase.setOriIssuYm(DateUtility.changeWestYearMonthType(baappbase.getOriIssuYm()));
            baappbase.setPayYm(DateUtility.changeWestYearMonthType(baappbase.getPayYm()));
            baappbase.setBrChkDate(DateUtility.changeDateType(baappbase.getBrChkDate()));
            // 預先專換西元日期=>民國日期 end
            BaappbaseDataUpdateCase caseObj = new BaappbaseDataUpdateCase();
            BeanUtility.copyProperties(caseObj, baappbase);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔 資料清單 (檢核受款人的繼承人資料是否存在)
     * 
     * @param apNo 受理編號
     * @param heirSeqNo 序號
     * @return
     */
    public List<BaappbaseDataUpdateCase> getHeirSeqNoExistDataBy(String apNo, String heirSeqNo) {
        List<Baappbase> list = baappbaseDao.selectHeirSeqNoExistDataBy(apNo, heirSeqNo);
        List<BaappbaseDataUpdateCase> returnList = new ArrayList<BaappbaseDataUpdateCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            BaappbaseDataUpdateCase caseObj = new BaappbaseDataUpdateCase();
            BeanUtility.copyProperties(caseObj, baappbase);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 依傳入條件取得 給付主檔 資料清單 (退匯金額分配資料)
     * 
     * @param apNo 受理編號
     * @param heirSeqNo 序號
     * @return
     */
    public List<BaappbaseDataUpdateCase> getRepayIssueAmtDataBy(String apNo, String heirSeqNo) {
        List<Baappbase> list = baappbaseDao.selectRepayIssueAmtDataBy(apNo, heirSeqNo);
        List<BaappbaseDataUpdateCase> returnList = new ArrayList<BaappbaseDataUpdateCase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            BaappbaseDataUpdateCase caseObj = new BaappbaseDataUpdateCase();
            BeanUtility.copyProperties(caseObj, baappbase);
            returnList.add(caseObj);
        }
        return returnList;
    }

    /**
     * 退匯金額分配資料 for 給付核定檔(<code>BADAPR</code>)
     * 
     * @param badapr 給付核定檔
     */
    public void updateRepayIssueAmtDataForDeathRepay(String apNo, String seqNo, String oriIssuYm, String payYm, String issuKind) {

        // 更新資料到 給付核定檔
        // [
        log.debug("Start Update BADAPR ...");
        badaprDao.updateRepayIssueAmtDataForDeathRepay(apNo, seqNo, oriIssuYm, payYm, issuKind);
        log.debug("Update BADAPR Finished ...");
        // ]
    }

    /**
     * 刪除核定檔(BADAPR)的資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     */
    public void deleteRepayIssueAmtDataForDeathRepay(UserBean userData, BaappbaseDataUpdateCase babasicupdateData) {
        // 刪除 核定檔的資料
        // [
        log.debug("Start to Delete BADAPR ...");

        String apNo = babasicupdateData.getApNo();
        String seqNo = babasicupdateData.getSeqNo();
        // 將民國年轉為西元
        String oriIssuYm = DateUtility.changeChineseYearMonthType(babasicupdateData.getOriIssuYm());
        String payYm = DateUtility.changeChineseYearMonthType(babasicupdateData.getPayYm());
        // 取得改前值物件

        // 刪除 給付主檔
        badaprDao.deleteRepayIssueAmtDataForDeathRepay(apNo, seqNo, oriIssuYm, payYm, babasicupdateData.getIssuKind());

        // // delect MMAPLOG
        // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
        // // 紀錄 Log
        // FrameworkLogUtil.doDeleteLog(beforBabasicamt);
        // }
        log.debug("Delete BADAPR Finished ...");
        // ]
    }

    /**
     * 新增繼承人資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void insertDataForDeathRepay(BaappbaseDataUpdateCase CaseData) {

        log.debug("Start to Insert BADAPR ...");
        Badapr insertData = new Badapr();
        BeanUtility.copyProperties(insertData, CaseData);

        badaprDao.insertDataForDeathRepay(insertData);

        log.debug("Insert BADAPR Finished ...");
        // Insert MMAPLOG

        // if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
        // // 紀錄 Log
        // FrameworkLogUtil.doInsertLog(baappbase);
        // }
    }

    /**
     * 退匯金額分配資料 for 給付核定檔(<code>BADAPR</code>)
     * 
     * @param badapr 給付核定檔
     */
    public void updateOldAgeDeathRepayDataBy(BigDecimal remitAmt, String brChkDate, String apNo, String seqNo, String oriIssuYm, String payYm, String issuKind) {

        // 更新資料到 給付核定檔
        // [
        log.debug("Start Update BADAPR ...");
        badaprDao.updateOldAgeDeathRepayDataForDeathRepay(remitAmt, brChkDate, apNo, seqNo, oriIssuYm, payYm, issuKind);
        log.debug("Update BADAPR Finished ...");
        // ]

    }

    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 退匯金額分配資料
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacDeathRepayForQryData_2By(String apNo, String seqNo, String oriIssuYm, String payYm, String issuKind) {
        // 更新資料到 退匯檔
        // [
        log.debug("Start Update BAPFLBAC ...");
        bapflbacDao.updateBapflbacDeathRepayForQryData_2By(apNo, seqNo, oriIssuYm, payYm, issuKind);
        log.debug("Update BAPFLBAC Finished ...");
        // ]
    }

    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 退匯金額分配資料
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacDeathRepayForQryData_4By(String apNo, String seqNo, String issuYm, String payYm, String issuKind) {
        // 更新資料到 退匯檔
        // [
        log.debug("Start Update BAPFLBAC ...");
        bapflbacDao.updateBapflbacDeathRepayForQryData_2By(apNo, seqNo, issuYm, payYm, issuKind);
        log.debug("Update BAPFLBAC Finished ...");
        // ]
    }

    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 退匯金額分配資料
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlDeathRepayForQryData_2By(String apNo, String seqNo, String oriIssuYm, String payYm, String procUser, String procDeptId, String procIp, String issuKind) {
        // 更新資料到 改匯檔
        // [
        log.debug("Start Update BAREGIVEDTL ...");
        baregivedtlDao.updateBaregivedtlDeathRepayForQryData_2By(apNo, seqNo, oriIssuYm, payYm, procUser, procDeptId, procIp, issuKind);
        log.debug("Update BAREGIVEDTL Finished ...");
        // ]
    }

    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 退匯金額分配資料
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlDeathRepayForQryData_4By(String apNo, String seqNo, String issuYm, String payYm, String procUser, String procDeptId, String procIp, String issuKind) {
        // 更新資料到 改匯檔
        // [
        log.debug("Start Update BAREGIVEDTL ...");
        baregivedtlDao.updateBaregivedtlDeathRepayForQryData_4By(apNo, seqNo, issuYm, payYm, procUser, procDeptId, procIp, issuKind);
        log.debug("Update BAREGIVEDTL Finished ...");
        // ]
    }

    /**
     * 更新出納系統的退改匯資料(呼叫PROCEDURE)
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @param oriIssuYm 原核定年月
     * @param payYm 給付年月
     * @param afPayDate 改匯初付日期
     * @return
     */
    public String doExpDeathRepayRec(String apNo, String seqNo, String oriIssuYm, String payYm, String afPayDate, String issuKind) {
        // procMsg 為procMsgCode+procMsg
        String procMsg = bapflbacDao.doExpDeathRepayRec(apNo, seqNo, oriIssuYm, payYm, afPayDate, issuKind);
        log.info(procMsg);

        return procMsg;
    }

    /**
     * 更新出納系統的退改匯資料(呼叫PROCEDURE)
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @param oriIssuYm 原核定年月
     * @param payYm 給付年月
     * @param brChkDate 改匯初付日期
     * @return
     */
    public String doDeathRepayRefundment(String apNo, String seqNo, String oriIssuYm, String payYm, String brChkDate, String issuKind) {
        // procMsg 為procMsgCode+procMsg
        String procMsg = bapflbacDao.doDeathRepayRefundment(apNo, seqNo, oriIssuYm, payYm, brChkDate, issuKind);
        log.info(procMsg);

        return procMsg;
    }

    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param ISSUYM(原核定年月)
     * @param PAYYM(給付年月)
     * @param PAYDATE
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM,PAYYM,PAYDATE")
    public String doExpSingleRec(String apNo, String seqNo, String issuYm, String payYm, String payDate) {
        // procMsg 為procMsgCode+procMsg
        String procMsg = bapflbacDao.doExpSingleRec(apNo, seqNo, issuYm, payYm, payDate);
        log.info(procMsg);

        return procMsg;
    }

    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param oriIssuYm 原核定年月
     * @param PAYYM(給付年月)
     * @param BRCHKDATE
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ORIISSUYM,PAYYM,BRCHKDATE")
    public String doExpCancelSingleRec(String apNo, String seqNo, String oriIssuYm, String payYm, String brChkDate) {
        // procMsg 為procMsgCode+procMsg
        String procMsg = bapflbacDao.doExpCancelSingleRec(apNo, seqNo, oriIssuYm, payYm, brChkDate);
        log.info(procMsg);

        return procMsg;
    }

    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param ACPDATE 收回日期
     * @return
     */
    public String doRtnCashRpt(String apNo, String seqNo, String acpDate) {
        // procMsg 為procMsgCode+procMsg
        String procMsg = bapflbacDao.doRtnCashRpt(apNo, seqNo, acpDate);
        log.info(procMsg);

        return procMsg;
    }

    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param ACPDATE 收回日期
     * @return
     */
    public String doRtnRefundmentRpt(String apNo, String seqNo, String acpDate) {
        // procMsg 為procMsgCode+procMsg
        String procMsg = bapflbacDao.doRtnRefundmentRpt(apNo, seqNo, acpDate);
        log.info(procMsg);

        return procMsg;
    }

    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param CANCELDATE(註銷日期)
     * @return
     */
    public String doCancelRtnCashRpt(String apNo, String seqNo, String cancelDate) {
        // procMsg 為procMsgCode+procMsg
        String procMsg = bapflbacDao.doCancelRtnCashRpt(apNo, seqNo, cancelDate);
        log.info(procMsg);

        return procMsg;
    }

    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param CANCELDATE(註銷日期)
     * @return
     */
    public String doCancelRtnRefundmentRpt(String apNo, String seqNo, String cancelDate) {
        // procMsg 為procMsgCode+procMsg
        String procMsg = bapflbacDao.doCancelRtnRefundmentRpt(apNo, seqNo, cancelDate);
        log.info(procMsg);

        return procMsg;
    }

    /**
     * 新增繼承人資料 for 退匯檔(<code>BAPFLBAC</code>)
     * 
     * @param Bapflbac 給付主檔
     */
    public void insertBapflbacDataForDeathRepayBy(BaappbaseDataUpdateCase CaseData) {

        log.debug("Start to Insert BAPFLBAC ...");
        Bapflbac insertData = new Bapflbac();
        BeanUtility.copyProperties(insertData, CaseData);

        bapflbacDao.insertBapflbacDataForDeathRepayBy(insertData);

        log.debug("Insert BAPFLBAC Finished ...");
        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(insertData);
        }
    }

    /**
     * 新增繼承人資料 for 退匯檔(<code>BAPFLBAC</code>)
     * 
     * @param Bapflbac 給付主檔
     */
    public void insertBapflbacDataForDeathRepay2By(BaappbaseDataUpdateCase CaseData) {

        log.debug("Start to Insert BAPFLBAC ...");
        Bapflbac insertData = new Bapflbac();
        BeanUtility.copyProperties(insertData, CaseData);

        bapflbacDao.insertBapflbacDataForDeathRepay2By(insertData);

        log.debug("Insert BAPFLBAC Finished ...");
        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(insertData);
        }
    }

    /**
     * 取得匯款匯費 for 受款人死亡改匯處理作業
     * 
     * @pramati paramCode
     */

    public String getParamNameForDeathRepayData(String paramCode) {
        return baparamDao.getParamNameForDeathRepayData(paramCode);
    }

    /**
     * 取得每月取得分配人數 for 受款人死亡改匯處理作業
     * 
     * @param apNo 受理編號
     * @param heirSeqNo 繼承人序
     */

    public String selectRepayIssueAmtDataCountBy(String apNo, String heirSeqNo) {
        return baappbaseDao.selectRepayIssueAmtDataCountBy(apNo, heirSeqNo);
    }

    /**
     * 取得 改匯黨資料(<code>BAREGIVEDTL</code>) For 無勾選時確認是否該筆資料當天有無改匯或取消紀錄
     * 
     * @param Baregivedtl 改匯檔
     */

    public String selectCheckAfChkDateForCheckBox(String apNo, String seqNo, String oriIssuYm, String payYm) {
        return baregivedtlDao.selectCheckAfChkDateForCheckBox(apNo, seqNo, oriIssuYm, payYm);
    }

    /**
     * 【改匯註銷】按鈕的處理動作 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付核定檔
     */
    public void updateForOldAgeDeathRepayDoCancel(Baappbase baappbase) {

        // 更新資料到 給付核定檔
        // [
        log.debug("Start Update BAAPPBASE ...");
        baappbaseDao.updateForOldAgeDeathRepayDoCancel(baappbase);
        log.debug("Update BAAPPBASE Finished ...");
        // ]

    }

    /**
     * 檢核完成，開始進行死亡改匯存檔處理作業 for 受款人死亡改匯處理作業
     * 
     * @param
     * @throws Exception
     */
    public void doOldAgeDeathRepayOperation(UserBean userData, int[] checkIndex, List<BaappbaseDataUpdateCase> deathRepayDataCaseList, BaappbaseDataUpdateCase babasicupdateData, List<BaappbaseDataUpdateCase> oldAgeDeathRepayDataCaseList,
                    BaappbaseDataUpdateCase checkOldAgeDeathRepayData, List<BaappbaseDataUpdateCase> heirSeqNoExistDataCaseList, List<BaappbaseDataUpdateCase> repayIssueAmtDataCaseListOld) throws Exception {

        // 更新核定檔(BADAPR)受款人的資料 將REMITMK改回5
        for (BaappbaseDataUpdateCase deathRepayData : deathRepayDataCaseList) {
            updateRepayIssueAmtDataForDeathRepay(babasicupdateData.getApNo(), babasicupdateData.getSeqNo(), DateUtility.changeChineseYearMonthType(deathRepayData.getOriIssuYm()), DateUtility.changeChineseYearMonthType(deathRepayData.getPayYm()), deathRepayData.getIssuKind());
        }
        // 刪除核定檔(BADAPR)的資料：需依照(QryData_2)的資料筆數，逐筆刪除。即(QryData_2)有2筆資料時，需刪除2筆資料所對應的資料，以此類推。
        for (BaappbaseDataUpdateCase deathRepayData : deathRepayDataCaseList) {
            babasicupdateData.setOriIssuYm(deathRepayData.getOriIssuYm());
            babasicupdateData.setPayYm(deathRepayData.getPayYm());
            babasicupdateData.setIssuKind(deathRepayData.getIssuKind());
            // 刪除BADAPR REMITMK為2的所有資料
            deleteRepayIssueAmtDataForDeathRepay(userData, babasicupdateData);
        }

        // {繼承人資料}無勾選任何資料時，僅需要修改核定檔(BADAPR)的資料，需依照(QryData_2)的資料筆數，逐筆更新。
        if (checkIndex == null) {
            for (BaappbaseDataUpdateCase deathRepayData : deathRepayDataCaseList) {
                // 新增BADAPR 因無勾選，取消分配，新增原受款人資料
                updateOldAgeDeathRepayDataBy(deathRepayData.getRemitAmt(), DateUtility.changeDateType(deathRepayData.getBrChkDate()), babasicupdateData.getApNo(), babasicupdateData.getSeqNo(),
                                DateUtility.changeChineseYearMonthType(deathRepayData.getOriIssuYm()), DateUtility.changeChineseYearMonthType(deathRepayData.getPayYm()), deathRepayData.getIssuKind());
            }
            // {繼承人資料}有勾選資料時，則新增核定檔(BADAPR)的資料：需依照勾選的資料筆數，逐筆計算金額及新增資料
            // 以勾選的繼承人資料，逐筆新增對應的核定檔資料，(有勾選且Qry_data4無資料)
        }
        else if (checkIndex != null) {
            for (BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList) {

                BigDecimal checkIndexSize = new BigDecimal(checkIndex.length);
                BigDecimal remitAmt = oldAgeDeathRepayData.getRemitAmt();
                List<BigDecimal> avgNumList = getAvgNum(checkIndexSize, remitAmt);
                BaappbaseDataUpdateCase insertDataCase = new BaappbaseDataUpdateCase();
                // 加入insert資料 Insert BADAPR
                insertDataCase.setApNo(checkOldAgeDeathRepayData.getApNo());
                insertDataCase.setSeqNo(checkOldAgeDeathRepayData.getSeqNo());
                insertDataCase.setOriIssuYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm()));
                insertDataCase.setPayYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm()));
                insertDataCase.setPayKind(oldAgeDeathRepayData.getIssuKind());
                for (int i = 0; i <= checkIndex.length - 1; i++) {
                    int Index = checkIndex[i];
                    insertDataCase.setHeirSeqNo(heirSeqNoExistDataCaseList.get(Index).getSeqNo());
                    insertDataCase.setAvgNum(avgNumList.get(i));
                    // INSERT勾選分配後資料進入BADAPR
                    insertDataForDeathRepay(insertDataCase);
                }
            }
        }

        // 當{退匯金額分配資料}(QryData_4)無任何資料(資料筆數<=0)時，更新死亡退匯的受款人資料，將其資料狀態更新為給付收回。需依照(QryData_2)的資料筆數，逐筆更新。
        if (repayIssueAmtDataCaseListOld.size() <= 0) {
            for (BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList) {
                // 更新退匯及改匯檔
                String apNo = checkOldAgeDeathRepayData.getApNo();
                String seqNo = checkOldAgeDeathRepayData.getSeqNo();
                String oriIssuYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm());
                String payYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm());
                String procUser = userData.getEmpNo();
                String procDeptId = userData.getDeptId();
                String procIp = userData.getLoginIP();
                // 將BRMK、AFMK改為 2、4 原為 3、3
                updateBapflbacDeathRepayForQryData_2By(apNo, seqNo, oriIssuYm, payYm, oldAgeDeathRepayData.getIssuKind());
                // 將MK、AFMK改為 4、4 原為 1、1 call SP後會回寫分配後資料 新資料為 1、1
                updateBaregivedtlDeathRepayForQryData_2By(apNo, seqNo, oriIssuYm, payYm, procUser, procDeptId, procIp, oldAgeDeathRepayData.getIssuKind());
                // 呼叫PROCEDURE 將出納table PFLBAC 的資料 MK 改為 4 原資料為1，新增 出納table PFLBACEVENT 將出納受款人資料 MK改為4 原為1，因為已分配金額 故MK改為4
                String procMsg = doExpDeathRepayRec(apNo, seqNo, oriIssuYm, payYm, DateUtility.getNowWestDate(), oldAgeDeathRepayData.getIssuKind());
                if (!"0".equals(procMsg.substring(0, 1))) {
                    throw new Exception("V_O_PROCMSGCODE)<>0，處理作業失敗，ROLLBACK。procMsg:" + procMsg);
                }
            }
        }
        // 當{退匯金額分配資料}(QryData_4)有資料(資料筆數 > 0)時
        if (repayIssueAmtDataCaseListOld.size() > 0) {
            for (BaappbaseDataUpdateCase repayIssueAmtData : repayIssueAmtDataCaseListOld) {
                // 更新退匯及改會檔
                String apNo = checkOldAgeDeathRepayData.getApNo();
                String seqNo = repayIssueAmtData.getSeqNo();
                String issuYm = DateUtility.changeChineseYearMonthType(repayIssueAmtData.getIssuYm());
                String payYm = DateUtility.changeChineseYearMonthType(repayIssueAmtData.getPayYm());
                String procUser = userData.getEmpNo();
                String procDeptId = userData.getDeptId();
                String procIp = userData.getLoginIP();
                // 將BRMK、AFMK改為 2、4 原為 3、3
                updateBapflbacDeathRepayForQryData_4By(apNo, seqNo, issuYm, payYm, repayIssueAmtData.getIssuKind());
                // 呼叫PROCEDURE 將出納table PFLBAC 的資料 MK 改為 4 原資料為1，新增 出納table PFLBACEVENT 將出納受款人資料 MK改為4 原為1，因為已分配金額 故MK改為4
                updateBaregivedtlDeathRepayForQryData_4By(apNo, seqNo, issuYm, payYm, procUser, procDeptId, procIp, repayIssueAmtData.getIssuKind());
                // 呼叫PROCEDURE
                String procMsg = doExpDeathRepayRec(checkOldAgeDeathRepayData.getApNo(), repayIssueAmtData.getSeqNo(), repayIssueAmtData.getIssuYm(), repayIssueAmtData.getPayYm(), DateUtility.getNowWestDate(), repayIssueAmtData.getIssuKind());
                if (!"0".equals(procMsg.substring(0, 1))) {
                    throw new Exception("V_O_PROCMSGCODE)<>0，處理作業失敗，ROLLBACK。procMsg:" + procMsg);
                }
            }
        }

        // 當{退匯金額分配資料}(QryData_4)有資料(資料筆數 > 0) 無勾選時
        if (repayIssueAmtDataCaseListOld.size() > 0 && checkIndex == null) {
            BaappbaseDataUpdateCase insertBapflbacDataCase = new BaappbaseDataUpdateCase();
            for (BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList) {
                insertBapflbacDataCase.setApNo(checkOldAgeDeathRepayData.getApNo());
                insertBapflbacDataCase.setSeqNo(checkOldAgeDeathRepayData.getSeqNo());
                insertBapflbacDataCase.setOriIssuYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm()));
                insertBapflbacDataCase.setPayYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm()));
                insertBapflbacDataCase.setRemitAmt(oldAgeDeathRepayData.getRemitAmt());
                insertBapflbacDataCase.setProcUser(userData.getEmpNo());
                insertBapflbacDataCase.setProcIp(userData.getLoginIP());
                insertBapflbacDataCase.setProcDeptId(userData.getDeptId());
                insertBapflbacDataCase.setIssuKind(oldAgeDeathRepayData.getIssuKind());
                // 新增原受款人資料回Bapflbac
                insertBapflbacDataForDeathRepayBy(insertBapflbacDataCase);
            }
        }

        // 當{退匯金額分配資料}(QryData_4)無資料(資料筆數 > 0) 有勾選時
        if (checkIndex != null) {
            // 以勾選的繼承人資料，逐筆新增對應的核定檔資料，(有勾選且Qry_data4無資料)
            for (BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList) {

                BigDecimal checkIndexSize = new BigDecimal(checkIndex.length);
                BigDecimal remitAmt = oldAgeDeathRepayData.getRemitAmt();
                List<BigDecimal> avgNumList = getAvgNum(checkIndexSize, remitAmt);
                BaappbaseDataUpdateCase insertBapflbacDataCase = new BaappbaseDataUpdateCase();
                // 加入insert資料
                insertBapflbacDataCase.setApNo(checkOldAgeDeathRepayData.getApNo());
                insertBapflbacDataCase.setSeqNo(checkOldAgeDeathRepayData.getSeqNo());
                insertBapflbacDataCase.setOriIssuYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm()));
                insertBapflbacDataCase.setPayYm(DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm()));
                insertBapflbacDataCase.setProcUser(userData.getEmpNo());
                insertBapflbacDataCase.setProcIp(userData.getLoginIP());
                insertBapflbacDataCase.setProcDeptId(userData.getDeptId());
                insertBapflbacDataCase.setIssuKind(oldAgeDeathRepayData.getIssuKind());
                for (int i = 0; i <= checkIndex.length - 1; i++) {
                    int Index = checkIndex[i];
                    String heirSeqNo = heirSeqNoExistDataCaseList.get(Index).getSeqNo();
                    insertBapflbacDataCase.setHeirSeqNo(heirSeqNo);
                    insertBapflbacDataCase.setAvgNum(avgNumList.get(i));
                    // 新增分配金額資料進入Bapflbac CALL第二個SP回寫分配資料時，會抓取此資料
                    insertBapflbacDataForDeathRepay2By(insertBapflbacDataCase);
                }
            }
        }

        if (checkIndex == null) {
            for (BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList) {
                String apNo = checkOldAgeDeathRepayData.getApNo();
                String seqNo = checkOldAgeDeathRepayData.getSeqNo();
                String oriIssuYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm());
                String payYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm());
                String procMsg = doDeathRepayRefundment(apNo, seqNo, oriIssuYm, payYm, DateUtility.getNowWestDate(), oldAgeDeathRepayData.getIssuKind());
                String procMsgStr = procMsg;
                if (!"0".equals(procMsg.substring(0, 1))) {
                    throw new Exception("V_O_PROCMSGCODE)<>0，處理作業失敗，ROLLBACK。procMsg:" + procMsg);
                }
            }
        }

        if (checkIndex != null) {
            for (BaappbaseDataUpdateCase oldAgeDeathRepayData : oldAgeDeathRepayDataCaseList) {
                for (int i = 0; i <= checkIndex.length - 1; i++) {
                    int Index = checkIndex[i];
                    String apNo = checkOldAgeDeathRepayData.getApNo();
                    String seqNo = heirSeqNoExistDataCaseList.get(Index).getSeqNo();
                    String oriIssuYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getOriIssuYm());
                    String payYm = DateUtility.changeChineseYearMonthType(oldAgeDeathRepayData.getPayYm());
                    // CALL SP回寫分配金額資料至BAREGIVEDTL 資料MK為1 原分配的資料已改為4 如沒回寫此TABLE 無法抓取新分配之資料
                    // 此SP也會新增 分配金額後的資料至PFLBAC出納TABLE
                    String procMsg = doDeathRepayRefundment(apNo, seqNo, oriIssuYm, payYm, DateUtility.getNowWestDate(), oldAgeDeathRepayData.getIssuKind());
                    String procMsgStr = procMsg;
                    if (!"0".equals(procMsg.substring(0, 1))) {
                        throw new Exception("V_O_PROCMSGCODE)<>0，處理作業失敗，ROLLBACK。procMsg:" + procMsg);
                    }
                }
            }
        }

    }

    /**
     * 分配金額 for 受款人死亡改匯處理作業
     * 
     * @pramati
     */
    public static List<BigDecimal> getAvgNum(BigDecimal persons, BigDecimal num) {
        List<BigDecimal> result = new ArrayList<BigDecimal>();
        for (int i = 1; i <= persons.longValue(); i++) {
            result.add(!(num.remainder(persons).compareTo(new BigDecimal(i)) == -1) ? num.divide(persons, BigDecimal.ROUND_DOWN).add(new BigDecimal(1)) : num.divide(persons, BigDecimal.ROUND_DOWN));
        }
        return result;
    }

    // OldAge Death Repay end

    /**
     * 新增 遺屬強迫不合格記錄檔(<code>BACOMPELNOPAY</code>)
     */
    public void insertBacompelnopayData(Bacompelnopay bacompelnopay) {
        bacompelnopayDao.insertData(bacompelnopay);
    }

    /**
     * 修改 遺屬強迫不合格記錄檔(<code>BACOMPELNOPAY</code>) 資料
     */
    public void updateBacompelnopayData(Bacompelnopay bacompelnopay) {
        bacompelnopayDao.updateData(bacompelnopay);
    }

    /**
     * 刪除 遺屬強迫不合格記錄檔(<code>BACOMPELNOPAY</code>) 資料
     */
    public void deleteBacompelnopayData(String apNo, String seqNo) {
        bacompelnopayDao.deleteData(apNo, seqNo);
    }

    /**
     * 更新 審核給付清單紀錄檔 (<code>BALP0D020</code>)
     */
    public void updateProcMkByApNo(String apNo) {
        balp0d020Dao.updateProcMkByApNo(apNo);
    }

    /**
     * 更新 延伸給付主黨 (<code>BAAPPEXPAND</code>)
     */
    public void updateAbleapsYmForDisabledCheckMarkLevelAdjust(Baappexpand baappexpand) {
        baappexpandDao.updateAbleapsYmForDisabledCheckMarkLevelAdjust(baappexpand);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 得請領起始年月 for 失能年金編審註記程度調整
     * 
     * @param apNo 受理編號
     * @param baappbaseId
     */
    public String getAbleapsYmForDisabledCheckMarkLevelAdjust(String apNo, BigDecimal baappbaseId) {
        return baappexpandDao.getAbleapsYmForDisabledCheckMarkLevelAdjust(apNo, baappbaseId);
    }

    public void updateRegetCipbMkByApNo(String apNo, String regetCipbMk) {
        baappbaseDao.updateRegetCipbMkByApNo(apNo, regetCipbMk);
    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID DATALIST<br>
     * 
     * @param apNo
     * @param insKdCash
     * @param bliAccountCode
     * @param bookedBook
     * @param bkAccountDt
     * @param batchNo
     * @param cashAmt
     * @param divSeq
     * @param indexNo
     * @return
     */
    public List<CashReceiveDataCase> selectBaunacpdtlDataForCashReceivedBy(String apNo, String insKdCash, String bliAccountCode, String bookedBook, String bkAccountDt, String batchNo, String serialNo, BigDecimal cashAmt, String divSeq,
                    String indexNo) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlDataForCashReceivedBy(apNo, insKdCash, bliAccountCode, bookedBook, bkAccountDt, batchNo, serialNo, cashAmt, divSeq, indexNo);

        List<CashReceiveDataCase> returnList = new ArrayList<CashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            CashReceiveDataCase caseObj = new CashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID DATALIST<br>
     * 
     * @param apNo
     * @param insKdCash
     * @param bliAccountCode
     * @param bookedBook
     * @param bkAccountDt
     * @param batchNo
     * @param cashAmt
     * @param divSeq
     * @param indexNo
     * @return
     */
    public List<CashReceiveDataCase> selectBaunacpdtlIdForCashReceivedBy(String apNo, String insKdCash, String bliAccountCode, String bookedBook, String bkAccountDt, String batchNo, String serialNo, BigDecimal cashAmt, String divSeq,
                    String indexNo) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlIdForCashReceivedBy(apNo, insKdCash, bliAccountCode, bookedBook, bkAccountDt, batchNo, serialNo, cashAmt, divSeq, indexNo);

        List<CashReceiveDataCase> returnList = new ArrayList<CashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            CashReceiveDataCase caseObj = new CashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID DATALIST<br>
     * 
     * @param transActionId
     * @param transActionSeq
     * @param inskdRegive
     * @return
     */
    public List<RemittanceReceiveDataCase> selectBaunacpdtlDataForRemittanceReceivedBy(String transActionId, String transActionSeq, String insKd) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlDataForRemittanceReceivedBy(transActionId, transActionSeq, insKd);

        List<RemittanceReceiveDataCase> returnList = new ArrayList<RemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            RemittanceReceiveDataCase caseObj = new RemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID RECREM LIST<br>
     * 
     * @param transActionId
     * @param transActionSeq
     * @param inskdRegive
     * @return
     */
    public List<RemittanceReceiveDataCase> selectBaunacpdtlIdForRemittanceReceivedBy(String transActionId, String transActionSeq, String insKd) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlIdForRemittanceReceivedBy(transActionId, transActionSeq, insKd);

        List<RemittanceReceiveDataCase> returnList = new ArrayList<RemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            RemittanceReceiveDataCase caseObj = new RemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID DATALIST<br>
     * 
     * @param apNo
     * @param insKdCash
     * @param bliAccountCode
     * @param bookedBook
     * @param bkAccountDt
     * @param batchNo
     * @param cashAmt
     * @param divSeq
     * @param indexNo
     * @return
     */
    public List<DisabledCashReceiveDataCase> selectBaunacpdtlDataForDisabledCashReceivedBy(String apNo, String insKdCash, String bliAccountCode, String bookedBook, String bkAccountDt, String batchNo, String serialNo, BigDecimal cashAmt,
                    String divSeq, String indexNo) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlDataForCashReceivedBy(apNo, insKdCash, bliAccountCode, bookedBook, bkAccountDt, batchNo, serialNo, cashAmt, divSeq, indexNo);

        List<DisabledCashReceiveDataCase> returnList = new ArrayList<DisabledCashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            DisabledCashReceiveDataCase caseObj = new DisabledCashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID DATALIST<br>
     * 
     * @param apNo
     * @param insKdCash
     * @param bliAccountCode
     * @param bookedBook
     * @param bkAccountDt
     * @param batchNo
     * @param cashAmt
     * @param divSeq
     * @param indexNo
     * @return
     */
    public List<DisabledCashReceiveDataCase> selectBaunacpdtlIdForDisabledCashReceivedBy(String apNo, String insKdCash, String bliAccountCode, String bookedBook, String bkAccountDt, String batchNo, String serialNo, BigDecimal cashAmt, String divSeq,
                    String indexNo) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlIdForCashReceivedBy(apNo, insKdCash, bliAccountCode, bookedBook, bkAccountDt, batchNo, serialNo, cashAmt, divSeq, indexNo);

        List<DisabledCashReceiveDataCase> returnList = new ArrayList<DisabledCashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            DisabledCashReceiveDataCase caseObj = new DisabledCashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID DATALIST<br>
     * 
     * @param transActionId
     * @param transActionSeq
     * @param inskdRegive
     * @return
     */
    public List<DisabledRemittanceReceiveDataCase> selectBaunacpdtlDataForDisabledRemittanceReceivedBy(String transActionId, String transActionSeq, String insKd) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlDataForRemittanceReceivedBy(transActionId, transActionSeq, insKd);

        List<DisabledRemittanceReceiveDataCase> returnList = new ArrayList<DisabledRemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            DisabledRemittanceReceiveDataCase caseObj = new DisabledRemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID RECREM LIST<br>
     * 
     * @param transActionId
     * @param transActionSeq
     * @param inskdRegive
     * @return
     */
    public List<DisabledRemittanceReceiveDataCase> selectBaunacpdtlIdForDisabledRemittanceReceivedBy(String transActionId, String transActionSeq, String insKd) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlIdForRemittanceReceivedBy(transActionId, transActionSeq, insKd);

        List<DisabledRemittanceReceiveDataCase> returnList = new ArrayList<DisabledRemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            DisabledRemittanceReceiveDataCase caseObj = new DisabledRemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID DATALIST<br>
     * 
     * @param apNo
     * @param insKdCash
     * @param bliAccountCode
     * @param bookedBook
     * @param bkAccountDt
     * @param batchNo
     * @param cashAmt
     * @param divSeq
     * @param indexNo
     * @return
     */
    public List<SurvivorCashReceiveDataCase> selectBaunacpdtlDataForSurvivorCashReceivedBy(String apNo, String insKdCash, String bliAccountCode, String bookedBook, String bkAccountDt, String batchNo, String serialNo, BigDecimal cashAmt,
                    String divSeq, String indexNo) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlDataForCashReceivedBy(apNo, insKdCash, bliAccountCode, bookedBook, bkAccountDt, batchNo, serialNo, cashAmt, divSeq, indexNo);

        List<SurvivorCashReceiveDataCase> returnList = new ArrayList<SurvivorCashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            SurvivorCashReceiveDataCase caseObj = new SurvivorCashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID DATALIST<br>
     * 
     * @param apNo
     * @param insKdCash
     * @param bliAccountCode
     * @param bookedBook
     * @param bkAccountDt
     * @param batchNo
     * @param cashAmt
     * @param divSeq
     * @param indexNo
     * @return
     */
    public List<SurvivorCashReceiveDataCase> selectBaunacpdtlIdForSurvivorCashReceivedBy(String apNo, String insKdCash, String bliAccountCode, String bookedBook, String bkAccountDt, String batchNo, String serialNo, BigDecimal cashAmt, String divSeq,
                    String indexNo) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlIdForCashReceivedBy(apNo, insKdCash, bliAccountCode, bookedBook, bkAccountDt, batchNo, serialNo, cashAmt, divSeq, indexNo);

        List<SurvivorCashReceiveDataCase> returnList = new ArrayList<SurvivorCashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            SurvivorCashReceiveDataCase caseObj = new SurvivorCashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID DATALIST<br>
     * 
     * @param transActionId
     * @param transActionSeq
     * @param inskdRegive
     * @return
     */
    public List<SurvivorRemittanceReceiveDataCase> selectBaunacpdtlDataForSurvivorRemittanceReceivedBy(String transActionId, String transActionSeq, String insKd) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlDataForRemittanceReceivedBy(transActionId, transActionSeq, insKd);

        List<SurvivorRemittanceReceiveDataCase> returnList = new ArrayList<SurvivorRemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            SurvivorRemittanceReceiveDataCase caseObj = new SurvivorRemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID RECREM LIST<br>
     * 
     * @param transActionId
     * @param transActionSeq
     * @param inskdRegive
     * @return
     */
    public List<SurvivorRemittanceReceiveDataCase> selectBaunacpdtlIdForSurvivorRemittanceReceivedBy(String transActionId, String transActionSeq, String insKd) {

        List<Baacpdtl> list = baacpdtlDao.selectBaunacpdtlIdForRemittanceReceivedBy(transActionId, transActionSeq, insKd);

        List<SurvivorRemittanceReceiveDataCase> returnList = new ArrayList<SurvivorRemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            SurvivorRemittanceReceiveDataCase caseObj = new SurvivorRemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }
        return returnList;

    }

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for for 應收收回處理作業 - 退現收回註銷
     * 
     * @param baunacpdtlId
     * @return
     */
    public CashReceiveDataCase selectDataForCashReceiveBy(BigDecimal baunacpdtlId) {
        CashReceiveDataCase caseData = new CashReceiveDataCase();
        Baunacpdtl baunacpdtl = baunacpdtlDao.selectDataForPaymentReceiveBy(baunacpdtlId);

        if (baunacpdtl == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baunacpdtl);
            return caseData;
        }

    }

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for for 應收收回處理作業 - 退現收回註銷
     * 
     * @param baunacpdtlId
     * @return
     */
    public DisabledCashReceiveDataCase selectDataForDisabledCashReceiveBy(BigDecimal baunacpdtlId) {
        DisabledCashReceiveDataCase caseData = new DisabledCashReceiveDataCase();
        Baunacpdtl baunacpdtl = baunacpdtlDao.selectDataForPaymentReceiveBy(baunacpdtlId);

        if (baunacpdtl == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baunacpdtl);
            return caseData;
        }

    }

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for for 應收收回處理作業 - 退現收回註銷
     * 
     * @param baunacpdtlId
     * @return
     */
    public SurvivorCashReceiveDataCase selectDataForSurvivorCashReceiveBy(BigDecimal baunacpdtlId) {
        SurvivorCashReceiveDataCase caseData = new SurvivorCashReceiveDataCase();
        Baunacpdtl baunacpdtl = baunacpdtlDao.selectDataForPaymentReceiveBy(baunacpdtlId);

        if (baunacpdtl == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baunacpdtl);
            return caseData;
        }

    }

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for for 應收收回處理作業 - 退現收回註銷
     * 
     * @param baunacpdtlId
     * @return
     */
    public RemittanceReceiveDataCase selectDataForRemittanceReceiveBy(BigDecimal baunacpdtlId) {
        RemittanceReceiveDataCase caseData = new RemittanceReceiveDataCase();
        Baunacpdtl baunacpdtl = baunacpdtlDao.selectDataForPaymentReceiveBy(baunacpdtlId);

        if (baunacpdtl == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baunacpdtl);
            return caseData;
        }

    }

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for for 應收收回處理作業 - 退現收回註銷
     * 
     * @param baunacpdtlId
     * @return
     */
    public DisabledRemittanceReceiveDataCase selectDataForDisabledRemittanceReceiveBy(BigDecimal baunacpdtlId) {
        DisabledRemittanceReceiveDataCase caseData = new DisabledRemittanceReceiveDataCase();
        Baunacpdtl baunacpdtl = baunacpdtlDao.selectDataForPaymentReceiveBy(baunacpdtlId);

        if (baunacpdtl == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baunacpdtl);
            return caseData;
        }

    }

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for for 應收收回處理作業 - 退現收回註銷
     * 
     * @param baunacpdtlId
     * @return
     */
    public SurvivorRemittanceReceiveDataCase selectDataForSurvivorRemittanceReceiveBy(BigDecimal baunacpdtlId) {
        SurvivorRemittanceReceiveDataCase caseData = new SurvivorRemittanceReceiveDataCase();
        Baunacpdtl baunacpdtl = baunacpdtlDao.selectDataForPaymentReceiveBy(baunacpdtlId);

        if (baunacpdtl == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baunacpdtl);
            return caseData;
        }

    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public OldAgePaymentReceiveDataCase selectCashReceiveDataBy(String apNo, String payCode) {
        OldAgePaymentReceiveDataCase caseData = new OldAgePaymentReceiveDataCase();
        List<Baappbase> baappbase = baappbaseDao.selectCashReceiveDataBy(apNo, payCode);
        for (int i = 0; i <  baappbase.size()  ; i++) {
            Baappbase obj = baappbase.get(i);
            BeanUtility.copyProperties(caseData, obj);
        }
        return caseData;
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public OldAgePaymentReceiveDataCase selectChkCancelCashReceiveDataBy(String apNo, String payCode) {
        OldAgePaymentReceiveDataCase caseData = new OldAgePaymentReceiveDataCase();
        Baappbase baappbase = baappbaseDao.selectChkCancelCashReceiveDataBy(apNo, payCode);

        if (baappbase == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baappbase);
            return caseData;
        }
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 失能年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public DisabledPaymentReceiveDataCase selectDisabledCashReceiveDataBy(String apNo, String payCode) {
        DisabledPaymentReceiveDataCase caseData = new DisabledPaymentReceiveDataCase();
        Baappbase baappbase = baappbaseDao.selectKCashReceiveDataBy(apNo, payCode);

        if (baappbase == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baappbase);
            return caseData;
        }
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 失能年金應收收回處理作業-現金收回註銷
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public DisabledPaymentReceiveDataCase selectChkCancelDisabledCashReceiveDataBy(String apNo, String payCode) {
        DisabledPaymentReceiveDataCase caseData = new DisabledPaymentReceiveDataCase();
        Baappbase baappbase = baappbaseDao.selectChkCancelCashReceiveDataBy(apNo, payCode);

        if (baappbase == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baappbase);
            return caseData;
        }
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public SurvivorPaymentReceiveDataCase selectSurvivorCashReceiveDataBy(String apNo, String payCode) {
        SurvivorPaymentReceiveDataCase caseData = new SurvivorPaymentReceiveDataCase();
        Baappbase baappbase = baappbaseDao.selectSCashReceiveDataBy(apNo, payCode);

        if (baappbase == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baappbase);
            return caseData;
        }
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-現金收回註銷
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public SurvivorPaymentReceiveDataCase selectChkCancelSurvivorCashReceiveDataBy(String apNo, String payCode) {
        SurvivorPaymentReceiveDataCase caseData = new SurvivorPaymentReceiveDataCase();
        Baappbase baappbase = baappbaseDao.selectChkCancelCashReceiveDataBy(apNo, payCode);

        if (baappbase == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baappbase);
            return caseData;
        }
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public OldAgePaymentReceiveDataCase selectRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        OldAgePaymentReceiveDataCase caseData = new OldAgePaymentReceiveDataCase();
        Baappbase baappbase = baappbaseDao.selectRemittanceReceiveDataBy(apNo, seqNo, payCode);

        if (baappbase == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baappbase);
            return caseData;
        }
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-退匯收回註銷
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public OldAgePaymentReceiveDataCase selectChkCancelRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        OldAgePaymentReceiveDataCase caseData = new OldAgePaymentReceiveDataCase();
        Baappbase baappbase = baappbaseDao.selectChkCancelRemittanceReceiveDataBy(apNo, seqNo, payCode);

        if (baappbase == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baappbase);
            return caseData;
        }
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 失能年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public DisabledPaymentReceiveDataCase selectDisabledRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        DisabledPaymentReceiveDataCase caseData = new DisabledPaymentReceiveDataCase();
        Baappbase baappbase = baappbaseDao.selectDisabledRemittanceReceiveDataBy(apNo, seqNo, payCode);

        if (baappbase == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baappbase);
            return caseData;
        }
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 失能年金應收收回處理作業-退匯收回註銷
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public DisabledPaymentReceiveDataCase selectChkCancelDisabledRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        DisabledPaymentReceiveDataCase caseData = new DisabledPaymentReceiveDataCase();
        Baappbase baappbase = baappbaseDao.selectChkCancelDisabledRemittanceReceiveDataBy(apNo, seqNo, payCode);

        if (baappbase == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baappbase);
            return caseData;
        }
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 遺屬年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public SurvivorPaymentReceiveDataCase selectSurvivorRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        SurvivorPaymentReceiveDataCase caseData = new SurvivorPaymentReceiveDataCase();
        Baappbase baappbase = baappbaseDao.selectSurvivorRemittanceReceiveDataBy(apNo, seqNo, payCode);

        if (baappbase == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baappbase);
            return caseData;
        }
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 遺屬年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public SurvivorPaymentReceiveDataCase selectChkCancelSurvivorRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        SurvivorPaymentReceiveDataCase caseData = new SurvivorPaymentReceiveDataCase();
        Baappbase baappbase = baappbaseDao.selectChkCancelSurvivorRemittanceReceiveDataBy(apNo, seqNo, payCode);

        if (baappbase == null) {
            return null;
        }
        else {
            BeanUtility.copyProperties(caseData, baappbase);
            return caseData;
        }
    }

    /**
     * 依傳入條件取得 應收資料(<code>BAUNACPDTL</code>) 資料 for 老年年金應收收回處理 and 列印作業 - 應收收回相關報表 - 整批收回核定清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<AccountsReceivableDataCase> selectAccountsReceivableDataListBy(String apNo, String seqNo, String issuYm, String payYm) {
        List<Baunacpdtl> list = baunacpdtlDao.selectAccountsReceivableDataListBy(apNo, seqNo, issuYm, payYm);
        List<AccountsReceivableDataCase> returnList = new ArrayList<AccountsReceivableDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baunacpdtl obj = list.get(i);
            AccountsReceivableDataCase caseObj = new AccountsReceivableDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 應收資料(<code>BAUNACPDTL</code>) 資料 for 失能年金應收收回處理
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<DisabledAccountsReceivableDataCase> selectDisabledAccountsReceivableDataListBy(String apNo, String seqNo, String issuYm, String payYm) {
        List<Baunacpdtl> list = baunacpdtlDao.selectAccountsReceivableDataListBy(apNo, seqNo, issuYm, payYm);
        List<DisabledAccountsReceivableDataCase> returnList = new ArrayList<DisabledAccountsReceivableDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baunacpdtl obj = list.get(i);
            DisabledAccountsReceivableDataCase caseObj = new DisabledAccountsReceivableDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得重新查核失能程度檔(<code>BARECHECK</code>) 資料 FOR 失能年金受款人資料更正 重新查核失能程度
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return <code>List<Bachkfile></code> 物件
     */
    public List<DisabledApplicationDataUpdateBareCheckCase> selectBareCheckDataBy(String apNo, String seqNo) {
        List<Barecheck> list = barecheckDao.selectBareCheckDataBy(apNo, seqNo);
        List<DisabledApplicationDataUpdateBareCheckCase> returnList = new ArrayList<DisabledApplicationDataUpdateBareCheckCase>();

        for (int i = 0; i < list.size(); i++) {
            Barecheck obj = list.get(i);
            DisabledApplicationDataUpdateBareCheckCase caseObj = new DisabledApplicationDataUpdateBareCheckCase();
            // 轉換日期格式
            obj.setReChkYm(DateUtility.changeWestYearMonthType(obj.getReChkYm()));
            obj.setComReChkYm(DateUtility.changeWestYearMonthType(obj.getComReChkYm()));

            BeanUtility.copyProperties(caseObj, obj);
            // 重新查核狀態 中文
            if (StringUtils.isNotBlank(caseObj.getReChkStatus())) {
                caseObj.setReChkStatusStr(caseObj.getReChkStatus() + "-" + baparamDao.selectParamNameBy(null, "KRECHKSTATUS", caseObj.getReChkStatus()));
            }
            // 重新查核結果 中文
            if (StringUtils.isNotBlank(caseObj.getReChkResult())) {
                caseObj.setReChkResultStr(caseObj.getReChkResult() + "-" + baparamDao.getParamNameForKRECHKRESULT(caseObj.getReChkResult()));
            }
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 應收資料(<code>BAUNACPDTL</code>) 資料 for 遺屬年金應收收回處理
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<SurvivorAccountsReceivableDataCase> selectSurvivorAccountsReceivableDataListBy(String apNo, String seqNo, String issuYm, String payYm) {
        List<Baunacpdtl> list = baunacpdtlDao.selectAccountsReceivableDataListBy(apNo, seqNo, issuYm, payYm);
        List<SurvivorAccountsReceivableDataCase> returnList = new ArrayList<SurvivorAccountsReceivableDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baunacpdtl obj = list.get(i);
            SurvivorAccountsReceivableDataCase caseObj = new SurvivorAccountsReceivableDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 老年年金應收收回處理
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<RemittanceReceiveDataCase> selectRemittanceReceiveDataListBy(String apNo, String seqNo) {
        List<Bapflbac> list = bapflbacDao.selectRemittanceReceiveDataListBy(apNo, seqNo);
        List<RemittanceReceiveDataCase> returnList = new ArrayList<RemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Bapflbac obj = list.get(i);
            RemittanceReceiveDataCase caseObj = new RemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 老年年金應收收回處理 註銷
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<RemittanceReceiveDataCase> selectRemittanceReceivedDataListBy(String apNo, String seqNo) {
        List<Bapflbac> list = bapflbacDao.selectRemittanceReceivedDataListBy(apNo, seqNo);
        List<RemittanceReceiveDataCase> returnList = new ArrayList<RemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Bapflbac obj = list.get(i);
            RemittanceReceiveDataCase caseObj = new RemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 失能年金應收收回處理
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<DisabledRemittanceReceiveDataCase> selectDisabledRemittanceReceiveDataListBy(String apNo, String seqNo) {
        List<Bapflbac> list = bapflbacDao.selectDisabledRemittanceReceiveDataListBy(apNo, seqNo);
        List<DisabledRemittanceReceiveDataCase> returnList = new ArrayList<DisabledRemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Bapflbac obj = list.get(i);
            DisabledRemittanceReceiveDataCase caseObj = new DisabledRemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 失能年金應收收回處理 註銷
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<DisabledRemittanceReceiveDataCase> selectDisabledRemittanceReceivedDataListBy(String apNo, String seqNo) {
        List<Bapflbac> list = bapflbacDao.selectDisabledRemittanceReceivedDataListBy(apNo, seqNo);
        List<DisabledRemittanceReceiveDataCase> returnList = new ArrayList<DisabledRemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Bapflbac obj = list.get(i);
            DisabledRemittanceReceiveDataCase caseObj = new DisabledRemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 遺屬年金應收收回處理
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<SurvivorRemittanceReceiveDataCase> selectSurvivorRemittanceReceiveDataListBy(String apNo, String seqNo) {
        List<Bapflbac> list = bapflbacDao.selectSurvivorRemittanceReceiveDataListBy(apNo, seqNo);
        List<SurvivorRemittanceReceiveDataCase> returnList = new ArrayList<SurvivorRemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Bapflbac obj = list.get(i);
            SurvivorRemittanceReceiveDataCase caseObj = new SurvivorRemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 遺屬年金應收收回處理
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<SurvivorRemittanceReceiveDataCase> selectSurvivorRemittanceReceivedDataListBy(String apNo, String seqNo) {
        List<Bapflbac> list = bapflbacDao.selectSurvivorRemittanceReceivedDataListBy(apNo, seqNo);
        List<SurvivorRemittanceReceiveDataCase> returnList = new ArrayList<SurvivorRemittanceReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Bapflbac obj = list.get(i);
            SurvivorRemittanceReceiveDataCase caseObj = new SurvivorRemittanceReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退現資料(<code>localPfpccky</code>) 資料 for 老年年金應收收回處理
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<CashReceiveDataCase> selectCashReceiveDataListBy(String apNo, String payCode) {
        List<Pfpccky> list = localPfpcckyDao.selectCashReceiveDataListBy(apNo, payCode);
        List<CashReceiveDataCase> returnList = new ArrayList<CashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Pfpccky obj = list.get(i);
            CashReceiveDataCase caseObj = new CashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            if (StringUtils.isNotBlank(caseObj.getBkAccountDt())) {
                caseObj.setBkAccountDt(DateUtility.changeWestYearType(caseObj.getBkAccountDt().substring(0, 4)) + caseObj.getBkAccountDt().substring(4, caseObj.getBkAccountDt().length()));
            }
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退現資料(<code>baacpdtl</code>) 資料 for 老年年金應收收回註銷處理
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<CashReceiveDataCase> selectCashReceivedDataListBy(String apNo, String payCode) {
        List<Baacpdtl> list = baacpdtlDao.selectCashReceivedDataBy(apNo, payCode);
        List<CashReceiveDataCase> returnList = new ArrayList<CashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            CashReceiveDataCase caseObj = new CashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退現資料(<code>localPfpccky</code>) 資料 for 失能年金應收收回處理
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<DisabledCashReceiveDataCase> selectDisabledCashReceiveDataListBy(String apNo, String payCode) {
        List<Pfpccky> list = localPfpcckyDao.selectCashReceiveDataListBy(apNo, payCode);
        List<DisabledCashReceiveDataCase> returnList = new ArrayList<DisabledCashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Pfpccky obj = list.get(i);
            DisabledCashReceiveDataCase caseObj = new DisabledCashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退現資料(<code>localPfpccky</code>) 資料 for 失能年金應收收回處理
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<DisabledCashReceiveDataCase> selectDisabledCashReceivedDataListBy(String apNo, String payCode) {
        List<Baacpdtl> list = baacpdtlDao.selectCashReceivedDataBy(apNo, payCode);
        List<DisabledCashReceiveDataCase> returnList = new ArrayList<DisabledCashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            DisabledCashReceiveDataCase caseObj = new DisabledCashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退現資料(<code>BAPFLBAC</code>) 資料 for 遺屬年金應收收回處理
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<SurvivorCashReceiveDataCase> selectSurvivorCashReceiveDataListBy(String apNo, String payCode) {
        List<Pfpccky> list = localPfpcckyDao.selectCashReceiveDataListBy(apNo, payCode);
        List<SurvivorCashReceiveDataCase> returnList = new ArrayList<SurvivorCashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Pfpccky obj = list.get(i);
            SurvivorCashReceiveDataCase caseObj = new SurvivorCashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 依傳入條件取得 退現資料(<code>BAPFLBAC</code>) 資料 for 遺屬年金應收收回處理
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<SurvivorCashReceiveDataCase> selectSurvivorCashReceivedDataListBy(String apNo, String payCode) {
        List<Baacpdtl> list = baacpdtlDao.selectCashReceivedDataBy(apNo, payCode);
        List<SurvivorCashReceiveDataCase> returnList = new ArrayList<SurvivorCashReceiveDataCase>();

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl obj = list.get(i);
            SurvivorCashReceiveDataCase caseObj = new SurvivorCashReceiveDataCase();
            BeanUtility.copyProperties(caseObj, obj);
            returnList.add(caseObj);
        }

        return returnList;
    }

    /**
     * 更新 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 應收收回處理作業 and 列印作業 - 應收收回相關報表 - 整批收回核定清單
     * 
     * @param recRem 未收總金額
     * @param actEndMk 應收結案註記
     * @param baunacprecId 應收記錄編號
     * @param Baunacpdtl 應收帳務明細檔
     */
    public void updateBaunacpdtlForPaymentReceive(BigDecimal recRem, String actEndMk, BigDecimal baunacpdtlId) {
        baunacpdtlDao.updateBaunacpdtlForPaymentReceive(recRem, actEndMk, baunacpdtlId);
    }

    /**
     * 新增(<code>BAACPDTL</code>) 資料 for 1. 老年年金應收收回處理作業 - 退匯
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataForRemittanceReceiveData(UserBean userData, OldAgePaymentReceiveDataCase paymentReceiveData, AccountsReceivableDataCase accountsReceivableData, RemittanceReceiveDataCase remittanceReceiveData) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAACPDTL ...");
        Baacpdtl baacpdtl = new Baacpdtl();
        BeanUtility.copyProperties(baacpdtl, accountsReceivableData);

        baacpdtl.setEmpNo(userData.getEmpNo()); // 處理人員
        baacpdtl.setTransActionId(remittanceReceiveData.getTransActionId());
        baacpdtl.setTransActionSeq(remittanceReceiveData.getTransActionSeq());
        baacpdtl.setInsKd(remittanceReceiveData.getInsKd());
        baacpdtl.setDisPayKind(paymentReceiveData.getPayKind());

        baacpdtlDao.insertDataForRemittanceReceiveData(baacpdtl);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baacpdtl);
        }
        log.debug("Insert BAACPDTL Finished ...");
        // ]
    }

    /**
     * 新增(<code>BAACPDTL</code>) 資料 for 1. 失能年金應收收回處理作業 - 退匯
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataForDisabledRemittanceReceiveData(UserBean userData, DisabledPaymentReceiveDataCase paymentReceiveData, DisabledAccountsReceivableDataCase accountsReceivableData, DisabledRemittanceReceiveDataCase remittanceReceiveData) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAACPDTL ...");
        Baacpdtl baacpdtl = new Baacpdtl();
        BeanUtility.copyProperties(baacpdtl, accountsReceivableData);

        baacpdtl.setEmpNo(userData.getEmpNo()); // 處理人員
        baacpdtl.setTransActionId(remittanceReceiveData.getTransActionId());
        baacpdtl.setTransActionSeq(remittanceReceiveData.getTransActionSeq());
        baacpdtl.setInsKd(remittanceReceiveData.getInsKd());
        baacpdtl.setDisPayKind(paymentReceiveData.getPayKind());

        baacpdtlDao.insertDataForRemittanceReceiveData(baacpdtl);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baacpdtl);
        }
        log.debug("Insert BAACPDTL Finished ...");
        // ]
    }

    /**
     * 新增(<code>BAACPDTL</code>) 資料 for 1. 遺屬年金應收收回處理作業 - 退匯
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataForSurvivorRemittanceReceiveData(UserBean userData, SurvivorPaymentReceiveDataCase paymentReceiveData, SurvivorAccountsReceivableDataCase accountsReceivableData, SurvivorRemittanceReceiveDataCase remittanceReceiveData) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAACPDTL ...");
        Baacpdtl baacpdtl = new Baacpdtl();
        BeanUtility.copyProperties(baacpdtl, accountsReceivableData);

        baacpdtl.setEmpNo(userData.getEmpNo()); // 處理人員
        baacpdtl.setTransActionId(remittanceReceiveData.getTransActionId());
        baacpdtl.setTransActionSeq(remittanceReceiveData.getTransActionSeq());
        baacpdtl.setInsKd(remittanceReceiveData.getInsKd());
        baacpdtl.setDisPayKind(paymentReceiveData.getPayKind());

        baacpdtlDao.insertDataForRemittanceReceiveData(baacpdtl);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baacpdtl);
        }
        log.debug("Insert BAACPDTL Finished ...");
        // ]
    }

    /**
     * 新增(<code>BAACPDTL</code>) 資料 for 1. 老年年金應收收回處理作業 - 退現
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataForCashReceiveData(UserBean userData, OldAgePaymentReceiveDataCase paymentReceiveData, AccountsReceivableDataCase accountsReceivableData, CashReceiveDataCase cashReceiveData) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAACPDTL ...");
        Baacpdtl baacpdtl = new Baacpdtl();
        BeanUtility.copyProperties(baacpdtl, accountsReceivableData);

        baacpdtl.setEmpNo(userData.getEmpNo()); // 處理人員

        baacpdtl.setInsKd(cashReceiveData.getInsKd());
        baacpdtl.setBli_Account_Code(cashReceiveData.getBli_Account_Code());
        baacpdtl.setBookedBook(cashReceiveData.getBookedBook());
        baacpdtl.setBkAccountDt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
        baacpdtl.setBatchNo(cashReceiveData.getBatchNo());
        baacpdtl.setSerialNo(cashReceiveData.getSerialNo());
        baacpdtl.setCash_Amt(cashReceiveData.getCash_Amt());
        baacpdtl.setDivSeq(cashReceiveData.getDivSeq());
        baacpdtl.setIndex_No(cashReceiveData.getIndex_No());

        baacpdtl.setDisPayKind(paymentReceiveData.getPayKind());

        baacpdtlDao.insertDataForCashReceiveData(baacpdtl);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baacpdtl);
        }
        log.debug("Insert BAACPDTL Finished ...");
        // ]
    }

    /**
     * 新增(<code>BAACPDTL</code>) 資料 for 1. 失能年金應收收回處理作業 - 退現
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataForDisabledCashReceiveData(UserBean userData, DisabledPaymentReceiveDataCase paymentReceiveData, DisabledAccountsReceivableDataCase accountsReceivableData, DisabledCashReceiveDataCase cashReceiveData) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAACPDTL ...");
        Baacpdtl baacpdtl = new Baacpdtl();
        BeanUtility.copyProperties(baacpdtl, accountsReceivableData);

        baacpdtl.setEmpNo(userData.getEmpNo()); // 處理人員

        baacpdtl.setInsKd(cashReceiveData.getInsKd());
        baacpdtl.setBli_Account_Code(cashReceiveData.getBli_Account_Code());
        baacpdtl.setBookedBook(cashReceiveData.getBookedBook());
        baacpdtl.setBkAccountDt(cashReceiveData.getBkAccountDt());
        baacpdtl.setBatchNo(cashReceiveData.getBatchNo());
        baacpdtl.setSerialNo(cashReceiveData.getSerialNo());
        baacpdtl.setCash_Amt(cashReceiveData.getCash_Amt());
        baacpdtl.setDivSeq(cashReceiveData.getDivSeq());
        baacpdtl.setIndex_No(cashReceiveData.getIndex_No());

        baacpdtl.setDisPayKind(paymentReceiveData.getPayKind());

        baacpdtlDao.insertDataForCashReceiveData(baacpdtl);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baacpdtl);
        }
        log.debug("Insert BAACPDTL Finished ...");
        // ]
    }

    /**
     * 新增(<code>BAACPDTL</code>) 資料 for 1. 遺屬年金應收收回處理作業 - 退現
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataForSurvivorCashReceiveData(UserBean userData, SurvivorPaymentReceiveDataCase paymentReceiveData, SurvivorAccountsReceivableDataCase accountsReceivableData, SurvivorCashReceiveDataCase cashReceiveData) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAACPDTL ...");
        Baacpdtl baacpdtl = new Baacpdtl();
        BeanUtility.copyProperties(baacpdtl, accountsReceivableData);

        baacpdtl.setEmpNo(userData.getEmpNo()); // 處理人員

        baacpdtl.setInsKd(cashReceiveData.getInsKd());
        baacpdtl.setBli_Account_Code(cashReceiveData.getBli_Account_Code());
        baacpdtl.setBookedBook(cashReceiveData.getBookedBook());
        baacpdtl.setBkAccountDt(cashReceiveData.getBkAccountDt());
        baacpdtl.setBatchNo(cashReceiveData.getBatchNo());
        baacpdtl.setSerialNo(cashReceiveData.getSerialNo());
        baacpdtl.setCash_Amt(cashReceiveData.getCash_Amt());
        baacpdtl.setDivSeq(cashReceiveData.getDivSeq());
        baacpdtl.setIndex_No(cashReceiveData.getIndex_No());

        baacpdtl.setDisPayKind(paymentReceiveData.getPayKind());

        baacpdtlDao.insertDataForCashReceiveData(baacpdtl);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baacpdtl);
        }
        log.debug("Insert BAACPDTL Finished ...");
        // ]
    }

    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 年金應收收回處理作業-退匯收回
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlForPaymentReceive(UserBean userData, String apNo, String seqNo, String issuKind, String oriIssuYm, String payYm) {
        // 更新資料到 改匯檔
        // [
        log.debug("Start Update BAREGIVEDTL ...");
        Baregivedtl baregivedtl = new Baregivedtl();
        baregivedtl.setApNo(apNo);
        baregivedtl.setSeqNo(seqNo);
        baregivedtl.setIssuKind(issuKind);
        baregivedtl.setOriIssuYm(oriIssuYm);
        baregivedtl.setPayYm(payYm);
        baregivedtl.setProcDeptId(userData.getDeptId());
        baregivedtl.setProcUser(userData.getEmpNo());
        baregivedtl.setProcIp(userData.getLoginIP());

        baregivedtlDao.updateBaregivedtlForPaymentReceive(baregivedtl);
        log.debug("Update BAREGIVEDTL Finished ...");
        // ]
    }

    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 年金應收收回處理作業-退匯收回註銷
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlForCancelReceive(UserBean userData, String apNo, String seqNo, String oriIssuYm, String payYm) {
        // 更新資料到 改匯檔
        // [
        log.debug("Start Update BAREGIVEDTL ...");
        Baregivedtl baregivedtl = new Baregivedtl();
        baregivedtl.setApNo(apNo);
        baregivedtl.setSeqNo(seqNo);
        baregivedtl.setOriIssuYm(oriIssuYm);
        baregivedtl.setPayYm(payYm);
        baregivedtl.setProcDeptId(userData.getDeptId());
        baregivedtl.setProcUser(userData.getEmpNo());
        baregivedtl.setProcIp(userData.getLoginIP());

        baregivedtlDao.updateBaregivedtlForCancelReceive(baregivedtl);
        log.debug("Update BAREGIVEDTL Finished ...");
        // ]
    }

    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 年金應收收回處理作業-退匯收回
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacForPaymentReceive(UserBean userData, String apNo, String seqNo, String issuKind, String oriIssuYm, String payYm, BigDecimal remitAmt) {
        // 更新資料到 退匯檔
        // [
        log.debug("Start Update BAPFLBAC ...");
        Bapflbac bapflbac = new Bapflbac();
        bapflbac.setApNo(apNo);
        bapflbac.setSeqNo(seqNo);
        bapflbac.setIssuKind(issuKind);
        bapflbac.setOriIssuYm(oriIssuYm);
        bapflbac.setPayYm(payYm);
        bapflbac.setProcDeptId(userData.getDeptId());
        bapflbac.setProcUser(userData.getEmpNo());
        bapflbac.setProcIp(userData.getLoginIP());
        bapflbacDao.updateBapflbacForPaymentReceive(bapflbac);
        log.debug("Update BAPFLBAC Finished ...");
        // ]
    }

    /**
     * 更新 重新查核失能程度檔(<code>BARECHECK</code>) 資料
     * 
     * @param barecheck 重新查核失能程度檔
     */
    public void updateBareCheckData(DisabledApplicationDataUpdateBareCheckCase caseData) {
        // 更新資料到 重新查核失能程度檔
        // [
        log.debug("Start Update BARECHECK ...");
        Barecheck barecheck = new Barecheck();
        BeanUtility.copyProperties(barecheck, caseData);
        barecheckDao.updateBareCheckData(barecheck);
        log.debug("Update BARECHECK Finished ...");
        // ]
    }

    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 年金應收收回處理作業-退匯收回註銷
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacForCancelReceive(UserBean userData, String apNo, String seqNo, String oriIssuYm, String payYm, BigDecimal remitAmt) {
        // 更新資料到 退匯檔
        // [
        log.debug("Start Update BAPFLBAC ...");
        Bapflbac bapflbac = new Bapflbac();
        bapflbac.setApNo(apNo);
        bapflbac.setSeqNo(seqNo);
        bapflbac.setOriIssuYm(oriIssuYm);
        bapflbac.setPayYm(payYm);
        bapflbac.setProcDeptId(userData.getDeptId());
        bapflbac.setProcUser(userData.getEmpNo());
        bapflbac.setProcIp(userData.getLoginIP());
        bapflbacDao.updateBapflbacForPaymentReceive(bapflbac);
        bapflbacDao.updateBapflbacForCancelReceive(bapflbac);
        log.debug("Update BAPFLBAC Finished ...");
        // ]
    }

    /**
     * 更新 退現資料檔(<code>BAACPDTL</code>) 年金應收收回處理作業-退現收回註銷
     * 
     * @param Baacpdtl 退匯檔
     */
    public void updateBaacpdtlForCancelCashReceive(BigDecimal baunacpdtlId, BigDecimal baacpdtlId) {
        // 更新資料到 退匯檔
        // [
        log.debug("Start Update BAACPDTL ...");
        baacpdtlDao.updateBaacpdtlForCancelCashReceive(baunacpdtlId, baacpdtlId);
        log.debug("Update BAACPDTL Finished ...");
        // ]
    }

    /**
     * 更新 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料
     * 
     * @param baunacpdtl 退現資料檔
     */
    public void updateBaunacpdtlForCancelCashReceive(BigDecimal baunacpdtlId, BigDecimal recRem) {
        // 更新資料到 應收帳務明細檔
        Baunacpdtl baunacpdtl = new Baunacpdtl();
        baunacpdtl.setBaunacpdtlId(baunacpdtlId);
        baunacpdtl.setRecRem(recRem);
        // [
        log.debug("Start Update BAUNACPDTL ...");
        baunacpdtlDao.updateBaunacpdtlForCancelCashReceive(baunacpdtl);
        log.debug("Update BAUNACPDTL Finished ...");
        // ]
    }

    /**
     * 新增(<code>BAACPDTL</code>) 資料 for 1. 老年年金應收收回處理作業 - 退現 and 列印作業 - 應收收回相關報表 - 整批收回核定清單
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataForBatchPaymentReceiveData(UserBean userData, OldAgePaymentReceiveDataCase paymentReceiveData, AccountsReceivableDataCase accountsReceivableData, BatchPaymentReceiveDataCase cashReceiveData, String chkDate1, String typeMk) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAACPDTL ...");
        Baacpdtl baacpdtl = new Baacpdtl();
        BeanUtility.copyProperties(baacpdtl, accountsReceivableData);

        baacpdtl.setEmpNo(userData.getEmpNo()); // 處理人員

        baacpdtl.setInsKd(cashReceiveData.getInsKd());
        baacpdtl.setBli_Account_Code(cashReceiveData.getBli_Account_Code());
        baacpdtl.setBookedBook(cashReceiveData.getBookedBook());
        baacpdtl.setBkAccountDt(cashReceiveData.getBkAccountDt());
        baacpdtl.setBatchNo(cashReceiveData.getBatchNo());
        baacpdtl.setSerialNo(cashReceiveData.getSerialNo());
        baacpdtl.setCash_Amt(cashReceiveData.getCash_Amt());
        baacpdtl.setDivSeq(cashReceiveData.getDivSeq());
        baacpdtl.setIndex_No(cashReceiveData.getIndex_No());
        baacpdtl.setAcpDate(chkDate1);
        baacpdtl.setDisPayKind(cashReceiveData.getPayKind());
        baacpdtl.setRecAmt(cashReceiveData.getRecAmt());
        baacpdtl.setIssuYm(cashReceiveData.getIssuYm());

        String sAdWkMk = null;
        String sNlWkMk = null;
        if (accountsReceivableData.getAdWkMk() == null || accountsReceivableData.getAdWkMk().equals("")) {
            sAdWkMk = "1";
        }
        else {
            sAdWkMk = accountsReceivableData.getAdWkMk();
        }

        if (accountsReceivableData.getNlWkMk() == null || accountsReceivableData.getNlWkMk().equals("")) {
            sNlWkMk = "1";
        }
        else {
            sNlWkMk = accountsReceivableData.getNlWkMk();
        }

        baacpdtl.setNlWkMk(sNlWkMk);
        baacpdtl.setAdWkMk(sAdWkMk);
        baacpdtl.setNaChgMk(accountsReceivableData.getNaChgMk());
        baacpdtl.setSts(accountsReceivableData.getSts());
        baacpdtl.setTypeMk(typeMk);

        baacpdtlDao.insertDataForBatchPaymentReceiveData(baacpdtl);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(baacpdtl);
        }
        log.debug("Insert BAACPDTL Finished ...");
        // ]
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-現金收回 and 列印作業 - 應收收回相關報表 - 整批收回核定清單
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<OldAgePaymentReceiveDataCase> selectBatchPaymentReceiveDataBy(String apNo, String payCode, String chkDate) {
        List<OldAgePaymentReceiveDataCase> caseData = new ArrayList<OldAgePaymentReceiveDataCase>();
        List<Baappbase> list = baappbaseDao.selectBatchPaymentReceiveDataBy(apNo, payCode, chkDate);

        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = (Baappbase) list.get(i);
            OldAgePaymentReceiveDataCase caseObj = new OldAgePaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, baappbase);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR 整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @return <code>Baacpdtl</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectBaacpdtlDataBy(String apNo, String seqNo, String acpDate) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Baacpdtl> list = baacpdtlDao.selectBaacpdtlDataBy(apNo, seqNo, acpDate);

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl baacpdtl = (Baacpdtl) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, baacpdtl);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR 更正作業 - 應收收回處理作業 - 老年年金應收收回處理
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @return <code>Baacpdtl</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectBaacpdtlDataForPaymentReceiveBy(String apNo, String seqNo, BigDecimal baunacpdtlId, String batchNo, String bkAccountDt, String bli_Account_Code, String bookedBook, BigDecimal cash_Amt, String divSeq,
                    String index_No, String serialNo) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Baacpdtl> list = baacpdtlDao.selectBaacpdtlDataForPaymentReceiveBy(apNo, seqNo, baunacpdtlId, batchNo, bkAccountDt, bli_Account_Code, bookedBook, cash_Amt, divSeq, index_No, serialNo);

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl baacpdtl = (Baacpdtl) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, baacpdtl);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （失能）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @return <code>Baacpdtl</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectKBaacpdtlDataBy(String apNo, String seqNo, String acpDate) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Baacpdtl> list = baacpdtlDao.selectKBaacpdtlDataBy(apNo, seqNo, acpDate);

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl baacpdtl = (Baacpdtl) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, baacpdtl);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （遺屬）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @return <code>Baacpdtl</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectSBaacpdtlDataBy(String apNo, String seqNo, String acpDate) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Baacpdtl> list = baacpdtlDao.selectSBaacpdtlDataBy(apNo, seqNo, acpDate);

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl baacpdtl = (Baacpdtl) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, baacpdtl);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for 整批收回核定清單-應收已收核定清單
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataToBapayrptrecordForBatchPaymentReceive(UserBean userData, OldAgePaymentReceiveDataCase paymentReceiveData, BatchPaymentReceiveDataCase accountsReceivableData, BatchPaymentReceiveDataCase cashReceiveData, String chkDate1) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAPAYRPTRECORD ...");
        // Baacpdtl baacpdtl = new Baacpdtl();
        Bapayrptrecord bapayrptrecord = new Bapayrptrecord();
        // BeanUtility.copyProperties(baacpdtl, accountsReceivableData);
        BeanUtility.copyProperties(bapayrptrecord, accountsReceivableData);
        /**
         * baacpdtl.setEmpNo(userData.getEmpNo()); // 處理人員 baacpdtl.setInsKd(cashReceiveData.getInsKd()); baacpdtl.setBli_Account_Code(cashReceiveData.getBli_Account_Code()); baacpdtl.setBookedBook(cashReceiveData.getBookedBook());
         * baacpdtl.setBkAccountDt(cashReceiveData.getBkAccountDt()); baacpdtl.setBatchNo(cashReceiveData.getBatchNo()); baacpdtl.setSerialNo(cashReceiveData.getSerialNo()); baacpdtl.setCash_Amt(cashReceiveData.getCash_Amt());
         * baacpdtl.setDivSeq(cashReceiveData.getDivSeq()); baacpdtl.setIndex_No(cashReceiveData.getIndex_No()); baacpdtl.setDisPayKind(paymentReceiveData.getPayKind());
         */

        // bapayrptrecord.setPayDate(DateUtility.changeDateType(paymentReceiveData.getBkAccountDt()));
        // bapayrptrecord.setChkDate(DateUtility.changeDateType(paymentReceiveData.getBkAccountDt()));
        bapayrptrecord.setPayDate(chkDate1);
        bapayrptrecord.setChkDate(chkDate1);
        bapayrptrecord.setPayCode(paymentReceiveData.getApNo().substring(0, 1));
        bapayrptrecord.setIssuYm(paymentReceiveData.getIssuYm());
        bapayrptrecord.setPayKind(paymentReceiveData.getPayKind());

        bapayrptrecordDao.insertDataForBatchPaymentReceiveData(bapayrptrecord);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptrecord);
        }
        log.debug("Insert BAPAYRPTRECORD Finished ...");
        // ]
    }

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for 更正作業 - 應收收回處理作業 - 老年年金應收收回處理
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataToBapayrptrecordForPaymentReceive(UserBean userData, OldAgePaymentReceiveDataCase paymentReceiveData, BatchPaymentReceiveDataCase accountsReceivableData, BatchPaymentReceiveDataCase cashReceiveData, String chkDate1) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAPAYRPTRECORD ...");
        // Baacpdtl baacpdtl = new Baacpdtl();
        Bapayrptrecord bapayrptrecord = new Bapayrptrecord();
        // BeanUtility.copyProperties(baacpdtl, accountsReceivableData);
        BeanUtility.copyProperties(bapayrptrecord, accountsReceivableData);

        // bapayrptrecord.setPayDate(DateUtility.changeDateType(paymentReceiveData.getBkAccountDt()));
        // bapayrptrecord.setChkDate(DateUtility.changeDateType(paymentReceiveData.getBkAccountDt()));
        bapayrptrecord.setPayDate(chkDate1);
        bapayrptrecord.setChkDate(chkDate1);
        bapayrptrecord.setPayCode(paymentReceiveData.getApNo().substring(0, 1));
        bapayrptrecord.setIssuYm(paymentReceiveData.getIssuYm());
        bapayrptrecord.setPayKind(paymentReceiveData.getPayKind());

        bapayrptrecord.setRptRows("N");

        bapayrptrecordDao.insertDataForPaymentReceiveData(bapayrptrecord);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptrecord);
        }
        log.debug("Insert BAPAYRPTRECORD Finished ...");
        // ]
    }

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for （失能）整批收回核定清單-應收已收核定清單
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertKDataToBapayrptrecordForBatchPaymentReceive(UserBean userData, OldAgePaymentReceiveDataCase paymentReceiveData, BatchPaymentReceiveDataCase accountsReceivableData, BatchPaymentReceiveDataCase cashReceiveData, String chkDate1) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAPAYRPTRECORD ...");
        // Baacpdtl baacpdtl = new Baacpdtl();
        Bapayrptrecord bapayrptrecord = new Bapayrptrecord();
        // BeanUtility.copyProperties(baacpdtl, accountsReceivableData);
        BeanUtility.copyProperties(bapayrptrecord, accountsReceivableData);
        /**
         * baacpdtl.setEmpNo(userData.getEmpNo()); // 處理人員 baacpdtl.setInsKd(cashReceiveData.getInsKd()); baacpdtl.setBli_Account_Code(cashReceiveData.getBli_Account_Code()); baacpdtl.setBookedBook(cashReceiveData.getBookedBook());
         * baacpdtl.setBkAccountDt(cashReceiveData.getBkAccountDt()); baacpdtl.setBatchNo(cashReceiveData.getBatchNo()); baacpdtl.setSerialNo(cashReceiveData.getSerialNo()); baacpdtl.setCash_Amt(cashReceiveData.getCash_Amt());
         * baacpdtl.setDivSeq(cashReceiveData.getDivSeq()); baacpdtl.setIndex_No(cashReceiveData.getIndex_No()); baacpdtl.setDisPayKind(paymentReceiveData.getPayKind());
         */

        // bapayrptrecord.setPayDate(DateUtility.changeDateType(paymentReceiveData.getBkAccountDt()));
        // bapayrptrecord.setChkDate(DateUtility.changeDateType(paymentReceiveData.getBkAccountDt()));
        bapayrptrecord.setPayDate(chkDate1);
        bapayrptrecord.setChkDate(chkDate1);
        bapayrptrecord.setPayCode(paymentReceiveData.getApNo().substring(0, 1));
        bapayrptrecord.setIssuYm(paymentReceiveData.getIssuYm());
        bapayrptrecord.setPayKind(paymentReceiveData.getPayKind());

        bapayrptrecordDao.insertKDataForBatchPaymentReceiveData(bapayrptrecord);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptrecord);
        }
        log.debug("Insert BAPAYRPTRECORD Finished ...");
        // ]
    }

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for （遺屬）整批收回核定清單-應收已收核定清單
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertSDataToBapayrptrecordForBatchPaymentReceive(UserBean userData, OldAgePaymentReceiveDataCase paymentReceiveData, BatchPaymentReceiveDataCase accountsReceivableData, BatchPaymentReceiveDataCase cashReceiveData, String chkDate1) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAPAYRPTRECORD ...");
        // Baacpdtl baacpdtl = new Baacpdtl();
        Bapayrptrecord bapayrptrecord = new Bapayrptrecord();
        // BeanUtility.copyProperties(baacpdtl, accountsReceivableData);
        BeanUtility.copyProperties(bapayrptrecord, accountsReceivableData);
        /**
         * baacpdtl.setEmpNo(userData.getEmpNo()); // 處理人員 baacpdtl.setInsKd(cashReceiveData.getInsKd()); baacpdtl.setBli_Account_Code(cashReceiveData.getBli_Account_Code()); baacpdtl.setBookedBook(cashReceiveData.getBookedBook());
         * baacpdtl.setBkAccountDt(cashReceiveData.getBkAccountDt()); baacpdtl.setBatchNo(cashReceiveData.getBatchNo()); baacpdtl.setSerialNo(cashReceiveData.getSerialNo()); baacpdtl.setCash_Amt(cashReceiveData.getCash_Amt());
         * baacpdtl.setDivSeq(cashReceiveData.getDivSeq()); baacpdtl.setIndex_No(cashReceiveData.getIndex_No()); baacpdtl.setDisPayKind(paymentReceiveData.getPayKind());
         */

        // bapayrptrecord.setPayDate(DateUtility.changeDateType(paymentReceiveData.getBkAccountDt()));
        // bapayrptrecord.setChkDate(DateUtility.changeDateType(paymentReceiveData.getBkAccountDt()));
        bapayrptrecord.setPayDate(chkDate1);
        bapayrptrecord.setChkDate(chkDate1);
        bapayrptrecord.setPayCode(paymentReceiveData.getApNo().substring(0, 1));
        bapayrptrecord.setIssuYm(paymentReceiveData.getIssuYm());
        bapayrptrecord.setPayKind(paymentReceiveData.getPayKind());

        bapayrptrecordDao.insertSDataForBatchPaymentReceiveData(bapayrptrecord);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptrecord);
        }
        log.debug("Insert BAPAYRPTRECORD Finished ...");
        // ]
    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR 整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 受理編號
     * @return <code>Bapayrptrecord</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectBapayrptrecordDataForRptsumBy(String payCode, String chkDate) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptrecord> list = bapayrptrecordDao.selectBapayrptrecordDataForRptsumBy(payCode, chkDate);

        for (int i = 0; i < list.size(); i++) {
            Bapayrptrecord bapayrptrecord = (Bapayrptrecord) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, bapayrptrecord);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR 整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 受理編號
     * @return <code>Bapayrptrecord</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectBapayrptrecordDataToRptsumForPaymentReceiveBy(String payCode, String sApNo) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptrecord> list = bapayrptrecordDao.selectBapayrptrecordDataToRptsumForPaymentReceiveBy(payCode, sApNo);

        for (int i = 0; i < list.size(); i++) {
            Bapayrptrecord bapayrptrecord = (Bapayrptrecord) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, bapayrptrecord);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR （失能）整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 受理編號
     * @return <code>Bapayrptrecord</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectKBapayrptrecordDataForRptsumBy(String payCode, String chkDate) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptrecord> list = bapayrptrecordDao.selectKBapayrptrecordDataForRptsumBy(payCode, chkDate);

        for (int i = 0; i < list.size(); i++) {
            Bapayrptrecord bapayrptrecord = (Bapayrptrecord) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, bapayrptrecord);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR （遺屬）整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 受理編號
     * @return <code>Bapayrptrecord</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectSBapayrptrecordDataForRptsumBy(String payCode, String chkDate) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptrecord> list = bapayrptrecordDao.selectSBapayrptrecordDataForRptsumBy(payCode, chkDate);

        for (int i = 0; i < list.size(); i++) {
            Bapayrptrecord bapayrptrecord = (Bapayrptrecord) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, bapayrptrecord);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for 整批收回核定清單-應收已收核定清單
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public void insertDataToBapayrptsumForBatchPaymentReceive(UserBean userData, BatchPaymentReceiveDataCase accountsReceivableData, String payCode) {

        // 新增資料到 給付主檔
        log.debug("Start Insert BAPAYRPTSUM ...");
        Bapayrptsum bapayrptsum = new Bapayrptsum();
        BeanUtility.copyProperties(bapayrptsum, accountsReceivableData);

        bapayrptsum.setPayCode(payCode);

        bapayrptsumDao.insertDataForBatchPaymentReceiveData(bapayrptsum);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptsum);
        }
        log.debug("Insert BAPAYRPTSUM Finished ...");
    }

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for 整批收回核定清單-應收已收核定清單
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public void insertDataToBapayrptsumForPaymentReceive(UserBean userData, BatchPaymentReceiveDataCase accountsReceivableData, String payCode) {

        // 新增資料到 給付主檔
        log.debug("Start Insert BAPAYRPTSUM ...");
        Bapayrptsum bapayrptsum = new Bapayrptsum();
        BeanUtility.copyProperties(bapayrptsum, accountsReceivableData);

        bapayrptsum.setPayCode(payCode);
        bapayrptsum.setRptNote("N");

        bapayrptsumDao.insertDataForPaymentReceiveData(bapayrptsum);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptsum);
        }
        log.debug("Insert BAPAYRPTSUM Finished ...");
    }

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for （失能）整批收回核定清單-應收已收核定清單
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public void insertKDataToBapayrptsumForBatchPaymentReceive(UserBean userData, BatchPaymentReceiveDataCase accountsReceivableData, String payCode) {

        // 新增資料到 給付主檔
        log.debug("Start Insert BAPAYRPTSUM ...");
        Bapayrptsum bapayrptsum = new Bapayrptsum();
        BeanUtility.copyProperties(bapayrptsum, accountsReceivableData);

        bapayrptsum.setPayCode(payCode);

        String sAdWkMk = null;
        String sNlWkMk = null;
        if (accountsReceivableData.getAdWkMk() == null || accountsReceivableData.getAdWkMk().equals("")) {
            sAdWkMk = "1";
        }
        else {
            sAdWkMk = accountsReceivableData.getAdWkMk();
        }

        if (accountsReceivableData.getNlWkMk() == null || accountsReceivableData.getNlWkMk().equals("")) {
            sNlWkMk = "1";
        }
        else {
            sNlWkMk = accountsReceivableData.getNlWkMk();
        }

        bapayrptsum.setNlWkMk(sNlWkMk);
        bapayrptsum.setAdWkMk(sAdWkMk);
        bapayrptsum.setNaChgMk(accountsReceivableData.getNaChgMk());

        bapayrptsumDao.insertKDataForBatchPaymentReceiveData(bapayrptsum);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptsum);
        }
        log.debug("Insert BAPAYRPTSUM Finished ...");
    }

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for （遺屬）整批收回核定清單-應收已收核定清單
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public void insertSDataToBapayrptsumForBatchPaymentReceive(UserBean userData, BatchPaymentReceiveDataCase accountsReceivableData, String payCode) {

        // 新增資料到 給付主檔
        log.debug("Start Insert BAPAYRPTSUM ...");
        Bapayrptsum bapayrptsum = new Bapayrptsum();
        BeanUtility.copyProperties(bapayrptsum, accountsReceivableData);

        bapayrptsum.setPayCode(payCode);

        bapayrptsumDao.insertSDataForBatchPaymentReceiveData(bapayrptsum);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptsum);
        }
        log.debug("Insert BAPAYRPTSUM Finished ...");
    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR 整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 受理編號
     * @param CHKDATE
     * @return <code>Bapayrptrecord</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectBapayrptsumDataForRptaccountBy(String payCode, String chkDate) {
        BigDecimal v_tmpAMT = new BigDecimal(0);

        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptsum> list = bapayrptsumDao.selectBapayrptsumDataForRptaccountBy(payCode, chkDate);

        for (int i = 0; i < list.size(); i++) {

            for (int j = 1; j < 3; j++) {

                Bapayrptsum bapayrptsum = (Bapayrptsum) list.get(i);
                BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
                BeanUtility.copyProperties(caseObj, bapayrptsum);

                caseObj.setAccountSeq(new BigDecimal(1));
                if (j == 1) {
                    caseObj.setAccountSeq(new BigDecimal(0));
                    caseObj.setAccountNo("236X121 19 5");
                    caseObj.setAccountName("暫收及待結轉帳項－普現－暫收款");
                    caseObj.setPayAmt(bapayrptsum.getIssueAmt());
                    v_tmpAMT = bapayrptsum.getIssueAmt();

                    // 回寫PFM&PFD時所需資訊 Start
                    caseObj.setAccountMemo("");

                    if (bapayrptsum.getSts().equals("1")) {
                        caseObj.setOppAccountNo("113X121 11111 8");
                        caseObj.setOppAccountName("其他應收款－普通－老年給付（年金）");
                    }
                    else if (bapayrptsum.getSts().equals("2")) {
                        caseObj.setOppAccountNo("1723121 11111 9");
                        caseObj.setOppAccountName("催收款項－普通－老年給付（年金）");
                    }
                    else if (bapayrptsum.getSts().equals("3")) {
                        caseObj.setOppAccountNo("422D121 1 A");
                        caseObj.setOppAccountName("收回呆帳－勞給普－現－收回呆帳");
                    }

                    caseObj.setActTitleApCode("GGG");
                    caseObj.setDataCd("1");
                    caseObj.setDecideAmt(bapayrptsum.getIssueAmt());
                    caseObj.setCashAmt(new BigDecimal(0));
                    caseObj.setCutAmt(new BigDecimal(0));
                    caseObj.setAcceptPayType("1");
                    caseObj.setCnt(bapayrptsum.getDataAmt());
                    caseObj.setAccountTopfMk("Y");
                    // 回寫PFM&PFD時所需資訊 End

                }
                else if (j == 2) {
                    v_tmpAMT = bapayrptsum.getPayAmt();

                    if (bapayrptsum.getSts().equals("1")) {
                        caseObj.setAccountNo("113X121 11111 8");
                        caseObj.setAccountName("其他應收款－普通－老年給付（年金）");
                    }
                    else if (bapayrptsum.getSts().equals("2")) {
                        caseObj.setAccountNo("1723121 11111 9");
                        caseObj.setAccountName("催收款項－普通－老年給付（年金）");
                    }
                    else if (bapayrptsum.getSts().equals("3")) {
                        caseObj.setAccountNo("422D121 1 A");
                        caseObj.setAccountName("收回呆帳－勞給普－現－收回呆帳");
                    }

                    caseObj.setPayAmt(bapayrptsum.getPayAmt());

                    // 回寫PFM&PFD時所需資訊 Start
                    caseObj.setAccountMemo("");
                    caseObj.setOppAccountNo("");
                    caseObj.setOppAccountName("");
                    caseObj.setActTitleApCode("");
                    caseObj.setDataCd("");
                    caseObj.setDecideAmt(new BigDecimal(0));
                    caseObj.setCashAmt(new BigDecimal(0));
                    caseObj.setCutAmt(new BigDecimal(0));
                    caseObj.setAcceptPayType("");
                    caseObj.setCnt(new BigDecimal(0));
                    caseObj.setAccountTopfMk("N");
                    // 回寫PFM&PFD時所需資訊 End

                }
                if (v_tmpAMT.intValue() > 0) {
                    caseData.add(caseObj);
                }
            }

        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR 整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 受理編號
     * @param CHKDATE
     * @return <code>Bapayrptrecord</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectBapayrptsumDataToRptaccountForPaymentReceiveBy(String payCode, String sApNo) {
        BigDecimal v_tmpAMT = new BigDecimal(0);

        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptsum> list = bapayrptsumDao.selectBapayrptsumDataToRptaccountForPaymentReceiveBy(payCode, sApNo);

        for (int i = 0; i < list.size(); i++) {

            for (int j = 1; j < 3; j++) {

                Bapayrptsum bapayrptsum = (Bapayrptsum) list.get(i);
                BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
                BeanUtility.copyProperties(caseObj, bapayrptsum);

                caseObj.setAccountSeq(new BigDecimal(1));
                if (j == 1) {
                    caseObj.setAccountSeq(new BigDecimal(0));
                    caseObj.setAccountNo("236X121 19 5");
                    caseObj.setAccountName("暫收及待結轉帳項－普現－暫收款");
                    caseObj.setPayAmt(bapayrptsum.getIssueAmt());
                    v_tmpAMT = bapayrptsum.getIssueAmt();

                    // 回寫PFM&PFD時所需資訊 Start
                    caseObj.setAccountMemo("");

                    if (bapayrptsum.getSts().equals("1")) {
                        caseObj.setOppAccountNo("113X121 11111 8");
                        caseObj.setOppAccountName("其他應收款－普通－老年給付（年金）");
                    }
                    else if (bapayrptsum.getSts().equals("2")) {
                        caseObj.setOppAccountNo("1723121 11111 9");
                        caseObj.setOppAccountName("催收款項－普通－老年給付（年金）");
                    }
                    else if (bapayrptsum.getSts().equals("3")) {
                        caseObj.setOppAccountNo("422D121 1 A");
                        caseObj.setOppAccountName("收回呆帳－勞給普－現－收回呆帳");
                    }

                    caseObj.setActTitleApCode("GGG");
                    caseObj.setDataCd("1");
                    caseObj.setDecideAmt(bapayrptsum.getIssueAmt());
                    caseObj.setCashAmt(new BigDecimal(0));
                    caseObj.setCutAmt(new BigDecimal(0));
                    caseObj.setAcceptPayType("1");
                    caseObj.setCnt(bapayrptsum.getDataAmt());
                    caseObj.setAccountTopfMk("Y");
                    // 回寫PFM&PFD時所需資訊 End

                }
                else if (j == 2) {
                    v_tmpAMT = bapayrptsum.getPayAmt();

                    if (bapayrptsum.getSts().equals("1")) {
                        caseObj.setAccountNo("113X121 11111 8");
                        caseObj.setAccountName("其他應收款－普通－老年給付（年金）");
                    }
                    else if (bapayrptsum.getSts().equals("2")) {
                        caseObj.setAccountNo("1723121 11111 9");
                        caseObj.setAccountName("催收款項－普通－老年給付（年金）");
                    }
                    else if (bapayrptsum.getSts().equals("3")) {
                        caseObj.setAccountNo("422D121 1 A");
                        caseObj.setAccountName("收回呆帳－勞給普－現－收回呆帳");
                    }

                    caseObj.setPayAmt(bapayrptsum.getPayAmt());

                    // 回寫PFM&PFD時所需資訊 Start
                    caseObj.setAccountMemo("");
                    caseObj.setOppAccountNo("");
                    caseObj.setOppAccountName("");
                    caseObj.setActTitleApCode("");
                    caseObj.setDataCd("");
                    caseObj.setDecideAmt(new BigDecimal(0));
                    caseObj.setCashAmt(new BigDecimal(0));
                    caseObj.setCutAmt(new BigDecimal(0));
                    caseObj.setAcceptPayType("");
                    caseObj.setCnt(new BigDecimal(0));
                    caseObj.setAccountTopfMk("N");
                    // 回寫PFM&PFD時所需資訊 End

                }
                if (v_tmpAMT.intValue() > 0) {
                    caseData.add(caseObj);
                }
            }

        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR （失能）整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 受理編號
     * @param CHKDATE
     * @return <code>Bapayrptrecord</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectKBapayrptsumDataForRptaccountBy(String payCode, String chkDate) {
        BigDecimal v_tmpAMT = new BigDecimal(0);

        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptrecord> list = bapayrptrecordDao.selectKBapayrptrecordDataForRptaccountBy(payCode, chkDate);

        for (int i = 0; i < list.size(); i++) {

            for (int j = 1; j < 5; j++) {

                Bapayrptrecord bapayrptsum = (Bapayrptrecord) list.get(i);
                BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
                BeanUtility.copyProperties(caseObj, bapayrptsum);

                caseObj.setAccountSeq(new BigDecimal(1));
                if (j == 1) {
                    caseObj.setAccountSeq(new BigDecimal(0));
                    v_tmpAMT = caseObj.getIssueAmtA();
                    caseObj.setPayAmt(v_tmpAMT);

                    if (bapayrptsum.getNlWkMk().equals("1") || bapayrptsum.getNlWkMk() == null) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("236X121 19 5");
                            caseObj.setAccountName("暫收及待結轉帳項－普現－暫收款");
                            caseObj.setOppAccountNo("113X121 11102 7");
                            caseObj.setOppAccountName("其他應收款－普通－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("236X121 19 5");
                            caseObj.setAccountName("暫收及待結轉帳項－普現－暫收款");
                            caseObj.setOppAccountNo("1723121 11102 8");
                            caseObj.setOppAccountName("催收款項－普通－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("236X121 19 5");
                            caseObj.setAccountName("暫收及待結轉帳項－普現－暫收款");
                            caseObj.setOppAccountNo("422D121 1 A");
                            caseObj.setOppAccountName("收回呆帳－勞給普－現－收回呆帳");
                        }

                    }
                    else if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("1")) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("113X122 11102 A");
                            caseObj.setOppAccountName("其他應收款－職災－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("1723122 11102 1");
                            caseObj.setOppAccountName("催收款項－職災－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("422D122 1 4");
                            caseObj.setOppAccountName("收回呆帳－勞給職－現－收回呆帳");
                        }

                    }
                    else if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("2")) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("113X122 12102 1");
                            caseObj.setOppAccountName("其他應收款－加職－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("1723122 12102 2");
                            caseObj.setOppAccountName("催收款項－加職－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("422D122 1 4");
                            caseObj.setOppAccountName("收回呆帳－勞給職－現－收回呆帳");
                        }

                    }

                    // 回寫PFM&PFD時所需資訊 Start
                    caseObj.setAccountMemo("");
                    caseObj.setActTitleApCode("GGG");
                    caseObj.setDataCd("1");
                    caseObj.setDecideAmt(bapayrptsum.getIssueAmtA());
                    caseObj.setCashAmt(new BigDecimal(0));
                    caseObj.setCutAmt(new BigDecimal(0));
                    caseObj.setAcceptPayType("1");
                    caseObj.setCnt(bapayrptsum.getDataAmt());
                    caseObj.setAccountTopfMk("Y");
                    // 回寫PFM&PFD時所需資訊 End

                }
                else if (j == 2) {
                    caseObj.setAccountSeq(new BigDecimal(0));
                    v_tmpAMT = caseObj.getIssueAmtB();
                    caseObj.setPayAmt(v_tmpAMT);

                    if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("1")) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("113X122 11101 2");
                            caseObj.setAccountName("其他應收款－職災－失能給付（補償金）");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("1723122 11101 3");
                            caseObj.setAccountName("催收款項－職災－失能給付（補償金）");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("422D122 1 4");
                            caseObj.setAccountName("收回呆帳－勞給職－現－收回呆帳");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }

                    }
                    else if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("2")) {
                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("113X122 12101 3");
                            caseObj.setAccountName("其他應收款－加職－失能給付（補償金）");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("1723122 12101 4");
                            caseObj.setAccountName("催收款項－加職－失能給付（補償金）");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("422D122 1 4");
                            caseObj.setAccountName("收回呆帳－勞給職－現－收回呆帳");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }
                    }

                    // caseObj.setPayAmt(bapayrptsum.getPayAmt());

                    // 回寫PFM&PFD時所需資訊 Start
                    caseObj.setAccountMemo("");
                    caseObj.setActTitleApCode("");
                    caseObj.setDataCd("");
                    caseObj.setDecideAmt(bapayrptsum.getIssueAmtB());
                    caseObj.setCashAmt(new BigDecimal(0));
                    caseObj.setCutAmt(new BigDecimal(0));
                    caseObj.setAcceptPayType("");
                    caseObj.setCnt(new BigDecimal(0));
                    caseObj.setAccountTopfMk("N");
                    // 回寫PFM&PFD時所需資訊 End

                }
                else if (j == 3) {
                    // caseObj.setAccountSeq(new BigDecimal(0));
                    v_tmpAMT = caseObj.getPayAmtA();
                    caseObj.setPayAmt(v_tmpAMT);

                    if (bapayrptsum.getNlWkMk().equals("1") || bapayrptsum.getNlWkMk() == null) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("113X121 11102 7");
                            caseObj.setAccountName("其他應收款－普通－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("1723121 11102 8");
                            caseObj.setAccountName("催收款項－普通－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("422D121 1 A");
                            caseObj.setAccountName("收回呆帳－勞給普－現－收回呆帳");
                        }
                    }
                    else if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("1")) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("113X122 11102 A");
                            caseObj.setAccountName("其他應收款－職災－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("1723122 11102 1");
                            caseObj.setAccountName("催收款項－職災－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("422D122 1 4");
                            caseObj.setAccountName("收回呆帳－勞給職－現－收回呆帳");
                        }
                    }
                    else if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("2")) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("113X122 12102 1");
                            caseObj.setAccountName("其他應收款－加職－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("1723122 12102 2");
                            caseObj.setAccountName("催收款項－加職－失能給付（年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("422D122 1 4");
                            caseObj.setAccountName("收回呆帳－勞給職－現－收回呆帳");
                        }
                    }

                    caseObj.setPayAmt(bapayrptsum.getPayAmtA());

                    // 回寫PFM&PFD時所需資訊 Start
                    caseObj.setAccountMemo("");
                    caseObj.setOppAccountNo("");
                    caseObj.setOppAccountName("");
                    caseObj.setActTitleApCode("");
                    caseObj.setDataCd("");
                    caseObj.setDecideAmt(new BigDecimal(0));
                    caseObj.setCashAmt(new BigDecimal(0));
                    caseObj.setCutAmt(new BigDecimal(0));
                    caseObj.setAcceptPayType("");
                    caseObj.setCnt(new BigDecimal(0));
                    caseObj.setAccountTopfMk("N");
                    // 回寫PFM&PFD時所需資訊 End
                }
                else if (j == 4) {
                    // caseObj.setAccountSeq(new BigDecimal(0));
                    v_tmpAMT = caseObj.getPayAmtB();
                    caseObj.setPayAmt(v_tmpAMT);

                    if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("1")) {
                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("113X122 11101 2");
                            caseObj.setAccountName("其他應收款－職災－失能給付（補償金）");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("1723122 11101 3");
                            caseObj.setAccountName("催收款項－職災－失能給付（補償金）");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("422D122 1 4");
                            caseObj.setAccountName("收回呆帳－勞給職－現－收回呆帳");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }
                    }
                    else if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("2")) {
                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("113X122 12101 3");
                            caseObj.setAccountName("其他應收款－加職－失能給付（補償金）");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("1723122 12101 4");
                            caseObj.setAccountName("催收款項－加職－失能給付（補償金）");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("422D122 1 4");
                            caseObj.setAccountName("收回呆帳－勞給職－現－收回呆帳");
                            caseObj.setOppAccountNo("236X122 19 9");
                            caseObj.setOppAccountName("暫收及待結轉帳項－職現－暫收款");
                        }
                    }

                    caseObj.setPayAmt(bapayrptsum.getPayAmtB());

                    // 回寫PFM&PFD時所需資訊 Start
                    caseObj.setAccountMemo("");
                    caseObj.setOppAccountNo("");
                    caseObj.setOppAccountName("");
                    caseObj.setActTitleApCode("");
                    caseObj.setDataCd("");
                    caseObj.setDecideAmt(new BigDecimal(0));
                    caseObj.setCashAmt(new BigDecimal(0));
                    caseObj.setCutAmt(new BigDecimal(0));
                    caseObj.setAcceptPayType("");
                    caseObj.setCnt(new BigDecimal(0));
                    caseObj.setAccountTopfMk("N");
                    // 回寫PFM&PFD時所需資訊 End
                }

                if (v_tmpAMT.intValue() > 0) {
                    caseData.add(caseObj);
                }
            }

        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR （遺屬）整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 受理編號
     * @param CHKDATE
     * @return <code>Bapayrptrecord</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectSBapayrptsumDataForRptaccountBy(String payCode, String chkDate) {
        BigDecimal v_tmpAMT = new BigDecimal(0);

        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptsum> list = bapayrptsumDao.selectSBapayrptsumDataForRptaccountBy(payCode, chkDate);

        for (int i = 0; i < list.size(); i++) {

            for (int j = 1; j < 3; j++) {

                Bapayrptsum bapayrptsum = (Bapayrptsum) list.get(i);
                BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
                BeanUtility.copyProperties(caseObj, bapayrptsum);

                caseObj.setAccountSeq(new BigDecimal(1));
                if (j == 1) {
                    caseObj.setAccountSeq(new BigDecimal(0));
                    caseObj.setPayAmt(bapayrptsum.getIssueAmt());
                    v_tmpAMT = bapayrptsum.getIssueAmt();

                    if (bapayrptsum.getNlWkMk().equals("1") || bapayrptsum.getNlWkMk() == null) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("236X121 19 5");
                            caseObj.setAccountName("暫收及待結轉帳項－普現－暫收款");
                            caseObj.setOppAccountNo("113X121 11054 9");
                            caseObj.setOppAccountName("其他應收款－普通－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("236X121 19 5");
                            caseObj.setAccountName("暫收及待結轉帳項－普現－暫收款");
                            caseObj.setOppAccountNo("1723121 11054 0");
                            caseObj.setOppAccountName("催收款項－普通－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("236X121 19 5");
                            caseObj.setAccountName("暫收及待結轉帳項－普現－暫收款");
                            caseObj.setOppAccountNo("422D121 1 A");
                            caseObj.setOppAccountName("收回呆帳－勞給普－現－收回呆帳");
                        }

                    }
                    else if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("1")) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("113X122 11054 2");
                            caseObj.setOppAccountName("其他應收款－職災－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("1723122 11054 3");
                            caseObj.setOppAccountName("催收款項－職災－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("422D122 1 4");
                            caseObj.setOppAccountName("收回呆帳－勞給職－現－收回呆帳");
                        }

                    }
                    else if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("2")) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("113X122 12054 3");
                            caseObj.setOppAccountName("其他應收款－加職－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("1723122 12054 4");
                            caseObj.setOppAccountName("催收款項－加職－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("236X122 19 9");
                            caseObj.setAccountName("暫收及待結轉帳項－職現－暫收款");
                            caseObj.setOppAccountNo("422D122 1 4");
                            caseObj.setOppAccountName("收回呆帳－勞給職－現－收回呆帳");
                        }

                    }

                    // 回寫PFM&PFD時所需資訊 Start
                    caseObj.setAccountMemo("");
                    caseObj.setActTitleApCode("GGG");
                    caseObj.setDataCd("1");
                    caseObj.setDecideAmt(bapayrptsum.getIssueAmt());
                    caseObj.setCashAmt(new BigDecimal(0));
                    caseObj.setCutAmt(new BigDecimal(0));
                    caseObj.setAcceptPayType("1");
                    caseObj.setCnt(bapayrptsum.getDataAmt());
                    caseObj.setAccountTopfMk("Y");
                    // 回寫PFM&PFD時所需資訊 End

                }
                else if (j == 2) {
                    v_tmpAMT = bapayrptsum.getPayAmt();

                    if (bapayrptsum.getNlWkMk().equals("1") || bapayrptsum.getNlWkMk() == null) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("113X121 11054 9");
                            caseObj.setAccountName("其他應收款－普通－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("1723121 11054 0");
                            caseObj.setAccountName("催收款項－普通－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("422D121 1 A");
                            caseObj.setAccountName("收回呆帳－勞給普－現－收回呆帳");
                        }

                    }
                    else if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("1")) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("113X122 11054 2");
                            caseObj.setAccountName("其他應收款－職災－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("1723122 11054 3");
                            caseObj.setAccountName("催收款項－職災－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("422D122 1 4");
                            caseObj.setAccountName("收回呆帳－勞給職－現－收回呆帳");
                        }

                    }
                    else if (bapayrptsum.getNlWkMk().equals("2") && bapayrptsum.getAdWkMk().equals("2")) {

                        if (bapayrptsum.getSts().equals("1")) {
                            caseObj.setAccountNo("113X122 12054 3");
                            caseObj.setAccountName("其他應收款－加職－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("2")) {
                            caseObj.setAccountNo("1723122 12054 4");
                            caseObj.setAccountName("催收款項－加職－本人死亡給付（遺屬年金）");
                        }
                        else if (bapayrptsum.getSts().equals("3")) {
                            caseObj.setAccountNo("422D122 1 4");
                            caseObj.setAccountName("收回呆帳－勞給職－現－收回呆帳");
                        }

                    }

                    caseObj.setPayAmt(bapayrptsum.getPayAmt());

                    // 回寫PFM&PFD時所需資訊 Start
                    caseObj.setAccountMemo("");
                    caseObj.setOppAccountNo("");
                    caseObj.setOppAccountName("");
                    caseObj.setActTitleApCode("");
                    caseObj.setDataCd("");
                    caseObj.setDecideAmt(new BigDecimal(0));
                    caseObj.setCashAmt(new BigDecimal(0));
                    caseObj.setCutAmt(new BigDecimal(0));
                    caseObj.setAcceptPayType("");
                    caseObj.setCnt(new BigDecimal(0));
                    caseObj.setAccountTopfMk("N");
                    // 回寫PFM&PFD時所需資訊 End

                }
                if (v_tmpAMT.intValue() > 0) {
                    caseData.add(caseObj);
                }
            }

        }
        return caseData;

    }

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回核定清單-應收已收核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public void insertDataToBapayrptaccountForBatchPaymentReceive(UserBean userData, BatchPaymentReceiveDataCase accountsReceivableData, String payCode) {

        // 新增資料到 給付主檔
        log.debug("Start Insert BAPAYRPTACCOUNT ...");
        Bapayrptaccount bapayrptaccount = new Bapayrptaccount();
        BeanUtility.copyProperties(bapayrptaccount, accountsReceivableData);

        bapayrptaccount.setPayCode(payCode);

        bapayrptaccountDao.insertDataForBatchPaymentReceiveData(bapayrptaccount);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptaccount);
        }
        log.debug("Insert BAPAYRPTACCOUNT Finished ...");
    }

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回核定清單-應收已收核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public void insertDataToBapayrptaccountForPaymentReceive(UserBean userData, BatchPaymentReceiveDataCase accountsReceivableData, String payCode) {

        // 新增資料到 給付主檔
        log.debug("Start Insert BAPAYRPTACCOUNT ...");
        Bapayrptaccount bapayrptaccount = new Bapayrptaccount();
        BeanUtility.copyProperties(bapayrptaccount, accountsReceivableData);

        bapayrptaccount.setPayCode(payCode);

        bapayrptaccountDao.insertDataForPaymentReceiveData(bapayrptaccount);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptaccount);
        }
        log.debug("Insert BAPAYRPTACCOUNT Finished ...");
    }

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for （失能）整批收回核定清單-應收已收核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public void insertKDataToBapayrptaccountForBatchPaymentReceive(UserBean userData, BatchPaymentReceiveDataCase accountsReceivableData, String payCode) {

        // 新增資料到 給付主檔
        log.debug("Start Insert BAPAYRPTACCOUNT ...");
        Bapayrptaccount bapayrptaccount = new Bapayrptaccount();
        BeanUtility.copyProperties(bapayrptaccount, accountsReceivableData);

        bapayrptaccount.setPayCode(payCode);

        String sAdWkMk = null;
        String sNlWkMk = null;
        if (accountsReceivableData.getAdWkMk() == null || accountsReceivableData.getAdWkMk().equals("")) {
            sAdWkMk = "1";
        }
        else {
            sAdWkMk = accountsReceivableData.getAdWkMk();
        }

        if (accountsReceivableData.getNlWkMk() == null || accountsReceivableData.getNlWkMk().equals("")) {
            sNlWkMk = "1";
        }
        else {
            sNlWkMk = accountsReceivableData.getNlWkMk();
        }

        bapayrptaccount.setNlWkMk(sNlWkMk);
        bapayrptaccount.setAdWkMk(sAdWkMk);
        bapayrptaccount.setNaChgMk(accountsReceivableData.getNaChgMk());

        bapayrptaccountDao.insertKDataForBatchPaymentReceiveData(bapayrptaccount);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptaccount);
        }
        log.debug("Insert BAPAYRPTACCOUNT Finished ...");
    }

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for （遺屬）整批收回核定清單-應收已收核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public void insertSDataToBapayrptaccountForBatchPaymentReceive(UserBean userData, BatchPaymentReceiveDataCase accountsReceivableData, String payCode) {

        // 新增資料到 給付主檔
        log.debug("Start Insert BAPAYRPTACCOUNT ...");
        Bapayrptaccount bapayrptaccount = new Bapayrptaccount();
        BeanUtility.copyProperties(bapayrptaccount, accountsReceivableData);

        bapayrptaccount.setPayCode(payCode);

        bapayrptaccountDao.insertSDataForBatchPaymentReceiveData(bapayrptaccount);

        // Insert MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bapayrptaccount);
        }
        log.debug("Insert BAPAYRPTACCOUNT Finished ...");
    }

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for 整批收回核定清單-失敗清單
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataToBafailurelistForBatchPaymentReceive(UserBean userData, OldAgePaymentReceiveDataCase paymentReceiveData, BatchPaymentReceiveDataCase failureListData, BatchPaymentReceiveDataCase cashReceiveData, String chkDate1) {

        // 新增資料到 給付主檔
        // [
        log.debug("Start Insert BAFAILURELIST ...");
        // Baacpdtl baacpdtl = new Baacpdtl();
        Bafailurelist bafailurelist = new Bafailurelist();
        // BeanUtility.copyProperties(baacpdtl, accountsReceivableData);
        BeanUtility.copyProperties(bafailurelist, failureListData);
        /**
         * baacpdtl.setEmpNo(userData.getEmpNo()); // 處理人員 baacpdtl.setInsKd(cashReceiveData.getInsKd()); baacpdtl.setBli_Account_Code(cashReceiveData.getBli_Account_Code()); baacpdtl.setBookedBook(cashReceiveData.getBookedBook());
         * baacpdtl.setBkAccountDt(cashReceiveData.getBkAccountDt()); baacpdtl.setBatchNo(cashReceiveData.getBatchNo()); baacpdtl.setSerialNo(cashReceiveData.getSerialNo()); baacpdtl.setCash_Amt(cashReceiveData.getCash_Amt());
         * baacpdtl.setDivSeq(cashReceiveData.getDivSeq()); baacpdtl.setIndex_No(cashReceiveData.getIndex_No()); baacpdtl.setDisPayKind(paymentReceiveData.getPayKind());
         */

        // bapayrptrecord.setPayDate(DateUtility.changeDateType(paymentReceiveData.getBkAccountDt()));
        // bapayrptrecord.setChkDate(DateUtility.changeDateType(paymentReceiveData.getBkAccountDt()));
        // bafailurelist.setPayDate(chkDate1);
        // bafailurelist.setChkDate(chkDate1);
        // bafailurelist.setPayCode(paymentReceiveData.getApNo().substring(0, 1));
        // bafailurelist.setIssuYm(paymentReceiveData.getIssuYm());
        // bafailurelist.setPayKind(paymentReceiveData.getPayKind());

        bafailurelistDao.insertDataForFailureListData(bafailurelist);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(bafailurelist);
        }
        log.debug("Insert BAFAILURELIST Finished ...");
        // ]
    }

    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 (<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public List<BatchPaymentReceiveDataCase> doBatchPaymentReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, List<BatchPaymentReceiveDataCase> cashReceiveDataCaseList, UserBean userData,
                    BatchPaymentReceiveForm queryForm) {

        // 給付別
        String payCode1 = queryForm.getPayCode();
        // 核收日期（轉西元）
        String chkDate1 = DateUtility.changeDateType(queryForm.getChkDate());

        // List<OldAgePaymentReceiveDataCase> caseData = selectBatchPaymentReceiveDataBy(payCode1, queryForm.getPayCode(), chkDate1);
        List<BatchPaymentReceiveDataCase> failureList = new ArrayList<BatchPaymentReceiveDataCase>();

        int x = 0;

        // 取得勾選資料 index
        // int checkIndex[] = queryForm.getIndex();

        // for (int i = 0; i < checkIndex.length; i++) {
        for (int i = 0; i < cashReceiveDataCaseList.size(); i++) {
            // int Index = checkIndex[i];

            // 退現資料
            // List<BatchPaymentReceiveDataCase> cashReceiveDataCaseList = CaseSessionHelper.getBatchPaymentReceiveDataCaseList(request);
            List caseData2 = new ArrayList();
            int iAffairreCount = 0;
            PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();

            // 照順序扣除 應收未收餘額
            // BatchPaymentReceiveDataCase cashReceiveData = cashReceiveDataCaseList.get(Index);
            BatchPaymentReceiveDataCase cashReceiveData = cashReceiveDataCaseList.get(i);
            // long cash_Amt = cashReceiveData.getCash_Amt().intValue();
            long cash_Amt = cashReceiveData.getAvailable_Money().intValue();
            long cash_AmtT = cashReceiveData.getCash_Amt().intValue();

            String apno = cashReceiveData.getTempHandleNo().toString();
            String issuym = cashReceiveData.getIssuYm().toString();
            String payym = cashReceiveData.getPayYm().toString();
            String seqno = cashReceiveData.getSeqNo().toString();

            // List<OldAgePaymentReceiveDataCase> caseData = updateService.selectBatchPaymentReceiveDataBy(apno, queryForm.getPayCode());

            // if (caseData == null) {
            // continue;
            // }

            // 應收資料
            // List<AccountsReceivableDataCase> accountsReceivableDataCaseList = updateService.selectAccountsReceivableDataListBy(apno, caseData1.getSeqNo());
            List<AccountsReceivableDataCase> accountsReceivableDataCaseList = selectAccountsReceivableDataListBy(apno, seqno, issuym, payym);

            for (AccountsReceivableDataCase accountsReceivableData : accountsReceivableDataCaseList) {
                long recRem = accountsReceivableData.getRecRem().intValue(); // 未收總金額
                cashReceiveData.setRecm(accountsReceivableData.getIssuYm());
                cashReceiveData.setApNo(accountsReceivableData.getApNo());
                cashReceiveData.setIssuYm(accountsReceivableData.getIssuYm());
                cashReceiveData.setPayKind(accountsReceivableData.getPayKind());

                // PFPCCKY $ > BAUNACPDTL $ 不可收回，列入失敗清單
                if (cash_Amt - recRem > 0) {
                    // x = x + 1;
                    // failureList.get(x).setTempHandleNo(cashReceiveData.getTempHandleNo());
                    // failureList.get(x).setDivSeq(cashReceiveData.getDivSeq());
                    // failureList.get(x).setAvailable_Money(cashReceiveData.getAvailable_Money());
                    // failureList.get(x).setRemark("暫收款項明細檔可用餘額大於應收未收明細檔未收總金額。");
                    cashReceiveData.setChkDate(chkDate1);
                    cashReceiveData.setPayCode(payCode1);
                    cashReceiveData.setRemark("暫收款項明細檔可用餘額大於應收未收明細檔未收總金額。(BA)");
                    cashReceiveData.setCrtUser(userData.getUserId());

                    failureList.add(cashReceiveData);

                    return failureList;

                }
                else {
                    // PFPCCKY $ <= BAUNACPDTL $ 可收回
                    long lastRecRem = recRem - cash_Amt;
                    String sActendmk = null;
                    // 現金以後除為0 停止繼續
                    if (lastRecRem == recRem) {
                        break;
                    }
                    else {
                        // accountsReceivableData.setRecRem(new BigDecimal(lastRecRem));

                        // 收回金額
                        cashReceiveData.setRetrieveMoney(cash_Amt);

                        // cash_Amt = 0;

                        // 現金金額 此set可能不需要
                        cashReceiveData.setCash_Amt(new BigDecimal(cash_AmtT));

                        cashReceiveData.setRecAmt(new BigDecimal(cash_Amt));
                        cash_Amt = cash_Amt - recRem;

                        accountsReceivableData.setRecRem(new BigDecimal(lastRecRem));

                        // 可用餘額
                        // cashReceiveData.setAvailable_Money(new BigDecimal(lastRecRem));
                        cashReceiveData.setAvailable_Money(new BigDecimal(0));

                        if (lastRecRem == 0l) {
                            sActendmk = "9";
                        }
                        else {
                            sActendmk = "1";
                        }

                        // 更新 應收帳務明細檔
                        // " "：未收訖；1：部分收回；9：已收訖
                        // updateService.updateBaunacpdtlForPaymentReceive(accountsReceivableData.getRecRem(), "1", accountsReceivableData.getBaunacpdtlId());
                        updateBaunacpdtlForPaymentReceive(accountsReceivableData.getRecRem(), sActendmk, accountsReceivableData.getBaunacpdtlId());

                        // 新增 BAACPDTL
                        // updateService.insertDataForBatchPaymentReceiveData(userData, caseData1, accountsReceivableData, cashReceiveData);
                        insertDataForBatchPaymentReceiveData(userData, null, accountsReceivableData, cashReceiveData, chkDate1, "G");

                        // call 年金元件service
                        iAffairreCount = iAffairreCount + 1;

                        // PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();
                        log.info("(UpdateService.doBatchPaymentReceive: [cash_Amt - recRem < 0]) pcckyuserrec vlue:");
                        pcckyuserrec.setInskd(cashReceiveData.getInsKd());
                        log.info("pcckyuserrec.setInskd:" + pcckyuserrec.getInskd());
                        pcckyuserrec.setBliAccountCode(cashReceiveData.getBli_Account_Code());
                        log.info("pcckyuserrec.setBliAccountCode:" + pcckyuserrec.getBliAccountCode());
                        pcckyuserrec.setBookedbook(cashReceiveData.getBookedBook());
                        log.info("pcckyuserrec.setBookedbook:" + pcckyuserrec.getBookedbook());
                        pcckyuserrec.setBkaccountdt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
                        log.info("pcckyuserrec.setBkaccountdt:" + pcckyuserrec.getBkaccountdt());
                        pcckyuserrec.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
                        log.info("pcckyuserrec.setBatchno:" + pcckyuserrec.getBatchno());
                        pcckyuserrec.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
                        log.info("pcckyuserrec.setSerialno:" + pcckyuserrec.getSerialno());
                        if (cashReceiveData.getCash_Amt() == null) {
                            pcckyuserrec.setCashAmt((long) 0);
                        }
                        else {
                            pcckyuserrec.setCashAmt(cashReceiveData.getCash_Amt().longValue());
                        }
                        log.info("pcckyuserrec.setCashAmt:" + pcckyuserrec.getCashAmt());
                        pcckyuserrec.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
                        log.info("pcckyuserrec.setDivseq:" + pcckyuserrec.getDivseq());
                        pcckyuserrec.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
                        log.info("pcckyuserrec.setIndexNo:" + pcckyuserrec.getIndexNo());
                        // pcckyuserrec.setExpid(1l);
                        pcckyuserrec.setRetrievemoney(cashReceiveData.getRetrieveMoney());// 收回金額
                        log.info("pcckyuserrec.setRetrievemoney:" + pcckyuserrec.getRetrievemoney());
                        pcckyuserrec.setApno(cashReceiveData.getTempHandleNo());
                        log.info("pcckyuserrec.setApno:" + pcckyuserrec.getApno());
                        pcckyuserrec.setRecm(cashReceiveData.getRecm());
                        log.info("pcckyuserrec.setRecm:" + pcckyuserrec.getRecm());
                        pcckyuserrec.setPersonno(userData.getUserId());
                        log.info("pcckyuserrec.setPersonno:" + pcckyuserrec.getPersonno());
                        pcckyuserrec.setPnodept(userData.getDeptId());
                        log.info("pcckyuserrec.setPnodept:" + pcckyuserrec.getPnodept());

                        pcckyuserrec.setPnodate(DateUtility.changeDateType(userData.getLoginDate()));
                        log.info("pcckyuserrec.setPnodate:" + pcckyuserrec.getPnodate());
                        pcckyuserrec.setCamfield(cashReceiveData.getTempHandleNo());
                        log.info("pcckyuserrec.setCamfield:" + Encode.forJava(cashReceiveData.getTempHandleNo()));
                        caseData2.add(pcckyuserrec);

                        // doPfxx0w040Service(userData, cashReceiveData, caseData1);

                        // 退現金額扣除完畢 已剩金額為0 call sp傳送資料至出納
                        // BaWeb 執行完收回作業後，Call SP 產生應收已收清冊(單筆退現收回) [應收已收核定清單(退現收回)]
                        // updateService.doRtnCashRpt(cashReceiveData.getApNo(), cashReceiveData.getSeqNo(), DateUtility.getNowWestDate());
                        // doRtnCashRpt(cashReceiveData.getApNo(), cashReceiveData.getSeqNo(), DateUtility.getNowWestDate());

                        break;
                    }
                } // if(cash_Amt - recRem >= 0){

            } // for(AccountsReceivableDataCase accountsReceivableData : accountsReceivableDataCaseList){

            cashReceiveData.setAffairreCount(new BigDecimal(iAffairreCount));
            doPfxx0w040Service(userData, cashReceiveData, caseData2);

        } // for (int i = 0; i < checkIndex.length; i++) {

        return failureList;

    }

    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 (<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void createReportData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, UserBean userData, BatchPaymentReceiveForm queryForm, String chkDate1, String payCode1,
                    List<OldAgePaymentReceiveDataCase> caseData10) {
        log.debug("執行 列印作業 - 應收收回相關報表 - 整批收回核定清單 - 查詢頁面 UpdateService.createReportData() 開始 ... ");

        List<BatchPaymentReceiveDataCase> caseData3 = new ArrayList<BatchPaymentReceiveDataCase>();

        if (payCode1.equals("L")) {

            for (OldAgePaymentReceiveDataCase caseData1 : caseData10) {

                // 產生 BAPAYRPTRECORD 資料 (1 apNo + 1 seqNo)
                // List<BatchPaymentReceiveDataCase> caseData3 = updateService.selectBaacpdtlDataBy(apno, caseData1.getSeqNo(), chkDate1);
                caseData3 = selectBaacpdtlDataBy(caseData1.getApNo(), caseData1.getSeqNo(), chkDate1);

                for (BatchPaymentReceiveDataCase accountsReceivableData1 : caseData3) {
                    // updateService.insertDataToBapayrptrecordForBatchPaymentReceive(userData, caseData1, accountsReceivableData, cashReceiveData);
                    insertDataToBapayrptrecordForBatchPaymentReceive(userData, caseData1, accountsReceivableData1, null, chkDate1);
                }

            } // for(OldAgePaymentReceiveDataCase caseData1 : caseData){

            // 產生 BAPAYRPTSUM, BAPAYRPTACCOUNT 資料：
            // (1) BAPAYRPTSUM
            // List<BatchPaymentReceiveDataCase> caseData4 = updateService.selectBapayrptrecordDataForRptsumBy(queryForm.getPayCode(), chkDate1);
            List<BatchPaymentReceiveDataCase> caseData4 = selectBapayrptrecordDataForRptsumBy(queryForm.getPayCode(), chkDate1);
            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData4) {
                // updateService.insertDataToBapayrptsumForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getPayCode());
                insertDataToBapayrptsumForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getPayCode());
            }
            // (2) BAPAYRPTACCOUNT
            // List<BatchPaymentReceiveDataCase> caseData5 = updateService.selectBapayrptsumDataForRptaccountBy(queryForm.getPayCode(), chkDate1);
            List<BatchPaymentReceiveDataCase> caseData5 = selectBapayrptsumDataForRptaccountBy(queryForm.getPayCode(), chkDate1);
            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData5) {
                // updateService.insertDataToBapayrptaccountForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getPayCode());
                insertDataToBapayrptaccountForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getPayCode());
            }

            // PFM & PFD
            dealWithPfmPfd(mapping, form, request, response, caseData3, userData, queryForm, chkDate1, payCode1);

        }
        else if (payCode1.equals("K")) {

            for (OldAgePaymentReceiveDataCase caseData1 : caseData10) {

                // 產生 BAPAYRPTRECORD 資料 (1 apNo + 1 seqNo)
                caseData3 = selectKBaacpdtlDataBy(caseData1.getApNo(), caseData1.getSeqNo(), chkDate1);

                for (BatchPaymentReceiveDataCase accountsReceivableData1 : caseData3) {
                    insertKDataToBapayrptrecordForBatchPaymentReceive(userData, caseData1, accountsReceivableData1, null, chkDate1);
                }

            } // for(OldAgePaymentReceiveDataCase caseData1 : caseData){

            // 產生 BAPAYRPTSUM, BAPAYRPTACCOUNT 資料：
            // (1) BAPAYRPTSUM
            List<BatchPaymentReceiveDataCase> caseData4 = selectKBapayrptrecordDataForRptsumBy(queryForm.getPayCode(), chkDate1);

            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData4) {
                insertKDataToBapayrptsumForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getPayCode());
            }

            // (2) BAPAYRPTACCOUNT
            List<BatchPaymentReceiveDataCase> caseData5 = selectKBapayrptsumDataForRptaccountBy(queryForm.getPayCode(), chkDate1);

            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData5) {
                insertKDataToBapayrptaccountForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getPayCode());
            }

            // PFM & PFD
            dealWithPfmPfd_K(mapping, form, request, response, caseData3, userData, queryForm, chkDate1, payCode1);

        }
        else if (payCode1.equals("S")) {

            for (OldAgePaymentReceiveDataCase caseData1 : caseData10) {

                // 產生 BAPAYRPTRECORD 資料 (1 apNo + 1 seqNo)
                caseData3 = selectSBaacpdtlDataBy(caseData1.getApNo(), caseData1.getSeqNo(), chkDate1);

                for (BatchPaymentReceiveDataCase accountsReceivableData1 : caseData3) {
                    insertSDataToBapayrptrecordForBatchPaymentReceive(userData, caseData1, accountsReceivableData1, null, chkDate1);
                }

            } // for(OldAgePaymentReceiveDataCase caseData1 : caseData){

            // 產生 BAPAYRPTSUM, BAPAYRPTACCOUNT 資料：
            // (1) BAPAYRPTSUM
            List<BatchPaymentReceiveDataCase> caseData4 = selectSBapayrptrecordDataForRptsumBy(queryForm.getPayCode(), chkDate1);

            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData4) {
                insertSDataToBapayrptsumForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getPayCode());
            }

            // (2) BAPAYRPTACCOUNT
            List<BatchPaymentReceiveDataCase> caseData5 = selectSBapayrptsumDataForRptaccountBy(queryForm.getPayCode(), chkDate1);

            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData5) {
                insertSDataToBapayrptaccountForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getPayCode());
            }

            // PFM & PFD
            dealWithPfmPfd_S(mapping, form, request, response, caseData3, userData, queryForm, chkDate1, payCode1);

        }

    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 (<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void createReportDataForPaymentReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, UserBean userData, OldAgePaymentReceiveForm queryForm,
                    List<OldAgePaymentReceiveDataCase> caseData10) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 UpdateService.createReportDataForPaymentReceive() 開始 ... ");

        List<BatchPaymentReceiveDataCase> caseData3 = new ArrayList<BatchPaymentReceiveDataCase>();

        // 受理編號
        String sApNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();

        if (queryForm.getApNo1().equals("L")) {

            for (OldAgePaymentReceiveDataCase caseData1 : caseData10) {
                // 產生 BAPAYRPTRECORD 資料 (1 apNo + 1 seqNo)
                caseData3 = selectBaacpdtlDataForPaymentReceiveBy(caseData1.getApNo(), caseData1.getSeqNo(), caseData1.getBaunacpdtlId(), caseData1.getBatchNo(), caseData1.getBkAccountDt(), caseData1.getBli_Account_Code(), caseData1.getBookedBook(),
                                caseData1.getCash_Amt(), caseData1.getDivSeq(), caseData1.getIndex_No(), caseData1.getSerialNo());

                for (BatchPaymentReceiveDataCase accountsReceivableData1 : caseData3) {
                    insertDataToBapayrptrecordForPaymentReceive(userData, caseData1, accountsReceivableData1, null, caseData1.getTempPayDte());
                }
            } // for(OldAgePaymentReceiveDataCase caseData1 : caseData){

            // 產生 BAPAYRPTSUM, BAPAYRPTACCOUNT 資料：
            // (1) BAPAYRPTSUM
            List<BatchPaymentReceiveDataCase> caseData4 = selectBapayrptrecordDataToRptsumForPaymentReceiveBy(queryForm.getApNo1(), sApNo);
            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData4) {
                insertDataToBapayrptsumForPaymentReceive(userData, accountsReceivableData, queryForm.getApNo1());
            }

            // (2) BAPAYRPTACCOUNT
            List<BatchPaymentReceiveDataCase> caseData5 = selectBapayrptsumDataToRptaccountForPaymentReceiveBy(queryForm.getApNo1(), sApNo);
            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData5) {
                insertDataToBapayrptaccountForPaymentReceive(userData, accountsReceivableData, queryForm.getApNo1());
            }

            // PFM & PFD
            dealWithPfmPfdForPaymentReceive(mapping, form, request, response, caseData3, userData, queryForm, queryForm.getApNo1(), sApNo);

        }
        else if (queryForm.getApNo1().equals("K")) {

            for (OldAgePaymentReceiveDataCase caseData1 : caseData10) {

                // 產生 BAPAYRPTRECORD 資料 (1 apNo + 1 seqNo)
                caseData3 = selectKBaacpdtlDataBy(caseData1.getApNo(), caseData1.getSeqNo(), "chkDate1");

                for (BatchPaymentReceiveDataCase accountsReceivableData1 : caseData3) {
                    insertKDataToBapayrptrecordForBatchPaymentReceive(userData, caseData1, accountsReceivableData1, null, "chkDate1");
                }

            } // for(OldAgePaymentReceiveDataCase caseData1 : caseData){

            // 產生 BAPAYRPTSUM, BAPAYRPTACCOUNT 資料：
            // (1) BAPAYRPTSUM
            List<BatchPaymentReceiveDataCase> caseData4 = selectKBapayrptrecordDataForRptsumBy(queryForm.getApNo1(), "chkDate1");

            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData4) {
                insertKDataToBapayrptsumForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getApNo1());
            }

            // (2) BAPAYRPTACCOUNT
            List<BatchPaymentReceiveDataCase> caseData5 = selectKBapayrptsumDataForRptaccountBy(queryForm.getApNo1(), "chkDate1");

            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData5) {
                insertKDataToBapayrptaccountForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getApNo1());
            }

            // PFM & PFD
            // dealWithPfmPfd_K(mapping, form, request, response, caseData3, userData, queryForm, "chkDate1", queryForm.getApNo1());

        }
        else if (queryForm.getApNo1().equals("S")) {

            for (OldAgePaymentReceiveDataCase caseData1 : caseData10) {

                // 產生 BAPAYRPTRECORD 資料 (1 apNo + 1 seqNo)
                caseData3 = selectSBaacpdtlDataBy(caseData1.getApNo(), caseData1.getSeqNo(), "chkDate1");

                for (BatchPaymentReceiveDataCase accountsReceivableData1 : caseData3) {
                    insertSDataToBapayrptrecordForBatchPaymentReceive(userData, caseData1, accountsReceivableData1, null, "chkDate1");
                }

            } // for(OldAgePaymentReceiveDataCase caseData1 : caseData){

            // 產生 BAPAYRPTSUM, BAPAYRPTACCOUNT 資料：
            // (1) BAPAYRPTSUM
            List<BatchPaymentReceiveDataCase> caseData4 = selectSBapayrptrecordDataForRptsumBy(queryForm.getApNo1(), "chkDate1");

            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData4) {
                insertSDataToBapayrptsumForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getApNo1());
            }

            // (2) BAPAYRPTACCOUNT
            List<BatchPaymentReceiveDataCase> caseData5 = selectSBapayrptsumDataForRptaccountBy(queryForm.getApNo1(), "chkDate1");

            for (BatchPaymentReceiveDataCase accountsReceivableData : caseData5) {
                insertSDataToBapayrptaccountForBatchPaymentReceive(userData, accountsReceivableData, queryForm.getApNo1());
            }

            // PFM & PFD
            // dealWithPfmPfd_S(mapping, form, request, response, caseData3, userData, queryForm, "chkDate1", queryForm.getApNo1());

        }

    }

    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 PFM & PFD（老年） (<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void dealWithPfmPfd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, List<BatchPaymentReceiveDataCase> cashReceiveDataCaseList, UserBean userData, BatchPaymentReceiveForm queryForm,
                    String chkDate1, String payCode1) {
        String v_i_cprndt = DateUtility.changeDateType(DateUtility.getNowChineseDate());
        String v_paykind = "45";
        long v_issue_amt = 0;

        // 待產生 PFM&PFD 的報表種類
        List<BatchPaymentReceiveDataCase> caseData6 = selectRpttypeForPfmPfdBy(queryForm.getPayCode(), chkDate1);

        for (BatchPaymentReceiveDataCase rpttypeForPfmPfdData : caseData6) {
            int v_cprnpage = 0;
            String v_g_ProgName = "UpdateService.dealWithPfmPfd";
            int v_g_flag = 0;
            int v_lcnt = 0;
            boolean v_beingpageflag = false;
            String v_worktype = "GGA"; // 報表的業務別
            String v_js_resource = "BAACPDTL"; // 報表的資料來源檔案
            String v_rechkdept = baparamDao.getParamNameForPfmPfd(queryForm.getPayCode()); // 複核人員單位別
            int nowRowCount = 0;

            // 待產生 PFM&PFD 的資料
            List<BatchPaymentReceiveDataCase> caseData7 = selectDataForPfmPfdBy(queryForm.getPayCode(), chkDate1, rpttypeForPfmPfdData.getIssuTyp(), rpttypeForPfmPfdData.getPayTyp(), rpttypeForPfmPfdData.getIssuYm());

            for (BatchPaymentReceiveDataCase pfmPfdData : caseData7) {

                // 待產生 PFM&PFD 的資料 (寫 PFD 明細)
                List<BatchPaymentReceiveDataCase> caseData8 = selectDataForPfdBy(queryForm.getPayCode(), chkDate1, rpttypeForPfmPfdData.getIssuTyp(), pfmPfdData.getApNo(), pfmPfdData.getApSeqNo(), rpttypeForPfmPfdData.getPayTyp(),
                                rpttypeForPfmPfdData.getIssuYm());
                v_cprnpage = pfpfmDao.selectCprnpage(v_i_cprndt);

                for (BatchPaymentReceiveDataCase accountDataForPfmPfdData : caseData8 /** cashReceiveDataCaseList */
                ) {

                    int v_putfilepage = pfpfmDao.selectPutfile_page(v_i_cprndt, v_paykind);
                    v_lcnt = v_lcnt + 1;
                    String v_bli_account_code = "";
                    String v_banty = "";
                    String v_accountno = "";
                    v_issue_amt = v_issue_amt + accountDataForPfmPfdData.getIssueAmt().intValue();
                    int v_accept_amt = 0;
                    int v_pfd_cnt = 1;
                    BigDecimal v_cdetail_keyfield = accountDataForPfmPfdData.getBaacpdtlId();
                    String v_payeeacc = "";

                    nowRowCount = nowRowCount + 1;

                    insertDataToPfpfdForBatchPaymentReceive(accountDataForPfmPfdData, pfmPfdData, v_i_cprndt, v_paykind, v_cprnpage, v_lcnt, v_cdetail_keyfield, v_putfilepage, v_bli_account_code, chkDate1, v_pfd_cnt, v_payeeacc);

                    updateBapayrptrecordForPfmPfd(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt); // 更新核定清單日期及頁次的相關Table及欄位
                    updateBaacpdtlForPfmPfd(accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt); // 當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM

                    // 當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                    if (v_lcnt == 20 || nowRowCount == caseData8.size()) {
                        insertDataToPfpfmForBatchPaymentReceive(accountDataForPfmPfdData, pfmPfdData, v_i_cprndt, v_cprnpage, v_worktype, v_paykind, v_banty, v_accountno, v_js_resource, v_issue_amt, v_accept_amt, v_lcnt, v_rechkdept, v_putfilepage,
                                        chkDate1);

                        v_lcnt = 0;
                        v_issue_amt = 0;
                        v_accept_amt = 0;
                        v_pfd_cnt = 0;

                        // 更新核定清單日期及頁次的相關Table及欄位
                        if (v_beingpageflag == false) {
                            updateBapayrptsumForPfmPfd(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt, payCode1);
                            updateBapayrptaccountForPfmPfd(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt, payCode1);

                            v_beingpageflag = true;
                        }
                        v_cprnpage = pfpfmDao.selectCprnpage(v_i_cprndt);
                    }
                }
            }

            updateErptpageForPfmPfd(rpttypeForPfmPfdData, v_i_cprndt, (v_cprnpage - 1), v_lcnt, payCode1, chkDate1); // 更新分頁大類的最大頁次
        }
    }

    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 PFM & PFD（老年） (<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void dealWithPfmPfdForPaymentReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, List<BatchPaymentReceiveDataCase> cashReceiveDataCaseList, UserBean userData,
                    OldAgePaymentReceiveForm queryForm, String payCode1, String sApNo) {
        String v_i_cprndt = DateUtility.changeDateType(DateUtility.getNowChineseDate());
        String v_paykind = "45";
        long v_issue_amt = 0;

        // 待產生 PFM&PFD 的報表種類
        List<BatchPaymentReceiveDataCase> caseData6 = selectRpttypeForPaymentReceivePfmPfdBy(queryForm.getApNo1(), sApNo);

        for (BatchPaymentReceiveDataCase rpttypeForPfmPfdData : caseData6) {
            int v_cprnpage = 0;
            String v_g_ProgName = "UpdateService.dealWithPfmPfd";
            int v_g_flag = 0;
            int v_lcnt = 0;
            boolean v_beingpageflag = false;
            String v_worktype = "GGA"; // 報表的業務別
            String v_js_resource = "BAACPDTL"; // 報表的資料來源檔案
            String v_rechkdept = baparamDao.getParamNameForPfmPfd(queryForm.getApNo1()); // 複核人員單位別
            int nowRowCount = 0;

            // 待產生 PFM&PFD 的資料
            List<BatchPaymentReceiveDataCase> caseData7 = selectDataForPfmPfdBy(queryForm.getApNo1(), rpttypeForPfmPfdData.getChkDate(), rpttypeForPfmPfdData.getIssuTyp(), rpttypeForPfmPfdData.getPayTyp(), rpttypeForPfmPfdData.getIssuYm());

            for (BatchPaymentReceiveDataCase pfmPfdData : caseData7) {

                // 待產生 PFM&PFD 的資料 (寫 PFD 明細)
                List<BatchPaymentReceiveDataCase> caseData8 = selectDataForPaymentReceivePfdBy(queryForm.getApNo1(), rpttypeForPfmPfdData.getChkDate(), rpttypeForPfmPfdData.getIssuTyp(), pfmPfdData.getApNo(), pfmPfdData.getApSeqNo(),
                                rpttypeForPfmPfdData.getPayTyp(), rpttypeForPfmPfdData.getIssuYm());
                v_cprnpage = pfpfmDao.selectCprnpage(v_i_cprndt);

                for (BatchPaymentReceiveDataCase accountDataForPfmPfdData : caseData8 /** cashReceiveDataCaseList */
                ) {

                    int v_putfilepage = pfpfmDao.selectPutfile_page(v_i_cprndt, v_paykind);
                    v_lcnt = v_lcnt + 1;
                    String v_bli_account_code = "";
                    String v_banty = "";
                    String v_accountno = "";
                    v_issue_amt = v_issue_amt + accountDataForPfmPfdData.getIssueAmt().intValue();
                    int v_accept_amt = 0;
                    int v_pfd_cnt = 1;
                    BigDecimal v_cdetail_keyfield = accountDataForPfmPfdData.getBaacpdtlId();
                    String v_payeeacc = "";

                    nowRowCount = nowRowCount + 1;

                    insertDataToPfpfdForBatchPaymentReceive(accountDataForPfmPfdData, pfmPfdData, v_i_cprndt, v_paykind, v_cprnpage, v_lcnt, v_cdetail_keyfield, v_putfilepage, v_bli_account_code, rpttypeForPfmPfdData.getChkDate(), v_pfd_cnt,
                                    v_payeeacc);

                    updateBapayrptrecordForPfmPfd(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt); // 更新核定清單日期及頁次的相關Table及欄位
                    updateBaacpdtlForPfmPfd(accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt); // 當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM

                    // 當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                    if (v_lcnt == 20 || nowRowCount == caseData8.size()) {
                        insertDataToPfpfmForBatchPaymentReceive(accountDataForPfmPfdData, pfmPfdData, v_i_cprndt, v_cprnpage, v_worktype, v_paykind, v_banty, v_accountno, v_js_resource, v_issue_amt, v_accept_amt, v_lcnt, v_rechkdept, v_putfilepage,
                                        rpttypeForPfmPfdData.getChkDate());

                        v_lcnt = 0;
                        v_issue_amt = 0;
                        v_accept_amt = 0;
                        v_pfd_cnt = 0;

                        // 更新核定清單日期及頁次的相關Table及欄位
                        if (v_beingpageflag == false) {
                            updateBapayrptsumForPfmPfd(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt, payCode1);
                            updateBapayrptaccountForPfmPfd(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt, payCode1);

                            v_beingpageflag = true;
                        }
                        v_cprnpage = pfpfmDao.selectCprnpage(v_i_cprndt);
                    }
                }
            }

            updateErptpageForPfmPfd(rpttypeForPfmPfdData, v_i_cprndt, (v_cprnpage - 1), v_lcnt, payCode1, rpttypeForPfmPfdData.getChkDate()); // 更新分頁大類的最大頁次
        }

        updateTempMk(queryForm.getApNo1(), sApNo);

    }

    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 PFM & PFD（失能） (<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void dealWithPfmPfd_K(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, List<BatchPaymentReceiveDataCase> cashReceiveDataCaseList, UserBean userData, BatchPaymentReceiveForm queryForm,
                    String chkDate1, String payCode1) {
        String v_i_cprndt = DateUtility.changeDateType(DateUtility.getNowChineseDate());
        String v_paykind = "35";
        long v_issue_amt = 0;

        List<BatchPaymentReceiveDataCase> caseData6 = selectRpttypeForPfmPfdKBy(queryForm.getPayCode(), chkDate1);

        for (BatchPaymentReceiveDataCase rpttypeForPfmPfdData : caseData6) {
            int v_cprnpage = 0;
            String v_g_ProgName = "UpdateService.dealWithPfmPfd_K";
            int v_g_flag = 0;
            int v_lcnt = 0;
            boolean v_beingpageflag = false;
            String v_worktype = "GGA"; // 報表的業務別
            String v_js_resource = "BAACPDTL"; // 報表的資料來源檔案
            String v_rechkdept = baparamDao.getParamNameForPfmPfd(queryForm.getPayCode()); // 複核人員單位別
            int nowRowCount = 0;

            // 待產生 PFM&PFD 的資料
            List<BatchPaymentReceiveDataCase> caseData7 = selectDataForPfmPfdKBy(queryForm.getPayCode(), chkDate1, rpttypeForPfmPfdData.getIssuTyp(), rpttypeForPfmPfdData.getPayTyp(), rpttypeForPfmPfdData.getIssuYm(),
                            rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk());

            for (BatchPaymentReceiveDataCase pfmPfdData : caseData7) {

                // 待產生 PFM&PFD 的資料 (寫 PFD 明細)
                List<BatchPaymentReceiveDataCase> caseData8 = selectDataForPfdKBy(queryForm.getPayCode(), chkDate1, rpttypeForPfmPfdData.getIssuTyp(), pfmPfdData.getApNo(), pfmPfdData.getApSeqNo(), rpttypeForPfmPfdData.getPayTyp(),
                                rpttypeForPfmPfdData.getIssuYm(), rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk());
                v_cprnpage = pfpfmDao.selectCprnpage(v_i_cprndt);

                for (BatchPaymentReceiveDataCase accountDataForPfmPfdData : caseData8 /** cashReceiveDataCaseList */
                ) {

                    int v_putfilepage = pfpfmDao.selectPutfile_page(v_i_cprndt, v_paykind);
                    v_lcnt = v_lcnt + 1;
                    String v_bli_account_code = "";
                    String v_banty = "";
                    String v_accountno = "";
                    v_issue_amt = v_issue_amt + accountDataForPfmPfdData.getIssueAmt().intValue();
                    int v_accept_amt = 0;
                    int v_pfd_cnt = 1;
                    BigDecimal v_cdetail_keyfield = accountDataForPfmPfdData.getBaacpdtlId();
                    String v_payeeacc = "";

                    nowRowCount = nowRowCount + 1;

                    insertDataToPfpfdForBatchPaymentReceive(accountDataForPfmPfdData, pfmPfdData, v_i_cprndt, v_paykind, v_cprnpage, v_lcnt, v_cdetail_keyfield, v_putfilepage, v_bli_account_code, chkDate1, v_pfd_cnt, v_payeeacc);

                    updateBapayrptrecordForPfmPfdK(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt, rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk()); // 更新核定清單日期及頁次的相關Table及欄位
                    updateBaacpdtlForPfmPfd(accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt); // 當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM

                    // 當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                    if (v_lcnt == 20 || nowRowCount == caseData8.size()) {
                        insertDataToPfpfmForBatchPaymentReceive(accountDataForPfmPfdData, pfmPfdData, v_i_cprndt, v_cprnpage, v_worktype, v_paykind, v_banty, v_accountno, v_js_resource, v_issue_amt, v_accept_amt, v_lcnt, v_rechkdept, v_putfilepage,
                                        chkDate1);

                        v_lcnt = 0;
                        v_issue_amt = 0;
                        v_accept_amt = 0;
                        v_pfd_cnt = 0;

                        // 更新核定清單日期及頁次的相關Table及欄位
                        if (v_beingpageflag == false) {
                            updateBapayrptsumForPfmPfdK(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt, payCode1, rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk());
                            updateBapayrptaccountForPfmPfdK(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt, payCode1, rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk());

                            v_beingpageflag = true;
                        }

                        v_cprnpage = pfpfmDao.selectCprnpage(v_i_cprndt);
                    }
                }
            }

            updateErptpageForPfmPfdK(rpttypeForPfmPfdData, v_i_cprndt, (v_cprnpage - 1), v_lcnt, payCode1, chkDate1, rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk()); // 更新分頁大類的最大頁次
        }
    }

    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 PFM & PFD（遺屬） (<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void dealWithPfmPfd_S(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, List<BatchPaymentReceiveDataCase> cashReceiveDataCaseList, UserBean userData, BatchPaymentReceiveForm queryForm,
                    String chkDate1, String payCode1) {
        String v_i_cprndt = DateUtility.changeDateType(DateUtility.getNowChineseDate());
        String v_paykind = "55";
        long v_issue_amt = 0;

        List<BatchPaymentReceiveDataCase> caseData6 = selectRpttypeForPfmPfdSBy(queryForm.getPayCode(), chkDate1);

        for (BatchPaymentReceiveDataCase rpttypeForPfmPfdData : caseData6) {
            int v_cprnpage = 0;
            String v_g_ProgName = "UpdateService.dealWithPfmPfd_S";
            int v_g_flag = 0;
            int v_lcnt = 0;
            boolean v_beingpageflag = false;
            String v_worktype = "GGA"; // 報表的業務別
            String v_js_resource = "BAACPDTL"; // 報表的資料來源檔案
            String v_rechkdept = baparamDao.getParamNameForPfmPfd(queryForm.getPayCode()); // 複核人員單位別
            int nowRowCount = 0;

            // 待產生 PFM&PFD 的資料
            List<BatchPaymentReceiveDataCase> caseData7 = selectDataForPfmPfdSBy(queryForm.getPayCode(), chkDate1, rpttypeForPfmPfdData.getIssuTyp(), rpttypeForPfmPfdData.getPayTyp(), rpttypeForPfmPfdData.getIssuYm(),
                            rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk());

            for (BatchPaymentReceiveDataCase pfmPfdData : caseData7) {

                // 待產生 PFM&PFD 的資料 (寫 PFD 明細)
                List<BatchPaymentReceiveDataCase> caseData8 = selectDataForPfdSBy(queryForm.getPayCode(), chkDate1, rpttypeForPfmPfdData.getIssuTyp(), pfmPfdData.getApNo(), pfmPfdData.getApSeqNo(), rpttypeForPfmPfdData.getPayTyp(),
                                rpttypeForPfmPfdData.getIssuYm(), rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk());
                v_cprnpage = pfpfmDao.selectCprnpage(v_i_cprndt);

                for (BatchPaymentReceiveDataCase accountDataForPfmPfdData : caseData8 /** cashReceiveDataCaseList */
                ) {

                    int v_putfilepage = pfpfmDao.selectPutfile_page(v_i_cprndt, v_paykind);
                    v_lcnt = v_lcnt + 1;
                    String v_bli_account_code = "";
                    String v_banty = "";
                    String v_accountno = "";
                    v_issue_amt = v_issue_amt + accountDataForPfmPfdData.getIssueAmt().intValue();
                    int v_accept_amt = 0;
                    int v_pfd_cnt = 1;
                    BigDecimal v_cdetail_keyfield = accountDataForPfmPfdData.getBaacpdtlId();
                    String v_payeeacc = "";

                    nowRowCount = nowRowCount + 1;

                    insertDataToPfpfdForBatchPaymentReceive(accountDataForPfmPfdData, pfmPfdData, v_i_cprndt, v_paykind, v_cprnpage, v_lcnt, v_cdetail_keyfield, v_putfilepage, v_bli_account_code, chkDate1, v_pfd_cnt, v_payeeacc);

                    updateBapayrptrecordForPfmPfdS(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt, rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk()); // 更新核定清單日期及頁次的相關Table及欄位
                    updateBaacpdtlForPfmPfd(accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt); // 當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM

                    // 當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                    if (v_lcnt == 20 || nowRowCount == caseData8.size()) {
                        insertDataToPfpfmForBatchPaymentReceive(accountDataForPfmPfdData, pfmPfdData, v_i_cprndt, v_cprnpage, v_worktype, v_paykind, v_banty, v_accountno, v_js_resource, v_issue_amt, v_accept_amt, v_lcnt, v_rechkdept, v_putfilepage,
                                        chkDate1);

                        v_lcnt = 0;
                        v_issue_amt = 0;
                        v_accept_amt = 0;
                        v_pfd_cnt = 0;

                        // 更新核定清單日期及頁次的相關Table及欄位
                        if (v_beingpageflag == false) {
                            updateBapayrptsumForPfmPfdS(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt, payCode1, rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk());
                            updateBapayrptaccountForPfmPfdS(rpttypeForPfmPfdData, accountDataForPfmPfdData, v_i_cprndt, v_cprnpage, v_lcnt, payCode1, rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk());

                            v_beingpageflag = true;
                        }

                        v_cprnpage = pfpfmDao.selectCprnpage(v_i_cprndt);
                    }
                }
            }

            updateErptpageForPfmPfdS(rpttypeForPfmPfdData, v_i_cprndt, (v_cprnpage - 1), v_lcnt, payCode1, chkDate1, rpttypeForPfmPfdData.getNlWkMk(), rpttypeForPfmPfdData.getAdWkMk()); // 更新分頁大類的最大頁次
        }
    }

    /**
     * 新增(<code>PFPFD</code>) 資料 for 整批收回核定清單-應收已收核定清單
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataToPfpfdForBatchPaymentReceive(BatchPaymentReceiveDataCase accountDataForPfmPfdData, BatchPaymentReceiveDataCase pfmPfdData, String v_i_cprndt, String v_paykind, int v_cprnpage, int v_lcnt, BigDecimal v_cdetail_keyfield,
                    int v_putfilepage, String v_bli_account_code, String chkDate1, int v_pfd_cnt, String v_payeeacc) {

        // 新增資料到 PFPFD
        // [
        log.debug("Start Insert PFPFD ...");
        Pfpfd pfpfd = new Pfpfd();

        BeanUtility.copyProperties(pfpfd, accountDataForPfmPfdData);

        pfpfd.setCprnDt(v_i_cprndt);
        pfpfd.setCprnPage(new BigDecimal(v_cprnpage));
        pfpfd.setLcnt(new BigDecimal(v_lcnt));
        pfpfd.setCdetail_Keyfield(v_cdetail_keyfield.toString());
        pfpfd.setPer_Unit_Cd("1");
        pfpfd.setId_No(accountDataForPfmPfdData.getBenIdnNo());
        pfpfd.setPer_Unit_Name(accountDataForPfmPfdData.getBenName());
        pfpfd.setAccept_Pay_Type("");
        pfpfd.setAccount_No(v_payeeacc);
        pfpfd.setDecide_Amt(accountDataForPfmPfdData.getIssueAmt());
        pfpfd.setCash_Amt(new BigDecimal(0));
        pfpfd.setCmemo(pfmPfdData.getAccountMemo());
        pfpfd.setChkSdate(chkDate1);
        pfpfd.setOpcFm("Y");
        pfpfd.setCnt(new BigDecimal(v_pfd_cnt));
        pfpfd.setBli_Account_Code(v_bli_account_code);
        pfpfd.setActtitle_Ap_Code(pfmPfdData.getActTitleApCode());
        pfpfd.setActDb(pfmPfdData.getActDb()); // not finished
        pfpfd.setActCr(pfmPfdData.getActCr()); // not finished
        pfpfd.setData_Cd(pfmPfdData.getDataCd());
        pfpfd.setPutfile_Page(new BigDecimal(v_putfilepage));
        pfpfd.setInsuranced_Name(accountDataForPfmPfdData.getBenName());
        pfpfd.setAccept_Issue_Cd(v_paykind);
        pfpfd.setApNo(accountDataForPfmPfdData.getApNo());
        pfpfd.setGvSeq(accountDataForPfmPfdData.getSeqNo());
        pfpfd.setPayYm(accountDataForPfmPfdData.getPayYm());
        pfpfd.setIssuYm(accountDataForPfmPfdData.getIssuYm());

        pfpfdDao.insertDataForBatchPaymentReceiveData(pfpfd);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(pfpfd);
        }
        log.debug("Insert PFPFD Finished ...");
        // ]
    }

    /**
     * 新增(<code>PFPFM</code>) 資料 for 整批收回核定清單-應收已收核定清單
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public void insertDataToPfpfmForBatchPaymentReceive(BatchPaymentReceiveDataCase accountDataForPfmPfdData, BatchPaymentReceiveDataCase pfmPfdData, String v_i_cprndt, int v_cprnpage, String v_worktype, String v_paykind, String v_banty,
                    String v_accountno, String v_js_resource, long v_issue_amt, int v_accept_amt, int v_lcnt, String v_rechkdept, int v_putfilepage, String chkDate1) {

        // 新增資料到 PFPFM
        // [
        log.debug("Start Insert PFPFM ...");
        Pfpfm pfpfm = new Pfpfm();

        BeanUtility.copyProperties(pfpfm, accountDataForPfmPfdData);

        pfpfm.setCprnDt(v_i_cprndt);
        pfpfm.setCprnPage(new BigDecimal(v_cprnpage));
        pfpfm.setWorkType(v_worktype);
        pfpfm.setAccept_Issue_Kind(v_paykind);
        pfpfm.setBanTy(v_banty);
        pfpfm.setAccountNo(v_accountno);
        pfpfm.setBookedBook("");
        pfpfm.setJs_Resource(v_js_resource);
        pfpfm.setIssue_Amt(new BigDecimal(v_issue_amt));
        pfpfm.setAccept_Amt(new BigDecimal(v_accept_amt));
        pfpfm.setPfd_Cnt(new BigDecimal(v_lcnt));
        pfpfm.setRechkDept(v_rechkdept);
        pfpfm.setRckDate(chkDate1);
        pfpfm.setConfirm_Dt(chkDate1);
        pfpfm.setPayDte(chkDate1);
        pfpfm.setPutfile_Page(new BigDecimal(v_putfilepage));

        pfpfmDao.insertDataForBatchPaymentReceiveData(pfpfm);

        // Insert MMAPLOG

        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doInsertLog(pfpfm);
        }
        log.debug("Insert PFPFM Finished ...");
        // ]
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BAPAYRPTRECORD</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateBapayrptrecordForPfmPfd(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, BatchPaymentReceiveDataCase accountDataForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt) {

        log.debug("執行 UpdateService.updateBapayrptrecordForPfmPfd() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String apNo = accountDataForPfmPfdData.getApNo();
        String seqNo = accountDataForPfmPfdData.getSeqNo();
        String issuYm = accountDataForPfmPfdData.getIssuYm();
        String payYm = accountDataForPfmPfdData.getPayYm();
        String chkDate = accountDataForPfmPfdData.getChkDate();

        bapayrptrecordDao.updateBapayrptrecordForPfmPfd(v_cprnpage, v_lcnt, issuTyp, apNo, seqNo, issuYm, payYm, chkDate);

        log.debug("執行 UpdateService.updateBapayrptrecordForPfmPfd() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BAPAYRPTRECORD</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateBapayrptrecordForPfmPfdK(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, BatchPaymentReceiveDataCase accountDataForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt, String nlWkMk, String adWkMk) {

        log.debug("執行 UpdateService.updateBapayrptrecordForPfmPfdK() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String apNo = accountDataForPfmPfdData.getApNo();
        String seqNo = accountDataForPfmPfdData.getSeqNo();
        String issuYm = accountDataForPfmPfdData.getIssuYm();
        String payYm = accountDataForPfmPfdData.getPayYm();
        String chkDate = accountDataForPfmPfdData.getChkDate();

        bapayrptrecordDao.updateBapayrptrecordForPfmPfdK(v_cprnpage, v_lcnt, issuTyp, apNo, seqNo, issuYm, payYm, chkDate, nlWkMk, adWkMk);

        log.debug("執行 UpdateService.updateBapayrptrecordForPfmPfdK() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BAPAYRPTRECORD</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateBapayrptrecordForPfmPfdS(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, BatchPaymentReceiveDataCase accountDataForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt, String nlWkMk, String adWkMk) {

        log.debug("執行 UpdateService.updateBapayrptrecordForPfmPfdS() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String apNo = accountDataForPfmPfdData.getApNo();
        String seqNo = accountDataForPfmPfdData.getSeqNo();
        String issuYm = accountDataForPfmPfdData.getIssuYm();
        String payYm = accountDataForPfmPfdData.getPayYm();
        String chkDate = accountDataForPfmPfdData.getChkDate();

        bapayrptrecordDao.updateBapayrptrecordForPfmPfdS(v_cprnpage, v_lcnt, issuTyp, apNo, seqNo, issuYm, payYm, chkDate, nlWkMk, adWkMk);

        log.debug("執行 UpdateService.updateBapayrptrecordForPfmPfdS() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BADAPR</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateBaacpdtlForPfmPfd(BatchPaymentReceiveDataCase accountDataForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt) {

        log.debug("執行 UpdateService.updateBaacpdtlForPfmPfd() 開始 ...");

        BigDecimal baacpdtlId = accountDataForPfmPfdData.getBaacpdtlId();

        baacpdtlDao.updateBaacpdtlForPfmPfd(v_cprnpage, v_lcnt, baacpdtlId);

        log.debug("執行 UpdateService.updateBaacpdtlForPfmPfd() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BAPAYRPTSUM</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateBapayrptsumForPfmPfd(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, BatchPaymentReceiveDataCase accountDataForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt, String payCode) {
        log.debug("執行 UpdateService.updateBapayrptsumForPfmPfd() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String issuYm = accountDataForPfmPfdData.getIssuYm();
        String chkDate = accountDataForPfmPfdData.getChkDate();

        bapayrptsumDao.updateBapayrptsumForPfmPfd(v_cprnpage, v_lcnt, issuTyp, issuYm, chkDate, payCode);

        log.debug("執行 UpdateService.updateBapayrptsumForPfmPfd() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BAPAYRPTSUM</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateBapayrptsumForPfmPfdK(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, BatchPaymentReceiveDataCase accountDataForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt, String payCode, String nlWkMk, String adWkMk) {
        log.debug("執行 UpdateService.updateBapayrptsumForPfmPfdK() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String issuYm = accountDataForPfmPfdData.getIssuYm();
        String chkDate = accountDataForPfmPfdData.getChkDate();

        bapayrptsumDao.updateBapayrptsumForPfmPfdK(v_cprnpage, v_lcnt, issuTyp, issuYm, chkDate, payCode, nlWkMk, adWkMk);

        log.debug("執行 UpdateService.updateBapayrptsumForPfmPfdK() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BAPAYRPTSUM</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateBapayrptsumForPfmPfdS(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, BatchPaymentReceiveDataCase accountDataForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt, String payCode, String nlWkMk, String adWkMk) {
        log.debug("執行 UpdateService.updateBapayrptsumForPfmPfdS() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String issuYm = accountDataForPfmPfdData.getIssuYm();
        String chkDate = accountDataForPfmPfdData.getChkDate();

        bapayrptsumDao.updateBapayrptsumForPfmPfdS(v_cprnpage, v_lcnt, issuTyp, issuYm, chkDate, payCode, nlWkMk, adWkMk);

        log.debug("執行 UpdateService.updateBapayrptsumForPfmPfdS() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BADAPR</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateBapayrptaccountForPfmPfd(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, BatchPaymentReceiveDataCase accountDataForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt, String payCode) {
        log.debug("執行 UpdateService.updateBapayrptaccountForPfmPfd() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String issuYm = accountDataForPfmPfdData.getIssuYm();
        String chkDate = accountDataForPfmPfdData.getChkDate();

        bapayrptaccountDao.updateBapayrptaccountForPfmPfd(v_cprnpage, v_lcnt, issuTyp, issuYm, chkDate, payCode);

        log.debug("執行 UpdateService.updateBapayrptaccountForPfmPfd() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BADAPR</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateBapayrptaccountForPfmPfdK(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, BatchPaymentReceiveDataCase accountDataForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt, String payCode, String nlWkMk, String adWkMk) {
        log.debug("執行 UpdateService.updateBapayrptaccountForPfmPfdK() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String issuYm = accountDataForPfmPfdData.getIssuYm();
        String chkDate = accountDataForPfmPfdData.getChkDate();

        bapayrptaccountDao.updateBapayrptaccountForPfmPfdK(v_cprnpage, v_lcnt, issuTyp, issuYm, chkDate, payCode, nlWkMk, adWkMk);

        log.debug("執行 UpdateService.updateBapayrptaccountForPfmPfdK() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BADAPR</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateBapayrptaccountForPfmPfdS(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, BatchPaymentReceiveDataCase accountDataForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt, String payCode, String nlWkMk, String adWkMk) {
        log.debug("執行 UpdateService.updateBapayrptaccountForPfmPfdS() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String issuYm = accountDataForPfmPfdData.getIssuYm();
        String chkDate = accountDataForPfmPfdData.getChkDate();

        bapayrptaccountDao.updateBapayrptaccountForPfmPfdS(v_cprnpage, v_lcnt, issuTyp, issuYm, chkDate, payCode, nlWkMk, adWkMk);

        log.debug("執行 UpdateService.updateBapayrptaccountForPfmPfdS() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BADAPR</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateErptpageForPfmPfd(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt, String payCode, String chkDate1) {
        log.debug("執行 UpdateService.updateErptpageForPfmPfd() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String issuYm = rpttypeForPfmPfdData.getIssuYm();
        // String chkDate = rpttypeForPfmPfdData.getChkDate();

        bapayrptaccountDao.updateErptpageForPfmPfd(v_i_cprndt, v_cprnpage, v_lcnt, issuTyp, chkDate1, payCode, issuYm);

        log.debug("執行 UpdateService.updateErptpageForPfmPfd() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BADAPR</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateTempMk(String payCode, String apNo) {
        log.debug("執行 UpdateService.updateTempMk() 開始 ...");

        bapayrptaccountDao.updateTempMk(payCode, apNo);
        bapayrptsumDao.updateTempMk(payCode, apNo);
        bapayrptrecordDao.updateTempMk(payCode, apNo);

        log.debug("執行 UpdateService.updateTempMk() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BADAPR</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateErptpageForPfmPfdK(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt, String payCode, String chkDate1, String nlWkMk, String adWkMk) {
        log.debug("執行 UpdateService.updateErptpageForPfmPfdK() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String issuYm = rpttypeForPfmPfdData.getIssuYm();
        // String chkDate = rpttypeForPfmPfdData.getChkDate();

        bapayrptaccountDao.updateErptpageForPfmPfdK(v_i_cprndt, v_cprnpage, v_lcnt, issuTyp, chkDate1, payCode, issuYm, nlWkMk, adWkMk);

        log.debug("執行 UpdateService.updateErptpageForPfmPfdK() 完成 ...");
    }

    /**
     * 依傳入的 <code>Badapr</code> 資料更新至 給付核定檔 (<code>BADAPR</code>)<br>
     * for : 更正作業 - 止付處理作業<br>
     * 
     * @param caseData <code>Badapr</code>
     */
    public void updateErptpageForPfmPfdS(BatchPaymentReceiveDataCase rpttypeForPfmPfdData, String v_i_cprndt, int v_cprnpage, int v_lcnt, String payCode, String chkDate1, String nlWkMk, String adWkMk) {
        log.debug("執行 UpdateService.updateErptpageForPfmPfdS() 開始 ...");

        String issuTyp = rpttypeForPfmPfdData.getIssuTyp();
        String issuYm = rpttypeForPfmPfdData.getIssuYm();
        // String chkDate = rpttypeForPfmPfdData.getChkDate();

        bapayrptaccountDao.updateErptpageForPfmPfdS(v_i_cprndt, v_cprnpage, v_lcnt, issuTyp, chkDate1, payCode, issuYm, nlWkMk, adWkMk);

        log.debug("執行 UpdateService.updateErptpageForPfmPfdS() 完成 ...");
    }

    public void doPfxx0w040Service(UserBean userData, BatchPaymentReceiveDataCase cashReceiveData, List<PfpcckyuserrecVO> caseData) {

        // call 年金元件 service
        List list = new ArrayList();
        Map map = new HashMap();

        map.put("personid", userData.getUserId());
        map.put("deptid", userData.getDeptId());
        map.put("ip", userData.getLoginIP());

        log.info("(doPfxx0w040Service) pfpcckyvo vlue:");
        PfpcckyVO pfpcckyvo = new PfpcckyVO();

        pfpcckyvo.setInskd(cashReceiveData.getInsKd());
        log.info("pfpcckyvo.setInskd:" + pfpcckyvo.getInskd());

        pfpcckyvo.setBliAccountCode(cashReceiveData.getBli_Account_Code());
        log.info("pfpcckyvo.setBliAccountCode:" + pfpcckyvo.getBliAccountCode());

        pfpcckyvo.setBookedbook(cashReceiveData.getBookedBook());
        log.info("pfpcckyvo.setBookedbook:" + pfpcckyvo.getBookedbook());

        if (cashReceiveData.getBkAccountDt().length() == 7) {
            pfpcckyvo.setBkaccountdt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
        }
        else {
            pfpcckyvo.setBkaccountdt(cashReceiveData.getBkAccountDt());
        }
        log.info("pfpcckyvo.setBkaccountdt:" + pfpcckyvo.getBkaccountdt());

        pfpcckyvo.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
        log.info("pfpcckyvo.setBatchno:" + pfpcckyvo.getBatchno());

        pfpcckyvo.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
        log.info("pfpcckyvo.setSerialno:" + pfpcckyvo.getSerialno());

        pfpcckyvo.setCashAmt(cashReceiveData.getCash_Amt().longValue());
        log.info("pfpcckyvo.setCashAmt:" + pfpcckyvo.getCashAmt());

        pfpcckyvo.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
        log.info("pfpcckyvo.setDivseq:" + pfpcckyvo.getDivseq());

        pfpcckyvo.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
        log.info("pfpcckyvo.setIndexNo:" + pfpcckyvo.getIndexNo());
        // ------------------------------------------------------------//
        pfpcckyvo.setMovetocode(cashReceiveData.getMoveToCode());
        log.info("pfpcckyvo.setMovetocode:" + pfpcckyvo.getMovetocode());

        pfpcckyvo.setTemphandleno(cashReceiveData.getTempHandleNo());
        log.info("pfpcckyvo.setTemphandleno:" + pfpcckyvo.getTemphandleno());

        pfpcckyvo.setAffairreprno(userData.getUserId());
        log.info("pfpcckyvo.setAffairreprno:" + pfpcckyvo.getAffairreprno());

        pfpcckyvo.setAffairredept(userData.getDeptId());
        log.info("pfpcckyvo.setAffairredept:" + pfpcckyvo.getAffairredept());

        pfpcckyvo.setAffairredate(DateUtility.getNowWestDate());
        log.info("pfpcckyvo.setAffairredate:" + DateUtility.getNowWestDate());

        if (cashReceiveData.getAvailable_Money() == null) {
            pfpcckyvo.setAvailableMoney((long) 0);// 可用餘額
        }
        else {
            pfpcckyvo.setAvailableMoney(cashReceiveData.getAvailable_Money().longValue());// 可用餘額
        }
        log.info("pfpcckyvo.setAvailableMoney:" + pfpcckyvo.getAvailableMoney());

        if (cashReceiveData.getAffairreCount() == null) {
            pfpcckyvo.setAffairrecount((long) 0);
        }
        else {
            pfpcckyvo.setAffairrecount(cashReceiveData.getAffairreCount().longValue());
        }
        log.info("pfpcckyvo.setAffairrecount:" + pfpcckyvo.getAffairrecount());

        map.put("pcckyvo", pfpcckyvo);

        // ------------------------------------------------------------//
        // ------------------------------------------------------------//

        Integer iExpid = selectExpid(pfpcckyvo.getInskd(), pfpcckyvo.getBliAccountCode(), pfpcckyvo.getBookedbook(), pfpcckyvo.getBkaccountdt(), pfpcckyvo.getBatchno(), pfpcckyvo.getSerialno(), pfpcckyvo.getCashAmt(), pfpcckyvo.getDivseq(),
                        pfpcckyvo.getIndexNo());
        if (iExpid == null) {
            iExpid = 0;
        }
        log.info("(doPfxx0w040Service) [pfpcckyvo] iExpid vlue:" + iExpid);

        List detailList = new ArrayList();

        for (int j = 0; j < caseData.size(); j++) {
            PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();
            log.info("(doPfxx0w040Service) pcckyuserrec vlue:");

            pcckyuserrec.setInskd(cashReceiveData.getInsKd());
            log.info("pcckyuserrec.setInskd:" + pcckyuserrec.getInskd());

            pcckyuserrec.setBliAccountCode(cashReceiveData.getBli_Account_Code());
            log.info("pcckyuserrec.setBliAccountCode:" + pcckyuserrec.getBliAccountCode());

            pcckyuserrec.setBookedbook(cashReceiveData.getBookedBook());
            log.info("pcckyuserrec.setBookedbook:" + pcckyuserrec.getBookedbook());

            if (cashReceiveData.getBkAccountDt().length() == 7) {
                pcckyuserrec.setBkaccountdt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
            }
            else {
                pcckyuserrec.setBkaccountdt(cashReceiveData.getBkAccountDt());
            }
            log.info("pcckyuserrec.setBkaccountdt:" + pcckyuserrec.getBkaccountdt());

            pcckyuserrec.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
            log.info("pcckyuserrec.setBatchno:" + pcckyuserrec.getBatchno());

            pcckyuserrec.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
            log.info("pcckyuserrec.setSerialno:" + pcckyuserrec.getSerialno());

            if (cashReceiveData.getCash_Amt() == null) {
                pcckyuserrec.setCashAmt((long) 0);
            }
            else {
                pcckyuserrec.setCashAmt(cashReceiveData.getCash_Amt().longValue());
            }
            log.info("pcckyuserrec.setCashAmt:" + pcckyuserrec.getCashAmt());

            pcckyuserrec.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
            log.info("pcckyuserrec.setDivseq:" + pcckyuserrec.getDivseq());

            pcckyuserrec.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
            log.info("pcckyuserrec.setIndexNo:" + pcckyuserrec.getIndexNo());
            // ------------------------------------------------------------//

            iExpid = iExpid.intValue() + 1;

            pcckyuserrec.setExpid(Long.valueOf(iExpid));
            log.info("pcckyuserrec.setExpid:" + pcckyuserrec.getExpid());

            pcckyuserrec.setRetrievemoney(cashReceiveData.getRetrieveMoney());// 收回金額
            log.info("pcckyuserrec.setRetrievemoney:" + pcckyuserrec.getRetrievemoney());

            pcckyuserrec.setApno(caseData.get(j).getApno());
            log.info("pcckyuserrec.setApno:" + pcckyuserrec.getApno());

            pcckyuserrec.setRecm(cashReceiveData.getRecm());
            log.info("pcckyuserrec.setRecm:" + Encode.forJava(cashReceiveData.getRecm()));

            pcckyuserrec.setPersonno(userData.getUserId());
            log.info("pcckyuserrec.setPersonno:" + pcckyuserrec.getPersonno());

            pcckyuserrec.setPnodept(userData.getDeptId());
            log.info("pcckyuserrec.setPnodept:" + pcckyuserrec.getPnodept());

            pcckyuserrec.setPnodate(DateUtility.changeDateType(userData.getLoginDate()));
            log.info("pcckyuserrec.setPnodate:" + pcckyuserrec.getPnodate());

            pcckyuserrec.setCamfield(cashReceiveData.getTempHandleNo());
            log.info("pcckyuserrec.setCamfield:" + Encode.forJava(cashReceiveData.getTempHandleNo()));

            detailList.add(pcckyuserrec);
        }
        map.put("pcckylist", detailList);

        // map.put("pcckylist", pcckyuserrec);

        list.add(map);

        if (list != null) {
            log.info("list is not null ");
            log.info(list.size());
        }

        if (list.isEmpty())
            log.info("list is empty");

        if (pfxx0w040Service == null)
            log.info("pfxx0w040Service is null");

        log.info(pfxx0w040Service.writeSavePfxx0w040n(list));
        String deBugStop = "";
    }

    /**
     * 依傳入條件取得 郵遞區號
     * 
     * @param apNo 受理編號
     * @return
     */
    public String getZipCode(String addr) {
        String zip = null;
        // 取得戶籍資料檔
        zip = cvldtlDao.selectZipCodeData(addr);
        if (StringUtils.isNotBlank(zip))
            return zip;
        else
            return "";
    }

    /**
     * 依傳入條件取得 地址資料
     * 
     * @param apno 受理編號
     * @return
     */
    public PaymentProcessCase getAddrData(String apno, String seqNo) {
        Baappbase caseData = baappbaseDao.selectAddrData(apno, seqNo);
        PaymentProcessCase returnCase = new PaymentProcessCase();
        returnCase.setAddr(caseData.getCommAddr());// 地址
        returnCase.setZipCode(caseData.getCommZip());// 郵遞區號
        return returnCase;
    }

    /**
     * 依傳入條件取得 退回現金業務單位使用紀錄檔(<code>PFPCCKYUSERREC</code>) EXPID
     * 
     * @param idn 身分證號
     * @return
     */
    public Integer selectExpid(String sInskd, String sBliAccountCode, String sBookedbook, String sBkaccountdt, Long lBatchno, Long lSerialno, Long lCashAmt, Long lDivseq, Long lIndexNo) {
        if (sBkaccountdt.length() == 7) {
            sBkaccountdt = DateUtility.changeDateType(sBkaccountdt);
        }

        Integer iExpid = localpfpcckyuserrecDao.selectExpid(sInskd, sBliAccountCode, sBookedbook, sBkaccountdt, lBatchno, lSerialno, lCashAmt, lDivseq, lIndexNo);
        return iExpid;
    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTACCOUNT</code>) 資料 FOR 整批收回核定清單-PFM&PFD
     * 
     * @param PAYCODE 受理編號
     * @param CHKDATE
     * @return <code>Bapayrptaccount</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectRpttypeForPfmPfdBy(String payCode, String chkDate) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptaccount> list = bapayrptaccountDao.getRpttypeForPfmPfdResult(payCode, chkDate);

        for (int i = 0; i < list.size(); i++) {
            Bapayrptaccount Bapayrptaccount = (Bapayrptaccount) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, Bapayrptaccount);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTACCOUNT</code>) 資料 FOR 整批收回核定清單-PFM&PFD
     * 
     * @param PAYCODE 受理編號
     * @param CHKDATE
     * @return <code>Bapayrptaccount</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectRpttypeForPaymentReceivePfmPfdBy(String payCode, String sApNo) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptaccount> list = bapayrptaccountDao.getRpttypeForPaymentReceivePfmPfdResult(payCode, sApNo);

        for (int i = 0; i < list.size(); i++) {
            Bapayrptaccount Bapayrptaccount = (Bapayrptaccount) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, Bapayrptaccount);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTACCOUNT</code>) 資料 FOR 整批收回核定清單-PFM&PFD (K)
     * 
     * @param PAYCODE 受理編號
     * @param CHKDATE
     * @return <code>Bapayrptaccount</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectRpttypeForPfmPfdKBy(String payCode, String chkDate) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptaccount> list = bapayrptaccountDao.getRpttypeForPfmPfdKResult(payCode, chkDate);

        for (int i = 0; i < list.size(); i++) {
            Bapayrptaccount Bapayrptaccount = (Bapayrptaccount) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, Bapayrptaccount);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTACCOUNT</code>) 資料 FOR 整批收回核定清單-PFM&PFD (S)
     * 
     * @param PAYCODE 受理編號
     * @param CHKDATE
     * @return <code>Bapayrptaccount</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectRpttypeForPfmPfdSBy(String payCode, String chkDate) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptaccount> list = bapayrptaccountDao.getRpttypeForPfmPfdSResult(payCode, chkDate);

        for (int i = 0; i < list.size(); i++) {
            Bapayrptaccount Bapayrptaccount = (Bapayrptaccount) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, Bapayrptaccount);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTACCOUNT</code>) 資料 FOR 整批收回核定清單-PFM&PFD
     * 
     * @param PAYCODE 受理編號
     * @return <code>Bapayrptaccount</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectDataForPfmPfdBy(String payCode, String chkDate, String issuTyp, String payTyp, String issuYm) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptaccount> list = bapayrptaccountDao.getDataForPfmPfdResult(payCode, chkDate, issuTyp, payTyp, issuYm);

        for (int i = 0; i < list.size(); i++) {
            Bapayrptaccount Bapayrptaccount = (Bapayrptaccount) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, Bapayrptaccount);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTACCOUNT</code>) 資料 FOR 整批收回核定清單-PFM&PFD (K)
     * 
     * @param PAYCODE 受理編號
     * @return <code>Bapayrptaccount</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectDataForPfmPfdKBy(String payCode, String chkDate, String issuTyp, String payTyp, String issuYm, String nlWkMk, String adWkMk) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptaccount> list = bapayrptaccountDao.getDataForPfmPfdKResult(payCode, chkDate, issuTyp, payTyp, issuYm, nlWkMk, adWkMk);

        for (int i = 0; i < list.size(); i++) {
            Bapayrptaccount Bapayrptaccount = (Bapayrptaccount) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, Bapayrptaccount);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTACCOUNT</code>) 資料 FOR 整批收回核定清單-PFM&PFD (S)
     * 
     * @param PAYCODE 受理編號
     * @return <code>Bapayrptaccount</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectDataForPfmPfdSBy(String payCode, String chkDate, String issuTyp, String payTyp, String issuYm, String nlWkMk, String adWkMk) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Bapayrptaccount> list = bapayrptaccountDao.getDataForPfmPfdSResult(payCode, chkDate, issuTyp, payTyp, issuYm, nlWkMk, adWkMk);

        for (int i = 0; i < list.size(); i++) {
            Bapayrptaccount Bapayrptaccount = (Bapayrptaccount) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, Bapayrptaccount);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTACCOUNT</code>) 資料 FOR 整批收回核定清單-PFM&PFD
     * 
     * @param PAYCODE 受理編號
     * @return <code>Bapayrptaccount</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectDataForPfdBy(String payCode, String chkDate, String issuTyp, String apNo, String apSeqNo, String payTyp, String issuYm) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Baacpdtl> list = baacpdtlDao.getDataForPfdResult(payCode, chkDate, chkDate, apNo, apSeqNo, payTyp, issuYm);

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl Baacpdtl = (Baacpdtl) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, Baacpdtl);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTACCOUNT</code>) 資料 FOR 整批收回核定清單-PFM&PFD
     * 
     * @param PAYCODE 受理編號
     * @return <code>Bapayrptaccount</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectDataForPaymentReceivePfdBy(String payCode, String chkDate, String issuTyp, String apNo, String apSeqNo, String payTyp, String issuYm) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Baacpdtl> list = baacpdtlDao.getDataForPaymentReceivePfdResult(payCode, chkDate, chkDate, apNo, apSeqNo, payTyp, issuYm);

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl Baacpdtl = (Baacpdtl) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, Baacpdtl);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTACCOUNT</code>) 資料 FOR 整批收回核定清單-PFM&PFD (K)
     * 
     * @param PAYCODE 受理編號
     * @return <code>Bapayrptaccount</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectDataForPfdKBy(String payCode, String chkDate, String issuTyp, String apNo, String apSeqNo, String payTyp, String issuYm, String nlWkMk, String adWkMk) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Baacpdtl> list = baacpdtlDao.getDataForPfdKResult(payCode, chkDate, chkDate, apNo, apSeqNo, payTyp, issuYm, nlWkMk, adWkMk);

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl Baacpdtl = (Baacpdtl) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, Baacpdtl);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTACCOUNT</code>) 資料 FOR 整批收回核定清單-PFM&PFD (S)
     * 
     * @param PAYCODE 受理編號
     * @return <code>Bapayrptaccount</code> 物件
     */
    public List<BatchPaymentReceiveDataCase> selectDataForPfdSBy(String payCode, String chkDate, String issuTyp, String apNo, String apSeqNo, String payTyp, String issuYm, String nlWkMk, String adWkMk) {
        List<BatchPaymentReceiveDataCase> caseData = new ArrayList<BatchPaymentReceiveDataCase>();
        List<Baacpdtl> list = baacpdtlDao.getDataForPfdSResult(payCode, chkDate, chkDate, apNo, apSeqNo, payTyp, issuYm, nlWkMk, adWkMk);

        for (int i = 0; i < list.size(); i++) {
            Baacpdtl Baacpdtl = (Baacpdtl) list.get(i);
            BatchPaymentReceiveDataCase caseObj = new BatchPaymentReceiveDataCase();
            BeanUtility.copyProperties(caseObj, Baacpdtl);

            caseData.add(caseObj);
        }
        return caseData;

    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 (<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public List<OldAgePaymentReceiveDataCase> doOldAgePaymentReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, UserBean userData, OldAgePaymentReceiveForm queryForm,
                    Map<String, List<CashReceiveDataCase>> dataMap, String key) {

        /**
         * // 給付別 String payCode1 = queryForm.getPayCode(); // 核收日期（轉西元） String chkDate1 = DateUtility.changeDateType(queryForm.getChkDate());
         */

        List<CashReceiveDataCase> cashReceiveDataCaseList = dataMap.get(key);

        // List<OldAgePaymentReceiveDataCase> caseData = selectBatchPaymentReceiveDataBy(payCode1, queryForm.getPayCode(), chkDate1);
        /** List<BatchPaymentReceiveDataCase> failureList = new ArrayList<BatchPaymentReceiveDataCase>(); */
        List<OldAgePaymentReceiveDataCase> tempCashReceiveList = new ArrayList<OldAgePaymentReceiveDataCase>();

        int x = 0;

        // 取得勾選資料 index
        // int checkIndex[] = queryForm.getIndex();

        // for (int i = 0; i < checkIndex.length; i++) {
        for (int i = 0; i < cashReceiveDataCaseList.size(); i++) {
            // int Index = checkIndex[i];

            // 退現資料
            // List<BatchPaymentReceiveDataCase> cashReceiveDataCaseList = CaseSessionHelper.getBatchPaymentReceiveDataCaseList(request);
            List caseData2 = new ArrayList();
            int iAffairreCount = 0;
            PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();

            // 照順序扣除 應收未收餘額
            // BatchPaymentReceiveDataCase cashReceiveData = cashReceiveDataCaseList.get(Index);
            CashReceiveDataCase cashReceiveData = cashReceiveDataCaseList.get(i);
            BatchPaymentReceiveDataCase batchPaymentReceiveDataCase = new BatchPaymentReceiveDataCase();
            OldAgePaymentReceiveDataCase oldAgePaymentReceiveDataCase = new OldAgePaymentReceiveDataCase();
            // long cash_Amt = cashReceiveData.getCash_Amt().intValue();
            long cash_Amt = cashReceiveData.getAvailable_Money().intValue();
            long cash_AmtT = cashReceiveData.getCash_Amt().intValue();

            String apno = cashReceiveData.getTempHandleNo().toString();

            // 民國年月日 轉 西元年月日
            cashReceiveData.setBkAccountDt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
            /**
             * String issuym = cashReceiveData.getIssuYm().toString(); String payym = cashReceiveData.getPayYm().toString(); String seqno = cashReceiveData.getSeqNo().toString();
             */

            // List<OldAgePaymentReceiveDataCase> caseData = updateService.selectBatchPaymentReceiveDataBy(apno, queryForm.getPayCode());

            // if (caseData == null) {
            // continue;
            // }

            // 應收資料
            // List<AccountsReceivableDataCase> accountsReceivableDataCaseList = updateService.selectAccountsReceivableDataListBy(apno, caseData1.getSeqNo());
            List<AccountsReceivableDataCase> accountsReceivableDataCaseList = selectAccountsReceivableDataListBy(apno, null, null, null);

            for (AccountsReceivableDataCase accountsReceivableData : accountsReceivableDataCaseList) {
                long recRem = accountsReceivableData.getRecRem().intValue(); // 未收總金額
                cashReceiveData.setRecm(accountsReceivableData.getIssuYm());
                cashReceiveData.setApNo(accountsReceivableData.getApNo());
                cashReceiveData.setIssuYm(accountsReceivableData.getIssuYm());
                cashReceiveData.setPayKind(accountsReceivableData.getPayKind());

                // PFPCCKY $ > BAUNACPDTL $ 不可收回，列入失敗清單
                if (cash_Amt - recRem > 0) {
                    // x = x + 1;
                    // failureList.get(x).setTempHandleNo(cashReceiveData.getTempHandleNo());
                    // failureList.get(x).setDivSeq(cashReceiveData.getDivSeq());
                    // failureList.get(x).setAvailable_Money(cashReceiveData.getAvailable_Money());
                    // failureList.get(x).setRemark("暫收款項明細檔可用餘額大於應收未收明細檔未收總金額。");
                    /**
                     * cashReceiveData.setChkDate(chkDate1); cashReceiveData.setPayCode(payCode1); cashReceiveData.setRemark("暫收款項明細檔可用餘額大於應收未收明細檔未收總金額。(BA)"); cashReceiveData.setCrtUser(userData.getUserId()); failureList.add(cashReceiveData); return
                     * failureList;
                     */

//                }
//                else {
                    // PFPCCKY $ <= BAUNACPDTL $ 可收回
                    long lastRecRem = recRem - cash_Amt;
                    String sActendmk = null;
                    // 現金以後除為0 停止繼續
                    if (lastRecRem == recRem) {
                        break;
                    }
                    else {
                        // accountsReceivableData.setRecRem(new BigDecimal(lastRecRem));

                        // 收回金額
                        cashReceiveData.setRetrieveMoney(cash_Amt);

                        // cash_Amt = 0;

                        // 現金金額 此set可能不需要
                        cashReceiveData.setCash_Amt(new BigDecimal(cash_AmtT));

                        cashReceiveData.setRecAmt(new BigDecimal(cash_Amt));
                        cash_Amt = cash_Amt - recRem;

                        accountsReceivableData.setRecRem(new BigDecimal(lastRecRem));

                        // 可用餘額
                        // cashReceiveData.setAvailable_Money(new BigDecimal(lastRecRem));
                        cashReceiveData.setAvailable_Money(new BigDecimal(0));

                        if (lastRecRem == 0l) {
                            sActendmk = "9";
                        }
                        else {
                            sActendmk = "1";
                        }

                        // 更新 應收帳務明細檔
                        // " "：未收訖；1：部分收回；9：已收訖
                        // updateService.updateBaunacpdtlForPaymentReceive(accountsReceivableData.getRecRem(), "1", accountsReceivableData.getBaunacpdtlId());
                        updateBaunacpdtlForPaymentReceive(accountsReceivableData.getRecRem(), sActendmk, accountsReceivableData.getBaunacpdtlId());

                        // 新增 BAACPDTL
                        BeanUtility.copyProperties(batchPaymentReceiveDataCase, cashReceiveData);
                        // updateService.insertDataForBatchPaymentReceiveData(userData, caseData1, accountsReceivableData, cashReceiveData);
                        insertDataForBatchPaymentReceiveData(userData, null, accountsReceivableData, batchPaymentReceiveDataCase, cashReceiveData.getTempPayDte(), "E");
                        cashReceiveData.setBaunacpdtlId(accountsReceivableData.getBaunacpdtlId());

                        // call 年金元件service
                        iAffairreCount = iAffairreCount + 1;

                        // PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();
                        log.info("(UpdateService.doBatchPaymentReceive: [cash_Amt - recRem < 0]) pcckyuserrec vlue:");
                        pcckyuserrec.setInskd(cashReceiveData.getInsKd());
                        log.info("pcckyuserrec.setInskd:" + pcckyuserrec.getInskd());
                        pcckyuserrec.setBliAccountCode(cashReceiveData.getBli_Account_Code());
                        log.info("pcckyuserrec.setBliAccountCode:" + pcckyuserrec.getBliAccountCode());
                        pcckyuserrec.setBookedbook(cashReceiveData.getBookedBook());
                        log.info("pcckyuserrec.setBookedbook:" + pcckyuserrec.getBookedbook());

                        if (cashReceiveData.getBkAccountDt().length() == 7) {
                            pcckyuserrec.setBkaccountdt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
                        }
                        else {
                            pcckyuserrec.setBkaccountdt(cashReceiveData.getBkAccountDt());
                        }
                        log.info("pcckyuserrec.setBkaccountdt:" + pcckyuserrec.getBkaccountdt());
                        pcckyuserrec.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
                        log.info("pcckyuserrec.setBatchno:" + pcckyuserrec.getBatchno());
                        pcckyuserrec.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
                        log.info("pcckyuserrec.setSerialno:" + pcckyuserrec.getSerialno());
                        if (cashReceiveData.getCash_Amt() == null) {
                            pcckyuserrec.setCashAmt((long) 0);
                        }
                        else {
                            pcckyuserrec.setCashAmt(cashReceiveData.getCash_Amt().longValue());
                        }
                        log.info("pcckyuserrec.setCashAmt:" + pcckyuserrec.getCashAmt());
                        pcckyuserrec.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
                        log.info("pcckyuserrec.setDivseq:" + pcckyuserrec.getDivseq());
                        pcckyuserrec.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
                        log.info("pcckyuserrec.setIndexNo:" + pcckyuserrec.getIndexNo());
                        // pcckyuserrec.setExpid(1l);
                        pcckyuserrec.setRetrievemoney(cashReceiveData.getRetrieveMoney());// 收回金額
                        log.info("pcckyuserrec.setRetrievemoney:" + pcckyuserrec.getRetrievemoney());
                        pcckyuserrec.setApno(cashReceiveData.getTempHandleNo());
                        log.info("pcckyuserrec.setApno:" + pcckyuserrec.getApno());
                        pcckyuserrec.setRecm(cashReceiveData.getRecm());
                        log.info("pcckyuserrec.setRecm:" + pcckyuserrec.getRecm());
                        pcckyuserrec.setPersonno(userData.getUserId());
                        log.info("pcckyuserrec.setPersonno:" + pcckyuserrec.getPersonno());
                        pcckyuserrec.setPnodept(userData.getDeptId());
                        log.info("pcckyuserrec.setPnodept:" + pcckyuserrec.getPnodept());

                        pcckyuserrec.setPnodate(DateUtility.changeDateType(userData.getLoginDate()));
                        log.info("pcckyuserrec.setPnodate:" + pcckyuserrec.getPnodate());
                        pcckyuserrec.setCamfield(cashReceiveData.getTempHandleNo());
                        log.info("pcckyuserrec.setCamfield:" + cashReceiveData.getTempHandleNo());
                        caseData2.add(pcckyuserrec);

                        // doPfxx0w040Service(userData, cashReceiveData, caseData1);

                        // 退現金額扣除完畢 已剩金額為0 call sp傳送資料至出納
                        // BaWeb 執行完收回作業後，Call SP 產生應收已收清冊(單筆退現收回) [應收已收核定清單(退現收回)]
                        // updateService.doRtnCashRpt(cashReceiveData.getApNo(), cashReceiveData.getSeqNo(), DateUtility.getNowWestDate());
                        // doRtnCashRpt(cashReceiveData.getApNo(), cashReceiveData.getSeqNo(), DateUtility.getNowWestDate());

                        BeanUtility.copyProperties(oldAgePaymentReceiveDataCase, cashReceiveData);
                        tempCashReceiveList.add(oldAgePaymentReceiveDataCase);

                        break;
                    }
                } // if(cash_Amt - recRem >= 0){

            } // for(AccountsReceivableDataCase accountsReceivableData : accountsReceivableDataCaseList){

            cashReceiveData.setAffairreCount(new BigDecimal(iAffairreCount));
            batchPaymentReceiveDataCase.setAffairreCount(new BigDecimal(iAffairreCount));

            doPfxx0w040Service(userData, batchPaymentReceiveDataCase, caseData2);

        } // for (int i = 0; i < checkIndex.length; i++) {

        return tempCashReceiveList;

    }

    public void doPfxx0w040UpdateService(UserBean userData, CashReceiveDataCase cashReceiveData, OldAgePaymentReceiveDataCase caseData) {

        // call 年金元件 service
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("personid", userData.getEmpNo());
        map.put("deptid", userData.getDeptId());
        map.put("ip", userData.getLoginIP());
        PfpcckyVO pfpcckyvo = new PfpcckyVO();
        log.info("pfpcckyvo vlue:");
        pfpcckyvo.setInskd(cashReceiveData.getInsKd());
        log.info("pfpcckyvo.setInskd:" + pfpcckyvo.getInskd());
        pfpcckyvo.setBliAccountCode(cashReceiveData.getBli_Account_Code());
        log.info("pfpcckyvo.setBliAccountCode:" + pfpcckyvo.getBliAccountCode());
        pfpcckyvo.setBookedbook(cashReceiveData.getBookedBook());
        log.info("pfpcckyvo.setBookedbook:" + pfpcckyvo.getBookedbook());
        pfpcckyvo.setBkaccountdt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
        log.info("pfpcckyvo.setBkaccountdt:" + pfpcckyvo.getBkaccountdt());
        pfpcckyvo.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
        log.info("pfpcckyvo.setBatchno:" + pfpcckyvo.getBatchno());
        pfpcckyvo.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
        log.info("pfpcckyvo.setSerialno:" + pfpcckyvo.getSerialno());
        pfpcckyvo.setCashAmt(cashReceiveData.getCash_Amt().longValue());
        log.info("pfpcckyvo.setCashAmt:" + pfpcckyvo.getCashAmt());
        pfpcckyvo.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
        log.info("pfpcckyvo.setDivseq:" + pfpcckyvo.getDivseq());
        pfpcckyvo.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
        log.info("pfpcckyvo.setIndexNo:" + pfpcckyvo.getIndexNo());
        pfpcckyvo.setMovetocode(cashReceiveData.getMoveToCode());
        log.info("pfpcckyvo.setMovetocode:" + pfpcckyvo.getMovetocode());
        pfpcckyvo.setTemphandleno(cashReceiveData.getTempHandleNo());
        log.info("pfpcckyvo.setTemphandleno:" + pfpcckyvo.getTemphandleno());
        pfpcckyvo.setAffairreprno(userData.getEmpNo());
        log.info("pfpcckyvo.setAffairreprno:" + userData.getEmpNo());
        pfpcckyvo.setAffairredept(userData.getDeptId());
        log.info("pfpcckyvo.setAffairredept:" + userData.getDeptId());
        pfpcckyvo.setAffairredate(DateUtility.getNowWestDate());
        log.info("pfpcckyvo.setAffairredate:" + DateUtility.getNowWestDate());

        if (cashReceiveData.getAvailable_Money() == null) {
            pfpcckyvo.setAvailableMoney((long) 0);// 可用餘額
        }
        else {
            pfpcckyvo.setAvailableMoney(cashReceiveData.getAvailable_Money().longValue());// 可用餘額
        }
        log.info("pfpcckyvo.setAvailableMoney:" + pfpcckyvo.getAvailableMoney());
        if (cashReceiveData.getAffairreCount() == null) {
            pfpcckyvo.setAffairrecount((long) 0);
        }
        else {
            pfpcckyvo.setAffairrecount(cashReceiveData.getAffairreCount().longValue());
        }
        log.info("pfpcckyvo.setAffairrecount:" + pfpcckyvo.getAffairrecount());

        map.put("pcckyvo", pfpcckyvo);

        List detailList = new ArrayList();
        PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();
        log.info("pcckyuserrec vlue:");
        pcckyuserrec.setInskd(cashReceiveData.getInsKd());
        log.info("pcckyuserrec.setInskd:" + pcckyuserrec.getInskd());
        pcckyuserrec.setBliAccountCode(cashReceiveData.getBli_Account_Code());
        log.info("pcckyuserrec.setBliAccountCode:" + pcckyuserrec.getBliAccountCode());
        pcckyuserrec.setBookedbook(cashReceiveData.getBookedBook());
        log.info("pcckyuserrec.setBookedbook:" + pcckyuserrec.getBookedbook());
        pcckyuserrec.setBkaccountdt(cashReceiveData.getBkAccountDt());
        log.info("pcckyuserrec.setBkaccountdt:" + pcckyuserrec.getBkaccountdt());
        pcckyuserrec.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
        log.info("pcckyuserrec.setBatchno:" + pcckyuserrec.getBatchno());
        pcckyuserrec.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
        log.info("pcckyuserrec.setSerialno:" + pcckyuserrec.getSerialno());
        if (cashReceiveData.getCash_Amt() == null) {
            pcckyuserrec.setCashAmt((long) 0);
        }
        else {
            pcckyuserrec.setCashAmt(cashReceiveData.getCash_Amt().longValue());
        }
        log.info("pcckyuserrec.setCashAmt:" + pcckyuserrec.getCashAmt());
        pcckyuserrec.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
        log.info("pcckyuserrec.setDivseq:" + pcckyuserrec.getDivseq());
        pcckyuserrec.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
        log.info("pcckyuserrec.setIndexNo:" + pcckyuserrec.getIndexNo());
        pcckyuserrec.setExpid(1l);
        pcckyuserrec.setRetrievemoney(cashReceiveData.getRetrieveMoney());// 收回金額
        log.info("pcckyuserrec.setRetrievemoney:" + pcckyuserrec.getRetrievemoney());
        pcckyuserrec.setApno(caseData.getApNo());
        log.info("pcckyuserrec.setApno:" + pcckyuserrec.getApno());
        pcckyuserrec.setRecm(cashReceiveData.getRecm());
        log.info("pcckyuserrec.setRecm:" + pcckyuserrec.getRecm());
        pcckyuserrec.setPersonno(userData.getEmpNo());
        log.info("pcckyuserrec.setPersonno:" + pcckyuserrec.getPersonno());
        pcckyuserrec.setPnodept(userData.getDeptId());
        log.info("pcckyuserrec.setPnodept:" + pcckyuserrec.getPnodept());
        if (userData.getLoginTime().length() < 8) {
            pcckyuserrec.setPnodate(userData.getLoginTime());
        }
        else {
            pcckyuserrec.setPnodate(userData.getLoginTime().substring(0, 8));
        }
        log.info("pcckyuserrec.setPnodate:" + pcckyuserrec.getPnodate());
        pcckyuserrec.setCamfield(cashReceiveData.getTempHandleNo());
        log.info("pcckyuserrec.setCamfield:" + cashReceiveData.getTempHandleNo());
        detailList.add(pcckyuserrec);
        map.put("pcckylist", detailList);
        // map.put("pcckylist", pcckyuserrec);

        list.add(map);

        if (list != null) {
            log.info("list is not null ");
            log.info(list.size());
        }

        if (list.isEmpty())
            log.info("list is empty");

        if (pfxx0w040Service == null)
            log.info("pfxx0w040Service is null");

        log.info(pfxx0w040Service.writeUpdatePfxx0w040n(list));
        String deBugStop = "";
    }

    /**
     * 新增不合格核定通知紀錄檔 for 核定函列印作業
     * 
     * @param
     */
    public void insertUnqualifiedNotice(List<Baunqualifiednotice> baunqualifiednoticeList, String apNo, String seqNo) {

        // 新增不合格核定通知紀錄檔 (BAUNQUALIFIEDNOTICE)
        baunqualifiednoticeDao.insertData(baunqualifiednoticeList);

        // 更新主檔欄位
        baappbaseDao.updateIssuNotifyingMkByApNo(apNo);
        baappbaseDao.updateUnqualifiedCauseByApNo(apNo, seqNo);

    }

    /**
     * 新增 (<code>BAMARGINAMTNOTIFY</code>) 資料 for 老年差額金通知
     * 
     * @param
     * @return
     */
    public void insertDataToBamarginamtnotifyForMonthlyRpt31(UserBean userData, List<MonthlyRpt31Case> caseDataList, HashMap<String, Object> map) {

        String rptKind = (String) map.get("rptKind");
        String sProgId = (String) map.get("progId");
        String sLetterType = "";
        String sPreLetterType = "";
        String sNotifyForm = "";

        if (rptKind.equals("001") || rptKind.equals("002") || rptKind.equals("003")) {
            sLetterType = "11";
        }
        else if (rptKind.equals("001U") || rptKind.equals("002U") || rptKind.equals("003U")) {
            sLetterType = "12";
        }
        else if (rptKind.equals("001P") || rptKind.equals("002P") || rptKind.equals("003P")) {
            sLetterType = "13";
        }

        if (rptKind.equals("001") || rptKind.equals("001U") || rptKind.equals("001P")) {
            sNotifyForm = "001";
        }
        else if (rptKind.equals("002") || rptKind.equals("002U") || rptKind.equals("002P")) {
            sNotifyForm = "002";
        }
        else if (rptKind.equals("003") || rptKind.equals("003U") || rptKind.equals("003P")) {
            sNotifyForm = "003";
        }

        for (MonthlyRpt31Case caseData : caseDataList) {

            // 新增行政支援紀錄檔 (MAADMREC)
            List<Maadmrec> maadmrecList = new ArrayList<Maadmrec>();
            Maadmrec maadmrec = new Maadmrec();

            // 取得 MAADMREC.SEQNO
            String sSeqNo = maadmrecDao.selectSeqNoBy(caseData.getApNo(), caseData.getIssuYm(), DateUtility.getNowWestDate(), sLetterType);

            maadmrec.setApNo(caseData.getApNo());
            maadmrec.setProDate(DateUtility.getNowWestDate());
            maadmrec.setLetterType(sLetterType);
            maadmrec.setSeqNo(sSeqNo);
            maadmrec.setIssuYm(caseData.getIssuYm());
            maadmrec.setIdNo(caseData.getEvtIdnNo());
            maadmrec.setName(caseData.getEvtName());
            maadmrec.setBrDate(caseData.getEvtBrDate());
            maadmrec.setNdomk1("D");
            maadmrec.setPromoteUser(userData.getEmpNo());
            maadmrec.setCrtUser(userData.getEmpNo());
            maadmrec.setCrtTime(DateUtility.getNowWestDateTime(true));

            maadmrecList.add(maadmrec);

            // 寫入行政支援紀錄檔 (MAADMREC)
            maadmrecDao.insertDataForMonthlyRpt31(maadmrecList);

            BigDecimal bMaAdmRecId = maadmrecDao.selectMaAdmRecIdForMonthlyRpt31(caseData.getApNo(), sSeqNo, caseData.getIssuYm(), sLetterType, DateUtility.getNowWestDate());

            // 新增老年差額金通知發文紀錄檔 (BAMARGINAMTNOTIFY)
            List<Bamarginamtnotify> bamarginamtnotifyList = new ArrayList<Bamarginamtnotify>();
            Bamarginamtnotify bamarginamtnotify = new Bamarginamtnotify();

            if (rptKind.equals("001") || rptKind.equals("002") || rptKind.equals("003")) {
                bamarginamtnotify.setProgId(sProgId);
                bamarginamtnotify.setApNo(caseData.getApNo());
                bamarginamtnotify.setSeqNo(caseData.getSeqNo());
                bamarginamtnotify.setIssuYm1(caseData.getIssuYm());
                bamarginamtnotify.setProcDate1(DateUtility.getNowWestDate());
                bamarginamtnotify.setIssueDate1(DateUtility.getNowWestDate());
                bamarginamtnotify.setNotifyForm(sNotifyForm);
                bamarginamtnotify.setMaAdmRecId1(bMaAdmRecId);
                bamarginamtnotify.setCrtUser(userData.getEmpNo());
                bamarginamtnotify.setCrtTime(DateUtility.getNowWestDateTime(true));

                bamarginamtnotifyList.add(bamarginamtnotify);

                // 寫入老年差額金通知發文紀錄檔 (BAMARGINAMTNOTIFY)
                bamarginamtnotifyDao.insertDataForMonthlyRpt31(bamarginamtnotifyList);
            }
            else if (rptKind.equals("001U") || rptKind.equals("002U") || rptKind.equals("003U")) {
                bamarginamtnotify.setApNo(caseData.getApNo());
                bamarginamtnotify.setIssuYm2(caseData.getIssuYm());
                bamarginamtnotify.setProcDate2(DateUtility.getNowWestDate());
                bamarginamtnotify.setIssueDate2(DateUtility.getNowWestDate());
                bamarginamtnotify.setMaAdmRecId2(bMaAdmRecId);

                bamarginamtnotifyList.add(bamarginamtnotify);

                // 更新老年差額金通知發文紀錄檔 (BAMARGINAMTNOTIFY)
                bamarginamtnotifyDao.updateDataForMonthlyRpt31U(bamarginamtnotifyList);
            }
            else if (rptKind.equals("001P") || rptKind.equals("002P") || rptKind.equals("003P")) {
                bamarginamtnotify.setApNo(caseData.getApNo());
                bamarginamtnotify.setIssuYm3(caseData.getIssuYm());
                bamarginamtnotify.setProcDate3(DateUtility.getNowWestDate());
                bamarginamtnotify.setIssueDate3(DateUtility.getNowWestDate());
                bamarginamtnotify.setMaAdmRecId3(bMaAdmRecId);

                bamarginamtnotifyList.add(bamarginamtnotify);

                // 更新老年差額金通知發文紀錄檔 (BAMARGINAMTNOTIFY)
                bamarginamtnotifyDao.updateDataForMonthlyRpt31P(bamarginamtnotifyList);
            }

            // 第二次通知函（催辦）及第三次通知函（延不補正）的發文日期 (ISSUEDATE) 於前一次通知函對應之行政支援記錄 (MAADMREC) 寫入銷案日期 (CLOSEDATE)

            List<Bamarginamtnotify> dataList = bamarginamtnotifyDao.selectDataForMonthlyRpt13(caseData.getApNo());
            BigDecimal bPreMaAdmRecId = null;
            if (dataList != null && dataList.size() > 0) {
                if (rptKind.equals("001U") || rptKind.equals("002U") || rptKind.equals("003U")) {
                    bPreMaAdmRecId = dataList.get(0).getMaAdmRecId1();
                }
                else if (rptKind.equals("001P") || rptKind.equals("002P") || rptKind.equals("003P")) {
                    bPreMaAdmRecId = dataList.get(0).getMaAdmRecId2();
                }
            }

            if (!(rptKind.equals("001") || rptKind.equals("002") || rptKind.equals("003"))) {

                Maadmrec maadmrec1 = new Maadmrec();

                if (rptKind.equals("001U") || rptKind.equals("002U") || rptKind.equals("003U")) {
                    sPreLetterType = "11";
                }
                else if (rptKind.equals("001P") || rptKind.equals("002P") || rptKind.equals("003P")) {
                    sPreLetterType = "12";
                }

                maadmrec1.setMaAdmRecId(bPreMaAdmRecId);
                maadmrec1.setApNo(caseData.getApNo());
                maadmrec1.setClosDate(DateUtility.getNowWestDate());
                maadmrec1.setLetterType(sPreLetterType);
                maadmrec1.setSeqNo(caseData.getSeqNo());
                maadmrec1.setUpdUser(userData.getEmpNo());
                maadmrec1.setUpdTime(DateUtility.getNowWestDateTime(true));

                maadmrecDao.updateCloseDateForMonthlyRpt31(maadmrec1);

            }
        }

    }

    /**
     * 更新老年、失能結案狀態 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo
     * @param seqNo
     * @param updUser
     * @param caseTyp
     * @param issuYm
     */
    public void updateDataForOldAgeAndDsiabledCloseStatusAlteration(String apNo, String updUser, String caseTyp) {
        // 更新該筆案件的處理狀態變更為「改核」(12)，並清除該筆案件的結案日期及結案原因。
        baappbaseDao.updateDataForOldAgeAndDsiabledCloseStatusAlteration(apNo, updUser);

        // 當案件類別為不給付案者時(CASETYP=3)，將該案件相關的行政支援函別為 21 之資料，管制註記上 D。
        // 當案件類別為不給付案者時(CASETYP=3)，將該案件的案件類別更改為 1(新案-無核付資料) 或 2(續發案-有核付資料)。
//        if (caseTyp.equals("3")) {
            maadmrecDao.updateDataForCloseStatusAlteration(apNo, updUser);
            baappbaseDao.updateCaseTypForCloseStatusAlteration(apNo, updUser);
//        }
    }
    
    /**
     * 更新遺屬結案狀態 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo
     * @param seqNo
     * @param updUser
     * @param caseTyp
     * @param issuYm
     */
    public void updateDataForCloseStatusAlteration(String apNo, String seqNo, String updUser, String caseTyp) {
        // 更新有勾選之遺屬及該筆事故者的處理狀態變更為「改核」(12)，並清除有勾選之遺屬及該筆事故者的結案日期及結案原因。
        baappbaseDao.updateDataForCloseStatusAlteration(apNo, seqNo, updUser);

        // 當案件類別為不給付案者時(CASETYP=3)，將該案件的案件類別更改為 1(新案-無核付資料) 或 2(續發案-有核付資料)。
//        if (caseTyp.equals("3")) {
            maadmrecDao.updateDataForCloseStatusAlteration(apNo, updUser);
            baappbaseDao.updateCaseTypForCloseStatusAlteration(apNo, updUser);
//        }

    }

    public void setPfxx0w040Service(Pfxx0w040Service pfxx0w040Service) {
        this.pfxx0w040Service = pfxx0w040Service;
    }

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
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

    public void setBaparamDao(BaparamDao baparamDao) {
        this.baparamDao = baparamDao;
    }

    public void setBadaprDao(BadaprDao badaprDao) {
        this.badaprDao = badaprDao;
    }

    public void setBasenimaintDao(BasenimaintDao basenimaintDao) {
        this.basenimaintDao = basenimaintDao;
    }

    public void setBachkfileDao(BachkfileDao bachkfileDao) {
        this.bachkfileDao = bachkfileDao;
    }

    public void setBaoncepayDao(BaoncepayDao baoncepayDao) {
        this.baoncepayDao = baoncepayDao;
    }

    public void setBadupeidnDao(BadupeidnDao badupeidnDao) {
        this.badupeidnDao = badupeidnDao;
    }

    public void setCaubDao(CaubDao caubDao) {
        this.caubDao = caubDao;
    }

    public void setBbcmf08Dao(Bbcmf08Dao bbcmf08Dao) {
        this.bbcmf08Dao = bbcmf08Dao;
    }

    public void setBafamilyDao(BafamilyDao bafamilyDao) {
        this.bafamilyDao = bafamilyDao;
    }

    public void setBachkcontrlDao(BachkcontrlDao bachkcontrlDao) {
        this.bachkcontrlDao = bachkcontrlDao;
    }

    public void setBaappexpandDao(BaappexpandDao baappexpandDao) {
        this.baappexpandDao = baappexpandDao;
    }

    public void setBbcmf07Dao(Bbcmf07Dao bbcmf07Dao) {
        this.bbcmf07Dao = bbcmf07Dao;
    }

    public void setBaregivedtlDao(BaregivedtlDao baregivedtlDao) {
        this.baregivedtlDao = baregivedtlDao;
    }

    public void setBastudtermDao(BastudtermDao bastudtermDao) {
        this.bastudtermDao = bastudtermDao;
    }

    public void setBbcmf06Dao(Bbcmf06Dao bbcmf06Dao) {
        this.bbcmf06Dao = bbcmf06Dao;
    }

    public void setBacriinjDao(BacriinjDao bacriinjDao) {
        this.bacriinjDao = bacriinjDao;
    }

    public void setBacolumnrecDao(BacolumnrecDao bacolumnrecDao) {
        this.bacolumnrecDao = bacolumnrecDao;
    }

    public void setBacompelnopayDao(BacompelnopayDao bacompelnopayDao) {
        this.bacompelnopayDao = bacompelnopayDao;
    }

    public void setBalp0d020Dao(Balp0d020Dao balp0d020Dao) {
        this.balp0d020Dao = balp0d020Dao;
    }

    public void setCipbDao(CipbDao cipbDao) {
        this.cipbDao = cipbDao;
    }

    public void setBapflbacDao(BapflbacDao bapflbacDao) {
        this.bapflbacDao = bapflbacDao;
    }

    public void setBbcmf29Dao(Bbcmf29Dao bbcmf29Dao) {
        this.bbcmf29Dao = bbcmf29Dao;
    }

    public void setBaunacpdtlDao(BaunacpdtlDao baunacpdtlDao) {
        this.baunacpdtlDao = baunacpdtlDao;
    }

    public void setLocalPfpcckyDao(LocalPfpcckyDao localPfpcckyDao) {
        this.localPfpcckyDao = localPfpcckyDao;
    }

    public void setBaacpdtlDao(BaacpdtlDao baacpdtlDao) {
        this.baacpdtlDao = baacpdtlDao;
    }

    public void setNbappbaseDao(NbappbaseDao nbappbaseDao) {
        this.nbappbaseDao = nbappbaseDao;
    }

    public void setBarecheckDao(BarecheckDao barecheckDao) {
        this.barecheckDao = barecheckDao;
    }

    public void setBapayrptrecordDao(BapayrptrecordDao bapayrptrecordDao) {
        this.bapayrptrecordDao = bapayrptrecordDao;
    }

    public void setBapayrptsumDao(BapayrptsumDao bapayrptsumDao) {
        this.bapayrptsumDao = bapayrptsumDao;
    }

    public void setBapayrptaccountDao(BapayrptaccountDao bapayrptaccountDao) {
        this.bapayrptaccountDao = bapayrptaccountDao;
    }

    public void setLocalPfpcckyuserrecDao(LocalPfpcckyuserrecDao localpfpcckyuserrecDao) {
        this.localpfpcckyuserrecDao = localpfpcckyuserrecDao;
    }

    public void setBafailurelistDao(BafailurelistDao bafailurelistDao) {
        this.bafailurelistDao = bafailurelistDao;
    }

    public void setPfpfmDao(PfpfmDao pfpfmDao) {
        this.pfpfmDao = pfpfmDao;
    }

    public void setPfpfdDao(PfpfdDao pfpfdDao) {
        this.pfpfdDao = pfpfdDao;
    }

    public void setBaunqualifiednoticeDao(BaunqualifiednoticeDao baunqualifiednoticeDao) {
        this.baunqualifiednoticeDao = baunqualifiednoticeDao;
    }

    public void setBamarginamtnotifyDao(BamarginamtnotifyDao bamarginamtnotifyDao) {
        this.bamarginamtnotifyDao = bamarginamtnotifyDao;
    }

    public void setMaadmrecDao(MaadmrecDao maadmrecDao) {
        this.maadmrecDao = maadmrecDao;
    }

    public void setBahandicaptermDao(BahandicaptermDao bahandicaptermDao) {
        this.bahandicaptermDao = bahandicaptermDao;
    }
}
