<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BARC0D111A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
          
    }    
    //checkBox 單選
    function chooseOne(checkBox){ 
        //先取得同name的chekcBox的集合物件 
        var obj = document.getElementsByName(checkBox.name);    
        for (i=0; i<obj.length; i++){ 
        //判斷obj集合中的i元素是否為cb，若否則表示未被點選 
        if (obj[i]!=checkBox) obj[i].checked = false; 
        //若是 但原先未被勾選 則變成勾選；反之 則變為未勾選 
        else  obj[i].checked = checkBox.checked;          
      } 
    } 
    // Checkbox 全選 / 全部取消 的處理
    // begin ... [
    function selectAllPlus(obj, elements) {
        if (typeof obj == 'string')
            obj = document.getElementById(obj);
    
        if (typeof elements == 'string')
            element = document.getElementsByName(elements);
    
        for (i = 0; i < element.length; i++) {
            if(element[i].disabled == false){
                element[i].checked = obj.checked;                       
                if(obj.checked==true){
                    objs = document.getElementsByName(element[i].name);
                    for (j = 0; j < objs.length; j++) {
                        if (objs[j]!=element[i]) {                    
                            objs[j].checked = false; 
                        }                
                    }
                }
            }
        }        
    }
    <%-- 確認按鈕 --%>
    <%-- 按下 確認按鈕時  檢查checkbox是否有勾選的資料 --%>
    <%-- begin ... [ --%>
    function checkFields() {
        var bClicked = false;        
        var reChkObjs = document.getElementsByName("reChkCheckbox");
        var exeMkObjs = document.getElementsByName("exeMkCheckbox");
        var refundObjs = document.getElementsByName("refundCheckbox");
        apNoOfConfirm = new Array(0);                
                
        for (i = 0; i < reChkObjs.length; i++) {
            if (reChkObjs[i].checked) {
                bClicked = true;
                temp = new Array(1)
                temp[0] = reChkObjs[i].value;
                apNoOfConfirm = apNoOfConfirm.concat(temp);
            }         
        }               
        for (i = 0; i < exeMkObjs.length; i++) {
            if (exeMkObjs[i].checked) {
                bClicked = true;
                temp = new Array(1)
                temp[0] = exeMkObjs[i].value;
                apNoOfConfirm = apNoOfConfirm.concat(temp);
            }         
        }               
        for (i = 0; i < refundObjs.length; i++) {
            if (refundObjs[i].checked) {
                bClicked = true;
                temp = new Array(1)
                temp[0] = refundObjs[i].value;
                apNoOfConfirm = apNoOfConfirm.concat(temp);
            }         
        }
        $("apNoOfConfirm").value=apNoOfConfirm;
        
        if(bClicked == false){
            alert("請勾選要確認的資料");
            return false;           
        }else if(apNoOfConfirm == ""){
            alert("沒有可確認的資料");
            return false;  
        }else{
            return bClicked;
        }                               
    }
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/disabledPaymentDecision" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="apNo" property="apNo" value="" />
        <html:hidden styleId="apNoOfConfirm" property="apNoOfConfirm" value="" />
        <bean:define id="qryCond" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_QUERY_FORM %>" />
        <fieldset>
            <legend>&nbsp;失能年金給付決行作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnConfirm" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doSaveData'; if (checkFields()){document.DisabledPaymentDecisionForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.DisabledPaymentDecisionForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>    
                <tr>
                    <td colspan="2" class="issuetitle_L">
                        <bean:define id="payTitle" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_CASE_FOR_TITLE%>" />                        
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:if test='${(payTitle.pagePayKind eq "L")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                    </c:if>
                                    <c:if test='${(payTitle.pagePayKind eq "K")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                    </c:if>
                                    <c:if test='${(payTitle.pagePayKind eq "S")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                    </c:if>
                                </td>
                                <td width="25%">
                                    <c:if test="${qryCond.qryCond eq '2'}">
                                        <span class="issuetitle_L_down">清單種類：</span>審核給付清單
                                    </c:if>
                                </td>
                                <td width="25%">
                                    <c:if test="${qryCond.qryCond eq '2'}">
                                        <span class="issuetitle_L_down">清單列印日期：</span>
                                        <c:out value="${payTitle.rptDateStr}" />         
                                    </c:if>
                                </td>
                                <td width="25%">
                                    <c:if test="${qryCond.qryCond eq '2'}">
                                        <span class="issuetitle_L_down">頁　次：</span>
                                        <fmt:formatNumber value="${payTitle.pageNo}" />
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">審核日期：</span>
                                    <c:out value="${payTitle.chkDateStr}" />            
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">審核人員：</span>
                                    <c:out value="${payTitle.chkMan}" />            
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">核定金額合計：</span>
                                    <fmt:formatNumber value="${payTitle.tissueAmt}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">實付金額合計：</span>
                                    <fmt:formatNumber value="${payTitle.taplPayAmt}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td colspan="2">
                        <bean:define id="list" name="<%=ConstantKey.DISABLED_PAYMENT_DECISION_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.DECISION_LIST_PAGE_SIZE%>">
                            <display:column title="<input type='checkbox' name='radioAll' onclick='chooseOne(this);selectAllPlus(this, \"reChkCheckbox\");'>全選<br>複 核" style="width:6%; text-align:center;">                                
                                <c:if test="${listItem.isShowReChk eq 'Y'}">
                                    <c:if test="${listItem.reBox eq 'Y'}">
                                        <input type="checkbox" id="reChkCheckbox" name='checkbox<c:out value="${listItem_rowNum}" />' value="<c:out value="${listItem.apNo};${listItem.balp0d020Id}" />;30" onclick='chooseOne(this);' checked="true">
                                    </c:if>
                                    <c:if test="${listItem.reBox ne'Y'}">
                                        <input type="checkbox" id="reChkCheckbox" name='checkbox<c:out value="${listItem_rowNum}" />' value="<c:out value="${listItem.apNo};${listItem.balp0d020Id}" />;30" onclick='chooseOne(this);'>                                    
                                    </c:if>
                                </c:if>
                                <c:if test="${listItem.isShowReChk eq 'N'}">
                                    <input type="checkbox" id="reChkCheckbox" name='checkbox<c:out value="${listItem_rowNum}" />' value="<c:out value="${listItem.apNo};${listItem.balp0d020Id}" />;30" disabled="disabled">
                                </c:if>                                
                            </display:column>
                            <display:column title="<input type='checkbox' name='radioAll' onclick='chooseOne(this);selectAllPlus(this, \"exeMkCheckbox\");'>全選<br>決 行" style="width:6%; text-align:center;">
                                <c:if test="${listItem.isShowExeMk eq 'Y'}">
                                    <c:if test="${listItem.exeBox eq 'Y'}">
                                        <input type="checkbox" id="exeMkCheckbox" name='checkbox<c:out value="${listItem_rowNum}" />' value="<c:out value="${listItem.apNo}" />;<c:out value="${listItem.balp0d020Id}" />;40" onclick='chooseOne(this);' checked="true">
                                    </c:if>
                                    <c:if test="${listItem.exeBox ne 'Y'}">
                                        <input type="checkbox" id="exeMkCheckbox" name='checkbox<c:out value="${listItem_rowNum}" />' value="<c:out value="${listItem.apNo}" />;<c:out value="${listItem.balp0d020Id}" />;40" onclick='chooseOne(this);'>                                    
                                    </c:if>
                                </c:if>
                                <c:if test="${listItem.isShowExeMk eq 'N'}">
                                    <input type="checkbox" id="exeMkCheckbox" name='checkbox<c:out value="${listItem_rowNum}" />' value="<c:out value="${listItem.apNo}" />;<c:out value="${listItem.balp0d020Id}" />;40" disabled="disabled">
                                </c:if>                                                                
                            </display:column>
                            <display:column title="<input type='checkbox' name='radioAll' onclick='chooseOne(this);selectAllPlus(this, \"refundCheckbox\");'>全選<br>退 件" style="width:6%; text-align:center;">
                                <input type="checkbox" id="refundCheckbox" name='checkbox<c:out value="${listItem_rowNum}" />' value="<c:out value="${listItem.apNo}" />;<c:out value="${listItem.balp0d020Id}" />;11" onclick='chooseOne(this);'>                                
                            </display:column>                            
                            <display:column title="受理編號" style="width:12%; text-align:center;">
                                <c:out value="${listItem.apNoStrDisplay}" />&nbsp;
                            </display:column>
                            <display:column title="事故者姓名" style="width:14%; text-align:left;">
                                <c:out value="${listItem.evtName}" />&nbsp;
                            </display:column>
                            <display:column title="事故者<br>身分證號" style="width:7%; text-align:left;">
                                <c:out value="${listItem.evtIdnNo}" />&nbsp;
                            </display:column>
                            <display:column title="給付年月<br>起迄" style="width:6%; text-align:center;">
                                <c:if test="${listItem.paysYmStr eq listItem.payeYmStr}">
                                    <c:out value="${listItem.paysYmStr}" />
                                </c:if>
                                <c:if test="${listItem.paysYmStr ne listItem.payeYmStr}">
                                    <c:out value="${listItem.paysYmStr}" />
                                    <br>
                                    <c:out value="${listItem.payeYmStr}" />
                                </c:if>
                            </display:column>
                            <display:column title="核定金額<br>實付金額" style="width:7%; text-align:right;">
                                <c:if test="${listItem.tissueAmt eq listItem.taplPayAmt}">
                                    <fmt:formatNumber value="${listItem.tissueAmt}" />
                                </c:if>
                                <c:if test="${listItem.tissueAmt ne listItem.taplPayAmt}">
                                    <fmt:formatNumber value="${listItem.tissueAmt}" />
                                    <br>
                                    <fmt:formatNumber value="${listItem.taplPayAmt}" />
                                </c:if>
                            </display:column>
                            <display:column title="<span class='needtxt'>人工審<br>核註記</span>" style="width:6%; text-align:center;">
                                <span class='needtxt'><c:out value="${listItem.manchkMk}" /></span>&nbsp;
                            </display:column>
                            <display:column title="版次" style="width:4%; text-align:center;">
                                <c:out value="${listItem.veriSeq}" />&nbsp;
                            </display:column>
                            <display:column title="資料別" style="width:8%; text-align:left;">
                                <c:if test='${(listItem.caseTyp eq "1")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_1%>" />
                                </c:if>
                                <c:if test='${(listItem.caseTyp eq "2")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_2%>" />
                                </c:if>
                                <c:if test='${(listItem.caseTyp eq "3")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_3%>" />
                                </c:if>
                                <c:if test='${(listItem.caseTyp eq "4")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_4%>" />
                                </c:if>
                                <c:if test='${(listItem.caseTyp eq "5")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_5%>" />
                                </c:if>
                                <c:if test='${(listItem.caseTyp eq "6")}'>
                                    <c:out value="<%=ConstantKey.BAAPPBASE_CASETYP_STR_6%>" />
                                </c:if>&nbsp;
                            </display:column>
                            <display:column title="處理<br>狀態" style="width:10%; text-align:left;">
                                <c:out value="${listItem.procStatStr}" />&nbsp;
                            </display:column>
                            <display:column title="資料明細" style="width:6%; text-align:center;">
                                <acl:checkButton buttonName="btnDetail">
                                    <input type="button" name="btnDetail" class="button" value="明細資料" onclick="$('page').value='1'; $('method').value='doSingleReview'; $('apNo').value='<c:out value="${listItem.apNo}" />'; document.DisabledPaymentDecisionForm.submit();" />&nbsp;
                                </acl:checkButton>                            
                            </display:column>
                        </display:table>
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
