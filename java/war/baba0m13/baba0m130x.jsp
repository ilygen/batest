<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.bj.helper.CaseSessionHelper" %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M130X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="MonthSForm" page="1" />
    <script language="javascript" type="text/javascript">
     //頁面欄位值初始化       
    function initAll(){
       $("issuYm").value = $("issuYmStr").value;
       checkCount();
    }    
    
    function checkCount(){
       if($("checkCount").value == "Y"){
          $("btnConfirm").disabled = true;
       }else{
          $("btnConfirm").disabled = false;
       }
    }   
    Event.observe(window, 'load', initAll, false);
    </script>
    
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/monthS" method="post" onsubmit="return validateMonthSForm(this);">
        
        <fieldset>
            <legend>&nbsp;遺屬年金線上月核定作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="checkCount" property="checkCount" value="<%=CaseSessionHelper.getMonthLCheckCountStr(request) %>" />
                        <html:hidden styleId="issuYmStr" property="issuYmStr" value="<%=CaseSessionHelper.getMonthQueryIssuYmStr(request) %>" />
                        
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnConfirm" class="button" value="確  認" onclick="$('page').value='1'; $('method').value='doQuery'; if (document.MonthSForm.onsubmit()){document.MonthSForm.submit();}else{return false;}" tabindex="6"/>
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnQuery" class="button" value="查  詢" onclick="$('page').value='1'; $('method').value='doQueryList'; if (document.MonthSForm.onsubmit()){document.MonthSForm.submit();}else{return false;}" tabindex="7"/>
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input type="reset" name="btnReset" class="button" value="清  除" tabindex="8"/>
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">核定年月：</span>
                    </td>
                    <td class="formtxt">
                        <html:text styleId="issuYm" property="issuYm" tabindex="2" size="10" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" />&nbsp;(如民國98年1月，請輸入09801)&nbsp;
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">　</span>
                        <span class="issuetitle_R_down">核定日期：</span>
                    </td>
                    <td class="formtxt">
                        <html:text property="chkDate" styleId="chkDate" tabindex="3" styleClass="textinput" maxlength="7" size="10" onblur="this.value = asc(this.value);" />&nbsp;(僅提供線上月核定查詢使用)&nbsp; 
                    </td>        
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <hr size="1" noshade="noshade">
                        <span class="titleinput">※注意事項：</span><br>
                      1.&nbsp;線上月核定作業每日僅可執行一次。<br>
            　                                                  2.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
            　                                                  3.&nbsp;<span class="needtxt">＊</span>為必填欄位。
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
