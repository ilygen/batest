package tw.gov.bli.ba.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.domain.Basenimaint;

/**
 * DAO for 年資維護介接檔 (<code>BASENIMAINT</code>)
 * 
 * @author Rickychi
 */
public interface BasenimaintDao {
    /**
     * 依傳入條件取得 年資維護介接檔(<code>BASENIMAINT</code>) 資料List
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param seniTyp 處理狀態
     * @return 內含 <code>Basenimaint</code> 物件的 List
     */
    public List<Basenimaint> selectDataBy(String apNo, String seqNo, String seniTyp);

    /**
     * 依傳入條件取得 年資維護介接檔(<code>BASENIMAINT</code>) 序號
     * 
     * @param apNo 受理編號
     * @param seniTyp 處理狀態
     * @return
     */
    public String selectSeqNoBy(String apNo, String seniTyp);

    /**
     * 新增 年資維護介接檔(<code>BASENIMAINT</code>) 資料
     * 
     * @param basenimaint 年資維護介接檔
     * @return
     */
    public void insertData(Basenimaint basenimaint);

    /**
     * 修改 年資維護介接檔(<code>BASENIMAINT</code>) 資料
     * 
     * @param basenimaint 給付主檔
     * @return
     */
    public void updateData(Basenimaint basenimaint);

    /**
     * 修改 年資維護介接檔(<code>BASENIMAINT</code>) 資料 for 一次請領之年資採計方式
     * 
     * @param basenimaint 給付主檔
     * @return
     */
    public void updateDataForOldSeniab(Basenimaint basenimaint);

    /**
     * 刪除 年資維護介接檔(<code>BASENIMAINT</code>) 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param seniTyp 年資類別
     * @return
     */
    public void deleteData(String apNo, String seqNo, String seniTyp);
}
