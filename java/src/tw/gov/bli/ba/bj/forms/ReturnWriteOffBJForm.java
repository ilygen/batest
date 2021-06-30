package tw.gov.bli.ba.bj.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 批次處理 - 收回作業 (BABA0M020X)
 * 
 * @author swim
 */
public class ReturnWriteOffBJForm extends BaseValidatorForm {
    private String method;

    private String upTimeBeg;// 處理日期起日
    private String upTimeEnd;// 處理日期迄日
    private String idForConfirm;// 執行批次處理的資料列編號

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUpTimeBeg() {
        return upTimeBeg;
    }

    public void setUpTimeBeg(String upTimeBeg) {
        this.upTimeBeg = upTimeBeg;
    }

    public String getUpTimeEnd() {
        return upTimeEnd;
    }

    public void setUpTimeEnd(String upTimeEnd) {
        this.upTimeEnd = upTimeEnd;
    }

    public String getIdForConfirm() {
        return idForConfirm;
    }

    public void setIdForConfirm(String idForConfirm) {
        this.idForConfirm = idForConfirm;
    }
}
