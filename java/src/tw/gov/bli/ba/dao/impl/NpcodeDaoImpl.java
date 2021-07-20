package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.NpcodeDao;
import tw.gov.bli.ba.domain.Npcode;

/**
 * DAO for 學校代碼檔 (<code>NPCODE</code>)
 * 
 * @author Noctis
 */
public class NpcodeDaoImpl extends SqlMapClientDaoSupport implements NpcodeDao {

    /**
     * 取得 學校代碼檔 (<code>NPCODE</code>) FOR 眷屬資料更正 遺屬年金受款人資料更正
     * @return
     */
    public List<Npcode> selectNpCodeOptionBy() {
        return getSqlMapClientTemplate().queryForList("NPCODE.selectNpCodeOptionBy");
    }
    
    /**
     * 取得 學校代碼檔 (<code>NPCODE</code>) FOR 眷屬資料更正 遺屬年金受款人資料更正
     * @return
     */
    public Npcode selectNpCodeNameBy(String schoolCode) {
    	HashMap<String,String> map = new HashMap<String, String>();
    	map.put("schoolCode" , schoolCode);

        return (Npcode) getSqlMapClientTemplate().queryForObject("NPCODE.selectNpCodeNameBy", map);
    }

    /**
     * 取得 學校代碼檔 (<code>NPCODE</code>) FOR 眷屬資料更正 遺屬年金受款人資料更正
     * @return
     */
    public List<Npcode> selectSchoolListBy(String schoolName) {
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("schoolName" , schoolName);

        return getSqlMapClientTemplate().queryForList("NPCODE.selectNpSchoolNameBy", map);
    }    
    
}
