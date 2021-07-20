package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.cases.BatchPaymentReceiveDataCase;
import tw.gov.bli.ba.rpt.forms.BatchPaymentReceiveForm;
import tw.gov.bli.ba.rpt.report.BatchPaymentReceiveReport;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.CashReceiveDataCase;
import tw.gov.bli.ba.update.cases.OldAgePaymentReceiveDataCase;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.pf.model.service.Pfxx0w040Service;
import tw.gov.bli.pf.model.vo.PfpcckyVO;
import tw.gov.bli.pf.model.vo.PfpcckyuserrecVO;

/**
 * 列印作業 - 應收收回相關報表 - 整批收回核定清單
 * 
 * @author Kiyomi
 */
public class BatchPaymentReceiveAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(BatchPaymentReceiveAction.class);
    private UpdateService updateService;
    private RptService rptService;
    private Pfxx0w040Service pfxx0w040Service;

    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 - 查詢頁面 (balp0d690l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 應收收回相關報表 - 整批收回核定清單 - 查詢頁面 BatchPaymentReceiveAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        BatchPaymentReceiveForm queryForm = (BatchPaymentReceiveForm) form;

        try {
            // 給付別
            String payCode = queryForm.getPayCode();
            // 核收日期（轉西元）
            String chkDate = DateUtility.changeDateType(queryForm.getChkDate());

            // 列印
            if (queryForm.getReportType().equals("1")) {
                // 查詢 收回狀況
                // OldAgePaymentReceiveDataCase caseData = updateService.selectCashReceiveDataBy(apno,queryForm.getApNo1());
                // OldAgePaymentReceiveDataCase caseData = updateService.selectCashReceiveDataBy(payCode, chkDate);

                queryForm.setChkDateStr(queryForm.getChkDate());
                queryForm.setPayCodeStr(payCode);

                // 查詢退現資料
                List<BatchPaymentReceiveDataCase> cashReceiveDataCaseList = rptService.selectCashReceiveDataListBy(payCode, chkDate);

                if (cashReceiveDataCaseList == null) {
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }

                // 查詢 應收資料
                // List<AccountsReceivableDataCase> accountsReceivableDataCaseList = updateService.selectAccountsReceivableDataListBy(apno,queryForm.getSeqNo());

                // 測試資料--------------------------------------------------
                // OldAgePaymentReceiveDataCase testCaseData = new OldAgePaymentReceiveDataCase();
                // testCaseData.setApNo("L20000000001"); // 受理編號
                // testCaseData.setSeqNo("0000"); // 受款人序
                // testCaseData.setEvtName("測試人"); // 事故者姓名
                // testCaseData.setEvtIdnNo("A123456789"); // 事故者身分證號
                // testCaseData.setEvtBrDate("19800205"); // 事故者出生日期
                // testCaseData.setEvtJobDate("19850205") ;// 事故日期
                //
                // List<AccountsReceivableDataCase> testAccountsCaseDataList = new ArrayList<AccountsReceivableDataCase>();
                // for(int i = 0 ; i<5 ; i++){
                // AccountsReceivableDataCase accountsCaseData = new AccountsReceivableDataCase();
                // accountsCaseData.setApNo("L20000000001");
                // accountsCaseData.setIssuYm("0980708");
                // accountsCaseData.setPayYm("0980101");
                // accountsCaseData.setRecAmt(new BigDecimal(5000));
                // accountsCaseData.setRecRem(new BigDecimal(10000));
                // accountsCaseData.setSeqNo("0000");
                // accountsCaseData.setSts("2");
                // accountsCaseData.setUnacpDate("19900101");
                // testAccountsCaseDataList.add(accountsCaseData);
                // }
                //
                // List<CashReceiveDataCase> testCashReceiveDataCaseList = new ArrayList<CashReceiveDataCase>();
                // for(int i = 0 ; i<5 ; i++){
                // CashReceiveDataCase cashReceiveDataCaseData = new CashReceiveDataCase();
                // cashReceiveDataCaseData.setInsKd("L");
                // cashReceiveDataCaseData.setBli_Account_Code("888");
                // cashReceiveDataCaseData.setBookedBook("入帳方式");
                // cashReceiveDataCaseData.setBkAccountDt("19900101");
                // cashReceiveDataCaseData.setBatchNo("234567");
                // cashReceiveDataCaseData.setSerialNo("0000");
                // cashReceiveDataCaseData.setCash_Amt(new BigDecimal(10000));
                // cashReceiveDataCaseData.setDivSeq("19900101");
                // cashReceiveDataCaseData.setIndex_No("19900101");
                // cashReceiveDataCaseData.setMoveToCode("19900101");
                // cashReceiveDataCaseData.setTempHandleNo("19900101");
                // testCashReceiveDataCaseList.add(cashReceiveDataCaseData);
                // }
                //
                // 測試資料--------------------------------------------------

                FormSessionHelper.setBatchPaymentReceiveForm(queryForm, request);

                // 測試用
                // CaseSessionHelper.setOldAgePaymentReceiveDataCase(testCaseData, request);
                // CaseSessionHelper.setAccountsReceivableDataCaseList(testAccountsCaseDataList, request);
                // CaseSessionHelper.setCashReceiveDataCaseList(testCashReceiveDataCaseList, request);

                // CaseSessionHelper.setOldAgePaymentReceiveDataCase(caseData, request);
                // CaseSessionHelper.setAccountsReceivableDataCaseList(accountsReceivableDataCaseList, request);
                CaseSessionHelper.setBatchPaymentReceiveDataCaseList(cashReceiveDataCaseList, request);

                log.debug("執行 列印作業 - 應收收回相關報表 - 整批收回核定清單 - 查詢頁面 BatchPaymentReceiveAction.doQuery() 完成 ... ");

                return mapping.findForward(ConstantKey.FORWARD_QUERY_LIST);

                // 補印
            }
            else {

                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("BatchPaymentReceiveAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 - 查詢頁面 (balp0d690l.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirmCashReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 應收收回相關報表 - 整批收回核定清單 - 查詢頁面 BatchPaymentReceiveAction.doConfirmCashReceive() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        BatchPaymentReceiveForm queryForm = (BatchPaymentReceiveForm) form;

        // 給付別
        String payCode1 = queryForm.getPayCode();
        // 核收日期（轉西元）
        String chkDate1 = DateUtility.changeDateType(queryForm.getChkDate());
        // 失敗清單
        List<BatchPaymentReceiveDataCase> failureList = new ArrayList<BatchPaymentReceiveDataCase>();
        List<BatchPaymentReceiveDataCase> tempFailureList = new ArrayList<BatchPaymentReceiveDataCase>();
        List<BatchPaymentReceiveDataCase> cashReceiveDataCaseList = new ArrayList<BatchPaymentReceiveDataCase>();
        List<OldAgePaymentReceiveDataCase> caseData10 = new ArrayList<OldAgePaymentReceiveDataCase>();

        try {

            // 列印
            if (queryForm.getReportType().equals("1")) {
                queryForm.setChkDateStr(queryForm.getChkDate());
                queryForm.setPayCodeStr(payCode1);

                // 查詢退現資料
                cashReceiveDataCaseList = rptService.selectCashReceiveDataMasterListBy(payCode1, chkDate1);
                caseData10 = updateService.selectBatchPaymentReceiveDataBy(payCode1, queryForm.getPayCode(), chkDate1);

                if (cashReceiveDataCaseList == null || cashReceiveDataCaseList.size() <= 0) {
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else {
                    // try {
                    for (int i = 0; i < cashReceiveDataCaseList.size(); i++) {
                        List<BatchPaymentReceiveDataCase> tempList = new ArrayList<BatchPaymentReceiveDataCase>();
                        tempList.add(cashReceiveDataCaseList.get(i));
                        BigDecimal bMoney = tempList.get(0).getAvailable_Money();

                        try {
                            tempFailureList = updateService.doBatchPaymentReceive(mapping, form, request, response, tempList, userData, queryForm);
                            if (tempFailureList.size() > 0) {
                                failureList.add(tempFailureList.get(0));
                            }
                        }
                        catch (Exception e) {
                            tempList.get(0).setChkDate(chkDate1);
                            tempList.get(0).setPayCode(payCode1);
                            tempList.get(0).setRemark(e.getMessage());
                            tempList.get(0).setAvailable_Money(bMoney);
                            tempList.get(0).setCrtUser(userData.getUserId());

                            failureList.add(tempList.get(0));

                        }
                    }
                    // }
                    // catch (Exception e) {

                    // }
                }

                if (failureList != null) {
                    if (failureList.size() != 0) {
                        // List<BatchPaymentReceiveDataCase> caseData3 = updateService.selectBaacpdtlDataBy(caseData1.getApNo(), caseData1.getSeqNo(), chkDate1);

                        for (BatchPaymentReceiveDataCase failureListData : failureList) {
                            // updateService.insertDataToBapayrptrecordForBatchPaymentReceive(userData, caseData1, accountsReceivableData, cashReceiveData);
                            updateService.insertDataToBafailurelistForBatchPaymentReceive(userData, null, failureListData, null, null);
                        }
                    }
                }

                // 產生報表檔資料 及 PFM & PFD 資料
                if ((cashReceiveDataCaseList != null || cashReceiveDataCaseList.size() > 0)) {
                    try {
                        updateService.createReportData(mapping, form, request, response, userData, queryForm, chkDate1, queryForm.getPayCode(), caseData10);
                    }
                    catch (Exception e) {

                        // 設定查詢失敗訊息
                        log.error("BatchPaymentReceiveAction.createReportData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
                        saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
                        return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                    }
                }

            } // if (queryForm.getReportType().equals("1")) {

            // 列印應收已收核定清單、應收已收清冊

            String payCode = queryForm.getPayCode();
            String chkDate = DateUtility.getNowChineseDate();
            String cPrnDate = DateUtility.getNowChineseDate();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("chkDate", DateUtility.changeDateType(chkDate));
            map.put("chkDate1", DateUtility.changeDateType(chkDate1));
            map.put("cPrnDate", cPrnDate);

            List<BatchPaymentReceiveDataCase> caseDataList = new ArrayList<BatchPaymentReceiveDataCase>();
            List<BatchPaymentReceiveDataCase> caseDataList2 = new ArrayList<BatchPaymentReceiveDataCase>();

            List<BatchPaymentReceiveDataCase> caseDataList1 = new ArrayList<BatchPaymentReceiveDataCase>();
            List<BatchPaymentReceiveDataCase> caseDataList3 = new ArrayList<BatchPaymentReceiveDataCase>();
            List<BatchPaymentReceiveDataCase> caseDataFooter = new ArrayList<BatchPaymentReceiveDataCase>();
            List<BatchPaymentReceiveDataCase> caseDataFooter3 = new ArrayList<BatchPaymentReceiveDataCase>();

            // 抓報表資料
            if (payCode.equals("L")) {
                // (1) BAPAYRPTRECORD
                caseDataList = rptService.getBatchPaymentReceiveDataBy("18", payCode, chkDate1, DateUtility.changeDateType(cPrnDate), "", "");
                caseDataList2 = null;

                // (2) BAPAYRPTSUM
                caseDataList1 = rptService.selectBatchPaymentReceiveSumDataBy("18", payCode, chkDate1, DateUtility.changeDateType(cPrnDate), "", "");
                caseDataList3 = null;

                // (3) BAPAYRPTACCOUNT
                caseDataFooter = rptService.selectBatchPaymentReceiveAccountDataBy("18", payCode, chkDate1, DateUtility.changeDateType(cPrnDate), "", "");
                caseDataFooter3 = null;

            }
            else {
                // (1) BAPAYRPTRECORD
                // 【NACHGMK is null】
                caseDataList = rptService.getBatchPaymentReceiveDataBy("18", payCode, chkDate1, DateUtility.changeDateType(cPrnDate), "IS NULL", "=");

                // 【NACHGMK is not null】
                caseDataList2 = rptService.getBatchPaymentReceiveDataBy("18", payCode, chkDate1, DateUtility.changeDateType(cPrnDate), "IS NOT NULL", "<>");

                // (2) BAPAYRPTSUM & BAPAYRPTACCOUNT
                // 【NACHGMK is null】
                caseDataList1 = rptService.selectBatchPaymentReceiveSumDataBy("18", payCode, chkDate1, DateUtility.changeDateType(cPrnDate), "IS NULL", "=");
                caseDataFooter = rptService.selectBatchPaymentReceiveAccountDataBy("18", payCode, chkDate1, DateUtility.changeDateType(cPrnDate), "IS NULL", "=");

                // 【NACHGMK is not null】
                caseDataList3 = rptService.selectBatchPaymentReceiveSumDataBy("18", payCode, chkDate1, DateUtility.changeDateType(cPrnDate), "IS NOT NULL", "<>");
                caseDataFooter3 = rptService.selectBatchPaymentReceiveAccountDataBy("18", payCode, chkDate1, DateUtility.changeDateType(cPrnDate), "IS NOT NULL", "<>");

            }

            // 補印 - 抓失敗清單資料
            if (queryForm.getReportType().equals("2")) {
                failureList = rptService.getFailureListDataBy(payCode, chkDate1);
            }

            // 報表列印資料
            List<BatchPaymentReceiveDataCase> reportData = new ArrayList<BatchPaymentReceiveDataCase>();

            // if (caseDataList.size() > 0 || caseDataList2.size() > 0) {

            // 【NACHGMK is null】
            if (caseDataList != null) {
                for (BatchPaymentReceiveDataCase caseData : caseDataList) {
                    if (StringUtils.isBlank(caseData.getNaChgMk())) {
                        caseData.setIsNaChgMk("N");
                        reportData.add(caseData);
                    }
                }
            }

            // 【NACHGMK is not null】
            if (caseDataList2 != null) {
                for (BatchPaymentReceiveDataCase caseData : caseDataList2) {
                    if (StringUtils.isNotBlank(caseData.getNaChgMk())) {
                        caseData.setIsNaChgMk("Y");
                        reportData.add(caseData);
                    }
                }
            }
            // }

            if (((StringUtils.equals(payCode, "L") && (caseDataList == null || caseDataList.size() == 0 || caseDataList1 == null || caseDataList1.size() == 0 || caseDataFooter == null || caseDataFooter.size() == 0))
                            || ((StringUtils.equals(payCode, "K") || StringUtils.equals(payCode, "S")) && ((caseDataList == null || caseDataList.size() == 0) && (caseDataList2 == null || caseDataList2.size() == 0))))
                            && (failureList == null || failureList.size() == 0)) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                queryForm.reset(mapping, request);
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                BatchPaymentReceiveReport report = new BatchPaymentReceiveReport();
                baoOutput = report.printReport(userData, reportData, map, caseDataList1, caseDataFooter, caseDataList3, caseDataFooter3, failureList);

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(payCode) + "_BatchPaymentReceive_" + Encode.forJava(printDate) + ".pdf" + "\"");
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
                // queryForm.reset(mapping, request);
                return null;
            }

        }
        catch (Exception e) {

            // 設定查詢失敗訊息
            log.error("BatchPaymentReceiveAction.doConfirmCashReceive() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);

        }
    }

    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 (balp0d691c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        // 清除 Session
        // CaseSessionHelper.removeOldAgePaymentReceiveDataCase(request);
        // CaseSessionHelper.removeAccountsReceivableDataCaseList(request);
        // CaseSessionHelper.removeRemittanceReceiveDataCaseList(request);
        // CaseSessionHelper.removeCashReceiveDataCaseList(request);
        // 清除明細資料畫面
        FormSessionHelper.removeBatchPaymentReceiveForm(request);

        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void doPfxx0w040Service(UserBean userData, BatchPaymentReceiveDataCase cashReceiveData, OldAgePaymentReceiveDataCase caseData) {

        // call 年金元件 service
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("personid", userData.getEmpNo());
        map.put("deptid", userData.getDeptId());
        map.put("ip", userData.getLoginIP());
        PfpcckyVO pfpcckyvo = new PfpcckyVO();
        pfpcckyvo.setInskd(cashReceiveData.getInsKd());
        pfpcckyvo.setBliAccountCode(cashReceiveData.getBli_Account_Code());
        pfpcckyvo.setBookedbook(cashReceiveData.getBookedBook());
        pfpcckyvo.setBkaccountdt(cashReceiveData.getBkAccountDt());
        pfpcckyvo.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
        pfpcckyvo.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
        pfpcckyvo.setCashAmt(cashReceiveData.getCash_Amt().longValue());
        pfpcckyvo.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
        pfpcckyvo.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
        pfpcckyvo.setMovetocode(cashReceiveData.getMoveToCode());
        pfpcckyvo.setTemphandleno(cashReceiveData.getTempHandleNo());
        pfpcckyvo.setAffairreprno(cashReceiveData.getAffairrePrno());
        pfpcckyvo.setAffairredept(cashReceiveData.getAffairredept());
        pfpcckyvo.setAffairredate(cashReceiveData.getAffairredate());
        pfpcckyvo.setAvailableMoney(cashReceiveData.getAvailable_Money().longValue());// 可用餘額
        pfpcckyvo.setAffairrecount(cashReceiveData.getAffairreCount().longValue());
        map.put("pcckyvo", pfpcckyvo);

        list.add(map);
        List detailList = new ArrayList();
        map.put("pcckylist", detailList);
        PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();
        detailList.add(pcckyuserrec);
        pcckyuserrec.setInskd(cashReceiveData.getInsKd());
        pcckyuserrec.setBliAccountCode(cashReceiveData.getBli_Account_Code());
        pcckyuserrec.setBookedbook(cashReceiveData.getBookedBook());
        pcckyuserrec.setBkaccountdt(cashReceiveData.getBkAccountDt());
        pcckyuserrec.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
        pcckyuserrec.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
        pcckyuserrec.setCashAmt(cashReceiveData.getCash_Amt().longValue());
        pcckyuserrec.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
        pcckyuserrec.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
        pcckyuserrec.setExpid(1l);
        pcckyuserrec.setRetrievemoney(cashReceiveData.getRetrieveMoney());// 收回金額
        pcckyuserrec.setApno(caseData.getApNo());
        pcckyuserrec.setRecm(cashReceiveData.getRecm());
        pcckyuserrec.setPersonno(userData.getEmpNo());
        pcckyuserrec.setPnodept(userData.getDeptId());
        pcckyuserrec.setPnodate(userData.getLoginTime().substring(0, 8));
        pcckyuserrec.setCamfield(cashReceiveData.getCamField());

        log.info(pfxx0w040Service.writeSavePfxx0w040n(list));
        String deBugStop = "";
    }

    public void doPfxx0w040UpdateService(UserBean userData, CashReceiveDataCase cashReceiveData, OldAgePaymentReceiveDataCase caseData) {

        // call 年金元件 service
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("personid", userData.getEmpNo());
        map.put("deptid", userData.getDeptId());
        map.put("ip", userData.getLoginIP());
        PfpcckyVO pfpcckyvo = new PfpcckyVO();
        pfpcckyvo.setInskd(cashReceiveData.getInsKd());
        pfpcckyvo.setBliAccountCode(cashReceiveData.getBli_Account_Code());
        pfpcckyvo.setBookedbook(cashReceiveData.getBookedBook());
        pfpcckyvo.setBkaccountdt(cashReceiveData.getBkAccountDt());
        pfpcckyvo.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
        pfpcckyvo.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
        pfpcckyvo.setCashAmt(cashReceiveData.getCash_Amt().longValue());
        pfpcckyvo.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
        pfpcckyvo.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
        pfpcckyvo.setMovetocode(cashReceiveData.getMoveToCode());
        pfpcckyvo.setTemphandleno(cashReceiveData.getTempHandleNo());
        pfpcckyvo.setAffairreprno(cashReceiveData.getAffairrePrno());
        pfpcckyvo.setAffairredept(cashReceiveData.getAffairredept());
        pfpcckyvo.setAffairredate(cashReceiveData.getAffairredate());
        pfpcckyvo.setAvailableMoney(cashReceiveData.getAvailable_Money().longValue());// 可用餘額
        pfpcckyvo.setAffairrecount(cashReceiveData.getAffairreCount().longValue());
        map.put("pcckyvo", pfpcckyvo);

        list.add(map);
        List detailList = new ArrayList();
        map.put("pcckylist", detailList);
        PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();
        detailList.add(pcckyuserrec);
        pcckyuserrec.setInskd(cashReceiveData.getInsKd());
        pcckyuserrec.setBliAccountCode(cashReceiveData.getBli_Account_Code());
        pcckyuserrec.setBookedbook(cashReceiveData.getBookedBook());
        pcckyuserrec.setBkaccountdt(cashReceiveData.getBkAccountDt());
        pcckyuserrec.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
        pcckyuserrec.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
        pcckyuserrec.setCashAmt(cashReceiveData.getCash_Amt().longValue());
        pcckyuserrec.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
        pcckyuserrec.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
        pcckyuserrec.setExpid(1l);
        pcckyuserrec.setRetrievemoney(cashReceiveData.getRetrieveMoney());// 收回金額
        pcckyuserrec.setApno(caseData.getApNo());
        pcckyuserrec.setRecm(cashReceiveData.getRecm());
        pcckyuserrec.setPersonno(userData.getEmpNo());
        pcckyuserrec.setPnodept(userData.getDeptId());
        pcckyuserrec.setPnodate(userData.getLoginTime().substring(0, 8));
        pcckyuserrec.setCamfield(cashReceiveData.getCamField());

        log.info(pfxx0w040Service.writeUpdatePfxx0w040n(list));
        String deBugStop = "";
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }

    public void setPfxx0w040Service(Pfxx0w040Service pfxx0w040Service) {
        this.pfxx0w040Service = pfxx0w040Service;
    }

}
