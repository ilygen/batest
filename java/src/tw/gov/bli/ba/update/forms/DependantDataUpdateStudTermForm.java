package tw.gov.bli.ba.update.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 眷屬資料更正 (BAMO0D074C)
 * 
 * @author Evelyn Hsu
 */

public class DependantDataUpdateStudTermForm extends DependantDataUpdateDetailForm {

    private String method;
    private String apNo;
    private String seqNo;
    private String termNo;
    private String studSdate;// 在學起始年月
    private String studEdate;// 在學結束年月
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.apNo = "";
        this.seqNo = "";
        this.termNo = "";
        this.studSdate = "";
        this.studEdate = "";
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
    public String getTermNo() {
        return termNo;
    }
    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }
    public String getStudSdate() {
        return studSdate;
    }
    public void setStudSdate(String studSdate) {
        this.studSdate = studSdate;
    }
    public String getStudEdate() {
        return studEdate;
    }
    public void setStudEdate(String studEdate) {
        this.studEdate = studEdate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    
    
    
}
