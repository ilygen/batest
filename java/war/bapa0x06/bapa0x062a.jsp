<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X062A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/maintCommonAjaxDo.js"/>'></script> 
	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>       
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="CpiDtlMaintDetailForm" page="1" />
    <script language="javascript" type="text/javascript">	

	<%-- begin 檢查必填欄位 --%>
    function checkRequireFields() {
		var msg = "";
	    
	    if(Trim($("issuYear").value) == "")
            msg += '「核定年度」：為必輸欄位。\r\n';
        if(Trim($("adjYearS").value) == "")
            msg += '「申請年度(起)」：為必輸欄位。\r\n';   
        if(Trim($("adjYearE").value) == "")
            msg += '「申請年度(迄)」：為必輸欄位。\r\n';               
        if(Trim($("cpiYear").value) == "")
            msg += '「指數年度(起)」：為必輸欄位。\r\n';           
        if(Trim($("cpiYearE").value) == "")
            msg += '「指數年度(迄)」：為必輸欄位。\r\n';   
        if(Trim($("cpiB").value) == "")
            msg += '「物價指數(起)」：為必輸欄位。\r\n'; 
        if(Trim($("cpiE").value) == "")   
            msg += '「物價指數(迄)」：為必輸欄位。\r\n';                
		if(Trim($("reqRpno").value) == "")
            msg += '「報請核定文號」：為必輸欄位。\r\n';	            
		if(Trim($("issuRpno").value) == "")
          	msg += '「核定文號」：為必輸欄位。\r\n';          		
		if(Trim($("issuDesc").value) == "")
            msg += '「核定結果」：為必輸欄位。\r\n';            	            	
            		
		if (msg != "") {
		    alert(msg);
		    return false;
		} else {
            return true;
        }  						
	}
	
	function checkYYY(the){
		var i;
		if(the.value == ""){
			return;
		}
		for(i=the.value.length;i<3;i++){
			the.value = '0' + the.value;
		}
	}
	
    <%-- 計算成長率 --%>
    function setCpIndex(){
        var cpiB = $("cpiB").value*1;
		var cpiE = $("cpiE").value*1;
		var cpIndex = (cpiE-cpiB)/cpiB;
		var gtcpIndex = Math.round(cpIndex * 1000000)/1000000*100;
	    //無條件捨去至小數第二位，與計算無關，只放欄位用
	    var growT = Math.round(gtcpIndex*100)/100;
	    var growTString = growT.toString();
	    $("cpIndex").value = growTString;
	    
	    <%-- 如值為NaN或Infinity 替換為0 --%>
	    if($("cpIndex").value == 'NaN'){ 
	    $("cpIndex").value = '0';
	    }
	    if($("cpIndex").value == 'Infinity'){ 
	    $("cpIndex").value = '0';
	    }
	}	
	
    <%-- 畫面重設 --%>
    function resetForm(){
        cleanForm();
        initAllForClean();
        $("adjYearStrDis").innerHTML = "";
    }
    
    function initAllForClean(){
        $("adjYearS").value="";
        $("adjYearE").value="";
    }
    Event.observe(window, 'load', initAllForClean, false);    		    
    </script>	  				
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/cpiDtlMaintDetail" method="post" onsubmit="return validateCpiDtlMaintDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;物價指數調整明細資料新增&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="6" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnSave">
			                <input name="btnSave" type="button" class="button" value="確  認 " onclick="$('page').value='1'; $('method').value='doSave'; if (document.CpiDtlMaintDetailForm.onsubmit() && checkRequireFields()){document.CpiDtlMaintDetailForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input tabindex="65" type="button" name="btnReset" class="button" value="清　除" onclick="resetForm()"/>&nbsp;
			            </acl:checkButton>
			            <acl:checkButton buttonName="btnBack">
			                <input name="btnBack" type="button" class="button" value="返  回 " onClick="$('page').value='1'; $('method').value='doBack'; if (document.CpiDtlMaintDetailForm.onsubmit()){document.CpiDtlMaintDetailForm.submit();}else{return false;}">
			            </acl:checkButton>
		            </td>
                </tr>
                <tr>
                    <td>
                        <table width="98%" border="0" cellpadding="3" cellspacing="5" class="px13">
                            <tr>
                                <td width="40%">
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
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">核定年度：</span>
        	                        <html:text styleId="issuYear" property="issuYear" size="3" maxlength="3" styleClass="textinput" onblur="checkYYY(this)" readonly="true" />
                                </td>
                                <td idth="50%" id="iss">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">申請年度起迄：</span>
        	                        <html:text styleId="adjYearS" property="adjYearS" size="3" maxlength="3" styleClass="textinput" onblur="checkYYY(this)"/>&nbsp;~&nbsp;
        	                        <html:text styleId="adjYearE" property="adjYearE" size="3" maxlength="3" styleClass="textinput" onblur="checkYYY(this)"/>
                                </td>
                            </tr>     
                            <tr>
	                            <td idth="50%" id="iss" colspan="2">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">指數年度/ 物價指數(起)：</span>
        	                        <html:text styleId="cpiYear" property="cpiYear" size="3" maxlength="3" styleClass="textinput" onblur="checkYYY(this)"/>&nbsp;/&nbsp;
        	                        <html:text styleId="cpiB" property="cpiB" size="6" maxlength="6" styleClass="textinput" onblur="setCpIndex()" />

                                </td>
	                            <td idth="50%" id="iss">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">指數年度/ 物價指數(迄)：</span>
        	                        <html:text styleId="cpiYearE" property="cpiYearE" size="3" maxlength="3" styleClass="textinput" onblur="checkYYY(this)"/>&nbsp;/&nbsp;
        	                        <html:text styleId="cpiE" property="cpiE" size="6" maxlength="6" styleClass="textinput" onblur="setCpIndex()"/>
                                </td>
                            </tr>                            
	                        <tr>
                                <td id="iss" idth="100%" id="iss" colspan="4">
                                    &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">累計成長率：</span>
                                    <html:text styleId="cpIndex" property="cpIndex" size="6" maxlength="6" styleClass="textinput" readonly="true" />&nbsp;%&nbsp;
		                        </td>
                            </tr>   
                            	                         	  
	                        <tr>
                                <td id="iss" idth="100%" id="iss" colspan="4">
                                    <span class="needtxt">＊</span><span class="issuetitle_L_down">報請核定文號：</span>
									<html:text styleId="reqRpno" property="reqRpno" size="100" maxlength="100" styleClass="textinput" />
		                        </td>		
	                        </tr>		
	                        <tr>		
		                        <td id="iss" idth="100%" id="iss" colspan="4">
		                            <span class="needtxt">＊</span><span class="issuetitle_L_down">核定文號：</span>
		                            <html:text styleId="issuRpno" property="issuRpno" size="100" maxlength="100" styleClass="textinput" />
		                        </td>
	                        </tr>		                        
	                        <tr>
		                        <td id="iss" idth="100%" id="iss" colspan="4">
		                            <span class="needtxt">＊</span><span class="issuetitle_L_down">核定結果：</span>
		                            <html:text styleId="issuDesc" property="issuDesc" size="100" maxlength="100" styleClass="textinput" />                                
		                        </td>
	                        </tr>	  
	                    </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                        	 <tr>
		                        <td valign="top" width="11%" >
		                            <span class="needtxt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="issuetitle_L_down">申請年度：</span>                            
		                        </td>
		                        <td id="adjYearStrDis" >
                                    <c:forEach var="cpiAdjYearAmountData"  items="${ConstantKey.CPI_DTL_ADJYEAR_AMOUNT_CASE_LIST}">                                                    
                                         <c:out value="${cpiAdjYearAmountData.adjYearStr}"/>
                                         <br>
                                    </c:forEach>
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