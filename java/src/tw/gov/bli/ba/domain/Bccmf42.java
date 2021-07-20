package tw.gov.bli.ba.domain;

import java.io.Serializable;

/**
 * 保密資料檔(BCCMF42)
 * 
 * @author Noctis
 */
public class Bccmf42 implements Serializable {
    private String idn;// 身分證號
    private String payTyp;
    
	public String getIdn() {
		return idn;
	}
	
	public void setIdn(String idn) {
		this.idn = idn;
	}
	
	public String getPayTyp() {
		return payTyp;
	}
	
	public void setPayTyp(String payTyp) {
		this.payTyp = payTyp;
	}
    
    
}
