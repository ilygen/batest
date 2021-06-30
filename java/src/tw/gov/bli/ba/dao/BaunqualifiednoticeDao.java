package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Baarclist;
import tw.gov.bli.ba.domain.Baunqualifiednotice;

/**
 * DAO for 不合格核定通知紀錄檔 (<code>BAUNQUALIFIEDNOTICE</code>)
 * 
 * @author KIYOMI
 */
public interface BaunqualifiednoticeDao {

    /**
     * 查詢 不合格核定通知紀錄檔(<code>BAUNQUALIFIEDNOTICE</code>) 核定通知紀錄檔資料 for 給付查詢-核定通知記錄
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baunqualifiednotice> selectPaymentQueryData(String apNo);
    
    /**
     * 新增 不合格核定通知紀錄檔(<code>BAUNQUALIFIEDNOTICE</code>) 資料
     * 
     * @param BAUNQUALIFIEDNOTICE 不合格核定通知紀錄檔
     * @return
     */
    public void insertData(final List<Baunqualifiednotice> baunqualifiednoticeList);
}
