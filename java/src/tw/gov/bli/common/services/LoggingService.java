package tw.gov.bli.common.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.common.ConstantKey;
import tw.gov.bli.common.dao.MmaccesslgDao;
import tw.gov.bli.common.dao.MmaplogDao;
import tw.gov.bli.common.dao.MmquerylogDao;
import tw.gov.bli.common.dao.PortalLogDao;
import tw.gov.bli.common.domain.Mmaccesslg;
import tw.gov.bli.common.domain.Mmaplog;
import tw.gov.bli.common.domain.Mmquerylog;
import tw.gov.bli.common.domain.PortalLog;
import tw.gov.bli.common.domain.UserInfo;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.util.DateUtil;

/**
 * 紀錄 PORTAL_LOG / MMACCESSLG / MMAPLOG / MMQUERYLOG 之 Service<br>
 * 
 * @author Goston
 */
public class LoggingService {
    private static Log log = LogFactory.getLog(LoggingService.class);

    private PortalLogDao portalLogDao;
    private MmaccesslgDao mmaccesslgDao;
    private MmquerylogDao mmquerylogDao;
    private MmaplogDao mmaplogDao;

    /**
     * 記錄 Portal Log<br>
     * 注意: 本方法為 Framework Logging 機制用, 一般系統請勿呼叫本方法
     * 
     * @param userData 使用者物件
     * @param request HttpServletRequest
     * @return 成功回傳 <code>PORTAL_LOG.SYS_ID</code>, 失敗回傳 null
     */
    public BigDecimal loggingPortalLog(UserInfo userData, HttpServletRequest request) {
        String path = request.getServletPath();

        Timestamp date = DateUtil.getNowDateTimeAsTimestamp();
        String now = DateUtil.parseTimestampToWestDateTime(date, true);

        PortalLog logData = new PortalLog();
        logData.setLogDateTime(now); // 紀錄時間
        logData.setUserId(userData.getUserId()); // 用戶代號
        logData.setUserIP(userData.getLoginIP()); // 用戶 IP 位址
        logData.setUserAction("Click Link"); // 用戶執行動作
        logData.setApCode(EnvFacadeHelper.getSystemId()); // 應用系統代號
        logData.setApName(EnvFacadeHelper.getSystemName()); // 應用系統名稱
        logData.setApFunctionCode(EnvFacadeHelper.getItemIdByServletPath(path)); // 應用系統功能代號
        logData.setApFunctionName(EnvFacadeHelper.getItemNameByServletPath(path)); // 應用系統功能名稱
        logData.setApUrl(request.getServletPath()); // 應用系統網址
        logData.setLogDescript(""); // 說明
        logData.setDateTime(date); // 系統時間
        logData.setToken(userData.getToken()); // 檢查資訊

        return portalLogDao.insertData(logData);
    }

    /**
     * 記錄 Portal Log<br>
     * 未使用 Framework Logging 機制請使用此方法紀錄 Portal Log
     * 
     * @param logData <code>tw.gov.bli.common.domain.PortalLog</code> 物件
     * @return 成功回傳 <code>PORTAL_LOG.SYS_ID</code>, 失敗回傳 <code>null</code>
     */
    public BigDecimal loggingPortalLog(PortalLog logData) {
        return portalLogDao.insertData(logData);
    }

    /**
     * 記錄 Access Log<br>
     * 未使用 Framework Logging 機制請使用此方法紀錄 Access Log
     * 
     * @param logData <code>tw.gov.bli.common.domain.Mmaccesslg</code> 物件
     * @return 成功回傳 <code>MMACCESSLG.SNO</code>, 失敗回傳 <code>null</code>
     */
    public BigDecimal loggingAccessLog(Mmaccesslg logData) {
        return mmaccesslgDao.insertData(logData);
    }

    /**
     * 記錄 Access Log 及 Query Log<br>
     * 注意: 本方法為 Framework Logging 機制用, 一般系統請勿呼叫本方法
     * 
     * @param userData 使用者物件
     * @param tableName 查詢的 TABLE 名稱
     * @param xmlData 查詢條件 XML 字串
     */
    public void loggingQueryLog(UserInfo userData, String tableName, String xmlData) {
        String acsTime = DateUtil.getNowChineseDateTimeMs(); // 操作日期時間 (民國年YYYMMDDHHMMSSSS)

        // 處理 MMACCESSLG
        Mmaccesslg accessLog = new Mmaccesslg();
        accessLog.setAcsTime(acsTime); // 操作日期時間 (民國年YYYMMDDHHMMSSSS)
        accessLog.setApName(userData.getApFunctionCode()); // 程式名稱
        accessLog.setPsNo(userData.getUserId()); // 員工編號
        accessLog.setDeptId(userData.getDeptId()); // 所屬工作部門代碼
        accessLog.setTrnsId(""); // 異動代碼
        accessLog.setTermId(userData.getLoginIP()); // 連線 IP / 終端機代碼
        accessLog.setStype(EnvFacadeHelper.getSystemId()); // 系統別
        accessLog.setUno(""); // 保險證號
        accessLog.setYm(""); // 資料年月
        accessLog.setIdNo(""); // 申請人身分證號
        accessLog.setProposer(""); // 申請人中文姓名
        accessLog.setProc(""); // 作業項目
        accessLog.setApNo(""); // 受理編號
        accessLog.setEvIdNo(""); // 事故者身分證號
        accessLog.setEvBrth(""); // 事故者出生日
        accessLog.setScrNo(userData.getProgId()); // 畫面代號
        accessLog.setQmk(""); // 查詢備註
        accessLog.setAccType(ConstantKey.ACCESS_TYPE_QUERY); // 操作類別 (I/U/D/P/Q)
        // accessLog.setSno(); // 編號 - 存檔時取得
        accessLog.setSysId(userData.getSysId()); // 編號 (PORTAL_LOG.SYS_ID)

        BigDecimal sno = mmaccesslgDao.insertData(accessLog);

        // 處理 MMQUERYLOG
        Mmquerylog queryLog = new Mmquerylog();
        queryLog.setTableName(tableName); // TABLE 名稱
        queryLog.setQyTime(acsTime); // 查詢時間
        queryLog.setPgmName(userData.getApFunctionName()); // 程式名稱
        queryLog.setPgmCode(userData.getApFunctionCode()); // 程式代碼
        queryLog.setDeptId(userData.getDeptId()); // 員工部門代號
        queryLog.setQueryMan(userData.getUserId()); // 員工編號
        queryLog.setTermEd(userData.getLoginIP()); // 終端機位址
        queryLog.setQyCode(ConstantKey.ACCESS_TYPE_QUERY); // 查詢代號
        queryLog.setQyCondition(xmlData); // 查詢條件
        queryLog.setIdNo(""); // 證號
        queryLog.setMemo(""); // 備註
        queryLog.setSno(sno); // 編號 (MMACCESSLG.SNO)

        mmquerylogDao.insertData(queryLog);
    }

    /**
     * 記錄 Query Log<br>
     * 未使用 Framework Logging 機制請使用此方法紀錄 Query Log
     * 
     * @param logData <code>tw.gov.bli.common.domain.Mmquerylog</code> 物件
     */
    public void loggingQueryLog(Mmquerylog logData) {
        mmquerylogDao.insertData(logData);
    }

    /**
     * 記錄 AP Log<br>
     * 注意: 本方法為 Framework Logging 機制用, 一般系統請勿呼叫本方法
     * 
     * @param userData 使用者物件
     * @param accessType 操作類別 (I/U/D/P/Q)
     * @param tableName 異動的 TABLE 名稱
     * @param pkField 異動 TABLE 主鍵
     * @param field 異動欄位
     * @param befImg 改前內容
     * @param aftImg 改後內容
     */
    public void loggingApLog(UserInfo userData, String accessType, String tableName, String pkField, String field, String befImg, String aftImg) {
        String acsTime = DateUtil.getNowChineseDateTimeMs(); // 操作日期時間 (民國年YYYMMDDHHMMSSSS)

        // 處理 MMACCESSLG
        Mmaccesslg accessLog = new Mmaccesslg();
        accessLog.setAcsTime(acsTime); // 操作日期時間 (民國年YYYMMDDHHMMSSSS)
        accessLog.setApName(userData.getApFunctionCode()); // 程式名稱
        accessLog.setPsNo(userData.getUserId()); // 員工編號
        accessLog.setDeptId(userData.getDeptId()); // 所屬工作部門代碼
        accessLog.setTrnsId(""); // 異動代碼
        accessLog.setTermId(userData.getLoginIP()); // 連線 IP / 終端機代碼
        accessLog.setStype(EnvFacadeHelper.getSystemId()); // 系統別
        accessLog.setUno(""); // 保險證號
        accessLog.setYm(""); // 資料年月
        accessLog.setIdNo(""); // 申請人身分證號
        accessLog.setProposer(""); // 申請人中文姓名
        accessLog.setProc(""); // 作業項目
        accessLog.setApNo(""); // 受理編號
        accessLog.setEvIdNo(""); // 事故者身分證號
        accessLog.setEvBrth(""); // 事故者出生日
        accessLog.setScrNo(userData.getProgId()); // 畫面代號
        accessLog.setQmk(""); // 查詢備註
        accessLog.setAccType(accessType); // 操作類別 (I/U/D/P/Q)
        // accessLog.setSno(); // 編號 - 存檔時取得
        accessLog.setSysId(userData.getSysId()); // 編號 (PORTAL_LOG.SYS_ID)

        BigDecimal sno = mmaccesslgDao.insertData(accessLog);

        // 處理 MMAPLOG
        Mmaplog apLog = new Mmaplog();
        apLog.setTableName(tableName); // 異動 TABLE 名稱
        apLog.setPkField(pkField); // 異動 TABLE 主鍵
        apLog.setChgTime(acsTime); // 異動時間
        apLog.setPgmName(userData.getApFunctionName()); // 程式名稱
        apLog.setPgmCode(userData.getApFunctionCode()); // 程式代碼
        apLog.setDeptId(userData.getDeptId()); // 異動員工部門代號
        apLog.setModifyMan(userData.getUserId()); // 異動員工編號
        apLog.setTermEd(userData.getLoginIP()); // 終端機位址
        apLog.setChgCode(accessType); // 異動代號
        apLog.setField(field); // 異動欄位
        apLog.setBefImg(befImg); // 改前內容
        apLog.setAftImg(aftImg); // 改後內容
        apLog.setIdNo(""); // 證號
        apLog.setMemo(""); // 備註
        apLog.setSno(sno); // 編號

        mmaplogDao.insertData(apLog);
    }

    /**
     * 記錄 AP Log<br>
     * 注意: 本方法為 Framework Logging 機制用, 一般系統請勿呼叫本方法
     * 
     * @param userData 使用者物件
     * @param accessType 操作類別 (I/U/D/P/Q)
     * @param tableName 異動的 TABLE 名稱
     * @param apLogList 內含 <code>tw.gov.bli.common.domain.Mmaplog</code> 物件的 List
     */
    public void loggingApLog(UserInfo userData, String accessType, String tableName, List<Mmaplog> apLogList) {
        String acsTime = DateUtil.getNowChineseDateTimeMs(); // 操作日期時間 (民國年YYYMMDDHHMMSSSS)

        // 處理 MMACCESSLG
        Mmaccesslg accessLog = new Mmaccesslg();
        accessLog.setAcsTime(acsTime); // 操作日期時間 (民國年YYYMMDDHHMMSSSS)
        accessLog.setApName(userData.getApFunctionCode()); // 程式名稱
        accessLog.setPsNo(userData.getUserId()); // 員工編號
        accessLog.setDeptId(userData.getDeptId()); // 所屬工作部門代碼
        accessLog.setTrnsId(""); // 異動代碼
        accessLog.setTermId(userData.getLoginIP()); // 連線 IP / 終端機代碼
        accessLog.setStype(EnvFacadeHelper.getSystemId()); // 系統別
        accessLog.setUno(""); // 保險證號
        accessLog.setYm(""); // 資料年月
        accessLog.setIdNo(""); // 申請人身分證號
        accessLog.setProposer(""); // 申請人中文姓名
        accessLog.setProc(""); // 作業項目
        accessLog.setApNo(""); // 受理編號
        accessLog.setEvIdNo(""); // 事故者身分證號
        accessLog.setEvBrth(""); // 事故者出生日
        accessLog.setScrNo(userData.getProgId()); // 畫面代號
        accessLog.setQmk(""); // 查詢備註
        accessLog.setAccType(accessType); // 操作類別 (I/U/D/P/Q)
        // accessLog.setSno(); // 編號 - 存檔時取得
        accessLog.setSysId(userData.getSysId()); // 編號 (PORTAL_LOG.SYS_ID)

        BigDecimal sno = mmaccesslgDao.insertData(accessLog);

        // 處理 MMAPLOG
        for (Mmaplog apLog : apLogList) {
            apLog.setChgTime(acsTime); // 異動時間
            apLog.setSno(sno); // 編號
        }

        mmaplogDao.insertData(apLogList);
    }

    /**
     * 記錄 AP Log<br>
     * 未使用 Framework Logging 機制請使用此方法紀錄 AP Log
     * 
     * @param logData <code>tw.gov.bli.common.domain.Mmaplog</code> 物件
     */
    public void loggingApLog(Mmaplog logData) {
        mmaplogDao.insertData(logData);
    }

    /**
     * 記錄 AP Log<br>
     * 未使用 Framework Logging 機制請使用此方法紀錄 AP Log
     * 
     * @param logList 內含 <code>tw.gov.bli.common.domain.Mmaplog</code> 物件的 List
     */
    public void loggingApLog(List<Mmaplog> logList) {
        mmaplogDao.insertData(logList);
    }

    public void setPortalLogDao(PortalLogDao portalLogDao) {
        this.portalLogDao = portalLogDao;
    }

    public void setMmaccesslgDao(MmaccesslgDao mmaccesslgDao) {
        this.mmaccesslgDao = mmaccesslgDao;
    }

    public void setMmquerylogDao(MmquerylogDao mmquerylogDao) {
        this.mmquerylogDao = mmquerylogDao;
    }

    public void setMmaplogDao(MmaplogDao mmaplogDao) {
        this.mmaplogDao = mmaplogDao;
    }
}
