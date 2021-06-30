package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapaallocateDao;
import tw.gov.bli.ba.domain.Bapaallocate;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 承辦人分案原則參數檔 (<code>BAPAALLOCATE</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BAPAALLOCATE")
public class BapaallocateDaoImpl extends SqlMapClientDaoSupport implements BapaallocateDao {
    /**
     * 取得 承辦人分案原則參數檔(<code>BAPAALLOCATE</code>) 承辦人分機號碼資料
     * 
     * @param empNo 員工編號
     * @param payCode 給付別
     * @return <code>empExt</code> String物件
     */
    @DaoFieldList("EMPNO,PAYCODE")
    public String selectEmpExtDataBy(String empNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("empNo", empNo);
        map.put("payCode", payCode);
        return (String) getSqlMapClientTemplate().queryForObject("BAPAALLOCATE.selectEmpExtDataBy", map);
    }

    /**
     * 取得 承辦人分案原則參數檔(<code>BAPAALLOCATE</code>) 員工編號
     * 
     * @param apNo 受理編號
     * @return <code>empNo</code> String物件
     */
    @DaoFieldList("APNO")
    public String selectEmpNoBy(String apNo) {
        if (apNo.length() == 13) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("apNo", apNo);
            return (String) getSqlMapClientTemplate().queryForObject("BAPAALLOCATE.selectEmpNoBy", map);
        }
        else {
            return "";
        }
    }
}
