package tw.gov.bli.ba.domain;

import java.io.Serializable;

/**
 * 月核案件檢核確認檔 (<code>BARVCONFIRM</code>)
 *
 * @author Noctis
 */
public class Barvconfirm implements Serializable {
	private String payCode; //給付別
	private String issuYm; //核定年月
	private String confirmMk; //Y：月核案件檢核確認註記
	
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
