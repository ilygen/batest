<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D212Q01" />
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
    
    Event.stopObserving(window, 'beforeunload', beforeUnload);
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
                            <td align="right">
                                <acl:checkButton buttonName="btnPrint">
                                    <input name="btnPrint" type="button" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReportSurvivorApplicationData'; document.PaymentQueryForm.submit();"/>&nbsp;
                                </acl:checkButton>
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
                                            <h5><a>事故者／受款人</a></h5>
                                            <a href='<c:url value="/paymentQuery.do?method=doSurvivorAvgAmtData" />' title="連結至：平均薪資 " target="_self" class="needtxt"><span>平均薪資</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doSurvivorLoanData" />' title="連結至：紓困貸款" target="_self" class="needtxt"><span>紓困貸款</span></a>
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
                            <td colspan="10" align="center" class="table1">
                                <table width="100%" cellpadding="3" cellspacing="3" class="px13">                                        
                                    <tr>
                                        <td colspan="4" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;事故者資料</td>
                                    </tr> 
                                    <tr>
                                        <td colspan="4" id="iss">
                                            <span class="issuetitle_L_down">事故者姓名：</span>
                                            <c:out value="${detail.evtName}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">事故者身分證號：</span>
                                            <c:out value="${detail.evtIdnNo}" />&nbsp;
                                        </td>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">事故者出生日期：</span>
                                            <c:out value="${detail.evtBrDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">性別：</span>
                                            <c:if test="${detail.evtSex eq '1'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_1 %>" />                                    
                                            </c:if>
                                            <c:if test="${detail.evtSex eq '2'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_2 %>" />                                    
                                            </c:if>&nbsp;
                                        </td>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">關係：</span>
                                            <c:out value="${detail.benEvtRelStr}" />&nbsp;
                                        </td>
                                    </tr>    
                                    <tr>
                                        <td id="iss" colspan="1" width="25%">
                                            <span class="issuetitle_L_down">國籍別：</span>
                                            <c:if test="${detail.evtNationTpe eq '1'}">
                                                <c:out value="本國" />
                                            </c:if>
                                            <c:if test="${detail.evtNationTpe eq '2'}">
                                                <c:out value="外籍" />
                                            </c:if>&nbsp;
                                        </td>
                                        <td id="iss" colspan="3" width="25%">
                                            <span class="issuetitle_L_down">國籍：</span>
                                            <c:out value="${detail.evtNationCode}" />&nbsp;
                                            <c:out value="${detail.evtNationName}" />
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <logic:empty name="<%=ConstantKey.PAYMENT_QUERY_BEN_DATA_CASE_LIST%>">
                            <tr>
                                <td align="center" class="table1">
                                    <table width="98%" cellpadding="3" cellspacing="3" class="px13">                                       
                                        <tr>
                                            <td colspan="5" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;受款人資料</td>
                                        </tr>
                                        <tr>
                                            <td id="iss" align="center" colspan="5">
                                                <font color="red">無資料</font>
                                            </td>
                                        </tr>
                                    </table>                                
                                </td>
                            </tr>                                         
                        </logic:empty>
                        <logic:iterate id="benDetailData" indexId="benDataIndex" name="<%=ConstantKey.PAYMENT_QUERY_BEN_DATA_CASE_LIST%>" >
                            <c:if test="${benDetailData.seqNo ne '0000'}" >
                            <tr>
                                <td colspan="10" align="center" class="table1">
                                    <table width="100%" cellpadding="3" cellspacing="3" class="px13">      
                                        <tr>
                                            <td colspan="4" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;受款人資料</td>
                                        </tr> 
                                        <tr>
                                            <td id="iss" colspan="1">                                
                                                <span class="issuetitle_L_down">受款人序：</span>
                                                <c:out value="${benDataIndex+1}" />
                                            </td>
                                            <td id="iss" colspan="3">
                                                <span class="issuetitle_L_down">受款人姓名：</span>
                                                <c:out value="${benDetailData.benName}" />&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="25%" id="iss">
                                                <span class="issuetitle_L_down">受款人身分證號：</span>
                                                <c:out value="${benDetailData.benIdnNo}" />&nbsp;
                                            </td>
                                            <c:if test="${(benDetailData.benEvtRel ne 'Z')and(benDetailData.benEvtRel ne 'F')and(benDetailData.benEvtRel ne 'N') }"> 
                                                <td id="iss" width="25%">
                                                    <span class="issuetitle_L_down">受款人出生日期：</span>
                                                    <c:out value="${benDetailData.benBrDateStr}" />&nbsp;
                                                </td>
                                            </c:if>
                                            <c:if test="${(benDetailData.benEvtRel ne 'Z')and(benDetailData.benEvtRel ne 'F')and(benDetailData.benEvtRel ne 'N') }">                                     
                                                <td id="iss" colspan="1" width="25%">
                                                    <span class="issuetitle_L_down">性別：</span>
                                                    <c:if test="${benDetailData.benSex eq '1'}">
                                                        <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_1 %>" />                                    
                                                    </c:if>
                                                    <c:if test="${benDetailData.benSex eq '2'}">
                                                        <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_2 %>" />                                    
                                                    </c:if>&nbsp;
                                                </td>
                                            </c:if>
                                            <c:choose>
                                                <c:when test="${(benDetailData.benEvtRel eq 'F')or(benDetailData.benEvtRel eq 'N')}">
                                                    <td id="iss" colspan="3" width="75%">
                                                </c:when>
                                                <c:otherwise>
                                                    <td id="iss" colspan="1" width="25%">
                                                </c:otherwise>
                                            </c:choose>
                                                <span class="issuetitle_L_down">關係：</span>
                                                <c:out value="${benDetailData.benEvtRelStr}" />&nbsp;
                                            </td>
                                            <c:if test="${(benDetailData.benEvtRel ne 'Z')and(benDetailData.benEvtRel ne 'F')and(benDetailData.benEvtRel ne 'N') }">
                                                </tr>
                                            </c:if>
                                            <c:if test="${benDetailData.benEvtRel eq 'Z'}"> 
                                                <td id="iss" colspan='2'>
                                                    <span class="issuetitle_L_down">實際補償金額：</span>
                                                    <fmt:formatNumber value="${benDetailData.cutAmt}" />&nbsp;
                                                </td>
                                            </c:if>
                                        </tr>
                                        <tr>
                                            <td id="iss" width="25%">
                                                <span class="issuetitle_L_down">申請日期：</span>
                                                <c:out value="${benDetailData.appDateStr}" />&nbsp;
                                            </td>
                                            <td id="iss" width="25%">
                                                <span class="issuetitle_L_down">國籍別：</span>
                                                <c:if test="${benDetailData.benNationTyp eq '1'}">
                                                    <c:out value="本國" />
                                                </c:if>
                                                <c:if test="${benDetailData.benNationTyp eq '2'}">
                                                    <c:out value="外籍" />
                                                </c:if>&nbsp;
                                            </td>
                                            <td id="iss" width="25%">
                                                <span class="issuetitle_L_down">國籍：</span>
                                                <c:out value="${benDetailData.benNationCode}" />&nbsp;
                                                <c:out value="${benDetailData.benNationName}" />
                                            </td>
                                            <td id="iss" width="25%">
                                                <span class="issuetitle_L_down">身分查核年月：</span>
                                                <c:out value="${benDetailData.idnChkYmStr}" />&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">得請領起月：</span>
                                                <c:out value="${benDetailData.ableApsYmStr}" />
                                            </td>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">放棄請領起始年月：</span>
                                                <c:out value="${benDetailData.abanApsYmStr}" />&nbsp;
                                            </td>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">死亡日期：</span>
                                                <c:out value="${benDetailData.benDieDateStr}" />&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">結婚日期：</span>
                                                <c:out value="${benDetailData.marryDateStr}" />
                                            </td>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">再婚日期：</span>
                                                <c:out value="${benDetailData.digamyDateStr}" />
                                            </td>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">強迫不合格年月：</span>
                                                <c:if test="${(benDetailData.compelSdate ne '')or(benDetailData.compelEdate ne '')}">
                                                    <a href="<c:url value="/paymentQuery.do?method=doSurvivorCompelNopayDetailData&apNo=${benDetailData.apNo}&seqNo=${benDetailData.seqNo}" />" title="連結至：強迫不合格起迄年月" target="_self">
                                                        <c:out value="${benDetailData.compelSdateStr}" />
                                                        &nbsp;~&nbsp;
                                                        <c:out value="${benDetailData.compelEdateStr}" />
                                                        &nbsp;
                                                        (<fmt:formatNumber value="${benDetailData.dataCount}" />)
                                                    </a>
                                                </c:if>
                                            </td>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">不合格原因：</span>
                                                <c:out value="${benDetailData.unqualifiedCause}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">被保險人扶養：</span>
                                                <c:out value="${benDetailData.raiseEvtMkStr}" />&nbsp;
                                            </td>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">配偶扶養：</span>
                                                <c:out value="${benDetailData.raiseChildMkStr}" />&nbsp;
                                            </td>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">收養日期：</span>
                                                <c:out value="${benDetailData.adoPtDateStr}" />&nbsp;
                                            </td>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">親屬關係變動日期：</span>
                                                <c:out value="${benDetailData.relatChgDateStr}" />&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">在學起迄年月：</span>
                                                <c:if test="${(benDetailData.studSdate ne '')or(benDetailData.studEdate ne '')}">
                                                    <a href="<c:url value="/paymentQuery.do?method=doSurvivorStudDetailData&apNo=${benDetailData.apNo}&seqNo=${benDetailData.seqNo}" />" title="連結至：在學起迄年月" target="_self">
                                                        <c:out value="${benDetailData.studSdateStr}" />
                                                        &nbsp;~&nbsp;
                                                        <c:out value="${benDetailData.studEdateStr}" />
                                                        &nbsp;
                                                        (<fmt:formatNumber value="${benDetailData.studDataCount}" />)
                                                    </a>
                                                </c:if>
                                            </td>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">每月工作收入註記／收入：</span>
                                                <c:out value="${benDetailData.monIncomeMkStr}" />
                                                <c:if test="${benDetailData.monIncomeMk eq 'Y'}">
                                                                ／<fmt:formatNumber value="${benDetailData.monIncome}" />
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="4">
                                                <span class="issuetitle_L_down">學校代碼：</span>
                                                <c:out value="${benDetailData.schoolCodeStr}"/>&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">受禁治產(監護)宣告：</span>
                                                <c:out value="${benDetailData.interDictMk}" />&nbsp;
                                                <c:if test="${benDetailData.interDictMk eq 'Y'}">
                                                    (宣告起迄期間：
                                                        <c:out value="${benDetailData.interDictDateStr}" />
                                                        <c:if test="${(benDetailData.interDictDateStr ne '')or(benDetailData.repealInterdictDateStr ne '')}">
                                                            &nbsp;~&nbsp;
                                                        </c:if>
                                                        <c:out value="${benDetailData.repealInterdictDateStr}" />
                                                    )
                                                </c:if>
                                            </td>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">重殘起迄年月：</span>
                                                <c:if test="${(benDetailData.handicapSdateStr ne '')or(benDetailData.handicapEdateStr ne '')}">
                                                    <a href="<c:url value="/paymentQuery.do?method=doSurvivorHandicapDetailData&apNo=${benDetailData.apNo}&seqNo=${benDetailData.seqNo}" />" title="連結至：重殘起迄年月" target="_self">
                                                        <c:out value="${benDetailData.handicapSdateStr}" />
                                                        &nbsp;~&nbsp;
                                                        <c:out value="${benDetailData.handicapEdateStr}" />
                                                        &nbsp;
                                                        (<fmt:formatNumber value="${benDetailData.handicapDataCount}" />)
                                                    </a>
                                                </c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">遺屬失蹤起迄期間：</span>
                                                <c:out value="${benDetailData.benMissingSdateStr}" />
                                                <c:if test="${(benDetailData.benMissingSdateStr ne '')or(benDetailData.benMissingEdateStr ne '')}">
                                                    &nbsp;~&nbsp;
                                                </c:if>
                                                <c:out value="${benDetailData.benMissingEdateStr}" />&nbsp;
                                            </td>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">監管起迄期間：</span>
                                                <c:out value="${benDetailData.prisonSdateStr}" />
                                                <c:if test="${(benDetailData.prisonSdateStr ne '')or(benDetailData.prisonEdateStr ne '')}">
                                                    &nbsp;~&nbsp;
                                                </c:if>
                                                <c:out value="${benDetailData.prisonEdateStr}" />&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="1" width="25%">
                                                <span class="issuetitle_L_down">電話1：</span>
                                                <c:out value="${benDetailData.tel1}" />&nbsp;
                                            </td>
                                            <td id="iss" colspan="1" width="25%">
                                                <span class="issuetitle_L_down">電話2：</span>
                                                <c:out value="${benDetailData.tel2}" />&nbsp;
                                            </td>
                                            <td id="iss" colspan="2" valign="top" nowrap="nowrap">
                                                <span class="issuetitle_L_down">地址：</span>
                                                <c:if test="${benDetailData.commTyp eq '1'}">
                                                    <c:out value="同戶籍地" />&nbsp;-&nbsp;<c:out value="${benDetailData.commZip}" />&nbsp;
                                                </c:if>
                                                <c:if test="${benDetailData.commTyp eq '2'}">
                                                    <c:out value="現住址" />&nbsp;-&nbsp;<c:out value="${benDetailData.commZip}" />&nbsp;
                                                </c:if>
                                                <c:out value="${benDetailData.commAddr}" />&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">給付方式：</span>
                                                <c:if test="${(benDetailData.accRel eq '1')or(benDetailData.accRel eq '2')}">
                                                    <c:out value="本人領取" />&nbsp;-&nbsp;
                                                    <c:out value="${benDetailData.payTypStr}" />
                                                </c:if>
                                                <c:if test="${benDetailData.accRel eq '3'}">
                                                    <c:out value="具名領取" />&nbsp;-&nbsp;<c:out value="${benDetailData.accName}" />
                                                </c:if>&nbsp;                                    
                                            </td>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">帳號：</span>
                                                <c:if test="${(benDetailData.accRel eq '1')or(benDetailData.accRel eq '2')}">
                                                    <c:out value="${benDetailData.payBankId}" />&nbsp;-&nbsp;
                                                    <c:choose>
                                                          <c:when test="${(benDetailData.payTyp eq '1')and(benDetailData.payBankId ne '700')}">
                                                              <c:out value="0000" />
                                                          </c:when>
                                                          <c:otherwise>
                                                              <c:out value="${benDetailData.branchId}" />
                                                          </c:otherwise>
                                                    </c:choose>
                                                    <c:if test="${(benDetailData.branchId ne '')or(benDetailData.payEeacc ne '')}">
                                                        &nbsp;-&nbsp;
                                                    </c:if>
                                                    <c:out value="${benDetailData.payEeacc}" />&nbsp;
                                                    <c:if test="${benDetailData.bankName ne ''}">
                                                        (&nbsp;<c:out value="${benDetailData.bankName}" />&nbsp;)
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${benDetailData.accRel eq '3'}">
                                                    &nbsp;
                                                </c:if>
                                                <c:if test="${benDetailData.specialAcc eq 'Y'}">
                                                   &nbsp;專戶
                                                </c:if>
                                            </td>
                                         </tr>
                                         <tr>
                                            <td id="iss" colspan="4">
                                                <span class="issuetitle_L_down">戶名：</span>
                                                <c:if test="${(benDetailData.accRel eq '1')or(benDetailData.accRel eq '2')}">
                                                    <c:out value="${benDetailData.accName}" />
                                                </c:if>
                                                <c:if test="${benDetailData.accRel eq '3'}">
                                                    &nbsp;
                                                </c:if>
                                            </td>
                                        </tr>
                                        <%-- Added By LouisChange 20200311 begin --%>
                                        <%-- ba_1090122544 需求 : 戶名下面新增一行，新增顯示「原住民羅馬拼音:」; 此欄位有值才顯示 --%>
                                        <c:if test="${not empty benDetailData.rmp_Name}">
                                        	<td id="iss" colspan="4">
                                               <span class="issuetitle_L_down">原住民羅馬拼音：</span>
                                                <c:out value="${benDetailData.rmp_Name}" />&nbsp;
                                            </td>
                                        </c:if>
                                        <%-- Added By LouisChange 20200311 end --%>
                                        <c:if test="${(benDetailData.benEvtRel ne 'Z')and(benDetailData.benEvtRel ne 'F')and(benDetailData.benEvtRel ne 'N') }">
                                            <tr>
                                                <td id="iss" colspan="1">
                                                    <span class="issuetitle_L_down">婚姻狀況：</span>
                                                    <c:out value="${benDetailData.benMarrMkStr}" />&nbsp;
                                                </td>
                                                <td id="iss" colspan="3">
                                                    <span class="issuetitle_L_down">法定代理人姓名：</span>
                                                    <c:out value="${benDetailData.grdName}" />&nbsp;
                                                </td>
                                            </tr>
                                            <tr>    
                                                <td id="iss" colspan="1">
                                                    <span class="issuetitle_L_down">計息存儲：</span>
                                                    <c:out value="${benDetailData.savingMkStr}" />&nbsp;
                                                </td>
                                                <td id="iss" colspan="1">
                                                    <span class="issuetitle_L_down">法定代理人身分證號：</span>
                                                    <c:out value="${benDetailData.grdIdnNo}" />&nbsp;
                                                </td>
                                                <td id="iss" colspan="2">
                                                    <span class="issuetitle_L_down">法定代理人出生日期：</span>
                                                    <c:out value="${benDetailData.grdBrDateStr}" />&nbsp;
                                                </td>
                                            </tr>
                                        </c:if>
                                        <tr>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">代辦人姓名：</span>
                                                <c:out value="${benDetailData.assignName}" />&nbsp;
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">代辦人身分證號：</span>
                                                <c:out value="${benDetailData.assignIdnNo}" />&nbsp;
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">代辦人出生日期：</span>
                                                <c:out value="${benDetailData.assignBrDateStr}" />&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">結案日期：</span>
                                                <c:out value="${benDetailData.closeDateStr}" />&nbsp;
                                            </td>
                                            <td id="iss" colspan="1">
                                                <span class="issuetitle_L_down">結案原因：</span>
                                                <c:out value="${benDetailData.closeCause}" />&nbsp;
                                            </td>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">不合格原因：</span>
                                                <c:out value="${benDetailData.unqualifiedCause}" />&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">擇領起月：</span>
                                                <c:out value="${benDetailData.choiceYmStr}" />&nbsp;
                                            </td>
                                            <td id="iss" colspan="3">
                                                <span class="issuetitle_L_down">擇領老年年金受理編號：</span>
                                                &nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="4">
                                                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                                                    <tr>
                                                        <td width="13%" valign="top">
                                                            <span class="issuetitle_L_down" title="21-滿55歲且婚姻1年以上,25-有扶養符合條件之子女者">遺屬符合註記：</span>
                                                        </td>
                                                        <td width="87%" valign="top">
                                                            <logic:iterate id="chkFileData" name="benDetailData" property="matchChkList">                                                    
                                                                <c:out value="${chkFileData.issuPayYm}" />：
                                                                <logic:iterate id="chkFile" indexId="i" name="chkFileData" property="chkFileDataList">
                                                                    <span title="<c:out value="${chkFile.chkResult}" />">
                                                                        <c:out value="${chkFile.chkCodePost}"/>
                                                                    </span>
                                                                    <c:if test="${i+1 ne chkFileData.chkFileDataSize}">
                                                                        <c:out value=",　" />
                                                                    </c:if>
                                                                </logic:iterate><br>
                                                            </logic:iterate>
                                                            <logic:empty name="benDetailData" property="matchChkList">
                                                                &nbsp;
                                                            </logic:empty>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="4">
                                                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                                                    <tr>
                                                        <td width="13%" valign="top">
                                                            <span class="issuetitle_L_down" title="09-親屬關係已變更致不符請領資格,12-收入已逾標準">遺屬編審註記：</span>
                                                        </td>
                                                        <td width="87%" valign="top">
                                                            <logic:iterate id="chkFileData" name="benDetailData" property="benChkList">                                                    
                                                                <c:out value="${chkFileData.issuPayYm}" />：
                                                                <logic:iterate id="chkFile" indexId="i" name="chkFileData" property="chkFileDataList">
                                                                    <span title="<c:out value="${chkFile.chkResult}" />">
                                                                        <c:out value="${chkFile.chkCodePost}"/>
                                                                    </span>
                                                                    <c:if test="${i+1 ne chkFileData.chkFileDataSize}">
                                                                        <c:out value=",　" />
                                                                    </c:if>
                                                                </logic:iterate><br>
                                                            </logic:iterate>
                                                            <logic:empty name="benDetailData" property="benChkList">
                                                                &nbsp;
                                                            </logic:empty> 
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="4"></td>
                                        </tr>
                                   </table>
                                </td>
                            </tr>           
                            </c:if>       
                        </logic:iterate>    
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
