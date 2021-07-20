<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.query.forms.PaymentQueryForm" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D010Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/queryCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <html:javascript formName="PaymentQueryForm" page="1" />           
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){
        // 此頁面ajax必須先執行完成才能到後端執行 因此dwr設為同步
        dwr.engine.setAsync(false);     
        
        var qryCond = '<c:out value="${PaymentQueryForm.qryCond}" />';
        var startYm = '<c:out value="${PaymentQueryForm.startYm}" />';
        var endYm = '<c:out value="${PaymentQueryForm.endYm}" />';        
        if(qryCond == "ISSUYM"){
            $("qryCond1").checked=true;
            $("ajaxQryCond").value = "ISSUYM";
        }else if(qryCond == "PAYYM"){
            $("qryCond2").checked=true;
            $("ajaxQryCond").value = "PAYYM";
        }else{
            $("qryCond1").checked=true;
            $("ajaxQryCond").value = "ISSUYM";
        }
        
        if(startYm == "" && endYm == ""){
            $("startYm").value = '<%=DateUtility.calYear(DateUtility.getNowChineseDate(), -1).substring(0, 5)%>';
            $("endYm").value = '<%=DateUtility.getNowChineseDate().substring(0, 5)%>';
        }
    }    
    
    //chenge QryCond     
    function changeQryCond1(){
          $("ajaxQryCond").value = "ISSUYM";
    }       
    function changeQryCond2(){
          $("ajaxQryCond").value = "PAYYM";
    }   
    
    function checkFields(){

        var msg = "";
        
        if ($("qryCond1").checked==false && $("qryCond2").checked==false){
        	$("qryCond1").checked=true;        	
        }
        
        //if(Trim($("apNo1").value)=="" && Trim($("apNo2").value)=="" && Trim($("apNo3").value)=="" && Trim($("apNo4").value)=="" && Trim($("name").value)=="" && Trim($("idn").value)=="" && Trim($("brDate").value)=="" && Trim($("startYm").value)=="" && Trim($("endYm").value)==""){
        if(Trim($("apNo1").value)=="" && Trim($("apNo2").value)=="" && Trim($("apNo3").value)=="" && Trim($("apNo4").value)=="" && Trim($("name").value)=="" && Trim($("idn").value)=="" && Trim($("brDate").value)==""){
            msg+="請輸入查詢條件\r\n";
        }else{
            if(Trim($("apNo1").value)!="" || Trim($("apNo2").value)!="" || Trim($("apNo3").value)!="" || Trim($("apNo4").value)!=""){
                if(Trim($("apNo1").value)=="" || Trim($("apNo2").value)=="" || Trim($("apNo3").value)=="" || Trim($("apNo4").value)==""){
                    msg+="請輸入完整「受理編號」\r\n";
                    $("apNo1").focus();
                }        
            }
            
            if($("idn").value!=""){
            	var idn = $F("idn");
            	if (Trim(idn).length < 8){
            		msg+="「身分證號」不符規則\r\n";
            	}
            }
            
            if($("startYm").value!="" && $("endYm").value==""){
                $("endYm").value = $("startYm").value
            }
            if($("startYm").value=="" && $("endYm").value!=""){
                $("startYm").value = $("endYm").value
            }                
                                                                                                                       
            if($("startYm").value!="" &&  $("endYm").value!="" ){
                var startYm = $F("startYm");
                var endYm = $F("endYm");
                
                if (Trim(startYm).length > 0 && Trim(endYm).length > 0) {
                    // 起日 比 迄日 大
                    if (parseNumber(startYm) > parseNumber(endYm)) {
                        // 訊息: 「查詢起迄年月」範圍錯誤，請重新確認。
                        msg+='<bean:message bundle="<%=Global.BA_MSG%>" key="errors.date.range" arg0='<%=baResBundle.getMessage("label.query.paymentQuery.qryYm")%>' />\r\n';
                        $("startYm").focus();
                    }
                }        
            }
        } 
            
        if(msg!=""){
            alert(msg);
            return false;
        }else{
            return true;
        }                
    }
    
    // Ajax for 保密資料確認
    function checkSecrecy(){
        var apNo = $("apNo1").value+$("apNo2").value+$("apNo3").value+$("apNo4").value;
        var benName = $("name").value;
        var benIdnNo = $("idn").value;
        var benBrDate = $("brDate").value;
        var qryCond = $("ajaxQryCond").value;
        var startYm = $("startYm").value;
        var endYm = $("endYm").value;
    	queryCommonAjaxDo.checkSecrecy(apNo,benIdnNo,benName,benBrDate,qryCond,startYm,endYm,checkSecrecyResult);
    }
    
    function checkSecrecyResult(checkResult){
        
    	$("checkResult").value = checkResult;
        if(Trim($("checkResult").value) == "Y"){  
            alert("保密案件，請勿告知辦理情形!");
        }  	
    }
    
    <%-- 畫面重設 --%>
    function resetForm(){
        //document.OldAgeAnnuityReceiptQueryForm.reset();
        cleanForm();
        initAllForClean();
    }
    
    function initAllForClean(){
        $("qryCond1").checked=true;
    }
    
    Event.observe(window, 'load', initAll, false);
    //-->    
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/paymentQuery" method="post" onsubmit="return validatePaymentQueryForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <input type="hidden" id="checkResult" name="checkResult" value="">
        <input type="hidden" id="ajaxQryCond" name="ajaxQryCond" value="">
        
        <fieldset>
            <legend>&nbsp;給付查詢&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnQuery">
                            <input tabindex="60" type="button" name="btnQuery" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doQuery'; if(document.PaymentQueryForm.onsubmit() && checkFields()){checkSecrecy(); document.PaymentQueryForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>                        
                        <acl:checkButton buttonName="btnReset">
                            <input tabindex="65" type="button" name="btnReset" class="button" value="清　除" onclick="resetForm()"/>
                        </acl:checkButton>                        
                    </td>
                </tr> 
                <tr>
                    <td width="30%" align="right" class="issuetitle_R_down">受理編號：</td>        
                    <td>
                        <html:text tabindex="5" property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'))" />
                        &nbsp;-
                        <html:text tabindex="10" property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2'), $('apNo3'))"/>
                        &nbsp;-
                        <html:text tabindex="15" property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3'), $('apNo4'))" />
                        &nbsp;-
                        <html:text tabindex="20" property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)"/>
                    </td>
                </tr>        
                <tr>
                    <td align="right" class="issuetitle_R_down">姓　　名：</td>
                    <td>
                        <html:text tabindex="25" property="name" styleId="name" styleClass="textinput" size="15" maxlength="50" onblur="this.value=asc(this.value);"/>
                    </td>                           
                </tr>
                <tr>
                    <td align="right" class="issuetitle_R_down">身分證號：</td>
                    <td>
                        <html:text tabindex="30" property="idn" styleId="idn" styleClass="textinput" size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();"/>
                    </td>        
                </tr>
                <tr>
                    <td align="right" class="issuetitle_R_down">出生日期：</td>
                    <td>
                        <html:text tabindex="35" property="brDate" styleId="brDate" styleClass="textinput" size="10" maxlength="8" onblur="this.value=asc(this.value);"/>
                    </td>
                </tr>
                <tr>        
                    <td align="right"><span class="issuetitle_R_down">查詢條件：</span></td>
                    <td class="formtxt">
                        <html:radio tabindex="40" styleId="qryCond1" property="qryCond" value="ISSUYM" onchange="changeQryCond1();"/>核定年月                                
                        <html:radio tabindex="45" styleId="qryCond2" property="qryCond" value="PAYYM" onchange="changeQryCond2();"/>給付年月
                    </td>        
                </tr>
                <tr>
                    <td align="right"><span class="issuetitle_R_down">起迄年月：</span></td>
                    <td>
                        <html:text tabindex="50" property="startYm" styleId="startYm" styleClass="textinput" size="10" maxlength="5" onblur="this.value=asc(this.value);" onkeyup="autotab($('startYm'), $('endYm'))"/>&nbsp;~&nbsp;
                        <html:text tabindex="55" property="endYm" styleId="endYm" styleClass="textinput" size="10" maxlength="5" onblur="this.value=asc(this.value);" onkeyup="autotab($('endYm'), $('btnQuery'))"/>
                    </td>        
                </tr>        
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2"><hr size="1" noshade>
                    <span class="titleinput">※注意事項：</span><br>
                       　1.&nbsp;上列欄位需擇一輸入資料，方能進行查詢。<br>
                       　2.&nbsp;外籍人士請於身分證號欄位填寫護照號碼。<br>
                       　3.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
                       　4.&nbsp;起迄年月欄位預設帶入系統年月減一年至系統年月的資料；欲查詢其他區間年月的資料時，請直接修改起迄年月欄位。
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
