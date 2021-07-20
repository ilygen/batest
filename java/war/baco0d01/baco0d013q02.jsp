<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BACO0D013Q02" />
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
       
    function checkFields() {
        var msg = "";        
        
        if($("notifyForm").value==""){
            msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.review.notifyForm")%>' />\r\n"
        }
        if($("chgNote").value==""){
            msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.review.chgNote")%>' />\r\n"
        }
        
        if(msg != ""){
            alert(msg);                                                                       
            return false;
        }else{
            var objs = document.getElementsByName("manchkMkRadio"); 
            manchkMk = new Array(0)
            for (i = 0; i < objs.length; i++) {
                if (objs[i].checked) {
                    temp = new Array(1)
                    temp[0] = objs[i].value;
                    manchkMk = manchkMk.concat(temp);
                }
            }
            $("manchkMk").value=manchkMk;            
            return true;
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
        <html:form action="/paymentReview" method="post" onsubmit="">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="manchkMk" property="manchkMk" />
        
        <fieldset>
            <legend>&nbsp;審核清單明細資料&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right" colspan="7">                       
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackMaster'; document.PaymentReviewForm.submit();" />&nbsp;
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td>
                        <bean:define id="pay" name="<%=ConstantKey.PAYMENT_REVIEW_CASE%>" />                        
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${pay.apNoStrDisplay}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${pay.appDateStr}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">版　次：</span>
                                    <c:out value="${pay.veriSeq}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${pay.evtName}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${pay.evtIdnNo}" />
                                </td>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${pay.evtBrDateStr}" />
                                </td>
                            </tr>
                            <tr>
                                <td height="27">
                                    <span class="issuetitle_L_down">事故者年齡：</span>
                                    <c:out value="${pay.evtAge}" />&nbsp;歲
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者性別：</span>
                                    <c:if test='${(pay.evtSex eq "1")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_1%>" />
                                    </c:if>
                                    <c:if test='${(pay.evtSex eq "2")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_2%>" />
                                    </c:if>&nbsp;
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">首次給付年月：</span>
                                    <c:out value="${pay.payYmsStr}" />
                                    <c:if test='${(pay.payYmsStr ne "")or(pay.payYmeStr ne "")}'>
                                        &nbsp;~&nbsp;
                                    </c:if>
                                    <c:out value="${pay.payYmeStr}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">核定年月：</span>
                                    <c:out value="${pay.issuYmStr}" />&nbsp;
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">給付年月起迄：</span>
                                    <c:out value="${pay.minPayYmStr}" />
                                    <c:if test='${(pay.minPayYmStr ne "")or(pay.maxPayYmStr ne "")}'>
                                        &nbsp;~&nbsp;
                                    </c:if>
                                    <c:out value="${pay.maxPayYmStr}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">核定格式：</span>
                                    <c:out value="${pay.notifyForm}" />&nbsp;
                                </td>                                
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table border="0" cellpadding="0" cellspacing="0" class="issuetitle_L" id="tabsJ">
                            <tr>
                                <td>
                                    <%-- 
                                    <a href='<c:url value="/paymentReview.do?method=reviewMasterData" />' title="連結至：總表資料" target="_self"><span>總表資料</span></a>
                                    <h5><a>事故者／受款人資料</a></h5>
                                    <a href='<c:url value="/paymentReview.do?method=reviewIssuData" />' title="連結至：核定資料" target="_self"><span>核定資料</span></a>
                                    <a href='<c:url value="/paymentReview.do?method=reviewSeniorityData" />' title="連結至：年資資料" target="_self"><span>年資資料</span></a>
                                    <a href='<c:url value="/paymentReview.do?method=reviewApplyData" />' title="連結至：請領同類/他類資料" target="_self"><span>請領同類／他類／另案扣減</span></a>
                                    --%> 
                                    <a href='<c:url value="/paymentReview.do?method=reviewMasterData" />' target="_self"><span>總表資料</span></a>
                                    <h5><a>事故者／受款人資料</a></h5>
                                    <a href='<c:url value="/paymentReview.do?method=reviewIssuData" />' target="_self"><span>核定資料</span></a>
                                    <a href='<c:url value="/paymentReview.do?method=reviewSeniorityData" />' target="_self"><span>年資資料</span></a>
                                    <a href='<c:url value="/paymentReview.do?method=reviewApplyData" />' target="_self"><span>請領同類／他類／另案扣減</span></a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>                
                <logic:iterate id="benDetailData" indexId="benDataIndex" name="<%=ConstantKey.PAYMENT_REVIEW_BENEFICIARY_DATA_LIST%>">
                <tr>
                    <td colspan="3" align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                            <c:if test="${benDataIndex eq '0'}">                                
                                <tr>
                                    <td colspan="3" class="issuetitle_L">
                                        <span class="systemMsg">▲</span>&nbsp;事故者資料                                    
                                    </td>
                                </tr>
                                <tr>                                    
                                    <td colspan="3" id="iss">
                                        <span class="issuetitle_L_down">事故者姓名：</span>
                                        <c:out value="${benDetailData.benName}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss" >
                                        <span class="issuetitle_L_down">事故者身分證號：</span>
                                        <c:out value="${benDetailData.benIdnNo}" />
                                    </td>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">事故者出生日期：</span>
                                        <c:out value="${benDetailData.benBrDateStr}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss" width="32%">
                                        <span class="issuetitle_L_down">性別：</span>
                                        <c:if test="${benDetailData.benSex eq '1'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_1 %>" />                                    
                                        </c:if>
                                        <c:if test="${benDetailData.benSex eq '2'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_2 %>" />                                    
                                        </c:if>
                                    </td>
                                    <td id="iss" width="32%">
                                        <span class="issuetitle_L_down">關係：</span>
                                        <c:if test="${benDetailData.benEvtRel eq '1'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '2'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '3'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '4'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '5'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '6'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '7'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'A'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'C'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'F'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'N'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'Z'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'O'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O %>" />
                                        </c:if>
                                    </td>
                                    <td id="iss" width="36%">
                                        <span class="issuetitle_L_down">申請日期：</span>
                                        <c:out value="${benDetailData.benEvtAppDateStr}" />&nbsp;
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${benDataIndex ne '0'}">
                                <tr>
                                    <td colspan="3" class="issuetitle_L">
                                        <span class="systemMsg">▲</span>&nbsp;受款人資料                                    
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">                                
                                        <span class="issuetitle_L_down">受款人序：</span>
                                        <c:out value="${benDataIndex}" />
                                    </td>
                                    <td colspan="2" id="iss">
                                        <span class="issuetitle_L_down">受款人姓名：</span>
                                        <c:out value="${benDetailData.benName}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">受款人身分證號：</span>
                                        <c:out value="${benDetailData.benIdnNo}" />
                                    </td>
                                    <td id="iss">
                                        <c:if test="${(benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')or(benDetailData.benEvtRel eq 'E')}">
                                            <span class="issuetitle_L_down">受款人出生日期：</span>
                                            <c:out value="${benDetailData.benBrDateStr}" />
                                        </c:if>
                                        <c:if test="${(benDetailData.benEvtRel ne '1')and(benDetailData.benEvtRel ne '2')and(benDetailData.benEvtRel ne '3')and(benDetailData.benEvtRel ne '4')and(benDetailData.benEvtRel ne '5')and(benDetailData.benEvtRel ne '6')and(benDetailData.benEvtRel ne '7')and(benDetailData.benEvtRel ne 'E')}">
                                            &nbsp;
                                        </c:if>
                                    </td>
                                    <td id="iss">
                                        <c:if test="${((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')or(benDetailData.benEvtRel eq 'E'))and(benDetailData.benNationTyp eq '2')}">
                                            <span class="issuetitle_L_down">性別：</span>
                                            <c:if test="${benDetailData.benSex eq '1'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_1 %>" />                                    
                                            </c:if>
                                            <c:if test="${benDetailData.benSex eq '2'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_2 %>" />                                    
                                            </c:if>
                                        </c:if>
                                        <c:if test="${((benDetailData.benEvtRel ne '1')and(benDetailData.benEvtRel ne '2')and(benDetailData.benEvtRel ne '3')and(benDetailData.benEvtRel ne '4')and(benDetailData.benEvtRel ne '5')and(benDetailData.benEvtRel ne '6')and(benDetailData.benEvtRel ne '7')and(benDetailData.benEvtRel ne 'E'))or(benDetailData.benNationTyp ne '2')}">
                                            &nbsp;
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="32%" id="iss">
                                        <span class="issuetitle_L_down">關係：</span>
                                        <c:if test="${benDetailData.benEvtRel eq '1'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '2'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '3'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '4'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '5'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '6'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq '7'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7 %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'A'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'C'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'F'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'N'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'Z'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z %>" />
                                        </c:if>
                                        <c:if test="${benDetailData.benEvtRel eq 'O'}">
                                            <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O %>" />
                                        </c:if>
                                    </td>
                                    <td width="32%" id="iss">
                                        <c:if test="${(benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')}">
                                            <span class="issuetitle_L_down">繼承人申請日期：</span>
                                            <c:out value="${benDetailData.benEvtAppDateStr}" />&nbsp;
                                        </c:if>
                                        <c:if test="${(benDetailData.benEvtRel ne '1')and(benDetailData.benEvtRel ne '2')and(benDetailData.benEvtRel ne '3')and(benDetailData.benEvtRel ne '4')and(benDetailData.benEvtRel ne '5')and(benDetailData.benEvtRel ne '6')and(benDetailData.benEvtRel ne '7')}">
                                            &nbsp;
                                        </c:if>
                                    </td>
                                    <td width="36%" id="iss">
                                        <c:if test="${(benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')}">
                                            <span class="issuetitle_L_down">繼承自受款人：</span>
                                            <c:out value="${benDetailData.benEvtName}" />&nbsp;
                                        </c:if>
                                        <c:if test="${(benDetailData.benEvtRel ne '1')and(benDetailData.benEvtRel ne '2')and(benDetailData.benEvtRel ne '3')and(benDetailData.benEvtRel ne '4')and(benDetailData.benEvtRel ne '5')and(benDetailData.benEvtRel ne '6')and(benDetailData.benEvtRel ne '7')}">
                                            &nbsp;
                                        </c:if>
                                    </td>
                                </tr>
                            </c:if>                           
                            <c:if test="${(benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')or(benDetailData.benEvtRel eq 'E')}">
                            <tr>
                                <td id="iss">
                                    <c:if test="${(benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')or(benDetailData.benEvtRel eq 'E')}">
                                        <span class="issuetitle_L_down">國籍別：</span>
                                        <c:if test="${benDetailData.benNationTyp eq '1'}">
                                            <c:out value="本國" />
                                        </c:if>
                                        <c:if test="${benDetailData.benNationTyp eq '2'}">
                                            <c:out value="外籍" />
                                        </c:if>&nbsp;
                                    </c:if>
                                    <c:if test="${(benDetailData.benEvtRel ne '1')and(benDetailData.benEvtRel ne '2')and(benDetailData.benEvtRel ne '3')and(benDetailData.benEvtRel ne '4')and(benDetailData.benEvtRel ne '5')and(benDetailData.benEvtRel ne '6')and(benDetailData.benEvtRel ne '7')and(benDetailData.benEvtRel ne 'E')}">
                                        &nbsp;
                                    </c:if>
                                </td>
                                <td id="iss">
                                    <c:if test="${((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')or(benDetailData.benEvtRel eq 'E'))and(benDetailData.benNationTyp eq '2')}">
                                        <span class="issuetitle_L_down">國籍：</span>
                                        <c:out value="${benDetailData.benNationCode}" />&nbsp;
                                        <c:out value="${benDetailData.benNationName}" />
                                    </c:if>
                                    <c:if test="${((benDetailData.benEvtRel ne '1')and(benDetailData.benEvtRel ne '2')and(benDetailData.benEvtRel ne '3')and(benDetailData.benEvtRel ne '4')and(benDetailData.benEvtRel ne '5')and(benDetailData.benEvtRel ne '6')and(benDetailData.benEvtRel ne '7')and(benDetailData.benEvtRel ne 'E'))or(benDetailData.benNationTyp ne '2')}">
                                        &nbsp;
                                    </c:if>
                                </td>
                                <td id="iss">
                                    <c:if test="${((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7'))and(benDetailData.benDataQ1 ge '1')}">
                                        <span class="issuetitle_L_down">身分查核年月：</span>
                                        <c:out value="${benDetailData.idnChkYmStr}" />&nbsp;
                                    </c:if>
                                    <c:if test="${((benDetailData.benEvtRel ne '1')and(benDetailData.benEvtRel ne '2')and(benDetailData.benEvtRel ne '3')and(benDetailData.benEvtRel ne '4')and(benDetailData.benEvtRel ne '5')and(benDetailData.benEvtRel ne '6')and(benDetailData.benEvtRel ne '7'))or(benDetailData.benDataQ1 lt '1')}">
                                        &nbsp;
                                    </c:if>
                                </td>
                            </tr>
                            </c:if>
                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">電話1：</span>
                                    <c:out value="${benDetailData.tel1}" />
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">電話2：</span>
                                    <c:out value="${benDetailData.tel2}" />&nbsp;
                                </td>
                                <td id="iss">
                                    <span class="issuetitle_L_down">地址：</span>
                                    <c:if test="${benDetailData.commTyp eq '1'}">
                                        <c:out value="同戶籍地" />&nbsp;-&nbsp;<c:out value="${benDetailData.commZip}" />&nbsp;<c:out value="${benDetailData.commAddr}" />
                                    </c:if>
                                    <c:if test="${benDetailData.commTyp eq '2'}">
                                        <c:out value="現住址" />&nbsp;-&nbsp;<c:out value="${benDetailData.commZip}" />&nbsp;<c:out value="${benDetailData.commAddr}" />
                                    </c:if>
                                </td>
                            </tr>
                            <c:if test="${((benDetailData.accRel eq '1')or(benDetailData.accRel eq '2'))or(((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7'))and(benDetailData.benDataQ1 ge '1'))or(((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')or(benDetailData.benEvtRel eq 'A')or(benDetailData.benEvtRel eq 'C')or(benDetailData.benEvtRel eq 'E')or(benDetailData.benEvtRel eq 'Z'))and((benDetailData.payTyp eq '1')or(benDetailData.payTyp eq '2')or(benDetailData.payTyp eq '5')or(benDetailData.payTyp eq '6')or(benDetailData.payTyp eq '7')or(benDetailData.payTyp eq '8')))}">
                                <tr>
                                    <td id="iss">
                                        <c:if test="${(benDetailData.accRel eq '1')or(benDetailData.accRel eq '2')}">
                                            <span class="issuetitle_L_down">給付方式：</span>                                    
                                            <c:out value="本人領取" />&nbsp;-&nbsp;
                                            <c:if test="${benDetailData.payTyp eq '1'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_PAYTYP_STR_1 %>" />
                                            </c:if>
                                            <c:if test="${benDetailData.payTyp eq '2'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_PAYTYP_STR_2 %>" />
                                            </c:if>
                                            <c:if test="${benDetailData.payTyp eq '3'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_PAYTYP_STR_3 %>" />
                                            </c:if>
                                            <c:if test="${benDetailData.payTyp eq '4'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_PAYTYP_STR_4 %>" />
                                            </c:if>
                                            <c:if test="${benDetailData.payTyp eq '5'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_PAYTYP_STR_5 %>" />
                                            </c:if>
                                            <c:if test="${benDetailData.payTyp eq '6'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_PAYTYP_STR_6 %>" />
                                            </c:if>
                                            <c:if test="${benDetailData.payTyp eq '7'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_PAYTYP_STR_7 %>" />
                                            </c:if>
                                            <c:if test="${benDetailData.payTyp eq '8'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_PAYTYP_STR_8 %>" />
                                            </c:if>
                                            <c:if test="${benDetailData.payTyp eq '9'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_PAYTYP_STR_9 %>" />
                                            </c:if>
                                            <c:if test="${benDetailData.payTyp eq 'A'}">
                                                <c:out value="<%=ConstantKey.BAAPPBASE_PAYTYP_STR_A %>" />
                                            </c:if>
                                        </c:if>
                                        <c:if test="${benDetailData.accRel eq '3'}">
                                            <c:if test="${((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7'))and(benDetailData.benDataQ1 ge '1')}">
                                                <span class="issuetitle_L_down">給付方式：</span>                                    
                                                <c:out value="具名領取" />&nbsp;-&nbsp;<c:out value="${benDetailData.accName}" />
                                            </c:if>                                        
                                        </c:if>&nbsp;                                    
                                    </td>
                                    <td id="iss" colspan="2">
                                        <c:if test="${((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')or(benDetailData.benEvtRel eq 'A')or(benDetailData.benEvtRel eq 'C')or(benDetailData.benEvtRel eq 'E')or(benDetailData.benEvtRel eq 'Z'))and((benDetailData.payTyp eq '1')or(benDetailData.payTyp eq '2')or(benDetailData.payTyp eq '5')or(benDetailData.payTyp eq '6')or(benDetailData.payTyp eq '7')or(benDetailData.payTyp eq '8'))}">
                                            <span class="issuetitle_L_down">帳號：</span>
                                            <c:if test="${(benDetailData.accRel eq '1')or(benDetailData.accRel eq '2')}">
                                                <c:out value="${benDetailData.payBankId}" />
                                                <c:out value="${benDetailData.branchId}" />&nbsp;-&nbsp;
                                                <c:out value="${benDetailData.payEeacc}" />
                                                
                                                <c:if test="${((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')or(benDetailData.benEvtRel eq 'A')or(benDetailData.benEvtRel eq 'C')or(benDetailData.benEvtRel eq 'E')or(benDetailData.benEvtRel eq 'Z'))and((benDetailData.payTyp eq '5')or(benDetailData.payTyp eq '6'))}">
                                                    &nbsp;(&nbsp;<c:out value="${benDetailData.bankName}" />&nbsp;)
                                                </c:if>
                                            </c:if>
                                            <c:if test="${benDetailData.accRel eq '3'}">
                                                &nbsp;
                                            </c:if>
                                        </c:if>&nbsp;
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${(((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7'))and(benDetailData.benDataQ1 ge '1'))or(((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')or(benDetailData.benEvtRel eq 'A')or(benDetailData.benEvtRel eq 'C')or(benDetailData.benEvtRel eq 'E')or(benDetailData.benEvtRel eq 'Z'))and((benDetailData.payTyp eq '1')or(benDetailData.payTyp eq '2')or(benDetailData.payTyp eq '5')or(benDetailData.payTyp eq '6')or(benDetailData.payTyp eq '7')or(benDetailData.payTyp eq '8')))}">
                                <tr>
                                    <td colspan="3" id="iss">
                                        <c:if test="${((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')or(benDetailData.benEvtRel eq 'A')or(benDetailData.benEvtRel eq 'C')or(benDetailData.benEvtRel eq 'E')or(benDetailData.benEvtRel eq 'Z'))and((benDetailData.payTyp eq '1')or(benDetailData.payTyp eq '2')or(benDetailData.payTyp eq '5')or(benDetailData.payTyp eq '6')or(benDetailData.payTyp eq '7')or(benDetailData.payTyp eq '8'))}">
                                            <span class="issuetitle_L_down">戶名：</span>
                                            <c:if test="${(benDetailData.accRel eq '1')or(benDetailData.accRel eq '2')}">
                                                <c:out value="${benDetailData.accName}" />
                                            </c:if>
                                            <c:if test="${benDetailData.accRel eq '3'}">
                                                &nbsp;
                                            </c:if>
                                        </c:if>&nbsp;
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${(benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')}">
                                <tr>
                                    <td colspan="3" id="iss">
                                        <span class="issuetitle_L_down">法定代理人姓名：</span>
                                        <c:out value="${benDetailData.grdName}" />&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">法定代理人身分證號：</span>
                                        <c:out value="${benDetailData.grdIdnNo}" />&nbsp;
                                    </td>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">法定代理人出生日期：</span>
                                        <c:out value="${benDetailData.grdBrDateStr}" />&nbsp;
                                    </td>
                                </tr>                          
                            </c:if>
                            <logic:notEmpty name="benDetailData" property="benChkDataList">
                                <tr>
                                    <td colspan="3">
                                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                                            <tr>
                                                <td width="13%" valign="top">
                                                    <span class="issuetitle_L_down">事故者編審註記：</span>
                                                </td>
                                                <logic:iterate id="benChkData" name="benDetailData" property="benChkDataList">                                                    
                                                    <td width="87%"><c:out value="${benChkData.issuPayYm}" />：
                                                    <logic:iterate id="chkFile" indexId="i" name="benChkData" property="chkFileList">
                                                        <span title="<c:out value="${chkFile.chkResult}" />">
                                                        <c:out value="${chkFile.chkCodePost}" /></span>
                                                        <c:if test="${i+1 ne benChkData.chkFileDataSize}">
                                                            <c:out value=",　" />
                                                        </c:if>
                                                    </logic:iterate>
                                                    </td>
                                                </logic:iterate>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </logic:notEmpty>
                            <logic:empty name="benDetailData" property="benChkDataList">
                                <tr>
                                    <td colspan="3"align="center">
                                        <font color="red">無資料</font>
                                    </td>
                                </tr>
                            </logic:empty>                                                                                   
                      </table>
                    </td>
                </tr>                  
                </logic:iterate>
                
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
