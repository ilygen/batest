<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X023C" />
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
    /* <!-- */
    <%-- begin 檢查必填欄位 --%>
    function checkRequireFields() {
        var msg = "";
        
        if (Trim($F("actMk")) == "")
            msg += '「格式是否有效」：為必輸欄位。\r\n';
        if (Trim($F("dataContPurpose")) == "")
            msg += '「主旨」：為必輸欄位。\r\n';
        
            
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
    
    <%-- begin 檢查必填欄位 --%>
    function checkFields() {
        var msg = "";

        if (Trim($F("dataContExplain")) == "")
            msg += '「說明」：為必輸欄位。\r\n';
            
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
        dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");               
    }

    Event.observe(window, 'load', initAll , false);
    var actionurl ="<c:url value='/adviceMaintDetail.do?method='/>";
    <%-- 畫面導向 --%>	
    function doAction(command) {
    	document.forms[0].action = actionurl + command;
    	if(command == 'doDeleteDataCount' || command == 'doModifyDataCount'){
    		document.forms[0].method = 'post';
    	}
    	document.forms[0].submit();
    }
    function doDelete() {
    	doAction("doDeleteDataCount");
    }
    function doModify() {
    	doAction("doModifyDataCount");
    }
    /* --> */
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
                        <html:hidden styleId="type" property="type" value="modify" /> 
                         <acl:checkButton buttonName="btnAdd">
                            <input name="btnAdd" type="button" class="button" value="插入代碼" onclick="$('page').value='1'; $('method').value='doAddU'; if (document.AdviceMaintDetailForm.onsubmit()){document.AdviceMaintDetailForm.submit();}else{return false;}" onmouseover="getPurposePosition(); getExplainPosition();" />&nbsp;&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnConfirm">
                            <input name="btnConfirm" type="button" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doConfirm'; if (document.AdviceMaintDetailForm.onsubmit() && checkRequireFields()){document.AdviceMaintDetailForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返　回" onClick="$('page').value='1'; $('method').value='doBack';document.AdviceMaintDetailForm.submit();">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="13%">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_L_down">給付種類：</span>
                    </td>
                    <td>
                        <c:if test='${AdviceMaintDetailForm.payCode eq "L"}'>
                            <%=ConstantKey.BAAPPBASE_PAYKIND_STR_45%>
                        </c:if>
                        <c:if test='${AdviceMaintDetailForm.payCode eq "K"}'>
                            <%=ConstantKey.BAAPPBASE_PAYKIND_STR_35%>
                        </c:if>
                        <c:if test='${AdviceMaintDetailForm.payCode eq "S"}'>
                            <%=ConstantKey.BAAPPBASE_PAYKIND_STR_56%>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_L_down">核定格式：</span>
                    </td>
                    <td>
                        <c:out value="${AdviceMaintDetailForm.authTyp}" />
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
                    <td>
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_L_down">格式是否有效：</span>
                    </td>
                    <td>
                        <html:select property="actMk" styleId="actMk" styleClass="formtxt">
                            <html:option value="Y">有效</html:option>
                            <html:option value="N">無效</html:option>
                        </html:select>
          	        </td>
                </tr>
                <tr>
                    <td valign="top">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_L_down">主　　旨：</span>
                    </td>
                    <td>
                        <html:textarea property="dataContPurpose" styleId="dataContPurpose" cols="100" rows="5" styleClass="formtxt" onmousedown="chooseDataContPurpose();" ></html:textarea>
                    </td>
                </tr>
                <tr>
                    <td valign="top">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_L_down">說　　明：</span>
                    </td>
                    <td>
                        <html:textarea property="dataContExplain" styleId="dataContExplain" cols="100" rows="5" styleClass="formtxt" onmousedown="chooseDataContExplain();" ></html:textarea>
                        <input class="button_90" type="button" name="btnAdd" value="新增說明" onclick="$('page').value='1'; $('method').value='doAddDataCount'; if (document.AdviceMaintDetailForm.onsubmit() && checkFields()){document.AdviceMaintDetailForm.submit();}else{return false;}">
                    </td>
                </tr>
                <tr>
          	        <td colspan="3">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="5" align="center"><hr size="1" noshade>
                        <bean:define id="list" name="<%=ConstantKey.ADVICE_MAINT_DATA_CONT_CASE%>" />
                    
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >
                        <display:column title="序號" style="width:8%; text-align:center;">
                            <c:out value="${listItem_rowNum}" />
                            <html:hidden styleId="rowId" property="rowId" value="${listItem_rowNum}" />
                        </display:column>
                        <display:column property="dataCont" title="說　明" style="width:30%; text-align:left;" />
                        <display:column title="資料明細" style="width:14%; text-align:center;" >
                            <%-- <input type="button" class="button" name="btnMod" value="修改" onclick="location.href='<c:url value="/adviceMaintDetail.do"/>?method=doModifyDataCount&type=modify&rowId=<c:out value="${listItem_rowNum}" />';"> --%>
<%--                             <input type="button" class="button" name="btnDel" value="刪除" onclick="location.href='<c:url value="/adviceMaintDetail.do"/>?method=doDeleteDataCount&type=modify&rowId=<c:out value="${listItem_rowNum}" />';"> --%>
							<input type="button" class="button" name="btnMod" value="修改" onclick="doModify()">
							<input type="button" class="button" name="btnDel" value="刪除" onclick="doDelete()">
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