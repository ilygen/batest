<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BAMO0D211C" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
<script language="javascript" type="text/javascript">

	
<%-- 確認按鈕 --%>
<%-- 按下 確認按鈕時  檢查checkbox是否有勾選的資料 --%>

	function checkFields() {
        var bClicked = false;
        var objs = document.getElementsByName("idForConfirm");
        idForConfirm = new Array(0);
        
        for (i = 0; i < objs.length; i++) {
            if (objs[i].checked == true) {
                bClicked = true;
                temp = new Array(1)
                temp[0] = objs[i].value;
                idForConfirm = idForConfirm.concat(temp);
            }
        }
        $("idForConfirm").value=idForConfirm;
        
        if(bClicked == false){
            alert("請勾選要確認的資料");
            return false;           
        }else if(idForConfirm == ""){
            alert("沒有可確認的資料");
            return false;  
        }else{
            return bClicked;
        } 
	}

	// checkBox 單選
	function chooseOne(cb) {
		var obj = document.getElementsByName("idForConfirm");

		for (i = 0; i < obj.length; i++) {
			if (obj[i] != cb) {
				obj[i].checked = false;
			} else {
				obj[i].checked = cb.checked;
			}
		}
	}

	// checkBox 多選
	function chkIndex(obj) {
		indexs = document.getElementsByName("idForConfirm");

		var isAllChecked = true;
		for (var i = 0; i < indexs.length; i++) {
			if (indexs[i].checked == false) {
				isAllChecked = false;
				break;
			}
		}
	}
</script>
</head>
<body scroll="no">

	<div id="mainContent">

		<%@ include file="/includes/ba_header.jsp"%>

		<%@ include file="/includes/ba_menu.jsp"%>

		<div id="main" class="mainBody">
			<html:form action="/disabledCloseStatusAlteration" method="post">
				<html:hidden styleId="page" property="page" value="1" />
				<html:hidden styleId="method" property="method" value="" />
				<html:hidden styleId="apNo" property="apNo" />
				<html:hidden styleId="baappbaseId" property="baappbaseId" />
				<html:hidden styleId="idForConfirm" property="idForConfirm" />

				<fieldset>
					<legend>&nbsp;失能年金結案狀態變更作業&nbsp;</legend>

					<div align="right" id="showtime">
						網頁下載時間：民國&nbsp;
						<func:nowDateTime />
					</div>

					<table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
						<tr>
							<td colspan="6" align="right">
								<acl:checkButton buttonName="btnQuery">
									<input type="button" name="btnQuery" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doUpdate'; if(checkFields()){document.CloseStatusAlterationForm.submit();}else{return false;}" />&nbsp;
                        		</acl:checkButton> 
                        		<acl:checkButton buttonName="btnBack">
									<input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.CloseStatusAlterationForm.submit();" />
								</acl:checkButton></td>
						</tr>
						<tr align="center">
							<td><bean:define id="list" name="<%=ConstantKey.CLOSE_STATUS_ALTERATION_CASE%>" /> 
								<display:table name="pageScope.list" id="listItem">
									<display:column title="勾選" style="width:10%; text-align:center;">
										<c:choose>
	 										<c:when test="${listItem.idForConfirm eq 'Y'}">
												<input type='checkbox' name='idForConfirm' value='<c:out value="${listItem.apNo}" />;<c:out value="${listItem.seqNo}" />;<c:out value="${listItem.caseTyp}" />;<c:out value="${listItem.issuYm}" />' onclick="return chkIndex(this);"> 
											</c:when>
											<c:otherwise>
												<input type='checkbox' name='idForConfirm' disabled> 
											</c:otherwise>
										</c:choose>
										<c:if test="${listItem.idForConfirm ne 'N'}">
											<input type='hidden' name='idForConfirm' value=''>&nbsp;
                                		</c:if>
									</display:column>
									<display:column title="受理編號" style="width:15%; text-align:center">
										<c:out value="${listItem.apNoStrDisplay}" />&nbsp;
                            		</display:column>
									<display:column title="事故者姓名" style="width:45%;">
										<c:out value="${listItem.evtName}" />&nbsp;
                            		</display:column>
									<display:column title="身分證號碼" style="width:15%;">
										<c:out value="${listItem.evtIdnNo}" />&nbsp;
                            		</display:column>
									<display:column title="案件狀態" style="width:15%;">
										<c:if test="${listItem.idForConfirm eq 'Y'}">
										結案&nbsp;
										</c:if>
                            		</display:column>
								</display:table></td>
						</tr>
						<tr>
							<td colspan="6">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="6">&nbsp;</td>
						</tr>
					</table>
				</fieldset>

			</html:form>
		</div>
	</div>

	<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
