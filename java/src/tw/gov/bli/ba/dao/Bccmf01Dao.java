package tw.gov.bli.ba.dao;

import java.math.BigDecimal;

/**
 * DAO for 資料檔 (<code>BCCMF01</code>)
 * 
 * @author Noctis
 */
public interface Bccmf01Dao {
	
	/**
     * 取得 保密資料檔(<code>BCCMF01</code>) 是否可查看保密資料
     * 
     * @param prpNo 身分證號
     * @return <code>data</code> String物件
     */
    public BigDecimal selectBccmf01CheckCount(String prpNo);

   
}
