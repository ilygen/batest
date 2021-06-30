package tw.gov.bli.ba.dao;

import java.util.HashMap;

import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 現金給付分案尾碼原則檔 (<code>BBCMF08</code>)
 * 
 * @author swim
 */
public interface Bbcmf08Dao {
    /**
     * 取得 現金給付分案尾碼原則檔(<code>BBCMF08</code>) 承辦人分機號碼資料
     * 
     * @param gvCd1 員工編號
     * @return <code>data2</code> String物件
     */
    public String selectBbcmf08DataBy(String gvCd1);
}
