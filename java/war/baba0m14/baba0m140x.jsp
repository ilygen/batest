<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.bj.forms.OldMediaBatchForm" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M140X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script> 
    <script type='text/javascript' src='<c:url value="/dwr/interface/bjCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>  
    <html:javascript formName="OldMediaBatchForm" page="1" />           
    <script language="javascript" type="text/javascript">
    // Ajax for 取得 檢查輸入核定年月與核定日期
    function chkYmDate() {
        bjCommonAjaxDo.getCheckChkdate("L", $("issuYm").value, $("chkDate").value, chkYmDateResult);
    }
    
    function chkYmDateResult(checkResult){   
        $("checkResult").value = checkResult;  
        checkDate();
    }
    function checkDate(){
    	var checkDate = $("checkResult").value;
    	var chkDate = $("chkDate").value;
    	var issuYm = $("issuYm").value;
    	if(chkDate.length == 7 && issuYm.length ==5 && $('issuTyp').value=='1'){
    		if(checkDate == 'false'){
	    		//暫存檔中有資料
	    		document.getElementById("btnProMedia").disabled = false;
	    		document.getElementById("btnDownMedia").disabled = false;
	    		document.getElementById("btnProcQuery").disabled = false;
	    		
	    	}else{
	    		//暫存檔中無資料
	    		alert("欲執行核定年月："+$("issuYm").value+"及核定日期："+$("chkDate").value+"，請執行線上產製媒體檔作業。");
	    		$("chkDate").value = "";
	    		$("chkDate").focus();
	    	}
    	}
    }
    function cleanDate(){
    	$("issuYm").value = "";
   		$("chkDate").value = "";
    }
    function reTry(){
    	document.getElementById("btnProMedia").disabled = false;
    	document.getElementById("btnDownMedia").disabled = false;
    	document.getElementById("btnProcQuery").disabled = false;
    }
    Date.prototype.Format = function (fmt) { //author: meizz 
        var o = {
            "M+": this.getMonth() + 1, //月份 
            "d+": this.getDate(), //日 
            "h+": this.getHours(), //小时 
            "m+": this.getMinutes(), //分 
            "s+": this.getSeconds(), //秒 
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
            "S": this.getMilliseconds() //毫秒 
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }           

    function defaultDate(){
    	var now = new Date().Format("yyyyMMdd");
    	var chkDateStr = changeDateType(now);
        $("issuYm").value = "";
        $("chkDate").value = chkDateStr;
    }

    //頁面欄位值初始化       
    function initAll(){        
    	defaultDate();
    }    
    <%-- 畫面重設 --%>
    function cleanAll(){
         $("issuYm").value = "";
         $("chkDate").value = "";
         
    }
    
    
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/prodOldMediaBatch" method="post" onsubmit="return validateOldMediaBatchForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <input type="hidden" id="checkResult" name="checkResult" value="">
        <fieldset>
            <legend>&nbsp;老年年金批次產製媒體檔作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnProMedia">
                            <input type="button" name="btnProMedia" class="button_120" value="產製媒體檔" onclick="$('page').value='1'; $('method').value='doProduce'; if(document.OldMediaBatchForm.onsubmit() ){document.OldMediaBatchForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnDownMedia">
                            <input type="button" name="btnDownMedia" class="button_120" value="媒體檔下載" onclick="$('page').value='1'; $('method').value='doDownloadFile'; if(document.OldMediaBatchForm.onsubmit() ){document.OldMediaBatchForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>    
                         <acl:checkButton buttonName="btnProcQuery">
                            <input type="button" name="btnProcQuery" class="button_120" value="進度查詢" onclick="$('page').value='1'; $('method').value='doQueryProcess'; if(document.OldMediaBatchForm.onsubmit() ){document.OldMediaBatchForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>                                       
                        <acl:checkButton buttonName="btnCleanAll">
                            <input type="button" name="btnCleanAll" class="button" value="清　除"  onclick="cleanAll();"/>
                        </acl:checkButton> 
                                             
                    </td>
                </tr>
                 <tr>
                   <td  width="15%" align="center"/>
                   <td  width="30%" align="left" class="issuetitle_L_down"> <span class="needtxt">＊</span>核付處理類別： 
                        <html:select property="issuTyp" onchange="defaultDate()" >
                            <html:option value="">請選擇</html:option>
                            <html:option value="1">月核付</html:option>
                            <html:option value="2">第一次改匯核付</html:option>
                            <html:option value="3">第二次改匯核付</html:option>
                         </html:select>
                     </td>
                </tr>
                <tr>
                	 <td  width="15%" align="center"/>
                	 <td  width="30%" align="left" class="issuetitle_L_down"> <span class="needtxt">＊</span>核定年月： 
                	    <html:text property="issuYm" styleId="issuYm" styleClass="textinput" size="5" maxlength="5"  onkeyup="autotab($('issuYm'),$('chkDate'))"/>
                    	<span class="formtxt">(如民國98年1月，請輸入09801)</span>
                    </td>        
                </tr>      
                <tr>
                	<td  width="15%" align="center"/>
                	<td  width="30%" align="left"  class="issuetitle_L_down">
                     <span class="needtxt">＊</span>核定日期： 
                        <html:text property="chkDate" styleId="chkDate" styleClass="textinput" size="7" maxlength="7" onkeyup="autotab($('chkDate'),$('btnProMedia'));" onkeydown="reTry();" onblur="chkYmDate();" />
                    	<span class="formtxt">(如民國98年1月1日，請輸入0980101)</span>
                    </td>        
                </tr>               
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2"><hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>
                           　&nbsp;<span class="needtxt">＊</span>為必填欄位。
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
