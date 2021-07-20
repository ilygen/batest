package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

public class OldAgeReviewRpt02Case implements Serializable {
    private String rptTyp;// 報表種類
    private String rptDate;// 列印日期
    private String version;// 版次
    private BigDecimal pageNo;// 頁次
    private BigDecimal seqNo;// 序號
    private String payCode;// 給付別
    private String apNo;// 受理編號
    private String evtName;// 事故者姓名
    private String caseTyp; // 案件類別
    private String paysYm; // 給付年月(起)
    private String payeYm; // 給付年月(迄)
    private String veriSeq; // 版次
    private BigDecimal tissueAmt;// 合計核定金額
    private BigDecimal taplPayAmt;// 合計實付金額
    private String chkDate; // 審核日期
    private String chkMan;// 審核人員
    private String mexcLvl; // 應決行層級
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String appDate;// 申請(受理)日期
    private String issuYm; // 核定年月
    private String upCauseCode; // 異動狀況
    private String cprnDate; // 核定清單印表日期
    private String cprnPage; // 核定清單頁次

    // 頁面顯式轉換
    public String getMexcLvlStr() {
        String mexcLvlStr = "";
        if ((ConstantKey.BAAPPBASE_MEXCLVL_1).equals(getMexcLvl())) {
            mexcLvlStr = ConstantKey.BAAPPBASE_MEXCLVL_1 + "-" + ConstantKey.BAAPPBASE_MEXCLVL_STR_1;
        }
        else if ((ConstantKey.BAAPPBASE_MEXCLVL_2).equals(getMexcLvl())) {
            mexcLvlStr = ConstantKey.BAAPPBASE_MEXCLVL_2 + "-" + ConstantKey.BAAPPBASE_MEXCLVL_STR_2;
        }
        else if ((ConstantKey.BAAPPBASE_MEXCLVL_3).equals(getMexcLvl())) {
            mexcLvlStr = ConstantKey.BAAPPBASE_MEXCLVL_3 + "-" + ConstantKey.BAAPPBASE_MEXCLVL_STR_3;
        }
        else if ((ConstantKey.BAAPPBASE_MEXCLVL_4).equals(getMexcLvl())) {
            mexcLvlStr = ConstantKey.BAAPPBASE_MEXCLVL_4 + "-" + ConstantKey.BAAPPBASE_MEXCLVL_STR_4;
        }
        else if ((ConstantKey.BAAPPBASE_MEXCLVL_5).equals(getMexcLvl())) {
            mexcLvlStr = ConstantKey.BAAPPBASE_MEXCLVL_5 + "-" + ConstantKey.BAAPPBASE_MEXCLVL_STR_5;
        }
        else if ((ConstantKey.BAAPPBASE_MEXCLVL_6).equals(getMexcLvl())) {
            mexcLvlStr = ConstantKey.BAAPPBASE_MEXCLVL_6 + "-" + ConstantKey.BAAPPBASE_MEXCLVL_STR_6;
        }
        return mexcLvlStr;
    }

    public String getApNoStr() {
        if (StringUtils.isNotBlank(apNo) && apNo.length() == ConstantKey.APNO_LENGTH) {
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        }
        else {
            return "";
        }
    }

    public String getPayCodeStr() {
        String payCodeStr = "";
        if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(getPayCode())) {
            payCodeStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
        }
        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(getPayCode())) {
            payCodeStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
        }
        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(getPayCode())) {
            payCodeStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
        }
        return payCodeStr;
    }

    public String getCaseTypStr() {
        String caseTypStr = "";
        if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_1;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_2;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_3;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_4;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_5;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_6;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_A).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_A;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_B).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_B;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_C).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_C;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_D).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_D;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_E).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_E;
        }
        else if ((ConstantKey.BAAPPBASE_CASETYP_F).equals(getCaseTyp())) {
            caseTypStr = ConstantKey.BAAPPBASE_CASETYP_STR_F;
        }
        return caseTypStr;
    }

    public String getRptDateStr() {
        if (StringUtils.isNotBlank(getRptDate())) {
            return DateUtility.changeDateType(getRptDate());
        }
        else {
            return "";
        }
    }

    public String getChkDateStr() {
        if (StringUtils.isNotBlank(getChkDate())) {
            return DateUtility.changeDateType(getChkDate());
        }
        else {
            return "";
        }
    }

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm())) {
            return DateUtility.changeWestYearMonthType(getIssuYm());
        }
        else {
            return "";
        }
    }

    public String getPaysYmStr() {
        if (StringUtils.isNotBlank(getPaysYm())) {
            return DateUtility.changeWestYearMonthType(getPaysYm());
        }
        else {
            return "";
        }
    }

    public String getPayeYmStr() {
        if (StringUtils.isNotBlank(getPayeYm())) {
            return DateUtility.changeWestYearMonthType(getPayeYm());
        }
        else {
            return "";
        }
    }

    // ]

    public String getRptTyp() {
        return rptTyp;
    }

    public void setRptTyp(String rptTyp) {
        this.rptTyp = rptTyp;
    }

    public String getRptDate() {
        return rptDate;
    }

    public void setRptDate(String rptDate) {
        this.rptDate = rptDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public BigDecimal getPageNo() {
        return pageNo;
    }

    public void setPageNo(BigDecimal pageNo) {
        this.pageNo = pageNo;
    }

    public BigDecimal getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(BigDecimal seqNo) {
        this.seqNo = seqNo;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getPaysYm() {
        return paysYm;
    }

    public void setPaysYm(String paysYm) {
        this.paysYm = paysYm;
    }

    public String getPayeYm() {
        return payeYm;
    }

    public void setPayeYm(String payeYm) {
        this.payeYm = payeYm;
    }

    public String getVeriSeq() {
        return veriSeq;
    }

    public void setVeriSeq(String veriSeq) {
        this.veriSeq = veriSeq;
    }

    public BigDecimal getTissueAmt() {
        return tissueAmt;
    }

    public void setTissueAmt(BigDecimal tissueAmt) {
        this.tissueAmt = tissueAmt;
    }

    public BigDecimal getTaplPayAmt() {
        return taplPayAmt;
    }

    public void setTaplPayAmt(BigDecimal taplPayAmt) {
        this.taplPayAmt = taplPayAmt;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }

    public String getMexcLvl() {
        return mexcLvl;
    }

    public void setMexcLvl(String mexcLvl) {
        this.mexcLvl = mexcLvl;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getUpCauseCode() {
        return upCauseCode;
    }

    public void setUpCauseCode(String upCauseCode) {
        this.upCauseCode = upCauseCode;
    }

    public String getCprnDate() {
        return cprnDate;
    }

    public void setCprnDate(String cprnDate) {
        this.cprnDate = cprnDate;
    }

    public String getCprnPage() {
        return cprnPage;
    }

    public void setCprnPage(String cprnPage) {
        this.cprnPage = cprnPage;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
}
