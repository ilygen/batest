package tw.gov.bli.ba.update.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 通訊資料更正 (BAMO0D010C)
 * 
 * @author Rickychi
 */
public class CommunicationDataUpdateForm extends BaseValidatorForm {
    private String method;

    private BigDecimal baappbaseId;// 資料列編號
    private String apNo;// 受理編號
    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    private String benIdnNo;// 受益人身分證號
    private String benBrDate;// 受益人出生日期
    private String tel1;// 電話1
    private String tel2;// 電話2
    private String commTyp;// 通訊地址別
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String cvlDtlCommZip;// 通訊郵遞區號(戶籍)
    private String cvlDtlCommAddr;// 通訊地址(戶籍)

    private String oldTel1;// 電話1
    private String oldTel2;// 電話2
    private String oldCommTyp;// 通訊地址別
    private String oldCommZip;// 通訊郵遞區號
    private String oldCommAddr;// 通訊地址

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public String getApNoStr() {
        return getApNo1() + getApNo2() + getApNo3() + getApNo4();
    }

    public String getApNoStrDisplay() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
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

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenBrDate() {
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public String getCvlDtlCommZip() {
        return cvlDtlCommZip;
    }

    public void setCvlDtlCommZip(String cvlDtlCommZip) {
        this.cvlDtlCommZip = cvlDtlCommZip;
    }

    public String getCvlDtlCommAddr() {
        return cvlDtlCommAddr;
    }

    public void setCvlDtlCommAddr(String cvlDtlCommAddr) {
        this.cvlDtlCommAddr = cvlDtlCommAddr;
    }
}
