<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X112A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/maintCommonAjaxDo.js"/>'></script> 
	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>       
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="AvgAmtMonMaintDetailForm" page="1" />
    <script language="javascript" type="text/javascript">	
	
	function checkYYY(the){
		var i;
		if(the.value == ""){
			return;
		}
		for(i=the.value.length;i<3;i++){
			the.value = '0' + the.value;
		}
	}

    <%-- 畫面重設 --%>
    function resetForm(){
        cleanForm();
        initAllForClean();
    }
    
    function initAllForClean(){
        $("effYear").value="";
        $("appMonth").value="";
    }
    Event.observe(window, 'load', initAllForClean, false);    		    
    </script>	  				
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/avgAmtMonMaintDetail" method="post" onsubmit="return validateAvgAmtMonMaintDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;勞保年金平均薪資月數參數維護作業新增&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="6" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                         <html:hidden styleId="effYearOrg" property="effYearOrg" />
                        <acl:checkButton buttonName="btnSave">
			                <input name="btnSave" type="button" class="button" value="確  認 " onclick="$('page').value='1'; $('method').value='doSave'; if (document.AvgAmtMonMaintDetailForm.onsubmit()){document.AvgAmtMonMaintDetailForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input tabindex="65" type="button" name="btnReset" class="button" value="清　除" onclick="resetForm()"/>
			            </acl:checkButton>
			            <acl:checkButton buttonName="btnBack">
			                <input name="btnBack" type="button" class="button" value="返  回 " onClick="javascript:history.back();">
			            </acl:checkButton>
		            </td>
                </tr>
                <tr>
                    <td>
                        <table width="98%" border="0" cellpadding="3" cellspacing="5" class="px13">
                            <tr>
                                <td width="20%">
                                    <span class="issuetitle_L_down">新增人員：</span>
                                    <c:out value="${userData.empNo}" />
                                </td>
                                <td width="40%">
                                    <span class="issuetitle_L_down">新增日期：</span>
                                    <c:out value="${userData.loginDate}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
	            <tr>
                    <td colspan="2" align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="5" class="px13">	    
                            <tr>
	                            <td idth="50%" id="iss" colspan="2">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">施行年度：</span>
        	                        <html:text styleId="effYear" property="effYear" size="3" maxlength="3" styleClass="textinput" onblur="checkYYY(this)"/>
                                </td>
	                            <td idth="50%" id="iss">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">採計月數：</span>
        	                        <html:text styleId="appMonth" property="appMonth" size="3" maxlength="3" styleClass="textinput" />
                                </td>
                            </tr>                            
	                    </table>
                    </td>
                </tr>	  
                <tr>
                    <td colspan="2"><hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>
            　                                                1.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
            　                                                2.&nbsp;<span class="needtxt">＊</span>為必填欄位。
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