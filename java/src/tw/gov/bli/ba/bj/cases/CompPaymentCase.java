package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * case for 補正核付作業
 * 
 * @author Eddie
 */
public class CompPaymentCase implements Serializable {
	private String apNo; // 受理編號
	private String payCode; // 給付別
	private String evtName; // 事故者姓名
	private String evtIdnNo; // 事故者身分證號
    private String evtBrDate;// 事故者出生日期
	private String appDate; // 申請日期
	private String evtJobDate; // 事故日期
	private String issuYm; // 核定年月
	private String chkDate; // 核定日期
	private String aplPayDate; // 核付日期
	private String remitDate; // 入帳日期
	private String payYm; // 給付年月
	private String seqNo; // 受款人序
	private String benName; // 受款人姓名
	private String benIdnNo; // 受款人身分證號
	private String benEvtRel; // 關係
	private String issueAmt; // 核定金額
	private String aplPayAmt; // 實付金額
	private BigDecimal baappbaseId; // 資料列編號
	private String mtestMk; // 編審處理註記
	private String procTime; // 處理日期時間
	private String upCol; // 改的欄位
	private String aValue; // 改前值
	private String bValue; // 改後值
	private String empNo; // session員工編號
	private String status; // 'U'／'A' (若為新增則塞入'A'，反之則為'U')

	public String getaValue() {
		return aValue;
	}

	public void setaValue(String aValue) {
		this.aValue = aValue;
	}

	public String getbValue() {
		return bValue;
	}

	public void setbValue(String bValue) {
		this.bValue = bValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getUpCol() {
		return upCol;
	}

	public void setUpCol(String upCol) {
		this.upCol = upCol;
	}

	public String getProcTime() {
		return procTime;
	}

	public void setProcTime(String procTime) {
		this.procTime = procTime;
	}

	public String getEvtName() {
		return evtName;
	}

	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}

	public String getEvtIdnNo() {
		return evtIdnNo;
	}

	public void setEvtIdnNo(String evtIdnNo) {
		this.evtIdnNo = evtIdnNo;
	}
	
	public String getEvtBrDate() {
		return evtBrDate;
	}

	public void setEvtBrDate(String evtBrDate) {
		this.evtBrDate = evtBrDate;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getEvtJobDate() {
		return evtJobDate;
	}

	public void setEvtJobDate(String evtJobDate) {
		this.evtJobDate = evtJobDate;
	}

	public String getChkDate() {
		return chkDate;
	}

	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}

	public String getAplPayDate() {
		return aplPayDate;
	}

	public void setAplPayDate(String aplPayDate) {
		this.aplPayDate = aplPayDate;
	}

	public String getRemitDate() {
		return remitDate;
	}

	public void setRemitDate(String remitDate) {
		this.remitDate = remitDate;
	}

	public String getPayYm() {
		return payYm;
	}

	public void setPayYm(String payYm) {
		this.payYm = payYm;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getBenName() {
		return benName;
	}

	public void setBenName(String benName) {
		this.benName = benName;
	}

	public String getBenIdnNo() {
		return benIdnNo;
	}

	public void setBenIdnNo(String benIdnNo) {
		this.benIdnNo = benIdnNo;
	}

	public String getBenEvtRel() {
		return benEvtRel;
	}

	public void setBenEvtRel(String benEvtRel) {
		this.benEvtRel = benEvtRel;
	}

	public String getIssueAmt() {
		return issueAmt;
	}

	public void setIssueAmt(String issueAmt) {
		this.issueAmt = issueAmt;
	}

	public String getAplPayAmt() {
		return aplPayAmt;
	}

	public void setAplPayAmt(String aplPayAmt) {
		this.aplPayAmt = aplPayAmt;
	}

	public BigDecimal getBaappbaseId() {
		return baappbaseId;
	}

	public void setBaappbaseId(BigDecimal baappbaseId) {
		this.baappbaseId = baappbaseId;
	}

	public String getMtestMk() {
		return mtestMk;
	}

	public void setMtestMk(String mtestMk) {
		this.mtestMk = mtestMk;
	}

	public String getApNo() {
		return apNo;
	}

	public void setApNo(String apNo) {
		this.apNo = apNo;
	}

	public String getIssuYm() {
		return issuYm;
	}

	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getApNoStrDisplay() {
		if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
			return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-"
					+ apNo.substring(7, 12);
		else
			return StringUtils.defaultString(apNo);
	}

	public String getEvtBrDateDisplay() {
		// 若日期為8碼(西元年月日)才轉
		if (StringUtils.isNotBlank(evtBrDate) && evtBrDate.length() == 8) {
			return (DateUtility.changeDateType(evtBrDate));
		}
		return evtBrDate;
	}

	public String getAppDateDisplay() {
		// 若日期為8碼(西元年月日)才轉
		if (StringUtils.isNotBlank(appDate) && appDate.length() == 8) {
			return (DateUtility.changeDateType(appDate));
		}
		return appDate;
	}

	public String getEvtJobDateDisplay() {
		// 若日期為8碼(西元年月日)才轉
		if (StringUtils.isNotBlank(evtJobDate) && evtJobDate.length() == 8) {
			return (DateUtility.changeDateType(evtJobDate));
		}
		return evtJobDate;
	}

	public String getIssuYmDisplay() {
		// 若日期為6碼(西元年月)才轉
		if (StringUtils.isNotBlank(issuYm) && issuYm.length() == 6) {
			return (DateUtility.changeWestYearMonthType(issuYm));
		}
		return issuYm;
	}

	public String getPayYmDisplay() {
		// 若日期為6碼(西元年月)才轉
		if (StringUtils.isNotBlank(payYm) && payYm.length() == 6) {
			return (DateUtility.changeWestYearMonthType(payYm));
		}
		return payYm;
	}

	public String getBenEvtRelDisplay() {
		String benEvtRelStr = "";
		if ((ConstantKey.BAAPPBASE_BENEVTREL_1).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_2).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_3).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_4).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_5).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_6).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_7).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_A).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_C).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_E).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_F).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_N).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_Z).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z;
		} else if ((ConstantKey.BAAPPBASE_BENEVTREL_O).equals(getBenEvtRel())) {
			benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O;
		}
		return benEvtRelStr;
	}
}
