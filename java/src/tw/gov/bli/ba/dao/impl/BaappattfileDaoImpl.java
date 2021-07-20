package tw.gov.bli.ba.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BaappattfileDao;
import tw.gov.bli.ba.domain.Baappattfile;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 給付案件附件檔 (<code>BAAPPATTFILE</code>)
 *
 * @author Eddie
 */
@DaoTable("BAAPPATTFILE")
public class BaappattfileDaoImpl extends SqlMapClientDaoSupport implements BaappattfileDao {

    /**
     * 依傳入的條件取得 給付案件附件檔(<code>BAAPPLOG</code>) 的File資料
     *
     * @param apNo 受理編號
     * @return File
     */
    public Baappattfile selectFile(String apNo) {
        return (Baappattfile) getSqlMapClientTemplate().queryForObject("BAAPPATTFILE.selectFile", apNo);
    }

}
