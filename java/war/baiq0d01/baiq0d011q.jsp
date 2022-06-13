<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D011Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
          
    }    
    
    <%-- 確認按鈕 --%>
    <%-- 按下 確認按鈕時  檢查checkbox是否有勾選的資料 --%>    
    <%-- begin ... [ --%>
    function checkFields() {
           
    }
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/paymentQuery" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="apNo" property="apNo" value="" />
        
        <fieldset>
            <legend>&nbsp;給付查詢&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="8" align="right">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.PaymentQueryForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>                
                <bean:define id="qryForm" name="<%=ConstantKey.PAYMENT_QUERY_COND_FORM %>" />
                <c:if test="${((qryForm.startYm ne '')&&(qryForm.endYm ne ''))}">
                <tr>
                    <td colspan="8" class="issuetitle_L">
                         <c:if test="${(qryForm.qryCond eq 'ISSUYM')}">
                            <c:out value="核定年月起迄："/>                         
                         </c:if>                         
                         <c:if test="${(qryForm.qryCond eq 'PAYYM')}">
                            <c:out value="給付年月起迄："/>                         
                         </c:if>
                         <span class="px13">
                            <c:out value="${qryForm.startYm}"/>
                            &nbsp;~&nbsp;
                            <c:out value="${qryForm.endYm}"/>
                         </span>
                    </td>
                </tr>
                </c:if>
                <tr align="center">                   
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.PAYMENT_QUERY_MASTER_CASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">                            
                            <display:column title="序 號" headerClass="issuetitle_L" class="issueinfo_C" style="width:7%; text-align:center;">
                                <c:out value="${listItem_rowNum}" />&nbsp;
                            </display:column>
                            <display:column title="受理編號" headerClass="issuetitle_L" class="issueinfo" style="width:12%; text-align:left;">
                                <c:out value="${listItem.apNoStrDisplay}" />&nbsp;
                                <c:if test='${(listItem.pagePayKind ne "L")}'>
                                	<c:out value="${listItem.sysCode}" />&nbsp;
                                </c:if>
                            </display:column>
                            <display:column title="事故者姓名" headerClass="issuetitle_L" class="issueinfo" style="width:16%; text-align:left;">
                                <c:out value="${listItem.evtName}" />&nbsp;
                            </display:column>
                            <display:column title="事故者身分證號" headerClass="issuetitle_L" class="issueinfo" style="width:11%; text-align:left;">
                                <c:out value="${listItem.evtIdnNo}" />&nbsp;
                            </display:column>
                            <display:column title="事故者出生日期" headerClass="issuetitle_L" class="issueinfo_C" style="width:11%; text-align:center;">
                                <c:out value="${listItem.evtBrDateStr}" />&nbsp;
                            </display:column>
                            <display:column title="給付別" headerClass="issuetitle_L" class="issueinfo" style="width:9%; text-align:left;">
                                <c:if test='${(listItem.pagePayKind eq "L")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                </c:if>
                                <c:if test='${(listItem.pagePayKind eq "K")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                </c:if>
                                <c:if test='${(listItem.pagePayKind eq "S")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                </c:if>&nbsp;
                            </display:column>
                            <display:column title="申請日期" headerClass="issuetitle_L" class="issueinfo_C" style="width:9%; text-align:center;">
                                <c:out value="${listItem.appDateStr}" />&nbsp;
                            </display:column> 
                            <display:column title="處理狀態" headerClass="issuetitle_L" class="issueinfo" style="width:17%; text-align:left;">
                                <c:out value="${listItem.procStatStr}" />&nbsp;
                            </display:column>                            
                            <display:column title="資料明細" headerClass="issuetitle_L" class="issueinfo_C" style="width:8%; text-align:center;">
                                <acl:checkButton buttonName="btnDetail">
                                    <c:if test='${(listItem.pagePayKind eq "L")}'>
                                    <c:choose>
                                        <c:when test='${listItem.detailLock eq "Y"}'>
									       <input type="button" name="btnDetail" class="button" value="明細查詢" disabled="true" />&nbsp;
								        </c:when>
								        <c:otherwise>
									       <input type="button" name="btnDetail" class="button" value="明細查詢" onclick="$('page').value='1'; $('method').value='doOldAgeDetail'; $('apNo').value='<c:out value="${listItem.apNo}" />'; document.PaymentQueryForm.submit();" />&nbsp;                                     
								        </c:otherwise>	
								    </c:choose>	    
                                        <c:if test='${(listItem.secrecy eq "Y")}'>
                                           <br><c:out value="※保密案件"/></br> 
                                        </c:if>
                                    </c:if>
                                    <c:if test='${(listItem.pagePayKind eq "K")}'>
                                    <c:choose>
                                        <c:when test='${listItem.detailLock eq "Y"}'>
									       <input type="button" name="btnDetail" class="button" value="明細查詢" disabled="true" />&nbsp;
								        </c:when>
								        <c:otherwise>
									       <input type="button" name="btnDetail" class="button" value="明細查詢" onclick="$('page').value='1'; $('method').value='doDisabledDetail'; $('apNo').value='<c:out value="${listItem.apNo}" />'; document.PaymentQueryForm.submit();" />&nbsp;                                     
								        </c:otherwise>	
								    </c:choose>	    
                                        <c:if test='${(listItem.secrecy eq "Y")}'>
                                           <br><c:out value="※保密案件"/></br> 
                                        </c:if>
                                    </c:if>
                                    <c:if test='${(listItem.pagePayKind eq "S")}'>
                                    <c:choose>
                                        <c:when test='${listItem.detailLock eq "Y"}'>
									       <input type="button" name="btnDetail" class="button" value="明細查詢" disabled="true" />&nbsp;
								        </c:when>
								        <c:otherwise>
									       <input type="button" name="btnDetail" class="button" value="明細查詢" onclick="$('page').value='1'; $('method').value='doSurvivorDetail'; $('apNo').value='<c:out value="${listItem.apNo}" />'; document.PaymentQueryForm.submit();" />&nbsp;                                     
								        </c:otherwise>
								    </c:choose>    
                                        <c:if test='${(listItem.secrecy eq "Y")}'>
                                           <br><c:out value="※保密案件"/></br> 
                                        </c:if>
                                    </c:if>
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
