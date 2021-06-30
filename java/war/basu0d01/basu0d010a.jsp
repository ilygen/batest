<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BASU0D010A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/executiveCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="ExecutiveSupportMaintForm" page="1" />
    <script language="javascript" type="text/javascript">
	//根據受理編號 取得是否已取銷註記
	function getProcastatStatus(){
			
			var theForm = document.ExecutiveSupportMaintForm;
			var apNo = theForm.apNo1.value + theForm.apNo2.value + theForm.apNo3.value + theForm.apNo4.value;
			executiveCommonAjaxDo.getProcastatStatus(apNo, setProcastatStatus);
			
    }
    
    function setProcastatStatus(procastatStatus){           
        
         if(procastatStatus != 0 ){
            alert("受理案件已註銷，不得新增或修改行政支援記錄。");
            return false;
        }else {
            document.ExecutiveSupportMaintForm.submit();
        }
        
    }
       
 
</script>
    
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/executiveSupportMaint" method="post" onsubmit="return validateExecutiveSupportMaintForm(this);">
        
        <fieldset>
            <legend>&nbsp;行政支援記錄維護&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnInsert">
                            <input type="button" name="btnInsert" class="button" value="新 增" onclick="$('page').value='1'; $('method').value='doAdd'; if(document.ExecutiveSupportMaintForm.onsubmit()){getProcastatStatus();}" tabindex="6"/>
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnModify">
                            <input type="button" name="btnModify" class="button" value="修 改" onclick="$('page').value='1'; $('method').value='doModify'; if (document.ExecutiveSupportMaintForm.onsubmit()){document.ExecutiveSupportMaintForm.submit();}else{return false;}" tabindex="7"/>
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input type="reset" name="btnReset" class="button" value="清  除" tabindex="8"/>
                        </acl:checkButton>
                    </td>
                    <input type="hidden" id="procastatStatus" name="procastatStatus" value="">
                </tr>
                <tr>
                    <td width="30%" align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">受理編號：</span>
                    </td>
                    <td>
                        <span class="formtxt">
                            <html:text styleId="apNo1" property="apNo1" size="1" maxlength="1" styleClass="textinput" onkeyup="this.value=asc(this.value).toUpperCase(); autotab($('apNo1'), $('apNo2'));" tabindex="1"/>
                            &nbsp;-&nbsp;
                            <html:text styleId="apNo2" property="apNo2" size="1" maxlength="1" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('apNo2'), $('apNo3'));" tabindex="2"/>
                            &nbsp;-&nbsp;
                            <html:text styleId="apNo3" property="apNo3" size="5" maxlength="5" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('apNo3'), $('apNo4'));" tabindex="3"/>
                            &nbsp;-&nbsp;
                            <html:text styleId="apNo4" property="apNo4" size="5" maxlength="5" styleClass="textinput" onblur="this.value=asc(this.value);" onkeyup="autotab($('apNo4'), $('issuYm'));" tabindex="4"/>
                         </span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">核定年月：</span>
                    </td>
                    <td>
                        <span class="formtxt">
                        <html:text styleId="issuYm" property="issuYm" size="5" maxlength="5" styleClass="textinput" tabindex="5" onkeyup="autotab($('issuYm'), $('btnInsert'));"/>&nbsp;
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <hr size="1" noshade="noshade">
                        <span class="titleinput">※注意事項：</span><br>
            　                                                  1.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
            　                                                  2.&nbsp;<span class="needtxt">＊</span>為必填欄位。
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
