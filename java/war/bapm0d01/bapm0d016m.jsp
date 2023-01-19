<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPM0D016M" />
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
    	var writeOffSeq = document.getElementsByName("writeoffSeqNo");
    	var obj = document.getElementsByName("reChkCheckbox");
    	var chkObj = document.getElementsByName("chkObj");
    	for(j=0; j<obj.length; j++){
    		if(writeOffSeq[j].value.empty()){
    			obj[j].checked = false;
    		}
    	}
    	if($("totTrgAmt").value.empty()){chooseSet();}

    }
    function checkFields(){
        var msg="";
        if(msg!=""){
            return false;
        }else{
            return true;
        }
    }
    function chooseSet(cb){
    	 var obj = document.getElementsByName("reChkCheckbox");
    	 var writeOffSeq = document.getElementsByName("writeoffSeqNo");
    	 var tmpSum = 1;
    	 for(j=0; j<obj.length; j++){
    	 	if(obj[j].checked){
          		writeOffSeq[j].value = tmpSum;
          		tmpSum = tmpSum + 1;
          	}else{
          		writeOffSeq[j].value = "";
          	}

          }

    }

    function chooseChange(cb){
		  var obj = document.getElementsByName("reChkCheckbox");
		  var writeOffSeq = document.getElementsByName("writeoffSeqNo");
		  var payAmt = document.getElementsByName("payAmt");

		  var tmpCount = 0;
		  var tmpSum = 1;
		  var totAmtSum = 0;
		  for (i=0; i<obj.length; i++){
       	 	//判斷obj集合中的i元素是否為cb，若否則表示未被點選
       	 	if (obj[i]== cb){
       	  		if(writeOffSeq[i].value.empty()){
       	  			tmpCount = tmpCount + 1;
       	  			writeOffSeq[i].value = tmpCount;
       	  		}else{
    	  			writeOffSeq[i].value = "";
       	  		}
          	}
          }
          for(j=0;j<obj.length; j++){
          	if(!writeOffSeq[j].value.empty()){
          		writeOffSeq[j].value = tmpSum;
          		totAmtSum = totAmtSum + parseInt(payAmt[j].value)
          		tmpSum = tmpSum + 1;
          	}
          }
          $("payTotAmt").value = totAmtSum;
          $("stageAmt").value = Math.round($("payTotAmt").value / $("totAmtStage").value);
	}
	function computMonthlyAmt(){

		$("stageAmt").value = Math.round($("payTotAmt").value / $("totAmtStage").value);
	}
	function changeClass(){
		if(!$("classNo").value.empty()){
			$("caseType").value = "傳繳案件";
		}else{
			$("caseType").value = "一般案件";
		}
	}
	function changePaymentDate(){
		var sDate = '<%=DateUtility.getNowChineseDate()%>';
		if($("chkObj").value=="2"){
			$("paymentDateLine").value = calDay(sDate, 30);
		}else if($("chkObj").value=="1"){
			$("paymentDateLine").value ="";
		}else if($("chkObj").value=="3"){
            $("paymentDateLine").value ="";
        }

	}
	function changePayTotAmt(cb){
		var payAmtChange = 0;
		var payAmt =  document.getElementsByName("payAmt");
		var obj = document.getElementsByName("reChkCheckbox");
		var writeOffSeq = document.getElementsByName("writeoffSeqNo");

		for(j=0;j<obj.length; j++){
			if(!writeOffSeq[j].value.empty()){
          		payAmtChange = payAmtChange + parseInt(payAmt[j].value);
          	}
        }
        $("payTotAmt").value = payAmtChange;
        $("stageAmt").value = Math.round($("payTotAmt").value / $("totAmtStage").value);
    }
    function computeMonthPayAmt(){
    	if(!$("totTrgAmt").value.empty() && !$("interestTryStage").value.empty()){
    		$("monthlyPayAmt").value = Math.round( parseInt($("totTrgAmt").value) / parseInt($("interestTryStage").value))
    	}
    	if(parseInt($("totTrgAmt").value)>parseInt($("payTotAmt").value)){
    		alert("輸入金額錯誤，必須小於應繳總本金");
    	}

    }
    function checkFieldNext(){
    		var payAmt =  document.getElementsByName("payAmt");
    		var obj = document.getElementsByName("reChkCheckbox");
			var writeOffSeq = document.getElementsByName("writeoffSeqNo");
			var mapnoMk = document.getElementsByName("mapnoMk");
    		//確認有無勾選的資料
      	    var bClicked = false;
	        var objs = document.getElementsByName("reChkCheckbox");
	        var objSeqNo = document.getElementsByName("writeoffSeqNo");
    		var objApnoMk = document.getElementsByName("mapnoMk");
    		var objPayAmt = document.getElementsByName("payAmt");
	        idForConfirm = new Array(0);
	        writeForConfirm= new Array(0);
	        mapnoForConfirm = new Array(0);
	        payAmtForConfirm = new Array(0);
    		var msg = "";
    	 	/*//利息標的金額、利息計算期數、利息每期本金其中一個有值，三個欄位皆需有值
    	 	if($("totTrgAmt").value.empty()){
    	 		msg+="請輸入「利息標的金額」\r\n";
    	 	}
    	 	if($("interestTryStage").value.empty()){
    	 		msg+="請輸入「利息試算期數」\r\n";
    	 	}
    	 	if($("monthlyPayAmt").value.empty()){
    	 		msg+="請輸入「利息每期本金」\r\n";
    	 	}*/
	        if(isNaN($("stageAmt").value)){
	        	msg+="「每期攤還本金」格式錯誤\r\n";
	        }
	        if(isNaN($("totAmtStage").value)){
	        	msg+="「本金期數」格式錯誤\r\n";
	        }
	        if(isNaN($("interestStage").value)){
	        	msg+="「利息期數」格式錯誤\r\n";
	        }
	        if(isNaN($("totTrgAmt").value)){
	        	msg+="「利息標的金額」格式錯誤\r\n";
	        }
	        if(isNaN($("interestTryStage").value)){
	        	msg+="「利息試算期數 」格式錯誤\r\n";
	        }
	        if(isNaN($("monthlyPayAmt").value)){
	        	msg+="「利息每期本金」格式錯誤\r\n";
	        }
	        if($("chkObj").value != "1" && $("chkObj").value != "2" && $("chkObj").value != "3" && $("paymentDateLine").value.empty()){
	        	msg+="請選擇「繳款期限」方式\r\n";
	        }
	       if($("zipCode").value.empty()||$("addr").value.empty()){
      	    	msg+="郵遞區號及地址欄位不得為空。"
      	    }
			var flag = 0;
			for(j=0;j<obj.length; j++){
				if(!writeOffSeq[j].value.empty()){
          			if(payAmt[j].value.empty()){
          				flag=1;
          			}
          		}else{
          			if(mapnoMk[j].checked){
						flag=2;
          			}
          		}
      	    }
      	    if(flag == 1){
      	    	msg+="請輸入勾選之「應繳本金」\r\n";
      	    }
      	    if(flag == 2){
      	    	msg+="主辦案必須為勾選之案件編號\r\n"
      	    }
      	    if($("totAmtStage").value.empty()||$("interestStage").value.empty()||$("stageAmt").value.empty()){
      	    	msg+="請輸入必填欄位\r\n";
      	    }

	        //抓取checkbox所選的值
	        for (i = 0; i < objs.length; i++) {
	            if (objs[i].checked) {
	                bClicked = true;
	                temp = new Array(1);
	                temp[0] = objs[i].value;
	                idForConfirm = idForConfirm.concat(temp);

	                tempSeqNo = new Array(1);
	                tempSeqNo[0] = objSeqNo[i].value;
	                writeForConfirm = writeForConfirm.concat(tempSeqNo);

	                if(objApnoMk[i].checked){
	                	tempApnoMk = new Array(1);
	                	tempApnoMk[0] = objApnoMk[i].value;
	                	mapnoForConfirm = mapnoForConfirm.concat(tempApnoMk);

	                }
	                tempPayAmt = new Array(1);
	                tempPayAmt[0] = objPayAmt[i].value;
	                payAmtForConfirm = payAmtForConfirm.concat(tempPayAmt);
	            }
	        }
	        $("idForConfirm").value=idForConfirm;
	        $("seqNoForConfirm").value=writeForConfirm;
	        $("mkForConfirm").value=mapnoForConfirm;
	        $("payAmtForConfirm").value=payAmtForConfirm;
	        if(bClicked == false){
	            msg+="請勾選要確認的資料";

	        }else if(idForConfirm == ""){
	            msg+="沒有可確認的資料";

	        }
	        var max = 0;
   			var min = 0;
      	    for(i=0; i<writeOffSeq.length; i++){
      	    	if((writeOffSeq[i].value != '' && objs[i].checked)){
      	    		if(i==0){
      	    			max=writeOffSeq[i].value;
      	    			min=writeOffSeq[i].value;
      	    		}
      	    		if(writeOffSeq[i].value>max){
      	    			max = writeOffSeq[i].value;
      	    		}
      	    		if(writeOffSeq[i].value<min){
      	    			min = writeOffSeq[i].value;
      	    		}
      	    	}
      	    }
      	    var flag = true;
      	    for(i=min; i<=max; i++){
					if(flag!=false && flagDouble!=false){
 						var flagDouble = false;
 						for(j=0;j<writeOffSeq.length;j++){
 							if(writeOffSeq[j].value !== null || writeOffSeq[j].value !== undefined || writeOffSeq[j].value !== ''){

								if(writeOffSeq[j].value==i){
				      	    		flag = true;
					      	    	if(flagDouble==true){
					      	    		flag = false;
					      	    		flagDouble = false;
					      	    		j = writeOffSeq.length;
					      	    	}else{
					      	    		flagDouble = true;
					      	    	}
					      	    }else{
					      	   		if(flagDouble!=true){
					      	   			flag = false;
					      	   		}
					      	    }
				      	    }
	      	    		}
 					}
      	    }
      	    if(flag==false){
      	    	msg+="銷帳序有誤，請確認銷帳序。"
      	    }
	        if(msg != ""){
	            alert(msg);
	            return false;
	        }else{
	            return true;
	        }
    }
    function checkFieldCompute(){
    		var payAmt =  document.getElementsByName("payAmt");
    		var obj = document.getElementsByName("reChkCheckbox");
			var writeOffSeq = document.getElementsByName("writeoffSeqNo");
			var mapnoMk = document.getElementsByName("mapnoMk");
    		//確認有無勾選的資料
      	    var bClicked = false;
	        var objs = document.getElementsByName("reChkCheckbox");
	        var objSeqNo = document.getElementsByName("writeoffSeqNo");
    		var objApnoMk = document.getElementsByName("mapnoMk");
    		var objPayAmt = document.getElementsByName("payAmt");
	        idForConfirm = new Array(0);
	        writeForConfirm= new Array(0);
	        mapnoForConfirm = new Array(0);
	        payAmtForConfirm = new Array(0);
    		var msg = "";
    	 	//利息標的金額、利息計算期數、利息每期本金其中一個有值，三個欄位皆需有值
    	 	if($("totTrgAmt").value.empty()){
    	 		msg+="請輸入「利息標的金額」\r\n";
    	 	}
    	 	if($("interestTryStage").value.empty()){
    	 		msg+="請輸入「利息試算期數」\r\n";
    	 	}
    	 	if($("monthlyPayAmt").value.empty()){
    	 		msg+="請輸入「利息每期本金」\r\n";
    	 	}
	        if(isNaN($("stageAmt").value)){
	        	msg+="「每期攤還本金」格式錯誤\r\n";
	        }
	        if(isNaN($("totAmtStage").value)){
	        	msg+="「本金期數」格式錯誤\r\n";
	        }
	        if(isNaN($("interestStage").value)){
	        	msg+="「利息期數」格式錯誤\r\n";
	        }
	        if(isNaN($("totTrgAmt").value)){
	        	msg+="「利息標的金額」格式錯誤\r\n";
	        }
	        if(isNaN($("interestTryStage").value)){
	        	msg+="「利息試算期數 」格式錯誤\r\n";
	        }
	        if(isNaN($("monthlyPayAmt").value)){
	        	msg+="「利息每期本金」格式錯誤\r\n";
	        }
	        if($("chkObj").value != "1" && $("chkObj").value != "2" && $("chkObj").value != "3" && $("paymentDateLine").value.empty()){
	        	msg+="請選擇「繳款期限」方式\r\n";
	        }

			var flag = 0;
			for(j=0;j<obj.length; j++){
				if(!writeOffSeq[j].value.empty()){
          			if(payAmt[j].value.empty()){
          				flag=1;
          			}
          		}else{
          			if(mapnoMk[j].checked){
						flag=2;
          			}
          		}
      	    }
      	    if(flag == 1){
      	    	msg+="請輸入勾選之「應繳本金」\r\n";
      	    }
      	    if(flag == 2){
      	    	msg+="主辦案必須為勾選之案件編號\r\n"
      	    }
      	    if($("totAmtStage").value.empty()||$("interestStage").value.empty()||$("stageAmt").value.empty()){
      	    	msg+="請輸入必填欄位\r\n";
      	    }

	        //抓取checkbox所選的值
	        for (i = 0; i < objs.length; i++) {
	            if (objs[i].checked) {
	                bClicked = true;
	                temp = new Array(1);
	                temp[0] = objs[i].value;
	                idForConfirm = idForConfirm.concat(temp);

	                tempSeqNo = new Array(1);
	                tempSeqNo[0] = objSeqNo[i].value;
	                writeForConfirm = writeForConfirm.concat(tempSeqNo);

	                if(objApnoMk[i].checked){
	                	tempApnoMk = new Array(1);
	                	tempApnoMk[0] = objApnoMk[i].value;
	                	mapnoForConfirm = mapnoForConfirm.concat(tempApnoMk);

	                }
	                tempPayAmt = new Array(1);
	                tempPayAmt[0] = objPayAmt[i].value;
	                payAmtForConfirm = payAmtForConfirm.concat(tempPayAmt);
	            }
	        }
	        $("idForConfirm").value=idForConfirm;
	        $("seqNoForConfirm").value=writeForConfirm;
	        $("mkForConfirm").value=mapnoForConfirm;
	        $("payAmtForConfirm").value=payAmtForConfirm;
	        if(bClicked == false){
	            msg+="請勾選要確認的資料";

	        }else if(idForConfirm == ""){
	            msg+="沒有可確認的資料";

	        }

	        if(msg != ""){
	            alert(msg);
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
            <legend>&nbsp;開單明細維護作業&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
            <bean:define id="payTitle" name="<%=ConstantKey.PAYMENT_PROCESS_QUERY_FORM%>" />
                <tr>
                    <td colspan="2" align="right">
                    	<html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="paymentNo" property="paymentNo"  value="${payTitle.paymentNo}"/>
                        <html:hidden styleId="idForConfirm" property="idForConfirm"/>
                        <html:hidden styleId="mkForConfirm" property="mkForConfirm"/>
                        <html:hidden styleId="seqNoForConfirm" property="seqNoForConfirm"/>
                        <html:hidden styleId="payAmtForConfirm" property="payAmtForConfirm"/>
                       <acl:checkButton buttonName="btnConfirm">
                            <input name="btnConfirm" type="button" class="button" value="確　認"  onclick="$('page').value='1'; $('method').value='doUpdateBasic'; if (checkFieldNext()){document.PaymentProcessQueryForm.submit();}else{return false;}"/>&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnCompute">
                            <input name="btnCompute" type="button" class="button" value="利息計算" disabled="disabled" onclick="$('page').value='1'; $('method').value='doCompute';  if (checkFieldCompute()){document.PaymentProcessQueryForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <c:if test="${empty payTitle.prtDate}">
                        <acl:checkButton buttonName="btnDelete">
                            <input name="btnDelete" type="button" class="button" value="刪　除" onclick="$('page').value='1'; $('method').value='doDelete'; document.PaymentProcessQueryForm.submit();" />&nbsp;
                        </acl:checkButton>
                        </c:if>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="javascript:history.back();" />&nbsp;
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
                            	<span class="issuetitle_L_down">繳款單號：</span>
                            	<c:out value="${payTitle.paymentNo}" />
                           </td>
		                </tr>
		              </table>
		            </td>
		          </tr>
                <tr>
                    <td colspan="11" class="issuetitle_L">
                       <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="15%">
                                    <span class="issuetitle_L_down">類別：</span>
                                     <html:text  property="caseType" styleId="caseType" name="caseType" disabled="true" value="${payTitle.caseType}"   tabindex="10" styleClass="textinput" maxlength="6" size="6"  />
                                 </td>

                                <td width="25%">
                                	 <span class="issuetitle_L_down">繳款期限：</span>
                        			<html:text  property="paymentDateLine" styleId="paymentDateLine" disabled="true" value="${payTitle.paymentDateLine}" tabindex="10" styleClass="textinput" size="15" maxlength="10"  />
			           				 <html:select styleId="chkObj"  property="chkObj" value="${payTitle.chkObj}" onchange="changePaymentDate()" disabled="true">
			                            <html:option value="">請選擇</html:option>
			                            <html:option value="1">文到之翌日起30日內</html:option>
			                            <html:option value="2">系統日+30日</html:option>
                                         <html:option value="3">文到之翌日起15日內</html:option>
			                         </html:select>
			           			</td>

                                <td width="35%">
                                    <span class="issuetitle_L_down">應繳總本金：</span>
                                    <html:text  property="payTotAmt" styleId="payTotAmt" name="payTotAmt" disabled="true" value="${payTitle.payTotAmt}"  tabindex="10" styleClass="textinput" maxlength="9" size="10"  />
                        	    </td>

                            </tr>
                            <tr>
                                <td colspan="3">
                                    <span class="issuetitle_L_down">移送案號：</span>
                                    <html:text property="classNo" styleId="classNo" onblur="changeClass()" tabindex="10" styleClass="textinput" maxlength="25" size="25"  />
			           			</td>
                            </tr>
                            <tr>
                                <td colspan="3">
                                	<span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">地址：</span>
                                    <html:text property="zipCode" styleId="zipCode"  tabindex="10" styleClass="textinput" maxlength="6" size="6"  />
			           				<html:text property="addr" styleId="addr"  tabindex="10" styleClass="textinput" maxlength="100" size="100"  />
			           			</td>
                            </tr>
                            <tr>
                                <td width="15%">
                                	<span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">本金期數：</span>
                                    <html:text  property="totAmtStage"  name="totAmtStage" styleId="totAmtStage" disabled="true" onblur="computMonthlyAmt()" value="${payTitle.totAmtStage}" tabindex="10" styleClass="textinput" maxlength="3" size="5"  />
			           			</td>

                                <td width="25%">
                               		 <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">利息期數：</span>
                                    <html:text  property="interestStage" styleId="interestStage"  disabled="true" value="${payTitle.interestStage}" tabindex="10" styleClass="textinput" maxlength="3" size="5"  />

			           			</td>
			           			 <td width="35%">
			           			 	<span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">每期攤還本金：</span>
                                    <html:text  property="stageAmt" name="stageAmt" styleId="stageAmt" disabled="true" value="${payTitle.stageAmt }"  tabindex="10"  styleClass="textinput" maxlength="9" size="10"  />
			           			</td>
                            </tr>
                            <tr>
                                <td width="15%">
                                	<span class="issuetitle_L_down">利息標的金額：</span>
                                    <html:text  property="totTrgAmt" styleId="totTrgAmt" disabled="true" onblur="computeMonthPayAmt()" tabindex="10" styleClass="textinput" maxlength="9" size="10"  />
			           			</td>

                                <td width="25%">
                               		 <span class="issuetitle_L_down">利息試算期數：</span>
                                    <html:text  property="interestTryStage" styleId="interestTryStage" disabled="true" onblur="computeMonthPayAmt()"  tabindex="10" styleClass="textinput" maxlength="3" size="5"  />

			           			</td>
			           			 <td width="35%">
			           			 	<span class="issuetitle_L_down">利息每期本金：</span>
                                    <html:text property="monthlyPayAmt" styleId="monthlyPayAmt" disabled="true" tabindex="10" styleClass="textinput" maxlength="9" size="10"  />
			           			</td>
                            </tr>
                        </table>
                    </td>
                </tr>
 				<tr align="center">
	                    <td>
	                        <bean:define id="list" name="<%=ConstantKey.PAYMENT_PROCESS_DETAIL_CASE_LIST%>" />
	                        <display:table name="pageScope.list" id="listItem" >
	                            <display:column title="<input type='checkbox' checked name='chkBatch' disabled  onclick='selectAll(this, \"reChkCheckbox\");chooseSet();'>全選" style="width:6%; text-align:center;">
                                	<input type='checkbox' disabled="disabled" id="reChkCheckbox" name='reChkCheckbox' onclick="chooseChange(this)" checked  value='<c:out value="${listItem_rowNum-1}"/>' />
                            	</display:column>
	                            <display:column title="受理編號" style="width:12%; text-align:left;">
	                                <c:out value="${listItem.apno}" />&nbsp;
	                            </display:column>
	                            <display:column title="受款人序 " style="width:12%; text-align:left;">
	                                <c:out value="${listItem.seqNo}" />&nbsp;
	                            </display:column>
	                            <display:column title="姓名" style="width:10%; text-align:center;">
	                                <c:out value="${listItem.benName}" />
	                            </display:column>
	                            <display:column title="核定年月" style="width:8%; text-align:right;">
	                                <c:out value="${listItem.issuYm}" />
	                            </display:column>
	                            <display:column title="給付年月" style="width:8%; text-align:right;">
	                                <c:out value="${listItem.payYm}" />
	                            </display:column>
	                            <display:column title="應收金額" style="width:8%; text-align:right;">
	                                <fmt:formatNumber value="${listItem.recAmt}" />
	                            </display:column>
	                             <display:column title="未收餘額" style="width:10%; text-align:right;">
	                                 <fmt:formatNumber value="${listItem.recRem}" />
	                            </display:column>
	                             <display:column title="應繳本金" style="width:10%; text-align:right;">
	                                 <html:text property="payAmt" styleId="payAmt" name ="payAmt" disabled="true" value="${listItem.payAmt}" onblur="changePayTotAmt(this)" style="text-align:right;"  tabindex="10" styleClass="textinput" maxlength="9" size="10"  />
	                            </display:column>
	                            <c:if test="${listItem.mapnoMk eq '0'}">
		                            <display:column title="主辦案" style="width:12%; text-align:center;">
		                                <input type="radio" name="mapnoMk" value='<%=(listItem_rowNum.intValue())%>' id="mapnoMk"/>
		                            </display:column>
	                            </c:if>
	                             <c:if test="${listItem.mapnoMk eq '1'}">
		                            <display:column title="主辦案" style="width:12%; text-align:center;">
		                                 <input type="radio" id="mapnoMk" value='<%=(listItem_rowNum.intValue())%>' name="mapnoMk" checked />
		                            </display:column>
	                            </c:if>
	                            <display:column title="銷帳序" style="width:4%; text-align:center;">
	                            	 <html:text property="writeoffSeqNo" style="text-align:center;" styleId="writeoffSeqNo" name="writeoffSeqNo"  value="${listItem.writeoffSeqNo}"  tabindex="10" styleClass="textinput" maxlength="9" size="10"  />
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
