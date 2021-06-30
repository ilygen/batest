<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.REVIEW_FEE_RECEIPT_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BARE0D011A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/reviewFeeCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="ReviewFeeReceiptForm" page="2" />
    <script language="javascript" type="text/javascript">
    <!--
    <%-- 複檢醫院代碼 變更時的處理 (AJAX) --%>
    <%-- [ --%>
    function doHosIdChange() {
        if (Trim($F("hosId")).length > 0)
            reviewFeeCommonAjaxDo.getHosName(Trim($F("hosId")), fillHosNameValue);
        else
            $("divHosName").innerHTML = "";
    }

    function fillHosNameValue(hosName) {
        if (hosName != null && hosName != "")
            $("divHosName").innerHTML = "(" + hosName + ")";
        else
            $("divHosName").innerHTML = "";
    }
    <%-- ] --%>
    
    <%-- 受款人身分證號 變更時 設定 性別 --%>
    function setBenSex() {
        if (getRadioValue("benNationTyp") == "1") {
            if (Trim($F("benIdnNo")).length > 2) {
                var sex = $F("benIdnNo").substring(1, 2);
                if (sex == "1" || sex == "2")
                    setRadioValue("benSex", sex);
            }
        }
    }
    
    <%-- 國籍別 變更時的處理 --%>
    function doBenNationTypChange() {
        if (getRadioValue("benNationTyp") == "1") {
            $("divSex").style.display = "none";
            $("divNation").style.display = "none";

            $("tmpBenNationCode").value = "000";
            $("benNationCode").value = "000";

            if (Trim($F("benIdnNo")).length > 2) {
                var sex = $F("benIdnNo").substring(1, 2);
                if (sex == "1" || sex == "2")
                    setRadioValue("benSex", sex);
            }

            // 通訊地址欄位處理
            // [
            $("divCommTyp").style.display = "inline";
            setRadioValue("commTyp", "2");
            $("commZip").value = "";
            $("commAddr").value = "";
            doCommTypChange();
            // ]
        }
        else {
            $("divSex").style.display = "inline";
            $("divNation").style.display = "inline";
            
            // 通訊地址欄位處理
            // [
            setRadioValue("commTyp", "2");
            $("divCommTyp").style.display = "none";
            $("commZip").value = "";
            $("commAddr").value = "";
            doCommTypChange();
            // ]
        }
    }

    <%-- 國籍 變更時的處理 --%>
    function doBenNationCodeChange() {
        $("tmpBenNationCode").value = $F("benNationCode");
    }

    function doTmpBenNationCodeChange() {
        $("benNationCode").value = $F("tmpBenNationCode");
    }
    
    <%-- 給付方式 變更時的處理 --%>
    function doPayTypChange() {
        if ($F("payTyp") == "1" || $F("payTyp") == "2") {
            setTextInputMaxLength($("accountNo1"), 7);

            $("divBank").style.display = "inline";

            $("divAccountNo1").style.display = "inline";
            $("divAccountNo2").style.display = "inline";
            $("divBankName").style.display = "none";
            $("divAccName").style.display = "none";

            if ($F("accountNo1").length > 7)
                $("accountNo1").value = Trim($F("accountNo1").substring(0, 7));

            $("bankName").value = "";
            $("accName").value = "";
        }
        else if ($F("payTyp") == "5" || $F("payTyp") == "6") {
            setTextInputMaxLength($("accountNo1"), 21);

            $("divBank").style.display = "inline";

            $("divAccountNo1").style.display = "inline";
            $("divAccountNo2").style.display = "none";
            $("divBankName").style.display = "inline";
            $("divAccName").style.display = "inline";
            
            $("accountNo2").value = "";
            $("accName").value = $F("benName");
        }
        else {
            $("divBank").style.display = "none";
            
            $("divAccountNo1").style.display = "none";
            $("divAccountNo2").style.display = "none";
            $("divBankName").style.display = "none";
            $("divAccName").style.display = "none";
            
            $("accountNo1").value = "";
            $("accountNo2").value = "";
            $("bankName").value = "";
            $("accName").value = "";
        }
    }
    
    <%-- 通訊地址別 變更時的處理 --%>
    function doCommTypChange() {
        if (getRadioValue("commTyp") == "1") {
            $("commZip").value = "";
            $("commAddr").value = "";
            $("divCommAddr").style.display = "none";
        }
        else {
            $("divCommAddr").style.display = "inline";
        }
    }
    
    <%-- 複檢費用 非複檢必須費用 檢核 --%>
    function checkFees() {
        if ($F("reFees").length > 0) {
            if (parseNumber($F("reFees")) < 0) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.reviewFee.receipt.reFees" />」不得小於 0');
                $("reFees").focus();
                return false;
            }
        }
        
        if ($F("nonreFees").length > 0) {
            if (parseNumber($F("nonreFees")) < 0) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.reviewFee.receipt.nonreFees" />」不得小於 0');
                $("nonreFees").focus();
                return false;
            }
            
            if (parseNumber($F("reFees")) - parseNumber($F("nonreFees")) < 0) {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.reviewFee.receipt.reFees" />」 - 「<bean:message bundle="<%=Global.BA_MSG%>" key="label.reviewFee.receipt.nonreFees" />」不得小於 0, 請重新確認');
                $("reFees").focus();
                return false;
            }
        }
    
        return true;
    }
    
    <%-- 性別 國籍 檢核 --%>
    function checkSexAndNation() {
        if (getRadioValue("benNationTyp") == "2") {
            if (getRadioValue("benSex") == null || getRadioValue("benSex") == "") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.reviewFee.receipt.benSex" />」為必填欄位');
                return false;
            }

            if ($F("benNationCode") == "") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.reviewFee.receipt.benNationCode" />」為必填欄位');
                return false;
            }
        }

        return true;
    }
    
    <%-- 金融機構名稱 戶名 檢核 --%>
    function checkBankNameAndAccName() {
        if ($F("payTyp") == "5" || $F("payTyp") == "6") {
            if (Trim($F("bankName")) == "") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.reviewFee.receipt.bankName" />」為必填欄位');
                $("bankName").focus();
                return false;
            }

            if (Trim($F("accName")) == "") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.reviewFee.receipt.accName" />」為必填欄位');
                $("accName").focus();
                return false;
            }
        }
        
        return true;
    }

    <%-- 地址 檢核 --%>
    function checkCommAddr() {
        if (getRadioValue("commTyp") == "2") {
            if (Trim($F("commZip")) == "") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.reviewFee.receipt.commZip" />」為必填欄位');
                $("commZip").focus();
                return false;
            }
            
            if (Trim($F("commAddr")) == "") {
                alert('「<bean:message bundle="<%=Global.BA_MSG%>" key="label.reviewFee.receipt.commAddr" />」為必填欄位');
                $("commAddr").focus();
                return false;
            }
        }

        return true;
    }

    function checkFields() {
        if (checkFees() && checkSexAndNation() && checkBankNameAndAccName() && checkCommAddr())
            return true;
        else
            return false;
    }

    function initAll() {
        doBenNationTypChange();
        doBenNationCodeChange();
        doPayTypChange();
        doCommTypChange();
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
        <html:form action="/reviewFeeReceipt" method="post" onsubmit="return validateReviewFeeReceiptForm(this);">
        
        <fieldset>
            <legend>&nbsp;複檢費用受理作業&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right">
                        <html:hidden styleId="page" property="page" value="2" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" id="btnConfirm" name="btnConfirm" tabindex="100" class="button" value="確　認" onclick="$('page').value='2'; $('method').value='doInsert'; if (document.ReviewFeeReceiptForm.onsubmit() && checkFields()){document.ReviewFeeReceiptForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input type="button" id="btnReset" name="btnReset" tabindex="101" class="button" value="清  除" onclick="document.ReviewFeeReceiptForm.reset(); initAll();" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" id="btnBack" name="btnBack" tabindex="102" class="button" value="返　回" onclick="if (confirm('確定離開？')) { location.href='<c:url value="/enterReviewFeeReceipt.do?parameter=enterReviewFeeReceipt"/>'; } else {return false;}" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">複檢費用受理編號：</span>
                                    <c:out value="${caseData.reApNoString}" />
                                </td>
                                <td colspan="3">
                                    <span class="issuetitle_L_down">複檢費用申請序：</span>
                                    <c:out value="${caseData.apSeq}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${caseData.apNoString}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">給付別：</span>
                                    失能年金
                                </td>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${caseData.appDateString}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${caseData.evtName}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${caseData.evtIdnNo}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${caseData.evtBrDateString}" />
                                </td>
                            </tr>
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">診斷失能日期：</span>
                                    <c:out value="${caseData.evtJobDateString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">失能項目：</span>
                                    <c:out value="${caseData.criInJdpString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">失能等級：</span>
                                    <c:out value="${caseData.criInJclString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">傷病分類：</span>
                                    <c:out value="${caseData.evTypString}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                            <tr>
                                <td colspan="3" class="issuetitle_L">
                                    <span class="systemMsg">▲</span>&nbsp;申請資料
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">通知複檢日期：</span>
                                    <html:text styleId="inreDate" property="inreDate" styleClass="textinput" size="10" maxlength="7" tabindex="1" />
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">複檢原因：</span>
                                    <html:select styleId="reasCode" property="reasCode" tabindex="2">
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="reasCodeOptionList" />
                                    </html:select>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">複檢醫院代碼：</span>
                                    <html:text styleId="hosId" property="hosId" styleClass="textinput" size="10" maxlength="10" tabindex="3" onkeyup="doHosIdChange();" onblur="doHosIdChange();" />
                                    <span id="divHosName" class="formtxt">
                                        <bean:write name="ReviewFeeReceiptForm" property="hosName" />
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">複檢費用申請日期：</span>
                                    <html:text styleId="recosDate" property="recosDate" styleClass="textinput" size="10" maxlength="7" tabindex="4" />
                                </td>
                                <td id="iss">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">複檢門診次數：</span>
                                    <html:text styleId="reNum" property="reNum" styleClass="textinput" size="2" maxlength="2" tabindex="5" />
                                </td>
                                <td id="iss">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">複檢住院天數：</span>
                                    <html:text styleId="rehpStay" property="rehpStay" styleClass="textinput" size="2" maxlength="2" tabindex="6" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">複檢費用：</span>
                                    <html:text styleId="reFees" property="reFees" styleClass="textinput" size="10" maxlength="7" tabindex="7" />
                                </td>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">非複檢必須費用：</span>
                                    <html:text styleId="nonreFees" property="nonreFees" styleClass="textinput" size="10" maxlength="7" tabindex="8" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" class="issuetitle_L">
                                    <br />
                                    <span class="systemMsg">▲</span>&nbsp;受款人資料
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">關　係：</span>
                                    <html:select styleId="benEvtRel" property="benEvtRel" tabindex="9">
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="benEvtRelOptionList" />
                                    </html:select>
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">核定格式：</span>
                                    <html:text styleId="notifyForm" property="notifyForm" styleClass="textinput" size="3" maxlength="3" tabindex="10" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">受款人姓名：</span>
                                    <html:text styleId="benName" property="benName" styleClass="textinput" size="50" maxlength="50" tabindex="11" />
                                </td>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">受款人身分證號：</span>
                                    <html:text styleId="benIdnNo" property="benIdnNo" styleClass="textinput" size="15" maxlength="10" tabindex="12" onblur="this.value = asc(this.value).toUpperCase(); setBenSex();" />
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">受款人出生日期：</span>
                                    <html:text styleId="benBrDate" property="benBrDate" styleClass="textinput" size="10" maxlength="8" tabindex="13" />
                                </td>
                            </tr>
                            <tr>
                                <td width="33%" id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">國籍別：</span>
                                    <span class="formtxt">
                                        <html:radio styleId="benNationTyp" property="benNationTyp" value="1" tabindex="14" onblur="doBenNationTypChange();" onclick="doBenNationTypChange();" onchange="doBenNationTypChange();" />本國
                                        <html:radio styleId="benNationTyp" property="benNationTyp" value="2" tabindex="15" onblur="doBenNationTypChange();" onclick="doBenNationTypChange();" onchange="doBenNationTypChange();" />外籍
                                    </span>
                                </td>
                                <td width="33%" id="iss">
                                    <div id="divSex">
                                        &nbsp;&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">性　別：</span>
                                        <span class="formtxt">
                                            <html:radio styleId="benSex" property="benSex" value="1" tabindex="16" />男
                                        </span>
                                        <span class="formtxt">
                                            <html:radio styleId="benSex" property="benSex" value="2" tabindex="17" />女
                                        </span>
                                    </div>
                                </td>
                                <td width="34%" id="iss">
                                    <div id="divNation">
                                        &nbsp;&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">國　籍：</span>
                                        <html:text styleId="tmpBenNationCode" property="tmpBenNationCode" styleClass="textinput" size="3" maxlength="3" tabindex="18" onchange="doTmpBenNationCodeChange();" onblur="doTmpBenNationCodeChange();"/>
                                        &nbsp;
                                        <html:select styleId="benNationCode" property="benNationCode" tabindex="19" onchange="doBenNationCodeChange();" onblur="doBenNationCodeChange();">
                                            <html:option value="">請選擇...</html:option>
                                            <html:optionsCollection label="cname" value="countryId" property="countryOptionList" />
                                        </html:select>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">給付方式：</span>
                                    <html:select styleId="payTyp" property="payTyp" tabindex="20" onchange="doPayTypChange();">
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="payTypOptionList" />
                                    </html:select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">
                                    <span id="divBank" style="width: 100%; border-bottom-width: 0.05em; border-bottom-style: dotted; border-bottom-color: #666666;padding-bottom:3px; padding-top:4px;">
                                        <div id="divAccountNo1">
                                            <span class="needtxt">＊</span>
                                            <span class="issuetitle_L_down">帳　號：</span>
                                            <html:text styleId="accountNo1" property="accountNo1" styleClass="textinput" size="8" maxlength="7" tabindex="21" />
                                        </div>
                                        <div id="divAccountNo2">
                                            &nbsp;-&nbsp;
                                            <html:text styleId="accountNo2" property="accountNo2" styleClass="textinput" size="15" maxlength="14" tabindex="22" />
                                        </div>
                                        <div id="divBankName">
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <span class="needtxt">＊</span>
                                            <span class="issuetitle_L_down">金融機構名稱：</span>
                                            <html:text styleId="bankName" property="bankName" styleClass="textinput" size="50" maxlength="120" tabindex="23" />
                                        </div>
                                        <div id="divAccName">
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <span class="needtxt">＊</span>
                                            <span class="issuetitle_L_down">戶名：</span>
                                            <html:text styleId="accName" property="accName" styleClass="textinput" size="10" maxlength="100" tabindex="24" />
                                        </div>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">電　話1：</span>
                                    <html:text styleId="tel1" property="tel1" styleClass="textinput" size="20" maxlength="20" tabindex="25" />
                                </td>
                                <td id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="issuetitle_L_down">電　話2：</span>
                                    <html:text styleId="tel2" property="tel2" styleClass="textinput" size="20" maxlength="20" tabindex="26" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">地　址：</span>
                                    <div id="divCommTyp">
                                        <span class="formtxt">
                                            <html:radio styleId="commTyp" property="commTyp" value="1" tabindex="27" onblur="doCommTypChange();" onclick="doCommTypChange();" onchange="doCommTypChange();" />同戶籍地
                                            <html:radio styleId="commTyp" property="commTyp" value="2" tabindex="28" onblur="doCommTypChange();" onclick="doCommTypChange();" onchange="doCommTypChange();" />現住址
                                        </span>
                                    </div>
                                </td>
                                <td id="iss" colspan="2">
                                    <div id="divCommAddr">
                                        <span class="formtxt">
                                            現住址：
                                            <html:text styleId="commZip" property="commZip" styleClass="textinput" size="8" maxlength="6" tabindex="29" />
                                            (郵遞區號)
                                            &nbsp;-&nbsp;
                                            <html:text styleId="commAddr" property="commAddr" styleClass="textinput" size="72" maxlength="80" tabindex="30" onblur="this.value = unAsc(this.value.toUpperCase());" onkeyup="this.value = unAsc(this.value.toUpperCase());" />
                                        </span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span>
                        <br />
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        1.&nbsp;外藉人士請於身分證字號欄位填寫護照號碼、居留證號碼或統一編號。
                        <br />
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        2.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。
                        <br />
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        3.&nbsp;<span class="needtxt">＊</span>為必填欄位。
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
