<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="tw.gov.bli.ba.update.cases.ChkFileCase" %>
<%@ page import="tw.gov.bli.ba.update.cases.ChkFileCollectionCase" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D031C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>   
    <html:javascript formName="ApplicationDataUpdateForm" page="1" />        
    <script language="javascript" type="text/javascript">    
    <!--     
    //畫面初始化
    function initAll(){
        <%--var chkStr = '';
        <%for(int i=0; i<((List<ChkFileCollectionCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).size(); i++){%>
            <%if(i==1){%>            
                chkStr+='';
            <%}%>
            <%if(i!=((List<ChkFileCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).size()-1){%>
                chkStr+='<span title="'+'<%=(((List<ChkFileCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).get(i)).getChkFileDescStr()%>'+'">'
                         +'<%=(((List<ChkFileCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).get(i)).getPayYmStr()%>'+'　'
                         +'<%=(((List<ChkFileCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).get(i)).getChkFileNameStr()%>'+'</span>,　';
            <%}else{%>
                chkStr+='<span title="'+'<%=(((List<ChkFileCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).get(i)).getChkFileDescStr()%>'+'">'
                         +'<%=(((List<ChkFileCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).get(i)).getPayYmStr()%>'+'　'
                         +'<%=(((List<ChkFileCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).get(i)).getChkFileNameStr()%>'+'</span>';                
            
            <%}if("LS".equals((((List<ChkFileCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).get(i)).getChkFileCode())){%>
                  $("loanMkContent").style.display="inline";
            <%}if("UF".equals((((List<ChkFileCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).get(i)).getChkFileCode())){%>
                  $("owesContent").style.display="inline";
            <%}%>                                                                                
        <%}%>         
         $("chkContent").innerHTML=chkStr;--%>
         
         if($("isShowInterValMonth").value == 'Y'){
             if('<c:out value="${ApplicationDataUpdateForm.interValMonth}" />' != "<%=ConstantKey.BAAPPBASE_INTERVALMONTH_BY_SIXMONTH%>"){
                $("interValMonth1").checked=true;
             }else{
                $("interValMonth2").checked=true;
             }
         }
         changNationTpe();  
         checkNotifyForm();
         changEvtDieDate();   
         checkIdnoExist();      
         if($("evtDieDate").value != "" && $("evtDieDate").value != null){
             changeCloseCause();
         }
         getMaxAplPayDate();
    }
    
    //變更 國籍別 時畫面異動
    function changNationTpe(){        
        if($("evtNationTpe1").checked==true){
            $("evtNationCode").disabled = true; 
            $("sexContent").style.display="none"
            $("nationContent").style.display="none"
            $("evtSex1").checked=false;
            $("evtSex2").checked=false;
            //$("evtNationCode").value = "";     
            //$("evtNationCodeOption").value = "";            
        }
        if($("evtNationTpe2").checked==true){
            $("evtNationCode").disabled = false; 
            $("sexContent").style.display="inline"
            $("nationContent").style.display="inline"
        }
        autoForeignEvtSex();
    }

    //變更 死亡日期 時畫面異動
    function changEvtDieDate(){        
        if($("evtDieDate").value=="" || $("evtDieDate").value==null){
             //20111003 by Kiyomi 修改：不管「死亡日期」有沒有值，「結案日期」及「結案原因」皆需顯示。        
             //$("closeDateContent").style.display="none";
             //$("closeCauseContent").style.display="none";
             //20111003 by Kiyomi 修改：不管「死亡日期」有沒有值，「結案日期」及「結案原因」皆可輸入。
             //$("closeDate").disabled = true; 
             //$("closeCause").disabled = true;
             $("closeDate").disabled = false; 
             $("closeCause").disabled = false;             
             //$("closeDate").value = ""; 
             //$("closeCause").value = "";
             $("onceAmtContent").style.display="none" 
         }else{
             //20111003 by Kiyomi 修改：不管「死亡日期」有沒有值，「結案日期」及「結案原因」皆需顯示。         
             //$("closeDateContent").style.display="inline";
             //$("closeCauseContent").style.display="inline";
             $("closeDate").disabled = false; 
             $("closeCause").disabled = false;             
             changeCloseCause();
         }
    }
    
    //變更 結案原因 時畫面異動
    function changeCloseCause(){
        if($("closeCause").value=="1"){
            $("onceAmtContent").style.display="inline"                      
        }else{
            $("onceAmtContent").style.display="none"              
        }
    }
      
    // Ajax for 取得 檢查核定格式
    function checkNotifyForm() {   
        updateCommonAjaxDo.checkNotifyForm($("apNo").value, checkNotifyFormResult);
    }
    
    function checkNotifyFormResult(checkResult){   
        $("checkResult").value = checkResult;                
    }

    // Ajax for 取得 最新核付日期
    function getMaxAplPayDate() {   
        updateCommonAjaxDo.getMaxAplPayDate($("apNo").value, setMaxAplPayDate);
    }
    
    function setMaxAplPayDate(maxAplPayDate){           
        $("maxAplPayDate").value = maxAplPayDate;
    }
     
    // Ajax for 取得 出生日期錯誤參數 確認是否有此筆資料P120436303 0480229  $("evtIdnNo").value,$("evtBrDate").value
    function checkIdnoExist() {   
        if(isNaN($("evtBrDate").value) == false){
        updateCommonAjaxDo.checkIdnoExist($("evtIdnNo").value,$("evtBrDate").value,checkIdnoExistResult);
        }
    	}
    	function checkIdnoExistResult(idnoExist) {  
    		$("idnoExist").value = idnoExist;
    	}
    	
    //檢核事故者出生日期 20121220 邏輯修改
    function isValidEvtDateTrue() {   
        var evtBrDate = $("evtBrDate").value;

        if(isValidDate($("evtBrDate").value) == false){
        
        if($("idnoExist").value == null || $("idnoExist").value == "" || $("idnoExist").value == "null"){
        alert("輸入之「事故者出生日期」錯誤，請重新輸入");
        return false;
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
		
		var secondText = $("evtIdnNo").value.substring(1,2);
if($("evtIdnNo").value.length==10){
		if($("evtNationTpe2").checked==true && $("evtSex1").checked==true){
     			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
     				alert('身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
					$("evtSex1").focus();
        		}
     		}else if($("evtNationTpe2").checked==true && $("evtSex2").checked==true){
     			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
     				alert('身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
					$("evtSex1").focus();
        		}
     		}
}
        if($("evtNameStatus").value == "Y"){
            if($("evtName").value==""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.update.applicationDataUpdate.evtName")%>' />\r\n"
                $("evtName").focus();
            }
        }    
        
        if (document.getElementsByName("evtNationTpe")[0].checked==true){
            if(!isValidIdNoForTest($("evtIdnNo").value) || !chkPID_CHAR($("evtIdnNo").value))
            	msg +='「事故者身分證號」輸入有誤，請輸入長度為10 碼且符合格式的資料。\r\n';
           
        }    
        
        if (document.getElementsByName("evtNationTpe")[1].checked==true){
            if(!isEngNum($("evtIdnNo").value))
	           msg +='「事故者身分證號」格式錯誤。\r\n';
	        if(Trim($("evtNationCode").value) == "000")
	           msg +='「國籍別」為外籍不得輸入中華民國之國籍代碼。\r\n';
        }              
        
        if ($("isShowInterValMonth").value=="Y"){
            if($("interValMonth").value=="")
               msg +='請選擇「發放方式」。\r\n';
        }
        
        if($("evtNationTpe2").checked==true){
            if($("evtSex1").checked==false && $("evtSex2").checked==false){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.update.applicationDataUpdate.evtSex")%>' />\r\n"                
                $("evtSex1").focus();
            }   
            if($("evtNationCode").value==""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.update.applicationDataUpdate.evtNationCode")%>' />\r\n"
                $("evtNationCode").focus();
            }                
        }
        
        if(parseNumber($("appDate").value)<parseNumber("0980101")){
            msg += "「申請日期」不可小於 0980101。\r\n";
            $("appDate").focus();
        }
        
        //檢查核定格式
        //[
        if(Trim($("notifyForm").value)==""){
            msg += "「核定格式」為必填欄位。\r\n"
            $("notifyForm").focus();
        }else{
            checkResult = ($("checkResult").value).split(",");
            var chkNotifyForm = false;
            for(i=0; i<checkResult.length; i++){
                if(checkResult[i]==$("notifyForm").value){
                    chkNotifyForm = true;
                    break;
                }
            }
            if(!chkNotifyForm){
                msg += "「核定格式」不正確。\r\n"
                $("notifyForm").focus();
            }
        }
        //]
        
        if((Trim($("evtDieDate").value))!="" && parseNumber($("evtDieDate").value) < parseNumber($("evtJobDate").value)){
            msg += "「死亡日期」需大於等於「離職日期」。\r\n"
            $("evtDieDate").focus();
        }
        
        //if($("closeDateContent").style.display=="inline"){
        //    if((Trim($("closeDate").value))!="" && parseNumber($("closeDate").value) < parseNumber($("maxAplPayDate").value)){
        //        msg += "「結案日期」需大於等於「最新1筆的核付日期」。\r\n"
        //        $("closeDate").focus();
        //    }
        //}
        //檢核事故者出生日期  是否為數字 及 年月格式
        //先檢核錯誤檔 是否有此身分證及生日
        if($("evtBrDate").value.length < 7){
                    msg += '輸入之「事故者出生日期」錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
        }
        if(isNaN($("evtBrDate").value)){
                    msg += '輸入之「事故者出生日期」錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
        }
        if($("evtBrDate").value.length == 7){
           var chkMonth = $("evtBrDate").value.substring(3,5);
           var chkDay   = $("evtBrDate").value.substring(5,7);
           if(chkMonth > 12 || chkDay > 32){
                    msg += '輸入之「事故者出生日期」錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
           }
        }else if($("evtBrDate").value.length == 8){
           var chkfrist = $("evtBrDate").value.substring(0,1);
           var chkMonth = $("evtBrDate").value.substring(4,6);
           var chkDay   = $("evtBrDate").value.substring(6,8);
           if(chkfrist != "-"){
                    msg += '輸入之「事故者出生日期」錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
           }
           if(chkMonth > 12 || chkDay > 32){
                    msg += '輸入之「事故者出生日期」錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
           }
        }
       
        if(msg != ""){
            alert(msg);
            return false;
        }else{
            //如果國籍別為本國, 清除性別、國籍資料
            if($("evtNationTpe1").checked==true){
                $("evtNationCode").value = "";     
                $("evtNationCodeOption").value = ""; 
            }
            //如果死亡日期為空, 清除結案日期、結案原因
            //if((Trim($("evtDieDate").value))==""){
            //    $("closeDate").value = ""; 
            //    $("closeCause").value = "";
            //}
            if(isValidEvtDateTrue()){
            return true;
            }else{
            return false;
            }
        }
    }
    <%-- ] ... end --%>

    function controlButton() { 
    	// 獲得該button對象 
    	var btnUpdate = document.getElementById('btnModify'); 
    	var btnPrint = document.getElementById('btnPrint');
    	var btnBack = document.getElementById('btnBack');
    	// 創建計時器 返回計時器ID 
    	var intervalId = setInterval(function () { 
    		btnUpdate.disabled = true;
    		btnPrint.disabled = true;
    		btnBack.disabled = true;
    		}, 1000); 
    	}   
 	// Added by JohnsonHuang 20200115 [Begin]
  	//外國人身分證號碼自動帶入
    function autoForeignEvtSex(){
    	var secondText = $("evtIdnNo").value.substring(1,2);
		if($("evtIdnNo").value.length==10){
    	if($("evtNationTpe2").checked==true && $("evtSex1").checked==false && $("evtSex2").checked==false){
    		if(secondText=="A" || secondText=="a" || secondText=="C" || secondText=="c" || secondText=="8"){
    			$("evtSex1").checked=true; 
    			$("evtSex2").checked=false;	
    		}else if(secondText=="B" || secondText=="b" || secondText=="D" || secondText=="d" || secondText=="9"){
    			$("evtSex1").checked=false; 
    			$("evtSex2").checked=true;   	
    		}else{
    			$("evtSex1").checked=false; 
    			$("evtSex2").checked=false;
    			alert('「不符合外來人口統一證號編碼原則，請輸入正確身分證」');
    		}
    	}else{
    		if($("evtNationTpe2").checked==true && $("evtSex1").checked==true){
     			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
     				alert('身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
        		}
     		}else if($("evtNationTpe2").checked==true && $("evtSex2").checked==true){
     			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
     				alert('身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
        		}
     		}
    	}
		}
    }
 	// Added by EthanChen 20200115 [End]
    
	
	
	
    Event.observe(window, 'load', initAll, false);
    
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/applicationDataUpdate" method="post" onsubmit="return validateApplicationDataUpdateForm(this);">
        <bean:define id="app" name="<%=ConstantKey.BAAPPBASE_DATA_UPDATE_CASE%>"/>                        
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="apNo" property="apNo" />
        <html:hidden styleId="baappbaseId" property="baappbaseId" />
        <html:hidden styleId="accRel" property="accRel"/>
        <html:hidden styleId="idnoExist" property="idnoExist"/>
        <input type="hidden" id="checkResult" name="checkResult" value="">
        <input type="hidden" id="maxAplPayDate" name="maxAplPayDate" value="">
        <input type="hidden" id="evtNameStatus" name="evtNameStatus" value="<c:out value="${app.evtNameStatus}" />">
        <input type="hidden" id="isShowInterValMonth" name="isShowInterValMonth" value="<c:out value="${app.isShowInterValMonth}" />">
         
        <fieldset>
            <legend>&nbsp;老年年金案件資料更正&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right">
                        <acl:checkButton buttonName="btnModify">
                            <input type="button" name="btnModify" class="button" value="確　認" onclick="checkIdnoExist();$('page').value='1'; $('method').value='doUpdate'; getMaxAplPayDate(); if(document.ApplicationDataUpdateForm.onsubmit() && checkFields()){document.ApplicationDataUpdateForm.submit();controlButton();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnCancel">
                            <c:if test="${app.delBtnStatus eq 'Y'}">
                                <input type="reset" name="btnCancel" class="button" value="註　銷"  onclick="$('page').value='1'; $('method').value='doCancel'; if(confirm('請確認是否註銷本案件？')){document.ApplicationDataUpdateForm.submit();}else{return false;}"/>&nbsp;                            
                            </c:if>
                            <c:if test="${app.delBtnStatus eq 'N'}">
                                <input type="reset" name="btnCancel" class="button" value="註　銷" disabled="true"/>&nbsp;                            
                            </c:if>
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnPrint">
                            <input name="btnPrint" type="button" class="button_120" value="檢視受理/審核清單" onclick="location.href='<c:url value="/printOldAgeReviewRpt01.do?method=doReport&apNo1Begin=${app.apNo1Str}&apNo2Begin=${app.apNo2Str}&apNo3Begin=${app.apNo3Str}&apNo4Begin=${app.apNo4Str}&apNo1End=${app.apNo1Str}&apNo2End=${app.apNo2Str}&apNo3End=${app.apNo3Str}&apNo4End=${app.apNo4Str}&action=single" />'">&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.ApplicationDataUpdateForm.submit();"/>
                        </acl:checkButton>  
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="24%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${app.apNoStrDisplay}" />
                                </td>
                                <td width="21%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:if test='${(app.pagePayKind eq "L")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                    </c:if>
                                    <c:if test='${(app.pagePayKind eq "K")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                    </c:if>
                                    <c:if test='${(app.pagePayKind eq "S")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                    </c:if>
                                </td>
                                <td width="21%">
                                    <span class="issuetitle_L_down">退保日期：</span>
                                    <c:out value="${app.evtDateStr}" />
                                </td>
                                <%-- <td width="18%">
                                    <span class="issuetitle_L_down">資料別：</span>
                                    <c:if test='${(app.caseTyp eq "1")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_1%>" />
                                    </c:if>
                                    <c:if test='${(app.caseTyp eq "2")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_2%>" />
                                    </c:if>
                                    <c:if test='${(app.caseTyp eq "3")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_3%>" />
                                    </c:if>
                                    <c:if test='${(app.caseTyp eq "4")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_4%>" />
                                    </c:if>
                                </td> --%>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">處理狀態：</span>
                                    <c:if test='${(app.procStat eq "00")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_00%>" />
                                    </c:if>
                                    <c:if test='${(app.procStat eq "01")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_01%>" />
                                    </c:if>
                                    <c:if test='${(app.procStat eq "10")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_10%>" />
                                    </c:if>
                                    <c:if test='${(app.procStat eq "11")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_11%>" />
                                    </c:if>
                                    <c:if test='${(app.procStat eq "12")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_12%>" />
                                    </c:if>
                                    <c:if test='${(app.procStat eq "13")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_13%>" />
                                    </c:if>
                                    <c:if test='${(app.procStat eq "20")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_20%>" />
                                    </c:if>
                                    <c:if test='${(app.procStat eq "30")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_30%>" />
                                    </c:if>
                                    <c:if test='${(app.procStat eq "40")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_40%>" />
                                    </c:if>
                                    <c:if test='${(app.procStat eq "50")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_50%>" />
                                    </c:if>
                                    <c:if test='${(app.procStat eq "90")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_90%>" />
                                    </c:if>
                                    <c:if test='${(app.procStat eq "99")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_99%>" />
                                    </c:if>                                                                      
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                            <tr>
                                <td colspan="4" class="issuetitle_L">
                                    <span class="systemMsg">▲</span>&nbsp;案件資料
                                </td>
                            </tr>
                            <tr>
                                <td width="33%" id="iss" colspan="2">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">國籍別：</span>
                                    <span class="formtxt">
                                        <html:radio styleId="evtNationTpe1" property="evtNationTpe" value="1" onclick="changNationTpe();" />本國
                                        <html:radio styleId="evtNationTpe2" property="evtNationTpe" value="2" onclick="changNationTpe();" />外籍
                                    </span>
                                </td>
                                <td width="33%" id="iss">      
                                    <span class="issuetitle_L_down">
                                        <div id="sexContent" style="display: none;">
                                                       　性別：
                                            <span class="formtxt">
                                                <html:radio styleId="evtSex1" property="evtSex" value="1" />男                                        
                                                <html:radio styleId="evtSex2" property="evtSex" value="2" />女                                        
                                            </span>
                                        </div>
                                    </span>&nbsp;
                                </td>
                                <td width="34%" id="iss">                                    
                                    <span class="issuetitle_L_down">
                                        <div id="nationContent" style="display: none;">
                                                       　國　籍：
                                            <html:text property="evtNationCode" styleId="evtNationCode" styleClass="textinput" size="2" maxlength="3" />
                                            <label>
                                                <html:select property="evtNationCodeOption" onchange="$('evtNationCode').value=$('evtNationCodeOption').value">
                                                    <html:option value="">請選擇</html:option>
                                                    <html:options collection="<%=ConstantKey.COUNTRYID_OPTION_LIST%>" property="countryId" labelProperty="cname" />
                                                </html:select>    
                                            </label>
                                        </div>&nbsp;
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="4">
                                    <c:if test="${app.evtNameStatus eq 'Y'}">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">事故者姓名：</span>
                                        <html:text property="evtName" styleId="evtName" styleClass="textinput" size="50" maxlength="50"/>                            
                                    </c:if>
                                    <c:if test="${app.evtNameStatus eq 'N'}">
                                        <span class="issuetitle_L_down">　事故者姓名：</span>
                                        <html:text property="evtName" styleId="evtName" styleClass="textinput" size="50" maxlength="50" disabled="true"/>                            
                                    </c:if>
                                    <c:if test="${(app.dupeIdnNoMk eq '1')or((app.dupeIdnNoMk eq '2'))}">
                                        <input name="button" type="button" class="button_120" value="選擇事故者姓名" onclick="$('page').value='1'; $('method').value='chooseEvtName'; document.ApplicationDataUpdateForm.submit();">&nbsp;
                                    </c:if>
                                    <c:if test="${app.isShowCvldtlName eq 'Y'}">
                                        <span class="formtxt">(&nbsp;戶籍姓名<input id="cvldtlName" name="cvldtlName" type="text" class="disabled" value="<c:out value="${app.cvldtlName}" />" size="50" disabled="true">)</span>
                                    </c:if>            
                                </td>                                
                            </tr>
                            <tr>
                                <td id="iss" colspan="2">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <html:text property="evtIdnNo" styleId="evtIdnNo" styleClass="textinput" size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();checkIdnoExist();autoForeignEvtSex();"/>                            
                                </td>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <html:text property="evtBrDate" styleId="evtBrDate" styleClass="textinput" size="10" maxlength="8" onblur="this.value=asc(this.value);checkIdnoExist();"/>                                    
                                </td>
                                <td id="iss" valign="top">
                                    <c:if test="${app.isShowInterValMonth eq 'Y'}">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">發放方式：</span>
                                        <html:select property="interValMonth" styleId="interValMonth">
                                            <html:option styleId="interValMonth1" value="<%=ConstantKey.BAAPPBASE_INTERVALMONTH_BY_MONTH %>">按月發放</html:option>
                                            <html:option styleId="interValMonth2" value="<%=ConstantKey.BAAPPBASE_INTERVALMONTH_BY_SIXMONTH %>">半年發放</html:option>
                                        </html:select>
                                    </c:if>
                                    <c:if test="${app.isShowInterValMonth ne 'Y'}">
                                        &nbsp;
                                    </c:if>
                                </td>           
                            </tr>
                            <tr>
                                <td id="iss" colspan="2">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">離職日期：</span>
                                    <html:text property="evtJobDate" styleId="evtJobDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>                                    
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">　死亡日期：</span>
                                    <html:text property="evtDieDate" styleId="evtDieDate" styleClass="textinput" size="10" maxlength="7" onchange="changEvtDieDate();" onblur="this.value=asc(this.value); changEvtDieDate();"/>                                    
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">　國、勞合併申請：</span>
                                    <html:text property="combapMark" styleId="combapMark" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();"/>                                    
                                    <span class="formtxt">(合併申請者，請輸入Y)</span>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="2">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <html:text property="appDate" styleId="appDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value)"/>                                    
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">　申請單位保險證號：</span>
                                    <html:text property="apUbno" styleId="apUbno" styleClass="textinput" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>                                    
                                </td>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">最後單位保險證號：</span>
                                    <html:text property="lsUbno" styleId="lsUbno" styleClass="textinput" size="8" maxlength="8" onblur="this.value=asc(this.value).toUpperCase();"/>                                    
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="2">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">申請項目：</span>
                                    <html:select property="apItem">
                                        <html:option value="">請選擇</html:option>
                                        <html:options collection="<%=ConstantKey.APITEM_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                    </html:select>
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">核定格式：</span>
                                    <html:text property="notifyForm" styleId="notifyForm" styleClass="textinput" size="5" maxlength="3" onblur="this.value=asc(this.value).toUpperCase();"/>                                    
                                </td>
                            </tr>
                            <%--
                            <c:if test="${(app.closeCause eq '1')and(app.evtDieDate ne '')and(app.evtDieDate ne null)}">
                            <tr>
                                <td id="iss" colspan="3">
                                    <span class="issuetitle_L_down">已領失能年金受理編號：</span>
                                    <html:text property="dabApNo1" styleId="dabApNo1" styleClass="textinput" size="1" maxlength="1" onkeyup="this.value=asc(this.value).toUpperCase(); autotab($('dabApNo1'), $('dabApNo2'))" />
                                    &nbsp;-
                                    <html:text property="dabApNo2" styleId="dabApNo2" styleClass="textinput" size="1" maxlength="1" onkeyup="autotab($('dabApNo2'), $('dabApNo3'))"/>
                                    &nbsp;-
                                    <html:text property="dabApNo3" styleId="dabApNo3" styleClass="textinput" size="5" maxlength="5" onkeyup="autotab($('dabApNo3'), $('dabApNo4'))" />
                                    &nbsp;-
                                    <html:text property="dabApNo4" styleId="dabApNo4" styleClass="textinput" size="5" maxlength="5" />                                        
                                </td>
                            </tr>
                            </c:if>
                            --%>
                            <tr>
                                <td id="iss" valign="top" width="15%">
                                    <span class="issuetitle_L_down">　事故者編審註記：</span>
                                </td>
                                <td id="iss" valign="top" width="85%" colspan="3">
                                    <logic:notEmpty name="<%=ConstantKey.CHKFILE_OPTION_LIST%>">
                                        <logic:iterate id="chkFileList" indexId="i" name="<%=ConstantKey.CHKFILE_OPTION_LIST%>" >
                                            <c:out value="${chkFileList.payYmStr}" /><c:out value="：" />                                        
                                            <%--<bean:define id="chkFile" name="chkFileList" property="chkFileCollection"/>--%>
                                            <logic:iterate id="chkFile" indexId="j" name="chkFileList" property="chkFileCollection" >
                                                <span title="<c:out value="${chkFile.chkFileDesc}" />"><c:out value="${chkFile.chkFileName}" /></span>
                                                <%if(j.intValue()+1!=(((List<ChkFileCollectionCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).get(i.intValue())).getChkFileCollection().size()) {%>
                                                        <c:out value=",　" />
                                                <%}%>
                                            </logic:iterate>
                                            <%if(i.intValue()+1!=((List<ChkFileCollectionCase>)request.getSession().getAttribute(ConstantKey.CHKFILE_OPTION_LIST)).size()) {%>
                                                <br>
                                            <%}%>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                    <span id="chkContent">&nbsp;</span>                                    
                                </td>
                            </tr>
                            <c:if test="${app.isShowLoanMk eq 'Y'}">
                                <tr>
                                    <td id="iss" colspan="4">
                                        <html:checkbox property="loanMk" styleId="loanMk" value="1" />不須抵銷紓困貸款
                                        <div id="owesContent" style="display: none">                                    
                                            <input type="button" name="button" class="button_90" value="欠費期間維護" onclick="$('page').value='1'; $('method').value='prepareOwesDataUpate'; document.ApplicationDataUpdateForm.submit();"/>
                                        </div>&nbsp;
                                    </td>   
                                </tr>
                            </c:if>
                            <c:if test="${app.isShowLoanMk ne 'Y'}">
                                <tr style="display: none">
                                    <td id="iss" colspan="4">
                                        <div id="owesContent" style="display: none">                                    
                                            <input type="button" name="button" class="button_90" value="欠費期間維護" onclick="$('page').value='1'; $('method').value='prepareOwesDataUpate'; document.ApplicationDataUpdateForm.submit();"/>
                                        </div>&nbsp;
                                    </td>   
                                </tr>
                            </c:if>
                            <tr>
                                <td id="iss" colspan="4">
                                    <div id="closeCauseContent" style="display: inline">                            
                                    <span class="issuetitle_L_down">　結案原因：</span>
                                    <html:select property="closeCause" onchange="changeCloseCause();">
                                        <html:option value="">請選擇</html:option>
                                        <logic:iterate id="optionList" name="<%=ConstantKey.CLOSECAUSE_OPTION_LIST%>">
                                            <%--<c:if test="${app.oncePayMk eq 'N'}">
                                                <c:if test="${optionList.paramCode eq '2'}">
                                                    <html:option value="${optionList.paramCode}"><c:out value="${optionList.paramName}" /></html:option>
                                                </c:if>                    
                                            </c:if>
                                            <c:if test="${app.oncePayMk ne 'N'}">--%>
                                                <html:option value="${optionList.paramCode}"><c:out value="${optionList.paramName}" /></html:option>
                                            <%--</c:if>--%>
                                        </logic:iterate>
                                        <%--
                                        <html:options collection="<%=ConstantKey.CLOSECAUSE_OPTION_LIST%>" property="paramCode" labelProperty="paramName"/>
                                        --%>
                                    </html:select>
                                    </div>
                                    <div id="onceAmtContent" style="display: none">
                                        <c:if test="${(app.closeCause eq '1')and(app.evtDieDate ne '')and(app.evtDieDate ne null)}">
                                            <input type="button" name="button" class="button_90" value="一次給付更正" onclick="$('page').value='1'; $('method').value='prepareOnceAmtDataUpdate'; document.ApplicationDataUpdateForm.submit();"/>
                                        </c:if>
                                    </div>                                    
                                </td>
                                <td id="iss" colspan="2">
                                    <div id="closeDateContent" style="display: none">
                                    <span class="issuetitle_L_down">　結案日期：</span>
                                    <html:text property="closeDate" styleId="closeDate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>                                    
                                    </div>
                                </td>                                
                            </tr>
                            <tr>
                                <td colspan="4"></td>
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
</div>

<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
