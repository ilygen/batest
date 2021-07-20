<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D061C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>    
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
</head>
<script language="javascript" type="text/javascript">
    <!--
     baChkFileIdW = new Array(0);
     baChkFileIdO = new Array(0);
    //頁面欄位值初始化       
    function initAll(){   
       
    }    

    function checkRegetCipbMk(apNo) {
    	var regetCipbMk = "";
        if($("regetCipbMk").checked==true){
        	regetCipbMk = "1";        
        }else if($("regetCipbMk").checked==false){
        	regetCipbMk = "";      
        }
        
        updateCommonAjaxDo.setRegetCipbMk(apNo, regetCipbMk);            
    }
        
    //根據傳進來的 CHKCODE 找尋相同的CHKCODE的RADIO欄位 
    function findRaidoByChkCode(chkCode){
       chkCodeRadios = new Array(0); 
       elements =document.getElementsByTagName('*');
       for (var i = 0; i < elements.length; i++) {
       obj = elements[i];
       if("radio" == obj.type){
            var name = new String(obj.name); 
              if(name.indexOf(chkCode)=='0'){
                radioArray = new Array(1);
                radioArray[0]=obj;
                chkCodeRadios = chkCodeRadios.concat(radioArray);
              }
         }
       }
       return chkCodeRadios;
    }
    
    //修改為W
    function changeToW(chkCode){
     chkCodeRadios = findRaidoByChkCode(chkCode);
      for(i = 0;i<chkCodeRadios.length; i++){
        obj = chkCodeRadios[i];
        if("W" == obj.value){
          obj.checked = 'true';
          var name = new String(obj.name); 
          id = name.substr(name.indexOf('-')+1,name.length);
          addIdW(id);
          delIdO(id);
        }
      }
    }
    //修改為O
    function changeToO(chkCode){  
      chkCodeRadios = findRaidoByChkCode(chkCode);
      for(i = 0;i<chkCodeRadios.length; i++){
        obj = chkCodeRadios[i];
        if("O" == obj.value){
          obj.checked = 'true';
          var name = new String(obj.name); 
          id = name.substr(name.indexOf('-')+1,name.length);
          addIdO(id);
          delIdW(id);
        }
      }
    }
    
    
    //增加修改為W的baChkFileId
    function addIdW(id){
      flag = true;
    for(var i=0;i<baChkFileIdW.length;i++){
       if(id == baChkFileIdW[i]){
         flag = false;
       } 
    }
      if(flag){
        var ay = new Array(1);
        ay[0]=id;
        baChkFileIdW = baChkFileIdW.concat(ay);
      }
      $('baChkFileIdW').value = baChkFileIdW;
      //alert('baChkFileIdW = '+$('baChkFileIdW').value);
    }
    
     //增加修改為O的baChkFileId
     function addIdO(id){
      flag = true;
    for(var i=0;i<baChkFileIdO.length;i++){
       if(id == baChkFileIdO[i]){
         flag = false;
       } 
    }
      if(flag){
        var ay = new Array(1);
        ay[0]=id;
        baChkFileIdO = baChkFileIdO.concat(ay);
      }
      $('baChkFileIdO').value = baChkFileIdO;
      //alert('baChkFileIdO = '+$('baChkFileIdO').value);
    }
    
    //刪除修改為W的baChkFileId
    function delIdW(id){
       for(var i=0;i<baChkFileIdW.length;i++){
          if(id == baChkFileIdW[i]){
              baChkFileIdW[i]=null;
           } 
       }
       $('baChkFileIdW').value = baChkFileIdW;
       //alert('baChkFileIdW = '+$('baChkFileIdW').value);
    }
    
    //刪除修改為O的baChkFileId
    function delIdO(id){
       for(var i=0;i<baChkFileIdO.length;i++){
          if(id == baChkFileIdO[i]){
              baChkFileIdO[i]=null;
           } 
       }
       $('baChkFileIdO').value = baChkFileIdO;
       //alert('baChkFileIdO = '+$('baChkFileIdO').value);
    }
    
    function controlButton() { 
    	// 獲得該button對象 
    	var btnUpdate = document.getElementById('btnUpdate'); 
    	var btnPrint = document.getElementById('btnPrint');
    	var btnBack = document.getElementById('btnBack');
    	// 創建計時器 返回計時器ID 
    	var intervalId = setInterval(function () { 
    		btnUpdate.disabled = true;
    		btnPrint.disabled = true;
    		btnBack.disabled = true;
    		}, 1000); 
    	} 
    function getRprUrl(){
        var apNo1Begin = '<c:out value="${CheckMarkLevelAdjustCase.apNo1Str}" />';
        var apNo2Begin = '<c:out value="${CheckMarkLevelAdjustCase.apNo2Str}" />';
        var apNo3Begin = '<c:out value="${CheckMarkLevelAdjustCase.apNo3Str}" />';
        var apNo4Begin = '<c:out value="${CheckMarkLevelAdjustCase.apNo4Str}" />';
        var apNo1End = '<c:out value="${CheckMarkLevelAdjustCase.apNo1Str}" />';
        var apNo2End = '<c:out value="${CheckMarkLevelAdjustCase.apNo2Str}" />';
        var apNo3End = '<c:out value="${CheckMarkLevelAdjustCase.apNo3Str}" />';
        var apNo4End = '<c:out value="${CheckMarkLevelAdjustCase.apNo4Str}" />';
        //var url =  "/BaWeb/printOldAgeReviewRpt01.do?method=doReport&apNo1Begin=" + apNo1Begin + "&apNo2Begin=" + apNo2Begin + "&apNo3Begin=" + apNo3Begin + "&apNo4Begin=" + apNo4Begin + "&apNo1End=" + apNo1End + "&apNo2End=" + apNo2End + "&apNo3End=" + apNo3End + "&apNo4End=" + apNo4End;
        //alert(url);
        this.location.href = "/BaWeb/printOldAgeReviewRpt01.do?method=doReport&apNo1Begin=" + apNo1Begin + "&apNo2Begin=" + apNo2Begin + "&apNo3Begin=" + apNo3Begin + "&apNo4Begin=" + apNo4Begin + "&apNo1End=" + apNo1End + "&apNo2End=" + apNo2End + "&apNo3End=" + apNo3End + "&apNo4End=" + apNo4End + "&action=single";
        
    }

    
    Event.observe(window, 'load', initAll, false);
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    </script>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/checkMarkLevelAdjust" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="baChkFileIdW" property="baChkFileIdW" value="" />
        <html:hidden styleId="baChkFileIdO" property="baChkFileIdO" value="" />
        <fieldset>
            <legend>&nbsp;老年年金編審註記程度調整 &nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
           <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
           <tr>           
                <td align="right" colspan="8">
                    <acl:checkButton buttonName="btnUpdate">
                        <input type="button" name="btnUpdate" class="button" value="確　 認" onclick="$('page').value='1'; $('method').value='doUpdate'; document.CheckMarkLevelAdjustForm.submit();controlButton();" />&nbsp;
                    </acl:checkButton>
                    <acl:checkButton buttonName="btnPrint">
                        <input name="btnPrint" type="button" class="button_120" value="檢視受理/審核清單" onclick="getRprUrl();" />&nbsp;
                    </acl:checkButton>
                    <acl:checkButton buttonName="btnBack">
                        <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.CheckMarkLevelAdjustForm.submit()" />
                    </acl:checkButton>
                </td>
          </tr>
          <tr>
            <td>
                <table width="100%" border="0" cellspacing="2" cellpadding="2" class="px13">
                <tr>
                  <td width="50%"><span class="issuetitle_L_down">受理編號：</span><c:out value="${CheckMarkLevelAdjustCase.formatApNo}" /></td>
                  <td width="50%"><span class="issuetitle_L_down">給付別：</span>
                         <c:if test='${(CheckMarkLevelAdjustCase.kind eq "L")}'>
                             <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.kind eq "K")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.kind eq "S")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                         </c:if>
                   </td>
                </tr>
                </table>
            </td>
          </tr>
          <tr>
            <td colspan="8" align="center" class="table1">
              <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                <tr>
                  <td colspan="4" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;案件資料</td>
                </tr>
                <tr>
                  <td width="50%" id="iss"  colspan="2"><span class="issuetitle_L_down">事故者姓名：</span><c:out value="${CheckMarkLevelAdjustCase.evtName}" /></td>

                  <td width="25%" id="iss"><span class="issuetitle_L_down">事故者身分證號：</span><c:out value="${CheckMarkLevelAdjustCase.evtIdnNo}" /></td>
                  <td width="25%" id="iss"><span class="issuetitle_L_down">事故者出生日期：</span><c:out value="${CheckMarkLevelAdjustCase.chineseEvtBrDate}" /></td>
                </tr>
                <tr>
                  <td id="iss" width="25%"><span class="issuetitle_L_down">核定年月：</span><c:out value="${CheckMarkLevelAdjustCase.chineseIssuYm}" /></td>
                  <td id="iss" width="25%"><span class="issuetitle_L_down">申請日期：</span><c:out value="${CheckMarkLevelAdjustCase.chineseAppDate}" /></td>
                  <td id="iss" width="50%" colspan="2"><span class="issuetitle_L_down">事故日期：</span><c:out value="${CheckMarkLevelAdjustCase.evtJobDateStr}" /></td>
                </tr>
                <tr>
                  <td id="iss" width="25%"><span class="issuetitle_L_down">處理狀態：</span>
                         <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "00")}'>
                             <c:out value="<%=ConstantKey. BAAPPBASE_PROCSTAT_STR_00%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "01")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_01%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "10")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_10%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "11")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_11%>" />
                         </c:if>
                          <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "12")}'>
                             <c:out value="<%=ConstantKey. BAAPPBASE_PROCSTAT_STR_12%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "13")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_13%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "20")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_20%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "30")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_30%>" />
                         </c:if>
                          <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "40")}'>
                             <c:out value="<%=ConstantKey. BAAPPBASE_PROCSTAT_STR_40%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "50")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_50%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "90")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_90%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.procStat eq "99")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_PROCSTAT_STR_99%>" />
                         </c:if>
                  </td>
                  <td id="iss" width="25%"><span class="issuetitle_L_down">資料別：</span>
                          <c:if test='${(CheckMarkLevelAdjustCase.caseTyp eq "1")}'>
                             <c:out value="<%=ConstantKey. BAAPPBASE_CASETYP_STR_1%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.caseTyp eq "2")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_2%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.caseTyp eq "3")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_3%>" />
                         </c:if>
                         <c:if test='${(CheckMarkLevelAdjustCase.caseTyp eq "4")}'>
                              <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_4%>" />
                         </c:if>
                  </td>
                                <td id="iss" colspan="1">
                                	<c:if test="${CheckMarkLevelAdjustCase.regetCipbMk eq '1'}">
                                    	<input type="checkbox" id="regetCipbMk" name="regetCipbMk" onclick="checkRegetCipbMk('<c:out value="${CheckMarkLevelAdjustCase.apNo}" />');" checked="true"><span class="issuetitle_L_down">重讀CIPB</span>
                                 	</c:if>
                                	<c:if test="${CheckMarkLevelAdjustCase.regetCipbMk ne '1'}">
                                    	<input type="checkbox" id="regetCipbMk" name="regetCipbMk" onclick="checkRegetCipbMk('<c:out value="${CheckMarkLevelAdjustCase.apNo}" />');"><span class="issuetitle_L_down">重讀CIPB</span>
                                 	</c:if>                                                               	                              
                                </td>
                                
                                <td id="iss" colspan="1">
                                   	<input type="checkbox" id="clearCloseDate" name="clearCloseDate" value="Y" ><span class="issuetitle_L_down">清空結案日期</span>
                                </td>
                </tr>
                <tr>
                  <td colspan="4"></td>
                </tr>                
              </table>
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          
           
           <bean:define id="Case" name="<%=ConstantKey.CHECK_MARK_LEVEL_ADJUST_CASE%>" />
           <bean:define id="list" property="detailList"  name="Case"/>
           <logic:notEmpty property="payYmList" name="Case">
           <tr>         
            <td>
              <table cellpadding="1" cellspacing="3" width="100%" class="px13">
                <tr>
                  <td colspan="7" class="issuetitle_L"><span class="issuetitle_search">&#9658;</span>&nbsp;編審註記：依給付年月排序</td>
                </tr>
                <tr align="center">
                  <td width="8%" class="issuetitle_L">受款人序</td>
                  <td width="10%" class="issuetitle_L">受款人姓名</td>

                  <td width="12%" class="issuetitle_L">受款人身分證號</td>
                  <td width="10%" class="issuetitle_L">關 係</td>
                  <td width="28%" class="issuetitle_L">編審註記</td>
                  <td width="12%" class="issuetitle_L">嚴重程度</td>
                  <td width="20%" class="issuetitle_L">程度調整</td>
                </tr>
                
                <logic:iterate id="detail" name="list" indexId="index">
                  <tr>
                    <td colspan="7" id="iss">&nbsp;給付年月：<bean:write name="Case" property="payYmList[${index}]"/></td>
                  </tr>
                <tr align="center">
                  <td colspan="7" align="center">
                  <bean:define id="data"   name="detail" type="java.util.List"/>
                    <display:table name="pageScope.data" id="listItem">  
                       <display:column  style="width:8%; text-align:center;" class="issueinfo_C">
                             <c:out value="${listItem.seqNo}" />&nbsp
                       </display:column>
                       <display:column  style="width:10%; text-align:center;" class="issueinfo_C">
                              <c:out value="${listItem.benName}" />&nbsp
                       </display:column>
                       <display:column  style="width:12%; text-align:center;" class="issueinfo_C">
                                <c:out value="${listItem.benIdnNo}" />&nbsp
                       </display:column>
                       <display:column  style="width:10%; text-align:center;" class="issueinfo_C">
                               <c:if test='${(listItem.benEvtRel eq "1")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "2")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "3")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "4")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "5")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "6")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "7")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "A")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "C")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "F")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "N")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "Z")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "O")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O%>" />
                               </c:if>&nbsp
                       </display:column>
                       <display:column  style="width:28%; text-align:center;" class="issueinfo_C">
                                  <c:out value="${listItem.chkCode}-" /><c:out value="${listItem.chkResult}" />&nbsp
                       </display:column>
                       <display:column  style="width:12%; text-align:center;" class="issueinfo_C">
                                    <c:if test='${(listItem.chkCodePre eq "O")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_O%>" />
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePre eq "W")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_W%>" />
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePre eq "E")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_E%>" />
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePre eq "G")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_G%>" />
                                    </c:if>&nbsp
                       </display:column>
                       <display:column  style="width:20%; text-align:center;" class="issueinfo_C">
                                  <c:if test='${(listItem.chkCodePre eq "O")}'>
                                     <c:if test='${(listItem.chkCodePost eq "W")}'>
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="W" checked="checked" onclick="changeToW('<c:out value="${listItem.chkCodeReplaceEscap}" />');"  type="radio">W-警告
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="O" onclick="changeToO('<c:out value="${listItem.chkCodeReplaceEscap}" />');" type="radio">O-可穿透                      
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePost eq "O")}'>
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="W"  onclick="changeToW('<c:out value="${listItem.chkCodeReplaceEscap}" />');"  type="radio">W-警告
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="O" checked="checked" onclick="changeToO('<c:out value="${listItem.chkCodeReplaceEscap}" />');" type="radio">O-可穿透                      
                                    </c:if>
                                    </c:if>&nbsp
                       </display:column>                      
                     </display:table>  
                    </td>
                </tr>           
                </logic:iterate>               
                </table>
            </td>
          </tr>
          <tr>
            <td><hr width="100%" size="1px" noshade></td>
          </tr>
          </logic:notEmpty>
          
          <logic:notEmpty property="seqnoList" name="Case">
           <tr>         
            <td>
              <table cellpadding="1" cellspacing="3" width="100%" class="px13">
                <tr>
                  <td colspan="7" class="issuetitle_L"><span class="issuetitle_search">&#9658;</span>&nbsp;編審註記：依受款人序排序</td>
                </tr>
                <tr align="center">
                   <td class="issuetitle_L" width="22%">給付年月</td>
                  <td class="issuetitle_L" width="34%">編審註記</td>
                  <td class="issuetitle_L" width="22%">嚴重程度</td>
                  <td class="issuetitle_L" width="22%">程度調整</td>
                </tr>
                
                <logic:iterate id="detail" name="list" indexId="index">
                  <tr>
                    <td colspan="4" id="iss">
                    <table class="px13" border="0" cellpadding="2" cellspacing="2" width="80%">
                      <tbody><tr>
                        <td>受款人序：<bean:write name="Case" property="seqnoList[${index}].seqNo"/></td>
                        <td>受款人姓名：<bean:write name="Case" property="seqnoList[${index}].benName"/></td>

                        <td>受款人身分證號：<bean:write name="Case" property="seqnoList[${index}].benIdnNo"/></td>
                        <bean:define id="benEvtRel" property="seqnoList[${index}].benEvtRel"  name="Case"/>
                        <td>關　係：
                               <c:if test='${(benEvtRel eq "1")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "2")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "3")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "4")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "5")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "6")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "7")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "A")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "C")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "F")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "N")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "Z")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z%>" />
                               </c:if>
                               <c:if test='${(benEvtRel eq "O")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O%>" />
                               </c:if>
                        </td>
                      </tr>
                    </tbody></table>
                  </td>
                  </tr>
                <tr align="center">
                  <td colspan="7" align="center">
                  <bean:define id="data1"   name="detail" type="java.util.List"/>
                    <display:table name="pageScope.data1" id="listItem">  
                       <display:column  style="width:22%; text-align:center;" class="issueinfo_C">
                             <c:out value="${listItem.payYm}" />&nbsp
                       </display:column>
                       <display:column  style="width:34%; text-align:center;" class="issueinfo_C">
                                  <c:out value="${listItem.chkCode}-" /><c:out value="${listItem.chkResult}" />&nbsp
                       </display:column>
                       <display:column  style="width:22%; text-align:center;" class="issueinfo_C">
                                    <c:if test='${(listItem.chkCodePre eq "O")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_O%>" />
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePre eq "W")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_W%>" />
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePre eq "E")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_E%>" />
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePre eq "G")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_G%>" />
                                    </c:if>&nbsp
                       </display:column>
                       <display:column  style="width:22%; text-align:center;" class="issueinfo_C">
                                  <c:if test='${(listItem.chkCodePre eq "O")}'>
                                     <c:if test='${(listItem.chkCodePost eq "W")}'>
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="W" checked="checked" onclick="changeToW('<c:out value="${listItem.chkCodeReplaceEscap}" />');"  type="radio">W-警告
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="O" onclick="changeToO('<c:out value="${listItem.chkCodeReplaceEscap}" />');" type="radio">O-可穿透                      
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePost eq "O")}'>
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="W"  onclick="changeToW('<c:out value="${listItem.chkCodeReplaceEscap}" />');"  type="radio">W-警告
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="O" checked="checked" onclick="changeToO('<c:out value="${listItem.chkCodeReplaceEscap}" />');" type="radio">O-可穿透                      
                                    </c:if>
                                    </c:if>&nbsp
                       </display:column>                      
                     </display:table>  
                    </td>
                </tr>           
                </logic:iterate>               
                </table>
            </td>
          </tr>
          <tr>
            <td><hr width="100%" size="1px" noshade></td>
          </tr>
          </logic:notEmpty>
          
          <logic:notEmpty property="markList" name="Case">
           <tr>         
            <td>
              <table cellpadding="1" cellspacing="3" width="100%" class="px13">
                <tr>
                  <td colspan="7" class="issuetitle_L"><span class="issuetitle_search">&#9658;</span>&nbsp;編審註記：依編審註記排序</td>
                </tr>
                <tr align="center">
                  <td class="issuetitle_L" width="14%">給付年月</td>
                  <td class="issuetitle_L" width="12%">受款人序</td>
                  <td class="issuetitle_L" width="14%">受款人姓名</td>
                  <td class="issuetitle_L" width="14%">受款人身分證號</td>
                  <td class="issuetitle_L" width="12%">關 係</td>
                  <td class="issuetitle_L" width="14%">嚴重程度</td>
                  <td class="issuetitle_L" width="20%">程度調整</td>
                </tr>
                
                <logic:iterate id="detail" name="list" indexId="index">
                  <tr>
                    <td colspan="7" id="iss">&nbsp;編審註記：<bean:write name="Case" property="markList[${index}]"/></td>
                  </tr>
                <tr align="center">
                  <td colspan="7" align="center">
                  <bean:define id="data2"   name="detail" type="java.util.List"/>
                    <display:table name="pageScope.data2" id="listItem">  
                       <display:column  style="width:14%; text-align:center;" class="issueinfo_C">
                             <c:out value="${listItem.payYm}" />&nbsp
                       </display:column>
                       <display:column  style="width:12%; text-align:center;" class="issueinfo_C">
                              <c:out value="${listItem.seqNo}" />&nbsp
                       </display:column>
                       <display:column  style="width:14%; text-align:center;" class="issueinfo_C">
                                <c:out value="${listItem.benName}" />&nbsp
                       </display:column>
                       <display:column  style="width:14%; text-align:center;" class="issueinfo_C">
                                <c:out value="${listItem.benIdnNo}" />&nbsp
                       </display:column>
                       <display:column  style="width:12%; text-align:center;" class="issueinfo_C">
                               <c:if test='${(listItem.benEvtRel eq "1")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "2")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "3")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "4")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "5")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "6")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "7")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "A")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "C")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "F")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "N")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "Z")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z%>" />
                               </c:if>
                               <c:if test='${(listItem.benEvtRel eq "O")}'>
                                     <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O%>" />
                               </c:if>&nbsp
                       </display:column>
                       <display:column  style="width:14%; text-align:center;" class="issueinfo_C">
                                    <c:if test='${(listItem.chkCodePre eq "O")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_O%>" />
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePre eq "W")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_W%>" />
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePre eq "E")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_E%>" />
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePre eq "G")}'>
                                        <c:out value="<%=ConstantKey.BACHKFILE_CHKCODE_G%>" />
                                    </c:if>&nbsp
                       </display:column>
                       <display:column  style="width:20%; text-align:center;" class="issueinfo_C">
                                  <c:if test='${(listItem.chkCodePre eq "O")}'>
                                     <c:if test='${(listItem.chkCodePost eq "W")}'>
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="W" checked="checked" onclick="changeToW('<c:out value="${listItem.chkCodeReplaceEscap}" />');"  type="radio">W-警告
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="O" onclick="changeToO('<c:out value="${listItem.chkCodeReplaceEscap}" />');" type="radio">O-可穿透                      
                                    </c:if>
                                    <c:if test='${(listItem.chkCodePost eq "O")}'>
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="W"  onclick="changeToW('<c:out value="${listItem.chkCodeReplaceEscap}" />');"  type="radio">W-警告
                                      <input name="<c:out value="${listItem.chkCodeReplaceEscap}" />-<c:out value="${listItem.baChkFileId}" />" value="O" checked="checked" onclick="changeToO('<c:out value="${listItem.chkCodeReplaceEscap}" />');" type="radio">O-可穿透                      
                                    </c:if>
                                    </c:if>&nbsp
                       </display:column>                      
                     </display:table>  
                    </td>
                </tr>           
                </logic:iterate>               
                </table>
            </td>
          </tr>
          <tr>
            <td><hr width="100%" size="1px" noshade></td>
          </tr>
          </logic:notEmpty>
                             
          </table>   
        </fieldset>
        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
