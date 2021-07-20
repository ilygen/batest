<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_CASE%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D137C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />   
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>  
    <html:javascript formName="DisabledApplicationDataUpdateForm" page="5" />	
    <script language="javascript" type="text/javascript">
      function changeReChkStatus() {
          if($("reChkStatus").value == "1" ){
   
           $("reChkResult1Div").style.display = "inline";
           $("reChkResult2Div").style.display = "none";
           //$("reChkResult1").value = "";
           //$("reChkResult2").value = "";
           
        }else if($("reChkStatus").value == "2" ){

           $("reChkResult1Div").style.display = "none";
           $("reChkResult2Div").style.display = "inline";
           //$("reChkResult1").value = "";
           //$("reChkResult2").value = "";
           
        }else{
        
           $("reChkResult1Div").style.display = "inline";
           $("reChkResult2Div").style.display = "none";
           $("reChkResult1").value = "";
           $("reChkResult2").value = "";
           
        }
      }
      
      function chkComReChkYm(){
        var msg = ""; 
        var reChkYm = $("reChkYm").value;
        var comReChkYm = $("comReChkYm").value;
        var nowDate = "<%=DateUtility.getNowChineseDate()%>";
        var nowYearMonth = nowDate.substring(0,5);

        if(comReChkYm < reChkYm){
              msg += '「完成重新查核年月」必填大於等於「重新查核失能程度年月」。\r\n';
              $("comReChkYm").focus();
        }
        
        if(comReChkYm > nowYearMonth){
              msg += '「完成重新查核年月」必填小於等於「系統年月」。\r\n';
              $("comReChkYm").focus();
        }
        
        if(!comReChkYm == ""){
              
           if($("reChkStatus").value == ""){
              msg += '重新查核狀態或結果未選擇，請確認。\r\n';
           }
           
           if($("reChkStatus").value == "1" && $("reChkResult1").value == ""){
              msg += '重新查核狀態或結果未選擇，請確認。\r\n';
           }
           
            if($("reChkStatus").value == "2" && $("reChkResult2").value == ""){
              msg += '重新查核狀態或結果未選擇，請確認。\r\n';
           }
              
        }
        
        if(msg != ""){
            alert(msg);
            return false;
        }else{
            return true;
        }
    }
      
      function initAll() {
        
        changeReChkStatus();
        
    }
    
    Event.observe(window, 'load', initAll , false);
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    </script>          
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/disabledApplicationDataUpdate" method="post" onsubmit="return validateDisabledApplicationDataUpdateForm(this);">
        
        <fieldset>
            <legend>&nbsp;重新查核失能程度維護作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="100%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="8" align="right">
                        <html:hidden styleId="page" property="page" value="5" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnUpdate">
                            <input type="button" class="button" name="btnUpdate" value="確　認" onclick="$('page').value='5'; $('method').value='doRehcUpdate';if (document.DisabledApplicationDataUpdateForm.onsubmit() && chkComReChkYm()){document.DisabledApplicationDataUpdateForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='5'; $('method').value='doBackToRechUpdate'; document.DisabledApplicationDataUpdateForm.submit();" />&nbsp;
                        </acl:checkButton> 
                        &nbsp;                                            
                    </td>
                </tr> 
                <tr>
                    <td colspan="7" class="issuetitle_L">                          
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                             <tr>
                                <td width="33%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <bean:write name="DisabledApplicationDataUpdateForm" property="apNoString" />
                                </td>
                                <td width="33%">
                                    <c:if test="${caseData.payKind eq '36'}">
                                        <span class="issuetitle_L_down">給付種類：</span>國併勞
                                    </c:if>
                                    <c:if test="${caseData.payKind eq '38'}">
                                        <span class="issuetitle_L_down">給付種類：</span>勞併國
                                    </c:if>
                                    <c:if test="${(caseData.payKind ne '36') and (caseData.payKind ne '38')}">
                                    <span class="issuetitle_L_down">給付別：</span>
                                                                                                             失能年金
                                    </c:if>
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">處理狀態：</span>
                                    <bean:write name="DisabledApplicationDataUpdateForm" property="procStatString" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">
                                    <span class="issuetitle_L_down">失能項目：</span>
                                    <c:out value="${caseData.criInJdpStr}" />
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
	                                <span class="issuetitle_L_down">重新查核失能程度年月：</span>
        	                        <html:text styleId="reChkYm" property="reChkYm" size="5" maxlength="5" styleClass="disabled" readonly="true"/>
                                </td>
                            </tr>                        
	                        <tr>
                                <td id="iss" idth="100%" id="iss" colspan="4">
                                    <span class="issuetitle_L_down">是否複檢：</span>
                                    <html:select property="isreChk">
                                         <html:option value="">請選擇...</html:option>
                                         <html:option styleId="isreChk1" value="1">是</html:option>
                                         <html:option styleId="isreChk2" value="2">否</html:option>
                                         <html:hidden styleId="oldIsreChk" property="oldIsreChk" />
                                    </html:select>
		                        </td>
                            </tr>   
                            	                         	  
	                        <tr>
                                <td id="iss" idth="100%" id="iss" colspan="4">
                                    <span class="issuetitle_L_down">重新查核狀態：</span>
                                    <html:select styleId="reChkStatus" property="reChkStatus" onblur="changeReChkStatus();">
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="reChkStatusOptionList" />
                                    </html:select>
                                    <html:hidden styleId="oldReChkStatus" property="oldReChkStatus" />
		                        </td>		
	                        </tr>		
	                        <tr>		
		                        <td id="iss" idth="100%" id="iss" colspan="4">
		                        <div id="reChkResult1Div">
		                            <span class="issuetitle_L_down">重新查核結果：</span>
		                            <html:select styleId="reChkResult1" property="reChkResult1" >
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="reChkResult1OptionList" />
                                    </html:select>
                                </div>
                                    <html:hidden styleId="oldReChkResult1" property="oldReChkResult1" />
                                <div id="reChkResult2Div">  
                                    <span class="issuetitle_L_down">重新查核結果：</span>
                                    <html:select styleId="reChkResult2" property="reChkResult2">
                                        <html:option value="">請選擇...</html:option>
                                        <html:optionsCollection label="paramName" value="paramCode" property="reChkResult2OptionList" />
                                    </html:select>
                                </div>
                                    <html:hidden styleId="oldReChkResult2" property="oldReChkResult2" />
		                        </td>
	                        </tr>		                        
	                        <tr>
		                        <td id="iss" idth="100%" id="iss" colspan="4">
		                            <span class="issuetitle_L_down">完成重新查核年月：</span>
		                            <html:text styleId="comReChkYm" property="comReChkYm" size="5" maxlength="5" styleClass="textinput" />    
		                            <html:hidden styleId="oldComReChkYm" property="oldComReChkYm" />                            
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
