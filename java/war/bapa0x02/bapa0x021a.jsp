<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X021A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="AdviceMaintDetailForm" page="1" />
    <script language="javascript" type="text/javascript">
    <%-- begin 檢查必填欄位 --%>
    function checkRequireFields() {
        var msg = "";
        
        if (Trim($F("payCode")) == "")
            msg += '「給付別」：為必輸欄位。\r\n';
        if (Trim($F("authTyp")) == "")
            msg += '「核定格式」：為必輸欄位。\r\n';
        if (Trim($F("dataContPurpose")) == "")
            msg += '「主旨」：為必輸欄位。\r\n';
        //if (Trim($F("dataContExplain")) == "")
        //    msg += '「說明」：為必輸欄位。\r\n';
        //    
        if (msg != "") {
            alert(msg);
            return false;
        }
        else {
            return true;
        }      
    }
    
    //取得滑鼠在text位置
    function getInputSelection(el) {
    var start = 0, end = 0, normalizedValue, range,
        textInputRange, len, endRange;

    if (typeof el.selectionStart == "number" && typeof el.selectionEnd == "number") {
        start = el.selectionStart;
        end = el.selectionEnd;
    } else {
        range = document.selection.createRange();

        if (range && range.parentElement() == el) {
            len = el.value.length;
            normalizedValue = el.value.replace(/\r\n/g, "\n");

            // Create a working TextRange that lives only in the input
            textInputRange = el.createTextRange();
            textInputRange.moveToBookmark(range.getBookmark());

            // Check if the start and end of the selection are at the very end
            // of the input, since moveStart/moveEnd doesn't return what we want
            // in those cases
            endRange = el.createTextRange();
            endRange.collapse(false);

            if (textInputRange.compareEndPoints("StartToEnd", endRange) > -1) {
                start = end = len;
            } else {
                start = -textInputRange.moveStart("character", -len);
                start += normalizedValue.slice(0, start).split("\n").length - 1;

                if (textInputRange.compareEndPoints("EndToEnd", endRange) > -1) {
                    end = len;
                } else {
                    end = -textInputRange.moveEnd("character", -len);
                    end += normalizedValue.slice(0, end).split("\n").length - 1;
                }
            }
        }
    }

    return {
        start: start,
        end: end
    };
    }

    function getPurposePosition() {
        var cursorPurposePosition = getInputSelection($("dataContPurpose")).start;
        $("cursorPurposePosition").value = cursorPurposePosition;
    }
    
    function getExplainPosition() {
        var cursorExplainPosition = getInputSelection($("dataContExplain")).start;
        $("cursorExplainPosition").value = cursorExplainPosition;
    }

    //判斷選取主旨或說明欄位
    function chooseDataContPurpose() {
        $("chooseData").value = "1";
    }
    
     function chooseDataContExplain() {
         $("chooseData").value = "2";
    }
    <%-- ] ... end --%>
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        document.AdviceMaintDetailForm.reset();
        initAll();       
    }
    <%-- ] ... end --%>
    
    function initAll() {
        dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");    
    }

    Event.observe(window, 'load', initAll , false);
    -->
    </script>
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/adviceMaintDetail" method="post" onsubmit="return validateAdviceMaintDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;核定通知書維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="chooseData" property="chooseData" value="" />
                        <html:hidden styleId="cursorPurposePosition" property="cursorPurposePosition" value="0" />
                        <html:hidden styleId="cursorExplainPosition" property="cursorExplainPosition" value="0" />
                        <html:hidden styleId="type" property="type" value="add" />
                        <acl:checkButton buttonName="btnAdd">
                            <input name="btnAdd" type="button" class="button" value="插入代碼" onclick="$('page').value='1'; $('method').value='doAddA'; if (document.AdviceMaintDetailForm.onsubmit()){document.AdviceMaintDetailForm.submit();}else{return false;}" onmouseover="getPurposePosition(); getExplainPosition();" />&nbsp;&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnSave">
                            <input name="btnSave" type="button" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doSave'; if (document.AdviceMaintDetailForm.onsubmit() && checkRequireFields()){document.AdviceMaintDetailForm.submit();}else{return false;}" />&nbsp;&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input name="btnClear" type="reset" class="button" value="清　除" onclick="resetForm();">&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返　回" onClick="$('page').value='1'; $('method').value='doBack';document.AdviceMaintDetailForm.submit();">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="10%">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_L_down">給付別：</span>
                    </td>
                    <td>
                        <html:select styleId="payCode" property="payCode">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_L_down">核定格式：</span>
                    </td>
                    <td>
                        <html:text property="authTyp" styleId="authTyp" styleClass="textinput" maxlength="3" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="needtxt">　</span>
                        <span class="issuetitle_L_down">格式說明：</span>
                    </td>
                    <td>
                        <html:text property="authTypDesc" styleId="authTypDesc" styleClass="textinput" maxlength="30" size="70" />
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_L_down">主　　旨：</span>
                    </td>
                    <td colspan="2">
                        <html:textarea property="dataContPurpose" styleId="dataContPurpose" cols="100" rows="5" styleClass="formtxt" onmousedown="chooseDataContPurpose();" ></html:textarea>
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_L_down">說　　明：</span>
                    </td>
                    <td colspan="2">
                        <html:textarea property="dataContExplain" styleId="dataContExplain" cols="100" rows="5" styleClass="formtxt" onmousedown="chooseDataContExplain();" ></html:textarea>
                        <input class="button" type="button" name="btnAdd" value="新增說明" onclick="$('page').value='1'; $('method').value='doAddDataCount'; if (document.AdviceMaintDetailForm.onsubmit()){document.AdviceMaintDetailForm.submit();}else{return false;}">
                    </td>
                </tr>
                <tr>
        	        <td colspan="3" align="right" valign="bottom"></td>
                </tr>
                <tr>
                    <td colspan="5" align="center"><hr size="1" noshade>
                        <bean:define id="list" name="<%=ConstantKey.ADVICE_MAINT_DATA_CONT_CASE%>" />
                    
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >
                        <display:column title="序號" style="width:8%; text-align:center;">
                            <c:out value="${listItem_rowNum}" />
                        </display:column>
                        <display:column property="dataCont" title="說　明" style="width:30%; text-align:left;" />
                        <display:column title="資料明細" style="width:14%; text-align:center;" >
                            <input type="button" class="button" name="btnMod" value="修改" onclick="location.href='<c:url value="/adviceMaintDetail.do"/>?method=doModifyDataCount&type=add&rowId=<c:out value="${listItem_rowNum}" />';">
                            <input type="button" class="button" name="btnDel" value="刪除" onclick="location.href='<c:url value="/adviceMaintDetail.do"/>?method=doDeleteDataCount&type=add&rowId=<c:out value="${listItem_rowNum}" />';">
                        </display:column>
                        </display:table>
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