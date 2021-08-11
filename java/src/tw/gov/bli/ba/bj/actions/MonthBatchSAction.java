package tw.gov.bli.ba.bj.actions;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.encoder.Encode;

import com.iisi.SecureToken;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.MonthBatchDtlCase;
import tw.gov.bli.ba.bj.cases.MonthBatchSCase;
import tw.gov.bli.ba.bj.forms.MonthBatchSForm;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面 (baba0m050x.jsp)
 * 
 * @author Noctis
 */
public class MonthBatchSAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthBatchSAction.class);

    private BjService bjService;

    private static final String FORWARD_QUERY_LIST_SUCCESS = "queryListSuccess";
    private static final String FORWARD_QUERY_LIST_FAIL = "queryListFail";
    private static final String payCode = ConstantKey.BAAPPBASE_PAGE_PAYKIND_S;

    /**
     * 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面 (baba0m050x.jsp) 按下 確認
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面  MonthBatchSAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthBatchSForm iform = (MonthBatchSForm) form;

        String procTypeStr = "";

        if (iform.getProcType().equals("2")) {
            procTypeStr = "批次月試核";
        }
        else if (iform.getProcType().equals("3")) {
            procTypeStr = "第一次批次月核定";
        }
        else if (iform.getProcType().equals("4")) {
            procTypeStr = "第二次批次月核定";
        }

        try {

            // 查詢批次月處理作業於系統日期當日是否已經執行過。
            // int checkCount = bjService.selectCountCheckForBjMonthBatchBy(payCode,DateUtility.changeChineseYearMonthType(iform.getIssuYm()),iform.getProcType()).intValue();

            // if(checkCount > 0){

            // saveMessages(session, CustomMessageHelper.getMonthBatchPerformedErrorMessage(procTypeStr));
            // log.debug("批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面 MonthBatchSAction.doConfirm() 完成 ... ");
            // return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);

            // }else{
            // 新增一筆批次作業紀錄於「勞保年金批次作業檔_BABATCHJOB」中
            // bjService.insertBabatchjobDataForMonthBatch(DateUtility.changeChineseYearMonthType(iform.getIssuYm()) , payCode, iform.getProcType(), userData);

            String returnCode = "";

            if (iform.getProcType().equals("2")) {

                // 呼叫批次編審
                // String endpoint = (ResourceBundle.getBundle("webServiceUrl")).getString("jobScheduleWebServicesUrl");
                String endpoint = PropertyHelper.getProperty("jobScheduleWebServicesUrl");
                // 直接引用遠程的wsdl文件
                // 以下都是套路
                Service service = new Service();

                Call call = (Call) service.createCall();

                call.setTargetEndpointAddress(endpoint);

                call.setOperationName("scheduleMonth");// WSDL里面描述的接口名稱

                call.addParameter("in0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// payCode
                call.addParameter("in1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// dateTime
                call.addParameter("in2", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// userId
                call.addParameter("in3", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// deptId
                call.addParameter("in4", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// loginIP
                call.addParameter("in5", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// paySeqNo
                call.addParameter("in6", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// caseTyp
                call.addParameter("in7", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// TOKEN

                call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 設置返回類型

                // 排成延後五分鐘開始
                DateFormat df = new SimpleDateFormat("yyyyMMddHHmm", Locale.TAIWAN);
                Date now = new Date();
                now.setTime(System.currentTimeMillis());
                Date afterDate = new Date(now.getTime() + 150000);
                String dateTime = df.format(afterDate);
                String dateTime1 = DateUtility.getNowWestDateTime(true);

                String userId = userData.getUserId();
                String deptId = userData.getDeptId();
                String loginIP = userData.getLoginIP();

                returnCode = (String) call.invoke(new Object[] { payCode, dateTime, userId, deptId, loginIP, "1", iform.getCaseTyp(), SecureToken.getInstance().getToken() });
                log.debug("執行 批次月處理作業 - 遺屬年金批次月處理作業 - 呼叫批次排程編審結果... " + returnCode);

            }
            else {
                // 呼叫批次編審
                // String endpoint = (ResourceBundle.getBundle("webServiceUrl")).getString("jobScheduleWebServicesUrl");
                String endpoint = PropertyHelper.getProperty("jobScheduleWebServicesUrl");
                // 直接引用遠程的wsdl文件
                // 以下都是套路
                Service service = new Service();

                Call call = (Call) service.createCall();

                call.setTargetEndpointAddress(endpoint);

                call.setOperationName("scheduleMonthCheck");// WSDL里面描述的接口名稱

                call.addParameter("in0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// payCode
                call.addParameter("in1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// procType
                call.addParameter("in2", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// dateTime
                call.addParameter("in3", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// userId
                call.addParameter("in4", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// deptId
                call.addParameter("in5", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// loginIP
                call.addParameter("in6", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// paySeqNo
                call.addParameter("in7", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// caseTyp
                call.addParameter("in8", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// TOKEN

                call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 設置返回類型

                // 排成延後五分鐘開始
                DateFormat df = new SimpleDateFormat("yyyyMMddHHmm", Locale.TAIWAN);
                Date now = new Date();
                now.setTime(System.currentTimeMillis());
                Date afterDate = new Date(now.getTime() + 150000);
                String dateTime = df.format(afterDate);

                String userId = userData.getUserId();
                String deptId = userData.getDeptId();
                String loginIP = userData.getLoginIP();
                if (iform.getProcType().equals("3")) {
                    returnCode = (String) call.invoke(new Object[] { payCode, iform.getProcType(), dateTime, userId, deptId, loginIP, "1", iform.getCaseTyp(), SecureToken.getInstance().getToken() });
                }
                else {
                    returnCode = (String) call.invoke(new Object[] { payCode, iform.getProcType(), dateTime, userId, deptId, loginIP, "1", null, SecureToken.getInstance().getToken() });
                }

                log.debug("執行  批次月處理作業 - 遺屬年金批次月處理作業 - 呼叫批次排程編審結果... " + returnCode);
            }

            String scheduleMsg = "";

            if (returnCode.equals("0")) {

                scheduleMsg = "遺屬年金" + iform.getIssuYm() + "核定年月批次月處理作業(月處理類型(" + procTypeStr + "))";

                // 批次作業完成後，直接將批次月處理條件帶入[進度查詢_BABA0M050X]頁面查明，
                List<MonthBatchSCase> dataList = bjService.selectMonthBatchSQueryDataListBy(payCode, null, DateUtility.getNowWestDate(), iform.getProcType());

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setMonthBatchSCaseList(dataList, request);

                saveMessages(session, CustomMessageHelper.getMonthScheduleBatchSuccessMessage(scheduleMsg));
                log.debug("批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面  MonthBatchSAction.doConfirm() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);

            }
            else if (returnCode.equals("2")) {

                saveMessages(session, CustomMessageHelper.getMonthBatchPerformedErrorMessage(procTypeStr));
                log.debug("批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面  MonthBatchLAction.doConfirm() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);

            }
            else {

                scheduleMsg = "遺屬年金" + iform.getIssuYm() + "核定年月批次月處理作業(月處理類型(" + procTypeStr + "))";

                saveMessages(session, CustomMessageHelper.getMonthScheduleBatchFailMessage(scheduleMsg));
                log.debug("批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面  MonthBatchSAction.doConfirm() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            // }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthBatchSAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面 (baba0m050x.jsp) 按下 進度查詢
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面  MonthBatchSAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthBatchSForm iform = (MonthBatchSForm) form;

        try {

            List<MonthBatchSCase> dataList = bjService.selectMonthBatchSQueryDataListBy(payCode, DateUtility.changeChineseYearMonthType(iform.getIssuYm()), null, iform.getProcType());

            if (dataList.size() < 1) {

                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面  MonthBatchSAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);

            }
            else {

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setMonthBatchSCaseList(dataList, request);

                log.debug("批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面  MonthBatchSAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthBatchSAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面 (baba0m051q.jsp) 按下 檔案下載
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDownLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面  MonthBatchSAction.doDownLoad() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthBatchSForm iform = (MonthBatchSForm) form;

        try {

            String dirForDownload = "/apdata/ba/ba_rpt/";
            String fileNameAll = StringUtils.defaultString(request.getParameter("fileName"));
            String fileName = fileNameAll.replace("/apdata/ba/ba_rpt/", "");
            // String fileName = "test111";

            if (StringUtils.isBlank(fileName)) {

                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 資料頁面  MonthBatchSAction.doDownLoad() 完成 ... ");
                return mapping.findForward(FORWARD_QUERY_LIST_FAIL);

            } else {
            	Pattern pattern = Pattern.compile("[\\/:\"*?<>|]");
        		boolean hasInvalidStr = pattern.matcher(fileName).find();
        		if(hasInvalidStr) {
        			saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    log.debug("批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 資料頁面  MonthBatchSAction.doDownLoad() 完成 ... ");
                    return mapping.findForward(FORWARD_QUERY_LIST_FAIL);
        		}

                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                baoOutput = bjService.getZipFile(fileName, dirForDownload);

                if (baoOutput == null) {

                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    log.debug("批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 資料頁面  MonthBatchSAction.doDownLoad() 完成 ... ");
                    return mapping.findForward(FORWARD_QUERY_LIST_FAIL);

                }
                else {
                    // 設定回傳回 Client 端之檔案大小, 若不設定,
                    // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                    response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(fileName) + "\"");
                    response.setHeader("Content-Encoding", "zip");
                    response.setContentType("application/zip"); // 設定MIME
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

                    return null;
                }
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthBatchSAction.doDownLoad() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 查詢頁面 (baba0m051q.jsp) 按下 狀態明細
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 資料頁面  MonthBatchSAction.doDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthBatchSForm iform = (MonthBatchSForm) form;

        try {

            String baJobId = StringUtils.defaultString(request.getParameter("baJobId"));

            List<MonthBatchDtlCase> dataList = bjService.selectBabatchjobdtlDataForBjMonthBatchBy(baJobId);

            if (dataList.size() < 1) {

                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 資料頁面  MonthBatchSAction.doDetail() 完成 ... ");
                return mapping.findForward(FORWARD_QUERY_LIST_FAIL);

            }
            else {

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setMonthBatchDtlCaseList(dataList, request);

                log.debug("批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 資料頁面  MonthBatchSAction.doDetail() 完成 ... ");
                return mapping.findForward(FORWARD_QUERY_LIST_SUCCESS);
            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthBatchSAction.doDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 資料頁面 (baba0m111q.jsp,baba0m112a.jsp) 按下 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 返回 MonthBatchSAction.doBack() 開始 ... ");

        MonthBatchSForm iform = (MonthBatchSForm) form;

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        iform.setIssuYm("");
        iform.setProcType("");
        CaseSessionHelper.removeMonthBatchSCaseList(request);

        log.debug("執行批次處理 - 批次月處理作業 - 遺屬年金批次月處理作業 - 返回 MonthBatchSAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }

}
