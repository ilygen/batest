package tw.gov.bli.ba.bj.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import common.util.dbutil.DbToolsUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.forms.CheckExecForm;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.ExceptionUtility;

import java.io.InputStream;

public class CheckExecAction extends BaseDispatchAction {
	private BjService bjService;
	public ActionForward doUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		CheckExecForm iform = (CheckExecForm) form;
		String add = PropertyHelper.getProperty("ftpDbClient.serverAddress");
		int port = Integer.parseInt(PropertyHelper.getProperty("ftpDbClient.serverPort"));
		String ac = PropertyHelper.getProperty("ftpDbClient.userId");
		String pwd = DbToolsUtils.decrypt(PropertyHelper.getProperty("ftpDbClient.userPass"));
		String des = PropertyHelper.getProperty("ftpDbClient.dirForRecordFileMedia");
		FTPClient ftp = new FTPClient();
		try {
			ftp.connect(add, port);
			ftp.login(ac, pwd);
			ftp.setControlEncoding("UTF-8");
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			ftp.changeWorkingDirectory(new String(des.getBytes(),"ISO-8859-1"));
			InputStream fis = iform.getUploadFile().getInputStream();
			ftp.storeFile(new String(iform.getUploadFile().getFileName().getBytes(), "ISO-8859-1"), fis);
			fis.close();
			ftp.logout();
			ftp.disconnect();
		} catch (Exception e) {
			// 設定查詢失敗訊息
			log.error("CheckExecAction.doUpload() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
			saveMessages(session, DatabaseMessageHelper.failUpload());
			return mapping.findForward(ConstantKey.FORWARD_FAIL);
		}
		saveMessages(session ,DatabaseMessageHelper.successUpload());
		return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
	}
	public BjService getBjService() {
		return bjService;
	}

	public void setBjService(BjService bjService) {
		this.bjService = bjService;
	}
}
