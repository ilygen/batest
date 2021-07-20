<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BARE0D010A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="ReviewFeeReceiptForm" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    function initAll() {
        $("apNo1").focus();
    }

    Event.stopObserving(window, 'load', inputFieldFocus);
    Event.observe(window, 'load', initAll , false);
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/reviewFeeReceipt" method="post" onsubmit="return validateReviewFeeReceiptForm(this);">
        
        <fieldset>
            <legend>&nbsp;複檢費用受理作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnInsert">
                            <input type="button" id="btnInsert" name="btnInsert" tabindex="6" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='doInsertQuery'; if (document.ReviewFeeReceiptForm.onsubmit()){document.ReviewFeeReceiptForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnModify">
                            <input type="button" id="btnModify" name="btnModify" tabindex="7" class="button" value="修　改" onclick="$('page').value='1'; $('method').value='doModifyQuery'; if (document.ReviewFeeReceiptForm.onsubmit()){document.ReviewFeeReceiptForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input type="reset" id="btnReset" name="btnReset" tabindex="8" class="button" value="清  除" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="issuetitle_R_down">身分證號：</span>
                    </td>
                    <td>
                        <html:text styleId="qryIdnNo" property="qryIdnNo" tabindex="5" size="15" maxlength="10" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();;" onkeyup="autotab(this, $('apNo1'));" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
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
                        <html:text styleId="apNo4" property="apNo4" tabindex="4" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('btnInsert'));" />
                        <span class="formtxt">(新增時，請輸入本案之受理編號)</span>
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
