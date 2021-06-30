<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X042C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="PreviewConditionMaintDetailForOldAgeAnnuityForm" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    <%-- begin 檢查必填欄位 --%>
    function checkFields() {
        var msg = "";
        
        if (Trim($F("binsurSeni")) > Trim($F("einsurSeni")))
            msg += '「投保年資」：迄值不能小於起值。\r\n';
        if (Trim($F("bissueAmt")) > Trim($F("eissueAmt")))
            msg += '「核定金額」：迄值不能小於起值。\r\n';
        if (Trim($F("bcondition")) > Trim($F("econdition")))
            msg += '「申請年齡」：迄值不能小於起值。\r\n';
            
        if (msg != "") {
            alert(msg);
            return false;
        }
        else {
            return true;
        }      
    }
    <%-- ] ... end --%>
    
    function initAll() {    
    }

    Event.observe(window, 'load', initAll , false);
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/previewConditionMaintForOldAgeAnnuity" method="post" onsubmit="return validatePreviewConditionMaintDetailForOldAgeAnnuityForm(this);">

        <fieldset>
            <legend>&nbsp;先抽對象維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
      
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="2">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnSave">
                            <input type="button" name="btnSave" class="button" value="確  認" onclick="$('page').value='1'; $('method').value='doSave'; if (document.PreviewConditionMaintDetailForOldAgeAnnuityForm.onsubmit() && checkFields()){document.PreviewConditionMaintDetailForOldAgeAnnuityForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返  回" onclick="location.href='<c:url value="/enterPreviewConditionMaint.do?parameter=enterPreviewConditionMaint" />';" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">給付別：</span>老年年金
                                    <html:hidden styleId="payCode" property="payCode" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                            <tr>
                                <td width="25%" id="iss">
                                    <span class="issuetitle_L_down">投保年資區間數：</span>
                                    <span class="formtxt">
                                        <html:text styleId="binsurSeni" property="binsurSeni" size="5" maxlength="3" styleClass="textinput" />&nbsp;年
                                        &nbsp;~&nbsp;
                                        <html:text styleId="einsurSeni" property="einsurSeni" size="5" maxlength="3" styleClass="textinput" />&nbsp;年
                                    </span>
                                </td>
                                <td width="25%" id="iss">
                                    <span class="issuetitle_L_down">核定金額區間數：</span>
                                    <span class="formtxt">
                                        <html:text styleId="bissueAmt" property="bissueAmt" size="10" maxlength="6" styleClass="textinput" />&nbsp;元
                                        &nbsp;~&nbsp;
                                        <html:text styleId="eissueAmt" property="eissueAmt" size="10" maxlength="6" styleClass="textinput" />&nbsp;元
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">申請年齡區間數：</span>
                                    <span class="formtxt">
                                        <html:text styleId="bcondition" property="bcondition" size="5" maxlength="3" styleClass="textinput" />&nbsp;歲
                                        &nbsp;~&nbsp;
                                        <html:text styleId="econdition" property="econdition" size="5" maxlength="3" styleClass="textinput" />&nbsp;歲
                                    </span>
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">申請項目：</span>
                                    <span class="formtxt">
                                        <html:multibox styleId="condition1Array" property="condition1Array" value="1" />1.老年年金給付
                                        <html:multibox styleId="condition1Array" property="condition1Array" value="2" />2.減額老年年金給付
                                        <html:multibox styleId="condition1Array" property="condition1Array" value="3" />3.危險堅強體力
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">條件：</span>
                                    <span class="formtxt">
                                        <html:radio styleId="andOrTyp" property="andOrTyp" value="0" />AND
                                        <html:radio styleId="andOrTyp" property="andOrTyp" value="1" />OR
                                    </span>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td width="15%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">抽樣間隔數：</span>
                    </td>
                    <td width="90%">
                        <html:text styleId="range" property="range" size="10" maxlength="6" styleClass="textinput" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">抽樣件數：</span>
                    </td>
                    <td>
                        <html:text styleId="sampleVolume" property="sampleVolume" size="10" maxlength="6" styleClass="textinput" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">每月限制抽樣件數：</span>
                    </td>
                    <td>
                        <html:text styleId="limitAmount" property="limitAmount" size="10" maxlength="6" styleClass="textinput" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <span class="needtxt">＊</span><span class="issuetitle_R_down">列印方式：</span>
                    </td>
                    <td>
                        <div>
                            <html:select styleId="printTyp" property="printTyp">
                                <html:option value="0">不按分案原則</html:option>
                                <html:option value="1">按分案原則</html:option>
                            </html:select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">是否抽樣：</span>
                    </td>
                    <td>
                        <span class="formtxt">
                            <html:radio styleId="enable" property="enable" value="0" />是
                            <html:radio styleId="enable" property="enable" value="1" />否
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
            </table>
        </fieldset>
        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
