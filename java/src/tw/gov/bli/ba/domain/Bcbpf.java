package tw.gov.bli.ba.domain;

import java.io.Serializable;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 銀行資料檔
 * 
 * @author Noctis
 */

@Table("BCBPF")
public class Bcbpf implements Serializable {
    @PkeyField("BNKALL")
    private String bnkAll;// 主機構代碼
    @PkeyField("BNKONO")
    private String bnkOno;// 分支機構代碼
    private String stusf;// 機構名稱
    private String bnkNme;// 機構簡稱
    private String modifyMan;// 機構地址
    private String bnkTelno;// 機構電話
    private String delFlg;// 刪除註記
	public String getBnkAll() {
		return bnkAll;
	}
	public void setBnkAll(String bnkAll) {
		this.bnkAll = bnkAll;
	}
	public String getBnkOno() {
		return bnkOno;
	}
	public void setBnkOno(String bnkOno) {
		this.bnkOno = bnkOno;
	}
	public String getStusf() {
		return stusf;
	}
	public void setStusf(String stusf) {
		this.stusf = stusf;
	}
	public String getBnkNme() {
		return bnkNme;
	}
	public void setBnkNme(String bnkNme) {
		this.bnkNme = bnkNme;
	}
	public String getModifyMan() {
		return modifyMan;
	}
	public void setModifyMan(String modifyMan) {
		this.modifyMan = modifyMan;
	}
	public String getBnkTelno() {
		return bnkTelno;
	}
	public void setBnkTelno(String bnkTelno) {
		this.bnkTelno = bnkTelno;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

}
