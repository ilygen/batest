package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * case for 給付媒體回押註記作業
 * 
 * @author swim
 */
public class BagivedtlCase implements Serializable {
    private static final long serialVersionUID = -5056817223316820001L;

    private String stat2; // 處理狀況
    private String paramName; // 處理狀況
    private String statCount; // 處理狀況總筆數
    private BigDecimal statAmt; // 處理狀況總金額

    private String payCount1; // 出帳資料總筆數
    private BigDecimal paySum1; // 出帳資料總金額
    private String payCount2; // 媒體回覆總筆數
    private BigDecimal paySum2; // 媒體回覆總金額
    private String payCount3; // 比對異常總筆數
    private BigDecimal paySum3; // 比對異常總金額
    private String payCount4; // 無回覆記錄筆數
    private BigDecimal paySum4; // 無回覆記錄金額
    private String payCount5; // 無出帳記錄筆數
    private BigDecimal paySum5; // 無出帳記錄金額

    public String getStat2() {
        return stat2;
    }

    public void setStat2(String stat2) {
        this.stat2 = stat2;
    }

    public String getStatCount() {
        return statCount;
    }

    public void setStatCount(String statCount) {
        this.statCount = statCount;
    }

    public BigDecimal getStatAmt() {
        return statAmt;
    }

    public void setStatAmt(BigDecimal statAmt) {
        this.statAmt = statAmt;
    }

    public String getStatAmtStr() {
        if (statAmt != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(statAmt.longValue());
        }
        else {
            return "";
        }
    }

    public String getStatCountStr() {
        if (statCount != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(Integer.decode(statCount).longValue());
        }
        else {
            return "";
        }
    }

    public String getStat2Str() {
        return stat2 + "-" + paramName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getPayCount1() {
        return payCount1;
    }

    public void setPayCount1(String payCount1) {
        this.payCount1 = payCount1;
    }

    public BigDecimal getPaySum1() {
        return paySum1;
    }

    public void setPaySum1(BigDecimal paySum1) {
        this.paySum1 = paySum1;
    }

    public String getPayCount2() {
        return payCount2;
    }

    public void setPayCount2(String payCount2) {
        this.payCount2 = payCount2;
    }

    public BigDecimal getPaySum2() {
        return paySum2;
    }

    public void setPaySum2(BigDecimal paySum2) {
        this.paySum2 = paySum2;
    }

    public String getPayCount3() {
        return payCount3;
    }

    public void setPayCount3(String payCount3) {
        this.payCount3 = payCount3;
    }

    public BigDecimal getPaySum3() {
        return paySum3;
    }

    public void setPaySum3(BigDecimal paySum3) {
        this.paySum3 = paySum3;
    }

    public String getPayCount4() {
        return payCount4;
    }

    public void setPayCount4(String payCount4) {
        this.payCount4 = payCount4;
    }

    public BigDecimal getPaySum4() {
        return paySum4;
    }

    public void setPaySum4(BigDecimal paySum4) {
        this.paySum4 = paySum4;
    }

    public String getPayCount5() {
        return payCount5;
    }

    public void setPayCount5(String payCount5) {
        this.payCount5 = payCount5;
    }

    public BigDecimal getPaySum5() {
        return paySum5;
    }

    public void setPaySum5(BigDecimal paySum5) {
        this.paySum5 = paySum5;
    }

    public String getPayCount1Str() {
        if (payCount1 != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(Integer.decode(payCount1).longValue());
        }
        else {
            return "";
        }
    }

    public String getPayCount2Str() {
        if (payCount2 != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(Integer.decode(payCount2).longValue());
        }
        else {
            return "";
        }
    }

    public String getPayCount3Str() {
        if (payCount3 != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(Integer.decode(payCount3).longValue());
        }
        else {
            return "";
        }
    }

    public String getPayCount4Str() {
        if (payCount4 != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(Integer.decode(payCount4).longValue());
        }
        else {
            return "";
        }
    }

    public String getPayCount5Str() {
        if (payCount5 != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(Integer.decode(payCount5).longValue());
        }
        else {
            return "";
        }
    }

    public String getPaySum1Str() {
        if (paySum1 != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(paySum1.longValue());
        }
        else {
            return "";
        }
    }

    public String getPaySum2Str() {
        if (paySum2 != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(paySum2.longValue());
        }
        else {
            return "";
        }
    }

    public String getPaySum3Str() {
        if (paySum3 != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(paySum3.longValue());
        }
        else {
            return "";
        }
    }

    public String getPaySum4Str() {
        if (paySum4 != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(paySum4.longValue());
        }
        else {
            return "";
        }
    }

    public String getPaySum5Str() {
        if (paySum5 != null) {
            DecimalFormat df = new DecimalFormat("##,###,###,###,###");
            return df.format(paySum5.longValue());
        }
        else {
            return "";
        }
    }
}
