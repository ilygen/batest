<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M040X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <html:javascript formName="ReceivableAdjustBJForm" page="1" />           
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
  
    }    
    function checkFields(){
        var msg = "";
                   
        if($("recKind").value==""){
            msg+="「收回方式」為必填欄位。\r\n"
        }           
        if(msg!=""){
            alert(msg);
            return false;
        }else{
            return true;
        }                
    }
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/receivableAdjustBJ" method="post" onsubmit="return validateReceivableAdjustBJForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;已收調整作業 &nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnQuery" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doQuery'; if(document.ReceivableAdjustBJForm.onsubmit()){document.ReceivableAdjustBJForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>                        
                        <acl:checkButton buttonName="btnClear">
                            <input type="reset" name="btnClear" class="button" value="清　除" />
                        </acl:checkButton>                        
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right" class="issuetitle_R_down">
                        <span class="needtxt">＊</span>受理編號：
                    </td>        
                    <td>
                        <html:text property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'))" />
                        &nbsp;-
                        <html:text property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2'), $('apNo3'))"/>
                        &nbsp;-
                        <html:text property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3'), $('apNo4'))" />
                        &nbsp;-
                        <html:text property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="issuetitle_R_down">受款人身分證號：</td>
                    <td>
                        <html:text property="benIdnNo" styleId="benIdnNo" styleClass="textinput" size="15" maxlength="10" onblur="this.value=asc(this.value).toUpperCase();"/>
                    </td>        
                </tr>               
                <tr>                    
                    <td align="right" class="issuetitle_R_down">
                        <span class="needtxt">＊</span>收回方式：
                    </td>
                    <td>
                        <html:select property="recKind" styleId="recKind">
                            <html:option value="">請選擇</html:option>
                            <html:option value="01">應收款</html:option>
                            <html:option value="02">催收款</html:option> 
                            <html:option value="03">另案扣減</html:option>                                        
                        </html:select>
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
