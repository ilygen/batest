<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D3F0L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="MonthlyRpt15Form" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    <%-- begin 檢查必填欄位 --%>
    function checkFields(){        
        if (Trim($F("payCode")) == "")
            msg += '「給付別」：為必輸欄位。\r\n';
        if (Trim($F("issuYm")) == "")
            msg += '「核定年月」：為必輸欄位。\r\n';
        if (Trim($F("chkDate")) == "")
        	msg += '「核定日期」:為必輸欄位。\r\n';
        return true;
    }
    <%-- ] ... end --%>
    
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
        document.MonthlyRpt15Form.reset();
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
        <html:form action="/printMonthlyRpt15" method="post" onsubmit="return validateMonthlyRpt15Form(this);">
        
        <fieldset>
            <legend>&nbsp;應收已收清冊&nbsp;</legend> 
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnPrint">
                            <input name="btnPrint" type="button" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.MonthlyRpt15Form.onsubmit()){document.MonthlyRpt15Form.submit();}else{return false;}"/>&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input name="btnClear" type="reset" class="button" value="清　除" onclick="resetForm();">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">給付別：</span>
                    </td>
                    <td width="10%">
                        <html:select styleId="payCode" property="payCode" onchange="chgPayCode();">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>
                    <td width="60%" align="left">
                    	<div id="npWithLipContent" style="display: none">   
                    		<input type="checkbox" id="npWithLip" name="npWithLip" value="Y">國併勞
                    	</div> 
                    </td>                              
                </tr>
                <tr>
                    <td align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">核定年月：</span>
                    </td>
                    <td>
                        <html:text property="issuYm" styleId="issuYm" styleClass="textinput" maxlength="5" size="10" onblur="this.value = asc(this.value);" />
                    </td>        
                </tr>
                 <tr>
                    <td align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">核定日期：</span>
                    </td>
                    <td>
                        <html:text property="chkDate" styleId="chkDate" styleClass="textinput" maxlength="7" size="10" onblur="this.value = asc(this.value);" />
                    </td>        
                </tr>
                <tr>
          	        <td colspan="3">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="3">
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
