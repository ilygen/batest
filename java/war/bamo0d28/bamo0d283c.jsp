<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
  		<acl:setProgId progId="BAMO0D283C" />
    	<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    	<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    	<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    	<script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    	<html:javascript formName="SurvivorPayeeDataUpdateDetailForm" page="1" />
  		<script language="javascript" type="text/javascript">
        var today = "<%=DateUtility.getNowChineseDate()%>";

        //初始化給付方式
        function initPayCategory() {
            var frm = document.forms['SurvivorPayeeDataUpdateDetailForm'];
            var accRel = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.accRel}" />';
            $('payCategory').value = '';
            if (accRel == '3') {
                $("payCategory").value = "2";
            } else if(accRel == '1'){
                $("payCategory").value = "1";
            }
            if ($("payCategory").value == "1") {
                $("payTyp").disabled = false;
                $("accSeqNo").disabled = true;
                frm['payCategory'][0].checked = true;
                initPayType();
            }
            else if ($("payCategory").value == "2") {
                frm['payCategory'][1].checked = true;
                $("payTyp").value = "";
                $("payBankId").value="";  
                $("branchId").value="";
                $("payAccount").value = "";
                $("payEeacc").value = "";
                $("bankName").value = "";
                $("accName").value = "";
                $("accountContent1").style.display = "none";
                $("accountContent2").style.display = "none";
                $("accountContent3").style.display = "none";
                $("payTyp").disabled = true;
                var accSeqNoControl = '<c:out value="${SurvivorPayeeDataUpdateQueryCaseAccSeqNoControl}" />';
                if (accSeqNoControl == "1") {
                    frm['payCategory'][1].disabled = true;
                    $("accSeqNo").disabled = true;
                } else {
                    frm['payCategory'][1].disabled = false;
                    $("accSeqNo").disabled = false;
                }
                var accseq = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.accSeqNo}" />';
                var accseqOption = frm['accSeqNo'];
                for (var i = 0; i < accseqOption.options.length; i++) {
                    if (accseqOption.options[i].value == accseq) {
                        accseqOption.options[i].selected = true;
                    }
                }
            }
            else if (Trim($("payCategory").value) == "") {
                frm['payCategory'][0].checked = false;
                frm['payCategory'][1].checked = false;
                $("accountContent1").style.display = "none";
                $("accountContent2").style.display = "none";
                $("accountContent3").style.display = "none";
            }
            
        	var savingMk = $("savingMk").value;
//        	alert (savingMk);
        	if(savingMk == "Y"){
                frm['payCategory'][0].checked = true;
                frm['payCategory'][1].checked = false;              
                $("payTyp").disabled = true;
                $("payCategory2").disabled = true;
                $("accSeqNo").disabled = true;
        	} else {
//                frm['payCategory'][0].checked = true;
//                frm['payCategory'][1].checked = false;
//                $("payTyp").disabled = false;
//                $("payCategory2").disabled = false;
//                $("accSeqNo").disabled = true;
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

        function changePayCategory(){
            if($("payCategory").value=="1"){
                $("accSeqNo").value="";
                $("payTyp").disabled = false;
                $("accSeqNo").disabled = true;
                $('accRel').value = '1';
            }
            if($("payCategory").value=="2"){
                $("payTyp").value="";
                $("payBankId").value="";  
                $("branchId").value="";
                $("payAccount").value="";
                $("payEeacc").value="";
                $("bankName").value="";
                $("accName").value="";
                $("accountContent1").style.display="none";
                $("accountContent2").style.display="none";
                $("accountContent3").style.display="none";
                $('accRel').value = '3';
                $("payTyp").disabled = true;
                var accSeqNoControl = '<c:out value="${SurvivorPayeeDataUpdateQueryCaseAccSeqNoControl}" />';
                if(accSeqNoControl == "1"){
                    document.getElementsByName("payCategory")[1].disabled = true;
                    $("accSeqNo").disabled = true;
                } else {
                    document.getElementsByName("payCategory")[1].disabled = false;
                    $("accSeqNo").disabled = false;
                }
            }
        }

        function initPayType() {
            var payTyp = $("payTyp").value;
            $("accSeqNo").value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.seqNo}" />';
            var payBankId = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.payBankId}"/>';
            var branchId = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.branchId}"/>';
            $("payBankIdBranchId").value = payBankId + branchId;
            $("payEeacc").value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.payEeacc}" />';
            $("bankName").value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.bankName}" />';
            if (payTyp == "1" || payTyp == "2") {
                $("accountContent1").style.display = "inline";
                $("accountContent2").style.display = "none";
                $("accountContent3").style.display = "inline";
                $("payAccount").value = "";
                $("bankName").value = "";
                $("specialAccContent").style.display="inline";
                if($("origSpecialAcc").value == "Y"){
                 $("specialAccAfter").checked = true;
                }  
            }
            if (payTyp == "" || payTyp == "3" || payTyp == "4" || payTyp == "A") {
                $("accName").value = "";
                $("payBankIdBranchId").value = "";
                $("payEeacc").value = "";
                $("payAccount").value = "";
                $("bankName").value = "";
                //$("accName").value = "";
                $("accountContent1").style.display = "none";
                $("accountContent2").style.display = "none";
                $("accountContent3").style.display = "none";
                $("specialAccContent").style.display="none";
            }
            if (payTyp == "5" || payTyp == "6") {
                $("accName").value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.accName}" />';
                $("payBankIdBranchId").value = "";
                $("payAccount").value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.payAccount}" />';
                $("bankName").value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.bankName}" />';
                $("accountContent1").style.display = "none";
                $("accountContent2").style.display = "inline";
                $("accountContent3").style.display = "inline";
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

        // 變更 給付方式(本人領取) 時畫面異動
        function changePayType(){
            var payTyp = $("payTyp").value;
            $("accName").value = "";
            $("accSeqNo").value = "";
            $("payBankId").value="";  
            $("branchId").value=""; 
            $("payEeacc").value = "";
            $("bankName").value = "";
            $("payAccount").value = "";
            //$("accName").value = "";
            if (payTyp == "1" || payTyp == "2") {
                if ($("accName").value == '') {
                    if ($('benName') != null) {
                        $("accName").value = $("benName").value;
                    }
                }
                $("accountContent1").style.display = "inline";
                $("accountContent2").style.display = "none";
                $("accountContent3").style.display = "inline";
                $("specialAccContent").style.display="inline";  
            }else{
                $("specialAccContent").style.display="none";    
            }   
            if (payTyp == "" || payTyp == "3" || payTyp == "4" || payTyp == "A") {
                $("accountContent1").style.display = "none";
                $("accountContent2").style.display = "none";
                $("accountContent3").style.display = "none";
                $("specialAccContent").style.display="none";  
            }
            if (payTyp == "5" || payTyp == "6") {
                $("accountContent1").style.display = "none";
                $("accountContent2").style.display = "inline";
                $("accountContent3").style.display = "inline";
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

        function toggleAccNameOnAccountReset() {
            if ($('accName') != null && $("accName").value == '') {
                if ($('benName') != null) {
                    $("accName").value = $("benName").value;
                }
            }
        }
        
        function changeSavingMk(){
        	var frm = document.forms['SurvivorPayeeDataUpdateDetailForm'];
        	var savingMk = $("savingMk").value;
//        	alert (savingMk);
        	if(savingMk == "Y"){
                $("payCategory").value = "1";
                $("payTyp").value = "3";                
                $("payTyp").disabled = true;
                $("payCategory2").disabled = true;
                $("accSeqNo").disabled = true;
                frm['payCategory'][0].checked = true;
        	} else {
                $("payCategory").value = "1";
                $("payTyp").value = "";                
                $("payTyp").disabled = false;
                $("payCategory2").disabled = false;
                $("accSeqNo").disabled = true;
                frm['payCategory'][0].checked = true;
        	}
//        	alert ($("payTyp").value);
        	changePayType();
        	// initPayType();
//        	alert ($("payTyp").value);
        }

        function initInterdictDateDisplay(){
            var interdictMkValue = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.interdictMk}" />';
            if(interdictMkValue == 'Y'){
                $('interdictMk').checked = true;
                $("interdictDateSection").style.display="inline";
            } else if(interdictMkValue == 'N') {
                $('interdictMk').checked = false;
                $("interdictDateSection").style.display="inline";
            } else if(interdictMkValue == '') {
                $('interdictMk').checked = false;
                $('interdictDate').value = '';
                $("interdictDateSection").style.display="none";
            }
        }

        //變更受禁治產宣告註記時
        function toggleInterdictDateDisplay(){
            if($('interdictMk').checked){
                $('interdictDateSection').style.display = 'inline';
            } else {
                $('interdictDate').value = '';
                $('repealInterdictDate').value = '';
                $('interdictDateSection').style.display = 'none';
            }
        }

        function isFieldEmpty(field){
            return  field == null || field == '';
        }

        function initCommTyp(){
            var frm = document.forms['SurvivorPayeeDataUpdateDetailForm'];
            $('commTyp').value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.commTyp}" />';
            if(isFieldEmpty($('commTyp').value)) {
                $('commTyp').value='2';
                frm['commTyp'][1].checked = true;
            } else {
                $("commZip").value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.commZip}" />';
                $("commAddr").value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.commAddr}" />';
                $("addContent1").style.display = "inline";
                $("addContent2").style.display = "none";
                if ($("commTyp").value == "1") {
                    $("addContent1").style.display = "none";
                    $("addContent2").style.display = "inline";
                    $("commZip").disabled = true;
                    $("commAddr").disabled = true;
                    frm['commTyp'][0].checked = true;
                }
                else if ($("commTyp").value == "2") {
                    $("commZip").disabled = false;
                    $("commAddr").disabled = false;
                    $("addContent1").style.display = "inline";
                    $("addContent2").style.display = "none";
                    frm['commTyp'][1].checked = true;
                }
            }
        }

        //變更每月工作收入註記時
        function toggleMonIncomeDisplay(){
            if($("monIncomeMk").checked){
                $("monIncomeMk").value = 'Y';
                $("monIncomeSection").style.display="inline";
            }else{
                $("monIncome").value = "";
                $("monIncomeSection").style.display="none";
            }
        }

        //變更在學時
        function toggleAtSchoolDisplay(){
            if($("studMk").checked){
                $("studMk").value = 'Y';
                $("btnStudterm").style.display="inline";
                $("schoolCodeContent").style.display="inline";
            }else{
                $("studMk").value = "";
                $("schoolCode").value = "";
	            $("schoolCodeOption").value = "";
                $("btnStudterm").style.display="none";
                $("schoolCodeContent").style.display="none";
            }
        }

        //變更重殘起訖年月維護
        function toggleHandicapDisplay(){
            if($("handicapMk").checked){
                $("handicapMk").value = 'Y';
                $("handicap").style.display="inline";
            }else{
                $("handicapMk").value = "";
                $("handicap").style.display="none";
            }
        }
        //變更放棄請領註記時
        function toggleAbanApplyMk(){
            if($("abanApplyMk").checked){
                $("abanApplyMk").value = "Y";
                $("abanApplyMkSection").style.display="inline";
            } else {
                $("abanApplyMk").value = "N";
                $("abanApSym").value = "";
                $("abanApplyMkSection").style.display="none";
            }
        }
        
        function initAllFields(){
        	$("benEvtRel").disabled = true;
        	$("ableApsYm").disabled = true;
            $("benName").disabled = true;
            $("appDate").disabled = true;
            $("benIdnNo").disabled = true;
            $("benBrDate").disabled = true;
            $("benDieDate").disabled = true;
            $(spanBenNationTyp).disabled = true;
            $(spanBenSex).disabled = true;            
            $("benNationCode").disabled = true;
            $("benNationCodeOption").disabled = true;
            
            $("spanIdnChkNote").disabled = true;
            //$("idnChkYear").disabled = true;
            //$("idnChkMonth").disabled = true;
            
            $("marryDate").disabled = true;
            $("digaMyDate").disabled = true;
            $("raiseChildMk").disabled = true;
            $("adoptDate").disabled = true;
            $("raiseEvtMk").disabled = true;
            
            $("monIncomeMk").disabled = true;
            $("monIncome").disabled = true;
            $("studMk").disabled = true;
            $("btnStudterm").disabled = true;
            $("schoolCode").disabled = true;
            $("schoolCodeOption").disabled = true;
            $("compelMk").disabled = true;
            $("btnCompelData").disabled = true;
            
            $("handicapMk").disabled = true;
            $("btnHandicapterm").disabled = true;
            $("interdictMk").disabled = true;
            $("interdictDate").disabled = true;
            $("repealInterdictDate").disabled = true;
            $("abanApplyMk").disabled = true;
            $("abanApSym").disabled = true;
            $("benMissingSdate").disabled = true;
            $("benMissingEdate").disabled = true;
            $("relatChgDate").disabled = true;
            $("prisonSdate").disabled = true;
            $("prisonEdate").disabled = true;
            
            $("spanPayCategory").disabled = true;
            $("payBankId").disabled = true;
            $("branchId").disabled = true;
            $("payEeacc").disabled = true;
            $("accName").disabled = true;
            $("payAccount").disabled = true;
            $("bankName").disabled = true;
            
            
            $("tel1").disabled = true;
            $("tel2").disabled = true;
            $("rdocommtyp1").disabled = true;
            $("rdocommtyp2").disabled = true;
            $("commZip").disabled = true;
            $("commAddr").disabled = true;
            $("spanBenMarrMk").disabled = true;
            $("savingMk").disabled = true;
            
            
            $("grdName").disabled = true; 
            $("grdIdnNo").disabled = true;  
            $("grdBrDate").disabled = true;
            $("assignName").disabled = true;
            $("assignIdnNo").disabled = true;
            $("assignBrDate").disabled = true;
            $("closeCause").disabled = true;
            $("closeDate").disabled = true;      
        }        

        //改變關係時須重設顯示狀態的一些欄位
        function initFieldByBenEvtRel(){

            $("marrySection").style.display = "none";
            $("raiseSection").style.display = "none";
            $("incapableSection").style.display = "none";
            $("raiseEvtMkSection").style.display="none";
            $("ableApSymSection").style.display="none";
            $("interdictDateSection").style.display="none";
            $("studMkSection").style.display="none";
            $("btnStudterm").style.display="none";
            $("schoolCodeContent").style.display="none";
            $("savingMkSection").style.display="none";

            switch($("benEvtRel").value) {
                case "2":
                    $("incapableSection").style.display = "inline";
                    $("marrySection").style.display = "inline";
                    $('raiseChildMk').checked = false;
                    $('raiseEvtMk').checked = false;
                    $('studMk').checked = false;
                    $("schoolCode").value = "";
    	            $("schoolCodeOption").value = "";
    	            $("savingMkSection").style.display="inline";
                    break;
                case "3":
                    $("ableApSymSection").style.display = "inline";
                    $('raiseChildMk').checked = false;
                    $('raiseEvtMk').checked = false;
                    $('handicapMk').checked = false;
                    $('studMk').checked = false;
                    $("schoolCode").value = "";
    	            $("schoolCodeOption").value = "";
    	            $("savingMkSection").style.display="none";
                    break;
                case "4":
                    $("incapableSection").style.display = "inline";
                    $("studMkSection").style.display = "inline";
                    $("raiseSection").style.display = "inline";
                    if($('studMk').checked) $("btnStudterm").style.display="inline";
                    if(!$('studMk').checked) $("btnStudterm").style.display="none";
                    if($('studMk').checked) $("schoolCodeContent").style.display="inline";
                    if(!$('studMk').checked) $("schoolCodeContent").style.display="none";
                    $("savingMkSection").style.display="inline";
                    break;
                case "5":
                    $('raiseChildMk').checked = false;
                    $('raiseEvtMk').checked = false;
                    $('handicapMk').checked = false;
                    $('studMk').checked = false;
                    $("savingMkSection").style.display="none";
                    break;
                case "6":
                    $("raiseEvtMkSection").style.display = "inline";
                    $("incapableSection").style.display = "inline";
                    $('raiseChildMk').checked = false;
                    $('studMk').checked = false;
                    $("schoolCode").value = "";
    	            $("schoolCodeOption").value = "";
    	            $("savingMkSection").style.display="inline";
                    break;
                case "7":
                    $("raiseEvtMkSection").style.display = "inline";
                    $("incapableSection").style.display = "inline";
                    $("studMkSection").style.display = "inline";
                    if($('studMk').checked) $("btnStudterm").style.display="inline";
                    if(!$('studMk').checked) $("btnStudterm").style.display="none";
                    if($('studMk').checked) $("schoolCodeContent").style.display="inline";
                    if(!$('studMk').checked) $("schoolCodeContent").style.display="none";
                    $("savingMkSection").style.display="inline";
                    break;
                default:
                    $('raiseChildMk').checked = false;
                    $('raiseEvtMk').checked = false;
                    $('handicapMk').checked = false;
                    $('studMk').checked = false;
                    $("schoolCode").value = "";
    	            $("schoolCodeOption").value = "";
    	            $("savingMkSection").style.display="none";
                    break;
            }
        }

        //改變關係時須重設連動改變的一些欄位
        function toggleFieldAfterRelChange(){

            $("marrySection").style.display="none";
            $("raiseSection").style.display="none";
            $("incapableSection").style.display="none";
            $("raiseEvtMkSection").style.display="none";
            $("ableApSymSection").style.display="none";
            $("interdictDateSection").style.display="none";
            $("studMkSection").style.display="none";
            $("btnStudterm").style.display="none";
            $("schoolCodeContent").style.display="none";
            $("savingMkSection").style.display="none";

            switch($("benEvtRel").value) {
                case "2":
                    $("raiseEvtMk").value="";
                    $("raiseChildMk").value="";
                    $("ableApsYm").value="";
                    $('ableApSym').value = '';
                    $('adoptDate').value = '';
                    $('raiseChildMk').checked = false;
                    $('raiseEvtMk').checked = false;
                    $('studMk').checked = false;
                    $("schoolCode").value = "";
    	            $("schoolCodeOption").value = "";
                    $("marrySection").style.display = "inline";
                    $("incapableSection").style.display = "inline";
                    if($('interdictMk').checked) $("interdictDateSection").style.display="inline";
                    $("savingMkSection").style.display="inline";
                    break;
                case "3":
                    $("raiseEvtMk").value = "";
                    $("raiseChildMk").value = "";
                    $("marryDate").value = "";
                    $('interdictDate').value = '';
                    $('digaMyDate').value = '';
                    $('adoptDate').value = '';
                    $('raiseChildMk').checked = false;
                    $('raiseEvtMk').checked = false;
                    $('handicapMk').checked = false;
                    $('studMk').checked = false;
                    $("schoolCode").value = "";
    	            $("schoolCodeOption").value = "";
                    $('interdictMk').checked = false;
                    $("ableApSymSection").style.display="inline";
                    $("savingMkSection").style.display="none";
                    break;
                case "4":
                    $("raiseEvtMk").value="";
                    $("marryDate").value = "";
                    $("ableApsYm").value="";
                    $('digaMyDate').value = '';
                    $("incapableSection").style.display = "inline";
                    $("studMkSection").style.display = "inline";
                    $("raiseSection").style.display = "inline";
                    if($('studMk').checked) $("btnStudterm").style.display="inline";
                    if(!$('studMk').checked) $("btnStudterm").style.display="none";
                    if($('studMk').checked) $("schoolCodeContent").style.display="inline";
                    if(!$('studMk').checked) $("schoolCodeContent").style.display="none";
                    if($('interdictMk').checked) $("interdictDateSection").style.display="inline";
                    $("savingMkSection").style.display="inline";
                    break;
                case "5":
                    $('marryDate').value = '';
                    $('digaMyDate').value = '';
                    $('ableApSym').value = '';
                    $('interdictDate').value = '';
                    $('adoptDate').value = '';
                    $('raiseChildMk').checked = false;
                    $('raiseEvtMk').checked = false;
                    $('handicapMk').checked = false;
                    $('studMk').checked = false;
                    $("schoolCode").value = "";
    	            $("schoolCodeOption").value = "";
                    $('interdictMk').checekd = false;
                    $("savingMkSection").style.display="none";
                    break;
                case "6":
                    $("marryDate").value = "";
                    $('digaMyDate').value = '';
                    $("raiseChildMk").value="";
                    $("ableApsYm").value="";
                    $('adoptDate').value = '';
                    $('raiseChildMk').checked = false;
                    $('studMk').checked = false;
                    $("schoolCode").value = "";
    	            $("schoolCodeOption").value = "";
                    $("raiseEvtMkSection").style.display = "inline";
                    $("incapableSection").style.display = "inline";
                    if($('interdictMk').checked) $("interdictDateSection").style.display="inline";
                    $("savingMkSection").style.display="inline";
                    break;
                case "7":
                    $("marryDate").value = "";
                    $("raiseChildMk").value="";
                    $("ableApsYm").value="";
                    $('digaMyDate').value = '';
                    $('adoptDate').value = '';
                    $("raiseEvtMkSection").style.display = "inline";
                    $("incapableSection").style.display = "inline";
                    $("studMkSection").style.display = "inline";
                    if($('studMk').checked) $("btnStudterm").style.display="inline";
                    if(!$('studMk').checked) $("btnStudterm").style.display="none";
                    if($('studMk').checked) $("schoolCodeContent").style.display="inline";
                    if(!$('studMk').checked) $("schoolCodeContent").style.display="none";
                    if($('interdictMk').checked) $("interdictDateSection").style.display="inline";
                    $("savingMkSection").style.display="inline";
                    break;
                default:
                    $('marryDate').value = '';
                    $('digaMyDate').value = '';
                    $('ableApSym').value = '';
                    $('interdictDate').value = '';
                    $('adoptDate').value = '';
                    $('raiseChildMk').checked = false;
                    $('raiseEvtMk').checked = false;
                    $('handicapMk').checked = false;
                    $('studMk').checked = false;
                    $("schoolCode").value = "";
    	            $("schoolCodeOption").value = "";
                    $('interdictMk').checked = false;
                    $("savingMkSection").style.display="none";
                    break;
            }
        }

        function changeCommTyp(){
            $("commZip").value = "";
            $("commAddr").value = "";
            $("addContent1").style.display="inline";
            $("addContent2").style.display="none";
            if($("commTyp").value=="1"){
                $("addContent1").style.display="none";
                $("addContent2").style.display="inline";
                DWREngine.setAsync(false);
                
				//if(!isValidDate($("benBrDate").value)){
	            //    alert('輸入之「受款人出生日期」錯誤，請重新確認。\r\n');
	            //}else{
	                CVLDTLUtil.getCvldtlZip();
	                CVLDTLUtil.getCvldtlAddr();
	                $("commZip").disabled = true;
	                $("commAddr").disabled = true;
	                document.getElementsByName("commTyp")[0].checked=true;
	                if(Trim($("commAddr").value) == ""){//如果沒抓到戶籍地址資料,則跳回現住址並提示使用者
	                    $("commTyp").value = "2";
	                    alert('戶籍地住址無資料，請選擇「現住址」並輸入住址資料。\r\n');
	                    changeCommTyp();
	                    $("commZip").value = "";
	                    $("commAddr").value = "";
	                }
	             //}
            }
            if($("commTyp").value=="2"){
                $("commZip").disabled = false;
                $("commAddr").disabled = false;
                $("addContent1").style.display="inline";
                $("addContent2").style.display="none";
                document.getElementsByName("commTyp")[1].checked=true;
            }
        }

        var DefaultMarritalStatusSetter = {
            isNotAdult : function() {
                var benBrDate = calDay($("benBrDate").value, 0);
                <%--var sYearTwenty = '<%=DateUtility.calYear(DateUtility.getNowChineseDate(),-20)%>';--%>
                var sYearTwenty = '<%=DateUtility.calYear( DateUtility.getNowChineseDate().substring(0, 5).concat( String.valueOf(DateUtility.lastDay(DateUtility.getNowChineseDate())) ) ,-20)%>';
                return benBrDate > sYearTwenty;
            },
            checkMarritalStatus : function(){
                $("benMarrMkSection").show();
                document.getElementsByName("benMarrMk")[1].checked = true;
//                DefaultMarritalStatusSetter.checkSavingMk();
            },
            checkSavingMk : function(){
                if( document.getElementsByName("benMarrMk")[0].checked ){
                    if($('savingMkSection') != null){
                        $('savingMkSection').hide();
                        $("savingMkSectionSpace").show();		//計息存儲Space
                        if($('savingMk') != null){
                            $('savingMk').checked = false;
                        }
                    }
                }
                if( document.getElementsByName("benMarrMk")[1].checked ){
                    $('savingMkSection').show();
                    $("savingMkSectionSpace").hide();		//計息存儲Space
                }
            },
            unCheckMarritalStatus : function(){
                document.getElementsByName("benMarrMk")[0].checked = false;
                document.getElementsByName("benMarrMk")[1].checked = false;
                $("benMarrMkSection").hide();
//                $("savingMkSectionSpace").hide();		//計息存儲Space
//                if($('savingMkSection') != null){
//                    $("savingMkSection").hide();
//                    $('savingMk').checked = false;
//                }
            },
            initMarritalStatus : function() {
                var mk = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.benMarrMk}"/>';
//                var saving = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.savingMk}"/>';
                if (( $F('benEvtRel') === '4' || $F('benEvtRel') === '6' || $F('benEvtRel') === '7') && DefaultMarritalStatusSetter.isNotAdult()) {
                    $("benMarrMkSection").show();
                    if (mk == 'Y') {
                        document.getElementsByName("benMarrMk")[0].checked = true;
//                        $("savingMkSection").hide();
//                        $("savingMkSectionSpace").show();		//計息存儲Space
                    } else if (mk == 'N') {
                        document.getElementsByName("benMarrMk")[1].checked = true;
//                        $("savingMkSection").show();
//                        $("savingMkSectionSpace").hide();		//計息存儲Space
//                        if (saving == 'Y') {
//                            $('savingMk').checked = true;
//                        } else if (saving == 'N') {
//                            $('savingMk').checked = false;
//                        }
                    }
                }else{
//                    $("savingMkSectionSpace").hide();		//計息存儲Space
                }
            },
            setDefaultMarritalStatus : function() {
                if(  ( $F('benEvtRel') === '4' || $F('benEvtRel') === '6' || $F('benEvtRel') === '7') && DefaultMarritalStatusSetter.isNotAdult() ){
                    DefaultMarritalStatusSetter.checkMarritalStatus();
                } else {
                    DefaultMarritalStatusSetter.unCheckMarritalStatus();
                }
            }
        };

        function initBenNationTpe(){
            var frm = document.forms['SurvivorPayeeDataUpdateDetailForm'];
            $('benNationTyp').value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.benNationTyp}" />' || '1';
            if($("benNationTyp").value=="1"){
                $("benSex").value = "";
                $("benNationCode").value = "";
                $("sexContent").style.display="none";
                $("nationalityContent").style.display="none";
                frm['benNationTyp'][0].checked = true;
                frm['commTyp'][0].disabled = false;
            }
            if($("benNationTyp").value=="2"){
                $("benSex").value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.benSex}" />';
                $("benNationCode").value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.benNationCode}" />';
                var benNation = frm['benNationCodeOption'];
                for(var i = 0; i < benNation.options.length; i++){
                    if((benNation.options[i].value) == $("benNationCode").value){
                        benNation.options[i].selected = true;
                    }
                }
                $("sexContent").style.display = "inline";
                $("nationalityContent").style.display = "inline";
                if ($("benSex").value == '1') frm['benSex'][0].checked = true;
                if ($("benSex").value == '2') frm['benSex'][1].checked = true;
                frm['benNationTyp'][1].checked = true;
                frm['benNationCode'].disabled = false;
                frm['commTyp'][0].disabled = true;
            }
        }
        

        // 變更 國籍別 時畫面異動
        function changeBenNationTpe(){
        	var frm = document.forms['SurvivorPayeeDataUpdateDetailForm'];
        	$("benSex").value = "";
        	$("benNationCode").value = "";
        	frm['commTyp'][0].disabled = false;
        	if ($("benNationTyp").value == "1") {
            	document.getElementsByName("benSex")[0].checked=false;
                document.getElementsByName("benSex")[1].checked=false;
				$("sexContent").style.display = "none";
            	$("nationalityContent").style.display = "none";
            	frm['benSex'][0].checked = false;
            	frm['benSex'][1].checked = false;
        	}
        	if ($("benNationTyp").value == "2") {
            	$("sexContent").style.display = "inline";
            	$("nationalityContent").style.display = "inline";
            	$("benNationCode").readOnly = true;
            	frm['commTyp'][0].disabled = true;
            	frm['commTyp'][1].checked = true;
                frm['benSex'][0].checked = false;
            	frm['benSex'][1].checked = false;
                $('benNationCode').value = '';
            	frm['benNationCodeOption'].selectedIndex = 0;
                $("commTyp").value = "2";
                $("commZip").disabled = false;
                $("commAddr").disabled = false;
                $("addContent1").style.display = "inline";
                $("addContent2").style.display = "none";
        	}
        	autoForeignBenSex();
        }

    <%-- 重置身分查核年月預設值 --%>
    function initIdnChkNote() {
        $("chkForeigner").style.display="none";
        $("idnChkNote1").checked = false;
        $("idnChkNote2").checked = false;
        $("idnChkYear").value = "";
        $("idnChkMonth").value = "";
        
        var oldIdnChkYm = "";
        if(Trim($("oldIdnChkYm").value)!=""){
            oldIdnChkYm = changeDateType($("oldIdnChkYm").value+"01").substring(0 , 5);
        }else{
            oldIdnChkYm = today.substring(0 , 5);
        }
        
        if($("oldIdnChkNote").value=='1'){
            $("chkForeigner").style.display="none";
            if(parseNumber(oldIdnChkYm) > parseNumber(today.substring(0 , 5))){
                $("idnChkNote1").checked = false;
                $("idnChkNote1").disabled = true;
            }else{
                $("idnChkNote1").checked = true;
            }
        }
        else if($("oldIdnChkNote").value=='2'){
            $("idnChkNote2").checked = true;
            $("chkForeigner").style.display="inline";
            $("idnChkYear").value = $("oldIdnChkYear").value;
            $("idnChkMonth").value = $("oldIdnChkMonth").value;
        }
        else{
            $("chkForeigner").style.display="none";
            $("idnChkNote1").checked = false;
            $("idnChkNote2").checked = false;
            $("idnChkYear").value = "";
            $("idnChkMonth").value = "";
        }
    }

        // 身分查核年月
        function chkIdnChkNote() {
            if($("idnChkNote1").checked){
                $("idnChkYear").value = "";
                $("idnChkMonth").value = "";
                $("chkForeigner").style.display="none";
            }
            else if($("idnChkNote2").checked){
                $("chkForeigner").style.display="inline";
            } 
        }

        //查戶籍工具
        var CVLDTLUtil = {
            getCvldtlZip   : function() { // Ajax for 取得 戶籍檔郵遞區號
                if (($("benIdnNo").value != "") && ($("benBrDate").value != "")) {
                    updateCommonAjaxDo.getCvldtlZip($("benIdnNo").value, $("benBrDate").value, CVLDTLUtil.fillCvldtlZip);
                }
            },
            getCvldtlAddr  : function() { // Ajax for 取得 戶籍檔地址
                if (($("benIdnNo").value != "") && ($("benBrDate").value != "")) {
                    updateCommonAjaxDo.getCvldtlAddr($("benIdnNo").value, $("benBrDate").value, CVLDTLUtil.fillCvldtlAddr);
                }
            },
            fillCvldtlZip  : function(zip) { //callback,將取得的戶籍郵遞區號填入畫面欄位
                var commZipGroup = document.getElementsByName("commZip");
                for (var i = 0; i < commZipGroup.length; i++) {
                    commZipGroup[i].value = zip;
                }
            },
            fillCvldtlAddr : function(addr) { //callback,將取得的地址填入畫面欄位
                var commAddrGroup = document.getElementsByName("commAddr");
                for (var i = 0; i < commAddrGroup.length; i++) {
                    commAddrGroup[i].value = addr;
                }
            }
        };

        function moveFocus(elementId) {
            var frm = document.forms['SurvivorPayeeDataUpdateDetailForm'];
            frm[elementId].focus();
        }

        function checkRequireFields() {
            var nowDate = "<%=DateUtility.getNowChineseDate()%>";
            var nowDateYM = nowDate.substring(0,5);
            var benBrDate = calDay($("benBrDate").value,0);
            <%--var sYearTwenty = '<%=DateUtility.calYear(DateUtility.getNowChineseDate(), -20)%>';--%>
            var sYearTwenty = '<%=DateUtility.calYear( DateUtility.getNowChineseDate().substring(0, 5).concat( String.valueOf(DateUtility.lastDay(DateUtility.getNowChineseDate())) ) ,-20)%>';
            var evtDieDate = '<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtDieDateStr}" />';
            var evtBrDateChineseString = '<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtBrDateStr}" />';
            var evtIdnNo = '<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtIdnNo}" />';
            var frm = document.forms['SurvivorPayeeDataUpdateDetailForm'];
            var grdIdentity = Trim($F("grdIdnNo"));
            var validator = new SimpleValidator();
            var studtermListSize = '<c:out value="${SURVIVOR_PAYEE_STUDTERM_SIZE}" />';
            var handicaptermListSize = '<c:out value = "${SURVIVOR_PAYEE_HANDICAPTERM_SIZE}" />';
            var studResult = '<c:out value="${studResult}" />';
            var compelDataListSize = '<c:out value="${SURVIVOR_PAYEE_COMPELDATA_SIZE}" />';
			
			var secondText = $("benIdnNo").value.substring(1,2);
			if($("benIdnNo").value.length==10){
		if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[0].checked==true){
 			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
				validator.addErrorMsg('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」。');
 				 $("benSex").focus();
    		}
 		}else if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[1].checked==true){
 			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
 				validator.addErrorMsg('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」。');
 				 $("benSex").focus();
    		}
 		}
			}
			
            
            //如果強迫不合格有勾,但listSize為0,則提示警告訊息
            if ($('compelMk') != null && $('compelMk').checked) {
                if (compelDataListSize == 0) {
                    validator.addErrorMsg('請輸入「不合格年月」資料。');
                }
            }
            //如果強迫不合格沒勾,但listSize不為0,則提示警告訊息
            if ($('compelMk') == null || !$('compelMk').checked) {
                if (compelDataListSize != 0) {
                    //$("compelMk").checked = true;
                    //$("compelMk").value = 'Y';
                    //$("btnCompelData").style.display="inline";
                    validator.addErrorMsg('已有「不合格年月」資料，不得取消「強迫不合格」勾選。');
                }
            }
                
            if(!validator.isRequiredValueEmpty('「關係」', $('benEvtRel'))){
                validator.isRequiredValueEmpty('「國籍別」', frm['benNationTyp']);
                validator.isRequiredValueEmpty('「遺屬姓名」', $('benName'));
                if (Trim($("benEvtRel").value) != "O"){
                	validator.isRequiredValueEmpty('「遺屬身分證號」', $('benIdnNo'));
                }
                validator.isRequiredValueEmpty('「遺屬出生日期」', $('benBrDate'));
                validator.isRequiredValueEmpty('「遺屬申請日期」', $('appDate'));
                validator.isZipFormatInvalid($F('commZip'));

                if(Trim($F('benEvtRel')) == '2'){
                    if( $('marryDate') != null && Trim($F('marryDate')) == "" ){
                        validator.isRequiredValueEmpty('「結婚日期」', $('marryDate'));
                    }
                }

                if ((frm['payCategory'][0].checked && Trim($("payTyp").value) == "") ||
                    (frm['payCategory'][1].checked && Trim($("accSeqNo").value) == "") ||
                    (!frm['payCategory'][0].checked && !frm['payCategory'][1].checked ) ) {
                    validator.isRequiredValueEmpty('「給付方式」', $('payTyp'));
                }
                
                if(Trim($("benIdnNo").value) == (Trim(evtIdnNo)))
                    validator.addErrorMsg('「遺屬」的身份證號不得與事故者相同。');
                    
                if (document.getElementsByName("benNationTyp")[0].checked==true && Trim($("benEvtRel").value) != "O"){
	                 if(!isValidIdNoForTest($("benIdnNo").value) || !chkPID_CHAR($("benIdnNo").value))
	                 	 validator.addErrorMsg('「遺屬身分證號(保險證號)」輸入有誤，請輸入長度為10 碼且符合格式的資料。');
	                
	             }
	             
	              if (document.getElementsByName("benNationTyp")[1].checked==true){
	             	if(!isEngNum($("benIdnNo").value)){
	             		validator.addErrorMsg('「遺屬身分證號(保險證號)」格式錯誤。');
	             	}
	             }

                if(grdIdentity !== ''){
                    if(grdIdentity === Trim($F("benIdnNo")) ){
                        validator.addErrorMsg('「法定代理人」的身份證號不得與遺屬相同。');
                    }
                    if(grdIdentity === Trim(evtIdnNo) ){
                        validator.addErrorMsg('「法定代理人」的身份證號不得與事故者相同。');
                    }
                }

                if(!validator.isRequiredValueEmpty('「遺屬申請日期」', $('appDate')) ){
                    validator.isGreaterThanOrEqual({name : '「遺屬申請日期」', value : $F('appDate')},
                                                   {name : '「098年1月1日」', value : '0980101'});
                    validator.isGreaterThanOrEqual({name : '「遺屬申請日期」', value : $F('appDate')},
                                                   {name : '「事故者死亡日期」', value : evtDieDate});
                    validator.isGreaterThanOrEqual({name : '「遺屬申請日期」', value : $F('appDate')},
                                                   {name : '「出生日期」', value : Trim($F("benBrDate"))});
                    validator.isSmallerThanOrEqual({name : '「遺屬申請日期」', value : $F('appDate')},
                                                   {name : '「系統日期」', value : nowDate});
                }

                if(frm['benNationTyp'][1].checked ){
                    validator.isRequiredValueEmpty('「性別」', $('benSex'));
                    if($('benNationCode') !=null && Trim($('benNationCode').value) == '000'){
                        validator.addErrorMsg('「國籍別」為外籍不得輸入中華民國之國籍代碼。');
                    }
                    if (!validator.isRequiredValueEmpty('「國籍」', $('benNationCode'))) {
                        validator.isAlphaNumericValue('「國籍」', $F('benNationCode'))
                    }
                }

                //未滿20歲
                if (benBrDate > sYearTwenty) {
                    if (( $F('benEvtRel') === '4' || $F('benEvtRel') === '6' || $F('benEvtRel') === '7')) {
                        validator.isRequiredValueEmpty('「婚姻狀況」', frm['benMarrMk']);
                        if(($('savingMk').checked==false && $('benMarrMk').checked==false)||($('interdictMk').checked && ($('repealInterdictDate') == null || Trim($F('repealInterdictDate')) == ''))) {
                          
                                //validator.isRequiredValueEmpty('「法定代理人姓名」', $('grdName'));
                                //validator.isRequiredValueEmpty('「法定代理人身分證號」', $('grdIdnNo'));
                                //validator.isRequiredValueEmpty('「法定代理人出生日期」', $('grdBrDate'));
                        
                        }
                    }
                } else { //滿20歲(不會有計息存儲與婚姻狀況欄位,所以只檢查interdictmk
                    if (( $F('benEvtRel') === '2' || $F('benEvtRel') === '4' || $F('benEvtRel') === '6' || $F('benEvtRel') === '7')) {
                        if($('interdictMk').checked==true && ($('repealInterdictDate') == null || Trim($F('repealInterdictDate')) == '') ){
                        
                            //validator.isRequiredValueEmpty('「法定代理人姓名」', $('grdName'));
                            //validator.isRequiredValueEmpty('「法定代理人身分證號」', $('grdIdnNo'));
                            //validator.isRequiredValueEmpty('「法定代理人出生日期」', $('grdBrDate'));
                        }
                    }
                }

                if( $('benBrDate') != null && $F('benBrDate') !== ''){
                    validator.isSmallerThanOrEqual({name : '「遺屬出生日期」', value : $F('benBrDate')},
                                                   {name : '「系統日期」', value : nowDate});
                    switch ($F('benEvtRel')) {
                        case '3':
                            validator.isSmallerThan({name : '「遺屬出生日期」', value : $F('benBrDate')},
                                                    {name : '「事故者出生日期」', value : evtBrDateChineseString});
                            break;
                        case '4':
                            validator.isGreaterThan({name : '「遺屬出生日期」', value : $F('benBrDate')},
                                                    {name : '「事故者出生日期」', value : evtBrDateChineseString});
                            break;
                        case '5':
                            validator.isSmallerThan({name : '「遺屬出生日期」', value : $F('benBrDate')},
                                                    {name : '「事故者出生日期」', value : evtBrDateChineseString});
                            break;
                        case '7':
                            validator.isGreaterThan({name : '「遺屬出生日期」', value : $F('benBrDate')},
                                                    {name : '「事故者出生日期」', value : evtBrDateChineseString});
                            break;
                        default:
                            break;
                    }
                }

                if( $('benDieDate') != null && $F('benDieDate') !== ''){
                    validator.isChineseDateFormatValid('「遺屬死亡日期」', $F('benDieDate'));
                    validator.isGreaterThanOrEqual({name : '「遺屬死亡日期」', value : $F('benDieDate')},
                                                   {name : '「遺屬出生日期」', value : $F('benBrDate')});
                    validator.isSmallerThanOrEqual({name : '「遺屬死亡日期」', value : $F('benDieDate')},
                                                   {name : '「系統日期」', value : nowDate});
                }

                if(frm['idnChkNote']){
                    var oldIdnChkYm = "";
                    if(Trim($("oldIdnChkYm").value)!=""){
                        oldIdnChkYm = changeDateType($("oldIdnChkYm").value+"01").substring(0 , 5);
                    }else{
                        oldIdnChkYm = today.substring(0 , 5);
                    }
                
                    if (frm['idnChkNote'][0].checked) {
                        if(parseNumber(oldIdnChkYm) > parseNumber(today.substring(0 , 5))){
                            validator.addErrorMsg('「身分查核年月」大於系統年月，不得點選自動遞延13個月。');
                            initIdnChkNote();
                        }                   
                    }
                    else if(frm['idnChkNote'][1].checked){
                        if(Trim($("idnChkYear").value) == ""){
                            validator.addErrorMsg('「身分查核年月年度」：為必輸欄位。');
                            initIdnChkNote();
                        }
                        if(Trim($("idnChkMonth").value) == ""){
                            validator.addErrorMsg('「身分查核年月月份」：為必輸欄位。');
                            initIdnChkNote();
                        }
                        if(Trim($("idnChkYear").value) != "" && Trim($("idnChkMonth").value) != ""){
                            if (!(parseNumber("" + $F("idnChkYear") + $F("idnChkMonth")) >= parseNumber(today.substring(0 , 5)) && parseNumber("" + $F("idnChkYear") + $F("idnChkMonth")) <= parseNumber(calMonth(today, 13).substring(0 , 5)))) {
                                validator.addErrorMsg('「身分查核年月」需大於等於系統年月，且小於等於系統年月+13個月。');
                                initIdnChkNote();
                            }
                        }
                    }
                }

                validator.isSmallerThanOrEqual({name : '「法定代理人出生日期」', value : $F('grdBrDate')},
                                               {name : '「系統日期」', value : nowDate});
                validator.isSmallerThanOrEqual({name : '「代辦人出生日期」', value : $F('assignBrDate')},
                                               {name : '「系統日期」', value : nowDate});

                if($('adoptDate')!=null && $F('adoptDate') != ''){
                    validator.isSmallerThanOrEqual({name : '「收養日期」', value : $F('adoptDate')},
                                                   {name : '「系統日期」', value : nowDate});
                    validator.isGreaterThanOrEqual({name : '「收養日期」', value : $F('adoptDate')  },
                                                   {name : '「遺屬出生日期」', value : $F('benBrDate') });
                }
                
                if( $('abanApSym') != null && $F('abanApSym') != ''){
                    validator.isGreaterThanOrEqual({name : '「放棄請領起始年月」', value : ($F('abanApSym')).substring(0, 5)  },
                                                   {name : '「事故者死亡年月」', value : evtDieDate.substring(0, 5)});
                }

                if($('relatChgDate') != null && $F('relatChgDate') != ''){
                    validator.isSmallerThanOrEqual({name : '「親屬關係變動日期」', value : $F('relatChgDate')},
                                                   {name : '「系統日期」', value : nowDate});
                    validator.isGreaterThanOrEqual({name : '「親屬關係變動日期」', value : $F('relatChgDate')},
                                                   {name : '「遺屬出生日期」', value : $F('benBrDate')});
                }

                if ($('monIncome') != null && $F('monIncome') != '') {
                    if (! $F('monIncome').match(/^[0-9]+$/)) {
                        validator.addErrorMsg('「每月工作收入」：不得為負，且需為數字');
                    }
                }


                if($('benMissingSdate') != null && $F('benMissingSdate') != ''){
                    validator.isSmallerThanOrEqual({name : '「遺屬失蹤日期起」', value : $F('benMissingSdate')},
                                                   {name : '「系統日期」', value : nowDate});
                }

                if($('benMissingEdate') != null && $F('benMissingEdate') != ''){
                    validator.isRequiredValueEmpty('「遺屬失蹤日期起」', $('benMissingSdate'));
                    validator.isSmallerThanOrEqual({name : '「遺屬失蹤日期迄」', value : $F('benMissingEdate')},
                                                   {name : '「系統日期」', value : nowDate});
                    if($('benMissingSdate') != null && $F('benMissingSdate') != ''){
                        validator.isGreaterThanOrEqual({name : '「遺屬失蹤日期迄」', value : $F('benMissingEdate')},
                                                       {name : '「遺屬失蹤日期起」', value : $F('benMissingSdate')});
                    }
                }

                if($('prisonSdate') != null && $F('prisonSdate') != ''){
                    validator.isSmallerThanOrEqual({name : '「監管日期起」', value : $F('prisonSdate')},
                                                   {name : '「系統日期」', value : nowDate});
                }

                if($('prisonEdate') != null && $F('prisonEdate') != ''){
                    validator.isRequiredValueEmpty('「監管日期起」', $('prisonSdate'));
                    validator.isSmallerThanOrEqual({name : '「監管日期迄」', value : $F('prisonEdate')},
                                                   {name : '「系統日期」', value : nowDate});
                    if($('prisonSdate') != null && $F('prisonSdate') != ''){
                        validator.isGreaterThanOrEqual({name : '「監管日期迄」', value : $F('prisonEdate')},
                                                       {name : '「監管日期起」', value : $F('prisonSdate')});
                    }
                }

                if($('interdictMk') != null && $('interdictMk').checked){
                    validator.isRequiredValueEmpty('「受禁治產(監護)宣告日期」', $('interdictDate'));
                    if($('repealInterdictDate') != null && $F('repealInterdictDate') != ''){
                        validator.isSmallerThanOrEqual({name : '「受禁治產(監護)宣告日期」', value : $F('interdictDate')},
                                                       {name : '「受禁治產(監護)撤銷日期」', value : $F('repealInterdictDate')});
                        validator.isSmallerThanOrEqual({name : '「受禁治產(監護)撤銷日期」', value : $F('repealInterdictDate')},
                                                       {name : '「系統日期」', value : nowDate});
                    }
                    
                   
                }
                if($('abanApplyMk') != null && $('abanApplyMk').checked){
                    validator.isRequiredValueEmpty('「放棄請領年月」', $('abanApSym'));
                }


                if($('interdictDate') != null && $F('interdictDate') != ''){
                    validator.isSmallerThanOrEqual({name : '「受禁治產(監護)宣告日期」', value : $F('interdictDate')},
                                                   {name : '「系統日期」', value : nowDate});
                }

                if($('marryDate') != null && $F('marryDate') != ''){
                    validator.isSmallerThanOrEqual({name : '「結婚日期」', value : $F('marryDate')},
                                                   {name : '「事故者死亡日期」', value : evtDieDate});
                    validator.isGreaterThanOrEqual({name : '「結婚日期」', value : $F('marryDate')},
                                                   {name : '「遺屬出生日期」', value : $F('benBrDate')} );
                    validator.isGreaterThan({name : '「結婚日期」', value : $F('marryDate')},
                                            {name : '「事故者出生日期」', value : evtBrDateChineseString});
                }

                if($('digaMyDate') != null && $F('digaMyDate') != ''){
                    validator.isGreaterThanOrEqual({name : '「再婚日期」', value : $F('digaMyDate')},
                                                   {name : '「遺屬出生日期」', value : $F('benBrDate')});
                    validator.isSmallerThanOrEqual({name : '「再婚日期」', value : $F('digaMyDate')},
                                                   {name : '「系統日期」', value : nowDate});
                    if($('marryDate') != null && $F('marryDate') != ''){
                        validator.isGreaterThanOrEqual({name : '「再婚日期」', value : $F('digaMyDate')},
                                                       {name : '「結婚日期」', value : $F('marryDate')});
                    }
                }

                //validator.isChineseDateFormatValid('「遺屬出生日期」', $F('benBrDate'));
                validator.isChineseDateFormatValid('「遺屬申請日期」', $F('appDate'));
                validator.isChineseDateFormatValid('「遺屬死亡日期」', $F('benDieDate') );
                validator.isChineseDateFormatValid('「親屬關係變動日期」', $F('relatChgDate'));
                validator.isChineseDateFormatValid('「收養日期」', $F('adoptDate'));
                validator.isChineseDateFormatValid('「遺屬失蹤日期起」', $F('benMissingSdate'));
                validator.isChineseDateFormatValid('「遺屬失蹤日期迄」', $F('benMissingEdate'));
                validator.isChineseDateFormatValid('「監管日期起」', $F('prisonSdate'));
                validator.isChineseDateFormatValid('「監管日期迄」', $F('prisonEdate'));
                validator.isChineseDateFormatValid('「結婚日期」', $F('marryDate'));
                validator.isChineseDateFormatValid('「再婚日期」', $F('digaMyDate'));
                validator.isChineseDateFormatValid('「放棄請領起始年月」', $F('abanApSym'));
                validator.isChineseDateFormatValid('「受禁治產(監護)宣告日期」', $F('interdictDate'));
                validator.isChineseDateFormatValid('「受禁治產(監護)撤銷日期」', $F('repealInterdictDate'));

                //validator.checkLength('「遺屬出生日期」', $F('benBrDate'), 7);
                validator.checkLength('「遺屬死亡日期」', $F('benDieDate'), 7);
                validator.checkLength('「遺屬申請日期」', $F('appDate'), 7);
                validator.checkLength('「親屬關係變動日期」', $F('relatChgDate'), 7);
                validator.checkLength('「收養日期」', $F('adoptDate'), 7);
                validator.checkLength('「遺屬失蹤日期起」', $F('benMissingSdate'), 7);
                validator.checkLength('「遺屬失蹤日期迄」', $F('benMissingEdate'), 7);
                validator.checkLength('「監管日期起」', $F('prisonSdate'), 7);
                validator.checkLength('「監管日期迄」', $F('prisonEdate'), 7);
                validator.checkLength('「結婚日期」', $F('marryDate'), 7);
                validator.checkLength('「再婚日期」', $F('digaMyDate'), 7);
                validator.checkLength('「放棄請領起始年月」', $F('abanApSym'), 5);
                validator.checkLength('「受禁治產(監護)宣告日期」', $F('interdictDate'), 7);
                validator.checkLength('「受禁治產(監護)撤銷日期」', $F('repealInterdictDate'), 7);

                if($('commTyp').value === '1'){
                    if (Trim($("commAddr").value) === "") {
                        validator.addErrorMsg('戶籍地住址無資料，請選擇「現住址」並輸入住址資料。');
                        $("commTyp").value = "2";
                        changeCommTyp();
                    }
                }
                if ($('commTyp').value === '2') {
                    validator.isRequiredValueEmpty('「郵遞區號」', $('commZip'));
                    validator.isRequiredValueEmpty('「地址」', $('commAddr'));
                }

                //如果在學有勾,但listSize為0,則提示警告訊息
                if ($('studMk') != null && $('studMk').checked && Trim($F('schoolCode')) == '') {
                    if (studtermListSize == 0) {
                        validator.addErrorMsg('請輸入「在學起迄年月」資料。');
                    }
                }
                //如果在學沒勾,但listSize不為0,則提示警告訊息
                if ($('studMk') == null || !$('studMk').checked) {
                    if (studtermListSize != 0) {
                        validator.addErrorMsg('已有「在學起迄年月」資料，不得取消「在學」勾選。');
                    }
                }
                
                //如果重殘有勾,但listSize為0,則提示警告訊息
                if ($('handicapMk') != null && $('handicapMk').checked) {
                    if (handicaptermListSize == 0) {
                        validator.addErrorMsg('請輸入「重殘起迄年月」資料。');
                    }
                }
                //如果重殘沒勾,但listSize不為0,則提示警告訊息
                if ($('handicapMk') == null || !$('handicapMk').checked) {
                    if (handicaptermListSize != 0) {
                        validator.addErrorMsg('已有「重殘起迄年月」資料，不得取消「重殘起訖年月」勾選。');
                    }
                }
                
                if (Trim($("schoolCode").value)!="" && studResult == "Y"){
                	validator.addErrorMsg('在學起迄年月不可大於最大給付年月。');
        		}                 

                if(Trim($F('assignIdnNo')) != ''){
                    validator.isRequiredValueEmpty('「代辦人姓名」', $('assignName'));
                }
                //if(Trim($F('assignName')) != ''){
                //    validator.isRequiredValueEmpty('「代辦人身分證號」', $('assignIdnNo'));
                //}
                if(Trim($F('assignBrDate')) != ''){
                    validator.isRequiredValueEmpty('「代辦人姓名」', $('assignName'));
                    validator.isRequiredValueEmpty('「代辦人身分證號」', $('assignIdnNo'));
                }

                if (Trim($F("payTyp")) === '1' || Trim($F("payTyp")) === '2') {

                    var payBankIdBranchId = $F('payBankId') + $F('branchId');
                    validator.isRequiredValueEmpty('「帳號(前)」', $('payBankId'));
                    validator.isRequiredValueEmpty('「帳號(前)」', $('branchId'));
                    validator.isRequiredValueEmpty('「帳號(後)」', $('payEeacc'));
                    validator.checkLength('「帳號(前)局號」', $F('payBankId'), 3);
                    validator.checkLength('「帳號(前)分支代號」', $F('branchId'), 4);
                    validator.isNumericValue('「帳號(後)」', $F('payEeacc'));
                    validator.isRequiredValueEmpty('「戶名」', $('accName'));
                    validator.checkLength('當「給付方式」為1或2時,「受款人身份證號」', $F('benIdnNo'), 10);

                    if (Trim($F('payTyp')) === '1') {
                        if (payBankIdBranchId.substr(0, 3) === '700') {
                            validator.addErrorMsg('「帳號前三碼」：不可為700。');
                        }
                    }

                    if (Trim($F('payTyp')) === '2') {
                        if (payBankIdBranchId !== "7000010" && payBankIdBranchId !== "7000021") {
                            validator.addErrorMsg('「帳號(前)」：限定只能輸入700-0010或700-0021。');
                        } else {
                            if (isValidPayEeacc(payBankIdBranchId, Trim($F("payEeacc")))) {
                                validator.addErrorMsg('「帳號」格式錯誤，請重新確認。');
                            }

                            if (payBankIdBranchId === "7000010") {
                                validator.checkLength('「帳號(後)」', $F('payEeacc'), 8);
                            }
                            if (payBankIdBranchId === '7000021') {
                                validator.checkLength('「帳號(後)」', $F('payEeacc'), 14);
                            }
                        }
                    }
                } else if (Trim($F("payTyp")) === "5" || Trim($F("payTyp")) === "6") {
                    validator.isRequiredValueEmpty('「金融機構名稱」', $('bankName'));
                    if (!validator.isRequiredValueEmpty('「帳號」', $('payAccount'))) {
                        validator.isAlphaNumericValue('「帳號」', $F('payAccount'));
                    }
                }
            }

            if(validator.getErrorMsgs().length !== 0) {
                alert(validator.getErrorMsgsAsString());
                return false;
            } else {
                if(chkEvtBrDate()){
                	$('payTyp').disabled = false;
                	return true;
                }else{
                	return false;
                }
            }

        }

        function setSelectedCountryOnBenNationCodeChange() {
            var frm = document.forms['SurvivorPayeeDataUpdateDetailForm'];
            if ($('benNationCode') !== null) {
                var nationCode = $F('benNationCode');
                frm['benNationCodeOption'].selectedIndex = 0;
                var benNationList = frm['benNationCodeOption'];
                for (var i = 0; i < benNationList.options.length; i++) {
                    if ((benNationList.options[i].value) == nationCode) {
                        benNationList.options[i].selected = true;
                    }
                }
            }
        }
        
              <%-- 1030813 payTyp=1時tab跳過0000 --%>
        function tabChange(){

           if (Trim($("payTyp").value) == "1"){
             $("branchId").tabIndex = -1;
          }else{
             $("branchId").tabIndex = 0;
          }
      
        }
        
          <%-- 1030813 payTyp=1時tab跳過0000 --%>
       function autoTabChange(){

          if (Trim($("payTyp").value) == "1"){
             autotab($('payBankId'), $('payEeacc'));
          }else{
             autotab($('payBankId'), $('branchId'));
          }
      
       }

        function resetForm(){
            document.forms['SurvivorPayeeDataUpdateDetailForm'].reset();
            initAll();
        }
        
        // 學校代碼下拉選單變動
		function changeSchoolCodeOption(){
		    $("schoolCode").value = $("schoolCodeOption").value;    
        }	
        	
        // 學校代碼下拉選單變動
		function changeSchoolCode(){
		    $("schoolCodeOption").value = $("schoolCode").value;    
        }
        
	    <%-- 學校代碼查詢 --%>
	    function doQuerySchool(){
	        var argsObj = new Object();
	        
	        argsObj.schoolCode = $F("schoolCode");

	        var res = window.showModalDialog('<c:url value="/bamo0d28/bamo0d285q.jsp"/>', argsObj, 'dialogWidth:560px;dialogHeight:500px;status:no');
	        
	        if (res != null) {
	            $("schoolCode").value = res.schoolCode;
	            $("schoolCodeOption").value = res.schoolCode;		        
	        }
	    }          

        function initAll(){
            var frm = document.forms['SurvivorPayeeDataUpdateDetailForm'];
            $('commTyp').value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.commTyp}" />';
            $('idnChkNote').value= '<c:out value="${SurvivorPayeeDataUpdateDetailForm.idnChkNote}" />';
            $('monIncome').value= '<c:out value="${SurvivorPayeeDataUpdateDetailForm.monIncome}" />';
            $('abanApSym').value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.abanApSym}" />';
            $('ableApsYm').value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.ableApsYm}" />';
            $('savingMk').value = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.savingMk}" />';

            //學校代碼
		    changeSchoolCode();
            tabChange();
            //檢核生日錯誤檔
            checkIdnoExist();
            toggleAbanApplyMk();
            //受禁治產宣告
            initInterdictDateDisplay();
            initBenNationTpe();
            initPayCategory();
            initCommTyp();
            initFieldByBenEvtRel();
            toggleInterdictDateDisplay();
            toggleMonIncomeDisplay();
            toggleAtSchoolDisplay();
            toggleHandicapDisplay();
            // 身份查核年月 Q1==true才會出現
            var Q1 = '<c:out value="${SurvivorPayeeDataUpdateQueryCaseQ1}" />';
            if(Q1=="true"){
                $("idnChkNoteContent").style.display="inline";
                initIdnChkNote();
            } else {
                $("idnChkNoteContent").style.display="none";
            }
            // 具名領取
            var disableCoreceive = '<c:out value="${SurvivorPayeeDataUpdateQueryCaseQ2}" />';
            if(disableCoreceive == "true"){
                $("payCategory2").disabled = true;
                $("accSeqNo").disabled = true;
            }
            var toHideCoreceiveNameListDropDownMenu = '<c:out value="${toHideCoreceiveNameListDropDownMenu}" />';
            if(toHideCoreceiveNameListDropDownMenu == "true"){
                $("coreceiveSection").style.display="none";
            }

            DefaultMarritalStatusSetter.initMarritalStatus();
            Event.observe('benEvtRel', 'change', DefaultMarritalStatusSetter.setDefaultMarritalStatus, false);
            Event.observe('benBrDate', 'blur', DefaultMarritalStatusSetter.setDefaultMarritalStatus, false);
            Event.observe('interdictMk', 'click', toggleInterdictDateDisplay, false);
            
            
            //if ($("closeDate").value != '' && $('closeDate') != null) {
            var sCloseDate = '<c:out value="${SurvivorPayeeDataUpdateDetailForm.closeDate}" />';                        
			if (sCloseDate != "") {                       
	            initAllFields();
            }
            
            //強迫不合格註記
            if($('compelMk').checked){
                $("btnCompelData").style.display="inline";
            }else{
                $("btnCompelData").style.display="none";
            }
        }
        
        //變更強迫不合格註記時
        function toggleCompelDataDisplay(){
            if($("compelMk").checked){
                $("compelMk").value = 'Y';
                $("btnCompelData").style.display="inline";
            }else{
                $("compelMk").value = "";
                $("btnCompelData").style.display="none";
            }
        }

                // Ajax for 取得 出生日期錯誤參數 確認是否有此筆資料P120436303 0480229  $("benIdnNo").value,$("benBrDate").value
    		    function checkIdnoExist() {   
        		    if(isNaN($("benBrDate").value) == false){
        		    updateCommonAjaxDo.checkIdnoExist($("benIdnNo").value,$("benBrDate").value,checkIdnoExistResult);
        		    }
    		    }
    		    function checkIdnoExistResult(idnoExist) {  
    		       $("idnoExist").value = idnoExist;
    		    }
    		    
    		       //檢核事故者出生日期 20121220 邏輯修改
            function isValidEvtDateTrue() {   
            var evtBrDate = $("benBrDate").value;

            if(isValidDate($("benBrDate").value) == false){
        
            if($("idnoExist").value == null || $("idnoExist").value == "" || $("idnoExist").value == "null"){
            alert("輸入之「受款人出生日期」錯誤，請重新輸入");
            return false;
            }else{
              return true;
            }  
            }else{
              return true;
            }
            }

             function chkEvtBrDate() {
            //檢核事故者出生日期  是否為數字 及 年月格式
        
            var msg = ""; 
        
            if($("benBrDate").value.length < 7){
                        msg += '輸入之「受款人出生日期」錯誤，請重新輸入\r\n';
                        $("benBrDate").focus();
            } 
            if(isNaN($("benBrDate").value)){
                        msg += '輸入之「受款人出生日期」錯誤，請重新輸入\r\n';
                        $("benBrDate").focus();
                       
            }
            if($("benBrDate").value.length == 7){
               var chkMonth = $("benBrDate").value.substring(3,5);
               var chkDay   = $("benBrDate").value.substring(5,7);
                   if(chkMonth > 12 || chkDay > 32){
                        msg += '輸入之「受款人出生日期」錯誤，請重新輸入\r\n';
                        $("benBrDate").focus();
               }
            }else if($("benBrDate").value.length == 8){
               var chkfrist = $("benBrDate").value.substring(0,1);
               var chkMonth = $("benBrDate").value.substring(4,6);
               var chkDay   = $("benBrDate").value.substring(6,8);
               if(chkfrist != "-"){
                        msg += '輸入之「受款人出生日期」錯誤，請重新輸入\r\n';
                        $("benBrDate").focus();
                       
               }
               if(chkMonth > 12 || chkDay > 32){
                        msg += '輸入之「受款人出生日期」錯誤，請重新輸入\r\n';
                        $("benBrDate").focus();
               }
            }        
        
        
            if(msg != ""){
                alert(msg);
                return false;
            }else{    
                if(isValidEvtDateTrue()){
                return true;
                }else{
                return false;
                }
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
				if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[0].checked==true){
					if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
						alert('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
					}
				}else if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[1].checked==true){
					if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
						alert('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
					}
				}
			}
			}
		}
     	// Added by EthanChen 20200115 [End]
	 
        Event.observe(window, 'load', initAll, false);
  	</script>
</head>

<body scroll="no">
<div id="content">
  <%@ include file="/includes/ba_header.jsp"%>
  <%@ include file="/includes/ba_menu.jsp"%>
  
    <div id="main" class="mainBody">
    <html:form action="/survivorPayeeDataUpdateDetail" method="post" onsubmit="return validateSurvivorPayeeDataUpdateDetailForm(this);">
      <fieldset>

      <legend>&nbsp;遺屬年金受款人資料更正&nbsp;</legend>
        <div align="right" id="showtime">網頁下載時間：<func:nowDateTime /></div>
        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
        <tr>
          <td align="right">
            <bean:define id="payeeData" name="<%=ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_CASE%>"/>
          	<html:hidden styleId="page" property="page" value="1" />
			<html:hidden styleId="method" property="method" value="" />
			<html:hidden styleId="baappexpandId" property="baappexpandId"/>
			<html:hidden styleId="baappbaseId" property="baappbaseId"/>
			<html:hidden styleId="idnoExist" property="idnoExist"/>
			<input type="hidden" id="origPayBankIdBranchId" name="origPayBankIdBranchId" value="<c:out value="${payeeData.payBankIdBranchId}" />"/>
            <input type="hidden" id="origPayEeacc" name="origPayEeacc" value="<c:out value="${payeeData.payEeacc}" />"/>
            <input type="hidden" id="origSpecialAcc" name="origSpecialAcc" value="<c:out value="${payeeData.specialAcc}" />"/>
            <acl:checkButton buttonName="btnSave">
                <c:if test='${empty SurvivorPayeeDataUpdateDetailForm.closeDate}'>            
				    <input name="btnSave" type="button" class="button" value="確　認" onclick="checkIdnoExist();$('page').value='1'; $('method').value='doConfirm'; if(chkSpecialAcc()){if (document.SurvivorPayeeDataUpdateDetailForm.onsubmit() && checkRequireFields()){
					   																															if($('payTyp').value=='4'){if(confirm('非行動不便之扣押戶，給付方式不得選擇匯票郵寄申請人，請重新選擇給付方式。按下【確定】時，不存檔並回到畫面重新選擇「給付方式」；按下【取消】時，確定「給付方式」為匯票郵寄並繼續存檔作業。')){return false;}else{document.SurvivorPayeeDataUpdateDetailForm.submit();}
																																				}else{document.SurvivorPayeeDataUpdateDetailForm.submit();}}else{return false;}}else{return false;}" />&nbsp;
                </c:if>
                <c:if test='${not empty SurvivorPayeeDataUpdateDetailForm.closeDate}'>
				    <input name="btnSave" type="button" class="button" value="確　認" disabled="disabled"/>&nbsp;                      　                  		  
                </c:if>                  
            </acl:checkButton>
			<acl:checkButton buttonName="btnBack">
				<input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='3'; $('method').value='doBackList'; document.SurvivorPayeeDataUpdateDetailForm.submit();" />
			</acl:checkButton>
          </td>
        </tr>

        <tr>
          <td>
            <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
              <tr>
                <td width="25%"><span class="issuetitle_L_down">受理編號：</span>
                	<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].apNoStrDisplay}" />
                	<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].sysCode}" />
                </td>
                <td width="25%"><span class="issuetitle_L_down">給付別：</span>
                	<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].payCode}" />
                </td>
                <td width="25%"><span class="issuetitle_L_down">申請日期：</span>
                	<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].appDateChineseString}" />
                 </td>
                 <td width="25%"><span class="issuetitle_L_down">事故日期：</span>
                 	<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtJobDateStr}" />
                 </td>
               </tr>
               <tr>
                 <td colspan="2"><span class="issuetitle_L_down">事故者姓名：</span>
                 	<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtName}" />
                 </td>
                 <td width="25%"><span class="issuetitle_L_down">事故者身分證號：</span>
                 	<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtIdnNo}" />
                 </td>
                 <td width="25%"><span class="issuetitle_L_down">事故者出生日期：</span>
                 	<c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtBrDateChineseString}" />
                 </td>
                 </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td align="center" class="table1">          
            <table width="98%" cellpadding="3" cellspacing="4" class="px13">
              <tr>
              	<td colspan="3" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;受款人資料</td>

              </tr>
              <tr>
				<td id="iss">
					&nbsp;&nbsp;<span class="needtxt">＊</span>
					<span class="issuetitle_L_down">關　係：</span>															
                    <html:select property="benEvtRel" styleId="benEvtRel" styleClass="formtxt" onchange="toggleFieldAfterRelChange();" >
					<html:option value="">請選擇</html:option>
						<html:options collection="<%=ConstantKey.PAYEE_RELATIION_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
					</html:select>									
				</td>
				<td id="iss">
                	<div id="ableApSymSection">
                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">得請領起月：</span>                    	                  	
                        <html:select property="ableApsYm" styleId="ableApsYm" styleClass="formtxt">
                        	<html:option value="">請選擇</html:option>
                        	<logic:notEmpty name="<%=ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_ABLEAPSYM_OPTION_LIST%>">
                        	<logic:iterate id="ableApsYmList" name="<%=ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_ABLEAPSYM_OPTION_LIST%>" indexId="i">                                                                        
                        		<html:option value="${ableApsYmList}"><c:out value="${ableApsYmList}" /></html:option>                                                
                        	</logic:iterate>
                        	</logic:notEmpty>
                        </html:select>                                                
                	</div>
                	&nbsp;
                </td>
                <td id="iss">
                    <html:checkbox styleId="compelMk" property="compelMk" value="Y" onclick="toggleCompelDataDisplay();"/>
                    <span class="issuetitle_L_down">強迫不合格</span>
                    <span id="compel">
                        <input name="btnCompelData" type="button" class="button_120" value="不合格年月維護" onclick="$('page').value='1'; $('method').value='doPrepareMaintainCompelData'; document.SurvivorPayeeDataUpdateDetailForm.submit();" style="display: none"/>
                    </span>
                </td>
              </tr>
              <tr>
                <td id="iss" colspan="2">
                	&nbsp;&nbsp;<span class="needtxt">＊</span>
                	<span class="issuetitle_L_down">遺屬姓名：</span>                 	
					<html:text property="benName" styleId="benName" styleClass="textinput" size="50" maxlength="50" onblur="this.value=asc(this.value);"/>				
                </td>
				<td id="iss">
                    <span class="needtxt">＊</span>
					<span class="issuetitle_L_down">遺屬申請日期：</span>					
					<html:text property="appDate" styleId="appDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>                    					
                </td>
			  </tr>
              <tr>
                <td id="iss">
                	&nbsp;&nbsp;<span class="needtxt">＊</span>
                	<span class="issuetitle_L_down">遺屬身分證號(保險證號)：</span>
                	<html:text property="benIdnNo" styleId="benIdnNo" styleClass="textinput" size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();checkIdnoExist();autoForeignBenSex();" />	
				</td>
                <td id="iss">
                    &nbsp;&nbsp;<span class="needtxt">＊</span>
                	<span class="issuetitle_L_down">遺屬出生日期：</span>
                	<html:text property="benBrDate" styleId="benBrDate" styleClass="textinput" size="10" maxlength="8" onblur="this.value=asc(this.value);checkIdnoExist();"/>
                </td>
				<td id="iss">
                    &nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">遺屬死亡日期：</span>
                	<html:text property="benDieDate" styleId="benDieDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                </td>
              </tr>
              <tr>
                <td width="34%" id="iss">
					&nbsp;&nbsp;<span class="needtxt">＊</span>
					<span class="issuetitle_L_down">國籍別：</span>
                    <span id="spanBenNationTyp" name="spanBenNationTyp" class="formtxt">
                    	<html:radio property="benNationTyp" value="1" onclick="$('benNationTyp').value='1'; changeBenNationTpe();" />本國
                    	<html:radio property="benNationTyp" value="2" onclick="$('benNationTyp').value='2'; changeBenNationTpe();" />外籍
                    </span>                	
				</td>
                <td width="33%" id="iss">
					<span id="sexContent">
                        &nbsp;&nbsp;<span class="needtxt">＊</span>
						<span class="issuetitle_L_down">性別：</span>
						<span id="spanBenSex" name="spanBenSex" class="formtxt"> 
						    <html:radio property="benSex" value="1" onclick="$('benSex').value='1';" />男
						    <html:radio property="benSex" value="2" onclick="$('benSex').value='2';" />女 
						</span>
					</span>
				</td>
                <td width="33%" id="iss">
					<span id="nationalityContent">
    					<span class="needtxt">＊</span>
    					<span class="issuetitle_L_down">國籍：</span>
    						<html:text property="benNationCode" styleId="countryId" styleClass="textinput" size="2" maxlength="3" readonly = "true" onblur="this.value=asc(this.value).toUpperCase();" onfocus="moveFocus('benNationCodeOption');"/>
    					<label>
    						<html:select property="benNationCodeOption" onchange="$('benNationCode').value=$('benNationCodeOption').value">
    							<html:option value="">請選擇</html:option>
    							<html:options collection="<%=ConstantKey.COUNTRYID_OPTION_LIST%>" property="countryId" labelProperty="cname" />
    						</html:select>
    					</label>
					</span>
				</td>
              </tr>
			  <tr>
                <td id="iss" colspan="3">
                	<div id="idnChkNoteContent">
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<span class="issuetitle_L_down">身分查核年月
                        <c:if test='${not empty SurvivorPayeeDataUpdateDetailForm.idnChkYear}'>
                            (<c:out value="${SurvivorPayeeDataUpdateDetailForm.idnChkYear}"/>年<c:out value="${SurvivorPayeeDataUpdateDetailForm.idnChkMonth}" />月)
                        </c:if>
                    ：</span>
                	<span id="spanIdnChkNote" name="spanIdnChkNote" class="formtxt">
                		<html:radio styleId="idnChkNote1" property="idnChkNote" value="1" onclick="$('idnChkNote').value='1'; chkIdnChkNote();"/>自動遞延13個月
                		<html:radio styleId="idnChkNote2" property="idnChkNote" value="2" onclick="$('idnChkNote').value='2'; chkIdnChkNote();"/>手動調整
                		<div id="chkForeigner" style="display: inline;">
                			<html:text property="idnChkYear" styleId="idnChkYear" styleClass="textinput" size="3" maxlength="3" onblur="this.value=asc(this.value);"/>年
                			<html:text property="idnChkMonth" styleId="idnChkMonth" styleClass="textinput" size="2" maxlength="2" onblur="this.value=asc(this.value);"/>月
                		</div>
                        <html:hidden styleId="oldIdnChkYm" property="oldIdnChkYm" />
                        <html:hidden styleId="oldIdnChkNote" property="oldIdnChkNote" />
                        <input type="hidden" name="oldIdnChkYear" value="<c:out value="${SurvivorPayeeDataUpdateDetailForm.idnChkYear}" />" >
                        <input type="hidden" name="oldIdnChkMonth" value="<c:out value="${SurvivorPayeeDataUpdateDetailForm.idnChkMonth}" />" >
                	</span>
                	</div>
                </td>
              </tr>
              <tr id="marrySection">
			    <td id="iss">
                    <div>
                        &nbsp;&nbsp;<span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">結婚日期：</span>
                        <html:text property="marryDate" styleId="marryDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                    </div>
                </td>
			  	<td id="iss" colspan="2">
                    <div>
			  		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">再婚日期：</span>
                  	    <html:text property="digaMyDate" styleId="digaMyDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                    </div>
                </td>
			  </tr>
			  <tr id="raiseSection">
                  <td id="iss">
                    <div>
                	    <html:checkbox styleId="raiseChildMk" property="raiseChildMk" value="Y"/>
                	    <span class="issuetitle_L_down">配偶扶養</span>
                    </div>
				 </td>
                 <td id="iss" colspan="2">
			        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">收養日期：</span>
              	    <html:text property="adoptDate" styleId="adoptDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                 </td>
			  </tr>
              <tr id="raiseEvtMkSection">
                  <td id="iss" colspan="3">
                    <div>
          		        <html:checkbox styleId="raiseEvtMk" property="raiseEvtMk" value="Y"/>
          		        <span class="issuetitle_L_down">被保險人扶養</span>
                    </div>
              	</td>
              </tr>
              <tr>
                <td id="iss"  colspan="3">
					<html:checkbox styleId="monIncomeMk" property="monIncomeMk" value="Y" onclick="toggleMonIncomeDisplay();"/>
					<span class="issuetitle_L_down">每月工作收入註記</span>
                    <div id="monIncomeSection" class="formtxt">
                    	：<html:text property="monIncome" styleId="monIncome" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);"/>&nbsp;元
                    </div>
                </td>
              </tr>
              <tr id="studMkSection">                
			    <td id="iss" >
                   
			    	    <html:checkbox styleId="studMk" property="studMk" value="Y" onclick="toggleAtSchoolDisplay();"/>
			    	    <span class="issuetitle_L_down">在　學</span>
                        <span id="atSchool">
                            <input name="btnStudterm" type="button" class="button_120" value="在學起迄年月維護" onclick="$('page').value='1'; $('method').value='doPrepareMaintainStudterm'; document.SurvivorPayeeDataUpdateDetailForm.submit();" />
                        </span>
                 
				</td>
				<td id="iss" colspan="2">
                    <div id="schoolCodeContent">
			           <span class="issuetitle_L_down">學校代碼：</span>
			           <html:text property="schoolCode" styleId="schoolCode" styleClass="textinput" size="7" maxlength="4" onblur="this.value=asc(this.value);changeSchoolCode();" />
			           <html:select property="schoolCodeOption" styleId="schoolCodeOption" styleClass="formtxt" tabindex="14" onchange="changeSchoolCodeOption();">
			           <html:option value="">請選擇</html:option>
			           <html:options collection="<%=ConstantKey.SCHOOLCODE_OPTION_LIST%>" property="codeNo" labelProperty="codeString" />
			           </html:select>
			           <input name="btnQuerySchool" type="button" class="button_120" value="學校名稱查詢" onclick="doQuerySchool();">
			        </div>
			        &nbsp;
			    </td>
			  </tr>
              <tr id="incapableSection">
			  	<td id="iss">
                    <div>
          		        <html:checkbox styleId="handicapMk" property="handicapMk" value="Y" onclick="toggleHandicapDisplay();"/>
          		        <span class="issuetitle_L_down">重殘起迄年月</span>
          		        <span id="handicap">
                            <input name="btnHandicapterm" type="button" class="button_120" value="重殘起迄年月維護" onclick="$('page').value='1'; $('method').value='doPrepareMaintainHandicapterm'; document.SurvivorPayeeDataUpdateDetailForm.submit();" />
                        </span>
                    </div>
              	</td>
                <td id="iss" colspan="2">
                    <span>
                       <html:checkbox styleId="interdictMk" property="interdictMk" value="Y"/>
                        <span class="issuetitle_L_down">受禁治產(監護)宣告</span>
                        <div id="interdictDateSection" style="display: none;">
                            <span class="issuetitle_L_down">-&nbsp;宣告／撤銷日期：</span>
                            <html:text property="interdictDate" styleId="interdictDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                            &nbsp;／&nbsp;<html:text property="repealInterdictDate" styleId="repealInterdictDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                        </div>
                    </span>
				</td>
              </tr>
              <%--<span id="interdictMkSection">--%>
                          <%--<span class="issuetitle_L_down">&nbsp;&nbsp;　受禁治產審查註記：</span>--%>
                          <%--<html:select property="interdictMk" styleClass="formtxt" onchange="toggleInterdictDateDisplay();">--%>
                              <%--<option value="" selected>請選擇...</option>--%>
                              <%--<option value="Y">Y-受禁治產宣告</option>--%>
                              <%--<option value="N">N-撤銷禁治產宣告</option>--%>
                          <%--</html:select>--%>
                      <%--</span>--%>
                    <%--<span id="interdictDateSection">--%>
                        <%--<div id="interdictDateDesc" class="issuetitle_L_down">受禁治產宣告日期：</div>--%>
                    	    <%--<html:text property="interdictDate" styleId="interdictDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>--%>
                    <%--</span>--%>
			  <tr>
				  <td id="iss">
                      <html:checkbox styleId="abanApplyMk" property="abanApplyMk" value="Y" onclick="toggleAbanApplyMk();"/>
                      <span class="issuetitle_L_down">放棄請領</span>
                      <span id="abanApplyMkSection">
                      <span class="issuetitle_L_down">-&nbsp;放棄請領起始年月：</span>
                    	  <html:text property="abanApSym" styleId="abanApSym" styleClass="textinput" size="10" maxlength="5" onblur="this.value=asc(this.value);"/>
                      </span>	
				  </td>
                  <td id="iss" colspan="2">
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_R_down">遺屬失蹤起迄期間：</span>
                	  <html:text property="benMissingSdate" styleId="benMissingSdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
					  &nbsp;~&nbsp;<html:text property="benMissingEdate" styleId="benMissingEdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
				  </td>
			  </tr>
			  <tr>		
                <td id="iss">
			    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_R_down">親屬關係變動日期：</span>
					<html:text property="relatChgDate" styleId="relatChgDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
				</td>
				<td id="iss" colspan="2">
				    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_R_down">監管起迄期間：</span>
                	<html:text property="prisonSdate" styleId="prisonSdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                    &nbsp;~&nbsp;<html:text property="prisonEdate" styleId="prisonEdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
				</td>
			  </tr>
              <tr>
                <td colspan="3" id="iss">
                <html:hidden property="accRel" styleId="accRel" />
                &nbsp;&nbsp;<span class="needtxt">＊</span>
                <span class="issuetitle_L_down">給付方式：</span>
                <span id="spanPayCategory" name="spanPayCategory" class="formtxt">
                	<html:radio styleId="payCategory1" property="payCategory" value="1" onclick="$('payCategory').value='1';changePayCategory();"/>本人領取
                	<html:select property="payTyp" styleId="payTyp" styleClass="formtxt" onchange="changePayType();" onblur="tabChange();">
                		<html:option value="">請選擇</html:option>
                	<html:options collection="<%=ConstantKey.PAYTYP_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                	</html:select>
                    <div id="coreceiveSection" style="display: inline;">
                	<html:radio styleId="payCategory2" property="payCategory" value="2" onclick="$('payCategory').value='2';changePayCategory();"/>具名領取
                	<html:select property="accSeqNo" styleId="accSeqNo" styleClass="formtxt">
                		<html:option value="">請選擇</html:option>
                		<logic:notEmpty name="<%=ConstantKey.BEN_OPTION_LIST%>">
                		<logic:iterate id="benList" name="<%=ConstantKey.BEN_OPTION_LIST%>">                                                                        
                			<html:option value="${benList.seqNo}" ><c:out value="${benList.benName}" /></html:option>                                                
                		</logic:iterate>
                		</logic:notEmpty>
                	</html:select>
                    </div>
                    <div id="specialAccContent" style="display: none;">   
                    	 <input type="checkbox" id="specialAccAfter" name="specialAccAfter" value="Y">專戶
                    </div> 
                </span>
              	</td>
              </tr>
              <tr>
                <td colspan="3" id="iss">
                <div id="accountContent1">
                	&nbsp;&nbsp;<span class="needtxt">＊</span>
                	<span class="issuetitle_L_down">帳　號：</span>
            		<html:text property="payBankId" styleId="payBankId" styleClass="textinput" size="1"  maxlength="3"  onchange="this.value=Trim($('payBankId').value)" onblur="this.value=asc(this.value);toggleAccNameOnAccountReset();" onkeyup="autoTabChange();"/>&nbsp;-
                    <html:text property="branchId"  styleId="branchId"  styleClass="textinput" size="1"  maxlength="4"  onchange="this.value=Trim($('branchId').value)"  onblur="this.value=asc(this.value);toggleAccNameOnAccountReset();" onkeyup="autotab($('branchId'), $('payEeacc'))"/>&nbsp;-
                    <html:text property="payEeacc"  styleId="payEeacc"  styleClass="textinput" size="14" maxlength="14" onchange="this.value=Trim($('payEeacc').value)"  onblur="this.value=asc(this.value);toggleAccNameOnAccountReset();"/>
                    <html:hidden styleId="payBankIdBranchId" property="payBankIdBranchId"/>
                </div>
                <div id="accountContent2">
                	&nbsp;&nbsp;<span class="needtxt">＊</span>
                	<span class="issuetitle_L_down">帳　號：</span>
                	<html:text property="payAccount" styleId="payAccount" styleClass="textinput" size="21" maxlength="21" onblur="this.value=asc(this.value).toUpperCase();"/>
                	&nbsp;&nbsp;
                	<span class="needtxt">＊</span>
                	<span class="issuetitle_L_down">金融機構名稱：</span>
                	<html:text property="bankName" styleId="bankName" styleClass="textinput" size="50" maxlength="120" onblur="this.value=asc(this.value).toUpperCase();"/>                                   　   
                </div>
                <div id="accountContent3">
                	&nbsp;&nbsp;
                	<span class="needtxt">＊</span>
                	<span class="issuetitle_L_down">戶　名：</span>
                	<html:text property="accName" styleId="accName" styleClass="textinput" size="50" maxlength="50" onchange="this.value=Trim($('accName').value)" onblur="this.value=asc(this.value).toUpperCase();"/>                
                </div>
                </td>
              </tr>
              <tr>
                <td id="iss">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">電　話1：</span>
                	<html:text property="tel1" styleId="tel1" styleClass="textinput" size="20" maxlength="20" />
				</td>
                <td id="iss" colspan="2">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">電　話2：</span>
                	<html:text property="tel2" styleId="tel2" styleClass="textinput" size="20" maxlength="20" />
				</td>
              </tr>
              <tr>
				<td id="iss">
                	&nbsp;&nbsp;<span class="needtxt">＊</span>
                	<span class="issuetitle_L_down">地　址：</span>
                	<span class="formtxt" id="rdocommtyp1">
                		<html:radio property="commTyp" value="1" onclick="$('commTyp').value='1'; changeCommTyp();"/>同戶籍地
                	</span>
                	<span class="formtxt" id="rdocommtyp2">
                		<html:radio property="commTyp" value="2" onclick="$('commTyp').value='2'; changeCommTyp();"/>現住址
                	</span>
                </td>
                <td id="iss" colspan="2">
                	<div class="formtxt" id="addContent1">
    					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">現住址：</span>
                		<html:text property="commZip" styleId="commZip" styleClass="textinput" size="6" maxlength="6" onchange="this.value=Trim($('commZip').value)" onblur="this.value=asc(this.value);"/>(郵遞區號)&nbsp;-
                		<html:text property="commAddr" styleId="commAddr" styleClass="textinput" size="72" maxlength="240" onchange="this.value=Trim($('commAddr').value)" onblur="this.value=asc(this.value);"/>
                	</div>
                	<div class="formtxt" id="addContent2">
                		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">現住址：</span>
                		<html:text property="commZip" styleId="commZip" styleClass="disabled" size="6" maxlength="6" onchange="this.value=Trim($('commZip').value)" onblur="this.value=asc(this.value);" onfocus="moveFocus('grdName');"/>(郵遞區號)&nbsp;-
                		<html:text property="commAddr" styleId="commAddr" styleClass="disabled" size="58" maxlength="240" onchange="this.value=Trim($('commAddr').value)" onblur="this.value=asc(this.value);" onfocus="moveFocus('grdName');"/>
                	</div>
                </td>
              </tr>
              <tr>
                  <td id="iss" colspan="3">
                      <div id="benMarrMkSection" style="display: none;">
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">婚姻狀況：</span>
                          <span id="spanBenMarrMk" name="spanBenMarrMk" class="formtxt">
                               <html:radio property="benMarrMk" value="Y" onclick="$('benMarrMk').value='Y';"/>已婚
                               <html:radio property="benMarrMk" value="N" onclick="$('benMarrMk').value='N';"/>未婚
                          </span>
                      </div>
                  </td>

              </tr>
              <tr>
              	  <td id="iss" colspan="3">
                      <div id="savingMkSection" style="display: none;">
                      <%--<html:checkbox styleId="savingMk" property="savingMk" value="Y"/>--%>
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">計息存儲：</span>
                          <html:select property="savingMk" styleClass="formtxt" onchange="changeSavingMk();" >
                               <option value="" selected>無計息存儲</option>
                               <option value="Y">Y-計息存儲存續期間</option>
                               <option value="T">T-結束計息存儲</option>
                          </html:select>
                      </div>
                      <div id="savingMkSectionSpace" style="display: none;">&nbsp;</div>
                  </td>
              </tr>
              <tr>
                  <td id="iss" colspan="3">
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">法定代理人姓名：</span>
                      <html:text property="grdName" styleId="grdName" styleClass="textinput" size="45" maxlength="50"/>
                  </td>
              </tr>
              <tr>
				<td id="iss">
				    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">法定代理人身分證號：</span>
                	<html:text property="grdIdnNo" styleId="grdIdnNo" styleClass="textinput" size="25" maxlength="20" onblur="this.value=Trim(asc(this.value).toUpperCase());"/>
				</td>
                <td id="iss" colspan="2">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">法定代理人出生日期：</span>
                	<html:text property="grdBrDate" styleId="grdBrDate" styleClass="textinput" size="10" maxlength="8"/>
                </td>                
              </tr>
              <tr>
                 <td id="iss" colspan="3">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">代辦人姓名：</span>
                	<html:text property="assignName" styleId="assignName" styleClass="textinput" size="45" maxlength="50"/>
                </td>
              </tr>
			  <tr>
				<td id="iss">
				    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">代辦人身分證號(統一編號)：</span>
                	<html:text property="assignIdnNo" styleId="assignIdnNo" styleClass="textinput" size="13" maxlength="10" onblur="this.value=Trim(asc(this.value).toUpperCase());"/>
				</td>
                <td id="iss" colspan="2">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">代辦人出生日期：</span>
                	<html:text property="assignBrDate" styleId="assignBrDate" styleClass="textinput" size="10" maxlength="8"/>
                </td>                
              </tr>
			  <tr>
                  <td id="iss">
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">結案原因：</span>
                      <html:select property="closeCause" styleId="closeCause" styleClass="formtxt">
                      <html:option value="">請選擇</html:option>
                      <html:options collection="<%=ConstantKey.SURVIVOR_CLOSE_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                      </html:select>
                  </td>		
				  <c:if test='${not empty SurvivorPayeeDataUpdateDetailForm.closeDate}'>　                  		  
                      <td id="iss" colspan="2">
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">結案日期：</span>
                          <html:text property="closeDate" styleId="closeDate" styleClass="textinput" size="10" maxlength="8"/>                      
                      </td>   
                  </c:if>                  
                  <c:if test='${empty SurvivorPayeeDataUpdateDetailForm.closeDate}'> 
                      <td id="iss" colspan="2">
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down"></span>
                      </td>                  
                  </c:if>                   
                                		  
                </tr>
			  <tr>
                  <td id="iss" colspan="3">
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">不合格原因：</span>
                      <html:select property="unqualifiedCause" styleId="unqualifiedCause" styleClass="formtxt">
                      <html:option value="">請選擇</html:option>
                      <html:options collection="<%=ConstantKey.SURVIVOR_UNQUALIFIEDCAUSE_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                      </html:select>
                  </td>           
                                		  
              </tr>
              <tr>
                  <td id="iss" colspan="3">
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <span class="issuetitle_L_down">來源受理編號：</span>
                      <c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].apnoFm}" />
                  </td>
              </tr>
			  <tr>
                  <td id="iss" colspan="3">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                          <tr>
                              <td valign="top" width="15%">
                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">遺屬符合註記：</span>
                              </td>
                              <td valign="top" align="left" width="85%" >
                              	  <c:forEach var="map" items="${qualifyMarkMap}">
               					  <c:out value="${map.key}" />：&nbsp;
               					  <bean:size id='qualifyMkListSize' collection="${map.value}" ></bean:size>
               					  <logic:iterate id='qualifyListHolder' collection="${map.value}" indexId="i">
               					  	  <span title="<c:out value="${qualifyListHolder.chkCode}" />-<c:out value="${qualifyListHolder.chkResult}" />">
               					  		  <c:out value="${qualifyListHolder.chkCode}"/>-<c:out value="${qualifyListHolder.chkCodePost}" />
               						  </span>
               						  <c:if test="${i+1 ne qualifyMkListSize}">
                                          <c:out value=", " />
                                      </c:if>
               					  </logic:iterate><br/>
            				  	  </c:forEach>
				              </td>
				          </tr>
				      </table>
				  </td>
			  </tr>
			  <tr>
                  <td id="iss" colspan="3">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                          <tr>
                              <td valign="top" width="15%">
                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">遺屬編審註記：</span>
                              </td>
                              <td valign="top" align="left" width="85%">
                                  <c:forEach var="map" items="${checkMarkMap}">
               					      <c:out value="${map.key}" />：&nbsp;
               						  <bean:size id='chkMkListSize' collection="${map.value}" ></bean:size>
               						  <logic:iterate id='listHolder' collection="${map.value}" indexId="i">
               							  <span title="<c:out value="${listHolder.chkCode}" />-<c:out value="${listHolder.chkResult}" />">
               								  <c:out value="${listHolder.chkCode}"/>-<c:out value="${listHolder.chkCodePost}" />
               							  </span>
               							  <c:if test="${i+1 ne chkMkListSize}">
                                        	  <c:out value=", " />
                                          </c:if>
               						  </logic:iterate><br/>
            				      </c:forEach>
				              </td>
				          </tr>
				      </table>				      
				  </td>
			  </tr>
              <tr>
                  <td colspan="3">&nbsp;</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td><hr size="1" noshade>

          <span class="titleinput">※注意事項：</span><br>
          　		1.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
          　		2.&nbsp;<span class="needtxt">＊</span>為必填欄位。</td>
        </tr>
        </table>
      </fieldset>
      <p></p>
      </html:form>
    </div>
  
</div>
<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>