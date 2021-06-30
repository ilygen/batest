package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 明細資料 - 失能相關資料
 * 
 * @author Rickychi
 */
public class PaymentQueryDisabledDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String prType;// 先核普通
    private String criMedium;// 媒介物
    private String evTyp;// 傷病分類
    private String rehcYm;// 重新查核失能程度年月
    private String ocaccIdentMk;// 符合第20條之1註記
    private String evCode;// 傷病原因
    private String criInIssul;// 核定等級
    private String criInJcl1;// 失能等級1
    private String criInJcl2;// 失能等級2
    private String criInJcl3;// 失能等級3
    private String criInPart1;// 受傷部位1
    private String criInPart2;// 受傷部位2
    private String criInPart3;// 受傷部位3
    private String criInJnme1;// 國際疾病代碼1
    private String criInJnme2;// 國際疾病代碼2
    private String criInJnme3;// 國際疾病代碼3
    private String criInJnme4;// 國際疾病代碼4
    private String criInJdp1;// 失能項目1
    private String criInJdp2;// 失能項目2
    private String criInJdp3;// 失能項目3
    private String criInJdp4;// 失能項目4
    private String criInJdp5;// 失能項目5
    private String criInJdp6;// 失能項目6
    private String criInJdp7;// 失能項目7
    private String criInJdp8;// 失能項目8
    private String criInJdp9;// 失能項目9
    private String criInJdp10;// 失能項目10
    private String hosId;// 醫療院所代碼
    private String doctorName1;// 醫師姓名1
    private String doctorName2;// 醫師姓名2
    private String hpSnam;// 醫院簡稱
    private String ocAccHosId;// 職病醫療院所代碼
    private String ocAccDoctorName1;// 職病醫師姓名1
    private String ocAccDoctorName2;// 職病醫師姓名2
    private String ocAccHpSnam;// 職病醫院簡稱
    private String judgeDate;// 判決日期
    private String famAppMk;// 符合第63條之1第3項註記
    private BigDecimal deductDay; // 扣除天數
    private String disQualMk; // 年金請領資格

    public String getEvTypStr() {
        String evTypStr = "";
        if ((ConstantKey.BAAPPEXPAND_EVTYP_1).equals(getEvTyp())) {
            evTypStr = ConstantKey.BAAPPEXPAND_EVTYP_STR_1;
        }
        else if ((ConstantKey.BAAPPEXPAND_EVTYP_2).equals(getEvTyp())) {
            evTypStr = ConstantKey.BAAPPEXPAND_EVTYP_STR_2;
        }
        else if ((ConstantKey.BAAPPEXPAND_EVTYP_3).equals(getEvTyp())) {
            evTypStr = ConstantKey.BAAPPEXPAND_EVTYP_STR_3;
        }
        else if ((ConstantKey.BAAPPEXPAND_EVTYP_4).equals(getEvTyp())) {
            evTypStr = ConstantKey.BAAPPEXPAND_EVTYP_STR_4;
        }
        return evTypStr;
    }

    public String getDisQualMkStr() {
        String disQualMkStr = "";
        if ((ConstantKey.BAAPPEXPAND_DISQUALMK_1).equals(getDisQualMk())) {
            disQualMkStr = ConstantKey.BAAPPEXPAND_DISQUALMK_STR_1;
        }
        else if ((ConstantKey.BAAPPEXPAND_DISQUALMK_2).equals(getDisQualMk())) {
            disQualMkStr = ConstantKey.BAAPPEXPAND_DISQUALMK_STR_2;
        }
        return disQualMkStr;
    }

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

    public String getDoctorNameStr() {
        String doctorName = "";
        if (StringUtils.isNotBlank(getDoctorName1())) {
            doctorName += getDoctorName1() + ",";
        }
        if (StringUtils.isNotBlank(getDoctorName2())) {
            doctorName += getDoctorName2() + ",";
        }
        if (StringUtils.isNotBlank(doctorName)) {
            doctorName = doctorName.substring(0, doctorName.length() - 1);
        }
        return doctorName;
    }

    public String getHosIdSname() {
        String hosIdSname = "";
        if (StringUtils.isNotBlank(getHosId())) {
            hosIdSname += getHosId();
        }
        if (StringUtils.isNotBlank(getHpSnam())) {
            hosIdSname += "(" + getHpSnam() + ")";
        }
        return hosIdSname;
    }

    public String getOcAccDoctorNameStr() {
        String doctorName = "";
        if (StringUtils.isNotBlank(getOcAccDoctorName1())) {
            doctorName += getOcAccDoctorName1() + ",";
        }
        if (StringUtils.isNotBlank(getOcAccDoctorName2())) {
            doctorName += getOcAccDoctorName2() + ",";
        }
        if (StringUtils.isNotBlank(doctorName)) {
            doctorName = doctorName.substring(0, doctorName.length() - 1);
        }
        return doctorName;
    }

    public String getOcAccHosIdSname() {
        String hosIdSname = "";
        if (StringUtils.isNotBlank(getOcAccHosId())) {
            hosIdSname += getOcAccHosId();
        }
        if (StringUtils.isNotBlank(getOcAccHpSnam())) {
            hosIdSname += "(" + getOcAccHpSnam() + ")";
        }
        return hosIdSname;
    }

    public String getRehcYmStr() {
        if (StringUtils.isNotBlank(getRehcYm())) {
            return DateUtility.changeWestYearMonthType(getRehcYm());
        }
        else {
            return getRehcYm();
        }
    }

    public String getJudgeDateStr() {
        if (StringUtils.isNotBlank(getJudgeDate())) {
            return DateUtility.changeDateType(getJudgeDate());
        }
        else {
            return getJudgeDate();
        }
    }

    public String getFamAppMkStr() {
        String famAppMkStr = "";
        if ((ConstantKey.BAAPPEXPAND_FAMAPPMK_Y).equals(getFamAppMk())) {
            famAppMkStr = ConstantKey.BAAPPEXPAND_FAMAPPMK_STR_Y;
        }
        return famAppMkStr;
    }

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType;
    }

    public String getCriMedium() {
        return criMedium;
    }

    public void setCriMedium(String criMedium) {
        this.criMedium = criMedium;
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

    public String getCriInIssul() {
        return criInIssul;
    }

    public void setCriInIssul(String criInIssul) {
        this.criInIssul = criInIssul;
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

    public String getHpSnam() {
        return hpSnam;
    }

    public void setHpSnam(String hpSnam) {
        this.hpSnam = hpSnam;
    }

    public String getOcAccHosId() {
        return ocAccHosId;
    }

    public void setOcAccHosId(String ocAccHosId) {
        this.ocAccHosId = ocAccHosId;
    }

    public String getOcAccDoctorName1() {
        return ocAccDoctorName1;
    }

    public void setOcAccDoctorName1(String ocAccDoctorName1) {
        this.ocAccDoctorName1 = ocAccDoctorName1;
    }

    public String getOcAccDoctorName2() {
        return ocAccDoctorName2;
    }

    public void setOcAccDoctorName2(String ocAccDoctorName2) {
        this.ocAccDoctorName2 = ocAccDoctorName2;
    }

    public String getOcAccHpSnam() {
        return ocAccHpSnam;
    }

    public void setOcAccHpSnam(String ocAccHpSnam) {
        this.ocAccHpSnam = ocAccHpSnam;
    }

    public String getOcaccIdentMk() {
        return ocaccIdentMk;
    }

    public void setOcaccIdentMk(String ocaccIdentMk) {
        this.ocaccIdentMk = ocaccIdentMk;
    }

    public String getRehcYm() {
        return rehcYm;
    }

    public void setRehcYm(String rehcYm) {
        this.rehcYm = rehcYm;
    }

    public String getJudgeDate() {
        return judgeDate;
    }

    public void setJudgeDate(String judgeDate) {
        this.judgeDate = judgeDate;
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

    public String getDisQualMk() {
        return disQualMk;
    }

    public void setDisQualMk(String disQualMk) {
        this.disQualMk = disQualMk;
    }

}
