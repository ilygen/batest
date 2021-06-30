<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.REVIEW_FEE_RECEIPT_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BARE0D012C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script language="javascript" type="text/javascript">
    <!--
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/reviewFeeReceipt" method="post">
        
        <fieldset>
            <legend>&nbsp;複檢費用受理作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="7">
                        <html:hidden styleId="page" property="page" value="3" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="listApSeq" property="listApSeq" value="" />
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" id="btnBack" name="btnBack" tabindex="100" class="button" value="返　回" onclick="$('page').value='3'; $('method').value='doBackToQueryPage'; document.ReviewFeeReceiptForm.submit();" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="7" class="issuetitle_L">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">複檢費用受理編號：</span>
                                    <c:out value="${caseData.reApNoString}" />
                                </td>
                                <td colspan="3">
                                    &nbsp;
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
                <tr align="center">
                    <td colspan="7" align="right" valign="bottom">
                        <display:table name="pageScope.caseData.detailList" id="listItem" pagesize="10" excludedParams="*">
                            <display:column title="序&nbsp;號" style="width:10%; text-align:center;">
                                &nbsp;<c:out value="${listItem_rowNum}" />&nbsp;
                            </display:column>
                            <display:column title="&nbsp;通知複檢日期&nbsp;" style="width:14%; text-align:center;">
                                &nbsp;<c:out value="${listItem.inreDateString}" />&nbsp;
                            </display:column>
                            <display:column title="&nbsp;複檢費用申請日期&nbsp;" style="width:15%; text-align:center;">
                                &nbsp;<c:out value="${listItem.recosDateString}" />&nbsp;
                            </display:column>
                            <display:column title="&nbsp;複檢費用&nbsp;" style="width:15%; text-align:right;">
                                &nbsp;<fmt:formatNumber value="${listItem.reFees}" pattern='###,###' />&nbsp;
                            </display:column>
                            <display:column title="&nbsp;非複檢必須費用&nbsp;" style="width:15%; text-align:right;">
                                &nbsp;<fmt:formatNumber value="${listItem.nonreFees}" pattern='###,###' />&nbsp;
                            </display:column>
                            <display:column title="&nbsp;複檢實付金額&nbsp;" style="width:15%; text-align:right;">
                                &nbsp;<fmt:formatNumber value="${listItem.reAmtPay}" pattern='###,###' />&nbsp;
                            </display:column>
                            <display:column title="&nbsp;資料明細&nbsp;" style="width:16%; text-align:center;">
                                <input type="button" name="btnDetail" class="button" value="修　改" onclick="$('page').value='3'; $('method').value='doQueryModifyData'; $('listApSeq').value='<c:out value="${listItem.apSeq}" />'; document.ReviewFeeReceiptForm.submit();" />
                            </display:column>
                        </display:table>
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
