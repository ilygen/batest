package tw.gov.bli.ba.maint.forms;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import tw.gov.bli.ba.maint.cases.DeductItemMaintCase;

/**
 * 維護作業 - 扣減參數維護作業 (bapa0x051a.jsp)
 * 
 * @author yuyin
 */
public class DeductItemMaintDetailForm extends BaseValidatorForm {
    private String method;

    private String payCode; // 給付種類
    private String cutTyp[]; // 扣減項目代碼
    private String paramName; // 扣減項目名稱
    private String cutSeq[]; // 扣減順位
    private List<DeductItemMaintCase> data;

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public void resetForm() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<DeductItemMaintCase> getData() {
        return data;
    }

    public void setData(List<DeductItemMaintCase> data) {
        this.data = data;
    }

    public String[] getCutSeq() {
        return cutSeq;
    }

    public void setCutSeq(String[] cutSeq) {
        this.cutSeq = cutSeq;
    }

    public String[] getCutTyp() {
        return cutTyp;
    }

    public void setCutTyp(String[] cutTyp) {
        this.cutTyp = cutTyp;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

}
