<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D380L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/rptAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="MonthlyRpt08Form" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    <%-- begin 檢查必填欄位 --%>
    function checkFields(){        
        if (Trim($F("payCode")) == "")
            msg += '「給付別」：為必輸欄位。\r\n';
        if (Trim($F("issuYm")) == "")
            msg += '「核定年月」：為必輸欄位。\r\n';
        if (Trim($F("chkDate") == ""))
        	msg += '「核定日期」：為必輸欄位。\r\n';
        return true;
    }
    <%-- ] ... end --%>
    
    function chgPayCode(){
        if($('payCode').value == "L"){
            $('npWithLipContent').style.display="none";
        }else if($('payCode').value == "K"){
            $('npWithLipContent').style.display="inline";
        }else if($('payCode').value == "S"){
            $('npWithLipContent').style.display="none";        
        }
    }

    function doCheckSubmit() {
        var npWithLip = "N";
        if ($("npWithLip").checked)
            npWithLip = "Y";
        rptAjaxDo.isMonthlyRpt08FileExist($F("payCode"), $F("issuYm"), $F("chkDate"), npWithLip, afterCheck);
    }

    function afterCheck(retult) {
        if (retult == "true") {
            $('page').value='1'; 
            $('method').value='doReport';
            document.MonthlyRpt08Form.submit();
        }
        else {
            
                $('page').value='1'; 
                $('method').value='doReport';
                $("btnPrint").disabled = true;
                $("btnClear").disabled = true;
                document.MonthlyRpt08Form.submit();        
        }
    }
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        document.MonthlyRpt08Form.reset();
        initAll();       
    }
    <%-- ] ... end --%>
    
    function initAll() {
        if($('payCode').value == "L"){
            $('npWithLipContent').style.display="none";
        }else if($('payCode').value == "K"){
            $('npWithLipContent').style.display="inline";
             var seqNo = '<%=session.getAttribute("seqNo")%>';
             if(seqNo == '2'){
             	$('npWithLip').checked = true;
             }
        }else if($('payCode').value == "S"){
            $('npWithLipContent').style.display="none";   
        }
    }

    Event.observe(window, 'load', initAll , false);
    -->
    </script>
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/printMonthlyRpt08" method="post" onsubmit="return validateMonthlyRpt08Form(this);">
        
        <fieldset>
            <legend>&nbsp;月核定合格清冊&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnPrint">
                            <input id="btnPrint" name="btnPrint" type="button" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.MonthlyRpt08Form.onsubmit()){doCheckSubmit(); return false;}else{return false;}"/>&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnProgress">
                            <input id="btnProgress" name="btnProgress" type="button" class="button" value="進度查詢" onclick="$('page').value='1'; $('method').value='doProgress'; document.MonthlyRpt08Form.submit();"/>&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input id="btnClear" name="btnClear" type="reset" class="button" value="清　除" onclick="resetForm();">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">給付別：</span>
                    </td>
                    <td width="10%">
                        <html:select styleId="payCode" property="payCode" onchange="chgPayCode();">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>
                    <td width="60%" align="left">
                    	<div id="npWithLipContent" style="display: none">   
                    		<input type="checkbox" id="npWithLip" name="npWithLip" value="Y">國併勞
                    	</div> 
                    </td>                             
                </tr>
                <tr>
                    <td align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">核定年月：</span>
                    </td>
                    <td>
                        <html:text property="issuYm" styleId="issuYm" styleClass="textinput" maxlength="5" size="10" onblur="this.value = asc(this.value);" />
                    </td>        
                </tr>
                <tr>
                    <td align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">核定日期：</span>
                    </td>
                    <td>
                        <html:text property="chkDate" styleId="chkDate" styleClass="textinput" maxlength="7" size="10" onblur="this.value = asc(this.value);" />
                    </td>        
                </tr>
                <tr>
          	        <td colspan="3">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="3">
                        <hr size="1" noshade>
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
