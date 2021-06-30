<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X093C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
   	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
   	<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
   	<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
   	<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
	<html:javascript formName="BasicAmtMaintDetailForm" page="1" />	
    <script language="javascript" type="text/javascript">	
	
	<%-- begin 檢查必填欄位 --%>
	function checkRequireFields() {
		var msg = "";        	                                                              
		if(Trim($("cpiYear1").value) == "")
			msg += '「年度(一)」：為必輸欄位。\r\n';        		
        		        	                                                              
		if(Trim($("cpiYear2").value) == "")
          	msg += '「年度(二)」：為必輸欄位。\r\n';
          	
        if(Trim($("cpiNdex1").value) == "")
          	msg += '「物價指數(一)」：為必輸欄位。\r\n';
          	
        if(Trim($("cpiNdex2").value) == "")
          	msg += '「物價指數(二)」：為必輸欄位。\r\n';
        
        if(Trim($("basicAmt").value) == "")
          	msg += '「基本金額」：為必輸欄位。\r\n';  	  	  	
            	            
		if(Trim($("payYmB").value) == "")
			msg += '「給付年月起月」：為必輸欄位。\r\n';
	            		
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
	function checkYYYMM(the){
		var i;
		if(the.value == ""){
			return;
		}
		for(i=the.value.length;i<5;i++){
			the.value = '0' + the.value;
		}
	}		
	
	<%-- 計算成長率 --%>
    function setgrowThrate(){
        var cpiNdex1 = $("cpiNdex1").value*1;
		var cpiNdex2 = $("cpiNdex2").value*1;
		var gt = (cpiNdex2-cpiNdex1)/cpiNdex1;
		var gtMathround = Math.round(gt * 1000000)/1000000*100;
	    //無條件捨去至小數第二位，與計算無關，只放欄位用
	    var growT = Math.round(gtMathround*100)/100;
	    var growTString = growT.toString();
	    $("growThrate").value = growTString;
	<%-- 計算加計金額--%>
	    if(gtMathround > 0){
	    var lastbasicamt = parseInt($("lastbasicamt").value, 10);
	    var basicAmtafter = Math.round(lastbasicamt*(1+(gtMathround/100)));
	    $("basicAmt").value = basicAmtafter.toString();
	    }
	}		
	function initAll(){
        $("basicAmt").focus();
        }
    Event.stopObserving(window, 'load', inputFieldFocus);
	Event.observe(window, 'load', initAll , false);	    
    </script>							
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/disabledBasicAmtMaintDetail" method="post" onsubmit="return validateBasicAmtMaintDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;失能年金基本金額調整作業修改&nbsp;</legend>
          
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="lastBasicAmt" property="lastBasicAmt" />
                        <acl:checkButton buttonName="btnConfirm">
                        
                            <c:choose>
							<c:when test="${BasicAmtMaintDetailForm.payYmB le BasicAmtMaintDetailForm.sysDate}"> 
							<input name="btnConfirm" type="button" class="button" value="確  認 "  disabled="true" />&nbsp;&nbsp;
						    </c:when>
						    <c:otherwise>
						    <input name="btnConfirm" type="button" class="button" value="確  認 " onclick="$('page').value='1'; $('method').value='doConfirm'; if (document.BasicAmtMaintDetailForm.onsubmit() && checkRequireFields()){document.BasicAmtMaintDetailForm.submit();}else{return false;}" />&nbsp;&nbsp;
							</c:otherwise>
							</c:choose>                     
                        			                
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
                                <td width="40%">
                                    <span class="issuetitle_L_down">修改人員：</span>
                                    <c:out value="${BasicAmtMaintDetailForm.user}" />
                                </td>
                                <td width="40%">
                                    <span class="issuetitle_L_down">修改日期：</span>
                                    <c:out value="${BasicAmtMaintDetailForm.date}" />
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
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">年度/ 物價指數(一)：</span>
        	                        <html:text styleId="cpiYear1" property="cpiYear1" size="3" maxlength="3" styleClass="textinput" readonly="true" onblur="checkYYY(this)"/>&nbsp;/&nbsp;
        	                        <c:choose>
	                                <c:when test="${BasicAmtMaintDetailForm.payYmB le BasicAmtMaintDetailForm.sysDate}"> 
        	                        <html:text styleId="cpiNdex1" property="cpiNdex1" size="6" maxlength="6" styleClass="textinput" readonly="true"/>
        	                        </c:when>
						            <c:otherwise>
        	                        <html:text styleId="cpiNdex1" property="cpiNdex1" size="6" maxlength="6" styleClass="textinput" onchange="setgrowThrate()"/>
                                    </c:otherwise>
							        </c:choose>
                                </td>
	                            <td idth="50%" id="iss">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">年度/ 物價指數(二)：</span>
        	                        <html:text styleId="cpiYear2" property="cpiYear2" size="3" maxlength="3" styleClass="textinput" readonly="true" onblur="checkYYY(this)"/>&nbsp;/&nbsp;
        	                        <c:choose>
	                                <c:when test="${BasicAmtMaintDetailForm.payYmB le BasicAmtMaintDetailForm.sysDate}"> 
        	                        <html:text styleId="cpiNdex2" property="cpiNdex2" size="6" maxlength="6" styleClass="textinput" readonly="true"/>
        	                        </c:when>
						            <c:otherwise>
        	                        <html:text styleId="cpiNdex2" property="cpiNdex2" size="6" maxlength="6" styleClass="textinput" onchange="setgrowThrate()"/>
                                    </c:otherwise>
							        </c:choose>
                                </td>
                            </tr>
	                        <tr>
                                <td width="50%" id="iss" colspan="2">
                                    &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">成長率：</span>
                                    <html:text styleId="growThrate" property="growThrate" size="6" maxlength="6" styleClass="textinput" readonly="true" />&nbsp;%&nbsp;
		                        </td>
	                            <td idth="50%" id="iss">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">基本金額：</span>
        	                        <c:choose>
	                                <c:when test="${BasicAmtMaintDetailForm.payYmB le BasicAmtMaintDetailForm.sysDate}"> 
        	                        <html:text styleId="basicAmt" property="basicAmt" size="6" maxlength="6" styleClass="textinput" readonly="true" />
        	                        </c:when>
						            <c:otherwise>
        	                        <html:text styleId="basicAmt" property="basicAmt" size="6" maxlength="6" styleClass="textinput"  />
                                    </c:otherwise>
							        </c:choose>
                                </td>		                        
                            </tr>     
                            <tr>
                                <td width="50%" id="iss" colspan="2">
                                    <span class="needtxt">＊</span><span class="issuetitle_L_down">給付年月起月：</span>
                                    <html:text styleId="payYmB" property="payYmB" size="6" maxlength="6" styleClass="textinput" readonly="true" />
		                        </td>
	                            <td idth="50%" id="iss">
	                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">給付年月迄月：</span>
        	                        <c:choose>
	                                <c:when test="${BasicAmtMaintDetailForm.payYmB le BasicAmtMaintDetailForm.sysDate}"> 
        	                        <html:text styleId="payYmE" property="payYmE" size="6" maxlength="6" styleClass="textinput" readonly="true" />
        	                        </c:when>
						            <c:otherwise>
        	                         <html:text styleId="payYmE" property="payYmE" size="6" maxlength="6" styleClass="textinput"  />
                                    </c:otherwise>
							        </c:choose>
                                </td>		                        
                            </tr>                          	  
	                        <tr>
                                <td idth="100%" id="iss" colspan="4">
                                    <span class="needtxt">＊</span><span class="issuetitle_L_down">報請核定文號：</span>
									
		                            <c:choose>
	                                <c:when test="${BasicAmtMaintDetailForm.payYmB le BasicAmtMaintDetailForm.sysDate}"> 
        	                        <html:text styleId="reqRpno" property="reqRpno" size="100" maxlength="150" styleClass="textinput" readonly="true"/>
        	                        </c:when>
						            <c:otherwise>
        	                        <html:text styleId="reqRpno" property="reqRpno" size="100" maxlength="150" styleClass="textinput" />
                                    </c:otherwise>
							        </c:choose>
		                        </td>		
	                        </tr>		
	                        <tr>		
		                        <td idth="100%" id="iss" colspan="4">
		                            <span class="needtxt">＊</span><span class="issuetitle_L_down">核定文號：</span>
		                            <c:choose>
	                                <c:when test="${BasicAmtMaintDetailForm.payYmB le BasicAmtMaintDetailForm.sysDate}"> 
        	                        <html:text styleId="issuRpno" property="issuRpno" size="100" maxlength="150" styleClass="textinput" readonly="true"/>
        	                        </c:when>
						            <c:otherwise>
        	                        <html:text styleId="issuRpno" property="issuRpno" size="100" maxlength="150" styleClass="textinput" />
                                    </c:otherwise>
							        </c:choose>
		                            
		                        </td>
	                        </tr>		                        
	                        <tr>
		                        <td idth="100%" id="iss" colspan="4">
		                            <span class="needtxt">＊</span><span class="issuetitle_L_down">核定結果：</span>
		                                                            
		                            <c:choose>
	                                <c:when test="${BasicAmtMaintDetailForm.payYmB le BasicAmtMaintDetailForm.sysDate}"> 
        	                        <html:text styleId="issuDesc" property="issuDesc" size="100" maxlength="240" styleClass="textinput" readonly="true"/>
        	                        </c:when>
						            <c:otherwise>
        	                        <html:text styleId="issuDesc" property="issuDesc" size="100" maxlength="240" styleClass="textinput" />
                                    </c:otherwise>
							        </c:choose>
		                        
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