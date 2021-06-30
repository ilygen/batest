package tw.gov.bli.ba.update.forms;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 遺屬年金受款人資料更正 (BAMO0D280C) 明細頁面
 * 
 * @author Azuritul
 */
public class SurvivorPayeeDataUpdateDetailForm extends BaseValidatorForm {

    private String method;
    private BigDecimal baappbaseId;// 資料列編號
    private String seqNo;
    private String benNationTyp; // 受益人國籍別
    private String benSex; // 受益人性別
    private String benNationCode; // 國籍代碼
    private String benNationCodeOption; // 國籍代碼
    private String benName; // 受益人姓名
    private String benBrDate; // 受益人出生日期
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
    private String benDieDate; // 遺屬死亡日期
    private String closeCause; // 結案原因
    private String closeDate; // 結案日期
    private String unqualifiedCause; // 不合格原因

    // BAAPPEXPAND欄位
    private String baappexpandId; // 延伸主檔ID
    private String raiseEvtMk; // 被保險人扶養
    private String handicapMk; // 領有重殘
    private String abanApplyMk; // 放棄請領
    private String abanApSym; // 放棄請領起始年月
    private String interdictMk; // 受禁治產(監護)宣告
    private String interdictDate; // 受禁治產(監護)宣告 - 宣告日期
    private String repealInterdictDate; // 受禁治產(監護)宣告 - 撤銷日期
    private String relatChgDate; // 親屬關係變動日期
    private String adoptDate; // 收養日期
    private String benMissingSdate; // 遺屬失蹤日期起
    private String benMissingEdate; // 遺屬失蹤日期迄
    private String prisonSdate; // 監管期間起
    private String prisonEdate; // 監管期間迄
    private String marryDate; // 結婚日期
    private String digaMyDate; // 再婚日期
    private String studMk; // 在學
    private String monIncomeMk; // 每月工作收入註記
    private String monIncome; // 每月工作收入
    private String raiseChildMk; // 配偶扶養
    private String savingMk; // 計息存儲
    private String assignName; // 代辦人姓名
    private String assignIdnNo; // 代辦人身分證號
    private String assignBrDate; // 代辦人生日
    private String ableApsYm; // 得請領起月
    private String compelMk;// 強迫不合格註記
    private String schoolCode; //學校代碼
    private String schoolCodeOption; //學校代碼 下單選單

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
    private String oldAplDpt; // 申請代算單位
    private String oldCompelMk;// 強迫不合格註記
    private String oldBenDieDate; // 遺屬死亡日期
    private String oldMarryDate; // 結婚日期
    private String oldDigaMyDate; // 再婚日期
    private String oldMonIncomeMk; // 每月工作收入註記
    private String oldMonIncome; // 每月工作收入
    private String oldHandicapMk; // 領有重殘
    private String oldAbanApplyMk; // 放棄請領
    private String oldAbanApSym; // 放棄請領起始年月
    private String oldBenMissingSdate; // 遺屬失蹤日期起
    private String oldBenMissingEdate; // 遺屬失蹤日期迄
    private String oldRelatChgDate; // 親屬關係變動日期
    private String oldPrisonSdate; // 監管期間起
    private String oldPrisonEdate; // 監管期間迄
    private String oldAssignName; // 代辦人姓名
    private String oldAssignIdnNo; // 代辦人身分證號
    private String oldAssignBrDate; // 代辦人生日
    private String oldCloseCause; // 結案原因
    private String oldCloseDate; // 結案日期
    private String oldInterdictMk; // 受禁治產(監護)宣告
    private String oldInterdictDate; // 受禁治產(監護)宣告 - 宣告日期
    
    private String oldAbleApsYm; // 得請領起月
    private String oldStudMk; // 在學
    private String oldRaiseChildMk; // 配偶扶養
    private String oldRepealInterdictDate; // 受禁治產(監護)宣告 - 撤銷日期
    private String oldAdoptDate; // 收養日期
    private String oldRaiseEvtMk; // 被保險人扶養
    private String oldSavingMk; // 計息存儲
    private String oldBenNationCode; // 國籍代碼
    private String oldSchoolCode; //學校代碼
    
    private String uname; // 申請代算單位名稱
    private String idnoExist;//檢核生日錯誤檔
    
    private String specialAcc;// 專戶
    private String oldSpecialAcc;// 專戶
    private String specialAccAfter;// 專戶 儲存用
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

    public String getBaappexpandId() {
        return baappexpandId;
    }

    public void setBaappexpandId(String baappexpandId) {
        this.baappexpandId = baappexpandId;
    }

    public String getRaiseEvtMk() {
        return raiseEvtMk;
    }

    public void setRaiseEvtMk(String raiseEvtMk) {
        this.raiseEvtMk = raiseEvtMk;
    }

    public String getHandicapMk() {
        return handicapMk;
    }

    public void setHandicapMk(String handicapMk) {
        this.handicapMk = handicapMk;
    }

    public String getAbanApplyMk() {
        return abanApplyMk;
    }

    public void setAbanApplyMk(String abanApplyMk) {
        this.abanApplyMk = abanApplyMk;
    }

    public String getAbanApSym() {
        return abanApSym;
    }

    public void setAbanApSym(String abanApSym) {
        this.abanApSym = abanApSym;
    }

    public String getInterdictMk() {
        return interdictMk;
    }

    public void setInterdictMk(String interdictMk) {
        this.interdictMk = interdictMk;
    }

    public String getInterdictDate() {
        return interdictDate;
    }

    public void setInterdictDate(String interdictDate) {
        this.interdictDate = interdictDate;
    }

    public String getRepealInterdictDate() {
        return repealInterdictDate;
    }

    public void setRepealInterdictDate(String repealInterdictDate) {
        this.repealInterdictDate = repealInterdictDate;
    }

    public String getRelatChgDate() {
        return relatChgDate;
    }

    public void setRelatChgDate(String relatChgDate) {
        this.relatChgDate = relatChgDate;
    }

    public String getAdoptDate() {
        return adoptDate;
    }

    public void setAdoptDate(String adoptDate) {
        this.adoptDate = adoptDate;
    }

    public String getBenMissingSdate() {
        return benMissingSdate;
    }

    public void setBenMissingSdate(String benMissingSdate) {
        this.benMissingSdate = benMissingSdate;
    }

    public String getBenMissingEdate() {
        return benMissingEdate;
    }

    public void setBenMissingEdate(String benMissingEdate) {
        this.benMissingEdate = benMissingEdate;
    }

    public String getPrisonSdate() {
        return prisonSdate;
    }

    public void setPrisonSdate(String prisonSdate) {
        this.prisonSdate = prisonSdate;
    }

    public String getPrisonEdate() {
        return prisonEdate;
    }

    public void setPrisonEdate(String prisonEdate) {
        this.prisonEdate = prisonEdate;
    }

    public String getMarryDate() {
        return marryDate;
    }

    public void setMarryDate(String marryDate) {
        this.marryDate = marryDate;
    }

    public String getDigaMyDate() {
        return digaMyDate;
    }

    public void setDigaMyDate(String digaMyDate) {
        this.digaMyDate = digaMyDate;
    }

    public String getStudMk() {
        return studMk;
    }

    public void setStudMk(String studMk) {
        this.studMk = studMk;
    }

    public String getMonIncomeMk() {
        return monIncomeMk;
    }

    public void setMonIncomeMk(String monIncomeMk) {
        this.monIncomeMk = monIncomeMk;
    }

    public String getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(String monIncome) {
        this.monIncome = monIncome;
    }

    public String getRaiseChildMk() {
        return raiseChildMk;
    }

    public void setRaiseChildMk(String raiseChildMk) {
        this.raiseChildMk = raiseChildMk;
    }

    public String getSavingMk() {
        return savingMk;
    }

    public void setSavingMk(String savingMk) {
        this.savingMk = savingMk;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    public String getAssignIdnNo() {
        return assignIdnNo;
    }

    public void setAssignIdnNo(String assignIdnNo) {
        this.assignIdnNo = assignIdnNo;
    }

    public String getAssignBrDate() {
        return assignBrDate;
    }

    public void setAssignBrDate(String assignBrDate) {
        this.assignBrDate = assignBrDate;
    }

    public String getCloseCause() {
        return closeCause;
    }

    public void setCloseCause(String closeCause) {
        this.closeCause = closeCause;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }
    
    public String getUnqualifiedCause() {
        return unqualifiedCause;
    }

    public void setUnqualifiedCause(String unqualifiedCause) {
        this.unqualifiedCause = unqualifiedCause;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        savingMk = "";
        handicapMk = "";
        abanApplyMk = "";
        monIncomeMk = "";
        studMk = "";
        raiseChildMk = "";
        raiseEvtMk = "";
        interdictMk = "";
        compelMk = "";
    }

    public String getAbleApsYm() {
        return ableApsYm;
    }

    public void setAbleApsYm(String ableApsYm) {
        this.ableApsYm = ableApsYm;
    }

    public String getCompelMk() {
        return compelMk;
    }

    public void setCompelMk(String compelMk) {
        this.compelMk = compelMk;
    }

    public String getOldCompelMk() {
        return oldCompelMk;
    }

    public void setOldCompelMk(String oldCompelMk) {
        this.oldCompelMk = oldCompelMk;
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

	public String getOldBenDieDate() {
		return oldBenDieDate;
	}

	public void setOldBenDieDate(String oldBenDieDate) {
		this.oldBenDieDate = oldBenDieDate;
	}

	public String getOldMarryDate() {
		return oldMarryDate;
	}

	public void setOldMarryDate(String oldMarryDate) {
		this.oldMarryDate = oldMarryDate;
	}

	public String getOldDigaMyDate() {
		return oldDigaMyDate;
	}

	public void setOldDigaMyDate(String oldDigaMyDate) {
		this.oldDigaMyDate = oldDigaMyDate;
	}

	public String getOldMonIncomeMk() {
		return oldMonIncomeMk;
	}

	public void setOldMonIncomeMk(String oldMonIncomeMk) {
		this.oldMonIncomeMk = oldMonIncomeMk;
	}

	public String getOldHandicapMk() {
		return oldHandicapMk;
	}

	public void setOldHandicapMk(String oldHandicapMk) {
		this.oldHandicapMk = oldHandicapMk;
	}

	public String getOldAbanApplyMk() {
		return oldAbanApplyMk;
	}

	public void setOldAbanApplyMk(String oldAbanApplyMk) {
		this.oldAbanApplyMk = oldAbanApplyMk;
	}

	public String getOldAbanApSym() {
		return oldAbanApSym;
	}

	public void setOldAbanApSym(String oldAbanApSym) {
		this.oldAbanApSym = oldAbanApSym;
	}

	public String getOldBenMissingSdate() {
		return oldBenMissingSdate;
	}

	public void setOldBenMissingSdate(String oldBenMissingSdate) {
		this.oldBenMissingSdate = oldBenMissingSdate;
	}

	public String getOldBenMissingEdate() {
		return oldBenMissingEdate;
	}

	public void setOldBenMissingEdate(String oldBenMissingEdate) {
		this.oldBenMissingEdate = oldBenMissingEdate;
	}

	public String getOldRelatChgDate() {
		return oldRelatChgDate;
	}

	public void setOldRelatChgDate(String oldRelatChgDate) {
		this.oldRelatChgDate = oldRelatChgDate;
	}

	public String getOldPrisonSdate() {
		return oldPrisonSdate;
	}

	public void setOldPrisonSdate(String oldPrisonSdate) {
		this.oldPrisonSdate = oldPrisonSdate;
	}

	public String getOldPrisonEdate() {
		return oldPrisonEdate;
	}

	public void setOldPrisonEdate(String oldPrisonEdate) {
		this.oldPrisonEdate = oldPrisonEdate;
	}

	public String getOldAssignName() {
		return oldAssignName;
	}

	public void setOldAssignName(String oldAssignName) {
		this.oldAssignName = oldAssignName;
	}

	public String getOldAssignIdnNo() {
		return oldAssignIdnNo;
	}

	public void setOldAssignIdnNo(String oldAssignIdnNo) {
		this.oldAssignIdnNo = oldAssignIdnNo;
	}

	public String getOldAssignBrDate() {
		return oldAssignBrDate;
	}

	public void setOldAssignBrDate(String oldAssignBrDate) {
		this.oldAssignBrDate = oldAssignBrDate;
	}

	public String getOldCloseCause() {
		return oldCloseCause;
	}

	public void setOldCloseCause(String oldCloseCause) {
		this.oldCloseCause = oldCloseCause;
	}

	public String getOldCloseDate() {
		return oldCloseDate;
	}

	public void setOldCloseDate(String oldCloseDate) {
		this.oldCloseDate = oldCloseDate;
	}

	public String getOldMonIncome() {
		return oldMonIncome;
	}

	public void setOldMonIncome(String oldMonIncome) {
		this.oldMonIncome = oldMonIncome;
	}

	public String getOldInterdictMk() {
		return oldInterdictMk;
	}

	public void setOldInterdictMk(String oldInterdictMk) {
		this.oldInterdictMk = oldInterdictMk;
	}

	public String getOldInterdictDate() {
		return oldInterdictDate;
	}

	public void setOldInterdictDate(String oldInterdictDate) {
		this.oldInterdictDate = oldInterdictDate;
	}

	public String getOldAbleApsYm() {
		return oldAbleApsYm;
	}

	public void setOldAbleApsYm(String oldAbleApsYm) {
		this.oldAbleApsYm = oldAbleApsYm;
	}

	public String getOldStudMk() {
		return oldStudMk;
	}

	public void setOldStudMk(String oldStudMk) {
		this.oldStudMk = oldStudMk;
	}

	public String getOldRaiseChildMk() {
		return oldRaiseChildMk;
	}

	public void setOldRaiseChildMk(String oldRaiseChildMk) {
		this.oldRaiseChildMk = oldRaiseChildMk;
	}

	public String getOldRepealInterdictDate() {
		return oldRepealInterdictDate;
	}

	public void setOldRepealInterdictDate(String oldRepealInterdictDate) {
		this.oldRepealInterdictDate = oldRepealInterdictDate;
	}

	public String getOldAdoptDate() {
		return oldAdoptDate;
	}

	public void setOldAdoptDate(String oldAdoptDate) {
		this.oldAdoptDate = oldAdoptDate;
	}

	public String getOldRaiseEvtMk() {
		return oldRaiseEvtMk;
	}

	public void setOldRaiseEvtMk(String oldRaiseEvtMk) {
		this.oldRaiseEvtMk = oldRaiseEvtMk;
	}

	public String getOldSavingMk() {
		return oldSavingMk;
	}

	public void setOldSavingMk(String oldSavingMk) {
		this.oldSavingMk = oldSavingMk;
	}

	public String getOldBenNationCode() {
		return oldBenNationCode;
	}

	public void setOldBenNationCode(String oldBenNationCode) {
		this.oldBenNationCode = oldBenNationCode;
	}

	public String getOldSpecialAccAfter() {
		return oldSpecialAccAfter;
	}

	public void setOldSpecialAccAfter(String oldSpecialAccAfter) {
		this.oldSpecialAccAfter = oldSpecialAccAfter;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getSchoolCodeOption() {
		return schoolCodeOption;
	}

	public void setSchoolCodeOption(String schoolCodeOption) {
		this.schoolCodeOption = schoolCodeOption;
	}

	public String getOldSchoolCode() {
		return oldSchoolCode;
	}

	public void setOldSchoolCode(String oldSchoolCode) {
		this.oldSchoolCode = oldSchoolCode;
	}

}
