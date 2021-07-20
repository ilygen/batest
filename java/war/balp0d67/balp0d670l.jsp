<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D670L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>      
    <html:javascript formName="OtherRpt08Form" page="1" />                   
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
        
    function chgPayCode(){
        if($('payCode').value == "L"){
            $('npWithLipContent').style.display="none";
        }else if($('payCode').value == "K"){
            $('npWithLipContent').style.display="inline";
        }else if($('payCode').value == "S"){
            $('npWithLipContent').style.display="none";        
        }
    }    
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
     function resetForm(){
        $('payCode').value = "";
        $('apno1').value = "";
        $('apno2').value = "";
        $('apno3').value = "";
        $('apno4').value = "";
        $('bDebtDate').value = "";
        initAll();       
    }
    function checkFields(){
         var msg = "";
    	
    	if($('payCode').value != "L" && $('payCode').value != "K" && $('payCode').value != "S"){
    		msg+="請選擇「給付別」\r\n";
    	}
    	if($('deadYy').value==""){
            msg+="請輸入「呆帳年度」\r\n";
        }
       if(Trim($F("apNo1")).length != 0||Trim($F("apNo2")).length != 0||Trim($F("apNo3")).length != 0||Trim($F("apNo4")).length != 0){
            msg+="補印清單無須輸入受理編號\r\n";
       		$('apNo1').value = "";
       		$('apNo2').value = "";
       		$('apNo3').value = "";
       		$('apNo4').value = "";
        }
       	if(Trim($F("bDebtDate")).length != 0){
            msg+="列印報表無須轉呆日期\r\n";
       		$('bDebtDate').value = "";
       		
        }
    	if(msg!=""){
            alert(msg);
            return false;
        }else{
            return true;
        }    
    }
    
    function checkFieldsComp(){
    	var msg = "";
    	
    	if($('payCode').value != "L" && $('payCode').value != "K" && $('payCode').value != "S"){
    		msg+="請選擇「給付別」\r\n";
    	}
    	if(Trim($F("deadYy")).length != 0){
            msg+="補印清單無須輸入呆帳年度。\r\n";
            $('deadYy').value = "";
        }
       	
       	if(Trim($F("apNo1")).length != 0||Trim($F("apNo2")).length != 0||Trim($F("apNo3")).length != 0||Trim($F("apNo4")).length != 0){
            msg+="補印清單無須輸入受理編號\r\n";
       		$('apNo1').value = "";
       		$('apNo2').value = "";
       		$('apNo3').value = "";
       		$('apNo4').value = "";
        }
        
        if($('bDebtDate').value == "" ){
    		msg+="請輸入「轉呆帳日期」\r\n";
    	}
        
    	if(msg!=""){
            alert(msg);
            return false;
        }else{
            return true;
        }    
    
    }
    
    function checkFieldMaintain(){
    	var msg = "";
    	
    	if($('payCode').value != "L" && $('payCode').value != "K" && $('payCode').value != "S"){
    		msg+="請選擇「給付別」\r\n";
    	}
    	if($('deadYy').value==""){
            msg+="請輸入「呆帳年度」\r\n";
        }
        if(Trim($F("apNo1")).length == 0||Trim($F("apNo2")).length == 0||Trim($F("apNo3")).length == 0||Trim($F("apNo4")).length == 0){
            msg+="請輸入「受理編號」\r\n";  
        }
        if(Trim($F("bDebtDate")).length != 0){
            msg+="維護無須輸入轉呆帳日期\r\n";
       		$('bDebtDate').value = "";
       		
        }
    	if(msg!=""){
            alert(msg);
            return false;
        }else{
            return true;
        }    
    }
    
	function initAll() {
    	$('payCode').value = "";
    	$('deadYy').value = "";
    	$('apNo1').value = "";
    	$('apNo2').value = "";
    	$('apNo3').value = "";
    	$('apNo4').value = "";
        dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");      
    }
    
    Event.observe(window, 'load', initAll, false);
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/printOtherRpt08" method="post" onsubmit="return validateOtherRpt08Form(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;轉呆帳核定清單&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                    	<acl:checkButton buttonName="btnMaintain">
                            <input type="button" name="btnMaintain" class="button" value="維　護" onclick="$('page').value='1'; $('method').value='doDetail'; if(document.OtherRpt08Form.onsubmit() && checkFieldMaintain()){document.OtherRpt08Form.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnQuery" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if(document.OtherRpt08Form.onsubmit() && checkFields()){document.OtherRpt08Form.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnCompQuery">
                            <input type="button" name="btnCompQuery" class="button" value="補　印" onclick="$('page').value='1'; $('method').value='doCompReport'; if(document.OtherRpt08Form.onsubmit() && checkFieldsComp()){document.OtherRpt08Form.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input name="btnClear" type="reset" class="button" value="清  除 " onclick="resetForm()"/>
                        </acl:checkButton>                        
                    </td>
                </tr>
                 <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">給付別：</span>
                    </td>
                    <td>
                        <html:select styleId="payCode" property="payCode">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>
                                                
                </tr>
                
                 <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">呆帳年度：</span>
                    </td>
                    <td>
                        <html:text property="deadYy" styleId="deadYy" styleClass="textinput" size="10" maxlength="3"  />
                        
                    </td>
                </tr> 
                <tr>
                    <td width="30%" align="right">
                    	<span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">受理編號：</span>
                    </td>
                    <td>
                        <html:text property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'))" />
                        &nbsp;-
                        <html:text property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2'), $('apNo3'))"/>
                        &nbsp;-
                        <html:text property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3'), $('apNo4'))" />
                        &nbsp;-
                        <html:text property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" />
                    </td>
                </tr>   
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">轉呆帳日期：</span>
                    </td>
                    <td>
                        <html:text property="bDebtDate" styleId="bDebtDate" styleClass="textinput" size="10" maxlength="7"  />
                        
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2"><hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>
                 　                                   1.執行維護時，請輸入「給付別」、「呆帳年度」、「受理編號」。<br>
 				  &nbsp;&nbsp; &nbsp;2.執行列印時，請輸入「給付別」、「呆帳年度」。<br>
 				   &nbsp; &nbsp;&nbsp;3.執行補印時，請輸入「給付別」、「轉呆帳日期」。<br>
                 
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
