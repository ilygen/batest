package tw.gov.bli.ba.dao;

/**
 * DAO for 給付媒體上傳記錄檔
 * 
 * @author eddie
 */

public interface BamfileftprecordDao {
    
    /**
     * 依傳入條件新增 給付媒體上傳記錄檔
     *
     * @param mfileName 媒體檔案名稱
     * @param mfileDate 媒體檔案日期
     * @param chkDate 核定日期
     *
     */
    public void insertData(String mfileName, String mfileDate, String chkDate);

    /**
     * 依傳入條件更新 給付媒體上傳記錄檔
     *
     * @param mfileName 媒體檔案名稱
     * @param mfileDate 媒體檔案日期
     * @param chkDate 核定日期
     *
     */
    public void updateData(String mfileName, String mfileDate, String chkDate);

}
