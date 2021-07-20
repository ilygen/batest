<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAPA0X143C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/maintCommonAjaxDo.js"/>'></script> 
	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>       
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="SurvivorReplacementRatioMaintDetailForm" page="1" />
    <script language="javascript" type="text/javascript">	
	
	<%-- begin 檢查必填欄位 --%>
    function checkRequireFields() {

		var msg = "";
	    
	    //若平均薪資級距2欄位有輸入，則「平均薪資級距1」為必輸欄位。
	    if(!Trim($("insAvg2").value) == ""){
	 
	      if(Trim($("insAvg1").value) == ""){
	        msg += '「平均薪資級距1」為必輸欄位。\r\n';  
	      }else if(!Trim($("insAvg4").value) == ""){
	        if(Trim($("insAvg3").value) == ""){
	        msg += '「平均薪資級距3」為必輸欄位。\r\n';  
	        }
	      }
	    }else     
	    //若平均薪資級距3欄位有輸入，則「平均薪資級距1」「平均薪資級距2」為必輸欄位。
	    if(!Trim($("insAvg3").value) == ""){ 
	     if(Trim($("insAvg1").value) == "" && Trim($("insAvg2").value) == ""){
	        msg += '「平均薪資級距1」「平均薪資級距2」為必輸欄位。\r\n';  
	     }else if(Trim($("insAvg1").value) == ""){
	        msg += '「平均薪資級距1」為必輸欄位。\r\n';  
	     }else if(Trim($("insAvg2").value) == ""){
	        msg += '「平均薪資級距2」為必輸欄位。\r\n'; 
	     }
	    }else
	    //若平均薪資級距4欄位有輸入，則「平均薪資級距1」「平均薪資級距2」「平均薪資級距3」為必輸欄位。
        if(!Trim($("insAvg4").value) == ""){
         if(Trim($("insAvg1").value) == "" && Trim($("insAvg2").value) == "" && Trim($("insAvg3").value) == ""){
	        msg += '「平均薪資級距1」「平均薪資級距2」「平均薪資級距3」為必輸欄位。\r\n';  
	     }else if(Trim($("insAvg2").value) == "" && Trim($("insAvg3").value) == ""){
	        msg += '「平均薪資級距2」「平均薪資級距3」為必輸欄位。\r\n';  
	     }else if(Trim($("insAvg1").value) == "" && Trim($("insAvg3").value) == ""){
	        msg += '「平均薪資級距1」「平均薪資級距3」為必輸欄位。\r\n';  
	     }else if(Trim($("insAvg1").value) == ""){
	        msg += '「平均薪資級距1」為必輸欄位。\r\n';  
	     }else if(Trim($("insAvg2").value) == ""){
	        msg += '「平均薪資級距2」為必輸欄位。\r\n'; 
	     }else if(Trim($("insAvg3").value) == ""){
	        msg += '「平均薪資級距3」為必輸欄位。\r\n'; 
	     }
        }     	        
        
         if(!Trim($("insAvg1").value) == ""){
            if(Trim($("irrTypeA2").value) == ""){
              msg += '「所得替代率A」為必輸欄位。\r\n'; 
            }
         }    	
         if(!Trim($("insAvg2").value) == ""){
            if(Trim($("irrTypeB2").value) == ""){
              msg += '「所得替代率B」為必輸欄位。\r\n'; 
            }
         }    
         if(!Trim($("insAvg3").value) == ""){
            if(Trim($("irrTypeC2").value) == ""){
              msg += '「所得替代率C」為必輸欄位。\r\n'; 
            }
         }    
         if(!Trim($("insAvg4").value) == ""){
            if(Trim($("irrTypeD2").value) == ""){
              msg += '「所得替代率D」為必輸欄位。\r\n'; 
            }
         }    

         if(!Trim($("irrTypeD2").value) == ""){
            if(Trim($("insAvg4").value) == ""){
              msg += '「平均薪資級距4」為必輸欄位。\r\n'; 
            }
         }      	

         //「所得替代率第二式A2」的值大於「所得替代率第二式B2」的值大於「所得替代率第二式C2」的值大於「所得替代率第二式D2」的值。
          if(!Trim($("irrTypeB2").value) == ""){
             if(parseFloat(Trim($("irrTypeB2").value)) > parseFloat(Trim($("irrTypeA2").value))){
               msg += '「所得替代率A」的值須大於「所得替代率B」的值。\r\n'; 
             }
         } 
         if(!Trim($("irrTypeC2").value) == ""){
             if(parseFloat(Trim($("irrTypeC2").value)) > parseFloat(Trim($("irrTypeB2").value))){
               msg += '「所得替代率B」的值須大於「所得替代率C」的值。\r\n'; 
             }
         }
         if(!Trim($("irrTypeD2").value) == ""){
             if(parseFloat(Trim($("irrTypeD2").value)) > parseFloat(Trim($("irrTypeC2").value))){
               msg += '「所得替代率C」的值須大於「所得替代率D」的值。\r\n'; 
             }
         }
            		
		if (msg != "") {
		    alert(msg);
		    return false;
		} else {
            return true;
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
    }
    
    </script>	  				
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/survivorReplacementRatioMaintDetail" method="post" onsubmit="return validateSurvivorReplacementRatioMaintDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;遺屬年金所得替代率參數維護作業修改&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            <bean:define id="replacementRatioData" name="<%=ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_CASE %>" />
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="6" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                         <html:hidden styleId="effYmOrg" property="effYmOrg" />
                        <acl:checkButton buttonName="btnSave">
                        <c:if test='${replacementRatioData.deleteUpdateStr eq "Y"}'>
			                <input name="btnSave" type="button" class="button" value="確  認 " disabled="true" />
                        </c:if>
                        <c:if test='${replacementRatioData.deleteUpdateStr ne "Y"}'>
                            <input name="btnSave" type="button" class="button" value="確  認 " onclick="$('page').value='1'; $('method').value='doConfirm'; if (document.SurvivorReplacementRatioMaintDetailForm.onsubmit() && checkRequireFields()){document.SurvivorReplacementRatioMaintDetailForm.submit();}else{return false;}" />
                        </c:if>
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
                                    <span class="issuetitle_L_down">修改人員：</span>
                                    <c:out value="${SurvivorReplacementRatioMaintDetailForm.user}" />
                                </td>
                                <td width="40%">
                                    <span class="issuetitle_L_down">修改日期：</span>
                                    <c:out value="${SurvivorReplacementRatioMaintDetailForm.date}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
	            <tr>
                    <td colspan="2" align="center" class="table1">
                    <c:if test='${replacementRatioData.deleteUpdateStr eq "Y"}'>
                        <table width="98%" cellpadding="3" cellspacing="5" class="px13">	
                            <tr>
	                            <td id="iss" colspan="2">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">施行年月：</span>
        	                        <html:text styleId="effYm" property="effYm" size="5" maxlength="5" styleClass="textinput" disabled="true" />
                                </td>
                            </tr>   
                            <tr>
	                            <td idth="50%" id="iss">
	                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">平均薪資級距1：</span>
        	                        <html:text styleId="insAvg1" property="insAvg1" size="6" maxlength="6" styleClass="textinput" disabled="true" />
                                </td>
                                <td idth="50%" id="iss">
	                                <span class="issuetitle_L_down">所得替代率A：</span>
        	                        <html:text styleId="irrTypeA2" property="irrTypeA2" size="6" maxlength="6" styleClass="textinput" disabled="true" />&nbsp;%&nbsp;
                                </td>
                            </tr>     
                            <tr>
	                            <td idth="50%" id="iss">
	                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">平均薪資級距2：</span>
        	                        <html:text styleId="insAvg2" property="insAvg2" size="6" maxlength="6" styleClass="textinput" disabled="true" />
                                </td>
                                <td idth="50%" id="iss">
	                                <span class="issuetitle_L_down">所得替代率B：</span>
        	                        <html:text styleId="irrTypeB2" property="irrTypeB2" size="6" maxlength="6" styleClass="textinput" disabled="true" />&nbsp;%&nbsp;
                                </td>
                            </tr>  
                            <tr>
	                            <td idth="50%" id="iss">
	                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">平均薪資級距3：</span>
        	                        <html:text styleId="insAvg3" property="insAvg3" size="6" maxlength="6" styleClass="textinput" disabled="true" />
                                </td>
                                <td idth="50%" id="iss">
	                                <span class="issuetitle_L_down">所得替代率C：</span>
        	                        <html:text styleId="irrTypeC2" property="irrTypeC2" size="6" maxlength="6" styleClass="textinput" disabled="true" />&nbsp;%&nbsp;
                                </td>
                            </tr>  
                            <tr>
	                            <td idth="50%" id="iss">
	                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">平均薪資級距4：</span>
        	                        <html:text styleId="insAvg4" property="insAvg4" size="6" maxlength="6" styleClass="textinput" disabled="true" />
                                </td>
                                <td idth="50%" id="iss">
	                                <span class="issuetitle_L_down">所得替代率D：</span>
        	                        <html:text styleId="irrTypeD2" property="irrTypeD2" size="6" maxlength="6" styleClass="textinput" disabled="true" />&nbsp;%&nbsp;
                                </td>
                            </tr>                         
	                    </table>
	                    </c:if>
	                    <c:if test='${replacementRatioData.deleteUpdateStr ne "Y"}'>
	                    <table width="98%" cellpadding="3" cellspacing="5" class="px13">	
                            <tr>
	                            <td id="iss" colspan="2">
	                                <span class="needtxt">＊</span><span class="issuetitle_L_down">施行年月：</span>
        	                        <html:text styleId="effYm" property="effYm" size="5" maxlength="5" styleClass="disabled" readonly="true" onblur="checkYYYMM(this)"/>
                                </td>
                            </tr>   
                            <tr>
	                            <td idth="50%" id="iss">
	                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">平均薪資級距1：</span>
        	                        <html:text styleId="insAvg1" property="insAvg1" size="6" maxlength="6" styleClass="textinput" />
                                </td>
                                <td idth="50%" id="iss">
	                                <span class="issuetitle_L_down">所得替代率A：</span>
        	                        <html:text styleId="irrTypeA2" property="irrTypeA2" size="6" maxlength="6" styleClass="textinput" />&nbsp;%&nbsp;
                                </td>
                            </tr>     
                            <tr>
	                            <td idth="50%" id="iss">
	                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">平均薪資級距2：</span>
        	                        <html:text styleId="insAvg2" property="insAvg2" size="6" maxlength="6" styleClass="textinput" />
                                </td>
                                <td idth="50%" id="iss">
	                                <span class="issuetitle_L_down">所得替代率B：</span>
        	                        <html:text styleId="irrTypeB2" property="irrTypeB2" size="6" maxlength="6" styleClass="textinput" />&nbsp;%&nbsp;
                                </td>
                            </tr>  
                            <tr>
	                            <td idth="50%" id="iss">
	                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">平均薪資級距3：</span>
        	                        <html:text styleId="insAvg3" property="insAvg3" size="6" maxlength="6" styleClass="textinput" />
                                </td>
                                <td idth="50%" id="iss">
	                                <span class="issuetitle_L_down">所得替代率C：</span>
        	                        <html:text styleId="irrTypeC2" property="irrTypeC2" size="6" maxlength="6" styleClass="textinput" />&nbsp;%&nbsp;
                                </td>
                            </tr>  
                            <tr>
	                            <td idth="50%" id="iss">
	                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">平均薪資級距4：</span>
        	                        <html:text styleId="insAvg4" property="insAvg4" size="6" maxlength="6" styleClass="textinput" />
                                </td>
                                <td idth="50%" id="iss">
	                                <span class="issuetitle_L_down">所得替代率D：</span>
        	                        <html:text styleId="irrTypeD2" property="irrTypeD2" size="6" maxlength="6" styleClass="textinput" />&nbsp;%&nbsp;
                                </td>
                            </tr>                         
	                    </table>
	                    </c:if>
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