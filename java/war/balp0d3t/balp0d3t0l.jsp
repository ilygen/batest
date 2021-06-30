<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BALP0D3T0L" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
<html:javascript formName="MonthlyRpt31Form" page="1" />
<script language="javascript" type="text/javascript">
	
<%-- begin 檢查必填欄位 --%>
	function checkFields() {
		if (Trim($F("payCode")) == "") {
			alert('「給付別」：為必輸欄位。\r\n');
		} else {
			return true;
		}

		if (rptKind == "") {
			alert("「報表格式」為必填欄位。");
			$("rptKind").focus();
			return false;
		}

		return false;
	}
<%-- ] ... end --%>
	
<%-- 畫面清空重設 --%>
	
<%-- begin ... [ --%>
	function resetForm() {
		document.MonthlyRpt31Form.reset();
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
		<html:form action="/printMonthlyRpt31" method="post" onsubmit="return validateMonthlyRpt31Form(this);">

			<fieldset>
				<legend>&nbsp;老年差額金通知&nbsp;</legend>

				<div align="right" id="showtime">網頁下載時間：民國&nbsp;<func:nowDateTime /></div>

				<table width="98%" border="0" align="center" cellpadding="2"
					cellspacing="2" class="px13">
					<tr>
						<td colspan="2" align="right">
							<html:hidden styleId="page"	property="page" value="1" /> 
							<html:hidden styleId="method" property="method" value="" /> 
							<acl:checkButton buttonName="btnPrint">
								<input name="btnPrint" type="button" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if ( document.MonthlyRpt31Form.onsubmit()  ){document.MonthlyRpt31Form.submit();}else{return false;}" />&nbsp;
                        	</acl:checkButton> 
                        	<acl:checkButton buttonName="btnReset">
								<input name="btnReset" type="reset" class="button" value="清　除" onclick="resetForm();">
							</acl:checkButton></td>
					</tr>


					<tr>
						<td width="30%" align="right"><span class="needtxt">＊</span>
							<span class="issuetitle_R_down">受理編號：</span></td>
						<td>
								<html:text styleId="apNo1" property="apNo1" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo2'));" onkeypress="" /> &nbsp;-&nbsp; 
								<html:text styleId="apNo2" property="apNo2" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo3'));" /> &nbsp;-&nbsp; 
								<html:text styleId="apNo3" property="apNo3" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo4'));" /> &nbsp;-&nbsp; 
								<html:text styleId="apNo4" property="apNo4" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" />
						</td>
					</tr>
					<tr>
						<td width="30%" align="right"><span class="needtxt">＊</span>
							<span class="issuetitle_R_down">報表格式：</span></td>
						<td><html:select property="rptKind" styleId="rptKind">
								<html:option value="">請選擇</html:option>
								<html:option value="001">001 無差額</html:option>
								<html:option value="002">002 有差額及未及入帳金額</html:option>
								<html:option value="003">003 有差額、無未及入帳金額</html:option>
								<html:option value="001U">001 無差額（催辦）</html:option>
								<html:option value="002U">002 有差額及未及入帳金額（催辦）</html:option>
								<html:option value="003U">003 有差額、無未及入帳金額（催辦）</html:option>
								<html:option value="001P">001 無差額（延不補正）</html:option>
								<html:option value="002P">002 有差額及未及入帳金額（延不補正）</html:option>
								<html:option value="003P">003 有差額、無未及入帳金額（延不補正）</html:option>
							</html:select></td>
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
