<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html>
    <head>
    <acl:setProgId progId="BAMO0D183C" />
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
    var today = "<%=DateUtility.getNowChineseDate()%>";

    function toggleReadonlyFieldForEvt(){
        var benEvtRel = '<c:out value="${DisabledPayeeDataUpdateDetailCase.benEvtRel}" />';
        var frm = document.forms['DisabledPayeeDataUpdateDetailForm'];

        if (benEvtRel == '1'){
            $("appDateSection").style.display = "none";
            if ($('evtDieDate') && Trim($F('evtDieDate')) !== '') {
                for (var i = 0; i < frm.elements.length; i++) {
                    var e = frm.elements[i];
                    if (e.type) {
                        if (e.type === 'text' || e.type === 'select-one' || e.type === 'radio' || e.type === 'button') {
                            e.disabled = true;
                        }
                    }
                }
                frm['btnBack'].disabled = false;

            } else {
                $("benEvtRel").disabled = true;
                $("benName").disabled = true;
                $("benIdnNo").disabled = true;
                $("benBrDate").disabled = true;
                if ($("evtDieDate")) {
                    $("evtDieDate").disabled = true;
                }
                frm['benNationTyp'][0].disabled = true;
                frm['benNationTyp'][1].disabled = true;
                frm['benNationCode'].disabled = true;
                frm['benNationCodeOption'].disabled = true;
                if(frm['benSex'] != null){
                	frm['benSex'][0].disabled = true;
                	frm['benSex'][1].disabled = true;
                }

            }
        } else {
            $("appDateSection").style.display = "inline";
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
        if($("payCategory1").checked){//本人領取
            $("accSeqNo").value='';
            $("payBankId").value ='';
            $("branchId").value ='';
            $("payEeacc").value = '';
            $("payAccount").value = '';
            $("payTyp").disabled = false;
            $("accSeqNo").disabled = true;
        }
        if($("payCategory2").checked){//具名領取
            $("payTyp").value="";
            $("payBankId").value ="";
            $("branchId").value ="";
            $("payAccount").value="";
            $("payEeacc").value="";
            $("bankName").value="";
            $("accName").value="";
            $("accountContent1").style.display = "none";
            $("accountContent2").style.display = "none";
            $("accountContent3").style.display = "none";
            $("payTyp").disabled = true;
            $("accSeqNo").disabled = false;

        }
    }

    //給付方式初始化
    function initPayType(){
        var payTyp = $("payTyp").value;
        $("accSeqNo").value='<c:out value="${DisabledPayeeDataUpdateDetailCase.seqNo}" />';
        $("payBankIdBranchId").value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.payBankId}"/>' + '<c:out value="${DisabledPayeeDataUpdateDetailCase.branchId}" />';
        $("payBankId").value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.payBankId}"/>';
        $("branchId").value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.branchId}" />';
        $("payEeacc").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.payEeacc}" />';
        $("bankName").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.bankName}" />';
        if(payTyp=="1"||payTyp=="2"){
            $("accountContent1").style.display="inline";
            $("accountContent2").style.display="none";
            $("accountContent3").style.display = "inline";
            $("payAccount").value = "";
        	$("bankName").value = "";
        	$("specialAccContent").style.display="inline";
        	if($("origSpecialAcc").value == "Y"){
                 $("specialAccAfter").checked = true;
            }
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
            $("specialAccContent").style.display="none";
        }
        if(payTyp=="5"||payTyp=="6"){
            $("accName").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.accName}" />';
            $("payBankIdBranchId").value="";
            $("payAccount").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.payBankId}" />' + '<c:out value="${DisabledPayeeDataUpdateDetailCase.branchId}" />' + '<c:out value="${DisabledPayeeDataUpdateDetailCase.payEeacc}" />';
        	$("bankName").value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.bankName}" />';
            $("accountContent1").style.display="none";
            $("accountContent2").style.display="inline";
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
        $("accSeqNo").value='';
        $("payBankId").value ='';
        $("branchId").value ='';
        $("payEeacc").value = '';
        $("bankName").value = '';
        $("payAccount").value = '';
        //$("accName").value="";
        if(payTyp=="1"||payTyp=="2"){
            if ($("accName").value == '') {
                if($('benName')!=null){
                    $("accName").value = $("benName").value;
                }
            }
            $("accountContent1").style.display="inline";
            $("accountContent2").style.display="none";
            $("accountContent3").style.display="inline";
            $("specialAccContent").style.display="inline";
        }else{
            $("specialAccContent").style.display="none";
        }
        if(payTyp==""||payTyp=="3"||payTyp=="4" || payTyp=="A"){
            $("accountContent1").style.display="none";
            $("accountContent2").style.display="none";
            $("accountContent3").style.display="none";
            $("specialAccContent").style.display="none";
        }
        if(payTyp=="5"||payTyp=="6"){
            $("accountContent1").style.display="none";
            $("accountContent2").style.display="inline";
            $("accountContent3").style.display="inline";
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

    // 變更 通訊地址別 時畫面異動
    function changeCommTyp(){
        if($("commTyp").value=="1"){
            $("commZip").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.commZip}" />';
            $("commAddr").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.commAddr}" />';
            $("addContent1").style.display="none";
        	$("addContent2").style.display="inline";
            DWREngine.setAsync(false);

			if(!isValidDate($("benBrDate").value)){
                alert('輸入之「受款人出生日期」錯誤，請重新確認。\r\n');
            }else{
	            CVLDTLUtil.getCvldtlZip();
	            CVLDTLUtil.getCvldtlAddr();
	            $("commZip").disabled = true;
	            $("commAddr").disabled = true;
	            document.getElementsByName("commTyp")[0].checked=true;
	            if(Trim($("commAddr").value) == ""){//如果沒抓到戶籍地址資料,則跳回現住址並提示使用者
	                $("commTyp").value = "2";
	                alert('戶籍地住址無資料，請選擇「現住址」並輸入住址資料。\r\n');
	                document.getElementsByName("commTyp")[1].checked=true;
	                changeCommTyp();
	                $("commZip").value = "";
	                $("commAddr").value = "";
	            }
	        }
        }
        if($("commTyp").value=="2"){
            $("commZip").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.commZip}" />';
            $("commAddr").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.commAddr}" />';
        	$("addContent1").style.display="inline";
        	$("addContent2").style.display="none";
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
            document.getElementsByName("commTyp")[1].checked=true;
        }
    }

    // 初始化 國籍別
    function initBenNationTpe(){
        var frm = document.forms['DisabledPayeeDataUpdateDetailForm'];
        if($("benNationTyp").value=="1"){
        	$("benSex").value = "";
        	$("benNationCode").value = "";
            $("sexContent").style.display="none";
            $("nationalityContent").style.display="none";
            document.getElementsByName("benNationTyp")[0].checked=true;
            frm['commTyp'][0].disabled = false;
        }
        if($("benNationTyp").value=="2"){
        	$("benSex").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.benSex}" />';
        	$("benNationCode").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.benNationCode}" />';
        	var benNation = document.getElementsByName("benNationCodeOption")[0];
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
            frm['commTyp'][0].disabled = true;
        }
    }

    // 變更 國籍別 時畫面異動
    function changeBenNationTpe(){
        var frm = document.forms['DisabledPayeeDataUpdateDetailForm'];
        $("benSex").value = "";
        $("benNationCode").value = "";
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

    function toggleAccNameOnAccountReset() {
        if ($('accName') != null && $("accName").value == '') {
            if($('benName') != null){
                $("accName").value = $("benName").value;
            }
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

    // 若「關係」<>1 且未滿18歲者之受款人,「婚姻狀況」,婚姻狀況預設"未婚"
    var DefaultMarritalStatusSetter = {
        isNotAdult : function() {
            var benBrDate = calDay($("benBrDate").value, 0);
            var sYearEighteen = '<%=DateUtility.calYear( DateUtility.getNowChineseDate().substring(0, 5).concat( String.valueOf(DateUtility.lastDay(DateUtility.getNowChineseDate())) ) ,-18)%>';
            return benBrDate > sYearEighteen;
        },
        initMarritalStatus : function(){
        	var mk = '<c:out value="${DisabledPayeeDataUpdateDetailCase.benMarrMk}"/>';
            if (( $F('benEvtRel') === '4' || $F('benEvtRel') === '6' || $F('benEvtRel') === '7') && DefaultMarritalStatusSetter.isNotAdult()) {
                $("benMarrMkSection").show();
                if( mk == 'Y') {
                	document.getElementsByName("benMarrMk")[0].checked = true;
            	} else if ( mk == 'N') {
                	document.getElementsByName("benMarrMk")[1].checked = true;
            	}
            }
        },
        checkMarritalStatus : function() {
            $("benMarrMkSection").show();
            document.getElementsByName("benMarrMk")[1].checked = true;
        },
        unCheckMarritalStatus : function() {
        	var mk = '<c:out value="${DisabledPayeeDataUpdateDetailCase.benMarrMk}"/>';
            document.getElementsByName("benMarrMk")[0].checked = false;
            document.getElementsByName("benMarrMk")[1].checked = false;
            $("benMarrMk").value = '';
            $("benMarrMkSection").hide();
        },
        setDefaultMarritalStatus : function() {
            if (( $F('benEvtRel') === '4' || $F('benEvtRel') === '6' || $F('benEvtRel') === '7') && DefaultMarritalStatusSetter.isNotAdult()) {
                DefaultMarritalStatusSetter.checkMarritalStatus();
            } else {
                DefaultMarritalStatusSetter.unCheckMarritalStatus();
            }
        }
    };

    function checkRequireFields() {
        var nowDate = "<%=DateUtility.getNowChineseDate()%>";
        var nowDateYM = nowDate.substring(0,5);
        var benBrDate = calDay($("benBrDate").value,0);
        var sYearEighteen = '<%=DateUtility.calYear( DateUtility.getNowChineseDate().substring(0, 5).concat( String.valueOf(DateUtility.lastDay(DateUtility.getNowChineseDate())) ) ,-18)%>';
        var benSeqNo = '<c:out value="${DisabledPayeeDataUpdateDetailForm.seqNo}" />';
        var evtSeqNo = '<c:out value="${DisabledPayeeDataUpdateQueryCase[0].seqNo}" />';
        var isEvtDieDateUpdatable = '<c:out value="${isEvtDieDateUpdatable}" />';
        var evtIdnNo = '<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtIdnNo}" />';
        var evtBrDateChineseString = '<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtBrDateChineseString}" />';
        var evtDieDateChineseString = '<c:out value="${DisabledPayeeDataUpdateQueryCase[0].evtDieDateStr}" />';
        var frm = document.forms['DisabledPayeeDataUpdateDetailForm'];
        var payKind = '<c:out value="${DisabledPayeeDataUpdateDetailCase.payKind}"/>';
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

            //檢核必輸欄位
            validator.isRequiredValueEmpty('「國籍別」', frm['benNationTyp']);
            validator.isRequiredValueEmpty('「受款人姓名」', $('benName'));
            validator.isRequiredValueEmpty('「受款人身分證號」', $('benIdnNo'));
            validator.isRequiredValueEmpty('「受款人出生日期」', $('benBrDate'));
            validator.isZipFormatInvalid($F('commZip'));

            //檢核身分證號
            /*if( evtSeqNo != benSeqNo ){
                validator.isNotTheSame( [{name : '「受款人身份證號與生日」', value : (Trim($F("benIdnNo")) + Trim($F("benBrDate"))) },
                                         {name : '「事故者身份證號與生日」', value : (Trim(evtIdnNo) + Trim(evtBrDateChineseString)) } ] );
                validator.isNotTheSame( [{name : '「受款人身份證號」', value : (Trim($F("benIdnNo")) ) },
                                         {name : '「事故者身份證號」', value : Trim(evtIdnNo)  } ] );
            }*/

            if( evtSeqNo != benSeqNo ){
                validator.isNotTheSame( [{name : '「受款人身份證號」', value : (Trim($F("benIdnNo")) ) },
                                         {name : '「事故者身份證號」', value : Trim(evtIdnNo)  } ] );
            }

            /*validator.isNotTheSame( [{name : '「法定代理人身份證號與生日」', value : Trim($F('grdIdnNo') +Trim($F('grdBrDate')) )},
                                     {name : '「受款人身份證號與生日」',     value : (Trim($F("benIdnNo")) + Trim($F("benBrDate"))) } ] );*/

            validator.isNotTheSame( [{name : '「法定代理人身份證號」', value : Trim($F('grdIdnNo')  )},
                                     {name : '「受款人身份證號」',     value : (Trim($F("benIdnNo")) ) } ] );

			validator.isNotTheSame( [{name : '「法定代理人身份證號」', value : Trim($F('grdIdnNo')  )},
                                     {name : '「事故者身份證號」',     value : Trim(evtIdnNo) } ] );

            if (!validator.isRequiredValueEmpty('「繼承人申請日期」', $('appDate'))) {
                if(payKind !== '36'){
                validator.isGreaterThanOrEqual({name : '「繼承人申請日期」', value : $F('appDate')},
                                               {name : '「系統日期」', value : '0980101'});
                }
                validator.isGreaterThanOrEqual({name : '「繼承人申請日期」', value : $F('appDate')},
                                               {name : '「事故者死亡日期」', value : evtDieDateChineseString});
                validator.isGreaterThanOrEqual({name : '「繼承人申請日期」', value : $F('appDate')},
                                               {name : '「出生日期」', value : Trim($F("benBrDate"))});
                validator.isSmallerThanOrEqual({name : '「繼承人申請日期」', value : $F('appDate')},
                                               {name : '「系統日期」', value : nowDate});
            }

            if (frm['benNationTyp'][1].checked) {
                validator.isRequiredValueEmpty('「性別」', $('benSex'));
                if (!validator.isRequiredValueEmpty('「國籍」', $('benNationCode'))) {
                    validator.isAlphaNumericValue('「國籍」', $F('benNationCode'))
                }
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

            if ((!frm['payCategory'][0].checked && !frm['payCategory'][1].checked) ||
                 (frm['payCategory'][0].checked && Trim($F('payTyp')) === '') ||
                 (frm['payCategory'][1].checked && Trim($F('accSeqNo')) === '')) {
                validator.isRequiredValueEmpty('「給付方式」', $('payTyp'));
            }

            if (benBrDate > sYearEighteen) {
                if (( $F('benEvtRel') === '4' || $F('benEvtRel') === '6' || $F('benEvtRel') === '7')) {
                    validator.isRequiredValueEmpty('「婚姻狀況」', frm['benMarrMk']);
                }
                if (frm['benMarrMk'][1].checked) {
                    validator.isRequiredValueEmpty('「法定代理人姓名」', $('grdName'));
                    validator.isRequiredValueEmpty('「法定代理人身分證號」', $('grdIdnNo'));
                    validator.isRequiredValueEmpty('「法定代理人出生日期」', $('grdBrDate'));
                    validator.isSmallerThanOrEqual({name : '「法定代理人出生日期」', value : $F('grdBrDate')},
                                                   {name : '「系統日期」', value : nowDate});
                }
            }

            if ($('benBrDate') != null && $F('benBrDate') !== '') {
                validator.isSmallerThanOrEqual({name : '「受款人出生日期」', value : $F('benBrDate')},
                                               {name : '「系統日期」', value : nowDate});
                switch ($F('benEvtRel')) {
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

            if ($('benDieDate') != null && $F('benDieDate') !== '') {
                validator.isChineseDateFormatValid('「受款人死亡日期」', $F('benDieDate'));
                validator.isGreaterThanOrEqual({name : '「受款人死亡日期」', value : $F('benDieDate')},
                                               {name : '「繼承人申請日期」', value : $F('appDate')});
                validator.isSmallerThanOrEqual({name : '「受款人死亡日期」', value : $F('benDieDate')},
                                               {name : '「系統日期」', value : nowDate});
            }
            if ($('evtDieDate') != null && $F('evtDieDate') !== '') {
                validator.isChineseDateFormatValid('「事故者死亡日期」', $F('evtDieDate'));
            }

            if ($('commTyp').value === '1') {
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

            if (frm['benNationTyp'][1].checked) {
                if ($('benNationCode') != null && Trim($('benNationCode').value) == '000') {
                    validator.addErrorMsg('「國籍別」為外籍不得輸入中華民國之國籍代碼');
                }
            }

             if (document.getElementsByName("benNationTyp")[0].checked==true){
	                 if(!isValidIdNoForTest($("benIdnNo").value) || !chkPID_CHAR($("benIdnNo").value))
	                 	 validator.addErrorMsg('「受款人身分證號(保險證號)」輸入有誤，請輸入長度為10 碼且符合格式的資料。');

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
                validator.isRequiredValueEmpty('「戶名」', $('accName'));
            }
            /*
            //2009.11.14 added
            //當該事故者的繼承人資料已登錄且核付,則事故者的「死亡日期」欄位資料不得為空,僅為修改事故者的「死亡日期」欄位資料,
            //如要將該欄位清空,存檔時要POP Msg:[繼承人(受款人)資料已核付。事故者的「死亡日期」不得為空];
            //若該受理案件的繼承人資料已登錄未核付時,則事故者的「死亡日期」欄位資料可清為空白,
            //但事故者的「死亡日期」欄位清為空白時,存檔時要POP Msg:[繼承人(受款人)資料已登錄。
            //若清空事故者的死亡日期時,會一併刪除相關繼承人(受款人)的資料](無繼承人(受款人)資料時,不POP Msg)。
            //2009.11.2x  updated
            //如果是事故者本人,則死亡日期為唯讀不給改
            //不要跑這段
            if ('<c:out value="${DisabledPayeeDataUpdateDetailForm.seqNo}" />' == '0000') { //事故者才需要檢查
                if ($('evtDieDate') != null && $F('evtDieDate') == '' && '<c:out value="${DisabledPayeeDataUpdateDetailForm.evtDieDate}" />' != '') {
                    if (isEvtDieDateUpdatable == 'false')
                        msg += '繼承人(受款人)資料已核付。事故者的「死亡日期」不得為空';
                    else
                        confirmMsg += '繼承人(受款人)資料已登錄。若清空事故者的死亡日期時,會一併刪除相關繼承人(受款人)的資料';
                }
            }*/

        }

        if (validator.getErrorMsgs().length !== 0) {
            alert(validator.getErrorMsgsAsString());
            return false;
        } else {
            if(chkEvtBrDate()){
            return true;
            }else{
            return false;
            }
        }

    }

    function moveFocus(elementId) {
        var frm = document.forms['DisabledPayeeDataUpdateDetailForm'];
        frm[elementId].focus();
    }

    var setSelectedCountryOnBenNationCodeChange = function() {
        var frm = document.forms['DisabledPayeeDataUpdateDetailForm'];
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
    };

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
        document.DisabledPayeeDataUpdateDetailForm.reset();
        initAll();
    }

    function initAll(){
        tabChange();
        checkIdnoExist();
        $('benNationTyp').value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.benNationTyp}"/>';
        $('commTyp').value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.commTyp}" />';

        // 給付方式
        var accRel = '<c:out value="${DisabledPayeeDataUpdateDetailCase.accRel}" />';
        if(accRel == '3'){
        	$("payCategory").value = "2";
        	document.getElementsByName("payCategory")[1].checked=true;
        } else if(accRel == '1') {
        	$("payCategory").value = "1";
        	document.getElementsByName("payCategory")[0].checked=true;
        }
        initBenNationTpe();
        // 具名領取
        var disableCoreceive = '<c:out value="${DisabledPayeeDataUpdateQueryCaseQ2}" />';
        if(disableCoreceive == "true"){
            $("payCategory2").disabled = true;
            $("accSeqNo").disabled = true;
        }

        var toHideCoreceiveNameListDropDownMenu = '<c:out value="${toHideCoreceiveNameListDropDownMenu}" />';
        if(toHideCoreceiveNameListDropDownMenu == "true"){
            $("coreceiveSection").style.display="none";
        }

        changeCommTyp();

        //領取人初始化
        if($("payCategory").value=="1"){//本人領取
            $("accSeqNo").value='<c:out value="${DisabledPayeeDataUpdateDetailCase.seqNo}" />';
            $("payBankIdBranchId").value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.payBankId}"/>' + '<c:out value="${DisabledPayeeDataUpdateDetailCase.branchId}" />';
            $("payBankId").value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.payBankId}"/>';
            $("branchId").value ='<c:out value="${DisabledPayeeDataUpdateDetailCase.branchId}" />';
            $("payEeacc").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.payEeacc}" />';
            $("payAccount").value = '<c:out value="${DisabledPayeeDataUpdateDetailCase.payBankId}" />' + '<c:out value="${DisabledPayeeDataUpdateDetailCase.branchId}" />' + '<c:out value="${DisabledPayeeDataUpdateDetailCase.payEeacc}" />';
            $("payTyp").disabled = false;
            $("accSeqNo").disabled = true;
            initPayType();
        }
        else if($("payCategory").value=="2"){//具名領取
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

            $("payTyp").disabled = true;
            var accSeqNoControl = '<c:out value="${DisabledPayeeDataUpdateQueryCaseAccSeqNoControl}" />';
            if(accSeqNoControl == "1"){
                document.getElementsByName("payCategory")[1].disabled = true;
                $("accSeqNo").disabled = true;
            } else {
                document.getElementsByName("payCategory")[1].disabled = false;
                $("accSeqNo").disabled = false;
            }

            //取得受款人accRel及accSeqNo之值,若accRel = '3'則表示有具名領取人物, 此時依其accSeqNo決定選單要選到哪個序號
            var accseq = '<c:out value="${DisabledPayeeDataUpdateDetailCase.accSeqNo}" />';
            var accseqOption = document.getElementsByName("accSeqNo")[0];
            for(var i = 0; i <accseqOption.options.length; i++){
                if(accseqOption.options[i].value == accseq){
                    accseqOption.options[i].selected = true;
                }
            }
        }

        toggleReadonlyFieldForEvt();
        // 身份查核年月 showCheckYm==true才會出現
        var showCheckYm = '<c:out value="${DisabledPayeeDataUpdateQueryCaseQ1}" />';
        if(showCheckYm == "true"){
            $("idnChkNoteContent").style.display="inline";
            initIdnChkNote();
        } else {
            $("idnChkNoteContent").style.display="none";
        }
        DefaultMarritalStatusSetter.initMarritalStatus();
        Event.observe('benBrDate', 'blur', DefaultMarritalStatusSetter.setDefaultMarritalStatus, false);
        Event.observe('benEvtRel', 'change', DefaultMarritalStatusSetter.setDefaultMarritalStatus, false);
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
			if(document.getElementsByName("benNationTyp")[1].checked && $("benIdnNo").value.length==10 &&
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
                        <legend>&nbsp;失能年金受款人資料修改&nbsp;</legend>
                        <div align="right" id="showtime">網頁下載時間：<func:nowDateTime /></div>
                        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
							<tr>
								<td align="right">
								    <bean:define id="payeeData" name="<%=ConstantKey.DISABLED_PAYEE_DATA_UPDATE_DETAIL_CASE%>"/>
									<html:hidden styleId="page" property="page" value="1" />
									<html:hidden styleId="method" property="method" value="" />
									<html:hidden styleId="idnoExist" property="idnoExist"/>
									<input type="hidden" id="origPayBankIdBranchId" name="origPayBankIdBranchId" value="<c:out value="${payeeData.payBankIdBranchId}" />"/>
									<input type="hidden" id="origPayBankId" name="origPayBankId" value="<c:out value="${payeeData.payBankId}" />"/>
									<input type="hidden" id="origBranchId" name="origBranchId" value="<c:out value="${payeeData.branchId}" />"/>
                       		    	<input type="hidden" id="origPayEeacc" name="origPayEeacc" value="<c:out value="${payeeData.payEeacc}" />"/>
                     			    <input type="hidden" id="origSpecialAcc" name="origSpecialAcc" value="<c:out value="${payeeData.specialAcc}" />"/>
									<acl:checkButton buttonName="btnSave">
										<input name="btnSave" type="button" class="button" value="確　認"
											onclick="checkIdnoExist();$('page').value='1'; $('method').value='doConfirm'; if(chkSpecialAcc()){if (document.DisabledPayeeDataUpdateDetailForm.onsubmit() && checkRequireFields()){
																								if($('payTyp').value=='4'){if(confirm('非行動不便之扣押戶，給付方式不得選擇匯票郵寄申請人，請重新選擇給付方式。按下【確定】時，不存檔並回到畫面重新選擇「給付方式」；按下【取消】時，確定「給付方式」為匯票郵寄並繼續存檔作業。')){return false;}else{document.DisabledPayeeDataUpdateDetailForm.submit();}
																								}else{document.DisabledPayeeDataUpdateDetailForm.submit();}}else{return false;}}else{return false;}" />&nbsp;
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
                                    			<c:out value="${DisabledPayeeDataUpdateQueryCase[0].sysCode}" />
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
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="2">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">受款人姓名：</span>
                                                <html:text property="benName" styleId="benName" styleClass="textinput" size="50" maxlength="50" onblur="this.value=asc(this.value);"/>
											</td>
											<td id="iss">
												<div id="appDateSection">
                                                	<span class="needtxt">＊</span><span class="issuetitle_L_down">繼承人申請日期：</span>
													<html:text property="appDate" styleId="appDate" styleClass="textinput" size="7" maxlength="7"/>
												</div>&nbsp;
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
                                                <c:if test="${DisabledPayeeDataUpdateDetailForm.seqNo eq '0000'}">
                                                	<html:text property="evtDieDate" styleId="evtDieDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                                                </c:if>
                                                <c:if test="${DisabledPayeeDataUpdateDetailForm.seqNo ne '0000'}">
                                                	<html:text property="benDieDate" styleId="benDieDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                                                </c:if>
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
                                            <td colspan="3" id="iss">
                                            	<div id="idnChkNoteContent">&nbsp;&nbsp;
                                                	<c:if test="${DisabledPayeeDataUpdateDetailForm.seqNo eq '0000'}">
                                                		<span class="issuetitle_L_down">身分查核年月
                                                            <c:if test='${not empty DisabledPayeeDataUpdateDetailForm.idnChkYear}'>
                                                                (<c:out value="${DisabledPayeeDataUpdateDetailForm.idnChkYear}"/>年<c:out value="${DisabledPayeeDataUpdateDetailForm.idnChkMonth}" />月)
                                                            </c:if>
                                                        	：</span>
                                                		<span class="formtxt">
                                                		<html:radio styleId="idnChkNote1" property="idnChkNote" value="1" onclick="$('idnChkNote').value='1'; chkIdnChkNote();"/>自動遞延13個月
                                                		<html:radio styleId="idnChkNote2" property="idnChkNote" value="2" onclick="$('idnChkNote').value='2'; chkIdnChkNote();"/>手動調整
                                                    	<div id="chkForeigner">
                                                    		<html:text property="idnChkYear" styleId="idnChkYear" styleClass="textinput" size="3" maxlength="3" onblur="this.value=asc(this.value);"/>年
                                                    		<html:text property="idnChkMonth" styleId="idnChkMonth" styleClass="textinput" size="2" maxlength="2" onblur="this.value=asc(this.value);"/>月
                                                		</div>
                                                         <html:hidden styleId="oldIdnChkYm" property="oldIdnChkYm" />
                                                         <html:hidden styleId="oldIdnChkNote" property="oldIdnChkNote" />
                                                         <input type="hidden" name="oldIdnChkYear" value="<c:out value="${DisabledPayeeDataUpdateDetailForm.idnChkYear}" />" >
                                                         <input type="hidden" name="oldIdnChkMonth" value="<c:out value="${DisabledPayeeDataUpdateDetailForm.idnChkMonth}" />" >
                                                		</span>
                                            		</c:if>
                                    			</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" id="iss">
                                                <span class="needtxt">＊</span><span class="issuetitle_L_down">給付方式：</span>
                                    			<span class="formtxt">
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
                                                	<html:text property="accName" styleId="accName" styleClass="textinput" size="50" maxlength="50" onchange="this.value=Trim($('accName').value)" onblur="this.value=asc(this.value).toUpperCase();"/>
                                    			</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">　<span class="issuetitle_L_down">電　話1：</span>
                                            	<html:text property="tel1" styleId="tel1" styleClass="textinput" size="20" maxlength="20" />
                                            </td>
                                            <td colspan="2" id="iss">
                                            	&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">電　話2：</span>
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
                                            <td colspan="2" id="iss">
                                                <div class="formtxt" id="addContent1">
                                                    &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">現住址：</span>
                                                    <html:text property="commZip" styleId="commZip" styleClass="textinput" size="6" maxlength="6" onchange="this.value=Trim($('commZip').value)" onblur="this.value=asc(this.value);"/>(郵遞區號)&nbsp;-
                                            		<html:text property="commAddr" styleId="commAddr" styleClass="textinput" size="72" maxlength="240" onchange="this.value=Trim($('commAddr').value)" onblur="this.value=asc(this.value);"/>
                                                </div>
                                                <div class="formtxt" id="addContent2">
                                                    &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">現住址：</span>
                                                	<html:text property="commZip" styleId="commZip" styleClass="disabled" size="6" maxlength="6" onchange="this.value=Trim($('commZip').value)" onfocus="moveFocus('grdName');" onblur="this.value=asc(this.value);"/>(郵遞區號)&nbsp;-
                                                	<html:text property="commAddr" styleId="commAddr" styleClass="disabled" size="60" maxlength="240" onchange="this.value=Trim($('commAddr').value)" onfocus="moveFocus('grdName');" onblur="this.value=asc(this.value);"/>
                                            	</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                                <div id="benMarrMkSection" style="display: none;">
                                                    &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">婚姻狀況：</span>
                                                    <span class="formtxt">
                                                    	<html:radio property="benMarrMk" value="Y" onclick="$('benMarrMk').value='Y'; "/>已婚
                                                    	<html:radio property="benMarrMk" value="N" onclick="$('benMarrMk').value='N'; "/>未婚
                                                    </div>
                                                </div>
                                            </td>
                                            <td colspan="2" id="iss">
                                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">法定代理人姓名：</span>
                                                                  <html:text property="grdName" styleId="grdName" styleClass="textinput" size="50" maxlength="50"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss">
                                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">法定代理人身分證號：</span>
                                                                  <html:text property="grdIdnNo" styleId="grdIdnNo" styleClass="textinput" size="25" maxlength="20" onblur="this.value=Trim(asc(this.value).toUpperCase());"/>
                                            </td>
                                            <td id="iss" colspan="2">
                                                &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">法定代理人出生日期：</span>
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
			                            	<td id="iss" colspan="3">
			                                    &nbsp;&nbsp;
			                                    <span class="issuetitle_L_down">來源受理編號：</span>
			                                    <c:out value="${DisabledPayeeDataUpdateQueryCase[0].apnoFm}" />
			                                </td>
			                            </tr>
                                        <tr>
                  							<td id="iss" colspan="3">
                  							    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                  							        <tr>
		                                                <td valign="top" width="10%">
		                                                    &nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">編審註記：</span>
		                                                </td>
		                                                <td align="left" valign="top" width="90%">
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


