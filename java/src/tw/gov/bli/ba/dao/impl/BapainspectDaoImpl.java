package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BapainspectDao;
import tw.gov.bli.ba.domain.Bapainspect;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 先抽對象條件參數檔 (<code>BAPAINSPECT</code>)
 * 
 * @author Goston
 */
@DaoTable("BAPAINSPECT")
public class BapainspectDaoImpl extends SqlMapClientDaoSupport implements BapainspectDao {

    /**
     * 依傳入的條件取得 先抽對象條件參數檔 (<code>BAPAINSPECT</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @return <code>Bapainspect</code> 物件
     */
    @DaoFieldList("PAYCODE")
    public Bapainspect selectDataBy(String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);

        return (Bapainspect) getSqlMapClientTemplate().queryForObject("BAPAINSPECT.selectDataBy", map);
    }

    /**
     * 新增資料到 先抽對象條件參數檔 (<code>BAPAINSPECT</code>)<br>
     * 
     * @param data <code>Bapainspect</code> 物件
     */
    public void insertData(Bapainspect data) {
        if (data != null)
            getSqlMapClientTemplate().insert("BAPAINSPECT.insertData", data);
    }

    /**
     * 刪除 先抽對象條件參數檔 (<code>BAPAINSPECT</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     */
    public void deleteData(String payCode) {
        getSqlMapClientTemplate().delete("BAPAINSPECT.deleteData", payCode);
    }

}
