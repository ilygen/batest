<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPM0D032M" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/queryCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
                    
    }   
    Event.observe(window, 'load', initAll, false);
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    </script>
</head>
<body scroll="no">

    <div id="mainContent">

        <%@ include file="/includes/ba_header.jsp"%>

        <%@ include file="/includes/ba_menu.jsp"%>

        <div id="main" class="mainBody">
            <html:form action="/paymentProgressQuery" method="post" onsubmit="">
                <html:hidden styleId="page" property="page" value="1" />
                <html:hidden styleId="method" property="method" value="" />
                <fieldset>
                    <legend>
                        &nbsp;繳款單明細查詢作業&nbsp;
                    </legend>
                    <div align="right" id="showtime">
                        網頁下載時間：民國&nbsp;<func:nowDateTime />
                    </div>
                     <bean:define id="payTitle" name="<%=ConstantKey.PAYMENT_PROCESS_QUERY_FORM%>" />
                    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="0" class="px13">
                        <tr>
		                    <td colspan="2" align="right">
		                    	
		                         <acl:checkButton buttonName="btnPrint">
		                          	<input type="button" name="btnPrint" class="button_90" value="列印畫面" onclick="javascript:window.open('<c:url value="/bapm0d03/bapm0d032m02p.jsp"/>');">   	                                        
		                    	</acl:checkButton>   
		                    	<acl:checkButton buttonName="btnBack">
		                            <input name="btnBack" type="button" class="button" value="返　回"  onclick="$('page').value='1'; $('method').value='doBack'; document.PaymentProgressQueryForm.submit();" />&nbsp;
		                        </acl:checkButton>        	                                        
		                    </td>
		                </tr>
                        <tr>
				            <td colspan="11" class="issuetitle_L">
				             
				              <table class="px13" width="100%" border="0" cellspacing="2" cellpadding="2">
				                <tr>
				                   <td width="15%">
		                            	<span class="issuetitle_L_down">身分證號：</span>
		                            	<c:out value="${payTitle.idn}" />
		                           </td>
				                   <td width="25%">
		                            	<span class="issuetitle_L_down">繳款單號：</span>
		                            	<c:out value="${payTitle.paymentNo}" />
		                           </td>
				                </tr>
				              </table>
				            </td>
				         </tr>    
		                <tr>
		                    <td colspan="11" class="issuetitle_L">
		                       <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
		                            <tr>
		                                <td width="15%">
		                                    <span class="issuetitle_L_down">類別：</span>
		                                     <c:out value="${payTitle.caseType}" />
		                                </td>
		                            
		                                <td width="25%">
		                                	 <span class="issuetitle_L_down">繳款期限：</span>
		                        			 <c:out value="${payTitle.paymentDateLine}" />&nbsp;
					           				
					           			</td>
		                            		<td width="20%">
		                                	 <span class="issuetitle_L_down">應繳總額：</span>
		                        			 <c:out value="${payTitle.totAmt}" />&nbsp;
					           				
					           			</td>
		                                <td width="35%">
		                                    <span class="issuetitle_L_down">應繳總本金：</span>
		                                    <c:out value="${payTitle.payTotAmt}" />
		                        	    </td>
		                                
		                            </tr>
		                            <tr>
		                                <td width="15%">
		                                    <span class="issuetitle_L_down">總利息：</span>
		                                     <c:out value="${payTitle.totInterst}" />
		                                </td>
		                            
		                                <td width="25%">
		                                	 <span class="issuetitle_L_down">總執行費用：</span>
		                        			 <c:out value="${payTitle.totExec}" />
					           		     </td>
		                                <td width="20%">
		                                    <span class="issuetitle_L_down">期數：</span>
		                                    <c:out value="${payTitle.totStage}" />
		                        	    </td>
		                            </tr>
		                            <tr>
		                                <td colspan="4">
		                                    <span class="issuetitle_L_down">移送案號：</span>
		                                    <c:out value="${payTitle.classNo}" />
					           			</td>
		                            </tr>
		                            <tr>
		                                <td colspan="4">
		                                    <span class="issuetitle_L_down">地址：</span>
		                                    <c:out value="${payTitle.zipCode}" /> <c:out value="${payTitle.addr}"  />
		                                </td>
		                            </tr>
		                            <tr>
		                                <td width="15%">
		                                	<span class="needtxt">＊</span>
		                                    <span class="issuetitle_L_down">本金期數：</span>
		                                    <c:out value="${payTitle.totAmtStage}"/>
					           			</td>
					           			
		                                <td width="25%">
		                               		 <span class="needtxt">＊</span>
		                                    <span class="issuetitle_L_down">利息期數：</span>
		                                    <c:out value="${payTitle.interestStage}"/>
					           			</td>
					           			 <td width="20%">
					           			 	<span class="needtxt">＊</span>
		                                    <span class="issuetitle_L_down">每期攤還本金：</span>
		                                    <c:out value="${payTitle.stageAmt}"/>
					           			</td>
		                            </tr>
		                            <tr>
		                                <td width="15%">
		                                	<span class="issuetitle_L_down">利息標的金額：</span>
		                                    <c:out value="${payTitle.totTrgAmt}"/>
					           			</td>
					           			
		                                <td width="25%">
		                               		 <span class="issuetitle_L_down">利息試算期數：</span>
		                                    <c:out value="${payTitle.interestTryStage}"/>
					           			</td>
		                            </tr>
		                             
		                        </table>
		                       
		                       </td>
		                       
		                    </tr>
		                	<tr>
	                            <td colspan="14">
	                                <table border="0" cellpadding="0" cellspacing="0" class="issuetitle_L" id="tabsJ">
	                                    
	                                    <tr>
	                                        <td>
	                                            
	                                            <a href='<c:url value="/paymentProgressQuery.do?method=doConfirmData" />' title="連結至：分期明細" target="_self" class="needtxt"><span>分期明細</span></a>
	                                            <a href='<c:url value="/paymentProgressQuery.do?method=doAssignData" />' title="連結至：各期本金分配明細" target="_self" class="needtxt"><span>各期本金分配明細</span></a>
	                                        	<h5><a>利息試算明細</a></h5>
	                                        </td>
	                                    </tr>
	                                </table>
	                            </td>
                        	</tr>
                        	<tr align="center">
					     				   <td>
					                        <bean:define id="list" name="<%=ConstantKey.PAYMENT_PROCESS_DETAIL_CASE_LIST%>" />
					                        <display:table name="pageScope.list" id="listItem" >
					                          <c:if test="${listItem.writeoffSeqNo ne ''}">
					                            <display:column title="受理編號" style="width:12%; text-align:center;">
					                                <c:out value="${listItem.apno}" />&nbsp;
					                            </display:column>
					                            <display:column title="受款人序 " style="width:12%; text-align:center;">
					                                <c:out value="${listItem.seqNo}" />&nbsp;
					                            </display:column>
					                            <display:column title="姓名" style="width:10%; text-align:center;">
					                                <c:out value="${listItem.benName}" />
					                            </display:column>
					                            <display:column title="核定年月" style="width:10%; text-align:right;">
					                                <c:out value="${listItem.issuYm}" />
					                            </display:column>
					                            <display:column title="給付年月" style="width:10%; text-align:right;">
					                                <c:out value="${listItem.payYm}" />
					                            </display:column>
					                            <display:column title="應收金額" style="width:10%; text-align:right;">
					                                <fmt:formatNumber value="${listItem.recAmt}" />
					                            </display:column>
					                             <display:column title="未收餘額" style="width:10%; text-align:right;">
					                                 <fmt:formatNumber value="${listItem.recRem}" />
					                            </display:column>
					                             <display:column title="應繳本金" style="width:10%; text-align:right;">
					                                 <fmt:formatNumber value="${listItem.payAmt}" />
					                            </display:column>
					                            <c:if test="${listItem.mapnoMk eq '0'}">
						                            <display:column title="主辦案" style="width:12%; text-align:center;">
						                                <input type="radio" name="mapnoMk" value='<%=(listItem_rowNum.intValue())%>' disabled="disabled" id="mapnoMk"/>                       
						                            </display:column>
					                            </c:if>
					                             <c:if test="${listItem.mapnoMk eq '1'}">
						                            <display:column title="主辦案" style="width:12%; text-align:center;">
						                                 <input type="radio" id="mapnoMk" value='<%=(listItem_rowNum.intValue())%>' disabled="disabled" name="mapnoMk" checked />                               
						                            </display:column>
					                            </c:if>
					                            <display:column title="銷帳序" style="width:4%; text-align:center;">
					                            	<c:out value="${listItem.writeoffSeqNo}" />
					       	                    </display:column>
					       	                    </c:if>
					                        </display:table>
					                    </td>
					                </tr>
					                <tr>
					                    <td>
					                        <table width="100%" border="0" cellpadding="3" cellspacing="5" class="px13">
					                            <tr>
                               					 <td width="84%" class="issuetitle_L"><span class="systemMsg">▲</span>利息試算明細</td>
												 </tr>
                        					</table>
                    					</td>
                					</tr>
							<tr align="center">
					     				   <td>
					                        <bean:define id="listInterest" name="<%=ConstantKey.PAYMENT_INTEREST_DETAIL_CASE_LIST%>" />
					                        <display:table name="pageScope.listInterest" id="listItemA" >
					                        <c:if test="${listItemA.nowStage ne '0'}">
						                        <display:column title="期數" style="width:5%; text-align:center;">
					                                <c:out value="${listItemA.nowStage}" />&nbsp;
					                            </display:column>
					                            <display:column title="期初本金餘額" style="width:15%; text-align:right;">
					                                 <fmt:formatNumber value="${listItemA.accAmt}" />
					                            </display:column>
					                            <display:column title="期末還款金額  " style="width:15%; text-align:right;">
					                                 <fmt:formatNumber value="${listItemA.rePayAmt}" />&nbsp;
					                            </display:column>
					                            <display:column title="期末本金餘額" style="width:15%; text-align:right;">
				                            	 <fmt:formatNumber value="${listItemA.accAmtEnd}" />
					                           	</display:column>
				                            </c:if>
				                            <c:if test="${listItemA.nowStage eq '0'}">
						                        <display:column title="期數" style="width:5%; text-align:center;">
					                                <c:out value="" />&nbsp;
					                            </display:column>
					                            <display:column title="期初本金餘額" style="width:15%; text-align:right;">
					                                  <c:out value="" />&nbsp;
					                            </display:column>
					                            <display:column title="期末還款金額  " style="width:15%; text-align:right;">
					                                <c:out value="" />&nbsp;	
					                            </display:column>
					                            <display:column title="期末本金餘額" style="width:15%; text-align:right;">
				                            		<c:out value="" />&nbsp;	
					                           	</display:column>
				                            </c:if>
				                            <c:if test="${listItemA.iRate ne '0'}">
					                            <display:column title="利率" style="width:5%; text-align:center;">
					                                <c:out value="${listItemA.iRate}" />
					                            </display:column>
				                            </c:if>
				                            <c:if test="${listItemA.iRate eq '0'}">
					                            <display:column title="利率" style="width:5%; text-align:center;">
					                                <c:out value=" _"/>
					                            </display:column>
				                            </c:if>
				                            <c:if test="${listItemA_rowNum eq '1'}">
					                            <display:column title="利息起算日" style="width:12%; text-align:center;">
					                                 <c:out value=" ${listItemA.interestBegDate}"/>
					                            </display:column>
				                            </c:if>
				                            <c:if test="${listItemA_rowNum ne '1'}">
				                            	<display:column title="利息起算日" style="width:12%; text-align:center;">
				                            		<c:out value=" ${listItemA.interestBegDate}"/>
					                            </display:column>
				                            </c:if>
				                            <display:column title="利息結算日" style="width:13%; text-align:center;">
				                                <c:out value=" ${listItemA.interestEndDate}"/>
				                            </display:column>
				                             <display:column title="天數" style="width:6%; text-align:center;">
				                                 <c:out  value="${listItemA.dateBetween}" />
				                            </display:column>
				                             <display:column title="試算利息" style="width:7%; text-align:right;">
				                                   <fmt:formatNumber value="${listItemA.tryInterest}" />
				                            </display:column>
				                             <display:column title="調整利息" style="width:7%; text-align:right;">
				                                <c:out  value="${listItemA.adjInterest}" />
				                            </display:column>
	                                       </display:table>
					                    </td>
					                </tr>
		                       	 <tr>
		                            <td colspan="9">&nbsp;</td>
		                        </tr>                          				             
                    </table>
                </fieldset>
            </html:form>
        </div>
    </div>

    <%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
