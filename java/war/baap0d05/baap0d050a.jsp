<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.receipt.forms.DisabledAnnuityReceiptForm" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAAP0D050A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>      
    <html:javascript formName="DisabledAnnuityReceiptQueryForm" page="1" />        
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){  
        $('apNo1For36').focus();
    }
    
    <%-- 畫面重設 --%>
    function resetForm(){
        cleanForm();
        $('apNo1For36').focus();
    }
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" onload="Event.stopObserving(window, 'load', inputFieldFocus);">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/disabledAnnuityReceipt" method="post" onsubmit="return validateDisabledAnnuityReceiptQueryForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="origSFlag36" property="origSFlag36"/>
        
        <fieldset>
            <legend>&nbsp;國併勞年金臨櫃受理作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnModify36">
                            <input type="button" id="btnModify36" name="btnModify36" tabindex="80" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='prepareInsert36Data'; document.DisabledAnnuityReceiptQueryForm.submit();" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input type="button" id="btnClear" name="btnClear" tabindex="90" class="button" value="清　除" onclick="resetForm();"/>
                        </acl:checkButton>                        
                    </td>
                </tr> 
                <tr>
                    <td width="30%" align="right" class="issuetitle_R_down">國保受理編號：</td>
                    <td>
                        <html:text property="apNo1For36" styleId="apNo1For36" tabindex="20" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1For36'), $('apNo2For36'))"/>
                        &nbsp;-
                        <html:text property="apNo2For36" styleId="apNo2For36" tabindex="30" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2For36'), $('apNo3For36'));" />
                        &nbsp;-
                        <html:text property="apNo3For36" styleId="apNo3For36" tabindex="40" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3For36'), $('apNo4For36'));" />
                        &nbsp;-
                        <html:text property="apNo4For36" styleId="apNo4For36" tabindex="50" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo4For36'), $('btnModify36'));" />
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