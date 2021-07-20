<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BACO0D013Q03" />
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
        <html:form action="/paymentReview" method="post" onsubmit="">
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
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackMaster'; document.PaymentReviewForm.submit();" />&nbsp;
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="7">
                        <bean:define id="pay" name="<%=ConstantKey.PAYMENT_REVIEW_CASE%>" />                        
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
                                    <%-- 
                                    <a href='<c:url value="/paymentReview.do?method=reviewMasterData" />' title="連結至：總表資料" target="_self"><span>總表資料</span></a>
                                    <a href='<c:url value="/paymentReview.do?method=reviewBeneficiaryData" />' title="連結至：事故者/受款人資料" target="_self"><span>事故者／受款人資料</span></a>
                                    <a href='<c:url value="/paymentReview.do?method=reviewIssuData" />' title="連結至：核定資料" target="_self"><span>核定資料</span></a>
                                    <h5><a>年資資料</a></h5>
                                    <a href='<c:url value="/paymentReview.do?method=reviewApplyData" />' title="連結至：請領同類/他類資料" target="_self" class="needtxt"><span>請領同類／他類／另案扣減</span></a>
                                    --%>
                                    <a href='<c:url value="/paymentReview.do?method=reviewMasterData" />' target="_self"><span>總表資料</span></a>
                                    <a href='<c:url value="/paymentReview.do?method=reviewBeneficiaryData" />' target="_self"><span>事故者／受款人資料</span></a>
                                    <a href='<c:url value="/paymentReview.do?method=reviewIssuData" />' target="_self"><span>核定資料</span></a>
                                    <h5><a>年資資料</a></h5>
                                    <a href='<c:url value="/paymentReview.do?method=reviewApplyData" />' target="_self" class="needtxt"><span>請領同類／他類／另案扣減</span></a>
                                    
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
                                    <span class="systemMsg">▲</span>&nbsp;欠費期間維護資料
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="4">
                                    <span class="issuetitle_L_down">最後單位保險證號：</span>
                                    <c:out value="${pay.lsUbno}" />&nbsp;
                                </td>
                            </tr>
                            <tr align="center">
                                <td>
                                    <bean:define id="seniList" name="<%=ConstantKey.PAYMENT_REVIEW_SENIMAINT_DATA_LIST%>" />
                                    <display:table name="pageScope.seniList" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                                        <display:column title="序號" style="width:33%; text-align:center;">
                                            <c:out value="${listItem_rowNum}" />
                                        </display:column>
                                        <display:column title="欠費期間起迄" style="width:33%; text-align:center;">
                                            <c:out value="${listItem.owesBdateStr}" />
                                            <c:if test='${(listItem.owesBdateStr ne "")or(listItem.owesEdateStr ne "")}'>
                                                &nbsp;~&nbsp;
                                            </c:if>
                                            <c:out value="${listItem.owesEdateStr}" />
                                        </display:column>
                                        <display:column title="欠費註記" style="width:34%; text-align:center;">
                                            <c:if test='${(listItem.evtSex eq "1")}'>
                                                <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_1%>" />
                                            </c:if>
                                            <c:if test='${(listItem.evtSex eq "2")}'>
                                                <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_2%>" />
                                            </c:if>&nbsp;
                                        </display:column>                                        
                                    </display:table>              
                                </td>
                            </tr>                                
                            <tr>
                                <td colspan="4"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="7">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="7" class="issuetitle_L"><span class="issuetitle_search">&#9658;</span>&nbsp;承保異動資料</td>
                </tr>
                <tr align="center">
                    <td>
                        <bean:define id="ciptList" name="<%=ConstantKey.PAYMENT_REVIEW_CIPT_DATA_LIST%>" />
                        <display:table name="pageScope.ciptList" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                            <display:column title="序 號" style="width:14%; text-align:center;">
                                <c:out value="${listItem_rowNum}" />
                            </display:column>
                            <display:column title="保險證號" style="width:14%; text-align:center;">
                                <c:out value="${listItem.uno}" />&nbsp;
                            </display:column>
                            <display:column title="異動別" style="width:14%; text-align:center;">
                                <c:out value="${listItem.txcd}" />&nbsp;
                            </display:column>
                            <display:column title="投保薪資" style="width:14%; text-align:center;">
                                <c:out value="${listItem.wage}" />&nbsp;
                            </display:column>
                            <display:column title="部 門" style="width:14%; text-align:center;">
                                <c:out value="${listItem.dept}" />&nbsp;
                            </display:column>
                            <display:column title="生效日期" style="width:14%; text-align:center;">
                                <c:out value="${listItem.efDteStr}" />
                            </display:column>
                            <display:column title="退保日期" style="width:14%; text-align:center;">
                                <c:out value="${listItem.proDteStr}" />&nbsp;
                            </display:column>                                        
                        </display:table>              
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
