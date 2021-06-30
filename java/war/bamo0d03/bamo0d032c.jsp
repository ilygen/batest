<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="tw.gov.bli.ba.update.cases.ChkFileCase" %>
<%@ page import="tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D032C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>   
    <html:javascript formName="OnceAmtDataUpdateForm" page="1" />        
    <script language="javascript" type="text/javascript">
    var evtDieDate = '<c:out value ="<%=((BaappbaseDataUpdateCase)request.getSession().getAttribute(ConstantKey.BAAPPBASE_DATA_UPDATE_CASE)).getEvtDieDateStr()%>" />'
    
    <!--     
    //畫面初始化
    function initAll(){
        //$("oldSeniab").value = "";
        var reSeniMk = '<c:out value ="<%=((BaappbaseDataUpdateCase)request.getSession().getAttribute(ConstantKey.BAAPPBASE_DATA_UPDATE_CASE)).getReSeniMk()%>" />'
        if(reSeniMk==""||reSeniMk=="null"){
            $("offInsurDate").disabled = true;
            $("lawRetireDate").disabled = true;
            $("offInsurDateCont").style.display="none";
            $("lawRetireDateCont").style.display="none";
        }else{
            $("offInsurDate").disabled = false;
            $("lawRetireDate").disabled = false;
            $("offInsurDateCont").style.display="inline";
            $("lawRetireDateCont").style.display="inline";
        } 
    }        
    
    function chgReSeniMk(){
        if($("reSeniMk").value!=""){
            $("offInsurDate").disabled = false;
            $("lawRetireDate").disabled = false;
            $("offInsurDateCont").style.display="inline";
            $("lawRetireDateCont").style.display="inline";
        }else{
            $("offInsurDate").value="";
            $("lawRetireDate").value="";
            $("offInsurDate").disabled = true;
            $("lawRetireDate").disabled = true;
            $("offInsurDateCont").style.display="none";
            $("lawRetireDateCont").style.display="none";
        }
    }
        
    <%-- 進階欄位驗證 --%>
    <%-- 注意: 點選修改按鈕時此部份之驗證須在 Validation.xml 驗證之前執行 --%>
    <%-- begin ... [ --%>
    function checkFields(method){ 
        var msg = "";  
        var offInsurDate = $("offInsurDate").value
        var lawRetireDate = $("lawRetireDate").value
        
        if(method!="insert"){
            if($("checkedSeqNo").value==""){
                msg+='請點選欲修改的資料\r\n';
            }
        }
        
        if(method!="delete"){
            var dabApNo = Trim($("dabApNo1").value) + Trim($("dabApNo2").value) + Trim($("dabApNo3").value) + Trim($("dabApNo4").value) 
            if(dabApNo!="" && dabApNo.length!=<%=ConstantKey.APNO_LENGTH%>){
                msg+='請輸入完整「已領失能年金受理編號」。\r\n';
                $("dabApNo1").focus();
            }
            
            if($("reSeniMk").value!=""){
                if(Trim(offInsurDate).length == 0){
                    msg+='「轉公保日期」為必填欄位。\r\n';
                    $("offInsurDate").focus();
                }
                if(Trim(lawRetireDate).length == 0){
                    msg+='「依法退休日期」為必填欄位。\r\n';
                    $("lawRetireDate").focus();
                }
                if(Trim(offInsurDate).length>0 && parseNumber(offInsurDate) > parseNumber(evtDieDate)){
                    msg+='「轉公保日期」不可大於「事故者死亡日期」\r\n';
                    $("offInsurDate").focus();
                }
                if(Trim(lawRetireDate).length>0 && parseNumber(lawRetireDate) > parseNumber(evtDieDate)){
                    msg+='「依法退休日期」不可大於「事故者死亡日期」\r\n';
                    $("lawRetireDate").focus();
                } 
            }
        }
             
        if(msg != ""){
            alert(msg);
            return false;
        }else{
            return true;
        }
    }
    <%-- ] ... end --%>
        
    Event.observe(window, 'load', initAll, false);
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/onceAmtDataUpdate" method="post" onsubmit="return validateOnceAmtDataUpdateForm(this);">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="apNo" property="apNo" />        
        <html:hidden styleId="lsUbno" property="lsUbno" />
        <html:hidden styleId="lsUbnoCk" property="lsUbnoCk" />
        <html:hidden styleId="owesMk" property="owesMk" />
        <input type="hidden" name="checkedSeqNo" value="">
                        
        <fieldset>
            <legend>&nbsp;老年年金一次給付資料更正&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right">
                        <%-- 2009.01.12 修改 --%>
                        <%-- [新增]、[修改]、[刪除]的按鈕先藏起來; 新增[確認]的按鈕。--%>
                        <%-- [ --%>
                        <%--
                        <acl:checkButton buttonName="btnInsert">
                            <input type="button" name="btnInsert" class="button" value="新　增" onclick="$('page').value='1'; $('method').value='doInsert'; if(document.OnceAmtDataUpdateForm.onsubmit() && checkFields('insert')){document.OnceAmtDataUpdateForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnUpdate">
                            <input type="reset" name="btnUpdate" class="button" value="修　改"  onclick="$('page').value='1'; $('method').value='doUpdate'; if(document.OnceAmtDataUpdateForm.onsubmit() && checkFields('update')){document.OnceAmtDataUpdateForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>                        
                        <acl:checkButton buttonName="btnDelete">
                            <input type="reset" name="btnDelete" class="button" value="刪　除"  onclick="$('page').value='1'; $('method').value='doDelete'; if(checkFields('delete')){document.OnceAmtDataUpdateForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        --%>
                        <acl:checkButton buttonName="btnInsert">
                            <input type="button" name="btnInsert" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doConfirm'; if(document.OnceAmtDataUpdateForm.onsubmit() && checkFields('insert')){document.OnceAmtDataUpdateForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.OnceAmtDataUpdateForm.submit();"/>
                        </acl:checkButton>       
                    </td>
                </tr>
                <tr>
                    <td>
                        <bean:define id="onceamt" name="<%=ConstantKey.BAAPPBASE_DATA_UPDATE_CASE%>"/>                        
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="33%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${onceamt.apNoStrDisplay}" />
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:if test='${(onceamt.pagePayKind eq "L")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L%>" />
                                    </c:if>
                                    <c:if test='${(onceamt.pagePayKind eq "K")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K%>" />
                                    </c:if>
                                    <c:if test='${(onceamt.pagePayKind eq "S")}'>
                                        <c:out value="<%=ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S%>" />
                                    </c:if>
                                </td>
                                <td width="34%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${onceamt.appDateStr}" />
                                </td>                                
                            </tr>
                            <tr>
                                <td>
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${onceamt.evtName}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${onceamt.evtIdnNo}" />
                                </td>
                                <td>
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${onceamt.evtBrDateStr}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" align="center" class="table1">
                      <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                        <tr>
                            <td id="iss" width="33%">
                                <span class="issuetitle_L_down">一次給付實發年資：</span>
                                <c:out value="${onceamt.onceAplPayAmtStr}" />
                            </td>
                            <td id="iss" width="33%">
                                <span class="issuetitle_L_down">一次給付平均薪資：</span>
                                <c:out value="${onceamt.onceAvgAmt}" />
                            </td>
                            <td id="iss" width="34%">
                                <span class="issuetitle_L_down">　一次給付核定金額：</span>
                                <c:out value="${onceamt.onceAmt}" />
                            </td>
                        </tr>
                        <tr>
                            <td id="iss">
                                <span class="issuetitle_L_down">老年一次金金額：</span>
                                <c:out value="${onceamt.onceOldAmt}" />
                            </td>
                            <td id="iss">
                                <span class="issuetitle_L_down">一次給付符合註記：</span>
                                <c:out value="${onceamt.oncePayMk}" />
                            </td>
                            <td id="iss">
                                <span class="needtxt">＊</span>
                                <span class="issuetitle_L_down">年資採計方式：</span>
                                <html:select property="oldSeniab">
                                    <html:option value="">請選擇</html:option>
                                    <html:option value="1">按半年/一年計</html:option>
                                    <html:option value="2">按比例計算</html:option>                                        
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td id="iss" colspan="3">
                                <span class="issuetitle_L_down">已領失能年金受理編號：</span>
                                <html:text property="dabApNo1" styleId="dabApNo1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('dabApNo1'), $('dabApNo2'))" />
                                &nbsp;-
                                <html:text property="dabApNo2" styleId="dabApNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value);" onkeyup="autotab($('dabApNo2'), $('dabApNo3'))"/>
                                &nbsp;-
                                <html:text property="dabApNo3" styleId="dabApNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" onkeyup="autotab($('dabApNo3'), $('dabApNo4'))" />
                                &nbsp;-
                                <html:text property="dabApNo4" styleId="dabApNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" />                                        
                            </td>
                        </tr>
                        <tr>
                            <td id="iss"><span class="issuetitle_L_down">轉公保註記：</span>
                                <html:select property="reSeniMk" onchange="chgReSeniMk();">
                                    <html:option value="">請選擇</html:option>
                                    <html:option value="1">公保</html:option>
                                    <html:option value="2">軍保</html:option> 
                                    <html:option value="3">私校保</html:option>                                        
                                </html:select>
                            </td>
                            <td id="iss">
                                <div id="offInsurDateCont" style="display: none">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">轉公保日期：</span>
                                    <html:text property="offInsurDate" styleId="offInsurDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);"/>
                                </div>&nbsp;
                            </td>
                            <td id="iss">
                                <div id="lawRetireDateCont" style="display: none">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">依法退休日期：</span>
                                    <html:text property="lawRetireDate" styleId="lawRetireDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);"/>
                                </div>&nbsp;
                            </td>
                        </tr> 
                        <tr>
                            <td></td>
                        </tr>
                      </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">&nbsp;</td>
                </tr>
                <%-- 2009.01.12 修改 --%>
                <%-- '職訓薪資扣除起迄'及下方的Grid資料全都先藏起來。--%>
                <%-- 所以相關步驟通通先註解掉 --%>
                <%-- [ --%>
                <%--
                <tr>
                    <td colspan="3" class="issuetitle_L">
                        <span class="needtxt">＊</span>職訓薪資扣除起迄：
                        <html:text property="owesBdate" styleId="owesBdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                        &nbsp;~&nbsp;
                        <html:text property="owesEdate" styleId="owesEdate" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                        <span class="formtxt">(如民國98年1月1日，請輸入0980101)</span>
                    </td>
                </tr>
                <tr align="center">
                    <td colspan="3" >
                        <bean:define id="list" name="<%=ConstantKey.BASENIMAINTCASE_LIST%>" />
                        <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.DETAIL_LIST_PAGE_SIZE%>">
                            <display:column title="&nbsp;" style="width:10%; text-align:center;">
                                <html:radio property="seqNo" value="${listItem.seqNo}" onclick="$('checkedSeqNo').value='${listItem.seqNo}'; $('owesBdate').value='${listItem.owesBdateStr}'; $('owesEdate').value='${listItem.owesEdateStr}';" />                               
                            </display:column>                            
                            <display:column title="序號" style="width:25%; text-align:center;">
                                <c:out value="${listItem_rowNum}" />
                            </display:column>
                            <display:column title="職訓薪資扣除起迄" style="width:65%; text-align:center;">
                                <c:out value="${listItem.owesBdateStr}" />                                
                                <c:if test='${(listItem.owesBdateStr ne "")or(listItem.owesEdateStr ne "")}'>
                                &nbsp;~&nbsp;
                                </c:if>
                                <c:out value="${listItem.owesEdateStr}" />
                            </display:column>                                                                                  
                        </display:table>
                    </td>                                                                    
                </tr>                                               
                <tr>
                    <td colspan="3">&nbsp;</td>
                </tr> 
                --%>
                <%-- ] --%>
            </table>            
        </fieldset>        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
