<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D011C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script> 
    <html:javascript formName="CommunicationDataUpdateForm" page="1" />                  
    <script language="javascript" type="text/javascript">
    <!--     
    function initAll(){
        var benNationTyp = '<c:out value="${BaappbaseDataUpdateCase.benNationTyp}" />';
        var benEvtRel = '<c:out value="${BaappbaseDataUpdateCase.benEvtRel}" />';
        
        if($("origBenEvtRel").value=='A' || $("origBenEvtRel").value=='C' || $("origBenEvtRel").value=='O'){
            $("relCommTyp").style.display = "none";
            $("commTyp2").checked==true;
        }
        
        //2010.01.08修改 benEvtRel(關係)為1~7 + benNationTyp(國籍別)=1..."同戶籍"選項欄位才能Enabled
        if(!((benEvtRel=='1' || benEvtRel=='2' || benEvtRel=='3' || benEvtRel=='4' || benEvtRel=='5' || benEvtRel=='6' || benEvtRel=='7') && benNationTyp=='1')){
            $("commTyp1").disabled=true;
            $("commTyp2").checked=true;
        }
        
        if($("commTyp1").checked==true){
            //$("commZip").disabled = true;
            //$("commAddr").disabled = true;
            //$("commZip").value = "";
            //$("commAddr").value = "";            
            $("addContent1").style.display="inline";
            $("addContent2").style.display="none";            
        }
        if($("commTyp2").checked==true){
            //$("commZip").disabled = false;
            //$("commAddr").disabled = false;
            $("addContent1").style.display="none";
            $("addContent2").style.display="inline";  
        }
        
        //如果關係=F, N, disable所有輸入欄位以及確認button
        var benEvtRel = ($("benEvtRel").value).toUpperCase();
        if(benEvtRel=="F" || benEvtRel=="N"){
            $("btnSave").disabled = true;
            $("addContent1").style.display="none";
            $("addContent2").style.display="inline";  
            for (i = 0; i < document.forms[0].length; i++){
                obj = document.forms[0].elements[i];
                if(obj.type == "text" || obj.type == "textarea" || obj.type == "radio" || obj.type == "checkbox" || obj.type == "select-one"){
                    obj.disabled = true;
                }            
            }
        }
    }
    
    //變更 通訊地址別 時畫面異動
    function changeCommTyp(){
        if($("commTyp1").checked==true){
            $("addContent1").style.display="inline";
            $("addContent2").style.display="none";  
            //$("commZip").value = "";
            //$("commAddr").value = "";
            //$("commZip").disabled = true;
            //$("commAddr").disabled = true;
        }
        if($("commTyp2").checked==true){
            $("addContent1").style.display="none";
            $("addContent2").style.display="inline";  
            //$("commZip").disabled = false;
            //$("commAddr").disabled = false;
        }    
    }
    
    function checkFields(){        
        var msg = "";
        
        if($("commTyp1").checked==true){
            if($("commZipStr").value == "" || $("commAddrStr").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="msg.noCommAddrData" />\r\n"
                $("commTyp2").focus();
            }
        }        
        if($("commTyp2").checked==true){
            if($("commZip").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.update.communicationDataUpdate.commZip")%>' />\r\n"
                $("commZip").focus();
            }
            if($("commAddr").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.update.communicationDataUpdate.commAddr")%>' />\r\n"
                $("commAddr").focus();
            }               
        }
        
        if(msg != ""){
            alert(msg);
            return false;
        }else{
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
        <html:form action="/communicationDataUpdate" method="post" onsubmit="return validateCommunicationDataUpdateForm(this);">
        <bean:define id="comm" name="<%=ConstantKey.BAAPPBASE_DATA_UPDATE_CASE%>"/>
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="apNo" property="apNo" />
        <html:hidden styleId="baappbaseId" property="baappbaseId" />
        <html:hidden styleId="benIdnNo" property="benIdnNo" />
        <html:hidden styleId="benBrDate" property="benBrDate" />
        <input type="hidden" name="csrfToken" value="<c:out value="${csrfToken}"/>"/>
        <input type="hidden" id="origBenEvtRel" name="origBenEvtRel" value="<c:out value="${comm.benEvtRel}" />"/>
                
        <fieldset>
            <legend>&nbsp;通訊資料更正&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <acl:checkButton buttonName="btnSave">
                            <input type="button" name="btnSave" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doUpdate'; if(document.CommunicationDataUpdateForm.onsubmit() && checkFields()){document.CommunicationDataUpdateForm.submit();}else{return false}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="reset" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doModifyBack'; document.CommunicationDataUpdateForm.submit();"/>
                        </acl:checkButton>                        
                    </td>
                </tr> 
                <tr>
                    <td>
                        <html:hidden styleId="benEvtRel" name="comm" property="benEvtRel"/>
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${comm.apNoStrDisplay}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:if test='${(comm.pagePayKind eq "L")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                    </c:if>
                                    <c:if test='${(comm.pagePayKind eq "K")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                    </c:if>
                                    <c:if test='${(comm.pagePayKind eq "S")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                    </c:if>                                                                       
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${comm.appDateStr}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故日期：</span>
                                    <c:out value="${comm.evtJobDateStr}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${comm.evtName}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${comm.evtIdnNo}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${comm.evtBrDateStr}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="3" class="px13">
                            <tr>
                                <td colspan="3" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;受款人資料</td>
                            </tr>
                            <tr>
                                <td colspan="3" id="iss" width="100%">
                                    <span class="issuetitle_L_down">受款人姓名：</span>
                                    <c:out value="${comm.benName}" />
                                </td>
                            </tr>
                            <tr>
                                <td width="33%" id="iss">   
                                    <span class="issuetitle_L_down">受款人身分證號：</span>
                                    <c:out value="${comm.benIdnNo}" />    
                                </td>
                                <td width="33%" id="iss">
                                    <span class="issuetitle_L_down">受款人出生日期：</span>
                                    <c:out value="${comm.benBrDateStr}" />
                                </td>
                                <td width="34%" id="iss">
                                    <span class="issuetitle_L_down">關　係：</span>
                                    <c:if test='${(comm.benEvtRel eq "1")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "2")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "3")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "4")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "5")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "6")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "7")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "A")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "C")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "E")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "F")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "N")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "Z")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z%>" />
                                    </c:if>
                                    <c:if test='${(comm.benEvtRel eq "O")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O%>" />
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="1" id="iss">
                                    <span class="issuetitle_L_down">電　話1：</span>
                                    <html:text property="tel1" styleId="tel1" styleClass="textinput" size="20" maxlength="20" onblur="this.value=asc(this.value);"/>
                                </td>
                                <td colspan="2" id="iss">
                                    <span class="issuetitle_L_down">電　話2：</span>
                                    <html:text property="tel2" styleId="tel2" styleClass="textinput" size="20" maxlength="20" onblur="this.value=asc(this.value);"/>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">地　址：</span>
                                    <span class="formtxt">
                                    <div id="relCommTyp" style="display: inline">
                                        <html:radio styleId="commTyp1" property="commTyp" value="1" onclick="changeCommTyp();" onblur="this.value=asc(this.value);"/>同戶籍地                                
                                    </div>
                                    <html:radio styleId="commTyp2" property="commTyp" value="2" onclick="changeCommTyp();" onblur="this.value=asc(this.value);"/>現住址</span>                                    
                                </td>
                                <td id="iss" colspan="3">
                                    <span class="formtxt">
                                        <div id="addContent1" style="display: none;">
                                                  現住址：
                                        <input type="text" name="commZipStr" class="textinput" size="6" maxlength="6" value="<c:out value="${CommunicationDataUpdateForm.cvlDtlCommZip}" />" disabled="true"/>(郵遞區號)&nbsp;-
                                        <input type="text" name="commAddrStr" class="textinput" size="72" maxlength="240" value="<c:out value="${CommunicationDataUpdateForm.cvlDtlCommAddr}" />" disabled="true"/>
                                        </div>
                                        <div id="addContent2" style="display: none;">
                                                  現住址：
                                        <html:text property="commZip" styleId="commZip" styleClass="textinput" size="6" maxlength="6" onblur="this.value=Trim(asc(this.value));"/>(郵遞區號)&nbsp;-
                                        <html:text property="commAddr" styleId="commAddr" styleClass="textinput" size="72" maxlength="240" onblur="this.value=asc(this.value);"/>
                                        </div>
                                    </span>                                                                            
                                </td>
                            </tr>
                        </table>
                    </td>
                 </tr>
                 <tr>
                    <td>
                        <hr size="1" noshade>
                        <span class="titleinput">※注意事項：</span><br>
                    　     &nbsp;<span class="needtxt">＊</span>為必填欄位。
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
