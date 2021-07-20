package tw.gov.bli.ba.dao;

import java.util.HashMap;

import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * 依傳入條件取得 出生日期錯誤參數檔(<code>BBCMF29</code>) 比對錯誤檔裡有無資料
 * 
 * @param IDNO  身分證號
 * @param BRDTE 出生日期
 * @return
 */
public interface Bbcmf29Dao {
    /**
     * 取得 現金給付分案尾碼原則檔(<code>BBCMF09</code>) 承辦人分機號碼資料
     * 
     * @param apNo 受理編號
     * @return <code>data2</code> String物件
     */
    public String checkIdnoExist(String idNo,String brDte);

   
}
