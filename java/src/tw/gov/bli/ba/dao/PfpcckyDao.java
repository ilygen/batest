package tw.gov.bli.ba.dao;

import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Pbbmsa;
import tw.gov.bli.ba.domain.Pfpccky;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 退現資料檔 (<code>PFPCCKY</code>) <br>
 * 
 * @author Noctis
 */
public interface PfpcckyDao {

    /**
     * 依傳入條件取得 退現資料檔 (<code>PFPCCKY</code>)  for 老年年金應收收回處理
     * 
     * @param apNo  受理編號
     * @return
     */
    public List<Pfpccky> selectCashReceiveDataListBy(String apNo);

    
}
