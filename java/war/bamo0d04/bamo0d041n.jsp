<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D041N" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="StopPaymentProcessDetailForm" page="1" />
    <script language="javascript" type="text/javascript">
    <!-- 
    <%-- begin 檢查必填欄位 --%>
    function checkFields(){
        var msg = "";
        
        if (Trim($F("stexpndReason")) == "")
            msg += '「止付原因」：為必輸欄位。\r\n';
        
        if (msg != "") {
            alert(msg);
            return false;
        }
        else {
            return true;
        }
    }
    <%-- ] ... end --%>
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        document.StopPaymentProcessDetailForm.reset();
        initAll();       
    }
    <%-- ] ... end --%>
    
    function initAll() {
        dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");      
    }

    Event.observe(window, 'load', initAll , false);
    
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    -->
    </script>
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/stopPaymentProcessDetail" method="post" onsubmit="return validateStopPaymentProcessDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;止付處理&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="8">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnUpdate">
                            <c:if test='${empty StopPaymentProcessQueryCase.stexpndMk and StopPaymentProcessQueryCase.btnUpdate eq "enable"}'>
                                <input name="btnUpdate" type="button" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doUpdate'; if (document.StopPaymentProcessDetailForm.onsubmit() && checkFields()){document.StopPaymentProcessDetailForm.submit();}else{return false;}" />&nbsp;
                            </c:if>
                            <c:if test='${not empty StopPaymentProcessQueryCase.stexpndMk and empty StopPaymentProcessQueryCase.btnUpdate}'>
                                <input name="btnUpdate" type="button" class="button" value="確　認" disabled="disabled" />&nbsp;
                            </c:if>
                        </acl:checkButton>
			            <acl:checkButton buttonName="btnPrint">
			                <c:if test='${StopPaymentProcessQueryCase.stexpndMk eq "Y"}'>
                                <input name="btnPrint" type="button" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.StopPaymentProcessDetailForm.onsubmit()){document.StopPaymentProcessDetailForm.submit();}else{return false;}"/>&nbsp;
                            </c:if>
                            <c:if test='${StopPaymentProcessQueryCase.stexpndMk ne "Y"}'>
                                <input name="btnPrint" type="button" class="button" value="列　印" disabled="disabled" />&nbsp;
                            </c:if>
                        </acl:checkButton>
			            <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.StopPaymentProcessDetailForm.submit();" />
                        </acl:checkButton>
                    </td>
               </tr>
               <tr>
                   <td colspan="8">
                       <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                           <tr>
                               <td width="50%">
                                   <span class="issuetitle_L_down">受理編號：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.apNoString}" />
                               </td>
                               <td width="50%">
                                   <span class="issuetitle_L_down">給付別：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.payCode}" />
                               </td>
                           </tr>
                       </table>
                   </td>
               </tr>
               <tr>
                   <td colspan="8" align="center" class="table1">
                       <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                           <tr>
                	           <td colspan="4" class="issuetitle_L">
                	               <span class="systemMsg">▲</span>&nbsp;案件資料
                	           </td>
                           </tr>
                           <tr>
                               <td colspan="4" id="iss">
                                   <span class="issuetitle_L_down">事故者姓名：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.evtName}" />
                               </td>
                           </tr>
                           <tr>
                               <td width="25%" id="iss">
                                   <span class="issuetitle_L_down">事故者身分證號：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.evtIdnNo}" />
                               </td>
                               <td width="25%" id="iss">
                                   <span class="issuetitle_L_down">事故者出生日期：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.evtBrDateChineseString}" />
                               </td>
                               <td id="iss">
                                   <span class="issuetitle_L_down">申請日期：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.appDateChineseString}" />
                               </td>
                               <td id="iss">
                                   <span class="issuetitle_L_down">事故日期：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.evtJobDateStr}" />
                               </td>
                           </tr>
                           <tr>
                           	   <td id="iss">
                                   <span  width="25%" class="issuetitle_L_down">核定年月：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.issuYm}" />
                               </td>
                               <td id="iss">
                                   <span   width="25%" class="issuetitle_L_down">處理狀態：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.procStatName}" />
                               </td>
                               <td id="iss">
                                   <span  width="25%" class="issuetitle_L_down">資料別：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.caseTypStr}" />
                               </td>
                               <td  width="25%" id="iss">
                                   <span class="issuetitle_L_down">承辦人員：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.promoteUser}" />
                               </td>
                           </tr>
                           <tr>
                               <td id="iss">
                                   <span class="issuetitle_L_down">給付年月起迄：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.payBegYmStr}" />
                                  ~<c:out value="${StopPaymentProcessQueryCase.payEndYmStr}" />
                               </td>
                               <td id="iss">
                                   <span class="issuetitle_L_down">核付日期：</span>
                                   <c:out value="${StopPaymentProcessQueryCase.aplpayDateChineseString}" />
                               </td>
                               <td id="iss">
                                   <span class="issuetitle_L_down">合計核定金額：</span>
                                   <fmt:formatNumber value="${StopPaymentProcessQueryCase.befIssueAmt}" />
                               </td>
                               <td id="iss">
                                   <span class="issuetitle_L_down">合計實付金額：</span>
                                   <fmt:formatNumber value="${StopPaymentProcessQueryCase.aplpayAmt}" />
                               </td>
                           </tr>
                           <tr>
                               <td id="iss">
                                   <span class="needtxt">＊</span>
                                   <span class="issuetitle_L_down">止付日期：</span>
                                   <c:if test='${empty StopPaymentProcessQueryCase.stexpndDate}'>
                                       <%=DateUtility.getNowChineseDate()%>
                                   </c:if>
                                   <c:if test='${not empty StopPaymentProcessQueryCase.stexpndDate}'>
                                       <c:out value="${StopPaymentProcessQueryCase.stexpndDateChineseString}" />
                                   </c:if>
                               </td>
                               <td id="iss" colspan="3">
                                   <span class="needtxt">＊</span><span class="issuetitle_L_down">止付原因：</span>
                                   <html:select styleId="stexpndReason" property="stexpndReason">
                                       <html:option value="">請選擇</html:option>
                                       <html:options collection="<%=ConstantKey.STEXPNDREASON_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                   </html:select>
                               </td>
                           </tr>
                           <tr>
                	           <td colspan="4"></td>
                           </tr>
                       </table>
                   </td>
               </tr>
               <tr>
          	       <td colspan="8">&nbsp;</td>
               </tr>
               <tr align="center">
                   <td class="issuetitle_L">
                       <table cellpadding="1" cellspacing="3" width="100%" class="px13">
                           <tr>
                               <td colspan="9" class="issuetitle_L">
                                   <span class="issuetitle_search">&#9658;</span>&nbsp;受款人資料
                               </td>
                           </tr>
                           <tr align="center">
                               <td width="8%" class="issuetitle_L">受款人序</td>
                               <td width="10%" class="issuetitle_L">受款人姓名</td>
                               <td width="12%" class="issuetitle_L">受款人身分證號</td>
                               <td width="12%" class="issuetitle_L">關 係</td>
                               <td width="20%" class="issuetitle_L">給付方式</td>
                               <td width="10%" class="issuetitle_L">可領金額</td>
                               <td width="9%" class="issuetitle_L">核付日期</td>
                               <td width="9%" class="issuetitle_L">入帳日期</td>
                               <td width="10%" class="issuetitle_L">止付條件(註)</td>
                           </tr>
                           <tr align="center">
                               <td colspan="9" align="center">
                    
                               <bean:define id="Case" name="<%=ConstantKey.STOP_PAYMENT_PROCESS_QUERY_CASE%>" />
                               <display:table name="pageScope.Case.applyList" id="listItem">
                               <display:column  style="width:8%; text-align:center;"  class="issueinfo_C" >
                                   <c:out value="${listItem.firstCol}" />&nbsp;
                               </display:column>
                               <display:column  style="width:10%; text-align:left;" class="issueinfo" >
                                   <c:out value="${listItem.benName}" />&nbsp;
                               </display:column>
                               <display:column  style="width:12%; text-align:left;" class="issueinfo" >
                                   <c:out value="${listItem.benIdnNo}" />&nbsp;
                               </display:column>
                               <display:column  style="width:12%; text-align:left;" class="issueinfo" >
                                   <c:out value="${listItem.benEvtRelStr}" />&nbsp;
                               </display:column>
                               <display:column  style="width:20%; text-align:left;" class="issueinfo">
                                   <c:out value="${listItem.payTypStr}" />&nbsp;
                               </display:column>
                               <display:column style="width:10%; text-align:right;" class="issueinfo_R">
                                   <fmt:formatNumber value="${listItem.mustIssueAmt}" />&nbsp;
                               </display:column>
                               <display:column  style="width:9%; text-align:center;" class="issueinfo_C">
                                   <c:out value="${listItem.aplpayDateStr}" />&nbsp;
                               </display:column> 
                               <display:column  style="width:9%; text-align:center;" class="issueinfo_C">
                                   <c:out value="${listItem.remitDateStr}" />&nbsp;
                               </display:column>  
                               <display:column  style="width:10%; text-align:center;" class="issueinfo_C">
                                   <c:if test='${empty listItem.stexpnd}'>
                                       <c:out value="" />&nbsp;
                                   </c:if>
                                   <c:if test='${listItem.stexpnd eq "1"}'>
                                       <c:out value="條件1" />&nbsp;
                                   </c:if>
                                   <c:if test='${listItem.stexpnd eq "2"}'>
                                       <c:out value="條件2" />&nbsp;
                                   </c:if>
                                   <c:if test='${listItem.stexpnd eq "3"}'>
                                       <c:out value="條件3" />&nbsp;
                                   </c:if>
                                   <c:if test='${listItem.stexpnd eq "4"}'>
                                       <c:out value="條件4" />&nbsp;
                                   </c:if>
                                   <c:if test='${listItem.stexpnd eq "5"}'>
                                       <c:out value="條件5" />&nbsp;
                                   </c:if>
                                   &nbsp;
                               </display:column>                   
                               </display:table>  
                               </td>
                           </tr>                        
                       </table>
                   </td>
               </tr>
               <tr>
          	       <td colspan="9">&nbsp;</td>
               </tr> 
               <tr>
                   <td colspan="9"><hr size="1" noshade>
                       <span class="titleinput">※注意事項：</span><br>
          　                                               1.&nbsp;各給付方式的止付條件如下述；當符合其條件者，始可執行止付作業。<br>
          　                                               條件1：給付方式為『匯入銀行帳戶』及『匯入郵局帳號』時，其止付條件為：實付金額大於0者，已核付但尚未領取者。<br>
          　                                               條件2：給付方式為『來局領取』時，其止付條件為：已核付但尚未領取且未退匯者。<br>
          　                                               條件3：給付方式為『郵政匯票』、『國外匯款』、『大陸地區匯款』及『扣押戶』時，其止付條件為：系統日期需小於等於核付日期加3個工作天者。<br>
          　                                               2.&nbsp;<span class="needtxt">＊</span>為必填欄位。
                   </td>
               </tr>         
            </table>
        </fieldset>
        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
