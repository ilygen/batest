package tw.gov.bli.common.dao;

import java.util.List;
import tw.gov.bli.common.domain.Mmaplog;

public interface MmaplogDao {
    /**
     * 紀錄 AP Log (單筆)
     * 
     * @param logData <code>Mmaplog</code> 物件
     */
    public void insertData(Mmaplog logData);

    /**
     * 紀錄 AP Log (多筆)
     * 
     * @param logList 內含 <code>Mmaplog</code> 物件的 List
     */
    public void insertData(final List<Mmaplog> logList);
}
