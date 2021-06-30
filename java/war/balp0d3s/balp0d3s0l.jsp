<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BALP0D3S0L" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
<html:javascript formName="MonthlyRpt30Form" page="1" />
<script language="javascript" type="text/javascript">
	function checkFields() {
		var payCode = $("payCode").value;
		var rptKind = $("rptKind").value;
		var issuYm = $("issuYm").value;

		if (payCode == "") {
			alert("「給付別」為必填欄位。");
			$("payCode").focus();
			return false;
		}
		
		if (rptKind == "") {
			alert("「報表類別」為必填欄位。");
			$("rptKind").focus();
			return false;
		}
		
		if (issuYm == "") {
			alert("「核定年月」為必填欄位。");
			$("issuYm").focus();
			return false;
		}

		return true;
	}

	function initAll() {

	}

	Event.observe(window, 'load', initAll, false);
</script>
</head>
<body scroll="no">

	<div id="mainContent">

		<%@ include file="/includes/ba_header.jsp"%>

		<%@ include file="/includes/ba_menu.jsp"%>

		<div id="main" class="mainBody">
			<html:form action="/printMonthlyRpt30" method="post" onsubmit="return validateMonthlyRpt30Form(this);">

				<fieldset>
					<legend>&nbsp;查核失能程度通知函&nbsp;</legend>

					<div align="right" id="showtime">網頁下載時間：民國&nbsp;<func:nowDateTime /></div>

					<table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
						<tr>
							<td colspan="2" align="right">
							<html:hidden styleId="page" property="page" value="1" /> 
							<html:hidden styleId="method" property="method" value="" /> 
                                <acl:checkButton buttonName="btnPrint"><input type="button" name="btnPrint" class="button" value="列  印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.MonthlyRpt30Form.onsubmit() && checkFields()){document.MonthlyRpt30Form.submit();}else{return false;}" /></acl:checkButton> &nbsp; 
								<acl:checkButton buttonName="btnClear"><input type="reset" name="btnClear" class="button" value="清  除" /></acl:checkButton>
							</td>
						</tr>
						<tr>
							<td width="30%" align="right"><span class="needtxt">＊</span>
								<span class="issuetitle_R_down">給付別：</span></td>
							<td><html:select styleId="payCode" property="payCode" tabindex="1">
									<html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
								</html:select></td>
						</tr>
						<tr>
							<td width="30%" align="right"><span class="needtxt">＊</span>
								<span class="issuetitle_R_down">報表類別：</span></td>
							<td><html:select property="rptKind" styleId="rptKind">
									<html:option value="">請選擇</html:option>
									<html:option value="1">1. 重新查核失能程度通知函</html:option>
									<html:option value="2">2. 通過查核續發失能年金通知函</html:option>
								</html:select></td>
						</tr>
						<tr>
							<td width="30%" align="right"><span class="needtxt">＊</span>
								<span class="issuetitle_R_down">核定年月：</span></td>
							<td><html:text styleId="issuYm" property="issuYm" tabindex="2" size="10" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" /></td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2">
								<hr size="1" noshade> <span class="titleinput">※注意事項：</span>
								<br>&nbsp; <span class="needtxt">＊</span>為必填欄位。
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
