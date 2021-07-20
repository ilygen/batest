package tw.gov.bli.common.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.owasp.encoder.Encode;

public class HttpUtil {
    
    public static String getClientIP(HttpServletRequest request) {
        String ip = Encode.forHtml(request.getHeader("X-Forwarded-For"));

        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = Encode.forHtml(request.getHeader("Proxy-Client-IP"));
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = Encode.forHtml(request.getHeader("WL-Proxy-Client-IP"));
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = Encode.forHtml(request.getHeader("HTTP_CLIENT_IP"));
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = Encode.forHtml(request.getHeader("HTTP_X_FORWARDED_FOR"));
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
            ip = Encode.forHtml(request.getRemoteAddr());
        }
        
        return StringUtils.trimToEmpty(ip);
    }

}
