package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.PkeyField;

/**
 * 眷屬遺屬重編欄位更正記錄檔
 * 
 * @author Rickychi
 */
public class Bacolumnrec implements Serializable {
    @PkeyField("BACOLUMNID")
    private BigDecimal bacolumnId;// 資料列編號
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String benEvtRel;// 受益人與事故者關係
    private String columnName;// 欄位名稱
    private String columnValue;// 欄位值
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private String procMk;// 處理註記
    private String procTime;// 處理日期時間

    public BigDecimal getBacolumnId() {
        return bacolumnId;
    }

    public void setBacolumnId(BigDecimal bacolumnId) {
        this.bacolumnId = bacolumnId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getProcMk() {
        return procMk;
    }

    public void setProcMk(String procMk) {
        this.procMk = procMk;
    }

    public String getProcTime() {
        return procTime;
    }

    public void setProcTime(String procTime) {
        this.procTime = procTime;
    }
}
