<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M102A" />
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
    
    <%-- 確認按鈕 --%>
    <%-- 按下 確認按鈕時  檢查checkbox是否有勾選的資料 --%>    
    <%-- begin ... [ --%>
    function checkFields() {
        var bClicked = false;
        var objs = document.getElementsByName("indexOfConfirm");
        $("apNoOfConfirm").value="";
        indexOfConfirm = new Array(0);
        
        for (i = 0; i < objs.length; i++) {
            if (objs[i].checked == true) {
                bClicked = true;
                temp = new Array(1)
                temp[0] = objs[i].value;
                indexOfConfirm = indexOfConfirm.concat(temp);
            }
        }
        $("apNoOfConfirm").value=indexOfConfirm;
        
        // 調整前一核定年月    
        var objsBfMonth = document.getElementsByName("indexOfBfMonth");
        $("bfMonthOfConfirm").value="";
        indexOfBfMonth = new Array(0);
        
        for (i = 0; i < objsBfMonth.length; i++) {
            if (objsBfMonth[i].checked == true) {
                temp = new Array(1)
                temp[0] = objsBfMonth[i].value;
                indexOfBfMonth = indexOfBfMonth.concat(temp);
            }
        }
        $("bfMonthOfConfirm").value=indexOfBfMonth;
        
        if(bClicked == false){
            alert("請勾選要確認的資料");
            return false;           
        }else if(indexOfConfirm == ""){
            alert("沒有可確認的資料");
            return false;  
        }else{
            return bClicked;
        }  
    }
    
    <%-- 刪除按鈕 --%>
    <%-- 按下 確認按鈕時  檢查checkbox是否有勾選的資料 --%>    
    <%-- begin ... [ --%>
    function checkDeleteFields() {
        var bClicked = false;
        var objs = document.getElementsByName("indexOfConfirm");
        $("apNoOfConfirm").value="";
        indexOfConfirm = new Array(0);
        
        for (i = 0; i < objs.length; i++) {
            if (objs[i].checked == true) {
                bClicked = true;
                temp = new Array(1)
                temp[0] = objs[i].value;
                indexOfConfirm = indexOfConfirm.concat(temp);
            }
        }
        $("apNoOfConfirm").value=indexOfConfirm;
        
        // 調整前一核定年月    
        var objsBfMonth = document.getElementsByName("indexOfBfMonth");
        $("bfMonthOfConfirm").value="";
        indexOfBfMonth = new Array(0);
        
        for (i = 0; i < objsBfMonth.length; i++) {
            if (objsBfMonth[i].checked == true) {
                temp = new Array(1)
                temp[0] = objsBfMonth[i].value;
                indexOfBfMonth = indexOfBfMonth.concat(temp);
            }
        }
        $("bfMonthOfConfirm").value=indexOfBfMonth;
        
        if(bClicked == false){
            alert("請勾選要刪除的資料");
            return false;           
        }else if(indexOfConfirm == ""){
            alert("沒有可刪除的資料");
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
        <html:form action="/monthCheckK" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="apNoOfConfirm" property="apNoOfConfirm" />
        <html:hidden styleId="bfMonthOfConfirm" property="bfMonthOfConfirm" />
        
        <fieldset>
            <legend>&nbsp;失能年金線上月試核作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnConfirm" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doConfirmList'; if (checkFields()){document.MonthCheckKForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnDelete">
                            <input type="button" name="btnDelete" class="button" value="刪　除" onclick="$('page').value='1'; $('method').value='doDelete'; if (checkDeleteFields()){document.MonthCheckKForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.MonthCheckKForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>    
                <tr>
                    <td colspan="11" class="issuetitle_L">
                        <bean:define id="payTitle" name="<%=ConstantKey.MONTH_QUERY_CASE_FOR_TITLE%>" />                        
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="100%">
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
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.MONTH_CHECK_K_CASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                            <display:column title="<input type='checkbox' name='chkReview' onclick='selectAll(this, \"indexOfConfirm\");'>全選<br>編　審" style="width:7%; text-align:center;">
                                <input type='checkbox' name='indexOfConfirm' value='<%=(listItem_rowNum.intValue()-1)%>'>
                                &nbsp;
                            </display:column>
                             <display:column title="<input type='checkbox' name='chkBfMonth' onclick='selectAll(this, \"indexOfBfMonth\");'>全選<br>調整前一核定年月" style="width:9%; text-align:center;">
                                <c:if test='${(listItem.adjIssuYmMk eq "Y")}'>
                                    <input type='checkbox' name='indexOfBfMonth' value='<%=(listItem_rowNum.intValue()-1)%>' checked="true" >
                                </c:if>
                                <c:if test='${(listItem.adjIssuYmMk ne "Y")}'>
                                    <input type='checkbox' name='indexOfBfMonth' value='<%=(listItem_rowNum.intValue()-1)%>'>
                                </c:if>
                                &nbsp;
                            </display:column>
                            <display:column title="受理編號" style="width:12%; text-align:center;">
                                <c:out value="${listItem.apNoStrDisplay}" />&nbsp;
                            </display:column>
                            <display:column title="事故者姓名" style="width:9%; text-align:left;">
                                <c:out value="${listItem.evtName}" />&nbsp;
                            </display:column>
                            <display:column title="事故者身分證號" style="width:9%; text-align:left;">
                                <c:out value="${listItem.evtIdnNo}" />&nbsp;
                            </display:column>
                            <display:column title="核定年月" style="width:9%; text-align:center;">
                               <c:out value="${listItem.issuYmStr}" />&nbsp;
                            </display:column>
                            <display:column title="案件類別" style="width:6%; text-align:left;">
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
                                </c:if>&nbsp;
                            </display:column>
                            <display:column title="處理狀態" style="width:9%; text-align:center;">
                               <c:out value="${listItem.procStatString}" />&nbsp;
                            </display:column>
                            <display:column title='<span class="needtxt">人工審核結果</span>' style="width:9%; text-align:left;">
                                <font color="red">
                                <c:if test="${listItem.manchkMk eq 'Y'}">
                                    <c:out value="Y-合格" />
                                </c:if>
                                <c:if test="${listItem.manchkMk eq 'N'}">
                                    <c:out value="N-不合格" />
                                </c:if>
                                <c:if test="${(listItem.manchkMk ne 'Y')and(listItem.manchkMk ne 'N')}">
                                    <c:out value="待處理" />
                                </c:if>
                                </font>&nbsp;
                            </display:column>  
                            <display:column title='<span class="needtxt">線上作業狀態</span>' style="width:9%; text-align:left;">
                                <font color="red">
                                <c:if test="${(listItem.procType eq '1') and (listItem.status eq '0')}">
                                    <c:out value="線上月試核執行結束" />
                                </c:if>
                                <c:if test="${(listItem.procType eq '1') and (listItem.status eq '1')}">
                                    <c:out value="線上月試核錯誤" />
                                </c:if>
                                <c:if test="${(listItem.procType eq '2') and (listItem.status eq '0')}">
                                    <c:out value="線上月核定執行結束" />
                                </c:if>
                                <c:if test="${(listItem.procType eq '2') and (listItem.status eq '1')}">
                                    <c:out value="線上月核定錯誤" />
                                </c:if>
                                </font>
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
