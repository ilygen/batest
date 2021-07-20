<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPM0D031M" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script language="javascript" type="text/javascript">
    
   
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
    	<html:form action="/paymentProgressQuery" method="post" onsubmit="return validatePaymentProgressQueryForm(this);">
        <fieldset>
            <legend>&nbsp;繳款單查詢作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
            <bean:define id="payTitle" name="<%=ConstantKey.PAYMENT_PROGRESS_QUERY_FORM%>" />
                <tr>
                    <td colspan="2" align="right">
                    	<html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="idForConfirm" property="idForConfirm" value="" />
                       
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返   回" onclick="$('page').value='1'; $('method').value='doBackFirstPage'; document.PaymentProgressQueryForm.submit();"/>&nbsp;
                       	</acl:checkButton>                   
                    </td>
                </tr>
                <tr>
		            <td colspan="11" class="issuetitle_L">
		             
		              <table class="px13" width="100%" border="0" cellspacing="2" cellpadding="2">
		                <tr>
		                   <td width="15%">
                            	<span class="issuetitle_L_down">身分證號：</span>
                            	<c:out value="${payTitle.idn}" />
                           </td>
		                   <td width="25%">
                            	<span class="issuetitle_L_down">姓名：</span>
                            	<c:out value="${payTitle.paymentName}" />
                           </td>
		                </tr>
		              </table>
		            </td>
		          </tr> 
		      
 				<tr align="center">
	                    <td>
	                        <bean:define id="list" name="<%=ConstantKey.PAYMENT_QUERY_LIST%>" />
	                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" class="px13">
	                            <display:column title="序&nbsp;號" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                               	    <c:out value="${listItem_rowNum}" />&nbsp;
                          		</display:column>
	                            <display:column title="列印日期" style="width:14%; text-align:center;">
	                                <c:out value="${listItem.prtDate}" />&nbsp;
	                            </display:column>
	                            <display:column title="繳款單號 " style="width:14%; text-align:center;">
	                                <c:out value="${listItem.paymentNo}" />&nbsp;
	                            </display:column>
	                            <display:column title="移送案號" style="width:13%; text-align:center;">
	                                <c:out value="${listItem.caseNo}" />&nbsp;
	                            </display:column>
	                            <display:column title="主辦案受理編號" style="width:13%; text-align:center;">
	                                <c:out value="${listItem.mApno}" />&nbsp;
	                            </display:column>
	                            <display:column title="應繳總額" style="width:13%; text-align:right;">
	                                <fmt:formatNumber value="${listItem.payTotAmt}" />&nbsp;
	                            </display:column>
	                            <display:column title="期&nbsp;數" style="width:13%; text-align:center;">
	                                <c:out value="${listItem.nowStage}" />&nbsp;
	                            </display:column>
	                            <display:column title="明&nbsp;細" style="width:10%; text-align:center;">
	                               <acl:checkButton buttonName="btnUpdate">
                                    <input type="button" class="button" name="btnQuery" value="查　詢" onclick="location.href='<c:url value="/paymentProgressQuery.do"/>?method=doQueryDtl&rowNum=<c:out value="${listItem_rowNum}" />';">
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