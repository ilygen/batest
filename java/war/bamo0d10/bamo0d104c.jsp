<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D104C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="OldAgePaymentReceiveForm" page="1" /> 
    <script language="javascript" type="text/javascript">
    <!--
    //頁面欄位值初始化       
    function initAll(){        
        
    }      
    
    //checkbox 單選
    function chk(input){
    
      indexs = document.getElementsByName("index");
      for(var i=0;i<indexs.length;i++){
         indexs[i].checked = false;
      }
      input.checked = true;
      return true;
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
        
        if(isChkAnyOne() == true){
        msg+="請勾選「退現已收資料」。\r\n";
        }
        
        if(msg!=""){
            alert(msg);
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
            <html:form action="/oldAgePaymentReceive" method="post" onsubmit="">
                <html:hidden styleId="page" property="page" value="1" />
                <html:hidden styleId="method" property="method" value="" />
                <fieldset>
                    <legend>
                        &nbsp;老年年金應收收回註銷處理&nbsp;
                    </legend>
                    <div align="right" id="showtime">
                        網頁下載時間：民國&nbsp;<func:nowDateTime />
                    </div>
                    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                        <tr>
                            <td align="right" colspan="8">
                                <acl:checkButton buttonName="btnSave">
                                    <input name="btnSave" type="button" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doCancelCashReceive'; if(checkFields()){document.OldAgePaymentReceiveForm.submit();}else{return false;}" />&nbsp;      
	                            </acl:checkButton>
                                &nbsp;
                                <acl:checkButton buttonName="btnBack">
                                    <input name="btnBack"  type="button"  class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.OldAgePaymentReceiveForm.submit();" />&nbsp;
                                </acl:checkButton>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14">
                                <bean:define id="detail" name="<%=ConstantKey.OLDAGE_PAYMENT_RECEIVE_DATA_CASE%>" />
                                <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                    <tr>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">受理編號：</span>
                                            <c:out value="${detail.apNoStrDisplay}" />
                                        </td>                   
                                        <td width="25%">
                                            <span class="issuetitle_L_down">給付別：</span>
                                            <c:out value="${detail.apNoKindStrDisplay}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">&nbsp;</span>
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">收回狀況：</span>
                                            <c:out value="1-現金收回" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">事故者姓名：</span>
                                            <c:out value="${detail.evtName}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">身分證號：</span>
                                            <c:out value="${detail.evtIdnNo}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">出生日期：</span>
                                            <c:out value="${detail.evtBrDateStr}" />
                                        </td>
                                        <td width="25%">
                                            <span class="issuetitle_L_down">事故日期：</span>
                                            <c:out value="${detail.evtJobDateStr}" />
                                        </td>
                                    </tr>
                              </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                                    <tr>
                                        <td colspan="8" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;退現已收資料</td>
                                    </tr>
                                    <tr>
                                        <td colspan="8" align="center">
                                            <bean:define id="list" name="<%=ConstantKey.CASH_RECEIVE_DATA_CASE_LIST%>" />
                                            <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>"> 
                                                <display:column title="選取" headerClass="issuetitle_L" class="issueinfo_C"  style="text-align:center;">
                                                    <html:checkbox styleId="index<%=String.valueOf(listItem_rowNum.intValue()-1)%>" property="index" value="<%=String.valueOf(listItem_rowNum.intValue()-1)%>" onclick="return chk(this);" />&nbsp;
                                                </display:column>
                                                <display:column title="　" headerClass="issuetitle_L" class="issueinfo_C" style="text-align:center;">
                                                    <c:out value="${listItem_rowNum}" />&nbsp;
                                                </display:column>                    
                                                <display:column title="保險別" headerClass="issuetitle_L" class="issueinfo_C" style="text-align:center;">
                                                    <c:out value="${listItem.insKdCash}" />&nbsp;
                                                </display:column>
                                                <display:column title="局帳戶代號" headerClass="issuetitle_L" class="issueinfo_C" style="text-align:center;">
                                                    <c:out value="${listItem.bliAccountCode}" />&nbsp;
                                                </display:column>
                                                <display:column title="入帳方式" headerClass="issuetitle_L" class="issueinfo_C" style="text-align:center;">
                                                    <c:out value="${listItem.bookedBook}" />&nbsp;
                                                </display:column>
                                                <display:column title="銀行入帳日期" headerClass="issuetitle_L" class="issueinfo_C" style="text-align:center;">
                                                    <c:out value="${listItem.bkAccountDt}" />&nbsp;
                                                </display:column>
                                                <display:column title="批號" headerClass="issuetitle_L" class="issueinfo_C" style="text-align:center;">
                                                    <c:out value="${listItem.batchNo}" />&nbsp;
                                                </display:column>
                                                <display:column title="流水號" headerClass="issuetitle_L" class="issueinfo_C" style="text-align:center;">
                                                    <c:out value="${listItem.serialNo}" />&nbsp;
                                                </display:column>
                                                <display:column title="現金金額" headerClass="issuetitle_L" class="issueinfo_C" style="text-align:right;">
                                                    <fmt:formatNumber value="${listItem.cashAmt}" />&nbsp;
                                                </display:column>
                                                <display:column title="分割序號" headerClass="issuetitle_L" class="issueinfo_C" style="text-align:center;">
                                                    <c:out value="${listItem.divSeq}" />&nbsp;
                                                </display:column>
                                                <display:column title="序號" headerClass="issuetitle_L" class="issueinfo_C" style="text-align:center;">
                                                    <c:out value="${listItem.indexNo}" />&nbsp;
                                                </display:column>
                                            </display:table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
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
