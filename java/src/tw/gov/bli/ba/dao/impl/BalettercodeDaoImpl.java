package tw.gov.bli.ba.dao.impl;

/**
 * DAO for 核定函代碼參數檔 (<code>BALETTERCODE</code>)
 * 
 * @author Noctis
 */

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BalettercodeDao;
import tw.gov.bli.ba.domain.Balettercode;

public class BalettercodeDaoImpl extends SqlMapClientDaoSupport implements BalettercodeDao {

    /**
     * 取得 核定函代碼參數檔(<code>BALETTERCODE</code>) 資料 FOR 核定通知書維護作業
     * 
     * @return <code>List<Bachkfile></code> 物件
     */
    public List<Balettercode> selectBalettercodeDataBy() {
  
        return getSqlMapClientTemplate().queryForList("BALETTERCODE.selectBalettercodeDataBy");
    }
    
}
