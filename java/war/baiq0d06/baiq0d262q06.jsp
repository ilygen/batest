<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D262Q06" />
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
                    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="0" class="px13">
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
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorAvgAmtData" />' title="連結至：平均薪資 " target="_self" class="needtxt"><span>平均薪資</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorLoanData" />' title="連結至：紓困貸款" target="_self" class="needtxt"><span>紓困貸款</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorCpiData" />' title="連結至：物價指數調整" target="_self" class="needtxt"><span>物價指數調整</span></a>
                                            <h5><a>物價指數調整記錄</a></h5>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorArclistData" />' title="連結至：歸檔記錄" target="_self" class="needtxt"><span>歸檔記錄</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorSimplifyUpdateLogQueryData" />' title="連結至：更正日誌" target="_self" class="needtxt"><span>更正日誌</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doSurvivorUnqualifiedNoticeData" />' title="連結至：核定通知記錄" target="_self" class="needtxt"><span>核定通知記錄</span></a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
                                        <td colspan="8" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;物價指數調整記錄資料</td>
                                    </tr>
                                    <tr>
                                        <td colspan="8" align="center">
                                            <bean:define id="list" name="<%=ConstantKey.PAYMENT_QUERY_CPIREC_DATA_CASE_LIST%>" />
                                            <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>"> 
                                                <display:column title="序 號" headerClass="issuetitle_L" class="issueinfo_C" style="width:10%; text-align:center;">
                                                    <c:out value="${listItem_rowNum}" />&nbsp;
                                                </display:column>         
                                                <display:column title="核定年度" headerClass="issuetitle_L" class="issueinfo_C" style="width:15%; text-align:center;">
                                                    <c:out value="${listItem.issuYearStr}" />&nbsp;
                                                </display:column>           
                                                <display:column title="申請年度" headerClass="issuetitle_L" class="issueinfo_C" style="width:15%; text-align:center;">
                                                    <c:out value="${listItem.adjYearStr}" />&nbsp;
                                                </display:column>                   
                                                <display:column title="調整指數" headerClass="issuetitle_L" class="issueinfo_C" style="width:15%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.adjCpi}" />
                                                    <c:if test="${listItem.adjCpi ne null}">%</c:if>&nbsp;
                                                </display:column>
                                                <display:column title="調整起月" headerClass="issuetitle_L" class="issueinfo_C" style="width:15%; text-align:center;">
                                                    <c:out value="${listItem.adjYmBStr}" />&nbsp;
                                                </display:column>
                                                <display:column title="指數年度(起)" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:center;">
                                                    <c:out value="${listItem.cpiYearBStr}" />&nbsp;
                                                </display:column>
                                                <display:column title="指數年度(迄)" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:center;">
                                                    <c:out value="${listItem.cpiYearEStr}" />&nbsp;
                                                </display:column>
                                            </display:table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
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
