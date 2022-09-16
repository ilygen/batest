<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.receipt.forms.DisabledAnnuityReceiptForm" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAAP0D020A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>      
    <html:javascript formName="DisabledAnnuityReceiptQueryForm" page="1" />        
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){  
        <%if(request.getSession().getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QUERY_FORM) == null){%>     
            $("apNo1").value='K';
            disableInsertButton(false);
        <%}else{%>
            disableInsertButton(false);
            var apNo1 = '<c:out value ="<%=((DisabledAnnuityReceiptForm)request.getSession().getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QUERY_FORM)).getApNo1()%>" />'     
            var apNo2 = '<c:out value ="<%=((DisabledAnnuityReceiptForm)request.getSession().getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QUERY_FORM)).getApNo2()%>" />'     
            var apNo3 = '<c:out value ="<%=((DisabledAnnuityReceiptForm)request.getSession().getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QUERY_FORM)).getApNo3()%>" />'     
            var apNo4 = '<c:out value ="<%=((DisabledAnnuityReceiptForm)request.getSession().getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QUERY_FORM)).getApNo4()%>" />'     
            var evtIdnNo = '<c:out value ="<%=((DisabledAnnuityReceiptForm)request.getSession().getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QUERY_FORM)).getEvtIdnNo()%>" />'     
            if(apNo1==""||apNo1=="null"){
                $("apNo1").value='K';
            }else{
                $("apNo1").value=apNo1;
            }            
            if((apNo2!=""&&apNo2!="null")||(apNo3!=""&&apNo3!="null")||(apNo4!=""&&apNo4!="null")||(evtIdnNo!=""&&evtIdnNo!="null")){
                disableInsertButton(true);
            }
        <%}%>             
        $('apNo2').focus();
        
        if($("origSFlag36").value == "Y"){
            $("sFlag36").checked = true;
        }else{
            $("sFlag36").checked = false;
        }
        
        if($("origSFlag36").value != "Y"){
            $("apNo1For36").disabled = true;
            $("apNo2For36").disabled = true;
            $("apNo3For36").disabled = true;
            $("apNo4For36").disabled = true;
            $("apNo1For36").value = "";
            $("apNo2For36").value = "";
            $("apNo3For36").value = "";
            $("apNo4For36").value = "";
            $("btnModify36").disabled = true;
            $('apNo2').focus();
        }else{
            $("evtIdnNo").disabled = true;
            $("apNo1").disabled = true;
            $("apNo2").disabled = true;
            $("apNo3").disabled = true;
            $("apNo4").disabled = true;
            $("evtIdnNo").value = "";
            $("apNo1").value = "";
            $("apNo2").value = "";
            $("apNo3").value = "";
            $("apNo4").value = "";
            $("btnInsert").disabled = true;
            $("btnModify").disabled = true;
            $('apNo2For36').focus();
        }
    }
    
    <%-- 檢查是否輸入資料 --%>
    <%-- 有: Disable 新增 按鍵 --%>
    <%-- 無: Enable 新增 按鍵 --%>
    <%-- begin ... [ --%>
    function doInputChange() {
        var bClicked = false;
        var apNo2 = $("apNo2").value;
        var apNo3 = $("apNo3").value;
        var apNo4 = $("apNo4").value;
        var evtIdnNo = $("evtIdnNo").value;
        if (apNo2!="" || apNo3!="" || apNo4!="" || evtIdnNo!="") {
            bClicked = true;
        }
        disableInsertButton(bClicked);
    }
    <%-- ] ... end --%>

    function disableInsertButton(isDisabled) {
        $("btnInsert").disabled = isDisabled;
    }    
    
    function doCheckBoxChange() {
        if($("sFlag36").checked == true){
            $("apNo1For36").disabled = false;
            $("apNo2For36").disabled = false;
            $("apNo3For36").disabled = false;
            $("apNo4For36").disabled = false;
            $("evtIdnNo").disabled = true;
            $("apNo1").disabled = true;
            $("apNo2").disabled = true;
            $("apNo3").disabled = true;
            $("apNo4").disabled = true;
            $("evtIdnNo").value = "";
            $("apNo1").value = "K";
            $("apNo2").value = "";
            $("apNo3").value = "";
            $("apNo4").value = "";
            $("btnInsert").disabled = true;
            $("btnModify").disabled = true;
            $("btnModify36").disabled = false;
            $('apNo1For36').focus();
        }else{
            $("evtIdnNo").disabled = false;
            $("apNo1").disabled = false;
            $("apNo2").disabled = false;
            $("apNo3").disabled = false;
            $("apNo4").disabled = false;
            $("apNo1For36").disabled = true;
            $("apNo2For36").disabled = true;
            $("apNo3For36").disabled = true;
            $("apNo4For36").disabled = true;
            $("apNo1For36").value = "";
            $("apNo2For36").value = "";
            $("apNo3For36").value = "";
            $("apNo4For36").value = "";
            $("apNo1").value = "K";
            $("btnInsert").disabled = false;
            $("btnModify").disabled = false;
            $("btnModify36").disabled = true;
            $('apNo2').focus();
        }
    }

    function checkFields(){
        var evtIdnNo = Trim($("evtIdnNo").value);
        var apNo = Trim($("apNo1").value) + Trim($("apNo2").value) + Trim($("apNo3").value) + Trim($("apNo4").value);                               
        if(evtIdnNo == "" && apNo.length != <%=ConstantKey.APNO_LENGTH%>){
            alert("請輸入查詢條件");
            $("apNo1").focus();
            return false;        
        }
        return true;
    }
    
    <%-- 畫面重設 --%>
    function resetForm(){
        //document.OldAgeAnnuityReceiptQueryForm.reset();
        cleanForm();
        initAllForClean();
    }
    
    function initAllForClean(){
        $("apNo1").value='K';
        $("apNo2").focus=true;
        disableInsertButton(false);
    }
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" onload="Event.stopObserving(window, 'load', inputFieldFocus);">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/disabledAnnuityReceipt" method="post" onsubmit="return validateDisabledAnnuityReceiptQueryForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="origSFlag36" property="origSFlag36"/>
        
        <fieldset>
            <legend>&nbsp;失能年金批次受理作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnInsert">
                            <input type="button" id="btnInsert" name="btnInsert" tabindex="60" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='prepareInsert'; document.DisabledAnnuityReceiptQueryForm.submit();" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnModify">
                            <input type="button" id="btnModify" name="btnModify" tabindex="70" class="button" value="修　改" onclick="$('page').value='1'; $('method').value='prepareModify'; if (document.DisabledAnnuityReceiptQueryForm.onsubmit() && checkFields()){document.DisabledAnnuityReceiptQueryForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <%--
                        <acl:checkButton buttonName="btnModify36">
                            <input type="button" id="btnModify36" name="btnModify36" tabindex="80" class="button" value="國併勞" onclick="$('page').value='1'; $('method').value='prepareInsert36Data'; document.DisabledAnnuityReceiptQueryForm.submit();" />&nbsp;
                        </acl:checkButton>
                        --%>
                        <acl:checkButton buttonName="btnClear">
                            <input type="button" id="btnClear" name="btnClear" tabindex="90" class="button" value="清　除" onclick="resetForm();"/>
                        </acl:checkButton>                        
                    </td>
                </tr> 
                <tr>
                    <td width="30%" align="right" class="issuetitle_R_down">事故者身分證號：</td>
                    <td>
                        <html:text property="evtIdnNo" styleId="evtIdnNo" tabindex="10" styleClass="textinput" size="15" maxlength="10" onkeyup="doInputChange();" onmouseout="doInputChange();" onblur="this.value=asc(this.value).toUpperCase();"/>
                    </td>
                </tr>   
                <tr>
                    <td align="right" class="issuetitle_R_down">受理編號：</td>
                    <td>
                        <html:text property="apNo1" styleId="apNo1" tabindex="20" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'))"/>
                        &nbsp;-
                        <html:text property="apNo2" styleId="apNo2" tabindex="30" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2'), $('apNo3'));doInputChange();" onmouseout="doInputChange();"/>
                        &nbsp;-
                        <html:text property="apNo3" styleId="apNo3" tabindex="40" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3'), $('apNo4'));doInputChange();" onmouseout="doInputChange();"/>
                        &nbsp;-
                        <html:text property="apNo4" styleId="apNo4" tabindex="50" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo4'), $('btnModify'));doInputChange();" onmouseout="doInputChange();"/>
                    </td>
                </tr>
                <%--
                <tr>
                    <td align="right" class="issuetitle_R_down"><input type="checkbox" id="sFlag36" name="sFlag36" onclick="doCheckBoxChange();" value="Y" >國保受理編號：</td>
                    <td>
                        <html:text property="apNo1For36" styleId="apNo1For36" tabindex="20" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1For36'), $('apNo2For36'))"/>
                        &nbsp;-
                        <html:text property="apNo2For36" styleId="apNo2For36" tabindex="30" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2For36'), $('apNo3For36'));" />
                        &nbsp;-
                        <html:text property="apNo3For36" styleId="apNo3For36" tabindex="40" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3For36'), $('apNo4For36'));" />
                        &nbsp;-
                        <html:text property="apNo4For36" styleId="apNo4For36" tabindex="50" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo4For36'), $('btnModify36'));" />
                    </td>
                </tr>
                --%>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>                                                
            </table>
        </fieldset>
        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>