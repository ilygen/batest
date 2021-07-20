package tw.gov.bli.ba.update.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateHandicapTermForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bahandicapterm;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import java.util.List;
import java.math.BigDecimal;

/**
 * 遺屬年金受款人資料更正 - 重殘起訖年月維護
 * 
 * 
 * 
 */
public class SurvivorPayeeDataUpdateHandicapTermAction extends SurvivorPayeeDataUpdateAction{

    private final static Log log = LogFactory.getLog(SurvivorPayeeDataUpdateHandicapTermAction.class);
    // 更正作業 - 遺屬受款人資料更正 - 重殘期間資料清單頁面
    private static final String FORWARD_HANDICAPTERM_DATA_LIST = "handicapTermeDataList";
    // 更正作業 - 遺屬受款人資料更正 - 由重殘期間資料回到受款人資料明細修改頁面
    private static final String FORWARD_BACK_TO_MODIFY = "backToModify";
    // 更正作業 - 遺屬受款人資料更正 - 由重殘期間資料回到受款人資料明細新增頁面
    private static final String FORWARD_BACK_TO_INSERT = "backToInsert";
    
    private UpdateService updateService;

    private static final String LOG_INFO_DOSAVE_START = "執行 遺屬年金受款人資料更正作業 - 新增重殘期間頁面 SurvivorPayeeDataUpdateHandicapTermAction.doSave() 開始 ... ";
    private static final String LOG_INFO_DOSAVE_ERROR = "執行 遺屬年金受款人資料更正作業 - 新增重殘期間頁面 SurvivorPayeeDataUpdateHandicapTermAction.doSave() 發生錯誤:";
    private static final String LOG_INFO_DOUPDATE_START = "執行 遺屬年金受款人資料更正作業 - 修改重殘期間頁面 SurvivorPayeeDataUpdateHandicapTermAction.doUpdate() 開始 ... ";
    private static final String LOG_INFO_DOUPDATE_ERROR = "執行 遺屬年金受款人資料更正作業 - 新增重殘期間頁面 SurvivorPayeeDataUpdateHandicapTermAction.doUpdate() 發生錯誤:";
    private static final String LOG_INFO_DODELETE_START = "執行 遺屬年金受款人資料更正作業 - 刪除重殘期間頁面 SurvivorPayeeDataUpdateHandicapTermAction.doDelete() 開始 ... ";
    private static final String LOG_INFO_DODELETE_ERROR = "執行 遺屬年金受款人資料更正作業 - 刪除重殘期間頁面SurvivorPayeeDataUpdateHandicapTermAction.doDelete() 發生錯誤:";
    private static final String LOG_INFO_DOBACK_START = "執行 遺屬年金受款人資料更正作業 - 重殘期間維護頁面(返回) SurvivorPayeeDataUpdateHandicapTermAction.doBack() 開始 ... ";
    private static final String LOG_INFO_DOBACK_ERROR = "執行 遺屬年金受款人資料更正作業 - 重殘期間維護頁面(返回) SurvivorPayeeDataUpdateHandicapTermAction.doBack() 發生錯誤:";


    /**
     * 重殘期間維護作業 - 遺屬年金受款人資料更正作業 - 新增重殘期間 <br/>
     * (BAMO0D289C01.jsp)<br/>
     * 按下「新增」的動作
     *  
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOSAVE_START);
        try {
            UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
            SurvivorPayeeDataUpdateHandicapTermForm termForm = (SurvivorPayeeDataUpdateHandicapTermForm)form;
            //受款人詳細資料的form
            SurvivorPayeeDataUpdateDetailForm detailForm =  FormSessionHelper.getSurvivorPayeeDataUpdateDetailForm(request);  
            //事故者的form
            SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);      
            //先看Session中是否已有資料
            List<Bahandicapterm> list = CaseSessionHelper.getSurvivorPayeeDataUpdateHandicaptermList(request);

            //新增資料,只存至Session中,不存資料庫
            Bahandicapterm toBeSaved = new Bahandicapterm();
            BigDecimal maxTermNo = getMaxTermNoFromHandicaptermList(list).add(new BigDecimal(1));

            toBeSaved.setApNo(queryForm.getApNo());
            toBeSaved.setSeqNo(detailForm.getSeqNo());
            toBeSaved.setHandicapSdate(termForm.getHandicapSdate());
            toBeSaved.setHandicapEdate(termForm.getHandicapEdate());
            toBeSaved.setTermNo(maxTermNo);
            toBeSaved.setCrtUser(userData.getEmpNo());
            toBeSaved.setCrtTime(DateUtility.getNowWestDateTime(true));
            toBeSaved.setUpdUser(userData.getEmpNo());
            toBeSaved.setUpdTime(DateUtility.getNowWestDateTime(true));

            list.add(toBeSaved);
            
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            String maxPayYm = updateService.selectMaxPayYmForSBy(apNo, "0000");
            String results = "";
            
            for (int i = 0; i < list.size(); i++) {
                String handicapEDate1 = list.get(i).getHandicapEdate();
                
                if (handicapEDate1.length() == 5) {
                    handicapEDate1 = DateUtility.changeChineseYearMonthType(handicapEDate1);
                }
                
                if (Integer.parseInt(handicapEDate1) > Integer.parseInt(maxPayYm)){
                    results = "Y";
                }
            }            
            
            FormSessionHelper.removeSurvivorPayeeDataUpdateHandicapTermForm(request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateHandicaptermList(list, request);
            if(list != null && !list.isEmpty()){
            	request.getSession().setAttribute("SURVIVOR_PAYEE_HANDICAPTERM_SIZE", list.size());
            } else {
            	request.getSession().setAttribute("SURVIVOR_PAYEE_HANDICAPTERM_SIZE", 0);
            }
            request.getSession().setAttribute("handicapResult", results);
            
            return mapping.findForward(FORWARD_HANDICAPTERM_DATA_LIST);
        } catch (Exception e) {
            log.error(LOG_INFO_DOSAVE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(FORWARD_HANDICAPTERM_DATA_LIST);
        }
    }
    
    /**
     * 重殘期間維護作業 - 遺屬年金受款人資料更正作業 - 修改重殘期間 <br/>
     * (BAMO0D289C01.jsp)<br/>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOUPDATE_START);
        try {
            UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
            SurvivorPayeeDataUpdateHandicapTermForm termForm = (SurvivorPayeeDataUpdateHandicapTermForm)form;
            //受款人詳細資料的form
            SurvivorPayeeDataUpdateDetailForm detailForm =  FormSessionHelper.getSurvivorPayeeDataUpdateDetailForm(request);  
            //事故者的form
            SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);      
            
            List<Bahandicapterm> list = CaseSessionHelper.getSurvivorPayeeDataUpdateHandicaptermList(request);
            //取得要修改的term
            Bahandicapterm term = getBahandicaptermByTermNo(termForm.getTermNo(), list); 
            term.setHandicapSdate(termForm.getHandicapSdate());
            term.setHandicapEdate(termForm.getHandicapEdate());
            term.setUpdUser(userData.getEmpNo());
            term.setUpdTime(DateUtility.getNowWestDateTime(true));

            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            String maxPayYm = updateService.selectMaxPayYmForSBy(apNo, "0000");
            String results = "";
            
            for (int i = 0; i < list.size(); i++) {
                String handicapEDate1 = list.get(i).getHandicapEdate();
                
                if (handicapEDate1.length() == 5) {
                    handicapEDate1 = DateUtility.changeChineseYearMonthType(handicapEDate1);
                }
                
                if (Integer.parseInt(handicapEDate1) > Integer.parseInt(maxPayYm)){
                    results = "Y";
                }
            }            
            
            FormSessionHelper.removeSurvivorPayeeDataUpdateHandicapTermForm(request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateHandicaptermList(list, request);
            
            if(list != null && !list.isEmpty()){
            	request.getSession().setAttribute("SURVIVOR_PAYEE_HANDICAPTERM_SIZE", list.size());
            } else {
            	request.getSession().setAttribute("SURVIVOR_PAYEE_HANDICAPTERM_SIZE", 0);
            }
            request.getSession().setAttribute("handicapResult", results);
            
            return mapping.findForward(FORWARD_HANDICAPTERM_DATA_LIST);
        } catch (Exception e) {
            log.error(LOG_INFO_DOUPDATE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(FORWARD_HANDICAPTERM_DATA_LIST);
        }
    }
    
    /**
     * 重殘期間維護作業 - 遺屬年金受款人資料更正作業 - 刪除重殘期間 <br/>
     * (BAMO0D289C01.jsp)<br/>
     * 按下「刪除」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DODELETE_START);
        try {
            SurvivorPayeeDataUpdateHandicapTermForm termForm = (SurvivorPayeeDataUpdateHandicapTermForm)form;
            
            //事故者的form
            SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);            
            System.out.println("in doDelete");
            List<Bahandicapterm> list = CaseSessionHelper.getSurvivorPayeeDataUpdateHandicaptermList(request);
            //取得要刪除的term
            System.out.println(termForm.getTermNo());
            Bahandicapterm term = getBahandicaptermByTermNo(termForm.getTermNo(), list);
            list.remove(term);
            
//            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
//            String maxPayYm = updateService.selectMaxPayYmForSBy(apNo, "0000");
//            String results = "";
//            
//            for (int i = 0; i < list.size(); i++) {
//                String handicapEDate1 = list.get(i).getHandicapEdate();
//                
//                if (handicapEDate1.length() == 5) {
//                    handicapEDate1 = DateUtility.changeChineseYearMonthType(handicapEDate1);
//                }
//                
//                if (Integer.parseInt(handicapEDate1) > Integer.parseInt(maxPayYm)){
//                    results = "Y";
//                }
//            }            

            FormSessionHelper.removeSurvivorPayeeDataUpdateHandicapTermForm(request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateHandicaptermList(list, request);
            
            if(list != null && !list.isEmpty()){
            	request.getSession().setAttribute("SURVIVOR_PAYEE_HANDICAPTERM_SIZE", list.size());
            } else {
            	request.getSession().setAttribute("SURVIVOR_PAYEE_HANDICAPTERM_SIZE", 0);
            }
            
            return mapping.findForward(FORWARD_HANDICAPTERM_DATA_LIST);
        } catch (Exception e) {
            log.error(LOG_INFO_DODELETE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(FORWARD_HANDICAPTERM_DATA_LIST);
        }
    }

    /**
     * 重殘期間維護作業 - 遺屬年金受款人資料更正作業 - 刪除重殘期間 <br/>
     * (BAMO0D289C01.jsp)<br/>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOBACK_START);
        try {
            FormSessionHelper.removeSurvivorPayeeDataUpdateHandicapTermForm(request);
            saveMessages(request.getSession(), null);
            
            String mode = (String)request.getSession().getAttribute("HandicapTermMode");
            if("I".equalsIgnoreCase(mode)){
            	request.getSession().removeAttribute("HandicapTermMode");
            	return mapping.findForward(FORWARD_BACK_TO_INSERT);
            }
            return mapping.findForward(FORWARD_BACK_TO_MODIFY);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOBACK_ERROR + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }

    }

    /**
     * 由重殘期間資料中取出最大的TermNo
     * 若無資料則回傳1
     * @param list
     * @return
     */
    private BigDecimal getMaxTermNoFromHandicaptermList(List<Bahandicapterm> list) {
        Bahandicapterm maxHandicapTerm = getMaxBahandicaptermFromList(list);
        if (maxHandicapTerm != null) {
            BigDecimal maxTermNo = maxHandicapTerm.getTermNo();
            return (maxTermNo == null)? new BigDecimal(0) : maxTermNo;
        }
        return new BigDecimal(0);
    }

    /**
     * 由重殘期間資料中取出最大的Bahandicapterm
     * @param list
     * @return
     */
    private Bahandicapterm getMaxBahandicaptermFromList(List<Bahandicapterm> list){
        if(!list.isEmpty()){
            return list.get(list.size() - 1);
        }
        return null;
    }

    /**
     * 由重殘期間資料中找出與傳入之TermNo相合之Bahandicapterm資料
     * @param termNo
     * @param list
     * @return
     */
    private Bahandicapterm getBahandicaptermByTermNo(String termNo, List<Bahandicapterm> list){
        for(Bahandicapterm o : list){
            if(termNo.equals(o.getTermNo().toString())){
                return o;
            }
        }
        return null;
    }
    
    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }    

}
