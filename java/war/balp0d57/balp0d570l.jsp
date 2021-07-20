<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BALP0D570L" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
<html:javascript formName="OtherRpt12Form" page="1" />
<script language="javascript" type="text/javascript">
	
<%-- begin 檢查必填欄位 --%>
<%-- ] ... end --%>
	
<%-- 畫面清空重設 --%>
	
<%-- begin ... [ --%>
	function resetForm() {
		document.OtherRpt12Form.reset();
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
		<html:form action="/printOtherRpt12" method="post" onsubmit="return validateOtherRpt12Form(this);">

			<fieldset>
				<legend>&nbsp;批次老年差額金通知列印作業&nbsp;</legend>

				<div align="right" id="showtime">網頁下載時間：民國&nbsp;<func:nowDateTime /></div>

				<table width="98%" border="0" align="center" cellpadding="2"
					cellspacing="2" class="px13">
					<tr>
						<td colspan="2" align="right">
							<html:hidden styleId="page"	property="page" value="1" /> 
							<html:hidden styleId="method" property="method" value="" /> 
							<acl:checkButton buttonName="btnPrint">
								<input name="btnPrint" type="button" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if ( document.OtherRpt12Form.onsubmit()  ){document.OtherRpt12Form.submit();}else{return false;}" />&nbsp;
                        	</acl:checkButton> 
                        	<acl:checkButton buttonName="btnReset">
								<input name="btnReset" type="reset" class="button" value="清　除" onclick="resetForm();">
							</acl:checkButton></td>
					</tr>
					<tr>
						<td width="30%" align="right"><span class="needtxt">　</span>
							<span class="issuetitle_R_down">發文別：</span></td>
						<td><html:select property="rptKind" styleId="rptKind">
								<html:option value="A">全部</html:option>
								<html:option value="1">1-第1次發函</html:option>
								<html:option value="2">2-催辦</html:option>
								<html:option value="3">3-延不補正</html:option>
							</html:select></td>
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
