<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D162Q04" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/queryCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
                    
    }      
    
    // Ajax for 更新核定資料
    function flashIssuData() {
        var qryCond = '<c:out value="${PaymentQueryCondForm.qryCond}" />';
        var optionYm = 'ALL';
                
        if(qryCond=='ISSUYM'){
            optionYm = $("issuYmOption").value;
        }
        if(qryCond=='PAYYM'){
            optionYm = $("payYmOption").value;
        }
        queryCommonAjaxDo.flashNpIssuDataList(optionYm, qryCond, setIssuData);
    }
    
    function setIssuData(issuDataList) {
        dwr.util.removeAllRows("issuDataTable");
        $("issuDataSpan").innerHTML = "";
        var qryCond = '<c:out value="${PaymentQueryCondForm.qryCond}" />';
        var innerHTMLStr = "";
        if(issuDataList.length==0){
            innerHTMLStr += '<table width="100%" border="0" align="center" cellpadding="0" cellspacing="2" class="px13">';
            innerHTMLStr += '<tr><td width="100%" align="center">';
            innerHTMLStr += '<font color="red">無資料</font>';
            innerHTMLStr += '</td></tr>';
            innerHTMLStr += '</table>';
        }else{
            for(var i=0; i<issuDataList.length; i++){
                var issuData = issuDataList[i];
                var issuDataArray = issuData.issuDataArray;
                innerHTMLStr += '<tr align="center"><td colspan="10">';
                if(i%2 == 0){
                    innerHTMLStr += '<table id="tb" width="100%" cellpadding="0" cellspacing="2" class="px13">';
                }else{
                    innerHTMLStr += '<table id="tb" width="100%" cellpadding="0" cellspacing="2" class="px13" bgcolor="#E8E8FF">';
                }
                
                innerHTMLStr += '<tr>';
                innerHTMLStr += '<td colspan="10" width="100%" class="issuetitle_L_down">';
                if(qryCond == "ISSUYM"){
                    innerHTMLStr += '核定年月：' + getDefaultString(issuData.issuYmStr);
                }                      
                else if(qryCond == "PAYYM"){
                    innerHTMLStr += '給付年月：' + getDefaultString(issuData.payYmStr);
                }
                innerHTMLStr += '</td>';
                innerHTMLStr += '</tr>';
                
                if(issuDataArray.length == 0){
                    innerHTMLStr += '<tr>';
                    innerHTMLStr += '<td align="center" colspan="10">';
                    innerHTMLStr += '<font color="red">無資料</font>';
                    innerHTMLStr += '</td>';
                    innerHTMLStr += '</tr>';
                }else{
                    for(var j=0; j<issuDataArray.length; j++){
                        var subIssuData = issuDataArray[j];
                        
                        if(j==0){
                            innerHTMLStr += '<tr align="center">';
                            if(qryCond == "ISSUYM"){
                                innerHTMLStr += '<td width="10%" class="issuetitle_L">給付年月</td>';
                            }else if(qryCond == "PAYYM"){
                                innerHTMLStr += '<td width="10%" class="issuetitle_L">核定年月</td>';
                            }
                            innerHTMLStr += '<td width="10%" class="issuetitle_L">國保金額</td>';
                            innerHTMLStr += '<td width="10%" class="issuetitle_L">在途保費</td>';
                            innerHTMLStr += '<td width="10%" class="issuetitle_L">利 息</td>';
                            innerHTMLStr += '<td width="10%" class="issuetitle_L">應 收</td>';
                            innerHTMLStr += '<td width="10%" class="issuetitle_L">補 發</td>';
                            innerHTMLStr += '<td width="10%" class="issuetitle_L">減 領</td>';
                            innerHTMLStr += '<td width="10%" class="issuetitle_L">另案扣減</td>';
                            innerHTMLStr += '<td width="10%" class="issuetitle_L">實付金額</td>';
                            innerHTMLStr += '<td width="10%" class="issuetitle_L"><span class="needtxt">審核結果</span></td>';
                            innerHTMLStr += '</tr>';
                        }
                        
                        innerHTMLStr += '<tr>';
                        if(qryCond == "ISSUYM"){
                            innerHTMLStr += '<td width="10%" class="issueinfo_C">'+getDefaultString(subIssuData.payYmStr)+'</td>';
                        }else if(qryCond == "PAYYM"){
                            innerHTMLStr += '<td width="10%" class="issueinfo_C">'+getDefaultString(subIssuData.issuYmStr)+'</td>';
                        }
                        //國保金額
                        innerHTMLStr += '<td width="10%" class="issueinfo_R">'+getDefaultString(numFormat(subIssuData.issueAmt))+'</td>';
                        //在途保費
                        innerHTMLStr += '<td width="10%" class="issueinfo_R">'+getDefaultString(numFormat(subIssuData.sagtotAmt))+'</td>';
                        //利息
                        innerHTMLStr += '<td width="10%" class="issueinfo_R">'+getDefaultString(numFormat(subIssuData.itrtAmt))+'</td>';
                        //應收
                        innerHTMLStr += '<td width="10%" class="issueinfo_R">'+getDefaultString(numFormat(subIssuData.recAmt))+'</td>';
                        //補發
                        innerHTMLStr += '<td width="10%" class="issueinfo_R">'+getDefaultString(numFormat(subIssuData.supAmt))+'</td>';
                        //減領
                        innerHTMLStr += '<td width="10%" class="issueinfo_R">'+getDefaultString(numFormat(subIssuData.cutAmt))+'</td>';
                        //另案扣減
                        innerHTMLStr += '<td width="10%" class="issueinfo_R">'+getDefaultString(numFormat(subIssuData.otherAmt))+'</td>';
                        //實付金額
                        innerHTMLStr += '<td width="10%" class="issueinfo_R">'+getDefaultString(numFormat(subIssuData.aplPayAmt))+'</td>';
                        //審核結果
                        innerHTMLStr += '<td width="10%" class="issueinfo_C"><span  class="needtxt">'+getDefaultString(subIssuData.manChkFlg)+'</span></td>';
                        innerHTMLStr += '</tr>';
                    }                
                }
                innerHTMLStr +='</table>';
            }
        }
        $("issuDataSpan").innerHTML = innerHTMLStr;
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
                <html:hidden styleId="reApNo" property="reApNo" value="" />
                <html:hidden styleId="apSeq" property="apSeq" value="" />
                <html:hidden styleId="apNo" property="apNo" value="" />

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
                                        <td>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledApplicationData" />' title="連結至：案件資料" target="_self" class="needtxt"><span>案件資料</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledBenData" />' title="連結至：事故者/受款人" target="_self" class="needtxt"><span>事故者／受款人</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledFamilyData" />' title="連結至：眷屬資料" target="_self" class="needtxt"><span>眷屬資料</span></a>
                                            <%--<a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledReFeesMasterDataList" />' title="連結至：複檢費用資料" target="_self" class="needtxt"><span>複檢費用資料</span></a>--%>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledAvgAmtData" />' title="連結至：平均薪資" target="_self" class="needtxt"><span>平均薪資</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledLoanData" />' title="連結至：紓困貸款" target="_self" class="needtxt"><span>紓困貸款</span></a>
                                            <h5><a>國保資料</a></h5>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledCpiData" />' title="連結至：物價指數調整" target="_self" class="needtxt"><span>物價指數調整</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledCpiRecData" />' title="連結至：物價指數調整記錄" target="_self" class="needtxt"><span>物價指數調整記錄</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledArclistData" />' title="連結至：歸檔記錄" target="_self" class="needtxt"><span>歸檔記錄</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledUpdateLogQueryData" />' title="連結至：更正日誌" target="_self" class="needtxt"><span>更正日誌</span></a>
                                            <c:if test="${(detail.payKind eq '35') or (detail.payKind eq '38')}">
                                               <a href='<c:url value="/paymentQuerySimplify.do?method=doDisabledRehcData" />' title="連結至：重新查核失能程度" target="_self" class="needtxt"><span>重新查核失能程度</span></a>
                                            </c:if> 
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14" align="center" class="table1" align="center">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                <bean:define id="npData" name="<%=ConstantKey.PAYMENT_QUERY_NP_DATA_CASE%>" />
                                    <tr>
                                        <td colspan="4" class="issuetitle_L">
                                            <span class="systemMsg">▲</span>&nbsp;國保資料
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">國保受理編號：</span>
                                            <c:out value="${npData.apNoStrDisplay}" />
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">國保年資：</span>
                                            <c:out value="${npData.valSeniSrt}" />
                                        </td>
                                        <c:if test="${detail.payKind eq '36'}">
                                            <td id="iss">
                                                <span class="issuetitle_L_down">首發年月：</span>
                                                <c:out value="${npData.payYmsString}" />
                                            </td>
                                            <td id="iss">
                                                <span class="issuetitle_L_down">併計勞保年資：</span>
                                                <c:out value="${npData.labMerge}" />
                                            </td>                                  
                                        </c:if>
                                        <c:if test="${detail.payKind ne '36'}">
                                            <td id="iss">&nbsp;</td>
                                        </c:if>
                                    </tr>
                                    <c:if test="${detail.payKind eq '36'}">
                                        <tr>
                                            <td id="iss">
                                            <span class="issuetitle_L_down">障礙等級：</span>
                                                <c:out value="${npData.dabLevel}" />
                                            </td>
                                            <td id="iss">
                                            <span class="issuetitle_L_down">障礙類別：</span>
                                                <c:out value="${npData.dabType}" />
                                            </td>
                                            <td id="iss">
                                            <span class="issuetitle_L_down">鑑定部位：</span>
                                                <c:out value="${npData.dabPart}" />
                                            </td>
                                            <td id="iss">
                                            <span class="issuetitle_L_down">國保電腦／人工審核結果：</span>
                                                <c:out value="${npData.manChkFlg}" />
                                            </td>                                            
                                        </tr>
                                    </c:if>                                        
                                    <tr>
                                         <td colspan="4"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        
                        <bean:define id="qryForm" name="<%=ConstantKey.PAYMENT_QUERY_COND_FORM%>" />
                        <c:if test="${((qryForm.startYm ne '')&&(qryForm.endYm ne ''))}">
                        <tr>
                            <td colspan="14" class="issuetitle_L"><br>
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
                        
                        <bean:define id="issuDataList" name="<%=ConstantKey.PAYMENT_QUERY_NP_ISSU_DATA_LIST%>" />
                        <c:if test="${npData.mapNoStr eq ''}">
                            <tr>
                                <td align="center" colspan="14">
                                    <font color="red">並無關聯國保資料的受理編號。</font>
                                </td>
                            </tr>   
                        </c:if>
                        <c:if test="${npData.mapNoStr ne ''}">
                        <logic:empty name="issuDataList" >
                            <tr>
                                <td align="center" colspan="14">
                                    <font color="red">無資料</font>
                                </td>
                            </tr>                                
                        </logic:empty>
                        <logic:notEmpty name="issuDataList">
                            <tr>
                                <td colspan="14" class="issuetitle_L">
                                                                      篩選條件：
                                   <c:if test="${(qryForm.qryCond eq 'ISSUYM')}">
                                                                                核定年月&nbsp;-&nbsp;
                                        <html:select tabindex="70" property="issuYmOption" styleClass="formtxt" onchange="flashIssuData();">
                                            <html:option value="ALL">全 選</html:option>
                                            <html:options collection="<%=ConstantKey.PAYMENT_QUERY_NPDATA_ISSUYM_OPTION_LIST%>" property="issuYm" labelProperty="issuYmStr" />
                                        </html:select>
                                    </c:if>
                                    <c:if test="${(qryForm.qryCond eq 'PAYYM')}">
                                                                                給付年月&nbsp;-&nbsp;
                                        <html:select tabindex="70" property="payYmOption" styleClass="formtxt" onchange="flashIssuData();">
                                            <html:option value="ALL">全 選</html:option>
                                            <html:options collection="<%=ConstantKey.PAYMENT_QUERY_NPDATA_PAYYM_OPTION_LIST%>" property="payYm" labelProperty="payYmStr" />
                                        </html:select>
                                    </c:if>
                                </td>
                            </tr>
                            <tr align="center">
                                <td colspan="14">
                                    <span id="issuDataSpan"></span>
                                    <tbody id="issuDataTable" align="center" width="100%" cellpadding="0" cellspacing="2" class="px13">
                                            <%Integer index=0; %>
                                            <logic:iterate id="issu" indexId="issuIndex" name="issuDataList" >
                                                <%--判斷資料底色用 --%>
                                                <%index += 1; %>
                                                <tr align="center">
                                                    <td colspan="10">
                                                        <%if(index%2==1){%>
                                                            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="2" class="px13" height="30">
                                                        <%}%>
                                                        <%if(index%2==0){%>
                                                            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="2" class="px13" height="30" bgcolor="#E8E8FF">
                                                        <%}%>
                                                            <tr>
                                                                <td colspan="10" width="100%" class="issuetitle_L_down">
                                                                    <c:if test="${(qryForm.qryCond eq 'ISSUYM')}">
                                                                                                                                                核定年月：<c:out value="${issu.issuYmStr}" /> 
                                                                    </c:if>                       
                                                                    <c:if test="${(qryForm.qryCond eq 'PAYYM')}">
                                                                                                                                                給付年月：<c:out value="${issu.payYmStr}" /> 
                                                                    </c:if>  
                                                                </td>
                                                            </tr>
                                                            <logic:empty name="issu" property="issuDataList">
                                                                <tr>
                                                                    <td colspan="10" align="center" colspan="10">
                                                                        <font color="red">無資料</font>
                                                                    </td>
                                                                </tr>                             
                                                            </logic:empty>
                                                            <logic:notEmpty name="issu" property="issuDataList">
                                                                <logic:iterate id="subIssu" indexId="subIssuIndex" name="issu" property="issuDataList">
                                                                    <logic:notEmpty name="subIssu">
                                                                        <c:if test="${subIssuIndex eq '0'}">
                                                                            <tr align="center">
                                                                                <c:if test="${(qryForm.qryCond eq 'ISSUYM')}">
                                                                                    <td width="10%" class="issuetitle_L">給付年月</td>
                                                                                </c:if>
                                                                                <c:if test="${(qryForm.qryCond eq 'PAYYM')}">
                                                                                    <td width="10%" class="issuetitle_L">核定年月</td>
                                                                                </c:if>
                                                                                <td width="10%" class="issuetitle_L">國保金額</td>
                                                                                <td width="10%" class="issuetitle_L">在途保費</td>
                                                                                <td width="10%" class="issuetitle_L">利&nbsp;息</td>
                                                                                <td width="10%" class="issuetitle_L">應&nbsp;收</td>
                                                                                <td width="10%" class="issuetitle_L">補&nbsp;發</td>
                                                                                <td width="10%" class="issuetitle_L">減&nbsp;領</td>
                                                                                <td width="10%" class="issuetitle_L">另案扣減</td>
                                                                                <td width="10%" class="issuetitle_L">實付金額</td>
                                                                                <td width="10%" class="issuetitle_L"><span class="needtxt">審核結果</span></td>
                                                                            </tr>
                                                                        </c:if>
                                                                            <tr>
                                                                                <c:if test="${(qryForm.qryCond eq 'ISSUYM')}">
                                                                                    <td width="10%" class="issueinfo_C">
                                                                                        <c:out value="${subIssu.payYmStr}" />
                                                                                    </td>
                                                                                </c:if>
                                                                                <c:if test="${(qryForm.qryCond eq 'PAYYM')}">
                                                                                    <td width="10%" class="issueinfo_C">
                                                                                        <c:out value="${subIssu.issuYmStr}" />
                                                                                    </td>
                                                                                </c:if>
                                                                                
                                                                                <td width="10%" class="issueinfo_R">
                                                                                    <fmt:formatNumber value="${subIssu.issueAmt}" />
                                                                                </td>
                                                                                <td width="10%" class="issueinfo_R">
                                                                                    <fmt:formatNumber value="${subIssu.sagtotAmt}" />
                                                                                </td>
                                                                                <td width="10%" class="issueinfo_R">
                                                                                    <fmt:formatNumber value="${subIssu.itrtAmt}" />
                                                                                </td>
                                                                                <td width="10%" class="issueinfo_R">
                                                                                    <fmt:formatNumber value="${subIssu.recAmt}" />
                                                                                </td>
                                                                                <td width="10%" class="issueinfo_R">
                                                                                    <fmt:formatNumber value="${subIssu.supAmt}" />
                                                                                </td>
                                                                                <td width="10%" class="issueinfo_R">
                                                                                    <fmt:formatNumber value="${subIssu.cutAmt}" />
                                                                                </td>
                                                                                <td width="10%" class="issueinfo_R">
                                                                                    <fmt:formatNumber value="${subIssu.otherAmt}" />
                                                                                </td>
                                                                                <td width="10%" class="issueinfo_R">
                                                                                    <fmt:formatNumber value="${subIssu.aplPayAmt}" />
                                                                                </td>
                                                                                <td width="10%" class="issueinfo_C">
                                                                                    <span class="needtxt">
                                                                                        <c:out value="${subIssu.manChkFlg}" />
                                                                                    </span>
                                                                                </td>
                                                                            </tr>
                                                                    </logic:notEmpty>
                                                                </logic:iterate>
                                                            </logic:notEmpty>
                                                        </table>
                                                    </td>
                                                </tr>     
                                            </logic:iterate>
                                    </tbody>
                                <td>&nbsp;</td>    
                            </tr>
                        </logic:notEmpty>
                        </c:if>
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