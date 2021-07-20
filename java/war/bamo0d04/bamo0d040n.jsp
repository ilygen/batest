<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D040N" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="StopPaymentProcessQueryForm" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        document.StopPaymentProcessQueryForm.reset();
        initAll();       
    }
    <%-- ] ... end --%>
    
    function initAll() {
        dwr.util.useLoadingMessage("<bean:message key="msg.system.loading"/>");      
    }

    Event.observe(window, 'load', initAll , false);
    -->
    </script>
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/stopPaymentProcessQuery" method="post" onsubmit="return validateStopPaymentProcessQueryForm(this);">
        
        <fieldset>
            <legend>&nbsp;止付處理&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnQuery">
                            <input name="btnQuery" type="button" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doQuery'; if (document.StopPaymentProcessQueryForm.onsubmit()){document.StopPaymentProcessQueryForm.submit();}else{return false;}" />&nbsp;      
                        </acl:checkButton>
			            <acl:checkButton buttonName="btnClear">
                            <input name="btnClear" type="reset" class="button" value="清　除" onclick="resetForm();">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span><span class="issuetitle_R_down">受理編號：</span>
                    </td>
                    <td>
                        <html:text property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onkeyup="this.value=asc(this.value).toUpperCase(); autotab($('apNo1'), $('apNo2'))" />
                        &nbsp;-
                        <html:text property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value);" onkeyup="autotab($('apNo2'), $('apNo3'))" />
                        &nbsp;-
                        <html:text property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" onkeyup="autotab($('apNo3'), $('apNo4'))" />
                        &nbsp;-
                        <html:text property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" onkeyup="autotab($('apNo4'), $('issuYm'))" />
                    </td>        
                </tr>
                <tr>
        	        <td align="right">
        	            <span class="needtxt">＊</span><span class="issuetitle_R_down">核定年月：</span>
        	        </td>
                    <td>
                        <html:text property="issuYm" styleId="issuYm" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" onkeyup="autotab($('issuYm'), $('btnQuery'))" />
                    </td>  
                </tr>
                <tr>
        	        <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>
          　                                                  1.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
          　                                                  2.&nbsp;<span class="needtxt">＊</span>為必填欄位。<br>
          　                                                  3.&nbsp;各給付方式的止付條件如下述；當符合其條件者，始可執行止付作業。<br>
          　　                                                   條件1：給付方式為『匯入銀行帳戶』及『匯入郵局帳號』時，其止付條件為：實付金額大於0者，已核付但尚未領取者。<br>
          　　                                                   條件2：給付方式為『來局領取』時，其止付條件為：已核付但尚未領取且未退匯者。<br>
          　　                                                   條件3：給付方式為『郵政匯票』、『國外匯款』、『大陸地區匯款』及『扣押戶』時，其止付條件為：系統日期需小於等於核付日期加3個工作天者。<br>
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