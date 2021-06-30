<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M220X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="SetProcedureForm" page="1" />	
    <script language="javascript" type="text/javascript">

    </script>			
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/setProcedure" method="post" onsubmit="return validateSetProcedureForm(this);">
        
        <fieldset>
            <legend>&nbsp;批次程式設定作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
	  
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnInsert">
			                <input name="btnInsert" type="button" class="button" value="新  增 " onclick="" />&nbsp;&nbsp;
			            </acl:checkButton>
			            <acl:checkButton buttonName="btnQuery">
			                <input name="btnModity" type="button" class="button" value="修  改 " onclick="" />&nbsp;&nbsp;       
			            </acl:checkButton>
			            <acl:checkButton buttonName="btnClear">
			                <input name="btnClear" type="reset" class="button" value="清  除 ">
			            </acl:checkButton>
		            </td>
                </tr>
                
                <tr>
                    <td align="right" width="30%">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">程式名稱：</span>
                    </td>
                    <td>
                        <html:select styleId="procedureName" property="procedureName" tabindex="2" onchange="">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PROCEDURE_NAME_LIST%>" property="procedureName" labelProperty="procDesc" />
                        </html:select>
                    </td>
                </tr>                     	  
	  
	            <tr>
	                <td colspan="2" align="right" valign="bottom">&nbsp;</td>
                </tr>
            </table>
        </fieldset>
        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>