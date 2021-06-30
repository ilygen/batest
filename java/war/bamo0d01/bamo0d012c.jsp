<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D012C" />
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
        <html:form action="/communicationDataUpdate" method="post">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="apNo" property="apNo" />
        <html:hidden styleId="baappbaseId" property="baappbaseId" />
        <input type="hidden" name="csrfToken" value="<c:out value="${csrfToken}"/>"/>
        <fieldset>
            <legend>&nbsp;通訊資料更正&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="6" align="right">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.CommunicationDataUpdateForm.submit();"/>
                        </acl:checkButton>                        
                    </td>
                </tr> 
                <tr>
                    <td colspan="6" class="issuetitle_L">
                        <bean:define id="comm" name="<%=ConstantKey.BAAPPBASE_DATA_UPDATE_CASE%>"/>                            
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${comm.apNoStrDisplay}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:if test='${(comm.pagePayKind eq "L")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                    </c:if>
                                    <c:if test='${(comm.pagePayKind eq "K")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                    </c:if>
                                    <c:if test='${(comm.pagePayKind eq "S")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                    </c:if>                                 
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${comm.appDateStr}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故日期：</span>
                                    <c:out value="${comm.evtJobDateStr}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${comm.evtName}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${comm.evtIdnNo}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${comm.evtBrDateStr}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td colspan="6" class="issuetitle_L">
                        <bean:define id="list" name="<%=ConstantKey.COMMUNICATION_DATA_UPDATE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                            <display:column title="序號" headerClass="issuetitle_L" style="width:8%; text-align:center;">
                                <c:out value="${listItem_rowNum}" />
                            </display:column>
                            <display:column title="受款人姓名" headerClass="issuetitle_L" style="width:15%; text-align:left;">
                                <c:out value="${listItem.benName}" />&nbsp;
                            </display:column>
                            <display:column title="受款人身分證號" headerClass="issuetitle_L" style="width:15%; text-align:left;">
                                <c:out value="${listItem.benIdnNo}" />&nbsp;
                            </display:column>
                            <display:column title="關　係" headerClass="issuetitle_L" style="width:12%; text-align:left;">
                                <c:if test='${(listItem.benEvtRel eq "1")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "2")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "3")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "4")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "5")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "6")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "7")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "A")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "C")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "E")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "F")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "N")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "Z")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "O")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O%>" />
                                </c:if>&nbsp;
                            </display:column>
                            <display:column title="通訊地址" headerClass="issuetitle_L" style="width:33%; text-align:left;">
                                <c:out value="${listItem.commZip}" />&nbsp;
                                <c:out value="${listItem.commAddr}" />
                            </display:column>
                            <display:column title="資料明細" headerClass="issuetitle_L" style="width:13%; text-align:center;">
                                <acl:checkButton buttonName="btnModify">
                                    <%--
                                    <c:if test="${(listItem.pagePayKind eq 'K')and(listItem.benDieDate ne '')}">
                                        <input type="button" name="btnModify" class="button" value="修　改" disabled="true" />&nbsp;
                                    </c:if>
                                    <c:if test="${(listItem.pagePayKind ne 'K')or((listItem.pagePayKind eq 'K')and(listItem.benDieDate eq ''))}">
                                        <input type="button" name="btnModify" class="button" value="修　改" onclick="$('page').value='1'; $('method').value='doModifyDetail'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.CommunicationDataUpdateForm.submit();" />&nbsp;
                                    </c:if>
                                    --%>
                                    <c:if test="${((listItem.pagePayKind eq 'L')or(listItem.pagePayKind eq 'K'))and(listItem.benDieDate ne '')}">
                                        <input type="button" name="btnModify" class="button" value="修　改" disabled="true" />&nbsp;
                                    </c:if>
                                    <c:if test="${(listItem.pagePayKind eq 'S')or(((listItem.pagePayKind eq 'L')or(listItem.pagePayKind eq 'K'))and(listItem.benDieDate eq ''))}">
                                        <input type="button" name="btnModify" class="button" value="修　改" onclick="$('page').value='1'; $('method').value='doModifyDetail'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.CommunicationDataUpdateForm.submit();" />&nbsp;
                                    </c:if>
                                </acl:checkButton>                            
                            </display:column>
                        </display:table>              
                    </td>                
                </tr>                
                <tr>
                    <td colspan="6">&nbsp;</td>
                </tr>                                                
            </table>
        </fieldset>
        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
