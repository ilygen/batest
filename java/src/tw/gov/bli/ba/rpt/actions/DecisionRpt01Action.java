package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.util.List;
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
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.rpt.cases.DecisionRpt01Case;
import tw.gov.bli.ba.rpt.forms.DecisionRpt01Form;
import tw.gov.bli.ba.rpt.report.DecisionRpt01Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import tw.gov.bli.ba.domain.Baarclist;

/**
 * 列印作業 - 決行作業相關報表 - 歸檔清單 - 查詢頁面 (balp0d210l.jsp)
 * 
 * @author Goston
 */
public class DecisionRpt01Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DecisionRpt01Action.class);

    private RptService rptService;

    /**
     * 列印作業 - 決行作業相關報表 - 歸檔清單 - 查詢頁面 (balp0d210l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 決行作業相關報表 - 歸檔清單 - 查詢頁面 DecisionRpt01Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DecisionRpt01Form queryForm = (DecisionRpt01Form) form;

        try {
            // 抓報表資料
            List<DecisionRpt01Case> caseList = rptService.getDecisionRpt01DataBy(queryForm.getPayCode(), queryForm.getDecisionSdate(), queryForm.getDecisionEdate(), queryForm.getExeMan());

            if (caseList.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                Baarclist maxData = rptService.selectMaxBaarclistNumAndArcPg(queryForm.getPayCode());
                // 遺屬案件類別3，需另外編碼（從901開始）
                Baarclist maxDataForS = rptService.selectMaxBaarclistNumAndArcPgForS(queryForm.getPayCode());

                // 列印報表
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                DecisionRpt01Report report = new DecisionRpt01Report(queryForm.getPayCode(), queryForm.getDecisionSdate(), queryForm.getDecisionEdate(), maxData.getArcPg(), maxDataForS.getArcPg());
                baoOutput = report.doReport(userData, caseList);

                // 更新資料庫
                rptService.upDateDateForDecisionRpt01(queryForm.getPayCode(), queryForm.getDecisionSdate(), queryForm.getDecisionEdate(), caseList, (maxDataForS.getBaarclistNum().intValue() > maxData.getBaarclistNum().intValue()) ? maxDataForS.getBaarclistNum() : maxData.getBaarclistNum());
                // 合併 PDF 檔案
                // PdfReader readerY = new PdfReader(baoYOutput.toByteArray());
                // PdfReader readerN = new PdfReader(baoNOutput.toByteArray());
                // Document document = new Document(readerY.getPageSizeWithRotation(1));
                // PdfCopy copy = new PdfCopy(document, baoOutput);
                // document.open();
                // for (int i = 1; i <= readerY.getNumberOfPages(); i++) {
                // PdfImportedPage page = copy.getImportedPage(readerY, i);
                // copy.addPage(page);
                // }
                // for (int i = 1; i <= readerN.getNumberOfPages(); i++) {
                // PdfImportedPage page = copy.getImportedPage(readerN, i);
                // copy.addPage(page);
                // }
                // document.close();

                String printDate = DateUtility.getNowChineseDate();

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"DecisionRpt01_" + printDate + ".pdf" + "\"");
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

                return null;
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 歸檔清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
