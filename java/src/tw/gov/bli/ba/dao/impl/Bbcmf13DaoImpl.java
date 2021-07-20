package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.Bbcmf13Dao;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 勞保管制對象參數檔 (<code>BBCMF13</code>)
 * 
 * @author Noctis
 */
@DaoTable("BBCMF13")
public class Bbcmf13DaoImpl extends SqlMapClientDaoSupport implements Bbcmf13Dao {

    /**
     * 依傳入的條件取得 醫療院所參數檔(<code>BBCMF13</code>) 醫院簡稱
     * 
     * @param hpCode 醫院代碼
     * @return
     */
    public Integer selectBbcmf13CountBy(String hpNo, String hpSnam) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("hpNo", hpNo);
        map.put("hpSnam", hpSnam);

        return (Integer) getSqlMapClientTemplate().queryForObject("BBCMF13.selectBbcmf13CountBy", map);
    }

}
