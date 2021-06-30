<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPM0D013M" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/interface/bjCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
  	<script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){
    	if($("message").innerHTML != "" && $("afterInterest").value != "Y"){
			document.getElementById("btnPrint").disabled = true;
    		document.getElementById("btnCompute").disabled = true;
    		document.getElementById("btnConfirm").disabled = true;
    		document.getElementById("btnClear").disabled = true;    
    		document.getElementById("totInterestSpan").innerHTML="";	
    		
    	}
    	
    }    
    function paymentDates(rowNum){
    	var flag = true;
    	var interestBegDate = document.getElementsByName("interestBegDate");
    	var interestEndDate = document.getElementsByName("interestEndDate");
    	
    	for(i=1;i<interestBegDate.length;i++){
       		if(document.getElementsByName("interestBegDate")[i].value.length>0){
       			flag = false;
       		}
       	}
       	for(z=0;z<interestEndDate.length;z++){
       		if(	document.getElementsByName("interestEndDate")[z].value.length>0){
       			flag = false;
       		}
       	}
       	
    	if(flag==true){
    		 paymentDate(rowNum);
    	}
    }
    function paymentDate(rowNum) {
    	var interestBegDate = document.getElementsByName("interestBegDate");
    	bjCommonAjaxDo.getPaymentDates(rowNum, interestBegDate[0].value, paymentDatesResult);
    }
    function paymentDatesResult(paymentDateResult){
    	var stringLeng = paymentDateResult.length - 7;;
    	var index = parseInt(paymentDateResult.substring(0,stringLeng));
    	    	
    	if(index <= document.getElementsByName("interestBegDate").length){
    		var date = paymentDateResult.substring(stringLeng,paymentDateResult.length);
    		if(index<document.getElementsByName("interestBegDate").length){
    			document.getElementsByName("interestBegDate")[index].value = date;
    		}
    		document.getElementsByName("interestEndDate")[index-1].value = calDay(date, -1);
    		paymentDate(index);
    	}
    	
    	
    }
    
   	function recomputeAdjInterest(){
   		var obj = document.getElementsByName("adjInterest"); 
   		var tmpTot = 0;
   		
   		for(j=0; j<obj.length; j++){
   			tmpTot = tmpTot + parseInt(obj[j].value);
   		}
   		document.getElementById("totInterestSpan").innerHTML=tmpTot;
   		$("totInterst").value =tmpTot;
   		
   	}
   	
   	function checkFieldPrint(){
   		var msg="";
   		var obj = document.getElementsByName("adjInterest"); 
    	var adjInterestTemp = new Array(0);
    	var tmpSum = 1;
    	
    	for(j=0; j<obj.length; j++){
    		tempInterest = new Array(1);
    		tempInterest[0] = obj[j].value;
	        adjInterestTemp = adjInterestTemp.concat(tempInterest);
	    }
	    $("adjInterestList").value = adjInterestTemp;
	    
    	var monthlyPay = document.getElementsByName("rePayAmt");
    	var monthlyPayTmp = new Array(0);
    	var totMonthlyPay=0; 
	  	for(j=0; j<monthlyPay.length; j++){
    		payTmp = new Array(1);
    		payTmp[0] = monthlyPay[j].value;
	        monthlyPayTmp = monthlyPayTmp.concat(payTmp);
	        totMonthlyPay = totMonthlyPay + parseInt(monthlyPay[j].value);
	    }
	    if(totMonthlyPay != $("totTrgAmt").value){
	    	msg+="期末還款金額加總須等於利息標的金額\n"
	    }
	    var interestBegDate = document.getElementsByName("interestBegDate");
    	var interestEndDate = document.getElementsByName("interestEndDate");
    	
	  	for(j=0; j<interestBegDate.length; j++){
		  	if(interestEndDate[j].value == null || interestEndDate[j].value == undefined || interestEndDate[j].value == '') { 
	     			msg+="利息結算日不能為空\n"; 
			}	
			if(interestBegDate[j].value == null || interestBegDate[j].value == undefined || interestBegDate[j].value == '') { 
	     			msg+="利息起算日不能為空\n"; 
			}
		  	if(interestBegDate[j].value>interestEndDate[j].value){
	    			msg+="利息起算日不得大於利息結算日"+interestBegDate[j].value+"-"+interestEndDate[j].value+"\n";
	    	}
	    	if(!isValidDate(interestBegDate[j].value)){
	    			msg+="日期格式錯誤"+interestBegDate[j].value+"\n";
	    	}
	    	if(!isValidDate(interestEndDate[j].value)){
	    			msg+="日期格式錯誤"+interestEndDate[j].value+"\n";
	    	}
	    }
	     
	     if(msg != ""){
	      alert(msg);
	      return false;
	    }else{
	      return true;
	    }
   	}
   	function computeInterestEndDate(){
   		var interestEndDate = document.getElementsByName("interestEndDate");
    	var interestEndDateTmp = new Array(0);
    	var interestBegDate = document.getElementsByName("interestBegDate");
    	var interestBegDateTmp = new Array(0);
    	for(j=0; j<interestEndDate.length; j++){
    		if(j+1 < interestEndDate.length){
    			document.getElementsByName("interestBegDate")[j+1].value = calDay(document.getElementsByName("interestEndDate")[j].value, 1);
    		}
	    }
   	}
    function checkFields(){
    	var msg = "";
    	var objs = document.getElementsByName("interestBegDate");
    	var monthlyPay = document.getElementsByName("rePayAmt");
    	var monthlyPayTmp = new Array(0);
    	var totMonthlyPay=0; 
    	
    	for(j=0; j<monthlyPay.length; j++){
    		payTmp = new Array(1);
    		payTmp[0] = monthlyPay[j].value;
	        monthlyPayTmp = monthlyPayTmp.concat(payTmp);
	        totMonthlyPay = totMonthlyPay + parseInt(monthlyPay[j].value);
	    }
	    if(totMonthlyPay != $("totTrgAmt").value){
	    	msg+="期末還款金額加總須等於利息標的金額\n"
	    }
	    var interestEndDate = document.getElementsByName("interestEndDate");
    	var interestEndDateTmp = new Array(0);
    	var interestBegDate = document.getElementsByName("interestBegDate");
    	var interestBegDateTmp = new Array(0);
    	for(j=0; j<interestEndDate.length; j++){
    		dateTmp = new Array(1);
    		dateTmp[0] = interestEndDate[j].value;
	        interestEndDateTmp = interestEndDateTmp.concat(dateTmp);
	        if(interestEndDate[j].value == null || interestEndDate[j].value == undefined || interestEndDate[j].value == '') { 
     			msg+="利息結算日不能為空\n"; 
			}else{
		    	if(!isValidDate(interestEndDate[j].value)){
		    			msg+="日期格式錯誤"+interestEndDate[j].value+"\n";
		    	}
			}
	        dateTmp1 = new Array(1);
    		dateTmp1[0] = interestBegDate[j].value;
	        interestBegDateTmp = interestBegDateTmp.concat(dateTmp);
	       
	        if(interestBegDate[j].value == null || interestBegDate[j].value == undefined || interestBegDate[j].value == '') { 
     			msg+="利息起算日不能為空\n"; 
			}else{
				if(!isValidDate(interestBegDate[j].value)){
		    			msg+="日期格式錯誤"+interestBegDate[j].value+"\n";
		    	}
			
			}
			
	    }
	    
	    $("monthlyPayList").value= monthlyPayTmp;
	    $("interestEndDateList").value=interestEndDateTmp;
	    $("interestBegDateList").value=interestBegDateTmp;
    	$("interestBegDates").value = objs[0].value;
    	
    	var interestBegDate = document.getElementsByName("interestBegDate");
    	var interestEndDate = document.getElementsByName("interestEndDate");
    	
	  	for(j=0; j<interestBegDate.length; j++){
    		if(interestBegDate[j].value>interestEndDate[j].value){
    			msg+= "利息起算日不得大於利息結算日"+interestBegDate[j].value+"-"+interestEndDate[j].value+"\n";
    		}
    		
	    }
    	
    	
	    if(msg != ""){
	      alert(msg);
	      return false;
	    }else{
	      $("modifyString").value = "R";//重算
	      return true;
	    }
    }
    function changeCompute(){
    	var monthlyPay = document.getElementsByName("rePayAmt");
    	var tmpAmt = 0;
    	var tmpTrgAmt=parseInt($("totTrgAmt").value);
    	$("0a").innerHTML = tmpTrgAmt;
    	for(j=0; j<monthlyPay.length; j++){
    		if(parseInt(monthlyPay[j].value)>=parseInt($(j.toString()+"a").innerHTML)){
    			monthlyPay[j].value = $(j.toString()+"a").innerHTML;
    		}
    		if(parseInt(monthlyPay[j].value)==0){
    			monthlyPay[j].value = $(j.toString()+"a").innerHTML;
    		}
    		
    		$(j.toString()).innerHTML = parseInt($(j.toString()+"a").innerHTML) - parseInt(monthlyPay[j].value);
    		if((j+1)<monthlyPay.length){
    			$((j+1).toString()+"a").innerHTML = $(j.toString()).innerHTML;
    			tmpAmt = parseInt(tmpAmt) + parseInt(monthlyPay[j].value);
    		}else{
    			 $(j.toString()).innerHTML = '0';
    			 monthlyPay[monthlyPay.length-1].value = parseInt(tmpTrgAmt) - parseInt(tmpAmt);
    		}
    		
    		
    		
	    }
	   
	   
    }
    function stopButton(){
    	$("message").innerHTML = "<font color='red'>請執行重算以取得正確的利息資料</font>";
    	document.getElementById("btnCompute").disabled = false;
   		document.getElementById("btnClear").disabled = false;
		document.getElementById("btnPrint").disabled = true;
   		document.getElementById("btnConfirm").disabled = true;
   		
    }
    
  	function deleteInterest(){
  		alert("")
  	}
    function resetAdj(){
        var adjInterest = document.getElementsByName("adjInterest");
    	
    	for(i=0; i<adjInterest.length; i++){
    		adjInterest[i].value = '0';
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
            <legend>&nbsp;利息計算作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
            <bean:define id="payTitle" name="<%=ConstantKey.PAYMENT_PROCESS_QUERY_FORM%>" />
                <tr>
                    <td colspan="2" align="right">
                    	<html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="interestBegDates" property="interestBegDates"/>
                        <html:hidden styleId="adjInterestList" property="adjInterestList"/>
                        <html:hidden styleId="totInterst" property="totInterst"/>
                        <html:hidden styleId="monthlyPayList" property="monthlyPayList"/>
                        <html:hidden styleId="interestEndDateList" property="interestEndDateList"/>
                        <html:hidden styleId="interestBegDateList" property="interestBegDateList"/>
                        <html:hidden styleId="modifyString" property="modifyString"/>
                        <html:hidden styleId="afterInterest" property="afterInterest"/>
                        <html:hidden styleId="insertString" property="insertString" value=""/>
                        <html:hidden styleId="totTrgAmt" property="totTrgAmt" value="${payTitle.totTrgAmt}"/>
                    	<acl:checkButton buttonName="btnConfirm">
                            <input name="btnConfirm" type="button" class="button" id="btnConfirm" value="確　認" onclick="$('page').value='1';$('insertString').value='';  $('method').value='doConfirm';if(checkFieldPrint()){document.PaymentProcessQueryForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnPrint">
                            <input name="btnPrint" type="button" class="button_120" id="btnPrint" value="列印試算結果" onclick="$('page').value='1'; $('insertString').value=''; $('method').value='doPrintInterest';   if(checkFieldPrint()){document.PaymentProcessQueryForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                         <acl:checkButton buttonName="btnPrint">
                            <input name="btnDelete" type="button" class="button" id="btnDelete" value="刪　除"onclick="if(confirm('是否確定刪除?')){location.href='<c:url value="/paymentProcessList.do"/>?method=doDeleteInterest'};">&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" id="btnBack" value="返　回" onclick="$('page').value='1'; $('method').value='doBackSession';  document.PaymentProcessQueryForm.submit();" />&nbsp;
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
	                            	<c:out value="${payTitle.insertString}" />
	                           </td>
			                   <td width="25%">
	                            	<span class="issuetitle_L_down">利息試算期數：</span>
	                            	<c:out value="${payTitle.interestTryStage}" />
	                           </td>
	                           <td colspan="2"/>
                            </tr>
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">繳款單號：</span>
                                    <c:out value="${payTitle.paymentNo}" />
  			           			</td>
  			           			<td width="25%">
                                    <span class="issuetitle_L_down">移送案號：</span>
                                    <c:out value="${payTitle.classNo}" />
  			           			</td>
  			           			 <td colspan="2"/>
                            </tr>
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">繳款期限：</span>
                                    <c:out value="${payTitle.paymentDateLine}" />
  			           			</td>	
  			           			<td width="25%">
                                    <span class="issuetitle_L_down">試算本利和：</span>
                                     <fmt:formatNumber value="${payTitle.tryInterestAmt}" />
  			           			</td>
  			           			<td width="25%">
			           			 	<span class="issuetitle_L_down">利息標的金額：</span>
			           			 	<span id="totTrgAmt">
                                      <fmt:formatNumber value="${payTitle.totTrgAmt}" />
                                      </span>
			           			</td>
			           			
			           			<c:if test="${payTitle.insertString eq 'I'}">
			           			<td width="25%">
                                    <span class="issuetitle_L_down">總利息：</span>
                                    <span id="totInterestSpan" >
                                    	 <c:out value="_" /> 
                                    </span>
  			           			</td>
  			           			</c:if>
  			           			<c:if test="${payTitle.insertString ne 'I'}">
			           			<td width="25%">
                                    <span class="issuetitle_L_down">總利息：</span>
                                    <span id="totInterestSpan" >
                                    	 <fmt:formatNumber value="${payTitle.totInterst}" /> 
                                    </span>
  			           			</td>
  			           			</c:if>
                            </tr>
                            
                        </table>
                    </td>
                </tr>
             	<tr>
                    <td colspan="11">
                       <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                           
                           <tr>
                           		<td width="80%" />
                           		<td align="right">
									<acl:checkButton buttonName="btnCompute">
							               	<input name="btnCompute" type="button" class="button" id="btnCompute" value="重　算" onclick="$('page').value='1'; $('method').value='doCompute';if(checkFields()){document.PaymentProcessQueryForm.submit();}else{return false;}" />&nbsp;
									</acl:checkButton>
									<acl:checkButton buttonName="btnRest">
										    <input name="btnClear" type="button" class="button" id="btnClear" value="清  除 " onclick="resetAdj()">
									</acl:checkButton>  
								</td>
			             	</tr>
                            
                        </table>
                    </td>
                </tr>
 						<tr>
 							<td>
	                        <bean:define id="list" name="<%=ConstantKey.PAYMENT_INTEREST_DETAIL_CASE_LIST%>" />
	                      		    
	                       	    <display:table name="pageScope.list" id="listItem">
	                            <display:column title="期數" style="width:5%; text-align:center;">
	                                <c:out value="${listItem.nowStage}" />&nbsp;
	                            </display:column>
	                            <display:column title="期初本金餘額" style="width:10%; text-align:right;">
	                            	<span id="<c:out value="${listItem_rowNum-1}a"/>">
	                                 <fmt:formatNumber value="${listItem.accAmt}" />
	                                 </span>
	                            </display:column>
	                            <display:column title="期末還款金額 " style="width:10%; text-align:right;">
	                           		 <html:text property="rePayAmt" style="text-align:center;" styleId="rePayAmt" name="rePayAmt" onchange="stopButton()" onblur="changeCompute()" value="${listItem.rePayAmt}"  tabindex="10" styleClass="textinput" maxlength="7" size="10"  />
	                            </display:column>
	                            <display:column title="期末本金餘額" style="width:10%; text-align:right;">
	                            	<span id="<c:out value="${listItem_rowNum-1}"/>">
	                           		  <fmt:formatNumber value="${listItem.accAmtEnd}" />
	                           		</span>
	                            </display:column>
	                            <c:if test="${ payTitle.insertString eq 'I'}">
	                            	<display:column title="利率" style="width:10%; text-align:center;">
			                                <c:out value=" _"/>
			                         </display:column>
		                            <display:column title="利息起算日" style="width:12%; text-align:center;">
		                                 <html:text property="interestBegDate" style="text-align:center;" styleId="interestBegDate"  name="interestBegDate"  value="" onchange="stopButton()" onblur="paymentDates('${listItem_rowNum-1}')" tabindex="10" styleClass="textinput" maxlength="7" size="10"  />
		                            </display:column>
		                            <display:column title="利息結算日" style="width:13%; text-align:center;">
	                                	<html:text property="interestEndDate" style="text-align:center;" styleId="interestEndDate" name="interestEndDate" value="" onblur="computeInterestEndDate()"  onchange="stopButton()"  tabindex="10" styleClass="textinput" maxlength="7" size="10"  />
	                            	</display:column>
	                            </c:if>
	                            <c:if test="${payTitle.insertString ne 'I'}">
		                            <c:if test="${listItem.iRate ne '0'}">
			                            <display:column title="利率" style="width:10%; text-align:center;">
			                                <c:out value="${listItem.iRate}" />
			                            </display:column>
		                            </c:if>
		                            <c:if test="${listItem.iRate eq '0'}">
			                            <display:column title="利率" style="width:10%; text-align:center;">
			                                <c:out value=" _"/>
			                            </display:column>
		                            </c:if>
		                            <display:column title="利息起算日" style="width:12%; text-align:center;">
		                                 <html:text property="interestBegDate" style="text-align:center;" styleId="interestBegDate" name="interestBegDate"  value="${listItem.interestBegDate}" onchange="stopButton()" onblur="paymentDates('${listItem_rowNum-1}')"  tabindex="10" styleClass="textinput" maxlength="7" size="10"  />
		                            </display:column>
		                            <display:column title="利息結算日" style="width:13%; text-align:center;">
	                                	<html:text property="interestEndDate" style="text-align:center;" styleId="interestEndDate" name="interestEndDate" onblur="computeInterestEndDate()" value="${listItem.interestEndDate}" onchange="stopButton()" tabindex="10" styleClass="textinput" maxlength="7" size="10"  />
	                            	</display:column>	
	                            </c:if>
	                           
	                            
	                             <c:if test="${payTitle.insertString ne 'I'}">
	                            	 <display:column title="天數" style="width:5%; text-align:center;">
		                                 <c:out  value="${listItem.dateBetween}" />
		                            </display:column>
		                             <display:column title="試算利息" style="width:12%; text-align:right;">
		                                  &nbsp; <fmt:formatNumber value="${listItem.tryInterest}" /> 
		                            </display:column>
		                             <display:column title="調整利息" style="width:13%; text-align:right;">
		                                 <html:text property="adjInterest" style="text-align:right;" styleId="adjInterest" name="adjInterest"  onblur="recomputeAdjInterest()"  value="${listItem.adjInterest}"  	tabindex="10" styleClass="textinput" maxlength="9" size="10"  />
		                            </display:column>
	                            </c:if>
	                            <c:if test="${ payTitle.insertString eq 'I'}">
		                            <display:column title="天數" style="width:5%; text-align:center;">
		                                 <c:out  value="_" />
		                            </display:column>
		                             <display:column title="試算利息" style="width:12%; text-align:right;">
		                                   <c:out  value="_" />
		                            </display:column>
		                             <display:column title="調整利息" style="width:13%; text-align:right;">
		                                 <c:out  value="_" />
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