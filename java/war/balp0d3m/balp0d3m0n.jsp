<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">



<html:html lang="true">
	<head>
	    <acl:setProgId progId="BALP0D3M0N" />
	    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
	    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
	    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
	    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
	    <html:javascript formName="MonthlyRpt22Form" page="1" />
	    <script language="javascript" type="text/javascript">
	    <!--
	    
	    <%-- ] ... end --%>
	    
	    <%-- 畫面清空重設 --%>
	    <%-- begin ... [ --%>
	    function doBack(){
	        var theForm = document.MonthlyRpt22Form;
	        theForm.action = "<c:url value='/printMonthlyRpt22.do?parameter=printMonthlyRpt22&method=doBack'/>";
  			theForm.submit();         
	    }
	    <%-- ] ... end --%>
	    
	    function initAll() {
	        dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");      
	    }
	
	    
	    -->
	    </script>
	</head>
    
    <body scroll="no">
    <div id="mainContent">
        
        <%@ include file="/includes/ba_header.jsp"%>
   		<%@ include file="/includes/ba_menu.jsp"%>
 
                <div id="main" class="mainBody"> 
                <html:form action="/printMonthlyRpt22" method="post" onsubmit="return validateMonthlyRpt22Form(this);">
                    <fieldset>
                        <legend>&nbsp;月處理核定報表彙整一覽表&nbsp;</legend>
                        <div align="right" id="showtime">
                               	 網頁下載時間：民國&nbsp;<func:nowDateTime />
            			</div>
            			
                        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td align="right"><input name="btnBack" type="button" class="button" value="返　回" onClick="javascript:doBack();" /></td>
                                
                            </tr>
                            <tr>
                                <td>
                                    <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                    	
                                    	<tr>
                                            <td width="34%"><span class="issuetitle_L_down">給付別：</span><c:if test="${pay_Code=='L'}">老年年金</c:if><c:if test="${pay_Code=='K'}">失能年金</c:if><c:if test="${pay_Code=='S'}">遺屬年金</c:if></td>
                                            <td width="33%"><span class="issuetitle_L_down">核定年月：</span><c:out value="${issu_Ym}"/></td>
                                            <td width="33%"><span class="issuetitle_L_down">核定日期：</span><c:out value="${chkDate}"/></td>
                                         	
                                        </tr>
                                       
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="9" align="center" class="table1">
                                    <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                                        <tr>
                                            <td colspan="3" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;已產生之報表</td>
                                        </tr>
                                        <c:forEach var="row" begin="0" end="${rtnValsRow}" step="1"> 
	                                        <tr>
	            								<c:forEach var="col" begin="0" end="${rtnValsCol}" step="1">
	            									<td id="iss" width="33%"><c:out value='${rtnVals[row][col]}'/></td>
	            								</c:forEach>
	            							</tr>
            							</c:forEach>
                                        <tr>
                                            <td id="iss" colspan="3"><span class="issuetitle_L_down">報表共計：<c:out value="${reports}"/>張</span></td>
                                        </tr>
                                        <tr>
                                            <td colspan="3"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <p></p>
                    </html:form>
                </div>

        
        <%@ include file="/includes/ba_footer.jsp"%>
	</div>
    </body>
</html:html>