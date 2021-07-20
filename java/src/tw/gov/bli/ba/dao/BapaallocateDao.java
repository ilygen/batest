package tw.gov.bli.ba.dao;

import java.util.HashMap;

/**
 * DAO for 承辦人分案原則參數檔 (<code>BAPAALLOCATE</code>)
 * 
 * @author Rickychi
 */
public interface BapaallocateDao {
    /**
     * 取得 承辦人分案原則參數檔(<code>BAPAALLOCATE</code>) 承辦人分機號碼資料
     * 
     * @param empNo 員工編號
     * @param payCode 給付別
     * @return <code>empExt</code> String物件
     */
    public String selectEmpExtDataBy(String empNo, String payCode);

    /**
     * 取得 承辦人分案原則參數檔(<code>BAPAALLOCATE</code>) 員工編號
     * 
     * @param apNo 受理編號
     * @return <code>empNo</code> String物件
     */
    public String selectEmpNoBy(String apNo);
}
