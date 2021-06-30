package tw.gov.bli.ba.domain;

import java.io.Serializable;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 國家代碼檔
 * 
 * @author Rickychi
 */
@Table("BACOUNTRY")
public class Bacountry implements Serializable {
    @PkeyField("COUNTRY_ID")
    private String countryId;// 國家代碼
    private String cname;// 國家名稱
    
    public String getCountryId() {
        return countryId;
    }
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
}
