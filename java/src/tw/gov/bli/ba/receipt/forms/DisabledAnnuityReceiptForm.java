package tw.gov.bli.ba.receipt.forms;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import tw.gov.bli.common.annotation.PkeyField;

/**
 * 受理作業 - 失能年金給付受理作業 (BAAP0D020A)
 * 
 * @author Rickychi
 */
public class DisabledAnnuityReceiptForm extends BaseValidatorForm {
    private String method;
    private BigDecimal baappbaseId;// 給付主檔資料列編號
    private BigDecimal bafamilyId;// 眷屬檔資料列編號
    private BigDecimal bafamilytempId;// 資料列編號
    private BigDecimal baappexpandId;// 給付延伸主檔資料列編號
    private String seqNo;// 序號
    private String apNo;// 受理編號
    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    private String apNoFor36;// 國保受理編號
    private String apNo1For36;// 國保受理編號-1
    private String apNo2For36;// 國保受理編號-2
    private String apNo3For36;// 國保受理編號-3
    private String apNo4For36;// 國保受理編號-4
    private String appDate;// 申請日期
    private String apUbno;// 申請單位保險證號
    private String dataIdx; // 修改模式資料序號
    private String sysCode;// 系統類別

    // 事故者個人資料
    private String evtNationTpe;// 事故者國籍別
    private String evtSex;// 性別
    private String evtNationCode;// 事故者國籍
    private String evtNationCodeOption;// 事故者國籍下拉選單
    private String evtName;// 事故者姓名
    private String cvldtlName;// 戶籍姓名
    private String evtIdnNo;// 事故者身分證號
    private String evtBrDate;// 事故者出生日期
    private String tel1;// 電話1
    private String tel2;// 電話2
    private String commTyp;// 通訊地址別
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String grdIdnNo;// 法定代理人身分證號
    private String grdName;// 法定代理人姓名
    private String grdBrDate;// 法定代理人出生日期
    private String mobilePhone; // 手機複驗
    private String evtHandIcapMk;// 有無診斷書
    private String defaultGrdData;// 法定代理人預設眷屬1

    // 保險事故
    private String evAppTyp;// 申請傷病分類
    private String evTyp;// 核定傷病分類
    private String evtJobDate;// 診斷失能日期
    private String evCode;// 傷病原因
    private String criInPart1;// 受傷部位
    private String criInPart2;// 受傷部位
    private String criInPart3;// 受傷部位
    private String criMedium;// 媒 介 物
    private String injDte;// 傷病發生日期
    private String fvisitDte;// 初診日期
    private String lsurgeryDte;// 最後手術日期
    private String lsradiationDte;// 最後放射(化學)治療日期
    private String criInJdp1;// 失能項目
    private String criInJdp2;// 失能項目
    private String criInJdp3;// 失能項目
    private String criInJdp4;// 失能項目
    private String criInJdp5;// 失能項目
    private String criInJdp6;// 失能項目
    private String criInJdp7;// 失能項目
    private String criInJdp8;// 失能項目
    private String criInJdp9;// 失能項目
    private String criInJdp10;// 失能項目
    private String hosId;// 醫療院所代碼
    private String doctorName1;// 醫師姓名
    private String doctorName2;// 醫師姓名
    private String criInJnme1;// 國際疾病代碼
    private String criInJnme2;// 國際疾病代碼
    private String criInJnme3;// 國際疾病代碼
    private String criInJnme4;// 國際疾病代碼
    private String forinsAddr;// 外籍被保險人母國地址

    // 給付資料
    private String payTyp;// 給付方式
    private String payBankIdBranchId;// 帳號(前)
    private String payEeacc;// 帳號(後)
    private String chkPayBankIdChkBranchId;// 帳號複驗(前)
    private String chkPayEeacc;// 帳號複驗(後)
    private String payBankId;// 金融機構總代號
    private String branchId;// 分支代號
    private String chkPayBankId;// 帳號複驗 金融機構總代號
    private String chkBranchId;// 帳號複驗 分支代號

    // 眷屬資料
    private String famNationTyp;// 遺屬/眷屬國籍別
    private String famSex;// 遺屬/眷屬性別
    private String famNationCode;// 遺屬/眷屬國籍
    private String famNationCodeOption;// 遺屬/眷屬國籍下拉選單
    private String famName;// 遺屬/眷屬姓名
    private String famAppDate;// 遺屬/眷屬申請日期
    private String famIdnNo;// 遺屬/眷屬身分證號
    private String famBrDate;// 遺屬/眷屬出生日期
    private String famEvtRel;// 遺屬/眷屬與事故者關係
    private String marryDate;// 結婚日期
    private String raiseChildMk;// 配偶扶養註記
    private String studMk;// 在學註記
    private String monIncomeMk;// 每月工作收入註記
    private String monIncome;// 每月工作收入金額
    private String handIcapMk;// 領有重度以上身心障礙手冊
    private String interDictMk;// 受禁治產(監護)宣告

    private String actionTyp; // 案件新增/修改模式
    private String famOptionMode;// 眷屬資料新增/修改模式
    private String focusLocation;// foucs位置

	private String schoolCode; //學校代碼
	private String schoolCodeOption; //學校代碼 下單選單

    // 修改專用
    // [
    private String oldAppDate;// 申請日期
    private String oldApUbno;// 申請單位保險證號
    private String oldEvtNationTpe;// 事故者國籍別
    private String oldEvtSex;// 性別
    private String oldEvtNationCode;// 事故者國籍
    private String oldEvtNationCodeOption;// 事故者國籍下拉選單
    private String oldEvtName;// 事故者姓名
    private String oldCvldtlName;// 戶籍姓名
    private String oldEvtIdnNo;// 事故者身分證號
    private String oldEvtBrDate;// 事故者出生日期
    private String oldTel1;// 電話1
    private String oldTel2;// 電話2
    private String oldCommTyp;// 通訊地址別
    private String oldCommZip;// 通訊郵遞區號
    private String oldCommAddr;// 通訊地址
    private String oldGrdIdnNo;// 法定代理人身分證號
    private String oldGrdName;// 法定代理人姓名
    private String oldGrdBrDate;// 法定代理人出生日期
    private String oldMobilePhone; // 手機複驗
    private String oldEvtHandIcapMk;// 有無診斷書
    private String oldEvAppTyp;// 申請傷病分類
    private String oldEvTyp;// 核定傷病分類
    private String oldEvtJobDate;// 診斷失能日期
    private String oldEvCode;// 傷病原因
    private String oldCriInPart1;// 受傷部位
    private String oldCriInPart2;// 受傷部位
    private String oldCriInPart3;// 受傷部位
    private String oldCriMedium;// 媒 介 物
    private String oldCriInJdp1;// 失能項目
    private String oldCriInJdp2;// 失能項目
    private String oldCriInJdp3;// 失能項目
    private String oldCriInJdp4;// 失能項目
    private String oldCriInJdp5;// 失能項目
    private String oldCriInJdp6;// 失能項目
    private String oldCriInJdp7;// 失能項目
    private String oldCriInJdp8;// 失能項目
    private String oldCriInJdp9;// 失能項目
    private String oldCriInJdp10;// 失能項目
    private String oldHosId;// 醫療院所代碼
    private String oldDoctorName1;// 醫師姓名
    private String oldDoctorName2;// 醫師姓名
    private String oldCriInJnme1;// 國際疾病代碼
    private String oldCriInJnme2;// 國際疾病代碼
    private String oldCriInJnme3;// 國際疾病代碼
    private String oldCriInJnme4;// 國際疾病代碼
    private String oldPayTyp;// 給付方式
    private String oldPayBankIdBranchId;// 帳號(前)
    private String oldPayBankId;// 帳號(前)
    private String oldBranchId;// 帳號(前)
    private String oldPayEeacc;// 帳號(後)
    private BigDecimal oldBafamilytempId;// 資料列編號
    private String oldSeqNo;// 序號
    private String oldFamNationTyp;// 遺屬/眷屬國籍別
    private String oldFamSex;// 遺屬/眷屬性別
    private String oldFamNationCode;// 遺屬/眷屬國籍
    private String oldFamNationCodeOption;// 遺屬/眷屬國籍下拉選單
    private String oldFamName;// 遺屬/眷屬姓名
    private String oldFamAppDate;// 遺屬/眷屬申請日期
    private String oldFamIdnNo;// 遺屬/眷屬身分證號
    private String oldFamBrDate;// 遺屬/眷屬出生日期
    private String oldFamEvtRel;// 遺屬/眷屬與事故者關係
    private String oldMarryDate;// 結婚日期
    private String oldRaiseChildMk;// 配偶扶養註記
    private String oldStudMk;// 在學註記
    private String oldMonIncomeMk;// 每月工作收入註記
    private String oldMonIncome;// 每月工作收入金額
    private String oldHandIcapMk;// 領有重度以上身心障礙手冊
    private String oldInterDictMk;// 受禁治產(監護)宣告
    private String oldChkPayBankIdChkBranchId;// 帳號(前)
    private String oldChkPayEeacc;// 帳號(後)
    // ]

    // 新增/修改眷屬時暫存事故者資料用
    // [
    private String tempApNo1;// 受理編號-1
    private String tempApNo2;// 受理編號-2
    private String tempApNo3;// 受理編號-3
    private String tempApNo4;// 受理編號-4
    private String tempAppDate;// 申請日期
    private String tempApUbno;// 申請單位保險證號

    // 事故者個人資料
    private String tempEvtNationTpe;// 事故者國籍別
    private String tempEvtSex;// 性別
    private String tempEvtNationCode;// 事故者國籍
    private String tempEvtNationCodeOption;// 事故者國籍下拉選單
    private String tempEvtName;// 事故者姓名
    private String tempCvldtlName;// 戶籍姓名
    private String tempEvtIdnNo;// 事故者身分證號
    private String tempEvtBrDate;// 事故者出生日期
    private String tempTel1;// 電話1
    private String tempTel2;// 電話2
    private String tempCommTyp;// 通訊地址別
    private String tempCommZip;// 通訊郵遞區號
    private String tempCommAddr;// 通訊地址
    private String tempGrdIdnNo;// 法定代理人身分證號
    private String tempGrdName;// 法定代理人姓名
    private String tempGrdBrDate;// 法定代理人出生日期
    private String tempMobilePhone; // 手機複驗
    private String tempEvtHandIcapMk;// 有無診斷書
    private String tempDefaultGrdData;// 法定代理人預設眷屬1

    // 保險事故
    private String tempEvAppTyp;// 申請傷病分類
    private String tempEvTyp;// 核定傷病分類
    private String tempEvtJobDate;// 診斷失能日期
    private String tempEvCode;// 傷病原因
    private String tempCriInPart1;// 受傷部位
    private String tempCriInPart2;// 受傷部位
    private String tempCriInPart3;// 受傷部位
    private String tempCriMedium;// 媒 介 物
    private String tempInjDte;// 傷病發生日期
    private String tempFvisitDte;// 初診日期
    private String tempLsurgeryDte;// 最後手術日期
    private String tempLsradiationDte;// 最後放射(化學)治療日期
    private String tempCriInJdp1;// 失能項目
    private String tempCriInJdp2;// 失能項目
    private String tempCriInJdp3;// 失能項目
    private String tempCriInJdp4;// 失能項目
    private String tempCriInJdp5;// 失能項目
    private String tempCriInJdp6;// 失能項目
    private String tempCriInJdp7;// 失能項目
    private String tempCriInJdp8;// 失能項目
    private String tempCriInJdp9;// 失能項目
    private String tempCriInJdp10;// 失能項目
    private String tempHosId;// 醫療院所代碼
    private String tempDoctorName1;// 醫師姓名
    private String tempDoctorName2;// 醫師姓名
    private String tempCriInJnme1;// 國際疾病代碼
    private String tempCriInJnme2;// 國際疾病代碼
    private String tempCriInJnme3;// 國際疾病代碼
    private String tempCriInJnme4;// 國際疾病代碼
    private String tempForinsAddr;// 外籍被保險人母國地址

    // 給付資料
    private String tempPayTyp;// 給付方式
    private String tempPayBankIdBranchId;// 帳號(前)
    private String tempPayEeacc;// 帳號(後)
    private String tempChkPayBankIdChkBranchId;// 帳號(前)
    private String tempChkPayEeacc;// 帳號(後)
    private String tempChkPayBankId;// 帳號(前)
    private String tempChkBranchId;// 帳號(前)
    private String tempPayBankId;// 帳號(前)
    private String tempBranchId;// 帳號(前)
    
    private String sFlag36;//國併勞
    private String origSFlag36; //頁面保留資料使用

    // ]

    public String getTempEvtNationTpe() {
        return tempEvtNationTpe;
    }

    public void setTempEvtNationTpe(String tempEvtNationTpe) {
        this.tempEvtNationTpe = tempEvtNationTpe;
    }

    public String getTempEvtSex() {
        return tempEvtSex;
    }

    public void setTempEvtSex(String tempEvtSex) {
        this.tempEvtSex = tempEvtSex;
    }

    public String getTempEvtNationCode() {
        return tempEvtNationCode;
    }

    public void setTempEvtNationCode(String tempEvtNationCode) {
        this.tempEvtNationCode = tempEvtNationCode;
    }

    public String getTempEvtNationCodeOption() {
        return tempEvtNationCodeOption;
    }

    public void setTempEvtNationCodeOption(String tempEvtNationCodeOption) {
        this.tempEvtNationCodeOption = tempEvtNationCodeOption;
    }

    public String getTempEvtName() {
        return tempEvtName;
    }

    public void setTempEvtName(String tempEvtName) {
        this.tempEvtName = tempEvtName;
    }

    public String getTempCvldtlName() {
        return tempCvldtlName;
    }

    public void setTempCvldtlName(String tempCvldtlName) {
        this.tempCvldtlName = tempCvldtlName;
    }

    public String getTempEvtIdnNo() {
        return tempEvtIdnNo;
    }

    public void setTempEvtIdnNo(String tempEvtIdnNo) {
        this.tempEvtIdnNo = tempEvtIdnNo;
    }

    public String getTempEvtBrDate() {
        return tempEvtBrDate;
    }

    public void setTempEvtBrDate(String tempEvtBrDate) {
        this.tempEvtBrDate = tempEvtBrDate;
    }

    public String getTempTel1() {
        return tempTel1;
    }

    public void setTempTel1(String tempTel1) {
        this.tempTel1 = tempTel1;
    }

    public String getTempTel2() {
        return tempTel2;
    }

    public void setTempTel2(String tempTel2) {
        this.tempTel2 = tempTel2;
    }

    public String getTempCommTyp() {
        return tempCommTyp;
    }

    public void setTempCommTyp(String tempCommTyp) {
        this.tempCommTyp = tempCommTyp;
    }

    public String getTempCommZip() {
        return tempCommZip;
    }

    public void setTempCommZip(String tempCommZip) {
        this.tempCommZip = tempCommZip;
    }

    public String getTempCommAddr() {
        return tempCommAddr;
    }

    public void setTempCommAddr(String tempCommAddr) {
        this.tempCommAddr = tempCommAddr;
    }

    public String getTempGrdIdnNo() {
        return tempGrdIdnNo;
    }

    public void setTempGrdIdnNo(String tempGrdIdnNo) {
        this.tempGrdIdnNo = tempGrdIdnNo;
    }

    public String getTempGrdName() {
        return tempGrdName;
    }

    public void setTempGrdName(String tempGrdName) {
        this.tempGrdName = tempGrdName;
    }

    public String getTempGrdBrDate() {
        return tempGrdBrDate;
    }

    public void setTempGrdBrDate(String tempGrdBrDate) {
        this.tempGrdBrDate = tempGrdBrDate;
    }

    public String getTempEvTyp() {
        return tempEvTyp;
    }

    public void setTempEvTyp(String tempEvTyp) {
        this.tempEvTyp = tempEvTyp;
    }

    public String getTempEvtJobDate() {
        return tempEvtJobDate;
    }

    public void setTempEvtJobDate(String tempEvtJobDate) {
        this.tempEvtJobDate = tempEvtJobDate;
    }

    public String getTempEvCode() {
        return tempEvCode;
    }

    public void setTempEvCode(String tempEvCode) {
        this.tempEvCode = tempEvCode;
    }

    public String getTempCriInPart1() {
        return tempCriInPart1;
    }

    public void setTempCriInPart1(String tempCriInPart1) {
        this.tempCriInPart1 = tempCriInPart1;
    }

    public String getTempCriInPart2() {
        return tempCriInPart2;
    }

    public void setTempCriInPart2(String tempCriInPart2) {
        this.tempCriInPart2 = tempCriInPart2;
    }

    public String getTempCriInPart3() {
        return tempCriInPart3;
    }

    public void setTempCriInPart3(String tempCriInPart3) {
        this.tempCriInPart3 = tempCriInPart3;
    }

    public String getTempCriMedium() {
        return tempCriMedium;
    }

    public void setTempCriMedium(String tempCriMedium) {
        this.tempCriMedium = tempCriMedium;
    }

    public String getTempCriInJdp1() {
        return tempCriInJdp1;
    }

    public void setTempCriInJdp1(String tempCriInJdp1) {
        this.tempCriInJdp1 = tempCriInJdp1;
    }

    public String getTempCriInJdp2() {
        return tempCriInJdp2;
    }

    public void setTempCriInJdp2(String tempCriInJdp2) {
        this.tempCriInJdp2 = tempCriInJdp2;
    }

    public String getTempCriInJdp3() {
        return tempCriInJdp3;
    }

    public void setTempCriInJdp3(String tempCriInJdp3) {
        this.tempCriInJdp3 = tempCriInJdp3;
    }

    public String getTempCriInJdp4() {
        return tempCriInJdp4;
    }

    public void setTempCriInJdp4(String tempCriInJdp4) {
        this.tempCriInJdp4 = tempCriInJdp4;
    }

    public String getTempCriInJdp5() {
        return tempCriInJdp5;
    }

    public void setTempCriInJdp5(String tempCriInJdp5) {
        this.tempCriInJdp5 = tempCriInJdp5;
    }

    public String getTempCriInJdp6() {
        return tempCriInJdp6;
    }

    public void setTempCriInJdp6(String tempCriInJdp6) {
        this.tempCriInJdp6 = tempCriInJdp6;
    }

    public String getTempCriInJdp7() {
        return tempCriInJdp7;
    }

    public void setTempCriInJdp7(String tempCriInJdp7) {
        this.tempCriInJdp7 = tempCriInJdp7;
    }

    public String getTempCriInJdp8() {
        return tempCriInJdp8;
    }

    public void setTempCriInJdp8(String tempCriInJdp8) {
        this.tempCriInJdp8 = tempCriInJdp8;
    }

    public String getTempCriInJdp9() {
        return tempCriInJdp9;
    }

    public void setTempCriInJdp9(String tempCriInJdp9) {
        this.tempCriInJdp9 = tempCriInJdp9;
    }

    public String getTempCriInJdp10() {
        return tempCriInJdp10;
    }

    public void setTempCriInJdp10(String tempCriInJdp10) {
        this.tempCriInJdp10 = tempCriInJdp10;
    }

    public String getTempHosId() {
        return tempHosId;
    }

    public void setTempHosId(String tempHosId) {
        this.tempHosId = tempHosId;
    }

    public String getTempDoctorName1() {
        return tempDoctorName1;
    }

    public void setTempDoctorName1(String tempDoctorName1) {
        this.tempDoctorName1 = tempDoctorName1;
    }

    public String getTempDoctorName2() {
        return tempDoctorName2;
    }

    public void setTempDoctorName2(String tempDoctorName2) {
        this.tempDoctorName2 = tempDoctorName2;
    }

    public String getTempCriInJnme1() {
        return tempCriInJnme1;
    }

    public void setTempCriInJnme1(String tempCriInJnme1) {
        this.tempCriInJnme1 = tempCriInJnme1;
    }

    public String getTempCriInJnme2() {
        return tempCriInJnme2;
    }

    public void setTempCriInJnme2(String tempCriInJnme2) {
        this.tempCriInJnme2 = tempCriInJnme2;
    }

    public String getTempCriInJnme3() {
        return tempCriInJnme3;
    }

    public void setTempCriInJnme3(String tempCriInJnme3) {
        this.tempCriInJnme3 = tempCriInJnme3;
    }

    public String getTempCriInJnme4() {
        return tempCriInJnme4;
    }

    public void setTempCriInJnme4(String tempCriInJnme4) {
        this.tempCriInJnme4 = tempCriInJnme4;
    }

    public String getTempPayTyp() {
        return tempPayTyp;
    }

    public void setTempPayTyp(String tempPayTyp) {
        this.tempPayTyp = tempPayTyp;
    }

    public String getTempPayBankIdBranchId() {
        return tempPayBankIdBranchId;
    }

    public void setTempPayBankIdBranchId(String tempPayBankIdBranchId) {
        this.tempPayBankIdBranchId = tempPayBankIdBranchId;
    }

    public String getTempPayEeacc() {
        return tempPayEeacc;
    }

    public void setTempPayEeacc(String tempPayEeacc) {
        this.tempPayEeacc = tempPayEeacc;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    // 移除眷屬資料
    public void cleanFamData() {
        bafamilytempId = null;// 資料列編號
        seqNo = "";// 序號
        famNationTyp = "";// 遺屬/眷屬國籍別
        famSex = "";// 遺屬/眷屬性別
        famNationCode = "";// 遺屬/眷屬國籍
        famNationCodeOption = "";// 遺屬/眷屬國籍下拉選單
        famName = "";// 遺屬/眷屬姓名
        famAppDate = "";// 遺屬/眷屬申請日期
        famIdnNo = "";// 遺屬/眷屬身分證號
        famBrDate = "";// 遺屬/眷屬出生日期
        famEvtRel = "";// 遺屬/眷屬與事故者關係
        marryDate = "";// 結婚日期
        raiseChildMk = "";// 配偶扶養註記
        studMk = "";// 在學註記
        monIncomeMk = "";// 每月工作收入註記
        monIncome = "";// 每月工作收入金額
        handIcapMk = "";// 領有重度以上身心障礙手冊
        interDictMk = "";// 受禁治產(監護)宣告
        famOptionMode = "";
    }

    public String getApNoStr() {
        return getApNo1() + getApNo2() + getApNo3() + getApNo4();
    }
    
    public String getApNoStrFor36() {
        return getApNo1For36() + getApNo2For36() + getApNo3For36() + getApNo4For36();
    }

    public String getApNoStrDisplay() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

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

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getApUbno() {
        return apUbno;
    }

    public void setApUbno(String apUbno) {
        this.apUbno = apUbno;
    }

    public String getEvtNationTpe() {
        return evtNationTpe;
    }

    public void setEvtNationTpe(String evtNationTpe) {
        this.evtNationTpe = evtNationTpe;
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

    public String getEvtNationCodeOption() {
        return evtNationCodeOption;
    }

    public void setEvtNationCodeOption(String evtNationCodeOption) {
        this.evtNationCodeOption = evtNationCodeOption;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getCvldtlName() {
        return cvldtlName;
    }

    public void setCvldtlName(String cvldtlName) {
        this.cvldtlName = cvldtlName;
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

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getCommTyp() {
        return commTyp;
    }

    public void setCommTyp(String commTyp) {
        this.commTyp = commTyp;
    }

    public String getCommZip() {
        return commZip;
    }

    public void setCommZip(String commZip) {
        this.commZip = commZip;
    }

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    public String getGrdIdnNo() {
        return grdIdnNo;
    }

    public void setGrdIdnNo(String grdIdnNo) {
        this.grdIdnNo = grdIdnNo;
    }

    public String getGrdName() {
        return grdName;
    }

    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    public String getGrdBrDate() {
        return grdBrDate;
    }

    public void setGrdBrDate(String grdBrDate) {
        this.grdBrDate = grdBrDate;
    }

    public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getEvCode() {
        return evCode;
    }

    public void setEvCode(String evCode) {
        this.evCode = evCode;
    }

    public String getCriInPart1() {
        return criInPart1;
    }

    public void setCriInPart1(String criInPart1) {
        this.criInPart1 = criInPart1;
    }

    public String getCriInPart2() {
        return criInPart2;
    }

    public void setCriInPart2(String criInPart2) {
        this.criInPart2 = criInPart2;
    }

    public String getCriInPart3() {
        return criInPart3;
    }

    public void setCriInPart3(String criInPart3) {
        this.criInPart3 = criInPart3;
    }

    public String getCriMedium() {
        return criMedium;
    }

    public void setCriMedium(String criMedium) {
        this.criMedium = criMedium;
    }

    public String getCriInJdp1() {
        return criInJdp1;
    }

    public void setCriInJdp1(String criInJdp1) {
        this.criInJdp1 = criInJdp1;
    }

    public String getCriInJdp2() {
        return criInJdp2;
    }

    public void setCriInJdp2(String criInJdp2) {
        this.criInJdp2 = criInJdp2;
    }

    public String getCriInJdp3() {
        return criInJdp3;
    }

    public void setCriInJdp3(String criInJdp3) {
        this.criInJdp3 = criInJdp3;
    }

    public String getCriInJdp4() {
        return criInJdp4;
    }

    public void setCriInJdp4(String criInJdp4) {
        this.criInJdp4 = criInJdp4;
    }

    public String getCriInJdp5() {
        return criInJdp5;
    }

    public void setCriInJdp5(String criInJdp5) {
        this.criInJdp5 = criInJdp5;
    }

    public String getCriInJdp6() {
        return criInJdp6;
    }

    public void setCriInJdp6(String criInJdp6) {
        this.criInJdp6 = criInJdp6;
    }

    public String getCriInJdp7() {
        return criInJdp7;
    }

    public void setCriInJdp7(String criInJdp7) {
        this.criInJdp7 = criInJdp7;
    }

    public String getCriInJdp8() {
        return criInJdp8;
    }

    public void setCriInJdp8(String criInJdp8) {
        this.criInJdp8 = criInJdp8;
    }

    public String getCriInJdp9() {
        return criInJdp9;
    }

    public void setCriInJdp9(String criInJdp9) {
        this.criInJdp9 = criInJdp9;
    }

    public String getCriInJdp10() {
        return criInJdp10;
    }

    public void setCriInJdp10(String criInJdp10) {
        this.criInJdp10 = criInJdp10;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getDoctorName1() {
        return doctorName1;
    }

    public void setDoctorName1(String doctorName1) {
        this.doctorName1 = doctorName1;
    }

    public String getDoctorName2() {
        return doctorName2;
    }

    public void setDoctorName2(String doctorName2) {
        this.doctorName2 = doctorName2;
    }

    public String getCriInJnme1() {
        return criInJnme1;
    }

    public void setCriInJnme1(String criInJnme1) {
        this.criInJnme1 = criInJnme1;
    }

    public String getCriInJnme2() {
        return criInJnme2;
    }

    public void setCriInJnme2(String criInJnme2) {
        this.criInJnme2 = criInJnme2;
    }

    public String getCriInJnme3() {
        return criInJnme3;
    }

    public void setCriInJnme3(String criInJnme3) {
        this.criInJnme3 = criInJnme3;
    }

    public String getCriInJnme4() {
        return criInJnme4;
    }

    public void setCriInJnme4(String criInJnme4) {
        this.criInJnme4 = criInJnme4;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getPayBankIdBranchId() {
    	if(StringUtils.isBlank(payBankId)){
    		payBankId = "";
    	}
    	if(StringUtils.isBlank(branchId)){
    		branchId = "";
    	}
    	payBankIdBranchId = payBankId + branchId;
        return payBankIdBranchId;
    }

    public void setPayBankIdBranchId(String payBankIdBranchId) {
        this.payBankIdBranchId = payBankIdBranchId;
    }

    public String getPayEeacc() {
        return payEeacc;
    }

    public void setPayEeacc(String payEeacc) {
        this.payEeacc = payEeacc;
    }

    public BigDecimal getBafamilytempId() {
        return bafamilytempId;
    }

    public void setBafamilytempId(BigDecimal bafamilytempId) {
        this.bafamilytempId = bafamilytempId;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFamNationTyp() {
        return famNationTyp;
    }

    public void setFamNationTyp(String famNationTyp) {
        this.famNationTyp = famNationTyp;
    }

    public String getFamSex() {
        return famSex;
    }

    public void setFamSex(String famSex) {
        this.famSex = famSex;
    }

    public String getFamNationCode() {
        return famNationCode;
    }

    public void setFamNationCode(String famNationCode) {
        this.famNationCode = famNationCode;
    }

    public String getFamNationCodeOption() {
        return famNationCodeOption;
    }

    public void setFamNationCodeOption(String famNationCodeOption) {
        this.famNationCodeOption = famNationCodeOption;
    }

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
    }

    public String getFamAppDate() {
        return famAppDate;
    }

    public void setFamAppDate(String famAppDate) {
        this.famAppDate = famAppDate;
    }

    public String getFamIdnNo() {
        return famIdnNo;
    }

    public void setFamIdnNo(String famIdnNo) {
        this.famIdnNo = famIdnNo;
    }

    public String getFamBrDate() {
        return famBrDate;
    }

    public void setFamBrDate(String famBrDate) {
        this.famBrDate = famBrDate;
    }

    public String getFamEvtRel() {
        return famEvtRel;
    }

    public void setFamEvtRel(String famEvtRel) {
        this.famEvtRel = famEvtRel;
    }

    public String getMarryDate() {
        return marryDate;
    }

    public void setMarryDate(String marryDate) {
        this.marryDate = marryDate;
    }

    public String getRaiseChildMk() {
        return raiseChildMk;
    }

    public void setRaiseChildMk(String raiseChildMk) {
        this.raiseChildMk = raiseChildMk;
    }

    public String getStudMk() {
        return studMk;
    }

    public void setStudMk(String studMk) {
        this.studMk = studMk;
    }

    public String getMonIncomeMk() {
        return monIncomeMk;
    }

    public void setMonIncomeMk(String monIncomeMk) {
        this.monIncomeMk = monIncomeMk;
    }

    public String getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(String monIncome) {
        this.monIncome = monIncome;
    }

    public String getHandIcapMk() {
        return handIcapMk;
    }

    public void setHandIcapMk(String handIcapMk) {
        this.handIcapMk = handIcapMk;
    }

    public String getInterDictMk() {
        return interDictMk;
    }

    public void setInterDictMk(String interDictMk) {
        this.interDictMk = interDictMk;
    }

    public String getActionTyp() {
        return actionTyp;
    }

    public void setActionTyp(String actionTyp) {
        this.actionTyp = actionTyp;
    }

    public String getOldAppDate() {
        return oldAppDate;
    }

    public void setOldAppDate(String oldAppDate) {
        this.oldAppDate = oldAppDate;
    }

    public String getOldApUbno() {
        return oldApUbno;
    }

    public void setOldApUbno(String oldApUbno) {
        this.oldApUbno = oldApUbno;
    }

    public String getOldEvtNationTpe() {
        return oldEvtNationTpe;
    }

    public void setOldEvtNationTpe(String oldEvtNationTpe) {
        this.oldEvtNationTpe = oldEvtNationTpe;
    }

    public String getOldEvtSex() {
        return oldEvtSex;
    }

    public void setOldEvtSex(String oldEvtSex) {
        this.oldEvtSex = oldEvtSex;
    }

    public String getOldEvtNationCode() {
        return oldEvtNationCode;
    }

    public void setOldEvtNationCode(String oldEvtNationCode) {
        this.oldEvtNationCode = oldEvtNationCode;
    }

    public String getOldEvtNationCodeOption() {
        return oldEvtNationCodeOption;
    }

    public void setOldEvtNationCodeOption(String oldEvtNationCodeOption) {
        this.oldEvtNationCodeOption = oldEvtNationCodeOption;
    }

    public String getOldEvtName() {
        return oldEvtName;
    }

    public void setOldEvtName(String oldEvtName) {
        this.oldEvtName = oldEvtName;
    }

    public String getOldCvldtlName() {
        return oldCvldtlName;
    }

    public void setOldCvldtlName(String oldCvldtlName) {
        this.oldCvldtlName = oldCvldtlName;
    }

    public String getOldEvtIdnNo() {
        return oldEvtIdnNo;
    }

    public void setOldEvtIdnNo(String oldEvtIdnNo) {
        this.oldEvtIdnNo = oldEvtIdnNo;
    }

    public String getOldEvtBrDate() {
        return oldEvtBrDate;
    }

    public void setOldEvtBrDate(String oldEvtBrDate) {
        this.oldEvtBrDate = oldEvtBrDate;
    }

    public String getOldTel1() {
        return oldTel1;
    }

    public void setOldTel1(String oldTel1) {
        this.oldTel1 = oldTel1;
    }

    public String getOldTel2() {
        return oldTel2;
    }

    public void setOldTel2(String oldTel2) {
        this.oldTel2 = oldTel2;
    }

    public String getOldCommTyp() {
        return oldCommTyp;
    }

    public void setOldCommTyp(String oldCommTyp) {
        this.oldCommTyp = oldCommTyp;
    }

    public String getOldCommZip() {
        return oldCommZip;
    }

    public void setOldCommZip(String oldCommZip) {
        this.oldCommZip = oldCommZip;
    }

    public String getOldCommAddr() {
        return oldCommAddr;
    }

    public void setOldCommAddr(String oldCommAddr) {
        this.oldCommAddr = oldCommAddr;
    }

    public String getOldGrdIdnNo() {
        return oldGrdIdnNo;
    }

    public void setOldGrdIdnNo(String oldGrdIdnNo) {
        this.oldGrdIdnNo = oldGrdIdnNo;
    }

    public String getOldGrdName() {
        return oldGrdName;
    }

    public void setOldGrdName(String oldGrdName) {
        this.oldGrdName = oldGrdName;
    }

    public String getOldGrdBrDate() {
        return oldGrdBrDate;
    }

    public void setOldGrdBrDate(String oldGrdBrDate) {
        this.oldGrdBrDate = oldGrdBrDate;
    }

    public String getOldEvTyp() {
        return oldEvTyp;
    }

    public void setOldEvTyp(String oldEvTyp) {
        this.oldEvTyp = oldEvTyp;
    }

    public String getOldEvtJobDate() {
        return oldEvtJobDate;
    }

    public void setOldEvtJobDate(String oldEvtJobDate) {
        this.oldEvtJobDate = oldEvtJobDate;
    }

    public String getOldEvCode() {
        return oldEvCode;
    }

    public void setOldEvCode(String oldEvCode) {
        this.oldEvCode = oldEvCode;
    }

    public String getOldCriInPart1() {
        return oldCriInPart1;
    }

    public void setOldCriInPart1(String oldCriInPart1) {
        this.oldCriInPart1 = oldCriInPart1;
    }

    public String getOldCriInPart2() {
        return oldCriInPart2;
    }

    public void setOldCriInPart2(String oldCriInPart2) {
        this.oldCriInPart2 = oldCriInPart2;
    }

    public String getOldCriInPart3() {
        return oldCriInPart3;
    }

    public void setOldCriInPart3(String oldCriInPart3) {
        this.oldCriInPart3 = oldCriInPart3;
    }

    public String getOldCriMedium() {
        return oldCriMedium;
    }

    public void setOldCriMedium(String oldCriMedium) {
        this.oldCriMedium = oldCriMedium;
    }

    public String getOldCriInJdp1() {
        return oldCriInJdp1;
    }

    public void setOldCriInJdp1(String oldCriInJdp1) {
        this.oldCriInJdp1 = oldCriInJdp1;
    }

    public String getOldCriInJdp2() {
        return oldCriInJdp2;
    }

    public void setOldCriInJdp2(String oldCriInJdp2) {
        this.oldCriInJdp2 = oldCriInJdp2;
    }

    public String getOldCriInJdp3() {
        return oldCriInJdp3;
    }

    public void setOldCriInJdp3(String oldCriInJdp3) {
        this.oldCriInJdp3 = oldCriInJdp3;
    }

    public String getOldCriInJdp4() {
        return oldCriInJdp4;
    }

    public void setOldCriInJdp4(String oldCriInJdp4) {
        this.oldCriInJdp4 = oldCriInJdp4;
    }

    public String getOldCriInJdp5() {
        return oldCriInJdp5;
    }

    public void setOldCriInJdp5(String oldCriInJdp5) {
        this.oldCriInJdp5 = oldCriInJdp5;
    }

    public String getOldCriInJdp6() {
        return oldCriInJdp6;
    }

    public void setOldCriInJdp6(String oldCriInJdp6) {
        this.oldCriInJdp6 = oldCriInJdp6;
    }

    public String getOldCriInJdp7() {
        return oldCriInJdp7;
    }

    public void setOldCriInJdp7(String oldCriInJdp7) {
        this.oldCriInJdp7 = oldCriInJdp7;
    }

    public String getOldCriInJdp8() {
        return oldCriInJdp8;
    }

    public void setOldCriInJdp8(String oldCriInJdp8) {
        this.oldCriInJdp8 = oldCriInJdp8;
    }

    public String getOldCriInJdp9() {
        return oldCriInJdp9;
    }

    public void setOldCriInJdp9(String oldCriInJdp9) {
        this.oldCriInJdp9 = oldCriInJdp9;
    }

    public String getOldCriInJdp10() {
        return oldCriInJdp10;
    }

    public void setOldCriInJdp10(String oldCriInJdp10) {
        this.oldCriInJdp10 = oldCriInJdp10;
    }

    public String getOldHosId() {
        return oldHosId;
    }

    public void setOldHosId(String oldHosId) {
        this.oldHosId = oldHosId;
    }

    public String getOldDoctorName1() {
        return oldDoctorName1;
    }

    public void setOldDoctorName1(String oldDoctorName1) {
        this.oldDoctorName1 = oldDoctorName1;
    }

    public String getOldDoctorName2() {
        return oldDoctorName2;
    }

    public void setOldDoctorName2(String oldDoctorName2) {
        this.oldDoctorName2 = oldDoctorName2;
    }

    public String getOldCriInJnme1() {
        return oldCriInJnme1;
    }

    public void setOldCriInJnme1(String oldCriInJnme1) {
        this.oldCriInJnme1 = oldCriInJnme1;
    }

    public String getOldCriInJnme2() {
        return oldCriInJnme2;
    }

    public void setOldCriInJnme2(String oldCriInJnme2) {
        this.oldCriInJnme2 = oldCriInJnme2;
    }

    public String getOldCriInJnme3() {
        return oldCriInJnme3;
    }

    public void setOldCriInJnme3(String oldCriInJnme3) {
        this.oldCriInJnme3 = oldCriInJnme3;
    }

    public String getOldCriInJnme4() {
        return oldCriInJnme4;
    }

    public void setOldCriInJnme4(String oldCriInJnme4) {
        this.oldCriInJnme4 = oldCriInJnme4;
    }

    public String getOldPayTyp() {
        return oldPayTyp;
    }

    public void setOldPayTyp(String oldPayTyp) {
        this.oldPayTyp = oldPayTyp;
    }

    public String getOldPayBankIdBranchId() {
        return oldPayBankIdBranchId;
    }

    public void setOldPayBankIdBranchId(String oldPayBankIdBranchId) {
        this.oldPayBankIdBranchId = oldPayBankIdBranchId;
    }

    public String getOldPayEeacc() {
        return oldPayEeacc;
    }

    public void setOldPayEeacc(String oldPayEeacc) {
        this.oldPayEeacc = oldPayEeacc;
    }

    public BigDecimal getOldBafamilytempId() {
        return oldBafamilytempId;
    }

    public void setOldBafamilytempId(BigDecimal oldBafamilytempId) {
        this.oldBafamilytempId = oldBafamilytempId;
    }

    public String getOldSeqNo() {
        return oldSeqNo;
    }

    public void setOldSeqNo(String oldSeqNo) {
        this.oldSeqNo = oldSeqNo;
    }

    public String getOldFamNationTyp() {
        return oldFamNationTyp;
    }

    public void setOldFamNationTyp(String oldFamNationTyp) {
        this.oldFamNationTyp = oldFamNationTyp;
    }

    public String getOldFamSex() {
        return oldFamSex;
    }

    public void setOldFamSex(String oldFamSex) {
        this.oldFamSex = oldFamSex;
    }

    public String getOldFamNationCode() {
        return oldFamNationCode;
    }

    public void setOldFamNationCode(String oldFamNationCode) {
        this.oldFamNationCode = oldFamNationCode;
    }

    public String getOldFamNationCodeOption() {
        return oldFamNationCodeOption;
    }

    public void setOldFamNationCodeOption(String oldFamNationCodeOption) {
        this.oldFamNationCodeOption = oldFamNationCodeOption;
    }

    public String getOldFamName() {
        return oldFamName;
    }

    public void setOldFamName(String oldFamName) {
        this.oldFamName = oldFamName;
    }

    public String getOldFamAppDate() {
        return oldFamAppDate;
    }

    public void setOldFamAppDate(String oldFamAppDate) {
        this.oldFamAppDate = oldFamAppDate;
    }

    public String getOldFamIdnNo() {
        return oldFamIdnNo;
    }

    public void setOldFamIdnNo(String oldFamIdnNo) {
        this.oldFamIdnNo = oldFamIdnNo;
    }

    public String getOldFamBrDate() {
        return oldFamBrDate;
    }

    public void setOldFamBrDate(String oldFamBrDate) {
        this.oldFamBrDate = oldFamBrDate;
    }

    public String getOldFamEvtRel() {
        return oldFamEvtRel;
    }

    public void setOldFamEvtRel(String oldFamEvtRel) {
        this.oldFamEvtRel = oldFamEvtRel;
    }

    public String getOldMarryDate() {
        return oldMarryDate;
    }

    public void setOldMarryDate(String oldMarryDate) {
        this.oldMarryDate = oldMarryDate;
    }

    public String getOldRaiseChildMk() {
        return oldRaiseChildMk;
    }

    public void setOldRaiseChildMk(String oldRaiseChildMk) {
        this.oldRaiseChildMk = oldRaiseChildMk;
    }

    public String getOldStudMk() {
        return oldStudMk;
    }

    public void setOldStudMk(String oldStudMk) {
        this.oldStudMk = oldStudMk;
    }

    public String getOldMonIncomeMk() {
        return oldMonIncomeMk;
    }

    public void setOldMonIncomeMk(String oldMonIncomeMk) {
        this.oldMonIncomeMk = oldMonIncomeMk;
    }

    public String getOldMonIncome() {
        return oldMonIncome;
    }

    public void setOldMonIncome(String oldMonIncome) {
        this.oldMonIncome = oldMonIncome;
    }

    public String getOldHandIcapMk() {
        return oldHandIcapMk;
    }

    public void setOldHandIcapMk(String oldHandIcapMk) {
        this.oldHandIcapMk = oldHandIcapMk;
    }

    public String getOldInterDictMk() {
        return oldInterDictMk;
    }

    public void setOldInterDictMk(String oldInterDictMk) {
        this.oldInterDictMk = oldInterDictMk;
    }

    public String getFamOptionMode() {
        return famOptionMode;
    }

    public void setFamOptionMode(String famOptionMode) {
        this.famOptionMode = famOptionMode;
    }

    public BigDecimal getBafamilyId() {
        return bafamilyId;
    }

    public void setBafamilyId(BigDecimal bafamilyId) {
        this.bafamilyId = bafamilyId;
    }

    public BigDecimal getBaappexpandId() {
        return baappexpandId;
    }

    public void setBaappexpandId(BigDecimal baappexpandId) {
        this.baappexpandId = baappexpandId;
    }

    public String getTempApNo1() {
        return tempApNo1;
    }

    public void setTempApNo1(String tempApNo1) {
        this.tempApNo1 = tempApNo1;
    }

    public String getTempApNo2() {
        return tempApNo2;
    }

    public void setTempApNo2(String tempApNo2) {
        this.tempApNo2 = tempApNo2;
    }

    public String getTempApNo3() {
        return tempApNo3;
    }

    public void setTempApNo3(String tempApNo3) {
        this.tempApNo3 = tempApNo3;
    }

    public String getTempApNo4() {
        return tempApNo4;
    }

    public void setTempApNo4(String tempApNo4) {
        this.tempApNo4 = tempApNo4;
    }

    public String getTempAppDate() {
        return tempAppDate;
    }

    public void setTempAppDate(String tempAppDate) {
        this.tempAppDate = tempAppDate;
    }

    public String getTempApUbno() {
        return tempApUbno;
    }

    public void setTempApUbno(String tempApUbno) {
        this.tempApUbno = tempApUbno;
    }

    public String getDataIdx() {
        return dataIdx;
    }

    public void setDataIdx(String dataIdx) {
        this.dataIdx = dataIdx;
    }

    public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getFocusLocation() {
        return focusLocation;
    }

    public void setFocusLocation(String focusLocation) {
        this.focusLocation = focusLocation;
    }

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getSchoolCodeOption() {
		return schoolCodeOption;
	}

	public void setSchoolCodeOption(String schoolCodeOption) {
		this.schoolCodeOption = schoolCodeOption;
	}

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEvtHandIcapMk() {
        return evtHandIcapMk;
    }

    public void setEvtHandIcapMk(String evtHandIcapMk) {
        this.evtHandIcapMk = evtHandIcapMk;
    }

    public String getDefaultGrdData() {
        return defaultGrdData;
    }

    public void setDefaultGrdData(String defaultGrdData) {
        this.defaultGrdData = defaultGrdData;
    }

    public String getOldMobilePhone() {
        return oldMobilePhone;
    }

    public void setOldMobilePhone(String oldMobilePhone) {
        this.oldMobilePhone = oldMobilePhone;
    }

    public String getOldEvtHandIcapMk() {
        return oldEvtHandIcapMk;
    }

    public void setOldEvtHandIcapMk(String oldEvtHandIcapMk) {
        this.oldEvtHandIcapMk = oldEvtHandIcapMk;
    }

    public String getTempMobilePhone() {
        return tempMobilePhone;
    }

    public void setTempMobilePhone(String tempMobilePhone) {
        this.tempMobilePhone = tempMobilePhone;
    }

    public String getTempEvtHandIcapMk() {
        return tempEvtHandIcapMk;
    }

    public void setTempEvtHandIcapMk(String tempEvtHandIcapMk) {
        this.tempEvtHandIcapMk = tempEvtHandIcapMk;
    }

    public String getTempDefaultGrdData() {
        return tempDefaultGrdData;
    }

    public void setTempDefaultGrdData(String tempDefaultGrdData) {
        this.tempDefaultGrdData = tempDefaultGrdData;
    }

    public String getChkPayBankIdChkBranchId() {
    	if(StringUtils.isBlank(chkPayBankId)){
    		chkPayBankId = "";
    	}
    	if(StringUtils.isBlank(chkBranchId)){
    		chkBranchId = "";
    	}
        chkPayBankIdChkBranchId = chkPayBankId + chkBranchId;
    	
		return chkPayBankIdChkBranchId;
	}

	public void setChkPayBankIdChkBranchId(String chkPayBankIdChkBranchId) {
		this.chkPayBankIdChkBranchId = chkPayBankIdChkBranchId;
	}

	public String getChkPayEeacc() {
		return chkPayEeacc;
	}

	public void setChkPayEeacc(String chkPayEeacc) {
		this.chkPayEeacc = chkPayEeacc;
	}

	public String getTempChkPayBankIdChkBranchId() {
		return tempChkPayBankIdChkBranchId;
	}

	public void setTempChkPayBankIdChkBranchId(String tempChkPayBankIdChkBranchId) {
		this.tempChkPayBankIdChkBranchId = tempChkPayBankIdChkBranchId;
	}

	public String getTempChkPayEeacc() {
		return tempChkPayEeacc;
	}

	public void setTempChkPayEeacc(String tempChkPayEeacc) {
		this.tempChkPayEeacc = tempChkPayEeacc;
	}

	public String getApNoFor36() {
		return apNoFor36;
	}

	public void setApNoFor36(String apNoFor36) {
		this.apNoFor36 = apNoFor36;
	}

	public String getApNo1For36() {
		return apNo1For36;
	}

	public void setApNo1For36(String apNo1For36) {
		this.apNo1For36 = apNo1For36;
	}

	public String getApNo2For36() {
		return apNo2For36;
	}

	public void setApNo2For36(String apNo2For36) {
		this.apNo2For36 = apNo2For36;
	}

	public String getApNo3For36() {
		return apNo3For36;
	}

	public void setApNo3For36(String apNo3For36) {
		this.apNo3For36 = apNo3For36;
	}

	public String getApNo4For36() {
		return apNo4For36;
	}

	public void setApNo4For36(String apNo4For36) {
		this.apNo4For36 = apNo4For36;
	}

	public String getsFlag36() {
		return sFlag36;
	}

	public void setsFlag36(String sFlag36) {
		this.sFlag36 = sFlag36;
	}

	public String getOrigSFlag36() {
		return origSFlag36;
	}

	public void setOrigSFlag36(String origSFlag36) {
		this.origSFlag36 = origSFlag36;
	}

	public String getPayBankId() {
		return payBankId;
	}

	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getChkPayBankId() {
		return chkPayBankId;
	}

	public void setChkPayBankId(String chkPayBankId) {
		this.chkPayBankId = chkPayBankId;
	}

	public String getChkBranchId() {
		return chkBranchId;
	}

	public void setChkBranchId(String chkBranchId) {
		this.chkBranchId = chkBranchId;
	}

	public String getOldPayBankId() {
		return oldPayBankId;
	}

	public void setOldPayBankId(String oldPayBankId) {
		this.oldPayBankId = oldPayBankId;
	}

	public String getOldBranchId() {
		return oldBranchId;
	}

	public void setOldBranchId(String oldBranchId) {
		this.oldBranchId = oldBranchId;
	}

	public String getTempChkPayBankId() {
		return tempChkPayBankId;
	}

	public void setTempChkPayBankId(String tempChkPayBankId) {
		this.tempChkPayBankId = tempChkPayBankId;
	}

	public String getTempChkBranchId() {
		return tempChkBranchId;
	}

	public void setTempChkBranchId(String tempChkBranchId) {
		this.tempChkBranchId = tempChkBranchId;
	}

	public String getTempPayBankId() {
		return tempPayBankId;
	}

	public void setTempPayBankId(String tempPayBankId) {
		this.tempPayBankId = tempPayBankId;
	}

	public String getTempBranchId() {
		return tempBranchId;
	}

	public void setTempBranchId(String tempBranchId) {
		this.tempBranchId = tempBranchId;
	}

	public String getOldChkPayBankIdChkBranchId() {
		return oldChkPayBankIdChkBranchId;
	}

	public void setOldChkPayBankIdChkBranchId(String oldChkPayBankIdChkBranchId) {
		this.oldChkPayBankIdChkBranchId = oldChkPayBankIdChkBranchId;
	}

	public String getOldChkPayEeacc() {
		return oldChkPayEeacc;
	}

	public void setOldChkPayEeacc(String oldChkPayEeacc) {
		this.oldChkPayEeacc = oldChkPayEeacc;
	}

	public String getInjDte() {
		return injDte;
	}

	public void setInjDte(String injDte) {
		this.injDte = injDte;
	}

	public String getFvisitDte() {
		return fvisitDte;
	}

	public void setFvisitDte(String fvisitDte) {
		this.fvisitDte = fvisitDte;
	}

	public String getLsurgeryDte() {
		return lsurgeryDte;
	}

	public void setLsurgeryDte(String lsurgeryDte) {
		this.lsurgeryDte = lsurgeryDte;
	}

	public String getLsradiationDte() {
		return lsradiationDte;
	}

	public void setLsradiationDte(String lsradiationDte) {
		this.lsradiationDte = lsradiationDte;
	}

	public String getForinsAddr() {
		return forinsAddr;
	}

	public void setForinsAddr(String forinsAddr) {
		this.forinsAddr = forinsAddr;
	}

	public String getTempInjDte() {
		return tempInjDte;
	}

	public void setTempInjDte(String tempInjDte) {
		this.tempInjDte = tempInjDte;
	}

	public String getTempFvisitDte() {
		return tempFvisitDte;
	}

	public void setTempFvisitDte(String tempFvisitDte) {
		this.tempFvisitDte = tempFvisitDte;
	}

	public String getTempLsurgeryDte() {
		return tempLsurgeryDte;
	}

	public void setTempLsurgeryDte(String tempLsurgeryDte) {
		this.tempLsurgeryDte = tempLsurgeryDte;
	}

	public String getTempLsradiationDte() {
		return tempLsradiationDte;
	}

	public void setTempLsradiationDte(String tempLsradiationDte) {
		this.tempLsradiationDte = tempLsradiationDte;
	}

	public String getTempForinsAddr() {
		return tempForinsAddr;
	}

	public void setTempForinsAddr(String tempForinsAddr) {
		this.tempForinsAddr = tempForinsAddr;
	}

	public String getEvAppTyp() {
		return evAppTyp;
	}

	public void setEvAppTyp(String evAppTyp) {
		this.evAppTyp = evAppTyp;
	}

	public String getOldEvAppTyp() {
		return oldEvAppTyp;
	}

	public void setOldEvAppTyp(String oldEvAppTyp) {
		this.oldEvAppTyp = oldEvAppTyp;
	}

	public String getTempEvAppTyp() {
		return tempEvAppTyp;
	}

	public void setTempEvAppTyp(String tempEvAppTyp) {
		this.tempEvAppTyp = tempEvAppTyp;
	}

}
