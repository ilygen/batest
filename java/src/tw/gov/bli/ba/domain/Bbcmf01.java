package tw.gov.bli.ba.domain;

import java.io.Serializable;

/**
 * 現金給付參數檔(操作權限)
 * 
 * @author Rickychi
 */
public class Bbcmf01 implements Serializable {
    private String pmTyp;// 參數類別 '01'=權限設定
    private String prpNo;// 員工編號
    private String payTyp;// 給付種類 11/13/14/21/31/33/41/46/51/52/53/61/99
    private String delMk;// 註銷權限 'Y':具有註銷權限
    private String vwdel;// 查詢註銷權限 'Y':具有查詢註銷權限
    private String pack;// 打包權限 'Y':具有打包權限
    private String upDpm;// 更正參數檔權限 'Y'具有更正參數檔權限
    private String upRb;// 修改補發收回 'Y':具有修改補發收回基 本資料權限
    private String upThru;// 穿透權 'Y':具有穿透權限
    private String insKd;// 保險別
    private String signl1;// 複核權
    private String signl2;// 決行層級 1=承辦人員，2=複核，3=科長，4=一等專員，5=副經理，6=經理
    private String signl3;// 代決行層級
    private String signl4;// 決行後小改
    private String signl5;// 整檔權限
    private String signl6;// 清稿後決行
    private String upPno;// 異動人員
    private String upDte;// 異動日期
    private String deptId;// 部室代號

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

    public String getDelMk() {
        return delMk;
    }

    public void setDelMk(String delMk) {
        this.delMk = delMk;
    }

    public String getVwdel() {
        return vwdel;
    }

    public void setVwdel(String vwdel) {
        this.vwdel = vwdel;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getUpDpm() {
        return upDpm;
    }

    public void setUpDpm(String upDpm) {
        this.upDpm = upDpm;
    }

    public String getUpRb() {
        return upRb;
    }

    public void setUpRb(String upRb) {
        this.upRb = upRb;
    }

    public String getUpThru() {
        return upThru;
    }

    public void setUpThru(String upThru) {
        this.upThru = upThru;
    }

    public String getInsKd() {
        return insKd;
    }

    public void setInsKd(String insKd) {
        this.insKd = insKd;
    }

    public String getSignl1() {
        return signl1;
    }

    public void setSignl1(String signl1) {
        this.signl1 = signl1;
    }

    public String getSignl2() {
        return signl2;
    }

    public void setSignl2(String signl2) {
        this.signl2 = signl2;
    }

    public String getSignl3() {
        return signl3;
    }

    public void setSignl3(String signl3) {
        this.signl3 = signl3;
    }

    public String getSignl4() {
        return signl4;
    }

    public void setSignl4(String signl4) {
        this.signl4 = signl4;
    }

    public String getSignl5() {
        return signl5;
    }

    public void setSignl5(String signl5) {
        this.signl5 = signl5;
    }

    public String getSignl6() {
        return signl6;
    }

    public void setSignl6(String signl6) {
        this.signl6 = signl6;
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

}
