package tw.gov.bli.ba.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BabanDao;
import tw.gov.bli.ba.dao.BamfileftprecordDao;
import tw.gov.bli.ba.domain.Baban;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * DAO for 給付媒體上傳記錄檔
 *
 * @author eddie
 */

public class BamfileftprecordDaoImpl extends SqlMapClientDaoSupport implements BamfileftprecordDao {

    /**
     * 依傳入條件新增 給付媒體上傳記錄檔
     *
     * @param mfileName 媒體檔案名稱
     * @param mfileDate 媒體檔案日期
     * @param chkDate 核定日期
     *
     */
    public void insertData(String mfileName, String mfileDate, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mfileName", mfileName);
        map.put("mfileDate", mfileDate);
        map.put("chkDate", chkDate);
        getSqlMapClientTemplate().insert("BAMFILEFTPRECORD.insertData", map);
    }

    /**
     * 依傳入條件更新 給付媒體上傳記錄檔
     *
     * @param mfileName 媒體檔案名稱
     * @param mfileDate 媒體檔案日期
     * @param chkDate 核定日期
     *
     */
    public void updateData(String mfileName, String mfileDate, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mfileName", mfileName);
        map.put("mfileDate", mfileDate);
        map.put("chkDate", chkDate);
        getSqlMapClientTemplate().update("BAMFILEFTPRECORD.updateData", map);
    }

}
