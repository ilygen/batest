package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.LocalPfpcckyuserrecDao;
import tw.gov.bli.ba.domain.Pfpcckyuserrec;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 退回現金業務單位使用紀錄檔 (<code>PFPCCKYUSERREC</code>)
 * 
 * @author Kiyomi
 */
@DaoTable("PFPCCKYUSERREC")
public class LocalPfpcckyuserrecDaoImpl extends SqlMapClientDaoSupport implements LocalPfpcckyuserrecDao {

    /**
     * 依傳入條件取得 退回現金業務單位使用紀錄檔(<code>PFPCCKYUSERREC</code>) EXPID
     * 
     * @param idn 身分證號
     * @return
     */
    public Integer selectExpid(String sInskd, String sBliAccountCode, String sBookedbook, String sBkaccountdt, Long lBatchno, Long lSerialno, Long lCashAmt, Long lDivseq, Long lIndexNo){
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(sInskd))
            map.put("sInskd", sInskd);
        
        if (StringUtils.isNotBlank(sBliAccountCode))
            map.put("sBliAccountCode", sBliAccountCode);
        
        if (StringUtils.isNotBlank(sBookedbook))
            map.put("sBookedbook", sBookedbook);
        
        if (StringUtils.isNotBlank(sBkaccountdt))
            map.put("sBkaccountdt", sBkaccountdt);
        
        map.put("lBatchno", lBatchno);
        map.put("lSerialno", lSerialno);
        map.put("lCashAmt", lCashAmt);
        map.put("lDivseq", lDivseq);
        map.put("lIndexNo", lIndexNo);
        
        
        return (Integer) getSqlMapClientTemplate().queryForObject("PFPCCKYUSERREC.selectExpid", map);
    }
    
}
