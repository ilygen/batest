<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.update.cases.DisabledCheckMarkLevelAdjustCase" %>
<bean:define id="caseData" name="<%=ConstantKey.DISABLED_CHECK_MARK_LEVEL_ADJUST_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D161C" />
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
        if (checkValidYearMonth())
            return true;
        else
            return false;
    }
    
    <%-- 起迄年月 相關函數--%>
    <%-- [ --%>
    //調整起迄年月預設值
    function setValiYm(id,chkCode,payYm){
       if(chkCode == 'BQ' || chkCode == '12'){
           if($("valisYm" + id) != null && $("valisYm" + id).disabled != true)
              $("valisYm" + id).value = payYm;
           if($("valieYm" + id) != null && $("valieYm" + id).disabled != true)   
              $("valieYm" + id).value = payYm;
       }else{
           if($("valisYm" + id) != null && $("valisYm" + id).disabled != true)
              $("valisYm" + id).value = payYm;
       }
    }
    //清除起迄年月預設值
    function cleanValiYm(id){
         if($("valisYm" + id) != null)
            $("valisYm" + id).value = '';
        if($("valieYm" + id) != null)
            $("valieYm" + id).value = '';
    }
    //調整起年月預設值
    function checkValisYm(id,payYm){
       if(Trim($F("valisYm" + id) ) != "" && Trim($F("valisYm" + id) ) != payYm){
         alert("調整起始年月只能等於該給付年月");
         $("valisYm" + id).value = payYm;
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
        <html:form action="/disabledCheckMarkLevelAdjust" method="post">
        
        <fieldset>
            <legend>&nbsp;失能年金編審註記程度調整&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
        
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="8">
                        <html:hidden styleId="page" property="page" value="2" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" id="btnConfirm" name="btnConfirm" tabindex="999" class="button" value="確　認" onclick="$('page').value='2'; $('method').value='doSave'; if (checkFields()){document.DisabledCheckMarkLevelAdjustForm.submit();controlButton();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" id="btnPrint" name="btnPrint" tabindex="1000" class="button_120" value="檢視受理/審核清單" onclick="$('page').value='2'; $('method').value='doPrint'; document.DisabledCheckMarkLevelAdjustForm.submit();">
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" id="btnBack" name="btnBack" tabindex="1001" class="button" value="返　回" onclick="if (confirm('確定離開？')) { location.href='<c:url value="/enterDisabledCheckMarkLevelAdjust.do?parameter=enterDisabledCheckMarkLevelAdjust"/>'; } else {return false;}">
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
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                            <tr>
                                <td colspan="4" class="issuetitle_L">
                                    <span class="systemMsg">▲</span>&nbsp;案件資料
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" width="25%">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${caseData.evtName}" />
                                </td>
                                <td id="iss" width="25%">
                                    <span class="issuetitle_L_down"></span>
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
                                <td id="iss" width="25%">
                                    <span class="issuetitle_L_down">事故日期：</span>
                                    <c:out value="${caseData.evtJobDateString}" />
                                </td>
                                <td id="iss" width="25%">
                                    <span class="issuetitle_L_down">得請領起始年月：</span>
                                    <html:select styleId="ableApsYm" property="ableApsYm">
                                         <html:option value=""></html:option>
                                         <html:option value="appDate"><c:out value="${caseData.appDateJsp}" /></html:option>
                                         <html:option value="evtJobDate"><c:out value="${caseData.evtJobDateJsp}" /></html:option>
                                     </html:select>
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
                                <td id="iss" width="25%">
                                	<c:if test="${caseData.regetCipbMk eq '1'}">
                                    	<input type="checkbox" id="regetCipbMk" name="regetCipbMk" onclick="checkRegetCipbMk('<c:out value="${caseData.apNo}" />');" checked="true"><span class="issuetitle_L_down">重讀CIPB</span>
                                 	</c:if>
                                	<c:if test="${caseData.regetCipbMk ne '1'}">
                                    	<input type="checkbox" id="regetCipbMk" name="regetCipbMk" onclick="checkRegetCipbMk('<c:out value="${caseData.apNo}" />');"><span class="issuetitle_L_down">重讀CIPB</span>
                                 	</c:if>                                                               	                              
                                </td>  
                                <td id="iss" width="25%">
                                    <span class="issuetitle_L_down"></span>
                                   </td>                                 
                            </tr>
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
                    <td>
                        <table cellpadding="1" cellspacing="3" width="100%" class="px13">
                            <tr>
                                <td colspan="5" class="issuetitle_L">
                                    <span class="issuetitle_search">&#9658;</span>&nbsp;編審註記：依受款人/眷屬排序
                                </td>
                            </tr>
                            <c:if test="${caseData.benListSize gt 0}">
                                <tr align="center">
                                    <td width="20%" class="issuetitle_L">給付年月</td>
                                    <td width="30%" class="issuetitle_L">編審註記</td>
                                    <td width="10%" class="issuetitle_L">嚴重程度</td>
                                    <td width="20%" class="issuetitle_L">程度調整</td>
                                    <td width="20%" class="issuetitle_L">調整起迄年月</td>
                                </tr>
                                <logic:iterate id="benData" indexId="i" name="caseData" property="benList">
                                <tr>
                                <td  colspan="5">
                                <table  cellpadding="1" cellspacing="3" width="100%" class="px13"  <%= (i%2==0)? "bgcolor='#FFFFFF'":"bgcolor='#E8E8FF'"%>>
                                <tr>
                                    <td colspan="5" id="iss">
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
                                                    <label>
                                                        <input type="radio" name='adjLevel<c:out value="${benDetailData.baChkFileId}" />' value="W" <c:if test="${benDetailData.chkCodePost eq 'W'}">checked</c:if> onclick="setValiYm('<c:out value="${benDetailData.baChkFileId}" />','<c:out value="${benDetailData.chkCode}" />','<c:out value="${benDetailData.payYmShortString}" />');" >W-警告
                                                        <input type="radio" name='adjLevel<c:out value="${benDetailData.baChkFileId}" />' value="O" <c:if test="${benDetailData.chkCodePost eq 'O'}">checked</c:if> onclick="cleanValiYm('<c:out value="${benDetailData.baChkFileId}" />');" >O-可穿透
                                                    </label>
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                &nbsp;
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td width="20%" class="issueinfo_C">
                                        <c:choose>
                                            <c:when test="${benDetailData.chkCodePre eq 'O' and benDetailData.maxMk eq '1'}">
                                                <input name='valisYm<c:out value="${benDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value="<c:out value="${benDetailData.valisYmString}" />" onchange="checkValisYm(<c:out value="${benDetailData.baChkFileId}" />,'<c:out value="${benDetailData.payYmShortString}" />');">
                                                &nbsp;~&nbsp;
                                                <input name='valieYm<c:out value="${benDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value="<c:out value="${benDetailData.valieYmString}" />">
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
                            <c:if test="${caseData.famListSize gt 0}">
                                <tr align="center">
                                    <td width="20%" class="issuetitle_L">給付年月</td>
                                    <td width="30%" class="issuetitle_L">編審註記</td>
                                    <td width="10%" class="issuetitle_L">嚴重程度</td>
                                    <td width="20%" class="issuetitle_L">程度調整</td>
                                    <td width="20%" class="issuetitle_L">調整起迄年月</td>
                                </tr>
                                <logic:iterate id="famData" indexId="i" name="caseData" property="famList">
                                <tr>
                                <td  colspan="5">
                                <table  cellpadding="1" cellspacing="3" width="100%" class="px13"  <%= ((((DisabledCheckMarkLevelAdjustCase)caseData).getBenListSize()+i)%2==0)? "bgcolor='#FFFFFF'":"bgcolor='#E8E8FF'"%>>
                                <tr>
                                    <td colspan="5" id="iss">
                                        <table width="80%" border="0" cellspacing="2" cellpadding="2" class="px13">
                                            <tr>
                                                <td>眷屬序：<c:out value="${i + 1}" /></td>
                                                <td>眷屬姓名：<c:out value="${famData.famName}" /></td>
                                                <td>眷屬身分證號：<c:out value="${famData.famIdnNo}" /></td>
                                                <td>關　係：<c:out value="${famData.famEvtRel}" />-<c:out value="${famData.famEvtRelString}" /></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <logic:iterate id="famDetailData" indexId="n" name="famData" property="detailList">
                                <tr>
                                    <td width="20%" class="issueinfo_C">
                                        <c:out value="${famDetailData.payYmString}" />
                                        <input type='hidden' id='listPayYm<c:out value="${famDetailData.baChkFileId}" />' name='listPayYm<c:out value="${famDetailData.baChkFileId}" />' value='<c:out value="${famDetailData.payYmShortString}" />' >
                                    </td>
                                    <td width="30%" class="issueinfo">
                                        <c:out value="${famDetailData.chkCode}" />-<c:out value="${famDetailData.chkResult}" />
                                    </td>
                                    <td width="10%" class="issueinfo">
                                        <c:out value="${famDetailData.chkCodePreString}" />
                                    </td>
                                    <td width="20%" class="issueinfo_C">
                                        <c:choose>
                                            <c:when test="${famDetailData.chkCodePre eq 'O'}">
                                                <span class="formtxt">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${(famDetailData.chkCode eq '01' and famDetailData.payYm ne famData.famDieYm) or (famDetailData.chkCode eq '09' and famData.famEvtRel eq '2')}">
                                                                <input type="radio" name='adjLevel<c:out value="${famDetailData.baChkFileId}" />' value="W" <c:if test="${famDetailData.chkCodePost eq 'W'}">checked</c:if> disabled="disabled" >W-警告
                                                                <input type="radio" name='adjLevel<c:out value="${famDetailData.baChkFileId}" />' value="O" <c:if test="${famDetailData.chkCodePost eq 'O'}">checked</c:if> disabled="disabled" >O-可穿透
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="radio" name='adjLevel<c:out value="${famDetailData.baChkFileId}" />' value="W" <c:if test="${famDetailData.chkCodePost eq 'W'}">checked</c:if> onclick="setValiYm('<c:out value="${famDetailData.baChkFileId}" />','<c:out value="${famDetailData.chkCode}" />','<c:out value="${famDetailData.payYmShortString}" />');" >W-警告
                                                                <input type="radio" name='adjLevel<c:out value="${famDetailData.baChkFileId}" />' value="O" <c:if test="${famDetailData.chkCodePost eq 'O'}">checked</c:if> onclick="cleanValiYm('<c:out value="${famDetailData.baChkFileId}" />');" >O-可穿透
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </label>
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                &nbsp;
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td width="20%" class="issueinfo_C">
                                        <c:choose>
                                            <c:when test="${famDetailData.chkCodePre eq 'O' and famDetailData.maxMk eq '1'}">
                                                <c:choose>
                                                    <c:when test="${(famDetailData.chkCode eq '01') or (famDetailData.chkCode eq '09' and famData.famEvtRel eq '2')}">
                                                        <input name='valisYm<c:out value="${famDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value="<c:out value="${famDetailData.valisYmString}" />" disabled="disabled">
                                                        &nbsp;~&nbsp;
                                                        <input name='valieYm<c:out value="${famDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value="<c:out value="${famDetailData.valieYmString}" />" disabled="disabled">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${famDetailData.chkCode eq '09' and famData.famEvtRel eq '2'}">
                                                                <input name='valisYm<c:out value="${famDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value="<c:out value="${famDetailData.valisYmString}" />" disabled="disabled">
                                                                &nbsp;~&nbsp;
                                                                <input name='valieYm<c:out value="${famDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value="<c:out value="${famDetailData.valieYmString}" />">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input name='valisYm<c:out value="${famDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value="<c:out value="${famDetailData.valisYmString}" />" onchange="checkValisYm(<c:out value="${famDetailData.baChkFileId}" />,'<c:out value="${famDetailData.payYmShortString}" />');">
                                                                &nbsp;~&nbsp;
                                                                <input name='valieYm<c:out value="${famDetailData.baChkFileId}" />' type="text" class="textinput" size="10" maxlength="5" value="<c:out value="${famDetailData.valieYmString}" />">
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
