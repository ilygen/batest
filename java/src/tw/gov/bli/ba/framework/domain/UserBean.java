package tw.gov.bli.ba.framework.domain;

import java.io.Serializable;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.domain.UserInfoBean;

/**
 * 勞工保險年金給付系統的使用者物件
 * 
 * @author Goston
 */
public class UserBean extends UserInfoBean implements Serializable {
    private String loginDate; // 登入日期
    private String loginTime; // 登入時間
    private String headerFileName; // 系統要用哪個 Header 圖檔 (節日變換用)

    /**
     * 取得登入日期格式化字串
     * 
     * @return 登入日期格式化字串
     */
    public String getLoginDateString() {
        return DateUtility.formatChineseDateString(loginDate, true);
    }

    /**
     * 取得登入時間格式化字串
     * 
     * @return 登入時間格式化字串
     */
    public String getLoginTimeString() {
        return DateUtility.formatTimeString(loginTime, false);
    }

    public UserBean() {

    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getHeaderFileName() {
        return headerFileName;
    }

    public void setHeaderFileName(String headerFileName) {
        this.headerFileName = headerFileName;
    }

}
