<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D053C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="InheritorRegisterForm" page="1" />           
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){  
           if($("benNationTyp").defaultChecked==true){
            sexContent.style.display="none"; 
            nationalityContent.style.display="none";
            //$("benSex").checked = "1";
            $("benNationCode").value = "";
            $("benNationCodeOption").value = "";
            $("benNationCode").disabled=true;
      }
      if($("commTyp").defaultChecked==true){
            addContent.style.display="none"; 
            $("commZip").value = "";
            $("commAddr").value = "";

      }
      //改成民國日期
      $("benBrDate").defaultValue = changeDateType($("benBrDate").value);
      $("grdBrDate").defaultValue = changeDateType($("grdBrDate").value);
      $("benBrDate").value = changeDateType($("benBrDate").value);
      $("grdBrDate").value = changeDateType($("grdBrDate").value);
    }    
    
    //變更 國籍別 時畫面異動
    function changeBenNationTyp(){
        if($("benNationTyp").value=="1"){
        	document.getElementsByName("benSex")[0].checked=false;
    		document.getElementsByName("benSex")[1].checked=false;
            sexContent.style.display="none"; 
            nationalityContent.style.display="none";
            //$("benSex").checked = "1";
            //$("benNationCode").value = "";
            $("benNationCodeOption").value = "";
        }
        if($("benNationTyp").value=="2"){        	
            sexContent.style.display="inline"; 
            nationalityContent.style.display="inline"; 
            $("benSex").disabled = false;
            $("benNationCode").disabled = false;
            $("benNationCodeOption").disabled = false;
        } 
        autoForeignBenSex();
    }
    //變更 通訊地址別 時畫面異動
    function changeCommTyp(){
        if($("commTyp").value=="1"){
            addContent.style.display="none"; 
           
            $("commZip").value = "";
            $("commAddr").value = "";
        }
        if($("commTyp").value=="2"){
            addContent.style.display="inline";  
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }    
    }
     <%-- 進階欄位驗證 --%>
    <%-- 注意: 此部份之驗證須在 Validation.xml 驗證之後執行 --%>
    <%-- begin ... [ --%>
    function checkFields(){
        var msg = "";
		
		var secondText = $("benIdnNo").value.substring(1,2);
		if($("benIdnNo").value.length==10){
		if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[0].checked){
 			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
 				msg += '個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';	
 				 $("benSex").focus();
    		}
 		}else if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[1].checked){
 			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
 				msg += '個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';	
 				 $("benSex").focus();
    		}
 		}
		}
        if($("benNationTyp").value=="2"){
            if($("benSex").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.oldAgeAnnuityReceipt.evtSex")%>' />\r\n"
            }
            if($("benNationCode").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.oldAgeAnnuityReceipt.evtNationCode")%>' />\r\n"
            }                  
        }
        
        if($("commTyp").value=="2"){
            if($("commZip").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.oldAgeAnnuityReceipt.commZip")%>' />\r\n"
            }
            if($("commAddr").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.oldAgeAnnuityReceipt.commAddr")%>' />\r\n"
            }               
        }
        
        if(msg != ""){
            alert(msg);
            return false;
        }else{
            return true;
        }
    }
// Added by JohnsonHuang 20200115 [Begin]
        //外國人身分證號碼自動帶入		
		function autoForeignBenSex(){
			var secondText = $("benIdnNo").value.substring(1,2);
			if($("benIdnNo").value.length==10){
			if(document.getElementsByName("benNationTyp")[1].checked &&
				document.getElementsByName("benSex")[0].checked==false && document.getElementsByName("benSex")[1].checked==false){
				if(secondText=="A" || secondText=="a" || secondText=="C" || secondText=="c" || secondText=="8"){
					document.getElementsByName("benSex")[0].checked=true;
					document.getElementsByName("benSex")[1].checked=false;   	
				}else if(secondText=="B" || secondText=="b" || secondText=="D" || secondText=="d" || secondText=="9"){
					document.getElementsByName("benSex")[0].checked=false;
					document.getElementsByName("benSex")[1].checked=true;	
				}else{
					document.getElementsByName("benSex")[0].checked=false;
					document.getElementsByName("benSex")[1].checked=false; 
					alert('個人資料「不符合外來人口統一證號編碼原則，請輸入正確身分證」');
				}
			}else{
				if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[0].checked){
					if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
						alert('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
					}
				}else if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[1].checked){
					if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
						alert('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
						}
					}
				}
			}
			}
       // Added by EthanChen 20200115 [End]
     <%-- ] ... end --%>
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/inheritorRegister" method="post" onsubmit="return validateInheritorRegisterForm(this)">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="baappbaseId" property="baappbaseId" />
        
        <fieldset>
            <legend>&nbsp;債務繼承人資料登錄作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnSave">
                            <input type="button" name="btnSave" class="button" value="確　定" onclick="$('page').value='1'; $('method').value='doUpdate'; if (document.InheritorRegisterForm.onsubmit()&&checkFields()){document.InheritorRegisterForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnDelete">
                            <input type="button" name="btnDelete" class="button" value="刪　除" onclick="$('page').value='1'; $('method').value='doDelete'; document.InheritorRegisterForm.submit();" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnReset">
                            <input type="reset" name="btnReset" class="button" value="清　除" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackList'; document.InheritorRegisterForm.submit()"/>
                        </acl:checkButton>                        
                    </td>
                </tr>
                <tr>
                <td>
                   <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                   <tr>
                      <td width="24%"><span class="issuetitle_L_down">受理編號：</span><c:out value="${InheritorRegisterCase.formatApNo}" /></td>
                      <td width="21%"><span class="issuetitle_L_down">給付別：</span><c:out value="${InheritorRegisterCase.kind}" /></td>
                      <td width="21%"><span class="issuetitle_L_down">申請日期：</span><c:out value="${InheritorRegisterCase.chineseAppDate}" /></td>
                   </tr>
                   <tr>
                      <td><span class="issuetitle_L_down">事故者姓名：</span><c:out value="${InheritorRegisterCase.evtName}" /></td>
                      <td><span class="issuetitle_L_down">事故者出生日期：</span><c:out value="${InheritorRegisterCase.chineseEvtBrDate}" /></td>
                      <td><span class="issuetitle_L_down">事故者身分證號：</span><c:out value="${InheritorRegisterCase.evtIdnNo}" /></td>
                   </tr>
                   </table>
               </td>
               </tr>
            <td align="center" class="table1">
              <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                <tr>
                    <td colspan="3" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;債務繼承人資料</td>
                </tr>
                <tr>
                  <td id="iss" width="34%"><span class="needtxt">＊</span><span class="issuetitle_L_down">國籍別：</span>
                  <span class="formtxt"><html:radio styleId="benNationTyp" property="benNationTyp" value="1" onclick="$('benNationTyp').value='1';changeBenNationTyp();"/>本國
                  <html:radio styleId="benNationTyp" property="benNationTyp" value="2" onclick="$('benNationTyp').value='2';changeBenNationTyp();"/>外　籍</span></td>
                  <td width="33%" id="iss"><span class="issuetitle_L_down"><div id="sexContent">性別：
                       <span class="formtxt"><html:radio styleId="benSex" property="benSex" value="1" />男</span>   
                       <span class="formtxt"><html:radio styleId="benSex" property="benSex" value="2"/>女</span></div></span>
                  </td>
                  <td width="33%" id="iss"><span class="issuetitle_L_down"><div id="nationalityContent">國　籍：
                        <html:text property="benNationCode" styleId="benNationCode" styleClass="textinput" size="2" maxlength="3" />
                        <label>
                        <html:select styleId="benNationCodeOption" property="benNationCodeOption" onchange="$('benNationCode').value=$('benNationCodeOption').value">
                        <html:option value="">請選擇...</html:option>
                        <html:options collection="<%=ConstantKey.COUNTRYID_OPTION_LIST%>" property="countryId" labelProperty="cname" />
                        </html:select>
                        </label></div></span>
                  </td>
                </tr>
                <tr>
                  <td id="iss"><span class="needtxt">＊</span><span class="issuetitle_L_down">姓　名：</span>
                  <html:text styleId="benName" property="benName" size="20" maxlength="50" styleClass="textinput" /></td>
                  <td id="iss"><span class="needtxt">＊</span><span class="issuetitle_L_down">出生日期：</span>
                  <html:text styleId="benBrDate" property="benBrDate" size="10" maxlength="8" styleClass="textinput" /></td>
                  <td id="iss" width="33%"><span class="needtxt">＊</span><span class="issuetitle_L_down">身分證號：</span>
                  <html:text styleId="benIdnNo" property="benIdnNo" size="15" maxlength="10" styleClass="textinput" onchange="this.value=this.value.toUpperCase();this.value=asc(this.value);"  onblur="autoForeignBenSex();"/></td>
                </tr>
                <tr>
                  <td id="iss"><span class="needtxt">＊</span><span class="issuetitle_L_down">關　係：</span>
                      <html:select styleId="benEvtRel" property="benEvtRel" >
                          <html:option value="">請選擇...</html:option>
                          <html:options collection="<%=ConstantKey.RELATIION_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                      </html:select>
                  </td>
                  <td id="iss"><span class="issuetitle_L_down">電話1：</span>
                      <html:text styleId="tel1" property="tel1" size="18" maxlength="18" styleClass="textinput" /></td>
                  <td id="iss"><span class="issuetitle_L_down">電話2：</span>
                      <html:text styleId="tel" property="tel2" size="18" maxlength="18" styleClass="textinput" /></td>
                </tr>
                <tr>
                  <td id="iss"><span class="needtxt">＊</span><span class="issuetitle_L_down">地址：</span>
                     <span class="formtxt"><html:radio  styleId="commTyp" property="commTyp" value="1" onclick="$('commTyp').value='1'; changeCommTyp();" />同戶籍地
                     <html:radio styleId="commTyp" property="commTyp" value="2" onclick="$('commTyp').value='2'; changeCommTyp();"/>現住址</span></td>
                  <td id="iss" colspan="2">
                  <span class="formtxt"><div id="addContent">現住址：
                     <html:text styleId="commZip" property="commZip" size="8" maxlength="6" styleClass="textinput" onchange="this.value=Trim($('commZip').value)"/>(郵遞區號) &nbsp;-
                     <html:text styleId="commAddr" property="commAddr" size="72" maxlength="80" styleClass="textinput" onchange="this.value=Trim($('commAddr').value)"/></div></span></td>
                </tr>
                <tr>
                  <td id="iss"><span class="issuetitle_L_down">法定代理人姓名：</span>
                      <html:text styleId="grdName" property="grdName" size="20" maxlength="50" styleClass="textinput" /></td>
                  <td id="iss"><span class="issuetitle_L_down">法定代理人出生日期：</span>
                      <html:text styleId="grdBrDate" property="grdBrDate" size="10" maxlength="8" styleClass="textinput" onkeyup="autotab(this);"/></td>
                  <td id="iss"><span class="issuetitle_L_down">法定代理人身分證號：</span>
                      <html:text styleId="grdIdnNo" property="grdIdnNo" size="15" maxlength="10" styleClass="textinput" onchange="this.value=this.value.toUpperCase();this.value=asc(this.value);" /></td>
                </tr>
                <tr>
                    <td colspan="3"></td>
                </tr>
              </table>
            </td>
          <tr>
            <td><hr size="1" noshade>
            <span class="titleinput">※注意事項：</span><br>
            　                             1.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
            　                             2.&nbsp;<span class="needtxt">＊</span>為必填欄位。</td>
          </tr>                                              
        </table>
        </fieldset>        
        </html:form>
    </div>
</div>
<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
