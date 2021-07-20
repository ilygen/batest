<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M012X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>  
    <script type='text/javascript' src='<c:url value="/js/jquery-1.8.0.min.js"/>'></script> 
    <html:javascript formName="UpdatePaidMarkBJDetailForm" page="1" /> 
     
    <script language="javascript" type="text/javascript">
    //jQuery 使caption標籤 置左
    jQuery.noConflict();
    jQuery(document).ready(function() {
    
    jQuery("caption").addClass("issuetitle_L");
    jQuery("caption").css("text-align","left");
    
    }); 
    
    var procStat = '<c:out value="${UpdatePaidMarkBJDetailForm.procStat}" />';
    var procFlag = '<c:out value="${UpdatePaidMarkBJDetailForm.procFlag}" />';
    <%-- begin 檢查必填欄位 --%>
    function checkFields() {
        var msg = "";
            
        if(Trim(procStat) == "3" && Trim(procFlag) == "0"){
            if(Trim($("procFlag").value) == "0")
                msg += '「回押註記」：為必輸欄位。\r\n';
            if(Trim($("procFlag").value) == "2" && Trim($("procMemo").value) == "")
                msg += '「回押註記備註」：為必輸欄位。\r\n';
        }    
                    
        if (msg != "") {
            alert(msg);
            return false;
        }
        else {
            return true;
        }      
    }
    <%-- ] ... end --%>
    
     // 變更 給付方式(本人領取) 時畫面異動  
    function changeProcFlag(){      
        if($("procFlag").value == "1"){
            // 回押註記備註
            $("procMemo").disabled = true;                                                                            
        } else {                       
            // 回押註記備註
            $("procMemo").disabled = false;                                     
        }         
    }
    
    <%-- 畫面清空重設 --%>
    <%-- begin ... [ --%>
    function resetForm(){
        document.PayeeDataUpdateDetailForm.reset();
        initAll();
    }
    <%-- ] ... end --%>
    
    function initAll(){
        
        // 回押註記備註
        $("procMemo").disabled = true;
        
        if(Trim(procStat) == "3" && Trim(procFlag) == "0"){
            //預設radio為 UpdatePaidMarkBJDetailForm.procFlag
            $("procFlag").value = Trim(procFlag);
            // 確認按鈕
            $("btnSave").disabled = false;
        } else {
            // 確認按鈕
            $("btnSave").disabled = true;
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
        <html:form action="/updatePaidMarkBJDetail" method="post" onsubmit="return validateUpdatePaidMarkBJDetailForm(this);">
        
        <fieldset>
            <legend>&nbsp;給付媒體回押註記&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td width="100%" align="right" colspan="2">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnSave">
                            <input type="button" name="btnSave" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doSave'; if(document.UpdatePaidMarkBJDetailForm.onsubmit() && checkFields()){document.UpdatePaidMarkBJDetailForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.UpdatePaidMarkBJDetailForm.submit();" />&nbsp;
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td>
            	        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="33%"><span class="issuetitle_L_down">出帳類別：</span><c:out value="${UpdatePaidMarkBJDetailForm.taTyp}" /></td>
                                <td width="33%"><span class="issuetitle_L_down">出帳日期：</span><c:out value="${UpdatePaidMarkBJDetailForm.payDate}" /></td>
                                <td width="34%"><span class="issuetitle_L_down">檔案名稱：</span><c:out value="${UpdatePaidMarkBJDetailForm.fileName}" /></td>
                            </tr>
            	        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1">
            	        <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                            <tr>
                	            <td colspan="3" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;媒體檔案資訊</td>
                            </tr>
                            <tr>
                                <td id="iss" width="33%"><span class="issuetitle_L_down">發件單位：</span><c:out value="${UpdatePaidMarkBJDetailForm.sunit}" /></td>
                                <td id="iss" width="33%"><span class="issuetitle_L_down">檔案產生時間：</span><c:out value="${UpdatePaidMarkBJDetailForm.upTime}" /></td>
                                <td id="iss" width="34%"><span class="issuetitle_L_down">給付別：</span><c:out value="${UpdatePaidMarkBJDetailForm.payCodeStr}" /></td>
                            </tr>
                            <tr>
                                <td id="iss"><span class="issuetitle_L_down">資料筆數：</span><c:out value="${UpdatePaidMarkBJDetailForm.dataCount}" /></td>
                                <td id="iss" colspan="2"><span class="issuetitle_L_down">總金額：</span><c:out value="${UpdatePaidMarkBJDetailForm.amount}" /></td>
                            </tr>
                            <tr>
                                <td id="iss" width="33%"><span class="issuetitle_L_down">資料來源類別：</span><c:out value="${UpdatePaidMarkBJDetailForm.upTyp}" /></td>
                                <td id="iss" width="33%"><span class="issuetitle_L_down">資料來源：</span><c:out value="${UpdatePaidMarkBJDetailForm.upOrgan}" /></td>
                                <td id="iss" width="34%"><span class="issuetitle_L_down">資料來源單位ID：</span><c:out value="${UpdatePaidMarkBJDetailForm.upOrganId}" /></td>
                            </tr>
                            <tr>
                                <td id="iss"><span class="issuetitle_L_down">處理狀態：</span><c:out value="${UpdatePaidMarkBJDetailCase.procStatStr}" /></td>
                                <td id="iss"><span class="issuetitle_L_down">轉入開始時間：</span><c:out value="${UpdatePaidMarkBJDetailForm.stTime}" />&nbsp;</td>
                                <td id="iss"><span class="issuetitle_L_down">轉入結束時間：</span><c:out value="${UpdatePaidMarkBJDetailForm.endTime}" />&nbsp;</td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">回押註記：</span>
                                    <c:if test='${UpdatePaidMarkBJDetailForm.procStat eq "3" and UpdatePaidMarkBJDetailForm.procFlag eq "0"}'>
                                        <!--<html:radio property="procFlag" value="1" onclick="$('procFlag').value='1'; changeProcFlag();"/>是-->
                                        <!--<html:radio property="procFlag" value="2" onclick="$('procFlag').value='2'; changeProcFlag();"/>否-->
                                        <input type=radio name="procFlag" id="procFlag" value="1" onclick="$('procFlag').value='1'; changeProcFlag();"/>是
                                        <input type=radio name="procFlag" id="procFlag" value="2" onclick="$('procFlag').value='2'; changeProcFlag();"/>否
                                    </c:if>
                                    <c:if test='${UpdatePaidMarkBJDetailForm.procFlag eq "1"}'>
                                                                                                           是
                                    </c:if>
                                    <c:if test='${UpdatePaidMarkBJDetailForm.procFlag eq "2"}'>
                                                                                                           否
                                    </c:if>
                                </td>
                                <td id="iss"><span class="issuetitle_L_down">回押註記說明：</span>
                                    <c:if test='${UpdatePaidMarkBJDetailForm.procFlag eq "0"}'>
                                        <%=ConstantKey.BABATCHREC_PROCFLAG_STR_0 %>
                                    </c:if>
                                    <c:if test='${UpdatePaidMarkBJDetailForm.procFlag eq "1"}'>
                                        <%=ConstantKey.BABATCHREC_PROCFLAG_STR_1 %>
                                    </c:if>
                                    <c:if test='${UpdatePaidMarkBJDetailForm.procFlag eq "2"}'>
                                        <%=ConstantKey.BABATCHREC_PROCFLAG_STR_2 %>
                                    </c:if>
                                    <c:if test='${UpdatePaidMarkBJDetailForm.procFlag eq "3"}'>
                                        <%=ConstantKey.BABATCHREC_PROCFLAG_STR_3 %>
                                    </c:if>
                                    <c:if test='${UpdatePaidMarkBJDetailForm.procFlag eq "4"}'>
                                        <%=ConstantKey.BABATCHREC_PROCFLAG_STR_4 %>
                                    </c:if>
                                    &nbsp;
                                </td>
                                <td id="iss"><span class="issuetitle_L_down">回押註記人員：</span><c:out value="${UpdatePaidMarkBJDetailForm.procUser}" />&nbsp;</td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">回押註記備註：</span>
                                    <html:text property="procMemo" styleId="procMemo" styleClass="textinput" size="50" maxlength="50" />
                                </td>
                                <td id="iss"><span class="issuetitle_L_down">回押註記日期：</span><c:out value="${UpdatePaidMarkBJDetailForm.procTime}" /></td>
                            </tr>
                            <tr>
                                <td colspan="3"></td>
                            </tr>
            	        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1">
            	        <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                            <tr>
                	            <td colspan="2" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;給付媒體檔案資料比對結果</td>
                            </tr>
                            <tr>
                                <td id="iss" width="50%"><span class="issuetitle_L_down">出帳資料總筆數：</span><c:out value="${UpdatePaidMarkBJDetailCase2.payCount1Str}" /></td>
                                <td id="iss" width="50%"><span class="issuetitle_L_down">出帳資料總金額：</span><c:out value="${UpdatePaidMarkBJDetailCase2.paySum1Str}" /></td>
                            </tr>
                            <tr>
                                <td id="iss"><span class="issuetitle_L_down">媒體回覆總筆數：</span><c:out value="${UpdatePaidMarkBJDetailCase2.payCount2Str}" /></td>
                                <td id="iss"><span class="issuetitle_L_down">媒體回覆總金額：</span><c:out value="${UpdatePaidMarkBJDetailCase2.paySum2Str}" /></td>  
                            </tr>    
                            <tr>
                                <td id="iss"><span class="issuetitle_L_down">比對異常總筆數：</span><c:out value="${UpdatePaidMarkBJDetailCase2.payCount3Str}" /></td>
                                <td id="iss"><span class="issuetitle_L_down">比對異常總金額：</span><c:out value="${UpdatePaidMarkBJDetailCase2.paySum3Str}" /></td>
                            </tr>
                            <tr>
                                <td id="iss"><span class="issuetitle_L_down">無回覆記錄筆數：</span><c:out value="${UpdatePaidMarkBJDetailCase2.payCount4Str}" /></td>
                                <td id="iss"><span class="issuetitle_L_down">無回覆記錄金額：</span><c:out value="${UpdatePaidMarkBJDetailCase2.paySum4Str}" /></td>
                            </tr>
                            <tr>
                                <td id="iss"><span class="issuetitle_L_down">無出帳記錄筆數：</span><c:out value="${UpdatePaidMarkBJDetailCase2.payCount5Str}" /></td>
                                <td id="iss"><span class="issuetitle_L_down">無出帳記錄金額：</span><c:out value="${UpdatePaidMarkBJDetailCase2.paySum5Str}" /></td>
                            </tr>
                            <tr>
                                <td colspan="2"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1">
                        <bean:define id="list" name="<%=ConstantKey.UPDATE_PAID_MARK_BJ_DETAIL_CASE_3%>" />
                    
                        <display:table name="pageScope.list" id="listItem" class="px13">
                        <tr>
                        <display:caption><span class="systemMsg">　▲</span>&nbsp;媒體轉入狀況資訊</display:caption>
                        </tr>
                        <display:column style="width:50%; text-align:left;" class="issuetitle_L_down">
                            <c:out value="　${listItem.stat2Str}" />筆數：<c:out value="${listItem.statCountStr}" />
                        </display:column>
                        <display:column style="width:50%; text-align:left;" class="issuetitle_L_down">
                            <c:out value="${listItem.stat2Str}" />金額：<c:out value="${listItem.statAmtStr}" />
                        </display:column>        
                        </display:table>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"></td>
                </tr>
            </table>
      </fieldset>        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>