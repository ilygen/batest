package tw.gov.bli.ba.domain;

import java.io.Serializable;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 承辦人分案原則參數檔
 * 
 * @author Rickychi
 */
@Table("BAPAALLOCATE")
public class Bapaallocate implements Serializable {
    @PkeyField("PAYCOND")
    private String payCode;// 給付別

    @PkeyField("EMPNO")
    private String empNo;// 員工編號

    private String empName;// 員工姓名
    private String empExt;// 員工分機
    private String alLocateSno;// 尾2碼起
    private String alLocateEno;// 尾2碼訖
    private String alLocateNo1;// 尾2碼斷號1
    private String alLocateNo2;// 尾2碼斷號2
    private String alLocateNo3;// 尾2碼斷號3
    private String alLocateNo4;// 尾2碼斷號4
    private String alLocateNo5;// 尾2碼斷號5
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private String updUser;// 異動者代號
    private String updTime;// 異動日期時間

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpExt() {
        return empExt;
    }

    public void setEmpExt(String empExt) {
        this.empExt = empExt;
    }

    public String getAlLocateSno() {
        return alLocateSno;
    }

    public void setAlLocateSno(String alLocateSno) {
        this.alLocateSno = alLocateSno;
    }

    public String getAlLocateEno() {
        return alLocateEno;
    }

    public void setAlLocateEno(String alLocateEno) {
        this.alLocateEno = alLocateEno;
    }

    public String getAlLocateNo1() {
        return alLocateNo1;
    }

    public void setAlLocateNo1(String alLocateNo1) {
        this.alLocateNo1 = alLocateNo1;
    }

    public String getAlLocateNo2() {
        return alLocateNo2;
    }

    public void setAlLocateNo2(String alLocateNo2) {
        this.alLocateNo2 = alLocateNo2;
    }

    public String getAlLocateNo3() {
        return alLocateNo3;
    }

    public void setAlLocateNo3(String alLocateNo3) {
        this.alLocateNo3 = alLocateNo3;
    }

    public String getAlLocateNo4() {
        return alLocateNo4;
    }

    public void setAlLocateNo4(String alLocateNo4) {
        this.alLocateNo4 = alLocateNo4;
    }

    public String getAlLocateNo5() {
        return alLocateNo5;
    }

    public void setAlLocateNo5(String alLocateNo5) {
        this.alLocateNo5 = alLocateNo5;
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

}
