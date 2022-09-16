package tw.gov.bli.ba.receipt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 遺屬年金受理作業 事故者資料
 * 
 * @author Rickychi
 */
public class SurvivorAnnuityReceiptEvtCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private BigDecimal baappbaseId;// 給付主檔資料列編號
    private BigDecimal baappexpandId; // 給付延伸主檔資料列編號
    private BigDecimal bafamilytempId;// 遺屬暫存擋資料列編號

    private String apNo;// 受理編號
    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    private String seqNo;// 序號
    private String appDate;// 申請日期
    private String apUbno;// 申請單位保險證號
    private String apnoFm;// 來源受理編號
    private String sysCode;// 系統類別

    // 事故者個人資料
    private String evtNationTpe;// 國籍別
    private String evtDieDate;// 死亡日期
    private String evtSex;// 性別
    private String evtNationCode;// 國籍
    private String evtNationCodeeOption;// 國籍下拉選單
    private String evtName;// 事故者姓名
    private String cvldtlName;// 戶籍姓名
    private String evtIdnNo;// 事故者身分證號
    private String evtBrDate;// 事故者出生日期
    private String evAppTyp;// 申請傷病分類
    private String evTyp;// 核定傷病分類
    private String apItem;// 申請項目
    private String judgeDate;// 判決日期

    // Baappbase欄位
    private String evtJobDate;// 事故者離職日期
    private String evtAge;// 事故者年齡
    private String evtIds;// 事故者國保識別碼
    private String evtAppName;// 事故者申請時姓名

    private List<Baapplog> baapplogList;// 更正記錄檔

    public List<Baapplog> getBaapplogList() {
        return baapplogList;
    }

    public void setBaapplogList(List<Baapplog> baapplogList) {
        this.baapplogList = baapplogList;
    }

    // 頁面顯示轉換
    // [
    public String getApNoStrDisplay() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    }

    public String getAppDateStr() {
        if (StringUtils.isNotBlank(getAppDate()) && getAppDate().length() == 8) {
            return DateUtility.changeDateType(getAppDate());
        }
        else {
            return getAppDate();
        }
    }

    public String getEvtDieDateStr() {
        if (StringUtils.isNotBlank(getEvtDieDate()) && getEvtDieDate().length() == 8) {
            return DateUtility.changeDateType(getEvtDieDate());
        }
        else {
            return getEvtDieDate();
        }
    }

    public String getEvtJobDateStr() {
        if (StringUtils.isNotBlank(getEvtJobDate()) && getEvtJobDate().length() == 8) {
            return DateUtility.changeDateType(getEvtJobDate());
        }
        else {
            return getEvtJobDate();
        }
    }

    public String getEvtBrDateStr() {
        if (StringUtils.isNotBlank(getEvtBrDate()) && getEvtBrDate().length() == 8) {
            return DateUtility.changeDateType(getEvtBrDate());
        }
        else {
            return getEvtBrDate();
        }
    }

    public String getApItemStr() {
        String apItemStr = "";
        if (("1").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_1;
        }
        else if (("2").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_2;
        }
        else if (("3").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_3;
        }
        else if (("4").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_4;
        }
        else if (("5").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_5;
        }
        else if (("7").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_7;
        }
        else if (("8").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_8;
        }
        else if (("9").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_9;
        }
        else if (("0").equals(getApItem())) {
            apItemStr = ConstantKey.BAAPPBASE_APITEM_CODESTR_0;
        }
        return apItemStr;
    }

    // ]

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getApNo1() {
        return apNo1;
    }

    public void setApNo1(String apNo1) {
        this.apNo1 = apNo1;
    }

    public String getApNo2() {
        return apNo2;
    }

    public void setApNo2(String apNo2) {
        this.apNo2 = apNo2;
    }

    public String getApNo3() {
        return apNo3;
    }

    public void setApNo3(String apNo3) {
        this.apNo3 = apNo3;
    }

    public String getApNo4() {
        return apNo4;
    }

    public void setApNo4(String apNo4) {
        this.apNo4 = apNo4;
    }

    public String getApUbno() {
        return apUbno;
    }

    public void setApUbno(String apUbno) {
        this.apUbno = apUbno;
    }

    public String getApnoFm() {
		return apnoFm;
	}

	public void setApnoFm(String apnoFm) {
		this.apnoFm = apnoFm;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getEvtNationTpe() {
        return evtNationTpe;
    }

    public void setEvtNationTpe(String evtNationTpe) {
        this.evtNationTpe = evtNationTpe;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getEvtSex() {
        return evtSex;
    }

    public void setEvtSex(String evtSex) {
        this.evtSex = evtSex;
    }

    public String getEvtNationCode() {
        return evtNationCode;
    }

    public void setEvtNationCode(String evtNationCode) {
        this.evtNationCode = evtNationCode;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getEvAppTyp() {
		return evAppTyp;
	}

	public void setEvAppTyp(String evAppTyp) {
		this.evAppTyp = evAppTyp;
	}

	public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
    }

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getJudgeDate() {
		return judgeDate;
	}

	public void setJudgeDate(String judgeDate) {
		this.judgeDate = judgeDate;
	}

	public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getEvtAge() {
        return evtAge;
    }

    public void setEvtAge(String evtAge) {
        this.evtAge = evtAge;
    }

    public String getEvtIds() {
        return evtIds;
    }

    public void setEvtIds(String evtIds) {
        this.evtIds = evtIds;
    }

    public String getEvtAppName() {
        return evtAppName;
    }

    public void setEvtAppName(String evtAppName) {
        this.evtAppName = evtAppName;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public BigDecimal getBaappexpandId() {
        return baappexpandId;
    }

    public void setBaappexpandId(BigDecimal baappexpandId) {
        this.baappexpandId = baappexpandId;
    }

    public String getEvtNationCodeeOption() {
        return evtNationCodeeOption;
    }

    public void setEvtNationCodeeOption(String evtNationCodeeOption) {
        this.evtNationCodeeOption = evtNationCodeeOption;
    }

    public String getCvldtlName() {
        return cvldtlName;
    }

    public void setCvldtlName(String cvldtlName) {
        this.cvldtlName = cvldtlName;
    }

    public BigDecimal getBafamilytempId() {
        return bafamilytempId;
    }

    public void setBafamilytempId(BigDecimal bafamilytempId) {
        this.bafamilytempId = bafamilytempId;
    }
}
