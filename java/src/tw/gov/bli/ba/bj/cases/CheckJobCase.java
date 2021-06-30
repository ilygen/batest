package tw.gov.bli.ba.bj.cases;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;

/**
 * case for 檢核確認作業
 * 
 * @author Noctis
 */
public class CheckJobCase {
	
	private String payCode; //給付別
	private String issuYm; //核定年月
	private String confirmMk; //Y：月核案件檢核確認註記
	
	/**
     * 給付別中文名稱
     * 
     * @return
     */
    public String getPayCodeStr() {
        if (StringUtils.equalsIgnoreCase(payCode, "L"))
            return "老年年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "K"))
            return "失能年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "S"))
            return "遺屬年金";
        else
            return "";
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
	public String getConfirmMk() {
		return confirmMk;
	}
	public void setConfirmMk(String confirmMk) {
		this.confirmMk = confirmMk;
	}
}
