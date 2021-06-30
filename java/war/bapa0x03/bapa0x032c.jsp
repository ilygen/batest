<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X032C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="DecisionLevelMaintListForm" page="1" />
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
  
    <div id="main" class="mainBody">
        <html:form action="/decisionLevelMaintList" method="post">
        
        <fieldset>
            <legend>&nbsp;決行層級維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="4">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.DecisionLevelMaintListForm.submit();" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr align="center">
                    <td colspan="4" class="issuetitle_L">
                    
                    <bean:define id="list" name="<%=ConstantKey.DECISION_LEVEL_MAINT_QUERY_CASE%>" />
                    
                    <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >
                    <display:column title="序號" style="width:4%; text-align:center;">
                        <c:out value="${listItem_rowNum}" />
    			    </display:column>
                    <display:column title="決行層級" style="width:10%; text-align:center;">
                        <c:if test="${(listItem.rechklvl eq '1')}">
                                                                             承辦人員
                        </c:if>
                        <c:if test="${(listItem.rechklvl eq '2')}">
                                                                             複核
                        </c:if>
                        <c:if test="${(listItem.rechklvl eq '3')}">
                                                                             科長
                        </c:if>
                        <c:if test="${(listItem.rechklvl eq '4')}">
                                                                             一等專員
                        </c:if>
                        <c:if test="${(listItem.rechklvl eq '5')}">
                                                                             副經理
                        </c:if>
                        <c:if test="${(listItem.rechklvl eq '6')}">
                                                                             經理
                        </c:if>
                    </display:column>
                    <display:column title="決行金額" style="width:15%; text-align:left;" > 
                        <c:out value='${listItem.stdAmt}' />
                    </display:column>                     
                    <display:column title="註記程度決行權限" style="width:15%; text-align:center;" >
                        <c:if test="${listItem.echkLevel eq '1'}">
                            <input name="echkLevel" type="checkbox" checked="checked" disabled="disabled">1.錯誤程度(E)
                        </c:if>
                        <c:if test="${listItem.echkLevel eq null}">
                            <input name="echkLevel" type="checkbox" disabled="disabled">1.錯誤程度(E)
                        </c:if>
                        <c:if test="${listItem.ochkLevel eq '1'}">
                            <input name="ochkLevel" type="checkbox" checked="checked" disabled="disabled">2.穿透程度(O)
                        </c:if>
                        <c:if test="${listItem.ochkLevel eq null}">
                            <input name="ochkLevel" type="checkbox" disabled="disabled">2.穿透程度(O)
                        </c:if>
                        <c:if test="${listItem.wchkLevel eq '1'}">
                            <input name="wchkLevel" type="checkbox" checked="checked" disabled="disabled">3.警告程度(W)
                        </c:if>
                        <c:if test="${listItem.wchkLevel eq null}">
                            <input name="wchkLevel" type="checkbox" disabled="disabled">3.警告程度(W)
                        </c:if>
                    </display:column>
                    <display:column title="資料明細" style="width:10%; text-align:center;" >
                        <acl:checkButton buttonName="btnUpdate">
                            <input type="button" class="button" name="btnUpdate" value="修改" onclick="location.href='<c:url value="/decisionLevelMaintList.do"/>?method=doUpdate&payCode=<c:out value="${listItem.payCode}" />&payKind=<c:out value="${listItem.payKind}" />&rechklvl=<c:out value="${listItem.rechklvl}" />';">
                        </acl:checkButton>
                    </display:column>            
                    </display:table>
            
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