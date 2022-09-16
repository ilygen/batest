package tw.gov.bli.ba.domain;

import java.io.Serializable;
import tw.gov.bli.common.annotation.Table;

@Table("BAAP0D060")
public class Baap0d060 implements Serializable {

	private String apno;// 受理編號
	private String evtNationTpe;// 事故者國籍別
	private String evtNationCode;// 事故者國籍
	private String evtSex;// 事故者性別
	private String evtIdnNo;// 事故者身分證號
	private String evtName;// 事故者姓名
	private String evtBrDate;// 事故者出生日期
	private String evtDieDate;// 事故者死亡日期
	private String judgeDate;// 判決日期
	private String appDate;// 申請日期
	private String apUbno;// 申請單位保險證號
	private String apItem;// 申請項目
	private String evAppTyp;// 申請傷病分類
	private String evTyp;// 核定傷病分類
	private String monNotifyingMk;// 寄發月通知表
	private String apnoFm;// 來源受理編號

	public String getApno() {
		return apno;
	}

	public void setApno(String apno) {
		this.apno = apno;
	}

	public String getEvtNationTpe() {
		return evtNationTpe;
	}

	public void setEvtNationTpe(String evtNationTpe) {
		this.evtNationTpe = evtNationTpe;
	}

	public String getEvtNationCode() {
		return evtNationCode;
	}

	public void setEvtNationCode(String evtNationCode) {
		this.evtNationCode = evtNationCode;
	}

	public String getEvtSex() {
		return evtSex;
	}

	public void setEvtSex(String evtSex) {
		this.evtSex = evtSex;
	}

	public String getEvtIdnNo() {
		return evtIdnNo;
	}

	public void setEvtIdnNo(String evtIdnNo) {
		this.evtIdnNo = evtIdnNo;
	}

	public String getEvtName() {
		return evtName;
	}

	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}

	public String getEvtBrDate() {
		return evtBrDate;
	}

	public void setEvtBrDate(String evtBrDate) {
		this.evtBrDate = evtBrDate;
	}

	public String getEvtDieDate() {
		return evtDieDate;
	}

	public void setEvtDieDate(String evtDieDate) {
		this.evtDieDate = evtDieDate;
	}

	public String getJudgeDate() {
		return judgeDate;
	}

	public void setJudgeDate(String judgeDate) {
		this.judgeDate = judgeDate;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getApUbno() {
		return apUbno;
	}

	public void setApUbno(String apUbno) {
		this.apUbno = apUbno;
	}

	public String getApItem() {
		return apItem;
	}

	public void setApItem(String apItem) {
		this.apItem = apItem;
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

	public String getMonNotifyingMk() {
		return monNotifyingMk;
	}

	public void setMonNotifyingMk(String monNotifyingMk) {
		this.monNotifyingMk = monNotifyingMk;
	}

	public String getApnoFm() {
		return apnoFm;
	}

	public void setApnoFm(String apnoFm) {
		this.apnoFm = apnoFm;
	}

}
