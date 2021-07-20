<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M211c" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>      
    <script language="javascript" type="text/javascript">
    
    //頁面欄位值初始化       
    function initAll(){
    
         // 設定進頁面時的checkBox狀態        
         var objs = document.getElementsByName("indexOfConfirm");
         checkBoxIndex = ($("confirmMkOfConfirm").value).split(",");
         for(i = 0 ; i < checkBoxIndex.length ; i++){
             objs[checkBoxIndex[i]].checked = true;
         }
    }    
    
    <%-- 確認按鈕 --%>
    <%-- 按下 確認按鈕時  檢查checkbox是否有勾選的資料 --%>    
    <%-- begin ... [ --%>
    function checkFields() {
        var bClicked = true;
        var objs = document.getElementsByName("indexOfConfirm");
        $("confirmMkOfConfirm").value="";
        indexOfConfirm = new Array(0);
        
        for (i = 0; i < objs.length; i++) {
            if (objs[i].checked == true) {
                bClicked = true;
                temp = new Array(1)
                temp[0] = objs[i].value;
                indexOfConfirm = indexOfConfirm.concat(temp);
            }
        }
        $("confirmMkOfConfirm").value=indexOfConfirm;

        // 不是必填 不做勾選判斷 只回傳勾選資料
        //if(bClicked == false){
        //    alert("請勾選要確認的資料");
        //    return false;           
        //}else if(indexOfConfirm == ""){
        //    alert("沒有可確認的資料");
        //    return false;  
        //}else{
        return bClicked;
        //}  

    }
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/checkJob" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="confirmMkOfConfirm" property="confirmMkOfConfirm" />
        
        <fieldset>
            <legend>&nbsp;檢核確認作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnConfirm" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doConfirm'; if (checkFields()){document.CheckJobForm.submit();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.CheckJobForm.submit();" />&nbsp;
                        </acl:checkButton>                                             
                    </td>
                </tr>    
                <tr align="center">
                    <td>
                        <bean:define id="list" name="<%=ConstantKey.CHECK_JOB_CASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                            <display:column title="序 號" headerClass="issuetitle_L" class="issueinfo_C" style="width:7%; text-align:center;">
                                <c:out value="${listItem_rowNum}" />&nbsp;
                            </display:column>
                            <display:column title="給付別" style="width:9%; text-align:center;">
                                <c:out value="${listItem.payCodeStr}" />&nbsp;
                            </display:column>
                            <display:column title="核定年月" style="width:9%; text-align:center;">
                                <c:out value="${listItem.issuYm}" />&nbsp;
                            </display:column> 
                            <display:column title="<span class='needtxt'>＊</span>檢核確認註記" style="width:7%; text-align:center;">
                                <input type='checkbox' name='indexOfConfirm' value='<%=(listItem_rowNum.intValue()-1)%>'>
                                &nbsp;
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
