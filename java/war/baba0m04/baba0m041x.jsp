<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M041X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <script language="javascript" type="text/javascript">
    <%-- 確認按鈕 --%>
    <%-- 按下 確認按鈕時  檢查checkbox是否有勾選的資料 --%>    
    <%-- begin ... [ --%>
    function checkFields() {
        var bClicked = false;
        var objs = document.getElementsByName("idForConfirm");
        idForConfirm = new Array(0);
        
        for (i = 0; i < objs.length; i++) {
            if (objs[i].checked == true) {
                bClicked = true;
                temp = new Array(1)
                temp[0] = objs[i].value;
                idForConfirm = idForConfirm.concat(temp);
            }
        }
        $("idForConfirm").value=idForConfirm;
        
        if(bClicked == false){
            alert("請勾選要確認的資料");
            return false;           
        }else if(idForConfirm == ""){
            alert("沒有可確認的資料");
            return false;  
        }else{
            return bClicked;
        }        
    }
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/receivableAdjustBJ" method="post" >
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="baunacprecId" property="baunacprecId" />        
        <html:hidden styleId="idForConfirm" property="idForConfirm" />
        
        <fieldset>
            <legend>&nbsp;已收調整作業 &nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="11" align="right">
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnConfirm" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doBatch'; if(checkFields()){document.ReceivableAdjustBJForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.ReceivableAdjustBJForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>                
                <bean:define id="master" name="<%=ConstantKey.RECEIVABLE_ADJUST_BJ_MASTER_CASE %>" />
                <tr>
                    <td colspan="11" class="issuetitle_L">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="issuetitle_L_down">
                            <tr class="px13">            
                                <td colspan="4">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${master.apNoStr}" />
                                </td>
                            </tr>
                            <tr class="px13">
                                <td width="25%">
                                    <span class="issuetitle_L_down">受款人身分證號：</span>
                                    <c:out value="${master.benIdnNo}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受款人姓名：</span>
                                    <c:out value="${master.benName}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">更正前身分證號：</span>
                                    <c:out value="${master.befBenIdnNo}" />
                                </td>            
                                <td width="25%">
                                    <span class="issuetitle_L_down">更正前姓名：</span>
                                    <c:out value="${master.befBenName}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td>                    
                        <bean:define id="list" name="<%=ConstantKey.RECEIVABLE_ADJUST_BJ_MASTER_CASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">                            
                            <display:column title="<input type='checkbox' name='chkBatch' onclick='selectAll(this, \"idForConfirm\");'>全選" style="width:12%; text-align:center;">
                                <input type='checkbox' name='idForConfirm' value='<c:out value="${listItem.baunacprecId}" />' >
                            </display:column>
                            <display:column title="收回方式" style="width:12%; text-align:center;">
                                <c:if test="${listItem.recKind eq '01'}">
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_01 %>" />
                                </c:if>
                                <c:if test="${listItem.recKind eq '02'}">
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_02 %>" />
                                </c:if>
                                <c:if test="${listItem.recKind eq '03'}">
                                    <c:out value="<%=ConstantKey.BAUNACPREC_RECKIND_STR_03 %>" />
                                </c:if>&nbsp;
                            </display:column>
                            <display:column title="入帳日期" style="width:12%; text-align:center;">
                                <c:out value="${listItem.aplPayDateStr}" />&nbsp;
                            </display:column>
                            <display:column title="核定年月" style="width:12%; text-align:center;">
                                <c:out value="${listItem.issuYmStr}" />&nbsp;
                            </display:column>
                            <display:column title="給付年月" style="width:13%; text-align:center;">
                                <c:out value="${listItem.payYmStr}" />&nbsp;
                            </display:column>
                            <display:column title="應收金額" style="width:13%; text-align:center;">
                                <c:out value="${listItem.recAmt}" />&nbsp;
                            </display:column>
                            <display:column title="已收金額" style="width:13%; text-align:center;">
                                <c:out value="${listItem.woAmt}" />&nbsp;
                            </display:column>
                            <display:column title="資料明細" style="width:13%; text-align:center;">
                                <acl:checkButton buttonName="btnDetail">
                                    <input type="button" name="btnDetail" class="button" value="明細查詢" onclick="$('page').value='1'; $('method').value='doDetail'; $('baunacprecId').value='<c:out value="${listItem.baunacprecId}" />'; document.ReceivableAdjustBJForm.submit();" />&nbsp;
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
