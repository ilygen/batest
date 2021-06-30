package tw.gov.bli.common.dao;

import java.math.BigDecimal;
import tw.gov.bli.common.domain.Mmaccesslg;

public interface MmaccesslgDao {
    /**
     * 紀錄 Access Log
     * 
     * @param logData <code>Mmaccesslg</code> 物件
     * @return <code>MMACCESSLG.SNO</code>
     */
    public BigDecimal insertData(Mmaccesslg logData);
}
