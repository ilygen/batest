<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BARC0D013Q04" />
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
       
    function checkFields() {
        var msg = "";                
        
        if(msg != ""){
            alert(msg);                                                                       
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
                                    <c:if test='${(pay.payYmsStr ne "")or(pay.payYmeStr ne "")}'>
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
                                    <c:if test='${(pay.minPayYmStr ne "")or(pay.maxPayYmStr ne "")}'>
                                        &nbsp;~&nbsp;
                                    </c:if>
                                    <c:out value="${pay.maxPayYmStr}" />
                                </td>
                                <td>                                    
                                    <span class="issuetitle_L_down">核定格式：</span>
                                    <c:out value="${pay.notifyForm}" />                                                                       
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
                                    <a href='<c:url value="/paymentDecision.do?method=reviewApplyData" />' title="連結至：請領同類/他類資料" target="_self" class="needtxt"><span>請領同類／他類／另案扣減</span></a>
                                    <a href='<c:url value="/paymentDecision.do?method=reviewBeneficiaryData" />' title="連結至：事故者/受款人資料" target="_self"><span>事故者／受款人資料</span></a>
                                    <a href='<c:url value="/paymentDecision.do?method=reviewIssuData" />' title="連結至：核定資料" target="_self"><span>核定資料</span></a>
                                    <h5><a>年資資料</a></h5>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" cellpadding="1" cellspacing="3" class="px13">
                            <tr>
                                <td colspan="9" class="issuetitle_L"><span class="issuetitle_search">&#9658;</span>&nbsp;核定資料</td>
                            </tr>
                            <tr>
                                <td colspan="9" class="issuetitle_L">
                                         更正原因：
                                    <c:out value="${pay.chgNote}" />                                    
                                </td>
                            </tr>
                            <tr align="center">
                                <td width="9%" class="issuetitle_L">給付年月</td>
                                <td width="11%" class="issuetitle_L">核定金額/<br>物價調整金額</td>
                                <td width="9%" class="issuetitle_L">應收金額</td>
                                <td width="9%" class="issuetitle_L">補發金額</td>
                                <td width="9%" class="issuetitle_L">事故者扣減<br>總金額</td>
                                <td width="9%" class="issuetitle_L">匯款匯費</td>
                                <td width="9%" class="issuetitle_L">實付金額</td>
                                <td width="10%" class="issuetitle_L">電腦編審結果</td>
                                <td width="25%" class="issuetitle_L">人工審核結果</td>                                
                            </tr>
                            
                            <logic:iterate id="benData" indexId="index" name="<%=ConstantKey.PAYMENT_DECISION_BENEFICIARY_DATA_LIST%>" >                                                                                                            
                                <tr>
                                    <td colspan="9" id="iss">                                        
                                        <table width="80%" border="0" cellspacing="2" cellpadding="2" class="px13">
                                            <tr>
                                                <c:if test="${index eq '0'}">                                                   
                                                    <td class="issuetitle_L_down">
                                                               事故者姓名：<c:out value="${benData.benName}" />&nbsp;  
                                                    </td>
                                                    <td class="issuetitle_L_down">
                                                               事故者身分證號：<c:out value="${benData.benIdnNo}" />&nbsp;  
                                                    </td>                                                    
                                                </c:if>
                                                <c:if test="${index ne '0'}">
                                                    <td>
                                                               受款人序：<c:out value="${index}" />&nbsp;
                                                    </td>
                                                    <td>
                                                               受款人姓名：<c:out value="${benData.benName}" />&nbsp;
                                                    </td>
                                                    <td>受款人身分證號：<c:out value="${benData.benIdnNo}" />&nbsp;</td>                                                
                                                    <td>關　係：
                                                        <c:if test="${benData.benEvtRel eq '1'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1 %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq '2'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2 %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq '3'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3 %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq '4'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4 %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq '5'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5 %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq '6'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6 %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq '7'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7 %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq 'A'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq 'C'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq 'F'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq 'N'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq 'Z'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z %>" />
                                                        </c:if>
                                                        <c:if test="${benData.benEvtRel eq 'O'}">
                                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O %>" />
                                                        </c:if>&nbsp;
                                                    </td>
                                                </c:if>
                                            </tr>                                                                                        
                                        </table>                                                                                                                       
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="9">
                                        <table width="100%" border="0" cellspacing="2" cellpadding="2" class="px13">
                                            <logic:notEmpty name="benData" property="benIssueDataList">
                                                <logic:iterate id="benIssueData" indexId="index" name="benData" property="benIssueDataList">                                                                        
                                                    <tr>
                                                        <td width="9%" class="issueinfo_C">
                                                            <c:out value="${benIssueData.issuYmStr}" />&nbsp;
                                                        </td>                                                    
                                                        <td width="11%" class="issueinfo_C">
                                                            <c:out value="${benIssueData.issueAmt}" />&nbsp;
                                                        </td>
                                                        <td width="9%" class="issueinfo_C">
                                                            <c:out value="${benIssueData.recAmt}" />&nbsp;
                                                        </td>
                                                        <td width="9%" class="issueinfo_C">
                                                            <c:out value="${benIssueData.supAmt}" />&nbsp;
                                                        </td>
                                                        <td width="9%" class="issueinfo_C">
                                                            <c:out value="${benIssueData.otherAmt}" />&nbsp;
                                                        </td>
                                                        <td width="9%" class="issueinfo_C">
                                                            <c:out value="${benIssueData.payRate}" />&nbsp;
                                                        </td>
                                                        <td width="9%" class="issueinfo_C">
                                                            <c:out value="${benIssueData.aplPayAmt}" />&nbsp;
                                                        </td>
                                                        <td width="10%" class="issueinfo_C">
                                                            <c:if test="${benIssueData.acceptMk eq 'Y'}">
                                                                <c:out value="Y-合格" />
                                                            </c:if>
                                                            <c:if test="${benIssueData.acceptMk eq 'N'}">
                                                                <c:out value="N-不合格" />
                                                            </c:if>
                                                            <c:if test="${(benIssueData.acceptMk ne 'Y')and(benIssueData.acceptMk ne 'N')}">
                                                                <c:out value="待處理" />
                                                            </c:if>&nbsp;
                                                        </td>
                                                        <td width="25%" class="issueinfo_C">
                                                            <c:if test="${benIssueData.manchkMk eq 'Y'}">
                                                                <c:out value="Y-合格" />
                                                            </c:if>
                                                            <c:if test="${benIssueData.manchkMk eq 'N'}">
                                                                <c:out value="N-不合格" />
                                                            </c:if>
                                                            <c:if test="${(benIssueData.manchkMk eq '')and(benIssueData.manchkMk eq ' ')}">
                                                                <c:out value="待處理" />
                                                            </c:if>&nbsp;                                                                                                               
                                                        </td>                                       
                                                    </tr>      
                                                </logic:iterate>
                                            </logic:notEmpty>
                                            <logic:empty name="benData" property="benIssueDataList">
                                                <tr>
                                                    <td colspan="9" class="issueinfo_C" align="center">
                                                        <font color="red">無資料</font>
                                                    </td>
                                                </tr>
                                            </logic:empty>                                                                                                                          
                                        </table>
                                    </td>
                                </tr>                                                         
                            </logic:iterate>
                            <tr>
                                <td colspan="10">&nbsp;</td>
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
