<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D092C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="PayeeDataUpdateListForm" page="1" />
    <script language="javascript" type="text/javascript">
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    </script>
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/payeeDataUpdateListForOldAgeDeathRepay" method="post">
        
        <fieldset>
            <legend>&nbsp;老年年金受款人死亡改匯處理繼承人維護&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="8">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnInsert">
                            <input name="btnInsert" type="button" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='doInsert'; document.PayeeDataUpdateListForm.submit();" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackOldAgeDeathRepayList'; document.PayeeDataUpdateListForm.submit();" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" class="issuetitle_L">
                                <bean:define id="detail" name="<%=ConstantKey.CHECK_OLDAGE_DEATH_REPAY_DATA_CASE%>" />
                                <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                    <tr>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受理編號：</span>
                                            <c:out value="${detail.apNoStrDisplay}" />
                                        </td>                   
                                        <td width="25%">
                                            <span class="issuetitle_L_down">給付別：</span>
                                            <c:out value="${detail.payCode}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受款人序：</span>
                                            <c:out value="${detail.seqNo}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受款人死亡日期：</span>
                                            <c:out value="${detail.benDieDate}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受款人姓名：</span>
                                            <c:out value="${detail.benName}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">&nbsp;</span>
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受款人身分證號：</span>
                                            <c:out value="${detail.benIdnNo}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受款人出生日期：</span>
                                            <c:out value="${detail.benBrDate}" />
                                        </td>
                                    </tr>
                              </table>
                            </td>
                </tr>
                <tr align="center">
                    <td class="issuetitle_L">
                        <bean:define id="list" name="<%=ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE%>" />
                    
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >
                        <display:column title="序號" headerClass="issuetitle_L" style="width:7%; text-align:center;">
                            <c:out value="${listItem_rowNum}" />
    	                </display:column>
                        <display:column property="benName" title="繼承人姓名" headerClass="issuetitle_L" style="width:26%; text-align:left;" />
                        <display:column property="benIdnNo" title="繼承人身分證號" headerClass="issuetitle_L" style="width:15%; text-align:left;" />
                        <display:column property="benEvtRelName" title="關 係" headerClass="issuetitle_L" style="width:22%; text-align:left;" />    
                        <display:column title="資料明細" headerClass="issuetitle_L" style="width:21%; text-align:center;" >
                            <acl:checkButton buttonName="btnUpdate">
                                <input type="button" class="button" name="btnUpdate" value="修　改" onclick="location.href='<c:url value="/payeeDataUpdateListForOldAgeDeathRepay.do"/>?method=doUpdate&baappbaseId=<c:out value="${listItem.baappbaseId}" />';">
                            </acl:checkButton>
                            <acl:checkButton buttonName="btnDelete">
                                <c:if test='${listItem.delButtonQ4 gt "0"}'>
                                    <input type="button" class="button" name="btnDelete" value="刪　除" disabled="disabled">
                                </c:if>
                                <c:if test='${listItem.delButtonQ4 eq "0"}'>
                                    <input type="button" class="button" name="btnDelete" value="刪　除" onclick="if (confirm('<bean:message bundle="<%=Global.BA_MSG%>" key="msg.delete.confirm"/>')){location.href='<c:url value="/payeeDataUpdateListForOldAgeDeathRepay.do"/>?method=doDelete&baappbaseId=<c:out value="${listItem.baappbaseId}" />';}">
                                </c:if>
                            </acl:checkButton>
                            <acl:checkButton buttonName="btnCancel">
                            <c:choose>
                                <c:when test='${(listItem.dateCount1 ge "1") and (listItem.dateCount2 ge "1") and (listItem.dateCount3 le "0")}'>
                                <input type="button" class="button" name="btnCancel" value="改匯註銷" onclick="location.href='<c:url value="/payeeDataUpdateListForOldAgeDeathRepay.do"/>?method=doCancel&baappbaseId=<c:out value="${listItem.baappbaseId}" />';">
                                </c:when>
                                <c:otherwise>
                                <input type="button" class="button" name="btnCancel" value="改匯註銷" disabled="disabled">
                                </c:otherwise>
                            </c:choose>
                            </acl:checkButton>
                        </display:column>
                        </display:table>
                    </td>
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