package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.dao.BapaissudateDao;
import tw.gov.bli.ba.dao.BaproceduredtlDao;
import tw.gov.bli.ba.domain.Bapaissudate;
import tw.gov.bli.ba.domain.Baproceduredtl;


public class BjAjaxService {
	private static Log log = LogFactory.getLog(DecisionAjaxService.class);
	private BapaissudateDao bapaissudateDao;
	private BaproceduredtlDao baproceduredtlDao;
	
	public boolean getCheckChkdate(String payCode, String issuYm, String chkDate) {
		BigDecimal chkDateCount = bapaissudateDao.selectCheckChkdate(payCode, issuYm, chkDate);
		if(chkDateCount.compareTo(BigDecimal.ZERO) == 0){
			//沒有在暫存檔中
			return true;
		}else{
			//在暫存檔中有資料
			return false;
		}
	}
	
    /**
     * 自 資料庫處理程序主檔 (<code>BAPROCEDUREDTL</code>) 取得 程式名稱 下拉式選單的資料
     * 
     * @return 內含 <code>Baproceduredtl</code> 物件的 List
     */
    public List<Baproceduredtl> getParameters(String procName) {
        return baproceduredtlDao.selectListBy(procName);
    }    	
	
	public void setBapaissudateDao(BapaissudateDao bapaissudateDao) {
		this.bapaissudateDao = bapaissudateDao;
	}

    public void setBaproceduredtlDao(BaproceduredtlDao baproceduredtlDao) {
        this.baproceduredtlDao = baproceduredtlDao;
    }	

    
}
