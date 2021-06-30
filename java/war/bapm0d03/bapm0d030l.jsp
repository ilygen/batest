<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPM0D030L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="PaymentProgressQueryForm" page="1" />
    
    <script language="javascript" type="text/javascript">	
	
	function initAllForClean(){
        $("idn").value="";
        $("paymentDate").value = "";
        $("sequenceNo").value = "";
        $("caseNo").value = "";
        
    }
	<%-- 畫面重設 --%>
    function resetForm(){
        cleanForm();
        initAllForClean();
    }	
    
    function checkFieldNext(){
    	if($("idn").value == "" && $("caseNo").value == "" && $("sequenceNo").value =="" && $("paymentDate").value==""){
   			alert("請輸入其中一個條件");
   			return false;
    	}else if($("sequenceNo").value != "" || $("paymentDate").value !=""){
    		if($("sequenceNo").value == "" || $("paymentDate").value ==""){
    			alert("流水號及開單日期必須同時輸入兩個條件");
    			return false;
    		}else{
    			return true;
    		}
    	}else{
    		return true;
    	}
    	
    }	
        	   		    
    </script>    			
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/paymentProgressQuery" method="post" onsubmit="return validatePaymentProgressQueryForm(this);">
        
        <fieldset>
            <legend>&nbsp;繳款單查詢作業 &nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
	  
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
			            <acl:checkButton buttonName="btnQuery">
			                <input name="btnQuery" type="button" class="button" value="確  定 " onclick="$('page').value='1'; $('method').value='doQuery'; if (checkFieldNext() && document.PaymentProgressQueryForm.onsubmit()){document.PaymentProgressQueryForm.submit();}else{return false;}" />&nbsp;&nbsp;   
			            </acl:checkButton>
			            <acl:checkButton buttonName="btnClear">
			                <input name="btnClear" type="button" class="button" value="清  除 " onclick="resetForm()">
			            </acl:checkButton>
		            </td>
                </tr>     	  
	            <tr>
	            	<td width="30%" align="right">
                       <span class="issuetitle_R_down">身分證號：</span>
                    </td>
                    <td>
                        <html:text property="idn" styleId="idn" tabindex="10" styleClass="textinput" size="15" maxlength="10"  />
		            </td>
                </tr>
                
                 <tr>
	            	<td align="right">
                        <span class="issuetitle_R_down">移送案號：</span>
                    </td>
                    <td>
                        <html:text property="caseNo" styleId="caseNo" tabindex="10" styleClass="textinput" size="15" maxlength="10"  />
		            </td>
                </tr>
                <tr>
	            	<td align="right">
                        <span class="issuetitle_R_down">開單日期：</span>
                    </td>
                    <td>
                        <html:text property="paymentDate" styleId="paymentDate" tabindex="10" styleClass="textinput" size="15" maxlength="7"  />
		            	 <span class="issuetitle_R_down">流水號：</span>
		            	<html:text property="sequenceNo" styleId="sequenceNo" tabindex="10" styleClass="textinput" size="15" maxlength="5"  />
		            </td>
                </tr>
                
	            <tr>
	                <td colspan="2" align="right" valign="bottom">&nbsp;</td>
                </tr>
            </table>
        </fieldset>
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>