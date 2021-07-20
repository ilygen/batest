package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 失能年金編審註記程度調整 - 受款人編審註記資料
 * 
 * @author Goston
 */
public class DisabledCheckMarkLevelAdjustBenDetailDataCase implements Serializable {
    private BigDecimal baChkFileId; // 資料列編號
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String chkCode; // 編審註記代號
    private String chkCodePre; // 編審註記代號類型
    private String chkCodePost; // 編審註記代號改後類型
    private String chkResult; // 編審結果說明
    private String keyValue; // 關鍵欄位值
    private String maxMk; // 最大給付年月註記
    private String adjLevel; // 程度調整
    private String valisYm; // 有效年月 - 起
    private String valieYm; // 有效年月 - 迄

    /**
     * 核定年月
     * 
     * @return
     */
    public String getIssuYmString() {
        return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(issuYm), false);
    }

    /**
     * 給付年月
     * 
     * @return
     */
    public String getPayYmString() {
        return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYm), false);
    }

    /**
     * 給付年月
     * 
     * @return
     */
    public String getPayYmShortString() {
        return DateUtility.changeWestYearMonthType(payYm);
    }

    /**
     * 編審註記代號類型
     * 
     * @return
     */
    public String getChkCodePreString() {
        if (StringUtils.equalsIgnoreCase(chkCodePre, "E"))
            return ConstantKey.BACHKFILE_CHKCODE_E;
        else if (StringUtils.equalsIgnoreCase(chkCodePre, "O"))
            return ConstantKey.BACHKFILE_CHKCODE_O;
        else if (StringUtils.equalsIgnoreCase(chkCodePre, "W"))
            return ConstantKey.BACHKFILE_CHKCODE_W;
        else if (StringUtils.equalsIgnoreCase(chkCodePre, "G"))
            return ConstantKey.BACHKFILE_CHKCODE_G;
        else
            return "";
    }

    /**
     * 有效年月 - 起
     * 
     * @return
     */
    public String getValisYmString() {
        if (StringUtils.length(valisYm) == 6)
            return DateUtility.changeWestYearMonthType(valisYm);
        else
            return StringUtils.defaultString(valisYm);
    }

    /**
     * 有效年月 - 迄
     * 
     * @return
     */
    public String getValieYmString() {
        if (StringUtils.length(valieYm) == 6)
            return DateUtility.changeWestYearMonthType(valieYm);
        else
            return StringUtils.defaultString(valieYm);
    }

    public DisabledCheckMarkLevelAdjustBenDetailDataCase() {

    }

    public BigDecimal getBaChkFileId() {
        return baChkFileId;
    }

    public void setBaChkFileId(BigDecimal baChkFileId) {
        this.baChkFileId = baChkFileId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public String getChkCode() {
        return chkCode;
    }

    public void setChkCode(String chkCode) {
        this.chkCode = chkCode;
    }

    public String getChkCodePre() {
        return chkCodePre;
    }

    public void setChkCodePre(String chkCodePre) {
        this.chkCodePre = chkCodePre;
    }

    public String getChkCodePost() {
        return chkCodePost;
    }

    public void setChkCodePost(String chkCodePost) {
        this.chkCodePost = chkCodePost;
    }

    public String getChkResult() {
        return chkResult;
    }

    public void setChkResult(String chkResult) {
        this.chkResult = chkResult;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getMaxMk() {
        return maxMk;
    }

    public void setMaxMk(String maxMk) {
        this.maxMk = maxMk;
    }

    public String getValisYm() {
        return valisYm;
    }

    public void setValisYm(String valisYm) {
        this.valisYm = valisYm;
    }

    public String getValieYm() {
        return valieYm;
    }

    public void setValieYm(String valieYm) {
        this.valieYm = valieYm;
    }

    public String getAdjLevel() {
        return adjLevel;
    }

    public void setAdjLevel(String adjLevel) {
        this.adjLevel = adjLevel;
    }

}
