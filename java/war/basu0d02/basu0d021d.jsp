<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BASU0D021D" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="ExecutiveSupportCloseListForm" page="1" />
    <script language="javascript" type="text/javascript">
    	// Checkbox 全選 / 全部取消 的處理
		// 用於頁面上僅有一排checkBox
		// begin ... [
		function selectAllByType(obj, elements) {
		
		    if (typeof obj == 'string')
		        obj = document.getElementById(obj);
		
		    if (typeof elements == 'string')
		        elements = document.getElementsByName(elements);
		    
		   for (i = 0; i < elements.length; i++) {
		   	if(!elements[i].disabled){
		          elements[i].checked = obj.checked;
		        }
		    }
		}
		function checkRequireFields(){
			var a=0;
			elements = document.getElementsByName("checkBox");
			obj = document.getElementById("ckbox01");
			 for (i = 0; i < elements.length; i++) {
			   	if(elements[i].checked){
			          a=1;
			          break;
			       }
		    }
		    if(a>0){
			     	return true;
			     }else{
			     	alert("請勾選欲銷案的行政支援記錄");
			        return false;
			     }
		}
		
		
		// ] ... end
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/executiveSupportCloseList" method="post" onsubmit="return validateExecutiveSupportCloseListForm(this);">
        
        <fieldset>
            <legend>&nbsp;行政支援記錄銷案&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="9">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="index" property="index" value="" />
                        <acl:checkButton buttonName="btnUpdate">
                            <input type="button" name="btnUpdate" class="button" value="確　 認" <c:if test="${buttonStatus=='true'}">disabled="true"</c:if> onclick="$('page').value='1'; $('method').value='doClose'; if (document.ExecutiveSupportCloseListForm.onsubmit()&&checkRequireFields()){document.ExecutiveSupportCloseListForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <bean:define id="qryCondFrom" name="<%=ConstantKey.EXECUTIVE_SUPPORT_CLOSE_QUERY_FORM %>"/> 
                        <c:if test="${(qryCondFrom.qryFromWhere eq 'BASU0D020D')}">
                            <acl:checkButton buttonName="btnBack">
                                <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackClean'; document.ExecutiveSupportCloseListForm.submit();" />
                            </acl:checkButton>
                        </c:if>
                        <c:if test="${(qryCondFrom.qryFromWhere eq 'BACO0D013Q')}">
                            <acl:checkButton buttonName="btnBack">
                                <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackForOldAgePaymentReview'; document.ExecutiveSupportCloseListForm.submit();" />
                            </acl:checkButton>
                        </c:if>
                        <c:if test="${(qryCondFrom.qryFromWhere eq 'BACO0D113Q')}">
                            <acl:checkButton buttonName="btnBack">
                                <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackForDisabledPaymentReview'; document.ExecutiveSupportCloseListForm.submit();" />
                            </acl:checkButton>
                        </c:if>
                        <c:if test="${(qryCondFrom.qryFromWhere eq 'BACO0D213Q')}">
                            <acl:checkButton buttonName="btnBack">
                                <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackForSurvivorPaymentReview'; document.ExecutiveSupportCloseListForm.submit();" />
                            </acl:checkButton>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td colspan="9" class="issuetitle_L">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${ExecutiveSupportCloseCase.apNoStr}" />
                                </td>
                                 <td width="25%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:out value="${ExecutiveSupportCloseCase.payKindStr}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">審核人員：</span>
                                    <c:out value="${ExecutiveSupportCloseCase.chkMan}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">核定年月：</span>
                                    <c:out value="${ExecutiveSupportCloseCase.issuYm}" />
                                </td>
                            </tr>
                            <tr>
                               <td colspan="2">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${ExecutiveSupportCloseCase.evtName}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${ExecutiveSupportCloseCase.evtIdnNo}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${ExecutiveSupportCloseCase.evtBrDate}" />
                                </td>
                                
                                
                                <td>&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td colspan="9" class="issuetitle_L">
                     <bean:define id="list" name="<%=ConstantKey.EXECUTIVE_SUPPORT_CLOSE_LIST_CASE%>" />
                     	
                     	<display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                        	<c:choose>
			                   <c:when test='${buttonStatusTemp == "true"}'>
			                   	<c:set var="isdisabled" value="disabled"/>
			                   </c:when>
			                   <c:otherwise>
			                   	<c:set var="isdisabled" value=""/>
			                   </c:otherwise>
			                </c:choose>
					        <display:column headerClass="issuetitle_L" class="issueinfo_C" title="<input id='checkbox01' name='checkbox01' type='checkbox' ${isdisabled} onClick='selectAllByType(this, \"checkBox\");'>全選<br> 銷案" style="width:7%; text-align:center;">
                             <c:choose>
                            <c:when test="${buttonStatus=='true'}">
	                    		<html:multibox styleId="checkBox" property="checkBox" value="${listItem_rowNum}" disabled="true"/>
	                    	</c:when>
	                        <c:when test="${listItem.letterType=='21' || listItem.letterType=='22' || listItem.letterType=='23' || listItem.letterType=='24' || 
	                        listItem.letterType=='30' || listItem.letterType=='31' || listItem.letterType=='40' || listItem.payMk=='Y'}">
	                    		<html:multibox styleId="checkBox" property="checkBox" value="${listItem_rowNum}" disabled="true"/>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<html:multibox styleId="checkBox" property="checkBox" value="${listItem_rowNum}" disabled="false" />
	                    	</c:otherwise>
                    		</c:choose>
                        </display:column>  
                        <display:column headerClass="issuetitle_L" title="承辦日期" style="width:7%; text-align:center;">
                            <c:out value="${listItem.proDate}" />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="承辦人員" style="width:7%; text-align:center;">
                            <c:out value="${listItem.promoteUser}" />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="處理函別" style="width:8%; text-align:left;">
                            <c:out value="${listItem.paramName}" />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="序號" style="width:6%; text-align:center;">
                            <c:out value="${listItem.seqNo}" />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="函別內容一/救濟事由" style="width:20%; text-align:left;">
                            <c:out value="${listItem.ndomkName1}" />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="函別內容二/救濟事由" style="width:20%; text-align:left;">
                            <c:out value="${listItem.ndomkName2}" />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="主　旨" style="width:25%; text-align:left;">
                            <c:out value="${listItem.note}" />&nbsp;
                        </display:column>
                        </display:table>
                    <td>
                </tr>
                <tr>
                    <td colspan="9" align="center" class="word12"><br>
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
