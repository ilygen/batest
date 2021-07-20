package tw.gov.bli.ba.bj.helper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import tw.gov.bli.ba.util.ExceptionUtility;

public class FtpDbHelper {
	private static Log log = LogFactory.getLog(FtpDbHelper.class);

	private String controlEncoding; // FTP Control Encoding
	private String serverAddress; // FTP Server Address
	private String serverPort; // FTP Server Port
	private String userId; // 使用者帳號
	private String userPass; // 使用者密碼
	private String dirForRecordFile; // 紀錄檔存放之目錄
	private String dirForDataFile; // 文字檔存放之目錄
	private String dirForRecordFileBackup; // 紀錄檔備份之目錄
	private String dirForDataFileBackup; // 文字檔備份之目錄
	private String txtFileEncoding; // 文字檔之編碼
	private String procRecordFileNamePrefix; // 紀錄檔 - 檔名字首
	private List<String> paidMarkFileNamePrefix; // 給付媒體回押註記 - 檔名字首
	private List<String> writeOffFileNamePrefix; // 收回沖銷作業 - 檔名字首
	private String dirForRecordFileMedia;
	/**
	 * 自 FTP 取得指定 檔名 的 紀錄檔 內容
	 * 
	 * @param fileName
	 *            欲取得的檔名
	 * @return 回傳 String Array, 每行分別為 Array 中的一個 String 物件
	 */
	public String[] getRecordFileContent(String fileName) {
		return getTxtFileContent(fileName, dirForRecordFile);
	}

	/**
	 * 自 FTP 取得指定 檔名 的 資料文字檔 內容
	 * 
	 * @param fileName
	 *            欲取得的檔名
	 * @return 回傳 String Array, 每行分別為 Array 中的一個 String 物件
	 */
	public String[] getTxtFileContent(String fileName) {
		return getTxtFileContent(fileName, dirForDataFile);
	}

	/**
	 * 自 FTP 取得指定 目錄 及 檔名 的文字檔內容 (指定目錄)
	 * 
	 * @param fileName
	 *            欲取得的檔名
	 * @param dirForTxtDownload
	 *            目錄名
	 * @return 回傳 String Array, 每行分別為 Array 中的一個 String 物件
	 */
	public String[] getTxtFileContent(String fileName, String dirForTxtDownload) {
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding(controlEncoding);

		boolean error = false;

		try {
			int reply;
			ftp.connect(serverAddress, Integer.parseInt(serverPort));
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.error("FTP 連線失敗...");
				throw new RuntimeException("FTP 連線失敗...");
			}

			ftp.login(userId, userPass);

			ftp.changeWorkingDirectory(dirForTxtDownload);

			InputStream is = ftp.retrieveFileStream(fileName);

			ArrayList<String> list = new ArrayList<String>();

			String strLine = "";
			if (is != null) {
				BufferedReader bf = new BufferedReader(new InputStreamReader(is, txtFileEncoding));
				while ((strLine = bf.readLine()) != null) {
					if (StringUtils.isNotBlank(StringUtils.trimToEmpty(strLine)))
						list.add(strLine);
				}
				bf.close();
				is.close();
			} else {
				log.error("欲從 FTP 取得的檔案不存在, 檔案名稱: " + StringUtils.defaultString(fileName));
				throw new RuntimeException("欲從 FTP 取得的檔案不存在, 檔案名稱: " + StringUtils.defaultString(fileName));
			}

			ftp.logout();

			return (String[]) list.toArray(new String[0]);
		} catch (Exception e) {
			log.error("FtpHelper.getTxtFileContent() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			throw new RuntimeException("FTP 發生錯誤:" + StringUtils.defaultString(e.getMessage()));
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
		}
	}

	/**
	 * 將指定的 紀錄檔 檔案備份到 dirForRecordFileBackup 目錄中
	 * 
	 * @param fileName
	 *            欲備份的檔名
	 */
	public void backupRecordFile(String fileName) {
		if (StringUtils.isBlank(dirForRecordFileBackup))
			return;

		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding(controlEncoding);

		boolean error = false;

		try {
			int reply;
			ftp.connect(serverAddress, Integer.parseInt(serverPort));
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.error("FTP 連線失敗...");
				throw new RuntimeException("FTP 連線失敗...");
			}

			ftp.login(userId, userPass);

			// 搬檔
			ftp.changeWorkingDirectory(dirForRecordFile);
			boolean bOperation = ftp.rename(fileName, dirForRecordFileBackup + fileName + "_DONE");

			if (!bOperation)
				log.info("紀錄檔" + fileName + "檔案搬移失敗 Code:" + ftp.getReplyCode() + "備份檔案路徑：" + dirForRecordFileBackup);
			else
				log.info("紀錄檔" + fileName + "檔案搬移成功 Code:" + ftp.getReplyCode());

			ftp.logout();

		} catch (Exception e) {
			log.error("FtpHelper.backupRecordFile() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			throw new RuntimeException("FTP 發生錯誤:" + StringUtils.defaultString(e.getMessage()));
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
		}
	}

	/**
	 * 將指定的 資料文字檔 檔案備份到 dirForDataFileBackup 目錄中
	 * 
	 * @param fileName
	 *            欲備份的檔名
	 */
	public void backupFile(String fileName) {
		if (StringUtils.isBlank(dirForDataFileBackup))
			return;

		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding(controlEncoding);

		boolean error = false;

		try {
			int reply;
			ftp.connect(serverAddress, Integer.parseInt(serverPort));
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.error("FTP 連線失敗...");
				throw new RuntimeException("FTP 連線失敗...");
			}

			ftp.login(userId, userPass);

			// 搬檔
			ftp.changeWorkingDirectory(dirForDataFile);
			boolean bOperation = ftp.rename(fileName, dirForDataFileBackup + fileName + "_DONE");

			if (!bOperation)
				log.info("資料文字檔" + fileName + "檔案搬移失敗 Code:" + ftp.getReplyCode() + "備份檔案路徑：" + dirForDataFileBackup);
			else
				log.info("資料文字檔" + fileName + "檔案搬移成功 Code:" + ftp.getReplyCode());

			ftp.logout();

		} catch (Exception e) {
			log.error("FtpHelper.backupFile() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			throw new RuntimeException("FTP 發生錯誤:" + StringUtils.defaultString(e.getMessage()));
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
		}
	}

	/**
	 * 取得所有在 dirForRecordFile 目錄中的 紀錄檔檔名
	 * 
	 * @return 回傳 String Array, 每個檔名分別為 Array 中的一個 String 物件
	 */
	public String[] getRecordFileNames() {
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding(controlEncoding);

		boolean error = false;

		try {
			int reply;
			ftp.connect(serverAddress, Integer.parseInt(serverPort));
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.error("FTP 連線失敗...");
				throw new RuntimeException("FTP 連線失敗...");
			}

			ftp.login(userId, userPass);

			// 取得紀錄檔檔名
			ftp.changeWorkingDirectory(dirForRecordFile);
			FTPFile[] files = ftp.listFiles();
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				FTPFile file = files[i];

				if (file.isFile()) {
					if (StringUtils.startsWithIgnoreCase(file.getName(), procRecordFileNamePrefix)) {
						list.add(file.getName());
					}
				}
			}

			ftp.logout();

			return (String[]) list.toArray(new String[0]);
		} catch (Exception e) {
			log.error("FtpHelper.getRecordFileNames() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			throw new RuntimeException("FTP 發生錯誤:" + StringUtils.defaultString(e.getMessage()));
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
		}
	}

	/**
	 * 取得 給付媒體回押註記 - 檔名字首
	 * 
	 * @return
	 */
	public List<String> getPaidMarkFileNamePrefix() {
		return paidMarkFileNamePrefix;
	}

	/**
	 * 取得 收回沖銷作業 - 檔名字首
	 * 
	 * @return
	 */
	public List<String> getWriteOffFileNamePrefix() {
		return writeOffFileNamePrefix;
	}

	/**
	 * 取得所有在 dirForRecordFile 目錄中的 紀錄檔檔名
	 * 
	 * @return 回傳 String Array, 每個檔名分別為 Array 中的一個 String 物件
	 */
	public String getRecordFileNames(String mfileName) {
		FTPClient ftp = new FTPClient();
		ftp.setControlEncoding(controlEncoding);

		boolean error = false;

		try {
			int reply;
			ftp.connect(serverAddress, Integer.parseInt(serverPort));
			reply = ftp.getReplyCode();
			
			ftp.setFileTransferMode(ftp.COMPRESSED_TRANSFER_MODE);
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.error("FTP 連線失敗...");
				throw new RuntimeException("FTP 連線失敗...");
			}

			ftp.login(userId, userPass);
			// 取得紀錄檔檔名
			ftp.changeWorkingDirectory(dirForRecordFileMedia);
			InputStream is = retrieveFileStream(ftp, dirForRecordFileMedia+mfileName);
			// 未來確定path後要將mfileName改為mfilepath
			if (is == null) {
				ftp.disconnect();
				log.error("FTP取無該檔...");
				return null;
			} else {
				String outContent = downloadFile(is);
				ftp.logout();
				return outContent;
			}
			
			
		} catch (Exception e) {
			log.error("FtpHelper.getRecordFileNames() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			throw new RuntimeException("FTP 發生錯誤:" + StringUtils.defaultString(e.getMessage()));
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
		}
	}

	public String downloadFile(InputStream is) throws Exception{
		StringBuffer str = new StringBuffer("");

        String strLine = "";
        if (is != null) {
            BufferedReader bf = new BufferedReader(new InputStreamReader(is,txtFileEncoding));
            while ((strLine = bf.readLine()) != null) {
                str.append(strLine + "\r\n");
            }
            bf.close();
            is.close();
        } 
		return str.toString();
	}
	public long getFileSize(FTPClient ftp, String mfileName) {
		try {
			long fileSize = 0;
			boolean fileok = ftp.isConnected();
			System.out.println(fileok);
			FTPFile[] files = ftp.listFiles(mfileName);
			if (files.length == 1 && files[0].isFile()) {
				fileSize = files[0].getSize();
				return fileSize;
			} else {
				return 0;
			}
		} catch (Exception e) {
			log.error("FTP 取檔發生錯誤...");
			throw new RuntimeException("FTP 取檔發生錯誤...");
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
		}

	}

	public InputStream retrieveFileStream(FTPClient ftp, String mfileName) {
		try {
			InputStream is = ftp.retrieveFileStream(mfileName);
			int reply = ftp.getReplyCode();
			if (is == null || (!FTPReply.isPositivePreliminary(reply) && !FTPReply.isPositiveCompletion(reply))) {
				return null;
			} else {
				return is;
			}
		} catch (Exception e) {
			log.error("FTP 取檔發生錯誤...");
			throw new RuntimeException("FTP 取檔發生錯誤...");
		}

	}
    
    /**
     * 自 FTP 取得指定 目錄 及 檔名 的pdf檔 (指定目錄)
     * 
     * @param fileName 欲取得的檔名
     * @param dirForTxtDownload 目錄名
     * @return 回傳 String Array, 每行分別為 Array 中的一個 String 物件
     */
    public ByteArrayOutputStream getZipFile(String fileName, String dirForDownload) {
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");

        boolean error = false;

        try {
            int reply;
            ftp.connect(serverAddress, Integer.parseInt(serverPort));
            reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                log.error("FTP 連線失敗...");
                throw new RuntimeException("FTP 連線失敗...");
            }

            ftp.login(userId, userPass);

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            Boolean checkDir = ftp.changeWorkingDirectory(dirForDownload);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            
            Boolean success =  ftp.retrieveFile(fileName, baoOutput);

            ftp.logout();

            if(success){
            	return baoOutput;
            }else{
            	return null;
            }
        }
        catch (Exception e) {
            log.error("FtpHelper.getPdfFile() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            throw new RuntimeException("FTP 發生錯誤:" + StringUtils.defaultString(e.getMessage()));
        }
        finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                }
                catch (IOException ioe) {
                    // do nothing
                }
            }
        }
    }

	public void setControlEncoding(String controlEncoding) {
		this.controlEncoding = controlEncoding;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public void setDirForRecordFile(String dirForRecordFile) {
		this.dirForRecordFile = dirForRecordFile;
	}

	public void setDirForDataFile(String dirForDataFile) {
		this.dirForDataFile = dirForDataFile;
	}

	public void setDirForRecordFileBackup(String dirForRecordFileBackup) {
		this.dirForRecordFileBackup = dirForRecordFileBackup;
	}

	public void setDirForDataFileBackup(String dirForDataFileBackup) {
		this.dirForDataFileBackup = dirForDataFileBackup;
	}

	public void setTxtFileEncoding(String txtFileEncoding) {
		this.txtFileEncoding = txtFileEncoding;
	}

	public void setProcRecordFileNamePrefix(String procRecordFileNamePrefix) {
		this.procRecordFileNamePrefix = procRecordFileNamePrefix;
	}

	public void setPaidMarkFileNamePrefix(List<String> paidMarkFileNamePrefix) {
		this.paidMarkFileNamePrefix = paidMarkFileNamePrefix;
	}

	public void setWriteOffFileNamePrefix(List<String> writeOffFileNamePrefix) {
		this.writeOffFileNamePrefix = writeOffFileNamePrefix;
	}

	public String getDirForRecordFileMedia() {
		return dirForRecordFileMedia;
	}

	public void setDirForRecordFileMedia(String dirForRecordFileMedia) {
		this.dirForRecordFileMedia = dirForRecordFileMedia;
	}

	public static void setLog(Log log) {
		FtpDbHelper.log = log;
	}

}
