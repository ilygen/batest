// -------------------------------------------------------------
// 一般驗證處理函式
// -------------------------------------------------------------

// 顯示 Struts Validation 錯誤訊息
function showErrorMessage(message) {
    if (message != null && Trim(message) != "") {
        message = message.replace(/<ul>/gi,'');
        message = message.replace(/<\/ul>/gi,"");
        message = message.replace(/<li>/gi, "");
        message = message.replace(/<\/li>/gi, "\r\n");
        alert(message);
    }
}

// 身分證字號檢查
// begin ... [
var IDN_STR = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
var NUM_STR = "0123456789";

function isValidIdNoForTest(IdNo) {
    var bValid = true;

    if (IdNo != null && IdNo.length == 10) {
        IdNo = Trim(IdNo.toUpperCase());

        if (IDN_STR.indexOf(IdNo.substr(0,1)) < 0) {
            bValid = false;
        } else if ((IdNo.substr(1,1) != "1") && (IdNo.substr(1,1) != "2")) {
            bValid = false;
        }
    }else {
        bValid = false;
    }

    return bValid;
}

function isValidIdNo(IdNo) {
    var bValid = true;

    if (IdNo != null && IdNo.length == 10) {
        IdNo = Trim(IdNo.toUpperCase());
        if (!chkPID_CHAR(IdNo)) return false;

        var iChkNum = getPID_SUM(IdNo);

        if (iChkNum % 10 != 0) {
            var iLastNum = IdNo.substr(9, 1) * 1;
            for (i=0; i<10; i++) {
                var xRightAlpNum = iChkNum - iLastNum + i;
                if ((xRightAlpNum % 10) ==0) {
                    bValid = false;
                    break;
                }
            }
        }
    }
    else {
        bValid = false;
    }

    return bValid;
}

// 身分證字號檢查 - 檢查合法字元
function chkPID_CHAR(sPID) {
    sPID = sPID.toUpperCase();
    var bValid = true;
    var iPIDLen = String(sPID).length;

    var sChk = IDN_STR + NUM_STR;
    for(i=0;i<iPIDLen;i++) {
        if (sChk.indexOf(sPID.substr(i,1)) < 0) {
            bValid = false;
            break;
        }
    }

    if (bValid) {
        if (IDN_STR.indexOf(sPID.substr(0,1)) < 0) {
            bValid = false;
        } else if ((sPID.substr(1,1) != "1") && (sPID.substr(1,1) != "2")) {
            bValid = false;
        } else {
            for(var i=2; i<iPIDLen; i++) {
                if (NUM_STR.indexOf(sPID.substr(i, 1)) < 0) {
                    bValid = false;
                    break;
                }
            }
        }
    }
    return bValid;
}

// 身分證字號檢查 - 累加檢查碼
function getPID_SUM(sPID) {
    var iChkNum = 0;

    // 第 1 碼
    iChkNum = IDN_STR.indexOf(sPID.substr(0,1)) + 10;
    iChkNum = Math.floor(iChkNum/10) + (iChkNum%10*9);

    // 第 2 - 9 碼
    for(var i=1; i<sPID.length-1; i++) {
        iChkNum += sPID.substr(i,1) * (9-i);
    }

    // 第 10 碼
    iChkNum += sPID.substr(9,1)*1;

    return iChkNum;
}
// ] ... end

// -------------------------------------------------------------
// 網頁元件處理函式
// -------------------------------------------------------------

// Span / Div 物件的展開及收闔處理
// begin ... [
function procDivShow(element) {
    if (typeof element == 'string')
        element = document.getElementById(element);

    if(element.style.display == 'none')
        element.style.display = 'inline';
    else
        element.style.display = 'none';
}
// ] ... end

// Checkbox 全選 / 全部取消 的處理
// 用於頁面上僅有一排checkBox
// begin ... [
function selectAll(obj, elements) {
    if (typeof obj == 'string')
        obj = document.getElementById(obj);

    if (typeof elements == 'string')
        elements = document.getElementsByName(elements);

    for (i = 0; i < elements.length; i++) {
        if(elements[i].disabled != true && elements[i].readonly != true){
            elements[i].checked = obj.checked;
        }
    }
}
// ] ... end

// Checkbox 全選 / 全部取消 的處理 
// 用於頁面上有多排checkBox
// begin ... [
function selectAllPlus(obj, elements) {
    if (typeof obj == 'string')
        obj = document.getElementById(obj);
    
    if (typeof elements == 'string')
        element = document.getElementsByName(elements);
    
    for (i = 0; i < element.length; i++) {
        if(element[i].disabled != true && element[i].readonly != true){
            element[i].checked = obj.checked;                       
            if(obj.checked==true){
                objs = document.getElementsByName(element[i].name);
                for (j = 0; j < objs.length; j++) {
                    if (objs[j]!=element[i]) {                    
                        objs[j].checked = false; 
                    }                
                }
            }
        }
    }
}
// ] ... end

// 輸入欄位打滿時, 自動移到指定欄位
//
// 參數:
// origObj - 來源物件的 ID 或 Object
// nextObj - 目標物件的 ID 或 Object
//
// 範例:
// onkeyup="autotab(this, $('nextId'))"
// onkeyup="autotab(this, 'nextId')"
// onkeyup="autotab($('origId'), $('nextId'))"
// onkeyup="autotab('origId', 'nextId')"
//
// begin ... [
function autotab(origObj, nextObj) {
    if (typeof origObj == 'string')
        origObj = document.getElementById(origObj);

    if (typeof nextObj == 'string')
        nextObj = document.getElementById(nextObj);

    if (event.keyCode!=8  && event.keyCode!=9  && event.keyCode!=12 && event.keyCode!=13 &&
        event.keyCode!=16 && event.keyCode!=17 && event.keyCode!=18 && event.keyCode!=20 &&
        event.keyCode!=27 && event.keyCode!=32 && event.keyCode!=33 && event.keyCode!=34 &&
        event.keyCode!=35 && event.keyCode!=36 && event.keyCode!=37 && event.keyCode!=38 &&
        event.keyCode!=39 && event.keyCode!=40 && event.keyCode!=45 && event.keyCode!=46) {
        
        if (origObj != null && nextObj != null && (origObj.type=="text" || origObj.type=="textarea") && (nextObj.type=="text" || nextObj.type=="textarea" || nextObj.type=="button")) {
            if (origObj.getAttribute("maxlength") != null) {
                if (origObj.value.length >= origObj.getAttribute("maxlength")) {
                    if (!nextObj.disabled) {
                        nextObj.focus();
                        if (nextObj.getAttribute("type") == "text" ) {
                            nextObj.select();
                        }                        
                    }
                }
            }
        }
    }
}
// ] ... end

// 取得 Radio 控制元件所選取的項目的值
// begin ... [
function getRadioValue(elements) {
    if (typeof elements == 'string')
        elements = document.getElementsByName(elements);

    if (elements == null)
        return "";
        
    var value = "";
    
    for (i = 0; i < elements.length; i++) {
        if (elements[i].checked)
            value = elements[i].value;
    }
    
    return value;
}
// ] ... end

// 設定 Radio 控制元件所選定的值
// begin ... [
function setRadioValue(elements, value) {
    if (typeof elements == 'string')
        elements = document.getElementsByName(elements);

    if (elements != null) {
        for (i = 0; i < elements.length; i++) {
            if (elements[i].value == value)
                elements[i].checked = true;
        }
    }
}
// ] ... end

// 清空 Radio 控制元件的值 (取消選取)
// begin ... [
function clearRadio(elements) {
    if (typeof elements == 'string')
        elements = document.getElementsByName(elements);

    if (elements != null) {
        for (i = 0; i < elements.length; i++) {
            if (elements[i].checked)
                elements[i].checked = false;
        }
    }
}
// ] ... end

// 設定指定的 text 輸入元件的 maxlength
// begin ... [
function setTextInputMaxLength(element, length) {
    if (typeof element == 'string')
        element = document.getElementById(element);

    if (typeof length == 'string')
        length = parseNumber(length);

    if (element != null) {
        element.setAttribute("maxlength", length);
        element.maxLength = length;
        element.setAttribute("size", length + 3);
    }
}
// ] ... end

// 畫面清空
// begin ... [
function cleanForm() {
    for (i = 0; i < document.forms[0].length; i++) {
        obj = document.forms[0].elements[i];
        if(obj.disabled != true && obj.readOnly != true){
	        switch(obj.type){
	            case "text":
	                obj.value="";
	                break;
	            case "textarea":
	                obj.value="";
	                break;
	            case "password":
                    obj.value="";
                    break;
	            case "radio":
	                var objName = obj.getAttribute("name")
	                elements = document.getElementsByName(objName)
	                for (j = 0; j < elements.length; j++){
	                    if(j==0)
	                        elements[j].checked=true
	                    else
	                        elements[j].checked=false
	                }
	                break;
	            case "checkbox":
	                obj.checked = false;
	                break;
	            case "select-one" :
	                obj.options[0].selected = true;
	                break;
	            case "select-multiple" :
	                while (obj.selectedIndex != -1){
	                    indx = obj.selectedIndex;
	                    obj.options[indx].selected = false;
	                }
	                obj.selected = false;
	                break;
	       }
        }
    }
}
// ] ... end

// -------------------------------------------------------------
// 字串處理函式
// -------------------------------------------------------------

// 將字串轉換為數值
// 由於 JavaScript 的 parseFloat() 內定會將 0 開頭的字串當成八進位處理,
// 所以若要處理十進位則必須自行處理轉換的動作
// begin ... [
function parseNumber(value) {
    if (Trim(value).length == 0)
        return "";
    // 刪除字串開頭的 "0"
    var zeroIndex = 0;
    while (value.charAt(zeroIndex) == '0') {
        zeroIndex++;
    }
    value = value.substring(zeroIndex, value.length);
    if (value == "")
        value = "0";
    if (value.charAt(0) == '.')
        value = "0" + value;
    if (isNaN(value))
        return "";
    else
        return parseFloat(value);
}
// ] ... end

// 字串處理 - 在字串左邊補上指定字元至指定長度
// begin ... [
function leftPad(str, size, padChar) {
    str = "" + str; // 如果傳入的為數字先轉為字串

    if (padChar == null || padChar == "")
        padChar = " ";

    while (str.length < size) {
        str = padChar + str;
    }

    return str;
}
// ] ... end

// 字串處理 - 在字串右邊補上指定字元至指定長度
// begin ... [
function rightPad(str, size, padChar) {
    str = "" + str; // 如果傳入的為數字先轉為字串

    if (padChar == null || padChar == "")
        padChar = " ";

    while (str.length < size) {
        str = str + padChar;
    }

    return str;
}
// ] ... end

// 字串處理 - 去除傳入字串左右之空白字元
// begin ... [
function Trim(STRING) {
    STRING = LTrim(STRING);
    return RTrim(STRING);
}
// ] ... end

// 字串處理 - 去除傳入字串右邊之空白字元
// begin ... [
function RTrim(STRING) {
    while(STRING.charAt((STRING.length -1))==" ")
    {
        STRING = STRING.substring(0, STRING.length - 1);
    }
    return STRING;
}
// ] ... end

// 字串處理 - 去除傳入字串左邊之空白字元
// begin ... [
function LTrim(STRING) {
    while(STRING.charAt(0)==" ")
    {
        STRING = STRING.replace(STRING.charAt(0),"");
    }
    return STRING;
}

// 判斷傳入的字串是否超過指定長度<br>
// 一個中文字為兩個字元
function checkStringMaxLength(str, max) {
    var isValid = true;
    var strlen = 0;
    if(str!=null && str!=""){
        for (i = 0; i < str.length; i++) {
            if (isValid) {
                if (str.charCodeAt(i) >= 10000){
                    strlen += 2;
                }else{
                    strlen += 1;
                }                
            }                       
        }
        if(strlen>max){
            isValid = false;
        }else{
            isValid = true;
        }
    }   
    return isValid;
}
// ] ... end

//數字金額千位分隔
function numFormat(stringValue) {
    stringEnd = " ";
    n = "";
    if (isNaN(stringValue)||stringValue==null) {
        return "";
    } else {
        stringValue = stringValue.toString();
        if (stringValue.indexOf(".") != -1) {
            aa = stringValue.split(".");
            stringValue = aa[0];
            stringEnd = "." + aa[1];
        }
        stringValue = "" + eval(stringValue);
        len = stringValue.length;
        for (i = len - 1; i >= 0; i--) {
            n = stringValue.charAt(i) + n;
            if ((((len - i) % 3) == 0) && (i != 0)) {
                n = "," + n;
            }
        }
        return n + stringEnd;
    }
}
// -------------------------------------------------------------
// 日期處理函式
// -------------------------------------------------------------

// 檢查傳入之日期是否為有效日期
//
// 參數:
// dateValue - 日期, 可接受民國及西元日期
//
// 回傳值:
// 是 - true
// 否 - false
//
// 範例:
// isValidDate("0960229") - false
// isValidDate("20070229") - false
// isValidDate("0970229") - true
// isValidDate("20080229") - true
//
// begin ... [
function isValidDate(dateValue) {
    return isValidDate(dateValue, false)
}
// ] ... end

// 檢查傳入之日期是否為有效日期
//
// 參數:
// dateValue - 日期, 可接受民國及西元日期
// bBeforeROC - true 民國前 false 民國
//
// 回傳值:
// 是 - true
// 否 - false
//
// begin ... [
function isValidDate(dateValue, bBeforeROC)
{
    var isValid = true;
    var iValue = Trim(dateValue);
    var pDateType=iValue.length;

    if (((iValue.replace(/[-.]/gi,'')).length!=7 && (iValue.replace(/[-.]/gi,'')).length!=8) || (isNaN(iValue)) || (parseNumber(iValue.substr(0,(pDateType-4))) == 0) ) {
        isValid = false;
    }
    else {
        var nYear = 0;
        var nDay = parseNumber(iValue.substr((pDateType-2),2));
        var nMon = parseNumber(iValue.substr((pDateType-4),2))-1;

        if (pDateType==7) {
            if (bBeforeROC)
                nYear = 1912 - parseNumber(iValue.substr(0,(pDateType-4)));
            else
                nYear = parseNumber(iValue.substr(0,(pDateType-4))) + 1911;
        }
        else {
            nYear = parseNumber(iValue.substr(0,(pDateType-4)));
        }

        var sDate = new Date(Date.UTC(nYear, nMon, nDay, 0, 0, 0));

        if(!((sDate.getUTCFullYear()==nYear) && (sDate.getMonth()==nMon) && (sDate.getDate()==nDay))) {
            isValid = false;
        }
    }

    return isValid;
}
// ] ... end

// 檢查傳入之日期是否為有效年月
//
// 參數:
// dateValue - 日期, 可接受民國及西元年月
// bBeforeROC - true 民國前 false 民國
//
// 回傳值:
// 是 - true
// 否 - false
//
// begin ... [
function isValidYearMonth(ymValue) {
    return isValidYearMonth(ymValue, false)
}

function isValidYearMonth(ymValue, bBeforeROC){
    var isValid = true;
    var iValue = Trim(ymValue);
    var pDateType=iValue.length;
    
    if ((iValue.replace(/[-.]/gi,'')).length!=5 || (isNaN(iValue)) || (parseNumber(iValue.substr(0,(pDateType-2))) == 0) ) {
        isValid = false;
    }
    else {
        var nYear = 0;
        var nMon = parseNumber(iValue.substr((pDateType-2),2))-1;
        var nDay = 1;
       
        if (pDateType==5) {
            if (bBeforeROC)
                nYear = 1912 - parseNumber(iValue.substr(0,(pDateType-2)));
            else
                nYear = parseNumber(iValue.substr(0,(pDateType-2))) + 1911;
        }
        else {
            nYear = parseNumber(iValue.substr(0,(pDateType-2)));
        }
        var sDate = new Date(Date.UTC(nYear, nMon, nDay, 0, 0, 0));
        
        if(!((sDate.getUTCFullYear()==nYear) && (sDate.getMonth()==nMon) && (sDate.getDate()==nDay))) {
            isValid = false;
        }
    }
    return isValid;
}
// ] ... end

// 檢查傳入之日期是否為有效年
//
// 參數:
// dateValue - 日期, 可接受民國及西元年
// bBeforeROC - true 民國前 false 民國
//
// 回傳值:
// 是 - true
// 否 - false
//
// begin ... [

function isValidYear(yValue) {
    return isValidYear(yValue, false)
}

function isValidYear(yValue, bBeforeROC){
    var isValid = true;
    var iValue = Trim(yValue);
    var pDateType=iValue.length;
    
    if ((iValue.replace(/[-.]/gi,'')).length!=3 || (isNaN(iValue))) {
        isValid = false;
    }
    else {

        var nYear = 0;
		var nMon = 1;
        var nDay = 1;
       
        if (pDateType==3) {
            if (bBeforeROC)
                nYear = 1912 - parseNumber(iValue);
            else
                nYear = parseNumber(iValue) + 1911;
        }
        else {
            nYear = parseNumber(iValue);
        }
        
        var sDate = new Date(Date.UTC(nYear, nMon, nDay, 0, 0, 0));

        if(!((sDate.getUTCFullYear()==nYear) && (sDate.getMonth()==nMon) && (sDate.getDate()==nDay))) {
            isValid = false;
        }
    }
    return isValid;
}
// ] ... end

// 檢查傳入之日期是否為有效年
//
// 參數:
// dateValue - 日期, 可接受民國及西元年
// bBeforeROC - true 民國前 false 民國
//
// 回傳值:
// 是 - true
// 否 - false
//
// begin ... [

function isValidMonth(mValue) {
    return isValidMonth(mValue, false)
}

function isValidMonth(mValue, bBeforeROC){
    var isValid = true;
    var iValue = Trim(mValue);
    var pDateType=iValue.length;
    
    if ((iValue.replace(/[-.]/gi,'')).length!=2 || (isNaN(iValue))) {
        isValid = false;
    }
    else {

        var nYear = 1911;
		var nMon = parseNumber(iValue)-1;
        var nDay = 1;
        
        var sDate = new Date(Date.UTC(nYear, nMon, nDay, 0, 0, 0));
		
        if(!((sDate.getUTCFullYear()==nYear) && (sDate.getMonth()==nMon) && (sDate.getDate()==nDay))) {
            isValid = false;
        }
    }
    return isValid;
}
// ] ... end

// 民國日期轉西元, 西元日期轉民國
// (若傳入民國日期, 僅可傳入民國後日期)
//
// 參數:
// sDate - 民國或西元日期
//
// 範例:
// changeDateType("0961231") - "20071231"
// changeDateType("20071231") - "0961231"
// changeDateType("0960229") - "0960229" (日期不合法, 回傳原值)
// changeDateType("20070229") - "20070229" (日期不合法, 回傳原值)
//
// begin ... [
function changeDateType(sDate) {
    sDate = Trim(sDate);

    if (!isValidDate(sDate))
        return sDate;

    if (sDate.length == 7) {
        return leftPad(parseNumber(sDate.substring(0, 3)) + 1911, 4, "0") + sDate.substring(3, 5) + sDate.substring(5, 7);
    }
    else if (sDate.length == 8) {
        return leftPad(parseNumber(sDate.substring(0, 4)) -1911, 3, "0") + sDate.substring(4, 6) + sDate.substring(6, 8);
    }
    else {
        return sDate;
    }
}
// ] ... end

// 日期加減運算
// 往後為加, 往前為減
//
// 參數:
// sDate - 民國或西元日期
// nDay - 欲加減的天數
//
// 回傳值:
// 傳入 民國日期 - 計算後之民國日期
// 傳入 西元日期 - 計算後之西元日期
//
// 範例:
// calDay("0961231", 1) - "0970101"
// calDay("20071231", 1) - "20080101"
// calDay("0960101", 1) - "0960102"
// calDay("20070101", 1) - "20070102"
// calDay("0961211", -15) - "0961126"
// calDay("20071211", -15) - "20071126"
//
// begin ... [
function calDay(sDate, nDay) {
    sDate = Trim(sDate);

    if (!isValidDate(sDate))
        return sDate;

    var bChinese = false;

    if (sDate.length == 7)
        bChinese = true;

    var baseDate = sDate;
    if (bChinese)
        baseDate = changeDateType(baseDate);

    var theDate = new Date(parseNumber(baseDate.substring(0, 4)), parseNumber(baseDate.substring(4, 6)) -1, baseDate.substring(6, 8), 00, 00, 00);
    var b = theDate.getDate();
    b = b + nDay;
    theDate.setDate(b);

    if (bChinese)
        return leftPad(theDate.getFullYear() - 1911, 3, "0") + leftPad(theDate.getMonth() + 1, 2, "0") + leftPad(theDate.getDate(), 2, "0");
    else
        return leftPad(theDate.getFullYear(), 4, "0") + leftPad(theDate.getMonth() + 1, 2, "0") + leftPad(theDate.getDate(), 2, "0");
}
// ] ... end

// 月份加減運算
// 往後為加, 往前為減
//
// 參數:
// sDate - 民國或西元日期
// nMonth - 欲加減的月數
//
// 回傳值:
// 傳入 民國日期 - 計算後之民國日期
// 傳入 西元日期 - 計算後之西元日期
//
// 範例:
// calDay("0961201", 2) - "0970201"
// calDay("200712", 1) - "20080212"
// calDay("0960101", 1) - "0960301"
//
// begin ... [
function calMonth(sDate, nMonth) {
    if (!isValidDate(sDate))
        return sDate;

    var bChinese = false;
    
    if (sDate.length == 7)
        bChinese = true;
    
    var baseDate = sDate;
    
    if (bChinese)
        baseDate = changeDateType(baseDate);
    
    var theDate = new Date(parseNumber(baseDate.substring(0, 4)), parseNumber(baseDate.substring(4, 6)) - 1, baseDate.substring(6, 8), 00, 00, 00);
    
    var b = theDate.getMonth();
    b = b + nMonth;
    theDate.setMonth(b);
    
    if (bChinese)
        return leftPad(theDate.getFullYear() - 1911, 3, "0") + leftPad(theDate.getMonth() + 1, 2, "0") + leftPad(theDate.getDate(), 2, "0");
    else
        return leftPad(theDate.getFullYear(), 4, "0") + leftPad(theDate.getMonth() + 1, 2, "0") + leftPad(theDate.getDate(), 2, "0");
}

// ] ... end

// 全形半形轉換
// begin ... [
function asc(text)
{
    var asciiTable = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    var big5Table  = "%u3000%uFF01%u201D%uFF03%uFF04%uFF05%uFF06%u2019%uFF08%uFF09%uFF0A%uFF0B%uFF0C%uFF0D%uFF0E%uFF0F%uFF10%uFF11%uFF12%uFF13%uFF14%uFF15%uFF16%uFF17%uFF18%uFF19%uFF1A%uFF1B%uFF1C%uFF1D%uFF1E%uFF1F%uFF20%uFF21%uFF22%uFF23%uFF24%uFF25%uFF26%uFF27%uFF28%uFF29%uFF2A%uFF2B%uFF2C%uFF2D%uFF2E%uFF2F%uFF30%uFF31%uFF32%uFF33%uFF34%uFF35%uFF36%uFF37%uFF38%uFF39%uFF3A%uFF3B%uFF3C%uFF3D%uFF3E%uFF3F%u2018%uFF41%uFF42%uFF43%uFF44%uFF45%uFF46%uFF47%uFF48%uFF49%uFF4A%uFF4B%uFF4C%uFF4D%uFF4E%uFF4F%uFF50%uFF51%uFF52%uFF53%uFF54%uFF55%uFF56%uFF57%uFF58%uFF59%uFF5A%uFF5B%uFF5C%uFF5D%uFF5E";
 
    var result = "";
    for (var i = 0; i < text.length; i++) {
        var val = escape(text.charAt(i));
        var j   = big5Table.indexOf(val);
        result += (((j > -1) && (val.length == 6)) ? asciiTable.charAt(j / 6) : text.charAt(i));
    }
    
    return result;
}

function unAsc(text) {
    var asciiTable = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    var big5Table = "%u3000%uFF01%u201D%uFF03%uFF04%uFF05%uFF06%u2019%uFF08%uFF09%uFF0A%uFF0B%uFF0C%uFF0D%uFF0E%uFF0F%uFF10%uFF11%uFF12%uFF13%uFF14%uFF15%uFF16%uFF17%uFF18%uFF19%uFF1A%uFF1B%uFF1C%uFF1D%uFF1E%uFF1F%uFF20%uFF21%uFF22%uFF23%uFF24%uFF25%uFF26%uFF27%uFF28%uFF29%uFF2A%uFF2B%uFF2C%uFF2D%uFF2E%uFF2F%uFF30%uFF31%uFF32%uFF33%uFF34%uFF35%uFF36%uFF37%uFF38%uFF39%uFF3A%uFF3B%uFF3C%uFF3D%uFF3E%uFF3F%u2018%uFF41%uFF42%uFF43%uFF44%uFF45%uFF46%uFF47%uFF48%uFF49%uFF4A%uFF4B%uFF4C%uFF4D%uFF4E%uFF4F%uFF50%uFF51%uFF52%uFF53%uFF54%uFF55%uFF56%uFF57%uFF58%uFF59%uFF5A%uFF5B%uFF5C%uFF5D%uFF5E";
    var result = "";
    for (var i = 0; i < text.length; i++) {
        var val = text.charAt(i);
        var j = asciiTable.indexOf(val) * 6;
        result += (j > -1 ? unescape(big5Table.substring(j, j + 6)) : val);
    }
    return result;
}

//半形空白轉全形空白，其他轉半形
function unAscSpaceOtherAsc(text) {
	var asciiTable = " ";
	var asciiTableExceptSpace = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
	var big5Table = "%u3000";
	var big5TableExceptSpace = "%uFF01%u201D%uFF03%uFF04%uFF05%uFF06%u2019%uFF08%uFF09%uFF0A%uFF0B%uFF0C%uFF0D%uFF0E%uFF0F%uFF10%uFF11%uFF12%uFF13%uFF14%uFF15%uFF16%uFF17%uFF18%uFF19%uFF1A%uFF1B%uFF1C%uFF1D%uFF1E%uFF1F%uFF20%uFF21%uFF22%uFF23%uFF24%uFF25%uFF26%uFF27%uFF28%uFF29%uFF2A%uFF2B%uFF2C%uFF2D%uFF2E%uFF2F%uFF30%uFF31%uFF32%uFF33%uFF34%uFF35%uFF36%uFF37%uFF38%uFF39%uFF3A%uFF3B%uFF3C%uFF3D%uFF3E%uFF3F%u2018%uFF41%uFF42%uFF43%uFF44%uFF45%uFF46%uFF47%uFF48%uFF49%uFF4A%uFF4B%uFF4C%uFF4D%uFF4E%uFF4F%uFF50%uFF51%uFF52%uFF53%uFF54%uFF55%uFF56%uFF57%uFF58%uFF59%uFF5A%uFF5B%uFF5C%uFF5D%uFF5E";
	var result = "";
	
	for(var i = 0; i < text.length; i++) {
		var val = escape(text.charAt(i));
		var j = big5TableExceptSpace.indexOf(val);
		
		if(asciiTable.indexOf(text.charAt(i)) == 0) {
			var val2 = text.charAt(i);
			var k = asciiTable.indexOf(val2) * 6;
			result += unescape(big5Table);
		}else {
			result += (((j > -1) && (val.length == 6)) ? asciiTableExceptSpace.charAt(j / 6) : text.charAt(i));
		}

	}
	
	return result;
}

//檢查輸入自串是否為英數字
//是, 回傳true
//否, 回傳false
function isEngNum(str){
    re = /\W/;
    if (re.test(str))
    {
        return false;
    }
    else
    {
        return true;
    }
}

// 檢查傳入之國籍碼是否有效
//
// 參數:
// nValue - 國籍碼, 可接受民國及西元年
//
// 回傳值:
// 是 - true
// 否 - false
//
// begin ... [

function isNationCode(nValue) {
    return isNationCode(nValue, false)
}

function isNationCode(nValue){
    var isValid = true;
    var iValue = Trim(nValue);
    var pNationType=iValue.length;
    
    if ((iValue.replace(/[-.]/gi,'')).length!=3 || (isNaN(iValue))) {
        isValid = false;
    }
    
    return isValid;
}
// ] ... end
 
// -------------------------------------------------------------
// 輸入法處理函式
// -------------------------------------------------------------
// 輸入欄位輸入法切換
// begin ... [
function toEngInput(obj) {
    obj.style.imeMode = "inactive";
}

function toChtInput(obj) {
    obj.style.imeMode = "active";
}
// ] ... end

// check PayEeacc
function isValidPayEeacc(branch , account){
    var isValid = false;
    var str=new Array("0","0","0","0","0","0","0","0","0","0","0","0","0","0")

    for(var i=0;i<=account.length;i++)
        str[i]=account.charAt(i);

    if(branch == "7000010" && account.length <= 8){
        if (str[7] == ((11-((str[0]*2 + str[1]*3 + str[2]*4 + str[3]*5 + str[4]*6 + str[5]*7 +str[6]*8 )%11))%10)) {
	        isValid = false;
	    }
	    else {
	        isValid=true;
	    }
    } else if(branch == "7000021" && account.length <= 14){
        if(str[6] == ((11-((str[0]*2 + str[1]*3 + str[2]*4 + str[3]*5 + str[4]*6 + str[5]*7)%11))%10)){
            if (str[13] == ((11-((str[7]*2 + str[8]*3 + str[9]*4 + str[10]*5 + str[11]*6 + str[12]*7)%11))%10)) {
	            isValid = false;
	            }
	        else {
	            isValid = true;
	        }
        }
	    else {
	            isValid = true;
	    }
	}

	return isValid;
}


// -------------------------------------------------------------
// 日期區間處理物件
// -------------------------------------------------------------
// begin ... [

/**
 * DateRange class
 * 接受一組民國年月之起迄年月為參數,endDate必須大於startDate
 * 目前未處理endDate 小於 startDate的情況,若輸入之endDate 小於startDate
 * 則可能會產生預期外的狀況
 *
 * 使用例:
 * var aRange = new DateRange('09001', '09212');
 *
 * @author Azuritul
 * @param startDate
 * @param endDate
 */
function DateRange(startDate, endDate) {
    this.rangeStart = startDate;
    this.rangeEnd = endDate;
    this.monthsInBetween = [];
}
DateRange.prototype.monthArray = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];

/**
 * 用於幫object的monthsInBetween[]賦值
 *
 * 使用例:
 * var aRange = new DateRange('09001', '09212');
 * aRange.extractMonthsInBetween();
 * 則aRange.monthsInBetween[]會有09001, 09002, 09003 ... 09212之資料
 *
 */
DateRange.prototype.extractMonthsInBetween = function() {
    var count = 1;
    this.monthsInBetween[0] = this.rangeStart;
    if (this.rangeStart < this.rangeEnd) {
        this.monthsInBetween[count] = this.getNextYearMonth(this.rangeStart);
        while ( parseInt(this.monthsInBetween[count], 10) != parseInt(this.rangeEnd, 10) ) {
            var next = this.getNextYearMonth(this.monthsInBetween[count]);
            count++;
            this.monthsInBetween[count] = next;
        }
    }
};

/**
 * 檢查傳進來的date是否在本range之中
 * @param inputDate
 */
DateRange.prototype.isDateInRange = function(inputDate) {
    return inputDate >= this.rangeStart && inputDate <= this.rangeEnd;
};

/**
 * 取得傳入參數的下個月
 * 使用例:
 * var aRange = new DateRange('09001', '09212');
 * var nextYM = aRange.getNextYearMonth(aRange.studyStart);
 * 則nextYM之值為'09002'
 *
 * @param yearMonth
 */
DateRange.prototype.getNextYearMonth = function(yearMonth) {
    var year = yearMonth.substring(0, 3);
    var month = yearMonth.substring(3, 5);
    var nextYear, nextMonth;
    if (parseInt(month, 10) == 12) {
        nextMonth = this.monthArray[0];
        if (year.charAt(0) != '0') {
            nextYear = parseInt(year, 10) + 1;
        }
        else {
            if(year == '099')
                nextYear = '100';
            else
                nextYear = '0' + (parseInt(year, 10) + 1);
        }
        return nextYear + nextMonth;
    }
    if(year.charAt(0) != '0') nextYear = parseInt(year, 10);
    else nextYear = '0' + (parseInt(year, 10));
    nextMonth = this.monthArray[parseInt(month, 10)];
    return nextYear + nextMonth;
};

/**
 * 印出DateRange class中 monthsInBetween 中的所有年月
 */
DateRange.prototype.printMonthsInBetween = function() {
    if (typeof this.monthsInBetween != 'undefined' && this.monthsInBetween.length != 0) {
        for (var index = 0; index < this.monthsInBetween.length; index++) {
            alert(this.monthsInBetween[index]);
        }
    }
};
// ] ... end

/**
 * 簡單的檢核類別
 * 使用例:
 * var validator = new SimpleValidator();
 * 之後就可以透過validator使用各公用函式
 * @author Azuritul
 */
function SimpleValidator() {
    //私有常數,用來存放一些預設的固定訊息
    var Constants = {
        ErrorMsg : {
            ArgumentTypeMismatch : '：參數型別不正確。',
            TooManyParams        : '：參數數量過多。',
            NotEnoughtParams     : '：參數不足。',
            ChineseDateFormat    : '：日期格式錯誤。',
            ZipFormat            : '「郵遞區號」：格式錯誤。',
            IsRequired           : '：為必輸欄位。',
            IsNumeric            : '：必須為數字。',
            IsAlphaNumeric       : '：必須為英數字。',
            LengthTooShort       : '：輸入值長度不足。',
            NoSuchElement        : '：指定的網頁元素不存在。'
        }
    };
    //私有變數,用來儲存錯誤提示訊息
    var errorMsgs = [];
    /**
     * 私有函式,用來檢查傳入值是否為空(全/半形空白也視為空)
     * @param value
     * @return true|false
     */
    var isBlank = function(value) {
        return typeof value === 'undefined' || value === null || /^(\s*　*)$/.test(value);
    };
    /**
     * 私有函式,用來檢查傳入值是否非空(全/半形空白也視為空)
     * @param value
     * @return true|false
     */
    var isNotBlank = function(value) {
        return !isBlank(value);
    };
    /**
     * 私有函式,用來檢核民國日期,目前尚未實作檢核民國前日期
     * @param fieldName 欄位名稱
     * @param value     要檢查的值
     * @return true|false　
     */
    var isYMDFormatValid = function(fieldName, value) {
        var patternBigMonth = new RegExp("^([0-9][0-9][0-9])([0][13578]|10|12)([0][1-9]|1[0-9]|2[0-9]|3[0-1])$");
        var patternSmallMonth = new RegExp("^([0-9][0-9][0-9])([0][469]|11)([0][1-9]|1[0-9]|2[0-9]|3[0])$");
        var patternFebruary = new RegExp("^([0-9][0-9][0-9])(02)([0][1-9]|1[0-9]|2[0-9])$");
        if (!(patternBigMonth.test(value) || patternSmallMonth.test(value) || patternFebruary.test(value))) {
            errorMsgs.push(fieldName + Constants.ErrorMsg.ChineseDateFormat);
            return false;
        }
        return true;
    };
    /**
     * 私有函式,用來檢核民國年月,目前尚未實作檢核民國前年月
     * @param fieldName 欄位名稱
     * @param value     要檢查的值
     * @return true|false　
     */
    var isYMFormatValid = function(fieldName, value) {
        var pattern = new RegExp("^([0-9][0-9][0-9])([0][1-9]|1[0-2])$");
        if (!pattern.test(value)) {
            errorMsgs.push(fieldName + Constants.ErrorMsg.ChineseDateFormat);
            return false;
        }
        return true;
    };
    var isMessageExisted = function(msg) {
        for(var i = 0; i < errorMsgs.length; i++){
            if(errorMsgs[i] === msg){
                return true;
            }
        }
        return false;
    };
    /**
     * 公用函式,用來檢核民國日期,會依參數:[value]的長度決定呼叫哪支檢核函式
     * @param fieldName 欄位名稱　
     * @param value     要檢查的值
     * @return true||false
     */
    this.isChineseDateFormatValid = function(fieldName, value) {
        if (fieldName != null && value != null) {
            switch (value.length) {
                case 0:
                    break;
                case 5:
                    return isYMFormatValid(fieldName, value);
                case 7:
                    return isYMDFormatValid(fieldName, value);
                default:
                    errorMsgs.push(fieldName + Constants.ErrorMsg.ChineseDateFormat);
                    return false;
            }
        }
    };
    /**
     * 公用函式,用來檢核必填欄位是否有值
     * @param fieldName 欄位名稱
     * @param elem      要檢核的HTML欄位
     * @return true|false
     */
    this.isRequiredValueEmpty = function(fieldName, elem) {
        if (!elem) throw new Error(Constants.ErrorMsg.NoSuchElement);
        //可能傳進來的東西: array, input text, checkbox, radio, select-one
        if (typeof elem.length === 'number') {
            if (elem.length >= 1) {
                for (var i = 0; i < elem.length; i++) {
                    if (typeof elem[i].checked !== 'undefined') {
                        if (elem[i].checked) {
                            return false;
                        }
                    }
                }
                if (elem.type === 'select-one') {
                    if (Trim(elem.value) !== '') {
                        return false;
                    }
                }
            }
            if(!isMessageExisted(fieldName + Constants.ErrorMsg.IsRequired)){
                errorMsgs.push(fieldName + Constants.ErrorMsg.IsRequired);
            }
            return true;
        } else {
            if (elem.nodeType === 1) {
                if (Trim(elem.value) !== '') {
                    return false;
                }
                if (!isMessageExisted(fieldName + Constants.ErrorMsg.IsRequired)) {
                    errorMsgs.push(fieldName + Constants.ErrorMsg.IsRequired);
                }
                return true;
            }
        }
        return false;
    };
    /**
     * 公用函式,用來檢核傳入的元素值是否相等,若其中任二元素相等,則產生錯誤訊息
     * @param valueSet 要比對的物件的陣列,物件結構為{name:'',value:''}
     */
    this.isNotTheSame = function(valueSet) {
        //valueSet會是name value pair的陣列
        if (valueSet.length > 30) throw new Error(Constants.ErrorMsg.TooManyParams);
        if (valueSet.length < 2) throw new Error(Constants.ErrorMsg.NotEnoughtParams);
        while (valueSet.length !== 0) {
            var toBeCompared = valueSet.shift();
            for (var i = 0; i < valueSet.length; i++) {
                if (toBeCompared['value'] === valueSet[i]['value']) {
                    errorMsgs.push(toBeCompared['name'] + '之值不得與' + valueSet[i]['name'] + '之值相同。');
                }
            }
        }
    };
    /**
     * 公用函式,用來檢核郵遞區號之格式是否正確
     * @param value 郵遞區號的值
     */
    this.isZipFormatInvalid = function(value) {
        if (isNotBlank(value) && !(value.match(/^(\d+)$/))) {
            errorMsgs.push(Constants.ErrorMsg.ZipFormat);
            return true;
        }
        return false;
    };
    /**
     * 公用函式,用來檢核傳入的值是否為數字
     * @param fieldName 欄位名稱
     * @param value     要檢查的值
     * @return true|false　
     */
    this.isNumericValue = function(fieldName, value) {
        if (isNotBlank(value) && (value.match(/^(\d+)$/))) {
            return true;
        } else {
            errorMsgs.push(fieldName + Constants.ErrorMsg.IsNumeric);
            return false;
        }
    };
    /**
     * 公用函式,用來檢核傳入的值是否為英數字
     * @param fieldName 欄位名稱　
     * @param value     要檢查的值　
     * @return true|false
     */
    this.isAlphaNumericValue = function(fieldName, value) {
        if (value.match(/^[a-zA-Z0-9]+$/)) {
            return true;
        } else {
            errorMsgs.push(fieldName + Constants.ErrorMsg.IsAlphaNumeric);
            return false;
        }
    };
    /**
     * 公用函式,用來檢核傳入的值是否有達到指定長度
     * @param fieldName 欄位名稱　
     * @param value     要檢查的值
     * @param length    長度　
     */
    this.checkLength = function(fieldName, value, length) {
        if (isNotBlank(value) && value.length !== length) {
            errorMsgs.push(fieldName + '：長度需為' + length + '碼');
        }
    };
    /**
     * 公用函式,用來檢核傳入的arg0是否有大於arg1
     * @param arg0
     * @param arg1
     * @return true|false
     */
    this.isGreaterThan = function(arg0, arg1) {
        if (arg0['value'] <= arg1['value']) {
            errorMsgs.push(arg0['name'] + '需大於' + arg1['name']);
            return false;
        }
        return true;
    };
    /**
     * 公用函式,用來檢核傳入的arg0是否大於等於arg1
     * @param arg0
     * @param arg1
     * @return true|false
     */
    this.isGreaterThanOrEqual = function(arg0, arg1) {
        if (arg0['value'] < arg1['value']) {
            errorMsgs.push(arg0['name'] + '需大於或等於' + arg1['name']);
            return false;
        }
        return true;
    };
    /**
     * 公用函式,用來檢核傳入的arg0是否有小於arg1
     * @param arg0
     * @param arg1
     * @return true|false
     */
    this.isSmallerThan = function(arg0, arg1) {
        if (arg0['value'] >= arg1['value']) {
            errorMsgs.push(arg0['name'] + '需小於' + arg1['name']);
            return false;
        }
        return true;
    };
    /**
     * 公用函式,用來檢核傳入的arg0是否小於等於arg1
     * @param arg0
     * @param arg1
     * @return true|false
     */
    this.isSmallerThanOrEqual = function(arg0, arg1) {
        if (arg0['value'] > arg1['value']) {
            errorMsgs.push(arg0['name'] + '需小於或等於' + arg1['name']);
            return false;
        }
        return true;
    };
    this.addErrorMsg = function(msg) {
        errorMsgs.push(msg);
    };
    this.clearErrorMsg = function() {
        errorMsgs = [];
    };
    this.getErrorMsgs = function() {
        return errorMsgs;
    };
    this.getErrorMsgsAsString = function() {
        return (errorMsgs.length === 0) ? '' : ( errorMsgs.length === 1 ? errorMsgs[0] + '\r\n' : errorMsgs.join('\r\n') );
    };
};

//字串處理
//如果傳入值為null, 回傳" "；否則回傳原始傳入值
//防止資料底線消失
function getDefaultString(strValue){
    if(strValue=="" || strValue==" " || strValue==null){
        return "&nbsp;";
    }else{
        return strValue;
    }
}
