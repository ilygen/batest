<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D050Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="ExecutiveSupportQueryForm" page="1" />
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/executiveSupportQueryQuery" method="post" onsubmit="return validateExecutiveSupportQueryForm(this);">
        
        <fieldset>
            <legend>&nbsp;行政支援查詢&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="qryFromWhere" property="qryFromWhere" value="BAIQ0D050Q" />
                        
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnQuery" class="button" value="確  認" onclick="$('page').value='1'; $('method').value='doQuery'; if (document.ExecutiveSupportQueryForm.onsubmit()){document.ExecutiveSupportQueryForm.submit();}else{return false;}" />
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
                        <html:text property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo2'));" onkeypress=""  />
                        &nbsp;-
                        <html:text property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onkeyup="autotab(this, $('apNo3'));" />
                        &nbsp;-
                        <html:text property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onkeyup="autotab(this, $('apNo4'));"/>
                        &nbsp;-
                        <html:text property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onkeyup="autotab(this, $('idNo'));"/>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="issuetitle_R_down">身分證號：</span>
                    </td>
                    <td>
                        <html:text property="idNo" styleId="idNo" styleClass="textinput" size="11" maxlength="11"  onkeyup="autotab(this, $('issuYm'));"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="issuetitle_R_down">核定年月：</td>
                    <td>
                        <span class="formtxt">
                            <html:text styleId="issuYm" property="issuYm" size="5" maxlength="5" styleClass="textinput" onkeyup="autotab(this, $('btnQuery'));"/>&nbsp;
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2"><hr size="1" noshade="noshade">
                        <span class="titleinput">※注意事項：</span><br>
            　                                                  1.&nbsp;有關日期的欄位，填寫格式如民國98年1月，請輸入09801。<br>
            　                                                  2.&nbsp;<span class="needtxt">＊</span>為必填欄位。</td>
                </tr>
            </table>
        </fieldset>
        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
