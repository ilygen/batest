<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BAIQ0D070Q" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<html:javascript formName="AnnuityStatisticsForm" page="1" />
<script language="javascript" type="text/javascript">
    // 給付別，點選失能年金或遺屬年金時，分析選項出現傷病分類，老年年金則不顯示
	function selectPayCode() {
	    if (document.getElementById('payCode').value == 'K' 
	     || document.getElementById('payCode').value == 'S'){
	        document.getElementById('payCodeShow').innerHTML = ''+
	        '<input type="radio" name="analysisSelect" value="E">傷病分類';
	    }else{
	        document.getElementById('payCodeShow').innerHTML = '';
	    }
	}
    // 案件類別選擇全部，核付年月迄Disabled，若點選首發，則皆開放輸入
    function paymentYearMonth() {
    	var caseClassification = document.getElementsByName("caseClassification");
    	for(k = 0;k < caseClassification.length;k++){
    		if(caseClassification[k].checked) {
    			var value = caseClassification[k].value;
    			if(value == 1){
    		     	$("paymentYMEnd").value = '';
    		     	$("paymentYMEnd").style.backgroundColor = "";
    				$("page").value = "1";
    				document.getElementById("paymentYMEnd").disabled = true;
    			} else {
    				$("page").value = "2";
    				document.getElementById("paymentYMEnd").disabled = false;
    			}
    		}
    	}
    }
	// 點選新增級距
	function insertSpacing() {
		i++;
		if(i < 10) {
			i = "0" + i;
		}

		var span = document.createElement("span");
		var input = document.createElement("input");
		var input2 = document.createElement("input");
		var spanText = document.createTextNode(i + ".\u00A0");
		var spanText2 = document.createTextNode("\u00A0~\u00A0");
		var spanText3 = document.createTextNode("\u00A0\u00A0\u00A0");
		var br = document.createElement("br");
		
		span.className = "issuetitle_R_down";
		input.className = "textinput";
		input.name = "spacing";
		input.size = 10;
		input.maxLength = 10;
		input2.className = "textinput";
		input2.name = "spacing";
		input2.size = 10;
		input2.maxlength = 10;
		
		span.appendChild(spanText);
		span.appendChild(input);
		span.appendChild(spanText2);
		span.appendChild(input2);
		span.appendChild(spanText3);
		document.getElementById('qryTypeShow').appendChild(span);
		if(i % 4 == 0)
			document.getElementById('qryTypeShow').appendChild(br);
	}
	// 點選刪除級距
	function deleteSpacing() {
		var count = document.getElementById('qryTypeShow').childElementCount;
		if(count > 4) {
			var qryTypeShow = document.getElementById('qryTypeShow');
			if(i % 4 == 0) {
				qryTypeShow.removeChild(qryTypeShow.lastChild);
				qryTypeShow = document.getElementById('qryTypeShow');
			} 
			qryTypeShow.removeChild(qryTypeShow.lastChild);
			i--;
		} else {
			alert("級距區間輸入(起迄)欄位僅剩一組，無法全數刪除");
		}
	}
	// 統計項目選擇金額級距、展減級距、平均薪資級距、年資級距時出現新增級距、刪除級距按鈕以及出現第一組可輸入的input
    function statisticalItem() {
	    i = 1;
// 	    array = 1;
        if (document.getElementById('qrytype').value == 'C' 
         || document.getElementById('qrytype').value == 'D'
         || document.getElementById('qrytype').value == 'E'
         || document.getElementById('qrytype').value == 'F'){
            document.getElementById('qryTypeShow').innerHTML = ''+
            '<input name="InsertSpacing" id="InsertSpacing" type="button" class="button_90" value="新增級距" onclick="insertSpacing();">&nbsp;&nbsp;&nbsp;'+
            '<input name="DeleteSpacing" id="DeleteSpacing" type="button" class="button_90" value="刪除級距" onclick="deleteSpacing();"><br>'+
            '<span class="issuetitle_R_down">01.&nbsp;<input name="spacing" type="text" class="textinput" value="" size="10" maxlength="10">&nbsp;~&nbsp;<input name="spacing" type="text" class="textinput" value="" size="10" maxlength="10">&nbsp;&nbsp;&nbsp;</span>';
        }else{
            document.getElementById('qryTypeShow').innerHTML = '';
        }
     }
    
    // 頁面欄位值初始化
    function initAll(){
    	document.getElementById('qryTypeShow').innerHTML = '';
    	paymentYearMonth();
    }
    // 確認表單輸入內容是否符合規則
    function checkFields() {
    	var regNum = /^[1-9][0-9]*$/;
    	var regNum2 = /^(\-)?\d+(\.\d{1,2})?$/;
    	var msg = "";
    	// 確認給付別有選擇
    	if(document.getElementById('payCode').value != 'L'
    	&& document.getElementById('payCode').value != 'K'
    	&& document.getElementById('payCode').value != 'S') {
    		msg += "「給付別」為必填欄位  \r\n";
    	}
    	// 確認統計項目有選擇
    	if(document.getElementById('qrytype').value != 'A'
        && document.getElementById('qrytype').value != 'B'
        && document.getElementById('qrytype').value != 'C'
        && document.getElementById('qrytype').value != 'D'
        && document.getElementById('qrytype').value != 'E'
        && document.getElementById('qrytype').value != 'F'
        && document.getElementById('qrytype').value != 'G'
        && document.getElementById('qrytype').value != 'H') {
    		msg += "「統計項目」為必填欄位 \r\n";
    	}
		// 案件類別選擇全部
    	if(document.getElementsByName("caseClassification")[0].checked) {
    		// 確認核付年月起有輸入
    		if(Trim($("paymentYMStart").value) == "") {
    			msg += "「核付年月起」為必填欄位  \r\n";
    		}
    	}
		// 案件類別選擇首發
    	if(document.getElementsByName("caseClassification")[1].checked) {
    		// 確認核付年月起有輸入
    		if(Trim($("paymentYMStart").value) == "") {
    			msg += "「核付年月起」為必填欄位  \r\n";
    		}
    		// 確認核付年月迄有輸入
    		if(Trim($("paymentYMEnd").value) == "") {
    			msg += "「核付年月迄」為必填欄位  \r\n";
    		}
            // 確認核付年月迄大於核付年月起
        	if(Trim($("paymentYMStart").value) != "" && Trim($("paymentYMEnd").value) != "") {
            	var paymentYMStart = Trim($("paymentYMStart").value);
            	var paymentYMEnd = Trim($("paymentYMEnd").value);
            	if(paymentYMEnd < paymentYMStart) {
            		msg += "起月必須小於等於迄月 \r\n";
            	}
        	}
    	}
        // 統計項目選擇"金額級距"、"展減級距"、"平均薪資級距"、"年資級距"時，檢核是否有輸入資料
        if(document.getElementById('qrytype').value == 'C'
        || document.getElementById('qrytype').value == 'D'
        || document.getElementById('qrytype').value == 'E'
        || document.getElementById('qrytype').value == 'F') {
        	var array = document.getElementsByName("spacing");
        	for(var loop = 0 ; loop < array.length ; loop ++) {
	            if(Trim(document.getElementsByName('spacing')[loop].value) == "") {
        			msg += "請輸入「級距區間」(起迄) \r\n";
                    alert(msg);
            		return false;
        		}
        	}
        }
        // 統計項目選擇展減級距限定只能輸入正、負含小數點的數值，最多輸入至小數點第二位
        if(document.getElementById("qrytype").value == "D") {
        	var array = document.getElementsByName("spacing");
        	for(var loop = 0 ; loop < array.length ; loop ++) {
            	var num = Trim(document.getElementsByName('spacing')[loop].value);
            	if(!regNum2.test(num)) {
            		msg += "「級距區間」(起迄)格式錯誤 \r\n";
                    alert(msg);
            		return false;
            	}
        	}
        }
        // 統計項目選擇金額級距、展減級距、平均薪資級距、年資級距，限定只能輸入正整數
        if (document.getElementById('qrytype').value == 'C'
        ||  document.getElementById('qrytype').value == 'E'
        ||  document.getElementById('qrytype').value == 'F') {
        	var array = document.getElementsByName("spacing");
        	for(var loop = 0 ; loop < array.length ; loop ++) {
            	var num = Trim(document.getElementsByName('spacing')[loop].value);
            	if(!regNum.test(num)) {
            		msg += "「級距區間」(起迄)格式錯誤 \r\n";
                    alert(msg);
            		return false;
            	}
        	}
        }
        // 檢核每組級距欄位值不得重複且須連續未中斷 for C、E、F
        if(document.getElementById('qrytype').value == 'C'
        || document.getElementById('qrytype').value == 'E'
        || document.getElementById('qrytype').value == 'F' ) {
        	var array = document.getElementsByName("spacing");
        	for(var loop = 0;loop < array.length - 1;loop ++) {
        		for(var loop_2 = loop + 1;loop_2 < array.length;loop_2 ++) {
                	// 判斷是否重複
        			if(parseInt(Trim(document.getElementsByName('spacing')[loop].value)) == parseInt(Trim(document.getElementsByName('spacing')[loop_2].value))) {
        				msg += "「級距區間」(起迄)欄位值不得重複且須連續未中斷) \r\n";
        	            alert(msg);
                		return false;
        			}
                	//判斷是否按照大小輸入
            		if(parseInt(Trim(document.getElementsByName('spacing')[loop].value)) > parseInt(Trim(document.getElementsByName('spacing')[loop_2].value))) {
            			msg += "「級距區間」(起迄)欄位值不得重複且須連續未中斷) \r\n";
                        alert(msg);
                		return false;
            		}
        		}
        	}
            // 判斷是否連續
            for(var loop = 1;loop < array.length - 1;loop += 2){
            	if(parseInt(Trim(document.getElementsByName('spacing')[loop].value))+1 != parseInt(Trim(document.getElementsByName('spacing')[loop+1].value))) {
            		msg += "「級距區間」(起迄)欄位值不得重複且須連續未中斷) \r\n";
                    alert(msg);
            		return false;
            	}
            }
        }
        // 檢核每組級距欄位值不得重複且須連續未中斷 for D
        if(document.getElementById('qrytype').value == 'D') {
        	var array = document.getElementsByName("spacing");
        	for(var loop = 0;loop < array.length - 1;loop ++) {
        		for(var loop_2 = loop + 1;loop_2 < array.length;loop_2 ++) {
                	// 判斷是否重複
        			if(parseFloat(Trim(document.getElementsByName('spacing')[loop].value)) == parseFloat(Trim(document.getElementsByName('spacing')[loop_2].value))) {
        				msg += "「級距區間」(起迄)欄位值不得重複且須連續未中斷) \r\n";
        	            alert(msg);
                		return false;
        			}
                	//判斷是否按照大小輸入
                	if(parseFloat(Trim(document.getElementsByName('spacing')[loop].value)) > parseFloat(Trim(document.getElementsByName('spacing')[loop_2].value))) {
            			msg += "「級距區間」(起迄)欄位值不得重複且須連續未中斷) \r\n";
                        alert(msg);
                		return false;
                	}
        		}
        	}
        	
            // 判斷是否連續
            for(var loop = 1;loop < array.length - 1;loop += 2) {
            	var trimNum = Trim(document.getElementsByName('spacing')[loop].value); // 先把數字trim
            	var indexOf = Trim(document.getElementsByName('spacing')[loop].value).lastIndexOf("."); // 取小數點位置
            	var num = trimNum.substring(0,indexOf) + trimNum.substring(indexOf+1,trimNum.length); // 把小數點取出來
            	
            	var trimNum_2 = Trim(document.getElementsByName('spacing')[loop+1].value); // 先把數字trim (要被比對的數字)
            	var indexOf_2 = Trim(document.getElementsByName('spacing')[loop+1].value).lastIndexOf("."); // 取小數點位置 (要被比對的數字)
            	var num_2 = trimNum_2.substring(0,indexOf_2) + trimNum_2.substring(indexOf_2+1,trimNum_2.length); // 把小數點取出來 (要被比對的數字)
            	
            	// 把兩個數字變成整數後判斷是否連續
            	if(parseInt(num) + 1 != parseInt(num_2)) {
            		msg += "「級距區間」(起迄)欄位值不得重複且須連續未中斷) \r\n";
            		alert(msg);
            		return false;
            	}
            }
        }
        // alert 錯誤訊息
        if(Trim(msg).length != 0){
            alert(msg);
            return false;
        }
        return true;
    }

	<%-- 畫面清空重設 --%>
	<%-- begin ... [ --%>
	function resetForm() {
    	$("payCode").value = '';
     	$("paymentYMStart").value = '';
     	$("paymentYMEnd").value = '';
    	document.getElementsByName("caseClassification")[0].checked = true;
		initAll();
		selectPayCode();
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
			<html:form action="/annuityStatistics" method="post" onsubmit="return validateAnnuityStatisticsForm(this);">
			    <html:hidden styleId="page" property="page" value="1" />
				<html:hidden styleId="method" property="method" value="" />

				<fieldset>
					<legend>&nbsp;年金統計查詢&nbsp;</legend>

					<div align="right" id="showtime">
						網頁下載時間：民國&nbsp;
						<func:nowDateTime />
					</div>

					<table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
						<tr>
							<td colspan="2" align="right">
								<acl:checkButton buttonName="btnConfirm">
									<input type="button" name="btnConfirm" id="btnConfirm" class="button" value="確　認" onclick="$('method').value='doReport'; if(checkFields()){document.AnnuityStatisticsForm.submit();}else{return false;}" />&nbsp;
								</acl:checkButton>
                        		<acl:checkButton buttonName="btnReset">
									<input type="reset" name="btnReset" id="btnReset" class="button" value="清　除" onclick="resetForm();" />
								</acl:checkButton></td>
						</tr>
                        <tr>
                           <td width="30%" align="right">
                              <span class="needtxt">＊</span>
                              <span class="issuetitle_R_down">給付別：</span>
                           </td>
                           <td width="70%">
                              <select name="payCode" id="payCode" class="formtxt" onChange="selectPayCode();">
                                 <option selected>請選擇給付別</option>
                                 <option value="L">老年年金</option>
                                 <option value="K">失能年金</option>
                                 <option value="S">遺屬年金</option>
                              </select>
                           </td>
                        </tr>
                        <tr>
                           <td align="right">
                              <span class="needtxt">＊</span>
                              <span class="issuetitle_R_down">案件類別：</span>
                           </td>
                           <td class="formtxt">
                              <input type="radio" name="caseClassification" value="1" onclick="paymentYearMonth();" checked>全部&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="caseClassification" value="2" onclick="paymentYearMonth();">首發
                           </td>
                        </tr>
                        <tr>
                           <td align="right">
                              <span class="needtxt">＊</span>
                              <span class="issuetitle_R_down">核付年月起迄：</span>
                           </td>
                           <td>
                              <input name="paymentYMStart" id="paymentYMStart" type="text" class="textinput" value="" size="10" maxlength="5" onkeyup="autotab(this, $('paymentYMEnd'));">&nbsp;~&nbsp;
                              <input name="paymentYMEnd" id="paymentYMEnd" type="text" class="textinput" value="" size="10" maxlength="5">
                           </td>
                        </tr>
                        <tr>
                           <td align="right"> 
                              <span class="needtxt">＊</span>
                              <span class="issuetitle_R_down">分析選項：</span> 
                           </td>
                           <td class="formtxt">
                              <input type="radio" name="analysisSelect" value="X" checked>無&nbsp;&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="analysisSelect" value="S">性別&nbsp;&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="analysisSelect" value="U">單位類別&nbsp;&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="analysisSelect" value="N">國籍別&nbsp;(給付主檔)&nbsp;&nbsp;&nbsp;&nbsp;
                              <input type="radio" name="analysisSelect" value="C">外籍別&nbsp;(CIPB被保險人基本資料檔)&nbsp;&nbsp;&nbsp;&nbsp;
                              <span id="payCodeShow"></span>
                           </td>
                        </tr>
                        <tr>
                           <td align="right">
                              <span class="needtxt">＊</span>
                              <span class="issuetitle_R_down">統計項目：</span>
                           </td>
                           <td>
                              <select name="qrytype" id="qrytype" class="formtxt" onChange="statisticalItem();">
                                 <option selected>請選擇統計項目</option>
                                 <option value="A">件數及金額</option>
                                 <option value="B">年度平均</option>
                                 <option value="C">金額級距</option>
                                 <option value="D">展減級距</option>
                                 <option value="E">平均薪資級距</option>
                                 <option value="F">年資級距</option>
                                 <option value="G">年齡級距</option>
                                 <option value="H">投保薪資級距</option>
                              </select>&nbsp;&nbsp;&nbsp;
                              <span id="qryTypeShow"></span>
                           </td>
                        </tr>
                        <tr>
                           <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                           <td colspan="2">
                              <hr size="1" noshade>
                              <span class="titleinput">※注意事項：</span><br>
                              　1.&nbsp;案件類別為全部時，核付年月起迄僅可指定1個月。<br>
                              　2.&nbsp;有關年月的欄位，填寫格式如民國108年1月1日，請輸入1080101。<br>
                              　3.&nbsp;<span class="needtxt">＊</span>為必填欄位。
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
