package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * case for 轉呆帳核定清單
 * 
 * @author ZeHua
 */
public class OtherRpt08Case implements Serializable{
	private String apNo;// 受理編號
    private String seqNo; //受款人序號
    private String benName; //受款人姓名
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private BigDecimal issueAmt;// 核定金額
    private BigDecimal unacpAmt;// 應收金額
    private String chkDate;// 核定日期
    private String nlWkMk;    
    private String adWkMk;     
    private String naChgMk;
    private String cPrnDate; //印表日期
    private BigDecimal rptPage;//清冊頁次
    private BigDecimal apNoAmt;// 件數
	private BigDecimal dataAmt;// 筆數
	//private BigDecimal issueAmt;// 核定金額
	//private BigDecimal unacpAmt;// 應收金額
	//private String chkDate;// 核定日期
	// Footer使用
	private String rptTyp;
	private String payCode;
	// private String payTyp;
	// private String chkDate;
	private String benEvtRel;
	private BigDecimal accountSeq;
	private String accountNo;
	private String accountName;
	private BigDecimal payAmt;
	private String paySeqNo;
	private String procTime;
	private String benIdnNo;
	private BigDecimal recAmt;
	private BigDecimal recRem;
	private BigDecimal eRptPage; // 清冊頁次
	private String baunacpdtlId;
	private String bDebtYear;
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
    public String getBenName() {
        return benName;
    }
    public void setBenName(String benName) {
        this.benName = benName;
    }
    public String getPayYm() {
        return payYm;
    }
    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public String getApNoStr() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }    
    public String getPayCodeStr(String adWkMk, String nlWkMk, String isNaChgMk) {
        String payCodeStr = "";
        String isNaChgMkStr = "";
        String paySeqStr = "";          
        
        if ("Y".equals(isNaChgMk)) {
            if ("12".equals(naChgMk)) {
                isNaChgMkStr = " - 普改職";
            } else if ("13".equals(naChgMk)) {
                isNaChgMkStr = " - 普改加職";
            } else if ("21".equals(naChgMk)) {
                isNaChgMkStr = " - 職改普";
            } else if ("23".equals(naChgMk)) {
                isNaChgMkStr = " - 職改加職";
            } else if ("31".equals(naChgMk)) {
                isNaChgMkStr = " - 加職改普";
            } else if ("32".equals(naChgMk)) {
                isNaChgMkStr = " - 加職改職";
            }
        }
        else if ("N".equals(isNaChgMk)) {
            isNaChgMkStr = "";
        }        
        
        if(apNo.equals("") || apNo.equals(null)){
            return "";
        } else {
            if(apNo.substring(0, 1).equals("L") )
                return "普通-"+ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L+"(年金)";
            else if(apNo.substring(0, 1).equals("K"))
                
                if (("1").equals(nlWkMk)){
                    return payCodeStr = "普通-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K + "(年金)" + isNaChgMkStr;                
                }else if (("2").equals(nlWkMk) && ("1").equals(adWkMk)){
                    return payCodeStr = "職災-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K + "(年金)" + isNaChgMkStr;                
                }else if (("2").equals(nlWkMk) && ("2").equals(adWkMk)){
                    return payCodeStr = "加職-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K + "(年金)" + isNaChgMkStr;                
                }else {
                    return payCodeStr = "普通-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K + "(年金)";
                }                
                //return "○○"+ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K+"(年金)";
            else if(apNo.substring(0, 1).equals("S"))
                                
                if (("1").equals(nlWkMk)){
                    return payCodeStr = "普通-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S + "(年金)" + isNaChgMkStr;                
                }else if (("2").equals(nlWkMk) && ("1").equals(adWkMk)){
                    return payCodeStr = "職災-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S + "(年金)" + isNaChgMkStr;                
                }else if (("2").equals(nlWkMk) && ("2").equals(adWkMk)){
                    return payCodeStr = "加職-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S + "(年金)" + isNaChgMkStr;                
                }else {
                    return payCodeStr = "普通-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S + "(年金)";
                }                
                
                //return "○○"+ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S+"(年金)";
            else
                return "";          
        }
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
    public String getIssuYm() {
        return issuYm;
    }
    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }
    public BigDecimal getUnacpAmt() {
        return unacpAmt;
    }
    public void setUnacpAmt(BigDecimal unacpAmt) {
        this.unacpAmt = unacpAmt;
    }
    public String getChkDate() {
        return chkDate;
    }
    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
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


	public String getcPrnDate() {
		return cPrnDate;
	}


	public void setcPrnDate(String cPrnDate) {
		this.cPrnDate = cPrnDate;
	}


	public BigDecimal getRptPage() {
		return rptPage;
	}


	public void setRptPage(BigDecimal rptPage) {
		this.rptPage = rptPage;
	}


	public BigDecimal getApNoAmt() {
		return apNoAmt;
	}


	public void setApNoAmt(BigDecimal apNoAmt) {
		this.apNoAmt = apNoAmt;
	}


	public BigDecimal getDataAmt() {
		return dataAmt;
	}


	public void setDataAmt(BigDecimal dataAmt) {
		this.dataAmt = dataAmt;
	}


	public String getRptTyp() {
		return rptTyp;
	}


	public void setRptTyp(String rptTyp) {
		this.rptTyp = rptTyp;
	}


	public String getPayCode() {
		return payCode;
	}


	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}


	public String getBenEvtRel() {
		return benEvtRel;
	}


	public void setBenEvtRel(String benEvtRel) {
		this.benEvtRel = benEvtRel;
	}


	public BigDecimal getAccountSeq() {
		return accountSeq;
	}


	public void setAccountSeq(BigDecimal accountSeq) {
		this.accountSeq = accountSeq;
	}


	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public BigDecimal getPayAmt() {
		return payAmt;
	}


	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}


	public String getPaySeqNo() {
		return paySeqNo;
	}


	public void setPaySeqNo(String paySeqNo) {
		this.paySeqNo = paySeqNo;
	}


	public String getProcTime() {
		return procTime;
	}


	public void setProcTime(String procTime) {
		this.procTime = procTime;
	}


	public BigDecimal geteRptPage() {
		return eRptPage;
	}


	public void seteRptPage(BigDecimal eRptPage) {
		this.eRptPage = eRptPage;
	}


	public String getBenIdnNo() {
		return benIdnNo;
	}


	public void setBenIdnNo(String benIdnNo) {
		this.benIdnNo = benIdnNo;
	}


	public BigDecimal getRecAmt() {
		return recAmt;
	}


	public void setRecAmt(BigDecimal recAmt) {
		this.recAmt = recAmt;
	}


	public BigDecimal getRecRem() {
		return recRem;
	}


	public void setRecRem(BigDecimal recRem) {
		this.recRem = recRem;
	}


	public String getBaunacpdtlId() {
		return baunacpdtlId;
	}


	public void setBaunacpdtlId(String baunacpdtlId) {
		this.baunacpdtlId = baunacpdtlId;
	}


	public String getbDebtYear() {
		return bDebtYear;
	}


	public void setbDebtYear(String bDebtYear) {
		this.bDebtYear = bDebtYear;
	}
}
