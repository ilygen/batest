<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BABA0X220X" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
<html:javascript formName="CheckExecForm" page="2" />
<script language="javascript" type="text/javascript">
    //頁面欄位值初始化
    function initAll(){
    
    }
    
    <%-- 檔案上傳副檔名檢查 --%>
    <%-- begin ... [ --%>
    var validFilesTypes = ['txt'];
    function checkFileNameExt() {
    	var file = document.getElementById('uploadFile');
    	var filePath = file.value;
    	var isValidFile = false;

    	var ext = filePath.split('.').pop().toLowerCase();

    	for (var i = 0; i < validFilesTypes.length; i++) {
    		if (ext == validFilesTypes[i]) {
		    	isValidFile = true;
		    	break;
	    	}
    	}
    	if (!isValidFile) {
    		alert('無效的檔案。請上傳正確副檔名');
    	}

    	return isValidFile;
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
			<html:form action="/checkExec" method="post" onsubmit="" enctype="multipart/form-data">
			    <html:hidden styleId="page" property="page" value="1" />
				<html:hidden styleId="method" property="method" value="" />

				<fieldset>
					<legend>&nbsp;執行檢核作業&nbsp;</legend>

					<div align="right" id="showtime">
						網頁下載時間：民國&nbsp;
						<func:nowDateTime />
					</div>

					<table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
						<tr>
							<td colspan="2" align="right">
								<acl:checkButton buttonName="btnInsert">
									<input type="button" name="btnInsert" class="button" value="執 行" onclick="$('page').value='1'; $('method').value='doUpload'; document.CheckExecForm.submit();" />&nbsp;
								</acl:checkButton>
								<acl:checkButton buttonName="btnReset">
									<input type="reset" name="btnReset" class="button" value="清　除" onclick="resetForm();" />
								</acl:checkButton>
							</td>
						</tr>
						<tr>
							<td width="30%" align="right"><span class="needtxt">＊</span>
								<span class="issuetitle_R_down">檢核資料：</span>
							</td>
							<td width="70%">
                            	<html:file property="uploadFile" styleId="uploadFile" styleClass="textinput" size="25" />
                           </td>
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
