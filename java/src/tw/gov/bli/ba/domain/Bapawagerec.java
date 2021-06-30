package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 勞保最高最低投保薪資紀錄檔
 * 
 * @author KIYOMI
 */

@Table("BAPAWAGEREC")
public class Bapawagerec implements Serializable {

    private String adjYm;// 調整年月
    private BigDecimal minWage;// 最低投保薪資
    private BigDecimal maxWage;// 最高投保薪資
    private String effectYmb; // 生效年月（起）
    private String effectYme; // 生效年月（迄）
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String updUser; // 異動者代號
    private String updTime; // 異動日期時間

    public String getAdjYm() {
        return adjYm;
    }

    public void setAdjYm(String adjYm) {
        this.adjYm = adjYm;
    }

    public BigDecimal getMinWage() {
        return minWage;
    }

    public void setMinWage(BigDecimal minWage) {
        this.minWage = minWage;
    }

    public BigDecimal getMaxWage() {
        return maxWage;
    }

    public void setMaxWage(BigDecimal maxWage) {
        this.maxWage = maxWage;
    }

    public String getEffectYmb() {
        return effectYmb;
    }

    public void setEffectYmb(String effectYmb) {
        this.effectYmb = effectYmb;
    }

    public String getEffectYme() {
        return effectYme;
    }

    public void setEffectYme(String effectYme) {
        this.effectYme = effectYme;
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
