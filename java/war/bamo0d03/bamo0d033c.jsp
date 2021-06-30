<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D033C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>     
    <html:javascript formName="ApplicationDataUpdateForm" page="1" />                                
    <script language="javascript" type="text/javascript">
    function initAll(){
        //var idnNo = $("idnNo").value
        //objs = document.getElementsByName("evtDataRadio");
        //idnNoStrfor (i = 0; i < objs.length; i++){
        //    objStr = (objs[i].value).split(";");
        //    
        //    if(idnNo == objStr[1]){
        //        objs[i].checked = true;
        //        break;
        //    }        
        //}
        
    }       
    
    function checkFields(){
        var isChecked = false;
        objs = document.getElementsByName("evtDataRadio");
        for (i = 0; i < objs.length; i++){
            if(objs[i].checked == true){
                isChecked = true;
                $("evtData").value = objs[i].value
                break;
            }        
        }
        
        if(!isChecked){
            alert('請選擇事故者。')
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
        <html:form action="/applicationDataUpdate" method="post">
        <html:hidden styleId="page" property="page" value="1" />
        <html:hidden styleId="method" property="method" value="" />
        <html:hidden styleId="evtData" property="evtData" value="" />
        <bean:define id="app" name="<%=ConstantKey.BAAPPBASE_DATA_UPDATE_CASE%>"/> 
        <input type="hidden" id="idnNo" name="idnNo" value="${app.evtIdnNo}" />
        
        <fieldset>
            <legend>&nbsp;老年年金案件資料更正&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="5" align="right">
                        <acl:checkButton buttonName="btnConfirm">
                            <input type="button" name="btnQuery" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doUpdateEvtData'; if(checkFields()){document.ApplicationDataUpdateForm.submit();}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackDetail'; document.ApplicationDataUpdateForm.submit();" />
                        </acl:checkButton>                        
                    </td>
                </tr>
                <tr>
                <td colspan="5" class="issuetitle_L">
                    <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">    
                        <tr>
                            <td>
                                <span class="issuetitle_L_down">受理編號：</span>
                                <c:out value="${app.apNoStrDisplay}" />
                            </td>
                            <td>
                                <span class="issuetitle_L_down">事故者身分證號：</span>
                                <c:out value="${app.evtIdnNo}" />
                            </td>
                        </tr>
                    </table>
                </td>
                </tr>
                <tr>
                    <td colspan="5" class="issuetitle_L">
                        <span class="issuetitle_search">&#9658;</span>
                        <span>&nbsp;事故者編審註記：BK&nbsp;-&nbsp;W(同身分證號者有多人)</span>
                    </td>   
                </tr>
                <tr align="center">
                    <td>
                        <bean:define id="seniList" name="<%=ConstantKey.APPLICATION_UPDATE_DUPEIDNNO_CASE_LIST%>" />
                        <display:table name="pageScope.seniList" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                            <display:column title="　" style="width:10%; text-align:center;">
                                <c:if test="${listItem.selMk eq '2'}">
                                    <input type="radio" id="evtDataRadio<c:out value="${listItem_rowNum}" />" name="evtDataRadio" value="<c:out value="${listItem.name}" />;<c:out value="${listItem.idnNo}" />;<c:out value="${listItem.brDate}" />" onclick="$('evtData').value = this.value" checked="true"/>
                                </c:if>
                                <c:if test="${listItem.selMk ne '2'}">
                                    <input type="radio" id="evtDataRadio<c:out value="${listItem_rowNum}" />" name="evtDataRadio" value="<c:out value="${listItem.name}" />;<c:out value="${listItem.idnNo}" />;<c:out value="${listItem.brDate}" />" onclick="$('evtData').value = this.value" />
                                </c:if>
                            </display:column>
                            <display:column title="事故者姓名" style="width:20%; text-align:left;">
                                <c:out value="${listItem.name}" />
                            </display:column>
                            <%--
                            <display:column title="事故者身分證號" style="width:20%; text-align:center;">
                                <c:out value="${listItem.idnNoStr}" />
                            </display:column>
                            --%>
                            <display:column title="事故者出生日期" style="width:20%; text-align:left;">
                                <c:out value="${listItem.brDateStr}" />
                            </display:column>
                            <display:column title="　" style="width:50%; text-align:center;">
                                &nbsp;
                            </display:column>
                        </display:table>    
                    </td>
                </tr>      
                <tr>
                    <td colspan="5">&nbsp;</td>
                </tr>                                               
            </table>
        </fieldset>
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>
