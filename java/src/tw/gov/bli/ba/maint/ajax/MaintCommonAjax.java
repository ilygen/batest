package tw.gov.bli.ba.maint.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.MaintAjaxService;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.BeanUtility;

/**
 * Ajax Service for 維護作業
 * 
 * @author Kiyomi
 */
public class MaintCommonAjax {
    private static Log log = LogFactory.getLog(MaintCommonAjax.class);

    private SelectOptionService selectOptionService;
    private MaintService maintService;
    private MaintAjaxService maintAjaxService;


    public MaintAjaxService getMaintAjaxService() {
        return maintAjaxService;
    }


    public void setMaintAjaxService(MaintAjaxService maintAjaxService) {
        this.maintAjaxService = maintAjaxService;
    }


    /**
     * 依傳入條件 回傳預算累計成長率<br>
     * 
     * @param cpiYear 指數年度
     * @return
     */
//    public BigDecimal getCountCpi(String cpiYear) {
//        log.debug("執行 MaintCommonAjax.getSumCpi 開始 ...");
//        BigDecimal countCpi = maintAjaxService.getSumCpi(cpiYear);
//        log.debug("執行 MaintCommonAjax.getSumCpi 結束 ...");
//        return countCpi;
//    }

}
