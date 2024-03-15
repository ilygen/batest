package tw.gov.bli.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import tw.gov.bli.ba.helper.PropertyHelper;

/**
 * 呼叫MGMR中繼API工具
 *
 * @author Josh
 * @version 2023/02/04
 * @version 2023/03/17 copy from BC
 * @version 2023/03/27 Kevin upload多筆上傳修正只會打第一次api問題<br>
 */
public class MgMrUtil {
	private static Log log = LogFactory.getLog(MgMrUtil.class);
	private final static String MG_MR_RELAY_API_PGID = "baeweb.ip";
	private final static String MG_MR_RELAY_API_URL = "/baEWeb/ba/remoting/mgmrRelay/";

	public final static String MG_MR_SUCCESS_CODE = "00";
	public final static String MG_MR_FAIL_CODE = "99";
	
	enum Type {
		MG("MG"), MR("MR");
		String value;

		private Type(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * [單檔]案上傳forMG
	 * @param file  : 檔案
	 * @param loginid : 登入id
	 * @param isDeleteFile : 是否刪掉本地檔案
	 * @return: String
	 */
	public String upload_MG(Map<String, Object> ftpConfig, File file, String loginid, boolean isDeleteFile) {
		return this.upload_MG(ftpConfig, file, loginid, isDeleteFile, null);
	}
	
	/**
	 * [單檔]案上傳forMG
	 * @param file  : 檔案
	 * @param loginid : 登入id
	 * @param isDeleteFile : 是否刪掉本地檔案
	 * @param crossAP : 跨系統別
	 * @return: String
	 */
	public String upload_MG(Map<String, Object> ftpConfig, File file, String loginid, boolean isDeleteFile, String crossAP) {
		return this.upload(ftpConfig, file, loginid, null, null, "MG", isDeleteFile, crossAP);
	}

	/**
	 * [單檔]案上傳forMR
	 * @param file : 檔案
	 * @param loginid : 登入id
	 * @param reportid : 報表id
	 * @param isDeleteFile : 是否刪掉本地檔案
	 * @return: String
	 */
	public String upload_MR(Map<String, Object> ftpConfig, File file, String loginid, String reportid, boolean isDeleteFile) {
		return this.upload_MR(ftpConfig, file, loginid, reportid, isDeleteFile, null);
	}
	


	/**
	 * [單檔]案上傳forMR
	 * @param file : 檔案
	 * @param loginid : 登入id
	 * @param reportid : 報表id
	 * @param isDeleteFile : 是否刪掉本地檔案
	 * @param crossAP : 跨系統別
	 * @return: String
	 */
	public String upload_MR(Map<String, Object> ftpConfig, File file, String loginid, String reportid, boolean isDeleteFile, String crossAP) {
		return this.upload(ftpConfig, file, loginid, reportid, null, "MR", isDeleteFile, crossAP);
	}

	/**
	 * Config傳入 + 檔案上傳forMR
	 * @param file : 檔案
	 * @param loginid : 登入id
	 * @param reportid : 報表id
	 * @param type : MG或MR
	 * @param ipaddr : URL
	 * @param portNo : 端口號
	 * @param targetDir : 目標目錄
	 * @param isDeleteFile : 是否刪掉本地檔案
	 * @param crossAP : 跨系統別
	 * @return: String
	 */
	public String uploadIncomingTarget(File file, String loginid, String reportid, String type, String ipaddr, String portNo, String targetDir, boolean isDeleteFile, String crossAP) {
		log.info("開始執行MgMrUtil.uploadIncomingTarget");
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			
			log.info("開始傳參數");
			MultipartEntityBuilder entityBuilder = createMultipartEntityBuilder(b -> b
		            .addTextBody("type", type, ContentType.TEXT_PLAIN)         
		            .addTextBody("loginid", loginid, ContentType.TEXT_PLAIN) 
		            .addTextBody("ipaddr", ipaddr, ContentType.TEXT_PLAIN)    
		            .addTextBody("portno", portNo, ContentType.TEXT_PLAIN)   
		            .addTextBody("ftpdir", targetDir, ContentType.TEXT_PLAIN)
					);
			if(StringUtils.isNotBlank(reportid)) entityBuilder.addTextBody("reportid", reportid, ContentType.TEXT_PLAIN);
			if(StringUtils.isNotBlank(crossAP)) entityBuilder.addTextBody("crossAP", crossAP, ContentType.TEXT_PLAIN);
			
			if (file.exists()) {
				log.info("傳入的File有存在,並且name=" + file.getName());
			}

			// 傳檔案
			entityBuilder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, file.getName());
			entityBuilder.addTextBody("filename", file.getName(), ContentType.TEXT_PLAIN);

			log.info("開始傳檔案");
			String urlStr = "https://" + PropertyHelper.getProperty(MG_MR_RELAY_API_PGID) + MG_MR_RELAY_API_URL + "upload";
			log.info("url為:" + urlStr);
			HttpPost httpPost = new HttpPost(urlStr);

			HttpEntity entity = entityBuilder.build();
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			String result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);

			if (isDeleteFile) {
				file.delete();
			}
			
			log.info("resp：" + result);
			log.info("結束執行MgMrUtil.uploadIncomingTarget");
			return (String) new JSONObject(result).get("statuscode");
		} catch (Exception e) {
			log.error("MgMrUtil.uploadIncomingTarget error：" + e.getMessage(), e);
		}
		return "";
	}
	
	/**
	 * 上傳
	 * @param ftpConfig
	 * @param file
	 * @param loginid
	 * @param reportid
	 * @param type
	 * @param isDeleteFile
	 * @return
	 */
	private String upload(Map<String, Object> ftpConfig, File file, String loginid, String reportid, String subDir, String type, boolean isDeleteFile, String crossAP) {
		log.info("開始執行MgMrUtil.upload");
		try {
			String ftpdir = StringUtils.isNotBlank(subDir) ? 
							MapUtils.getString(ftpConfig, "ftpdir") + "/" + subDir : 
							MapUtils.getString(ftpConfig, "ftpdir");
			
			return uploadIncomingTarget(file, 
										loginid, 
										reportid, 
										type, 
										MapUtils.getString(ftpConfig, "ipaddr"),
										MapUtils.getString(ftpConfig, "portno"), 
										ftpdir, 
										isDeleteFile, crossAP);
		} catch (Exception e) {
			log.info("執行MgMrUtil.upload失敗");
		}
			
		return "";
	}
	
	/**
	 * [MG]檔案下載到指定目錄
	 * @param fileName : 檔案的名稱
	 * @param outputDir : 輸出目錄
	 * @return: String
	 */
	public String download(Map<String, Object> ftpConfig, String fileName, String outputDir, String loginid) {
		return download(ftpConfig, fileName, outputDir, loginid, null);
	}

	/**
	 * [MG]檔案下載到指定目錄
	 * @param fileName : 檔案的名稱
	 * @param outputDir : 輸出目錄
	 * @param crossAP : 跨系統別
	 * @return: String
	 */
	public String download(Map<String, Object> ftpConfig, String fileName, String outputDir, String loginid, String crossAP) {
		log.info("開始執行MgMrUtil.download");
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			log.info("開始傳參數");
			MultipartEntityBuilder entityBuilder = createMultipartEntityBuilder(b -> b
		            .addTextBody("filename", fileName, ContentType.TEXT_PLAIN)                               
		            .addTextBody("outputdir", outputDir, ContentType.TEXT_PLAIN)                            
		            .addTextBody("loginid", loginid, ContentType.TEXT_PLAIN)                              
		            .addTextBody("ipaddr", MapUtils.getString(ftpConfig, "ipaddr"), ContentType.TEXT_PLAIN) 
		            .addTextBody("portno", MapUtils.getString(ftpConfig, "portno"), ContentType.TEXT_PLAIN)
		            .addTextBody("ftpdir", MapUtils.getString(ftpConfig, "ftpdir"), ContentType.TEXT_PLAIN)
					);
			if(StringUtils.isNotBlank(crossAP)) entityBuilder.addTextBody("crossAP", crossAP, ContentType.TEXT_PLAIN);
			
			String urlStr = "https://" + PropertyHelper.getProperty(MG_MR_RELAY_API_PGID) + MG_MR_RELAY_API_URL + "download";
			log.info("url為:" + urlStr);
	    	HttpPost httpPost = new HttpPost(urlStr);
	    	
	    	HttpEntity entity = entityBuilder.build();
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();

			log.info("發送API完成");
			String result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
			log.info("resp：" + result);
			
			String statuscode = "";
			File file = null;
			if (result != null && !result.isEmpty()) {
				JSONObject json = new JSONObject(result);
				statuscode = json.getString("statuscode");
				String base64Str = json.getString("downloadFile");
				byte[] downloadFile = Base64.getDecoder().decode(base64Str);
				//讀取檔案資料
				file = new File(cleanString(outputDir), cleanString(fileName));
				
				// 處理下載的檔案
				try(OutputStream os = new FileOutputStream(file);){
		            os.write(downloadFile);
		            log.debug(file.getAbsolutePath() + "下載成功");
				} catch (Exception e) {
					log.error("MgMrUtil.download OutputStream error：" + e.getMessage(), e);
				}
			}
			log.info("結束執行MgMrUtil.download");
			return (String) new JSONObject(result).get("statuscode");
			
		} catch (Exception e) {
			log.error("MgMrUtil.download error：" + e.getMessage(), e);
		}

		return "";
	}
	
	/**
	 * 取得檔案列表
	 * @param prefix : 欲搜尋的前綴
	 * @param suffix : 欲搜尋的後綴
	 * @param loginid : 登入id
	 * @return: List
	 */
	public List<MgFile> query(Map<String, Object> ftpConfig, String prefix, String suffix, String loginid) {
		return query(ftpConfig, prefix, suffix, loginid, null);
	}

	/**
	 * 取得檔案列表
	 * @param prefix : 欲搜尋的前綴
	 * @param suffix : 欲搜尋的後綴
	 * @param loginid : 登入id
	 * @param crossAP : 跨系統別
	 * @return: List
	 */
	public List<MgFile> query(Map<String, Object> ftpConfig, String prefix, String suffix, String loginid, String crossAP) {
		log.info("開始執行MgMrUtil.query");

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			log.info("開始傳參數");
			
			MultipartEntityBuilder entityBuilder = createMultipartEntityBuilder(b -> b
		            .addTextBody("prefix", prefix, ContentType.TEXT_PLAIN)                               
		            .addTextBody("suffix", suffix, ContentType.TEXT_PLAIN)                            
		            .addTextBody("loginid", loginid, ContentType.TEXT_PLAIN)                              
		            .addTextBody("ipaddr", MapUtils.getString(ftpConfig, "ipaddr"), ContentType.TEXT_PLAIN) 
		            .addTextBody("portno", MapUtils.getString(ftpConfig, "portno"), ContentType.TEXT_PLAIN)
		            .addTextBody("ftpdir", MapUtils.getString(ftpConfig, "ftpdir"), ContentType.TEXT_PLAIN));
			if(StringUtils.isNotBlank(crossAP)) entityBuilder.addTextBody("crossAP", crossAP, ContentType.TEXT_PLAIN);
			
			String urlStr = "https://" + PropertyHelper.getProperty(MG_MR_RELAY_API_PGID) + MG_MR_RELAY_API_URL + "query";
			log.info("url為:" + urlStr);
			HttpPost httpPost = new HttpPost(urlStr);
			
	    	HttpEntity entity = entityBuilder.build();
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			log.info("發送API完成");

			if (responseEntity != null) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
					String line = reader.readLine();

					log.info("line :"+line);
					log.info("結束執行MgMrUtil.query");
					JSONObject jSONObject = new JSONObject(line);
					if(!JSONObject.NULL.equals(jSONObject.get("list"))) {
						JSONArray jsonArray =  (JSONArray)jSONObject.get("list");
						List<MgFile> list = new ArrayList<MgFile>();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject j = jsonArray.getJSONObject(i);
							MgFile mgFile = new MgFile();
							mgFile.setType(j.get("type").equals(null)? null : j.get("type").toString());
							mgFile.setName(j.get("name").equals(null)? null : j.get("name").toString());
							mgFile.setParentId(j.get("parentId").equals(null)? null : Integer.valueOf(j.get("parentId").toString()));
							mgFile.setPathToken(j.get("pathToken").equals(null)? null : j.get("pathToken").toString());
							mgFile.setDescription(j.get("description").equals(null)? null : j.get("description").toString());
							mgFile.setStatus(j.get("status").equals(null)? null : Integer.valueOf(j.get("status").toString()));
							mgFile.setCreateDate(j.get("createDate").equals(null)? null : j.get("createDate").toString());
							mgFile.setHoldTime(j.get("holdTime").equals(null)? null : Integer.valueOf(j.get("holdTime").toString()));
							mgFile.setSize(j.get("size").equals(null)? null : Long.parseLong(j.get("size").toString()));
							mgFile.setId(j.get("id").equals(null)? null : j.get("id").toString());
							mgFile.setUploadTime(j.get("uploadTime").equals(null)? null : j.get("uploadTime").toString());
							list.add(mgFile);
						}
						return list;
					}
				}
			}
			
		} catch (Exception e) {
			log.error("MgMrUtil.query error：" + e.getMessage(), e);
		}

		return null;
	}
	
	/**
	 * 刪除檔案
	 * @param fileName : 檔案的名稱
	 * @param loginid : 登入id
	 * @return: String
	 */
	public String delete(Map<String, Object> ftpConfig, String fileName, String loginid) {
		return delete(ftpConfig, fileName, loginid, null);
	}

	/**
	 * 刪除檔案
	 * @param fileName : 檔案的名稱
	 * @param loginid : 登入id
	 * @return: String
	 */
	public String delete(Map<String, Object> ftpConfig, String fileName, String loginid, String crossAP) {
		log.info("開始執行MgMrUtil.delete");
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			log.info("開始傳參數");
			MultipartEntityBuilder entityBuilder = createMultipartEntityBuilder(b -> b
		            .addTextBody("filename", fileName, ContentType.TEXT_PLAIN)                               
		            .addTextBody("loginid", loginid, ContentType.TEXT_PLAIN)                              
		            .addTextBody("ipaddr", MapUtils.getString(ftpConfig, "ipaddr"), ContentType.TEXT_PLAIN) 
		            .addTextBody("portno", MapUtils.getString(ftpConfig, "portno"), ContentType.TEXT_PLAIN)
		            .addTextBody("ftpdir", MapUtils.getString(ftpConfig, "ftpdir"), ContentType.TEXT_PLAIN));
			if(StringUtils.isNotBlank(crossAP)) entityBuilder.addTextBody("crossAP", crossAP, ContentType.TEXT_PLAIN);
			
			String urlStr = "https://" + PropertyHelper.getProperty(MG_MR_RELAY_API_PGID) + MG_MR_RELAY_API_URL + "delete" ;
			log.info("url為:" + urlStr);
	    	HttpPost httpPost = new HttpPost(urlStr);
	    	
	    	HttpEntity entity = entityBuilder.build();
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			
			log.info("發送API完成");
			String result = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
			log.info("resp：" + result);
			log.info("結束執行MgMrUtil.delete");
			return (String) new JSONObject(result).get("statuscode");
					
		} catch (Exception e) {
			log.error("MgMrUtil.delete error：" + e.getMessage(), e);
		}

		return "";
	}
	
	/**
	 * 更新檔案名稱
	 * @param fileName : 檔案的名稱
	 * @param replaceName : 更新後的名稱
	 * @param loginid : 登入id
	 * @return: String
	 */
	public String rename(Map<String, Object> ftpConfig, String fileName, String replaceName, String loginid) {
		return rename(ftpConfig, fileName, replaceName, loginid, null);
	}

	/**
	 * 更新檔案名稱
	 * @param fileName : 檔案的名稱
	 * @param replaceName : 更新後的名稱
	 * @param loginid : 登入id
	 * @return: String
	 */
	public String rename(Map<String, Object> ftpConfig, String fileName, String replaceName, String loginid, String crossAP) {
		log.info("開始執行MgMrUtil.rename");
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			
			log.info("開始傳參數");
			MultipartEntityBuilder entityBuilder = createMultipartEntityBuilder(b -> b
		            .addTextBody("sourcefilepath", MapUtils.getString(ftpConfig, "ftpdir") + File.separator + fileName, ContentType.TEXT_PLAIN)                               
		            .addTextBody("replacename", replaceName, ContentType.TEXT_PLAIN)                            
		            .addTextBody("loginid", loginid, ContentType.TEXT_PLAIN)                              
		            .addTextBody("ipaddr", MapUtils.getString(ftpConfig, "ipaddr"), ContentType.TEXT_PLAIN) 
		            .addTextBody("portno", MapUtils.getString(ftpConfig, "portno"), ContentType.TEXT_PLAIN)
		            .addTextBody("ftpdir", MapUtils.getString(ftpConfig, "ftpdir"), ContentType.TEXT_PLAIN));
			if(StringUtils.isNotBlank(crossAP)) entityBuilder.addTextBody("crossAP", crossAP, ContentType.TEXT_PLAIN);
			
			String urlStr = "https://" + PropertyHelper.getProperty(MG_MR_RELAY_API_PGID) + MG_MR_RELAY_API_URL + "rename" ;
			log.info("url為:" + urlStr);
	    	HttpPost httpPost = new HttpPost(urlStr);
	    	
	    	HttpEntity entity = entityBuilder.build();
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			
			log.info("發送API完成");
			if (responseEntity != null) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
					String line = reader.readLine();
					log.info("結束執行MgMrUtil.rename");
					return (String)new JSONObject(line).get("statuscode");
				}
			}
					
		} catch (Exception e) {
			log.error("MgMrUtil.rename error：" + e.getMessage(), e);
		}
		
		return "";
	}

	/**
	 * 搬移檔案
	 * @param fileName : 檔案的名稱
	 * @param targetDirPath : 目標資料夾路徑
	 * @param loginid : 登入id
	 * @param crossAP 
	 * @return: String
	 */
	public String move(Map<String, Object> ftpConfig, String fileName, String targetDirPath, String loginid) {
		return move(ftpConfig, fileName, targetDirPath, loginid, null);
	}

	/**
	 * 搬移檔案(跨系統操作)
	 * 
	 * @param sourcefilepath : 來源路徑 + 檔案名稱
	 * @param targetDirPath  : 目標資料夾路徑
	 * @param loginid        : 登入id
	 * @param crossAP        : 跨系統別
	 * @return  
	 * 
	 */
	public String move(Map<String, Object> ftpConfig, String sourcefilepath, String targetDirPath, String loginid, String crossAP) {
		log.info("開始執行MgMrUtil.move");

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			log.info("開始傳參數");
			MultipartEntityBuilder entityBuilder = createMultipartEntityBuilder(b -> b
		            .addTextBody("sourcefilepath",  sourcefilepath, ContentType.TEXT_PLAIN)                               
		            .addTextBody("targetdirpath", targetDirPath, ContentType.TEXT_PLAIN)                            
		            .addTextBody("loginid", loginid, ContentType.TEXT_PLAIN)                              
		            .addTextBody("ipaddr", MapUtils.getString(ftpConfig, "ipaddr"), ContentType.TEXT_PLAIN) 
		            .addTextBody("portno", MapUtils.getString(ftpConfig, "portno"), ContentType.TEXT_PLAIN)
		            .addTextBody("ftpdir", MapUtils.getString(ftpConfig, "ftpdir"), ContentType.TEXT_PLAIN)
					);
			if(StringUtils.isNotBlank(crossAP)) entityBuilder.addTextBody("crossAP", crossAP, ContentType.TEXT_PLAIN);
			
			String urlStr = "https://" + PropertyHelper.getProperty(MG_MR_RELAY_API_PGID) + MG_MR_RELAY_API_URL + "move";
			log.info("url為:" + urlStr);
			HttpPost httpPost = new HttpPost(urlStr);
			
	    	HttpEntity entity = entityBuilder.build();
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			log.info("發送API完成");
			
			if (responseEntity != null) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
					String line = reader.readLine();
					log.info("結束執行MgMrUtil.move");
					return (String)new JSONObject(line).get("statuscode");
				}
			}
					
		} catch (Exception e) {
			log.error("MgMrUtil.move error：" + e.getMessage(), e);
		}
		
		return "";
	}

	/**
	 * 檔案複製
	 * @param fileName : 檔案的名稱
	 * @param targetDirPath : 目標資料夾路徑
	 * @param loginid : 登入id
	 * @return: String
	 */
	public String copy(Map<String, Object> ftpConfig, String fileName, String targetDirPath, String loginid) {
		log.info("開始執行MgMrUtil.copy");
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			log.info("開始傳參數");
			MultipartEntityBuilder entityBuilder = createMultipartEntityBuilder(b -> b
		            .addTextBody("sourcefilepath", MapUtils.getString(ftpConfig, "ftpdir") + File.separator + fileName, ContentType.TEXT_PLAIN)                               
		            .addTextBody("targetdirpath", targetDirPath, ContentType.TEXT_PLAIN)                            
		            .addTextBody("loginid", loginid, ContentType.TEXT_PLAIN)                              
		            .addTextBody("ipaddr", MapUtils.getString(ftpConfig, "ipaddr"), ContentType.TEXT_PLAIN) 
		            .addTextBody("portno", MapUtils.getString(ftpConfig, "portno"), ContentType.TEXT_PLAIN)
		            .addTextBody("ftpdir", MapUtils.getString(ftpConfig, "ftpdir"), ContentType.TEXT_PLAIN));
			
			String urlStr = "https://" + PropertyHelper.getProperty(MG_MR_RELAY_API_PGID) + MG_MR_RELAY_API_URL + "copy" ;
			log.info("url為:" + urlStr);
			HttpPost httpPost = new HttpPost(urlStr);
			
	    	HttpEntity entity = entityBuilder.build();
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			log.info("發送API完成");

			if (responseEntity != null) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
					String line = reader.readLine();
					log.info("結束執行MgMrUtil.move");
					return (String)new JSONObject(line).get("statuscode");
				}
			}
					
		} catch (Exception e) {
			log.error("MgMrUtil.copy error：" + e.getMessage(), e);
		}
		
		return "";
	}

	/**
	 * 檔案複製:目標路徑由BACKUPDIR獲取
	 * @param fileName : 檔案的名稱
	 * @param loginid : 登入id
	 * @return: String
	 */
	public String backUp(Map<String, Object> ftpConfig, String fileName, String loginid) {
		log.info("開始執行MgMrUtil.backUp");
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			log.info("開始傳參數");
			MultipartEntityBuilder entityBuilder = createMultipartEntityBuilder(b -> b
		            .addTextBody("sourcefilepath", MapUtils.getString(ftpConfig, "ftpdir") + File.separator + fileName, ContentType.TEXT_PLAIN)                               
		            .addTextBody("targetdirpath", MapUtils.getString(ftpConfig, "backupdir"), ContentType.TEXT_PLAIN)                            
		            .addTextBody("loginid", loginid, ContentType.TEXT_PLAIN)                              
		            .addTextBody("ipaddr", MapUtils.getString(ftpConfig, "ipaddr"), ContentType.TEXT_PLAIN) 
		            .addTextBody("portno", MapUtils.getString(ftpConfig, "portno"), ContentType.TEXT_PLAIN)
		            .addTextBody("ftpdir", MapUtils.getString(ftpConfig, "ftpdir"), ContentType.TEXT_PLAIN));
			
			String urlStr = "https://" + PropertyHelper.getProperty(MG_MR_RELAY_API_PGID) + MG_MR_RELAY_API_URL + "copy";
			log.info("url為:" + urlStr);
			HttpPost httpPost = new HttpPost(urlStr);
			
	    	HttpEntity entity = entityBuilder.build();
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			log.info("發送API完成");

			if (responseEntity != null) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
					String line = reader.readLine();
					log.info("結束執行MgMrUtil.move");
					return (String)new JSONObject(line).get("statuscode");
				}
			}
					
		} catch (Exception e) {
			log.error("MgMrUtil.copy error：" + e.getMessage(), e);
		}

		return "";
	}
	
	private MultipartEntityBuilder createMultipartEntityBuilder(Consumer<MultipartEntityBuilder> consumer) {
	    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	    builder.setCharset(StandardCharsets.UTF_8);
	    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	    consumer.accept(builder);
	    return builder;
	}

    /**
     * 修正 Path Manipulation 路徑篡改問題
     * @param aString
     * @return
     */
    public String cleanString(String aString) {
    	if (aString == null) return null;
    	String cleanString = "";
    	for (int i = 0; i < aString.length(); ++i) {
    		cleanString += cleanChar(aString.charAt(i));
    	}
    	return cleanString;
    }
    
    private char cleanChar(char aChar) {

    	// 0 - 9
    	for (int i = 48; i < 58; ++i) {
    		if (aChar == i) return (char) i;
    	}

    	// 'A' - 'Z'
    	for (int i = 65; i < 91; ++i) {
    		if (aChar == i) return (char) i;
    	}

    	// 'a' - 'z'
    	for (int i = 97; i < 123; ++i) {
    		if (aChar == i) return (char) i;
    	}

    	// other valid characters
    	switch (aChar) {
    		case '/':
    			return '/';
    		case '.':
    			return '.';
    		case '-':
    			return '-';
    		case '_':
    			return '_';
    		case ' ':
    			return ' ';
    		case '\\':
    			return '\\';
    		case ':':
    			return ':';
    	}
    	return '%';
    }
    
}
