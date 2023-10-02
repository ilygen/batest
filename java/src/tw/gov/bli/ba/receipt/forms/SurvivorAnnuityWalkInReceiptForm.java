package tw.gov.bli.ba.receipt.forms;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 受理作業 - 遺屬年金給付受理作業 (BAAP0D030A)
 * 
 * @author Rickychi
 */
public class SurvivorAnnuityWalkInReceiptForm extends BaseValidatorForm {
	private String method;

	private BigDecimal baappbaseId;// 資料列編號
	private BigDecimal baappexpandId; // 給付延伸主檔資料列編號
	private BigDecimal bafamilytempId;// 遺屬暫存擋資料列編號
	private String seqNo;// 序號
	private String apNo;// 受理編號
	private String apNo1;// 受理編號-1
	private String apNo2;// 受理編號-2
	private String apNo3;// 受理編號-3
	private String apNo4;// 受理編號-4
	private String apNo1ForBb;// BB 受理編號-1
	private String apNo2ForBb;// BB 受理編號-2
	private String apNo3ForBb;// BB 受理編號-3
	private String apNo4ForBb;// BB 受理編號-4
	private String procType;// 受理類別[1.普通遺屬年金、2.職災死亡一次金(轉入)、3.普通死亡一次金(轉入)、4.職災遺屬年金]
	private String evtNationCodeOption;// 事故者國籍下拉選單
	private String benNationCodeOption;// 遺屬國籍下拉選單
	private String accSeqNoOption;// 具領人下拉選單
	private String cvldtlName;// 戶籍姓名
	private BigDecimal evtBaappbaseId;// 事故者資料列編號
	private String dataIdx; // 修改模式資料序號
	private String sysCode;// 系統類別

	// 事故者資料
	private String apUbno;// 申請單位保險證號
	private String appDate;// 申請日期
	private String evtNationTpe;// 國籍別
	private String evtDieDate;// 死亡日期
	private String evtJobDate;// 事故日期
	private String evtSex;// 性別
	private String evtNationCode;// 國籍
	private String evtName;// 事故者姓名
	private String evtIdnNo;// 事故者身分證號
	private String evtBrDate;// 事故者出生日期
	private String evAppTyp;// 申請傷病分類
	private String evTyp;// 核定傷病分類
	private String apItem;// 申請項目
	private String apnoFm;// 來源受理編號
	private String monNotifyingMkCheck;// 寄送月通知表(checkbox)
	private String monNotifyingMk;// 寄送月通知表
	private String judgeDate;// 判決日期

	// 遺屬資料
	private String benNationTyp;// 國籍別
	private String benSex;// 性別
	private String benNationCode;// 國籍
	private String benName;// 遺屬姓名
	private String benAppDate;// 遺屬申請日期
	private String benIdnNo;// 遺屬身分證號
	private String benBrDate;// 遺屬者出生日期
	private String benEvtRel;// 關係
	private String tel1;// 電話1
	private String tel2;// 電話2
	private String commTyp;// 通訊地址別
	private String commZip;// 通訊郵遞區號
	private String commAddr;// 通訊地址
	private String grdName;// 法定代理人姓名
	private String grdIdnNo;// 法定代理人身分證號
	private String grdBrDate;// 法定代理人出生日期
	private String marryDate;// 結婚日期
	private String studMk;// 在學
	private String monIncomeMk;// 每月工作收入註記
	private BigDecimal monIncome;// 每月工作收入
	private String schoolCode;// 學校代碼
	private String schoolCodeOption; //學校代碼 下單選單
	private String benMarrMk;// 婚姻狀況
	private String adoPtDate;// 收養日期
	private String handIcapMk;// 領有重度以上身心障礙手冊或證明
	private String raiseEvtMkCheck;// 被保險人扶養(checkbox)
	private String raiseEvtMk;// 被保險人扶養
	private String interDictMk;// 受禁治產(監護)宣告
	private String assignName;// 代辦人姓名
	private String assignIdnNo;// 代辦人身分證號
	private String assignBrDate;// 代辦人出生日期
	private String accData;// 具名領取資料
	private String accName;// 戶名
	private String accRel;// 戶名與受益人關係
	private String accSeqNo;// 具名領取人
	private String payTyp;// 給付方式
	private String bankName;// 金融機構名稱
	private String payBankId;// 金融機構總代號
	private String branchId;// 分支代號
	private String payEeacc;// 銀行帳號
	private String payBankIdBranchId;// 帳號前7碼
	private String chkPayBankIdChkBranchId;// 帳號複驗(前)
	private String chkPayBankId;// 帳號複驗 金融機構總代號
	private String chkBranchId;// 帳號複驗 分支代號
	private String chkPayEeacc;// 帳號複驗(後)

	private String mobilePhone; // 手機複驗
	private String savingMkCheck;// 計息存儲(checkbox)
	private String savingMk;// 計息存儲
	private String defaultGrdData;// 法定代理人預設眷屬1

	private String payCategory;// 給付方式(本人領取 or 具名領取)
	private String specialAccCheck;// 專戶(checkbox)
	private String specialAcc;// 專戶
	private String actionTyp; // 案件新增/修改模式
	private String benOptionMode;// 遺屬資料新增/修改模式
	private String focusLocation;// foucs位置

	// Field for baapplog
	// [
	private String oldApNo;// 受理編號
	private String oldApNo1;// 受理編號-1
	private String oldApNo2;// 受理編號-2
	private String oldApNo3;// 受理編號-3
	private String oldApNo4;// 受理編號-4
	private String oldApUbno;// 申請單位保險證號
	private String oldAppDate;// 申請日期
	private String oldEvtNationTpe;// 國籍別
	private String oldEvtDieDate;// 死亡日期
	private String oldEvtSex;// 性別
	private String oldEvtNationCode;// 國籍
	private String oldEvtName;// 事故者姓名
	private String oldEvtIdnNo;// 事故者身分證號
	private String oldEvtBrDate;// 事故者出生日期
	private String oldEvAppTyp;// 申請傷病分類
	private String oldEvTyp;// 核定傷病分類
	private String oldApItem;// 申請項目
	private String oldBenNationTyp;// 國籍別
	private String oldBenSex;// 性別
	private String oldBenNationCode;// 國籍
	private String oldBenName;// 遺屬姓名
	private String oldBenAppDate;// 遺屬申請日期
	private String oldBenIdnNo;// 遺屬身分證號
	private String oldBenBrDate;// 遺屬者出生日期
	private String oldBenEvtRel;// 關係
	private String oldTel1;// 電話1
	private String oldTel2;// 電話2
	private String oldCommTyp;// 通訊地址別
	private String oldCommZip;// 通訊郵遞區號
	private String oldCommAddr;// 通訊地址
	private String oldGrdName;// 法定代理人姓名
	private String oldGrdIdnNo;// 法定代理人身分證號
	private String oldGrdBrDate;// 法定代理人出生日期
	private String oldMarryDate;// 結婚日期
	private String oldStudMk;// 在學
	private String oldMonIncomeMk;// 每月工作收入註記
	private BigDecimal oldMonIncome;// 每月工作收入
	private String oldHandIcapMk;// 領有重度以上身心障礙手冊或證明
	private String oldInterDictMk;// 受禁治產(監護)宣告
	private String oldAccData;// 具名領取資料
	private String oldAccName;// 戶名
	// private String oldAccRel;// 戶名與受益人關係
	private String oldAccSeqNo;// 具名領取人
	private String oldPayTyp;// 給付方式
	private String oldBankName;// 金融機構名稱
	private String oldPayBankId;// 金融機構總代號
	private String oldBranchId;// 分支代號
	private String oldPayEeacc;// 銀行帳號
	private String oldPayBankIdBranchId;// 帳號前7碼
	private String oldMobilePhone; // 手機複驗
	private String oldChkPayBankIdChkBranchId;// 帳號(前)
	private String oldChkPayEeacc;// 帳號(後)
	private String oldEvtJobDate;// 事故日期
	private String oldIinterDictMk;// 受禁治產(監護)宣告
	// ]

	// 新增/修改遺屬時暫存事故者資料用
	// [
	private String tempApNo1;// 受理編號-1
	private String tempApNo2;// 受理編號-2
	private String tempApNo3;// 受理編號-3
	private String tempApNo4;// 受理編號-4
	private String tempApUbno;// 申請單位保險證號
	private String tempAppDate;// 申請日期
	// 事故者資料
	private String tempEvtNationTpe;// 國籍別
	private String tempEvtDieDate;// 死亡日期
	private String tempEvtSex;// 性別
	private String tempEvtNationCode;// 國籍
	private String tempEvtNationCodeOption;// 事故者國籍下拉選單
	private String tempEvtName;// 事故者姓名
	private String tempEvtIdnNo;// 事故者身分證號
	private String tempEvtBrDate;// 事故者出生日期
	private String tempEvAppTyp;// 申請傷病分類
	private String tempEvTyp;// 核定傷病分類
	private String tempApItem;// 申請項目
	private String tempApnoFm;// 來源受理編號
	private String tempMonNotifyiingMk;// 寄送月通知表
	private String tempJudgeDate;// 判決日期

	// ]

	public String getPayCategory() {
		return payCategory;
	}

	public void setPayCategory(String payCategory) {
		this.payCategory = payCategory;
	}

	public String getSpecialAccCheck() {
		return specialAccCheck;
	}

	public void setSpecialAccCheck(String specialAccCheck) {
		this.specialAccCheck = specialAccCheck;
	}

	public String getSpecialAcc() {
		return specialAcc;
	}

	public void setSpecialAcc(String specialAcc) {
		this.specialAcc = specialAcc;
	}

	public String getApNoStr() {
		return getApNo1() + getApNo2() + getApNo3() + getApNo4();
	}

	public String getApNoStrDisplay() {
		return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-"
				+ getApNo().substring(7, 12);
	}
	
	public String getApNoStrForBb() {
		return getApNo1ForBb() + getApNo2ForBb() + getApNo3ForBb() + getApNo4ForBb();
	}
	
	public void cleanApnoForBc() {
		apNo1 = "";
		apNo2 = "";
		apNo3 = "";
		apNo4 = "";
	}

	public void cleanApnoForBb() {
		apNo1ForBb = "";
		apNo2ForBb = "";
		apNo3ForBb = "";
		apNo4ForBb = "";
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

	public String getApNo1ForBb() {
		return apNo1ForBb;
	}

	public void setApNo1ForBb(String apNo1ForBb) {
		this.apNo1ForBb = apNo1ForBb;
	}

	public String getApNo2ForBb() {
		return apNo2ForBb;
	}

	public void setApNo2ForBb(String apNo2ForBb) {
		this.apNo2ForBb = apNo2ForBb;
	}

	public String getApNo3ForBb() {
		return apNo3ForBb;
	}

	public void setApNo3ForBb(String apNo3ForBb) {
		this.apNo3ForBb = apNo3ForBb;
	}

	public String getApNo4ForBb() {
		return apNo4ForBb;
	}

	public void setApNo4ForBb(String apNo4ForBb) {
		this.apNo4ForBb = apNo4ForBb;
	}

	public String getProcType() {
		return procType;
	}

	public void setProcType(String procType) {
		this.procType = procType;
	}

	public String getEvtNationCodeOption() {
		return evtNationCodeOption;
	}

	public void setEvtNationCodeOption(String evtNationCodeOption) {
		this.evtNationCodeOption = evtNationCodeOption;
	}

	public String getApUbno() {
		return apUbno;
	}

	public void setApUbno(String apUbno) {
		this.apUbno = apUbno;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getEvtNationTpe() {
		return evtNationTpe;
	}

	public void setEvtNationTpe(String evtNationTpe) {
		this.evtNationTpe = evtNationTpe;
	}

	public String getEvtDieDate() {
		return evtDieDate;
	}

	public void setEvtDieDate(String evtDieDate) {
		this.evtDieDate = evtDieDate;
	}

	public String getEvtSex() {
		return evtSex;
	}

	public void setEvtSex(String evtSex) {
		this.evtSex = evtSex;
	}

	public String getEvtNationCode() {
		return evtNationCode;
	}

	public void setEvtNationCode(String evtNationCode) {
		this.evtNationCode = evtNationCode;
	}

	public String getEvtName() {
		return evtName;
	}

	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}

	public String getEvtIdnNo() {
		return evtIdnNo;
	}

	public void setEvtIdnNo(String evtIdnNo) {
		this.evtIdnNo = evtIdnNo;
	}

	public String getEvtBrDate() {
		return evtBrDate;
	}

	public void setEvtBrDate(String evtBrDate) {
		this.evtBrDate = evtBrDate;
	}

	public String getEvAppTyp() {
		return evAppTyp;
	}

	public void setEvAppTyp(String evAppTyp) {
		this.evAppTyp = evAppTyp;
	}

	public String getEvTyp() {
		return evTyp;
	}

	public void setEvTyp(String evTyp) {
		this.evTyp = evTyp;
	}

	public String getApItem() {
		return apItem;
	}

	public void setApItem(String apItem) {
		this.apItem = apItem;
	}

	public String getApnoFm() {
		return apnoFm;
	}

	public void setApnoFm(String apnoFm) {
		this.apnoFm = apnoFm;
	}

	public String getMonNotifyingMkCheck() {
		return monNotifyingMkCheck;
	}

	public void setMonNotifyingMkCheck(String monNotifyingMkCheck) {
		this.monNotifyingMkCheck = monNotifyingMkCheck;
	}

	public String getMonNotifyingMk() {
		return monNotifyingMk;
	}

	public void setMonNotifyingMk(String monNotifyingMk) {
		this.monNotifyingMk = monNotifyingMk;
	}

	public String getJudgeDate() {
		return judgeDate;
	}

	public void setJudgeDate(String judgeDate) {
		this.judgeDate = judgeDate;
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

	public String getBenNationCode() {
		return benNationCode;
	}

	public void setBenNationCode(String benNationCode) {
		this.benNationCode = benNationCode;
	}

	public String getBenName() {
		return benName;
	}

	public void setBenName(String benName) {
		this.benName = benName;
	}

	public String getBenIdnNo() {
		return benIdnNo;
	}

	public void setBenIdnNo(String benIdnNo) {
		this.benIdnNo = benIdnNo;
	}

	public String getBenBrDate() {
		return benBrDate;
	}

	public void setBenBrDate(String benBrDate) {
		this.benBrDate = benBrDate;
	}

	public String getBenEvtRel() {
		return benEvtRel;
	}

	public void setBenEvtRel(String benEvtRel) {
		this.benEvtRel = benEvtRel;
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

	public String getGrdName() {
		return grdName;
	}

	public void setGrdName(String grdName) {
		this.grdName = grdName;
	}

	public String getGrdIdnNo() {
		return grdIdnNo;
	}

	public void setGrdIdnNo(String grdIdnNo) {
		this.grdIdnNo = grdIdnNo;
	}

	public String getGrdBrDate() {
		return grdBrDate;
	}

	public void setGrdBrDate(String grdBrDate) {
		this.grdBrDate = grdBrDate;
	}

	public String getMarryDate() {
		return marryDate;
	}

	public void setMarryDate(String marryDate) {
		this.marryDate = marryDate;
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

	public BigDecimal getMonIncome() {
		return monIncome;
	}

	public void setMonIncome(BigDecimal monIncome) {
		this.monIncome = monIncome;
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

	public String getBenMarrMk() {
		return benMarrMk;
	}

	public void setBenMarrMk(String benMarrMk) {
		this.benMarrMk = benMarrMk;
	}

	public String getAdoPtDate() {
		return adoPtDate;
	}

	public void setAdoPtDate(String adoPtDate) {
		this.adoPtDate = adoPtDate;
	}

	public String getHandIcapMk() {
		return handIcapMk;
	}

	public void setHandIcapMk(String handIcapMk) {
		this.handIcapMk = handIcapMk;
	}

	public String getRaiseEvtMkCheck() {
		return raiseEvtMkCheck;
	}

	public void setRaiseEvtMkCheck(String raiseEvtMkCheck) {
		this.raiseEvtMkCheck = raiseEvtMkCheck;
	}

	public String getRaiseEvtMk() {
		return raiseEvtMk;
	}

	public void setRaiseEvtMk(String raiseEvtMk) {
		this.raiseEvtMk = raiseEvtMk;
	}

	public String getInterDictMk() {
		return interDictMk;
	}

	public void setInterDictMk(String interDictMk) {
		this.interDictMk = interDictMk;
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

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
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

	public String getBenAppDate() {
		return benAppDate;
	}

	public void setBenAppDate(String benAppDate) {
		this.benAppDate = benAppDate;
	}

	public String getBenNationCodeOption() {
		return benNationCodeOption;
	}

	public void setBenNationCodeOption(String benNationCodeOption) {
		this.benNationCodeOption = benNationCodeOption;
	}

	public String getAccSeqNo() {
		return accSeqNo;
	}

	public void setAccSeqNo(String accSeqNo) {
		this.accSeqNo = accSeqNo;
	}

	public String getAccSeqNoOption() {
		return accSeqNoOption;
	}

	public void setAccSeqNoOption(String accSeqNoOption) {
		this.accSeqNoOption = accSeqNoOption;
	}

	public String getEvtJobDate() {
		return evtJobDate;
	}

	public void setEvtJobDate(String evtJobDate) {
		this.evtJobDate = evtJobDate;
	}

	public String getCvldtlName() {
		return cvldtlName;
	}

	public void setCvldtlName(String cvldtlName) {
		this.cvldtlName = cvldtlName;
	}

	public String getAccData() {
		return accData;
	}

	public void setAccData(String accData) {
		this.accData = accData;
	}

	public BigDecimal getEvtBaappbaseId() {
		return evtBaappbaseId;
	}

	public void setEvtBaappbaseId(BigDecimal evtBaappbaseId) {
		this.evtBaappbaseId = evtBaappbaseId;
	}

	public String getPayBankIdBranchId() {
		if (StringUtils.isBlank(payBankId)) {
			payBankId = "";
		}
		if (StringUtils.isBlank(branchId)) {
			branchId = "";
		}
		payBankIdBranchId = payBankId + branchId;
		return payBankIdBranchId;
	}

	public void setPayBankIdBranchId(String payBankIdBranchId) {
		this.payBankIdBranchId = payBankIdBranchId;
	}

	public String getBenOptionMode() {
		return benOptionMode;
	}

	public void setBenOptionMode(String benOptionMode) {
		this.benOptionMode = benOptionMode;
	}

	public BigDecimal getBaappexpandId() {
		return baappexpandId;
	}

	public void setBaappexpandId(BigDecimal baappexpandId) {
		this.baappexpandId = baappexpandId;
	}

	public String getActionTyp() {
		return actionTyp;
	}

	public void setActionTyp(String actionTyp) {
		this.actionTyp = actionTyp;
	}

	public BigDecimal getBafamilytempId() {
		return bafamilytempId;
	}

	public void setBafamilytempId(BigDecimal bafamilytempId) {
		this.bafamilytempId = bafamilytempId;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getOldApNo() {
		return oldApNo;
	}

	public void setOldApNo(String oldApNo) {
		this.oldApNo = oldApNo;
	}

	public String getOldApNo1() {
		return oldApNo1;
	}

	public void setOldApNo1(String oldApNo1) {
		this.oldApNo1 = oldApNo1;
	}

	public String getOldApNo2() {
		return oldApNo2;
	}

	public void setOldApNo2(String oldApNo2) {
		this.oldApNo2 = oldApNo2;
	}

	public String getOldApNo3() {
		return oldApNo3;
	}

	public void setOldApNo3(String oldApNo3) {
		this.oldApNo3 = oldApNo3;
	}

	public String getOldApNo4() {
		return oldApNo4;
	}

	public void setOldApNo4(String oldApNo4) {
		this.oldApNo4 = oldApNo4;
	}

	public String getOldApUbno() {
		return oldApUbno;
	}

	public void setOldApUbno(String oldApUbno) {
		this.oldApUbno = oldApUbno;
	}

	public String getOldAppDate() {
		return oldAppDate;
	}

	public void setOldAppDate(String oldAppDate) {
		this.oldAppDate = oldAppDate;
	}

	public String getOldEvtNationTpe() {
		return oldEvtNationTpe;
	}

	public void setOldEvtNationTpe(String oldEvtNationTpe) {
		this.oldEvtNationTpe = oldEvtNationTpe;
	}

	public String getOldEvtDieDate() {
		return oldEvtDieDate;
	}

	public void setOldEvtDieDate(String oldEvtDieDate) {
		this.oldEvtDieDate = oldEvtDieDate;
	}

	public String getOldEvtSex() {
		return oldEvtSex;
	}

	public void setOldEvtSex(String oldEvtSex) {
		this.oldEvtSex = oldEvtSex;
	}

	public String getOldEvtNationCode() {
		return oldEvtNationCode;
	}

	public void setOldEvtNationCode(String oldEvtNationCode) {
		this.oldEvtNationCode = oldEvtNationCode;
	}

	public String getOldEvtName() {
		return oldEvtName;
	}

	public void setOldEvtName(String oldEvtName) {
		this.oldEvtName = oldEvtName;
	}

	public String getOldEvtIdnNo() {
		return oldEvtIdnNo;
	}

	public void setOldEvtIdnNo(String oldEvtIdnNo) {
		this.oldEvtIdnNo = oldEvtIdnNo;
	}

	public String getOldEvtBrDate() {
		return oldEvtBrDate;
	}

	public void setOldEvtBrDate(String oldEvtBrDate) {
		this.oldEvtBrDate = oldEvtBrDate;
	}

	public String getOldEvAppTyp() {
		return oldEvAppTyp;
	}

	public void setOldEvAppTyp(String oldEvAppTyp) {
		this.oldEvAppTyp = oldEvAppTyp;
	}

	public String getOldEvTyp() {
		return oldEvTyp;
	}

	public void setOldEvTyp(String oldEvTyp) {
		this.oldEvTyp = oldEvTyp;
	}

	public String getOldApItem() {
		return oldApItem;
	}

	public void setOldApItem(String oldApItem) {
		this.oldApItem = oldApItem;
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

	public String getOldBenNationCode() {
		return oldBenNationCode;
	}

	public void setOldBenNationCode(String oldBenNationCode) {
		this.oldBenNationCode = oldBenNationCode;
	}

	public String getOldBenName() {
		return oldBenName;
	}

	public void setOldBenName(String oldBenName) {
		this.oldBenName = oldBenName;
	}

	public String getOldBenAppDate() {
		return oldBenAppDate;
	}

	public void setOldBenAppDate(String oldBenAppDate) {
		this.oldBenAppDate = oldBenAppDate;
	}

	public String getOldBenIdnNo() {
		return oldBenIdnNo;
	}

	public void setOldBenIdnNo(String oldBenIdnNo) {
		this.oldBenIdnNo = oldBenIdnNo;
	}

	public String getOldBenBrDate() {
		return oldBenBrDate;
	}

	public void setOldBenBrDate(String oldBenBrDate) {
		this.oldBenBrDate = oldBenBrDate;
	}

	public String getOldBenEvtRel() {
		return oldBenEvtRel;
	}

	public void setOldBenEvtRel(String oldBenEvtRel) {
		this.oldBenEvtRel = oldBenEvtRel;
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

	public String getOldGrdName() {
		return oldGrdName;
	}

	public void setOldGrdName(String oldGrdName) {
		this.oldGrdName = oldGrdName;
	}

	public String getOldGrdIdnNo() {
		return oldGrdIdnNo;
	}

	public void setOldGrdIdnNo(String oldGrdIdnNo) {
		this.oldGrdIdnNo = oldGrdIdnNo;
	}

	public String getOldGrdBrDate() {
		return oldGrdBrDate;
	}

	public void setOldGrdBrDate(String oldGrdBrDate) {
		this.oldGrdBrDate = oldGrdBrDate;
	}

	public String getOldMarryDate() {
		return oldMarryDate;
	}

	public void setOldMarryDate(String oldMarryDate) {
		this.oldMarryDate = oldMarryDate;
	}

	public String getOldStudMk() {
		return oldStudMk;
	}

	public void setOldStudMk(String oldStudMk) {
		this.oldStudMk = oldStudMk;
	}

	public String getOldMonIncomeMk() {
		return oldMonIncomeMk;
	}

	public void setOldMonIncomeMk(String oldMonIncomeMk) {
		this.oldMonIncomeMk = oldMonIncomeMk;
	}

	public BigDecimal getOldMonIncome() {
		return oldMonIncome;
	}

	public void setOldMonIncome(BigDecimal oldMonIncome) {
		this.oldMonIncome = oldMonIncome;
	}

	public String getOldHandIcapMk() {
		return oldHandIcapMk;
	}

	public void setOldHandIcapMk(String oldHandIcapMk) {
		this.oldHandIcapMk = oldHandIcapMk;
	}

	public String getOldInterDictMk() {
		return oldInterDictMk;
	}

	public void setOldInterDictMk(String oldInterDictMk) {
		this.oldInterDictMk = oldInterDictMk;
	}

	public String getOldAccData() {
		return oldAccData;
	}

	public void setOldAccData(String oldAccData) {
		this.oldAccData = oldAccData;
	}

	public String getOldAccName() {
		return oldAccName;
	}

	public void setOldAccName(String oldAccName) {
		this.oldAccName = oldAccName;
	}

	public String getOldAccSeqNo() {
		return oldAccSeqNo;
	}

	public void setOldAccSeqNo(String oldAccSeqNo) {
		this.oldAccSeqNo = oldAccSeqNo;
	}

	public String getOldPayTyp() {
		return oldPayTyp;
	}

	public void setOldPayTyp(String oldPayTyp) {
		this.oldPayTyp = oldPayTyp;
	}

	public String getOldBankName() {
		return oldBankName;
	}

	public void setOldBankName(String oldBankName) {
		this.oldBankName = oldBankName;
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

	public String getOldPayBankIdBranchId() {
		return oldPayBankIdBranchId;
	}

	public void setOldPayBankIdBranchId(String oldPayBankIdBranchId) {
		this.oldPayBankIdBranchId = oldPayBankIdBranchId;
	}

	public String getTempApNo1() {
		return tempApNo1;
	}

	public void setTempApNo1(String tempApNo1) {
		this.tempApNo1 = tempApNo1;
	}

	public String getTempApNo2() {
		return tempApNo2;
	}

	public void setTempApNo2(String tempApNo2) {
		this.tempApNo2 = tempApNo2;
	}

	public String getTempApNo3() {
		return tempApNo3;
	}

	public void setTempApNo3(String tempApNo3) {
		this.tempApNo3 = tempApNo3;
	}

	public String getTempApNo4() {
		return tempApNo4;
	}

	public void setTempApNo4(String tempApNo4) {
		this.tempApNo4 = tempApNo4;
	}

	public String getTempApUbno() {
		return tempApUbno;
	}

	public void setTempApUbno(String tempApUbno) {
		this.tempApUbno = tempApUbno;
	}

	public String getTempAppDate() {
		return tempAppDate;
	}

	public void setTempAppDate(String tempAppDate) {
		this.tempAppDate = tempAppDate;
	}

	public String getTempEvtNationTpe() {
		return tempEvtNationTpe;
	}

	public void setTempEvtNationTpe(String tempEvtNationTpe) {
		this.tempEvtNationTpe = tempEvtNationTpe;
	}

	public String getTempEvtDieDate() {
		return tempEvtDieDate;
	}

	public void setTempEvtDieDate(String tempEvtDieDate) {
		this.tempEvtDieDate = tempEvtDieDate;
	}

	public String getTempEvtSex() {
		return tempEvtSex;
	}

	public void setTempEvtSex(String tempEvtSex) {
		this.tempEvtSex = tempEvtSex;
	}

	public String getTempEvtNationCode() {
		return tempEvtNationCode;
	}

	public void setTempEvtNationCode(String tempEvtNationCode) {
		this.tempEvtNationCode = tempEvtNationCode;
	}

	public String getTempEvtNationCodeOption() {
		return tempEvtNationCodeOption;
	}

	public void setTempEvtNationCodeOption(String tempEvtNationCodeOption) {
		this.tempEvtNationCodeOption = tempEvtNationCodeOption;
	}

	public String getTempEvtName() {
		return tempEvtName;
	}

	public void setTempEvtName(String tempEvtName) {
		this.tempEvtName = tempEvtName;
	}

	public String getTempEvtIdnNo() {
		return tempEvtIdnNo;
	}

	public void setTempEvtIdnNo(String tempEvtIdnNo) {
		this.tempEvtIdnNo = tempEvtIdnNo;
	}

	public String getTempEvtBrDate() {
		return tempEvtBrDate;
	}

	public void setTempEvtBrDate(String tempEvtBrDate) {
		this.tempEvtBrDate = tempEvtBrDate;
	}

	public String getTempEvAppTyp() {
		return tempEvAppTyp;
	}

	public void setTempEvAppTyp(String tempEvAppTyp) {
		this.tempEvAppTyp = tempEvAppTyp;
	}

	public String getTempEvTyp() {
		return tempEvTyp;
	}

	public void setTempEvTyp(String tempEvTyp) {
		this.tempEvTyp = tempEvTyp;
	}

	public String getTempApItem() {
		return tempApItem;
	}

	public void setTempApItem(String tempApItem) {
		this.tempApItem = tempApItem;
	}

	public String getDataIdx() {
		return dataIdx;
	}

	public void setDataIdx(String dataIdx) {
		this.dataIdx = dataIdx;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getFocusLocation() {
		return focusLocation;
	}

	public void setFocusLocation(String focusLocation) {
		this.focusLocation = focusLocation;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getSavingMkCheck() {
		return savingMkCheck;
	}

	public void setSavingMkCheck(String savingMkCheck) {
		this.savingMkCheck = savingMkCheck;
	}

	public String getSavingMk() {
		return savingMk;
	}

	public void setSavingMk(String savingMk) {
		this.savingMk = savingMk;
	}

	public String getDefaultGrdData() {
		return defaultGrdData;
	}

	public void setDefaultGrdData(String defaultGrdData) {
		this.defaultGrdData = defaultGrdData;
	}

	public String getOldMobilePhone() {
		return oldMobilePhone;
	}

	public void setOldMobilePhone(String oldMobilePhone) {
		this.oldMobilePhone = oldMobilePhone;
	}

	public String getChkPayBankIdChkBranchId() {
		if (StringUtils.isBlank(chkPayBankId)) {
			chkPayBankId = "";
		}
		if (StringUtils.isBlank(chkBranchId)) {
			chkBranchId = "";
		}
		chkPayBankIdChkBranchId = chkPayBankId + chkBranchId;

		return chkPayBankIdChkBranchId;
	}

	public void setChkPayBankIdChkBranchId(String chkPayBankIdChkBranchId) {
		this.chkPayBankIdChkBranchId = chkPayBankIdChkBranchId;
	}

	public String getChkPayEeacc() {
		return chkPayEeacc;
	}

	public void setChkPayEeacc(String chkPayEeacc) {
		this.chkPayEeacc = chkPayEeacc;
	}

	public String getChkPayBankId() {
		return chkPayBankId;
	}

	public void setChkPayBankId(String chkPayBankId) {
		this.chkPayBankId = chkPayBankId;
	}

	public String getChkBranchId() {
		return chkBranchId;
	}

	public void setChkBranchId(String chkBranchId) {
		this.chkBranchId = chkBranchId;
	}

	public String getOldChkPayBankIdChkBranchId() {
		return oldChkPayBankIdChkBranchId;
	}

	public void setOldChkPayBankIdChkBranchId(String oldChkPayBankIdChkBranchId) {
		this.oldChkPayBankIdChkBranchId = oldChkPayBankIdChkBranchId;
	}

	public String getOldChkPayEeacc() {
		return oldChkPayEeacc;
	}

	public void setOldChkPayEeacc(String oldChkPayEeacc) {
		this.oldChkPayEeacc = oldChkPayEeacc;
	}

	public String getOldEvtJobDate() {
		return oldEvtJobDate;
	}

	public void setOldEvtJobDate(String oldEvtJobDate) {
		this.oldEvtJobDate = oldEvtJobDate;
	}

	public String getOldIinterDictMk() {
		return oldIinterDictMk;
	}

	public void setOldIinterDictMk(String oldIinterDictMk) {
		this.oldIinterDictMk = oldIinterDictMk;
	}

	public String getTempApnoFm() {
		return tempApnoFm;
	}

	public void setTempApnoFm(String tempApnoFm) {
		this.tempApnoFm = tempApnoFm;
	}

	public String getTempMonNotifyiingMk() {
		return tempMonNotifyiingMk;
	}

	public void setTempMonNotifyiingMk(String tempMonNotifyiingMk) {
		this.tempMonNotifyiingMk = tempMonNotifyiingMk;
	}

	public String getTempJudgeDate() {
		return tempJudgeDate;
	}

	public void setTempJudgeDate(String tempJudgeDate) {
		this.tempJudgeDate = tempJudgeDate;
	}

}
