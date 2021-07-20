<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BASU0D012C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" /><script type='text/javascript' src='<c:url value="/dwr/interface/executiveCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/interface/executiveCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="ExecutiveSupportMaintListForm" page="1" />
    <script language="javascript" type="text/javascript">
    
    	function getPayMk(proDte, hpNo){
    		
    		executiveCommonAjaxDo.getBabanPayMk("<c:out value="${ExecutiveSupportMaintCase.apNo}" />","<c:out value="${ExecutiveSupportMaintCase.issuYm}" />",proDte,hpNo,getStatus)
    		
    	}
    	
    	function getStatus(status){
    		 var msg = "";

    		if(status=='Y'){
        		msg += '已付費不可修改';
        	}
        	
        	if (msg != "") {
	            alert(msg);
	            return false;
	        }
	        else {
	            document.ExecutiveSupportMaintListForm.submit();
	        }        
    	}
    	
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/executiveSupportMaintList" method="post" onsubmit="return validateExecutiveSupportMaintListForm(this);">
        
        <fieldset>
            <legend>&nbsp;行政支援記錄維護&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="9">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="index" property="index" value="" />
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.ExecutiveSupportMaintListForm.submit();" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" class="issuetitle_L">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">         
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${ExecutiveSupportMaintCase.apNoStr}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:out value="${ExecutiveSupportMaintCase.payKindStr}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">審核人員：</span>
                                    <c:out value="${ExecutiveSupportMaintCase.chkMan}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">核定年月：</span>
                                    <c:out value="${ExecutiveSupportMaintCase.issuYm}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${ExecutiveSupportMaintCase.evtName}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${ExecutiveSupportMaintCase.evtIdnNo}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${ExecutiveSupportMaintCase.evtBrDate}" />
                                </td>
                                
                                <td>&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td colspan="8" class="issuetitle_L">
                        <bean:define id="list" name="<%=ConstantKey.EXECUTIVE_SUPPORT_MAINT_LIST_CASE%>" />
                        
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">  
                        <display:column headerClass="issuetitle_L" title="承辦日期" style="width:9%; text-align:center;">
                            <c:out value="${listItem.proDate} " />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="承辦人員" style="width:9%; text-align:center;">
                            <c:out value="${listItem.promoteUser} " />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="處理函別" style="width:9%; text-align:left;">
                            <c:out value="${listItem.letterType} - ${listItem.paramName} " />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="序號" style="width:7%; text-align:center;">
                            <c:out value="${listItem.seqNo} " />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="函別內容一/救濟事由" style="width:20%; text-align:left;">
                            <c:out value="${listItem.ndomk1}" />
                            <c:if test="${(listItem.ndomk1 ne '')and(listItem.ndomkName1 ne '')}">
                            -
                            </c:if>
                            <c:out value="${listItem.ndomkName1} " />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="函別內容二/救濟事由" style="width:20%; text-align:left;">
                            <c:out value="${listItem.ndomk2}" />
                            <c:if test="${(listItem.ndomk2 ne '')and(listItem.ndomkName2 ne '')}">
                            -
                            </c:if>
                            <c:out value="${listItem.ndomkName2} " />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="銷案日期" style="width:10%; text-align:center;">
                            <c:out value="${listItem.closDate} " />&nbsp;  
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="管制註記" style="width7%; text-align:center;">
                            <c:out value="${listItem.delMk} " />&nbsp;
                        </display:column>
                        <display:column headerClass="issuetitle_L" title="資料明細" style="width:9%; text-align:center;">
                            <acl:checkButton buttonName="btnModify">
                            	<c:if test="${listItem.letterType eq '17'}">
                                	<input type="button" name="btnModify" class="button" value="修　改" onclick="$('page').value='1'; $('method').value='doDetail';$('index').value='<c:out value="${listItem_rowNum}" />';if (document.ExecutiveSupportMaintListForm.onsubmit()){getPayMk('<c:out value="${listItem.proDate}" />', '<c:out value="${listItem.hpNo}" />');}else{return false;}" />
                           		</c:if>
                           		<c:if test="${listItem.letterType ne '17'}">
                           			<input type="button" name="btnModify" class="button" value="修　改" onclick="$('page').value='1'; $('method').value='doDetail';$('index').value='<c:out value="${listItem_rowNum}" />';if (document.ExecutiveSupportMaintListForm.onsubmit()){document.ExecutiveSupportMaintListForm.submit();}else{return false;}" />
                           		</c:if>
                            </acl:checkButton>
                        </display:column>
                        </display:table>
                    <td>
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
