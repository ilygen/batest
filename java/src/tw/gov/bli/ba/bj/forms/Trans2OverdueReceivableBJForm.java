package tw.gov.bli.ba.bj.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 批次處理 - 轉催收作業 (BABA0M030X)
 * 
 * @author Rickychi
 */
public class Trans2OverdueReceivableBJForm extends BaseValidatorForm {
    private String method;

    private String unacpSdate;// 應收立帳起日
    private String unacpEdate;// 應收立帳迄日

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUnacpSdate() {
        return unacpSdate;
    }

    public void setUnacpSdate(String unacpSdate) {
        this.unacpSdate = unacpSdate;
    }

    public String getUnacpEdate() {
        return unacpEdate;
    }

    public void setUnacpEdate(String unacpEdate) {
        this.unacpEdate = unacpEdate;
    }

}
