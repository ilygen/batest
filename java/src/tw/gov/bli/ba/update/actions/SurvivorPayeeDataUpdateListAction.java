package tw.gov.bli.ba.update.actions;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.*;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01Case;
import tw.gov.bli.ba.rpt.report.SurvivorReviewRpt01Report;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.BadaprDataCase;
import tw.gov.bli.ba.update.cases.SurvivorApplicationDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCompelDataCase;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 更正作業 - 遺屬年金受款人資料更正(BAMO0D282C)
 * 
 * @author Azuritul
 */
public class SurvivorPayeeDataUpdateListAction extends SurvivorPayeeDataUpdateAction {

    private static Log log = LogFactory.getLog(SurvivorPayeeDataUpdateListAction.class);
    // 更正作業 - 遺屬受款人資料更正 - 遺屬受款人資料新增作業頁面
    private static final String FORWARD_INSERT_SURVIVOR_PAYEE_DETAIL = "insertPayeeData";
    // 更正作業 - 遺屬受款人資料更正 - 遺屬受款人資料繼承人新增作業頁面
    private static final String FORWARD_INSERT_SURVIVOR_INHERIT_PAYEE_DETAIL = "insertInheritPayeeData";
    // 更正作業 - 遺屬受款人資料更正 - 遺屬受款人資料修改作業頁面
    private static final String FORWARD_UPDATE_SURVIVOR_PAYEE_DATA = "updatePayeeData";
    // 更正作業 - 遺屬受款人資料更正 - 遺屬受款人繼承人資料修改作業頁面
    private static final String FORWARD_UPDATE_SURVIVOR_INHERIT_PAYEE_DATA = "updateInheritPayeeData";
    // 更正作業 - 遺屬受款人資料更正 - 遺屬受款人資料可領金額作業頁面
    private static final String FORWARD_QUERY_SURVIVOR_PAYEE_DATA = "queryPayeeData";
    // 更正作業 - 遺屬受款人資料更正 - 遺屬受款人資料清單頁面
    private static final String FORWARD_MODIFY_SURVIVOR_PAYEE_DATA_LIST = "modifyPayeeDataList";

    private static final String LOG_INFO_DOPREPAREINSERT_START = "執行 遺屬年金受款人資料更正 - 清單頁面 SurvivorPayeeDataUpdateListAction.doPrepareInsert() 開始 ... ";
    private static final String LOG_INFO_DOPREPAREINSERT_ERROR = "執行 遺屬年金受款人資料更正 - 清單頁面 SurvivorPayeeDataUpdateListAction.doPrepareInsert() 發生錯誤:";

    private static final String LOG_INFO_DOPREPAREINSERTINHERIT_START = "執行 遺屬年金受款人資料更正 - 清單頁面 SurvivorPayeeDataUpdateListAction.doPrepareInsertInherit() 開始 ... ";
    private static final String LOG_INFO_DOPREPAREINSERTINHERIT_ERROR = "執行 遺屬年金受款人資料更正 - 清單頁面 SurvivorPayeeDataUpdateListAction.doPrepareInsertInherit() 發生錯誤:";

    private static final String LOG_INFO_DOPREPAREUPDATE_START = "執行 遺屬年金受款人資料更正作業 - 清單頁面 SurvivorPayeeDataUpdateListAction.doPrepareUpdate() 開始 ... ";
    private static final String LOG_INFO_DOPREPAREUPDATE_ERROR = "執行 遺屬年金受款人資料更正作業 - 清單頁面 SurvivorPayeeDataUpdateListAction.doPrepareUpdate() 發生錯誤:";

    private static final String LOG_INFO_DOPREPAREUPDATEHEIR_START = "執行 遺屬年金受款人資料更正作業 - 清單頁面 SurvivorPayeeDataUpdateListAction.doPrepareUpdateHeir() 開始 ... ";
    private static final String LOG_INFO_DOPREPAREUPDATEHEIR_ERROR = "執行 遺屬年金受款人資料更正作業 - 清單頁面 SurvivorPayeeDataUpdateListAction.doPrepareUpdateHeir() 發生錯誤:";

    private static final String LOG_INFO_DODELETE_START = "執行 遺屬年金受款人資料更正作業 - 清單頁面 SurvivorPayeeDataUpdateListAction.doDelete() 開始 ... ";
    private static final String LOG_INFO_DODELETE_ERROR = "執行 遺屬年金受款人資料更正作業 - 清單頁面 SurvivorPayeeDataUpdateListAction.doDelete() 發生錯誤:";
    private static final String LOG_INFO_DODELETE_CHECKMARK_FAIL = "執行 遺屬年金受款人資料更正作業 - 清單頁面 SurvivorPayeeDataUpdateListAction.doDelete() 即時編審發生錯誤:";
    private static final String LOG_INFO_DODELETE_CHECKMARK_RESULT = "執行 遺屬年金受款人資料更正作業 - 清單頁面 SurvivorPayeeDataUpdateListAction.doDelete() 呼叫即時編審結果... ";

    private static final String LOG_INFO_DOQUERY_START = "執行 遺屬年金受款人資料更正作業 - 可領金額頁面 SurvivorPayeeDataUpdateListAction.doQuery() 開始 ... ";
    private static final String LOG_INFO_DOQUERY_ERROR = "執行 遺屬受款人資料更正作業 - 可領金額頁面 SurvivorPayeeDataUpdateListAction.doQuery() 發生錯誤:";

    private static final String LOG_INFO_DOPRINT_START = "執行 遺屬受款人資料更正作業 - 檢視受理/審核清單 SurvivorPayeeDataUpdateListAction.doPrint() 開始 ... ";
    private static final String LOG_INFO_DOPRINT_COMPLETE = "執行 遺屬受款人資料更正作業 - 檢視受理/審核清單 SurvivorPayeeDataUpdateListAction.doPrint() 完成 ... ";
    private static final String LOG_INFO_DOPRINT_ERROR = "執行 遺屬受款人資料更正作業 - 檢視受理/審核清單 SurvivorPayeeDataUpdateListAction.doPrint() 發生錯誤:";

    private static final String LOG_INFO_DOBACKLIST_START = "執行 遺屬受款人資料更正作業 - 可領金額頁面 SurvivorPayeeDataUpdateListAction.doBackList() 開始 ... ";
    private static final String LOG_INFO_DOBACKLIST_ERROR = "執行 遺屬受款人資料更正作業 - 可領金額頁面 SurvivorPayeeDataUpdateListAction.doBackList() 發生錯誤:";

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 清單頁面 (bamo0d282c.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPREPAREINSERT_START);
        FormSessionHelper.removeSurvivorPayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removeSurvivorPayeeDataUpdateDetailCase(request);
        try {
            // 用一開始的QueryAction就抓的QueryCase的List
            List<SurvivorPayeeDataUpdateCase> list = CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase(request);
            List<SurvivorPayeeDataUpdateCase> evtlist = CaseSessionHelper.getSurvivorPayeeEvtDataUpdateQueryCase(request);
            // if (list.isEmpty()) {
            // saveMessages(request.getSession(), DatabaseMessageHelper.getNoResultForApNoMessage());
            // return mapping.findForward(ConstantKey.FORWARD_BACK);
            // }
            setNecessaryDropDownMenu(request, evtlist.get(0), null, null);

            // 檢查是否有共同具名領取的受款人, 若為true 則具名領取的選項及下拉選單要設為disable
            boolean toDisableCoreceiver = updateService.hasCoreceiver(evtlist.get(0).getApNo(), evtlist.get(0).getSeqNo());
            // 檢查具名領取下拉選單的size,size <= 0則畫面將具名領取選單隱藏起來
            boolean toHideCoreceiveNameListDropDownMenu = (getCoreceiveNameListDropDownMenu(request)).isEmpty();
            // 取得學校代碼
            request.getSession().setAttribute(ConstantKey.SCHOOLCODE_OPTION_LIST, selectOptionService.selectNpCodeOptionBy());

            request.getSession().setAttribute("toHideCoreceiveNameListDropDownMenu", toHideCoreceiveNameListDropDownMenu);
            CaseSessionHelper.setSurvivorPayeeDataUpdateDetailCase(null, null, false, toDisableCoreceiver, "", request);
            return mapping.findForward(FORWARD_INSERT_SURVIVOR_PAYEE_DETAIL);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOPREPAREINSERT_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 清單頁面 (bamo0d282c.jsp) <br>
     * 按下「新增繼承人」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareInsertInherit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPREPAREINSERTINHERIT_START);

        // 清除明細資料畫面
        FormSessionHelper.removeSurvivorPayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removeSurvivorPayeeDataUpdateDetailCase(request);

        // 取得上層的baappbaseId
        // 取得該主檔資料，儲存於Session以供繼承人使用
        String baappbaseId = StringUtils.defaultString(request.getParameter("baappbaseId"));
        BaappbaseDataUpdateCase inheritFrom = updateService.getBaappbaseDetail(new BigDecimal(baappbaseId), null, null, null);
        request.getSession().setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_INHERIT_FROM, inheritFrom);

        try {
            // 用一開始的QueryAction就抓的QueryCase的List
            List<SurvivorPayeeDataUpdateCase> list = CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase(request);
            List<SurvivorPayeeDataUpdateCase> evtlist = CaseSessionHelper.getSurvivorPayeeEvtDataUpdateQueryCase(request);
            if (evtlist.isEmpty()) {
                saveMessages(request.getSession(), DatabaseMessageHelper.getNoResultForApNoMessage());
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }
            setNecessaryDropDownMenu(request, evtlist.get(0), null, null);

            // 檢查是否有共同具名領取的受款人, 若為true 則具名領取的選項及下拉選單要設為disable
            boolean toDisableCoreceiver = updateService.hasCoreceiver(evtlist.get(0).getApNo(), evtlist.get(0).getSeqNo());
            // 檢查具名領取下拉選單的size,size <= 0則畫面將具名領取選單隱藏起來
            boolean toHideCoreceiveNameListDropDownMenu = (getCoreceiveNameListDropDownMenu(request)).isEmpty();

            request.getSession().setAttribute("toHideCoreceiveNameListDropDownMenu", toHideCoreceiveNameListDropDownMenu);
            CaseSessionHelper.setSurvivorPayeeDataUpdateDetailCase(null, null, false, toDisableCoreceiver, "", request);

            return mapping.findForward(FORWARD_INSERT_SURVIVOR_INHERIT_PAYEE_DETAIL);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOPREPAREINSERTINHERIT_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 在 更正作業 - 遺屬年金受款人資料更正作業 - 清單頁面 (bamo0d282c.jsp) <br>
     * 按下「修改」的動作 (修改繼承人)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPREPAREUPDATE_START);

        // 清除明細資料畫面
        FormSessionHelper.removeSurvivorPayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removeSurvivorPayeeDataUpdateDetailCase(request);

        try {
            String baappbaseId = StringUtils.defaultString(request.getParameter("baappbaseId"));
            // 重新抓取資料 - 直接用舊有的SQL即可(PS只有抓主檔)
            Baappbase detailData = updateService.getPayeeDataUpdateForUpdateCaseBy(baappbaseId);
            // 重新抓取資料 - 抓延伸主檔 並設至Form及Case中
            Baappexpand detailBaappexpandData = updateService.getSurvivorPayeeDataUpdateForUpdateCaseBy(new BigDecimal(baappbaseId));

            // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
            SurvivorPayeeDataUpdateDetailForm updateForm = new SurvivorPayeeDataUpdateDetailForm();
            // 將Baappbase資料設至Form
            BeanUtility.copyPropertiesForUpdate(updateForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
            updateForm.setOldSpecialAccAfter(updateForm.getSpecialAcc());
            // 將Baappexpand資料設至Form
            migrateBaappexpandDataToForm(detailBaappexpandData, updateForm);
            // 變更日期為民國年
            updateForm = convertDateToChineseYear(updateForm);
            detailBaappexpandData = convertDateToChineseYear(detailBaappexpandData);

            // 查核年月 分成兩個欄位
            if (StringUtils.defaultString(detailData.getIdnChkYm()).trim().length() != 0) {
                updateForm.setIdnChkYear(DateUtility.changeWestYearMonthType(detailData.getIdnChkYm()).substring(0, 3));
                updateForm.setIdnChkMonth(DateUtility.changeWestYearMonthType(detailData.getIdnChkYm()).substring(3, 5));
            }

            // 帳號 分成兩個欄位或一個欄位
            if (StringUtils.equals(ConstantKey.BAAPPBASE_PAYTYP_1, detailData.getPayTyp()) || StringUtils.equals(ConstantKey.BAAPPBASE_PAYTYP_2, detailData.getPayTyp())) {
                updateForm.setPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId());
                updateForm.setOldPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId());
                detailData.setPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId());
            }
            else if (StringUtils.equals(ConstantKey.BAAPPBASE_PAYTYP_5, detailData.getPayTyp()) || StringUtils.equals(ConstantKey.BAAPPBASE_PAYTYP_6, detailData.getPayTyp())) {
                updateForm.setPayAccount(detailData.getPayBankId() + detailData.getBranchId() + detailData.getPayEeacc());
                updateForm.setOldPayAccount(detailData.getPayBankId() + detailData.getBranchId() + detailData.getPayEeacc());
            }

            // 使用一開始的QueryAction就抓的QueryCase的List
            List<SurvivorPayeeDataUpdateCase> list = CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase(request);
            List<SurvivorPayeeDataUpdateCase> evtlist = CaseSessionHelper.getSurvivorPayeeEvtDataUpdateQueryCase(request);
            setNecessaryDropDownMenu(request, evtlist.get(0), baappbaseId, updateForm.getBenEvtRel());

            // 傳入受款人的APNO,SEQNO,ISSUYM以確定是否要顯示身份查核年月
            boolean toDisplayIdnCheck = updateService.displayIdnChkYearMonth(detailData.getApNo(), detailData.getSeqNo(), detailData.getIssuYm());
            // 檢查是否有共同具名領取的受款人, 若為true 則具名領取的選項及下拉選單要設為disable
            boolean toDisableCoreceiver = updateService.hasCoreceiver(detailData.getApNo(), detailData.getSeqNo());
            // 檢查具名領取下拉選單的size,size <= 0則畫面將具名領取選單隱藏起來
            boolean toHideCoreceiveNameListDropDownMenu = (getCoreceiveNameListDropDownMenu(request)).isEmpty();
            // 檢查是否要顯示再婚日期
            boolean displayDigamyDate = updateService.displayDigamyDate(detailData.getApNo(), detailData.getSeqNo(), detailData.getIssuYm());
            // 取得學校代碼
            request.getSession().setAttribute(ConstantKey.SCHOOLCODE_OPTION_LIST, selectOptionService.selectNpCodeOptionBy());

            // 取得在學清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<Bastudterm> termList = CaseSessionHelper.getSurvivorPayeeDataUpdateStudtermList(request);
            if (termList == null) {
                termList = updateService.selectStudtermListForSurvivorPayeeDataUpdate(detailData.getApNo(), updateForm.getSeqNo());
            }

            // 取得重殘清單
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<Bahandicapterm> bahandicaptermList = CaseSessionHelper.getSurvivorPayeeDataUpdateHandicaptermList(request);
            if (bahandicaptermList == null) {
                bahandicaptermList = updateService.selectHandicaptermListForSurvivorPayeeDataUpdate(detailData.getApNo(), updateForm.getSeqNo());
            }

            String maxPayYm = updateService.selectMaxPayYmForSBy(detailData.getApNo(), "0000");
            String results = "";

            for (int i = 0; i < termList.size(); i++) {
                String studEDate1 = termList.get(i).getStudEdate();

                if (studEDate1.length() == 5) {
                    studEDate1 = DateUtility.changeChineseYearMonthType(studEDate1);
                }

                if (Integer.parseInt(studEDate1) > Integer.parseInt(maxPayYm)) {
                    results = "Y";
                }
            }

            // 取得不合格年月清單資料
            // 需判斷Session是否有資料,若有資料則直接用Session資料
            // 若無資料則fetch from db
            List<SurvivorPayeeDataUpdateCompelDataCase> compelDataList = CaseSessionHelper.getSurvivorPayeeDataUpdateCompelDataList(request);
            if (compelDataList == null) {
                compelDataList = updateService.getCompelDataList(detailData.getApNo(), updateForm.getSeqNo());
            }

            // 取得遺屬註記及符合註記
            Map<String, ArrayList<Bachkfile>> mapChkMk = fetchChkmkList(detailData.getApNo(), detailData.getSeqNo());
            Map<String, ArrayList<Bachkfile>> map = fetchQualifyChkmkList(detailData.getApNo(), detailData.getSeqNo());

            request.getSession().setAttribute("qualifyMarkMap", map);
            request.getSession().setAttribute("checkMarkMap", mapChkMk);
            request.getSession().setAttribute("toHideCoreceiveNameListDropDownMenu", toHideCoreceiveNameListDropDownMenu);
            request.getSession().setAttribute("toDisplayDigaMyDate", displayDigamyDate);
            FormSessionHelper.setSurvivorPayeeDataUpdateDetailForm(updateForm, request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateStudtermList(termList, request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateHandicaptermList(bahandicaptermList, request);
            if (termList != null && !termList.isEmpty()) {
                request.getSession().setAttribute("SURVIVOR_PAYEE_STUDTERM_SIZE", termList.size());
            }
            else {
                request.getSession().setAttribute("SURVIVOR_PAYEE_STUDTERM_SIZE", 0);
            }
            if (bahandicaptermList != null && !bahandicaptermList.isEmpty()) {
                request.getSession().setAttribute("SURVIVOR_PAYEE_HANDICAPTERM_SIZE", bahandicaptermList.size());
            }
            else {
                request.getSession().setAttribute("SURVIVOR_PAYEE_HANDICAPTERM_SIZE", 0);
            }
            request.getSession().setAttribute("studResult", results);
            CaseSessionHelper.setSurvivorPayeeDataUpdateCompelDataList(compelDataList, request);
            if (compelDataList != null && !compelDataList.isEmpty()) {
                request.getSession().setAttribute("SURVIVOR_PAYEE_COMPELDATA_SIZE", compelDataList.size());
            }
            else {
                request.getSession().setAttribute("SURVIVOR_PAYEE_COMPELDATA_SIZE", 0);
            }
            CaseSessionHelper.setSurvivorPayeeDataUpdateDetailCase(detailData, detailBaappexpandData, toDisplayIdnCheck, toDisableCoreceiver, "", request);

            return mapping.findForward(FORWARD_UPDATE_SURVIVOR_PAYEE_DATA);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOPREPAREUPDATE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 在 更正作業 - 遺屬年金受款人資料更正作業 - 清單頁面 (bamo0d288c.jsp) <br>
     * 按下「修改」的動作 (修改繼承人)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrepareUpdateHeir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPREPAREUPDATEHEIR_START);

        // 清除明細資料畫面
        FormSessionHelper.removeSurvivorPayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removeSurvivorPayeeDataUpdateDetailCase(request);
        try {
            // 透過baappbaseId取得該受款人資料
            String baappbaseId = StringUtils.defaultString(request.getParameter("baappbaseId"));
            BaappbaseDataUpdateCase benDetail = updateService.getBaappbaseDetail(new BigDecimal(baappbaseId), null, null, null);
            // 取得該受款人上層之資料(原本只取名字,2009.11.11因為要取得上層受款人之死亡日期做為檢核條件,故改為取得整個主檔物件)
            // 此處受到影響的頁面會是BAMO0D288C.jsp
            Baappbase upperBen = updateService.getDisabledAncestorMainData(benDetail.getApNo(), benDetail.getSeqNo());
            request.getSession().setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_INHERIT_FROM, upperBen);

            // 重新抓取資料 - 直接用舊有的SQL即可
            Baappbase detailData = updateService.getPayeeDataUpdateForUpdateCaseBy(baappbaseId);
            // 重新抓取資料 - 還要再抓延伸主檔 並設至Form及Case中
            Baappexpand detailBaappexpandData = updateService.getSurvivorPayeeDataUpdateForUpdateCaseBy(detailData.getBaappbaseId());
            // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
            SurvivorPayeeDataUpdateDetailForm updateForm = new SurvivorPayeeDataUpdateDetailForm();
            BeanUtility.copyPropertiesForUpdate(updateForm, detailData, ConstantKey.OLD_FIELD_PREFIX);

            // 帳號 分成兩個欄位或一個欄位
            if (StringUtils.equals(ConstantKey.BAAPPBASE_PAYTYP_1, detailData.getPayTyp()) || StringUtils.equals(ConstantKey.BAAPPBASE_PAYTYP_2, detailData.getPayTyp())) {
                updateForm.setPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId());
                updateForm.setOldPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId());
                detailData.setPayBankIdBranchId(detailData.getPayBankId() + detailData.getBranchId());
            }
            else if (StringUtils.equals(ConstantKey.BAAPPBASE_PAYTYP_5, detailData.getPayTyp()) || StringUtils.equals(ConstantKey.BAAPPBASE_PAYTYP_6, detailData.getPayTyp())) {
                updateForm.setPayAccount(detailData.getPayBankId() + detailData.getBranchId() + detailData.getPayEeacc());
                updateForm.setOldPayAccount(detailData.getPayBankId() + detailData.getBranchId() + detailData.getPayEeacc());
            }

            // 將Baappexpand資料設至Form
            migrateBaappexpandDataToForm(detailBaappexpandData, updateForm);

            // 變更日期為民國年
            updateForm = convertDateToChineseYear(updateForm);
            detailBaappexpandData = convertDateToChineseYear(detailBaappexpandData);

            // 改成用一開始的QueryAction就抓的QueryCase的List
            List<SurvivorPayeeDataUpdateCase> list = CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase(request);
            List<SurvivorPayeeDataUpdateCase> evtlist = CaseSessionHelper.getSurvivorPayeeEvtDataUpdateQueryCase(request);
            setNecessaryDropDownMenu(request, evtlist.get(0), baappbaseId, updateForm.getBenEvtRel());

            // 檢查是否有共同具名領取的受款人, 若為true 則具名領取的選項及下拉選單要設為disable
            // boolean toDisableCoreceiver = updateService.hasCoreceiver(list.get(0).getApNo(), list.get(0).getSeqNo());

            // 檢查是否有共同具名領取的受款人, 若為true 則具名領取的選項及下拉選單要設為disable
            boolean toDisableCoreceiver = updateService.hasCoreceiver(detailData.getApNo(), detailData.getSeqNo());
            // 檢查具名領取下拉選單的size,size <= 0則畫面將具名領取選單隱藏起來
            boolean toHideCoreceiveNameListDropDownMenu = (getCoreceiveNameListDropDownMenu(request)).isEmpty();

            request.getSession().setAttribute("toHideCoreceiveNameListDropDownMenu", toHideCoreceiveNameListDropDownMenu);
            CaseSessionHelper.setSurvivorPayeeDataUpdateDetailCase(detailData, detailBaappexpandData, false, toDisableCoreceiver, "", request);

            FormSessionHelper.setSurvivorPayeeDataUpdateDetailForm(updateForm, request);
            return mapping.findForward(FORWARD_UPDATE_SURVIVOR_INHERIT_PAYEE_DATA);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOPREPAREUPDATEHEIR_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 刪除頁面 (bamo0d282c.jsp) <br>
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
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        FormSessionHelper.removeSurvivorPayeeDataUpdateDetailForm(request);
        CaseSessionHelper.removeSurvivorPayeeDataUpdateDetailCase(request);
        try {
            String baappbaseId = StringUtils.defaultString(request.getParameter("baappbaseId"));
            Baappbase detailData = updateService.getPayeeDataUpdateForUpdateCaseBy(baappbaseId);
            updateService.deleteSurvivorPayeeDataUpdate(userData, detailData);

            // 呼叫即時編審
            String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
            try {
                returnCode = callCheckMarkWebService(detailData.getApNo());
            }
            catch (Exception e) {
                log.error(LOG_INFO_DODELETE_CHECKMARK_FAIL + ExceptionUtility.getStackTrace(e));
            }

            // 更新清單資料
            SurvivorPayeeDataUpdateQueryForm listForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(listForm.getApNo(), "0000"), request);
            CaseSessionHelper.setSurvivorPayeeEvtDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(listForm.getApNo(), null), request);
            log.debug(LOG_INFO_DODELETE_CHECKMARK_RESULT + returnCode);
            if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                saveMessages(request.getSession(), CustomMessageHelper.getCheckMarkFailMessage());
                return mapping.findForward(FORWARD_MODIFY_SURVIVOR_PAYEE_DATA_LIST);
            }

            saveMessages(request.getSession(), DatabaseMessageHelper.getDeleteSuccessMessage());
            return mapping.findForward(FORWARD_MODIFY_SURVIVOR_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DODELETE_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 可領金額頁面 (bamo0d282c.jsp) <br>
     * 按下「可領金額」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOQUERY_START);

        // 清除 Session
        CaseSessionHelper.removeSurvivorPayeeDataForBadaprCase(request);
        // 取得查詢List
        List<SurvivorPayeeDataUpdateCase> queryList = CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase(request);
        SurvivorPayeeDataUpdateCase queryData = queryList.get(0);

        try {
            // 取得遺屬年金受款人可領金額資料(表頭部份)
            BadaprDataCase headerData = updateService.selectSurvivorPayeeDataHeaderForBadapr(queryData.getApNo());
            // 取得遺屬年金受款人可領金額資料(內容部份)
            List<SurvivorPayeeDataUpdateCase> caseDataList = updateService.selectSurvivorPayeeDataListForBadapr(queryData.getApNo());
            TreeSet<String> seqNoSet = new TreeSet<String>();
            for (SurvivorPayeeDataUpdateCase o : caseDataList) {
                seqNoSet.add(o.getSeqNo());
            }

            List<Map> contentList = new ArrayList<Map>();
            for (String seqNo : seqNoSet) {
                for (SurvivorPayeeDataUpdateCase o : caseDataList) {
                    if (o.getSeqNo().equals(seqNo)) {
                        Map header = new HashMap();
                        header.put("benIdnNo", o.getBenIdnNo());
                        header.put("benName", o.getBenName());
                        header.put("benEvtRel", o.getBenEvtRelName());
                        String inheritFrom = updateService.getDisabledAncestorData(o.getApNo(), o.getSeqNo());
                        if (StringUtils.isEmpty(inheritFrom)) {
                            header.put("inheritFrom", queryData.getEvtName());
                        }
                        else {
                            header.put("inheritFrom", inheritFrom);
                        }
                        header.put("seqNo", o.getSeqNo());
                        header.put("payYmList", new ArrayList<SurvivorPayeeDataUpdateCase>(0));
                        contentList.add(header);
                        break;
                    }
                }
            }

            for (Map headerMap : contentList) {
                String headerSeqNo = (String) headerMap.get("seqNo");
                for (SurvivorPayeeDataUpdateCase o : caseDataList) {
                    if (o.getSeqNo().equals(headerSeqNo)) {
                        ((ArrayList<SurvivorPayeeDataUpdateCase>) headerMap.get("payYmList")).add(o);
                    }
                }
            }

            if (caseDataList != null && caseDataList.size() > 0) {
                SurvivorPayeeDataUpdateCase caseData = caseDataList.get(0);
                if (caseData != null) {
                    request.getSession().setAttribute("badaprIssueYm", caseDataList.get(0).getIssuYmStr());
                }
            }
            CaseSessionHelper.setSurvivorPayeeDataForBadaprCase(headerData, request);
            CaseSessionHelper.setSurvivorPayeeDataListForBadaprCase(contentList, request);
            return mapping.findForward(FORWARD_QUERY_SURVIVOR_PAYEE_DATA);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOQUERY_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 檢視受理/審核清單 (bamo0d282c.jsp) <br>
     * 按下「檢視受理/審核清單」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOPRINT_START);
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        try {

            // 自 Session 中取出 Case 資料
            List<SurvivorPayeeDataUpdateCase> list = CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase(request);
            List<SurvivorPayeeDataUpdateCase> evtlist = CaseSessionHelper.getSurvivorPayeeEvtDataUpdateQueryCase(request);
            SurvivorPayeeDataUpdateCase caseData = evtlist.get(0);
            List<SurvivorReviewRpt01Case> caseList = rptService.getSurvivorReviewRpt01DataBy(caseData.getApNo(), caseData.getApNo());

            if (evtlist == null || evtlist.size() == 0) {
                saveMessages(request.getSession(), CustomMessageHelper.getReportGenerateFailMessage());
                return mapping.findForward(FORWARD_MODIFY_SURVIVOR_PAYEE_DATA_LIST);
            }

            if (caseList == null || caseList.size() == 0) {
                saveMessages(request.getSession(), CustomMessageHelper.getReportGenerateFailMessage());
                return mapping.findForward(FORWARD_MODIFY_SURVIVOR_PAYEE_DATA_LIST);
            }

            SurvivorReviewRpt01Report report = new SurvivorReviewRpt01Report();
            ByteArrayOutputStream baoOutput = report.doReport(userData, caseList);

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            response.setHeader("Content-disposition", "attachment; filename=\"SurvivorReviewRpt01_" + printDate + ".pdf" + "\"");
            response.setHeader("Content-Encoding", "pdf");
            response.setContentType("application/pdf");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentLength(baoOutput.size());

            // 傳回 Client 端
            ServletOutputStream sout = null;
            try {
                sout = response.getOutputStream();
                baoOutput.writeTo(sout);
                sout.flush();
            }
            catch (Exception e) {
            }
            finally {
                if (baoOutput != null)
                    baoOutput.close();
                if (sout != null)
                    sout.close();
            }

            log.debug(LOG_INFO_DOPRINT_COMPLETE);
            return null;
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOPRINT_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MODIFY_SURVIVOR_PAYEE_DATA_LIST);
        }
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 (bamo0d282c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        cleanDataInSession(request);
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業 - 可領金額 (bamo0d284c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOBACKLIST_START);
        try {
            // 清除 Session
            CaseSessionHelper.removeSurvivorPayeeDataForBadaprCase(request);
            // 更新清單資料
            SurvivorPayeeDataUpdateQueryForm listForm = FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm(request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(listForm.getApNo(), "0000"), request);
            CaseSessionHelper.setSurvivorPayeeEvtDataUpdateQueryCase(refetchSurvivorPayeeDataUpdateCaseList(listForm.getApNo(), null), request);
            saveMessages(request.getSession(), null);
            return mapping.findForward(FORWARD_MODIFY_SURVIVOR_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOBACKLIST_ERROR + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 抓取畫面下拉選單之資料
     * 
     * @param request
     * @param evtCase
     * @param selfBaappbaseId
     */
    private void setNecessaryDropDownMenu(HttpServletRequest request, SurvivorPayeeDataUpdateCase evtCase, String selfBaappbaseId, String benEvtRel) {
        setCountryDropDownMenu(request);
        setCloseCauseDropDownMenu(request);
        setUnqualifiedCauseDropDownMenu(request); // 不合格原因
        setRelationDropDownMenu(request, benEvtRel);
        setPayTypeDropDownMenu(request, benEvtRel);
        setCoreceiveNameListDropDownMenu(request, evtCase.getBaappbaseId(), evtCase.getApNo(), selfBaappbaseId);
        setAbleApsymMenu(request, evtCase.getEvtDieDateStr());
    }

    /**
     * 將Baappexpand的資料帶到SurvivorPayeeDataUpdateDetailForm
     * 
     * @param detailBaappexpandData
     * @param updateForm
     */
    private void migrateBaappexpandDataToForm(Baappexpand detailBaappexpandData, SurvivorPayeeDataUpdateDetailForm updateForm) {
        if (detailBaappexpandData.getMonIncome() != null) {
            if (StringUtils.isNotBlank(detailBaappexpandData.getMonIncome().toString())) {
                updateForm.setMonIncome(StringUtils.defaultString(detailBaappexpandData.getMonIncome().toString()));
                updateForm.setOldMonIncome(StringUtils.defaultString(detailBaappexpandData.getMonIncome().toString()));
            }
        }
        if (detailBaappexpandData.getBaappexpandId() != null) {
            updateForm.setBaappexpandId(StringUtils.defaultString(detailBaappexpandData.getBaappexpandId().toString()));
        }
        if (detailBaappexpandData.getBaappbaseId() != null) {
            updateForm.setBaappbaseId(new BigDecimal(StringUtils.defaultString(detailBaappexpandData.getBaappbaseId().toString())));
        }
        updateForm.setMarryDate(StringUtils.defaultString(detailBaappexpandData.getMarryDate()));
        updateForm.setDigaMyDate(StringUtils.defaultString(detailBaappexpandData.getDigamyDate()));
        updateForm.setMonIncomeMk(detailBaappexpandData.getMonIncomeMk());
        updateForm.setHandicapMk(StringUtils.defaultString(detailBaappexpandData.getHandIcapMk()));
        updateForm.setStudMk(StringUtils.defaultString(detailBaappexpandData.getStudMk()));
        updateForm.setAbanApplyMk(StringUtils.defaultString(detailBaappexpandData.getAbanApplyMk()));
        updateForm.setAbanApSym(StringUtils.defaultString(detailBaappexpandData.getAbanApsYm()));
        updateForm.setAbleApsYm(StringUtils.defaultString(detailBaappexpandData.getAbleApsYm()));
        updateForm.setRaiseChildMk(StringUtils.defaultString(detailBaappexpandData.getRaiseChildMk()));
        updateForm.setInterdictMk(StringUtils.defaultString(detailBaappexpandData.getInterDictMk()));
        updateForm.setInterdictDate(StringUtils.defaultString(detailBaappexpandData.getInterDictDate()));
        updateForm.setRepealInterdictDate(StringUtils.defaultString(detailBaappexpandData.getRepealInterdictDate()));
        updateForm.setBenMissingSdate(StringUtils.defaultString(detailBaappexpandData.getBenMissingSdate()));
        updateForm.setBenMissingEdate(StringUtils.defaultString(detailBaappexpandData.getBenMissingEdate()));
        updateForm.setPrisonSdate(StringUtils.defaultString(detailBaappexpandData.getPrisonSdate()));
        updateForm.setPrisonEdate(StringUtils.defaultString(detailBaappexpandData.getPrisonEdate()));
        updateForm.setRelatChgDate(StringUtils.defaultString(detailBaappexpandData.getRelatChgDate()));
        updateForm.setAdoptDate(StringUtils.defaultString(detailBaappexpandData.getAdoPtDate()));
        updateForm.setAssignBrDate(StringUtils.defaultString(detailBaappexpandData.getAssignBrDate()));
        updateForm.setAssignName(StringUtils.defaultString(detailBaappexpandData.getAssignName()));
        updateForm.setAssignIdnNo(StringUtils.defaultString(detailBaappexpandData.getAssignIdnNo()));
        updateForm.setRaiseEvtMk(StringUtils.defaultString(detailBaappexpandData.getRaiseEvtMk()));
        updateForm.setSavingMk(StringUtils.defaultString(detailBaappexpandData.getSavingMk()));
        updateForm.setCompelMk(StringUtils.defaultString(detailBaappexpandData.getCompelMk()));
        updateForm.setSchoolCode(StringUtils.defaultString(detailBaappexpandData.getSchoolCode()));
        //old
        updateForm.setOldMarryDate(StringUtils.defaultString(detailBaappexpandData.getMarryDate()));
        updateForm.setOldDigaMyDate(StringUtils.defaultString(detailBaappexpandData.getDigamyDate()));
        updateForm.setOldMonIncomeMk(detailBaappexpandData.getMonIncomeMk());
        updateForm.setOldHandicapMk(StringUtils.defaultString(detailBaappexpandData.getHandIcapMk()));
        updateForm.setOldStudMk(StringUtils.defaultString(detailBaappexpandData.getStudMk()));
        updateForm.setOldAbanApplyMk(StringUtils.defaultString(detailBaappexpandData.getAbanApplyMk()));
        updateForm.setOldAbanApSym(StringUtils.defaultString(detailBaappexpandData.getAbanApsYm()));
        updateForm.setOldAbleApsYm(StringUtils.defaultString(detailBaappexpandData.getAbleApsYm()));
        updateForm.setOldRaiseChildMk(StringUtils.defaultString(detailBaappexpandData.getRaiseChildMk()));
        updateForm.setOldInterdictMk(StringUtils.defaultString(detailBaappexpandData.getInterDictMk()));
        updateForm.setOldInterdictDate(StringUtils.defaultString(detailBaappexpandData.getInterDictDate()));
        updateForm.setOldRepealInterdictDate(StringUtils.defaultString(detailBaappexpandData.getRepealInterdictDate()));
        updateForm.setOldBenMissingSdate(StringUtils.defaultString(detailBaappexpandData.getBenMissingSdate()));
        updateForm.setOldBenMissingEdate(StringUtils.defaultString(detailBaappexpandData.getBenMissingEdate()));
        updateForm.setOldPrisonSdate(StringUtils.defaultString(detailBaappexpandData.getPrisonSdate()));
        updateForm.setOldPrisonEdate(StringUtils.defaultString(detailBaappexpandData.getPrisonEdate()));
        updateForm.setOldRelatChgDate(StringUtils.defaultString(detailBaappexpandData.getRelatChgDate()));
        updateForm.setOldAdoptDate(StringUtils.defaultString(detailBaappexpandData.getAdoPtDate()));
        updateForm.setOldAssignBrDate(StringUtils.defaultString(detailBaappexpandData.getAssignBrDate()));
        updateForm.setOldAssignName(StringUtils.defaultString(detailBaappexpandData.getAssignName()));
        updateForm.setOldAssignIdnNo(StringUtils.defaultString(detailBaappexpandData.getAssignIdnNo()));
        updateForm.setOldRaiseEvtMk(StringUtils.defaultString(detailBaappexpandData.getRaiseEvtMk()));
        updateForm.setOldSavingMk(StringUtils.defaultString(detailBaappexpandData.getSavingMk()));
        updateForm.setOldCompelMk(StringUtils.defaultString(detailBaappexpandData.getCompelMk()));
    }

}
