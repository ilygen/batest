package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class MonthlyRpt29Case implements Serializable {
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
    // B01 主旨&說明
    private String dataContMK1; // 失能每月
    private String dataContMK2; // 失能每月
    private String dataContMK3; // 失能每月
    private String dataContMK4; // 失能每月
    private String dataContMS1; // 遺屬每月
    private String dataContMS2; // 遺屬每月
    private String dataContMS3; // 遺屬每月
    private String dataContMS4; // 遺屬每月
    private String dataContYK1; // 失能每年
    private String dataContYK2; // 失能每年
    private String dataContYK3; // 失能每年
    private String dataContYK4; // 失能每年
    private String dataContYS1; // 遺屬每年
    private String dataContYS2; // 遺屬每年
    private String dataContYS3; // 遺屬每年
    private String dataContYS4; // 遺屬每年

    // B01 說明二 細項
    private String dataCont1;
    private String dataCont2;
    private String dataCont2ForK;
    private String dataCont3;
    private String dataCont4;
    private String dataCont5;

    // 20150123新增 6月份 B03 主旨&說明
    private String dataContMonth06YK1; // 失能每年 6月份
    private String dataContMonth06YK2; // 失能每年6月份
    private String dataContMonth06YK3; // 失能每年6月份
    private String dataContMonth06YS1; // 遺屬每年6月份
    private String dataContMonth06YS2; // 遺屬每年6月份
    private String dataContMonth06YS3; // 遺屬每年6月份

    // Excel
    private String payType; // 給付別
    private String seqNo; // 受款人序
    private String payYm; // 給付年月
    private String evtName; // 事故者姓名
    private String benName; // 受益人姓名
    private String arcDate; // 歸檔日期
    private String arcPg; // 歸檔頁次
    private String procStat; // 處理狀態
    private String chkCode; // 符合註記
    private String prpNo; // 初核人員
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String studSdate; // 在學起月
    private String studEdate; // 在學迄月

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

    public String getDataContMK1() {

        dataContMK1 = "為重新審核  台端所請勞保失能年金給付加發眷屬補助需要，請於<A123>前重新檢具<A114>之" + "「<A124>學年度第<A125>學期」學費收據影本或在學證明（請詳閱說明二）送局憑辦" + "（如欲以傳真方式補具，請電洽本案承辦人），如該子女已未在學，亦請來函說明。逾期本局將依規定停止加發該眷屬之補助，請  查照。";

        return dataContMK1;
    }

    public void setDataContMK1(String dataContMK1) {
        this.dataContMK1 = dataContMK1;
    }

    public String getDataContMK2() {

        dataContMK2 = "一、依據勞工保險條例第25條規定，被保險人無正當理由，不接受保險人特約醫療院、" + "所之檢查或補具應繳之證件，或受益人不補具應繳之證件者，保險人不負發給保險給付之責任。" + "第54條之2第1項第3款規定，請領失能年金給付者，子女有符合下列條件之一時，加發眷屬補助：" + "（1）未成年。（2）無謀生能力。（3）25歲以下，在學，且每月工作收入未超過投保薪資分級表第1級。"
                        + "第3項規定略以，子女不符合第1項第3款所定之請領條件，其加給眷屬補助應停止發給。" + "另同條例施行細則第73條第3款規定，在學者，應檢附學費收據影本或在學證明，並應於每年9月底前，" + "重新檢具相關證明送保險人查核，經查核符合條件者，應繼續發給至次年8月底止。";

        return dataContMK2;
    }

    public void setDataContMK2(String dataContMK2) {
        this.dataContMK2 = dataContMK2;
    }

    public String getDataContMK3() {

        dataContMK3 = "二、請依下列第(1)點至第(4)點所述，擇一檢具：";

        return dataContMK3;
    }

    public void setDataContMK3(String dataContMK3) {
        this.dataContMK3 = dataContMK3;
    }

    public String getDataContMK4() {
        dataContMK4 = "三、補正時請註明本案本局之受理號碼：<A001>。";
        return dataContMK4;
    }

    public void setDataContMK4(String dataContMK4) {
        this.dataContMK4 = dataContMK4;
    }

    public String getDataContMS1() {

        dataContMS1 = "為重新審核  台端所請勞保遺屬年金給付，請於<A123>前重新檢具" + "「<A124>學年度第<A125>學期」學費收據影本或在學證明（請詳閱說明二）" + "送局憑辦。逾期將依規定，停止加計（發給）  台端所請勞保遺屬年金給付，請  查照。";

        return dataContMS1;
    }

    public void setDataContMS1(String dataContMS1) {
        this.dataContMS1 = dataContMS1;
    }

    public String getDataContMS2() {

        dataContMS2 = "一、依據勞工保險條例第25條規定，被保險人無正當理由，不接受保險人特約醫療院、" + "所之檢查或補具應繳之證件，或受益人不補具應繳之證件者，保險人不負發給保險給付之責任。" + "第63條第2項規定，子女、孫子女請領遺屬年金給付須符合下列情形之一：" + "（1）未成年。（2）無謀生能力。（3）25歲以下，在學，且每月工作收入未超過投保薪資分級表第1級。"
                        + "同條例第63條之4規定略以，子女、孫子女不符合第63條第2項所定請領條件，其年金給付應停止發給。" + "另同條例施行細則第85條第4款規定，在學者，應檢附學費收據影本或在學證明，並應於每年9月底前，" + "重新檢具相關證明送保險人查核，經查核符合條件者，應繼續發給至次年8月底止。";

        return dataContMS2;
    }

    public void setDataContMS2(String dataContMS2) {
        this.dataContMS2 = dataContMS2;
    }

    public String getDataContMS3() {

        dataContMS3 = "二、請依下列第(1)點至第(4)點所述，擇一檢具：";

        return dataContMS3;
    }

    public void setDataContMS3(String dataContMS3) {
        this.dataContMS3 = dataContMS3;
    }

    public String getDataContMS4() {

        dataContMS4 = "三、補正時請註明本案本局之受理號碼：<A001>。";

        return dataContMS4;
    }

    public void setDataContMS4(String dataContMS4) {
        this.dataContMS4 = dataContMS4;
    }

    public String getDataContYK1() {

        dataContYK1 = "為重新審核  台端所請勞保失能年金給付加發眷屬補助需要，請於<A111>前重新檢具<A114>之" + "「<A113>學年度第1學期」學費收據影本或在學證明（請詳閱說明二）送局憑辦" + "（如欲以傳真方式補具，請電洽本案承辦人），如該子女已未在學，亦請來函說明。逾期本局將依規定停止加發該眷屬之補助，請  查照。";

        return dataContYK1;
    }

    public void setDataContYK1(String dataContYK1) {
        this.dataContYK1 = dataContYK1;
    }

    public String getDataContYK2() {

        dataContYK2 = "一、依據勞工保險條例第25條規定，被保險人無正當理由，不接受保險人特約醫療院、" + "所之檢查或補具應繳之證件，或受益人不補具應繳之證件者，保險人不負發給保險給付之責任。" + "第54條之2第1項第3款規定，請領失能年金給付者，子女有符合下列條件之一時，加發眷屬補助：" + "（1）未成年。（2）無謀生能力。（3）25歲以下，在學，且每月工作收入未超過投保薪資分級表第1級。"
                        + "第3項規定略以，子女不符合第1項第3款所定之請領條件，其加給眷屬補助應停止發給。" + "另同條例施行細則第73條第3款規定，在學者，應檢附學費收據影本或在學證明，並應於每年9月底前，" + "重新檢具相關證明送保險人查核，經查核符合條件者，應繼續發給至次年8月底止。";

        return dataContYK2;
    }

    public void setDataContYK2(String dataContYK2) {
        this.dataContYK2 = dataContYK2;
    }

    public String getDataContYK3() {

        dataContYK3 = "二、請依下列第(1)點至第(4)點所述，擇一檢具：";

        return dataContYK3;
    }

    public void setDataContYK3(String dataContYK3) {
        this.dataContYK3 = dataContYK3;
    }

    public String getDataContYK4() {

        dataContYK4 = "三、補正時請註明本案本局之受理號碼：<A001>。";

        return dataContYK4;
    }

    public void setDataContYK4(String dataContYK4) {
        this.dataContYK4 = dataContYK4;
    }

    public String getDataContYS1() {

        dataContYS1 = "為重新審核  台端所請勞保遺屬年金給付，請於<A111>前重新檢具" + "「<A113>學年度第1學期」學費收據影本或在學證明（請詳閱說明二）" + "送局憑辦，逾期將依規定，停止加計（發給）  台端所請勞保遺屬年金給付，倘  台端未在學，亦請通知本局，請  查照。";

        return dataContYS1;
    }

    public void setDataContYS1(String dataContYS1) {
        this.dataContYS1 = dataContYS1;
    }

    public String getDataContYS2() {

        dataContYS2 = "一、依據勞工保險條例第25條規定，被保險人無正當理由，不接受保險人特約醫療院、所之檢查或補具應繳之證件，" + "或受益人不補具應繳之證件者，保險人不負發給保險給付之責任。第63條第2項規定，" + "子女、孫子女請領遺屬年金給付須符合下列情形之一：" + "（1）未成年。（2）無謀生能力。（3）25歲以下，在學，且每月工作收入未超過投保薪資分級表第1級。"
                        + "同條例第63條之4規定略以，子女、孫子女不符合第63條第2項所定請領條件，其年金給付應停止發給。" + "另同條例施行細則第85條第4款規定，在學者，應檢附學費收據影本或在學證明，並應於每年9月底前，" + "重新檢具相關證明送保險人查核，經查核符合條件者，應繼續發給至次年8月底止。";

        return dataContYS2;
    }

    public void setDataContYS2(String dataContYS2) {
        this.dataContYS2 = dataContYS2;
    }

    public String getDataContYS3() {

        dataContYS3 = "二、請依下列第(1)點至第(5)點所述，擇一檢具：";

        return dataContYS3;
    }

    public void setDataContYS3(String dataContYS3) {
        this.dataContYS3 = dataContYS3;
    }

    public String getDataContYS4() {

        dataContYS4 = "三、補正時請註明本案本局之受理號碼：<A001>。";

        return dataContYS4;
    }

    public void setDataContYS4(String dataContYS4) {
        this.dataContYS4 = dataContYS4;
    }

    public String getDataContMonth06YK1() {

        dataContMonth06YK1 = "查  台端之子女<A114>前於學校修業中，現在是否已畢業？如已畢業，請檢附畢業證書影本送局；" + "尚未畢業(含暑修或延畢)或擬繼續升學者，亦請檢附相關資料(如錄取通知、學分繳費單)通知本局。" + "前述應補正之資料請於<A113>年7月31日前送局憑辦（如欲以傳真方式補具，請電洽本案承辦人），" + "如該子女已未在學，亦請來函說明。逾期本局將依規定停止加發該眷屬之補助，請  查照。";

        return dataContMonth06YK1;
    }

    public void setDataContMonth06YK1(String dataContMonth06YK1) {
        this.dataContMonth06YK1 = dataContMonth06YK1;
    }

    public String getDataContMonth06YK2() {

        dataContMonth06YK2 = "一、依據勞工保險條例第25條規定，被保險人無正當理由，不接受保險人特約醫療院、所之檢查或補具應繳之證件" + "，或受益人不補具應繳之證件者，保險人不負發給保險給付之責任。第54條之2第1項第3款規定，請領失能年金給付者，" + "子女有符合下列條件之一時，加發眷屬補助：（1）未成年。（2）無謀生能力。（3）25歲以下，在學，且每月工作收入未超過投保薪資分級表第1級。"
                        + "第3項規定略以，子女不符合第1項第3款所定之請領條件，其加給眷屬補助應停止發給。另同條例施行細則第73條第3款規";

        return dataContMonth06YK2;
    }

    public void setDataContMonth06YK2(String dataContMonth06YK2) {
        this.dataContMonth06YK2 = dataContMonth06YK2;
    }

    public String getDataContMonth06YK3() {

        dataContMonth06YK3 = "二、補正時請註明本案本局之受理號碼：<A001>。";

        return dataContMonth06YK3;
    }

    public void setDataContMonth06YK3(String dataContMonth06YK3) {
        this.dataContMonth06YK3 = dataContMonth06YK3;
    }

    public String getDataContMonth06YS1() {

        dataContMonth06YS1 = "查  台端前於學校修業中，現在是否已畢業？如已畢業，請檢附畢業證書影本送局；" + "尚未畢業(含暑修或延畢)或擬繼續升學者，亦請檢附相關資料(如錄取通知、學分繳費單)通知本局。" + "前述應補正之資料請於<A113>年7月31日前送局憑辦，逾期將依規定，停止加計（發給）  台端所請勞保遺屬年金給付，請  查照。";

        return dataContMonth06YS1;
    }

    public void setDataContMonth06YS1(String dataContMonth06YS1) {
        this.dataContMonth06YS1 = dataContMonth06YS1;
    }

    public String getDataContMonth06YS2() {

        dataContMonth06YS2 = "一、依據勞工保險條例第25條規定，被保險人無正當理由，不接受保險人特約醫療院、" + "所之檢查或補具應繳之證件，或受益人不補具應繳之證件者，保險人不負發給保險給付之責任。" + "第63條第2項規定，子女、孫子女請領遺屬年金給付須符合下列情形之一：（1）未成年。（2）無謀生能力。（3）25歲以下，"
                        + "在學，且每月工作收入未超過投保薪資分級表第1級。同條例第63條之4規定略以，子女、孫子女不符合第63條第2項所定請領條件，其年金給付應停止發給。" + "另同條例施行細則第85條第4款規定，在學者，應檢附學費收據影本或在學證明，並應於每年9月底前，重新檢具相關證明送保險人查核，" + "經查核符合條件者，應繼續發給至次年8月底止。";

        return dataContMonth06YS2;
    }

    public void setDataContMonth06YS2(String dataContMonth06YS2) {
        this.dataContMonth06YS2 = dataContMonth06YS2;
    }

    public String getDataContMonth06YS3() {

        dataContMonth06YS3 = "二、補正時請註明本案本局之受理號碼：<A001>。";

        return dataContMonth06YS3;
    }

    public void setDataContMonth06YS3(String dataContMonth06YS3) {
        this.dataContMonth06YS3 = dataContMonth06YS3;
    }

    public String getDataCont1() {

        dataCont1 = "(1)蓋有註冊章之學生證正反面影本可視為在學證明，倘學生證上未蓋註冊章，請向學校申請      在學證明。";

        return dataCont1;
    }

    public void setDataCont1(String dataCont1) {
        this.dataCont1 = dataCont1;
    }

    public String getDataCont2() {

        dataCont2 = "(2)學費收據應顯示台端全名，如有缺漏請自行簽名。";

        return dataCont2;
    }

    public void setDataCont2(String dataCont2) {
        this.dataCont2 = dataCont2;
    }

    public String getDataCont2ForK() {

        dataCont2ForK = "(2)學費收據應顯示子女全名，如有缺漏請自行簽名。";

        return dataCont2ForK;
    }

    public void setDataCont2ForK(String dataCont2ForK) {
        this.dataCont2ForK = dataCont2ForK;
    }

    public String getDataCont3() {

        dataCont3 = "(3)以信用卡繳交學費者，請同時檢附學費繳費通知單及信用卡帳單影本。";

        return dataCont3;
    }

    public void setDataCont3(String dataCont3) {
        this.dataCont3 = dataCont3;
    }

    public String getDataCont4() {

        dataCont4 = "(4)於國外就學者，學校應符合教育部採認規定，並同時檢附經我國駐外單位驗證之在學證明      及工作收入證明（包含原始憑證及中譯本）。";

        return dataCont4;
    }

    public void setDataCont4(String dataCont4) {
        this.dataCont4 = dataCont4;
    }

    public String getDataCont5() {

        dataCont5 = "(5)如台端已休學或畢業，請檢附休學證明或畢業證書影本送局憑辦。";

        return dataCont5;
    }

    public void setDataCont5(String dataCont5) {
        this.dataCont5 = dataCont5;
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

    public String getArcDate() {
        return arcDate;
    }

    public void setArcDate(String arcDate) {
        this.arcDate = arcDate;
    }

    public String getArcPg() {
        return arcPg;
    }

    public void setArcPg(String arcPg) {
        this.arcPg = arcPg;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getChkCode() {
        return chkCode;
    }

    public void setChkCode(String chkCode) {
        this.chkCode = chkCode;
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

    public String getStudSdate() {
        return studSdate;
    }

    public void setStudSdate(String studSdate) {
        this.studSdate = studSdate;
    }

    public String getStudEdate() {
        return studEdate;
    }

    public void setStudEdate(String studEdate) {
        this.studEdate = studEdate;
    }

}
