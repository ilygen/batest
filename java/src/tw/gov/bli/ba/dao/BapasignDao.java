package tw.gov.bli.ba.dao;

public interface BapasignDao {
    /**
     * 查詢 核定通知書署名參數檔 (<code>BAPASIGN</code>) 目前任職的總經理
     * 
     * @return
     */
    public String selectManager(String aplpayDate);
}
