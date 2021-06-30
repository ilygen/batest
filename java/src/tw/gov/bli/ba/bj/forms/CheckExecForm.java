package tw.gov.bli.ba.bj.forms;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class CheckExecForm extends BaseValidatorForm implements Serializable{
	private static final long serialVersionUID = -1284115293608300452L;
	private FormFile uploadFile; // 上傳的檔案

	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}
}
