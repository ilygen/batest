<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D200C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>      
    <html:javascript formName="DisabledPaymentReceiveForm" page="1" />                   
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
    
    }    
    
    function checkFields(){
        
        var msg = "";
        
        if($("receiveType").value == "2"){
        if(Trim($F("seqNo")).length == 0){
            msg+="請輸入「受款人序」。\r\n";
            $("seqNo").focus();
        }
        if(Trim($F("seqNo")).length != 0 && Trim($F("seqNo")).length !=4 && !isNaN($("seqNo").value)){
            msg+="請輸入完整「受款人序」。\r\n";
            $("seqNo").focus();
        }
        }
        if(isNaN($("seqNo").value)){
            msg+="「受款人序」格式錯誤。\r\n";
            $("seqNo").focus();
        }
        if($("receiveType").value == ""){
            msg+="「收回狀況」為必填欄位。\r\n";
            
        }
        
        if(msg!=""){
            alert(msg);
            return false;
        }else{
            return true;
        }         
    }
    
    function initAllForClean(){
        $("apNo1").value="";
        $("apNo2").value="";
        $("apNo3").value="";
        $("apNo4").value="";
        $("seqNo").value="";
        $("receiveType").value="";
    }
    
	<%-- 畫面重設 --%>
    function resetForm(){
        cleanForm();
        initAllForClean();
    }	
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/disabledPaymentReceive" method="post" onsubmit="return validateDisabledPaymentReceiveForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;失能年金應收收回處理&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnQuery" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doQuery'; if(document.DisabledPaymentReceiveForm.onsubmit() && checkFields()){document.DisabledPaymentReceiveForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnSave">
                            <input type="button" name="btnQuery" class="button" value="註　銷" onclick="$('page').value='1'; $('method').value='doCancel'; if(document.DisabledPaymentReceiveForm.onsubmit() && checkFields()){document.DisabledPaymentReceiveForm.submit();}else{return false;}" />&nbsp;      
	                    </acl:checkButton>
	                    &nbsp;
                        <acl:checkButton buttonName="btnClear">
                            <input name="btnClear" type="reset" class="button" value="清  除 " onclick="resetForm()">
                        </acl:checkButton>                        
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">受理編號：</span>
                    </td>
                    <td>
                        <html:text property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'))" />
                        &nbsp;-
                        <html:text property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2'), $('apNo3'))"/>
                        &nbsp;-
                        <html:text property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3'), $('apNo4'))" />
                        &nbsp;-
                        <html:text property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" />
                    </td>
                </tr>   
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">受款人序：</span>
                    </td>
                    <td>
                        <html:text property="seqNo" styleId="seqNo" styleClass="textinput" size="4" maxlength="4" onblur="this.value=asc(this.value)" onkeyup="autotab($('seqNo'), $('btnQuery'))"/>
                    </td>
                </tr>   
                <tr>    
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_L_down">收回狀況：</span>
                    </td>
                    <td>
                        <html:select property="receiveType" styleId="receiveType" styleClass="formtxt">
                            <html:option value="">請選擇</html:option>
                            <html:option value="1">1-現金收回</html:option>
                            <html:option value="2">2-退匯收回</html:option>
                        </html:select> 
                    </td>                         
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2"><hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>
                 　                 &nbsp;1.<span class="needtxt">＊</span>為必填欄位。<br>
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
