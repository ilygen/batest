<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X025C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="AdviceMaintListForm" page="1" />				
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/adviceMaintList" method="post">
        
        <fieldset>
            <legend>&nbsp;核定通知書維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
          	        <td align="right" colspan="2">
          	            <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnBack">
          	                <input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.AdviceMaintListForm.submit();" />
          	            </acl:checkButton>
          	        </td>
                </tr>
                <tr>
                	<td><span class="issuetitle_L_down">給付種類：</span><c:out value="${sessionScope.PAYCODE}" /></td>         
                </tr>
                <tr align="center">
                	<td>
                        <bean:define id="list" name="<%=ConstantKey.ADVICE_MAINT_QUERY_CASE%>" />
                    
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >
                        <display:column title="核定格式" style="width:14%; text-align:center;">
                             <c:out value="${listItem.authTyp}" />&nbsp;
                        </display:column>
                        <display:column title="格式說明" style="width:20%; text-align:left;" >
                          <c:out value="${listItem.authTypDesc}" />&nbsp;
                        </display:column>
                        <display:column title="新增日期" style="width:15%; text-align:center;" >
                          <c:out value="${listItem.crtTime}" />&nbsp;
                        </display:column>
                        <display:column title="異動日期" style="width:15%; text-align:center;" >
                          <c:out value="${listItem.updTime}" />&nbsp;
                        </display:column>
                        <display:column title="維護人員" style="width:15%; text-align:center;" >    
                          <c:out value="${listItem.updUser}" />&nbsp;
                        </display:column>           
                        <display:column title="資料明細" style="width:14%; text-align:center;" >
                            <acl:checkButton buttonName="btnUpdate">
                                <input type="button" class="button" name="btnUpdate" value="查詢" onclick="location.href='<c:url value="/adviceMaintList.do"/>?method=doQueryDetail&payCode=<c:out value="${listItem.payCode}" />&authTyp=<c:out value="${listItem.authTyp}" />';">
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