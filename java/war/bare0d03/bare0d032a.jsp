<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.REVIEW_FEE_DECISION_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BARE0D032A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script language="javascript" type="text/javascript">
    <!--
    Event.stopObserving(window, 'load', inputFieldFocus);
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/reviewFeeDecision" method="post">
        
        <fieldset>
            <legend>&nbsp;複檢費用決行作業&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right">
                        <html:hidden styleId="page" property="page" value="3" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" id="btnBack" name="btnBack" tabindex="102" class="button" value="返　回" onclick="$('page').value='3'; $('method').value='doBackToListPage'; if (confirm('確定離開？')) { history.back(); } else {return false;}" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">複檢費用受理編號：</span>
                                    <c:out value="${caseData.reApNoString}" />
                                </td>
                                <td colspan="3">
                                    <span class="issuetitle_L_down">複檢費用申請序：</span>
                                    <c:out value="${caseData.detailData.apSeq}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${caseData.apNoString}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">給付別：</span>
                                    失能年金
                                </td>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${caseData.appDateString}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${caseData.evtName}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${caseData.evtIdnNo}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${caseData.evtBrDateString}" />
                                </td>
                            </tr>
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">診斷失能日期：</span>
                                    <c:out value="${caseData.evtJobDateString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">失能項目：</span>
                                    <c:out value="${caseData.criInJdpString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">失能等級：</span>
                                    <c:out value="${caseData.criInJclString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">傷病分類：</span>
                                    <c:out value="${caseData.evTypString}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                            <tr>
                                <td colspan="3" class="issuetitle_L">
                                    <span class="systemMsg">▲</span>&nbsp;申請資料
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">通知複檢日期：</span>
                                    <c:out value="${caseData.detailData.inreDateString}" />
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">複檢原因：</span>
                                    <c:out value="${caseData.detailData.reasString}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <span class="issuetitle_L_down">複檢醫院代碼：</span>
                                    <c:out value="${caseData.detailData.hosString}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">複檢費用申請日期：</span>
                                    <c:out value="${caseData.detailData.recosDateString}" />
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">複檢門診次數：</span>
                                    <fmt:formatNumber value="${caseData.detailData.reNum}" pattern='###,###' />
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">複檢住院天數：</span>
                                    <fmt:formatNumber value="${caseData.detailData.rehpStay}" pattern='###,###' />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">複檢費用：</span>
                                    <fmt:formatNumber value="${caseData.detailData.reFees}" pattern='###,###' />
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">非複檢必須費用：</span>
                                    <fmt:formatNumber value="${caseData.detailData.nonreFees}" pattern='###,###' />
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">複檢實付金額：</span>
                                    <fmt:formatNumber value="${caseData.detailData.reAmtPay}" pattern='###,###' />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" class="issuetitle_L">
                                    <br />
                                    <span class="systemMsg">▲</span>&nbsp;受款人資料
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">關　係：</span>
                                    <c:out value="${caseData.detailData.benEvtRelString}" />
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">核定格式：</span>
                                    <c:out value="${caseData.detailData.notifyForm}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <span class="issuetitle_L_down">受款人姓名：</span>
                                    <c:out value="${caseData.detailData.benName}" />
                                </td>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">受款人身分證號：</span>
                                    <c:out value="${caseData.detailData.benIdnNo}" />
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">受款人出生日期：</span>
                                    <c:out value="${caseData.detailData.benBrDateString}" />
                                </td>
                            </tr>
                            <tr>
                                <td width="34%" id="iss">
                                    <span class="issuetitle_L_down">國籍別：</span>
                                    <c:out value="${caseData.detailData.benNationTypString}" />
                                </td>
                                <td width="33%" id="iss">
                                    <span class="issuetitle_L_down">性　別：</span>
                                    <c:out value="${caseData.detailData.benSexString}" />
                                </td>
                                <td width="33%" id="iss">
                                    <span class="issuetitle_L_down">國　籍：</span>
                                    <c:out value="${caseData.detailData.benNationString}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">給付方式：</span>
                                    <c:out value="${caseData.detailData.payTypString}" />
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">帳　號：</span>
                                    <c:out value="${caseData.detailData.accountNoString}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <span class="issuetitle_L_down">戶　名：</span>
                                    <c:out value="${caseData.detailData.accName}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">電　話1：</span>
                                    <c:out value="${caseData.detailData.tel1}" />
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">電　話2：</span>
                                    <c:out value="${caseData.detailData.tel2}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <span class="issuetitle_L_down">地　址：</span>
                                    <c:out value="${caseData.detailData.commTypString}" />
                                    &nbsp;-&nbsp;
                                    <c:out value="${caseData.detailData.commZip}" />
                                    <c:out value="${caseData.detailData.commAddr}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </fieldset>
        
        </html:form>

        <p></p>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
