package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import tw.gov.bli.ba.dao.BaunqualifiednoticeDao;
import tw.gov.bli.ba.domain.Baarclist;
import tw.gov.bli.ba.domain.Baunqualifiednotice;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOIMPL for 不合格核定通知紀錄檔 (<code>BAUNQUALIFIEDNOTICE</code>)
 * 
 * @author KIYOMI
 */
@DaoTable("BAUNQUALIFIEDNOTICE")
public class BaunqualifiednoticeDaoImpl extends SqlMapClientDaoSupport implements BaunqualifiednoticeDao {

    /**
     * 查詢 不合格核定通知紀錄檔(<code>BAUNQUALIFIEDNOTICE</code>) 核定通知紀錄檔資料 for 給付查詢-核定通知記錄
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baunqualifiednotice> selectPaymentQueryData(String apNo) {
        return (List<Baunqualifiednotice>) getSqlMapClientTemplate().queryForList("BAUNQUALIFIEDNOTICE.selectDataForPaymentQuery", apNo);
    }

    /**
     * 新增 不合格核定通知紀錄檔(<code>BAUNQUALIFIEDNOTICE</code>) 資料
     * 
     * @param BAUNQUALIFIEDNOTICE 不合格核定通知紀錄檔
     * @return
     */
    public void insertData(final List<Baunqualifiednotice> baunqualifiednoticeList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (Baunqualifiednotice baunqualifiednotice : baunqualifiednoticeList) {

                    executor.insert("BAUNQUALIFIEDNOTICE.insertData", baunqualifiednotice);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

}
