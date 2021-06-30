package tw.gov.bli.ba.update.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * 更正作業 - 失能年金受款人資料更正  明細頁面
 *
 * @author Azuritul
 */
public class DisabledPayeeDataUpdateDetailForm extends BaseValidatorForm {

    private String method;
    private BigDecimal baappbaseId;// 資料列編號
    private String seqNo;
    private String evtDieDate; //事故者死亡日期
    private String benNationTyp; // 受益人國籍別
    private String benSex; // 受益人性別
    private String benNationCode; // 國籍代碼
    private String benNationCodeOption; // 國籍代碼
    private String benName; // 受益人姓名
    private String benBrDate; // 受益人出生日期
    private String benDieDate;//受益人死亡日期
    private String benIdnNo; // 受益人身分證號
    private String mustIssueAmt; // 可領金額
    private String benEvtRel; // 受益人與事故者關係
    private String appDate; // 申請日期
    private String appUser; // 繼承自受款人
    private String payTyp; // 給付方式
    private String accSeqNo;// 給付方式 具名領取 被共同具領之受款人員序號
    private String payBankId; // 金融機構總代號
    private String branchId; // 分支代號
    private String payEeacc; // 銀行帳號
    private String accRel; // 戶名與受益人關係
    private String bankName; // 金融機構名稱
    private String accName; // 戶名
    private String idnChkYm; // 身分查核年月
    private String idnChkNote; // 身分查核註記
    private String tel1; // 電話1
    private String tel2; // 電話2
    private String commTyp; // 通訊地址別
    private String commZip; // 通訊郵遞區號
    private String commAddr; // 通訊地址
    private String grdIdnNo; // 法定代理人身分證號
    private String grdName; // 法定代理人姓名
    private String grdBrDate; // 法定代理人出生日期
    private String payBankIdBranchId;// 帳號(前)(bankId+branchId)
    private String payAccount;// 海外帳號
    private String payCategory;// 給付方式(本人領取 or 具名領取)
    private String idnChkYear; // 查核年
    private String idnChkMonth; // 查核月
    private String cutAmt; // 扣減/補償總金額
    private String benMarrMk; // 婚姻狀況
    // 因畫面上有兩個欄位都是寫進cutAmt
    private String mustIssueAmt1; // 可領金額 投保單位墊付金額
    private String mustIssueAmt2; // 可領金額 實際補償金額
    private String uname; //申請代算單位名稱
    private String closeCause; //結案原因
    private String idnoExist;//核定錯誤檔
    
    private String specialAcc;// 專戶
    private String oldSpecialAcc;// 專戶
    private String specialAccAfter;// 專戶 儲存用

    private String oldBenNationTyp; // 受益人國籍別
    private String oldBenSex; // 受益人性別
    private String oldCountryId; // 國籍
    private String oldBenName; // 受益人姓名
    private String oldBenBrDate; // 受益人出生日期
    private String oldBenIdnNo; // 受益人身分證號
    private String oldMustIssueAmt; // 可領金額
    private String oldBenEvtRel; // 受益人與事故者關係
    private String oldAppDate; // 申請日期
    private String oldPayTyp; // 給付方式
    private String oldAccSeqNo; // 給付方式2
    private String oldPayBankId; // 金融機構總代號
    private String oldBranchId; // 分支代號
    private String oldPayEeacc; // 銀行帳號
    private String oldAccRel; // 戶名與受益人關係
    private String oldBankName; // 金融機構名稱
    private String oldAccName; // 戶名
    private String oldIdnChkYm; // 身分查核年月
    private String oldIdnChkNote; // 身分查核註記
    private String oldTel1; // 電話1
    private String oldTel2; // 電話2
    private String oldCommTyp; // 通訊地址別
    private String oldCommZip; // 通訊郵遞區號
    private String oldCommAddr; // 通訊地址
    private String oldGrdIdnNo; // 法定代理人身分證號
    private String oldGrdName; // 法定代理人姓名
    private String oldGrdBrDate; // 法定代理人出生日期
    private String oldPayBankIdBranchId;// 帳號(前)(bankId+branchId)
    private String oldPayAccount;// 海外帳號
    private String oldCutAmt; // 扣減/補償總金額
    private String oldBenMarrMk; // 婚姻狀況
    private String oldAplDpt; //申請代算單位
    private String oldEvtDieDate; //事故者死亡日期
    private String oldBenNationCode; // 國籍代碼
    private String oldBenNationCodeOption; // 國籍代碼
    private String oldBenDieDate;//受益人死亡日期
    private String oldAppUser; // 繼承自受款人
    private String oldPayCategory;// 給付方式(本人領取 or 具名領取)
    private String oldIdnChkYear; // 查核年
    private String oldIdnChkMonth; // 查核月
    // 因畫面上有兩個欄位都是寫進cutAmt
    private String oldMustIssueAmt1; // 可領金額 投保單位墊付金額
    private String oldMustIssueAmt2; // 可領金額 實際補償金額
    private String oldUname; //申請代算單位名稱
    private String oldCloseCause; //結案原因
    private String oldIdnoExist;//核定錯誤檔
    private String oldSpecialAccAfter;// 專戶 儲存用
 
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    	
    	if (page == 1 || StringUtils.equals(method, "doSave")) {
            ActionErrors errors = super.validate(mapping, request);

            // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
            if (errors != null && errors.size() > 0)
                return errors;

            return errors;
        }
        else {
            return null;
        }
    	
    }
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		benMarrMk = "";
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBenNationTyp() {
        return benNationTyp;
    }

    public void setBenNationTyp(String benNationTyp) {
        this.benNationTyp = benNationTyp;
    }

    public String getBenSex() {
        return benSex;
    }

    public void setBenSex(String benSex) {
        this.benSex = benSex;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenBrDate() {
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getMustIssueAmt() {
        return mustIssueAmt;
    }

    public void setMustIssueAmt(String mustIssueAmt) {
        this.mustIssueAmt = mustIssueAmt;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
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

    public String getAccRel() {
        return accRel;
    }

    public void setAccRel(String accRel) {
        this.accRel = accRel;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getIdnChkYm() {
        return idnChkYm;
    }

    public void setIdnChkYm(String idnChkYm) {
        this.idnChkYm = idnChkYm;
    }

    public String getIdnChkNote() {
        return idnChkNote;
    }

    public void setIdnChkNote(String idnChkNote) {
        this.idnChkNote = idnChkNote;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getCommTyp() {
        return commTyp;
    }

    public void setCommTyp(String commTyp) {
        this.commTyp = commTyp;
    }

    public String getCommZip() {
        return commZip;
    }

    public void setCommZip(String commZip) {
        this.commZip = commZip;
    }

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    public String getGrdIdnNo() {
        return grdIdnNo;
    }

    public void setGrdIdnNo(String grdIdnNo) {
        this.grdIdnNo = grdIdnNo;
    }

    public String getGrdName() {
        return grdName;
    }

    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    public String getGrdBrDate() {
        return grdBrDate;
    }

    public void setGrdBrDate(String grdBrDate) {
        this.grdBrDate = grdBrDate;
    }

    public String getOldBenNationTyp() {
        return oldBenNationTyp;
    }

    public void setOldBenNationTyp(String oldBenNationTyp) {
        this.oldBenNationTyp = oldBenNationTyp;
    }

    public String getOldBenSex() {
        return oldBenSex;
    }

    public void setOldBenSex(String oldBenSex) {
        this.oldBenSex = oldBenSex;
    }

    public String getOldCountryId() {
        return oldCountryId;
    }

    public void setOldCountryId(String oldCountryId) {
        this.oldCountryId = oldCountryId;
    }

    public String getOldBenName() {
        return oldBenName;
    }

    public void setOldBenName(String oldBenName) {
        this.oldBenName = oldBenName;
    }

    public String getOldBenBrDate() {
        return oldBenBrDate;
    }

    public void setOldBenBrDate(String oldBenBrDate) {
        this.oldBenBrDate = oldBenBrDate;
    }

    public String getOldBenIdnNo() {
        return oldBenIdnNo;
    }

    public void setOldBenIdnNo(String oldBenIdnNo) {
        this.oldBenIdnNo = oldBenIdnNo;
    }

    public String getOldMustIssueAmt() {
        return oldMustIssueAmt;
    }

    public void setOldMustIssueAmt(String oldMustIssueAmt) {
        this.oldMustIssueAmt = oldMustIssueAmt;
    }

    public String getOldBenEvtRel() {
        return oldBenEvtRel;
    }

    public void setOldBenEvtRel(String oldBenEvtRel) {
        this.oldBenEvtRel = oldBenEvtRel;
    }

    public String getOldAppDate() {
        return oldAppDate;
    }

    public void setOldAppDate(String oldAppDate) {
        this.oldAppDate = oldAppDate;
    }

    public String getOldPayTyp() {
        return oldPayTyp;
    }

    public void setOldPayTyp(String oldPayTyp) {
        this.oldPayTyp = oldPayTyp;
    }

    public String getOldPayBankId() {
        return oldPayBankId;
    }

    public void setOldPayBankId(String oldPayBankId) {
        this.oldPayBankId = oldPayBankId;
    }

    public String getOldBranchId() {
        return oldBranchId;
    }

    public void setOldBranchId(String oldBranchId) {
        this.oldBranchId = oldBranchId;
    }

    public String getOldPayEeacc() {
        return oldPayEeacc;
    }

    public void setOldPayEeacc(String oldPayEeacc) {
        this.oldPayEeacc = oldPayEeacc;
    }

    public String getOldAccRel() {
        return oldAccRel;
    }

    public void setOldAccRel(String oldAccRel) {
        this.oldAccRel = oldAccRel;
    }

    public String getOldBankName() {
        return oldBankName;
    }

    public void setOldBankName(String oldBankName) {
        this.oldBankName = oldBankName;
    }

    public String getOldAccName() {
        return oldAccName;
    }

    public void setOldAccName(String oldAccName) {
        this.oldAccName = oldAccName;
    }

    public String getOldIdnChkYm() {
        return oldIdnChkYm;
    }

    public void setOldIdnChkYm(String oldIdnChkYm) {
        this.oldIdnChkYm = oldIdnChkYm;
    }

    public String getOldIdnChkNote() {
        return oldIdnChkNote;
    }

    public void setOldIdnChkNote(String oldIdnChkNote) {
        this.oldIdnChkNote = oldIdnChkNote;
    }

    public String getOldTel1() {
        return oldTel1;
    }

    public void setOldTel1(String oldTel1) {
        this.oldTel1 = oldTel1;
    }

    public String getOldTel2() {
        return oldTel2;
    }

    public void setOldTel2(String oldTel2) {
        this.oldTel2 = oldTel2;
    }

    public String getOldCommTyp() {
        return oldCommTyp;
    }

    public void setOldCommTyp(String oldCommTyp) {
        this.oldCommTyp = oldCommTyp;
    }

    public String getOldCommZip() {
        return oldCommZip;
    }

    public void setOldCommZip(String oldCommZip) {
        this.oldCommZip = oldCommZip;
    }

    public String getOldCommAddr() {
        return oldCommAddr;
    }

    public void setOldCommAddr(String oldCommAddr) {
        this.oldCommAddr = oldCommAddr;
    }

    public String getOldGrdIdnNo() {
        return oldGrdIdnNo;
    }

    public void setOldGrdIdnNo(String oldGrdIdnNo) {
        this.oldGrdIdnNo = oldGrdIdnNo;
    }

    public String getOldGrdName() {
        return oldGrdName;
    }

    public void setOldGrdName(String oldGrdName) {
        this.oldGrdName = oldGrdName;
    }

    public String getOldGrdBrDate() {
        return oldGrdBrDate;
    }

    public void setOldGrdBrDate(String oldGrdBrDate) {
        this.oldGrdBrDate = oldGrdBrDate;
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

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getOldPayBankIdBranchId() {
        return oldPayBankIdBranchId;
    }

    public void setOldPayBankIdBranchId(String oldPayBankIdBranchId) {
        this.oldPayBankIdBranchId = oldPayBankIdBranchId;
    }

    public String getOldPayAccount() {
        return oldPayAccount;
    }

    public void setOldPayAccount(String oldPayAccount) {
        this.oldPayAccount = oldPayAccount;
    }

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

    public String getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(String payCategory) {
        this.payCategory = payCategory;
    }

    public String getBenNationCode() {
        return benNationCode;
    }

    public void setBenNationCode(String benNationCode) {
        this.benNationCode = benNationCode;
    }

    public String getBenNationCodeOption() {
        return benNationCodeOption;
    }

    public void setBenNationCodeOption(String benNationCodeOption) {
        this.benNationCodeOption = benNationCodeOption;
    }

    public String getIdnChkYear() {
        return idnChkYear;
    }

    public void setIdnChkYear(String idnChkYear) {
        this.idnChkYear = idnChkYear;
    }

    public String getIdnChkMonth() {
        return idnChkMonth;
    }

    public void setIdnChkMonth(String idnChkMonth) {
        this.idnChkMonth = idnChkMonth;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getMustIssueAmt1() {
        return mustIssueAmt1;
    }

    public void setMustIssueAmt1(String mustIssueAmt1) {
        this.mustIssueAmt1 = mustIssueAmt1;
    }

    public String getMustIssueAmt2() {
        return mustIssueAmt2;
    }

    public void setMustIssueAmt2(String mustIssueAmt2) {
        this.mustIssueAmt2 = mustIssueAmt2;
    }

    public String getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(String cutAmt) {
        this.cutAmt = cutAmt;
    }

    public String getOldCutAmt() {
        return oldCutAmt;
    }

    public void setOldCutAmt(String oldCutAmt) {
        this.oldCutAmt = oldCutAmt;
    }

    public String getAccSeqNo() {
        return accSeqNo;
    }

    public void setAccSeqNo(String accSeqNo) {
        this.accSeqNo = accSeqNo;
    }

    public String getOldAccSeqNo() {
        return oldAccSeqNo;
    }

    public void setOldAccSeqNo(String oldAccSeqNo) {
        this.oldAccSeqNo = oldAccSeqNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getBenMarrMk() {
        return benMarrMk;
    }

    public void setBenMarrMk(String benMarrMk) {
        this.benMarrMk = benMarrMk;
    }

    public String getOldBenMarrMk() {
        return oldBenMarrMk;
    }

    public void setOldBenMarrMk(String oldBenMarrMk) {
        this.oldBenMarrMk = oldBenMarrMk;
    }

    public String getOldAplDpt() {
        return oldAplDpt;
    }

    public void setOldAplDpt(String oldAplDpt) {
        this.oldAplDpt = oldAplDpt;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getBenDieDate() {
        return benDieDate;
    }

    public void setBenDieDate(String benDieDate) {
        this.benDieDate = benDieDate;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getCloseCause() {
		return closeCause;
	}

	public void setCloseCause(String closeCause) {
		this.closeCause = closeCause;
	}

	public String getIdnoExist() {
		return idnoExist;
	}

	public void setIdnoExist(String idnoExist) {
		this.idnoExist = idnoExist;
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

	public String getOldSpecialAcc() {
		return oldSpecialAcc;
	}

	public void setOldSpecialAcc(String oldSpecialAcc) {
		this.oldSpecialAcc = oldSpecialAcc;
	}

	public String getOldEvtDieDate() {
		return oldEvtDieDate;
	}

	public void setOldEvtDieDate(String oldEvtDieDate) {
		this.oldEvtDieDate = oldEvtDieDate;
	}

	public String getOldBenNationCode() {
		return oldBenNationCode;
	}

	public void setOldBenNationCode(String oldBenNationCode) {
		this.oldBenNationCode = oldBenNationCode;
	}

	public String getOldBenNationCodeOption() {
		return oldBenNationCodeOption;
	}

	public void setOldBenNationCodeOption(String oldBenNationCodeOption) {
		this.oldBenNationCodeOption = oldBenNationCodeOption;
	}

	public String getOldBenDieDate() {
		return oldBenDieDate;
	}

	public void setOldBenDieDate(String oldBenDieDate) {
		this.oldBenDieDate = oldBenDieDate;
	}

	public String getOldAppUser() {
		return oldAppUser;
	}

	public void setOldAppUser(String oldAppUser) {
		this.oldAppUser = oldAppUser;
	}

	public String getOldPayCategory() {
		return oldPayCategory;
	}

	public void setOldPayCategory(String oldPayCategory) {
		this.oldPayCategory = oldPayCategory;
	}

	public String getOldIdnChkYear() {
		return oldIdnChkYear;
	}

	public void setOldIdnChkYear(String oldIdnChkYear) {
		this.oldIdnChkYear = oldIdnChkYear;
	}

	public String getOldIdnChkMonth() {
		return oldIdnChkMonth;
	}

	public void setOldIdnChkMonth(String oldIdnChkMonth) {
		this.oldIdnChkMonth = oldIdnChkMonth;
	}

	public String getOldMustIssueAmt1() {
		return oldMustIssueAmt1;
	}

	public void setOldMustIssueAmt1(String oldMustIssueAmt1) {
		this.oldMustIssueAmt1 = oldMustIssueAmt1;
	}

	public String getOldMustIssueAmt2() {
		return oldMustIssueAmt2;
	}

	public void setOldMustIssueAmt2(String oldMustIssueAmt2) {
		this.oldMustIssueAmt2 = oldMustIssueAmt2;
	}

	public String getOldUname() {
		return oldUname;
	}

	public void setOldUname(String oldUname) {
		this.oldUname = oldUname;
	}

	public String getOldCloseCause() {
		return oldCloseCause;
	}

	public void setOldCloseCause(String oldCloseCause) {
		this.oldCloseCause = oldCloseCause;
	}

	public String getOldIdnoExist() {
		return oldIdnoExist;
	}

	public void setOldIdnoExist(String oldIdnoExist) {
		this.oldIdnoExist = oldIdnoExist;
	}

	public String getOldSpecialAccAfter() {
		return oldSpecialAccAfter;
	}

	public void setOldSpecialAccAfter(String oldSpecialAccAfter) {
		this.oldSpecialAccAfter = oldSpecialAccAfter;
	}
	
}
