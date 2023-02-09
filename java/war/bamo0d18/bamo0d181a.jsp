<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html>
    <head>
        <acl:setProgId progId="BAMO0D181A" />
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

        var Toggle = {
            togglePayCategory : function() { //變更給付方式畫面異動
                if ($("payCategory").value == "1") {
                    $("accSeqNo").value = "";
                    $("payTyp").disabled = false;
                    $("accSeqNo").disabled = true;
                }
                if ($("payCategory").value == "2") {
                    var accSeqNoControl = '<c:out value="${DisabledPayeeDataUpdateQueryCaseAccSeqNoControl}" />';
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
                    if (accSeqNoControl == "1") {
                        document.getElementsByName("payCategory")[1].disabled = true;
                        $("accSeqNo").disabled = true;
                    } else {
                        document.getElementsByName("payCategory")[1].disabled = false;
                        $("accSeqNo").disabled = false;
                    }
                }
            },
            togglePayType : function() { //變更給付種類畫面異動
                var payTyp = $("payTyp").value;
                $("payBankId").value="";
                $("branchId").value="";
                $("payAccount").value = "";
                $("payEeacc").value = "";
                $("bankName").value = "";
                $("accName").value = "";

                if (payTyp == "1" || payTyp == "2") {
                    if($("accName").value == '') {
                    	$("accName").value = $("benName").value;
                    }
                    $("accountContent1").style.display = "inline";
                    $("accountContent2").style.display = "none";
                    $("accountContent3").style.display = "inline";
                    $("specialAccContent").style.display="inline";
                }else{
                    $("specialAccContent").style.display="none";
                }
                if (payTyp == "" || payTyp == "3" || payTyp == "4" || payTyp == "A") {
                    $("accName").value = "";
                    $("accountContent1").style.display = "none";
                    $("accountContent2").style.display = "none";
                    $("accountContent3").style.display = "none";
                    $("specialAccContent").style.display="none";
                }
                if (payTyp == "5" || payTyp == "6") {
                    $("accName").value = "";
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
            },
            toggleCommTyp : function() { // 變更 通訊地址別 時畫面異動
                $("commZip").value = "";
                $("commAddr").value = "";
                $("addContent1").style.display = "inline";
                $("addContent2").style.display = "none";
                if ($("commTyp").value == "1") {
                    $("addContent1").style.display = "none";
                    $("addContent2").style.display = "inline";
                    DWREngine.setAsync(false);

					if(!isValidDate($("benBrDate").value)){
					      alert('輸入之「受款人出生日期」錯誤，請重新確認。\r\n');
					}else{
	                    CVLDTLUtil.getCvldtlZip();
	                    CVLDTLUtil.getCvldtlAddr();
	                    $("commZip").disabled = true;
	                    $("commAddr").disabled = true;
	                    document.getElementsByName("commTyp")[0].checked = true;
	                    if (Trim($("commAddr").value) == "") {//如果沒抓到戶籍地址資料,則跳回現住址並提示使用者
	                        $("commTyp").value = "2";
	                        alert('戶籍地住址無資料，請選擇「現住址」並輸入住址資料。\r\n');
	                        Toggle.toggleCommTyp();
	                        $("commZip").value = "";
	                        $("commAddr").value = "";
	                    }
	                }
                }
                if ($("commTyp").value == "2") {
                    $("commZip").disabled = false;
                    $("commAddr").disabled = false;
                    $("addContent1").style.display = "inline";
                    $("addContent2").style.display = "none";
                    document.getElementsByName("commTyp")[1].checked = true;
                }
            },
            toggleBenNationType : function() { // 變更 國籍別 時畫面異動
                $("benSex").value = "";
                $("benNationCode").value = "";
                var frm = document.forms['DisabledPayeeDataUpdateDetailForm'];
                frm['commTyp'][0].disabled = false;
                if ($("benNationTyp").value == "1") {
                    document.getElementsByName("benSex")[0].checked=false;
                	document.getElementsByName("benSex")[1].checked=false;
					$("sexContent").style.display = "none";
                    $("nationalityContent").style.display = "none";
                }
                if ($("benNationTyp").value == "2") {
                    $("sexContent").style.display = "inline";
                    $("nationalityContent").style.display = "inline";
                    //$("benNationCode").disabled = true;
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
            },
            toggleIdnChkNote : function() { // 身分查核年月
                $("idnChkYear").value = "";
                $("idnChkMonth").value = "";
                if ($("idnChkNote").value == "0") $("chkForeigner").style.display = "none";
                if ($("idnChkNote").value == "1") $("chkForeigner").style.display = "none";
                if ($("idnChkNote").value == "2") $("chkForeigner").style.display = "inline";
            },
            toggleAccNameOnAccountReset : function(){
                if($('accName')!=null && $("accName").value == ''){
                    $("accName").value = $("benName").value;
                }
            }
        };

        // 若「關係」<>1 且未滿18歲者之受款人,「婚姻狀況」,婚姻狀況預設"未婚"
        var DefaultMarritalStatusSetter = {
            isNotAdult : function(){
                var benBrDate = calDay($("benBrDate").value, 0);
                var sYearEighteen = '<%=DateUtility.calYear( DateUtility.getNowChineseDate().substring(0, 5).concat( String.valueOf(DateUtility.lastDay(DateUtility.getNowChineseDate())) ) ,-18)%>';
                return benBrDate > sYearEighteen;
            },
            checkMarritalStatus : function(){
                $("benMarrMkSection").show();
                document.getElementsByName("benMarrMk")[1].checked = true;
            },
            unCheckMarritalStatus : function(){
                document.getElementsByName("benMarrMk")[0].checked = false;
                document.getElementsByName("benMarrMk")[1].checked = false;
                $("benMarrMkSection").hide();
            },
            setDefaultMarritalStatus : function() {
                if(  ( $F('benEvtRel') === '4' || $F('benEvtRel') === '6' || $F('benEvtRel') === '7') && DefaultMarritalStatusSetter.isNotAdult() ){
                    DefaultMarritalStatusSetter.checkMarritalStatus();
                } else {
                    DefaultMarritalStatusSetter.unCheckMarritalStatus();
                }
            }
        };

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

        /*function moveFocus(){
            $('grdName').focus();
        }*/

        function moveFocus(elementId){
            var frm = document.forms['DisabledPayeeDataUpdateDetailForm'];
            frm[elementId].focus();
        }

        function checkRequireFields() {
            var nowDate = "<%=DateUtility.getNowChineseDate()%>";
            var nowDateYM = nowDate.substring(0, 5);
            var benBrDate = calDay($("benBrDate").value, 0);
            var sYearEighteen = '<%=DateUtility.calYear( DateUtility.getNowChineseDate().substring(0, 5).concat( String.valueOf(DateUtility.lastDay(DateUtility.getNowChineseDate())) ) ,-18)%>';
            var evtIdnNo = '<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtIdnNo}" />';
            var evtBrDateChineseString = '<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtBrDateChineseString}" />';
            var evtDieDateChineseString = '<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtDieDateStr}" />';
            var errorMessage = [];
            var frm = document.forms['DisabledPayeeDataUpdateDetailForm'];
            var validator = new SimpleValidator();

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

            if(!validator.isRequiredValueEmpty('「關係」', $('benEvtRel'))){

                validator.isRequiredValueEmpty('「國籍別」', frm['benNationTyp']);
                validator.isRequiredValueEmpty('「受款人姓名」', $('benName'));
                validator.isRequiredValueEmpty('「受款人身分證號」', $('benIdnNo'));
                validator.isRequiredValueEmpty('「受款人出生日期」', $('benBrDate'));
                validator.isZipFormatInvalid($F('commZip'));

                validator.isNotTheSame( [{name : '「法定代理人身份證號」', value : Trim($F('grdIdnNo')  )},
                                         {name : '「受款人身份證號」',     value : (Trim($F("benIdnNo")) ) },
                                         {name : '「事故者身份證號」',     value : (Trim(evtIdnNo) ) } ] );

                /*validator.isNotTheSame( [{name : '「法定代理人身份證號與生日」', value : Trim($F('grdIdnNo') +Trim($F('grdBrDate')) )},
                                         {name : '「受款人身份證號與生日」',     value : (Trim($F("benIdnNo")) + Trim($F("benBrDate"))) },
                                         {name : '「事故者身份證號與生日」',     value : (Trim(evtIdnNo) + Trim(evtBrDateChineseString)) } ] );*/

                if(!validator.isRequiredValueEmpty('「繼承人申請日期」', $('appDate')) ){
                    validator.isGreaterThanOrEqual({name : '「繼承人申請日期」', value : $F('appDate')},
                                                   {name : '「系統日期」', value : '0980101'});
                    validator.isGreaterThanOrEqual({name : '「繼承人申請日期」', value : $F('appDate')},
                                                   {name : '「事故者死亡日期」', value : evtDieDateChineseString});
                    validator.isGreaterThanOrEqual({name : '「繼承人申請日期」', value : $F('appDate')},
                                                   {name : '「出生日期」', value : Trim($F("benBrDate"))});
                    validator.isSmallerThanOrEqual({name : '「繼承人申請日期」', value : $F('appDate')},
                                                   {name : '「系統日期」', value : nowDate});
                }
                if(frm['benNationTyp'][1].checked ){
                    validator.isRequiredValueEmpty('「性別」', $('benSex'));
                    if(!validator.isRequiredValueEmpty('「國籍」', $('benNationCode'))){
                        validator.isAlphaNumericValue( '「國籍」', $F('benNationCode')  )
                    }
                }
                if(frm['idnChkNote'][2].checked ) {
                    validator.isRequiredValueEmpty('「查核年月年度」', $('idnChkYear'));
                    validator.isRequiredValueEmpty('「查核年月月份」', $('idnChkMonth'));
                    validator.isGreaterThan({name : '「查核年月」', value : Trim($F("idnChkYear")) + Trim($F("idnChkMonth")) },
                                            {name : '「系統年月」', value : nowDateYM });
                }
                if( (frm['payCategory'][0].checked && Trim($F('payTyp')) === '')  ||  (frm['payCategory'][1].checked && Trim($F('accSeqNo')) === '')  ){
                    validator.isRequiredValueEmpty('「給付方式」', $('payTyp'));
                }

                if (benBrDate > sYearEighteen) {
                    if (( $F('benEvtRel') === '4' || $F('benEvtRel') === '6' || $F('benEvtRel') === '7')){
                        validator.isRequiredValueEmpty('「婚姻狀況」', frm['benMarrMk']);
                    }
                    if(frm['benMarrMk'][1].checked){
                        validator.isRequiredValueEmpty('「法定代理人姓名」', $('grdName'));
                        validator.isRequiredValueEmpty('「法定代理人身分證號」', $('grdIdnNo'));
                        validator.isRequiredValueEmpty('「法定代理人出生日期」', $('grdBrDate'));
                        validator.isSmallerThanOrEqual({name : '「法定代理人出生日期」', value : $F('grdBrDate')},
                                                       {name : '「系統日期」', value : nowDate});
                    }
                }

                if( $('benBrDate') != null && $F('benBrDate') !== ''){
                    validator.isSmallerThanOrEqual({name : '「受款人出生日期」', value : $F('benBrDate')},
                                                   {name : '「系統日期」', value : nowDate});
                    switch ($F('benEvtRel')) { //依關係不同,受款人出生日期需判斷是否大於或小於事故者出生日期
                        case '3':
                            validator.isSmallerThan({name : '「受款人出生日期」', value : $F('benBrDate')},
                                                    {name : '「事故者出生日期」', value : evtBrDateChineseString});
                            break;
                        case '4':
                            validator.isGreaterThan({name : '「受款人出生日期」', value : $F('benBrDate')},
                                                    {name : '「事故者出生日期」', value : evtBrDateChineseString});
                            break;
                        case '5':
                            validator.isSmallerThan({name : '「受款人出生日期」', value : $F('benBrDate')},
                                                    {name : '「事故者出生日期」', value : evtBrDateChineseString});
                            break;
                        case '7':
                            validator.isGreaterThan({name : '「受款人出生日期」', value : $F('benBrDate')},
                                                    {name : '「事故者出生日期」', value : evtBrDateChineseString});
                            break;
                        default:
                            break;
                    }
                }

                if( $('benDieDate') != null && $F('benDieDate') !== ''){
                    validator.isChineseDateFormatValid('「受款人死亡日期」', $F('benDieDate'));
                    validator.isGreaterThanOrEqual({name : '「受款人死亡日期」', value : $F('benDieDate')},
                                                   {name : '「繼承人申請日期」', value : $F('appDate')});
                    validator.isSmallerThanOrEqual({name : '「受款人死亡日期」', value : $F('benDieDate')},
                                                   {name : '「系統日期」', value : nowDate});
                }

                if($('commTyp').value === '1'){
                    if (Trim($("commAddr").value) === "") {
                        validator.addErrorMsg('戶籍地住址無資料，請選擇「現住址」並輸入住址資料。');
                        $("commTyp").value = "2";
                        Toggle.toggleCommTyp();
                    }
                }
                if ($('commTyp').value === '2') {
                    validator.isRequiredValueEmpty('「郵遞區號」', $('commZip'));
                    validator.isRequiredValueEmpty('「地址」', $('commAddr'));
                }

                if(frm['benNationTyp'][1].checked){
                    if($('benNationCode') !=null && Trim($('benNationCode').value) == '000'){
                        validator.addErrorMsg('「國籍別」為外籍不得輸入中華民國之國籍代碼');
                    }
                }

                if (document.getElementsByName("benNationTyp")[0].checked==true){
	                 if(!isValidIdNoForTest($("benIdnNo").value) || !chkPID_CHAR($("benIdnNo").value))
	                 	 validator.addErrorMsg('「受款人身分證號(保險證號)」 輸入有誤，請輸入長度為10 碼且符合格式的資料。');
	             }

	             if (document.getElementsByName("benNationTyp")[1].checked==true){
	             	if(!isEngNum($("benIdnNo").value)){
	             		validator.addErrorMsg('「受款人身分證號(保險證號)」格式錯誤。');
	             	}
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

                    if(Trim($F('payTyp')) === '1') {
                        if(payBankIdBranchId.substr(0, 3) === '700') {
                            validator.addErrorMsg('「帳號前三碼」：不可為700。');
                        }
                    }

                    if(Trim($F('payTyp')) === '2') {
                        if (payBankIdBranchId !== "7000010" && payBankIdBranchId !== "7000021") {
                            validator.addErrorMsg('「帳號(前)」：限定只能輸入700-0010或700-0021。');
                        } else {
                            if (isValidPayEeacc(payBankIdBranchId, Trim($F("payEeacc")))) {
                                validator.addErrorMsg('「帳號」格式錯誤，請重新確認。');
                            }

                            if(payBankIdBranchId === "7000010"){
                                validator.checkLength('「帳號(後)」', $F('payEeacc'), 8 );
                            }
                            if(payBankIdBranchId === '7000021'){
                                validator.checkLength('「帳號(後)」', $F('payEeacc'), 14);
                            }
                        }
                    }
                } else if (Trim($F("payTyp")) === "5" || Trim($F("payTyp")) === "6") {
                    validator.isRequiredValueEmpty('「金融機構名稱」', $('bankName'));
                    if(!validator.isRequiredValueEmpty('「帳號」', $('payAccount'))){
                        validator.isAlphaNumericValue('「帳號」', $F('payAccount'));
                    }
                }
            }

            if(validator.getErrorMsgs().length !== 0) {
                alert(validator.getErrorMsgsAsString() + errorMessage.join('\r\n'));
                return false;
            } else {
                if(chkEvtBrDate()){
                return true;
                }else{
                return false;
                }
            }

        }

                // Ajax for 取得 出生日期錯誤參數 確認是否有此筆資料P120436303 0480229  $("famIdnNo").value,$("famBrDate").value
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

        var setSelectedCountryOnBenNationCodeChange = function(){
            var frm = document.forms['DisabledPayeeDataUpdateDetailForm'];
            if($('benNationCode') !== null){
                var nationCode = $F('benNationCode');
                frm['benNationCodeOption'].selectedIndex = 0;
                var benNationList = frm['benNationCodeOption'];
                for (var i = 0; i < benNationList.options.length; i++) {
                    if ((benNationList.options[i].value) == nationCode) {
                        benNationList.options[i].selected = true;
                    }
                }
            }
        };

        function resetForm() {
            document.DisabledPayeeDataUpdateDetailForm.reset();
            initAll();
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

        function initAll() {
            checkIdnoExist();
            $('benNationTyp').value = '1';
            document.getElementsByName("benNationTyp")[0].checked = true;
            $('commTyp').value = '2';
            document.getElementsByName("commTyp")[1].checked = true;
            $('idnChkNote').value = '0';
            document.getElementsByName("idnChkNote")[1].checked = true;
            $("benEvtRel").value = "";
            $("payCategory").value = "1";
            document.getElementsByName("payCategory")[0].checked = true;
            Toggle.toggleBenNationType();
            Toggle.toggleIdnChkNote();
            Toggle.togglePayType();
            Toggle.toggleCommTyp();
            Toggle.togglePayCategory();
            // 新增時不需出現
            $("idnChkNoteContent").style.display = "none";
            $("addContent2").style.display = "none";
            // 具名領取
            var toHideCoreceiveNameListDropDownMenu = '<c:out value="${toHideCoreceiveNameListDropDownMenu}" />';
            if (toHideCoreceiveNameListDropDownMenu == "true") {
                $("coreceiveSection").style.display = "none";
            }
            Event.observe('payTyp', 'change', Toggle.togglePayType, false);
            Event.observe('benBrDate', 'blur', DefaultMarritalStatusSetter.setDefaultMarritalStatus, false);
            Event.observe('benEvtRel', 'change', DefaultMarritalStatusSetter.setDefaultMarritalStatus, false);
            //Event.observe('benNationCode', 'keyup', setSelectedCountryOnBenNationCodeChange, false);
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
                        <legend>&nbsp;失能年金受款人資料新增&nbsp;</legend>
                        <div align="right" id="showtime">網頁下載時間：<func:nowDateTime /></div>
                        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
							<tr>
								<td align="right">
									<html:hidden styleId="page" property="page" value="1" />
									<html:hidden styleId="method" property="method" value="" />
									<html:hidden styleId="idnoExist" property="idnoExist"/>
									<acl:checkButton buttonName="btnSave">
										<input name="btnSave" type="button" class="button" value="確　認"
											onclick="checkIdnoExist();$('page').value='1'; $('method').value='doSave'; if (document.DisabledPayeeDataUpdateDetailForm.onsubmit() && checkRequireFields()){
																								if($('payTyp').value=='4'){if(confirm('非行動不便之扣押戶，給付方式不得選擇匯票郵寄申請人，請重新選擇給付方式。按下【確定】時，不存檔並回到畫面重新選擇「給付方式」；按下【取消】時，確定「給付方式」為匯票郵寄並繼續存檔作業。')){return false;}else{document.DisabledPayeeDataUpdateDetailForm.submit();}
																								}else{document.DisabledPayeeDataUpdateDetailForm.submit();}}else{return false;}" />&nbsp;
                        			</acl:checkButton>
									<acl:checkButton buttonName="btnClear">
										<input name="btnClear" type="button" class="button" value="清　除"
										onclick="resetForm();">&nbsp;
                        			</acl:checkButton>
									<acl:checkButton buttonName="btnBack">
										<input name="btnBack" type="button" class="button" value="返　回"
										onclick="$('page').value='3'; $('method').value='doBackList'; document.DisabledPayeeDataUpdateDetailForm.submit();" />
									</acl:checkButton>
								</td>
							</tr>
							<tr>
                                <td>
                                    <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                        <tr>
                                			<td width="25%"><span class="issuetitle_L_down">受理編號：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].apNoStrDisplay}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">給付別：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].payCode}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">申請日期：</span>
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].appDateChineseString}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">事故日期：</span>
                                				<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtJobDateStr}" />
                                			</td>

                            			</tr>
                                        <tr>
                                			<td colspan="2"><span class="issuetitle_L_down">事故者姓名：</span>
                                    		<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtName}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">事故者出生日期：</span>
                                    		<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtBrDateChineseString}" />
                                			</td>
                                			<td width="25%"><span class="issuetitle_L_down">事故者身分證號：</span>
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
                                            <td id="iss" colspan="3">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">關係：</span>
                                    			<html:select property="benEvtRel" styleId="benEvtRel" styleClass="formtxt">
                                        			<html:option value="">請選擇</html:option>
                                        			<html:options collection="<%=ConstantKey.PAYEE_RELATIION_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                    			</html:select>
                                             </td>
                                            <!-- <td id="iss" colspan="2"><div id="relationContent">&nbsp;</div></td>  -->
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="2">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">受款人姓名：</span>
                                                <html:text property="benName" styleId="benName" styleClass="textinput" size="50" maxlength="50" onblur="this.value=asc(this.value);"/>
											</td>
											<td id="iss">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">繼承人申請日期：</span>
												<html:text property="appDate" styleId="appDate" styleClass="textinput" size="7" maxlength="7"/>
                                            </td>

                                        <tr>
                                            <td id="iss">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">受款人身分證號：</span>
                                                <html:text property="benIdnNo" styleId="benIdnNo" styleClass="textinput" size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();checkIdnoExist();autoForeignBenSex();"/>
                                            </td>
                                            <td id="iss">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">受款人出生日期：</span>
                                                <html:text property="benBrDate" styleId="benBrDate" styleClass="textinput" size="10" maxlength="8" onblur="this.value=asc(this.value);checkIdnoExist();"/>
                                            </td>
                                            <td id="iss">
                                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">受款人死亡日期：</span>
                                                <html:text property="benDieDate" styleId="benDieDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="34%" id="iss">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">國籍別：</span>
                                                <span class="formtxt">
                                            		<html:radio property="benNationTyp" value="1" onclick="$('benNationTyp').value='1'; Toggle.toggleBenNationType();" />本國
                                            		<html:radio property="benNationTyp" value="2" onclick="$('benNationTyp').value='2'; Toggle.toggleBenNationType();" />外籍
                                        		</span>
                                            </td>
                                            <td width="33%" id="iss">
                                            	<div id="sexContent">
                                            		<span class="needtxt">＊</span><span class="issuetitle_L_down">性別：</span>
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
                                            		<span class="needtxt">＊</span><span class="issuetitle_L_down">國籍：</span>
                                        			<html:text property="benNationCode" styleId="countryId" styleClass="textinput" size="2" maxlength="3" readonly = "true" onblur="this.value=asc(this.value).toUpperCase();" onfocus="moveFocus('benNationCodeOption');" />
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
                                            <td id="iss" colspan="3">
                                            	<div id="idnChkNoteContent">
                                        		<span class="issuetitle_L_down">身分查核年月：</span>
                                        		<span class="formtxt">
                                        		<html:radio property="idnChkNote" value="1" onclick="$('idnChkNote').value='1'; Toggle.toggleIdnChkNote();"/>自動遞延13個月
                                        		<html:radio property="idnChkNote" value="0" onclick="$('idnChkNote').value='0'; Toggle.toggleIdnChkNote();"/>取消遞延
                                        		<html:radio property="idnChkNote" value="2" onclick="$('idnChkNote').value='2'; Toggle.toggleIdnChkNote();"/>手動調整
                                            	<div id="chkForeigner" style="display: inline;">
                                            		<html:text property="idnChkYear" styleId="idnChkYear" styleClass="textinput" size="3" maxlength="3" onblur="this.value=asc(this.value);"/>年
                                            		<html:text property="idnChkMonth" styleId="idnChkMonth" styleClass="textinput" size="2" maxlength="2" onblur="this.value=asc(this.value);"/>月
                                        		</div>
                                        		</span>
                                    			</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" id="iss">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">給付方式：</span>
                                    			<span class="formtxt">
                                        			<html:radio styleId="payCategory1" property="payCategory" value="1" onclick="$('payCategory').value='1';Toggle.togglePayCategory();"/>本人領取
                                        			<html:select property="payTyp" styleId="payTyp" styleClass="formtxt" onblur="tabChange();">
                                            			<html:option value="">請選擇</html:option>
                                            			<html:options collection="<%=ConstantKey.PAYTYP_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                        			</html:select>
                                                    <div id="coreceiveSection" style="display: inline;">
                                        			<html:radio styleId="payCategory2" property="payCategory" value="2" onclick="$('payCategory').value='2';Toggle.togglePayCategory();"/>具名領取
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
                                		                 <input type="checkbox" id="specialAcc" name="specialAcc" value="Y">專戶
                    	                            </div>
                                    			</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" id="iss">
                                                <div id="accountContent1">
                                                    <span class="needtxt">＊</span><span class="issuetitle_L_down">帳　號：</span>
                                                    <html:text property="payBankId" styleId="payBankId" styleClass="textinput" size="1"  maxlength="3"  onchange="this.value=Trim($('payBankId').value)" onblur="this.value=asc(this.value);Toggle.toggleAccNameOnAccountReset();" onkeyup="autoTabChange();"/>&nbsp;-
                                                    <html:text property="branchId"  styleId="branchId"  styleClass="textinput" size="1"  maxlength="4"  onchange="this.value=Trim($('branchId').value)"  onblur="this.value=asc(this.value);Toggle.toggleAccNameOnAccountReset();" onkeyup="autotab($('branchId'), $('payEeacc'))"/>&nbsp;-
                                                    <html:text property="payEeacc"  styleId="payEeacc"  styleClass="textinput" size="14" maxlength="14" onchange="this.value=Trim($('payEeacc').value)"  onblur="this.value=asc(this.value);Toggle.toggleAccNameOnAccountReset();"/>
                                                    <html:hidden styleId="payBankIdBranchId" property="payBankIdBranchId"/>
                                                </div>
                                                <div id="accountContent2">
                                        			<span class="needtxt">＊</span><span class="issuetitle_L_down">帳　號：</span>
                                        			<html:text property="payAccount" styleId="payAccount" styleClass="textinput" size="21" maxlength="21" onblur="this.value=asc(this.value).toUpperCase();"/>
                                        			&nbsp;
                                        			<span class="needtxt">＊</span><span class="issuetitle_L_down">金融機構名稱：</span>
                                        			<html:text property="bankName" styleId="bankName" styleClass="textinput" size="50" maxlength="120" onblur="this.value=asc(this.value).toUpperCase();"/>                                   　
                                    			</div>
                                                <div id="accountContent3">
                                                	<span class="needtxt">＊</span><span class="issuetitle_L_down">戶　名：</span>
                                                	<html:text property="accName" styleId="accName" styleClass="textinput" size="50" maxlength="50" onblur="this.value=asc(this.value).toUpperCase();"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">　<span class="issuetitle_L_down">電　話1：</span>
                                            	<html:text property="tel1" styleId="tel1" styleClass="textinput" size="20" maxlength="20" />
                                            </td>
                                            <td id="iss" colspan="2">
                                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">電　話2：</span>
                                                <html:text property="tel2" styleId="tel2" styleClass="textinput" size="20" maxlength="20" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">地　址：</span>
                                                <span class="formtxt" id="rdocommtyp1">
                                                    <html:radio property="commTyp" value="1" onclick="$('commTyp').value='1'; Toggle.toggleCommTyp();"/>同戶籍地
                                                </span>
                                                <span class="formtxt" id="rdocommtyp2">
                                                    <html:radio property="commTyp" value="2" onclick="$('commTyp').value='2'; Toggle.toggleCommTyp();"/>現住址
                                                </span>
                                            </td>
                                            <td id="iss" colspan="2">
                                                <div class="formtxt" id="addContent1">
                                                    &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">現住址：</span>
                                                    <html:text property="commZip" styleId="commZip" styleClass="textinput" size="6" maxlength="6" onchange="this.value=Trim($('commZip').value)" onblur="this.value=asc(this.value);"/>(郵遞區號)&nbsp;-
                                            		<html:text property="commAddr" styleId="commAddr" styleClass="textinput" size="72" maxlength="240" onchange="this.value=Trim($('commAddr').value)" onblur="this.value=asc(this.value);"/>
                                                </div>
                                                <div class="formtxt" id="addContent2">
                                                    &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">現住址：</span>
                                                    <html:text property="commZip" styleId="commZip" styleClass="disabled" size="6" maxlength="6" onchange="this.value=Trim($('commZip').value)" onblur="this.value=asc(this.value);" onfocus="moveFocus('grdName');"/>(郵遞區號)&nbsp;-
                                                    <html:text property="commAddr" styleId="commAddr" styleClass="disabled" size="60" maxlength="240" onchange="this.value=Trim($('commAddr').value)" onblur="this.value=asc(this.value);" onfocus="moveFocus('grdName');"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                                <span id="benMarrMkSection" style="display: none;">
                                                    <span class="issuetitle_L_down">　婚姻狀況：</span>
                                                    <span class="formtxt">
                                                	    <html:radio property="benMarrMk" value="Y" onclick="$('benMarrMk').value='Y'; "/>已婚
                                                	    <html:radio property="benMarrMk" value="N" onclick="$('benMarrMk').value='N'; "/>未婚
                                                    </span>
                                                </span>
                                            </td>
                                            <td id="iss" colspan="2">　<span class="issuetitle_L_down">法定代理人姓名：</span>
                                            <html:text property="grdName" styleId="grdName" styleClass="textinput" size="50" maxlength="50"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">　<span class="issuetitle_L_down">法定代理人身分證號：</span>
                                            <html:text property="grdIdnNo" styleId="grdIdnNo" styleClass="textinput" size="25" maxlength="20" onblur="this.value=Trim(asc(this.value).toUpperCase());"/>
                                            </td>
                                            <td id="iss" colspan="2">　<span class="issuetitle_L_down">法定代理人出生日期：</span>
                                            <html:text property="grdBrDate" styleId="grdBrDate" styleClass="textinput" size="20" maxlength="7"/>
                                            </td>
                                        </tr>
                                        <%--
                                        <tr>
                                            <td id="iss" colspan="3">
                                                <span class="issuetitle_L_down">　結案原因：</span>
                                                <html:select property="closeCause" styleId="closeCause" styleClass="formtxt">
                                        			<html:option value="">請選擇</html:option>
                                        			<html:options collection="<%=ConstantKey.CLOSECAUSE_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                    			</html:select>
                                            </td>
                                        </tr>
                                         --%>
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
