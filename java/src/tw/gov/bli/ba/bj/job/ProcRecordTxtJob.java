package tw.gov.bli.ba.bj.job;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.bj.helper.BatchHelper;
import tw.gov.bli.ba.bj.helper.FtpHelper;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.ExceptionUtility;

/**
 * 給付媒體回押註記 及 收回沖銷 - FTP 資料文字檔 處理
 * 
 * @author Goston
 */
public class ProcRecordTxtJob {
    private static Log log = LogFactory.getLog(ProcRecordTxtJob.class);

    private FtpHelper ftpClient;
    private BjService bjService;

    public void process() {
        try {
            
            if (!BatchHelper.needExecuteBatch()) {
                log.info("本主機不須執行 給付媒體回押註記 及 收回沖銷 - FTP 資料文字檔 處理...");
                
                return;
            }
            
            log.info("開始 給付媒體回押註記 及 收回沖銷 - FTP 資料文字檔 處理...");

            // 取得資料文字檔檔名清單
            List<Map> fileList = ftpClient.getRecordFileNames2();

            // 處理資料文字檔
            if (fileList != null) {
                for (Map file: fileList) {
                    try {
                        bjService.insertRecordFileData(
                        	MapUtils.getString(file, "filename", ""),
                        	(Calendar)MapUtils.getObject(file, "timestamp"));
                    }
                    catch (Exception e) {
                        log.error("處理 給付媒體回押註記 及 收回沖銷 - FTP 資料文字檔 發生錯誤, 原因: " + ExceptionUtility.getStackTrace(e));
                    }
                }
            }

            log.info("處理 給付媒體回押註記 及 收回沖銷 - FTP 資料文字檔 完成...");
        }
        catch (Exception e) {
            log.error("處理 給付媒體回押註記 及 收回沖銷 - FTP 資料文字檔 發生錯誤, 原因: " + ExceptionUtility.getStackTrace(e));
        }
    }

    public FtpHelper getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FtpHelper ftpClient) {
        this.ftpClient = ftpClient;
    }

    public BjService getBjService() {
        return bjService;
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }

}
