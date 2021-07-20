<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D650L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>      
    <html:javascript formName="OtherRpt06Form" page="1" />                   
    <script language="javascript" type="text/javascript">
    <!--
    <%-- begin 檢查必填欄位 --%>
    function checkFields(){        
        var msg = "";
    	if($('payCode').value != "L" && $('payCode').value != "K" && $('payCode').value != "S"){
    		msg+="請選擇「給付別」\r\n";
    	}
        if (Trim($F("procYm")) == ""){
        	msg += '「處理年月」：為必輸欄位。\r\n';
        }
        if(Trim($F("demDate")).length != 0){
        	msg += "無須輸入轉催收日期。\r\n";
        	$('demDate').value = "";
        }
        if(msg!=""){
            alert(msg);
            return false;
        }else{
            return true;
        }
    }
    
	function chgPayCode(){
        if($('payCode').value == "L"){
            $('npWithLipContent').style.display="none";
        }else if($('payCode').value == "K"){
            $('npWithLipContent').style.display="inline";
        }else if($('payCode').value == "S"){
            $('npWithLipContent').style.display="none";        
        }
    }    
    function checkFieldsComp(){
    	var msg = "";
    	if($('payCode').value != "L" && $('payCode').value != "K" && $('payCode').value != "S"){
    		msg+="請選擇「給付別」\r\n";
    	}
    	if($('demDate').value == "" ){
    		msg+="請輸入「轉催收日期」\r\n";
    	}
        if(Trim($F("procYm")).length != 0){
        	msg += "補印清單無須輸入處理年月。\r\n";
        	$('procYm').value = "";
        }
        
        if(msg!=""){
            alert(msg);
            return false;
        }else{
            return true;
        }
    
    }
    
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        $('payCode').value = "";
        $('demDate').value = "";
        $('procYm').value = "";
        initAll();       
    }
    <%-- ] ... end --%>
    
    function initAll() {
    	$('payCode').value = "";
    	$('demDate').value = "";
    	$('procYm').value = "";
        dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");      
    }

    Event.observe(window, 'load', initAll , false);
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/printOtherRpt06" method="post" onsubmit="return validateOtherRpt06Form(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;轉催收核定清單&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnQuery" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if(document.OtherRpt06Form.onsubmit() && checkFields()){document.OtherRpt06Form.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnCompQuery" class="button" value="補　印" onclick="$('page').value='1'; $('method').value='doCompReport'; if(document.OtherRpt06Form.onsubmit() && checkFieldsComp()){document.OtherRpt06Form.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input name="btnClear" type="reset" class="button" value="清　除 " onclick="resetForm()"/>
                        </acl:checkButton>                        
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">給付別：</span>
                    </td>
                    <td>
                        <html:select styleId="payCode" property="payCode" tabindex="1">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>
                                               
                </tr>  
                <tr>
                	<td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">處理年月：</span>
                    </td>
                	<td>
                        <html:text property="procYm" styleId="procYm" styleClass="textinput" maxlength="5" size="10" onblur="this.value = asc(this.value);" />
                    </td> 

                 </tr>   
                 <tr>
                	<td width="30%" align="right">
                		<span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">轉催收日期：</span>
                    </td>
                	<td>
                        <html:text property="demDate" styleId="demDate" styleClass="textinput" maxlength="7" size="10" onblur="this.value = asc(this.value);" />
                    </td> 

                 </tr> 
               
                 <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2"><hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>
                 　                            1.執行列印時，請輸入「給付別」、「處理年月」。<br>
 				 &nbsp;&nbsp;&nbsp;&nbsp;2.執行補印時，請輸入「給付別」、「轉催收日期」。<br>
                 
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
