<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D3A2Q" />
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
    
    <%-- 確認按鈕 --%>
    <%-- 按下 確認按鈕時  檢查checkbox是否有勾選的資料 --%>    
    <%-- begin ... [ --%>
    function checkFields() {
           
    }
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/printMonthlyRpt10" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
      	
        <fieldset>
            <legend>&nbsp;核付明細表下載處理狀況查詢 &nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="8" align="right">
                       <acl:checkButton buttonName="btnBack">
                          <input type="button" name="btnBack" class="button" value="返　回"  onclick="javascript:history.back();"  />&nbsp;
                         </acl:checkButton>                                                
                    </td>
                </tr>                
                
                <tr align="center">                   
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.MONTHLY_RPT10_PROGRESS_CASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >                            
                            <display:column title="序 號" headerClass="issuetitle_L" class="issueinfo_C" style="width:7%; text-align:center;">
                                <c:out value="${listItem.rowNum}" />&nbsp;
                            </display:column>
                            <display:column title="核定年月" headerClass="issuetitle_L" class="issueinfo" style="width:12%; text-align:center;">
                                <c:out value="${listItem.issuYm}" />&nbsp;
                            </display:column>
                            <display:column title="核定日期" headerClass="issuetitle_L" class="issueinfo" style="width:16%; text-align:center;">
                                <c:out value="${listItem.chkDate}" />&nbsp;
                            </display:column>
                            <display:column title="給付別" headerClass="issuetitle_L" class="issueinfo" style="width:16%; text-align:center;">
                                <c:out value="${listItem.payCode}" />&nbsp;
                            </display:column>  
                            <display:column title="處理狀態" headerClass="issuetitle_L" class="issueinfo" style="width:16%; text-align:center;">
                                <c:out value="${listItem.status}" />&nbsp;
                            </display:column>
                            <display:column title="處理類別" headerClass="issuetitle_L" class="issueinfo" style="width:16%; text-align:center;">
                                <c:out value="${listItem.procType}" />&nbsp;
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
