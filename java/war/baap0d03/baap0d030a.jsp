<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.receipt.forms.SurvivorAnnuityReceiptForm" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAAP0D030A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>      
    <html:javascript formName="SurvivorAnnuityReceiptQueryForm" page="1" />        
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){          
        <%if(request.getSession().getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QUERY_FORM) == null){%>     
            $("apNo1").value='S';
            disableInsertButton(false);
        <%}else{%>
            disableInsertButton(false);
            var apNo1 = '<c:out value ="<%=((SurvivorAnnuityReceiptForm)request.getSession().getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QUERY_FORM)).getApNo1()%>" />'     
            var apNo2 = '<c:out value ="<%=((SurvivorAnnuityReceiptForm)request.getSession().getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QUERY_FORM)).getApNo2()%>" />'     
            var apNo3 = '<c:out value ="<%=((SurvivorAnnuityReceiptForm)request.getSession().getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QUERY_FORM)).getApNo3()%>" />'     
            var apNo4 = '<c:out value ="<%=((SurvivorAnnuityReceiptForm)request.getSession().getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QUERY_FORM)).getApNo4()%>" />'     
            var evtIdnNo = '<c:out value ="<%=((SurvivorAnnuityReceiptForm)request.getSession().getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QUERY_FORM)).getEvtIdnNo()%>" />'     
            if(apNo1==""||apNo1=="null"){
                $("apNo1").value='S';
            }else{
                $("apNo1").value=apNo1;
            }
            if((apNo2!=""&&apNo2!="null")||(apNo3!=""&&apNo3!="null")||(apNo4!=""&&apNo4!="null")||(evtIdnNo!=""&&evtIdnNo!="null")){
                disableInsertButton(true);
            }
        <%}%>             
        $('apNo2').focus();
    }
    
    <%-- 檢查是否輸入資料 --%>
    <%-- 有: Disable 新增 按鍵 --%>
    <%-- 無: Enable 新增 按鍵 --%>
    <%-- begin ... [ --%>
    function doInputChange() {
        var bClicked = false;
        //var apNo1 = $("apNo1").value;
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
        cleanForm();
        initAllForClean();
    }
    
    function initAllForClean(){
        $("apNo1").value='S';
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
        <html:form action="/survivorAnnuityReceiptQuery" method="post" onsubmit="return validateSurvivorAnnuityReceiptQueryForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;遺屬年金給付受理作業 &nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnInsert">
                            <input type="button" id="btnInsert" name="btnInsert" tabindex="60" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='prepareInsert'; document.SurvivorAnnuityReceiptQueryForm.submit();" disabled="true"/>&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnModify">
                            <input type="button" id="btnModify" name="btnModify" tabindex="70" class="button" value="修　改" onclick="$('page').value='1'; $('method').value='prepareModify'; if (document.SurvivorAnnuityReceiptQueryForm.onsubmit() && checkFields()){document.SurvivorAnnuityReceiptQueryForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input type="button" id="btnClear" name="btnClear" tabindex="80" class="button" value="清　除" onclick="resetForm();"/>
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
                        <html:text property="apNo1" styleId="apNo1" tabindex="20" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'));doInputChange();" onmouseout="doInputChange();"/>
                        &nbsp;-
                        <html:text property="apNo2" styleId="apNo2" tabindex="30" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2'), $('apNo3'));doInputChange();" onmouseout="doInputChange();"/>
                        &nbsp;-
                        <html:text property="apNo3" styleId="apNo3" tabindex="40" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3'), $('apNo4'));doInputChange();" onmouseout="doInputChange();"/>
                        &nbsp;-
                        <html:text property="apNo4" styleId="apNo4" tabindex="50" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo4'), $('btnModify'));doInputChange();" onmouseout="doInputChange();"/>
                    </td>
                </tr>
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
