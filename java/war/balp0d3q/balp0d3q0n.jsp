<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.OTHER_RPT_05_FILE_LIST%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D3Q0N" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="OtherRpt05Form" page="2" />
    <script language="javascript" type="text/javascript">
    <!--
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    -->
    </script>
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/printOtherRpt05" method="post" onsubmit="return validateOtherRpt05Form(this);">
        
        <fieldset>
            <legend>&nbsp;批次核定函&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="2" />
                        <html:hidden styleId="method" property="method" value="download" />
                        <html:hidden styleId="downloadFileName" property="downloadFileName" />
                    </td>
                </tr>
                <tr>
                    <td colspan="3" align="right">
                        <display:table name="pageScope.caseData" id="listItem" pagesize="0" excludedParams="*">
                            <display:column title="檔&nbsp;名" style="width:84%; text-align:center;">
                                &nbsp;<c:out value="${listItem}" />&nbsp;
                            </display:column>
                            <display:column title="操&nbsp;作" style="width:16%; text-align:center;">
                                <input type="button" name="btnDownload" class="button" value="下　載" onclick="$('page').value='2'; $('method').value='downloadFile'; $('downloadFileName').value='<c:out value="${listItem}" />'; document.OtherRpt05Form.submit();" />
                            </display:column>
                        </display:table>
                    </td>
                </tr>
            </table>
        </fieldset>
        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
