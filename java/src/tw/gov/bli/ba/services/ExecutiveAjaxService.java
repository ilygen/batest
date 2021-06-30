package tw.gov.bli.ba.services;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BabanDao;
import tw.gov.bli.ba.dao.BapandomkDao;
import tw.gov.bli.ba.dao.BaparamDao;
import tw.gov.bli.ba.dao.Bbcmf07Dao;
import tw.gov.bli.ba.dao.Bbcmf13Dao;
import tw.gov.bli.ba.domain.Bapandomk;
import tw.gov.bli.ba.domain.Baparam;

public class ExecutiveAjaxService {
    private static Log log = LogFactory.getLog(ExecutiveAjaxService.class);
    private BapandomkDao bapandomkDao;
    private BaparamDao baparamDao;
    private BaappbaseDao baappbaseDao;
    private BabanDao babanDao;
    private Bbcmf07Dao bbcmf07Dao;
    private Bbcmf13Dao bbcmf13Dao;

    /**
     * 自 處理註記參數檔 (<code>BAPANDOMK</code>) 取得 處理註記 下拉式選單的資料
     * 
     * @return
     */
    public List<Bapandomk> getNdomkOptionList(String letterType, String lpaymk, String kpaymk, String spaymk) {
        return bapandomkDao.selectListBy( letterType, lpaymk, kpaymk, spaymk);
    }
    
    /**
     * 自 處理註記參數檔 (<code>BAPANDOMK</code>) 取得 行政救濟辦理情形 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getReliefOptionList(String reliefKind) {
        return baparamDao.selectReliefOptionListBy(reliefKind);
    }
    
    /**
     * 依傳入的條件取得 是否為已註銷 (<code>BAAPPBASE</code>) 的案件資料<br>
     * 
     * @param apNo 受理編號
     * @return 
     */
    public Integer getProcastatStatus(String apNo) {
        return baappbaseDao.getProcastatStatus(apNo);
    }

    /**
     * 依傳入條件 (<code>BABAN</code>) 取得 查調病歷的資料
     * 
     * @return
     */
    
    public Integer getBabanCount(String apNo, String issuYm, String proDte, String clinNo) {  
        return babanDao.getBabanCount(apNo, issuYm, proDte, clinNo);
    }
    
    /**
     * 依傳入條件 (<code>BBCMF07</code>) 取得 醫療院所參數檔的資料
     * 
     * @return
     */
    
    public String selectHpSnamBy(String hpNo) {  
        return bbcmf07Dao.selectHpSnamBy(hpNo);
    }
    
    /**
     * 依傳入條件 (<code>BBCMF13</code>) 取得 勞保管制對象參數檔的資料
     * 
     * @return
     */
    
    public Integer selectBbcmf13CountBy(String hpNo, String hpSnam) {  
        return bbcmf13Dao.selectBbcmf13CountBy(hpNo, hpSnam);
    }
    
    
    /**
     * 依傳入條件 (<code>BABAN</code>) 取得 查調病歷的資料
     * 
     * @return
     */
    
    public String getBabanPayMk(String apNo, String issuYm, String proDte, String clinNo) {  
        return babanDao.getBabanPayMk(apNo, issuYm, proDte, clinNo);
    }
    

    public void setBapandomkDao(BapandomkDao bapandomkDao) {
        this.bapandomkDao = bapandomkDao;
    }

    public void setBaparamDao(BaparamDao baparamDao) {
        this.baparamDao = baparamDao;
    }
    
    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }
    
    public void setBabanDao(BabanDao babanDao) {
        this.babanDao = babanDao;
    }

    public void setBbcmf07Dao(Bbcmf07Dao bbcmf07Dao) {
        this.bbcmf07Dao = bbcmf07Dao;
    }
    
    public void setBbcmf13Dao(Bbcmf13Dao bbcmf13Dao) {
        this.bbcmf13Dao = bbcmf13Dao;
    }
    
}
