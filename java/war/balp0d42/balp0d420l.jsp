<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D420L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="AuditRpt01Form" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/printAuditRpt01" method="post" onsubmit="return validateAuditRpt01Form(this);">
        
        <fieldset>
            <legend>&nbsp;逾期未決行案件管制表&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" id="btnPrint" name="btnPrint" tabindex="3" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.AuditRpt01Form.onsubmit()){document.AuditRpt01Form.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input type="reset" id="btnReset" name="btnReset" tabindex="4" class="button" value="清  除" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">給付別：</span>
                    </td>
                    <td>
                        <html:select styleId="payCode" property="payCode" tabindex="1">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">截止年月：</span>
                    </td>
                    <td>
                        <html:text tabindex="60" property="endYm" styleId="endYm" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" />
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">報表種類：</span>
                    </td>
                    <td class="formtxt">
                        <html:radio styleId="prtTyp1" property="prtTyp" value="1" />當月&nbsp;
                        <html:radio styleId="prtTyp2" property="prtTyp" value="2" />非當月
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
                        1.<span class="needtxt">＊</span>為必填欄位。
                        <br>&nbsp;
                        2.<span class="needtxt"></span>截止年月預設每次核定年月-1個月。
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
