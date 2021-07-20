package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BaappexpandDao;
import tw.gov.bli.ba.dao.BabasicamtDao;
import tw.gov.bli.ba.dao.BacpidtlDao;
import tw.gov.bli.ba.dao.BacpirecDao;
import tw.gov.bli.ba.dao.BalettercodeDao;
import tw.gov.bli.ba.dao.BanotifyDao;
import tw.gov.bli.ba.dao.BapaavgmonDao;
import tw.gov.bli.ba.dao.BapacutDao;
import tw.gov.bli.ba.dao.BapadchkDao;
import tw.gov.bli.ba.dao.BapadcslvlDao;
import tw.gov.bli.ba.dao.BapainspectDao;
import tw.gov.bli.ba.dao.BapairrDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Babasicamt;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.domain.Bacpirec;
import tw.gov.bli.ba.domain.Balettercode;
import tw.gov.bli.ba.domain.Bapaavgmon;
import tw.gov.bli.ba.domain.Bapacut;
import tw.gov.bli.ba.domain.Bapadchk;
import tw.gov.bli.ba.domain.Bapadcslvl;
import tw.gov.bli.ba.domain.Bapainspect;
import tw.gov.bli.ba.domain.Bapairr;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.maint.cases.AdviceMaintCase;
import tw.gov.bli.ba.maint.cases.AvgAmtMonMaintCase;
import tw.gov.bli.ba.maint.cases.BaletterCodeCase;
import tw.gov.bli.ba.maint.cases.BasicAmtMaintCase;
import tw.gov.bli.ba.maint.cases.CheckMarkMaintCase;
import tw.gov.bli.ba.maint.cases.CpiDtlMaintCase;
import tw.gov.bli.ba.maint.cases.CpiRecMaintCase;
import tw.gov.bli.ba.maint.cases.DeductItemMaintCase;
import tw.gov.bli.ba.maint.cases.DisabledReplacementRatioMaintCase;
import tw.gov.bli.ba.maint.cases.PreviewConditionMaintCase;
import tw.gov.bli.ba.maint.cases.ReplacementRatioMaintCase;
import tw.gov.bli.ba.maint.cases.ResetPaymentParameterCase;
import tw.gov.bli.ba.maint.cases.SurvivorReplacementRatioMaintCase;
import tw.gov.bli.ba.maint.forms.DeductItemMaintDetailForm;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.util.FrameworkLogUtil;

/**
 * Service for 維護作業
 * 
 * @author Goston
 */
public class MaintService {
    private static Log log = LogFactory.getLog(MaintService.class);

    private BapainspectDao bapainspectDao;
    private BapadchkDao bapadchkDao;
    private BapadcslvlDao bapadcslvlDao;
    private BanotifyDao banotifyDao;
    private BapacutDao bapacutDao;
    private BacpidtlDao bacpidtlDao;
    private BacpirecDao bacpirecDao;
    private BabasicamtDao babasicamtDao;
    private BapaavgmonDao bapaavgmonDao;
    private BapairrDao bapairrDao;
    private BalettercodeDao balettercodeDao;
    private BaappbaseDao baappbaseDao;
    private BaappexpandDao baappexpandDao;
    private UpdateService updateService;

    /**
     * 依傳入的 給付種類 自 先抽對象條件參數檔 (<code>BAPAINSPECT</code>) 取得 <code>PreviewConditionMaintCase</code><br>
     * for : 維護作業 - 先抽對象維護作業<br>
     * 
     * @param payKind 給付種類
     * @return <code>PreviewConditionMaintCase</code>; 若無資料回傳 <code>null</code>
     */
    public PreviewConditionMaintCase getPreviewConditionMaintCaseBy(String payCode) {
        log.debug("執行 MaintService.getPreviewConditionMaintCaseBy() 開始 ...");

        Bapainspect bapainspectData = bapainspectDao.selectDataBy(payCode);

        PreviewConditionMaintCase caseData = null;

        if (bapainspectData != null) {
            caseData = new PreviewConditionMaintCase();
            BeanUtility.copyProperties(caseData, bapainspectData);
        }

        log.debug("執行 MaintService.getPreviewConditionMaintCaseBy() 完成 ...");

        return caseData;
    }

    /**
     * 依傳入的 <code>PreviewConditionMaintCase</code> 資料更新至 先抽對象條件參數檔 (<code>BAPAINSPECT</code>)<br>
     * for : 維護作業 - 先抽對象維護作業<br>
     * 
     * @param caseData <code>PreviewConditionMaintCase</code>
     */
    public void savePreviewConditionMaintData(PreviewConditionMaintCase caseData, UserBean userData) {
        log.debug("執行 MaintService.savePreviewConditionMaintData() 開始 ...");

        if (caseData != null && userData != null) {
            Bapainspect bapainspectData = new Bapainspect();

            BeanUtility.copyProperties(bapainspectData, caseData);

            bapainspectData.setUpdUser(userData.getUserId()); // 異動者代號
            bapainspectData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            // 先刪除再新增
            bapainspectDao.deleteData(bapainspectData.getPayCode());
            bapainspectDao.insertData(bapainspectData);
        }

        log.debug("執行 MaintService.savePreviewConditionMaintData() 完成 ...");
    }

    /**
     * 依傳入的 給付種類 自 扣減參數檔 (<code>BAPACUT</code>) 取得 <code>DeductItemMaintCase</code><br>
     * for : 維護作業 - 扣減參數維護作業<br>
     * 
     * @param payKind 給付種類
     * @return <code>DeductItemMaintCase</code>; 若無資料回傳 <code>null</code>
     */
    public List<DeductItemMaintCase> getDeductItemMaintCaseBy(String payCode) {
        log.debug("執行 MaintService.getDeductItemMaintCaseBy() 開始 ...");

        List<DeductItemMaintCase> applyList = new ArrayList<DeductItemMaintCase>();
        List<Bapacut> list = bapacutDao.selectData(payCode);

        for (int i = 0; i < list.size(); i++) {
            Bapacut bapacut = (Bapacut) list.get(i);

            DeductItemMaintCase applyCase = new DeductItemMaintCase();
            BeanUtility.copyProperties(applyCase, bapacut);
            applyList.add(applyCase);
        }

        log.debug("執行 MaintService.getDeductItemMaintCaseBy() 完成 ...");

        return applyList;
    }

    /**
     * 依傳入的 <code>DeductItemMaintCase</code> 資料更新至 扣減參數檔 (<code>BAPACUT</code>)<br>
     * for : 維護作業 - 扣減參數維護作業<br>
     * 
     * @param caseData <code>PreviewConditionMaintCase</code>
     */
    public void saveDeductItemMaintData(DeductItemMaintCase caseData, UserBean userData, DeductItemMaintDetailForm iform) {
        log.debug("執行 MaintService.saveDeductItemMaintData() 開始 ...");
        // DeductItemMaintCase deductCase = (DeductItemMaintCase)caseData.getData().get(0);
        // 先刪除再新增
        bapacutDao.deleteData(caseData.getPayCode());
        String[] cutSeqList = iform.getCutSeq();

        if (caseData != null && userData != null) {
            for (int i = 0; i < caseData.getData().size(); i++) {
                Bapacut bapacutData = new Bapacut();
                DeductItemMaintCase deductItemMaintCase = (DeductItemMaintCase) caseData.getData().get(i);
                BeanUtility.copyProperties(bapacutData, deductItemMaintCase);
                bapacutData.setCutSeq(cutSeqList[i]);
                bapacutData.setUpdUser(userData.getEmpNo()); // 異動者代號
                bapacutData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

                bapacutDao.insertData(bapacutData);
            }
        }

        log.debug("執行 MaintService.saveDeductItemMaintData() 完成 ...");
    }

    /**
     * 依傳入的 給付種類 自 編審註記檔 (<code>BAPADCHK</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 編審註記維護作業<br>
     * 
     * @param chkObj 給付種類
     * @param chkGroup 給付類別
     * @param chkCode 編審註記代號
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public List<CheckMarkMaintCase> getCheckMarkMaintQueryCaseBy(String chkObj, String chkGroup, String chkCode) {
        log.debug("執行 MaintService.getCheckMarkMaintQueryCaseBy() 開始 ...");

        List<Bapadchk> bapadchkList = bapadchkDao.selectData(chkObj, chkGroup, chkCode);
        List<CheckMarkMaintCase> caseList = new ArrayList<CheckMarkMaintCase>();
        for (int i = 0; i < bapadchkList.size(); i++) {
            Bapadchk obj = bapadchkList.get(i);
            CheckMarkMaintCase caseObj = new CheckMarkMaintCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseList.add(caseObj);
        }
        log.debug("執行 MaintService.getCheckMarkMaintQueryCaseBy() 完成 ...");

        return caseList;
    }

    /**
     * 依傳入的 <code>Bapadchk</code> 資料更新至 編審註記檔 (<code>BAPADCHK</code>)<br>
     * for : 維護作業 - 編審註記維護作業<br>
     * 
     * @param caseData <code>Bapadchk</code>
     */
    public void saveCheckMarkMaintData(Bapadchk detailData, UserBean userData) {
        log.debug("執行 MaintService.saveCheckMarkMaintData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapadchk bapadchkData = new Bapadchk();

            BeanUtility.copyProperties(bapadchkData, detailData);

            bapadchkData.setChkTyp("A");
            bapadchkData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapadchkData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            bapadchkDao.insertData(bapadchkData);
        }

        log.debug("執行 MaintService.saveCheckMarkMaintData() 完成 ...");
    }

    /**
     * 依傳入的 給付種類 自 編審註記檔 (<code>BAPADCHK</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 編審註記維護作業<br>
     * 
     * @param chkTyp 編審註記種類
     * @param chkObj 給付種類
     * @param chkGroup 給付類別
     * @param chkCode 編審註記代號
     * @param valibDate 有效日期起
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public Bapadchk getCheckMarkMaintForUpdateCaseBy(String chkTyp, String chkObj, String chkGroup, String chkCode, String valibDate) {
        log.debug("執行 MaintService.getCheckMarkMaintForUpdateCaseBy() 開始 ...");

        Bapadchk bapadchkData = bapadchkDao.selectSingleForUpdateData(chkTyp, chkObj, chkGroup, chkCode, valibDate);

        log.debug("執行 MaintService.getCheckMarkMaintForUpdateCaseBy() 完成 ...");

        return bapadchkData;
    }

    /**
     * 依傳入的 <code>Bapadchk</code> 資料更新至 編審註記檔 (<code>BAPADCHK</code>)<br>
     * for : 維護作業 - 編審註記維護作業<br>
     * 
     * @param caseData <code>Bapadchk</code>
     */
    public void updateCheckMarkMaintData(Bapadchk detailData, UserBean userData) {
        log.debug("執行 MaintService.updateCheckMarkMaintData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapadchk bapadchkData = new Bapadchk();

            BeanUtility.copyProperties(bapadchkData, detailData);

            bapadchkData.setChkTyp("A");
            bapadchkData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapadchkData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            bapadchkDao.updateData(bapadchkData);
        }

        log.debug("執行 MaintService.updateCheckMarkMaintData() 完成 ...");
    }

    /**
     * 依傳入的 給付種類 自 核定通知書檔 (<code>BANOTIFY</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 核定通知書維護作業<br>
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public List<AdviceMaintCase> getAdviceMaintQueryCaseBy(String payCode, String authTyp) {
        log.debug("執行 MaintService.getAdviceMaintQueryCaseBy() 開始 ...");

        List<AdviceMaintCase> banotifyData = banotifyDao.selectData(payCode, authTyp, "0", "01"); // 主旨
        for (AdviceMaintCase caseData : banotifyData) {
            if (StringUtils.isNotBlank(caseData.getCrtTime()) && caseData.getCrtTime().length() > 7) {
                caseData.setCrtTime(DateUtility.changeDateType(caseData.getCrtTime().substring(0, 8)));
            }
            if (StringUtils.isNotBlank(caseData.getUpdTime()) && caseData.getUpdTime().length() > 7) {
                caseData.setUpdTime(DateUtility.changeDateType(caseData.getUpdTime().substring(0, 8)));
            }
        }

        for (int i = 0; i < banotifyData.size(); i++) {
            AdviceMaintCase purposeData = banotifyData.get(i);

            AdviceMaintCase explainData = banotifyDao.selectDataContExplain(purposeData.getPayCode(), purposeData.getAuthTyp(), "1", "01"); // 說明
            if (explainData != null) {
                purposeData.setDataContExplain(explainData.getDataContExplain());
            }
        }

        log.debug("執行 MaintService.getAdviceMaintQueryCaseBy() 完成 ...");

        return banotifyData;
    }

    /**
     * 依傳入的 給付種類 自 核定通知書檔 (<code>BANOTIFY</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 核定通知書維護作業<br>
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public List<AdviceMaintCase> getAdviceMaintForUpdateCaseBy(String payCode, String authTyp, String dataTyp) {
        log.debug("執行 MaintService.getAdviceMaintForUpdateCaseBy() 開始 ...");

        List<AdviceMaintCase> banotifyData = banotifyDao.selectSingleForUpdateData(payCode, authTyp, dataTyp);

        log.debug("執行 MaintService.getAdviceMaintForUpdateCaseBy() 完成 ...");

        return banotifyData;
    }

    /**
     * 依傳入的 <code>Banotify</code> 資料更新至 核定通知書檔 (<code>BANOTIFY</code>)<br>
     * for : 維護作業 - 核定通知書維護作業<br>
     * 
     * @param caseData <code>Banotify</code>
     */
    public void saveAdviceMaintData(AdviceMaintCase detailData, List<AdviceMaintCase> banotifyList, UserBean userData) {
        log.debug("執行 MaintService.saveAdviceMaintData() 開始 ...");

        if (detailData != null && userData != null) {
            AdviceMaintCase banotifyDataPurpose = new AdviceMaintCase();
            AdviceMaintCase banotifyDataExplain = new AdviceMaintCase();

            BeanUtility.copyProperties(banotifyDataPurpose, detailData);
            BeanUtility.copyProperties(banotifyDataExplain, detailData);

            // 主旨
            banotifyDataPurpose.setDataTyp("0"); // 資料區分
            banotifyDataPurpose.setDataSeqNo("01");
            banotifyDataPurpose.setDataCont(detailData.getDataContPurpose());
            banotifyDataPurpose.setActMk("Y"); // 是否有效
            banotifyDataPurpose.setCrtUser(userData.getEmpNo()); // 新增者代號
            banotifyDataPurpose.setCrtTime(DateUtility.getNowWestDateTime(true)); // 新增日期時間
            banotifyDataPurpose.setUpdUser(userData.getEmpNo()); // 異動者代號
            banotifyDataPurpose.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            banotifyDao.insertData(banotifyDataPurpose);

            // 說明
            for (int seqNo = 0; seqNo < banotifyList.size(); seqNo++) {
                AdviceMaintCase banotify = banotifyList.get(seqNo);

                banotifyDataExplain.setDataTyp("1"); // 資料區分
                banotifyDataExplain.setDataSeqNo(StringUtility.chtLeftPad(Integer.toString(seqNo + 1), 2, "0"));
                banotifyDataExplain.setDataCont(banotify.getDataCont());
                banotifyDataExplain.setActMk("Y"); // 是否有效
                banotifyDataExplain.setCrtUser(userData.getEmpNo()); // 新增者代號
                banotifyDataExplain.setCrtTime(DateUtility.getNowWestDateTime(true)); // 新增日期時間
                banotifyDataExplain.setUpdUser(userData.getEmpNo()); // 異動者代號
                banotifyDataExplain.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

                banotifyDao.insertData(banotifyDataExplain);
            }

        }

        log.debug("執行 MaintService.saveAdviceMaintData() 完成 ...");
    }

    /**
     * 依傳入的 <code>Banotify</code> 資料更新至 核定通知書檔 (<code>BANOTIFY</code>)<br>
     * for : 維護作業 - 核定通知書維護作業<br>
     * 
     * @param caseData <code>Banotify</code>
     */
    public void updateAdviceMaintData(AdviceMaintCase detailData, List<AdviceMaintCase> banotifyList, UserBean userData) {
        log.debug("執行 MaintService.updateAdviceMaintData() 開始 ...");

        if (detailData != null && userData != null) {
            banotifyDao.deleteData(detailData);

            AdviceMaintCase banotifyDataPurpose = new AdviceMaintCase();
            AdviceMaintCase banotifyDataExplain = new AdviceMaintCase();

            BeanUtility.copyProperties(banotifyDataPurpose, detailData);
            BeanUtility.copyProperties(banotifyDataExplain, detailData);

            // 主旨
            banotifyDataPurpose.setDataTyp("0"); // 資料區分
            banotifyDataPurpose.setDataSeqNo("01");
            banotifyDataPurpose.setDataCont(detailData.getDataContPurpose());
            banotifyDataPurpose.setUpdUser(userData.getEmpNo()); // 異動者代號
            banotifyDataPurpose.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間
            banotifyDataPurpose.setCrtUser(detailData.getCrtUser()); // 新增者代號
            banotifyDataPurpose.setCrtTime(detailData.getCrtTime()); // 新增日期時間

            banotifyDao.insertData(banotifyDataPurpose);

            // 說明
            for (int seqNo = 0; seqNo < banotifyList.size(); seqNo++) {
                AdviceMaintCase banotify = banotifyList.get(seqNo);

                banotifyDataExplain.setDataTyp("1"); // 資料區分
                banotifyDataExplain.setDataSeqNo(StringUtility.chtLeftPad(Integer.toString(seqNo + 1), 2, "0"));
                banotifyDataExplain.setDataCont(banotify.getDataCont());
                banotifyDataExplain.setActMk(banotifyDataPurpose.getActMk()); // 是否有效
                banotifyDataExplain.setUpdUser(userData.getEmpNo()); // 異動者代號
                banotifyDataExplain.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間
                banotifyDataExplain.setCrtUser(detailData.getCrtUser()); // 新增者代號
                banotifyDataExplain.setCrtTime(detailData.getCrtTime()); // 新增日期時間

                banotifyDao.insertData(banotifyDataExplain);
            }
        }

        log.debug("執行 MaintService.updateAdviceMaintData() 完成 ...");
    }

    /**
     * 依傳入的 <code>Bapadcslvl</code> 資料更新至 決行層級檔 (<code>BAPADCSLVL</code>)<br>
     * for : 維護作業 - 決行層級維護作業<br>
     * 
     * @param caseData <code>Bapadcslvl</code>
     */
    public void saveDecisionLevelMaintData(Bapadcslvl detailData, UserBean userData) {
        log.debug("執行 MaintService.saveDecisionLevelMaintData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapadcslvl bapadcslvlData = new Bapadcslvl();

            BeanUtility.copyProperties(bapadcslvlData, detailData);

            bapadcslvlData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapadcslvlData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            bapadcslvlDao.insertData(bapadcslvlData);
        }

        log.debug("執行 MaintService.saveDecisionLevelMaintData() 完成 ...");
    }

    /**
     * 依傳入的 給付種類 自 決行層級檔 (<code>BAPADCSLVL</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 決行層級維護作業<br>
     * 
     * @param payCode 給付種類
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public List<Bapadcslvl> getDecisionLevelMaintQueryCaseBy(String payCode, String payKind, String rechklvl) {
        log.debug("執行 MaintService.getDecisionLevelMaintQueryCaseBy() 開始 ...");

        List<Bapadcslvl> bapadcslvlData = bapadcslvlDao.selectData(payCode, payKind, rechklvl);

        log.debug("執行 MaintService.getDecisionLevelMaintQueryCaseBy() 完成 ...");

        return bapadcslvlData;
    }

    /**
     * 依傳入的 給付種類 自 決行層級檔 (<code>BAPADCSLVL</code>) 取得 <code>Bapadcslvl</code><br>
     * for : 維護作業 - 決行層級維護作業<br>
     * 
     * @param payCode 給付種類
     * @param rechklvl 決行層級
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public Bapadcslvl getDecisionLevelMaintForUpdateCaseBy(String payCode, String payKind, String rechklvl) {
        log.debug("執行 MaintService.getDecisionLevelMaintForUpdateCaseBy() 開始 ...");

        Bapadcslvl bapadcslvlData = bapadcslvlDao.selectSingleForUpdateData(payCode, payKind, rechklvl);

        log.debug("執行 MaintService.getDecisionLevelMaintForUpdateCaseBy() 完成 ...");

        return bapadcslvlData;
    }

    /**
     * 依傳入的 <code>Bapadcslvl</code> 資料更新至 決行層級檔 (<code>BAPADCSLVL</code>)<br>
     * for : 維護作業 - 決行層級維護作業<br>
     * 
     * @param caseData <code>Bapadcslvl</code>
     */
    public void updateDecisionLevelMaintData(Bapadcslvl detailData, UserBean userData) {
        log.debug("執行 MaintService.updateDecisionLevelMaintData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapadcslvl bapadcslvlData = new Bapadcslvl();

            BeanUtility.copyProperties(bapadcslvlData, detailData);

            bapadcslvlData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapadcslvlData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            bapadcslvlDao.updateData(bapadcslvlData);
        }

        log.debug("執行 MaintService.updateDecisionLevelMaintData() 完成 ...");
    }

    /**
     * 依傳入的 給付種類 自 決行層級檔 (<code>BAPADCSLVL</code>) <br>
     * for : 維護作業 - 決行層級維護作業<br>
     * 
     * @param payCode 給付別
     * @param payKind 給付種類
     * @param rechklvl 決行層級
     */
    public void deleteDecisionLevelMaintCaseBy(Bapadcslvl detailData) {
        log.debug("執行 MaintService.deleteDecisionLevelMaintCaseBy() 開始 ...");

        bapadcslvlDao.deleteData(detailData);

        log.debug("執行 MaintService.deleteDecisionLevelMaintCaseBy() 完成 ...");
    }

    /**
     * 依傳入的 指數年度 自 物價指數調整明細檔 (<code>BACPIDTL</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 編審註記維護作業<br>
     * 
     * @param issuYear 核定年度
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public List<CpiDtlMaintCase> getCpiDtlMaintQueryCaseBy(String issuYear) {
        log.debug("執行 MaintService.getCpiDtlMaintQueryCaseBy() 開始 ...");

        List<Bacpidtl> bacpidtlList = bacpidtlDao.selectData(issuYear);
        List<CpiDtlMaintCase> caseList = new ArrayList<CpiDtlMaintCase>();
        for (int i = 0; i < bacpidtlList.size(); i++) {
            Bacpidtl obj = bacpidtlList.get(i);
            obj.setIssuYear(DateUtility.changeWestYearType(obj.getIssuYear()));
            obj.setAdjYearS(DateUtility.changeWestYearType(obj.getAdjYearS()));
            obj.setAdjYearE(DateUtility.changeWestYearType(obj.getAdjYearE()));
            obj.setCpiYear(DateUtility.changeWestYearType(obj.getCpiYear()));
            obj.setCpiYearE(DateUtility.changeWestYearType(obj.getCpiYearE()));
            obj.setSysDate(DateUtility.changeWestYearMonthType(obj.getSysDate()));
            CpiDtlMaintCase caseObj = new CpiDtlMaintCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseList.add(caseObj);
        }
        log.debug("執行 MaintService.getCpiDtlMaintQueryCaseBy() 完成 ...");

        return caseList;
    }

    /**
     * 依傳入的年度 自 物價指數調整明細檔 (<code>BACPIDTL</code>) 取得 <code>BACPIDTL</code><br>
     * for : 維護作業 - 物價指數調整明細檔 確認資料是否重複<br>
     * 
     * @param ADJYEAR 核定年度
     * @return <code>BACPIDTL</code>; 若無資料回傳 <code>null</code>
     */
    public Bacpidtl getCpiDtlMaintForCheckSaveCaseBy(String issuYear, String adjYear) {
        log.debug("執行 MaintService.getCpiDtlMaintForCheckSaveCaseBy() 開始 ...");

        Bacpidtl bacpidtlData = bacpidtlDao.selectSingleForCheckSaveDataForDtl(issuYear, adjYear);

        log.debug("執行 MaintService.getCpiDtlMaintForCheckSaveCaseBy() 完成 ...");

        return bacpidtlData;
    }

    /**
     * 取得 物價指數調整明細檔 (<code>BACPIDTL</code>) 取得 <code>BACPIDTL</code><br>
     * for : 維護作業 - 物價指數調整紀錄檔 查詢是否有今年度資料及ADJMK狀態<br>
     * 
     * @return <code>BACPIDTL</code>; 若無資料回傳 <code>null</code>
     */
    public List<CpiRecMaintCase> getCpiDtlMaintForCpiRecCaseBy() {
        log.debug("執行 MaintService.getCpiDtlMaintForCpiRecCaseBy() 開始 ...");

        List<Bacpidtl> bacpidtlList = bacpidtlDao.selectSingleForCpiDltForCpiRecData();
        List<CpiRecMaintCase> caseList = new ArrayList<CpiRecMaintCase>();
        for (int i = 0; i < bacpidtlList.size(); i++) {
            Bacpidtl obj = bacpidtlList.get(i);
            obj.setIssuYear(DateUtility.changeWestYearType(obj.getIssuYear()));
            obj.setAdjYearS(DateUtility.changeWestYearType(obj.getAdjYearS()));
            obj.setAdjYearE(DateUtility.changeWestYearType(obj.getAdjYearE()));
            obj.setCpiYear(DateUtility.changeWestYearType(obj.getCpiYear()));
            obj.setCpiYearE(DateUtility.changeWestYearType(obj.getCpiYearE()));
            obj.setSysDate(DateUtility.changeWestYearMonthType(obj.getSysDate()));
            CpiRecMaintCase caseObj = new CpiRecMaintCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseObj.setCpiYearB(obj.getCpiYear());
            caseObj.setAdjCpi(obj.getCpIndex());
            caseList.add(caseObj);
        }

        log.debug("執行 MaintService.getCpiDtlMaintForCpiRecCaseBy() 完成 ...");

        return caseList;
    }

    /**
     * 取得 物價指數調整明細檔 (<code>BACPIDTL</code>) 取得 <code>BACPIDTL</code><br>
     * for : 維護作業 - 物價指數調整紀錄檔 查詢是否有今年度資料及ADJMK狀態<br>
     * 
     * @return <code>BACPIDTL</code>; 若無資料回傳 <code>null</code>
     */
    public List<CpiRecMaintCase> selectInsertDataForCpiRec(String issuYear) {
        log.debug("執行 MaintService.selectInsertDataForCpiRec() 開始 ...");

        List<Bacpidtl> bacpidtlList = bacpidtlDao.selectInsertDataForCpiRec(issuYear);
        List<CpiRecMaintCase> caseList = new ArrayList<CpiRecMaintCase>();
        for (int i = 0; i < bacpidtlList.size(); i++) {
            Bacpidtl obj = bacpidtlList.get(i);
            CpiRecMaintCase caseObj = new CpiRecMaintCase();

            BeanUtility.copyProperties(caseObj, obj);
            caseObj.setCpiYearB(obj.getCpiYear());
            caseObj.setAdjCpi(obj.getCpIndex());

            caseList.add(caseObj);
        }

        log.debug("執行 MaintService.getCpiDtlMaintForCpiRecCaseBy() 完成 ...");

        return caseList;
    }

    /**
     * 依傳入的 <code>Bacpidtl</code> 資料更新至 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * for : 維護作業 - 物價指數調整明細新增作業<br>
     * 
     * @param caseData <code>Bacpidtl</code>
     */
    public void saveCpiDtlMaintData(Bacpidtl detailData, UserBean userData, BigDecimal newSeqNo) {
        log.debug("執行 MaintService.saveCpiDtlMaintData() 開始 ...");

        if (detailData != null && userData != null) {

            detailData.setCrtUser(userData.getEmpNo()); // 異動者代號
            detailData.setCrtTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間
            detailData.setSeqNo(newSeqNo);

            for (int i = Integer.parseInt(detailData.getAdjYearS()); i < Integer.parseInt(detailData.getAdjYearE()) + 1; i++) {

                detailData.setAdjYear(String.valueOf(i));
                bacpidtlDao.insertData(detailData);

            }

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doInsertLog(detailData);
            }
        }

        log.debug("執行 MaintService.saveCpiDtlMaintData() 完成 ...");
    }

    /**
     * 依傳入的 <code>Bacpidtl</code> 資料更新至 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * for : 維護作業 - 物價指數調整明細修改作業<br>
     * 
     * @param caseData <code>Bacpidtl</code>
     */
    public void updateCpiDtlMaintData(CpiDtlMaintCase newData, CpiDtlMaintCase oldData, UserBean userData) {
        log.debug("執行 MaintService.updateCpiDtlMaintData() 開始 ...");

        if (newData != null) {

            Bacpidtl bacpidtlData = new Bacpidtl();

            BeanUtility.copyProperties(bacpidtlData, newData);

            // new
            bacpidtlData.setIssuYear(DateUtility.changeChineseYearType(bacpidtlData.getIssuYear()));
            bacpidtlData.setCpiYear(DateUtility.changeChineseYearType(bacpidtlData.getCpiYear()));
            bacpidtlData.setCpiYearE(DateUtility.changeChineseYearType(bacpidtlData.getCpiYearE()));
            bacpidtlData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bacpidtlData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間
            // old
            bacpidtlData.setOldCpiYear(DateUtility.changeChineseYearType(oldData.getCpiYear()));
            bacpidtlData.setOldCpiYearE(DateUtility.changeChineseYearType(oldData.getCpiYearE()));
            bacpidtlData.setOldCpiB(oldData.getCpiB());
            bacpidtlData.setOldCpiE(oldData.getCpiE());
            bacpidtlData.setOldCpIndex(oldData.getCpIndex());

            bacpidtlDao.updateData(bacpidtlData);

            // Update MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(oldData, newData);
            }
        }

        log.debug("執行 MaintService.updateCpiDtlMaintData() 完成 ...");
    }

    /**
     * 依傳入的 <code>Bacpidtl</code> 資料更新至 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * for : 維護作業 - 物價指數調整明細修改作業<br>
     * 
     * @param caseData <code>Bacpidtl</code>
     */
    public void updateAdjYear(Bacpidtl detailData, UserBean userData) {
        log.debug("執行 MaintService.updateAdjYear() 開始 ...");

        if (detailData != null && userData != null) {
            Bacpidtl bacpidtlData = new Bacpidtl();

            BeanUtility.copyProperties(bacpidtlData, detailData);

            bacpidtlData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bacpidtlData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            List<Bacpidtl> bacpidtlDataBefore = bacpidtlDao.selectListForForCpiRec(bacpidtlData.getIssuYear());

            bacpidtlDao.updateAdjMk(bacpidtlData);

            List<Bacpidtl> bacpidtlDataAfter = bacpidtlDao.selectListForForCpiRec(bacpidtlData.getIssuYear());

            // UpdateAdjYear MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateListLog(bacpidtlDataBefore, bacpidtlDataAfter);
            }
        }

        log.debug("執行 MaintService.updateAdjUpdate() 完成 ...");
    }

    /**
     * 刪除 物價指數調整明細檔 資料
     */
    public void deleteBacpidtlData(CpiDtlMaintCase caseData) {

        log.debug("Start to Delete BACPIDTL ...");

        String issuYear = DateUtility.changeChineseYearType(caseData.getIssuYear()); // 核定年度
        String cpiYear = DateUtility.changeChineseYearType(caseData.getCpiYear()); // 指數年度(起)
        String cpiYearE = DateUtility.changeChineseYearType(caseData.getCpiYearE()); // 指數年度(迄)

        // 取得欲刪除之申請年度資料
        Bacpidtl bacpidtl = new Bacpidtl();
        bacpidtl.setIssuYear(issuYear);
        bacpidtl.setCpiYear(cpiYear);
        bacpidtl.setCpiYearE(cpiYearE);
        bacpidtl.setCpiB(caseData.getCpiB());
        bacpidtl.setCpiE(caseData.getCpiE());
        bacpidtl.setCpIndex(caseData.getCpIndex());
        List<Bacpidtl> deleteDataList = bacpidtlDao.selectUpdDelDataBy(bacpidtl);

        // 取得欲刪除之申請年度
        // StringBuffer adjYearBuf = new StringBuffer();
        String[] adjYear = new String[deleteDataList.size()];
        for (int i = 0; i < deleteDataList.size(); i++) {
            adjYear[i] = deleteDataList.get(i).getAdjYear();
            // if(i != deleteDataList.size()-1){
            // adjYearBuf.append("'"+deleteDataList.get(i).getAdjYear()+"'"+",");
            // }else{
            // adjYearBuf.append("'"+deleteDataList.get(i).getAdjYear()+"'");
            // }
        }

        // String adjYear = "("+adjYearBuf.toString()+")"; // 欲刪除之申請年度
        String eqType = "in";

        // 刪除 物價指數調整明細檔
        bacpidtlDao.deleteData(issuYear, eqType, adjYear, cpiYear, cpiYearE, caseData.getCpiB(), caseData.getCpiE(), caseData.getCpIndex());

        // Delete MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteListLog(deleteDataList);
        }
        log.debug("Delete BACPIDTL Finished ...");

    }

    /**
     * 依傳入的 調整年度 自 物價指數調整紀錄檔 (<code>BACPIREC</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 物價指數調整紀錄維護作業<br>
     * 
     * @param ADJYear 調整年度
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public List<CpiRecMaintCase> getCpiRecMaintQueryCaseBy(String issuYear) {
        log.debug("執行 MaintService.getCpiRecMaintQueryCaseBy() 開始 ...");

        List<Bacpirec> bacpirecList = bacpirecDao.selectData(issuYear);
        List<CpiRecMaintCase> caseList = new ArrayList<CpiRecMaintCase>();
        for (int i = 0; i < bacpirecList.size(); i++) {
            Bacpirec obj = bacpirecList.get(i);
            obj.setIssuYear(DateUtility.changeWestYearType(obj.getIssuYear()));
            obj.setAdjYearS(DateUtility.changeWestYearType(obj.getAdjYearS()));
            obj.setAdjYearE(DateUtility.changeWestYearType(obj.getAdjYearE()));
            obj.setAdjYmB(DateUtility.changeWestYearMonthType(obj.getAdjYmB()));
            obj.setSysDate(DateUtility.changeWestYearMonthType(obj.getSysDate()));
            CpiRecMaintCase caseObj = new CpiRecMaintCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseList.add(caseObj);
        }
        log.debug("執行 MaintService.getCpiRecMaintQueryCaseBy() 完成 ...");

        return caseList;
    }

    /**
     * 依傳入的 指數年度 自 物價指數調整紀錄檔 (<code>BACPIREC</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 物價指數調整紀錄維護作業<br>
     * 
     * @param cpiYear 指數年度
     * @param cpIndex 物價指數成長率
     * @param reqRpno 報請核定文號
     * @param issuRpno 核定文號
     * @param issuDesc 核定結果
     * @param updUser 員工編號
     * @param updTime 系統日期時間
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public List<CpiRecMaintCase> getCpiRecMaintForUpdateCaseBy(String issuYear) {
        log.debug("執行 MaintService.getCpiRecMaintForUpdateCaseBy() 開始 ...");

        List<Bacpirec> bacpirecList = bacpirecDao.selectSingleForUpdateData(issuYear);
        List<CpiRecMaintCase> caseList = new ArrayList<CpiRecMaintCase>();
        for (int i = 0; i < bacpirecList.size(); i++) {
            Bacpirec obj = bacpirecList.get(i);
            obj.setIssuYear(DateUtility.changeWestYearType(obj.getIssuYear()));
            obj.setAdjYearS(DateUtility.changeWestYearType(obj.getAdjYearS()));
            obj.setAdjYearE(DateUtility.changeWestYearType(obj.getAdjYearE()));
            obj.setCpiYearE(DateUtility.changeWestYearType(obj.getCpiYearE()));
            obj.setCpiYearB(DateUtility.changeWestYearType(obj.getCpiYearB()));
            obj.setAdjYmB(DateUtility.changeWestYearMonthType(obj.getAdjYmB()));
            obj.setSysDate(DateUtility.changeWestYearMonthType(obj.getSysDate()));
            CpiRecMaintCase caseObj = new CpiRecMaintCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseList.add(caseObj);
        }

        log.debug("執行 MaintService.getCpiRecMaintForUpdateCaseBy() 完成 ...");

        return caseList;
    }

    /**
     * 依傳入的 <code>Bacpirec</code> 資料更新至 物價指數調整紀錄檔 (<code>BACPIREC</code>)<br>
     * for : 維護作業 - 物價指數調整紀錄修改作業<br>
     * 
     * @param caseData <code>Bacpirec</code>
     */
    public void updateCpiRecMaintData(Bacpirec detailData, UserBean userData) {
        log.debug("執行 MaintService.updateCpiRecMaintData() 開始 ...");

        if (detailData != null && userData != null) {

            detailData.setUpdUser(userData.getEmpNo()); // 異動者代號
            detailData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            List<Bacpidtl> bacpidtlDataBefore = bacpidtlDao.selectListForForCpiRec(detailData.getIssuYear());

            bacpirecDao.updateData(detailData);

            List<Bacpidtl> bacpidtlDataAfter = bacpidtlDao.selectListForForCpiRec(detailData.getIssuYear());

            // Update MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateListLog(bacpidtlDataBefore, bacpidtlDataAfter);
            }
        }

        log.debug("執行 MaintService.updateCpiRecMaintData() 完成 ...");
    }

    /**
     * 刪除 物價指數調整紀錄檔 資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     */
    public void deleteBacpirecData(UserBean userData, CpiRecMaintCase caseData, Bacpidtl detailData) {
        // 刪除 給付主檔
        // [
        log.debug("Start to Delete BACPIREC ...");

        if (caseData != null) {

            String issuYear = DateUtility.changeChineseYearType(caseData.getIssuYear());

            // 取得改前值物件
            List<Bacpirec> bacpidtlDataListBefore = bacpirecDao.checkData(issuYear);
            // 刪除 物價指數調整紀錄檔
            bacpirecDao.deleteData(issuYear);

            // Delete MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doDeleteListLog(bacpidtlDataListBefore);
            }

            // update BACPIDTL
            if (detailData != null) {
                Bacpidtl bacpidtlData = new Bacpidtl();

                BeanUtility.copyProperties(bacpidtlData, detailData);

                bacpidtlData.setUpdUser(userData.getEmpNo()); // 異動者代號
                bacpidtlData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

                List<Bacpidtl> bacpidtlDataBefore = bacpidtlDao.selectListForForCpiRec(bacpidtlData.getIssuYear());

                bacpidtlDao.updateAdjMk(bacpidtlData);

                List<Bacpidtl> bacpidtlDataAfter = bacpidtlDao.selectListForForCpiRec(bacpidtlData.getIssuYear());

                // UpdateAdjYear MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateListLog(bacpidtlDataBefore, bacpidtlDataAfter);
                }
            }

            log.debug("Delete BACPIDTL Finished ...");

        }
        else {

            log.debug("Delete BACPIDTL fail...");
        }

        // ]
    }

    /**
     * 依傳入的 指數年度 自 物價指數調整明細檔 (<code>BACPIREC</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 編審註記維護作業<br>
     * 
     * @param adjYear 指數年度
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public List<CpiRecMaintCase> getCpiRecMaintDetailCaseBy(String issuYear) {
        log.debug("執行 MaintService.getCpiRecMaintDetailCaseBy() 開始 ...");

        List<Bacpirec> bacpirecList = bacpirecDao.checkData(issuYear);
        List<CpiRecMaintCase> caseList = new ArrayList<CpiRecMaintCase>();
        for (int i = 0; i < bacpirecList.size(); i++) {
            Bacpirec obj = bacpirecList.get(i);
            obj.setAdjYear(DateUtility.changeWestYearType(obj.getAdjYear()));
            obj.setCpiYearB(DateUtility.changeWestYearType(obj.getCpiYearB()));
            obj.setCpiYearE(DateUtility.changeWestYearType(obj.getCpiYearE()));
            obj.setAdjYmB(DateUtility.changeWestYearMonthType(obj.getAdjYmB()));
            obj.setSysDate(DateUtility.changeWestYearMonthType(obj.getSysDate()));
            CpiRecMaintCase caseObj = new CpiRecMaintCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseList.add(caseObj);
        }
        log.debug("執行 MaintService.getCpiRecMaintDetailCaseBy() 完成 ...");

        return caseList;
    }

    /**
     * 依傳入的 <code>Bacpirec</code> 資料更新至 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * for : 維護作業 - 物價指數調整明細新增作業<br>
     * 
     * @param caseData <code>Bacpirec</code>
     */
    public void saveCpiRecMaintData(List<CpiRecMaintCase> insertList, Bacpidtl detailData, String adjYmB, UserBean userData) {
        log.debug("執行 MaintService.saveCpiRecMaintData() 開始 ...");

        if (insertList.size() > 0) {

            for (CpiRecMaintCase insertData : insertList) {

                Bacpirec bacpirecData = new Bacpirec();

                BeanUtility.copyProperties(bacpirecData, insertData);

                bacpirecData.setCrtUser(userData.getEmpNo()); // 異動者代號
                bacpirecData.setCrtTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間
                bacpirecData.setAdjYmB(adjYmB);

                bacpirecDao.insertData(bacpirecData);
            }

            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doInsertListLog(insertList);
            }

            // update BACPIDTL
            if (detailData != null) {
                Bacpidtl bacpidtlData = new Bacpidtl();

                BeanUtility.copyProperties(bacpidtlData, detailData);

                bacpidtlData.setUpdUser(userData.getEmpNo()); // 異動者代號
                bacpidtlData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

                List<Bacpidtl> bacpidtlDataBefore = bacpidtlDao.selectListForForCpiRec(bacpidtlData.getIssuYear());

                bacpidtlDao.updateAdjMk(bacpidtlData);

                List<Bacpidtl> bacpidtlDataAfter = bacpidtlDao.selectListForForCpiRec(bacpidtlData.getIssuYear());

                // UpdateAdjYear MMAPLOG
                if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                    // 紀錄 Log
                    FrameworkLogUtil.doUpdateListLog(bacpidtlDataBefore, bacpidtlDataAfter);
                }
            }

        }

        log.debug("執行 MaintService.saveCpiRecMaintData() 完成 ...");
    }

    /**
     * 取出 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 老年年金加計金額調整維護作業<br>
     * 
     * @param payYmB 給付年月起月
     * @param payCode 給付種類
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public List<BasicAmtMaintCase> getBasicAmtMaintQueryCaseBy(String payYmB, String payCode) {
        log.debug("執行 MaintService.getBasicAmtMaintQueryCaseBy() 開始 ...");

        List<Babasicamt> BabasicamtList = babasicamtDao.selectData(payYmB, payCode);
        List<BasicAmtMaintCase> caseList = new ArrayList<BasicAmtMaintCase>();
        for (int i = 0; i < BabasicamtList.size(); i++) {
            Babasicamt obj = BabasicamtList.get(i);
            obj.setCpiYear1(DateUtility.changeWestYearType(obj.getCpiYear1()));
            obj.setCpiYear2(DateUtility.changeWestYearType(obj.getCpiYear2()));
            obj.setPayYmB(DateUtility.changeWestYearMonthType(obj.getPayYmB()));
            obj.setPayYmE(DateUtility.changeWestYearMonthType(obj.getPayYmE()));
            obj.setSysDate(DateUtility.changeWestYearMonthType(obj.getSysDate()));
            obj.setDate(DateUtility.changeDateType(obj.getDate().substring(0, 8)));
            BasicAmtMaintCase caseObj = new BasicAmtMaintCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseList.add(caseObj);
        }
        log.debug("執行 MaintService.getBasicAmtMaintQueryCaseBy() 完成 ...");

        return caseList;
    }

    /**
     * 取出 老年年金加計金額調整紀錄檔 最後一筆資料 取得payYmE判斷是否有值 (<code>BABASICAMT</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 老年年金加計金額調整維護作業<br>
     * 
     * @param
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public Babasicamt getCheckPayYmEValue(String payCode) {
        log.debug("執行 MaintService.getCheckPayYmEValue() 開始 ...");
        Babasicamt babasicamt = null;
        babasicamt = babasicamtDao.checkPayYmEValue(payCode);
        log.debug("執行 MaintService.getCheckPayYmEValue() 完成 ...");

        return babasicamt;
    }

    /**
     * 依傳入的 <code>Babasicamt</code> 資料更新至 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>)<br>
     * for : 維護作業 - 老年年金加計金額調整作業<br>
     * 
     * @param caseData <code>Babasicamt</code>
     */
    public void saveBasicAmtMaintData(Babasicamt detailData, UserBean userData) {
        log.debug("執行 MaintService.saveCpiRecMaintData() 開始 ...");

        if (detailData != null && userData != null) {
            Babasicamt babasicamtData = new Babasicamt();

            BeanUtility.copyProperties(babasicamtData, detailData);

            babasicamtData.setCrtUser(userData.getEmpNo()); // 新增者代號
            babasicamtData.setCrtTime(DateUtility.getNowWestDateTime(true)); // 新增日期時間

            babasicamtDao.insertData(babasicamtData);

            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doInsertLog(babasicamtData);
            }
        }

        log.debug("執行 MaintService.saveBasicAmtMaintData() 完成 ...");
    }

    /**
     * 刪除 老年年金加計金額調整紀錄檔 資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     */
    public void deleteBasicAmtData(UserBean userData, BasicAmtMaintCase babasicamtData) {
        // 刪除 老年年金加計金額調整紀錄檔
        // [
        log.debug("Start to Delete BABASICAMT ...");
        // 西元年 給付類別
        String payYmB = babasicamtData.getPayYmB();
        String payCode = babasicamtData.getPayCode();
        String cpiYear1 = babasicamtData.getCpiYear1();
        String cpiYear2 = babasicamtData.getCpiYear2();
        // 取得改前值物件
        Babasicamt beforBabasicamt = babasicamtDao.selectSingleForUpdateData(payYmB, payCode);
        // 刪除 給付主檔
        babasicamtDao.deleteData(payCode, cpiYear1, cpiYear2);

        // delect MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteLog(beforBabasicamt);
        }
        log.debug("Delete BABASICAMT Finished ...");
        // ]
    }

    /**
     * 依傳入的 給付年月起月 自 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 老年年金加計金額調整紀錄修改作業<br>
     * 
     * @param PAYYMB 給付年月起月
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public Babasicamt getBasicAmtMaintForUpdateCaseBy(String payYmB, String payCode) {
        log.debug("執行 MaintService.getBasicAmtMaintForUpdateCaseBy() 開始 ...");

        Babasicamt babasicamtData = babasicamtDao.selectSingleForUpdateData(payYmB, payCode);

        log.debug("執行 MaintService.getBasicAmtMaintForUpdateCaseBy() 完成 ...");

        return babasicamtData;
    }

    /**
     * 依傳入的 給付年月起月 自 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 老年年金加計金額調整紀錄修改作業 確認資料是否重複<br>
     * 
     * @param CPIYEAR1 指數年度一
     * @param CPIYEAR2 指數年度二
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public Babasicamt getBasicAmtMaintForCheckSaveCaseBy(String payCode, String cpiYear1, String cpiYear2) {
        log.debug("執行 MaintService.getBasicAmtMaintForCheckSaveCaseBy() 開始 ...");

        Babasicamt babasicamtData = babasicamtDao.selectSingleForCheckSaveData(payCode, cpiYear1, cpiYear2);

        log.debug("執行 MaintService.getBasicAmtMaintForCheckSaveCaseBy() 完成 ...");

        return babasicamtData;
    }

    /**
     * 依傳入的 <code>babasicamt</code> 資料更新至 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>)<br>
     * for : 維護作業 - 老年年金加計金額調整紀錄修改作業<br>
     * 
     * @param caseData <code>babasicamt</code>
     */
    public void updateBasicAmtMaintData(Babasicamt detailData, UserBean userData) {
        log.debug("執行 MaintService.updateBasicAmtMaintData() 開始 ...");

        if (detailData != null && userData != null) {
            Babasicamt babasicamtData = new Babasicamt();

            BeanUtility.copyProperties(babasicamtData, detailData);

            babasicamtData.setUpdUser(userData.getEmpNo()); // 異動者代號
            babasicamtData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            Babasicamt babasicamtDataBefore = babasicamtDao.selectSingleForUpdateData(babasicamtData.getPayYmB(), babasicamtData.getPayCode());

            babasicamtDao.updateData(babasicamtData);

            Babasicamt babasicamtDataAfter = babasicamtDao.selectSingleForUpdateData(babasicamtData.getPayYmB(), babasicamtData.getPayCode());

            // Update MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(babasicamtDataBefore, babasicamtDataAfter);
            }
        }

        log.debug("執行 MaintService.updateCpiRecMaintData() 完成 ...");
    }

    /**
     * 依傳入條件取得 平均薪資檔(<code>BAPAAVGMON</code>) 資料清單 For 勞保年金平均薪資月數參數維護修改作業
     * 
     * @param effYear 施行年度
     * @return 內含 <code>Bapaavgmon</code> 物件的 List
     */
    public List<AvgAmtMonMaintCase> selectBapaavgmonDataListForAvgAmtMonMaintBy(String effYear) {
        log.debug("執行 MaintService.selectBapaavgmonDataListForAvgAmtMonMaintBy() 開始 ...");

        List<AvgAmtMonMaintCase> applyList = new ArrayList<AvgAmtMonMaintCase>();
        List<Bapaavgmon> list = bapaavgmonDao.selectBapaavgmonDataListForAvgAmtMonMaintBy(effYear);

        for (int i = 0; i < list.size(); i++) {
            Bapaavgmon bapaavgmon = (Bapaavgmon) list.get(i);

            AvgAmtMonMaintCase applyCase = new AvgAmtMonMaintCase();
            BeanUtility.copyProperties(applyCase, bapaavgmon);
            applyList.add(applyCase);
        }

        log.debug("執行 MaintService.selectBapaavgmonDataListForAvgAmtMonMaintBy() 完成 ...");

        return applyList;
    }

    /**
     * 刪除 平均薪資檔 資料 for 勞保年金平均薪資月數參數維護修改作業
     * 
     * @param effYear 施行年度
     */
    public void deleteBapaavgmonData(UserBean userData, AvgAmtMonMaintCase bapaavgmonData) {
        // 刪除 給付主檔
        // [
        log.debug("Start to Delete BAPAAVGMON ...");

        String effYear = bapaavgmonData.getEffYear();

        // 取得改前值物件
        Bapaavgmon beforBapaavgmon = new Bapaavgmon();
        BeanUtility.copyProperties(beforBapaavgmon, bapaavgmonData);
        // 刪除 給付主檔
        bapaavgmonDao.deleteData(effYear);

        // Delete MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteLog(beforBapaavgmon);
        }
        log.debug("Delete BAPAAVGMON Finished ...");
        // ]
    }

    /**
     * 依傳入的 <code>Bapaavgmon</code> 資料更新至 平均薪資檔 (<code>BAPAAVGMON</code>)<br>
     * for : 維護作業 - 勞保年金平均薪資月數參數維護修改作業<br>
     * 
     * @param caseData <code>Bapaavgmon</code>
     */
    public void insertBapaavgmonData(AvgAmtMonMaintCase detailData, UserBean userData) {
        log.debug("執行 MaintService.insertBapaavgmonData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapaavgmon bapaavgmonData = new Bapaavgmon();

            BeanUtility.copyProperties(bapaavgmonData, detailData);

            bapaavgmonData.setEffYear(DateUtility.changeChineseYearType(bapaavgmonData.getEffYear()));
            bapaavgmonData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapaavgmonData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間
            bapaavgmonData.setCrtUser(userData.getEmpNo()); // 新增者代號
            bapaavgmonData.setCrtTime(DateUtility.getNowWestDateTime(true)); // 新增日期時間

            bapaavgmonDao.insertData(bapaavgmonData);
            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doInsertLog(bapaavgmonData);
            }
        }

        log.debug("執行 MaintService.insertBapaavgmonData() 完成 ...");
    }

    /**
     * 依傳入的 <code>Bapaavgmon</code> 資料更新至 平均薪資檔 (<code>BAPAAVGMON</code>)<br>
     * for : 維護作業 - 勞保年金平均薪資月數參數維護修改作業<br>
     * 
     * @param caseData <code>Bapaavgmon</code>
     */
    public void updateBapaavgmonData(AvgAmtMonMaintCase detailData, AvgAmtMonMaintCase beforeData, UserBean userData) {
        log.debug("執行 MaintService.updateBapaavgmonData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapaavgmon bapaavgmonData = new Bapaavgmon();
            Bapaavgmon beforeBapaavgmonData = new Bapaavgmon();

            BeanUtility.copyProperties(bapaavgmonData, detailData);
            BeanUtility.copyProperties(beforeBapaavgmonData, beforeData);

            bapaavgmonData.setEffYear(DateUtility.changeChineseYearType(bapaavgmonData.getEffYear()));
            bapaavgmonData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapaavgmonData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            bapaavgmonDao.updateData(bapaavgmonData);

            // Update MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBapaavgmonData, bapaavgmonData);
            }
        }

        log.debug("執行 MaintService.updateBapaavgmonData() 完成 ...");
    }

    /**
     * 依傳入條件取得 勞保年金所得替代率參數檔(<code>BAPAIRR</code>) 資料清單 For 所得替代率參數維護作業
     * 
     * @param effYm 施行年月
     * @param payCode 給付別
     * @return 內含 <code>Bapairr</code> 物件的 List
     */
    public List<ReplacementRatioMaintCase> selectBapairrDataForReplacementRatioMaintBy(String effYm, String payCode) {
        log.debug("執行 MaintService.selectBapairrDataForReplacementRatioMaintBy() 開始 ...");

        List<ReplacementRatioMaintCase> applyList = new ArrayList<ReplacementRatioMaintCase>();
        List<Bapairr> list = bapairrDao.selectBapairrDataForReplacementRatioMaintBy(effYm, payCode);

        for (int i = 0; i < list.size(); i++) {
            Bapairr bapairr = (Bapairr) list.get(i);

            ReplacementRatioMaintCase applyCase = new ReplacementRatioMaintCase();
            BeanUtility.copyProperties(applyCase, bapairr);
            applyList.add(applyCase);
        }

        log.debug("執行 MaintService.selectBapairrDataForReplacementRatioMaintBy() 完成 ...");

        return applyList;
    }

    /**
     * 刪除 勞保年金所得替代率參數檔 資料 for 所得替代率參數維護作業
     * 
     * @param effYm 施行年月
     * @param payCode 給付別
     */
    public void deleteBapairrData(UserBean userData, ReplacementRatioMaintCase bapairrData) {
        // 刪除 給付主檔
        // [
        log.debug("Start to Delete BAPAIRR ...");

        String effYm = bapairrData.getEffYm();
        String payCode = bapairrData.getPayCode();

        // 取得改前值物件
        Bapairr beforBapairr = new Bapairr();
        BeanUtility.copyProperties(beforBapairr, bapairrData);
        // 刪除 給付主檔
        bapairrDao.deleteData(effYm, payCode);

        // Delete MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteLog(beforBapairr);
        }
        log.debug("Delete BAPAIRR Finished ...");
        // ]
    }

    /**
     * 依傳入的 <code>Bapairr</code> 資料更新至 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br>
     * for : 維護作業 - 勞保年金所得替代率參數維護修改作業<br>
     * 
     * @param caseData <code>Bapairr</code>
     */
    public void insertBapairrData(ReplacementRatioMaintCase detailData, UserBean userData) {
        log.debug("執行 MaintService.insertBapairrData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapairr bapairrData = new Bapairr();

            BeanUtility.copyProperties(bapairrData, detailData);

            bapairrData.setEffYm(DateUtility.changeChineseYearMonthType(bapairrData.getEffYm()));
            bapairrData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapairrData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間
            bapairrData.setCrtUser(userData.getEmpNo()); // 新增者代號
            bapairrData.setCrtTime(DateUtility.getNowWestDateTime(true)); // 新增日期時間

            bapairrDao.insertData(bapairrData);
            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doInsertLog(bapairrData);
            }
        }

        log.debug("執行 MaintService.insertBapairrData() 完成 ...");
    }

    /**
     * 依傳入的 <code>Bapairr</code> 資料更新至 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br>
     * for : 維護作業 - 勞保年金所得替代率參數維護修改作業<br>
     * 
     * @param caseData <code>Bapairr</code>
     */
    public void updateBapairrData(ReplacementRatioMaintCase detailData, ReplacementRatioMaintCase beforeData, UserBean userData) {
        log.debug("執行 MaintService.updateBapairrData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapairr bapairrData = new Bapairr();
            Bapairr beforeBapairrData = new Bapairr();

            BeanUtility.copyProperties(bapairrData, detailData);
            BeanUtility.copyProperties(beforeBapairrData, beforeData);

            bapairrData.setEffYm(DateUtility.changeChineseYearMonthType(bapairrData.getEffYm()));
            bapairrData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapairrData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            bapairrDao.updateData(bapairrData);

            // Update MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBapairrData, bapairrData);
            }
        }

        log.debug("執行 MaintService.updateBapairrData() 完成 ...");
    }

    /**
     * 依傳入條件取得 勞保年金所得替代率參數檔(<code>BAPAIRR</code>) 資料清單 For 失能年金所得替代率參數維護作業
     * 
     * @param effYm 施行年月
     * @param payCode 給付別
     * @return 內含 <code>Bapairr</code> 物件的 List
     */
    public List<DisabledReplacementRatioMaintCase> selectBapairrDataForDisabledReplacementRatioMaintBy(String effYm, String payCode) {
        log.debug("執行 MaintService.selectBapairrDataForDisabledReplacementRatioMaintBy() 開始 ...");

        List<DisabledReplacementRatioMaintCase> applyList = new ArrayList<DisabledReplacementRatioMaintCase>();
        List<Bapairr> list = bapairrDao.selectBapairrDataForReplacementRatioMaintBy(effYm, payCode);

        for (int i = 0; i < list.size(); i++) {
            Bapairr bapairr = (Bapairr) list.get(i);

            DisabledReplacementRatioMaintCase applyCase = new DisabledReplacementRatioMaintCase();
            BeanUtility.copyProperties(applyCase, bapairr);
            applyList.add(applyCase);
        }

        log.debug("執行 MaintService.selectBapairrDataForDisabledReplacementRatioMaintBy() 完成 ...");

        return applyList;
    }

    /**
     * 刪除 勞保年金所得替代率參數檔 資料 for 失能年金所得替代率參數維護作業
     * 
     * @param effYm 施行年月
     * @param payCode 給付別
     */
    public void deleteDisabledBapairrData(UserBean userData, DisabledReplacementRatioMaintCase bapairrData) {
        // 刪除 給付主檔
        // [
        log.debug("Start to Delete BAPAIRR ...");

        String effYm = bapairrData.getEffYm();
        String payCode = bapairrData.getPayCode();

        // 取得改前值物件
        Bapairr beforBapairr = new Bapairr();
        BeanUtility.copyProperties(beforBapairr, bapairrData);
        // 刪除 給付主檔
        bapairrDao.deleteData(effYm, payCode);

        // Delete MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteLog(beforBapairr);
        }
        log.debug("Delete BAPAIRR Finished ...");
        // ]
    }

    /**
     * 依傳入的 <code>Bapairr</code> 資料更新至 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br>
     * for : 維護作業 - 失能年金所得替代率參數維護修改作業<br>
     * 
     * @param caseData <code>Bapairr</code>
     */
    public void insertDisabledBapairrData(DisabledReplacementRatioMaintCase detailData, UserBean userData) {
        log.debug("執行 MaintService.insertDisabledBapairrData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapairr bapairrData = new Bapairr();

            BeanUtility.copyProperties(bapairrData, detailData);

            bapairrData.setEffYm(DateUtility.changeChineseYearMonthType(bapairrData.getEffYm()));
            bapairrData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapairrData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間
            bapairrData.setCrtUser(userData.getEmpNo()); // 新增者代號
            bapairrData.setCrtTime(DateUtility.getNowWestDateTime(true)); // 新增日期時間

            bapairrDao.insertDataForDisabledSurvivor(bapairrData);
            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doInsertLog(bapairrData);
            }
        }

        log.debug("執行 MaintService.insertDisabledBapairrData() 完成 ...");
    }

    /**
     * 依傳入的 <code>Bapairr</code> 資料更新至 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br>
     * for : 維護作業 - 失能年金所得替代率參數維護修改作業<br>
     * 
     * @param caseData <code>Bapairr</code>
     */
    public void updateDisabledBapairrData(DisabledReplacementRatioMaintCase detailData, DisabledReplacementRatioMaintCase beforeData, UserBean userData) {
        log.debug("執行 MaintService.updateDisabledBapairrData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapairr bapairrData = new Bapairr();
            Bapairr beforeBapairrData = new Bapairr();

            BeanUtility.copyProperties(bapairrData, detailData);
            BeanUtility.copyProperties(beforeBapairrData, beforeData);

            bapairrData.setEffYm(DateUtility.changeChineseYearMonthType(bapairrData.getEffYm()));
            bapairrData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapairrData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            bapairrDao.updateDataForDisabledSurvivor(bapairrData);

            // Update MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBapairrData, bapairrData);
            }
        }

        log.debug("執行 MaintService.updateDisabledBapairrData() 完成 ...");
    }

    /**
     * 依傳入條件取得 勞保年金所得替代率參數檔(<code>BAPAIRR</code>) 資料清單 For 遺屬年金所得替代率參數維護作業
     * 
     * @param effYm 施行年月
     * @param payCode 給付別
     * @return 內含 <code>Bapairr</code> 物件的 List
     */
    public List<SurvivorReplacementRatioMaintCase> selectBapairrDataForSurvivorReplacementRatioMaintBy(String effYm, String payCode) {
        log.debug("執行 MaintService.selectBapairrDataForSurvivorReplacementRatioMaintBy() 開始 ...");

        List<SurvivorReplacementRatioMaintCase> applyList = new ArrayList<SurvivorReplacementRatioMaintCase>();
        List<Bapairr> list = bapairrDao.selectBapairrDataForReplacementRatioMaintBy(effYm, payCode);

        for (int i = 0; i < list.size(); i++) {
            Bapairr bapairr = (Bapairr) list.get(i);

            SurvivorReplacementRatioMaintCase applyCase = new SurvivorReplacementRatioMaintCase();
            BeanUtility.copyProperties(applyCase, bapairr);
            applyList.add(applyCase);
        }

        log.debug("執行 MaintService.selectBapairrDataForSurvivorReplacementRatioMaintBy() 完成 ...");

        return applyList;
    }

    /**
     * 刪除 勞保年金所得替代率參數檔 資料 for 遺屬年金所得替代率參數維護作業
     * 
     * @param effYm 施行年月
     * @param payCode 給付別
     */
    public void deleteSurvivorBapairrData(UserBean userData, SurvivorReplacementRatioMaintCase bapairrData) {
        // 刪除 給付主檔
        // [
        log.debug("Start to Delete BAPAIRR ...");

        String effYm = bapairrData.getEffYm();
        String payCode = bapairrData.getPayCode();

        // 取得改前值物件
        Bapairr beforBapairr = new Bapairr();
        BeanUtility.copyProperties(beforBapairr, bapairrData);
        // 刪除 給付主檔
        bapairrDao.deleteData(effYm, payCode);

        // Delete MMAPLOG
        if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
            // 紀錄 Log
            FrameworkLogUtil.doDeleteLog(beforBapairr);
        }
        log.debug("Delete BAPAIRR Finished ...");
        // ]
    }

    /**
     * 依傳入的 <code>Bapairr</code> 資料更新至 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br>
     * for : 維護作業 - 遺屬年金所得替代率參數維護修改作業<br>
     * 
     * @param caseData <code>Bapairr</code>
     */
    public void insertSurvivorBapairrData(SurvivorReplacementRatioMaintCase detailData, UserBean userData) {
        log.debug("執行 MaintService.insertSurvivorBapairrData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapairr bapairrData = new Bapairr();

            BeanUtility.copyProperties(bapairrData, detailData);

            bapairrData.setEffYm(DateUtility.changeChineseYearMonthType(bapairrData.getEffYm()));
            bapairrData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapairrData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間
            bapairrData.setCrtUser(userData.getEmpNo()); // 新增者代號
            bapairrData.setCrtTime(DateUtility.getNowWestDateTime(true)); // 新增日期時間

            bapairrDao.insertDataForDisabledSurvivor(bapairrData);
            // Insert MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doInsertLog(bapairrData);
            }
        }

        log.debug("執行 MaintService.insertSurvivorBapairrData() 完成 ...");
    }

    /**
     * 依傳入的 <code>Bapairr</code> 資料更新至 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br>
     * for : 維護作業 - 遺屬年金所得替代率參數維護修改作業<br>
     * 
     * @param caseData <code>Bapairr</code>
     */
    public void updateSurvivorBapairrData(SurvivorReplacementRatioMaintCase detailData, SurvivorReplacementRatioMaintCase beforeData, UserBean userData) {
        log.debug("執行 MaintService.updateSurvivorBapairrData() 開始 ...");

        if (detailData != null && userData != null) {
            Bapairr bapairrData = new Bapairr();
            Bapairr beforeBapairrData = new Bapairr();

            BeanUtility.copyProperties(bapairrData, detailData);
            BeanUtility.copyProperties(beforeBapairrData, beforeData);

            bapairrData.setEffYm(DateUtility.changeChineseYearMonthType(bapairrData.getEffYm()));
            bapairrData.setUpdUser(userData.getEmpNo()); // 異動者代號
            bapairrData.setUpdTime(DateUtility.getNowWestDateTime(true)); // 異動日期時間

            bapairrDao.updateDataForDisabledSurvivor(bapairrData);

            // Update MMAPLOG
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
                // 紀錄 Log
                FrameworkLogUtil.doUpdateLog(beforeBapairrData, bapairrData);
            }
        }

        log.debug("執行 MaintService.updateSurvivorBapairrData() 完成 ...");
    }

    /**
     * 核定函代碼參數檔 (<code>BALETTERCODE</code>) 取得 <code>List</code><br>
     * for : 維護作業 - 核定通知書維護作業<br>
     * 
     * @return <code>List</code>; 若無資料回傳 <code>null</code>
     */
    public List<BaletterCodeCase> selectBalettercodeDataBy() {
        log.debug("執行 MaintService.selectBalettercodeDataBy() 開始 ...");

        List<Balettercode> balettercodeList = balettercodeDao.selectBalettercodeDataBy();
        List<BaletterCodeCase> caseList = new ArrayList<BaletterCodeCase>();

        // 每四筆放入一個case
        int index = 0;

        // 計算需做幾個case
        int listSize = balettercodeList.size();
        int times = 0;
        if (listSize % 4 != 0) {
            times = listSize / 4 + 1;
        }
        else {
            times = listSize / 4;
        }

        for (int x = 0; x < times; x++) {
            BaletterCodeCase caseObj = new BaletterCodeCase();

            for (int i = 0; i < 4; i++) {

                if (i == 0) {
                    if (index < listSize) {
                        Balettercode obj = balettercodeList.get(index);
                        caseObj.setCode1(obj.getCode());
                        caseObj.setCodeName1(obj.getCodeName());
                        index++;
                    }
                }
                if (i == 1) {
                    if (index < listSize) {
                        Balettercode obj = balettercodeList.get(index);
                        caseObj.setCode2(obj.getCode());
                        caseObj.setCodeName2(obj.getCodeName());
                        index++;
                    }
                }
                if (i == 2) {
                    if (index < listSize) {
                        Balettercode obj = balettercodeList.get(index);
                        caseObj.setCode3(obj.getCode());
                        caseObj.setCodeName3(obj.getCodeName());
                        index++;
                    }
                }
                if (i == 3) {
                    if (index < listSize) {
                        Balettercode obj = balettercodeList.get(index);
                        caseObj.setCode4(obj.getCode());
                        caseObj.setCodeName4(obj.getCodeName());
                        index++;
                    }
                }

            }
            caseList.add(caseObj);

        }

        log.debug("執行 MaintService.selectBalettercodeDataBy() 完成 ...");

        return caseList;
    }

    /**
     * 取得 物價指數調整明細檔 (<code>BACPIDTL</code>) 取得 <code>seqNo</code><br>
     * 
     * @return <code>seqNo</code>; 若無資料回傳 <code>null</code>
     */
    public BigDecimal selectSeqNoForCpiDtl(Bacpidtl data) {
        log.debug("執行 MaintService.selectSeqNoForCpiDtl() 開始 ...");

        BigDecimal newSeqNo = bacpidtlDao.selectSeqNoForCpiDtl(data);

        log.debug("執行 MaintService.selectSeqNoForCpiDtl() 完成 ...");

        return newSeqNo;
    }

    /**
     * 取得 物價指數調整明細檔 (<code>BACPIDTL</code>) 取得 <code>seqNo</code><br>
     * 
     * @return <code>seqNo</code>; 若無資料回傳 <code>null</code>
     */
    public List<CpiDtlMaintCase> selectAdjYearDataBy(Bacpidtl data) {
        log.debug("執行 MaintService.selectAdjYearDataResult() 開始 ...");

        List<Bacpidtl> bacpidtlList = bacpidtlDao.selectAdjYearDataBy(data);
        List<CpiDtlMaintCase> caseList = new ArrayList<CpiDtlMaintCase>();
        for (int i = 0; i < bacpidtlList.size(); i++) {
            Bacpidtl obj = bacpidtlList.get(i);

            obj.setAdjYearS(DateUtility.changeWestYearType(obj.getAdjYearS()));
            obj.setAdjYearE(DateUtility.changeWestYearType(obj.getAdjYearE()));

            CpiDtlMaintCase caseObj = new CpiDtlMaintCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseList.add(caseObj);
        }
        log.debug("執行 MaintService.selectAdjYearDataBy() 完成 ...");

        return caseList;
    }

    /**
     * 取得 物價指數調整明細檔 (<code>BACPIDTL</code>)
     * 
     * @return <code>List<CpiDtlMaintCase></code>; 若無資料回傳 <code>null</code>
     */
    public List<CpiDtlMaintCase> selectUpdDelDataBy(Bacpidtl data) {
        log.debug("執行 MaintService.selectUpdDelDataBy() 開始 ...");

        List<Bacpidtl> bacpidtlList = bacpidtlDao.selectUpdDelDataBy(data);
        List<CpiDtlMaintCase> caseList = new ArrayList<CpiDtlMaintCase>();
        for (int i = 0; i < bacpidtlList.size(); i++) {

            Bacpidtl obj = bacpidtlList.get(i);

            CpiDtlMaintCase caseObj = new CpiDtlMaintCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseList.add(caseObj);
        }
        log.debug("執行 MaintService.selectUpdDelDataBy() 完成 ...");

        return caseList;
    }

    /**
     * 取得 物價指數調整明細檔 (<code>BACPIDTL</code>)
     * 
     * @return <code>List<CpiDtlMaintCase></code>; 若無資料回傳 <code>null</code>
     */
    public List<CpiDtlMaintCase> selectDetailUserDataBy(Bacpidtl data) {
        log.debug("執行 MaintService.selectDetailUserDataBy() 開始 ...");

        List<Bacpidtl> bacpidtlList = bacpidtlDao.selectDetailUserDataBy(data);
        List<CpiDtlMaintCase> caseList = new ArrayList<CpiDtlMaintCase>();
        for (int i = 0; i < bacpidtlList.size(); i++) {

            Bacpidtl obj = bacpidtlList.get(i);

            CpiDtlMaintCase caseObj = new CpiDtlMaintCase();
            BeanUtility.copyProperties(caseObj, obj);
            caseObj.setAdjYearS(DateUtility.changeWestYearType(caseObj.getAdjYearS()));
            caseObj.setAdjYearE(DateUtility.changeWestYearType(caseObj.getAdjYearE()));
            caseObj.setDate(DateUtility.changeDateType(caseObj.getDate()));
            caseList.add(caseObj);
        }
        log.debug("執行 MaintService.selectDetailUserDataBy() 完成 ...");

        return caseList;
    }
    
    /**
     * 依傳入條件取得 給付主檔 受款人資料清單 for 結案狀態變更作業
     * 
     * @param apno 受理編號
     * @return
     */
    public ResetPaymentParameterCase selectDataForResetPaymentParameter(String apNo) {
        // return baappbaseDao.selectPayeeListForCloseStatusAlteration(apNo);

        Baappbase list = baappbaseDao.selectDataForResetPaymentParameter(apNo);
        
        if(list != null) {
        	ResetPaymentParameterCase caseObj = new ResetPaymentParameterCase();
            BeanUtility.copyProperties(caseObj, list);
            return caseObj;
        }

        return null;
    }

    /**
     * 依傳入的 <code>Baappexpand</code> 資料更新至 給付延伸主檔 (<code>BAAPPEXPAND</code>)<br>
     * for : 維護作業 - 重設案件給付參數資料<br>
     * 
     * @param apNo
     * @param userData
     */
    public void updateCpiBaseYMForResetPaymentParameter(String apNo, UserBean userData) {
        log.debug("執行 MaintService.updateCpiBaseYMForResetPaymentParameter() 開始 ...");

        String sUpdUser = userData.getEmpNo();
        String sUpdTime = DateUtility.getNowWestDateTime(true);

        baappexpandDao.updateCpiBaseYMForResetPaymentParameter(apNo, sUpdUser, sUpdTime);

        log.debug("執行 MaintService.updateCpiBaseYMForResetPaymentParameter() 完成 ...");
    }

    /**
     * 依傳入的 <code>Baappexpand</code> 資料更新至 給付延伸主檔 (<code>BAAPPEXPAND</code>)<br>
     * for : 維護作業 - 重設案件給付參數資料<br>
     * 
     * @param apNo
     * @param userData
     */
    public void updateInsAvgAmtAppYearForResetPaymentParameter(String apNo, UserBean userData) {
        log.debug("執行 MaintService.updateInsAvgAmtAppYearForResetPaymentParameter() 開始 ...");

        String sUpdUser = userData.getEmpNo();
        String sUpdTime = DateUtility.getNowWestDateTime(true);

        baappexpandDao.updateInsAvgAmtAppYearForResetPaymentParameter(apNo, sUpdUser, sUpdTime);

        log.debug("執行 MaintService.updateInsAvgAmtAppYearForResetPaymentParameter() 完成 ...");
    }

    /**
     * 依傳入的 <code>Baappexpand</code> 資料更新至 給付延伸主檔 (<code>BAAPPEXPAND</code>)<br>
     * for : 維護作業 - 重設案件給付參數資料<br>
     * 
     * @param apNo
     * @param userData
     */
    public void regetCipbForResetPaymentParameter(String apNo, UserBean userData) {
        log.debug("執行 MaintService.regetCipbForResetPaymentParameter() 開始 ...");

        List<Baappbase> detailDataList = baappbaseDao.selectDataToReGetCipbForResetPaymentParameter(apNo);

        // 呼叫 Store Procedure sp_BA_Get_CIPB (眷屬身份證號,事故日期【NULL】)
        for (int i = 0; i < detailDataList.size(); i++) {
            updateService.doGetCipb(detailDataList.get(i).getApNo(), detailDataList.get(i).getSeqNo(), detailDataList.get(i).getAppDate(), detailDataList.get(i).getBenIdnNo(), detailDataList.get(i).getEvtJobDate());
        }

        log.debug("執行 MaintService.regetCipbForResetPaymentParameter() 完成 ...");
    }
    
    public String removeTag(String str){
		Pattern p_script, p_style, p_html, p_special;
		Matcher m_script, m_style, m_html, m_special;

		//script
		String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
		p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		m_script = p_script.matcher(str);
		str = m_script.replaceAll("");
		
		//style
		String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
		p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		m_style = p_style.matcher(str);
		str = m_style.replaceAll("");
		
		//HTML
		String regEx_html = "<[^>]+>";
		p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		m_html = p_html.matcher(str);
		str = m_html.replaceAll("");
		
		//special case
		String regEx_special = "\\&[a-zA-Z]{1,10};";
		p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
		m_special = p_special.matcher(str);
		str = m_special.replaceAll("");
		
		return str;
	}

    public void setBapainspectDao(BapainspectDao bapainspectDao) {
        this.bapainspectDao = bapainspectDao;
    }

    public void setBapadchkDao(BapadchkDao bapadchkDao) {
        this.bapadchkDao = bapadchkDao;
    }

    public void setBapadcslvlDao(BapadcslvlDao bapadcslvlDao) {
        this.bapadcslvlDao = bapadcslvlDao;
    }

    public void setBanotifyDao(BanotifyDao banotifyDao) {
        this.banotifyDao = banotifyDao;
    }

    public void setBapacutDao(BapacutDao bapacutDao) {
        this.bapacutDao = bapacutDao;
    }

    public void setBacpidtlDao(BacpidtlDao bacpidtlDao) {
        this.bacpidtlDao = bacpidtlDao;
    }

    public void setBacpirecDao(BacpirecDao bacpirecDao) {
        this.bacpirecDao = bacpirecDao;
    }

    public void setBabasicamtDao(BabasicamtDao babasicamtDao) {
        this.babasicamtDao = babasicamtDao;
    }

    public void setBapaavgmonDao(BapaavgmonDao bapaavgmonDao) {
        this.bapaavgmonDao = bapaavgmonDao;
    }

    public void setBapairrDao(BapairrDao bapairrDao) {
        this.bapairrDao = bapairrDao;
    }

    public void setBalettercodeDao(BalettercodeDao balettercodeDao) {
        this.balettercodeDao = balettercodeDao;
    }

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setBaappexpandDao(BaappexpandDao baappexpandDao) {
        this.baappexpandDao = baappexpandDao;
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }
}
