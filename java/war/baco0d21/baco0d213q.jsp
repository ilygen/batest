<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List"%>
<%@ page
	import="tw.gov.bli.ba.review.cases.DisabledPaymentReviewExtCase"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
	<acl:setProgId progId="BACO0D213Q" />
	<title><bean:message bundle="<%=Global.BA_MSG%>"
			key="title.system.name" />
	</title>
	<link rel="stylesheet" type="text/css"
		href="<c:url value='/css/bastyle.css'/>" />
	<link rel="stylesheet" type="text/css"
		href="<c:url value='/css/menu.css'/>" />
	<script type='text/javascript'
		src='<c:url value="/dwr/interface/reviewCommonAjaxDo.js"/>'>
</script>
	<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'>
</script>
	<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'>
</script>
	<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'>
</script>
	<script type='text/javascript'
		src='<c:url value="/js/ba_functions.js"/>'>
</script>
	<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'>
</script>
	<script language="javascript" type="text/javascript">
<!--
    //頁面欄位值初始化       
    function initAll(){        
       if($('origNotifyForm').value==''){
           $('notifyForm').value = '001';
       }
       if("<c:out value='SurvivorPaymentReviewForm.chgNote'/>"==''){
           $('chgNote').value = '50';
       }
       if($("q2").value > 0 && $("caseTyp").value != "3" && $("caseTyp").value != "6"){
       alert('有尚未銷案之行政支援記錄，請進行銷案');
       $("btnUpdate").disabled = true;
       }
       checkNotifyForm();
       checkMaadmrec();
    }      
    
    // Ajax for 取得 檢查核定格式
    function checkNotifyForm() {
        reviewCommonAjaxDo.checkNotifyForm($("apNo").value, checkNotifyFormResult);
    }
    
    function checkNotifyFormResult(checkResult){   
        $("checkResult").value = checkResult;                
    }
    
    // Ajax for 檢核行政支援記錄檔
    function checkMaadmrec(){
    	reviewCommonAjaxDo.checkMaadmrec($("apNo").value, $("caseTyp").value, checkMaadmrecResult);
    }
    
    function checkMaadmrecResult(checkResult1){
        var msg = "";
        
    	$("checkResult1").value = checkResult1;

        if(Trim($("checkResult1").value)!=""){  
            $("btnUpdate").disabled = true;
        	msg += "案件類別有誤，請重新編審！。\r\n"
        }  	
        
        if(msg != ""){
            alert(msg);
            return false;
        }else{
            return true;
        }
    }
    
    // Ajax for 更新核定資料
    function flashIssuData() {
        var payYm = $("payYmOption").value;
        var seqNo = $("seqNoOption").value;
        var acceptMk = $("acceptMkOption").value;
        reviewCommonAjaxDo.flashIssuDataList(payYm, seqNo, acceptMk, setIssuData);
    }
    
    
    function setIssuData(issuDataList) {
        dwr.util.removeAllRows("benIssuDataTable");
        $("issuDataSpan").innerHTML = "";
        var innerHTMLStr = "";
        if(issuDataList.length==0){
            innerHTMLStr += "<table id='tb' width='100%' cellpadding='3' cellspacing='5' class='px13'>";
            innerHTMLStr += "<tr><td width='100%' align='center'>";
            innerHTMLStr += "<font color='red'>無資料</font>";
            innerHTMLStr += "</td></tr>";
            innerHTMLStr += "</table>";
        }else{
            for(var i=0; i<issuDataList.length; i++){
                var issuData = issuDataList[i];
                var benIssueDataArray = issuData.benIssueDataArray;
                var payYmStr = issuData.payYmStr;
                
                if(i%2 == 0){
                    innerHTMLStr += '<table id="tb" width="100%" cellpadding="3" cellspacing="5" class="px13">';
                }else{
                    innerHTMLStr += '<table id="tb" width="100%" cellpadding="3" cellspacing="5" class="px13" bgcolor="#E8E8FF">';
                }
                
                innerHTMLStr += '<tr><td class="issuetitle_L" colspan="10" ><span class="issuetitle_search">&#9658;</span>&nbsp;給付年月：' + payYmStr +'</td></tr>';
                
                for(var j=0; j<benIssueDataArray.length; j++){
                    benIssuData = benIssueDataArray[j];
                    innerHTMLStr += '<tr>';
                    //序
                    innerHTMLStr += '<td class="issueinfo_C" width="5%">'+(j+1)+'</td>';
                    //姓 名
                    innerHTMLStr += '<td class="issueinfo" width="15%">'+getDefaultString((benIssuData.benName))+'</td>';
                    //可領金額
                    innerHTMLStr += '<td class="issueinfo_R" width="10%">'+getDefaultString(numFormat(benIssuData.issueAmt))+'</td>';
                    //應收金額
                    innerHTMLStr += '<td class="issueinfo_R" width="10%">'+getDefaultString(numFormat(benIssuData.recAmt))+'</td>';
                    //本案沖抵
                    innerHTMLStr += '<td class="issueinfo_R" width="10%">'+getDefaultString(numFormat(benIssuData.payBanance))+'</td>';
                    //扣減金額
                    innerHTMLStr += '<td class="issueinfo_R" width="10%">'+getDefaultString(numFormat(benIssuData.otherAmt))+'</td>';
                    //紓困／呆帳
                    innerHTMLStr += '<td class="issueinfo_R" width="10%">'+getDefaultString(numFormat(benIssuData.offsetAmt))+" ／ "+getDefaultString(numFormat(benIssuData.badDebtAmt))+'</td>';
                    //匯款匯費
                    innerHTMLStr += '<td class="issueinfo_R" width="10%">'+getDefaultString(numFormat(benIssuData.payRate))+'</td>';
                    //實付金額
                    innerHTMLStr += '<td class="issueinfo_R" width="10%">'+getDefaultString(numFormat(benIssuData.aplPayAmt))+'</td>';
                    //電腦編審結果
                    var acceptMkStr = "";
                    if(benIssuData.acceptMk == 'Y'){
                        acceptMkStr = "Y-合格";
                    }else if(benIssuData.acceptMk == 'N'){
                        acceptMkStr = "N-不合格";
                    }else{
                        acceptMkStr = "待處理";
                    }
                    innerHTMLStr += '<td class="issueinfo" width="10%">'+(acceptMkStr)+'</td>';
                    
                    innerHTMLStr += '</tr>';
                }
                innerHTMLStr +='</table>';
            }
        }
        $("issuDataSpan").innerHTML = innerHTMLStr;
    }
    
    //字串處理
    //如果傳入值為null, 回傳" "；否則回傳原始傳入值
    //防止資料底線消失
    function getDefaultString(strValue){
        if(strValue=="" || strValue==" " || strValue==null){
            return "&nbsp;";
        }else{
            return strValue;
        }
    }

    //送出前檢查
    function checkFields(){
        var msg = "";       
        //檢查核定格式
        //[
        if(Trim($("notifyForm").value)==""){
            msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.review.notifyForm")%>' />\r\n";
            $("notifyForm").focus();
        }else{
            checkResult = ($("checkResult").value).split(",");
            var chkNotifyForm = false;
            for(i=0; i<checkResult.length; i++){
                if(checkResult[i]==$("notifyForm").value){
                    chkNotifyForm = true;
                    break;
                }
            }
            if(!chkNotifyForm){
                msg += "「核定格式」不正確。\r\n";
                $("notifyForm").focus();
            }
        }
        //]
        
        if($("caseTyp").value=="2"){        
            if($("chgNote").value==""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.review.chgNote")%>' />\r\n";
                $("chgNote").focus();
            }
        }       
        
        if(msg != ""){
            alert(msg);                                                                       
            return false;
        }else{
            var evtMcMkRadios = document.getElementsByName("evtMcMkRadio"); 
            var manchkMk;
                    
            for (i = 0; i < evtMcMkRadios.length; i++) {
                if (evtMcMkRadios[i].checked) {
                    $("manchkMk").value = evtMcMkRadios[i].value;
                }
            }
            return true;
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
			<html:form action="/survivorPaymentReview" method="post" onsubmit="">
				<html:hidden styleId="page" property="page" value="1" />
				<html:hidden styleId="method" property="method" value="" />
				<html:hidden styleId="manchkMk" property="manchkMk" />
				<bean:define id="pay"
					name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_CASE%>" />
				<bean:define id="disabledData"
					name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_DISABLED_DATA%>" />
				<input type="hidden" id="apNo" name="apNo" value="<c:out value="${pay.apNo}" />">
				<input type="hidden" id="checkResult" name="checkResult" value="">
				<input type="hidden" id="checkResult1" name="checkResult1" value="">
				<input type="hidden" id="origNotifyForm" name="origNotifyForm"
					value="<c:out value="${SurvivorPaymentReviewForm.notifyForm}" />">
				<input type="hidden" id="caseTyp" name="caseTyp"
					value="<c:out value="${pay.caseTyp}" />">
				<input type="hidden" id="q2" name="q2" value="<c:out value="${pay.q2}" />">
				<fieldset>
					<legend>
						&nbsp;遺屬年金審核清單明細資料&nbsp;
					</legend>

					<div align="right" id="showtime">
						網頁下載時間：民國&nbsp;
						<func:nowDateTime />
					</div>

					<table width="98%" border="0" align="center" cellpadding="2"
						cellspacing="2" class="px13">
						<tr>
							<td align="right">
								<acl:checkButton buttonName="btnUpdate">
									<c:if
										test="${(pay.q2 gt 0) and (pay.caseTyp ne '3' and pay.caseTyp ne '6')}">
										<input type="button" id="btnUpdate" name="btnUpdate" class="button"
											value="確　認" disabled="true" />&nbsp;                              
                                    </c:if>
									<c:if
										test="${(pay.q2 le 0) or ((pay.q2 gt 0) and (pay.caseTyp eq '3' or pay.caseTyp eq '6'))}">
										<input type="button" id="btnUpdate" name="btnUpdate" class="button"
											value="確　認"
											onclick="checkMaadmrec(); checkNotifyForm(); $('page').value='1'; $('method').value='doSaveBeneficiaryData'; if(checkFields()){document.SurvivorPaymentReviewForm.submit();}else{return false;}" />&nbsp;
                                    </c:if>
								</acl:checkButton>
								<acl:checkButton buttonName="btnBack">
									<bean:define id="qryCond"
										name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_QUERY_FORM %>" />
									<c:if test="${qryCond.resultSize gt 1}">
										<input type="button" name="btnBack" class="button" value="返　回"
											onclick="$('page').value='1'; $('method').value='doBackList'; document.SurvivorPaymentReviewForm.submit();" />&nbsp;
                                    </c:if>
									<c:if test="${qryCond.resultSize le 1}">
										<input type="button" name="btnBack" class="button" value="返　回"
											onclick="$('page').value='1'; $('method').value='doBack'; document.SurvivorPaymentReviewForm.submit();" />&nbsp;
                                    </c:if>
								</acl:checkButton>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="2" cellspacing="2"
									class="px13">
									<tr>
										<td width="34%">
											<span class="issuetitle_L_down">受理編號：</span>
											<c:out value="${pay.apNoStrDisplay}" />
											&nbsp;
										</td>
										<td width="33%">
											<span class="issuetitle_L_down">事故者死亡日期：</span>
											<c:out value="${pay.evtDieDateStr}" />
											&nbsp;
										</td>
										<td width="33%">
											<span class="issuetitle_L_down">版次：</span>
											<c:out value="${pay.veriSeq}" />
											&nbsp;
										</td>
									</tr>
									<tr>
										<td colspan="3">
											<span class="issuetitle_L_down">事故者姓名：</span>
											<c:out value="${pay.evtName}" />
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>
											<span class="issuetitle_L_down">事故者身分證號：</span>
											<c:out value="${pay.evtIdnNo}" />
											&nbsp;
										</td>
										<td>
											<span class="issuetitle_L_down">事故者出生日期：</span>
											<c:out value="${pay.evtBrDateStr}" />
											&nbsp;
										</td>
										<td>
											<span class="issuetitle_L_down">申請日期：</span>
											<c:out value="${pay.appDateStr}" />
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>
											<span class="issuetitle_L_down">首次給付年月：</span>
											<c:out value="${pay.payYmsStr}" />
											<c:if test='${(pay.payYmsStr ne "")or(pay.payYmeStr ne "")}'>
                                            &nbsp;~&nbsp;
                                            </c:if>
											<c:out value="${pay.payYmeStr}" />
											&nbsp;
										</td>
										<td>
											<span class="issuetitle_L_down">核定年月：</span>
											<c:out value="${pay.issuYmStr}" />
											&nbsp;
										</td>
										<td>
											<span class="issuetitle_L_down">給付年月起迄：</span>
											<c:out value="${pay.minPayYmStr}" />
											<c:if
												test='${(pay.minPayYmStr ne "")or(pay.maxPayYmStr ne "")}'>
                                            &nbsp;~&nbsp;
                                            </c:if>
											<c:out value="${pay.maxPayYmStr}" />
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>
											<span class="issuetitle_L_down">電腦編審結果：</span>
											<c:if test="${pay.acceptMk eq 'Y'}">
												<c:out value="Y-合格" />
											</c:if>
											<c:if test="${pay.acceptMk eq 'N'}">
												<c:out value="N-不合格" />
											</c:if>
											<c:if test="${(pay.acceptMk ne 'Y')and(pay.acceptMk ne 'N')}">
												<c:out value="待處理" />
											</c:if>
										</td>
										<td>
											<span class="issuetitle_L_down">人工審核結果：</span>
											<c:if test="${pay.manchkMk eq 'Y'}">
												<c:out value="Y-合格" />
											</c:if>
											<c:if test="${pay.manchkMk eq 'N'}">
												<c:out value="N-不合格" />
											</c:if>
											<c:if test="${(pay.manchkMk ne 'Y')and(pay.manchkMk ne 'N')}">
												<c:out value="待處理" />
											</c:if>
										</td>
										<td>
											<span class="issuetitle_L_down">案件類別：</span>

											<c:if test='${(pay.caseTyp eq "1")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_1%>" />
											</c:if>
											<c:if test='${(pay.caseTyp eq "2")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_2%>" />
											</c:if>
											<c:if test='${(pay.caseTyp eq "3")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_3%>" />
											</c:if>
											<c:if test='${(pay.caseTyp eq "4")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_4%>" />
											</c:if>
											<c:if test='${(pay.caseTyp eq "5")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_5%>" />
											</c:if>
											<c:if test='${(pay.caseTyp eq "6")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_6%>" />
											</c:if>
											<c:if test='${(pay.caseTyp eq "A")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_A%>" />
											</c:if>
											<c:if test='${(pay.caseTyp eq "B")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_B%>" />
											</c:if>
											<c:if test='${(paypay.caseTyp eq "C")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_C%>" />
											</c:if>
											<c:if test='${(pay.caseTyp eq "D")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_D%>" />
											</c:if>
											<c:if test='${(pay.caseTyp eq "E")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_E%>" />
											</c:if>
											<c:if test='${(pay.caseTyp eq "F")}'>
												<c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_F%>" />
											</c:if>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center" class="table1">
								<table width="98%" cellpadding="1" cellspacing="3" class="px13">
									<tr>
										<td colspan="9">
											<table width="100%" cellpadding="0" cellspacing="0"
												class="px13">
												<td width="85%" class="issuetitle_L">
													<span class="systemMsg">▲</span>&nbsp;核定資料
												</td>
												<td width="15%" id="tabsJ" class="issuetitle_L"
													valign="bottom">
													<a
														href="<c:url value="/survivorPaymentReview.do?method=doReviewRpt&action=single" />"
														onclick="Event.stopObserving(window, 'beforeunload', beforeUnload);"><span>檢視受理/審核清單</span>
													</a>
												</td>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan="2" class="issuetitle_L">
											<span class="needtxt">＊</span>
											<span class="issuetitle_L_down">核定格式：</span>
											<html:text property="notifyForm" styleId="notifyForm"
												styleClass="textinput" size="3" maxlength="3"
												onchange="checkNotifyForm();"
												onblur="this.value=asc(this.value);" />
										</td>
										<td colspan="7" class="issuetitle_L">
											<c:if test="${pay.caseTyp eq '2'}">
												<span class="needtxt">＊</span>                                    
                                                             更正原因：
                                                <html:select
													property="chgNote">
													<html:options
														collection="<%=ConstantKey.CHGNOTE_OPTION_LIST%>"
														property="paramCode" labelProperty="paramName" />
												</html:select>
											</c:if>
											<c:if test="${pay.caseTyp ne '2'}">
                                                             更正原因：
                                                <html:select
													property="chgNote" disabled="true">
													<html:options
														collection="<%=ConstantKey.CHGNOTE_OPTION_LIST%>"
														property="paramCode" labelProperty="paramName" />
												</html:select>
											</c:if>
										</td>
									</tr>
									<tr>
										<td colspan="7" class="issuetitle_L">
											[&nbsp;&nbsp;
											<span class="issuetitle_search">事故者資料</span>&nbsp;&nbsp;]
										</td>
									</tr>
									<logic:iterate id="beneficiaryData"
										name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_BENEFICIARY_DATA_LIST %>"
										indexId="index">
										<c:if test="${beneficiaryData.seqNo eq '0000'}">
											<tr>
												<td colspan="7" class="issuetitle_L">
													<table width="100%" border="0" cellspacing="0"
														cellpadding="0" class="px13">
														<tr>
															<td colspan="2">
																<span class="issuetitle_L_down">姓 名：</span>
																<c:out value="${beneficiaryData.evtName}" />
															</td>
															<td>
																<span class="issuetitle_L_down">電腦編審結果：</span>
																<c:if test="${beneficiaryData.acceptMk eq 'Y'}">
																	<c:out value="Y-合格" />
																</c:if>
																<c:if test="${beneficiaryData.acceptMk eq 'N'}">
																	<c:out value="N-不合格" />
																</c:if>
																<c:if
																	test="${(beneficiaryData.acceptMk ne 'Y')and(beneficiaryData.acceptMk ne 'N')}">
																	<c:out value="待處理" />
																</c:if>
															</td>
														</tr>
														<tr>
															<td width="33%">
																<span class="issuetitle_L_down">身分證號：</span>
																<c:out value="${beneficiaryData.evtIdnNo}" />
															</td>
															<td width="33%">
																<span class="issuetitle_L_down">關 係：</span>
																<c:out value="${beneficiaryData.benEvtRelStr}" />
															</td>
															<td width="34%">
																<span class="issuetitle_L_down">人工審核結果：</span>
																<label class="formtxt">
																	<c:if test="${beneficiaryData.manchkMk eq 'Y'}">
																		<c:if test="${beneficiaryData.manchkMkCtlY eq 'Y'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="Y" checked="true">Y-合格
                                                                    </c:if>
																		<c:if test="${beneficiaryData.manchkMkCtlY eq 'N'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="Y" checked="true"
																				disabled="true">Y-合格
                                                                    </c:if>
																		<c:if test="${beneficiaryData.manchkMkCtlN eq 'Y'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="N">N-不合格
                                                                    </c:if>
																		<c:if test="${beneficiaryData.manchkMkCtlN eq 'N'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="N" disabled="true">N-不合格
                                                                    </c:if>
																		<c:if
																			test="${beneficiaryData.manchkMkCtlSpace eq 'Y'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="">待處理
                                                                    </c:if>
																		<c:if
																			test="${beneficiaryData.manchkMkCtlSpace eq 'N'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="" disabled="true">待處理
                                                                    </c:if>
																	</c:if>
																	<c:if test="${beneficiaryData.manchkMk eq 'N'}">
																		<c:if test="${beneficiaryData.manchkMkCtlY eq 'Y'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="Y">Y-合格
                                                                    </c:if>
																		<c:if test="${beneficiaryData.manchkMkCtlY eq 'N'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="Y" disabled="true">Y-合格
                                                                    </c:if>
																		<c:if test="${beneficiaryData.manchkMkCtlN eq 'Y'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="N" checked="true">N-不合格
                                                                    </c:if>
																		<c:if test="${beneficiaryData.manchkMkCtlN eq 'N'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="N" checked="true"
																				disabled="true">N-不合格
                                                                    </c:if>
																		<c:if
																			test="${beneficiaryData.manchkMkCtlSpace eq 'Y'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="">待處理
                                                                    </c:if>
																		<c:if
																			test="${beneficiaryData.manchkMkCtlSpace eq 'N'}">
																			<input type="radio" id="evtMcMkRadio"
																				name="evtMcMk<c:out value="${index}" />" value="" disabled="true">待處理
                                                                    </c:if>
																	</c:if>
																	<c:if test="${beneficiaryData.manchkMk eq ' '}">
																		<c:if test="${beneficiaryData.manchkMkCtlY eq 'Y'}">
																			<input type="radio" id="evtMcMkRadio" name="evtMcMk"
																				value="Y">Y-合格
                                                                    </c:if>
																		<c:if test="${beneficiaryData.manchkMkCtlY eq 'N'}">
																			<input type="radio" id="evtMcMkRadio" name="evtMcMk"
																				value="Y" disabled="true">Y-合格
                                                                    </c:if>
																		<c:if test="${beneficiaryData.manchkMkCtlN eq 'Y'}">
																			<input type="radio" id="evtMcMkRadio" name="evtMcMk"
																				value="N">N-不合格
                                                                    </c:if>
																		<c:if test="${beneficiaryData.manchkMkCtlN eq 'N'}">
																			<input type="radio" id="evtMcMkRadio" name="evtMcMk"
																				value="N" disabled="true">N-不合格
                                                                    </c:if>
																		<c:if
																			test="${beneficiaryData.manchkMkCtlSpace eq 'Y'}">
																			<input type="radio" id="evtMcMkRadio" name="evtMcMk"
																				value="" checked="true">待處理
                                                                    </c:if>
																		<c:if
																			test="${beneficiaryData.manchkMkCtlSpace eq 'N'}">
																			<input type="radio" id="evtMcMkRadio" name="evtMcMk"
																				value="" checked="true" disabled="true">待處理
                                                                    </c:if>
																	</c:if>
																</label>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr align="center">
												<td class="issuetitle_L" width="14%">
													給付年月
												</td>
												<td class="issuetitle_L" width="16%">
													核定／物價調整金額
												</td>
												<td class="issuetitle_L" width="14%">
													應收／沖抵金額
												</td>
												<td class="issuetitle_L" width="14%">
													補發金額
												</td>
												<td class="issuetitle_L" width="14%">
													事故者／遺屬扣減
												</td>
												<td class="issuetitle_L" width="14%">
													紓困金額
												</td>
												<td class="issuetitle_L" width="14%">
													實付金額
												</td>
											</tr>
											<logic:iterate id="issueData" name="beneficiaryData"
												property="benIssueDataList">
												<tr>
													<td class="issueinfo_C">
														<c:out value="${issueData.payYmStr}" />
														&nbsp;
													</td>
													<td class="issueinfo_R">
														<fmt:formatNumber value="${issueData.issueAmt}" />
														&nbsp;
													</td>
													<td class="issueinfo_R">
														<fmt:formatNumber value="${issueData.recAmt}" />
														&nbsp;／&nbsp;
														<fmt:formatNumber value="${issueData.payBanance}" />
													</td>
													<td class="issueinfo_R">
														<fmt:formatNumber value="${issueData.supAmt}" />
														&nbsp;
													</td>
													<td class="issueinfo_R">
														<fmt:formatNumber value="${issueData.otherAmt}" />
														&nbsp;
													</td>
													<td class="issueinfo_R">
														<fmt:formatNumber value="${issueData.offsetAmt}" />
														&nbsp;
													</td>
													<td class="issueinfo_R">
														<fmt:formatNumber value="${issueData.aplPayAmt}" />
														&nbsp;
													</td>
												</tr>
											</logic:iterate>
										</c:if>
									</logic:iterate>
								</table>
								<hr size="1" noshade width="98%">
								<table width="98%" cellpadding="1" cellspacing="3" class="px13">
									<tr>
										<td colspan="10" class="issuetitle_L">
											[&nbsp;&nbsp;
											<span class="issuetitle_search">受款人資料</span>&nbsp;&nbsp;]
										</td>
									</tr>
									<tr>
										<td colspan="10">
											<table width="100%" border="0" cellspacing="2"
												cellpadding="1" class="px13">
												<logic:iterate id="beneficiaryData"
													name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_BENEFICIARY_DATA_LIST %>"
													indexId="index">
													<c:if test="${beneficiaryData.seqNo ne '0000'}">
														<tr>
															<td class="issuetitle_L_down" id="iss_b" rowspan="2"
																valign="top" width="6%">
																序：
																<c:out value="${index}" />
															</td>
															<td colspan="3">
																<span class="issuetitle_L_down">姓 名：</span>
																<c:out value="${beneficiaryData.benName}" />
															</td>
														</tr>
														<tr>
															<td id="iss_b" width="29%">
																<span class="issuetitle_L_down">身分證號：</span>
																<c:out value="${beneficiaryData.benIdnNo}" />
															</td>
															<td id="iss_b" width="29%">
																<span class="issuetitle_L_down">關 係：</span>
																<c:out value="${beneficiaryData.benEvtRelStr}" />
															</td>
															<td id="iss_b" width="36%">
																<span class="issuetitle_L_down">電腦編審結果：</span>
																<c:if test="${beneficiaryData.acceptMk eq 'Y'}">
																	<c:out value="Y-合格" />
																</c:if>
																<c:if test="${beneficiaryData.acceptMk eq 'N'}">
																	<c:out value="N-不合格" />
																</c:if>
																<c:if
																	test="${(beneficiaryData.acceptMk ne 'Y')and(beneficiaryData.acceptMk ne 'N')}">
																	<c:out value="待處理" />
																</c:if>
															</td>
														</tr>
													</c:if>
												</logic:iterate>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan="10" class="issuetitle_L">
											篩選條件：給付年月&nbsp;-&nbsp;
											<label>
												<html:select tabindex="70" property="payYmOption"
													styleClass="formtxt" onchange="flashIssuData();">
													<html:option value="ALL">全 選</html:option>
													<html:options
														collection="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_PAYYM_OPTION_LIST%>"
														property="payYm" labelProperty="payYmStr" />
												</html:select>
											</label>
											&nbsp;&nbsp;受款人&nbsp;-&nbsp;
											<label>
												<html:select tabindex="70" property="seqNoOption"
													styleClass="formtxt" onchange="flashIssuData();">
													<html:option value="ALL">全 選</html:option>
													<html:options
														collection="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_SEQNO_OPTION_LIST%>"
														property="seqNo" labelProperty="benName" />
												</html:select>
											</label>
											&nbsp;&nbsp;電腦編審結果&nbsp;-&nbsp;
											<select name="acceptMkOption" class="formtxt"
												onchange="flashIssuData();">
												<option value="ALL" selected>
													全 選
												</option>
												<option value="Y">
													Y-合格
												</option>
												<option value="N">
													N-不合格
												</option>
												<option value=" ">
													待處理
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan="10" width="100%">
											<table width="100%" cellpadding="3" cellspacing="5"
												class="px13">
												<tr align="center">
													<td width="5%" class="issuetitle_L">
														序
													</td>
													<td width="15%" class="issuetitle_L">
														姓 名
													</td>
													<td width="10%" class="issuetitle_L">
														可領金額
													</td>
													<td width="10%" class="issuetitle_L">
														應收金額
													</td>
													<td width="10%" class="issuetitle_L">
														本案沖抵
													</td>
													<td width="10%" class="issuetitle_L">
														扣減金額
													</td>
													<td width="10%" class="issuetitle_L">
														紓困／呆帳
													</td>
													<td width="10%" class="issuetitle_L">
														匯款匯費
													</td>
													<td width="10%" class="issuetitle_L">
														實付金額
													</td>
													<td width="10%" class="issuetitle_L">
														電腦編審結果
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr align="center">
										<td colspan="10">
											<span id="issuDataSpan"></span>
											<tbody id="benIssuDataTable" align="center" width="100%"
												cellpadding="3" cellspacing="5" class="px13">
												<logic:notEmpty
													name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_ISSU_DATA_LIST%>">
													<tr>
														<td width="100%">
															<logic:iterate id="allIssuDataList"
																name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_ISSU_DATA_LIST%>"
																indexId="allIndex">
																<c:if test="${allIndex%2 eq 0}">
																	<table width="100%" cellpadding="3" cellspacing="5"
																		class="px13">
																		<tr>
																			<td class="issuetitle_L" colspan="10">
																				<span class="issuetitle_search">&#9658;</span>&nbsp;給付年月：
																				<c:out value="${allIssuDataList.payYmStr}" />
																				&nbsp;
																			</td>
																		</tr>
																		<logic:iterate id="benIssuData" name="allIssuDataList"
																			property="benIssueDataList" indexId="index">
																			<tr>
																				<td class="issueinfo_C" width="5%">
																					<c:out value="${index+1}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo" width="15%">
																					<c:out value="${benIssuData.benName}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.issueAmt}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.recAmt}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.payBanance}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.otherAmt}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.offsetAmt}" />
																					&nbsp;／&nbsp;
																					<fmt:formatNumber value="${benIssuData.badDebtAmt}" />
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.payRate}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.aplPayAmt}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo" width="10%">
																					<c:if test="${benIssuData.acceptMk eq 'Y'}">
																						<c:out value="Y-合格" />
																					</c:if>
																					<c:if test="${benIssuData.acceptMk eq 'N'}">
																						<c:out value="N-不合格" />
																					</c:if>
																					<c:if
																						test="${(benIssuData.acceptMk ne 'Y')and(benIssuData.acceptMk ne 'N')}">
																						<c:out value="待處理" />
																					</c:if>
																				</td>
																			</tr>
																		</logic:iterate>
																	</table>
																</c:if>
																<c:if test="${allIndex%2 eq 1}">
																	<table width="100%" cellpadding="3" cellspacing="5"
																		class="px13" bgcolor="#E8E8FF">
																		<tr>
																			<td class="issuetitle_L" colspan="10">
																				<span class="issuetitle_search">&#9658;</span>&nbsp;給付年月：
																				<c:out value="${allIssuDataList.payYmStr}" />
																				&nbsp;
																			</td>
																		</tr>
																		<logic:iterate id="benIssuData" name="allIssuDataList"
																			property="benIssueDataList" indexId="index">
																			<tr>
																				<td class="issueinfo_C" width="5%">
																					<c:out value="${index+1}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo" width="15%">
																					<c:out value="${benIssuData.benName}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.issueAmt}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.recAmt}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.payBanance}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.otherAmt}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.offsetAmt}" />
																					&nbsp;／&nbsp;
																					<fmt:formatNumber value="${benIssuData.badDebtAmt}" />
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.payRate}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo_R" width="10%">
																					<fmt:formatNumber value="${benIssuData.aplPayAmt}" />
																					&nbsp;
																				</td>
																				<td class="issueinfo" width="10%">
																					<c:if test="${benIssuData.acceptMk eq 'Y'}">
																						<c:out value="Y-合格" />
																					</c:if>
																					<c:if test="${benIssuData.acceptMk eq 'N'}">
																						<c:out value="N-不合格" />
																					</c:if>
																					<c:if
																						test="${(benIssuData.acceptMk ne 'Y')and(benIssuData.acceptMk ne 'N')}">
																						<c:out value="待處理" />
																					</c:if>
																				</td>
																			</tr>
																		</logic:iterate>
																	</table>
																</c:if>
															</logic:iterate>
														</td>
													</tr>
												</logic:notEmpty>
												<logic:empty
													name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_ISSU_DATA_LIST%>">
													<tr>
														<td width="100%" align="center">
															<font color="red">無資料</font>
														</td>
													</tr>
												</logic:empty>
											</tbody>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="7" align="center" class="table1">
								<table width="98%" cellpadding="3" cellspacing="5" class="px13">
									<tr>
										<td colspan="3">
											<table width="100%" cellpadding="0" cellspacing="0"
												class="px13">
												<td width="88%" class="issuetitle_L">
													<span class="systemMsg">▲</span>&nbsp;行政支援記錄
												</td>
												<td width="12%" id="tabsJ" class="issuetitle_L"
													valign="bottom">
													<a
														href="<c:url value="/executiveSupportClose.do?method=doQuery&qryFromWhere=BACO0D213Q&apNo=${pay.apNo}&issuYm=${pay.issuYmStr}" />"
														target="_self"><span>行政支援銷案</span>
													</a>
												</td>
											</table>
										</td>
									</tr>
									<bean:define id="letterType1List"
										name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_1_LIST %>" />
									<bean:define id="letterType2List"
										name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_2_LIST %>" />
									<bean:define id="letterType3List"
										name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_3_LIST %>" />
									<bean:define id="letterType4List"
										name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_4_LIST %>" />
									<bean:define id="letterType5List"
										name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_5_LIST %>" />
									<bean:define id="letterType6List"
										name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_6_LIST %>" />
									<tr>
										<td id="iss" colspan="2">
											<span class="issuetitle_L_down">交查日期：</span>
											<c:forEach var="detail1List" items="${letterType1List}"
												varStatus="detail1">
												<span title="<c:out value="${detail1List.ndomk1}" />"> <c:choose>
														<c:when test='${detail1.last}'>
															<c:out value="${detail1List.proDate}" />
														</c:when>
														<c:otherwise>
															<c:out value="${detail1List.proDate}," />
														</c:otherwise>
													</c:choose> </span>&nbsp;
                                             </c:forEach>
										</td>
										<td id="iss">
											<span class="issuetitle_L_down">不給付日期：</span>
											<c:forEach var="detail2List" items="${letterType2List}"
												varStatus="detail2">
												<c:if test='${detail2List.ndomkName2 ne "-"}'>
													<span
														title="<c:out value="${detail2List.ndomkName1}" />、<c:out value="${detail2List.ndomkName2}" />">
												</c:if>
												<c:if test='${detail2List.ndomkName2 eq "-"}'>
													<span title="<c:out value="${detail2List.ndomkName1}" />">
												</c:if>
												<c:choose>
													<c:when test='${detail2.last && detail2List.ndomk2 ne ""}'>
														<c:out
															value="${detail2List.ndomk1}、${detail2List.ndomk2}-${detail2List.proDateStr}" />
													</c:when>
													<c:when test='${detail2.last && detail2List.ndomk2 eq ""}'>
														<c:out
															value="${detail2List.ndomk1}-${detail2List.proDateStr}" />
													</c:when>
													<c:otherwise>
														<c:if test='${detail2List.ndomk2 ne ""}'>
															<c:out
																value="${detail2List.ndomk1}、${detail2List.ndomk2}-${detail2List.proDateStr}," />
														</c:if>
														<c:if test='${detail2List.ndomk2 eq ""}'>
															<c:out
																value="${detail2List.ndomk1}-${detail2List.proDateStr}," />
														</c:if>
													</c:otherwise>
												</c:choose>
												</span>&nbsp;
                                             </c:forEach>
										</td>
									</tr>
									<tr>
										<td id="iss" colspan="2">
											<span class="issuetitle_L_down">補件日期：</span>
											<c:forEach var="detail3List" items="${letterType3List}"
												varStatus="detail3">
												<span title="<c:out value="${detail3List.ndomk1}" />"> <c:choose>
														<c:when test='${detail3.last}'>
															<c:out value="${detail3List.proDate}" />
														</c:when>
														<c:otherwise>
															<c:out value="${detail3List.proDate}," />
														</c:otherwise>
													</c:choose> </span>&nbsp;
                                             </c:forEach>
										</td>
										<td id="iss">
											<span class="issuetitle_L_down">照會日期：</span>
											<c:forEach var="detail4List" items="${letterType4List}"
												varStatus="detail4">
												<span title="<c:out value="${detail4List.ndomk1}" />"> <c:choose>
														<c:when test='${detail4.last}'>
															<c:out value="${detail4List.proDate}" />
														</c:when>
														<c:otherwise>
															<c:out value="${detail4List.proDate}," />
														</c:otherwise>
													</c:choose> </span>&nbsp;
                                            </c:forEach>
										</td>
									</tr>
									<tr>
										<td id="iss" colspan="2">
											<span class="issuetitle_L_down">調病歷日期：</span> &nbsp;
										</td>
										<td id="iss" colspan="1">
											<span class="issuetitle_L_down">其他簽函日期：</span>
											<c:forEach var="detail5List" items="${letterType5List}"
												varStatus="detail5">
												<span title="<c:out value="${detail5List.ndomk1}" />"> <c:choose>
														<c:when test='${detail5.last}'>
															<c:out value="${detail5List.proDate}" />
														</c:when>
														<c:otherwise>
															<c:out value="${detail5List.proDate}," />
														</c:otherwise>
													</c:choose> </span>&nbsp;
                                             </c:forEach>
										</td>
									</tr>
									<tr>
										<td id="iss" width="25%">
											<span class="issuetitle_L_down">行政救濟日期：</span>
											<c:out value="${letterType6List.proDateStr}" />
										</td>
										<td id="iss" width="25%">
											<span class="issuetitle_L_down">救濟種類：</span>
											<c:out value="${letterType6List.reliefKind}" />
										</td>
										<td id="iss" width="50%">
											<span class="issuetitle_L_down">行政救濟辦理情形：</span>
											<c:out value="${letterType6List.reliefStat}" />
										</td>
									</tr>
									<tr>
										<td id="iss" colspan="3">
											<span class="issuetitle_L_down">救濟事由：</span>
											<c:out value="${letterType6List.reliefCause}" />
										</td>
									</tr>
									<tr>
										<td colspan="3"></td>
									</tr>
								</table>
							</td>
						</tr>
						<%--
                        <tr>
                            <td colspan="7" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                                    <tr>
                                        <td colspan="4">
                                            <table width="100%" cellpadding="0" cellspacing="0" class="px13">
                                                <td width="85%" class="issuetitle_L">
                                                    <span class="systemMsg">▲</span>&nbsp;總表資料
                                                </td>
                                                <td width="15%" id="tabsJ" class="issuetitle_L" valign="bottom">
                                                    <a href="<c:url value="/survivorPaymentReview.do?method=doReviewRpt" />" onclick="Event.stopObserving(window, 'beforeunload', beforeUnload);"><span>檢視受理/審核清單</span></a>
                                                </td>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">投保薪資：</span>
                                            <fmt:formatNumber value="${pay.lsInsmAmt}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">平均薪資：</span>
                                            <fmt:formatNumber value="${pay.insAvgAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">核定總額：</span>
                                            <fmt:formatNumber value="${pay.befIssueAmt}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">補發總額：</span>
                                            <fmt:formatNumber value="${pay.supAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">紓困總額：</span>
                                            <fmt:formatNumber value="${pay.offsetAmt}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">扣減總額：</span>
                                            <fmt:formatNumber value="${pay.otherAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
                                            <span class="issuetitle_L_down">實付總額：</span>
                                            <fmt:formatNumber value="${pay.aplPayAmt}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">實付年資：</span>
                                            <c:out value="${pay.aplPaySeniY}" />年<c:out value="${pay.aplPaySeniM}" />月
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">投保年資：</span>
                                            <c:out value="${pay.nitrmY}" />年<c:out value="${pay.nitrmM}" />月
                                            (<c:out value="${pay.itrmY}" />年<c:out value="${pay.itrmD}" />日)
                                        </td>
                                    </tr>
                                    
                                    
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">申請單位保險證號：</span>
                                            <c:out value="${pay.apUbno}" />
                                            <c:out value="${pay.apubnock}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">事故發生單位保險證號：</span>
                                            <c:out value="${pay.lsUbno}" />
                                            <c:out value="${pay.lsUbnoCk}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">申請項目：</span>
                                            <c:out value="${pay.apItemStr}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">判決日期：</span>
                                            <c:out value="${disabledData.judgeDateStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">傷病分類：</span>
                                            <c:out value="${disabledData.evTypStr}" />&nbsp;
                                        </td>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">傷病原因：</span>
                                            <c:out value="${disabledData.evCode}" />&nbsp;
                                        </td>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">受傷部位：</span>
                                            <c:out value="${disabledData.criInPartStr}" />&nbsp;
                                        </td>
                                        <td id="iss" width="25%">
                                            <span class="issuetitle_L_down">符合第63條之1第3項：：</span>
                                            <fmt:formatNumber value="${disabledData.famAppMkStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">國際疾病代碼：</span>
                                            <c:out value="${disabledData.criInJnmeStr}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="1">
                                            <span class="issuetitle_L_down">媒介物：</span>
                                            <c:out value="${disabledData.criMedium}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="1">
                                            <span class="issuetitle_L_down">先核普通：</span>
                                            <c:out value="${disabledData.prType}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">基本月給付金額／計算式：</span>
                                            <fmt:formatNumber value="${pay.oldaAmt}" />
                                            &nbsp;／&nbsp;
                                            <c:if test='${(pay.oldAb eq "1")}'>
                                                <c:out value="<%=ConstantKey.BADAPR_OLDAB_STR_1 %>" />
                                            </c:if>
                                            <c:if test='${(pay.oldAb eq "2")}'>
                                                <c:out value="<%=ConstantKey.BADAPR_OLDAB_STR_2 %>" />
                                            </c:if>
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">
                                                <c:if test='${(pay.apItem eq "2")}'>        
                                                            減額期間／比率：
                                                </c:if>
                                                <c:if test='${(pay.apItem ne "2")}'>        
                                                            展延期間／比率：
                                                </c:if>
                                            </span>
                                            <c:out value="${pay.oldRateDateYMStr}" />
                                            &nbsp;／&nbsp;
                                            <c:out value="${pay.oldRate}" />%
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">勞保給付金額：</span>
                                            <fmt:formatNumber value="${pay.oldbAmt}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="2">
                                            <span class="issuetitle_L_down">遺屬加計比例／金額：</span>
                                            <fmt:formatNumber value="${pay.oldRate}" />%
                                            &nbsp;／&nbsp;
                                            <fmt:formatNumber value="${pay.oldRateAmt}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="3">
                                            <span class="issuetitle_L_down">屆齡日期：</span>
                                            <c:out value="${pay.evtExpireDateStr}" />&nbsp;
                                        </td>
                                        <td id="iss" colspan="1">
                                            <span class="issuetitle_L_down">符合日期：</span>
                                            <c:out value="${pay.evtEligibleDateStr}" />&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="4">
                                            <span class="issuetitle_L_down">受理鍵入資料及修改紀錄：</span>
                                            (鍵入/更正人員代號：<c:out value="${pay.crtUser}" />&nbsp;/&nbsp;<c:out value="${pay.updUser}" />)
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                                    <tr>
                                        <td colspan="7" class="issuetitle_L">
                                            <span class="systemMsg">▲</span>&nbsp;編審資料
                                        </td>
                                    </tr>
                                    <tr align="center">
                                        <td colspan="7">
                                            <bean:define id="payList" name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_PAY_DATA_LIST%>" />
                                            <display:table name="pageScope.payList" id="listItem" pagesize="0">
                                                <display:column title="給付年月" style="width:14%; text-align:center;">
                                                    <c:out value="${listItem.payYmStr}" />&nbsp;
                                                </display:column>
                                                <display:column title="核定／物價調整金額" style="width:16%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.issueAmt}" />&nbsp;
                                                </display:column>
                                                <display:column title="應收／沖抵金額" style="width:14%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.recAmt}" />
                                                    &nbsp;／&nbsp;
                                                    <fmt:formatNumber value="${listItem.payBanance}" />
                                                </display:column>
                                                <display:column title="補發金額" style="width:14%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.supAmt}" />&nbsp;
                                                </display:column>
                                                <display:column title="事故者／遺屬扣減" style="width:14%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.otherAmt}" />&nbsp;
                                                </display:column>
                                                <display:column title="紓困金額" style="width:14%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.offsetAmt}" />&nbsp;
                                                </display:column>
                                                <display:column title="實付金額"  style="width:14%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.aplPayAmt}" />&nbsp;
                                                </display:column>
                                            </display:table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="7">
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                                                <tr>
                                                    <td id="iss" valign="top" width="14%">
                                                        <span class="issuetitle_L_down">事故者編審註記：</span>
                                                    </td>
                                                    <td id="iss" valign="top" width="62%">
                                                        <logic:iterate id="chkFileData" name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_EVT_CHK_LIST %>">                                                    
                                                            <c:out value="${chkFileData.issuPayYm}" />：
                                                            <logic:iterate id="chkFile" indexId="i" name="chkFileData" property="chkFileList">
                                                                <span title="<c:out value="${chkFile.chkResult}" />">
                                                                    <c:out value="${chkFile.chkCodePost}"/>
                                                                </span>
                                                                <c:if test="${i+1 ne chkFileData.chkFileDataSize}">
                                                                    <c:out value=",　" />
                                                                </c:if>
                                                            </logic:iterate><br>
                                                        </logic:iterate>
                                                        <logic:empty name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_EVT_CHK_LIST %>">
                                                            &nbsp;
                                                        </logic:empty>
                                                    </td>
                                                    <td id="iss" valign="top" width="24%">
                                                        <c:if test="${detail.loanMk eq '1'}">
                                                            <strong><c:out value="<%=ConstantKey.BAAPPBASE_LOANMK_STR_1 %>" /></strong>
                                                        </c:if>&nbsp;
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="1" valign="top" width="14%">
                                            <span class="issuetitle_L_down">遺屬符合註記：</span>
                                        </td>
                                        <td id="iss" colspan="6" valign="top" width="86%">
                                            <logic:iterate id="masterChkFile" name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_MATCH_CHK_LIST %>">                                                    
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="px13">
                                                    <tr>
                                                        <td valign="top" width="12%" align="left">
                                                            <strong>遺屬序：<c:out value="${masterChkFile.seqNo}" /></strong>
                                                        </td>
                                                        <td valign="top" width="88%" align="left">
                                                            <logic:iterate id="chkFileData" name="masterChkFile" property="otherChkDataList">                                                    
                                                                <c:out value="${chkFileData.issuPayYm}" />：
                                                                <logic:iterate id="chkFile" indexId="i" name="chkFileData" property="chkFileList">
                                                                    <span title="<c:out value="${chkFile.chkResult}" />">
                                                                        <c:out value="${chkFile.chkCodePost}"/>
                                                                    </span>
                                                                    <c:if test="${i+1 ne chkFileData.chkFileDataSize}">
                                                                        <c:out value=",　" />
                                                                    </c:if>
                                                                </logic:iterate><br>
                                                            </logic:iterate>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </logic:iterate>
                                            <logic:empty name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_MATCH_CHK_LIST %>">
                                                &nbsp;
                                            </logic:empty>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="iss" colspan="1" valign="top" width="14%">
                                            <span class="issuetitle_L_down">遺屬編審註記：</span>
                                        </td>
                                        <td id="iss" colspan="6" valign="top" width="86%">
                                            <logic:iterate id="masterChkFile" name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_CHK_LIST %>">                                                    
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="px13">
                                                    <tr>
                                                        <td valign="top" width="12%" align="left">
                                                            <strong>遺屬序：<c:out value="${masterChkFile.seqNo}" /></strong>
                                                        </td>
                                                        <td valign="top" width="88%" align="left">
                                                            <logic:iterate id="chkFileData" name="masterChkFile" property="otherChkDataList">                                                    
                                                                <c:out value="${chkFileData.issuPayYm}" />：
                                                                <logic:iterate id="chkFile" indexId="i" name="chkFileData" property="chkFileList">
                                                                    <span title="<c:out value="${chkFile.chkResult}" />">
                                                                        <c:out value="${chkFile.chkCodePost}"/>
                                                                    </span>
                                                                    <c:if test="${i+1 ne chkFileData.chkFileDataSize}">
                                                                        <c:out value=",　" />
                                                                    </c:if>
                                                                </logic:iterate><br>
                                                            </logic:iterate>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </logic:iterate>
                                            <logic:empty name="<%=ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_CHK_LIST %>">
                                                &nbsp;
                                            </logic:empty>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        --%>
						<tr>
							<td colspan="7">
								&nbsp;
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
