<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M030X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <html:javascript formName="Trans2OverdueReceivableBJForm" page="1" />           
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
  
    }    
    function checkFields(){
        var msg = "";
                           
        if($("unacpSdate").value=="" && $("unacpEdate").value==""){
            msg+="請輸入「應收立帳起迄日期」\r\n";
        }else{
            if($("unacpSdate").value!="" && $("unacpEdate").value==""){
                $("unacpEdate").value = $("unacpSdate").value
            }
            if($("unacpSdate").value=="" && $("unacpEdate").value!=""){
                $("unacpSdate").value = $("unacpEdate").value
            }                
        } 
                                                                                                                   
        if($("unacpSdate").value!="" &&  $("unacpEdate").value!="" ){
            var unacpSdate = $F("unacpSdate");
            var unacpEdate = $F("unacpEdate");
            
            if (Trim(unacpSdate).length > 0 && Trim(unacpEdate).length > 0) {
                // 起日 比 迄日 大
                if (parseNumber(unacpSdate) > parseNumber(unacpEdate)) {
                    // 訊息: 「應收立帳起迄日期」範圍錯誤，請重新確認。
                    msg+='<bean:message bundle="<%=Global.BA_MSG%>" key="errors.date.range" arg0='<%=baResBundle.getMessage("label.query.receivableQuery.unacpDate")%>' />\r\n';                    
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
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/trans2OverdueReceivableBJ" method="post" onsubmit="return validateTrans2OverdueReceivableBJForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        
        <fieldset>
            <legend>&nbsp;轉催收作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnQuery" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doQuery'; if(document.Trans2OverdueReceivableBJForm.onsubmit() && checkFields()){document.Trans2OverdueReceivableBJForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>                        
                        <acl:checkButton buttonName="btnClear">
                            <input type="reset" name="btnClear" class="button" value="清　除" />
                        </acl:checkButton>                        
                    </td>
                </tr> 
                <tr>                    
                    <td align="right" class="issuetitle_R_down">
                        <span class="needtxt">＊</span>應收立帳起迄日期：
                    </td>
                    <td>
                        <html:text property="unacpSdate" styleId="unacpSdate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);" onkeyup="autotab($('unacpSdate'), $('unacpEdate'))"/>&nbsp;~&nbsp;
                        <html:text property="unacpEdate" styleId="unacpEdate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);" onkeyup="autotab($('unacpEdate'), $('btnQuery'))"/>
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
