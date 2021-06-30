package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 勞保遺屬年金給付受理編審清單 - 事故者死亡一次給付相關資料
 * 
 * @author Evelyn Hsu
 */

public class SurvivorReviewRpt01DieOncePayDataCase implements Serializable {

    private BigDecimal baoncepayId; // 資料列編號
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String appDate; // 申請日期
    private String evtIds; // 被保險人社福識別碼
    private String evtIdnNo; // 被保險人身分證號
    private String evtName; // 被保險人姓名
    private String evtBrDate; // 被保險人出生日期
    private String evtExpireDate; // 被保險人屆齡日期
    private String evtDate; // 被保險人退保日期
    private String evtJobDate; // 被保險人離職日期
    private BigDecimal oldtY; // 投保年資(年)
    private BigDecimal oldtD; // 投保年資(日)
    private BigDecimal nitrmY; // 投保年資(年-年金制)
    private BigDecimal nitrmM; // 投保年資(月-年金制)
    private BigDecimal noldtY; // 老年年資(年)
    private BigDecimal noldtM; // 老年年資(月)
    private BigDecimal onceAvgAmt; // 一次給付平均薪資
    private BigDecimal oncePayYm; // 一次給付月數
    private BigDecimal oncePayAmt; // 一次給付核定金額
    private BigDecimal onceOldAmt; // 老年一次金金額
    private BigDecimal insAvgAmt; // 老年一次金平均薪資
    private BigDecimal insNitrmY; // 老年一次投保年資(年)
    private BigDecimal insNitrmM; // 老年一次投保年資(月)
    private String procTime; // 處理日期時間
    
    public SurvivorReviewRpt01DieOncePayDataCase(){
        
    }

    public BigDecimal getBaoncepayId() {
        return baoncepayId;
    }

    public void setBaoncepayId(BigDecimal baoncepayId) {
        this.baoncepayId = baoncepayId;
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

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getEvtIds() {
        return evtIds;
    }

    public void setEvtIds(String evtIds) {
        this.evtIds = evtIds;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getEvtExpireDate() {
        return evtExpireDate;
    }

    public void setEvtExpireDate(String evtExpireDate) {
        this.evtExpireDate = evtExpireDate;
    }

    public String getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(String evtDate) {
        this.evtDate = evtDate;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public BigDecimal getOldtY() {
        return oldtY;
    }

    public void setOldtY(BigDecimal oldtY) {
        this.oldtY = oldtY;
    }

    public BigDecimal getOldtD() {
        return oldtD;
    }

    public void setOldtD(BigDecimal oldtD) {
        this.oldtD = oldtD;
    }

    public BigDecimal getNitrmY() {
        return nitrmY;
    }

    public void setNitrmY(BigDecimal nitrmY) {
        this.nitrmY = nitrmY;
    }

    public BigDecimal getNitrmM() {
        return nitrmM;
    }

    public void setNitrmM(BigDecimal nitrmM) {
        this.nitrmM = nitrmM;
    }

    public BigDecimal getNoldtY() {
        return noldtY;
    }

    public void setNoldtY(BigDecimal noldtY) {
        this.noldtY = noldtY;
    }

    public BigDecimal getNoldtM() {
        return noldtM;
    }

    public void setNoldtM(BigDecimal noldtM) {
        this.noldtM = noldtM;
    }

    public BigDecimal getOnceAvgAmt() {
        return onceAvgAmt;
    }

    public void setOnceAvgAmt(BigDecimal onceAvgAmt) {
        this.onceAvgAmt = onceAvgAmt;
    }

    public BigDecimal getOncePayYm() {
        return oncePayYm;
    }

    public void setOncePayYm(BigDecimal oncePayYm) {
        this.oncePayYm = oncePayYm;
    }

    public BigDecimal getOncePayAmt() {
        return oncePayAmt;
    }

    public void setOncePayAmt(BigDecimal oncePayAmt) {
        this.oncePayAmt = oncePayAmt;
    }

    public BigDecimal getOnceOldAmt() {
        return onceOldAmt;
    }

    public void setOnceOldAmt(BigDecimal onceOldAmt) {
        this.onceOldAmt = onceOldAmt;
    }

    public BigDecimal getInsAvgAmt() {
        return insAvgAmt;
    }

    public void setInsAvgAmt(BigDecimal insAvgAmt) {
        this.insAvgAmt = insAvgAmt;
    }

    public BigDecimal getInsNitrmY() {
        return insNitrmY;
    }

    public void setInsNitrmY(BigDecimal insNitrmY) {
        this.insNitrmY = insNitrmY;
    }

    public BigDecimal getInsNitrmM() {
        return insNitrmM;
    }

    public void setInsNitrmM(BigDecimal insNitrmM) {
        this.insNitrmM = insNitrmM;
    }

    public String getProcTime() {
        return procTime;
    }

    public void setProcTime(String procTime) {
        this.procTime = procTime;
    }
    
    
    
}
