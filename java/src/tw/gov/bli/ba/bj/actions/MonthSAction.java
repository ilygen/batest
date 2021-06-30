package tw.gov.bli.ba.bj.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.MonthQueryCase;
import tw.gov.bli.ba.bj.cases.MonthSCase;
import tw.gov.bli.ba.bj.forms.MonthSForm;
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
import com.iisi.SecureToken;

/**
 * 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面 (baba0m110x.jsp)
 * 
 * @author Noctis
 */
public class MonthSAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthSAction.class);

    private BjService bjService;

    private static final String FORWARD_QUERY_LIST_SUCCESS = "queryListSuccess";
    private static final String FORWARD_QUERY_LIST_FAIL = "queryListFail";
    private static final String payCode = ConstantKey.BAAPPBASE_PAGE_PAYKIND_S;

    /**
     * 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面 (baba0m110x.jsp) 按下 確認
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面  MonthSAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthSForm iform = (MonthSForm) form;

        try {

            // 核定年月條件於確認時，僅能輸入目前qryISSUYM所查出的核定年月減一個月。
            // 讀取目前系統最大核定年月
            String maxIssuYm = bjService.selectMaxIssuYmBy(payCode);

            String checkIssuYm = DateUtility.calMonth(maxIssuYm + "01", -1).substring(0, 6);

            if (!DateUtility.changeChineseYearMonthType(iform.getIssuYm()).equals(checkIssuYm)) {

                saveMessages(session, CustomMessageHelper.getCheckMonthErrorMessage());
                log.debug("批次處理 - 線上月核定作業 - 老年年金線上月核定作業 - 查詢頁面  MonthLAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            // 查詢線上月核定作業於系統日期當日是否已經執行過。
            int checkCount = bjService.selectCountCheckForBjMonthBy(payCode).intValue();

            if (checkCount > 0) {

                saveMessages(session, CustomMessageHelper.getMonthPerformedErrorMessage());
                log.debug("批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面  MonthSAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);

            }
            else {

                List<MonthSCase> dataList = bjService.selectMonthSDataListBy(payCode, DateUtility.changeChineseYearMonthType(iform.getIssuYm()));

                if (dataList.size() < 1) {

                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    log.debug("批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面  MonthSAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);

                }
                else {

                    MonthQueryCase caseData = new MonthQueryCase();
                    caseData.setPagePayKind(payCode);

                    // 將 查詢結果清單 存到 Request Scope
                    CaseSessionHelper.setMonthQueryCaseForTitle(caseData, request);// title預設抓第一筆資料
                    CaseSessionHelper.setMonthSCaseList(dataList, request);

                    log.debug("批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面  MonthSAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
                }

            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthSAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面 (baba0m110x.jsp) 按下 查詢
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面  MonthSAction.doQueryList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthSForm iform = (MonthSForm) form;

        try {

            List<MonthSCase> dataList = bjService.selectMonthSQueryDataListBy(payCode, DateUtility.changeChineseYearMonthType(iform.getIssuYm()), DateUtility.changeDateType(iform.getChkDate(), false));

            if (dataList.size() < 1) {

                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面  MonthSAction.doQueryList() 完成 ... ");
                return mapping.findForward(FORWARD_QUERY_LIST_FAIL);

            }
            else {

                MonthQueryCase caseData = new MonthQueryCase();
                caseData.setPagePayKind(payCode);

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setMonthQueryCaseForTitle(caseData, request);
                CaseSessionHelper.setMonthSCaseList(dataList, request);

                log.debug("批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面  MonthSAction.doQueryList() 完成 ... ");
                return mapping.findForward(FORWARD_QUERY_LIST_SUCCESS);
            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthSAction.doQueryList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 清單頁面 (baba0m111q.jsp) 按下 確認
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 清單頁面  MonthSAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthSForm iform = (MonthSForm) form;

        try {

            // 取得清單資料
            List<MonthSCase> dataList = CaseSessionHelper.getMonthSCaseList(request);

            // 紀錄月核勾選的apno
            List<String> apNoList = new ArrayList<String>();

            // 讀取目前系統最大核定年月
            String maxIssuYm = bjService.selectMaxIssuYmBy(payCode);
            // 已核付過資料
            StringBuffer apNoMsg = new StringBuffer();

            String[] tmpIndexList = iform.getApNoOfConfirm().split(",");

            for (int i = 0; i < tmpIndexList.length; i++) {
                String dataIndex = tmpIndexList[i];

                if (StringUtils.isNotBlank(dataIndex)) {

                    MonthSCase caseObj = (MonthSCase) dataList.get(Integer.parseInt(dataIndex));

                    // 紀錄月核勾選的apno
                    apNoList.add(caseObj.getApNo());

                    // 若ADJISSUYMMK調整前一核定年月欄位為Y，則帶入ISSUYM前一個月
                    String issuYm = maxIssuYm;

                    if (StringUtils.isNotBlank(issuYm)) {

                        if (StringUtils.isNotBlank(caseObj.getAdjIssuYmMk())) {
                            if (caseObj.getAdjIssuYmMk().equals("Y")) {
                                issuYm = DateUtility.calMonth(maxIssuYm + "01", -1).substring(0, 6);
                            }
                        }
                    }

                    // 依照畫面上所勾選要編審的案件，依照下列SQL逐筆檢核該案件是否於該核定年月已經核付過。
                    int checkCount = bjService.selectCountForMonthBy(caseObj.getApNo(), issuYm).intValue();

                    if (checkCount > 0) {
                        apNoMsg.append("受理編號" + caseObj.getApNo() + "於核定年月" + DateUtility.changeWestYearMonthType(issuYm) + "已核付，");
                    }
                    else {

                        // 所勾選要編審的案件，於核定年月均無核付，則逐筆呼叫單筆月核定編審程式，逐筆編審
                        // 呼叫即時編審WebService
                        // String endpoint = (ResourceBundle.getBundle("webServiceUrl")).getString("monthCheckWebServicesUrl");
                        String endpoint = PropertyHelper.getProperty("monthCheckWebServicesUrl");
                        // 直接引用遠程的wsdl文件
                        // 以下都是套路
                        Service service = new Service();

                        Call call = (Call) service.createCall();

                        call.setTargetEndpointAddress(endpoint);

                        call.setOperationName("doCheckMark");// WSDL里面描述的接口名稱

                        call.addParameter("in0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// apNo

                        call.addParameter("in1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// AdjIssuYmMk

                        call.addParameter("in2", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); // TOKEN

                        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 設置返回類型

                        String returnCode = (String) call.invoke(new Object[] { caseObj.getApNo(), caseObj.getAdjIssuYmMk(), SecureToken.getInstance().getToken() });
                        log.debug("執行 線上月試核作業 - 老年年金線上月試核作業 - 呼叫即時編審結果... " + returnCode);
                        String procMsg = returnCode;// 編審訊息

                        // 更新 勞保年金線上作業檔
                        bjService.updateBaonlinejobDataForMonthS(caseObj, issuYm, procMsg, userData);

                        // 當所有勾選的編審案件，全部編審完畢，讀取「承辦人電子郵件檔_BAUSEREMAIL」，通知保險收支科執行「線上產製媒體檔作業」。
                        // List<BaUserEmailCase> baUserEmailCaseDataList = bjService.selectBauseremailDataListBy();

                        bjService.sendMonthMail(payCode, issuYm);
                    }

                }
            }

            // 更新頁面資料
            List<MonthSCase> dataListNew = new ArrayList<MonthSCase>();

            for (int i = 0; i < apNoList.size(); i++) {
                List<MonthSCase> caseData = bjService.selectMonthSApprovedDataListBy(apNoList.get(i), DateUtility.changeChineseYearMonthType(iform.getIssuYm()));

                if (caseData.size() > 0) {
                    dataListNew.add(caseData.get(0));
                }
            }

            if (dataListNew.size() < 1) {

                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 清單頁面  MonthSAction.doConfirm() 完成 ... ");
                return mapping.findForward(FORWARD_QUERY_LIST_FAIL);

            }
            else {

                MonthQueryCase caseData = new MonthQueryCase();
                caseData.setPagePayKind(payCode);

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setMonthQueryCaseForTitle(caseData, request);
                CaseSessionHelper.setMonthSCaseList(dataListNew, request);

                if (StringUtils.isNotBlank(apNoMsg.toString())) {
                    saveMessages(session, CustomMessageHelper.getMonthApprovedFailMessage(apNoMsg.toString()));
                }
                else {
                    saveMessages(session, CustomMessageHelper.getMonthSuccessMessage());
                }
                log.debug("批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 清單頁面  MonthSAction.doConfirm() 完成 ... ");
                return mapping.findForward(FORWARD_QUERY_LIST_SUCCESS);
            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthSAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 清單頁面 (baba0m111q.jsp) 按下返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 清單頁面  MonthSAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthSForm iform = (MonthSForm) form;

        try {

            // 取得清單資料
            List<MonthSCase> dataList = CaseSessionHelper.getMonthSCaseList(request);

            String[] tmpIndexList = iform.getApNoOfConfirm().split(",");

            for (int i = 0; i < tmpIndexList.length; i++) {
                String dataIndex = tmpIndexList[i];

                if (StringUtils.isNotBlank(dataIndex)) {

                    MonthSCase caseObj = (MonthSCase) dataList.get(Integer.parseInt(dataIndex));

                    // 依照畫面上所勾選要編審的案件於「勞保年金線上作業檔_BAONLINEJOB」中上D註記。
                    bjService.updateBaonlinejobDataForMonthSDelete(caseObj, userData);

                }
            }

            // 更新頁面資料
            List<MonthSCase> dataListNew = bjService.selectMonthSDataListBy(payCode, DateUtility.changeChineseYearMonthType(iform.getIssuYm()));

            MonthQueryCase caseData = new MonthQueryCase();
            caseData.setPagePayKind(payCode);

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setMonthQueryCaseForTitle(caseData, request);
            CaseSessionHelper.setMonthSCaseList(dataListNew, request);

            log.debug("批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 清單頁面  MonthSAction.doDelete() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthSAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 資料頁面 (baba0m111q.jsp,baba0m112a.jsp) 按下 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 返回 MonthSAction.doBack() 開始 ... ");

        MonthSForm iform = (MonthSForm) form;

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        iform.setApNoOfConfirm("");
        iform.setIssuYm("");
        iform.setChkDate("");
        CaseSessionHelper.removeMonthQueryCaseForTitle(request);
        CaseSessionHelper.removeMonthSCaseList(request);

        log.debug("執行批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 返回 MonthSAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }

}
