<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D210L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="DecisionRpt01Form" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    // 如果決行日期只打了起值, 則自動將迄值帶入起值
    function copyValue(oring, dest) {
        dest.value = asc(oring.value);
    }
    
    //列印時鎖住按鈕 以免連按兩次$("btnInsert").disabled
    function disabledBtnPrint() {
        $("btnPrint").disabled = true;
    }
    
    function checkFields(){
        if($("decisionSdate").value!="" &&  $("decisionEdate").value!="" ){
            var decisionSdate = $F("decisionSdate");
            var decisionEdate = $F("decisionEdate");
            
            if (Trim(decisionSdate).length > 0 && Trim(decisionEdate).length > 0) {
                // 起日 比 迄日 大
                if (parseNumber(decisionSdate) > parseNumber(decisionEdate)) {
                    // 訊息: 「決行日期」範圍錯誤，請重新確認。
                   alert("「決行日期」範圍錯誤，請重新確認。");       
                   return false;             
                }
            }
            return true;        
         }
    }
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/printDecisionRpt01" method="post" onsubmit="return validateDecisionRpt01Form(this);">

        <fieldset>
            <legend>&nbsp;歸檔清單&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" id="btnPrint" name="btnPrint" tabindex="3" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.DecisionRpt01Form.onsubmit() && checkFields()){disabledBtnPrint(); document.DecisionRpt01Form.submit();}else{return false;}" />
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
                        <span class="issuetitle_R_down">決行日期：</span>
                    </td>
                    <td>
                        <html:text tabindex="60" property="decisionSdate" styleId="decisionSdate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);" onkeyup="copyValue(this, $('decisionEdate')); autotab(this, $('decisionEdate'));"/>
                        &nbsp;~&nbsp;
                        <html:text tabindex="70" property="decisionEdate" styleId="decisionEdate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);" onkeyup="autotab(this, $('exeMan'));"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <span class="issuetitle_R_down">決行人員：</span>
                    </td>
                    <td>
                        <html:text tabindex="80" property="exeMan" styleId="exeMan" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" onkeyup="autotab(this, $('btnPrint'));"/>
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
