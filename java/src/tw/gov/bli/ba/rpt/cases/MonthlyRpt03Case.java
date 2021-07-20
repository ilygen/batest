package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Case for 月核定差異統計表
 * 
 * @author Goston
 */
public class MonthlyRpt03Case implements Serializable {
    private String issuYmBegin;
    private String issuYmEnd;
    private String payCode;

    // 系統參數檔 (BAPARAM)
    // [
    private String payCodeName; // 給付別中文名稱
    // ]

    // 月核定差異統計檔 (BALP0D330)
    // [
    private List<MonthlyRpt03DetailCase> detailList;
    // ]

    // 月核定差異統計檔 (BALP0D330) 的 HashMap
    // [
    private HashMap<String, MonthlyRpt03DetailCase> detailMap;

    // ]

    /**
     * 設定 月核定差異統計檔 (BALP0D330) 的 List 及 HashMap
     */
    public void setDetailList(List<MonthlyRpt03DetailCase> detailList) {
        this.detailList = detailList;

        // 建立 HashMap
        for (MonthlyRpt03DetailCase detailCaseData : detailList) {
            detailMap.put(detailCaseData.getIssuYm(), detailCaseData);
        }
    }

    public MonthlyRpt03Case() {
        detailMap = new HashMap<String, MonthlyRpt03DetailCase>();
    }

    public String getIssuYmBegin() {
        return issuYmBegin;
    }

    public void setIssuYmBegin(String issuYmBegin) {
        this.issuYmBegin = issuYmBegin;
    }

    public String getIssuYmEnd() {
        return issuYmEnd;
    }

    public void setIssuYmEnd(String issuYmEnd) {
        this.issuYmEnd = issuYmEnd;
    }

    public String getPayCodeName() {
        return payCodeName;
    }

    public void setPayCodeName(String payCodeName) {
        this.payCodeName = payCodeName;
    }

    public List<MonthlyRpt03DetailCase> getDetailList() {
        return detailList;
    }

    public HashMap<String, MonthlyRpt03DetailCase> getDetailMap() {
        return detailMap;
    }

    public void setDetailMap(HashMap<String, MonthlyRpt03DetailCase> detailMap) {
        this.detailMap = detailMap;
    }

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

}
