package tw.gov.bli.common.dao.impl;

import java.sql.SQLException;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.common.dao.MmaplogDao;
import tw.gov.bli.common.domain.Mmaplog;
import tw.gov.bli.common.util.StrUtil;
import com.ibatis.sqlmap.client.SqlMapExecutor;

public class MmaplogDaoImpl extends SqlMapClientDaoSupport implements MmaplogDao {

    /**
     * 紀錄 AP Log (單筆)
     * 
     * @param logData <code>Mmaplog</code> 物件
     */
    public void insertData(Mmaplog logData) {
        // 檢核各欄位長度
        // [
        if (StrUtil.stringRealLength(logData.getPkField()) > 256) // 異動 TABLE主 鍵
            logData.setPkField(StrUtil.chtStrSubstring(logData.getPkField(), 0, 256));
        if (StrUtil.stringRealLength(logData.getField()) > 2000) // 異動欄位
            logData.setField(StrUtil.chtStrSubstring(logData.getField(), 0, 2000));
        if (StrUtil.stringRealLength(logData.getBefImg()) > 2000) // 改前內容
            logData.setBefImg(StrUtil.chtStrSubstring(logData.getBefImg(), 0, 2000));
        if (StrUtil.stringRealLength(logData.getAftImg()) > 2000) // 改後內容
            logData.setAftImg(StrUtil.chtStrSubstring(logData.getAftImg(), 0, 2000));
        // ]

        getSqlMapClientTemplate().insert("MMAPLOG.insertData", logData);
    }

    /**
     * 紀錄 AP Log (多筆)
     * 
     * @param logList 內含 <code>Mmaplog</code> 物件的 List
     */
    public void insertData(final List<Mmaplog> logList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (Mmaplog logData : logList) {
                    // 檢核各欄位長度
                    // [
                    if (StrUtil.stringRealLength(logData.getPkField()) > 256) // 異動 TABLE主 鍵
                        logData.setPkField(StrUtil.chtStrSubstring(logData.getPkField(), 0, 256));
                    if (StrUtil.stringRealLength(logData.getField()) > 2000) // 異動欄位
                        logData.setField(StrUtil.chtStrSubstring(logData.getField(), 0, 2000));
                    if (StrUtil.stringRealLength(logData.getBefImg()) > 2000) // 改前內容
                        logData.setBefImg(StrUtil.chtStrSubstring(logData.getBefImg(), 0, 2000));
                    if (StrUtil.stringRealLength(logData.getAftImg()) > 2000) // 改後內容
                        logData.setAftImg(StrUtil.chtStrSubstring(logData.getAftImg(), 0, 2000));
                    // ]
                    executor.insert("MMAPLOG.insertData", logData);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

}
