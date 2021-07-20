package tw.gov.bli.ba.maint.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.util.DateUtil;

/**
 * Case for 勞保年金平均薪資月數參數維護修改作業
 *
 * @author Noctis
 */
public class AvgAmtMonMaintCase implements Serializable {
	
	private String effYear;      // 施行年度
	private String appMonth;     // 採計年月
	private String crtUser;      // 新增者代號
    private String crtTime;      // 新增日期時間
    private String updUser;      // 異動者代號
    private String updTime;      // 異動日期時間
    private String user;         // 異動人員
    private String date;         // 處理日期
    
    //判斷是否可以刪除
    public String getDeleteUpdateStr() {
    	if(StringUtils.isNotBlank(effYear)){
        if (Integer.parseInt(effYear) <= Integer.parseInt(DateUtility.getNowWestDate().substring(0, 4)))
            return "Y";
        else
            return "";
    	}else{
    		return "";
    	}
    }
    
	public String getDateStr() {
        if (StringUtils.isNotEmpty(date))
            return DateUtility.changeDateType(date);
        else
            return "";
    }
	
	public String getEffYearStr() {
        if (StringUtils.isNotEmpty(effYear))
            return DateUtility.changeWestYearType(effYear);
        else
            return "";
    }

	public String getEffYear() {
		return effYear;
	}

	public void setEffYear(String effYear) {
		this.effYear = effYear;
	}

	public String getAppMonth() {
		return appMonth;
	}

	public void setAppMonth(String appMonth) {
		this.appMonth = appMonth;
	}

	public String getCrtUser() {
		return crtUser;
	}

	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	public String getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}

	public String getUpdUser() {
		return updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	

}
