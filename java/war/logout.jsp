<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include_nologin.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/logoutAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script language="javascript" type="text/javascript">
    <!--
    function doLogout() {
        logoutAjaxDo.doLogout(doAfterLogout);
        $("msg").innerHTML = "系統登出中請稍侯...";
    }

    function doAfterLogout(data) {
        $("msg").innerHTML = "登出成功，視窗關閉中...";
        window.close();
    }
    
    function initAll() {
        doLogout();
    }
    Event.observe(window, 'load', initAll , false);
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <div id="main" class="mainBody">
    
        <div align="center">
            <font color="red"><span id="msg">系統登出中請稍侯...</span></font>
            <br />
            <br />
            <br />
        <div>

    </div>

</div>

</body>
</html:html>
