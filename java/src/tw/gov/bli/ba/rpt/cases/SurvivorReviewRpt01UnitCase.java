package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 勞保遺屬年金給付受理編審清單 - 投保單位資料
 * 
 * @author Kiyomi
 */

public class SurvivorReviewRpt01UnitCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;

    private BigDecimal prsnoB; // 月末保險人數
    private String ubNo; // 保險證號
    private String uname; // 投保單位名稱
    private String ename; // 負責人姓名

    public SurvivorReviewRpt01UnitCase() {

    }

    public BigDecimal getPrsnoB() {
        return prsnoB;
    }

    public void setPrsnoB(BigDecimal prsnoB) {
        this.prsnoB = prsnoB;
    }

    public String getUbNo() {
        return ubNo;
    }

    public void setUbNo(String ubNo) {
        this.ubNo = ubNo;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }
}
