<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D112Q02" />
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
                                <bean:define id="disabledData" name="<%=ConstantKey.PAYMENT_QUERY_DISABLED_DATA_CASE%>" />
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
                                        <td width="33%">
                                            <span class="issuetitle_L_down">事故者姓名：</span>
                                            <c:out value="${detail.evtName}" />
                                        </td>
                                        <td width="33%">
                                            <c:choose>
                                                <c:when test="${detail.containCheckMark3M}">
                                                    <span class="issuetitle_L_down">併計國保年資：</span>
                                                    <c:out value="${detail.comnpMkStr}" />
                                                </c:when>
                                                <c:otherwise>
                                                    &nbsp;
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td width="34%">
                                            <span class="issuetitle_L_down">年金請領資格：</span>
                                            <c:out value="${disabledData.disQualMkStr}" />
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
                                        <td >
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledApplicationData" />' title="連結至：案件資料" target="_self" class="needtxt"><span>案件資料</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledBenData" />' title="連結至：事故者/受款人" target="_self" class="needtxt"><span>事故者／受款人</span></a>
                                            <h5><a>眷屬資料</a></h5>
                                            <%--<a href='<c:url value="/paymentQuery.do?method=doDisabledReFeesMasterDataList" />' title="連結至：複檢費用資料" target="_self" class="needtxt"><span>複檢費用資料</span></a>--%>
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledAvgAmtData" />' title="連結至：平均薪資" target="_self" class="needtxt"><span>平均薪資</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledLoanData" />' title="連結至：紓困貸款" target="_self" class="needtxt"><span>紓困貸款</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledNpData" />' title="連結至：國保資料" target="_self" class="needtxt"><span>國保資料</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledCpiData" />' title="連結至：物價指數調整" target="_self" class="needtxt"><span>物價指數調整</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledCpiRecData" />' title="連結至：物價指數調整記錄" target="_self" class="needtxt"><span>物價指數調整記錄</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledArclistData" />' title="連結至：歸檔記錄資料" target="_self" class="needtxt"><span>歸檔記錄資料</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledUpdateLogQueryData" />' title="連結至：更正日誌" target="_self" class="needtxt"><span>更正日誌</span></a>
                                            <c:if test="${(detail.payKind eq '35') or (detail.payKind eq '38')}">
                                               <a href='<c:url value="/paymentQuery.do?method=doDisabledRehcData" />' title="連結至：重新查核失能程度" target="_self" class="needtxt"><span>重新查核失能程度</span></a>
                                            </c:if> 
                                         </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <logic:empty name="<%=ConstantKey.PAYMENT_QUERY_FAMILY_DATA_LIST%>">
                            <tr>
                                <td align="center" class="table1">
                                    <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                        <tr>
                                            <td colspan="4" class="issuetitle_L">
                                                <span class="systemMsg">▲</span>&nbsp;眷屬資料
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="4" class="issuetitle_L_down" align="center">
                                                <font color="red">無資料</font>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </logic:empty>
                        <logic:iterate id="famData" indexId="famDataIndex" name="<%=ConstantKey.PAYMENT_QUERY_FAMILY_DATA_LIST%>">
                            <tr>
                                <td align="center" class="table1">
                                    <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                        <tr>
                                            <td colspan="4" class="issuetitle_L">
                                                <span class="systemMsg">▲</span>&nbsp;眷屬資料
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">序　號：</span>
                                                <c:out value="${famData.seqNo}"/>&nbsp;
                                            </td>
                                            <td id="iss" colspan="3">
                                                <span class="issuetitle_L_down">姓　名：</span>
                                                <c:out value="${famData.famName}"/>&nbsp;
                                            </td>
                                        </tr>
    
                                        <tr>
                                            <td width="25%" id="iss">
                                                <span class="issuetitle_L_down">身分證號：</span>
                                                <c:out value="${famData.famIdnNo}"/>&nbsp;
                                            </td>  
                                            <td width="25%" id="iss">
                                                <span class="issuetitle_L_down">出生日期：</span>
                                                <c:out value="${famData.famBrDateStr}"/>&nbsp;
                                            </td>
                                            <td width="25%" id="iss">
                                                <span class="issuetitle_L_down">性　別：</span>
                                                <c:out value="${famData.famSexStr}"/>&nbsp;
                                            </td>
                                            <td width="25%" id="iss">
                                                <span class="issuetitle_L_down">關　係：</span>
                                                <c:out value="${famData.famEvtRelStr}"/>&nbsp;
                                            </td>
                                        </tr>
                                        <tr>                  
                                            <td id="iss">
                                                <span class="issuetitle_L_down">申請日期：</span>
                                                <c:out value="${famData.famAppDateStr}"/>&nbsp;
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">國籍別：</span>
                                                <c:out value="${famData.famNationTypStr}"/>&nbsp;
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">國　籍：</span>
                                                <c:out value="${famData.famNationCode}" />&nbsp;
                                                <c:out value="${famData.famNationName}" />
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">身份查核年月：</span>
                                                <c:out value="${famData.idnChkYmStr}" />&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">結婚日期：</span>
                                                <c:out value="${famData.marryDateStr}"/>&nbsp;
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">離婚日期：</span>
                                                <c:out value="${famData.divorceDateStr}"/>&nbsp;
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">放棄請領起始年月：</span>
                                                <c:out value="${famData.abanApsYmStr}"/>&nbsp;
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">死亡日期：</span>
                                                <c:out value="${famData.famDieDateStr}"/>&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">配偶扶養：</span>
                                                <c:out value="${famData.raiseChildMkStr}"/>&nbsp;
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">收養日期：</span>
                                                <c:out value="${famData.adoPtDateStr}"/>&nbsp;
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">親屬關係變動日期：</span>
                                                <c:out value="${famData.relatChgDateStr}"/>&nbsp;
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">得請領起月：</span>
                                                <c:out value="${famData.ableApsYmStr}"/>&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">在學起迄年月：</span>
                                                <c:if test="${(famData.studSdate ne '')or(famData.studEdate ne '')}">
                                                    <a href="<c:url value="/paymentQuery.do?method=doDisabledStudDetailData&apNo=${famData.apNo}&seqNo=${famData.seqNo}" />" title="連結至：在學起迄年月" target="_self">
                                                        <c:out value="${famData.studSdateStr}" />
                                                        &nbsp;~&nbsp;
                                                        <c:out value="${famData.studEdateStr}" />
                                                        &nbsp;
                                                        (<fmt:formatNumber value="${famData.studDataCount}" />)
                                                    </a>
                                                </c:if>
                                            </td>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">每月工作收入註記／收入：</span>
                                                <c:out value="${famData.monIncomeMkStr}"/>
                                                <c:if test="${(famData.monIncomeMkStr ne '') or (famData.monIncome ne null)}">
                                                    &nbsp;／&nbsp;
                                                </c:if>
                                                <c:out value="${famData.monIncome}"/>&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="4">
                                                <span class="issuetitle_L_down">學校代碼：</span>
                                                <c:out value="${famData.schoolCodeStr}"/>&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">受禁治產(監護)宣告：</span>
                                                <c:out value="${famData.interDictMk}"/>
                                                <c:if test="${famData.interDictMk eq 'Y'}">
                                                    (宣告起迄期間：
                                                        <c:out value="${famData.interDictDateStr}" />
                                                        <c:if test="${(famData.interDictDateStr ne '')or(famData.repealInterDictDateStr ne '')}">
                                                            &nbsp;~&nbsp;
                                                        </c:if>
                                                        <c:out value="${famData.repealInterDictDateStr}" />
                                                    )
                                                </c:if>
                                            </td>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">領有重度以上身心障礙手冊或證明：</span>
                                                <c:out value="${famData.handIcapMkStr}"/>&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">眷屬失蹤起迄期間：</span>
                                                <c:out value="${famData.benMissingSdateStr}"/>
                                                <c:if test="${(famData.benMissingSdateStr ne '') or (famData.benMissingEdateStr ne '')}">
                                                &nbsp;~&nbsp;
                                                </c:if>
                                                <c:out value="${famData.benMissingEdateStr}"/>&nbsp;
                                            </td>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">監管起迄期間：</span>
                                                <c:out value="${famData.prisonSdateStr}"/>
                                                <c:if test="${(famData.prisonSdateStr ne '') or (famData.prisonEdateStr ne '')}">
                                                &nbsp;~&nbsp;
                                                </c:if>
                                                <c:out value="${famData.prisonEdateStr}"/>&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">結案日期：</span>
                                                <c:out value="${famData.closeDateStr}"/>&nbsp;
                                            </td>
                                            <td id="iss" colspan="3">
                                                <span class="issuetitle_L_down">結案原因：</span>
                                                <c:out value="${famData.closeCause}"/>&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="4">
                                                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                                                    <tr>
                                                        <td width="13%" valign="top">
                                                            <span class="issuetitle_L_down" title="21-滿55歲且婚姻1年以上,25-有扶養符合條件之子女者">眷屬符合註記：</span>
                                                        </td>
                                                        <td width="87%" valign="top">
                                                            <logic:iterate id="chkFileData" name="famData" property="matchChkFileDataList">                                                    
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
                                                            <logic:empty name="famData" property="matchChkFileDataList">
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
                                                            <span class="issuetitle_L_down" title="09-親屬關係已變更致不符請領資格,12-收入已逾標準">眷屬編審註記：</span>
                                                        </td>
                                                        <td width="87%" valign="top">
                                                            <logic:iterate id="chkFileData" name="famData" property="benChkFileDataList">                                                    
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
                                                            <logic:empty name="famData" property="benChkFileDataList">
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
