package tw.gov.bli.ba.domain;

import java.io.Serializable;

/**
 * 總經理署名圖
 *
 * @author ttlin
 */

public class Bamanager implements Serializable {
    private byte[] attachment;// 總經理署名圖

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

}
