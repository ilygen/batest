<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BALP0D550L" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
<html:javascript formName="OtherRpt10Form" page="1" />
<script language="javascript" type="text/javascript">
<!--
	
<%-- begin 檢查必填欄位 --%>
	function checkFields() {
		if (Trim($F("issuYm")) == "")
			msg += '「核定年月」：為必輸欄位。\r\n';

		return true;
	}
<%-- ] ... end --%>
	
<%-- 畫面清空重設 --%>
	
<%-- begin ... [ --%>
	function resetForm() {
		document.OtherRpt10Form.reset();
		initAll();
	}
<%-- ] ... end --%>
	function initAll() {
		dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");
	}

	Event.observe(window, 'load', initAll, false);

	Event.stopObserving(window, 'beforeunload', beforeUnload);
	-->
</script>
</head>
<body scroll="no">

	<div id="mainContent">

		<%@ include file="/includes/ba_header.jsp"%>

		<%@ include file="/includes/ba_menu.jsp"%>

		<div id="main" class="mainBody">
			<html:form action="/printOtherRpt10" method="post" onsubmit="return validateOtherRpt10Form(this);">

				<fieldset>
					<legend>&nbsp;穿透案件統計表&nbsp;</legend>

					<div align="right" id="showtime">
						網頁下載時間：民國&nbsp;
						<func:nowDateTime />
					</div>

					<table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
						<tr>
							<td colspan="2" align="right">
							<html:hidden styleId="page"	property="page" value="1" /> 
							<html:hidden styleId="method" property="method" value="" /> 
								<acl:checkButton buttonName="btnPrint">
									<input type="button" id="btnPrint" name="btnPrint" tabindex="3" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.OtherRpt10Form.onsubmit()){document.OtherRpt10Form.submit();}else{return false;}" />
								</acl:checkButton> &nbsp; 
								<acl:checkButton buttonName="btnReset">
									<input type="reset" id="btnReset" name="btnReset" tabindex="4" class="button" value="清  除" />
								</acl:checkButton></td>
						</tr>
						<tr>
							<td width="30%" align="right">
								<span class="needtxt">＊</span> 
								<span class="issuetitle_R_down">核定年月：</span>
							</td>
							<td>
								<html:text property="issuYm" styleId="issuYm" styleClass="textinput" maxlength="5" size="10" onblur="this.value = asc(this.value);" />
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2">
								<hr size="1" noshade> <span class="titleinput">※注意事項：</span><br>
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
