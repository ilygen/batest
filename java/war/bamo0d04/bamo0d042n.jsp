<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D042N" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="StopPaymentProcessQueryForm" page="1" />
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/stopPaymentProcessQuery" method="post" onsubmit="return validateStopPaymentProcessQueryFormForm(this);">
        
        <fieldset>
            <legend>&nbsp;止付處理&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="8">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.StopPaymentProcessQueryForm.submit();" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" class="issuetitle_L">                        
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="33%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${StopPaymentProcessQueryCase[0].apNoString}" />
                                </td>
                                <td width="33%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:out value="${StopPaymentProcessQueryCase[0].payCode}" />
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${StopPaymentProcessQueryCase[0].appDateChineseString}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${StopPaymentProcessQueryCase[0].evtName}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${StopPaymentProcessQueryCase[0].evtBrDateChineseString}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${StopPaymentProcessQueryCase[0].evtIdnNo}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                <td class="issuetitle_L">
                <bean:define id="list" name="<%=ConstantKey.STOP_PAYMENT_PROCESS_QUERY_CASE%>" />
                    
                <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >
                <display:column title="序號" style="width:4%; text-align:center;" class="issuetitle_L">
                    <c:out value="${listItem_rowNum}" />
    			</display:column>
                <display:column property="benName" title="受款人姓名" style="width:10%; text-align:center;" />
                <display:column property="benIdnNo" title="受款人身分證號" style="width:10%; text-align:center;" />
                <display:column property="benEvtRelName" title="關 係" style="width:15%; text-align:center;" />
                <display:column property="issuYm" title="核定年月" style="width:20%; text-align:center;" />
                <display:column property="issueAmt" title="核定金額" style="width:20%; text-align:center;" />
                <display:column property="aplpayAmt" title="實付金額" style="width:15%; text-align:center;" />            
                <display:column title="資料明細" style="width:10%; text-align:center;">
                    <acl:checkButton buttonName="btnUpdate">
                        <logic:notEqual property="stexpndMk" name="listItem" value="Y" >
                            <input type="button" class="button" name="btnUpdate" value="止　付" onclick="location.href='<c:url value="/stopPaymentProcessQuery.do"/>?method=doUpdate&apNo=<c:out value="${listItem.apNo}" />&baappbaseId=<c:out value="${listItem.baappbaseId}" />';">
                        </logic:notEqual>
                        <logic:equal property="stexpndMk" name="listItem" value="Y" >
                                                                            已止付
                        </logic:equal>
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