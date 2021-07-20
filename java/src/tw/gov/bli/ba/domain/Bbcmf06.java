package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import tw.gov.bli.common.annotation.Table;

/**
 * 資料名稱參數檔 (<code>BBCMF06</code>)
 * 
 * @author Goston
 */
@Table("BBCMF06")
public class Bbcmf06 implements Serializable {
    private String insKd; // 保險別
    private String payTyp; // 給付種類
    private String pmTyp; // 參數類別
    private String dataKd; // 資料種類
    private String subDataKd; // 資料子種類
    private String dataCd; // 資料代碼
    private String dataNm; // 資料名稱
    private String data1; // 參數內容 1
    private String data2; // 參數內容 2
    private String data3; // 參數內容 3
    private String valid; // 使用狀態
    private Timestamp actDte; // 正式作業日期
    private String pmDesc; // 參數說明
    private String keyinMan; // 鍵入人員
    private Timestamp keyinDate; // 鍵入日期時間
    private String uppNo; // 異動人員
    private Timestamp upDte; // 異動日期

    public Bbcmf06() {

    }

    public String getInsKd() {
        return insKd;
    }

    public void setInsKd(String insKd) {
        this.insKd = insKd;
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

    public String getDataKd() {
        return dataKd;
    }

    public void setDataKd(String dataKd) {
        this.dataKd = dataKd;
    }

    public String getSubDataKd() {
        return subDataKd;
    }

    public void setSubDataKd(String subDataKd) {
        this.subDataKd = subDataKd;
    }

    public String getDataCd() {
        return dataCd;
    }

    public void setDataCd(String dataCd) {
        this.dataCd = dataCd;
    }

    public String getDataNm() {
        return dataNm;
    }

    public void setDataNm(String dataNm) {
        this.dataNm = dataNm;
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

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Timestamp getActDte() {
        return actDte;
    }

    public void setActDte(Timestamp actDte) {
        this.actDte = actDte;
    }

    public String getPmDesc() {
        return pmDesc;
    }

    public void setPmDesc(String pmDesc) {
        this.pmDesc = pmDesc;
    }

    public String getKeyinMan() {
        return keyinMan;
    }

    public void setKeyinMan(String keyinMan) {
        this.keyinMan = keyinMan;
    }

    public Timestamp getKeyinDate() {
        return keyinDate;
    }

    public void setKeyinDate(Timestamp keyinDate) {
        this.keyinDate = keyinDate;
    }

    public String getUppNo() {
        return uppNo;
    }

    public void setUppNo(String uppNo) {
        this.uppNo = uppNo;
    }

    public Timestamp getUpDte() {
        return upDte;
    }

    public void setUpDte(Timestamp upDte) {
        this.upDte = upDte;
    }

}
