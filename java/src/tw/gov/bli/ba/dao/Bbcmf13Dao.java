package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Bbcmf07;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 勞保管制對象參數檔 (<code>BBCMF13</code>)
 * 
 * @author Noctis
 */
public interface Bbcmf13Dao {

    /**
     * 依傳入的條件取得 勞保管制對象參數檔(<code>BBCMF13</code>) 是否為列管醫院
     * 
     * @param hpNo 醫院代碼
     * @param hpSnam 醫院名稱
     * @return
     */
    public Integer selectBbcmf13CountBy(String hpNo, String hpSnam);
    
  
}
