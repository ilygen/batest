package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;
/**
 * 行政支援記錄檔
 * 
 * @author swim
 */
@Table("MAADMREC")
public class Maadmrec implements Serializable{
    @PkeyField("MAADMRECID")
    private BigDecimal maAdmRecId; // 資料列編號
    @PkeyField("APNO")
    private String apNo;          //受理編號
    @PkeyField("PRODATE")
    private String proDate;       //承辦/創收日期
    @PkeyField("LETTERTYPE")
    private String letterType;    //函別
    @PkeyField("SEQNO")
    private String seqNo;         //序號
    private String issuYm;        //核定年月
    private String imk;           //保險別
    private String rpNo;          //文號
    private String slrelate;      //相關文號
    private String unitNo;        //提繳單位編號
    private String idNo;          //身分證號
    private String name;          //姓名
    private String brDate;        //出生日期
    private String subNo;         //辦事處代號
    private String proDepNo;      //承辦部室代號
    private String slrpNo;        //來受文號
    private String slDate;        //來受文日期
    private String unNo;          //來受文機關代碼
    private String unDes;         //來受文機關名稱
    private String caseTyp;       //公文案類
    private String note;          //主旨
    private String dealDesc;      //辦理情形
    private String ndomk1;        //函別內容一
    private String ndomk2;        //函別內容二
    private String seclvl;        //密等註記
    private String sddMk;         //公文承辦註記
    private String promoteUser;   //承辦人員
    private BigDecimal saveYear;  //保存年限
    private String delMk;         //刪除註記
    private String supflowMk;     //行政支援後續處理註記
    private String chgMk;         //更正別
    private String upadmMk;       //更正內容註記
    private String chkMk;         //勾稽註記
    private String bdMk;          //呆帳註記
    private String hurryMk;       //催辦註記
    private String rechkMk;       //複核決行註記
    private String finishDate;    //承辦完成日期
    private String addrMk;        //地址別
    private String owesYm;        //欠費/保費/給付起年月
    private String oweeYm;        //欠費/保費/給付迄年月
    private String prTyp;         //內容別
    private String reliefCause;   //救濟事由
    private String reliefTyp;     //救濟種類
    private String reliefKind;    //救濟種類
    private String reliefStat;    //行政救濟辦理情形
    private String closDate;      //銷案日期
    private String closeMk;       //銷案註記
    private String closeNo;       //銷案文號 
    private String hpNo;          //醫院代號
    private String injser;        //爭議部位
    private String ids;           //社福識別碼
    private String source;        //來源別
    private String omDate;        //發文日期
    private String crtUser;       //新增者代號
    private String crtTime;       //新增日期時間
    private String updUser;       //異動者代號
    private String updTime;       //異動日期時間   
    
    // Field not in MAADMERC
    // [  
    private String paramName;   // BAPARAM.paramName - 處理函別名稱 for 行政支援查詢 功能
    private String ndomkName1;  // BAPANDOMK.ndomkName - 處理註記一名稱 for 行政支援查詢 功能
    private String ndomkName2;  // BAPARAM.paramName - 處理註記二名稱 for 行政支援查詢 功能
    // ]
    public BigDecimal getMaAdmRecId() {
        return maAdmRecId;
    }
    public void setMaAdmRecId(BigDecimal maAdmRecId) {
        this.maAdmRecId = maAdmRecId;
    }
    public String getApNo() {
        return apNo;
    }
    public void setApNo(String apNo) {
        this.apNo = apNo;
    }
    public String getProDate() {
        return proDate;
    }
    public void setProDate(String proDate) {
        this.proDate = proDate;
    }
    public String getLetterType() {
        return letterType;
    }
    public void setLetterType(String letterType) {
        this.letterType = letterType;
    }
    public String getSeqNo() {
        return seqNo;
    }
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    public String getIssuYm() {
        return issuYm;
    }
    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }
    public String getImk() {
        return imk;
    }
    public void setImk(String imk) {
        this.imk = imk;
    }
    public String getRpNo() {
        return rpNo;
    }
    public void setRpNo(String rpNo) {
        this.rpNo = rpNo;
    }
    public String getSlrelate() {
        return slrelate;
    }
    public void setSlrelate(String slrelate) {
        this.slrelate = slrelate;
    }
    public String getUnitNo() {
        return unitNo;
    }
    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }
    public String getIdNo() {
        return idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrDate() {
        return brDate;
    }
    public void setBrDate(String brDate) {
        this.brDate = brDate;
    }
    public String getSubNo() {
        return subNo;
    }
    public void setSubNo(String subNo) {
        this.subNo = subNo;
    }
    public String getProDepNo() {
        return proDepNo;
    }
    public void setProDepNo(String proDepNo) {
        this.proDepNo = proDepNo;
    }
    public String getSlrpNo() {
        return slrpNo;
    }
    public void setSlrpNo(String slrpNo) {
        this.slrpNo = slrpNo;
    }
    public String getSlDate() {
        return slDate;
    }
    public void setSlDate(String slDate) {
        this.slDate = slDate;
    }
    public String getUnNo() {
        return unNo;
    }
    public void setUnNo(String unNo) {
        this.unNo = unNo;
    }
    public String getUnDes() {
        return unDes;
    }
    public void setUnDes(String unDes) {
        this.unDes = unDes;
    }
    public String getCaseTyp() {
        return caseTyp;
    }
    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getDealDesc() {
        return dealDesc;
    }
    public void setDealDesc(String dealDesc) {
        this.dealDesc = dealDesc;
    }
    public String getNdomk1() {
        return ndomk1;
    }
    public void setNdomk1(String ndomk1) {
        this.ndomk1 = ndomk1;
    }
    public String getNdomk2() {
        return ndomk2;
    }
    public void setNdomk2(String ndomk2) {
        this.ndomk2 = ndomk2;
    }
    public String getSeclvl() {
        return seclvl;
    }
    public void setSeclvl(String seclvl) {
        this.seclvl = seclvl;
    }
    public String getSddMk() {
        return sddMk;
    }
    public void setSddMk(String sddMk) {
        this.sddMk = sddMk;
    }
    public String getPromoteUser() {
        return promoteUser;
    }
    public void setPromoteUser(String promoteUser) {
        this.promoteUser = promoteUser;
    }
    public BigDecimal getSaveYear() {
        return saveYear;
    }
    public void setSaveYear(BigDecimal saveYear) {
        this.saveYear = saveYear;
    }
    public String getDelMk() {
        return delMk;
    }
    public void setDelMk(String delMk) {
        this.delMk = delMk;
    }
    public String getSupflowMk() {
        return supflowMk;
    }
    public void setSupflowMk(String supflowMk) {
        this.supflowMk = supflowMk;
    }
    public String getChgMk() {
        return chgMk;
    }
    public void setChgMk(String chgMk) {
        this.chgMk = chgMk;
    }
    public String getUpadmMk() {
        return upadmMk;
    }
    public void setUpadmMk(String upadmMk) {
        this.upadmMk = upadmMk;
    }
    public String getChkMk() {
        return chkMk;
    }
    public void setChkMk(String chkMk) {
        this.chkMk = chkMk;
    }
    public String getBdMk() {
        return bdMk;
    }
    public void setBdMk(String bdMk) {
        this.bdMk = bdMk;
    }
    public String getHurryMk() {
        return hurryMk;
    }
    public void setHurryMk(String hurryMk) {
        this.hurryMk = hurryMk;
    }
    public String getRechkMk() {
        return rechkMk;
    }
    public void setRechkMk(String rechkMk) {
        this.rechkMk = rechkMk;
    }
    public String getFinishDate() {
        return finishDate;
    }
    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
    public String getAddrMk() {
        return addrMk;
    }
    public void setAddrMk(String addrMk) {
        this.addrMk = addrMk;
    }
    public String getOwesYm() {
        return owesYm;
    }
    public void setOwesYm(String owesYm) {
        this.owesYm = owesYm;
    }
    public String getOweeYm() {
        return oweeYm;
    }
    public void setOweeYm(String oweeYm) {
        this.oweeYm = oweeYm;
    }
    public String getPrTyp() {
        return prTyp;
    }
    public void setPrTyp(String prTyp) {
        this.prTyp = prTyp;
    }
    public String getReliefCause() {
        return reliefCause;
    }
    public void setReliefCause(String reliefCause) {
        this.reliefCause = reliefCause;
    }
    public String getReliefTyp() {
        return reliefTyp;
    }
    public void setReliefTyp(String reliefTyp) {
        this.reliefTyp = reliefTyp;
    }
    public String getReliefKind() {
        return reliefKind;
    }
    public void setReliefKind(String reliefKind) {
        this.reliefKind = reliefKind;
    }
    public String getReliefStat() {
        return reliefStat;
    }
    public void setReliefStat(String reliefStat) {
        this.reliefStat = reliefStat;
    }
    public String getClosDate() {
        return closDate;
    }
    public void setClosDate(String closDate) {
        this.closDate = closDate;
    }
    public String getCloseMk() {
        return closeMk;
    }
    public void setCloseMk(String closeMk) {
        this.closeMk = closeMk;
    }
    public String getCloseNo() {
        return closeNo;
    }
    public void setCloseNo(String closeNo) {
        this.closeNo = closeNo;
    }
    public String getHpNo() {
        return hpNo;
    }
    public void setHpNo(String hpNo) {
        this.hpNo = hpNo;
    }
    public String getInjser() {
        return injser;
    }
    public void setInjser(String injser) {
        this.injser = injser;
    }
    public String getIds() {
        return ids;
    }
    public void setIds(String ids) {
        this.ids = ids;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getOmDate() {
        return omDate;
    }
    public void setOmDate(String omDate) {
        this.omDate = omDate;
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
    public String getParamName() {
        return paramName;
    }
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
    public String getNdomkName1() {
        return ndomkName1;
    }
    public void setNdomkName1(String ndomkName1) {
        this.ndomkName1 = ndomkName1;
    }
    public String getNdomkName2() {
        return ndomkName2;
    }
    public void setNdomkName2(String ndomkName2) {
        this.ndomkName2 = ndomkName2;
    }
}
