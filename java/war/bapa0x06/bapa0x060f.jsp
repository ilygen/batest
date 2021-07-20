<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X060F" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="CpiDtlMaintQueryForm" page="1" />
    
    <script language="javascript" type="text/javascript">	
	
	function checkYYY(the){
		var i;
		if(the.value == ""){
			return;
		}
		for(i=the.value.length;i<3;i++){
			the.value = '0' + the.value;
		}
	}
	
	function initAllForClean(){
        $("issuYear").value="";
    }
	<%-- 畫面重設 --%>
    function resetForm(){
        cleanForm();
        initAllForClean();
    }	
        	   		    
    </script>    			
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/cpiDtlMaintQuery" method="post" onsubmit="return validateCpiDtlMaintQueryForm(this);">
        
        <fieldset>
            <legend>&nbsp;物價指數調整明細維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
	  
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
			            <acl:checkButton buttonName="btnQuery">
			                <input name="btnQuery" type="button" class="button" value="確  認 " onclick="$('page').value='1'; $('method').value='doQuery'; if (document.CpiDtlMaintQueryForm.onsubmit()){document.CpiDtlMaintQueryForm.submit();}else{return false;}" />&nbsp;&nbsp;   
			            </acl:checkButton>
			            <acl:checkButton buttonName="btnClear">
			                <input name="btnClear" type="reset" class="button" value="清  除 " onclick="resetForm()">
			            </acl:checkButton>
		            </td>
                </tr>     	  
	            <tr>
                    <td width="30%" align="right" class="issuetitle_R_down">
                                                    核定年度：
                    </td>
                    <td>
                        <html:text property="issuYear" styleId="issuYear" styleClass="textinput" size="3" maxlength="3" onblur="checkYYY(this)" />
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