package tw.gov.bli.ba.dao;

/**
 * SD假日檔
 * 
 * @author jerry
 */
public interface SdholidayDao {
    /**
     * 檢查傳入的日期是否為假日
     */
    boolean checkHoliday(String date);
}
