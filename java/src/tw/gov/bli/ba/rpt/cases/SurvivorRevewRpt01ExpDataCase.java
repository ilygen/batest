package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保遺屬年金給付受理編審清單 - 給付延伸資料
 * 
 * @author Evelyn Hsu
 */

public class SurvivorRevewRpt01ExpDataCase implements Serializable{
    
    private String criInJcl1;  //失能等級 1
    private String criInJcl2;  //失能等級 2
    private String criInJcl3;  //失能等級 3
    private String criInJdp1;  //失能項目 1
    private String criInJdp2;  //失能項目 2
    private String criInJdp3;  //失能項目 3
    private String criInJdp4;  //失能項目 4
    private String criInJdp5;  //失能項目 5
    private String criInJdp6;  //失能項目 6
    private String criInJdp7;  //失能項目 7
    private String criInJdp8;  //失能項目 8
    private String criInJdp9;  //失能項目 9
    private String criInJdp10; //失能項目 10
    private String criInIssul; //核定等級
    private String criInJnme1;  //國際疾病代碼 1
    private String criInJnme2;  //國際疾病代碼 2
    private String criInJnme3;  //國際疾病代碼 3
    private String criInJnme4;  //國際疾病代碼 4
    private String evTyp;       //傷病分類
    private String evCode;      //傷病原因
    private String criInPart1;  //受傷部位 1
    private String criInPart2;  //受傷部位 2
    private String criInPart3;  //受傷部位 3
    private String criMedium;  //媒介物
    private String hosId;       //醫院代碼
    private String doctorName1;//醫生姓名1
    private String doctorName2;//醫生姓名2
    private String prType;        //先核普通
    private String judgeDate; //判決日期
    private String famAppMk; //符合63條之1第3項
    private BigDecimal deductDay;//扣除天數
    private String adWkMk; //加職註記
    private BigDecimal ocAccaddAmt;// 己領職災增給金額 
    private String ocaccIdentMk; // 符合第20條之1註記
    private String monNotifyingMk;  // 寄發月通知表
    
    
    // 失能等級
    public String getCriInJclStr() {
        String criInJcl = "";
        if (StringUtils.isNotBlank(getCriInJcl1())) {
            criInJcl += getCriInJcl1() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJcl2())) {
            criInJcl += getCriInJcl2() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJcl3())) {
            criInJcl += getCriInJcl3() + ",";
        }
        if (StringUtils.isNotBlank(criInJcl)) {
            criInJcl = criInJcl.substring(0, criInJcl.length() - 1);
        }
        return criInJcl;
    }
    
    // 失能項目
    public String getCriInJdpStr() {
        String criInJdp = "";
        if (StringUtils.isNotBlank(getCriInJdp1())) {
            criInJdp += getCriInJdp1() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp2())) {
            criInJdp += getCriInJdp2() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp3())) {
            criInJdp += getCriInJdp3() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp4())) {
            criInJdp += getCriInJdp4() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp5())) {
            criInJdp += getCriInJdp5() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp6())) {
            criInJdp += getCriInJdp6() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp7())) {
            criInJdp += getCriInJdp7() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp8())) {
            criInJdp += getCriInJdp8() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp9())) {
            criInJdp += getCriInJdp9() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp10())) {
            criInJdp += getCriInJdp10() + ",";
        }
        if (StringUtils.isNotBlank(criInJdp)) {
            criInJdp = criInJdp.substring(0, criInJdp.length() - 1);
        }
        return criInJdp;
    }
    
    //國際疾病代碼
    public String getCriInJnmeStr() {
        String criInJnme = "";
        if (StringUtils.isNotBlank(getCriInJnme1())) {
            criInJnme += getCriInJnme1() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJnme2())) {
            criInJnme += getCriInJnme2() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJnme3())) {
            criInJnme += getCriInJnme3() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJnme4())) {
            criInJnme += getCriInJnme4() + ",";
        }
        if (StringUtils.isNotBlank(criInJnme)) {
            criInJnme = criInJnme.substring(0, criInJnme.length() - 1);
        }
        return criInJnme;
    }
    
    // 受傷部位
    public String getCriInPartStr() {
        String criInPart = "";
        if (StringUtils.isNotBlank(getCriInPart1())) {
            criInPart += getCriInPart1() + ",";
        }
        if (StringUtils.isNotBlank(getCriInPart2())) {
            criInPart += getCriInPart2() + ",";
        }
        if (StringUtils.isNotBlank(getCriInPart3())) {
            criInPart += getCriInPart3() + ",";
        }
        if (StringUtils.isNotBlank(criInPart)) {
            criInPart = criInPart.substring(0, criInPart.length() - 1);
        }
        return criInPart;
    }
    
    /**
     * 判決日期
     */
    public String getJudgeDateString() {
        if (StringUtils.length(judgeDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(judgeDate), false);
        else
            return StringUtils.defaultString(judgeDate);
    }

    public String getCriInJcl1() {
        return criInJcl1;
    }

    public void setCriInJcl1(String criInJcl1) {
        this.criInJcl1 = criInJcl1;
    }

    public String getCriInJcl2() {
        return criInJcl2;
    }

    public void setCriInJcl2(String criInJcl2) {
        this.criInJcl2 = criInJcl2;
    }

    public String getCriInJcl3() {
        return criInJcl3;
    }

    public void setCriInJcl3(String criInJcl3) {
        this.criInJcl3 = criInJcl3;
    }

    public String getCriInJdp1() {
        return criInJdp1;
    }

    public void setCriInJdp1(String criInJdp1) {
        this.criInJdp1 = criInJdp1;
    }

    public String getCriInJdp2() {
        return criInJdp2;
    }

    public void setCriInJdp2(String criInJdp2) {
        this.criInJdp2 = criInJdp2;
    }

    public String getCriInJdp3() {
        return criInJdp3;
    }

    public void setCriInJdp3(String criInJdp3) {
        this.criInJdp3 = criInJdp3;
    }

    public String getCriInJdp4() {
        return criInJdp4;
    }

    public void setCriInJdp4(String criInJdp4) {
        this.criInJdp4 = criInJdp4;
    }

    public String getCriInJdp5() {
        return criInJdp5;
    }

    public void setCriInJdp5(String criInJdp5) {
        this.criInJdp5 = criInJdp5;
    }

    public String getCriInJdp6() {
        return criInJdp6;
    }

    public void setCriInJdp6(String criInJdp6) {
        this.criInJdp6 = criInJdp6;
    }

    public String getCriInJdp7() {
        return criInJdp7;
    }

    public void setCriInJdp7(String criInJdp7) {
        this.criInJdp7 = criInJdp7;
    }

    public String getCriInJdp8() {
        return criInJdp8;
    }

    public void setCriInJdp8(String criInJdp8) {
        this.criInJdp8 = criInJdp8;
    }

    public String getCriInJdp9() {
        return criInJdp9;
    }

    public void setCriInJdp9(String criInJdp9) {
        this.criInJdp9 = criInJdp9;
    }

    public String getCriInJdp10() {
        return criInJdp10;
    }

    public void setCriInJdp10(String criInJdp10) {
        this.criInJdp10 = criInJdp10;
    }

    public String getCriInIssul() {
        return criInIssul;
    }

    public void setCriInIssul(String criInIssul) {
        this.criInIssul = criInIssul;
    }

    public String getCriInJnme1() {
        return criInJnme1;
    }

    public void setCriInJnme1(String criInJnme1) {
        this.criInJnme1 = criInJnme1;
    }

    public String getCriInJnme2() {
        return criInJnme2;
    }

    public void setCriInJnme2(String criInJnme2) {
        this.criInJnme2 = criInJnme2;
    }

    public String getCriInJnme3() {
        return criInJnme3;
    }

    public void setCriInJnme3(String criInJnme3) {
        this.criInJnme3 = criInJnme3;
    }

    public String getCriInJnme4() {
        return criInJnme4;
    }

    public void setCriInJnme4(String criInJnme4) {
        this.criInJnme4 = criInJnme4;
    }

    public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
    }

    public String getEvCode() {
        return evCode;
    }

    public void setEvCode(String evCode) {
        this.evCode = evCode;
    }

    public String getCriInPart1() {
        return criInPart1;
    }

    public void setCriInPart1(String criInPart1) {
        this.criInPart1 = criInPart1;
    }

    public String getCriInPart2() {
        return criInPart2;
    }

    public void setCriInPart2(String criInPart2) {
        this.criInPart2 = criInPart2;
    }

    public String getCriInPart3() {
        return criInPart3;
    }

    public void setCriInPart3(String criInPart3) {
        this.criInPart3 = criInPart3;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getDoctorName1() {
        return doctorName1;
    }

    public void setDoctorName1(String doctorName1) {
        this.doctorName1 = doctorName1;
    }

    public String getDoctorName2() {
        return doctorName2;
    }

    public void setDoctorName2(String doctorName2) {
        this.doctorName2 = doctorName2;
    }

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType;
    }

    public String getJudgeDate() {
        return judgeDate;
    }

    public void setJudgeDate(String judgeDate) {
        this.judgeDate = judgeDate;
    }

    public String getCriMedium() {
        return criMedium;
    }

    public void setCriMedium(String criMedium) {
        this.criMedium = criMedium;
    }

    public String getFamAppMk() {
        return famAppMk;
    }

    public void setFamAppMk(String famAppMk) {
        this.famAppMk = famAppMk;
    }

    public BigDecimal getDeductDay() {
        return deductDay;
    }

    public void setDeductDay(BigDecimal deductDay) {
        this.deductDay = deductDay;
    }

    public String getAdWkMk() {
        return adWkMk;
    }

    public void setAdWkMk(String adWkMk) {
        this.adWkMk = adWkMk;
    }

	public BigDecimal getOcAccaddAmt() {
		return ocAccaddAmt;
	}

	public void setOcAccaddAmt(BigDecimal ocAccaddAmt) {
		this.ocAccaddAmt = ocAccaddAmt;
	}

	public String getOcaccIdentMk() {
		return ocaccIdentMk;
	}

	public void setOcaccIdentMk(String ocaccIdentMk) {
		this.ocaccIdentMk = ocaccIdentMk;
	}
    
    public String getMonNotifyingMk() {
        return monNotifyingMk;
    }

    public void setMonNotifyingMk(String monNotifyingMk) {
        this.monNotifyingMk = monNotifyingMk;
    }    
    
}