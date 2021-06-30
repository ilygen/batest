<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D112Q05" />
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
                            <td align="left"  style="color: blue;font-size: 120%;font-weight: bold;">
                               <c:if test="${detail.secrecy eq 'Y'}">
                                    <c:out value="※保密案件"/>
                               </c:if>
                            </td> 
                            <td align="right" >
                                <acl:checkButton buttonName="btnBack">
                                    <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doDisabledReFeesMasterDataList'; document.PaymentQueryForm.submit();" />&nbsp;
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
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledApplicationData" />' title="連結至：案件資料" target="_self" class="needtxt"><span>案件資料</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledBenData" />' title="連結至：事故者/受款人" target="_self" class="needtxt"><span>事故者／受款人</span></a>
                                            <a href='<c:url value="/paymentQuery.do?method=doDisabledFamilyData" />' title="連結至：眷屬資料" target="_self" class="needtxt"><span>眷屬資料</span></a>
                                            <h5><a>複檢費用資料</a></h5>
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
                        <tr>
                            <td align="center" class="table1">
                                <bean:define id="reFees" name="<%=ConstantKey.PAYMENT_QUERY_REFEES_DETAIL_DATA_CASE%>" />
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
                                        <td colspan="4" class="issuetitle_L">
                                            <span class="systemMsg">▲</span>&nbsp;申請資料
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">複檢費用受理編號：</span>
                                            <c:out value="${reFees.reApNoStrDisplay}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">複檢費用申請序：</span>
                                            <c:out value="${reFees.apSeq}" />&nbsp;
                                        </td>              
                                    </tr>
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">複檢費用申請日期：</span>
                                            <c:out value="${reFees.recosDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">通知複檢日期：</span>
                                            <c:out value="${reFees.inreDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">複檢門診次數：</span>
                                            <c:out value="${reFees.reNum}" />&nbsp;
                                        </td>       
                                        <td id="iss">
                                            <span class="issuetitle_L_down">複檢住院天數：</span>
                                            <c:out value="${reFees.rehpStay}" />&nbsp;
                                        </td>      
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">複檢原因：</span>
                                            <c:out value="${reFees.reasCode}" />&nbsp;
                                        </td>             
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">複檢醫院代碼：</span>
                                            <c:out value="${reFees.hosId}" />&nbsp;( <c:out value="${reFees.hpSnam}" />)
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">複檢費用：</span>
                                            <c:out value="${reFees.reFees}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">非複檢必須費用：</span>
                                            <c:out value="${reFees.nonreFees}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">複檢實付金額：</span>
                                            <c:out value="${reFees.reAmtPay}" />&nbsp;
                                        </td>       
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">複檢費用處理狀態：</span>
                                            <c:out value="${reFees.procStatStr}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">決行層級：</span>
                                            <c:out value="${reFees.mexcLvl}" />&nbsp;
                                        </td>
                                    </tr>   
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">初核日期：</span>
                                            <c:out value="${reFees.chkDateStr}" />&nbsp;
                                        </td>        
                                        <td id="iss">
                                            <span class="issuetitle_L_down">初核人員：</span>
                                            <c:out value="${reFees.chkMan}" />&nbsp;
                                        </td>            
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">初核狀況：</span>
                                            <c:out value="${reFees.chkStatStr}" />&nbsp;
                                        </td>    
                                    </tr>   
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">複核日期：</span>
                                            <c:out value="${reFees.rechkDate}" />&nbsp;
                                        </td>        
                                        <td id="iss">
                                            <span class="issuetitle_L_down">複核人員：</span>
                                            <c:out value="${reFees.rechkMan}" />&nbsp;
                                        </td>            
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">複核狀況：</span>
                                            <c:out value="${reFees.rechkStatStr}" />&nbsp;
                                        </td>    
                                    </tr>   
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">決行日期：</span>
                                            <c:out value="${reFees.exeDateStr}" />&nbsp;
                                        </td>        
                                        <td id="iss" colspan="3">
                                            <span class="issuetitle_L_down">決行人員：</span>
                                            <c:out value="${reFees.exeMan}" />&nbsp;
                                        </td>            
                                    </tr>             
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">核付日期：</span>
                                            <c:out value="${reFees.payDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">入帳日期：</span>
                                            <c:out value="${reFees.rpayDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">退匯日期：</span>
                                            <c:out value="${reFees.rtDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">改匯日期：</span>
                                            <c:out value="${reFees.repDateStr}" />&nbsp;
                                        </td>
                                    </tr>       
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">止付日期：</span>
                                            <c:out value="${reFees.stexpndDateStr}" />&nbsp;
                                        </td>        
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">止付原因：</span>
                                            <c:out value="${reFees.stexpndReason}" />&nbsp;
                                        </td>    
                                    </tr>
                                    <tr>
                                        <td colspan="4" class="issuetitle_L">
                                            <span class="systemMsg">▲</span>&nbsp;受款人資料
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
                                            <span class="issuetitle_L_down">受款人姓名：</span>
                                            <c:out value="${reFees.benName}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">受款人身分證號：</span>
                                            <c:out value="${reFees.benIdnNo}" />&nbsp;
                                        </td>
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">受款人出生日期：</span>
                                            <c:out value="${reFees.benBrDateStr}" />&nbsp;
                                        </td>
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">性　別：</span>
                                            <c:if test="${reFees.benSex eq '1'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_1 %>" />                                    
                                            </c:if>
                                            <c:if test="${reFees.benSex eq '2'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_2 %>" />                                    
                                            </c:if>&nbsp;
                                        </td>
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">關　係：</span>
                                            <c:out value="${reFees.benEvtRelStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">國籍別：</span>
                                            <c:if test="${reFees.benNationTyp eq '1'}">
                                                <c:out value="本國" />
                                            </c:if>
                                            <c:if test="${reFees.benNationTyp eq '2'}">
                                                <c:out value="外籍" />
                                            </c:if>&nbsp;
                                        <td id="iss">
                                            <span class="issuetitle_L_down">國　籍：</span>
                                            <c:out value="${reFees.benNationCode}" />&nbsp;
                                            <c:out value="${reFees.benNationName}" />
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">核定格式：</span>
                                            <c:out value="${reFees.notifyForm}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">電　話1：</span>
                                            <c:out value="${reFees.tel1}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">電　話2：</span>
                                            <c:out value="${reFees.tel2}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">地　址：</span>
                                            <c:if test="${reFees.commTyp eq '1'}">
                                                <c:out value="同戶籍地" />&nbsp;-&nbsp;<c:out value="${reFees.commZip}" />&nbsp;
                                            </c:if>
                                            <c:if test="${reFees.commTyp eq '2'}">
                                                <c:out value="現住址" />&nbsp;-&nbsp;<c:out value="${reFees.commZip}" />&nbsp;
                                            </c:if>
                                            <c:out value="${reFees.commAddr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">給付方式：</span>
                                            <c:out value="本人領取" />&nbsp;-&nbsp;
                                            <c:out value="${reFees.payTypStr}" />
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">帳　號：</span>
                                            <c:out value="${reFees.payBankId}" />
                                            <c:out value="${reFees.branchId}" />&nbsp;-&nbsp;
                                            <c:out value="${reFees.payeeAcc}" />&nbsp;
                                            (&nbsp;<c:out value="${reFees.bankName}" />&nbsp;)
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
                                            <span class="issuetitle_L_down">戶　名：</span>
                                            <c:out value="${reFees.accName}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="4">
                                        </td>
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
