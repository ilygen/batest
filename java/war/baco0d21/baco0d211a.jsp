<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BACO0D211A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
          
    }    
    
    <%-- 確認按鈕 --%>
    <%-- 按下 確認按鈕時  檢查checkbox是否有勾選的資料 --%>    
    <%-- begin ... [ --%>
    function checkFields() {

    }
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/survivorPaymentReview" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="apNo" property="apNo" value="" />
        <html:hidden styleId="apNoOfConfirm" property="apNoOfConfirm" />
        <html:hidden styleId="dataIndex" property="dataIndex" value="" />
        
        <fieldset>
            <legend>&nbsp;遺屬年金給付審核作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.SurvivorPaymentReviewForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>    
                <tr>
                    <td colspan="11" class="issuetitle_L">
                        <bean:define id="payTitle" name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_CASE_FOR_TITLE%>" />                        
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:if test='${(payTitle.pagePayKind eq "L")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                    </c:if>
                                    <c:if test='${(payTitle.pagePayKind eq "K")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                    </c:if>
                                    <c:if test='${(payTitle.pagePayKind eq "S")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                    </c:if>
                                </td>
                                <td width="25%">
                                <%-- 
                                    <span class="issuetitle_L_down">清單種類：</span>
                                    <c:if test='${payTitle.rptTyp eq "001"}'>
                                        <c:out value="<%=ConstantKey.BAEXALIST_RPTTYP_STR_001 %>" />
                                    </c:if>
                                    <c:if test='${payTitle.rptTyp eq "002"}'>
                                        <c:out value="<%=ConstantKey.BAEXALIST_RPTTYP_STR_002 %>" />
                                    </c:if>                                    
                                --%>
                                </td>
                                <td width="25%">
                                <%--     
                                    <span class="issuetitle_L_down">清單列印日期：</span>
                                    <c:out value="${payTitle.rptDateStr}" />                                    
                                --%>
                                </td>
                                <td width="25%">
                                <%-- 
                                    <span class="issuetitle_L_down">頁　次：</span>
                                    <c:out value="${payTitle.pageNo}" />
                                --%>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                            <display:column title="受理編號" style="width:12%; text-align:center;">
                                <c:out value="${listItem.apNoStrDisplay}" />&nbsp;
                            </display:column>
                            <display:column title="事故者姓名" style="width:15%; text-align:left;">
                                <c:out value="${listItem.evtName}" />&nbsp;
                            </display:column>
                            <display:column title="事故者身分證號" style="width:12%; text-align:left;">
                                <c:out value="${listItem.evtIdnNo}" />&nbsp;
                            </display:column>
                            <display:column title="給付年月起迄" style="width:10%; text-align:center;">
                                <c:if test='${(listItem.paysYm eq listItem.payeYm)}'>
                                    <c:out value="${listItem.paysYmStr}" />
                                </c:if>
                                <c:if test='${(listItem.paysYm ne listItem.payeYm)}'>
                                    <c:out value="${listItem.paysYmStr}" />
                                    <c:if test='${(listItem.paysYmStr ne "")or(listItem.payeYmStr ne "")}'>
                                        &nbsp;/&nbsp;
                                    </c:if>
                                    <c:out value="${listItem.payeYmStr}" />
                                </c:if>&nbsp;
                            </display:column>
                            <display:column title="核定總金額" style="width:8%; text-align:right;">
                                <fmt:formatNumber value="${listItem.tissueAmt}" />
                                <c:if test="${listItem.tissueAmt eq null}" >
                                    &nbsp;
                                </c:if>
                            </display:column>
                            <display:column title="實付總金額" style="width:8%; text-align:right;">
                                <fmt:formatNumber value="${listItem.taplPayAmt}" />
                                <c:if test="${listItem.taplPayAmt eq null}" >
                                    &nbsp;
                                </c:if>
                            </display:column>
                            <display:column title="版 次" style="width:7%; text-align:center;">
                                <c:out value="${listItem.veriSeq}" />&nbsp;
                            </display:column>
                            <display:column title="資料別" style="width:9%; text-align:left;">
                                <c:if test='${(listItem.caseTyp eq "1")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_1%>" />
                                </c:if>
                                <c:if test='${(listItem.caseTyp eq "2")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_2%>" />
                                </c:if>
                                <c:if test='${(listItem.caseTyp eq "3")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_3%>" />
                                </c:if>
                                <c:if test='${(listItem.caseTyp eq "4")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_4%>" />
                                </c:if>&nbsp;
                            </display:column>
                            <display:column title='<span class="needtxt">人工審核結果</span>' style="width:10%; text-align:left;">
                                <font color="red">
                                <c:if test="${listItem.manchkMk eq 'Y'}">
                                    <c:out value="Y-合格" />
                                </c:if>
                                <c:if test="${listItem.manchkMk eq 'N'}">
                                    <c:out value="N-不合格" />
                                </c:if>
                                <c:if test="${(listItem.manchkMk ne 'Y')and(listItem.manchkMk ne 'N')}">
                                    <c:out value="待處理" />
                                </c:if>
                                </font>&nbsp;
                            </display:column>
                            <display:column title="資料明細" style="width:9%; text-align:center;">
                                <acl:checkButton buttonName="btnReview">
                                    <input type="button" name="btnReview" class="button" value="個案審核" onclick="$('page').value='1'; $('method').value='doSingleReview'; $('apNo').value='<c:out value="${listItem.apNo}" />'; $('dataIndex').value='<c:out value="${listItem_rowNum-1}" />'; document.SurvivorPaymentReviewForm.submit();" />&nbsp;
                                </acl:checkButton>                            
                            </display:column>
                        </display:table>
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
