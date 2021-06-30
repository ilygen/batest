package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.Table;

/**
 * 國保給付核定檔
 * 
 * @author Rickychi
 */
@Table("NBDAPR")
public class Nbdapr implements Serializable {
    private String chkDt;// 核定日期
    private String aplPayDate;// 帳務日期
    private String mchkType;// 月核類別

    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private BigDecimal issueAmt;// 核定金額
    private BigDecimal sagtotAmt;// 代扣保費金額
    private BigDecimal itrtAmt;// 利息(20091023新增)(代扣需加此欄方為代扣保費總和)
    private BigDecimal recAmt;// 收回金額
    private BigDecimal supAmt;// 補發金額
    private BigDecimal cutAmt;// 減領金額
    private BigDecimal otherAmt;// 另案扣減金額
    private BigDecimal aplPayAmt;// 實付金額
    private String manChkFlg;// 人工審核結果

    public String getChkDt() {
        return chkDt;
    }

    public void setChkDt(String chkDt) {
        this.chkDt = chkDt;
    }

    public String getAplPayDate() {
        return aplPayDate;
    }

    public void setAplPayDate(String aplPayDate) {
        this.aplPayDate = aplPayDate;
    }

    public String getMchkType() {
        return mchkType;
    }

    public void setMchkType(String mchkType) {
        this.mchkType = mchkType;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public BigDecimal getSagtotAmt() {
        return sagtotAmt;
    }

    public void setSagtotAmt(BigDecimal sagtotAmt) {
        this.sagtotAmt = sagtotAmt;
    }

    public BigDecimal getItrtAmt() {
        return itrtAmt;
    }

    public void setItrtAmt(BigDecimal itrtAmt) {
        this.itrtAmt = itrtAmt;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getSupAmt() {
        return supAmt;
    }

    public void setSupAmt(BigDecimal supAmt) {
        this.supAmt = supAmt;
    }

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getAplPayAmt() {
        return aplPayAmt;
    }

    public void setAplPayAmt(BigDecimal aplPayAmt) {
        this.aplPayAmt = aplPayAmt;
    }

    public String getManChkFlg() {
        return manChkFlg;
    }

    public void setManChkFlg(String manChkFlg) {
        this.manChkFlg = manChkFlg;
    }
}
