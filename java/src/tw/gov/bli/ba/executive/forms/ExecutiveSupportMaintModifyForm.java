package tw.gov.bli.ba.executive.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 行政支援作業 - 行政支援記錄修改 - 修改業面 (basu0d0513c.jsp)
 * 
 * @author jerry
 */
public class ExecutiveSupportMaintModifyForm extends BaseValidatorForm {
    private String letterType; // 處理函別
    private String proDate;// 承辦日期
    private String ndomk1;// 處理註記一
    private String ndomk2;// 處理註記二
    private String note;// 摘 由
    private String closDate;// 銷案日期
    private String delMk;// 管制註記
    private String promoteUser;// 承辦人員
    private String reliefTyp; // 給付性質
    private String reliefKind; // 救濟種類
    private String reliefStat; // 行政救濟辦理情形
    private String slrpNo1; // 來受文號
    private String slrpNo2;
    private String slrpNo3;
    private String slrpNo4;
    private String slrpNo5;
    private String slDate; // 來受文日期
    private String unNo; // 來受文機關代號
    private String unDes; // 來受文機關名稱
    private String caseTyp; // 分類號
    private String sddMk; // 承辦註記
    private String finishDate; // 承辦完成日
    private String slrelate1; // 相關文號
    private String slrelate2;
    private String slrelate3;
    private String slrelate4;
    private String slrelate5;
    private String closeNo1; // 銷案文號
    private String closeNo2;
    private String closeNo3;
    private String closeNo4;
    private String closeNo5;
    private String hpNo; // 醫院代號
    private String injser; // 爭議部位
    private String unitNo; // 單位保險證號
    private String payMk; //付費註記

    public String getLetterType() {
        return letterType;
    }

    public void setLetterType(String letterType) {
        this.letterType = letterType;
    }

    public String getProDate() {
        return proDate;
    }

    public void setProDate(String proDate) {
        this.proDate = proDate;
    }

    public String getClosDate() {
        return closDate;
    }

    public void setClosDate(String closDate) {
        this.closDate = closDate;
    }

    public String getNdomk1() {
        return ndomk1;
    }

    public void setNdomk1(String ndomk1) {
        this.ndomk1 = ndomk1;
    }

    public String getNdomk2() {
        return ndomk2;
    }

    public void setNdomk2(String ndomk2) {
        this.ndomk2 = ndomk2;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDelMk() {
        return delMk;
    }

    public void setDelMk(String delMk) {
        this.delMk = delMk;
    }

    public String getPromoteUser() {
        return promoteUser;
    }

    public void setPromoteUser(String promoteUser) {
        this.promoteUser = promoteUser;
    }

    public String getReliefTyp() {
        return reliefTyp;
    }

    public void setReliefTyp(String reliefTyp) {
        this.reliefTyp = reliefTyp;
    }

    public String getReliefKind() {
        return reliefKind;
    }

    public void setReliefKind(String reliefKind) {
        this.reliefKind = reliefKind;
    }

    public String getReliefStat() {
        return reliefStat;
    }

    public void setReliefStat(String reliefStat) {
        this.reliefStat = reliefStat;
    }

    public String getSlrpNo1() {
        return slrpNo1;
    }

    public void setSlrpNo1(String slrpNo1) {
        this.slrpNo1 = slrpNo1;
    }

    public String getSlrpNo2() {
        return slrpNo2;
    }

    public void setSlrpNo2(String slrpNo2) {
        this.slrpNo2 = slrpNo2;
    }

    public String getSlrpNo3() {
        return slrpNo3;
    }

    public void setSlrpNo3(String slrpNo3) {
        this.slrpNo3 = slrpNo3;
    }

    public String getSlrpNo4() {
        return slrpNo4;
    }

    public void setSlrpNo4(String slrpNo4) {
        this.slrpNo4 = slrpNo4;
    }

    public String getSlrpNo5() {
        return slrpNo5;
    }

    public void setSlrpNo5(String slrpNo5) {
        this.slrpNo5 = slrpNo5;
    }

    public String getSlDate() {
        return slDate;
    }

    public void setSlDate(String slDate) {
        this.slDate = slDate;
    }

    public String getUnNo() {
        return unNo;
    }

    public void setUnNo(String unNo) {
        this.unNo = unNo;
    }

    public String getUnDes() {
        return unDes;
    }

    public void setUnDes(String unDes) {
        this.unDes = unDes;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getSddMk() {
        return sddMk;
    }

    public void setSddMk(String sddMk) {
        this.sddMk = sddMk;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getSlrelate1() {
        return slrelate1;
    }

    public void setSlrelate1(String slrelate1) {
        this.slrelate1 = slrelate1;
    }

    public String getSlrelate2() {
        return slrelate2;
    }

    public void setSlrelate2(String slrelate2) {
        this.slrelate2 = slrelate2;
    }

    public String getSlrelate3() {
        return slrelate3;
    }

    public void setSlrelate3(String slrelate3) {
        this.slrelate3 = slrelate3;
    }

    public String getSlrelate4() {
        return slrelate4;
    }

    public void setSlrelate4(String slrelate4) {
        this.slrelate4 = slrelate4;
    }

    public String getSlrelate5() {
        return slrelate5;
    }

    public void setSlrelate5(String slrelate5) {
        this.slrelate5 = slrelate5;
    }

    public String getCloseNo1() {
        return closeNo1;
    }

    public void setCloseNo1(String closeNo1) {
        this.closeNo1 = closeNo1;
    }

    public String getCloseNo2() {
        return closeNo2;
    }

    public void setCloseNo2(String closeNo2) {
        this.closeNo2 = closeNo2;
    }

    public String getCloseNo3() {
        return closeNo3;
    }

    public void setCloseNo3(String closeNo3) {
        this.closeNo3 = closeNo3;
    }

    public String getCloseNo4() {
        return closeNo4;
    }

    public void setCloseNo4(String closeNo4) {
        this.closeNo4 = closeNo4;
    }

    public String getCloseNo5() {
        return closeNo5;
    }

    public void setCloseNo5(String closeNo5) {
        this.closeNo5 = closeNo5;
    }

    public String getHpNo() {
        return hpNo;
    }

    public void setHpNo(String hpNo) {
        this.hpNo = hpNo;
    }

    public String getInjser() {
        return injser;
    }

    public void setInjser(String injser) {
        this.injser = injser;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getPayMk() {
        return payMk;
    }

    public void setPayMk(String payMk) {
        this.payMk = payMk;
    }
}
