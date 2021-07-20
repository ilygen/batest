package tw.gov.bli.ba.maint.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
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

import com.ctc.wstx.util.DataUtil;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.Global;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.maint.forms.AnnuityAcceptDataTransferForm;
import tw.gov.bli.ba.services.QueryService;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.util.DateUtil;

/**
 * 維護作業 - 年金受理資料轉出 - 查詢頁面 (bapa0x160q.jsp)
 * 
 * @author Eddie
 */
public class AnnuityAcceptDataTransferAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(AnnuityAcceptDataTransferAction.class);
    private QueryService queryService;

    /**
     * 維護作業 - 年金受理資料轉出 - 查詢頁面 (bapa0x160q.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(" 維護作業 - 年金受理資料轉出 - 查詢頁面 AnnuityAcceptDataTransferAction.doQuery() 開始 ... ");
        HttpSession session = request.getSession();
        
        AnnuityAcceptDataTransferForm queryForm = (AnnuityAcceptDataTransferForm) form;
        try {
            String apNo = queryForm.getApNo();

            // 查詢資料
            List<String> dataList = new ArrayList<String>();

            dataList = queryService.forAnnuityAcceptDataTransfer(apNo);

            if (dataList == null || dataList.isEmpty()) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }else {
        		String fileName = "filedata_" + DateUtil.getNowWestDateTime(true) + "_" + apNo + ".sql";
        		StringBuilder os = new StringBuilder();
            	for(String s : dataList) {
            		os.append(s + "\r\n");
            	}
            	
            	String contentType = getServlet().getServletContext().getMimeType(fileName);
                response.setHeader("Content-disposition", "attachment; filename=" + Encode.forJava(fileName) + "\"");
                response.setHeader("Content-Encoding", "txt");
                response.setContentType(contentType); // 設定 MIME TYPE
                StringReader in = new StringReader(os.toString());
                ServletOutputStream out = response.getOutputStream();
                try {
                    // 為解決 UNIX 下中文變亂碼的問題, 需強制指定輸出內容編碼
                    IOUtils.copy(in, out, Global.IO_ENCODING_UTF8);
                }
                finally {
                    IOUtils.closeQuietly(out);
                    IOUtils.closeQuietly(in);
                }
            }
            log.debug("執行  維護作業 - 年金受理資料轉出 - 查詢頁面 AnnuityAcceptDataTransferAction.doQuery() 完成 ... ");
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AnnuityAcceptDataTransferAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setQueryService(QueryService queryService) {
    	this.queryService = queryService;
    }
}
