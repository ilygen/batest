package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BacountryDao;
import tw.gov.bli.ba.dao.BafamilytempDao;
import tw.gov.bli.ba.dao.BanotifyDao;
import tw.gov.bli.ba.dao.BapandomkDao;
import tw.gov.bli.ba.dao.BaparamDao;
import tw.gov.bli.ba.dao.BaprocedureDao;
import tw.gov.bli.ba.dao.MaadmrecDao;
import tw.gov.bli.ba.dao.NpcodeDao;
import tw.gov.bli.ba.dao.PbbmsaDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bacountry;
import tw.gov.bli.ba.domain.Bafamilytemp;
import tw.gov.bli.ba.domain.Banotify;
import tw.gov.bli.ba.domain.Bapandomk;
import tw.gov.bli.ba.domain.Baparam;
import tw.gov.bli.ba.domain.Baprocedure;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.domain.Npcode;
import tw.gov.bli.ba.domain.Pbbmsa;

/**
 * Service for 下拉選單項目
 * 
 * @author Goston
 */
public class SelectOptionService {
    private static Log log = LogFactory.getLog(SelectOptionService.class);

    private BaparamDao baparamDao;
    private BacountryDao bacountryDao;
    private BapandomkDao bapandomkDao;
    private BaappbaseDao baappbaseDao;
    private BanotifyDao banotifyDao;
    private PbbmsaDao pbbmsaDao;
    private BafamilytempDao bafamilytempDao;
    private MaadmrecDao maadmrecDao;
    private NpcodeDao npcodeDao;
    private BaprocedureDao baprocedureDao;

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 給付種類 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getPayKindOptionList() {
        return baparamDao.selectListBy(ConstantKey.BAPARAM_PARAMTYP_PRIVATE, ConstantKey.BAPARAM_PARAMGROUP_PAYCODE, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 給付種類 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getReChkStatusOptionList() {
        return baparamDao.selectListBy(null, ConstantKey.BAPARAM_PARAMGROUP_KRECHKSTATUS, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 給付種類 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getReChkResult1OptionList() {
        return baparamDao.selectListBy(null, ConstantKey.BAPARAM_PARAMGROUP_KRECHKRESULT1, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 給付種類 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getReChkResult2OptionList() {
        return baparamDao.selectListBy(null, ConstantKey.BAPARAM_PARAMGROUP_KRECHKRESULT2, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 給付類別 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getPayCategoryOptionList() {
        return baparamDao.selectListBy(ConstantKey.BAPARAM_PARAMTYP_PRIVATE, ConstantKey.BAPARAM_PARAMGROUP_CHKGROUP, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 嚴重程度 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getChkLevelOptionList() {
        return baparamDao.selectListBy(ConstantKey.BAPARAM_PARAMTYP_PRIVATE, ConstantKey.BAPARAM_PARAMGROUP_CHKLEVEL, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 決行層級 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getRechkLvlOptionList() {
        return baparamDao.selectListBy(ConstantKey.BAPARAM_PARAMTYP_SHARE, ConstantKey.BAPARAM_PARAMGROUP_RECHKLVL, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 止付原因 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getStexpndReasonOptionList() {
        return baparamDao.selectOptionListBy(ConstantKey.BAPARAM_PARAMTYP_PRIVATE, ConstantKey.BAPARAM_PARAMGROUP_STEXPNDREASON, null);
    }

    /**
     * 自 國家代碼檔 (<code>BACOUNTRY</code>) 取得 國籍別 下拉式選單的資料
     * 
     * @param countryId 國家代碼
     * @param cname 國家名稱
     * @return
     */
    public List<Bacountry> getCountry() {
        return bacountryDao.selectDataBy(null, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 種類名稱 的資料
     * 
     * @return
     */
    public String getParamName(String paramGroup, String paramCode) {
        return baparamDao.selectParamNameBy(ConstantKey.BAPARAM_PARAMTYP_PRIVATE, paramGroup, paramCode);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 處理函別 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getLetterTypeOptionList() {
        return baparamDao.selectListBy(ConstantKey.BAPARAM_PARAMTYP_SHARE, ConstantKey.BAPARAM_PARAMGROUP_LETTERTYPE, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 處理函別 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getUpdateLetterTypeOptionList() {
        return baparamDao.selectListByLetter(ConstantKey.BAPARAM_PARAMTYP_SHARE, ConstantKey.BAPARAM_PARAMGROUP_LETTERTYPE, null);
    }

    /**
     * 自 處理註記參數檔 (<code>BAPANDOMK</code>) 取得 處理註記 下拉式選單的資料
     * 
     * @return
     */
    public List<Bapandomk> getNdomkOptionList(String letterType, String lpaymk, String kpaymk, String spaymk) {
        return bapandomkDao.selectListBy(letterType, lpaymk, kpaymk, spaymk);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 給付方式 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getPayTypOptionList(String benEvtRel) {
        if ("Z".equals(benEvtRel)) {
            return baparamDao.selectOptionListByUpdate(null, ConstantKey.BAPARAM_PARAMGROUP_PAYTYP, null);
        }
        else {
            return baparamDao.selectOptionListBy(null, ConstantKey.BAPARAM_PARAMGROUP_PAYTYP, null);
        }
    }

    /**
     * 依傳入條件取得 具名領取(<code>BAAPPBASE</code>) 下拉選單
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getBenOptionList(BigDecimal baappbaseId, String apNo) {
        // 先檢查事故者是否活著
        Integer status = baappbaseDao.selectEvtAliveStatusBy(apNo);
        List<Baappbase> resultList = new ArrayList<Baappbase>();
        // 事故者活著, 則benEvtRel=('1', '2', '3', '4', '5', '6' ,'7', 'C', 'E')
        if (status > 0) {
            resultList = baappbaseDao.selectBenListBy(baappbaseId, apNo, "1", new String[] { "('1", "2", "3", "4", "5", "6", "7", "C", "E" }, null);
        }
        // //事故者死亡, 則benEvtRel=('2', '3', '4', '5', '6' ,'7', 'C', 'E')
        else {
            resultList = baappbaseDao.selectBenListBy(baappbaseId, apNo, "1", new String[] { "2", "3", "4", "5", "6", "7", "C", "E" }, null);
        }
        return resultList;
    }

    /**
     * 依傳入條件取得 失能年金受款人 具名領取(<code>BAAPPBASE</code>) 下拉選單
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getDisabledBenOptionList(BigDecimal baappbaseId, String apNo) {
        // 先檢查事故者是否活著
        Integer status = baappbaseDao.selectEvtAliveStatusBy(apNo);
        List<Baappbase> resultList = new ArrayList<Baappbase>();
        // 事故者活著, 則benEvtRel=('1', '2', '3', '4', '5', '6' ,'7', 'C', 'E')
        if (status > 0) {
            resultList = baappbaseDao.selectDisabledBenListBy(baappbaseId, apNo, "1", new String[] { "1", "2", "3", "4", "5", "6", "7", "C", "E" }, null);
        }
        // //事故者死亡, 則benEvtRel=('2', '3', '4', '5', '6' ,'7', 'C', 'E')
        else {
            resultList = baappbaseDao.selectDisabledBenListBy(baappbaseId, apNo, "1", new String[] { "2", "3", "4", "5", "6", "7", "C", "E" }, null);
        }

        return resultList;
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> getRelationOptionList() {
        return baparamDao.selectRelationOptionList();
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 關係 1 ~ 7
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> getDisabledPayeeRelationOptionList() {
        return baparamDao.selectDisabledPayeeRelationOptionList();
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> getPayeeRelationOptionList() {
        return baparamDao.selectPayeeRelationOptionList();
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> getPayeeRelationOptionList2(String typeOne, String typeTwo, String typeThree, String typeFour, String typeFive, Boolean relationStatus) {
        return baparamDao.selectPayeeRelationOptionList2(typeOne, typeTwo, typeThree, typeFour, typeFive, relationStatus);
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> selectBenNameOptionList(String apNo, String baappbaseId) {
        return baparamDao.selectBenNameOptionList(apNo, baappbaseId);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 申請項目 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getApItemOptionList() {
        return baparamDao.selectOptionListBy(null, ConstantKey.BAPARAM_PARAMGROUP_APITEM, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 申請項目 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getApItemOldOptionList() {
        return baparamDao.selectOldOptionListBy(null, ConstantKey.BAPARAM_PARAMGROUP_APITEM, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 結案原因 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getCloseCauseOptionList(String payCode) {
        return baparamDao.selectOptionListBy(null, payCode + ConstantKey.BAPARAM_PARAMGROUP_CLOSECAUSE, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 更正原因 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getChgNoteOptionList() {
        return baparamDao.selectOptionListBy(null, ConstantKey.BAPARAM_PARAMGROUP_CHGNOTE, null);
    }

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 核定通知書格式資料
     * 
     * @param payCode 給付種類
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @param dataSeqNo 資料序號
     * @return
     */
    public List<Banotify> getNotifyFormList(String payCode, String authTyp, String dataTyp, String dataSeqNo) {
        return banotifyDao.selectDataBy(payCode, authTyp, dataTyp, dataSeqNo);
    }

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 核定通知書格式
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<String> selectNotifyFormBy(String apNo) {
        return banotifyDao.selectNotifyFormBy(apNo);
    }

    /**
     * 依傳入條件取得 具名領取(<code>BAAPPBASE</code>) 下拉選單 for 遺屬年金受理作業(主檔)
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getBenOptionListForSurvivor(BigDecimal baappbaseId, String apNo) {
        List<Baappbase> resultList = baappbaseDao.selectBenListBy(baappbaseId, apNo, "1", new String[] { "2", "3", "4", "5", "6", "7" }, null);
        return resultList;
    }

    /**
     * 依傳入條件取得 具名領取(<code>BAFAMILYTEMP</code>) 資料 for 遺屬年金受理作業(遺屬暫存擋)
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 序號
     * @return 內含 <code>Bafamilytemp</code> 物件的 List
     */
    public List<Bafamilytemp> getBenOptionListForSurvivorTemp(BigDecimal bafamilytempId, String seqNo) {
        return bafamilytempDao.selectBenListBy(bafamilytempId, seqNo);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 給付性質 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getReliefTypOptionList() {
        return baparamDao.selectOptionListBy(ConstantKey.BAPARAM_PARAMTYP_SHARE, ConstantKey.BAPARAM_PARAMGROUP_RELIEFTYP, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 救濟種類 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getReliefKindOptionList() {
        return baparamDao.selectOptionListBy(ConstantKey.BAPARAM_PARAMTYP_SHARE, ConstantKey.BAPARAM_PARAMGROUP_RELIEFKIND, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 行政救濟辦理情形 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getReliefStatOptionList(String reliefKind) {
        return baparamDao.selectReliefOptionListBy(reliefKind);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 傷病分類 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getEvTypOptionList() {
        return baparamDao.selectListBy(null, ConstantKey.BAPARAM_PARAMGROUP_EVTYP, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 年金請領資格 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getDisQualMkOptionList() {
        return baparamDao.selectListBy("0", ConstantKey.BAPARAM_PARAMGROUP_DISQUALMK, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 (失能) 結案原因 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getKcloseCauseOptionList() {
        return baparamDao.selectListBy(null, ConstantKey.BAPARAM_PARAMGROUP_KCLOSECAUSE, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 (遺屬) 結案原因 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getScloseCauseOptionList() {
        return baparamDao.selectListBy(null, ConstantKey.BAPARAM_PARAMGROUP_SCLOSECAUSE, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 (遺屬受款人資料更正) 結案原因 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getSurvivorCloseCauseOptionList() {
        return baparamDao.selectListBy(null, ConstantKey.BAPARAM_PARAMGROUP_SURVIVORCLOSECAUSE, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 (遺屬受款人資料更正) 不合格原因 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getSurvivorUnqualifiedCauseOptionList() {
        return baparamDao.selectListBy(null, ConstantKey.BAPARAM_PARAMGROUP_SURVIVORUNQUALIFIEDCAUSE, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 (遺屬) 申請項目 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getSurvivorApplicationDataUpdateApItemList() {
        return baparamDao.selectSurvivorApplicationDataUpdateApItemList();
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 複檢原因 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getReasCodeOptionList() {
        return baparamDao.selectListBy(null, ConstantKey.BAPARAM_PARAMGROUP_REASCODE, null);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 關係 下拉式選單的資料 for 複檢費用受理作業
     * 
     * @return
     */
    public List<Baparam> getReviewFeeReceiptRelationOptionList() {
        return baparamDao.selectReviewFeeReceiptRelationList();
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 給付方式 下拉式選單的資料 for 複檢費用受理作業
     * 
     * @return
     */
    public List<Baparam> getReviewFeeReceiptPayTypeOptionList() {
        return baparamDao.selectReviewFeeReceiptPayTypeList();
    }

    /**
     * 依傳入的條件取得 申請代算單位 (<code>PBBMSA</code>) 下拉式選單資料
     * 
     * @param brDate 事故者出生日期
     * @param idnNo 事故者身分證號
     * @param oldAplDpt 已存在的申請代算單位
     * @return
     */
    public List<Pbbmsa> getOldAplDptOptionList(String brDate, String idnNo, String oldAplDpt) {

        return pbbmsaDao.getOldAplDptOptionList(brDate, idnNo, oldAplDpt);
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 關係資料的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * (PARAMNAME||'-'||PARAMNAME) as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> getDependantRelationOptionList() {
        return baparamDao.selectDependantRelationOptionList();
    }

    /**
     * 取得 系統參數檔 (<code>BAPARAM</code>) 結案原因的下拉選單<br>
     * PARAMCODE as 下拉選單的Value <br>
     * PARAMNAME as 下拉選單的Option<br>
     * 
     * @return 內含 <code>Baparam</code> 物件的 List
     */
    public List<Baparam> getDependantCloseOptionList() {
        return baparamDao.selectDependantCloseOptionList();
    }

    /**
     * 依傳入的條件取得 行政支援記錄檔 (<code>BANOTIFY</code>) 的資料
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Maadmrec> selectMaadmrecBy(String apNo) {
        return maadmrecDao.selectMaadmrecDataBy(apNo);
    }

    /**
     * 自 系統參數檔 (<code>BAPARAM</code>) 取得 處理函別 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getUpdateLetterByBaban() {
        return baparamDao.selectListByBaban(ConstantKey.BAPARAM_PARAMTYP_SHARE, ConstantKey.BAPARAM_PARAMGROUP_LETTERTYPE, null);
    }

    /**
     * 自 資料庫處理程序主檔 (<code>BAPROCEDURE</code>) 取得 程式名稱 下拉式選單的資料
     * 
     * @return 內含 <code>Baprocedure</code> 物件的 List
     */
    public List<Baprocedure> getProcedureList() {
        return baprocedureDao.selectListBy();
    }

    /**
     * 自 學校代碼檔 (<code>NPCODE</code>) 取得 學校代碼 下拉式選單的資料
     * 
     * @return
     */
    public List<Npcode> selectNpCodeOptionBy() {
        return npcodeDao.selectNpCodeOptionBy();
    }

    public void setBaparamDao(BaparamDao baparamDao) {
        this.baparamDao = baparamDao;
    }

    public void setBacountryDao(BacountryDao bacountryDao) {
        this.bacountryDao = bacountryDao;
    }

    public void setBapandomkDao(BapandomkDao bapandomkDao) {
        this.bapandomkDao = bapandomkDao;
    }

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setBanotifyDao(BanotifyDao banotifyDao) {
        this.banotifyDao = banotifyDao;
    }

    public void setBafamilytempDao(BafamilytempDao bafamilytempDao) {
        this.bafamilytempDao = bafamilytempDao;
    }

    public void setPbbmsaDao(PbbmsaDao pbbmsaDao) {
        this.pbbmsaDao = pbbmsaDao;
    }

    public void setMaadmrecDao(MaadmrecDao maadmrecDao) {
        this.maadmrecDao = maadmrecDao;
    }

    public void setNpcodeDao(NpcodeDao npcodeDao) {
        this.npcodeDao = npcodeDao;
    }

    public void setBaprocedureDao(BaprocedureDao baprocedureDao) {
        this.baprocedureDao = baprocedureDao;
    }

}
