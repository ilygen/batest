package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.domain.Baunacprec;

/**
 * DAO for 應收帳務記錄檔 (<code>BAUNACPREC</code>)
 * 
 * @author Rickychi
 */
public interface BaunacprecDao {
    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 資料 for 應收查詢
     * 
     * @param apNo 受理編號
     * @param benIdnNo 事故者身分證號
     * @param unacpSdate 立帳起日
     * @param unacpEdate 立帳迄日
     * @return 內含 <code>Baunacprec</code> 物件的 List
     */
    public List<Baunacprec> selectReceivableDataBy(String apNo, String benIdnNo, String unacpSdate, String unacpEdate);

    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 資料 for 轉催收作業
     * 
     * @param unacpSdate 立帳起日
     * @param unacpEdate 立帳迄日
     * @param recKind 收回種類
     * @return 內含 <code>Baunacprec</code> 物件的 List
     */
    public List<Baunacprec> selectTrans2OverdueReceivableDataBy(String unacpSdate, String unacpEdate, String recKind);

    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 應收總筆數資料
     * 
     * @param unacpSdate 立帳起日
     * @param unacpEdate 立帳迄日
     * @param recKind 收回種類
     * @return <code>BigDecimal</code> 物件
     */
    public BigDecimal selectTotalAmtBy(String unacpSdate, String unacpEdate, String recKind);

    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 應收總金額資料
     * 
     * @param unacpSdate 立帳起日
     * @param unacpEdate 立帳迄日
     * @param recKind 收回種類
     * @return <code>BigDecimal</code> 物件
     */
    public BigDecimal selectSumRecAmtBy(String unacpSdate, String unacpEdate, String recKind);

    /**
     * 更新 應收帳務記錄檔(<code>BAUNACPREC</code>) 收回種類資料
     * 
     * @param Baunacprec 應收帳務記錄檔
     */
    public void updateRecKind(Baunacprec baunacprec);
    
    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 已收調整作業 Master資料
     * 
     * @param apNo 受理編號
     * @param benIdnNo $受款人身份證號
     * @param recKind 收回種類
     * @return 內含<code>Baunacprec</code> 物件的List
     */
    public List<Baunacprec> selectReceivableAdjMasterDataBy(String apNo, String benIdnNo, String recKind) ;

    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 已收調整作業 Detail資料
     * 
     * @param baunacprecId 應收記錄編號
     * @return <code>Baunacprec</code> 物件
     */
    public Baunacprec selectReceivableAdjDetailDataBy(BigDecimal baunacprecId) ;
    
    /**
     * 更新 應收帳務記錄檔(<code>BAUNACPREC</code>) 資料 for 已收調整作業
     * 
     * @param Baunacprec 應收帳務記錄檔
     */
    public void updateForReceivableAdj(Baunacprec baunacprec);
}
