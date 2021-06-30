<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X024C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="AdviceMaintDetailForm" page="1" />
    <script language="javascript" type="text/javascript">

    <%-- ] ... end --%>
    
    function initAll() {
        dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");    
    }

    Event.observe(window, 'load', initAll , false);
    -->
    </script>
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/adviceMaintDetail" method="post" onsubmit="return validateAdviceMaintDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;核定通知書維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="chooseData" property="chooseData"/>
                        <html:hidden styleId="type" property="type" value="add" />
                        <acl:checkButton buttonName="btnSave">
                            <input name="btnSave" type="button" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doAddCode'; if (document.AdviceMaintDetailForm.onsubmit()){document.AdviceMaintDetailForm.submit();}else{return false;}" />&nbsp;&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返　回" onClick="javascript:history.back();">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="5" align="center"><hr size="1" noshade>
                        <bean:define id="list" name="<%=ConstantKey.BALETTER_CODE_CASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" >
                        <display:column title="　" style="width:3%; text-align:center;">
                            <c:if test='${listItem.code1 ne null}'>
                                <html:radio styleId="addCode" property="addCode" value="${listItem.code1}"/>
                            </c:if>
                        </display:column>
                        <display:column title="代碼" style="width:5%; text-align:left;">
                            <c:out value="${listItem.code1}" />
                        </display:column>
                        <display:column title="中文說明" style="width:17%; text-align:left;">
                            <c:out value="${listItem.codeName1}" />
                        </display:column>
                        <display:column title="　" style="width:3%; text-align:center;">
                            <c:if test='${listItem.code2 ne null}'>
                                <html:radio styleId="addCode" property="addCode" value="${listItem.code2}"/>
                            </c:if>
                        </display:column>
                        <display:column title="代碼" style="width:5%; text-align:left;">
                            <c:out value="${listItem.code2}" />
                        </display:column>
                        <display:column title="中文說明" style="width:17%; text-align:left;">
                            <c:out value="${listItem.codeName2}" />
                        </display:column>
                        <display:column title="　" style="width:3%; text-align:center;">
                            <c:if test='${listItem.code3 ne null}'>
                                <html:radio styleId="addCode" property="addCode" value="${listItem.code3}"/>
                            </c:if>
                        </display:column>
                        <display:column title="代碼" style="width:5%; text-align:left;">
                            <c:out value="${listItem.code3}" />
                        </display:column>
                        <display:column title="中文說明" style="width:17%; text-align:left;">
                            <c:out value="${listItem.codeName3}" />
                        </display:column>
                        <display:column title="　" style="width:3%; text-align:center;">
                            <c:if test='${listItem.code4 ne null}'>
                                <html:radio styleId="addCode" property="addCode" value="${listItem.code4}"/>
                            </c:if>
                        </display:column>
                        <display:column title="代碼" style="width:5%; text-align:left;">
                            <c:out value="${listItem.code4}" />
                        </display:column>
                        <display:column title="中文說明" style="width:17%; text-align:left;">
                            <c:out value="${listItem.codeName4}" />
                        </display:column>
                        </display:table>
			        </td>
			    </tr>
            </table>
        </fieldset>
    
    </html:form>
    </div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>