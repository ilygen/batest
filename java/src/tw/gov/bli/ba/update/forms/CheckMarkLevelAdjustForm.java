package tw.gov.bli.ba.update.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class CheckMarkLevelAdjustForm extends BaseValidatorForm {
    private String apNo1;
    private String apNo2;
    private String apNo3;
    private String apNo4;
    private String orderBy;
    private String baChkFileIdW;
    private String baChkFileIdO;
    private String regetCipbMk;
    private String clearCloseDate;

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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getBaChkFileIdW() {
        return baChkFileIdW;
    }

    public void setBaChkFileIdW(String baChkFileIdW) {
        this.baChkFileIdW = baChkFileIdW;
    }

    public String getBaChkFileIdO() {
        return baChkFileIdO;
    }

    public void setBaChkFileIdO(String baChkFileIdO) {
        this.baChkFileIdO = baChkFileIdO;
    }

    public String getRegetCipbMk() {
        return regetCipbMk;
    }

    public void setRegetCipbMk(String regetCipbMk) {
        this.regetCipbMk = regetCipbMk;
    }

    public String getClearCloseDate() {
        return clearCloseDate;
    }

    public void setClearCloseDate(String clearCloseDate) {
        this.clearCloseDate = clearCloseDate;
    }
}
