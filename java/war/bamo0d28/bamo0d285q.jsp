<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
        <acl:setProgId progId="BAMO0D285Q" />
        <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
	    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
	    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
	    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
	    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    	<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>	   
        <script type="text/javascript">
        <!--
        var argsObj = window.dialogArguments;
        
        function doQuery() {
            $("schoolCode").value = "";
            $("schoolName").value = "";

            if ($F("schoolName1") != "") {
                updateCommonAjaxDo.getSchoolDataBy($F("schoolName1"), fillSchoolListData);
            }
            else {
                alert("請先輸入欲查詢的學校名稱");
            }
        }
   
        function createDiv(align) {
            var eDiv = document.createElement("div");

            var aAlign = document.createAttribute("align");
            aAlign.nodeValue = align;
            eDiv.setAttributeNode(aAlign);

            return eDiv;
        }

        setSchoolData = function(schoolCode, schoolName) {
            $("schoolCode").value = schoolCode;
            $("schoolName").value = schoolName
        }

        var rowIndex = 0;

        var getSchoolCode = function(data) {
            var eDiv = createDiv("left");

            if (rowIndex % 2 == 1)
                eDiv.style.backgroundColor = "#E1FFFF";

            eDiv.appendChild(document.createTextNode(data.codeNo));
            eDiv.onclick = new Function("setSchoolData('" + data.codeNo + "','" + data.codeName + "')");

            return eDiv;
        };

        var getSchoolName = function(data) {
            var eDiv = createDiv("left");

            if (rowIndex % 2 == 1)
                eDiv.style.backgroundColor = "#E1FFFF";

            eDiv.appendChild(document.createTextNode(data.codeName));
            eDiv.onclick = new Function("setSchoolData('" + data.codeNo + "','" + data.codeName + "')");

            rowIndex++;

            return eDiv;
        };        
        
 
        function fillSchoolListData(list) {
            rowIndex = 0;
            dwr.util.removeAllRows("detailList");
            dwr.util.addRows("detailList", list, [ getSchoolCode, getSchoolName ]);

            if (list.length == 1) {
                $("schoolCode").value = list[0].codeNo;
                $("schoolName").value = list[0].codeName;
            }
        }        
        
        function doReturn(res) {
            if (res) {
                if ($F("schoolCode") == "" || $F("schoolName") == "") {
                    alert("請選擇學校");
                    return;
                }

                var argsObj = new Object();
                argsObj.schoolCode = $F("schoolCode");
                argsObj.schoolName = $F("schoolName");

                window.returnValue = argsObj;
                window.close();
            }else{
                window.close();
            }
        }        
    
        function initAll() {
            <%--dwr.util.useLoadingMessage("<bean:message bundle="<%=Global.NA_MSG%>" key="msg.system.loading"/>");--%>

            if (argsObj != null) {
                var schoolCode = argsObj.schoolCode;
               	updateCommonAjaxDo.getSchoolDataBy(null, fillSchoolListData);            
            }
        }


        Event.observe(window, 'load', initAll , false);        
        -->        
        </script>
  </head>
  
  <body scroll="yes">    
    <div id="main">
        <html:form action="/survivorPayeeDataUpdateDetail" method="post" onsubmit="">
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="5">
                <tr>
                    <td>        
        				<fieldset>
        					<legend>&nbsp;學校名稱查詢&nbsp;</legend>
        								    
        					<table width="100%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
        						<tr>
        							<td colspan="5"><hr size="1" noshade></td>
        						</tr>
        						<tr>
        							<td width="15%" align="right">
        								<span class="issuetitle_L_down">學校名稱：</span>
        							</td>
        							<td width="65%">
        								<input id="schoolName1" name="schoolName1" type="text" class="textinput" size="50">
        							</td>
        							<td width="20%" align="center">
        								<input name="btnQuery" type="button" value="查　詢" class="button" onclick="doQuery();">
        							</td>
        						</tr>
        						<tr>
        							<td colspan="5"><hr size="1" noshade></td>
        						</tr>
                                <tr>
                                    <td colspan="5">
                                        <div style="height:200px;overflow:auto;">
                                            <table width="90%" border="1" align="center" cellpadding="3" cellspacing="2" class="px13" bordercolor="#0099FF">
                                                <tr align="center" bgcolor="#CCFFFF">
                                                    <td width="36%" bgcolor="#78C0FC"><strong>學校代碼</strong></td>
                                                    <td width="64%" bgcolor="#78C0FC"><strong>學校名稱</strong></td>
                                                </tr>
                                                <tbody id="detailList">

                                                </tbody>
                                            </table>
                                        <div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="5"><hr size="1" noshade></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td colspan="4">
                                        <span class="issuetitle_L_down">學校代碼：</span>
                                        <input id="schoolCode" name="schoolCode" type="text" class="textinput" size="10" disabled>
                                        (<input id="schoolName2" name="schoolName" type="text" class="textinput" size="20" disabled>)
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="5"><hr size="1" noshade></td>
                                </tr>
                                <tr>
                                    <td colspan="5" align="center">
                                        <input type="button" class="button" value="確　認" onClick="doReturn(true);">　
                                        <input type="button" class="button" value="取　消" onClick="doReturn(false);">
                                    </td>
                                </tr>                                        								
        					</table>
        				</fieldset>
                    </td>
                </tr>        
             </table>   
    		</html:form>
    </div>

  </body>
</html:html>