package tw.gov.bli.ba.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.NpbanklistDao;
import tw.gov.bli.ba.domain.Npbanklist;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 銀行資料檔(<code>NPBANKLIST</code>)
 * 
 * @author Rickychi
 */
@DaoTable("NPBANKLIST")
public class NpbanklistDaoImpl extends SqlMapClientDaoSupport implements NpbanklistDao {
    /**
     * 依傳入條件取得 銀行資料檔(<code>NPBANKLIST</code>) 金融機構資料<br>
     * 
     * @param payBankIdBranchId 帳號(前)
     * @return <code>String</code>
     */
    @DaoFieldList("MAIN_CODE||BRANCH_CODE")
    public List<Npbanklist> selectBankDataBy(String payBankIdBranchId) {
        return getSqlMapClientTemplate().queryForList("NPBANKLIST.selectBankDataBy", payBankIdBranchId);
    }

    /**
     * 依傳入條件取得 銀行資料檔(<code>NPBANKLIST</code>) 金融機構名稱<br>
     * 傳入「帳號」(前：1~3碼)||'0000'), 回傳 金融機構名稱一<br>
     * 傳入「帳號」(前), 回傳 金融機構名稱二<br>
     * 
     * @param payBankIdBranchId 帳號(前)
     * @return <code>String</code>
     */
    @DaoFieldList("MAIN_CODE||BRANCH_CODE")
    public String selectBankNameBy(String payBankIdBranchId) {
        return (String) getSqlMapClientTemplate().queryForObject("NPBANKLIST.selectBankNameBy", payBankIdBranchId);
    }

}
