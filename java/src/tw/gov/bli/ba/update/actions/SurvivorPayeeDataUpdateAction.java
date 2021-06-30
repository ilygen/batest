package tw.gov.bli.ba.update.actions;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import org.apache.commons.lang.StringUtils;
import com.iisi.SecureToken;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bachkfile;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateDetailForm;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;

/**
 * 遺屬受款人資料更正BaseAction
 * @author Azuritul
 *
 */
public class SurvivorPayeeDataUpdateAction extends BaseDispatchAction{
	
	protected UpdateService updateService;
    protected SelectOptionService selectOptionService;
    protected RptService rptService;

    /**
     * 清除Session裡所存的Case資料
     * @param request HttpServletRequest
     */
    protected void cleanCaseInSession(HttpServletRequest request){
        CaseSessionHelper.removeSurvivorPayeeDataUpdateQueryCase(request);
        CaseSessionHelper.removeSurvivorPayeeDataUpdateDetailCase(request);
        CaseSessionHelper.removeSurvivorPayeeDataUpdateList(request);
        CaseSessionHelper.removeSurvivorPayeeDataForBadaprCase(request);
    }

    /**
     * 清除Session裡所存的Form資料
     * @param request HttpServletRequest
     */
    protected void cleanFormInSession(HttpServletRequest request){
        FormSessionHelper.removeSurvivorPayeeDataUpdateQueryForm(request);
        FormSessionHelper.removeSurvivorPayeeDataUpdateDetailForm(request);
    }
    
    /**
     * 清除Session裡所存的Form及Case資料
     * @param request HttpServletRequest
     */
    protected void cleanDataInSession(HttpServletRequest request){
        cleanCaseInSession(request);
        cleanFormInSession(request);
    }

    /**
     * 呼叫即時編審WebService
     * @param apNo 受理編號
     * @return String
     * @throws javax.xml.rpc.ServiceException ServiceException
     * @throws java.rmi.RemoteException RemoteException
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
     * 回資料列表頁面前，需取得更新過的遺屬受款人清單資料
     * @param apNo 受理編號,用來取得更新過的遺屬受款人資料
     * @return List<SurvivorPayeeDataUpdateCase>
     */
	protected List<SurvivorPayeeDataUpdateCase> refetchSurvivorPayeeDataUpdateCaseList(String apNo, String seqNo) {
		List<SurvivorPayeeDataUpdateCase> applyList = updateService.selectSurvivorPayeeDataForList(apNo, seqNo);
		for(SurvivorPayeeDataUpdateCase o : applyList){
			//如果非本人，且為事故者本人的繼承者，則設定其繼承自受款人為事故者本人
			if( !"0000".equals(o.getSeqNo())  && "00".equals(o.getSeqNo().substring(2))){
				o.setAppUser( applyList.get(0).getEvtName() );
				o.setIsHeir("N");//顯示哪個修改按鈕(修改繼承人或修改受款人) isHeir為"Y"的話則為繼承人,
			}
			//如果是本人，則繼承自受款人為空白
			else if("0000".equals(o.getSeqNo())) {
				o.setAppUser("");
				o.setIsHeir("N");
			}
			//如果非上述情況，則找出其上層的受款人
			else {
				o.setAppUser(getUpperNode(o.getApNo(), o.getSeqNo()));
				o.setIsHeir("Y");
			}
		}
		//如果有下層的受款人,那也不給刪除
		//判斷下層有受款人繼承的條件:
		//有人前兩碼的seqno與自己相同
		List<SurvivorPayeeDataUpdateCase> list = new ArrayList<SurvivorPayeeDataUpdateCase>(applyList);
		for(SurvivorPayeeDataUpdateCase o : applyList){
			String seqno = o.getSeqNo();
            String isHeir = o.getIsHeir();
            if (payeeHasHeir(seqno, isHeir, list)){
                o.setHasHeir("Y");
            }
		}
		return applyList;
	}

    /**
     * 檢查看該受款人之下有沒有下層的繼承人
     * @param payeeSeqNo  該受款人序號
     * @param payeeIsHeir 該受款人本身是否為繼承人　
     * @param list        要比對的受款人List
     * @return boolean
     */
    protected boolean payeeHasHeir(String payeeSeqNo, String payeeIsHeir, List<SurvivorPayeeDataUpdateCase> list) {
        for (SurvivorPayeeDataUpdateCase p : list) {
            //若傳進的List底下有不等於自己,且該受款人本身不是繼承人,且前兩碼有與自己相等之資料,則該受款人之下層有繼承人
            if (!payeeSeqNo.equals(p.getSeqNo()) && !"Y".equals(payeeIsHeir) && payeeSeqNo.subSequence(0, 2).equals(p.getSeqNo().subSequence(0, 2))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取得傳進來的受款人序號上層之受款人
     * @param apNo  代理編號
     * @param seqNo 受款人序號　
     * @return String
     */
    protected String getUpperNode(String apNo, String seqNo){
        String upperNode = updateService.getDisabledAncestorData(apNo, seqNo);
        return StringUtils.defaultString(upperNode);
    }

    /**
     * 取得並儲存結案原因下拉選單
     * @param request
     */
    protected void setCloseCauseDropDownMenu(HttpServletRequest request) {
        request.getSession().setAttribute(ConstantKey.SURVIVOR_CLOSE_OPTION_LIST, selectOptionService.getSurvivorCloseCauseOptionList());
    }
    
    /**
     * 取得並儲存不合格原因下拉選單
     * @param request
     */
    protected void setUnqualifiedCauseDropDownMenu(HttpServletRequest request) {
        request.getSession().setAttribute(ConstantKey.SURVIVOR_UNQUALIFIEDCAUSE_OPTION_LIST, selectOptionService.getSurvivorUnqualifiedCauseOptionList());
    }

    /**
     * 取得並儲存國籍下拉選單
     * @param request
     */
    protected void setCountryDropDownMenu(HttpServletRequest request) {
        request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
    }

    /**
     * 取得並儲存給付方式下拉選單
     * @param request
     * @param benEvtRel
     */
    protected void setPayTypeDropDownMenu(HttpServletRequest request, String benEvtRel) {
        request.getSession().setAttribute(ConstantKey.PAYTYP_OPTION_LIST, selectOptionService.getPayTypOptionList(benEvtRel));
    }

    /**
     * 取得並儲存受款人與事故者關係下拉選單
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
     * 取回Session中儲存的具名領取下拉選單
     *
     * @param request
     * @return
     */
    protected List<Baappbase> getCoreceiveNameListDropDownMenu(HttpServletRequest request) {
        return (List<Baappbase>) request.getSession().getAttribute(ConstantKey.BEN_OPTION_LIST);
    }
    
    /**
     * 取得並儲存得請領起月下拉選單
     * @param request
     * @param evtDieDate 傳民國年的死亡日期
     */
    protected void setAbleApsymMenu(HttpServletRequest request, String evtDieDate) {
        List<String> able = new ArrayList<String>();
        if(StringUtils.isNotBlank(evtDieDate)){
        	if(Integer.parseInt(evtDieDate) >= 980101){
        		for(int i = 0 ; i < 13; i++){
                	able.add(DateUtility.calMonth(evtDieDate, i).substring(0, 5));
                }
        	} else {
        		for(int i = 0; i < 13; i++){
        			able.add(DateUtility.calMonth("0980101", i).substring(0, 5));
        		}
        	}
            request.getSession().setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_ABLEAPSYM_OPTION_LIST, able);
        }
    }

    /**
     * 取得遺屬編審註記
     * @param apNo
     * @param seqNo
     * @return Map<String, String>
     */
    protected Map<String, ArrayList<Bachkfile>> fetchChkmkList(String apNo,String seqNo) {
		List<Bachkfile> qualifyList = updateService.getSurvivorPayeeCheckMark(apNo, seqNo);
		// 遺屬註記及符合註記皆需以PAYYM來做分組,同一組PAYYM的為一行
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
	 * 取得遺屬符合註記QualifyCheckMark List
	 * 
	 * @param apNo
	 * @param seqNo
	 * @return Map<String, String>
	 */
    protected Map<String, ArrayList<Bachkfile>> fetchQualifyChkmkList(String apNo, String seqNo){
    	List<Bachkfile> qualifyList = updateService.getSurvivorPayeeQualifyCheckMark(apNo, seqNo);

    	// 遺屬註記及符合註記皆需以PAYYM來做分組,同一組PAYYM的為一行
        // 格式例: 09809：21 , 25
        TreeSet<String> keySet = new TreeSet<String>();
        for(Bachkfile o : qualifyList){
        	keySet.add(o.getPayYmStr());
        }
        Map<String, ArrayList<Bachkfile>> map = new TreeMap<String, ArrayList<Bachkfile>>();
        for (String key : keySet) {
        	map.put(key, new ArrayList<Bachkfile>());
        }
        for(Bachkfile o : qualifyList){
            for (String key : keySet) {
                if (o.getPayYmStr().equals(key)) {
                	(map.get(key)).add(o);
                }
            }
        }
        return map;
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }

    public void setRptService(RptService rptService){
    	this.rptService = rptService;
    }

    /**
     * 將SurvivorPayeeDataUpdateCase中的日期欄位轉換為西元年
     * @param detailData
     * @param detailForm
     * @return
     */
	protected SurvivorPayeeDataUpdateCase convertCaseDateToWesternStyle(SurvivorPayeeDataUpdateCase detailData, SurvivorPayeeDataUpdateDetailForm detailForm) {
		SurvivorPayeeDataUpdateCase caseData = detailData;
        caseData.setAppDate(convertToWesternDate(detailForm.getAppDate()));
        caseData.setBenBrDate(convertToWesternDate(detailForm.getBenBrDate()));
        caseData.setBenDieDate(convertToWesternDate(detailForm.getBenDieDate()));
        caseData.setAbanApSym(convertToWesternDate(detailForm.getAbanApSym()));
        caseData.setAbleApsYm(convertToWesternDate(detailForm.getAbleApsYm()));
        caseData.setMarryDate(convertToWesternDate(detailForm.getMarryDate()));
        caseData.setDigaMyDate(convertToWesternDate(detailForm.getDigaMyDate()));
        caseData.setRelatChgDate(convertToWesternDate(detailForm.getRelatChgDate()));
        caseData.setAdoptDate(convertToWesternDate(detailForm.getAdoptDate()));
        caseData.setBenMissingSdate(convertToWesternDate(detailForm.getBenMissingSdate()));
        caseData.setBenMissingEdate(convertToWesternDate(detailForm.getBenMissingEdate()));
        caseData.setPrisonSdate(convertToWesternDate(detailForm.getPrisonSdate()));
        caseData.setPrisonEdate(convertToWesternDate(detailForm.getPrisonEdate()));
        caseData.setGrdBrDate(convertToWesternDate(detailForm.getGrdBrDate()));
        caseData.setAssignBrDate(convertToWesternDate(detailForm.getAssignBrDate()));
        caseData.setInterdictDate(convertToWesternDate(detailForm.getInterdictDate()));
        caseData.setRepealInterdictDate(convertToWesternDate(detailForm.getRepealInterdictDate()));
		return caseData;
	}

    /**
     * 將延伸主檔的日期轉換為民國年顯示
     * @param expandDataCase
     * @return
     */
    protected Baappexpand convertDateToChineseYear(Baappexpand expandDataCase){
    	Baappexpand expand = expandDataCase;
        expand.setAbanApsYm(convertToChineseDate(expandDataCase.getAbanApsYm()));
        expand.setAbleApsYm(convertToChineseDate(expandDataCase.getAbleApsYm()));
        expand.setRelatChgDate(convertToChineseDate(expandDataCase.getRelatChgDate()));
        expand.setAdoPtDate(convertToChineseDate(expandDataCase.getAdoPtDate()));
        expand.setBenMissingSdate(convertToChineseDate(expandDataCase.getBenMissingSdate()));
        expand.setBenMissingEdate(convertToChineseDate(expandDataCase.getBenMissingEdate()));
        expand.setPrisonSdate(convertToChineseDate(expandDataCase.getPrisonSdate()));
        expand.setPrisonEdate(convertToChineseDate(expandDataCase.getPrisonEdate()));
        expand.setMarryDate(convertToChineseDate(expandDataCase.getMarryDate()));
        expand.setDigamyDate(convertToChineseDate(expandDataCase.getDigamyDate()));
        expand.setAssignBrDate(convertToChineseDate(expandDataCase.getAssignBrDate()));
        expand.setInterDictDate(convertToChineseDate(expandDataCase.getInterDictDate()));
        expand.setRepealInterdictDate(convertToChineseDate(expandDataCase.getRepealInterdictDate()));
    	return expand;
    }

    /**
     * 將SurvivorPayeeDataUpdateDetailForm的日期轉為民國年顯示
     * @param updateForm
     * @return
     */
    protected SurvivorPayeeDataUpdateDetailForm convertDateToChineseYear(SurvivorPayeeDataUpdateDetailForm updateForm) {
		SurvivorPayeeDataUpdateDetailForm form = updateForm;
        form.setAppDate(convertToChineseDate(updateForm.getAppDate()));
        form.setBenBrDate(convertToChineseDate(updateForm.getBenBrDate()));
        form.setBenDieDate(convertToChineseDate(updateForm.getBenDieDate()));
        form.setAbanApSym(convertToChineseDate(updateForm.getAbanApSym()));
        form.setAbleApsYm(convertToChineseDate(updateForm.getAbleApsYm()));
        form.setRelatChgDate(convertToChineseDate(updateForm.getRelatChgDate()));
        form.setAdoptDate(convertToChineseDate(updateForm.getAdoptDate()));
        form.setBenMissingSdate(convertToChineseDate(updateForm.getBenMissingSdate()));
        form.setBenMissingEdate(convertToChineseDate(updateForm.getBenMissingEdate()));
        form.setPrisonSdate(convertToChineseDate(updateForm.getPrisonSdate()));
        form.setPrisonEdate(convertToChineseDate(updateForm.getPrisonEdate()));
        form.setMarryDate(convertToChineseDate(updateForm.getMarryDate()));
        form.setDigaMyDate(convertToChineseDate(updateForm.getDigaMyDate()));
        form.setGrdBrDate(convertToChineseDate(updateForm.getGrdBrDate()));
        form.setAssignBrDate(convertToChineseDate(updateForm.getAssignBrDate()));
        form.setInterdictDate(convertToChineseDate(updateForm.getInterdictDate()));
        form.setRepealInterdictDate(convertToChineseDate(updateForm.getRepealInterdictDate()));
        form.setCloseDate(convertToChineseDate(updateForm.getCloseDate()));
        form.setIdnChkYm(convertToChineseDate(updateForm.getIdnChkYm()));
		return form;
	}

    /**
     * 將傳進來的民國日期轉為西元日期
     * @param date
     * @return
     */
    private String convertToWesternDate(String date) {
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
     * @param date
     * @return
     */
    private String convertToChineseDate(String date) {
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
}
