package tw.gov.bli.common.aop;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;
import tw.gov.bli.common.domain.UserInfo;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.services.LoggingService;
import tw.gov.bli.common.util.ExceptionUtil;
import tw.gov.bli.common.util.FrameworkLogUtil;
import tw.gov.bli.common.util.StrUtil;

/**
 * 用來紀錄 <code>MMQUERYLOG</code> 的 <code>MethodBeforeAdvice</code><br>
 * 於紀錄時會先新增一筆紀錄到 <code>MMACCESSLG</code> (Master) 中, 再紀錄 <code>MMQUERYLOG</code><br>
 * 注意: 未使用 Framework 提供之 Logging 機制者, 請勿使用此 Advice...<br>
 * <br>
 * 欲使用本 Advice 請於 Spring Config 定義如下 (Pointcut 建議定於 DAO 實做):<br>
 * <code><pre>
 *  &lt;aop:config&gt;
 *      &lt;aop:pointcut id=&quot;queryLogPointcut&quot; expression=&quot;execution(* tw.gov.bli.common.dao..*.*(..))&quot; /&gt;
 *      &lt;aop:advisor advice-ref=&quot;queryLogBeforeAdvice&quot; pointcut-ref=&quot;queryLogPointcut&quot; /&gt;
 *  &lt;/aop:config&gt;
 * 
 *  &lt;bean id=&quot;queryLogBeforeAdvice&quot; class=&quot;tw.gov.bli.common.aop.QueryLogBeforeAdvice&quot; /&gt;
 * </pre><code>
 * 詳細說明請見 Spring 官方文件
 * 
 * @author Goston
 */
public class QueryLogBeforeAdvice {
    private static Log log = LogFactory.getLog(QueryLogBeforeAdvice.class);

    private LoggingService loggingService;

    public void doQueryLog(JoinPoint pointcut) {
        HttpServletRequest request = HttpHelper.getHttpRequest();
        
        log.debug("進入了 doQueryLog ... " + pointcut.getSignature().getName());
        
        // 判斷是否需紀錄 Log
        if (EnvFacadeHelper.isNeedLogging(request)) {
            try {
                UserInfo userData = UserSessionHelper.getUserData(request);

                // 使用者沒登入, 或為批次作業
                if (userData == null)
                    return;

                String table = "";
                String[] fieldList = {""};

                Method method = getTargetMethod(pointcut);

                // 先看 Method Level 有沒有定義 Table Name, 若無再檢核 Class Level 是否定義 Table Name
                if (method.isAnnotationPresent(DaoTable.class))
                    table = StringUtils.defaultString(method.getAnnotation(DaoTable.class).value()).trim();
                else if (pointcut.getTarget().getClass().isAnnotationPresent(DaoTable.class))
                    table = StringUtils.defaultString(pointcut.getTarget().getClass().getAnnotation(DaoTable.class).value()).trim();

                // 有定義 Table Name 再繼續取得 Field List
                if (StringUtils.isNotBlank(table)) {
                    if (method.isAnnotationPresent(DaoFieldList.class))
                        fieldList = StrUtil.splitTrim(StringUtils.defaultString(method.getAnnotation(DaoFieldList.class).value()), ",");

                    // 參數長度相等
                    if (fieldList != null && fieldList.length == pointcut.getArgs().length) {
                        String xmlData = FrameworkLogUtil.generateQueryLogXML(table, method.getName(), fieldList, pointcut.getArgs());

                        // 紀錄 MMACCESSLG 及 MMQUERYLOG
                        if (xmlData != null) {
                            loggingService.loggingQueryLog(userData, table, xmlData);
                        }
                    }
                }
            }
            catch (Exception e) {
                log.debug("Query Log 寫入錯誤, 原因: " + ExceptionUtil.getStackTrace(e));
            }
        }
    }

    public Method getTargetMethod(JoinPoint pointcut) throws Exception {
        MethodSignature methodSig = (MethodSignature) pointcut.getSignature();

        return pointcut.getTarget().getClass().getMethod(pointcut.getSignature().getName(), methodSig.getMethod().getParameterTypes());
    }

    public void setLoggingService(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

}
