package tw.gov.bli.ba.domain;

import java.io.Serializable;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 核定通知書格式檔 (<code>BANOTIFY</code>)
 * 
 * @author swim
 */
@Table("BANOTIFY")
public class Banotify implements Serializable {
    @PkeyField("PAYCODE")
    private String payCode; // 給付別
    private String authTyp; // 核定格式
    private String authTypDesc; // 格式說明
    private String dataTyp; // 資料區分
    private String dataSeqNo; // 資料序號
    private String dataCont; // 資料內容 
    private String payCase; // 適用案件
    private String payMk; // 是否給付
    private String actMk; // 是否有效
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String updUser; // 異動者代號
    private String updTime; // 異動日期時間
    
    public String getPayCode() {
        return payCode;
    }
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    public String getAuthTyp() {
        return authTyp;
    }
    public void setAuthTyp(String authTyp) {
        this.authTyp = authTyp;
    }
    public String getDataTyp() {
        return dataTyp;
    }
    public void setDataTyp(String dataTyp) {
        this.dataTyp = dataTyp;
    }
    public String getDataCont() {
        return dataCont;
    }
    public void setDataCont(String dataCont) {
        this.dataCont = dataCont;
    }
    public String getPayCase() {
        return payCase;
    }
    public void setPayCase(String payCase) {
        this.payCase = payCase;
    }
    public String getPayMk() {
        return payMk;
    }
    public void setPayMk(String payMk) {
        this.payMk = payMk;
    }
    public String getActMk() {
        return actMk;
    }
    public void setActMk(String actMk) {
        this.actMk = actMk;
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
    public String getUpdUser() {
        return updUser;
    }
    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }
    public String getUpdTime() {
        return updTime;
    }
    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }
    public String getDataSeqNo() {
        return dataSeqNo;
    }
    public void setDataSeqNo(String dataSeqNo) {
        this.dataSeqNo = dataSeqNo;
    }
	public String getAuthTypDesc() {
		return authTypDesc;
	}
	public void setAuthTypDesc(String authTypDesc) {
		this.authTypDesc = authTypDesc;
	}
    
}
