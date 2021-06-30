package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BaunacprecDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baunacprec;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 應收帳務記錄檔 (<code>BAUNACPREC</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BAUNACPREC")
public class BaunacprecDaoImpl extends SqlMapClientDaoSupport implements BaunacprecDao {

    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 資料 for 應收查詢
     * 
     * @param apNo 受理編號
     * @param benIdnNo 事故者身分證號
     * @param unacpSdate 立帳起日
     * @param unacpEdate 立帳迄日
     * @return 內含 <code>Baunacprec</code> 物件的 List
     */
    @DaoFieldList("APNO,BENIDNNO,UNACPSDATE,UNACPEDATE")
    public List<Baunacprec> selectReceivableDataBy(String apNo, String benIdnNo, String unacpSdate, String unacpEdate) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(benIdnNo))
            map.put("benIdnNo", benIdnNo);
        if (StringUtils.isNotBlank(unacpSdate))
            map.put("unacpSdate", unacpSdate);
        if (StringUtils.isNotBlank(unacpEdate))
            map.put("unacpEdate", unacpEdate);
        return getSqlMapClientTemplate().queryForList("BAUNACPREC.selectReceivableDataBy", map);
    }

    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 資料 for 轉催收作業
     * 
     * @param unacpSdate 立帳起日
     * @param unacpEdate 立帳迄日
     * @param recKind 收回種類
     * @return 內含 <code>Baunacprec</code> 物件的 List
     */
    @DaoFieldList("UNACPSDATE,UNACPEDATE,RECKIND")
    public List<Baunacprec> selectTrans2OverdueReceivableDataBy(String unacpSdate, String unacpEdate, String recKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(unacpSdate))
            map.put("unacpSdate", unacpSdate);
        if (StringUtils.isNotBlank(unacpEdate))
            map.put("unacpEdate", unacpEdate);
        if (StringUtils.isNotBlank(recKind))
            map.put("recKind", recKind);
        return getSqlMapClientTemplate().queryForList("BAUNACPREC.selectTrans2OverdueReceivableDataBy", map);
    }

    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 應收總筆數資料
     * 
     * @param unacpSdate 立帳起日
     * @param unacpEdate 立帳迄日
     * @param recKind 收回種類
     * @return <code>BigDecimal</code> 物件
     */
    @DaoFieldList("UNACPSDATE,UNACPEDATE,RECKIND")
    public BigDecimal selectTotalAmtBy(String unacpSdate, String unacpEdate, String recKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(unacpSdate))
            map.put("unacpSdate", unacpSdate);
        if (StringUtils.isNotBlank(unacpEdate))
            map.put("unacpEdate", unacpEdate);
        if (StringUtils.isNotBlank(recKind))
            map.put("recKind", recKind);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAUNACPREC.selectTotalAmtBy", map);
    }

    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 應收總金額資料
     * 
     * @param unacpSdate 立帳起日
     * @param unacpEdate 立帳迄日
     * @param recKind 收回種類
     * @return <code>BigDecimal</code> 物件
     */
    @DaoFieldList("UNACPSDATE,UNACPEDATE,RECKIND")
    public BigDecimal selectSumRecAmtBy(String unacpSdate, String unacpEdate, String recKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(unacpSdate))
            map.put("unacpSdate", unacpSdate);
        if (StringUtils.isNotBlank(unacpEdate))
            map.put("unacpEdate", unacpEdate);
        if (StringUtils.isNotBlank(recKind))
            map.put("recKind", recKind);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAUNACPREC.selectSumRecAmtBy", map);
    }

    /**
     * 更新 應收帳務記錄檔(<code>BAUNACPREC</code>) 收回種類資料
     * 
     * @param Baunacprec 應收帳務記錄檔
     */
    public void updateRecKind(Baunacprec baunacprec) {
        getSqlMapClientTemplate().update("BAUNACPREC.updateRecKind", baunacprec);
    }

    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 已收調整作業 Master資料
     * 
     * @param apNo 受理編號
     * @param benIdnNo $受款人身份證號
     * @param recKind 收回種類
     * @return 內含<code>Baunacprec</code> 物件的List
     */
    @DaoFieldList("APNO,BENIDNNO,RECKIND")
    public List<Baunacprec> selectReceivableAdjMasterDataBy(String apNo, String benIdnNo, String recKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(benIdnNo))
            map.put("benIdnNo", benIdnNo);
        if (StringUtils.isNotBlank(recKind))
            map.put("recKind", recKind);
        return getSqlMapClientTemplate().queryForList("BAUNACPREC.selectReceivableAdjMasterDataBy", map);
    }

    /**
     * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 已收調整作業 Detail資料
     * 
     * @param baunacprecId 應收記錄編號
     * @return <code>Baunacprec</code> 物件
     */
    @DaoFieldList("BAUNACPRECID")
    public Baunacprec selectReceivableAdjDetailDataBy(BigDecimal baunacprecId) {
        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        if (StringUtils.isNotBlank(baunacprecId.toString()))
            map.put("baunacprecId", baunacprecId);
        return (Baunacprec) getSqlMapClientTemplate().queryForObject("BAUNACPREC.selectReceivableAdjDetailDataBy", map);
    }
    
    /**
     * 更新 應收帳務記錄檔(<code>BAUNACPREC</code>) 資料 for 已收調整作業
     * 
     * @param Baunacprec 應收帳務記錄檔
     */
    public void updateForReceivableAdj(Baunacprec baunacprec) {
        getSqlMapClientTemplate().update("BAUNACPREC.updateForReceivableAdj", baunacprec);
    }
}
