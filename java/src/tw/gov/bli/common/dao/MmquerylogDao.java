package tw.gov.bli.common.dao;

import tw.gov.bli.common.domain.Mmquerylog;

public interface MmquerylogDao {
    /**
     * 紀錄 Query Log
     * 
     * @param logData <code>Mmquerylog</code> 物件
     */
    public void insertData(Mmquerylog logData);
}
