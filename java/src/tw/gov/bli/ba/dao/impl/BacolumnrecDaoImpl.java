package tw.gov.bli.ba.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import tw.gov.bli.ba.dao.BacolumnrecDao;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.domain.Bacolumnrec;

/**
 * DAOImpl for 眷屬遺屬重編欄位更正記錄檔 (<code>BACOLUMNREC</code>)
 * 
 * @author Rickychi
 */
public class BacolumnrecDaoImpl extends SqlMapClientDaoSupport implements BacolumnrecDao {
    /**
     * 新增 眷屬遺屬重編欄位更正記錄檔(<code>BACOLUMNREC</code>) 資料
     * 
     * @param bacolumnrec
     */
    public void insertData(Bacolumnrec bacolumnrec) {
        getSqlMapClientTemplate().insert("BACOLUMNREC.insertData", bacolumnrec);
    }

    /**
     * 新增 眷屬遺屬重編欄位更正記錄檔(<code>BACOLUMNREC</code>) 資料
     * 
     * @param bacolumnrecList
     */
    public void insertData(final List<Bacolumnrec> bacolumnrecList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (int i = 0; i < bacolumnrecList.size(); i++) {
                    Bacolumnrec data = (Bacolumnrec) bacolumnrecList.get(i);
                    executor.insert("BACOLUMNREC.insertData", data);
                }
                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }
}
