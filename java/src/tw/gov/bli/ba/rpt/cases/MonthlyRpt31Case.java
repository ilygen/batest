package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class MonthlyRpt31Case implements Serializable {
    private static final long serialVersionUID = -6541879602291890822L;
    private String apNo;
    private String wordNo;// 發文字號
    private String wordDate;// 發文日期
    private String unit;// 承辦單位
    private String comTel;// 連絡電話
    private String addrZip;// 郵遞區號
    private String addr;// 地址
    private String name;// 受文者姓名
    private List<String> message;// 主旨說明
    private String issuYm;
    private String pbm0001;// 核定通知書檢核碼
    private String manager;// 總經理名稱

    // 主旨 & 說明
    private String dataContL00101;
    private String dataContL00102;
    private String dataContL00103;
    private String dataContL00104;
    private String dataContL00105;
    private String dataContL00106;

    private String dataContL00201;
    private String dataContL00202;
    private String dataContL00203;
    private String dataContL00204;
    private String dataContL00205;
    private String dataContL00206;
    private String dataContL00207;

    private String dataContL00301;
    private String dataContL00302;
    private String dataContL00303;
    private String dataContL00304;
    private String dataContL00305;
    private String dataContL00306;

    private String dataContL001U01;
    private String dataContL002U01;
    private String dataContL003U01;

    private String dataContL001P01;
    private String dataContL002P01;
    private String dataContL003P01;

    // 說明二細項
    private String dataContSubitemL00201;
    private String dataContSubitemL00202;
    private String dataContSubitemL00203;
    private String dataContSubitemL00204;

    private String dataContSubitemL00301;
    private String dataContSubitemL00302;
    private String dataContSubitemL00303;
    private String dataContSubitemL00304;

    private String payType; // 給付別
    private String seqNo; // 受款人序
    private String payYm; // 給付年月
    private String evtName; // 事故者姓名
    private String benName; // 受益人姓名
    private String prpNo; // 初核人員
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String grdName;// 法定代理人姓名
    private String notifyForm;// 核定通知書格式
    private String evtIdnNo;// 事故者身分證號
    private String benSex;// 受益人性別
    private String evtBrDate;// 事故者出生日期
    private String evtDieDate;// 事故者死亡日期
    private String appDate;// 申請日期
    private String payYms;// 首次發放起月
    private String evtJobDate;// 事故者離職日期
    private BigDecimal marginAmt;// 老年差額金
    private BigDecimal oncePayM; // 一次給付月數
    private BigDecimal onceIssueAmt; // 一次給付金額
    private BigDecimal annuAmt;// 累計已領年金金額
    private BigDecimal befIssueAmt;// 核定總額
    private BigDecimal noldtY; // 老年年資(年)
    private BigDecimal noldtM; // 老年年資(月)
    private String dupeIdnNoMk;// 身分證重號註記

    public String getManagerStr() {
        int title = StringUtils.lastIndexOf(manager, "總經理") + 3;
        String name = StringUtils.substringAfterLast(manager, "總經理");
        String str = "";
        for (int i = 0; i < StringUtils.length(name); i++) {
            str = str + StringUtils.substring(name, i, i + 1) + "  ";
        }
        String managerName = StringUtils.substring(manager, 0, title) + "  " + str;
        return managerName;
    }

    public String getWordNo() {
        return wordNo;
    }

    public void setWordNo(String wordNo) {
        this.wordNo = wordNo;
    }

    public String getWordDate() {
        return wordDate;
    }

    public void setWordDate(String wordDate) {
        this.wordDate = wordDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getComTel() {
        return comTel;
    }

    public void setComTel(String comTel) {
        this.comTel = comTel;
    }

    public String getAddrZip() {
        return addrZip;
    }

    public void setAddrZip(String addrZip) {
        this.addrZip = addrZip;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getPbm0001() {
        return pbm0001;
    }

    public void setPbm0001(String pbm0001) {
        this.pbm0001 = pbm0001;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    // 主旨 & 說明
    public String getDataContL00101() {

        dataContL00101 = "<A003>君（身分證統一編號：<A004>，出生日期：<A005>）於<A006>死亡，其生前請領之老年年金給付案，請依說明段回復，請查照。";

        return dataContL00101;
    }

    public void setDataContL00101(String dataContL00101) {
        this.dataContL00101 = dataContL00101;
    }

    public String getDataContL00102() {

        dataContL00102 = "一、依據<A003>君所送勞工保險老年給付申請書件及本局審查結果辦理。";

        return dataContL00102;
    }

    public void setDataContL00102(String dataContL00102) {
        this.dataContL00102 = dataContL00102;
    }

    public String getDataContL00103() {

        dataContL00103 = "二、法令依據─勞工保險條例第65條之1第2項、第65條之2第2項、65條之2第3項、及第65條之3規定辦理。";

        return dataContL00103;
    }

    public void setDataContL00103(String dataContL00103) {
        this.dataContL00103 = dataContL00103;
    }

    public String getDataContL00104() {

        dataContL00104 = "三、本件<A003>君前於<A002>申請老年年金給付，經本局核定自<L011>起，按月於次月底前發給在案，今查<A003>君已於<A006>死亡，依照勞工保險條例第65條之2第3項規定，<L012>尚未匯入帳戶之老年年金給付<L013>元，得由其法定繼承人請領，請於文到30日內，填具老年年金給付共同委任及切結書，並補具死亡證明書、載有死亡日期之全戶戶籍謄本（記事勿省略）、法定繼承人現住址之戶籍謄本（記事勿省略）、具領人之金融機構存摺封面影本至局辦理請領手續。";

        return dataContL00104;
    }

    public void setDataContL00104(String dataContL00104) {
        this.dataContL00104 = dataContL00104;
    }

    public String getDataContL00105() {

        dataContL00105 = "四、另依同條例第63條之1第1項規定：「被保險人退保，於領取失能年金給付或老年年金給付期間死亡者，其符合前條（第63條）第2項規定之遺屬，得請領遺屬年金給付。」隨文檢附勞工保險本人死亡給付申請書及給付收據用紙，<A003>君死亡，請遺屬詳閱申請書背面說明一之（三）遺屬年金規定，如符合請領條件時，始得依該說明二（三）辦理遺屬年金給付請領手續。有關遺屬年金相關問題，請洽本局職業災害給付組死亡給付科（分機：2263）。";

        return dataContL00105;
    }

    public void setDataContL00105(String dataContL00105) {
        this.dataContL00105 = dataContL00105;
    }

    public String getDataContL00106() {

        dataContL00106 = "五、檢附勞工保險老年年金給付共同委任及切結書空白用紙乙份。";

        return dataContL00106;
    }

    public void setDataContL00106(String dataContL00106) {
        this.dataContL00106 = dataContL00106;
    }

    public String getDataContL00201() {

        dataContL00201 = "<A003>君（身分證統一編號：<A004>，出生日期：<A005>）於<A006>死亡，其生前請領之老年年金給付案，請依說明段回復，請查照。";

        return dataContL00201;
    }

    public void setDataContL00201(String dataContL00201) {
        this.dataContL00201 = dataContL00201;
    }

    public String getDataContL00202() {

        dataContL00202 = "一、依據<A003>君所送勞工保險老年給付申請書件及本局審查結果辦理。";

        return dataContL00202;
    }

    public void setDataContL00202(String dataContL00202) {
        this.dataContL00202 = dataContL00202;
    }

    public String getDataContL00203() {

        dataContL00203 = "二、法令依據─勞工保險條例第65條之1第2項、第65條之2第2項、65條之2第3項、及第65條之3規定辦理。";

        return dataContL00203;
    }

    public void setDataContL00203(String dataContL00203) {
        this.dataContL00203 = dataContL00203;
    }

    public String getDataContL00204() {

        dataContL00204 = "三、本件<A003>君前於<A002>申請老年年金給付，經本局核定自<L011>起，按月於次月底前發給在案，今查<A003>君已於<A006>死亡，依照勞工保險條例第65條之2第3項規定，<L012>尚未匯入帳戶之老年年金給付<L013>元，得由其法定繼承人請領。";

        return dataContL00204;
    }

    public void setDataContL00204(String dataContL00204) {
        this.dataContL00204 = dataContL00204;
    }

    public String getDataContL00205() {

        dataContL00205 = "四、次查<A003>君於<L014>離職退保時，年齡及年資已符合勞工保險條例第58條第2項一次請領老年給付之規定，依前揭規定，其遺屬得選擇承領「一次請領老年給付扣除已領年金給付總額之差額」或由符合請領年金給付條件之遺屬請領「遺屬年金給付」，給付標準分別如下：";

        return dataContL00205;
    }

    public void setDataContL00205(String dataContL00205) {
        this.dataContL00205 = dataContL00205;
    }

    public String getDataContL00206() {

        dataContL00206 = "五、<A003>君之遺屬欲選擇何種給付，請審慎考慮後，依以下所列檢具相關書據證件送局憑辦：";

        return dataContL00206;
    }

    public void setDataContL00206(String dataContL00206) {
        this.dataContL00206 = dataContL00206;
    }

    public String getDataContL00207() {

        dataContL00207 = "六、檢附勞工保險老年年金給付共同委任及切結書、老年給付差額申請書及給付收據、本人死亡給付申請書及給付收據。";

        return dataContL00207;
    }

    public void setDataContL00207(String dataContL00207) {
        this.dataContL00207 = dataContL00207;
    }

    public String getDataContL00301() {

        dataContL00301 = "<A003>君（身分證統一編號：<A004>，出生日期：<A005>）於<A006>死亡，其生前請領之老年年金給付案，請依說明段回復，請查照。";

        return dataContL00301;
    }

    public void setDataContL00301(String dataContL00301) {
        this.dataContL00301 = dataContL00301;
    }

    public String getDataContL00302() {

        dataContL00302 = "一、依據<A003>君所送勞工保險老年給付申請書件及本局審查結果辦理。";

        return dataContL00302;
    }

    public void setDataContL00302(String dataContL00302) {
        this.dataContL00302 = dataContL00302;
    }

    public String getDataContL00303() {

        dataContL00303 = "二、法令依據─勞工保險條例第65條之1第2項、第65條之2第2項、65條之2第3項、及第65條之3規定辦理。";

        return dataContL00303;
    }

    public void setDataContL00303(String dataContL00303) {
        this.dataContL00303 = dataContL00303;
    }

    public String getDataContL00304() {

        dataContL00304 = "三、查<A003>君於<L014>離職退保時，年齡及年資已符合勞工保險條例第58條第2項一次請領老年給付之規定，依前揭規定，其遺屬得選擇承領「一次請領老年給付扣除已領年金給付總額之差額」或由符合請領年金給付條件之遺屬請領「遺屬年金給付」，給付標準分別如下：";

        return dataContL00304;
    }

    public void setDataContL00304(String dataContL00304) {
        this.dataContL00304 = dataContL00304;
    }

    public String getDataContL00305() {

        dataContL00305 = "四、<A003>君之遺屬欲選擇何種給付，請審慎考慮後，依以下所列檢具相關書據證件送局憑辦：";

        return dataContL00305;
    }

    public void setDataContL00305(String dataContL00305) {
        this.dataContL00305 = dataContL00305;
    }

    public String getDataContL00306() {

        dataContL00306 = "五、檢附老年給付差額申請書及給付收據、本人死亡給付申請書及給付收據。";

        return dataContL00306;
    }

    public void setDataContL00306(String dataContL00306) {
        this.dataContL00306 = dataContL00306;
    }

    // 報表格式催辦 (001~003) 主旨文字如下，說明段與 001~003 相同
    public String getDataContL001U01() {

        dataContL001U01 = "<A003>君（身分證統一編號：<A004>，出生日期：<A005>）於<A006>死亡，其生前請領之老年年金給付案，本局前於<L023>函請依說明段補正手續，迄未見復，敬請於文到20日內儘速補正手續，逾期本局將依勞工保險條例第25條規定不予給付，請查照。(催辦)";

        return dataContL001U01;
    }

    public void setDataContL001U01(String dataContL001U01) {
        this.dataContL001U01 = dataContL001U01;
    }

    public String getDataContL002U01() {

        dataContL002U01 = "<A003>君（身分證統一編號：<A004>，出生日期：<A005>）於<A006>死亡，其生前請領之老年年金給付案，本局前於<L023>函請依說明段補正手續，迄未見復，敬請於文到20日內儘速補正手續，逾期本局將依勞工保險條例第25條規定不予給付，請查照。(催辦)";

        return dataContL002U01;
    }

    public void setDataContL002U01(String dataContL002U01) {
        this.dataContL002U01 = dataContL002U01;
    }

    public String getDataContL003U01() {

        dataContL003U01 = "<A003>君（身分證統一編號：<A004>，出生日期：<A005>）於<A006>死亡，其生前請領之老年年金給付案，本局前於<L023>函請依說明段補正手續，迄未見復，敬請於文到20日內儘速補正手續，逾期本局將依勞工保險條例第25條規定不予給付，請查照。(催辦)";

        return dataContL003U01;
    }

    public void setDataContL003U01(String dataContL003U01) {
        this.dataContL003U01 = dataContL003U01;
    }

    // 延不補正 (001~003) 主旨文字如下，說明段與 001~003 相同
    public String getDataContL001P01() {

        dataContL001P01 = "<A003>君（身分證統一編號：<A004>，出生日期：<A005>）於<A006>死亡，其生前請領之老年年金給付案，前經本局於<L023>函請就說明段補正手續，嗣於<L024>又函催在案，為時已久迄未獲復，無法核辦，依照勞工保險條例第25條規定，本局核定不予給付，請查照。(延不補正)";

        return dataContL001P01;
    }

    public void setDataContL001P01(String dataContL001P01) {
        this.dataContL001P01 = dataContL001P01;
    }

    public String getDataContL002P01() {

        dataContL002P01 = "<A003>君（身分證統一編號：<A004>，出生日期：<A005>）於<A006>死亡，其生前請領之老年年金給付案，前經本局於<L023>函請就說明段補正手續，嗣於<L024>又函催在案，為時已久迄未獲復，無法核辦，依照勞工保險條例第25條規定，本局核定不予給付，請查照。(延不補正)";

        return dataContL002P01;
    }

    public void setDataContL002P01(String dataContL002P01) {
        this.dataContL002P01 = dataContL002P01;
    }

    public String getDataContL003P01() {

        dataContL003P01 = "<A003>君（身分證統一編號：<A004>，出生日期：<A005>）於<A006>死亡，其生前請領之老年年金給付案，前經本局於<L023>函請就說明段補正手續，嗣於<L024>又函催在案，為時已久迄未獲復，無法核辦，依照勞工保險條例第25條規定，本局核定不予給付，請查照。(延不補正)";

        return dataContL003P01;
    }

    public void setDataContL003P01(String dataContL003P01) {
        this.dataContL003P01 = dataContL003P01;
    }

    // 說明二細項
    public String getDataContSubitemL00201() {

        dataContSubitemL00201 = "(一)一次請領老年給付之差額：<L015>元。〔計算方式為按<A003>君之老年一次給付年資<L016>年又<L017>個月，及離職退保當月起前3年之平均月投保薪資<L018>元計算，可發給一次請領老年給付<L019>個月，計<L020>元，扣除已領<L011>至<L021>之老年年金給付<L022>元，差額為<L015>元（含<L012>尚未匯入帳戶之老年年金給付合計<L013>元。）〕";

        return dataContSubitemL00201;
    }

    public void setDataContSubitemL00201(String dataContSubitemL00201) {
        this.dataContSubitemL00201 = dataContSubitemL00201;
    }

    public String getDataContSubitemL00202() {

        dataContSubitemL00202 = "(二)遺屬年金給付：計算方式為老年年金給付之半數，如尚有其他符合請領條件之遺屬，每多1人再按前述遺屬年金金額加計25%，最多加計50%。至有關遺屬年金相關問題，請洽本局職業災害給付組死亡給付科（分機：2263）。";

        return dataContSubitemL00202;
    }

    public void setDataContSubitemL00202(String dataContSubitemL00202) {
        this.dataContSubitemL00202 = dataContSubitemL00202;
    }

    public String getDataContSubitemL00203() {

        dataContSubitemL00203 = "(一)選擇承領「一次請領老年給付之差額」者：請於文到30日內，填具勞工保險老年給付差額申請書及給付收據（附空白用紙），並檢附死亡證明書、載有死亡日期之電腦化全戶戶籍謄本、受益人之電腦化戶籍謄本及金融機構存摺封面影本。";

        return dataContSubitemL00203;
    }

    public void setDataContSubitemL00203(String dataContSubitemL00203) {
        this.dataContSubitemL00203 = dataContSubitemL00203;
    }

    public String getDataContSubitemL00204() {

        dataContSubitemL00204 = "(二)選擇請領「遺屬年金給付」者：隨文檢附勞工保險本人死亡給付申請書及給付收據用紙，請遺屬詳閱申請書背面說明一之（三）遺屬年金規定，如符合請領條件時，始得依該說明二（三）辦理遺屬年金給付請領手續。另請填具勞工保險老年年金給付共同委任及切結書（附空白用紙），俾便辦理未及匯入帳戶之老年年金給付承領手續。";

        return dataContSubitemL00204;
    }

    public void setDataContSubitemL00204(String dataContSubitemL00204) {
        this.dataContSubitemL00204 = dataContSubitemL00204;
    }

    public String getDataContSubitemL00301() {

        dataContSubitemL00301 = "(一)一次請領老年給付之差額：<L015>元。（計算方式為按<A003>君之老年一次給付年資<L016>年又<L017>個月，及離職退保當月起前3年之平均月投保薪資<L018>元計算，可發給一次請領老年給付<L019>個月，計<L020>元，扣除已領<L011>至<L021>之老年年金給付<L022>元，差額為<L015>元）";

        return dataContSubitemL00301;
    }

    public void setDataContSubitemL00301(String dataContSubitemL00301) {
        this.dataContSubitemL00301 = dataContSubitemL00301;
    }

    public String getDataContSubitemL00302() {

        dataContSubitemL00302 = "(二)遺屬年金給付：計算方式為老年年金給付金額之半數，如尚有其他符合請領條件之遺屬，每多1人再按前述遺屬年金金額加計25%，最多加計50%。至有關遺屬年金相關問題，請洽本局職業災害給付組死亡給付科（分機：2263）。";

        return dataContSubitemL00302;
    }

    public void setDataContSubitemL00302(String dataContSubitemL00302) {
        this.dataContSubitemL00302 = dataContSubitemL00302;
    }

    public String getDataContSubitemL00303() {

        dataContSubitemL00303 = "(一)選擇承領「一次請領老年給付之差額」者：請於文到30日內，填具勞工保險老年給付差額申請書及給付收據（附空白用紙），並檢附死亡證明書、載有死亡日期之電腦化全戶戶籍謄本、受益人之電腦化戶籍謄本及金融機構存摺封面影本。";

        return dataContSubitemL00303;
    }

    public void setDataContSubitemL00303(String dataContSubitemL00303) {
        this.dataContSubitemL00303 = dataContSubitemL00303;
    }

    public String getDataContSubitemL00304() {

        dataContSubitemL00304 = "(二)選擇請領「遺屬年金給付」者：隨文檢附勞工保險本人死亡給付申請書及給付收據用紙，請遺屬詳閱申請書背面說明一之（三）遺屬年金規定，如符合請領條件時，始得依該說明二（三）辦理遺屬年金給付請領手續。";

        return dataContSubitemL00304;
    }

    public void setDataContSubitemL00304(String dataContSubitemL00304) {
        this.dataContSubitemL00304 = dataContSubitemL00304;
    }

    // --------------------------------------------------
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getPrpNo() {
        return prpNo;
    }

    public void setPrpNo(String prpNo) {
        this.prpNo = prpNo;
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

    public String getGrdName() {
        return grdName;
    }

    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getBenSex() {
        return benSex;
    }

    public void setBenSex(String benSex) {
        this.benSex = benSex;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getPayYms() {
        return payYms;
    }

    public void setPayYms(String payYms) {
        this.payYms = payYms;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public BigDecimal getMarginAmt() {
        return marginAmt;
    }

    public void setMarginAmt(BigDecimal marginAmt) {
        this.marginAmt = marginAmt;
    }

    public BigDecimal getOncePayM() {
        return oncePayM;
    }

    public void setOncePayM(BigDecimal oncePayM) {
        this.oncePayM = oncePayM;
    }

    public BigDecimal getOnceIssueAmt() {
        return onceIssueAmt;
    }

    public void setOnceIssueAmt(BigDecimal onceIssueAmt) {
        this.onceIssueAmt = onceIssueAmt;
    }

    public BigDecimal getAnnuAmt() {
        return annuAmt;
    }

    public void setAnnuAmt(BigDecimal annuAmt) {
        this.annuAmt = annuAmt;
    }

    public BigDecimal getBefIssueAmt() {
        return befIssueAmt;
    }

    public void setBefIssueAmt(BigDecimal befIssueAmt) {
        this.befIssueAmt = befIssueAmt;
    }

    public BigDecimal getNoldtY() {
        return noldtY;
    }

    public void setNoldtY(BigDecimal noldtY) {
        this.noldtY = noldtY;
    }

    public BigDecimal getNoldtM() {
        return noldtM;
    }

    public void setNoldtM(BigDecimal noldtM) {
        this.noldtM = noldtM;
    }

    public String getDupeIdnNoMk() {
        return dupeIdnNoMk;
    }

    public void setDupeIdnNoMk(String dupeIdnNoMk) {
        this.dupeIdnNoMk = dupeIdnNoMk;
    }
}
