package tw.gov.bli.common.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.common.dao.MmquerylogDao;
import tw.gov.bli.common.domain.Mmquerylog;
import tw.gov.bli.common.util.StrUtil;

public class MmquerylogDaoImpl extends SqlMapClientDaoSupport implements MmquerylogDao {

    /**
     * 紀錄 Query Log
     * 
     * @param logData <code>Mmquerylog</code> 物件
     */
    public void insertData(Mmquerylog logData) {
        // 檢核各欄位長度
        // [
        if (StrUtil.stringRealLength(logData.getQyCondition()) > 4000) // 查詢條件
            logData.setQyCondition(StrUtil.chtStrSubstring(logData.getQyCondition(), 0, 4000));

        if (StrUtil.stringRealLength(logData.getMemo()) > 256) // 備註
            logData.setQyCondition(StrUtil.chtStrSubstring(logData.getMemo(), 0, 256));
        // ]

        getSqlMapClientTemplate().insert("MMQUERYLOG.insertData", logData);
    }

}
