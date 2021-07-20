package tw.gov.bli.ba.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BcbpfDao;
import tw.gov.bli.ba.domain.Bcbpf;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 銀行資料檔(<code>BCBPF</code>)
 * 
 * @author Noctis
 */
@DaoTable("BCBPF")
public class BcbpfDaoImpl extends SqlMapClientDaoSupport implements BcbpfDao {
    /**
     * 依傳入條件取得 銀行資料檔(<code>BCBPF</code>) 金融機構資料<br>
     * 
     * @param payBankIdBranchId 帳號(前)
     * @return <code>String</code>
     */
    @DaoFieldList("BNKALL||BNKONO")
    public List<Bcbpf> selectBankDataBy(String payBankIdBranchId) {
        return getSqlMapClientTemplate().queryForList("BCBPF.selectBankDataBy", payBankIdBranchId);
    }

    /**
     * 依傳入條件取得 銀行資料檔(<code>BCBPF</code>) 金融機構名稱<br>
     * 傳入「帳號」(前：1~3碼)||'0000'), 回傳 金融機構名稱一<br>
     * 傳入「帳號」(前), 回傳 金融機構名稱二<br>
     * 
     * @param payBankIdBranchId 帳號(前)
     * @return <code>String</code>
     */
    @DaoFieldList("BNKALL||BNKONO")
    public String selectBankNameBy(String payBankIdBranchId) {
        return (String) getSqlMapClientTemplate().queryForObject("BCBPF.selectBankNameBy", payBankIdBranchId);
    }

}
