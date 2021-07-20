package tw.gov.bli.ba.reviewfee.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 複檢費用 - 複檢費用審核作業 (bare0d020a.jsp bare0d021a.jsp bare0d022a.jsp)
 * 
 * @author Goston
 */
public class ReviewFeeReviewForm extends BaseValidatorForm {
    private boolean initialed;

    private String method;

    // Page Query
    private String apNo1; // 受理編號 - 第一欄 ( 1 碼 )
    private String apNo2; // 受理編號 - 第二欄 ( 1 碼 )
    private String apNo3; // 受理編號 - 第三欄 ( 5 碼 )
    private String apNo4; // 受理編號 - 第四欄 ( 5 碼 )

    // Page List
    private BigDecimal listApSeq; // 複檢費用申請序

    // Page Detail
    private String reApNo; // 複檢費用受理編號
    private BigDecimal apSeq; // 複檢費用申請序

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public void resetForm(HttpServletRequest request) {
        if (!initialed) {
            clearFields();
            initialed = true;
        }
    }

    public void clearFields() {
        reApNo = null; // 複檢費用受理編號
        apSeq = null; // 複檢費用申請序
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetForm(request);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public BigDecimal getListApSeq() {
        return listApSeq;
    }

    public void setListApSeq(BigDecimal listApSeq) {
        this.listApSeq = listApSeq;
    }

    public String getReApNo() {
        return reApNo;
    }

    public void setReApNo(String reApNo) {
        this.reApNo = reApNo;
    }

    public BigDecimal getApSeq() {
        return apSeq;
    }

    public void setApSeq(BigDecimal apSeq) {
        this.apSeq = apSeq;
    }

}
