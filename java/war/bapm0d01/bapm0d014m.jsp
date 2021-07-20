<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPM0D014M" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
  	<script language="javascript" type="text/javascript">
    <!-- -->
   	function computeExecAmt(){
   		var obj = document.getElementsByName("execAmt"); 
   		
   		var tmpTot = 0;
   		for(j=0; j<obj.length; j++){
   			if(isNaN(obj[j].value)){
   				alert("執行費輸入錯誤");
   			}else{
   				if(obj[j].value != 0){
   					tmpTot = tmpTot + parseInt(obj[j].value.replace(/\b(0+)/gi,""));
   				}else{
   					tmpTot = tmpTot + parseInt(obj[j].value);
   				}
   				document.getElementById("totExecSpan").innerHTML=tmpTot;
	   			$("totExec").value =tmpTot;
	   			document.getElementById("totIntAmt").innerHTML = parseInt(document.getElementById("payTotAmt").innerHTML)+parseInt(document.getElementById("totInterestSpan").innerHTML)+parseInt(tmpTot);
   			}
	   	}
	   	
   	}
   	
   	function computeInterest(){
   		var obj = document.getElementsByName("payInterest"); 
   		var tmpTot = 0;
   		var checkInt;
   		document.getElementById("totInterestSpan").innerHTML = 0;
   		for(j=0; j<obj.length; j++){
	   		if(isNaN(obj[j].value)){
	   				alert("利息輸入錯誤");
	   		}else{
		   		if(obj[j].value != 0){
		   			tmpTot = tmpTot + parseInt(obj[j].value.replace(/\b(0+)/gi,""));
		   		}else{
		   			tmpTot = tmpTot + parseInt(obj[j].value);
		   		}	
	   		}
   		}
   		document.getElementById("totIntAmt").innerHTML = parseInt(document.getElementById("payTotAmt").innerHTML)+parseInt(document.getElementById("totExecSpan").innerHTML)+parseInt(tmpTot);
   		$("totInterst").value = tmpTot;
   		document.getElementById("totInterestSpan").innerHTML = tmpTot;
   		
   		
   	}
    function checkFields(){
		var msg = "";
    	var objs = document.getElementsByName("payInterest");
    	var objAmts = document.getElementsByName("execAmt");
    	var repayAmt = document.getElementsByName("rePayAmt");
    	var paymentDateLine = document.getElementsByName("paymentDateLine");
    	var payInterest = document.getElementsByName("payInterest");
    	interestList = new Array(0);
    	paymentDateLineList = new Array(0);
	    execAmtList= new Array(0);
    	for (i = 0; i < objs.length; i++) {
	    	temp = new Array(1);
	        temp[0] = objs[i].value;
	        interestList = interestList.concat(temp);
	        
	        tempDate = new Array(1);
	        tempDate[0] = paymentDateLine[i].value;
	        paymentDateLineList = paymentDateLineList.concat(tempDate);
	         
	    }
	    for(k=0; k<objAmts.length;k++){
	        tempAmts = new Array(1);
	       	tempAmts[0] = objAmts[k].value;
	       	execAmtList = execAmtList.concat(tempAmts);
	    } 
	    
	    var repayAmtTot=0;
	    var interestTot=0;
	    for(j=0; j<repayAmt.length;j++){
	    	repayAmtTot = repayAmtTot + parseInt(repayAmt[j].value);
	    }
	    for(z=0; z<payInterest.length;z++){
	    	interestTot = interestTot + parseInt(payInterest[z].value);
	    }
	    $("interstList").value = interestList;
	    $("execList").value = execAmtList;
	    $("paymentDateLineList").value = paymentDateLineList;
	    
	    if(repayAmtTot != parseInt(document.getElementById("payTotAmt").innerHTML)){
	    	msg+="本金加總後須等於應繳總本金\r\n";
	    }
	    
	     if(interestTot != parseInt(document.getElementById("totInterestSpan").innerHTML)){
	    	msg+="利息加總後須等於總利息\r\n";
	    }
	    
	    if(msg != ""){
	      alert(msg);
	      return false;
	    }else{
	      return true;
	    }    	  
    }
    
    function initAll(){
    	computeInterest();
    	computeExecAmt();
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
            <legend>&nbsp;開單確認作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
            <bean:define id="payTitle" name="<%=ConstantKey.PAYMENT_PROCESS_QUERY_FORM%>" />
                <tr>
                    <td colspan="2" align="right">
                    	<html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="totExec" property="totExec"  />
                        <html:hidden styleId="execList" property="execList"  />
                        <html:hidden styleId="paymentDateLineList" property="paymentDateLineList"  />
                    	<html:hidden styleId="totInterst" property="totInterst"/>
                    	<html:hidden styleId="interstList" property="interstList"/>
                    	
                       	<c:if test="${payTitle.isPrint eq '0'}">
                       	<acl:checkButton buttonName="btnConfirm">
                            <input name="btnConfirm" type="button" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doSavePayment'; if(checkFields()){document.PaymentProcessQueryForm.submit();}else{return false;}" " />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnPrint">
                            <input name="btnPrint" type="button" class="button" value="列　印" disabled="disabled" onclick="$('page').value='1'; $('method').value='doPrint';   if(checkFields()){document.PaymentProcessQueryForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        </c:if>
                         <c:if test="${payTitle.isPrint ne '0'}">
                         <acl:checkButton buttonName="btnConfirm">
                            <input name="btnConfirm" type="button" class="button" value="確　認" disabled="disabled" onclick="$('page').value='1'; $('method').value='doSavePayment'; if(checkFields()){document.PaymentProcessQueryForm.submit();}else{return false;}" " />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnPrint">
                            <input name="btnPrint" type="button" class="button" value="列　印"  onclick="$('page').value='1'; $('method').value='doPrint';   if(checkFields()){document.PaymentProcessQueryForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        </c:if>
                        <acl:checkButton buttonName="btnAssign">
                            <input name="btnAssign" type="button" class="button" value="分配明細" onclick="$('page').value='1'; $('method').value='doAssign'; if (checkFields()){document.PaymentProcessQueryForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button"  class="button" value="返　回" onclick="javascript:history.back();"/>&nbsp;
                        </acl:checkButton>     	                                        
                    </td>
                </tr>
                    
                <tr>
                    <td colspan="11" class="issuetitle_L">
                       <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                               <td width="25%">
	                            	<span class="issuetitle_L_down">身分證號：</span>
	                            	<c:out value="${payTitle.idn}" />
	                           </td>
			                  
  			           			<td width="25%">
                                    <span class="issuetitle_L_down">移送案號：</span>
                                    <c:out value="${payTitle.classNo}" />
  			           			</td>
  			           			<td colspan="2"/>
                            </tr>
                            <tr>
                                <td width="25%">
                                	<span class="issuetitle_L_down">應繳總額：</span>
                                    <span id="totIntAmt" >
                                   		 <c:out value="${payTitle.payTotIntAmt}" />
                                    </span>
                                    
  			           			</td>	
  			           			<td width="25%">
                                    <span class="issuetitle_L_down">應繳總本金：</span>
                                    <span id="payTotAmt" >
                                   	 <c:out value="${payTitle.payTotAmt}" />
                                     </span>
  			           			</td>
  			           			<td width="25%">
			           			 	<span class="issuetitle_L_down">總利息：</span>
			           			 	 <span id="totInterestSpan" >
                                     <c:out value="${payTitle.totInterst}" />
                                     </span>
			           			</td>
			           			<td width="25%">
                                    <span class="issuetitle_L_down">總執行費用：</span>
                                    <span id="totExecSpan" >
                                    	<c:out value="${payTitle.totExec}" /> 
                                    </span>
  			           			</td>
                            </tr>
                        </table>
                    </td>
                </tr>
 				<tr align="center">
	                    <td>
	                        <bean:define id="list" name="<%=ConstantKey.PAYMENT_ASSIGN_DETAIL_CASE_LIST%>" />
	                        <display:table name="pageScope.list" id="listItem">
	                            <display:column title="期數" style="width:5%; text-align:center;">
	                                <c:out value="${listItem.nowStage}" />&nbsp;
	                            </display:column>
	                            <display:column title="繳款單號 " style="width:25%; text-align:center;">
	                                <c:out value="${listItem.paymentNoDetail}" />&nbsp;
	                            </display:column>
	                            <display:column title="繳款期限" style="width:25%; text-align:center;">
	                            	<html:text property="paymentDateLine" style="text-align:center;" styleId="paymentDateLine" name="paymentDateLine"   value="${listItem.paymentDateLine}"  tabindex="10" styleClass="textinput" maxlength="7" size="10"  />
	                            </display:column>
	                            <c:if test="${listItem.rePayAmt eq '0'}">
	                            <display:column title="每期本金" style="width:15%; text-align:right;">
		                       		<c:out value="${listItem.rePayAmt}" />
		                        </display:column>
		                        </c:if>
		                        <c:if test="${listItem.rePayAmt ne '0'}">
	                            <display:column title="每期本金" style="width:15%; text-align:right;">
		                                <html:text property="rePayAmt" style="text-align:center;" styleId="rePayAmt" name="rePayAmt"   value="${listItem.rePayAmt}"  tabindex="10" styleClass="textinput" maxlength="7" size="10"  />
		                        </display:column>
		                        </c:if>
	                            <display:column title="利息" style="width:15%; text-align:right;">
		                                 <html:text property="payInterest" style="text-align:center;" styleId="payInterest" name="payInterest"  value="${listItem.payInterest}"  tabindex="10" styleClass="textinput" maxlength="7" size="10"  />
		                        </display:column>
		                        <c:if test="${listItem.mflag eq '1'}">
	                            <display:column title="執行費	" style="width:15%; text-align:right;">
	                            		<html:text property="execAmt" style="text-align:center;" styleId="execAmt" name="execAmt" value="${listItem.execAmt}"  onblur="computeExecAmt()" tabindex="10" styleClass="textinput" maxlength="7" size="10"  />
		                        </display:column>
		                        </c:if>
		                        <c:if test="${listItem.mflag eq '0'}">
	                            <display:column title="執行費	" style="width:15%; text-align:right;">
	                            		<html:text property="execAmt" style="text-align:center;" styleId="execAmt" name="execAmt" value="${listItem.execAmt}" readonly="true"   tabindex="10" styleClass="textinput" maxlength="7" size="10"  />
		                         </display:column>
		                        </c:if>
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