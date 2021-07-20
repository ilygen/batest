<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M031X" />
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
        <html:form action="/trans2OverdueReceivableBJ" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;轉催收作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="9" align="right">
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnConfirm" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doBatch'; document.Trans2OverdueReceivableBJForm.submit();" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.Trans2OverdueReceivableBJForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>                
                <bean:define id="master" name="<%=ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_MASTER_CASE %>" />
                <tr>
                    <td colspan="9" class="issuetitle_L">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="33%">
                                    <span class="issuetitle_L_down">應收立帳起迄日期：</span>
                                    <c:out value="${master.unacpSdateStr}" />
                                    &nbsp;~&nbsp;
                                    <c:out value="${master.unacpEdateStr}" />
                                </td>
                                <td width="33%">
                                    <span class="issuetitle_L_down">應收總筆數：</span>
                                    <c:out value="${master.totalAmt}" />
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">應收總金額：</span>
                                    <c:out value="${master.sumRecAmt}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_DETAIL_CASE%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">                            
                            <display:column title="受理編號" style="width:14%; text-align:center;">
                                <c:out value="${listItem.apNoStr}" />&nbsp;
                            </display:column>
                            <display:column title="立帳對象" style="width:14%; text-align:center;">
                                <c:out value="${listItem.evtName}" />&nbsp;
                            </display:column>
                            <display:column title="身分證號" style="width:16%; text-align:center;">
                                <c:out value="${listItem.evtIdnNo}" />&nbsp;
                            </display:column>
                            <display:column title="立帳日期" style="width:14%; text-align:center;">
                                <c:out value="${listItem.unacpDateStr}" />&nbsp;
                            </display:column>
                            <display:column title="應收金額" style="width:14%; text-align:center;">
                                <c:out value="${listItem.recAmt}" />&nbsp;
                            </display:column>
                            <display:column title="已收金額" style="width:14%; text-align:center;">
                                <c:out value="${listItem.woAmt}" />&nbsp;
                            </display:column>
                            <display:column title="未收金額" style="width:14%; text-align:center;">
                                <c:out value="${listItem.recRem}" />&nbsp;
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
