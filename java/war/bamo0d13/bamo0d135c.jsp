<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D135C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="DisabledApplicationDataUpdateForm" page="4" />
    <script language="javascript" type="text/javascript">
    <!--
    var today = "<%=DateUtility.getNowChineseDate()%>";
    
    <%-- 國籍別 變更時的處理 --%>
    function doEvtNationTpeChange() {
        if (getRadioValue("evtNationTpe") == "1") {
            $("divSex").style.display = "none";
            $("divNation").style.display = "none";
            
            $("tmpEvtNationCode").value = "000";
            $("evtNationCode").value = "000";
            
            if (Trim($F("evtIdnNo")).length > 2) {
                var sex = $F("evtIdnNo").substring(1, 2);
                if (sex == "1" || sex == "2")
                    setRadioValue("evtSex", sex);
            }
        }
        else {
            $("divSex").style.display = "inline";
            $("divNation").style.display = "inline";
        }
    }
    
    function doEvtNationTpeAfterChange() {
        if (getRadioValue("evtNationTpe") == "2") {
            var objs = document.getElementsByName("evtSex");
            for (i = 0; i < objs.length; i++) {
                objs[i].checked = false;
            }

            $("evtSex").focus();
            $("evtNationCode").style.background = inputBlurBackgroundColor;

            $("tmpEvtNationCode").value = "";
            $("evtNationCode").value = "";

            $("divSex").style.display = "inline";
            $("divNation").style.display = "inline";
        }
        autoForeignEvtSex();
    }
    
    <%-- 國籍 變更時的處理 --%>
    function doEvtNationCodeChange() {
        $("tmpEvtNationCode").value = $F("evtNationCode");
        
        if ($F("evtNationCode") == "")
            $("tmpEvtNationCode").selectedIndex = 0;

        if ($F("tmpEvtNationCode") == "null" || $("tmpEvtNationCode").selectedIndex == null || $("tmpEvtNationCode").selectedIndex == -1) {
            $("tmpEvtNationCode").selectedIndex = 0;
        }
    }
    
    function doTmpEvtNationCodeChange() {
        $("evtNationCode").value = $F("tmpEvtNationCode");
        if ($F("evtNationCode") == "null") {
            $("evtNationCode").value = "";
            $("tmpEvtNationCode").selectedIndex = 0;
        }
    }
    
    <%-- 事故者身分證號 變更時 設定 性別 --%>
    function setEvtSex() {
        if (getRadioValue("evtNationTpe") == "1") {
            if (Trim($F("evtIdnNo")).length > 2) {
                var sex = $F("evtIdnNo").substring(1, 2);
                if (sex == "1" || sex == "2")
                    setRadioValue("evtSex", sex);
            }
        }
    }
    
    <%-- 取得 戶籍姓名 (AJAX) --%>
    <%-- [ --%>
    function getCvldtlName() {
        if ($("name") != null) {
            if (Trim($F("evtIdnNo")).length == 10 && Trim($F("evtBrDate")).length >= 7 && isNaN($("evtBrDate").value) == false)
                updateCommonAjaxDo.getCvldtlName(Trim($F("evtIdnNo")), Trim($F("evtBrDate")), fillCvldtlNameValue);
        }
    }
    
    // Ajax for 取得 出生日期錯誤參數 確認是否有此筆資料"P120436303", "0480229"  $("evtIdnNo").value,$("evtBrDate").value
    function checkIdnoExist() {   
        if(isNaN($("evtBrDate").value) == false){
        updateCommonAjaxDo.checkIdnoExist($("evtIdnNo").value,$("evtBrDate").value,checkIdnoExistResult);
        }
    	}
    	function checkIdnoExistResult(idnoExist) {  
    		$("idnoExist").value = idnoExist;
    	}

    //檢核事故者出生日期 20121220 邏輯修改
    function isValidEvtDateTrue() {   
        var evtBrDate = $("evtBrDate").value;

        if(isValidDate($("evtBrDate").value) == false){
        
        if($("idnoExist").value == null || $("idnoExist").value == "" || $("idnoExist").value == "null"){
        alert("輸入之「事故者出生日期」錯誤，請重新輸入");
        return false;
        }else{
          return true;
        }
        }else{
          return true;
        }
    }

    function fillCvldtlNameValue(cvldtlName) {
        $("name").value = cvldtlName;
    }
    <%-- ] --%>
    
    <%-- 結案原因 變更時的處理 --%>
    function doCloseCauseChange() {
        if ($F("closeCause") == "04") {
            $("divChoiceYm").style.display = "inline";
        }
        else {
            $("choiceYm").value = "";
            $("divChoiceYm").style.display = "none";
        }
    }

    <%-- 性別 國籍 檢核 --%>
    function checkSexAndNation() {
        if (getRadioValue("evtNationTpe") == "2") {
            if (getRadioValue("evtSex") == null || getRadioValue("evtSex") == "") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtSex" />」為必填欄位');
                return false;
            }
            
            if ($F("evtNationCode") == "") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtNationCode" />」為必填欄位');
                return false;
            }
            
            if ($F("evtNationCode") == "000") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtNationCode" />」為外籍不得輸入中華民國之國籍代碼');
                return false;
            }
            
            /*
            if ($("evtNationCode").selectedindex == null) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtNationCode" />」輸入錯誤');
                return false;
            }
            */
        }
        
        return true;
    }
    
    <%-- 事故者身分證號 檢核 --%>
    function checkEvtIdnNo() {
        if (getRadioValue("evtNationTpe") == "1") {
            if (Trim($F("evtIdnNo")).length != 10 || !chkPID_CHAR($F("evtIdnNo"))) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtIdnNo" />」輸入有誤，請輸入長度為 10 碼且符合格式的資料');
                return false;
            }
        }
        else {
            if (Trim($F("evtIdnNo")).length > 0) {
                if (!isEngNum($F("evtIdnNo"))) {
                    alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtIdnNo" />」格式錯誤');
                    return false;
                }
            }
        }
        
        return true;
    }
    
    
    <%-- 死亡日期 檢核 --%>
    function checkEvtDieDate() {
        var benCount = '<c:out value="${caseData.benCount}" />';
        var benCountAfterCheck = '<c:out value="${caseData.benCountAfterCheck}" />';

        if (parseNumber(benCount) != 0 && parseNumber(benCountAfterCheck) == 0) {
            if (Trim($F("evtDieDate")).length == 0) {
                alert("<bean:message bundle="<%=Global.BA_MSG%>" key="msg.update.disabled.evtDieDate" />");
                $("evtDieDate").focus();
                return false;
            }
        }

        if (Trim($F("evtDieDate")).length > 0) {
            if (parseNumber($F("evtDieDate")) > parseNumber(today)) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtDieDate" />」不得大於系統日期');
                $("evtDieDate").focus();
                return false;
            }
        }

        return true;
    }

    <%-- 事故者出生日期 檢核 --%>
    function checkEvtBrDate() {
        if (Trim($F("evtDieDate")).length > 0 && isNaN($("evtBrDate").value) == false) {
            if (parseNumber($F("evtBrDate")) > parseNumber($F("evtDieDate"))) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtBrDate" />」不得大於「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtDieDate" />」');
                $("evtBrDate").focus();
                return false;
            }
        }
    
        if(isNaN($("evtBrDate").value) == false){
        if (parseNumber($F("evtBrDate")) > parseNumber($F("appDate"))) {
            alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtBrDate" />」不得大於「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.appDate" />」');
            $("evtBrDate").focus();
            return false;
        }
        }
        return true;
    }
    
    <%-- 核定格式 檢核 --%>
    function checkNotifyForm() {
        var validNotifyFormValue = ($("validNotifyFormValue").value).split(",");

        var bValid = false;
        for(i = 0; i < validNotifyFormValue.length; i++){
            if($F("notifyForm") == validNotifyFormValue[i]) {
                bValid = true;
                break;
            }
        }

        if(!bValid){
            alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.notifyForm" />」不正確');
            $("notifyForm").focus();
            return false;
        }
        
        return true;
    }
    
    <%-- 診斷失能日期 檢核 --%>
    function checkEvtJobDate() {
        if (parseNumber($F("evtJobDate")) > parseNumber(today)) {
            alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtJobDate" />」不得大於系統日期');
            $("evtJobDate").focus();
            return false;
        }
        
        /*
        if (parseNumber($F("evtJobDate")) > parseNumber($F("appDate"))) {
            alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtJobDate" />」不得大於「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.appDate" />」');
            $("evtJobDate").focus();
            return false;
        }
        */
        
        if (Trim($F("evtDieDate")).length > 0) {
            if (parseNumber($F("evtJobDate")) > parseNumber($F("evtDieDate"))) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtJobDate" />」不得大於「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evtDieDate" />」');
                $("evtJobDate").focus();
                return false;
            }
        }

        return true;
    }
    
    function focusEvtIdnNo(e) {
        if (getRadioValue("evtNationTpe") == "2") {
            $("evtIdnNo").focus();
            return false;
        }

        return true;
    }

    function focusAppDate(e) {
        var benCount = '<c:out value="${caseData.benCount}" />';
        var benCountAfterCheck = '<c:out value="${caseData.benCountAfterCheck}" />';
        
        if (parseNumber(benCount) == 0)
            return true;
        else {
            if (parseNumber(benCountAfterCheck) != 0) {
                $("appDate").focus();
                return false;
            }
            else {
                return true;
            }
        }
    }
    
    function initAll() {
        doEvtNationTpeChange();
        doEvtNationCodeChange();
        doCloseCauseChange();
        checkIdnoExist();
        
        // 國籍
        Event.observe($("evtNationCode"), 'keydown', focusEvtIdnNo.bindAsEventListener(), false);
        Event.observe($("evtNationCode"), 'focus', focusEvtIdnNo.bindAsEventListener(), false);

        // 死亡日期
        Event.observe($("evtDieDate"), 'keydown', focusAppDate.bindAsEventListener(), false);
        Event.observe($("evtDieDate"), 'focus', focusAppDate.bindAsEventListener(), false);
        
        // 發放方式
        if($("isShowInterValMonth").value == 'Y'){
             if('<c:out value="${DisabledApplicationDataUpdateForm.interValMonth}" />' != "<%=ConstantKey.BAAPPBASE_INTERVALMONTH_BY_SIXMONTH%>"){
                $("interValMonth1").checked=true;
             }else{
                $("interValMonth2").checked=true;
             }
         }
        
        //國併勞勞併國註記
        var formComSeniority = '<c:out value="${DisabledApplicationDataUpdateForm.comSeniority}" />';
        var payKind = '<c:out value="${caseData.payKind}" />';
        //var comSeniority = '<c:out value="${caseData.comSeniority}" />';
        //if(formComSeniority != ''){
        if(formComSeniority == 'Y'){
            $("comSeniorityCk").checked=true;
        }else{
            $("comSeniorityCk").checked=false;
        }
        
        if (Trim($F("notifyForm")).length == 0)
            $("notifyForm").value = "999";
            
        $("evtNationCode").style.background = inputBlurBackgroundColor;
        $("evtIdnNo").focus();
    }
    
    function checkFields() {
        if($("comSeniorityCk").checked==true){
            $("comSeniority").value = "Y";
        }else{
            $("comSeniority").value = "";
        }
        
        if (checkSexAndNation() && checkEvtIdnNo() && checkEvtJobDate() && checkEvtDieDate() && checkEvtBrDate() && checkNotifyForm() && chkEvtBrDate()) {
            return true;
        }else {
            return false;
        }
    }
    
    function chkEvtBrDate() {
        //檢核事故者出生日期  是否為數字 及 年月格式
        var msg = ""; 
		
		var elements = document.getElementsByName("evtSex");
			for (var i = 0; i < elements.length; i++) {
			
			var checked=elements[i].checked;
			var defaultValue=elements[i].defaultValue;
			var secondText = $("evtIdnNo").value.substring(1,2);
			if($("evtIdnNo").value.length==10){
			if(getRadioValue("evtNationTpe") == "2" && defaultValue == "1" && checked){
				if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
					msg += '身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';	
							$("evtSex").focus();
					}
				}else if(getRadioValue("evtNationTpe") == "2"  && defaultValue == "2" && checked){
				if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
					msg += '身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';	
							$("evtSex").focus();
					}
				}
			}
			}

        if($("evtBrDate").value.length < 7){
                    msg += '輸入之「事故者出生日期」錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
        } 
        if(isNaN($("evtBrDate").value)){
                    msg += '輸入之「事故者出生日期」錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
                   
        }
        if($("evtBrDate").value.length == 7){
           var chkMonth = $("evtBrDate").value.substring(3,5);
           var chkDay   = $("evtBrDate").value.substring(5,7);
           if(chkMonth > 12 || chkDay > 32){
                    msg += '輸入之「事故者出生日期」錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
           }
        }else if($("evtBrDate").value.length == 8){
           var chkfrist = $("evtBrDate").value.substring(0,1);
           var chkMonth = $("evtBrDate").value.substring(4,6);
           var chkDay   = $("evtBrDate").value.substring(6,8);
           if(chkfrist != "-"){
                    msg += '輸入之「事故者出生日期」錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
                   
           }
           if(chkMonth > 12 || chkDay > 32){
                    msg += '輸入之「事故者出生日期」錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
           }
        }        
        
        if(msg != ""){
            alert(msg);
            return false;
        }else{
            if(isValidEvtDateTrue()){
            return true;
            }else{
            return false;
            }
        }
    }
    
    function controlButton() { 
    	// 獲得該button對象 
    	var btnUpdate = document.getElementById('btnConfirm'); 
    	var btnPrint = document.getElementById('btnPrint');
    	var btnBack = document.getElementById('btnBack');
    	// 創建計時器 返回計時器ID 
    	var intervalId = setInterval(function () { 
    		btnUpdate.disabled = true;
    		btnPrint.disabled = true;
    		btnBack.disabled = true;
    		}, 1000); 
    	}
 	
	// Added by JohnsonHuang 20200115 [Begin]
  	//外國人身分證號碼自動帶入
    function autoForeignEvtSex(){
    	var secondText = $("evtIdnNo").value.substring(1,2);
		if($("evtIdnNo").value.length==10){
    	if(getRadioValue("evtNationTpe") == "2" && (getRadioValue("evtSex") == null || getRadioValue("evtSex") == "")){
    		if(secondText=="A" || secondText=="a" || secondText=="C" || secondText=="c" || secondText=="8"){
    			setRadioValue("evtSex", "1"); 
    		}else if(secondText=="B" || secondText=="b" || secondText=="D" || secondText=="d" || secondText=="9"){
    			setRadioValue("evtSex", "2");	
    		}else{
    			var elements = document.getElementsByName("evtSex");
    			if (elements != null) {
    				for (var i = 0; i < elements.length; i++) {
    			    	elements[i].checked = false;
    			   	}
    			}
    			alert('「不符合外來人口統一證號編碼原則，請輸入正確身分證」');
    		}
    	}else{
			var elements = document.getElementsByName("evtSex");
			
				for (var i = 0; i < elements.length; i++) {
				
				var checked=elements[i].checked;
				var defaultValue=elements[i].defaultValue;
				if(getRadioValue("evtNationTpe") == "2" && defaultValue == "1" && checked){
					if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
						alert('身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
						}
					}else if(getRadioValue("evtNationTpe") == "2"  && defaultValue == "2" && checked){
					if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
						alert('身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
						}
					}
				}
    		}
		}
  		  }
 	// Added by EthanChen 20200115 [End]
	
    Event.observe(window, 'load', initAll , false);
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/disabledApplicationDataUpdate" method="post" onsubmit="return validateDisabledApplicationDataUpdateForm(this);">
        
        <fieldset>
            <legend>&nbsp;失能年金案件資料更正&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right">
                        <html:hidden styleId="page" property="page" value="4" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="baappbaseId" property="baappbaseId" />
                        <html:hidden styleId="apNo" property="apNo" />
                        <html:hidden styleId="seqNo" property="seqNo" />
                        <html:hidden styleId="procStat" property="procStat" />
                        <html:hidden styleId="baappexpandId" property="baappexpandId" />
                        <html:hidden styleId="validNotifyFormValue" property="validNotifyFormValue" />
                        <html:hidden styleId="idnoExist" property="idnoExist"/>
                        <input type="hidden" id="isShowInterValMonth" name="isShowInterValMonth" value="<c:out value="${app.isShowInterValMonth}" />">
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" id="btnConfirm" name="btnConfirm" tabindex="999" class="button" value="確　認" onclick="checkIdnoExist();$('page').value='4'; $('method').value='doSave'; if (document.DisabledApplicationDataUpdateForm.onsubmit() && checkFields()){document.DisabledApplicationDataUpdateForm.submit();controlButton();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <c:if test="${caseData.showCancelButton}">
                            <acl:checkButton buttonName="btnDelete">
                                <input type="button" id="btnDelete" name="btnDelete" tabindex="1000" class="button" value="註　銷" onclick="$('page').value='4'; $('method').value='doDelete'; if (confirm('確定註銷？')) {document.DisabledApplicationDataUpdateForm.submit();} else {return false;}"/>
                            </acl:checkButton>
                            &nbsp;
                        </c:if>
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" id="btnPrint" name="btnPrint" tabindex="1001" class="button_120" value="檢視受理/審核清單" onclick="$('page').value='4'; $('method').value='doPrint'; document.DisabledApplicationDataUpdateForm.submit();">
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" id="btnBack" name="btnBack" tabindex="1002" class="button" value="返　回" onclick="if (confirm('確定離開？')) { location.href='<c:url value="/enterDisabledApplicationDataUpdate.do?parameter=enterDisabledApplicationDataUpdate"/>'; } else {return false;}">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="33%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <bean:write name="DisabledApplicationDataUpdateForm" property="apNoString" />
                                </td>
                                <td width="33%">
                                    <c:if test="${caseData.payKind eq '36'}">
                                        <span class="issuetitle_L_down">給付種類：</span>國併勞
                                    </c:if>
                                    <c:if test="${caseData.payKind eq '38'}">
                                        <span class="issuetitle_L_down">給付種類：</span>勞併國
                                    </c:if>
                                    <c:if test="${(caseData.payKind ne '36') and (caseData.payKind ne '38')}">
                                    <span class="issuetitle_L_down">給付別：</span>
                                                                        失能年金
                                    </c:if>
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">處理狀態：</span>
                                    <bean:write name="DisabledApplicationDataUpdateForm" property="procStatString" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                            <tr>
                                <td colspan="3" class="issuetitle_L">
                                    <span class="systemMsg">▲</span>&nbsp;案件資料
                                </td>
                            </tr>
                            <tr>
                                <td width="33%" id="iss">
                                    &nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">國籍別：</span>
                                    <span class="formtxt" >
                                        <html:radio styleId="evtNationTpe" property="evtNationTpe" value="1" tabindex="1" onblur="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" onclick="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" onchange="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" disabled="true"/>本國
                                        <html:radio styleId="evtNationTpe" property="evtNationTpe" value="2" tabindex="2" onblur="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" onclick="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" onchange="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" disabled="true"/>外籍
                                        <html:hidden styleId="oldEvtNationTpe" property="oldEvtNationTpe" />
                                    </span>
                                </td>
                                <td width="33%" id="iss">
                                    <div id="divSex">
                                        &nbsp;&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">性　別：</span>
                                        <span class="formtxt">
                                            <html:radio styleId="evtSex" property="evtSex" value="1" tabindex="3" disabled="true"/>男

                                        </span>
                                        <span class="formtxt">
                                            <html:radio styleId="evtSex" property="evtSex" value="2" tabindex="4" disabled="true"/>女

                                        </span>
                                    </div>
                                    <html:hidden styleId="oldEvtSex" property="oldEvtSex" />
                                </td>
                                <td width="34%" id="iss">
                                    <div id="divNation">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">國　籍：</span>
                                        <html:text styleId="evtNationCode" property="evtNationCode" styleClass="textinput" size="3" maxlength="3" tabindex="5" onchange="doEvtNationCodeChange();" onkeyup="this.value = asc(this.value).toUpperCase();" onblur="this.value = asc(this.value).toUpperCase(); doEvtNationCodeChange();"/>
                                        &nbsp;
                                        <html:select styleId="tmpEvtNationCode" property="tmpEvtNationCode" tabindex="6" onchange="doTmpEvtNationCodeChange();" onblur="doTmpEvtNationCodeChange();" disabled="true">
                                            <html:option value="">請選擇...</html:option>
                                            <html:optionsCollection label="cname" value="countryId" property="countryOptionList" />
                                        </html:select>
                                    </div>
                                    <html:hidden styleId="oldEvtNationCode" property="oldEvtNationCode" />
                                    <html:hidden styleId="oldTmpEvtNationCode" property="oldTmpEvtNationCode" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    &nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:choose>
                                        <c:when test="${caseData.canModifyEvtName}">
                                            <html:text styleId="evtName" property="evtName" styleClass="disabled" size="40" maxlength="50" tabindex="7" />
                                        </c:when>
                                        <c:otherwise>
                                            <html:text styleId="evtName" property="evtName" styleClass="disabled" size="40" maxlength="50" onclick="this.blur(); $('evtIdnNo').focus();" onfocus="this.blur(); $('evtIdnNo').focus();" />
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${caseData.dupeIdnNoMk eq '1' or caseData.dupeIdnNoMk eq '2'}">
                                        <input type="button" id="btnChooseEvtName" name="btnChooseEvtName" tabindex="8" class="button_120" value="選擇事故者姓名" onclick="$('page').value='4'; $('method').value='doSelectEvtName'; document.DisabledApplicationDataUpdateForm.submit();" />
                                    </c:if>
                                    <c:if test="${caseData.containCheckMarkBD}">
                                        <span class="formtxt">
                                        (戶籍姓名：<html:text styleId="name" property="name" size="50" maxlength="50" onclick="this.blur(); $('evtIdnNo').focus();" onfocus="this.blur(); $('evtIdnNo').focus();" styleClass="disabled" readonly="true" />)
                                        </span>
                                    </c:if>
                                    <html:hidden styleId="oldEvtName" property="oldEvtName" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    &nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <html:text styleId="evtIdnNo" property="evtIdnNo" styleClass="disabled" readonly="true" size="25" maxlength="20" tabindex="9" onkeyup="this.value = asc(this.value).toUpperCase(); getCvldtlName();" onblur="this.value = asc(this.value).toUpperCase(); getCvldtlName(); setEvtSex();checkIdnoExist();autoForeignEvtSex();" />
                                    <html:hidden styleId="oldEvtIdnNo" property="oldEvtIdnNo" />
                                </td>
                                <td id="iss">
                                    &nbsp;&nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <html:text styleId="evtBrDate" property="evtBrDate" styleClass="disabled" readonly="true" size="10" maxlength="8" tabindex="10" onkeyup="getCvldtlName();" onblur="getCvldtlName();checkIdnoExist();" />
                                    <html:hidden styleId="oldEvtBrDate" property="oldEvtBrDate" />
                                </td>
                                <td id="iss">
                                    <span class="needtxt">　</span>
                                    <html:multibox styleId="comSeniorityCk" property="comSeniorityCk" value="Y" disabled="true"/>
                                    <span class="issuetitle_L_down">併計勞保年資</span>
                                    <html:hidden styleId="comSeniority" property="comSeniority" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    &nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">診斷失能日期：</span>
                                    <html:text styleId="evtJobDate" property="evtJobDate" styleClass="disabled" readonly="true" size="10" maxlength="7" tabindex="11" />
                                    <html:hidden styleId="oldEvtJobDate" property="oldEvtJobDate" />
                                </td>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">死亡日期：</span>
                                    <html:text styleId="evtDieDate" property="evtDieDate" styleClass="disabled" readonly="true" size="10" maxlength="7" tabindex="12" />
                                    <html:hidden styleId="oldEvtDieDate" property="oldEvtDieDate" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    &nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <html:text styleId="appDate" property="appDate" styleClass="disabled" readonly="true" size="10" maxlength="7" tabindex="13" />
                                    <html:hidden styleId="oldAppDate" property="oldAppDate" />
                                </td>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">最後單位保險證號：</span>
                                    <html:text styleId="lsUbno" property="lsUbno" styleClass="disabled" readonly="true" size="10" maxlength="8" tabindex="14" onblur="doUbno();" />
                                    <html:hidden styleId="oldLsUbno" property="oldLsUbno" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="1">
                                    &nbsp;&nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">核定格式：</span>
                                    <html:text styleId="notifyForm" property="notifyForm" styleClass="disabled" readonly="true" size="5" maxlength="3" tabindex="78" />
                                    <html:hidden styleId="oldNotifyForm" property="oldNotifyForm" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <table border="0" class="px13">
                                        <tr>
                                            <td border="0" valign="top" width="25%">
                                                &nbsp;&nbsp;&nbsp;&nbsp;
                                                <span class="issuetitle_L_down">事故者編審註記：</span>
                                            </td>
                                            <td border="0">
                                                <span class="formtxt">
                                                    <c:forEach var="checkFileList" items="${caseData.checkFileData}" varStatus="i">
                                                        <c:forEach var="item" items="${checkFileList}" varStatus="j">
                                                            <c:if test="${j.index eq 0}">
                                                                <c:out value="${item.payYmString}" />：

                                                            </c:if>
                                                            <c:if test="${j.index gt 0}">
                                                                <c:out value=",　" />
                                                            </c:if>
                                                            <span title='<c:out value="${item.chkFileDesc}" />'>
                                                                <c:out value="${item.chkFileName}" />
                                                            </span>
                                                        </c:forEach>
                                                        <br />
                                                    </c:forEach>
                                                </span>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <c:if test="${caseData.containCheckMarkLS eq true}">
                                <tr>
                                    <td id="iss" colspan="3">
                                        <html:hidden styleId="oldLoanMk" property="oldLoanMk" />
                                        <html:multibox styleId="loanMk" property="loanMk" value="1" tabindex="88" />
                                        <span class="issuetitle_L_down">不須抵銷紓困貸款</span>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td id="iss" colspan="3">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">結案原因：</span>
                                    <html:select styleId="closeCause" property="closeCause" tabindex="90" onchange="doCloseCauseChange();" onblur="doCloseCauseChange();">
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="kcloseCauseOptionList" />
                                    </html:select>
                                    <div id="divChoiceYm">
                                        <span class="formtxt">擇領起月：</span>
                                        <html:text styleId="choiceYm" property="choiceYm" styleClass="textinput" size="10" maxlength="5" tabindex="92" />
                                    </div>
                                    <html:hidden styleId="oldCloseCause" property="oldCloseCause" />
                                    <html:hidden styleId="oldChoiceYm" property="oldChoiceYm" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span>
                        <br />
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        1.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。

                        <br />
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        2.&nbsp;<span class="needtxt">＊</span>為必填欄位。

                    </td>
                </tr>
            </table>
        </fieldset>

         </html:form>

        <p></p>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
