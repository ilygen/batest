<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.REVIEW_FEE_DECISION_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BARE0D031A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script language="javascript" type="text/javascript">
    <!--
    function doSelectAllReCheckList(obj) {
        selectAll(obj, "reCheckList");
        
        <c:if test="${caseData.showDecision eq true}">
            $("selectAllDecisionList").checked = false;
            doSelectNone("decisionList");
        </c:if>
        
        <c:if test="${caseData.showReturn eq true}">
            $("selectAllReturnList").checked = false;
            doSelectNone("returnList");
        </c:if>
    }
    
    function doSelectAllDecisionList(obj) {
        selectAll(obj, "decisionList");
        
        <c:if test="${caseData.showReCheck eq true}">
            $("selectAllReCheckList").checked = false;
            doSelectNone("reCheckList");
        </c:if>
        
        <c:if test="${caseData.showReturn eq true}">
            $("selectAllReturnList").checked = false;
            doSelectNone("returnList");
        </c:if>
    }
    
    function doSelectAllReturnList(obj) {
        selectAll(obj, "returnList");

        <c:if test="${caseData.showReCheck eq true}">
            $("selectAllReCheckList").checked = false;
            doSelectNone("reCheckList");
        </c:if>

        <c:if test="${caseData.showDecision eq true}">
            $("selectAllDecisionList").checked = false;
            doSelectNone("decisionList");
        </c:if>
    }
    
    function doSelectNone(elements) {
        if (typeof elements == 'string')
            elements = document.getElementsByName(elements);

        for (i = 0; i < elements.length; i++) {
            if(elements[i].disabled != true && elements[i].readonly != true){
                elements[i].checked = false;
            }
        }
    }
    
    function doCheckbox(obj) {
        if (obj.checked) {
            if (obj.name == "reCheckList")  {
                <c:if test="${caseData.showDecision eq true}">
                    doUncheck("decisionList", obj.value);
                </c:if>

                <c:if test="${caseData.showReturn eq true}">
                    doUncheck("returnList", obj.value);
                </c:if>
            }
            else if (obj.name == "decisionList") {
                <c:if test="${caseData.showReCheck eq true}">
                    doUncheck("reCheckList", obj.value);
                </c:if>

                <c:if test="${caseData.showReturn eq true}">
                    doUncheck("returnList", obj.value);
                </c:if>
            }
            else if (obj.name == "returnList") {
                <c:if test="${caseData.showReCheck eq true}">
                    doUncheck("reCheckList", obj.value);
                </c:if>

                <c:if test="${caseData.showDecision eq true}">
                    doUncheck("decisionList", obj.value);
                </c:if>
            }
        }
    }
    
    function doUncheck(elements, apSeq) {
        if (typeof elements == 'string')
            elements = document.getElementsByName(elements);

        for (i = 0; i < elements.length; i++) {
            if(elements[i].disabled != true && elements[i].readonly != true) {
                if (elements[i].value == apSeq) {
                    elements[i].checked = false;
                    break;
                }
            }
        }
    }
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
                    <td align="right" colspan="10">
                        <html:hidden styleId="page" property="page" value="3" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="listApSeq" property="listApSeq" value="" />
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" id="btnConfirm" name="btnConfirm" tabindex="100" class="button" value="確　認" onclick="$('page').value='2'; $('method').value='doSave'; document.ReviewFeeDecisionForm.submit();" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" id="btnBack" name="btnBack" tabindex="102" class="button" value="返　回" onclick="$('page').value='2'; $('method').value='doBackToQueryPage'; if (confirm('確定離開？')) { document.ReviewFeeDecisionForm.submit(); } else {return false;}" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="10" class="issuetitle_L">
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
                    <td colspan="10" align="right" valign="bottom">
                        <display:table name="pageScope.caseData.detailList" id="listItem" pagesize="10" excludedParams="*">
                            <display:column title="序&nbsp;號" style="width:3%; text-align:center;">
                                &nbsp;<c:out value="${listItem_rowNum}" />&nbsp;
                            </display:column>
                            <c:choose>
                                <c:when test="${caseData.showReCheck eq true}">
                                    <display:column title='<input id="selectAllReCheckList" name="selectAllReCheckList" type="checkbox" onclick="doSelectAllReCheckList(this);">全選<br />複&nbsp;核' style="width:6%; text-align:center;">
                                        <html:multibox property="reCheckList" onclick="doCheckbox(this);">
                                            <c:out value="${listItem.apSeq}" />
                                        </html:multibox>
                                    </display:column>
                                </c:when>
                                <c:otherwise>
                                    <display:column title="複&nbsp;核" style="width:6%; text-align:center;">
                                        &nbsp;
                                    </display:column>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${caseData.showDecision eq true}">
                                    <display:column title='<input id="selectAllDecisionList" name="selectAllDecisionList" type="checkbox" onclick="doSelectAllDecisionList(this);">全選<br />決&nbsp;行' style="width:6%; text-align:center;">
                                        <html:multibox property="decisionList" onclick="doCheckbox(this);">
                                            <c:out value="${listItem.apSeq}" />
                                        </html:multibox>
                                    </display:column>
                                </c:when>
                                <c:otherwise>
                                    <display:column title="決&nbsp;行" style="width:6%; text-align:center;">
                                        &nbsp;
                                    </display:column>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${caseData.showReturn eq true}">
                                    <display:column title='<input id="selectAllReturnList" name="selectAllReturnList" type="checkbox" onclick="doSelectAllReturnList(this);">全選<br />退&nbsp;件' style="width:6%; text-align:center;">
                                        <html:multibox property="returnList" onclick="doCheckbox(this);">
                                            <c:out value="${listItem.apSeq}" />
                                        </html:multibox>
                                    </display:column>
                                </c:when>
                                <c:otherwise>
                                    <display:column title="退&nbsp;件" style="width:6%; text-align:center;">
                                        &nbsp;
                                    </display:column>
                                </c:otherwise>
                            </c:choose>
                            <display:column title="&nbsp;通知複檢日期&nbsp;" style="width:15%; text-align:center;">
                                &nbsp;<c:out value="${listItem.inreDateString}" />&nbsp;
                            </display:column>
                            <display:column title="&nbsp;複檢費用申請日期&nbsp;" style="width:15%; text-align:center;">
                                &nbsp;<c:out value="${listItem.recosDateString}" />&nbsp;
                            </display:column>
                            <display:column title="&nbsp;複檢費用&nbsp;" style="width:13%; text-align:right;">
                                &nbsp;<fmt:formatNumber value="${listItem.reFees}" pattern='###,###' />&nbsp;
                            </display:column>
                            <display:column title="&nbsp;非複檢必須費用&nbsp;" style="width:13%; text-align:right;">
                                &nbsp;<fmt:formatNumber value="${listItem.nonreFees}" pattern='###,###' />&nbsp;
                            </display:column>
                            <display:column title="&nbsp;複檢實付金額&nbsp;" style="width:13%; text-align:right;">
                                &nbsp;<fmt:formatNumber value="${listItem.reAmtPay}" pattern='###,###' />&nbsp;
                            </display:column>
                            <display:column title="&nbsp;資料明細&nbsp;" style="width:10%; text-align:center;">
                                <input type="button" name="btnDetail" class="button" value="明細資料" onclick="$('page').value='2'; $('method').value='doQueryDetailData'; $('listApSeq').value='<c:out value="${listItem.apSeq}" />'; document.ReviewFeeDecisionForm.submit();" />
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
