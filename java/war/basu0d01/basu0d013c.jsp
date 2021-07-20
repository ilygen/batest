<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BASU0D013C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/executiveCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="ExecutiveSupportMaintModifyForm" page="1" />
    <script language="javascript" type="text/javascript">
    //頁面欄位值初始化       
    function initAll(){   
      checkClosdate();  

      if($("letterType").value == "31"){
          $("reliefKindContent").style.display="inline";
          $("reliefStatContent").style.display="inline";
          $("reliefTypContent").style.display="inline";
      } else {
          $("reliefKindContent").style.display="none";
          $("reliefStatContent").style.display="none";
          $("reliefTypContent").style.display="none";
      }
      
      //檢核是否為列管醫院
      getHpTitleStr();

    }    
    
    //根據處理函別 改變處理註記 的選單選項  
    function changeOption(obj){
        //處理函別為21不給付函，不可更改為其他函別
        if("<c:out value="${ExecutiveSupportMaintModifyForm.letterType}" />" == 21){
            $("letterType").value = 21;
            alert("處理函別為不給付函，不可更改為其他函別")
            return;
        }
        
        //處理函別為其他函別，不可更改為不給付函
        if("<c:out value="${ExecutiveSupportMaintModifyForm.letterType}" />" != 21 &&  $("letterType").value == 21){
            $("letterType").value = "<c:out value="${ExecutiveSupportMaintModifyForm.letterType}" />";
            alert("處理函別其他函別，不可更改為不給付函")
            return;
        }
        checkClosdate();
        var lpaymk="";
        var kpaymk="";
        var spaymk="";
        if("L"=="<c:out value="${ExecutiveSupportMaintCase.kind}" />") lpaymk=1;
        if("K"=="<c:out value="${ExecutiveSupportMaintCase.kind}" />") kpaymk=1;
        if("S"=="<c:out value="${ExecutiveSupportMaintCase.kind}" />") spaymk=1;
        executiveCommonAjaxDo.getNdomkOptionList(obj.options[obj.selectedIndex].value, lpaymk, kpaymk, spaymk, setOptionType);
        
        if($("letterType").value == "31"){
            $("reliefKindContent").style.display="inline";
            $("reliefStatContent").style.display="inline";
            $("reliefTypContent").style.display="inline";
        } else {
            $("reliefKindContent").style.display="none";
            $("reliefStatContent").style.display="none";
            $("reliefTypContent").style.display="none";
        }
        
        if("<c:out value="${ExecutiveSupportMaintCase.procStatMk}" />" == "Y"){
            if($("letterType").value == "11" || $("letterType").value == "13" || $("letterType").value == "21") {
            	document.getElementById("btnSave").disabled = true;
            }else{
            	document.getElementById("btnSave").disabled = false;
            }
        }
    }
    
    function setOptionType(data){
        DWRUtil.removeAllOptions("ndomk1");
        DWRUtil.addOptions("ndomk1", {'':'請選擇...'});
        DWRUtil.addOptions("ndomk1", data ,'ndomk','ndomkName');
        
        DWRUtil.removeAllOptions("ndomk2");
        DWRUtil.addOptions("ndomk2", {'':'請選擇...'});
        DWRUtil.addOptions("ndomk2", data ,'ndomk','ndomkName');
    }
    
    function changeRelief(obj){
        if(obj.options[obj.selectedIndex].value == ""){
            DWRUtil.removeAllOptions("reliefStat");
            DWRUtil.addOptions("reliefStat", {'':'請選擇...'});
        } else {
            executiveCommonAjaxDo.getReliefOptionList(obj.options[obj.selectedIndex].value, setOption);
        }
    }
    
    function setOption(data){
        DWRUtil.removeAllOptions("reliefStat");
        DWRUtil.addOptions("reliefStat", {'':'請選擇...'});
        DWRUtil.addOptions("reliefStat", data ,'paramCode','paramName');
    }
    
    //檢查處理註記一 二不可相同
    function checkOption(obj){
       if($("ndomk1").value == $("ndomk2").value){
          alert("處理註記一,處理註記二 兩欄位必須不同");
          $("ndomk2").value='';
       }
    }
    
    //處理函別」為21不給付函，則銷案日期不提供登錄且欄位值清空。
    function checkClosdate(){
       if($("letterType").value == "21" || $("letterType").value == "22" || $("letterType").value == "23" || 
       	  $("letterType").value == "24" || $("letterType").value == "30" || $("letterType").value == "31" || 
       	  $("letterType").value == "40" )
       	  {
           $("closDate").value = "";
           $("closDate").disabled=true;
       }else{
           $("closDate").disabled=false;
       }    
    }
    
    //理函別欄位值為： 21不給付函時，不可刪除
    function checkType21(){
        if($("letterType").value == "21"){
            alert("處理函別為21不給付函時不可刪除")
            return false;
        }
        return true;
    }
    
    function chgClosDate(){
        if($("closDate").value != ""){
            $("delMk").value = "";
            $("delMk").disabled = true;
        }
        else {
            $("delMk").disabled = false;
        }
    }
    
    function chgDelMk(){
        if($("delMk").value != ""){
            $("closDate").value = "";
            $("closDate").disabled = true;
        }
        else {
            $("closDate").disabled = false;
        }
    }
    
    <%-- begin 檢查必填欄位 --%>
    function checkRequireFields(q2) {
        var prodate = new String(changeDateType($("proDate").value));
        var oldProdate =  new String(changeDateType("<c:out value="${ExecutiveSupportMaintModifyForm.proDate}" />"));
        var msg = "";

        //理函別欄位值為： 21不給付函時，則承辦日期不可小於原承辦日期
        if($("letterType").value == 21 && prodate < oldProdate)
            msg += '「21-不給付函」承辦日期不可小於原承辦日期。\r\n';
        if(Trim($("delMk").value) != "" && Trim($("delMk").value) != "D")
            msg += '「管制註記」只能允許輸入英文大寫D。\r\n';
        if(Trim($("letterType").value) == "31"){
            if(Trim($("reliefKind").value) == "")
                msg += '「救濟種類」為必輸欄位。\r\n';
            if(Trim($("reliefStat").value) == "")
                msg += '「行政救濟辦理情形」為必輸欄位。\r\n';
            if(Trim($("reliefTyp").value) == "")
                msg += '「給付性質」為必輸欄位。\r\n';
        }
        //處理函別為「11-補件函」，函別內容一為「4-通知複檢」或函別內容二為「4-通知複檢」，則「醫院代號為必填」
       	if($("letterType").value == 11){
       		if($("ndomk1").value==4&&Trim($("hpNo").value) == '' || $("ndomk2").value==4&&Trim($("hpNo").value) == '' ){
       			msg += '「醫院代號」為必輸欄位。\r\n';
       		}
       		
       	}
        
       
        if($("letterType").value == 17 && q2>0){
        	msg += '同一承辦日期已存在\r\n';
        }  
         
        
        if (msg != "") {
            alert(msg);
            return false;
        }
        else {
        	document.ExecutiveSupportMaintModifyForm.submit();
            
        }      
    }
    
    function getStatus(){
    
    	var msg = "";
    	var apNo = "<c:out value="${ExecutiveSupportMaintCase.apNo}" />";
        var issuYm = "<c:out value="${ExecutiveSupportMaintCase.issuYm}" />";
        var proDate = $("proDate").value;
        var hpNo = $("hpNo").value;
        var prodDateOld="<c:out value="${ExecutiveSupportMaintDetailCase.proDate}" />";
        var hpNoOld = "<c:out value="${ExecutiveSupportMaintDetailCase.hpNo}" />";
        
         //處理函別為「17-調病歷函」，則「醫院代號」為必填
        if($("letterType").value == 17 && Trim($("hpNo").value) == ''){
            msg += '「醫院代號」為必輸欄位。\r\n';
        }
        //處理函別為「17-調病歷函」，則「來受文號」為必填
        if($("letterType").value == 17&&Trim($("slrpNo1").value)==''&&Trim($("slrpNo2").value)==''&&Trim($("slrpNo3").value)==''&&Trim($("slrpNo4").value)==''&&Trim($("slrpNo5").value)==''){
        	msg += '「來受文號」為必輸欄位。\r\n';
        }
        
         if (msg != "") {
            alert(msg);
            return false;
        }
        else {
        
	        if($("letterType").value == 17 &&  prodDateOld != $("proDate").value &&  hpNoOld!= $("hpNo").value){
	          	executiveCommonAjaxDo.getBabanCount(apNo,issuYm,proDate,hpNo,checkRequireFields);
	        }else{
	        	checkRequireFields(0);
	        } 
        }
     	
    }
    
     //查詢醫院名稱及是否為列管醫院
    function getHpTitleStr(){
        var hpNo = $("hpNo").value;
        executiveCommonAjaxDo.getHpTitleStr(hpNo, chkHpTitleStr);
    }
    
    function chkHpTitleStr(hpTitleStr){
        document.getElementById("hpTitleStr").innerHTML = hpTitleStr[0];
        
        if(hpTitleStr[1] == "Y"){
           alert("此為列管醫院");
        }
    }
    
    // 處理函別欄位值為： 21，存檔時檢核
    function checkType21_2(){
    	// BAAPPBASE.PAYYMS 有值，處理函別選 21-不給付函，函別內容一或函別內容二選 54-延不補正時，秀出警告訊息且不予存檔。
    	var payYms = "<c:out value="${ExecutiveSupportMaintCase.payYms}" />";

        if($("letterType").value == "21"  && Trim(payYms) != '' && ($("ndomk1").value == 54 || $("ndomk2").value == 54)){
            alert("此案為給付案件，不得做不給付案延不補正。\r\n")
            return false;
        }
        return true;
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
        <html:form action="/executiveSupportMaintModify" method="post" onsubmit="return validateExecutiveSupportMaintModifyForm(this);">
        
        <fieldset>
            <legend>&nbsp;行政支援記錄維護 &nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
            <tr>
                <td align="right">
                    <html:hidden styleId="page" property="page" value="1" />
                    <html:hidden styleId="method" property="method" value="" />
                    <acl:checkButton buttonName="btnSave">
                        <input type="button" name="btnSave" id="btnSave" class="button" value="確　 認" <c:if test="${buttonStatus=='true'}">disabled="true"</c:if> onclick="$('page').value='1'; $('method').value='doSave'; if (document.ExecutiveSupportMaintModifyForm.onsubmit() && checkType21_2() && checkRequireFields()){document.ExecutiveSupportMaintModifyForm.submit();}else{return false;}" />
                    </acl:checkButton>
                    &nbsp;
                    <acl:checkButton buttonName="btnDelete">
                        <input type="button" name="btnDelete" class="button" value="刪　除" <c:if test="${buttonStatus=='true'}">disabled="true"</c:if> onclick="$('page').value='1'; $('method').value='doDelete';if (document.ExecutiveSupportMaintModifyForm.onsubmit() && checkType21()){document.ExecutiveSupportMaintModifyForm.submit();}else{return false;}" />
                    </acl:checkButton>
                    &nbsp;
                    <acl:checkButton buttonName="btnBack">
                        <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.ExecutiveSupportMaintModifyForm.submit();" />
                    </acl:checkButton>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                        <tr>
                            <td width="25%">
                                <span class="issuetitle_L_down">受理編號：</span>
                                <c:out value="${ExecutiveSupportMaintCase.apNoStr}" />
                            </td>
                            <td width="25%">
                                <span class="issuetitle_L_down">給付別：</span>
                                <c:out value="${ExecutiveSupportMaintCase.payKindStr}" />
                            </td>
                            <td width="25%">
                                <span class="issuetitle_L_down">審核人員：</span>
                                <c:out value="${ExecutiveSupportMaintCase.chkMan}" />
                            </td>
                            <td width="25%">
                                <span class="issuetitle_L_down">核定年月：</span>
                                <c:out value="${ExecutiveSupportMaintCase.issuYm}" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <span class="issuetitle_L_down">事故者姓名：</span>
                                <c:out value="${ExecutiveSupportMaintCase.evtName}" />
                            </td>
                            <td width="25%">
                                <span class="issuetitle_L_down">事故者出生日期：</span>
                                <c:out value="${ExecutiveSupportMaintCase.evtBrDate}" />
                            </td>
                            <td width="25%">
                                <span class="issuetitle_L_down">事故者身分證號：</span>
                                <c:out value="${ExecutiveSupportMaintCase.evtIdnNo}" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="issuetitle_L_down">序號：</span>
                                <c:out value="${ExecutiveSupportMaintDetailCase.seqNo}" />
                            </td>
                            <td colspan="3">
                                <span class="issuetitle_L_down">來源別：</span>
                                <c:out value="${ExecutiveSupportMaintDetailCase.sourceStr}" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td align="center" class="table1">
                    <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                        <tr>
                            <td id="iss" width="50%">
                                <span class="needtxt">＊</span>
                                <span class="issuetitle_L_down">處理函別：</span>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "1"}'>
                                <html:select styleId="letterType" property="letterType" onchange="changeOption(this);" >
                                    <c:if test='${ExecutiveSupportMaintDetailCase.letterType ne "17"}'>
                                    <html:option value="">請選擇...</html:option>
                                    </c:if>
                                    <html:options collection="<%=ConstantKey.LETTERTYPE_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                </html:select>
                                </c:if>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "0"}'>
                                    <html:hidden styleId="letterType" property="letterType" styleClass="textinput" />
                                    <c:out value="${ExecutiveSupportMaintDetailCase.letterType}" /> - <c:out value="${ExecutiveSupportMaintDetailCase.paramName}" />
                                </c:if>
                            </td>
                            <td id="iss" width="50%">
                                <span class="needtxt">＊</span>
                                <span class="issuetitle_L_down">承辦日期：</span>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "1"}'>
                                    <html:text styleId="proDate" property="proDate" size="10" maxlength="7" styleClass="textinput" onblur="this.value=asc(this.value).toUpperCase();"/>
                                </c:if>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "0"}'>
                                    <html:hidden styleId="proDate" property="proDate" styleClass="textinput" />
                                    <c:out value="${ExecutiveSupportMaintDetailCase.proDate}" />
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td id="iss" colspan="2">
                                <span class="issuetitle_L_down">承辦人員：</span>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "1"}'>
                                    <html:text styleId="promoteUser" property="promoteUser" size="12" maxlength="12" styleClass="textinput" />
                                </c:if>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "0"}'>
                                    <html:hidden styleId="promoteUser" property="promoteUser" styleClass="textinput" />
                                    <c:out value="${ExecutiveSupportMaintDetailCase.promoteUser}" />
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td id="iss">
                                <span class="needtxt">＊</span>
                                <span class="issuetitle_L_down">函別內容一/救濟事由：</span>
                                <html:select styleId="ndomk1" property="ndomk1" onchange="checkOption();">
                                    <html:option value="">請選擇...</html:option>
                                    <html:options collection="<%=ConstantKey.NDOMK_OPTION_LIST%>" property="ndomk" labelProperty="ndomkName" />
                                </html:select>
                            </td>
                            <td id="iss">
                                <span class="issuetitle_L_down">函別內容二/救濟事由：</span>
                                <html:select styleId="ndomk2" property="ndomk2" onchange="checkOption();">
                                    <html:option value="">請選擇...</html:option>
                                    <html:options collection="<%=ConstantKey.NDOMK_OPTION_LIST%>" property="ndomk" labelProperty="ndomkName" />
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td id="iss">
                                <div id="reliefKindContent">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">救濟種類：</span>
                                    <html:select styleId="reliefKind" property="reliefKind" onchange="changeRelief(this);">
                                        <html:option value="">請選擇...</html:option>
                                        <html:options collection="<%=ConstantKey.RELIEFKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                    </html:select>
                                </div>
                            </td>
                            <td id="iss">
                                <div id="reliefStatContent">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">行政救濟辦理情形：</span>
                                    <html:select styleId="reliefStat" property="reliefStat">
                                        <html:option value="">請選擇...</html:option>
                                        <html:options collection="<%=ConstantKey.RELIEFSTAT_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                    </html:select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td id="iss" colspan="2">
                                <div id="reliefTypContent">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">給付性質：</span>
                                    <html:select styleId="reliefTyp" property="reliefTyp">
                                        <html:option value="">請選擇...</html:option>
                                        <html:options collection="<%=ConstantKey.RELIEFTYP_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                    </html:select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td id="iss">
                                <span class="issuetitle_L_down">來受文號：</span>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "1"}'>
                                <html:text styleId="slrpNo1" property="slrpNo1" size="3" maxlength="3" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('slrpNo1'), $('slrpNo2'))" />&nbsp;-
                                <html:text styleId="slrpNo2" property="slrpNo2" size="2" maxlength="2" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('slrpNo2'), $('slrpNo3'))" />&nbsp;-
                                <html:text styleId="slrpNo3" property="slrpNo3" size="1" maxlength="1" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('slrpNo3'), $('slrpNo4'))" />&nbsp;-
                                <html:text styleId="slrpNo4" property="slrpNo4" size="6" maxlength="6" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('slrpNo4'), $('slrpNo5'))" />&nbsp;-
                                <html:text styleId="slrpNo5" property="slrpNo5" size="1" maxlength="1" styleClass="textinput" />
                                </c:if>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "0"}'>
                                    <html:hidden styleId="slrpNo1" property="slrpNo1" styleClass="textinput" />
                                    <html:hidden styleId="slrpNo2" property="slrpNo2" styleClass="textinput" />
                                    <html:hidden styleId="slrpNo3" property="slrpNo3" styleClass="textinput" />
                                    <html:hidden styleId="slrpNo4" property="slrpNo4" styleClass="textinput" />
                                    <html:hidden styleId="slrpNo5" property="slrpNo5" styleClass="textinput" />
                                    <c:out value="${ExecutiveSupportMaintDetailCase.slrpNoStr}" />
                                </c:if>
                            </td>
                            <td id="iss">
                                <span class="issuetitle_L_down">來受文日期：</span>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "1"}'>
                                    <html:text styleId="slDate" property="slDate" size="10" maxlength="7" styleClass="textinput" />
                                </c:if>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "0"}'>
                                    <html:hidden styleId="slDate" property="slDate" styleClass="textinput" />
                                    <c:out value="${ExecutiveSupportMaintDetailCase.slDate}" />
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td id="iss">
                                <span class="issuetitle_L_down">來受文機關代號：</span>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "1"}'>
                                    <html:text styleId="unNo" property="unNo" size="10" maxlength="10" styleClass="textinput" />
                                </c:if>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "0"}'>
                                    <html:hidden styleId="unNo" property="unNo" styleClass="textinput" />
                                    <c:out value="${ExecutiveSupportMaintDetailCase.unNo}" />
                                </c:if>
                            </td>
                            <td id="iss">
                                <span class="issuetitle_L_down">來受文機關名稱：</span>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "1"}'>
                                    <html:text styleId="unDes" property="unDes" size="30" maxlength="15" styleClass="textinput" />
                                </c:if>
                                <c:if test='${ExecutiveSupportMaintDetailCase.source eq "0"}'>
                                    <html:hidden styleId="unDes" property="unDes" styleClass="textinput" />
                                    <c:out value="${ExecutiveSupportMaintDetailCase.unDes}" />
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td id="iss">
                                <span class="issuetitle_L_down">主　旨：</span>
                                <%-- <html:text styleId="note" property="note" size="50" maxlength="30" styleClass="textinput" /> --%>
                                <html:textarea property="note" styleId="note" cols="70" rows="5" styleClass="formtxt" ></html:textarea>
                            </td>
                            <td id="iss">
                                <span class="issuetitle_L_down">分類號：</span>
                                <html:text styleId="caseTyp" property="caseTyp" size="6" maxlength="6" styleClass="textinput" />
                            </td>
                        </tr>
                        <tr>
                            <td id="iss">
                                <span class="issuetitle_L_down">承辦註記：</span>
                                <html:text styleId="sddMk" property="sddMk" size="2" maxlength="2" styleClass="textinput" onblur="this.value=asc(this.value).toUpperCase();"/>
                            </td>
                            <td id="iss">
                                <span class="issuetitle_L_down">管制註記：</span>
                                <html:text styleId="delMk" property="delMk" size="2" maxlength="1" styleClass="textinput" onblur="this.value=asc(this.value).toUpperCase();"/>
                            </td>
                        </tr>
                        <tr>
                            <td id="iss">
                                <span class="issuetitle_L_down">承辦完成日：</span>
                                <html:text styleId="finishDate" property="finishDate" size="10" maxlength="7" styleClass="textinput" />
                            </td>
                            <div id="paymkContent">
                            <td id="iss">
                                <span class="issuetitle_L_down">付費註記：</span>
                                <html:select styleId="payMk" property="payMk">
                                    <html:option value="">請選擇...</html:option>
                                    <html:option value="Y">Y-已付</html:option>
                                    <html:option value="A">A-預付</html:option>
                                    <html:option value="N">N-不付</html:option>
                                </html:select>
                            
                            </td>
                            </div>
                        </tr>
                        <tr>
                            <td id="iss">
                                <span class="issuetitle_L_down">相關文號：</span>
                                <html:text styleId="slrelate1" property="slrelate1" size="3" maxlength="3" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('slrelate1'), $('slrelate2'))" />&nbsp;-
                                <html:text styleId="slrelate2" property="slrelate2" size="2" maxlength="2" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('slrelate2'), $('slrelate3'))" />&nbsp;-
                                <html:text styleId="slrelate3" property="slrelate3" size="1" maxlength="1" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('slrelate3'), $('slrelate4'))" />&nbsp;-
                                <html:text styleId="slrelate4" property="slrelate4" size="6" maxlength="6" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('slrelate4'), $('slrelate5'))" />&nbsp;-
                                <html:text styleId="slrelate5" property="slrelate5" size="1" maxlength="1" styleClass="textinput" />
                            </td>
                        </tr>
                        <tr>
                            <td id="iss">
                                <span class="issuetitle_L_down">銷案日期：</span>
                                <html:text styleId="closDate" property="closDate" size="10" maxlength="7" styleClass="textinput" />
                            </td>
                            <td id="iss">
                                <span class="issuetitle_L_down">銷案文號：</span>
                                <html:text styleId="closeNo1" property="closeNo1" size="3" maxlength="3" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('closeNo1'), $('closeNo2'))" />&nbsp;-
                                <html:text styleId="closeNo2" property="closeNo2" size="2" maxlength="2" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('closeNo2'), $('closeNo3'))" />&nbsp;-
                                <html:text styleId="closeNo3" property="closeNo3" size="1" maxlength="1" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('closeNo3'), $('closeNo4'))" />&nbsp;-
                                <html:text styleId="closeNo4" property="closeNo4" size="6" maxlength="6" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('closeNo4'), $('closeNo5'))" />&nbsp;-
                                <html:text styleId="closeNo5" property="closeNo5" size="1" maxlength="1" styleClass="textinput" />
                            </td>
                        </tr>
                        <tr>
                            <td id="iss">
                                <span class="issuetitle_L_down">醫院代號：</span>
                                <html:text styleId="hpNo" property="hpNo" size="10" maxlength="10" styleClass="textinput" onblur="this.value=asc(Trim(this.value)).toUpperCase();if(Trim(this.value)!=''){this.value = leftPad(Trim(this.value).toUpperCase(),10,'0')};getHpTitleStr();"/>
                                <span id="hpTitleStr" class="issuetitle_L_down"></span>
                            </td>
                            <div id="injserContent">
                            <td id="iss">
                                <span class="issuetitle_L_down">爭議部位：</span>
                                <html:text styleId="injser" property="injser" size="2" maxlength="2" styleClass="textinput" onblur="this.value=asc(this.value).toUpperCase();"/>
                            </td>
                            </div>
                        </tr>
                        <tr>
                            <td id="iss" colspan="2">
                                <span class="issuetitle_L_down">單位保險證號：</span>
                                <html:text styleId="unitNo" property="unitNo" size="8" maxlength="8" styleClass="textinput" onblur="this.value=asc(this.value).toUpperCase();"/>
                            </td>                  
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
