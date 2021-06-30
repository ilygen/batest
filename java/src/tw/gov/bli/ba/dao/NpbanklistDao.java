package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Npbanklist;

/**
 * DAO for 銀行資料檔(<code>NPBANKLIST</code>)
 * 
 * @author Rickychi
 */
public interface NpbanklistDao {
    /**
     * 依傳入條件取得 銀行資料檔(<code>NPBANKLIST</code>) 金融機構資料<br>
     * 
     * @param payBankIdBranchId 帳號(前)
     * @return <code>String</code>
     */
    public List<Npbanklist> selectBankDataBy(String payBankIdBranchId);
    
    /**
     * 依傳入條件取得 銀行資料檔(<code>NPBANKLIST</code>) 金融機構名稱<br>
     * 傳入「帳號」(前：1~3碼)||'0000'), 回傳 金融機構名稱一<br>
     * 傳入「帳號」(前), 回傳 金融機構名稱二<br>
     * 
     * @param payBankIdBranchId 帳號(前)
     * @return <code>String</code>
     */
    public String selectBankNameBy(String accountF);
}
