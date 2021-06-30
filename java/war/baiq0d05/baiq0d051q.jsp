<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D051Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="ExecutiveSupportQueryListForm" page="1" />
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/executiveSupportQueryList" method="post" onsubmit="return validateExecutiveSupportQueryListForm(this);">
        
        <fieldset>
            <legend>&nbsp;行政支援查詢&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
            <tr>
                <td align="right" colspan="9">
                    <bean:define id="qryCondFrom" name="<%=ConstantKey.EXECUTIVE_SUPPORT_QUERY_COND_FORM %>"/> 
                    <html:hidden styleId="page" property="page" value="1" />
                    <html:hidden styleId="method" property="method" value="" />
                    <html:hidden styleId="index" property="index" value="" />
                    <c:if test="${(qryCondFrom.qryFromWhere eq 'BAIQ0D050Q')}">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返  回" onclick="$('page').value='1'; $('method').value='doBack'; document.ExecutiveSupportQueryListForm.submit();" />
                        </acl:checkButton>
                    </c:if>
                    <c:if test="${(qryCondFrom.qryFromWhere eq 'BAIQ0D012Q')or(qryCondFrom.qryFromWhere eq 'BAIQ0D062Q')}">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackToPaymentQueryForOldAge'; document.ExecutiveSupportQueryListForm.submit();" />
                        </acl:checkButton>
                    </c:if>
                    <c:if test="${(qryCondFrom.qryFromWhere eq 'BAIQ0D112Q')or(qryCondFrom.qryFromWhere eq 'BAIQ0D162Q')}">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackToPaymentQueryForDisabled'; document.ExecutiveSupportQueryListForm.submit();" />
                        </acl:checkButton>
                    </c:if>
                    <c:if test="${(qryCondFrom.qryFromWhere eq 'BAIQ0D212Q')or(qryCondFrom.qryFromWhere eq 'BAIQ0D262Q')}">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackToPaymentQueryForSurvivor'; document.ExecutiveSupportQueryListForm.submit();" />
                        </acl:checkButton>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td colspan="9" class="issuetitle_L">
                    <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                    <tr>
                        <td width="25%">
                            <span class="issuetitle_L_down">受理編號：</span>
                            <c:out value="${ExecutiveSupportQueryCase.apNoStr}" />
                        </td>
                        <td width="25%">
                            <span class="issuetitle_L_down">給付別：</span>
                            <c:out value="${ExecutiveSupportQueryCase.payKindStr}" />
                        </td>
                        <td colspan="2">
                            <span class="issuetitle_L_down">審核人員：</span>
                            <c:out value="${ExecutiveSupportQueryCase.chkMan}" />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <span class="issuetitle_L_down">事故者姓名：</span>
                            <c:out value="${ExecutiveSupportQueryCase.evtName}" />
                        </td>
                        <td width="25%">
                            <span class="issuetitle_L_down">事故者出生日期：</span>
                            <c:out value="${ExecutiveSupportQueryCase.evtBrDate}" />
                        </td>
                        <td width="25%">
                            <span class="issuetitle_L_down">事故者身分證號：</span>
                            <c:out value="${ExecutiveSupportQueryCase.evtIdnNo}" />
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>
            <tr align="center">
                <td colspan="9" class="issuetitle_L">
                    <bean:define id="list" name="<%=ConstantKey.EXECUTIVE_SUPPORT_QUERY_CASE_LIST%>" />
                
                    <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                    <display:column  title="核定年月" headerClass="issuetitle_L" style="width:8%; text-align:center;">
                        <c:out value="${listItem.issuYm}" />&nbsp;
                    </display:column>   
                    <display:column  title="承辦日期" headerClass="issuetitle_L" style="width:8%; text-align:center;">
                        <c:out value="${listItem.proDate}" />&nbsp;
                    </display:column>
                    <display:column  title="承辦人員" headerClass="issuetitle_L" style="width:8%; text-align:center;">
                        <c:out value="${listItem.promoteUser}" />&nbsp;
                    </display:column>
                    <display:column  title="處理函別" headerClass="issuetitle_L" style="width:9%; text-align:left;">
                        <c:out value="${listItem.letterType}" />
                        <c:if test="${(listItem.letterType ne '')or(listItem.paramName ne '')}">
                        -
                        </c:if>
                        <c:out value="${listItem.paramName}" />&nbsp;
                    </display:column>
                    <display:column  title="序號" headerClass="issuetitle_L" style="width:7%; text-align:center;">
                        <c:out value="${listItem.seqNo}" />&nbsp;
                    </display:column>
                    <display:column  title="函別內容一/救濟事由" headerClass="issuetitle_L" style="width:18%; text-align:left;">
                        <c:out value="${listItem.ndomk1}" />
                        <c:if test="${(listItem.ndomk1 ne '')and(listItem.ndomkName1 ne '')}">
                        -
                        </c:if>
                        <c:out value="${listItem.ndomkName1}" />&nbsp;
                    </display:column>
                    <display:column  title="函別內容二/救濟事由" headerClass="issuetitle_L" style="width:18%; text-align:left;">
                        <c:out value="${listItem.ndomk2}" />
                        <c:if test="${(listItem.ndomk2 ne '')and(listItem.ndomkName2 ne '')}">
                        -
                        </c:if>
                        <c:out value="${listItem.ndomkName2}" />&nbsp;
                    </display:column>
                    <display:column  title="銷案日期" headerClass="issuetitle_L" style="width:8%; text-align:center;">
                        <c:out value="${listItem.closDate}" />&nbsp;
                    </display:column>
                    <display:column  title="管制註記" headerClass="issuetitle_L" style="width:8%; text-align:center;">
                        <c:out value="${listItem.delMk}" />&nbsp;
                    </display:column>
                    <display:column  title="資料明細" headerClass="issuetitle_L" style="width:8%; text-align:center;">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnQuery" class="button" value="明細查詢" onclick="$('page').value='1'; $('method').value='doDetail';$('index').value='<c:out value="${listItem_rowNum}" />';if (document.ExecutiveSupportQueryListForm.onsubmit()){document.ExecutiveSupportQueryListForm.submit();}else{return false;}" />
                        </acl:checkButton>
                    </display:column>
                    </display:table>
                </td>
            </tr>
            <tr>
                <td colspan="8" align="center" class="word12"><br>
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
