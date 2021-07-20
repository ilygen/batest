<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X072A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/maintCommonAjaxDo.js"/>'></script> 
	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>           
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="CpiRecMaintDetailForm" page="1" />
    <script language="javascript" type="text/javascript">	

	<%-- begin 檢查必填欄位 --%>
    function checkRequireFields() {
		var msg = "";
        		
		if(Trim($("issuYear").value) == "")
			msg += '「核定年度」：為必輸欄位。\r\n';        		
        		        	                                                              
		if(Trim($("adjYmB").value) == "")
          	msg += '「調整起月」：為必輸欄位。\r\n';
            	            
		if(Trim($("reqRpno").value) == "")
            msg += '「報請核定文號」：為必輸欄位。\r\n';
	            
		if(Trim($("issuRpno").value) == "")
          	msg += '「核定文號」：為必輸欄位。\r\n';
            		
		if(Trim($("issuDesc").value) == "")
            msg += '「核定結果」：為必輸欄位。\r\n';            	            	
        
        <%-- begin 檢查 調整起月 起月年度應等於核定年度當年。 --%>
        var adjYmBYear = Trim($("adjYmB").value).substring(0,3);
	    if(adjYmBYear != Trim($("issuYear").value))
	        msg += '「調整起月」：起月年度應等於核定年度當年。\r\n';
        <%-- begin 檢查  調整起月 不能小於等於其它調整起月。--%>
        var lastAdjYmB = parseInt(Trim($("lastAdjYmB").value));
        var nowAdjYmB  = parseInt(Trim($("adjYmB").value));
        if(nowAdjYmB <= lastAdjYmB)
            msg += '「調整起月」：不能小於等於其它調整起月。\r\n'; 
         
            		
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

    <%-- 畫面重設 --%>
    function resetForm(){
        cleanForm();
        initAllForClean();
    }
    
    function initAllForClean(){
        $("adjYmB").value="";
    }
    Event.observe(window, 'load', initAllForClean, false);    		    
    </script>  				
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/cpiRecMaintDetail" method="post" onsubmit="return validateCpiRecMaintDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;物價指數調整紀錄資料新增&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="6" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="lastAdjYmB" property="lastAdjYmB" />
                        <acl:checkButton buttonName="btnSave">
			                <input name="btnSave" type="button" class="button" value="確  認 " onclick="$('page').value='1'; $('method').value='doSave'; if (document.CpiRecMaintDetailForm.onsubmit() && checkRequireFields()){document.CpiRecMaintDetailForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">                            
                            <input tabindex="65" type="button" name="btnReset" class="button" value="清　除" onclick="resetForm()"/>&nbsp;
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
        	                        <html:text styleId="issuYear" property="issuYear" size="3" maxlength="3" styleClass="textinput" readonly="true" onblur="checkYYY(this)"/>

                                </td>
	                            <td idth="50%" id="iss">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">調整起月：</span>
        	                        <html:text styleId="adjYmB" property="adjYmB" size="8" maxlength="8" styleClass="textinput" onblur="checkYYYMM(this)"/>
                                </td>
                            </tr>               	  
	                        <tr>
                                <td idth="100%" id="iss" colspan="4">
                                    <span class="needtxt">＊</span><span class="issuetitle_L_down">報請核定文號：</span>
									<html:text styleId="reqRpno" property="reqRpno" size="100" maxlength="100" styleClass="textinput" readonly="true" />
		                        </td>		
	                        </tr>		
	                        <tr>		
		                        <td idth="100%" id="iss" colspan="4">
		                            <span class="needtxt">＊</span><span class="issuetitle_L_down">核定文號：</span>
		                            <html:text styleId="issuRpno" property="issuRpno" size="100" maxlength="100" styleClass="textinput" readonly="true" />
		                        </td>
	                        </tr>		                        
	                        <tr>
		                        <td idth="100%" id="iss" colspan="4">
		                            <span class="needtxt">＊</span><span class="issuetitle_L_down">核定結果：</span>
		                            <html:text styleId="issuDesc" property="issuDesc" size="100" maxlength="100" styleClass="textinput" readonly="true" />                                
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
                        <bean:define id="list" name="<%=ConstantKey.CPI_REC_MAINT_DETAIL_CASE_LIST%>" />
                           <display:table name="pageScope.list" id="listItem" class="px13">                            
                               <display:column title="序&nbsp;號" style="width:8%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                   <c:out value="${listItem_rowNum}" />&nbsp;
                               </display:column>
                               <display:column title="申請年度" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                   <c:out value="${listItem.adjYearStr}" />&nbsp;
                               </display:column>
                               <display:column title="調整指數" style="width:10%; text-align:right;" class="issueinfo_C" headerClass="issuetitle_L">
                                   <c:out value="${listItem.cpIndex}" />%&nbsp;
                               </display:column>
                               <display:column title="指數年度(起)" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                   <c:out value="${listItem.cpiYearB}" />&nbsp;
                               </display:column>
                               <display:column title="指數年度(迄)" style="width:10%; text-align:center;" class="issueinfo_C" headerClass="issuetitle_L">
                                   <c:out value="${listItem.cpiYearE}" />&nbsp;
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