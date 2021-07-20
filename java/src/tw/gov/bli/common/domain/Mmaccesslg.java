package tw.gov.bli.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Access Log Domain 物件
 * 
 * @author Goston
 */
public class Mmaccesslg implements Serializable {
    /**
     * 操作日期時間 (民國年YYYMMDDHHMMSSSS)
     */
    private String acsTime;

    /**
     * 程式名稱
     */
    private String apName;

    /**
     * 員工編號
     */
    private String psNo;

    /**
     * 所屬工作部門代碼
     */
    private String deptId;

    /**
     * 異動代碼
     */
    private String trnsId;

    /**
     * 連線 IP / 終端機代碼
     */
    private String termId;

    /**
     * 系統別
     */
    private String stype;

    /**
     * 保險證號
     */
    private String uno;

    /**
     * 資料年月
     */
    private String ym;

    /**
     * 申請人身分證號
     */
    private String idNo;

    /**
     * 申請人中文姓名
     */
    private String proposer;

    /**
     * 作業項目
     */
    private String proc;

    /**
     * 受理編號
     */
    private String apNo;

    /**
     * 事故者身分證號
     */
    private String evIdNo;

    /**
     * 事故者出生日
     */
    private String evBrth;

    /**
     * 畫面代號
     */
    private String scrNo;

    /**
     * 查詢備註
     */
    private String qmk;

    /**
     * 操作類別 (I/U/D/P/Q)
     */
    private String accType;

    /**
     * 編號
     */
    private BigDecimal sno;

    /**
     * 編號 (PORTAL_LOG.SYS_ID)
     */
    private BigDecimal sysId;

    public Mmaccesslg() {

    }

    public String getAcsTime() {
        return acsTime;
    }

    public void setAcsTime(String acsTime) {
        this.acsTime = acsTime;
    }

    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    public String getPsNo() {
        return psNo;
    }

    public void setPsNo(String psNo) {
        this.psNo = psNo;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getTrnsId() {
        return trnsId;
    }

    public void setTrnsId(String trnsId) {
        this.trnsId = trnsId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public String getProc() {
        return proc;
    }

    public void setProc(String proc) {
        this.proc = proc;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getEvIdNo() {
        return evIdNo;
    }

    public void setEvIdNo(String evIdNo) {
        this.evIdNo = evIdNo;
    }

    public String getEvBrth() {
        return evBrth;
    }

    public void setEvBrth(String evBrth) {
        this.evBrth = evBrth;
    }

    public String getScrNo() {
        return scrNo;
    }

    public void setScrNo(String scrNo) {
        this.scrNo = scrNo;
    }

    public String getQmk() {
        return qmk;
    }

    public void setQmk(String qmk) {
        this.qmk = qmk;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public BigDecimal getSno() {
        return sno;
    }

    public void setSno(BigDecimal sno) {
        this.sno = sno;
    }

    public BigDecimal getSysId() {
        return sysId;
    }

    public void setSysId(BigDecimal sysId) {
        this.sysId = sysId;
    }

}
