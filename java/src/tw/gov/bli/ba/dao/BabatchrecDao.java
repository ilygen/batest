package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.update.cases.ChkFileCase;
import tw.gov.bli.ba.bj.cases.BatchRecTemp;
import tw.gov.bli.ba.domain.Babatchrec;

/**
 * DAO for 批次作業記錄檔 (<code>BABATCHREC</code>)
 * 
 * @author swim
 */
public interface BabatchrecDao {
    /**
     * 依傳入條押註記
     * 
     * @param upTimeBeg 處理日期起日
     * @param UpTimeEnd 處理日期迄日
     * @param batchTyp 批次處理類型
     * @return <code>List<Babatchrec></code> 物件
     */
    public List<Babatchrec> selectUpdatePaidMarkBJListBy(String upTimeBeg, String UpTimeEnd, String batchTyp);
    
    /**
     * 依傳入條押註記
     * 
     * @param baBatchRecId 
     * @return <code>Babatchrec</code> 物件
     */
    public Babatchrec selectUpdatePaidMarkBJBy(String baBatchRecId);

    /**
     * 更新 批次作業記錄檔(<code>BABATCHREC</code>) 給付媒體回押註記
     * 
     * @param babatchrec 批次作業記錄檔
     */
    public void updateUpdatePaidMarkBJ(Babatchrec babatchrec);

    /**
     * 依傳入條件取得 批次作業記錄檔(<code>BABATCHREC</code>) 資料 FOR 收回作業
     * 
     * @param upTimeBeg 處理日期起日
     * @param UpTimeEnd 處理日期迄日
     * @param batchTyp 批次處理類型
     * @return <code>List<Babatchrec></code> 物件
     */
    public List<Babatchrec> selectReturnWriteOffBJListBy(String upTimeBeg, String UpTimeEnd, String batchTyp);

    /**
     * 更新 批次作業記錄檔(<code>BABATCHREC</code>) 收回作業
     * 
     * @param babatchrec 批次作業記錄檔
     */
    public void updateReturnWriteOffBJ(Babatchrec babatchrec);

    /**
     * 新增資料到 批次作業記錄檔(<code>BABATCHREC</code>)
     * 
     * @param data <code>Babatchrec</code> 物件
     */
    public BigDecimal insertData(Babatchrec data);

    /**
     * 新增資料到 批次作業記錄檔(<code>BABATCHREC</code>)
     * 
     * @param data <code>Babatchrectmp</code> 物件
     */
    public void insertBaBatchRecTmpData(BatchRecTemp data);
    
    /**
     * 更新 批次作業記錄檔(<code>BABATCHREC</code>) 給付媒體回押註記
     * 
     * @param babatchrec 批次作業記錄檔  ProFlag = 1
     */
    public void updateUpdatePaidMarkBJDetailProFlag1(Babatchrec data);
    
    /**
     * 更新 批次作業記錄檔(<code>BABATCHREC</code>) 給付媒體回押註記
     * 
     * @param babatchrec 批次作業記錄檔  ProFlag = 2
     */
    public void updateUpdatePaidMarkBJDetailProFlag2(Babatchrec data);
}
