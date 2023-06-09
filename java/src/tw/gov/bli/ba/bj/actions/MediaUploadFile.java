package tw.gov.bli.ba.bj.actions;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.struts.action.ActionForm;

import common.util.dbutil.DbToolsUtils;
import tw.gov.bli.ba.bj.cases.MediaOnlineDownloadCase;
import tw.gov.bli.ba.bj.forms.OldMediaBatchForm;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.util.ExceptionUtility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 媒體檔作業 - 媒體檔下載 - 傳送作業
 *
 * @author Eddie
 */
public class MediaUploadFile {
    private static Log log = LogFactory.getLog(MediaUploadFile.class);

    /**
     * 媒體檔作業 - 媒體檔下載 - 傳送作業
     * @throws Exception 
     */
    public boolean doUploadFile(String mfileName) throws Exception {
        log.error("批次處理 - 老年年金批次產製媒體檔作業-媒體檔下載-傳送作業OldMediaBatchAction.doUploadFile() 開始 ...");

        String srcIp = PropertyHelper.getProperty("ftpDbClient.serverAddress");
        int srcPort = Integer.parseInt(PropertyHelper.getProperty("ftpDbClient.serverPort"));
        String srcUserId = PropertyHelper.getProperty("ftpDbClient.userId");
        String srcPwd = DbToolsUtils.decrypt(PropertyHelper.getProperty("ftpDbClient.userPass"));
        String srcDir = PropertyHelper.getProperty("ftpDbClient.dirForRecordFileMedia");

        String desIp = PropertyHelper.getProperty("ftpOutClient.serverAddress");
        int desPort = Integer.parseInt(PropertyHelper.getProperty("ftpOutClient.serverPort"));
        String desUserId = PropertyHelper.getProperty("ftpOutClient.userId");
        String desPwd = DbToolsUtils.decrypt(PropertyHelper.getProperty("ftpOutClient.userPass"));
        String desDir = PropertyHelper.getProperty("ftpOutClient.dirForDataFile");

        FTPClient srcFTP = new FTPClient();
        FTPClient desFTP = new FTPClient();
        ByteArrayOutputStream bos = null;
        InputStream is = null;

        try {
            srcFTP.connect(srcIp, srcPort);
            srcFTP.login(srcUserId, srcPwd);
            srcFTP.setControlEncoding("UTF-8");
            srcFTP.setFileType(FTPClient.BINARY_FILE_TYPE);
            srcFTP.changeWorkingDirectory(new String(srcDir.getBytes(), "ISO-8859-1"));
            bos = new ByteArrayOutputStream();
            srcFTP.retrieveFile(mfileName, bos);
            is = new ByteArrayInputStream(bos.toByteArray());

            desFTP.connect(desIp, desPort);
            desFTP.login(desUserId, desPwd);
            desFTP.setControlEncoding("UTF-8");
            desFTP.setFileType(FTPClient.BINARY_FILE_TYPE);
            desFTP.enterLocalPassiveMode();
            desFTP.changeWorkingDirectory(new String(desDir.getBytes(), "ISO-8859-1"));
            return desFTP.storeFile(new String(mfileName.getBytes(), "ISO-8859-1"), is);
        } catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("傳送作業OldMediaBatchAction.doUploadFile() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return false;
        } finally {
            try {
                if (desFTP.isConnected()) {
                    desFTP.logout();
                    desFTP.disconnect();
                }
            } catch (Exception e) {
                log.error("傳送作業OldMediaBatchAction.doUploadFile() desFTP 中斷連線發生錯誤:" + ExceptionUtility.getStackTrace(e));
            }

            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                log.error("傳送作業OldMediaBatchAction.doUploadFile() 關閉資源發生錯誤:" + ExceptionUtility.getStackTrace(e));
            }

            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                log.error("傳送作業OldMediaBatchAction.doUploadFile() 關閉資源發生錯誤:" + ExceptionUtility.getStackTrace(e));
            }

            try {
                if (srcFTP.isConnected()) {
                    srcFTP.logout();
                    srcFTP.disconnect();
                }
            } catch (Exception e) {
                log.error("傳送作業OldMediaBatchAction.doUploadFile() srcFTP 中斷連線發生錯誤:" + ExceptionUtility.getStackTrace(e));
            }
        }
    }

    public List<MediaOnlineDownloadCase> getNewCase(List<MediaOnlineDownloadCase> caseData, String mfileName) {
        List<MediaOnlineDownloadCase> caseData2 = new ArrayList<MediaOnlineDownloadCase>();
        for (MediaOnlineDownloadCase c : caseData) {
            if (StringUtils.equals(c.getMfileName(), mfileName)) {
                c.setFtpSeq(c.getFtpSeq().add(BigDecimal.valueOf(1)));
                caseData2.add(c);
            }else{
                caseData2.add(c);
            }
        }
        return caseData2;
    }
}
