package tw.gov.bli.ba.update.actions;

import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bachkfile;
import tw.gov.bli.ba.domain.Baparam;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.TreeSet;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import com.iisi.SecureToken;

/**
 * User: Chris
 * Date: 2009/10/5
 * Time: 上午 11:30:46
 */
public class DisabledPayeeDataUpdateAction extends BaseDispatchAction {

    protected UpdateService updateService;
    protected SelectOptionService selectOptionService;
    protected RptService rptService;

    /**
     * 清除Session裡所存的Case資料
     *
     * @param request HttpServletRequest
     */
    protected void cleanCaseInSession(HttpServletRequest request) {
        CaseSessionHelper.removeDisabledPayeeDataUpdateQueryCase(request);
        CaseSessionHelper.removeDisabledPayeeDataUpdateDetailCase(request);
        CaseSessionHelper.removeDisabledPayeeDataUpdateList(request);
        //CaseSessionHelper.removeDisabledPayeeDataForBadaprCase(request);
    }

    /**
     * 清除Session裡所存的Form資料
     *
     * @param request HttpServletRequest
     */
    protected void cleanFormInSession(HttpServletRequest request) {
        FormSessionHelper.removeDisabledPayeeDataUpdateQueryForm(request);
        FormSessionHelper.removeDisabledPayeeDataUpdateDetailForm(request);
    }

    /**
     * 清除Session裡所存的Form及Case資料
     *
     * @param request HttpServletRequest
     */
    protected void cleanDataInSession(HttpServletRequest request) {
        cleanCaseInSession(request);
        cleanFormInSession(request);
    }

    /**
     * @param apNo
     * @return
     */
    protected List<BaappbaseDataUpdateCase> fetchDisabledPayeeDataUpdateCaseList(String apNo) {
        List<BaappbaseDataUpdateCase> list = updateService.selectDisabledPayeeDataForList(apNo);
        for (BaappbaseDataUpdateCase o : list) {
            //如果非本人，且為事故者本人的繼承者，則設定其繼承自受款人為事故者本人
            if (!"0000".equals(o.getSeqNo()) && "00".equals(o.getSeqNo().substring(2))) {
                o.setAppUser(list.get(0).getEvtName());
                o.setIsHeir("N");//顯示哪個修改按鈕(修改繼承人或修改受款人) isHeir為"Y"的話則為繼承人,
            }
            //如果是本人，則繼承自受款人為空白
            else if ("0000".equals(o.getSeqNo())) {
                o.setAppUser("");
                o.setIsHeir("N");
            }
            //如果非上述情況，則找出其上層的受款人
            else {
                o.setAppUser(updateService.getDisabledAncestorData(o.getApNo(), o.getSeqNo()));
                o.setIsHeir("Y");
            }
        }
        return list;
    }

    /**
     * 事故者死亡日期是否可以update
     *
     * @param apNo
     * @return
     */
    protected boolean isEvtDieDateUpdatable(String apNo) {
        Boolean b = updateService.isEvtDieDateUpdatableForDisabledPayee(apNo);
        return b.booleanValue();
    }

    /**
     * 呼叫即時編審WebService
     *
     * @param apNo 受理編號
     * @return String
     * @throws javax.xml.rpc.ServiceException ServiceException
     * @throws java.rmi.RemoteException       RemoteException
     */
    protected String callCheckMarkWebService(String apNo) throws ServiceException, RemoteException {
    	// String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
        String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
    	log.info("webServiceUrl: "+ webServiceUrl);
        SingleCheckMarkServiceHttpBindingStub binding;
        binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator().getSingleCheckMarkServiceHttpPort();
        return binding.doCheckMark(apNo,SecureToken.getInstance().getToken());
    }

    /**
     * 取得並儲存國籍下拉選單
     *
     * @param request
     */
    protected void setCountryDropDownMenu(HttpServletRequest request) {
        request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
    }

    /**
     * 取得並儲存給付方式下拉選單
     *
     * @param request
     * @param benEvtRel
     */
    protected void setPayTypeDropDownMenu(HttpServletRequest request, String benEvtRel) {
        request.getSession().setAttribute(ConstantKey.PAYTYP_OPTION_LIST, selectOptionService.getPayTypOptionList(benEvtRel));
    }

    /**
     * 取得並儲存受款人與事故者關係下拉選單
     *
     * @param request
     * @param benEvtRel
     */
    protected void setRelationDropDownMenu(HttpServletRequest request, String benEvtRel) {
        if (StringUtils.isBlank(benEvtRel)) {
            request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getRelationOptionList());
        }
        if (!"1".equals(benEvtRel)) {
            request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getRelationOptionList());
        } else {
            request.getSession().setAttribute(ConstantKey.PAYEE_RELATIION_OPTION_LIST, selectOptionService.getDisabledPayeeRelationOptionList());
        }
    }

    /**
     * 取得並儲存具名領取下拉選單
     *
     * @param request
     * @param caseBaappbaseId
     * @param apNo
     * @param selfBaappbaseId
     */
    protected void setCoreceiveNameListDropDownMenu(HttpServletRequest request, BigDecimal caseBaappbaseId, String apNo, String selfBaappbaseId) {
        List<Baappbase> benList = selectOptionService.getDisabledBenOptionList(caseBaappbaseId, apNo);
        List<Baappbase> dropDownList = new ArrayList<Baappbase>(benList);
        //具名領取清單要把自己濾掉
        for (Baappbase o : benList) {
            if (selfBaappbaseId != null && o.getBaappbaseId() != null && selfBaappbaseId.equals(o.getBaappbaseId().toString())) {
                dropDownList.remove(o);
            }
        }
        request.getSession().setAttribute(ConstantKey.BEN_OPTION_LIST, dropDownList);
    }

    /**
     * 2009.12.11新增(因User尚未提供失能受款人結案原因的選項,目前先抓失能案件資料更正的結案原因)
     * 取得並儲存結案原因下拉選單
     *
     * @param request
     */
    protected void setCloseCauseDropDownMenu(HttpServletRequest request, String payCode) {
        request.getSession().setAttribute(ConstantKey.CLOSECAUSE_OPTION_LIST, selectOptionService.getCloseCauseOptionList(payCode));
    }

    /**
     * 2009.12.14 Added
     * 取得受款人編審註記,直接用遺屬已經寫好的
     *
     * @param apNo
     * @param seqNo
     * @return Map<String, String>
     */
    protected Map<String, ArrayList<Bachkfile>> fetchChkmkList(String apNo, String seqNo) {
        List<Bachkfile> qualifyList = updateService.getSurvivorPayeeCheckMark(apNo, seqNo);
        // 格式例: 09809：21 , 25
        TreeSet<String> keySet = new TreeSet<String>();
        for (Bachkfile o : qualifyList) {
            keySet.add(o.getPayYmStr());
        }

        Map<String, ArrayList<Bachkfile>> map = new TreeMap<String, ArrayList<Bachkfile>>();
        for (String key : keySet) {
            map.put(key, new ArrayList<Bachkfile>());
        }
        for (Bachkfile o : qualifyList) {
            for (String key : keySet) {
                if (o.getPayYmStr().equals(key)) {
                    (map.get(key)).add(o);
                }
            }
        }

        return map;
    }

    /**
     * 取回Session中儲存的具名領取下拉選單
     *
     * @param request
     * @return
     */
    protected List<Baappbase> getCoreceiveNameListDropDownMenu(HttpServletRequest request) {
        return (List<Baappbase>) request.getSession().getAttribute(ConstantKey.BEN_OPTION_LIST);
    }

    /**
     * 將傳進來的民國日期轉為西元日期
     *
     * @param date
     * @return
     */
    protected String convertToWesternDate(String date) {
        if (StringUtils.isNotBlank(date)) {
            if ((date.trim().length()) == 5) {
                return (DateUtility.changeDateType(date + "01").substring(0, 6));
            }
            if ((date.trim().length() == 7)) {
                return DateUtility.changeDateType(date);
            }
        }
        return StringUtils.defaultString(date);
    }

    /**
     * 將傳進來的西元日期轉為民國日期
     *
     * @param date
     * @return
     */
    protected String convertToChineseDate(String date) {
        if (StringUtils.isNotBlank(date)) {
            if ((date.trim().length()) == 8) {
                return DateUtility.changeDateType(date);
            }
            if ((date.trim().length() == 6)) {
                return (DateUtility.changeDateType(date + "01")).substring(0, 5);
            }
        }
        return StringUtils.defaultString(date);
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }

}
