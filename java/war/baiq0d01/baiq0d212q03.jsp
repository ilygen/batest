<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D212Q03" />
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
    
    function checkFields(){
        var msg="";
        if(msg!=""){
            return false;
        }else{
            return true;
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
            <html:form action="/paymentQuery" method="post" onsubmit="">
                <html:hidden styleId="page" property="page" value="1" />
                <html:hidden styleId="method" property="method" value="" />

                <fieldset>
                    <legend>
                        &nbsp;給付查詢&nbsp;
                    </legend>
                    <div align="right" id="showtime">
                        網頁下載時間：民國&nbsp;<func:nowDateTime />
                    </div>
                    <bean:define id="detail" name="<%=ConstantKey.PAYMENT_QUERY_DETAIL_DATA_CASE%>" />
                    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="0" class="px13">
                        <tr>
                            <td colspan="14">
                            <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                            <td align="left"  style="color: blue;font-size: 120%;font-weight: bold;">
                               <c:if test="${detail.secrecy eq 'Y'}">
                                    <c:out value="※保密案件"/>
                               </c:if>
                            </td> 
                            <td align="right">
                                <acl:checkButton buttonName="btnBack">
                                    <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackMaster'; document.PaymentQueryForm.submit();" />&nbsp;
                                </acl:checkButton>
                            </td>
                            </tr>
                            </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:define id="qryForm" name="<%=ConstantKey.PAYMENT_QUERY_COND_FORM%>" />
                               
                                <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                    <tr>
                                        <td width="33%">
                                            <span class="issuetitle_L_down">受理編號：</span>
                                            <c:out value="${detail.apNoStrDisplay}" />
                                        </td>                   
                                        <td width="33%">
                                            <span class="issuetitle_L_down">事故日期／申請日期：</span>
                                            <c:out value="${detail.evtJobDateStr}" />&nbsp;／&nbsp;<c:out value="${detail.appDateStr}" />
                                        </td>
                                        <td width="34%">
                                            <span class="issuetitle_L_down">給付別：</span>
                                            <c:out value="${detail.pagePayKindStr}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            <span class="issuetitle_L_down">事故者姓名：</span>
                                            <c:out value="${detail.evtName}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="issuetitle_L_down">事故者身分證號：</span>
                                            <c:out value="${detail.evtIdnNo}" />
                                        </td>               
                                        <td>
                                            <span class="issuetitle_L_down">事故者出生日期：</span>
                                            <c:out value="${detail.evtBrDateStr}" />&nbsp;
                                        </td>
                                        <td>
                                            <span class="issuetitle_L_down">事故者年齡／性別：</span>
                                            <c:out value="${detail.evtAge}" />&nbsp;歲／
                                            <c:out value="${detail.evtSexStr}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="issuetitle_L_down">電腦／人工審核結果：</span>
                                            <c:out value="${detail.acceptMkStr}" />
                                            &nbsp;／&nbsp;
                                            <c:out value="${detail.manchkMkStr}" />
                                        </td>
                                        <td>
                                            <span class="issuetitle_L_down">處理狀態：</span>
                                            <c:out value="${detail.procStatStr}" />
                                        </td>
                                        <td>
                                            <span class="issuetitle_L_down">給付種類：</span>
                                            <c:out value="${detail.payKindStr}" />
                                        </td>
                                    </tr>
                              </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <table border="0" cellpadding="0" cellspacing="0" class="issuetitle_L" id="tabsJ">
                                    <tr>
                                        <td valign="bottom" width="81%">
                                        </td>
                                        <td valign="bottom" width="19%" rowspan="2">
                                                    　承辦人分機號碼：<span class="word12"><c:out value="${detail.empExt}" />&nbsp;</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <a href='<c:url value="/paymentQuery.do?method=doSurvivorApplicationData" />' title="連結至：案件資料" target="_self" class="needtxt"><span>案件資料</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doSurvivorBenData" />' title="連結至：事故者/受款人" target="_self" class="needtxt"><span>事故者／受款人</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doSurvivorAvgAmtData" />' title="連結至：平均薪資" target="_self" class="needtxt"><span>平均薪資</span></a>
                                            <h5><a>紓困貸款</a></h5>
                                            <a href='<c:url value="/paymentQuery.do?method=doSurvivorCpiData" />' title="連結至：物價指數調整" target="_self" class="needtxt"><span>物價指數調整</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doSurvivorCpiRecData" />' title="連結至：物價指數調整記錄" target="_self" class="needtxt"><span>物價指數調整記錄</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doSurvivorArclistData" />' title="連結至：歸檔記錄" target="_self" class="needtxt"><span>歸檔記錄</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doSurvivorUpdateLogQueryData" />' title="連結至：更正日誌" target="_self" class="needtxt"><span>更正日誌</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doSurvivorUnqualifiedNoticeData" />' title="連結至：核定通知記錄" target="_self" class="needtxt"><span>核定通知記錄</span></a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" class="table1">
                                <bean:define id="loanMaster" name="<%=ConstantKey.PAYMENT_QUERY_LOAN_MASTER_DATA_CASE%>" />
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
                                        <td colspan="4" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;最新紓困貸款資料</td>
                                    </tr>
                                    <tr>
                                        <td id="iss" width="25%"><span class="issuetitle_L_down">勞貸貸款金額：</span><fmt:formatNumber value="${loanMaster.loanAmt}" /></td>
                                        <td id="iss" width="25%"><span class="issuetitle_L_down">本金餘額：</span><fmt:formatNumber value="${loanMaster.recapAmt}" /></td>
                                        <td id="iss" width="25%"><span class="issuetitle_L_down">利&nbsp;息：</span><fmt:formatNumber value="${loanMaster.loaniTrt}" /></td>
                                        <td id="iss" width="25%"><span class="issuetitle_L_down">本息截止日：</span><c:out value="${loanMaster.dlineDateStr}" /></td>
                                    </tr>
                                    <tr>
                                        <td id="iss"><span class="issuetitle_L_down">呆帳金額：</span><fmt:formatNumber value="${loanMaster.badDebtAmt}" /></td>
                                        <td id="iss"><span class="issuetitle_L_down">其他費用：</span><fmt:formatNumber value="${loanMaster.offsetExp}" /></td>
                                        <td id="iss" colspan="2"><span class="issuetitle_L_down">扣減金額：</span><fmt:formatNumber value="${loanMaster.offsetAmt}" /></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <c:if test="${((qryForm.startYm ne '')&&(qryForm.endYm ne ''))}">
                        <tr>
                            <td colspan="9" class="issuetitle_L"><br>
                                <span class="issuetitle_search">&#9658;</span>&nbsp;
                                <c:if test="${(qryForm.qryCond eq 'ISSUYM')}">
                                    <c:out value="核定年月起迄："/>                         
                                </c:if>                         
                                <c:if test="${(qryForm.qryCond eq 'PAYYM')}">
                                    <c:out value="給付年月起迄："/>                         
                                </c:if>   
                                <span class="px13">
                                    <c:out value="${qryForm.startYm}" />
                                    <c:if test="${(qryForm.startYm ne '')or(qryForm.endYm ne '')}">
                                    &nbsp;~&nbsp;
                                    </c:if>
                                    <c:out value="${qryForm.endYm}" />                                
                                </span>
                            </td>
                        </tr>
                        </c:if>
                        <tr>
                            <td width=100% align="center">
                                <bean:define id="list" name="<%=ConstantKey.PAYMENT_QUERY_LOAN_DETAIL_DATA_CASE_LIST%>" />
                                <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>"> 
                                    <c:if test="${(qryForm.qryCond eq 'ISSUYM')}">
                                        <display:column title="核定年月" headerClass="issuetitle_L" class="issueinfo_C" style="width:12%; text-align:center;">
                                            <c:out value="${listItem.issuYmStr}" />&nbsp;
                                        </display:column>                     
                                    </c:if>                         
                                    <c:if test="${(qryForm.qryCond eq 'PAYYM')}">
                                        <display:column title="給付年月" headerClass="issuetitle_L" class="issueinfo_C" style="width:12%; text-align:center;">
                                            <c:out value="${listItem.payYmStr}" />&nbsp;
                                        </display:column>                   
                                    </c:if> 
                                    <display:column title="勞貸貸款金額" headerClass="issuetitle_L" class="issueinfo_C" style="width:13%; text-align:center;">
                                        <fmt:formatNumber value="${listItem.loanAmt}" />&nbsp;
                                    </display:column>
                                    <display:column title="本金餘額" headerClass="issuetitle_L" class="issueinfo_C" style="width:13%; text-align:center;">
                                        <fmt:formatNumber value="${listItem.recapAmt}" />&nbsp;
                                    </display:column>
                                    <display:column title="利&nbsp;息" headerClass="issuetitle_L" class="issueinfo_C" style="width:12%; text-align:center;">
                                        <fmt:formatNumber value="${listItem.loaniTrt}" />&nbsp;
                                    </display:column>
                                    <display:column title="本息截止日" headerClass="issuetitle_L" class="issueinfo_C" style="width:13%; text-align:center;">
                                        <c:out value="${listItem.dlineDateStr}" />&nbsp;
                                    </display:column>
                                    <display:column title="呆帳金額" headerClass="issuetitle_L" class="issueinfo_C" style="width:13%; text-align:center;">
                                        <fmt:formatNumber value="${listItem.badDebtAmt}" />&nbsp;
                                    </display:column>
                                    <display:column title="其他費用" headerClass="issuetitle_L" class="issueinfo_C" style="width:12%; text-align:center;">
                                        <fmt:formatNumber value="${listItem.offsetExp}" />&nbsp;
                                    </display:column>
                                    <display:column title="扣減金額" headerClass="issuetitle_L" class="issueinfo_C" style="width:12%; text-align:center;">
                                        <fmt:formatNumber value="${listItem.offsetAmt}" />&nbsp;
                                    </display:column>
                                </display:table>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                </fieldset>
            </html:form>
        </div>
    </div>

    <%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
