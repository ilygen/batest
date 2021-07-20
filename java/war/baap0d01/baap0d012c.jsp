<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAAP0D012C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script language="javascript" type="text/javascript">
    
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/oldAgeAnnuityReceiptList" method="post" onsubmit="return validateOldAgeAnnuityReceiptForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="baappbaseId" property="baappbaseId" value="" />
        
        <fieldset>
            <legend>&nbsp;老年年金給付受理作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
                                                
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
              <tr>
                <td align="right" colspan="8">
                    <acl:checkButton buttonName="btnBack">
                        <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.OldAgeAnnuityReceiptForm.submit();"/>
                    </acl:checkButton>
                </td>
              </tr>
              <tr align="center">
                <td>
                    <bean:define id="list" name="<%=ConstantKey.OLDAGE_ANNUITY_RECEIPT_LIST%>" />
                    <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                        <display:column title="序號" headerClass="issuetitle_L" class="issueinfo_C" style="width:7%; text-align:center;">
                            <c:out value="${listItem_rowNum}" />
                        </display:column>
                        <display:column title="申請日期" headerClass="issuetitle_L" class="issueinfo_C" style="width:10%; text-align:center;">
                            <c:out value="${listItem.appDateStr}" />&nbsp;
                        </display:column>
                        <display:column title="受理編號" headerClass="issuetitle_L" class="issueinfo_C" style="width:15%; text-align:center;">
                            <c:out value="${listItem.apNoStrDisplay}" />&nbsp;
                        </display:column>
                        <display:column title="事故者姓名" headerClass="issuetitle_L" class="issueinfo" style="width:20%;">
                            <c:out value="${listItem.evtName}" />&nbsp;
                        </display:column>
                        <display:column title="事故者身分證號" headerClass="issuetitle_L" class="issueinfo" style="width:12%;">
                            <c:out value="${listItem.evtIdnNo}" />&nbsp;
                        </display:column>
                         <display:column title="事故者出生日期" headerClass="issuetitle_L" class="issueinfo_C" style="width:12%; text-align:center;">
                            <c:out value="${listItem.evtBrDateStr}" />&nbsp;
                        </display:column>
                        <display:column title="申請項目" headerClass="issuetitle_L" class="issueinfo" style="width:14%;">
                            <c:if test='${(listItem.apItem eq "1")}'>
                                <c:out value="<%=ConstantKey.APITEM_PARAMCODE_1%>" />
                            </c:if>
                            <c:if test='${(listItem.apItem eq "2")}'>
                                <c:out value="<%=ConstantKey.APITEM_PARAMCODE_2%>" />
                            </c:if>
                            <c:if test='${(listItem.apItem eq "3")}'>
                                <c:out value="<%=ConstantKey.APITEM_PARAMCODE_3%>" />
                            </c:if>&nbsp;
                        </display:column>
                        <display:column title="資料明細" style="width:10%; text-align:center;">
                            <acl:checkButton buttonName="btnModify2">
                                <input type="button" name="btnModify2" class="button" value="修　改" onclick="$('page').value='1'; $('method').value='prepareModifyDetail'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.OldAgeAnnuityReceiptForm.submit();" />&nbsp;
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
