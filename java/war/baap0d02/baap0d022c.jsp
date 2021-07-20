<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAAP0D022C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/receiptCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
</head>

<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/disabledAnnuityReceiptList" method="post" >
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="baappbaseId" property="baappbaseId" />
               
        <fieldset>
            <legend>&nbsp;失能年金給付受理作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
          	        <td align="right" colspan="8">
          	            <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.DisabledAnnuityReceiptForm.submit();"/>
                        </acl:checkButton>
          	        </td>
                </tr>
                <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.DISABLED_ANNUITY_RECEIPT_LIST%>" />
                        
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                        <display:column title="序號" style="width:8%; text-align:center;">
                            <c:out value="${listItem_rowNum}" />
                        </display:column>
                        <display:column title="申請日期" style="width:10%; text-align:center;">
                            <c:out value="${listItem.appDateStr}" />&nbsp;
                        </display:column>
                        <display:column title="受理編號" style="width:15%; text-align:center;">
                            <c:out value="${listItem.apNoStrDisplay}" />&nbsp;
                        </display:column>
                        <display:column title="事故者姓名" style="width:12%; text-align:left;">
                            <c:out value="${listItem.evtName}" />&nbsp;
                        </display:column>
                        <display:column title="事故者身分證號" style="width:15%; text-align:left;">
                            <c:out value="${listItem.evtIdnNo}" />&nbsp;
                        </display:column>
                        <display:column title="事故者出生日期" style="width:13%; text-align:center;">
                            <c:out value="${listItem.evtBrDateStr}" />&nbsp;
                        </display:column>
                        <display:column title="申請項目" style="width:14%; text-align:left;">
                            <c:out value="${listItem.apItemStr}" />&nbsp;
                        </display:column>
                        <display:column title="資料明細" style="width:13%; text-align:center;">
                            <acl:checkButton buttonName="btnModify">
                                <input type="button" name="btnModify" class="button" value="修　改" onclick="$('page').value='1'; $('method').value='prepareModifyDetail'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.DisabledAnnuityReceiptForm.submit();" />&nbsp;
                            </acl:checkButton>                            
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