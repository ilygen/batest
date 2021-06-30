package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Balettercode;

/**
 * DAO for 核定函代碼參數檔 (<code>BALETTERCODE</code>)
 * 
 * @author Noctis
 */
public interface BalettercodeDao {
	
	 /**
     * 取得 核定函代碼參數檔(<code>BALETTERCODE</code>) 資料 FOR 核定通知書維護作業
     * 
     * @return <code>List<Bachkfile></code> 物件
     */
    public List<Balettercode> selectBalettercodeDataBy();
}
