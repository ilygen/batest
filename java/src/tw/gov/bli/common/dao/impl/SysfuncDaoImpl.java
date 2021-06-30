package tw.gov.bli.common.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;
import tw.gov.bli.common.dao.SysfuncDao;
import tw.gov.bli.common.domain.SystemFunction;
@DaoTable("ITEMS")
public class SysfuncDaoImpl extends SqlMapClientDaoSupport implements SysfuncDao {

    /**
     * 依應用系統代號取得系統功能清單
     * 
     * @param systemId 應用系統代號
     * @return
     */
    @DaoFieldList("SYSTEM_ID")
    public List<SystemFunction> selectSysFuncBySystemId(String systemId) {
        return getSqlMapClientTemplate().queryForList("SYSFUNC.selectSysFuncBySystemId", systemId);
    }

    /**
     * 依應用系統代號取得系統功能清單 for CAS
     * 
     * @param systemId 應用系統代號
     * @return 內含 <code>tw.gov.bli.common.domain.SystemFunction</code> 物件的 List
     */
    public List<SystemFunction> selectCasSysFuncBySystemId() {
        return getSqlMapClientTemplate().queryForList("SYSFUNC.selectCasSysFuncBySystemId");
    }
}
