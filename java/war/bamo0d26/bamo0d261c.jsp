<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustBenDataCase" %>
<%@ page import="tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustBenDetailDataCase" %>
<bean:define id="caseData" name="<%=ConstantKey.SURVIVOR_CHECK_MARK_LEVEL_ADJUST_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D261C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script language="javascript" type="text/javascript">
    <!--
    <%-- 調整起迄年月 檢核 --%>
    <%-- [ --%>
    function checkValidYearMonth() {
        var regValisYm = /valisYm/i;
        var regValieYm = /valieYm/i;

        var inputFieldList = document.getElementsByTagName("input");

        for (i = 0; i < inputFieldList.length; i++) { // ... [
            var inputObj = inputFieldList[i];

            if (inputObj.type != "text")
                continue;

            if (regValisYm.test(inputObj.name) || regValieYm.test(inputObj.name)) { // ... [
                if ($F(inputObj) != "") { // ... [
                    // 若非空白, 進行年月格式檢核
                    if (!isValidDate($F(inputObj) + "01")) {
                        alert("「調整起迄年月」格式錯誤");
                        inputObj.focus();
                        inputObj.select();
                        return false;
                    }
                    
                    // 起月 須大等於 給付年月
                    if (regValisYm.test(inputObj.name)) {
                        var key = inputObj.name.replace(regValisYm, "");

                        if (Trim($F(inputObj)) != "") {
                            if (parseNumber($F(inputObj)) < parseNumber($F("listPayYm" + key))) {
                                alert("「調整年月 - 起」不得小於給付年月");
                                inputObj.focus();
                                inputObj.select();
                                return false;
                            }
                        }
                    }

                    // 如果 迄月 不為空值, 則 起月 必須有值
                    if (regValieYm.test(inputObj.name)) {
                        var key = inputObj.name.replace(regValieYm, "");

                        if (Trim($F("valisYm" + key) ) == "") {
                            alert("「調整年月 - 迄」有值則「調整年月 - 起」不得為空值");
                            $("valisYm" + key).focus();
                            $("valisYm" + key).select();
                            return false;
                        }
                        
                        // 迄月 須大等於 給付年月
                        if (Trim($F(inputObj)) != "") {
                            if (parseNumber($F(inputObj)) < parseNumber($F("listPayYm" + key))) {
                                alert("「調整年月 - 迄」不得小於給付年月");
                                inputObj.focus();
                                inputObj.select();
                                return false;
                            }
                        }

                        // 判斷起迄範圍是否正確
                        if (isValidDate($F("valisYm" + key) + "01")) {
                            if (parseNumber($F("valisYm" + key)) > parseNumber($F(inputObj)) ) {
                                alert("「調整起迄年月」範圍錯誤");
                                $("valisYm" + key).focus();
                                $("valisYm" + key).select();
                                return false;
                            }
                        }
                    }
                } // ] ... if ($F(inputObj) != "")
            } // ] ... if (regValisYm.test(inputObj.name) || regValieYm.test(inputObj.name))
        } // ] ... for (i = 0; i < inputFieldList.length; i++)

        return true;
    }
    <%-- ] --%>
    function checkRegetCipbMk(apNo) {
    	var regetCipbMk = "";
        if($("regetCipbMk").checked==true){
        	regetCipbMk = "1";        
        }else if($("regetCipbMk").checked==false){
        	regetCipbMk = "";      
        }
        
        updateCommonAjaxDo.setRegetCipbMk(apNo, regetCipbMk);            
    }
    function checkFields() {
        //欄位檢查前先將所有的受益人資料列出來
        var payYm = "";
        var seqNo = "";
        $("payYmOption").value = "";
        $("seqNoOption").value = "";
        DWREngine.setAsync(false);
        updateCommonAjaxDo.getBenFilterdataList(payYm, seqNo, setBenData);
        
        if (checkValidYearMonth())
            return true;
        else
            return false;
    }
    
    <%-- 起迄年月 相關函數--%>
    <%-- [ --%>
    //調整起迄年月預設值
    function setValiYm(id,chkCode,payYm){
       if(chkCode == 'BQ' || chkCode == '12' || chkCode == '06' || chkCode == 'AF'){
           if($("valisYm" + id) != null && $("valisYm" + id).disabled != true)
              $("valisYm" + id).value = payYm;
           if($("valieYm" + id) != null && $("valieYm" + id).disabled != true)   
              $("valieYm" + id).value = payYm;
       }else{
           if($("valisYm" + id) != null && $("valisYm" + id).disabled != true)
              $("valisYm" + id).value = payYm;
       }
       //更新資料到CASEDATA中
       updateCaseData(id);
    }
    
    //清除起迄年月預設值
    function cleanValiYm(id){
        if($("valisYm" + id) != null)
            $("valisYm" + id).value = '';
        if($("valieYm" + id) != null)
            $("valieYm" + id).value = '';
       //更新資料到CASEDATA中
       updateCaseData(id);     
    }
     <%-- ] --%>
     
    <%-- 根據下拉選單過濾受益人資料 --%> 
    <%-- [ --%>
    // Ajax for 過濾受益人資料
    function filterBenData() {
        var payYm = $("payYmOption").value;
        var seqNo = $("seqNoOption").value;
        
        updateCommonAjaxDo.getBenFilterdataList(payYm, seqNo, setBenData);
    }
    function setBenData(benDataList) {
        dwr.util.removeAllRows("benIssuDataTable");
        $("issuDataSpan").innerHTML = "";
        var innerHTMLStr = '<table cellpadding="5" cellspacing="0" width="100%" class="px13">';
          innerHTMLStr += '<table width="100%" border="0" cellspacing="1" cellpadding="0" class="px13">';
          innerHTMLStr += '<tr align="center">';
          innerHTMLStr += '<td width="20%" class="issuetitle_L">給付年月</td>';
          innerHTMLStr += '<td width="30%" class="issuetitle_L">編審註記</td>';
          innerHTMLStr += '<td width="10%" class="issuetitle_L">嚴重程度</td>';
          innerHTMLStr += '<td width="20%" class="issuetitle_L">程度調整</td>';
          innerHTMLStr += '<td width="20%" class="issuetitle_L">調整起迄年月</td>';
          innerHTMLStr += '</tr>';
          innerHTMLStr += '</table>';
        if(benDataList.length>0){
          for(var i=0;i<benDataList.length;i++){
            var benData = benDataList[i];
            if(i%2 == 0){
              innerHTMLStr += '<table width="100%" border="0" cellspacing="1" cellpadding="0" class="px13">';
            }else {
              innerHTMLStr += '<table width="100%" border="0" cellspacing="1" cellpadding="0" class="px13" bgcolor="#E8E8FF">';
            }
            innerHTMLStr += '<tr align="center">';
            innerHTMLStr += '<td align="center" colspan="5" id="iss" >';
            innerHTMLStr += '<table width="80%" border="0" cellspacing="2" cellpadding="2" class="px13">';
            innerHTMLStr += '<tr >';
            innerHTMLStr += '<td>受款人序：'+ (i+1) +' </td>'; 
            innerHTMLStr += '<td>受款人姓名：'+getDefaultString(benData.benName)+' </td>';
            innerHTMLStr += '<td>受款人身分證號：'+getDefaultString((benData.benIdnNo))+' </td>';
            innerHTMLStr += '<td>關　係：'+getDefaultString((benData.benEvtRel))+'-'+getDefaultString((benData.benEvtRelString))+' </td>';
            innerHTMLStr += '</tr>';
            innerHTMLStr += '</table>';
            innerHTMLStr += '</td>';
            innerHTMLStr += '</tr>';
            innerHTMLStr += '</table>';
            
            var benDetailList = benData.detailListArray;
            if(i%2 == 0){
              innerHTMLStr += '<table width="100%" border="0" cellspacing="5" cellpadding="0" class="px13" >';
            }else{
              innerHTMLStr += '<table width="100%" border="0" cellspacing="5" cellpadding="0" class="px13" bgcolor="#E8E8FF">';
            }
            for(var j=0;j<benDetailList.length;j++){
              var benDetailData = benDetailList[j];
              innerHTMLStr += '<tr>';
              //======================================================================================================
              // 給付年月
              innerHTMLStr += '<td width="20%" class="issueinfo_C">';
              innerHTMLStr += getDefaultString(benDetailData.payYmShortString);
              innerHTMLStr += "<input type='hidden' id='listPayYm+"+benDetailData.baChkFileId+"' name='listPayYm"+benDetailData.baChkFileId+"' value='"+benDetailData.PayYmShortString+"' >";
              innerHTMLStr += '</td>';
              //======================================================================================================
              // 編審註記
              innerHTMLStr += '<td width="30%" class="issueinfo">';
              innerHTMLStr += getDefaultString(benDetailData.chkCode)+'-'+getDefaultString(benDetailData.chkResult);
              innerHTMLStr += '</td>';
              //======================================================================================================
              // 嚴重程度
              innerHTMLStr += '<td width="10%" class="issueinfo">';
              innerHTMLStr += getDefaultString(benDetailData.chkCodePreString);
              innerHTMLStr += '</td>';
              //======================================================================================================
              // 程度調整
              innerHTMLStr += '<td class="issueinfo_C">';
              innerHTMLStr += '<span class="formtxt">';
              if((benDetailData.chkCode == '01' && ((benData.benDieDate != benData.appDate) || (benData.benDieDate == benData.appDate && benDetailData.payYm > benData.benDieYm)) ) || (benDetailData.chkCode == '09' && benData.benEvtRel == '2')){
                if(benDetailData.chkCodePost == 'W'){
                  innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="W" checked disabled="disabled" >W-警告';
                innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="O" disabled="disabled" >O-可穿透';
                }else{
                  innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="W" disabled="disabled" >W-警告';
                  innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="O" checked disabled="disabled" >O-可穿透';
                }
              }else{
                //畫面有更改過 用adjLevel判斷
                if(benDetailData.adjLevel !=null && benDetailData.adjLevel =='W'){
                  innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="W" checked onclick="setValiYm('+"'"+benDetailData.baChkFileId+"','"+benDetailData.chkCode+"','"+benDetailData.payYmShortString+"'"+');" >W-警告';
                  innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="O" onclick="cleanValiYm('+"'"+benDetailData.baChkFileId+"'"+');" >O-可穿透';
                }else if(benDetailData.adjLevel !=null && benDetailData.adjLevel =='O'){
                  innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="W" onclick="setValiYm('+"'"+benDetailData.baChkFileId+"','"+benDetailData.chkCode+"','"+benDetailData.payYmShortString+"'"+');" >W-警告';
                  innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="O" checked onclick="cleanValiYm('+"'"+benDetailData.baChkFileId+"'"+');" >O-可穿透';
                }
                //畫面未更改過 用chkCodePost判斷
                else if(benDetailData.chkCodePost == 'W'){
                  innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="W" checked onclick="setValiYm('+"'"+benDetailData.baChkFileId+"','"+benDetailData.chkCode+"','"+benDetailData.payYmShortString+"'"+');" >W-警告';
                  innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="O" onclick="cleanValiYm('+"'"+benDetailData.baChkFileId+"'"+');" >O-可穿透';
                }else{
                  innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="W" onclick="setValiYm('+"'"+benDetailData.baChkFileId+"','"+benDetailData.chkCode+"','"+benDetailData.payYmShortString+"'"+');" >W-警告';
                  innerHTMLStr += '<input type="radio" name="adjLevel'+benDetailData.baChkFileId+'" value="O" checked onclick="cleanValiYm('+"'"+benDetailData.baChkFileId+"'"+');" >O-可穿透';
                }
              }   
              innerHTMLStr += '</span>';                                
              innerHTMLStr += '</td>';
              //======================================================================================================
              //調整起迄年月
              innerHTMLStr += '<td class="issueinfo_C">';
              if(benDetailData.chkCodePre == 'O' && benDetailData.maxMk == '1'){
                if(benDetailData.chkCode == '01' && ((benData.benDieDate != benData.appDate) || (benData.benDieDate == benData.appDate && benDetailData.payYm > benData.benDieYm))){
                  innerHTMLStr += '<input name="valisYm'+benDetailData.baChkFileId+'" type="text" class="textinput" size="10" maxlength="5" value="'+benDetailData.valisYmString+'" disabled="disabled">';
                  innerHTMLStr += '&nbsp;~&nbsp;';
                  innerHTMLStr += '<input name="valieYm'+benDetailData.baChkFileId+'" type="text" class="textinput" size="10" maxlength="5" value="'+benDetailData.valieYmString+'" disabled="disabled">';
                }else{
                   if(benDetailData.chkCode == '09' && benData.benEvtRel == '2'){
                     innerHTMLStr += '<input name="valisYm'+benDetailData.baChkFileId+'" type="text" class="textinput" size="10" maxlength="5" value="'+benDetailData.valisYmString+'" disabled="disabled">';
                     innerHTMLStr += '&nbsp;~&nbsp;';
                     innerHTMLStr += '<input name="valieYm'+benDetailData.baChkFileId+'" type="text" class="textinput" size="10" maxlength="5" value="'+benDetailData.valieYmString+'" disabled="disabled">';
                   }else{
                     innerHTMLStr += '<input name="valisYm'+benDetailData.baChkFileId+'" type="text" class="textinput" size="10" maxlength="5" value="'+benDetailData.valisYmString+'" onchange="checkValisYm('+"'"+benDetailData.baChkFileId+"','"+benDetailData.payYmShortString+"'"+');" >';
                     innerHTMLStr += '&nbsp;~&nbsp;';
                     innerHTMLStr += '<input name="valieYm'+benDetailData.baChkFileId+'" type="text" class="textinput" size="10" maxlength="5" value="'+benDetailData.valieYmString+'" onchange="updateCaseData('+"'"+benDetailData.baChkFileId+"'"+');" >';
                   }
                }
              }else{
                innerHTMLStr += '&nbsp;';
              }
              innerHTMLStr += '</td>';
              //======================================================================================================                      
              innerHTMLStr += '</tr>';                     
            }
            innerHTMLStr += '</table>';
          }                         
           
        }
        innerHTMLStr +='</table>';
        $("issuDataSpan").innerHTML = innerHTMLStr;
    }
    
    //字串處理
    //如果傳入值為null, 回傳" "；否則回傳原始傳入值
    //防止資料底線消失
    function getDefaultString(strValue){
        if(strValue=="" || strValue==" " || strValue==null){
            return "&nbsp;";
        }else{
            return strValue;
        }
    }
     <%-- ] --%>
     
     <%-- 將畫面更改的資料 更新到CASEDATA中 --%>
     <%-- [ --%>
     //ajax for 將畫面更改的資料 更新到CASEDATA中
     function updateCaseData(id){
      
         var level = "";
         var valisYm = "";
         var valieYm = "";
         if($("adjLevel" + id).checked==true){
           level = "W";
         }else{
           level = "O";
         }
         if($("valisYm" + id)!=null && $("valisYm" + id).value!=""){
           valisYm = $("valisYm" + id).value;
         }
         if($("valieYm" + id)!=null && $("valieYm" + id).value!=""){
           valieYm = $("valieYm" + id).value;
         }
         updateCommonAjaxDo.setCheckMarkLevel(id,level,valisYm,valieYm);
       
     }
     
     //調整起年月預設值
    function checkValisYm(id,payYm){
       if(Trim($F("valisYm" + id) ) != "" && Trim($F("valisYm" + id) ) != payYm){
         alert("調整起始年月只能等於該給付年月");
         $("valisYm" + id).value = payYm;
       }
       updateCaseData(id);
    }
     <%-- ] --%>
     
     <%-- 更新事故者資料  點選某一註記 則全部給付年月 都要一起改變 --%>
     <%-- [ --%>
     function setEvtData(chkCodeReplaceEscap,level){
       updateCommonAjaxDo.getEvtDetailDataCase({callback:function(evtDetailList) { 
                                                  setEvtDetail(evtDetailList, chkCodeReplaceEscap,level); 
                                                  } 
                                                });
     }
     function setEvtDetail(evtDetailList,chkCodeReplaceEscap,level){
       for(var i=0;i<evtDetailList.length;i++){
          if(chkCodeReplaceEscap == evtDetailList[i].chkCodeReplaceEscap){
             var radios = document.getElementsByName('adjLevel'+ evtDetailList[i].baChkFileId);
             if(radios!=null){
               for(var j=0;j<radios.length;j++){
                 if(radios[j].value == level){
                     radios[j].checked = true;
                 }else{
                     radios[j].checked = false;
                 }
               }
               if("W"==level){
                 setValiYm(evtDetailList[i].baChkFileId,evtDetailList[i].chkCode,evtDetailList[i].payYmShortString);
               }else if("O"==level){
                 cleanValiYm(evtDetailList[i].baChkFileId);
               }
             }
          }
       }
     }
     <%-- ] --%>
          
     function controlButton() { 
     	// 獲得該button對象 
     	var btnConfirm = document.getElementById('btnConfirm'); 
     	var btnPrint = document.getElementById('btnPrint');
     	var btnBack = document.getElementById('btnBack');
     	// 創建計時器 返回計時器ID 
     	var intervalId = setInterval(function () { 
     		btnConfirm.disabled = true;
     		btnPrint.disabled = true;
     		btnBack.disabled = true;
     		}, 1000); 
     	}        
     
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">
    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/survivorCheckMarkLevelAdjust" method="post">
        
        <fieldset>
            <legend>&nbsp;遺屬年金編審註記程度調整&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="8">
                        <html:hidden styleId="page" property="page" value="2" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" id="btnConfirm" name="btnConfirm" tabindex="999" class="button" value="確　認" onclick="$('page').value='2'; $('method').value='doSave'; if (checkFields()){document.SurvivorCheckMarkLevelAdjustForm.submit();controlButton();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" id="btnPrint" name="btnPrint" tabindex="1000" class="button_120" value="檢視受理/審核清單" onclick="$('page').value='2'; $('method').value='doPrint'; document.SurvivorCheckMarkLevelAdjustForm.submit();">
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" id="btnBack" name="btnBack" tabindex="1001" class="button" value="返　回" onclick="if (confirm('確定離開？')) { location.href='<c:url value="/enterSurvivorCheckMarkLevelAdjust.do?parameter=enterSurvivorCheckMarkLevelAdjust"/>'; } else {return false;}">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0" cellspacing="2" cellpadding="2" class="px13">
                            <tr>
                                <td width="50%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${caseData.apNoString}" />
                                </td>
                                <td width="50%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    遺屬年金
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" align="center" class="table1">
                        <table width="100%" cellpadding="3" cellspacing="5" class="px13">
                            <tr>
                                <td colspan="4" class="issuetitle_L">
                                    <span class="systemMsg">▲</span>&nbsp;案件資料
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${caseData.evtName}" />
                                </td>
                                <td id="iss" width="25%">
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${caseData.evtIdnNo}" />
                                </td>
                                <td id="iss" width="25%">
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${caseData.evtBrDateString}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" width="25%">
                                    <span class="issuetitle_L_down">核定年月：</span>
                                    <c:out value="${caseData.issuYmString}" />
                                </td>
                                <td id="iss" width="25%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${caseData.appDateString}" />
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">事故日期：</span>
                                    <c:out value="${caseData.evtJobDateString}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" width="25%">
                                    <span class="issuetitle_L_down">處理狀態：</span>
                                    <c:out value="${caseData.procStatString}" />
                                </td>
                                <td id="iss" width="25%">
                                    <span class="issuetitle_L_down">資料別：</span>
                                    <c:out value="${caseData.caseTypString}" />
                                </td>
                                <td id="iss" colspan="2">
                                	<c:if test="${caseData.regetCipbMk eq '1'}">
                                    	<input type="checkbox" id="regetCipbMk" name="regetCipbMk" onclick="checkRegetCipbMk('<c:out value="${caseData.apNo}" />');" checked="true"><span class="issuetitle_L_down">重讀CIPB</span>
                                 	</c:if>
                                	<c:if test="${caseData.regetCipbMk ne '1'}">
                                    	<input type="checkbox" id="regetCipbMk" name="regetCipbMk" onclick="checkRegetCipbMk('<c:out value="${caseData.apNo}" />');"><span class="issuetitle_L_down">重讀CIPB</span>
                                 	</c:if>                                                               	                              
                                </td>                                                                                               
                            </tr>
                            <table cellpadding="1" cellspacing="3" width="100%" class="px13">
                            <c:if test="${caseData.evtDataInBenList.detailListSize gt 0}">
                                <tr align="center" >
                                    <td width="20%" class="issuetitle_L">給付年月</td>
                                    <td width="30%" class="issuetitle_L">編審註記</td>
                                    <td width="10%" class="issuetitle_L">嚴重程度</td>
                                    <td width="20%" class="issuetitle_L">程度調整</td>
                                    <td width="20%" class="issuetitle_L">調整起迄年月</td>
                                </tr>
                                <logic:iterate id="evtDetailData" indexId="i" name="caseData" property="evtDataInBenList.detailList">
                                <tr>
                                    <td  class="issueinfo_C">
                                        <c:out value="${evtDetailData.payYmString}" />
                                        <input type='hidden' id='listPayYm<c:out value="${evtDetailData.baChkFileId}" />' name='listPayYm<c:out value="${evtDetailData.baChkFileId}" />' value='<c:out value="${evtDetailData.payYmShortString}" />' >
                                    </td>
                                    <td class="issueinfo">
                                        <c:out value="${evtDetailData.chkCode}" />-<c:out value="${evtDetailData.chkResult}" />
                                    </td>
                                    <td class="issueinfo">
                                        <c:out value="${evtDetailData.chkCodePreString}" />
                                    </td>
                                    <td class="issueinfo_C">
                                        <c:choose>
                                            <c:when test="${evtDetailData.chkCodePre eq 'O'}">
                                                <span class="formtxt">
                                                    <label>
                                                        <input type="radio" name='adjLevel<c:out value="${evtDetailData.baChkFileId}" />' value="W" <c:if test="${evtDetailData.chkCodePost eq 'W'}">checked</c:if> onclick="setEvtData('<c:out value="${evtDetailData.chkCodeReplaceEscap}" />','W');" >W-警告
                                                        <input type="radio" name='adjLevel<c:out value="${evtDetailData.baChkFileId}" />' value="O" <c:if test="${evtDetailData.chkCodePost eq 'O'}">checked</c:if> onclick="setEvtData('<c:out value="${evtDetailData.chkCodeReplaceEscap}" />','O');">O-可穿透
                                                    </label>
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                &nbsp;
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="issueinfo_C">
                                        <c:choose>
                                            <c:when test="${evtDetailData.chkCodePre eq 'O' and evtDetailData.maxMk eq '1'}">
                                                <input name='valisYm<c:out value="${evtDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value="<c:out value="${evtDetailData.valisYmString}" />" onchange="checkValisYm(<c:out value="${evtDetailData.baChkFileId}" />,'<c:out value="${evtDetailData.payYmShortString}" />');">
                                                &nbsp;~&nbsp;
                                                <input name='valieYm<c:out value="${evtDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value="<c:out value="${evtDetailData.valieYmString}" />">
                                            </c:when>
                                            <c:otherwise>
                                                &nbsp;
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                </logic:iterate>
                            </c:if>
                            </table>
                            <tr>
                                <td colspan="4"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td >
                        <table cellpadding="1" cellspacing="3" width="98%" class="px13" align="center">
                            <tr>
                                <td colspan="5" class="issuetitle_L" width="100%"> 
                                    <span class="issuetitle_search">&#9658;</span>&nbsp;編審註記：依受款人序排序
                                </td>
                            </tr>
                             <tr>
                                <td colspan="5" class="issuetitle_L">篩選條件：給付年月&nbsp;-&nbsp;
                                    <html:select  property="payYmOption" styleClass="formtxt" onchange="filterBenData();">
                                        <html:option value="">全 選</html:option>
                                        <html:options collection="<%=ConstantKey.SURVIVOR_CHECK_MARK_LEVEL_ADJUST_PAYYM_OPTION_LIST%>" property="key" labelProperty="value" />
                                    </html:select> 
                                    &nbsp;&nbsp;受款人&nbsp;-&nbsp;
                                    <html:select  property="seqNoOption" styleClass="formtxt" onchange="filterBenData();">
                                        <html:option value="">全 選</html:option>
                                        <html:options collection="<%=ConstantKey.SURVIVOR_CHECK_MARK_LEVEL_ADJUST_SEQNO_OPTION_LIST%>" property="key" labelProperty="value" />
                                    </html:select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="5" width="100%"> 
                                    <span id="issuDataSpan" ></span>
                                </td>
                            </tr>        
                            <tbody id="benIssuDataTable" align="center" width="100%" cellpadding="3" cellspacing="5" class="px13" >
                            <c:if test="${caseData.benListSize gt 0}">
                                <tr align="center">
                                    <td width="20%" class="issuetitle_L">給付年月</td>
                                    <td width="30%" class="issuetitle_L">編審註記</td>
                                    <td width="10%" class="issuetitle_L">嚴重程度</td>
                                    <td width="20%" class="issuetitle_L">程度調整</td>
                                    <td width="20%" class="issuetitle_L">調整起迄年月</td>
                                </tr>
                                <logic:iterate id="benData" indexId="i" name="caseData" property="benDataInBenList">
                                <tr>
                                <td  colspan="5">
                                <table  cellpadding="1" cellspacing="3" width="100%" class="px13"  <%= (i%2==0)? "bgcolor='#FFFFFF'":"bgcolor='#E8E8FF'"%>>
                                <tr>
                                    <td align="center" colspan="5" id="iss">
                                        <table width="80%" border="0" cellspacing="2" cellpadding="2" class="px13" >
                                            <tr>
                                                <td>受款人序：<c:out value="${i + 1}" /></td>
                                                <td>受款人姓名：<c:out value="${benData.benName}" /></td>
                                                <td>受款人身分證號：<c:out value="${benData.benIdnNo}" /></td>
                                                <td>關　係：<c:out value="${benData.benEvtRel}" />-<c:out value="${benData.benEvtRelString}" /></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <logic:iterate id="benDetailData" indexId="n" name="benData" property="detailList">
                                <tr>
                                    <td width="20%" class="issueinfo_C">
                                        <c:out value="${benDetailData.payYmString}" />
                                        <input type='hidden' id='listPayYm<c:out value="${benDetailData.baChkFileId}" />' name='listPayYm<c:out value="${benDetailData.baChkFileId}" />' value='<c:out value="${benDetailData.payYmShortString}" />' >
                                    </td>
                                    <td width="30%" class="issueinfo">
                                        <c:out value="${benDetailData.chkCode}" />-<c:out value="${benDetailData.chkResult}" />
                                    </td>
                                    <td width="10%" class="issueinfo">
                                        <c:out value="${benDetailData.chkCodePreString}" />
                                    </td>
                                    <td width="20%" class="issueinfo_C">
                                        <c:choose>
                                            <c:when test="${benDetailData.chkCodePre eq 'O'}">
                                                <span class="formtxt">
                                                        <c:choose>
                                                            <c:when test="${benDetailData.chkCode eq '01' and ((benData.benDieDate ne benData.appDate) or (benData.benDieDate eq benData.appDate and benDetailData.payYm gt benData.benDieYm))}">
                                                                <input type="radio" name='adjLevel<c:out value="${benDetailData.baChkFileId}" />' value="W" <c:if test="${benDetailData.chkCodePost eq 'W'}">checked</c:if> disabled="disabled" >W-警告
                                                                <input type="radio" name='adjLevel<c:out value="${benDetailData.baChkFileId}" />' value="O" <c:if test="${benDetailData.chkCodePost eq 'O'}">checked</c:if> disabled="disabled" >O-可穿透
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:choose>
                                                                    <c:when test="${benDetailData.chkCode eq '09' and benData.benEvtRel eq '2'}">
                                                                		<input type="radio" name='adjLevel<c:out value="${benDetailData.baChkFileId}" />' value="W" <c:if test="${benDetailData.chkCodePost eq 'W'}">checked</c:if> disabled="disabled" >W-警告
                                                                		<input type="radio" name='adjLevel<c:out value="${benDetailData.baChkFileId}" />' value="O" <c:if test="${benDetailData.chkCodePost eq 'O'}">checked</c:if> disabled="disabled" >O-可穿透
                                                            		</c:when>
                                                            		<c:otherwise>
                                                                		<input type="radio" name='adjLevel<c:out value="${benDetailData.baChkFileId}" />' value="W" <c:if test="${benDetailData.chkCodePost eq 'W'}">checked</c:if> onclick="setValiYm('<c:out value="${benDetailData.baChkFileId}" />','<c:out value="${benDetailData.chkCode}" />','<c:out value="${benDetailData.payYmShortString}" />');" >W-警告
                                                                		<input type="radio" name='adjLevel<c:out value="${benDetailData.baChkFileId}" />' value="O" <c:if test="${benDetailData.chkCodePost eq 'O'}">checked</c:if> onclick="cleanValiYm('<c:out value="${benDetailData.baChkFileId}" />');" >O-可穿透
                                                            		</c:otherwise>
                                                           			</c:choose>
                                                            </c:otherwise>
                                                        </c:choose>
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                &nbsp;
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td width="20%" class="issueinfo_C">
                                        <c:choose>
                                            <c:when test="${(benDetailData.chkCodePre eq 'O' and benDetailData.maxMk eq '1') }">
                                               <c:choose>
                                                    <c:when test="${benDetailData.chkCode eq '01' and ((benData.benDieDate  ne benData.appDate) or (benData.benDieDate eq benData.appDate and benDetailData.payYm gt benData.benDieYm))}">
                                                        <input name='valisYm<c:out value="${benDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value='<c:out value="${benDetailData.valisYmString}" />' disabled="disabled">
                                                        &nbsp;~&nbsp;
                                                        <input name='valieYm<c:out value="${benDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value='<c:out value="${benDetailData.valieYmString}" />' disabled="disabled">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${benDetailData.chkCode eq '09' and benData.benEvtRel eq '2'}">
                                                                <input name='valisYm<c:out value="${benDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value='<c:out value="${benDetailData.valisYmString}" />' disabled="disabled">
                                                                &nbsp;~&nbsp;
                                                                <input name='valieYm<c:out value="${benDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value='<c:out value="${benDetailData.valieYmString}" />' disabled="disabled">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input name='valisYm<c:out value="${benDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value='<c:out value="${benDetailData.valisYmString}" />' onchange="checkValisYm(<c:out value="${benDetailData.baChkFileId}" />,'<c:out value="${benDetailData.payYmShortString}" />');">
                                                                &nbsp;~&nbsp;
                                                                <input name='valieYm<c:out value="${benDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value='<c:out value="${benDetailData.valieYmString}" />' onchange="updateCaseData('<c:out value="${benDetailData.baChkFileId}" />');">
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                &nbsp;
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                </logic:iterate>
                                </table>
                                </td>
                                </tr>
                                </logic:iterate>
                            </c:if>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <hr width="100%" size="1px" noshade>
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
