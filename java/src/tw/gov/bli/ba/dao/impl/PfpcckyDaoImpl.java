package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.PbbmsaDao;
import tw.gov.bli.ba.dao.PfpcckyDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Pbbmsa;
import tw.gov.bli.ba.domain.Pfpccky;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 現金給付參考檔 (<code>PBBMSA</code>) <br>
 * 
 * @author Goston
 */
@DaoTable("PBBMSA")
public class PfpcckyDaoImpl extends SqlMapClientDaoSupport implements PfpcckyDao {

    /**
     * 依傳入條件取得 退現資料檔 (<code>PFPCCKY</code>)  for 老年年金應收收回處理
     * 
     * @param apNo  受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Pfpccky> selectCashReceiveDataListBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("PFPCCKY.selectCashReceiveDataListBy", map);
    }
  
}
