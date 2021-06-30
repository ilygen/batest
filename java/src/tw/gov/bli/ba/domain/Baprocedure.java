package tw.gov.bli.ba.domain;

import java.io.Serializable;

/**
 * 資料庫處理程序主檔(BAPROCEDURE)
 * 
 * @author KIYOMI
 */
public class Baprocedure implements Serializable {
    private String procedureName;     // 程式名稱
    private String procDesc;          // 程式說明
    
	public String getprocedureName() {
		return procedureName;
	}
	
	public void setprocedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	
	public String getProcDesc() {
		return procDesc;
	}
	
	public void setProcDesc(String procDesc) {
		this.procDesc = procDesc;
	}
    
    
}
