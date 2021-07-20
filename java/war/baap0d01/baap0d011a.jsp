<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.util.DateUtility" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAAP0D011A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/receiptCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <html:javascript formName="OldAgeAnnuityReceiptForm" page="1" />
    <script language="javascript" type="text/javascript">
    function initAll(){
        if('<c:out value="${OldAgeAnnuityReceiptForm.evtNationTpe}" />'!=""){
            if('<c:out value="${OldAgeAnnuityReceiptForm.evtNationTpe}" />'=="1"){
                $("evtSex").disabled = true;
                $("evtNationCode").disabled = true;
                $("evtNationCodeOption").disabled = true;
                $("evtSex").value = "";
                $("evtNationCode").value = "";
                $("evtNationCodeOption").value = "";
                $("commTyp").readOnly = false;
                if('<c:out value="${OldAgeAnnuityReceiptForm.commTyp}" />'=="1"){
                    $("commZip").disabled = true;
                    $("commAddr").disabled = true;
                    $("commZip").value = "";
                    $("commAddr").value = "";
                }else{
                    $("commZip").disabled = false;
                    $("commAddr").disabled = false;
                }
            }else{
                $("evtSex").disabled = false;
                $("evtNationCode").disabled = false;
                $("evtNationCodeOption").disabled = false;
                $("commTyp").value="2";
                $("commTyp").readOnly = true;
                $("commZip").disabled = false;
                $("commAddr").disabled = false;
            }            
        }else{
            $("evtNationTpe").value="1";
            $("commTyp").value="2";
            $("apItem").value="1";            
            $("evtSex").disabled = true;
            $("evtNationCode").disabled = true;
            $("evtNationCodeOption").disabled = true;
            $("commTyp").value="2";
            $("commTyp").readOnly = false;
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }
        //手機複驗
        enableMobilePhone();
        if($("mobilePhone").disabled == false){
            $("mobilePhone").value='<c:out value="${OldAgeAnnuityReceiptForm.mobilePhone}" />';
        }else{
            $("mobilePhone").value="";
        }
        changePayTyp();
        initChkbox();
    }
    
    //變更 國籍別 時畫面異動    
    function changeEvtNationTpe(){
        if($("evtNationTpe").value=="1"){
            $("evtSex").disabled = true;
            $("evtSex").value = "";
            $("evtNationCode").disabled = true;
            $("evtNationCode").value = "";
            $("evtNationCodeOption").disabled = true;
            $("evtNationCodeOption").value = "";
            $("commTyp").value="2";
            $("commTyp").readOnly = false;
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }
        if($("evtNationTpe").value=="2"){
            $("evtSex").disabled = false;
            $("evtNationCode").disabled = false;
            $("evtNationCodeOption").disabled = false;
            $("commTyp").value="2";
            $("commTyp").readOnly = true;
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }
        autoForeignEvtSex();
    }
    
    function chgEvtNationTpeFocus(){
        if(($("evtNationTpe").value=="1") && event.keyCode==9){
            $("evtName").focus();
        }else if(($("evtNationTpe").value=="2") && event.keyCode==9){
            $("evtSex").focus();
        }
    }
    //變更 通訊地址別 時畫面異動
    function changeCommTyp(){
        if($("commTyp").value=="1"){
            $("commZip").value = "";
            $("commAddr").value = ""; 
            $("commZip").disabled = true;
            $("commAddr").disabled = true;
        }
        if($("commTyp").value=="2"){
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }
    }
        
    function chgCommTypFocus(){
        if($("commTyp").value == 1 && event.keyCode==9){
            $("apItem").focus();
        }else if($("commTyp").value == 2 && event.keyCode==9){
            $("commZip").focus();
        }
    }
    
    //變更 給付方式 時畫面異動
    function changePayTyp(){
        if($("payTyp").value=="1"){
            $("payBankId").disabled = false;
            $("branchId").value = '0000';
            $("branchId").readOnly = true;
            $("branchId").addClassName('disabledN');
            $("branchId").disabled = false;
            $("payEeacc").disabled = false;
            //帳號複驗
            $("chkPayBankId").disabled = false;
            $("chkBranchId").value = '0000';
            $("chkBranchId").readOnly = true;
            $("chkBranchId").addClassName('disabledN');
            $("chkBranchId").disabled = false;
            $("chkPayEeacc").disabled = false;
        }
        if($("payTyp").value=="2"){
            $("payBankId").disabled = false;
            $("branchId").disabled = false;
            $("payEeacc").disabled = false;
            $("branchId").readOnly = false;
            $("branchId").removeClassName('disabledN');
            //帳號複驗
            $("chkPayBankId").disabled = false;
            $("chkBranchId").disabled = false;
            $("chkPayEeacc").disabled = false;
            $("chkBranchId").readOnly = false;
            $("chkBranchId").removeClassName('disabledN');
            
             if($("branchId").value == "0000"){
              $("branchId").value = "";
            }
            if($("chkBranchId").value == "0000"){
              $("chkBranchId").value = "";
            }
        }
        if($("payTyp").value=="A" || $("payTyp").value=="a"){
            $("payBankId").disabled = true;
            $("branchId").disabled = true;
            $("payEeacc").disabled = true;
            $("payBankId").value = "";
            $("branchId").value = "";
            $("payEeacc").value = "";
            $("branchId").removeClassName('disabledN');
            
            //帳號複驗
            $("chkPayBankId").disabled = true;
            $("chkBranchId").disabled = true;
            $("chkPayEeacc").disabled = true;
            $("chkPayBankId").value = "";
            $("chkBranchId").value = "";
            $("chkPayEeacc").value = "";
            $("chkBranchId").removeClassName('disabledN');
        }    
    }
    
    function chgPayTypFocus(){
        if(($("payTyp").value=="1"||$("payTyp").value=="2") && event.keyCode==9){
            $("payBankId").focus;
        }else if(($("payTyp").value=="A") && event.keyCode==9){
            $("btnConfirm").focus;
        }
    }
    
    // Ajax for 取得 戶籍檔姓名
    function initCvldtlName() {   
        if(($("evtIdnNo").value != "") && !isNaN($("evtBrDate").value)){
            receiptCommonAjaxDo.getCvldtlName($("evtIdnNo").value, $("evtBrDate").value, fillCvldtlName);       
        }else{
            $("cvldtlName").value = ""; 
        }
    }
    
    //檢核事故者出生日期 20121220 邏輯修改
    function isValidEvtDateTrue() {   
        var evtBrDate = $("evtBrDate").value;
        
        if(isValidDate($("evtBrDate").value) == false){
          if(confirm("輸入之「事故者出生日期」錯誤，是否繼續進行存檔?") == true){
          return true;
          }else{
          $("evtBrDate").focus();
          return false;
          }
        }else{
          return true;
        }

    }

    function fillCvldtlName(name) {
        $("cvldtlName").value = name;        
    }
    
    <%-- 進階欄位驗證 --%>
    <%-- 注意: 此部份之驗證須在 Validation.xml 驗證之後執行 --%>
    <%-- begin ... [ --%>
    function checkFields(){
        var msg = "";
		
		var secondText = $("evtIdnNo").value.substring(1,2);
		if($("evtIdnNo").value.length==10){
		if($("evtNationTpe").value=="2" && $("evtSex").value == "1"){
 			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
 				msg += '身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';	
 				 $("evtSex").focus();
    		}
 		}else if($("evtNationTpe").value=="2" && $("evtSex").value == "2"){
 			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
 				msg += '身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';	
 				 $("evtSex").focus();
    		}
 		}
		}
        
        if(Trim($("apNo1").value) !="" || Trim($("apNo2").value) !="" || Trim($("apNo3").value) !="" || Trim($("apNo4").value) !=""){
            var apNo = Trim($("apNo1").value) + Trim($("apNo2").value) + Trim($("apNo3").value) + Trim($("apNo4").value);
            if(apNo.length != <%=ConstantKey.APNO_LENGTH%>){
                msg += "請輸入完整受理編號。\r\n";
                $("apNo1").focus();
            }
        }
        if(parseNumber($("appDate").value)<parseNumber("0980101")){
            msg += "「申請日期」不可小於 0980101。\r\n"
            $("appDate").focus();
        }
        if($("evtNationTpe").value=="2"){	
            if($("evtSex").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.oldAgeAnnuityReceipt.evtSex")%>' />\r\n"
                $("evtSex").focus();
            }
            if($("evtNationCode").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.oldAgeAnnuityReceipt.evtNationCode")%>' />\r\n"
                $("evtNationCode").focus();
            }                  
        }
        if($("commTyp").value=="2"){
            if($("commAddr").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.oldAgeAnnuityReceipt.commAddr")%>' />\r\n"
                $("commAddr").focus();
            }               
        }
        // 檢查手機複驗
        if($("mobilePhone").disabled==false){
            var tel1 = $("tel1").value;
            var tel2 = $("tel2").value;
            var shortTel1 = "";
            var shortTel2 = "";
            if(tel1.length >=2){
                shortTel1 = tel1.substring(0,2); 
            }
            if(tel2.length >=2){
                shortTel2 = tel2.substring(0,2); 
            }
            
            if(Trim($("mobilePhone").value)==""){
                msg += "「手機複驗」為必填欄位。\r\n"
                $("mobilePhone").focus();
            }else if(($("mobilePhone").value).length != 10 || !((shortTel1=="09" && $("mobilePhone").value==$("tel1").value)||(shortTel2=="09" && $("mobilePhone").value==$("tel2").value))){
                msg += "「手機複驗」輸入有誤，請檢查。\r\n"
                $("mobilePhone").focus();
            }
        } 
        if (Trim($("payTyp").value) == "1" || Trim($("payTyp").value) == "2"){
            var payBankIdBranchId = Trim($("payBankId").value) + Trim($("branchId").value);
            var payEeacc = Trim($("payEeacc").value);
            
            if(Trim($("payBankId").value) == "" || Trim($("branchId").value) == "" || Trim($("payEeacc").value) == ""){
                msg += '「帳號」：為必填欄位。\r\n';
                $("payBankId").focus();            
            }
            else if(isNaN($("payBankId").value) || isNaN($("branchId").value) || isNaN($("payEeacc").value)){
                if(isNaN($("payBankId").value) || isNaN($("branchId").value)){
                    msg += '「帳號(前)」：必須為數字。\r\n';
                    $("payBankId").focus();  
                }
                if(isNaN($("payEeacc").value)){
                    msg += '「帳號(後)」：必須為數字。\r\n';
                    $("payEeacc").focus();  
                }
            }
            else{
                if (Trim($("payTyp").value) == "1" && payBankIdBranchId.substr(0,3) == "700"){
                    msg += '「帳號前三碼」：不可為700。\r\n';
                    $("payBankId").focus();
                }
                if (Trim($("payTyp").value) == "2" && payBankIdBranchId != "7000010" && payBankIdBranchId != "7000021"){
                    msg += '「帳號(前)」：限定只能輸入700-0010或700-0021。\r\n';
                    $("payBankId").focus();
                } 
                // else {
                //    if(isValidPayEeacc(payBankIdBranchId,Trim($("payEeacc").value)))
                //        msg += '「帳號」格式錯誤，請重新確認。\r\n';
                //}
            }
            
            //if (Trim($("payTyp").value) == "2" && payBankIdBranchId == "7000010" && $F("payEeacc").length > 8)
            //    msg += '「帳號(後)」：長度為8碼。\r\n';
            //if (Trim($("payTyp").value) == "2" && payBankIdBranchId == "7000021" && $F("payEeacc").length != 14)
            //    msg += '「帳號(後)」：長度為14碼。\r\n';                        
        }
        
        //帳號複驗檢核
        if (Trim($("payTyp").value) == "1" || Trim($("payTyp").value) == "2"){
            var chkPayBankIdChkBranchId = Trim($("chkPayBankId").value) + Trim($("chkBranchId").value);
            var chkPayEeacc = Trim($("chkPayEeacc").value);
            
            if(Trim($("chkPayBankId").value) == "" || Trim($("chkBranchId").value) == "" || Trim($("chkPayEeacc").value) == ""){
                msg += '「帳號複驗」：為必填欄位。\r\n';
                $("chkPayBankId").focus();            
            }
            else if(isNaN($("chkPayBankId").value) || isNaN($("chkBranchId").value) || isNaN($("chkPayEeacc").value)){
                if(isNaN($("chkPayBankId").value) || isNaN($("chkPayBankId").value)){
                    msg += '「帳號複驗(前)」：必須為數字。\r\n';
                    $("chkPayBankId").focus();  
                }
                if(isNaN($("chkPayEeacc").value)){
                    msg += '「帳號複驗(後)」：必須為數字。\r\n';
                    $("chkPayEeacc").focus();  
                }
            }
            else{
                if (Trim($("payTyp").value) == "1" && chkPayBankIdChkBranchId.substr(0,3) == "700"){
                    msg += '「帳號複驗前三碼」：不可為700。\r\n';
                    $("chkPayBankId").focus();
                }
                if (Trim($("payTyp").value) == "2" && chkPayBankIdChkBranchId != "7000010" && chkPayBankIdChkBranchId != "7000021"){
                    msg += '「帳號複驗(前)」：限定只能輸入700-0010或700-0021。\r\n';
                    $("chkPayBankId").focus();
                }
              
            }
        }
        
        //帳號複驗
        if(payBankIdBranchId != chkPayBankIdChkBranchId){
                    msg += '「帳號(前)及帳號複驗(前)不一致，請重新輸入」\r\n';
                    $("payBankId").focus();
        }
        if(payEeacc != chkPayEeacc){
                    msg += '「帳號(後)及帳號複驗(後)不一致，請重新輸入」\r\n';
                    $("payBankId").focus();
        }
        
        //檢核事故者出生日期  是否為數字 及 年月格式
        if($("evtBrDate").value.length < 7){
                    msg += '輸入之「事故者出生日期」格式錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
        } 
        if(isNaN($("evtBrDate").value)){
                    msg += '輸入之「事故者出生日期」格式錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
        }
        if($("evtBrDate").value.length == 7){
           var chkMonth = $("evtBrDate").value.substring(3,5);
           var chkDay   = $("evtBrDate").value.substring(5,7);
           if(chkMonth > 12 || chkDay > 32){
                    msg += '輸入之「事故者出生日期」格式錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
           }
        }else if($("evtBrDate").value.length == 8){
           var chkfrist = $("evtBrDate").value.substring(0,1);
           var chkMonth = $("evtBrDate").value.substring(4,6);
           var chkDay   = $("evtBrDate").value.substring(6,8);
           if(chkfrist != "-"){
                    msg += '輸入之「事故者出生日期」格式錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
           }
           if(chkMonth > 12 || chkDay > 32){
                    msg += '輸入之「事故者出生日期」格式錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
           }
        }
        
        if(msg != ""){
            alert(msg);
            return false;
        }else{
            return true;
        }
        
    }
    <%-- ] ... end --%>
    
    <%-- 1030813 payTyp=1時tab跳過0000 --%>
    function tabChange(){
    
       if (Trim($("payTyp").value) == "1"){
           $("branchId").tabIndex = -1;
           $("chkBranchId").tabIndex = -1;
       }
       if (Trim($("payTyp").value) == "2" || Trim($("payTyp").value) == "A" || Trim($("payTyp").value) == ""){
           $("branchId").tabIndex = 195;
           $("chkBranchId").tabIndex = 215;
       }
    }
    
    <%-- 畫面清空重設 --%>
    function resetForm(){
        cleanForm();
        initAll();
    }
    
    //手機複驗
    function enableMobilePhone(){
        var tel1 = $("tel1").value;
        var tel2 = $("tel2").value;
        var shortTel1 = "";
        var shortTel2 = "";
        if(tel1.length >=2){
            shortTel1 = tel1.substring(0,2); 
        }
        if(tel2.length >=2){
            shortTel2 = tel2.substring(0,2); 
        }
    
        if((shortTel1=="09" && tel1.length==10) || (shortTel2=="09" && tel2.length==10)){
            $("mobilePhone").disabled = false;
            //$("mobilePhone").value = "";
        }else{
            $("mobilePhone").disabled = true;
            $("mobilePhone").value = "";
        }
    }
 	// Added by JohnsonHuang 20200115 [Begin]
	 //外國人身分證號碼自動帶入
    function autoForeignEvtSex(){
    	var secondText = $("evtIdnNo").value.substring(1,2);
		if($("evtIdnNo").value.length==10){
    	if($("evtNationTpe").value=="2"  && Trim($("evtSex").value)==""){
    		if(secondText=="A" || secondText=="a" || secondText=="C" || secondText=="c" || secondText=="8"){
    			$("evtSex").value = "1";	
    		}else if(secondText=="B" || secondText=="b" || secondText=="D" || secondText=="d" || secondText=="9"){
    			$("evtSex").value = "2";	
    		}else{
    			$("evtSex").value = "";
    			alert('「不符合外來人口統一證號編碼原則，請輸入正確身分證」');
    		}
    	}else{
    		if($("evtNationTpe").value=="2" && $("evtSex").value == "1"){
     			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
     				alert('身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
        		}
     		}else if($("evtNationTpe").value=="2" && $("evtSex").value == "2"){
     			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
     				alert('身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
        		}
     		}
    	}
		}
    }
 	// Added by EthanChen 20200115 [End]
 	// Added by EthanChen 20210512 for ba_11004227619 [Begin]
 	// 畫面載入時要預設畫面checkbox欄位是否勾選
 	function initChkbox(){
 		// init專戶欄位
 		if($("specialAcc").value == "Y"){
 			$("chkSpecialAcc").checked = true;
 		}else{
 			$("chkSpecialAcc").checked = false;
 		}
 	}
 	// 當專戶checkbox異動時要更新回hidden欄位
 	function specialAccOnchange(){
 		if($("chkSpecialAcc").checked){
 			$("specialAcc").value = "Y";
 		}else{
 			$("specialAcc").value = "";
 		}
 	}
	// Added by EthanChen 20210512 for ba_11004227619 [End]
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/oldAgeAnnuityReceiptInsert" method="post" onsubmit="return validateOldAgeAnnuityReceiptForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
               
        <fieldset>
            <legend>&nbsp;老年年金給付受理作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnConfirm">
                            <input tabindex="260" type="button" name="btnConfirm" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doInsert'; if (document.OldAgeAnnuityReceiptForm.onsubmit() && checkFields() && isValidEvtDateTrue()){document.OldAgeAnnuityReceiptForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input tabindex="270" type="button" name="btnClear" class="button" value="清　除" onclick="resetForm();"/>&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input tabindex="280" type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.OldAgeAnnuityReceiptForm.submit();"/>
                        </acl:checkButton>                        
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <html:text tabindex="2" property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" />
                                    &nbsp;-
                                    <html:text tabindex="4" property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value);" />
                                    &nbsp;-           
                                    <html:text tabindex="6" property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" />
                                    &nbsp;-
                                    <html:text tabindex="8" property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">申請單位保險證號：</span>
                                    <html:text tabindex="10" property="apUbno" styleId="apUbno" styleClass="textinput" size="9" maxlength="9" onblur="this.value=asc(this.value).toUpperCase();"/>
                                </td>
                                <td>
                                    <span class="needtxt">＊</span><span class="issuetitle_R_down">申請日期：</span>
                                    <html:text tabindex="20" property="appDate" styleId="appDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value).toUpperCase();"/>
                                </td>
                                <td>
                                    <span class="issuetitle_R_down">離職日期：</span>
                                    <html:text tabindex="30" property="evtJobDate" styleId="evtJobDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value).toUpperCase();"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                            <tr>
                                <td colspan="2" class="issuetitle_L">
                                    <span class="systemMsg">▲</span>&nbsp;個人資料
                                </td>
                            </tr>
                            <tr>
                                <td width="45%" id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">國籍別：</span>
                                    <html:text tabindex="40" property="evtNationTpe" styleId="evtNationTpe" styleClass="textinput" size="1" maxlength="1" onkeyup="changeEvtNationTpe();" onkeypress="chgEvtNationTpeFocus();" onblur="this.value=asc(this.value).toUpperCase();"/>
                                    <span class="formtxt">(1-本國，2-外籍)</span>
                                </td>
                                <td width="55%" id="iss">&nbsp;</td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">　性　別：</span>
                                    <html:text tabindex="50" property="evtSex" styleId="evtSex" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value);"/>
                                    <span class="formtxt">(外籍者請填寫，1-男，2-女)</span>
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">　國　籍：</span>
                                    <html:text tabindex="60" property="evtNationCode" styleId="evtNationCode" styleClass="textinput" size="3" maxlength="3" onblur="this.value=asc(this.value);"/>
                                    <label>
                                        <html:select tabindex="70" property="evtNationCodeOption" onchange="$('evtNationCode').value=$('evtNationCodeOption').value;">
                                            <html:option value="">請選擇</html:option>
                                            <html:options collection="<%=ConstantKey.COUNTRYID_OPTION_LIST%>" property="countryId" labelProperty="cname" />
                                        </html:select>    
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <html:text tabindex="80" property="evtName" styleId="evtName" styleClass="textinput" size="50" maxlength="50" onblur="this.value=unAscSpaceOtherAsc(this.value)"/>
                                    <span class="formtxt">
                                        (&nbsp;戶籍姓名<html:text property="cvldtlName" styleId="cvldtlName" styleClass="textinput" disabled="true" size="50"/>)
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss"><span class="needtxt">＊</span><span class="issuetitle_L_down">事故者身分證字號：</span>
                                    <html:text tabindex="90" property="evtIdnNo" styleId="evtIdnNo" styleClass="textinput"  size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();initCvldtlName();autoForeignEvtSex();"/>
                                </td>
                                <td id="iss"><span class="needtxt">＊</span><span class="issuetitle_L_down">事故者出生日期：</span>
                                    <html:text tabindex="100" property="evtBrDate" styleId="evtBrDate" styleClass="textinput" size="8" maxlength="8" onblur="this.value=asc(this.value);initCvldtlName();"/>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss"><span class="issuetitle_L_down">　電　話1：</span>
                                    <html:text tabindex="110" property="tel1" styleId="tel1" styleClass="textinput" size="20" maxlength="20" onblur="this.value=asc(this.value).toUpperCase(); enableMobilePhone();"/></td>
                                <td id="iss"><span class="issuetitle_L_down">　電　話2：</span>
                                    <html:text tabindex="120" property="tel2" styleId="tel2" styleClass="textinput" size="20" maxlength="20" onblur="this.value=asc(this.value).toUpperCase(); enableMobilePhone();"/></td>
                              </tr>
                              <tr>
                                <td colspan="2" id="iss"><span class="needtxt">＊</span><span class="issuetitle_L_down">地　址：</span>
                                <html:text tabindex="130" property="commTyp" styleId="commTyp" styleClass="textinput" size="1" maxlength="1" onkeyup="changeCommTyp();" onkeypress="chgCommTypFocus();" onblur="this.value=asc(this.value).toUpperCase();"/>
                                <span class="formtxt">(1-同戶籍地，2-現住址)　
                                      現住址：<html:text tabindex="140" property="commZip" styleId="commZip" styleClass="textinput" size="6" maxlength="6" onblur="this.value=asc(this.value);"/>(郵遞區號)&nbsp;-
                                <html:text tabindex="150" property="commAddr" styleId="commAddr" styleClass="textinput" size="72" maxlength="240" onblur="this.value=asc(this.value);"/></span></td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="2" >
                                    <span class="issuetitle_L_down">　手機複驗：</span>
                                    <html:text tabindex="155" property="mobilePhone" styleId="mobilePhone" styleClass="textinput"  size="10" maxlength="10" onblur="this.value=asc(this.value);"/>
                                </td>
                            </tr>                
                            <tr>
                                <td colspan="2" class="issuetitle_L"><br>
                                <span class="systemMsg">▲</span>&nbsp;給付資料</td>
                            </tr>
                            <tr>
                                <td id="iss"><span class="needtxt">＊</span><span class="issuetitle_R_down">申請項目：</span>
                                    <html:text tabindex="160" property="apItem" styleId="apItem" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value);"/>
                                    <span class="formtxt">(1-老年年金給付，2-減額老年年金給付)</span>
                                </td>
                                <td id="iss"><span class="issuetitle_L_down">　國、勞合併申請：</span>
                                    <html:text tabindex="170" property="combapMark" styleId="combapMark" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase()"/>
                                    <span class="formtxt">(國、勞合併申請者，請輸入Y)</span>                                
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" id="iss"><span class="needtxt">＊</span><span class="issuetitle_R_down">給付方式：</span>
                                    <html:text tabindex="180" property="payTyp" styleId="payTyp" styleClass="textinput" size="1" maxlength="1" onkeyup="changePayTyp();" onkeypress="chgPayTypFocus();" onblur="this.value=asc(this.value).toUpperCase();tabChange();"/>
                                    <span class="formtxt">(1-匯入銀行帳戶，2-匯入郵局帳號，A-扣押戶)</span>
                                    <html:checkbox property="chkSpecialAcc" styleId="chkSpecialAcc" onchange="specialAccOnchange();"/>專戶
                                    <html:hidden styleId="specialAcc" property="specialAcc"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="issuetitle_L_down" id="iss">　帳　號：
                                    <html:text tabindex="190" property="payBankId" styleId="payBankId" styleClass="textinput" size="1" maxlength="3" onblur="this.value=asc(this.value);"/>&nbsp;-
                                    <html:text tabindex="195" property="branchId" styleId="branchId" styleClass="textinput" size="1" maxlength="4" onblur="this.value=asc(this.value);"/>&nbsp;-
                                    <html:text tabindex="200" property="payEeacc" styleId="payEeacc" styleClass="textinput" size="14" maxlength="14" onblur="this.value=asc(this.value);"/>
                                    <html:hidden styleId="payBankIdBranchId" property="payBankIdBranchId"/>
                                </td>
                                <td class="issuetitle_L_down" id="iss">　帳號複驗：
                                     <html:text tabindex="210" property="chkPayBankId" styleId="chkPayBankId" styleClass="textinput" size="1" maxlength="3" onblur="this.value=asc(this.value);"/>&nbsp;-
                                    <html:text tabindex="215" property="chkBranchId" styleId="chkBranchId" styleClass="textinput" size="1" maxlength="4" onblur="this.value=asc(this.value);"/>&nbsp;-
                                    <html:text tabindex="220" property="chkPayEeacc" styleId="chkPayEeacc" styleClass="textinput" size="14" maxlength="14" onblur="this.value=asc(this.value);"/>
                                    <html:hidden styleId="chkPayBankIdChkBranchId" property="chkPayBankIdChkBranchId"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="issuetitle_L_down" colspan="2" id="iss">　法定代理人姓名：
                                    <html:text tabindex="230" property="grdName" styleId="grdName" styleClass="textinput" size="20" maxlength="50" onblur="this.value=asc(this.value);"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="issuetitle_L_down" id="iss">　法定代理人身分證字號：
                                    <html:text tabindex="240" property="grdIdnNo" styleId="grdIdnNo" styleClass="textinput" size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();"/>
                                </td>
                                <td class="issuetitle_L_down" id="iss">　法定代理人出生日期：
                                    <html:text tabindex="250" property="grdBrDate" styleId="grdBrDate" styleClass="textinput" size="8" maxlength="8" onblur="this.value=asc(this.value);"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>
                　           1.&nbsp;外藉人士請於身分證字號欄位填寫護照號碼、居留證號碼或統一編號。<br>
                　           2.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101；如民國前1年1月1日，請輸入-0010101。<br>
                　           3.&nbsp;<span class="needtxt">＊</span>為必填欄位。
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
