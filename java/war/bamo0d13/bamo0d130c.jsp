<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D130C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="DisabledApplicationDataUpdateForm" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    function getProcStat(){
        $("message").innerHTML = "";
        var apNo = $F('apNo1') + $F('apNo2') + $F('apNo3') + $F('apNo4');
        updateCommonAjaxDo.getBaappbaseProcStat(apNo, chkProcStat);
    }

    function chkProcStat(procStat){
        if (procStat == '') {
            $("message").innerHTML = "<font color='red'>W0040 無查詢資料</font>";
            return false;
        } else if (procStat == '0' || procStat == '1' || procStat == '9') {
            document.DisabledApplicationDataUpdateForm.submit();
        } else if (procStat=='2') {
            if (confirm('案件經審核核定，是否繼續更正？')) {
                document.DisabledApplicationDataUpdateForm.submit();
            } else {
                return false;
            }
        } else if (procStat == '3' || procStat == '4' || procStat == '5') {
            $("message").innerHTML = "<font color='red'>W1004 本案已決行！</font>";
            return false;
        }

    }
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/disabledApplicationDataUpdate" method="post" onsubmit="return validateDisabledApplicationDataUpdateForm(this);">

        <fieldset>
            <legend>&nbsp;失能年金案件資料更正&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" id="btnConfirm" name="btnConfirm" tabindex="5" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doQuery'; if (document.DisabledApplicationDataUpdateForm.onsubmit()){getProcStat();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input type="reset" id="btnReset" name="btnReset" tabindex="6" class="button" value="清  除" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">受理編號：</span>
                    </td>
                    <td>
                        <html:text styleId="apNo1" property="apNo1" tabindex="1" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo2'));" />
                        &nbsp;-&nbsp;
                        <html:text styleId="apNo2" property="apNo2" tabindex="2" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo3'));" />
                        &nbsp;-&nbsp;
                        <html:text styleId="apNo3" property="apNo3" tabindex="3" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo4'));" />
                        &nbsp;-&nbsp;
                        <html:text styleId="apNo4" property="apNo4" tabindex="4" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('btnConfirm'));" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span>
                        <br />
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <span class="needtxt">＊</span>為必填欄位。
                    </td>
                </tr>
            </table>
        </fieldset>

        </html:form>

        <p></p>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
