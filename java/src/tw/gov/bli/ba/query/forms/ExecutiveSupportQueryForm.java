package tw.gov.bli.ba.query.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 查詢作業 - 行政支援查詢 - 查詢頁面 (baiq0d050q.jsp)
 * 
 * @author jerry
 */
public class ExecutiveSupportQueryForm extends BaseValidatorForm {
    private String apNo1;
    private String apNo2;
    private String apNo3;
    private String apNo4;
    private String idNo;
    private String issuYm;
    private String qryFromWhere;
    private String apNo;

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
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

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getQryFromWhere() {
        return qryFromWhere;
    }

    public void setQryFromWhere(String qryFromWhere) {
        this.qryFromWhere = qryFromWhere;
    }

}
