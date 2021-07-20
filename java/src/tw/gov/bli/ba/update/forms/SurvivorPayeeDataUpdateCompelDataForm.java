package tw.gov.bli.ba.update.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 遺屬年金受款人資料更正 - 不合格年月維護
 * 
 * @author Rickychi
 */
public class SurvivorPayeeDataUpdateCompelDataForm extends BaseValidatorForm {

    private String apNo;
    private String seqNo;
    private String compelNo;// 序號
    private String compelSdate;// 強迫不合格起始年月
    private String compelEdate;// 強迫不合格結束年月
    private String compelDesc;// 強迫不合格原因

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.apNo = "";
        this.seqNo = "";
        this.compelNo = "";
        this.compelSdate = "";
        this.compelEdate = "";
        this.compelDesc = "";
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

    public String getCompelNo() {
        return compelNo;
    }

    public void setCompelNo(String compelNo) {
        this.compelNo = compelNo;
    }

    public String getCompelSdate() {
        return compelSdate;
    }

    public void setCompelSdate(String compelSdate) {
        this.compelSdate = compelSdate;
    }

    public String getCompelEdate() {
        return compelEdate;
    }

    public void setCompelEdate(String compelEdate) {
        this.compelEdate = compelEdate;
    }

    public String getCompelDesc() {
        return compelDesc;
    }

    public void setCompelDesc(String compelDesc) {
        this.compelDesc = compelDesc;
    }
}
