package tw.gov.bli.common.dao.impl;

import java.math.BigDecimal;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.common.dao.MmaccesslgDao;
import tw.gov.bli.common.domain.Mmaccesslg;

public class MmaccesslgDaoImpl extends SqlMapClientDaoSupport implements MmaccesslgDao {

    /**
     * 紀錄 Access Log
     * 
     * @param logData <code>Mmaccesslg</code> 物件
     * @return <code>MMACCESSLG.SNO</code>
     */
    public BigDecimal insertData(Mmaccesslg logData) {
        return (BigDecimal) getSqlMapClientTemplate().insert("MMACCESSLG.insertData", logData);
    }

}
