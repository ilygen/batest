<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BAPA0X160Q" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script language="javascript" type="text/javascript">
    <!--

	//頁面欄位值初始化       
	function initAll() {
		$("apNo").value = '';
	}

	function checkFields() {
		if (Trim($F("apNo")).length < 12) {
			alert("受理編號輸入錯誤");
			return false;
		}

		return true;
	}
<%-- 畫面清空重設 --%>
<%-- begin ... [ --%>
	function resetForm() {
		document.AnnuityAcceptDataTransferForm.reset();
		initAll();
	}
<%-- ] ... end --%>
	Event.observe(window, 'load', initAll, false);
</script>
</head>
<body scroll="no">

	<div id="mainContent">

		<%@ include file="/includes/ba_header.jsp"%>

		<%@ include file="/includes/ba_menu.jsp"%>

		<div id="main" class="mainBody">
			<html:form action="/annuityAcceptDataTransfer" method="post" onsubmit="">
				<html:hidden styleId="page" property="page" value="1" />
				<html:hidden styleId="method" property="method" value="" />

				<fieldset>
					<legend>&nbsp;年金受理轉出&nbsp;</legend>

					<div align="right" id="showtime">
						網頁下載時間：民國&nbsp;
						<func:nowDateTime />
					</div>

					<table width="98%" border="0" align="center" cellpadding="2"
						cellspacing="2" class="px13">
						<tr>
							<td colspan="2" align="right">
								<acl:checkButton buttonName="btnQuery">
									<input type="button" name="btnQuery" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doQuery'; if(checkFields()){document.AnnuityAcceptDataTransferForm.submit();}else{return false;}" />&nbsp;
                        		</acl:checkButton> 
                        		<acl:checkButton buttonName="btnReset">
									<input type="reset" name="btnReset" class="button" value="清　除" onclick="resetForm();" />
								</acl:checkButton></td>
						</tr>
						<tr>
							<td width="30%" align="right"><span class="needtxt">＊</span>
								<span class="issuetitle_R_down">受理編號：</span></td>
							<td>
								<html:text styleId="apNo" property="apNo" size="12" maxlength="12" styleClass="textinput" onkeyup="this.value=asc(this.value).toUpperCase();;autotab(this, $('btnQuery'));" onkeypress="" />
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
