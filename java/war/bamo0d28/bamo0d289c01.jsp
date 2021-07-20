<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
  <acl:setProgId progId="BAMO0D289C01" />
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
        function toggleUpdateMode(rowNum) {
            var refToTable = $("listItem");
            var rowToBeModified = refToTable.tBodies[0].rows[rowNum - 1];
            var startAndEnd = rowToBeModified.getElementsByTagName("td")[1].innerHTML;
            var dateArray = startAndEnd.split("~");
            $("handicapSdate").value = Trim(dateArray[0]);
            $("handicapEdate").value = Trim(dateArray[1]);
            //$("termNo").value = rowNum;
            $("btnSave").style.display = "none";
            $("btnUpdate").style.display = "inline";
            //要先檢查之前是否已有append過,有的話要remove掉該element
            var n = document.getElementById('selfRowPosition');
            if(typeof n != 'undefined' && n != null){
                var pageElement = document.getElementById('page');
                var newElement = ElementCreator.createHiddenInputField('selfRowPosition', rowNum - 1);
                var parent = pageElement.parentNode;
                parent.replaceChild(newElement, n);
            } else {
                var pageElement = document.getElementById('page');
                var newElement = ElementCreator.createHiddenInputField('selfRowPosition', rowNum - 1);
                var parent = pageElement.parentNode;
                parent.appendChild(newElement);
            }
        }

        function extractTableToRangeArray() {
            var rangeArray = [];
            var rows = $("listItem").tBodies[0].rows;
            var valueToBeIgnored = '';
            if (typeof $('selfRowPosition') !== 'undefined' && $('selfRowPosition') !== null)
                valueToBeIgnored = $F('selfRowPosition');
            for (var rowCount = 0; rowCount < rows.length; rowCount++) {
                if(typeof valueToBeIgnored !== 'undefined' && valueToBeIgnored !== null ){
                    if(String(rowCount) === valueToBeIgnored){
                        continue;
                    }
                }
                var row = rows[rowCount];
                var dateCell = row.getElementsByTagName("td")[1];
                if (typeof dateCell != 'undefined') {
                    var dateCell = row.getElementsByTagName("td")[1].innerHTML;
                    var dateArray = dateCell.split("~");
                    var dateRange = new DateRange(Trim(dateArray[0]), Trim(dateArray[1]));
                    rangeArray.push(dateRange);
                }
            }
            return rangeArray;
        }

        var ElementCreator = {
            createHiddenInputField : function(fieldName, fieldValue){
                var inputElement = document.createElement("input");
                inputElement.setAttributeNode(ElementCreator.createHtmlAttribute("type", "hidden"));
                inputElement.setAttributeNode(ElementCreator.createHtmlAttribute("name", fieldName));
                inputElement.setAttributeNode(ElementCreator.createHtmlAttribute("value", fieldValue));
                inputElement.setAttributeNode(ElementCreator.createHtmlAttribute("id", fieldName));
                return inputElement;
            },
            createHtmlAttribute : function(name, value){
                var attribute = document.createAttribute(name);
                attribute.nodeValue = value;
                return attribute;
            }
        };


        //檢查日期格式
        function isDateFormatValid(date) {
            var pattern = new RegExp("^([0-9][0-9][0-9])([0][1-9]|1[0-2])$");
            return pattern.test(date);
        }

        function isDateFormatNotValid(date) {
            var pattern = new RegExp("^([0-9][0-9][0-9])([0][1-9]|1[0-2])$");
            return !pattern.test(date);
        }

        function isEmpty(field) {
            return Trim(field) == "";
        }

        function checkRequireFields() {
            var nowDate = "<%=DateUtility.getNowChineseDate()%>";
            var nowDateYM = nowDate.substring(0, 5);
            var msg = "";
            if (isEmpty($F("handicapSdate"))) msg += '「重殘起迄年月」-「起月」：為必輸欄位。\r\n';
            if (isEmpty($F("handicapEdate"))) msg += '「重殘起迄年月」-「迄月」：為必輸欄位。\r\n';
            if (!isEmpty($F("handicapSdate")) && !isEmpty($F("handicapEdate"))) {
                if (!isDateFormatValid(Trim($F("handicapSdate")))) msg += '「重殘起迄年月」-「起月」：之格式輸入錯誤。\r\n';
                if (!isDateFormatValid(Trim($F("handicapEdate")))) msg += '「重殘起迄年月」-「迄月」：之格式輸入錯誤。\r\n';
                // (需再確認是否需要卡系統年月)
                // if (Trim($F("studSdate")) > nowDateYM ) msg += '「重殘起始年月」：不得大於系統年月。\r\n';
                if (Trim($F("handicapSdate")) > Trim($F("handicapEdate"))) msg += '「重殘起迄年月」-「迄月」：不得小於「重殘起迄年月」-「起月」。\r\n';
                if (isDateFormatValid(Trim($F("handicapSdate"))) && isDateFormatValid(Trim($F("handicapEdate")))) {
                    var dateRangeArray = extractTableToRangeArray();
                    var inputDateRange = new DateRange(Trim($F("handicapSdate")), Trim($F("handicapEdate")));
                    for(var t = 0; t <dateRangeArray.length; t++){
                        dateRangeArray[t].printMonthsInBetween();
                    }
                    //將所輸入的DateRange一一與已有的DateRange比對,若有任一個月已存在即發出警示衝突
                    inputDateRange.extractMonthsInBetween();
//                     if(inputDateRange.monthsInBetween.length>12)
//                     	msg = '「重殘起年月」至「重殘迄年月」合計不得大於12個月。\r\n';
                    for (var j = 0; j < inputDateRange.monthsInBetween.length; j++) {
                        var currentDate = inputDateRange.monthsInBetween[j];
                        for (var k = 0; k < dateRangeArray.length; k++) {
                            if (dateRangeArray[k].isDateInRange(currentDate)) msg = '使用者所輸入之重殘起迄年月與現存「重殘起迄年月」衝突。\r\n';
                        }
                    }
                }
            }

            if (msg != "") {
                alert(msg);
                return false;
            }
            return true;
        }

        function initAll() {
            $('btnSave').show();
            $('btnUpdate').hide();
        }

        function resetForm() {
            document.SurvivorPayeeDataUpdateHandicapTermForm.reset();
            initAll();
        }

        Event.observe(window, 'load', initAll, false);
    </script>
</head>

<body scroll="no">
    <div id="content">

    <%@ include file="/includes/ba_header.jsp" %>
    <%@ include file="/includes/ba_menu.jsp" %>

    <div id="main" class="mainBody">
    <html:form action="/survivorPayeeDataUpdateHandicapTerm" method="post">
      <fieldset>

      <legend>&nbsp;重殘起迄年月維護作業&nbsp;</legend>
          <div align="right" id="showtime">
              網頁下載時間：民國&nbsp;<func:nowDateTime/>
          </div>
          <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
              <tr>
                  <td align="right">
                      <html:hidden styleId="page" property="page" value="1"/>
                      <html:hidden styleId="method" property="method" value=""/>
                      <acl:checkButton buttonName="btnSave">
                          <input id="btnSave" name="btnSave" type="button" class="button" value="新　增"
                                 onclick="$('page').value='1'; $('method').value='doSave'; if(checkRequireFields()){document.SurvivorPayeeDataUpdateHandicapTermForm.submit();} else {return false;} "/>&nbsp;
                      </acl:checkButton>
                      <acl:checkButton buttonName="btnUpdate">
                          <input id="btnUpdate" name="btnUpdate" type="button" class="button" value="確　定"
                                 onclick="$('page').value='1'; $('method').value='doUpdate'; if(checkRequireFields()){document.SurvivorPayeeDataUpdateHandicapTermForm.submit();} else {return false;} "
                                 style="display: none;"/>&nbsp;
                      </acl:checkButton>
                      <acl:checkButton buttonName="btnClear">
                          <input name="btnClear" type="button" class="button" value="清　除" onclick="resetForm();">&nbsp;
                      </acl:checkButton>
                      <acl:checkButton buttonName="btnBack">
                          <input name="btnBack" type="button" class="button" value="返　回"
                                 onclick="$('page').value='1'; $('method').value='doBack'; document.SurvivorPayeeDataUpdateHandicapTermForm.submit();"/>
                      </acl:checkButton>
                  </td>
              </tr>
              <tr>
                  <td>
                      <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                          <tr>
                              <td width="25%"><span class="issuetitle_L_down">受理編號：</span>
                                  <c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].apNoStrDisplay}"/>
                              </td>
                              <td width="25%"><span class="issuetitle_L_down">給付別：</span>
                                  <c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].payCode}"/>
                              </td>
                              <td width="25%"><span class="issuetitle_L_down">申請日期：</span>
                                  <c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].appDateChineseString}"/>
                              </td>
                              <td width="25%"><span class="issuetitle_L_down">事故日期：</span>
                                  <c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtJobDateStr}"/>
                              </td>
                          </tr>
                          <tr>
                              <td colspan="2"><span class="issuetitle_L_down">事故者姓名：</span>
                                  <c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtName}"/>
                              </td>
                              <td width="25%"><span class="issuetitle_L_down">事故者身分證號：</span>
                                  <c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtIdnNo}"/>
                              </td>
                              <td width="25%"><span class="issuetitle_L_down">事故者出生日期：</span>
                                  <c:out value="${SurvivorPayeeEvtDataUpdateQueryCase[0].evtBrDateChineseString}"/>
                              </td>
                          </tr>
                          <tr>
                          	<td colspan="2"><span class="issuetitle_L_down">遺屬姓名：</span><c:out value="${SurvivorPayeeDataUpdateDetailForm.benName}"/></td>
                            <td><span class="issuetitle_L_down">遺屬身分證號：</span><c:out value="${SurvivorPayeeDataUpdateDetailForm.benIdnNo}"/></td>
                            <td><span class="issuetitle_L_down">遺屬出生日期：</span><c:out value="${SurvivorPayeeDataUpdateDetailForm.benBrDate}"/></td>
                          </tr>
                      </table>
                  </td>
              </tr>
              <tr>
                  <td align="center" class="table1">
                      <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                          <tr>
                              <td colspan="8" align="center" class="table1">
                                  <table width="98%" cellpadding="3" cellspacing="5" class="px13">
                                      <tr>
                                          <td id="iss">
                                              <span class="needtxt">＊</span><span class="issuetitle_L_down">重殘起迄年月：</span>
                                              <html:text property="handicapSdate" styleId="handicapSdate" styleClass="textinput"
                                                         size="10" maxlength="5" onblur="this.value=asc(this.value);"/>
                                              ~
                                              <html:text property="handicapEdate" styleId="handicapEdate" styleClass="textinput"
                                                         size="10" maxlength="5" onblur="this.value=asc(this.value);"/>
                                              <span class="formtxt">(如民國98年9月&nbsp;~&nbsp;民國99年8月，請輸入09809&nbsp;~&nbsp;09908)</span>
                                          </td>
                                      </tr>
                                      <tr>
                                          <td><html:hidden styleId="termNo" property="termNo"/></td>
                                      </tr>
                                  </table>
                              </td>
                          </tr>
                          <tr>
                              <td colspan="4">&nbsp;</td>
                          </tr>
                          <tr align="center">
                              <td colspan="4" class="issuetitle_L">
                                  <bean:define id="list" name="<%=ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_HANDICAPTERM_LIST_CASE%>"/>
                                  <display:table name="pageScope.list" id="listItem" pagesize="999">
                                      <display:column title="序 號" headerClass="issuetitle_L" class="issueinfo_C" style="width: 15%; text-align: center;">
                                          <c:out value="${listItem_rowNum}"/>&nbsp;
                                      </display:column>
                                      <display:column title="重殘起迄年月" headerClass="issuetitle_L" class="issueinfo_C" style="width: 45%;text-align: center;">
                                          <c:out value="${listItem.handicapSdateChineseStr}"/> ~ <c:out value="${listItem.handicapEdateChineseStr}"/>
                                      </display:column>
                                      <display:column title="操作區" headerClass="issuetitle_L" class="issueinfo_C" style="width: 40%; text-align: center;">
                                          <acl:checkButton buttonName="btnUpdateHandicapTerm">
                                              <input type="button" class="button_90" name="btnUpdateHandicapTerm"
                                                     value="修　改"
                                                     onclick="$('termNo').value='<c:out value="${listItem.termNo}"/>'; toggleUpdateMode('<c:out value="${listItem_rowNum}"/>'); $('handicapSdate').focus(); ">
                                          </acl:checkButton>
                                          <acl:checkButton buttonName="btnDeleteHandicapTerm">
                                              <input type="button" class="button_90" name="btnDeleteHandicapTerm"
                                                     value="刪　除"
                                                     onclick="$('method').value='doDelete'; $('termNo').value='<c:out value="${listItem.termNo}"/>'; document.SurvivorPayeeDataUpdateHandicapTermForm.submit();">
                                          </acl:checkButton>
                                      </display:column>
                                  </display:table>
                              </td>
                          </tr>
                          <tr>
                              <td colspan="4">
                                  &nbsp;
                              </td>
                          </tr>
                      </table>
                  </td>
              </tr>
              <tr>
                  <td>
                      <hr size="1" noshade>
                      <span class="titleinput">※注意事項：</span><br/>
                      　&nbsp;<span class="needtxt">＊</span>為必填欄位。
                  </td>
              </tr>
          </table>
      </fieldset>
      <p></p>
      </html:form>
    </div>

</div>

<%@ include file="/includes/ba_footer.jsp"%>
</body>
</html:html>