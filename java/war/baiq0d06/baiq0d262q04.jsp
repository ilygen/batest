<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D262Q04" />
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
                            <td align="left"  style="color: blue;font-size: 120%;font-weight: bold;">
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
                            <td colspan="10">
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
                            <td colspan="10">
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
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorApplicationData" />' title="連結至：案件資料" target="_self" class="needtxt"><span>案件資料</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorBenData" />' title="連結至：事故者/受款人" target="_self" class="needtxt"><span>事故者／受款人</span></a>
                                            <h5><a>平均薪資</a></h5>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorLoanData" />' title="連結至：紓困貸款" target="_self" class="needtxt"><span>紓困貸款</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorCpiData" />' title="連結至：物價指數調整" target="_self" class="needtxt"><span>物價指數調整</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorCpiRecData" />' title="連結至：物價指數調整記錄" target="_self" class="needtxt"><span>物價指數調整記錄</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorArclistData" />' title="連結至：歸檔記錄" target="_self" class="needtxt"><span>歸檔記錄</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorSimplifyUpdateLogQueryData" />' title="連結至：更正日誌" target="_self" class="needtxt"><span>更正日誌</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorUnqualifiedNoticeData" />' title="連結至：核定通知記錄" target="_self" class="needtxt"><span>核定通知記錄</span></a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="10" align="center" class="table1">
                                <bean:define id="seniData" name="<%=ConstantKey.PAYMENT_QUERY_AVGAMT_SENI_DATA_CASE%>" />
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
                                        <td colspan="4" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;年資資料</td>
                                    </tr>
                                    <tr>
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">投保年資：</span>
                                            <c:out value="${seniData.nitrmY}" />年<c:out value="${seniData.nitrmM}" />月&nbsp;
                                            (<c:out value="${seniData.itrmY}" />年<c:out value="${seniData.itrmD}" />日)
                                        </td>
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">實付年資：</span>
                                            <c:out value="${seniData.aplPaySeniY}" />年<c:out value="${seniData.aplPaySeniM}" />月
                                        </td>
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">老年年資：</span>
                                            <c:out value="${seniData.noldtY}" />年<c:out value="${seniData.noldtM}" />月
                                        </td>
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">國保年資：</span>
                                            <c:out value="${seniData.valseniY}" />年<c:out value="${seniData.valseniM}" />月
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
                                            <span class="issuetitle_L_down">平均月投保薪資(<c:out value="${seniData.appMonth}" />個月)：</span>
                                            <fmt:formatNumber value="${seniData.insAvgAmt}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="4"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="10" class="issuetitle_L">
                                <br><span class="issuetitle_search">&#9658;</span>&nbsp;最高<c:out value="${seniData.appMonth}" />個月平均薪資明細
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;實際均薪月數：<c:out value="${seniData.realAvgMon}" />個月
                            </td>
                        </tr>
                        <tr align="center">
                            <td width="10%" class="issuetitle_L">年&nbsp;月</td>
                            <td width="10%" class="issuetitle_L">薪&nbsp;資</td>
                            <td width="10%" class="issuetitle_L">年&nbsp;月</td>
                            <td width="10%" class="issuetitle_L">薪&nbsp;資</td>
                            <td width="10%" class="issuetitle_L">年&nbsp;月</td>
                            <td width="10%" class="issuetitle_L">薪&nbsp;資</td>
                            <td width="10%" class="issuetitle_L">年&nbsp;月</td>
                            <td width="10%" class="issuetitle_L">薪&nbsp;資</td>
                            <td width="10%" class="issuetitle_L">年&nbsp;月</td>
                            <td width="10%" class="issuetitle_L">薪&nbsp;資</td>
                        </tr>
                        <logic:empty name="<%=ConstantKey.PAYMENT_QUERY_SIXTY_MON_AVGAMT_DATA_CASE_LIST%>">
                            <tr>
                                <td colspan="10" width=100% align="center">
                                    <font color="red">無資料</font>
                                </td>
                            </tr>
                        </logic:empty>
                        <logic:notEmpty name="<%=ConstantKey.PAYMENT_QUERY_SIXTY_MON_AVGAMT_DATA_CASE_LIST%>">
                            <logic:iterate id="avgAmtDetail" indexId="dataIndex" name="<%=ConstantKey.PAYMENT_QUERY_SIXTY_MON_AVGAMT_DATA_CASE_LIST%>">
                                <c:if test="${(dataIndex+1)%5 eq 1}">
                                    <tr>
                                </c:if>
                                <td width="10%" class="issueinfo_C"><c:out value="${avgAmtDetail.avgYmStr}" /></td>
                                <td width="10%" class="issueinfo_R"><fmt:formatNumber value="${avgAmtDetail.avgWg}" /></td>
                                <%if(dataIndex+1 == ((List)request.getSession().getAttribute(ConstantKey.PAYMENT_QUERY_SIXTY_MON_AVGAMT_DATA_CASE_LIST)).size()){ %>
                                    <c:if test="${(dataIndex+1)%5 eq 1}">
                                        <td width="10%" class="issueinfo_C">&nbsp;</td>
                                        <td width="10%" class="issueinfo_R">&nbsp;</td>
                                        <td width="10%" class="issueinfo_C">&nbsp;</td>
                                        <td width="10%" class="issueinfo_R">&nbsp;</td>
                                        <td width="10%" class="issueinfo_C">&nbsp;</td>
                                        <td width="10%" class="issueinfo_R">&nbsp;</td>
                                        <td width="10%" class="issueinfo_C">&nbsp;</td>
                                        <td width="10%" class="issueinfo_R">&nbsp;</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${(dataIndex+1)%5 eq 2}">
                                        <td width="10%" class="issueinfo_C">&nbsp;</td>
                                        <td width="10%" class="issueinfo_R">&nbsp;</td>
                                        <td width="10%" class="issueinfo_C">&nbsp;</td>
                                        <td width="10%" class="issueinfo_R">&nbsp;</td>
                                        <td width="10%" class="issueinfo_C">&nbsp;</td>
                                        <td width="10%" class="issueinfo_R">&nbsp;</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${(dataIndex+1)%5 eq 3}">
                                        <td width="10%" class="issueinfo_C">&nbsp;</td>
                                        <td width="10%" class="issueinfo_R">&nbsp;</td>
                                        <td width="10%" class="issueinfo_C">&nbsp;</td>
                                        <td width="10%" class="issueinfo_R">&nbsp;</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${(dataIndex+1)%5 eq 4}">
                                        <td width="10%" class="issueinfo_C">&nbsp;</td>
                                        <td width="10%" class="issueinfo_R">&nbsp;</td>
                                    </c:if>
                                    </tr>
                                <%} %>
                            </logic:iterate>
                        </logic:notEmpty>
                        <tr>
                            <td colspan="10">&nbsp;</td>
                        </tr>
                    </table>
                </fieldset>
            </html:form>
        </div>
    </div>

    <%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
