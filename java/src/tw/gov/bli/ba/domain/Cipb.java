package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 被保險人基本資料檔
 * 
 * @author KIYOMI
 */

@Table("CIPB")
public class Cipb implements Serializable {

    private BigDecimal avgWg;
    private BigDecimal nitrmY;
    private BigDecimal nitrmM;
    private BigDecimal oldTY;
    private BigDecimal oldTD;
    
    
    private String cipbFMk; 

    public BigDecimal getAvgWg() {
        return avgWg;
    }

    public void setAvgWg(BigDecimal avgWg) {
        this.avgWg = avgWg;
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

    public BigDecimal getOldTY() {
        return oldTY;
    }

    public void setOldTY(BigDecimal oldTY) {
        this.oldTY = oldTY;
    }

    public BigDecimal getOldTD() {
        return oldTD;
    }

    public void setOldTD(BigDecimal oldTD) {
        this.oldTD = oldTD;
    }

	public String getCipbFMk() {
		return cipbFMk;
	}

	public void setCipbFMk(String cipbFMk) {
		this.cipbFMk = cipbFMk;
	}

}
