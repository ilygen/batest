package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 給付延伸主檔 (<code>BAAPPEXPAND</code>)
 * 
 * @author Evelyn Hsu
 */
@Table("BBPMAK")
public class Bbpmak implements Serializable {

    @LogField("APNO")
    private String apNo;// 受理編號

    @LogField("SEQNO")
    private String seqNo;// 序號

    @LogField("CANCELMK")
    private String cancelMk;// 撤銷註記

    @LogField("CANCELYM")
    private String cancelYm;// 撤銷請領年月

    @LogField("OCACCIDENTMK")
    private String ocaccIdentMk;// 符合第20條之1註記

    @LogField("PRTYPE")
    private String prType;// 先核普通

    @LogField("EVTYP")
    private String evTyp;// 傷病分類

    @LogField("EVCODE")
    private String evCode;// 傷病原因

    @LogField("CRIINPART1")
    private String criInPart1;// 受傷部位1

    @LogField("CRIINPART2")
    private String criInPart2;// 受傷部位2

    @LogField("CRIINPART3")
    private String criInPart3;// 受傷部位3

    @LogField("CRIMEDIUM")
    private String criMedium;// 媒介物

    @LogField("CRIINJNME1")
    private String criInJnme1;// 國際疾病代碼1

    @LogField("CRIINJNME2")
    private String criInJnme2;// 國際疾病代碼2

    @LogField("CRIINJNME3")
    private String criInJnme3;// 國際疾病代碼3

    @LogField("CRIINJNME4")
    private String criInJnme4;// 國際疾病代碼4

    @LogField("CRIINJCL1")
    private String criInJcl1;// 失能等級1

    @LogField("CRIINJCL2")
    private String criInJcl2;// 失能等級2

    @LogField("CRIINJCL3")
    private String criInJcl3;// 失能等級3

    @LogField("CRIINJDP1")
    private String criInJdp1;// 失能項目1

    @LogField("CRIINJDP2")
    private String criInJdp2;// 失能項目2

    @LogField("CRIINJDP3")
    private String criInJdp3;// 失能項目3

    @LogField("CRIINJDP4")
    private String criInJdp4;// 失能項目4

    @LogField("CRIINJDP5")
    private String criInJdp5;// 失能項目5

    @LogField("CRIINJDP6")
    private String criInJdp6;// 失能項目6

    @LogField("CRIINJDP7")
    private String criInJdp7;// 失能項目7

    @LogField("CRIINJDP8")
    private String criInJdp8;// 失能項目8

    @LogField("CRIINJDP9")
    private String criInJdp9;// 失能項目9

    @LogField("CRIINJDP10")
    private String criInJdp10;// 失能項目10

    @LogField("CRIINISSUL")
    private String criInIssul;// 核定等級

    @LogField("REHCMK")
    private String rehcMk;// 重新查核失能程度註記

    @LogField("REHCYM")
    private String rehcYm;// 重新查核失能程度年月

    @LogField("HOSID")
    private String hosId;// 醫療院所代碼

    @LogField("DOCTORNAME1")
    private String doctorName1;// 醫師姓名1

    @LogField("DOCTORNAME2")
    private String doctorName2;// 醫師姓名2
    
    @LogField("OCACCHOSID")
    private String ocAccHosId;// 職病醫療院所代碼

    @LogField("OCACCDOCTORNAME1")
    private String ocAccDoctorName1;// 職病醫師姓名1

    @LogField("OCACCDOCTORNAME2")
    private String ocAccDoctorName2;// 職病醫師姓名2    

    @LogField("OCACCADDAMT")
    private BigDecimal ocAccaddAmt;// 己領職災增給金額

    @LogField("PRFMLYMK")
    private String prFmlyMk;// 有無前一順位親屬註記

    @LogField("MONINCOMEMK")
    private String monIncomeMk;// 每月工作收入註記

    @LogField("MONINCOME")
    private BigDecimal monIncome;// 每月工作收入

    @LogField("FAMAPPMK")
    private String famAppMk;// 符合第63條之1第3項註記

    @LogField("STUDMK")
    private String studMk;// 在學註記

    @LogField("MARRYDATE")
    private String marryDate;// 結婚日期

    @LogField("DIGAMYDATE")
    private String digamyDate;// 再婚日期

    @LogField("ABANAPPLYMK")
    private String abanApplyMk;// 放棄請領

    @LogField("ABANAPSYM")
    private String abanApsYm;// 放棄請領起始年月

    @LogField("ABLEAPSYM")
    private String ableApsYm;// 得請領起始年月

    @LogField("RAISECHILDMK")
    private String raiseChildMk;// 配偶扶養

    @LogField("HANDICAPMK")
    private String handIcapMk;// 領有重度以上身心障礙手冊或證明

    @LogField("INTERDICTMK")
    private String interDictMk;// 受禁治產(監護)宣告註記

    @LogField("INTERDICTDATE")
    private String interDictDate;// 受禁治產(監護)宣告 - 宣告日期

    @LogField("REPEALINTERDICTDATE")
    private String repealInterdictDate;// 受禁治產(監護)宣告 - 撤銷日期

    @LogField("RELATCHGDATE")
    private String relatChgDate;// 親屬關係變動日期

    @LogField("JUDGEDATE")
    private String judgeDate;// 判決日期

    @LogField("BENMISSINGSDATE")
    private String benMissingSdate;// 受益人失蹤期間(起)

    @LogField("BENMISSINGEDATE")
    private String benMissingEdate;// 受益人失蹤期間(迄)

    @LogField("PRISONSDATE")
    private String prisonSdate;// 監管期間(起)

    @LogField("PRISONEDATE")
    private String prisonEdate;// 監管期間(迄)

    @LogField("ADOPTDATE")
    private String adoPtDate;// 收養日期

    @LogField("ASSIGNIDNNO")
    private String assignIdnNo;// 代辦人身分證號

    @LogField("ASSIGNNAME")
    private String assignName;// 代辦人姓名

    @LogField("ASSIGNBRDATE")
    private String assignBrDate;// 代辦人出生日期

    @LogField("RAISEEVTMK")
    private String raiseEvtMk;// 被保險人扶養

    @LogField("DEDUCTDAY")
    private BigDecimal deductDay; // 扣除天數

    @LogField("SAVINGMK")
    private String savingMk; // 計息存儲

    @LogField("CRTUSER")
    private String crtUser;// 新增者代號

    @LogField("CRTTIME")
    private String crtTime;// 新增日期時間

    @LogField("UPDUSER")
    private String updUser;// 異動者代號

    @LogField("UPDTIME")
    private String updTime;// 異動日期時間

    @LogField("ADWKMK")
    private String adWkMk; // 加職註記
    
    @LogField("NLWKMK")
    private String nlWkMk; // 普職註記

    @LogField("EVAPPTYP")
    private String evAppTyp; // 申請傷病分類

    @LogField("COMPELMK")
    private String compelMk; // 強迫不合格註記
    
    @LogField("COMNPMK")
    private String comnpMk; // 併計國保年資
    
    @LogField("DISQUALMK")
    private String disQualMk; //年金請領資格
    
    @LogField("SCHOOLCODE")
    private String schoolCode; //年金請領資格
    
    @LogField("MONNOTIFYINGMK")
    private String monNotifyingMk; //寄發月通知表   

    // Fields not in BAAPPEXPAND
    // [
    private String evTypName; // 傷病分類 - 中文 (取自 BAPARAM)

    // ]
    
    //失能等級
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
    
    //失能項目
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

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getCancelMk() {
        return cancelMk;
    }

    public void setCancelMk(String cancelMk) {
        this.cancelMk = cancelMk;
    }

    public String getCancelYm() {
        return cancelYm;
    }

    public void setCancelYm(String cancelYm) {
        this.cancelYm = cancelYm;
    }

    public String getOcaccIdentMk() {
        return ocaccIdentMk;
    }

    public void setOcaccIdentMk(String ocaccIdentMk) {
        this.ocaccIdentMk = ocaccIdentMk;
    }

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType;
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

    public String getCriMedium() {
        return criMedium;
    }

    public void setCriMedium(String criMedium) {
        this.criMedium = criMedium;
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

    public String getRehcMk() {
        return rehcMk;
    }

    public void setRehcMk(String rehcMk) {
        this.rehcMk = rehcMk;
    }

    public String getRehcYm() {
        return rehcYm;
    }

    public void setRehcYm(String rehcYm) {
        this.rehcYm = rehcYm;
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
    
    public String getPrFmlyMk() {
        return prFmlyMk;
    }

    public void setPrFmlyMk(String prFmlyMk) {
        this.prFmlyMk = prFmlyMk;
    }

    public String getMonIncomeMk() {
        return monIncomeMk;
    }

    public void setMonIncomeMk(String monIncomeMk) {
        this.monIncomeMk = monIncomeMk;
    }

    public BigDecimal getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(BigDecimal monIncome) {
        this.monIncome = monIncome;
    }

    public String getFamAppMk() {
        return famAppMk;
    }

    public void setFamAppMk(String famAppMk) {
        this.famAppMk = famAppMk;
    }

    public String getStudMk() {
        return studMk;
    }

    public void setStudMk(String studMk) {
        this.studMk = studMk;
    }

    public String getMarryDate() {
        return marryDate;
    }

    public void setMarryDate(String marryDate) {
        this.marryDate = marryDate;
    }

    public String getDigamyDate() {
        return digamyDate;
    }

    public void setDigamyDate(String digamyDate) {
        this.digamyDate = digamyDate;
    }

    public String getAbanApplyMk() {
        return abanApplyMk;
    }

    public void setAbanApplyMk(String abanApplyMk) {
        this.abanApplyMk = abanApplyMk;
    }

    public String getAbanApsYm() {
        return abanApsYm;
    }

    public void setAbanApsYm(String abanApsYm) {
        this.abanApsYm = abanApsYm;
    }

    public String getRaiseChildMk() {
        return raiseChildMk;
    }

    public void setRaiseChildMk(String raiseChildMk) {
        this.raiseChildMk = raiseChildMk;
    }

    public String getHandIcapMk() {
        return handIcapMk;
    }

    public void setHandIcapMk(String handIcapMk) {
        this.handIcapMk = handIcapMk;
    }

    public String getInterDictMk() {
        return interDictMk;
    }

    public void setInterDictMk(String interDictMk) {
        this.interDictMk = interDictMk;
    }

    public String getInterDictDate() {
        return interDictDate;
    }

    public void setInterDictDate(String interDictDate) {
        this.interDictDate = interDictDate;
    }

    public String getRepealInterdictDate() {
        return repealInterdictDate;
    }

    public void setRepealInterdictDate(String repealInterdictDate) {
        this.repealInterdictDate = repealInterdictDate;
    }

    public String getRelatChgDate() {
        return relatChgDate;
    }

    public void setRelatChgDate(String relatChgDate) {
        this.relatChgDate = relatChgDate;
    }

    public String getJudgeDate() {
        return judgeDate;
    }

    public void setJudgeDate(String judgeDate) {
        this.judgeDate = judgeDate;
    }

    public String getBenMissingSdate() {
        return benMissingSdate;
    }

    public void setBenMissingSdate(String benMissingSdate) {
        this.benMissingSdate = benMissingSdate;
    }

    public String getBenMissingEdate() {
        return benMissingEdate;
    }

    public void setBenMissingEdate(String benMissingEdate) {
        this.benMissingEdate = benMissingEdate;
    }

    public String getPrisonSdate() {
        return prisonSdate;
    }

    public void setPrisonSdate(String prisonSdate) {
        this.prisonSdate = prisonSdate;
    }

    public String getPrisonEdate() {
        return prisonEdate;
    }

    public void setPrisonEdate(String prisonEdate) {
        this.prisonEdate = prisonEdate;
    }

    public String getAdoPtDate() {
        return adoPtDate;
    }

    public void setAdoPtDate(String adoPtDate) {
        this.adoPtDate = adoPtDate;
    }

    public String getAssignIdnNo() {
        return assignIdnNo;
    }

    public void setAssignIdnNo(String assignIdnNo) {
        this.assignIdnNo = assignIdnNo;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    public String getAssignBrDate() {
        return assignBrDate;
    }

    public void setAssignBrDate(String assignBrDate) {
        this.assignBrDate = assignBrDate;
    }

    public String getRaiseEvtMk() {
        return raiseEvtMk;
    }

    public void setRaiseEvtMk(String raiseEvtMk) {
        this.raiseEvtMk = raiseEvtMk;
    }

    public String getSavingMk() {
        return savingMk;
    }

    public void setSavingMk(String savingMk) {
        this.savingMk = savingMk;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public BigDecimal getOcAccaddAmt() {
        return ocAccaddAmt;
    }

    public void setOcAccaddAmt(BigDecimal ocAccaddAmt) {
        this.ocAccaddAmt = ocAccaddAmt;
    }

    public String getAbleApsYm() {
        return ableApsYm;
    }

    public void setAbleApsYm(String ableApsYm) {
        this.ableApsYm = ableApsYm;
    }

    public String getEvTypName() {
        return evTypName;
    }

    public void setEvTypName(String evTypName) {
        this.evTypName = evTypName;
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

    public String getNlWkMk() {
        return nlWkMk;
    }

    public void setNlWkMk(String nlWkMk) {
        this.nlWkMk = nlWkMk;
    }

    public String getEvAppTyp() {
        return evAppTyp;
    }

    public void setEvAppTyp(String evAppTyp) {
        this.evAppTyp = evAppTyp;
    }

    public String getCompelMk() {
        return compelMk;
    }

    public void setCompelMk(String compelMk) {
        this.compelMk = compelMk;
    }

    public String getComnpMk() {
        return comnpMk;
    }

    public void setComnpMk(String comnpMk) {
        this.comnpMk = comnpMk;
    }

	public String getDisQualMk() {
		return disQualMk;
	}

	public void setDisQualMk(String disQualMk) {
		this.disQualMk = disQualMk;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	
    public String getMonNotifyingMk() {
        return monNotifyingMk;
    }

    public void setMonNotifyingMk(String monNotifyingMk) {
        this.monNotifyingMk = monNotifyingMk;
    }	

}
