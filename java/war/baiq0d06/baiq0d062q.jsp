<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D062Q" />
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
    function flashIssuData(optionTye) {
        var apNo = '<c:out value="${PaymentQueryDetailDataCase.apNo}" />'
        var qryCond = '<c:out value="${PaymentQueryCondForm.qryCond}" />';
        var optionYm = 'ALL';
        
        if(optionTye == "YM"){
            $("seqNoOption").value = "ALL";
        }else if(optionTye == "SEQNO"){
            if(qryCond == 'ISSUYM'){
                $("issuYmOption").value = "ALL";
            }
            if(qryCond == 'PAYYM'){
                $("payYmOption").value = "ALL";
            }
        }
        
        var seqNo = $("seqNoOption").value;
        
        if(qryCond=='ISSUYM'){
            optionYm = $("issuYmOption").value;
        }
        if(qryCond=='PAYYM'){
            optionYm = $("payYmOption").value;
        }
        queryCommonAjaxDo.flashIssuDataList(apNo, optionYm, seqNo, qryCond, setIssuData);
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
                var issuPayDataArray = issuData.issuPayDataArray;
                innerHTMLStr += '<tr align="center"><td colspan="9">';
                if(i%2 == 0){
                    innerHTMLStr += '<table id="tb" width="100%" cellpadding="0" cellspacing="2" class="px13">';
                }else{
                    innerHTMLStr += '<table id="tb" width="100%" cellpadding="0" cellspacing="2" class="px13" bgcolor="#E8E8FF">';
                }
                
                for(var j=0; j<issuPayDataArray.length; j++){
                    var subIssuData = issuPayDataArray[j];
                    var issuPayExtDataArray = subIssuData.issuPayExtDataArray;
                    
                    innerHTMLStr += '<tr align="center">';
                    innerHTMLStr += '<td colspan="10" id="iss">';
                    innerHTMLStr += '<table width="100%" border="0" cellspacing="0" cellpadding="2" class="px13" height="30">';
                    innerHTMLStr += '<tr>';
                    
                    if(j==0){
                        if(qryCond == "ISSUYM"){
                            innerHTMLStr += '<td width="22%" class="issuetitle_L_down">核定年月：'+getDefaultString(subIssuData.issuYmStr)+'</td>';
                            innerHTMLStr += '<td width="22%" class="issuetitle_L_down">給付年月：'+getDefaultString(subIssuData.payYmStr)+'</td>';
                        }
                        if(qryCond == "PAYYM"){
                            innerHTMLStr += '<td width="22%" class="issuetitle_L_down">給付年月：'+getDefaultString(subIssuData.payYmStr)+'</td>';
                            innerHTMLStr += '<td width="22%" class="issuetitle_L_down">核定年月：'+getDefaultString(subIssuData.issuYmStr)+'</td>';
                        }
                        innerHTMLStr += '<td width="22%" class="issuetitle_L_down">核付日期：'+getDefaultString(subIssuData.aplpayDateStr)+'</td>';
                        innerHTMLStr += '<td width="22%" class="issuetitle_L_down">止付日期：'+getDefaultString(subIssuData.stexpndDateStr)+'</td>';
                    }else{
                        innerHTMLStr += '<td width="22%" class="issuetitle_L_down"> </td>';
                        if(qryCond == "ISSUYM"){
                            innerHTMLStr += '<td width="22%" class="issuetitle_L_down">給付年月：'+getDefaultString(subIssuData.payYmStr)+'</td>';
                        }
                        if(qryCond == "PAYYM"){
                            innerHTMLStr += '<td width="22%" class="issuetitle_L_down">核定年月：'+getDefaultString(subIssuData.issuYmStr)+'</td>';
                        }
                        innerHTMLStr += '<td width="22%" class="issuetitle_L_down"> </td>';
                        innerHTMLStr += '<td width="22%" class="issuetitle_L_down"> </td>';
                    }
                    innerHTMLStr += '<td width="12%" class="issuetitle_R_down"> </td>';
                    innerHTMLStr += '</tr>';
                    innerHTMLStr += '</table>';
                    
                    if(j==0){
                        innerHTMLStr += '<tr align="center">';
                        innerHTMLStr += '<td width="7%" class="issuetitle_L">受款人序</td>';
                        innerHTMLStr += '<td width="19%" class="issuetitle_L">受款人姓名</td>';
                        innerHTMLStr += '<td width="22%" class="issuetitle_L">關　係</td>';
                        innerHTMLStr += '<td width="7%" class="issuetitle_L">核定金額</td>';
                        innerHTMLStr += '<td width="7%" class="issuetitle_L">紓困金額</td>';
                        innerHTMLStr += '<td width="8%" class="issuetitle_L">扣減總金額</td>';
                        innerHTMLStr += '<td width="9%" class="issuetitle_L">補發/收回金額</td>';
                        innerHTMLStr += '<td width="7%" class="issuetitle_L">實付金額</td>';
                        innerHTMLStr += '<td width="8%" class="issuetitle_L">帳務日期</td>';
                        innerHTMLStr += '<td width="6%" class="issuetitle_L"><span class="needtxt">核定結果</span></td>';
                        innerHTMLStr += '</tr>';
                    }
                    
                    for(var k=0; k<issuPayExtDataArray.length; k++){
                        var issuPayExtData = issuPayExtDataArray[k];
                        innerHTMLStr += '<tr>';
                        //受款人序 
                        innerHTMLStr += '<td class="issueinfo_C" width="7%">'+(k+1)+'</td>';
                        //受款人姓名
                        innerHTMLStr += '<td class="issueinfo" width="19%">'+getDefaultString(issuPayExtData.benName)+'</td>';
                        //關係
                        innerHTMLStr += '<td class="issueinfo" width="22%">'+getDefaultString(issuPayExtData.benEvtRelName)+'</td>';
                        //核定金額
                        innerHTMLStr += '<td class="issueinfo_R" width="7%">'+getDefaultString(numFormat(issuPayExtData.issueAmt))+'</td>';
                        //紓困金額
                        innerHTMLStr += '<td class="issueinfo_R" width="7%">'+getDefaultString(numFormat(issuPayExtData.offsetAmt))+'</td>';
                        //扣減總金額
                        innerHTMLStr += '<td class="issueinfo_R" width="8%">'+getDefaultString(numFormat(issuPayExtData.otherAmt))+'</td>';
                        //補發/收回金額
                        innerHTMLStr += '<td class="issueinfo_R" width="9%">'+getDefaultString(numFormat(issuPayExtData.recAmt))+'</td>';
                        //實付金額
                        innerHTMLStr += '<td class="issueinfo_R" width="7%">'+getDefaultString(numFormat(issuPayExtData.aplpayAmt))+'</td>';
                        //帳務日期
                        innerHTMLStr += '<td class="issueinfo_C" width="8%">'+getDefaultString(issuPayExtData.remitDateStr)+'</td>';
                        //核定結果
                        innerHTMLStr += '<td class="issueinfo_C" width="6%"><span class="needtxt">'+getDefaultString(issuPayExtData.manchkMkStr)+'</span></td>';

                        innerHTMLStr += '</tr>';
                    }
                }
                innerHTMLStr +='</table>';
                innerHTMLStr += '</td></tr>';
            }
        }
        $("issuDataSpan").innerHTML = innerHTMLStr;
    }
    
    //字串處理
    //如果傳入值為null, 回傳" "；否則回傳原始傳入值
    //防止資料底線消失
    function getDefaultString(strValue){
        if(strValue=="" || strValue==" " || strValue==null){
            return "&nbsp;";
        }else{
            return strValue;
        }
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
            <html:form action="/paymentQuerySimplify" method="post" onsubmit="">
                <html:hidden styleId="page" property="page" value="1" />
                <html:hidden styleId="method" property="method" value="" />
                <html:hidden styleId="apNo" property="apNo" value="" />
                <html:hidden styleId="issuYm" property="issuYm" value="" />
                <html:hidden styleId="payYm" property="payYm" value="" />

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
                                <acl:checkButton buttonName="btnPrint">
                                    <input name="btnPrint" type="button" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReportSimplifyApplicationData'; document.PaymentQueryForm.submit();"/>&nbsp;
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
                            <td colspan="14">
                                
                                <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                    <tr>
                                        <td width="33%">
                                            <span class="issuetitle_L_down">受理編號：</span>
                                            <c:out value="${detail.apNoStrDisplay}" />
                                            <c:out value="${detail.sysCode}" />
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
                                            <c:if test="${not empty detail.rmp_Name}">
                                                <c:out value="(羅馬拼音：${detail.rmp_Name})"/>
                                            </c:if>
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
                                            <h5><a>案件資料</a></h5>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeBenData" />' title="連結至：事故者/受款人" target="_self" class="needtxt"><span>事故者／受款人</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeAvgAmtData" />' title="連結至：平均薪資" target="_self" class="needtxt"><span>平均薪資</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeLoanData" />' title="連結至：紓困貸款" target="_self" class="needtxt"><span>紓困貸款</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeCpiData" />' title="連結至：物價指數調整" target="_self" class="needtxt"><span>物價指數調整</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeCpiRecData" />' title="連結至：物價指數調整記錄" target="_self" class="needtxt"><span>物價指數調整記錄</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeArclistData" />' title="連結至：歸檔記錄" target="_self" class="needtxt"><span>歸檔記錄</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeSimplifyUpdateLogQueryData" />' title="連結至：更正日誌" target="_self" class="needtxt"><span>更正日誌</span></a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>                
                            <td colspan="14" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
                                        <td colspan="4" class="issuetitle_L">
                                            <span class="systemMsg">▲</span>&nbsp;案件資料
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">申請項目：</span>
                                            <c:out value="${detail.apItemStr}" />&nbsp;
                                        </td> 
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">核定格式：</span>
                                            <c:out value="${detail.notifyForm}" />&nbsp;
                                        </td>
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">承辦人員：</span>
                                            <c:out value="${detail.promoteUser}" />&nbsp;
                                        </td>
                                        <td width="25%" id="iss">
                                            <span class="issuetitle_L_down">屆齡日期：</span>
                                            <c:out value="${detail.evtExpireDateStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">申請單位：</span>
                                            <c:out value="${detail.apUbno}" />&nbsp;
                                        </td>                    
                                        <td id="iss">
                                            <span class="issuetitle_L_down">最後單位：</span>
                                            <c:out value="${detail.lsUbno}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">退保日期：</span>
                                            <c:out value="${detail.evtDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">符合日期：</span>
                                            <c:out value="${detail.evtEligibleDateStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">國勞合併申請：</span>
                                            <c:out value="${detail.combapMark}" />&nbsp;
                                        </td>                    
                                        <td id="iss">
                                            <span class="issuetitle_L_down">相關受理編號：</span>
                                            <c:out value="${detail.mapNoStrDisplay}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">相關受理編號註記：</span>
                                            <c:out value="${detail.mapRootMk}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">發放方式：</span>
                                            <c:if test="${(detail.interValMonth eq '0') or (detail.interValMonth eq '1') or (detail.interValMonth eq '')}">
                                                           按月發放
                                            </c:if>
                                            <c:if test="${!((detail.interValMonth eq '0') or (detail.interValMonth eq '1') or (detail.interValMonth eq ''))}">
                                                           按&nbsp;<c:out value="${detail.interValMonth}" />&nbsp;個月發放
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">已領失能年金受理編號：</span>
                                            <c:out value="${detail.dabApNoStrDisplay}" />&nbsp;
                                        </td>                   
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">已領失能年金金額：</span>
                                            <fmt:formatNumber value="${detail.dabAnnuAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">首次給付年月／金額：</span>
                                            <c:out value="${detail.payYmsStr}" />
                                            <c:if test='${(detail.payYmsStr ne "")or(detail.payYmeStr ne "")}'>
                                                &nbsp;~&nbsp;
                                            </c:if>
                                            <c:out value="${detail.payYmeStr}" />
                                            &nbsp;／&nbsp;
                                            <fmt:formatNumber value="${detail.payAmts}" />
                                        </td>                   
                                        <td id="iss" colspan="1">
                                            <span class="issuetitle_L_down">累計已領年金金額：</span>
                                            <fmt:formatNumber value="${detail.annuAmt}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="1">
                                            <span class="issuetitle_L_down">試算老年差額金：</span>
                                            <fmt:formatNumber value="${detail.marginAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="1">
                                            <span class="issuetitle_L_down">更正註記：</span>
                                            <c:out value="${detail.chgMk}" />&nbsp;
                                        </td>                   
                                        <td id="iss" colspan="3">
                                            <span class="issuetitle_L_down">受理鍵入資料及修改紀錄：</span>
                                            (鍵入/更正人員代號：
                                            <c:if test="${detail.crtUser ne ''}">
                                                <c:out value="*****" />
                                            </c:if>
                                            <c:if test="${(detail.crtUser ne '')or(detail.updUser ne '')}">
                                            &nbsp;/&nbsp;
                                            </c:if>
                                            <c:if test="${detail.updUser ne ''}">
                                                <c:out value="*****" />
                                            </c:if>)
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
                                        <td colspan="4">
                                            <table width="100%" cellpadding="0" cellspacing="0" class="px13">
                                                <td width="100%" class="issuetitle_L">
                                                    <span class="systemMsg">▲</span>&nbsp;最新核定資料
                                                </td>
                                            </table>
                                        </td>    
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">核定年月：</span>
                                            <c:out value="${detail.issuYmStr}" />&nbsp;
                                        </td>                   
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">給付年月起迄：</span>
                                            <c:out value="${detail.minPayYmStr}" />
                                            <c:if test="${(detail.maxPayYmStr ne '')or(detail.minPayYmStr ne '')}">
                                            &nbsp;~&nbsp;
                                            </c:if>
                                            <c:out value="${detail.maxPayYmStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">核定總額：</span>
                                            <fmt:formatNumber value="${detail.befIssueAmt}" />&nbsp;
                                        </td>                   
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">實付總額：</span>
                                            <fmt:formatNumber value="${detail.aplPayAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
                                            <span class="issuetitle_L_down">平均薪資：</span>
                                            <fmt:formatNumber value="${detail.insAvgAmt}" />&nbsp;
                                        </td>                   
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">計算式／金額：</span>
                                            <c:out value="${detail.oldAbStr}" />&nbsp;／&nbsp;<fmt:formatNumber value="${detail.oldAmt}" />
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">
                                                <c:if test='${(detail.apItem eq "2")}'>
                                                    <c:out value="減額期間／比率：" />
                                                </c:if>
                                                <c:if test='${(detail.apItem ne "2")}'>
                                                    <c:out value="展延期間／比率：" />
                                                </c:if>
                                            </span>
                                            <c:out value="${detail.oldRateDateYMStr}" />
                                            <c:if test="${(detail.oldRateDateYMStr ne '')or(detail.oldRate ne null)}">
                                                &nbsp;／&nbsp;
                                            </c:if>
                                            <fmt:formatNumber value="${detail.oldRate}" />
                                            <c:if test="${detail.oldRate ne null}">
                                                %
                                            </c:if>&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">投保年資：</span>
                                            <c:out value="${detail.nitrmY}" />年
                                            <c:out value="${detail.nitrmM}" />月
                                            (<c:out value="${detail.itrmY}" />年
                                             <c:out value="${detail.itrmD}" />日)
                                        </td>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">實付年資：</span>
                                            <c:out value="${detail.aplPaySeniY}" />年
                                            <c:out value="${detail.aplPaySeniM}" />月
                                        </td>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">老年年資：</span>
                                            <c:out value="${detail.noldtY}" />年
                                            <c:out value="${detail.noldtM}" />月
                                        </td>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">國保年資：</span>
                                            <c:out value="${detail.valSeniY}" />年
                                            <c:out value="${detail.valSeniM}" />月
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">審核人員／日期：</span>
                                            <c:if test="${detail.chkMan ne ''}">
                                                <c:out value="*****" />
                                            </c:if>
                                            <c:if test="${(detail.chkMan ne '')or(detail.chkDateStr ne '')}">
                                                &nbsp;／&nbsp;
                                            </c:if>
                                            <c:out value="${detail.chkDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss">
                                            <span class="issuetitle_L_down">複核人員／日期：</span>
                                            <c:if test="${detail.rechkMan ne ''}">
                                                <c:out value="*****" />
                                            </c:if>
                                            <c:if test="${(detail.rechkMan ne '')or(detail.rechkDateStr ne '')}">
                                                &nbsp;／&nbsp;
                                            </c:if>
                                            <c:out value="${detail.rechkDateStr}" />&nbsp;                                        
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">決行人員／日期：</span>
                                            <c:if test="${detail.exeMan ne ''}">
                                                <c:out value="*****" />
                                            </c:if>
                                            <c:if test="${(detail.exeMan ne '')or(detail.exeDateStr ne '')}">
                                                &nbsp;／&nbsp;
                                            </c:if>
                                            <c:out value="${detail.exeDateStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
                                            <span class="issuetitle_L_down">更正原因：</span>
                                            <c:out value="${detail.chgNote}" />&nbsp;
                                        </td> 
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                                                <tr>
                                                    <td width="13%" valign="top">
                                                        <span class="issuetitle_L_down">事故者編審註記：</span>
                                                    </td>
                                                    <td width="62%">
                                                        <logic:iterate id="chkFileData" name="<%=ConstantKey.PAYMENT_QUERY_EVT_CHKFILE_DATA_CASE %>">                                                    
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
                                                    </td>
                                                    <td width="25%" valign="top" >
                                                        <c:if test="${detail.loanMk eq '1'}">
                                                            <strong><c:out value="<%=ConstantKey.BAAPPBASE_LOANMK_STR_1 %>" /></strong>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
									    <td colspan="4">
									       <table width="100%" cellpadding="0" cellspacing="0" class="px13">
                                                <td width="88%" class="issuetitle_L">
                                                    <span class="systemMsg">▲</span>&nbsp;行政支援記錄
                                                </td>
									            <td width="12%" valign="bottom" class="issuetitle_L" id="tabsJ">
									                <a href="<c:url value="/executiveSupportQueryQuery.do?method=doQuery&qryFromWhere=BAIQ0D062Q&apNo=${detail.apNo}&issuYm=${detail.issuYmStr}" />" target="_self">
									                	<span>行政支援查詢</span>
									                </a>
									            </td>
									        </table>
									    </td>
									</tr>
                                    <bean:define id="letterType1List" name="<%=ConstantKey.PAYMENT_QUERY_LETTER_TYPE_1_LIST %>" />
                                    <bean:define id="letterType2List" name="<%=ConstantKey.PAYMENT_QUERY_LETTER_TYPE_2_LIST %>" />
                                    <bean:define id="letterType3List" name="<%=ConstantKey.PAYMENT_QUERY_LETTER_TYPE_3_LIST %>" />
                                    <bean:define id="letterType4List" name="<%=ConstantKey.PAYMENT_QUERY_LETTER_TYPE_4_LIST %>" />
                                    <bean:define id="letterType5List" name="<%=ConstantKey.PAYMENT_QUERY_LETTER_TYPE_5_LIST %>" />
                                    <bean:define id="letterType6List" name="<%=ConstantKey.PAYMENT_QUERY_LETTER_TYPE_6_LIST %>" />
                                    <tr>
                                        <td id="iss" colspan="2" width="50%">
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
                                        <td id="iss" colspan="2" width="50%">
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
                                        <td id="iss" colspan="2" width="50%">
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
                                        <td id="iss" colspan="2" width="50%">
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
                                        <td id="iss" colspan="4">
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
                                        <td id="iss" colspan="1" width="25%">
                                            <span class="issuetitle_L_down">行政救濟日期：</span>
                                            <c:out value="${letterType6List.proDateStr}"/>
                                        </td>
                                        <td id="iss" colspan="1" width="25%">
                                            <span class="issuetitle_L_down">救濟種類：</span>
                                            <c:out value="${letterType6List.reliefKind}"/>
                                        </td>
                                        <td id="iss" colspan="2" width="50%">
                                            <span class="issuetitle_L_down">行政救濟辦理情形：</span>
                                            <c:out value="${letterType6List.reliefStat}"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
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
                        <tr>
                            <td colspan="14" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
                                      <td colspan="4" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;結案資料</td>
                                    </tr>
                                    <tr>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">結案日期：</span>
                                            <c:out value="${detail.closeDateStr}" />&nbsp;
                                        </td>                    
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">失蹤日期：</span>
                                            <c:out value="${detail.evtMissingDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">死亡日期：</span>
                                            <c:out value="${detail.evtDieDateStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
                                            <span class="issuetitle_L_down">結案原因：</span>
                                            <c:out value="${detail.closeCause}" />&nbsp;
                                        </td>
                                    </tr>
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
                        <bean:define id="issuPayDataList" name="<%=ConstantKey.PAYMENT_QUERY_ISSU_PAY_DATA_CASE%>" />
                        <logic:empty name="<%=ConstantKey.PAYMENT_QUERY_ISSU_PAY_DATA_CASE%>" >
                            <tr>
                                <td align="center" colspan="14">
                                    <font color="red">無資料</font>
                                </td>
                            </tr>                                
                        </logic:empty>
                        <logic:notEmpty name="issuPayDataList">
                            <tr>
                                <td colspan="14" class="issuetitle_L">
                                            篩選條件：
                                   <c:if test="${(qryForm.qryCond eq 'ISSUYM')}">
                                                核定年月&nbsp;-&nbsp;
                                        <html:select tabindex="70" property="issuYmOption" styleClass="formtxt" onchange="flashIssuData('YM');">
                                            <html:option value="ALL">全 選</html:option>
                                            <html:options collection="<%=ConstantKey.PAYMENT_QUERY_ISSUYM_OPTION_LIST%>" property="issuYm" labelProperty="issuYmStr" />
                                        </html:select>
                                    </c:if>
                                    <c:if test="${(qryForm.qryCond eq 'PAYYM')}">
                                                給付年月&nbsp;-&nbsp;
                                        <html:select tabindex="70" property="payYmOption" styleClass="formtxt" onchange="flashIssuData('YM');">
                                            <html:option value="ALL">全 選</html:option>
                                            <html:options collection="<%=ConstantKey.PAYMENT_QUERY_PAYYM_OPTION_LIST%>" property="payYm" labelProperty="payYmStr" />
                                        </html:select>
                                    </c:if>
                                    &nbsp;&nbsp;受款人&nbsp;-&nbsp;
                                    <html:select tabindex="70" property="seqNoOption" styleClass="formtxt" onchange="flashIssuData('SEQNO');">
                                        <html:option value="ALL">全 選</html:option>
                                        <html:options collection="<%=ConstantKey.PAYMENT_QUERY_SEQNO_OPTION_LIST%>" property="seqNo" labelProperty="benName" />
                                    </html:select>
                                </td>
                            </tr>
                        </logic:notEmpty>
                        <tr align="center">
                            <td colspan="14">
                                <span id="issuDataSpan"></span>
                                <tbody id="issuDataTable" align="center" width="100%" cellpadding="0" cellspacing="2" class="px13">
                                    <logic:notEmpty name="issuPayDataList">
                                        <%Integer index=0; %>
                                        <logic:iterate id="issu" indexId="issuIndex" name="issuPayDataList" >
                                            <%--判斷資料底色用 --%>
                                            <%index += 1; %>
                                            <tr align="center">
                                                <td colspan="9">
                                                    <%if(index%2==1){%>
                                                        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="2" class="px13">
                                                    <%}%>
                                                    <%if(index%2==0){%>
                                                        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="2" class="px13" bgcolor="#E8E8FF">
                                                    <%}%>
                                                        <logic:notEmpty name="issu" property="issuPayDataList">
                                                            <logic:iterate id="subIssu" indexId="subIssuIndex" name="issu" property="issuPayDataList">
                                                                <tr align="center">
                                                                    <td colspan="10" id="iss">
                                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="px13" height="30">
                                                                            <tr>
                                                                                <td width="22%" class="issuetitle_L_down">
                                                                                    <c:if test="${subIssuIndex eq '0'}">
                                                                                        <c:if test="${(qryForm.qryCond eq 'ISSUYM')}">
                                                                                                             核定年月：<c:out value="${subIssu.issuYmStr}" /> 
                                                                                        </c:if>                       
                                                                                        <c:if test="${(qryForm.qryCond eq 'PAYYM')}">
                                                                                                             給付年月：<c:out value="${subIssu.payYmStr}" /> 
                                                                                        </c:if>  
                                                                                    </c:if>
                                                                                    <c:if test="${subIssuIndex ne '0'}">           
                                                                                        &nbsp; 
                                                                                    </c:if>           
                                                                                </td>
                                                                                <td width="22%" class="issuetitle_L_down">
                                                                                    <c:if test="${(qryForm.qryCond eq 'ISSUYM')}">
                                                                                                         給付年月：<c:out value="${subIssu.payYmStr}" /> 
                                                                                    </c:if>                       
                                                                                    <c:if test="${(qryForm.qryCond eq 'PAYYM')}">
                                                                                                         核定年月：<c:out value="${subIssu.issuYmStr}" /> 
                                                                                    </c:if>&nbsp; 
                                                                                </td>
                                                                                <td width="22%" class="issuetitle_L_down">
                                                                                    <c:if test="${subIssuIndex eq '0'}">
                                                                                                         核付日期：<c:out value="${subIssu.aplpayDateStr}" />
                                                                                    </c:if>
                                                                                    <c:if test="${subIssuIndex ne '0'}">
                                                                                        &nbsp; 
                                                                                    </c:if>        
                                                                                </td>
                                                                                <td width="22%" class="issuetitle_L_down">
                                                                                    <c:if test="${subIssuIndex eq '0'}">
                                                                                                         止付日期：<c:out value="${subIssu.stexpndDateStr}" />
                                                                                    </c:if>
                                                                                    <c:if test="${subIssuIndex ne '0'}">
                                                                                        &nbsp; 
                                                                                    </c:if>
                                                                                </td>
                                                                                <td width="12%" class="issuetitle_R_down">
                                                                                    &nbsp;
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                <logic:empty name="subIssu" property="issuPayExtDataList">
                                                                    <tr>
                                                                        <td align="center" colspan="10">
                                                                            <font color="red">無資料</font>
                                                                        </td>
                                                                    </tr>                             
                                                                </logic:empty>
                                                                <logic:notEmpty name="subIssu" property="issuPayExtDataList">
                                                                    <c:if test="${subIssuIndex eq '0'}">
                                                                        <tr align="center">
                                                                            <td width="7%" class="issuetitle_L">受款人序</td>
                                                                            <td width="19%" class="issuetitle_L">受款人姓名</td>
                                                                            <td width="22%" class="issuetitle_L">關　係</td>
                                                                            <td width="7%" class="issuetitle_L">核定金額</td>
                                                                            <td width="7%" class="issuetitle_L">紓困金額</td>
                                                                            <td width="8%" class="issuetitle_L">扣減總金額</td>
                                                                            <td width="9%" class="issuetitle_L">補發/收回金額</td>
                                                                            <td width="7%" class="issuetitle_L">實付金額</td>
                                                                            <td width="8%" class="issuetitle_L">帳務日期</td>
                                                                            <td width="6%" class="issuetitle_L"><span class="needtxt">核定結果</span></td>
                                                                        </tr>
                                                                    </c:if>
                                                                    <logic:iterate id="issuExt" indexId="issuExtIndex" name="subIssu" property="issuPayExtDataList">
                                                                        <tr>
                                                                            <td width="7%" class="issueinfo_C">
                                                                                <%=issuExtIndex.intValue()+1%>
                                                                            </td>
                                                                            <td width="19%"class="issueinfo">
                                                                                <c:out value="${issuExt.benName}" />&nbsp;
                                                                            </td>
                                                                            <td width="22%"class="issueinfo">
                                                                                <c:out value="${issuExt.benEvtRelName}" />&nbsp;
                                                                            </td>
                                                                            <td width="7%" class="issueinfo_R">
                                                                                <fmt:formatNumber value="${issuExt.issueAmt}" />&nbsp;
                                                                            </td>
                                                                            <td width="7%" class="issueinfo_R">
                                                                                <fmt:formatNumber value="${issuExt.offsetAmt}" />&nbsp;
                                                                            </td>
                                                                            <td width="8%" class="issueinfo_R">
                                                                                <fmt:formatNumber value="${issuExt.otherAmt}" />&nbsp;
                                                                            </td>
                                                                            <td width="9%" class="issueinfo_R">
                                                                                <fmt:formatNumber value="${issuExt.recAmt}" />&nbsp;
                                                                            </td>
                                                                            <td width="7%" class="issueinfo_R">
                                                                                <fmt:formatNumber value="${issuExt.aplpayAmt}" />&nbsp;
                                                                            </td>
                                                                            <td width="8%" class="issueinfo_C">
                                                                                <c:out value="${issuExt.remitDateStr}" />&nbsp;
                                                                            </td>
                                                                            <td width="6%" class="issueinfo_C">
                                                                                <span class="needtxt">
                                                                                    <c:out value="${issuExt.manchkMkStr}" />
                                                                                </span>&nbsp;
                                                                            </td>
                                                                        </tr>
                                                                    </logic:iterate>
                                                                </logic:notEmpty>
                                                            </logic:iterate>
                                                        </logic:notEmpty>
                                                    </table>
                                                </td>
                                            </tr>     
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </tbody>
                            </td>    
                        </tr>
                        <tr>
                            <td colspan="14">&nbsp;</td>
                        </tr>
                    </table>
                </fieldset>
            </html:form>
        </div>
    </div>

    <%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>