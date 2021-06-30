package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 穿透案件統計表
 * 
 * @author Kiyomi
 */
public class OtherRpt10Case implements Serializable {

    private String apNo;// 受理編號
    private String caseTyp;// 案件類別
    private String chkMan;// 審核人員
    private String seqNo;// 序號
    
    private String apNo1;// 受理編號
    private String caseTyp1;// 案件類別
    private String seqNo1;// 序號    

    /**
     * 案件類別
     */
    public String getCaseTypString() {
        if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        else if ((ConstantKey.BAAPPBASE_CASETYP_A).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_A;
        else if ((ConstantKey.BAAPPBASE_CASETYP_B).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_B;
        else if ((ConstantKey.BAAPPBASE_CASETYP_C).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_C;
        else if ((ConstantKey.BAAPPBASE_CASETYP_D).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_D;
        else
            return "";
    }
    
    public String getCaseTypString1() {
        if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(getCaseTyp1()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(getCaseTyp1()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(getCaseTyp1()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(getCaseTyp1()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(getCaseTyp1()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(getCaseTyp1()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        else if ((ConstantKey.BAAPPBASE_CASETYP_A).equals(getCaseTyp1()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_A;
        else if ((ConstantKey.BAAPPBASE_CASETYP_B).equals(getCaseTyp1()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_B;
        else if ((ConstantKey.BAAPPBASE_CASETYP_C).equals(getCaseTyp1()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_C;
        else if ((ConstantKey.BAAPPBASE_CASETYP_D).equals(getCaseTyp1()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_D;
        else
            return "";
    }    

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }
    
    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    
    public String getApNo1() {
        return apNo1;
    }

    public void setApNo1(String apNo1) {
        this.apNo1 = apNo1;
    }

    public String getCaseTyp1() {
        return caseTyp1;
    }

    public void setCaseTyp1(String caseTyp1) {
        this.caseTyp1 = caseTyp1;
    }
    
    public String getSeqNo1() {
        return seqNo1;
    }

    public void setSeqNo1(String seqNo1) {
        this.seqNo1 = seqNo1;
    }      
    

}
