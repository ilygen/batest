<%@ taglib prefix="c"           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"         uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql"         uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x"           uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="html"        uri="http://struts.apache.org/tags-html"  %>
<%@ taglib prefix="html-el"     uri="http://struts.apache.org/tags-html-el"  %>
<%@ taglib prefix="bean"        uri="http://struts.apache.org/tags-bean"  %>
<%@ taglib prefix="bean-el"     uri="http://struts.apache.org/tags-bean-el"  %>
<%@ taglib prefix="logic"       uri="http://struts.apache.org/tags-logic"  %>
<%@ taglib prefix="logic-el"    uri="http://struts.apache.org/tags-logic-el"  %>
<%@ taglib prefix="display"     uri="http://displaytag.sf.net" %>
<%@ taglib prefix="display-el"  uri="http://displaytag.sf.net/el" %>
<%@ taglib prefix="acl"         uri="http://www.bli.gov.tw/common-acl" %>
<%@ taglib prefix="func"        uri="http://www.bli.gov.tw/common-func" %>
<%@ page import="tw.gov.bli.common.helper.UserSessionHelper" %>
<%@ page import="tw.gov.bli.ba.framework.domain.UserBean" %>
<%@ page import="tw.gov.bli.ba.Global" %>
<%@ page import="tw.gov.bli.ba.ConstantKey" %>
<%@ page import="tw.gov.bli.ba.util.DateUtility" %>

<%
    UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    if (userData == null) {
%>
    <c:redirect url="/unauthorized.jsp" />
<%
    }
    else {
        pageContext.setAttribute("userData", userData);
    }
%>

<bean:define id="baResBundle" name="<%=Global.BA_MSG%>" type="org.apache.struts.util.MessageResources" scope="application"/>
