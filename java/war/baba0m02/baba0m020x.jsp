<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M020X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="ReturnWriteOffBJForm" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        document.ReturnWriteOffBJForm.reset();
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
        <html:form action="/returnWriteOffBJ" method="post" onsubmit="return validateReturnWriteOffBJForm(this);">
        
        <fieldset>
            <legend>&nbsp;收回作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnQuery">
                            <input name="btnQuery" type="button" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doQuery'; if (document.ReturnWriteOffBJForm.onsubmit()){document.ReturnWriteOffBJForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
			            <acl:checkButton buttonName="btnClear">
                            <input name="btnClear" type="reset" class="button" value="清　除" onclick="resetForm();">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">檔案產生起迄日期：</span>
                    </td>
                    <td>
                        <html:text property="upTimeBeg" styleId="upTimeBeg" styleClass="textinput" size="7" maxlength="7" onkeyup="autotab(this, $('upTimeEnd'))" />
                        &nbsp;~
                        <html:text property="upTimeEnd" styleId="upTimeEnd" styleClass="textinput" size="7" maxlength="7" />
                        <span class="formtxt">(如民國98年1月1日，請輸入0980101)</span>
                    </td>        
                </tr>
                <tr>
          	        <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <hr size="1" noshade><span class="titleinput">※注意事項：</span><br>
            　                                               &nbsp;<span class="needtxt">＊</span>為必填欄位。
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