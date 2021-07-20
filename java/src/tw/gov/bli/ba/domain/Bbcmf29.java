package tw.gov.bli.ba.domain;

import java.io.Serializable;

/**
 * 出生日期錯誤參數檔(BBCMF29)
 * 
 * @author Noctis
 */
public class Bbcmf29 implements Serializable {
    private String idNo;  // 身分證號
    private String brDte; // 出生日期
    private String pbName;// 姓名
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getBrDte() {
		return brDte;
	}
	public void setBrDte(String brDte) {
		this.brDte = brDte;
	}
	public String getPbName() {
		return pbName;
	}
	public void setPbName(String pbName) {
		this.pbName = pbName;
	}
    
    
}
