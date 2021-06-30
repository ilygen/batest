<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X013C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
   	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
   	<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
   	<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
   	<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="CheckMarkMaintDetailForm" page="1" />	
    <script type='text/javascript'>
    <%-- begin 檢查必填欄位 --%>
    function checkRequireFields() {
        var msg = "";
         <%-- 
        if (Trim($F("chkCode")) == "")
            msg += '「編審註記代號」：為必輸欄位。\r\n';
        if (Trim($F("chkObj")) == "")
            msg += '「給付種類」：為必輸欄位。\r\n';
        --%>
        if (Trim($F("chkLevel")) == "")
            msg += '「嚴重程度」：為必輸欄位。\r\n';
        if (Trim($F("chkGroup")) == "")
            msg += '「類別」：為必輸欄位。\r\n';
        if (Trim($F("valibDate")) == "")
            msg += '「有效日期起」：為必輸欄位。\r\n';
        if (Trim($F("valibDate")) != "" && (isNaN($("valibDate").value) || $F("valibDate").length != 7))
            msg += '「有效日期起」格式錯誤，請重新確認。\r\n';
            
        if (Trim($F("valieDate")) == "")
            msg += '「有效日期迄」：非必輸欄位。\r\n';
        if (Trim($F("valieDate")) != "" && (isNaN($("valieDate").value) || $F("valieDate").length != 7))
            msg += '「有效日期迄」格式錯誤，請重新確認。\r\n';
            
        if (Trim($F("chkDesc")) == "")
            msg += '「中文說明」：為必輸欄位。\r\n';
        if (Trim($F("chkCondesc")) == "")
            msg += '「編審條件」：為必輸欄位。\r\n';
            
        if (msg != "") {
            alert(msg);
            return false;
        }
        else {
            return true;
        }      
    }
    <%-- ] ... end --%>
    </script>						
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/checkMarkMaintDetail" method="post" onsubmit="return validateCheckMarkMaintDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;編審註記維護作業&nbsp;</legend>
          
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnConfirm">
			                <input name="btnConfirm" type="button" class="button" value="確  認 " onclick="$('page').value='1'; $('method').value='doConfirm'; if (document.CheckMarkMaintDetailForm.onsubmit() && checkRequireFields()){document.CheckMarkMaintDetailForm.submit();}else{return false;}" />&nbsp;&nbsp;
			            </acl:checkButton>
			            <acl:checkButton buttonName="btnBack">
			                <input name="btnBack" type="button" class="button" value="返  回 " onClick="javascript:history.back();">
			            </acl:checkButton>
		            </td>
                </tr>  
                <tr>
                    <td colspan="2" align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="5" class="px13">	  
	                        <tr>
	                            <td idth="40%" id="iss">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">編審註記代號：</span>
	                                <c:out value="${CheckMarkMaintDetailForm.chkCode}" />
                                </td>
                                <td width="60%" id="iss">
                                    <span class="needtxt">＊</span><span class="issuetitle_L_down">給付別：</span>
                                    <c:if test='${CheckMarkMaintDetailForm.chkObj eq "L"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_45%>
                                    </c:if>
                                    <c:if test='${CheckMarkMaintDetailForm.chkObj eq "K"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_36%>
                                    </c:if>
                                    <c:if test='${CheckMarkMaintDetailForm.chkObj eq "S"}'>
                                        <%=ConstantKey.BAAPPBASE_PAYKIND_STR_56%>
                                    </c:if>
		                        </td>
                            </tr>	  
	                        <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span><span class="issuetitle_L_down">嚴重程度：</span>
			                        <html:select styleId="chkLevel" property="chkLevel">
                                        <html:option value="">請選擇</html:option>
                                        <html:options collection="<%=ConstantKey.CHKLEVEL_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                    </html:select>
		                        </td>		
		                        <td id="iss">
		                            <span class="needtxt">＊</span><span class="issuetitle_L_down">類別：</span>
			                        <html:select styleId="chkGroup" property="chkGroup">
                                        <html:option value="">請選擇</html:option>
                                        <html:options collection="<%=ConstantKey.PAYCATEGORY_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                    </html:select>
		                        </td>
	                        </tr>		
	                        <tr>
		                        <td id="iss" colspan="2">
		                            <span class="needtxt">＊</span><span class="issuetitle_L_down">有效日期起迄：</span>
		                            <html:text property="valibDate" styleId="valibDate" styleClass="textinput" size="10" maxlength="7" />&nbsp;~&nbsp;
                                    <html:text property="valieDate" styleId="valieDate" styleClass="textinput" size="10" maxlength="7" />
		                        </td>
	                        </tr>	  
	                        <tr>
	                            <td id="iss" colspan="2">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">中文說明：</span>
        	                        <html:text property="chkDesc" styleId="chkDesc" styleClass="textinput" size="100" maxlength="100" />	 
                                </td>		
	                        </tr>		
	                        <tr>
	                            <td id="iss" colspan="2">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">編審條件：</span>
        	                        <html:text property="chkCondesc" styleId="chkCondesc" styleClass="textinput" size="100" maxlength="100" />
                                </td>		
	                        </tr>		  
	                        <tr>
	                            <td id="iss" colspan="2">
	                                <span class="issuetitle_L_down">法令依據：</span>
                                    <html:text property="chkLawdesc" styleId="chkLawdesc" styleClass="textinput" size="100" maxlength="100" />		 
                                </td>		
	                        </tr>
	                    </table>
                    </td>
                </tr>	  
	            <tr>
	                <td colspan="2" align="right">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2"><hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>
            　                                                 1.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
            　                                                 2.&nbsp;<span class="needtxt">＊</span>為必填欄位。<br>
            　                                                 3.&nbsp;編審註記有效日期為永久者，請於有效迄欄位輸入9991231。
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