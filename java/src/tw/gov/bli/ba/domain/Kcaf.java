package tw.gov.bli.ba.domain;

public class Kcaf {
    private String INTyp;//保險別    
    private String BIdn;//原身分證號  
    private String BBrDte;//原出生日期 
    private String BName;//原姓名  
    private String proDte;//處理日期           
    private String proTim;//處理時間
    private String AIdn;//改後身分證號  
    private String ABrDte;//改後出生日期 
    private String AName;//改後姓名  
    private String mvcd;//搬移別   
    private String mlDte;//投遞日期  
    private String staff;//處理人員代號  
    public String getINTyp() {
        return INTyp;
    }
    public void setINTyp(String typ) {
        INTyp = typ;
    }
    public String getBIdn() {
        return BIdn;
    }
    public void setBIdn(String idn) {
        BIdn = idn;
    }
    public String getBBrDte() {
        return BBrDte;
    }
    public void setBBrDte(String brDte) {
        BBrDte = brDte;
    }
    public String getBName() {
        return BName;
    }
    public void setBName(String name) {
        BName = name;
    }
    public String getProDte() {
        return proDte;
    }
    public void setProDte(String proDte) {
        this.proDte = proDte;
    }
    public String getProTim() {
        return proTim;
    }
    public void setProTim(String proTim) {
        this.proTim = proTim;
    }
    public String getAIdn() {
        return AIdn;
    }
    public void setAIdn(String idn) {
        AIdn = idn;
    }
    public String getABrDte() {
        return ABrDte;
    }
    public void setABrDte(String brDte) {
        ABrDte = brDte;
    }
    public String getAName() {
        return AName;
    }
    public void setAName(String name) {
        AName = name;
    }
    public String getMvcd() {
        return mvcd;
    }
    public void setMvcd(String mvcd) {
        this.mvcd = mvcd;
    }
    public String getMlDte() {
        return mlDte;
    }
    public void setMlDte(String mlDte) {
        this.mlDte = mlDte;
    }
    public String getStaff() {
        return staff;
    }
    public void setStaff(String staff) {
        this.staff = staff;
    }
    
}
