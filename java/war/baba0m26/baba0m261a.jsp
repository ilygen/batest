<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BABA0M261A" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
<html:javascript formName="CompPaymentForm" page="1" />

</head>
<body scroll="no">

	<div id="mainContent">

		<%@ include file="/includes/ba_header.jsp"%>

		<%@ include file="/includes/ba_menu.jsp"%>

		<div id="main" class="mainBody">
			<html:form action="/survivorPayment" method="post" onsubmit="return validateCompPaymentForm(this);">
				<html:hidden styleId="page" property="page" value="1" />
				<html:hidden styleId="method" property="method" value="" />
				<fieldset>
					<legend>&nbsp;遺屬年金補正核付新增作業&nbsp;</legend>

					<div align="right" id="showtime">
						網頁下載時間：民國&nbsp;
						<func:nowDateTime />
					</div>

					<table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
						<tr>
							<td align="right" colspan="7">
                                <acl:checkButton buttonName="btnConfirm">
                                    <input tabindex="260" type="button" name="btnConfirm" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='insertPageDoConfirm'; if (document.CompPaymentForm.onsubmit()){document.CompPaymentForm.submit();}else{return false;}" />&nbsp;
                                </acl:checkButton>
                                <acl:checkButton buttonName="btnBack">
                                    <input tabindex="280" type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='2'; $('method').value='doBack'; document.CompPaymentForm.submit();"/>
                                </acl:checkButton>      
						</tr>
                        <tr>
                            <td colspan="7">
                                <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                    <tr>
                                        <td width="33%"><span class="issuetitle_L_down">受理編號：</span>
                                            <c:out value="${CompPaymentCase[0].apNoStrDisplay}" />
                                        </td>
                                        <td><span class="issuetitle_L_down">給付別：</span>遺屬年金</td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="7" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                                    <tr>
                                        <td colspan="4" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;案件資料</td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4"><span class="issuetitle_L_down">事故者姓名：</span>
                                            <c:out value="${CompPaymentCase[0].evtName}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="25%" id="iss"><span class="issuetitle_L_down">事故者身分證號：</span>
                                            <c:out value="${CompPaymentCase[0].evtIdnNo}" />
                                        </td>
                                        <td width="25%" id="iss">　<span class="issuetitle_L_down">事故者出生日期：</span>
                                            <c:out value="${CompPaymentCase[0].evtBrDateDisplay}" />
                                        </td>
                                        <td width="25%" id="iss"><span class="issuetitle_L_down">申請日期：</span>
                                            <c:out value="${CompPaymentCase[0].appDateDisplay}" />
                                        </td>
                                        <td width="25%" id="iss"><span class="issuetitle_L_down">事故日期：</span>
										    <c:out value="${CompPaymentCase[0].evtJobDateDisplay}" />
										</td>
                                    </tr>
                                    <tr>
                                        <td id="iss"><span class="issuetitle_L_down">核定年月：</span>
                                            <c:out value="${CompPaymentCase[0].issuYmDisplay}" />
                                        </td>
                                        <td id="iss"><span class="needtxt">＊</span>
                                                     <span class="issuetitle_L_down">核定日期：</span>
                                                     <html:text styleId="chkDate" property="chkDate" value="${CompPaymentCase[0].chkDate}" size="10" maxlength="7" styleClass="textinput" onkeyup="autotab(this, $('aplPayDate'));" />
                                        </td>
                                        <td id="iss"><span class="needtxt">＊</span>
                                                     <span class="issuetitle_L_down">核付日期：</span>
                                                     <html:text styleId="aplPayDate" property="aplPayDate" value="${CompPaymentCase[0].aplPayDate}" size="10" maxlength="7" styleClass="textinput" onkeyup="autotab(this, $('remitDate'));" />
                                        </td>
                                        <td id="iss"><span class="needtxt">＊</span>
                                                     <span class="issuetitle_L_down">入帳日期：</span>
                                                     <html:text styleId="remitDate" property="remitDate" value="${CompPaymentCase[0].remitDate}" size="10" maxlength="7" styleClass="textinput" onkeyup="autotab(this, $('btnConfirm'));" />
                                        </td>
                                        </tr>
                                        <tr>
                                            <td colspan="4"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="7" class="issuetitle_L"><br><span class="issuetitle_search">&#9658;</span>&nbsp;核付資料：</td>
                            </tr>
                            <tr align="center">
                                <td width="15%" class="issuetitle_L">給付年月</td>
                                <td width="15%" class="issuetitle_L">受款人序</td>
                                <td width="25%" class="issuetitle_L">受款人姓名</td>
                                <td width="10%" class="issuetitle_L">受款人身分證號</td>
                                <td width="15%" class="issuetitle_L">關 係</td>
                                <td width="10%" class="issuetitle_L">核定金額</td>
                                <td width="10%" class="issuetitle_L">實付金額</td>
                            </tr>
                            <bean:define id="CompPaymentCase" name="<%=ConstantKey.COMP_PAYMENT_CASE%>" />
                            <c:forEach var="list" items="${CompPaymentCase}">
                                <tr>
	                                <td class="issueinfo_C">${list.payYmDisplay}</td>
	                                <td class="issueinfo_C">${list.seqNo}</td>
	                                <td class="issueinfo">${list.benName}</td>
	                                <td class="issueinfo">${list.benIdnNo}</td>
	                                <td class="issueinfo">${list.benEvtRelDisplay}</td>
	                                <td class="issueinfo_R"><fmt:formatNumber value="${list.issueAmt}" /></td>
	                                <td class="issueinfo_R"><fmt:formatNumber value="${list.aplPayAmt}" /></td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="7"></td>
                            </tr>
                            <tr>
                                <td colspan="7">
                                    <hr size="1" noshade>
                                    <span class="titleinput">※注意事項：</span><br>
                                     　1.&nbsp;有關年月的欄位，填寫格式如民國108年1月1日，請輸入1080101。<br>
                              　      2.&nbsp;<span class="needtxt">＊</span>為必填欄位。
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
