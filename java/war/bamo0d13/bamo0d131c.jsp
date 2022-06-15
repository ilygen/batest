<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D131C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="DisabledApplicationDataUpdateForm" page="2" />
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
    
    <%-- 醫療院所代碼 變更時的處理 (AJAX) --%>
    <%-- [ --%>
    function doHosIdChange() {
        if (Trim($F("hosId")).length > 0)
            updateCommonAjaxDo.getHosName(Trim($F("hosId")), fillHosNameValue);
        else
            $("divHosName").innerHTML = "";
    }
    
    function fillHosNameValue(hosName) {
        if (hosName != null && hosName != "")
            $("divHosName").innerHTML = "(" + hosName + ")" ;
        else
            $("divHosName").innerHTML = "";
    }
    <%-- ] --%>
    
    <%-- 職病醫療院所代碼 變更時的處理 (AJAX) --%>
    <%-- [ --%>
    function doOcAccHosIdChange() {
        if (Trim($F("ocAccHosId")).length > 0)
            updateCommonAjaxDo.getHosName(Trim($F("ocAccHosId")), fillOcAccHosNameValue);
        else
            $("divOcAccHosName").innerHTML = "";
    }
    
    function fillOcAccHosNameValue(ocAccHosName) {
        if (ocAccHosName != null && ocAccHosName != "")
            $("divOcAccHosName").innerHTML = "(" + ocAccHosName + ")" ;
        else
            $("divOcAccHosName").innerHTML = "";
    }
    <%-- ] --%>    
    
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
    
    <%-- 重置查核失能程度註記預設值 --%>
    function initRehcMk() {
        $("rehcMk1").checked = false;
        $("rehcMk2").checked = false;
        $("rehcYear").value = "";
        $("rehcMonth").value = "";
        $("divRehcYm").style.display = "none";
            
        if($("oldRehcMk").value=="1"){
            if(parseNumber("<c:out value='${DisabledApplicationDataUpdateForm.rehcYmStr}' />") > parseNumber(today.substring(0 , 5))){
                $("rehcMk1").disabled = true;
                $("rehcMk1").checked = false;
            }else{
                $("rehcMk1").checked = true;
            }
        }
        else if ($("oldRehcMk").value=="2") {
            $("rehcMk2").checked = true;
            $("divRehcYm").style.display = "inline";
            $("rehcYear").value = $("oldRehcYear").value;
            $("rehcMonth").value = $("oldRehcMonth").value;
        }
        else {
            $("rehcMk1").checked = false;
            $("rehcMk2").checked = false;
            $("rehcYear").value = "";
            $("rehcMonth").value = "";
            $("divRehcYm").style.display = "none";
        }
    }
    
    <%-- 重新查核失能程度註記 變更時的處理 --%>
    function doRehcMkChange() {
        if ($("rehcMk2").checked==true) {
            $("divRehcYm").style.display = "inline";
        }
        else {
            $("rehcYear").value = "";
            $("rehcMonth").value = "";
            $("divRehcYm").style.display = "none";
        }
    }
    
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

    <%-- 傷病分類 變更時的處理 --%>
    function doEvTypChange() {
        if ($F("evTyp") == "3" || $F("evTyp") == "4") {
            //$("evCode").value = "";
            //$("criInPart1").value = "";
            //$("criInPart2").value = "";
            //$("criInPart3").value = "";
            //$("criMedium").value = "";
            $("cbxSameAsApply").checked = false;
            $("cbxSameAsApply").disabled = true;
            $("lsUbno").value =  "";
            //$("prType").disabled = false;
        }
        else {
            $("cbxSameAsApply").disabled = false;
            
            if (Trim($F("apUbno")).length > 0) {
                if ($F("apUbno") == $F("lsUbno")) {
                    $("cbxSameAsApply").checked = true;
                    $("lsUbno").value = Trim($F("apUbno"));
                }
                else {
                    $("cbxSameAsApply").checked = false;
                }
            }
            //$("prType").checked = false;
            //$("prType").disabled = true;
        }
        if ($F("evTyp") == "2") {
            //$("ocaccIdentMk").disabled = false;
            //$("ocaccIdentMk").value = $("oldOcaccIdentMk").value;
        }
        else {
            //$("ocaccIdentMk").value = "";
            //$("ocaccIdentMk").disabled = true;
        }
        
        doCutAmtChange();
    }
    
    function doEvTypAfterChange() {
        if ($F("evTyp") == "2") {
            //$("evCode").value = "";
            //$("criInPart1").value = "";
            //$("criInPart2").value = "";
            //$("criInPart3").value = "";
            //$("criMedium").value = "";
        }
        else if ($F("evTyp") == "1") {
            //$("evCode").value = "";
        }
    }
    
    <%-- 同申請單位 變更時的處理 --%>
    function doCbxSameAsApplyChange() {
        if ($F("evTyp") == "1" || $F("evTyp") == "2") {
            if ($("cbxSameAsApply").checked) {
                $("lsUbno").value = Trim($F("apUbno"));
            }
        }
    }
    
    <%-- 事故發生單位保險證號 變更時的處理 --%>
    function doLsUbnoChange() {
        if ($F("evTyp") == "1" || $F("evTyp") == "2") {
            if ($F("lsUbno") == $F("apUbno"))
                $("cbxSameAsApply").checked = true;
            else
                $("cbxSameAsApply").checked = false;
        }
    }
    
    <%-- 年金應扣金額 變更時的處理 --%>
    function doCutAmtChange() {
    <%--
        if (($F("evTyp") != "1" && $F("evTyp") != "2" && $F("evTyp") != "3" && $F("evTyp") != "4") || (Trim($F("cutAmt")).length > 0 && parseNumber($F("cutAmt")) <= 0))
    --%>
        if (($F("evTyp") != "1" && $F("evTyp") != "2" && $F("evTyp") != "3" && $F("evTyp") != "4"))
            $("deductDay").value = "";
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
    
    
    <%-- 發放方式 檢核 --%>
    function checkInterValMonth() {
        if ($("isShowInterValMonth").value=="Y"){
            if($("interValMonth").value==""){
               alert('請選擇「發放方式」');
               return false;
            }
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
    
    <%-- 申請日期 檢核 --%>
    function checkAppDate() {
        if (parseNumber($F("appDate")) > parseNumber(today) || parseNumber($F("appDate")) < parseNumber("0980101")) {
            alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.appDate" />」不得小於「0980101」且不得大於系統日期');
            $("appDate").focus();
            return false;
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
    
    <%-- 申請傷病分類 && 傷病分類 檢核 --%>
    function checkEvTyp() {
    	if ('1,2'.includes($F('evAppTyp'))) {
    		alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evAppTyp" />」僅得輸入「3」或「4」');
    		$("evAppTyp").focus();
    		return false;
    	}
    	if ('1,2'.includes($F('evTyp'))) {
    		alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evTyp" />」僅得輸入「3」或「4」');
    		$("evTyp").focus();
    		return false;
    	}
    	
        if($F("evTyp") == "1"){
            if (Trim($F("lsUbno")).length == 0) {
                alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.lsUbno" />」');
                $("lsUbno").focus();
                return false;
            }
        }
        if($F("evTyp") == "2"){
            if (Trim($F("lsUbno")).length == 0) {
                alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.lsUbno" />」');
                $("lsUbno").focus();
                return false;
            }
        }
        
       	<%--
        if ($F("evTyp") == "1" || $F("evTyp") == "2") {
            if ($F("evTyp") == "1") {
                if (Trim($F("evCode")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evCode" />」');
                    $("evCode").focus();
                    return false;
                }
    
                if (Trim($F("criInPart1")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.criInPart1" />」');
                    $("criInPart1").focus();
                    return false;
                }
    
                if (Trim($F("criMedium")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.criMedium" />」');
                    $("criMedium").focus();
                    return false;
                }
            }
            else if ($F("evTyp") == "2") {
                if (Trim($F("evCode")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evCode" />」');
                    $("evCode").focus();
                    return false;
                }
            }
        }
        else if ($F("evAppTyp") == "1" || $F("evAppTyp") == "2") {
            if ($F("evAppTyp") == "1") {
                if (Trim($F("evCode")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evCode" />」');
                    $("evCode").focus();
                    return false;
                }
    
                if (Trim($F("criInPart1")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.criInPart1" />」');
                    $("criInPart1").focus();
                    return false;
                }
    
                if (Trim($F("criMedium")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.criMedium" />」');
                    $("criMedium").focus();
                    return false;
                }
            }
            else if ($F("evAppTyp") == "2") {
                if (Trim($F("evCode")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.evCode" />」');
                    $("evCode").focus();
                    return false;
                }
            }
        }
		--%>

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
    
    <%-- 重新查核失能程度年月 檢核 --%>
    function checkRehcYearMonth() {
        <%-- c:if test="${caseData.containCheckMark3R}" --%>
            var oldRehcYm = "<c:out value='${DisabledApplicationDataUpdateForm.rehcYmStr}' />";
            if(Trim(oldRehcYm)==""){
                oldRehcYm = today.substring(0 , 5);
            }
                
            if (getRadioValue("rehcMk") == "1") {
                if (parseNumber(oldRehcYm) > parseNumber(today.substring(0 , 5))) {
                    alert("重新查核失能程度註記年月大於系統年月，不得點選自動遞延61個月");
                    $("rehcMk1").focus();
                    initRehcMk();
                    return false;
                }
            }
            else if (getRadioValue("rehcMk") == "2") {
                if (Trim($F("rehcYear")).length == 0 || Trim($F("rehcMonth")).length == 0) {
                    alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.rehcYm" />」為必填欄位');
                    $("rehcYear").focus();
                    initRehcMk();
                    return false;
                }
                
                if (!(parseNumber("" + $F("rehcYear") + $F("rehcMonth")) >= parseNumber(today.substring(0 , 5)) && parseNumber("" + $F("rehcYear") + $F("rehcMonth")) <= parseNumber(calMonth(today, 61).substring(0 , 5)))) {
                    alert("重新查核失能程度註記年月需大於等於系統年月，\r\n且小於等於系統年月+61個月");
                    $("rehcYear").focus();
                    initRehcMk();
                    return false;
                }
            }
        <%-- /c:if --%>

        return true;
    }
    
    <%-- 扣除日數 檢核 --%>
    function checkDeductDay() {
        var nCutAmt = $F("cutAmt");
        
        if (Trim($F("cutAmt")).length == 0)
            nCutAmt = "0";
    
        if (($F("evTyp") == "1" || $F("evTyp") == "2" || $F("evTyp") == "3" || $F("evTyp") == "4") && parseNumber(nCutAmt) > 0) {
            if (Trim($F("deductDay")).length == 0) {
                alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.deductDay" />」');
                $("deductDay").focus();
                return false;
            }
        }
        <%--
        if (parseNumber(nCutAmt) <= 0) {
            $("deductDay").value = "";
        }
        --%>
        return true;
    }
    
    <%-- 失能項目 / 醫療院所代碼 / 醫師姓名 檢核 --%>
    function checkEmptyValue() {
        var bOkCriInJdp = false;
        var bOkHosId = false;
        var bOkDoctorName = false;
        
        for (i = 1; i <= 10; i++) {
            if (Trim($F("criInJdp" + i)).length > 0) {
                bOkCriInJdp = true;
                break;
            }
        }

        if (Trim($F("hosId")).length > 0)
            bOkHosId = true;

        for (i = 1; i <= 2; i++) {
            if (Trim($F("doctorName" + i)).length > 0) {
                bOkDoctorName = true;
                break;
            }
        }
        
        if (bOkCriInJdp && bOkHosId && bOkDoctorName) {
            return true;
        }
        else {
            var msg = "";
            
            if (!bOkCriInJdp)
                msg = msg + '「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.criInJdp" />」';
                
            if (!bOkHosId)
                msg = msg + '「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.hosId" />」';
                
            if (!bOkDoctorName)
                msg = msg + '「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.doctorName" />」';
                
            if (confirm(msg + '無資料，請確認。')) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    
    <%-- 受傷部位 欄位重覆值檢核 --%>
    function checkCriInPartDupe() {
        for (i = 1; i <= 3; i++) {
            var criInPart = Trim($F("criInPart" + i));
            if (criInPart.length > 0) {
                for (n = i + 1; n <= 3; n++) {
                    if ($F("criInPart" + n).length > 0 && $F("criInPart" + n) == criInPart) {
                        alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.criInPart" />' + n + '」與「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.criInPart" />' + i + '」重覆');
                        $("criInPart" + n).focus();
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    <%-- 失能項目 欄位重覆值檢核 --%>
    function checkCriInJdpDupe() {
        for (i = 1; i <= 10; i++) {
            var criInJdp = Trim($F("criInJdp" + i));
            if (criInJdp.length > 0) {
                for (n = i + 1; n <= 10; n++) {
                    if ($F("criInJdp" + n).length > 0 && $F("criInJdp" + n) == criInJdp) {
                        alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.criInJdp" />' + n + '」與「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.criInJdp" />' + i + '」重覆');
                        $("criInJdp" + n).focus();
                        return false;
                    }
                }
            }
        }

        return true;
    }
    
    <%-- 醫師姓名 欄位重覆值檢核 --%>
    function checkDoctorNameDupe() {
        for (i = 1; i <= 2; i++) {
            var doctorName = Trim($F("doctorName" + i));
            if (doctorName.length > 0) {
                for (n = i + 1; n <= 2; n++) {
                    if ($F("doctorName" + n).length > 0 && $F("doctorName" + n) == doctorName) {
                        alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.doctorName" />' + n + '」與「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.doctorName" />' + i + '」重覆');
                        $("doctorName" + n).focus();
                        return false;
                    }
                }
            }
        }

        return true;
    }
    
    <%-- 國際疾病代碼 欄位重覆值檢核 --%>
    function checkCriInJnmeDupe() {
        for (i = 1; i <= 4; i++) {
            var criInJnme = Trim($F("criInJnme" + i));
            if (criInJnme.length > 0) {
                for (n = i + 1; n <= 4; n++) {
                    if ($F("criInJnme" + n).length > 0 && $F("criInJnme" + n) == criInJnme) {
                        alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.criInJnme" />' + n + '」與「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.disabled.criInJnme" />' + i + '」重覆');
                        $("criInJnme" + n).focus();
                        return false;
                    }
                }
            }
        }

        return true;
    }
    
    <%-- 欄位重覆值檢核 --%>
    function checkDupeValue() {
        if (checkCriInPartDupe() && checkCriInJdpDupe() && checkDoctorNameDupe() && checkCriInJnmeDupe())
            return true;
        else
            return false;
    }
    
    <%-- 受傷部位 欄位 Shift --%>
    function shiftCriInPart() {
        var criInPart = new Array();
        criInPart[0] = $F("criInPart1");
        criInPart[1] = $F("criInPart2");
        criInPart[2] = $F("criInPart3");
        
        $("criInPart1").value = "";
        $("criInPart2").value = "";
        $("criInPart3").value = "";
        
        var n = 0;
        for (i = 0 ; i < criInPart.length; i++) {
            if (Trim(criInPart[i]).length > 0) {
                $("criInPart" + (++n)).value = criInPart[i];
            }
        }
    }
    
    <%-- 失能項目 欄位 Shift --%>
    function shiftCriInJdp() {
        var criInJdp = new Array();
        criInJdp[0] = $F("criInJdp1");
        criInJdp[1] = $F("criInJdp2");
        criInJdp[2] = $F("criInJdp3");
        criInJdp[3] = $F("criInJdp4");
        criInJdp[4] = $F("criInJdp5");
        criInJdp[5] = $F("criInJdp6");
        criInJdp[6] = $F("criInJdp7");
        criInJdp[7] = $F("criInJdp8");
        criInJdp[8] = $F("criInJdp9");
        criInJdp[9] = $F("criInJdp10");

        $("criInJdp1").value = "";
        $("criInJdp2").value = "";
        $("criInJdp3").value = "";
        $("criInJdp4").value = "";
        $("criInJdp5").value = "";
        $("criInJdp6").value = "";
        $("criInJdp7").value = "";
        $("criInJdp8").value = "";
        $("criInJdp9").value = "";
        $("criInJdp10").value = "";

        var n = 0;
        for (i = 0 ; i < criInJdp.length; i++) {
            if (Trim(criInJdp[i]).length > 0) {
                $("criInJdp" + (++n)).value = criInJdp[i];
            }
        }
    }
    
    <%-- 醫師姓名 欄位 Shift --%>
    function shiftDoctorName() {
        var doctorName = new Array();
        doctorName[0] = $F("doctorName1");
        doctorName[1] = $F("doctorName2");

        $("doctorName1").value = "";
        $("doctorName2").value = "";

        var n = 0;
        for (i = 0 ; i < doctorName.length; i++) {
            if (Trim(doctorName[i]).length > 0) {
                $("doctorName" + (++n)).value = doctorName[i];
            }
        }
    }
    
    <%-- 國際疾病代碼 欄位 Shift --%>
    function shiftCriInJnme() {
        var criInJnme = new Array();
        criInJnme[0] = $F("criInJnme1");
        criInJnme[1] = $F("criInJnme2");
        criInJnme[2] = $F("criInJnme3");
        criInJnme[3] = $F("criInJnme4");

        $("criInJnme1").value = "";
        $("criInJnme2").value = "";
        $("criInJnme3").value = "";
        $("criInJnme4").value = "";

        var n = 0;
        for (i = 0 ; i < criInJnme.length; i++) {
            if (Trim(criInJnme[i]).length > 0) {
                $("criInJnme" + (++n)).value = criInJnme[i];
            }
        }
    }
    
    <%-- 輸入欄位 Shift --%>
    function doShiftFields() {
        shiftCriInPart();
        shiftCriInJdp();
        shiftDoctorName();
        shiftCriInJnme();
    }
    
    //變更 申請單位 時畫面異動    
    function changeMonNotifyingMk(){    	
        if (Trim($F("apUbno")).length > 0) {         
        	 $("monNotifyingMk").disabled = false;
        }
        else {
             $("monNotifyingMk").checked = false;
             $("monNotifyingMk").disabled = true;
        }   	    	    	   
    }      
    
    <%-- Form Submit 時, 申請單位保險證號 及 事故發生單位保險證號 處理 --%>
    function doUbno() {
        if ($F("evTyp") == "1" || $F("evTyp") == "2") {
            if ($("cbxSameAsApply").checked) {
                $("lsUbno").value = Trim($F("apUbno"));
            }
        }
        
        changeMonNotifyingMk();
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
    
    function focusCriInIssul(e) {
        if ($F("evTyp") == "3" || $F("evTyp") == "4") {
            $("criInIssul").focus();
            return false;
        }
        
        return true;
    }
    
    function focusCriInIssulExt(e) {
        if (!focusCriInIssul(e)) {
            return false;
        }
        else {
            if ($F("evTyp") == "2") {
                $("criInIssul").focus();
                return false;
            }

            return true;
        }
    }
    
    function focusEvTyp(e) {
        if ($F("evTyp") == "3" || $F("evTyp") == "4" || $("cbxSameAsApply").checked) {
            $("evTyp").focus();
            return false;
        }

        return true;
    }
    
    // 原本條件不符合不能輸入deductDay 20140708取消此條件
    function focusLoanMk(e) {
        if (($F("evTyp") != "1" && $F("evTyp") != "2" && $F("evTyp") != "3" && $F("evTyp") != "4") || (Trim($F("cutAmt")).length > 0 && parseNumber($F("cutAmt")) <= 0)) {
            $("deductDay").value = "";
            $("loanMk").focus();
            return false;
        }

        return true;
    }
    
    // 原本條件不符合不能輸入deductDay 20140708取消此條件
    function focusCloseCause(e) {
        if (($F("evTyp") != "1" && $F("evTyp") != "2" && $F("evTyp") != "3" && $F("evTyp") != "4") || (Trim($F("cutAmt")).length > 0 && parseNumber($F("cutAmt")) <= 0)) {
            $("deductDay").value = "";
            $("closeCause").focus();
            return false;
        }

        return true;
    }
    
    // 當「年金應扣金額」欄位為空白或0，開放「扣除日數」欄位可鍵入數值 
    // If (年金應扣金額為空白或0) and (扣除日數有值) then
	// Showmessage(尚未輸入年金應扣金額，如要輸入請按 [確定] ，如無須輸入，請按 [取消] 。)
    function checkDeductDayAlert() {
        if ((Trim($F("cutAmt")).length == 0 || parseNumber($F("cutAmt")) <= 0) && (Trim($F("deductDay")).length > 0)) {
           if(confirm("尚未輸入年金應扣金額，如要輸入請按 [確定] ，如無須輸入，請按 [取消] 。")){
               return false;
           }else{
               return true;
           }
            
        }else{
               return true;
        }
    }

    function initAll() {
    	// 隱藏職災欄位
    	var hiddenColumns = document.getElementsByClassName('col_hidden');
    	for (var e of hiddenColumns) {
    		e.style.display = 'none';
    	}
    	
        doEvtNationTpeChange();
        doEvtNationCodeChange();
        initRehcMk();
        doCloseCauseChange();
        doHosIdChange();
        //doOcAccHosIdChange();
        doEvTypChange();
        doCutAmtChange();
        checkIdnoExist();
        changeMonNotifyingMk();
        
        //國併勞勞併國註記
        var formComSeniority = '<c:out value="${DisabledApplicationDataUpdateForm.comSeniority}" />';
        var payKind = '<c:out value="${caseData.payKind}" />';
        var isContainCheckMark3M = '<c:out value="${caseData.containCheckMark3M}" />';
        //var comSeniority = '<c:out value="${caseData.comSeniority}" />';

        if(payKind == "36" || isContainCheckMark3M == "true" ){
            if(formComSeniority != ''){
                $("comSeniorityCk").checked=true;
            }else{
                $("comSeniorityCk").checked=false;
            }
        }
        
        //併計國保年資
        if(payKind != "36" && isContainCheckMark3M == "true" ){
        if(formComSeniority == 'Y'){
             $("comSeniorityCk").value = "Y"
        }else if(formComSeniority == 'N'){
             $("comSeniorityCk").value = "N"
        }else if(formComSeniority == 'X'){
             $("comSeniorityCk").value = "X"
        }else{
             $("comSeniorityCk").value = ""
        }
        }
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
        
        // 傷病原因
        <%--
        Event.observe($("evCode"), 'keydown', focusCriInIssul.bindAsEventListener(), false);
        Event.observe($("evCode"), 'focus', focusCriInIssul.bindAsEventListener(), false);
        --%>
        
        // 受傷部位
        <%--
        Event.observe($("criInPart1"), 'keydown', focusCriInIssulExt.bindAsEventListener(), false);
        Event.observe($("criInPart1"), 'focus', focusCriInIssulExt.bindAsEventListener(), false);
        Event.observe($("criInPart2"), 'keydown', focusCriInIssulExt.bindAsEventListener(), false);
        Event.observe($("criInPart2"), 'focus', focusCriInIssulExt.bindAsEventListener(), false);
        Event.observe($("criInPart3"), 'keydown', focusCriInIssulExt.bindAsEventListener(), false);
        Event.observe($("criInPart3"), 'focus', focusCriInIssulExt.bindAsEventListener(), false);
        --%>
        
        // 媒介物
        <%--
        Event.observe($("criMedium"), 'keydown', focusCriInIssulExt.bindAsEventListener(), false);
        Event.observe($("criMedium"), 'focus', focusCriInIssulExt.bindAsEventListener(), false);
        --%>
        
        // 事故發生單位保險證號
        Event.observe($("lsUbno"), 'keydown', focusEvTyp.bindAsEventListener(), false);
        Event.observe($("lsUbno"), 'focus', focusEvTyp.bindAsEventListener(), false);
        
        // 扣除天數
        <%--
        <c:choose>
            <c:when test="${caseData.containCheckMarkLS}">
                Event.observe($("deductDay"), 'keydown', focusLoanMk.bindAsEventListener(), false);
                Event.observe($("deductDay"), 'focus', focusLoanMk.bindAsEventListener(), false);
            </c:when>
            <c:otherwise>
                Event.observe($("deductDay"), 'keydown', focusCloseCause.bindAsEventListener(), false);
                Event.observe($("deductDay"), 'focus', focusCloseCause.bindAsEventListener(), false);
            </c:otherwise>
        </c:choose>
        --%>

        if (Trim($F("notifyForm")).length == 0)
            $("notifyForm").value = "999";
            
        $("evtNationCode").style.background = inputBlurBackgroundColor;
        $("evtIdnNo").focus();
    }
    
    function checkFields() {
    
      var payKind = '<c:out value="${caseData.payKind}" />';
      var isContainCheckMark3M = '<c:out value="${caseData.containCheckMark3M}" />';
      
        if(payKind == "36" || isContainCheckMark3M == "true" ){
            if($("comSeniorityCk").checked==true){
                $("comSeniority").value = "Y";
            }else{
                $("comSeniority").value = "";
            }
        }
        
        if(payKind != "36" && isContainCheckMark3M == "true" ){
            $("comSeniority").value = $("comSeniorityCk").value;
        }
        
        if (checkSexAndNation() && checkEvtIdnNo() && checkInterValMonth() && checkEvtJobDate() && checkEvtDieDate() && checkAppDate() && chkLsApUbno() && checkEvtBrDate() && checkEvTyp() && checkNotifyForm() && checkRehcYearMonth() && checkDeductDay() && chkEvtBrDate() && chkEvAppTyp()) {
        	        
            if (!checkEmptyValue())
                return false;
        
            if (checkDupeValue()) {
                if ($("monNotifyingMk").checked == false && Trim($F("apUbno")).length > 0) {
                	
              	  if(confirm("申請單位保險證號非空白，請確認是否勾選「寄發月通知表」?") == true){
                        return true;
                    }else{
                        $("apUbno").focus();
                        return false;
                    }
              }else{
              	return true;
              }                 	
                // return true;
            }else {
                return false;
            }                    
            
        }else {
            return false;
        }
    }
    
    function chkEvAppTyp(){
        var msg = ""; 
        
        
        
        
        
        var payKind = '<c:out value="${caseData.payKind}" />';

        if(payKind != "36"){
            if (Trim($("evAppTyp").value) == ""){
              msg += '「申請傷病分類」為必填欄位。\r\n';
              $("evAppTyp").focus();
            }
            if (Trim($("evTyp").value) == ""){
              msg += '「傷病分類」為必填欄位。\r\n';
              $("evTyp").focus();
            }
            if (Trim($("disQualMk").value) == ""){
              msg += '「年金請領資格」為必填欄位。\r\n';
              $("disQualMk").focus();
            }
        }
        
        if(msg != ""){
            alert(msg);
            return false;
        }else{
            return true;
        }
    }
    
    function chkLsApUbno(){
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
			
		
        var payKind = '<c:out value="${caseData.payKind}" />';
        //var comSeniority = '<c:out value="${caseData.comSeniority}" />';

        if(payKind == "36"){
            if (Trim($F("lsUbno")) == "") {
                msg += '請輸入「最後單位保險證號」';
                $("lsUbno").focus();
            }
        }else{
            if (Trim($F("apUbno")) == "") {
                msg += '請輸入「申請單位保險證號」';
                $("apUbno").focus();
            }
        }
        
        if(msg != ""){
            alert(msg);
            return false;
        }else{
            return true;
        }
    }
    
    function chkEvtBrDate() {
        //檢核事故者出生日期  是否為數字 及 年月格式
        var msg = ""; 

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

    function chgMonNotifyingMk(){
    	 if ($("monNotifyingMk").checked==true) {
             $F("monNotifyingMk") = "Y";
         }
         else {
        	 $F("monNotifyingMk") = "N";
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
                        <html:hidden styleId="page" property="page" value="2" />
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
                            <input type="button" id="btnConfirm" name="btnConfirm" tabindex="999" class="button" value="確　認" onclick="checkIdnoExist();$('page').value='2'; $('method').value='doSave'; doUbno(); doShiftFields(); if (document.DisabledApplicationDataUpdateForm.onsubmit() && checkFields() && checkDeductDayAlert()){document.DisabledApplicationDataUpdateForm.submit();controlButton();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <c:if test="${caseData.showCancelButton}">
                            <acl:checkButton buttonName="btnDelete">
                                <input type="button" id="btnDelete" name="btnDelete" tabindex="1000" class="button" value="註　銷" onclick="$('page').value='2'; $('method').value='doDelete'; if (confirm('確定註銷？')) {document.DisabledApplicationDataUpdateForm.submit();} else {return false;}" />
                            </acl:checkButton>
                            &nbsp;
                        </c:if>
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" id="btnPrint" name="btnPrint" tabindex="1001" class="button_120" value="檢視受理/審核清單" onclick="$('page').value='2'; $('method').value='doPrint'; document.DisabledApplicationDataUpdateForm.submit();">
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
                                    <c:out value="${caseData.sysCode}" />
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
                                    <span class="formtxt">
                                        <html:radio styleId="evtNationTpe" property="evtNationTpe" value="1" tabindex="1" onblur="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" onclick="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" onchange="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" />本國
                                        <html:radio styleId="evtNationTpe" property="evtNationTpe" value="2" tabindex="2" onblur="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" onclick="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" onchange="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" />外籍
                                        <html:hidden styleId="oldEvtNationTpe" property="oldEvtNationTpe" />
                                    </span>
                                </td>
                                <td width="33%" id="iss">
                              
                                    <div id="divSex">
                                        &nbsp;&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">性　別：</span>
                                        <span class="formtxt">
                                            <html:radio styleId="evtSex"  property="evtSex" value="1" tabindex="3" />男

                                        </span>
                                        <span class="formtxt">
                                            <html:radio styleId="evtSex" property="evtSex" value="2" tabindex="4" />女

                                        </span>
                                    </div>
                                    <html:hidden styleId="oldEvtSex" property="oldEvtSex" />
                                   
                                </td>
                                <td width="34%" id="iss">
                                    <div id="divNation">
                                    	&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">國　籍：</span>
                                        <html:text styleId="evtNationCode" property="evtNationCode" styleClass="textinput" size="3" maxlength="3" tabindex="5" onchange="doEvtNationCodeChange();" onkeyup="this.value = asc(this.value).toUpperCase();" onblur="this.value = asc(this.value).toUpperCase(); doEvtNationCodeChange();"/>
                                        &nbsp;
                                        <html:select styleId="tmpEvtNationCode" property="tmpEvtNationCode" tabindex="6" onchange="doTmpEvtNationCodeChange();" onblur="doTmpEvtNationCodeChange();">
                                            <html:option value="">請選擇...</html:option>
                                            <html:optionsCollection label="cname" value="countryId" property="countryOptionList" />
                                        </html:select>
                                    </div>
                                    <html:hidden styleId="oldEvtNationCode" property="oldEvtNationCode" />
                                    <html:hidden styleId="oldTmpEvtNationCode" property="oldTmpEvtNationCode" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="2">
                                    &nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:choose>
                                        <c:when test="${caseData.canModifyEvtName}">
                                            <html:text styleId="evtName" property="evtName" styleClass="textinput" size="40" maxlength="50" tabindex="7" />
                                        </c:when>
                                        <c:otherwise>
                                            <html:text styleId="evtName" property="evtName" styleClass="textinput" size="40" maxlength="50" />
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${caseData.dupeIdnNoMk eq '1' or caseData.dupeIdnNoMk eq '2'}">
                                        <input type="button" id="btnChooseEvtName" name="btnChooseEvtName" tabindex="8" class="button_120" value="選擇事故者姓名" onclick="$('page').value='2'; $('method').value='doSelectEvtName'; document.DisabledApplicationDataUpdateForm.submit();" />
                                    </c:if>
                                    <c:if test="${caseData.containCheckMarkBD}">
                                        <span class="formtxt">
                                        (戶籍姓名：<html:text styleId="name" property="name" styleClass="textinput" size="50" maxlength="50" />)
                                        </span>
                                    </c:if>
                                    <html:hidden styleId="oldEvtName" property="oldEvtName" />
                                </td>
                                <c:choose>
                                        <c:when test="${caseData.containCheckMark3M}">
                                            <td id="iss">
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            &nbsp;<span class="issuetitle_L_down">國保受理編號：</span>
                                            <html:text styleId="mapNo" property="mapNo" styleClass="textinput" size="12" maxlength="12" />
                                        　　　　　　　　　　　　　　　　　　　　　　　</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td id="iss">
                                            &nbsp;<span class="issuetitle_L_down">&nbsp;</span>
                                        　　　　　　　　　　　　　　　　　　　　　　　</td>
                                        </c:otherwise>
                                 </c:choose>
                                  <html:hidden styleId="oldMapNo" property="oldMapNo" />
                            </tr>
                            <tr>
                                <td id="iss">
                                    &nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <html:text styleId="evtIdnNo" property="evtIdnNo" styleClass="textinput" size="25" maxlength="20" tabindex="9" onkeyup="this.value = asc(this.value).toUpperCase(); getCvldtlName();" onblur="this.value = asc(this.value).toUpperCase(); getCvldtlName(); setEvtSex();checkIdnoExist();autoForeignEvtSex();" />
                                    <html:hidden styleId="oldEvtIdnNo" property="oldEvtIdnNo" />
                                </td>
                                <td id="iss">
                                    &nbsp;&nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <html:text styleId="evtBrDate" property="evtBrDate" styleClass="textinput" size="10" maxlength="8" tabindex="10" onkeyup="getCvldtlName();" onblur="getCvldtlName();checkIdnoExist();" />
                                    <html:hidden styleId="oldEvtBrDate" property="oldEvtBrDate" />
                                </td>
                                <td id="iss" valign="top">
                                    <c:if test="${caseData.isShowInterValMonth eq 'Y'}">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">發放方式：</span>
                                        <html:select property="interValMonth">
                                            <html:option styleId="interValMonth1" value="<%=ConstantKey.BAAPPBASE_INTERVALMONTH_BY_MONTH %>">按月發放</html:option>
                                            <html:option styleId="interValMonth2" value="<%=ConstantKey.BAAPPBASE_INTERVALMONTH_BY_SIXMONTH %>">半年發放</html:option>
                                        </html:select>
                                    </c:if>
                                    <c:if test="${caseData.isShowInterValMonth ne 'Y'}">
                                        &nbsp;
                                    </c:if>
                                </td>   
                            </tr>
                            <tr>
                                <td id="iss">
                                    &nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">診斷失能日期：</span>
                                    <html:text styleId="evtJobDate" property="evtJobDate" styleClass="textinput" size="10" maxlength="7" tabindex="11" />
                                    <html:hidden styleId="oldEvtJobDate" property="oldEvtJobDate" />
                                </td>
                                <td id="iss">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">死亡日期：</span>
                                    <html:text styleId="evtDieDate" property="evtDieDate" styleClass="textinput" size="10" maxlength="7" tabindex="12" />
                                    <html:hidden styleId="oldEvtDieDate" property="oldEvtDieDate" />
                                </td>
                                <td id="iss">
                                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <c:if test="${caseData.payKind eq '36'}">
                                        <span class="needtxt">　</span>
                                        <html:multibox styleId="comSeniorityCk" property="comSeniorityCk" value="Y" disabled="true"/>
                                        <span class="issuetitle_L_down">併計勞保年資</span>
                                    </c:if>
                                    <c:if test="${caseData.containCheckMark3M}">
                                        <span class="issuetitle_L_down">併計國保年資</span>
                                        <html:select property="comSeniorityCk">
                                         <html:option value="">請選擇...</html:option>
                                         <html:option styleId="comSeniorityCk1" value="Y">Y-同意併計</html:option>
                                         <html:option styleId="comSeniorityCk2" value="N">N-逕不併計</html:option>
                                         <html:option styleId="comSeniorityCk3" value="X">X-同意不併計</html:option>
                                        </html:select>
                                    </c:if>
                                    <html:hidden styleId="comSeniority" property="comSeniority" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    &nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <html:text styleId="appDate" property="appDate" styleClass="textinput" size="10" maxlength="7" tabindex="13" />
                                    <html:hidden styleId="oldAppDate" property="oldAppDate" />
                                </td>
                                <c:if test="${caseData.payKind eq '36'}">
                                    <td id="iss">
                                        &nbsp;&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">最後單位保險證號：</span>
                                        <html:text styleId="lsUbno" property="lsUbno" styleClass="textinput" size="10" maxlength="8" tabindex="14" onblur="doUbno();" />
                                        <html:hidden styleId="oldLsUbno" property="oldLsUbno" />
                                    </td>
                                </c:if>
                                <c:if test="${caseData.payKind ne '36'}">
                                    <td id="iss">
                                        &nbsp;&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">申請單位保險證號：</span>
                                        <html:text styleId="apUbno" property="apUbno" styleClass="textinput" size="10" maxlength="8" tabindex="14" onblur="doUbno();" onkeyup="changeMonNotifyingMk();" />
                                        <html:hidden styleId="oldApUbno" property="oldApUbno" />
                                    </td>
                                </c:if>
                                <td id="iss">
                                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">事故發生保險證號：</span>
                                    <html:multibox styleId="cbxSameAsApply" property="cbxSameAsApply" value="Y" tabindex="15" onclick="doCbxSameAsApplyChange();" />同申請單位
                                    <html:text styleId="lsUbno" property="lsUbno" styleClass="textinput" size="10" maxlength="8" tabindex="16" onblur="doLsUbnoChange();" />
                                    <html:hidden styleId="oldLsUbno" property="oldLsUbno" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    &nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">申請傷病分類：</span>
                                    <html:select styleId="evAppTyp" property="evAppTyp" tabindex="17">
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="evTypOptionList" />
                                    </html:select>
                                    <html:hidden styleId="oldEvAppTyp" property="oldEvAppTyp" />
                                </td>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">核定傷病分類：</span>
                                    <html:select styleId="evTyp" property="evTyp" tabindex="20" onchange="doEvTypChange();doEvTypAfterChange();" onblur="doEvTypChange();">
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="evTypOptionList" />
                                    </html:select>
                                    <html:hidden styleId="oldEvTyp" property="oldEvTyp" />
                                </td>
                                <td id="iss" class="col_hidden">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">傷病原因：</span>
                                    <html:text styleId="evCode" property="evCode" styleClass="textinput" size="5" maxlength="2" tabindex="22" />
                                    <html:hidden styleId="oldEvCode" property="oldEvCode" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" class="col_hidden">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">受傷部位：</span>
                                    <html:text styleId="criInPart1" property="criInPart1" styleClass="textinput" size="3" maxlength="3" tabindex="24" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInPart2" property="criInPart2" styleClass="textinput" size="3" maxlength="3" tabindex="26" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInPart3" property="criInPart3" styleClass="textinput" size="3" maxlength="3" tabindex="28" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    <html:hidden styleId="oldCriInPart1" property="oldCriInPart1" />
                                    <html:hidden styleId="oldCriInPart2" property="oldCriInPart2" />
                                    <html:hidden styleId="oldCriInPart3" property="oldCriInPart3" />
                                </td>
                                <td id="iss" class="col_hidden">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">媒 介 物：</span>
                                    <html:text styleId="criMedium" property="criMedium" styleClass="textinput" size="3" maxlength="3" tabindex="30" />
                                    <html:hidden styleId="oldCriMedium" property="oldCriMedium" />
                                </td>
                                <td id="iss" colspan="3">
                                	&nbsp;                     
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">年金請領資格：</span>
                                    <html:select styleId="disQualMk" property="disQualMk" tabindex="20" >
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="disQualMkOptionList" />
                                    </html:select>
                                    <html:hidden styleId="disQualMk" property="disQualMk" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">核定等級：</span>
                                    <html:text styleId="criInIssul" property="criInIssul" styleClass="textinput" size="5" maxlength="2" tabindex="32" onfocus="$('criInJdp1').focus();" />
                                    <html:hidden styleId="oldCriInIssul" property="oldCriInIssul" />
                                </td>
                                <td id="iss" colspan="1">
                                    <html:multibox styleId="monNotifyingMk" property="monNotifyingMk" value="Y" tabindex="80" onclick="chgMonNotifyingMk();" />
                                    <span class="issuetitle_L_down">寄發月通知表</span>
                                    <html:hidden styleId="oldMonNotifyingMk" property="oldMonNotifyingMk" />
                                </td>                                
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">失能項目：</span>
                                    <html:text styleId="criInJdp1" property="criInJdp1" styleClass="textinput" size="8" maxlength="8" tabindex="34" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJdp2" property="criInJdp2" styleClass="textinput" size="8" maxlength="8" tabindex="36" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJdp3" property="criInJdp3" styleClass="textinput" size="8" maxlength="8" tabindex="38" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJdp4" property="criInJdp4" styleClass="textinput" size="8" maxlength="8" tabindex="40" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJdp5" property="criInJdp5" styleClass="textinput" size="8" maxlength="8" tabindex="42" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJdp6" property="criInJdp6" styleClass="textinput" size="8" maxlength="8" tabindex="44" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJdp7" property="criInJdp7" styleClass="textinput" size="8" maxlength="8" tabindex="46" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJdp8" property="criInJdp8" styleClass="textinput" size="8" maxlength="8" tabindex="48" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJdp9" property="criInJdp9" styleClass="textinput" size="8" maxlength="8" tabindex="50" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJdp10" property="criInJdp10" styleClass="textinput" size="8" maxlength="8" tabindex="52" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    <html:hidden styleId="oldCriInJdp1" property="oldCriInJdp1" />
                                    <html:hidden styleId="oldCriInJdp2" property="oldCriInJdp2" />
                                    <html:hidden styleId="oldCriInJdp3" property="oldCriInJdp3" />
                                    <html:hidden styleId="oldCriInJdp4" property="oldCriInJdp4" />
                                    <html:hidden styleId="oldCriInJdp5" property="oldCriInJdp5" />
                                    <html:hidden styleId="oldCriInJdp6" property="oldCriInJdp6" />
                                    <html:hidden styleId="oldCriInJdp7" property="oldCriInJdp7" />
                                    <html:hidden styleId="oldCriInJdp8" property="oldCriInJdp8" />
                                    <html:hidden styleId="oldCriInJdp9" property="oldCriInJdp9" />
                                    <html:hidden styleId="oldCriInJdp10" property="oldCriInJdp10" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">醫療院所代碼：</span>
                                    <html:text styleId="hosId" property="hosId" styleClass="textinput" size="10" maxlength="10" tabindex="54" onkeyup="doHosIdChange();" onblur="doHosIdChange();" />
                                    <span id="divHosName">
                                        <bean:write name="DisabledApplicationDataUpdateForm" property="hosName" />
                                    </span>
                                    <html:hidden styleId="oldHosId" property="oldHosId" />
                                </td>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">醫師姓名：</span>
                                    <html:text styleId="doctorName1" property="doctorName1" styleClass="textinput" size="12" maxlength="12" tabindex="55" />
                                    &nbsp;
                                    <html:text styleId="doctorName2" property="doctorName2" styleClass="textinput" size="12" maxlength="12" tabindex="56" />
                                    <html:hidden styleId="oldDoctorName1" property="oldDoctorName1" />
                                    <html:hidden styleId="oldDoctorName2" property="oldDoctorName2" />
                                </td>
                            </tr>
                            <tr class="col_hidden">
                                <td id="iss">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">職病醫療院所代碼：</span>
                                    <html:text styleId="ocAccHosId" property="ocAccHosId" styleClass="textinput" size="10" maxlength="10" tabindex="57" onkeyup="doOcAccHosIdChange();" onblur="doOcAccHosIdChange();" />
                                    <span id="divOcAccHosName">
                                        <bean:write name="DisabledApplicationDataUpdateForm" property="ocAccHosName" />
                                    </span>
                                    <html:hidden styleId="oldOcAccHosId" property="oldOcAccHosId" />
                                </td>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">職病醫師姓名：</span>
                                    <html:text styleId="ocAccDoctorName1" property="ocAccDoctorName1" styleClass="textinput" size="12" maxlength="12" tabindex="58" />
                                    &nbsp;
                                    <html:text styleId="ocAccDoctorName2" property="ocAccDoctorName2" styleClass="textinput" size="12" maxlength="12" tabindex="59" />
                                    <html:hidden styleId="oldOcAccDoctorName1" property="oldOcAccDoctorName1" />
                                    <html:hidden styleId="oldOcAccDoctorName2" property="oldOcAccDoctorName2" />
                                </td>
                            </tr>                            
                            <tr>
                                <td id="iss" colspan="3">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">國際疾病代碼：</span>
                                    <html:text styleId="criInJnme1" property="criInJnme1" styleClass="textinput" size="30" maxlength="30" tabindex="60" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJnme2" property="criInJnme2" styleClass="textinput" size="30" maxlength="30" tabindex="62" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJnme3" property="criInJnme3" styleClass="textinput" size="30" maxlength="30" tabindex="64" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJnme4" property="criInJnme4" styleClass="textinput" size="30" maxlength="30" tabindex="66" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    <html:hidden styleId="oldCriInJnme1" property="oldCriInJnme1" />
                                    <html:hidden styleId="oldCriInJnme2" property="oldCriInJnme2" />
                                    <html:hidden styleId="oldCriInJnme3" property="oldCriInJnme3" />
                                    <html:hidden styleId="oldCriInJnme4" property="oldCriInJnme4" />
                                </td>
                            </tr>
							<tr>
							    <td id="iss" colspan="3">
								     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								     <!-- html:multibox styleId="cbxRehcMk" property="cbxRehcMk" value="Y" tabindex="41" / -->
								     <span class="issuetitle_L_down">重新查核失能程度註記(<c:out value="${DisabledApplicationDataUpdateForm.rehcYmString}" />)：</span>
								     <span class="formtxt">
								         <html:radio styleId="rehcMk1" property="rehcMk" value="1" tabindex="68" onblur="doRehcMkChange();" onchange="doRehcMkChange();" onclick="doRehcMkChange();" />自動遞延61個月
								         <html:radio styleId="rehcMk2" property="rehcMk" value="2" tabindex="70" onblur="doRehcMkChange();" onchange="doRehcMkChange();" onclick="doRehcMkChange();" />手動調整
								         <div id="divRehcYm">
								                                                                                                                    ：
								             <html:text styleId="rehcYear" property="rehcYear" styleClass="textinput" size="3" maxlength="3" tabindex="72" />年
								             <html:text styleId="rehcMonth" property="rehcMonth" styleClass="textinput" size="2" maxlength="2" tabindex="74" />月
								         </div>
								         <c:if test="${caseData.rehcOpen eq 'Y'}">
								              <input type="button" id="btnRehc" name="btnRehc" tabindex="75" class="button_120" value="重新查核失能程度" onclick="$('page').value='2'; $('method').value='doRehc'; document.DisabledApplicationDataUpdateForm.submit();">
								         </c:if>
								     </span>
								     <!-- html:hidden styleId="oldCbxRehcMk" property="oldCbxRehcMk" / -->
								     <html:hidden styleId="oldRehcMk" property="oldRehcMk" />
								     <html:hidden styleId="oldRehcYear" property="oldRehcYear" />
								     <html:hidden styleId="oldRehcMonth" property="oldRehcMonth" />
							    </td>
							</tr>
                            <tr>
                                <td id="iss" class="col_hidden">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">符合第20條之1</span>
                                    <html:hidden styleId="oldOcaccIdentMk" property="oldOcaccIdentMk" />
                                    <html:select property="ocaccIdentMk" tabindex="76" >
                                         <html:option value="">請選擇...</html:option>
                                         <html:option styleId="ocaccIdentMk1" value="1">已領取老年</html:option>
                                         <html:option styleId="ocaccIdentMk2" value="2">未領取老年</html:option>
                                    </html:select>
                                </td>
                                <td id="iss" colspan="3">
                                    &nbsp;&nbsp;
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">核定格式：</span>
                                    <html:text styleId="notifyForm" property="notifyForm" styleClass="textinput" size="5" maxlength="3" tabindex="78" />
                                    <html:hidden styleId="oldNotifyForm" property="oldNotifyForm" />
                                </td>
                                <td id="iss" class="col_hidden">
                                    <html:multibox styleId="prType" property="prType" value="Y" tabindex="80" />
                                    <span class="issuetitle_L_down">先核普通</span>
                                    <html:hidden styleId="oldPrType" property="oldPrType" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">年金應扣金額：</span>
                                    <html:text styleId="cutAmt" property="cutAmt" styleClass="textinput" size="8" maxlength="8" tabindex="82" onblur="doCutAmtChange();" />
                                    <html:hidden styleId="oldCutAmt" property="oldCutAmt" />
                                </td>
                                <td id="iss" class="col_hidden">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">己領職災增給金額：</span>
                                    <html:text styleId="ocAccaddAmt" property="ocAccaddAmt" styleClass="textinput" size="8" maxlength="7" tabindex="84" />
                                    <html:hidden styleId="oldOcAccaddAmt" property="oldOcAccaddAmt" />
                                </td>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">扣除日數：</span>
                                    <html:text styleId="deductDay" property="deductDay" styleClass="textinput" size="4" maxlength="4" tabindex="86" />
                                    <html:hidden styleId="oldDeductDay" property="oldDeductDay" />
                                </td>
                            </tr>
                            <tr>
                            	<td id="iss" colspan="3">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">來源受理編號：</span>
                                    <c:out value="${caseData.apnoFm}" />
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
