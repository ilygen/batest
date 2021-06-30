<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M042X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <script language="javascript" type="text/javascript">
    
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/receivableAdjustBJ" method="post" >
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;已收調整作業 &nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="6" align="right">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="javascript:history.back();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>                
                <bean:define id="master" name="<%=ConstantKey.RECEIVABLE_ADJUST_BJ_MASTER_CASE %>" />
                <tr>
                    <td colspan="6" class="issuetitle_L">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="issuetitle_L_down">
                            <tr class="px13">            
                                <td colspan="4">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${master.apNoStr}" />
                                </td>
                            </tr>
                            <tr class="px13">
                                <td width="25%">
                                    <span class="issuetitle_L_down">受款人姓名：</span>
                                    <c:out value="${master.benName}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受款人身分證號：</span>
                                    <c:out value="${master.benIdnNo}" />
                                </td>          
                                <td width="25%">
                                    <span class="issuetitle_L_down">更正前姓名：</span>
                                    <c:out value="${master.befBenName}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">更正前身分證號：</span>
                                    <c:out value="${master.befBenIdnNo}" />
                                </td>  
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td>                    
                        <bean:define id="detail" name="<%=ConstantKey.RECEIVABLE_ADJUST_BJ_DETAIL_CASE%>" />
                        <tr align="center">
                            <td width="16%" class="issuetitle">&nbsp;</td>
                            <td width="16%" class="issuetitle_L">收回方式</td>  
                            <td width="17%" class="issuetitle_L">核定年月</td>                
                            <td width="17%" class="issuetitle_L">給付年月</td>
                            <td width="17%" class="issuetitle_L">應收金額</td>
                            <td width="17%" class="issuetitle_L">已收金額</td>
                        </tr>
                        <tr>
                            <td class="issueinfo_C" >更正前</td>
                            <td class="issueinfo_C">
                                <c:if test="${detail.befRecKind eq '01'}">                                
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_01 %>" />
                                </c:if>
                                <c:if test="${detail.befRecKind eq '02'}">                                
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_02 %>" />
                                </c:if>
                                <c:if test="${detail.befRecKind eq '03'}">                                
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_03 %>" />
                                </c:if>
                            </td>
                            <td class="issueinfo_C">
                                <c:out value="${detail.befIssuYmStr}" />
                            </td>
                            <td class="issueinfo_C">
                                <c:out value="${detail.befPayYmStr}" />
                            </td>
                            <td class="issueinfo_C">
                                <c:out value="${detail.befRecAmt}" />
                            </td>
                            <td class="issueinfo_C">
                                <c:out value="${detail.befWoAmt}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="issueinfo_C">更正後</td>
                            <td class="issueinfo_C">
                                <c:if test="${detail.recKind eq '01'}">                                
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_01 %>" />
                                </c:if>
                                <c:if test="${detail.recKind eq '02'}">                                
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_02 %>" />
                                </c:if>
                                <c:if test="${detail.recKind eq '03'}">                                
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_03 %>" />
                                </c:if>
                            </td>
                            <td class="issueinfo_C">
                                <c:out value="${detail.issuYmStr}" />
                            </td>
                            <td class="issueinfo_C">
                                <c:out value="${detail.payYmStr}" />
                            </td>
                            <td class="issueinfo_C">
                                <c:out value="${detail.recAmt}" />
                            </td>
                            <td class="issueinfo_C">
                                <c:out value="${detail.woAmt}" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6">&nbsp;</td>
                        </tr>                        
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
