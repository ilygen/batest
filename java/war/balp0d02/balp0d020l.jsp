<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.rpt.forms.OldAgeReviewRpt02Form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D020L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="OldAgeReviewRpt02Form" page="1" />
    <script language="javascript" type="text/javascript">
    <!--
    <%-- begin 檢查必填欄位 --%>
    function initAll() {
        <%--
        <%if(request.getSession().getAttribute("OldAgeReviewRpt02Form") == null){%>     
        --%>
            $('prtTyp1').checked = true
            $('chkDateContent1').style.display="inline";
            $('chkDateContent2').style.display="inline";
            $('packDateContent1').style.display="none";
            $('packDateContent2').style.display="none";
        <%--
        <%}else{%>
            var prtTyp = '<%=((OldAgeReviewRpt02Form)request.getSession().getAttribute("OldAgeReviewRpt02Form")).getPrtTyp()%>'     
            if(prtTyp == '1'){
                $('prtTyp1').checked = true
                $('chkDateContent1').style.display="inline";
                $('chkDateContent2').style.display="inline";
                $('packDateContent1').style.display="none";
                $('packDateContent2').style.display="none";
            }else if(prtTyp == '2'){
                $('prtTyp2').checked = true
                $('chkDateContent1').style.display="none";
                $('chkDateContent2').style.display="none";
                $('packDateContent1').style.display="inline";
                $('packDateContent2').style.display="inline";
            }
        <%}%>
        --%>
    }
    
    function chgPrtTyp(){
        if($('prtTyp1').checked == true){
            $('chkDateContent1').style.display="inline";
            $('chkDateContent2').style.display="inline";
            $('packDateContent1').style.display="none";
            $('packDateContent2').style.display="none";
        }else if($('prtTyp2').checked == true){
            $('chkDateContent1').style.display="none";
            $('chkDateContent2').style.display="none";
            $('packDateContent1').style.display="inline";
            $('packDateContent2').style.display="inline";
        }
    }
    
    function checkFields() {
        var msg = "";
        
        if($('prtTyp2').checked == true){
            if(Trim($('packDate').value)==""){
                msg += "「打包日期」為必填欄位。\r\n"
                $('packDate').focus();
            }
            /*
            if(Trim($('pageNo').value)==""){
                msg += "「頁次」為必填欄位。\r\n";
                $('pageNo').focus();
            }
            */
        }
        
            
        if (msg != "") {
            alert(msg);
            return false;
        }
        else {
            return true;
        }      
    }
    <%-- ] ... end --%>
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        document.OldAgeReviewRpt02Form.reset();
        initAll();       
    }
    <%-- ] ... end --%>
    
    Event.observe(window, 'load', initAll , false);
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    -->
    </script>
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/printOldAgeReviewRpt02" method="post" onsubmit="return validateOldAgeReviewRpt02Form(this);">
        
        <fieldset>
            <legend>&nbsp;審核給付清單&nbsp;</legend>
            
            <div align="right" id="showtime">
                    網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnPrint">
                            <input name="btnPrint" type="button" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.OldAgeReviewRpt02Form.onsubmit() && checkFields()){document.OldAgeReviewRpt02Form.submit();}else{return false;}"/>&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnClear">
                            <input name="btnClear" type="reset" class="button" value="清　除" onclick="resetForm();">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">列印項目：</span>
                    </td>
                    <td class="formtxt">
                        <html:radio styleId="prtTyp1" property="prtTyp" value="1" onclick="chgPrtTyp();" />打包列印&nbsp;
                        <html:radio styleId="prtTyp2" property="prtTyp" value="2" onclick="chgPrtTyp();" />重新列印
                    </td>        
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">給付別：</span>
                    </td>
                    <td>
                        <html:select styleId="payCode" property="payCode">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>        
                </tr>
                <tr>
                    <td align="right">
                        <span class="needtxt">＊</span><span class="issuetitle_R_down">審核人員：</span>
                    </td>
                    <td>
                        <html:text property="chkMan" styleId="chkMan" styleClass="textinput" maxlength="5" size="5" onblur="this.value = asc(this.value);" />
                    </td>        
                </tr>
                <tr>
                    <td align="right">
                        <div id="chkDateContent1" style="display: none">
                            <span class="issuetitle_R_down">審核日期：</span>
                        </div>
                        <div id="packDateContent1" style="display: none">
                            <span class="needtxt">＊</span>
                            <span class="issuetitle_R_down">打包日期&nbsp;+&nbsp;頁次：</span>
                        </div>
                    </td>
                    <td>
                        <div id="chkDateContent2" style="display: none">
                            <html:text property="chkDate" styleId="chkDate" styleClass="textinput" maxlength="7" size="7" onblur="this.value = asc(this.value);" />
                        </div>
                        <div id="packDateContent2" style="display: none">
                            <html:text property="packDate" styleId="packDate" styleClass="textinput" maxlength="7" size="7" onblur="this.value = asc(this.value);" onkeyup="autotab($('packDate'), $('pageNo'))"/>
                            &nbsp;+
                            <html:text property="pageNo" styleId="pageNo" styleClass="textinput" maxlength="5" size="5" onblur="this.value = asc(this.value);" />
                        </div>
                    </td>
                </tr>
                <tr>
          	        <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
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
