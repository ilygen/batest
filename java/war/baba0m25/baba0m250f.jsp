<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BABA0M250F" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
<html:javascript formName="CompPaymentForm" page="2" />
<script language="javascript" type="text/javascript">
    //頁面欄位值初始化
    function initAll(){
    	$("apNo1").value = '';
    	$("apNo2").value = '';
    	$("apNo3").value = '';
    	$("apNo4").value = '';
    }
    
    function checkFields(){
        if(Trim($F("apNo1")).length == 0||Trim($F("apNo2")).length == 0||Trim($F("apNo3")).length == 0||Trim($F("apNo4")).length == 0){
            alert("請輸入受理編號");
            return false;
        }
        var msg = "";

        if(Trim($F("apNo1")) != "K"){
        	msg = "受理編號第一欄 僅能輸入K英文字母";
        }

        if(Trim($F("apNo3")).length <5 ){
            msg = "受理編號第三欄  不可小於五個字元";
        }

        if(Trim($F("apNo4")).length <5 ){
           if(Trim(msg).length != 0){
              msg = msg +"\n" +"受理編號第四欄  不可小於五個字元";
             }else{
              msg = "受理編號第四欄  不可小於五個字元";
             }
        }
        
        if(Trim(msg).length != 0){
            alert(msg);
            return false;
        }
        
        return true;
    }


<%-- 畫面清空重設 --%>
<%-- begin ... [ --%>
	function resetForm() {
		cleanForm();
		initAll();
	}
<%-- ] ... end --%>
	Event.observe(window, 'load', initAll, false);
</script>
</head>
<body scroll="no">

	<div id="mainContent">

		<%@ include file="/includes/ba_header.jsp"%>

		<%@ include file="/includes/ba_menu.jsp"%>

		<div id="main" class="mainBody">
			<html:form action="/disabledPayment" method="post" onsubmit="">
			    <html:hidden styleId="page" property="page" value="2" />
				<html:hidden styleId="method" property="method" value="" />

				<fieldset>
					<legend>&nbsp;失能年金補正核付作業&nbsp;</legend>

					<div align="right" id="showtime">
						網頁下載時間：民國&nbsp;
						<func:nowDateTime />
					</div>

					<table width="98%" border="0" align="center" cellpadding="2"
						cellspacing="2" class="px13">
						<tr>
							<td colspan="2" align="right">
								<acl:checkButton buttonName="btnInsert">
									<input type="button" name="btnInsert" class="button" value="新　增" onclick="$('page').value='2'; $('method').value='doInsert'; if(checkFields()){document.CompPaymentForm.submit();}else{return false;}" />&nbsp;
								</acl:checkButton>
								<acl:checkButton buttonName="btnModify">
									<input type="button" name="btnModify" class="button" value="修　改" onclick="$('page').value='2'; $('method').value='doModify'; if(checkFields()){document.CompPaymentForm.submit();}else{return false;}" />&nbsp;
                        		</acl:checkButton>
                        		<acl:checkButton buttonName="btnReset">
									<input type="reset" name="btnReset" class="button" value="清　除" onclick="resetForm();" />
								</acl:checkButton></td>
						</tr>
						<tr>
							<td width="30%" align="right"><span class="needtxt">＊</span>
								<span class="issuetitle_R_down">受理編號：</span></td>
							<td>
								<html:text styleId="apNo1" property="apNo1" size="1" maxlength="1" styleClass="textinput" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo2'));" onkeypress="" /> &nbsp;-&nbsp;
								<html:text styleId="apNo2" property="apNo2" size="1" maxlength="1" styleClass="textinput" onkeyup="autotab(this, $('apNo3'));" /> &nbsp;-&nbsp;
								<html:text styleId="apNo3" property="apNo3" size="5" maxlength="5" styleClass="textinput" onkeyup="autotab(this, $('apNo4'));" /> &nbsp;-&nbsp;
								<html:text styleId="apNo4" property="apNo4" size="5" maxlength="5" styleClass="textinput" onkeyup="autotab(this, $('btnQuery'));" />
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2">
								<hr size="1" noshade><span class="titleinput">※注意事項：</span><br>
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
