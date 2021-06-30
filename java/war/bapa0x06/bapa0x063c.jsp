<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X063C" />
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
     
    </script>	
									
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/cpiDtlMaintDetail" method="post" onsubmit="return validateCpiDtlMaintDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;物價指數調整明細資料修改&nbsp;</legend>
          
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="adjMk" property="adjMk"  />                        
                        <acl:checkButton buttonName="btnConfirm">
                                                                        
							<c:if test='${CpiDtlMaintDetailForm.adjMk eq "Y"}'>
								<input name="btnConfirm" type="button" class="button" value="確  認 " disabled="true" />&nbsp;&nbsp;
							</c:if>
							<c:if test='${CpiDtlMaintDetailForm.adjMk ne "Y"}'>
								<input name="btnConfirm" type="button" class="button" value="確  認 " onclick="$('page').value='1'; $('method').value='doConfirm'; if (document.CpiDtlMaintDetailForm.onsubmit() && checkRequireFields()){document.CpiDtlMaintDetailForm.submit();}else{return false;}" />&nbsp;&nbsp;
							</c:if>                         
                        			                
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
                                    <span class="issuetitle_L_down">修改人員：</span>
                                    <c:out value="${userData.empNo}" />
                                </td>
                                <td width="40%">
                                    <span class="issuetitle_L_down">修改日期：</span>
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
	                            <td id="iss" idth="100%" id="iss" colspan="4">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">核定年度：</span>  
        	                        <html:text styleId="issuYear" property="issuYear" size="3" maxlength="3" styleClass="textinput" readonly="true" onblur="checkYYY(this)"/>
                                </td>                      
                            </tr>
                            <tr>
	                            <td idth="50%" id="iss" colspan="2">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">指數年度/ 物價指數(起)：</span>
	                                <c:if test='${CpiDtlMaintDetailForm.adjMk eq "Y"}'>
        	                        <html:text styleId="cpiYear" property="cpiYear" size="3" maxlength="3" styleClass="textinput" readonly="true" onblur="checkYYY(this)"/>&nbsp;/&nbsp;
        	                        <html:text styleId="cpiB" property="cpiB" size="6" maxlength="6" styleClass="textinput" readonly="true" />&nbsp;
                                    </c:if>
                                    <c:if test='${CpiDtlMaintDetailForm.adjMk ne "Y"}'>
        	                        <html:text styleId="cpiYear" property="cpiYear" size="3" maxlength="3" styleClass="textinput" onblur="checkYYY(this)"/>&nbsp;/&nbsp;
        	                        <html:text styleId="cpiB" property="cpiB" size="6" maxlength="6" styleClass="textinput" onblur="setCpIndex()" />&nbsp;
                                    </c:if>
                                </td>
	                            <td idth="50%" id="iss">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">指數年度/ 物價指數(迄)：</span>
	                                <c:if test='${CpiDtlMaintDetailForm.adjMk eq "Y"}'>
        	                        <html:text styleId="cpiYearE" property="cpiYearE" size="3" maxlength="3" styleClass="textinput" readonly="true" onblur="checkYYY(this)"/>&nbsp;/&nbsp;
        	                        <html:text styleId="cpiE" property="cpiE" size="6" maxlength="6" styleClass="textinput" readonly="true" />&nbsp;
        	                        </c:if>
        	                        <c:if test='${CpiDtlMaintDetailForm.adjMk ne "Y"}'>
        	                        <html:text styleId="cpiYearE" property="cpiYearE" size="3" maxlength="3" styleClass="textinput" onblur="checkYYY(this)"/>&nbsp;/&nbsp;
        	                        <html:text styleId="cpiE" property="cpiE" size="6" maxlength="6" styleClass="textinput" onblur="setCpIndex()" />&nbsp;
        	                        </c:if>
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
                                    <c:if test='${CpiDtlMaintDetailForm.adjMk eq "Y"}'>
                                    	<html:text styleId="reqRpno" property="reqRpno" size="100" maxlength="100" styleClass="textinput" readonly="true" />
                                    </c:if>
									<c:if test='${CpiDtlMaintDetailForm.adjMk ne "Y"}'>
										<html:text styleId="reqRpno" property="reqRpno" size="100" maxlength="100" styleClass="textinput" />
									</c:if> 
		                        </td>		
	                        </tr>		
	                        <tr>		
		                        <td id="iss" idth="100%" id="iss" colspan="4">
		                            <span class="needtxt">＊</span><span class="issuetitle_L_down">核定文號：</span>
                                    <c:if test='${CpiDtlMaintDetailForm.adjMk eq "Y"}'>
                                    	<html:text styleId="issuRpno" property="issuRpno" size="100" maxlength="100" styleClass="textinput" readonly="true" />
                                    </c:if>
									<c:if test='${CpiDtlMaintDetailForm.adjMk ne "Y"}'>		                            
		                            	<html:text styleId="issuRpno" property="issuRpno" size="100" maxlength="100" styleClass="textinput" />
		                            </c:if> 
		                        </td>
	                        </tr>		                        
	                        <tr>
		                        <td id="iss" idth="100%" colspan="4">
		                            <span class="needtxt">＊</span><span class="issuetitle_L_down">核定結果：</span>
                                    <c:if test='${CpiDtlMaintDetailForm.adjMk eq "Y"}'>
                                    	<html:text styleId="issuDesc" property="issuDesc" size="100" maxlength="100" styleClass="textinput" readonly="true" />
                                    </c:if>
									<c:if test='${CpiDtlMaintDetailForm.adjMk ne "Y"}'>		                            
		                            	<html:text styleId="issuDesc" property="issuDesc" size="100" maxlength="100" styleClass="textinput" /> 
		                            </c:if>                                   
		                        </td>
	                        </tr>
	                        <tr>
		                        <td valign="top" width="10%" align="left">
		                            <span class="needtxt">&nbsp;&nbsp;&nbsp;</span><span class="issuetitle_L_down">申請年度：</span>   
		                        </td>     
		                        <td >                   
                                    <c:forEach var="cpiAdjYearCase"  items="${ConstantKey.CPI_DTL_ADJYEAR_CASE_LIST}">                                                    
                                         <c:out value="${cpiAdjYearCase.adjYear}"/><br>
                                    </c:forEach>
                                </td>
	                        </tr>	
	                    </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center" class="table1">
                      <table width="98%" cellpadding="3" cellspacing="5" class="px13">	  
	                  <tr align="center">
                       <td>
                        <bean:define id="list" name="<%=ConstantKey.CPI_DTL_USER_DETAIL_CASE_LIST%>" />
                           <display:table name="pageScope.list" id="listItem" class="px13">                            
                               <display:column title="序&nbsp;號" style="width:8%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                   <c:out value="${listItem_rowNum}" />&nbsp;
                               </display:column>
                               <display:column title="申請年度" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                   <c:out value="${listItem.adjYearStr}" />&nbsp;
                               </display:column>
                               <display:column title="異動人員" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                   <c:out value="${listItem.user}" />&nbsp;
                               </display:column>
                               <display:column title="異動日期" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                   <c:out value="${listItem.date}" />&nbsp;
                               </display:column>
                           </display:table>
                       </td>
                    </tr>
	                </table>
                    </td>
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