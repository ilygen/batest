package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.Bbcmf06Dao;
import tw.gov.bli.ba.domain.Bbcmf06;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * Dao for 資料名稱參數檔 (<code>BBCMF06</code>)
 * 
 * @author Goston
 */
@DaoTable("BBCMF06")
public class Bbcmf06DaoImpl extends SqlMapClientDaoSupport implements Bbcmf06Dao {

    /**
     * 依傳入的條件取得 資料名稱參數檔 (<code>BBCMF06</code>) 的資料
     * 
     * @param insKd 保險別
     * @param dataKd 資料種類
     * @param dataCd 資料代碼
     * @return
     */
    @DaoFieldList("INSKD,DATAKD,DATACD")
    public List<Bbcmf06> selectListBy(String insKd, String dataKd, String dataCd) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(insKd))
            map.put("insKd", insKd);
        if (StringUtils.isNotBlank(dataKd))
            map.put("dataKd", dataKd);
        if (StringUtils.isNotBlank(dataCd))
            map.put("dataCd", dataCd);

        return getSqlMapClientTemplate().queryForList("BBCMF06.selectDataBy", map);
    }

    /**
     * 依傳入的條件取得 資料名稱參數檔 (<code>BBCMF06</code>) 失能項目 (<code>CRIINJDP</code>) 的資料
     * 
     * @param dataCd 資料代碼
     * @return
     */
    @DaoFieldList("DATACD")
    public List<Bbcmf06> selectCriInJdpListBy(String dataCd) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("dataCd", dataCd);

        return getSqlMapClientTemplate().queryForList("BBCMF06.selectCriInJdpListBy", map);
    }

}
