package tw.gov.bli.ba.update.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 給付資料更正 (BAMO0D020C)
 * 
 * @author Rickychi
 */
public class PaymentDataUpdateForm extends BaseValidatorForm {
    private String method;

    private BigDecimal baappbaseId;// 資料列編號
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    private String benIdnNo;// 受益人姓名
    private String benName;// 受益人姓名
    private String bankName;// 金融機構名稱
    private String payTyp;// 給付方式
    private String payBankIdBranchId;// 帳號(前)(bankId+branchId)
    private String payBankId;// 帳號(前)(bankId)
    private String branchId;// 帳號(前)(branchId)
    private String payEeacc;// 銀行帳號 (帳號(後))
    private String accRel;// 戶名與受益人關係
    // 20090114 給付更正拿掉這個欄位
    private String accName;// 戶名
    private String accData;// 被共同具領之受款人員資料

    private String pagePayKind;// 給付別(受理編號第一碼)
    private String benSelectResult;// 具名領取選單筆數(0:結果=0筆; 1:結果>=1筆)
    private String payAccount;// 海外帳號
    private String payCategory;// 給付方式(本人領取 or 具名領取)
    private String accSeqNoAmt;// 被具名領取筆數(0:結果=0筆; 1:結果>=1筆)
    private String interValMonth;// 發放方式

    private String oldPayBankIdBranchId;// 帳號(前)(bankId+branchId)
    private String oldPayEeacc;// 銀行帳號 (帳號(後))
    private String oldPayAccount;// 海外帳號
    private String oldInterValMonth;// 發放方式
    private String oldBenIdnNo;// 受益人姓名
    private String oldBenName;// 受益人姓名
    private String oldBankName;// 金融機構名稱
    private String oldPayTyp;// 給付方式
    private String oldAccRel;// 戶名與受益人關係
    private String oldAccName;// 戶名
    private String oldAccData;// 被共同具領之受款人員資料
    private String oldBenSelectResult;// 具名領取選單筆數(0:結果=0筆; 1:結果>=1筆)
    private String oldPayCategory;// 給付方式(本人領取 or 具名領取)
    private String oldAccSeqNoAmt;// 被具名領取筆數(0:結果=0筆; 1:結果>=1筆)
    
    private String specialAcc;// 專戶
    private String oldSpecialAcc;// 專戶
    
    private String specialAccAfter;// 專戶 儲存用

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public String getApNoStr() {
        return getApNo1() + getApNo2() + getApNo3() + getApNo4();
    }

    public String getApNoStrDisplay() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getApNo1() {
        return apNo1;
    }

    public void setApNo1(String apNo1) {
        this.apNo1 = apNo1;
    }

    public String getApNo2() {
        return apNo2;
    }

    public void setApNo2(String apNo2) {
        this.apNo2 = apNo2;
    }

    public String getApNo3() {
        return apNo3;
    }

    public void setApNo3(String apNo3) {
        this.apNo3 = apNo3;
    }

    public String getApNo4() {
        return apNo4;
    }

    public void setApNo4(String apNo4) {
        this.apNo4 = apNo4;
    }

    public String getBenSelectResult() {
        return benSelectResult;
    }

    public void setBenSelectResult(String benSelectResult) {
        this.benSelectResult = benSelectResult;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPayBankIdBranchId() {
    	if(StringUtils.isBlank(payBankId)){
    		payBankId = "";
    	}
    	if(StringUtils.isBlank(branchId)){
    		branchId = "";
    	}
    	payBankIdBranchId = payBankId + branchId;
        return payBankIdBranchId;
    }

    public void setPayBankIdBranchId(String payBankIdBranchId) {
        this.payBankIdBranchId = payBankIdBranchId;
    }

    public String getPagePayKind() {
        return pagePayKind;
    }

    public void setPagePayKind(String pagePayKind) {
        this.pagePayKind = pagePayKind;
    }

    public String getPayEeacc() {
        return payEeacc;
    }

    public void setPayEeacc(String payEeacc) {
        this.payEeacc = payEeacc;
    }

    public String getAccRel() {
        return accRel;
    }

    public void setAccRel(String accRel) {
        this.accRel = accRel;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(String payCategory) {
        this.payCategory = payCategory;
    }

    public String getOldPayBankIdBranchId() {
        return oldPayBankIdBranchId;
    }

    public void setOldPayBankIdBranchId(String oldPayBankIdBranchId) {
        this.oldPayBankIdBranchId = oldPayBankIdBranchId;
    }

    public String getOldPayEeacc() {
        return oldPayEeacc;
    }

    public void setOldPayEeacc(String oldPayEeacc) {
        this.oldPayEeacc = oldPayEeacc;
    }

    public String getOldPayAccount() {
        return oldPayAccount;
    }

    public void setOldPayAccount(String oldPayAccount) {
        this.oldPayAccount = oldPayAccount;
    }

    public String getAccData() {
        return accData;
    }

    public void setAccData(String accData) {
        this.accData = accData;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccSeqNoAmt() {
        return accSeqNoAmt;
    }

    public void setAccSeqNoAmt(String accSeqNoAmt) {
        this.accSeqNoAmt = accSeqNoAmt;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getInterValMonth() {
        return interValMonth;
    }

    public void setInterValMonth(String interValMonth) {
        this.interValMonth = interValMonth;
    }

    public String getOldInterValMonth() {
        return oldInterValMonth;
    }

    public void setOldInterValMonth(String oldInterValMonth) {
        this.oldInterValMonth = oldInterValMonth;
    }

	public String getSpecialAcc() {
		return specialAcc;
	}

	public void setSpecialAcc(String specialAcc) {
		this.specialAcc = specialAcc;
	}

	public String getSpecialAccAfter() {
		return specialAccAfter;
	}

	public void setSpecialAccAfter(String specialAccAfter) {
		this.specialAccAfter = specialAccAfter;
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

	public String getOldBenIdnNo() {
		return oldBenIdnNo;
	}

	public void setOldBenIdnNo(String oldBenIdnNo) {
		this.oldBenIdnNo = oldBenIdnNo;
	}

	public String getOldBenName() {
		return oldBenName;
	}

	public void setOldBenName(String oldBenName) {
		this.oldBenName = oldBenName;
	}

	public String getOldBankName() {
		return oldBankName;
	}

	public void setOldBankName(String oldBankName) {
		this.oldBankName = oldBankName;
	}

	public String getOldPayTyp() {
		return oldPayTyp;
	}

	public void setOldPayTyp(String oldPayTyp) {
		this.oldPayTyp = oldPayTyp;
	}

	public String getOldAccRel() {
		return oldAccRel;
	}

	public void setOldAccRel(String oldAccRel) {
		this.oldAccRel = oldAccRel;
	}

	public String getOldAccName() {
		return oldAccName;
	}

	public void setOldAccName(String oldAccName) {
		this.oldAccName = oldAccName;
	}

	public String getOldAccData() {
		return oldAccData;
	}

	public void setOldAccData(String oldAccData) {
		this.oldAccData = oldAccData;
	}

	public String getOldBenSelectResult() {
		return oldBenSelectResult;
	}

	public void setOldBenSelectResult(String oldBenSelectResult) {
		this.oldBenSelectResult = oldBenSelectResult;
	}

	public String getOldPayCategory() {
		return oldPayCategory;
	}

	public void setOldPayCategory(String oldPayCategory) {
		this.oldPayCategory = oldPayCategory;
	}

	public String getOldAccSeqNoAmt() {
		return oldAccSeqNoAmt;
	}

	public void setOldAccSeqNoAmt(String oldAccSeqNoAmt) {
		this.oldAccSeqNoAmt = oldAccSeqNoAmt;
	}

	public String getOldSpecialAcc() {
		return oldSpecialAcc;
	}

	public void setOldSpecialAcc(String oldSpecialAcc) {
		this.oldSpecialAcc = oldSpecialAcc;
	}

}
