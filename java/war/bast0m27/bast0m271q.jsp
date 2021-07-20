<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAST0M271Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>     
    <script language="javascript" type="text/javascript">
    
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/execStatistics" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="2" />
        <html:hidden styleId="method" property="method" value="" />
      	<html:hidden styleId="baJobId" property="baJobId" value="" />
        
        <fieldset>
            <legend>&nbsp;年金統計執行作業處理狀況查詢 &nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="8" align="right">
                       <acl:checkButton buttonName="btnBack">
                          <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='2'; $('method').value='doBack270';  document.ExecStatisticsForm.submit();" />&nbsp;
                         </acl:checkButton>                                                
                    </td>
                </tr>                
                
                <tr align="center">                   
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.QRY_EXEC_STATISTICS_DATA_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >                            
                            <display:column title="序 號" headerClass="issuetitle_L" class="issueinfo_C" style="width:10%; text-align:center;">
                                <c:out value="${listItem_rowNum}" />&nbsp;
                            </display:column>
                            <display:column title="核付年月" headerClass="issuetitle_L" class="issueinfo_C" style="width:13%; text-align:center;">
                                <c:out value="${listItem.issuYm}" />&nbsp;
                            </display:column>
                            <display:column title="處理時間起迄" headerClass="issuetitle_L" class="issueinfo_C" style="width:30%; text-align:center;">
                                <c:out value="${listItem.procBegTime}" />~<c:out value="${listItem.procEndTime}" />&nbsp;
                            </display:column>
                            <display:column title="處理狀態" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:center;">
                                <c:out value="${listItem.status}" />&nbsp;
                            </display:column>
                            <display:column title="統計檔檢核報表" headerClass="issuetitle_L" class="issueinfo_C" style="width:15%; text-align:center;">
                               <c:if test="${listItem.status eq '執行結束'}">
                                <acl:checkButton buttonName="btnDetail">
                                    <input type="button" name="btnDetail" class="button_90" value="狀態明細" onclick="$('page').value='1'; $('method').value='doReport'; $('baJobId').value='<c:out value="${listItem.baJobId}" />'; document.ExecStatisticsForm.submit();" />&nbsp;
                                </acl:checkButton>
                                </c:if>    
                                <c:if test="${listItem.status ne '執行結束'}">
                                <acl:checkButton buttonName="btnDetail">
                                    <input type="button" name="btnDetail" class="button_90" value="狀態明細"  disabled="disabled" />&nbsp;
                                </acl:checkButton>
                                </c:if>  
                            </display:column>
                                           
                            <display:column title="資料明細" headerClass="issuetitle_L" class="issueinfo_C" style="width:12%; text-align:center;">
                                <c:if test="${listItem.status ne '等待中'}">
                                <acl:checkButton buttonName="btnDetail">
                                    <input type="button" name="btnDetail" class="button_90" value="狀態明細" onclick="$('page').value='1'; $('method').value='doQryProgressDtl'; $('baJobId').value='<c:out value="${listItem.baJobId}" />'; document.ExecStatisticsForm.submit();" />&nbsp;
                                </acl:checkButton>
                                </c:if>                 
                                <c:if test="${listItem.status eq '等待中'}">
                                <acl:checkButton buttonName="btnDetail">
                                    <input type="button" name="btnDetail" class="button_90" value="狀態明細" disabled="disabled"  />&nbsp;
                                </acl:checkButton>
                                </c:if>                                
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
