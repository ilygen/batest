package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.StringUtility;

public class OldAgeReviewRpt04Case implements Serializable{
    // 待收回[
    private String apNo;// 受理編號
    private String issuYm; // 核定年月
    private BigDecimal issueAmt = BigDecimal.ZERO; // 核付金額
    private BigDecimal recAmt = BigDecimal.ZERO;// 應收餘額
    private String appDate;// 申請(受理)日期
    private String seqNo;// 立帳序
    private String evtName;// 立帳對象
    private String evtIdnNo;// 身分證號
    private String evtBrDate;// 出生日期
    private String payItem;//給付項目
    private String deptName;//承保科別
    // ]
    // 待撥付[
    private String giveApNo;// 受理編號
    private String giveIssuYm; // 核定年月
    private BigDecimal giveIssueAmt = BigDecimal.ZERO; // 核付金額
    private BigDecimal giveRecAmt = BigDecimal.ZERO;// 應收餘額
    private String giveAppDate;// 申請(受理)日期
    private String giveSeqNo;// 受款人序
    private String giveEvtName;// 受款人名
    private String giveEvtIdnNo;// 身分證號
    private String giveEvtBrDate;// 出生日期

    // ]
    public String getApNo() {
        return StringUtils.defaultString(apNo);
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getIssuYm() {
        if (StringUtils.length(issuYm) == 6) {
            return DateUtility.changeWestYearMonthType(issuYm);
        }
        return StringUtils.defaultString(issuYm);
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getAppDate() {
        if (StringUtils.length(appDate) == 8) {
            return DateUtility.changeDateType(appDate);
        }
        return StringUtils.defaultString(appDate);
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getEvtName() {
        return StringUtils.defaultString(evtName);
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtIdnNo() {
        return StringUtils.defaultString(evtIdnNo);
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtBrDate() {
        if (StringUtils.length(evtBrDate) == 8) {
            return DateUtility.changeDateType(evtBrDate);
        }
        return StringUtils.defaultString(evtBrDate);
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public String getGiveApNo() {
        return StringUtils.defaultString(giveApNo);
    }

    public void setGiveApNo(String giveApNo) {
        this.giveApNo = giveApNo;
    }

    public String getGiveIssuYm() {
        if (StringUtils.length(giveIssuYm) == 6) {
            return DateUtility.changeWestYearMonthType(giveIssuYm);
        }
        return StringUtils.defaultString(giveIssuYm);
    }

    public void setGiveIssuYm(String giveIssuYm) {
        this.giveIssuYm = giveIssuYm;
    }

    public BigDecimal getGiveIssueAmt() {
        return giveIssueAmt;
    }

    public void setGiveIssueAmt(BigDecimal giveIssueAmt) {
        this.giveIssueAmt = giveIssueAmt;
    }

    public BigDecimal getGiveRecAmt() {
        return giveRecAmt;
    }

    public void setGiveRecAmt(BigDecimal giveRecAmt) {
        this.giveRecAmt = giveRecAmt;
    }

    public String getGiveAppDate() {
        if (StringUtils.length(giveAppDate) == 8) {
            return DateUtility.changeDateType(giveAppDate);
        }
        return StringUtils.defaultString(giveAppDate);
    }

    public void setGiveAppDate(String giveAppDate) {
        this.giveAppDate = giveAppDate;
    }

    public String getSeqNo() {
        return StringUtils.defaultString(seqNo);
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getGiveSeqNo() {
        return StringUtils.defaultString(giveSeqNo);
    }

    public void setGiveSeqNo(String giveSeqNo) {
        this.giveSeqNo = giveSeqNo;
    }

    public String getGiveEvtName() {
        return StringUtils.defaultString(giveEvtName);
    }

    public void setGiveEvtName(String giveEvtName) {
        this.giveEvtName = giveEvtName;
    }

    public String getGiveEvtIdnNo() {
        return StringUtils.defaultString(giveEvtIdnNo);
    }

    public void setGiveEvtIdnNo(String giveEvtIdnNo) {
        this.giveEvtIdnNo = giveEvtIdnNo;
    }

    public String getGiveEvtBrDate() {
        if (StringUtils.length(giveEvtBrDate) == 8) {
            return DateUtility.changeDateType(giveEvtBrDate);
        }
        return StringUtils.defaultString(giveEvtBrDate);
    }

    public void setGiveEvtBrDate(String giveEvtBrDate) {
        this.giveEvtBrDate = giveEvtBrDate;
    }

    public String getPayItem() {
        return StringUtils.defaultString(payItem);
    }

    public void setPayItem(String payItem) {
        this.payItem = payItem;
    }

    public String getDeptName() {
        return StringUtils.defaultString(deptName);
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getGivePayItem() {
        if (giveApNo.equals("") || giveApNo.equals(null)) {
            return "";
        }
        else {
            if (giveApNo.substring(0, 1).equals("L"))
                return ConstantKey.BALP0D040L_GIVE_PAY_KIND_L;
            else if (giveApNo.substring(0, 1).equals("K"))
                return ConstantKey.BALP0D040L_GIVE_PAY_KIND_K;
            else if (giveApNo.substring(0, 1).equals("S"))
                return ConstantKey.BALP0D040L_GIVE_PAY_KIND_S;
            else
                return "";
        }
    }

//    public String getPayItem() {
//        String apno56 = StringUtils.substring(apNo, 4, 6);
//        if (NumberUtils.isNumber(apno56)) {
//            switch (NumberUtils.toInt(apno56)) {
//                case 11:
//                    return ConstantKey.BALP0D040L_PAY_KIND_11;
//                case 21:
//                    return ConstantKey.BALP0D040L_PAY_KIND_21;
//                case 31:
//                    return ConstantKey.BALP0D040L_PAY_KIND_31;
//                case 41:
//                    return ConstantKey.BALP0D040L_PAY_KIND_41;
//                case 51:
//                    return ConstantKey.BALP0D040L_PAY_KIND_51;
//                case 52:
//                    return ConstantKey.BALP0D040L_PAY_KIND_52;
//                case 61:
//                    return ConstantKey.BALP0D040L_PAY_KIND_61;
//                case 91:
//                    return ConstantKey.BALP0D040L_PAY_KIND_91;
//                case 92:
//                    return ConstantKey.BALP0D040L_PAY_KIND_92;
//                case 82:
//                    return ConstantKey.BALP0D040L_PAY_KIND_82;
//                case 84:
//                    return ConstantKey.BALP0D040L_PAY_KIND_84;
//                case 85:
//                    return ConstantKey.BALP0D040L_PAY_KIND_85;
//                case 71:
//                    return ConstantKey.BALP0D040L_PAY_KIND_71;
//                case 73:
//                    return ConstantKey.BALP0D040L_PAY_KIND_73;
//                case 75:
//                    return ConstantKey.BALP0D040L_PAY_KIND_75;
//                case 13:
//                    return ConstantKey.BALP0D040L_PAY_KIND_13;
//                case 14:
//                    return ConstantKey.BALP0D040L_PAY_KIND_14;
//                case 33:
//                    return ConstantKey.BALP0D040L_PAY_KIND_33;
//                case 53:
//                    return ConstantKey.BALP0D040L_PAY_KIND_53;
//                default:
//                    return "";
//            }
//        }
//        else {
//            return "";
//        }
//
//    }

    public String getIssuYmStr() {
        if (StringUtils.isNotEmpty(issuYm))
            return DateUtility.changeWestYearMonthType(issuYm);
        else
            return "";
    }

    public String getAppDateStr() {
        if (StringUtils.isNotEmpty(appDate))
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(appDate), false);
        else
            return "";
    }

    public String getGiveAppDateStr() {
        if (StringUtils.isNotEmpty(giveAppDate))
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(giveAppDate), false);
        else
            return "";
    }

    public String getEvtBrDateStr() {
        if (StringUtils.isNotEmpty(evtBrDate))
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtBrDate), false);
        else
            return "";
    }

    public String getGiveEvtBrDateStr() {
        if (StringUtils.isNotEmpty(giveEvtBrDate))
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(giveEvtBrDate), false);
        else
            return "";
    }

    public String getGiveDeptName() {
        if (giveApNo.equals("") || giveApNo.equals(null)) {
            return "";
        }
        else {
            if (giveApNo.substring(0, 1).equals("L"))
                return ConstantKey.BALP0D040L_GIVE_DEPT_NAME_L;
            else if (giveApNo.substring(0, 1).equals("K"))
                return ConstantKey.BALP0D040L_GIVE_DEPT_NAME_K;
            else if (giveApNo.substring(0, 1).equals("S"))
                return ConstantKey.BALP0D040L_GIVE_DEPT_NAME_S;
            else
                return "";
        }
    }

//    public String getDeptName() {
//        String apno56 = StringUtils.substring(apNo, 4, 6);
//        if (NumberUtils.isNumber(apno56)) {
//            switch (NumberUtils.toInt(apno56)) {
//                case 11:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_11;
//                case 21:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_21;
//                case 31:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_31;
//                case 41:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_41;
//                case 51:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_51;
//                case 52:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_52;
//                case 61:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_61;
//                case 91:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_91;
//                case 92:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_92;
//                case 82:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_82;
//                case 84:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_84;
//                case 85:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_85;
//                case 71:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_71;
//                case 73:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_73;
//                case 75:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_75;
//                case 13:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_13;
//                case 14:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_14;
//                case 33:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_33;
//                case 53:
//                    return ConstantKey.BALP0D040L_DEPT_NAME_53;
//                default:
//                    return "";
//            }
//        }
//        else {
//            return "";
//        }
//
//    }

    public String getApNoStr() {
    	if(StringUtils.isNotBlank(apNo)){
        if (StringUtils.length(apNo) <= 12) {
            return apNo.substring(0, 3) + "-" + apNo.substring(3, 4) + "-" + apNo.substring(4, 6) + "-" + apNo.substring(6, 12);
        }
        else {
            return apNo.substring(0, 3) + "-" + apNo.substring(3, 4) + "-" + apNo.substring(4, 6) + "-" + apNo.substring(6, 12) + "-" + apNo.substring(12, apNo.length());
        }
    	}else{
    		return "";
    	}
    }

    public String getGiveApNoStr() {
        if (StringUtils.length(giveApNo) == ConstantKey.APNO_LENGTH)
            return giveApNo.substring(0, 1) + "-" + giveApNo.substring(1, 2) + "-" + giveApNo.substring(2, 7) + "-" + giveApNo.substring(7, 12);
        else
            return StringUtils.defaultString(giveApNo);
    }
}
