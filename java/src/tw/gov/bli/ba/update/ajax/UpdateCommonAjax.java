package tw.gov.bli.ba.update.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import tw.gov.bli.ba.domain.Baparam;
import tw.gov.bli.ba.domain.Bbcmf07;
import tw.gov.bli.ba.domain.Npbanklist;
import tw.gov.bli.ba.domain.Bcbpf;
import tw.gov.bli.ba.domain.Npcode;
import tw.gov.bli.ba.domain.Pbbmsa;
import tw.gov.bli.ba.payment.cases.PaymentProcessCase;
import tw.gov.bli.ba.receipt.ajax.ReceiptCommonAjax;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateAjaxService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustBenDataCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustBenDetailDataCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;

/**
 * Ajax Service for 更正作業
 * 
 * @author Rickychi
 */
public class UpdateCommonAjax {
    private static Log log = LogFactory.getLog(ReceiptCommonAjax.class);

    private SelectOptionService selectOptionService;
    private UpdateService updateService;
    private UpdateAjaxService updateAjaxService;

    /**
     * 依傳入條件 檢查核定格式<br>
     * 
     * @param apNo 受理編號
     * @param notifyForm 核定格式
     * @return
     */
    public String[] checkNotifyForm(String apNo) {
        log.debug("執行 UpdateCommonAjax.checkNotifyForm 開始 ...");
        List<String> resultList = selectOptionService.selectNotifyFormBy(apNo);
        String[] notifyForms = new String[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            notifyForms[i] = resultList.get(i);
        }
        log.debug("執行 UpdateCommonAjax.checkNotifyForm 結束 ...");
        return notifyForms;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 最新1筆的核付日期
     * 
     * @param apNo 受理編號
     * @return
     */
    public String getMaxAplPayDate(String apNo) {
        log.debug("執行 UpdateCommonAjax.getMaxAplPayDate 開始 ...");
        String maxAplPayDate = updateService.getMaxAplPayDate(apNo);
        if (StringUtils.isNotBlank(maxAplPayDate) && maxAplPayDate.length() == 8) {
            maxAplPayDate = DateUtility.changeDateType(maxAplPayDate);
        }
        log.debug("執行 UpdateCommonAjax.getMaxAplPayDate 結束 ...");
        return maxAplPayDate;
    }
    
    /**
     * 依傳入條件取得 出生日期錯誤參數檔(<code>BBCMF29</code>) 比對錯誤檔裡有無資料
     * 
     * @param IDNO  身分證號
     * @param BRDTE 出生日期
     * @return
     */
    public String checkIdnoExist(String idNo,String brDte) {
        log.debug("執行 UpdateCommonAjax.checkIdnoExist 開始 ...");
        
        String idnoExist = updateService.checkIdnoExist(idNo,brDte);
        
        log.debug("執行 UpdateCommonAjax.checkIdnoExist 結束 ...");
        return idnoExist;
    }

    /**
     * 依傳入條件 回傳戶籍郵遞區號<br>
     * 
     * @param benIdnNo 身份證號
     * @param benBrDate 出生日期
     * @return
     */
    public String getCvldtlZip(String benIdnNo, String benBrDate) {
        log.debug("執行 UpdateCommonAjax.getCvldtlZip 開始 ...");
        String zip = updateService.getCvldtlZip(benIdnNo, DateUtility.changeDateType(benBrDate));
        log.debug("執行 UpdateCommonAjax.getCvldtlZip 結束 ...");
        return zip;
    }
    /**
     * 依傳入條件 取得郵遞區號<br>
     * 
     * @param addr 地址
     * @return
     */
    public String getZipCode(String addr){
    	log.debug("執行 UpdateCommonAjax.getZipCode 開始 ...");
    	String zip = "";
    	if(StringUtils.isNotBlank(addr)){
    		if(addr.length()>=6){
    			zip = updateService.getZipCode(addr.substring(0,6));
    		}else{
    			zip = "";
    		}
        }else{
        	zip = "";
        }
        log.debug("執行 UpdateCommonAjax.getZipCode 結束 ...");
    	return zip;
    }
    
    public String getAddress(String mapnoMk){
    	HttpServletRequest request = HttpHelper.getHttpRequest();
    	log.debug("執行 UpdateCommonAjax.getAddress 開始 ...");
    	if(StringUtils.isNotBlank(mapnoMk)){
    		String addr = "";
        	if(StringUtils.isNotBlank(mapnoMk)){
        		List<PaymentProcessCase> caseList = CaseSessionHelper.getPaymentProcessDetailList(request);
        		if(Integer.parseInt(mapnoMk) > 0){
        			int apnoMk = Integer.parseInt(mapnoMk)-1;
        			String apno = caseList.get(apnoMk).getApno();
        			String seqNo = caseList.get(apnoMk).getSeqNo();
        			PaymentProcessCase addrData = updateService.getAddrData(apno,seqNo);
        	        if(addrData != null){
        	        	addr = addrData.getAddr();
        	        }
        		}        				
            }
        	log.debug("執行 UpdateCommonAjax.getAddress 結束 ...");
            return addr;
    	}else{
    		return "";
    	}		
    }
    public String getZip(String mapnoMk){
    	HttpServletRequest request = HttpHelper.getHttpRequest();
    	log.debug("執行 UpdateCommonAjax.getZipcode 開始 ...");
    	if(StringUtils.isNotBlank(mapnoMk)){
    		String zip = "";
        	if(StringUtils.isNotBlank(mapnoMk)){
        		List<PaymentProcessCase> caseList = CaseSessionHelper.getPaymentProcessDetailList(request);
        		if(Integer.parseInt(mapnoMk) > 0){
        			int apnoMk = Integer.parseInt(mapnoMk)-1;
        			String apno = caseList.get(apnoMk).getApno();
        			String seqNo = caseList.get(apnoMk).getSeqNo();
        			PaymentProcessCase addrData = updateService.getAddrData(apno,seqNo);
        	        if(addrData != null){
        	        	zip = addrData.getZipCode();
        	        }
        		}        				
            }
        	log.debug("執行 UpdateCommonAjax.getZipcode 結束 ...");
            return zip;
    	}else{
    		return "";
    	}		
    }
    /**
     * 依傳入條件 回傳戶籍地址<br>
     * 
     * @param benIdnNo 身份證號
     * @param benBrDate 出生日期
     * @return
     */
    public String getCvldtlAddr(String benIdnNo, String benBrDate) {
        log.debug("執行 UpdateCommonAjax.getCvldtlAddr 開始 ...");
        String addr = updateService.getCvldtlAddr(benIdnNo, DateUtility.changeDateType(benBrDate));
        log.debug("執行 UpdateCommonAjax.getCvldtlAddr 結束 ...");
        return addr;
    }

    /**
     * 依傳入條件 回傳受款人姓名<br>
     * 
     * @param ubNo 受款人身分證號
     * @return
     */
    public String getCaubUname(String ubNo) {
        log.debug("執行 UpdateCommonAjax.getCaubUname 開始 ...");
        String uname = updateService.getCaubUname(ubNo);
        log.debug("執行 UpdateCommonAjax.getCaubUname 結束 ...");
        return uname;
    }

    /**
     * 依傳入條件 回傳受款人姓名<br>
     * 
     * @param ubNo 受款人身分證號
     * @return
     */
    public String getCaubCzpcd(String ubNo) {
        log.debug("執行 UpdateCommonAjax.getCaubUname 開始 ...");
        String uname = updateService.getCaubCzpcd(ubNo);
        log.debug("執行 UpdateCommonAjax.getCaubUname 結束 ...");
        return uname;
    }

    /**
     * 依傳入條件 回傳受款人姓名<br>
     * 
     * @param ubNo 受款人身分證號
     * @return
     */
    public String getCaubCaddr(String ubNo) {
        log.debug("執行 UpdateCommonAjax.getCaubUname 開始 ...");
        String uname = updateService.getCaubCaddr(ubNo);
        log.debug("執行 UpdateCommonAjax.getCaubUname 結束 ...");
        return uname;
    }

    /**
     * 依傳入條件 回傳受款人姓名<br>
     * 
     * @param ubNo 受款人身分證號
     * @return
     */
    public String getCaubTel(String ubNo) {
        log.debug("執行 UpdateCommonAjax.getCaubUname 開始 ...");
        String uname = updateService.getCaubTel(ubNo);
        log.debug("執行 UpdateCommonAjax.getCaubUname 結束 ...");
        return uname;
    }

    /**
     * 依傳入條件 回傳受款人姓名<br>
     * 
     * @param gvCd1 受款人身分證號
     * @return
     */
    public String getBbcmf08Data(String gvCd1) {
        log.debug("執行 UpdateCommonAjax.getBbcmf08Data 開始 ...");
        String dataCount = updateService.getBbcmf08Data(gvCd1);
        log.debug("執行 UpdateCommonAjax.getBbcmf08Data 結束 ...");
        return dataCount;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 處理狀態
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>String</code> 物件
     */
    public String getBaappbaseProcStat(String apNo) {
        log.debug("執行 UpdateCommonAjax.getBaappbaseProcStat 開始 ...");
        String procStat = updateAjaxService.getBaappbaseProcStat(apNo);
        log.debug("執行 UpdateCommonAjax.getBaappbaseProcStat 結束 ...");
        return procStat;
    }

    /**
     * 依傳入關係條件取得 給付方式(<code>BAAPPBASE</code>) 下拉選單資料
     * 
     * @param relation 關係
     * @return
     */
    public List<Baparam> getPattyOptionList(String relation) {

        List<Baparam> pattyOptionList = updateAjaxService.getPattyOptionList(relation);

        return pattyOptionList;
    }

    /**
     * 依傳入銀行帳號取得 檢查檢查銀行帳號前7碼(<code>BCBPF</code>)
     * 
     * @param payBankIdBranchId 檢查銀行帳號
     * @return
     */
    public boolean checkBankAccount(String payBankIdBranchId) {

        return updateAjaxService.checkBankAccount(payBankIdBranchId);
    }

    /**
     * 依傳入關係條件取得 申請代算單位(<code>PBBMSA</code>) 下拉式選單資料
     * 
     * @param apNo 受理編號
     * @param brDate 事故者出生日期
     * @param idnNo 事故者身份證字號
     * @return
     */
    public List<Pbbmsa> getOldAplDpt(String apNo, String brDate, String idnNo, String oldAplDpt) {

        List<Pbbmsa> oldAplDptList = updateAjaxService.getOldAplDpt(apNo, brDate, idnNo, oldAplDpt);

        return oldAplDptList;
    }

    /**
     * 依傳入的 醫療院所代碼 自 醫療院所參數檔 (<code>BBCMF07<code>) 取得 醫療院所名稱
     * 
     * @param hosId 醫院代碼
     * @return
     */
    public String getHosName(String hosId) {
        Bbcmf07 hosData = updateAjaxService.getHosDataBy(hosId);
        if (hosData != null)
            return hosData.getHpSnam();
        else
            return "";
    }

    /**
     * 依傳入的條件 自 (<code>CVLDTL<code>) 取得 戶籍姓名
     * 
     * @param idNo 身分證號
     * @param brDate 出身日期
     * @return
     */
    public String getCvldtlName(String idNo, String brDate) {
        return updateAjaxService.getCvldtlName(idNo, brDate);
    }

    /**
     * 依傳入的條件 自 (<code>CVLDTL<code>) 取得 眷屬資料
     * 
     * @param apNo 受理編號
     * @param famIdnNo 眷屬身分證號
     * @param famBrDate 眷屬出生日期
     * @return
     */
    public Integer getDependantDataCount(String apNo, String famIdnNo, String bafamilyId) {
        return updateAjaxService.getDependantDataCount(apNo, famIdnNo, bafamilyId);
    }

    /**
     * 依傳入的條件 自 (<code>CVLDTL<code>) 取得 眷屬資料
     * 
     * @param apNo 受理編號
     * @param famIdnNo 眷屬身分證號
     * @param famBrDate 眷屬出生日期
     * @return
     */
    public Integer getEvtDataCount(String apNo, String famIdnNo, String bafamilyId) {
        return updateAjaxService.getEvtDataCount(apNo, famIdnNo, bafamilyId);
    }

    /**
     * 依傳入的條件 取得SurvivorCheckMarkLevelAdjustCase中的資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public SurvivorCheckMarkLevelAdjustBenDataCase[] getBenFilterdataList(String payYm, String seqNo) {
        return updateAjaxService.getBenFilterdataList(payYm, seqNo);
    }

    /**
     * 依傳入的條件 更新SurvivorCheckMarkLevelAdjustCase中的資料
     * 
     * @param baChkFileId 資料列編號
     * @param adjLevel 程度調整
     * @param valisYm 有效年月 - 起
     * @param valieYm 有效年月 - 迄
     * @return
     */
    public void setCheckMarkLevel(String baChkFileId, String adjLevel, String valisYm, String valieYm) {
        updateAjaxService.setCheckMarkLevel(baChkFileId, adjLevel, valisYm, valieYm);
    }
    
    /**
     * 依傳入的條件 更新SurvivorCheckMarkLevelAdjustCase中的資料
     * 
     * @param regetCipbMk 重讀CIPB
     * @return
     */
    public void setRegetCipbMk(String apNo, String regetCipbMk) {                        
        updateAjaxService.setRegetCipbMk(apNo, regetCipbMk);
    }    
    
    /**
     * 依傳入的條件 取得SurvivorCheckMarkLevelAdjustCase中事故者編審資料
     * 
     * @return
     */
    public SurvivorCheckMarkLevelAdjustBenDetailDataCase[] getEvtDetailDataCase(){
        return updateAjaxService.getEvtDetailDataCase();
    }
    
    /**
     * 依傳入條件取得 學校 資料
     * 
     * @param schoolName
     * @return
     */
    public List<Npcode> getSchoolDataBy(String schoolName) {
        return updateAjaxService.getSchoolListBy(schoolName);
    }    

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setUpdateAjaxService(UpdateAjaxService updateAjaxService) {
        this.updateAjaxService = updateAjaxService;
    }

}
