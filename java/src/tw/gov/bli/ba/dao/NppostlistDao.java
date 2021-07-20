package tw.gov.bli.ba.dao;

/**
 * DAO for 郵局代碼檔(<code>NPPOSTLIST</code>)
 * 
 * @author Rickychi
 */
public interface NppostlistDao {
    /**
     * 依傳入條件取得 郵局代碼檔(<code>NPPOSTLIST</code>) 郵局名稱<br>
     * 
     * @param postId 郵局電腦代號
     * @return <code>String</code>
     */
    public String selectPostNameBy(String postId);
}
