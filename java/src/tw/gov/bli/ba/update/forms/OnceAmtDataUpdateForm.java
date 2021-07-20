package tw.gov.bli.ba.update.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 案件資料更正 - 一次給付資料更正(BAMO0D032C)
 * 
 * @author Rickychi
 */
public class OnceAmtDataUpdateForm extends BaseValidatorForm {
    private String method;

    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String owesMk;// 欠費註記
    private String owesBdate;// 欠費期間起
    private String owesEdate;// 欠費期間迄
    private String oldSeniab;// 年資採計方式
    private String lsUbno;// 最後單位保險證號
    private String lsUbnoCk;// 最後單位保險證號檢查碼
    private String reSeniMk;// 轉公保註記
    private String offInsurDate;// 轉公保日期
    private String lawRetireDate;// 依法退休日期

    private String dabApNo;// 已領失能年金受理編號
    private String dabApNo1;// 已領失能年金受理編號-1
    private String dabApNo2;// 已領失能年金受理編號-2
    private String dabApNo3;// 已領失能年金受理編號-3
    private String dabApNo4;// 已領失能年金受理編號-4

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public String getLsUbno() {
        return lsUbno;
    }

    public void setLsUbno(String lsUbno) {
        this.lsUbno = lsUbno;
    }

    public String getLsUbnoCk() {
        return lsUbnoCk;
    }

    public void setLsUbnoCk(String lsUbnoCk) {
        this.lsUbnoCk = lsUbnoCk;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public String getOwesBdate() {
        return owesBdate;
    }

    public void setOwesBdate(String owesBdate) {
        this.owesBdate = owesBdate;
    }

    public String getOwesEdate() {
        return owesEdate;
    }

    public void setOwesEdate(String owesEdate) {
        this.owesEdate = owesEdate;
    }

    public String getOldSeniab() {
        return oldSeniab;
    }

    public void setOldSeniab(String oldSeniab) {
        this.oldSeniab = oldSeniab;
    }

    public String getOwesMk() {
        return owesMk;
    }

    public void setOwesMk(String owesMk) {
        this.owesMk = owesMk;
    }

    public String getReSeniMk() {
        return reSeniMk;
    }

    public void setReSeniMk(String reSeniMk) {
        this.reSeniMk = reSeniMk;
    }

    public String getOffInsurDate() {
        return offInsurDate;
    }

    public void setOffInsurDate(String offInsurDate) {
        this.offInsurDate = offInsurDate;
    }

    public String getLawRetireDate() {
        return lawRetireDate;
    }

    public void setLawRetireDate(String lawRetireDate) {
        this.lawRetireDate = lawRetireDate;
    }

    public String getDabApNo() {
        return getDabApNo1() + getDabApNo2() + getDabApNo3() + getDabApNo4();
    }

    public void setDabApNo(String dabApNo) {
        this.dabApNo = dabApNo;
    }

    public String getDabApNo1() {
        return dabApNo1;
    }

    public void setDabApNo1(String dabApNo1) {
        this.dabApNo1 = dabApNo1;
    }

    public String getDabApNo2() {
        return dabApNo2;
    }

    public void setDabApNo2(String dabApNo2) {
        this.dabApNo2 = dabApNo2;
    }

    public String getDabApNo3() {
        return dabApNo3;
    }

    public void setDabApNo3(String dabApNo3) {
        this.dabApNo3 = dabApNo3;
    }

    public String getDabApNo4() {
        return dabApNo4;
    }

    public void setDabApNo4(String dabApNo4) {
        this.dabApNo4 = dabApNo4;
    }
}
