package tw.gov.bli.ba.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BafamilyDao;
import tw.gov.bli.ba.dao.BaparamDao;
import tw.gov.bli.ba.dao.Bbcmf07Dao;
import tw.gov.bli.ba.dao.CvldtlDao;
import tw.gov.bli.ba.dao.NpbanklistDao;
import tw.gov.bli.ba.dao.BcbpfDao;
import tw.gov.bli.ba.dao.BacpidtlDao;
import tw.gov.bli.ba.dao.PbbmsaDao;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.domain.Baparam;
import tw.gov.bli.ba.domain.Bbcmf07;
import tw.gov.bli.ba.domain.Cvldtl;
import tw.gov.bli.ba.domain.Npbanklist;
import tw.gov.bli.ba.domain.Bcbpf;
import tw.gov.bli.ba.domain.Pbbmsa;
import tw.gov.bli.ba.update.actions.SurvivorCheckMarkLevelAdjustAction;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustBenDataCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustBenDetailDataCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Ajax Service for 更正作業
 * 
 * @author Kiyomi
 */
public class MaintAjaxService {
    private static Log log = LogFactory.getLog(MaintAjaxService.class);
    private BaappbaseDao baappbaseDao;
    private BaparamDao baparamDao;
    private PbbmsaDao pbbmsaDao;
    private Bbcmf07Dao bbcmf07Dao;
    private CvldtlDao cvldtlDao;
    private BafamilyDao bafamilyDao;
    private NpbanklistDao npbanklistDao;
    private BcbpfDao bcbpfDao;
    private BacpidtlDao bacpidtlDao;
    
    /**
     * 依傳入條件取得 物價指數調整明細檔(<code>Bacpidtl</code>) 預算累計成長率
     * 
     * @param cpiYear 指數年度
     * @return
     */
//    public BigDecimal getSumCpi(String cpiYear) {
//        BigDecimal countCpi = null;
//        // 取得物價指數調整明細檔資料
//
//        List<Bacpidtl> bacpidtlList = bacpidtlDao.selectSumCpiData(cpiYear);
//        
//        if (bacpidtlList.size() > 0)
//            countCpi = bacpidtlList.get(0).getCountCpi();
//        else
//            countCpi = null;        
//        
//
//        return countCpi;
//    }     
    
    

    public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
        this.baappbaseDao = baappbaseDao;
    }

    public void setBaparamDao(BaparamDao baparamDao) {
        this.baparamDao = baparamDao;
    }

    public void setPbbmsaDao(PbbmsaDao pbbmsaDao) {
        this.pbbmsaDao = pbbmsaDao;
    }

    public void setBbcmf07Dao(Bbcmf07Dao bbcmf07Dao) {
        this.bbcmf07Dao = bbcmf07Dao;
    }

    public void setCvldtlDao(CvldtlDao cvldtlDao) {
        this.cvldtlDao = cvldtlDao;
    }

    public void setBafamilyDao(BafamilyDao bafamilyDao) {
        this.bafamilyDao = bafamilyDao;
    }

    public void setNpbanklistDao(NpbanklistDao npbanklistDao) {
        this.npbanklistDao = npbanklistDao;
    }
    
    public void setBcbpfDao(BcbpfDao bcbpfDao) {
        this.bcbpfDao = bcbpfDao;
    }
    
    public void setBacpidtlDao(BacpidtlDao bacpidtlDao) {
        this.bacpidtlDao = bacpidtlDao;
    }    

}
