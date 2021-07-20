package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.util.List;


/**
 * Case for 穿透案件統計表 初核人員分類Case
 * 
 * @author Kiyomi
 */
public class OtherRpt10ClassCase implements Serializable {
	
	private String chkMan;// 初核人員
	private List<OtherRpt10Case> chkManOtherRptDataList;

	public String getChkMan() {
		return chkMan;
	}

	public void setChkMan(String chkMan) {
		this.chkMan = chkMan;
	}

	public List<OtherRpt10Case> getChkManOtherRptDataList() {
		return chkManOtherRptDataList;
	}

	public void setChkManOtherRptDataList(List<OtherRpt10Case> chkManOtherRptDataList) {
		this.chkManOtherRptDataList = chkManOtherRptDataList;
	}

	
}
