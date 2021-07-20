<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BALP0D671L" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
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
    
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no" >

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/printOtherRpt08" method="post" onsubmit="return validateOtherRpt08Form(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
       	<html:hidden styleId="apNoOfConfirm" property="apNoOfConfirm" />
        <fieldset>
            <legend>&nbsp;轉呆帳核定清單維護作業 &nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                	<td colspan="3" align="right">
                     	<acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnConfirm" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doSaveDataForAll'; if (checkFields()){document.OtherRpt08Form.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; javascript:history.back();"  />&nbsp;
                        </acl:checkButton>
                     </td>
                </tr>                
                <bean:define id="qryForm" name="<%=ConstantKey.OTHER_RPT08_FORM%>" />
                <tr>
                    <td width="30%"  class="issuetitle_L">
                          <c:out value="給付別："/>                         
                          <span class="px13">
                           <c:if test="${qryForm.payCode eq 'L'}">
                             <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                           </c:if>
						   <c:if test="${qryForm.payCode eq 'K'}">
                            <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                           </c:if>
                           <c:if test="${qryForm.payCode eq 'S'}">
                            <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                           </c:if>                            
                         </span>
                    </td>
                    <td width="35%" class="issuetitle_L">
                          <c:out value="呆帳年度："/>                         
                          <span class="px13">
                            <c:out value="${qryForm.deadYy}"/>
                         </span>
                    </td>
                    <td width="35%" class="issuetitle_L">
                          <c:out value="受理編號："/>                         
                          <span class="px13">
                            <c:out value="${qryForm.apNo1}${qryForm.apNo2}${qryForm.apNo3}${qryForm.apNo4}"/>
                         </span>
                    </td>
                </tr>
                
                <tr align="center">                   
                    <td colspan="4">
                        <bean:define id="list" name="<%=ConstantKey.OTHER_RPT08_DETAIL_CASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">                            
                            <display:column title="<input type='checkbox' name='chkMaintian' onclick='selectAll(this, \"indexOfConfirm\");'>全選" style="width:7%; text-align:center;">
                                <input type='checkbox' name='indexOfConfirm' value='<%=(listItem_rowNum.intValue()-1)%>'>
                            </display:column>
                        
	                        <display:column title="受款人序號" style="width:14%; text-align:center;">
	                            <c:out value="${listItem.apNo}" />&nbsp;
	                        </display:column>
	                        <display:column title="受款人姓名" style="width:14%; text-align:center;">
	                            <c:out value="${listItem.benName}" />&nbsp;
	                        </display:column>
	                        <display:column title="受款人身分證號" style="width:13%; text-align:center;">
	                            <c:out value="${listItem.benIdnNo}" />&nbsp;
	                        </display:column>
	                        <display:column title="核定年月" style="width:13%; text-align:center;">
	                            <c:out value="${listItem.issuYm}" />&nbsp;
	                        </display:column>
	                        <display:column title="給付年月" style="width:13%; text-align:center;">
	                            <c:out value="${listItem.payYm}" />&nbsp;
	                        </display:column>
	                        <display:column title="應收金額" style="width:13%; text-align:center;">
	                            <c:out value="${listItem.recAmt}" />&nbsp;
	                        </display:column>
	                        <display:column title="未收金額" style="width:13%; text-align:center;">
	                            <c:out value="${listItem.recRem}" />&nbsp;
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
