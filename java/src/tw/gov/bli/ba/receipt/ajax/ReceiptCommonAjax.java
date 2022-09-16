package tw.gov.bli.ba.receipt.ajax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bafamilytemp;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptFamCase;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptBenCase;
import tw.gov.bli.ba.services.ReceiptAjaxService;
import tw.gov.bli.ba.services.ReceiptService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Ajax Service for 受理作業
 * 
 * @author Rickychi
 */
public class ReceiptCommonAjax {
    private static Log log = LogFactory.getLog(ReceiptCommonAjax.class);

    private ReceiptAjaxService receiptAjaxService;
    private ReceiptService receiptService;

    public void setReceiptAjaxService(ReceiptAjaxService receiptAjaxService) {
        this.receiptAjaxService = receiptAjaxService;
    }

    public void setReceiptService(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}

	/**
     * 依傳入條件取得 戶政全戶檔 姓名資料<br>
     * 
     * @param idn 事故者身分證號
     * @param ebDate 出生日期
     * @return
     */
    public String getCvldtlName(String idn, String ebDate) {
        log.debug("執行 ReceiptCommonAjax.getCvldtlName(" + StringUtils.defaultString(idn) + StringUtils.defaultString(ebDate) + ") ...");
        ebDate = DateUtility.changeDateType(ebDate);
        return receiptAjaxService.selectCvldtlNameBy(idn, ebDate);
    }

    /**
     * 依傳入條件取得 眷屬暫存檔詳細資料 for 失能年金受理作業<br>
     * 
     * @param baappbaseId 資料列編號
     * @return
     */
    public DisabledAnnuityReceiptFamCase getDisabledFamTempDetailData(BigDecimal bafamilytempId, String seqNo) {
        log.debug("執行 ReceiptCommonAjax.getDisabledFamTempDetailData(" + bafamilytempId + "," + seqNo + ") ...");
        return receiptAjaxService.getDisabledFamTempDetailData(bafamilytempId, seqNo);
    }

    /**
     * 依傳入條件取得 眷屬詳細資料 for 失能年金受理作業<br>
     * 
     * @param baappbaseId 資料列編號
     * @return
     */
    public DisabledAnnuityReceiptFamCase getDisabledFamDetailData(BigDecimal bafamilyId) {
        log.debug("執行 ReceiptCommonAjax.getDisabledFamDetailData(" + bafamilyId + ") ...");
        return receiptAjaxService.getDisabledFamDetailData(bafamilyId);
    }

    /**
     * 依傳入條件取得 遺屬詳細資料<br>
     * 
     * @param baappbaseId 資料列編號
     * @return
     */
    public SurvivorAnnuityReceiptBenCase getSurvivorBenDetailData(BigDecimal baappbaseId) {
        log.debug("執行 ReceiptCommonAjax.getSurvivorDetail(" + baappbaseId + ") ...");
        return receiptAjaxService.getSurvivorBenDetailData(baappbaseId);
    }
    
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬詳細資料 for 遺屬年金受理作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @return <code>SurvivorAnnuityReceiptBenCase</code> 物件
     */
    public SurvivorAnnuityReceiptBenCase getSurvivorBenTempDetailData(BigDecimal bafamilytempId, String seqNo) {
        log.debug("執行 ReceiptCommonAjax.getSurvivorBenTempDetailData(" + bafamilytempId + "," + seqNo + ") ...");
        return receiptAjaxService.getSurvivorBenTempDetailData(bafamilytempId, seqNo);
    }

    /**
     * 依傳入條件取得 具名領取(<code>BAAPPBASE</code>) 下拉選單 for 遺屬年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getBenOptionListForSurvivor(BigDecimal baappbaseId, String apNo) {
        log.debug("執行 ReceiptCommonAjax.getBenOptionListForSurvivor(" + baappbaseId + "," + apNo + ") ...");
        return receiptAjaxService.getBenOptionListForSurvivor(baappbaseId, apNo);
    }
    
    /**
     * 依傳入條件取得 具名領取(<code>BAAPPBASE</code>) 下拉選單 for 遺屬年金受理作業
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 受理編號
     * @return 內含 <code>Bafamilytemp</code> 物件的 List
     */
    public List<Bafamilytemp> getBenOptionListForSurvivorTemp(BigDecimal bafamilytempId, String seqNo) {
        log.debug("執行 ReceiptCommonAjax.getBenOptionListForSurvivorTemp(" + bafamilytempId + "," + seqNo + ") ...");
        return receiptAjaxService.getBenOptionListForSurvivorTemp(bafamilytempId, seqNo);
    }
    
    /**
     * 依給付方式、帳號前7碼取得金融機構名稱
     * 
     * @param payTyp
     * @param payBankIdBranchId
     * @return
     */
    public String getBankName(String payTyp, String payBankIdBranchId) {
    	log.debug("執行 ReceiptCommonAjax.getBankName(" + payTyp + "," + payBankIdBranchId + ") ...");
    	String bankName = "";
    	if (StringUtils.equals(payTyp, "1")) {
    		bankName = receiptService.getBankName(payBankIdBranchId);
    	} else if (StringUtils.equals(payTyp, "2")) {
    		bankName = receiptService.getPostName(payBankIdBranchId);
    	}
    	return bankName;
    }
}
