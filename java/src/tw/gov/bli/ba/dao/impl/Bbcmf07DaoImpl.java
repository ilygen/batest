package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.Bbcmf07Dao;
import tw.gov.bli.ba.domain.Bbcmf07;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 醫療院所參數檔 (<code>BBCMF07</code>)
 * 
 * @author Evelyn Hsu
 */
@DaoTable("BBCMF07")
public class Bbcmf07DaoImpl extends SqlMapClientDaoSupport implements Bbcmf07Dao {

    /**
     * 依傳入的條件取得 醫療院所參數檔(<code>BBCMF07</code>) for 複檢費用審核給付清單
     * 
     * @param hpCode 醫院代碼
     * @return
     */
    @DaoFieldList("HPCODE")
    public List<Bbcmf07> selecReviewFeeReceiptHosListBy(String hpCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(hpCode))
            map.put("hpCode", hpCode);

        return getSqlMapClientTemplate().queryForList("BBCMF07.getReviewFeeReceiptHosDataBy", map);
    }

    /**
     * 依傳入的條件取得 醫療院所參數檔(<code>BBCMF07</code>) for 失能年金案件資料更正
     * 
     * @param hpCode 醫院代碼
     * @return
     */
    @DaoFieldList("HPCODE")
    public Bbcmf07 getDisabledApplicationDataUpdateHosDataBy(String hpCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(hpCode))
            map.put("hpCode", hpCode);

        List<Bbcmf07> returnList = getSqlMapClientTemplate().queryForList("BBCMF07.getDisabledApplicationDataUpdateHosDataBy", map);

        if (returnList != null && returnList.size() > 0)
            return returnList.get(0);
        else
            return null;
    }

    /**
     * 依傳入的條件取得 醫療院所參數檔(<code>BBCMF07</code>) for 複檢費用受理作業
     * 
     * @param hpCode 醫院代碼
     * @return
     */
    @DaoFieldList("HPCODE")
    public Bbcmf07 getReviewFeeReceiptHosDataBy(String hpCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(hpCode))
            map.put("hpCode", hpCode);

        List<Bbcmf07> returnList = getSqlMapClientTemplate().queryForList("BBCMF07.getReviewFeeReceiptHpDataBy", map);

        if (returnList != null && returnList.size() > 0)
            return returnList.get(0);
        else
            return null;
    }

    /**
     * 依傳入的條件取得 醫療院所參數檔(<code>BBCMF07</code>) 醫院簡稱
     * 
     * @param hpCode 醫院代碼
     * @return
     */
    @DaoFieldList("HPCODE")
    public String selectHpSnamBy(String hpCode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("hpCode", hpCode);

        return (String) getSqlMapClientTemplate().queryForObject("BBCMF07.selectHpSnamBy", map);
    }
    
    /**
     * 依傳入的條件取得 醫療院所參數檔(<code>BBCMF07</code>) for 查調病歷
     * 
     * @param hpCode 醫院代碼
     * @return
     */
    @DaoFieldList("HPCODE")
    public Bbcmf07 getExecutiveSupportHosData(String hpCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(hpCode))
            map.put("hpCode", hpCode);

        List<Bbcmf07> returnList = getSqlMapClientTemplate().queryForList("BBCMF07.getExecutiveSupportHosData", map);

        if (returnList != null && returnList.size() > 0)
            return returnList.get(0);
        else
            return null;
    }
}
