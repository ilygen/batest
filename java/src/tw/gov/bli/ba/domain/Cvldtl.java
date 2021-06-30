package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import tw.gov.bli.common.annotation.Table;

/**
 * 戶政全戶檔
 * 
 * @author Rickychi
 */

@Table("CVLDTL")
public class Cvldtl implements Serializable{
    private String npIds;// 社福識別碼
    private String apArea;// 受理鄉鎮代號
    private String idn;// 身分證號
    private String ebDate;// 出生日期
    private String name;// 姓名
    private String harea;// 戶政地區代號
    private String hzip;// 戶籍郵遞區號
    private String haddr;// 戶籍地址
    private String caddr;// 健保通訊地址
    private String transMk;// 健保轉帳代繳註記
    private String specCode;// 特殊記事代碼
    private String specDate;// 特殊記事日期
    private String olivCode;// 原住名身分代碼
    private String inDate;// 遷入日期
    private String neb;// 鄰
    private String nebc;// 鄰
    private BigDecimal acnt;// 行政區字數
    private BigDecimal vcnt;// 村里字數
    private BigDecimal ncnt;// 鄰字數
    private String msg01;// 無法轉BIG5之中文字
    private Date updDt;// 更新日期
    private String czip;// 健保局郵遞區號
    private String rmp_Name; // 羅馬拼音
    //For SP
    private String v_addrdiff;
    private String v_commzip;
    private String v_hsncode;
    private String v_commaddr;
    
    public String getNpIds() {
        return npIds;
    }
    public void setNpIds(String npIds) {
        this.npIds = npIds;
    }
    public String getApArea() {
        return apArea;
    }
    public void setApArea(String apArea) {
        this.apArea = apArea;
    }
    public String getIdn() {
        return idn;
    }
    public void setIdn(String idn) {
        this.idn = idn;
    }
    public String getEbDate() {
        return ebDate;
    }
    public void setEbDate(String ebDate) {
        this.ebDate = ebDate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHarea() {
        return harea;
    }
    public void setHarea(String harea) {
        this.harea = harea;
    }
    public String getHzip() {
        return hzip;
    }
    public void setHzip(String hzip) {
        this.hzip = hzip;
    }
    public String getHaddr() {
        return haddr;
    }
    public void setHaddr(String haddr) {
        this.haddr = haddr;
    }
    public String getCaddr() {
        return caddr;
    }
    public void setCaddr(String caddr) {
        this.caddr = caddr;
    }
    public String getTransMk() {
        return transMk;
    }
    public void setTransMk(String transMk) {
        this.transMk = transMk;
    }
    public String getSpecCode() {
        return specCode;
    }
    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }
    public String getSpecDate() {
        return specDate;
    }
    public void setSpecDate(String specDate) {
        this.specDate = specDate;
    }
    public String getOlivCode() {
        return olivCode;
    }
    public void setOlivCode(String olivCode) {
        this.olivCode = olivCode;
    }
    public String getInDate() {
        return inDate;
    }
    public void setInDate(String inDate) {
        this.inDate = inDate;
    }
    public String getNeb() {
        return neb;
    }
    public void setNeb(String neb) {
        this.neb = neb;
    }
    public String getNebc() {
        return nebc;
    }
    public void setNebc(String nebc) {
        this.nebc = nebc;
    }
    public BigDecimal getAcnt() {
        return acnt;
    }
    public void setAcnt(BigDecimal acnt) {
        this.acnt = acnt;
    }
    public BigDecimal getVcnt() {
        return vcnt;
    }
    public void setVcnt(BigDecimal vcnt) {
        this.vcnt = vcnt;
    }
    public BigDecimal getNcnt() {
        return ncnt;
    }
    public void setNcnt(BigDecimal ncnt) {
        this.ncnt = ncnt;
    }
    public String getMsg01() {
        return msg01;
    }
    public void setMsg01(String msg01) {
        this.msg01 = msg01;
    }
    public Date getUpdDt() {
        return updDt;
    }
    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }
    public String getCzip() {
        return czip;
    }
    public void setCzip(String czip) {
        this.czip = czip;
    }
	public String getV_addrdiff() {
		return v_addrdiff;
	}
	public void setV_addrdiff(String v_addrdiff) {
		this.v_addrdiff = v_addrdiff;
	}
	public String getV_commzip() {
		return v_commzip;
	}
	public void setV_commzip(String v_commzip) {
		this.v_commzip = v_commzip;
	}
	public String getV_hsncode() {
		return v_hsncode;
	}
	public void setV_hsncode(String v_hsncode) {
		this.v_hsncode = v_hsncode;
	}
	public String getV_commaddr() {
		return v_commaddr;
	}
	public void setV_commaddr(String v_commaddr) {
		this.v_commaddr = v_commaddr;
	}

    public String getRmp_Name() {
        return rmp_Name;
    }

    public void setRmp_Name(String rmp_Name) {
        this.rmp_Name = rmp_Name;
    }
}
