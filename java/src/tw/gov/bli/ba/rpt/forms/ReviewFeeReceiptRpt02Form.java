package tw.gov.bli.ba.rpt.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 複檢費用申請作業 - 勞保失能年金複檢費用審核給付清單 - 查詢頁面 (bare0d041l.jsp)
 * 
 * @author Evelyn Hsu
 */


public class ReviewFeeReceiptRpt02Form extends BaseValidatorForm{
    
    private String method;

    private String apNo1; // 受理編號 - 第一欄 ( 1碼 )
    private String apNo2; // 受理編號 - 第二欄 ( 1碼 )
    private String apNo3; // 受理編號 - 第三欄 ( 5碼 )
    private String apNo4; // 受理編號 - 第四欄 ( 5碼 )
    private String apSeq; // 複檢費用申請序
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        
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

    public String getApSeq() {
        return apSeq;
    }

    public void setApSeq(String apSeq) {
        this.apSeq = apSeq;
    }
    
    

}
