<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D062Q01" />
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
    
    function checkFields(){
        var msg="";
        if(msg!=""){
            return false;
        }else{
            return true;
        }
    }
    
    Event.observe(window, 'load', initAll, false);
    
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    </script>
</head>
<body scroll="no">

    <div id="mainContent">

        <%@ include file="/includes/ba_header.jsp"%>

        <%@ include file="/includes/ba_menu.jsp"%>

        <div id="main" class="mainBody">
            <html:form action="/paymentQuerySimplify" method="post" onsubmit="">
                <html:hidden styleId="page" property="page" value="1" />
                <html:hidden styleId="method" property="method" value="" />

                <fieldset>
                    <legend>
                        &nbsp;給付查詢(外單位專用)&nbsp;
                    </legend>
                    <div align="right" id="showtime">
                        網頁下載時間：民國&nbsp;<func:nowDateTime />
                    </div>
                    <bean:define id="detail" name="<%=ConstantKey.PAYMENT_QUERY_DETAIL_DATA_CASE%>" />
                    <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                        <tr>
                            <td colspan="14">
                            <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                            <td align="left" style="color: blue;font-size: 120%;font-weight: bold;">
                               <c:if test="${detail.secrecy eq 'Y'}">
                                    <c:out value="※保密案件"/>
                               </c:if>
                            </td> 
                            <td align="right" >
                                <acl:checkButton buttonName="btnPrint">
                                    <input name="btnPrint" type="button" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReportSimplifyApplicationData'; document.PaymentQueryForm.submit();"/>&nbsp;
                                </acl:checkButton>
                                <acl:checkButton buttonName="btnBack">
                                    <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackMaster'; document.PaymentQueryForm.submit();" />&nbsp;
                                </acl:checkButton>
                            </td>
                            </tr>
                            </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14">
                                <bean:define id="qryForm" name="<%=ConstantKey.PAYMENT_QUERY_COND_FORM%>" />
                                
                                <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                    <tr>
                                        <td width="33%">
                                            <span class="issuetitle_L_down">受理編號：</span>
                                            <c:out value="${detail.apNoStrDisplay}" />
                                        </td>                   
                                        <td width="33%">
                                            <span class="issuetitle_L_down">事故日期／申請日期：</span>
                                            <c:out value="${detail.evtJobDateStr}" />&nbsp;／&nbsp;<c:out value="${detail.appDateStr}" />
                                        </td>
                                        <td width="34%">
                                            <span class="issuetitle_L_down">給付別：</span>
                                            <c:out value="${detail.pagePayKindStr}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            <span class="issuetitle_L_down">事故者姓名：</span>
                                            <c:out value="${detail.evtName}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="issuetitle_L_down">事故者身分證號：</span>
                                            <c:out value="${detail.evtIdnNo}" />
                                        </td>               
                                        <td>
                                            <span class="issuetitle_L_down">事故者出生日期：</span>
                                            <c:out value="${detail.evtBrDateStr}" />&nbsp;
                                        </td>
                                        <td>
                                            <span class="issuetitle_L_down">事故者年齡／性別：</span>
                                            <c:out value="${detail.evtAge}" />&nbsp;歲／
                                            <c:out value="${detail.evtSexStr}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="issuetitle_L_down">電腦／人工審核結果：</span>
                                            <c:out value="${detail.acceptMkStr}" />
                                            &nbsp;／&nbsp;
                                            <c:out value="${detail.manchkMkStr}" />
                                        </td>
                                        <td>
                                            <span class="issuetitle_L_down">處理狀態：</span>
                                            <c:out value="${detail.procStatStr}" />
                                        </td>
                                        <td>
                                            <span class="issuetitle_L_down">給付種類：</span>
                                            <c:out value="${detail.payKindStr}" />
                                        </td>
                                    </tr>
                              </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="14">
                                <table border="0" cellpadding="0" cellspacing="0" class="issuetitle_L" id="tabsJ">
                                    <tr>
                                        <td valign="bottom" width="81%">
                                        </td>
                                        <td valign="bottom" width="19%" rowspan="2">
                                                    　承辦人分機號碼：<span class="word12"><c:out value="${detail.empExt}" />&nbsp;</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeApplicationData" />' title="連結至：案件資料" target="_self" class="needtxt"><span>案件資料</span></a>
                                            <h5><a>事故者／受款人</a></h5>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeAvgAmtData" />' title="連結至：平均薪資" target="_self" class="needtxt"><span>平均薪資</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeLoanData" />' title="連結至：紓困貸款" target="_self" class="needtxt"><span>紓困貸款</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeCpiData" />' title="連結至：物價指數調整" target="_self" class="needtxt"><span>物價指數調整</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeCpiRecData" />' title="連結至：物價指數調整記錄" target="_self" class="needtxt"><span>物價指數調整記錄</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeArclistData" />' title="連結至：歸檔記錄" target="_self" class="needtxt"><span>歸檔記錄</span></a>
                                            <a href='<c:url value="/paymentQuerySimplify.do?method=doOldAgeSimplifyUpdateLogQueryData" />' title="連結至：更正日誌" target="_self" class="needtxt"><span>更正日誌</span></a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <logic:empty name="<%=ConstantKey.PAYMENT_QUERY_BEN_DATA_CASE_LIST%>">
                        <tr>
                            <td colspan="14" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">                                       
                                    <tr>
                                        <td colspan="4" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;事故者資料</td>
                                    </tr>
                                    <tr>
                                        <td id="iss" align="center" colspan="4">
                                            <font color="red">無資料</font>
                                        </td>
                                    </tr>
                                </table>                                
                            </td>
                        </tr>                                         
                        <tr>
                            <td colspan="14" align="center" class="table1">
                                <table width="98%" cellpadding="3" cellspacing="3" class="px13">                                       
                                    <tr>
                                        <td colspan="5" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;受款人資料</td>
                                    </tr>
                                    <tr>
                                        <td id="iss" align="center" colspan="5">
                                            <font color="red">無資料</font>
                                        </td>
                                    </tr>
                                </table>                                
                            </td>
                        </tr>                                         
                        </logic:empty>
                        <logic:iterate id="benDetailData" indexId="benDataIndex" name="<%=ConstantKey.PAYMENT_QUERY_BEN_DATA_CASE_LIST%>" >
                            <tr>
                                <td colspan="14" align="center" class="table1">
                                    <table width="98%" cellpadding="3" cellspacing="3" class="px13">                                        
                                        <c:if test="${benDataIndex eq '0'}"> 
                                            <tr>
                                                <td colspan="5" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;事故者資料</td>
                                            </tr> 
                                            <tr>
                                                <td colspan="4" id="iss">
                                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                                    <c:out value="${benDetailData.benName}" />&nbsp;
                                                </td>
                                            </tr>
                                            <tr>
                                                <td width="25%" id="iss">
                                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                                    <c:out value="${benDetailData.benIdnNo}" />&nbsp;
                                                </td>
                                                <td id="iss" width="25%">
                                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                                    <c:out value="${benDetailData.benBrDateStr}" />&nbsp;
                                                </td>
                                        </c:if>
                                        <c:if test="${benDataIndex ne '0'}">
                                            <tr>
                                                <td colspan="5" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;受款人資料</td>
                                            </tr> 
                                            <tr>
                                                <td id="iss" colspan="1">                                
                                                    <span class="issuetitle_L_down">受款人序：</span>
                                                    <c:out value="${benDataIndex}" />
                                                </td>
                                                <td id="iss" colspan="3">
                                                    <span class="issuetitle_L_down">受款人姓名：</span>
                                                    <c:out value="${benDetailData.benName}" />&nbsp;
                                                </td>
                                             </tr>
                                             <tr>
                                                <td width="25%" id="iss">
                                                    <span class="issuetitle_L_down">受款人身分證號：</span>
                                                    <c:out value="${benDetailData.benIdnNo}" />&nbsp;
                                                </td>
                                               <c:if test="${(benDetailData.benEvtRel ne 'Z')and(benDetailData.benEvtRel ne 'F')and(benDetailData.benEvtRel ne 'N') }"> 
                                                <td id="iss" width="25%">
                                                    <span class="issuetitle_L_down">受款人出生日期：</span>
                                                    <c:out value="${benDetailData.benBrDateStr}" />&nbsp;
                                                </td>
                                            
                                            </c:if>
                                        </c:if> 
                                        	<c:if test="${(benDetailData.benEvtRel ne 'Z')and(benDetailData.benEvtRel ne 'F')and(benDetailData.benEvtRel ne 'N') }">                                     
                                            <td id="iss" colspan="1" width="25%">
                                                <span class="issuetitle_L_down">性別：</span>
                                                <c:if test="${benDetailData.benSex eq '1'}">
                                                    <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_1 %>" />                                    
                                                </c:if>
                                                <c:if test="${benDetailData.benSex eq '2'}">
                                                    <c:out value="<%=ConstantKey.BAAPPBASE_SEX_STR_2 %>" />                                    
                                                </c:if>&nbsp;
                                            </td>
                                            </c:if>
                                            <c:choose>
                                            	<c:when test="${(benDetailData.benEvtRel eq 'F')or(benDetailData.benEvtRel eq 'N')}">
                                            		<td id="iss" colspan="3" width="75%">
                                            	</c:when>
                                            	<c:otherwise>
                                            		<td id="iss" colspan="1" width="25%">
                                            	</c:otherwise>
                                            </c:choose>
                                                <span class="issuetitle_L_down">關係：</span>
                                                <c:out value="${benDetailData.benEvtRelStr}" />&nbsp;
                                            </td>
                                            <c:if test="${(benDetailData.benEvtRel ne 'Z')and(benDetailData.benEvtRel ne 'F')and(benDetailData.benEvtRel ne 'N') }">
                                            	</tr>
                                            </c:if>
                                            <c:if test="${benDetailData.benEvtRel eq 'Z'}"> 
                                            	<td id="iss" colspan='2'>
                                                <span class="issuetitle_L_down">實際補償金額：</span>
                                                <fmt:formatNumber value="${benDetailData.cutAmt}" />&nbsp;
                                            </td>
                                            </c:if>
                                        </tr>
                                        <tr>
                                            <c:if test="${benDataIndex eq '0'}">
                                                <td id="iss" colspan="1">
                                                    <span class="issuetitle_L_down">申請日期：</span>
                                                    <c:out value="${benDetailData.benEvtAppDateStr}" />&nbsp;
                                                </td>
                                            </c:if>
                                            <c:if test="${benDataIndex ne '0'}">
                                                <td id="iss" colspan="1">
                                                    <span class="issuetitle_L_down">繼承人申請日期：</span>
                                                    <c:out value="${benDetailData.benEvtAppDateStr}" />&nbsp;
                                                </td>
                                                <td id="iss" colspan="3">
                                                    <c:if test="${(benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7')}">
                                                        <span class="issuetitle_L_down">繼承自受款人：</span>
                                                        <c:out value="${benDetailData.benEvtName}" />&nbsp;
                                                    </c:if>
                                                    <c:if test="${!((benDetailData.benEvtRel eq '1')or(benDetailData.benEvtRel eq '2')or(benDetailData.benEvtRel eq '3')or(benDetailData.benEvtRel eq '4')or(benDetailData.benEvtRel eq '5')or(benDetailData.benEvtRel eq '6')or(benDetailData.benEvtRel eq '7'))}">
                                                        &nbsp;
                                                    </c:if>
                                                </td>
                                              </tr>
                                              <tr>
                                            </c:if>
                                            <c:if test="${benDataIndex eq '0'}">
	                                            <td id="iss" colspan="1" width="25%">
	                                                <span class="issuetitle_L_down">國籍別：</span>
	                                                <c:if test="${benDetailData.benNationTyp eq '1'}">
	                                                    <c:out value="本國" />
	                                                </c:if>
	                                                <c:if test="${benDetailData.benNationTyp eq '2'}">
	                                                    <c:out value="外籍" />
	                                                </c:if>&nbsp;
	                                            </td>
	                                            <td id="iss" colspan="1" width="25%">
	                                                <span class="issuetitle_L_down">國籍：</span>
	                                                <c:out value="${benDetailData.benNationCode}" />&nbsp;
	                                                <c:out value="${benDetailData.benNationName}" />
	                                            </td>
	                                            <c:choose>
                                            	<c:when test="${benDataIndex eq '0'}">
                                            		<td id="iss" colspan="1" width="25%">
                                            	</c:when>
                                            	<c:otherwise>
                                            		<td id="iss" colspan="2" widht="50%">
                                            	</c:otherwise>
                                            </c:choose>
	                                                <span class="issuetitle_L_down">身分查核年月：</span>
	                                                <c:out value="${benDetailData.idnChkYmStr}" />&nbsp;
	                                            </td>
                                            </c:if>
                                            <c:if test="${benDetailData.benEvtRel eq 'Z'}">
                                             	<td id="iss" colspan="1">
	                                                <span class="issuetitle_L_down">申請代算單位：</span>
	                                                <c:out value="${benDetailData.oldAplDpt}" />
	                                            </td>
	                                            <td id="iss" colspan="3">
	                                                <span class="issuetitle_L_down">申請代算單位名稱：</span>
	                                                <c:out value="${benDetailData.uname}" />
	                                            </td>
                                            </c:if>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="1" width="25%">
                                                <span class="issuetitle_L_down">電話1：</span>
                                                <c:if test="${benDetailData.tel1 ne ''}">
                                                    <c:out value="*****" />
                                                </c:if>&nbsp;
                                            </td>
                                            <td id="iss" colspan="1" width="25%">
                                                <span class="issuetitle_L_down">電話2：</span>
                                                <c:if test="${benDetailData.tel2 ne ''}">
                                                    <c:out value="*****" />
                                                </c:if>&nbsp;
                                            </td>
                                            <td id="iss" width="50%" colspan="2">
                                                <span class="issuetitle_L_down">地址：</span>
                                                <c:if test="${benDetailData.commTyp ne ''}">
                                                    <c:out value="********************" />
                                                </c:if>&nbsp;
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">給付方式：</span>
                                                <c:if test="${(benDetailData.accRel eq '1')or(benDetailData.accRel eq '2')}">
                                                    <c:out value="本人領取" />&nbsp;-&nbsp;
                                                    <c:out value="${benDetailData.payTypStr}" />
                                                </c:if>
                                                <c:if test="${benDetailData.accRel eq '3'}">
                                                    <c:out value="具名領取" />&nbsp;-&nbsp;<c:out value="${benDetailData.accName}" />
                                                </c:if>&nbsp;                                    
                                            </td>
                                            <td id="iss" colspan="2">
                                                <span class="issuetitle_L_down">帳號：</span>
                                                <c:if test="${(benDetailData.accRel eq '1')or(benDetailData.accRel eq '2')}">
                                                    <c:out value="********************" />
                                                </c:if>
                                                <c:if test="${benDetailData.accRel eq '3'}">
                                                    &nbsp;
                                                </c:if>
                                                <c:if test="${benDetailData.specialAcc eq 'Y'}">
                                                   &nbsp;專戶
                                                </c:if>
                                            </td>
                                       </tr>
                                       <tr>
                                            <td id="iss" colspan="4">
                                                <span class="issuetitle_L_down">戶名：</span>
                                                <c:if test="${(benDetailData.accRel eq '1')or(benDetailData.accRel eq '2')}">
                                                    <c:out value="${benDetailData.accName}" />
                                                </c:if>
                                                <c:if test="${benDetailData.accRel eq '3'}">
                                                    &nbsp;
                                                </c:if>
                                            </td>
                                        </tr>
                                        <c:if test="${(benDetailData.benEvtRel ne 'Z')and(benDetailData.benEvtRel ne 'F')and(benDetailData.benEvtRel ne 'N') }">
	                                        <tr>
	                                            <td id="iss"  colspan="2">
	                                                <span class="issuetitle_L_down">法定代理人姓名：</span>
	                                                <c:out value="${benDetailData.grdName}" />&nbsp;
	                                            </td>
	                                            <td id="iss">
	                                                <span class="issuetitle_L_down">法定代理人身分證號：</span>
	                                                <c:out value="${benDetailData.grdIdnNo}" />&nbsp;
	                                            </td>
	                                            <td id="iss" colspan="3">
	                                                <span class="issuetitle_L_down">法定代理人出生日期：</span>
	                                                <c:out value="${benDetailData.grdBrDateStr}" />&nbsp;
	                                            </td>
	                                        </tr>
	                                      </c:if>
                                        <%--
                                        <tr>
                                            <td colspan="5">
                                                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="px13">
                                                    <tr>
                                                        <td width="13%" valign="top">
                                                            <span class="issuetitle_L_down">事故者編審註記：</span>
                                                        </td>
                                                        <td width="87%">
                                                            <logic:iterate id="benChkData" name="benDetailData" property="benChkDataList">                                                    
                                                                <c:out value="${benChkData.issuPayYm}" />：
                                                                <logic:iterate id="chkFile" indexId="i" name="benChkData" property="benChkDataList">
                                                                    <span title="<c:out value="${chkFile.chkResult}" />">
                                                                    <c:out value="${chkFile.chkCodePost}" /></span>
                                                                    <c:if test="${i+1 ne benChkData.chkFileDataSize}">
                                                                        <c:out value=",　" />
                                                                    </c:if><br>
                                                                </logic:iterate>
                                                            </logic:iterate>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        --%>
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
