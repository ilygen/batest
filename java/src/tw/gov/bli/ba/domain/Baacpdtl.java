package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * BAACPDTL
 * 
 * @author Noctis
 */
@Table("BAACPDTL")
public class Baacpdtl implements Serializable {
    
	private BigDecimal baunacpdtlId;// 資料列編號(應收帳務明細編號)
	private BigDecimal baacpdtlId;// 資料列編號(應收帳務明細編號)
	private String benIds;// 受款人社福識別碼
    private String benIdnNo;// 受款人身分證號
    private BigDecimal recAmt;// 應收金額
    private String apNo;      // 受理編號
    private String seqNo;     // 受款人序
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private String disPayKind;// 給付別
    
    //For CashReceiveDataCase
    private String insKd;// 保險別
    //PFPCCKY bli_Account_Code
    private String bli_Account_Code;// 局帳戶代號
    private String bookedBook;// 入帳方式
    private String bkAccountDt;// 銀行入帳日期
    private String batchNo;// 批號
    private String serialNo;// 流水號
    //PFPCCKY cash_Amt 
    private BigDecimal cash_Amt;// 現金金額
    private String divSeq;// 分割序號
    private String index_No;// 序號
    //For CancelCashReceive
    private String insKdCash; //保險別
    //BAACPDTL bli_Account_Code cashAmt
    private String bliAccountCode; //局帳戶代號
    private BigDecimal cashAmt; //現金金額
    private String indexNo;// 序號
    
    //For CashReceiveDataCase
    private String transActionId;// 交易編號
    private String transActionSeq;// 交易序號
    
    private String empNo;// 處理人員
    
    // For 整批收回核定清單
    private String payKind;      // 給付別
    private String chkDate;
    private String issuTyp;
    private String benName;
    private String payBankId;    // 金融機構總代號
    private String branchId;     // 金融機構分支代號
    private String payEeacc;     // 銀行帳號    
    private BigDecimal issueAmt; // 核定金額
    private String nlWkMk;       // 普職註記
    private String adWkMk;       // 加職註記
    private String naChgMk;      // 普職互改註記    
    private String acpDate;      // 收回日期(月核定日期)
    private String caseTyp;      // 案件類別
    private String payTyp;       // 給付方式 
    private String sts;// 資料狀態
    private String typeMk; // 類別註記(已收金額來源)
    
    
    public Baacpdtl() {

    }

	public BigDecimal getBaunacpdtlId() {
		return baunacpdtlId;
	}

	public void setBaunacpdtlId(BigDecimal baunacpdtlId) {
		this.baunacpdtlId = baunacpdtlId;
	}
	
    public BigDecimal getBaacpdtlId() {
        return baacpdtlId;
    }

    public void setBaacpdtlId(BigDecimal baacpdtlId) {
        this.baacpdtlId = baacpdtlId;
    }

	public String getBenIds() {
		return benIds;
	}

	public void setBenIds(String benIds) {
		this.benIds = benIds;
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

	public String getPayYm() {
		return payYm;
	}

	public void setPayYm(String payYm) {
		this.payYm = payYm;
	}

	public String getInsKd() {
		return insKd;
	}

	public void setInsKd(String insKd) {
		this.insKd = insKd;
	}

	public String getBli_Account_Code() {
		return bli_Account_Code;
	}

	public void setBli_Account_Code(String bli_Account_Code) {
		this.bli_Account_Code = bli_Account_Code;
	}

	public String getBookedBook() {
		return bookedBook;
	}

	public void setBookedBook(String bookedBook) {
		this.bookedBook = bookedBook;
	}

	public String getBkAccountDt() {
		return bkAccountDt;
	}

	public void setBkAccountDt(String bkAccountDt) {
		this.bkAccountDt = bkAccountDt;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public BigDecimal getCash_Amt() {
		return cash_Amt;
	}

	public void setCash_Amt(BigDecimal cash_Amt) {
		this.cash_Amt = cash_Amt;
	}

	public String getDivSeq() {
		return divSeq;
	}

	public void setDivSeq(String divSeq) {
		this.divSeq = divSeq;
	}

	public String getIndex_No() {
		return index_No;
	}

	public void setIndex_No(String index_No) {
		this.index_No = index_No;
	}

	public String getTransActionId() {
		return transActionId;
	}

	public void setTransActionId(String transActionId) {
		this.transActionId = transActionId;
	}

	public String getTransActionSeq() {
		return transActionSeq;
	}

	public void setTransActionSeq(String transActionSeq) {
		this.transActionSeq = transActionSeq;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getDisPayKind() {
		return disPayKind;
	}

	public void setDisPayKind(String disPayKind) {
		this.disPayKind = disPayKind;
	}

	public String getInsKdCash() {
		return insKdCash;
	}

	public void setInsKdCash(String insKdCash) {
		this.insKdCash = insKdCash;
	}

	public String getBliAccountCode() {
		return bliAccountCode;
	}

	public void setBliAccountCode(String bliAccountCode) {
		this.bliAccountCode = bliAccountCode;
	}

	public BigDecimal getCashAmt() {
		return cashAmt;
	}

	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}

	public String getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}
	
    public String getPayKind() {
        return payKind;
    }
    
    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }    
    
    public String getChkDate() {
        return chkDate;
    }
    
    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getIssuTyp() {
        return issuTyp;
    }
    
    public void setIssuTyp(String issuTyp) {
        this.issuTyp = issuTyp;
    }	
	
    public String getBenName() {
        return benName;
    }
    
    public void setBenName(String benName) {
        this.benName = benName;
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

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }
    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
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

    public String getAcpDate() {
        return acpDate;
    }

    public void setAcpDate(String acpDate) {
        this.acpDate = acpDate;
    }
    
    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }
    
    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }
    
    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }
    
    public String getTypeMk() {
        return typeMk;
    }

    public void setTypeMk(String typeMk) {
        this.typeMk = typeMk;
    }

}
