<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D091C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="OldAgeDeathRepayForm" page="1" /> 
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
        $("chkAllIdx").checked=false;
        indexs = document.getElementsByName("index");
        //帶入勾選選項
        indexString = '<c:out value="${CheckOldAgeDeathRepayDataCase.indexString}" />';
        indexList = indexString.split("");
        for(var i=0; i<indexList.length; i++){
           index = indexList[i];
           indexs[index].checked=true;
        }
    }      
    //勾選查詢項目
    function chkIndex(obj){
        indexs = document.getElementsByName("index");
        
        if(obj.name == "chkAllIdx"){
            for(var i=0; i<indexs.length; i++){
                indexs[i].checked=obj.checked;
            }
        }
        else{
            var isAllChecked = true;
            for(var i=0; i<indexs.length; i++){
                if(indexs[i].checked==false){
                    isAllChecked = false;
                    break;
                }
            }
            
            if(isAllChecked==true){
                $("chkAllIdx").checked=true;
            }else{
                $("chkAllIdx").checked=false; 
            }
        }
    }
    
    function isChkAnyOne(){
    
    var isChkAnyOne = false;
        indexs = document.getElementsByName("index");
        for(var i=0; i<indexs.length; i++){
            if(indexs[i].checked==true){
                isChkAnyOne = true;
                break;
            }
        }
        if(isChkAnyOne == false){
            return true
        }else{
            return false
        }
    }
    
    function checkFields(){
        var msg="";
        if(msg!=""){
            return false;
        }else{
            return true;
        }
    }
    
    //列印報表時關掉鎖定畫面
    function closeBeforeunload(){
        Event.stopObserving(window, 'beforeunload', beforeUnload);
    }
    
    
    Event.observe(window, 'beforeunload', beforeUnload);
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no">

    <div id="mainContent">

        <%@ include file="/includes/ba_header.jsp"%>

        <%@ include file="/includes/ba_menu.jsp"%>

        <div id="main" class="mainBody">
            <html:form action="/oldAgeDeathRepay" method="post" onsubmit="">
                <html:hidden styleId="page" property="page" value="1" />
                <html:hidden styleId="method" property="method" value="" />
                <fieldset>
                    <legend>
                        &nbsp;老年年金受款人死亡改匯處理&nbsp;
                    </legend>
                    <div align="right" id="showtime">
                        網頁下載時間：民國&nbsp;<func:nowDateTime />
                    </div>
                    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                        <tr>
                            <td align="right" colspan="8">
                            <bean:define id="qryForm" name="<%=ConstantKey.OLDAGE_DEATH_REPAY_FORM%>" />
                                <acl:checkButton buttonName="btnSave">
                                       <c:choose>
                                           <c:when test='${qryForm.checkQryData4 eq "1"}'>
                                               <input name="btnSave" type="button" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doConfirm'; if(isChkAnyOne()){if(confirm('是否取消改匯處理作業？')){document.OldAgeDeathRepayForm.submit();}}else{document.OldAgeDeathRepayForm.submit();}" />&nbsp;
                                            </c:when>
                                            <c:otherwise>
                                               <input name="btnSave" type="button" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doConfirm'; document.OldAgeDeathRepayForm.submit();" />&nbsp;
                                           </c:otherwise>
                                       </c:choose>
	                            </acl:checkButton>
	                            &nbsp;
	                            <acl:checkButton buttonName="btnUpdate">
                                    <input name="btnUpdate" type="button"  class="button_90" value="繼承人維護" onclick="$('page').value='1'; $('method').value='doUpdateQuery'; document.OldAgeDeathRepayForm.submit();"/>&nbsp;
                                </acl:checkButton>
	                            &nbsp;
	                            <acl:checkButton buttonName="btnPrint">
                                    <input name="btnPrint" type="button"  class="button_90" value="改匯通知書" onclick="closeBeforeunload();$('page').value='1'; $('method').value='doReport'; document.OldAgeDeathRepayForm.submit();"/>&nbsp;
                                </acl:checkButton>
                                &nbsp;
                                <acl:checkButton buttonName="btnBack">
                                    <input name="btnBack"  type="button"  class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.OldAgeDeathRepayForm.submit();" />&nbsp;
                                </acl:checkButton>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14">
                                <bean:define id="detail" name="<%=ConstantKey.CHECK_OLDAGE_DEATH_REPAY_DATA_CASE%>" />
                                <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                    <tr>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受理編號：</span>
                                            <c:out value="${detail.apNoStrDisplay}" />
                                        </td>                   
                                        <td width="25%">
                                            <span class="issuetitle_L_down">給付別：</span>
                                            <c:out value="${detail.payCode}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受款人序：</span>
                                            <c:out value="${detail.seqNo}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受款人死亡日期：</span>
                                            <c:out value="${detail.benDieDate}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受款人姓名：</span>
                                            <c:out value="${detail.benName}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">&nbsp;</span>
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受款人身分證號：</span>
                                            <c:out value="${detail.benIdnNo}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受款人出生日期：</span>
                                            <c:out value="${detail.benBrDate}" />
                                        </td>
                                    </tr>
                              </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
                                        <td colspan="8" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;受款人退匯資料</td>
                                    </tr>
                                    <tr>
                                        <td colspan="8" align="center">
                                            <bean:define id="list" name="<%=ConstantKey.OLDAGE_DEATH_REPAY_DATA_CASE_LIST%>" />
                                            <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>"> 
                                                <display:column title="序 號" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:center;">
                                                    <c:out value="${listItem_rowNum}" />&nbsp;
                                                </display:column>                   
                                                <display:column title="核定年月" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:center;">
                                                    <c:out value="${listItem.oriIssuYm}" />&nbsp;
                                                </display:column>                   
                                                <display:column title="給付年月" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:center;">
                                                    <c:out value="${listItem.payYm}" />&nbsp;
                                                </display:column>
                                                <display:column title="退匯日期" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:center;">
                                                    <c:out value="${listItem.brChkDate}" />&nbsp;
                                                </display:column>
                                                <display:column title="退匯金額" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.remitAmt}" />
                                                </display:column>
                                            </display:table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
                                        <td colspan="8" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;繼承人資料</td>
                                    </tr>
                                    <tr>
                                        <td colspan="8" align="center">
                                            <bean:define id="list" name="<%=ConstantKey.HEIR_SEQNO_EXIST_DATA_CASE_LIST%>" />
                                            <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>"> 
                                                <display:column title="&nbsp;選取&nbsp;<input type='checkbox' name='chkAllIdx' onclick='chkIndex(this)'>(全選)" style="width:10%; text-align:center;">
                                                    <html:checkbox styleId="index<%=String.valueOf(listItem_rowNum.intValue()-1)%>" property="index" value="<%=String.valueOf(listItem_rowNum.intValue()-1)%>" onclick="chkIndex(this);"/>&nbsp;
                                                </display:column>                   
                                                <display:column title="受款人序" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:center;">
                                                    <c:out value="${listItem.seqNo}" />&nbsp;
                                                </display:column>                   
                                                <display:column title="受款人姓名" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:center;">
                                                    <c:out value="${listItem.benName}" />&nbsp;
                                                </display:column>
                                                <display:column title="受款人身分證號" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:left;">
                                                    <c:out value="${listItem.benIdnNo}" />&nbsp;
                                                </display:column>
                                                <display:column title="關係" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:left;">
                                                    <c:out value="${listItem.benEvtRelName}" />
                                                </display:column>
                                            </display:table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <logic:notEmpty name="<%=ConstantKey.REPAY_ISSUEAMT_DATA_CASE_LIST%>">
                        <tr>
                            <td colspan="14" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
                                        <td colspan="8" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;退匯金額分配資料</td>
                                    </tr>
                                    <tr>
                                        <td colspan="8" align="center">
                                            <bean:define id="list" name="<%=ConstantKey.REPAY_ISSUEAMT_DATA_CASE_LIST%>" />
                                            <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">               
                                                <display:column title="受款人序" headerClass="issuetitle_L" class="issueinfo_C" style="width:15%; text-align:center;">
                                                    <c:out value="${listItem.seqNo}" />&nbsp;
                                                </display:column>                   
                                                <display:column title="核定年月" headerClass="issuetitle_L" class="issueinfo_C" style="width:15%; text-align:center;">
                                                    <c:out value="${listItem.issuYm}" />&nbsp;
                                                </display:column>
                                                <display:column title="給付年月" headerClass="issuetitle_L" class="issueinfo_C" style="width:15%; text-align:center;">
                                                    <c:out value="${listItem.payYm}" />&nbsp;
                                                </display:column>
                                                <display:column title="受款人姓名" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:left;">
                                                    <c:out value="${listItem.benName}" />
                                                </display:column>
                                                <display:column title="分配後改匯金額" headerClass="issuetitle_L" class="issueinfo_C" style="width:20%; text-align:right;">
                                                    <fmt:formatNumber value="${listItem.issueAmt}" />&nbsp;
                                                </display:column>   
                                                <display:column title="改匯方式" headerClass="issuetitle_L" class="issueinfo_C" style="width:15%; text-align:left;">
                                                    <c:out value="${listItem.payTyp}-${listItem.payTypStr}" />&nbsp;
                                                </display:column>   
                                            </display:table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        
                        
                        </logic:notEmpty>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                </fieldset>
            </html:form>
        </div>
    </div>

    <%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
