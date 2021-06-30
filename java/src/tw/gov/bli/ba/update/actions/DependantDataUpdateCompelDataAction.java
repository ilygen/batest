package tw.gov.bli.ba.update.actions;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bacompelnopay;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.update.cases.DependantDataUpdateCompelDataCase;
import tw.gov.bli.ba.update.forms.DependantDataUpdateCompelDataForm;
import tw.gov.bli.ba.update.forms.DependantDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.DependantDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 眷屬資料更正 - 不合格年月資料維護
 */
public class DependantDataUpdateCompelDataAction extends BaseDispatchAction{

    private final static Log log = LogFactory.getLog(DependantDataUpdateCompelDataAction.class);
    // 更正作業 - 眷屬資料更正 - 不合格年月資料清單頁面
    private static final String FORWARD_COMPEL_DATA_LIST = "compelDataList";
    // 更正作業 - 眷屬資料更正 - 由不合格年月資料回到眷屬資料明細修改頁面
    private static final String FORWARD_BACK_TO_MODIFY = "backToModify";
    // 更正作業 - 眷屬資料更正 - 由不合格年月資料回到眷屬資料明細新增頁面
    private static final String FORWARD_BACK_TO_INSERT = "backToInsert";

    private static final String LOG_INFO_DOSAVE_START = "執行 眷屬資料更正作業 - 新增不合格年月頁面 SurvivorPayeeDataUpdateCompelAction.doSave() 開始 ... ";
    private static final String LOG_INFO_DOSAVE_ERROR = "執行 眷屬資料更正作業 - 新增不合格年月頁面 SurvivorPayeeDataUpdateCompelAction.doSave() 發生錯誤:";
    private static final String LOG_INFO_DOUPDATE_START = "執行 眷屬資料更正作業 - 修改不合格年月頁面 SurvivorPayeeDataUpdateCompelAction.doUpdate() 開始 ... ";
    private static final String LOG_INFO_DOUPDATE_ERROR = "執行 眷屬資料更正作業 - 新增不合格年月頁面 SurvivorPayeeDataUpdateCompelAction.doUpdate() 發生錯誤:";
    private static final String LOG_INFO_DODELETE_START = "執行 眷屬資料更正作業 - 刪除不合格年月頁面 SurvivorPayeeDataUpdateCompelAction.doDelete() 開始 ... ";
    private static final String LOG_INFO_DODELETE_ERROR = "執行 眷屬資料更正作業 - 刪除不合格年月頁面SurvivorPayeeDataUpdateCompelAction.doDelete() 發生錯誤:";
    private static final String LOG_INFO_DOBACK_START = "執行 眷屬資料更正作業 - 不合格年月維護頁面(返回) SurvivorPayeeDataUpdateCompelAction.doBack() 開始 ... ";
    private static final String LOG_INFO_DOBACK_ERROR = "執行 眷屬資料更正作業 - 不合格年月維護頁面(返回) SurvivorPayeeDataUpdateCompelAction.doBack() 發生錯誤:";


    /**
     * 不合格年月維護作業 - 眷屬資料更正作業 - 新增不合格年月 <br/>
     * (bamo0d075c.jsp)<br/>
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
            DependantDataUpdateCompelDataForm compelForm = (DependantDataUpdateCompelDataForm)form;
            //受款人詳細資料的form
            DependantDataUpdateDetailForm detailForm =  FormSessionHelper.getDependantDataUpdateDetailForm(request);  
            //事故者的form
            DependantDataUpdateQueryForm queryForm = FormSessionHelper.getDependantDataUpdateQueryForm(request);      
            //先看Session中是否已有資料
            List<DependantDataUpdateCompelDataCase> list = CaseSessionHelper.getDependantDataUpdateCompelDataList(request);

            //新增資料,只存至Session中,不存資料庫
            DependantDataUpdateCompelDataCase toBeSaved = new DependantDataUpdateCompelDataCase();
            BigDecimal maxCompelNo = getMaxCompelNoFromCompelDataList(list).add(new BigDecimal(1));

            toBeSaved.setApNo(queryForm.getApNo());
            toBeSaved.setSeqNo(detailForm.getSeqNo());
            toBeSaved.setCompelNo(maxCompelNo);
            toBeSaved.setCompelSdate(compelForm.getCompelSdate());
            toBeSaved.setCompelEdate(compelForm.getCompelEdate());
            toBeSaved.setCompelDesc(compelForm.getCompelDesc());
            toBeSaved.setCrtUser(userData.getEmpNo());
            toBeSaved.setCrtTime(DateUtility.getNowWestDateTime(true));
            toBeSaved.setUpdUser(userData.getEmpNo());
            toBeSaved.setUpdTime(DateUtility.getNowWestDateTime(true));

            list.add(toBeSaved);
            FormSessionHelper.removeDependantDataUpdateCompelDataForm(request);
            CaseSessionHelper.setDependantDataUpdateCompelDataList(list, request);
            if(list != null && !list.isEmpty()){
                request.getSession().setAttribute("DEPENDANT_COMPELDATA_SIZE", list.size());
            } else {
                request.getSession().setAttribute("DEPENDANT_COMPELDATA_SIZE", 0);
            }
            
            return mapping.findForward(FORWARD_COMPEL_DATA_LIST);
        } catch (Exception e) {
            log.error(LOG_INFO_DOSAVE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(FORWARD_COMPEL_DATA_LIST);
        }
    }
    
    /**
     * 不合格年月維護作業 - 遺屬年金受款人資料更正作業 - 修改不合格年月 <br/>
     * (bamo0d289c.jsp)<br/>
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
            DependantDataUpdateCompelDataForm compelForm = (DependantDataUpdateCompelDataForm)form;
            //受款人詳細資料的form
            DependantDataUpdateDetailForm detailForm =  FormSessionHelper.getDependantDataUpdateDetailForm(request);  
            //事故者的form
            DependantDataUpdateQueryForm queryForm = FormSessionHelper.getDependantDataUpdateQueryForm(request);      
            
            List<DependantDataUpdateCompelDataCase> list = CaseSessionHelper.getDependantDataUpdateCompelDataList(request);
            //取得要修改的term
            DependantDataUpdateCompelDataCase caseObj = getCompelDataByCompelNo(compelForm.getCompelNo(), list); 
            caseObj.setCompelSdate(compelForm.getCompelSdate());
            caseObj.setCompelEdate(compelForm.getCompelEdate());
            caseObj.setCompelDesc(compelForm.getCompelDesc());
            caseObj.setUpdUser(userData.getEmpNo());
            caseObj.setUpdTime(DateUtility.getNowWestDateTime(true));

            FormSessionHelper.removeDependantDataUpdateCompelDataForm(request);
            CaseSessionHelper.setDependantDataUpdateCompelDataList(list, request);
            
            if(list != null && !list.isEmpty()){
                request.getSession().setAttribute("DEPENDANT_COMPELDATA_SIZE", list.size());
            } else {
                request.getSession().setAttribute("DEPENDANT_COMPELDATA_SIZE", 0);
            }
            
            return mapping.findForward(FORWARD_COMPEL_DATA_LIST);
        } catch (Exception e) {
            log.error(LOG_INFO_DOUPDATE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(FORWARD_COMPEL_DATA_LIST);
        }
    }
    
    /**
     * 不合格年月維護作業 - 遺屬年金受款人資料更正作業 - 刪除不合格年月 <br/>
     * (bamo0d289c.jsp)<br/>
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
            DependantDataUpdateCompelDataForm compelForm = (DependantDataUpdateCompelDataForm)form;
            List<DependantDataUpdateCompelDataCase> list = CaseSessionHelper.getDependantDataUpdateCompelDataList(request);
            //取得要刪除的term
            DependantDataUpdateCompelDataCase term = getCompelDataByCompelNo(compelForm.getCompelNo(), list);
            list.remove(term);

            FormSessionHelper.removeDependantDataUpdateCompelDataForm(request);
            CaseSessionHelper.setDependantDataUpdateCompelDataList(list, request);
            
            if(list != null && !list.isEmpty()){
                request.getSession().setAttribute("DEPENDANT_COMPELDATA_SIZE", list.size());
            } else {
                request.getSession().setAttribute("DEPENDANT_COMPELDATA_SIZE", 0);
            }
            
            return mapping.findForward(FORWARD_COMPEL_DATA_LIST);
        } catch (Exception e) {
            log.error(LOG_INFO_DODELETE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(FORWARD_COMPEL_DATA_LIST);
        }
    }

    /**
     * 不合格年月維護作業 - 遺屬年金受款人資料更正作業 - 刪除不合格年月 <br/>
     * (bamo0d289c.jsp)<br/>
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
            FormSessionHelper.removeDependantDataUpdateCompelDataForm(request);
            saveMessages(request.getSession(), null);
            
            String mode = (String)request.getSession().getAttribute("CompelDataMode");
            if("I".equalsIgnoreCase(mode)){
                request.getSession().removeAttribute("CompelDataMode");
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
     * 由不合格年月資料中取出最大的compelNo
     * 若無資料則回傳1
     * @param list
     * @return
     */
    private BigDecimal getMaxCompelNoFromCompelDataList(List<DependantDataUpdateCompelDataCase> list) {
        DependantDataUpdateCompelDataCase caseObj = getMaxCompelDataFromList(list);
        if (caseObj != null) {
            BigDecimal compelNo = caseObj.getCompelNo();
            return (compelNo == null)? new BigDecimal(0) : compelNo;
        }
        return new BigDecimal(0);
    }

    /**
     * 由不合格年月資料中取出最大的DependantDataUpdateCompelCase
     * @param list
     * @return
     */
    private DependantDataUpdateCompelDataCase getMaxCompelDataFromList(List<DependantDataUpdateCompelDataCase> list){
        if(!list.isEmpty()){
            return list.get(list.size() - 1);
        }
        return null;
    }

    /**
     * 由不合格年月資料中找出與傳入之compelNo相合之DependantDataUpdateCompelCase資料
     * @param compelNo
     * @param list
     * @return
     */
    private DependantDataUpdateCompelDataCase getCompelDataByCompelNo(String compelNo, List<DependantDataUpdateCompelDataCase> list){
        for(DependantDataUpdateCompelDataCase o : list){
            if(compelNo.equals(o.getCompelNo().toString())){
                return o;
            }
        }
        return null;
    }

}
