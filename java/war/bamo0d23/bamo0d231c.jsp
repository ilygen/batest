<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.SURVIVOR_APPLICATION_DATA_UPDATE_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D231C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="SurvivorApplicationDataUpdateForm" page="2" />
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

    <%-- 取得 戶籍姓名 (AJAX) --%>
    <%-- [ --%>
    function getCvldtlName() {
        if ($("name") != null) {
            if (Trim($F("evtIdnNo")).length == 10 && Trim($F("evtBrDate")).length == 7 && isNaN($("evtBrDate").value) == false)
                updateCommonAjaxDo.getCvldtlName(Trim($F("evtIdnNo")), Trim($F("evtBrDate")), fillCvldtlNameValue);
        }
    }

    function fillCvldtlNameValue(cvldtlName) {
            $("name").value = cvldtlName;
    }
    <%-- ] --%>
    
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
    
    <%-- 申請項目 變更時的處理 --%>
    function doApItemChange() {
        if ($F("apItem") == "7" || $F("apItem") == "8") {
            $("divApItem").style.display = "inline";
        }
        else {
            $("divApItem").style.display = "none";
        }
        
        if ($F("apItem") != "4" && $F("apItem") != "5") {
            $("prType").checked = false;
        }
        
        if ($F("apItem") != "9") {
            $("famAppMk").checked = false;
        }
        
        if ($F("apItem") == "7" || $F("apItem") == "8" || $F("apItem") == "9") {
            $("cbxSameAsApply").checked = false;
            $("cbxSameAsApply").disabled = true;
            if ($F("apItem") != "9")
                $("lsUbno").value =  "";
        }
        else if ($F("apItem") == "4" || $F("apItem") == "5") {
            if ($F("evTyp") == "3" || $F("evTyp") == "4") {
                $("cbxSameAsApply").checked = false;
                $("cbxSameAsApply").disabled = true;
                $("lsUbno").value =  "";
            }
            else if ($F("evTyp") == "1" || $F("evTyp") == "2") {
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
            }
        }
    }

    <%-- 結案原因 變更時的處理 --%>
    function doCloseCauseChange() {
        if ($F("closeCause") == "02" || $F("closeCause") == "03") {
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
            //$("evCode").value="";
            //$("criInPart1").value="";
            //$("criInPart2").value="";
            //$("criInPart3").value="";
            //$("criMedium").value="";
            $("prType").disabled = false;
        }
        else {
            $("prType").checked = false;
            $("prType").disabled = true;
        }
        doApItemChange();
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
        if ($F("apItem") == "4" || $F("apItem") == "5") {
            if ($F("evTyp") == "1" || $F("evTyp") == "2") {
                if ($("cbxSameAsApply").checked) {
                    $("lsUbno").value = Trim($F("apUbno"));
                }
            }
        }
    }
    
    <%-- 事故發生單位保險證號 變更時的處理 --%>
    function doLsUbnoChange() {
        if ($F("apItem") == "4" || $F("apItem") == "5") {
            if ($F("evTyp") == "1" || $F("evTyp") == "2") {
                if ($F("lsUbno") == $F("apUbno"))
                    $("cbxSameAsApply").checked = true;
                else
                    $("cbxSameAsApply").checked = false;
            }
        }
    }

    <%-- 性別 國籍 檢核 --%>
    function checkSexAndNation() {
        if (getRadioValue("evtNationTpe") == "2") {
            if (getRadioValue("evtSex") == null || getRadioValue("evtSex") == "") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtSex" />」為必填欄位');
                return false;
            }

            if ($F("evtNationCode") == "") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtNationCode" />」為必填欄位');
                return false;
            }
            
            if ($F("evtNationCode") == "000") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtNationCode" />」為外籍不得輸入中華民國之國籍代碼');
                return false;
            }
            
            /*
            if ($("evtNationCode").selectedindex == null) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtNationCode" />」輸入錯誤');
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
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtIdnNo" />」輸入有誤，請輸入長度為 10 碼且符合格式的資料');
                return false;
            }
        }
        else {
            if (Trim($F("evtIdnNo")).length > 0) {
                if (!isEngNum($F("evtIdnNo"))) {
                    alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtIdnNo" />」格式錯誤');
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
    
    <%-- 判決日期 檢核 --%>
    function checkJudgeDate() {
        if (Trim($F("judgeDate")) != "") {
            if (parseNumber($F("judgeDate")) > parseNumber(today)) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.judgeDate" />」不得大於系統日期');
                $("judgeDate").focus();
                return false;
            }
        }
    
        return true;
    }
    
    <%-- 申請日期 檢核 --%>
    function checkAppDate() {
        if (parseNumber($F("appDate")) > parseNumber(today) || parseNumber($F("appDate")) < parseNumber("0980101")) {
            alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.appDate" />」不得小於「0980101」且不得大於系統日期');
            $("appDate").focus();
            return false;
        }

        return true;
    }
    
    <%-- 死亡日期 檢核 --%>
    function checkEvtDieDate() {
        if (Trim($F("judgeDate")) != "" && Trim($F("evtDieDate")) != "") {
            if (Trim($F("judgeDate")) < Trim($F("evtDieDate"))) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.judgeDate" />」須大於等於「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtDieDate" />」');
                return false;
            }
        }
        
        if (parseNumber($F("evtDieDate")) > parseNumber(today)) {
            alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtDieDate" />」不得大於系統日期');
            $("evtDieDate").focus();
            return false;
        }
        
        if (parseNumber($F("evtDieDate")) > parseNumber($F("appDate"))) {
            alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtDieDate" />」不得大於「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.appDate" />」');
            $("evtDieDate").focus();
            return false;
        }
        
        return true;
    }
    
    <%-- 事故者出生日期 檢核 --%>
    function checkEvtBrDate() {
        if (Trim($F("evtDieDate")) != "" && isNaN($("evtBrDate").value) == false) {
             if (Trim($F("evtBrDate")) > Trim($F("evtDieDate"))) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtBrDate" />」不得大於「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtDieDate" />」');
                $("evtBrDate").focus();
                return false;
            }
        }
        
        if (Trim($F("appDate")) != "" && isNaN($("evtBrDate").value) == false) {
             if (Trim($F("evtBrDate")) > Trim($F("appDate"))) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evtBrDate" />」不得大於「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.appDate" />」');
                $("evtBrDate").focus();
                return false;
            }
        }
        
        return true;
    }
    
    <%-- 申請項目 檢核 --%>
    function checkApItem() {
        if ($F("apItem") != "8" && $F("apItem") != "9") {
            if ($F("apItem") != "7") {
                if (Trim($F("apUbno")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.apUbno" />」');
                    $("apUbno").focus();
                    return false;
                }
            }
            
            if ($F("apItem") == "4" || $F("apItem") == "5") {
                if ($F("evTyp") == "1" || $F("evTyp") == "2") {
                    if (Trim($F("lsUbno")).length == 0) {
                        alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.lsUbno" />」');
                        $("lsUbno").focus();
                        return false;
                    }
                }
            }
        }
        else {
            if ($F("evTyp") == "1" || $F("evTyp") == "2") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.apItem" />」為 8、9 時「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evTyp" />」不得選擇「職業傷害」及「職業病」');
                $("evTyp").focus();
                return false;
            }
        }
    
        return true;
    }

    <%-- 申請傷病分類 && 傷病分類 檢核 --%>
    function checkEvTyp() {
        if ($F("evTyp") == "1" || $F("evTyp") == "2") {
            if ($F("evTyp") == "1") {
                if (Trim($F("evCode")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evCode" />」');
                    $("evCode").focus();
                    return false;
                }
    
                if (Trim($F("criInPart1")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.criInPart1" />」');
                    $("criInPart1").focus();
                    return false;
                }
    
                if (Trim($F("criMedium")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.criMedium" />」');
                    $("criMedium").focus();
                    return false;
                }
            }
            else if ($F("evTyp") == "2") {
                if (Trim($F("evCode")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evCode" />」');
                    $("evCode").focus();
                    return false;
                }
            }
        }
        else if ($F("evAppTyp") == "1" || $F("evAppTyp") == "2") {
            if ($F("evAppTyp") == "1") {
                if (Trim($F("evCode")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evCode" />」');
                    $("evCode").focus();
                    return false;
                }
    
                if (Trim($F("criInPart1")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.criInPart1" />」');
                    $("criInPart1").focus();
                    return false;
                }
    
                if (Trim($F("criMedium")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.criMedium" />」');
                    $("criMedium").focus();
                    return false;
                }
            }
            else if ($F("evAppTyp") == "2") {
                if (Trim($F("evCode")).length == 0) {
                    alert('請輸入「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.evCode" />」');
                    $("evCode").focus();
                    return false;
                }
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
            alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.notifyForm" />」不正確');
            $("notifyForm").focus();
            return false;
        }

        return true;
    }
    
    <%-- 受傷部位 欄位重覆值檢核 --%>
    function checkCriInPartDupe() {
        for (i = 1; i <= 3; i++) {
            var criInPart = Trim($F("criInPart" + i));
            if (criInPart.length > 0) {
                for (n = i + 1; n <= 3; n++) {
                    if ($F("criInPart" + n).length > 0 && $F("criInPart" + n) == criInPart) {
                        alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.criInPart" />' + n + '」與「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.criInPart" />' + i + '」重覆');
                        $("criInPart" + n).focus();
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
                        alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.criInJnme" />' + n + '」與「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.criInJnme" />' + i + '」重覆');
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
        if (checkCriInPartDupe() && checkCriInJnmeDupe())
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
        shiftCriInJnme();
    }
    
    function chgMonNotifyingMk(){
    	if ($("monNotifyingMk").checked == true) {
        	$("monNotifyingMk").value = "Y";
        }
        else {
        	$("monNotifyingMk").value = "N";
        }      	
    }
    
    function chgIssuNotifyingMk(){
    	if ($("issuNotifyingMk").checked == true) {
        	$("issuNotifyingMk").value = "Y";
        }
        else {
        	$("issuNotifyingMk").value = "N";
        }      	
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
        if ($F("apItem") == "4" || $F("apItem") == "5") {
            if ($F("evTyp") == "1" || $F("evTyp") == "2") {
                if ($("cbxSameAsApply").checked) {
                    $("lsUbno").value = Trim($F("apUbno"));
                }
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

    function focusCriInJnme(e) {
        if ($F("evTyp") == "3" || $F("evTyp") == "4") {
            $("criInJnme1").focus();
            return false;
        }

        return true;
    }
    
    function focusCriInJnmeExt(e) {
        if (!focusCriInJnme(e)) {
            return false;
        }
        else {
            if ($F("evTyp") == "2") {
                $("criInJnme1").focus();
                return false;
            }

            return true;
        }
    }
    
    function focusApItem(e) {
        if ($F("apItem") == "7" || $F("apItem") == "8" || $("cbxSameAsApply").checked) {
            $("apItem").focus();
            return false;
        }
        else if ($F("apItem") == "4" || $F("apItem") == "5") {
            if ($F("evTyp") == "3" || $F("evTyp") == "4") {
                $("apItem").focus();
                return false;
            }
        }
        
        return true;
    }
    
    function focusFamAppMk(e) {
        if ($F("apItem") != "4" && $F("apItem") != "5") {
            $("prType").checked = false;
            $("famAppMk").focus();
            return false;
        }
        
        return true;
    }
    
    function focusLoanMk(e) {
        if ($F("apItem") != "9") {
            $("famAppMk").checked = false;
            $("loanMk").focus();
            return false;
        }

        return true;
    }
    
    function focusCloseCause(e) {
        if ($F("apItem") != "9") {
            $("famAppMk").checked = false;
            $("closeCause").focus();
            return false;
        }

        return true;
    }        

    function initAll() {
        doEvtNationTpeChange();
        doEvtNationCodeChange();
        doCloseCauseChange();
        doApItemChange();
        doEvTypChange();
        checkIdnoExist();
        changeMonNotifyingMk();
        chgMonNotifyingMk();
        chgIssuNotifyingMk();
        
        // 國籍
        Event.observe($("evtNationCode"), 'keydown', focusEvtIdnNo.bindAsEventListener(), false);
        Event.observe($("evtNationCode"), 'focus', focusEvtIdnNo.bindAsEventListener(), false);
        
        // 發放方式
        if($("isShowInterValMonth").value == 'Y'){
            if('<c:out value="${SurvivorApplicationDataUpdateForm.interValMonth}" />' != "<%=ConstantKey.BAAPPBASE_INTERVALMONTH_BY_SIXMONTH%>"){
                $("interValMonth1").checked=true;
            }else{
                $("interValMonth2").checked=true;
            }
        }
        
        // 傷病原因
        <%--
        Event.observe($("evCode"), 'keydown', focusCriInJnme.bindAsEventListener(), false);
        Event.observe($("evCode"), 'focus', focusCriInJnme.bindAsEventListener(), false);
        --%>
        
        // 受傷部位
        <%--
        Event.observe($("criInPart1"), 'keydown', focusCriInJnmeExt.bindAsEventListener(), false);
        Event.observe($("criInPart1"), 'focus', focusCriInJnmeExt.bindAsEventListener(), false);
        Event.observe($("criInPart2"), 'keydown', focusCriInJnmeExt.bindAsEventListener(), false);
        Event.observe($("criInPart2"), 'focus', focusCriInJnmeExt.bindAsEventListener(), false);
        Event.observe($("criInPart3"), 'keydown', focusCriInJnmeExt.bindAsEventListener(), false);
        Event.observe($("criInPart3"), 'focus', focusCriInJnmeExt.bindAsEventListener(), false);
        --%>
        
        // 媒介物
        <%--
        Event.observe($("criMedium"), 'keydown', focusCriInJnmeExt.bindAsEventListener(), false);
        Event.observe($("criMedium"), 'focus', focusCriInJnmeExt.bindAsEventListener(), false);
        --%>
        
        // 事故發生單位保險證號
        Event.observe($("lsUbno"), 'keydown', focusApItem.bindAsEventListener(), false);
        Event.observe($("lsUbno"), 'focus', focusApItem.bindAsEventListener(), false);
        
        // 先核普通

        Event.observe($("prType"), 'keydown', focusFamAppMk.bindAsEventListener(), false);
        Event.observe($("prType"), 'click', focusFamAppMk.bindAsEventListener(), false);
        Event.observe($("prType"), 'focus', focusFamAppMk.bindAsEventListener(), false);          
        
        // 符合第63條之1第3項

        <c:choose>
            <c:when test="${caseData.containCheckMarkLS}">
                Event.observe($("famAppMk"), 'keydown', focusLoanMk.bindAsEventListener(), false);
                Event.observe($("famAppMk"), 'click', focusLoanMk.bindAsEventListener(), false);
                Event.observe($("famAppMk"), 'focus', focusLoanMk.bindAsEventListener(), false);
            </c:when>
            <c:otherwise>
                Event.observe($("famAppMk"), 'keydown', focusCloseCause.bindAsEventListener(), false);
                Event.observe($("famAppMk"), 'click', focusCloseCause.bindAsEventListener(), false);
                Event.observe($("famAppMk"), 'focus', focusCloseCause.bindAsEventListener(), false);
            </c:otherwise>
        </c:choose>

        var famAppMk = "<c:out value="${sessionScope.SurvivorApplicationDataUpdateForm.famAppMkString}" />";

        if ($F("apItem") == "9" && (famAppMk == "Y" || famAppMk == "1" || famAppMk == "2" || famAppMk == "3" || famAppMk == "4"))
            $("famAppMk").checked = true;
        else
            $("famAppMk").checked = false;
                      
        if (Trim($F("notifyForm")).length == 0)
            $("notifyForm").value = "999";
        
        var monNotifyingMk = "<c:out value="${sessionScope.SurvivorApplicationDataUpdateForm.monNotifyingMk}" />";
        if (monNotifyingMk == "Y")
        	$("monNotifyingMk").checked = true;
        else
        	$("monNotifyingMk").checked = false;
        
        var issuNotifyingMk = "<c:out value="${sessionScope.SurvivorApplicationDataUpdateForm.issuNotifyingMk}" />";
        if (issuNotifyingMk == "Y")
        	$("issuNotifyingMk").checked = true;
        else
        	$("issuNotifyingMk").checked = false;
            
        $("evtNationCode").style.background = inputBlurBackgroundColor;
        $("evtIdnNo").focus();
    }
    
    function checkCutAmt(){
        if (parseNumber($F("cutAmt")) < 0) {
            alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.update.survivor.cutAmt" />」不得小於0');
            $("cutAmt").focus();
            return false;
        }

        return true;
    }
    
    function checkFields() {    	
        if (checkSexAndNation() && checkEvtIdnNo() && checkInterValMonth() && checkJudgeDate() && checkAppDate() && checkEvtDieDate() && checkEvtBrDate() && checkApItem() && checkCutAmt() && checkEvTyp() && checkNotifyForm() && checkDupeValue()  && chkEvtBrDate())
        	{
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
        	}
            //return true;
        else
            return false;
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
    	var btnDelete = document.getElementById('btnDelete');
    	// 創建計時器 返回計時器ID 
    	var intervalId = setInterval(function () { 
    		btnUpdate.disabled = true;
    		btnPrint.disabled = true;
    		btnBack.disabled = true;
    		btnDelete.disabled = true;
    		}, 1000); 
    	}  
	// Added by JohnsonHuang 20200115 [Begin]
  	//外國人身分證號碼自動帶入
    function autoForeignEvtSex(){
    	var secondText = $("evtIdnNo").value.substring(1,2);
		if($("evtIdnNo").value.length==10){
    	if(getRadioValue("evtNationTpe") == "2" && $("evtIdnNo").value.length==10 && (getRadioValue("evtSex") == null || getRadioValue("evtSex") == "")){
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
        <html:form action="/survivorApplicationDataUpdate" method="post" onsubmit="return validateSurvivorApplicationDataUpdateForm(this);">
        
        <fieldset>
            <legend>&nbsp;遺屬年金案件資料更正&nbsp;</legend>

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
                            <input type="button" id="btnConfirm" name="btnConfirm" tabindex="999" class="button" value="確　認" onclick="checkIdnoExist();$('page').value='2'; $('method').value='doSave'; doUbno(); doShiftFields(); chgMonNotifyingMk(); chgIssuNotifyingMk(); if (document.SurvivorApplicationDataUpdateForm.onsubmit() && checkFields()){document.SurvivorApplicationDataUpdateForm.submit();controlButton();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <c:if test="${caseData.showCancelButton}">
                            <acl:checkButton buttonName="btnDelete">
                                <input type="button" id="btnDelete" name="btnDelete" tabindex="1000" class="button" value="註　銷" onclick="$('page').value='2'; $('method').value='doDelete'; if (confirm('確定註銷？')) {document.SurvivorApplicationDataUpdateForm.submit();} else {return false;}" />
                            </acl:checkButton>
                            &nbsp;
                        </c:if>
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" id="btnPrint" name="btnPrint" tabindex="1001" class="button_120" value="檢視受理/審核清單" onclick="$('page').value='2'; $('method').value='doPrint'; document.SurvivorApplicationDataUpdateForm.submit();">
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" id="btnBack" name="btnBack" tabindex="1002" class="button" value="返　回" onclick="if (confirm('確定離開？')) { location.href='<c:url value="/enterSurvivorApplicationDataUpdate.do?parameter=enterSurvivorApplicationDataUpdate"/>'; } else {return false;}">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="33%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <bean:write name="SurvivorApplicationDataUpdateForm" property="apNoString" />
                                </td>
                                <td width="33%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    遺屬年金
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">處理狀態：</span>
                                    <bean:write name="SurvivorApplicationDataUpdateForm" property="procStatString" />
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
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">國籍別：</span>
                                    <span class="formtxt">
                                        <html:radio styleId="evtNationTpe" property="evtNationTpe" value="1" tabindex="1" onblur="doEvtNationTpeChange(); doEvtNationTpeAfterChange();autoForeignEvtSex();" onclick="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" onchange="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" />本國
                                        <html:radio styleId="evtNationTpe" property="evtNationTpe" value="2" tabindex="2" onblur="doEvtNationTpeChange(); doEvtNationTpeAfterChange();autoForeignEvtSex();" onclick="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" onchange="doEvtNationTpeChange(); doEvtNationTpeAfterChange();" />外籍
                                        <html:hidden styleId="oldEvtNationTpe" property="oldEvtNationTpe" />
                                    </span>
                                </td>
                                <td width="33%" id="iss">
                                    <div id="divSex">
                                        &nbsp;&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">性　別：</span>
                                        <span class="formtxt">
                                            <html:radio styleId="evtSex" property="evtSex" value="1" tabindex="3" />男

                                        </span>
                                        <span class="formtxt">
                                            <html:radio styleId="evtSex" property="evtSex" value="2" tabindex="4" />女

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
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:choose>
                                        <c:when test="${caseData.canModifyEvtName}">
                                            <html:text styleId="evtName" property="evtName" styleClass="textinput" size="40" maxlength="50" tabindex="7" />
                                        </c:when>
                                        <c:otherwise>
                                            <html:text styleId="evtName" property="evtName" styleClass="textinput" size="40" maxlength="50" onclick="this.blur(); $('evtIdnNo').focus();" onfocus="this.blur(); $('evtIdnNo').focus();" />
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${caseData.dupeIdnNoMk eq '1' or caseData.dupeIdnNoMk eq '2'}">
                                        <input type="button" id="btnChooseEvtName" name="btnChooseEvtName" tabindex="8" class="button_120" value="選擇事故者姓名" onclick="$('page').value='2'; $('method').value='doSelectEvtName'; document.SurvivorApplicationDataUpdateForm.submit();" />
                                    </c:if>
                                    <c:if test="${caseData.containCheckMarkBD}">
                                        <span class="formtxt">
                                        (戶籍姓名：<html:text styleId="name" property="name" styleClass="textinput" size="50" maxlength="50" onclick="this.blur(); $('evtIdnNo').focus();" onfocus="this.blur(); $('evtIdnNo').focus();" />)
                                        </span>
                                    </c:if>
                                    <html:hidden styleId="oldEvtName" property="oldEvtName" />
                                </td>
                                <td id="iss" colspan="1">
                                    <html:checkbox styleId="monNotifyingMk" property="monNotifyingMk" value="" tabindex="80" onclick="chgMonNotifyingMk();" />
                                    <span class="issuetitle_L_down">寄發月通知表</span>
                                    <html:hidden styleId="oldMonNotifyingMk" property="oldMonNotifyingMk" />
                                </td>                                
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <html:text styleId="evtIdnNo" property="evtIdnNo" styleClass="textinput" size="15" maxlength="25" tabindex="9" onkeyup="this.value = asc(this.value).toUpperCase(); getCvldtlName();" onblur="this.value = asc(this.value).toUpperCase(); getCvldtlName(); setEvtSex();checkIdnoExist();autoForeignEvtSex();" />
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
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">死亡日期：</span>
                                    <html:text styleId="evtDieDate" property="evtDieDate" styleClass="textinput" size="10" maxlength="7" tabindex="12" />
                                    <html:hidden styleId="oldEvtDieDate" property="oldEvtDieDate" />
                                </td>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">判決日期：</span>
                                    <html:text styleId="judgeDate" property="judgeDate" styleClass="textinput" size="10" maxlength="7" tabindex="12" />
                                    <html:hidden styleId="oldJudgeDate" property="oldJudgeDate" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <html:text styleId="appDate" property="appDate" styleClass="textinput" size="10" maxlength="7" tabindex="13" />
                                    <html:hidden styleId="oldAppDate" property="oldAppDate" />
                                </td>
                                <td id="iss">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">申請單位保險證號：</span>
                                    <html:text styleId="apUbno" property="apUbno" styleClass="textinput" size="10" maxlength="8" tabindex="14" onblur="doUbno();" onkeyup="changeMonNotifyingMk();" />
                                    <html:hidden styleId="oldApUbno" property="oldApUbno" />
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">事故發生保險證號：</span>
                                    <html:multibox styleId="cbxSameAsApply" property="cbxSameAsApply" value="Y" tabindex="15" onclick="doCbxSameAsApplyChange();" />同申請單位

                                    <html:text styleId="lsUbno" property="lsUbno" styleClass="textinput" size="10" maxlength="8" tabindex="16" onblur="doLsUbnoChange();" />
                                    <html:hidden styleId="oldLsUbno" property="oldLsUbno" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">申請項目：</span>
                                    <html:select styleId="apItem" property="apItem" tabindex="17" onchange="doApItemChange();" onblur="doApItemChange();">
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="apItemOptionList" />
                                    </html:select>
                                    <div id="divApItem">&nbsp;</div>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">申請傷病分類：</span>
                                    <html:select styleId="evAppTyp" property="evAppTyp" tabindex="18" >
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="evTypOptionList" />
                                    </html:select>
                                    <html:hidden styleId="oldEvAppTyp" property="oldEvAppTyp" />
                                </td>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">傷病分類：</span>
                                    <html:select styleId="evTyp" property="evTyp" tabindex="20" onchange="doEvTypChange();doEvTypAfterChange();" onblur="doEvTypChange();">
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="evTypOptionList" />
                                    </html:select>
                                    <html:hidden styleId="oldEvTyp" property="oldEvTyp" />
                                </td>
                                <td id="iss">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">傷病原因：</span>
                                    <html:text styleId="evCode" property="evCode" styleClass="textinput" size="5" maxlength="2" tabindex="22" />
                                    <html:hidden styleId="oldEvCode" property="oldEvCode" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    &nbsp;&nbsp;&nbsp;
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
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">媒 介 物：</span>
                                    <html:text styleId="criMedium" property="criMedium" styleClass="textinput" size="3" maxlength="3" tabindex="30" />
                                    <html:hidden styleId="oldCriMedium" property="oldCriMedium" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">國際疾病代碼：</span>
                                    <html:text styleId="criInJnme1" property="criInJnme1" styleClass="textinput" size="30" maxlength="30" tabindex="36" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJnme2" property="criInJnme2" styleClass="textinput" size="30" maxlength="30" tabindex="37" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJnme3" property="criInJnme3" styleClass="textinput" size="30" maxlength="30" tabindex="38" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    &nbsp;
                                    <html:text styleId="criInJnme4" property="criInJnme4" styleClass="textinput" size="30" maxlength="30" tabindex="39" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = asc(this.value).toUpperCase();" />
                                    <html:hidden styleId="oldCriInJnme1" property="oldCriInJnme1" />
                                    <html:hidden styleId="oldCriInJnme2" property="oldCriInJnme2" />
                                    <html:hidden styleId="oldCriInJnme3" property="oldCriInJnme3" />
                                    <html:hidden styleId="oldCriInJnme4" property="oldCriInJnme4" />
                                </td>
                                <td id="iss">
                                    &nbsp;&nbsp;<span class="issuetitle_L_down">應扣失能金額：</span>
                                    <html:text styleId="cutAmt" property="cutAmt" styleClass="textinput" size="9" maxlength="7" tabindex="43" onblur="this.value=asc(this.value);"/>
                                    <html:hidden styleId="oldCutAmt" property="oldCutAmt" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">核定格式：</span>
                                    <html:text styleId="notifyForm" property="notifyForm" styleClass="textinput" size="5" maxlength="3" tabindex="47" />
                                    <html:hidden styleId="oldNotifyForm" property="oldNotifyForm" />
                                </td>
                                <td id="iss">
                                    <html:multibox styleId="prType" property="prType" value="Y" tabindex="48" />
                                    <span class="issuetitle_L_down">先核普通</span>
                                    <html:hidden styleId="oldPrType" property="oldPrType" />
                                </td>
                                <td id="iss">
                                    <html:multibox styleId="famAppMk" property="famAppMk" value="Y" tabindex="49" />
                                    <span class="issuetitle_L_down">符合第63條之1第3項 (<c:out value="${sessionScope.SurvivorApplicationDataUpdateForm.famAppMkString}" />)</span>
                                    <html:hidden styleId="oldFamAppMk" property="oldFamAppMk" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <table border="0" class="px13">
                                        <tr>
                                            <td border="0" valign="top" width="25%">
                                                &nbsp;&nbsp;
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
                                        <html:multibox styleId="loanMk" property="loanMk" value="1" tabindex="51" />
                                        <span class="issuetitle_L_down">不須抵銷紓困貸款</span>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">結案原因：</span>
                                    <html:select styleId="closeCause" property="closeCause" tabindex="52" onchange="doCloseCauseChange();" onblur="doCloseCauseChange();">
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="scloseCauseOptionList" />
                                    </html:select>
                                    <div id="divChoiceYm">
                                        <span class="formtxt">擇領起月：</span>
                                        <html:text styleId="choiceYm" property="choiceYm" styleClass="textinput" size="10" maxlength="5" tabindex="50" />
                                    </div>
                                    <html:hidden styleId="oldCloseCause" property="oldCloseCause" />
                                    <html:hidden styleId="oldChoiceYm" property="oldChoiceYm" />
                                </td>
                                <td id="iss" colspan="1">
                                	<c:if test="${caseData.caseTyp eq '2'}">
                                    	<html:checkbox styleId="issuNotifyingMk" property="issuNotifyingMk" value="" tabindex="80" onclick="chgIssuNotifyingMk();" />
                                    	<span class="issuetitle_L_down">寄發核定通知書</span>
                                	</c:if>
                                    <c:if test="${caseData.caseTyp ne '2'}">
                                        <input type='hidden' name='issuNotifyingMk' value='' >&nbsp;
                                    </c:if>
                                    <html:hidden styleId="oldIssuNotifyingMk" property="oldIssuNotifyingMk" />
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
