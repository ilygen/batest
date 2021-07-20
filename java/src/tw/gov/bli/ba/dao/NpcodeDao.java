package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Npcode;

/**
 * DAO for 學校代碼檔 (<code>NPCODE</code>)
 * 
 * @author Noctis
 */
public interface NpcodeDao {
	 /**
     * 取得 學校代碼檔 (<code>NPCODE</code>) 國保核定資料 FOR 失能年金給付查詢
     * @return
     */
    public List<Npcode> selectNpCodeOptionBy();
    
    /**
     * 取得 學校代碼檔 (<code>NPCODE</code>) FOR 眷屬資料更正 遺屬年金受款人資料更正
     * @return
     */
    public Npcode selectNpCodeNameBy(String schoolCode);
    
    /**
     * 取得 學校代碼檔 (<code>NPCODE</code>) FOR 眷屬資料更正 遺屬年金受款人資料更正
     * @return
     */
    public List<Npcode> selectSchoolListBy(String schoolName);    

}
