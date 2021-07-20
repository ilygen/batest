package tw.gov.bli.ba.dao;

import java.math.BigDecimal;

import tw.gov.bli.ba.domain.Bccmf42;

/**
 * DAO for 保密資料檔 (<code>BCCMF42</code>)
 * 
 * @author Noctis
 */
public interface Bccmf42Dao {
	/**
     * 取得 保密資料檔(<code>BCCMF42</code>) 是否有保密資料
     * 
     * @param idnNo 身分證號
     * @return <code>data</code> String物件
     */
    public BigDecimal checkIdnNoExist(String idnNo);
    
    /**
     * 取得 保密資料檔(<code>BCCMF42</code>) payTyp
     * 
     * @param idnNo 身分證號
     * @return <code>data</code> String物件
     */
    public Bccmf42 selectPayTypBy(String idnNo);

   
}
