<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D3A0L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="MonthlyRpt10Form" page="1" />
    <script language="javascript" type="text/javascript">
    function chgPayCode(){
        if($('payCode').value == "L"){
            $('npWithLipContent').style.display="none";
        }else if($('payCode').value == "K"){
            $('npWithLipContent').style.display="inline";
        }else if($('payCode').value == "S"){
            $('npWithLipContent').style.display="none";        
        }
    }       
    
    function occurProgress(){
    	
	    if($('reportType2').checked == true){
	    	$('chkProgress').style.display="inline";
	    }else{
	    	$('chkProgress').style.display="none";
	    }
    	 
    	
    }
    
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
        
         if($('reportType2').checked == true){
	    	$('chkProgress').style.display="inline";
	    }else{
	    	$('chkProgress').style.display="none";
	    }
    }
    
    Event.observe(window, 'load', initAll , false);
    <!--
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    -->
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/printMonthlyRpt10" method="post" onsubmit="return validateMonthlyRpt10Form(this);">

        <fieldset>
            <legend>&nbsp;核付清單/核付明細表&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" name="btnPrint" id="btnPrint" tabindex="6" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.MonthlyRpt10Form.onsubmit()){document.MonthlyRpt10Form.submit();}else{return false;}" />
                        </acl:checkButton>

                    </td>
                    <td colspan="1" id="chkProgress" align="right">
                        <acl:checkButton buttonName="btnPrint">
                        <input type="button" name="btnProgress" id="btnProgress" tabindex="6" class="button" value="進度查詢" onclick="$('page').value='1'; $('method').value='doProgress'; if (document.MonthlyRpt10Form.onsubmit()){document.MonthlyRpt10Form.submit();}else{return false;}" />
                   		</acl:checkButton>
                    </td>
                    <td colspan="1" align="right">
                       <acl:checkButton buttonName="btnReset">
                            <input type="reset" name="btnReset" id="btnReset" tabindex="7" class="button" value="清  除" />
                        </acl:checkButton>                   
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">給付別：</span>
                    </td>
                    <td width="10%">
                        <html:select styleId="payCode" property="payCode" onchange="chgPayCode();" tabindex="1">
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
                        <html:text styleId="issuYm" property="issuYm" tabindex="2" size="10" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" />
                    </td>
                </tr>
                 <tr>
                    <td align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">核定日期：</span>
                    </td>
                    <td>
                        <html:text property="chkDate" styleId="chkDate" tabindex="3" styleClass="textinput" maxlength="7" size="10" onblur="this.value = asc(this.value);" />
                    </td>        
                </tr>
                <tr>
                    <td colspan="1" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">報表種類：</span>
                    </td>
                    <td colspan="3">
                        <span class="formtxt">
                            <html:radio styleId="reportType1" property="reportType" tabindex="4" value="1" onclick="occurProgress();"/>核付清單
                            &nbsp;
                            <html:radio styleId="reportType3" property="reportType"  tabindex="4" value="3" onclick="occurProgress();"/>核付清單(郵政匯票) 
                            &nbsp;
                            <html:radio styleId="reportType2" property="reportType"  tabindex="5"  value="2" onclick="occurProgress();"/>核付明細表
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="5">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="5">
                        <hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span>
                        <br>&nbsp;
                        <span class="needtxt">＊</span>為必填欄位。
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
