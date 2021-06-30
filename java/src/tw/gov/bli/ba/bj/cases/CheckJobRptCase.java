package tw.gov.bli.ba.bj.cases;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;

/**
 * case for 產生檢核案件作業
 * 
 * @author Noctis
 */
public class CheckJobRptCase {
	
	private String apNo; //受理編號
	private String mchkTyp; //月核案件類別
	private String payYm; //給付年月
	private BigDecimal oldRate; //展延 減額比率
	private BigDecimal befIssueAmt; //總核定金額
	private String payKind; //給付種類
	private String benName; //受益人姓名
	private BigDecimal lastIssueAmt; //前次核定金額
	
	// 核付分類
    public String getMchkTypStr() {
        if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        else if((ConstantKey.BAAPPBASE_CASETYP_A).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_A;
        else if((ConstantKey.BAAPPBASE_CASETYP_B).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_B;
        else if((ConstantKey.BAAPPBASE_CASETYP_C).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_C;
        else if((ConstantKey.BAAPPBASE_CASETYP_D).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_D;
        else if((ConstantKey.BAAPPBASE_CASETYP_E).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_E;
        else if((ConstantKey.BAAPPBASE_CASETYP_F).equals(getMchkTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_F;
        else
            return ""; 
    }
    
    public String getPayKindStr() {
        String payKindStr = "";
        if (StringUtils.isNotBlank(getPayKind())) {
            // 轉換 給付種類
            if ((ConstantKey.BAAPPBASE_PAYKIND_35).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_35;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_36).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_36;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_37).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_37;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_38).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_38;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_39).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_39;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_45).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_45;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_48).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_48;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_49).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_49;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_55).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_55;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_56).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_56;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_57).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_57;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_58).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_58;
            }
            else if ((ConstantKey.BAAPPBASE_PAYKIND_59).equals(getPayKind())) {
                payKindStr = ConstantKey.BAAPPBASE_PAYKIND_STR_59;
            }
        }
        return payKindStr;
    }
	
	public String getApNo() {
		return apNo;
	}
	public void setApNo(String apNo) {
		this.apNo = apNo;
	}
	public String getMchkTyp() {
		return mchkTyp;
	}
	public void setMchkTyp(String mchkTyp) {
		this.mchkTyp = mchkTyp;
	}
	public String getPayYm() {
		return payYm;
	}
	public void setPayYm(String payYm) {
		this.payYm = payYm;
	}
	public BigDecimal getOldRate() {
		return oldRate;
	}
	public void setOldRate(BigDecimal oldRate) {
		this.oldRate = oldRate;
	}
	public BigDecimal getBefIssueAmt() {
		return befIssueAmt;
	}
	public void setBefIssueAmt(BigDecimal befIssueAmt) {
		this.befIssueAmt = befIssueAmt;
	}
	public String getPayKind() {
		return payKind;
	}
	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}
	public String getBenName() {
		return benName;
	}
	public void setBenName(String benName) {
		this.benName = benName;
	}
	public BigDecimal getLastIssueAmt() {
		return lastIssueAmt;
	}
	public void setLastIssueAmt(BigDecimal lastIssueAmt) {
		this.lastIssueAmt = lastIssueAmt;
	}
}
