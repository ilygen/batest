package tw.gov.bli.ba.update.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整 (bamo0d260c.jsp bamo0d261c.jsp)
 * 
 * @author Goston
 */
public class SurvivorCheckMarkLevelAdjustForm extends BaseValidatorForm {
    private boolean initialed;

    private String method;

    private String apNo1; // 受理編號 - 第一欄 ( 1 碼 )
    private String apNo2; // 受理編號 - 第二欄 ( 1 碼 )
    private String apNo3; // 受理編號 - 第三欄 ( 5 碼 )
    private String apNo4; // 受理編號 - 第四欄 ( 5 碼 )
    private String payYmOption; // 給付年月下拉選單值
    private String seqNoOption; // 受款人下拉選單值

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public void resetForm(HttpServletRequest request) {
        if (!initialed) {
            initialed = true;
        }
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

    public String getPayYmOption() {
        return payYmOption;
    }

    public void setPayYmOption(String payYmOption) {
        this.payYmOption = payYmOption;
    }

    public String getSeqNoOption() {
        return seqNoOption;
    }

    public void setSeqNoOption(String seqNoOption) {
        this.seqNoOption = seqNoOption;
    }
}
