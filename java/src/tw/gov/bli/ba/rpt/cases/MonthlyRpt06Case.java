package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;

public class MonthlyRpt06Case implements Serializable {
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String issuYm;// 核定年月
    private String caseTyp; // 案件類別
    private String payTyp; // 給付類別
    private String benName;// 受益人姓名
    private String chkMan;// 審核人員
    private String payBankId;// 金融機構總代號
    private String branchId;// 分支代號
    private String payEeacc;// 銀行帳號
    private String veriSeq; // 版次
    private String payYmBeg; // 給付年月(起)
    private String payYmEnd; // 給付年月(迄)
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal aplpayAmt; // 實付金額
    
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
    public String getCaseTyp() {
        return caseTyp;
    }
    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }
    public String getBenName() {
        return benName;
    }
    public void setBenName(String benName) {
        this.benName = benName;
    }
    public String getChkMan() {
        return chkMan;
    }
    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }
    public String getPayBankId() {
        return payBankId;
    }
    public void setPayBankId(String payBankId) {
        this.payBankId = payBankId;
    }
    public String getBranchId() {
        return branchId;
    }
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
    public String getPayEeacc() {
        return payEeacc;
    }
    public void setPayEeacc(String payEeacc) {
        this.payEeacc = payEeacc;
    }
    public String getVeriSeq() {
        return veriSeq;
    }
    public void setVeriSeq(String veriSeq) {
        this.veriSeq = veriSeq;
    }
    public String getPayYmBeg() {
        return payYmBeg;
    }
    public void setPayYmBeg(String payYmBeg) {
        this.payYmBeg = payYmBeg;
    }
    public String getPayYmEnd() {
        return payYmEnd;
    }
    public void setPayYmEnd(String payYmEnd) {
        this.payYmEnd = payYmEnd;
    }
    public BigDecimal getIssueAmt() {
        return issueAmt;
    }
    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }
    public BigDecimal getAplpayAmt() {
        return aplpayAmt;
    }
    public void setAplpayAmt(BigDecimal aplpayAmt) {
        this.aplpayAmt = aplpayAmt;
    }
    public String getApNoStr() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }    
    public String getPayCodeStr() {
        if(apNo.equals("") || apNo.equals(null)){
            return "";
        } else {
            if(apNo.substring(0, 1).equals("L") )
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
            else if(apNo.substring(0, 1).equals("K"))
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
            else if(apNo.substring(0, 1).equals("S"))
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
            else
                return "";          
        }
    }
    public String getPayCode() {
        if(apNo.equals("") || apNo.equals(null)){
            return "";
        } else {
            return apNo.substring(0, 1);          
        }
    }
    
    public String getCaseTypStr() {
        if(caseTyp.equals("1") )
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if(caseTyp.equals("2"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if(caseTyp.equals("3"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if(caseTyp.equals("4"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if(caseTyp.equals("5"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if(caseTyp.equals("6"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        else
            return "";
    }
    
    public String getPayBankIdStr() {
     	String payBankIdStr = "";
     	
     	payBankId = (StringUtils.isNotBlank(payBankId) ? payBankId : "");
     	branchId = (StringUtils.isNotBlank(branchId) ? branchId : "");
     	payEeacc = (StringUtils.isNotBlank(payEeacc) ? payEeacc : "");
     	
     	if(payTyp.equals("1") && !payBankId.equals("700")){
     		if(StringUtils.isBlank(payBankId) && StringUtils.isBlank(payEeacc)){
     		    payBankIdStr = "";
     		}else{
     			payBankIdStr = payBankId+"-0000-"+payEeacc;
     		}
     	}else{
     		payBankIdStr = payBankId+"-"+branchId+"-"+payEeacc;
     	}
         return payBankIdStr;
     }
    
    public String getPayYmStr() {
        if(StringUtils.isNotEmpty(payYmBeg) && StringUtils.isNotEmpty(payYmEnd))
            return DateUtility.changeWestYearMonthType(payYmBeg)+"~"+DateUtility.changeWestYearMonthType(payYmEnd);
        else
            return "";
    }
	public String getPayTyp() {
		return payTyp;
	}
	public void setPayTyp(String payTyp) {
		this.payTyp = payTyp;
	}
    
}