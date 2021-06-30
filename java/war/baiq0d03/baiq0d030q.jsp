<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D030Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="UpdateLogQueryForm" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    function copyValue(oring, dest) {
        if (dest.value == "")
            dest.value = asc(oring.value);
    }
    
    function checkDate() {
        if (Trim($F("updateDateBegin")) != "" && Trim($F("updateDateEnd")) != "") {
            if (parseNumber(Trim($F("updateDateBegin"))) > parseNumber(Trim($F("updateDateEnd")))) {
                alert("處理時間範圍錯誤, 起始日期不可大於結束日期");
                return false;
            }
        }
        return true;
    }
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/updateLogQuery" method="post" onsubmit="return validateUpdateLogQueryForm(this);">

        <fieldset>
            <legend>&nbsp;更正日誌查詢&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" id="btnQuery" name="btnQuery" tabindex="10" class="button" value="確  認" onclick="$('page').value='1'; $('method').value='doQuery'; if (document.UpdateLogQueryForm.onsubmit() && checkDate()){document.UpdateLogQueryForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input type="reset" id="btnReset" name="btnReset" tabindex="11" class="button" value="清  除" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td align="right" width="30%">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">給付別：</span>
                    </td>
                    <td>
                        <html:select styleId="payCode" property="payCode" tabindex="1" onchange="$('apNo1').value=this.value;">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td align="right" width="30%">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">處理時間：</span>
                    </td>
                    <td>
                        <html:text styleId="updateDateBegin" property="updateDateBegin" tabindex="2" size="7" maxlength="7" styleClass="textinput" onblur="this.value = asc(this.value); copyValue(this, $('updateDateEnd'));" onkeyup="autotab(this, $('updateDateEnd'));"/>
                        &nbsp;~&nbsp;
                        <html:text styleId="updateDateEnd" property="updateDateEnd" tabindex="3" size="7" maxlength="7" styleClass="textinput" onblur="this.value = asc(this.value); copyValue(this, $('updateDateBegin'));" onkeyup="autotab(this, $('apNo1'));"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" width="30%">
                        <span class="issuetitle_R_down">受理編號：</span>
                    </td>
                    <td>
                        <html:text styleId="apNo1" property="apNo1" tabindex="4" size="1" maxlength="1" styleClass="textinput" onfocus="$('apNo2').focus();" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo2'));" />
                        &nbsp;-&nbsp;
                        <html:text styleId="apNo2" property="apNo2" tabindex="5" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo3'));" />
                        &nbsp;-&nbsp;
                        <html:text styleId="apNo3" property="apNo3" tabindex="6" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo4'));" />
                        &nbsp;-&nbsp;
                        <html:text styleId="apNo4" property="apNo4" tabindex="7" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('benIdnNo'));"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" width="30%">
                        <span class="issuetitle_R_down">身分證號：</span>
                    </td>
                    <td>
                        <html:text styleId="benIdnNo" property="benIdnNo" tabindex="8" size="15" maxlength="10" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase(); autotab(this, $('updUser'));" />
                    </td>
                </tr>
                <tr>
                    <td align="right" width="30%">
                        <span class="issuetitle_R_down">更正人員：</span>
                    </td>
                    <td>
                        <html:text styleId="updUser" property="updUser" tabindex="9" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase(); autotab(this, $('btnQuery'));" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span>
                        <br>&nbsp;
                        <span class="needtxt">＊</span>為必填欄位。
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
