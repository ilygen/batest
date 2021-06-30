<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D072C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="DependantDataUpdateListForm" page="1" />
    <script language="javascript" type="text/javascript">
    	
    	
    Event.stopObserving(window, 'beforeunload', beforeUnload);
	</script>
</head>
	

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/dependantDataUpdateList" method="post">
        
        <fieldset>
            <legend>&nbsp;眷屬資料更正&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="8">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnInsert">
                            <input name="btnInsert" type="button" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='doInsert'; document.DependantDataUpdateListForm.submit();" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnPrint">
                            <input name="btnPrint" type="button" tabindex="1001" class="button_120" value="檢視受理/審核清單" onclick="location.href='<c:url value="/printOldAgeReviewRpt01.do?method=doReport&apNo1Begin=${DependantEvtDataUpdateQueryCase[0].apNo1}&apNo2Begin=${DependantEvtDataUpdateQueryCase[0].apNo2}&apNo3Begin=${DependantEvtDataUpdateQueryCase[0].apNo3}&apNo4Begin=${DependantEvtDataUpdateQueryCase[0].apNo4}&apNo1End=${DependantEvtDataUpdateQueryCase[0].apNo1}&apNo2End=${DependantEvtDataUpdateQueryCase[0].apNo2}&apNo3End=${DependantEvtDataUpdateQueryCase[0].apNo3}&apNo4End=${DependantEvtDataUpdateQueryCase[0].apNo4}&action=single"/>';"/>&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.DependantDataUpdateListForm.submit();" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" class="issuetitle_L">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${DependantEvtDataUpdateQueryCase[0].apNoStrDisplay}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:out value="${DependantEvtDataUpdateQueryCase[0].payCode}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${DependantEvtDataUpdateQueryCase[0].appDateChineseString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故日期：</span>
                                    <c:out value="${DependantEvtDataUpdateQueryCase[0].evtJobDateStr}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${DependantEvtDataUpdateQueryCase[0].evtName}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${DependantEvtDataUpdateQueryCase[0].evtBrDateChineseString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${DependantEvtDataUpdateQueryCase[0].evtIdnNo}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td class="issuetitle_L">
                        <bean:define id="list" name="<%=ConstantKey.DEPENDANT_DATA_UPDATE_QUERY_CASE%>" />
                    
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >
                        <display:column title="序號" headerClass="issuetitle_L" style="width:9%; text-align:center;">
                            <c:out value="${listItem_rowNum}" />
    	                </display:column>
                        <display:column property="famName" title="眷屬姓名" headerClass="issuetitle_L" style="width:26%; text-align:left;" />
                        <display:column property="famIdnNo" title="眷屬身分證號" headerClass="issuetitle_L" style="width:20%; text-align:left;" />
                        <display:column property="famEvtRelName" title="關 係" headerClass="issuetitle_L" style="width:20%; text-align:left;" />         
                        <display:column title="資料明細" headerClass="issuetitle_L" style="width:25%; text-align:center;" >
                            <acl:checkButton buttonName="btnUpdate">
                                <input type="button" class="button" name="btnUpdate" value="修　改"  onclick="location.href='<c:url value="/dependantDataUpdateList.do"/>?method=doUpdate&bafamilyId=<c:out value="${listItem.bafamilyId}" />';">
                            </acl:checkButton>
                            <c:if test='${deleteStatus==0}'>
	                            <acl:checkButton buttonName="btnDelete">
	                                    <input type="button" class="button" name="btnDelete" value="刪　除" onclick="location.href='<c:url value="/dependantDataUpdateList.do"/>?method=doDelete&bafamilyId=<c:out value="${listItem.bafamilyId}" />';">
	                            </acl:checkButton>
                            </c:if>
                        </display:column>
                        </display:table>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" align="center" class="word12"><br>
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