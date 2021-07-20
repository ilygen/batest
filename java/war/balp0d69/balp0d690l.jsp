<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BALP0D690L" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<html:javascript formName="BatchPaymentReceiveForm" page="1" />
<script language="javascript" type="text/javascript">
<!--
	//頁面欄位值初始化       
	function initAll() {

	}

	function checkFields() {

		var msg = "";
		<%--
		if ($("receiveType").value == "2") {
			if (Trim($F("seqNo")).length == 0) {
				msg += "請輸入「受款人序」。\r\n";
				$("seqNo").focus();
			}
			if (Trim($F("seqNo")).length != 0 && Trim($F("seqNo")).length != 4
					&& !isNaN($("seqNo").value)) {
				msg += "請輸入完整「受款人序」。\r\n";
				$("seqNo").focus();
			}
		}
		if (isNaN($("seqNo").value)) {
			msg += "「受款人序」格式錯誤。\r\n";
			$("seqNo").focus();
		}
		if ($("receiveType").value == "") {
			msg += "「收回狀況」為必填欄位。\r\n";

		}

		if (msg != "") {
			alert(msg);
			return false;
		} else {
			return true;
		}--%>
	}

	function initAllForClean() {
		$("payCode").value = "";
		$("chkDate").value = "";
	}
<%-- 畫面重設 --%>
	function resetForm() {
		//cleanForm();
		initAllForClean();
	}

	Event.observe(window, 'load', initAll, false);
</script>
</head>
<body scroll="no">

	<div id="mainContent">

		<%@ include file="/includes/ba_header.jsp"%>

		<%@ include file="/includes/ba_menu.jsp"%>

		<div id="main" class="mainBody">
			<html:form action="/batchPaymentReceive" method="post" onsubmit="return validateBatchPaymentReceiveForm(this);">
				<html:hidden styleId="page" property="page" value="1" />
				<html:hidden styleId="method" property="method" value="" />

				<fieldset>
					<legend>&nbsp;整批收回核定清單&nbsp;</legend>

					<div align="right" id="showtime">
						網頁下載時間：民國&nbsp;
						<func:nowDateTime />
					</div>

					<table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
						<tr>
							<td colspan="2" align="right">
								<acl:checkButton buttonName="btnQuery">
									<input type="button" name="btnQuery" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doConfirmCashReceive'; if(document.BatchPaymentReceiveForm.onsubmit() ){document.BatchPaymentReceiveForm.submit();}else{return false;}" />&nbsp;
                        		</acl:checkButton> &nbsp; 
                        		<acl:checkButton buttonName="btnClear">
									<input name="btnClear" type="reset" class="button" value="清　除 " onclick="resetForm()">
								</acl:checkButton></td>
						</tr>
						<tr>
							<td width="30%" align="right">
								<span class="needtxt">＊</span>
								<span class="issuetitle_R_down">功能選項：</span></td>
							<td><span class="formtxt"> 
								<html:radio	styleId="reportType" property="reportType" tabindex="4"	value="1" onclick="occurProgress();" />列印 &nbsp; 
								<html:radio	styleId="reportType" property="reportType" tabindex="4"	value="2" onclick="occurProgress();" />補印
							</span></td>
						</tr>
						<tr>
							<td width="30%" align="right">
								<span class="needtxt">＊</span>
								<span class="issuetitle_R_down">給付別：</span></td>
							<td>
								<html:select styleId="payCode" property="payCode" onchange="chgPayCode();" tabindex="1">
									<html:option value="">請選擇</html:option>
									<html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>"	property="paramCode" labelProperty="paramName" />
								</html:select>
							</td>
						</tr>
						<tr>
							<td width="30%" align="right">
								<span class="needtxt">＊</span> 
								<span class="issuetitle_R_down">核收日期：</span></td>
							<td>
								<html:text property="chkDate" styleId="chkDate"	tabindex="3" styleClass="textinput" maxlength="7" size="10"	onblur="this.value = asc(this.value);" />
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2">
								<hr size="1" noshade> <span class="titleinput">※注意事項：</span>
								<br> &nbsp;1. <span class="needtxt">＊</span>為必填欄位。<br>
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
