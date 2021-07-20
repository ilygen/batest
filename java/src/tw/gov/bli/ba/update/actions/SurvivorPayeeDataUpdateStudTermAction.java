package tw.gov.bli.ba.update.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateStudTermForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import java.util.List;
import java.math.BigDecimal;

/**
 * 遺屬年金受款人資料更正 - 在學期間維護
 * User: Chris
 * Date: 2009/10/26
 * Time: 下午 03:09:02
 */
public class SurvivorPayeeDataUpdateStudTermAction extends SurvivorPayeeDataUpdateAction{

    private final static Log log = LogFactory.getLog(SurvivorPayeeDataUpdateStudTermAction.class);
    // 更正作業 - 遺屬受款人資料更正 - 在學期間資料清單頁面
    private static final String FORWARD_STUDTERM_DATA_LIST = "studTermeDataList";
    // 更正作業 - 遺屬受款人資料更正 - 由在學期間資料回到受款人資料明細修改頁面
    private static final String FORWARD_BACK_TO_MODIFY = "backToModify";
    // 更正作業 - 遺屬受款人資料更正 - 由在學期間資料回到受款人資料明細新增頁面
    private static final String FORWARD_BACK_TO_INSERT = "backToInsert";
    
    private UpdateService updateService;

    private static final String LOG_INFO_DOSAVE_START = "執行 遺屬年金受款人資料更正作業 - 新增在學期間頁面 SurvivorPayeeDataUpdateStudTermAction.doSave() 開始 ... ";
    private static final String LOG_INFO_DOSAVE_ERROR = "執行 遺屬年金受款人資料更正作業 - 新增在學期間頁面 SurvivorPayeeDataUpdateStudTermAction.doSave() 發生錯誤:";
    private static final String LOG_INFO_DOUPDATE_START = "執行 遺屬年金受款人資料更正作業 - 修改在學期間頁面 SurvivorPayeeDataUpdateStudTermAction.doUpdate() 開始 ... ";
    private static final String LOG_INFO_DOUPDATE_ERROR = "執行 遺屬年金受款人資料更正作業 - 新增在學期間頁面 SurvivorPayeeDataUpdateStudTermAction.doUpdate() 發生錯誤:";
    private static final String LOG_INFO_DODELETE_START = "執行 遺屬年金受款人資料更正作業 - 刪除在學期間頁面 SurvivorPayeeDataUpdateStudTermAction.doDelete() 開始 ... ";
    private static final String LOG_INFO_DODELETE_ERROR = "執行 遺屬年金受款人資料更正作業 - 刪除在學期間頁面SurvivorPayeeDataUpdateStudTermAction.doDelete() 發生錯誤:";
    private static final String LOG_INFO_DOBACK_START = "執行 遺屬年金受款人資料更正作業 - 在學期間維護頁面(返回) SurvivorPayeeDataUpdateStudTermAction.doBack() 開始 ... ";
    private static final String LOG_INFO_DOBACK_ERROR = "執行 遺屬年金受款人資料更正作業 - 在學期間維護頁面(返回) SurvivorPayeeDataUpdateStudTermAction.doBack() 發生錯誤:";


    /**
     * 在學期間維護作業 - 遺屬年金受款人資料更正作業 - 新增在學期間 <br/>
     * (bamo0d286c.jsp)<br/>
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
            SurvivorPayeeDataUpdateStudTermForm termForm = (SurvivorPayeeDataUpdateStudTermForm)form;
            //受款人詳細資料的form
            SurvivorPayeeDataUpdateDetailForm detailForm =  FormSessionHelper.getSurvivorPayeeDataUpdateDetailForm(request);  
            //事故者的form
            SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);      
            //先看Session中是否已有資料
            List<Bastudterm> list = CaseSessionHelper.getSurvivorPayeeDataUpdateStudtermList(request);

            //新增資料,只存至Session中,不存資料庫
            Bastudterm toBeSaved = new Bastudterm();
            BigDecimal maxTermNo = getMaxTermNoFromStudtermList(list).add(new BigDecimal(1));

            toBeSaved.setApNo(queryForm.getApNo());
            toBeSaved.setSeqNo(detailForm.getSeqNo());
            toBeSaved.setStudSdate(termForm.getStudSdate());
            toBeSaved.setStudEdate(termForm.getStudEdate());
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
                String studEDate1 = list.get(i).getStudEdate();
                
                if (studEDate1.length() == 5) {
                    studEDate1 = DateUtility.changeChineseYearMonthType(studEDate1);
                }
                
                if (Integer.parseInt(studEDate1) > Integer.parseInt(maxPayYm)){
                    results = "Y";
                }
            }            
            
            FormSessionHelper.removeSurvivorPayeeDataUpdateStudTermForm(request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateStudtermList(list, request);
            if(list != null && !list.isEmpty()){
            	request.getSession().setAttribute("SURVIVOR_PAYEE_STUDTERM_SIZE", list.size());
            } else {
            	request.getSession().setAttribute("SURVIVOR_PAYEE_STUDTERM_SIZE", 0);
            }
            request.getSession().setAttribute("studResult", results);
            
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        } catch (Exception e) {
            log.error(LOG_INFO_DOSAVE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        }
    }
    
    /**
     * 在學期間維護作業 - 遺屬年金受款人資料更正作業 - 修改在學期間 <br/>
     * (bamo0d286c.jsp)<br/>
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
            SurvivorPayeeDataUpdateStudTermForm termForm = (SurvivorPayeeDataUpdateStudTermForm)form;
            //受款人詳細資料的form
            SurvivorPayeeDataUpdateDetailForm detailForm =  FormSessionHelper.getSurvivorPayeeDataUpdateDetailForm(request);  
            //事故者的form
            SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);      
            
            List<Bastudterm> list = CaseSessionHelper.getSurvivorPayeeDataUpdateStudtermList(request);
            //取得要修改的term
            Bastudterm term = getBastudtermByTermNo(termForm.getTermNo(), list); 
            term.setStudSdate(termForm.getStudSdate());
            term.setStudEdate(termForm.getStudEdate());
            term.setUpdUser(userData.getEmpNo());
            term.setUpdTime(DateUtility.getNowWestDateTime(true));

            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            String maxPayYm = updateService.selectMaxPayYmForSBy(apNo, "0000");
            String results = "";
            
            for (int i = 0; i < list.size(); i++) {
                String studEDate1 = list.get(i).getStudEdate();
                
                if (studEDate1.length() == 5) {
                    studEDate1 = DateUtility.changeChineseYearMonthType(studEDate1);
                }
                
                if (Integer.parseInt(studEDate1) > Integer.parseInt(maxPayYm)){
                    results = "Y";
                }
            }            
            
            FormSessionHelper.removeSurvivorPayeeDataUpdateStudTermForm(request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateStudtermList(list, request);
            
            if(list != null && !list.isEmpty()){
            	request.getSession().setAttribute("SURVIVOR_PAYEE_STUDTERM_SIZE", list.size());
            } else {
            	request.getSession().setAttribute("SURVIVOR_PAYEE_STUDTERM_SIZE", 0);
            }
            request.getSession().setAttribute("studResult", results);
            
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        } catch (Exception e) {
            log.error(LOG_INFO_DOUPDATE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        }
    }
    
    /**
     * 在學期間維護作業 - 遺屬年金受款人資料更正作業 - 刪除在學期間 <br/>
     * (bamo0d286c.jsp)<br/>
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
            SurvivorPayeeDataUpdateStudTermForm termForm = (SurvivorPayeeDataUpdateStudTermForm)form;
            
            //事故者的form
            SurvivorPayeeDataUpdateQueryForm queryForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);            
            
            List<Bastudterm> list = CaseSessionHelper.getSurvivorPayeeDataUpdateStudtermList(request);
            //取得要刪除的term
            Bastudterm term = getBastudtermByTermNo(termForm.getTermNo(), list);
            list.remove(term);
            
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            String maxPayYm = updateService.selectMaxPayYmForSBy(apNo, "0000");
            String results = "";
            
            for (int i = 0; i < list.size(); i++) {
                String studEDate1 = list.get(i).getStudEdate();
                
                if (studEDate1.length() == 5) {
                    studEDate1 = DateUtility.changeChineseYearMonthType(studEDate1);
                }
                
                if (Integer.parseInt(studEDate1) > Integer.parseInt(maxPayYm)){
                    results = "Y";
                }
            }            

            FormSessionHelper.removeSurvivorPayeeDataUpdateStudTermForm(request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateStudtermList(list, request);
            
            if(list != null && !list.isEmpty()){
            	request.getSession().setAttribute("SURVIVOR_PAYEE_STUDTERM_SIZE", list.size());
            } else {
            	request.getSession().setAttribute("SURVIVOR_PAYEE_STUDTERM_SIZE", 0);
            }
            
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        } catch (Exception e) {
            log.error(LOG_INFO_DODELETE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        }
    }

    /**
     * 在學期間維護作業 - 遺屬年金受款人資料更正作業 - 刪除在學期間 <br/>
     * (bamo0d286c.jsp)<br/>
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
            FormSessionHelper.removeSurvivorPayeeDataUpdateStudTermForm(request);
            saveMessages(request.getSession(), null);
            
            String mode = (String)request.getSession().getAttribute("StudTermMode");
            if("I".equalsIgnoreCase(mode)){
            	request.getSession().removeAttribute("StudTermMode");
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
     * 由在學期間資料中取出最大的TermNo
     * 若無資料則回傳1
     * @param list
     * @return
     */
    private BigDecimal getMaxTermNoFromStudtermList(List<Bastudterm> list) {
        Bastudterm maxStudTerm = getMaxBastudtermFromList(list);
        if (maxStudTerm != null) {
            BigDecimal maxTermNo = maxStudTerm.getTermNo();
            return (maxTermNo == null)? new BigDecimal(0) : maxTermNo;
        }
        return new BigDecimal(0);
    }

    /**
     * 由在學期間資料中取出最大的Bastudterm
     * @param list
     * @return
     */
    private Bastudterm getMaxBastudtermFromList(List<Bastudterm> list){
        if(!list.isEmpty()){
            return list.get(list.size() - 1);
        }
        return null;
    }

    /**
     * 由在學期間資料中找出與傳入之TermNo相合之Bastudterm資料
     * @param termNo
     * @param list
     * @return
     */
    private Bastudterm getBastudtermByTermNo(String termNo, List<Bastudterm> list){
        for(Bastudterm o : list){
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
