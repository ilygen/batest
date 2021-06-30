<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M061Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>  
    <html:javascript formName="MonthBatchKForm" page="1" />	       
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        

    }    

    Event.observe(window, 'load', initAll, false);
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/monthBatchK" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;失能年金批次月處理作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.MonthBatchKForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>    
                <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.MONTH_BATCH_K_CASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                            <display:column title="序&nbsp;號" style="width:8%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem_rowNum}" />&nbsp;
                            </display:column>
                            <display:column title="核定年月" style="width:9%; text-align:center;">
                               <c:out value="${listItem.issuYmStr}" />&nbsp;
                            </display:column>
                            <display:column title="核定日期" style="width:9%; text-align:center;">
                               <c:out value="${listItem.chkDateStr}" />&nbsp;
                            </display:column>
                            <display:column title="給付別" style="width:6%; text-align:center;">
                                <c:if test='${(listItem.payCode eq "L")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                </c:if>
                                <c:if test='${(listItem.payCode eq "K")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                </c:if>
                                <c:if test='${(listItem.payCode eq "S")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                </c:if>&nbsp;
                            </display:column>
                            <display:column title="給付種類" style="width:6%; text-align:center;">
                                <c:if test='${(listItem.paySeqNo eq "1")}'>
                                    <c:out value="35、38案" />
                                </c:if>
                                <c:if test='${(listItem.paySeqNo eq "2")}'>
                                    <c:out value="36案" />
                                </c:if>&nbsp;
                            </display:column>
                            <display:column title="處理類型" style="width:13%; text-align:center;">
                                <c:if test='${(listItem.procType eq "2")}'>
                                    <c:out value="批次月試核" />
                                </c:if>
                                <c:if test='${(listItem.procType eq "3")}'>
                                    <c:out value="第一次批次月核定" />
                                </c:if>
                                <c:if test='${(listItem.procType eq "4")}'>
                                    <c:out value="第二次批次月核定" />
                                </c:if>&nbsp;
                            </display:column>
                             <display:column title="處理狀態" style="width:10%; text-align:center;">
                                <c:if test='${(listItem.status eq "R")}'>
                                    <c:out value="執行中" />
                                </c:if>
                                <c:if test='${(listItem.status eq "W")}'>
                                    <c:out value="等待中" />
                                </c:if>
                                <c:if test='${(listItem.status eq "E")}'>
                                    <c:out value="執行結束" />
                                </c:if>
                                <c:if test='${(listItem.status eq "N")}'>
                                    <c:if test='${(listItem.procType eq "1")}'>
                                       <c:out value="媒體產製錯誤" />
                                    </c:if>
                                    <c:if test='${(listItem.procType eq "2")}'>
                                       <c:out value="批次月試核錯誤" />
                                    </c:if>
                                    <c:if test='${(listItem.procType eq "3")}'>
                                       <c:out value="第一次批次月核定錯誤" />
                                    </c:if>
                                    <c:if test='${(listItem.procType eq "4")}'>
                                       <c:out value="第二次批次月核定錯誤" />
                                    </c:if>
                                </c:if>
                            </display:column>
                            <display:column title="月處理檔案" style="width:14%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <acl:checkButton buttonName="btnUpdate">
                                    <c:if test='${(listItem.fileName ne "")}'>
                                      <input type="button" class="button" name="btnDownLoad" value="檔案下載" onclick="location.href='<c:url value="/monthBatchK.do"/>?method=doDownLoad&fileName=<c:out value="${listItem.fileName}" />';">
                                    </c:if>
                                    <c:if test='${(listItem.fileName eq "")}'>
                                      <input type="button" class="button" name="btnDownLoad" value="檔案下載" disabled="true" >
                                    </c:if>
                                </acl:checkButton>
                            </display:column>
                            <display:column title="處理狀態明細" style="width:14%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <acl:checkButton buttonName="btnDetail">
                                    <input type="button" class="button" name="btnDetail" value="狀態明細" onclick="location.href='<c:url value="/monthBatchK.do"/>?method=doDetail&baJobId=<c:out value="${listItem.baJobId}" />';">
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
