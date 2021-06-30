package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 月核定差異統計檔 (<code>BALP0D330</code>)
 * 
 * @author Goston
 */
public class Balp0d330 implements Serializable {
    private String payCode; // 給付別
    private String issuYm; // 核定年月
    private BigDecimal apCount; // 受理件數
    private BigDecimal qualify1; // 合格新案
    private BigDecimal qualify2; // 合格續發案
    private BigDecimal qualify3; // 合格停發後續發案
    private BigDecimal qualify4; // 合格補發案
    private BigDecimal qualify5; // 合格停發後補發案
    private BigDecimal newCase1; // 新案待處理
    private BigDecimal newCase2; // 新案不合格
    private BigDecimal disQualify1; // 合格案改待處理
    private BigDecimal disQualify2; // 合格案改不合格
    private String crtUser; // 資料新增人員代號
    private String crtTime; // 資料新增日期時間

    public Balp0d330() {

    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public BigDecimal getApCount() {
        return apCount;
    }

    public void setApCount(BigDecimal apCount) {
        this.apCount = apCount;
    }

    public BigDecimal getQualify1() {
        return qualify1;
    }

    public void setQualify1(BigDecimal qualify1) {
        this.qualify1 = qualify1;
    }

    public BigDecimal getQualify2() {
        return qualify2;
    }

    public void setQualify2(BigDecimal qualify2) {
        this.qualify2 = qualify2;
    }

    public BigDecimal getQualify3() {
        return qualify3;
    }

    public void setQualify3(BigDecimal qualify3) {
        this.qualify3 = qualify3;
    }

    public BigDecimal getQualify4() {
        return qualify4;
    }

    public void setQualify4(BigDecimal qualify4) {
        this.qualify4 = qualify4;
    }

    public BigDecimal getQualify5() {
        return qualify5;
    }

    public void setQualify5(BigDecimal qualify5) {
        this.qualify5 = qualify5;
    }

    public BigDecimal getNewCase1() {
        return newCase1;
    }

    public void setNewCase1(BigDecimal newCase1) {
        this.newCase1 = newCase1;
    }

    public BigDecimal getNewCase2() {
        return newCase2;
    }

    public void setNewCase2(BigDecimal newCase2) {
        this.newCase2 = newCase2;
    }

    public BigDecimal getDisQualify1() {
        return disQualify1;
    }

    public void setDisQualify1(BigDecimal disQualify1) {
        this.disQualify1 = disQualify1;
    }

    public BigDecimal getDisQualify2() {
        return disQualify2;
    }

    public void setDisQualify2(BigDecimal disQualify2) {
        this.disQualify2 = disQualify2;
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

}
