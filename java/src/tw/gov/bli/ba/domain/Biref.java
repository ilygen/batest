package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 就保給付檔
 * 
 * @author Rickychi
 */
@Table("BIREF")
public class Biref implements Serializable {
    private String payTyp;// 給付種類
    @PkeyField("APNO")
    private String apNo;// 受理編號
    private String staNo;// 就服站編號
    private String ideNo;// 認定編號
    private String idNo;// 身分證號
    private String brth;// 出生日期
    private String name;// 姓名
    private String tdte;// 離職日期
    private String ubno;// 離職保險證號
    private BigDecimal epYear;// 年數
    private BigDecimal epDay;// 日數
    private String apDte;// 受理日期
    private BigDecimal pwage;// 平均薪資
    private BigDecimal chkDay;// 核定日數
    private BigDecimal chkAmt;// 核定金額
    private String symDte;// 本次給付起日
    private String tymDte;// 本次給付迄日
    private String ckRs01;// 編審註記1
    private String ckRs02;// 編審註記2
    private String ckRs03;// 編審註記3
    private String ckRs04;// 編審註記4
    private String ckRs05;// 編審註記5
    private String ckRs06;// 編審註記6
    private String ckRs07;// 編審註記7
    private String ckRs08;// 編審註記8
    private String ckRs09;// 編審註記9
    private String ckRs10;// 編審註記10
    private String chkDte;// 核定日期
    private String npyDte;// 不給付日期
    private String ndcMrk;// 補件註記
    private String savDt1;// 補件日期
    private String sts1;// 初核狀況
    private String telNo1;// 聯絡電話1
    private String telNo2;// 聯絡電話2
    private String post;// 郵遞區號
    private String addres;// 通訊地址
    private String payWay;// 給付方式
    private String bnkall;// 銀行總行代號
    private String bnkBrn;// 分支代號
    private String hjNo;// 帳戶
    private BigDecimal netAmt;// 實付金額
    private String payDte;// 核付日期
    private String rtDte;// 退匯初核日期
    private String repDte;// 改匯日期
    private String rpDte;// 補發日期
    private BigDecimal rpAmt;// 補發金額
    private BigDecimal rbDte;// 收回日期
    private BigDecimal rbAmt;// 收回金額
    private String aprDte;// 求職登記日
    private String nubno;// 現職保險證號
    private String newkDt;// 覓得新職日期
    private String ideDte;// 認定日期
    private String rdeDte;// 再認定日期
    private String offCd;// 離職原因
    private String htDay;// 可核發最高天數
    private String othAmt;// 其他工作收入
    private String trUbno;// 訓練單位保險證號
    private String lvDte;// 離退訓日期
    private String rsDte;// 實際訓練起期
    private String reDte;// 實際訓練迄期
    private String trTyp;// 參訓方式
    private String lvcd;// 離退訓原因
    private String empSt;// 就業情形
    private String uempCd;// 未就業原因
    private String arDte;// 報到日期

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getStaNo() {
        return staNo;
    }

    public void setStaNo(String staNo) {
        this.staNo = staNo;
    }

    public String getIdeNo() {
        return ideNo;
    }

    public void setIdeNo(String ideNo) {
        this.ideNo = ideNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getBrth() {
        return brth;
    }

    public void setBrth(String brth) {
        this.brth = brth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTdte() {
        return tdte;
    }

    public void setTdte(String tdte) {
        this.tdte = tdte;
    }

    public String getUbno() {
        return ubno;
    }

    public void setUbno(String ubno) {
        this.ubno = ubno;
    }

    public BigDecimal getEpYear() {
        return epYear;
    }

    public void setEpYear(BigDecimal epYear) {
        this.epYear = epYear;
    }

    public BigDecimal getEpDay() {
        return epDay;
    }

    public void setEpDay(BigDecimal epDay) {
        this.epDay = epDay;
    }

    public String getApDte() {
        return apDte;
    }

    public void setApDte(String apDte) {
        this.apDte = apDte;
    }

    public BigDecimal getPwage() {
        return pwage;
    }

    public void setPwage(BigDecimal pwage) {
        this.pwage = pwage;
    }

    public BigDecimal getChkDay() {
        return chkDay;
    }

    public void setChkDay(BigDecimal chkDay) {
        this.chkDay = chkDay;
    }

    public BigDecimal getChkAmt() {
        return chkAmt;
    }

    public void setChkAmt(BigDecimal chkAmt) {
        this.chkAmt = chkAmt;
    }

    public String getSymDte() {
        return symDte;
    }

    public void setSymDte(String symDte) {
        this.symDte = symDte;
    }

    public String getTymDte() {
        return tymDte;
    }

    public void setTymDte(String tymDte) {
        this.tymDte = tymDte;
    }

    public String getCkRs01() {
        return ckRs01;
    }

    public void setCkRs01(String ckRs01) {
        this.ckRs01 = ckRs01;
    }

    public String getCkRs02() {
        return ckRs02;
    }

    public void setCkRs02(String ckRs02) {
        this.ckRs02 = ckRs02;
    }

    public String getCkRs03() {
        return ckRs03;
    }

    public void setCkRs03(String ckRs03) {
        this.ckRs03 = ckRs03;
    }

    public String getCkRs04() {
        return ckRs04;
    }

    public void setCkRs04(String ckRs04) {
        this.ckRs04 = ckRs04;
    }

    public String getCkRs05() {
        return ckRs05;
    }

    public void setCkRs05(String ckRs05) {
        this.ckRs05 = ckRs05;
    }

    public String getCkRs06() {
        return ckRs06;
    }

    public void setCkRs06(String ckRs06) {
        this.ckRs06 = ckRs06;
    }

    public String getCkRs07() {
        return ckRs07;
    }

    public void setCkRs07(String ckRs07) {
        this.ckRs07 = ckRs07;
    }

    public String getCkRs08() {
        return ckRs08;
    }

    public void setCkRs08(String ckRs08) {
        this.ckRs08 = ckRs08;
    }

    public String getCkRs09() {
        return ckRs09;
    }

    public void setCkRs09(String ckRs09) {
        this.ckRs09 = ckRs09;
    }

    public String getCkRs10() {
        return ckRs10;
    }

    public void setCkRs10(String ckRs10) {
        this.ckRs10 = ckRs10;
    }

    public String getChkDte() {
        return chkDte;
    }

    public void setChkDte(String chkDte) {
        this.chkDte = chkDte;
    }

    public String getNpyDte() {
        return npyDte;
    }

    public void setNpyDte(String npyDte) {
        this.npyDte = npyDte;
    }

    public String getNdcMrk() {
        return ndcMrk;
    }

    public void setNdcMrk(String ndcMrk) {
        this.ndcMrk = ndcMrk;
    }

    public String getSavDt1() {
        return savDt1;
    }

    public void setSavDt1(String savDt1) {
        this.savDt1 = savDt1;
    }

    public String getSts1() {
        return sts1;
    }

    public void setSts1(String sts1) {
        this.sts1 = sts1;
    }

    public String getTelNo1() {
        return telNo1;
    }

    public void setTelNo1(String telNo1) {
        this.telNo1 = telNo1;
    }

    public String getTelNo2() {
        return telNo2;
    }

    public void setTelNo2(String telNo2) {
        this.telNo2 = telNo2;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getBnkall() {
        return bnkall;
    }

    public void setBnkall(String bnkall) {
        this.bnkall = bnkall;
    }

    public String getBnkBrn() {
        return bnkBrn;
    }

    public void setBnkBrn(String bnkBrn) {
        this.bnkBrn = bnkBrn;
    }

    public String getHjNo() {
        return hjNo;
    }

    public void setHjNo(String hjNo) {
        this.hjNo = hjNo;
    }

    public BigDecimal getNetAmt() {
        return netAmt;
    }

    public void setNetAmt(BigDecimal netAmt) {
        this.netAmt = netAmt;
    }

    public String getPayDte() {
        return payDte;
    }

    public void setPayDte(String payDte) {
        this.payDte = payDte;
    }

    public String getRtDte() {
        return rtDte;
    }

    public void setRtDte(String rtDte) {
        this.rtDte = rtDte;
    }

    public String getRepDte() {
        return repDte;
    }

    public void setRepDte(String repDte) {
        this.repDte = repDte;
    }

    public String getRpDte() {
        return rpDte;
    }

    public void setRpDte(String rpDte) {
        this.rpDte = rpDte;
    }

    public BigDecimal getRpAmt() {
        return rpAmt;
    }

    public void setRpAmt(BigDecimal rpAmt) {
        this.rpAmt = rpAmt;
    }

    public BigDecimal getRbDte() {
        return rbDte;
    }

    public void setRbDte(BigDecimal rbDte) {
        this.rbDte = rbDte;
    }

    public BigDecimal getRbAmt() {
        return rbAmt;
    }

    public void setRbAmt(BigDecimal rbAmt) {
        this.rbAmt = rbAmt;
    }

    public String getAprDte() {
        return aprDte;
    }

    public void setAprDte(String aprDte) {
        this.aprDte = aprDte;
    }

    public String getNubno() {
        return nubno;
    }

    public void setNubno(String nubno) {
        this.nubno = nubno;
    }

    public String getNewkDt() {
        return newkDt;
    }

    public void setNewkDt(String newkDt) {
        this.newkDt = newkDt;
    }

    public String getIdeDte() {
        return ideDte;
    }

    public void setIdeDte(String ideDte) {
        this.ideDte = ideDte;
    }

    public String getRdeDte() {
        return rdeDte;
    }

    public void setRdeDte(String rdeDte) {
        this.rdeDte = rdeDte;
    }

    public String getOffCd() {
        return offCd;
    }

    public void setOffCd(String offCd) {
        this.offCd = offCd;
    }

    public String getHtDay() {
        return htDay;
    }

    public void setHtDay(String htDay) {
        this.htDay = htDay;
    }

    public String getOthAmt() {
        return othAmt;
    }

    public void setOthAmt(String othAmt) {
        this.othAmt = othAmt;
    }

    public String getTrUbno() {
        return trUbno;
    }

    public void setTrUbno(String trUbno) {
        this.trUbno = trUbno;
    }

    public String getLvDte() {
        return lvDte;
    }

    public void setLvDte(String lvDte) {
        this.lvDte = lvDte;
    }

    public String getRsDte() {
        return rsDte;
    }

    public void setRsDte(String rsDte) {
        this.rsDte = rsDte;
    }

    public String getReDte() {
        return reDte;
    }

    public void setReDte(String reDte) {
        this.reDte = reDte;
    }

    public String getTrTyp() {
        return trTyp;
    }

    public void setTrTyp(String trTyp) {
        this.trTyp = trTyp;
    }

    public String getLvcd() {
        return lvcd;
    }

    public void setLvcd(String lvcd) {
        this.lvcd = lvcd;
    }

    public String getEmpSt() {
        return empSt;
    }

    public void setEmpSt(String empSt) {
        this.empSt = empSt;
    }

    public String getUempCd() {
        return uempCd;
    }

    public void setUempCd(String uempCd) {
        this.uempCd = uempCd;
    }

    public String getArDte() {
        return arDte;
    }

    public void setArDte(String arDte) {
        this.arDte = arDte;
    }

}
