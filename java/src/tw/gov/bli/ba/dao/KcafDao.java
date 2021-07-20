package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Kcaf;

public interface KcafDao {
    /**
     * 依傳入的條件查詢 被保險人關鍵欄位變更檔 (<code>KCAF</code>) 的資料 FOR 註記BC
     * 
     * @param aIdNo  改後身分證號 
     * @param aBrDte 改後出生日期
     * @return
     */
    public List<Kcaf> selectByAIdnAndABrDte(String aIdn,String aBrDte);
}
