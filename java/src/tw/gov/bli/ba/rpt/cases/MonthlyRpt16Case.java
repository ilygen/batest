package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 退匯通知書
 * 
 * @author Goston
 */
public class MonthlyRpt16Case implements Serializable {
    private String payCode; // 給付別

    private String apNo; // 受理編號
    private String payDate; // 核付日期
    private BigDecimal remitAmt; // 退匯金額
    private String brNote; // 退匯原因
    private String brChkDate; // 退匯初核日期
    private String benIdn; // 受款人身分證號
    private String benName; // 受款人姓名
    private String commZip; // 郵遞區號
    private String commAddr; // 地址
    private String commTel; // 電話
    private String manager;// 總經理名稱
    private byte[] managerImg;// 總經理署名圖

    /**
     * 給付別中文名稱
     * 
     * @return
     */
    public String getPayCodeString() {
        if (StringUtils.equalsIgnoreCase(payCode, "L"))
            return "老年年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "K"))
            return "失能年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "S"))
            return "遺屬年金";
        else
            return "";
    }

    /**
     * 受理編號
     * 
     * @return
     */
    public String getApNoString() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }

    /**
     * 核付日期
     * 
     * @return
     */
    public String getPayDateString() {
        if (StringUtils.length(payDate) == 8) {
            return DateUtility.formatChineseDateString(payDate, true);
        }
        else {
            return "    年    月    日";
        }
    }

    /**
     * 退匯初核日期
     * 
     * @return
     */
    public String getBrChkDateChtString() {
        if (StringUtils.length(brChkDate) == 8) {
            return DateUtility.formatChineseDateString(brChkDate, true);
        }
        else {
            return "    年    月    日";
        }
    }

    /**
     * 退匯初核日期
     * 
     * @return
     */
    public String getBrChkDateEngString() {
        if (StringUtils.length(brChkDate) == 8) {
            return DateUtility.changeDateType(brChkDate);
        }
        else {
            return StringUtils.trimToEmpty(brChkDate);
        }
    }

    /**
     * 受款人身分證號 (第 5 到 7 碼打 * 號)(20150216修改隱碼為後4碼)
     * 
     * @return
     */
    public String getBenIdnString() {
    	
    	if(StringUtils.isNotBlank(benIdn)){
    	
           if (StringUtils.length(benIdn) >= 10) {
               //return StringUtils.substring(benIdn, 0, 4) + "***" + StringUtils.substring(benIdn, 7, 10);
        	   return StringUtils.substring(benIdn, 0, 6) + "****";
           }
           else {
               //return StringUtils.substring(benIdn, 0, 4) + "******";
        	   return StringUtils.substring(benIdn, 0, 6) + "****";
           }
        
    	}else{
    		return benIdn;
    	}
    }
    
    /**
     * 受款人電話 (第 3 到 7 碼打 * 號)(20150216修改隱碼為後4碼)
     * 
     * @return
     */
    public String getCommTelString() {
    	
    	if(StringUtils.isNotBlank(commTel)){
    	
           if (StringUtils.length(commTel) <= 7) {
        	   return StringUtils.substring(commTel, 0, 2) + "*****";
           }
           else {
        	   return StringUtils.substring(commTel, 0, 2) + "*****" + StringUtils.substring(commTel, 7, commTel.length());
           }
        
    	}else{
    		return commTel;
    	}
    }

    /**
     * 退匯金額
     * 
     * @return
     */
    public BigDecimal getRemitAmtNonNull() {
        if (remitAmt == null)
            return new BigDecimal(0);
        else
            return remitAmt;
    }

    public MonthlyRpt16Case() {

    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }

    public String getBrNote() {
        return brNote;
    }

    public void setBrNote(String brNote) {
        this.brNote = brNote;
    }

    public String getBrChkDate() {
        return brChkDate;
    }

    public void setBrChkDate(String brChkDate) {
        this.brChkDate = brChkDate;
    }

    public String getBenIdn() {
        return benIdn;
    }

    public void setBenIdn(String benIdn) {
        this.benIdn = benIdn;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getCommZip() {
        return commZip;
    }

    public void setCommZip(String commZip) {
        this.commZip = commZip;
    }

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    public String getCommTel() {
        return commTel;
    }

    public void setCommTel(String commTel) {
        this.commTel = commTel;
    }

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public byte[] getManagerImg() {
		return managerImg;
	}

	public void setManagerImg(byte[] managerImg) {
		this.managerImg = managerImg;
	}

}
