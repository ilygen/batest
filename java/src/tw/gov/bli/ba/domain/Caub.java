package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 投保單位檔 (<code>CAUB</code>)
 * 
 * @author Goston
 */
public class Caub implements Serializable {
    private BigDecimal prsnoB; // 月末保險人數
    private String ubNo; // 保險證號
    private String uname; // 投保單位名稱
    private String ename; // 負責人姓名
    private String czpcd; // 郵遞區號
    private String caddr; // 通訊地址
    private String tel; // 電話1
    private String ubType; // 單位類別
    public Caub() {

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

    public String getCzpcd() {
        return czpcd;
    }

    public void setCzpcd(String czpcd) {
        this.czpcd = czpcd;
    }

    public String getCaddr() {
        return caddr;
    }

    public void setCaddr(String caddr) {
        this.caddr = caddr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

	public String getUbType() {
		return ubType;
	}

	public void setUbType(String ubType) {
		this.ubType = ubType;
	}

}
