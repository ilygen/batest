<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D140L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="DataUpdateRpt04Form" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    <%-- begin 檢查必填欄位 --%>
    function checkFields(){        
        if(Trim($F("apNo1")).length == 0||Trim($F("apNo2")).length == 0||Trim($F("apNo3")).length == 0||Trim($F("apNo4")).length == 0||Trim($F("apNo5")).length == 0){
            alert("請輸入受理編號");
            return false;        
        }
        
        return true;
    }
    <%-- ] ... end --%>
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        document.DataUpdateRpt04Form.reset();
        initAll();       
    }
    <%-- ] ... end --%>
    
    function initAll() {
        dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");      
    }

    Event.observe(window, 'load', initAll , false);

    Event.stopObserving(window, 'beforeunload', beforeUnload);
    -->
    </script>
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/printDataUpdateRpt04" method="post" onsubmit="return validateDataUpdateRpt04Form(this);">
        
        <fieldset>
            <legend>&nbsp;紓困債務人照會單&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnPrint">
                            <input name="btnPrint" type="button" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.DataUpdateRpt04Form.onsubmit()){document.DataUpdateRpt04Form.submit();}else{return false;}"/>&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input name="btnClear" type="reset" class="button" value="清　除" onclick="resetForm();">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">受理編號：</span>
                    </td>
                    <td>
                        <html:text property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value=Trim($('apNo1').value); autotab($('apNo1'), $('apNo2'))" />
                        &nbsp;-
                        <html:text property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value = asc(this.value);" onkeyup="this.value=Trim($('apNo2').value); autotab($('apNo2'), $('apNo3'))" />
                        &nbsp;-
                        <html:text property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value = asc(this.value);" onkeyup="this.value=Trim($('apNo3').value); autotab($('apNo3'), $('apNo4'))" />
                        &nbsp;-
                        <html:text property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value = asc(this.value);" onkeyup="this.value=Trim($('apNo4').value);" />
                    </td>        
                </tr>
                <tr>
          	        <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>
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
