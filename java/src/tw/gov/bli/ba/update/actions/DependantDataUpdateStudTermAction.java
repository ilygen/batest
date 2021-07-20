package tw.gov.bli.ba.update.actions;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.MonthCheckKCase;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.forms.DependantDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.DependantDataUpdateQueryForm;
import tw.gov.bli.ba.update.forms.DependantDataUpdateStudTermForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 眷屬資料更正 - 在學期間維護 (bamo0d074c.jsp)
 * 
 * @author Evelyn Hsu
 */

public class DependantDataUpdateStudTermAction extends BaseDispatchAction {

    private final static Log log = LogFactory.getLog(DependantDataUpdateStudTermAction.class);
    
    private UpdateService updateService;
    
    // 更正作業 - 眷屬資料更正 - 在學期間新增頁面
    private static final String FORWARD_INSERT_STUDTERM_DATE = "insertStudTermData";
    // 更正作業 - 眷屬資料更正 - 在學期間修改頁面
    private static final String FORWARD_UPDATE_STUDTERM_DATA = "updateStudTermData";
    // 更正作業 - 眷屬資料更正 - 在學期間資料清單頁面
    private static final String FORWARD_STUDTERM_DATA_LIST = "studTermeDataList";
    // 更正作業 - 眷屬資料更正 - 由在學期間資料回到眷屬資料修改頁面
    private static final String FORWARD_BACK_TO_MODIFY = "backToModify";
    // 更正作業 - 眷屬資料更正 - 由在學期間資料回到眷屬資料新增頁面
    private static final String FORWARD_BACK_TO_INSERT = "backToInsert";

    /**
     * 在學期間維護作業 - 眷屬資料更正作業 - 新增在學期間 <br/> (bamo0d074c.jsp)<br/> 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 遺屬年金受款人資料更正作業 - 新增在學期間頁面 DependantDataUpdateStudTermAction.doSave() 開始 ... ");

        try {
            HttpSession session = request.getSession();
            UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
            DependantDataUpdateStudTermForm termForm = (DependantDataUpdateStudTermForm) form;
            // 眷屬資料
            DependantDataUpdateDetailForm detailForm = FormSessionHelper.getDependantDataUpdateDetailForm(request);
            // 事故者資料
            DependantDataUpdateQueryForm queryForm = FormSessionHelper.getDependantDataUpdateQueryForm(request);
            // 先看Session中是否已有資料
            List<Bastudterm> list = CaseSessionHelper.getDependantDataUpdateStudtermList(request);
            
            // 新增資料,只存至Session中,不存資料庫
            Bastudterm toBeSaved = new Bastudterm();
            BigDecimal maxTermNo = getMaxTermNoFromStudtermList(list).add(new BigDecimal(1));

            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            
            // BigDecimal maxTermNo = updateService.selectMaxTermNoBy(apNo, detailForm.getSeqNo());
            
            if(maxTermNo == null){
            	maxTermNo = new BigDecimal(1);
            }

            toBeSaved.setApNo(apNo);
            toBeSaved.setSeqNo(detailForm.getSeqNo());
            toBeSaved.setStudSdate(termForm.getStudSdate());
            toBeSaved.setStudEdate(termForm.getStudEdate());
            toBeSaved.setTermNo(maxTermNo);
            toBeSaved.setCrtUser(userData.getEmpNo());
            toBeSaved.setCrtTime(DateUtility.getNowWestDateTime(true));
            toBeSaved.setUpdUser(userData.getEmpNo());
            toBeSaved.setUpdTime(DateUtility.getNowWestDateTime(true));

            list.add(toBeSaved);
            /*
            // get system date
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMM");
            Date current = new Date();
            String sysDate = sdFormat.format(current);            
            String studEDate2 = "";                  
            
            for (int i = 0; i < list.size(); i++) {
                // find data from BASTUDTERM (A)                                
                    String studEDate1 = list.get(i).getStudEdate();
                    
                    if (studEDate1.length() == 5) {
                        studEDate1 = DateUtility.changeChineseYearMonthType(studEDate1);
                    }
                    
                    if (Integer.parseInt(studEDate1) <= Integer.parseInt(sysDate)) {
                        if (studEDate2.equals("")) {
                            studEDate2 = studEDate1;
                        } else {
                            if (Integer.parseInt(studEDate2) <= Integer.parseInt(studEDate1)) {
                                studEDate2 = studEDate1;
                            }
                        }                        
                    }                
            }
            
            // find the newest PAYYM from BAAPPBASE (B)
            String maxPayYm = updateService.selectMaxPayYmBy(apNo);
            
            // compare A with B
            String results = (Integer.parseInt(studEDate2) <= Integer.parseInt(maxPayYm)) ? "Y" : "N";*/
            
            String maxPayYm = updateService.selectMaxPayYmBy(apNo, "0000");
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
            
            
            FormSessionHelper.removeDependantDataUpdateStudTermForm(request);
            CaseSessionHelper.setDependantDataUpdateStudtermList(list, request);
            session.setAttribute("studStatus", list.size() > 0 && StringUtils.equals(detailForm.getSeqNo(), toBeSaved.getSeqNo()) ? list.size() : 0);
            session.setAttribute("studResult", results);
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        }
        catch (Exception e) {
            log.error("DependantDataUpdateStudTermAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        }
    }

    /**
     * 在學期間維護作業 - 眷屬資料更正作業 - 修改在學期間 <br/> (bamo0d074c.jsp)<br/> 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 修改在學期間頁面 DependantDataUpdateStudTermAction.doUpdate() 開始 ... ");
        try {
            HttpSession session = request.getSession();
            UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
            DependantDataUpdateStudTermForm termForm = (DependantDataUpdateStudTermForm) form;
            // 受款人詳細資料的form
            DependantDataUpdateDetailForm detailForm = FormSessionHelper.getDependantDataUpdateDetailForm(request);
            // 事故者的form
            DependantDataUpdateQueryForm queryForm = FormSessionHelper.getDependantDataUpdateQueryForm(request);
            
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();            

            List<Bastudterm> list = CaseSessionHelper.getDependantDataUpdateStudtermList(request);
            // 取得要修改的term
            Bastudterm term = getBastudtermByTermNo(termForm.getTermNo(), list);
            term.setStudSdate(termForm.getStudSdate());
            term.setStudEdate(termForm.getStudEdate());
            term.setUpdUser(userData.getEmpNo());
            term.setUpdTime(DateUtility.getNowWestDateTime(true));
            
            String maxPayYm = updateService.selectMaxPayYmBy(apNo, "0000");
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

            FormSessionHelper.removeDependantDataUpdateStudTermForm(request);
            CaseSessionHelper.setDependantDataUpdateStudtermList(list, request);
            session.setAttribute("studStatus", list.size() > 0 && StringUtils.equals(detailForm.getSeqNo(), term.getSeqNo()) ? list.size() : 0);
            session.setAttribute("studResult", results);
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        }
        catch (Exception e) {
            log.error("DependantDataUpdateStudTermAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        }
    }

    /**
     * 在學期間維護作業 - 眷屬資料更正作業 - 刪除在學期間 <br/> (bamo0d074c.jsp)<br/> 按下「刪除」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 刪除在學期間頁面 DependantDataUpdateStudTermAction.doDelete() 開始 ... ");
        try {
            HttpSession session = request.getSession();
            DependantDataUpdateStudTermForm termForm = (DependantDataUpdateStudTermForm) form;
            
            // 事故者資料
            DependantDataUpdateQueryForm queryForm = FormSessionHelper.getDependantDataUpdateQueryForm(request);            
            
            List<Bastudterm> list = CaseSessionHelper.getDependantDataUpdateStudtermList(request);
            // 取得要刪除的term
            Bastudterm term = getBastudtermByTermNo(termForm.getTermNo(), list);
            list.remove(term);
            
            String apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();            
            String maxPayYm = updateService.selectMaxPayYmBy(apNo, "0000");
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

            FormSessionHelper.removeDependantDataUpdateStudTermForm(request);
            CaseSessionHelper.setDependantDataUpdateStudtermList(list, request);
            session.setAttribute("studStatus", list.size() > 0 ? list.size() : 0);
            session.setAttribute("studResult", results);
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        }
        catch (Exception e) {
            log.error("DependantDataUpdateStudTermAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(FORWARD_STUDTERM_DATA_LIST);
        }
    }

    /**
     * 在學期間維護作業 - 眷屬資料更正作業 - 刪除在學期間 <br/> (bamo0d074c.jsp)<br/> 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 眷屬資料更正作業 - 在學期間維護頁面(返回) DepeendantDataUpdateStudTermAction.doBack() 開始 ... ");
        try {
            HttpSession session = request.getSession();
            List<Bastudterm> list = CaseSessionHelper.getDependantDataUpdateStudtermList(request);
            DependantDataUpdateDetailForm detailForm = FormSessionHelper.getDependantDataUpdateDetailForm(request);
            FormSessionHelper.removeDependantDataUpdateStudTermForm(request);
            saveMessages(request.getSession(), null);

            String mode = (String) request.getSession().getAttribute("StudTermMode");
            session.setAttribute("studStatus", list.size() > 0 ? list.size() : 0);
            if ("I".equalsIgnoreCase(mode)) {
                request.getSession().removeAttribute("StudTermMode");
                return mapping.findForward(FORWARD_BACK_TO_INSERT);
            }
            return mapping.findForward(FORWARD_BACK_TO_MODIFY);
        }
        catch (Exception e) {
            log.error("DependantDataUpdateStudTermAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }

    }

    /**
     * 由在學期間資料中取出最大的TermNo 若無資料則回傳1
     * 
     * @param list
     * @return
     */
    private BigDecimal getMaxTermNoFromStudtermList(List<Bastudterm> list) {
        Bastudterm maxStudTerm = getMaxBastudtermFromList(list);
        if (maxStudTerm != null) {
            BigDecimal maxTermNo = maxStudTerm.getTermNo();
            return (maxTermNo == null) ? new BigDecimal(0) : maxTermNo;
        }
        return new BigDecimal(0);
    }

    /**
     * 由在學期間資料中取出最大的Bastudterm
     * 
     * @param list
     * @return
     */
    private Bastudterm getMaxBastudtermFromList(List<Bastudterm> list) {
        if (!list.isEmpty() || list.size() != 0) {
            return list.get(list.size() - 1);
        }
        return null;
    }

    /**
     * 由在學期間資料中找出與傳入之TermNo相合之Bastudterm資料
     * 
     * @param termNo
     * @param list
     * @return
     */
    private Bastudterm getBastudtermByTermNo(String termNo, List<Bastudterm> list) {
        for (Bastudterm o : list) {
            if (termNo.equals(o.getTermNo().toString())) {
                return o;
            }
        }
        return null;
    }
    
    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

}
