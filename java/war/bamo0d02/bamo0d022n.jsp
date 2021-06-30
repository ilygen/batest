<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="tw.gov.bli.ba.update.cases.ChkFileCase" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D022N" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>   
    <html:javascript formName="OwesDataUpdateForm" page="1" />        
    <script language="javascript" type="text/javascript">
    <!--     
    //畫面初始化
    function initAll(){
        $("owesMk").value = "Y";
    }        
    
    <%-- 進階欄位驗證 --%>
    <%-- 注意: 點選修改按鈕時此部份之驗證須在 Validation.xml 驗證之前執行 --%>
    <%-- begin ... [ --%>
    function checkFields(){ 
        var msg = "";  
        if($("checkedSeqNo").value==""){
            msg+='請點選欲修改的資料';
        }
             
        if(msg != ""){
            alert(msg);
            return false;
        }else{
            return true;
        }
    }
    <%-- ] ... end --%>
        
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/owesDataUpdate" method="post" onsubmit="return validateOwesDataUpdateForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="apNo" property="apNo" />
        <html:hidden styleId="lsUbno" property="lsUbno" />
        <html:hidden styleId="lsUbnoCk" property="lsUbnoCk" />
        <input type="hidden" name="checkedSeqNo" value="">
                
        <fieldset>
            <legend>&nbsp;欠費期間維護&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right">
                        <acl:checkButton buttonName="btnInsert">
                            <input type="button" name="btnInsert" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='doInsert'; if(document.OwesDataUpdateForm.onsubmit()){document.OwesDataUpdateForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnUpdate">
                            <input type="reset" name="btnUpdate" class="button" value="修　改"  onclick="$('page').value='1'; $('method').value='doUpdate'; if(checkFields() && document.OwesDataUpdateForm.onsubmit()){document.OwesDataUpdateForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>                        
                        <acl:checkButton buttonName="btnDelete">
                            <input type="reset" name="btnDelete" class="button" value="刪　除"  onclick="$('page').value='1'; $('method').value='doDelete'; if(checkFields() ){document.OwesDataUpdateForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.OwesDataUpdateForm.submit();"/>
                        </acl:checkButton>       
                    </td>
                </tr>
                <tr>
                    <td>
                        <bean:define id="owes" name="<%=ConstantKey.BAAPPBASE_DATA_UPDATE_CASE%>"/>                        
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="33%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${owes.apNoStrDisplay}" />
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:if test='${(owes.pagePayKind eq "L")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                    </c:if>
                                    <c:if test='${(owes.pagePayKind eq "K")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                    </c:if>
                                    <c:if test='${(owes.pagePayKind eq "S")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                    </c:if>
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${owes.appDateStr}" />
                                </td>                                
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${owes.evtName}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${owes.evtIdnNo}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${owes.evtBrDateStr}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                            <tr>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">最後單位保險證號：</span>
                                    <c:out value="${owes.lsUbno}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">欠費註記：</span>
                                    <html:select property="owesMk">
                                        <html:option value="">請選擇</html:option>
                                        <html:option value="Y">Y-欠費</html:option>
                                        <html:option value="N">N-未欠費</html:option>                                        
                                    </html:select>                                    
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">欠費期間起迄：</span>
                                    <html:text property="owesBdate" styleId="owesBdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value).toUpperCase();"/>
                                    &nbsp;~&nbsp;
                                    <html:text property="owesEdate" styleId="owesEdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value).toUpperCase();"/>
                                    <span class="formtxt">(如民國98年1月1日，請輸入0980101)</span>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">&nbsp;</td>
                </tr>
                <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.BASENIMAINTCASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.DETAIL_LIST_PAGE_SIZE%>">
                            <display:column title="&nbsp;" style="width:10%; text-align:center;">
                                <html:radio property="seqNo" value="${listItem.seqNo}" onclick="$('checkedSeqNo').value='${listItem.seqNo}'; $('owesMk').value='${listItem.owesMk}'; $('owesBdate').value='${listItem.owesBdateStr}'; $('owesEdate').value='${listItem.owesEdateStr}';" />                               
                            </display:column>                            
                            <display:column title="序號" style="width:25%; text-align:center;">
                                <c:out value="${listItem_rowNum}" />
                            </display:column>
                            <display:column title="欠費期間起迄" style="width:40%; text-align:center;">
                                <c:out value="${listItem.owesBdateStr}" />
                                <c:if test='${(listItem.owesBdateStr ne "")or(listItem.owesEdateStr ne "")}'>
                                &nbsp;~&nbsp;
                                </c:if>
                                <c:out value="${listItem.owesEdateStr}" />
                            </display:column>
                            <display:column title="欠費註記" style="width:25%; text-align:center;">
                                <c:out value="${listItem.owesMk}" />&nbsp;
                            </display:column>                                                                                   
                        </display:table>                    
                    </td>                    
                </tr>                
                <tr>
                    <td colspan="4">&nbsp;</td>
                </tr>
            </table>            
        </fieldset>        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
