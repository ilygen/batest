<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X012C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="CheckMarkMaintListForm" page="1" />				
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/checkMarkMaintList" method="post">
        
        <fieldset>
            <legend>&nbsp;編審註記維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td align="right" colspan="7"> 
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnBack">         
			                <input name="btnBack" type="button" class="button" value="返  回 " onclick="$('page').value='1'; $('method').value='doBack'; document.CheckMarkMaintListForm.submit();" />
			            </acl:checkButton>
		            </td>
                </tr>	  
	            <tr>
	                <td align="right" colspan="7">
	                    <table class="px13" width="100%" border="0" cellspacing="2" cellpadding="2">
                            <tr>
                                <td width="20%" class="issuetitle_L">給付種類：<c:out value="${sessionScope.PAYCODE}" /></td>
	                            <td class="issuetitle_L">類別：<c:out value="${sessionScope.CHKGROUP}" /></td>
		                    </tr>
                        </table>
		            </td>
	            </tr>	  
	            <tr align="center">
	                <td>
	                    <bean:define id="list" name="<%=ConstantKey.CHECK_MARK_MAINT_QUERY_CASE%>" />
                    
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" class="px13">
                        <display:column property="chkCode" title="編審註記代號" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L"/>
                        <display:column property="chkLevelStr" title="嚴重程度" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L"/>
                        <display:column title="有效日期起迄" style="width:14%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                            <c:if test="${(listItem.valibDate ne null) and (listItem.valieDate ne null)}">
                                <c:out value="${listItem.valibDateStr}" />~<c:out value="${listItem.valieDateStr}" />
                            </c:if>
                        </display:column>
                        <display:column property="chkDesc" title="中文說明" style="width:18%; text-align:left;" class="issueinfo_C" headerClass="issuetitle_L"/>
                        <display:column property="chkCondesc" title="編審條件" style="width:24%; text-align:left;" class="issueinfo_C" headerClass="issuetitle_L"/>
                        <display:column title="法令依據" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">            
                            <c:out value="${listItem.chkLawdesc}" />
                            <logic:empty property="chkLawdesc" name="listItem">
                                    &nbsp;
                            </logic:empty>
                        </display:column>
                        <display:column title="資料明細" style="width:14%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                            <acl:checkButton buttonName="btnUpdate">
                                <input type="button" class="button" name="btnUpdate" value="修改" onclick="location.href='<c:url value="/checkMarkMaintList.do"/>?method=doUpdate&rowNum=<c:out value="${listItem_rowNum}" />';">
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