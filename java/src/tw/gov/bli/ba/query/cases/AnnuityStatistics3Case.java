package tw.gov.bli.ba.query.cases;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
/**
 * Case For 年金統計查詢附件3
 * @author TseHua
 *
 */
public class AnnuityStatistics3Case {
	private String qryType;
	private String qryPayCode;
	private BigDecimal totalSum;
	private List<AnnuityStatistics3DtlCase> dtlCase;
	private String analysisSelect;
	public String getQryType() {
		return qryType;
	}	
	public void setQryType(String qryType) {
		this.qryType = qryType;
	}
	public String getQryPayCode() {
		return qryPayCode;
	}
		public void setQryPayCode(String qryPayCode) {
		this.qryPayCode = qryPayCode;
	}
	public List<AnnuityStatistics3DtlCase> getDtlCase() {
		return dtlCase;
	}
	public void setDtlCase(List<AnnuityStatistics3DtlCase> dtlCase) {
		this.dtlCase = dtlCase;
	}
	public String getAnalysisSelect() {
		return analysisSelect;
	}
	public void setAnalysisSelect(String analysisSelect) {
		this.analysisSelect = analysisSelect;
	}
	public String getQryTypStr() {
		//C-金額級距、D-展減級距、E-平均薪資級距、F-年資級距
		if(StringUtils.equals(qryType, ConstantKey.SPACING_C)) {
			return ConstantKey.SPACING_C_STR;//"金額級距";
		} else if(StringUtils.equals(qryType, ConstantKey.SPACING_D)) {
			return ConstantKey.SPACING_D_STR;//"展減級距";
		} else if(StringUtils.equals(qryType, ConstantKey.SPACING_E)) {
			return ConstantKey.SPACING_E_STR;//"平均薪資級距";
		} else if(StringUtils.equals(qryType, ConstantKey.SPACING_F)) {
			return ConstantKey.SPACING_F_STR;//"年資級距";
		} else {
			return "";
		}
		
	}
	public String getQryPayCodeStr() {
		if(StringUtils.equals(qryPayCode, "L")) {
			return ConstantKey.PAYKIND_L;//"老年年金給付";
		}else if(StringUtils.equals(qryPayCode, "S")) {
			return ConstantKey.PAYKIND_S;//"遺屬年金給付";
		}else if(StringUtils.equals(qryPayCode, "K")) {
			return ConstantKey.PAYKIND_K;//"失能年金給付";
		}else {
			return "";
		}
	}
	public BigDecimal getTotalSum() {
		return totalSum;
	}
	public void setTotalSum(BigDecimal totalSum) {
		this.totalSum = totalSum;
	}
}
