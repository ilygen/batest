<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BALP0D560L" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
<html:javascript formName="OtherRpt11Form" page="1" />
<script language="javascript" type="text/javascript">
	
<%-- begin 檢查必填欄位 --%>
	function checkFields() {
        if ((Trim($F("apNo1")).length == 0 || Trim($F("apNo2")).length == 0 || Trim($F("apNo3")).length == 0 || Trim($F("apNo4")).length == 0) && (Trim($F("issuYm")).length == 0)) {
            alert("「受理編號」與「核定年月」請擇一鍵入。");
            return false;        
        }
        
        if ((Trim($F("apNo1")).length != 0 && Trim($F("apNo2")).length != 0 && Trim($F("apNo3")).length != 0 && Trim($F("apNo4")).length != 0) && (Trim($F("issuYm")).length != 0)) {
            alert("「受理編號」與「核定年月」請擇一鍵入。");
            return false;        
        }
        
		return true;
	}
<%-- ] ... end --%>
	
<%-- 畫面清空重設 --%>
	
<%-- begin ... [ --%>
	function resetForm() {
		document.OtherRpt11Form.reset();
		initAll();
	}
<%-- ] ... end --%>
	function initAll() {
		dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");
	}

	Event.observe(window, 'load', initAll, false);

	Event.stopObserving(window, 'beforeunload', beforeUnload);
</script>
</head>

<body scroll="no">

	<%@ include file="/includes/ba_header.jsp"%>

	<%@ include file="/includes/ba_menu.jsp"%>

	<div id="main" class="mainBody">
		<html:form action="/printOtherRpt11" method="post" onsubmit="return validateOtherRpt11Form(this);">

			<fieldset>
				<legend>&nbsp;老年差額金通知補印作業&nbsp;</legend>

				<div align="right" id="showtime">網頁下載時間：民國&nbsp;<func:nowDateTime /></div>

				<table width="98%" border="0" align="center" cellpadding="2"
					cellspacing="2" class="px13">
					<tr>
						<td colspan="2" align="right">
							<html:hidden styleId="page"	property="page" value="1" /> 
							<html:hidden styleId="method" property="method" value="" /> 
							<acl:checkButton buttonName="btnPrint">
								<input name="btnPrint" type="button" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.OtherRpt11Form.onsubmit() && checkFields()) {document.OtherRpt11Form.submit();}else{return false;}" />&nbsp;
                        	</acl:checkButton> 
                        	<acl:checkButton buttonName="btnReset">
								<input name="btnReset" type="reset" class="button" value="清　除" onclick="resetForm();">
							</acl:checkButton></td>
					</tr>


					<tr>
						<td width="30%" align="right"><span class="needtxt">　</span>
							<span class="issuetitle_R_down">受理編號：</span></td>
						<td>
								<html:text styleId="apNo1" property="apNo1" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo2'));" onkeypress="" /> &nbsp;-&nbsp; 
								<html:text styleId="apNo2" property="apNo2" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo3'));" /> &nbsp;-&nbsp; 
								<html:text styleId="apNo3" property="apNo3" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo4'));" /> &nbsp;-&nbsp; 
								<html:text styleId="apNo4" property="apNo4" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" />
						</td>
					</tr>
					<tr>
                    	<td align="right">
                        	<span class="needtxt">　</span>
                        	<span class="issuetitle_R_down">核定年月：</span>
                    	</td>
                    	<td>
                        	<html:text property="issuYm" styleId="issuYm" styleClass="textinput" maxlength="5" size="10" onblur="this.value = asc(this.value);" />
                        	<span>（如需整批列印，請鍵入核定年月）</span>
                    	</td>
					</tr>

					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2">
							<hr size="1" noshade> <span class="titleinput">※注意事項：</span><br>
								<span class="needtxt">　　受理編號、核定年月擇一鍵入。<br></span>
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
