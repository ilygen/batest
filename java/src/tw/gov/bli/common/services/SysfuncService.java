package tw.gov.bli.common.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.common.dao.PortalDao;
import tw.gov.bli.common.dao.SysfuncDao;
import tw.gov.bli.common.domain.SystemFunction;

/**
 * Framework 初始化用的 Service
 * 
 * @author Goston
 */
public class SysfuncService {
    private static Log log = LogFactory.getLog(SysfuncService.class);

    private SysfuncDao sysfuncDao;
    private PortalDao portalDao; 

    /**
     * 依應用系統代號取得系統功能清單
     * 
     * @param systemId 應用系統代號
     * @return 內含 <code>SystemFunction</code> 物件的 List
     */
    public List<SystemFunction> selectSysFuncBySystemId(String systemId) {
        return sysfuncDao.selectSysFuncBySystemId(systemId);
    }

    /**
     * 依應用系統代號取得系統功能清單 for CAS
     * 
     * @param systemId 應用系統代號
     * @return 內含 <code>SystemFunction</code> 物件的 List
     */
    public List<SystemFunction> selectCasSysFuncBySystemId(String systemId) {
        List<SystemFunction> list = sysfuncDao.selectCasSysFuncBySystemId();
        
        for (SystemFunction systemFunction : list) {
            List<SystemFunction> tempList = portalDao.selectCasSysFuncByItemId(systemFunction.getItemId());
            if (tempList != null & tempList.size() > 0) {
                systemFunction.setItemName(tempList.get(0).getItemName());
                if (StringUtils.isBlank(systemFunction.getUrlDesc()))
                    systemFunction.setUrlDesc(tempList.get(0).getItemName());
            }
        }
        
        return list;
    }

    public void setSysfuncDao(SysfuncDao sysfuncDao) {
        this.sysfuncDao = sysfuncDao;
    }

    public void setPortalDao(PortalDao portalDao) {
        this.portalDao = portalDao;
    }

    
}
