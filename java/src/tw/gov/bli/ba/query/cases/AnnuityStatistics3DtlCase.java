package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.util.List;
/**
 * Case For 年金統計查詢附件3
 * @author TseHua
 *
 */
public class AnnuityStatistics3DtlCase implements Serializable{
	private static final long serialVersionUID = 4370771528491152271L;
	private String spcStart;//級距(起)
	private String spcEnd;//級距(迄)
	private List<AnnuityStatistics3MetaDtlCase> dtlList;//年金統計查詢附件3(明細資料檔)
	public String getSpcStart() {
		return spcStart;
	}	
	public void setSpcStart(String spcStart) {
		this.spcStart = spcStart;
	}
	public String getSpcEnd() {
		return spcEnd;
	}
	public void setSpcEnd(String spcEnd) {
		this.spcEnd = spcEnd;
	}
	public List<AnnuityStatistics3MetaDtlCase> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<AnnuityStatistics3MetaDtlCase> dtlList) {
		this.dtlList = dtlList;
	}
	
}
