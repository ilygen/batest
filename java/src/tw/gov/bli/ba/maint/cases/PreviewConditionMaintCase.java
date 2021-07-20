package tw.gov.bli.ba.maint.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;

/**
 * Case for 先抽對象維護作業
 * 
 * @author Goston
 */
public class PreviewConditionMaintCase implements Serializable {
    private String payCode; // 給付種類
    private BigDecimal binsurSeni; // 投保年資 - 起
    private BigDecimal einsurSeni; // 投保年資 - 迄
    private BigDecimal bissueAmt; // 核定金額區間 - 起
    private BigDecimal eissueAmt; // 核定金額區間 - 迄
    private BigDecimal bcondition; // 區間條件 - 起
    private BigDecimal econdition; // 區間條件 - 迄
    private String condition1; // 條件一
    // private String condition2; // 條件二
    // private String condition3; // 條件二
    // private String condition4; // 條件四
    private String andOrTyp; // And Or 條件選項
    private BigDecimal range; // 抽樣間隔數
    private BigDecimal sampleVolume; // 抽樣件數
    private BigDecimal limitAmount; // 每月限制抽樣件數
    private String printTyp; // 列印方式
    private String enable; // 是否抽樣

    private String[] condition1Array; // 條件一 Array

    public void setCondition1Array(String[] condition1Array) {
        this.condition1Array = condition1Array;
        condition1 = StringUtils.join(condition1Array, ",");
    }

    public void setCondition1(String condition1) {
        this.condition1 = condition1;
        condition1Array = StringUtils.split(condition1, ",");
    }

    /**
     * 建構子
     */
    public PreviewConditionMaintCase() {
        andOrTyp = "0";
        range = new BigDecimal(0);
        sampleVolume = new BigDecimal(0);
        limitAmount = new BigDecimal(0);
        enable = "1";
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public BigDecimal getBinsurSeni() {
        return binsurSeni;
    }

    public void setBinsurSeni(BigDecimal binsurSeni) {
        this.binsurSeni = binsurSeni;
    }

    public BigDecimal getEinsurSeni() {
        return einsurSeni;
    }

    public void setEinsurSeni(BigDecimal einsurSeni) {
        this.einsurSeni = einsurSeni;
    }

    public BigDecimal getBissueAmt() {
        return bissueAmt;
    }

    public void setBissueAmt(BigDecimal bissueAmt) {
        this.bissueAmt = bissueAmt;
    }

    public BigDecimal getEissueAmt() {
        return eissueAmt;
    }

    public void setEissueAmt(BigDecimal eissueAmt) {
        this.eissueAmt = eissueAmt;
    }

    public BigDecimal getBcondition() {
        return bcondition;
    }

    public void setBcondition(BigDecimal bcondition) {
        this.bcondition = bcondition;
    }

    public BigDecimal getEcondition() {
        return econdition;
    }

    public void setEcondition(BigDecimal econdition) {
        this.econdition = econdition;
    }

    public String getAndOrTyp() {
        return andOrTyp;
    }

    public void setAndOrTyp(String andOrTyp) {
        this.andOrTyp = andOrTyp;
    }

    public BigDecimal getRange() {
        return range;
    }

    public void setRange(BigDecimal range) {
        this.range = range;
    }

    public BigDecimal getSampleVolume() {
        return sampleVolume;
    }

    public void setSampleVolume(BigDecimal sampleVolume) {
        this.sampleVolume = sampleVolume;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public String getPrintTyp() {
        return printTyp;
    }

    public void setPrintTyp(String printTyp) {
        this.printTyp = printTyp;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String[] getCondition1Array() {
        return condition1Array;
    }

    public String getCondition1() {
        return condition1;
    }
}
