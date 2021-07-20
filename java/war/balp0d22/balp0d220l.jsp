<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D220L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="DecisionRpt02Form" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    
    function checkFields(){
        if($("pageBegin").value!="" &&  $("pageEnd").value!="" ){
            var pageBegin = $F("pageBegin");
            var pageEnd = $F("pageEnd");
            
            if (Trim(pageBegin).length > 0 && Trim(pageEnd).length > 0) {
                // 起頁 比 迄頁 大
                if (parseNumber(pageBegin) > parseNumber(pageEnd)) {
                    // 訊息: 「歸檔頁次」範圍錯誤，請重新確認。
                   alert("「歸檔頁次」範圍錯誤，請重新確認。");       
                   return false;             
                }
            }
            return true;        
         }else{
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
        <html:form action="/printDecisionRpt02" method="post" onsubmit="return validateDecisionRpt02Form(this);">
        
        <fieldset>
            <legend>&nbsp;歸檔清單補印&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" id="btnPrint" name="btnPrint" tabindex="3" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.DecisionRpt02Form.onsubmit() && checkFields()){document.DecisionRpt02Form.submit();}else{return false;}" />
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
                        <span class="issuetitle_R_down">歸檔日期：</span>
                    </td>
                    <td>
                        <html:text tabindex="60" property="decisionDate" styleId="decisionDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <span class="issuetitle_R_down">歸檔頁次：</span>
                    </td>
                    <td>
                        <html:text tabindex="70" property="pageBegin" styleId="pageBegin" styleClass="textinput" size="3" maxlength="3" onblur="this.value=asc(this.value);" />
                        &nbsp;~&nbsp;
                        <html:text tabindex="80" property="pageEnd" styleId="pageEnd" styleClass="textinput" size="3" maxlength="3" onblur="this.value=asc(this.value);" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <span class="issuetitle_R_down">決行人員：</span>
                    </td>
                    <td>
                        <html:text tabindex="90" property="exeMan" styleId="exeMan" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" onkeyup="autotab(this, $('btnPrint'));"/>
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
