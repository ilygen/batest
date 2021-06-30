package tw.gov.bli.ba.update.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jaxen.function.SubstringFunction;
import com.iisi.SecureToken;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Bachkcontrl;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01Case;
import tw.gov.bli.ba.rpt.report.DisableReviewRpt01Kind36Report;
import tw.gov.bli.ba.rpt.report.DisableReviewRpt01Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.DisabledCheckMarkLevelAdjustBenDetailDataCase;
import tw.gov.bli.ba.update.cases.DisabledCheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.cases.DisabledCheckMarkLevelAdjustFamDetailDataCase;
import tw.gov.bli.ba.update.forms.DisabledCheckMarkLevelAdjustForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 (bamo0d160c.jsp bamo0d161c.jsp)
 * 
 * @author Goston
 */
public class DisabledCheckMarkLevelAdjustAction extends DispatchAction {
    private static Log log = LogFactory.getLog(DisabledCheckMarkLevelAdjustAction.class);

    private static final String FORWARD_DETAIL_PAGE = "detailPage";
    
    public static final String ADJ_LEVEL_MUST_HANDLE = "W"; // 需進行處理的 程度調整 值
    public static final String ADJ_LEVEL_FIELD_PREFIX = "adjLevel"; // 程度調整 輸入欄位名稱 Prefix
    public static final String VALID_START_YM_FIELD_PREFIX = "valisYm"; // 有效年月 - 起 輸入欄位名稱 Prefix
    public static final String VALID_END_YM_FIELD_PREFIX = "valieYm"; // 有效年月 - 迄 輸入欄位名稱 Prefix

    private UpdateService updateService;
    private RptService rptService;

    /**
     * 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 - 查詢頁面 (bamo0d160c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 - 查詢頁面 DisabledCheckMarkLevelAdjustAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledCheckMarkLevelAdjustForm queryForm = (DisabledCheckMarkLevelAdjustForm) form;

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeDisabledCheckMarkLevelAdjustCase(request);

            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(queryForm.getApNo1())) + StringUtils.trimToEmpty(queryForm.getApNo2()) + StringUtils.trimToEmpty(queryForm.getApNo3()) + StringUtils.trimToEmpty(queryForm.getApNo4());

            if (StringUtils.length(apNo) != ConstantKey.APNO_LENGTH) { // 受理編號長度不滿 12 碼則不做查詢
                // 設定 無此受理號碼或尚未產生核定資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultForApNoMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 取得 案件 資料
            DisabledCheckMarkLevelAdjustCase caseData = updateService.getDisabledCheckMarkLevelAdjustData(apNo);
            //放入畫面使用之 得請領起始年月
            String appDateJsp = DateUtility.changeDateType(caseData.getAppDate());
            String evtJobDate = DateUtility.changeDateType(caseData.getEvtJobDate());
            //jsp畫面使用之年月
            caseData.setAppDateJsp(appDateJsp.substring(0, 5));
            caseData.setEvtJobDateJsp(evtJobDate.substring(0, 5));
            // 如果案件不存在或沒有編審資料則顯示失敗訊息
            if (caseData == null || !caseData.hasCheckFileData()) {
                // 設定 無此受理號碼或尚未產生核定資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultForApNoMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            
            //放入得請領起始年月
            String ableapsYm = updateService.getAbleapsYmForDisabledCheckMarkLevelAdjust(caseData.getApNo(),caseData.getBaappbaseId());
            String appDateForForm = DateUtility.changeDateType(appDateJsp).substring(0, 6);
            String evtJobDateForForm = DateUtility.changeDateType(evtJobDate).substring(0, 6);
            if(StringUtils.isNotBlank(ableapsYm)){
            if(ableapsYm.equals(appDateForForm)){
                queryForm.setAbleApsYm("appDate");
            }else if (ableapsYm.equals(evtJobDateForForm)){
            	queryForm.setAbleApsYm("evtJobDate");
            }
            }else{
            	queryForm.setAbleApsYm("");
            }
            FormSessionHelper.setDisabledCheckMarkLevelAdjustForm(queryForm,request);
            // 將 CaseData 塞到 Session 中
            CaseSessionHelper.setDisabledCheckMarkLevelAdjustCase(caseData, request);

            log.debug("執行 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 - 查詢頁面 DisabledCheckMarkLevelAdjustAction.doQuery() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledCheckMarkLevelAdjustAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 - 修改頁面 (bamo0d161c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 - 修改頁面 DisabledCheckMarkLevelAdjustAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledCheckMarkLevelAdjustForm adjustForm = (DisabledCheckMarkLevelAdjustForm) form;
        
        try {
            // 自 Session 取出 caseData
            DisabledCheckMarkLevelAdjustCase caseData = CaseSessionHelper.getDisabledCheckMarkLevelAdjustCase(request);

            if (caseData == null) {
                // 設定 編審註記程度調整作業失敗 訊息
                saveMessages(session, DatabaseMessageHelper.getCheckMarkLevelAdjustSaveFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            String apNo = caseData.getApNo(); // 受理編號 - 供後續使用
            
            Map<String, String[]> parameterMap = request.getParameterMap();
            Enumeration<String> parameterNames = request.getParameterNames();

            ArrayList<Bachkcontrl> saveList = new ArrayList<Bachkcontrl>(); // 存檔 List

            String now = DateUtility.getNowWestDateTime(true); // 系統時間

            while (parameterNames.hasMoreElements()) { // begin ... [
                String parameterName = (String) parameterNames.nextElement();

                if (StringUtils.startsWith(parameterName, ADJ_LEVEL_FIELD_PREFIX)) { // begin ... [
                    // 取得 程度調整 輸入欄位的值
                    String[] parameterValue = parameterMap.get(parameterName);
                    if (parameterValue != null && parameterValue.length > 0) { // begin ... [
                        String key = StringUtils.replace(parameterName, ADJ_LEVEL_FIELD_PREFIX, "");

                        // 取得該 key 於頁面上的 程度調整 有效年月 - 起 及 有效年月 - 迄 輸入欄位值
                        String adjLevel = (parameterMap.get(ADJ_LEVEL_FIELD_PREFIX + key) == null) ? "" : StringUtils.defaultString(parameterMap.get(ADJ_LEVEL_FIELD_PREFIX + key)[0]).trim();
                        String valisYm = (parameterMap.get(VALID_START_YM_FIELD_PREFIX + key) == null) ? "" : StringUtils.defaultString(parameterMap.get(VALID_START_YM_FIELD_PREFIX + key)[0]).trim();
                        String valieYm = (parameterMap.get(VALID_END_YM_FIELD_PREFIX + key) == null) ? "" : StringUtils.defaultString(parameterMap.get(VALID_END_YM_FIELD_PREFIX + key)[0]).trim();

                        // 民國年月 轉 西元年月
                        if (StringUtils.length(valisYm) == 5)
                            valisYm = DateUtility.changeChineseYearMonthType(valisYm);
                        if (StringUtils.length(valieYm) == 5)
                            valieYm = DateUtility.changeChineseYearMonthType(valieYm);

                        // 將輸入欄位的值更新到 CaseData 中
                        caseData.setDetailListInputData(key, adjLevel, valisYm, valieYm);

                        // 若 程度調整 為 W 則將該筆資料加入存檔 List 中
                        if (StringUtils.equals(parameterValue[0], ADJ_LEVEL_MUST_HANDLE)) {
                            DisabledCheckMarkLevelAdjustBenDetailDataCase benDetailCase = caseData.searchBenDetailByKey(key);
                            if (benDetailCase != null) { // 受款人
                                Bachkcontrl ctlData = new Bachkcontrl();

                                ctlData.setApNo(benDetailCase.getApNo()); // 受理編號
                                ctlData.setSeqNo(benDetailCase.getSeqNo()); // 序號
                                ctlData.setIssuYm(benDetailCase.getIssuYm()); // 核定年月
                                ctlData.setPayYm(benDetailCase.getPayYm()); // 給付年月
                                ctlData.setContrlTyp("0"); // 管制對象類別 受款人 - 0 眷屬 - 1
                                ctlData.setChkCode(benDetailCase.getChkCode()); // 編審註記代號
                                ctlData.setValiSym(valisYm); // 有效年月起
                                ctlData.setValiEym(valieYm); // 有效年月迄
                                ctlData.setKeyValue(benDetailCase.getKeyValue()); // 關鍵欄位值
                                ctlData.setCrtUser(userData.getUserId()); // 新增者代號
                                ctlData.setCrtTime(now); // 新增日期時間

                                // 加入存檔 List 中
                                saveList.add(ctlData);
                            }
                            else { // 眷屬
                                DisabledCheckMarkLevelAdjustFamDetailDataCase famDetailCase = caseData.searchFamDetailByKey(key);

                                if (famDetailCase != null) {
                                    Bachkcontrl ctlData = new Bachkcontrl();

                                    ctlData.setApNo(famDetailCase.getApNo()); // 受理編號
                                    ctlData.setSeqNo(famDetailCase.getSeqNo()); // 序號
                                    ctlData.setIssuYm(famDetailCase.getIssuYm()); // 核定年月
                                    ctlData.setPayYm(famDetailCase.getPayYm()); // 給付年月
                                    ctlData.setContrlTyp("1"); // 管制對象類別 受款人 - 0 眷屬 - 1
                                    ctlData.setChkCode(famDetailCase.getChkCode()); // 編審註記代號
                                    ctlData.setValiSym(valisYm); // 有效年月起
                                    ctlData.setValiEym(valieYm); // 有效年月迄
                                    ctlData.setKeyValue(famDetailCase.getKeyValue()); // 關鍵欄位值
                                    ctlData.setCrtUser(userData.getUserId()); // 新增者代號
                                    ctlData.setCrtTime(now); // 新增日期時間

                                    // 加入存檔 List 中
                                    saveList.add(ctlData);
                                }
                            }
                        }
                    } // ] ... end if (parameterValue != null && parameterValue.length > 0 && StringUtils.equals(parameterValue[0], ADJ_LEVEL_MUST_HANDLE))
                } // ] ... end if (StringUtils.startsWith(parameterName, ADJ_LEVEL_FIELD_PREFIX))
            } // ] ... end while (parameterNames.hasMoreElements())

            // 存檔
            updateService.saveDisabledCheckMarkLevelAdjustData(caseData.getApNo(), caseData.getIssuYm(), saveList, caseData.getRegetCipbMk());
            //修改延伸給付主檔 20121226新增 下拉選單 得請領起始年月
            Baappexpand Baappexpand = new Baappexpand();
            if(StringUtils.isNotBlank(adjustForm.getAbleApsYm())){
            if(adjustForm.getAbleApsYm().equals("appDate")){
            	Baappexpand.setAbleApsYm(DateUtility.changeChineseYearMonthType(caseData.getAppDateJsp()));
            }else if (adjustForm.getAbleApsYm().equals("evtJobDate")){
            	Baappexpand.setAbleApsYm(DateUtility.changeChineseYearMonthType(caseData.getEvtJobDateJsp()));
            }
            }else{
            	Baappexpand.setAbleApsYm("");
            }
            Baappexpand.setApNo(caseData.getApNo());
            Baappexpand.setBaappbaseId(caseData.getBaappbaseId());
            updateService.updateAbleapsYmForDisabledCheckMarkLevelAdjust(Baappexpand);
            // 呼叫即時編審 WebService
            // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
            String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
            log.info("webServiceUrl: "+ webServiceUrl);
            String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
            try {
                SingleCheckMarkServiceHttpBindingStub binding;
                binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator().getSingleCheckMarkServiceHttpPort();
                returnCode = binding.doCheckMark(caseData.getApNo(),SecureToken.getInstance().getToken());
            }
            catch (Exception e) {
                log.error("DisabledCheckMarkLevelAdjustAction.doSave() 即時編審發生錯誤:" + ExceptionUtility.getStackTrace(e));
            }

            // 設定存檔訊息
            if (StringUtils.equals(ConstantKey.DO_CHECK_MARK_FAIL, returnCode))
                saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage()); // 設定即時編審失敗訊息
            else
                saveMessages(session, DatabaseMessageHelper.getCheckMarkLevelAdjustSaveSuccessMessage()); // 設定存檔成功訊息

            // 將 CaseData 自 Session 移除
            CaseSessionHelper.removeDisabledCheckMarkLevelAdjustCase(request);
            
            // 重新取得更新後的案件資料
            caseData = updateService.getDisabledCheckMarkLevelAdjustData(apNo);
            //放入畫面使用之 得請領起始年月
            String appDateJsp = DateUtility.changeDateType(caseData.getAppDate());
            String evtJobDate = DateUtility.changeDateType(caseData.getEvtJobDate());
            //jsp畫面使用之年月
            caseData.setAppDateJsp(appDateJsp.substring(0, 5));
            caseData.setEvtJobDateJsp(evtJobDate.substring(0, 5));
            // 將 CaseData 塞到 Session 中
            FormSessionHelper.setDisabledCheckMarkLevelAdjustForm(adjustForm,request);
            CaseSessionHelper.setDisabledCheckMarkLevelAdjustCase(caseData, request);

            log.debug("執行 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 - 修改頁面 DisabledCheckMarkLevelAdjustAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledCheckMarkLevelAdjustAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getCheckMarkLevelAdjustSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }
    
    /**
     * 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 - 修改頁面 (bamo0d161c.jsp) <br>
     * 按下「檢視受理/審核清單」的動作
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 - 修改頁面 DisabledCheckMarkLevelAdjustAction.doPrint() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 自 Session 中取出 Case 資料
            DisabledCheckMarkLevelAdjustCase caseData = CaseSessionHelper.getDisabledCheckMarkLevelAdjustCase(request);

            if (caseData == null) {
                // 設定報表產製失敗訊息
                saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
                return mapping.findForward(FORWARD_DETAIL_PAGE);
            }

            List<DisableReviewRpt01Case> caseList = new ArrayList<DisableReviewRpt01Case>();
            caseList = rptService.getDisableReviewRpt01DataBy(caseData.getApNo(), caseData.getApNo());

            if (caseList == null || caseList.size() == 0) {
                // 設定報表產製失敗訊息
                saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
                return mapping.findForward(FORWARD_DETAIL_PAGE);
            }
            
            if(caseList != null){
                for(int i = 0 ; i<caseList.size(); i++){
                	//放入得請領起始年月formatChineseYearMonthString
                	DisableReviewRpt01Case caseDataCase = (DisableReviewRpt01Case) caseList.get(i);
                	// 處理日期前
                    String ableapsYmBefor = rptService.getAbleapsYmForDisabledCheckMarkLevelAdjust(caseDataCase.getApNo(),caseDataCase.getBaappbaseId());
                    
                    if(StringUtils.isNotBlank(ableapsYmBefor)){
                    // 處理日期後 放入list內各筆資料之AbleapsYm 得請領起始年月
                    String ableapsYmAfter = DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(ableapsYmBefor),false);
                    caseDataCase.setAbleapsYm(ableapsYmAfter);
                    }
                }
                }

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            //DisableReviewRpt01Report report = new DisableReviewRpt01Report();
            //baoOutput = report.doReport(userData, caseList);
            //20140327修改
            DisableReviewRpt01Case caseObj = new DisableReviewRpt01Case();
            caseObj = (DisableReviewRpt01Case)caseList.get(0);                    
            if("36".equals(caseObj.getPayKind())){
                DisableReviewRpt01Kind36Report report = new DisableReviewRpt01Kind36Report();
                baoOutput = report.doReport(userData, caseList); 
            } else {
                DisableReviewRpt01Report report = new DisableReviewRpt01Report();
                baoOutput = report.doReport(userData, caseList);                        
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            response.setHeader("Content-disposition", "attachment; filename=\"DisableReviewRpt01_" + printDate + ".pdf" + "\"");
            response.setHeader("Content-Encoding", "pdf");
            response.setContentType("application/pdf"); // 設定MIME
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

            log.debug("執行 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 - 修改頁面 DisabledCheckMarkLevelAdjustAction.doPrint() 完成 ... ");

            return null;
        }
        catch (Exception e) {
            // 設定報表產製失敗訊息
            log.error("DisabledCheckMarkLevelAdjustAction.doPrint() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_DETAIL_PAGE);
        }
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
