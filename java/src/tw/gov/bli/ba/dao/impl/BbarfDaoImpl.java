package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BbarfDao;
import tw.gov.bli.ba.domain.Bbarf;


public class BbarfDaoImpl extends SqlMapClientDaoSupport implements BbarfDao{
    /**
     * 依傳入條件取得 現金給付應收未收檔(<code>BBARF</code>) 資料 
     * 
     * @param apNo 受理編號
     * @param gvIdno 受益人身分證號
     * @return
     */
    public Bbarf selectDataBy(String apNo, String gvIdno){
    	HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(gvIdno))
            map.put("gvIdno", gvIdno);

        return (Bbarf) getSqlMapClientTemplate().queryForObject("BBARF.selectDataBy",map);
    }
    /**
     * 依傳入條件取得 現金給付應收未收檔(<code>BBARF</code>) 資料 
     * 
     * @param benIdno 受益人身分證號
     * @return
     */
    public List<Bbarf> selectDataForOnceWriteOff(String gvIdno){
        return (List<Bbarf>) getSqlMapClientTemplate().queryForList("BBARF.selectDataForOnceWriteOff",gvIdno);
    }
}
