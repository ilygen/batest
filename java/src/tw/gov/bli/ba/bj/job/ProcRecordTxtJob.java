package tw.gov.bli.ba.bj.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.bj.helper.BatchHelper;
import tw.gov.bli.ba.bj.helper.FtpHelper;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.util.MgFile;
import tw.gov.bli.common.util.MgMrUtil;

/**
 * 給付媒體回押註記 及 收回沖銷 - FTP 資料文字檔 處理
 * 
 * @author Goston
 */
public class ProcRecordTxtJob {
    private static Log log = LogFactory.getLog(ProcRecordTxtJob.class);

    private FtpHelper ftpClient;
    private BjService bjService;
    private MgMrUtil mgMrUtil;
    
//	public void process() {
//		String _ftpinput = PropertyHelper.getProperty("mgBankFileIn");//原本是讀取ftpClient.dirForDataFile
//		String ipaddr = PropertyHelper.getProperty("mg.ip");
//		String portno = PropertyHelper.getProperty("mg.port");
//		String loginid = PropertyHelper.getProperty("sys.default.userid");
//		Map map = new HashMap();
//		map.put("ipaddr", ipaddr);
//		map.put("portno", portno);
//		map.put("loginid", loginid);
//		map.put("ftpdir", _ftpinput);
//		try {
//		    
//		    if (!BatchHelper.needExecuteBatch()) {
//		        log.info("本主機不須執行 給付媒體回押註記 及 收回沖銷 - FTP 資料文字檔 處理...");
//		        return;
//		    }
//		    
//		    log.info("開始 給付媒體回押註記 及 收回沖銷 - FTP 資料文字檔 處理...");
//		
//		    // 取得資料文字檔檔名清單
//			List<MgFile> mgFiles = mgMrUtil.query(map, "", "", loginid);
//		
//		    // 處理資料文字檔
//		    if (mgFiles != null) {
//		    	List<MgFile> tampList = getMGPaidMarkFileNameFileNames(mgFiles);
//		    	log.debug("tampList : " + tampList.size());
//		        for (MgFile file: tampList) {
//					try {
//						SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
//						Date date =sdf.parse(file.getCreateDate());
//						Calendar calendar = Calendar.getInstance();
//						calendar.setTime(date);
//						
//						bjService.insertRecordFileData_Mg(map, file.getName(), calendar);
//					}
//					catch (Exception e) {
//						log.error("處理 給付媒體回押註記 及 收回沖銷 - MG 資料文字檔 發生錯誤, 原因: " + ExceptionUtility.getStackTrace(e));
//					}
//		        }
//		    }
//		
//		    log.info("處理 給付媒體回押註記 及 收回沖銷 - MG 資料文字檔 完成...");
//		}
//		catch (Exception e) {
//		    log.error("處理 給付媒體回押註記 及 收回沖銷 - MG 資料文字檔 發生錯誤, 原因: " + ExceptionUtility.getStackTrace(e));
//		}
//	}

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

	/**
     * 只取所有在 MG 目錄中的 PaidMarkFileNamePrefix檔名
     * 
     * @return 回傳 MgFile List
     */
    public List<MgFile> getMGPaidMarkFileNameFileNames(List<MgFile> mgFiles) {
    	List<MgFile> getList = new ArrayList<>();
    	for (MgFile file: mgFiles) {
        	for (String paidMarkFileNamePrefix : ftpClient.getPaidMarkFileNamePrefix()) {
				if (StringUtils.startsWithIgnoreCase(file.getName(), paidMarkFileNamePrefix)) { // 給付媒體回押註記
					getList.add(file);
					log.debug("getList Filename: " + file.getName());
				}
			}
        }
    	return getList;
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

	public MgMrUtil getMgMrUtil() {
		return mgMrUtil;
	}

	public void setMgMrUtil(MgMrUtil mgMrUtil) {
		this.mgMrUtil = mgMrUtil;
	}

    
}
