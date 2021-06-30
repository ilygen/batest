package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.Table;

/**
 * 給付入帳媒體明細錄檔
 * 
 * @author swim
 */
@Table("BAGIVEDTL")
public class Bagivedtl implements Serializable {
    private BigDecimal baBatchRecId; 
    
    private String mfileName; // 媒體檔案名稱
    private String mfileDate; // 媒體檔案日期
    private String seqNo; // 序號
    private String rc2; // 區別碼
    private String sunit2; // 發件單位
    private String hbank2; // 總行代號
    private String bbank2; // 分行代號
    private String taTyp2; // 出帳類別
    private String payDate2; // 出帳日期
    private String accNo2; // 入帳帳號
    private String amt2; // 交易金額
    private String stat2; // 狀況代號
    private String apNo2; // 受理編號
    private String seq2; // 受款人序
    private String payTyp2; // 給付種類
    private String space2; // 空白
    private String payYm2; // 給付年月
    private String idN2; // 身分證號
    private String name2; // 受款人姓名
    private String insKd2; // 保險別
    private String emgMk2; // 緊急專案註記
    private String rpayDate2; // 實際核付日期
    private String issuYm2; // 核定年月
    private String nc2; // 備註代碼
    private String issuTyp; // 核付分類
    private String compareMk; 
    private String updTime; // 異動日期時間

    //not in this file
    private String paramName;
    private String statCount;
    private BigDecimal statAmt;
    
    private String payCount1;
    private BigDecimal paySum1;
    private String payCount2;
    private BigDecimal paySum2;
    private String payCount3;
    private BigDecimal paySum3;
    private String payCount4;
    private BigDecimal paySum4;
    private String payCount5;
    private BigDecimal paySum5;
    
    public Bagivedtl() {

    }

    public String getMfileName() {
        return mfileName;
    }

    public void setMfileName(String mfileName) {
        this.mfileName = mfileName;
    }

    public String getMfileDate() {
        return mfileDate;
    }

    public void setMfileDate(String mfileDate) {
        this.mfileDate = mfileDate;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getRc2() {
        return rc2;
    }

    public void setRc2(String rc2) {
        this.rc2 = rc2;
    }

    public String getSunit2() {
        return sunit2;
    }

    public void setSunit2(String sunit2) {
        this.sunit2 = sunit2;
    }

    public String getHbank2() {
        return hbank2;
    }

    public void setHbank2(String hbank2) {
        this.hbank2 = hbank2;
    }

    public String getBbank2() {
        return bbank2;
    }

    public void setBbank2(String bbank2) {
        this.bbank2 = bbank2;
    }

    public String getTaTyp2() {
        return taTyp2;
    }

    public void setTaTyp2(String taTyp2) {
        this.taTyp2 = taTyp2;
    }

    public String getPayDate2() {
        return payDate2;
    }

    public void setPayDate2(String payDate2) {
        this.payDate2 = payDate2;
    }

    public String getAccNo2() {
        return accNo2;
    }

    public void setAccNo2(String accNo2) {
        this.accNo2 = accNo2;
    }

    public String getAmt2() {
        return amt2;
    }

    public void setAmt2(String amt2) {
        this.amt2 = amt2;
    }

    public String getStat2() {
        return stat2;
    }

    public void setStat2(String stat2) {
        this.stat2 = stat2;
    }

    public String getApNo2() {
        return apNo2;
    }

    public void setApNo2(String apNo2) {
        this.apNo2 = apNo2;
    }

    public String getSeq2() {
        return seq2;
    }

    public void setSeq2(String seq2) {
        this.seq2 = seq2;
    }

    public String getPayTyp2() {
        return payTyp2;
    }

    public void setPayTyp2(String payTyp2) {
        this.payTyp2 = payTyp2;
    }

    public String getSpace2() {
        return space2;
    }

    public void setSpace2(String space2) {
        this.space2 = space2;
    }

    public String getPayYm2() {
        return payYm2;
    }

    public void setPayYm2(String payYm2) {
        this.payYm2 = payYm2;
    }

    public String getIdN2() {
        return idN2;
    }

    public void setIdN2(String idN2) {
        this.idN2 = idN2;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getInsKd2() {
        return insKd2;
    }

    public void setInsKd2(String insKd2) {
        this.insKd2 = insKd2;
    }

    public String getEmgMk2() {
        return emgMk2;
    }

    public void setEmgMk2(String emgMk2) {
        this.emgMk2 = emgMk2;
    }

    public String getRpayDate2() {
        return rpayDate2;
    }

    public void setRpayDate2(String rpayDate2) {
        this.rpayDate2 = rpayDate2;
    }

    public String getIssuYm2() {
        return issuYm2;
    }

    public void setIssuYm2(String issuYm2) {
        this.issuYm2 = issuYm2;
    }

    public String getNc2() {
        return nc2;
    }

    public void setNc2(String nc2) {
        this.nc2 = nc2;
    }

    public String getIssuTyp() {
        return issuTyp;
    }

    public void setIssuTyp(String issuTyp) {
        this.issuTyp = issuTyp;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public BigDecimal getBaBatchRecId() {
        return baBatchRecId;
    }

    public void setBaBatchRecId(BigDecimal baBatchRecId) {
        this.baBatchRecId = baBatchRecId;
    }

    public String getCompareMk() {
        return compareMk;
    }

    public void setCompareMk(String compareMk) {
        this.compareMk = compareMk;
    }

    public String getStatCount() {
        return statCount;
    }

    public void setStatCount(String statCount) {
        this.statCount = statCount;
    }

    public BigDecimal getStatAmt() {
        return statAmt;
    }

    public void setStatAmt(BigDecimal statAmt) {
        this.statAmt = statAmt;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getPayCount1() {
        return payCount1;
    }

    public void setPayCount1(String payCount1) {
        this.payCount1 = payCount1;
    }

    public BigDecimal getPaySum1() {
        return paySum1;
    }

    public void setPaySum1(BigDecimal paySum1) {
        this.paySum1 = paySum1;
    }

    public String getPayCount2() {
        return payCount2;
    }

    public void setPayCount2(String payCount2) {
        this.payCount2 = payCount2;
    }

    public BigDecimal getPaySum2() {
        return paySum2;
    }

    public void setPaySum2(BigDecimal paySum2) {
        this.paySum2 = paySum2;
    }

    public String getPayCount3() {
        return payCount3;
    }

    public void setPayCount3(String payCount3) {
        this.payCount3 = payCount3;
    }

    public BigDecimal getPaySum3() {
        return paySum3;
    }

    public void setPaySum3(BigDecimal paySum3) {
        this.paySum3 = paySum3;
    }

    public String getPayCount4() {
        return payCount4;
    }

    public void setPayCount4(String payCount4) {
        this.payCount4 = payCount4;
    }

    public BigDecimal getPaySum4() {
        return paySum4;
    }

    public void setPaySum4(BigDecimal paySum4) {
        this.paySum4 = paySum4;
    }

    public String getPayCount5() {
        return payCount5;
    }

    public void setPayCount5(String payCount5) {
        this.payCount5 = payCount5;
    }

    public BigDecimal getPaySum5() {
        return paySum5;
    }

    public void setPaySum5(BigDecimal paySum5) {
        this.paySum5 = paySum5;
    }
}
