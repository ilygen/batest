<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D052C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>            
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
    }    
    
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/inheritorRegister" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="baappbaseId" property="baappbaseId" />
        <fieldset>
            <legend>&nbsp;債務繼承人資料登錄作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <c:if test="${(InheritorRegisterCase.evtDieDate eq null) or (InheritorRegisterCase.evtDieDate eq '')}">
                            <acl:checkButton buttonName="btnInsert">
                                <input type="button" name="btnInsert" class="button" value="新  增" onclick="$('page').value='1'; $('method').value='doAdd'; document.InheritorRegisterForm.submit();" disabled="true"/>&nbsp;
                            </acl:checkButton>                        
                        </c:if>
                        <c:if test="${(InheritorRegisterCase.evtDieDate ne null) and (InheritorRegisterCase.evtDieDate ne '')}">
                        <acl:checkButton buttonName="btnInsert">
                            <input type="button" name="btnInsert" class="button" value="新  增" onclick="$('page').value='1'; $('method').value='doAdd'; document.InheritorRegisterForm.submit();" />&nbsp;
                        </acl:checkButton>
                        </c:if>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返  回" onclick="$('page').value='1'; $('method').value='doBack'; document.InheritorRegisterForm.submit();"/>
                        </acl:checkButton>                      
                    </td>
                </tr>
                <tr>
                <td>
                   <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                   <tr>
                      <td width="24%"><span class="issuetitle_L_down">受理編號：</span><c:out value="${InheritorRegisterCase.formatApNo}" /></td>
                      <td width="21%"><span class="issuetitle_L_down">給付別：</span><c:out value="${InheritorRegisterCase.kind}" /></td>
                      <td width="21%"><span class="issuetitle_L_down">申請日期：</span><c:out value="${InheritorRegisterCase.chineseAppDate}" /></td>
                   </tr>
                   <tr>
                      <td><span class="issuetitle_L_down">事故者姓名：</span><c:out value="${InheritorRegisterCase.evtName}" /></td>
                      <td><span class="issuetitle_L_down">事故者出生日期：</span><c:out value="${InheritorRegisterCase.chineseEvtBrDate}" /></td>
                      <td><span class="issuetitle_L_down">事故者身分證號：</span><c:out value="${InheritorRegisterCase.evtIdnNo}" /></td>
                   </tr>
                   </table>
               </td>
               </tr> 
               <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.INHERITOR_REGISTER_CASE%>" />
                        <display:table name="pageScope.list.detailList" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                            <display:column title="序號" style="width:14%; text-align:center;">
                                <c:out value="${listItem_rowNum}" />
                            </display:column>
                            <display:column title="姓　名" style="width:16%; text-align:center;">
                                <c:out value="${listItem.benName}" />
                            </display:column>         
                            <display:column title="出生日期" style="width:16%; text-align:center;">
                                 <c:out value="${listItem.benBrDate}" />                               
                            </display:column>
                            <display:column title="身分證號" style="width:18%; text-align:center;">
                                <c:out value="${listItem.benIdnNo}" />
                            </display:column>
                            <display:column title="關　係" style="width:16%; text-align:center;">
                                <c:if test='${(listItem.benEvtRel eq "1")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "2")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "3")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "4")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "5")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "6")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "7")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "A")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "C")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "F")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "N")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "Z")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z%>" />
                                </c:if>
                                <c:if test='${(listItem.benEvtRel eq "O")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O%>" />
                                </c:if> 
                            </display:column>
                            <display:column title="資料明細" style="width:13%; text-align:center;">
                                <acl:checkButton buttonName="btnModify">
                                    <input type="button" name="btnModify" class="button" value="修  改" onclick="$('page').value='1'; $('method').value='doModify'; $('baappbaseId').value='<c:out value="${listItem.baappbaseId}" />'; document.InheritorRegisterForm.submit();" />&nbsp;
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
