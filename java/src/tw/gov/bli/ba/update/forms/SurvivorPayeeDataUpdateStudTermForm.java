package tw.gov.bli.ba.update.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Action Form: 遺屬年金受款人資料更正 - 在學期間維護
 *
 * User: Chris
 * Date: 2009/10/26
 * Time: 下午 03:05:37
 */
public class SurvivorPayeeDataUpdateStudTermForm extends BaseValidatorForm {

    private String apNo;
    private String seqNo;
    private String termNo;
    private String studSdate;// 在學起始年月
    private String studEdate;// 在學結束年月

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

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.apNo = "";
        this.seqNo = "";
        this.termNo = "";
        this.studSdate = "";
        this.studEdate = "";
    }
}
