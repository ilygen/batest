<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.UPDATE_LOG_QUERY_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D031Q" />
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

    <div id="main" class="mainBody">

        <fieldset>
            <legend>&nbsp;更正日誌查詢&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="8">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="location.href='<c:url value="/enterUpdateLogQuery.do?parameter=enterUpdateLogQuery" />';" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" class="issuetitle_L">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="33%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:out value="${caseData.payCodeString}" />
                                </td>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">處理時間：</span>
                                    <c:out value="${caseData.updateDateBeginString}" />
                                    &nbsp;~&nbsp;
                                    <c:out value="${caseData.updateDateEndString}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${caseData.apNoString}" />
                                </td>
                                <td width="33%">
                                    <span class="issuetitle_L_down">身分證號：</span>
                                    <c:out value="${caseData.benIdnNo}" />
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">更正人員：</span>
                                    <c:out value="${caseData.updUser}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td colspan="8" align="center" class="px13">
                        <display:table name="pageScope.caseData.updateLogList" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" style="width: 100%; padding: 0 0 0 0; margin: 0 0 0 0;">
                            <display:column title="序號" style="width:6%; text-align:center;">
                                <c:out value="${listItem_rowNum}" />
                            </display:column>
                            <display:column title="受理編號 / 身分證號" style="width:15%; text-align:left; padding: 0 0 0 0;">
                                <table class="px13" cellpadding="0" cellspacing="0" width="100%" style="padding: 0 0 0 0;">
                                    <tr>
                                        <td class="issueinfo" width="100%" style="padding: 0 0 0 0;">
                                            <c:out value="${listItem.apNoString}" />
                                            <logic:empty property="apNoString" name="listItem">
                                                &nbsp;
                                            </logic:empty>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="issueinfo" border="0" width="100%" style="border-bottom: none; padding: 0 0 0 0;">
                                            <c:out value="${listItem.benIdnNo}" />
                                            <logic:empty property="benIdnNo" name="listItem">
                                                &nbsp;
                                            </logic:empty>
                                        </td>
                                    </tr>
                                </table>
                            </display:column>
                            <display:column title="受款人序" style="width:8%; text-align:center;">
                                <c:out value="${listItem.seqNo}" />
                                <logic:empty property="seqNo" name="listItem">
                                    &nbsp;
                                </logic:empty>
                            </display:column>
                            <display:column title="異動" style="width:6%; text-align:center;">
                                <c:out value="${listItem.statusString}" />
                                <logic:empty property="statusString" name="listItem">
                                    &nbsp;
                                </logic:empty>
                            </display:column>
                            <display:column title="更正欄名" style="width:16%; text-align:left;">
                                <c:out value="${listItem.upCol}" />
                                <logic:empty property="upCol" name="listItem">
                                    &nbsp;
                                </logic:empty>
                            </display:column>
                            <display:column title="更改內容" style="width:35%; text-align:left; padding: 0 0 0 0;">
                                <table class="px13" cellpadding="0" cellspacing="0" width="100%" style="padding: 0 0 0 0;">
                                    <tr>
                                        <td class="issueinfo" width="100%" style="padding: 0 0 0 0;" >
                                            改前：<c:out value="${listItem.bvalue}" />
                                            <logic:empty property="bvalue" name="listItem">
                                                &nbsp;
                                            </logic:empty>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="issueinfo" width="100%" style="border-bottom: none; padding: 0 0 0 0;">
                                            改後：<c:out value="${listItem.avalue}" />
                                            <logic:empty property="avalue" name="listItem">
                                                &nbsp;
                                            </logic:empty>
                                        </td>
                                    </tr>
                                </table>
                            </display:column>
                            <display:column title="更正人員" style="width:7%; text-align:left;">
                                <c:out value="${listItem.updUser}" />
                                <logic:empty property="updUser" name="listItem">
                                    &nbsp;
                                </logic:empty>
                            </display:column>
                            <display:column title="處理日期" style="width:7%; text-align:center;">
                                <c:out value="${listItem.updDateString}" />
                                <logic:empty property="updDateString" name="listItem">
                                    &nbsp;
                                </logic:empty>
                                <br />
                                <c:out value="${listItem.updTimeString}" />
                                <logic:empty property="updTimeString" name="listItem">
                                    &nbsp;
                                </logic:empty>
                            </display:column>
                        </display:table>
                    </td>
                </tr>
            </table>
        </fieldset>

    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
