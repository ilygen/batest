<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
 	<head>
    	<acl:setProgId progId="BAMO0D284C" />
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
    <html:form action="/survivorPayeeDataUpdateList" method="post">
      <fieldset>
      <legend>&nbsp;受款人可領金額登錄&nbsp;</legend>
        <div align="right" id="showtime">網頁下載時間：<func:nowDateTime /></div>

        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
          <tr>
              <td align="right" colspan="7">
                <html:hidden styleId="page" property="page" value="1" />
				<html:hidden styleId="method" property="method" value="" />
				<acl:checkButton buttonName="btnBack">
					<input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackList'; document.SurvivorPayeeDataUpdateListForm.submit();" />
				</acl:checkButton>
              </td>
           </tr>
          <tr>
            <td colspan="4">
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
                   <td width="25%"><span class="issuetitle_L_down">事故者出生日期：</span>
                   <c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtBrDateChineseString}" />
                   </td>
                   <td width="25%"><span class="issuetitle_L_down">事故者身分證號：</span>
                   <c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtIdnNo}" />
                   </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>

            <td colspan="4" align="center" class="table1">
              <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                <tr>
                  <td id="iss" width="33%"><span class="issuetitle_L_down">投保年資：</span>
                  	<c:out value="${SurvivorPayeeDataUpdateBadaprCase.nitrmYM}" />
                  </td>
                  <td id="iss" width="33%"><span class="issuetitle_L_down">實發年資：</span>
                  	<c:out value="${SurvivorPayeeDataUpdateBadaprCase.aplPaySeniYM}" />
                  </td>
                  <td id="iss" width="34%"><span class="issuetitle_L_down">平均月投保薪資(60個月)：</span>
                  	<fmt:formatNumber value="${SurvivorPayeeDataUpdateBadaprCase.insAvgAmt}"/>&nbsp;
                  </td>
                </tr>
                <tr>                  
                  <td id="iss"><span class="issuetitle_L_down">累計已領年金金額：</span>
                  	<fmt:formatNumber value="${SurvivorPayeeDataUpdateBadaprCase.annuAmt}" />
                  </td>
				  <td id="iss"><span class="issuetitle_L_down">職災補助金：</span>
					<fmt:formatNumber value="${SurvivorPayeeDataUpdateBadaprCase.ocAccaddAmt}" />
				  </td>
				  <td id="iss" colspan="3"><span class="issuetitle_L_down">核定金額：</span>
					<fmt:formatNumber value="${SurvivorPayeeDataUpdateBadaprCase.befIssueAmt}" />
				  </td>
                </tr>
              </table>
            </td>
          </tr>
		  
		  <tr>
            <td colspan="4" align="center" class="table1">
              <bean:define id="list" name="<%=ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE%>" />
              <table width="98%" cellpadding="3" cellspacing="5" class="px13">  
			    <tr>
                  <td colspan="4" class="issuetitle_L">核定年月：</span><c:out value="${badaprIssueYm}"/></td>
                </tr>              
                <tr align="center">
                  <td width="15%" class="issuetitle_L">給付年月</td>
                  <td width="15%" class="issuetitle_L">匯款匯費</td>
                  <td width="15%" class="issuetitle_L" colspan="2">受款人可領金額</td>
                </tr>
                <c:forEach var="listItem" items="${SurvivorPayeeDataUpdateBadaprListCase}" varStatus="status">
                <tr>
                  <td colspan="4" id="iss">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="px13">
                        <tr>
                          <td width="15%" rowspan="2" class="issuetitle_L_down">受款人序：<c:out value="${status.index+1}"/></td>
                          <td class="issuetitle_L_down" width="25%">受款人姓名：<c:out value="${listItem.benName}"/></td>
                          <td class="issuetitle_L_down" width="60%">關係：<c:out value="${listItem.benEvtRel}"/></td>
                        </tr>
                        <tr>
                          <td rowspan="2" class="issuetitle_L_down">受款人身分證號：<c:out value="${listItem.benIdnNo}"/></td>
						  <td class="issuetitle_L_down">繼承自受款人：<c:out value="${listItem.inheritFrom}"/>&nbsp;</td>
                        </tr>
                      </table>
                  </td>
                </tr>
                
                <logic:iterate id="payYmList" collection="${listItem.payYmList}">
                <tr>
				  <td class="issueinfo_C"><c:out value="${payYmList.payYm}"/>&nbsp;</td>
                  <td class="issueinfo_R"><fmt:formatNumber value="${payYmList.mitRate}" />&nbsp;</td>
                  <td class="issueinfo_R"><fmt:formatNumber value="${payYmList.issueAmt}" />&nbsp;</td>    
                </tr>
                </logic:iterate>
                </c:forEach>
                <tr>
                	<td colspan="4"></td>
                </tr>

              </table>
            </td>
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