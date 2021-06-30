<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D3Q0L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="OtherRpt05Form" page="1" />
    <script language="javascript" type="text/javascript">
    function chgPayCode(){
        if($('payCode').value == "L"){
            $('rptKindContent').style.display="inline";
            $('rptKindLabelContent').style.display="inline";
        }else if($('payCode').value == "K"){
            $('rptKindContent').style.display="none";
            $('rptKindLabelContent').style.display="none";
        }else if($('payCode').value == "S"){
            $('rptKindContent').style.display="none";
            $('rptKindLabelContent').style.display="none";
        }
    }
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        document.OtherRpt05Form.reset();
        initAll();       
    }
    <%-- ] ... end --%>
    
    function disabledbutton(){
    	$("btnPrint").disabled = true;
    	$("btnReset").disabled = true;
    }
    
    function initAll() {
    	$('rptKindContent').style.display="none";
        $('rptKindLabelContent').style.display="none";
        
        dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");     
    }
    <!--
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/printOtherRpt05" method="post" onsubmit="return validateOtherRpt05Form(this);">
        
        <fieldset>
            <legend>&nbsp;批次核定函&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" id="btnPrint" name="btnPrint" tabindex="3" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.OtherRpt05Form.onsubmit()){disabledbutton();document.OtherRpt05Form.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input type="reset" id="btnReset" name="btnReset" tabindex="4" class="button" value="清  除" onclick="resetForm();"/>
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">給付別：</span>
                    </td>
                    <td>
                        <html:select styleId="payCode" property="payCode" tabindex="1" onchange="chgPayCode();">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>
                </tr>
               <tr>
                    <td align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">核定年月：</span>
                    </td>
                    <td>
                        <html:text styleId="issuYm" property="issuYm" tabindex="2" size="10" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
                    	<div id="rptKindLabelContent" style="display: none">
                       		<span class="issuetitle_R_down">通知書類別：</span>
                       	</div>
                    </td>
                    <td>
                    	<div id="rptKindContent" style="display: none">
							<html:select property="rptKind" styleId="rptKind">
								<html:option value="A">全部</html:option>
								<html:option value="O">一般</html:option>
								<html:option value="B">補償金</html:option>
								<html:option value="D">死亡</html:option>
								<html:option value="L">紓困</html:option>
							</html:select>
						</div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span>
                        <br>&nbsp;
                        <span class="needtxt">＊</span>為必填欄位。
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
