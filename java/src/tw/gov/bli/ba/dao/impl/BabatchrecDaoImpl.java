package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BabatchrecDao;
import tw.gov.bli.ba.domain.Babatchrec;
import tw.gov.bli.ba.domain.Baunacprec;
import tw.gov.bli.ba.update.cases.ChkFileCase;
import tw.gov.bli.ba.bj.cases.BatchRecTemp;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 批次作業記錄檔 (<code>BABATCHREC</code>)
 * 
 * @author swim
 */
@DaoTable("BABATCHREC")
public class BabatchrecDaoImpl extends SqlMapClientDaoSupport implements BabatchrecDao {
    /**
     * 依傳入條件取得 批次作業記錄檔(<code>BABATCHREC</code>) 資料 FOR 給付媒體回押註記
     * 
     * @param upTimeBeg 處理日期起日
     * @param UpTimeEnd 處理日期迄日
     * @param batchTyp 批次處理類型
     * @return <code>List<Babatchrec></code> 物件
     */
    @DaoFieldList("UPTIME,BATCHTYP")
    public List<Babatchrec> selectUpdatePaidMarkBJListBy(String upTimeBeg, String UpTimeEnd, String batchTyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(upTimeBeg))
            map.put("upTimeBeg", upTimeBeg);
        if (StringUtils.isNotBlank(UpTimeEnd))
            map.put("UpTimeEnd", UpTimeEnd);
        if (StringUtils.isNotBlank(batchTyp))
            map.put("batchTyp", batchTyp);

        return getSqlMapClientTemplate().queryForList("BABATCHREC.selectData", map);
    }
    
    /**
     * 依傳入條件取得 批次作業記錄檔(<code>BABATCHREC</code>) 資料 FOR 給付媒體回押註記
     * 
     * @param baBatchRecId 
     * @return <code>Babatchrec</code> 物件
     */
    @DaoFieldList("BABATCHRECID")
    public Babatchrec selectUpdatePaidMarkBJBy(String baBatchRecId) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(baBatchRecId))
            map.put("baBatchRecId", baBatchRecId);

        return (Babatchrec) getSqlMapClientTemplate().queryForObject("BABATCHREC.selectData", map);
    }

    /**
     * 更新 批次作業記錄檔(<code>BABATCHREC</code>) 給付媒體回押註記
     * 
     * @param babatchrec 批次作業記錄檔
     */
    public void updateUpdatePaidMarkBJ(Babatchrec babatchrec) {
        getSqlMapClientTemplate().update("BABATCHREC.updateData", babatchrec);
    }

    /**
     * 依傳入條件取得 批次作業記錄檔(<code>BABATCHREC</code>) 資料 FOR 給付媒體回押註記
     * 
     * @param upTimeBeg 處理日期起日
     * @param UpTimeEnd 處理日期迄日
     * @param batchTyp 批次處理類型
     * @return <code>List<Babatchrec></code> 物件
     */
    @DaoFieldList("UPTIME,BATCHTYP")
    public List<Babatchrec> selectReturnWriteOffBJListBy(String upTimeBeg, String UpTimeEnd, String batchTyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(upTimeBeg))
            map.put("upTimeBeg", upTimeBeg);
        if (StringUtils.isNotBlank(UpTimeEnd))
            map.put("UpTimeEnd", UpTimeEnd);
        if (StringUtils.isNotBlank(batchTyp))
            map.put("batchTyp", batchTyp);

        return getSqlMapClientTemplate().queryForList("BABATCHREC.selectData", map);
    }

    /**
     * 更新 批次作業記錄檔(<code>BABATCHREC</code>) 給付媒體回押註記
     * 
     * @param babatchrec 批次作業記錄檔
     */
    public void updateReturnWriteOffBJ(Babatchrec babatchrec) {
        getSqlMapClientTemplate().update("BABATCHREC.updateData", babatchrec);
    }

    /**
     * 新增資料到 批次作業記錄檔(<code>BABATCHREC</code>)
     * 
     * @param data <code>Babatchrec</code> 物件
     */
    public BigDecimal insertData(Babatchrec data) {
        if (data != null)
            return (BigDecimal) getSqlMapClientTemplate().insert("BABATCHREC.insertData", data);
        else
            return null;
    }

    /**
     * 新增資料到 批次作業記錄檔(<code>BABATCHREC</code>)
     * 
     * @param data <code>Babatchrectmp</code> 物件
     */
    public void insertBaBatchRecTmpData(BatchRecTemp data) {
        if (data != null)
            getSqlMapClientTemplate().insert("BABATCHREC.insertBaBatchRecTmpData", data);
    }
    
    /**
     * 更新 批次作業記錄檔(<code>BABATCHREC</code>) 給付媒體回押註記
     * 
     * @param babatchrec 批次作業記錄檔  ProFlag = 1
     */
    public void updateUpdatePaidMarkBJDetailProFlag1(Babatchrec data) {
        getSqlMapClientTemplate().update("BABATCHREC.updateProcStatDataProFlag1", data);
    }
    
    /**
     * 更新 批次作業記錄檔(<code>BABATCHREC</code>) 給付媒體回押註記
     * 
     * @param babatchrec 批次作業記錄檔  ProFlag = 2
     */
    public void updateUpdatePaidMarkBJDetailProFlag2(Babatchrec data) {
        getSqlMapClientTemplate().update("BABATCHREC.updateProcStatDataProFlag2", data);
    }
}
