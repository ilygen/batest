<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BASU0D020D" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="ExecutiveSupportCloseForm" page="1" />
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/executiveSupportClose" method="post" onsubmit="return validateExecutiveSupportCloseForm(this);">
        
        <fieldset>
            <legend>&nbsp;行政支援記錄銷案&nbsp;</legend>
            
            <div align="right" id="showtime">
                               網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="qryFromWhere" property="qryFromWhere" value="BASU0D020D" />
                        
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnQuery" class="button" value="確  認" onclick="$('page').value='1'; $('method').value='doQuery'; if (document.ExecutiveSupportCloseForm.onsubmit()){document.ExecutiveSupportCloseForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input type="reset" name="btnReset" class="button" value="清  除" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                         <span class="needtxt">＊</span>
                         <span class="issuetitle_R_down">受理編號：</span>
                    </td>
                    <td>
                        <span class="formtxt">
                               <html:text styleId="apNo1" property="apNo1" size="1" maxlength="1" styleClass="textinput" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo2'));" onkeypress="" />
                               &nbsp;-&nbsp;
                               <html:text styleId="apNo2" property="apNo2" size="1" maxlength="1" styleClass="textinput" onkeyup="autotab(this, $('apNo3'));" />
                               &nbsp;-&nbsp;
                               <html:text styleId="apNo3" property="apNo3" size="5" maxlength="5" styleClass="textinput" onkeyup="autotab(this, $('apNo4'));" />
                               &nbsp;-&nbsp;
                               <html:text styleId="apNo4" property="apNo4" size="5" maxlength="5" styleClass="textinput"  onkeyup="autotab(this, $('btnQuery'));"/>
                         </span>
                    </td>
                </tr>
                <!-- 
                <tr>
                    <td align="right">
                        <span class="needtxt">　</span>
                        <span class="issuetitle_R_down">核定年月：</span>
                    </td>
                    <td>
                        <span class="formtxt">
                        <html:text styleId="issuYm" property="issuYm" size="5" maxlength="5" styleClass="textinput" onkeyup="autotab($('issuYm'), $('btnQuery'));"/>&nbsp;
                        </span>
                    </td>
                </tr>
                -->
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <hr size="1" noshade="noshade">
                        <span class="titleinput">※注意事項：</span><br>
            　                                                  1.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
            　                                                  2.&nbsp;<span class="needtxt">＊</span>為必填欄位。
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
