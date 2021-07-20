<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D680L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>      
    <html:javascript formName="OtherRpt09Form" page="1" />                   
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function checkFields(){
         var msg = "";
    	
    	if($('payCode').value != "L" && $('payCode').value != "K" && $('payCode').value != "S"){
    		msg+="請選擇「給付別」\r\n";
    	}
    	if($('deadYy').value==""){
            msg+="請輸入「呆帳年度」\r\n";
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
	<%-- 畫面重設 --%>
    function resetForm(){
    	$('payCode').value = "";
    	alert($('payCode').value);
    	$('deadYy').value = "";
    	$('bDebtDate').value = "";
        initAll();       
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
    
	function initAll() {
    	$('payCode').value = "";
    	$('deadYy').value = "";
    	$('bDebtDate').value = "";
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
        <html:form action="/printOtherRpt09" method="post" onsubmit="return validateOtherRpt09Form(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;轉呆帳核定清冊&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnQuery" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if(document.OtherRpt09Form.onsubmit() && checkFields()){document.OtherRpt09Form.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
	                    &nbsp;
	                    <acl:checkButton buttonName="btnCompQuery">
                            <input type="button" name="btnCompQuery" class="button" value="補　印" onclick="$('page').value='1'; $('method').value='doCompReport'; if(document.OtherRpt09Form.onsubmit() && checkFieldsComp()){document.OtherRpt09Form.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
	                    &nbsp;
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
