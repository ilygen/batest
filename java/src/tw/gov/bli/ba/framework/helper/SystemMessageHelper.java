package tw.gov.bli.ba.framework.helper;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class SystemMessageHelper {
    /**
     * 訊息: 登入失敗
     * 
     * @return
     */
    public static ActionMessages getLoginFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.framework.login.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 閒置逾時, 已自動重新登入
     * 
     * @return
     */
    public static ActionMessages getAutoReLoginMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.framework.autoReLogin"));
        return msgs;
    }

    /**
     * 訊息: 此功能目前未開放
     * 
     * @return
     */
    public static ActionMessages getFunctionUnavailableMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.function.unavailable"));
        return msgs;
    }
}
