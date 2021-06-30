<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M181Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
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
        <html:form action="/prodDisabledMediaOnline" method="post" onsubmit="return validateDisabledMediaOnlineForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="mfileName" property="mfileName" value="" />
        <html:hidden styleId="mfileDate" property="mfileDate" value="" />
        <html:hidden styleId="ftpSeq" property="ftpSeq" value="" />

        <fieldset>
            <legend>&nbsp;失能年金線上產製結果查詢 &nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="8" align="right">
                        <acl:checkButton buttonName="btnBack">
                          <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1';javascript:history.back();" />&nbsp;
                         </acl:checkButton>                                             
                    </td>
                </tr>                
                <bean:define id="qryForm" name="<%=ConstantKey.DISABLED_MEDIA_ONLINE_FORM %>" />
                <tr>
                    <td width="30%"  class="issuetitle_L">
                          <c:out value="給付別："/>                         
                          <span class="px13">
                           <c:if test="${qryForm.disabledMk eq 1}">
                            <c:out value="失能"/>
                           </c:if>
						   <c:if test="${qryForm.disabledMk eq 2}">
                            <c:out value="失能(國並勞36案)"/>
                           </c:if>                            
                         </span>
                    </td>
                    <td width="35%" class="issuetitle_L">
                          <c:out value="核定年月："/>                         
                          <span class="px13">
                            <c:out value="${qryForm.issuYm}"/>
                         </span>
                    </td>
                    <td width="35%" class="issuetitle_L">
                          <c:out value="核定日期："/>                         
                          <span class="px13">
                            <c:out value="${qryForm.chkDate}"/>
                         </span>
                    </td>
                </tr>
                
                <tr align="center">                   
                    <td colspan="4">
                        <bean:define id="list" name="<%=ConstantKey.DISABLED_MEDIA_ONLINE_CASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">                            
                            <display:column title="序 號" headerClass="issuetitle_L" class="issueinfo_C" style="width:7%; text-align:center;">
                                <c:out value="${listItem.rowNum}" />&nbsp;
                            </display:column>
                            <display:column title="媒體檔案名稱" headerClass="issuetitle_L" class="issueinfo" style="width:12%; text-align:center;">
                                <c:out value="${listItem.mfileName}" />&nbsp;
                            </display:column>
                            <display:column title="媒體檔案日期" headerClass="issuetitle_L" class="issueinfo" style="width:16%; text-align:center;">
                                <c:out value="${listItem.mfileDate}" />&nbsp;
                            </display:column>
                            <display:column title="檔案下載" headerClass="issuetitle_L" class="issueinfo_C" style="width:8%; text-align:center;">
                                <acl:checkButton buttonName="btnDetail">
                                   <input type="button" name="btnDetail" class="button" value="檔案下載" onclick="$('page').value='1'; $('method').value='doDownload';  $('mfileName').value='<c:out value="${listItem.mfileName}" />';   document.DisabledMediaOnlineForm.submit();" />&nbsp;
                                 </acl:checkButton>                            
                            </display:column>
                            <display:column title="傳送" headerClass="issuetitle_L" class="issueinfo_C" style="width:10%; text-align:center;">
                                <acl:checkButton buttonName="btnDetail">
                                    <input type="button" name="btnDetail" class="button" value="傳送" onclick="$('page').value='1'; $('method').value='doUploadFile';$('mfileName').value='<c:out value="${listItem.mfileName}" />';$('mfileDate').value='<c:out value="${listItem.mfileDate}" />';$('ftpSeq').value='<c:out value="${listItem.ftpSeq}" />'; document.DisabledMediaOnlineForm.submit();" />&nbsp;
                                </acl:checkButton>
                            </display:column>
                            <display:column title="成功次數" headerClass="issuetitle_L" class="issueinfo_C" style="width:10%; text-align:center;">
                                <c:out value="${listItem.ftpSeq}" />&nbsp;
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
