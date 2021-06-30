<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D021Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <script language="javascript" type="text/javascript">

    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/receivableQuery" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="apNo" property="apNo" value="" />
        <html:hidden styleId="evtIdnNo" property="evtIdnNo" value="" />
        <html:hidden styleId="unacpDate" property="unacpDate" value="" />
        
        <fieldset>
            <legend>&nbsp;應收查詢&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="9" align="right">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.ReceivableQueryForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>                
                <bean:define id="master" name="<%=ConstantKey.RECEIVABLE_QUERY_MASTER_CASE %>" />
                <tr>
                    <td colspan="9" class="issuetitle_L">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>        
                                <td width="33%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${master.apNoStr}" />
                                </td>
                                <td width="33%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:if test='${(master.pagePayKind eq "L")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                    </c:if>
                                    <c:if test='${(master.pagePayKind eq "K")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                    </c:if>
                                    <c:if test='${(master.pagePayKind eq "S")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                    </c:if>
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">應收立帳起迄日期：</span>
                                    <c:out value="${master.unacpSdateStr}" />
                                    &nbsp;~&nbsp;
                                    <c:out value="${master.unacpEdateStr}" />
                                </td>
                            </tr>
                            <tr>        
                                <td>
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${master.evtName}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${master.evtIdnNo}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${master.evtBrDateStr}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.RECEIVABLE_QUERY_MASTER_CASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">                            
                            <display:column title="序 號" style="width:7%; text-align:center;">
                                <c:out value="${listItem_rowNum}" />&nbsp;
                            </display:column>
                            <display:column title="受款人姓名" style="width:12%; text-align:center;">
                                <c:out value="${listItem.benName}" />&nbsp;
                            </display:column>
                            <display:column title="受款人身分證號" style="width:12%; text-align:center;">
                                <c:out value="${listItem.benIdnNo}" />&nbsp;
                            </display:column>
                            <display:column title="應收總金額" style="width:12%; text-align:center;">
                                <fmt:formatNumber value="${listItem.recAmt}" />&nbsp;
                            </display:column>
                            <display:column title="收回總金額" style="width:12%; text-align:center;">
                                <fmt:formatNumber value="${listItem.woAmt}" />&nbsp;
                            </display:column>
                            <display:column title="未收金額" style="width:12%; text-align:center;">
                                <fmt:formatNumber value="${listItem.recRem}" />&nbsp;
                            </display:column>
                            <display:column title="應收立帳日期" style="width:12%; text-align:center;">
                                <c:out value="${listItem.unacpDateStr}" />&nbsp;
                            </display:column>
                            <display:column title="應收狀態" style="width:12%; text-align:center;">
                                <c:if test='${(listItem.recKind eq "01")}'>
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_01%>" />
                                </c:if>
                                <c:if test='${(listItem.recKind eq "02")}'>
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_02%>" />
                                </c:if>
                                <c:if test='${(listItem.recKind eq "03")}'>
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_03%>" />
                                </c:if>
                                <c:if test='${(listItem.recKind ne "")or(listItem.procStat ne "")}'>
                                    &nbsp;-&nbsp;
                                </c:if>                                                                
                                <c:if test='${(listItem.procStat eq "00")}'>
                                    <c:out value="<%=ConstantKey.BAUNACPREC_PROCSTAT_STR_00%>" />
                                </c:if>
                                <c:if test='${(listItem.procStat eq "99")}'>
                                    <c:out value="<%=ConstantKey.BAUNACPREC_PROCSTAT_STR_99%>" />
                                </c:if>
                            </display:column>
                            <display:column title="資料明細" style="width:16%; text-align:center;">
                                <input type="button" name="btnDetail" class="button" value="明細查詢" onclick="$('page').value='1'; $('method').value='doDetail'; $('apNo').value='<c:out value="${listItem.apNo}" />'; $('evtIdnNo').value='<c:out value="${listItem.evtIdnNo}" />'; $('unacpDate').value='<c:out value="${listItem.unacpDate}" />'; document.ReceivableQueryForm.submit();" />
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
