package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import tw.gov.bli.ba.dao.BamarginamtnotifyDao;
import tw.gov.bli.ba.domain.Bamarginamtnotify;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOIMPL for 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>)
 * 
 * @author KIYOMI
 */
@DaoTable("BAMARGINAMTNOTIFY")
public class BamarginamtnotifyDaoImpl extends SqlMapClientDaoSupport implements BamarginamtnotifyDao {

    /**
     * 新增 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>) 資料
     * 
     * @param BAMARGINAMTNOTIFY 老年差額金通知發文紀錄檔
     * @return
     */
    public void insertDataForMonthlyRpt31(final List<Bamarginamtnotify> bamarginamtnotifyList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (Bamarginamtnotify bamarginamtnotify : bamarginamtnotifyList) {

                    executor.insert("BAMARGINAMTNOTIFY.insertDataForMonthlyRpt31", bamarginamtnotify);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 更新 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>) 資料 - 第二次通知函（催辦）
     * 
     * @param
     */
    public void updateDataForMonthlyRpt31U(final List<Bamarginamtnotify> bamarginamtnotifyList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (Bamarginamtnotify bamarginamtnotify : bamarginamtnotifyList) {

                    executor.insert("BAMARGINAMTNOTIFY.updateDataForMonthlyRpt31U", bamarginamtnotify);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 更新 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>) 資料 - 第三次通知函（延不補正）
     * 
     * @param
     */
    public void updateDataForMonthlyRpt31P(final List<Bamarginamtnotify> bamarginamtnotifyList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (Bamarginamtnotify bamarginamtnotify : bamarginamtnotifyList) {

                    executor.insert("BAMARGINAMTNOTIFY.updateDataForMonthlyRpt31P", bamarginamtnotify);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 查詢 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>) 資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Bamarginamtnotify> selectDataForMonthlyRpt13(String apNo) {
        return (List<Bamarginamtnotify>) getSqlMapClientTemplate().queryForList("BAMARGINAMTNOTIFY.selectDataForMonthlyRpt13", apNo);
    }

    /**
     * 依傳入條件取得 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>) 發文日期 for report replace 老年差額金通知
     * 
     * @param apNo 受理編號
     * @return
     */
    public HashMap<String, Object> selectRptReplaceForIssueDateL023(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BAMARGINAMTNOTIFY.selectRptReplaceForIssueDateL023", map);
    }
}
