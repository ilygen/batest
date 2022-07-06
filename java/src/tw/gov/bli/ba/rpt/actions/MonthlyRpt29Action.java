package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt29Case;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt29Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt29ExcelReport;
import tw.gov.bli.ba.rpt.report.MonthlyRpt29Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.ReportUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 列印作業 - 月核定處理相關報表 - 補送在學證明通知函 - 查詢頁面 (balp0d3p0l.jsp)
 * 
 * @author Noctis
 */
public class MonthlyRpt29Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt29Action.class);

    private RptService rptService;

    String LIST_PAGE = "listPage";

    /**
     * 列印作業 - 月核定處理相關報表 - 補送在學證明通知函 PDF+EXCEL - 查詢頁面 (balp0d3p0l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 應收收回相關報表 - 退匯現應收已收註銷清冊 - 查詢頁面 MonthlyRpt28Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt29Form queryForm = (MonthlyRpt29Form) form;

        try {
            String rptKind = queryForm.getRptKind();
            String payCode = queryForm.getPayCode();
            String studeDateFormChk = queryForm.getStudeDate();
            studeDateFormChk = (studeDateFormChk == null) ? "" : queryForm.getStudeDate();
            // 以下為sql傳入值 如studeDateFormChk=06 會變動
            String studeChkMonth = "09";
            String studeDate = "08";

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("rptKind", rptKind);
            map.put("payCode", payCode);

            String fileName = "";
            if (StringUtils.equals(rptKind, "1")) {
                if (payCode.equals("K")) {
                    fileName = "K_Month_MonthlyRpt29_";
                }
                else {
                    fileName = "S_Month_MonthlyRpt29_";
                }
            }
            else if (StringUtils.equals(rptKind, "2")) {
                if (payCode.equals("K")) {
                    fileName = "K_Year_MonthlyRpt29_" + studeDateFormChk + "_";
                }
                else {
                    fileName = "S_Year_MonthlyRpt29_" + studeDateFormChk + "_";
                }
            }

            // 抓取PDF 報表資料
            List<MonthlyRpt29Case> caseDataList = new ArrayList<MonthlyRpt29Case>();
            // 畫面上在學結束年月選6月，要再加印這一部份
            List<MonthlyRpt29Case> caseData06List = new ArrayList<MonthlyRpt29Case>();

            if (studeDateFormChk.equals("06")) {
                studeChkMonth = "07";
                studeDate = "06";
                caseData06List = rptService.getMonthlyRpt29DataForMonth06By(rptKind, payCode, studeChkMonth, studeDate, studeDateFormChk);

                // 06 B01資料
                if (caseData06List != null) {

                    studeChkMonth = "09";
                    studeDate = "08";

                    HashSet<String> apNoList = new HashSet<String>();

                    // 先取出不重複apNo
                    for (MonthlyRpt29Case caseData : caseData06List) {

                        apNoList.add(caseData.getWordNo());
                    }

                    // 抓取 各apNo B01 資料
                    for (String apNo : apNoList) {
                        List<MonthlyRpt29Case> caseData06B01List = new ArrayList<MonthlyRpt29Case>();
                        caseData06B01List = rptService.getMonthlyRpt29For06B01DataBy(rptKind, payCode, studeChkMonth, studeDate, apNo, studeDateFormChk);

                        if (caseData06B01List != null) {
                            caseDataList.addAll(caseData06B01List);
                        }
                    }

                }

            }
            else {
                // 08 09B01資料
                caseDataList = rptService.getMonthlyRpt29DataBy(rptKind, payCode, studeChkMonth, studeDate, studeDateFormChk);
            }

            // 抓取EXCEL 報表資料
            List<MonthlyRpt29Case> dataList = new ArrayList<MonthlyRpt29Case>();
            List<MonthlyRpt29Case> dataList06 = new ArrayList<MonthlyRpt29Case>();

            if (studeDateFormChk.equals("06")) {
                studeChkMonth = "07";
                studeDate = "06";
                dataList06 = rptService.getMonthlyRpt29ExcelDataBy(rptKind, payCode, studeChkMonth, studeDate, studeDateFormChk);

                // 06 B01資料
                if (dataList06 != null) {

                    studeChkMonth = "09";
                    studeDate = "08";

                    // 先取出不重複apNo
                    HashSet<String> apNoList = new HashSet<String>();
                    for (MonthlyRpt29Case caseData : dataList06) {

                        apNoList.add(caseData.getApNo());
                    }

                    // 抓取 各apNo B01 資料
                    for (String apNo : apNoList) {

                        List<MonthlyRpt29Case> caseData06B01List = new ArrayList<MonthlyRpt29Case>();

                        caseData06B01List = rptService.getMonthlyRpt29Excel06DataBy(rptKind, payCode, studeChkMonth, studeDate, apNo, studeDateFormChk);

                        if (caseData06B01List != null) {
                            dataList.addAll(caseData06B01List);
                        }
                    }

                    dataList.addAll(dataList06);
                }

            }
            else {

                dataList = rptService.getMonthlyRpt29ExcelDataBy(rptKind, payCode, studeChkMonth, studeDate, studeDateFormChk);
            }

            if ((caseDataList == null || caseDataList.size() == 0) && (caseData06List == null || caseData06List.size() == 0) && (dataList == null || dataList.size() == 0)) {

                // 抓取已產過的報表
                File[] fileList = ReportUtility.listFileAll(fileName);
                if (fileList != null && fileList.length > 0) {

                    List<String> files = new ArrayList<String>();
                    for (File existFile : fileList) {
                        files.add(existFile.getName());
                    }
                    files = files.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                    session.setAttribute(ConstantKey.MONTHLY_RPT_29_FILE_LIST, files);
                    return mapping.findForward(LIST_PAGE);

                }
                else {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    queryForm.reset(mapping, request);
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }

            }
            else {

                // 產製PDF報表
                if ((caseDataList != null || caseDataList.size() > 0) || (caseData06List != null || caseData06List.size() > 0)) {

                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                    MonthlyRpt29Report report = new MonthlyRpt29Report();
                    baoOutput = report.doReport(userData, caseDataList, caseData06List, map);

                    String printDate = DateUtility.getNowChineseDateTime(false);

                    // 寫入local檔案
                    File file = new File(PropertyHelper.getProperty("rpt.path") + Encode.forJava(fileName) + Encode.forJava(printDate) + ".pdf");
                    OutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(file);
                        baoOutput.writeTo(outputStream);
                    }
                    finally {
                        ExceptionUtil.safeColse(outputStream);
                        ExceptionUtil.safeColse(baoOutput);
                    }

                }

                // 產生EXCEL報表
                if (dataList != null || dataList.size() > 0) {

                    String excelTyp = "";
                    if (StringUtils.equals("1", rptKind)) {
                        excelTyp = "MonthlyRpt29ExcelM.xls";
                    }
                    else {
                        excelTyp = "MonthlyRpt29ExcelY.xls";
                    }

                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                    MonthlyRpt29ExcelReport report = new MonthlyRpt29ExcelReport(excelTyp);
                    baoOutput = report.doReport(dataList, map);

                    String printDate = DateUtility.getNowChineseDateTime(false);

                    // 寫入local檔案
                    File file = new File(PropertyHelper.getProperty("rpt.path") + Encode.forJava(fileName) + Encode.forJava(printDate) + ".xls");
                    OutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(file);
                        baoOutput.writeTo(outputStream);
                    }
                    finally {
                        ExceptionUtil.safeColse(outputStream);
                        ExceptionUtil.safeColse(baoOutput);
                    }
                }

                // 抓取以產過的報表
                File[] fileList = ReportUtility.listFileAll(fileName);
                if (fileList != null && fileList.length > 0) {

                    List<String> files = new ArrayList<String>();
                    for (File existFile : fileList) {
                        files.add(existFile.getName());
                    }
                    files = files.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                    session.setAttribute(ConstantKey.MONTHLY_RPT_29_FILE_LIST, files);
                    return mapping.findForward(LIST_PAGE);

                }
                else {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    queryForm.reset(mapping, request);
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 補送在學證明通知函 PDF 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 補送在學證明通知函 PDF - 查詢頁面 (balp0d3p0l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportPDF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 應收收回相關報表 - 退匯現應收已收註銷清冊 - 查詢頁面 MonthlyRpt28Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt29Form queryForm = (MonthlyRpt29Form) form;

        try {
            String rptKind = queryForm.getRptKind();
            String payCode = queryForm.getPayCode();
            String studeDateFormChk = queryForm.getStudeDate();
            studeDateFormChk = (studeDateFormChk == null) ? "" : queryForm.getStudeDate();
            // 以下為sql傳入值 如studeDateFormChk=06 會變動
            String studeChkMonth = "09";
            String studeDate = "08";

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("rptKind", rptKind);
            map.put("payCode", payCode);

            String fileName = "";
            if (StringUtils.equals(rptKind, "1")) {
                if (payCode.equals("K")) {
                    fileName = "K_Month_MonthlyRpt29_";
                }
                else {
                    fileName = "S_Month_MonthlyRpt29_";
                }
            }
            else if (StringUtils.equals(rptKind, "2")) {
                if (payCode.equals("K")) {
                    fileName = "K_Year_MonthlyRpt29_" + studeDateFormChk + "_";
                }
                else {
                    fileName = "S_Year_MonthlyRpt29_" + studeDateFormChk + "_";
                }
            }

            List<MonthlyRpt29Case> caseDataList = new ArrayList<MonthlyRpt29Case>();
            // 畫面上在學結束年月選6月，要再加印這一部份
            List<MonthlyRpt29Case> caseData06List = new ArrayList<MonthlyRpt29Case>();

            if (studeDateFormChk.equals("06")) {
                studeChkMonth = "07";
                studeDate = "06";
                caseData06List = rptService.getMonthlyRpt29DataForMonth06By(rptKind, payCode, studeChkMonth, studeDate, studeDateFormChk);

                // 06 B01資料
                if (caseData06List != null) {

                    studeChkMonth = "09";
                    studeDate = "08";

                    HashSet<String> apNoList = new HashSet<String>();

                    // 先取出不重複apNo
                    for (MonthlyRpt29Case caseData : caseData06List) {

                        apNoList.add(caseData.getWordNo());
                    }

                    // 抓取 各apNo B01 資料
                    for (String apNo : apNoList) {
                        List<MonthlyRpt29Case> caseData06B01List = new ArrayList<MonthlyRpt29Case>();
                        caseData06B01List = rptService.getMonthlyRpt29For06B01DataBy(rptKind, payCode, studeChkMonth, studeDate, apNo, studeDateFormChk);

                        if (caseData06B01List != null) {
                            caseDataList.addAll(caseData06B01List);
                        }
                    }

                }

            }
            else {
                // 08 09B01資料
                caseDataList = rptService.getMonthlyRpt29DataBy(rptKind, payCode, studeChkMonth, studeDate, studeDateFormChk);
            }

            if ((caseDataList == null || caseDataList.size() == 0) && (caseData06List == null || caseData06List.size() == 0)) {

                // 抓取以產過的報表
                File[] fileList = ReportUtility.listPDFFile(fileName);
                if (fileList != null && fileList.length > 0) {

                    List<String> files = new ArrayList<String>();
                    for (File existFile : fileList) {
                        files.add(existFile.getName());
                    }
                    session.setAttribute(ConstantKey.MONTHLY_RPT_29_FILE_LIST, files);
                    return mapping.findForward(LIST_PAGE);

                }
                else {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    queryForm.reset(mapping, request);
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }

            }
            else {

                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                MonthlyRpt29Report report = new MonthlyRpt29Report();
                baoOutput = report.doReport(userData, caseDataList, caseData06List, map);

                String printDate = DateUtility.getNowChineseDateTime(false);

                // 寫入local檔案
                File file = new File(PropertyHelper.getProperty("rpt.path") + Encode.forJava(fileName) + Encode.forJava(printDate) + ".pdf");
                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    baoOutput.writeTo(outputStream);
                }
                finally {
                    ExceptionUtil.safeColse(outputStream);
                    ExceptionUtil.safeColse(baoOutput);
                }

                // 抓取以產過的報表
                File[] fileList = ReportUtility.listPDFFile(fileName);
                if (fileList != null && fileList.length > 0) {

                    List<String> files = new ArrayList<String>();
                    for (File existFile : fileList) {
                        files.add(existFile.getName());
                    }
                    session.setAttribute(ConstantKey.MONTHLY_RPT_29_FILE_LIST, files);
                    return mapping.findForward(LIST_PAGE);

                }
                else {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    queryForm.reset(mapping, request);
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }

                // 20150723 改成寫入檔案
                // // 設定回傳回 Client 端之檔案大小, 若不設定,
                // // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                // response.setHeader("Content-disposition", "attachment; filename=\""+ title + printDate + ".pdf" + "\"");
                // response.setHeader("Content-Encoding", "pdf");
                // response.setContentType("application/pdf"); // 設定MIME
                // response.setHeader("Expires", "0");
                // response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                // response.setHeader("Pragma", "public");
                // response.setContentLength(baoOutput.size());
                //
                // // 傳回 Client 端
                // ServletOutputStream sout = null;
                // try {
                // sout = response.getOutputStream();
                // baoOutput.writeTo(sout);
                // sout.flush();
                // }
                // catch (Exception e) {
                // }
                // finally {
                // if (baoOutput != null)
                // baoOutput.close();
                // if (sout != null)
                // sout.close();
                // }
                // queryForm.reset(mapping, request);
                // return null;
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 補送在學證明通知函 PDF 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 勞保年金補送在學證明案件明細資料 Excel - 查詢頁面 (balp0d3p0l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("列印作業 - 月核定處理相關報表 - 勞保年金補送在學證明案件明細資料 Excel - 產製 MonthlyRpt29Action.doReportExcel() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt29Form queryForm = (MonthlyRpt29Form) form;

        try {

            String rptKind = queryForm.getRptKind();
            String payCode = queryForm.getPayCode();
            String studeDateFormChk = queryForm.getStudeDate();
            studeDateFormChk = (studeDateFormChk == null) ? "" : queryForm.getStudeDate();
            // 以下為sql傳入值 如studeDateFormChk=06 會變動
            String studeChkMonth = "09";
            String studeDate = "08";

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);
            map.put("rptKind", rptKind);

            String fileName = "";
            if (StringUtils.equals(rptKind, "1")) {
                if (payCode.equals("K")) {
                    fileName = "K_Month_MonthlyRpt29_";
                }
                else {
                    fileName = "S_Month_MonthlyRpt29_";
                }
            }
            else if (StringUtils.equals(rptKind, "2")) {
                if (payCode.equals("K")) {
                    fileName = "K_Year_MonthlyRpt29_" + studeDateFormChk + "_";
                }
                else {
                    fileName = "S_Year_MonthlyRpt29_" + studeDateFormChk + "_";
                }
            }

            // 取得資料
            List<MonthlyRpt29Case> dataList = new ArrayList<MonthlyRpt29Case>();
            List<MonthlyRpt29Case> dataList06 = new ArrayList<MonthlyRpt29Case>();

            if (studeDateFormChk.equals("06")) {
                studeChkMonth = "07";
                studeDate = "06";
                dataList06 = rptService.getMonthlyRpt29ExcelDataBy(rptKind, payCode, studeChkMonth, studeDate, studeDateFormChk);

                // 06 B01資料
                if (dataList06 != null) {

                    studeChkMonth = "09";
                    studeDate = "08";

                    // 先取出不重複apNo
                    HashSet<String> apNoList = new HashSet<String>();
                    for (MonthlyRpt29Case caseData : dataList06) {

                        apNoList.add(caseData.getApNo());
                    }

                    // 抓取 各apNo B01 資料
                    for (String apNo : apNoList) {

                        List<MonthlyRpt29Case> caseData06B01List = new ArrayList<MonthlyRpt29Case>();

                        caseData06B01List = rptService.getMonthlyRpt29Excel06DataBy(rptKind, payCode, studeChkMonth, studeDate, apNo, studeDateFormChk);

                        if (caseData06B01List != null) {
                            dataList.addAll(caseData06B01List);
                        }
                    }

                    dataList.addAll(dataList06);
                }

            }
            else {

                dataList = rptService.getMonthlyRpt29ExcelDataBy(rptKind, payCode, studeChkMonth, studeDate, studeDateFormChk);
            }

            if (dataList == null) {

                // 抓取以產過的報表
                File[] fileList = ReportUtility.listEXCELFile(fileName);
                if (fileList != null && fileList.length > 0) {

                    List<String> files = new ArrayList<String>();
                    for (File existFile : fileList) {
                        files.add(existFile.getName());
                    }
                    session.setAttribute(ConstantKey.MONTHLY_RPT_29_FILE_LIST, files);
                    return mapping.findForward(LIST_PAGE);

                }
                else {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    queryForm.reset(mapping, request);
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }

            }
            else {
                // 產生EXCEL報表

                String excelTyp = "";
                if (StringUtils.equals("1", rptKind)) {
                    excelTyp = "MonthlyRpt29ExcelM.xls";
                }
                else {
                    excelTyp = "MonthlyRpt29ExcelY.xls";
                }

                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                MonthlyRpt29ExcelReport report = new MonthlyRpt29ExcelReport(excelTyp);
                baoOutput = report.doReport(dataList, map);

                String printDate = DateUtility.getNowChineseDateTime(false);

                // 寫入local檔案
                File file = new File(PropertyHelper.getProperty("rpt.path") + Encode.forJava(fileName) + Encode.forJava(printDate) + ".xls");
                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    baoOutput.writeTo(outputStream);
                }
                finally {
                    ExceptionUtil.safeColse(outputStream);
                    ExceptionUtil.safeColse(baoOutput);
                }

                // 抓取以產過的報表
                File[] fileList = ReportUtility.listEXCELFile(fileName);
                if (fileList != null && fileList.length > 0) {

                    List<String> files = new ArrayList<String>();
                    for (File existFile : fileList) {
                        files.add(existFile.getName());
                    }
                    session.setAttribute(ConstantKey.MONTHLY_RPT_29_FILE_LIST, files);
                    return mapping.findForward(LIST_PAGE);

                }
                else {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    queryForm.reset(mapping, request);
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }

                // // 設定回傳回 Client 端之檔案大小, 若不設定,
                // // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                // response.setHeader("Content-disposition", "attachment; filename=\"" + new String(title.getBytes("Big5"), "ISO8859_1" ) + printDate + ".xls" + "\"");
                // response.setContentType("application/vnd.ms-excel"); // 設定MIME
                // response.setHeader("Pargma", "no-cache");
                // response.setHeader("Cache-Control", "max-age=1");
                // response.setHeader("Expires", "0");
                // response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                // response.setHeader("Pragma", "public");
                // response.setContentLength(baoOutput.size());
                //
                // // 傳回 Client 端
                // ServletOutputStream sout = null;
                // try {
                // sout = response.getOutputStream();
                // baoOutput.writeTo(sout);
                // sout.flush();
                // log.debug("列印作業 - 月核定處理相關報表 - 勞保年金補送在學證明案件明細資料 Excel - 產製 MonthlyRpt29Action.doReportExcel() 完成 ... ");
                // }
                // catch (Exception e) {
                // }
                // finally {
                // if (baoOutput != null)
                // baoOutput.close();
                // if (sout != null)
                // sout.close();
                // }

                // return null;
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 勞保年金補送在學證明案件明細資料 Excel 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.info("執行 列印作業 - 其他類報表  - 補送在學證明通知函 - 下載檔案 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt29Form queryForm = (MonthlyRpt29Form) form;
        FileInputStream fs = null;
        try {
            String fileName = queryForm.getDownloadFileName();
            List<String> fileNames = (List<String>) session.getAttribute(ConstantKey.MONTHLY_RPT_29_FILE_LIST);
            boolean fileExist = false;
            for(String fn : fileNames) {
            	if(StringUtils.equals(fn, fileName)) {
            		fileExist = true;
            		break;
            	}
            }
            if (StringUtils.isNotBlank(fileName) && fileExist) {
                fs = new FileInputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(fileName));
                byte[] content = IOUtils.toByteArray(fs);

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(fileName) + "\"");
                response.setHeader("Content-Encoding", "pdf");
                response.setContentType("application/pdf"); // 設定MIME
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setContentLength(content.length);

                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                baoOutput.write(content);
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
            else {
                saveMessages(session, CustomMessageHelper.getDownloadErrorMessage());
                return mapping.findForward(LIST_PAGE);
            }

        }
        catch (Exception e) {
            log.error("下載 補送在學證明通知函 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getDownloadErrorMessage());
            return mapping.findForward(LIST_PAGE);
        }
        finally {
            ExceptionUtil.safeColse(fs);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
