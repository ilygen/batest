<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X081F" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />   
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>  
    <html:javascript formName="BasicAmtMaintListForm" page="1" />
    <script language="javascript" type="text/javascript">
    
    <%-- 確認是否刪除 --%>
    function checkDelete(){
       if (!confirm("是否確定刪除?")) {
          window.event.returnValue = false;
        }
    }
    
    </script>      
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/basicAmtMaintList" method="post" onsubmit="return validateBasicAmtMaintListForm(this);">
        
        <fieldset>
            <legend>&nbsp;老年年金加計金額調整作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="100%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="8" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnConfirm" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='doInsert'; if (document.BasicAmtMaintListForm.onsubmit()){document.BasicAmtMaintListForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.BasicAmtMaintListForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>                

                <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.BASIC_AMT_MAINT_QUERY_CASE%>" />
                        
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" class="px13">                            
                            <display:column title="序&nbsp;號" style="width:8%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem_rowNum}" />&nbsp;
                            </display:column>
                            
                            <display:column title="年度 / 物價指數" style="width:12%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.cpiYear1}/${listItem.cpiNdex1}、${listItem.cpiYear2}/${listItem.cpiNdex2}" />&nbsp;
                            </display:column>
                            
                            <display:column title="成長率" style="width:11%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.growThrate}" />&nbsp;%&nbsp;
                            </display:column>
                            
                            <display:column title="加計金額" style="width:11%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <fmt:formatNumber value="${listItem.basicAmt}" />&nbsp;
                            </display:column>
                            
                            <display:column title="給付年月（起）" style="width:11%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.payYmB}" />
                            </display:column>
                            
                            <display:column title="給付年月（迄）" style="width:11%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">                                
                                <c:out value="${listItem.payYmE}" />&nbsp;
                            </display:column>                                                                                                              
                                                                                                 
                            <display:column title="異動人員" style="width:12%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">                                
                                <c:out value="${listItem.user}" />&nbsp;
                            </display:column>
                            
                            <display:column title="處理日期" style="width:12%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <c:out value="${listItem.date}" />&nbsp;
                            </display:column>
                            
                            <display:column title="資料明細" style="width:14%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                <acl:checkButton buttonName="btnUpdate">
                                    <input type="button" class="button" name="btnUpdate" value="修　改" onclick="location.href='<c:url value="/basicAmtMaintList.do"/>?method=doUpdate&rowNum=<c:out value="${listItem_rowNum}" />';">
                                </acl:checkButton>
                                <acl:checkButton buttonName="btnDelete">
									<c:choose>
									<c:when test="${listItem.payYmB le listItem.sysDate}">
										<input type="button" class="button" name="btnDelete" value="刪　除" disabled="true">
									</c:when>
									<c:otherwise>
										<input type="button" class="button" name="btnDelete" value="刪　除" onclick="if(confirm('是否確定刪除?')){location.href='<c:url value="/basicAmtMaintList.do"/>?method=doDelete&rowNum=<c:out value="${listItem_rowNum}" />'};">
									</c:otherwise>
									</c:choose>                                                                                                                       
                                </acl:checkButton>                                                                
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