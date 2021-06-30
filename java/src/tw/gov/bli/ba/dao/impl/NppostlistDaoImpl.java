package tw.gov.bli.ba.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.NppostlistDao;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 郵局代碼檔(<code>NPPOSTLIST</code>)
 * 
 * @author Rickychi
 */

@DaoTable("NPPOSTLIST")
public class NppostlistDaoImpl extends SqlMapClientDaoSupport implements NppostlistDao {
    /**
     * 依傳入條件取得 郵局代碼檔(<code>NPPOSTLIST</code>) 郵局名稱<br>
     * 
     * @param postId 郵局電腦代號
     * @return <code>String</code>
     */
    @DaoFieldList("POSTID")
    public String selectPostNameBy(String postId) {
        return (String) getSqlMapClientTemplate().queryForObject("NPPOSTLIST.selectPostNameBy", postId);
    }

}
