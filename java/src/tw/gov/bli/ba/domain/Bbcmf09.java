package tw.gov.bli.ba.domain;

import java.io.Serializable;

/**
 * 現金給付分案尾碼原則
 * 
 * @author Rickychi
 */
public class Bbcmf09 implements Serializable {
    private String pmTyp;// 參數類別
    private String prpNo;// 員工編號
    private String payTyp;// 給付種類
    private String groupMk;// 分組歸類
    private String seqNo;// 序號
    private String data1;// 參數內容1
    private String data2;// 參數內容2 承辦人分機
    private String tal11;// 參數內容3 尾1碼之1
    private String tal12;// 尾1碼之2
    private String tal13;// 尾1碼之3
    private String tal14;// 尾1碼之4
    private String tal15;// 尾1碼之5
    private String tal16;// 尾1碼之往前看
    private String taloe;// 尾3碼單雙區分
    private String tal2x;// 尾3碼之後2碼
    private String nlwkMk;// 普職區分
    private String tal20;// 尾2碼起訖
    private String tal21;// 尾2碼斷號1
    private String tal22;// 尾2碼斷號2
    private String tal23;// 尾2碼斷號3
    private String tal24;// 尾2碼斷號4
    private String tal25;// 尾2碼斷號5
    private String tal31;// 尾3碼斷號1
    private String tal32;// 尾3碼斷號2
    private String fmDte;// 使用起日
    private String toDte;// 使用訖日
    private String fName; //承辦人姓氏
    private String sex; //承辦人性別（1：男；2：女）
    
    public String getPmTyp() {
        return pmTyp;
    }

    public void setPmTyp(String pmTyp) {
        this.pmTyp = pmTyp;
    }

    public String getPrpNo() {
        return prpNo;
    }

    public void setPrpNo(String prpNo) {
        this.prpNo = prpNo;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getGroupMk() {
        return groupMk;
    }

    public void setGroupMk(String groupMk) {
        this.groupMk = groupMk;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getTal11() {
        return tal11;
    }

    public void setTal11(String tal11) {
        this.tal11 = tal11;
    }

    public String getTal12() {
        return tal12;
    }

    public void setTal12(String tal12) {
        this.tal12 = tal12;
    }

    public String getTal13() {
        return tal13;
    }

    public void setTal13(String tal13) {
        this.tal13 = tal13;
    }

    public String getTal14() {
        return tal14;
    }

    public void setTal14(String tal14) {
        this.tal14 = tal14;
    }

    public String getTal15() {
        return tal15;
    }

    public void setTal15(String tal15) {
        this.tal15 = tal15;
    }

    public String getTal16() {
        return tal16;
    }

    public void setTal16(String tal16) {
        this.tal16 = tal16;
    }

    public String getTaloe() {
        return taloe;
    }

    public void setTaloe(String taloe) {
        this.taloe = taloe;
    }

    public String getTal2x() {
        return tal2x;
    }

    public void setTal2x(String tal2x) {
        this.tal2x = tal2x;
    }

    public String getNlwkMk() {
        return nlwkMk;
    }

    public void setNlwkMk(String nlwkMk) {
        this.nlwkMk = nlwkMk;
    }

    public String getTal20() {
        return tal20;
    }

    public void setTal20(String tal20) {
        this.tal20 = tal20;
    }

    public String getTal21() {
        return tal21;
    }

    public void setTal21(String tal21) {
        this.tal21 = tal21;
    }

    public String getTal22() {
        return tal22;
    }

    public void setTal22(String tal22) {
        this.tal22 = tal22;
    }

    public String getTal23() {
        return tal23;
    }

    public void setTal23(String tal23) {
        this.tal23 = tal23;
    }

    public String getTal24() {
        return tal24;
    }

    public void setTal24(String tal24) {
        this.tal24 = tal24;
    }

    public String getTal25() {
        return tal25;
    }

    public void setTal25(String tal25) {
        this.tal25 = tal25;
    }

    public String getTal31() {
        return tal31;
    }

    public void setTal31(String tal31) {
        this.tal31 = tal31;
    }

    public String getTal32() {
        return tal32;
    }

    public void setTal32(String tal32) {
        this.tal32 = tal32;
    }

    public String getFmDte() {
        return fmDte;
    }

    public void setFmDte(String fmDte) {
        this.fmDte = fmDte;
    }

    public String getToDte() {
        return toDte;
    }

    public void setToDte(String toDte) {
        this.toDte = toDte;
    }

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
    
}
