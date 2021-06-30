<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
    <head>
    	<acl:setProgId progId="BAMO0D184C" />
		<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    	<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    	<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    	<script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    </head>

    <body scroll="no">
        <div id="content">
            <%@ include file="/includes/ba_header.jsp"%>

    		<%@ include file="/includes/ba_menu.jsp"%>
            
                <div id="main" class="mainBody">
                <html:form action="/disabledPayeeDataUpdateList" method="post">
                    <fieldset>
                        <legend>&nbsp;失能年金受款人可領金額登錄&nbsp;</legend>
                        <div align="right" id="showtime">網頁下載時間：<func:nowDateTime /></div>
                        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td align="right" colspan="6">
                                    <html:hidden styleId="page" property="page" value="1" />
									<html:hidden styleId="method" property="method" value="" />
									<acl:checkButton buttonName="btnSave">
										<input name="btnSave" type="button" class="button" value="確　定"
											onclick="$('page').value='1'; $('method').value='doBackList'; document.DisabledPayeeDataUpdateListForm.submit();" />&nbsp;
                        			</acl:checkButton>
									<acl:checkButton buttonName="btnBack">
										<input name="btnBack" type="button" class="button" value="返　回"
										onclick="$('page').value='1'; $('method').value='doBackList'; document.DisabledPayeeDataUpdateListForm.submit();" />
									</acl:checkButton>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6">
                                    <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                        <tr>
                                        	<td width="25%"><span class="issuetitle_L_down">受理編號：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].apNoStrDisplay}" />
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
                                			<td width="25%"><span class="issuetitle_L_down">事故者出生日期：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtBrDateChineseString}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">事故者身分證號：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtIdnNo}" />
                                			</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6" align="center" class="table1">
                                    <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                                        <tr>
                                            <td width="33%"><span class="issuetitle_L_down">月給付金額：</span></td>
                                            <td width="33%">
                                                <span class="issuetitle_L_down">職災補償一次金金額：</span>
                                                
                                            </td>
                                            <td width="34%">
                                                <span class="issuetitle_L_down">核定金額：</span>
                                                <fmt:formatNumber value="${DisabledPayeeDataUpdateBadaprCase.issueAmt}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td ><span class="issuetitle_L_down">一次給付日數：</span></td>
                                            <td ><span class="issuetitle_L_down">一次給付平均日薪：</span></td>
                                            <td ><span class="issuetitle_L_down">一次給付普通金額：</span></td>
                                        </tr>				
                                        <tr>
                                            <td ><span class="issuetitle_L_down">失能年金已領金額：</span></td>
                                            <td ><span class="issuetitle_L_down">已領老年年金受理編號：</span></td>
                                            <td ><span class="issuetitle_L_down">老年年金已領金額：</span></td>
                                        </tr>
                                        <tr>
                                            <td >
                                                <span class="issuetitle_L_down">已領年金給付總額：</span>
                                                <fmt:formatNumber value="${DisabledPayeeDataUpdateBadaprCase.annuAmt}" />
                                            </td>
                                            <td colspan="2"><span class="issuetitle_L_down">差額金金額：</span></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6">&nbsp;</td>
                            </tr>
                            <tr align="center">
                            	<td class="issuetitle_L">
                        		<bean:define id="list" name="<%=ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE%>" />
                        		<display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >
                        		<display:column title="序號" headerClass="issuetitle_L" class="issueinfo_C" style="width: 7%">
                            		<c:out value="${listItem_rowNum}" />
    	                		</display:column>
                        		<display:column title="受款人姓名" headerClass="issuetitle_L" class="issueinfo" style="width: 26%">
                        			<c:out value="${listItem.benName}" />&nbsp;
                        		</display:column>
                        		<display:column title="受款人身分證號" headerClass="issuetitle_L" class="issueinfo" style="width: 20%">
                        			<c:out value="${listItem.benIdnNo}" />&nbsp;
                        		</display:column>
                        		<display:column title="關　係" headerClass="issuetitle_L" class="issueinfo" style="width: 15%" >
 	                       			<c:out value="${listItem.benEvtRel}" />&nbsp;
                        		</display:column>
                        		<display:column title="匯款匯費" headerClass="issuetitle_L" class="issueinfo_R" style="width: 12%" >
                        			<fmt:formatNumber value="${listItem.mitRate}"/> 
                        		</display:column>
                        		<display:column title="受款人可領金額" headerClass="issuetitle_L" class="issueinfo_C" style="width: 20%">
                        			<fmt:formatNumber value="${listItem.mustIssueAmt}"/> 
                        		</display:column>
                        		</display:table>
                    			</td>
                            </tr>
                            <tr>
                                <td colspan="6">&nbsp;</td>
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

