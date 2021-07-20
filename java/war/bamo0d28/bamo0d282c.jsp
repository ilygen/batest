<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
    <head>
		<acl:setProgId progId="BAMO0D282C" />
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
                <html:form action="/survivorPayeeDataUpdateList" method="post">
                    <fieldset>
                        <legend>&nbsp;遺屬年金受款人資料更正&nbsp;</legend>
                        <div align="right" id="showtime">網頁下載時間：<func:nowDateTime /></div>
                        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td align="right" colspan="8">
                                <html:hidden styleId="page" property="page" value="1" />
                        		<html:hidden styleId="method" property="method" value="" />
                        		<html:hidden styleId="baappbaseId" property="baappbaseId" value="" />
                        		<acl:checkButton buttonName="btnInsert">
                            		<input name="btnInsert" type="button" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='doPrepareInsert'; document.SurvivorPayeeDataUpdateListForm.submit();" />&nbsp;
                        		</acl:checkButton>
                        		<acl:checkButton buttonName="btnQuery">
                                    <logic:notEmpty name="<%=ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE%>">
                            		  <input name="btnQuery" type="button" class="button" value="可領金額" onclick="$('page').value='1'; $('method').value='doQuery'; document.SurvivorPayeeDataUpdateListForm.submit();">&nbsp;
                                    </logic:notEmpty>
                                    <logic:empty name="<%=ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE%>">
                                      <input name="btnQuery" type="button" class="button" value="可領金額" disabled="true">&nbsp;
                                    </logic:empty>
                        		</acl:checkButton>
                        		<acl:checkButton buttonName="btnPrint">
                        			<input name="btnPrint" type="button" class="button_120" value="檢視受理/審核清單" onclick="$('page').value='1'; $('method').value='doPrint'; document.SurvivorPayeeDataUpdateListForm.submit();"/>
                        		</acl:checkButton>
                        		<acl:checkButton buttonName="btnBack">
                            		<input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.SurvivorPayeeDataUpdateListForm.submit();" />
                        		</acl:checkButton>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="8" class="issuetitle_L">
                                    <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                        <tr>
                                			<td width="25%"><span class="issuetitle_L_down">受理編號：</span>
                                    			<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].apNoStrDisplay}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">給付別：</span>
                                    			<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].payCode}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">申請日期：</span>
                                    			<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].appDateChineseString}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">事故日期：</span>
                                				<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtJobDateStr}" />
                                			</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2"><span class="issuetitle_L_down">事故者姓名：</span>
                                    			<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtName}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">事故者身分證號：</span>
                                    			<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtIdnNo}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">事故者出生日期：</span>
                                    			<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtBrDateChineseString}" />
                                			</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr align="center">
                                <td class="issuetitle_L">
                        		<bean:define id="list" name="<%=ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE%>" />
                        		<display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >
                        		<display:column title="序號" headerClass="issuetitle_L" style="width: 5%;text-align: center;">
                            		<c:out value="${listItem_rowNum}" />&nbsp;
    	                		</display:column>
                                <display:column title="受款人姓名" headerClass="issuetitle_L" style="width: 15%">
                                    <c:if test='${listItem.benEvtRel eq "1"}'>
                                        <c:out value="${listItem.evtName}"/>&nbsp;
                                    </c:if>
                                    <c:if test='${listItem.benEvtRel ne "1"}'>
                                        <c:out value="${listItem.benName}"/>&nbsp;
                                    </c:if>
                                </display:column>
                        		<display:column title="受款人身分證號" headerClass="issuetitle_L" style="width: 11%">
                                    <c:if test='${listItem.benEvtRel eq "1"}'>
                        			    <c:out value="${listItem.evtIdnNo}" />&nbsp;
                                    </c:if>
                                    <c:if test='${listItem.benEvtRel ne "1"}'>
                        			    <c:out value="${listItem.benIdnNo}" />&nbsp;
                                    </c:if>
                        		</display:column>
                        		<display:column title="關 係" headerClass="issuetitle_L" style="width: 9%" >
                        			<c:out value="${listItem.benEvtRelName}" />&nbsp;
                        		</display:column>
                        		<display:column title="死亡日期" headerClass="issuetitle_L" style="width: 9%; text-align: center;">
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
                        		<display:column title="資料明細" headerClass="issuetitle_L" style="width: 25%;text-align: center;">
                        			<c:if test='${listItem.benEvtRel eq "1"}'> <!-- 如果是本人,新增繼承人應該都是disabled,由新增受款人的地方去新增 -->
                        				<c:if test='${not empty listItem.evtDieDate}'>
                        					<acl:checkButton buttonName="btnAddHeir">
                                				<input type="button" class="button_90" name="btnAddHeir" value="新增繼承人" onclick="$('method').value='doPrepareInsertInherit'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.SurvivorPayeeDataUpdateListForm.submit();" disabled="disabled">
                            				</acl:checkButton>	
                        				</c:if>
                        				<c:if test='${empty listItem.evtDieDate}'>
                        					<acl:checkButton buttonName="btnAddHeir">
                                				<input type="button" class="button_90" name="btnAddHeir" value="新增繼承人" onclick="$('method').value='doPrepareInsertInherit'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.SurvivorPayeeDataUpdateListForm.submit();" disabled="disabled">
                            				</acl:checkButton>	
                        				</c:if>
                        			</c:if>
                            		<c:if test='${listItem.benEvtRel ne "1"}'>
                        				<c:if test='${not empty listItem.benDieDate}'>
                        					<acl:checkButton buttonName="btnAddHeir">
                                				<input type="button" class="button_90" name="btnAddHeir" value="新增繼承人" onclick="$('method').value='doPrepareInsertInherit'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.SurvivorPayeeDataUpdateListForm.submit();">
                            				</acl:checkButton>	
                        				</c:if>
                        				<c:if test='${empty listItem.benDieDate}'>
                        					<acl:checkButton buttonName="btnAddHeir">
                                				<input type="button" class="button_90" name="btnAddHeir" value="新增繼承人" onclick="$('method').value='doPrepareInsertInherit'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.SurvivorPayeeDataUpdateListForm.submit();" disabled="disabled">
                            				</acl:checkButton>	
                        				</c:if>
                        			</c:if>
                        			
                        			<c:if test='${listItem.isHeir eq "N"}'>
                        				<acl:checkButton buttonName="btnUpdate">
                                			<input type="button" class="button" name="btnUpdate" value="修　改" onclick="$('method').value='doPrepareUpdate'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.SurvivorPayeeDataUpdateListForm.submit();">
                            			</acl:checkButton>
                        			</c:if>
                            		<c:if test='${listItem.isHeir eq "Y"}'>
                            			<acl:checkButton buttonName="btnUpdate">
                                			<input type="button" class="button" name="btnUpdateHeir" value="修改(繼)" onclick="$('method').value='doPrepareUpdateHeir'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.SurvivorPayeeDataUpdateListForm.submit();">
                            			</acl:checkButton>
                            		</c:if>
                            		
                            		<acl:checkButton buttonName="btnDelete">
                                        <% List detaillist = (List) request.getSession().getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE); %>
                                        <% if(detaillist.size()>1){ %>
                                        
											<c:if test='${empty listItem.closeDate}'>                                        
                                            	<c:if test='${listItem.hasHeir eq "Y"}'>
                                                	<input type="button" class="button" name="btnDelete" value="刪　除" disabled="disabled">
                                            	</c:if>
                                            	<c:if test='${listItem.hasHeir ne "Y"}'>
                                                	<c:if test='${listItem.delButtonQ4 gt "0"}'>
                                                    	<input type="button" class="button" name="btnDelete" value="刪　除" disabled="disabled">
                                                	</c:if>
                                                	<c:if test='${listItem.delButtonQ4 eq "0"}'>
                                                    	<input type="button" class="button" name="btnDelete" value="刪　除" onclick="if (confirm('<bean:message bundle="<%=Global.BA_MSG%>" key="msg.delete.confirm"/>')){location.href='<c:url value="/survivorPayeeDataUpdateList.do"/>?method=doDelete&baappbaseId=<c:out value="${listItem.baappbaseId}" />';}">
                                                	</c:if>
                                            	</c:if>
											</c:if>                                            
                                            <c:if test='${not empty listItem.closeDate}'>
                                                	<input type="button" class="button" name="btnDelete" value="刪　除" disabled="disabled">                                            
											</c:if>
                                            
                                        <%}else{ %>    
                                            <input type="button" class="button" name="btnDelete" value="刪　除" disabled="disabled">
                                        <%}%>    
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