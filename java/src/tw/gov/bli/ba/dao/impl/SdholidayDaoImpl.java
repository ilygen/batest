package tw.gov.bli.ba.dao.impl;
/**
 * SD假日檔
 * 
 * @author jerry
 */

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.SdholidayDao;
import tw.gov.bli.ba.util.DateUtility;

public class SdholidayDaoImpl extends SqlMapClientDaoSupport implements SdholidayDao {

    /**
     * 檢查傳入的日期是否為假日
     */
    public boolean checkHoliday(String date) {
        if (DateUtility.isValidDate(date)) {
            Integer count = (Integer) getSqlMapClientTemplate().queryForObject("SDHOLIDAY.checkHoliday", date);
            if (count != null && count.intValue() > 0) {
                return true;
            }
        }
        return false;
    }

}
