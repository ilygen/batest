package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

public class MonthlyRpt14Case implements Serializable{
    private String apNo;// 受理編號
    private String payYm;// 給付年月
    private String benIdn;// 受款人身份證號
    private String benName;// 受款人姓名
    private String payTyp; // 給付方式
    private BigDecimal issueAmt;// 核定金額
    private BigDecimal otheraAmt;// 另案扣減(同類保險)金額
    private BigDecimal otherbAmt;// 另案扣減(他類保險)金額
    private String payAccount;// 金融機構轉帳帳號
    private BigDecimal mitRate;// 匯款匯費
    private BigDecimal offsetAmt; // 抵銷紓困
    private BigDecimal compenAmt; // 代扣補償金
    private String issuTyp; // 案件類別
    private BigDecimal inherItorAmt; // 保留遺屬津貼
    private BigDecimal itrtTax;// 代扣利息所得稅
    private BigDecimal otherAmt;// 其他費用
    private String cPrnDate; //印表日期
    private BigDecimal rptPage;//清冊頁次
    //判斷給付別使用
    private String nlWkMk;    
    private String adWkMk;     
    private String naChgMk; 
    private String isNaChgMk;
    
    private String payBankId; // 金融機構總代號
    private String branchId; // 分支代號
    private String payEeacc; // 銀行帳號

    /**
         * 印表日期
         * 
         * @return
         */
     public String getcPrnDateStr() {
            if (StringUtils.isNotBlank(getcPrnDate()))
                return DateUtility.changeDateType(getcPrnDate());
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
    
    public String getApNo() {
        return apNo;
    }
    public void setApNo(String apNo) {
        this.apNo = apNo;
    }
    public String getPayYm() {
        return payYm;
    }
    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }
    public String getBenIdn() {
        return benIdn;
    }
    public void setBenIdn(String benIdn) {
        this.benIdn = benIdn;
    }
    public String getPayTyp() {
        return payTyp;
    }
    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }
    public BigDecimal getOtheraAmt() {
        return otheraAmt;
    }
    public void setOtheraAmt(BigDecimal otheraAmt) {
        this.otheraAmt = otheraAmt;
    }
    public BigDecimal getOtherbAmt() {
        return otherbAmt;
    }
    public void setOtherbAmt(BigDecimal otherbAmt) {
        this.otherbAmt = otherbAmt;
    }
    public String getPayAccount() {
        return payAccount;
    }
    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }
    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }
    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }
    public BigDecimal getCompenAmt() {
        return compenAmt;
    }
    public void setCompenAmt(BigDecimal compenAmt) {
        this.compenAmt = compenAmt;
    }
    public String getBenName() {
        return benName;
    }
    public void setBenName(String benName) {
        this.benName = benName;
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
                return "普通-"+ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L+"(年金)";
            else if(apNo.substring(0, 1).equals("K"))
                return "○○"+ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K+"(年金)";
            else if(apNo.substring(0, 1).equals("S"))
                return "○○"+ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S+"(年金)";
            else
                return "";          
        }
    }
    public String getIssuTypStr() {
        if(issuTyp.equals("1") )
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if(issuTyp.equals("2"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if(issuTyp.equals("5"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else 
            return "";
    }
    public String getPayTypStr() {
        if(payTyp.equals("1") )
            return "金融轉帳";
        else if(payTyp.equals("2"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_2;
        else if(payTyp.equals("3"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_3;
        else if(payTyp.equals("4"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_4;
        else if(payTyp.equals("5"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_5;
        else if(payTyp.equals("6"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_6;
        else if(payTyp.equals("7"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_7;
        else if(payTyp.equals("8"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_8;
        else if(payTyp.equals("9"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_9;
        else if(payTyp.equals("A"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_A;
        else  
            return "";
    }
    
    public String getDeptNameString() {
        String payKindCode = StringUtils.substring(apNo, 0, 1);

        if (StringUtils.equalsIgnoreCase(payKindCode, "L"))
            return "生老";
        else if (StringUtils.equalsIgnoreCase(payKindCode, "K"))
            return "傷殘";
        else if (StringUtils.equalsIgnoreCase(payKindCode, "S"))
            return "死亡";
        else
            return "";
    }
    public BigDecimal getIssueAmt() {
        return issueAmt;
    }
    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }
    public BigDecimal getMitRate() {
        return mitRate;
    }
    public void setMitRate(BigDecimal mitRate) {
        this.mitRate = mitRate;
    }
    public String getIssuTyp() {
        return issuTyp;
    }
    public void setIssuTyp(String issuTyp) {
        this.issuTyp = issuTyp;
    }
    public BigDecimal getInherItorAmt() {
        return inherItorAmt;
    }
    public void setInherItorAmt(BigDecimal inherItorAmt) {
        this.inherItorAmt = inherItorAmt;
    }
    public BigDecimal getItrtTax() {
        return itrtTax;
    }
    public void setItrtTax(BigDecimal itrtTax) {
        this.itrtTax = itrtTax;
    }
    public BigDecimal getOtherAmt() {
        return otherAmt;
    }
    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

	public String getcPrnDate() {
		return cPrnDate;
	}

	public void setcPrnDate(String cPrnDate) {
		this.cPrnDate = cPrnDate;
	}

	public String getNlWkMk() {
		return nlWkMk;
	}

	public void setNlWkMk(String nlWkMk) {
		this.nlWkMk = nlWkMk;
	}

	public String getAdWkMk() {
		return adWkMk;
	}

	public void setAdWkMk(String adWkMk) {
		this.adWkMk = adWkMk;
	}

	public String getNaChgMk() {
		return naChgMk;
	}

	public void setNaChgMk(String naChgMk) {
		this.naChgMk = naChgMk;
	}

	public String getIsNaChgMk() {
		return isNaChgMk;
	}

	public void setIsNaChgMk(String isNaChgMk) {
		this.isNaChgMk = isNaChgMk;
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

	public BigDecimal getRptPage() {
		return rptPage;
	}

	public void setRptPage(BigDecimal rptPage) {
		this.rptPage = rptPage;
	}
}
