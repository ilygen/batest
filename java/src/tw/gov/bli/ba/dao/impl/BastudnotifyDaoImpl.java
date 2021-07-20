package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BastudnotifyDao;
import tw.gov.bli.ba.dao.BastudtermDao;
import tw.gov.bli.ba.domain.Bastudnotify;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOImpl for 遺屬在學期間檔 (<code>BASTUDNOTIFY</code>)
 * 
 * @author Noctis
 */

@DaoTable("BASTUDNOTIFY")
public class BastudnotifyDaoImpl extends SqlMapClientDaoSupport implements BastudnotifyDao {

    /**
     * 新增遺屬在學期間資料 (<code>BASTUDTERM</code>) for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    public BigDecimal insertBastudnotify(Bastudnotify bastudnotify) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BASTUDNOTIFY.insertBastudnotify", bastudnotify);
    }

}
