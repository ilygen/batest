package tw.gov.bli.ba.bj.actions;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import tw.gov.bli.ba.Global;
import tw.gov.bli.ba.bj.cases.MediaOnlineDownloadCase;
import tw.gov.bli.ba.bj.cases.MediaOnlineProgressCase;
import tw.gov.bli.ba.bj.cases.MediaOnlineProgressDtlCase;
import tw.gov.bli.ba.bj.forms.OldMediaOnlineForm;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FtpDbHelper;
import tw.gov.bli.ba.bj.job.MediaOnlineJob;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.ScheduleHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 批次處理 - 老年年金線上批次產製媒體檔作業
 * 
 * @author ZeHua
 */
public class OldMediaOnlineAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OldMediaOnlineAction.class);
    private BjService bjService;
    private FtpDbHelper ftpDbClient;
    private static final String PAYCODE_OLD = "L";
    private static final String PROCTYPE_ONLINE_MEDIA = "1";
    // private static final String PAYCODE_OLD_STR = "老年";

    /**
     * 批次處理 - 老年年金線上批次產製媒體檔作業-產製媒體檔-排程作業(BABA0M170X)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doProduce(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        OldMediaOnlineForm queryForm = (OldMediaOnlineForm) form;
        try {
            BigDecimal qryCheckBatch = bjService.selectCheckCountForBjMonthBy(PAYCODE_OLD);

            if (qryCheckBatch.compareTo(BigDecimal.ZERO) == 0) {
                // 計算目前佇列中是否有相同核定年月、日期、給付別的資料
                int batchNum = bjService.doQueryBatchJobStatus(DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()), DateUtility.changeDateType(queryForm.getChkDate()), PAYCODE_OLD, PROCTYPE_ONLINE_MEDIA);
                // 目前無相同資料在佇列中
                if (batchNum <= 0) {
                    String jobId = ScheduleHelper.scheduleNow(MediaOnlineJob.class, null, userData);
                    bjService.doScheduleBatchJob(jobId, DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()), DateUtility.changeDateType(queryForm.getChkDate(), false), PAYCODE_OLD, userData.getEmpNo(), userData.getDeptId(),
                                    userData.getLoginIP(), DateUtility.getNowWestDateTime(true), PROCTYPE_ONLINE_MEDIA, ConstantKey.STATUS_WAITING, ConstantKey.PAYSEQNO_1);
                    // 設定相同核定年月與核定日期已記入排程佇列
                    saveMessages(session, DatabaseMessageHelper.getScheduleSuccssMessage());
                    log.debug("批次處理 - 老年年金線上批次產製媒體檔作業-產製媒體檔-排程作業 OldMediaOnlineAction.doProduce() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_SCHEDULE_SUCCESS);
                    // 目前已有相同資料在佇列中
                }
                else {
                    saveMessages(session, DatabaseMessageHelper.getDuplicateMessage());
                    log.debug("批次處理 - 老年年金線上批次產製媒體檔作業-產製媒體檔-排程作業 OldMediaOnlineAction.doProduce() 完成 ...(排程中已有相同資料) ");
                    return mapping.findForward(ConstantKey.FORWARD_SCHEDULE_SUCCESS);
                }
            }
            else {
                saveMessages(session, DatabaseMessageHelper.getBetweenChkdateMessage());
                log.debug("批次處理 - 失能年金線上批次產製媒體檔作業-產製媒體檔-排程作業 DisabledMediaOnlineAction.doProduce() 完成 ...(系統日期介於批次月試核及月核定期間，無法執行此排程動作) ");
                return mapping.findForward(ConstantKey.FORWARD_SCHEDULE_FAIL);
            }

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldMediaOnlineAction.doProduct() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SCHEDULE_FAIL);
        }
    }

    /**
     * 批次處理 - 老年年金線上批次產製媒體檔作業-下載媒體檔-(BABA0M171Q)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDownloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        OldMediaOnlineForm queryForm = (OldMediaOnlineForm) form;
        try {
            // 取得該下載清單
            List<MediaOnlineDownloadCase> caseData = bjService.getMedaiFileOnline(PAYCODE_OLD, queryForm.getIssuYm(), DateUtility.changeDateType(queryForm.getChkDate()), ConstantKey.PAYSEQNO_1);
            if (caseData == null) {
                // 查無資料
                saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
                log.debug("批次處理 - 老年年金線上批次產製媒體檔作業-下載媒體檔- OldMediaOnlineAction.doDownloadFile() 完成 ...(查無資料) ");
                return mapping.findForward(ConstantKey.FORWARD_SCHEDULE_SUCCESS);
            }
            else {
                CaseSessionHelper.setOldMediaOnlineCaseList(caseData, request);
                log.debug("批次處理 - 老年年金線上批次產製媒體檔作業-下載媒體檔- OldMediaOnlineAction.doDownload() 完成 ...");
                return mapping.findForward(ConstantKey.FOWARD_DOWN_FILE);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldMediaOnlineAction.doDownload() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SCHEDULE_FAIL);
        }

    }

    /**
     * 批次處理 - 老年年金線上批次產製媒體檔作業-媒體檔下載-點選"下載"-(BABA0M171Q)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDownload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        OldMediaOnlineForm queryForm = (OldMediaOnlineForm) form;
        try {
            log.info("開始老年年金媒體檔下載處理...");
            // 取得ftp檔案
            String outputFile = ftpDbClient.getRecordFileNames(queryForm.getMfileName());
            // 設定回傳回 Client 端之檔案大小
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            String contentType = getServlet().getServletContext().getMimeType(queryForm.getMfileName());
            response.setHeader("Content-disposition", "attachment; filename=" + Encode.forJava(queryForm.getMfileName()) + "\"");
            response.setHeader("Content-Encoding", "txt");
            response.setContentType(contentType); // 設定 MIME TYPE

            // 傳回 Client 端
            StringReader in = new StringReader(outputFile);
            ServletOutputStream out = response.getOutputStream();
            try {
                // 為解決 UNIX 下中文變亂碼的問題, 需強制指定輸出內容編碼
                IOUtils.copy(in, out, Global.IO_ENCODING);
            }
            finally {
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(in);
            }
            log.debug("執行 OldMediaOnlineAction.doDownload() 完成 ...");

            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldMediaOnlineAction.doDownload() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getReportFailMessage());
            return mapping.findForward(ConstantKey.FOWARD_DOWN_FILE);
        }

    }

    /**
     * 批次處理 - 老年年金線上批次產製媒體檔作業-進度查詢-(BABA0M172Q)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        OldMediaOnlineForm queryForm = (OldMediaOnlineForm) form;
        try {
            // 查詢目前進度
            List<MediaOnlineProgressCase> caseData = bjService.getProgressStep(PROCTYPE_ONLINE_MEDIA, DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()), DateUtility.changeDateType(queryForm.getChkDate()), PAYCODE_OLD,
                            ConstantKey.PAYSEQNO_1);
            if (caseData == null) {
                saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
                log.debug("批次處理 - 老年年金線上批次產製媒體檔作業-進度查詢 - OldMediaOnlineAction.doQueryProcess() 完成 ...(查無資料) ");
                return mapping.findForward(ConstantKey.FORWARD_SCHEDULE_SUCCESS);
            }
            else {
                CaseSessionHelper.setOldMediaOnlineProgressList(caseData, request);
                log.debug("批次處理 - 老年年金線上批次產製媒體檔作業-進度查詢 - OldMediaOnlineAction.doQueryProcess() 完成 ...");
                return mapping.findForward(ConstantKey.FOWARD_QRY_PROGRESS);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldMediaOnlineAction.doQueryProcess() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SCHEDULE_FAIL);
        }

    }

    /**
     * 批次處理 - 老年年金線上批次產製媒體檔作業-進度查詢明細-(BABA0M173Q)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQryProgressDtl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        OldMediaOnlineForm queryForm = (OldMediaOnlineForm) form;
        try {
            // 進度查詢明細
            List<MediaOnlineProgressDtlCase> caseData = bjService.getProgressStepDtl(queryForm.getBaJobId());
            if (caseData == null) {
                saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
                log.debug("批次處理 - 老年年金線上批次處理狀況明細查詢  - OldMediaOnlineAction.doQryProgressDtl() 完成 ...(查無資料) ");
                return mapping.findForward(ConstantKey.FOWARD_QRY_PROGRESS);
            }
            else {
                CaseSessionHelper.setOldMediaOnlineProgressDtlList(caseData, request);
                log.debug("批次處理 - 老年年金線上批次處理狀況明細查詢  - OldMediaOnlineAction.doQryProgressDtl() 完成 ...");
                return mapping.findForward(ConstantKey.FOWARD_QRY_PROGRESS_DTL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldMediaOnlineAction.doQryProgressDtl() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FOWARD_QRY_PROGRESS);
        }
    }

    /**
     * 批次處理 - 老年年金線上產製媒體檔作業 - 媒體檔下載 - 傳送作業 (BABA0M171Q)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @returns
     */
    public ActionForward doUploadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        OldMediaOnlineForm queryForm = (OldMediaOnlineForm) form;
        MediaUploadFile mediaUploadFile = new MediaUploadFile();

        try {
            boolean result = mediaUploadFile.doUploadFile(queryForm.getMfileName());
            if (result) {
                if (queryForm.getFtpSeq().intValue() >= 1) {
                    bjService.updateData(queryForm.getMfileName(), queryForm.getMfileDate(), DateUtility.changeDateType(queryForm.getChkDate()));
                } else {
                    bjService.insertData(queryForm.getMfileName(), queryForm.getMfileDate(), DateUtility.changeDateType(queryForm.getChkDate()));
                }
                List<MediaOnlineDownloadCase> caseData2 = mediaUploadFile.getNewCase(CaseSessionHelper.getOldMediaOnlineCaseList(request), queryForm.getMfileName());
                CaseSessionHelper.setOldMediaOnlineCaseList(caseData2, request);
                saveMessages(session, DatabaseMessageHelper.successUpload2());
                log.debug("批次處理 - 老年年金線上產製媒體檔作業 - 產製媒體檔 - 傳送作業 OldMediaOnlineAction.doUploadFile() 完成 ...");
                return mapping.findForward(ConstantKey.FORWARD_SUCCESS_UPLOAD);
            } else {
                saveMessages(session, DatabaseMessageHelper.failUpload2());
                log.debug("批次處理 - 老年年金線上產製媒體檔作業 - 產製媒體檔 - 傳送作業 OldMediaOnlineAction.doUploadFile() 失敗 ...");
                return mapping.findForward(ConstantKey.FORWARD_FAIL_UPLOAD);
            }
        } catch (Exception e) {
            log.error("批次處理 - 老年年金線上產製媒體檔作業 - 產製媒體檔 - 傳送作業 OldMediaOnlineAction.doUploadFile() 失敗 ..." + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.failUpload2());
            return mapping.findForward(ConstantKey.FORWARD_FAIL_UPLOAD);
        }
    }

    /**
     * 批次處理 - 老年年金線上批次產製媒體檔作業-返回進度查詢頁-(BABA0M172Q)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            return mapping.findForward(ConstantKey.FOWARD_QRY_PROGRESS);
        }
        catch (Exception e) {
            return null;
        }

    }

    /**
     * 批次處理 - 老年年金線上批次產製媒體檔作業-返回首頁-(BABA0M170Q)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackFirst(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            return mapping.findForward(ConstantKey.FORWARD_SCHEDULE_SUCCESS);
        }
        catch (Exception e) {
            return null;
        }

    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }

    public void setFtpDbClient(FtpDbHelper ftpDbClient) {
        this.ftpDbClient = ftpDbClient;
    }

}
