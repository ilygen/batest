<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D041Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script language="javascript" type="text/javascript">
    <!--
    <%-- 列印 --%>
    <%-- begin ... [ --%>
    function doPrint() {
        procDivShow($(excludePrint));
        window.print();
        procDivShow($(excludePrint));
    }
    <%-- ] ... end --%>
    -->
    </script>
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
    
        <fieldset>
            <legend>&nbsp;編審註記查詢&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
    
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13" id="table">
                <tr>
                    <td colspan="3" align="right">
                        <acl:checkButton buttonName="btnPrint">
                            <div id="excludePrint" align="center" style="display:inline;">
                                <input name="btnPrint" type="button" class="button" value="列  印" onclick="doPrint();">
                            </div>
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返  回" onClick="javascript:history.back();">
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center" valign="bottom">
                        <bean:define id="list" name="<%=ConstantKey.CHECK_MARK_LEVEL_QUERY_CASE%>" />
                    
                        <display:table name="pageScope.list" id="listItem" class="px13">
                        <display:column title="序號" style="width:4%; text-align: center;">
                            <c:out value="${listItem_rowNum}" />
    			        </display:column>
                        <display:column property="chkCode" title="註記代號" style="width:10%; text-align:center;" />
                        <display:column property="chkDesc" title="中文說明" style="width:18%;" />
                        <display:column property="chkCondesc" title="編審條件" style="width:24%;" />
                        <display:column property="chkLevelStr" title="嚴重程度" style="width:10%; text-align:center;" />
                        <display:column property="chkObjStr" title="給付種類" style="width:6%; text-align:center;" />
                        <display:column title="有效日期起迄" style="width:14%; text-align:center;" >
                            <c:if test="${(listItem.valibDate ne null) and (listItem.valieDate ne null)}">
                                <c:out value="${listItem.valibDateStr}" />~<c:out value="${listItem.valieDateStr}" />
                            </c:if>
                        </display:column>
                        <display:column title="法令依據" style="width:10%; text-align:center;">
                            <c:out value="${listItem.chkLawdesc}" />
                            <logic:empty property="chkLawdesc" name="listItem">
                                    &nbsp;
                            </logic:empty>
                        </display:column>
                        </display:table>
                    
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>