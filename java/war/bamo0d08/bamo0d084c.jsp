<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D084C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="PayeeDataUpdateListForm" page="1" />
    <script language="javascript" type="text/javascript">
    // 受款人可領金額
	function checkFields() {
	    var msg = "";
        var befIssueAmt = '<c:out value="${PayeeDataUpdateBadaprCase.befIssueAmt}" />';
	    var mustIssueAmtSum = 0;
        var df = document.forms[0].mustIssueAmt;
        var mx = df.length; 
        
        // 單筆時df.length會undefined無法加總所以例外處理
        if(df.length == undefined){
            mustIssueAmtSum = $("mustIssueAmt").value;
        } else {
            for (var i=0; i<mx; i++) {
                if(isNaN(df[i].value)){
                    msg += '「可領金額」必須為數字，請重新確認。\r\n';
                    break
                } else {
                    if(parseInt(df[i].value) < 0){
                        msg += '「可領金額」必須大於0，請重新確認。\r\n';
                        break
                    }
                    mustIssueAmtSum += parseInt(df[i].value); 
                }
            }
        }
        
        if(befIssueAmt == "")
            befIssueAmt = 0;

        if(parseInt(mustIssueAmtSum) != parseInt(befIssueAmt))
            msg += '「可領金額」的總和需等於「核定金額」。\r\n';

        if (msg != "") {
            alert(msg);
            return false;
        }
        else {
            return true;
        }	    	    
	}
	
    function initAll(){  
    }
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>
    
    <div id="main" class="mainBody">
        <html:form action="/payeeDataUpdateList" method="post" styleId="detailData" onsubmit="return validatePayeeDataUpdateListForm(this);">
        
        <fieldset>
            <legend>&nbsp;老年年金受款人可領金額登錄&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="5">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnSave">
                            <input name="btnSave" type="button" class="button" value="確　定" disabled="disabled" onclick="$('page').value='1'; $('method').value='doSave'; if (document.PayeeDataUpdateListForm.onsubmit() && checkFields()){document.PayeeDataUpdateListForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackList'; document.PayeeDataUpdateListForm.submit();" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="5">
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].apNoStrDisplay}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].payCode}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].appDateChineseString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故日期：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].evtJobDateStr}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].evtName}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].evtBrDateChineseString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].evtIdnNo}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="6" align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                            <tr>
                                <td id="iss" width="33%">
                                    <span class="issuetitle_L_down">投保年資：</span>
                                    <c:out value="${PayeeDataUpdateBadaprCase.nitrmYM}" />
                                </td>
                                <td id="iss" width="33%">
                                    <span class="issuetitle_L_down">老年年資：</span>
                                    <c:out value="${PayeeDataUpdateBadaprCase.noldtYM}" />
                                </td>
                                <td id="iss" width="34%">
                                    <span class="issuetitle_L_down">實發年資：</span>
                                    <c:out value="${PayeeDataUpdateBadaprCase.aplPaySeniYM}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">國保年資：</span>
                                    <c:out value="${PayeeDataUpdateBadaprCase.valseniYM}" />
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">平均月投保薪資(60個月)：</span>
                                    <c:out value="${PayeeDataUpdateBadaprCase.insAvgAmt}" />
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">累計已領年金金額：</span>
                                    <fmt:formatNumber value="${PayeeDataUpdateBadaprCase.annuAmt}" />
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">計算式/金額：</span>
                                    <c:if test='${PayeeDataUpdateBadaprCase.oldab eq "1"}'>
                                                                                                第一式/<fmt:formatNumber value="${PayeeDataUpdateBadaprCase.oldaAmt}" />
                                    </c:if>
                                    <c:if test='${PayeeDataUpdateBadaprCase.oldab eq "2"}'>
                                                                                                第二式/<fmt:formatNumber value="${PayeeDataUpdateBadaprCase.oldbAmt}" />
                                    </c:if>   
                                </td>
                                <td id="iss">
                                    <c:if test='${PayeeDataUpdateBadaprCase.apItem eq "2"}'>
                                        <span class="issuetitle_L_down">減額期間/比率：</span>
                                        <c:out value="${PayeeDataUpdateBadaprCase.oldRateDateString}" /> /
                                        <c:out value="${PayeeDataUpdateBadaprCase.oldRate}" />%
                                    </c:if>
                                    <c:if test='${PayeeDataUpdateBadaprCase.apItem eq "1" or PayeeDataUpdateBadaprCase.apItem eq "3"}'>
                                        <span class="issuetitle_L_down">展延期間/比率：</span>
                                        <c:out value="${PayeeDataUpdateBadaprCase.oldRateSdateString}" /> ~
                                        <c:out value="${PayeeDataUpdateBadaprCase.oldRateEdateString}" /> /
                                        <c:out value="${PayeeDataUpdateBadaprCase.oldRate}" />%
                                    </c:if>
                                    <c:if test='${empty PayeeDataUpdateBadaprCase.apItem}'>
                                    &nbsp;
                                    </c:if>
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">核定金額：</span>
                                    <fmt:formatNumber value="${PayeeDataUpdateBadaprCase.befIssueAmt}" />
                                    <!--  <span class="issuetitle_L_down">核定年月：</span>
                                    <c:out value="${PayeeDataUpdateBadaprCase.issuYmStr}" />-->
                                </td>
                            </tr>
                            <tr>
                	            <td colspan="3"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
          	        <td colspan="6">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="3">
                        <bean:define id="list" name="<%=ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE%>" />
                    
                        <display:table name="pageScope.list" id="listItem"  >
                        <display:column title="序號" headerClass="issuetitle_L" class="issueinfo_C" style="width:7%; text-align:center;">
                            <c:out value="${listItem_rowNum}" />
    	                </display:column>
                        <display:column property="benName" headerClass="issuetitle_L" class="issueinfo" title="受款人姓名" style="width:30%; text-align:left;" />
                        <display:column property="benIdnNo" headerClass="issuetitle_L" class="issueinfo" title="受款人身分證號" style="width:8%; text-align:left;" />
                        <display:column property="benEvtRelName" headerClass="issuetitle_L" class="issueinfo" title="關 係" style="width:15%; text-align:left;" />
                        <display:column headerClass="issuetitle_L" class="issueinfo_R" title="匯款匯費" style="width:15%; text-align:right;" >
                        	<fmt:formatNumber value="${listItem.mitRate}" />
                        </display:column>
                        <display:column headerClass="issuetitle_L" class="issueinfo_R" title="受款人可領金額" style="width:15%; text-align:center;" >
                            <c:if test='${listItem.benEvtRel eq "1" or listItem.benEvtRel eq "2" or listItem.benEvtRel eq "3" or listItem.benEvtRel eq "4" or listItem.benEvtRel eq "5" or listItem.benEvtRel eq "6" or listItem.benEvtRel eq "7" or listItem.benEvtRel eq "O"}'>
                                <input name="mustIssueAmt" type="text" style="text-align:right" class="textinput" value="<fmt:formatNumber value="${listItem.mustIssueAmt}" />" onblur="this.value=asc(this.value);" disabled="disabled">
                            </c:if>
                            <c:if test='${listItem.benEvtRel eq "A" or listItem.benEvtRel eq "C" or listItem.benEvtRel eq "E" or listItem.benEvtRel eq "F" or listItem.benEvtRel eq "N" or listItem.benEvtRel eq "Z"}'>
                                <input name="mustIssueAmt" type="hidden" style="text-align:right" class="textinput"  value="<fmt:formatNumber value="${listItem.mustIssueAmt}" />">
                                <fmt:formatNumber value="${listItem.mustIssueAmt}" />
                            </c:if>
                        </display:column>        
                        </display:table>
                    </td>
                </tr>
                <tr>
          	        <td colspan="5">&nbsp;</td>
                </tr>
            </table>
        </fieldset>
    
    </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>