<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html>
    <head>
        <acl:setProgId progId="BAMO0D188C" />
    	<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    	<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    	<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    	<script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
        <html:javascript formName="DisabledPayeeDataUpdateDetailForm" page="1" />
        <script language="javascript" type="text/javascript">
    
    var accSeqNo = '<c:out value="${_ben_option_list[0].benName}" />';
    var accSeqNoControl = '<c:out value="${DisabledPayeeDataUpdateQueryCaseAccSeqNoControl}" />';
    var evtIdnNo = '<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtIdnNo}" />';
    var evtBrDate = '<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtBrDate}" />';
    var commZipTmp="";
    var commAddrTmp="";
    // 變更 給付方式 時畫面異動
    function changePayCategory(){
        if($("payCategory").value=="1"){//本人領取
            $("accSeqNo").value='<c:out value="${DisabledPayeeDataUpdateDetailCase.seqNo}" />';
            $("payBankIdBranchId").value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.payBankId}"/>' + '<c:out value="${DisabledPayeeDataUpdateDetailCase.branchId}" />';
            $("payEeacc").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.payEeacc}" />';
            $("payTyp").disabled = false;
            $("accSeqNo").disabled = true;
        }
        if($("payCategory").value=="2"){//具名領取
            $("payTyp").value="";
            $("payBankIdBranchId").value="";     
            $("payAccount").value=""; 
            $("payEeacc").value=""; 
            $("bankName").value=""; 
            $("accName").value=""; 

            $("accountContent1").style.display="none";
            $("accountContent2").style.display="none";
            $("accountContent3").style.display = "none";
            
            $("payTyp").disabled = true; 
            //$("accSeqNo").disabled = false; 
            if(accSeqNoControl == "1"){
                document.getElementsByName("payCategory")[1].disabled = true;
                $("accSeqNo").disabled = true;
            } else {
                document.getElementsByName("payCategory")[1].disabled = false;
                $("accSeqNo").disabled = false; 
            }                          
        }          
    }
    
    // 變更 給付方式(本人領取) 時畫面異動  
    function changePayType(){      
        var payTyp = $("payTyp").value;
        $("accSeqNo").value='<c:out value="${DisabledPayeeDataUpdateDetailCase.seqNo}" />';
        $("payBankIdBranchId").value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.payBankId}"/>' + '<c:out value="${DisabledPayeeDataUpdateDetailCase.branchId}" />';
        $("payEeacc").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.payEeacc}" />'; 
        $("bankName").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.bankName}" />';
        if(payTyp=="1"||payTyp=="2"){
            $("accName").value = $("benName").value;
            $("accountContent1").style.display="inline";
            $("accountContent2").style.display="none";
            $("accountContent3").style.display = "inline";  
            $("payAccount").value = "";
        	$("bankName").value = "";                                                                 
        }
        if(payTyp==""||payTyp=="3"||payTyp=="4" || payTyp=="A"){   
            $("accName").value = "";             
            $("payBankIdBranchId").value="";     
        	$("payEeacc").value=""; 
        	$("payAccount").value=""; 
        	$("bankName").value=""; 
        	//$("accName").value="";
            $("accountContent1").style.display="none";
            $("accountContent2").style.display="none";
            $("accountContent3").style.display = "none";                                    
        }
        if(payTyp=="5"||payTyp=="6"){
            $("accName").value = '<c:out value="${DisabledPayeeDataUpdateDetailForm.accName}" />';
            $("payBankIdBranchId").value="";
			$("payEeacc").value="";    
            $("payAccount").value = '<c:out value="${DisabledPayeeDataUpdateDetailForm.payEeacc}" />';
        	$("bankName").value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.bankName}" />';
            $("accountContent1").style.display="none";
            $("accountContent2").style.display="inline";
            $("accountContent3").style.display = "inline";                                 
        }
    }
    
    function getCommZipGroup(){
    	var zipGroup = document.getElementsByName("commZip");
    	return zipGroup;
    }
    function getCommAddrGroup(){
    	var addrGroup = document.getElementsByName("commAddr");
    	return addrGroup;
    }
    
    // 變更 通訊地址別 時畫面異動
    function changeCommTyp(){
    	
        if($("commTyp").value=="1"){
        	$("commZip").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.commZip}" />'
        	$("commAddr").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.commAddr}" />'
        	${"addContent1"}.style.display="none"; 
        	${"addContent2"}.style.display="inline";
            if($("commZip").value != "") { commZipTmp = $("commZip").value; }
            if($("commAddr").value != "") { commAddrTmp = $("commAddr").value; }
            // 戶籍 郵遞區號地址
            
			if(!isValidDate($("benBrDate").value)){
                alert('輸入之「受款人出生日期」錯誤，請重新確認。\r\n');
            }else{  
	            getCvldtlZip();
	            getCvldtlAddr();
	            $("commZip").disabled = true;
	            $("commAddr").disabled = true;
	            document.getElementsByName("commTyp")[0].checked=true;
            }
        }
        if($("commTyp").value=="2"){
        	$("commZip").value = "";
        	$("commAddr").value = "";
        	${"addContent1"}.style.display="inline"; 
        	${"addContent2"}.style.display="none";
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
            if(commZipTmp != "")
                $("commZip").value = commZipTmp;
            if(commAddrTmp != "")
                $("commAddr").value = commAddrTmp;
            document.getElementsByName("commTyp")[1].checked=true;
        }
    }
    
    // 變更 國籍別 時畫面異動    
    function changeBenNationTpe(){
        
        if($("benNationTyp").value=="1"){
        	document.getElementsByName("benSex")[0].checked=false;
            document.getElementsByName("benSex")[1].checked=false; 
			$("benSex").value = "";
        	$("benNationCode").value = "";
            $("sexContent").style.display="none"; 
            $("nationalityContent").style.display="none";
            document.getElementsByName("benNationTyp")[0].checked=true; 
        }
        if($("benNationTyp").value=="2"){
        	$("benSex").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.benSex}" />';
        	$("benNationCode").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.benNationCode}" />';
        	var benNation = document.getElementsByName("benNationCodeOption")[0];
        	
        	//Iterate the country name dropdown menu, set the matching entry selected
        	for(var i = 0; i < benNation.options.length; i++){
        		if((benNation.options[i].value) == $("benNationCode").value){
        			benNation.options[i].selected = true;
        		}
        	}
            $("sexContent").style.display="inline";  
            $("nationalityContent").style.display="inline";  
            $("benNationCode").disabled = false;
            document.getElementsByName("benNationTyp")[1].checked=true;
            if($("benSex").value == '1') document.getElementsByName("benSex")[0].checked = true;
        	if($("benSex").value == '2') document.getElementsByName("benSex")[1].checked = true;
        }
        autoForeignBenSex();
    }
    
    // 身分查核年月
	//function chkIdnChkNote() {
	//    $("idnChkYear").value = "";
    //    $("idnChkMonth").value = "";
    //    if($("idnChkNote").value=="0") {
    //    	$("chkForeigner").style.display="none";
    //    }
	//	if($("idnChkNote").value=="1") { 
	//		$("chkForeigner").style.display="none";
	//	}
	//	if($("idnChkNote").value=="2") {
	//		$("idnChkYear").value = '<c:out value="${DisabledPayeeDataUpdateDetailForm.idnChkYear}" />';
    //    	$("idnChkMonth").value = '<c:out value="${DisabledPayeeDataUpdateDetailForm.idnChkMonth}" />';
	//		$("chkForeigner").style.display="inline";
	//	}
	//}
	
	// Ajax for 取得 戶籍檔郵遞區號
    function getCvldtlZip() {
        if(($("benIdnNo").value != "") && ($("benBrDate").value != "")){
            updateCommonAjaxDo.getCvldtlZip($("benIdnNo").value, $("benBrDate").value, fillCvldtlZip);       
        } else {
        	alert("需輸入受款人身分證號及出生日期");
        }
    }

    function fillCvldtlZip(zip) {
        var commZipGroup = document.getElementsByName("commZip");
        for(var i = 0; i < commZipGroup.length; i++){
        	commZipGroup[i].value = zip;
        }   
    }
    
    
    // Ajax for 取得 戶籍檔地址
    function getCvldtlAddr() {   
        if(($("benIdnNo").value != "") && ($("benBrDate").value != "")){
            updateCommonAjaxDo.getCvldtlAddr($("benIdnNo").value, $("benBrDate").value, fillCvldtlAddr);       
        }
    }

    function fillCvldtlAddr(addr) {
        //$("commAddr").value = addr;
        var commAddrGroup = document.getElementsByName("commAddr");
        for(var i = 0; i < commAddrGroup.length; i++){
        	commAddrGroup[i].value = addr;
        } 
        //var commAddrGroup = document.getElementsByName("commAddr");
        //for(var i = 0; i < commAddrGroup.length; i++}{
        //	commAddrGroup[i].value = addr;
        //}        
    }
    
    // Ajax for 取得 Caub 受款人姓名
    function getCaubUname() {   
        if($("benIdnNo").value != ""){
            updateCommonAjaxDo.getCaubUname($("benIdnNo").value, fillCaubUname);       
        }
    }

    function fillCaubUname(uname) {
        if($("benName").value == "")
            $("benName").value = uname; 
    }
    
    // Ajax for 取得 Caub 郵遞區號
    function getCaubCzpcd() {   
        if($("benIdnNo").value != ""){
            updateCommonAjaxDo.getCaubCzpcd($("benIdnNo").value, fillCaubCzpcd);       
        }
    }

    function fillCaubCzpcd(czpcd) {
        if($("commZip").value == "")
            $("commZip").value = czpcd;        
    }
    
    // Ajax for 取得 Caub 通訊地址
    function getCaubCaddr() {   
        if($("benIdnNo").value != ""){
            updateCommonAjaxDo.getCaubCaddr($("benIdnNo").value, fillCaubCaddr);       
        }
    }

    function fillCaubCaddr(caddr) {
        if($("commAddr").value == "")
            $("commAddr").value = caddr;        
    }
    
    // Ajax for 取得 Caub 電話
    function getCaubTel() {   
        if($("benIdnNo").value != ""){
            updateCommonAjaxDo.getCaubTel($("benIdnNo").value, fillCaubTel);       
        }
    }

    function fillCaubTel(tel) {
        if($("tel1").value == "")
            $("tel1").value = tel;        
    }
    
    function callCaubData() {
        if(($("benEvtRel").value=="A" && $("benIdnNo").value != "") || ($("benEvtRel").value=="C" && $("benIdnNo").value != "")){
            getCaubUname();
            getCaubCzpcd();
            getCaubCaddr();
            getCaubTel();
        }
    }
    
    
    <%-- begin 檢查必填欄位 --%>
    function checkRequireFields() {
        var nowDate = "<%=DateUtility.getNowChineseDate()%>";
        var nowDateYM = nowDate.substring(0,5);
        var brDate = '<c:out value="${DisabledPayeeDataUpdateDetailForm.benBrDate}" />';
        var benBrDate = calDay($("benBrDate").value,-1);
        var sYearTwenty = '<%=DateUtility.calYear(DateUtility.getNowChineseDate(),-20)%>';
        var msg = "";
		
		var secondText = $("benIdnNo").value.substring(1,2);
		if($("benIdnNo").value.length==10){
		if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[0].checked==true){
 			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
				msg += '個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」。\r\n';	
 				 $("benSex").focus();
    		}
 		}else if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[1].checked==true){
 			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
 				msg += '個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」。\r\n';	
 				 $("benSex").focus();
    		}
 		}
		}
		
        if (Trim($("benEvtRel").value) == "2" || Trim($("benEvtRel").value) == "3" || 
            Trim($("benEvtRel").value) == "4" || Trim($("benEvtRel").value) == "5" ||
            Trim($("benEvtRel").value) == "6" || Trim($("benEvtRel").value) == "7"){
        
            if(Trim($("benBrDate").value) > nowDate) msg += '「受款人出生日期」：不得大於系統日期。\r\n';
            
            if(document.getElementsByName("benNationTyp")[0].checked==false && document.getElementsByName("benNationTyp")[1].checked==false)
                msg += '「國籍別」：為必輸欄位。\r\n';
            
            if (document.getElementsByName("benNationTyp")[1].checked==true){
                if(document.getElementsByName("benSex")[0].checked==false && document.getElementsByName("benSex")[1].checked==false)
                    msg += '「性別」：為必輸欄位。\r\n';
                if(Trim($("benNationCode").value) == "")
                    msg += '「國籍」：為必輸欄位。\r\n';
            }
            
           if (document.getElementsByName("benNationTyp")[0].checked==true || !chkPID_CHAR($("benIdnNo").value)){
	            if(!isValidIdNoForTest($("benIdnNo").value) || !chkPID_CHAR($("benIdnNo").value))
	            	msg +='「繼承人身分證號」輸入有誤，請輸入長度為10 碼且符合格式的資料。\r\n';
	           
        	}  
        	
        	if (document.getElementsByName("benNationTyp")[1].checked==true){
             	if(!isEngNum($("benIdnNo").value)){
             		msg +='「繼承人身分證號」格式錯誤。\r\n';
             	}
	       }
            //if (document.getElementsByName("idnChkNote")[2].checked==true){
            //    if(Trim($("idnChkYear").value) == "")
            //        msg += '「查核年月年度」：為必輸欄位。\r\n';
            //    if(Trim($("idnChkMonth").value) == "")
            //        msg += '「查核年月月份」：為必輸欄位。\r\n';
            //    if((Trim($("idnChkYear").value)+Trim($("idnChkMonth").value)) < nowDateYM)
            //        msg += '「查核年月月份」：必需大於等於系統年月。\r\n';
            //}
            
            // 未滿20歲以下為必填，超過20歲可不填，生日前一天即算成年
            if(benBrDate > sYearTwenty){
                if(Trim($("benMarrMk").value) == "")
                    msg += '「婚姻狀況」：為必輸欄位。\r\n';
                        
                if (document.getElementsByName("benMarrMk")[1].checked==true){
                    if(Trim($("grdName").value) == "")
                        msg += '「法定代理人姓名」：為必輸欄位。\r\n';
                    if(Trim($("grdIdnNo").value) == "")
                        msg += '「法定代理人身分證號」：為必輸欄位。\r\n';
                    if(Trim($("grdBrDate").value) == "")
                        msg += '「法定代理人出生日期」：為必輸欄位。\r\n';
                
                    // 法定代理人與受款人比對
                    if((Trim($("grdIdnNo").value)+Trim($("grdBrDate").value)) == (Trim($("benIdnNo").value)+Trim($("benBrDate").value)))
                        msg += '「法定代理人」的身份證號與生日不得與受款人或事故者相同。\r\n';
                    // 法定代理人與事故者比對
                    if((Trim($("grdIdnNo").value)+Trim($("grdBrDate").value)) == (Trim(evtIdnNo)+changeDateType(Trim(evtBrDate))))
                        msg += '「法定代理人」的身份證號與生日不得與受款人或事故者相同。\r\n';
                }
            }
            
            // 受款人與事故者比對
            if((Trim($("benIdnNo").value)+Trim($("benBrDate").value)) == (Trim(evtIdnNo)+changeDateType(Trim(evtBrDate))))
                msg += '「受款人」的身份證號與生日不得與事故者相同。\r\n';
     
        } else if(Trim($("benEvtRel").value) == ""){
            msg += '「關係」：為必輸欄位。\r\n';
        }
        
        // 共用的
        if(Trim($("benName").value) == "")
            msg += '「受款人姓名」：為必輸欄位。\r\n';
        if(Trim($("benIdnNo").value) == "") 
        	msg += '「受款人身分證號」：為必輸欄位。\r\n';
        if(document.getElementsByName("payCategory")[0].checked==true && Trim($("payTyp").value) == "")
            msg += '「給付方式」：為必輸欄位。\r\n';
        if(document.getElementsByName("payCategory")[1].checked==true && Trim($("accSeqNo").value) == "")
            msg += '「給付方式」：為必輸欄位。\r\n';

        if ($("commTyp").value == "2") {
            if(Trim($("commZip").value) == ""){
                msg += '「郵遞區號」：為必輸欄位。\r\n';
            }
            if(Trim($("commAddr").value) == ""){
                msg += '「地址」：為必輸欄位。\r\n';
            }
        }
        
        if (Trim($("payTyp").value) == "1" || Trim($("payTyp").value) == "2" || Trim($("payTyp").value) == "7" || Trim($("payTyp").value) == "8"){
            var payBankIdBranchId = document.DisabledPayeeDataUpdateDetailForm.payBankIdBranchId.value;

            if(Trim($("payBankIdBranchId").value) == "" || Trim($("payEeacc").value) == "")
                msg += '「帳號」：為必輸欄位。\r\n';
            if(Trim($("accName").value) == "")
                msg += '「戶名」：為必輸欄位。\r\n';
            if (Trim($("payTyp").value) == "1" && payBankIdBranchId.substr(0,3) == "700")
                msg += '「帳號前三碼」：不可為700。\r\n';
            if (Trim($("payTyp").value) == "2" && payBankIdBranchId != "7000010" && payBankIdBranchId != "7000021"){
                msg += '「帳號(前)」：限定只能輸入7000010或7000021。\r\n';
            } else {
                if(isValidPayEeacc(payBankIdBranchId,Trim($("payEeacc").value)))
                    msg += '「帳號」格式錯誤，請重新確認。\r\n';
            }
            
            if($F("payBankIdBranchId").length < 7)
                msg += '「帳號(前)」：長度為7碼。\r\n';
            if (Trim($("payTyp").value) == "2" && payBankIdBranchId == "7000010" && $F("payEeacc").length > 8)
                msg += '「帳號(後)」：長度為8碼。\r\n';
            if (Trim($("payTyp").value) == "2" && payBankIdBranchId == "7000021" && $F("payEeacc").length != 14)
                msg += '「帳號(後)」：長度為14碼。\r\n';
            
        } else if (Trim($("payTyp").value) == "5" || Trim($("payTyp").value) == "6"){
            if(Trim($("payAccount").value) == "")
                msg += '「帳號」：為必輸欄位。\r\n';
            if(Trim($("bankName").value) == "")
                msg += '「金融機構名稱」：為必輸欄位。\r\n';
        } 
             
        if (msg != "") {
            alert(msg);
            return false;
        }
        else {
            return true;
        }
              
    }
    
    //依傳入關係條件 取得給付方式與申請代算單位下拉選單資料
    //function getOptionList(){
    //	var benEvtRel = ($("benEvtRel").value).toUpperCase();
    //	var brDate = "<c:out value="${PayeeDataUpdateQueryCase[0].evtBrDateChineseString}" />";
    //	var idnNo = "<c:out value="${PayeeDataUpdateQueryCase[0].evtIdnNo}" />";
    //	var apNo = "<c:out value="${PayeeDataUpdateQueryCase[0].apNo}" />";
	//	updateCommonAjaxDo.getPattyOptionList(benEvtRel, setPaytyOptionList);
    //}
    
    function setPaytyOptionList(data){
        DWRUtil.removeAllOptions("payTyp");
        DWRUtil.addOptions("payTyp", {'':'請選擇...'});
        DWRUtil.addOptions("payTyp", data ,'paramCode','paramName');
    }

    <%-- ] ... end --%>
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        document.DisabledPayeeDataUpdateDetailForm.reset();
        initAll();
    }
    <%-- ] ... end --%>
    /**
     * 初始化畫面欄位狀態，並取得各欄位對應值
     */
    function initAll(){
        // 國籍別
        $('benNationTyp').value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.benNationTyp}"/>';
        // 地址別
        $('commTyp').value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.commTyp}" />';
        // 身份查核年月
        //$('idnChkNote').value='<c:out value="${DisabledPayeeDataUpdateDetailCase.idnChkNote}" />';
        // 給付方式
        var accRel = '<c:out value="${DisabledPayeeDataUpdateDetailCase.accRel}" />';
        if(accRel != '1'){
        	$("payCategory").value = "2";
        	document.getElementsByName("payCategory")[1].checked=true;
        } else {
        	$("payCategory").value = "1";
        	document.getElementsByName("payCategory")[0].checked=true;
        }
        changeBenNationTpe();
        //chkIdnChkNote();
        changePayType();
        changeCommTyp();
        changePayCategory();
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
                <html:form action="/disabledPayeeDataUpdateDetail" method="post" onsubmit="return validateDisabledPayeeDataUpdateDetailForm(this);">
                    <fieldset>
                        <legend>&nbsp;繼承人修改作業&nbsp;</legend>
                        <div align="right" id="showtime">網頁下載時間：<func:nowDateTime /></div>
                        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
							<tr>
								<td align="right">
									<html:hidden styleId="page" property="page" value="1" />
									<html:hidden styleId="method" property="method" value="" />
									<acl:checkButton buttonName="btnSave">
										<input name="btnSave" type="button" class="button" value="確　認"
											onclick="$('page').value='1'; $('method').value='doConfirmInherit'; if (document.DisabledPayeeDataUpdateDetailForm.onsubmit() && checkRequireFields()){document.DisabledPayeeDataUpdateDetailForm.submit();}else{return false;}" />&nbsp;
                        			</acl:checkButton>
									<acl:checkButton buttonName="btnBack">
										<input name="btnBack" type="button" class="button" value="返　回"
										onclick="$('page').value='1'; $('method').value='doBackList'; document.DisabledPayeeDataUpdateDetailForm.submit();" />
									</acl:checkButton>
								</td>
							</tr>
							<tr>
                                <td>
                                    <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                        <tr>
                                			<td width="24%"><span class="issuetitle_L_down">受理編號：</span>
                                    		<c:out value="${DisabledPayeeDataUpdateQueryCase[0].apNoStrDisplay}" />
                                			</td>
                                			<td width="21%"><span class="issuetitle_L_down">給付別：</span>
                                    		<c:out value="${DisabledPayeeDataUpdateQueryCase[0].payCode}" />
                                			</td>
                                			<td width="21%"><span class="issuetitle_L_down">申請日期：</span>
                                    		<c:out value="${DisabledPayeeDataUpdateQueryCase[0].appDateChineseString}" />
                                			</td>
                            			</tr>
                                        <tr>
                                			<td><span class="issuetitle_L_down">事故者姓名：</span>
                                    		<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtName}" />
                                			</td>
                                			<td><span class="issuetitle_L_down">事故者出生日期：</span>
                                    		<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtBrDateChineseString}" />
                                			</td>
                                			<td><span class="issuetitle_L_down">事故者身分證號：</span>
                                    		<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtIdnNo}" />
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
                                            <td id="iss" colspan="1">
                                                <span class="needtxt">＊</span>
                                    			<span class="issuetitle_L_down">關係：</span>
                                    			<html:select property="benEvtRel" styleId="benEvtRel" styleClass="formtxt">
                                        			<html:option value="">請選擇</html:option>
                                        			<html:options collection="<%=ConstantKey.PAYEE_RELATIION_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                    			</html:select>
                                             </td>
                                            <td id="iss" colspan="2">　<span class="issuetitle_L_down">繼承自受款人：</span>插入替代字</td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="3">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">繼承人姓名：</span>
                                                <html:text property="benName" styleId="benName" styleClass="textinput" size="50" maxlength="50" onblur="this.value=asc(this.value);"/>
											</td>
                                        <tr>
                                        	<td id="iss">
                                                    <span class="needtxt">＊</span><span class="issuetitle_L_down">繼承人申請日期：</span>
                                                    <html:text property="appDate" styleId="benIdnNo" styleClass="textinput" size="15" maxlength="10" onblur="this.value=asc(this.value).toUpperCase(); callCaubData(); getBbcmf08Data();"/>
                                            </td>
                                            <td id="iss">
                                                    <span class="needtxt">＊</span><span class="issuetitle_L_down">繼承人身分證號：</span>
                                                    <html:text property="benIdnNo" styleId="benIdnNo" styleClass="textinput" size="15" maxlength="10" onblur="this.value=asc(this.value).toUpperCase(); callCaubData(); getBbcmf08Data();autoForeignBenSex();"/>
                                            </td>
                                            <td id="iss" colspan="2">
                                                　							<span class="issuetitle_L_down">繼承人出生日期：</span>
                                                	<html:text property="benBrDate" styleId="benBrDate" styleClass="textinput" size="10" maxlength="8" onblur="this.value=asc(this.value);"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="34%" id="iss">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">國籍別：</span>
                                                <span class="formtxt">
                                            		<html:radio property="benNationTyp" value="1" onclick="$('benNationTyp').value='1'; changeBenNationTpe();" />本國
                                            		<html:radio property="benNationTyp" value="2" onclick="$('benNationTyp').value='2'; changeBenNationTpe();" />外籍
                                        		</span>
                                            </td>
                                            <td width="33%" id="iss">
                                            	<div id="sexContent">
                                            		<span class="needtxt">＊</span>
                                        			<span class="issuetitle_L_down">性別：</span>
                                        			<span class="formtxt">
                                            			<html:radio property="benSex" value="1" onclick="$('benSex').value='1';"/>男
                                        			</span>
                                        			<span class="formtxt">
                                            			<html:radio property="benSex" value="2" onclick="$('benSex').value='2';"/>女
                                        			</span>
                                        		</div>
                                        		<span>&nbsp;</span>
                                        	</td>
                                            <td width="33%" id="iss">
                                            	<div id="nationalityContent">
                                            		<span class="needtxt">＊</span>
                                        			<span class="issuetitle_L_down">國籍：</span>
                                        			<html:text property="benNationCode" styleId="countryId" styleClass="textinput" size="2" maxlength="3" readonly = "true"/>
				                        			<label>
				                            		<html:select property="benNationCodeOption" onchange="$('benNationCode').value=$('benNationCodeOption').value">
                                                		<html:option value="">請選擇</html:option>
                                                		<html:options collection="<%=ConstantKey.COUNTRYID_OPTION_LIST%>" property="countryId" labelProperty="cname" />
                                            		</html:select>
				                        			</label>
				                        		</div>
				                        		<span>&nbsp;</span>
				                        	</td>
                                        </tr>
                                        
                                        <tr>
                                            <td colspan="3" id="iss">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">給付方式：</span>
                                    			<span class="formtxt">
                                        			<html:radio styleId="payCategory1" property="payCategory" value="1" onclick="$('payCategory').value='1';changePayCategory();"/>本人領取
                                        			<html:select property="payTyp" styleId="payTyp" styleClass="formtxt" onchange="changePayType();">
                                            			<html:option value="">請選擇</html:option>
                                            			<html:options collection="<%=ConstantKey.PAYTYP_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                        			</html:select>
                                        			<html:radio styleId="payCategory2" property="payCategory" value="2" onclick="$('payCategory').value='2';changePayCategory();"/>具名領取
                                        			<html:select property="accSeqNo" styleId="accSeqNo" styleClass="formtxt">
                                            		<html:option value="">請選擇</html:option>
                                            		<logic:notEmpty name="<%=ConstantKey.BEN_OPTION_LIST%>">
                                                		<logic:iterate id="benList" name="<%=ConstantKey.BEN_OPTION_LIST%>">                                                                        
                                                    		<html:option value="${benList.seqNo}" ><c:out value="${benList.benName}" /></html:option>                                                
                                                		</logic:iterate>
                                            		</logic:notEmpty>
                                        			</html:select>
                                    			</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" id="iss">
                                                <div id="accountContent1">
                                                    <div style="padding-bottom:2px; padding-top:2px;">
                                                        <span class="needtxt">＊</span><span class="issuetitle_L_down">帳　號：</span>
                                                        <html:text property="payBankIdBranchId" styleId="payBankIdBranchId" styleClass="textinput" size="10" maxlength="7" onchange="this.value=Trim($('payBankIdBranchId').value)" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('payBankIdBranchId'), $('payEeacc'))"/>&nbsp;-
                                        				<html:text property="payEeacc" styleId="payEeacc" styleClass="textinput" size="22" maxlength="14" onchange="this.value=Trim($('payEeacc').value)" onblur="this.value=asc(this.value).toUpperCase();"/>
                                                    </div>
                                                </div>
                                                <span id="accountContent2">
                                        			<span class="needtxt">＊</span><span class="issuetitle_L_down">帳號：</span>
                                        			<html:text property="payAccount" styleId="payAccount" styleClass="textinput" size="21" maxlength="21" onblur="this.value=asc(this.value).toUpperCase();"/>
                                        			<span class="needtxt">＊</span>
                                        			<span class="issuetitle_L_down">金融機構名稱：</span>
                                        			<html:text property="bankName" styleId="bankName" styleClass="textinput" size="50" maxlength="60" onblur="this.value=asc(this.value).toUpperCase();"/>                                   　   
                                    			</span>
                                    			<div id="accountContent3">
                                                    <span class="needtxt">＊</span><span class="issuetitle_L_down">戶　名：</span>
                                                    <html:text property="accName" styleId="accName" styleClass="textinput" size="50" maxlength="50" onchange="this.value=Trim($('accName').value)" onblur="this.value=asc(this.value).toUpperCase();"/>                                    			
                                    			</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                            	<span class="issuetitle_L_down">電　話1：</span>
                                            	<html:text property="tel1" styleId="tel1" styleClass="textinput" size="20" maxlength="20" />
                                            </td>
                                            <td id="iss" colspan="2">　
                                            	<span class="issuetitle_L_down">電　話2：</span>
                                            	<html:text property="tel2" styleId="tel2" styleClass="textinput" size="20" maxlength="20" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">地　址：</span>
                                                <span class="formtxt" id="rdocommtyp1">
                                                    <html:radio property="commTyp" value="1" onclick="$('commTyp').value='1'; changeCommTyp();"/>同戶籍地
                                                </span>
                                                <span class="formtxt" id="rdocommtyp2">
                                                    <html:radio property="commTyp" value="2" onclick="$('commTyp').value='2'; changeCommTyp();"/>現住址
                                                </span>
                                            </td>
                                            <td id="iss" colspan="2">
                                                <div class="formtxt" id="addContent1">
                                                    　							<span class="issuetitle_L_down">現住址：</span>
                                                    <html:text property="commZip" styleId="commZip" styleClass="textinput" size="6" maxlength="6" onchange="this.value=Trim($('commZip').value)" onblur="this.value=asc(this.value);"/>(郵遞區號)&nbsp;-
                                            		<html:text property="commAddr" styleId="commAddr" styleClass="textinput" size="72" maxlength="240" onchange="this.value=Trim($('commAddr').value)" onblur="this.value=asc(this.value);"/>
                                                </div>
                                                <div class="formtxt" id="addContent2">
                                                	<html:text property="commZip" styleId="commZip" styleClass="disabled" size="6" maxlength="6" onchange="this.value=Trim($('commZip').value)" onblur="this.value=asc(this.value);"/>&nbsp;(郵遞區號)&nbsp;-&nbsp;
                                                	<html:text property="commAddr" styleId="commAddr" styleClass="disabled" size="72" maxlength="240" onchange="this.value=Trim($('commAddr').value)" onblur="this.value=asc(this.value);"/>
                                            	</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">婚姻狀況：</span>
                                                <span class="formtxt">
                                                	<html:radio property="benMarrMk" value="Y" onclick="$('benMarrMk').value='Y'; "/>已婚
                                                	<html:radio property="benMarrMk" value="N" onclick="$('benMarrMk').value='N'; "/>未婚
                                                </span>
                                            </td>
                                            <td id="iss" colspan="2">　<span class="issuetitle_L_down">法定代理人姓名：</span>
                                            <html:text property="grdName" styleId="grdName" styleClass="textinput" size="50" maxlength="50"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">　<span class="issuetitle_L_down">法定代理人身分證號：</span>
                                            <html:text property="grdIdnNo" styleId="grdIdnNo" styleClass="textinput" size="20" maxlength="50" onblur="this.value=asc(this.value).toUpperCase();"/>
                                            </td>
                                            <td id="iss" colspan="2">　<span class="issuetitle_L_down">法定代理人出生日期：</span>
                                            <html:text property="grdBrDate" styleId="grdBrDate" styleClass="textinput" size="20" maxlength="50"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <hr size="1" noshade>
                                    <span class="titleinput">※注意事項：</span><br>
                                    　						1.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
                                    　						2.&nbsp;<span class="needtxt">＊</span>為必填欄位。
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <p></p>
                    </html:form>
                </div>
        <%@ include file="/includes/ba_footer.jsp"%>
    	</div>
    </body>
</html:html>



