<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X010F" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="CheckMarkMaintQueryForm" page="1" />	
    <script language="javascript" type="text/javascript">
    <%-- begin 檢查Caub退保註記與變更名稱管制註記 [ --%>
    function checkFields() {
        if(Trim($("chkObj").value) == ""){
            alert("「給付種類」：為必輸欄位");
            return false;                      
        }  
        else
        {
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
        <html:form action="/checkMarkMaintQuery" method="post" onsubmit="return validateCheckMarkMaintQueryForm(this);">
        
        <fieldset>
            <legend>&nbsp;編審註記維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
	  
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnInsert">
			                <input name="btnInsert" type="button" class="button" value="新  增 " onclick="$('page').value='1'; $('method').value='doInsert'; if (document.CheckMarkMaintQueryForm.onsubmit()){document.CheckMarkMaintQueryForm.submit();}else{return false;}" />&nbsp;&nbsp;
			            </acl:checkButton>
			            <acl:checkButton buttonName="btnQuery">
			                <input name="btnQuery" type="button" class="button" value="修  改 " onclick="$('page').value='1'; $('method').value='doQuery'; if (document.CheckMarkMaintQueryForm.onsubmit() && checkFields()){document.CheckMarkMaintQueryForm.submit();}else{return false;}" />&nbsp;&nbsp;       
			            </acl:checkButton>
			            <acl:checkButton buttonName="btnClear">
			                <input name="btnClear" type="reset" class="button" value="清  除 ">
			            </acl:checkButton>
		            </td>
                </tr>     	  
	            <tr>
                    <td width="30%" align="right" class="issuetitle_R_down">
                                                     編審註記代號：
                    </td>
                    <td>
                        <html:text property="chkCode" styleId="chkCode" styleClass="textinput" size="2" maxlength="2" />
		            </td>
                </tr>	  
	            <tr>
                    <td width="30%" align="right" class="issuetitle_R_down">
                                                     類別：
                    </td>
                    <td>
		                <html:select styleId="chkGroup" property="chkGroup">
                            <html:option value="">全部</html:option>
                            <html:options collection="<%=ConstantKey.PAYCATEGORY_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
		            </td>
                </tr>	  
	            <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span><span class="issuetitle_R_down">給付種類：</span>
                    </td>
                    <td>
		                <html:select styleId="chkObj" property="chkObj">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
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