<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D136C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />   
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>  
    <html:javascript formName="DisabledApplicationDataUpdateForm" page="1" />	          
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/disabledApplicationDataUpdate" method="post" onsubmit="return validateDisabledApplicationDataUpdateForm(this);">
        
        <fieldset>
            <legend>&nbsp;重新查核失能程度維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="100%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="8" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackToUpdate'; document.DisabledApplicationDataUpdateForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr> 
                <tr>
                    <td colspan="7" class="issuetitle_L">                          
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                             <tr>
                                <td width="33%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <bean:write name="DisabledApplicationDataUpdateForm" property="apNoString" />
                                </td>
                                <td width="33%">
                                    <c:if test="${caseData.payKind eq '36'}">
                                        <span class="issuetitle_L_down">給付種類：</span>國併勞
                                    </c:if>
                                    <c:if test="${caseData.payKind eq '38'}">
                                        <span class="issuetitle_L_down">給付種類：</span>勞併國
                                    </c:if>
                                    <c:if test="${(caseData.payKind ne '36') and (caseData.payKind ne '38')}">
                                    <span class="issuetitle_L_down">給付別：</span>
                                                                                                             失能年金
                                    </c:if>
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">處理狀態：</span>
                                    <bean:write name="DisabledApplicationDataUpdateForm" property="procStatString" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">
                                    <span class="issuetitle_L_down">失能項目：</span>
                                    <c:out value="${caseData.criInJdpStr}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>                      
                 <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_BARE_CHECK_CASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" class="px13">                            
                            <display:column title="序&nbsp;號" style="width:8%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem_rowNum}" />&nbsp;
                            </display:column>
                            <display:column title="重新查核失能程度年月" style="width:13%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.reChkYm}" />&nbsp;
                            </display:column>
                            <display:column title="是否複檢" style="width:13%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.isreChkStr}" />&nbsp;
                            </display:column>
                            <display:column title="重新查核狀態" style="width:13%; text-align:left;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.reChkStatusStr}" />&nbsp;
                            </display:column>
                            <display:column title="重新查核結果" style="width:17%; text-align:left;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.reChkResultStr}" />&nbsp;
                            </display:column>
                            <display:column title="完成重新查核年月" style="width:9%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.comReChkYm}" />&nbsp;        
                            </display:column>
                            <display:column title="操作區" style="width:14%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <acl:checkButton buttonName="btnUpdate">
                                    <input type="button" class="button" name="btnUpdate" value="修　改" onclick="location.href='<c:url value="/disabledApplicationDataUpdate.do"/>?method=doRehcDetail&rowNum=<c:out value="${listItem_rowNum}" />';">
                                </acl:checkButton>
                                <acl:checkButton buttonName="btnDelete">
                                    <input type="button" class="button" name="btnDelete" value="刪　除" onclick="if(confirm('是否確定刪除?')){location.href='<c:url value="/disabledApplicationDataUpdate.do"/>?method=doRehcDelete&rowNum=<c:out value="${listItem_rowNum}" />'};">                                                                                             
                                </acl:checkButton>                                
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
