<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
    <head>
        <acl:setProgId progId="BAMO0D073C" />
        <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
        <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
	    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
	    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
		<html:javascript formName="DependantDataUpdateDetailForm" page="1" />
        <script language="javascript" type="text/javascript">
            var today = "<%=DateUtility.getNowChineseDate()%>";
           
            function changeFamEvtRel(){
		    	if($("famEvtRel").value==""){
		    		$("interDictContentYM").style.display="none";  	//禁治產日期

		            $("interDictContentY").style.display="none"; 	//受禁治產宣告日期
		            $("interDictContent").style.display="none";		//禁治產Mk
		            $("raiseChildContent").style.display="none";	//配偶扶養
		            $("handIcapContent").style.display="none";		//領有重度以上身心障礙手冊或證明

		            $("studMkContent").style.display="none";		//在學Mk
		            $("divorceContent").style.display="none";		//離婚
		            $("marryContent").style.display="none";			//結婚
		            $("adoPtContent").style.display="none";		    //收養日期
		            //$("studMkSpaceContent").style.display="none";	//在學Space
		            //$("handIcapSpaceContent").style.display="none";	//領有重度以上身心障礙手冊或證明Space
		            $("studMkContent").style.display="none";
		            $("raiseChildSpace").style.display="none";		//配偶扶養Space
		            $("adoPtSpace").style.display="none";		    //收養日期Space
		    		$("studContent").style.display="none";
		    		$("schoolCodeContent").style.display="none";
		            $("marryDate").value = "";
		    		$("divorceDate").value = "";
		    		$("adoPtDate").value = "";
		    		$("studMk").value="";
		    		$("repealInterDictDate").value='';
		    		$("interDictDate").value='';
		    		$("raiseChildMk").checked=false;
		    		$("raiseChildMk").value='';
		    	}
		    	
		    	
		    
		    	if($("famEvtRel").value==2){
		    		$("handIcapContent").style.display="inline";
		    		$("interDictContent").style.display="inline";
		    		$("marryContent").style.display="inline";
		    		$("divorceContent").style.display="inline";
		    		$("raiseChildContent").style.display="none";
		    		//$("studMkSpaceContent").style.display="none";
		    		//$("handIcapSpaceContent").style.display="none";
		    		$("studMkContent").style.display="none";
		    		$("studContent").style.display="none";
		    		$("schoolCodeContent").style.display="none";
		    		$("adoPtContent").style.display="none";
		    		$("raiseChildSpace").style.display="none";		//配偶扶養Space
		    		$("adoPtSpace").style.display="inline";		    //收養日期Space
		    		$("adoPtDate").value="";
		    		$("studMk").value="";
		    		$("raiseChildMk").checked=false;
		    		$("raiseChildMk").value='';		    		
		    	}
		    	
		    	if($("famEvtRel").value==3 || $("famEvtRel").value==5){
		    		$("handIcapContent").style.display="none";
		    		$("interDictContent").style.display="none";
		    		$("marryContent").style.display="none";
		    		$("divorceContent").style.display="none";
		    		$("raiseChildContent").style.display="none";
		    		//$("studMkSpaceContent").style.display="none";
		    		//$("handIcapSpaceContent").style.display="none";
		    		$("studMkContent").style.display="none";
		    		$("studContent").style.display="none";
		    		$("schoolCodeContent").style.display="none";
		    		$("adoPtContent").style.display="none";
		    		$("marryDate").value = "";
		    		$("divorceDate").value="";
		    		$("adoPtDate").value="";
		    		$("studMk").value="";
		    		//$("studSdate").value="";
		    		//$("studEdate").value="";
		    		$("interDictMk").value="";
		    		$("interDictContentYM").style.display="none"; 
		            $("interDictContentY").style.display="none"; 
		            
		            $("raiseChildSpace").style.display="none";		//配偶扶養Space
		            $("adoPtSpace").style.display="none";		    //收養日期Space
		            $("interDictDate").value='';
		            $("repealInterDictDate").value='';
		            $("raiseChildMk").checked=false;
		    		$("raiseChildMk").value='';
		    		$("handIcapMk").value='';
		    		$("handIcapMk").checked=false;
		    	}
		    	
		    	if($("famEvtRel").value==4){
		    		$("handIcapContent").style.display="inline";
		    		$("interDictContent").style.display="inline";
		    		$("marryContent").style.display="none";
		    		$("divorceContent").style.display="none";
		    		$("raiseChildContent").style.display="inline";
		    		$("raiseChildSpace").style.display="none";		//配偶扶養Space
		    		//$("handIcapSpaceContent").style.display="inline";
		    		$("studMkContent").style.display="inline";	
		    		$("adoPtContent").style.display="inline";
		    		$("adoPtSpace").style.display="none";		    //收養日期Space
		    		$("marryDate").value = "";
		    		$("divorceDate").value="";
		    		if($("studMk").checked == true){
		    			$("studContent").style.display="inline";
		    			$("schoolCodeContent").style.display="inline";
		    		}else{
		    			$("studContent").style.display="none";
		    			$("schoolCodeContent").style.display="none";
		    		}	    		
		    	}
		    	
		    	if($("famEvtRel").value==6){
		    		$("handIcapContent").style.display="inline";
		    		$("interDictContent").style.display="inline";
		    		$("marryContent").style.display="none";
		    		$("divorceContent").style.display="none";
		    		$("raiseChildContent").style.display="none";
		    		//$("studMkSpaceContent").style.display="none";
		    		//$("handIcapSpaceContent").style.display="none";
		    		$("studMkContent").style.display="none";
		    		$("studContent").style.display="none";
		    		$("schoolCodeContent").style.display="none";
		    		$("adoPtContent").style.display="none";
		    		$("raiseChildSpace").style.display="none";		//配偶扶養Space
		    		$("adoPtSpace").style.display="inline";		    //收養日期Space
		    		$("marryDate").value = "";
		    		$("divorceDate").value = "";
		    		$("adoPtDate").value="";
		    		$("studMk").value="";
		    		$("raiseChildMk").checked=false;
		    		$("raiseChildMk").value='';
		    	}
		    	
		    	if($("famEvtRel").value==7){
		    		$("handIcapContent").style.display="inline";
		    		$("interDictContent").style.display="inline";
		    		$("marryContent").style.display="none";
		    		$("divorceContent").style.display="none";
		    		$("raiseChildContent").style.display="none";
		    		//$("handIcapSpaceContent").style.display="none";
		    		$("studMkContent").style.display="inline";
		    		$("adoPtContent").style.display="none";
		    		$("marryDate").value = "";
		    		$("divorceDate").value = "";
		    		$("adoPtDate").value="";
		    		$("raiseChildSpace").style.display="inline";		//配偶扶養Space
		    		$("adoPtSpace").style.display="inline";		    //收養日期Space
		    		$("raiseChildMk").checked=false;
		    		$("raiseChildMk").value='';
		    		
		    		if($("studMk").checked == true){
		    			$("studContent").style.display="inline";
		    			//$("studMkSpaceContent").style.display="none";
		    		}else{
		    			$("studContent").style.display="none";
		    			//$("studMkSpaceContent").style.display="inline";
		    		}
		    		$("schoolCodeContent").style.display="none";
		    	}
		    	
		    	
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

		        var res = window.showModalDialog('<c:url value="/bamo0d07/bamo0d076q.jsp"/>', argsObj, 'dialogWidth:560px;dialogHeight:500px;status:no');
		        
		        if (res != null) {
		            $("schoolCode").value = res.schoolCode;
		            $("schoolCodeOption").value = res.schoolCode;		        
		        }
		    }         	
            
            // 變更 國籍別 時畫面異動    
		    function changeFamNationTpe(){
		        
		        if($("studMk").checked == false && $("famNationTyp").value=="1"){
		    		document.getElementsByName("famNationTyp")[0].checked=true;
		        }     
		        if(document.getElementsByName("famNationTyp")[0].checked==true){
		        	document.getElementsByName("famSex")[0].checked=false;
		            document.getElementsByName("famSex")[1].checked=false;
		        	$("famNationTyp").value="1";
		            $("sexContent").style.display="none"; 
		            $("nationalityContent").style.display="none"; 	            
		        	$("famNationCode").value = '';
		        	$("famNationCodeOption").value = '';
		        	$("famNationCodeOption").value=$("famNationCode").value;
		        	
		        }
		        if(document.getElementsByName("famNationTyp")[1].checked==true){
		        	$("famNationTyp").value="2";
		            $("sexContent").style.display="inline";  
		            $("nationalityContent").style.display="inline";  
		            $("famNationCode").disabled = false;  
		            $("famNationCodeOption").value=$("famNationCode").value;
		        }
		        autoForeignBenSex();
		    }
		    
		    function changeNationCode(){
		    	$("famNationCodeOption").value=$("famNationCode").value;
		    }
            <%--
		    // 配偶扶養
            function checkRaiseChild(){
                if($("raiseChildMk").checked==true){
                    $('raiseChildMk').value='Y';
                }
                if($("raiseChildMk").checked==false){
                    $('raiseChildMk').value='';
                }
            }
            
            // 領有重度以上身心障礙手冊或證明 
            function checkHandIcap(){
                if($("handIcapMk").checked==true){
                    $('handIcapMk').value='Y';
                }
                if($("handIcapMk").checked==false){
                    $('handIcapMk').value='';
                }
            }
            --%>
            
            // 放棄請領
            function checkAbanApply(){
                if($("abanApplyMk").checked==true){
                    $("abanApplyContent").style.display="inline";
                    $("abanApplyMk").value='Y';
                }
                if($("abanApplyMk").checked==false){
                    $("abanApplyContent").style.display="none";
                    $("abanApplyMk").value='';
                    $("abanApsYm").value='';
                }
            }
            
            // 就學期間
		    function checkStud(){
		    	 if($("famEvtRel").value==4 || $("famEvtRel").value==7){
				    	if($("studMk").checked == true){
				    		$("studContent").style.display="inline";
				    		//$("studMkSpaceContent").style.display="none";
				    		$("studMk").value='Y';
				    	}
				    	if($("studMk").checked==false){
				    		$("studContent").style.display="none";
				    		//$("studMkSpaceContent").style.display="inline";
				    		$("studMk").value='';
				    		//$("studSdate").value='';
				    		//$("studEdate").value='';
				    	}
				  }
				  if($("famEvtRel").value==4){
				    	if($("studMk").checked == true){
				    		$("schoolCodeContent").style.display="inline";
				    		$("studMk").value='Y';
				    	}
				    	if($("studMk").checked==false){
				    	    $("schoolCodeContent").style.display="none";
				    		$("studMk").value='';
				    	}
				  }
		    }
		    
		   //禁治產審查註記

		    function interChanged(){
		    		        
		        if($("interDictMk").checked == true){
		        	$("interDictContentY").style.display="inline"; 
		        	$("interDictContentYM").style.display="inline"; 
		            $("interDictMk").value='Y';
		        }
		        
		        if($("interDictMk").checked==false){
		        	$("interDictContentYM").style.display="none"; 
		            $("interDictContentY").style.display="none"; 
		            $("interDictMk").value='';
		            $("interDictDate").value='';
		            $("repealInterDictDate").value='';
		            
		            
		        }
		        
		    }
		    
		    //每月工作收入註記
		    function monIncomeMkChk(){
		        
		       	if($("monIncomeMk").checked == true){
				   $("monIncome").disabled=false;
				   $("monIncomeMk").value='Y';
				}
		    	if($("monIncomeMk").checked==false){
		    		$("monIncomeMk").value='';
		    		$("monIncome").value='';
		       		$("monIncome").disabled=true;
		    		
		    	}
		    }
		    
		    //是否有重復的眷屬資料
		    function checkBafamily(){
		    	var apNo = "<c:out value="${DependantDataUpdateQueryCase[0].apNo}" />";
		    	var famIdnNo = $("famIdnNo").value;
		    	var famBrDate =  $("famBrDate").value;
		    	var bafamilyId = $("bafamilyId").value;
                updateCommonAjaxDo.getDependantDataCount(apNo, famIdnNo, bafamilyId, getStatus);       
        	}	
        	
        	 // Ajax for 取得 出生日期錯誤參數 確認是否有此筆資料P120436303 0480229  $("famIdnNo").value,$("famBrDate").value
    		    function checkIdnoExist() {   
        		    if(isNaN($("famBrDate").value) == false){
        		    updateCommonAjaxDo.checkIdnoExist($("famIdnNo").value,$("famBrDate").value,checkIdnoExistResult);
        		    }
    		    }
    		    function checkIdnoExistResult(idnoExist) {  
    		       $("idnoExist").value = idnoExist;
    		    }
        	
        	 // 變更 身分查核年月 時畫面異動    
		    function changeIdnChkNote(){
		        if($("idnChkNote1").checked==true){
		        	$("idnChkContent").style.display="none";
		        }else if($("idnChkNote2").checked==true){
                    $("idnChkContent").style.display="inline";
                    
                }else{
		        	$("idnChkContent").style.display="none";
		        	$("idnChkM").value="";
		        	$("idnChkY").value="";
		        }
		    }
    

		    function getStatus(bafamilyCount) {
		    	var msg = "";
		    	var apNo = "<c:out value="${DependantDataUpdateQueryCase[0].apNo}" />";
			    var famIdnNo = $("famIdnNo").value;
			    var famBrDate =  $("famBrDate").value;
			    var bafamilyId = $("bafamilyId").value;
		        if(bafamilyCount>0)
        			msg += '同一眷屬資料已存在。\r\n';
        	    if (msg != "") {
		            alert(msg);
		            return false;
		        }
		        else {
		        	//是否有重復的事故者資料
                    updateCommonAjaxDo.getEvtDataCount(apNo, famIdnNo, bafamilyId, getEvtStatus);     
		        }
        		    
		    }
		    
		    
		   function getEvtStatus(evtCount) {
		   
		    	var msg = "";
		        if(evtCount>0)
        			msg += '眷屬資料與事故者資料不得重覆。\r\n';
        	    if (msg != "") {
		            alert(msg);
		            return false;
		        }
		        else {
		        	
                    document.DependantDataUpdateDetailForm.submit();
		        }
        		    
		    }
		  
		        //檢核事故者出生日期 20121220 邏輯修改
    		    function isValidEvtDateTrue() {   
        		    var evtBrDate = $("famBrDate").value;
		           
        		    if(isValidDate($("famBrDate").value) == false){
        	
        		    if($("idnoExist").value == null || $("idnoExist").value == "" || $("idnoExist").value == "null"){
        		    alert("輸入之「眷屬出生日期」錯誤，請重新輸入");
        		    return false;
        		    }else{
          		    return true;
        		    }  
        		    }else{
        		      return true;
        		    }
    		    }
		    
		    <%-- begin 檢查必填欄位 --%>
    		function checkRequireFields() {
    			var nowDate = "<%=DateUtility.getNowChineseDate()%>";
    			var nowDateYM = nowDate.substring(0,5);
    			var brDate = '<c:out value="${DependantDataUpdateDetailForm.famBrDate}" />';
        		var evtBrDate = '<c:out value="${DependantEvtDataUpdateQueryCase[0].evtBrDateChineseString}" />';
        		var evtDieDate = '<c:out value="${DependantEvtDataUpdateQueryCase[0].evtDieDateStr}" />';
        		var famBrDate = calDay($("famBrDate").value,-1);
        		var msg = "";
        		var oldFamIdNo = "<c:out value="${DependantDataUpdateDetailCase.famIdnNo}" />";
        		var oldFamBrDate ="<c:out value="${DependantDataUpdateDetailCase.famBrDate}" />";
        		var studData ='<c:out value="${studStatus}" />';
        		var studResult = '<c:out value="${studResult}" />';
        		var checkData ='<c:out value="${checkStatus}" />';
        		var compelDataListSize = '<c:out value="${DEPENDANT_COMPELDATA_SIZE}" />';
				var secondText = $("famIdnNo").value.substring(1,2);
        		if($("famIdnNo").value.length==10){
        		if(document.getElementsByName("famNationTyp")[1].checked && document.getElementsByName("famSex")[0].checked==true){
					if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
						msg += '個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';	
						$("famSex").focus();
					}
				}else if(document.getElementsByName("famNationTyp")[1].checked && document.getElementsByName("famSex")[1].checked==true){
					if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
						msg += '個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';	
						$("famSex").focus();
						}
					}
				}
            	//如果強迫不合格有勾,但listSize為0,則提示警告訊息
            	if ($('compelMk') != null && $('compelMk').checked) {
                	if (compelDataListSize == 0) {
                		msg += '請輸入「不合格年月」資料。\r\n';                    	
                	}
            	}
            	//如果強迫不合格沒勾,但listSize不為0,則提示警告訊息
            	if ($('compelMk') == null || !$('compelMk').checked) {
                	if (compelDataListSize != 0) {
                		msg += '已有「不合格年月」資料，不得取消「強迫不合格」勾選。\r\n';
                	}
            	}
        	
        		if(Trim($("famEvtRel").value) == "")
	                msg += '「關係」：為必輸欄位。\r\n';
    			
    			if(Trim($("famBrDate").value) == ""){
                	msg += '「眷屬出生日期」：為必輸欄位。\r\n';
            	} else {
	                if(Trim($("famBrDate").value) > nowDate)
	                    msg += '「眷屬出生日期」：不得大於系統日期。\r\n';
	                if(Trim($("famBrDate").value)>Trim($("famAppDate").value))
	                	msg += '「眷屬出生日期」：不得大於「申請日期」。\r\n';
	                if(Trim($("famBrDate").value)>Trim($("famDieDate").value) && Trim($("famDieDate").value)!="")
	                	msg += '「眷屬出生日期」：不得大於「死亡日期」。\r\n';
	                if(($("famEvtRel").value==3 || $("famEvtRel").value==5) && Trim($("famBrDate").value)>=evtBrDate)
	                	msg += '「眷屬出生日期」：不得大於等於「事故者出生日期」。\r\n';
	                if(($("famEvtRel").value==4 || $("famEvtRel").value==7) && Trim($("famBrDate").value)<=evtBrDate)
	                	msg += '「眷屬出生日期」：不得小於等於「事故者出生日期」。\r\n';
            	}
            	
            	if(Trim($("famDieDate").value)!="" && $("famDieDate").value>nowDate)
            		msg +='「死亡日期」：不得大於系統日期。\r\n';
            	
            	            	
            	if(Trim($("relatChgDate").value) != "" && $("relatChgDate").value>nowDate)
            		msg +='「親屬關係變動日期」：不得大於系統日期。\r\n';
            	if(Trim($("relatChgDate").value) != "" && Trim($("relatChgDate").value) < Trim($("famBrDate").value))
            		msg +='「親屬關係變動日期」：不得小於眷屬出生日期。\r\n';
                
                if(Trim($("benMissingSdate").value) != ""){
                	if(Trim($("benMissingSdate").value)>nowDate)
                		msg += '「眷屬失蹤起迄期間」：不得大於系統日期。\r\n';
                	if(Trim($("benMissingSdate").value)>Trim($("benMissingEdate").value) && Trim($("benMissingEdate").value) != "")
                		msg +='「眷屬失蹤起」：不得大於「眷屬失蹤迄」。\r\n';
                }
                
                if(Trim($("benMissingEdate").value) != ""){
                	if(Trim($("benMissingEdate").value)>nowDate)
                		msg += '「眷屬失蹤起迄期間」：不得大於系統日期。\r\n';
                	if(Trim($("benMissingSdate").value)=="" && Trim($("benMissingEdate").value) != "")
                		msg +='「眷屬失蹤起」：為必填欄位。\r\n';
                }
                
                if(Trim($("prisonSdate").value) != ""){
                	if(Trim($("prisonSdate").value)>nowDate)
                		msg += '「監管起迄期間」：不得大於系統日期。\r\n';
                	if(Trim($("prisonSdate").value)>Trim($("prisonEdate").value) && Trim($("prisonEdate").value)!= "")
                		msg +='「監管起」：不得大於「監管迄」。\r\n';
                }
                
                if(Trim($("prisonEdate").value) != ""){
                	if(Trim($("prisonEdate").value)>nowDate)
                		msg += '「監管起迄期間」：不得大於系統日期。\r\n';
                	if(Trim($("prisonEdate").value) != "" && Trim($("prisonSdate").value) == "")
                		msg +='「監管起」：為必填欄位。\r\n';
                	
                }
                              
                if(Trim($("abanApplyMk").value)=='Y' && Trim($("abanApsYm").value)!=""){
                    if(!isValidYearMonth($("abanApsYm").value)){
                        msg += '輸入之「放棄請領-起始年月」錯誤，請重新確認。\r\n';
                    }else if(Trim($("abanApsYm").value)> nowDateYM){
                        msg += '「放棄請領-起始年月」：不得大於系統日期。\r\n';
                    }
                }else if(Trim($("abanApplyMk").value)=='Y' && Trim($("abanApsYm").value)==""){
                	msg += '「放棄請領-起始年月」：為必輸欄位。\r\n';
                }
                	
            	if(Trim($("famIdnNo").value) == "")
                	msg += '「眷屬身分證號」：為必輸欄位。\r\n';
            
	            if(document.getElementsByName("famNationTyp")[0].checked==false && document.getElementsByName("famNationTyp")[1].checked==false)
	                msg += '「國籍別」：為必輸欄位。\r\n';
            
	            if (document.getElementsByName("famNationTyp")[1].checked==true){
	                if(document.getElementsByName("famSex")[0].checked==false && document.getElementsByName("famSex")[1].checked==false)
	                    msg += '「性別」：為必輸欄位。\r\n';
	                if(Trim($("famNationCode").value) == "" || $("famNationCodeOption").value=="")
	                    msg += '「國籍」：為必輸欄位。\r\n';
	                if(Trim($("famNationCode").value) == "000")
	                	msg +='「國籍別」為外籍不得輸入中華民國之國籍代碼。\r\n';
	                if(Trim($("famNationCode").value) != "" && $("famNationCodeOption").value !="" && !isNationCode($("famNationCode").value))
	                	msg += '輸入「國籍」錯誤，請重新確認。\r\n';
	            }
	            
	             if (document.getElementsByName("famNationTyp")[0].checked==true){
	                 if(!isValidIdNoForTest($("famIdnNo").value) || !chkPID_CHAR($("famIdnNo").value))
	                 	msg +='「眷屬身分證號」輸入有誤，請輸入長度為10 碼且符合格式的資料。\r\n';
	                 
	             }
	            
	            if (document.getElementsByName("famNationTyp")[1].checked==true){
	             	if(!isEngNum($("famIdnNo").value)){
	             		msg +='「眷屬身分證號」格式錯誤。\r\n';
	             	}
	        	 }
	            
	            if(Trim($("famAppDate").value) == ""){
                msg += '「眷屬申請日期」：為必輸欄位。\r\n';
	            } else {
	                if($("famAppDate").value > nowDate)
	                    msg += '「眷屬申請日期」：不得大於系統日期。\r\n';
	                if($("famAppDate").value < "0980101")
	                    msg += '「眷屬申請日期」：不得小於0980101。\r\n';
	                if($("famAppDate").value<$("famBrDate").value)
	                	msg += '「眷屬申請日期」：不得小於眷屬出生日期。\r\n';
	            }
	            
	            if(Trim($("famName").value) == "")
            		msg += '「眷屬姓名」：為必輸欄位。\r\n';
            		
            	            	
            	if($("famEvtRel").value==2){
            		if(Trim($("marryDate").value) != ""){
	            		if(Trim($("marryDate").value)>nowDate)
	            			msg +='「結婚日期」：不得大於系統日期。\r\n';
	            	    if(Trim($("marryDate").value) > Trim($("divorceDate").value) && Trim($("divorceDate").value) != "")
	            	    	msg +='「結婚日期」：不得大於「離婚日期」。\r\n';
	            	    if(Trim($("marryDate").value)<Trim($("famBrDate").value))
	            	    	msg +='「結婚日期」：不得小於「眷屬出生日期」。\r\n';
	            	    if(Trim($("marryDate").value)>evtDieDate && evtDieDate!="")
	            	    	msg +='「結婚日期」：不得大於「事故者死亡日期」。\r\n';
	            	    if(Trim($("marryDate").value)<evtBrDate && evtBrDate!="")
	            	    	msg +='「結婚日期」：不得小於「事故者出生日期」。\r\n';
	            	}else if(Trim($("marryDate").value) == ""){
	            		msg += '「結婚日期」：為必輸欄位。\r\n';
	            	}
	            	if(Trim($("divorceDate").value) >nowDate)
                		msg += '「離婚日期」：不得大於系統日期。\r\n';
            	}
            	
            	if($("famEvtRel").value==4){
	            	if(Trim($("adoPtDate").value) != "" && Trim($("adoPtDate").value)>nowDate)
	            		msg +='「收養日期」：不得大於系統日期。\r\n';
	            	if(Trim($("adoPtDate").value) != "" && Trim($("adoPtDate").value)<Trim($("famBrDate").value))
            			msg +='「收養日期」：不得小於「眷屬出生日期」。\r\n';
            	}
            	
            	<%--if($("famEvtRel").value==4 || $("famEvtRel").value==7){
	            	if(Trim($("studMk").value)=="Y"){
		                    if(Trim($("studSdate").value) != ""){
		                        if(!isValidYearMonth($("studSdate").value)){
		                            msg += '輸入之「在學起迄年月」錯誤，請重新確認。\r\n';
		                        }else{
		                            if(Trim($("studSdate").value)>nowDateYM){
		                                msg += '「在學起迄年月」：不得大於系統日期。\r\n';
		                            }
		                            if($("studSdate").value>$("studEdate").value && Trim($("studEdate").value)!=""){
		                                msg +='「在學起」：不得大於「在學迄」。\r\n';
		                            }
		                        }
		                    }
		                    if(Trim($("studEdate").value) != ""){
		                    	if(!isValidYearMonth($("studEdate").value)){
		                            msg += '輸入之「在學起迄年月」錯誤，請重新確認。\r\n';
		                        }else if(Trim($("studEdate").value)>nowDateYM){
		                    		msg += '「在學起迄年月」：不得大於系統日期。\r\n';
		                        }
		                    }
	            	}
            	}--%>

            	if($("famEvtRel").value==4 || $("famEvtRel").value==7){
            		if($("studMk").checked == true && studData=="0" && Trim($("schoolCode").value)==""){
            			msg += '請輸入「在學起迄年月」資料。\r\n';
            		}else if($("studMk").checked == false && studData!="0"){
            			$("studMk").checked = true;
            			$("studContent").style.display="inline";
				    	//$("studMkSpaceContent").style.display="none";
				    	$("studMk").value='Y';
            			msg += '已有「在學起迄年月」資料，不得取消「在學」勾選。\r\n';
            		}else if (Trim($("schoolCode").value)!="" && studResult == "Y"){
            			msg += '在學起迄年月不可大於最大給付年月。\r\n';
            		}
            	}
            	
            	if($("famEvtRel").value==2 || $("famEvtRel").value==4 || $("famEvtRel").value==6 || $("famEvtRel").value==7){
            		if($("interDictMk").checked == true && Trim($("interDictDate").value) >nowDate)
                		msg += '「受禁治產(監護)宣告日期」：不得大於系統日期。\r\n';
                
	                if($("interDictMk").checked == true && Trim($("interDictDate").value)=="")
	                	msg += '「受禁治產(監護)宣告日期」：為必輸欄位。\r\n';
	                
	                if($("interDictMk").checked == true && Trim($("repealInterDictDate").value) !="" && Trim($("repealInterDictDate").value) >nowDate)
                		msg += '「受禁治產(監護)撤銷日期」：不得大於系統日期。\r\n';
                	
                	if($("interDictMk").checked == true && Trim($("repealInterDictDate").value) !="" && Trim($("interDictDate").value)!="" && Trim($("interDictDate").value)>Trim($("repealInterDictDate").value))
                		msg += '「受禁治產(監護)宣告日期」：不得大於「受禁治產(監護)撤銷日期」。\r\n';
            	}
            	
            	if(checkData>0){
		     		var idnChkYm=Trim($("idnChkY").value)+Trim($("idnChkM").value);
                    
                    var oldIdnChkYm = "";
                    if(Trim($("oldIdnChkYm").value)!=""){
                        oldIdnChkYm = changeDateType($("oldIdnChkYm").value+"01").substring(0 , 5);
                    }else{
                        oldIdnChkYm = today.substring(0 , 5);
                    }
                
                    if ($("idnChkNote1").checked==true) {
                        if(parseNumber(oldIdnChkYm) > parseNumber(today.substring(0 , 5))){
                            msg += '「身分查核年月」大於系統年月，不得點選自動遞延13個月。\r\n';
                            initIdnChkNote();
                        }                   
                    }
                    else if($("idnChkNote2").checked==true){
                        if(Trim($("idnChkY").value) == ""){
                            msg += '「身分查核年月年度」：為必輸欄位。\r\n';
                            initIdnChkNote();
                        }
                        if(Trim($("idnChkM").value) == ""){
                            msg += '「身分查核年月月份」：為必輸欄位。\r\n';
                            initIdnChkNote();
                        }
                        if(Trim($("idnChkY").value) != "" && Trim($("idnChkM").value) != ""){
                            if (!(parseNumber("" + $F("idnChkY") + $F("idnChkM")) >= parseNumber(today.substring(0 , 5)) && parseNumber("" + $F("idnChkY") + $F("idnChkM")) <= parseNumber(calMonth(today, 13).substring(0 , 5)))) {
                                msg += "「身分查核年月」需大於等於系統年月，且小於等於系統年月+13個月。\r\n";
                                initIdnChkNote();
                            }
                        }
                        
                    }
		     	}
		     	
		     	if($("famBrDate").value.length == 7){
                 var chkMonth = $("famBrDate").value.substring(3,5);
                 var chkDay   = $("famBrDate").value.substring(5,7);
                 if(chkMonth > 12 || chkDay > 32){
                          msg += '輸入之「眷屬出生日期」錯誤，請重新輸入\r\n';
                          $("famBrDate").focus();
                 }
              }else if($("famBrDate").value.length == 8){
                 var chkfrist = $("famBrDate").value.substring(0,1);
                 var chkMonth = $("famBrDate").value.substring(4,6);
                 var chkDay   = $("famBrDate").value.substring(6,8);
                 if(chkfrist != "-"){
                          msg += '輸入之「眷屬出生日期」錯誤，請重新輸入\r\n';
                          $("famBrDate").focus();
                         
                 }
                 if(chkMonth > 12 || chkDay > 32){
                          msg += '輸入之「眷屬出生日期」錯誤，請重新輸入\r\n';
                          $("famBrDate").focus();
                 }
              }    
              
              // 檢核得請領起月
              if(Trim($("ableApsYm").value) != ""){
                  // 
                  var ableApsYm = Trim($("ableApsYm").value);
                  var nowDateYm = "<%=DateUtility.getNowChineseDate().substring(0, 5)%>";
                  var appDate = '<c:out value="${DependantEvtDataUpdateQueryCase[0].appDateChineseString}"/>';
                  var appDateYm = "";
		          if(appDate != ""){
		             appDateYm = appDate.substring(0, 5);
		          }
		          var famAppDateYm = Trim($("famAppDate").value).substring(0, 5);
		          
                  if(ableApsYm > nowDateYm){
                      msg += '「得請領年月」：不得大於系統年月。\r\n';
                  }
                  if(ableApsYm < appDateYm){
                      msg += '「得請領年月」：不得小於「事故者申請日期年月」。\r\n';
                  }
                  if(ableApsYm < famAppDateYm){
                     // 計算月份差
                     var year1 =  ableApsYm.substr(0,3);
                     var year2 =  famAppDateYm.substr(0,3); 
                     var month1 = ableApsYm.substr(3,2);
                     var month2 = famAppDateYm.substr(3,2);
                     var len =(year2-year1)*12+(month2-month1);
                     // 計算眷屬申請年月-60個月
		             var len60Ym = calMonth(Trim($("famAppDate").value), -60).substring(0, 5);
		          
                     if((len > 60)){
                         msg += '「得請領年月」僅能大於等於'+len60Ym+'。 \r\n';
                     }
                  }
              } 
            		
                if (msg != "") {
		            alert(msg);
		            return false;
		        }
		        else {
                    if(isValidEvtDateTrue()){
                    return true;
                    checkBafamily();
                    }else{
                    return false;
                }
                  
		        }							
		    }
		    
		     function initAll(){
		        //學校代碼
		        changeSchoolCode();
		        //檢查生日錯誤檔
		        checkIdnoExist();
		     	// 國籍別
		        if(document.getElementsByName("famNationTyp")[0].checked){
		            $("famNationCode").disabled = true;
		            $("sexContent").style.display="none"; 
		            $("nationalityContent").style.display="none"; 
		        } else if(document.getElementsByName("famNationTyp")[1].checked){
		            $("sexContent").style.display="inline";  
		            $("nationalityContent").style.display="inline";  
		            $("famNationCodeOption").value=$("famNationCode").value;
		        } else { 
		            $("sexContent").style.display="none";
		            $("nationalityContent").style.display="none";	
		        }   
		        
		        monIncomeMkChk();
		        
		        if($("famEvtRel").value==2){
		    		$("handIcapContent").style.display="inline";
		    		$("interDictContent").style.display="inline";
		    		$("marryContent").style.display="inline";
		    		$("divorceContent").style.display="inline";
		    		$("raiseChildContent").style.display="none";
		    		//$("studMkSpaceContent").style.display="none";
		    		//$("handIcapSpaceContent").style.display="none";
		    		$("studMkContent").style.display="none";
		    		$("studContent").style.display="none";
		    		$("schoolCodeContent").style.display="none";
		    		$("adoPtContent").style.display="none";
		    		$("adoPtSpace").style.display="inline";		    //收養日期Space
		    		$("raiseChildSpace").style.display="none";		//配偶扶養Space
		    		interChanged();
		    	}
		    	
		    	if($("famEvtRel").value==3 || $("famEvtRel").value==5){
		    		$("handIcapContent").style.display="none";
		    		$("interDictContent").style.display="none";
		    		$("marryContent").style.display="none";
		    		$("divorceContent").style.display="none";
		    		$("raiseChildContent").style.display="none";
		    		//$("studMkSpaceContent").style.display="none";
		    		//$("handIcapSpaceContent").style.display="none";
		    		$("studMkContent").style.display="none";
		    		$("studContent").style.display="none";
		    		$("schoolCodeContent").style.display="none";
		    		$("adoPtContent").style.display="none";
		    		$("interDictContentYM").style.display="none"; 
		            $("interDictContentY").style.display="none"; 
		            $("raiseChildSpace").style.display="none";		//配偶扶養Space	
		            $("adoPtSpace").style.display="none";		    //收養日期Space
		    	}
		    	
		    	if($("famEvtRel").value==4){
		    		$("handIcapContent").style.display="inline";
		    		$("interDictContent").style.display="inline";
		    		$("marryContent").style.display="none";
		    		$("divorceContent").style.display="none";
		    		$("raiseChildContent").style.display="inline";
		    		//$("studMkSpaceContent").style.display="inline";
		    		//$("handIcapSpaceContent").style.display="inline";
		    		$("studMkContent").style.display="inline";	
		    		$("adoPtContent").style.display="inline";
		    		$("raiseChildSpace").style.display="none";		//配偶扶養Space
		    		$("adoPtSpace").style.display="none";		    //收養日期Space
		    		//$("adoPtDate").value="";
		    		checkStud();
		    		interChanged();	    		
		    	}
		    	
		    	if($("famEvtRel").value==6){
		    		$("handIcapContent").style.display="inline";
		    		$("interDictContent").style.display="inline";
		    		$("marryContent").style.display="none";
		    		$("divorceContent").style.display="none";
		    		$("raiseChildContent").style.display="none";
		    		//$("studMkSpaceContent").style.display="none";
		    		//$("handIcapSpaceContent").style.display="none";
		    		$("studMkContent").style.display="none";
		    		$("studContent").style.display="none";
		    		$("schoolCodeContent").style.display="none";
		    		$("adoPtContent").style.display="none";
		    		$("raiseChildSpace").style.display="none";		//配偶扶養Space
		    		$("adoPtSpace").style.display="inline";		    //收養日期Space
		    		interChanged();
		    	}
		    	
		    	if($("famEvtRel").value==7){
		    		$("handIcapContent").style.display="inline";
		    		$("interDictContent").style.display="inline";
		    		$("marryContent").style.display="none";
		    		$("divorceContent").style.display="none";
		    		$("raiseChildContent").style.display="none";
		    		//$("studMkSpaceContent").style.display="inline";
		    		//$("handIcapSpaceContent").style.display="none";
		    		$("studMkContent").style.display="inline";
		    		$("schoolCodeContent").style.display="none";
		    		$("adoPtContent").style.display="none";
		    		$("raiseChildSpace").style.display="inline";		//配偶扶養Space
		    		$("adoPtSpace").style.display="inline";		    //收養日期Space
		    		checkStud();
		    		interChanged();
		    	}	 
		     	checkAbanApply();
		     	
		     	//身分查核年月
		     	initIdnChkNote();
		     	
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
             
             <%-- 重置身分查核年月預設值 --%>
            function initIdnChkNote() {
                $("idnChkNoteContent").style.display="none";
                $("idnChkContent").style.display="none";
                //$("chkYmNContent").style.display="none";
                $("chkYmYContent").style.display="none";
                $("idnChkNote1").checked = false;
                $("idnChkNote2").checked = false;
                $("idnChkY").value = "";
                $("idnChkM").value = "";
                
                var oldIdnChkYm = "";
                if(Trim($("oldIdnChkYm").value)!=""){
                    oldIdnChkYm = changeDateType($("oldIdnChkYm").value+"01").substring(0 , 5);
                }else{
                    oldIdnChkYm = today.substring(0 , 5);
                }
                
                var checkData ='<c:out value="${checkStatus}" />';
                if(checkData>0){
                    $("idnChkNoteContent").style.display="inline";
                    $("chkYmYContent").style.display="inline";
                    if($("oldIdnChkNote").value=='1'){
                        if(parseNumber(oldIdnChkYm) > parseNumber(today.substring(0 , 5))){
                            $("idnChkNote1").checked = false;
                            $("idnChkNote1").disabled = true;
                        }else{
                            $("idnChkNote1").checked = true;
                        }
                    }else if($("oldIdnChkNote").value=='2'){
                        $("idnChkNote2").checked = true;
                        $("idnChkContent").style.display="inline";
                        $("idnChkY").value = $("oldIdnChkY").value;
                        $("idnChkM").value = $("oldIdnChkM").value;
                    }else {
                        $("idnChkContent").style.display="none";
                        $("idnChkNote1").checked = false;
                        $("idnChkNote2").checked = false;
                        $("idnChkY").value = "";
                        $("idnChkM").value = "";
                    }
                }                
            }
 // Added by JohnsonHuang 2020603 [Begin]
		     //外國人身分證號碼自動帶入
		     function autoForeignBenSex(){
		    	 var secondText = $("famIdnNo").value.substring(1,2);
				 if($("famIdnNo").value.length==10){
					if(document.getElementsByName("famNationTyp")[1].checked &&
						document.getElementsByName("famSex")[0].checked==false && document.getElementsByName("famSex")[1].checked==false){
						if(secondText=="A" || secondText=="a" || secondText=="C" || secondText=="c" || secondText=="8"){
							document.getElementsByName("famSex")[0].checked=true;
							document.getElementsByName("famSex")[1].checked=false;   	
						}else if(secondText=="B" || secondText=="b" || secondText=="D" || secondText=="d" || secondText=="9"){
							document.getElementsByName("famSex")[0].checked=false;
							document.getElementsByName("famSex")[1].checked=true;	
						}else{
							document.getElementsByName("famSex")[0].checked=false;
							document.getElementsByName("famSex")[1].checked=false; 
							alert('個人資料「不符合外來人口統一證號編碼原則，請輸入正確身分證」');
						}
					}else{
						if(document.getElementsByName("famNationTyp")[1].checked && document.getElementsByName("famSex")[0].checked==true){
							if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
								alert('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
							}
						}else if(document.getElementsByName("famNationTyp")[1].checked && document.getElementsByName("famSex")[1].checked==true){
							if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
								alert('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
								}
							}
						}
				 }
		      }
		  	  // Added by JohnsonHuang 2020603 [End]
		     Event.observe(window, 'load', initAll, false);
        </script>
    </head>
    
    <body scroll="no">
	
        <%@ include file="/includes/ba_header.jsp"%>
    	<%@ include file="/includes/ba_menu.jsp"%>
             
                <div id="main" class="mainBody">
                <html:form action="/depedantDataUpdateDetail" method="post" onsubmit="return validateDependantDataUpdateDetailForm(this);">  
                    <fieldset>
                        <legend>&nbsp;眷屬資料修改&nbsp;</legend>
                        <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
                 <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                   <tr>
	                   <td align="right">
	                        <html:hidden styleId="page" property="page" value="1" />
	                        <html:hidden styleId="method" property="method" value="" />
	                        <html:hidden styleId="bafamilyId" property="bafamilyId"  />
	                        <html:hidden styleId="idnoExist" property="idnoExist"/>
	                        <acl:checkButton buttonName="btnSave">
	                            <input name="btnSave" type="button" class="button" value="確　認" onclick="checkIdnoExist();$('page').value='1'; $('method').value='doConfirm'; if (document.DependantDataUpdateDetailForm.onsubmit() && checkRequireFields()){document.DependantDataUpdateDetailForm.submit();}else{return false;}" />&nbsp;
	                        </acl:checkButton>
	                        <acl:checkButton buttonName="btnBack">
	                            <input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackList'; document.DependantDataUpdateDetailForm.submit();" />
	                        </acl:checkButton>
	                    </td>
	                </tr>
                            <tr>
                                <td>
                                    <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
			                            <tr>
			                                <td width="25%">
			                                    <span class="issuetitle_L_down">受理編號：</span>
			                                    <c:out value="${DependantDataUpdateQueryCase[0].apNoStrDisplay}" />
			                                </td>
			                                <td width="25%">
			                                    <span class="issuetitle_L_down">給付別：</span>
			                                    <c:out value="${DependantDataUpdateQueryCase[0].payCode}" />
			                                </td>
			                                <td width="25%">
			                                    <span class="issuetitle_L_down">申請日期：</span>
			                                    <c:out value="${DependantDataUpdateQueryCase[0].appDateChineseString}" />
			                                </td>
			                                <td width="25%">
			                                    <span class="issuetitle_L_down">事故日期：</span>
			                                    <c:out value="${DependantDataUpdateQueryCase[0].evtJobDateStr}" />
			                                </td>
			                            </tr>
			                            <tr>
			                                <td colspan="2">
			                                    <span class="issuetitle_L_down">事故者姓名：</span>
			                                    <c:out value="${DependantDataUpdateQueryCase[0].evtName}" />
			                                </td>
			                                <td width="25%">
			                                    <span class="issuetitle_L_down">事故者出生日期：</span>
			                                    <c:out value="${DependantDataUpdateQueryCase[0].evtBrDateChineseString}" />
			                                </td>
			                                <td width="25%">
			                                    <span class="issuetitle_L_down">事故者身分證號：</span>
			                                    <c:out value="${DependantDataUpdateQueryCase[0].evtIdnNo}" />
			                                </td>
			                            </tr>
			                        </table>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" class="table1">
                                    <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                                        <tr>
			                	            <td colspan="3" class="issuetitle_L">
			                	                <span class="systemMsg">▲</span>&nbsp;眷屬資料
			                	            </td>
			                            </tr>
                                        <tr>
                                            <td id="iss" colspan="2">
			                                    &nbsp;&nbsp;<span class="needtxt">＊</span>
			                                    <span class="issuetitle_L_down">關　係：</span>
			                                    <html:select property="famEvtRel" styleId="famEvtRel" styleClass="formtxt" onchange="changeFamEvtRel();" tabindex="1">
			                                        <html:option value="">請選擇</html:option>
			                                        <html:options collection="<%=ConstantKey.PAYEE_RELATIION_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
			                                    </html:select>
			                                </td>
                							<td id="iss" colspan="1">
                    							<html:checkbox styleId="compelMk" property="compelMk" value="Y" onclick="toggleCompelDataDisplay();"/>
                    							<span class="issuetitle_L_down">強迫不合格</span>
                    							<span id="compel">
                        							<input name="btnCompelData" type="button" class="button_120" value="不合格年月維護" onclick="$('page').value='1'; $('method').value='doPrepareMaintainCompelData'; document.DependantDataUpdateDetailForm.submit();" style="display: none"/>
                    							</span>
                							</td>			                                
                                        </tr>
                                        <tr>
                                            <td id="iss" width="34%">
                                                &nbsp;&nbsp;<span class="needtxt">＊</span>
                                                <span class="issuetitle_L_down">姓　名：</span>
                                                <html:text property="famName" styleId="famName" styleClass="textinput" size="50" maxlength="50" onblur="this.value=asc(this.value);" tabindex="2"/>
                                            	
                                            </td>
                                             <td id="iss" width="33%">
                                                &nbsp;&nbsp;<span class="needtxt">＊</span>
                                                <span class="issuetitle_L_down">申請日期：</span>
                                                <html:text property="famAppDate" styleId="famAppDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);" tabindex="3"/>
                                            </td>  
                                            <td id="iss" width="33%">
                                                &nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">得請領起月：</span>
                                                <html:text property="ableApsYm" styleId="ableApsYm" styleClass="textinput" size="10" maxlength="5" onblur="this.value=asc(this.value);" tabindex="3"/>
                                            </td>   
                                        </tr>
                                        <tr>
                                            <td id="iss" width="34%">
                                                &nbsp;&nbsp;<span class="needtxt">＊</span>
                                                <span class="issuetitle_L_down">身分證號：</span>
                                                <html:text property="famIdnNo" styleId="famIdnNo" styleClass="textinput" size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();checkIdnoExist();autoForeignBenSex();" tabindex="4"/>
                                            </td>
                                            <td id="iss" width="33%">
                                                &nbsp;&nbsp;<span class="needtxt">＊</span>
                                                <span class="issuetitle_L_down">出生日期：</span>
                                                <html:text property="famBrDate" styleId="famBrDate" styleClass="textinput" size="8" maxlength="8" onblur="this.value=asc(this.value);checkIdnoExist();" tabindex="5"/>
                                            </td>
                                            <td id="iss" width="33%">
                                                &nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">死亡日期：</span>
                                                <html:text property="famDieDate" styleId="famDieDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);" tabindex="6"/>
                                            </td>         				
                                        </tr>
                                        <tr>
                                        	<td id="iss">
			                                    &nbsp;&nbsp;<span class="needtxt">＊</span>
		                                        <span class="issuetitle_L_down">國籍別：</span>
		                                        <span class="formtxt">
		                                            <html:radio property="famNationTyp" value="1" onclick="$('famNationTyp').value='1'; changeFamNationTpe();" tabindex="7"/>本國
		                                            <html:radio property="famNationTyp" value="2" onclick="$('famNationTyp').value='2'; changeFamNationTpe();" tabindex="7"/>外籍
		                                    	</span>
			                                </td>
			                                <td id="iss">
			                                    <div id="sexContent">
			                                    	&nbsp;&nbsp;<span class="needtxt">＊</span>
			                                        <span class="issuetitle_L_down">性別：</span>
			                                        <span class="formtxt">
			                                            <html:radio property="famSex" value="1" onclick="$('famSex').value='1';" onkeyup="autotab($('famSex'), $('famNationCodeOption'))" tabindex="8"/>男
			                                        </span>
			                                        <span class="formtxt">
			                                            <html:radio property="famSex" value="2" onclick="$('famSex').value='2';" onkeyup="autotab($('famSex'), $('famNationCodeOption'))" tabindex="8"/>女
			                                        </span>
			                                    </div>
			                                    &nbsp;                
			                                </td>
			                                <td id="iss">
			                                  	<div id="nationalityContent">
			                                  		<span class="needtxt">＊</span>
			                                        <span class="issuetitle_L_down">國籍：</span>
			                                        <html:text property="famNationCode" styleId="countryId" styleClass="textinput" size="2" maxlength="3" onchange="changeNationCode();" readonly = "true"/>
							                        <label>
							                            <html:select property="famNationCodeOption" onchange="$('famNationCode').value=$('famNationCodeOption').value" tabindex="9">
			                                                <html:option value="">請選擇</html:option>
			                                                <html:options collection="<%=ConstantKey.COUNTRYID_OPTION_LIST%>" property="countryId" labelProperty="cname" />
			                                            </html:select>
							                        </label> 
							                    </div>
							                    &nbsp;
							                 </td>
                                        </tr>
                                        <tr>
                                        	<td id="iss">
                                            	<div id="marryContent">
                                            		&nbsp;&nbsp;<span class="needtxt">＊</span>
                                                    <span class="issuetitle_R_down">結婚日期：</span>
                                                    <html:text property="marryDate" styleId="marryDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);" tabindex="10"/>
                                                </div>
                                            </td>
                                            <td id="iss" colspan="2">
                                                <div id="divorceContent">
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">離婚日期：</span>
                                                    <html:text property="divorceDate" styleId="divorceDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);" tabindex="11"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                        	<td id="iss" colspan="3">
                                                <%--
                                        		<div id="chkYmNContent">
                                        			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">身分查核年月(&nbsp;&nbsp;年&nbsp;&nbsp;月)：</span>
                                        		</div>
                                                --%>
                                        		<div id="chkYmYContent">
                                        			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">身分查核年月(<c:out value="${idnChkY}" />年<c:out value="${idnChkM}" />月)：</span>
                                        		</div>
                                        	    <div id="idnChkNoteContent">
			                                        <span class="formtxt">
			                                            <html:radio styleId="idnChkNote1" property="idnChkNote" value="1" onclick="$('idnChkNote').value='1'; changeIdnChkNote();" tabindex="12"/>自動遞延13個月
			                                            <html:radio styleId="idnChkNote2" property="idnChkNote" value="2" onclick="$('idnChkNote').value='2'; changeIdnChkNote();" tabindex="12"/>手動調整
			                                         </span>
			                                    </div>  
			                                    <div id="idnChkContent">
    	                                            <html:text property="idnChkY" styleId="idnChkY" styleClass="textinput" size="3" maxlength="3" onblur="this.value=asc(this.value);"/>年
    	                                            <html:text property="idnChkM" styleId="idnChkM" styleClass="textinput" size="2" maxlength="2" onblur="this.value=asc(this.value);"/>月
                                                    
                                                    <html:hidden styleId="oldIdnChkYm" property="oldIdnChkYm" />
                                                    <html:hidden styleId="oldIdnChkNote" property="oldIdnChkNote" />
                                                    <input type="hidden" name="oldIdnChkY" value="<c:out value="${DependantDataUpdateDetailForm.idnChkY}" />" >
                                                    <input type="hidden" name="oldIdnChkM" value="<c:out value="${DependantDataUpdateDetailForm.idnChkM}" />" >
                                                </div>
                                        	</td>
                                        </tr>
                                        <tr>
                                        	<td id="iss">
                                                <div id="studMkContent">
                                                    <html:checkbox styleId="studMk" property="studMk" value="Y" onclick="checkStud();" tabindex="13"/>
                                                    <span class="issuetitle_L_down">在學&nbsp;&nbsp;</span>
                                                </div>
                                                <div id="studContent">
                                                	<span id="atSchool">
								                        <input name="btnStudterm" type="button" class="button_120" value="在學起迄年月維護" onclick="$('page').value='1'; $('method').value='doPrepareMaintainStudterm'; document.DependantDataUpdateDetailForm.submit();" />
								                    </span>
                                                </div>
                                            </td>
                                        	<td id="iss" colspan="2">
	                                            <div id="raiseChildContent">
	                                            	<html:checkbox styleId="raiseChildMk" property="raiseChildMk" value="Y" tabindex="14"/>
	                                            	<span class="issuetitle_L_down">配偶扶養</span>
	                                            </div>
	                                            <div id="raiseChildSpace">
	                                             	&nbsp;&nbsp;
	                                            </div>
	                                        </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="3">
                                               <div id="schoolCodeContent">
			                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">學校代碼：</span>
			                                    <html:text property="schoolCode" styleId="schoolCode" styleClass="textinput" size="7" maxlength="4" onblur="this.value=asc(this.value);changeSchoolCode();" />
			                                    <html:select property="schoolCodeOption" styleId="schoolCodeOption" styleClass="formtxt" tabindex="14" onchange="changeSchoolCodeOption();">
			                                        <html:option value="">請選擇</html:option>
			                                        <html:options collection="<%=ConstantKey.SCHOOLCODE_OPTION_LIST%>" property="codeNo" labelProperty="codeString" />
			                                    </html:select>
			                                    <input name="btnQuerySchool" type="button" class="button_120" value="學校名稱查詢" onclick="doQuerySchool();">
			                                   </div>
			                                </td>
			                            </tr>    
                                        <tr>
                                        	<td id="iss">
                                        		<html:checkbox styleId="monIncomeMk" property="monIncomeMk" value="Y" onclick="monIncomeMkChk();" tabindex="15"/>
                                                <span class="issuetitle_L_down">每月工作收入：</span>
                                                <html:text property="monIncome" styleId="monIncome" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);"/>元</span>
                                        	</td>
                                        	<td id="iss" colspan="2">
	                                            <div id="interDictContent">
                                                    <html:checkbox styleId="interDictMk" property="interDictMk" value="Y" onclick="interChanged();" tabindex="12"/>
                                                    <span class="issuetitle_L_down">受禁治產(監護)宣告</span>
                                                </div>
                                                <div id="interDictContentY">
                                                	<span class="issuetitle_L_down">-&nbsp;宣告／撤銷日期：</span>
                                                </div>
                                                <div id="interDictContentYM">
                                                	<html:text property="interDictDate" styleId="interDictDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);"/>／
                                                	<html:text property="repealInterDictDate" styleId="repealInterDictDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);"/>
                                                </div>
                                                &nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="1">
				                                <div id="handIcapContent">
	                                            	<html:checkbox styleId="handIcapMk" property="handIcapMk" value="Y"/>
	                                            	<span class="issuetitle_L_down">領有重度以上身心障礙手冊或證明</span>
	                                            </div>
                                            </td>
                                            <td id="iss" colspan="2" >
                                                <div id="adoPtContent">
	                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">收養日期：</span>
	                                                <html:text property="adoPtDate" styleId="adoPtDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);"/>
    	                                        </div> 
    	                                        <div id="adoPtSpace">
    	                                            &nbsp;
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" >
                                                <html:checkbox styleId="abanApplyMk" property="abanApplyMk" value="Y" onclick="checkAbanApply();"/>
                                                <span class="issuetitle_L_down">放棄請領</span>
                                                <div id="abanApplyContent">
                                                	<span class="issuetitle_L_down">-&nbsp;起始年月：</span>
                                                	<html:text property="abanApsYm" styleId="abanApsYm" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);"/>
                                                </div>
                                            </td> 
                                            <td id="iss" colspan="2">
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_R_down">失蹤起迄期間：</span>
                                                <html:text property="benMissingSdate" styleId="benMissingSdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>&nbsp;~
                                                <html:text property="benMissingEdate" styleId="benMissingEdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                                            </td>
                                        </tr>
                                        <tr>		
                                            <td id="iss">
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_R_down">親屬關係變動日期：</span>
                                                <html:text property="relatChgDate" styleId="relatChgDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);"/>
                                            </td>
                                            <td id="iss" colspan="2">
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_R_down">監管起迄期間：</span>
                                                <html:text property="prisonSdate" styleId="prisonSdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>&nbsp;~
                                                <html:text property="prisonEdate" styleId="prisonEdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="3">
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">結案原因：</span>
                                                <html:select property="closeCause" styleId="closeCause" styleClass="formtxt"> 
			                                        <html:option value="">請選擇</html:option>
			                                         <logic:iterate id="optionList" name="<%=ConstantKey.CLOSE_OPTION_LIST%>">                                                                        
			                                            <html:option value="${optionList.paramCode}"><c:out value="${optionList.paramCode}" />-<c:out value="${optionList.paramName}" /></html:option>
			                                         </logic:iterate>
			                                         <%--
			                                        <html:options collection="<%=ConstantKey.CLOSE_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
			                                        --%>
			                                    </html:select>
                                             </td>			  
                                        </tr>			
                                        <tr>
                                            <td id="iss" colspan="3">
                                            	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
	                                                <tr>
	                                                    <td valign="top" width="15%">
	                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">眷屬符合註記：</span>
	                                                    </td>
	                                                    <td valign="top" width="85%">
	                                                        <logic:iterate id="masterChkFile" name="<%=ConstantKey.DEPENDANT_QUERY_MATCH_CHKFILE_DATA_CASE %>">                                                    
	                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="px13">
	                                                                <tr>
	                                                                    <td valign="top" width="90%" align="left">
	                                                                        <logic:iterate id="chkFileData" name="masterChkFile" property="otherChkDataList">                                                    
	                                                                            <c:out value="${chkFileData.issuPayYm}" />：

	                                                                            <logic:iterate id="chkFile" indexId="i" name="chkFileData" property="chkFileDataList">
	                                                                                <span title="<c:out value="${chkFile.chkResult}" />">
	                                                                                    <c:out value="${chkFile.chkCodePost}"/>
	                                                                                </span>
	                                                                                <c:if test="${i+1 ne chkFileData.chkFileDataSize}">
	                                                                                    <c:out value=",　" />
	                                                                                </c:if>
	                                                                            </logic:iterate><br>
	                                                                        </logic:iterate>
	                                                                    </td>
	                                                                </tr>
	                                                            </table>
	                                                        </logic:iterate>
	                                                        <logic:empty name="<%=ConstantKey.DEPENDANT_QUERY_MATCH_CHKFILE_DATA_CASE %>">
	                                                            &nbsp;
	                                                        </logic:empty>
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
		                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="issuetitle_L_down">眷屬編審註記：</span>
		                                                </td>
		                                                <td valign="top" width="85%">
		                                                    <logic:iterate id="masterChkFile" name="<%=ConstantKey.DEPENDANT_QUERY_BEN_CHKFILE_DATA_CASE %>">                                                    
		                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="px13">
		                                                            <tr>
		                                                                <td valign="top" width="90%" align="left">
		                                                                    <logic:iterate id="chkFileData" name="masterChkFile" property="otherChkDataList">                                                    
		                                                                        <c:out value="${chkFileData.issuPayYm}" />：

		                                                                        <logic:iterate id="chkFile" indexId="i" name="chkFileData" property="chkFileDataList">
		                                                                            <span title="<c:out value="${chkFile.chkResult}" />">
		                                                                                <c:out value="${chkFile.chkCodePost}"/>
		                                                                            </span>
		                                                                            <c:if test="${i+1 ne chkFileData.chkFileDataSize}">
		                                                                                <c:out value=",　" />
		                                                                            </c:if>
		                                                                        </logic:iterate><br>
		                                                                    </logic:iterate>
		                                                                </td>
		                                                            </tr>
		                                                        </table>
		                                                    </logic:iterate>
		                                                    <logic:empty name="<%=ConstantKey.DEPENDANT_QUERY_BEN_CHKFILE_DATA_CASE %>">
		                                                        &nbsp;
		                                                    </logic:empty>
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
                                <td><hr size="1" noshade>
			                        <span class="titleinput">※注意事項：</span><br>
			            　                                                1.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
			            　                                                2.&nbsp;<span class="needtxt">＊</span>為必填欄位。

			                    </td>
                            </tr>
                        </table>
                    </fieldset>
              </html:form>
        </div>
        <%@ include file="/includes/ba_footer.jsp"%>
    
    </body>
</html:html>