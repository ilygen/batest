<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BAPA0X151C" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
<script language="javascript" type="text/javascript">
<%-- 畫面清空重設 --%>
<%-- begin ... [ --%>
	function resetForm() {		
		initAll();
	}
<%-- ] ... end --%>

<%-- 頁面欄位值初始化  --%>      
	function initAll(){
		$('apNo').value = "<c:out value="${ResetPaymentParameterCase.apNo}" />";
		funcType.checked = false;
	}


	function checkFields() {

		if (document.getElementsByName("funcType")[0].checked == true
				|| document.getElementsByName("funcType")[1].checked == true
				|| document.getElementsByName("funcType")[2].checked == true) {
			if (document.getElementsByName("funcType")[0].checked == true) {
				if (confirm("請確認是否『重讀 CIPB』??") == true) {
					return true;
				} else {
					return false;
				}
			} else if (document.getElementsByName("funcType")[1].checked == true) {
				if (confirm("請確認是否『清除 CPI 基準年月』??") == true) {
					return true;
				} else {
					return false;
				}
			} else if (document.getElementsByName("funcType")[2].checked == true) {
				if (confirm("請確認是否『清除計算平均薪資申請年度』??") == true) {
					return true;
				} else {
					return false;
				}
			}

		} else {
			alert("請選擇選項名稱。");
			return false;
		}

	}

	Event.observe(window, 'load', initAll, false);
</script>
</head>
<body scroll="no">

	<div id="mainContent">

		<%@ include file="/includes/ba_header.jsp"%>

		<%@ include file="/includes/ba_menu.jsp"%>

		<div id="main" class="mainBody">
			<html:form action="/resetPaymentParameter" method="post">
				<html:hidden styleId="page" property="page" value="1" />
				<html:hidden styleId="method" property="method" value="" />
				<html:hidden styleId="apNo" property="apNo" />
				<html:hidden styleId="baappbaseId" property="baappbaseId" />

				<fieldset>
					<legend>&nbsp;重設案件給付參數資料&nbsp;</legend>

					<div align="right" id="showtime">網頁下載時間：民國&nbsp;<func:nowDateTime /></div>

					<table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
						<tr>
							<td colspan="6" align="right">
								<acl:checkButton buttonName="btnQuery">
									<input type="button" name="btnQuery" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doUpdate'; if(checkFields()){document.ResetPaymentParameterForm.submit();}else{return false;}" />&nbsp;
                        		</acl:checkButton> 
                        		<acl:checkButton buttonName="btnReset">
									<input type="reset" name="btnReset" class="button" value="清　除" onclick="resetForm();" />
								</acl:checkButton>
                        		<acl:checkButton buttonName="btnBack">
									<input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.ResetPaymentParameterForm.submit();" />
								</acl:checkButton></td>
						</tr>
						<tr align="center">
							<td><bean:define id="detail" name="<%=ConstantKey.RESET_PAYMENT_PARAMETER_CASE%>" />
								<table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
									<tr>
										<td width="33%"><span class="issuetitle_L_down">受理編號：</span>
											<c:out value="${detail.apNoStrDisplay}" /></td>
										<td width="33%"><span class="issuetitle_L_down">處理狀態：</span>
											<c:out value="${detail.procStatStr}" /></td>
										<td width="34%"><span class="issuetitle_L_down">申請項目：</span>
											<c:out value="${detail.apItemStr}" /></td>
									</tr>

									<tr>
										<td><span class="issuetitle_L_down">事故者姓名：</span> 
											<c:out value="${detail.evtName}" /></td>
										<td><span class="issuetitle_L_down">事故者身分證號：</span> 
											<c:out value="${detail.evtIdnNo}" /></td>
										<td><span class="issuetitle_L_down">事故者出生日期：</span> 
											<c:out value="${detail.evtBrDateStr}" />&nbsp;</td>
									</tr>

									<tr>
										<td colspan="6">&nbsp;</td>
									</tr>
									<tr>
										<td><span class="issuetitle_L_down">選           項</span> 
											</td>
										<td><span class="issuetitle_L_down">相關欄位現有內容</span> 
											</td>
										<td><span class="issuetitle_L_down">&nbsp;</span> 
											</td>
									</tr>
									<tr>
										<td colspan="6"><hr style="border:1px dashed #000; height:1px"></td>
									</tr>
									<tr>
										<td><input type="radio" id="funcType" name="funcType" value="1"  onclick="$('funcType').value='1';" />
											<span class="issuetitle_L_down">重讀 CIPB</span> 
											</td>
										<td><span class="issuetitle_L_down">平均薪資：</span> 
											<fmt:formatNumber value="${detail.insAvgAmt}" />&nbsp;</td>
										<td><span class="issuetitle_L_down">&nbsp;</span> 
											</td>
									</tr>
									<tr>
										<td><span class="issuetitle_L_down">&nbsp;</span> 
											</td>
										<td><span class="issuetitle_L_down">實付年資：</span> 
											<c:out value="${detail.aplPaySeniY}" />年
                                            <c:out value="${detail.aplPaySeniM}" />月</td>
										<td><span class="issuetitle_L_down">&nbsp;</span> 
											</td>
									</tr>
									<tr>
										<td><input type="radio" id="funcType" name="funcType" value="2"  onclick="$('funcType').value='2';" />
											<span class="issuetitle_L_down">清除 CPI 基準年月</span> 
											</td>
										<td><span class="issuetitle_L_down">基準年月：</span> 
											<c:out value="${detail.cpiBaseYM}" /></td>
										<td><span class="issuetitle_L_down">&nbsp;</span> 
											</td>
									</tr>
									<tr>
										<td><input type="radio" id="funcType" name="funcType" value="3"  onclick="$('funcType').value='3';" />
											<span class="issuetitle_L_down">清除計算平均薪資申請年度</span> 
											</td>
										<td><span class="issuetitle_L_down">申請年度：</span> 
											<c:out value="${detail.insAvgAmtAppYear}" /></td>
										<td><span class="issuetitle_L_down">&nbsp;</span> 
											</td>
									</tr>
								</table></td>
						</tr>


						<tr>
							<td colspan="6">&nbsp;</td>
						</tr>
					</table>
				</fieldset>

			</html:form>
		</div>
	</div>

	<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
