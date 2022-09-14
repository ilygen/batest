package tw.gov.bli.ba.domain;

import java.io.Serializable;

import tw.gov.bli.common.annotation.Table;

@Table("BAAP0D040")
public class Baap0d040 implements Serializable {

	private String apno;// 受理編號
	private String apUbno;// 申請單位
	private String appDate;// 申請日期
	private String evtNationTpe;// 事故者國籍別：1-本國、2-外籍
	private String evtSex;// 性別
	private String evtNationCode;// 事故者國籍
	private String evtName;// 事故者姓名
	private String evtIdnNo;// 事故者身分證號
	private String evtBrDate;// 事故者出生日期
	private String tel1;// 電話1
	private String tel2;// 電話2
	private String commTyp;// 地址別：1-同戶籍地、2-現住址
	private String commZip;// 郵遞區號
	private String commAddr;// 地址
	private String mobilePhone;// 手機複驗
	private String grdName;// 法定代理人姓名
	private String grdIdnNo;// 法定代理人身分證號
	private String grdBrDate;// 法定代理人出生日期
	private String evAppTyp;// 申請傷病分類
	private String evTyp;// 核定傷病分類
	private String evtJobDate;// 診斷失能日期
	private String injDte;// 傷害發生日期
	private String fvisitDte;// 初診日期
	private String lsurgeryDte;// 最後手術日期
	private String lsradiationDte;// 最後放射(化學)治療日期
	private String criInJdp1;// 失能項目1
	private String criInJdp2;// 失能項目2
	private String criInJdp3;// 失能項目3
	private String criInJdp4;// 失能項目4
	private String criInJdp5;// 失能項目5
	private String criInJdp6;// 失能項目6
	private String criInJdp7;// 失能項目7
	private String criInJdp8;// 失能項目8
	private String criInJdp9;// 失能項目9
	private String criInJdp10;// 失能項目10
	private String hosId;// 醫療院所代碼
	private String doctorName1;// 醫師姓名1
	private String doctorName2;// 醫師姓名2
	private String criInJnme1;// 國際疾病代碼1
	private String criInJnme2;// 國際疾病代碼2
	private String criInJnme3;// 國際疾病代碼3
	private String criInJnme4;// 國際疾病代碼4
	private String payTyp;// 給付方式
	private String payBankId;// 金融機構總代號
	private String branchId;// 分支代號
	private String payEeacc;// 銀行帳號
	private String apnoFm;// 來源受理編號

	public String getApno() {
		return apno;
	}

	public void setApno(String apno) {
		this.apno = apno;
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

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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

	public String getEvtJobDate() {
		return evtJobDate;
	}

	public void setEvtJobDate(String evtJobDate) {
		this.evtJobDate = evtJobDate;
	}

	public String getInjDte() {
		return injDte;
	}

	public void setInjDte(String injDte) {
		this.injDte = injDte;
	}

	public String getFvisitDte() {
		return fvisitDte;
	}

	public void setFvisitDte(String fvisitDte) {
		this.fvisitDte = fvisitDte;
	}

	public String getLsurgeryDte() {
		return lsurgeryDte;
	}

	public void setLsurgeryDte(String lsurgeryDte) {
		this.lsurgeryDte = lsurgeryDte;
	}

	public String getLsradiationDte() {
		return lsradiationDte;
	}

	public void setLsradiationDte(String lsradiationDte) {
		this.lsradiationDte = lsradiationDte;
	}

	public String getCriInJdp1() {
		return criInJdp1;
	}

	public void setCriInJdp1(String criInJdp1) {
		this.criInJdp1 = criInJdp1;
	}

	public String getCriInJdp2() {
		return criInJdp2;
	}

	public void setCriInJdp2(String criInJdp2) {
		this.criInJdp2 = criInJdp2;
	}

	public String getCriInJdp3() {
		return criInJdp3;
	}

	public void setCriInJdp3(String criInJdp3) {
		this.criInJdp3 = criInJdp3;
	}

	public String getCriInJdp4() {
		return criInJdp4;
	}

	public void setCriInJdp4(String criInJdp4) {
		this.criInJdp4 = criInJdp4;
	}

	public String getCriInJdp5() {
		return criInJdp5;
	}

	public void setCriInJdp5(String criInJdp5) {
		this.criInJdp5 = criInJdp5;
	}

	public String getCriInJdp6() {
		return criInJdp6;
	}

	public void setCriInJdp6(String criInJdp6) {
		this.criInJdp6 = criInJdp6;
	}

	public String getCriInJdp7() {
		return criInJdp7;
	}

	public void setCriInJdp7(String criInJdp7) {
		this.criInJdp7 = criInJdp7;
	}

	public String getCriInJdp8() {
		return criInJdp8;
	}

	public void setCriInJdp8(String criInJdp8) {
		this.criInJdp8 = criInJdp8;
	}

	public String getCriInJdp9() {
		return criInJdp9;
	}

	public void setCriInJdp9(String criInJdp9) {
		this.criInJdp9 = criInJdp9;
	}

	public String getCriInJdp10() {
		return criInJdp10;
	}

	public void setCriInJdp10(String criInJdp10) {
		this.criInJdp10 = criInJdp10;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getDoctorName1() {
		return doctorName1;
	}

	public void setDoctorName1(String doctorName1) {
		this.doctorName1 = doctorName1;
	}

	public String getDoctorName2() {
		return doctorName2;
	}

	public void setDoctorName2(String doctorName2) {
		this.doctorName2 = doctorName2;
	}

	public String getCriInJnme1() {
		return criInJnme1;
	}

	public void setCriInJnme1(String criInJnme1) {
		this.criInJnme1 = criInJnme1;
	}

	public String getCriInJnme2() {
		return criInJnme2;
	}

	public void setCriInJnme2(String criInJnme2) {
		this.criInJnme2 = criInJnme2;
	}

	public String getCriInJnme3() {
		return criInJnme3;
	}

	public void setCriInJnme3(String criInJnme3) {
		this.criInJnme3 = criInJnme3;
	}

	public String getCriInJnme4() {
		return criInJnme4;
	}

	public void setCriInJnme4(String criInJnme4) {
		this.criInJnme4 = criInJnme4;
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

	public String getApnoFm() {
		return apnoFm;
	}

	public void setApnoFm(String apnoFm) {
		this.apnoFm = apnoFm;
	}

}
