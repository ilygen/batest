<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.receipt.forms.SurvivorAnnuityWalkInReceiptForm" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAAP0D060A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>      
    <html:javascript formName="SurvivorAnnuityWalkInReceiptForm" page="1" />        
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){          
        $('procType').focus();
    }
    
    <%-- 受理類別 change event --%>
    function changeProcType() {
    	var procType = $('procType').value;
    	if ('2,3'.indexOf(procType) > -1) {
    		$('apnoBc').style.display = 'block';
    		$('apnoBb').style.display = 'none';
    	} else if (procType == '4') {
    		$('apnoBc').style.display = 'none';
    		$('apnoBb').style.display = 'block';
    	}
    	cleanApno();
    }
    
    function cleanApno() {
    	$('apNo1').value = '';
    	$('apNo2').value = '';
    	$('apNo3').value = '';
    	$('apNo4').value = '';
    	$('apNo1ForBb').value = '';
    	$('apNo2ForBb').value = '';
    	$('apNo3ForBb').value = '';
    	$('apNo4ForBb').value = '';
    }
    
 	// 檢查轉入作業所需要的受理編號是否有輸入
    function checkFields(procType) {
    	if ('2,3'.indexOf(procType) > -1) {
    		for(var i = 1; i <= 4; i++) {
    			if (Trim($('apNo' + i).value).length < 1) {
            		alert("請輸入受理編號");
    				$('apNo' + i).focus();
    				return false;
    			}
    		}
    	} else if (procType == '4') {
    		for(var i = 1; i <= 4; i++) {
    			if (Trim($('apNo' + i + 'ForBb').value).length < 1) {
            		alert("請輸入受理編號");
    				$('apNo' + i + 'ForBb').focus();
    				return false;
    			}
    		}
    	}
        return true;
    }
    
    function doSubmit() {
    	var procType = $('procType').value;
    	$('method').value='prepareInsert';
    	
    	if (procType == '1' || (checkFields(procType) && document.SurvivorAnnuityWalkInReceiptForm.onsubmit())) {
	    	document.SurvivorAnnuityWalkInReceiptForm.submit();
    	} else {
    		return false;
    	}
    }
    
    <%-- 畫面重設 --%>
    function resetForm(){
        cleanForm();
        $('procType').focus();
    }
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" onload="Event.stopObserving(window, 'load', inputFieldFocus);">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/survivorAnnuityWalkInReceiptQuery" method="post" onsubmit="return validateSurvivorAnnuityWalkInReceiptForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;遺屬年金臨櫃受理作業 &nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnInsert">
                            <input type="button" id="btnInsert" name="btnInsert" tabindex="60" class="button" value="新　增" onclick="doSubmit()"/>&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input type="button" id="btnClear" name="btnClear" tabindex="80" class="button" value="清　除" onclick="resetForm();"/>
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">受理類別：</span>
                    </td>
                    <td>
                    	<html:select property="procType" tabindex="10" onchange="changeProcType();">
                    		<html:option value="1">1-普通遺屬年金受理</html:option>
                    		<html:option value="2">2-職災死亡一次金(轉入)</html:option>
                    		<html:option value="3">3-普通死亡一次金(轉入)</html:option>
                    		<html:option value="4">4-職災遺屬年金(轉入)</html:option>
                    	</html:select>
                    </td>
                </tr>
                <tr id="apnoBc">
                    <td align="right" class="issuetitle_R_down">受理編號：</td>
                    <td>
                        <html:text property="apNo1" styleId="apNo1" tabindex="21" styleClass="textinput" size="3" maxlength="3" 
                        	onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'));"/>
                        &nbsp;-
                        <html:text property="apNo2" styleId="apNo2" tabindex="22" styleClass="textinput" size="1" maxlength="1" 
                        	onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2'), $('apNo3'));"/>
                        &nbsp;-
                        <html:text property="apNo3" styleId="apNo3" tabindex="23" styleClass="textinput" size="2" maxlength="2" 
                        	onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3'), $('apNo4'));"/>
                        &nbsp;-
                        <html:text property="apNo4" styleId="apNo4" tabindex="24" styleClass="textinput" size="6" maxlength="6" 
                        	onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo4'), $('btnInsert'));"/>
                    </td>
                </tr>
                <tr id="apnoBb" style="display: none;">
                    <td align="right" class="issuetitle_R_down">受理編號：</td>
                    <td>
                        <html:text property="apNo1ForBb" styleId="apNo1ForBb" tabindex="31" styleClass="textinput" size="1" maxlength="1"
                        	onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1ForBb'), $('apNo2ForBb'))"/>
                        &nbsp;-
                        <html:text property="apNo2ForBb" styleId="apNo2ForBb" tabindex="32" styleClass="textinput" size="1" maxlength="1"
                        	onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2ForBb'), $('apNo3ForBb'));"/>
                        &nbsp;-
                        <html:text property="apNo3ForBb" styleId="apNo3ForBb" tabindex="33" styleClass="textinput" size="5" maxlength="5"
							onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3ForBb'), $('apNo4ForBb'));"/>
                        &nbsp;-
                        <html:text property="apNo4ForBb" styleId="apNo4ForBb" tabindex="34" styleClass="textinput" size="5" maxlength="5"
                        	onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo4ForBb'), $('btnInsert'))"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>                                                
            </table>
        </fieldset>
        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
