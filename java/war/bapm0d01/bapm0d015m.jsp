<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPM0D015M" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
  	<script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
                    
    }      
   	
    function checkFields(){
		var msg = "";
    	var objs = document.getElementsByName("payInterest");
    	var objAmts = document.getElementsByName("execAmt");
    	interestList = new Array(0);
	    execAmtList= new Array(0);
    	for (i = 0; i < objs.length; i++) {
	    	temp = new Array(1);
	        temp[0] = objs[i].value;
	        interestList = interestList.concat(temp);
	                
	        tempAmts = new Array(1);
	        tempAmts[0] = objAmts[i].value;
	        execAmtList = execAmtList.concat(tempAmts);
	    }
	    $("interstList").value = interestList;
	    $("execList").value = execAmtList;
	        
	    if(msg != ""){
	      alert(msg);
	      return false;
	    }else{
	      return true;
	    }    	  
    }
    
    function initAll(){
    
    	
    }
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
    	<html:form action="/paymentProcessList" method="post" onsubmit="return validatePaymentProcessQueryForm(this);">
        <fieldset>
            <legend>&nbsp;繳款單分配明細&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
            <bean:define id="payTitle" name="<%=ConstantKey.PAYMENT_PROCESS_QUERY_FORM%>" />
                <tr>
                    <td colspan="2" align="right">
                    	<html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="totExec" property="totExec"  />
                        <html:hidden styleId="execList" property="execList"  />
                    	<html:hidden styleId="totInterst" property="totInterst"/>
                    	<html:hidden styleId="interstList" property="interstList"/>
                    	<acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button"  class="button" value="返　回" onclick="javascript:history.back();" />&nbsp;
                        </acl:checkButton>     	                                        
                    </td>
                </tr>
                    
                <tr>
                    <td colspan="11" class="issuetitle_L">
                       <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                               <td width="25%">
	                            	<span class="issuetitle_L_down">身分證號：</span>
	                            	<c:out value="${payTitle.idn}" />
	                           </td>
			                  
  			           			<td width="25%">
                                    <span class="issuetitle_L_down">期數：</span>
                                    <c:out value="${payTitle.totStage}" />
  			           			</td>
  			           			<td colspan="2"/>
                            </tr>
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">繳款單號：</span>
                                    <c:out value="${payTitle.paymentNo}" />
  			           			</td>	
  			           			<td width="25%">
                                    <span class="issuetitle_L_down">移送案號：</span>
                                    <c:out value="${payTitle.classNo}" />
  			           			</td>
  			           			<td colspan="2"/>
                            </tr>
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">應繳總額：</span>
                                    <c:out value="${payTitle.totAmt}" />
  			           			</td>	
  			           			<td width="25%">
                                    <span class="issuetitle_L_down">應繳總本金：</span>
                                    <c:out value="${payTitle.payTotAmt}" />
  			           			</td>
  			           			<td width="25%">
                                    <span class="issuetitle_L_down">本金期數：</span>
                                    <c:out value="${payTitle.totAmtStage}" />
  			           			</td>
  			           			<td width="25%">
                                    <span class="issuetitle_L_down">每期攤還本金：</span>
                                    <c:out value="${payTitle.stageAmt}" />
  			           			</td>
                            </tr>
                        </table>
                    </td>
                </tr>
 				<tr align="center">
	                    <td>
	                        <bean:define id="list" name="<%=ConstantKey.PAYMENT_ASSIGN_LAST_CASE_LIST%>" />
	                        <display:table name="pageScope.list" id="listItem">
	                            <display:column title="期數" style="width:5%; text-align:center;">
	                           		 <c:out value="${listItem.nowStagForShow}" />&nbsp;
	                            </display:column>
	                            <display:column title="繳款單號 " style="width:25%; text-align:center;">
	                                <c:out value="${listItem.paymentNoDetail}" />&nbsp;
	                            </display:column>
	                            <display:column title="受理編號" style="width:25%; text-align:center;">
	                                 <c:out value="${listItem.apno}" />
	                            </display:column>
	                            <display:column title="核定年月" style="width:15%; text-align:center;">
		                               <c:out value="${listItem.issuYm}" />
		                        </display:column>
	                            <display:column title="給付年月" style="width:15%; text-align:center;">
		                                  <c:out value="${listItem.payYm}" />
		                        </display:column>
	                            <display:column title="應繳本金 " style="width:15%; text-align:right;">
	                            		 <c:out value="${listItem.payAmt}" />
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