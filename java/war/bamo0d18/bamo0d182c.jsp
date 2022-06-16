<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
    <head>
		<acl:setProgId progId="BAMO0D182C" />
        <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    	<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    	<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
	    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    	<script type='text/javascript'>
    		
    		Event.stopObserving(window, 'beforeunload', beforeUnload);
    	
    	</script>
   	</head>
    
    <body scroll="no">
        <div id="content">
            <%@ include file="/includes/ba_header.jsp"%>
    		<%@ include file="/includes/ba_menu.jsp"%>
            
            
                <div id="main" class="mainBody">
                <html:form action="/disabledPayeeDataUpdateList" method="post">
                    <fieldset>
                        <legend>&nbsp;失能年金受款人資料更正&nbsp;</legend>
                        <div align="right" id="showtime">網頁下載時間：<func:nowDateTime /></div>
                        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td align="right" colspan="8">
                                <html:hidden styleId="page" property="page" value="1" />
                        		<html:hidden styleId="method" property="method" value="" />
                        		<html:hidden styleId="baappbaseId" property="baappbaseId" value="" />
                                <c:if test='${empty DisabledPayeeDataUpdateQueryCase[0].evtDieDate}'>
                        		    <acl:checkButton buttonName="btnInsert">
                            		    <input name="btnInsert" type="button" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='doPrepareInsert'; document.DisabledPayeeDataUpdateListForm.submit();" disabled="disabled"/>&nbsp;
                        		    </acl:checkButton>
                                </c:if>
                                <c:if test='${not empty DisabledPayeeDataUpdateQueryCase[0].evtDieDate}'>
                        		    <acl:checkButton buttonName="btnInsert">
                            		    <input name="btnInsert" type="button" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='doPrepareInsert'; document.DisabledPayeeDataUpdateListForm.submit();" />&nbsp;
                        		    </acl:checkButton>
                                </c:if>
                        		<acl:checkButton buttonName="btnQuery">
                            		<input name="btnQuery" type="button" class="button" value="可領金額" onclick="$('page').value='1'; $('method').value='doQuery'; document.DisabledPayeeDataUpdateListForm.submit();">&nbsp;
                        		</acl:checkButton>
                        		<acl:checkButton buttonName="btnPrint">
                        			<input name="btnPrint" type="button" class="button_120" value="檢視受理/審核清單" onclick="$('page').value='1'; $('method').value='doPrint'; document.DisabledPayeeDataUpdateListForm.submit();"/>
                        		</acl:checkButton>
                        		<acl:checkButton buttonName="btnBack">
                            		<input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.DisabledPayeeDataUpdateListForm.submit();" />
                        		</acl:checkButton>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="8" class="issuetitle_L">
                                    <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                        <tr>
                                			<td width="25%"><span class="issuetitle_L_down">受理編號：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].apNoStrDisplay}" />
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].sysCode}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">給付別：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].payCode}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">申請日期：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].appDateChineseString}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">事故日期：</span>
                                				<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtJobDateStr}" />
                                			</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2"><span class="issuetitle_L_down">事故者姓名：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtName}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">事故者身分證號：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtIdnNo}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">事故者出生日期：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtBrDateChineseString}" />
                                			</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr align="center">
                                <td class="issuetitle_L">
                        		<bean:define id="list" name="<%=ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE%>" />
                        		<display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >
                        		<display:column title="序號" headerClass="issuetitle_L" style="width: 5%; text-align: center;">
                            		<c:out value="${listItem_rowNum}" />&nbsp;
    	                		</display:column>
                        		<display:column title="受款人姓名" headerClass="issuetitle_L" style="width: 15%">
                        			<c:out value="${listItem.benName}" />&nbsp;
                        		</display:column>
                        		<display:column title="受款人身分證號" headerClass="issuetitle_L" style="width: 11%">
                        			<c:out value="${listItem.benIdnNo}" />&nbsp;
                        		</display:column>
                        		<display:column title="關 係" headerClass="issuetitle_L" style="width: 9%" >
                        			<c:out value="${listItem.benEvtRelName}" />&nbsp;
                        		</display:column>
                        		<display:column title="死亡日期" headerClass="issuetitle_L" style="width: 9%; text-align: center">
                        			<c:if test='${listItem.benEvtRel eq "1"}'>
                        				<c:out value="${listItem.evtDieDateStr}" />&nbsp;
                        			</c:if>
                        			<c:if test='${listItem.benEvtRel ne "1"}'>
                        				<c:out value="${listItem.benDieDateChineseString}" />&nbsp;
                        			</c:if>
                        		</display:column>
                        		<display:column title="繼承自受款人" headerClass="issuetitle_L" style="width: 15%" >
                        			<c:out value="${listItem.appUser}" />&nbsp;
                        		</display:column>
                        		<display:column title="受款人可領金額" headerClass="issuetitle_L" style="width: 11%; text-align: right;">
                        			<fmt:formatNumber value="${listItem.mustIssueAmt}"/>&nbsp; 
                        		</display:column>
                        		<display:column title="資料明細" headerClass="issuetitle_L" style="width: 25%; text-align: center;">		
                        			<c:if test='${listItem.isHeir eq "N"}'>
                        				<acl:checkButton buttonName="btnUpdate">
                                			<input type="button" class="button" name="btnUpdate" value="修　改" onclick="$('method').value='doPrepareUpdate'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.DisabledPayeeDataUpdateListForm.submit();">
                            			</acl:checkButton>
                        			</c:if>
                            		<acl:checkButton buttonName="btnDelete">
                                		<c:if test='${listItem.delButtonQ4 gt "0"}'>
                                    		<input type="button" class="button" name="btnDelete" value="刪　除" disabled="disabled">
                                		</c:if>
                                		<c:if test='${listItem.delButtonQ4 eq "0"}'>
                                    		<input type="button" class="button" name="btnDelete" value="刪　除" onclick="if (confirm('<bean:message bundle="<%=Global.BA_MSG%>" key="msg.delete.confirm"/>')){location.href='<c:url value="/disabledPayeeDataUpdateList.do"/>?method=doDelete&baappbaseId=<c:out value="${listItem.baappbaseId}" />';}">
                                		</c:if>
                            		</acl:checkButton>
                        		</display:column>
                        		</display:table>
                    			</td>
                            </tr>
                            <tr>
                                <td colspan="8">&nbsp;</td>
                            </tr>
                        </table>
                    </fieldset>
                    <p></p>
                 </html:form>   
                </div>
            
        </div>
        <%@ include file="/includes/ba_footer.jsp"%>
    </body>
</html:html>