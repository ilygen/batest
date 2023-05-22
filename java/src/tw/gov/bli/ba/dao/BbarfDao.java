package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Bbarf;


public interface BbarfDao {
    /**
     * 依傳入條件取得 現金給付應收未收檔(<code>BBARF</code>) 資料 
     * 
     * @param apNo 受理編號
     * @return
     */
    public Bbarf selectDataBy(String apNo, String seqNo);
    /**
     * 依傳入條件取得 現金給付應收未收檔(<code>BBARF</code>) 資料 
     * 
     * @param benIdno 受益人身分證號
     * @return
     */
    public List<Bbarf> selectDataForOnceWriteOff(String gvIdno);
}
