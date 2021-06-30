<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X141F" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />   
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>  
    <html:javascript formName="SurvivorReplacementRatioMaintListForm" page="1" />	          
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/survivorReplacementRatioMaintList" method="post" onsubmit="return validateSurvivorReplacementRatioMaintListForm(this);">
        
        <fieldset>
            <legend>&nbsp;遺屬年金所得替代率參數維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="100%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="8" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="effYmOrg" property="effYmOrg" />
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnConfirm" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='doInsert'; if (document.SurvivorReplacementRatioMaintListForm.onsubmit()){document.SurvivorReplacementRatioMaintListForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.SurvivorReplacementRatioMaintListForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>                

                <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST%>" />
                        
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" class="px13">                            
                            <display:column title="序&nbsp;號" style="width:8%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem_rowNum}" />&nbsp;
                            </display:column>
                            <display:column title="施行年月" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.effYmStr}" />&nbsp;
                            </display:column>
                            <display:column title="平均薪資級距1" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <fmt:formatNumber value="${listItem.insAvg1}" />&nbsp;
                            </display:column>
                            <display:column title="平均薪資級距2" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <fmt:formatNumber value="${listItem.insAvg2}" />&nbsp;
                            </display:column>
                            <display:column title="平均薪資級距3" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <fmt:formatNumber value="${listItem.insAvg3}" />&nbsp;
                            </display:column>
                            <display:column title="平均薪資級距4" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <fmt:formatNumber value="${listItem.insAvg4}" />&nbsp;
                            </display:column>
                            <display:column title="異動人員" style="width:8%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.user}" />&nbsp;
                            </display:column>
                            <display:column title="處理日期" style="width:8%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.dateStr}" />&nbsp;
                            </display:column>
                            <display:column title="資料明細" style="width:15%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <acl:checkButton buttonName="btnUpdate">
                                    <input type="button" class="button" name="btnUpdate" value="修　改" onclick="location.href='<c:url value="/survivorReplacementRatioMaintList.do"/>?method=doUpdate&rowNum=<c:out value="${listItem_rowNum}" />';">
                                </acl:checkButton>
                                <acl:checkButton buttonName="btnDelete">
                                    <c:if test='${listItem.deleteUpdateStr eq "Y"}'>
                                    	<input type="button" class="button" name="btnDelete" value="刪　除" disabled="true">
                                	</c:if>
                                	<c:if test='${listItem.deleteUpdateStr ne "Y"}'>
                                        <input type="button" class="button" name="btnDelete" value="刪　除" onclick="if(confirm('是否確定刪除?')){location.href='<c:url value="/survivorReplacementRatioMaintList.do"/>?method=doDelete&rowNum=<c:out value="${listItem_rowNum}" />'};">                                                                                             
                                    </c:if>  
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
