<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPM0D011L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
                    
    }      
    function checkFields(){
    	
        var msg="";
        if(msg!=""){
            return false;
        }else{
            return true;
        }
    }
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
    	<html:form action="/paymentProcessList" method="post" onsubmit="return validatePaymentProcessQueryForm(this);">
       
        <fieldset>
            <legend>&nbsp;開單維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                    	<html:hidden styleId="page" 		property="page" value="1" />
                        <html:hidden styleId="method" 		property="method" value="" />
                        <html:hidden styleId="paymentNoforModify"	property="paymentNoforModify" value="" />
                        <html:hidden styleId="insertString"	property="insertString" value="" />
       					<html:hidden styleId="modifyString" 	property="modifyString" value="" />
                    	<acl:checkButton buttonName="btnInsert">
                            <input name="btnInsert" type="button" class="button" value="新　增" onclick="$('page').value='1'; $('insertString').value='I'; $('method').value='doInsert'; document.PaymentProcessQueryForm.submit();" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="javascript:history.back();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>    
                <tr>
                    <td colspan="11" class="issuetitle_L">
                        <bean:define id="payTitle" name="<%=ConstantKey.PAYMENT_PROCESS_QUERY_FORM%>" />                        
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">身分證號：</span>
                                    <c:out value="${payTitle.idn}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">姓名：</span>
                                    <c:out value="${payTitle.evtName}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <logic:empty name="<%=ConstantKey.PAYMENT_PROCESS_QUERY_CASE_LIST%>">
                	<tr>
                    	<td align="center" colspan="10">
                        	<font color="red">無資料</font>
                    	</td>
                	</tr>                             
                </logic:empty>
                <logic:notEmpty name="<%=ConstantKey.PAYMENT_PROCESS_QUERY_CASE_LIST%>">
	                <tr align="center">
	                    <td>
	                        <bean:define id="list" name="<%=ConstantKey.PAYMENT_PROCESS_QUERY_CASE_LIST%>" />
	                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
	                            <display:column title="序號" style="width:12%; text-align:center;">
	                                <c:out value="${listItem_rowNum}" />&nbsp;
	                            </display:column>
	                            <display:column title="列印日期" style="width:15%; text-align:center;">
	                                <c:out value="${listItem.prtDate}" />&nbsp;
	                            </display:column>
	                            <display:column title="繳款單號" style="width:12%; text-align:center;">
	                                <c:out value="${listItem.paymentNo}" />&nbsp;
	                            </display:column>
	                            <display:column title="移送案號" style="width:10%; text-align:center;">
	                                <c:out value="${listItem.caseNo}" />&nbsp;
	                            </display:column>
	                            <display:column title="主辦案受理編號" style="width:8%; text-align:center;">
	                                <c:out value="${listItem.apno}" />&nbsp;
	                            </display:column>
	                            <display:column title="應繳總額" style="width:8%; text-align:right;">
	                                <fmt:formatNumber value="${listItem.payTotAmt}" />
	                                <c:if test="${listItem.payTotAmt eq null}" >
	                                    &nbsp;
	                                </c:if>
	                            </display:column>
	                            <display:column title="修　改" style="width:9%; text-align:center;">
	                                <acl:checkButton buttonName="btnModify">
	                                    <input type="button" name="btnModify" class="button" value="修　改" onclick="$('page').value='1';  $('method').value='doModify'; $('modifyString').value='M'; $('paymentNoforModify').value='<c:out value="${listItem.paymentNo}" />'; document.PaymentProcessQueryForm.submit();" />&nbsp;
	                                </acl:checkButton>      
	                                                    
	                            </display:column>
	                        </display:table>
	                    </td>
	                </tr>
	            </logic:notEmpty>         
            </table>
        </fieldset>        
        </html:form>
    </div>
</div>
<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>