package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Bapapaykind;

public interface BapapaykindDao {
    /**
     * 依傳入條件取得 給付種類對應科別參數檔(<code>BAPAPAYKIND</code>) 資料 
     * 
     * @param reType 收支區分
     * @return
     */
    public List<Bapapaykind> selectDataBy(String payKind);
}
