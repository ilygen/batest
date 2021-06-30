package tw.gov.bli.ba.maint.cases;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * Case for 核定通知書維護作業
 * 
 * @author swim
 */
public class AdviceMaintCase implements Serializable {
    private String payCode; // 給付別
    private String authTyp; // 核定格式
    private String authTypDesc; // 核定格式
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
    
    private String dataContPurpose; // 資料內容 主旨
    private String dataContExplain; // 資料內容 說明
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
    public String getDataContPurpose() {
        return dataContPurpose;
    }
    public void setDataContPurpose(String dataContPurpose) {
        this.dataContPurpose = dataContPurpose;
    }
    public String getDataContExplain() {
        return dataContExplain;
    }
    public void setDataContExplain(String dataContExplain) {
        this.dataContExplain = dataContExplain;
    }
    public String getDataSeqNo() {
        return dataSeqNo;
    }
    public void setDataSeqNo(String dataSeqNo) {
        this.dataSeqNo = dataSeqNo;
    }
    public String getDataContPurposeSubstr() {
        if(StringUtils.length(dataContPurpose) > 20)
            return dataContPurpose.substring(0,20);
        else
            return dataContPurpose;
    }
    public String getDataContExplainSubstr() {
        if(StringUtils.length(dataContExplain) > 20)
            return dataContExplain.substring(0,20);
        else
            return dataContExplain;
    }
	public String getAuthTypDesc() {
		return authTypDesc;
	}
	public void setAuthTypDesc(String authTypDesc) {
		this.authTypDesc = authTypDesc;
	}


}
