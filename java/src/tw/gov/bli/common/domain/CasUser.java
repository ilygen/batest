package tw.gov.bli.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * CAS 登入時, 用來儲存使用者資料之 Domain Object<br>
 * Framework 使用<br>
 * 
 * @author Goston
 */
public class CasUser implements Serializable {
    /**
     * 使用者代碼
     */
    private String userId;

    /**
     * 使用者名稱
     */
    private String userName;

    /**
     * 員工編號
     */
    private String empId;

    /**
     * 部門代碼
     */
    private String deptId;

    public CasUser() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

}
