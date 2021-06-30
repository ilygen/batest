package tw.gov.bli.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * AP Log Domain 物件
 * 
 * @author Goston
 */
public class Mmaplog implements Serializable {
    /**
     * 異動 TABLE 名稱
     */
    private String tableName;

    /**
     * 異動 TABLE 主鍵
     */
    private String pkField;

    /**
     * 異動時間
     */
    private String chgTime;

    /**
     * 程式名稱
     */
    private String pgmName;

    /**
     * 程式代碼
     */
    private String pgmCode;

    /**
     * 異動員工部門代號
     */
    private String deptId;

    /**
     * 異動員工編號
     */
    private String modifyMan;

    /**
     * 終端機位址
     */
    private String termEd;

    /**
     * 異動代號
     */
    private String chgCode;

    /**
     * 異動欄位
     */
    private String field;

    /**
     * 改前內容
     */
    private String befImg;

    /**
     * 改後內容
     */
    private String aftImg;

    /**
     * 證號
     */
    private String idNo;

    /**
     * 備註
     */
    private String memo;

    /**
     * 編號
     */
    private BigDecimal sno;

    public Mmaplog() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPkField() {
        return pkField;
    }

    public void setPkField(String pkField) {
        this.pkField = pkField;
    }

    public String getChgTime() {
        return chgTime;
    }

    public void setChgTime(String chgTime) {
        this.chgTime = chgTime;
    }

    public String getPgmName() {
        return pgmName;
    }

    public void setPgmName(String pgmName) {
        this.pgmName = pgmName;
    }

    public String getPgmCode() {
        return pgmCode;
    }

    public void setPgmCode(String pgmCode) {
        this.pgmCode = pgmCode;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getModifyMan() {
        return modifyMan;
    }

    public void setModifyMan(String modifyMan) {
        this.modifyMan = modifyMan;
    }

    public String getTermEd() {
        return termEd;
    }

    public void setTermEd(String termEd) {
        this.termEd = termEd;
    }

    public String getChgCode() {
        return chgCode;
    }

    public void setChgCode(String chgCode) {
        this.chgCode = chgCode;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getBefImg() {
        return befImg;
    }

    public void setBefImg(String befImg) {
        this.befImg = befImg;
    }

    public String getAftImg() {
        return aftImg;
    }

    public void setAftImg(String aftImg) {
        this.aftImg = aftImg;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public BigDecimal getSno() {
        return sno;
    }

    public void setSno(BigDecimal sno) {
        this.sno = sno;
    }

}
