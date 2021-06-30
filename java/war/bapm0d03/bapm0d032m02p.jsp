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
    function doPrint() {
       window.print();
    }
    Event.observe(window, 'load', initAll, false);
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    </script>
</head>
<body>
 	<bean:define id="payTitle" name="<%=ConstantKey.PAYMENT_PROCESS_QUERY_FORM%>" />
     <div align="center">
        <p align="center">
            <font size="5" style="letter-spacing: 8px">
                <strong>繳款單明細查詢作業(利息試算明細)</strong>
            </font>
        </p>
       	
        <table border="0" align="center" cellpadding="2" cellspacing="2">
          		<tr align="center">
			       <p>
					<strong>身分證號：</strong><c:out value="${payTitle.idn}" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>繳款單號：</strong><c:out value="${payTitle.paymentNo}" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>類別：</strong><c:out value="${payTitle.caseType}" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</p>
					<p>
					<strong>繳款期限：</strong><c:out value="${payTitle.paymentDateLine}" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>應繳總額：</strong><c:out value="${payTitle.totAmt}" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>應繳總本金：</strong><c:out value="${payTitle.payTotAmt}" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</p>
					<p>
					<strong>總利息：</strong> <c:out value="${payTitle.totInterst}" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>總執行費用：</strong> <c:out value="${payTitle.totExec}" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>期數：</strong>  <c:out value="${payTitle.totStage}" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</p>
					<p>
					<strong>移送案號：</strong>   <c:out value="${payTitle.classNo}" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</p>
					<p>
					<strong>地址：</strong>  <c:out value="${payTitle.addr}"  />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</p>
					<p>
					<strong>本金期數：</strong>   <c:out value="${payTitle.totAmtStage}"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>利息期數：</strong>   <c:out value="${payTitle.interestStage}"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>每期攤還本金：</strong>  <c:out value="${payTitle.stageAmt}"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</p>
					<p>
					<strong>利息標的金額：</strong>    <c:out value="${payTitle.totTrgAmt}"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<strong>利息試算期數：</strong>    <c:out value="${payTitle.interestTryStage}"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</p>
        		</tr>
        		<tr>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                </tr>
               
         		<tr align="center">
                    <td width="150"><strong>受理編號</strong></font></td>
                    <td width="150"><strong>受款人序</strong></font></td>
                    <td width="150"><strong>姓名</strong></font></td>
                    <td width="150"><strong>核定年月</strong></font></td>
                    <td width="150"><strong>給付年月</strong></font></td>
                    <td width="150"><strong>應收金額</strong></font></td>
                    <td width="150"><strong>未收餘額</strong></font></td>
                    <td width="150"><strong>應繳本金</strong></font></td>
                    <td width="150"><strong>主辦案</strong></font></td>
                    <td width="150"><strong>銷帳序</strong></font></td>
                </tr>
                <tr>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                </tr>
                <logic:iterate id="listItem" indexId="i"  name="<%=ConstantKey.PAYMENT_PROCESS_DETAIL_CASE_LIST%>">
                <tr> 
                    <td align="center"><c:out value="${listItem.apno}" /></td>
                    <td align="center"><c:out value="${listItem.seqNo}" /></td>
                    <td align="center"><c:out value="${listItem.benName}" /></td>
                    <td align="center"><c:out value="${listItem.issuYm}" /></td>
                    <td align="center"><c:out value="${listItem.payYm}" /></td>
                    <td align="right"><fmt:formatNumber value="${listItem.recAmt}" /></td>
                      <td align="right"><fmt:formatNumber value="${listItem.recRem}" /></td>
                    <td align="right"><fmt:formatNumber value="${listItem.payAmt}" /></td>
                    <c:if test="${listItem.mapnoMk eq '0'}">
							<td align="center"><c:out value="N" /></td>
			        </c:if>
			        <c:if test="${listItem.mapnoMk eq '1'}">
							<td align="center"><c:out value="Y" /></td>
					</c:if>
					 <td align="center"><c:out value="${listItem.writeoffSeqNo}" /></td>
                </tr>
                </logic:iterate>
               <tr>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                </tr>
                <tr align="center">
                    <td width="121"><strong>期數</strong></font></td>
                    <td width="121"><strong>期初本金餘額</strong></font></td>
                    <td width="121"><strong>期末還款金額</strong></font></td>
                    <td width="121"><strong>期末本金餘額</strong></font></td>
                    <td width="121"><strong>利率</strong></font></td>
                    <td width="121"><strong>利息起算日</strong></font></td>
                    <td width="121"><strong>利息結算日</strong></font></td>
                    <td width="121"><strong>天數</strong></font></td>
                    <td width="121"><strong>試算利息</strong></font></td>
                    <td width="123"><strong>調整利息</strong></font></td>
                </tr>
                <tr>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                </tr>
                <logic:iterate id="listItemj" indexId="j"  name="<%=ConstantKey.PAYMENT_INTEREST_DETAIL_CASE_LIST%>">
                <tr> 
                     <c:if test="${listItemA.nowStage ne '0'}">
						<td align="center"><c:out value="${listItemj.nowStage}" />&nbsp;</td>
						<td align="right"><fmt:formatNumber value="${listItemj.accAmt}" /></td>
					 	<td align="right"><fmt:formatNumber value="${listItemj.rePayAmt}" />&nbsp;</td>
					 	<td align="right"><fmt:formatNumber value="${listItemj.accAmtEnd}" /></td>
				    </c:if>
                      <c:if test="${listItemA.nowStage eq '0'}">
							<td align="center"><c:out value="" />&nbsp;</td>
					     	<td align="center"><c:out value="" />&nbsp;</td>
					  		<td align="center"><c:out value="" />&nbsp;</td>
				      </c:if>
				      <c:if test="${listItemA.iRate ne '0'}">
				      		<td align="center"><c:out value="${listItemj.iRate}" />&nbsp;</td>
					  </c:if>
				       <c:if test="${listItemA.iRate eq '0'}">
					        <td align="center"><c:out value="_" />&nbsp;</td>
				       </c:if>
				       <c:if test="${listItemA_rowNum eq '1'}">
				       		<td align="center"><c:out value="${listItemj.interestBegDate}" />&nbsp;</td>
					   </c:if>
				       <c:if test="${listItemA_rowNum ne '1'}">
				       		<td align="center"><c:out value="${listItemj.interestBegDate}" />&nbsp;</td>
				       </c:if>		
				      <td align="center"><c:out value="${listItemj.interestEndDate}" />&nbsp;</td>                      	
				      <td align="center"><c:out value="${listItemj.dateBetween}" />&nbsp;</td>   
				      <td align="right"><fmt:formatNumber value="${listItemj.tryInterest}" />&nbsp;</td>       
				      <td align="right"><fmt:formatNumber value="${listItemj.adjInterest}" />&nbsp;</td>              
                </tr>
                </logic:iterate>
               <tr>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                    <td><hr size="1" noshade></td>
                </tr>
        <p>&nbsp;</p>
        </table>
        <div id="excludePrint" align="center" style="display: inline;">
            <input type="button" id="btnPrint" name="btnPrint" value="列印" onclick="doPrint();">
            &nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" id="btnClose" name="btnClose" value="關閉視窗" onclick="window.close();">
        </div>
    </div>
    <p></p>
</body>
<HEAD>
	<meta HTTP-EQUIV="Pragma" content="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<meta HTTP-EQUIV="Expires" CONTENT="-1">
</HEAD>
</html:html>
