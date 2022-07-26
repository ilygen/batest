package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BafamilyDao;
import tw.gov.bli.ba.dao.BafamilytempDao;
import tw.gov.bli.ba.dao.CvldtlDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bafamily;
import tw.gov.bli.ba.domain.Bafamilytemp;
import tw.gov.bli.ba.domain.Cvldtl;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptFamCase;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptBenCase;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Ajax Service for 受理作業
 * 
 * @author Rickychi
 */
public class ReceiptAjaxService {
    private static Log log = LogFactory.getLog(ReceiptAjaxService.class);

    private CvldtlDao cvldtlDao;
    private BaappbaseDao baappbaseDao;
    private BafamilyDao bafamilyDao;
    private BafamilytempDao bafamilytempDao;

    /**
     * 依傳入條件取得 戶政全戶檔 姓名資料<br>
     * 為避免回傳資料超過一筆以上, 故查詢結果不等於一筆皆回傳空字串<br>
     * 
     * @param idn 事故者身分證號
     * @param ebDate 出生日期
     * @return
     */
    public String selectCvldtlNameBy(String idn, String ebDate) {
        List<Cvldtl> list = cvldtlDao.selectDataBy(idn, ebDate);

        // 為避免回傳資料超過一筆以上, 故查詢結果不等於一筆皆回傳空字串
        if (list.size() == 1) {
            return ((Cvldtl) list.get(0)).getName();
        }
        else {
            return "";
        }
    }

    /**
     * 依傳入條件取得 眷屬暫存檔(<code>BAFAMILYTEMP</code>) 眷屬資料 for 失能年金受理作業
     * 
     * @param bafamilytempId 眷屬暫存檔資料列編號
     * @param seqNo 序號
     * @return <code>DisabledAnnuityReceiptFamCase</code> 物件
     */
    public DisabledAnnuityReceiptFamCase getDisabledFamTempDetailData(BigDecimal bafamilytempId, String seqNo) {
        List<Bafamilytemp> bafamilytempList = bafamilytempDao.selectDataBy(bafamilytempId, seqNo);
        DisabledAnnuityReceiptFamCase caseObj = new DisabledAnnuityReceiptFamCase();
        if (bafamilytempList.size() == 1) {
            Bafamilytemp bafamilytemp = bafamilytempList.get(0);
            BeanUtility.copyProperties(caseObj, bafamilytemp);
            caseObj = transDateForDisabledFam(caseObj);
        }
        return caseObj;
    }

    /**
     * 依傳入條件取得 眷屬檔(<code>BAFAMILY</code>) 眷屬資料 for 失能年金受理作業
     * 
     * @param bafamilyId 眷屬檔資料列編號
     * @param seqNo 序號
     * @return <code>DisabledAnnuityReceiptFamCase</code> 物件
     */
    public DisabledAnnuityReceiptFamCase getDisabledFamDetailData(BigDecimal bafamilyId) {
        List<Bafamily> bafamilyList = bafamilyDao.selectDataBy(bafamilyId, null, null, null);
        DisabledAnnuityReceiptFamCase caseObj = new DisabledAnnuityReceiptFamCase();
        if (bafamilyList != null) {
            Bafamily bafamily = bafamilyList.get(0);
            BeanUtility.copyProperties(caseObj, bafamily);
            caseObj = transDateForDisabledFam(caseObj);
        }
        return caseObj;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬詳細資料 for 遺屬年金受理作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @return <code>SurvivorAnnuityReceiptBenCase</code> 物件
     */
    public SurvivorAnnuityReceiptBenCase getSurvivorBenDetailData(BigDecimal baappbaseId) {
        Baappbase baappbase = baappbaseDao.selectDetailDataForSurvivorAnnuityReceiptBy(baappbaseId, null, null, null);
        SurvivorAnnuityReceiptBenCase caseObj = new SurvivorAnnuityReceiptBenCase();
        if (baappbase != null) {
            BeanUtility.copyProperties(caseObj, baappbase);
            caseObj.setBenAppDate(baappbase.getAppDate());
            caseObj.setPayBankIdBranchId(baappbase.getPayBankId() + baappbase.getBranchId());
            caseObj.setChkPayBankIdChkBranchId(caseObj.getChkPayBankId() + caseObj.getChkBranchId());
            caseObj = transDateForSurvivorBen(caseObj);
            Integer status = baappbaseDao.selectAccSeqNoAmt(caseObj.getApNo(), caseObj.getSeqNo());
            String accSeqNoAmt = "0";
            if (status > 0) {
                accSeqNoAmt = "1";
            }
            caseObj.setAccSeqNoAmt(accSeqNoAmt);
        }
        return caseObj;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬詳細資料 for 遺屬年金受理作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @return <code>SurvivorAnnuityReceiptBenCase</code> 物件
     */
    public SurvivorAnnuityReceiptBenCase getSurvivorBenTempDetailData(BigDecimal bafamilytempId, String seqNo) {
        List<Bafamilytemp> bafamilytempList = bafamilytempDao.selectDataBy(bafamilytempId, seqNo);
        SurvivorAnnuityReceiptBenCase caseObj = new SurvivorAnnuityReceiptBenCase();
        if (bafamilytempList.size() == 1) {
            Bafamilytemp bafamilytemp = bafamilytempList.get(0);
            BeanUtility.copyProperties(caseObj, bafamilytemp);
            caseObj.setPayBankIdBranchId(caseObj.getPayBankId() + caseObj.getBranchId());
            caseObj.setChkPayBankIdChkBranchId(caseObj.getChkPayBankId() + caseObj.getChkBranchId());
            caseObj = transDateForSurvivorBen(caseObj);
            Integer status = bafamilytempDao.selectAccSeqNoAmt(bafamilytempId, seqNo);
            String accSeqNoAmt = "0";
            if (status > 0) {
                accSeqNoAmt = "1";
            }
            caseObj.setAccSeqNoAmt(accSeqNoAmt);
        }
        return caseObj;
    }

    /**
     * 依傳入資料(<code>SurvivorAnnuityReceiptEvtCase</code>) 將西元年月日轉換成民國年月日 for 遺屬年金受理作業 事故者資料
     * 
     * @param caseobj 轉換資料之物件
     * @return <code>DisabledAnnuityReceiptFamCase</code> 物件
     */
    public DisabledAnnuityReceiptFamCase transDateForDisabledFam(DisabledAnnuityReceiptFamCase caseObj) {
        if (StringUtils.isNotBlank(caseObj.getFamAppDateStr())) {
            caseObj.setFamAppDate(caseObj.getFamAppDateStr());
        }
        if (StringUtils.isNotBlank(caseObj.getFamBrDateStr())) {
            caseObj.setFamBrDate(caseObj.getFamBrDateStr());
        }
        if (StringUtils.isNotBlank(caseObj.getMarryDateStr())) {
            caseObj.setMarryDate(caseObj.getMarryDateStr());
        }
        return caseObj;
    }

    /**
     * 依傳入資料(<code>SurvivorAnnuityReceiptBenCase</code>) 將西元年月日轉換成民國年月日
     * 
     * @param caseobj 轉換資料之物件
     * @return <code>SurvivorAnnuityReceiptCase</code> 物件
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

        if (StringUtils.isNotBlank(caseObj.getFamAppDate()) && caseObj.getFamAppDate().length() == 8)
            caseObj.setFamAppDate(DateUtility.changeDateType(caseObj.getFamAppDate()));
        if (StringUtils.isNotBlank(caseObj.getFamBrDate()) && caseObj.getFamBrDate().length() == 8)
            caseObj.setFamBrDate(DateUtility.changeDateType(caseObj.getFamBrDate()));
        // 收養日期
        if (StringUtils.isNotBlank(caseObj.getAdoPtDate()) && caseObj.getAdoPtDate().length() == 8) {
        	caseObj.setAdoPtDate(DateUtility.changeDateType(caseObj.getAdoPtDate()));
        }
        // 代辦人出生日期
        if (StringUtils.isNotBlank(caseObj.getAssignBrDate()) && caseObj.getAssignBrDate().length() == 8) {
        	caseObj.setAssignBrDate(DateUtility.changeDateType(caseObj.getAssignBrDate()));
        }
        return caseObj;
    }

    /**
     * 依傳入條件取得 具名領取(<code>BAAPPBASE</code>) 下拉選單 for 遺屬年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getBenOptionListForSurvivor(BigDecimal baappbaseId, String apNo) {
        List<Baappbase> list = baappbaseDao.selectBenListBy(baappbaseId, apNo, "1", new String[] { "2", "3", "4", "5", "6", "7" }, null);
        List<Baappbase> returnlist = new ArrayList<Baappbase>();
        for (int i = 0; i < list.size(); i++) {
            Baappbase baappbase = list.get(i);
            baappbase.setSeqNo(baappbase.getBaappbaseId().toString() + ";" + baappbase.getSeqNo());
            returnlist.add(baappbase);
        }

        return returnlist;
    }

    /**
     * 依傳入條件取得 具名領取(<code>BAAPPBASE</code>) 下拉選單 for 遺屬年金受理作業
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 受理編號
     * @return 內含 <code>Bafamilytemp</code> 物件的 List
     */
    public List<Bafamilytemp> getBenOptionListForSurvivorTemp(BigDecimal bafamilytempId, String seqNo) {
        List<Bafamilytemp> list = bafamilytempDao.selectBenListBy(bafamilytempId, seqNo);
        List<Bafamilytemp> returnlist = new ArrayList<Bafamilytemp>();
        for (int i = 0; i < list.size(); i++) {
            Bafamilytemp bafamilytemp = list.get(i);
            bafamilytemp.setAccSeqNo(bafamilytemp.getBafamilytempId().toString() + ";" + bafamilytemp.getAccSeqNo());
            returnlist.add(bafamilytemp);
        }

        return returnlist;
    }

    public void setCvldtlDao(CvldtlDao cvldtlDao) {
        this.cvldtlDao = cvldtlDao;
    }

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setBafamilyDao(BafamilyDao bafamilyDao) {
        this.bafamilyDao = bafamilyDao;
    }

    public void setBafamilytempDao(BafamilytempDao bafamilytempDao) {
        this.bafamilytempDao = bafamilytempDao;
    }
}
