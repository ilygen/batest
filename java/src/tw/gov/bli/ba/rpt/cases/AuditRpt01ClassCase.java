package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.util.List;


/**
 * Case for 逾期未決行案件管制表 初核人員分類Case
 * 
 * @author Noctis
 */
public class AuditRpt01ClassCase implements Serializable {
	
	private String chkMan;// 初核人員
	private List<AuditRpt01Case> chkManAuditRptDataList;

	public String getChkMan() {
		return chkMan;
	}

	public void setChkMan(String chkMan) {
		this.chkMan = chkMan;
	}

	public List<AuditRpt01Case> getChkManAuditRptDataList() {
		return chkManAuditRptDataList;
	}

	public void setChkManAuditRptDataList(
			List<AuditRpt01Case> chkManAuditRptDataList) {
		this.chkManAuditRptDataList = chkManAuditRptDataList;
	}

	
}
