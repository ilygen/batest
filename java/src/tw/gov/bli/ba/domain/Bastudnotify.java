package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoTable;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 遺屬在學期間檔 (<code>BASTUDNOTIFY</code>)
 * 
 * @author Noctis
 */
@Table("BASTUDNOTIFY")
public class Bastudnotify implements Serializable {
	
	@LogField("APNO")
    private String apNo;// 受理編號

    @LogField("SEQNO")
    private String seqNo;// 序號
    
    @LogField("EVTNAME")
    private String evtName;// 事故者姓名
    
    @LogField("BENNAME")
    private String benName;// 受益人姓名
    
    @LogField("NOTIFYTYPE")
    private String notifyType; //
    
    private String studeDate;// 月份

	public String getApNo() {
		return apNo;
	}

	public void setApNo(String apNo) {
		this.apNo = apNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getEvtName() {
		return evtName;
	}

	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}

	public String getBenName() {
		return benName;
	}

	public void setBenName(String benName) {
		this.benName = benName;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	
    public String getStudeDate() {
        return studeDate;
    }

    public void setStudeDate(String studeDate) {
        this.studeDate = studeDate;
    }

}
