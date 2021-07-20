package tw.gov.bli.ba.domain;

import java.io.Serializable;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 銀行資料檔
 * 
 * @author Rickychi
 */

@Table("NPBANKLIST")
public class Npbanklist implements Serializable {
    @PkeyField("MAIN_CODE")
    private String mainCode;// 主機構代碼
    @PkeyField("BRANCH_CODE")
    private String branchCode;// 分支機構代碼
    private String mainName;// 機構名稱
    private String shortName;// 機構簡稱
    private String mainAddr;// 機構地址
    private String mainTel;// 機構電話

    public String getMainCode() {
        return mainCode;
    }

    public void setMainCode(String mainCode) {
        this.mainCode = mainCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMainAddr() {
        return mainAddr;
    }

    public void setMainAddr(String mainAddr) {
        this.mainAddr = mainAddr;
    }

    public String getMainTel() {
        return mainTel;
    }

    public void setMainTel(String mainTel) {
        this.mainTel = mainTel;
    }

}
