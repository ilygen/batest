<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X033C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="DecisionLevelMaintDetailForm" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    var payCode = '<c:out value="${DecisionLevelMaintDetailForm.payCode}" />';

    <%-- begin 檢查必填欄位 --%>
    function checkRequireFields() {
        checkLevel();
        var msg = "";
        
        if (Trim($F("stdAmt")) == "")
            msg += '「決行金額」：為必輸欄位。\r\n';
        if(isNaN($("stdAmt").value))
            msg += '「決行金額」：必須為數字。\r\n';
        if ($("echk").value == "false" && $("ochk").value == "false" && $("wchk").value == "false")
            msg += '「錯誤」、「穿透」、「警告」至少必須點選一個欄位。\r\n';
            
        if (msg != "") {
            alert(msg);
            return false;
        }
        else {
            return true;
        }      
    }
    <%-- ] ... end --%>
    
    // 變更 年金 時畫面異動    
    function changePayCode(){
        if(payCode == "L"){
            $("hicLevelContent").style.display="none";
            $("nlwkmkContent").style.display="none";  
        } 
        else {
            $("hicLevelContent").style.display="inline";
            $("nlwkmkContent").style.display="inline";   
        }
    }
    
    function checkLevel() {
       if ($("echkLevel").checked){
         $("echk").value = "true";
       }else{
         $("echk").value = "false";
       }        
       if ($("ochkLevel").checked){
         $("ochk").value = "true";
       }else{
         $("ochk").value = "false";
       }
       if ($("wchkLevel").checked){
         $("wchk").value = "true";
       }else{
         $("wchk").value = "false";
       }
    }
       
    function initAll() {        
        changePayCode();                   
    }

    Event.observe(window, 'load', initAll , false);
    -->
    </script>		
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
  
    <div id="main" class="mainBody">
        <html:form action="/decisionLevelMaintDetail" method="post" onsubmit="return validateDecisionLevelMaintDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;決行層級維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" /> 
                        <acl:checkButton buttonName="btnConfirm">
                            <input name="btnConfirm" type="button" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doConfirm'; if (document.DecisionLevelMaintDetailForm.onsubmit() && checkRequireFields()){document.DecisionLevelMaintDetailForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnDelete">
                            <input name="btnDelete" type="button" class="button" value="刪　除" onclick="if (confirm('是否確定刪除?')){$('page').value='1'; $('method').value='doDelete'; if (document.DecisionLevelMaintDetailForm.onsubmit()){document.DecisionLevelMaintDetailForm.submit();}else{return false;}}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返　回" onClick="javascript:history.back();">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
          	        <td align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                            <tr>
                                <td colspan="2" id="iss">
                                    <span class="needtxt">＊</span><span class="issuetitle_R_down">給付別：</span>
                                    <c:if test='${DecisionLevelMaintDetailForm.payCode eq "L"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_45%>
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.payCode eq "K"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_36%>
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.payCode eq "S"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_56%>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" id="iss">
                                    <span class="needtxt">＊</span><span class="issuetitle_R_down">給付種類：</span>
                                    <c:if test='${DecisionLevelMaintDetailForm.payKind eq "45"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_45%>
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.payKind eq "48"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_48%>
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.payKind eq "49"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_49%>
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.payKind eq "36"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_36%>
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.payKind eq "37"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_37%>
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.payKind eq "38"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_38%>
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.payKind eq "56"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_56%>
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.payKind eq "57"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_57%>
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.payKind eq "58"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_58%>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>    
                                <td width="50%" id="iss">
                                    <span class="needtxt">＊</span><span class="issuetitle_R_down">決行層級：</span>
                                    <c:if test='${DecisionLevelMaintDetailForm.rechklvl eq "1"}'>
                                                                                                             承辦人員
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.rechklvl eq "2"}'>
                                                                                                             複核
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.rechklvl eq "3"}'>
                                                                                                             科長
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.rechklvl eq "4"}'>
                                                                                                             一等專員
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.rechklvl eq "5"}'>
                                                                                                             副經理
                                    </c:if>
                                    <c:if test='${DecisionLevelMaintDetailForm.rechklvl eq "6"}'>
                                                                                                             經理
                                    </c:if>
                                    
                                </td>
                                <td width="50%" id="iss"><span class="needtxt">＊</span><span class="issuetitle_R_down">決行金額：</span>
                                    <html:text property="stdAmt" styleId="stdAmt" styleClass="textinput" size="10" maxlength="7" />
                                </td>        
                            </tr>
                            <tr>    
                                <td width="50%" id="iss">
                                    <div id="hicLevelContent">
                                        <span class="issuetitle_R_down">失能等級：</span>
                                        <html:text property="hicLevel" styleId="hicLevel" styleClass="textinput" size="2" maxlength="2" />
                                    </div>
                                </td>
                                <td width="50%" id="iss">
                                    <div id="nlwkmkContent">
                                        <span class="issuetitle_R_down">普職限制：</span>
                                        <html:select styleId="nlwkmk" property="nlwkmk">
                                            <html:option value="">請選擇</html:option>
                                            <html:option value="<%=ConstantKey.NLWKMK_1%>">普通</html:option>
                                            <html:option value="<%=ConstantKey.NLWKMK_2%>">職災</html:option>
                                        </html:select>
                                    </div>
                                </td>        
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div id="selectItem"></div>
                                </td>        
                            </tr>
                            <tr>
                                <td colspan="2" id="iss">
                                <span class="needtxt">＊</span>
                                <span class="issuetitle_R_down">註記程度決行權限：</span>
                                <span class="formtxt">
                                    <html:checkbox styleId="echkLevel" property="echkLevel" onclick="checkLevel();"/>1.錯誤程度(E)
                                    <html:hidden styleId="echk" property="echk" value="false" />
                                    <html:checkbox styleId="ochkLevel" property="ochkLevel" onclick="checkLevel();"/>2.穿透程度(O)
                                    <html:hidden styleId="ochk" property="ochk" value="false" />
                                    <html:checkbox styleId="wchkLevel" property="wchkLevel" onclick="checkLevel();"/>3.警告程度(W)
                                    <html:hidden styleId="wchk" property="wchk" value="false" />
                                </span></td>        
                            </tr>
                            <tr>
                	            <td colspan="2"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>&nbsp;
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