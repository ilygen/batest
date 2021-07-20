<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D3P0L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="MonthlyRpt29Form" page="1" />
    <script language="javascript" type="text/javascript">
    
    <%-- 檢查是否開啟在學結束欄位 --%>
    function doRptKindChange() {
    
        var rptKind = $("rptKind").value;

        if(rptKind == 2){
             $("studeDate").disabled = false;
        }else{
             $("studeDate").disabled = true;
        }

    }
    
    function checkFields(){
        var rptKind = $("rptKind").value;
        var studeDate = $("studeDate").value;

        if(rptKind == 2){
             if(studeDate == ""){
               alert("「核定年月」為必填欄位。");
               $("studeDate").focus();
               return false;  
             }
             return true;
        }                         
        return true;
    }
    
    function initAll() {
        doRptKindChange();
    }

    Event.observe(window, 'load', initAll , false);

    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/printMonthlyRpt29" method="post" onsubmit="return validateMonthlyRpt29Form(this);">
        
        <fieldset>
            <legend>&nbsp;補送在學證明通知函&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <%--
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" name="btnPrint" class="button_90" value="列印PDF" onclick="$('page').value='1'; $('method').value='doReportPDF'; if (document.MonthlyRpt29Form.onsubmit() && checkFields()){document.MonthlyRpt29Form.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" name="btnPrint" class="button_90" value="列印EXCEL" onclick="$('page').value='1'; $('method').value='doReportExcel'; if (document.MonthlyRpt29Form.onsubmit() && checkFields()){document.MonthlyRpt29Form.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        --%>
                        <acl:checkButton buttonName="btnPrint">
                            <input type="button" name="btnPrint" class="button" value="列  印" onclick="$('page').value='1'; $('method').value='doReport'; if (document.MonthlyRpt29Form.onsubmit() && checkFields()){document.MonthlyRpt29Form.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnClear">
                            <input type="reset" name="btnClear" class="button" value="清  除" />
                        </acl:checkButton>
                    </td>
                </tr>
                 <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">給付別：</span>
                    </td>
                    <td>
                        <html:select styleId="payCode" property="payCode" tabindex="1">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PAYKIND_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">報表類別：</span>
                    </td>
                    <td>
                        <html:select property="rptKind" styleId="rptKind" onchange="doRptKindChange();">
                            <html:option value="">請選擇</html:option>
                            <html:option value="1">每月補送在學證明通知函</html:option>
                            <html:option value="2">年度補送在學證明通知函</html:option>                                       
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">核定年月：</span>
                    </td>
                    <td>
                        <html:select property="studeDate" styleId="studeDate">
                            <html:option value="">請選擇</html:option>
                            <html:option value="06">6月</html:option>
                            <html:option value="08">8月</html:option> 
                            <html:option value="09">9月</html:option>                                      
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
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
