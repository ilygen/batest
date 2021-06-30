<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.bj.forms.MonthCheckSForm" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M100X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>  
    <html:javascript formName="MonthCheckSForm" page="1" />      
    <script language="javascript" type="text/javascript">
    <!--                    
    //頁面欄位值初始化       
    function initAll(){
    
    }    
       
   
    function checkFields(){
        
            if(Trim($F("apNo1")).length == 0||Trim($F("apNo2")).length == 0||Trim($F("apNo3")).length == 0||Trim($F("apNo4")).length == 0){
                alert("請輸入受理編號");
                return false;        
            }
        return true;
    }
    
    function cleanAll(){
        $("apNo1").value = "";
        $("apNo2").value = "";
        $("apNo3").value = "";
        $("apNo4").value = "";
        $("apNoA1").value = "";
        $("apNoA2").value = "";
        $("apNoA3").value = "";
        $("apNoA4").value = "";
        $("apNoB1").value = "";
        $("apNoB2").value = "";
        $("apNoB3").value = "";
        $("apNoB4").value = "";
        $("apNoC1").value = "";
        $("apNoC2").value = "";
        $("apNoC3").value = "";
        $("apNoC4").value = "";
        $("apNoD1").value = "";
        $("apNoD2").value = "";
        $("apNoD3").value = "";
        $("apNoD4").value = "";
        $("apNoE1").value = "";
        $("apNoE2").value = "";
        $("apNoE3").value = "";
        $("apNoE4").value = "";
        $("apNoF1").value = "";
        $("apNoF2").value = "";
        $("apNoF3").value = "";
        $("apNoF4").value = "";
        $("apNoG1").value = "";
        $("apNoG2").value = "";
        $("apNoG3").value = "";
        $("apNoG4").value = "";
        $("apNoH1").value = "";
        $("apNoH2").value = "";
        $("apNoH3").value = "";
        $("apNoH4").value = "";
        $("apNoI1").value = "";
        $("apNoI2").value = "";
        $("apNoI3").value = "";
        $("apNoI4").value = "";
        $("apNo1").focus();
    }
    Event.observe(window, 'load', initAll, false);
    --></script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/monthCheckS" method="post" onsubmit="return validateMonthCheckSForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;遺屬年金線上月試核作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" tabindex="500" name="btnQuery" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doQuery'; if(checkFields()&&document.MonthCheckSForm.onsubmit()){document.MonthCheckSForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnQueryList">
                            <input type="button" tabindex="510" name="btnQueryList" class="button" value="查  詢" onclick="$('page').value='1'; $('method').value='doQueryList'; if (document.MonthCheckSForm.onsubmit()){document.MonthCheckSForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnClear">
                            <input type="button" tabindex="520" name="btnClear" class="button" value="清　除" onclick="cleanAll();"/>
                        </acl:checkButton>                        
                    </td>
                </tr>    
                <tr>
                    <td width="20%" align="right">
                    </td>
                    <td class="formtxt" width="100%">
                        <span class="issuetitle_L_down">受理編號</span>：
                            <html:text tabindex="10" property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'))" />
                            &nbsp;-
                            <html:text tabindex="20" property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2'), $('apNo3'))"/>
                            &nbsp;-
                            <html:text tabindex="30" property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3'), $('apNo4'))" />
                            &nbsp;-
                            <html:text tabindex="40" property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo4'), $('apNoA1'))"/>
                    </td>
                   </tr>    
                <tr>
                    <td width="20%" align="right">
                    </td>
                     <td class="formtxt" width="100%">
                        <span class="issuetitle_L_down">受理編號</span>：
                            <html:text tabindex="50" property="apNoA1" styleId="apNoA1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNoA1'), $('apNoA2'))" />
                            &nbsp;-
                            <html:text tabindex="60" property="apNoA2" styleId="apNoA2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoA2'), $('apNoA3'))"/>
                            &nbsp;-
                            <html:text tabindex="70" property="apNoA3" styleId="apNoA3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoA3'), $('apNoA4'))" />
                            &nbsp;-
                            <html:text tabindex="80" property="apNoA4" styleId="apNoA4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoA4'), $('apNoB1'))"/>
                    </td>   
                    </tr>    
                <tr>      
                    <td width="20%" align="right">
                    </td>
                     <td class="formtxt" width="100%">
                        <span class="issuetitle_L_down">受理編號</span>：
                            <html:text tabindex="90" property="apNoB1" styleId="apNoB1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNoB1'), $('apNoB2'))" />
                            &nbsp;-
                            <html:text tabindex="100" property="apNoB2" styleId="apNoB2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoB2'), $('apNoB3'))"/>
                            &nbsp;-
                            <html:text tabindex="110" property="apNoB3" styleId="apNoB3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoB3'), $('apNoB4'))" />
                            &nbsp;-
                            <html:text tabindex="120" property="apNoB4" styleId="apNoB4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoB4'), $('apNoC1'))"/>
                    </td>     
                    </tr>    
                <tr>    
                     <td width="20%" align="right">
                     </td>
                     <td class="formtxt" width="100%">
                        <span class="issuetitle_L_down">受理編號</span>：
                            <html:text tabindex="130" property="apNoC1" styleId="apNoC1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNoC1'), $('apNoC2'))" />
                            &nbsp;-
                            <html:text tabindex="140" property="apNoC2" styleId="apNoC2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoC2'), $('apNoC3'))"/>
                            &nbsp;-
                            <html:text tabindex="150" property="apNoC3" styleId="apNoC3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoC3'), $('apNoC4'))" />
                            &nbsp;-
                            <html:text tabindex="160" property="apNoC4" styleId="apNoC4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoC4'), $('apNoD1'))"/>
                    </td>    
                    </tr>    
                <tr>     
                     <td width="20%" align="right">
                     </td>
                     <td class="formtxt" width="100%">
                        <span class="issuetitle_L_down">受理編號</span>：
                            <html:text tabindex="170" property="apNoD1" styleId="apNoD1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNoD1'), $('apNoD2'))" />
                            &nbsp;-
                            <html:text tabindex="180" property="apNoD2" styleId="apNoD2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoD2'), $('apNoD3'))"/>
                            &nbsp;-
                            <html:text tabindex="190" property="apNoD3" styleId="apNoD3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoD3'), $('apNoD4'))" />
                            &nbsp;-
                            <html:text tabindex="200" property="apNoD4" styleId="apNoD4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoD4'), $('apNoE1'))"/>
                    </td>       
                    </tr>    
                <tr>  
                     <td width="20%" align="right">
                     </td>
                     <td class="formtxt" width="100%">
                        <span class="issuetitle_L_down">受理編號</span>：
                            <html:text tabindex="210" property="apNoE1" styleId="apNoE1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNoE1'), $('apNoE2'))" />
                            &nbsp;-
                            <html:text tabindex="220" property="apNoE2" styleId="apNoE2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoE2'), $('apNoE3'))"/>
                            &nbsp;-
                            <html:text tabindex="230" property="apNoE3" styleId="apNoE3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoE3'), $('apNoE4'))" />
                            &nbsp;-
                            <html:text tabindex="240" property="apNoE4" styleId="apNoE4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoE4'), $('apNoF1'))"/>
                    </td>       
                    </tr>    
                <tr>  
                     <td width="20%" align="right">
                     </td>
                     <td class="formtxt" width="100%">
                        <span class="issuetitle_L_down">受理編號</span>：
                            <html:text tabindex="250" property="apNoF1" styleId="apNoF1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNoF1'), $('apNoF2'))" />
                            &nbsp;-
                            <html:text tabindex="260" property="apNoF2" styleId="apNoF2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoF2'), $('apNoF3'))"/>
                            &nbsp;-
                            <html:text tabindex="270" property="apNoF3" styleId="apNoF3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoF3'), $('apNoF4'))" />
                            &nbsp;-
                            <html:text tabindex="280" property="apNoF4" styleId="apNoF4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoF4'), $('apNoG1'))"/>
                    </td>      
                    </tr>    
                <tr>   
                     <td width="20%" align="right">
                     </td>
                     <td class="formtxt" width="100%">
                        <span class="issuetitle_L_down">受理編號</span>：
                            <html:text tabindex="290" property="apNoG1" styleId="apNoG1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNoG1'), $('apNoG2'))" />
                            &nbsp;-
                            <html:text tabindex="300" property="apNoG2" styleId="apNoG2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoG2'), $('apNoG3'))"/>
                            &nbsp;-
                            <html:text tabindex="310" property="apNoG3" styleId="apNoG3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoG3'), $('apNoG4'))" />
                            &nbsp;-
                            <html:text tabindex="320" property="apNoG4" styleId="apNoG4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoG4'), $('apNoH1'))"/>
                    </td>       
                    </tr>    
                <tr>  
                     <td width="20%" align="right">
                     </td>
                     <td class="formtxt" width="100%">
                        <span class="issuetitle_L_down">受理編號</span>：
                            <html:text tabindex="330" property="apNoH1" styleId="apNoH1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNoH1'), $('apNoH2'))" />
                            &nbsp;-
                            <html:text tabindex="340" property="apNoH2" styleId="apNoH2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoH2'), $('apNoH3'))"/>
                            &nbsp;-
                            <html:text tabindex="350" property="apNoH3" styleId="apNoH3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoH3'), $('apNoH4'))" />
                            &nbsp;-
                            <html:text tabindex="360" property="apNoH4" styleId="apNoH4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoH4'), $('apNoI1'))"/>
                    </td>        
                    </tr>    
                <tr> 
                     <td width="20%" align="right">
                     </td>
                     <td class="formtxt" width="100%">
                        <span class="issuetitle_L_down">受理編號</span>：
                            <html:text tabindex="370" property="apNoI1" styleId="apNoI1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNoI1'), $('apNoI2'))" />
                            &nbsp;-
                            <html:text tabindex="380" property="apNoI2" styleId="apNoI2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoI2'), $('apNoI3'))"/>
                            &nbsp;-
                            <html:text tabindex="390" property="apNoI3" styleId="apNoI3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoI3'), $('apNoI4'))" />
                            &nbsp;-
                            <html:text tabindex="400" property="apNoI4" styleId="apNoI4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNoI4'), $('btnQuery'))"/>
                    </td>                                                                                    
                </tr> 
                 <tr> 
                    <td width="20%" align="right">
                    </td>
                    <td class="formtxt" width="100%">
                        <span class="issuetitle_L_down">核定年月：</span>
                        <html:text styleId="issuYm" property="issuYm" tabindex="2" size="10" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" />&nbsp;(僅提供線上月試核查詢使用)&nbsp;
                    </td>  
                </tr>     
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>     
                 <tr>
                    <td colspan="2">
                        <hr size="1" noshade="noshade">
                        <span class="titleinput">※注意事項：</span><br>
                      1.&nbsp;<span class="needtxt">＊</span>為必填欄位。
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
