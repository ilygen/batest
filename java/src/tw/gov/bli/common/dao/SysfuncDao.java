package tw.gov.bli.common.dao;

import java.util.List;
import tw.gov.bli.common.domain.SystemFunction;

public interface SysfuncDao {
    
    /**
     * 依應用系統代號取得系統功能清單
     * 
     * @param systemId 應用系統代號
     * @return 內含 <code>tw.gov.bli.common.domain.SystemFunction</code> 物件的 List
     */   
    public List<SystemFunction> selectSysFuncBySystemId(String systemId);
    
    /**
     * 依應用系統代號取得系統功能清單 for CAS
     * 
     * @param systemId 應用系統代號
     * @return 內含 <code>tw.gov.bli.common.domain.SystemFunction</code> 物件的 List
     */   
    public List<SystemFunction> selectCasSysFuncBySystemId();
}
