<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D162Q03" />
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
            <html:form action="/paymentQuerySimplify" method="post" onsubmit="">
                <html:hidden styleId="page" property="page" value="1" />
                <html:hidden styleId="method" property="method" value="" />
                <html:hidden styleId="reApNo" property="reApNo" value="" />
                <html:hidden styleId="apSeq" property="apSeq" value="" />
                <html:hidden styleId="apNo" property="apNo" value="" />

                <fieldset>
                    <legend>
                        &nbsp;給付查詢(外單位專用)&nbsp;
                    </legend>
                    <div align="right" id="showtime">
                        網頁下載時間：民國&nbsp;<func:nowDateTime />
                    </div>
                    <bean:define id="detail" name="<%=ConstantKey.PAYMENT_QUERY_DETAIL_DATA_CASE%>" />
                    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                        <tr>
                            <td colspan="14">
                            <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                            <td align="left" style="color: blue;font-size: 120%;font-weight: bold;">
                               <c:if test="${detail.secrecy eq 'Y'}">
                                    <c:out value="※保密案件"/>
                               </c:if>
                            </td> 
                            <td align="right" >
                                <acl:checkButton buttonName="btnBack">
                                    <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackMaster'; document.PaymentQueryForm.submit();" />&nbsp;
                                </acl:checkButton>
                            </td>
                            </tr>
                            </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14">
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
                                        <td colspan="1">
                                            <span class="issuetitle_L_down">事故者姓名：</span>
                                            <c:out value="${detail.evtName}" />
                                        </td>
                                        <td colspan="2">
                                            <c:choose>
                                                <c:when test="${detail.containCheckMark3M}">
                                                    <span class="issuetitle_L_down">併計國保年資：</span>
                                                    <c:out value="${detail.comnpMk}" />
                                                </c:when>
                                                <c:otherwise>
                                                    &nbsp;
                                                </c:otherwise>
                                            </c:choose>
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
                            <td colspan="14">
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
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledApplicationData" />' title="連結至：案件資料" target="_self" class="needtxt"><span>案件資料</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledBenData" />' title="連結至：事故者/受款人資料" target="_self" class="needtxt"><span>事故者／受款人資料</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledFamilyData" />' title="連結至：眷屬資料" target="_self" class="needtxt"><span>眷屬資料</span></a>
                                            <h5><a>複檢費用資料</a></h5>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledAvgAmtData" />' title="連結至：平均薪資" target="_self" class="needtxt"><span>平均薪資</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledLoanData" />' title="連結至：紓困貸款" target="_self" class="needtxt"><span>紓困貸款</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledNpData" />' title="連結至：國保資料" target="_self" class="needtxt"><span>國保資料</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledCpiData" />' title="連結至：物價指數調整" target="_self" class="needtxt"><span>物價指數調整</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledCpiRecData" />' title="連結至：物價指數調整記錄" target="_self" class="needtxt"><span>物價指數調整記錄</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledArclistData" />' title="連結至：歸檔記錄" target="_self" class="needtxt"><span>歸檔記錄</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledSimplifyUpdateLogQueryData" />' title="連結至：更正日誌" target="_self" class="needtxt"><span>更正日誌</span></a>
                                            <c:if test="${(detail.payKind eq '35') or (detail.payKind eq '38')}">  
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledRehcData" />' title="連結至：重新查核失能程度" target="_self" class="needtxt"><span>重新查核失能程度</span></a>
                                            </c:if>   
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                <bean:define id="list" name="<%=ConstantKey.PAYMENT_QUERY_REFEES_MASTER_DATA_LIST%>" />
                                    <tr>
                                        <td colspan="8" class="issuetitle_L">
                                            <span class="systemMsg">▲</span>&nbsp;複檢費用資料
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="8" align="center">
                                            <span class="issuetitle_L_down">複檢費用受理編號：</span>
                                            <c:forEach var="master" items="${list}" varStatus="masterList">
                                                <c:choose>
                                                    <c:when test='${masterList.first}'>
                                                        <c:out value="${master.reApNoStrDisplay}"/>
                                                    </c:when>
                                                </c:choose>
                                                &nbsp;
                                             </c:forEach>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="8" align="center">
                                            <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                                                <display:column title="序號" headerClass="issuetitle_L" style="width:7%; text-align:center;">
                                                    <c:out value="${listItem_rowNum}" />
                                                </display:column>
                                                <display:column title="通知複檢日期" headerClass="issuetitle_L" style="width:11%; text-align:center;">
                                                    <c:out value="${listItem.inreDateStr}" />&nbsp;
                                                </display:column>
                                                <display:column title="複檢費用申請日期" headerClass="issuetitle_L" style="width:15%; text-align:center;">
                                                    <c:out value="${listItem.recosDateStr}" />&nbsp;
                                                </display:column>
                                                <display:column title="複檢費用" headerClass="issuetitle_L" style="width:15%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.reFees}" />&nbsp;
                                                </display:column>
                                                <display:column title="非複檢必須費用" headerClass="issuetitle_L" style="width:15%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.nonreFees}" />&nbsp;
                                                </display:column>
                                                <display:column title="複檢實付金額" headerClass="issuetitle_L" style="width:15%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.reAmtPay}" />&nbsp;
                                                </display:column>
                                                <display:column title="核付日期" headerClass="issuetitle_L" style="width:11%; text-align:center;">
                                                    <c:out value="${listItem.payDateStr}" />&nbsp;
                                                </display:column>
                                                <display:column title="資料明細" headerClass="issuetitle_L" style="width:11%; text-align:center;">
                                                    <input type="button" name="btnDetail" class="button" value="明細查詢" onclick="$('page').value='1'; $('method').value='doDisabledReFeesDetailData'; $('reApNo').value='<c:out value="${listItem.reApNo}" />'; $('apSeq').value='<c:out value="${listItem.apSeq}" />'; $('apNo').value='<c:out value="${listItem.apNo}" />'; document.PaymentQueryForm.submit();">&nbsp;
                                                </display:column>
                                            </display:table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="8"></td>
                                    </tr>
                                </table>
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
