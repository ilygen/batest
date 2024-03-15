package tw.gov.bli.ba.services;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.BaUserEmailCase;
import tw.gov.bli.ba.bj.cases.BagivedtlCase;
import tw.gov.bli.ba.bj.cases.BatchRecTemp;
import tw.gov.bli.ba.bj.cases.CheckJobCase;
import tw.gov.bli.ba.bj.cases.CheckJobRptCase;
import tw.gov.bli.ba.bj.cases.CompPaymentCase;
import tw.gov.bli.ba.bj.cases.ExecStatisticReportCase;
import tw.gov.bli.ba.bj.cases.ExecStatisticsDtlCase;
import tw.gov.bli.ba.bj.cases.MediaOnlineDownloadCase;
import tw.gov.bli.ba.bj.cases.MediaOnlineProgressCase;
import tw.gov.bli.ba.bj.cases.MediaOnlineProgressDtlCase;
import tw.gov.bli.ba.bj.cases.MonthBatchDtlCase;
import tw.gov.bli.ba.bj.cases.MonthBatchKCase;
import tw.gov.bli.ba.bj.cases.MonthBatchLCase;
import tw.gov.bli.ba.bj.cases.MonthBatchSCase;
import tw.gov.bli.ba.bj.cases.MonthCheckKCase;
import tw.gov.bli.ba.bj.cases.MonthCheckLCase;
import tw.gov.bli.ba.bj.cases.MonthCheckSCase;
import tw.gov.bli.ba.bj.cases.MonthKCase;
import tw.gov.bli.ba.bj.cases.MonthLCase;
import tw.gov.bli.ba.bj.cases.MonthSCase;
import tw.gov.bli.ba.bj.cases.QryProcedureCase;
import tw.gov.bli.ba.bj.cases.ReceivableAdjustBJDetailCase;
import tw.gov.bli.ba.bj.cases.ReceivableAdjustBJMasterCase;
import tw.gov.bli.ba.bj.cases.ReturnWriteOffBJCase;
import tw.gov.bli.ba.bj.cases.RunProcedureCase;
import tw.gov.bli.ba.bj.cases.Trans2OverdueReceivableBJDetailCase;
import tw.gov.bli.ba.bj.cases.UpdatePaidMarkBJCase;
import tw.gov.bli.ba.bj.helper.FtpApHelper;
import tw.gov.bli.ba.bj.helper.FtpHelper;
import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.dao.BaapplogDao;
import tw.gov.bli.ba.dao.BabatchjobDao;
import tw.gov.bli.ba.dao.BabatchjobdtlDao;
import tw.gov.bli.ba.dao.BabatchrecDao;
import tw.gov.bli.ba.dao.BadaprDao;
import tw.gov.bli.ba.dao.BagivedtlDao;
import tw.gov.bli.ba.dao.BagivetmpdtlDao;
import tw.gov.bli.ba.dao.BamfileftprecordDao;
import tw.gov.bli.ba.dao.BansfDao;
import tw.gov.bli.ba.dao.BaonlinejobDao;
import tw.gov.bli.ba.dao.BapaissudateDao;
import tw.gov.bli.ba.dao.BaprocedureDao;
import tw.gov.bli.ba.dao.BaproceduredataDao;
import tw.gov.bli.ba.dao.BaproceduredtlDao;
import tw.gov.bli.ba.dao.BarvcaseDao;
import tw.gov.bli.ba.dao.BarvconfirmDao;
import tw.gov.bli.ba.dao.BaunacpdtlDao;
import tw.gov.bli.ba.dao.BaunacprecDao;
import tw.gov.bli.ba.dao.BauseremailDao;
import tw.gov.bli.ba.dao.RunprocedurejobDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.ba.domain.Babatchjobdtl;
import tw.gov.bli.ba.domain.Babatchrec;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.domain.Bagivedtl;
import tw.gov.bli.ba.domain.Bagivetmpdtl;
import tw.gov.bli.ba.domain.Bansf;
import tw.gov.bli.ba.domain.Baonlinejob;
import tw.gov.bli.ba.domain.Baproceduredata;
import tw.gov.bli.ba.domain.Baproceduredtl;
import tw.gov.bli.ba.domain.Barvcase;
import tw.gov.bli.ba.domain.Barvconfirm;
import tw.gov.bli.ba.domain.Baunacpdtl;
import tw.gov.bli.ba.domain.Baunacprec;
import tw.gov.bli.ba.domain.Bauseremail;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.MailHelper;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.util.MgFile;
import tw.gov.bli.common.util.MgMrUtil;

/**
 * Service for 批次處理
 * 
 * @author Rickychi
 */
public class BjService {
	private static Log log = LogFactory.getLog(BjService.class);

	private BaunacprecDao baunacprecDao;
	private BaunacpdtlDao baunacpdtlDao;
	private BabatchrecDao babatchrecDao;
	private BagivedtlDao bagivedtlDao;
	private BaonlinejobDao baonlinejobDao;
	private BabatchjobDao babatchjobDao;
	private BabatchjobdtlDao babatchjobdtlDao;
	private BapaissudateDao bapaissudateDao;
	private BaprocedureDao baprocedureDao;
	private BaproceduredataDao baproceduredataDao;
	private BaproceduredtlDao baproceduredtlDao;
	private BadaprDao badaprDao;
	private BaappbaseDao baappbaseDao;
	private BauseremailDao bauseremailDao;
	private BagivetmpdtlDao bagivetmpdtlDao;
	private BarvconfirmDao barvconfirmDao;
	private BarvcaseDao barvcaseDao;
	private RunprocedurejobDao runprocedurejobDao;
	private BaapplogDao baapplogDao;
	private MailHelper mailHelper;
	private FtpHelper ftpClient;
	private FtpApHelper ftpApClient;
	private BansfDao bansfDao;
	private BamfileftprecordDao bamfileftprecordDao;
	private MgMrUtil mgMrUtil;
	
	/**
	 * 給付媒體回押註記 及 收回作業 - FTP 紀錄檔 處理
	 * 
	 * @param fileName
	 */
	public void insertRecordData(String fileName) {
		
		// 取得紀錄檔內容
		String[] fileContent = ftpClient.getRecordFileContent(fileName);

		if (fileContent != null) { // ... [
			Babatchrec recordData = new Babatchrec();
			recordData.setProcStat("0"); // 處理狀態 0: 未處理 1: 已入排程、2: 排程處理中 3: 已處理
			recordData.setProcFlag("0"); // 回押註記

			for (int i = 0; i < fileContent.length; i++) { // ... [
				String line = fileContent[i];

				if (StringUtils.contains(line, "=")) { // ... [
					String key = StringUtils.substring(line, 0, StringUtils.indexOf(line, "="));
					String value = StringUtils.substring(line, StringUtils.indexOf(line, "=") + 1, line.length());

					// 資料列編號
					// 2009.03.06 改由 Sequence 取得
					/*
					 * if (StringUtils.equalsIgnoreCase(key, "MEMOLOGID")) { if
					 * (StringUtils.isBlank(value)) throw new
					 * RuntimeException("MEMOLOGID 資料列編號為空值"); else recordData.setBaBatchRecId(new
					 * BigDecimal(value)); }
					 */

					// 批次處理類型 及 檔案名稱
					if (StringUtils.equalsIgnoreCase(key, "UPFLENAME")) {
						for (String paidMarkFileNamePrefix : ftpClient.getPaidMarkFileNamePrefix()) {
							if (StringUtils.startsWithIgnoreCase(value, paidMarkFileNamePrefix)) { // 給付媒體回押註記
								recordData.setBatchTyp("01");
								recordData.setFileName(value);
								break;
							}
						}

						for (String writeOffFileNamePrefix : ftpClient.getWriteOffFileNamePrefix()) {
							if (StringUtils.startsWithIgnoreCase(value, writeOffFileNamePrefix)) { // 收回沖銷
								recordData.setBatchTyp("02");
								recordData.setFileName(value);
								break;
							}
						}

						if (StringUtils.isBlank(recordData.getBatchTyp())) {
							throw new RuntimeException("UPFLENAME 檔案名稱錯誤");
						}
					}

					// 筆數
					if (StringUtils.equalsIgnoreCase(key, "FREC")) {
						if (StringUtils.isNotBlank(value))
							recordData.setDataCount(new BigDecimal(value));
					}

					// 資料來源類別
					if (StringUtils.equalsIgnoreCase(key, "AKIND")) {
						if (StringUtils.isNotBlank(value))
							recordData.setUpTyp(value);
					}

					// 資料來源
					if (StringUtils.equalsIgnoreCase(key, "UPNAME")) {
						if (StringUtils.isNotBlank(value))
							recordData.setUpOrgan(value);
					}

					// 資料來源單位 ID
					if (StringUtils.equalsIgnoreCase(key, "CMPID")) {
						if (StringUtils.isNotBlank(value))
							recordData.setUpOrganId(value);
					}

					// 檔案產生時間
					if (StringUtils.equalsIgnoreCase(key, "UPFTIME")) {
						if (StringUtils.isNotBlank(value))
							recordData.setUpTime(value);
					}

					// 總金額
					if (StringUtils.equalsIgnoreCase(key, "AMOUNT")) {
						if (StringUtils.isNotBlank(value))
							recordData.setAmount(new BigDecimal(value));
					}
				} // ] ... end if (StringUtils.contains(line, "="))

				// 發件單位 , 出帳類別 , 出帳日期
				if (StringUtils.length(recordData.getFileName()) >= 13) {
					// 發件單位
					recordData.setSunit(StringUtils.substring(recordData.getFileName(), 3, 6));
					// 出帳類別
					recordData.setTaTyp(StringUtils.substring(recordData.getFileName(), 0, 3));
					// 出帳日期
					recordData.setPayDate(
							DateUtility.changeDateType(StringUtils.substring(recordData.getFileName(), 6, 13)));
				}

				// 異動日期時間
				recordData.setUpdTime(DateUtility.getNowWestDateTime(true));
			} // ] ... end for (int i = 0; i < fileContent.length; i++)

			// 存檔處理
			log.info("新增紀錄檔資料至 BABATCHREC 開始，檔名:" + StringUtils.trimToEmpty(fileName));
			BigDecimal key = babatchrecDao.insertData(recordData);
			if (key != null)
				log.info("BABATCHREC.BABATCHRECID = " + StringUtils.trimToEmpty(key.toPlainString()) + "，檔名:"
						+ StringUtils.trimToEmpty(fileName));

			log.info("新增紀錄檔資料至 BABATCHREC 完成，檔名:" + StringUtils.trimToEmpty(fileName));

			// 存檔處理完後將 FTP 上的檔案搬移到備份目錄
			ftpClient.backupRecordFile(fileName);
		} // ] ... end if (fileContent != null)
	}

	/**
	 * 給付媒體回押註記 及 收回作業 - FTP 資料文字檔 處理
	 * 
	 * @param fileName
	 * @param fileTimestamp
	 */
	public void insertRecordFileData(String fileName, Calendar fileTimestamp) {
		// 取得資料文字檔內容
		String[] fileContent = ftpClient.getRecordFileContent(fileName);
		if (fileContent != null) { // ... [
			Babatchrec recordData = new Babatchrec();
			recordData.setProcStat("0"); // 處理狀態 0: 未處理 1: 已入排程、2: 排程處理中 3: 已處理
			recordData.setProcFlag("0"); // 回押註記

			// get first line
			// sample: "1005     BLI     BL111111222139862           0000000027530500000000141 1111122                                                              "
			String line = fileContent[0];
			// 批次處理類型 及 檔案名稱
			for (String paidMarkFileNamePrefix : ftpClient.getPaidMarkFileNamePrefix()) {
				if (StringUtils.startsWithIgnoreCase(fileName, paidMarkFileNamePrefix)) { // 給付媒體回押註記
					recordData.setBatchTyp("01");
					recordData.setFileName(fileName);
					break;
				}
			}

			for (String writeOffFileNamePrefix : ftpClient.getWriteOffFileNamePrefix()) {
				if (StringUtils.startsWithIgnoreCase(fileName, writeOffFileNamePrefix)) { // 收回沖銷
					recordData.setBatchTyp("02");
					recordData.setFileName(fileName);
					break;
				}
			}

			if (StringUtils.isBlank(recordData.getBatchTyp())) {
				throw new RuntimeException("檔案名稱錯誤");
			}

			// 筆數
			String frec = StringUtils.substring(line, 59, 69);
			if (StringUtils.isNotBlank(frec))
				recordData.setDataCount(new BigDecimal(frec));

			// 資料來源
			String upname = "土地銀行";
			if (StringUtils.isNotBlank(upname))
				recordData.setUpOrgan(upname);

			// 資料來源單位 ID
			String cmpid = "03700301"; // 土地銀行統編
			if (StringUtils.isNotBlank(cmpid))
				recordData.setUpOrganId(cmpid);

			// 檔案產生時間
			if (fileTimestamp != null) {
				recordData.setUpTime(DateUtility.parseDateToWestDateTime(fileTimestamp.getTime(), true));
			}

			// 總金額
			String amount = StringUtils.substring(line, 45, 59);
			if (StringUtils.isNotBlank(amount))
				recordData.setAmount(new BigDecimal(amount));

			// 發件單位 , 出帳類別 , 出帳日期 , 資料來源類別
			if (StringUtils.length(recordData.getFileName()) >= 26) {
				// 發件單位
				recordData.setSunit(StringUtils.substring(recordData.getFileName(), 3, 6));
				// 出帳類別
				recordData.setTaTyp(StringUtils.substring(recordData.getFileName(), 0, 3));
				// 出帳日期
				recordData.setPayDate(
						DateUtility.changeDateType(StringUtils.substring(recordData.getFileName(), 6, 13)));
				// 資料來源類別 1: T, 2: K, 3: S
				String akind = StringUtils.substring(recordData.getFileName(), 25, 26);
				if (StringUtils.isNotBlank(akind)) {
					if (akind.equals("1")) {
						recordData.setUpTyp("T");
					} else if (akind.equals("2")) {
						recordData.setUpTyp("K");
					} else if (akind.equals("3")) {
						recordData.setUpTyp("S");
					}
				}
			}

			// 異動日期時間
			recordData.setUpdTime(DateUtility.getNowWestDateTime(true));

			// 存檔處理
			log.info("新增資料文字檔資料至 BABATCHREC 開始，檔名:" + StringUtils.trimToEmpty(fileName));
			BigDecimal key = babatchrecDao.insertData(recordData);
			if (key != null)
				log.info("BABATCHREC.BABATCHRECID = " + StringUtils.trimToEmpty(key.toPlainString()) + "，檔名:"
						+ StringUtils.trimToEmpty(fileName));

			log.info("新增資料文字檔資料至 BABATCHREC 完成，檔名:" + StringUtils.trimToEmpty(fileName));

			// 存檔處理完後將 FTP 上的檔案搬移到 DataFile 目錄
			ftpClient.moveFileMedia(fileName);
		}
	}
	
	/**
	 * 給付媒體回押註記 及 收回作業 - MG 資料文字檔 處理
	 * 
	 * @param fileName
	 * @param fileTimestamp
	 */
	public void insertRecordFileData_Mg(Map map, String fileName, Calendar fileTimestamp) {
		String _ftpoutput_BK = PropertyHelper.getProperty("mgBankFileBK");
		String _ftpinput = PropertyHelper.getProperty("mgBankFileIn");//原本是讀取ftpClient.dirForDataFile
		String ipaddr = PropertyHelper.getProperty("mg.ip");
		String portno = PropertyHelper.getProperty("mg.port");
		String loginid = PropertyHelper.getProperty("sys.default.userid");
		String workDir = PropertyHelper.getProperty("rpt.path");
		// 取得fileName文字名檔案
    	List<MgFile> mgFiles = mgMrUtil.query(map, fileName, "", loginid);
//		// 取得資料文字檔內容
		if (mgFiles != null) { 
			Babatchrec recordData = new Babatchrec();
			recordData.setProcStat("0"); // 處理狀態 0: 未處理 1: 已入排程、2: 排程處理中 3: 已處理
			recordData.setProcFlag("0"); // 回押註記
			
			// 取得資料文字檔內容(先將檔案下載下來在讀取內容)
			for(int i=0; i<mgFiles.size(); i++){
            	String mgFileName = mgFiles.get(i).getName();
//                map.put("ftpdir", _ftpinput); // change dir
            	if (StringUtils.endsWith(fileName, ".txt") || StringUtils.endsWith(fileName, ".TXT")) {
            		// 下載遠端檔案
            		String statusDownload = mgMrUtil.download(map, mgFileName, workDir, loginid);
            		if (MgMrUtil.MG_MR_SUCCESS_CODE.equals(statusDownload)) {
            			// 下載成功後開始讀取內容
            			log.debug("download success.");
//						String outputFilenamePr = "PROC_BA_" + mgFileName;
						
			            //下載到本機後位置
						String downloadFilepath = workDir + fileName ;
						
						String[] fileLinseList = getTxtFileContent(downloadFilepath);
						// get first line
						// sample: "1005     BLI     BL111111222139862           0000000027530500000000141 1111122                                                              "
						String line = fileLinseList[0];
						
						// 批次處理類型 及 檔案名稱
						for (String paidMarkFileNamePrefix : ftpClient.getPaidMarkFileNamePrefix()) {
							if (StringUtils.startsWithIgnoreCase(fileName, paidMarkFileNamePrefix)) { // 給付媒體回押註記
								recordData.setBatchTyp("01");
								recordData.setFileName(fileName);
								break;
							}
						}
			
						for (String writeOffFileNamePrefix : ftpClient.getWriteOffFileNamePrefix()) {
							if (StringUtils.startsWithIgnoreCase(fileName, writeOffFileNamePrefix)) { // 收回沖銷
								recordData.setBatchTyp("02");
								recordData.setFileName(fileName);
								break;
							}
						}
			
						if (StringUtils.isBlank(recordData.getBatchTyp())) {
							throw new RuntimeException("檔案名稱錯誤");
						}
			
						// 筆數
						String frec = StringUtils.substring(line, 59, 69);
						if (StringUtils.isNotBlank(frec))
							recordData.setDataCount(new BigDecimal(frec));
			
						// 資料來源
						String upname = "土地銀行";
						if (StringUtils.isNotBlank(upname))
							recordData.setUpOrgan(upname);
			
						// 資料來源單位 ID
						String cmpid = "03700301"; // 土地銀行統編
						if (StringUtils.isNotBlank(cmpid))
							recordData.setUpOrganId(cmpid);
			
						// 檔案產生時間
						if (fileTimestamp != null) {
							recordData.setUpTime(DateUtility.parseDateToWestDateTime(fileTimestamp.getTime(), true));
						}
			
						// 總金額
						String amount = StringUtils.substring(line, 45, 59);
						if (StringUtils.isNotBlank(amount))
							recordData.setAmount(new BigDecimal(amount));
			
						// 發件單位 , 出帳類別 , 出帳日期 , 資料來源類別
						if (StringUtils.length(recordData.getFileName()) >= 26) {
							// 發件單位
							recordData.setSunit(StringUtils.substring(recordData.getFileName(), 3, 6));
							// 出帳類別
							recordData.setTaTyp(StringUtils.substring(recordData.getFileName(), 0, 3));
							// 出帳日期
							recordData.setPayDate(
									DateUtility.changeDateType(StringUtils.substring(recordData.getFileName(), 6, 13)));
							// 資料來源類別 1: T, 2: K, 3: S
							String akind = StringUtils.substring(recordData.getFileName(), 25, 26);
							if (StringUtils.isNotBlank(akind)) {
								if (akind.equals("1")) {
									recordData.setUpTyp("T");
								} else if (akind.equals("2")) {
									recordData.setUpTyp("K");
								} else if (akind.equals("3")) {
									recordData.setUpTyp("S");
								}
							}
						}
			
						// 異動日期時間
						recordData.setUpdTime(DateUtility.getNowWestDateTime(true));
			
						// 存檔處理
						log.info("新增資料文字檔資料至 BABATCHREC 開始，檔名:" + StringUtils.trimToEmpty(fileName));
						BigDecimal key = babatchrecDao.insertData(recordData);
						if (key != null)
							log.info("BABATCHREC.BABATCHRECID = " + StringUtils.trimToEmpty(key.toPlainString()) + "，檔名:"
									+ StringUtils.trimToEmpty(fileName));
			
						log.info("新增資料文字檔資料至 BABATCHREC 完成，檔名:" + StringUtils.trimToEmpty(fileName));
						
						// 存檔處理完後將 MG 上的檔案搬移到 BK 目錄
						map.put("ftpdir", _ftpoutput_BK); // change dir
						File downloadFile = new File(workDir, fileName);
						upload(map, loginid, downloadFile);

            		} else {
            			log.error("download failed!");
            		}
            	} else {
            		log.info("_ftpinput :  " + fileName + " file not found");
            	}
            }
		}
	}
	
	
	private void upload(Map map, String loginid, File file) {
		String statusUpload = mgMrUtil.upload_MG(map, file, loginid, true);
		final String msg = StringUtils.equals(MgMrUtil.MG_MR_SUCCESS_CODE, statusUpload) ? 
		        String.join(" ", file.getName(), "MG上傳檔案成功") :
		        String.join(" ", file.getName(), "MG上傳檔案失敗，請洽資訊人員");
		log.debug(msg);
	}
	
	private String[] getTxtFileContent(String downloadFilepath) {
		String _txtFileEncoding = ftpClient.getTxtFileEncoding();
		ArrayList<String> list = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(downloadFilepath)), _txtFileEncoding));) {
            String strLine = "";
			
			while ((strLine = br.readLine()) != null) {
				if (StringUtils.isNotBlank(StringUtils.trimToEmpty(strLine)))
                    list.add(strLine);
			}
			
		} catch (FileNotFoundException e) {
			log.error("downloadFilepath FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			log.error("downloadFilepath IOException: " + e.getMessage());
		}
		return list.toArray(new String[0]);
	}

	/**
	 * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 資料 for 轉催收作業
	 * 
	 * @param unacpSdate 立帳起日
	 * @param unacpEdate 立帳迄日
	 * @param recKind    收回種類
	 * @return 內含 <code>Baunacprec</code> 物件的 List
	 */
	public List<Trans2OverdueReceivableBJDetailCase> getTrans2OverdueReceivableData(String unacpSdate,
			String unacpEdate, String recKind) {
		List<Baunacprec> list = baunacprecDao.selectTrans2OverdueReceivableDataBy(unacpSdate, unacpEdate, recKind);
		List<Trans2OverdueReceivableBJDetailCase> returnList = new ArrayList<Trans2OverdueReceivableBJDetailCase>();
		for (int i = 0; i < list.size(); i++) {
			Baunacprec obj = list.get(i);
			Trans2OverdueReceivableBJDetailCase caseObj = new Trans2OverdueReceivableBJDetailCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 應收總筆數資料
	 * 
	 * @param unacpSdate 立帳起日
	 * @param unacpEdate 立帳迄日
	 * @param recKind    收回種類
	 * @return <code>BigDecimal</code> 物件
	 */
	public BigDecimal getBaunacprecTotalAmt(String unacpSdate, String unacpEdate, String recKind) {
		return baunacprecDao.selectTotalAmtBy(unacpSdate, unacpEdate, recKind);
	}

	/**
	 * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 應收總金額資料
	 * 
	 * @param unacpSdate 立帳起日
	 * @param unacpEdate 立帳迄日
	 * @param recKind    收回種類
	 * @return <code>BigDecimal</code> 物件
	 */
	public BigDecimal getBaunacprecSumRecAmt(String unacpSdate, String unacpEdate, String recKind) {
		return baunacprecDao.selectSumRecAmtBy(unacpSdate, unacpEdate, recKind);
	}

	/**
	 * 轉催收作業 BATCHJOB
	 * 
	 * @param caseList 資料清單
	 */
	public void doTrans2OverdueReceivableBJ(List<Trans2OverdueReceivableBJDetailCase> caseList) {
		for (int i = 0; i < caseList.size(); i++) {
			Trans2OverdueReceivableBJDetailCase obj = caseList.get(i);
			Baunacprec baunacprec = new Baunacprec();
			Baunacpdtl baunacpdtl = new Baunacpdtl();
			baunacprec.setBaunacprecId(obj.getBaunacprecId());
			baunacprec.setRecKind("02");
			baunacpdtl.setBaunacprecId(obj.getBaunacprecId());
			baunacpdtl.setRecKind("02");
			// 更新應收帳務記錄檔
			baunacprecDao.updateRecKind(baunacprec);
			// 更新應收帳務明細檔
			baunacpdtlDao.updateRecKind(baunacpdtl);
		}
	}

	/**
	 * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 已收調整作業 Master資料
	 * 
	 * @param apNo     受理編號
	 * @param benIdnNo $受款人身份證號
	 * @param recKind  收回種類
	 * @return 內含<code>ReceivableAdjustBJMasterCase</code> 物件的List
	 */
	public List<ReceivableAdjustBJMasterCase> getReceivableAdjMasterData(String apNo, String benIdnNo, String recKind) {
		List<Baunacprec> list = baunacprecDao.selectReceivableAdjMasterDataBy(apNo, benIdnNo, recKind);
		List<ReceivableAdjustBJMasterCase> returnList = new ArrayList<ReceivableAdjustBJMasterCase>();
		for (int i = 0; i < list.size(); i++) {
			Baunacprec obj = list.get(i);
			ReceivableAdjustBJMasterCase caseObj = new ReceivableAdjustBJMasterCase();
			BeanUtility.copyProperties(caseObj, obj);
			returnList.add(caseObj);
		}
		return returnList;
	}

	/**
	 * 依傳入條件取得 應收帳務記錄檔(<code>BAUNACPREC</code>) 已收調整作業 Detail資料
	 * 
	 * @param baunacprecId 應收記錄編號
	 * @return <code>ReceivableAdjustBJDetailCase</code> 物件
	 */
	public ReceivableAdjustBJDetailCase getReceivableAdjDetailData(BigDecimal baunacprecId) {
		Baunacprec obj = baunacprecDao.selectReceivableAdjDetailDataBy(baunacprecId);
		ReceivableAdjustBJDetailCase caseObj = new ReceivableAdjustBJDetailCase();
		BeanUtility.copyProperties(caseObj, obj);
		return caseObj;
	}

	/**
	 * 已收調整作業 BATCHJOB
	 * 
	 * @param caseList 資料清單
	 * @param empNo    員工編號
	 */
	public void doReceivableAdjustMasterBJ(String[] baunacprecIdList, String empNo) {
		for (int i = 0; i < baunacprecIdList.length; i++) {
			BigDecimal baunacprecId = new BigDecimal(baunacprecIdList[i]);
			Baunacprec baunacprec = new Baunacprec();
			Baunacpdtl baunacpdtl = new Baunacpdtl();
			baunacprec.setBaunacprecId(baunacprecId);
			baunacprec.setProcStat("01");
			baunacprec.setProcMan(empNo);
			baunacprec.setProcDate(DateUtility.getNowWestDate());
			baunacpdtl.setBaunacprecId(baunacprecId);
			baunacpdtl.setProcStat("01");
			baunacpdtl.setProcMan(empNo);
			baunacpdtl.setProcDate(DateUtility.getNowWestDate());
			// 更新應收帳務記錄檔
			baunacprecDao.updateForReceivableAdj(baunacprec);
			// 更新應收帳務明細檔
			baunacpdtlDao.updateForReceivableAdj(baunacpdtl);
		}
	}

	/**
	 * 依傳入條件取得 批次作業記錄檔(<code>BABATCHREC</code>) for 給付媒體回押註記資料
	 * 
	 * @param upTimeBeg 處理日期起日
	 * @param UpTimeEnd 處理日期迄日
	 * @return 內含<code>UpdatePaidMarkBJCase</code> 物件的List
	 */
	public List<UpdatePaidMarkBJCase> getUpdatePaidMarkBJListData(String upTimeBeg, String upTimeEnd) {
		List<Babatchrec> dataList = babatchrecDao.selectUpdatePaidMarkBJListBy(upTimeBeg, upTimeEnd, "01");
		List<UpdatePaidMarkBJCase> caseList = new ArrayList<UpdatePaidMarkBJCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Babatchrec babatchrec = dataList.get(i);
			UpdatePaidMarkBJCase updatePaidMarkBJCase = new UpdatePaidMarkBJCase();
			BeanUtility.copyProperties(updatePaidMarkBJCase, babatchrec);
			caseList.add(updatePaidMarkBJCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 批次作業記錄檔(<code>BABATCHREC</code>) for 給付媒體回押註記資料
	 * 
	 * @param upTimeBeg 處理日期起日
	 * @param UpTimeEnd 處理日期迄日
	 * @return <code>UpdatePaidMarkBJCase</code> 物件
	 */
	public UpdatePaidMarkBJCase getUpdatePaidMarkBJData(String baBatchRecId) {
		Babatchrec caseData = babatchrecDao.selectUpdatePaidMarkBJBy(baBatchRecId);
		UpdatePaidMarkBJCase updatePaidMarkBJCase = new UpdatePaidMarkBJCase();
		BeanUtility.copyProperties(updatePaidMarkBJCase, caseData);
		return updatePaidMarkBJCase;
	}

	/**
	 * 依傳入條件取得 批次作業記錄檔(<code>BABATCHREC</code>) for 給付媒體回押註記資料
	 * 
	 * @param baBatchRecId
	 * @return <code>List<Bagivedtl></code> 物件
	 */
	public List<BagivedtlCase> getUpdatePaidMarkBJData3(String baBatchRecId) {
		List<Bagivedtl> caseList = bagivedtlDao.selectUpdatePaidMarkList3By(baBatchRecId);
		List<BagivedtlCase> objList = new ArrayList<BagivedtlCase>();

		for (Bagivedtl caseData : caseList) {
			BagivedtlCase objData = new BagivedtlCase();
			BeanUtility.copyProperties(objData, caseData);

			objList.add(objData);
		}

		return objList;
	}

	/**
	 * 依傳入條件取得 批次作業記錄檔(<code>BABATCHREC</code>) for 給付媒體回押註記資料
	 * 
	 * @param baBatchRecId
	 * @return <code>List<Bagivedtl></code> 物件
	 */
	public BagivedtlCase getUpdatePaidMarkBJData2(String baBatchRecId, String payCode, String payDate, String taTyp) {
		Bagivedtl caseData = bagivedtlDao.selectUpdatePaidMark2By(baBatchRecId, payCode, payDate, taTyp);
		BagivedtlCase objData = new BagivedtlCase();

		if (caseData != null) {
			BeanUtility.copyProperties(objData, caseData);
		}

		return objData;
	}

	/**
	 * 給付媒體回押註記 BATCHJOB
	 * 
	 * @param baBatchRecId 資料清單
	 * @param empNo        員工編號
	 */
	@SuppressWarnings("unchecked")
	public String doUpdatePaidMarkBJ(String[] baBatchRecId, String empNo, UserBean userData) {
		//baproperty
		String _ftpoutput_BK = PropertyHelper.getProperty("mgBankFileBK");
		String _ftpinput = PropertyHelper.getProperty("mgBankFileIn");//原本是讀取ftpClient.dirForDataFile
		String ipaddr = PropertyHelper.getProperty("mg.ip");
		String portno = PropertyHelper.getProperty("mg.port");
		String loginid = PropertyHelper.getProperty("sys.default.userid");
		String workDir = PropertyHelper.getProperty("rpt.path");
		
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("ipaddr", ipaddr);
		map.put("portno", portno);
		map.put("loginid", loginid);
		map.put("ftpdir", _ftpinput);
		
		String payCode = "";
		for (int i = 0; i < baBatchRecId.length; i++) {
			if (baBatchRecId[i].length() != 0) {
				String[] recIdFileName = baBatchRecId[i].split(";");
				BigDecimal batchRecId = new BigDecimal(recIdFileName[0]);
				String mfileName = recIdFileName[1];
				String mfileDate = DateUtility.getNowWestDateTime(true);
				
				// 取得fileName文字名檔案
				List<MgFile> mgFiles = mgMrUtil.query(map, mfileName, "", loginid);
//				// 取得資料文字檔內容
				if (mgFiles != null) { 
					// 取得資料文字檔內容(先將檔案下載下來在讀取內容)
					for(int k=0; i<mgFiles.size(); k++){
		            	String mgFileName = mgFiles.get(k).getName();
		            	log.debug("MgFile.mgFileName: " + mgFileName);

		            	if (StringUtils.endsWith(mgFileName, ".txt") || StringUtils.endsWith(mgFileName, ".TXT")) {
		            		// 下載遠端檔案
		            		String statusDownload = mgMrUtil.download(map, mgFileName, workDir, loginid);
		            		if (MgMrUtil.MG_MR_SUCCESS_CODE.equals(statusDownload)) {
		            			// 下載成功後開始讀取內容
		            			log.debug("download success.");
								
					            //下載到本機後位置
								String downloadFilepath = workDir + mfileName ;
								log.debug("downloadFilepath: " + downloadFilepath);
								
								String[] txtFile = getTxtFileContent(downloadFilepath);
								
								Babatchrec babatchrec = new Babatchrec();
								babatchrec.setBaBatchRecId(batchRecId);
								babatchrec.setProcStat("2"); // 2-排程處理中
								babatchrec.setBatchTyp("01");
								babatchrec.setStTime(DateUtility.getNowWestDateTime(true));
								babatchrec.setUpdTime(mfileDate);
								// 更新批次作業記錄檔
								babatchrecDao.updateUpdatePaidMarkBJ(babatchrec);
								
								log.info("給付媒體回押註記 自 " + Encode.forJava(mfileName) + " 讀取 " + txtFile.length + " 行資料");
								
								if (txtFile != null) {
									payCode = txtFile[1].substring(57, 58); // 文字檔第二行抓取
									
									// 批次新增資料
									bagivedtlDao.insertStringData(batchRecId.toPlainString(), mfileName, mfileDate, txtFile);
									
									log.info("給付媒體回押註記 新增 " + (txtFile.length - 2) + " 筆資料至 BAGIVEDTL");
									
									// 存檔處理完後將 MG 上的檔案搬移到 BK 目錄
									map.put("ftpdir", _ftpoutput_BK); // change dir
//									File downloadFile = new File(workDir, mfileName);
//									upload(map, loginid, downloadFile);
									// 確保工作目錄有效
//									String workDir = workDir;
									if (isValidDirectory(downloadFilepath)) {
//										File downloadFile = new File(workDir, mfileName);
									    upload(map, loginid, new File(downloadFilepath));
									}
								} // end if(txtFile != null)
								
		            		}	
		            	} else {
		            		log.info("mginput :  " + mfileName + " file not found");
		            	}
            		}
				}else {
					log.info("MgFile fileName: " + mfileName + " 查無資料");
				}

			}
		}
		
//		String payCode = "";
//		String procMsg = "";
//		for (int i = 0; i < baBatchRecId.length; i++) {
//			if (baBatchRecId[i].length() != 0) {
//				String[] recIdFileName = baBatchRecId[i].split(";");
//				BigDecimal batchRecId = new BigDecimal(recIdFileName[0]);
//				String mfileName = recIdFileName[1];
//				String mfileDate = DateUtility.getNowWestDateTime(true);
//
//				Babatchrec babatchrec = new Babatchrec();
//				babatchrec.setBaBatchRecId(batchRecId);
//				babatchrec.setProcStat("2"); // 2-排程處理中
//				babatchrec.setBatchTyp("01");
//				babatchrec.setStTime(DateUtility.getNowWestDateTime(true));
//				babatchrec.setUpdTime(mfileDate);
//				// 更新批次作業記錄檔
//				babatchrecDao.updateUpdatePaidMarkBJ(babatchrec);
//
//				// 抓取FTP上的檔案轉至BABATCHRECTMP
//				String[] txtFile = ftpClient.getTxtFileContent(mfileName);
//
//				log.info("給付媒體回押註記 自 " + Encode.forJava(mfileName) + " 讀取 " + txtFile.length + " 行資料");
//
//				if (txtFile != null) {
//					payCode = txtFile[1].substring(57, 58); // 文字檔第二行抓取
//
//					/*
//					 * List<BatchRecTemp> caseList = new ArrayList<BatchRecTemp>(); for (int j = 1;
//					 * j < txtFile.length - 1; j++) { if (StringUtils.isNotBlank(txtFile[j])) {
//					 * BatchRecTemp caseData = new BatchRecTemp();
//					 * caseData.setBaBatchRecId(batchRecId); caseData.setMfileName(fileName);
//					 * caseData.setMfileDate(DateUtility.getNowWestDateTime(true));
//					 * caseData.setSeqNo(Integer.toString(j));
//					 * caseData.setRc2(txtFile[j].substring(0, 1));
//					 * caseData.setSunit2(txtFile[j].substring(1, 9));
//					 * caseData.setHbank2(txtFile[j].substring(9, 12));
//					 * caseData.setBbank2(txtFile[j].substring(12, 17));
//					 * caseData.setTaTyp2(txtFile[j].substring(17, 20));
//					 * caseData.setPayDate2(txtFile[j].substring(20, 27));
//					 * caseData.setAccNo2(txtFile[j].substring(27, 41));
//					 * caseData.setAmt2(txtFile[j].substring(41, 55));
//					 * caseData.setStat2(txtFile[j].substring(55, 57));
//					 * caseData.setApNo2(txtFile[j].substring(57, 69));
//					 * caseData.setSeq2(txtFile[j].substring(69, 73));
//					 * caseData.setPayTyp2(txtFile[j].substring(73, 75));
//					 * caseData.setSpace2(txtFile[j].substring(75, 77));
//					 * caseData.setPayYm2(txtFile[j].substring(77, 82));
//					 * caseData.setIdN2(txtFile[j].substring(82, 92));
//					 * caseData.setName2(txtFile[j].substring(92, 107));
//					 * caseData.setInsKd2(txtFile[j].substring(107, 108));
//					 * caseData.setEmgMk2(txtFile[j].substring(108, 109));
//					 * caseData.setRpayDate2(txtFile[j].substring(109, 116));
//					 * caseData.setIssuYm2(txtFile[j].substring(116, 121));
//					 * caseData.setNc2(txtFile[j].substring(121, 125)); caseData.setCompareMk("0");
//					 * caseData.setUpdTime(DateUtility.getNowWestDateTime(true)); if (j == 2) {
//					 * payCode = txtFile[j].substring(57, 58); } caseList.add(caseData); } }
//					 */
//
//					// 批次新增資料
//					bagivedtlDao.insertStringData(batchRecId.toPlainString(), mfileName, mfileDate, txtFile);
//
//					// 呼叫StoreProcedure Name:Ba_ProcGiveDtl('1',qry_BABATCHREC.BABATCHRECID)
//					/*
//					 * procMsg = doUpdatePaidMarkStatus1("1", payCode, batchRecId, userData);
//					 */
//					log.info("給付媒體回押註記 新增 " + (txtFile.length - 2) + " 筆資料至 BAGIVEDTL");
//
//					// 存檔處理完後將 FTP 上的檔案搬移到備份目錄
//					ftpClient.backupFile(mfileName);
//				} // end if(txtFile != null)
//			}
//		}
		return payCode;
	}
	
	private boolean isValidDirectory(String directory) {
	    File dir = new File(directory);
	    return dir.exists() && dir.isDirectory();
	}

	
	/**
	 * 呼叫StoreProcedure Name:Ba_ProcGiveDtl('1',qry_BABATCHREC.BABATCHRECID)
	 * 
	 * @param payCode
	 * @param batchRecId
	 * @param userData
	 * @return
	 */
	public String doUpdatePaidMarkProcedure(String payCode, BigDecimal batchRecId, UserBean userData) {
		log.info("給付媒體回押註記 - 呼叫 Stored Procedure: payCode = " + StringUtils.trimToEmpty(payCode) + " batchRecId = "
				+ batchRecId.toPlainString());
		return doUpdatePaidMarkStatus1("1", payCode, batchRecId, userData);
	}

	/**
	 * 給付媒體回押註記 BATCHJOB ProFlag = 1
	 * 
	 * @param baBatchRecId 資料清單
	 * @param empNo        員工編號
	 */
	public void doUpdatePaidMarkBJDetailProFlag1(UserBean userData, UpdatePaidMarkBJCase caseData) {
		Babatchrec caseObj = new Babatchrec();
		caseObj.setProcUser(userData.getEmpNo());
		caseObj.setProcMemo(caseData.getProcMemo());
		caseObj.setBaBatchRecId(caseData.getBaBatchRecId());
		caseObj.setBatchTyp("01");
		babatchrecDao.updateUpdatePaidMarkBJDetailProFlag1(caseObj);
	}

	/**
	 * 給付媒體回押註記 BATCHJOB ProFlag = 2
	 * 
	 * @param baBatchRecId 資料清單
	 * @param empNo        員工編號
	 */
	public void doUpdatePaidMarkBJDetailProFlag2(UserBean userData, UpdatePaidMarkBJCase caseData) {
		Babatchrec caseObj = new Babatchrec();
		caseObj.setProcUser(userData.getEmpNo());
		caseObj.setProcMemo(caseData.getProcMemo());
		caseObj.setBaBatchRecId(caseData.getBaBatchRecId());
		caseObj.setBatchTyp("01");
		babatchrecDao.updateUpdatePaidMarkBJDetailProFlag2(caseObj);
	}

	/**
	 * 依傳入條件取得 批次作業記錄檔(<code>BABATCHREC</code>) for 收回作業資料
	 * 
	 * @param upTimeBeg 處理日期起日
	 * @param UpTimeEnd 處理日期迄日
	 * @return 內含<code>UpdatePaidMarkBJCase</code> 物件的List
	 */
	public List<ReturnWriteOffBJCase> getReturnWriteOffBJData(String upTimeBeg, String upTimeEnd) {
		List<Babatchrec> dataList = babatchrecDao.selectReturnWriteOffBJListBy(upTimeBeg, upTimeEnd, "02");
		List<ReturnWriteOffBJCase> caseList = new ArrayList<ReturnWriteOffBJCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Babatchrec babatchrec = dataList.get(i);
			ReturnWriteOffBJCase returnWriteOffBJCase = new ReturnWriteOffBJCase();
			BeanUtility.copyProperties(returnWriteOffBJCase, babatchrec);
			caseList.add(returnWriteOffBJCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 「查詢線上月核定作業於系統日期當日是否已經執行過。 for
	 * 線上月核定作業
	 * 
	 * @param payCode 給付別
	 * @return <code>BigDecimal</code>
	 */
	public BigDecimal selectCountCheckForBjMonthBy(String payCode) {
		return baonlinejobDao.selectCountCheckForBjMonthBy(payCode);
	}

	/**
	 * 依傳入條件取得 勞保年金批次作業檔(<code>BABATCHJOB</code>) 查詢該給付別於當次核定年月，是否已經執行過。 for 老年 遺屬
	 * 批次月處理作業
	 * 
	 * @param payCode  給付別
	 * @param issuYm   核定年月
	 * @param procType 月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定)
	 * @return <code>BigDecimal</code>
	 */
	public BigDecimal selectCountCheckForBjMonthBatchBy(String payCode, String issuYm, String procType) {
		return babatchjobDao.selectCountCheckForBjMonthBatchBy(payCode, issuYm, procType);
	}

	/**
	 * 依傳入條件取得 勞保年金批次作業檔(<code>BABATCHJOB</code>) 查詢該給付別於當次核定年月，是否已經執行過。 for 失能
	 * 批次月處理作業
	 * 
	 * @param payCode  給付別
	 * @param issuYm   核定年月
	 * @param procType 月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定)
	 * @param paySeqNo 失能年金(35、38案)欄位值為1、失能年金(36案)欄位值為2(其他給付別不用)
	 * @return <code>BigDecimal</code>
	 */
	public BigDecimal selectCountCheckForBjMonthBatchKBy(String payCode, String issuYm, String procType,
			String paySeqNo) {
		return babatchjobDao.selectCountCheckForBjMonthBatchKBy(payCode, issuYm, procType, paySeqNo);
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>)
	 * 「依照下列SQL逐筆檢核該案件是否已存在於「勞保年金線上作業檔_BAONLINEJOB」中。 for 線上月試核作業
	 * 
	 * @param apNo 受理編號
	 * @return <code>BigDecimal</code>
	 */
	public BigDecimal selectCountCheckForBjMonthCheckBy(String apNo) {
		return baonlinejobDao.selectCountCheckForBjMonthCheckBy(apNo);
	}

	/**
	 * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 線上月核定作業 依照下列SQL逐筆檢核該案件是否於該核定年月已經核付過。
	 * 
	 * @param apNo   受理編號
	 * @param issuYm 核定年月
	 * @return 內含 <code>BigDecimal</code> 物件
	 */
	public BigDecimal selectCountForMonthBy(String apNo, String issuYm) {
		return badaprDao.selectCountForMonthBy(apNo, issuYm);
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料清單 老年
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthLCase> selectMonthLDataListBy(String payCode, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthDataListBy(payCode, issuYm);
		List<MonthLCase> caseList = new ArrayList<MonthLCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthLCase monthLCase = new MonthLCase();
			BeanUtility.copyProperties(monthLCase, baonlinejob);
			caseList.add(monthLCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料清單 失能
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthKCase> selectMonthKDataListBy(String payCode, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthDataListBy(payCode, issuYm);
		List<MonthKCase> caseList = new ArrayList<MonthKCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthKCase monthKCase = new MonthKCase();
			BeanUtility.copyProperties(monthKCase, baonlinejob);
			caseList.add(monthKCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料清單 遺屬
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthSCase> selectMonthSDataListBy(String payCode, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthDataListBy(payCode, issuYm);
		List<MonthSCase> caseList = new ArrayList<MonthSCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthSCase monthSCase = new MonthSCase();
			BeanUtility.copyProperties(monthSCase, baonlinejob);
			caseList.add(monthSCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料清單 查詢 老年
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthLCase> selectMonthLQueryDataListBy(String payCode, String issuYm, String chkDate) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthQueryDataListBy(payCode, issuYm, chkDate);
		List<MonthLCase> caseList = new ArrayList<MonthLCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthLCase monthLCase = new MonthLCase();
			BeanUtility.copyProperties(monthLCase, baonlinejob);
			monthLCase.setIssuYm(DateUtility.changeWestYearMonthType(baonlinejob.getIssuYm()));
			monthLCase.setChkDate(DateUtility.changeDateType(baonlinejob.getChkDate()));
			caseList.add(monthLCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料清單 查詢 失能
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthKCase> selectMonthKQueryDataListBy(String payCode, String issuYm, String chkDate) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthQueryDataListBy(payCode, issuYm, chkDate);
		List<MonthKCase> caseList = new ArrayList<MonthKCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthKCase monthKCase = new MonthKCase();
			BeanUtility.copyProperties(monthKCase, baonlinejob);
			monthKCase.setIssuYm(DateUtility.changeWestYearMonthType(baonlinejob.getIssuYm()));
			monthKCase.setChkDate(DateUtility.changeDateType(baonlinejob.getChkDate()));
			caseList.add(monthKCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料清單 查詢 遺屬
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthSCase> selectMonthSQueryDataListBy(String payCode, String issuYm, String chkDate) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthQueryDataListBy(payCode, issuYm, chkDate);
		List<MonthSCase> caseList = new ArrayList<MonthSCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthSCase monthSCase = new MonthSCase();
			BeanUtility.copyProperties(monthSCase, baonlinejob);
			monthSCase.setIssuYm(DateUtility.changeWestYearMonthType(baonlinejob.getIssuYm()));
			monthSCase.setChkDate(DateUtility.changeDateType(baonlinejob.getChkDate()));
			caseList.add(monthSCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 編審後 資料清單 查詢 老年
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthLCase> selectMonthLApprovedDataListBy(String apNo, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthApprovedDataListBy(apNo, issuYm);
		List<MonthLCase> caseList = new ArrayList<MonthLCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthLCase monthLCase = new MonthLCase();
			BeanUtility.copyProperties(monthLCase, baonlinejob);
			monthLCase.setIssuYm(DateUtility.changeWestYearMonthType(baonlinejob.getIssuYm()));
			monthLCase.setChkDate(DateUtility.changeDateType(baonlinejob.getChkDate()));
			caseList.add(monthLCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 編審後 資料清單 查詢 失能
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthKCase> selectMonthKApprovedDataListBy(String apNo, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthApprovedDataListBy(apNo, issuYm);
		List<MonthKCase> caseList = new ArrayList<MonthKCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthKCase monthKCase = new MonthKCase();
			BeanUtility.copyProperties(monthKCase, baonlinejob);
			monthKCase.setIssuYm(DateUtility.changeWestYearMonthType(baonlinejob.getIssuYm()));
			monthKCase.setChkDate(DateUtility.changeDateType(baonlinejob.getChkDate()));
			caseList.add(monthKCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 編審後 資料清單 查詢 遺屬
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthSCase> selectMonthSApprovedDataListBy(String apNo, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthApprovedDataListBy(apNo, issuYm);
		List<MonthSCase> caseList = new ArrayList<MonthSCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthSCase monthSCase = new MonthSCase();
			BeanUtility.copyProperties(monthSCase, baonlinejob);
			monthSCase.setIssuYm(DateUtility.changeWestYearMonthType(baonlinejob.getIssuYm()));
			monthSCase.setChkDate(DateUtility.changeDateType(baonlinejob.getChkDate()));
			caseList.add(monthSCase);
		}
		return caseList;
	}

	/**
	 * DAO for 承辦人電子郵件檔 (<code>BAUSEREMAIL</code>)
	 * 
	 * @author Noctis
	 */
	public List<BaUserEmailCase> selectBauseremailDataListBy() {
		List<Bauseremail> dataList = bauseremailDao.selectBauseremailDataListBy();
		List<BaUserEmailCase> caseList = new ArrayList<BaUserEmailCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Bauseremail bauseremail = dataList.get(i);
			BaUserEmailCase baUserEmailCase = new BaUserEmailCase();
			BeanUtility.copyProperties(baUserEmailCase, bauseremail);
			caseList.add(baUserEmailCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 線上月核定作業
	 * 
	 * @param payCode 給付別
	 * @return
	 */
	public String selectMaxIssuYmBy(String payCode) {

		String maxIssuYm = bapaissudateDao.selectMaxIssuYmBy(payCode);
		return maxIssuYm;
	}

	/**
	 * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 線上月核定作業
	 * 
	 * @param payCode 給付別
	 * @return
	 */
	public BigDecimal selectCheckCountForBjMonthBy(String payCode) {

		BigDecimal checkCount = bapaissudateDao.selectCheckCountForBjMonthBy(payCode);
		return checkCount;
	}

	/**
 	* 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 失能國併勞批次產製媒體作業
	 * 
	 * @param payCode 給付別
	 * @return
	 */
	public BigDecimal selectCheckCountForBjMonthBy2(String payCode) {

		BigDecimal checkCount = bapaissudateDao.selectCheckCountForBjMonthBy2(payCode);
		return checkCount;
	}

	/**
	 * 依傳入條件取得 資料 for 補正核付作業
	 * 
	 * @param payCode 給付別
	 * @return
	 */
	public String qryChkIssuym(String payCode) {
		return bapaissudateDao.qryChkIssuym(payCode);
	}

	/**
	 * 依傳入條件取得 資料 for 補正核付作業
	 * 
	 * @param apNo    受理編號
	 * @param payCode 給付別
	 * @return
	 */
	public List<CompPaymentCase> qryUpdManPayList(String apNo, String issuYm) {
		List<CompPaymentCase> compPaymentCase = new ArrayList<CompPaymentCase>();
		List<Baappbase> baappbase = baappbaseDao.qryUpdManPayList(apNo, issuYm);
		for (Baappbase bs : baappbase) {
			CompPaymentCase returnCase = new CompPaymentCase();
			bs.setChkDate(DateUtility.changeDateType(bs.getChkDate()));
			bs.setAplPayDate(DateUtility.changeDateType(bs.getAplPayDate()));
			bs.setRemitDate(DateUtility.changeDateType(bs.getRemitDate()));
			BeanUtility.copyProperties(returnCase, bs);
			compPaymentCase.add(returnCase);
		}
		return compPaymentCase;
	}

	/**
	 * 依傳入條件取得 資料 for 老年、失能年金補正核付作業
	 * 
	 * @param apNo    受理編號
	 * @param payCode 給付別
	 * @return
	 */
	public List<CompPaymentCase> qryAddManPayList(String apNo, String issuYm) {
		List<CompPaymentCase> compPaymentCase = new ArrayList<CompPaymentCase>();
		List<Baappbase> baappbase = baappbaseDao.qryAddManPayList(apNo, issuYm);
		for (Baappbase bs : baappbase) {
			CompPaymentCase returnCase = new CompPaymentCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setEvtBrDate(DateUtility.changeDateToROCYear(returnCase.getEvtBrDate()));
			returnCase.setChkDate(DateUtility.changeDateToROCYear(returnCase.getChkDate()));
			returnCase.setAplPayDate(DateUtility.changeDateToROCYear(returnCase.getAplPayDate()));
			returnCase.setRemitDate(DateUtility.changeDateToROCYear(returnCase.getRemitDate()));
			compPaymentCase.add(returnCase);
		}
		return compPaymentCase;
	}

	/**
	 * 依傳入條件取得 資料 for 老年、失能年金補正核付作業
	 * 
	 * @param apNo    受理編號
	 * @param payCode 給付別
	 * @return
	 */
	public List<CompPaymentCase> qryAddManPayListForSurvivor(String apNo, String issuYm) {
		List<CompPaymentCase> compPaymentCase = new ArrayList<CompPaymentCase>();
		List<Baappbase> baappbase = baappbaseDao.qryAddManPayListForSurvivor(apNo, issuYm);
		for (Baappbase bs : baappbase) {
			CompPaymentCase returnCase = new CompPaymentCase();
			BeanUtility.copyProperties(returnCase, bs);
			returnCase.setEvtBrDate(DateUtility.changeDateToROCYear(returnCase.getEvtBrDate()));
			returnCase.setChkDate(DateUtility.changeDateToROCYear(returnCase.getChkDate()));
			returnCase.setAplPayDate(DateUtility.changeDateToROCYear(returnCase.getAplPayDate()));
			returnCase.setRemitDate(DateUtility.changeDateToROCYear(returnCase.getRemitDate()));
			compPaymentCase.add(returnCase);
		}
		return compPaymentCase;
	}
	
	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業 老年
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthL(MonthLCase caseData, String maxIssuYm, String status,
			UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setIssuYm(maxIssuYm);
		baonlinejob.setChkDate(DateUtility.getNowWestDate());
		baonlinejob.setProcType("2");
		baonlinejob.setStatus(status);
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());

		// 更新應收帳務記錄檔
		baonlinejobDao.updateBaonlinejobDataForMonth(baonlinejob);

	}

	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業 失能
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthK(MonthKCase caseData, String maxIssuYm, String status,
			UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setIssuYm(maxIssuYm);
		baonlinejob.setChkDate(DateUtility.getNowWestDate());
		baonlinejob.setProcType("2");
		baonlinejob.setStatus(status);
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());

		// 更新應收帳務記錄檔
		baonlinejobDao.updateBaonlinejobDataForMonth(baonlinejob);

	}

	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業 遺屬
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthS(MonthSCase caseData, String maxIssuYm, String status,
			UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setIssuYm(maxIssuYm);
		baonlinejob.setChkDate(DateUtility.getNowWestDate());
		baonlinejob.setProcType("2");
		baonlinejob.setStatus(status);
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());

		// 更新應收帳務記錄檔
		baonlinejobDao.updateBaonlinejobDataForMonth(baonlinejob);

	}

	/**
	 * 新增 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月試核作業 老年
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void insertBaonlinejobDataForMonthCheckL(MonthCheckLCase caseData, String issuYm, String status,
			UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setIssuYm(issuYm);
		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setChkDate("");
		baonlinejob.setAdjIssuYmMk(caseData.getAdjIssuYmMk());
		baonlinejob.setProcType("1");
		baonlinejob.setStatus(status);
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());
		baonlinejob.setCaseMk("");

		// 更新應收帳務記錄檔
		baonlinejobDao.insertBaonlinejobDataForMonthCheck(baonlinejob);

	}

	/**
	 * 新增 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月試核作業 失能
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void insertBaonlinejobDataForMonthCheckK(MonthCheckKCase caseData, String issuYm, String status,
			UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setIssuYm(issuYm);
		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setChkDate("");
		baonlinejob.setAdjIssuYmMk(caseData.getAdjIssuYmMk());
		baonlinejob.setProcType("1");
		baonlinejob.setStatus(status);
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());
		baonlinejob.setCaseMk("");

		// 更新應收帳務記錄檔
		baonlinejobDao.insertBaonlinejobDataForMonthCheck(baonlinejob);

	}

	/**
	 * 新增 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月試核作業 遺屬
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void insertBaonlinejobDataForMonthCheckS(MonthCheckSCase caseData, String issuYm, String status,
			UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setIssuYm(issuYm);
		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setChkDate("");
		baonlinejob.setAdjIssuYmMk(caseData.getAdjIssuYmMk());
		baonlinejob.setProcType("1");
		baonlinejob.setStatus(status);
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());
		baonlinejob.setCaseMk("");

		// 更新應收帳務記錄檔
		baonlinejobDao.insertBaonlinejobDataForMonthCheck(baonlinejob);

	}

	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月試核作業 老年
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthCheckL(MonthCheckLCase caseData, String issuYm, String status,
			UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setIssuYm(issuYm);
		baonlinejob.setAdjIssuYmMk(caseData.getAdjIssuYmMk());
		baonlinejob.setStatus(status);
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());

		// 更新應收帳務記錄檔
		baonlinejobDao.updateBaonlinejobDataForMonthCheck(baonlinejob);

	}

	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月試核作業 失能
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthCheckK(MonthCheckKCase caseData, String issuYm, String status,
			UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setIssuYm(issuYm);
		baonlinejob.setAdjIssuYmMk(caseData.getAdjIssuYmMk());
		baonlinejob.setStatus(status);
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());

		// 更新應收帳務記錄檔
		baonlinejobDao.updateBaonlinejobDataForMonthCheck(baonlinejob);

	}

	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月試核作業 遺屬
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthCheckS(MonthCheckSCase caseData, String issuYm, String status,
			UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setIssuYm(issuYm);
		baonlinejob.setAdjIssuYmMk(caseData.getAdjIssuYmMk());
		baonlinejob.setStatus(status);
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());

		// 更新應收帳務記錄檔
		baonlinejobDao.updateBaonlinejobDataForMonthCheck(baonlinejob);

	}

	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業 刪除 老年
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthLDelete(MonthLCase caseData, UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());
		baonlinejob.setCaseMk("D");

		// 更新勞保年金線上作業檔
		baonlinejobDao.updateBaonlinejobDataForMonthDelete(baonlinejob);

	}

	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業 刪除 失能
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthSDelete(MonthSCase caseData, UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());
		baonlinejob.setCaseMk("D");

		// 更新勞保年金線上作業檔
		baonlinejobDao.updateBaonlinejobDataForMonthDelete(baonlinejob);

	}

	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業 刪除 老年
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthKDelete(MonthKCase caseData, UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());
		baonlinejob.setCaseMk("D");

		// 更新勞保年金線上作業檔
		baonlinejobDao.updateBaonlinejobDataForMonthDelete(baonlinejob);

	}

	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業 刪除 老年
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthCheckLDelete(MonthCheckLCase caseData, UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());
		baonlinejob.setCaseMk("D");

		// 更新勞保年金線上作業檔
		baonlinejobDao.updateBaonlinejobDataForMonthDelete(baonlinejob);

	}

	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業 刪除 失能
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthCheckKDelete(MonthCheckKCase caseData, UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());
		baonlinejob.setCaseMk("D");

		// 更新勞保年金線上作業檔
		baonlinejobDao.updateBaonlinejobDataForMonthDelete(baonlinejob);

	}

	/**
	 * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業 刪除 遺屬
	 * 
	 * @param Baonlinejob 給付主檔
	 */
	public void updateBaonlinejobDataForMonthCheckSDelete(MonthCheckSCase caseData, UserBean userData) {

		Baonlinejob baonlinejob = new Baonlinejob();

		baonlinejob.setApNo(caseData.getApNo());
		baonlinejob.setBaonlinejobId(caseData.getBaonlinejobId());
		baonlinejob.setProcTime(DateUtility.getNowWestDateTime(true));
		baonlinejob.setProcEmpNo(userData.getEmpNo());
		baonlinejob.setProcDepId(userData.getDeptId());
		baonlinejob.setProcIp(userData.getLoginIP());
		baonlinejob.setCaseMk("D");

		// 更新勞保年金線上作業檔
		baonlinejobDao.updateBaonlinejobDataForMonthDelete(baonlinejob);

	}

	/**
	 * 依傳入條件取得 勞保年金主檔(<code>BAAPPBASE</code>) 資料清單 老年
	 * 
	 * @param payCode 給付別
	 * @param apNo    受理編號
	 * @return 內含 <code>Baappbase</code> 物件的 List
	 */
	public List<MonthCheckLCase> selectMonthCheckLDataListBy(String payCode, String apNo, String apNoA, String apNoB,
			String apNoC, String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI) {
		List<Baappbase> dataList = baappbaseDao.selectMonthCheckLDataListBy(payCode, apNo, apNoA, apNoB, apNoC, apNoD,
				apNoE, apNoF, apNoG, apNoH, apNoI);
		List<MonthCheckLCase> caseList = new ArrayList<MonthCheckLCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baappbase baappbase = dataList.get(i);
			MonthCheckLCase monthCheckLCase = new MonthCheckLCase();
			BeanUtility.copyProperties(monthCheckLCase, baappbase);
			caseList.add(monthCheckLCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金主檔(<code>BAAPPBASE</code>) 資料清單 失能
	 * 
	 * @param payCode 給付別
	 * @param apNo    受理編號
	 * @return 內含 <code>Baappbase</code> 物件的 List
	 */
	public List<MonthCheckKCase> selectMonthCheckKDataListBy(String payCode, String apNo, String apNoA, String apNoB,
			String apNoC, String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI) {
		List<Baappbase> dataList = baappbaseDao.selectMonthCheckKDataListBy(payCode, apNo, apNoA, apNoB, apNoC, apNoD,
				apNoE, apNoF, apNoG, apNoH, apNoI);
		List<MonthCheckKCase> caseList = new ArrayList<MonthCheckKCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baappbase baappbase = dataList.get(i);
			MonthCheckKCase monthCheckKCase = new MonthCheckKCase();
			BeanUtility.copyProperties(monthCheckKCase, baappbase);
			caseList.add(monthCheckKCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金主檔(<code>BAAPPBASE</code>) 資料清單 遺屬
	 * 
	 * @param payCode 給付別
	 * @param apNo    受理編號
	 * @return 內含 <code>Baappbase</code> 物件的 List
	 */
	public List<MonthCheckSCase> selectMonthCheckSDataListBy(String payCode, String apNo, String apNoA, String apNoB,
			String apNoC, String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI) {
		List<Baappbase> dataList = baappbaseDao.selectMonthCheckSDataListBy(payCode, apNo, apNoA, apNoB, apNoC, apNoD,
				apNoE, apNoF, apNoG, apNoH, apNoI);
		List<MonthCheckSCase> caseList = new ArrayList<MonthCheckSCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baappbase baappbase = dataList.get(i);
			MonthCheckSCase monthCheckSCase = new MonthCheckSCase();
			BeanUtility.copyProperties(monthCheckSCase, baappbase);
			caseList.add(monthCheckSCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金主檔(<code>BAAPPBASE</code>) 查詢每個受理案件及處理狀態 老年
	 * 
	 * @param payCode 給付別
	 * @param apNo    受理編號
	 * @return 內含 <code>Baappbase</code> 物件的 List
	 */
	public List<MonthCheckLCase> selectChkMonthLDataBy(String apNo, String apNoA, String apNoB, String apNoC,
			String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI) {
		List<Baappbase> dataList = baappbaseDao.selectChkMonthDataBy(apNo, apNoA, apNoB, apNoC, apNoD, apNoE, apNoF,
				apNoG, apNoH, apNoI);
		List<MonthCheckLCase> caseList = new ArrayList<MonthCheckLCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baappbase baappbase = dataList.get(i);
			MonthCheckLCase monthCheckLCase = new MonthCheckLCase();
			BeanUtility.copyProperties(monthCheckLCase, baappbase);
			caseList.add(monthCheckLCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金主檔(<code>BAAPPBASE</code>) 查詢每個受理案件及處理狀態 失能
	 * 
	 * @param payCode 給付別
	 * @param apNo    受理編號
	 * @return 內含 <code>Baappbase</code> 物件的 List
	 */
	public List<MonthCheckKCase> selectChkMonthKDataBy(String apNo, String apNoA, String apNoB, String apNoC,
			String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI) {
		List<Baappbase> dataList = baappbaseDao.selectChkMonthDataBy(apNo, apNoA, apNoB, apNoC, apNoD, apNoE, apNoF,
				apNoG, apNoH, apNoI);
		List<MonthCheckKCase> caseList = new ArrayList<MonthCheckKCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baappbase baappbase = dataList.get(i);
			MonthCheckKCase monthCheckKCase = new MonthCheckKCase();
			BeanUtility.copyProperties(monthCheckKCase, baappbase);
			caseList.add(monthCheckKCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金主檔(<code>BAAPPBASE</code>) 查詢每個受理案件及處理狀態 遺屬
	 * 
	 * @param payCode 給付別
	 * @param apNo    受理編號
	 * @return 內含 <code>Baappbase</code> 物件的 List
	 */
	public List<MonthCheckSCase> selectChkMonthSDataBy(String apNo, String apNoA, String apNoB, String apNoC,
			String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI) {
		List<Baappbase> dataList = baappbaseDao.selectChkMonthDataBy(apNo, apNoA, apNoB, apNoC, apNoD, apNoE, apNoF,
				apNoG, apNoH, apNoI);
		List<MonthCheckSCase> caseList = new ArrayList<MonthCheckSCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baappbase baappbase = dataList.get(i);
			MonthCheckSCase monthCheckSCase = new MonthCheckSCase();
			BeanUtility.copyProperties(monthCheckSCase, baappbase);
			caseList.add(monthCheckSCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 月試核 資料清單 查詢 老年
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthCheckLCase> selectMonthCheckLQueryDataListBy(String payCode, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthCheckQueryDataListBy(payCode, issuYm);
		List<MonthCheckLCase> caseList = new ArrayList<MonthCheckLCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthCheckLCase monthCheckLCase = new MonthCheckLCase();
			BeanUtility.copyProperties(monthCheckLCase, baonlinejob);
			caseList.add(monthCheckLCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 月試核 資料清單 查詢 失能
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthCheckKCase> selectMonthCheckKQueryDataListBy(String payCode, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthCheckQueryDataListBy(payCode, issuYm);
		List<MonthCheckKCase> caseList = new ArrayList<MonthCheckKCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthCheckKCase monthCheckKCase = new MonthCheckKCase();
			BeanUtility.copyProperties(monthCheckKCase, baonlinejob);
			caseList.add(monthCheckKCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 月試核 資料清單 查詢 遺屬
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthCheckSCase> selectMonthCheckSQueryDataListBy(String payCode, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthCheckQueryDataListBy(payCode, issuYm);
		List<MonthCheckSCase> caseList = new ArrayList<MonthCheckSCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthCheckSCase monthCheckSCase = new MonthCheckSCase();
			BeanUtility.copyProperties(monthCheckSCase, baonlinejob);
			caseList.add(monthCheckSCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 月試核 資料清單 編審後 老年
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthCheckLCase> selectMonthCheckLApprovedDataListBy(String apNo, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthCheckApprovedDataListBy(apNo, issuYm);
		List<MonthCheckLCase> caseList = new ArrayList<MonthCheckLCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthCheckLCase monthCheckLCase = new MonthCheckLCase();
			BeanUtility.copyProperties(monthCheckLCase, baonlinejob);
			caseList.add(monthCheckLCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 月試核 資料清單 編審後 失能
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthCheckKCase> selectMonthCheckKApprovedDataListBy(String apNo, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthCheckApprovedDataListBy(apNo, issuYm);
		List<MonthCheckKCase> caseList = new ArrayList<MonthCheckKCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthCheckKCase monthCheckKCase = new MonthCheckKCase();
			BeanUtility.copyProperties(monthCheckKCase, baonlinejob);
			caseList.add(monthCheckKCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 月試核 資料清單 編審後 遺屬
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @return 內含 <code>Baonlinejob</code> 物件的 List
	 */
	public List<MonthCheckSCase> selectMonthCheckSApprovedDataListBy(String apNo, String issuYm) {
		List<Baonlinejob> dataList = baonlinejobDao.selectMonthCheckApprovedDataListBy(apNo, issuYm);
		List<MonthCheckSCase> caseList = new ArrayList<MonthCheckSCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Baonlinejob baonlinejob = dataList.get(i);
			MonthCheckSCase monthCheckSCase = new MonthCheckSCase();
			BeanUtility.copyProperties(monthCheckSCase, baonlinejob);
			caseList.add(monthCheckSCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金批次作業檔(<code>BABATCHJOB</code>) 資料清單 老年
	 * 
	 * @param payCode  給付別
	 * @param issuYm   核定年月
	 * @param procType 月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定)
	 * @return 內含 <code>Babatchjob</code> 物件的 List
	 */
	public List<MonthBatchLCase> selectMonthBatchQueryDataListBy(String payCode, String issuYm, String baJobIdDate,
			String procType) {
		List<Babatchjob> dataList = babatchjobDao.selectMonthBatchQueryDataListBy(payCode, issuYm, baJobIdDate,
				procType);
		List<MonthBatchLCase> caseList = new ArrayList<MonthBatchLCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Babatchjob babatchjob = dataList.get(i);
			MonthBatchLCase monthBatchLCase = new MonthBatchLCase();
			BeanUtility.copyProperties(monthBatchLCase, babatchjob);
			caseList.add(monthBatchLCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金批次作業檔(<code>BABATCHJOB</code>) 資料清單 失能
	 * 
	 * @param payCode  給付別
	 * @param issuYm   核定年月
	 * @param procType 月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定)
	 * @param paySeqNo 失能年金(35、38案)欄位值為1、失能年金(36案)欄位值為2(其他給付別不用)
	 * @return 內含 <code>Babatchjob</code> 物件的 List
	 */
	public List<MonthBatchKCase> selectMonthBatchKQueryDataListBy(String payCode, String issuYm, String baJobIdDate,
			String procType, String paySeqNo) {
		List<Babatchjob> dataList = babatchjobDao.selectMonthBatchKQueryDataListBy(payCode, issuYm, baJobIdDate,
				procType, paySeqNo);
		List<MonthBatchKCase> caseList = new ArrayList<MonthBatchKCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Babatchjob babatchjob = dataList.get(i);
			MonthBatchKCase monthBatchKCase = new MonthBatchKCase();
			BeanUtility.copyProperties(monthBatchKCase, babatchjob);
			caseList.add(monthBatchKCase);
		}
		return caseList;
	}

	/**
	 * 依傳入條件取得 勞保年金批次作業檔(<code>BABATCHJOB</code>) 資料清單 遺屬
	 * 
	 * @param payCode  給付別
	 * @param issuYm   核定年月
	 * @param procType 月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定)
	 * @return 內含 <code>Babatchjob</code> 物件的 List
	 */
	public List<MonthBatchSCase> selectMonthBatchSQueryDataListBy(String payCode, String issuYm, String baJobIdDate,
			String procType) {
		List<Babatchjob> dataList = babatchjobDao.selectMonthBatchQueryDataListBy(payCode, issuYm, baJobIdDate,
				procType);
		List<MonthBatchSCase> caseList = new ArrayList<MonthBatchSCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Babatchjob babatchjob = dataList.get(i);
			MonthBatchSCase monthBatchSCase = new MonthBatchSCase();
			BeanUtility.copyProperties(monthBatchSCase, babatchjob);
			caseList.add(monthBatchSCase);
		}
		return caseList;
	}

	/**
	 * 新增 勞保年金批次作業檔(<code>BABATCHJOB</code>) for 批次月處理作業 老年 遺屬
	 * 
	 * @param Babatchjob 給付主檔
	 */
	public void insertBabatchjobDataForMonthBatch(String issuYm, String payCode, String procType, UserBean userData) {

		Babatchjob babatchjob = new Babatchjob();

		babatchjob.setBaJobId(userData.getEmpNo() + DateUtility.getNowWestDateTime(true) + "J");
		babatchjob.setIssuYm(issuYm);
		babatchjob.setChkDate("");
		babatchjob.setPayCode(payCode);
		babatchjob.setProcEmpNo(userData.getEmpNo());
		babatchjob.setProcDeptId(userData.getDeptId());
		babatchjob.setProcIp(userData.getLoginIP());
		babatchjob.setProcBegTime(DateUtility.getNowWestDateTime(true));
		babatchjob.setProcEndTime("");
		babatchjob.setProcType(procType);
		babatchjob.setStatus("W");
		babatchjob.setPaySeqNo("1");

		// 更新應收帳務記錄檔
		babatchjobDao.insertBabatchjobDataForMonthBatch(babatchjob);

	}

	/**
	 * 新增 勞保年金批次作業檔(<code>BABATCHJOB</code>) for 批次月處理作業 失能
	 * 
	 * @param Babatchjob 給付主檔
	 */
	public void insertBabatchjobDataForMonthBatchK(String issuYm, String payCode, String procType, String paySeqNo,
			UserBean userData) {

		Babatchjob babatchjob = new Babatchjob();

		babatchjob.setBaJobId(userData.getEmpNo() + DateUtility.getNowWestDateTime(true) + "J");
		babatchjob.setIssuYm(issuYm);
		babatchjob.setChkDate("");
		babatchjob.setPayCode(payCode);
		babatchjob.setProcEmpNo(userData.getEmpNo());
		babatchjob.setProcDeptId(userData.getDeptId());
		babatchjob.setProcIp(userData.getLoginIP());
		babatchjob.setProcBegTime(DateUtility.getNowWestDateTime(true));
		babatchjob.setProcEndTime("");
		babatchjob.setProcType(procType);
		babatchjob.setStatus("W");
		babatchjob.setPaySeqNo(paySeqNo);

		// 更新應收帳務記錄檔
		babatchjobDao.insertBabatchjobDataForMonthBatchK(babatchjob);

	}

	/**
	 * 依傳入條件取得 勞保年金批次作業明細檔(<code>BABATCHJOBDTL</code>) 資料清單
	 * 
	 * @param baJobId 資料列編號(JOBID)
	 * @return 內含 <code>Babatchjobdtl</code> 物件的 List
	 */
	public List<MonthBatchDtlCase> selectBabatchjobdtlDataForBjMonthBatchBy(String baJobId) {
		List<Babatchjobdtl> dataList = babatchjobdtlDao.selectBabatchjobdtlDataForBjMonthBatchBy(baJobId);
		List<MonthBatchDtlCase> caseList = new ArrayList<MonthBatchDtlCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Babatchjobdtl babatchjobdtl = dataList.get(i);
			MonthBatchDtlCase monthBatchDtlCase = new MonthBatchDtlCase();
			BeanUtility.copyProperties(monthBatchDtlCase, babatchjobdtl);
			caseList.add(monthBatchDtlCase);
		}
		return caseList;
	}

	/**
	 * 收回作業 BATCHJOB
	 * 
	 * @param baBatchRecId 資料清單
	 * @param empNo        員工編號
	 */
	public void doReturnWriteOffBJ(String[] baBatchRecId, String empNo) {
		for (int i = 0; i < baBatchRecId.length; i++) {
			String[] recIdFileName = baBatchRecId[i].split(";");
			BigDecimal batchRecId = new BigDecimal(recIdFileName[0]);
			String fileName = recIdFileName[1];

			Babatchrec babatchrec = new Babatchrec();
			babatchrec.setBaBatchRecId(batchRecId);
			babatchrec.setProcStat("3"); // 已處理
			babatchrec.setBatchTyp("02");
			babatchrec.setProcUser(empNo);
			babatchrec.setProcTime(DateUtility.getNowWestDateTime(true));
			// 更新批次作業記錄檔
			babatchrecDao.updateReturnWriteOffBJ(babatchrec);

			// 抓取FTP上的檔案轉至BABATCHRECTMP
			String[] txtFile = ftpClient.getTxtFileContent(fileName);

			if (txtFile != null) {
				for (int j = 0; j < txtFile.length; j++) {
					BatchRecTemp caseData = new BatchRecTemp();
					caseData.setBaBatchRecId(batchRecId);
					caseData.setDataNo(new BigDecimal(j + 1));
					caseData.setDataDesc(txtFile[j]);

					babatchrecDao.insertBaBatchRecTmpData(caseData);
				}

				// 存檔處理完後將 FTP 上的檔案搬移到備份目錄
				ftpClient.backupFile(fileName);
			} // end if(txtFile != null)
		}
	}

	/**
	 * 給付媒體回押註記 StoreProcedure Name:Ba_ProcGiveDtl('1',qry_BABATCHREC.BABATCHRECID)
	 * 
	 * @param baBatchRecId 資料清單
	 * @param procTyp      回押註記
	 */
	public String doUpdatePaidMarkStatus1(String procTyp, String payCode, BigDecimal batchRecId, UserBean userData) {
		// 呼叫StoreProcedure Name:Ba_ProcGiveDtl('1',qry_BABATCHREC.BABATCHRECID)
		String procMsg = bagivedtlDao.chkPaidMarkStatus(procTyp, payCode, batchRecId, userData);
		log.error(procMsg);
		return procMsg;
	}

	/**
	 * 給付媒體回押註記 StoreProcedure Name:Ba_ProcGiveDtl('2',qry_BABATCHREC.BABATCHRECID)
	 * 
	 * @param baBatchRecId 資料清單
	 * @param procTyp      回押註記
	 */
	public String doUpdatePaidMarkStatus(String procTyp, String payCode, BigDecimal baBatchRecId, UserBean userData) {
		// 呼叫StoreProcedure Name:Ba_ProcGiveDtl('2',qry_BABATCHREC.BABATCHRECID)
		String procMsg = bagivedtlDao.updatePaidMarkStatus(procTyp, payCode, baBatchRecId, userData);
		log.error(procMsg);
		return procMsg;
	}

	/**
	 * 自 FTP 取得指定 目錄 及 檔名 的pdf檔 (指定目錄)
	 * 
	 * @param fileName          欲取得的檔名
	 * @param dirForTxtDownload 目錄名
	 * @return 回傳 String Array, 每行分別為 Array 中的一個 String 物件
	 */
	public ByteArrayOutputStream getZipFile(String fileName, String dirForDownload) {
		return ftpApClient.getZipFile(fileName, dirForDownload);
	}

	public String selectUpdatePaidMarkBJPayCodeBy(String payDate, BigDecimal baBatchRecId, String taTyp) {
		return bagivedtlDao.selectUpdatePaidMarkBJPayCodeBy(payDate, baBatchRecId, taTyp);
	}

	/**
	 * 依傳入條件取得該核定日期、核定年月、給付別的對應下載清單
	 * 
	 * @param payCode  給付別
	 * @param issuYm   核定年月
	 * @param chkDate  核定日期
	 * @param paySeqNo 欄位值(皆預設為1第一次出媒體)
	 * @return
	 */
	public List<MediaOnlineDownloadCase> getMedaiFileOnline(String payCode, String issuYm, String chkDate,
			String paySeqNo) {
		List<Bagivetmpdtl> list = bagivetmpdtlDao.getMedaiFileOnlineDownloadDataBy(payCode, issuYm, chkDate, paySeqNo);
		List<MediaOnlineDownloadCase> returnList = new ArrayList<MediaOnlineDownloadCase>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Bagivetmpdtl obj = list.get(i);
				MediaOnlineDownloadCase caseObj = new MediaOnlineDownloadCase();
				caseObj.setMfileDate(obj.getMfileDate());// 媒體檔案日期
				caseObj.setMfileName(obj.getMfileName());// 媒體檔案名稱
				caseObj.setRowNum(i + 1);// 序號
				caseObj.setFtpSeq(obj.getFtpSeq());
				returnList.add(caseObj);
			}
			return returnList;
		} else {
			return null;
		}

	}

	/**
	 * 查詢勞保年金線上產製媒體排程目前佇列中相同條件的數量(for 老年、遺屬)
	 * 
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @param payCode 給付別
	 */
	public Integer doQueryBatchJobStatus(String issuYm, String chkDate, String payCode, String procType) {
		return babatchjobDao.selectBatchJobStatus(issuYm, chkDate, payCode, procType);

	}

	/**
	 * 查詢勞保年金線上產製媒體排程目前佇列中相同條件的數量(for 失能)
	 * 
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @param payCode 給付別
	 */
	public Integer doQueryBatchJobStatusForDisabled(String issuYm, String chkDate, String payCode, String paySeqNo,
			String procType) {
		return babatchjobDao.selectBatchJobStatusForDisabled(issuYm, chkDate, payCode, paySeqNo, procType);

	}

	/**
	 * 查詢年金線上產製媒體排程目前進度
	 * 
	 * @param procType 處理類型
	 * @param issuYm   核定年月
	 * @param chkDate  核定日期
	 * @param payCode  給付別
	 * @param paySeqNo 欄位值預設為1
	 */
	public List<MediaOnlineProgressCase> getProgressStep(String procType, String issuYm, String chkDate, String payCode,
			String paySeqNo) {
		List<Babatchjob> dataList = babatchjobDao.selectNowProgressSteps(procType, issuYm, chkDate, payCode, paySeqNo);
		List<MediaOnlineProgressCase> caseList = new ArrayList<MediaOnlineProgressCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Babatchjob babatchjob = dataList.get(i);
			MediaOnlineProgressCase returnCase = new MediaOnlineProgressCase();
			returnCase.setBaJobId(babatchjob.getBaJobId());
			returnCase.setChkDate(DateUtility.changeDateType(babatchjob.getChkDate()));
			returnCase.setIssuYm(DateUtility.changeWestYearMonthType(babatchjob.getIssuYm()));
			if (StringUtils.equals(babatchjob.getPayCode(), "L")) {
				returnCase.setPayCode("老年");
			} else if (StringUtils.equals(babatchjob.getPayCode(), "K")) {
				returnCase.setPayCode("失能");
			} else if (StringUtils.equals(babatchjob.getPayCode(), "S")) {
				returnCase.setPayCode("遺屬");
			} else {
				returnCase.setPayCode("");
			}
			if (StringUtils.equals(babatchjob.getStatus(), "E")) {
				returnCase.setStatus("執行結束");
			} else if (StringUtils.equals(babatchjob.getStatus(), "N")) {
				returnCase.setStatus("媒體產製錯誤");
			} else if (StringUtils.equals(babatchjob.getStatus(), "W")) {
				returnCase.setStatus("等待中");
			} else {
				returnCase.setStatus("執行中");
			}

			returnCase.setRowNum(i + 1);
			if (StringUtils.equals(procType, "1") || StringUtils.equals(procType, "5")) {
				returnCase.setProcType("產製媒體");
			} else if (StringUtils.equals(procType, "10")) {
				returnCase.setProcType("第一次改匯核付");
			} else if (StringUtils.equals(procType, "11")) {
				returnCase.setProcType("第二次改匯核付");
			}
			caseList.add(returnCase);
		}
		if (caseList == null || caseList.size() <= 0) {
			return null;
		} else {
			return caseList;
		}

	}

	/**
	 * 查詢年金線上產製媒體排程-目前進度查詢(明細)
	 * 
	 * @param baJobId 資料列編號(JOBID)
	 */
	public List<MediaOnlineProgressDtlCase> getProgressStepDtl(String baJobId) {
		List<Babatchjobdtl> dataList = babatchjobdtlDao.selectNowProgressStepsDtl(baJobId);
		List<MediaOnlineProgressDtlCase> caseList = new ArrayList<MediaOnlineProgressDtlCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Babatchjobdtl babatchjobdtl = dataList.get(i);
			MediaOnlineProgressDtlCase returnCase = new MediaOnlineProgressDtlCase();
			// 0:成功 1:失敗
			if (StringUtils.isNotBlank(babatchjobdtl.getFlag())) {
				if (StringUtils.equals(babatchjobdtl.getFlag(), "1")) {
					returnCase.setFlag("失敗");
				} else {
					returnCase.setFlag("成功");
				}
			}
			returnCase.setFlagMsg(babatchjobdtl.getFlagMsg());
			if (StringUtils.isNotBlank(babatchjobdtl.getFlagTime())) {
				returnCase.setFlagTime(DateUtility
						.formatChineseDateTimeString(StringUtils.substring(babatchjobdtl.getFlagTime(), 0, 14), true));
			}
			returnCase.setRowNum(i + 1);
			caseList.add(returnCase);
		}
		if (caseList == null || caseList.size() <= 0) {
			return null;
		} else {
			return caseList;
		}

	}

	/**
	 * 依傳入條件取得 月核案件檢核確認檔 for 批次產製媒體檔
	 * 
	 * @param baappbaseId 資料列編號
	 * @param procStat    處理狀態
	 * @return
	 */
	public String selectDataCountBy(String payCode, String issuYm) {
		return barvconfirmDao.selectDataCountBy(payCode, issuYm);
	}

	/**
	 * 勞保年金線上產製媒體排程將作業計入排程
	 * 
	 * @param issuYm      核定年月
	 * @param chkDate     核定日期
	 * @param payCode     給付別
	 * @param procEmpNo   執行作業人員員工編號
	 * @param procDeptId  執行作業人員單位代碼
	 * @param procIp      執行作業人員IP
	 * @param procBegTime 處裡起始日期時間
	 * @param procType    處理類型
	 * @param status      處理狀態
	 * @param paySeqNo    欄位值
	 */
	public void doScheduleBatchJob(String baJobId, String issuYm, String chkDate, String payCode, String procEmpNo,
			String procDeptId, String procIp, String procBegTime, String procType, String status, String paySeqNo) {
		Babatchjob babatchjob = new Babatchjob();
		babatchjob.setBaJobId(baJobId);
		babatchjob.setIssuYm(issuYm);
		babatchjob.setChkDate(chkDate);
		babatchjob.setPayCode(payCode);
		babatchjob.setProcEmpNo(procEmpNo);
		babatchjob.setProcDeptId(procDeptId);
		babatchjob.setProcIp(procIp);
		babatchjob.setProcType(procType);
		babatchjob.setStatus(status);
		babatchjob.setPaySeqNo(paySeqNo);
		babatchjobDao.insertBatchJobM(babatchjob);
	}

	/**
	 * 勞保年金線上產製媒體排程將作業-更新排程作業狀態
	 * 
	 * @param baJobId         資料列編號(jobid)
	 * @param nowWestDateTime 處理時間
	 * @param status          處理狀態
	 */
	public void updateBaBatchJobStatus(String baJobId, String nowWestDateTime, String status, String procType) {
		Babatchjob babatchjob = new Babatchjob();
		babatchjob.setBaJobId(baJobId);
		if (StringUtils.equals(status, "N") || StringUtils.equals(status, "E")) {
			babatchjob.setProcEndTime(nowWestDateTime);
			babatchjob.setProcType(procType);
		} else {
			babatchjob.setProcBegTime(nowWestDateTime);
		}
		babatchjob.setStatus(status);
		babatchjobDao.updateBaBatchJobStatus(babatchjob);

	}

	/**
	 * 批次處理 - 批次程式執行作業 - 新增排程作業狀態
	 * 
	 * @param baJobId         資料列編號(jobid)
	 * @param nowWestDateTime 處理時間
	 * @param sReturnResult   回傳訊息
	 */
	public void insertBaBatchJobDtl(String baJobId, String nowWestDateTime, String sReturnResult) {

		Babatchjobdtl babatchjobdtl = new Babatchjobdtl();
		babatchjobdtl.setBaJobId(baJobId);
		babatchjobdtl.setFlagMsg(sReturnResult);
		babatchjobdtl.setFlagTime(nowWestDateTime);

		babatchjobdtlDao.insertBaBatchJobDtl(babatchjobdtl);
	}

	/**
	 * 勞保年金線上產製媒體完成-發送信件
	 * 
	 * @param payCode
	 * @param chkDate
	 * @param result
	 * @param email
	 */
	public void sendBatchMediaProduce(String payCode, String chkDate, String issuYm) {
		try {
			List<Bauseremail> dataList = bauseremailDao.selectBaMediaUserEmailDataListBy(payCode);
			if (dataList.size() > 0 && dataList != null) {
				for (int i = 0; i < dataList.size(); i++) {
					Bauseremail bauseremail = dataList.get(i);
					// 發送帳號啟用信
					mailHelper.sendMediaSendMail(payCode, chkDate, issuYm, bauseremail.getEmailAddr());
				}
			}
		} catch (Exception e) {
			log.error("發送產製媒體信失敗  (mailTo: " + ") : " + ExceptionUtility.getStackTrace(e));
		}

	}

	public void sendBc516ErrorMail(String payCode, String apNo, String errMsg) {
		try {
			List<Bauseremail> dataList = bauseremailDao.selectBaMediaUserEmailDataListBy(payCode);
			if (dataList.size() > 0 && dataList != null) {
				for (int i = 0; i < dataList.size(); i++) {
					Bauseremail bauseremail = dataList.get(i);
					// 發送執行BC 516案受理失敗信
					mailHelper.sendBc516ErrorMail(payCode, apNo, errMsg, bauseremail.getEmailAddr());
				}
			}
		} catch (Exception e) {
			log.error("發送執行BC 516案受理失敗信錯誤  (mailTo: " + ") : " + ExceptionUtility.getStackTrace(e));
		}
	}

	/**
	 * 產生媒體檔案(call storeprocedure)
	 * 
	 * @param batchJobId 資料列編號(JOBID)
	 * @param issuTyp    核付處理類別
	 * @param issuYm     核定年月
	 * @param chkDate    核定日期
	 * @param payCode    給付別
	 * @param mtestMk    處理註記
	 * @param procEmpNo  執行作業人員員工編號
	 * @param procDeptId 執行作業人員單位代碼
	 * @param procIp     執行作業人員IP
	 * @param paySeqNo   欄位值：1為第一次出媒體
	 * @return
	 */
	public String genBaPayFile(String batchJobId, String issuTyp, String issuYm, String chkDate, String payCode,
			String mtestMk, String procEmpNo, String procDeptId, String procIp, String paySeqNo, String procType) {
		// 呼叫StoreProcedure Name:SP_BA_genBAPayFile
		String outFlag = babatchjobDao.genBaPayFile(batchJobId, issuTyp, issuYm, chkDate, payCode, mtestMk, procEmpNo,
				procDeptId, procIp, paySeqNo, procType);
		return outFlag;
	}

	/**
	 * 取出勞保年金媒體作業目前要處理的工作
	 * 
	 * @param baJobId 資料列編號(JOBID)
	 */
	public Babatchjob getBaBatchJobData(String baJobId) {
		Babatchjob baBatchJobData = babatchjobDao.getBaBatchJobData(baJobId);
		return baBatchJobData;
	}

	/**
	 * 勞保年金線上月核定完成-發送信件
	 * 
	 * @param payCode
	 * @param chkDate
	 * @param result
	 * @param email
	 */
	public void sendMonthMail(String payCode, String issuYm) {
		try {
			String payCodeStr = "";
			if (payCode.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_L)) {
				payCodeStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
			} else if (payCode.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_K)) {
				payCodeStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
			} else if (payCode.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S)) {
				payCodeStr = ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
			}

			List<Bauseremail> dataList = bauseremailDao.selectBauseremailDataListBy();

			if (dataList.size() > 0 && dataList != null) {
				for (int i = 0; i < dataList.size(); i++) {
					Bauseremail bauseremail = dataList.get(i);
					// 發送帳號啟用信
					mailHelper.sendMonthMail(payCodeStr, DateUtility.changeWestYearMonthType(issuYm),
							DateUtility.getNowChineseDate(), bauseremail.getEmailAddr());
				}
			}
		} catch (Exception e) {
			log.debug("發送線上月核定完成信失敗  (mailTo: " + ") : " + e.getStackTrace());
		}

	}

	/**
	 * 依傳入條件取得 月核案件檢核確認檔 (<code>BARVCONFIRM</code>) 的資料
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @return <code>List<CheckJobCase></code> 物件
	 */
	public List<CheckJobCase> selectBarvconfirmDataBy(String payCode, String issuYm) {
		List<Barvconfirm> dataList = barvconfirmDao.selectBarvconfirmDataBy(payCode, issuYm);
		List<CheckJobCase> caseList = new ArrayList<CheckJobCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Barvconfirm barvconfirm = dataList.get(i);
			CheckJobCase checkJobCase = new CheckJobCase();
			BeanUtility.copyProperties(checkJobCase, barvconfirm);
			checkJobCase.setIssuYm(DateUtility.changeWestYearMonthType(checkJobCase.getIssuYm()));
			caseList.add(checkJobCase);
		}
		return caseList;
	}

	/**
	 * 依傳入的 <code>barvconfirm</code> 資料更新至 月核案件檢核確認檔 (<code>BARVCONFIRM</code>)<br>
	 * for : 批次作業 - 檢核確認作業<br>
	 * 
	 * @param caseData <code>barvconfirm</code>
	 */
	public void updateBarvconfirmData(List<CheckJobCase> caseList) {
		log.debug("執行 BjService.updateBarvconfirmData() 開始 ...");

		for (CheckJobCase caseData : caseList) {

			Barvconfirm barvconfirm = new Barvconfirm();

			BeanUtility.copyProperties(barvconfirm, caseData);
			barvconfirm.setIssuYm(DateUtility.changeChineseYearMonthType(barvconfirm.getIssuYm()));

			barvconfirmDao.updateBarvconfirmData(barvconfirm);
		}
		// // Update MMAPLOG
		// if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) {
		// // 紀錄 Log
		// FrameworkLogUtil.doUpdateLog(babasicamtDataBefore,babasicamtDataAfter);
		// }

		log.debug("執行 MaintService.updateCpiRecMaintData() 完成 ...");
	}

	/**
	 * 依傳入條件取得 月核案件檢核檔 (<code>BARVCASE</code>) 的資料
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param rvType
	 * @return <code>List<CheckJobCase></code> 物件
	 */
	public List<CheckJobRptCase> selectBarvcaseDataBy(String payCode, String issuYm, String rvType) {
		List<Barvcase> dataList = barvcaseDao.selectBarvcaseDataBy(payCode, issuYm, rvType);
		List<CheckJobRptCase> caseList = new ArrayList<CheckJobRptCase>();
		for (int i = 0; i < dataList.size(); i++) {
			Barvcase barvcase = dataList.get(i);
			CheckJobRptCase checkJobRptCase = new CheckJobRptCase();
			BeanUtility.copyProperties(checkJobRptCase, barvcase);
			caseList.add(checkJobRptCase);
		}
		return caseList;
	}

	/**
	 * 批次程式執行作業
	 * 
	 * @param baJobId
	 * @param procedureName
	 * @param seqNo
	 * @param paramValue
	 * @param creUser
	 * @param creDatetime
	 */
	public void saveProcedureData(String baJobId, String procedureName, String[] sParamsList, String creUser,
			String creDatetime) {
		String seqNo = "";

		List<Baproceduredtl> list = baproceduredtlDao.selectParametersNameBy(procedureName);

		// for (int i = 0; i < list.size(); i++) {
		// Baproceduredtl obj = list.get(i);
		// Trans2OverdueReceivableBJDetailCase caseObj = new
		// Trans2OverdueReceivableBJDetailCase();
		// BeanUtility.copyProperties(caseObj, obj);
		// returnList.add(caseObj);
		// }

		for (int i = 0; i < sParamsList.length; i++) {
			Baproceduredata baproceduredata = new Baproceduredata();

			baproceduredata.setBaJobId(baJobId);
			baproceduredata.setprocedureName(procedureName);

			if (String.valueOf(i).length() == 1) {
				seqNo = "00" + String.valueOf(i);
			} else if (String.valueOf(i).length() == 2) {
				seqNo = "0" + String.valueOf(i);
			} else {
				seqNo = String.valueOf(i);
			}

			baproceduredata.setSeqNo(seqNo);
			baproceduredata.setParameterName(list.get(i).getParameterName());
			baproceduredata.setParamValue(sParamsList[i].toString());
			baproceduredata.setCreUser(creUser);
			baproceduredata.setCreDatetime(creDatetime);

			baproceduredataDao.insertProcedureData(baproceduredata);
		}
	}

	/**
	 * 依傳入的條件取得 月核定合格清冊 的資料（老年）
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @return
	 */
	public List<RunProcedureCase> getRunProcedureDataBy(String jobId) {
		List<RunProcedureCase> caseList = new ArrayList<RunProcedureCase>();
		// 取得報表資料
		List<Baproceduredata> dataList = baproceduredataDao.getRunProcedureDataBy(jobId);

		for (Baproceduredata rptData : dataList) {
			RunProcedureCase caseData = new RunProcedureCase();
			BeanUtility.copyProperties(caseData, rptData);

			caseList.add(caseData);
		}

		return caseList;
	}

	/**
	 * 查詢 Procedure 排程目前進度
	 * 
	 * @param procType  處理類型
	 * @param startDate 處理起始日期
	 * @param endDate   處理結束日期
	 */
	public List<QryProcedureCase> getProcedureProgressStep(String procType, String startDate, String endDate) {
		List<Babatchjob> dataList = babatchjobDao.selectProcedureProgressSteps(procType, startDate, endDate);
		List<QryProcedureCase> caseList = new ArrayList<QryProcedureCase>();

		for (int i = 0; i < dataList.size(); i++) {
			Babatchjob babatchjob = dataList.get(i);
			QryProcedureCase returnCase = new QryProcedureCase();

			returnCase.setBaJobId(babatchjob.getBaJobId());
			// returnCase.setChkDate(DateUtility.changeDateType(babatchjob.getChkDate()));
			// returnCase.setIssuYm(DateUtility.changeWestYearMonthType(babatchjob.getIssuYm()));

			returnCase.setProcBegTime(babatchjob.getProcBegTime());
			returnCase.setProcEndTime(babatchjob.getProcEndTime());
			returnCase.setProcedureName(babatchjob.getProcedureName());
			returnCase.setFlagMsg(babatchjob.getFlagMsg());

			if (StringUtils.equals(babatchjob.getStatus(), "E")) {
				returnCase.setStatus("執行結束");
			} else if (StringUtils.equals(babatchjob.getStatus(), "N")) {
				returnCase.setStatus("Stored Procedure 執行錯誤");
			} else if (StringUtils.equals(babatchjob.getStatus(), "W")) {
				returnCase.setStatus("等待中");
			} else {
				returnCase.setStatus("執行中");
			}

			returnCase.setRowNum(i + 1);
			if (StringUtils.equals(procType, "9")) {
				returnCase.setProcType("Stored Procedure");
			}
			caseList.add(returnCase);
		}
		if (caseList == null || caseList.size() <= 0) {
			return null;
		} else {
			return caseList;
		}

	}

	/**
	 * 呼叫 Stored Procedure
	 * 
	 * @param famIdnNo   眷屬身分證號
	 * @param evtJobDate 事故日期
	 */
	public Map<String, String> runProcedure(List<RunProcedureCase> caseData) {
		// 呼叫StoreProcedure
		Map<String, String> map = runprocedurejobDao.callProcedure(caseData);

		// baproceduredataDao.doCallProcedure(sProcedure, params);
		return map;

	}

	/**
	 * BABA0M241A.jsp BABA0M251A.jsp 更新 BADAPR 的資料
	 */
	public void updManPayDataList(UserBean userData, CompPaymentCase compPaymentCase) {
		badaprDao.updManPayDataList(userData, compPaymentCase);
	}

	/**
	 * BABA0M241A.jsp BABA0M251A.jsp 新增 BADAPR 的資料
	 */
	public void addManPayDataList(UserBean userData, CompPaymentCase compPaymentCase) {
		badaprDao.addManPayDataList(userData, compPaymentCase);
	}

	/**
	 * 新增 老年、失能年金補正核付作業更正記錄檔(<code>BAAPPLOG</code>) 資料
	 * 
	 * @param compPaymentCase
	 */
	public void insertLogForCompPayment(List<String> list, List<CompPaymentCase> compPaymentCaseList, CompPaymentCase compPaymentCase) {
        for(int i = 0; i < compPaymentCaseList.size(); i++) {
        	compPaymentCase.setBaappbaseId(compPaymentCaseList.get(i).getBaappbaseId());
        	compPaymentCase.setSeqNo(compPaymentCaseList.get(i).getSeqNo());
        	compPaymentCase.setIssuYm(compPaymentCaseList.get(i).getIssuYm());
        	compPaymentCase.setPayYm(compPaymentCaseList.get(i).getPayYm());
			for (int j = 0; j < list.size(); j++) {
				compPaymentCase.setUpCol(list.get(j));
				if(StringUtils.equals("核定日期", compPaymentCase.getUpCol())) {
					compPaymentCase.setaValue(compPaymentCase.getChkDate());
					compPaymentCase.setbValue(DateUtility.changeDateToAD(compPaymentCaseList.get(i).getChkDate()));
				}
				
				if(StringUtils.equals("核付日期", compPaymentCase.getUpCol())) {
					compPaymentCase.setaValue(compPaymentCase.getAplPayDate());
					compPaymentCase.setbValue(DateUtility.changeDateToAD(compPaymentCaseList.get(i).getAplPayDate()));
				}
				
				if(StringUtils.equals("入帳日期", compPaymentCase.getUpCol())) {
					compPaymentCase.setaValue(compPaymentCase.getRemitDate());
					compPaymentCase.setbValue(DateUtility.changeDateToAD(compPaymentCaseList.get(i).getRemitDate()));
				}
				
				baapplogDao.insertLogForCompPayment(compPaymentCase);
			}
        }
	}

    /**
     * BABA0M242C.jsp BABA0M252C.jsp 更新 BADAPR 的資料
     */
	public void updManPayDataList2(UserBean userData, CompPaymentCase compPaymentCase) {
		badaprDao.updManPayDataList2(userData, compPaymentCase);
	}
	
    /**
          * 檢核是否可新增補正核付資料 for 補正核付作業
     */
	public List<Badapr> qryChkManPay(String apNo, String issuYm) {
		return badaprDao.qryChkManPay(apNo, issuYm);
	}
	
	/**
	 * BABA0M261A.jsp 更新 BADAPR 的資料
	 */
	public void updManPayDataListForSurvivor(UserBean userData, CompPaymentCase compPaymentCase) {
		badaprDao.updManPayDataListForSurvivor(userData, compPaymentCase);
	}
	
	/**
	 * BABA0M261A.jsp 新增 BADAPR 的資料
	 */
	public void addManPayDataListForSurvivor(UserBean userData, CompPaymentCase compPaymentCase) {
		badaprDao.addManPayDataListForSurvivor(userData, compPaymentCase);
	}
	
    /**
     * BABA0M262C.jsp 更新 BADAPR 的資料
     */
	public void updManPayDataListForSurvivor2(UserBean userData, CompPaymentCase compPaymentCase) {
		badaprDao.updManPayDataListForSurvivor2(userData, compPaymentCase);
	}
	
	/**
     * 查詢 處理年月是否已經存在BABATCHJOB
     * 
     * @param issuYm(西元)
     * @return
     */
 	public List<Babatchjob> queryBatchJobStatus(String issuYm) {
 		List<Babatchjob> jobList = babatchjobDao.selectBatchJob12Status(issuYm);
 		if(jobList == null || jobList.size()==0) {
 			return null;
 		} else {
 			//進度查詢，資料處裡
			for(int i=0; i<jobList.size();i++) {
				Babatchjob returnList = jobList.get(i);
				returnList.setIssuYm(DateUtility.changeWestYearMonthType(returnList.getIssuYm()));
				returnList.setProcBegTime(DateUtility.formatChineseDateTimeString(returnList.getProcBegTime(), true));
				returnList.setProcEndTime(DateUtility.formatChineseDateTimeString(returnList.getProcEndTime(), true));
				if(StringUtils.equals(returnList.getStatus(), ConstantKey.STATUS_RUNNING)) {
					returnList.setStatus(ConstantKey.STATUS_RUNNING_STR);//"執行中"
				} else if(StringUtils.equals(returnList.getStatus(), ConstantKey.STATUS_WAITING)) {
					returnList.setStatus(ConstantKey.STATUS_WAITING_STR);//"等待中"
				} else if(StringUtils.equals(returnList.getStatus(), ConstantKey.STATUS_END)) {
					returnList.setStatus(ConstantKey.STATUS_END_STR);//"執行結束"
				} else if(StringUtils.equals(returnList.getStatus(), ConstantKey.STATUS_ERROR)) {
					returnList.setStatus(ConstantKey.STATUS_ERROR_STR);//"發生錯誤"
				} else {
					returnList.setStatus("");
				}
			}
 			return jobList;
 		}
 		
    }
 	
 	/**
     * 查詢年金統計執行作業-目前進度查詢(明細)
     * 
     * @param baJobId 資料列編號(JOBID)
     */
    public List<ExecStatisticsDtlCase> getStatisticsProgressStepDtl(String baJobId) {
        List<Babatchjobdtl> dataList = babatchjobdtlDao.selectNowProgressStepsDtl(baJobId);
        List<ExecStatisticsDtlCase> caseList = new ArrayList<ExecStatisticsDtlCase>();
        for (int i = 0; i < dataList.size(); i++) {
            Babatchjobdtl babatchjobdtl = dataList.get(i);
            ExecStatisticsDtlCase returnCase = new ExecStatisticsDtlCase();
            // 0:成功 1:失敗
            if (StringUtils.isNotBlank(babatchjobdtl.getFlag())) {
                if (StringUtils.equals(babatchjobdtl.getFlag(), "1")) {
                    returnCase.setFlag("失敗");
                }
                else {
                    returnCase.setFlag("成功");
                }
            }
            returnCase.setFlagMsg(babatchjobdtl.getFlagMsg());
            if (StringUtils.isNotBlank(babatchjobdtl.getFlagTime())) {
                returnCase.setFlagTime(DateUtility.formatChineseDateTimeString(StringUtils.substring(babatchjobdtl.getFlagTime(), 0, 14), true));
            }
            caseList.add(returnCase);
        }
        if (caseList == null || caseList.size() <= 0) {
            return null;
        }
        else {
            return caseList;
        }
    }
    /**
     * 查詢年金統計執行作業-報表資料
     * 
     * @param baJobId 資料列編號(JOBID)
     */
    public List<ExecStatisticReportCase> getStatisticsReportData(String issuYm) {
        List<Bansf> dataList = bansfDao.selectStatisticsReportData(issuYm);
        if(dataList==null || dataList.size()<=0) {
        	return null;
        }
        List<ExecStatisticReportCase> caseList = new ArrayList<ExecStatisticReportCase>();
        for (int i = 0; i < dataList.size(); i++) {
        	Bansf bansfCase = dataList.get(i);
        	ExecStatisticReportCase returnCase = new ExecStatisticReportCase();
            returnCase.setAdWkMk(bansfCase.getAdWkMk());
            returnCase.setEvType(bansfCase.getEvType());
            returnCase.setFirstPay(bansfCase.getFirstPay());
            returnCase.setIssuYm(DateUtility.changeWestYearMonthType(bansfCase.getIssuYm()));
            returnCase.setPamts(new BigDecimal(bansfCase.getpAmts()));
            returnCase.setPayCnt(new BigDecimal(bansfCase.getPayCnt()));
            returnCase.setPayNo(bansfCase.getPayNo());
            caseList.add(returnCase);
        }
        return caseList;
    }

	/**
	 * 依傳入條件新增 給付媒體上傳記錄檔
	 *
	 * @param mfileName 媒體檔案名稱
	 * @param mfileDate 媒體檔案日期
	 * @param chkDate 核定日期
	 *
	 */
	public void insertData(String mfileName, String mfileDate, String chkDate) {
		bamfileftprecordDao.insertData(mfileName, mfileDate, chkDate);
	}

	/**
	 * 依傳入條件更新 給付媒體上傳記錄檔
	 *
	 * @param mfileName 媒體檔案名稱
	 * @param mfileDate 媒體檔案日期
	 * @param chkDate 核定日期
	 *
	 */
	public void updateData(String mfileName, String mfileDate, String chkDate) {
		bamfileftprecordDao.updateData(mfileName, mfileDate, chkDate);
	}
    /**
     * PKG_BANSF_01.SP_BANSF
     * @param jobId
     * @param issuYm
     */
    public String callSpBansf(String jobId, String issuYm) {
    	// 呼叫StoreProcedure Name:PKG_BANSF_01.SP_BANSF
    	return  babatchjobDao.callSpBansf(jobId, issuYm);
        
    }
    
    /**
	 * 查詢批次核定函排程目前佇列中相同條件的數量
	 * 
	 * @param issuYm  核定年月
	 * @param payCode  給付別
	 * @param fileName  檔案名稱
	 */
	public Babatchjob doQueryBatchJobStatusforOtherRpt05Action(String issuYm, String payCode, String fileName) {
		return babatchjobDao.doQueryBatchJobStatusforOtherRpt05Action(issuYm, payCode, fileName);
	}

	public void doScheduleBatchJobforOtherRpt05Action(String jobId, String issuYm, String fileName ,String status, String payCode){		
		Babatchjob babatchjob = new Babatchjob();
		babatchjob.setBaJobId(jobId);
		babatchjob.setIssuYm(issuYm);
		babatchjob.setPayCode(payCode);
		babatchjob.setFileName(fileName);
		babatchjob.setProcBegTime(DateUtility.getNowWestDateTime(true));
		babatchjob.setStatus(status);
		babatchjob.setPaySeqNo("1");
		
		babatchjobDao.doScheduleBatchJobforOtherRpt05Action(babatchjob);
	}
	
	 public void inserBabatchjobdtlData(Babatchjobdtl babatchjobdtl){
         babatchjobdtlDao.inserData(babatchjobdtl);
    }
	
	public void setBaunacprecDao(BaunacprecDao baunacprecDao) {
		this.baunacprecDao = baunacprecDao;
	}

	public void setBaunacpdtlDao(BaunacpdtlDao baunacpdtlDao) {
		this.baunacpdtlDao = baunacpdtlDao;
	}

	public void setBabatchrecDao(BabatchrecDao babatchrecDao) {
		this.babatchrecDao = babatchrecDao;
	}

	public void setBaonlinejobDao(BaonlinejobDao baonlinejobDao) {
		this.baonlinejobDao = baonlinejobDao;
	}

	public void setFtpClient(FtpHelper ftpClient) {
		this.ftpClient = ftpClient;
	}

	public void setFtpApClient(FtpApHelper ftpApClient) {
		this.ftpApClient = ftpApClient;
	}

	public void setBagivedtlDao(BagivedtlDao bagivedtlDao) {
		this.bagivedtlDao = bagivedtlDao;
	}

	public void setBapaissudateDao(BapaissudateDao bapaissudateDao) {
		this.bapaissudateDao = bapaissudateDao;
	}

	public void setBadaprDao(BadaprDao badaprDao) {
		this.badaprDao = badaprDao;
	}

	public void setBaappbaseDao(BaappbaseDao baappbaseDao) {
		this.baappbaseDao = baappbaseDao;
	}

	public void setBabatchjobDao(BabatchjobDao babatchjobDao) {
		this.babatchjobDao = babatchjobDao;
	}

	public void setBabatchjobdtlDao(BabatchjobdtlDao babatchjobdtlDao) {
		this.babatchjobdtlDao = babatchjobdtlDao;
	}

	public void setBauseremailDao(BauseremailDao bauseremailDao) {
		this.bauseremailDao = bauseremailDao;
	}

	public void setBagivetmpdtlDao(BagivetmpdtlDao bagivetmpdtlDao) {
		this.bagivetmpdtlDao = bagivetmpdtlDao;
	}

	public void setBarvconfirmDao(BarvconfirmDao barvconfirmDao) {
		this.barvconfirmDao = barvconfirmDao;
	}

	public void setBarvcaseDao(BarvcaseDao barvcaseDao) {
		this.barvcaseDao = barvcaseDao;
	}

	public void setMailHelper(MailHelper mailHelper) {
		this.mailHelper = mailHelper;
	}

	public void setBaprocedureDao(BaprocedureDao baprocedureDao) {
		this.baprocedureDao = baprocedureDao;
	}

	public void setBaproceduredataDao(BaproceduredataDao baproceduredataDao) {
		this.baproceduredataDao = baproceduredataDao;
	}

	public void setBaproceduredtlDao(BaproceduredtlDao baproceduredtlDao) {
		this.baproceduredtlDao = baproceduredtlDao;
	}

	public void setRunprocedurejobDao(RunprocedurejobDao runprocedurejobDao) {
		this.runprocedurejobDao = runprocedurejobDao;
	}

	public void setBaapplogDao(BaapplogDao baapplogDao) {
		this.baapplogDao = baapplogDao;
	}

	public void setBansfDao(BansfDao bansfDao) {
		this.bansfDao = bansfDao;
	}

	public void setBamfileftprecordDao(BamfileftprecordDao bamfileftprecordDao) {
		this.bamfileftprecordDao = bamfileftprecordDao;
	}
}
