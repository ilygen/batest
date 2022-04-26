package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 災保遺屬年金給付受理編審清單 - 請領同類給付資料 - 一次給付
 * 
 * @author Rickychi
 */
public class DisasterInsuranceReviewRpt01SurvivorOncePayDataCase implements Serializable {
    private String bmPayKnd; // 給付類別
    private String bmEvidNo; // 事故者身分證號
    private String bmEvBrth; // 事故者出生日期
    private String bmEvtDte; // 死亡日期
    private BigDecimal bmApCnt; // 申請次數
    private String bmEvName; // 事故者姓名
    private String bmApDte; // 受理日期
    private String bmApNo; // 受理號碼
    private String bmExmDte; // 核定日期
    private BigDecimal bmBigPg; // 歸檔頁次
    private String bmPayDte; // 核付日期
    private String bmUbNo; // 保險證號
    private String bmEvType; // 傷病分類
    private String bmEvCode; // 傷病原因
    private BigDecimal bmChkDay; // 核定日(月)數
    private BigDecimal bmChkAmt; // 核定金額
    private BigDecimal bmAdjAmts; // 補發收回金額
    private String bmAdjDate; // 補發收回日期
    private String bmPayWay; // 給付方式
    private String bmUmk; // 單位歸墊註記
    private String bmNdocMk; // 補件註記
    private String bmNrepDate; // 補件日期
    private String bmNopMark; // 不給付註記
    private String bmNopDate; // 不給付日期
    private String bmGvIdNo; // 申請者身分證號
    private String bmBirGvBrth; // 申請者出生日期
    private String bmBirF01; // 保留
    private BigDecimal bmBirInsYr; // 投保有效年資
    private String bmInjInjNme; // 傷病名稱
    private String bmInjPfmDte; // 核定起日
    private String bmInjPtoDte; // 核定訖日
    private String bmInjInPart; // 受傷部位
    private String bmInjMedium; // 媒介物
    private String bmInjWangMk; // 領薪註記
    private String bmInjF01; // 保留
    private String bmCriSpch; // 逕退註記
    private String bmCriInjCl1; // 殘廢等級１
    private String bmCriInjCl2; // 殘廢等級２
    private String bmCriInjDp1; // 殘廢項目１
    private String bmCriInjDp2; // 殘廢項目２
    private String bmCriInjDp3; // 殘廢項目３
    private String bmCriInjDp4; // 殘廢項目４
    private String bmCriInjDp5; // 殘廢項目５
    private String bmCriInjDp6; // 殘廢項目６
    private String bmCriInPart; // 受傷部位
    private String bmCriMedium; // 媒介物
    private String bmCriInjNme; // 殘廢名稱
    private BigDecimal bmOldWage; // 投保薪資
    private BigDecimal bmOldInsYr; // 投保年資
    private BigDecimal bmOldOldYy; // 老年年資 - 年
    private BigDecimal bmOldOldDd; // 老年年資 - 日
    private BigDecimal bmOldSamYr; // 同單位年資
    private String bmOldMchOld; // 符合申請老年
    private String bmOldHmk; // 身障註記
    private String bmOldF01; // 保留
    private String bmDeaGvBrth; // 申請者出生日期
    private String bmDeaInPart; // 受傷部位
    private String bmDeaMedium; // 媒介物
    private BigDecimal bmDeaGvSum; // 受益人數
    private String bmDeaF01; // 保留
    private BigDecimal bmDeaWidAmt; // 保留遺屬津貼
    private String bmLosGvBrth; // 申請者出生日期
    private String bmLosFmDte; // 核定起日
    private String bmLosToDte; // 核定訖日
    private String bmLosF01; // 保留
    private BigDecimal bmPwage; // 平均薪資
    private String bmGvName; // 申請者姓名
    private String bmRtDte; // 退匯日期
    private String bmRepDte; // 改匯日期
    private String bmRepWay; // 改匯方式
    private String bmCkRs01; // 編審註記
    private String bmCkRs02; // 編審註記
    private String bmCkRs03; // 編審註記
    private String bmCkRs04; // 編審註記
    private String bmCkRs05; // 編審註記
    private String bmCkRs06; // 編審註記
    private String bmCkRs07; // 編審註記
    private String bmCkRs08; // 編審註記
    private String bmCkRs09; // 編審註記
    private String bmCkRs10; // 編審註記
    private String bmDept; // 工作部門
    private String bmChType; // 改前傷病分類
    private String bmRmk; // 改核註記
    private String bmF99; // 保留
    private String bmOldOrgDpt;// 原事業主管機關
    private String bmOldAplDpt;// 申請代算單位
    private String bmOldLawNo;// 法令依據代碼
    private String bmDeaapItem; //申請項目
    
    
    /**
     * 受理號碼 14 碼<br>
     * 格式: xxx-x-xx-xxxxxx-xx
     * 
     * @return
     */
    public String getBmApNoString() {
        if (bmApNo.length() == 12) {
            return bmApNo.substring(0, 3) + "-" + bmApNo.substring(3, 4) + "-" + bmApNo.substring(4, 6) + "-" + bmApNo.substring(6, 12);
        }
        else {
            this.bmApNo = StringUtils.rightPad(bmApNo, 14, " ");
            return bmApNo.substring(0, 3) + "-" + bmApNo.substring(3, 4) + "-" + bmApNo.substring(4, 6) + "-" + bmApNo.substring(6, 12) + "-" + bmApNo.substring(12, 14);
        }
    }
    
    
    /*
     * 受理日期
     */
    public String getBmApDteString(){
        if(StringUtils.length(bmApDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmApDte), false);
        else
            return StringUtils.defaultString(bmApDte);
    }
    
    /**
     * 核定起日
     * 
     * @return
     */
    public String getBmInjPfmDteString() {
        if (StringUtils.length(bmInjPfmDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmInjPfmDte), false);
        else
            return StringUtils.defaultString(bmInjPfmDte);
    }
    
    /**
     * 事故日期
     * 
     * @return
     */
    public String getBmEvtDteString() {
        if (StringUtils.length(bmEvtDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmEvtDte), false);
        else
            return StringUtils.defaultString(bmEvtDte);
    }

    /**
     * 核定日期 / 複核日期
     * 
     * @return
     */
    public String getBmExmDteString() {
        if (StringUtils.length(bmExmDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmExmDte), false);
        else
            return StringUtils.defaultString(bmExmDte);
    }

    /**
     * 核付日期
     * 
     * @return
     */
    public String getBmPayDteString() {
        if (StringUtils.length(bmPayDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmPayDte), false);
        else
            return StringUtils.defaultString(bmPayDte);
    }

    /**
     * 補件日期
     * 
     * @return
     */
    public String getBmNrepDateString() {
        if (StringUtils.length(bmNrepDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmNrepDate), false);
        else
            return StringUtils.defaultString(bmNrepDate);
    }

    /**
     * 不給付日期
     * 
     * @return
     */
    public String getBmNopDateString() {
        if (StringUtils.length(bmNopDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmNopDate), false);
        else
            return StringUtils.defaultString(bmNopDate);
    }
    
    // 失能項目
    public String getCriInJdpStr() {
        String criInJdp = "";
        if (StringUtils.isNotBlank(getBmCriInjDp1())) {
            criInJdp += getBmCriInjDp1() + " ";
        }
        if (StringUtils.isNotBlank(getBmCriInjDp2())) {
            criInJdp += getBmCriInjDp2() + " ";
        }
        if (StringUtils.isNotBlank(getBmCriInjDp3())) {
            criInJdp += getBmCriInjDp3() + " ";
        }
        if (StringUtils.isNotBlank(getBmCriInjDp4())) {
            criInJdp += getBmCriInjDp4() + " ";
        }
        if (StringUtils.isNotBlank(getBmCriInjDp5())) {
            criInJdp += getBmCriInjDp5() + " ";
        }
        if (StringUtils.isNotBlank(getBmCriInjDp6())) {
            criInJdp += getBmCriInjDp6() + " ";
        }
        
        return criInJdp;
    }
    
    // 失能等級
    public String getCriInJclStr() {
        String criInJcl = "";
        if (StringUtils.isNotBlank(getBmCriInjCl1())) {
            criInJcl += getBmCriInjCl1() + " ";
        }
        if (StringUtils.isNotBlank(getBmCriInjCl2())) {
            criInJcl += getBmCriInjCl2() + " ";
        }
       
        if (StringUtils.isNotBlank(criInJcl)) {
            criInJcl = criInJcl.substring(0, criInJcl.length() - 1);
        }
        return criInJcl;
    }
    
    public DisasterInsuranceReviewRpt01SurvivorOncePayDataCase(){
        
    }
    

    public String getBmPayKnd() {
        return bmPayKnd;
    }

    public void setBmPayKnd(String bmPayKnd) {
        this.bmPayKnd = bmPayKnd;
    }

    public String getBmEvidNo() {
        return bmEvidNo;
    }

    public void setBmEvidNo(String bmEvidNo) {
        this.bmEvidNo = bmEvidNo;
    }

    public String getBmEvBrth() {
        return bmEvBrth;
    }

    public void setBmEvBrth(String bmEvBrth) {
        this.bmEvBrth = bmEvBrth;
    }

    public String getBmEvtDte() {
        return bmEvtDte;
    }

    public void setBmEvtDte(String bmEvtDte) {
        this.bmEvtDte = bmEvtDte;
    }

    public BigDecimal getBmApCnt() {
        return bmApCnt;
    }

    public void setBmApCnt(BigDecimal bmApCnt) {
        this.bmApCnt = bmApCnt;
    }

    public String getBmEvName() {
        return bmEvName;
    }

    public void setBmEvName(String bmEvName) {
        this.bmEvName = bmEvName;
    }

    public String getBmApDte() {
        return bmApDte;
    }

    public void setBmApDte(String bmApDte) {
        this.bmApDte = bmApDte;
    }

    public String getBmApNo() {
        return bmApNo;
    }

    public void setBmApNo(String bmApNo) {
        this.bmApNo = bmApNo;
    }

    public String getBmExmDte() {
        return bmExmDte;
    }

    public void setBmExmDte(String bmExmDte) {
        this.bmExmDte = bmExmDte;
    }

    public BigDecimal getBmBigPg() {
        return bmBigPg;
    }

    public void setBmBigPg(BigDecimal bmBigPg) {
        this.bmBigPg = bmBigPg;
    }

    public String getBmPayDte() {
        return bmPayDte;
    }

    public void setBmPayDte(String bmPayDte) {
        this.bmPayDte = bmPayDte;
    }

    public String getBmUbNo() {
        return bmUbNo;
    }

    public void setBmUbNo(String bmUbNo) {
        this.bmUbNo = bmUbNo;
    }

    public String getBmEvType() {
        return bmEvType;
    }

    public void setBmEvType(String bmEvType) {
        this.bmEvType = bmEvType;
    }

    public String getBmEvCode() {
        return bmEvCode;
    }

    public void setBmEvCode(String bmEvCode) {
        this.bmEvCode = bmEvCode;
    }

    public BigDecimal getBmChkDay() {
        return bmChkDay;
    }

    public void setBmChkDay(BigDecimal bmChkDay) {
        this.bmChkDay = bmChkDay;
    }

    public BigDecimal getBmChkAmt() {
        return bmChkAmt;
    }

    public void setBmChkAmt(BigDecimal bmChkAmt) {
        this.bmChkAmt = bmChkAmt;
    }

    public BigDecimal getBmAdjAmts() {
        return bmAdjAmts;
    }

    public void setBmAdjAmts(BigDecimal bmAdjAmts) {
        this.bmAdjAmts = bmAdjAmts;
    }

    public String getBmAdjDate() {
        return bmAdjDate;
    }

    public void setBmAdjDate(String bmAdjDate) {
        this.bmAdjDate = bmAdjDate;
    }

    public String getBmPayWay() {
        return bmPayWay;
    }

    public void setBmPayWay(String bmPayWay) {
        this.bmPayWay = bmPayWay;
    }

    public String getBmUmk() {
        return bmUmk;
    }

    public void setBmUmk(String bmUmk) {
        this.bmUmk = bmUmk;
    }

    public String getBmNdocMk() {
        return bmNdocMk;
    }

    public void setBmNdocMk(String bmNdocMk) {
        this.bmNdocMk = bmNdocMk;
    }

    public String getBmNrepDate() {
        return bmNrepDate;
    }

    public void setBmNrepDate(String bmNrepDate) {
        this.bmNrepDate = bmNrepDate;
    }

    public String getBmNopMark() {
        return bmNopMark;
    }

    public void setBmNopMark(String bmNopMark) {
        this.bmNopMark = bmNopMark;
    }

    public String getBmNopDate() {
        return bmNopDate;
    }

    public void setBmNopDate(String bmNopDate) {
        this.bmNopDate = bmNopDate;
    }

    public String getBmGvIdNo() {
        return bmGvIdNo;
    }

    public void setBmGvIdNo(String bmGvIdNo) {
        this.bmGvIdNo = bmGvIdNo;
    }

    public String getBmBirGvBrth() {
        return bmBirGvBrth;
    }

    public void setBmBirGvBrth(String bmBirGvBrth) {
        this.bmBirGvBrth = bmBirGvBrth;
    }

    public String getBmBirF01() {
        return bmBirF01;
    }

    public void setBmBirF01(String bmBirF01) {
        this.bmBirF01 = bmBirF01;
    }

    public BigDecimal getBmBirInsYr() {
        return bmBirInsYr;
    }

    public void setBmBirInsYr(BigDecimal bmBirInsYr) {
        this.bmBirInsYr = bmBirInsYr;
    }

    public String getBmInjInjNme() {
        return bmInjInjNme;
    }

    public void setBmInjInjNme(String bmInjInjNme) {
        this.bmInjInjNme = bmInjInjNme;
    }

    public String getBmInjPfmDte() {
        return bmInjPfmDte;
    }

    public void setBmInjPfmDte(String bmInjPfmDte) {
        this.bmInjPfmDte = bmInjPfmDte;
    }

    public String getBmInjPtoDte() {
        return bmInjPtoDte;
    }

    public void setBmInjPtoDte(String bmInjPtoDte) {
        this.bmInjPtoDte = bmInjPtoDte;
    }

    public String getBmInjInPart() {
        return bmInjInPart;
    }

    public void setBmInjInPart(String bmInjInPart) {
        this.bmInjInPart = bmInjInPart;
    }

    public String getBmInjMedium() {
        return bmInjMedium;
    }

    public void setBmInjMedium(String bmInjMedium) {
        this.bmInjMedium = bmInjMedium;
    }

    public String getBmInjWangMk() {
        return bmInjWangMk;
    }

    public void setBmInjWangMk(String bmInjWangMk) {
        this.bmInjWangMk = bmInjWangMk;
    }

    public String getBmInjF01() {
        return bmInjF01;
    }

    public void setBmInjF01(String bmInjF01) {
        this.bmInjF01 = bmInjF01;
    }

    public String getBmCriSpch() {
        return bmCriSpch;
    }

    public void setBmCriSpch(String bmCriSpch) {
        this.bmCriSpch = bmCriSpch;
    }

    public String getBmCriInjCl1() {
        return bmCriInjCl1;
    }

    public void setBmCriInjCl1(String bmCriInjCl1) {
        this.bmCriInjCl1 = bmCriInjCl1;
    }

    public String getBmCriInjCl2() {
        return bmCriInjCl2;
    }

    public void setBmCriInjCl2(String bmCriInjCl2) {
        this.bmCriInjCl2 = bmCriInjCl2;
    }

    public String getBmCriInjDp1() {
        return bmCriInjDp1;
    }

    public void setBmCriInjDp1(String bmCriInjDp1) {
        this.bmCriInjDp1 = bmCriInjDp1;
    }

    public String getBmCriInjDp2() {
        return bmCriInjDp2;
    }

    public void setBmCriInjDp2(String bmCriInjDp2) {
        this.bmCriInjDp2 = bmCriInjDp2;
    }

    public String getBmCriInjDp3() {
        return bmCriInjDp3;
    }

    public void setBmCriInjDp3(String bmCriInjDp3) {
        this.bmCriInjDp3 = bmCriInjDp3;
    }

    public String getBmCriInjDp4() {
        return bmCriInjDp4;
    }

    public void setBmCriInjDp4(String bmCriInjDp4) {
        this.bmCriInjDp4 = bmCriInjDp4;
    }

    public String getBmCriInjDp5() {
        return bmCriInjDp5;
    }

    public void setBmCriInjDp5(String bmCriInjDp5) {
        this.bmCriInjDp5 = bmCriInjDp5;
    }

    public String getBmCriInjDp6() {
        return bmCriInjDp6;
    }

    public void setBmCriInjDp6(String bmCriInjDp6) {
        this.bmCriInjDp6 = bmCriInjDp6;
    }

    public String getBmCriInPart() {
        return bmCriInPart;
    }

    public void setBmCriInPart(String bmCriInPart) {
        this.bmCriInPart = bmCriInPart;
    }

    public String getBmCriMedium() {
        return bmCriMedium;
    }

    public void setBmCriMedium(String bmCriMedium) {
        this.bmCriMedium = bmCriMedium;
    }

    public String getBmCriInjNme() {
        return bmCriInjNme;
    }

    public void setBmCriInjNme(String bmCriInjNme) {
        this.bmCriInjNme = bmCriInjNme;
    }

    public BigDecimal getBmOldWage() {
        return bmOldWage;
    }

    public void setBmOldWage(BigDecimal bmOldWage) {
        this.bmOldWage = bmOldWage;
    }

    public BigDecimal getBmOldInsYr() {
        return bmOldInsYr;
    }

    public void setBmOldInsYr(BigDecimal bmOldInsYr) {
        this.bmOldInsYr = bmOldInsYr;
    }

    public BigDecimal getBmOldOldYy() {
        return bmOldOldYy;
    }

    public void setBmOldOldYy(BigDecimal bmOldOldYy) {
        this.bmOldOldYy = bmOldOldYy;
    }

    public BigDecimal getBmOldOldDd() {
        return bmOldOldDd;
    }

    public void setBmOldOldDd(BigDecimal bmOldOldDd) {
        this.bmOldOldDd = bmOldOldDd;
    }

    public BigDecimal getBmOldSamYr() {
        return bmOldSamYr;
    }

    public void setBmOldSamYr(BigDecimal bmOldSamYr) {
        this.bmOldSamYr = bmOldSamYr;
    }

    public String getBmOldMchOld() {
        return bmOldMchOld;
    }

    public void setBmOldMchOld(String bmOldMchOld) {
        this.bmOldMchOld = bmOldMchOld;
    }

    public String getBmOldHmk() {
        return bmOldHmk;
    }

    public void setBmOldHmk(String bmOldHmk) {
        this.bmOldHmk = bmOldHmk;
    }

    public String getBmOldF01() {
        return bmOldF01;
    }

    public void setBmOldF01(String bmOldF01) {
        this.bmOldF01 = bmOldF01;
    }

    public String getBmDeaGvBrth() {
        return bmDeaGvBrth;
    }

    public void setBmDeaGvBrth(String bmDeaGvBrth) {
        this.bmDeaGvBrth = bmDeaGvBrth;
    }

    public String getBmDeaInPart() {
        return bmDeaInPart;
    }

    public void setBmDeaInPart(String bmDeaInPart) {
        this.bmDeaInPart = bmDeaInPart;
    }

    public String getBmDeaMedium() {
        return bmDeaMedium;
    }

    public void setBmDeaMedium(String bmDeaMedium) {
        this.bmDeaMedium = bmDeaMedium;
    }

    public BigDecimal getBmDeaGvSum() {
        return bmDeaGvSum;
    }

    public void setBmDeaGvSum(BigDecimal bmDeaGvSum) {
        this.bmDeaGvSum = bmDeaGvSum;
    }

    public String getBmDeaF01() {
        return bmDeaF01;
    }

    public void setBmDeaF01(String bmDeaF01) {
        this.bmDeaF01 = bmDeaF01;
    }

    public BigDecimal getBmDeaWidAmt() {
        return bmDeaWidAmt;
    }

    public void setBmDeaWidAmt(BigDecimal bmDeaWidAmt) {
        this.bmDeaWidAmt = bmDeaWidAmt;
    }

    public String getBmLosGvBrth() {
        return bmLosGvBrth;
    }

    public void setBmLosGvBrth(String bmLosGvBrth) {
        this.bmLosGvBrth = bmLosGvBrth;
    }

    public String getBmLosFmDte() {
        return bmLosFmDte;
    }

    public void setBmLosFmDte(String bmLosFmDte) {
        this.bmLosFmDte = bmLosFmDte;
    }

    public String getBmLosToDte() {
        return bmLosToDte;
    }

    public void setBmLosToDte(String bmLosToDte) {
        this.bmLosToDte = bmLosToDte;
    }

    public String getBmLosF01() {
        return bmLosF01;
    }

    public void setBmLosF01(String bmLosF01) {
        this.bmLosF01 = bmLosF01;
    }

    public BigDecimal getBmPwage() {
        return bmPwage;
    }

    public void setBmPwage(BigDecimal bmPwage) {
        this.bmPwage = bmPwage;
    }

    public String getBmGvName() {
        return bmGvName;
    }

    public void setBmGvName(String bmGvName) {
        this.bmGvName = bmGvName;
    }

    public String getBmRtDte() {
        return bmRtDte;
    }

    public void setBmRtDte(String bmRtDte) {
        this.bmRtDte = bmRtDte;
    }

    public String getBmRepDte() {
        return bmRepDte;
    }

    public void setBmRepDte(String bmRepDte) {
        this.bmRepDte = bmRepDte;
    }

    public String getBmRepWay() {
        return bmRepWay;
    }

    public void setBmRepWay(String bmRepWay) {
        this.bmRepWay = bmRepWay;
    }

    public String getBmCkRs01() {
        return bmCkRs01;
    }

    public void setBmCkRs01(String bmCkRs01) {
        this.bmCkRs01 = bmCkRs01;
    }

    public String getBmCkRs02() {
        return bmCkRs02;
    }

    public void setBmCkRs02(String bmCkRs02) {
        this.bmCkRs02 = bmCkRs02;
    }

    public String getBmCkRs03() {
        return bmCkRs03;
    }

    public void setBmCkRs03(String bmCkRs03) {
        this.bmCkRs03 = bmCkRs03;
    }

    public String getBmCkRs04() {
        return bmCkRs04;
    }

    public void setBmCkRs04(String bmCkRs04) {
        this.bmCkRs04 = bmCkRs04;
    }

    public String getBmCkRs05() {
        return bmCkRs05;
    }

    public void setBmCkRs05(String bmCkRs05) {
        this.bmCkRs05 = bmCkRs05;
    }

    public String getBmCkRs06() {
        return bmCkRs06;
    }

    public void setBmCkRs06(String bmCkRs06) {
        this.bmCkRs06 = bmCkRs06;
    }

    public String getBmCkRs07() {
        return bmCkRs07;
    }

    public void setBmCkRs07(String bmCkRs07) {
        this.bmCkRs07 = bmCkRs07;
    }

    public String getBmCkRs08() {
        return bmCkRs08;
    }

    public void setBmCkRs08(String bmCkRs08) {
        this.bmCkRs08 = bmCkRs08;
    }

    public String getBmCkRs09() {
        return bmCkRs09;
    }

    public void setBmCkRs09(String bmCkRs09) {
        this.bmCkRs09 = bmCkRs09;
    }

    public String getBmCkRs10() {
        return bmCkRs10;
    }

    public void setBmCkRs10(String bmCkRs10) {
        this.bmCkRs10 = bmCkRs10;
    }

    public String getBmDept() {
        return bmDept;
    }

    public void setBmDept(String bmDept) {
        this.bmDept = bmDept;
    }

    public String getBmChType() {
        return bmChType;
    }

    public void setBmChType(String bmChType) {
        this.bmChType = bmChType;
    }

    public String getBmRmk() {
        return bmRmk;
    }

    public void setBmRmk(String bmRmk) {
        this.bmRmk = bmRmk;
    }

    public String getBmF99() {
        return bmF99;
    }

    public void setBmF99(String bmF99) {
        this.bmF99 = bmF99;
    }

    public String getBmOldOrgDpt() {
        return bmOldOrgDpt;
    }

    public void setBmOldOrgDpt(String bmOldOrgDpt) {
        this.bmOldOrgDpt = bmOldOrgDpt;
    }

    public String getBmOldAplDpt() {
        return bmOldAplDpt;
    }

    public void setBmOldAplDpt(String bmOldAplDpt) {
        this.bmOldAplDpt = bmOldAplDpt;
    }

    public String getBmOldLawNo() {
        return bmOldLawNo;
    }

    public void setBmOldLawNo(String bmOldLawNo) {
        this.bmOldLawNo = bmOldLawNo;
    }


    public String getBmDeaapItem() {
        return bmDeaapItem;
    }


    public void setBmDeaapItem(String bmDeaapItem) {
        this.bmDeaapItem = bmDeaapItem;
    }
    
    
}
