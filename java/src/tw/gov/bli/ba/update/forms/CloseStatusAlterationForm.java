package tw.gov.bli.ba.update.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import tw.gov.bli.ba.util.DateUtility;

/**
 * 更正作業 - 結案狀態變更作業
 * 
 * @author KIYOMI
 */
public class CloseStatusAlterationForm extends BaseValidatorForm {
    private BigDecimal baappbaseId;
    private String benNationTyp;// 國籍別
    private String benNationCode;// 國籍
    private String benIdnNo;// 身分證號
    private String benName;// 姓名
    private String benBrDate;// 出生日期
    private String benSex;// 性別
    private String benEvtRel;// 關係
    private String tel1;// 電話1
    private String tel2;// 電話2
    private String commTyp;// 通訊地址別
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String grdIdnNo;// 法定代理人身分證號
    private String grdName;// 法定代理人姓名
    private String grdBrDate;// 法定代理人出生日期
    private String apNo1;
    private String apNo2;
    private String apNo3;
    private String apNo4;
    private String benNationCodeOption;

    private String oldBenNationTyp;// 國籍別
    private String oldBenNationCode;// 國籍
    private String oldBenIdnNo;// 身分證號
    private String oldBenName;// 姓名
    private String oldBenBrDate;// 出生日期
    private String oldBenSex;// 性別
    private String oldBenEvtRel;// 關係
    private String oldTel1;// 電話1
    private String oldTel2;// 電話2
    private String oldCommTyp;// 通訊地址別
    private String oldCommZip;// 通訊郵遞區號
    private String oldCommAddr;// 通訊地址
    private String oldGrdIdnNo;// 法定代理人身分證號
    private String oldGrdName;// 法定代理人姓名
    private String oldGrdBrDate;// 法定代理人出生日期
    private String oldBenNationCodeOption;

    private String idForConfirm;// 是否出現 checkBox
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String evtName;// 事故者姓名
    private String evtIdnNo;// 事故者身分證號
    private String procStat;// 處理狀態
    private String issuYm;// 核定年月
    private String caseTyp;// 案件類別

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        apNo1 = "";
        apNo2 = "";
        apNo3 = "";
        apNo4 = "";
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getBenNationCode() {
        return benNationCode;
    }

    public void setBenNationCode(String benNationCode) {
        this.benNationCode = benNationCode;
    }

    public String getBenNationTyp() {
        return benNationTyp;
    }

    public void setBenNationTyp(String benNationTyp) {
        this.benNationTyp = benNationTyp;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenBrDate() {
        // if(benBrDate!= null &&benBrDate.length()<8){
        // return DateUtility.changeDateType(benBrDate);
        // }
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenSex() {
        return benSex;
    }

    public void setBenSex(String benSex) {
        this.benSex = benSex;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getCommTyp() {
        return commTyp;
    }

    public void setCommTyp(String commTyp) {
        this.commTyp = commTyp;
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

    public String getGrdIdnNo() {
        return grdIdnNo;
    }

    public void setGrdIdnNo(String grdIdnNo) {
        this.grdIdnNo = grdIdnNo;
    }

    public String getGrdName() {
        return grdName;
    }

    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    public String getGrdBrDate() {
        if (grdBrDate != null && grdBrDate.length() < 8) {
            return DateUtility.changeDateType(grdBrDate);
        }
        return grdBrDate;
    }

    public void setGrdBrDate(String grdBrDate) {
        this.grdBrDate = grdBrDate;
    }

    public String getApNo1() {
        return apNo1;
    }

    public void setApNo1(String apNo1) {
        this.apNo1 = apNo1;
    }

    public String getApNo2() {
        return apNo2;
    }

    public void setApNo2(String apNo2) {
        this.apNo2 = apNo2;
    }

    public String getApNo3() {
        return apNo3;
    }

    public void setApNo3(String apNo3) {
        this.apNo3 = apNo3;
    }

    public String getApNo4() {
        return apNo4;
    }

    public void setApNo4(String apNo4) {
        this.apNo4 = apNo4;
    }

    public String getBenNationCodeOption() {
        return benNationCodeOption;
    }

    public void setBenNationCodeOption(String benNationCodeOption) {
        this.benNationCodeOption = benNationCodeOption;
    }

    public String getOldBenNationTyp() {
        return oldBenNationTyp;
    }

    public void setOldBenNationTyp(String oldBenNationTyp) {
        this.oldBenNationTyp = oldBenNationTyp;
    }

    public String getOldBenNationCode() {
        return oldBenNationCode;
    }

    public void setOldBenNationCode(String oldBenNationCode) {
        this.oldBenNationCode = oldBenNationCode;
    }

    public String getOldBenIdnNo() {
        return oldBenIdnNo;
    }

    public void setOldBenIdnNo(String oldBenIdnNo) {
        this.oldBenIdnNo = oldBenIdnNo;
    }

    public String getOldBenName() {
        return oldBenName;
    }

    public void setOldBenName(String oldBenName) {
        this.oldBenName = oldBenName;
    }

    public String getOldBenBrDate() {
        return oldBenBrDate;
    }

    public void setOldBenBrDate(String oldBenBrDate) {
        this.oldBenBrDate = oldBenBrDate;
    }

    public String getOldBenSex() {
        return oldBenSex;
    }

    public void setOldBenSex(String oldBenSex) {
        this.oldBenSex = oldBenSex;
    }

    public String getOldBenEvtRel() {
        return oldBenEvtRel;
    }

    public void setOldBenEvtRel(String oldBenEvtRel) {
        this.oldBenEvtRel = oldBenEvtRel;
    }

    public String getOldTel1() {
        return oldTel1;
    }

    public void setOldTel1(String oldTel1) {
        this.oldTel1 = oldTel1;
    }

    public String getOldTel2() {
        return oldTel2;
    }

    public void setOldTel2(String oldTel2) {
        this.oldTel2 = oldTel2;
    }

    public String getOldCommTyp() {
        return oldCommTyp;
    }

    public void setOldCommTyp(String oldCommTyp) {
        this.oldCommTyp = oldCommTyp;
    }

    public String getOldCommZip() {
        return oldCommZip;
    }

    public void setOldCommZip(String oldCommZip) {
        this.oldCommZip = oldCommZip;
    }

    public String getOldCommAddr() {
        return oldCommAddr;
    }

    public void setOldCommAddr(String oldCommAddr) {
        this.oldCommAddr = oldCommAddr;
    }

    public String getOldGrdIdnNo() {
        return oldGrdIdnNo;
    }

    public void setOldGrdIdnNo(String oldGrdIdnNo) {
        this.oldGrdIdnNo = oldGrdIdnNo;
    }

    public String getOldGrdName() {
        return oldGrdName;
    }

    public void setOldGrdName(String oldGrdName) {
        this.oldGrdName = oldGrdName;
    }

    public String getOldGrdBrDate() {
        return oldGrdBrDate;
    }

    public void setOldGrdBrDate(String oldGrdBrDate) {
        this.oldGrdBrDate = oldGrdBrDate;
    }

    public String getOldBenNationCodeOption() {
        return oldBenNationCodeOption;
    }

    public void setOldBenNationCodeOption(String oldBenNationCodeOption) {
        this.oldBenNationCodeOption = oldBenNationCodeOption;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getIdForConfirm() {
        return idForConfirm;
    }

    public void setIdForConfirm(String idForConfirm) {
        this.idForConfirm = idForConfirm;
    }
}
