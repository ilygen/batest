package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 先抽對象條件參數檔 (<code>BAPAINSPECT</code>)
 * 
 * @author Goston
 */
@Table("BAPAINSPECT")
public class Bapainspect implements Serializable {
    @PkeyField("PAYCODE")
    private String payCode; // 給付種類

    private BigDecimal binsurSeni; // 投保年資 - 起
    private BigDecimal einsurSeni; // 投保年資 - 迄
    private BigDecimal bissueAmt; // 核定金額區間 - 起
    private BigDecimal eissueAmt; // 核定金額區間 - 迄
    private BigDecimal bcondition; // 區間條件 - 起
    private BigDecimal econdition; // 區間條件 - 迄
    private String condition1; // 條件一
    private String condition2; // 條件二
    private String condition3; // 條件二
    private String condition4; // 條件四
    private String andOrTyp; // And Or 條件選項
    private BigDecimal range; // 抽樣間隔數
    private BigDecimal sampleVolume; // 抽樣件數
    private BigDecimal limitAmount; // 每月限制抽樣件數
    private String printTyp; // 列印方式
    private String enable; // 是否抽樣
    private String updUser; // 異動者代號
    private String updTime; // 異動日期時間

    public Bapainspect() {
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

    public String getCondition1() {
        return condition1;
    }

    public void setCondition1(String condition1) {
        this.condition1 = condition1;
    }

    public String getCondition2() {
        return condition2;
    }

    public void setCondition2(String condition2) {
        this.condition2 = condition2;
    }

    public String getCondition3() {
        return condition3;
    }

    public void setCondition3(String condition3) {
        this.condition3 = condition3;
    }

    public String getCondition4() {
        return condition4;
    }

    public void setCondition4(String condition4) {
        this.condition4 = condition4;
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

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

}
