<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="tw.gov.bli.ba.decision.cases.DisabledPaymentDecisionExtCase" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BARC0D113Q" />
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
            <html:form action="/disabledPaymentDecision" method="post" onsubmit="">
                <html:hidden styleId="page" property="page" value="1" />
                <html:hidden styleId="method" property="method" value="" />

                <fieldset>
                    <legend>
                        &nbsp;失能年金審核清單明細資料&nbsp;
                    </legend>

                    <div align="right" id="showtime">
                        網頁下載時間：民國&nbsp;<func:nowDateTime />
                    </div>

                    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                        <tr>
                            <td align="right" colspan="7">
                                <bean:define id="qryCond" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_QUERY_FORM %>" />
                                <acl:checkButton buttonName="btnBack">
                                    <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackList'; document.DisabledPaymentDecisionForm.submit();" />&nbsp;
                                </acl:checkButton>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="7">
                                <bean:define id="pay" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_CASE%>" />
                                <bean:define id="occAccData" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_OCCACC_DATA%>" />
                                <bean:define id="disabledData" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_DISABLED_DATA%>" />
                                <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                    <tr>
                                        <td width="34%">
                                            <span class="issuetitle_L_down">受理編號：</span>
                                            <c:out value="${pay.apNoStrDisplay}" />&nbsp;
                                        </td>
                                        <td width="33%">
                                            <span class="issuetitle_L_down">診斷失能日期／申請日期：</span>
                                            <c:out value="${pay.evtJobDateStr}" />
                                            &nbsp;／&nbsp;
                                            <c:out value="${pay.appDateStr}" />
                                        </td>
                                        <td width="33%">
                                            <span class="issuetitle_L_down">版次：</span>
                                            <c:out value="${pay.veriSeq}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            <span class="issuetitle_L_down">事故者姓名：</span>
                                            <c:out value="${pay.evtName}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="issuetitle_L_down">事故者身分證號：</span>
                                            <c:out value="${pay.evtIdnNo}" />&nbsp;
                                        </td>
                                        <td>
                                            <span class="issuetitle_L_down">事故者出生日期：</span>
                                            <c:out value="${pay.evtBrDateStr}" />&nbsp;
                                        </td>
                                        <td>
                                            <span class="issuetitle_L_down">事故者申請時年齡／性別：</span>
                                            <c:out value="${pay.evtAge}" />
                                            <c:if test="${(pay.evtAge ne null)or(pay.evtAge ne '')}">
                                                &nbsp;歲
                                            </c:if>／
                                            <c:if test='${(pay.evtSex eq "1")}'>
                                                <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_1%>" />
                                            </c:if>
                                            <c:if test='${(pay.evtSex eq "2")}'>
                                                <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_2%>" />
                                            </c:if>&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="issuetitle_L_down">首次給付年月：</span>
                                            <c:out value="${pay.payYmsStr}" />
                                            <c:if test='${(pay.payYmsStr ne "")or(pay.payYmeStr ne "")}'>
                                            &nbsp;~&nbsp;
                                            </c:if>
                                            <c:out value="${pay.payYmeStr}" />&nbsp;
                                        </td>
                                        <td>
                                            <span class="issuetitle_L_down">核定年月：</span>
                                            <c:out value="${pay.issuYmStr}" />&nbsp;
                                        </td>
                                        <td>
                                            <span class="issuetitle_L_down">給付年月起迄：</span>
                                            <c:out value="${pay.minPayYmStr}" />
                                            <c:if test='${(pay.minPayYmStr ne "")or(pay.maxPayYmStr ne "")}'>
                                            &nbsp;~&nbsp;
                                            </c:if>
                                            <c:out value="${pay.maxPayYmStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="issuetitle_L_down">電腦審核結果：</span>
                                            <c:if test="${pay.acceptMk eq 'Y'}">
                                                <c:out value="Y-合格" />
                                            </c:if>
                                            <c:if test="${pay.acceptMk eq 'N'}">
                                                <c:out value="N-不合格" />
                                            </c:if>
                                            <c:if test="${(pay.acceptMk ne 'Y')and(pay.acceptMk ne 'N')}">
                                                <c:out value="待處理" />
                                            </c:if>
                                        </td>
                                        <td colspan="2">
                                            <span class="issuetitle_L_down">人工電腦審核結果：</span>
                                            <c:if test="${pay.manchkMk eq 'Y'}">
                                                <c:out value="Y-合格" />
                                            </c:if>
                                            <c:if test="${pay.manchkMk eq 'N'}">
                                                <c:out value="N-不合格" />
                                            </c:if>
                                            <c:if test="${(pay.manchkMk ne 'Y')and(pay.manchkMk ne 'N')}">
                                                <c:out value="待處理" />
                                            </c:if>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="7" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                                    <tr>
                                        <td colspan="7" class="issuetitle_L"></td>
                                    </tr>
                                    <tr>
                                        <td colspan="7">
                                            <table width="100%" cellpadding="0" cellspacing="0" class="px13">
                                                <td width="85%" class="issuetitle_L">
                                                    <span class="systemMsg">▲</span>&nbsp;核定資料
                                                </td>
                                                <td width="15%" id="tabsJ" class="issuetitle_L" valign="bottom">
                                                    <a href="<c:url value="/disabledPaymentDecision.do?method=doReviewRpt&action=single" />" onclick="Event.stopObserving(window, 'beforeunload', beforeUnload);"><span>檢視受理/審核清單</span></a>
                                                </td>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" class="issuetitle_L">
                                            <span class="issuetitle_L_down">核定格式：</span>
                                            <c:out value="${pay.notifyForm}" />                                    
                                        </td>
                                        <td colspan="5" class="issuetitle_L">
                                                        更正原因：<c:out value="${pay.chgNote}" />
                                        </td>
                                    </tr>
                                    <tr align="center">
                                        <td colspan="7">
                                            <bean:define id="payList" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_PAY_DATA_LIST%>" />
                                            <display:table name="pageScope.payList" id="listItem" pagesize="0">
                                                <display:column title="給付年月" style="width:14%; text-align:center;">
                                                    <c:out value="${listItem.payYmStr}" />&nbsp;
                                                </display:column>
                                                <display:column title="核定／物價調整金額" style="width:16%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.issueAmt}" />&nbsp;
                                                </display:column>
                                                <display:column title="應收／沖抵金額" style="width:14%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.recAmt}" />
                                                    &nbsp;／&nbsp;
                                                    <fmt:formatNumber value="${listItem.payBanance}" />
                                                </display:column>
                                                <display:column title="補發金額" style="width:14%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.supAmt}" />&nbsp;
                                                </display:column>
                                                <display:column title="扣減總金額" style="width:14%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.otherAmt}" />&nbsp;
                                                </display:column>
                                                <display:column title="紓困金額" style="width:14%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.offsetAmt}" />&nbsp;
                                                </display:column>
                                                <display:column title="實付金額"  style="width:14%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.aplPayAmt}" />&nbsp;
                                                </display:column>
                                            </display:table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" class="table1" colspan="7">
                                <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                                    <tr>
                                        <td colspan="3" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;行政支援記錄</td>
                                    </tr>
                                    <bean:define id="letterType1List" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_1_LIST %>" />
                                    <bean:define id="letterType2List" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_2_LIST %>" />
                                    <bean:define id="letterType3List" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_3_LIST %>" />
                                    <bean:define id="letterType4List" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_4_LIST %>" />
                                    <bean:define id="letterType5List" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_5_LIST %>" />
                                    <bean:define id="letterType6List" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_6_LIST %>" />
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">交查日期：</span>
                                            <c:forEach var="detail1List" items="${letterType1List}" varStatus="detail1">
                                                <span title="<c:out value="${detail1List.ndomk1}" />">
                                                    <c:choose>
                                                        <c:when test='${detail1.last}'>
                                                            <c:out value="${detail1List.proDate}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${detail1List.proDate},"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>&nbsp;
                                             </c:forEach>
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">不給付日期：</span>
                                            <c:forEach var="detail2List" items="${letterType2List}" varStatus="detail2">
                                                <c:if test='${detail2List.ndomkName2 ne "-"}'>
                                                    <span title="<c:out value="${detail2List.ndomkName1}" />、<c:out value="${detail2List.ndomkName2}" />">
                                                </c:if>
                                                <c:if test='${detail2List.ndomkName2 eq "-"}'>
                                                    <span title="<c:out value="${detail2List.ndomkName1}" />">
                                                </c:if>
                                                <c:choose>
                                                    <c:when test='${detail2.last && detail2List.ndomk2 ne ""}'>
                                                        <c:out value="${detail2List.ndomk1}、${detail2List.ndomk2}-${detail2List.proDateStr}"/>
                                                    </c:when>
                                                    <c:when test='${detail2.last && detail2List.ndomk2 eq ""}'>
                                                        <c:out value="${detail2List.ndomk1}-${detail2List.proDateStr}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if test='${detail2List.ndomk2 ne ""}'>
                                                            <c:out value="${detail2List.ndomk1}、${detail2List.ndomk2}-${detail2List.proDateStr},"/>
                                                        </c:if>
                                                        <c:if test='${detail2List.ndomk2 eq ""}'>
                                                            <c:out value="${detail2List.ndomk1}-${detail2List.proDateStr},"/>
                                                        </c:if>
                                                    </c:otherwise>
                                                </c:choose>
                                                </span>&nbsp;
                                             </c:forEach>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">補件日期：</span>
                                            <c:forEach var="detail3List" items="${letterType3List}" varStatus="detail3">
                                                <span title="<c:out value="${detail3List.ndomk1}" />">
                                                    <c:choose>
                                                        <c:when test='${detail3.last}'>
                                                            <c:out value="${detail3List.proDate}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${detail3List.proDate},"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>&nbsp;
                                             </c:forEach>
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">照會日期：</span>
                                            <c:forEach var="detail4List" items="${letterType4List}" varStatus="detail4">
                                                <span title="<c:out value="${detail4List.ndomk1}" />">
                                                    <c:choose>
                                                        <c:when test='${detail4.last}'>
                                                            <c:out value="${detail4List.proDate}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${detail4List.proDate},"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>&nbsp;
                                            </c:forEach>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">調病歷日期：</span>
                                            &nbsp;
                                        </td>
                                        <td id="iss" colspan="1">
                                            <span class="issuetitle_L_down">其他簽函日期：</span>
                                            <c:forEach var="detail5List" items="${letterType5List}" varStatus="detail5">
                                                <span title="<c:out value="${detail5List.ndomk1}" />">
                                                    <c:choose>
                                                        <c:when test='${detail5.last}'>
                                                            <c:out value="${detail5List.proDate}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${detail5List.proDate},"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span>&nbsp;
                                             </c:forEach>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">行政救濟日期：</span>
                                            <c:out value="${letterType6List.proDateStr}"/>
                                        </td>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">救濟種類：</span>
                                            <c:out value="${letterType6List.reliefKind}"/>
                                        </td>
                                        <td id="iss" width="50%">
                                            <span class="issuetitle_L_down">行政救濟辦理情形：</span>
                                            <c:out value="${letterType6List.reliefStat}"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="3">
                                            <span class="issuetitle_L_down">救濟事由：</span>
                                            <c:out value="${letterType6List.reliefCause}"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <%--                        
                        <tr>
                            <td colspan="7" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                                    <tr>
                                        <td colspan="4" class="issuetitle_L">
                                            <span class="systemMsg">▲</span>&nbsp;總表資料
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">投保薪資：</span>
                                            <fmt:formatNumber value="${pay.lsInsmAmt}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">平均薪資：</span>
                                            <fmt:formatNumber value="${pay.insAvgAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">核定總額：</span>
                                            <fmt:formatNumber value="${pay.befIssueAmt}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">補發總額：</span>
                                            <fmt:formatNumber value="${pay.supAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">紓困總額：</span>
                                            <fmt:formatNumber value="${pay.offsetAmt}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">扣減總額：</span>
                                            <fmt:formatNumber value="${pay.otherAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
                                            <span class="issuetitle_L_down">實付總額：</span>
                                            <fmt:formatNumber value="${pay.aplPayAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">失能等級：</span>
                                            <c:out value="${disabledData.criInJclStr}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">核定等級：</span>
                                            <c:out value="${disabledData.criInIssul}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">失能項目：</span>
                                            <c:out value="${disabledData.criInJdpStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">申請單位保險證號：</span>
                                            <c:out value="${pay.apUbno}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">事故發生單位保險證號：</span>
                                            <c:out value="${pay.lsUbno}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
                                            <span class="issuetitle_L_down">國際疾病代碼：</span>
                                            <c:out value="${disabledData.criInJnmeStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss"  width="25%">
                                            <span class="issuetitle_L_down">傷病分類：</span>
                                            <c:out value="${disabledData.evTypStr}" />&nbsp;
                                        </td>
                                        <td id="iss"  width="25%">
                                            <span class="issuetitle_L_down">傷病原因：</span>
                                            <c:out value="${disabledData.evCode}" />&nbsp;
                                        </td>
                                        <td id="iss"  width="25%">
                                            <span class="issuetitle_L_down">受傷部位：</span>
                                            <c:out value="${disabledData.criInPartStr}" />&nbsp;
                                        </td>
                                        <td id="iss"  width="25%">
                                            <span class="issuetitle_L_down">媒介物：</span>
                                            <c:out value="${disabledData.criMedium}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">醫療院所代碼：</span>
                                            <c:out value="${disabledData.hosIdSname}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">醫師姓名：</span>
                                            <c:out value="${disabledData.doctorNameStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">勞保投保/實發年資：</span>
                                            <c:out value="${pay.nitrmY}" />年<c:out value="${pay.nitrmM}" />月
                                            (<c:out value="${pay.itrmY}" />年<c:out value="${pay.itrmD}" />日)
                                            &nbsp;/&nbsp;
                                            <c:out value="${pay.aplPaySeniY}" />年<c:out value="${pay.aplPaySeniM}" />月
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">勞保計算/給付金額：</span>
                                            <fmt:formatNumber value="${pay.oldaAmt}" />
                                            &nbsp;/&nbsp;
                                            <fmt:formatNumber value="${pay.oldbAmt}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">加發眷屬補助：</span>
                                            <fmt:formatNumber value="${pay.oldRate}" />%
                                            &nbsp;/&nbsp;
                                            <fmt:formatNumber value="${pay.oldRateAmt}" />
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">當月扣除失能：</span>
                                            <fmt:formatNumber value="${pay.compenAmt}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="1">
                                            <span class="issuetitle_L_down">扣除日數：</span>
                                            <fmt:formatNumber value="${disabledData.deductDay}" />
                                            <c:if test="${disabledData.deductDay ne null}">天</c:if>
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">職災補償一次金/6個月平均薪資：</span>
                                            <fmt:formatNumber value="${occAccData.befIssueAmt}" />
                                            &nbsp;/&nbsp;
                                            <fmt:formatNumber value="${occAccData.insAvgAmt}" />
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">已領職災增給金額：</span>
                                            <fmt:formatNumber value="${occAccData.ocAccaddAmt}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">實發職災一次金：</span>
                                            <fmt:formatNumber value="${occAccData.aplpayAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">應扣失能/已扣失能/未扣失能：</span>
                                            <fmt:formatNumber value="${pay.cutAmt}" />
                                            &nbsp;/&nbsp;
                                            <fmt:formatNumber value="${pay.lecomAmt}" />
                                            &nbsp;/&nbsp;
                                            <fmt:formatNumber value="${pay.recomAmt}" />
                                        </td>
                                        <td id="iss" colspan="1">
                                            <span class="issuetitle_L_down">符合第20條之1：</span>
                                            <fmt:formatNumber value="${disabledData.ocaccIdentMk}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="1">
                                            <span class="issuetitle_L_down">先核普通：</span>
                                            <fmt:formatNumber value="${disabledData.prType}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">死亡日期：</span>
                                            <c:out value="${pay.evtDieDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">失蹤日期：</span>
                                            <c:out value="${pay.evtMissingDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="3">
                                            <span class="issuetitle_L_down">受理鍵入資料及修改紀錄：</span>
                                            (鍵入/更正人員代號：<c:out value="${pay.crtUser}" />&nbsp;/&nbsp;<c:out value="${pay.updUser}" />)
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="7" align="center" class="table1">
                                <table width="98%" cellpadding="1" cellspacing="3" class="px13">
                                    <tr>
                                        <td colspan="8" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;編審資料</td>
                                    </tr>
                                    <tr align="center">
                                        <td width="14%" class="issuetitle_L">給付年月</td>
                                        <td width="14%" class="issuetitle_L">核定／物價調整金額</td>
                                        <td width="12%" class="issuetitle_L">應收／沖抵金額</td>
                                        <td width="12%" class="issuetitle_L">補發金額</td>
                                        <td width="12%" class="issuetitle_L">扣減總金額</td>
                                        <td width="12%" class="issuetitle_L">紓困金額</td>
                                        <td width="12%" class="issuetitle_L">實付金額</td>
                                        <td width="12%" class="issuetitle_L">電腦編審結果</td>
                                    </tr>
                                    <logic:iterate id="benData" indexId="index" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_BENEFICIARY_DATA_LIST%>" >                                                                                                            
                                        <c:if test="${index eq '0'}">
                                            <tr>
                                                <td colspan="8" id="iss">                                        
                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="px13">
                                                        <tr>
                                                            <td width="14%" rowspan="2" class="issuetitle_L_down">
                                                                           受款人序：<c:out value="${index+1}" />&nbsp;  
                                                            </td>                                               
                                                            <td class="issuetitle_L_down" width="86%">
                                                                           事故者姓名：<c:out value="${benData.benName}" />&nbsp;  
                                                            </td>
                                                        </tr>
                                                        <tr>    
                                                            <td rowspan="2" class="issuetitle_L_down">
                                                                           事故者身分證號：<c:out value="${benData.benIdnNo}" />&nbsp;  
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${(index ne '0')}">
                                            <tr>
                                                <td colspan="8" id="iss">                                        
                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="px13">
                                                       <tr>
                                                            <td width="14%" rowspan="2">
                                                                       受款人序：<c:out value="${index+1}" />&nbsp;  
                                                            </td>
                                                            <td colspan="2">
                                                                       受款人姓名：<c:out value="${benData.benName}" />&nbsp;  
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width="46%">受款人身分證號：<c:out value="${benData.benIdnNo}" />&nbsp;  </td>
                                                            <td width="40%">關　係：
                                                                <c:out value="${benData.benEvtRelStr} " />&nbsp; 
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </c:if>
                                            <tr>
                                                <td colspan="8">
                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2" class="px13">
                                                        <logic:notEmpty name="benData" property="benIssueDataList">
                                                            <logic:iterate id="benIssueData" indexId="benIndex" name="benData" property="benIssueDataList">                                                                        
                                                                <tr>
                                                                    <td width="14%" class="issueinfo_C">
                                                                        <c:out value="${benIssueData.payYmStr}" />&nbsp;  
                                                                    </td>                                                    
                                                                    <td width="14%" class="issueinfo_R">
                                                                        <fmt:formatNumber value="${benIssueData.issueAmt}" />&nbsp;
                                                                    </td>
                                                                    <td width="12%" class="issueinfo_R">
                                                                        <fmt:formatNumber value="${benIssueData.recAmt}" />
                                                                        &nbsp;／&nbsp;
                                                                        <fmt:formatNumber value="${benIssueData.payBanance}" />
                                                                    </td>
                                                                    <td width="12%" class="issueinfo_R">
                                                                        <fmt:formatNumber value="${benIssueData.supAmt}" />&nbsp;  
                                                                    </td>
                                                                    <td width="12%" class="issueinfo_R">
                                                                        <fmt:formatNumber value="${benIssueData.otherAmt}" />&nbsp;  
                                                                    </td>
                                                                    <td width="12%" class="issueinfo_R">
                                                                        <fmt:formatNumber value="${benIssueData.offsetAmt}" />&nbsp;  
                                                                    </td>
                                                                    <td width="12%" class="issueinfo_R">
                                                                        <fmt:formatNumber value="${benIssueData.aplPayAmt}" />&nbsp;  
                                                                    </td>
                                                                    <td width="12%" class="issueinfo">
                                                                        <c:if test="${(benIssueData.benEvtRel eq '1')or(benIssueData.benEvtRel eq '2')or(benIssueData.benEvtRel eq '3')or(benIssueData.benEvtRel eq '4')or(benIssueData.benEvtRel eq '5')or(benIssueData.benEvtRel eq '6')or(benIssueData.benEvtRel eq '7')}">
                                                                            <c:if test="${benIssueData.acceptMk eq 'Y'}">
                                                                                <c:out value="Y-合格" />
                                                                            </c:if>
                                                                            <c:if test="${benIssueData.acceptMk eq 'N'}">
                                                                                <c:out value="N-不合格" />
                                                                            </c:if>
                                                                            <c:if test="${(benIssueData.acceptMk ne 'Y')and(benIssueData.acceptMk ne 'N')}">
                                                                                <c:out value="待處理" />
                                                                            </c:if>&nbsp;
                                                                        </c:if>
                                                                        <c:if test="${(benIssueData.benEvtRel ne '1')and(benIssueData.benEvtRel ne '2')and(benIssueData.benEvtRel ne '3')and(benIssueData.benEvtRel ne '4')and(benIssueData.benEvtRel ne '5')and(benIssueData.benEvtRel ne '6')and(benIssueData.benEvtRel ne '7')}">
                                                                            &nbsp;
                                                                        </c:if>  
                                                                    </td>
                                                                </tr>      
                                                            </logic:iterate>
                                                        </logic:notEmpty>
                                                        <logic:empty name="benData" property="benIssueDataList">
                                                            <tr>
                                                                <td colspan="8" class="issueinfo_C" align="center">
                                                                    <font color="red">無資料</font>
                                                                </td>
                                                            </tr>
                                                        </logic:empty>                                                                                                                         
                                                    </table>
                                                </td>
                                            </tr>
                                    </logic:iterate>
                                    <tr>
                                        <td colspan="8">
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                                                <tr>
                                                    <td id="iss" valign="top" width="14%">
                                                        <span class="issuetitle_L_down">事故者編審註記：</span>
                                                    </td>
                                                    <td id="iss" valign="top" width="62%">
                                                        <logic:iterate id="chkFileData" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_EVT_CHK_LIST %>">                                                    
                                                            <c:out value="${chkFileData.issuPayYm}" />：
                                                            <logic:iterate id="chkFile" indexId="i" name="chkFileData" property="chkFileList">
                                                                <span title="<c:out value="${chkFile.chkResult}" />">
                                                                    <c:out value="${chkFile.chkCodePost}"/>
                                                                </span>
                                                                <c:if test="${i+1 ne chkFileData.chkFileDataSize}">
                                                                    <c:out value=",　" />
                                                                </c:if>
                                                            </logic:iterate><br>
                                                        </logic:iterate>
                                                        <logic:empty name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_EVT_CHK_LIST %>">
                                                            &nbsp;
                                                        </logic:empty>
                                                    </td>
                                                    <td id="iss" valign="top" width="24%">
                                                        <c:if test="${detail.loanMk eq '1'}">
                                                            <strong><c:out value="<%=ConstantKey.BAAPPBASE_LOANMK_STR_1 %>" /></strong>
                                                        </c:if>&nbsp;
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="1" valign="top" width="14%">
                                            <span class="issuetitle_L_down">眷屬符合註記：</span>
                                        </td>
                                        <td id="iss" colspan="7" valign="top" width="86%">
                                            <logic:iterate id="masterChkFile" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_MATCH_CHK_LIST %>">                                                    
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="px13">
                                                    <tr>
                                                        <td valign="top" width="10%" align="left">
                                                            <strong>眷屬序：<c:out value="${masterChkFile.seqNo}" /></strong>
                                                        </td>
                                                        <td valign="top" width="90%" align="left">
                                                            <logic:iterate id="chkFileData" name="masterChkFile" property="otherChkDataList">                                                    
                                                                <c:out value="${chkFileData.issuPayYm}" />：
                                                                <logic:iterate id="chkFile" indexId="i" name="chkFileData" property="chkFileList">
                                                                    <span title="<c:out value="${chkFile.chkResult}" />">
                                                                        <c:out value="${chkFile.chkCodePost}"/>
                                                                    </span>
                                                                    <c:if test="${i+1 ne chkFileData.chkFileDataSize}">
                                                                        <c:out value=",　" />
                                                                    </c:if>
                                                                </logic:iterate><br>
                                                            </logic:iterate>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </logic:iterate>
                                            <logic:empty name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_MATCH_CHK_LIST %>">
                                                &nbsp;
                                            </logic:empty>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="1" valign="top" width="14%">
                                            <span class="issuetitle_L_down">眷屬編審註記：</span>
                                        </td>
                                        <td id="iss" colspan="7" valign="top" width="86%">
                                            <logic:iterate id="masterChkFile" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_BEN_CHK_LIST %>">                                                    
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="px13">
                                                    <tr>
                                                        <td valign="top" width="10%" align="left">
                                                            <strong>眷屬序：<c:out value="${masterChkFile.seqNo}" /></strong>
                                                        </td>
                                                        <td valign="top" width="90%" align="left">
                                                            <logic:iterate id="chkFileData" name="masterChkFile" property="otherChkDataList">                                                    
                                                                <c:out value="${chkFileData.issuPayYm}" />：
                                                                <logic:iterate id="chkFile" indexId="i" name="chkFileData" property="chkFileList">
                                                                    <span title="<c:out value="${chkFile.chkResult}" />">
                                                                        <c:out value="${chkFile.chkCodePost}"/>
                                                                    </span>
                                                                    <c:if test="${i+1 ne chkFileData.chkFileDataSize}">
                                                                        <c:out value=",　" />
                                                                    </c:if>
                                                                </logic:iterate><br>
                                                            </logic:iterate>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </logic:iterate>
                                            <logic:empty name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_BEN_CHK_LIST %>">
                                                &nbsp;
                                            </logic:empty>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="8">&nbsp;</td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        --%>
                        <tr>
                            <td colspan="7">&nbsp;</td>
                        </tr>
                    </table>
                </fieldset>
            </html:form>
        </div>
    </div>

    <%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
