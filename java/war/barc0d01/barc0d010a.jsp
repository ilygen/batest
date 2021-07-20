<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.decision.forms.PaymentDecisionForm" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BARC0D010A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>   
    <html:javascript formName="PaymentDecisionForm" page="1" />           
    <script language="javascript" type="text/javascript">
    <!--                    
    //頁面欄位值初始化       
    function initAll(){     
        $("apNo1").disabled = true;
        $("apNo2").disabled = true;
        $("apNo3").disabled = true;
        $("apNo4").disabled = true;
        $("chkMan").disabled = true;
        $("rptDate").disabled = true;
        $("pageNo").disabled = true;
            
        if($("qryCond1").checked==true){
            $('cond1').style.display='inline';
            $('cond2').style.display='none';
            $("apNo1").disabled = false;
            $("apNo2").disabled = false;
            $("apNo3").disabled = false;
            $("apNo4").disabled = false;
        }else if($("qryCond2").checked==true){
            $('cond1').style.display='none';
            $('cond2').style.display='inline';
            $("chkMan").disabled = false;
            $("rptDate").disabled = false;
            $("pageNo").disabled = false;
        }
        <%if(request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_FORM) != null){%>
            var qryCond = '<c:out value ="<%=((PaymentDecisionForm)request.getSession().getAttribute(ConstantKey.PAYMENT_DECISION_FORM)).getQryCond()%>" />';
            if(qryCond=="2"){
                $("qryCond2").checked=true;
                $('cond1').style.display='none';
                $('cond2').style.display='inline';
                $("chkMan").disabled = false;
                $("rptDate").disabled = false;
                $("pageNo").disabled = false;
                $("apNo1").value = "";
                $("apNo2").value = "";
                $("apNo3").value = "";
                $("apNo4").value = "";
            }else{
                $("qryCond1").checked=true;
                $('cond1').style.display='inline';
                $('cond2').style.display='none';
                $("apNo1").disabled = false;
                $("apNo2").disabled = false;
                $("apNo3").disabled = false;
                $("apNo4").disabled = false;
                $("chkMan").value = "";
                $("rptDate").value = "";
                $("pageNo").value = "";
            }
        <%}else{%>
            $("qryCond2").checked=true;
            $('cond1').style.display='none';
            $('cond2').style.display='inline';
            $("chkMan").disabled = false;
            $("rptDate").disabled = false;
            $("pageNo").disabled = false;
            $("apNo1").value = "";
            $("apNo2").value = "";
            $("apNo3").value = "";
            $("apNo4").value = "";            
        <%}%>
    }    
       
    //變更查詢條件
    function changeQryCondition(){
        if($("qryCond1").checked==true){
            $('cond1').style.display='inline';
            $('cond2').style.display='none';
            $("apNo1").disabled = false;
            $("apNo2").disabled = false;
            $("apNo3").disabled = false;
            $("apNo4").disabled = false;
            $("chkMan").value = "";
            $("rptDate").value = "";
            $("pageNo").value = "";
            $("chkMan").disabled = true;
            $("rptDate").disabled = true;
            $("pageNo").disabled = true;            
        }
        if($("qryCond2").checked==true){
            $('cond1').style.display='none';
            $('cond2').style.display='inline';
            $("apNo1").value = "";
            $("apNo2").value = "";
            $("apNo3").value = "";
            $("apNo4").value = "";
            $("apNo1").disabled = true;
            $("apNo2").disabled = true;
            $("apNo3").disabled = true;
            $("apNo4").disabled = true;
            $("chkMan").disabled = false;
            $("rptDate").disabled = false;
            $("pageNo").disabled = false;        
        }
    }
    
    function checkFields(){
        if($("qryCond1").checked==true){
            if(Trim($F("apNo1")).length == 0||Trim($F("apNo2")).length == 0||Trim($F("apNo3")).length == 0||Trim($F("apNo4")).length == 0){
                alert("請輸入受理編號");
                return false;        
            }
        }else if($("qryCond2").checked==true){
            if(Trim($F("chkMan")).length == 0 || Trim($F("rptDate")).length == 0||Trim($F("pageNo")).length == 0){
                alert("請輸入審核人員 + 列印日期 + 頁次");
                return false;        
            }
        }else{
            alert("請輸入查詢條件");
            return false;
        }
        return true;
    }
    
    function cleanAll(){
        $("qryCond2").checked=true;
        $('cond1').style.display='none';
        $('cond2').style.display='inline';
        $("apNo1").disabled = true;
        $("apNo2").disabled = true;
        $("apNo3").disabled = true;
        $("apNo4").disabled = true;
        $("chkMan").disabled = false;
        $("rptDate").disabled = false;
        $("pageNo").disabled = false;
        $("apNo1").value = "";
        $("apNo2").value = "";
        $("apNo3").value = "";
        $("apNo4").value = "";
        $("rptDate").value = "";
        $("pageNo").value = "";
        $("chkMan").value = "";
        $("chkMan").focus();
    }
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/paymentDecision" method="post" onsubmit="return validatePaymentDecisionForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;老年年金給付決行作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" tabindex="70" name="btnQuery" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doQuery'; if(checkFields()&&document.PaymentDecisionForm.onsubmit()){document.PaymentDecisionForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input type="button" tabindex="80" name="btnClear" class="button" value="清　除" onclick="cleanAll();"/>
                        </acl:checkButton>                        
                    </td>
                </tr>    
                <tr>
                    <td width="20%" align="right">
                        <html:radio styleId="qryCond1" property="qryCond" value="1" onclick="changeQryCondition();" />
                    </td>
                    <td class="formtxt" width="80%">
                        <span class="issuetitle_L_down">受理編號</span>
                        <div id="cond1" style="display: none">：
                            <html:text tabindex="10" property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'))" />
                            &nbsp;-
                            <html:text tabindex="20" property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2'), $('apNo3'))"/>
                            &nbsp;-
                            <html:text tabindex="30" property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3'), $('apNo4'))" />
                            &nbsp;-
                            <html:text tabindex="40" property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo4'), $('btnQuery'))" />
                        </div>
                    </td>                                                                                
                </tr>
                <tr>
                    <td width="20%" align="right">
                        <html:radio styleId="qryCond2" property="qryCond" value="2" onclick="changeQryCondition();" />
                    </td>
                    <td class="formtxt" width="80%">
                        <span class="issuetitle_L_down">審核人員&nbsp;+&nbsp;列印日期&nbsp;+&nbsp;頁次</span>
                        <div id="cond2" style="display: none">：
                        	<html:text tabindex="50" property="chkMan" styleId="chkMan" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('chkMan'), $('rptDate'));" />(請輸入員工編號)&nbsp;+
                            <html:text tabindex="60" property="rptDate" styleId="rptDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value)" onkeyup="autotab($('rptDate'), $('pageNo'));" />&nbsp;+
                            <html:text tabindex="70" property="pageNo" styleId="pageNo" styleClass="textinput" size="3" maxlength="3" onblur="this.value=asc(this.value)" onkeyup="autotab($('pageNo'), $('btnQuery'))"/>
                        </div>
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
