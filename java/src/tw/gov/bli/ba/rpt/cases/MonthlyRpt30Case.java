package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class MonthlyRpt30Case implements Serializable {
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
    private byte[] managerImg;// 總經理署名圖

    // 主旨 & 說明
    private String dataContK01C0101;
    private String dataContK01C0102;
    private String dataContK01C0103;
    private String dataContK01C0104;

    private String dataContK01C0201;
    private String dataContK01C0202;
    private String dataContK01C0203;
    private String dataContK01C0204;

    private String dataContK01C0301;
    private String dataContK01C0302;
    private String dataContK01C0303;
    private String dataContK01C0304;

    private String dataContK01C0401;
    private String dataContK01C0402;
    private String dataContK01C0403;
    private String dataContK01C0404;

    private String dataContK02C0501;
    private String dataContK02C0502;
    private String dataContK02C0503;

    private String dataContK02C0601;
    private String dataContK02C0602;
    private String dataContK02C0603;

    // 說明二細項
    private String dataContK0101;
    private String dataContK0102;
    private String dataContK0103;

    // Excel
    private String payType; // 給付別
    private String seqNo; // 受款人序
    private String payYm; // 給付年月
    private String evtName; // 事故者姓名
    private String benName; // 受益人姓名
    private String prpNo; // 初核人員
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String rehcYm; // 重新查核失能程度年月
    private String criInJdp; // 失能項目
    private String comRechkYm;
    private String grdName;// 法定代理人姓名
    private String notifyForm;// 核定通知書格式
    private String evtIdnNo;// 事故者身分證號
    private String benSex;// 受益人性別
    private String evtBrDate;// 事故者出生日期

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
    public String getDataContK01C0101() {

        dataContK01C0101 = "台端請領勞工保險失能年金給付，本局為依規定至少每5年審核台端失能程度，惠請依說明二於<A159>前儘速補正手續，請  查照。";

        return dataContK01C0101;
    }

    public void setDataContK01C0101(String dataContK01C0101) {
        this.dataContK01C0101 = dataContK01C0101;
    }

    public String getDataContK01C0102() {

        dataContK01C0102 = "一、依照勞工保險條例第56條第2項規定略以，被保險人領取失能年金給付後，保險人應至少每5年審核其失能程度。";

        return dataContK01C0102;
    }

    public void setDataContK01C0102(String dataContK01C0102) {
        this.dataContK01C0102 = dataContK01C0102;
    }

    public String getDataContK01C0103() {

        dataContK01C0103 = "二、台端請領勞工保險失能年金給付即將屆滿5年，本局為依前揭規定審核台端失能程度，請就下列事項補正手續：";

        return dataContK01C0103;
    }

    public void setDataContK01C0103(String dataContK01C0103) {
        this.dataContK01C0103 = dataContK01C0103;
    }

    public String getDataContK01C0104() {

        dataContK01C0104 = "三、補件時請註明本案本局之受理號碼：<A001>。";

        return dataContK01C0104;
    }

    public void setDataContK01C0104(String dataContK01C0104) {
        this.dataContK01C0104 = dataContK01C0104;
    }

    public String getDataContK01C0201() {

        dataContK01C0201 = "<A003><K001>請領勞工保險失能年金給付，本局為依規定至少每5年審核其失能程度，惠請依說明二於<A159>前儘速補正手續，請  查照。";

        return dataContK01C0201;
    }

    public void setDataContK01C0201(String dataContK01C0201) {
        this.dataContK01C0201 = dataContK01C0201;
    }

    public String getDataContK01C0202() {

        dataContK01C0202 = "一、依照勞工保險條例第56條第2項規定略以，被保險人領取失能年金給付後，保險人應至少每5年審核其失能程度。";

        return dataContK01C0202;
    }

    public void setDataContK01C0202(String dataContK01C0202) {
        this.dataContK01C0202 = dataContK01C0202;
    }

    public String getDataContK01C0203() {

        dataContK01C0203 = "二、<A003><K001>請領勞工保險失能年金給付即將屆滿5年，本局為依前揭規定審核其失能程度，請就下列事項補正手續：";

        return dataContK01C0203;
    }

    public void setDataContK01C0203(String dataContK01C0203) {
        this.dataContK01C0203 = dataContK01C0203;
    }

    public String getDataContK01C0204() {

        dataContK01C0204 = "三、補件時請註明本案本局之受理號碼：<A001>。";

        return dataContK01C0204;
    }

    public void setDataContK01C0204(String dataContK01C0204) {
        this.dataContK01C0204 = dataContK01C0204;
    }

    public String getDataContK01C0301() {

        dataContK01C0301 = "台端請領勞工保險失能年金給付，本局為依規定至少每5年審核台端失能程度，惠請依說明二於<A159>前儘速補正手續，請  查照。";

        return dataContK01C0301;
    }

    public void setDataContK01C0301(String dataContK01C0301) {
        this.dataContK01C0301 = dataContK01C0301;
    }

    public String getDataContK01C0302() {

        dataContK01C0302 = "一、依照勞工保險條例第56條第2項規定略以，被保險人領取失能年金給付後，保險人應至少每5年審核其失能程度。";

        return dataContK01C0302;
    }

    public void setDataContK01C0302(String dataContK01C0302) {
        this.dataContK01C0302 = dataContK01C0302;
    }

    public String getDataContK01C0303() {

        dataContK01C0303 = "二、台端請領勞工保險失能年金給付距前次查核失能程度即將屆滿5年，本局為依前揭規定審核台端失能程度，請就下列事項補正手續：";

        return dataContK01C0303;
    }

    public void setDataContK01C0303(String dataContK01C0303) {
        this.dataContK01C0303 = dataContK01C0303;
    }

    public String getDataContK01C0304() {

        dataContK01C0304 = "三、補件時請註明本案本局之受理號碼：<A001>。";

        return dataContK01C0304;
    }

    public void setDataContK01C0304(String dataContK01C0304) {
        this.dataContK01C0304 = dataContK01C0304;
    }

    public String getDataContK01C0401() {

        dataContK01C0401 = "<A003><K001>請領勞工保險失能年金給付，本局為依規定至少每5年審核其失能程度，惠請依說明二於<A159>前儘速補正手續，請  查照。";

        return dataContK01C0401;
    }

    public void setDataContK01C0401(String dataContK01C0401) {
        this.dataContK01C0401 = dataContK01C0401;
    }

    public String getDataContK01C0402() {

        dataContK01C0402 = "一、依照勞工保險條例第56條第2項規定略以，被保險人領取失能年金給付後，保險人應至少每5年審核其失能程度。";

        return dataContK01C0402;
    }

    public void setDataContK01C0402(String dataContK01C0402) {
        this.dataContK01C0402 = dataContK01C0402;
    }

    public String getDataContK01C0403() {

        dataContK01C0403 = "二、<A003><K001>請領勞工保險失能年金給付距前次查核失能程度即將屆滿5年，本局為依前揭規定審核其失能程度，請就下列事項補正手續：";

        return dataContK01C0403;
    }

    public void setDataContK01C0403(String dataContK01C0403) {
        this.dataContK01C0403 = dataContK01C0403;
    }

    public String getDataContK01C0404() {

        dataContK01C0404 = "三、補件時請註明本案本局之受理號碼：<A001>。";

        return dataContK01C0404;
    }

    public void setDataContK01C0404(String dataContK01C0404) {
        this.dataContK01C0404 = dataContK01C0404;
    }

    public String getDataContK02C0501() {

        dataContK02C0501 = "台端（身分證統一編號：<A004>，出生日期：<A005>）領取勞工保險失能年金給付，經本局依規定每5年審核失能程度結果，仍符合給付規定，將繼續按月發給，請 查照。";

        return dataContK02C0501;
    }

    public void setDataContK02C0501(String dataContK02C0501) {
        this.dataContK02C0501 = dataContK02C0501;
    }

    public String getDataContK02C0502() {

        dataContK02C0502 = "一、據台端所送失能給付申請書件，失能年金給付失能狀況說明書及本局審核結果辦理。";

        return dataContK02C0502;
    }

    public void setDataContK02C0502(String dataContK02C0502) {
        this.dataContK02C0502 = dataContK02C0502;
    }

    public String getDataContK02C0503() {

        dataContK02C0503 = "二、如對本函有疑義，請電洽本局承辦單位聯絡方式。";

        return dataContK02C0503;
    }

    public void setDataContK02C0503(String dataContK02C0503) {
        this.dataContK02C0503 = dataContK02C0503;
    }

    public String getDataContK02C0601() {

        dataContK02C0601 = "<A003><K001>（身分證統一編號：<A004>，出生日期：<A005>）領取勞工保險失能年金給付，經本局依規定每5年審核失能程度結果，仍符合給付規定，將繼續按月發給，請 查照。";

        return dataContK02C0601;
    }

    public void setDataContK02C0601(String dataContK02C0601) {
        this.dataContK02C0601 = dataContK02C0601;
    }

    public String getDataContK02C0602() {

        dataContK02C0602 = "一、據<A003><K001>失能給付申請書件，失能年金給付失能狀況說明書及本局審核結果辦理。";

        return dataContK02C0602;
    }

    public void setDataContK02C0602(String dataContK02C0602) {
        this.dataContK02C0602 = dataContK02C0602;
    }

    public String getDataContK02C0603() {

        dataContK02C0603 = "二、如對本函有疑義，請電洽本局承辦單位聯絡方式。";

        return dataContK02C0603;
    }

    public void setDataContK02C0603(String dataContK02C0603) {
        this.dataContK02C0603 = dataContK02C0603;
    }

    // 說明二細項
    public String getDataContK0101() {

        dataContK0101 = "(一)請就目前失能情形填具「勞工保險失能年金失能狀況說明書」乙份(用紙附後)送局憑辦。";

        return dataContK0101;
    }

    public void setDataContK0101(String dataContK0101) {
        this.dataContK0101 = dataContK0101;
    }

    public String getDataContK0102() {

        dataContK0102 = "(二)如台端本人無法親自填寫「勞工保險失能年金失能狀況說明書」，得由家屬或日常照顧人員代填，惟需一併載明其與台端之關係及其身分證統一編號、連絡電話、地址等。";

        return dataContK0102;
    }

    public void setDataContK0102(String dataContK0102) {
        this.dataContK0102 = dataContK0102;
    }

    public String getDataContK0103() {

        dataContK0103 = "(二)如<A003><K001>本人無法親自填寫「勞工保險失能年金失能狀況說明書」，得由家屬或日常照顧人員代填，惟需一併載明其與<A003><K001>之關係及其身分證統一編號、連絡電話、地址等。";

        return dataContK0103;
    }

    public void setDataContK0103(String dataContK0103) {
        this.dataContK0103 = dataContK0103;
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

    public String getRehcYm() {
        return rehcYm;
    }

    public void setRehcYm(String rehcYm) {
        this.rehcYm = rehcYm;
    }

    public String getCriInJdp() {
        return criInJdp;
    }

    public void setCriInJdp(String criInJdp) {
        this.criInJdp = criInJdp;
    }

    public String getComRechkYm() {
        return comRechkYm;
    }

    public void setComRechkYm(String comRechkYm) {
        this.comRechkYm = comRechkYm;
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

	public byte[] getManagerImg() {
		return managerImg;
	}

	public void setManagerImg(byte[] managerImg) {
		this.managerImg = managerImg;
	}
}
