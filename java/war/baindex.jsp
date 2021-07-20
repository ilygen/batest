<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAINDEX" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main2" class="mainBody2">
        <table height="100%" width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
            <tr height="100%">  
                <td height="100%" width="100%" align="left" valign="bottom" >
                    <font color="#FFFFFF" style="font-size: 12px;">主機資訊:<func:serverInfo/></font>
                </td>
            </tr>
        </table>
    </div>
    
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
