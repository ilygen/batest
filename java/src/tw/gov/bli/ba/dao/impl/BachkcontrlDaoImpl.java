package tw.gov.bli.ba.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import tw.gov.bli.ba.dao.BachkcontrlDao;
import tw.gov.bli.ba.domain.Bachkcontrl;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>)
 * 
 * @author Goston
 */
@DaoTable("BACHKCONTRL")
public class BachkcontrlDaoImpl extends SqlMapClientDaoSupport implements BachkcontrlDao {

    /**
     * 新增資料到 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>) <br>
     * 
     * @param data <code>Bachkcontrl</code> 物件
     */
    public void insertData(Bachkcontrl data) {
        if (data != null)
            getSqlMapClientTemplate().insert("BACHKCONTRL.insertData", data);
    }

    /**
     * 新增資料到 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>) <br>
     * 
     * @param dataList
     */
    public void insertData(final List<Bachkcontrl> dataList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (int i = 0; i < dataList.size(); i++) {
                    Bachkcontrl data = (Bachkcontrl) dataList.get(i);
                    executor.insert("BACHKCONTRL.insertData", data);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 刪除 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>) 的資料
     * 
     * @param apNo 受理編號
     */
    public void deleteData(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        

        getSqlMapClientTemplate().delete("BACHKCONTRL.deleteData", map);
    }
    
    /**
     * 依傳入條件取得 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>) 的資料
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Bachkcontrl> selectDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BACHKCONTRL.selectDataBy", map);
    }
    
    /**
     * 刪除 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>) 的資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public void deleteDataBySeqNo(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        getSqlMapClientTemplate().delete("BACHKCONTRL.deleteDataBySeqNo", map);
    }

}
