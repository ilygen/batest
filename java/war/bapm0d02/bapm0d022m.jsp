<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPM0D022M" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script language="javascript" type="text/javascript">
    
    function checkedFields(cb){
	 	var bClicked = false;
        var objs = document.getElementsByName("reChkCheckbox");
        idForConfirm = new Array(0);
        
        for (i = 0; i < objs.length; i++) {
            if (objs[i].checked) {
                bClicked = true;
                temp = new Array(1)
                temp[0] = objs[i].value;
                idForConfirm = idForConfirm.concat(temp);
            }
        }
        $("idForConfirmDtl").value=idForConfirm;
        
        if(bClicked == false){
            alert("請勾選要確認的資料");
            return false;           
        }else if(idForConfirm == ""){
            alert("沒有可確認的資料");
            return false;  
        }else{
            return bClicked;
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
    	<html:form action="/paymentReprint" method="post" onsubmit="return validatePaymentReprintForm(this);">
        <fieldset>
            <legend>&nbsp;繳款單補印作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
            <bean:define id="payTitle" name="<%=ConstantKey.PAYMENT_REPRINT_FORM%>" />
                <tr>
                    <td colspan="2" align="right">
                    	<html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="idForConfirmDtl" property="idForConfirmDtl" value="" />
                        <acl:checkButton buttonName="btnComp">
                            <input name="btnComp" type="button" class="button" value="補　印" onclick="$('page').value='1'; $('method').value='doCompPrint'; if (checkedFields()){document.PaymentReprintForm.submit();}else{return false;}"/>&nbsp;
                        </acl:checkButton>
                       
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返   回" onclick="history.back()" />&nbsp;
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
	                        <bean:define id="list" name="<%=ConstantKey.PAYMENT_REPRINT_LIST%>" />
	                        <display:table name="pageScope.list" id="listItem" >
	                            <display:column title="<input type='checkbox' checked name='chkBatch' onclick='selectAll(this, \"reChkCheckbox\");chooseSet();'>全選" style="width:6%; text-align:center;">
                                	<input type='checkbox' id="reChkCheckbox" name='reChkCheckbox' onclick="chooseChange(this)" checked  value='<c:out value="${listItem_rowNum-1}"/>' />
                            	</display:column>
	                            <display:column title="維護日期" style="width:15%; text-align:center;">
	                                <c:out value="${listItem.maintainDate}" />&nbsp;
	                            </display:column>
	                            <display:column title="繳款單號 " style="width:16%; text-align:center;">
	                                <c:out value="${listItem.paymentNo}" />&nbsp;
	                            </display:column>
	                            <display:column title="移送案號" style="width:16%; text-align:center;">
	                                <c:out value="${listItem.caseNo}" />&nbsp;
	                            </display:column>
	                            <display:column title="主辦案受理編號" style="width:16%; text-align:center;">
	                                <c:out value="${listItem.mApno}" />&nbsp;
	                            </display:column>
	                            <display:column title="應繳總額" style="width:16%; text-align:right;">
	                                <fmt:formatNumber value="${listItem.payTotAmt}" />&nbsp;
	                            </display:column>
	                            <display:column title="列印日期" style="width:16%; text-align:center;">
	                                <c:out value="${listItem.prtDate}" />&nbsp;
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