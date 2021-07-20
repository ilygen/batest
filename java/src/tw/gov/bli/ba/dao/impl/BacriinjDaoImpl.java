package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BacriinjDao;
import tw.gov.bli.ba.domain.Bacriinj;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * Dao for 國際疾病代碼參數檔 (<code>BACRIINJ</code>)
 * 
 * @author Goston
 */
@DaoTable("BACRIINJ")
public class BacriinjDaoImpl extends SqlMapClientDaoSupport implements BacriinjDao {

    /**
     * 依傳入的條件取得 國際疾病代碼參數檔 (<code>BACRIINJ</code>) 的資料
     * 
     * @param criInJMk 國際疾病代碼
     * @return
     */
    @DaoFieldList("CRIINJMK")
    public Bacriinj selectDataBy(String criInJMk) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(criInJMk))
            map.put("criInJMk", criInJMk);

        return (Bacriinj) getSqlMapClientTemplate().queryForObject("BACRIINJ.selectDataBy", map);
    }

}
