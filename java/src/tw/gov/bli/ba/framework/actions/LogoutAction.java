package tw.gov.bli.ba.framework.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.common.helper.UserSessionHelper;

public class LogoutAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        // SSO 之故移往 AJAX 做
        // HttpSession session = request.getSession();
        // UserSessionHelper.removeUserData(request);
        
        return mapping.findForward(ConstantKey.FORWARD_SUCCESS);
    }
}
