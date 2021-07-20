package tw.gov.bli.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Query Log Domain 物件
 * 
 * @author Goston
 */
public class Mmquerylog implements Serializable {
    /**
     * TABLE 名稱
     */
    private String tableName;

    /**
     * 查詢時間
     */
    private String qyTime;

    /**
     * 程式名稱
     */
    private String pgmName;

    /**
     * 程式代碼
     */
    private String pgmCode;

    /**
     * 員工部門代號
     */
    private String deptId;

    /**
     * 員工編號
     */
    private String queryMan;

    /**
     * 終端機位址
     */
    private String termEd;

    /**
     * 查詢代號
     */
    private String qyCode;

    /**
     * 查詢條件
     */
    private String qyCondition;

    /**
     * 證號
     */
    private String idNo;

    /**
     * 備註
     */
    private String memo;

    /**
     * 編號 (MMACCESSLG.SNO)
     */
    private BigDecimal sno;

    public Mmquerylog() {

    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getQyTime() {
        return qyTime;
    }

    public void setQyTime(String qyTime) {
        this.qyTime = qyTime;
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

    public String getQueryMan() {
        return queryMan;
    }

    public void setQueryMan(String queryMan) {
        this.queryMan = queryMan;
    }

    public String getTermEd() {
        return termEd;
    }

    public void setTermEd(String termEd) {
        this.termEd = termEd;
    }

    public String getQyCode() {
        return qyCode;
    }

    public void setQyCode(String qyCode) {
        this.qyCode = qyCode;
    }

    public String getQyCondition() {
        return qyCondition;
    }

    public void setQyCondition(String qyCondition) {
        this.qyCondition = qyCondition;
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
