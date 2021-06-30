<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M240X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="QryProcedureForm" page="1" />	
    <script language="javascript" type="text/javascript">
    <!--
    function copyValue(oring, dest) {
        if (dest.value == "")
            dest.value = asc(oring.value);
    }
    
    function checkDate() {
        if (Trim($F("updateDateBegin")) == "" || Trim($F("updateDateEnd")) == "") {
        		alert("處理時間需輸入");
                return false;
        }    	
        
        if (Trim($F("updateDateBegin")) != "" && Trim($F("updateDateEnd")) != "") {
            if (parseNumber(Trim($F("updateDateBegin"))) > parseNumber(Trim($F("updateDateEnd")))) {
                alert("處理時間範圍錯誤, 起始日期不可大於結束日期");
                return false;
            }
        }
        return true;
    }
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        document.QryProcedureForm.reset();
        initAll();       
    }
    <%-- ] ... end --%>
    
    function initAll() {
        dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");      
    }

    Event.observe(window, 'load', initAll , false);	    
    
    -->
    </script>			
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/qryProcedure" method="post" onsubmit="return validateQryProcedureForm(this);">
        
        <fieldset>
            <legend>&nbsp;批次程式查詢作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
	  
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnInsert">
			                <input name="btnQuery" type="button" class="button" value="確  認 " onclick="$('page').value='1'; $('method').value='doQuery'; if (document.QryProcedureForm.onsubmit() && checkDate()){document.QryProcedureForm.submit();}else{return false;}" />&nbsp;&nbsp;
			            </acl:checkButton>

		            </td>
                </tr>     	  
                <tr>
                    <td align="right" width="30%">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">處理時間：</span>
                    </td>
                    <td>
                        <html:text styleId="updateDateBegin" property="updateDateBegin" tabindex="2" size="8" maxlength="8" styleClass="textinput" onblur="this.value = asc(this.value); copyValue(this, $('updateDateEnd'));" />
                        &nbsp;~&nbsp;
                        <html:text styleId="updateDateEnd" property="updateDateEnd" tabindex="3" size="8" maxlength="8" styleClass="textinput" onblur="this.value = asc(this.value); copyValue(this, $('updateDateBegin'));" />
                    </td>
                </tr>
                
                <tr>
                    <td align="right" width="30%">                        
                        
                    </td>
                    <td>
                    	<span class="issuetitle_R_down">Input format: YYYYMMDD</span>
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