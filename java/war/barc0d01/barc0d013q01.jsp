<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BARC0D013Q01" />
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
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/paymentDecision" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;審核清單明細資料&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="7">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackMaster'; document.PaymentDecisionForm.submit();" />&nbsp;
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="7">
                        <bean:define id="pay" name="<%=ConstantKey.PAYMENT_DECISION_CASE%>" />                        
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${pay.apNoStrDisplay}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${pay.appDateStr}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">版　次：</span>
                                    <c:out value="${pay.veriSeq}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${pay.evtName}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${pay.evtIdnNo}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${pay.evtBrDateStr}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">事故者年齡：</span>
                                    <c:out value="${pay.evtAge}" />&nbsp;歲
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者性別：</span>
                                    <c:if test='${(pay.evtSex eq "1")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_1%>" />
                                    </c:if>
                                    <c:if test='${(pay.evtSex eq "2")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_2%>" />
                                    </c:if>&nbsp;
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">首次給付年月：</span>
                                    <c:out value="${pay.payYmsStr}" />
                                    <c:if test="${(pay.payYmsStr ne '')or(pay.payYmeStr ne '')}">
                                        &nbsp;~&nbsp;
                                    </c:if>
                                    <c:out value="${pay.payYmeStr}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">核定年月：</span>
                                    <c:out value="${pay.issuYmStr}" />&nbsp;
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">給付年月起迄：</span>
                                    <c:out value="${pay.minPayYmStr}" />
                                    <c:if test="${(pay.minPayYmStr ne '')or(pay.maxPayYmStr ne '')}">
                                        &nbsp;~&nbsp;
                                    </c:if>
                                    <c:out value="${pay.maxPayYmStr}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">核定格式：</span>
                                    <c:out value="${pay.notifyForm}" />&nbsp;
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="7">
                        <table border="0" cellpadding="0" cellspacing="0" class="issuetitle_L" id="tabsJ">
                            <tr>
                                <td>
                                    <a href='<c:url value="/paymentDecision.do?method=reviewMasterData" />' title="連結至：總表資料" target="_self"><span>總表資料</span></a>
                                    <h5><a>請領同類／他類／另案扣減</a></h5>
                                    <a href='<c:url value="/paymentDecision.do?method=reviewBeneficiaryData" />' title="連結至：事故者/受款人資料" target="_self"><span>事故者／受款人資料</span></a>
                                    <a href='<c:url value="/paymentDecision.do?method=reviewIssuData" />' title="連結至：核定資料" target="_self"><span>核定資料</span></a>
                                    <a href='<c:url value="/paymentDecision.do?method=reviewSeniorityData" />' title="連結至：年資資料" target="_self"><span>年資資料</span></a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="7" align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                            <tr>
                                <td colspan="4" class="issuetitle_L">
                                    <span class="systemMsg">▲</span>&nbsp;請領同類給付資料
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" id="iss">
                                    [&nbsp;&nbsp;<span class="issuetitle_search">一次給付資料</span>&nbsp;&nbsp;]
                                </td>
                            </tr>
                            <logic:notEmpty name="<%=ConstantKey.PAYMENT_DECISION_ONCEPAY_DETAIL_DATA_CASE%>">
                            <bean:define id="oncePay" name="<%=ConstantKey.PAYMENT_DECISION_ONCEPAY_DETAIL_DATA_CASE%>" />
                                <tr>
                                    <td width="25%" id="iss">
                                        <span class="issuetitle_L_down">受理編號：</span>
                                        <c:out value="${oncePay.bmApNoStr}" />&nbsp;
                                    </td>
                                    <td width="25%" id="iss">
                                        <span class="issuetitle_L_down">受理日期：</span>
                                        <c:out value="${oncePay.bmApDteStr}" />&nbsp;
                                    </td>
                                    <td width="25%" id="iss">
                                        <span class="issuetitle_L_down">事故者姓名：</span>
                                        <c:out value="${oncePay.bmEvName}" />&nbsp;
                                    </td>
                                    <td width="25%" id="iss">
                                        <span class="issuetitle_L_down">事故日期：</span>
                                        <c:out value="${oncePay.bmEvtDteStr}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定金額：</span>
                                        <c:out value="${oncePay.bmChkAmt}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定日期：</span>
                                        <c:out value="${oncePay.bmExmDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核付日期：</span>
                                        <c:out value="${oncePay.bmPayDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">保險證號：</span>
                                        <c:out value="${oncePay.bmUbNo}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">補件日期/註記：</span>
                                        <c:out value="${oncePay.bmNrepDateStr}" />
                                        <c:if test="${(oncePay.bmNrepDateStr ne '')or(oncePay.bmNdocMk ne '')}">
                                        &nbsp;~&nbsp;
                                        </c:if>
                                        <c:out value="${oncePay.bmNdocMk}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">不給付日期：</span>
                                        <c:out value="${oncePay.bmNopDateStr}" />&nbsp;
                                    </td>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">補收金額：</span>
                                        <c:out value="${oncePay.bmAdjAmts}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4" id="iss"><span class="issuetitle_L_down">補償金－</span>
                                        <table cellpadding="2" cellspacing="2" width="100%" class="px13">
                                            <tr>
                                                <td width="25%">》事業主管機關：
                                                    &nbsp;
                                                </td>
                                                <td width="25%">》申請代算單位：
                                                    &nbsp;
                                                </td>
                                                <td width="25%">》法令依據：
                                                    &nbsp;
                                                </td>
                                                <td width="25%">》複核日期：
                                                    <c:out value="${oncePay.bmExmDteStr}" />&nbsp;
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </logic:notEmpty>
                            <tr>
                                <td colspan="4" id="iss">
                                    [&nbsp;&nbsp;<span class="issuetitle_search">年金給付資料</span>&nbsp;&nbsp;]
                                </td>
                            </tr>
                            <logic:iterate id="annuPay" name="<%=ConstantKey.PAYMENT_DECISION_ANNUITYPAY_DATA_CASE_LIST%>" >
                                <tr>
                                    <td id="iss" colspan="4">                                
                                        <span class="issuetitle_L_down">受理編號/核定年月/給付年月：</span>
                                        <c:out value="${annuPay.apNoStr}" />&nbsp;/&nbsp;
                                        <c:out value="${annuPay.issuYmStr}" />&nbsp;/&nbsp;
                                        <c:out value="${annuPay.payYmStr}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">申請日期：</span>
                                        <c:out value="${annuPay.appDateStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">事故者姓名：</span>
                                        <c:out value="${annuPay.evtName}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">事故日期：</span>
                                        <c:out value="${annuPay.evtDateStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">保險證號：</span>
                                        <c:out value="${annuPay.lsUbno}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定金額：</span>
                                        <c:out value="${annuPay.issuAmt}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定日期：</span>
                                        <c:out value="${annuPay.chkDateStr}" />&nbsp;
                                    </td>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">核付日期：</span>
                                        <c:out value="${annuPay.aplPayDateStr}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss_b">
                                        <span class="issuetitle_L_down">補收金額-已收金額：</span>
                                        <c:out value="${annuPay.recAmt}" />
                                        <c:if test="${(annuPay.recAmt ne null)or(annuPay.supAmt ne null)}">
                                        &nbsp;-&nbsp;
                                        </c:if>
                                        <c:out value="${annuPay.supAmt}" />&nbsp;
                                    </td>
                                    <td id="iss_b">
                                        <span class="issuetitle_L_down">補件日期/註記：</span>
                                        <c:out value="${annuPay.proDateStr}" />&nbsp;/&nbsp;<c:out value="${annuPay.ndomk1}" />
                                    </td>
                                    <td id="iss_b" colspan="2">
                                        <span class="issuetitle_L_down">不給付日期：</span>
                                        <c:out value="${annuPay.exeDateStr}" />&nbsp;
                                    </td>
                                </tr>
                            </logic:iterate>
                            <tr>
                                <td colspan="4" class="issuetitle_L"><br>
                                    <span class="systemMsg">▲</span>&nbsp;請領他類給付資料
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" id="iss">
                                    [&nbsp;&nbsp;<span class="issuetitle_search">申請失能給付紀錄</span>&nbsp;&nbsp;]
                                </td>
                            </tr>
                            <logic:iterate id="criPay" name="<%=ConstantKey.PAYMENT_DECISION_CRIPAY_APPLY_DATA_CASE_LIST%>" >
                                <tr>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">受理編號/給付年月：</span>
                                        <c:out value="${criPay.bmApNoStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">申請(受理)日期：</span>
                                        <c:out value="${criPay.bmApDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">事故者姓名：</span>
                                        <c:out value="${criPay.bmEvName}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定金額：</span>
                                        <c:out value="${criPay.bmChkAmt}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定日期：</span>
                                        <c:out value="${criPay.bmExmDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核付日期：</span>
                                        <c:out value="${criPay.bmPayDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">診斷失能日期：</span>
                                        <c:out value="${criPay.bmEvtDteStr}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss_b">
                                        <span class="issuetitle_L_down">補件日期/註記：</span>
                                        <c:out value="${criPay.bmNrepDateStr}" />&nbsp;
                                    </td>
                                    <td id="iss_b">
                                        <span class="issuetitle_L_down">不給付日期：</span>
                                        <c:out value="${criPay.bmNopDateStr}" />&nbsp;
                                    </td>
                                    <td id="iss_b">
                                        <span class="issuetitle_L_down">失能項目：</span>
                                        <c:out value="${criPay.bmCriInjCl}" />&nbsp;
                                    </td>
                                    <td id="iss_b">
                                        <span class="issuetitle_L_down">失能等級：</span>
                                        <c:out value="${criPay.bmCriInjDp}" />&nbsp;
                                    </td>
                                </tr>
                            </logic:iterate>
                            <tr>
                                <td colspan="4" id="iss">
                                    [&nbsp;&nbsp;<span class="issuetitle_search">申請死亡給付紀錄</span>&nbsp;&nbsp;]
                                </td>
                            </tr>
                            <logic:notEmpty name="<%=ConstantKey.PAYMENT_DECISION_DIEPAY_APPLY_DATA_CASE%>">
                            <bean:define id="diePay" name="<%=ConstantKey.PAYMENT_DECISION_DIEPAY_APPLY_DATA_CASE%>" />
                                <tr>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">受理編號/給付年月：</span>
                                        <c:out value="${diePay.bmApNoStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">申請日期：</span>
                                        <c:out value="${diePay.bmApDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">事故者姓名：</span>
                                        <c:out value="${diePay.bmEvName}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定金額：</span>
                                        <c:out value="${diePay.bmChkAmt}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定日期：</span>
                                        <c:out value="${diePay.bmExmDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核付日期：</span>
                                        <c:out value="${diePay.bmPayDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">死亡日期：</span>
                                        <c:out value="${diePay.bmEvtDteStr}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">補件日期/註記：</span>
                                        <c:out value="${diePay.bmNrepDateStr}" />
                                        <c:if test="${(diePay.bmNrepDateStr ne '')or(diePay.bmNdocMk ne '')}">
                                        &nbsp;/&nbsp;
                                        </c:if>
                                        <c:out value="${diePay.bmNdocMk}" />&nbsp;
                                    </td>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">不給付日期：</span>
                                        <c:out value="${diePay.bmNopDateStr}" />&nbsp;
                                    </td>
                                </tr>
                            </logic:notEmpty>
                            <tr>
                                <td colspan="4" id="iss">
                                    [&nbsp;&nbsp;<span class="issuetitle_search">申請傷病給付紀錄</span>&nbsp;&nbsp;]
                                </td>
                            </tr>
                            <logic:iterate id="injPay" name="<%=ConstantKey.PAYMENT_DECISION_INJPAY_APPLY_DATA_CASE_LIST%>" >
                                <tr>
                                    <td id="iss" colspan="4">
                                        <span class="issuetitle_L_down">受理編號/給付年月：</span>
                                        <c:out value="${injPay.bmApNoStr}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">申請日期：</span>
                                        <c:out value="${injPay.bmApDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">事故者姓名：</span>
                                        <c:out value="${injPay.bmEvName}" />&nbsp;
                                    </td>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">事故日期：</span>
                                        <c:out value="${injPay.bmEvtDteStr}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定起迄日：</span>
                                        <c:out value="${injPay.bmLosFmDteStr}" />
                                        <c:if test="${(injPay.bmLosFmDteStr ne '')or(injPay.bmLosToDteStr ne '')}">
                                        &nbsp;~&nbsp;
                                        </c:if>
                                        <c:out value="${injPay.bmLosToDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定金額：</span>
                                        <c:out value="${injPay.bmChkAmt}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定日期：</span>
                                        <c:out value="${injPay.bmExmDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核付日期：</span>
                                        <c:out value="${injPay.bmPayDteStr}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss_b" colspan="2">
                                        <span class="issuetitle_L_down">補件日期/註記：</span>
                                        <c:out value="${injPay.bmNrepDateStr}" />
                                        <c:if test="${(injPay.bmNrepDateStr ne '')or(injPay.bmNdocMk ne '')}">
                                        &nbsp;/&nbsp;
                                        </c:if>
                                        <c:out value="${injPay.bmNdocMk}" />&nbsp;
                                    </td>
                                    <td id="iss_b" colspan="2">
                                        <span class="issuetitle_L_down">不給付日期：</span>
                                        <c:out value="${injPay.bmNopDateStr}" />&nbsp;
                                    </td>
                                </tr>
                            </logic:iterate>
                            <tr>
                                <td colspan="4" id="iss">
                                    [&nbsp;&nbsp;<span class="issuetitle_search">申請失業給付紀錄</span>&nbsp;&nbsp;]
                                </td>
                            </tr>
                            <logic:iterate id="unEmpPay" name="<%=ConstantKey.PAYMENT_DECISION_UNEMPPAY_APPLY_DATA_CASE_LIST%>" >
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">受理編號：</span>
                                        <c:out value="${unEmpPay.apNoStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">申請日期：</span>
                                        <c:out value="${unEmpPay.apDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">事故者姓名：</span>
                                        <c:out value="${unEmpPay.name}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">求職日期：</span>
                                        <c:out value="${unEmpPay.tdteStr}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">給付起迄日：</span>
                                        <c:out value="${unEmpPay.symDteStr}" />
                                        <c:if test="${(unEmpPay.symDteStr ne '')or(unEmpPay.tymDteStr ne '')}">
                                        &nbsp;~&nbsp;
                                        </c:if>
                                        <c:out value="${unEmpPay.tymDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定金額：</span>
                                        <c:out value="${unEmpPay.chkAmt}" />&nbsp;
                                        </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核定日期：</span>
                                        <c:out value="${unEmpPay.chkDteStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">核付日期：</span>
                                        <c:out value="${unEmpPay.payDteStr}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss_b" colspan="2">
                                        <span class="issuetitle_L_down">補件日期/註記：</span>
                                    </td>
                                    <td id="iss_b" colspan="2">
                                        <span class="issuetitle_L_down">不給付日期：</span>
                                        <c:out value="${unEmpPay.npyDteStr}" />&nbsp;
                                    </td>
                                </tr>
                            </logic:iterate>
                            <tr>
                                <td colspan="4" id="iss">
                                    [&nbsp;&nbsp;<span class="issuetitle_search">申請國保給付紀錄</span>&nbsp;&nbsp;]
                                </td>
                            </tr>
                            <logic:notEmpty name="<%=ConstantKey.PAYMENT_DECISION_NPPAY_APPLY_DATA_CASE%>">
                            <bean:define id="npPay" name="<%=ConstantKey.PAYMENT_DECISION_NPPAY_APPLY_DATA_CASE%>" />
                                <tr>
                                    <td id="iss" colspan="4">
                                        <span class="issuetitle_L_down">受理編號/給付年月：</span>
                                        <c:out value="${npPay.apNoStr}" />
                                        <c:if test="${(npPay.apNoStr ne '')or(npPay.payYmStr ne '')}">
                                        &nbsp;/&nbsp;
                                        </c:if>
                                        <c:out value="${npPay.payYmStr}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">申請日期：</span>
                                        <c:out value="${npPay.appDateStr}" />&nbsp;
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">被保險人姓名：</span>
                                        <c:out value="${npPay.evteeName}" />&nbsp;
                                    </td>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">事故日期：</span>
                                        <c:out value="${npPay.evtDtStr}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss_b">
                                        <span class="issuetitle_L_down">核定金額：</span>
                                        <c:out value="${npPay.issueAmt}" />&nbsp;
                                    </td>
                                    <td id="iss_b">
                                        <span class="issuetitle_L_down">核定日期：</span>
                                        <c:out value="${npPay.chkDtStr}" />&nbsp;
                                    </td>
                                    <td id="iss_b">
                                        <span class="issuetitle_L_down">核付日期：</span>
                                        <c:out value="${npPay.aplPayDateStr}" />&nbsp;
                                    </td>
                                    <td id="iss_b">
                                        <span class="issuetitle_L_down">人工審核結果：</span>
                                        <c:out value="${npPay.manChkFlg}" />&nbsp;
                                    </td>
                                </tr>
                            </logic:notEmpty>
                            <tr>
                                <td colspan="4" class="issuetitle_L"><br>
                                    <span class="systemMsg">▲</span>&nbsp;另案扣減資料
                                </td>
                            </tr>
                            <%--
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">序號：</span>&nbsp;
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">受理編號/給付年月：</span>&nbsp;
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">立帳對象姓名：</span>&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td id="iss_b">
                                    <span class="issuetitle_L_down">應收總額：</span>&nbsp;
                                </td>
                                <td id="iss_b">
                                    <span class="issuetitle_L_down">未收餘額：</span>&nbsp;
                                </td>
                                <td id="iss_b">
                                    <span class="issuetitle_L_down">本次收回金額：</span>&nbsp;
                                </td>
                                <td id="iss_b">
                                    <span class="issuetitle_L_down">確定：</span>&nbsp;
                                </td>
                            </tr>
                            --%>
                        </table>
                    </td>
                </tr>
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
