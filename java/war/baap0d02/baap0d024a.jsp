<%@ page language="java" pageEncoding="UTF-8"%>                                                                                                                                                                                                                                                                                                               
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="tw.gov.bli.ba.receipt.helper.CaseSessionHelper" %>                                                                                                                                                                                                                                                                                                             
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">                                                                                                                                                                                                                                                                                                
<html:html lang="true">                                                                                                                                                                                                                                                                                                                                       
<head>                                                                                                                                                                                                                                                                                                                                                        
    <acl:setProgId progId="BAAP0D024A" />                                                                                                                                                                                                                                                                                                                     
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>                                                                                                                                                                                                                                                                       
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />                                                                                                                                                                                                                                                                        
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />                                                                                                                                                                                                                                                                           
    <script type='text/javascript' src='<c:url value="/dwr/interface/receiptCommonAjaxDo.js"/>'></script>                                                                                                                                                                                                                                                     
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>                                                                                                                                                                                                                                                                            
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>                                                                                                                                                                                                                                                                              
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>                                                                                                                                                                                                                                                                          
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>                                                                                                                                                                                                                                                                       
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>                                                                                                                                                                                                                                                                          
    <html:javascript formName="DisabledAnnuityReceiptForm" page="2" />                                                                                                                                                                                                                                                                                        
    <html:javascript formName="DisabledAnnuityReceiptFamForm" page="1" />                                                                                                                                                                                                                                                                                        
    <script language="javascript" type="text/javascript">                                                                                                                                                                                                                                                                                                     
    var nowDate = "<%=DateUtility.getNowChineseDate()%>";                                                                                                                                                                                                                                                                                                     
                                                                                                                                                                                                                                                                                                                                                              
    //變更 事故者國籍別 時畫面異動                                                                                                                                                                                                                                                                                                                            
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
        }else if($("evtNationTpe").value=="2"){                                                                                                                                                                                                                                                                                                                     
            $("evtSex").disabled = false;                                                                                                                                                                                                                                                                                                                     
            $("evtNationCode").disabled = false;                                                                                                                                                                                                                                                                                                              
            $("evtNationCodeOption").disabled = false;                                                                                                                                                                                                                                                                                                        
            $("commTyp").value="2";                                                                                                                                                                                                                                                                                                                           
            $("commTyp").readOnly = true;                                                                                                                                                                                                                                                                                                                     
            $("commZip").disabled = false;                                                                                                                                                                                                                                                                                                                    
            $("commAddr").disabled = false;                                                                                                                                                                                                                                                                                                                   
        }else{
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
	 autoForeignEvtSex();                                                                                                                                                                                                                                                                                                                                                  
    }                                                                                                                                                                                                                                                                                                                                                         
                                                                                                                                                                                                                                                                                                                                                              
    function chgEvtNationTpeFocus(){                                                                                                                                                                                                                                                                                                                          
        if(($("evtNationTpe").value=="1") && event.keyCode==9){                                                                                                                                                                                                                                                                                               
            $("evtName").focus();                                                                                                                                                                                                                                                                                                                             
        }else if(($("evtNationTpe").value=="2") && event.keyCode==9){                                                                                                                                                                                                                                                                                         
            $("evtSex").focus();                                                                                                                                                                                                                                                                                                                              
        }                                                                                                                                                                                                                                                                                                                                                     
    }                                                                                                                                                                                                                                                                                                                                                         
                                                                                                                                                                                                                                                                                                                                                              
    //變更 眷屬國籍別 時畫面異動                                                                                                                                                                                                                                                                                                                              
    function changeFamNationTyp(){ 
        if($("famNationTyp").value=="1"){ 
            $("famSex").disabled = true;                                                                                                                                                                                                                                                                                                                      
            $("famSex").value = "";                                                                                                                                                                                                                                                                                                                           
            $("famNationCode").disabled = true;                                                                                                                                                                                                                                                                                                               
            $("famNationCode").value = "";                                                                                                                                                                                                                                                                                                                    
            $("famNationCodeOption").disabled = true;                                                                                                                                                                                                                                                                                                         
            $("famNationCodeOption").value = "";                                                                                                                                                                                                                                                                                                              
        }else if($("famNationTyp").value=="2"){
            $("famSex").disabled = false;                                                                                                                                                                                                                                                                                                                     
            $("famNationCode").disabled = false;                                                                                                                                                                                                                                                                                                              
            $("famNationCodeOption").disabled = false;                                                                                                                                                                                                                                                                                                        
        }
        else{
            $("famSex").disabled = true;                                                                                                                                                                                                                                                                                                                      
            $("famSex").value = "";                                                                                                                                                                                                                                                                                                                           
            $("famNationCode").disabled = true;                                                                                                                                                                                                                                                                                                               
            $("famNationCode").value = "";                                                                                                                                                                                                                                                                                                                    
            $("famNationCodeOption").disabled = true;                                                                                                                                                                                                                                                                                                         
            $("famNationCodeOption").value = "";
        }
	autoForeignFamSex();                                                                                                                                                                                                                                                                                                                                                   
    }                                                                                                                                                                                                                                                                                                                                                         
                                                                                                                                                                                                                                                                                                                                                              
    function chgFamNationTypFocus(){ 
        if(($("famNationTyp").value=="1") && event.keyCode==9){                                                                                                                                                                                                                                                                                               
            $("famName").focus();                                                                                                                                                                                                                                                                                                                             
        }else if(($("famNationTyp").value=="2") && event.keyCode==9){                                                                                                                                                                                                                                                                                         
            $("famSex").focus();                                                                                                                                                                                                                                                                                                                              
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
                                                                                                                                                                                                                                                                                                                                                              
    //變更 眷屬關係 時畫面異動                                                                                                                                                                                                                                                                                                                                
    function chgFamEvtRel(){                                                                                                                                                                                                                                                                                                                               
        if($("famEvtRel").value=="2"){                                                                                                                                                                                                                                                                                                                        
            $("marryDate").disabled = false;                                                                                                                                                                                                                                                                                                                  
            //$("marryDate").value = "";                                                                                                                                                                                                                                                                                                                        
            $("raiseChildMk").disabled = true;
            $("raiseChildMk").value = "";
            $("studMk").disabled = true;                                                                                                                                                                                                                                                                                                                   
            $("studMk").value = "";
            $("handIcapMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
            //$("handIcapMk").value = "";     
            $("interDictMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
            //$("interDictMk").value = "";                                                                                                                                                                                                                                                                                                                           
        }else if($("famEvtRel").value=="4"){                                                                                                                                                                                                                                                                                                                        
            $("marryDate").disabled = true;                                                                                                                                                                                                                                                                                                                  
            $("marryDate").value = "";                                                                                                                                                                                                                                                                                                                        
            $("raiseChildMk").disabled = false;
            //$("raiseChildMk").value = "";
            $("studMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
            //$("studMk").value = "";
            $("handIcapMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
            //$("handIcapMk").value = "";     
            $("interDictMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
            //$("interDictMk").value = "";                                                                                                                                                                                                                                                                                                                            
        }else if($("famEvtRel").value=="7"){                                                                                                                                                                                                                                                                                                                        
            $("marryDate").disabled = true;                                                                                                                                                                                                                                                                                                                  
            $("marryDate").value = "";                                                                                                                                                                                                                                                                                                                        
            $("raiseChildMk").disabled = true;
            $("raiseChildMk").value = "";
            $("studMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
            //$("studMk").value = "";
            $("handIcapMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
            //$("handIcapMk").value = "";     
            $("interDictMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
            //$("interDictMk").value = "";                                                                                                                                                                                                                                                                                                                   
        }else if($("famEvtRel").value=="3"||$("famEvtRel").value=="5"){                                                                                                                                                                                                                                                                                                                                              
            $("marryDate").disabled = true;                                                                                                                                                                                                                                                                                                                  
            $("marryDate").value = "";                                                                                                                                                                                                                                                                                                                        
            $("raiseChildMk").disabled = true;
            $("raiseChildMk").value = "";
            $("studMk").disabled = true;                                                                                                                                                                                                                                                                                                                   
            $("studMk").value = "";
            $("handIcapMk").disabled = true;                                                                                                                                                                                                                                                                                                                   
            $("handIcapMk").value = "";     
            $("interDictMk").disabled = true;                                                                                                                                                                                                                                                                                                                   
            $("interDictMk").value = "";                                                                                                                                                                                                                                                                                             
        }else {                                                                                                                                                                                                                                                                                                                                              
            $("marryDate").disabled = true;                                                                                                                                                                                                                                                                                                                  
            $("marryDate").value = "";                                                                                                                                                                                                                                                                                                                        
            $("raiseChildMk").disabled = true;
            $("raiseChildMk").value = "";
            $("studMk").disabled = true;                                                                                                                                                                                                                                                                                                                   
            $("studMk").value = "";
            $("handIcapMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
            //$("handIcapMk").value = "";     
            $("interDictMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
            //$("interDictMk").value = "";                                                                                                                                                                                                                                                                                                                       
        }                                                                                                                                                                                                                                                                                                                                                     
    }
    
    function chgFamEvtRelFocus(){ 
        if(($("famEvtRel").value=="2") && event.keyCode==9){                                                                                                                                                                                                                                                                                               
            $("marryDate").focus();                                                                                                                                                                                                                                                                                                                             
        }else if(($("famEvtRel").value=="4") && event.keyCode==9){                                                                                                                                                                                                                                                                                         
            $("raiseChildMk").focus();                                                                                                                                                                                                                                                                                                                              
        }else if(($("famEvtRel").value=="7") && event.keyCode==9){                                                                                                                                                                                                                                                                                         
            $("studMk").focus();                                                                                                                                                                                                                                                                                                                              
        }else if(($("famEvtRel").value=="3"||$("famEvtRel").value=="5") && event.keyCode==9){                                                                                                                                                                                                                                                                                         
            $("monIncomeMk").focus();                                                                                                                                                                                                                                                                                                                              
        }                                                                                                                                                                                                                                                                                                                                                     
    }
    
    //變更 每月工作收入 時畫面異動
    function chgMonIncomeMk(){
        //只有 每月工作收入註記=Y 時可以輸入收入金額
        if(Trim(asc($("monIncomeMk").value).toUpperCase()) == "Y"){
            $("monIncome").disabled = false;
        }else{
            $("monIncome").value = "";
            $("monIncome").disabled = true;
        }
    }
    
    function chgMonIncomeMkFocus(){
        if((Trim(asc($("monIncomeMk").value).toUpperCase()) == "Y") && event.keyCode==9){
            $("monIncome").focus();
        }else if((Trim(asc($("monIncomeMk").value).toUpperCase()) != "Y") && event.keyCode==9){
            $("handIcap").focus();
        }
    }
    
    // 變更 受傷部位、失能項目、醫師姓名、國際疾病代碼 欄位狀態
    function chgInputTextStatus(origObj, nextObj){
        var origObjName = "";
        var nextObjName = "";
        
        if (typeof origObj == 'string'){
            origObjName = origObj;
            origObj = document.getElementById(origObj);
        }
        if (typeof nextObjName == 'string'){
            nextObjName = nextObj;
            nextObj = document.getElementById(nextObj);
        }
           
        if(Trim(origObj.value) !=""){
            nextObj.disabled = false;                                                                                                                                                                                                                                                                                                                  
        }else{
            var objName = origObjName.substring(0, origObjName.length-1);
            var objIdx = parseNumber(origObjName.substring(origObjName.length-1, origObjName.length))+1;

            //受傷部位
            if(origObjName.indexOf('criInPart') != -1){
                for(var i=objIdx; i<=3; i++){
                    nextObj = document.getElementById(objName+i);
                    nextObj.disabled = true;
                    nextObj.value = "";
                }
            }
            //失能項目
            else if(origObjName.indexOf('criInJdp') != -1){
                for(var i=objIdx; i<=10; i++){
                    nextObj = document.getElementById(objName+i);
                    nextObj.disabled = true;
                    nextObj.value = "";
                }
            }
            //醫師姓名
            else if(origObjName.indexOf('doctorName') != -1){
                for(var i=objIdx; i<=2; i++){
                    nextObj = document.getElementById(objName+i);
                    nextObj.disabled = true;
                    nextObj.value = "";
                }
            }
            //國際疾病代碼
            else if(origObjName.indexOf('criInJnme') != -1){
                for(var i=objIdx; i<=4; i++){
                    nextObj = document.getElementById(objName+i);
                    nextObj.disabled = true;
                    nextObj.value = "";
                }
            }
        }
    }  
    
    function chgInputTextFocus(origObj, nextObj){
        var origObjName = "";
        var nextObjName = "";
        
        if (typeof origObj == 'string'){
            origObjName = origObj;
            origObj = document.getElementById(origObj);
        }
        if (typeof nextObjName == 'string'){
            nextObjName = nextObj;
            nextObj = document.getElementById(nextObj);
        }
        
        //受傷部位
        if(origObjName.indexOf('criInPart') != -1){
            if((Trim(origObj.value) != "") && event.keyCode==9){
                nextObj.focus();
            }else if((Trim(origObj.value) == "") && event.keyCode==9){
                $("criMedium").focus();
            }
        }
        //失能項目
        else if(origObjName.indexOf('criInJdp') != -1){
            if((Trim(origObj.value) != "") && event.keyCode==9){
                nextObj.focus();
            }else if((Trim(origObj.value) == "") && event.keyCode==9){
                $("hosId").focus();
            }
        }
        //醫師姓名
        else if(origObjName.indexOf('doctorName') != -1){
            if((Trim(origObj.value) != "") && event.keyCode==9){
                nextObj.focus();
            }else if((Trim(origObj.value) == "") && event.keyCode==9){
                $("criInJnme1").focus();
            }
        }
        //國際疾病代碼
        else if(origObjName.indexOf('criInJnme') != -1){
            if((Trim(origObj.value) != "") && event.keyCode==9){
                nextObj.focus();
            }else if((Trim(origObj.value) == "") && event.keyCode==9){
                $("payTyp").focus();
            }
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
    
    //檢核事故者出生日期 20121220 邏輯修改
    function isValidFamDateTrue() {   
        var evtBrDate = $("famBrDate").value;
        
        if(isValidDate($("famBrDate").value) == false){
          if(confirm("輸入之「眷屬出生日期」錯誤，是否繼續進行存檔?") == true){
          return true;
          }else{
          $("famBrDate").focus();
          return false;
          }
        }else{
          return true;
        }

    }                                                                                                                                                                                                                                                                                                                                                       
                                                                                                                                                                                                                                                                                                                                                              
    function fillCvldtlName(name) {                                                                                                                                                                                                                                                                                                                           
        $("cvldtlName").value = name;                                                                                                                                                                                                                                                                                                                         
    }
    
    //取得欲修改之眷屬資料
    function getFamDetailData(bafamilytempId, seqNo){
        receiptCommonAjaxDo.getDisabledFamTempDetailData(bafamilytempId, seqNo, setFamData);  
    }
    
    function setFamData(famCase){
        $("seqNo").value = famCase.seqNo;     
        $("famNationTyp").value = famCase.famNationTyp;     
        if(famCase.famNationTyp == '2'){                                  
            $("famSex").value = famCase.famSex;                                       
            $("famNationCode").value = famCase.famNationCode;
            $("famNationCodeOption").value = famCase.famNationCode;
        }                                       
        $("famName").value = famCase.famName;                                       
        $("famAppDate").value = famCase.famAppDate;                                       
        $("famIdnNo").value = famCase.famIdnNo;                                       
        $("famBrDate").value = famCase.famBrDate;
        $("famEvtRel").value = famCase.famEvtRel;
        //$("marryDate").value = famCase.marryDate;                                       
        //$("raiseChildMk").value = famCase.raiseChildMk;                                       
        //$("studMk").value = famCase.studMk;                                       
        $("monIncomeMk").value = famCase.monIncomeMk;
        if(famCase.monIncome!="" && famCase.monIncome!="null"  && famCase.monIncome!=null){                                       
            $("monIncome").value = famCase.monIncome;            
        }                           
        //$("handIcapMk").value = famCase.handIcapMk;                                       
        //$("interDictMk").value = famCase.interDictMk;
        
        $("insertModeBtn").style.display="none";
        $("insertModeStr").style.display="none";    
        $("updateModeBtn").style.display="inline";    
        $("updateModeStr").style.display="inline";
        $("famNationTyp").focus;   
        changeFamNationTyp();
        chgFamEvtRel();
        if(famCase.famEvtRel == '2'){
            $("marryDate").value = famCase.marryDate;                                       
        }
        if(famCase.famEvtRel == '4'){
            $("raiseChildMk").value = famCase.raiseChildMk;  
            $("studMk").value = famCase.studMk;                         
        }
        if(famCase.famEvtRel == '7'){
            $("studMk").value = famCase.studMk;                          
        }
        if(famCase.famEvtRel != '3' && famCase.famEvtRel != '5'){
            $("handIcapMk").value = famCase.handIcapMk;      
            $("interDictMk").value = famCase.interDictMk;
        }
        chgMonIncomeMk();
        
        $("famNationTyp").focus();
    }
    
    <%-- 進階欄位驗證 --%>                                                                                                                                                                                                                                                                                                                                    
    <%-- 注意: 此部份之驗證須在 Validation.xml 驗證之後執行 --%>                                                                                                                                                                                                                                                                                              
    <%-- begin ... [ --%>                                                                                                                                                                                                                                                                                                                                     
    function checkFields(){                                                                                                                                                                                                                                                                                                                                   
        var msg = "";
        
        if(Trim($("apNo1").value)==""){
            msg+= "「受理編號第一欄」為必填欄位。\r\n"
            $("apNo1").focus();
        }
        if(Trim($("apNo2").value)==""){
            msg+= "「受理編號第二欄」為必填欄位。\r\n"
            $("apNo2").focus();
        }
        if(Trim($("apNo3").value)==""){
            msg+= "「受理編號第三欄」為必填欄位。\r\n"
            $("apNo3").focus();
        }
        if(Trim($("apNo4").value)==""){
            msg+= "「受理編號第四欄」為必填欄位。\r\n"
            $("apNo4").focus();
        }                                                                                                                                                                                                                                                                                                                                         
                                                                                                                                                                                                                                                                                                                                                 
        if(msg != ""){                                                                                                                                                                                                                                                                                                                                        
            alert(msg);                                                                                                                                                                                                                                                                                                                                       
            return false;                                                                                                                                                                                                                                                                                                                                     
        }else{                                                                                                                                                                                                                                                                                                                                                
            return true;                                                                                                                                                                                                                                                                                                                                      
        }                                                                                                                                                                                                                                                                                                                                                     
                                                                                                                                                                                                                                                                                                                                                              
    }                                                                                                                                                                                                                                                                                                                                                         
    <%-- ] ... end --%>                                                                                                                                                                                                                                                                                                                                       
                                                                                                                                                                                                                                                                                                                                                              
    <%-- 進階欄位驗證 --%>                                                                                                                                                                                                                                                                                                                                    
    <%-- 注意: 此部份之驗證須在 Validation.xml 驗證之後執行 --%>                                                                                                                                                                                                                                                                                              
    <%-- begin ... [ --%>
    //檢核事故者出生日期 20121220 邏輯修改
                                                                                                                                                                                                                                                                                                                            
    function checkFamFields(){                                                                                                                                                                                                                                                                                                                                
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
		
		var famText = $("famIdnNo").value.substring(1,2);
		if($("famIdnNo").value.length==10){
		if($("famNationTyp").value=="2" && $("famSex").value == "1"){
 			if(famText!="A" && famText!="a" && famText!="C" && famText!="c" && famText!="8"){
 				msg += '眷屬，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';	
 				 $("famSex").focus();
    		}
 		}else if($("famNationTyp").value=="2" && $("famSex").value == "2"){
 			if(famText!="B" && famText!="b" && famText!="D" && famText!="d" && famText!="9"){
 				msg += '眷屬，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';	
 				 $("famSex").focus();
    		}
 		}
		}
		
        // 檢查眷屬身分證號與生日是否與事故者相同
        if($("famIdnNo").value == $("evtIdnNo").value && $("famBrDate").value == $("evtBrDate").value){
            msg += " 眷屬資料與事故者資料不得重覆。\r\n"
            $("famIdnNo").focus();
        }
                                                                                                                                                                                                                                                                                                                                                             
        if(parseNumber($("famAppDate").value)<parseNumber("0980101")){                                                                                                                                                                                                                                                                                           
            msg += "「眷屬申請日期」不可小於 0980101。\r\n"                                                                                                                                                                                                                                                                                                       
            $("famAppDate").focus();                                                                                                                                                                                                                                                                                                                             
        }                                                                                                                                                                                                                                                                                                                                                     
        <%--2009.11.23修改 國籍別=2時 性別&國籍非必填                                                                                                                                                                                                                                                                                                                                                     
        if($("famNationTyp").value=="2"){                                                                                                                                                                                                                                                                                                                     
            if($("famSex").value == ""){                                                                                                                                                                                                                                                                                                                      
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.disabledAnnuityReceipt.famSex")%>' />\r\n"                                                                                                                                                                             
                $("famSex").focus();                                                                                                                                                                                                                                                                                                                          
            }                                                                                                                                                                                                                                                                                                                                                 
            if($("famNationCode").value == ""){                                                                                                                                                                                                                                                                                                               
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.disabledAnnuityReceipt.famNationCode")%>' />\r\n"                                                                                                                                                                      
                $("famNationCode").focus();                                                                                                                                                                                                                                                                                                                   
            }                                                                                                                                                                                                                                                                                                                                                 
        }  
        --%>  
        //檢查結婚日期
        //if($("famEvtRel").value=="2"){
        //    if($("marryDate").value == ""){
        //        msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.survivorAnnuityReceipt.marryDate")%>' />\r\n"
        //        $("marryDate").focus();
        //    }
        //}      
        
        //檢核事故者出生日期  是否為數字 及 年月格式
        if($("famBrDate").value.length < 7){
                    msg += '輸入之「眷屬出生日期」格式錯誤，請重新輸入\r\n';
                    $("famBrDate").focus();
        } 
        if(isNaN($("famBrDate").value)){
                    msg += '輸入之「眷屬出生日期」格式錯誤，請重新輸入\r\n';
                    $("famBrDate").focus();
        }
        if($("famBrDate").value.length == 7){
           var chkMonth = $("famBrDate").value.substring(3,5);
           var chkDay   = $("famBrDate").value.substring(5,7);
           if(chkMonth > 12 || chkDay > 32){
                    msg += '輸入之「眷屬出生日期」格式錯誤，請重新輸入\r\n';
                    $("famBrDate").focus();
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
                                                                                                                                                                                                                                                                                                                                                              
    function initAll(){
        
        if('<c:out value="${DisabledAnnuityReceiptFamForm.famOptionMode}" />'=="updateMode"){
            $("famOptionMode").value="updateMode";
            $("insertModeBtn").style.display="none";  
            $("insertModeStr").style.display="none";  
            $("updateModeBtn").style.display="inline";
            $("updateModeStr").style.display="inline";   
            $("dataIndex").innerHTML = $("dataIdx").value;
        }else{
            $("famOptionMode").value="insertMode";
            $("insertModeBtn").style.display="inline";  
            $("insertModeStr").style.display="inline";  
            $("updateModeBtn").style.display="none";
            $("updateModeStr").style.display="none";
        }
        
        //受理編號第一碼
        if('<c:out value="${DisabledAnnuityReceiptForm.apNo1}" />'==""){
            $("apNo1").value = "K"; 
        }
       
        //事故者國籍                                                                                                                                                                                                                                                                                                                                    
        if('<c:out value="${DisabledAnnuityReceiptForm.evtNationTpe}" />'!=""){
            if('<c:out value="${DisabledAnnuityReceiptForm.evtNationTpe}" />'=="1"){                                                                                                                                                                                                                                                                                            
                $("evtSex").disabled = true;                                                                                                                                                                                                                                                                                                                  
                $("evtNationCode").disabled = true;                                                                                                                                                                                                                                                                                                           
                $("evtNationCodeOption").disabled = true;                                                                                                                                                                                                                                                                                                     
                $("evtSex").value = "";                                                                                                                                                                                                                                                                                                                       
                $("evtNationCode").value = "";                                                                                                                                                                                                                                                                                                                
                $("evtNationCodeOption").value = "";                                                                                                                                                                                                                                                                                                          
                $("commTyp").readOnly = false;                                                                                                                                                                                                                                                                                                                
                if('<c:out value="${DisabledAnnuityReceiptForm.commTyp}" />'=="1"){                                                                                                                                                                                                                                                                                             
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
            $("evtSex").disabled = true;                                                                                                                                                                                                                                                                                                                      
            $("evtNationCode").disabled = true;                                                                                                                                                                                                                                                                                                               
            $("evtNationCodeOption").disabled = true;                                                                                                                                                                                                                                                                                                         
            $("commTyp").value="2";                                                                                                                                                                                                                                                                                                                           
            $("commTyp").readOnly = false;                                                                                                                                                                                                                                                                                                                    
            $("commZip").disabled = false;                                                                                                                                                                                                                                                                                                                    
            $("commAddr").disabled = false;                                                                                                                                                                                                                                                                                                                   
        }

        //眷屬國籍                                                                                                                                                                                                                                                                                                                              
        if('<c:out value="${DisabledAnnuityReceiptFamForm.famNationTyp}" />'!=""){                                                                                                                                                                                                                                                                                                 
            if('<c:out value="${DisabledAnnuityReceiptFamForm.famNationTyp}" />'=="1"){                                                                                                                                                                                                                                                                                            
                $("famSex").disabled = true;                                                                                                                                                                                                                                                                                                                  
                $("famNationCode").disabled = true;                                                                                                                                                                                                                                                                                                           
                $("famNationCodeOption").disabled = true;                                                                                                                                                                                                                                                                                                     
                $("famSex").value = "";                                                                                                                                                                                                                                                                                                                       
                $("famNationCode").value = "";                                                                                                                                                                                                                                                                                                                
                $("famNationCodeOption").value = "";                                                                                                                                                                                                                                                                                                          
            }else{                                                                                                                                                                                                                                                                                                                                            
                $("famSex").disabled = false;                                                                                                                                                                                                                                                                                                                 
                $("famNationCode").disabled = false;                                                                                                                                                                                                                                                                                                          
                $("famNationCodeOption").disabled = false;                                                                                                                                                                                                                                                                                                    
            }                                                                                                                                                                                                                                                                                                                                                 
        }else{     
            $("famNationTyp").value = "1";                                                                                                                                                                                                                                                                                                                                            
            $("famSex").disabled = true;                                                                                                                                                                                                                                                                                                                      
            $("famNationCode").disabled = true;                                                                                                                                                                                                                                                                                                               
            $("famNationCodeOption").disabled = true;                                                                                                                                                                                                                                                                                                         
        }
        //眷屬關係
        if('<c:out value="${DisabledAnnuityReceiptFamForm.famEvtRel}" />'!=""){                                                                                                                                                                                                                                                                                                 
            if('<c:out value="${DisabledAnnuityReceiptFamForm.famEvtRel}" />'=="2"){                                                                                                                                                                                                                                                                                                                        
                $("marryDate").disabled = false;                                                                                                                                                                                                                                                                                                                  
                $("marryDate").value = "";                                                                                                                                                                                                                                                                                                                        
                $("raiseChildMk").disabled = true;
                $("raiseChildMk").value = "";
                $("studMk").disabled = true;                                                                                                                                                                                                                                                                                                                   
                $("studMk").value = "";
                $("handIcapMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
                $("handIcapMk").value = "";     
                $("interDictMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
                $("interDictMk").value = "";                                                                                                                                                                                                                                                                                                                           
            }else if('<c:out value="${DisabledAnnuityReceiptFamForm.famEvtRel}" />'=="4"){                                                                                                                                                                                                                                                                                                                        
                $("marryDate").disabled = true;                                                                                                                                                                                                                                                                                                                  
                $("marryDate").value = "";                                                                                                                                                                                                                                                                                                                        
                $("raiseChildMk").disabled = false;
                $("raiseChildMk").value = "";
                $("studMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
                $("studMk").value = "";
                $("handIcapMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
                $("handIcapMk").value = "";     
                $("interDictMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
                $("interDictMk").value = "";                                                                                                                                                                                                                                                                                                                            
            }else if('<c:out value="${DisabledAnnuityReceiptFamForm.famEvtRel}" />'=="7"){                                                                                                                                                                                                                                                                                                                        
                $("marryDate").disabled = true;                                                                                                                                                                                                                                                                                                                  
                $("marryDate").value = "";                                                                                                                                                                                                                                                                                                                        
                $("raiseChildMk").disabled = true;
                $("raiseChildMk").value = "";
                $("studMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
                $("studMk").value = "";
                $("handIcapMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
                $("handIcapMk").value = "";     
                $("interDictMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
                $("interDictMk").value = "";                                                                                                                                                                                                                                                                                                                   
            }else if('<c:out value="${DisabledAnnuityReceiptFamForm.famEvtRel}" />'=="3"||'<c:out value="${DisabledAnnuityReceiptFamForm.famEvtRel}" />'=="5"){                                                                                                                                                                                                                                                                                                                                              
                $("marryDate").disabled = true;                                                                                                                                                                                                                                                                                                                  
                $("marryDate").value = "";                                                                                                                                                                                                                                                                                                                        
                $("raiseChildMk").disabled = true;
                $("raiseChildMk").value = "";
                $("studMk").disabled = true;                                                                                                                                                                                                                                                                                                                   
                $("studMk").value = "";
                $("handIcapMk").disabled = true;                                                                                                                                                                                                                                                                                                                   
                $("handIcapMk").value = "";     
                $("interDictMk").disabled = true;                                                                                                                                                                                                                                                                                                                   
                $("interDictMk").value = "";                                                                                                                                                                                                                                                                                             
            }else {                                                                                                                                                                                                                                                                                                                                              
                $("marryDate").disabled = true;                                                                                                                                                                                                                                                                                                                  
                $("marryDate").value = "";                                                                                                                                                                                                                                                                                                                        
                $("raiseChildMk").disabled = true;
                $("raiseChildMk").value = "";
                $("studMk").disabled = true;                                                                                                                                                                                                                                                                                                                   
                $("studMk").value = "";
                $("handIcapMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
                $("handIcapMk").value = "";     
                $("interDictMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
                $("interDictMk").value = "";                                                                                                                                                                                                                                                                                                                       
            }       
        }else{
            $("marryDate").disabled = true;                                                                                                                                                                                                                                                                                                                  
            $("raiseChildMk").disabled = true; 
            $("studMk").disabled = true;
            $("handIcapMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
            $("interDictMk").disabled = false;                                                                                                                                                                                                                                                                                                                   
        }
        if('<c:out value="${DisabledAnnuityReceiptFamForm.focusLocation}" />'=="famNationTyp"){
            $("famNationTyp").focus();
        }else{
            $("apNo1").focus();
        }
        changePayTyp();        

    }                                                                                                                                                                                                                                                                                                                                                         
                                                                                                                                                                                                                                                                                                                                                              
    <%-- 畫面清空重設 --%>                                                                                                                                                                                                                                                                                                                                    
    function resetForm(){                                                                                                                                                                                                                                                                                                                                     
        document.DisabledAnnuityReceiptForm.reset();                                                                                                                                                                                                                                                                                                          
        initAll();                                                                                                                                                                                                                                                                                                                                            
    }                                                                                                                                                                                                                                                                                                                                                         
                                                                                                                                                                                                                                                                                                                                                              
    function cleanEvtForm(){                                                                                                                                                                                                                                                                                                                                  
        cleanForm();                                                                                                                                                                                                                                                                                                                                          
        initAll();                                                                                                                                                                                                                                                                                                                                            
    }                                                                                                                                                                                                                                                                                                                                                         
                                                                                                                                                                                                                                                                                                                                                              
    function cleanFamData(){                                                                                                                                                                                                                                                                                                                                  
        $("seqNo").value = "";     
        $("famNationTyp").value = "1";     
        $("famSex").value = "";                                       
        $("famNationCode").value = "";
        $("famNationCodeOption").value = "";
        $("famName").value = "";                                       
        $("famAppDate").value = "";                                       
        $("famIdnNo").value = "";                                       
        $("famBrDate").value = "";
        $("famEvtRel").value = "";
        $("marryDate").value = "";                                       
        $("raiseChildMk").value = "";                                       
        $("studMk").value = "";                                       
        $("monIncomeMk").value = "";
        $("monIncome").value = "";            
        $("handIcapMk").value = "";                                       
        $("interDictMk").value = "";
        
        $("insertModeBtn").style.display="inline";  
        $("insertModeStr").style.display="inline";  
        $("updateModeBtn").style.display="none";
        $("updateModeStr").style.display="none";
        
        changeFamNationTyp();
        chgFamEvtRel();
        chgMonIncomeMk();                                                                                                                                                                                                                                                                                                                         
    }             
    
    //新增/修改眷屬資料保留已輸入之事故者資料
    function saveTempEvtData(){
        $("tempApNo1").value = $("apNo1").value;//受理編號-1
        $("tempApNo2").value = $("apNo2").value;//受理編號-2
        $("tempApNo3").value = $("apNo3").value;//受理編號-3
        $("tempApNo4").value = $("apNo4").value;//受理編號-4
        $("tempAppDate").value = $("appDate").value;//申請日期
        $("tempApUbno").value = $("apUbno").value;//申請單位保險證號
    
        //事故者個人資料                                                                          
        $("tempEvtNationTpe").value = $("evtNationTpe").value; //事故者國籍別                     
        $("tempEvtSex").value = $("evtSex").value; //性別                                         
        $("tempEvtNationCode").value = $("evtNationCode").value; //事故者國籍                     
        $("tempEvtNationCodeOption").value = $("evtNationCodeOption").value; //事故者國籍下拉選單 
        $("tempEvtName").value = $("evtName").value; //事故者姓名                                 
        $("tempCvldtlName").value = $("cvldtlName").value; //戶籍姓名                             
        $("tempEvtIdnNo").value = $("evtIdnNo").value; //事故者身分證號                           
        $("tempEvtBrDate").value = $("evtBrDate").value; //事故者出生日期                         
        $("tempTel1").value = $("tel1").value; //電話1                                            
        $("tempTel2").value = $("tel2").value; //電話2                                            
        $("tempCommTyp").value = $("commTyp").value; //通訊地址別                                 
        $("tempCommZip").value = $("commZip").value; //通訊郵遞區號                               
        $("tempCommAddr").value = $("commAddr").value; //通訊地址                                 
        $("tempGrdIdnNo").value = $("grdIdnNo").value; //法定代理人身分證號                       
        $("tempGrdName").value = $("grdName").value; //法定代理人姓名                             
        $("tempGrdBrDate").value = $("grdBrDate").value; //法定代理人出生日期
        $("tempMobilePhone").value = $("mobilePhone").value; // 手機複驗         
        $("tempEvtHandIcapMk").value = $("evtHandIcapMk").value; // 有無診斷書                     
        //$("tempDefaultGrdData").value = $("defaultGrdData").value; //法定代理人預設眷屬1
                                                                                                  
        //保險事故                                                                                
        $("tempEvTyp").value = $("evTyp").value; //傷病分類                                       
        $("tempEvtJobDate").value = $("evtJobDate").value; //診斷失能日期                         
        $("tempEvCode").value = $("evCode").value; //傷病原因                                     
        $("tempCriInPart1").value = $("criInPart1").value; //受傷部位                             
        $("tempCriInPart2").value = $("criInPart2").value; //受傷部位                             
        $("tempCriInPart3").value = $("criInPart3").value; //受傷部位                             
        $("tempCriMedium").value = $("criMedium").value; //媒介物                                 
        $("tempCriInJdp1").value = $("criInJdp1").value; //失能項目                               
        $("tempCriInJdp2").value = $("criInJdp2").value; //失能項目                               
        $("tempCriInJdp3").value = $("criInJdp3").value; //失能項目                               
        $("tempCriInJdp4").value = $("criInJdp4").value; //失能項目                               
        $("tempCriInJdp5").value = $("criInJdp5").value; //失能項目                               
        $("tempCriInJdp6").value = $("criInJdp6").value; //失能項目                               
        $("tempCriInJdp7").value = $("criInJdp7").value; //失能項目                               
        $("tempCriInJdp8").value = $("criInJdp8").value; //失能項目                               
        $("tempCriInJdp9").value = $("criInJdp9").value; //失能項目                               
        $("tempCriInJdp10").value = $("criInJdp10").value; //失能項目                             
        $("tempHosId").value = $("hosId").value; //醫療院所代碼                                   
        $("tempDoctorName1").value = $("doctorName1").value; //醫師姓名                           
        $("tempDoctorName2").value = $("doctorName2").value; //醫師姓名                           
        $("tempCriInJnme1").value = $("criInJnme1").value; //國際疾病代碼                         
        $("tempCriInJnme2").value = $("criInJnme2").value; //國際疾病代碼                         
        $("tempCriInJnme3").value = $("criInJnme3").value; //國際疾病代碼                         
        $("tempCriInJnme4").value = $("criInJnme4").value; //國際疾病代碼                         
                                                                                                  
        //給付資料                                                                                
        $("tempPayTyp").value = $("payTyp").value; //給付方式                                     
        $("tempPayBankIdBranchId").value = $("payBankIdBranchId").value; //帳號(前)               
        $("tempPayEeacc").value = $("payEeacc").value; //帳號(後) 複驗   
        $("tempChkPayBankIdChkBranchId").value = $("chkPayBankIdChkBranchId").value; //帳號(前)               
        $("tempChkPayEeacc").value = $("chkPayEeacc").value; //帳號(後) 複驗       
        $("tempPayBankId").value = $("payBankId").value; //帳號(前)
        $("tempBranchId").value = $("branchId").value; //帳號(前)   
        $("tempChkPayBankId").value = $("chkPayBankId").value; //帳號(前)  
        $("tempChkBranchId").value = $("chkBranchId").value; //帳號(前)                         
    }        
    
    //將第一筆遺屬資料填入為預設代理人資料
    function setDefaultGrdData(){
        var listSize = '<%=((List)request.getSession().getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_FAM_DATA_LIST)).size()%>';
        if(Trim(asc($("defaultGrdData").value)).toUpperCase() == "Y" && listSize>0){
            $("grdName").value = '<c:out value="${DisabledAnnuityReceiptFamDataList[0].famName}" />';
            $("grdIdnNo").value = '<c:out value="${DisabledAnnuityReceiptFamDataList[0].famIdnNo}" />';
            $("grdBrDate").value = '<c:out value="${DisabledAnnuityReceiptFamDataList[0].famBrDateStr}" />';
        }
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
            $("evtHandIcapMk").disabled = true;
            $("evtHandIcapMk").value = "";
        }
    } 
    
    // 檢查手機複驗
    function checkMobilePhone(){
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
            
            if(Trim($("mobilePhone").value)=="" || ($("mobilePhone").value).length != 10 || !((shortTel1=="09" && $("mobilePhone").value==$("tel1").value)||(shortTel2=="09" && $("mobilePhone").value==$("tel2").value))){
                $("evtHandIcapMk").disabled = true;
                $("evtHandIcapMk").value = "";
                //alert("手機號碼輸入有誤，請檢查。\r\n");
                //$("mobilePhone").focus();
            }else{
                $("evtHandIcapMk").disabled = false;
            }
        } 
    }
    
    function chgMobilePhoneFocus(){
        if(event.keyCode==9){
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
            
            if(Trim($("mobilePhone").value)==""||($("mobilePhone").value).length != 10 ||!((shortTel1=="09" && $("mobilePhone").value==$("tel1").value)||(shortTel2=="09" && $("mobilePhone").value==$("tel2").value))){
                $("evtHandIcapMk").disabled = true;
                $("evtHandIcapMk").value = "";
                //alert("手機號碼輸入有誤，請檢查。\r\n");
                
                $("evtHandIcapMk").disabled = true;
                $("evtHandIcapMk").value = "";
                //$("defaultGrdData").focus();
                $("grdName").focus();
            }else{
                $("evtHandIcapMk").disabled = false;
                $("evtHandIcapMk").focus();
            }
        }
    }
 	// Added by JohnsonHuang 20200115 [Begin]
    //外國人身分證號碼自動帶入(個人)
    function autoForeignEvtSex(){
    	var secondText = $("evtIdnNo").value.substring(1,2);
		if($("evtIdnNo").value.length==10){
    	if($("evtNationTpe").value=="2" && Trim($("evtSex").value)==""){
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
	
    //外國人身分證號碼自動帶入(眷屬)
    function autoForeignFamSex(){
    	var secondText = $("famIdnNo").value.substring(1,2);
		if($("famIdnNo").value.length==10){
    	if($("famNationTyp").value=="2" && Trim($("famSex").value)==""){
    		if(secondText=="A" || secondText=="a" || secondText=="C" || secondText=="c" || secondText=="8"){
    			$("famSex").value = "1";	
    		}else if(secondText=="B" || secondText=="b" || secondText=="D" || secondText=="d" || secondText=="9"){
    			$("famSex").value = "2";	
    		}else{
    			$("famSex").value = "";
    			alert('眷屬資料「不符合外來人口統一證號編碼原則，請輸入正確身分證」');
    		}
    	}else{
    		if($("famNationTyp").value=="2" && $("famSex").value == "1"){
     			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
     				alert('眷屬資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
        		}
     		}else if($("famNationTyp").value=="2" && $("famSex").value == "2"){
     			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
     				alert('眷屬資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
        		}
     		}
    	}
		}
    }     
 	// Added by JohnsonHuang 20200115 [End]
    Event.observe(window, 'load', initAll, false);
    Event.stopObserving(window, 'load', inputFieldFocus); 
    </script>
</head>                                                                                                                                                                                                                                                                                                                                                       
                                                                                                                                                                                                                                                                                                                                                              
<body scroll="no">                                                                                                                                                                                                                                                                                                                                            
<div id="mainContent">                                                                                                                                                                                                                                                                                                                                        
                                                                                                                                                                                                                                                                                                                                                              
    <%@ include file="/includes/ba_header.jsp"%>                                                                                                                                                                                                                                                                                                              
                                                                                                                                                                                                                                                                                                                                                              
    <%@ include file="/includes/ba_menu.jsp"%>                                                                                                                                                                                                                                                                                                                
                                                                                                                                                                                                                                                                                                                                                              
    <div id="main" class="mainBody">                                                                                                                                                                                                                                                                                                                          
        <fieldset>                                                                                                                                                                                                                                                                                                                                            
            <legend>&nbsp;失能年金給付受理作業&nbsp;</legend>                                                                                                                                                                                                                                                                                                 
                                                                                                                                                                                                                                                                                                                                                              
            <div align="right" id="showtime">                                                                                                                                                                                                                                                                                                                 
                    網頁下載時間：民國&nbsp;<func:nowDateTime />                                                                                                                                                                                                                                                                                  
            </div>                                                                                                                                                                                                                                                                                                                                            
                                                                                                                                                                                                                                                                                                                                                              
            <bean:define id="bafamilytempId" name="<%=ConstantKey.DISABLED_ANNUITY_RECEIPT_BAFAMILYTEMPID%>" />       
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">                                                                                                                                                                                                                                                        
                <html:form action="/disabledAnnuityReceiptInsert" method="post" onsubmit="return validateDisabledAnnuityReceiptForm(this);">                                                                                                                                                                                                                          
                <html:hidden styleId="page" property="page" value="2" />                                                                                                                                                                                                                                                                                              
                <html:hidden styleId="method" property="method" value="" />
                <html:hidden styleId="bafamilytempId" property="bafamilytempId" value="<%=CaseSessionHelper.getDisabledAnnuityReceiptBafamilytempIdStr(request) %>"/>
                <html:hidden styleId="actionTyp" property="actionTyp" value="insertMode"/>
                <html:hidden styleId="sFlag36" property="sFlag36"/>
                                                                                                                                                                                                                                                                                                                                                                      
                <tr>                                                                                                                                                                                                                                                                                                                                          
                    <td colspan="2" align="right">                                                                                                                                                                                                                                                                                                            
                        <acl:checkButton buttonName="btnConfirm">                                                                                                                                                                                                                                                                                             
                            <input tabindex="700" type="button" name="btnConfirm" class="button" value="確　認" onclick="$('page').value='2'; $('method').value='doInsert36Data'; if (document.DisabledAnnuityReceiptForm.onsubmit() && checkFields() && isValidEvtDateTrue()){document.DisabledAnnuityReceiptForm.submit();}else{return false;}" />&nbsp;                                  
                        </acl:checkButton>                                                                                                                                                                                                                                                                                                                    
                        <acl:checkButton buttonName="btnClear">                                                                                                                                                                                                                                                                                               
                            <input tabindex="710" type="button" name="btnClear" class="button" value="清　除" onclick="cleanEvtForm();"/>&nbsp;                                                                                                                                                                                                               
                        </acl:checkButton>                                                                                                                                                                                                                                                                                                                    
                        <acl:checkButton buttonName="btnBack">                                                                                                                                                                                                                                                                                                
                            <input tabindex="720" type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='2'; $('method').value='doBack'; document.DisabledAnnuityReceiptForm.submit();"/>                                                                                                                                       
                        </acl:checkButton>                                                                                                                                                                                                                                                                                                                    
                    </td>                                                                                                                                                                                                                                                                                                                                     
                </tr>                                                                                                                                                                                                                                                                                                                                         
                <tr>                                                                                                                                                                                                                                                                                                                                          
                    <td colspan="8">                                                                                                                                                                                                                                                                                                                          
                        <table width="100%" border="0" class="px13">                                                                                                                                                                                                                                                                                          
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td>                                                                                                                                                                                                                                                                                                                          
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">受理編號：</span>                                                                                                                                                                                                                                                                         
                                    <html:text tabindex="2" property="apNo1" styleId="apNo1" styleClass="disabled" readonly="true" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'))"/>                                                                                                                                                          
                                    &nbsp;-                                                                                                                                                                                                                                                                                                                   
                                    <html:text tabindex="4" property="apNo2" styleId="apNo2" styleClass="disabled" readonly="true" size="1" maxlength="1" onblur="this.value=asc(this.value);" onkeyup="autotab($('apNo2'), $('apNo3'))"/>                                                                                                                                                                        
                                    &nbsp;-                                                                                                                                                                                                                                                                                                                   
                                    <html:text tabindex="6" property="apNo3" styleId="apNo3" styleClass="disabled" readonly="true" size="5" maxlength="5" onblur="this.value=asc(this.value);" onkeyup="autotab($('apNo3'), $('apNo4'))"/>                                                                                                                                                                        
                                    &nbsp;-                                                                                                                                                                                                                                                                                                                   
                                    <html:text tabindex="8" property="apNo4" styleId="apNo4" styleClass="disabled" readonly="true" size="5" maxlength="5" onblur="this.value=asc(this.value);" />                                                                                                                                                                        
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td>                                                                                                                                                                                                                                                                                                                          
                                    <span class="issuetitle_L_down">申請單位保險證號：</span>                                                                                                                                                                                                                                                                 
                                    <html:text tabindex="10" property="apUbno" styleId="apUbno" styleClass="disabled" readonly="true" size="9" maxlength="9" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                            
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td>                                                                                                                                                                                                                                                                                                                          
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_R_down">申請日期：</span>                                                                                                                                                                                                                                                                         
                                    <html:text tabindex="20" property="appDate" styleId="appDate" styleClass="disabled" readonly="true" size="7" maxlength="7" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                          
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                        </table>                                                                                                                                                                                                                                                                                                                              
                    </td>                                                                                                                                                                                                                                                                                                                                     
                </tr>                                                                                                                                                                                                                                                                                                                                         
                <tr>                                                                                                                                                                                                                                                                                                                                          
                    <td align="center" class="table1" colspan="8">                                                                                                                                                                                                                                                                                            
                        <table width="98%" cellpadding="3" cellspacing="4" class="px13">                                                                                                                                                                                                                                                                      
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td colspan="2" class="issuetitle_L">                                                                                                                                                                                                                                                                                         
                                    <span class="systemMsg">▲</span>&nbsp;個人資料                                                                                                                                                                                                                                                                           
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss" colspan="2">                                                                                                                                                                                                                                                                                                     
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">國籍別：</span>                                                                                                                                                                                                                                                                           
                                    <html:text tabindex="30" property="evtNationTpe" styleId="evtNationTpe" styleClass="disabled" readonly="true" size="1" maxlength="1" onkeyup="changeEvtNationTpe();" onkeypress="chgEvtNationTpeFocus();" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                           
                                    <span class="formtxt">(1-本國，2-外籍)</span>                                                                                                                                                                                                                                                                             
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss" width="50%">　 <span class="issuetitle_L_down">性　別：</span>                                                                                                                                                                                                                                                   
                                    <html:text tabindex="40" property="evtSex" styleId="evtSex" styleClass="disabled" readonly="true" size="1" maxlength="1" onblur="this.value=asc(this.value);"/>                                                                                                                                                                          
                                    <span class="formtxt">(外籍者請填寫，1-男，2-女)</span>                                                                                                                                                                                                                                                                   
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td id="iss" width="50%">　 <span class="issuetitle_L_down">國　籍：</span>                                                                                                                                                                                                                                                   
                                    <html:text tabindex="50" property="evtNationCode" styleId="evtNationCode" styleClass="disabled" readonly="true" size="3" maxlength="3" onblur="this.value=asc(this.value);"/>                                                                                                                                                            
                                    <label>                                                                                                                                                                                                                                                                                                                   
                                        <html:select tabindex="60" property="evtNationCodeOption" onchange="$('evtNationCode').value=$('evtNationCodeOption').value;">                                                                                                                                                                                        
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
                                    <html:text tabindex="70" property="evtName" styleId="evtName" styleClass="disabled" readonly="true" size="50" maxlength="50" onblur="this.value=asc(this.value);"/>                                                                                                                                                                      
                                    <span class="formtxt">                                                                                                                                                                                                                                                                                                    
                                        (&nbsp;戶籍姓名：<html:text property="cvldtlName" styleId="cvldtlName" styleClass="disabled" readonly="true" disabled="true" size="50"/>&nbsp;)                                                                                                                                                                                      
                                    </span>                                                                                                                                                                                                                                                                                                                   
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss">                                                                                                                                                                                                                                                                                                                 
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">事故者身分證號：</span>                                                                                                                                                                                                                                                                   
                                    <html:text tabindex="80" property="evtIdnNo" styleId="evtIdnNo" styleClass="disabled" readonly="true"  size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();initCvldtlName();autoForeignEvtSex();" />                                                                                                                                    
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td id="iss">                                                                                                                                                                                                                                                                                                                 
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">事故者出生日期：</span>                                                                                                                                                                                                                                                                   
                                    <html:text tabindex="90" property="evtBrDate" styleId="evtBrDate" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value);initCvldtlName();"/>                                                                                                                                                   
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss">　 <span class="issuetitle_L_down">電　話1：</span>                                                                                                                                                                                                                                                              
                                    <html:text tabindex="100" property="tel1" styleId="tel1" styleClass="disabled" readonly="true" size="20" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                             
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td id="iss">　 <span class="issuetitle_L_down">電　話2：</span>                                                                                                                                                                                                                                                              
                                    <html:text tabindex="110" property="tel2" styleId="tel2" styleClass="disabled" readonly="true" size="20" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                             
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td colspan="2" id="iss">                                                                                                                                                                                                                                                                                                     
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">地　址：</span>                                                                                                                                                                                                                                                                           
                                    <html:text tabindex="120" property="commTyp" styleId="commTyp" styleClass="disabled" readonly="true" size="1" maxlength="1" onkeyup="changeCommTyp();" onkeypress="chgCommTypFocus();" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                              
                                    <span class="formtxt">(1-同戶籍地，2-現住址)
                                              現住址：<html:text tabindex="130" property="commZip" styleId="commZip" styleClass="disabled" readonly="true" size="6" maxlength="6" onblur="this.value=asc(this.value);"/>                                                                                                   
                                    (郵遞區號)&nbsp;-<html:text tabindex="140" property="commAddr" styleId="commAddr" styleClass="disabled" readonly="true" size="72" maxlength="240" onblur="this.value=asc(this.value);"/></span>                                                                                                                                          
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">　手機複驗：</span>
                                    <html:text tabindex="142" property="mobilePhone" styleId="mobilePhone" styleClass="disabled" readonly="true"  size="10" maxlength="10" onkeyup="this.value=asc(this.value); checkMobilePhone();" onkeypress="chgMobilePhoneFocus();" onblur="checkMobilePhone();"/>
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">　有無診斷書：</span>
                                    <html:text tabindex="144" property="evtHandIcapMk" styleId="evtHandIcapMk" styleClass="disabled" readonly="true"  size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();"/>
                                    <span class="formtxt">(Y-有，N-無)</span>
                                </td>
                            </tr>
                            <%--
                            <tr>
                                <td colspan="2" id="iss">
                                    <span class="issuetitle_L_down">　法定代理人預設遺屬序1：</span>
                                    <html:text tabindex="146" property="defaultGrdData" styleId="defaultGrdData" styleClass="textinput"  size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase(); setDefaultGrdData();"/>
                                </td>
                            </tr>
                            --%>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss" colspan="2">　 <span class="issuetitle_L_down">法定代理人姓名：</span>                                                                                                                                                                                                                                           
                                    <html:text tabindex="150" property="grdName" styleId="grdName" styleClass="disabled" readonly="true" size="20" maxlength="50" onblur="this.value=asc(this.value);"/>                                                                                                                                                                     
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss">　 <span class="issuetitle_L_down">法定代理人身分證號：</span>                                                                                                                                                                                                                                                   
                                    <html:text tabindex="160" property="grdIdnNo" styleId="grdIdnNo" styleClass="disabled" readonly="true" size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                     
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td id="iss">　 <span class="issuetitle_L_down">法定代理人出生日期：</span>                                                                                                                                                                                                                                                   
                                    <html:text tabindex="170" property="grdBrDate" styleId="grdBrDate" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value);"/>                                                                                                                                                                   
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td colspan="2" class="issuetitle_L"><br>                                                                                                                                                                                                                                                                                     
                                    <span class="systemMsg">▲</span>&nbsp;保險事故                                                                                                                                                                                                                                                                           
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss" width="50%">                                                                                                                                                                                                                                                                                                     
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">傷病分類：</span>                                                                                                                                                                                                                                                                         
                                    <html:text tabindex="180" property="evTyp" styleId="evTyp" styleClass="disabled" readonly="true" size="1" maxlength="1" onblur="this.value=asc(this.value);"/>                                                                                                                                                                           
                                    <span class="formtxt">(1-職業傷害，2-職業病，3-普通傷害，4-普通疾病)</span>                                                                                                                                                                                                                                               
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td id="iss" width="50%">　 <span class="issuetitle_L_down">診斷失能日期：</span>                                                                                                                                                                                                                                             
                                    <html:text tabindex="190" property="evtJobDate" styleId="evtJobDate" styleClass="disabled" readonly="true" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>                                                                                                                                                                
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss">　 <span class="issuetitle_L_down">傷病原因：</span>                                                                                                                                                                                                                                                             
                                    <html:text tabindex="200" property="evCode" styleId="evCode" styleClass="disabled" readonly="true" size="2" maxlength="2" onblur="this.value=asc(this.value);"/>                                                                                                                                                                         
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td id="iss">　 <span class="issuetitle_L_down">受傷部位：</span>                                                                                                                                                                                                                                                             
                                    <html:text tabindex="210" property="criInPart1" styleId="criInPart1" styleClass="disabled" readonly="true" size="3" maxlength="3" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                             
                                    <html:text tabindex="220" property="criInPart2" styleId="criInPart2" styleClass="disabled" readonly="true" size="3" maxlength="3" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                             
                                    <html:text tabindex="230" property="criInPart3" styleId="criInPart3" styleClass="disabled" readonly="true" size="3" maxlength="3" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                   
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss" colspan="2">　 <span class="issuetitle_L_down">媒 介 物：</span>                                                                                                                                                                                                                                                 
                                    <html:text tabindex="240" property="criMedium" styleId="criMedium" styleClass="disabled" readonly="true" size="3" maxlength="3" onblur="this.value=asc(this.value);"/>                                                                                                                                                                   
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td colspan="2" id="iss">　 <span class="issuetitle_L_down">失能項目：</span>                                                                                                                                                                                                                                                 
                                    <html:text tabindex="250" property="criInJdp1" styleId="criInJdp1" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                               
                                    <html:text tabindex="260" property="criInJdp2" styleId="criInJdp2" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                               
                                    <html:text tabindex="270" property="criInJdp3" styleId="criInJdp3" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                               
                                    <html:text tabindex="280" property="criInJdp4" styleId="criInJdp4" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                               
                                    <html:text tabindex="290" property="criInJdp5" styleId="criInJdp5" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                               
                                    <html:text tabindex="300" property="criInJdp6" styleId="criInJdp6" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                               
                                    <html:text tabindex="310" property="criInJdp7" styleId="criInJdp7" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                               
                                    <html:text tabindex="320" property="criInJdp8" styleId="criInJdp8" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                               
                                    <html:text tabindex="330" property="criInJdp9" styleId="criInJdp9" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                               
                                    <html:text tabindex="340" property="criInJdp10" styleId="criInJdp10" styleClass="disabled" readonly="true" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                   
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss">　 <span class="issuetitle_L_down">醫療院所代碼：</span>                                                                                                                                                                                                                                                         
                                    <html:text tabindex="350" property="hosId" styleId="hosId" styleClass="disabled" readonly="true" size="10" maxlength="10" onblur="this.value=asc(this.value).toUpperCase(); if(Trim(this.value)!=''){this.value=leftPad(this.value, 10, '0');}"/>                                                                                                                                                           
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td id="iss">　 <span class="issuetitle_L_down">醫師姓名：</span>                                                                                                                                                                                                                                                             
                                    <html:text tabindex="360" property="doctorName1" styleId="doctorName1" styleClass="disabled" readonly="true" size="12" maxlength="12" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                                                                           
                                    <html:text tabindex="370" property="doctorName2" styleId="doctorName2" styleClass="disabled" readonly="true" size="12" maxlength="12" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                                                                 
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss" colspan="2">　 <span class="issuetitle_L_down">國際疾病代碼：</span>                                                                                                                                                                                                                                             
                                    <html:text tabindex="380" property="criInJnme1" styleId="criInJnme1" styleClass="disabled" readonly="true" size="30" maxlength="30" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                             
                                    <html:text tabindex="390" property="criInJnme2" styleId="criInJnme2" styleClass="disabled" readonly="true" size="30" maxlength="30" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                             
                                    <html:text tabindex="400" property="criInJnme3" styleId="criInJnme3" styleClass="disabled" readonly="true" size="30" maxlength="30" onblur="this.value=asc(this.value).toUpperCase();"/>&nbsp;                                                                                                                                             
                                    <html:text tabindex="410" property="criInJnme4" styleId="criInJnme4" styleClass="disabled" readonly="true" size="30" maxlength="30" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                   
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td colspan="2" class="issuetitle_L"><br>                                                                                                                                                                                                                                                                                     
                                    <span class="systemMsg">▲</span>&nbsp;給付資料                                                                                                                                                                                                                                                                           
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss" colspan="2">                                                                                                                                                                                                                                                                                                     
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">給付方式：</span>                                                                                                                                                                                                                                                                         
                                    <html:text tabindex="420" property="payTyp" styleId="payTyp" styleClass="disabled" readonly="true" size="1" maxlength="1" onkeyup="changePayTyp();" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                 
                                    <span class="formtxt">(1-匯入銀行帳戶，2-匯入郵局帳號，A-扣押戶)</span>                                                                                                                                                                                                                                           
                                </td>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
                            </tr>    
                            <tr> 
                            <td id="iss">　 
                                    <span class="issuetitle_L_down">帳號：</span>                                                                                                                                                                                                                                                   
                                    <html:text tabindex="430" property="payBankId" styleId="payBankId" styleClass="textinput" size="1" maxlength="3" onblur="this.value=asc(this.value);"/>&nbsp;-
                                    <html:text tabindex="435" property="branchId" styleId="branchId" styleClass="textinput" size="1" maxlength="4" onblur="this.value=asc(this.value);"/>&nbsp;-
                                    <html:text tabindex="440" property="payEeacc" styleId="payEeacc" styleClass="textinput" size="14" maxlength="14" onblur="this.value=asc(this.value);"/>
                                    <html:hidden styleId="payBankIdBranchId" property="payBankIdBranchId"/>                                                                                                                                                                                
                                 </td>
                                 <td id="iss">   
                                    <span class="issuetitle_L_down">　帳號複驗：</span>
                                    <html:text tabindex="450" property="chkPayBankId" styleId="chkPayBankId" styleClass="textinput" size="1" maxlength="3" onblur="this.value=asc(this.value);"/>&nbsp;-
                                    <html:text tabindex="455" property="chkBranchId" styleId="chkBranchId" styleClass="textinput" size="1" maxlength="4" onblur="this.value=asc(this.value);"/>&nbsp;-
                                    <html:text tabindex="460" property="chkPayEeacc" styleId="chkPayEeacc" styleClass="textinput" size="14" maxlength="14" onblur="this.value=asc(this.value);"/>
                                    <html:hidden styleId="chkPayBankIdChkBranchId" property="chkPayBankIdChkBranchId"/>
                                </td>    
                            </tr>                                                                                                                                                                                                                                                                                                                         
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td></td>                                                                                                                                                                                                                                                                                                                     
                            </tr>                                                                                                                                                                                                                                                                                                                             
                        </table>                                                                                                                                                                                                                                                                                                                              
                    </td>                                                                                                                                                                                                                                                                                                                                     
                </tr>
            </html:form>
            <html:form action="/disabledAnnuityReceiptInsertFam" method="post" onsubmit="return validateDisabledAnnuityReceiptFamForm(this);">                                                                                                                                                                                                                          
                <html:hidden styleId="famPage" property="page" value="1" />                                                                                                                                                                                                                                                                                              
                <html:hidden styleId="famMethod" property="method" value="" />
                <html:hidden styleId="famBafamilytempId" property="bafamilytempId" value="<%=CaseSessionHelper.getDisabledAnnuityReceiptBafamilytempIdStr(request) %>"/>
                <html:hidden styleId="famActionTyp" property="actionTyp" value="insertMode"/>
                <html:hidden styleId="famOptionMode" property="famOptionMode" value=""/>
                <html:hidden styleId="seqNo" property="seqNo" />
                <html:hidden styleId="dataIdx" property="dataIdx"/>
                <%--for 暫存以輸入之事故者資料 [--%>
                <html:hidden styleId="tempApNo1" property="tempApNo1" value=""/>
                <html:hidden styleId="tempApNo2" property="tempApNo2" value=""/>
                <html:hidden styleId="tempApNo3" property="tempApNo3" value=""/>
                <html:hidden styleId="tempApNo4" property="tempApNo4" value=""/>
                <html:hidden styleId="tempAppDate" property="tempAppDate" value=""/>
                <html:hidden styleId="tempApUbno" property="tempApUbno" value=""/>
                <html:hidden styleId="tempEvtNationTpe" property="tempEvtNationTpe" value=""/>
                <html:hidden styleId="tempEvtSex" property="tempEvtSex" value=""/>
                <html:hidden styleId="tempEvtNationCode" property="tempEvtNationCode" value=""/>
                <html:hidden styleId="tempEvtNationCodeOption" property="tempEvtNationCodeOption" value=""/>
                <html:hidden styleId="tempEvtName" property="tempEvtName" value=""/>
                <html:hidden styleId="tempCvldtlName" property="tempCvldtlName" value=""/>
                <html:hidden styleId="tempEvtIdnNo" property="tempEvtIdnNo" value=""/>
                <html:hidden styleId="tempEvtBrDate" property="tempEvtBrDate" value=""/>
                <html:hidden styleId="tempTel1" property="tempTel1" value=""/>
                <html:hidden styleId="tempTel2" property="tempTel2" value=""/>
                <html:hidden styleId="tempCommTyp" property="tempCommTyp" value=""/>
                <html:hidden styleId="tempCommZip" property="tempCommZip" value=""/>
                <html:hidden styleId="tempCommAddr" property="tempCommAddr" value=""/>
                <html:hidden styleId="tempGrdIdnNo" property="tempGrdIdnNo" value=""/>
                <html:hidden styleId="tempGrdName" property="tempGrdName" value=""/>
                <html:hidden styleId="tempGrdBrDate" property="tempGrdBrDate" value=""/>
                <html:hidden styleId="tempEvTyp" property="tempEvTyp" value=""/>
                <html:hidden styleId="tempEvtJobDate" property="tempEvtJobDate" value=""/>
                <html:hidden styleId="tempEvCode" property="tempEvCode" value=""/>
                <html:hidden styleId="tempCriInPart1" property="tempCriInPart1" value=""/>
                <html:hidden styleId="tempCriInPart2" property="tempCriInPart2" value=""/>
                <html:hidden styleId="tempCriInPart3" property="tempCriInPart3" value=""/>
                <html:hidden styleId="tempCriMedium" property="tempCriMedium" value=""/>
                <html:hidden styleId="tempCriInJdp1" property="tempCriInJdp1" value=""/>
                <html:hidden styleId="tempCriInJdp2" property="tempCriInJdp2" value=""/>
                <html:hidden styleId="tempCriInJdp3" property="tempCriInJdp3" value=""/>
                <html:hidden styleId="tempCriInJdp4" property="tempCriInJdp4" value=""/>
                <html:hidden styleId="tempCriInJdp5" property="tempCriInJdp5" value=""/>
                <html:hidden styleId="tempCriInJdp6" property="tempCriInJdp6" value=""/>
                <html:hidden styleId="tempCriInJdp7" property="tempCriInJdp7" value=""/>
                <html:hidden styleId="tempCriInJdp8" property="tempCriInJdp8" value=""/>
                <html:hidden styleId="tempCriInJdp9" property="tempCriInJdp9" value=""/>
                <html:hidden styleId="tempCriInJdp10" property="tempCriInJdp10" value=""/>
                <html:hidden styleId="tempHosId" property="tempHosId" value=""/>
                <html:hidden styleId="tempDoctorName1" property="tempDoctorName1" value=""/>
                <html:hidden styleId="tempDoctorName2" property="tempDoctorName2" value=""/>
                <html:hidden styleId="tempCriInJnme1" property="tempCriInJnme1" value=""/>
                <html:hidden styleId="tempCriInJnme2" property="tempCriInJnme2" value=""/>
                <html:hidden styleId="tempCriInJnme3" property="tempCriInJnme3" value=""/>
                <html:hidden styleId="tempCriInJnme4" property="tempCriInJnme4" value=""/>
                <html:hidden styleId="tempPayTyp" property="tempPayTyp" value=""/>
                <html:hidden styleId="tempPayBankIdBranchId" property="tempPayBankIdBranchId" value=""/>
                <html:hidden styleId="tempPayEeacc" property="tempPayEeacc" value=""/>
                <html:hidden styleId="tempChkPayBankIdChkBranchId" property="tempChkPayBankIdChkBranchId" value=""/>
                <html:hidden styleId="tempChkPayEeacc" property="tempChkPayEeacc" value=""/>
                <html:hidden styleId="tempMobilePhone" property="tempMobilePhone" value=""/>
                <html:hidden styleId="tempEvtHandIcapMk" property="tempEvtHandIcapMk" value=""/>
                <html:hidden styleId="tempDefaultGrdData" property="tempDefaultGrdData" value=""/>
                <html:hidden styleId="tempPayBankId" property="tempPayBankId" value=""/>
                <html:hidden styleId="tempBranchId" property="tempBranchId" value=""/>
                <html:hidden styleId="tempChkPayBankId" property="tempChkPayBankId" value=""/>
                <html:hidden styleId="tempChkBranchId" property="tempChkBranchId" value=""/>
                <%-- ]for 暫存以輸入之事故者資料 --%>
                <tr>                                                                                                                                                                                                                                                                                                                                          
                    <td align="center" class="table1" colspan="8">                                                                                                                                                                                                                                                                                            
                        <table width="98%" cellpadding="3" cellspacing="4" class="px13">                                                                                                                                                                                                                                                                      
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td colspan="2" class="issuetitle_L">                                                                                                                                                                                                                                                                                         
                                    <table width="100%" class="px13">                                                                                                                                                                                                                                                                                         
                                        <tr>
                                            <td align="left" style=" font:bold 0.95em/1.5em;color: #333333;line-height: 2em;">
                                                <span class="systemMsg">▲</span>&nbsp;眷屬資料
                                                <font color="red">
                                                    <div id="insertModeStr" style="display:inline">(新增模式)</div>
                                                    <div id="updateModeStr" style="display:none">(修改模式　眷屬資料序號：<span id="dataIndex"></span>)</div>
                                                </font>
                                            </td>
                                            <td align="right">
                                                <acl:checkButton buttonName="btnInsertFam">                                                                                                                                                                                                                                                                                           
                                                    <div id="insertModeBtn" style="display:inline">
                                                        <input type="button" tabindex="670" name="btnInsertFam" class="button" value="新增眷屬" onclick="$('famPage').value='1'; $('famMethod').value='doInsertFamData'; if (document.DisabledAnnuityReceiptFamForm.onsubmit() && checkFamFields() && isValidFamDateTrue()){saveTempEvtData(); document.DisabledAnnuityReceiptFamForm.submit();}else{return false;}" />&nbsp;
                                                    </div>
                                                </acl:checkButton>
                                                <acl:checkButton buttonName="btnUpdateFam">                                                                                                                                                                                                                                                                                           
                                                    <div id="updateModeBtn" style="display:none">
                                                        <input type="button" tabindex="680" name="btnUpdateFam" class="button" value="修改眷屬" onclick="$('famPage').value='1'; $('famMethod').value='doUpdateFamData'; if (document.DisabledAnnuityReceiptFamForm.onsubmit() && checkFamFields() && isValidFamDateTrue()){saveTempEvtData(); document.DisabledAnnuityReceiptFamForm.submit();}else{return false;}" />&nbsp;&nbsp;
                                                    </div>                                                                                                                                                                                                                                                                                                                    
                                                </acl:checkButton>
                                                <acl:checkButton buttonName="btnClearFam">                                                                                                                                                                                                                                                                                            
                                                    <input type="button" tabindex="690" name="btnClearFam" class="button" value="清除眷屬" onclick="cleanFamData();$('famOptionMode').value='insertMode';"/>&nbsp;                                                                                                                                                                                                                         
                                                </acl:checkButton>   
                                            </td>
                                        </tr>
                                    </table>                                                                                                                                                                                                                                                                           
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td colspan="2" id="iss">                                                                                                                                                                                                                                                                                                     
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">國籍別：</span>                                                                                                                                                                                                                                                                           
                                    <html:text tabindex="510" property="famNationTyp" styleId="famNationTyp" styleClass="textinput" size="1" maxlength="1" onkeyup="changeFamNationTyp();" onkeypress="chgFamNationTypFocus();" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                          
                                    <span class="formtxt">(1-本國，2-外籍)</span>                                                                                                                                                                                                                                                                             
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss" width="50%">　 <span class="issuetitle_L_down">性　別：</span>                                                                                                                                                                                                                                                   
                                    <html:text tabindex="520" property="famSex" styleId="famSex" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                           
                                    <span class="formtxt">(外籍者請填寫，1-男，2-女)</span>                                                                                                                                                                                                                                                                   
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td id="iss" width="50%">　 <span class="issuetitle_L_down">國　籍：</span>                                                                                                                                                                                                                                                   
                                    <html:text tabindex="530" property="famNationCode" styleId="famNationCode" styleClass="textinput" size="3" maxlength="3" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                             
                                    <label>                                                                                                                                                                                                                                                                                                                   
                                        <html:select tabindex="540" property="famNationCodeOption" onchange="$('famNationCode').value=$('famNationCodeOption').value;">                                                                                                                                                                                       
                                            <html:option value="">請選擇</html:option>                                                                                                                                                                                                                                                                        
                                            <html:options collection="<%=ConstantKey.COUNTRYID_OPTION_LIST%>" property="countryId" labelProperty="cname" />                                                                                                                                                                                                   
                                        </html:select>                                                                                                                                                                                                                                                                                                        
                                    </label>                                                                                                                                                                                                                                                                                                                  
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss">                                                                                                                                                                                                                                                                                                                 
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">眷屬姓名：</span>                                                                                                                                                                                                                                                                         
                                    <html:text tabindex="550" property="famName" styleId="famName" styleClass="textinput" size="50" maxlength="50" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                       
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td id="iss">                                                                                                                                                                                                                                                                                                                 
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">眷屬申請日期：</span>                                                                                                                                                                                                                                                                     
                                    <html:text tabindex="560" property="famAppDate" styleId="famAppDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value).toUpperCase();" onfocus="if($('famOptionMode').value=='insertMode'&& Trim(this.value)==''){this.value=$('appDate').value}"/>                                                                                                                                                  
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss">                                                                                                                                                                                                                                                                                                                 
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">眷屬身分證號：</span>                                                                                                                                                                                                                                                                     
                                    <html:text tabindex="570" property="famIdnNo" styleId="famIdnNo" styleClass="textinput" size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();autoForeignFamSex();"/>                                                                                                                                                     
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td id="iss">                                                                                                                                                                                                                                                                                                                 
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">眷屬出生日期：</span>                                                                                                                                                                                                                                                                     
                                    <html:text tabindex="580" property="famBrDate" styleId="famBrDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                    
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td colspan="2" id="iss">                                                                                                                                                                                                                                                                                                     
                                    <span class="needtxt">＊</span>                                                                                                                                                                                                                                                                                           
                                    <span class="issuetitle_L_down">關　　係：</span>                                                                                                                                                                                                                                                                         
                                    <html:text tabindex="590" property="famEvtRel" styleId="famEvtRel" styleClass="textinput" size="1" maxlength="1" onkeyup="chgFamEvtRel();" onkeypress="chgFamEvtRelFocus();" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                         
                                    <span class="formtxt">(2-配偶，3-父母，4-子女，5-祖父母，6-兄弟姊妹，7-孫子女)</span>                                                                                                                                                                                                                                     
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss">　 <span class="issuetitle_L_down">結婚日期：</span>                                                                                                                                                                                                                                                             
                                    <html:text tabindex="600" property="marryDate" styleId="marryDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                     
                                    <span class="formtxt">(僅配偶須填寫)</span>                                                                                                                                                                                                                                                                               
                                </td>                                                                                                                                                                                                                                                                                                                         
                                <td id="iss">　 <span class="issuetitle_L_down">配偶扶養：</span>                                                                                                                                                                                                                                                             
                                    <html:text tabindex="610" property="raiseChildMk" styleId="raiseChildMk" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                               
                                    <span class="formtxt">(僅子女須填寫。為配偶所扶養，請輸入Y)</span>                                                                                                                                                                                                                                                          
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss" colspan="2">　 <span class="issuetitle_L_down">是否在學：</span>                                                                                                                                                                                                                                                 
                                    <html:text tabindex="620" property="studMk" styleId="studMk" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                           
                                    <span class="formtxt">(在學者，請輸入Y)</span>                                                                                                                                                                                                                                                                            
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss" colspan="2">　 <span class="issuetitle_L_down">每月工作收入：</span>                                                                                                                                                                                                                                             
                                    <html:text tabindex="630" property="monIncomeMk" styleId="monIncomeMk" styleClass="textinput" size="1" maxlength="1" onkeyup="chgMonIncomeMk();" onkeypress="chgMonIncomeMkFocus();" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                 
                                    <span class="formtxt">(有每月工作收入者，請輸入Y，並填入金額)</span>                                                                                                                                                                                                                                                      
                                    <html:text tabindex="640" property="monIncome" styleId="monIncome" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);"/>&nbsp;元                                                                                                                                                           
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td id="iss" colspan="2">　 <span class="issuetitle_L_down">領有重度以上身心障礙手冊：</span>                                                                                                                                                                                                                                 
                                    <html:text tabindex="650" property="handIcapMk" styleId="handIcapMk" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                   
                                    <span class="formtxt">(領有重度以上身心障礙手冊者，請輸入Y)</span>                                                                                                                                                                                                                                                        
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td colspan="2" id="iss">　 <span class="issuetitle_L_down">受禁治產(監護)宣告：</span>                                                                                                                                                                                                                                             
                                    <html:text tabindex="660" property="interDictMk" styleId="interDictMk" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();"/>                                                                                                                                                 
                                    <span class="formtxt">(受禁治產(監護)宣告者，請輸入Y)</span>                                                                                                                                                                                                                                                                    
                                </td>                                                                                                                                                                                                                                                                                                                         
                            </tr>                                                                                                                                                                                                                                                                                                                             
                            <tr>                                                                                                                                                                                                                                                                                                                              
                                <td></td>                                                                                                                                                                                                                                                                                                                     
                            </tr>                                                                                                                                                                                                                                                                                                                             
                        </table>                                                                                                                                                                                                                                                                                                                              
                    </td>                                                                                                                                                                                                                                                                                                                                     
                </tr>                                                                                                                                                                                                                                                                                                                                         
                <tr>                                                                                                                                                                                                                                                                                                                                          
                    <td colspan="9" class="issuetitle_L">                                                                                                                                                                                                                                                                                                     
                        <span class="issuetitle_search">&#9658;</span>&nbsp;眷屬資料：                                                                                                                                                                                                                                                                        
                    </td>                                                                                                                                                                                                                                                                                                                                     
                </tr>                                                                                                                                                                                                                                                                                                                                         
                <tr align="center">                                                                                                                                                                                                                                                                                                                           
                    <td class="issuetitle_L">                                                                                                                                                                                                                                                                                                                 
                        <bean:define id="list" name="<%=ConstantKey.DISABLED_ANNUITY_RECEIPT_FAM_DATA_LIST%>" />                                                                                                                                                                                                                                              
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>" >                                                                                                                                                                                                                                       
                            <display:column title="序號" style="width:8%; text-align:center;">                                                                                                                                                                                                                                                                    
                                <c:out value="${listItem_rowNum}" />                                                                                                                                                                                                                                                                                              
                            </display:column>                                                                                                                                                                                                                                                                                                                     
                            <display:column title="關　係" style="width:10%; text-align:left;">
                                <c:out value="${listItem.famEvtRelStr}" />
                            </display:column>                                                                                                                                                                                                                                       
                            <display:column title="姓　名" style="width:14%; text-align:left;">
                                <c:out value="${listItem.famName}" />
                            </display:column>                                                                                                                                                                                                                                            
                            <display:column property="famIdnNo" title="身分證號" style="width:12%; text-align:center;" />
                            <display:column title="在　學" style="width:10%; text-align:center;" >                                                                                                                                                                                                                                                                
                                <c:out value="${listItem.studMkStr}" />&nbsp;
                            </display:column>                                                                                                                                                                                                                                                                                                                     
                            <display:column title="每月工作收入" style="width:10%; text-align:right;" >                                                                                                                                                                                                                                                          
                                <fmt:formatNumber value="${listItem.monIncome}" />&nbsp;                                                                                                                                                                                                                                                                          
                            </display:column>                                                                                                                                                                                                                                                                                                                     
                            <display:column title="領有身心障礙手冊" style="width:13%; text-align:center;" >                                                                                                                                                                                                                                                      
                                <c:out value="${listItem.handIcapMkStr}" />&nbsp;
                            </display:column>                                                                                                                                                                                                                                                                                                                     
                            <display:column title="操作區" style="width:14%; text-align:center;" >                                                                                                                                                                                                                                                                
                                <acl:checkButton buttonName="btnUpdate">
                                    <input type="button" class="button" name="btnUpdate" value="修　改" onclick="getFamDetailData('<c:out value="${listItem.bafamilytempId}" />', '<c:out value="${listItem.seqNo}" />');$('dataIdx').value=<c:out value="${listItem_rowNum}" />;$('dataIndex').innerHTML=<c:out value="${listItem_rowNum}" />;$('famOptionMode').value='updateMode';">&nbsp;                                                                                                  
                                </acl:checkButton>                                                                                                                                                                                                                                                                                                                
                                <acl:checkButton buttonName="btnDeleteFam">                                                                                                                                                                                                                                                                                       
                                    <input type="button" class="button" name="btnDeleteFam" value="刪　除" onclick="$('famPage').value='1'; $('famMethod').value='doDeleteFamData'; $('seqNo').value='<c:out value="${listItem.seqNo}" />'; if(confirm('<bean:message bundle="<%=Global.BA_MSG%>" key="msg.delete.confirm"/>')){document.DisabledAnnuityReceiptFamForm.submit();}else{return false;}">
                                </acl:checkButton>                                                                                                                                                                                                                                                                                                                
                            </display:column>                                                                                                                                                                                                                                                                                                                     
                        </display:table>                                                                                                                                                                                                                                                                                                                      
                    </td>                                                                                                                                                                                                                                                                                                                                     
                </tr>                                                                                                                                                                                                                                                                                                                                         
                <tr>                                                                                                                                                                                                                                                                                                                                          
                    <td colspan="8"><hr width="100%" size="1px" noshade></td>                                                                                                                                                                                                                                                                                 
                </tr>                                                                                                                                                                                                                                                                                                                                         
                <tr>                                                                                                                                                                                                                                                                                                                                          
                    <td colspan="8"><hr size="1" noshade>                                                                                                                                                                                                                                                                                                     
                        <span class="titleinput">※注意事項：</span><br>                                                                                                                                                                                                                                                                                      
          　                                                  1.&nbsp;外藉人士請於身分證號欄位填寫護照號碼。<br>                                                                                                                                                                                                                                              
          　                                                  2.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>                                                                                                                                                                                                                           
          　                                                  3.&nbsp;<span class="needtxt">＊</span>為必填欄位。                                                                                                                                                                                                                                             
                    </td>                                                                                                                                                                                                                                                                                                                                     
                </tr> 
            </html:form>                                                                                                                                                                                                                                                                                                                                               
            </table>                                                                                                                                                                                                                                                                                                                                          
        </fieldset>                                                                                                                                                                                                                                                                                                                                           
    </div>                                                                                                                                                                                                                                                                                                                                                    
</div>                                                                                                                                                                                                                                                                                                                                                        
                                                                                                                                                                                                                                                                                                                                                              
<%@ include file="/includes/ba_footer.jsp"%>                                                                                                                                                                                                                                                                                                                  
                                                                                                                                                                                                                                                                                                                                                              
</body>                                                                                                                                                                                                                                                                                                                                                       
</html:html>                                                                                                                                                                                                                                                                                                                                                  