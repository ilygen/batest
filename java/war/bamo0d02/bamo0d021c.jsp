<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="tw.gov.bli.ba.update.forms.PaymentDataUpdateForm" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D021C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <script language="javascript" type="text/javascript">
    <!--     
    //畫面初始化
    function initAll(){       
        var payCategory = '<%=((PaymentDataUpdateForm)request.getSession().getAttribute(ConstantKey.PAYMENT_DATA_UPDATE_FORM)).getPayCategory()%>';     
        
        var benEvtRel = $("benEvtRel").value;
        var accRel = $("accRel").value;
        var benSelectResult = $("benSelectResult").value;
        var payTyp = $("payTyp").value;   
        var accSeqNoAmt = $("accSeqNoAmt").value;   
             
        if(benEvtRel=="1"||benEvtRel=="2"||benEvtRel=="3"||benEvtRel=="4"||benEvtRel=="5"||benEvtRel=="6"||benEvtRel=="7"){        
            if(benSelectResult=="0"){
                $("payCategory1").checked = true;
                $("payTyp").disabled = false;
                $("payCategory2").disabled = true;
                $("accData").disabled = true;                                                                       
            }
            if(benSelectResult=="1"){            
                $("accSeqNoInput").style.display="inline";
                $("payCategory1").checked = true;
                $("payTyp").disabled = false;
                
                if(accSeqNoAmt=="0"){
                    $("payCategory2").disabled = false;
                    $("accData").disabled = false;
                    $("addMemo").style.display="inline"; 
                    /*
                    if(accRel=="3"){
                        $("payTyp").value="";
                        $("payTyp").disabled = true;
                        $("payCategory2").checked = true;
                    }
                    if(payCategory=="1"){
                        $("payCategory1").checked = true;
                        $("payTyp").disabled = false;
                    }*/
                }else{
                    $("payCategory2").disabled = true;
                    $("accData").disabled = true;
                    $("addMemo").style.display="inline"; 
                }
            }                   
        }
        
        if(payCategory=="1" || (accRel!="3" && payCategory!="1" && payCategory!="2")){
            $("payCategory1").checked = true;
            $("accountInput1").style.display="none";
            $("accountInput2").style.display="none";
            $("accountInput3").style.display="none";
            $("accData").value = ""; 
            $("accData").disabled = true;
            
            if(payTyp==""){
                $("accountInput1").style.display="none";
                $("accountInput2").style.display="none";
                $("payBankId").disabled=true;  
                $("branchId").disabled=true; 
                $("payAccount").disabled=true;
                $("payEeacc").disabled=true;
                $("bankName").disabled=true;
                $("accountInput3").style.display="none";     
                $("accName").disabled=true;                                              
            }  
            if(payTyp=="1"||payTyp=="2"||payTyp=="7"||payTyp=="8"){
                $("accountInput1").style.display="inline";
                if($("payBankId").value + $("branchId").value == $("origPayBankIdBranchId").value && ($("origBankName").value!=null && $("origBankName").value!='')){
                    $("bankNameDiv").style.display="inline";
                }
                $("accountInput2").style.display="none";
                $("accountInput3").style.display="inline";
                $("accName").disabled=false;  

                //專戶
                if(payTyp=="1"||payTyp=="2"){
                   $("specialAccContent").style.display="inline";
                   if($("origSpecialAcc").value == "Y"){
                      $("specialAccAfter").checked = true;
                   }                             
                }else{
                   $("specialAccContent").style.display="none";    
                }                                                                                            
            }                        
            if(payTyp=="3"||payTyp=="4"||payTyp=="9"||payTyp=="A"){
                $("accountInput1").style.display="none";
                $("accountInput2").style.display="none";
                $("payBankId").disabled=true;  
                $("branchId").disabled=true; 
                $("payAccount").disabled=true;
                $("payEeacc").disabled=true;
                $("bankName").disabled=true;
                $("accountInput3").style.display="none";     
                $("accName").disabled=true;     
                $("specialAccContent").style.display="none";                                             
            }                        
            if(payTyp=="5"||payTyp=="6"){
                $("accountInput1").style.display="none";
                $("accountInput2").style.display="inline";
                $("payBankId").disabled=true;  
                $("branchId").disabled=true; 
                $("payEeacc").disabled=true; 
                $("accountInput3").style.display="inline"; 
                $("accName").disabled=false;    
                $("specialAccContent").style.display="none";                                      
            }
            if(payTyp=="1"){
               $("payBankId").disabled = false;
                $("branchId").value = '0000';
                $("branchId").readOnly = true;
                $("branchId").addClassName('disabledN');
                $("branchId").disabled = false;
                $("payEeacc").disabled = false;
            }
            if(payTyp=="2"){
                $("payBankId").disabled = false;
                $("branchId").disabled = false;
                $("payEeacc").disabled = false;
                $("branchId").readOnly = false;
                $("branchId").removeClassName('disabledN');

                if($("branchId").value == "0000"){
                  $("branchId").value = "";
                }
            }
        }
        
        if(payCategory==2 || (accRel=="3" && payCategory!="1" && payCategory!="2")){
            $("payCategory2").checked = true;
            $("payTyp").value="";
            $("payTyp").disabled = true; 
            $("accountInput1").style.display="none";
            $("accountInput2").style.display="none";
            $("accountInput3").style.display="none";
            $("payBankId").value="";
            $("branchId").value="";          
            $("payAccount").value=""; 
            $("payEeacc").value=""; 
            $("bankName").value=""; 
            $("accName").value="";
            $("accData").disabled = false; 
            
            //設定 具名領取選單的值
            for(i=0; i<$("accData").options.length; i++){
                var accData = $("accData").options[i].value;
                strs = accData.split(";");
                if($("origAccSeqNo").value == strs[1]){
                    $("accData").value = accData;
                    break;
                }
            }
        }
        
        //如果關係=F, N, disablen所有輸入欄位以及確認button
        var benEvtRel = ($("benEvtRel").value).toUpperCase();
        if(benEvtRel=="F" || benEvtRel=="N"){
            $("btnSave").disabled = true;
            for (i = 0; i < document.forms[0].length; i++){
                obj = document.forms[0].elements[i];
                if(obj.type == "text" || obj.type == "textarea" || obj.type == "radio" || obj.type == "checkbox" || obj.type == "select-one"){
                    obj.disabled = true;
                }            
            }
        }
        
        tabChange();
    }
    
     <%-- 1030813 payTyp=1時tab跳過0000 --%>
    function tabChange(){

       if (Trim($("payTyp").value) == "1"){
           $("branchId").tabIndex = -1;
       }else{
           $("branchId").tabIndex = 70;
       }
    
    }
    
    //變更 給付方式 時畫面異動
    function changePayCategory(){
        if($("payCategory1").checked==true){
            $("accData").value="";
            $("accData").disabled = true;
            $("payTyp").disabled = false; 
            $("payTyp").value="";
        }
        if($("payCategory2").checked==true){
            $("payTyp").value="";
            $("payTyp").disabled = true; 
            $("accountInput1").style.display="none";
            $("accountInput2").style.display="none";
            $("accountInput3").style.display="none";
            $("payBankId").value="";  
            $("branchId").value="";    
            $("payAccount").value=""; 
            $("payEeacc").value=""; 
            $("bankName").value=""; 
            $("accName").value="";
            $("accData").disabled = false; 
            $("accData").value=""; 
        }         
    }
    
    //變更 給付方式(本人領取) 時畫面異動  
    function changePayType(){      
        var payTyp = $("payTyp").value; 
        //$("payBankIdBranchId").value="";     
        //$("payAccount").value=""; 
        //$("payEeacc").value=""; 
        //$("bankName").value=""; 
        //$("accName").value="";       
        var payment = '<%=ConstantKey.BAAPPBASE_DATA_UPDATE_CASE%>';
        
        if(payTyp=="1"||payTyp=="2"||payTyp=="7"||payTyp=="8"){
            $("accountInput1").style.display="inline";
            if($("payBankId").value + $("branchId").value == $("origPayBankIdBranchId").value && ($("origBankName").value!=null && $("origBankName").value!='')){
                $("bankNameDiv").style.display="inline";
            }
            $("accountInput2").style.display="none";  
            $("payBankId").disabled = false;  
            $("branchId").disabled = false;    
            $("payEeacc").disabled = false;
            $("payAccount").disabled=true;
            $("bankName").disabled=true;       
            $("accountInput3").style.display="inline"; 
            $("accName").disabled = false;    
            
            if($("accName").value==""){
            	$("accName").value='<c:out value="${BaappbaseDataUpdateCase.benName}" />';  
            	
            }
            
            if(payTyp=="1"||payTyp=="2"){
                $("specialAccContent").style.display="inline";                             
            }else{
                $("specialAccContent").style.display="none";    
            }                    
        }                        
        if(payTyp==""||payTyp=="3"||payTyp=="4"||payTyp=="9"||payTyp=="A"){                
            $("accountInput1").style.display="none";
            $("bankNameDiv").style.display="none";
            $("accountInput2").style.display="none";  
            $("accountInput3").style.display="none";
            $("accName").disabled=true;    
            $("specialAccContent").style.display="none";                                      
        }                        
        if(payTyp=="5"||payTyp=="6"){
            $("accountInput1").style.display="none";
            $("bankNameDiv").style.display="none";
            $("accountInput2").style.display="inline";  
            $("payBankId").disabled = true; 
            $("branchId").disabled = true;        
            $("payEeacc").disabled = true;
            $("payAccount").disabled=false;
            $("bankName").disabled=false;       
            $("accountInput3").style.display="inline"; 
            $("accName").disabled = false; 
             if($("accName").value==""){
            	$("accName").value='<c:out value="${BaappbaseDataUpdateCase.benName}" />';  
            	
            }                    
            $("specialAccContent").style.display="none";                                   
        }    
         if(payTyp=="1"){
               $("payBankId").disabled = false;
                $("branchId").value = '0000';
                $("branchId").readOnly = true;
                $("branchId").addClassName('disabledN');
                $("branchId").disabled = false;
                $("payEeacc").disabled = false;
            }
            if(payTyp=="2"){
                $("payBankId").disabled = false;
                $("branchId").disabled = false;
                $("payEeacc").disabled = false;
                $("branchId").readOnly = false;
                $("branchId").removeClassName('disabledN');

                if($("branchId").value == "0000"){
                  $("branchId").value = "";
                }
            }     
    }
    
    //非行動不便之扣押戶，給付方式不得選擇匯票郵寄申請人(4)。請重新選擇給付方式
    function chkPayTyp(){
        if($("payTyp").value == "4"){
            if(confirm('<bean:message bundle="<%=Global.BA_MSG%>" key="msg.paymentDataUpdate.payTypLimited"/>')){
                $("payTyp").focus();
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }
    
    //存檔時，判斷給付方式為本人領取中的「1-匯入銀行帳戶」或「2-匯入郵寄帳號」，則出現提示訊息「請確認專戶的設定是否正確」
    function chkSpecialAcc(){
        var payTyp = $("payTyp").value; 
        if(payTyp=="1"||payTyp=="2"){
            if($("specialAccAfter").checked == false){

              if($("origPayBankIdBranchId").value != $("payBankId").value + $("branchId").value || $("origPayEeacc").value != $("payEeacc").value){
               if(confirm("如非專戶請按確定，如為專戶請按取消並勾選專戶")){
                  return true;
               }else{
                  return false;
               }
              }else{
                  return true;
              }
            }else{
               return true;
            }
        }else{
            return true;
        }
    }
    <%-- 進階欄位驗證 --%>
    <%-- 注意: 此部份之驗證須在 Validation.xml 驗證之後執行 --%>
    <%-- begin ... [ --%>
    function checkFields(){ 
        var msg = "";
        if($("payCategory1").checked==false&&$("payCategory2").checked==false){       
            msg += "請選擇「給付方式」";
            $("payCategory1").focus();
        }else{
            if($("payCategory1").checked==true){
                if($("payTyp").value==""){
                    msg += "請選擇「給付方式」(本人領取)";
                    $("payTyp").focus();                
                }else{
                    if (Trim($("payTyp").value) == "1" || Trim($("payTyp").value) == "2" || Trim($("payTyp").value) == "7" || Trim($("payTyp").value) == "8"){
                        var payBankIdBranchId = Trim($("payBankId").value) + Trim($("branchId").value);
            
                        if(Trim($("payBankId").value) == "" || Trim($("branchId").value) == "" || Trim($("payEeacc").value) == ""){
                            msg += '「帳號」為必填欄位。\r\n';
                            $("payBankId").focus();               
                        }else {
                            if(isNaN($("payBankId").value) || isNaN($("branchId").value)){
                                msg += '「帳號(前)」必須為數字。\r\n';  
                                $("payBankId").focus();   
                            }else if(Trim($("payBankId").value).length < 3){
                                msg += '「帳號(前)代號」不可小於3碼。\r\n';   
                                $("payBankId").focus();           
                            }else if(Trim($("branchId").value).length < 4){
                                msg += '「帳號(前)分支代號」不可小於4碼。\r\n';   
                                $("branchId").focus();           
                            }else{
                                if (Trim($("payTyp").value) == "1" && payBankIdBranchId.substr(0,3) == "700"){
                                    msg += '「帳號前三碼」不可為700。\r\n';
                                    $("payBankId").focus();           
                                }    
                                if (Trim($("payTyp").value) == "2" && payBankIdBranchId != "7000010" && payBankIdBranchId != "7000021"){
                                    msg += '「帳號(前)」限定只能輸入700-0010或700-0021。\r\n';
                                    $("payBankId").focus();           
                                }
                            }
                            
                            if(isNaN($("payEeacc").value)){
                                msg += '「帳號(後)」必須為數字。\r\n';
                                $("payEeacc").focus();     
                            }
                            if ((Trim($("payTyp").value) == "2" || Trim($("payTyp").value) == "7" || Trim($("payTyp").value) == "8") && payBankIdBranchId == "7000010" && $F("payEeacc").length != 8){
                                msg += '「帳號(後)」長度為8碼。\r\n';
                                $("payEeacc").focus();
                            }
                            if ((Trim($("payTyp").value) == "2" || Trim($("payTyp").value) == "7" || Trim($("payTyp").value) == "8") && payBankIdBranchId == "7000021" && $F("payEeacc").length != 14){
                                msg += '「帳號(後)」長度為14碼。\r\n';
                                $("payEeacc").focus();                        
                            }
                            if(msg==""){
                                //檢查郵局帳號格式
                                if(Trim($("payTyp").value) == "2" && isValidPayEeacc(payBankIdBranchId,Trim($("payEeacc").value))){
                                    msg += '「帳號」格式錯誤，請重新確認。\r\n';
                                    $("payEeacc").focus();           
                                }
                            }
                        }
                    }
                    
                    if (Trim($("payTyp").value) == "5" || Trim($("payTyp").value) == "6"){
                        if(Trim($("payAccount").value) == ""){
                            msg += '「帳號」為必填欄位。\r\n';
                        }else if(!isEngNum($("payAccount").value)){
                            msg += '「帳號」必須為英數字。\r\n';  
                            $("payAccount").focus();   
                        }
                        if(Trim($("bankName").value) == ""){
                            msg += '「金融機構名稱」為必填欄位。\r\n';
                            $("bankName").focus();   
                        }    
                    }       
                    if(Trim($("payTyp").value) == "1"||Trim($("payTyp").value) == "2"||Trim($("payTyp").value) == "5"||Trim($("payTyp").value) == "6"||Trim($("payTyp").value) == "7"||Trim($("payTyp").value) == "8"){
                        if (Trim($("accName").value) == ""){
                            msg += '「戶名」為必填欄位。\r\n';
                            $("accName").focus();
                        }
                    }    
                }
            }
            if($("payCategory2").checked==true){
                if($("accData").value==""){
                    msg += "請選擇「給付方式」(具名領取)";
                    $("accData").focus();  
                }
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
        
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/paymentDataUpdate" method="post" >
        <bean:define id="payment" name="<%=ConstantKey.BAAPPBASE_DATA_UPDATE_CASE%>"/>
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="apNo" property="apNo" />
        <html:hidden styleId="seqNo" property="seqNo" />
        <html:hidden styleId="baappbaseId" property="baappbaseId" />
        <html:hidden styleId="benIdnNo" property="benIdnNo" />
        <html:hidden styleId="benName" property="benName" />
        <html:hidden styleId="accRel" property="accRel"/>
        <html:hidden styleId="benSelectResult" property="benSelectResult"/>
        <input type="hidden" id="origPayBankIdBranchId" name="origPayBankIdBranchId" value="<c:out value="${payment.payBankIdBranchId}" />"/>
        <input type="hidden" id="origPayBankId" name="origPayBankId" value="<c:out value="${payment.payBankId}" />"/>
        <input type="hidden" id="origPayEeacc" name="origPayEeacc" value="<c:out value="${payment.payEeacc}" />"/>
        <input type="hidden" id="origBankName" name="origBankName" value="<c:out value="${payment.bankName}" />"/>
        <input type="hidden" id="origAccSeqNo" name="origAccSeqNo" value="<c:out value="${payment.accSeqNo}" />"/>
        <input type="hidden" id="accSeqNoAmt"  name="accSeqNoAmt"  value="<c:out value="${payment.accSeqNoAmt}" />"/>
        <input type="hidden" id="origSpecialAcc" name="origSpecialAcc" value="<c:out value="${payment.specialAcc}" />"/>
        
        <fieldset>
            <legend>&nbsp;給付資料更正&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnSave">
                            <input tabindex="250" type="button" name="btnSave" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doUpdate'; if(chkPayTyp()){if(checkFields()){if(chkSpecialAcc()){document.PaymentDataUpdateForm.submit();}else{return false;}}else{return false;}}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input tabindex="260" type="reset" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doModifyBack'; document.PaymentDataUpdateForm.submit();"/>
                        </acl:checkButton>                        
                    </td>
                </tr> 
                <tr>
                    <td>
                        <html:hidden styleId="benEvtRel" name="payment" property="benEvtRel"/>
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${payment.apNoStrDisplay}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:if test='${(payment.pagePayKind eq "L")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                    </c:if>
                                    <c:if test='${(payment.pagePayKind eq "K")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                    </c:if>
                                    <c:if test='${(payment.pagePayKind eq "S")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                    </c:if>                                                                       
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${payment.appDateStr}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故日期：</span>
                                    <c:out value="${payment.evtJobDateStr}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${payment.evtName}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${payment.evtIdnNo}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${payment.evtBrDateStr}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                            <tr>
                                <td colspan="3" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;受款人資料</td>
                            </tr>
                            <tr>
                                <td colspan="3" width="100%" id="iss">
                                    <span class="issuetitle_L_down">受款人姓名：</span>
                                    <c:out value="${payment.benName}" />
                                </td>
                            </tr>
                            <tr>
                                <td width="23%" id="iss">   
                                    <span class="issuetitle_L_down">受款人身分證號：</span>
                                    <c:out value="${payment.benIdnNo}" />    
                                </td>
                                <td width="23%" id="iss">
                                    <span class="issuetitle_L_down">受款人出生日期：</span>
                                    <c:out value="${payment.benBrDateStr}" />
                                </td>
                                <td width="24%" id="iss">
                                    <span class="issuetitle_L_down">關　係：</span>
                                    <c:if test='${(payment.benEvtRel eq "1")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "2")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "3")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "4")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "5")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "6")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "7")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "A")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "C")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "E")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "F")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "N")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "Z")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z%>" />
                                    </c:if>
                                    <c:if test='${(payment.benEvtRel eq "O")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O%>" />
                                    </c:if>
                                </td>
                            </tr>                            
                            <tr>
                                <td id="iss" colspan="3">
                                    <span class="needtxt">＊</span><span class="issuetitle_L_down">給付方式：</span>
                                    <span class="formtxt">
                                        <html:radio tabindex="10" styleId="payCategory1" property="payCategory" value="1" onclick="changePayCategory();" />本人領取
                                        <html:select tabindex="20" property="payTyp" onchange="changePayType();" onblur="tabChange();">
                                            <html:option value="">請選擇</html:option>
                                            <html:options collection="<%=ConstantKey.PAYTYP_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                        </html:select>        

                                　                                                                                    <div id="accSeqNoInput" style="display: none;">
                                        <html:radio tabindex="30" styleId="payCategory2" property="payCategory" value="2" onclick="changePayCategory();" />具名領取
                                        <html:select tabindex="40" property="accData" >
                                            <html:option value="">請選擇</html:option>
                                            <logic:notEmpty name="<%=ConstantKey.BEN_OPTION_LIST%>">
                                                <logic:iterate id="benList" name="<%=ConstantKey.BEN_OPTION_LIST%>">
                                                    <html:option value="${benList.baappbaseId};${benList.seqNo}" ><c:out value="${benList.benName}" /></html:option>                                                
                                                </logic:iterate>
                                            </logic:notEmpty>
                                        </html:select> 
                                        </div>
                                        <div id="specialAccContent" style="display: none;">   
                    		                 <input tabindex="50" type="checkbox" id="specialAccAfter" name="specialAccAfter" value="Y" >專戶
                    	                </div> 
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">                                    
                                    <div id="accountInput1" style="display: none;">
                                        <span class="needtxt">＊</span><span class="issuetitle_L_down">帳號：</span>
                                        <html:text tabindex="60" property="payBankId" styleId="payBankId" styleClass="textinput" size="1" maxlength="3" onchange="if(this.value == $('origPayBankId').value){$('bankNameDiv').style.display='inline';}else{$('bankNameDiv').style.display='none'}" onblur="this.value=asc(this.value);" />&nbsp;-
                                        <html:text tabindex="70" property="branchId" styleId="branchId" styleClass="textinput" size="1" maxlength="4"  onblur="this.value=asc(this.value);" />&nbsp;-
                                        <html:text tabindex="80" property="payEeacc" styleId="payEeacc" styleClass="textinput" size="20" maxlength="14" onblur="this.value=asc(this.value);"/>     
                                        <html:hidden styleId="payBankIdBranchId" property="payBankIdBranchId"/>                                   　   
                                        <div id="bankNameDiv" style="display: none">
                                            (<font color="#8c8c8c"><c:out value="${payment.bankName}"/></font>)
                                        </div>
                                    </div>
                                    <div id="accountInput2" style="display: none;">
                                        <span class="needtxt">＊</span><span class="issuetitle_L_down">帳號：</span>
                                        <html:text tabindex="90" property="payAccount" styleId="payAccount" styleClass="textinput" size="21" maxlength="21" onblur="this.value=asc(this.value);"/>
                                        <span class="needtxt">＊</span><span class="issuetitle_L_down">金融機構名稱：</span>
                                        <html:text tabindex="100" property="bankName" styleId="bankName" styleClass="textinput" size="50" maxlength="120" onblur="this.value=asc(this.value);"/>                                   　   
                                    </div>
                                    <div id="accountInput3" style="display: none;">
                                        <span class="needtxt">＊</span><span class="issuetitle_L_down">戶名：</span>
                                        <html:text tabindex="110" property="accName" styleId="accName" styleClass="textinput" size="20" maxlength="50" onblur="this.value=asc(this.value).toUpperCase();"/>                                        　   
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                 </tr>
                 <tr>
                    <td>
                        <hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>
                             　1.&nbsp;給付方式如為「具名領取」者，請修改該具名領取受款人的給付資料。<br>
                             　2.&nbsp;<span class="needtxt">＊</span>為必填欄位。<br>
                        <div id="addMemo" style="display: none">
                             　3.&nbsp;受款人為「具名領取」受款人，無法將給付方式更正為具名領取。
                        </div>                        
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