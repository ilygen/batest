<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.SURVIVOR_APPLICATION_DATA_UPDATE_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D233C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="SurvivorApplicationDataUpdateForm" page="3" />
    <script language="javascript" type="text/javascript">
    <!--

    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/survivorApplicationDataUpdate" method="post">
        
        <fieldset>
            <legend>&nbsp;遺屬年金案件資料更正&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="5">
                        <html:hidden styleId="page" property="page" value="3" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" id="btnConfirm" name="btnConfirm" tabindex="999" class="button" value="確　認" onclick="$('page').value='3'; $('method').value='doSelectDupe'; document.SurvivorApplicationDataUpdateForm.submit();" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" id="btnBack" name="btnBack" tabindex="1000" class="button" value="返　回" onclick="$('page').value='3'; $('method').value='doBackToDetail'; document.SurvivorApplicationDataUpdateForm.submit();" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="5" class="issuetitle_L">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <bean:write name="SurvivorApplicationDataUpdateForm" property="apNoString" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <bean:write name="SurvivorApplicationDataUpdateForm" property="evtIdnNo" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="5" class="issuetitle_L">
                        <blockquote>
                            <blockquote>
                                <p>
                                    <span class="issuetitle_search">&#9658;</span>&nbsp;事故者編審註記：BK&nbsp;-&nbsp;W(同身分證號者有多人)</span>
                                </p>
                            </blockquote>
                        </blockquote>
                    </td>
                </tr>
                <tr align="center">
                    <td width="10%" class="issuetitle_L">&nbsp;</td>
                    <td width="20%" class="issuetitle_L">事故者姓名</td>
                    <td width="20%" class="issuetitle_L">事故者出生日期</td>
                    <td width="50%" class="issuetitle_L">&nbsp;</td>
                </tr>
                <c:forEach var="dupeData" items="${caseData.dupeIdnList}" varStatus="i">
                <tr>
                    <td class="issueinfo_C">
                        <c:choose>
                            <c:when test="${i.index eq 0 or dupeData.selMk eq '2'}">
                                <input type='radio' name='radioDupeIndex' value='<c:out value="${i.index}" />' checked>
                            </c:when>
                            <c:otherwise>
                                <input type='radio' name='radioDupeIndex' value='<c:out value="${i.index}" />'>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="issueinfo_C">
                        <c:out value="${dupeData.name}" />
                    </td>
                    <td class="issueinfo_C">
                        <c:out value="${dupeData.brDateString}" />
                    </td>
                    <td class="issueinfo">&nbsp;</td>
                </tr>
                </c:forEach>
                <tr>
                    <td colspan="5">&nbsp;</td>
                </tr>
            </table>
        </fieldset>

        </html:form>

        <p></p>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
