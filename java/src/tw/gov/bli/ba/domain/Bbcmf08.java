package tw.gov.bli.ba.domain;

import java.io.Serializable;

public class Bbcmf08 implements Serializable {
    private String inskd;
    private String payTyp;// 給付種類 11/13/14/21/31/33/41/46/51/52/53/61/99
    private String pmTyp; 
    private String gvCd1;
    private String gvName;
    private String gvsName;
    private String accSeq;
    private String gvCd2;
    private String pmDesc;
    private String status;
    private String keyInMan;
    private String keyInDate;
    private String upPno;
    private String upDte;
    public String getInskd() {
        return inskd;
    }
    public void setInskd(String inskd) {
        this.inskd = inskd;
    }
    public String getPayTyp() {
        return payTyp;
    }
    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }
    public String getPmTyp() {
        return pmTyp;
    }
    public void setPmTyp(String pmTyp) {
        this.pmTyp = pmTyp;
    }
    public String getGvCd1() {
        return gvCd1;
    }
    public void setGvCd1(String gvCd1) {
        this.gvCd1 = gvCd1;
    }
    public String getGvName() {
        return gvName;
    }
    public void setGvName(String gvName) {
        this.gvName = gvName;
    }
    public String getGvsName() {
        return gvsName;
    }
    public void setGvsName(String gvsName) {
        this.gvsName = gvsName;
    }
    public String getAccSeq() {
        return accSeq;
    }
    public void setAccSeq(String accSeq) {
        this.accSeq = accSeq;
    }
    public String getGvCd2() {
        return gvCd2;
    }
    public void setGvCd2(String gvCd2) {
        this.gvCd2 = gvCd2;
    }
    public String getPmDesc() {
        return pmDesc;
    }
    public void setPmDesc(String pmDesc) {
        this.pmDesc = pmDesc;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getKeyInMan() {
        return keyInMan;
    }
    public void setKeyInMan(String keyInMan) {
        this.keyInMan = keyInMan;
    }
    public String getKeyInDate() {
        return keyInDate;
    }
    public void setKeyInDate(String keyInDate) {
        this.keyInDate = keyInDate;
    }
    public String getUpPno() {
        return upPno;
    }
    public void setUpPno(String upPno) {
        this.upPno = upPno;
    }
    public String getUpDte() {
        return upDte;
    }
    public void setUpDte(String upDte) {
        this.upDte = upDte;
    }
}
