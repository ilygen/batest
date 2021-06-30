<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="tw.gov.bli.ba.decision.cases.PaymentDecisionExtCase" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BARC0D012A" />
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
        //var chkStr = '';
        var banName = '';
        <%--
        <%for(int i=0; i<((List<PaymentDecisionExtCase>)request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_CHK_LIST)).size(); i++){%>
            <%if(i!=((List<PaymentDecisionExtCase>)request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_CHK_LIST)).size()-1){%>
                chkStr+='<span title="'+'<%=(((List<PaymentDecisionExtCase>)request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_CHK_LIST)).get(i)).getChkResultStr()%>'+
                             '">'+'<%=(((List<PaymentDecisionExtCase>)request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_CHK_LIST)).get(i)).getChkCodeStr()%>'+'</span>,　';
            <%}else{%>
                chkStr+='<span title="'+'<%=(((List<PaymentDecisionExtCase>)request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_CHK_LIST)).get(i)).getChkResultStr()%>'+
                             '">'+'<%=(((List<PaymentDecisionExtCase>)request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_CHK_LIST)).get(i)).getChkCodeStr()%>'+'</span>';                                                                                                         
            <%}%>
        <%}%>            
        $("chkContent").innerHTML=chkStr; 
        --%>
        
        <%for(int i=0; i<((List<PaymentDecisionExtCase>)request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_BENNAME_LIST)).size(); i++){%>
            <%if(i!=((List<PaymentDecisionExtCase>)request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_BENNAME_LIST)).size()-1){%>
                banName+='<%=(((List<PaymentDecisionExtCase>)request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_BENNAME_LIST)).get(i)).getBenName()%>'+',　';
            <%}else{%>
                banName+='<%=(((List<PaymentDecisionExtCase>)request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_BENNAME_LIST)).get(i)).getBenName()%>';                                                                                                         
            <%}%>
        <%}%> 
        $("benNameContent").innerHTML=banName;          
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
            <legend>&nbsp;老年年金給付決行作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="javascript:history.back();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>    
                <tr>
                    <td colspan="2">
                        <bean:define id="pay" name="<%=ConstantKey.PAYMENT_DECISION_CASE%>" />
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                              <td width="25%">
                                <span class="issuetitle_L_down">受理編號：</span>
                                <c:out value="${pay.apNoStrDisplay}" />
                              </td>
                              <td width="25%">
                                <span class="issuetitle_L_down">給付別：</span>
                                <c:if test='${(pay.pagePayKind eq "L")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                </c:if>
                                <c:if test='${(pay.pagePayKind eq "K")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                </c:if>
                                <c:if test='${(pay.pagePayKind eq "S")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                </c:if>
                              </td>
                              <td width="25%">
                                <span class="issuetitle_L_down">版　次：</span>
                                <c:out value="${pay.veriSeq}" />
                              </td>
                              <td width="25%">
                                <span class="issuetitle_L_down">資料別：</span>
                                <c:if test='${(pay.caseTyp eq "1")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_1%>" />
                                </c:if>
                                <c:if test='${(pay.caseTyp eq "2")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_2%>" />
                                </c:if>
                                <c:if test='${(pay.caseTyp eq "3")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_3%>" />
                                </c:if>
                                <c:if test='${(pay.caseTyp eq "4")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_4%>" />
                                </c:if>
                              </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1" colspan="2">
                        <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                            <tr>
                                <td colspan="3" class="issuetitle_L">
                                    <span class="systemMsg">▲</span>&nbsp;
                                    <a href='<c:url value="/paymentDecision.do?method=doSingleReviewDetail" />'>審核清單明細資料</a>                                    
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" width="33%">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${pay.evtName}" />
                                </td>
                                <td id="iss" width="33%">
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${pay.evtIdnNo}" />
                                </td>
                                <td id="iss" width="34%">
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${pay.evtBrDateStr}" />
                                </td>
                            </tr>
                            <tr class="i">
                                <td id="iss">
                                    <span class="issuetitle_L_down">離職日期：</span>
                                    <c:out value="${pay.evtJobDateStr}" />
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${pay.appDateStr}" />
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">審核人員/審核日期：</span>
                                    <c:out value="${pay.chkMan}" />&nbsp;/&nbsp;<c:out value="${pay.chkDateStr}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">交查發函日期-註記：</span>
                                    <c:out value="${pay.letterTypeMk1}" />&nbsp;
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">不給付日期-註記：</span>
                                    <c:out value="${pay.letterTypeMk2}" />&nbsp;
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">補件通知日期-註記：</span>
                                    <c:out value="${pay.letterTypeMk3}" />&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">照會發函日期-註記：</span>
                                    <c:out value="${pay.letterTypeMk4}" />&nbsp;
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">其他簽函日期-註記：</span>
                                    <c:out value="${pay.letterTypeMk5}" />&nbsp;
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">電腦編審結果：</span>
                                    <c:if test='${(pay.acceptMk eq "Y")}'>
                                        <c:out value="<%=ConstantKey.PAYMENT_REVIEW_ACCEPTMK_STR_Y %>" />
                                    </c:if>
                                    <c:if test='${(pay.acceptMk eq "N")}'>
                                        <c:out value="<%=ConstantKey.PAYMENT_REVIEW_ACCEPTMK_STR_N %>" />
                                    </c:if>
                                    <c:if test='${(pay.acceptMk ne "Y")and(pay.acceptMk ne "N")}'>
                                        <c:out value="<%=ConstantKey.PAYMENT_REVIEW_ACCEPTMK_STR_OTHER %>" />
                                    </c:if>&nbsp;
                                </td>                                
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">核定年月：</span>
                                    <c:out value="${pay.issuYmStr}" />&nbsp;
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">給付年月起迄：</span>
                                    <c:out value="${pay.minPayYmStr}" />
                                    <c:if test='${(pay.minPayYmStr ne "")or(pay.maxPayYmStr ne "")}'>
                                        &nbsp;~&nbsp;
                                    </c:if>
                                    <c:out value="${pay.maxPayYmStr}" />&nbsp;
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">人工審核結果：</span>
                                    <c:out value="${pay.manchkMk}" />&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">合計核定金額：</span>
                                    <c:out value="${pay.issueAmt}" />&nbsp;
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">合計實付金額：</span>
                                    <c:out value="${pay.aplPayAmt}" />&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                                        <tr>
                                            <td width="13%" valign="top">
                                                <span class="issuetitle_L_down">事故者編審註記：</span>
                                            </td>
                                            <logic:iterate id="chkFileData" name="<%=ConstantKey.PAYMENT_DECISION_CHK_LIST %>">                                                    
                                                <td width="87%">
                                                    <c:out value="${chkFileData.issuPayYm}" />：
                                                    <logic:iterate id="chkFile" indexId="i" name="chkFileData" property="chkFileList">
                                                        <span title="<c:out value="${chkFile.chkResult}" />">
                                                            <c:out value="${chkFile.chkCodePost}"/>
                                                        </span>
                                                        <c:if test="${i+1 ne chkFileData.chkFileDataSize}">
                                                            <c:out value=",　" />
                                                        </c:if>
                                                    </logic:iterate>
                                                </td>
                                            </logic:iterate>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <span class="issuetitle_L_down">受款人：</span>
                                    <span id="benNameContent">&nbsp;</span>                                                                                            
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3"></td>
                            </tr>
                        </table>
                    </td>
                </tr>                                            
                <tr>
                    <td colspan="3">&nbsp;</td>
                </tr>     
            </table>
        </fieldset>        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
