<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.bj.helper.CaseSessionHelper" %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M070X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="MonthBatchSForm" page="1" />
    <script language="javascript" type="text/javascript">
     //頁面欄位值初始化       
    function initAll(){
       changeProcType();
    }
    
    function changeProcType(){
       if(Trim($('procType').value) == "4" || Trim($('procType').value) == ""){
          $('caseTypContent').style.display="none";
          $('caseTypContent1').style.display="none";
       }else{
          $('caseTypContent').style.display="inline";
          $('caseTypContent1').style.display="inline";
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
        <html:form action="/monthBatchS" method="post" onsubmit="return validateMonthBatchSForm(this);">
        
        <fieldset>
            <legend>&nbsp;遺屬年金批次月處理作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />

                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnConfirm" class="button" value="確  認" onclick="$('page').value='1'; $('method').value='doConfirm'; if (document.MonthBatchSForm.onsubmit()){document.MonthBatchSForm.submit();}else{return false;}" tabindex="6"/>
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnQuery">
                            <input type="button" name="btnQuery" class="button_90" value="進度查詢" onclick="$('page').value='1'; $('method').value='doQuery'; document.MonthBatchSForm.submit();" tabindex="7"/>
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input type="reset" name="btnReset" class="button" value="清  除" tabindex="8"/>
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">月處理類型：</span>
                    </td>
                    <td>
                        <html:select property="procType" onchange="changeProcType();">
                            <html:option value="">請選擇</html:option>
                            <html:option value="2">批次月試核</html:option>
                            <html:option value="3">第一次批次月核定</html:option>
                            <html:option value="4">第二次批次月核定</html:option>
                         </html:select>
                     </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="issuetitle_R_down">核定年月：</span>
                    </td>
                    <td class="formtxt">
                        <html:text styleId="issuYm" property="issuYm" tabindex="2" size="10" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" />&nbsp;(僅提供進度查詢使用)&nbsp;
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                    <div id="caseTypContent" style="display: none">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">案件類別：</span>
                    </div>    
                    </td>
                    <td>
                    <div id="caseTypContent1" style="display: none">
                        <html:select property="caseTyp">
                            <html:option value="">全部</html:option>
                            <html:option value="1">新案</html:option>
                            <html:option value="2">續發案</html:option>
                         </html:select>
                    </div>     
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
