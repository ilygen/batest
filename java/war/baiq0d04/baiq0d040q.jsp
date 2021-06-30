<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D040Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="CheckMarkLevelQueryForm" page="1" />
</head>
		
<body scroll="no">
    
    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/checkMarkLevelQuery" method="post" onsubmit="return validateCheckMarkLevelQueryForm(this);">
    
        <fieldset>
            <legend>&nbsp;編審註記查詢&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnQuery" class="button" value="確  認" onclick="$('page').value='1'; $('method').value='doQuery'; if (document.CheckMarkLevelQueryForm.onsubmit()){document.CheckMarkLevelQueryForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input type="reset" name="btnReset" class="button" value="清  除" />
                        </acl:checkButton>
			        </td>
                </tr>
                <tr>
                    <td align="right" class="issuetitle_R_down">
                                                     給付種類：
                    </td>
                    <td colspan="2">
                        <html:select styleId="chkObj" property="chkObj">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>
                </tr>
                <tr>
	                <td width="30%" align="right" class="issuetitle_R_down">
	                                           類　　別：
	                </td>
		            <td colspan="2">
		                <html:select styleId="chkGroup" property="chkGroup">
                            <html:option value="">全部</html:option>
                            <html:options collection="<%=ConstantKey.PAYCATEGORY_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>        
                </tr>	  
                <tr>
                    <td width="30%" align="right" class="issuetitle_R_down">編審註記代號：</td>
                    <td colspan="2">
                        <html:text property="chkCode" styleId="chkCode" styleClass="textinput" size="2" maxlength="2" />
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