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
import tw.gov.bli.ba.bj.cases.MonthCheckKCase;
import tw.gov.bli.ba.bj.cases.MonthQueryCase;
import tw.gov.bli.ba.bj.forms.MonthCheckKForm;
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
 * 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 查詢頁面 (baba0m090x.jsp)
 * 
 * @author Noctis
 */
public class MonthCheckKAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthCheckKAction.class);

    private BjService bjService;

    private static final String FORWARD_QUERY_LIST_SUCCESS = "queryListSuccess";
    private static final String FORWARD_QUERY_LIST_FAIL = "queryListFail";
    // 給付別
    private static final String payCode = ConstantKey.BAAPPBASE_PAGE_PAYKIND_K;

    /**
     * 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 查詢頁面 (baba0m090x.jsp) 按下 確認
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 查詢頁面  MonthCheckKAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthCheckKForm iform = (MonthCheckKForm) form;

        try {

            // 查詢每個受理案件及處理狀態，如有符合訊息區顯示受理編號及處理狀態不可編審，並停留於原畫面。
            List<MonthCheckKCase> chkDataList = bjService.selectChkMonthKDataBy(iform.getApNoStr(), iform.getApNoAStr(), iform.getApNoBStr(), iform.getApNoCStr(), iform.getApNoDStr(), iform.getApNoEStr(), iform.getApNoFStr(), iform.getApNoGStr(), iform.getApNoHStr(), iform.getApNoIStr());

            if (chkDataList.size() > 0) {
                StringBuffer chkDataMsg = new StringBuffer();
                for (MonthCheckKCase caseData : chkDataList) {
                    String chkMsg = caseData.getApNo() + " 處理狀態：" + caseData.getProcStatString() + "，";
                    chkDataMsg.append(chkMsg);
                }

                saveMessages(session, CustomMessageHelper.getMonthApprovedFailMessage(chkDataMsg.toString()));
                log.debug("批次處理 - 線上月試核作業 - 老年年金線上月試核作業 - 查詢頁面  MonthCheckLAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            // 取得資料
            List<MonthCheckKCase> dataList = bjService.selectMonthCheckKDataListBy(payCode, iform.getApNoStr(), iform.getApNoAStr(), iform.getApNoBStr(), iform.getApNoCStr(), iform.getApNoDStr(), iform.getApNoEStr(), iform.getApNoFStr(), iform.getApNoGStr(), iform.getApNoHStr(), iform.getApNoIStr());

            if (dataList.size() < 1) {

                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 查詢頁面  MonthCheckKAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);

            }
            else {

                MonthQueryCase caseData = new MonthQueryCase();
                caseData.setPagePayKind(payCode);

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setMonthQueryCaseForTitle(caseData, request);
                CaseSessionHelper.setMonthCheckKCaseList(dataList, request);

                log.debug("批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 查詢頁面  MonthCheckKAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthCheckKAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 查詢頁面 (baba0m090x.jsp) 按下 查詢
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 查詢頁面  MonthCheckKAction.doQueryList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthCheckKForm iform = (MonthCheckKForm) form;

        try {

            List<MonthCheckKCase> dataList = bjService.selectMonthCheckKQueryDataListBy(payCode, DateUtility.changeChineseYearMonthType(iform.getIssuYm()));

            if (dataList.size() < 1) {

                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 查詢頁面  MonthCheckKAction.doQueryList() 完成 ... ");
                return mapping.findForward(FORWARD_QUERY_LIST_FAIL);

            }
            else {

                MonthQueryCase caseData = new MonthQueryCase();
                caseData.setPagePayKind(payCode);

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setMonthQueryCaseForTitle(caseData, request);
                CaseSessionHelper.setMonthCheckKCaseList(dataList, request);

                log.debug("批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 查詢頁面  MonthCheckKAction.doQueryList() 完成 ... ");
                return mapping.findForward(FORWARD_QUERY_LIST_SUCCESS);
            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthCheckKAction.doQueryList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 清單頁面 (baba0m091q.jsp) 按下 確認
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 清單頁面  MonthCheckKAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthCheckKForm iform = (MonthCheckKForm) form;

        try {

            // 取得清單資料
            List<MonthCheckKCase> dataList = CaseSessionHelper.getMonthCheckKCaseList(request);

            // 紀錄月核勾選的apno
            List<String> apNoList = new ArrayList<String>();

            // 重置 調整前一核定年月 註記
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setAdjIssuYmMk("");
            }

            // 調整前一核定年月
            String[] tmpIndexBfMonthList = iform.getBfMonthOfConfirm().split(",");

            for (int i = 0; i < tmpIndexBfMonthList.length; i++) {
                String dataIndex = tmpIndexBfMonthList[i];

                if (StringUtils.isNotBlank(dataIndex)) {
                    dataList.get(Integer.parseInt(dataIndex)).setAdjIssuYmMk("Y");
                }
            }

            // 讀取目前系統最大核定年月
            String maxIssuYm = bjService.selectMaxIssuYmBy(payCode);
            // 已存在於「勞保年金線上作業檔_BAONLINEJOB」中尋錫
            StringBuffer apNoMsg1 = new StringBuffer();
            // 已核付過資料訊息
            StringBuffer apNoMsg2 = new StringBuffer();

            String[] tmpIndexList = iform.getApNoOfConfirm().split(",");

            for (int i = 0; i < tmpIndexList.length; i++) {
                String dataIndex = tmpIndexList[i];

                if (StringUtils.isNotBlank(dataIndex)) {

                    MonthCheckKCase caseObj = (MonthCheckKCase) dataList.get(Integer.parseInt(dataIndex));

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

                    // 依照畫面上所勾選要編審的案件，依照下列SQL逐筆檢核該案件是否已存在於「勞保年金線上作業檔_BAONLINEJOB」中。
                    int checkCount1 = bjService.selectCountCheckForBjMonthCheckBy(caseObj.getApNo()).intValue();
                    // 依照畫面上所勾選要編審的案件，依照下列SQL逐筆檢核該案件是否於該核定年月已經核付過。
                    int checkCount2 = bjService.selectCountForMonthBy(caseObj.getApNo(), issuYm).intValue();

                    if (checkCount1 > 0 || checkCount2 > 0) {
                        if (checkCount1 > 0) {
                            apNoMsg2.append(caseObj.getApNo() + "月試核已存在於勞保年金線上作業檔中，");
                        }
                        if (checkCount2 > 0) {
                            apNoMsg2.append(caseObj.getApNo() + "於核定年月" + DateUtility.changeWestYearMonthType(issuYm) + "已核付，");
                        }
                    }
                    else {

                        // 所勾選要編審的案件，於核定年月均無核付，則逐筆呼叫單筆月核定編審程式，逐筆編審
                        // 呼叫即時編審WebService
                        // String endpoint = (ResourceBundle.getBundle("webServiceUrl")).getString("monthWebServicesUrl");
                        String endpoint = PropertyHelper.getProperty("monthWebServicesUrl");
                        // 直接引用遠程的wsdl文件
                        // 以下都是套路
                        Service service = new Service();

                        Call call = (Call) service.createCall();

                        call.setTargetEndpointAddress(endpoint);

                        call.setOperationName("doCheckMark");// WSDL里面描述的接口名稱

                        call.addParameter("in0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); // apNo

                        call.addParameter("in1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); // AdjIssuYmMk

                        call.addParameter("in2", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); // TOKEN

                        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 設置返回類型

                        String returnCode = (String) call.invoke(new Object[] { caseObj.getApNo(), caseObj.getAdjIssuYmMk(), SecureToken.getInstance().getToken() });
                        log.debug("執行 線上月試核作業 - 老年年金線上月試核作業 - 呼叫即時編審結果... " + returnCode);
                        String procMsg = returnCode;// 編審訊息

                        // 更新 勞保年金線上作業檔
                        bjService.insertBaonlinejobDataForMonthCheckK(caseObj, issuYm, procMsg, userData);
                    }

                }
            }

            // 更新頁面資料
            List<MonthCheckKCase> dataListNew = new ArrayList<MonthCheckKCase>();

            for (int i = 0; i < apNoList.size(); i++) {
                List<MonthCheckKCase> caseData = bjService.selectMonthCheckKApprovedDataListBy(apNoList.get(i), DateUtility.changeChineseYearMonthType(iform.getIssuYm()));

                if (caseData.size() > 0) {
                    dataListNew.add(caseData.get(0));
                }
            }
            // 更新頁面資料 原本資料全拉
            // List<MonthCheckLCase> dataListNew = bjService.selectMonthCheckLQueryDataListBy(payCode, "");

            // if(dataListNew.size() < 1){
            //
            // saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
            // log.debug("批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 清單頁面  MonthCheckKAction.doConfirm() 完成 ... ");
            // return mapping.findForward(FORWARD_QUERY_LIST_FAIL);
            //
            // }else{

            MonthQueryCase caseData = new MonthQueryCase();
            caseData.setPagePayKind(payCode);

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setMonthQueryCaseForTitle(caseData, request);
            CaseSessionHelper.setMonthCheckKCaseList(dataListNew, request);

            if (StringUtils.isNotBlank(apNoMsg1.toString()) || StringUtils.isNotBlank(apNoMsg2.toString())) {
                saveMessages(session, CustomMessageHelper.getMonthApprovedFailMessage(apNoMsg1.toString() + apNoMsg2.toString()));
            }
            else {
                saveMessages(session, CustomMessageHelper.getMonthCheckSuccessMessage());
            }
            log.debug("批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 清單頁面  MonthCheckKAction.doConfirm() 完成 ... ");
            return mapping.findForward(FORWARD_QUERY_LIST_SUCCESS);
            // }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthCheckKAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 清單頁面 (baba0m092a.jsp) 按下 確認
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirmList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 清單頁面  MonthCheckKAction.doConfirmList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthCheckKForm iform = (MonthCheckKForm) form;

        try {

            // 取得清單資料
            List<MonthCheckKCase> dataList = CaseSessionHelper.getMonthCheckKCaseList(request);

            // 紀錄月核勾選的apno
            List<String> apNoList = new ArrayList<String>();

            // 重置 調整前一核定年月 註記
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setAdjIssuYmMk("");
            }

            // 調整前一核定年月
            String[] tmpIndexBfMonthList = iform.getBfMonthOfConfirm().split(",");

            for (int i = 0; i < tmpIndexBfMonthList.length; i++) {
                String dataIndex = tmpIndexBfMonthList[i];

                if (StringUtils.isNotBlank(dataIndex)) {
                    dataList.get(Integer.parseInt(dataIndex)).setAdjIssuYmMk("Y");
                }
            }

            // 讀取目前系統最大核定年月
            String maxIssuYm = bjService.selectMaxIssuYmBy(payCode);
            // 已核付過資料訊息
            StringBuffer apNoMsg = new StringBuffer();

            String[] tmpIndexList = iform.getApNoOfConfirm().split(",");

            for (int i = 0; i < tmpIndexList.length; i++) {
                String dataIndex = tmpIndexList[i];

                if (StringUtils.isNotBlank(dataIndex)) {

                    MonthCheckKCase caseObj = (MonthCheckKCase) dataList.get(Integer.parseInt(dataIndex));

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

                        apNoMsg.append(caseObj.getApNo() + "於核定年月" + DateUtility.changeWestYearMonthType(issuYm) + "已核付，");

                    }
                    else {

                        // 所勾選要編審的案件，於核定年月均無核付，則逐筆呼叫單筆月核定編審程式，逐筆編審
                        // 呼叫即時編審WebService
                        // String endpoint = (ResourceBundle.getBundle("webServiceUrl")).getString("monthWebServicesUrl");
                        String endpoint = PropertyHelper.getProperty("monthWebServicesUrl");
                        // 直接引用遠程的wsdl文件
                        // 以下都是套路
                        Service service = new Service();

                        Call call = (Call) service.createCall();

                        call.setTargetEndpointAddress(endpoint);

                        call.setOperationName("doCheckMark");// WSDL里面描述的接口名稱

                        call.addParameter("in0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); // apNo

                        call.addParameter("in1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); // AdjIssuYmMk

                        call.addParameter("in2", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN); // TOKEN

                        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 設置返回類型

                        String returnCode = (String) call.invoke(new Object[] { caseObj.getApNo(), caseObj.getAdjIssuYmMk(), SecureToken.getInstance().getToken() });
                        log.debug("執行 線上月試核作業 - 老年年金線上月試核作業 - 呼叫即時編審結果... " + returnCode);
                        String procMsg = returnCode;// 編審訊息

                        // 更新 勞保年金線上作業檔
                        bjService.updateBaonlinejobDataForMonthCheckK(caseObj, issuYm, procMsg, userData);
                    }

                }
            }

            // 更新頁面資料
            List<MonthCheckKCase> dataListNew = new ArrayList<MonthCheckKCase>();

            for (int i = 0; i < apNoList.size(); i++) {
                List<MonthCheckKCase> caseData = bjService.selectMonthCheckKApprovedDataListBy(apNoList.get(i), DateUtility.changeChineseYearMonthType(iform.getIssuYm()));

                if (caseData.size() > 0) {
                    dataListNew.add(caseData.get(0));
                }
            }
            // 更新頁面資料
            // List<MonthCheckKCase> dataListNew = bjService.selectMonthCheckKQueryDataListBy(payCode, DateUtility.changeChineseYearMonthType(iform.getIssuYm()));

            // if(dataListNew.size() < 1){
            //
            // saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
            // log.debug("批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 清單頁面  MonthCheckKAction.doConfirmList() 完成 ... ");
            // return mapping.findForward(FORWARD_QUERY_LIST_FAIL);
            //
            // }else{

            MonthQueryCase caseData = new MonthQueryCase();
            caseData.setPagePayKind(payCode);

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setMonthQueryCaseForTitle(caseData, request);
            CaseSessionHelper.setMonthCheckKCaseList(dataListNew, request);

            if (StringUtils.isNotBlank(apNoMsg.toString())) {
                saveMessages(session, CustomMessageHelper.getMonthApprovedFailMessage(apNoMsg.toString()));
            }
            else {
                saveMessages(session, CustomMessageHelper.getMonthCheckSuccessMessage());
            }
            log.debug("批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 清單頁面  MonthCheckKAction.doConfirmList() 完成 ... ");
            return mapping.findForward(FORWARD_QUERY_LIST_SUCCESS);
            // }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthCheckKAction.doConfirmList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 清單頁面 (baba0m092a.jsp) 按下 刪除
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 線上月核定作業 - 失能年金線上月核定作業 - 清單頁面  MonthCheckKAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        MonthCheckKForm iform = (MonthCheckKForm) form;

        try {

            // 取得清單資料
            List<MonthCheckKCase> dataList = CaseSessionHelper.getMonthCheckKCaseList(request);

            String[] tmpIndexList = iform.getApNoOfConfirm().split(",");

            for (int i = 0; i < tmpIndexList.length; i++) {
                String dataIndex = tmpIndexList[i];

                if (StringUtils.isNotBlank(dataIndex)) {

                    MonthCheckKCase caseObj = (MonthCheckKCase) dataList.get(Integer.parseInt(dataIndex));

                    // 依照畫面上所勾選要編審的案件於「勞保年金線上作業檔_BAONLINEJOB」中上D註記。
                    bjService.updateBaonlinejobDataForMonthCheckKDelete(caseObj, userData);

                }
            }

            // 更新頁面資料
            List<MonthCheckKCase> dataListNew = bjService.selectMonthCheckKQueryDataListBy(payCode, DateUtility.changeChineseYearMonthType(iform.getIssuYm()));

            MonthQueryCase caseData = new MonthQueryCase();
            caseData.setPagePayKind(payCode);

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setMonthQueryCaseForTitle(caseData, request);
            CaseSessionHelper.setMonthCheckKCaseList(dataListNew, request);

            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());
            log.debug("批次處理 - 線上月核定作業 - 失能年金線上月核定作業 - 清單頁面  MonthCheckKAction.doDelete() 完成 ... ");
            return mapping.findForward(FORWARD_QUERY_LIST_SUCCESS);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthCheckKAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 資料頁面 (baba0m091q.jsp,baba0m092a.jsp) 按下 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 返回 MonthCheckKAction.doBack() 開始 ... ");

        MonthCheckKForm iform = (MonthCheckKForm) form;

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        iform.setApNoOfConfirm("");
        iform.setBfMonthOfConfirm("");
        iform.setApNo1("");
        iform.setApNo2("");
        iform.setApNo3("");
        iform.setApNo4("");
        iform.setApNoA1("");
        iform.setApNoA2("");
        iform.setApNoA3("");
        iform.setApNoA4("");
        iform.setApNoB1("");
        iform.setApNoB2("");
        iform.setApNoB3("");
        iform.setApNoB4("");
        iform.setApNoC1("");
        iform.setApNoC2("");
        iform.setApNoC3("");
        iform.setApNoC4("");
        iform.setApNoD1("");
        iform.setApNoD2("");
        iform.setApNoD3("");
        iform.setApNoD4("");
        iform.setApNoE1("");
        iform.setApNoE2("");
        iform.setApNoE3("");
        iform.setApNoE4("");
        iform.setApNoF1("");
        iform.setApNoF2("");
        iform.setApNoF3("");
        iform.setApNoF4("");
        iform.setApNoG1("");
        iform.setApNoG2("");
        iform.setApNoG3("");
        iform.setApNoG4("");
        iform.setApNoH1("");
        iform.setApNoH2("");
        iform.setApNoH3("");
        iform.setApNoH4("");
        iform.setApNoI1("");
        iform.setApNoI2("");
        iform.setApNoI3("");
        iform.setApNoI4("");
        CaseSessionHelper.removeMonthQueryCaseForTitle(request);
        CaseSessionHelper.removeMonthCheckKCaseList(request);

        log.debug("執行批次處理 - 線上月試核作業 - 失能年金線上月試核作業 - 返回 MonthCheckKAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }

}
