<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M021X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>        
    <script language="javascript" type="text/javascript">
    <%-- 確認按鈕 --%>
    <%-- 按下 確認按鈕時  檢查checkbox是否有勾選的資料 --%>    
    <%-- begin ... [ --%>
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
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/returnWriteOffBJ" method="post" onsubmit="">
        
        <fieldset>
            <legend>&nbsp;收回作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="9" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="idForConfirm" property="idForConfirm" />
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnConfirm" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doBatch'; if(checkFields()){document.ReturnWriteOffBJForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.ReturnWriteOffBJForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>                
                <tr>
                    <td colspan="9" class="issuetitle_L">檔案產生日期起迄：
                        <span class="px13">
                            <c:out value="${ReturnWriteOffBJForm.upTimeBeg}" />~<c:out value="${ReturnWriteOffBJForm.upTimeEnd}" />
                        </span>
                    </td>
                </tr>
                <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.RETURN_WRITE_OFF_BJ_CASE%>" />
                        
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">                            
                            <display:column title="<input type='checkbox' name='chkBatch' onclick='selectAll(this, \"idForConfirm\");'><br>全選" style="width:6%; text-align:center;">
                                <c:if test="${listItem.procStat eq '0'}">
                                    <input type='checkbox' name='idForConfirm' value='<c:out value="${listItem.baBatchRecId}" />;<c:out value="${listItem.fileName}" />' >
                                </c:if>
                                <c:if test="${listItem.procStat ne '0'}">
                                    &nbsp;
                                </c:if>
                            </display:column>
                            <display:column title="處理狀態" style="width:9%; text-align:center;">
                                <c:out value="${listItem.procStatStr}" />&nbsp;
                            </display:column>
                            <display:column title="資料來源" style="width:9%; text-align:center;">
                                <c:out value="${listItem.upOrgan}" />&nbsp;
                            </display:column>
                            <display:column title="筆 數" style="width:6%; text-align:center;">
                                <c:out value="${listItem.dataCount}" />&nbsp;
                            </display:column>
                            <display:column title="開始時間" style="width:7%; text-align:center;">
                                <c:out value="${listItem.stTimeStr}" />&nbsp;
                            </display:column>
                            <display:column title="結束時間" style="width:7%; text-align:center;">
                                <c:out value="${listItem.endTimeStr}" />&nbsp;
                            </display:column>
                            <display:column title="耗費時間" style="width:7%; text-align:center;">
                                <c:out value="${listItem.executeTimeStr}" />&nbsp;
                            </display:column>
                            <display:column title="檔案名稱" style="width:9%; text-align:center;">
                                <c:out value="${listItem.fileName}" />&nbsp;
                            </display:column> 
                            <display:column title="檔案產生時間" style="width:11%; text-align:center;">
                                <c:out value="${listItem.upTimeStr}" />&nbsp;
                            </display:column>
                            <display:column title="處理人員" style="width:8%; text-align:center;">
                                <c:out value="${listItem.procUser}" />&nbsp;
                            </display:column>
                            <display:column title="處理日期" style="width:9%; text-align:center;">
                                <c:out value="${listItem.procTimeStr}" />&nbsp;
                            </display:column>
                            <display:column title="訊息說明" style="width:12%; text-align:center;">
                                <c:out value="${listItem.procMemo}" />&nbsp;
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
